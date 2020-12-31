using import enum
using import struct
using import Array
using import String
using import glm

spice patch-shader (shader patch)
    shader as:= string
    patch as:= string
    let match? start end = ('match? "^#version \\d\\d\\d\\n" shader)
    if match?
        let head = (lslice shader end)
        let tail = (rslice shader end)
        let result = (.. head patch tail)
        `result
    else
        error "unrecognized shader input"
run-stage;

import .window
import .io
import .math
using import .radlib.core-extensions
let glfw = (import .FFI.glfw)
let gl = (import .FFI.glad)

typedef GPUBuffer <:: u32
    inline... __typecall (cls)
        bitcast 0 this-type
    case (cls kind size)
        local handle : u32
        gl.GenBuffers 1 &handle
        gl.BindBuffer (kind as u32) handle
        gl.NamedBufferStorage handle (size as i64) null
            gl.GL_DYNAMIC_STORAGE_BIT
        bitcast handle this-type

    inline __drop (self)
        local handle : u32 = (storagecast (view self))
        gl.DeleteBuffers 1 &handle
        ;

typedef Mesh < Struct
    @@ memo
    inline __typecall (cls attributeT indexT)
        struct (.. "Mesh<" (tostring attributeT) "," (tostring indexT) ">")
            let IndexFormat = indexT
            let AttributeType = attributeT

            attribute-data : (Array AttributeType)
            _attribute-buffer : GPUBuffer
            _attribute-buffer-size : usize
            index-data : (Array IndexFormat)
            _index-buffer : GPUBuffer
            _index-buffer-size : usize

            fn ensure-storage (self)
                """"If store size is not enough to contain uploaded data, destroys the
                    attached GPU resources and recreates them with differently sized data stores.
                let attr-data-size = ((sizeof AttributeType) * (countof self.attribute-data))
                let index-data-size = ((sizeof IndexFormat) * (countof self.index-data))

                # find a size that can hold the amount of data we want by multiplying by 2 repeatedly
                inline find-next-size (current required)
                    if (current == 0)
                        required
                    else
                        loop (size = (deref current))
                            assert (size >= current) # probably an overflow
                            if (size >= required)
                                break size
                            size * 2

                if (self._attribute-buffer-size < attr-data-size)
                    let new-size = (find-next-size self._attribute-buffer-size attr-data-size)
                    self._attribute-buffer =
                        GPUBuffer gl.GL_SHADER_STORAGE_BUFFER new-size
                    self._attribute-buffer-size = new-size
                if (self._index-buffer-size < index-data-size)
                    let new-size = (find-next-size self._index-buffer-size index-data-size)
                    self._index-buffer =
                        GPUBuffer gl.GL_ELEMENT_ARRAY_BUFFER new-size
                    self._index-buffer-size = new-size
                ;

            fn update (self)
                """"Uploads mesh data to GPU.
                'ensure-storage self
                gl.NamedBufferSubData self._attribute-buffer 0 (self._attribute-buffer-size as i64)
                    (imply self.attribute-data pointer) as voidstar
                gl.NamedBufferSubData self._index-buffer 0 (self._index-buffer-size as i64)
                    (imply self.index-data pointer) as voidstar

            fn draw (self)
                gl.BindBufferBase gl.GL_SHADER_STORAGE_BUFFER 0 self._attribute-buffer
                gl.BindBuffer gl.GL_ELEMENT_ARRAY_BUFFER self._index-buffer
                let indexT =
                    static-match IndexFormat
                    case u16
                        gl.GL_UNSIGNED_SHORT
                    case u32
                        gl.GL_UNSIGNED_INT
                    default
                        assert false "invalid index format"

                gl.DrawElements gl.GL_TRIANGLES ((countof self.index-data) as i32)
                    \ indexT null

            inline... __typecall (cls, expected-attr-count : usize)
                let expected-index-count = ((expected-attr-count * 1.5) as usize) # estimate
                let attr-store-size = ((sizeof AttributeType) * expected-attr-count)
                let ibuffer-store-size = ((sizeof IndexFormat) * expected-index-count)

                let attr-handle =
                    GPUBuffer gl.GL_SHADER_STORAGE_BUFFER attr-store-size

                let ibuffer-handle =
                    GPUBuffer gl.GL_ELEMENT_ARRAY_BUFFER ibuffer-store-size

                local attr-array : (Array AttributeType)
                'reserve attr-array expected-attr-count
                local index-array : (Array IndexFormat)
                'reserve index-array expected-index-count

                super-type.__typecall cls
                    attribute-data = attr-array
                    _attribute-buffer = attr-handle
                    _attribute-buffer-size = attr-store-size
                    index-data = index-array
                    _index-buffer = ibuffer-handle
                    _index-buffer-size = ibuffer-store-size

            # default initialize to zero
            case (cls)
                super-type.__typecall cls

typedef GPUShaderProgram <:: u32
    fn compile-shader (source kind)
        imply kind i32
        source as:= rawstring

        let handle = (gl.CreateShader (kind as u32))
        gl.ShaderSource handle 1 (&local source) null
        gl.CompileShader handle

        local compilation-status : i32
        gl.GetShaderiv handle gl.GL_COMPILE_STATUS &compilation-status
        if (not compilation-status)
            local log-length : i32
            local message : (array i8 1024)
            gl.GetShaderInfoLog handle (sizeof message) &log-length &message
            io.log "Shader compilation error:\n"
            io.log (String &message (log-length as usize))
            io.log "\n"
        handle

    fn link-program (vs fs)
        let program = (gl.CreateProgram)
        gl.AttachShader program vs
        gl.AttachShader program fs
        gl.LinkProgram program
        # could make this less copy pastey by abstracting away error logging
        local link-status : i32
        gl.GetProgramiv program gl.GL_LINK_STATUS &link-status
        if (not link-status)
            local log-length : i32
            local message : (array i8 1024)
            gl.GetProgramInfoLog program (sizeof message) &log-length &message
            io.log "Shader program linking error:\n"
            io.log (String &message (log-length as usize))
            io.log "\n"
        # because we preemptively delete the shader stages, they are
            already marked for deletion when the program is dropped.
        gl.DeleteShader fs
        gl.DeleteShader vs
        program

    inline... __typecall (cls)
        bitcast 0 this-type
    case (cls handle)
        bitcast handle this-type
    case (cls vs fs)
        # TODO: move this out of here, maybe we need a variant that takes strings
        let vsource =
            patch-shader
                static-compile-glsl 420 'vertex (static-typify vs)
                "#extension GL_ARB_shader_storage_buffer_object : require\n"
        let vertex-module =
            compile-shader
                vsource as rawstring
                gl.GL_VERTEX_SHADER
        fsource := (static-compile-glsl 420 'fragment (static-typify fs)) as rawstring
        let fragment-module =
            compile-shader
                fsource
                gl.GL_FRAGMENT_SHADER

        let program = (link-program vertex-module fragment-module)
        bitcast program this-type

    inline __imply (selfT otherT)
        static-if (otherT == (storageof this-type))
            inline (self)
                storagecast (view self)

    inline __drop (self)
        gl.DeleteProgram (storagecast (view self))

global geometry-batch-shader : GPUShaderProgram 0
global world-transform : mat4

struct Vertex2D plain
    position : vec2
    color : vec4
    texcoords : vec3

struct GeometryBatch
    mesh : (Mesh Vertex2D u16)
    # image : (Rc ArrayTexture2D)
    _dirty? : bool

    # TODO: better way to handle this so we avoid segfaults?
    fn init (self)
        self.mesh = (typeinit 128)

    fn add-polyline (self points color)
        self._dirty? = true
        let vertex-offset = (countof self.mesh.attribute-data)
        for i in (range ((countof points) - 1))
            this-point := points @ i
            next-point := points @ (i + 1)

            dir := (normalize (next-point - this-point))
            perp := (math.rotate dir (pi / 2))

            inline make-vertex (pos)
                Vertex2D pos color
            'append self.mesh.attribute-data (make-vertex this-point)
            'append self.mesh.attribute-data (make-vertex (this-point + perp))
            'append self.mesh.attribute-data (make-vertex next-point)
            'append self.mesh.attribute-data (make-vertex (next-point + perp))

        for i in (range ((countof points) - 1))
            let segment-start = (vertex-offset + (i * 4))
            let left right left-e right-e =
                segment-start
                segment-start + 1
                segment-start + 2
                segment-start + 3
            'append self.mesh.index-data (left as u16)
            'append self.mesh.index-data (right as u16)
            'append self.mesh.index-data (right-e as u16)
            'append self.mesh.index-data (right-e as u16)
            'append self.mesh.index-data (left-e as u16)
            'append self.mesh.index-data (left as u16)

    # TODO: should be refactored as "add-polygon"
    fn add-rectangle (self position size color)
        self._dirty? = true
        let vertex-offset = (countof self.mesh.attribute-data)
        let attrs = self.mesh.attribute-data

        inline make-vertex (pos)
            Vertex2D pos color
        # 0 ---- 1
        # |      |
        # |      |
        # 2 ---- 3
        'append attrs (make-vertex position)
        'append attrs (make-vertex (position + size.x0))
        'append attrs (make-vertex (position + size.0y))
        'append attrs (make-vertex (position + size))

        let indices = self.mesh.index-data
        va-map
            inline (i)
                'append indices ((vertex-offset + i) as u16)
            _ 0 2 3 3 1 0


    fn clear (self)
        'clear self.mesh.attribute-data
        'clear self.mesh.index-data

    fn draw (self)
        gl.UseProgram geometry-batch-shader
        gl.UniformMatrix4fv
            gl.GetUniformLocation geometry-batch-shader "transform"
            1
            false
            (&local world-transform) as (pointer f32)
        if self._dirty?
            'update self.mesh
            self._dirty? = false
        # gl.BindTextures 0 1
        #     &local (storagecast (view self.image._handle))
        'draw self.mesh
        ;

fn geometry-vshader ()
    using import glsl
    buffer vertices :
        struct VertexArray plain
            data : (array Vertex2D)

    out vcolor : vec4
        location = 0
    out vtexcoords : vec3
        location = 1

    uniform transform : mat4

    attr := vertices.data @ gl_VertexID

    vcolor = attr.color
    vtexcoords = attr.texcoords
    gl_Position = transform * (vec4 attr.position 0 1)

fn geometry-fshader ()
    using import glsl
    in vcolor : vec4
        location = 0
    in vtexcoords : vec3
        location = 1
    out fcolor : vec4
        location = 0

    # uniform tex : sampler2DArray
    fcolor = vcolor

fn init-gl ()
    gl.init;

    enum OpenGLDebugLevel plain
        HIGH
        MEDIUM
        LOW
        NOTIFICATION

    # log-level is the lowest severity level we care about.
    inline make-openGL-debug-callback (log-level)
        let log-level = (log-level as i32)
        fn openGL-error-callback (source _type id severity _length message user-param)
            inline gl-debug-source (source)
                match source
                case gl.GL_DEBUG_SOURCE_API_ARB                ("API" as rawstring)
                case gl.GL_DEBUG_SOURCE_WINDOW_SYSTEM_ARB      ("Window System" as rawstring)
                case gl.GL_DEBUG_SOURCE_SHADER_COMPILER_ARB    ("Shader Compiler" as rawstring)
                case gl.GL_DEBUG_SOURCE_THIRD_PARTY_ARB        ("Third Party" as rawstring)
                case gl.GL_DEBUG_SOURCE_APPLICATION_ARB        ("Application" as rawstring)
                case gl.GL_DEBUG_SOURCE_OTHER_ARB              ("Other" as rawstring)
                default                                    ("?" as rawstring)

            inline gl-debug-type (type_)
                match type_
                case gl.GL_DEBUG_TYPE_ERROR_ARB                ("Error" as rawstring)
                case gl.GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR_ARB  ("Deprecated" as rawstring)
                case gl.GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR_ARB   ("Undefined Behavior" as rawstring)
                case gl.GL_DEBUG_TYPE_PORTABILITY_ARB          ("Portability" as rawstring)
                case gl.GL_DEBUG_TYPE_PERFORMANCE_ARB          ("Performance" as rawstring)
                case gl.GL_DEBUG_TYPE_OTHER_ARB                ("Other" as rawstring)
                default                                    ("?" as rawstring)

            inline gl-debug-severity (severity)
                match severity
                case gl.GL_DEBUG_SEVERITY_HIGH_ARB             ("High" as rawstring)
                case gl.GL_DEBUG_SEVERITY_MEDIUM_ARB           ("Medium" as rawstring)
                case gl.GL_DEBUG_SEVERITY_LOW_ARB              ("Low" as rawstring)
                # case gl.GL_DEBUG_SEVERITY_NOTIFICATION_ARB     ("Notification" as rawstring)
                default                                    ("?" as rawstring)

            using OpenGLDebugLevel
            match severity

            case gl.GL_DEBUG_SEVERITY_HIGH_ARB
            case gl.GL_DEBUG_SEVERITY_MEDIUM_ARB
                static-if (log-level < MEDIUM)
                    return;
            case gl.GL_DEBUG_SEVERITY_LOW_ARB
                static-if (log-level < LOW)
                    return;
            default
                ;

            io.log "%s %s %s %s %s %s %s %s\n"
                "source:" as rawstring
                (gl-debug-source source) as rawstring
                "| type:" as rawstring
                (gl-debug-type _type) as rawstring
                "| severity:" as rawstring
                (gl-debug-severity severity) as rawstring
                "| message:" as rawstring
                message as rawstring
            ;

    # gl.Enable gl.GL_DEBUG_OUTPUT
    gl.Enable gl.GL_BLEND
    gl.BlendFunc gl.GL_SRC_ALPHA gl.GL_ONE_MINUS_SRC_ALPHA
    # gl.Enable gl.GL_MULTISAMPLE
    gl.Enable gl.GL_FRAMEBUFFER_SRGB
    # TODO: add some colors to this
    gl.DebugMessageCallbackARB (make-openGL-debug-callback OpenGLDebugLevel.LOW) null
    local VAO : gl.GLuint
    gl.GenVertexArrays 1 &VAO
    gl.BindVertexArray VAO

global geometry-batch : GeometryBatch
fn init ()
    init-gl;
    geometry-batch-shader = (GPUShaderProgram geometry-vshader geometry-fshader)
    'init geometry-batch
    ;

fn new-frame ()
    gl.ClearColor 0.017 0.017 0.017 1.0
    gl.Clear (gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT)
    let wx wy = (window.size)
    world-transform =
        *
            math.scale (vec3 1 -1 1)
            math.translate (vec3 -1 -1 0)
            math.ortho wx wy
            # math.translate (floor -self.position.xy0)
    'clear geometry-batch

fn present ()
    'draw geometry-batch
    glfw.SwapBuffers window.main-window

fn rectangle (mode color position size)
    'add-rectangle geometry-batch position size color

do
    let init
        new-frame
        present

    vvv bind external
    do
        let
            rectangle
        locals;

    locals;

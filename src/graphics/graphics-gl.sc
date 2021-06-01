using import String
using import enum
using import struct
using import glm
using import Array

import ..window
import ..io
import ..math
import ..FFI.glad
import ..FFI.cute-spritebatch
import .common

let gl = FFI.glad
let spritebatch = FFI.cute-spritebatch

let &local =
    inline... (T : type, ...)
        &
            local T
                ...
    case (source)
        &
            local dummy-name = source

typedef ShaderProgram <:: u32
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
            io.log "Shader compilation error:"
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

    case (cls, vsource : String, fsource : String)
        let vertex-module =
            compile-shader
                vsource as rawstring
                gl.GL_VERTEX_SHADER
        let fragment-module =
            compile-shader
                fsource
                gl.GL_FRAGMENT_SHADER
        let program = (link-program vertex-module fragment-module)
        bitcast program this-type

    case (cls vs fs)
        let vsource =
            static-if ((typeof vs) == Closure)
                static-if (constant? vs)
                    let src = (static-compile-glsl 420 'vertex (static-typify vs))
                    String src (countof src)
                else
                    let src = (compile-glsl 420 'vertex (typify vs))
                    String src (countof src)
            else
                imply vs String
        let fsource =
            static-if ((typeof fs) == Closure)
                static-if (constant? fs)
                    let src = (static-compile-glsl 420 'fragment (static-typify fs))
                    String src (countof src)
                else
                    let src = (compile-glsl 420 'fragment (typify fs))
                    String src (countof src)
            else
                imply fs String
        this-function cls vsource fsource

    inline __imply (selfT otherT)
        static-if (otherT == (storageof this-type))
            inline (self)
                storagecast (view self)

    inline __drop (self)
        gl.DeleteProgram (storagecast (view self))

# note we receive texcoords in pixels, not normalized.
struct Vertex2D plain
    position  : vec2
    texcoords : ivec2
    color     : vec4

enum VertexAttributes plain
    Position
    TextureCoordinates
    Color

vvv bind default-vs default-fs
do
    using import glsl
    using VertexAttributes
    fn vertex ()
        uniform transform : mat4

        in aposition : vec2 (location = Position)
        in atexcoords : ivec2 (location = TextureCoordinates)
        in acolor : vec4 (location = Color)

        out vtexcoords : vec2 (location = TextureCoordinates)
        out vcolor : vec4 (location = Color)

        gl_Position = transform * (vec4 aposition 0 1)
        vtexcoords = (vec2 atexcoords)
        vcolor = acolor

    fn fragment ()
        uniform sprite : sampler2D
        in vtexcoords : vec2 (location = TextureCoordinates)
        in vcolor : vec4 (location = Color)

        out fcolor : vec4 (location = 0)

        fcolor = (texelFetch sprite (ivec2 vtexcoords) 0)

    _ vertex fragment

# TODO:  lot of this will be refactored out. As I improve the sprite and graphics primitives functionality,
# a more advanced, self resizing vertex buffer will be used in the form of an SSBO. For now a dumb handle
# will do.
global sprite-vbo : u32
global sprite-ibuf : u32
global default-shader : ShaderProgram
global transform-loc : i32

global batch : spritebatch.spritebatch

fn sprite (sprite position ...)
    let quad = (va-option quad ... (ivec4 0 0 sprite.size))
    let scale = (va-option scale ... (vec2 1 1))
    let rotation = (va-option rotation ... 0.0)

    # verify types if not default values, cast if possible for less friction
    position as:= vec2
    quad     as:= ivec4
    scale    as:= vec2
    rotation as:= f32

    spritebatch.push &batch
        spritebatch.sprite
            image_id = sprite.id
            w = sprite.size.x
            h = sprite.size.y
            x = position.x
            y = position.y
            sx = scale.x
            sy = scale.y
            c = (cos rotation)
            s = (sin rotation)

fn make-texture (pixels w h userdata)
    local handle : u32
    gl.GenTextures 1 &handle
    gl.BindTexture gl.GL_TEXTURE_2D handle

    vvv bind mipmap-count
    do
        let dimension = (max (w as f32) (h as f32))
        let levels = ((log2 dimension) + 1)
        levels as i32

    gl.TexStorage2D gl.GL_TEXTURE_2D mipmap-count gl.GL_SRGB8_ALPHA8 w h
    gl.TexSubImage2D gl.GL_TEXTURE_2D 0 0 0 w h gl.GL_RGBA gl.GL_UNSIGNED_BYTE pixels
    gl.GenerateMipmap gl.GL_TEXTURE_2D
    gl.TexParameteri gl.GL_TEXTURE_2D gl.GL_TEXTURE_WRAP_S gl.GL_REPEAT
    gl.TexParameteri gl.GL_TEXTURE_2D gl.GL_TEXTURE_WRAP_T gl.GL_REPEAT
    gl.TexParameteri gl.GL_TEXTURE_2D gl.GL_TEXTURE_MAG_FILTER gl.GL_NEAREST
    gl.TexParameteri gl.GL_TEXTURE_2D gl.GL_TEXTURE_MIN_FILTER gl.GL_LINEAR_MIPMAP_LINEAR

    handle as u64

fn destroy-texture (handle userdata)
    gl.DeleteTextures 1 (&local (handle as u32))

fn get-sprite-pixels (id buf expected-bytes userdata)
    buf as:= (mutable@ u8)
    let sdata = (common.sprites @ id) 
    assert ((sdata.size.x * sdata.size.y * 4) == expected-bytes)
    # TODO: memcpy this
    for i byte in (enumerate sdata.data)
        buf @ i = byte 

fn batch-submit (sprites count texturew textureh userdata)
    texturew as:= f32
    textureh as:= f32
    for i in (range count)
        sprite := sprites @ i
        let quad =
            ivec4 
                (texturew * sprite.minx) as i32
                (textureh * sprite.miny) as i32
                (texturew * sprite.maxx) as i32
                (textureh * sprite.maxy) as i32
        scale := (vec2 sprite.sx sprite.sy)
        size := (vec2 (quad.pq - quad.st)) * scale
        position := (vec2 sprite.x sprite.y)

        local vdata =
            arrayof Vertex2D
                typeinit position quad.sq # (vec2 0 1)
                typeinit (position + size.x0) quad.pq # (vec2 1 1)
                typeinit (position + size.0y) quad.st # (vec2 0 0)
                typeinit (position + size) quad.pt # (vec2 1 0)

        gl.BindTexture gl.GL_TEXTURE_2D (sprite.texture_id as u32)
        gl.BufferData gl.GL_ARRAY_BUFFER (sizeof vdata) &vdata gl.GL_STREAM_DRAW
        gl.DrawElements gl.GL_TRIANGLES 6 gl.GL_UNSIGNED_INT null

fn init ()
    gl.LoadGL;
    gl.Enable gl.GL_BLEND
    gl.BlendFunc gl.GL_SRC_ALPHA gl.GL_ONE_MINUS_SRC_ALPHA
    gl.Enable gl.GL_MULTISAMPLE
    gl.Enable gl.GL_FRAMEBUFFER_SRGB

    # default, single VAO
    local VAO : u32
    gl.GenVertexArrays 1 &VAO
    gl.BindVertexArray VAO

    # init our sprite buffer
    local vdata =
        arrayof Vertex2D
            typeinit (vec2 -0.5 -0.5) (ivec2 0 1)
            typeinit (vec2  0.5 -0.5) (ivec2 1 1)
            typeinit (vec2 -0.5  0.5) (ivec2 0 0)
            typeinit (vec2  0.5  0.5) (ivec2 1 0)
    gl.GenBuffers 1 &sprite-vbo
    gl.BindBuffer gl.GL_ARRAY_BUFFER sprite-vbo
    gl.BufferData gl.GL_ARRAY_BUFFER (sizeof vdata) &vdata gl.GL_STREAM_DRAW

    gl.EnableVertexAttribArray VertexAttributes.Position
    gl.EnableVertexAttribArray VertexAttributes.TextureCoordinates
    gl.EnableVertexAttribArray VertexAttributes.Color

    inline attribptr (name)
        inttoptr (offsetof Vertex2D name) voidstar

    gl.VertexAttribPointer
        \ VertexAttributes.Position 2 gl.GL_FLOAT false (sizeof Vertex2D) (attribptr 'position)
    gl.VertexAttribPointer
        \ VertexAttributes.TextureCoordinates 2 gl.GL_FLOAT false (sizeof Vertex2D) (attribptr 'texcoords)
    gl.VertexAttribPointer
        \ VertexAttributes.Color 4 gl.GL_FLOAT false (sizeof Vertex2D) (attribptr 'color)

    # 2 -- 3
    # | /  |
    # 0 -- 1
    local sprite-indices =
        arrayof u32 0 1 2 2 1 3

    gl.GenBuffers 1 &sprite-ibuf
    gl.BindBuffer gl.GL_ELEMENT_ARRAY_BUFFER sprite-ibuf
    gl.BufferData gl.GL_ELEMENT_ARRAY_BUFFER (sizeof sprite-indices) &sprite-indices gl.GL_STATIC_DRAW

    default-shader = (ShaderProgram default-vs default-fs)
    gl.UseProgram default-shader

    transform-loc = (gl.GetUniformLocation default-shader "transform")

    assert
        not
            spritebatch.init &batch 
                &local spritebatch.config
                    pixel_stride = ((sizeof u8) * 4)  
                    atlas_width_in_pixels = 4096
                    atlas_height_in_pixels = 4096
                    atlas_use_border_pixels = false # what is this?
                    ticks_to_decay_texture = 1800
                    lonely_buffer_count_till_flush = 0
                    ratio_to_decay_atlas = 0
                    ratio_to_merge_atlases = 0.25
                    batch_callback = batch-submit
                    get_pixels_callback = get-sprite-pixels
                    generate_texture_callback = make-texture
                    delete_texture_callback = destroy-texture
                null # userdata

# NOTE: maybe we don't need this once our drawing is less immediate, then we can have present clear and
# submit drawing stuff.
fn begin-frame ()
    let ww wh = (window.size)
    gl.Viewport 0 0 ww wh
    gl.ClearColor 0.017 0.017 0.017 1.0
    gl.Clear (gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT)

    local transform =
        *
            math.translate (vec3 -1 -1 0)
            math.ortho ww wh
    gl.UniformMatrix4fv transform-loc 1 false (&transform as (mutable@ f32))

fn present ()
    spritebatch.tick &batch
    # spritebatch.defrag &batch
    spritebatch.flush &batch
    window.gl-swap-buffers;

do
    let
        init
        begin-frame
        present
        sprite

    let Sprite = common.Sprite
    locals;

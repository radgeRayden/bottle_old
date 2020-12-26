using import enum

import .window
import .io
let glfw = (import .FFI.glfw)
let gl = (import .FFI.glad)

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

fn init ()
    init-gl;
    ;

fn present ()
    gl.ClearColor 0.017 0.017 0.017 1.0
    gl.Clear (gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT)
    glfw.SwapBuffers window.main-window

do
    let init

    vvv bind external
    do
        let present
        locals;

    locals;

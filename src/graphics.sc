import .window
let glfw = (import .FFI.glfw)
let gl = (import .FFI.glad)

fn present ()
    gl.ClearColor 0.017 0.017 0.017 1.0
    gl.Clear (gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT)
    glfw.SwapBuffers window.main-window

do
    vvv bind external
    do
        let present
        locals;
    locals;

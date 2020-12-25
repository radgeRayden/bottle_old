let argc argv = (launch-args)
assert (argc > 2)
let path = (argv @ 2)

using import FunctionChain

vvv bind bottle
do
    fnchain load
    fnchain update
    fnchain draw
    locals;

let forbidden-symbols = '()
let scope =
    fold (scope = (globals)) for sym in forbidden-symbols
        sym as:= Symbol
        'unbind scope sym

let additional-symbols =
    do
        let bottle
        locals;

let game =
    load-module "" (string path)
        main-module? = true
        scope = (scope .. additional-symbols)

load-library "libglfw.so"
load-library "./build/libgame.so"
run-stage;
let glfw = (import .src.FFI.glfw)
let gl = (import .src.FFI.glad)

glfw.Init;
glfw.WindowHint glfw.GLFW_RESIZABLE false
glfw.WindowHint glfw.GLFW_CLIENT_API glfw.GLFW_OPENGL_API
glfw.WindowHint glfw.GLFW_DOUBLEBUFFER true
glfw.WindowHint glfw.GLFW_OPENGL_FORWARD_COMPAT true
glfw.WindowHint glfw.GLFW_CONTEXT_VERSION_MAJOR 4
glfw.WindowHint glfw.GLFW_CONTEXT_VERSION_MINOR 2
glfw.WindowHint glfw.GLFW_OPENGL_DEBUG_CONTEXT true
glfw.WindowHint glfw.GLFW_OPENGL_PROFILE glfw.GLFW_OPENGL_CORE_PROFILE

let main-window = (glfw.CreateWindow 1280 720 "untitled" null null)
glfw.MakeContextCurrent main-window

gl.init;
gl.Enable gl.GL_FRAMEBUFFER_SRGB

bottle.load;

while (not (glfw.WindowShouldClose main-window))
    glfw.PollEvents;
    bottle.update 0
    bottle.draw;

    gl.ClearColor 0.017 0.017 0.017 1.0
    gl.Clear (gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT)
    glfw.SwapBuffers main-window

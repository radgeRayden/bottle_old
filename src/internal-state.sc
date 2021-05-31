using import struct
using import String
using import ..common

let glfw = (import .FFI.glfw)

struct BottleConfig plain
    window : 
        struct WindowOptions plain
            width       : i32 = 1280
            height      : i32 = 720
            x           : i32 = 100
            y           : i32 = 100
            title       : rawstring = "game in a bottle"
            fullscreen? : bool = false
            visible?    : bool = true
            maximized?  : bool = false
            vsync?      : bool = true
            resizable?  : bool = true
    graphics :
        struct GraphicsOptions plain
            backend : GraphicsBackend = GraphicsBackend.OpenGL
    modules :
        struct EnabledModules plain
            graphics : bool = true

global window : (mutable@ glfw.window)
global config : BottleConfig

do
    let window config
    locals;

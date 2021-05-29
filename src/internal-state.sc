using import struct
using import String
using import ..common

let glfw = (import .FFI.glfw)

struct BottleConfig
    window : 
        struct WindowOptions 
            width       : i32 = 1280
            height      : i32 = 720
            x           : i32 = 100
            y           : i32 = 100
            title       : String = "game in a bottle"
            fullscreen? : bool = false
            visible?    : bool = true
            maximized?  : bool = false
            vsync?      : bool = true
            resizable?  : bool = true
    graphics :
        struct GraphicsOptions
            backend : GraphicsBackend = GraphicsBackend.OpenGL
    modules :
        struct EnabledModules
            graphics : bool = true

global window : (mutable@ glfw.window)
global config : BottleConfig

do
    let window config
    locals;

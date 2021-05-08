using import struct
using import String

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

global window : (mutable@ glfw.window)
global config : BottleConfig

do
    let window config
    locals;

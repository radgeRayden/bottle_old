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

# because we want to decide certain things at "compile time", we will detect the presence of a 
  config file in the working directory. This file is supposed to return a function that takes a config struct
  and modifies it.
let config-fn = 
    try 
        (require-from "." ".config")
    else
        spice-quote
            fn (c)
                ;
run-stage;
local startup-config : BottleConfig
config-fn startup-config

global window : (mutable@ glfw.window)
global config : BottleConfig

do
    let window config startup-config 
    locals;

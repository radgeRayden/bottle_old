using import struct
let glfw = (import .FFI.glfw)
import .internal-state

from internal-state let window

struct MouseState plain
    Left : bool
    Right : bool
    Middle : bool

struct MouseButtonTimeInfo plain
    modified : f64    
    repeat-triggered : f64

struct MouseTimestamp plain
    Left : MouseButtonTimeInfo
    Right : MouseButtonTimeInfo
    Middle : MouseButtonTimeInfo

global previous-state : MouseState
global current-state : MouseState
global last-modified : MouseTimestamp

fn button-down? (code)
    (glfw.GetMouseButton window code) as bool

fn update ()
    previous-state = current-state
    current-state =
        MouseState
            Left = (button-down? glfw.GLFW_MOUSE_BUTTON_LEFT)
            Right = (button-down? glfw.GLFW_MOUSE_BUTTON_RIGHT)
            Middle = (button-down? glfw.GLFW_MOUSE_BUTTON_MIDDLE)

    # copied from input.sc
    # if any button has a different state now, we update the timestamp
    va-map 
        inline (field)
            let _key = (keyof field.Type) 
            let current previous =
                getattr current-state _key
                getattr previous-state _key
            if (current != previous)
                let button = (getattr last-modified _key)
                button.modified = (glfw.GetTime)
                button.repeat-triggered = 0.0
        MouseState.__fields__

inline down? (button)
    deref (getattr current-state button)

inline pressed? (button)
    (deref (getattr current-state button)) and (not (getattr previous-state button))

inline released? (button)
    (not (getattr current-state button)) and (deref (getattr previous-state button))

inline... holding? (button, delay : real = 1.0, rate : real = 0.25)
    if (down? button)
        let t = (glfw.GetTime)
        let button-info = (getattr last-modified button)
        delta := t - button-info.modified 
        repeat-delta := t - button-info.repeat-triggered

        holding? := delta > delay
        fire-repeat? := holding? and (repeat-delta > rate)
        if fire-repeat?
            button-info.repeat-triggered = t

        _ holding? fire-repeat?        
    else
        _ false false

do
    let
        update
        down?
        pressed?
        released?
        holding?
    locals;

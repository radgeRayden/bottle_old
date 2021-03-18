using import struct
using import Map
using import Option

let glfw = (import .FFI.glfw)
import .io
import .internal-state

from internal-state let window

struct InputState plain
    A : bool
    B : bool
    Left : bool
    Right : bool
    Up : bool
    Down : bool

struct ButtonTimeInfo plain
    modified : f64    
    repeat-triggered : f64

struct InputTimestamp plain
    A : ButtonTimeInfo
    B : ButtonTimeInfo
    Left : ButtonTimeInfo
    Right : ButtonTimeInfo
    Up : ButtonTimeInfo
    Down : ButtonTimeInfo

fn key-down? (code)
    (glfw.GetKey window code) as bool

global previous-state : InputState
global current-state : InputState
global last-modified : InputTimestamp
global gamepad : (Option i32)

fn init ()
    for i in (range glfw.GLFW_JOYSTICK_LAST)
        if (glfw.JoystickIsGamepad i)
            io.log "%s was enabled as primary controller\n" (glfw.GetGamepadName i)
            gamepad = i
            break;

    glfw.SetJoystickCallback
        fn (jid event)
            # if we have no gamepad assigned and a joystick is connected,
            # we try to assign it in place.
            if (event == glfw.GLFW_CONNECTED)
                if ((not gamepad) and ((glfw.JoystickIsGamepad jid) as bool))
                    gamepad = jid
                    io.log "%s was enabled as primary controller\n" (glfw.GetGamepadName jid)

            # however, if our assigned gamepad is disconnected, we look for
            # connected gamepads and try to switch to them before disabling
            # gamepad input.
            if ((event == glfw.GLFW_DISCONNECTED) and (jid == gamepad))
                io.log "Primary controller was disconnected\n"
                gamepad = none
                for i in (range glfw.GLFW_JOYSTICK_LAST)
                    if (glfw.JoystickIsGamepad i)
                        io.log "%s was enabled as primary controller\n" (glfw.GetGamepadName i)
                        gamepad = i
                        break;

    # TODO: load bindings from config file
    ;

fn update ()
    local gamepad-state : glfw.gamepadstate
    let gamepad =
        try (deref ('unwrap gamepad))
        else 0
    glfw.GetGamepadState gamepad &gamepad-state
    inline button-down? (code)
        (gamepad-state.buttons @ code) == glfw.GLFW_PRESS

    previous-state = current-state
    current-state =
        # TODO: make bindings replaceable
        InputState
            A =
                (key-down? glfw.GLFW_KEY_Z) or (button-down? glfw.GLFW_GAMEPAD_BUTTON_A)
            B =
                (key-down? glfw.GLFW_KEY_X) or (button-down? glfw.GLFW_GAMEPAD_BUTTON_B)
            Left =
                (key-down? glfw.GLFW_KEY_LEFT) or (button-down? glfw.GLFW_GAMEPAD_BUTTON_DPAD_LEFT)
            Right =
                (key-down? glfw.GLFW_KEY_RIGHT) or (button-down? glfw.GLFW_GAMEPAD_BUTTON_DPAD_RIGHT)
            Up =
                (key-down? glfw.GLFW_KEY_UP) or (button-down? glfw.GLFW_GAMEPAD_BUTTON_DPAD_UP)
            Down =
                (key-down? glfw.GLFW_KEY_DOWN) or (button-down? glfw.GLFW_GAMEPAD_BUTTON_DPAD_DOWN)

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
        InputState.__fields__
        
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
        init
        update
        down?
        pressed?
        released?
        holding?
    locals;

# Creates and manages the game window, interfacing with GLFW. Bottle is built to only ever use a single
# window, whose handle is kept as global state. While input is also managed by GLFW, it's handled separately
# by the input module.

using import Option
using import String
using import struct

using import ..common
import .internal-state
let glfw = (import .FFI.glfw)
let wgpu = (import .FFI.wgpu)

from internal-state let window config

# This helper queries internal window handles used by the OS (as opposed to the GLFW window handle).
# These are used when initializing certain graphics APIs that own the window surface.
fn get-native-window-info ()
    """"Returns information necessary to initialize a window surface (webgpu, vulkan).
        On Linux, returns (:_ X11Display X11Window);
        On Windows, returns (:_ ModuleHandle HWND)
    static-match operating-system
    case 'linux
        let GetX11Display =
            extern 'glfwGetX11Display (function (mutable@ voidstar))
        let GetX11Window =
            extern 'glfwGetX11Window (function u64 (mutable@ glfw.window))
        _ (GetX11Display) (GetX11Window window)
    case 'windows
        let GetModuleHandleA =
            extern 'GetModuleHandleA (function voidstar voidstar)
        let GetWin32Window =
            extern 'glfwGetWin32Window (function voidstar (mutable@ glfw.window))
        _ (GetModuleHandleA null) (GetWin32Window window)
    default
        error "OS not supported"

# Must be called to generate a WebGPU compatible surface to be handed off to the graphics module if
# using that backend.
fn create-wgpu-surface ()
    static-match operating-system
    case 'linux
        let x11-display x11-window = (get-native-window-info)
        wgpu.create_surface_from_xlib x11-display x11-window
    case 'windows
        let hinstance hwnd = (get-native-window-info)
        wgpu.create_surface_from_windows_hwnd hinstance hwnd
    default
        error "OS not supported"

fn position ()
    local x : i32
    local y : i32
    glfw.GetWindowPos window &x &y
    _ x y

fn set-position (x y)
    glfw.SetWindowPos window x y
    let x y = (position)
    config.window.x = x
    config.window.y = y

fn size ()
    local width : i32
    local height : i32
    glfw.GetWindowSize window &width &height
    _ width height

fn set-size (width height)
    glfw.SetWindowSize window width height

    # look up what's the actual size we end up with
    let width height = (size)
    config.window.width = width
    config.window.height = height

fn set-resizable (value)
    glfw.SetWindowAttrib window glfw.GLFW_RESIZABLE value
    ;

fn set-fullscreen (value)
    let opt = config.window
    monitor := (glfw.GetPrimaryMonitor)
    if value
        let x y = (position); opt.x = x; opt.y = y
        opt.fullscreen? = true
        video-mode := (glfw.GetVideoMode monitor)
        glfw.SetWindowMonitor window monitor 0 0 video-mode.width video-mode.height (glfw.GLFW_DONT_CARE as i32)
    else
        opt.fullscreen? = false
        glfw.SetWindowMonitor window null opt.x opt.y opt.width opt.height (glfw.GLFW_DONT_CARE as i32)
    ;

fn toggle-fullscreen ()
    set-fullscreen (not config.window.fullscreen?)

fn set-title (title)
    glfw.SetWindowTitle window title

fn poll-events ()
    glfw.PollEvents;

fn closed? ()
    glfw.WindowShouldClose window

fn gl-swap-buffers ()
    glfw.SwapBuffers window

fn init ()
    glfw.SetErrorCallback
        fn "glfw-raise-error" (code message)
            # TODO: when io module is available, print error message here.
            # handle possible errors gracefully if possible or quit
            print (string message)
            assert false

    glfw.Init;

    glfw.WindowHint glfw.GLFW_RESIZABLE config.window.resizable?

    switch config.graphics.backend
    case GraphicsBackend.WebGPU
        glfw.WindowHint glfw.GLFW_CLIENT_API glfw.GLFW_NO_API
    case GraphicsBackend.OpenGL
        glfw.WindowHint glfw.GLFW_CLIENT_API glfw.GLFW_OPENGL_API
        glfw.WindowHint glfw.GLFW_DOUBLEBUFFER true
        glfw.WindowHint glfw.GLFW_OPENGL_FORWARD_COMPAT true

        glfw.WindowHint glfw.GLFW_CONTEXT_VERSION_MAJOR 4
        glfw.WindowHint glfw.GLFW_CONTEXT_VERSION_MINOR 2
        glfw.WindowHint glfw.GLFW_OPENGL_DEBUG_CONTEXT true
        glfw.WindowHint glfw.GLFW_OPENGL_PROFILE glfw.GLFW_OPENGL_CORE_PROFILE

        glfw.WindowHint glfw.GLFW_SAMPLES 4
    default
        ;

    window = (glfw.CreateWindow config.window.width config.window.height config.window.title null null)
    if (window == null)
        # TODO: proper error handling
        assert false

    set-fullscreen config.window.fullscreen?

    if (config.graphics.backend == GraphicsBackend.OpenGL)
        glfw.MakeContextCurrent window
        glfw.SwapInterval 1

    # set universal shortcuts
    glfw.SetKeyCallback window
        fn (window key scancode action mods)
            # alt+enter
            if (((mods & glfw.GLFW_MOD_ALT) != 0) and (key == glfw.GLFW_KEY_ENTER) and (action == glfw.GLFW_PRESS))
                toggle-fullscreen; 

do
    let create-wgpu-surface 
        gl-swap-buffers 
        get-native-window-info

    let init 
        poll-events 
        closed? 

        size 
        set-size
        position
        set-position
        set-resizable
        set-fullscreen
        toggle-fullscreen
        set-title
    locals;

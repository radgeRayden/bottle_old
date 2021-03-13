# Creates and manages the game window, interfacing with GLFW. Bottle is built to only ever use a single
# window, whose handle is kept as global state. While input is also managed by GLFW, it's handled separately
# by the input module.

using import Option
import .config
import .internal-state
let glfw = (import .FFI.glfw)
let wgpu = (import .FFI.wgpu)

from internal-state let window

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

fn size ()
    local width : i32
    local height : i32
    glfw.GetWindowSize window &width &height
    _ width height

fn set-size (width height)
    glfw.SetWindowSize window width height

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

    static-match config.GRAPHICS_BACKEND
    case 'webgpu
        glfw.WindowHint glfw.GLFW_CLIENT_API glfw.GLFW_NO_API
        glfw.WindowHint glfw.GLFW_RESIZABLE false
    case 'opengl
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

    window = (glfw.CreateWindow 720 480 "bottle" null null)
    if (window == null)
        # TODO: proper error handling
        assert false

    if (config.GRAPHICS_BACKEND == 'opengl)
        glfw.MakeContextCurrent window

do
    let init create-wgpu-surface size set-size \
        poll-events closed? gl-swap-buffers 
    locals;

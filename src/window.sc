let glfw = (import .FFI.glfw)
global main-window : (mutable@ glfw.window)

fn init ()
    glfw.Init;
    glfw.WindowHint glfw.GLFW_RESIZABLE false
    glfw.WindowHint glfw.GLFW_CLIENT_API glfw.GLFW_OPENGL_API
    glfw.WindowHint glfw.GLFW_DOUBLEBUFFER true
    glfw.WindowHint glfw.GLFW_OPENGL_FORWARD_COMPAT true
    glfw.WindowHint glfw.GLFW_CONTEXT_VERSION_MAJOR 4
    glfw.WindowHint glfw.GLFW_CONTEXT_VERSION_MINOR 2
    glfw.WindowHint glfw.GLFW_OPENGL_DEBUG_CONTEXT true
    glfw.WindowHint glfw.GLFW_OPENGL_PROFILE glfw.GLFW_OPENGL_CORE_PROFILE

    main-window = (glfw.CreateWindow 1280 720 "untitled" null null)
    glfw.MakeContextCurrent main-window

inline closed? ()
    (glfw.WindowShouldClose main-window) as bool

inline poll-events ()
    glfw.PollEvents;

fn size ()
    local width : i32
    local height : i32
    glfw.GetWindowSize main-window &width &height
    _ (deref width) (deref height)

do
    let main-window
    let
        init
        size

    vvv bind external
    do
        let
            closed?
            poll-events
            size
        locals;

    locals;

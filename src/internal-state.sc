let glfw = (import .FFI.glfw)

do
    global window : (mutable@ glfw.window)
    locals;
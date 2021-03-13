let glfw = (import .FFI.glfw)

global last-update : f64

fn delta-time ()
    let current-time = (glfw.GetTime)
    let dt = (current-time - last-update)
    last-update = current-time
    dt

do
    let delta-time
    locals;

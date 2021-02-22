import .runtime
import .callbacks

let glfw = (import .FFI.glfw)
import .window
import .graphics

fn run ()
    window.init;
    graphics.init;

    callbacks.config none
    callbacks.load;

    while (not (glfw.WindowShouldClose window.window))
        glfw.PollEvents;
        callbacks.update;
        callbacks.draw;
        graphics.present;

do

    let run
    locals;

import .runtime
import .callbacks

import .window
import .graphics
import .input
import .time

fn run ()
    window.init;
    graphics.init;
    input.init;

    callbacks.config none
    callbacks.load;

    while (not (window.closed?))
        window.poll-events;
        input.update;
        callbacks.update (time.delta-time)
        graphics.begin-frame;
        callbacks.draw;
        graphics.present;

do

    let run
    locals;

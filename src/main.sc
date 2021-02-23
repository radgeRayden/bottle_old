import .runtime
import .callbacks

import .window
import .graphics

fn run ()
    window.init;
    graphics.init;

    callbacks.config none
    callbacks.load;

    while (not (window.closed?))
        window.poll-events;
        callbacks.update;
        callbacks.draw;
        graphics.present;

do

    let run
    locals;

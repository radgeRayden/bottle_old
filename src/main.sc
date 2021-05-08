import .runtime
import .callbacks

import .window
import .graphics
import .input
import .mouse
import .time

import .internal-state

fn run ()
    callbacks.config internal-state.config

    window.init;
    graphics.init;
    input.init;

    callbacks.load;

    while (not (window.closed?))
        window.poll-events;
        input.update;
        mouse.update;
        callbacks.update (time.delta-time)
        graphics.begin-frame;
        callbacks.draw;
        graphics.present;

do

    let run
    locals;

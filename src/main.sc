import .runtime
import .callbacks

import .window
import .graphics
import .input
import .mouse
import .time

import .internal-state

fn run ()
    let config = internal-state.config

    callbacks.config config

    window.init;

    # currently only the graphics module is optional
    if config.modules.graphics
        graphics.init;

    input.init;

    callbacks.load;

    while (not (window.closed?))
        window.poll-events;
        input.update;
        mouse.update;
        callbacks.update (time.delta-time)

        if config.modules.graphics
            graphics.begin-frame;

        callbacks.draw;

        if config.modules.graphics
            graphics.present;

do

    let run
    locals;

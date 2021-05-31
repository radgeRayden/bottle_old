import .runtime
import .callbacks

import .window
import .graphics
import .input
import .mouse
import .time

import .internal-state
run-stage;

fn run ()
    internal-state.config = internal-state.startup-config
    let config = internal-state.config

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

    callbacks.quit;

do

    let run
    locals;

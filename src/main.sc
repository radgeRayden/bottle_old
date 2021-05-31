import .runtime
import .callbacks

import .window
import .graphics
import .input
import .mouse
import .time

# because we want to decide certain things at "compile time", we will detect the presence of a 
  config file in the working directory. This file is supposed to return a function that takes a config struct
  and modifies it.
let config-fn = 
    try 
        (require-from "." ".config")
    else
        spice-quote
            fn (c)
                ;
run-stage;

import .internal-state
local config : (typeof internal-state.config)
config-fn config
run-stage;

fn run ()
    print (constant? config)
    internal-state.config = config
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

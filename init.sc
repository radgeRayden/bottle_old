let main = (import .src.main)
let graphics = (import .src.graphics)
let input = (import .src.input)
let window = (import .src.window)

vvv bind bottle
do
    using import .src.callbacks
    let run = main.run

    let graphics
        input
        window
    locals;

sugar-if main-module?
    print true
else
    bottle

let main = (import .src.main)
let graphics = (import .src.graphics)
let input = (import .src.input)

vvv bind bottle
do
    using import .src.callbacks
    let run = main.run

    let graphics
        input
    locals;

sugar-if main-module?
    print true
else
    bottle
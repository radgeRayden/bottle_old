let main = (import .src.main)
let graphics = (import .src.graphics)
let input = (import .src.input)
let window = (import .src.window)
let io = (import .src.io)
let mouse = (import .src.mouse)

vvv bind bottle
do
    using import .src.callbacks
    let run = main.run

    let graphics
        input
        mouse
        window
        io
    locals;

sugar-if main-module?
    print true
else
    bottle

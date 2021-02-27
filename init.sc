let main = (import .src.main)
let graphics = (import .src.graphics)

vvv bind bottle
do
    using import .src.callbacks
    let run = main.run
    let graphics
    locals;

sugar-if main-module?
    print true
else
    bottle
let main = (import .src.main)

vvv bind bottle
do
    using import .src.callbacks
    let run = main.run
    locals;

sugar-if main-module?
    print true
else
    bottle
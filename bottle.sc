let argc argv = (launch-args)
assert (argc > 2)
let path = (argv @ 2)

using import FunctionChain

let graphics = (import .src.graphics)
let window = (import .src.window)

vvv bind bottle
do
    fnchain load
    fnchain update
    fnchain draw

    let graphics = graphics.external
    let window = window.external
    locals;

let forbidden-symbols = '()
let scope =
    fold (scope = (globals)) for sym in forbidden-symbols
        sym as:= Symbol
        'unbind scope sym

let additional-symbols =
    do
        let bottle
        locals;

let game =
    load-module "" (string path)
        main-module? = true
        scope = (scope .. additional-symbols)

load-library "libglfw.so"
load-library "./build/libgame.so"
run-stage;

window.init;
graphics.init;

bottle.load;

while (not (bottle.window.closed?))
    bottle.window.poll-events;
    bottle.update 0
    bottle.graphics.new-frame;
    bottle.draw;
    bottle.graphics.present;

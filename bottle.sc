let argc argv = (launch-args)
assert (argc > 2)
let path = (argv @ 2)

using import FunctionChain

vvv bind bottle
do
    fnchain load
    fnchain update
    fnchain draw
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

run-stage;

bottle.load;

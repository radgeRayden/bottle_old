# import C functions and sanitize the scope
vvv bind glad
do
    let header =
        include
            options
                .. "-I" module-dir "/../../3rd-party/glad/include"
            "glad/glad.h"
    using header.extern
    using header.typedef
    using header.define filter "^(GL_|gl[A-Z])"
    locals;

let gl =
    fold (scope = glad) for k v in glad
        let key-name = (k as Symbol as string)
        let match? start end = ('match? "^gl(?=[A-Z])" key-name)
        if match?
            let new-name = (rslice key-name end)
            'bind scope (Symbol new-name) v
        else
            scope

run-stage;

fn init ()
    let status = (glad.gladLoadGL)
    if (status == 0)
        let puts = (extern 'puts (function i32 rawstring))
        puts "failed to initialize opengl"
        assert false

do
    using gl
    let init
    locals;

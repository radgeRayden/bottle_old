let header =
    include
        # options "-I./include"
        "GLFW/glfw3.h"

inline filter-scope (scope pattern)
    fold (scope = (Scope)) for k v in scope
        let name = (k as Symbol as string)
        let match? start end = ('match? pattern name)
        if match?
            'bind scope (Symbol (rslice name end)) v
        else
            scope

let glfw-extern = (filter-scope header.extern "^glfw")
let glfw-typedef = (filter-scope header.typedef "^GLFW")
let glfw-define = (filter-scope header.define "^(?=GLFW_)")

.. glfw-extern glfw-typedef glfw-define
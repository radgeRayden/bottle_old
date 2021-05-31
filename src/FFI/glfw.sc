using import .ffi-helpers

let header =
    include
        "GLFW/glfw3.h"
        options 
            .. "-I" module-dir "/cdeps/glfw/include"

let glfw-extern = (filter-scope header.extern "^glfw")
let glfw-typedef = (filter-scope header.typedef "^GLFW")
let glfw-define = (filter-scope header.define "^(?=GLFW_)")

.. glfw-extern glfw-typedef glfw-define

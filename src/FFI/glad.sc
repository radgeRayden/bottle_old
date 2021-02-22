let header =
    include
        "glad/glad.h"
        options
            .. "-I" module-dir "/../../cdeps/glad/include"

inline filter-scope (scope pattern)
    fold (scope = (Scope)) for k v in scope
        let name = (k as Symbol as string)
        let match? start end = ('match? pattern name)
        if match?
            'bind scope (Symbol (rslice name end)) v
        else
            scope

let glad-extern = (filter-scope header.extern "^gl(?=[A-Z])")
let glad-define = (filter-scope header.define "^((?=GL_)|gl(?=[A-Z]))")

.. glad-extern glad-define
    do
        let LoadGL = header.extern.gladLoadGL
        locals;
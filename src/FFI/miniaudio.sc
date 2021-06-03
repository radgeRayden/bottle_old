using import .ffi-helpers

let header =
    include
        "miniaudio/miniaudio.h"
        options 
            .. "-I" module-dir "/../../cdeps"

let miniaudio-extern = (filter-scope header.extern "^ma_")
let miniaudio-typedef = (filter-scope header.typedef "^ma_")
let miniaudio-define = (filter-scope header.define "^(?=MA_)")

inline enum-constructor (T)
    bitcast 0 T

for k v in miniaudio-typedef
    T := v as type
    if (T < CEnum)
        for k v in ('symbols T)
            let sname = (k as Symbol as string)
            let match? start end = ('match? (.. "^" (tostring T) "_") sname)
            if match?
                'set-symbol T (Symbol (rslice sname end)) v
                # TODO: erase old symbol from type
                'set-symbol T '__typecall enum-constructor

let ma =
    .. miniaudio-extern miniaudio-typedef miniaudio-define

using import Array
using import String

let C = (import .FFI.libc)

# Initially I had a single function that returned (Array i8), but to convert to String
# involved a memcpy, so now we made it generic wrt. the container.
inline read-full-file (filename containerT)
    local buf : containerT
    do
        using C.stdio
        let fhandle = (fopen filename "rb")
        fseek fhandle 0 SEEK_END
        let flen = (ftell fhandle)
        rewind fhandle

        'resize buf (flen as usize)
        fread buf._items 1 (flen as u64) fhandle
        fclose fhandle
    buf

fn load-file (filename)
    read-full-file filename (Array i8)

fn load-file-string (filename)
    read-full-file filename String

inline log (fmt ...)
    # convert arguments to make sure we don't print containers, since
      C varargs are indiscriminate.
    let ... =
        va-map
            inline (v)
                let T = (typeof v)
                static-if ((T == String) or (T == string))
                    v as rawstring
                else
                    v
            ...
    C.stdio.printf fmt ...
    ; # discard result

do
    let
        load-file
        load-file-string
        log
    locals;
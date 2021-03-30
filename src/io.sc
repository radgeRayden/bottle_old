using import Array
using import String
using import Option

let C = (import .FFI.libc)

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

# Initially I had a single function that returned (Array i8), but to convert to String
# involved a memcpy, so now we made it generic wrt. the container.
inline read-full-file (filename containerT)
    local buf : containerT

    using C.stdio
    let fhandle = (fopen filename "rb")
    if (fhandle == null)
        log "io: %s - there was an error loading the file.\n" filename        
        return ((Option containerT))

    fseek fhandle 0 SEEK_END
    let flen = (ftell fhandle)
    rewind fhandle

    'resize buf (flen as usize)
    fread buf._items 1 (flen as u64) fhandle
    fclose fhandle
    
    (Option containerT) buf

fn load-file (filename)
    read-full-file filename (Array i8)

fn load-file-string (filename)
    read-full-file filename String

do
    let
        load-file
        load-file-string
        log
    locals;

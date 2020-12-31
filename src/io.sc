vvv bind C
do
    let header =
        include """"int printf(const char *restrict format, ...);

    using header.extern
    unlet header
    locals;

using import String

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
    C.printf fmt ...
    ; # discard result

do
    let log
    locals;

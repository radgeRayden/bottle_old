using import ..radlib.foreign
using import ..radlib.core-extensions
import .physfs

define-scope soloud
    let soloud =
        include "../../3rd-party/soloud/include/soloud_c.h"

    using soloud.extern
    using soloud.typedef
    using soloud.enum filter "^SOLOUD_"
    using soloud.define filter "^SOLOUD_"
    let SoloudPhysFSFile_create =
        extern 'SoloudPhysFSFile_create (function (mutable@ voidstar) voidstar)
    let SoloudPhysFSFile_destroy =
        extern 'SoloudPhysFSFile_destroy (function void (mutable@ voidstar))

let filter-pattern =  "^Soloud_"
let soloud = (sanitize-scope soloud filter-pattern)
run-stage;

typedef+ soloud.SOLOUD_ENUMS
    inline __imply (T otherT)
        inline (self)
            bitcast self otherT

# because of the weird enum wrap catch all
let soloud =
    fold (scope = soloud) for k v in ('symbols soloud.SOLOUD_ENUMS)
        'bind scope k v

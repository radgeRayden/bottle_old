using import .ffi-helpers

let header =
    include
        """"#include "sprite_userdata.h"
            #include "cute_headers/cute_spritebatch.h"
        options
            .. "-I" module-dir "/../../cdeps"

let cute-spritebatch-extern = (filter-scope header.extern "^spritebatch_")
vvv bind cute-spritebatch-typedef
do
    let 
        spritebatch = header.typedef.spritebatch_t
        config = header.typedef.spritebatch_config_t
        sprite = header.typedef.spritebatch_sprite_t
    locals;

.. cute-spritebatch-extern 
    cute-spritebatch-typedef 

using import struct
using import glm
using import Array
using import Rc
using import String

import ..runtime
import ..FFI.stbi
import ..io

let stbi = FFI.stbi

struct Sprite
global gid : u64
global sprites : (Array (Rc Sprite))

struct Sprite
    size : ivec2
    id : u64
    data : (Array u8)

    inline... __typecall (cls)
        super-type.__typecall cls
    case (cls, imagedata : (Array u8), x : i32, y : i32)
        let id = (copy gid)
        gid += 1
        copy
            'append sprites
                Rc.wrap
                    super-type.__typecall cls
                        size = (typeinit x y)
                        id = id
                        data = imagedata

    case (cls, filedata : (Array i8))
        local x : i32
        local y : i32
        local ch : i32
        let data = 
            stbi.load_from_memory 
                (imply filedata pointer) as (@ u8) 
                (countof filedata) as i32
                &x
                &y
                &ch
                4

        let imagedata = 
            Struct.__typecall (Array u8)
                _count = (x * y * 4) # size * channels
                _capacity = (x * y * 4)
                _items = data

        this-function cls imagedata x y

    case (cls, filename : String)
        this-function cls ('force-unwrap (io.load-file filename))

do
    let Sprite sprites
    locals;

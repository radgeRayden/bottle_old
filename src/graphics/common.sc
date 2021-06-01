using import struct
using import glm
using import Array
using import Rc
using import String

import ..runtime
import ..FFI.stbi
import ..FFI.glad
import ..io

let stbi = FFI.stbi
let gl = FFI.glad

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

typedef GPUBuffer <:: u32
    inline... __typecall (cls)
        bitcast 0 this-type
    case (cls kind size)
        local handle : u32
        gl.GenBuffers 1 &handle
        gl.BindBuffer (kind as u32) handle
        gl.NamedBufferStorage handle (size as i64) null
            gl.GL_DYNAMIC_STORAGE_BIT
        bitcast handle this-type

    inline __drop (self)
        local handle : u32 = (storagecast (view self))
        gl.DeleteBuffers 1 &handle
        ;

typedef Mesh < Struct
    @@ memo
    inline __typecall (cls attributeT indexT)
        struct (.. "Mesh<" (tostring attributeT) "," (tostring indexT) ">")
            let IndexFormat = indexT
            let AttributeType = attributeT

            attribute-data : (Array AttributeType)
            _attribute-buffer : GPUBuffer
            _attribute-buffer-size : usize
            index-data : (Array IndexFormat)
            _index-buffer : GPUBuffer
            _index-buffer-size : usize

            fn ensure-storage (self)
                """"If store size is not enough to contain uploaded data, destroys the
                    attached GPU resources and recreates them with differently sized data stores.
                let attr-data-size = ((sizeof AttributeType) * (countof self.attribute-data))
                let index-data-size = ((sizeof IndexFormat) * (countof self.index-data))
                # find a size that can hold the amount of data we want by multiplying by 2 repeatedly
                inline find-next-size (current required)
                    if (current == 0)
                        required
                    else
                        loop (size = (deref current))
                            assert (size >= current) # probably an overflow
                            if (size >= required)
                                break size
                            size * 2
                if (self._attribute-buffer-size < attr-data-size)
                    let new-size = (find-next-size self._attribute-buffer-size attr-data-size)
                    self._attribute-buffer =
                        GPUBuffer gl.GL_SHADER_STORAGE_BUFFER new-size
                    self._attribute-buffer-size = new-size
                if (self._index-buffer-size < index-data-size)
                    let new-size = (find-next-size self._index-buffer-size index-data-size)
                    self._index-buffer =
                        GPUBuffer gl.GL_ELEMENT_ARRAY_BUFFER new-size
                    self._index-buffer-size = new-size
                ;
            fn update (self)
                """"Uploads mesh data to GPU.
                'ensure-storage self
                gl.NamedBufferSubData self._attribute-buffer 0 (self._attribute-buffer-size as i64)
                    (imply self.attribute-data pointer) as voidstar
                gl.NamedBufferSubData self._index-buffer 0 (self._index-buffer-size as i64)
                    (imply self.index-data pointer) as voidstar

            fn clear (self)
                'clear self.attribute-data
                'clear self.index-data

            fn draw (self)
                gl.BindBufferBase gl.GL_SHADER_STORAGE_BUFFER 0 self._attribute-buffer
                gl.BindBuffer gl.GL_ELEMENT_ARRAY_BUFFER self._index-buffer
                let indexT =
                    static-match IndexFormat
                    case u16
                        gl.GL_UNSIGNED_SHORT
                    case u32
                        gl.GL_UNSIGNED_INT
                    default
                        assert false "invalid index format"

                gl.DrawElements gl.GL_TRIANGLES ((countof self.index-data) as i32)
                    \ indexT null

            inline... __typecall (cls, expected-attr-count : usize)
                let expected-index-count = ((expected-attr-count * 1.5) as usize) # estimate
                let attr-store-size = ((sizeof AttributeType) * expected-attr-count)
                let ibuffer-store-size = ((sizeof IndexFormat) * expected-index-count)

                let attr-handle =
                    GPUBuffer gl.GL_SHADER_STORAGE_BUFFER attr-store-size

                let ibuffer-handle =
                    GPUBuffer gl.GL_ELEMENT_ARRAY_BUFFER ibuffer-store-size

                local attr-array : (Array AttributeType)
                'reserve attr-array expected-attr-count
                local index-array : (Array IndexFormat)
                'reserve index-array expected-index-count

                super-type.__typecall cls
                    attribute-data = attr-array
                    _attribute-buffer = attr-handle
                    _attribute-buffer-size = attr-store-size
                    index-data = index-array
                    _index-buffer = ibuffer-handle
                    _index-buffer-size = ibuffer-store-size

            # default initialize to zero
            case (cls)
                super-type.__typecall cls

do
    let Sprite Mesh sprites
    locals;

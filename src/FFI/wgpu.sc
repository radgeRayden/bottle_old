let header =
    include "wgpu.h"
        options (.. "-I" module-dir "/../../cdeps/wgpu-native/ffi")

vvv bind wgpu-extern
fold (scope = (Scope)) for k v in header.extern
    let name = (k as Symbol as string)
    let match? start end = ('match? "^wgpu_" name)
    if match?
        'bind scope (Symbol (rslice name end)) v
    else
        scope

vvv bind wgpu-typedef
fold (scope = (Scope)) for k v in header.typedef
    let name = (k as Symbol as string)
    let match? start end = ('match? "^WGPU" name)
    if match?
        'bind scope (Symbol (rslice name end)) v
    else
        scope

vvv bind wgpu-define
fold (scope = (Scope)) for k v in header.define
    let name = (k as Symbol as string)
    let match? start end = ('match? "^WGPU" name)
    if match?
        'bind scope k v
    else
        scope

inline enum-constructor (T)
    bitcast 0 T

vvv bind wgpu-enum
fold (scope = (Scope)) for k v in header.enum
    let T = (v as type)
    for k v in ('symbols T)
        let sname = (k as Symbol as string)
        let match? start end = ('match? "^.+_" sname)
        if match?
            'set-symbol T (Symbol (rslice sname end)) v
            'set-symbol T '__typecall enum-constructor

    # we already know all enums here shoult match WGPU prefix.
    let name = (rslice (k as Symbol as string) (countof "WGPU"))
    'bind scope (Symbol name) v

# in the future we should be able to skip defining these manually.
using import enum
vvv bind wgpu-masks
do
    enum Features : u64
        DEPTH_CLAMPING = 1
        TEXTURE_COMPRESSION_BC = 2
        TIMESTAMP_QUERY = 4
        PIPELINE_STATISTICS_QUERY = 8
        MAPPABLE_PRIMARY_BUFFERS = 65536
        SAMPLED_TEXTURE_BINDING_ARRAY = 131072
        SAMPLED_TEXTURE_ARRAY_DYNAMIC_INDEXING = 262144
        SAMPLED_TEXTURE_ARRAY_NON_UNIFORM_INDEXING = 524288
        UNSIZED_BINDING_ARRAY = 1048576
        MULTI_DRAW_INDIRECT = 2097152
        MULTI_DRAW_INDIRECT_COUNT = 4194304
        PUSH_CONSTANTS = 8388608
        ADDRESS_MODE_CLAMP_TO_BORDER = 16777216
        NON_FILL_POLYGON_MODE = 33554432
        TEXTURE_COMPRESSION_ETC2 = 67108864
        TEXTURE_COMPRESSION_ASTC_LDR = 134217728
        TEXTURE_ADAPTER_SPECIFIC_FORMAT_FEATURES = 268435456
        ALL_WEBGPU = 65535
        ALL_NATIVE = 18446744073709486080

    enum BufferUsage : u64
        MAP_READ = 1
        MAP_WRITE = 2
        COPY_SRC = 4
        COPY_DST = 8
        INDEX = 16
        VERTEX = 32
        UNIFORM = 64
        STORAGE = 128
        INDIRECT = 256

    enum TextureUsage : u64
        COPY_SRC = 1
        COPY_DST = 2
        SAMPLED = 4
        STORAGE = 8
        RENDER_ATTACHMENT = 16

    enum ShaderStage : u64
        NONE = 0
        VERTEX = 1
        FRAGMENT = 2
        COMPUTE = 4

    enum ShaderFlags : u64
        VALIDATION = 1
        EXPERIMENTAL_TRANSLATION = 2

    enum ColorWrite : u64
        RED = 1
        GREEN = 2
        BLUE = 4
        ALPHA = 8
        COLOR = 7
        ALL = 15
    locals;

let wgpu =
    .. wgpu-masks wgpu-enum wgpu-extern wgpu-typedef wgpu-define

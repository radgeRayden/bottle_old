using import String

import ..window
import ..io
import ..FFI.glad
import ..FFI.stbi
import .common

let gl = FFI.glad
let stbi = FFI.stbi

let &local =
    inline... (T : type, ...)
        &
            local T
                ...
    case (source)
        &
            local dummy-name = source


typedef+ common.Sprite
    inline... __typecall (cls)
        super-type.__typecall cls
    case (cls, filename : String)
        local x : i32
        local y : i32
        local ch : i32
        let data = (stbi.load filename &x &y &ch 0)

        local handle : u32
        gl.GenTextures 1 &handle
        gl.BindTexture gl.GL_TEXTURE_2D handle

        vvv bind mipmap-count
        do
            let dimension = (max (x as f32) (y as f32))
            let levels = ((log2 dimension) + 1)
            levels as i32

        gl.TexStorage2D gl.GL_TEXTURE_2D mipmap-count gl.GL_RGBA8 x y
        gl.TexSubImage2D gl.GL_TEXTURE_2D 0 0 0 x y gl.GL_RGBA gl.GL_UNSIGNED_BYTE data
        gl.GenerateMipmap gl.GL_TEXTURE_2D
        gl.TexParameteri gl.GL_TEXTURE_2D gl.GL_TEXTURE_WRAP_S gl.GL_REPEAT
        gl.TexParameteri gl.GL_TEXTURE_2D gl.GL_TEXTURE_WRAP_T gl.GL_REPEAT
        gl.TexParameteri gl.GL_TEXTURE_2D gl.GL_TEXTURE_MAG_FILTER gl.GL_NEAREST
        gl.TexParameteri gl.GL_TEXTURE_2D gl.GL_TEXTURE_MIN_FILTER gl.GL_LINEAR_MIPMAP_LINEAR

        free data

        super-type.__typecall cls
            size = (typeinit x y)
            _handle = handle

    inline __drop (self)
        gl.DeleteTextures 1 (&local (self._handle as u32))

fn init ()
    gl.LoadGL;
    gl.Enable gl.GL_BLEND
    gl.BlendFunc gl.GL_SRC_ALPHA gl.GL_ONE_MINUS_SRC_ALPHA
    gl.Enable gl.GL_MULTISAMPLE
    gl.Enable gl.GL_FRAMEBUFFER_SRGB

fn present ()
    gl.Viewport 0 0 (window.size)
    gl.ClearColor 0.017 0.017 0.017 1.0
    gl.Clear (gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT)

    window.gl-swap-buffers;

do
    let
        init
        present

    let Sprite = common.Sprite
    locals;
import ..internal-state
using import ...common

let cfg = internal-state.startup-config

type DummyModule <: (storageof Nothing)
    inline __getattr (cls key)
        (...) -> () 

if cfg.modules.graphics
    switch cfg.graphics.backend
    case GraphicsBackend.OpenGL
        require-from module-dir ".graphics-gl"
    case GraphicsBackend.WebGPU
        require-from module-dir ".graphics-wgpu"
    default
        error "unrecognized graphics backend option"
else
    `(DummyModule)


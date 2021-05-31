import ..internal-state
using import ...common

switch internal-state.startup-config.graphics.backend
case GraphicsBackend.OpenGL
    require-from module-dir ".graphics-gl"
case GraphicsBackend.WebGPU
    require-from module-dir ".graphics-wgpu"
default
    error "unrecognized graphics backend option"

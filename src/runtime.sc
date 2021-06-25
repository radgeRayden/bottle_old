import .internal-state
let config = internal-state.startup-config
using import ..common

inline lib (name)
    load-library (.. module-dir "/../runtime/" name)

switch operating-system
case 'linux
    lib "libbottle.so"
    lib "libglfw.so"
    if (config.graphics.backend == GraphicsBackend.WebGPU)
        lib "wgpu_native.so"
case 'windows
    lib "libbottle.dll"
    lib "glfw3.dll"
    if (config.graphics.backend == GraphicsBackend.WebGPU)
        lib "wgpu_native.dll"
default
    error "Unsupported OS."

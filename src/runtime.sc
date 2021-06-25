import .internal-state
let config = internal-state.startup-config
using import ..common

inline lib (name)
    try
        load-library (.. module-dir "/../runtime/" name)
    except (ex)
        'dump ex
        hide-traceback;
        error "There was a problem loading a shared library. Did you build the binary dependencies for bottle?"

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

import .config

switch operating-system
case 'linux
    load-library "libglfw.so"

    if (config.GRAPHICS_BACKEND == 'webgpu)
        load-library (.. module-dir "/../cdeps/wgpu-native/target/release/libwgpu_native.so")

    load-library (.. module-dir "/../cdeps/libgame.so")
    load-library (.. module-dir "/../cdeps/libgame.so")
case 'windows
default
    error "Unsupported OS."
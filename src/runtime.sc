import .build-options

switch operating-system
case 'linux
    load-library "libglfw.so"

    if (build-options.GRAPHICS_BACKEND == 'webgpu)
        load-library (.. module-dir "/../cdeps/wgpu-native/target/release/libwgpu_native.so")

    load-library (.. module-dir "/../cdeps/libgame.so")
case 'windows
    load-library "glfw3.dll"

    if (build-options.GRAPHICS_BACKEND == 'webgpu)
        load-library (.. module-dir "/../cdeps/wgpu-native/target/release/libwgpu_native.dll")

    load-library (.. module-dir "/../cdeps/libgame.dll")
default
    error "Unsupported OS."

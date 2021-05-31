import .build-options

switch operating-system
case 'linux
    load-library (.. module-dir "/../runtime/libbottle.so")
    load-library (.. module-dir "/../runtime/libglfw.so")
case 'windows
    load-library (.. module-dir "/../runtime/libbottle.dll")
    load-library (.. module-dir "/../runtime/glfw3.dll")
default
    error "Unsupported OS."

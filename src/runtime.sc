import .build-options

switch operating-system
case 'linux
    load-library "libglfw.so"
    load-library (.. module-dir "/../cdeps/libgame.so")
case 'windows
    load-library "glfw3.dll"
    load-library (.. module-dir "/../cdeps/libbottle.dll")
default
    error "Unsupported OS."

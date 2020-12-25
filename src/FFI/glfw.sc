typedef SCGenPointer < pointer
    inline __imply (A B)
        inline (self)
            imply (storagecast self) B
    inline __rimply (A B)
        inline (self other)
            imply self (storageof B)

let type-buffer = (alloca-array type 128)
let GLFWwindowiconifyfun = (sc_typename_type "GLFWwindowiconifyfun" SCGenPointer)
let GLFWwindow = (sc_typename_type "GLFWwindow" CStruct)
let GLFWerrorfun = (sc_typename_type "GLFWerrorfun" SCGenPointer)
let GLFWcursorposfun = (sc_typename_type "GLFWcursorposfun" SCGenPointer)
let GLFWscrollfun = (sc_typename_type "GLFWscrollfun" SCGenPointer)
let GLFWglproc = (sc_typename_type "GLFWglproc" SCGenPointer)
let GLFWcursor = (sc_typename_type "GLFWcursor" CStruct)
let GLFWwindowfocusfun = (sc_typename_type "GLFWwindowfocusfun" SCGenPointer)
let GLFWwindowclosefun = (sc_typename_type "GLFWwindowclosefun" SCGenPointer)
let GLFWcursorenterfun = (sc_typename_type "GLFWcursorenterfun" SCGenPointer)
let GLFWgammaramp = (sc_typename_type "GLFWgammaramp" CStruct)
let GLFWvkproc = (sc_typename_type "GLFWvkproc" SCGenPointer)
let GLFWwindowmaximizefun = (sc_typename_type "GLFWwindowmaximizefun" SCGenPointer)
let GLFWmousebuttonfun = (sc_typename_type "GLFWmousebuttonfun" SCGenPointer)
let GLFWvidmode = (sc_typename_type "GLFWvidmode" CStruct)
let GLFWwindowposfun = (sc_typename_type "GLFWwindowposfun" SCGenPointer)
let GLFWgamepadstate = (sc_typename_type "GLFWgamepadstate" CStruct)
let GLFWwindowsizefun = (sc_typename_type "GLFWwindowsizefun" SCGenPointer)
let GLFWimage = (sc_typename_type "GLFWimage" CStruct)
let GLFWframebuffersizefun = (sc_typename_type "GLFWframebuffersizefun" SCGenPointer)
let GLFWcharmodsfun = (sc_typename_type "GLFWcharmodsfun" SCGenPointer)
let GLFWcharfun = (sc_typename_type "GLFWcharfun" SCGenPointer)
let GLFWkeyfun = (sc_typename_type "GLFWkeyfun" SCGenPointer)
let GLFWwindowcontentscalefun = (sc_typename_type "GLFWwindowcontentscalefun" SCGenPointer)
let GLFWmonitor = (sc_typename_type "GLFWmonitor" CStruct)
let GLFWmonitorfun = (sc_typename_type "GLFWmonitorfun" SCGenPointer)
let GLFWjoystickfun = (sc_typename_type "GLFWjoystickfun" SCGenPointer)
let GLFWdropfun = (sc_typename_type "GLFWdropfun" SCGenPointer)
let GLFWwindowrefreshfun = (sc_typename_type "GLFWwindowrefreshfun" SCGenPointer)
sc_typename_type_set_opaque GLFWmonitor
sc_typename_type_set_opaque GLFWwindow
sc_typename_type_set_opaque GLFWcursor
store (sc_key_type 'width i32) (getelementptr type-buffer 0)
store (sc_key_type 'height i32) (getelementptr type-buffer 1)
store (sc_key_type 'redBits i32) (getelementptr type-buffer 2)
store (sc_key_type 'greenBits i32) (getelementptr type-buffer 3)
store (sc_key_type 'blueBits i32) (getelementptr type-buffer 4)
store (sc_key_type 'refreshRate i32) (getelementptr type-buffer 5)
sc_typename_type_set_storage GLFWvidmode (sc_tuple_type 6 type-buffer) typename-flag-plain
let _gensc_mutable@<u16> = (sc_pointer_type u16 0:u64 unnamed)
store (sc_key_type 'red _gensc_mutable@<u16>) (getelementptr type-buffer 0)
store (sc_key_type 'green _gensc_mutable@<u16>) (getelementptr type-buffer 1)
store (sc_key_type 'blue _gensc_mutable@<u16>) (getelementptr type-buffer 2)
store (sc_key_type 'size u32) (getelementptr type-buffer 3)
sc_typename_type_set_storage GLFWgammaramp (sc_tuple_type 4 type-buffer) typename-flag-plain
let _gensc_mutable@<u8> = (sc_pointer_type u8 0:u64 unnamed)
store (sc_key_type 'width i32) (getelementptr type-buffer 0)
store (sc_key_type 'height i32) (getelementptr type-buffer 1)
store (sc_key_type 'pixels _gensc_mutable@<u8>) (getelementptr type-buffer 2)
sc_typename_type_set_storage GLFWimage (sc_tuple_type 3 type-buffer) typename-flag-plain
let _gensc_array<u8_15> = (sc_array_type u8 15)
let _gensc_array<f32_6> = (sc_array_type f32 6)
store (sc_key_type 'buttons _gensc_array<u8_15>) (getelementptr type-buffer 0)
store (sc_key_type 'axes _gensc_array<f32_6>) (getelementptr type-buffer 1)
sc_typename_type_set_storage GLFWgamepadstate (sc_tuple_type 2 type-buffer) typename-flag-plain
sc_typename_type_set_storage GLFWglproc (sc_pointer_type (sc_function_type void 0 type-buffer) 2:u64 unnamed) typename-flag-plain
sc_typename_type_set_storage GLFWvkproc (sc_pointer_type (sc_function_type void 0 type-buffer) 2:u64 unnamed) typename-flag-plain
let _gensc_@<i8> = (sc_pointer_type i8 2:u64 unnamed)
store i32 (getelementptr type-buffer 0)
store _gensc_@<i8> (getelementptr type-buffer 1)
sc_typename_type_set_storage GLFWerrorfun (sc_pointer_type (sc_function_type void 2 type-buffer) 2:u64 unnamed) typename-flag-plain
let _gensc_mutable@<GLFWwindow> = (sc_pointer_type GLFWwindow 0:u64 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
store i32 (getelementptr type-buffer 2)
sc_typename_type_set_storage GLFWwindowposfun (sc_pointer_type (sc_function_type void 3 type-buffer) 2:u64 unnamed) typename-flag-plain
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
store i32 (getelementptr type-buffer 2)
sc_typename_type_set_storage GLFWwindowsizefun (sc_pointer_type (sc_function_type void 3 type-buffer) 2:u64 unnamed) typename-flag-plain
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
sc_typename_type_set_storage GLFWwindowclosefun (sc_pointer_type (sc_function_type void 1 type-buffer) 2:u64 unnamed) typename-flag-plain
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
sc_typename_type_set_storage GLFWwindowrefreshfun (sc_pointer_type (sc_function_type void 1 type-buffer) 2:u64 unnamed) typename-flag-plain
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
sc_typename_type_set_storage GLFWwindowfocusfun (sc_pointer_type (sc_function_type void 2 type-buffer) 2:u64 unnamed) typename-flag-plain
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
sc_typename_type_set_storage GLFWwindowiconifyfun (sc_pointer_type (sc_function_type void 2 type-buffer) 2:u64 unnamed) typename-flag-plain
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
sc_typename_type_set_storage GLFWwindowmaximizefun (sc_pointer_type (sc_function_type void 2 type-buffer) 2:u64 unnamed) typename-flag-plain
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
store i32 (getelementptr type-buffer 2)
sc_typename_type_set_storage GLFWframebuffersizefun (sc_pointer_type (sc_function_type void 3 type-buffer) 2:u64 unnamed) typename-flag-plain
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store f32 (getelementptr type-buffer 1)
store f32 (getelementptr type-buffer 2)
sc_typename_type_set_storage GLFWwindowcontentscalefun (sc_pointer_type (sc_function_type void 3 type-buffer) 2:u64 unnamed) typename-flag-plain
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
store i32 (getelementptr type-buffer 2)
store i32 (getelementptr type-buffer 3)
sc_typename_type_set_storage GLFWmousebuttonfun (sc_pointer_type (sc_function_type void 4 type-buffer) 2:u64 unnamed) typename-flag-plain
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store f64 (getelementptr type-buffer 1)
store f64 (getelementptr type-buffer 2)
sc_typename_type_set_storage GLFWcursorposfun (sc_pointer_type (sc_function_type void 3 type-buffer) 2:u64 unnamed) typename-flag-plain
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
sc_typename_type_set_storage GLFWcursorenterfun (sc_pointer_type (sc_function_type void 2 type-buffer) 2:u64 unnamed) typename-flag-plain
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store f64 (getelementptr type-buffer 1)
store f64 (getelementptr type-buffer 2)
sc_typename_type_set_storage GLFWscrollfun (sc_pointer_type (sc_function_type void 3 type-buffer) 2:u64 unnamed) typename-flag-plain
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
store i32 (getelementptr type-buffer 2)
store i32 (getelementptr type-buffer 3)
store i32 (getelementptr type-buffer 4)
sc_typename_type_set_storage GLFWkeyfun (sc_pointer_type (sc_function_type void 5 type-buffer) 2:u64 unnamed) typename-flag-plain
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store u32 (getelementptr type-buffer 1)
sc_typename_type_set_storage GLFWcharfun (sc_pointer_type (sc_function_type void 2 type-buffer) 2:u64 unnamed) typename-flag-plain
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store u32 (getelementptr type-buffer 1)
store i32 (getelementptr type-buffer 2)
sc_typename_type_set_storage GLFWcharmodsfun (sc_pointer_type (sc_function_type void 3 type-buffer) 2:u64 unnamed) typename-flag-plain
let _gensc_mutable@<_gensc_@<i8>> = (sc_pointer_type _gensc_@<i8> 0:u64 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
store _gensc_mutable@<_gensc_@<i8>> (getelementptr type-buffer 2)
sc_typename_type_set_storage GLFWdropfun (sc_pointer_type (sc_function_type void 3 type-buffer) 2:u64 unnamed) typename-flag-plain
let _gensc_mutable@<GLFWmonitor> = (sc_pointer_type GLFWmonitor 0:u64 unnamed)
store _gensc_mutable@<GLFWmonitor> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
sc_typename_type_set_storage GLFWmonitorfun (sc_pointer_type (sc_function_type void 2 type-buffer) 2:u64 unnamed) typename-flag-plain
store i32 (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
sc_typename_type_set_storage GLFWjoystickfun (sc_pointer_type (sc_function_type void 2 type-buffer) 2:u64 unnamed) typename-flag-plain
let _gensc_mutable@<i32> = (sc_pointer_type i32 0:u64 unnamed)
let _gensc_mutable@<_gensc_mutable@<GLFWmonitor>> = (sc_pointer_type _gensc_mutable@<GLFWmonitor> 0:u64 unnamed)
let _gensc_mutable@<f32> = (sc_pointer_type f32 0:u64 unnamed)
let _gensc_@<void> = (sc_pointer_type void 2:u64 unnamed)
let _gensc_@<GLFWvidmode> = (sc_pointer_type GLFWvidmode 2:u64 unnamed)
let _gensc_@<GLFWgammaramp> = (sc_pointer_type GLFWgammaramp 2:u64 unnamed)
let _gensc_@<GLFWimage> = (sc_pointer_type GLFWimage 2:u64 unnamed)
let _gensc_mutable@<f64> = (sc_pointer_type f64 0:u64 unnamed)
let _gensc_mutable@<GLFWcursor> = (sc_pointer_type GLFWcursor 0:u64 unnamed)
let _gensc_@<f32> = (sc_pointer_type f32 2:u64 unnamed)
let _gensc_@<u8> = (sc_pointer_type u8 2:u64 unnamed)
let _gensc_mutable@<GLFWgamepadstate> = (sc_pointer_type GLFWgamepadstate 0:u64 unnamed)
let _gensc_mutable@<u32> = (sc_pointer_type u32 0:u64 unnamed)
let glfwInit = (sc_global_new 'glfwInit (sc_function_type i32 0 type-buffer) 6 unnamed)
let glfwTerminate = (sc_global_new 'glfwTerminate (sc_function_type void 0 type-buffer) 6 unnamed)
store i32 (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
let glfwInitHint = (sc_global_new 'glfwInitHint (sc_function_type void 2 type-buffer) 6 unnamed)
store _gensc_mutable@<i32> (getelementptr type-buffer 0)
store _gensc_mutable@<i32> (getelementptr type-buffer 1)
store _gensc_mutable@<i32> (getelementptr type-buffer 2)
let glfwGetVersion = (sc_global_new 'glfwGetVersion (sc_function_type void 3 type-buffer) 6 unnamed)
let glfwGetVersionString = (sc_global_new 'glfwGetVersionString (sc_function_type _gensc_@<i8> 0 type-buffer) 6 unnamed)
store _gensc_mutable@<_gensc_@<i8>> (getelementptr type-buffer 0)
let glfwGetError = (sc_global_new 'glfwGetError (sc_function_type i32 1 type-buffer) 6 unnamed)
store GLFWerrorfun (getelementptr type-buffer 0)
let glfwSetErrorCallback = (sc_global_new 'glfwSetErrorCallback (sc_function_type GLFWerrorfun 1 type-buffer) 6 unnamed)
store _gensc_mutable@<i32> (getelementptr type-buffer 0)
let glfwGetMonitors = (sc_global_new 'glfwGetMonitors (sc_function_type _gensc_mutable@<_gensc_mutable@<GLFWmonitor>> 1 type-buffer) 6 unnamed)
let glfwGetPrimaryMonitor = (sc_global_new 'glfwGetPrimaryMonitor (sc_function_type _gensc_mutable@<GLFWmonitor> 0 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWmonitor> (getelementptr type-buffer 0)
store _gensc_mutable@<i32> (getelementptr type-buffer 1)
store _gensc_mutable@<i32> (getelementptr type-buffer 2)
let glfwGetMonitorPos = (sc_global_new 'glfwGetMonitorPos (sc_function_type void 3 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWmonitor> (getelementptr type-buffer 0)
store _gensc_mutable@<i32> (getelementptr type-buffer 1)
store _gensc_mutable@<i32> (getelementptr type-buffer 2)
store _gensc_mutable@<i32> (getelementptr type-buffer 3)
store _gensc_mutable@<i32> (getelementptr type-buffer 4)
let glfwGetMonitorWorkarea = (sc_global_new 'glfwGetMonitorWorkarea (sc_function_type void 5 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWmonitor> (getelementptr type-buffer 0)
store _gensc_mutable@<i32> (getelementptr type-buffer 1)
store _gensc_mutable@<i32> (getelementptr type-buffer 2)
let glfwGetMonitorPhysicalSize = (sc_global_new 'glfwGetMonitorPhysicalSize (sc_function_type void 3 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWmonitor> (getelementptr type-buffer 0)
store _gensc_mutable@<f32> (getelementptr type-buffer 1)
store _gensc_mutable@<f32> (getelementptr type-buffer 2)
let glfwGetMonitorContentScale = (sc_global_new 'glfwGetMonitorContentScale (sc_function_type void 3 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWmonitor> (getelementptr type-buffer 0)
let glfwGetMonitorName = (sc_global_new 'glfwGetMonitorName (sc_function_type _gensc_@<i8> 1 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWmonitor> (getelementptr type-buffer 0)
store _gensc_@<void> (getelementptr type-buffer 1)
let glfwSetMonitorUserPointer = (sc_global_new 'glfwSetMonitorUserPointer (sc_function_type void 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWmonitor> (getelementptr type-buffer 0)
let glfwGetMonitorUserPointer = (sc_global_new 'glfwGetMonitorUserPointer (sc_function_type _gensc_@<void> 1 type-buffer) 6 unnamed)
store GLFWmonitorfun (getelementptr type-buffer 0)
let glfwSetMonitorCallback = (sc_global_new 'glfwSetMonitorCallback (sc_function_type GLFWmonitorfun 1 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWmonitor> (getelementptr type-buffer 0)
store _gensc_mutable@<i32> (getelementptr type-buffer 1)
let glfwGetVideoModes = (sc_global_new 'glfwGetVideoModes (sc_function_type _gensc_@<GLFWvidmode> 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWmonitor> (getelementptr type-buffer 0)
let glfwGetVideoMode = (sc_global_new 'glfwGetVideoMode (sc_function_type _gensc_@<GLFWvidmode> 1 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWmonitor> (getelementptr type-buffer 0)
store f32 (getelementptr type-buffer 1)
let glfwSetGamma = (sc_global_new 'glfwSetGamma (sc_function_type void 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWmonitor> (getelementptr type-buffer 0)
let glfwGetGammaRamp = (sc_global_new 'glfwGetGammaRamp (sc_function_type _gensc_@<GLFWgammaramp> 1 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWmonitor> (getelementptr type-buffer 0)
store _gensc_@<GLFWgammaramp> (getelementptr type-buffer 1)
let glfwSetGammaRamp = (sc_global_new 'glfwSetGammaRamp (sc_function_type void 2 type-buffer) 6 unnamed)
let glfwDefaultWindowHints = (sc_global_new 'glfwDefaultWindowHints (sc_function_type void 0 type-buffer) 6 unnamed)
store i32 (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
let glfwWindowHint = (sc_global_new 'glfwWindowHint (sc_function_type void 2 type-buffer) 6 unnamed)
store i32 (getelementptr type-buffer 0)
store _gensc_@<i8> (getelementptr type-buffer 1)
let glfwWindowHintString = (sc_global_new 'glfwWindowHintString (sc_function_type void 2 type-buffer) 6 unnamed)
store i32 (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
store _gensc_@<i8> (getelementptr type-buffer 2)
store _gensc_mutable@<GLFWmonitor> (getelementptr type-buffer 3)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 4)
let glfwCreateWindow = (sc_global_new 'glfwCreateWindow (sc_function_type _gensc_mutable@<GLFWwindow> 5 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
let glfwDestroyWindow = (sc_global_new 'glfwDestroyWindow (sc_function_type void 1 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
let glfwWindowShouldClose = (sc_global_new 'glfwWindowShouldClose (sc_function_type i32 1 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
let glfwSetWindowShouldClose = (sc_global_new 'glfwSetWindowShouldClose (sc_function_type void 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store _gensc_@<i8> (getelementptr type-buffer 1)
let glfwSetWindowTitle = (sc_global_new 'glfwSetWindowTitle (sc_function_type void 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
store _gensc_@<GLFWimage> (getelementptr type-buffer 2)
let glfwSetWindowIcon = (sc_global_new 'glfwSetWindowIcon (sc_function_type void 3 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store _gensc_mutable@<i32> (getelementptr type-buffer 1)
store _gensc_mutable@<i32> (getelementptr type-buffer 2)
let glfwGetWindowPos = (sc_global_new 'glfwGetWindowPos (sc_function_type void 3 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
store i32 (getelementptr type-buffer 2)
let glfwSetWindowPos = (sc_global_new 'glfwSetWindowPos (sc_function_type void 3 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store _gensc_mutable@<i32> (getelementptr type-buffer 1)
store _gensc_mutable@<i32> (getelementptr type-buffer 2)
let glfwGetWindowSize = (sc_global_new 'glfwGetWindowSize (sc_function_type void 3 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
store i32 (getelementptr type-buffer 2)
store i32 (getelementptr type-buffer 3)
store i32 (getelementptr type-buffer 4)
let glfwSetWindowSizeLimits = (sc_global_new 'glfwSetWindowSizeLimits (sc_function_type void 5 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
store i32 (getelementptr type-buffer 2)
let glfwSetWindowAspectRatio = (sc_global_new 'glfwSetWindowAspectRatio (sc_function_type void 3 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
store i32 (getelementptr type-buffer 2)
let glfwSetWindowSize = (sc_global_new 'glfwSetWindowSize (sc_function_type void 3 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store _gensc_mutable@<i32> (getelementptr type-buffer 1)
store _gensc_mutable@<i32> (getelementptr type-buffer 2)
let glfwGetFramebufferSize = (sc_global_new 'glfwGetFramebufferSize (sc_function_type void 3 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store _gensc_mutable@<i32> (getelementptr type-buffer 1)
store _gensc_mutable@<i32> (getelementptr type-buffer 2)
store _gensc_mutable@<i32> (getelementptr type-buffer 3)
store _gensc_mutable@<i32> (getelementptr type-buffer 4)
let glfwGetWindowFrameSize = (sc_global_new 'glfwGetWindowFrameSize (sc_function_type void 5 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store _gensc_mutable@<f32> (getelementptr type-buffer 1)
store _gensc_mutable@<f32> (getelementptr type-buffer 2)
let glfwGetWindowContentScale = (sc_global_new 'glfwGetWindowContentScale (sc_function_type void 3 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
let glfwGetWindowOpacity = (sc_global_new 'glfwGetWindowOpacity (sc_function_type f32 1 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store f32 (getelementptr type-buffer 1)
let glfwSetWindowOpacity = (sc_global_new 'glfwSetWindowOpacity (sc_function_type void 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
let glfwIconifyWindow = (sc_global_new 'glfwIconifyWindow (sc_function_type void 1 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
let glfwRestoreWindow = (sc_global_new 'glfwRestoreWindow (sc_function_type void 1 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
let glfwMaximizeWindow = (sc_global_new 'glfwMaximizeWindow (sc_function_type void 1 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
let glfwShowWindow = (sc_global_new 'glfwShowWindow (sc_function_type void 1 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
let glfwHideWindow = (sc_global_new 'glfwHideWindow (sc_function_type void 1 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
let glfwFocusWindow = (sc_global_new 'glfwFocusWindow (sc_function_type void 1 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
let glfwRequestWindowAttention = (sc_global_new 'glfwRequestWindowAttention (sc_function_type void 1 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
let glfwGetWindowMonitor = (sc_global_new 'glfwGetWindowMonitor (sc_function_type _gensc_mutable@<GLFWmonitor> 1 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store _gensc_mutable@<GLFWmonitor> (getelementptr type-buffer 1)
store i32 (getelementptr type-buffer 2)
store i32 (getelementptr type-buffer 3)
store i32 (getelementptr type-buffer 4)
store i32 (getelementptr type-buffer 5)
store i32 (getelementptr type-buffer 6)
let glfwSetWindowMonitor = (sc_global_new 'glfwSetWindowMonitor (sc_function_type void 7 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
let glfwGetWindowAttrib = (sc_global_new 'glfwGetWindowAttrib (sc_function_type i32 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
store i32 (getelementptr type-buffer 2)
let glfwSetWindowAttrib = (sc_global_new 'glfwSetWindowAttrib (sc_function_type void 3 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store _gensc_@<void> (getelementptr type-buffer 1)
let glfwSetWindowUserPointer = (sc_global_new 'glfwSetWindowUserPointer (sc_function_type void 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
let glfwGetWindowUserPointer = (sc_global_new 'glfwGetWindowUserPointer (sc_function_type _gensc_@<void> 1 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store GLFWframebuffersizefun (getelementptr type-buffer 1)
let glfwSetWindowPosCallback = (sc_global_new 'glfwSetWindowPosCallback (sc_function_type GLFWframebuffersizefun 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store GLFWframebuffersizefun (getelementptr type-buffer 1)
let glfwSetWindowSizeCallback = (sc_global_new 'glfwSetWindowSizeCallback (sc_function_type GLFWframebuffersizefun 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store GLFWwindowrefreshfun (getelementptr type-buffer 1)
let glfwSetWindowCloseCallback = (sc_global_new 'glfwSetWindowCloseCallback (sc_function_type GLFWwindowrefreshfun 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store GLFWwindowrefreshfun (getelementptr type-buffer 1)
let glfwSetWindowRefreshCallback = (sc_global_new 'glfwSetWindowRefreshCallback (sc_function_type GLFWwindowrefreshfun 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store GLFWcursorenterfun (getelementptr type-buffer 1)
let glfwSetWindowFocusCallback = (sc_global_new 'glfwSetWindowFocusCallback (sc_function_type GLFWcursorenterfun 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store GLFWcursorenterfun (getelementptr type-buffer 1)
let glfwSetWindowIconifyCallback = (sc_global_new 'glfwSetWindowIconifyCallback (sc_function_type GLFWcursorenterfun 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store GLFWcursorenterfun (getelementptr type-buffer 1)
let glfwSetWindowMaximizeCallback = (sc_global_new 'glfwSetWindowMaximizeCallback (sc_function_type GLFWcursorenterfun 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store GLFWframebuffersizefun (getelementptr type-buffer 1)
let glfwSetFramebufferSizeCallback = (sc_global_new 'glfwSetFramebufferSizeCallback (sc_function_type GLFWframebuffersizefun 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store GLFWwindowcontentscalefun (getelementptr type-buffer 1)
let glfwSetWindowContentScaleCallback = (sc_global_new 'glfwSetWindowContentScaleCallback (sc_function_type GLFWwindowcontentscalefun 2 type-buffer) 6 unnamed)
let glfwPollEvents = (sc_global_new 'glfwPollEvents (sc_function_type void 0 type-buffer) 6 unnamed)
let glfwWaitEvents = (sc_global_new 'glfwWaitEvents (sc_function_type void 0 type-buffer) 6 unnamed)
store f64 (getelementptr type-buffer 0)
let glfwWaitEventsTimeout = (sc_global_new 'glfwWaitEventsTimeout (sc_function_type void 1 type-buffer) 6 unnamed)
let glfwPostEmptyEvent = (sc_global_new 'glfwPostEmptyEvent (sc_function_type void 0 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
let glfwGetInputMode = (sc_global_new 'glfwGetInputMode (sc_function_type i32 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
store i32 (getelementptr type-buffer 2)
let glfwSetInputMode = (sc_global_new 'glfwSetInputMode (sc_function_type void 3 type-buffer) 6 unnamed)
let glfwRawMouseMotionSupported = (sc_global_new 'glfwRawMouseMotionSupported (sc_function_type i32 0 type-buffer) 6 unnamed)
store i32 (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
let glfwGetKeyName = (sc_global_new 'glfwGetKeyName (sc_function_type _gensc_@<i8> 2 type-buffer) 6 unnamed)
store i32 (getelementptr type-buffer 0)
let glfwGetKeyScancode = (sc_global_new 'glfwGetKeyScancode (sc_function_type i32 1 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
let glfwGetKey = (sc_global_new 'glfwGetKey (sc_function_type i32 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
let glfwGetMouseButton = (sc_global_new 'glfwGetMouseButton (sc_function_type i32 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store _gensc_mutable@<f64> (getelementptr type-buffer 1)
store _gensc_mutable@<f64> (getelementptr type-buffer 2)
let glfwGetCursorPos = (sc_global_new 'glfwGetCursorPos (sc_function_type void 3 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store f64 (getelementptr type-buffer 1)
store f64 (getelementptr type-buffer 2)
let glfwSetCursorPos = (sc_global_new 'glfwSetCursorPos (sc_function_type void 3 type-buffer) 6 unnamed)
store _gensc_@<GLFWimage> (getelementptr type-buffer 0)
store i32 (getelementptr type-buffer 1)
store i32 (getelementptr type-buffer 2)
let glfwCreateCursor = (sc_global_new 'glfwCreateCursor (sc_function_type _gensc_mutable@<GLFWcursor> 3 type-buffer) 6 unnamed)
store i32 (getelementptr type-buffer 0)
let glfwCreateStandardCursor = (sc_global_new 'glfwCreateStandardCursor (sc_function_type _gensc_mutable@<GLFWcursor> 1 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWcursor> (getelementptr type-buffer 0)
let glfwDestroyCursor = (sc_global_new 'glfwDestroyCursor (sc_function_type void 1 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store _gensc_mutable@<GLFWcursor> (getelementptr type-buffer 1)
let glfwSetCursor = (sc_global_new 'glfwSetCursor (sc_function_type void 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store GLFWkeyfun (getelementptr type-buffer 1)
let glfwSetKeyCallback = (sc_global_new 'glfwSetKeyCallback (sc_function_type GLFWkeyfun 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store GLFWcharfun (getelementptr type-buffer 1)
let glfwSetCharCallback = (sc_global_new 'glfwSetCharCallback (sc_function_type GLFWcharfun 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store GLFWcharmodsfun (getelementptr type-buffer 1)
let glfwSetCharModsCallback = (sc_global_new 'glfwSetCharModsCallback (sc_function_type GLFWcharmodsfun 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store GLFWmousebuttonfun (getelementptr type-buffer 1)
let glfwSetMouseButtonCallback = (sc_global_new 'glfwSetMouseButtonCallback (sc_function_type GLFWmousebuttonfun 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store GLFWscrollfun (getelementptr type-buffer 1)
let glfwSetCursorPosCallback = (sc_global_new 'glfwSetCursorPosCallback (sc_function_type GLFWscrollfun 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store GLFWcursorenterfun (getelementptr type-buffer 1)
let glfwSetCursorEnterCallback = (sc_global_new 'glfwSetCursorEnterCallback (sc_function_type GLFWcursorenterfun 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store GLFWscrollfun (getelementptr type-buffer 1)
let glfwSetScrollCallback = (sc_global_new 'glfwSetScrollCallback (sc_function_type GLFWscrollfun 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store GLFWdropfun (getelementptr type-buffer 1)
let glfwSetDropCallback = (sc_global_new 'glfwSetDropCallback (sc_function_type GLFWdropfun 2 type-buffer) 6 unnamed)
store i32 (getelementptr type-buffer 0)
let glfwJoystickPresent = (sc_global_new 'glfwJoystickPresent (sc_function_type i32 1 type-buffer) 6 unnamed)
store i32 (getelementptr type-buffer 0)
store _gensc_mutable@<i32> (getelementptr type-buffer 1)
let glfwGetJoystickAxes = (sc_global_new 'glfwGetJoystickAxes (sc_function_type _gensc_@<f32> 2 type-buffer) 6 unnamed)
store i32 (getelementptr type-buffer 0)
store _gensc_mutable@<i32> (getelementptr type-buffer 1)
let glfwGetJoystickButtons = (sc_global_new 'glfwGetJoystickButtons (sc_function_type _gensc_@<u8> 2 type-buffer) 6 unnamed)
store i32 (getelementptr type-buffer 0)
store _gensc_mutable@<i32> (getelementptr type-buffer 1)
let glfwGetJoystickHats = (sc_global_new 'glfwGetJoystickHats (sc_function_type _gensc_@<u8> 2 type-buffer) 6 unnamed)
store i32 (getelementptr type-buffer 0)
let glfwGetJoystickName = (sc_global_new 'glfwGetJoystickName (sc_function_type _gensc_@<i8> 1 type-buffer) 6 unnamed)
store i32 (getelementptr type-buffer 0)
let glfwGetJoystickGUID = (sc_global_new 'glfwGetJoystickGUID (sc_function_type _gensc_@<i8> 1 type-buffer) 6 unnamed)
store i32 (getelementptr type-buffer 0)
store _gensc_@<void> (getelementptr type-buffer 1)
let glfwSetJoystickUserPointer = (sc_global_new 'glfwSetJoystickUserPointer (sc_function_type void 2 type-buffer) 6 unnamed)
store i32 (getelementptr type-buffer 0)
let glfwGetJoystickUserPointer = (sc_global_new 'glfwGetJoystickUserPointer (sc_function_type _gensc_@<void> 1 type-buffer) 6 unnamed)
store i32 (getelementptr type-buffer 0)
let glfwJoystickIsGamepad = (sc_global_new 'glfwJoystickIsGamepad (sc_function_type i32 1 type-buffer) 6 unnamed)
store GLFWjoystickfun (getelementptr type-buffer 0)
let glfwSetJoystickCallback = (sc_global_new 'glfwSetJoystickCallback (sc_function_type GLFWjoystickfun 1 type-buffer) 6 unnamed)
store _gensc_@<i8> (getelementptr type-buffer 0)
let glfwUpdateGamepadMappings = (sc_global_new 'glfwUpdateGamepadMappings (sc_function_type i32 1 type-buffer) 6 unnamed)
store i32 (getelementptr type-buffer 0)
let glfwGetGamepadName = (sc_global_new 'glfwGetGamepadName (sc_function_type _gensc_@<i8> 1 type-buffer) 6 unnamed)
store i32 (getelementptr type-buffer 0)
store _gensc_mutable@<GLFWgamepadstate> (getelementptr type-buffer 1)
let glfwGetGamepadState = (sc_global_new 'glfwGetGamepadState (sc_function_type i32 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
store _gensc_@<i8> (getelementptr type-buffer 1)
let glfwSetClipboardString = (sc_global_new 'glfwSetClipboardString (sc_function_type void 2 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
let glfwGetClipboardString = (sc_global_new 'glfwGetClipboardString (sc_function_type _gensc_@<i8> 1 type-buffer) 6 unnamed)
let glfwGetTime = (sc_global_new 'glfwGetTime (sc_function_type f64 0 type-buffer) 6 unnamed)
store f64 (getelementptr type-buffer 0)
let glfwSetTime = (sc_global_new 'glfwSetTime (sc_function_type void 1 type-buffer) 6 unnamed)
let glfwGetTimerValue = (sc_global_new 'glfwGetTimerValue (sc_function_type u64 0 type-buffer) 6 unnamed)
let glfwGetTimerFrequency = (sc_global_new 'glfwGetTimerFrequency (sc_function_type u64 0 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
let glfwMakeContextCurrent = (sc_global_new 'glfwMakeContextCurrent (sc_function_type void 1 type-buffer) 6 unnamed)
let glfwGetCurrentContext = (sc_global_new 'glfwGetCurrentContext (sc_function_type _gensc_mutable@<GLFWwindow> 0 type-buffer) 6 unnamed)
store _gensc_mutable@<GLFWwindow> (getelementptr type-buffer 0)
let glfwSwapBuffers = (sc_global_new 'glfwSwapBuffers (sc_function_type void 1 type-buffer) 6 unnamed)
store i32 (getelementptr type-buffer 0)
let glfwSwapInterval = (sc_global_new 'glfwSwapInterval (sc_function_type void 1 type-buffer) 6 unnamed)
store _gensc_@<i8> (getelementptr type-buffer 0)
let glfwExtensionSupported = (sc_global_new 'glfwExtensionSupported (sc_function_type i32 1 type-buffer) 6 unnamed)
store _gensc_@<i8> (getelementptr type-buffer 0)
let glfwGetProcAddress = (sc_global_new 'glfwGetProcAddress (sc_function_type GLFWvkproc 1 type-buffer) 6 unnamed)
let glfwVulkanSupported = (sc_global_new 'glfwVulkanSupported (sc_function_type i32 0 type-buffer) 6 unnamed)
store _gensc_mutable@<u32> (getelementptr type-buffer 0)
let glfwGetRequiredInstanceExtensions = (sc_global_new 'glfwGetRequiredInstanceExtensions (sc_function_type _gensc_mutable@<_gensc_@<i8>> 1 type-buffer) 6 unnamed)
do
    let windowiconifyfun = GLFWwindowiconifyfun
    let window = GLFWwindow
    let errorfun = GLFWerrorfun
    let cursorposfun = GLFWcursorposfun
    let scrollfun = GLFWscrollfun
    let glproc = GLFWglproc
    let cursor = GLFWcursor
    let windowfocusfun = GLFWwindowfocusfun
    let windowclosefun = GLFWwindowclosefun
    let cursorenterfun = GLFWcursorenterfun
    let gammaramp = GLFWgammaramp
    let vkproc = GLFWvkproc
    let windowmaximizefun = GLFWwindowmaximizefun
    let mousebuttonfun = GLFWmousebuttonfun
    let vidmode = GLFWvidmode
    let windowposfun = GLFWwindowposfun
    let gamepadstate = GLFWgamepadstate
    let windowsizefun = GLFWwindowsizefun
    let image = GLFWimage
    let framebuffersizefun = GLFWframebuffersizefun
    let charmodsfun = GLFWcharmodsfun
    let charfun = GLFWcharfun
    let keyfun = GLFWkeyfun
    let windowcontentscalefun = GLFWwindowcontentscalefun
    let monitor = GLFWmonitor
    let monitorfun = GLFWmonitorfun
    let joystickfun = GLFWjoystickfun
    let dropfun = GLFWdropfun
    let windowrefreshfun = GLFWwindowrefreshfun
    let Init = glfwInit
    let Terminate = glfwTerminate
    let InitHint = glfwInitHint
    let GetVersion = glfwGetVersion
    let GetVersionString = glfwGetVersionString
    let GetError = glfwGetError
    let SetErrorCallback = glfwSetErrorCallback
    let GetMonitors = glfwGetMonitors
    let GetPrimaryMonitor = glfwGetPrimaryMonitor
    let GetMonitorPos = glfwGetMonitorPos
    let GetMonitorWorkarea = glfwGetMonitorWorkarea
    let GetMonitorPhysicalSize = glfwGetMonitorPhysicalSize
    let GetMonitorContentScale = glfwGetMonitorContentScale
    let GetMonitorName = glfwGetMonitorName
    let SetMonitorUserPointer = glfwSetMonitorUserPointer
    let GetMonitorUserPointer = glfwGetMonitorUserPointer
    let SetMonitorCallback = glfwSetMonitorCallback
    let GetVideoModes = glfwGetVideoModes
    let GetVideoMode = glfwGetVideoMode
    let SetGamma = glfwSetGamma
    let GetGammaRamp = glfwGetGammaRamp
    let SetGammaRamp = glfwSetGammaRamp
    let DefaultWindowHints = glfwDefaultWindowHints
    let WindowHint = glfwWindowHint
    let WindowHintString = glfwWindowHintString
    let CreateWindow = glfwCreateWindow
    let DestroyWindow = glfwDestroyWindow
    let WindowShouldClose = glfwWindowShouldClose
    let SetWindowShouldClose = glfwSetWindowShouldClose
    let SetWindowTitle = glfwSetWindowTitle
    let SetWindowIcon = glfwSetWindowIcon
    let GetWindowPos = glfwGetWindowPos
    let SetWindowPos = glfwSetWindowPos
    let GetWindowSize = glfwGetWindowSize
    let SetWindowSizeLimits = glfwSetWindowSizeLimits
    let SetWindowAspectRatio = glfwSetWindowAspectRatio
    let SetWindowSize = glfwSetWindowSize
    let GetFramebufferSize = glfwGetFramebufferSize
    let GetWindowFrameSize = glfwGetWindowFrameSize
    let GetWindowContentScale = glfwGetWindowContentScale
    let GetWindowOpacity = glfwGetWindowOpacity
    let SetWindowOpacity = glfwSetWindowOpacity
    let IconifyWindow = glfwIconifyWindow
    let RestoreWindow = glfwRestoreWindow
    let MaximizeWindow = glfwMaximizeWindow
    let ShowWindow = glfwShowWindow
    let HideWindow = glfwHideWindow
    let FocusWindow = glfwFocusWindow
    let RequestWindowAttention = glfwRequestWindowAttention
    let GetWindowMonitor = glfwGetWindowMonitor
    let SetWindowMonitor = glfwSetWindowMonitor
    let GetWindowAttrib = glfwGetWindowAttrib
    let SetWindowAttrib = glfwSetWindowAttrib
    let SetWindowUserPointer = glfwSetWindowUserPointer
    let GetWindowUserPointer = glfwGetWindowUserPointer
    let SetWindowPosCallback = glfwSetWindowPosCallback
    let SetWindowSizeCallback = glfwSetWindowSizeCallback
    let SetWindowCloseCallback = glfwSetWindowCloseCallback
    let SetWindowRefreshCallback = glfwSetWindowRefreshCallback
    let SetWindowFocusCallback = glfwSetWindowFocusCallback
    let SetWindowIconifyCallback = glfwSetWindowIconifyCallback
    let SetWindowMaximizeCallback = glfwSetWindowMaximizeCallback
    let SetFramebufferSizeCallback = glfwSetFramebufferSizeCallback
    let SetWindowContentScaleCallback = glfwSetWindowContentScaleCallback
    let PollEvents = glfwPollEvents
    let WaitEvents = glfwWaitEvents
    let WaitEventsTimeout = glfwWaitEventsTimeout
    let PostEmptyEvent = glfwPostEmptyEvent
    let GetInputMode = glfwGetInputMode
    let SetInputMode = glfwSetInputMode
    let RawMouseMotionSupported = glfwRawMouseMotionSupported
    let GetKeyName = glfwGetKeyName
    let GetKeyScancode = glfwGetKeyScancode
    let GetKey = glfwGetKey
    let GetMouseButton = glfwGetMouseButton
    let GetCursorPos = glfwGetCursorPos
    let SetCursorPos = glfwSetCursorPos
    let CreateCursor = glfwCreateCursor
    let CreateStandardCursor = glfwCreateStandardCursor
    let DestroyCursor = glfwDestroyCursor
    let SetCursor = glfwSetCursor
    let SetKeyCallback = glfwSetKeyCallback
    let SetCharCallback = glfwSetCharCallback
    let SetCharModsCallback = glfwSetCharModsCallback
    let SetMouseButtonCallback = glfwSetMouseButtonCallback
    let SetCursorPosCallback = glfwSetCursorPosCallback
    let SetCursorEnterCallback = glfwSetCursorEnterCallback
    let SetScrollCallback = glfwSetScrollCallback
    let SetDropCallback = glfwSetDropCallback
    let JoystickPresent = glfwJoystickPresent
    let GetJoystickAxes = glfwGetJoystickAxes
    let GetJoystickButtons = glfwGetJoystickButtons
    let GetJoystickHats = glfwGetJoystickHats
    let GetJoystickName = glfwGetJoystickName
    let GetJoystickGUID = glfwGetJoystickGUID
    let SetJoystickUserPointer = glfwSetJoystickUserPointer
    let GetJoystickUserPointer = glfwGetJoystickUserPointer
    let JoystickIsGamepad = glfwJoystickIsGamepad
    let SetJoystickCallback = glfwSetJoystickCallback
    let UpdateGamepadMappings = glfwUpdateGamepadMappings
    let GetGamepadName = glfwGetGamepadName
    let GetGamepadState = glfwGetGamepadState
    let SetClipboardString = glfwSetClipboardString
    let GetClipboardString = glfwGetClipboardString
    let GetTime = glfwGetTime
    let SetTime = glfwSetTime
    let GetTimerValue = glfwGetTimerValue
    let GetTimerFrequency = glfwGetTimerFrequency
    let MakeContextCurrent = glfwMakeContextCurrent
    let GetCurrentContext = glfwGetCurrentContext
    let SwapBuffers = glfwSwapBuffers
    let SwapInterval = glfwSwapInterval
    let ExtensionSupported = glfwExtensionSupported
    let GetProcAddress = glfwGetProcAddress
    let VulkanSupported = glfwVulkanSupported
    let GetRequiredInstanceExtensions = glfwGetRequiredInstanceExtensions
    let GLFW_PRESS = 1
    let GLFW_KEY_MENU = 348
    let GLFW_KEY_F13 = 302
    let GLFW_KEY_LEFT_SUPER = 343
    let GLFW_FOCUS_ON_SHOW = 131084
    let GLFW_MAXIMIZED = 131080
    let GLFW_JOYSTICK_6 = 5
    let GLFW_KEY_Z = 90
    let GLFW_JOYSTICK_13 = 12
    let GLFW_KEY_LEFT_ALT = 342
    let GLFW_KEY_9 = 57
    let GLFW_KEY_BACKSPACE = 259
    let GLFW_KEY_LAST = 348
    let GLFW_GAMEPAD_AXIS_LEFT_TRIGGER = 4
    let GLFW_STICKY_MOUSE_BUTTONS = 208899
    let GLFW_KEY_F = 70
    let GLFW_KEY_4 = 52
    let GLFW_RAW_MOUSE_MOTION = 208901
    let GLFW_KEY_Y = 89
    let GLFW_INVALID_ENUM = 65539
    let GLFW_KEY_N = 78
    let GLFW_KEY_F9 = 298
    let GLFW_OPENGL_ES_API = 196610
    let GLFW_KEY_SEMICOLON = 59
    let GLFW_HOVERED = 131083
    let GLFW_KEY_F11 = 300
    let GLFW_JOYSTICK_15 = 14
    let GLFW_KEY_UNKNOWN = 340282366920938463463374607431768211455
    let GLFW_KEY_KP_0 = 320
    let GLFW_CROSSHAIR_CURSOR = 221187
    let GLFW_FLOATING = 131079
    let GLFW_KEY_2 = 50
    let GLFW_MOUSE_BUTTON_7 = 6
    let GLFW_MOUSE_BUTTON_RIGHT = 1
    let GLFW_OUT_OF_MEMORY = 65541
    let GLFW_KEY_E = 69
    let GLFW_KEY_RIGHT_BRACKET = 93
    let GLFW_NO_RESET_NOTIFICATION = 200705
    let GLFW_KEY_KP_9 = 329
    let GLFW_JOYSTICK_9 = 8
    let GLFW_KEY_F21 = 310
    let GLFW_GAMEPAD_BUTTON_TRIANGLE = 3
    let GLFW_ACCUM_BLUE_BITS = 135177
    let GLFW_OPENGL_CORE_PROFILE = 204801
    let GLFW_CENTER_CURSOR = 131081
    let GLFW_KEY_Q = 81
    let GLFW_KEY_S = 83
    let GLFW_ARROW_CURSOR = 221185
    let GLFW_GAMEPAD_BUTTON_LAST = 14
    let GLFW_KEY_F10 = 299
    let GLFW_KEY_V = 86
    let GLFW_FORMAT_UNAVAILABLE = 65545
    let GLFW_KEY_APOSTROPHE = 39
    let GLFW_JOYSTICK_5 = 4
    let GLFW_DONT_CARE = 340282366920938463463374607431768211455
    let GLFW_CONTEXT_NO_ERROR = 139274
    let GLFW_MOD_ALT = 4
    let GLFW_JOYSTICK_16 = 15
    let GLFW_GAMEPAD_BUTTON_SQUARE = 2
    let GLFW_KEY_RIGHT = 262
    let GLFW_KEY_F25 = 314
    let GLFW_HAND_CURSOR = 221188
    let GLFW_KEY_F4 = 293
    let GLFW_KEY_GRAVE_ACCENT = 96
    let GLFW_JOYSTICK_2 = 1
    let GLFW_PLATFORM_ERROR = 65544
    let GLFW_MOD_CONTROL = 2
    let GLFW_GAMEPAD_BUTTON_X = 2
    let GLFW_JOYSTICK_14 = 13
    let GLFW_SAMPLES = 135181
    let GLFW_JOYSTICK_7 = 6
    let GLFW_DEPTH_BITS = 135173
    let GLFW_KEY_KP_2 = 322
    let GLFW_MOUSE_BUTTON_5 = 4
    let GLFW_KEY_ENTER = 257
    let GLFW_KEY_RIGHT_SHIFT = 344
    let GLFW_KEY_3 = 51
    let GLFW_MOUSE_BUTTON_4 = 3
    let GLFW_KEY_KP_SUBTRACT = 333
    let GLFW_VERSION_REVISION = 2
    let GLFW_GAMEPAD_BUTTON_DPAD_LEFT = 14
    let GLFW_X11_INSTANCE_NAME = 147458
    let GLFW_KEY_KP_4 = 324
    let GLFW_GAMEPAD_BUTTON_LEFT_BUMPER = 4
    let GLFW_KEY_B = 66
    let GLFW_DOUBLEBUFFER = 135184
    let GLFW_JOYSTICK_1 = 0
    let GLFW_GAMEPAD_BUTTON_DPAD_UP = 11
    let GLFW_CURSOR_NORMAL = 212993
    let GLFW_KEY_F14 = 303
    let GLFW_OPENGL_PROFILE = 139272
    let GLFW_KEY_CAPS_LOCK = 280
    let GLFW_KEY_RIGHT_SUPER = 347
    let GLFW_CURSOR_DISABLED = 212995
    let GLFW_LOCK_KEY_MODS = 208900
    let GLFW_KEY_RIGHT_ALT = 346
    let GLFW_KEY_F15 = 304
    let GLFW_AUX_BUFFERS = 135179
    let GLFW_KEY_0 = 48
    let GLFW_GAMEPAD_BUTTON_LEFT_THUMB = 9
    let GLFW_GAMEPAD_BUTTON_DPAD_RIGHT = 12
    let GLFW_EGL_CONTEXT_API = 221186
    let GLFW_KEY_F12 = 301
    let GLFW_HAT_UP = 1
    let GLFW_KEY_6 = 54
    let GLFW_REFRESH_RATE = 135183
    let GLFW_GAMEPAD_BUTTON_BACK = 6
    let GLFW_GAMEPAD_BUTTON_A = 0
    let GLFW_KEY_R = 82
    let GLFW_LOSE_CONTEXT_ON_RESET = 200706
    let GLFW_KEY_8 = 56
    let GLFW_MOD_CAPS_LOCK = 16
    let GLFW_HAT_RIGHT = 2
    let GLFW_KEY_U = 85
    let GLFW_KEY_PRINT_SCREEN = 283
    let GLFW_MOD_NUM_LOCK = 32
    let GLFW_GAMEPAD_AXIS_RIGHT_X = 2
    let GLFW_MOUSE_BUTTON_LAST = 7
    let GLFW_RELEASE_BEHAVIOR_FLUSH = 217089
    let GLFW_COCOA_FRAME_NAME = 143362
    let GLFW_OPENGL_DEBUG_CONTEXT = 139271
    let GLFW_KEY_F16 = 305
    let GLFW_SRGB_CAPABLE = 135182
    let GLFW_KEY_D = 68
    let GLFW_KEY_HOME = 268
    let GLFW_COCOA_GRAPHICS_SWITCHING = 143363
    let GLFW_KEY_END = 269
    let GLFW_BLUE_BITS = 135171
    let GLFW_CONTEXT_VERSION_MAJOR = 139266
    let GLFW_MOUSE_BUTTON_LEFT = 0
    let GLFW_KEY_NUM_LOCK = 282
    let GLFW_KEY_KP_ENTER = 335
    let GLFW_KEY_F22 = 311
    let GLFW_ACCUM_ALPHA_BITS = 135178
    let GLFW_MOUSE_BUTTON_2 = 1
    let GLFW_STEREO = 135180
    let GLFW_COCOA_MENUBAR = 331778
    let GLFW_KEY_PAGE_DOWN = 267
    let GLFW_VERSION_UNAVAILABLE = 65543
    let GLFW_X11_CLASS_NAME = 147457
    let GLFW_RELEASE = 0
    let GLFW_KEY_KP_DECIMAL = 330
    let GLFW_KEY_KP_ADD = 334
    let GLFW_KEY_X = 88
    let GLFW_KEY_UP = 265
    let GLFW_KEY_I = 73
    let GLFW_GREEN_BITS = 135170
    let GLFW_REPEAT = 2
    let GLFW_KEY_COMMA = 44
    let GLFW_HRESIZE_CURSOR = 221189
    let GLFW_KEY_KP_3 = 323
    let GLFW_KEY_W = 87
    let GLFW_MOUSE_BUTTON_3 = 2
    let GLFW_ANY_RELEASE_BEHAVIOR = 0
    let GLFW_JOYSTICK_8 = 7
    let GLFW_CLIENT_API = 139265
    let GLFW_ACCUM_RED_BITS = 135175
    let GLFW_KEY_J = 74
    let GLFW_HAT_CENTERED = 0
    let GLFW_GAMEPAD_BUTTON_GUIDE = 8
    let GLFW_HAT_DOWN = 4
    let GLFW_KEY_KP_6 = 326
    let GLFW_MOUSE_BUTTON_1 = 0
    let GLFW_KEY_F7 = 296
    let GLFW_KEY_SPACE = 32
    let GLFW_KEY_M = 77
    let GLFW_GAMEPAD_AXIS_LAST = 5
    let GLFW_KEY_KP_1 = 321
    let GLFW_GAMEPAD_AXIS_RIGHT_Y = 3
    let GLFW_KEY_SLASH = 47
    let GLFW_KEY_F8 = 297
    let GLFW_RESIZABLE = 131075
    let GLFW_VISIBLE = 131076
    let GLFW_COCOA_RETINA_FRAMEBUFFER = 143361
    let GLFW_KEY_F1 = 290
    let GLFW_CONTEXT_RELEASE_BEHAVIOR = 139273
    let GLFW_API_UNAVAILABLE = 65542
    let GLFW_MOUSE_BUTTON_8 = 7
    let GLFW_NATIVE_CONTEXT_API = 221185
    let GLFW_GAMEPAD_BUTTON_RIGHT_THUMB = 10
    let GLFW_KEY_LEFT = 263
    let GLFW_GAMEPAD_BUTTON_START = 7
    let GLFW_KEY_PAUSE = 284
    let GLFW_MOUSE_BUTTON_6 = 5
    let GLFW_KEY_7 = 55
    let GLFW_KEY_L = 76
    let GLFW_GAMEPAD_AXIS_LEFT_Y = 1
    let GLFW_KEY_F18 = 307
    let GLFW_GAMEPAD_BUTTON_CIRCLE = 1
    let GLFW_OPENGL_COMPAT_PROFILE = 204802
    let GLFW_KEY_BACKSLASH = 92
    let GLFW_RED_BITS = 135169
    let GLFW_KEY_WORLD_1 = 161
    let GLFW_KEY_K = 75
    let GLFW_KEY_F2 = 291
    let GLFW_KEY_C = 67
    let GLFW_KEY_LEFT_BRACKET = 91
    let GLFW_KEY_WORLD_2 = 162
    let GLFW_KEY_TAB = 258
    let GLFW_DECORATED = 131077
    let GLFW_TRUE = 1
    let GLFW_OPENGL_API = 196609
    let GLFW_KEY_INSERT = 260
    let GLFW_MOD_SHIFT = 1
    let GLFW_JOYSTICK_HAT_BUTTONS = 327681
    let GLFW_VERSION_MAJOR = 3
    let GLFW_STENCIL_BITS = 135174
    let GLFW_GAMEPAD_BUTTON_B = 1
    let GLFW_KEY_ESCAPE = 256
    let GLFW_KEY_F17 = 306
    let GLFW_OPENGL_FORWARD_COMPAT = 139270
    let GLFW_HAT_LEFT = 8
    let GLFW_KEY_KP_8 = 328
    let GLFW_GAMEPAD_BUTTON_DPAD_DOWN = 13
    let GLFW_NO_ROBUSTNESS = 0
    let GLFW_MOD_SUPER = 8
    let GLFW_KEY_KP_MULTIPLY = 332
    let GLFW_CONNECTED = 262145
    let GLFW_CONTEXT_REVISION = 139268
    let GLFW_KEY_LEFT_SHIFT = 340
    let GLFW_ALPHA_BITS = 135172
    let GLFW_IBEAM_CURSOR = 221186
    let GLFW_NO_ERROR = 0
    let GLFW_KEY_LEFT_CONTROL = 341
    let GLFW_KEY_F6 = 295
    let GLFW_INVALID_VALUE = 65540
    let GLFW_GAMEPAD_BUTTON_Y = 3
    let GLFW_KEY_KP_DIVIDE = 331
    let GLFW_KEY_DELETE = 261
    let GLFW_KEY_RIGHT_CONTROL = 345
    let GLFW_ACCUM_GREEN_BITS = 135176
    let GLFW_KEY_G = 71
    let GLFW_KEY_EQUAL = 61
    let GLFW_JOYSTICK_LAST = 15
    let GLFW_KEY_H = 72
    let GLFW_KEY_P = 80
    let GLFW_KEY_SCROLL_LOCK = 281
    let GLFW_CURSOR_HIDDEN = 212994
    let GLFW_ICONIFIED = 131074
    let GLFW_KEY_F19 = 308
    let GLFW_CURSOR = 208897
    let GLFW_JOYSTICK_10 = 9
    let GLFW_GAMEPAD_BUTTON_CROSS = 0
    let GLFW_CONTEXT_ROBUSTNESS = 139269
    let GLFW_KEY_F5 = 294
    let GLFW_KEY_F20 = 309
    let GLFW_SCALE_TO_MONITOR = 139276
    let GLFW_DISCONNECTED = 262146
    let GLFW_AUTO_ICONIFY = 131078
    let GLFW_KEY_5 = 53
    let GLFW_KEY_O = 79
    let GLFW_NO_WINDOW_CONTEXT = 65546
    let GLFW_KEY_MINUS = 45
    let GLFW_FOCUSED = 131073
    let GLFW_OPENGL_ANY_PROFILE = 0
    let GLFW_KEY_1 = 49
    let GLFW_KEY_KP_EQUAL = 336
    let GLFW_JOYSTICK_11 = 10
    let GLFW_STICKY_KEYS = 208898
    let GLFW_KEY_F23 = 312
    let GLFW_NO_API = 0
    let GLFW_COCOA_CHDIR_RESOURCES = 331777
    let GLFW_GAMEPAD_BUTTON_RIGHT_BUMPER = 5
    let GLFW_KEY_F3 = 292
    let GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER = 5
    let GLFW_GAMEPAD_AXIS_LEFT_X = 0
    let GLFW_CONTEXT_CREATION_API = 139275
    let GLFW_FALSE = 0
    let GLFW_KEY_T = 84
    let GLFW_OSMESA_CONTEXT_API = 221187
    let GLFW_VRESIZE_CURSOR = 221190
    let GLFW_KEY_PAGE_UP = 266
    let GLFW_KEY_KP_7 = 327
    let GLFW_KEY_PERIOD = 46
    let GLFW_NO_CURRENT_CONTEXT = 65538
    let GLFW_JOYSTICK_12 = 11
    let GLFW_KEY_DOWN = 264
    let GLFW_RELEASE_BEHAVIOR_NONE = 217090
    let GLFW_JOYSTICK_3 = 2
    let GLFW_MOUSE_BUTTON_MIDDLE = 2
    let GLFW_KEY_F24 = 313
    let GLFW_JOYSTICK_4 = 3
    let GLFW_NOT_INITIALIZED = 65537
    let GLFW_KEY_KP_5 = 325
    let GLFW_TRANSPARENT_FRAMEBUFFER = 131082
    let GLFW_KEY_A = 65
    let GLFW_CONTEXT_VERSION_MINOR = 139267
    let GLFW_VERSION_MINOR = 3
    locals;

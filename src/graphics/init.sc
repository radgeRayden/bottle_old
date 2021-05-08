import ..build-options

switch build-options.GRAPHICS_BACKEND
case 'opengl
    require-from module-dir ".graphics-gl"
case 'webgpu
    require-from module-dir ".graphics-wgpu"
default
    error "unrecognized graphics backend option"

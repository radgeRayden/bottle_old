using import enum

enum GraphicsBackend plain
    OpenGL
    WebGPU

    inline __rimply (otherT selfT)
        static-if (otherT == string)
            inline (value)
                match value
                case "OpenGL"
                    this-type.OpenGL
                case "WebGPU"
                    this-type.WebGPU
                default
                    error "unrecognized graphics backend"

do
    let GraphicsBackend
    locals;

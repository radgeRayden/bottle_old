let soloud = (import .FFI.soloud)
import .io

global soloud-instance : (mutable@ soloud.Soloud)

# INTERNAL INTERFACE
# ================================================================================
fn init ()
    soloud-instance = (soloud.create)

    inline try-backend (backend)
        let result =
            soloud.initEx
                soloud-instance
                soloud.SOLOUD_CLIP_ROUNDOFF
                backend
                soloud.SOLOUD_AUTO
                soloud.SOLOUD_AUTO
                2
        if result
            io.log (soloud.getErrorString soloud-instance result)
        result == 0

    static-match operating-system
    case 'linux
        if (try-backend soloud.SOLOUD_PORTAUDIO)
            return;
        else
            io.log "Portaudio backend not available. Consider installing the portaudio package for your distribution.\n"
            ;
        if (try-backend soloud.SOLOUD_MINIAUDIO)
            return;
        else
            io.log "Failed to initialize sound system.\n"
            ;
    case 'windows
        if (try-backend soloud.SOLOUD_MINIAUDIO)
            return;
        else
            io.log "Failed to initialize miniaudio backend.\n"
            ;
        if (try-backend soloud.SOLOUD_WASAPI)
            return;
        else
            io.log "Failed to initialize sound system.\n"
            ;
    default
        error "unsupported OS"

fn cleanup ()
    soloud.deinit soloud-instance
    soloud.destroy soloud-instance

# EXTERNAL INTERFACE
# ================================================================================
fn sfx (kind seed...)
    let sfxr = (soloud.Sfxr_create)
    let preset =
        switch kind
        case 'coin
            soloud.SFXR_COIN
        case 'laser
            soloud.SFXR_LASER
        case 'explosion
            soloud.SFXR_EXPLOSION
        case 'powerup
            soloud.SFXR_POWERUP
        case 'hurt
            soloud.SFXR_HURT
        case 'jump
            soloud.SFXR_JUMP
        case 'blip
            soloud.SFXR_BLIP
        default
            # TODO:
            # warning? error? to be decided.
            soloud.SFXR_BLIP
    let seed = seed...
    soloud.Sfxr_loadPreset sfxr preset ((deref seed) or 0)
    soloud.play soloud-instance sfxr
    ;

do
    let
        init
        cleanup

    vvv bind external
    do
        let sfx
        locals;

    locals;

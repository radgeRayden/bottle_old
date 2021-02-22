using import struct
import .window
let wgpu = (import .FFI.wgpu)

inline &local (T ...)
    &
        local T
            ...

struct GfxState plain
    surface : wgpu.SurfaceId
    adapter : wgpu.AdapterId
    device  : wgpu.DeviceId
    swapchain : wgpu.SwapChainId
    queue : wgpu.QueueId

global istate : GfxState

fn init ()
    istate.surface = (window.create-wgpu-surface)

    wgpu.request_adapter_async
        &local wgpu.RequestAdapterOptions
            power_preference = wgpu.PowerPreference.LowPower
            compatible_surface = istate.surface
        # backend bit
        2 | 4 | 8
        fn (id userdata)
            userdata as:= (mutable@ wgpu.AdapterId)
            @userdata = id
        &istate.adapter as voidstar

    istate.device =
        wgpu.adapter_request_device istate.adapter
            &local wgpu.DeviceDescriptor
                label = "my awesome device"
                features = 0
                limits = (typeinit 1)
                null

    let ww wh = (window.size)
    istate.swapchain =
        wgpu.device_create_swap_chain istate.device istate.surface
            &local wgpu.SwapChainDescriptor
                usage = wgpu.TextureUsage.RENDER_ATTACHMENT
                format = wgpu.TextureFormat.Bgra8UnormSrgb
                width = (ww as u32)
                height = (wh as u32)
                present_mode = wgpu.PresentMode.Fifo

    istate.queue =
        wgpu.device_get_default_queue istate.device

fn present ()
    let next-image = (wgpu.swap_chain_get_current_texture_view istate.swapchain)
    if (not next-image)
        assert false

    let cmd-encoder =
        wgpu.device_create_command_encoder istate.device
            &local wgpu.CommandEncoderDescriptor "cmdencoder"
    let rp =
        wgpu.command_encoder_begin_render_pass cmd-encoder
            &local wgpu.RenderPassDescriptor
                color_attachments =
                    &local wgpu.ColorAttachmentDescriptor
                        attachment = next-image
                        resolve_target = 0
                        channel =
                            typeinit
                                load_op = wgpu.LoadOp.Clear
                                store_op = wgpu.StoreOp.Store
                                clear_value = (typeinit 0.017 0.017 0.017 1.0)
                                read_only = false

                color_attachments_length = 1
                depth_stencil_attachment = null

    wgpu.render_pass_end_pass rp
    local cmdbuf = (wgpu.command_encoder_finish cmd-encoder null)
    wgpu.queue_submit istate.queue &cmdbuf 1
    wgpu.swap_chain_present istate.swapchain

do
    let init present
    locals;
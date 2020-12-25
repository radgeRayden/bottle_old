import platform
class UnsupportedPlatform(Exception):
    pass

def module_dep(name):
    return f"./.git/modules/3rd-party/{name}/HEAD"

make_flavor = ""
make = ""
cc = ""
cxx = ""
include_dirs = [
    "./3rd-party/glad/include",
    "./3rd-party/cimgui/imgui",
    "./3rd-party/soloud/include",
    "./3rd-party/physfs-3.0.2/src",
    "./3rd-party/glfw/include",
    "./3rd-party/xxHash",
]
iflags = ""
for idir in include_dirs:
    iflags = iflags + "-I" + idir + " "

cflags = f"-Wall -O2 -fPIC {iflags}"
cxxflags = f"{cflags} -DIMGUI_IMPL_API='extern \"C\"' -DIMGUI_IMPL_OPENGL_LOADER_GLAD"
exename = ""

genie_url = ""
genie_name = ""

soloud_dir = "./3rd-party/soloud"
soloud_static = f"{soloud_dir}/lib/libsoloud_static_x64.a"
soloud_dynamic = ""
soloud_backends = ""

cimgui_dir = "./3rd-party/cimgui"
cimgui_static = f"{cimgui_dir}/libcimgui.a"
cimgui_dynamic = ""

glfw_dir = "./3rd-party/glfw"
glfw_build = f"{glfw_dir}/build"
glfw_static = f"{glfw_build}/src/libglfw3.a"
glfw_dynamic = ""

physfs_dir = "./3rd-party/physfs-3.0.2"
physfs_build = f"{physfs_dir}/build"
physfs_static = f"{physfs_build}/libphysfs.a"
physfs_dynamic = ""

libgame_dynamic = ""

lflags_common = "-lpthread -lm"
lflags_aot = f"-L. -l:{soloud_static} -l:{cimgui_static} -l:{glfw_static} -l:{physfs_static}"

operating_system = platform.system()
is_windows = operating_system.startswith("MINGW")
if is_windows:
    make_flavor = "MinGW"
    make = "mingw32-make"
    cc = "x86_64-w64-mingw32-gcc"
    cxx = "x86_64-w64-mingw32-g++"
    lflags_common = lflags_common + " -lgdi32 -lwinmm -lole32 -luuid"
    exename = "game.exe"

    genie_url = "https://github.com/bkaradzic/bx/raw/master/tools/bin/windows/genie.exe"
    genie_name = "genie.exe"
    soloud_dynamic = f"{soloud_dir}/lib/soloud_x64.dll"
    cimgui_dynamic = f"{cimgui_dir}/cimgui.dll"
    glfw_dynamic = f"{glfw_build}/src/glfw3.dll"
    physfs_dynamic = f"{physfs_build}/libphysfs.dll"
    libgame_dynamic = "./3rd-party/libgame.dll"

    soloud_backends = "--with-miniaudio --with-wasapi"
elif "Linux" in operating_system:
    make_flavor = "Unix"
    make = "make"
    cc = "gcc"
    cxx = "g++"
    lflags_common = lflags_common + " -ldl -lX11 -lasound"
    exename = "game"

    genie_url = "https://github.com/bkaradzic/bx/raw/master/tools/bin/linux/genie"
    genie_name = "genie"
    soloud_dynamic = f"{soloud_dir}/lib/libsoloud_x64.so"
    cimgui_dynamic = f"{cimgui_dir}/cimgui.so"
    glfw_dynamic = f"{glfw_build}/src/libglfw.so"
    physfs_dynamic = f"{physfs_build}/libphysfs.so"
    libgame_dynamic = "./3rd-party/libgame.so"

    soloud_backends = "--with-miniaudio --with-portaudio"
else:
    raise UnsupportedPlatform

def wrap_cmake(basedir, options):
    return f"mkdir -p {basedir}; cd {basedir}; cmake .. -G '{make_flavor} Makefiles' {options}"

from doit.tools import LongRunning
from doit.tools import run_once

def task_get_genie():
    """get the genie executable to build soloud"""
    genie_path = f"./3rd-party/{genie_name}"
    dl_cmd = f"wget {genie_url} -O {genie_path}"
    chmod_cmd = f"chmod +x {genie_path}"
    return {
        'targets': [genie_path],
        'actions': [dl_cmd, chmod_cmd],
        'uptodate': [run_once]
    }

def download_soloud_dll():
    soloud_url = "http://sol.gfxile.net/soloud/soloud_20200207_lite.zip"
    dl_cmd = f"wget {soloud_url} -O {soloud_dir}/lite.zip"
    copy_cmd = f"unzip -jo {soloud_dir}/lite.zip soloud20200207/bin/soloud_x64.dll -d {soloud_dir}/lib"
    return {
        'basename': "soloud_dll",
        'actions': [dl_cmd, copy_cmd],
        'targets': [soloud_dynamic],
        'uptodate': [run_once]
    }

def build_soloud_so():
    build_dir = f"{soloud_dir}/build"
    genie_path = f"./3rd-party/{genie_name}"
    genie_cmd = f"{genie_path} --file={build_dir}/genie.lua {soloud_backends} --platform=x64 gmake"
    make_cmd = f"make -C {build_dir}/gmake config=release64 SoloudDynamic"
    return {
        'basename': "soloud_so",
        'actions': [genie_cmd, make_cmd],
        'targets': [soloud_dynamic],
        'file_dep': [genie_path, module_dep("soloud")]
    }

def task_soloud_dynamic():
    genie_path = f"./3rd-party/{genie_name}"
    if is_windows:
        yield download_soloud_dll()
    elif "Linux" in operating_system:
        yield build_soloud_so()
    else:
        raise UnsupportedPlatform

def task_soloud():
    genie_path = f"./3rd-party/{genie_name}"
    build_dir = f"{soloud_dir}/build"
    genie_cmd = f"{genie_path} --file={build_dir}/genie.lua {soloud_backends} --with-nosound --platform=x64 gmake"
    make_cmd = f"{make} -C {build_dir}/gmake config=release64 SoloudStatic"
    return {
        'actions': [f"rm -rf {build_dir}/gmake", genie_cmd, make_cmd],
        'targets': [soloud_static],
        'file_dep': [genie_path, soloud_dynamic, module_dep("soloud")]
    }

def task_cimgui():
    cmd_static = f"{make} -C {cimgui_dir} static"
    cmd_dynamic = f"{make} -C {cimgui_dir}"
    return {
        'actions': [cmd_static, cmd_dynamic],
        'targets': [cimgui_static, cimgui_dynamic],
        'file_dep': [module_dep("cimgui")]
    }

def task_glfw():
    shared_options = "-DGLFW_BUILD_EXAMPLES=off -DGLFW_BUILD_TESTS=off -DGLFW_BUILD_DOCS=off -DBUILD_SHARED_LIBS=on"
    static_options = "-DGLFW_BUILD_EXAMPLES=off -DGLFW_BUILD_TESTS=off -DGLFW_BUILD_DOCS=off -DBUILD_SHARED_LIBS=off"
    make_cmd = f"{make} -C {glfw_build}"
    return {
        'actions': [wrap_cmake(glfw_build, shared_options), make_cmd, wrap_cmake(glfw_build, static_options), make_cmd],
        'targets': [glfw_static, glfw_dynamic],
        'file_dep': [module_dep("glfw")]
    }

def task_physfs():
    make_shared = f"{make} -C {physfs_build}"
    make_static = f"{make} -C {physfs_build} physfs-static"
    return {
        'actions': [wrap_cmake(physfs_build, ""), make_shared, make_static],
        'targets': [physfs_static, physfs_dynamic],
        'uptodate': [True]
    }

def gen_obj_name(src):
    if src.endswith(".c"):
        return src[:-2] + ".o"
    elif src.endswith(".cpp"):
        return src[:-4] + ".o"
    else:
        raise f"not a C or C++ source file {src}"

import os
def compile_source(src):
    if src.endswith(".c"):
        target_name = gen_obj_name(src)
        return {
            'basename': target_name,
            'actions': [f"{cc} -c {src} {cflags} -o {target_name}"],
            'targets': [target_name],
            'file_dep': [src]
        }
    elif src.endswith(".cpp"):
        target_name = gen_obj_name(src)
        return {
            'basename': target_name,
            'actions': [f"{cxx} -c {src} {cxxflags} -o {target_name}"],
            'targets': [target_name],
            'file_dep': [src]
        }
    else:
        raise f"not a C or C++ source file {src}"

libgame_src = [
    "./3rd-party/glad/src/glad.c",
    # "./3rd-party/cJSON/cJSON.c",
    # "./3rd-party/stb.c",
    # "./3rd-party/cute.c",
    # "./3rd-party/cimgui/imgui/examples/imgui_impl_opengl3.cpp",
    # "./3rd-party/cimgui/imgui/examples/imgui_impl_glfw.cpp",
    # "./3rd-party/soloud_physfs_ext.cpp",
    # "./3rd-party/tiny-regex-c/re.c",
    # "./3rd-party/hash.c"
]
soloud_c = "./3rd-party/soloud/src/c_api/soloud_c.cpp"

libgame_objs = [gen_obj_name(src) for src in libgame_src]
libgame_objs_str = ""
for obj in libgame_objs:
    libgame_objs_str = libgame_objs_str + obj + " "

def libgame_windows():
    for src in libgame_src:
        yield compile_source(src)

    lflags = f"-Wl,--whole-archive {glfw_static} {cimgui_static} {physfs_static} -Wl,--no-whole-archive -Wl,--export-all {lflags_common}"
    cmd = f"{cxx} -o {libgame_dynamic} {libgame_objs_str} -shared {lflags}"
    yield {
        'basename': "libgame.dll",
        'actions': [cmd],
        'targets': [libgame_dynamic],
        'file_dep': libgame_objs + [glfw_static, cimgui_static, physfs_static],
    }

def libgame_linux():
    for src in libgame_src:
        yield compile_source(src)

    lflags = f"-Wl,-E {lflags_common}"
    cmd = f"{cxx} -o {libgame_dynamic} {libgame_objs_str} -shared {lflags}"
    yield {
        'basename': "libgame.so",
        'actions': [cmd],
        'targets': [libgame_dynamic],
        'file_dep': libgame_objs,
    }

def task_libgame():
    if is_windows:
        yield libgame_windows()
    elif "Linux" in operating_system:
        yield libgame_linux()
    else:
        raise UnsupportedPlatform

# runtime_libs = [libgame_dynamic, glfw_dynamic, cimgui_dynamic, physfs_dynamic, soloud_dynamic]
runtime_libs = [libgame_dynamic]
runtime_targets = [f"./build/{os.path.split(lib)[1]}" for lib in runtime_libs]
def task_runtime():
    def mkcopy(lib):
        return f"cp $(realpath {lib}) ./build/{os.path.split(lib)[1]}"
    copy_libs = [mkcopy(lib) for lib in runtime_libs]
    return {
        'actions': ["mkdir -p ./build"] + copy_libs,
        'file_dep': runtime_libs,
        'targets': runtime_targets
    }

def task_bin_artifact():
    scopes_cmd = "scopes ./src/boot.sc -silent"
    lflags = f"{lflags_aot} {lflags_common}"
    compile_cmd = f"{cxx} -g {cflags} -o ./bin/{exename} {libgame_objs_str} {soloud_c} ./build/game.o {lflags}"
    return {
        'actions': ["mkdir -p ./bin", scopes_cmd, compile_cmd],
        'targets': ["./build/game.o", f"./bin/{exename}"],
        'file_dep': runtime_targets + [soloud_c],
        'uptodate': [False]
    }

def dist_windows(pkg_path):
    libgcc = "/mingw64/bin/libgcc_s_seh-1.dll"
    libstdcpp = "/mingw64/bin/libstdc++-6.dll"
    libpthread = "/mingw64/bin/libwinpthread-1.dll"
    dlls_cmd = f"cp {libgcc} {libstdcpp} {libpthread} ./bin/"
    pkg_cmd = f"zip -r {pkg_path} ./bin ./data"
    yield {
        'basename': "dist",
        'actions': [dlls_cmd, pkg_cmd],
        'targets': [pkg_path],
        'file_dep': ["./build/game.o", f"./bin/{exename}"]
    }

def dist_linux(pkg_path):
    pkg_cmd = f"zip -r {pkg_path} ./bin ./data"
    yield {
        'basename': "dist",
        'actions': [pkg_cmd],
        'targets': [pkg_path],
        'file_dep': ["./build/game.o", f"./bin/{exename}"]
    }

def task_dist():
    pkg_path = "./dist.zip"
    if is_windows:
        yield dist_windows(pkg_path)
    elif "Linux" in operating_system:
        yield dist_linux(pkg_path)
    else:
        raise UnsupportedPlatform

def task_fclean():
    objs = libgame_objs_str + f"{soloud_dir}/src/c_api/soloud_c.o"
    rm_cmd = f"rm -rf {objs} ./build ./bin {glfw_build} {physfs_build} {soloud_dir}/build/gmake ./dist.zip"
    cimgui_cmd = f"cd {cimgui_dir}; {make} clean"
    rm_lib_cmds = [f"rm -f {lib}" for lib in runtime_libs]
    return {
        'actions': [rm_cmd, cimgui_cmd] + rm_lib_cmds
    }

def task_launch():
    """launch bottle"""
    cmd = "scopes ./bottle.sc"
    return {
            'actions': [LongRunning(cmd)],
            'file_dep': runtime_targets,
            'uptodate': [False]
        }

def task_launch_sokoban():
    """launch bottle"""
    cmd = "scopes ./bottle.sc ./demos/sokoban/main.sc"
    return {
            'actions': [LongRunning(cmd)],
            'file_dep': runtime_targets,
            'uptodate': [False]
        }

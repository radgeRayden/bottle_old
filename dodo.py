import platform
class UnsupportedPlatform(Exception):
    pass

make_flavor = ""
make = ""
cc = ""
cxx = ""
include_dirs = [
    "./cdeps/glad/include",
    "./cdeps/glfw/include",
]
iflags = ""
for idir in include_dirs:
    iflags = iflags + "-I" + idir + " "

cflags = f"-Wall -O2 -fPIC {iflags}"
cxxflags = ""
exename = ""

glfw_dir = "./cdeps/glfw"
glfw_build = f"{glfw_dir}/build"
glfw_static = f"{glfw_build}/src/libglfw3.a"
glfw_dynamic = ""

libbottle_dynamic = ""

lflags_common = "-lpthread -lm"
lflags_aot = f"-L. -l:{glfw_static}"

operating_system = platform.system()
is_windows = operating_system.startswith("MINGW")
if is_windows:
    make_flavor = "MinGW"
    make = "mingw32-make"
    cc = "x86_64-w64-mingw32-gcc"
    cxx = "x86_64-w64-mingw32-g++"
    lflags_common = lflags_common + " -lgdi32 -lwinmm -lole32 -luuid"
    exename = "game.exe"

    glfw_dynamic = f"{glfw_build}/src/glfw3.dll"
    libbottle_dynamic = "./cdeps/libbottle.dll"

elif "Linux" in operating_system:
    make_flavor = "Unix"
    make = "make"
    cc = "gcc"
    cxx = "g++"
    lflags_common = lflags_common + " -ldl -lX11 -lasound"
    exename = "game"

    glfw_dynamic = f"{glfw_build}/src/libglfw.so"
    libbottle_dynamic = "./cdeps/libbottle.so"
else:
    raise UnsupportedPlatform
def module_dep(name):
    return f"./.git/modules/cdeps/{name}/HEAD"

# routines for handling of obj files from C/C++, similar to what make offers.
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

def wrap_cmake(basedir, options):
    return f"mkdir -p {basedir}; cd {basedir}; cmake .. -G '{make_flavor} Makefiles' {options}"

from doit.tools import LongRunning
from doit.tools import run_once

libbottle_src = [
    "./cdeps/glad/src/glad.c",
    "./cdeps/stb.c",
    "./cdeps/cute_headers.c",
    "./cdeps/miniaudio.c"
]
libbottle_objs = [gen_obj_name(src) for src in libbottle_src]
libbottle_objs_str = ""
for obj in libbottle_objs:
    libbottle_objs_str = libbottle_objs_str + obj + " "

def libbottle_windows():
    for src in libbottle_src:
        yield compile_source(src)

    lflags = f"-Wl,--export-all {lflags_common}"
    cmd = f"{cxx} -o {libbottle_dynamic} {libbottle_objs_str} -shared {lflags}"
    yield {
        'basename': "libbottle.dll",
        'actions': [cmd],
        'targets': [libbottle_dynamic],
        'file_dep': libbottle_objs + [glfw_dynamic] + ["./cdeps/sprite_userdata.h"],
    }

def libbottle_linux():
    for src in libbottle_src:
        yield compile_source(src)

    lflags = f"-Wl,-E {lflags_common}"
    cmd = f"{cxx} -o {libbottle_dynamic} {libbottle_objs_str} -shared {lflags}"
    yield {
        'basename': "libbottle.so",
        'actions': [cmd],
        'targets': [libbottle_dynamic],
        'file_dep': libbottle_objs,
    }

def task_libbottle():
    if is_windows:
        yield libbottle_windows()
    elif "Linux" in operating_system:
        yield libbottle_linux()
    else:
        raise UnsupportedPlatform

def task_glfw():
    shared_options = "-DGLFW_BUILD_EXAMPLES=off -DGLFW_BUILD_TESTS=off -DGLFW_BUILD_DOCS=off -DBUILD_SHARED_LIBS=on"
    static_options = "-DGLFW_BUILD_EXAMPLES=off -DGLFW_BUILD_TESTS=off -DGLFW_BUILD_DOCS=off -DBUILD_SHARED_LIBS=off"
    make_cmd = f"{make} -C {glfw_build}"
    return {
        'actions': [wrap_cmake(glfw_build, shared_options), make_cmd, wrap_cmake(glfw_build, static_options), make_cmd],
        'targets': [glfw_static, glfw_dynamic],
    }

runtime_libs = [libbottle_dynamic, glfw_dynamic]
runtime_targets = [f"./runtime/{os.path.split(lib)[1]}" for lib in runtime_libs]
def task_runtime():
    def mkcopy(lib):
        return f"cp $(realpath {lib}) ./runtime/{os.path.split(lib)[1]}"
    copy_libs = [mkcopy(lib) for lib in runtime_libs]
    return {
        'actions': ["mkdir -p ./runtime"] + copy_libs,
        'file_dep': runtime_libs,
        'targets': runtime_targets
    }

def task_fclean():
    def mkdelete(artifact):
        return f"rm -f {artifact}"
    return {
        'actions': [mkdelete(artifact) for artifact in runtime_libs + runtime_targets + libbottle_objs],
        'uptodate': [False]
    }

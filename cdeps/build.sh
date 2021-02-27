#!/usr/bin/env bash
set -euo pipefail

gcc -fPIC -o libgame.so -shared stb.c ./glad/src/glad.c -I./glad/include

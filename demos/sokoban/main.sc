using import String
using import struct
using import enum
using import Array
using import glm

spice prefix:ch (str)
    str as:= string
    let count = (countof str)
    if (count != 1)
        hide-traceback;
        error "character literals should have a size of one"

    c := str @ 0
    `c
run-stage;

global levels =
    arrayof String
        """"XXXXXXXXXX
            XGOOBOOOPX
            XXXXXXXXXX

        """"XXXXXXXXXXXXX
            XGXOOOOOOOOOX
            XOOOOOOOOOOOX
            XOXOOOXXXXXXX
            XOOOOOOOBOOPX
            XXXOOOXXXXXXX
            XXXXXXXXXXXXX

        """"XXXXXXXXXXXXX
            XOOOOOGOOOOOX
            XOOOOOBOOOOOX
            XGOOOBPBOOOGX
            XOOOOOBOOOOOX
            XOOOOOGOOOOOX
            XXXXXXXXXXXXX

enum TileType plain
    Free
    Wall
    Goal

struct BoardState
    tiles      : (Array TileType)
    dimensions : ivec2
    boxes      : (Array ivec2)
    player     : ivec2

    inline tile@ (self x y)
        self.tiles @ (y * self.dimensions.x + x)

fn parse-board (n)
    let board-str = (levels @ n)
    # we assume the first line dictates width for the whole board.

    local board : BoardState
    fold (width = 0) for c in board-str
        if (c == 10:i8)
            board.dimensions.x = width
            break width
        width + 1

    fold (x y = 0 0) for c in board-str
        switch c
        case ch"X"
            'append board.tiles TileType.Wall
        case ch"O"
            'append board.tiles TileType.Free
        case ch"G"
            'append board.tiles TileType.Goal
        case ch"P"
            'append board.tiles TileType.Free
            board.player = (ivec2 x y)
        case ch"B"
            'append board.tiles TileType.Free
            'append board.boxes (ivec2 x y)
        case 10:i8
            board.dimensions.y += 1
            repeat 0 (y + 1)
        default
            assert false "unrecognized tile type"
            unreachable;

        _ (x + 1) y
    deref board

global board : BoardState

@@ 'on bottle.load
fn ()
    board = (parse-board 0)
    ;

@@ 'on bottle.update
fn (dt)
    ;

@@ 'on bottle.draw
fn ()
    using import itertools
    for x y in (dim (unpack board.dimensions))
        let t = ('tile@ board x y)
        let tcolor =
            switch t
            case TileType.Free
                if (board.player == (ivec2 x y))
                    vec4 0 0.5 1 1
                else
                    vec4 1
            case TileType.Wall
                vec4 1 0.5 0 1
            case TileType.Goal
                vec4 1 0 0 1
            default
                vec4;

        bottle.graphics.rectangle 'fill tcolor ((vec2 x y) * 20) (vec2 18)

    let bcolor = (vec4 0 0.5 0 1)
    for box in board.boxes
        bottle.graphics.rectangle 'fill bcolor ((vec2 box) * 20) (vec2 18)
        ;
    ;

locals;

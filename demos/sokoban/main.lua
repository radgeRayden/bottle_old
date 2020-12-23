-- love prototype to shape the API

local levels =
{
[[
XXXXXXXXXX
XGOOBOOOPX
XXXXXXXXXX
]],
[[
XXXXXXXXXXXXX
XGXOOOOOOOOOX
XOOOOOOOOOOOX
XOXOOOXXXXXXX
XOOOOOOOBOOPX
XXXOOOXXXXXXX
XXXXXXXXXXXXX
]]
}

local current_level = 1
local game_modes = {}
local game_mode = function () end

local board
local history

local function parse_board (n)
  board = {}
  board.data = {}
  board.boxes = {}
  local board_str = levels[n]
  local bwidth = board_str:find("\n") - 1
  local bheight = 0
  local x,y = 1, 1
  for c in board_str:gmatch(".") do
    if c == "X" then
      table.insert(board.data, false)
    elseif c == "O" then
      table.insert(board.data, true)
    elseif c == "G" then
      table.insert(board.data, "goal")
    elseif c == "P" then
      table.insert(board.data, true)
      board.player = {x, y}
    elseif c == "B" then
      table.insert(board.data, true)
      table.insert(board.boxes, {x, y})
    elseif c == "\n" then
      bheight = bheight + 1
      x = 0
      y = y + 1
    end
    x = x + 1
  end
  board.width = bwidth
  board.height = bheight
end

local function copyv(v)
  return {v[1], v[2]}
end

local function record_state ()
  local boxes = {}
  for k,box in ipairs(board.boxes) do
    table.insert(boxes, copyv(box))
  end
  table.insert(history, {player = copyv(board.player), boxes = boxes})
end

local function rollback_state ()
  if #history > 1 then
    local last = history[#history - 1]
    for i=1,#board.boxes do
      board.boxes[i] = copyv(last.boxes[i])
    end
    board.player = copyv(last.player)
    table.remove(history, #history)
  end
end

function love.load ()
  parse_board(1)
  game_mode = game_modes.puzzle
  history = {}
  record_state()
end
local function try_move (dx, dy)
  local x,y = board.player[1] + dx, board.player[2] + dy
  local idx = (y-1)*board.width+x
  local proj = board.data[idx]

  for k,box in ipairs(board.boxes) do
    if x == box[1] and y == box[2] then
      local bproj = board.data[(box[2]+dy-1)*board.width+box[1]+dx]
      if bproj then
        box[1] = box[1] + dx
        box[2] = box[2] + dy
        if bproj == "goal" then
          game_mode = game_modes.endlevel
        end
      else
        goto blocked
      end
    end
  end
  if proj then
    board.player[1] = x
    board.player[2] = y
  end
  record_state()
  :: blocked ::
end

local keys = {
  previous = {
    left = false,
    right = false,
    up = false,
    down = false,
    undo = false,
    restart = false,
  },
  current = {
    left = false,
    right = false,
    up = false,
    down = false,
    undo = false,
    restart = false,
  },
  map =
    {
      left = 'left',
      right = 'right',
      up = 'up',
      down = 'down',
      z = 'undo',
      r = 'restart'
    }
}

function keys.pressed(key)
  return keys.current[key] and not keys.previous[key]
end

function keys.released(key)
  return not keys.current[key] and keys.previous[key]
end

function keys.down(key)
  return keys.current[key]
end

function love.keypressed(key)
  if not keys.map[key] then return end
  keys.current[keys.map[key]] = true
end

function love.keyreleased(key)
  if key == 'escape' then
    love.event.quit()
  end
  if not keys.map[key] then return end
  keys.current[keys.map[key]] = false
end

function love.update(dt)
  game_mode(dt)
  for k,v in pairs(keys.current) do
    keys.previous[k] = v
  end
end

function love.draw ()
  for y=1,board.height do
    for x=1,board.width do
      local tile = board.data[(y-1)*board.width+x]
      if tile then
        love.graphics.setColor(1,1,1,1)
        if tile == "goal" then
          love.graphics.setColor(1,0,0,1)
        end
        love.graphics.rectangle('fill', x * 20, y * 20, 18, 18)
      else
        love.graphics.setColor(1,0.5,0,1)
        love.graphics.rectangle('fill', x * 20, y * 20, 18, 18)
      end
    end
  end
  local player = board.player
  love.graphics.setColor(0,0.5,1,1)
  love.graphics.rectangle('fill', player[1]*20, player[2]*20,18,18)
  love.graphics.setColor(0,0.5,0,1)
  for k,box in ipairs(board.boxes) do
    love.graphics.rectangle('fill', box[1]*20, box[2]*20,18,18)
  end
end

function game_modes.puzzle (dt)
  if keys.pressed('left') then
    try_move(-1,0)
  elseif keys.pressed('right') then
    try_move(1,0)
  elseif keys.pressed('up') then
    try_move(0,-1)
  elseif keys.pressed('down') then
    try_move(0,1)
  end
  if keys.pressed('undo') then
    rollback_state()
  end
end

function game_modes.endlevel (dt)
  current_level = current_level + 1
  parse_board(current_level)
  history = {}
  record_state()
  game_mode = game_modes.puzzle
end

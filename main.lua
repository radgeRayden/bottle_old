-- love prototype to shape the API

local board_str =
[[
XXXXXXXXXX
XOOOOOOOOX
XOOOOOOOOX
XOOOOOOOOX
XOOOOOOOOX
XOOXXXXOOX
XOOOOOOOOX
XOOOOOOOOX
XOOXGXOOOX
XXXXXXXXXX
]]

local board = {width = 10, height = 10, data = {}}
local player = {2,2}
local box = {5,5}

function love.load ()
  -- parse board
  for c in board_str:gmatch(".") do
    if c == "X" then
      table.insert(board.data, false)
    elseif c == "O" then
      table.insert(board.data, true)
    elseif c == "G" then
      table.insert(board.data, "goal")
    end
  end
end

function love.keypressed(key)
  if key == 'escape' then
    love.event.quit()
  end
end

local function try_move (dx, dy)
  local x,y = player[1] + dx, player[2] + dy
  local idx = (y-1)*board.width+x
  local proj = board.data[idx]

  if x == box[1] and y == box[2] then
    local bproj = board.data[(box[2]+dy-1)*board.width+box[1]+dx]
    if bproj then
      box[1] = box[1] + dx
      box[2] = box[2] + dy
      if bproj == "goal" then
        print("win!")
      end
    else
      goto blocked
    end
  end
  if proj then
    player[1] = x
    player[2] = y
  end
  :: blocked ::
end

function love.keyreleased(key)
  if key == 'left' then
    try_move(-1,0)
  elseif key == 'right' then
    try_move(1,0)
  elseif key == 'up' then
    try_move(0,-1)
  elseif key == 'down' then
    try_move(0,1)
  end
end

function love.update(dt)
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
  love.graphics.setColor(0,0.5,1,1)
  love.graphics.rectangle('fill', player[1]*20, player[2]*20,18,18)
  love.graphics.setColor(0,0.5,0,1)
  love.graphics.rectangle('fill', box[1]*20, box[2]*20,18,18)
end

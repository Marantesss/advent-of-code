-- see if the file exists
function file_exists(file)
    local f = io.open(file, "rb")
    if f then f:close() end
    return f ~= nil
end

-- get all lines from a file, returns an empty 
-- list/table if the file does not exist
function lines_from(file)
    if not file_exists(file) then return {} end
    lines = {}
    for line in io.lines(file) do 
        lines[#lines + 1] = line
    end
    return lines
end

-- transforms string 'lines' into a list
function get_string_in_list(line)
    -- get input file
    local line_array = {}
    for word in string.gmatch(line, '([^,]+)') do -- '[^,]' means "everything but the comma, the + sign means "one or more characters"
        table.insert(line_array, word)
    end

    return line_array
end

-----------------------------------------
------------ FIRST PROBLEM --------------
-----------------------------------------
UP = 'U'
DOWN = 'D'
LEFT = 'L'
RIGHT = 'R'

function problem_1()
    local wire_one_coords = {}
    local wire_two_coords = {}
    for k, v in pairs(wire_one_path) do
        local direction = v:sub(1,1) -- get first char

        if direction == UP do
        elseif direction == D do
        elseif direction == D do
        elseif direction == D do
        else
            print("SOMETHING IS WRONG")
            return
        end
    end
end

-----------------------------------------
----------- SECOND PROBLEM --------------
-----------------------------------------


file = 'res/day03.txt'
lines = lines_from(file)

wire_one_path = get_string_in_list(lines[1])
wire_two_path = get_string_in_list(lines[2])

str = "test"
print(str)
print(str:sub(1,1))

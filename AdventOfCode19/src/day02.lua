-- CONSTANTS
SUM = 0
MULTIPLY = 1
END = 99

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

function get_input_in_list()
    -- get input file
    local file = 'res/day02.txt'
    local lines = lines_from(file)
    local list = {}
    for word in string.gmatch(lines[1], '([^,]+)') do -- '[^,]' means "everything but the comma, the + sign means "one or more characters"
        table.insert(list, tonumber(word))
    end

    return list
end

-----------------------------------------
------------ FIRST PROBLEM --------------
-----------------------------------------
function problem_1(list)
    local current_key = 1
    
    while true do
        print(list[current_key])
        if list[current_key] == SUM then
            sum_array(list, current_key+1, current_key+2, current_key+3)
        elseif list[current_key] == MULTIPLY then
            multiplay_array(list, current_key+1, current_key+2, current_key+3)
        elseif list[current_key] == END then
            return
        end
        current_key = current_key + 4
    end
end

function sum_array(list, index1, index2, index3)
    list[index3 + 1] = list[index1 + 1] + list[index2 + 1]
end

function multiplay_array(list, index1, index2, index3)
    list[index3 + 1] = list[index1 + 1] * list[index2 + 1]
end

-----------------------------------------
----------- SECOND PROBLEM --------------
-----------------------------------------

local old_list = get_input_in_list()
local new_list = old_list;
problem_1(new_list)

for k, v in pairs(new_list) do
    print(old_list[k] .. " - " .. new_list[k])
end

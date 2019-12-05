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
-- CONSTANTS
SUM = 1
MULTIPLY = 2
END = 99


function problem_1()
    local current_key = 1
    
    while true do
        if input_list[current_key] == SUM then
            sum_array(input_list[current_key + 1], input_list[current_key + 2], input_list[current_key + 3])
        elseif input_list[current_key] == MULTIPLY then
            multiplay_array(input_list[current_key + 1], input_list[current_key + 2], input_list[current_key + 3])
        elseif input_list[current_key] == END then
            return
        else
            print("SOMETHING WENT WRONG")
        end
        current_key = current_key + 4
    end
end

function sum_array(index1, index2, index3)
    input_list[index3 + 1] = input_list[index1 + 1] + input_list[index2 + 1]
end

function multiplay_array(index1, index2, index3)
    input_list[index3 + 1] = input_list[index1 + 1] * input_list[index2 + 1]
end

-----------------------------------------
----------- SECOND PROBLEM --------------
-----------------------------------------
function problem_2()
    
end

input_list = get_input_in_list()

problem_1()
print("Problem 1 solution " .. input_list[1])

problem_2()
print("Problem 2 solution " .. "eventually :)")


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

-- get input file
local file = 'res/day01.txt'
local lines = lines_from(file)

-- print all line numbers and their contents
local total_fuel = 0
for k,v in pairs(lines) do
    local fuel_for_module = math.floor(v / 3) - 2
    total_fuel = total_fuel + fuel_for_module
end
print(total_fuel)

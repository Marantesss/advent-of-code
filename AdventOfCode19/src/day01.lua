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


-----------------------------------------
------------ FIRST PROBLEM --------------
-----------------------------------------
function calculate_module_fuel(lines)
    local total_fuel = 0

    for k,v in pairs(lines) do
        local fuel_for_module = math.floor(v / 3) - 2
        total_fuel = total_fuel + fuel_for_module
    end

    return total_fuel
end


-----------------------------------------
----------- SECOND PROBLEM --------------
-----------------------------------------
function calculate_fuel_fuel(fuel)
    local more_fuel = math.floor(fuel / 3) - 2

    if more_fuel < 1 then
        return fuel 
    end
    return fuel + calculate_fuel_fuel(more_fuel)
end

function calculate_total_fuel(lines)
    local total_fuel = 0

    for k,v in pairs(lines) do
        local fuel_for_module = math.floor(v / 3) - 2
        local total_fuel_for_model = calculate_fuel_fuel(fuel_for_module)
        total_fuel = total_fuel + total_fuel_for_model
    end

    return total_fuel
end


-- get input file
local file = 'res/day01.txt'
local lines = lines_from(file)
local total_fuel = calculate_module_fuel(lines)
local total_total_fuel = calculate_total_fuel(lines)

print("First Problem - " .. total_fuel)
print("Second Problem - " .. total_total_fuel)



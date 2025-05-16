local function TABLE_STR(t, depth)
    depth = depth or -1
    local indent = ""
    local indentPrev = ""
    local next = depth
    local endl = " "
    if depth > -1 then
        for i = 1, depth + 1 do
            indent = indent .. "    "
        end
        for i = 1, depth do
            indentPrev = indentPrev .. "    "
        end
        next = depth + 1
        endl = "\n"
    end

    local str = "{" .. endl


    local first = true
    for k, v in pairs(t) do
        if not first then
            str = str .. "," .. endl
        else
            first = false
        end

        local key_str;
        local value_str;
        if type(k) == "string" then
            key_str = string.format("%q", k)
        elseif type(k) == "table" then
            key_str = TABLE_STR(k, next)
        else
            key_str = tostring(k)
        end

        if type(v) == "string" then
            value_str = string.format("%q", v)
        elseif type(v) == "table" then
            value_str = TABLE_STR(v, next)
        else
            value_str = tostring(v)
        end

        str = str .. indent .. "[" .. key_str .. "] = ".. value_str
    end

    return str .. endl .. indentPrev .. "}"
end

local sim = SIMULATION_CREATE("FiveM")
local simulator = SIMULATION_CREATE_SIMULATOR(sim, "SERVER")
print(TABLE_STR(simulator))
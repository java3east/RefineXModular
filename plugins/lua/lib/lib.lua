setmetatable(_G, {
    __index = function (t, k)
        local v = rawget(t, k)
        if v then
            return v
        end

        if PARENT_EXISTS(k) then
            return function(...)
                return PARENT_CALL(k, ...)
            end
        end
    end
})

function print(...)
    local file, line = debug.getinfo(2, "Sl").short_src, debug.getinfo(2, "l").currentline

    local str = ""
    local args = { ... }
    for i, v in ipairs(args) do
        str = str .. tostring(v) .. "    "
    end
    return PARENT_CALL("PRINT", str, file, line)
end

function __ref(val, static)
    if type(val) ~= 'table' then
        return val
    end
    local o = {}
    local mt = {}
    for k, v in pairs(val) do
        if k == "__RFXREF__" then
            return function(a, ...)
                local parameters = { ... }
                table.insert(parameters, 1, a)
                return PARENT_CALL("RFXREF", v, table.unpack(parameters))
            end, false
        elseif k == "__RFXREFMT__" then
            return function(a, ...)
                local parameters = { a, ... }
                return PARENT_CALL("RFXREF", v, table.unpack(parameters))
            end, true
        end
        if type(v) == 'table' then
            local obj, forMetatable = __ref(v, static)
            if not forMetatable then
                o[k] = obj
            else
                mt[k] = obj
            end
        else
            o[k] = v
        end
    end
    setmetatable(o, mt)
    return o
end

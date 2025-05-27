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
    local args = {...}
    for i, v in ipairs(args) do
        str = str .. tostring(v) .. "    "
    end
    return PARENT_CALL("PRINT", str, file, line)
end
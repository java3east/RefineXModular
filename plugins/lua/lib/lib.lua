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
    local args = {...}
    for i, v in ipairs(args) do
        if type(v) == "table" then
            args[i] = table.concat(v, ", ")
        end
    end
    return PARENT_CALL("PRINT", table.unpack(args))
end
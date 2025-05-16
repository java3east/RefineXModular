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
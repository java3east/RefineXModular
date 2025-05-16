function tableToString(tbl)
    local str = "{"
    local first = true

    for k, v in pairs(tbl) do
        if first then
            first = false
        else
            str = str .. ", "
        end
        if type(k) == "string" then
            str = str .. k .. "="
        end
        if type(v) == "table" then
            str = str .. tableToString(v)
        elseif type(v) == "string" then
            str = str .. '"' .. v .. '"'
        else
            str = str .. tostring(v)
        end
    end

    str = str .. "}"
    return str
end

function pSub(...)
    for i, v in ipairs({...}) do
        if type(v) == "table" then
            v = tableToString(v)
        end
        print(i, v)
    end
end

pSub(PARENT_CALL("test", "t", 1, 1.0, true, {{x=1},{x=2},{x=3}}))
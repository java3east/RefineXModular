---@class Test
---@field name string
---@field passed boolean
---@field fun fun(self: Test): boolean
Test = {}
Test.__index = Test

local tests = {}

---@return boolean passed
function Test.assert(condition, message)
    if condition then
        return true
    end
    RFX_ERROR("Test.assert: " .. (message or "Assertion failed"))
    return false
end

local function tableEqual(t1, t2)
    if #t1 ~= #t2 then return false end
    for k, v in pairs(t1) do
        if type(v) == "table" then
            if not tableEqual(v, t2[k]) then return false end
        elseif v ~= t2[k] then
            return false
        end
    end
    return true
end

local function tableStr(t)
    local str = "{"
    for k, v in pairs(t) do
        if type(v) == "table" then
            str = str .. k .. "=" .. tableStr(v) .. ", "
        else
            str = str .. k .. "=" .. tostring(v) .. ", "
        end
    end
    return str:sub(1, -3) .. "}"
end

function Test.assertEqual(a, b, message)
    if a == b then
        return true
    end
    if type(a) == "table" and type(b) == "table" then
        if tableEqual(a, b) then
            return true
        end
        RFX_ERROR("Test.assertEqual: " .. (message or "Tables are not equal") .. " (got: " .. tableStr(a) .. ", expected: " .. tableStr(b) .. ")")
        return false
    end
    RFX_ERROR("Test.assertEqual: " .. (message or "Values are not equal") .. " (got: " .. tostring(a) .. ", expected: " .. tostring(b) .. ")")
    return false
end

---@param value any
---@param acceptedValues any[]
---@param message string
---@return boolean passed
function Test.assertOneOf(value, acceptedValues, message)
    for _, v in ipairs(acceptedValues) do
        if value == v then
            return true
        end
    end
    RFX_ERROR("Test.assertOneOf: " .. (message or "Value not in accepted values"))
    return false
end

---@param moduleName string the name of the module
function Test.runAll(moduleName)
    for _, test in ipairs(tests) do
        test:run()
    end
    local total = 0
    local ok = 0
    for _, test in ipairs(tests) do
        print("    [" .. (test.passed and "✔️" or "❌") .. "] " .. (test.passed and Color.GREEN or Color.RED) .. tostring(test.name))
        ok = ok + (test.passed and 1 or 0)
        total = total + 1
    end
    tests = {}
    print(" ===== " .. Color.MAGENTA .. "Tests completed: " .. Color.BOLD .. ok .. "/" .. total .. Color.RESET .. Color.MAGENTA .. " successful" .. Color.RESET .. " ==== ")
end

---@param name string the name of the test
---@param fun fun(self: Test) : boolean the function to run for the test
function Test.new(name, fun)
    local test = {}
    setmetatable(test, Test)
    test.name = name or "Unnamed Test"
    test.passed = false
    test.fun = fun
    table.insert(tests, test)
    return test
end

function Test:run()
    local o, r = pcall(function ()
        self.passed = self:fun()
    end)
    if not o then
        RFX_ERROR("Test.run: " .. (self.name or "Unnamed Test") .. " failed with error: " .. tostring(r))
        self.passed = false
    end
end

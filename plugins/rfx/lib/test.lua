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
    print(" ===== " .. Color.MAGENTA .. "Running tests for module: " .. Color.BOLD .. "'" .. moduleName .. "'" .. Color.RESET .. " ==== ")
    for _, test in ipairs(tests) do
        test:run()
    end
    for _, test in ipairs(tests) do
        print("    [" .. (test.passed and "✔️" or "❌") .. "] " .. (test.passed and Color.GREEN or Color.RED) .. tostring(test.name))
    end
    tests = {}
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
    self.passed = self:fun()
end

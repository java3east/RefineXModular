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

function Test.runAll()
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

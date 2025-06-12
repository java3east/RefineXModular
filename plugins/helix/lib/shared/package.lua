---@class Package
Package = {}
Package.__index = Package

---@param file string the name of the file that should be loaded at this point
function Package.Require(file)
    PACKAGE_REQUIRE(file)
end

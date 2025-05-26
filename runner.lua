local sim = SIMULATION_CREATE("FiveM")
local res = RESOURCE_LOAD(sim, "./resources/test")
local cl1 = SIMULATOR_CREATE(sim, "CLIENT")
RESOURCE_START(res)
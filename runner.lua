local sim = SIMULATION_CREATE("FiveM")
local res = RESOURCE_LOAD(sim, "./resources/test")
RESOURCE_START(res)
local cl1 = SIMULATOR_CREATE(sim, "CLIENT")
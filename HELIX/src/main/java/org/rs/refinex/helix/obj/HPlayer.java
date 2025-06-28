package org.rs.refinex.helix.obj;

import org.rs.refinex.RefineX;
import org.rs.refinex.guid.GUID;
import org.rs.refinex.helix.simulation.simulators.ClientSimulator;
import org.rs.refinex.log.LogType;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.value.ExportFunction;

import java.util.HashMap;

public class HPlayer extends Controller {
    private static final HashMap<ClientSimulator, HPlayer> simulators = new HashMap<>();
    private final ClientSimulator simulator;
    private final String identifier;

    @ExportFunction
    public static HPlayer __of(Environment environment, GUID guid) {
        ClientSimulator simulator = (ClientSimulator) GUID.get(guid, ClientSimulator.class);
        return simulators.get(simulator);
    }

    @ExportFunction
    public static HPlayer GetByIndex(Environment environment, int index) {
        GUID guid = new GUID();
        guid.guid = index;
        return (HPlayer) GUID.get(guid, HPlayer.class);
    }

    public HPlayer(ClientSimulator simulator, String identifier) {
        this.simulator = simulator;
        this.identifier = identifier;
        this.name = "Player" + (int) (Math.random() * 1000);
        simulators.put(simulator, this);
    }

    public HPlayer(ClientSimulator simulator) {
        this(simulator, "AAAAA".replace('A', (char) ('A' + Math.random() * 26)));
    }

    /**
     * Returns the simulator this player is associated with.
     * @return the client simulator
     */
    public ClientSimulator client() {
        return this.simulator;
    }

    /**
     * Returns the name of the player.
     * @return the name of the player
     */
    @ExportFunction
    public String GetName(Environment environment) {
        return this.name;
    }

    /**
     * Returns the identifier of the player.
     * @return the identifier of the player
     */
    @ExportFunction
    public String GetIdentifier(Environment environment) {
        RefineX.logger.log(LogType.WARNING, "UNCONFIRMED API USAGE: 'HPlayer:GetIdentifier()' might not exist",
                environment.currentSource());
        return this.identifier;
    }
}

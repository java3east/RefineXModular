package org.rs.refinex.helix.obj;

import org.rs.refinex.guid.GUID;
import org.rs.refinex.helix.HelixEventHandler;
import org.rs.refinex.helix.simulation.simulators.ClientSimulator;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.value.ExportFunction;
import org.rs.refinex.value.Function;
import org.rs.refinex.value.Referencable;

import java.util.HashMap;

public class Player extends Referencable {
    private static final HashMap<ClientSimulator, Player> players = new HashMap<>();

    private static String randomIdentifier() {
        StringBuilder identifier = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            identifier.append((char) ('a' + Math.random() * 26));
        }
        return identifier.toString();
    }

    private final ClientSimulator simulator;
    private final String name = "Player" + (int) (Math.random() * 1000);
    public String identifier;

    public Player(ClientSimulator clientSimulator) {
        this.simulator = clientSimulator;
        this.identifier = randomIdentifier();
        players.put(clientSimulator, this);
    }

    public ClientSimulator client() {
        return this.simulator;
    }

    @ExportFunction
    public String GetName(Environment environment) {
        return name;
    }

    @ExportFunction
    public String getIdentifier(Environment environment) {
        return this.identifier;
    }

    @ExportFunction
    public static void Subscribe(Environment environment, String event, Function callback) {
        environment.addEventHandler(
                new HelixEventHandler(
                        environment,
                        event,
                        callback,
                        false,
                        environment.currentSource(),
                        "PLAYER"
                )
        );
    }

    @ExportFunction
    public static Player __of(Environment environment, GUID guid) {
        ClientSimulator simulator = (ClientSimulator) GUID.get(guid, ClientSimulator.class);
        return players.get(simulator);
    }
}

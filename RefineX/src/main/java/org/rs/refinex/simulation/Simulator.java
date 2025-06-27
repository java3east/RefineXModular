package org.rs.refinex.simulation;

import org.jetbrains.annotations.NotNull;
import org.rs.refinex.RefineX;
import org.rs.refinex.context.ContextEvent;
import org.rs.refinex.log.LogType;
import org.rs.refinex.plugin.Language;
import org.rs.refinex.scripting.Environment;
import org.rs.refinex.scripting.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Simulators represent a container of simulated scripting environments.
 * They can be used to represent a single client or server.
 *
 * @author Florian B.
 */
public abstract class Simulator {
    private final @NotNull String type;
    private final @NotNull Simulation simulation;
    private final HashMap<String, Environment> environments = new HashMap<>();
    private final HashMap<String, Object> dataset = new HashMap<>();
    private final List<ContextEvent> eventQueue = new ArrayList<>();

    private long gameTimer = 0;

    /**
     * Creates a new simulator for the given simulation.
     * @param simulation the simulation this simulator will be created for
     */
    public Simulator(final @NotNull Simulation simulation, final @NotNull String type) {
        this.simulation = simulation;
        this.type = type;
    }

    public @NotNull Optional<Object> getData(final @NotNull String key) {
        return Optional.ofNullable(dataset.get(key));
    }

    public void setData(final @NotNull String key, final @NotNull Object value) {
        dataset.put(key, value);
    }

    public @NotNull String getType() {
        return type;
    }

    /**
     * Returns the simulation this simulator belongs to.
     * @return the simulation
     */
    public @NotNull Simulation getSimulation() {
        return this.simulation;
    }

    /**
     * Adds the given namespace to the environment.
     * @param environment the environment to add the namespace to
     */
    protected abstract void addNamespaces(final @NotNull Environment environment);

    /**
     * Returns the name of this simulator
     * @return the name of this simulator
     */
    public abstract @NotNull String getName();

    /**
     * This method is called when a resource is trying to start on this simulator.
     * If the return value is false, the resource will not be started.
     * @param resource the resource that is trying to start
     * @return true if the resource can start, false otherwise
     */
    public abstract boolean onResourceStarting(final @NotNull Resource resource);

    /**
     * Called when a resource started on this simulator.
     * @param resource the resource that started
     */
    public abstract void onResourceStart(final @NotNull Resource resource);

    /**
     * This method is called when the simulator should be destroyed.
     * It should clean up all resources and environments, as well as disconnect
     * this simulator from the simulation.
     * It is allowed to reject the destruction, in case this is a SERVER simulator
     * or similar.
     * @return true if the simulator was destroyed successfully, false otherwise
     */
    public abstract boolean destroy();

    /**
     * Creates a new environment that should be bound to this simulator.
     * This will also register the environment in this simulator, so it can be
     * retrieved later with {@link #getEnvironment(String)}
     * @param name the name the environment should be registered with
     * @param language the language the environment should be created for
     * @return the created environment
     */
    public @NotNull Environment createEnvironment(final @NotNull String name, final @NotNull Language language, final @NotNull  Resource resource) {
        Environment environment = language.createEnvironment(this, resource);
        addNamespaces(environment);
        environments.put(name, environment);
        return environment;
    }

    public void dispatchEvent(final @NotNull ContextEvent event, boolean ignoreMissingHandlers) {
        boolean handled = false;
        for (Environment environment : environments.values()) {
            if (environment.dispatchEvent(event)) handled = true;
        }
        if (!handled && !ignoreMissingHandlers) {
            RefineX.logger.log(LogType.WARNING, "Event '" + event.name() + "' has no registered handler.", event.origin());
        }
    }

    public void queueEvent(final @NotNull ContextEvent event) {
        eventQueue.add(event);
    }

    public void tick(double frameTime) {
        gameTimer += (long) (frameTime * 1000);
        for (ContextEvent event : eventQueue) {
            dispatchEvent(event, false);
        }
        eventQueue.clear();
        for (Environment environment : environments.values()) {
            environment.tick(frameTime);
        }
    }

    /**
     * Returns an environment with the given name.
     * 
     * @throws RuntimeException if the environment does not exist
     * 
     * @param name the name of the environment to retrieve
     * @return the environment with the given name
     */
    public @NotNull Environment getEnvironment(final @NotNull String name) {
        return environments.get(name);
    }

    /**
     * Returns the current game timer of this simulator.
     * @return the current game timer
     */
    public long getGameTimer() {
        return gameTimer;
    }
}

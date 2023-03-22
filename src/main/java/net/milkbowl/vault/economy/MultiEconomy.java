package net.milkbowl.vault.economy;

import org.bukkit.World;

import java.util.Collection;

/**
 * This interface is used to provide multi-world, multi-currency support for the economy.
 * It allows disabling currencies/economies.
 */
public interface MultiEconomy {

    /**
     * Checks if MultiEconomy method is enabled.
     *
     * @return Success or Failure
     */
    public boolean isEnabled();

    /**
     * Gets name of MultiEconomy method
     *
     * @return Name of Economy Method
     */
    public String getName();

    /**
     * Checks to see if a name of the implementation exists with this name.
     *
     * @param name The name of the name of the implementation to search for.
     * @return True if the implementation exists, else false.
     */
    public boolean existsImplementation(String name);

    /**
     * Checks to see if a name of the implementation exists with this name.
     *
     * @param name  The name of the name of the implementation to search for.
     * @param world The name of the {@link World} to check for this name of the implementation in.
     * @return True if the implementation exists, else false.
     */
    public boolean existsImplementation(String name, String world);

    /**
     * Used to get the implementation with the specified name.
     *
     * @param name The name of the implementation to get.
     * @return The implementation with the specified name.
     */
    public Economy getImplementation(String name);

    /**
     * Used to get the default implementation. This could be the default implementation for the server globally or
     * for the default world if the implementation supports multi-world.
     *
     * @return The implementation that is the default for the server if multi-world support is not available
     * otherwise the default for the default world.
     */
    public Economy getDefault();

    /**
     * Checks to see if the default implementation is not null.
     *
     * @return True if the default implementation is not null, else false.
     */
    public default boolean hasDefault() {
        return getDefault() != null;
    }

    /**
     * Used to get the default implementation for the specified world if this implementation has multi-world
     * support, otherwise the default implementation for the server.
     *
     * @param world The world to get the default implementation for.
     * @return The default implementation for the specified world if this implementation has multi-world
     * support, otherwise the default implementation for the server.
     */
    public Economy getDefault(String world);

    /**
     * Checks to see if the default implementation for the specified world is not null.
     *
     * @param world The world to check the default implementation for.
     * @return True if the default implementation for the specified world is not null, else false.
     */
    public default boolean hasDefault(String world) {
        return getDefault(world) != null;
    }

    /**
     * Used to get a collection of every implementation identifier for the server.
     *
     * @return A collection of every implementation identifier that is available for the server.
     */
    public Collection<Economy> getAllImplementations();

    /**
     * Used to get a collection of every {@link Economy} object that is available in the specified world if
     * this implementation has multi-world support, otherwise all {@link Economy} objects for the server.
     *
     * @param world The world we want to get the {@link Economy} objects for.
     * @return A collection of every implementation identifier that is available in the specified world if
     * this implementation has multi-world support, otherwise all implementation identifiers for the server.
     */
    public Collection<Economy> getAllImplementations(String world);
}
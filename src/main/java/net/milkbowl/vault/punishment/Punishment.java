/* This file is part of Vault.

    Vault is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Vault is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with Vault.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.milkbowl.vault.punishment;

import org.bukkit.OfflinePlayer;

/**
 * The punishment interface for the punishment service
 */
public interface Punishment {
    /**
     *
     * @param player the player you want to check
     * @return the status of the mute
     */
    public boolean isMuted(OfflinePlayer player);

    /**
     * @param player the player you want to check
     * @return the status of the ban
     */
    public boolean isBanned(OfflinePlayer player);

    /**
     * @param player the player you want to check
     * @return the status of the jail
     */
    public boolean isJailed(OfflinePlayer player);

    /**
     * This is a perm ban!
     *
     * @param player the player you want to ban
     */
    public void banUser(OfflinePlayer player);

    /**
     * This is a temp ban
     *
     * @param player The player you want to be banned
     * @param length the length of the ban
     */
    public void banUser(OfflinePlayer player, long length);

    /**
     * @param player the player you want to jail
     * @param length the length of the jailing
     */
    public void jailUser(OfflinePlayer player, long length);

    /**
     * This is a perm mute
     *
     * @param player the player you want to mute
     */
    public void muteUser(OfflinePlayer player);

    /**
     * This is a temp mute
     *
     * @param player the player you want to mute
     * @param length the length of the mute
     */
    public void muteUser(OfflinePlayer player, long length);

    /**
     * this will unban the user
     * @param player the user you want to unban
     */
    public void unBanUser(OfflinePlayer player);

    /**
     * This will unmute the user
     * @param player The user ou want to unmute
     */
    public void unMuteUser(OfflinePlayer player);

    /**
     * This will unjail the user
     * @param player the user you want to unjail
     */
    public void unJailUser(OfflinePlayer player);
}

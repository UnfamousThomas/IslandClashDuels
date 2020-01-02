package me.unfamousthomas.IslandClashDuels.listeners;

import me.unfamousthomas.IslandClashDuels.IslandClashDuels;
import me.unfamousthomas.IslandClashDuels.user.User;
import me.unfamousthomas.IslandClashDuels.user.UserState;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class onQuitListener implements Listener {
	private IslandClashDuels instance = IslandClashDuels.getInstance();
	@EventHandler
	public void onQuitEvent(PlayerQuitEvent event) {
		if(instance.getUserManager().getUser(event.getPlayer().getUniqueId()).getState() == UserState.INGAME) {
			UUID uuid = instance.getUserManager().getDuelsHashMap().get(event.getPlayer().getUniqueId());

			instance.getUserManager().getUser(uuid).setState(UserState.IDLE);
			instance.getUserManager().getUser(uuid).setElo(instance.getUserManager().getUser(uuid).getElo() + 1);
			instance.getServer().getPlayer(uuid).sendMessage("The other player quit, so you have won the duel. Switching back to IDLE state.");

			User user = instance.getUserManager().getUser(event.getPlayer().getUniqueId());
			String elo = ChatColor.translateAlternateColorCodes('&', "&b[" + user.getElo() + "] ");
			//instance.getServer().getPlayer(uuid).setPlayerListName(elo + ChatColor.translateAlternateColorCodes('&', "&f" + event.getPlayer().getName()));


			instance.getUserManager().getDuelsHashMap().remove(uuid);
			instance.getUserManager().getDuelsHashMap().remove(event.getPlayer().getUniqueId());
		}

		instance.getUserManager().deleteUser(event.getPlayer().getUniqueId());

		//update database

	}
}

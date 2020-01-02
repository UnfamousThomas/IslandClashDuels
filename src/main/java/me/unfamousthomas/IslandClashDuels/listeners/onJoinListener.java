package me.unfamousthomas.IslandClashDuels.listeners;

import me.unfamousthomas.IslandClashDuels.IslandClashDuels;
import me.unfamousthomas.IslandClashDuels.user.User;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onJoinListener implements Listener {
	private IslandClashDuels instance = IslandClashDuels.getInstance();
	@EventHandler
	public void onJoinEvent(PlayerJoinEvent event) {
		instance.getUserManager().loadUser(event.getPlayer().getUniqueId(), event.getPlayer().getName());
		User user = instance.getUserManager().getUser(event.getPlayer().getUniqueId());
		String elo = ChatColor.translateAlternateColorCodes('&', "&b[" + user.getElo() + "] ");

		event.getPlayer().setPlayerListName(elo + ChatColor.translateAlternateColorCodes('&', "&f" + event.getPlayer().getName()));
	}
}

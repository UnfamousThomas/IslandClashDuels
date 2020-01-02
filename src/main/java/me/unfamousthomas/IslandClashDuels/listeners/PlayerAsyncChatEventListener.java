package me.unfamousthomas.IslandClashDuels.listeners;

import me.unfamousthomas.IslandClashDuels.IslandClashDuels;
import me.unfamousthomas.IslandClashDuels.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerAsyncChatEventListener implements Listener {
	private IslandClashDuels duels = IslandClashDuels.getInstance();

	@EventHandler
	public void onPlayerAsyncChatEvent(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		String message = event.getMessage();
		User user = duels.getUserManager().getUser(event.getPlayer().getUniqueId());

		String elo = ChatColor.translateAlternateColorCodes('&', "&b[" + user.getElo() + "] ");
		Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(elo + ChatColor.translateAlternateColorCodes('&', "&3" + event.getPlayer().getName() + " &7>> " + message)));
	}
}

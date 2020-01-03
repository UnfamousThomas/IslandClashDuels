package me.unfamousthomas.IslandClashDuels.duels;

import me.unfamousthomas.IslandClashDuels.IslandClashDuels;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class RequestObject {
	IslandClashDuels instance = IslandClashDuels.getInstance();

	public RequestObject(UUID requestedUUID, UUID requesterUUID) {
		this.requestedUUID = requestedUUID;
		this.requesterUUID = requesterUUID;
		this.millis = System.currentTimeMillis();
		this.accepted = false;
		initializeRequest(this.requesterUUID, this.requestedUUID);
	}

	public UUID requesterUUID;

	public UUID requestedUUID;

	public long millis;

	public boolean accepted;

	public void initializeRequest(UUID requesterUUID, UUID requestedUUID) {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (instance.getUserManager().getUser(requestedUUID) != null) {
					instance.getUserManager().getUser(requestedUUID).removeRequestObject(requesterUUID);
					if (!accepted) {
						if (Bukkit.getPlayer(requestedUUID) != null) {
							Bukkit.getPlayer(requestedUUID).sendMessage(ChatColor.translateAlternateColorCodes('&', "&cOne of your duel invites has expired."));
						}
					}
				}
				if (instance.getUserManager().getUser(requesterUUID) != null) {
					if (!accepted) {
						if (Bukkit.getPlayer(requestedUUID) != null) {
							Bukkit.getPlayer(requestedUUID).sendMessage(ChatColor.translateAlternateColorCodes('&', "One of your duel requests has expired."));

						}
					}
				}
			}
		}.runTaskLater(instance, 600L);
	}

	public void acceptRequest() {
		this.accepted = true;
		instance.getUserManager().getUser(this.requestedUUID).removeRequestObject(this.requesterUUID);
	}
}

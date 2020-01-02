package me.unfamousthomas.IslandClashDuels.commands;

import me.unfamousthomas.IslandClashDuels.IslandClashDuels;
import me.unfamousthomas.IslandClashDuels.user.User;
import me.unfamousthomas.IslandClashDuels.user.UserManager;
import me.unfamousthomas.IslandClashDuels.user.UserState;
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class DuelCommand implements CommandExecutor {
	private IslandClashDuels islandClashDuels = IslandClashDuels.getInstance();
	private UserManager userManager = islandClashDuels.getUserManager();
	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
				if(args.length == 0) {
					acceptRequest(player);
				} else if(args.length == 1) {
					String target = args[0];
					sendRequest(player, target);
				}
			}

		return true;
	}

	private void acceptRequest(Player player) {
		List<UUID> requestList = userManager.getUser(player.getUniqueId()).getRequestList();
		if (userManager.getUser(player.getUniqueId()).getState() != UserState.IDLE) {
			player.sendMessage((ChatColor.translateAlternateColorCodes('&', "You cannot accept duel requests right now!")));
			return;
		} else {
			if (requestList.size() > 0) {
				for (int i = 0; requestList.size() > i; i++) {
					if (userManager.getUser(requestList.get(i)) != null && userManager.getUser(requestList.get(i)).getState() == UserState.IDLE) {
						userManager.getUser(requestList.get(i)).setState(UserState.INGAME);
						userManager.getUser(player.getUniqueId()).setState(UserState.INGAME);
						Bukkit.getPlayer(requestList.get(i)).sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDuel started!"));
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDuel started!"));
						safeRandomTp(player, Bukkit.getPlayer(requestList.get(i)));
						userManager.getDuelsHashMap().put(player.getUniqueId(),Bukkit.getPlayer(requestList.get(i)).getUniqueId());
						userManager.getDuelsHashMap().put(Bukkit.getPlayer((requestList.get(i))).getUniqueId(), player.getUniqueId());
						//Yes, ik - I should use a Request object here rather then Hashmap - But don't have that sort of free time
						requestList.remove(i);
						break;
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "You do not have any valid duel requests."));

					}
					i++;
				}
				return;
			} else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bNo requests were found."));
				return;
			}
		}
	}

	private void sendRequest(Player player, String target) {
		//Check if player is on the network
		if (Bukkit.getServer().getPlayer(target) == null) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer named " + target + " was not found."));
		} else if(userManager.getUser(player.getUniqueId()).getState() != UserState.IDLE) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are in a duel. Cannot send the request."));
		}
			else {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSending your request to: " + target));
			UUID uuid = Bukkit.getServer().getPlayer(target).getUniqueId();
			new BukkitRunnable() {
				@Override
				public void run() {
					User targetUser = userManager.getUser(uuid);
					if(targetUser.getState() != UserState.IDLE) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCould not send request, user is not in IDLE state"));
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSent!"));
						targetUser.addRequestList(player.getUniqueId());
						Bukkit.getServer().getPlayer(target).sendMessage(ChatColor.translateAlternateColorCodes('&', "&cReceived duel request from: " + player.getName()));
					}
				}
			}.runTaskLater(islandClashDuels, 20L);

		}
	}

	private void safeRandomTp(Player player, Player player2) {
		Random r = new Random();
		int x = r.nextInt(1000);
		int z = r.nextInt(1000);

		int Y1 = Bukkit.getWorld("world").getHighestBlockYAt(new Location(Bukkit.getWorld("world"), x, 100, z));
		player.teleport(new Location(Bukkit.getWorld("world"), x, Y1, z));

		int Y2 = Bukkit.getWorld("world").getHighestBlockYAt(new Location(Bukkit.getWorld("world"), x + 50, 100, z));
		player2.teleport(new Location(Bukkit.getWorld("world"), x + 50, Y2, z));
	}

	public Long generatePoisson() {
		//My horrible attempt at making Poisson values - FYI I had no idea these were ven a thing before this
		Random double1 = new Random(100);
		Random double2 = new Random(130);
		PoissonDistribution poissonDistribution = new PoissonDistribution(double1.nextDouble(), double2.nextDouble());
		double variance = poissonDistribution.getNumericalVariance();
		long l = (new Double(variance)).longValue();
		System.out.println(l);
		 return l;
	}
}

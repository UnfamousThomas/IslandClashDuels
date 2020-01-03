package me.unfamousthomas.IslandClashDuels.commands;

import me.unfamousthomas.IslandClashDuels.IslandClashDuels;
import me.unfamousthomas.IslandClashDuels.duels.RequestObject;
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

import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class DuelCommand implements CommandExecutor {

	private IslandClashDuels islandClashDuels = IslandClashDuels.getInstance();
	private UserManager userManager = islandClashDuels.getUserManager();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 0) {
				acceptRequest(player);
			} else if (args.length == 1) {
				String target = args[0];
				sendRequest(player, target);
			}
		}

		return true;
	}

	private void sendRequest(Player player, String target) {
		Player targetPlayer = Bukkit.getPlayer(target);
		if (targetPlayer == null) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cError finding player."));
			return;
		}
		UserState playerState = islandClashDuels.getUserManager().getUser(player.getUniqueId()).getState();
		if (targetPlayer == player) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDont duel yourself. Dummy!"));
			return;
		}
		if (playerState != UserState.IDLE) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cError: either target or original user is not in IDLE state."));
			return;
		}
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSending request!"));

		new BukkitRunnable() {
			@Override
			public void run() {
				UserState targetState = islandClashDuels.getUserManager().getUser(targetPlayer.getUniqueId()).getState();

				if (targetState != UserState.IDLE) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "Error sending request: user is not idle!"));
					return;
				}

				if (islandClashDuels.getUserManager().getUser(targetPlayer.getUniqueId()).getRequestObject().containsKey(player.getUniqueId())) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cInvite already active."));
					return;
				}

				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSent!"));
				targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aReceived duel request from: " + player.getName()));

				islandClashDuels.getUserManager().getUser(targetPlayer.getUniqueId()).addRequestObject(player.getUniqueId(), new RequestObject(targetPlayer.getUniqueId(), player.getUniqueId()));
			}
		}.runTaskLater(islandClashDuels, 20L);
	}

	private void acceptRequest(Player accepter) {
		UserState accepterState = islandClashDuels.getUserManager().getUser(accepter.getUniqueId()).getState();

		if(accepterState != UserState.IDLE) {
			accepter.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cYou cannot duel while not in a idle state!"));
			return;
		}
		if(userManager.getUser(accepter.getUniqueId()).getRequestObject().isEmpty()) {
			accepter.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo requests found."));
			return;
		}
		int size = userManager.getUser(accepter.getUniqueId()).getRequestObject().size();
		RequestObject lastValue = (RequestObject) userManager.getUser(accepter.getUniqueId()).getRequestObject().values().toArray()[size - 1];
		for(Map.Entry<UUID, RequestObject> entry : userManager.getUser(accepter.getUniqueId()).getRequestObject().entrySet()) {
			UUID key = entry.getKey();
			RequestObject value = entry.getValue();
			User user = islandClashDuels.getUserManager().getUser(key);
			if(user == null && value == lastValue) {
				value.accepted = true;
				userManager.getUser(accepter.getUniqueId()).removeRequestObject(key);

			} else if(user != null && user.getState() == UserState.IDLE) {
				value.acceptRequest();
				accepter.sendMessage(ChatColor.translateAlternateColorCodes('&',"&bAccepted duel request from: &4" + Bukkit.getPlayer(key).getName()));
				userManager.getUser(accepter.getUniqueId()).setState(UserState.INGAME);
				userManager.getUser(key).setState(UserState.INGAME);
				userManager.getDuelsHashMap().put(key, accepter.getUniqueId());
				userManager.getDuelsHashMap().put(accepter.getUniqueId(), key);
				safeRandomTp(accepter, Bukkit.getPlayer(key));
				break;
			}
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

package me.unfamousthomas.IslandClashDuels;


import me.unfamousthomas.IslandClashDuels.commands.DuelCommand;
import me.unfamousthomas.IslandClashDuels.listeners.PlayerAsyncChatEventListener;
import me.unfamousthomas.IslandClashDuels.listeners.PlayerJoinEventListener;
import me.unfamousthomas.IslandClashDuels.listeners.PlayerQuitEventListener;
import me.unfamousthomas.IslandClashDuels.user.UserManager;
import me.unfamousthomas.IslandClashDuels.utils.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class IslandClashDuels extends JavaPlugin {
	private static IslandClashDuels instance;
	private UserManager userManager;

	@Override
	public void onEnable() {
		Logger.log(Logger.Level.INFO, "Plugin has been enabled.");
		instance = this;
		userManager = new UserManager();
		this.getCommand("duel").setExecutor(new DuelCommand());
		getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuitEventListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerAsyncChatEventListener(), this);


	}

	@Override
	public void onDisable() {
		Logger.log(Logger.Level.INFO, "Plugin has been disabled.");
		instance = null;
	}


	public static IslandClashDuels getInstance() {
		return instance;
	}

	public UserManager getUserManager() {
		return userManager;
	}


}

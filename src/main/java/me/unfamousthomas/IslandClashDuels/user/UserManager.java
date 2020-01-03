package me.unfamousthomas.IslandClashDuels.user;

import java.util.HashMap;
import java.util.UUID;

public class UserManager {
	private UserManager instance;
	private HashMap<UUID, User> userHashMap = new HashMap<>();
	private HashMap<UUID, UUID> duelsHashMap = new HashMap<>();
	public UserManager() {
		instance = this;
	}

	public void loadUser(UUID uuid, String IGN) {
		//In a real life situation you would load some data from DB - elo, rank etc & update last playtime
		instance.userHashMap.put(uuid, new User(uuid, IGN, 0));
	}

	public User getUser(UUID uuid) {
		return instance.userHashMap.get(uuid);
	}

	public void deleteUser(UUID uuid) {
		instance.userHashMap.remove(uuid);
	}

	public HashMap<UUID, User> getUserHashMap() {
		return userHashMap;
	}

	public HashMap<UUID, UUID> getDuelsHashMap() {
		return duelsHashMap;
	}
}

package me.unfamousthomas.IslandClashDuels.user;

import me.unfamousthomas.IslandClashDuels.duels.RequestObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class User {

	public User(UUID uuid, String IGN, Integer elo) {
		this.state = UserState.IDLE;
		this.uuid = uuid;
		this.IGN = IGN;
		this.elo = elo;
	}
	private HashMap<UUID, RequestObject> requestObject = new HashMap<>();
	private UserState state;
	private UUID uuid;
	private String IGN;
	private Integer elo;

	public HashMap<UUID, RequestObject> getRequestObject() {
		return requestObject;
	}

	public void setRequestObject(HashMap<UUID, RequestObject> requestObject) {
		this.requestObject = requestObject;
	}

	public void addRequestObject(UUID uuid, RequestObject object) {
		this.requestObject.put(uuid, object);
	}

	public void removeRequestObject(UUID uuid) {
		this.requestObject.remove(uuid);
	}

	public Integer getElo() {
		return elo;
	}

	public String getIGN() {
		return IGN;
	}

	public UserState getState() {
		return state;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setElo(Integer elo) {
		this.elo = elo;
	}

	public void setIGN(String IGN) {
		this.IGN = IGN;
	}

	public void setState(UserState state) {
		this.state = state;
	}

}

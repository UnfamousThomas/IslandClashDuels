package me.unfamousthomas.IslandClashDuels.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {

	public User(UUID uuid, String IGN, Integer elo) {
		this.state = UserState.IDLE;
		this.uuid = uuid;
		this.IGN = IGN;
		this.elo = elo;
	}
	private List<UUID> requestList = new ArrayList<>();
	private UserState state;
	private UUID uuid;
	private String IGN;
	private Integer elo;

	public List<UUID> getRequestList() {
		return requestList;
	}

	public void setRequestList(List<UUID> requestList) {
		this.requestList = requestList;
	}

	public void addRequestList(UUID uuid) {
		this.requestList.add(uuid);
	}

	public void removeRequestList(UUID uuid) {
		this.requestList.remove(uuid);
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

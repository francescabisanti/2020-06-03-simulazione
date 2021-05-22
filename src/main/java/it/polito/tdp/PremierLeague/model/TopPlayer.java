package it.polito.tdp.PremierLeague.model;

import java.util.List;

public class TopPlayer {
	Player topPlayer;
	List <Opponents> opponenti;
	
	public Player getTopPlayer() {
		return topPlayer;
	}
	public void setTopPlayer(Player topPlayer) {
		this.topPlayer = topPlayer;
	}
	public List<Opponents> getOpponenti() {
		return opponenti;
	}
	public void setOpponenti(List<Opponents> opponenti) {
		this.opponenti = opponenti;
	}
	
	
}

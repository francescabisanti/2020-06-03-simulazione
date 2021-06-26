package it.polito.tdp.PremierLeague.model;

import java.util.List;

public class TopPlayer {
	private Player giocatore;
	private List <Avversari> avversari;
	public TopPlayer(Player giocatore, List<Avversari> avversari) {
		super();
		this.giocatore = giocatore;
		this.avversari = avversari;
	}
	public Player getGiocatore() {
		return giocatore;
	}
	public void setGiocatore(Player giocatore) {
		this.giocatore = giocatore;
	}
	public List<Avversari> getAvversari() {
		return avversari;
	}
	public void setAvversari(List<Avversari> avversari) {
		this.avversari = avversari;
	}
	
	
	
}

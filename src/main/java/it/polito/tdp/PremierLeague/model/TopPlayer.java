package it.polito.tdp.PremierLeague.model;

import java.util.List;

public class TopPlayer {
	private Player top;
	private List <Avversari> avversari;
	public TopPlayer(Player top, List<Avversari> avversari) {
		super();
		this.top = top;
		this.avversari = avversari;
	}
	public Player getTop() {
		return top;
	}
	public void setTop(Player top) {
		this.top = top;
	}
	public List<Avversari> getAvversari() {
		return avversari;
	}
	public void setAvversari(List<Avversari> avversari) {
		this.avversari = avversari;
	}
	@Override
	public String toString() {
		return "TopPlayer = " + top + ", avversari=" + avversari.toString() + "\n";
	}
	
	
	
}

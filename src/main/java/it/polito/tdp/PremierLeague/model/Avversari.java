package it.polito.tdp.PremierLeague.model;

public class Avversari implements Comparable <Avversari>{
	private Player player;
	private Double peso;
	public Avversari(Player player, Double peso) {
		super();
		this.player = player;
		this.peso = peso;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Avversari o) {
		
		return o.peso.compareTo(this.peso);
	}
	@Override
	public String toString() {
		return player.toString() + " =  " + peso + "\n";
	}
	
	

}

package it.polito.tdp.PremierLeague.model;

public class Opponents implements Comparable <Opponents>{
	Player player;
	Integer peso;
	public Opponents(Player player, Integer peso) {
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
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
	
	@Override
	public int compareTo(Opponents o) {
		
		return (-1)*this.peso.compareTo(o.peso);
	}
	 
	
	@Override
	public String toString() {
		return player + " | " + peso;
	}
	
	
	
	

}

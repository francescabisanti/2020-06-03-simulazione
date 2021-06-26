package it.polito.tdp.PremierLeague.model;

public class Avversari implements Comparable <Avversari> {
	private Player a;
	private Double peso;
	public Avversari(Player a, Double peso) {
		super();
		this.a = a;
		this.peso = peso;
	}
	public Player getA() {
		return a;
	}
	public void setA(Player a) {
		this.a = a;
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
		return a.getPlayerID() + " "+ a.getName() + "| "+peso + "\n";
	}
	
	

}

package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	PremierLeagueDAO dao;
	SimpleWeightedGraph <Player, DefaultWeightedEdge>grafo;
	Map <Integer, Player> idMap;
	List <Player> migliore;
	int bestDegree=0;
	public Model() {
		dao= new PremierLeagueDAO();
		
	}
	
	public void creaGrafo(double goal) {
		idMap= new HashMap <Integer,Player>();
		grafo= new SimpleWeightedGraph <Player, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		dao.getVertici(goal, idMap);
		Graphs.addAllVertices(this.grafo, idMap.values());
		for(Adiacenza a: dao.getAdiacenze(idMap)) {
			if(grafo.vertexSet().contains(a.getP1())&&grafo.vertexSet().contains(a.getP2())) {
				if(a.getPeso()>0) {
					Graphs.addEdgeWithVertices(this.grafo, a.getP1(), a.getP2(), a.getPeso());
					
				}
				else if(a.getPeso()<0) {
					double peso=((double)-1)*(a.getPeso());
					Graphs.addEdgeWithVertices(this.grafo, a.getP2(), a.getP1(), peso);
					
				}
			}
		}
	}
	
	public TopPlayer getTopPlayer() {
		if(grafo== null)
			return null;
		
		Player best = null;
		Integer maxDegree = Integer.MIN_VALUE;
		for(Player p : grafo.vertexSet()) {
			if(grafo.outDegreeOf(p) > maxDegree) {
				maxDegree = grafo.outDegreeOf(p);
				best = p;
			}
		}
		
		TopPlayer topPlayer = new TopPlayer();
		topPlayer.setTopPlayer(best);
		
		List<Opponents> opponents = new ArrayList<>();
		for(DefaultWeightedEdge edge : grafo.outgoingEdgesOf(topPlayer.getTopPlayer())) {
			opponents.add(new Opponents(grafo.getEdgeTarget(edge), (int) grafo.getEdgeWeight(edge)));
		}
		Collections.sort(opponents);
		topPlayer.setOpponenti(opponents);
		return topPlayer;
		
	}
	
	public int numeVertici() {
		return this.grafo.vertexSet().size();
	}
	public int numArchi() {
		return this.grafo.edgeSet().size();
	}
	
	
	public List <Player> trovaSquadraVincente(int k){
		this.bestDegree=0;
		migliore= new LinkedList <Player>();
		List <Player> parziale= new ArrayList <Player>();
		
		cerca(parziale,new ArrayList<Player>(this.grafo.vertexSet()),k);
		
		return migliore;
	}

	private void cerca(List<Player> parziale, List <Player> giocatori, int k) {
		//caso terminale
        //--> parziale.size==k
		if(parziale.size()==k) {
			int grado=this.gradoTitolarita(parziale);
			if(grado>bestDegree) {
				migliore= new ArrayList<>(parziale);
				this.bestDegree=grado;
				
			}
			return;
		}
		for(Player p: giocatori) {
			if(!parziale.contains(p)) {
				parziale.add(p);
				List<Player> restanti = new ArrayList<>(giocatori);
				restanti.removeAll(Graphs.successorListOf(grafo, p));
				this.cerca(parziale, restanti,k);
				parziale.remove(p);
			}
		}
		
	}
	
	public int gradoTitolarita(List <Player> parziale) {
		int grado=0;
		for(Player p:parziale) {
			int pesoSomma=0;
			int pesoTolgo=0;
			for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(p)) {
				pesoSomma=(int) (pesoSomma+this.grafo.getEdgeWeight(e));
			}
			for(DefaultWeightedEdge e: this.grafo.incomingEdgesOf(p)) {
				pesoTolgo=(int) (pesoTolgo+this.grafo.getEdgeWeight(e));
			}
			grado=pesoSomma-pesoTolgo;
		}
		return grado;
	}
	public Graph<Player, DefaultWeightedEdge> getGrafo(){
		return grafo;
	}
	
}

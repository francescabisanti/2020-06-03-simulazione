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
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	SimpleDirectedWeightedGraph <Player, DefaultWeightedEdge> grafo;
	PremierLeagueDAO dao;
	Map <Integer, Player> idMap;
	List <Player> totale;
	List <Player> migliore;
	public Model() {
		dao= new PremierLeagueDAO();
		idMap= new HashMap<Integer, Player>();
		dao.listAllPlayers(idMap);
		totale= new ArrayList<>();
	}
	
	public void creaGrafo(Double goal) {
		grafo= new SimpleDirectedWeightedGraph <Player, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, dao.getVertici(idMap, goal));
		totale= dao.getVertici(idMap, goal);
		for(Adiacenza a: dao.getAdiacenza(idMap, goal)) {
			if(grafo.containsVertex(a.getP1())&& grafo.containsVertex(a.getP2())) {
				Graphs.addEdge(this.grafo, a.getP1(), a.getP2(), a.getPeso());
				
			}
		}
	}
	
	public TopPlayer migliore() {
		TopPlayer p=null;
		Double pesoMax=0.0;
		Player best=null;
		for(Player e: this.grafo.vertexSet()) {
			if(grafo.outDegreeOf(e)>pesoMax) {
				best=e;
				pesoMax=(double) grafo.outDegreeOf(e);
			}
		}
		List<Avversari> avversari= new ArrayList<>();
		for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(best)) {
			avversari.add(new Avversari(grafo.getEdgeTarget(e), grafo.getEdgeWeight(e)));
			
		}
		Collections.sort(avversari);
		p= new TopPlayer(best, avversari);
		return p;
	}
	
	public List<Player> trovaDreamTeam(Integer K){
		this.migliore= new ArrayList<>();
		List<Player> parziale= new ArrayList<>();
		List <Player> rimanenti= new ArrayList <Player>(this.grafo.vertexSet());
		
		parziale.add(this.gradoMax());
		List<Avversari> battuti= new ArrayList<Avversari>(this.migliore().getAvversari());
		for(Avversari a: battuti) {
			rimanenti.remove(a.getPlayer());
		}
		cerca(K, parziale, rimanenti);
		return migliore;
		
	}
	
	private void cerca(Integer k, List<Player> parziale, List<Player> totale) {
		if(parziale.size()==k) {
			if(this.grado(parziale)>this.grado(migliore)) {
				migliore=new ArrayList<>(parziale);
				
			}
			return;
			
			}
			for(Player p: totale) {
				if(!parziale.contains(p)) {
					parziale.add(p);
					List<Player>restanti= new ArrayList <Player>(totale);
					restanti.removeAll(Graphs.successorListOf(grafo, p));
					cerca(k,parziale, restanti);
					parziale.remove(p);
				}
			}
			
		
		
	}

	private Double grado(List<Player> parziale) {
		
		Double grado=0.0;
		for(Player p: this.grafo.vertexSet()) {
			Double pesoIn=0.0;
			Double pesoOut=0.0;
			for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(p)) {
				pesoOut=pesoOut+this.grafo.getEdgeWeight(e);
			}
			for(DefaultWeightedEdge e: this.grafo.incomingEdgesOf(p)) {
				pesoIn=pesoIn+this.grafo.getEdgeWeight(e);
			}
			grado=grado+pesoOut-pesoIn;
		}
		return grado;
	}
	
	private Player gradoMax() {
		Player max=null;
		Double gradoMax=0.0;
		for(Player p: grafo.vertexSet()) {
			Double gradoOut=0.0;
			Double gradoIn=0.0;
			for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(p)) {
				gradoOut=gradoOut+this.grafo.getEdgeWeight(e);
			}
			for(DefaultWeightedEdge e: this.grafo.incomingEdgesOf(p)) {
				gradoIn=gradoIn+this.grafo.getEdgeWeight(e);
			}
			if((gradoOut-gradoIn)>gradoMax) {
				max=p;
				gradoMax=gradoOut-gradoIn;
			}
		}
		return max;
	}

	public SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	
	public PremierLeagueDAO getDao() {
		return dao;
	}

	

	public Map<Integer, Player> getIdMap() {
		return idMap;
	}

	

	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
}

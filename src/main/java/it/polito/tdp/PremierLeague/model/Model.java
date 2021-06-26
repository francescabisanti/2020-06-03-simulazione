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
	SimpleDirectedWeightedGraph <Player, DefaultWeightedEdge>grafo;
	List <Player> migliore;
	int gradoMigliore;
	PremierLeagueDAO dao;
	Map <Integer, Player> idMap;
	
	public Model() {
		dao= new PremierLeagueDAO();
		idMap=new HashMap <Integer, Player>();
		dao.listAllPlayers(idMap);
	}
	
	public void creaGrafo (double goal) {
		grafo= new SimpleDirectedWeightedGraph <Player, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, dao.getVertici(goal, idMap));
		for(Adiacenza a: dao.getAdiacenze(goal, idMap)) {
			if(grafo.containsVertex(a.getP1())&&grafo.containsVertex(a.getP2())) {
				Graphs.addEdge(this.grafo, a.getP1(), a.getP2(), a.getPeso());
			}
		}
			
	}
	
	public TopPlayer migliore() {
		Double gradoOut=0.0;
		List <Avversari> result= new ArrayList <Avversari>();
		Player migliore=null;
		for(Player p: grafo.vertexSet()) {
			if(grafo.outDegreeOf(p)>gradoOut) {
				gradoOut=(double) grafo.outDegreeOf(p);
				migliore=p;
			}
			
		}
		for(DefaultWeightedEdge e: grafo.outgoingEdgesOf(migliore)) {
			Player avversario= Graphs.getOppositeVertex(grafo, e, migliore);
			Double peso= grafo.getEdgeWeight(grafo.getEdge(migliore, avversario));
			Avversari a= new Avversari(avversario, peso);
			result.add(a);
		}
		Collections.sort(result);
		TopPlayer t= new TopPlayer (migliore, result);
		return t;
	}
	public List <Player> trovaComboMigliore(int k){
		this.migliore= new ArrayList <Player>();
		this.gradoMigliore=0;
		List <Player> parziale= new ArrayList <>();
		
		cerca(parziale, k, new ArrayList <Player>(this.grafo.vertexSet()));
		return migliore;
	}
	
	
	private void cerca(List<Player> parziale, int k, List <Player>giocatori) {
		// TODO Auto-generated method stub
		if(parziale.size()==k) {
		if(this.calcoloGrado(parziale)>this.calcoloGrado(migliore)) {
			migliore=new ArrayList<>(parziale);
			this.gradoMigliore=calcoloGrado(parziale);
		}
		return;
		
		}
		for(Player p: giocatori) {
			if(!parziale.contains(p)) {
				parziale.add(p);
				List<Player>restanti= new ArrayList <Player>(giocatori);
				restanti.removeAll(Graphs.successorListOf(grafo, p));
				cerca(parziale, k, restanti);
				parziale.remove(p);
			}
		}
		
	}
	
	public Player gradoTitMax() {
		int grado=0;
		int gradoOut=0;
		int gradoIn=0;
		int gradoMax=0;
		Player migliore=null;
		for(Player p: grafo.vertexSet()) {
			for(DefaultWeightedEdge e : grafo.outgoingEdgesOf(p)) {
				gradoOut=(int) (gradoOut+grafo.getEdgeWeight(e));
			}
			for(DefaultWeightedEdge ee: grafo.incomingEdgesOf(p)) {
				gradoIn=(int) (gradoIn+grafo.getEdgeWeight(ee));
			}
			grado= grado+gradoOut-gradoIn;
			if(grado>gradoMax) {
				gradoMax=grado;
				migliore=p;
			}
			
		}
		return migliore;
	}
	
	public int calcoloGrado(List<Player>lista) {
		int grado=0;
		int gradoOut=0;
		int gradoIn=0;
		for(Player p: lista) {
			for(DefaultWeightedEdge e : grafo.outgoingEdgesOf(p)) {
				gradoOut=(int) (gradoOut+grafo.getEdgeWeight(e));
			}
			for(DefaultWeightedEdge ee: grafo.incomingEdgesOf(p)) {
				gradoIn=(int) (gradoIn+grafo.getEdgeWeight(ee));
			}
			grado= grado+gradoOut-gradoIn;
		}
		return grado;
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
		return grafo.vertexSet().size();
	}
	public int getNArchi() {
		return grafo.edgeSet().size();
	}
}

package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Adiacenza;
import it.polito.tdp.PremierLeague.model.Player;

public class PremierLeagueDAO {
	
	public void listAllPlayers(Map <Integer, Player> idMap){
		String sql = "SELECT * FROM Players";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				
				idMap.put(player.getPlayerID(), player);
			}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List <Player> getVertici (Double goal, Map <Integer, Player> idMap){
		String sql = "SELECT  p.PlayerID as id, AVG (a.Goals) AS peso "
				+ "FROM players p, actions a "
				+ "WHERE p.PlayerID=a.PlayerID "
				+ "GROUP BY p.PlayerID "
				+ "HAVING peso> ? ";
		List<Player> result= new ArrayList <Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1,goal);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = idMap.get(res.getInt("id"));
				if(player!=null)
					result.add(player);
				
				
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List <Adiacenza> getAdiacenze (Double goal, Map <Integer, Player> idMap){
		String sql = "SELECT  a1.PlayerID AS p1, a2.PlayerID AS p2, (SUM(a1.TimePlayed)-SUM(a2.TimePlayed)) AS t2 "
				+ "FROM actions a1, actions a2 "
				+ "WHERE a1.PlayerID> a2.PlayerID AND a1.TeamID!=a2.TeamID AND a1.`Starts`=1 AND a2.`Starts`=1 AND a1.MatchID=a2.MatchID "
				+ "GROUP BY a1.PlayerID, a2.PlayerID ";
		List<Adiacenza> result= new ArrayList <Adiacenza>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player1 = idMap.get(res.getInt("p1"));
				Player player2 = idMap.get(res.getInt("p2"));
				if(player1!=null && player2!=null) {
					double peso= res.getDouble("t2");
					if(peso>0) {
						Adiacenza a= new Adiacenza(player1,player2,peso);
						result.add(a);
					}
					else if(peso<0) {
						Adiacenza a= new Adiacenza (player2, player1, Math.abs(peso));
						result.add(a);
					}
				}
					
				
				
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

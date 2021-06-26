/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Avversari;
import it.polito.tdp.PremierLeague.model.Model;

import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.TopPlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnTopPlayer"
    private Button btnTopPlayer; // Value injected by FXMLLoader

    @FXML // fx:id="btnDreamTeam"
    private Button btnDreamTeam; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="txtGoals"
    private TextField txtGoals; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	String gS= this.txtGoals.getText();
    	Double goals;
    	try {
    		goals=Double.parseDouble(gS);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserisci un numero di goal valido!");
    		return;
    	}
    	if(gS.equals("")) {
    		this.txtResult.setText("Compila il campo di goals!");
    		return;
    		
    	}
    	this.model.creaGrafo(goals);
    	this.txtResult.appendText("#VERTICI: "+this.model.getNVertici()+"\n");
    	this.txtResult.appendText("#ARCHI: "+this.model.getNArchi()+"\n");
    	
    	
    }

    @FXML
    void doDreamTeam(ActionEvent event) {
    	this.txtResult.clear();
    	String kS= this.txtK.getText();
    	int k;
    	try {
    		k=Integer.parseInt(kS);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserisci un valore numerico valido!");
    		return;
    	}
    	if(kS==null) {
    		this.txtResult.setText("Inserisci un valore dei goals!");
    		return;
    	}
    	List <Player> dream= this.model.trovaComboMigliore(k);
    	for(Player p: dream) {
    		this.txtResult.appendText(p.toString()+"\n");
    	}
    	this.txtResult.appendText("Grado titolarità: "+this.model.calcoloGrado(dream));
    	
    	
    	
    	
    }

    @FXML
    void doTopPlayer(ActionEvent event) {
    	this.txtResult.clear();
    	if(model.getGrafo()==null) {
    		this.txtResult.setText("Devi prima creare il grafo");
    		return;
    		
    	}
    	TopPlayer p= model.migliore();
    	this.txtResult.appendText("TOP PLAYER: "+p.getGiocatore().getPlayerID()+" "+p.getGiocatore().getName()+"\n AVVERSARI BATTUTI: \n");
    	for(Avversari a:p.getAvversari()) {
    		this.txtResult.appendText(a.toString());
    	}
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTopPlayer != null : "fx:id=\"btnTopPlayer\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGoals != null : "fx:id=\"txtGoals\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}

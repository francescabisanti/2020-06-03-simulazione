/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Opponents;
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
    	String num= this.txtGoals.getText();
    	double goal;
    	try {
    		goal= Double.parseDouble(num);
    	}catch(NumberFormatException e ) {
    		this.txtResult.setText("Inserisci un valore numerico!");
    		return;
    	}
    	
    	this.model.creaGrafo(goal);
    	this.txtResult.appendText("GRAFO CREATO \n ");
    	this.txtResult.appendText("#VERTICI: "+this.model.numeVertici()+"\n");
    	this.txtResult.appendText("#ARCHI: "+this.model.numArchi()+"\n");
    }

    @FXML
    void doDreamTeam(ActionEvent event) {
    	this.txtResult.clear();
    	/*for(Player p: this.model.getGrafo().vertexSet()) {
    	this.txtResult.appendText(p.toString()+"\n");
    	}*/
    	if(this.model.getGrafo() == null) {
    		txtResult.clear();
    		txtResult.appendText("Crea prima il grafo!\n");
    		return;
    	}
    	
    	String kS= this.txtK.getText();
    	int k=Integer.parseInt(kS);
    	List <Player> dream=this.model.trovaSquadraVincente(k);
    	
    	for(Player p:dream) {
    		this.txtResult.appendText(p.toString()+"\n");
    	}
    	this.txtResult.appendText("GRADO: "+this.model.gradoTitolarita(dream));
    }

    @FXML
    void doTopPlayer(ActionEvent event) {
    	txtResult.clear();
    	TopPlayer topPlayer = this.model.getTopPlayer();
    	if(topPlayer == null) {
    		txtResult.appendText("Crea il grafo!");
    		return;
    	}
    	
    	txtResult.appendText("TOP PLAYER: " + topPlayer.getTopPlayer().toString());
    	txtResult.appendText("\n\nAVVERSARI BATTUTI:\n");
    	
    	for(Opponents o : topPlayer.getOpponenti()) {
    		txtResult.appendText(o.toString() + "\n");
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

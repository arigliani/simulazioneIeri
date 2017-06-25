package it.polito.tdp.formula1;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.formula1.model.Circuit;
import it.polito.tdp.formula1.model.Driver;
import it.polito.tdp.formula1.model.Model;
import it.polito.tdp.formula1.model.Race;
import it.polito.tdp.formula1.model.Season;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class Controller {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Circuit> boxCircuit;

    @FXML
    private ComboBox<?> boxDriver;

    @FXML
    private ComboBox<Season> boxSeason;

    @FXML
    private TextArea txtResult;

	private Model model;

    @FXML
    void doFantaGara(ActionEvent event) {

    }

    @FXML
    void doInfoGara(ActionEvent event) {
    	txtResult.clear();
    	Season s= this.boxSeason.getValue();
    	if (s == null) {
			txtResult.appendText("Errore: selezionare una stagione\n");
			return;
		}
    	Circuit c= this.boxCircuit.getValue();
    	if (c == null) {
			txtResult.appendText("Errore: selezionare una circuito\n");
			return;
		}
    	Race r=model.getGara(s,c);
    	List<Driver> d= model.getAllPilotaCircuiti(c, s);
    	txtResult.appendText(r.toString()+ "\n"+d.toString());

    }

    @FXML
    void initialize() {
        assert boxCircuit != null : "fx:id=\"boxCircuit\" was not injected: check your FXML file 'Formula1.fxml'.";
        assert boxDriver != null : "fx:id=\"boxDriver\" was not injected: check your FXML file 'Formula1.fxml'.";
        assert boxSeason != null : "fx:id=\"boxSeason\" was not injected: check your FXML file 'Formula1.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Formula1.fxml'.";
        
        boxSeason.valueProperty().addListener(new ChangeListener<Season>(){

			@Override
			public void changed(ObservableValue<? extends Season> observable, Season oldValue, Season newValue) {
				boxCircuit.getItems().clear();
				boxCircuit.getItems().addAll(model.getAllCircuiti(boxSeason.getValue()));
			}
        	
        });

    }

	public void setModel(Model model) {
		this.model = model ;
		boxSeason.getItems().addAll(model.getAllStagioni());
		
	}
}

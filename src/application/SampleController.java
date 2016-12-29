package application;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import bean.Corso;
import bean.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SampleController {
	
	private Model m = new Model();
	
	public void setModel(Model m ){
		this.m = m;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtMatricola;

    @FXML
    private Button btnElenco;

    @FXML
    private Button btnStudenti;

    @FXML
    private TextArea txtResult;

    @FXML
    void doElenco(ActionEvent event) {
    	txtResult.clear();
    	try{
    		int matricola = Integer.parseInt(txtMatricola.getText());
    	    if(!m.isPresente(matricola)){
    		   txtResult.appendText("La matricola non esiste nel db!\n");
    		   return;
    	      }	
    	    String nomecognome = m.dammiNome(matricola);
    	    List<Corso> corsi = m.getcorsi(matricola);
    	    txtResult.appendText("Studente : "+nomecognome+" \n");
    	    if(corsi.size()==0){
    	    	txtResult.appendText("Lo studente non è iscritto  a nessun corso!\n");
    	    	return;
    	    }
    	    for(Corso c : corsi){
    	    txtResult.appendText(c.toString()+" \n");
    	    }
    	}catch(Exception e ){
    		txtResult.appendText("Il formato non è valido");
    		return;
    	}

    }

    @FXML
    void doSimili(ActionEvent event) {
    	try{
    		int matricola = Integer.parseInt(txtMatricola.getText());
    	    if(!m.isPresente(matricola)){
    		   txtResult.appendText("La matricola non esiste nel db!\n");
    		   return;
    	      }	
    	    
    	    
//    	    List<Integer> simili = m.getStudentiConCorsiComuni(matricola);      //funziona
//    	    List<Studente> que= new LinkedList<Studente>();
//    	    for(Integer i : simili){
//    	    	Studente s = m.getStu(i);
//    	    	  if(!que.contains(s)){
//    	    	   que.add(s);
//    	    	   }   	    	
//    	    }
//    	    txtResult.appendText("Studenti che hanno almeno un corso in comune: \n ");
//    	    for(Studente st : que){
//    	        txtResult.appendText(st+" \n");
//    	       }

    	    Map<Integer, Integer> mappa = m.getMappa(matricola);
    	    List<Studente> que= new LinkedList<Studente>();
    	    for(Integer i : mappa.values()){
    	    	Studente s = m.getStu(i);
    	    	if(!que.contains(s)){
    	    		que.add(s);
    	    	}	
    	    }
    	    for(Studente st : que){
    	        txtResult.appendText(st+" \n");
    	       }
    	    List<Integer> num = new LinkedList<Integer>();
    	    for(Integer i : mappa.keySet()){
    	    	
    	    }
    	    
    	    
    	    
    	}catch(Exception e ){
    		txtResult.appendText("Il formato non è valido");
    		return;
    	}

    	    
    	

    }

    @FXML
    void initialize() {
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnElenco != null : "fx:id=\"btnElenco\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnStudenti != null : "fx:id=\"btnStudenti\" was not injected: check your FXML file 'Sample.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Sample.fxml'.";

    }
}

package application;

import java.util.List;
import java.util.Map;

import bean.Corso;
import bean.Studente;
import db.Dao;

public class Model {
	
	private Dao dao = new Dao();

	public boolean isPresente(int matricola) {
		return dao.isPresente(matricola);
	}

	public String dammiNome(int matricola){
		if(isPresente(matricola)){
			String nomeCognome = dao.getDammiStudente(matricola);
			return nomeCognome;
		}
		return null;
	}
	
	public List<Corso> getcorsi(int matricola){
		List<Corso> corsi = dao.getCorsi(matricola);
		return corsi;
	}
	
	public List<Integer> getStudentiConCorsiComuni(int matricola){
		List<Integer> stu = dao.getStudentiCheHannoAlmenoUnCorsoInComune(matricola);
		return stu;
	}
	
	public Studente getStu(int matricola){
		Studente s = dao.getDammiStudente1(matricola);
		return s;
	}
	
	public Map<Integer, Integer> getMappa(int matricola){
		Map<Integer, Integer> mappa = dao.getAltriStudentiConto(matricola);
		return mappa;
	}
}

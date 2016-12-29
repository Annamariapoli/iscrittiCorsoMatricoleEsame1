package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bean.Corso;
import bean.Studente;

public class Dao {

	public boolean isPresente(int matricola) {
		Connection conn = Dbconnect.getConnection();
		String query = "select * from studente where matricola =?";
		try{
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, matricola);
			ResultSet res = st.executeQuery();
			if(res.next()){
				return true;
			}
		}catch(SQLException e ){
			e.printStackTrace();
		}
		return false;
	}

	public String getDammiStudente(int matricola) {
		Connection conn = Dbconnect.getConnection();
		String query = "select nome, cognome from studente where matricola =?";
		try{
			String nome= null;
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, matricola);
			ResultSet res = st.executeQuery();
			if(res.next()){
				nome = res.getString("nome")+"  "+res.getString("cognome");
			}
			return nome;
		}catch(SQLException e ){
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Corso> getCorsi (int matricola){
		Connection conn = Dbconnect.getConnection();
		String query = "select c.codins, c.crediti, c.nome, c.pd from corso c, iscrizione i where c.codins=i.codins  and i.matricola=?";
		List<Corso> corsi = new LinkedList<Corso>();
		try{
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, matricola);
			ResultSet res = st.executeQuery();
			while(res.next()){
				Corso c = new Corso(res.getString("codins"), res.getInt("crediti"), res.getString("nome"), res.getInt("pd"));
				corsi.add(c);
			}
			conn.close();
			return corsi;
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> getStudentiCheHannoAlmenoUnCorsoInComune(int matricola){
		Connection conn = Dbconnect.getConnection();
		String query =" select distinct i2.matricola "
				+ "from  iscrizione i1, iscrizione i2 "
				+ "where i1.matricola=? and i1.matricola<>i2.matricola and i1.codins=i2.codins";
		List<Integer> matricole = new LinkedList<Integer>();
		try{
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, matricola);
			ResultSet res = st.executeQuery();
			while(res.next()){	
				matricole.add(res.getInt("matricola"));
			}
			conn.close();
			return matricole;
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
	}
	
	public Studente getDammiStudente1(int matricola){
		Connection conn = Dbconnect.getConnection();
		String query ="select * from studente where matricola=?";
		Studente s =null;
		try{
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, matricola);
			ResultSet res = st.executeQuery();
			if(res.next()){	
				s= new Studente(res.getInt("matricola"), res.getString("nome"), res.getString("cognome"), res.getString("cds"));
			}
			conn.close();
		}catch(SQLException e ){
			e.printStackTrace();
		}
		return s;
	}
	
	
	public Map<Integer, Integer> getAltriStudentiConto(int matricola){
		Connection conn = Dbconnect.getConnection();
		String query =" select  i2.matricola, count(i2.codins) as num "
				+ "from  iscrizione i1, iscrizione i2 "
				+ "where i1.matricola=? and i1.matricola<>i2.matricola and i1.codins=i2.codins"
				+ "group by i2.matricola order by num DESC;";
		Map<Integer, Integer> mappa = new HashMap<Integer, Integer>();
		try{
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, matricola);
			ResultSet res = st.executeQuery();
			while(res.next()){	
				mappa.put(res.getInt("matricola"), res.getInt("num"));
			}
			conn.close();
		}catch(SQLException e ){
			e.printStackTrace();
		}
		return mappa;
	}

}

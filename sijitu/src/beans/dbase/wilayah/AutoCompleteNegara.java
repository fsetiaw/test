package beans.dbase.wilayah;

import beans.dbase.SearchDb;
import beans.tools.Checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class AutoCompleteNegara
 */
@Stateless
@LocalBean
public class AutoCompleteNegara extends SearchDb {
	private int totalCountries;
	private String data = "Afghanistan,	Indonesia, Albania, Zimbabwe, Malaysia, Iran";
	private List<String> countries;
	String operatorNpm;
	String operatorNmm;
	String tknOperatorNickname;
	boolean petugas;
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;
    /**
     * @see SearchDb#SearchDb()
     */
    public AutoCompleteNegara() {
        super();
        countries = new ArrayList<String>();
        try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from wilayah where id_level_wil=0");
			rs = stmt.executeQuery();
			while(rs.next()) {
				String id_wil = rs.getString("id_wil");
				String id_neg = rs.getString("id_negara");
				String nm_neg = rs.getString("nm_wil");
				countries.add(nm_neg);
				//matched.add("Negara : "+nm_neg+"<br>Id : "+id_neg+"<br>Kode : "+id_wil);
			}
		}
    	catch (NamingException e) {
    		e.printStackTrace();
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
		//StringTokenizer st = new StringTokenizer(data, ",");
		
		//while(st.hasMoreTokens()) {
			//countries.add(st.nextToken().trim());
		//}
		totalCountries = countries.size();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public AutoCompleteNegara(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
    	countries = new ArrayList<String>();
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from wilayah where id_level_wil=0");
			rs = stmt.executeQuery();
			while(rs.next()) {
				String id_wil = rs.getString("id_wil");
				String id_neg = rs.getString("id_negara");
				String nm_neg = rs.getString("nm_wil");
				countries.add(nm_neg);
				//matched.add("Negara : "+nm_neg+"<br>Id : "+id_neg+"<br>Kode : "+id_wil);
			}
		}
    	catch (NamingException e) {
    		e.printStackTrace();
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
		//StringTokenizer st = new StringTokenizer(data, ",");
		
		//while(st.hasMoreTokens()) {
		//	countries.add(st.nextToken().trim());
		//}
		totalCountries = countries.size();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(Connection)
     */
    public AutoCompleteNegara(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    
    


    
    public List<String> getData(String query) {
		String country = null;
		query = query.toLowerCase();
		List<String> matched = new ArrayList<String>();
		for(int i=0; i<totalCountries; i++) {
			country = countries.get(i).toLowerCase();
			//if(country.startsWith(query)) {
			if(country.contains(query)) {
				matched.add(countries.get(i));
			}
		}
		return matched;
	}
}

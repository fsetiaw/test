package beans.dbase.wilayah;

import beans.dbase.SearchDb;
import beans.tools.Checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import beans.tools.*;
import org.apache.tomcat.jdbc.pool.DataSource;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;
import java.util.Collections;
/**
 * Session Bean implementation class SearchDbWilayah
 */
@Stateless
@LocalBean
public class SearchDbWilayah extends SearchDb {
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
    public SearchDbWilayah() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbWilayah(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(Connection)
     */
    public SearchDbWilayah(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

    //deprecated - pake byObjid version
    public Vector getListNegara() {
    	/*
    	 * get initial root folder yg bisa di akses oleh user 
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
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
				li.add(nm_neg);
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
    	
    	return v;
    }
    
    public Vector getListWilayah(int id_lvl_wil, String id_induk_wil) {
    	/*
    	 * get initial root folder yg bisa di akses oleh user 
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
		try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from wilayah where id_level_wil=? and id_induk_wilayah=?");
			stmt.setInt(1, id_lvl_wil);
			stmt.setString(2,id_induk_wil);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String id_wil = rs.getString("id_wil");
				//String id_neg = rs.getString("id_negara");
				String nm_wil = rs.getString("nm_wil");
				String id_ind = rs.getString("id_induk_wilayah");
				if(id_lvl_wil<3) {
					li.add(nm_wil+"`"+id_wil);	
				}
				else { 
					li.add(nm_wil+" ["+id_wil+"]"); //tingkat kecamatan
				}
				
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
    	
    	return v;
    }
    
 
}

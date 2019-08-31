package beans.dbase.feeder.importer;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.Tool;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class KapasitasImporter
 */
@Stateless
@LocalBean
public class WilayahImporter extends SearchDb {
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
    public WilayahImporter() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public WilayahImporter(String operatorNpm) {
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
    public WilayahImporter(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

    
    public Vector syncWilayah() {
    	Vector v_err = new Vector();
    	ListIterator lie = v_err.listIterator();
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/FEEDER_IMPORTER");
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from wilayah");
			rs = stmt.executeQuery();
			while(rs.next()) {
				String  id_wil = ""+rs.getString("id_wil");
				String  id_negara = ""+rs.getString("id_negara");
				String  nm_wil = ""+rs.getString("nm_wil");
				String  asal_wil = ""+rs.getString("asal_wil");
				String  kode_bps = ""+rs.getString("kode_bps");
				String  kode_dagri = ""+rs.getString("kode_dagri");
				String  kode_keu = ""+rs.getString("kode_keu");
				String  id_induk_wilayah = ""+rs.getString("id_induk_wilayah");
				String  id_level_wil = ""+rs.getInt("id_level_wil");
				if(v==null) {
					v = new Vector();
					li = v.listIterator();
				}
				li.add(id_wil+"$"+id_negara+"$"+nm_wil+"$"+asal_wil+"$"+kode_bps+"$"+kode_dagri+"$"+kode_keu+"$"+id_induk_wilayah+"$"+id_level_wil);
			}
			//System.out.println("vsize="+v.size());
			if(v!=null && v.size()>0) {
				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
	    		con = ds.getConnection();
	    		stmt = con.prepareStatement("update wilayah set id_negara=?,nm_wil=?,asal_wil=?,kode_bps=?,kode_dagri=?,kode_keu=?,id_induk_wilayah=?,id_level_wil=? where id_wil=?");
	    		li = v.listIterator();
				int j=0;
				while(li.hasNext()) {
					j++;
					String brs = (String)li.next();
					brs = brs.replace("`", "'");
					StringTokenizer st = new StringTokenizer(brs,"$");
					String  id_wil = st.nextToken();
					String  id_negara = st.nextToken();
					String  nm_wil = st.nextToken();
					String  asal_wil = st.nextToken();
					String  kode_bps = st.nextToken();
					String  kode_dagri = st.nextToken();
					String  kode_keu = st.nextToken();
					String  id_induk_wilayah = st.nextToken();
					String  id_level_wil = st.nextToken();
					int i=1;
					//id_wil
					
					//stmt.setString(i++, id_negara);
					if(Checker.isStringNullOrEmpty(id_negara)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, id_negara);	
					}
					//stmt.setString(i++, nm_wil);
					if(Checker.isStringNullOrEmpty(nm_wil)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, nm_wil);	
					}
					//stmt.setString(i++, asal_wil);
					if(Checker.isStringNullOrEmpty(asal_wil)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, asal_wil);	
					}
					//stmt.setString(i++, kode_bps);
					if(Checker.isStringNullOrEmpty(kode_bps)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, kode_bps);	
					}
					//stmt.setString(i++, kode_dagri);
					if(Checker.isStringNullOrEmpty(kode_dagri)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, kode_dagri);	
					}
					//stmt.setString(i++, kode_keu);
					if(Checker.isStringNullOrEmpty(kode_keu)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, kode_keu);	
					}
					//stmt.setString(i++, id_induk_wilayah);
					if(Checker.isStringNullOrEmpty(id_induk_wilayah)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, id_induk_wilayah);	
					}
					//stmt.setInt(i++, Integer.parseInt(id_level_wil));
					if(Checker.isStringNullOrEmpty(id_level_wil)) {
						stmt.setNull(i++, java.sql.Types.INTEGER);
					}
					else {
						stmt.setInt(i++, Integer.parseInt(id_level_wil));	
					}
					
					if(Checker.isStringNullOrEmpty(id_wil)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, id_wil);	
					}
					//System.out.println(j+"."+brs);
					i = stmt.executeUpdate();
				
					if(i>0) {
						li.remove();
					}
					
				}	
			}
			//System.out.println("vsize upd="+v.size());
			if(v!=null && v.size()>0) {
				stmt = con.prepareStatement("INSERT IGNORE INTO wilayah(id_wil,id_negara,nm_wil,asal_wil,kode_bps,kode_dagri,kode_keu,id_induk_wilayah,id_level_wil)VALUES(?,?,?,?,?,?,?,?,?)");
				li = v.listIterator();
				int j=0;
				while(li.hasNext()) {
					j++;
					String brs = (String)li.next();
					brs = brs.replace("`", "'");
					StringTokenizer st = new StringTokenizer(brs,"$");
					String  id_wil = st.nextToken();
					String  id_negara = st.nextToken();
					String  nm_wil = st.nextToken();
					String  asal_wil = st.nextToken();
					String  kode_bps = st.nextToken();
					String  kode_dagri = st.nextToken();
					String  kode_keu = st.nextToken();
					String  id_induk_wilayah = st.nextToken();
					String  id_level_wil = st.nextToken();
					int i=1;
					//id_wil
					if(Checker.isStringNullOrEmpty(id_wil)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, id_wil);	
					}
					//stmt.setString(i++, id_negara);
					if(Checker.isStringNullOrEmpty(id_negara)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, id_negara);	
					}
					//stmt.setString(i++, nm_wil);
					if(Checker.isStringNullOrEmpty(nm_wil)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, nm_wil);	
					}
					//stmt.setString(i++, asal_wil);
					if(Checker.isStringNullOrEmpty(asal_wil)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, asal_wil);	
					}
					//stmt.setString(i++, kode_bps);
					if(Checker.isStringNullOrEmpty(kode_bps)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, kode_bps);	
					}
					//stmt.setString(i++, kode_dagri);
					if(Checker.isStringNullOrEmpty(kode_dagri)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, kode_dagri);	
					}
					//stmt.setString(i++, kode_keu);
					if(Checker.isStringNullOrEmpty(kode_keu)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, kode_keu);	
					}
					//stmt.setString(i++, id_induk_wilayah);
					if(Checker.isStringNullOrEmpty(id_induk_wilayah)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, id_induk_wilayah);	
					}
					//stmt.setInt(i++, Integer.parseInt(id_level_wil));
					if(Checker.isStringNullOrEmpty(id_level_wil)) {
						stmt.setNull(i++, java.sql.Types.INTEGER);
					}
					else {
						stmt.setInt(i++, Integer.parseInt(id_level_wil));	
					}
					i = stmt.executeUpdate();
					//System.out.println("insert "+j+" = "+i);
				}	
			}
			
    		
    	}
    	catch (NamingException e) {
    		lie.add(e);
    		e.printStackTrace();
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	} 
    	catch (Exception ex) {
    		ex.printStackTrace();
    	} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	} 
    	return v_err;
    }

    
    public Vector syncNegara() {
    	Vector v_err = new Vector();
    	ListIterator lie = v_err.listIterator();
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/FEEDER_IMPORTER");
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from negara");
			rs = stmt.executeQuery();
			while(rs.next()) {
				String  id_negara = ""+rs.getString("id_negara");
				String  nm_negara = ""+rs.getString("nm_negara");
				String  a_ln = ""+rs.getDouble("a_ln");
				String  benua = ""+rs.getDouble("benua");
				if(v==null) {
					v = new Vector();
					li = v.listIterator();
				}
				li.add(id_negara+"$"+nm_negara+"$"+a_ln+"$"+benua);
			}
			//System.out.println("vsize="+v.size());
			if(v!=null && v.size()>0) {
				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
	    		con = ds.getConnection();
	    		stmt = con.prepareStatement("update negara set nm_negara=?,a_ln=?,benua=? where id_negara=?");
	    		li = v.listIterator();
				int j=0;
				while(li.hasNext()) {
					j++;
					String brs = (String)li.next();
					brs = brs.replace("`", "'");
					StringTokenizer st = new StringTokenizer(brs,"$");
					String  id_negara = st.nextToken();
					String  nm_negara = st.nextToken();
					String  a_ln = st.nextToken();
					String  benua = st.nextToken();
					int i=1;
					if(Checker.isStringNullOrEmpty(nm_negara)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, nm_negara);	
					}
					//stmt.setString(i++, nm_wil);
					if(Checker.isStringNullOrEmpty(a_ln)) {
						stmt.setNull(i++, java.sql.Types.DOUBLE);
					}
					else {
						stmt.setDouble(i++, Double.parseDouble(a_ln));	
					}
					//stmt.setString(i++, asal_wil);
					if(Checker.isStringNullOrEmpty(benua)) {
						stmt.setNull(i++, java.sql.Types.DOUBLE);
					}
					else {
						stmt.setDouble(i++, Double.parseDouble(benua));	
					}
					//stmt.setString(i++, kode_bps);
					if(Checker.isStringNullOrEmpty(id_negara)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, id_negara);	
					}
					//System.out.println(j+"."+brs);
					i = stmt.executeUpdate();
				
					if(i>0) {
						li.remove();
					}
					
				}	
			}
			//System.out.println("vsize upd="+v.size());
			if(v!=null && v.size()>0) {
				stmt = con.prepareStatement("INSERT IGNORE INTO negara(id_negara,nm_negara,a_ln,benua)VALUES(?,?,?,?)");
				li = v.listIterator();
				int j=0;
				while(li.hasNext()) {
					j++;
					String brs = (String)li.next();
					brs = brs.replace("`", "'");
					StringTokenizer st = new StringTokenizer(brs,"$");
					String  id_negara = st.nextToken();
					String  nm_negara = st.nextToken();
					String  a_ln = st.nextToken();
					String  benua = st.nextToken();
					
					int i=1;
					//id_wil
					if(Checker.isStringNullOrEmpty(id_negara)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, id_negara);	
					}
					//stmt.setString(i++, nm_wil);
					if(Checker.isStringNullOrEmpty(nm_negara)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, nm_negara);	
					}
					//stmt.setString(i++, asal_wil);
					if(Checker.isStringNullOrEmpty(a_ln)) {
						stmt.setNull(i++, java.sql.Types.DOUBLE);
					}
					else {
						stmt.setDouble(i++, Double.parseDouble(a_ln));	
					}
					if(Checker.isStringNullOrEmpty(benua)) {
						stmt.setNull(i++, java.sql.Types.DOUBLE);
					}
					else {
						stmt.setDouble(i++, Double.parseDouble(benua));	
					}
					i = stmt.executeUpdate();
					//System.out.println("insert "+j+" = "+i);
				}	
			}
			
    		
    	}
    	catch (NamingException e) {
    		lie.add(e);
    		e.printStackTrace();
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	} 
    	catch (Exception ex) {
    		ex.printStackTrace();
    	} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	} 
    	return v_err;
    }

    public Vector syncLvlWilayah() {
    	Vector v_err = new Vector();
    	ListIterator lie = v_err.listIterator();
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/FEEDER_IMPORTER");
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from level_wilayah");
			rs = stmt.executeQuery();
			while(rs.next()) {
				String  id_level_wil = ""+rs.getInt("id_level_wil");
				String  nm_level_wil = ""+rs.getString("nm_level_wilayah");
				if(v==null) {
					v = new Vector();
					li = v.listIterator();
				}
				li.add(id_level_wil+"$"+nm_level_wil);
			}
			//System.out.println("vsize="+v.size());
			if(v!=null && v.size()>0) {
				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
	    		con = ds.getConnection();
	    		stmt = con.prepareStatement("update level_wilayah set nm_level_wilayah=? where id_level_wil=?");
	    		li = v.listIterator();
				int j=0;
				while(li.hasNext()) {
					j++;
					String brs = (String)li.next();
					brs = brs.replace("`", "'");
					StringTokenizer st = new StringTokenizer(brs,"$");
					String  id_level_wil = st.nextToken();
					String  nm_level_wil = st.nextToken();
					int i=1;
					if(Checker.isStringNullOrEmpty(nm_level_wil)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, nm_level_wil);	
					}
					if(Checker.isStringNullOrEmpty(id_level_wil)) {
						stmt.setNull(i++, java.sql.Types.INTEGER);
					}
					else {
						stmt.setInt(i++, Integer.parseInt(id_level_wil));	
					}
					
					//System.out.println(j+"."+brs);
					i = stmt.executeUpdate();
				
					if(i>0) {
						li.remove();
					}
					
				}	
			}
			//System.out.println("vsize upd="+v.size());
			if(v!=null && v.size()>0) {
				stmt = con.prepareStatement("INSERT IGNORE INTO level_wilayah(id_level_wil,nm_level_wilayah)VALUES(?,?)");
				li = v.listIterator();
				int j=0;
				while(li.hasNext()) {
					j++;
					String brs = (String)li.next();
					brs = brs.replace("`", "'");
					StringTokenizer st = new StringTokenizer(brs,"$");
					String  id_level_wil = st.nextToken();
					String  nm_level_wil = st.nextToken();
					
					
					int i=1;
					//id_wil
					if(Checker.isStringNullOrEmpty(id_level_wil)) {
						stmt.setNull(i++, java.sql.Types.INTEGER);
					}
					else {
						stmt.setInt(i++, Integer.parseInt(id_level_wil));	
					}
					//stmt.setString(i++, nm_wil);
					if(Checker.isStringNullOrEmpty(nm_level_wil)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, nm_level_wil);	
					}
					
					i = stmt.executeUpdate();
					//System.out.println("insert "+j+" = "+i);
				}	
			}
			
    		
    	}
    	catch (NamingException e) {
    		lie.add(e);
    		e.printStackTrace();
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	} 
    	catch (Exception ex) {
    		ex.printStackTrace();
    	} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	} 
    	return v_err;
    }
    
}

package beans.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * Session Bean implementation class Constant
 */
@Stateless
@LocalBean
public class Constant {
	final static int at_page=1;
	final static int max_data_per_pg=5;
	final static int max_data_per_man_pg=100;
	final static String nama_pt = "Universitas Satyagama";
	final static String nama_yys = "Yayasan Satyagama";
    /**
     * Default constructor. 
     */
    public Constant() {
        // TODO Auto-generated constructor stub
    }

    
    public static String getNama_pt()	{
    	return nama_pt;
    }
    
    
    public static String getNama_yys()	{
    	return nama_pt;
    }
    
    public static int getAt_page()	{
    	return at_page;
    }
    
    public static int getMax_data_per_pg()	{
    	return max_data_per_pg;
    }
    
    public static int getMax_data_per_man_pg()	{
    	return max_data_per_man_pg;
    }
    
    public static String darkColor() {
    	return "#333";
    }
    
    public static String mildColor() {
    	return "#878787";
    }
    
    public static String lightColor() {
    	return "#EEEEEE";
    }
    
    public static String darkColorBlu() {
    	return "#369";
    }
    
    public static String mildColorBlu() {
    	return "#b3cce6";//7598BC//b3cce6//9fbfdf
    }
    
    public static String lightColorBlu() {
    	return "#d9e1e5";
    }
    
    public static String lightColorGrey() {
    	return "#DBDBDB";
    }

    public static int rangeMsgPerPage() {
    	return 21;
    }
    
    public static Vector getListGender() {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String value = getVelueFromConstantTable("GENDER");
    	StringTokenizer st = new StringTokenizer(value,"`");
    	while(st.hasMoreTokens()) {
    		li.add(st.nextToken());
    	}
    	return v;
    }
    
    public static Vector getListTipePendaftaran() {
    	/*
    	 * BARU / PINDAHAN
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String value = getVelueFromConstantTable("TIPE_MHS");
    	StringTokenizer st = new StringTokenizer(value,"`");
    	while(st.hasMoreTokens()) {
    		li.add(st.nextToken());
    	}
    	return v;
    }
    
    public static Vector getListStatusNikah() {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String value = getVelueFromConstantTable("STATUS");
    	StringTokenizer st = new StringTokenizer(value,"`");
    	while(st.hasMoreTokens()) {
    		li.add(st.nextToken());
    	}
    	return v;
    }
    
    
    public static Vector getListShiftAvail(String kdpst) {
    	Vector v = new Vector();
    	ListIterator li=v.listIterator();
    	String kdjen= Checker.getKdjen(kdpst);     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT KETERANGAN,SHIFT,KODE_KONVERSI FROM SHIFT where TOKEN_KDJEN_AVAILABILITY like ? order by KETERANGAN");
    		stmt.setString(1, "%"+kdjen+"%");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String keter = ""+rs.getString(1);
    			String shift = ""+rs.getString(2);
    			String versi = ""+rs.getString(3);
    			li.add(keter+"`"+shift+"`"+versi);
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
    
    
    public static String getVelueFromConstantTable(String keterangan) {
    	String value=null;
    	String kode=new String(keterangan.toUpperCase());     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT VALUE FROM CONSTANT where KETERANGAN=?");
    		stmt.setString(1,keterangan);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			if(value==null) {
    				value = new String(""+rs.getString(1));
    			}
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
    	return value;
    }
    
    /*
     * deprecated ganti getVelueFromConstantTable - kenapa juag retun vector
     */
    public static Vector getIt(String keterangan) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	li.add(keterangan.toUpperCase());
    	String kode=new String(keterangan.toUpperCase());     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT VALUE FROM CONSTANT where KETERANGAN=?");
    		stmt.setString(1,keterangan);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			li.add(""+rs.getString(1));
    		}
    		else {
    			li.add("null");
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
    
    public static String getValue(String keterangan) {
    	//Vector v = new Vector();
    	//ListIterator li = v.listIterator();
    	//li.add(keterangan.toUpperCase());
    	String value=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT VALUE FROM CONSTANT where KETERANGAN=?");
    		stmt.setString(1,keterangan);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			value = new String(rs.getString(1));
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
    	return value;
    }
    
    public static Vector getListNegara() {
    	Vector v = getListWilayah(0, null);
    	return v;
    }
    
    public static Vector getListProv(String nm_negara) {
    	String id_induk_wil = getIdWilayah(nm_negara);
    	//System.out.println("prop id="+id_induk_wil);
    	Vector v = getListWilayah(1, id_induk_wil);
    	return v;
    }
    
    public static Vector getListKota(String nm_prov) {
    	String id_induk_wil = getIdWilayah(nm_prov);
    	Vector v = getListWilayah(2, id_induk_wil);
    	return v;
    }
    
    public static Vector getListKota() {
    	Vector v = getListWilayah(2, null);
    	return v;
    }
    
    public static Vector getListKec(String nm_kota) {
    	String id_induk_wil = getIdWilayah(nm_kota);
    	Vector v = getListWilayah(3, id_induk_wil);
    	return v;
    }
    
    public static Vector getListWilayah(int id_lvl, String id_induk_wil) {
    	//0 negara
    	//1 Prov
    	//2 kota
    	//3 kecamatan
    	
    	Vector v = null;
    	ListIterator li = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		if(id_lvl==0 || Checker.isStringNullOrEmpty(id_induk_wil)) {
    			stmt = con.prepareStatement("SELECT id_wil,id_negara,nm_wil,id_induk_wilayah FROM wilayah where id_level_wil=?");
    			stmt.setInt(1,id_lvl);
    		}
    		else {
    			stmt = con.prepareStatement("SELECT id_wil,id_negara,nm_wil,id_induk_wilayah FROM wilayah where id_level_wil=? and id_induk_wilayah=?");
    			stmt.setInt(1,id_lvl);
        		stmt.setString(2, id_induk_wil);
    		}
    		
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			if(v==null) {
    				v = new Vector();
    				li = v.listIterator();
    			}
    			do {
    				String id_wil = ""+rs.getString(1);
        			String id_negara = ""+rs.getString(2);
        			String nm_wil = ""+rs.getString(3);
        			String id_induk_wilayah = ""+rs.getString(4);
        			li.add(id_wil.trim()+"`"+id_negara.trim()+"`"+nm_wil.trim()+"`"+id_induk_wilayah.trim());
    			}
    			while(rs.next());
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
    
    public static Vector getListPT() {
    	//0 negara
    	//1 Prov
    	//2 kota
    	//3 kecamatan
    	
    	Vector v = null;
    	ListIterator li = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT id_negara,nm_wil,nm_lemb,npsn from satuan_pendidikan a inner join wilayah b on a.id_wil=b.id_wil order by nm_lemb");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			if(v==null) {
    				v = new Vector();
    				li = v.listIterator();
    			}
    			String id_negara = ""+rs.getString(1);
        		String nm_wil = ""+rs.getString(2);
        		String nmmpt = ""+rs.getString(3);
        		String kdpti = ""+rs.getString(4);
        		
        		if(!Checker.isStringNullOrEmpty(nmmpt)) {
        			if(Checker.isStringNullOrEmpty(kdpti)) {
        				kdpti = "N/A";
        			}
        			//String tmp = id_negara.trim()+"`"+nm_wil.trim()+"`"+nmmpt.trim()+"`"+kdpti.trim();
        			//tmp = tmp.replace("``", "`null`");
        			li.add(id_negara.trim()+"`"+nm_wil.trim()+"`"+nmmpt.trim()+"`"+kdpti.trim());
        		}
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
    
    
    public static Vector getListAgama() {
    	//0 negara
    	//1 Prov
    	//2 kota
    	//3 kecamatan
    	
    	Vector v = null;
    	ListIterator li = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT nm_agama FROM agama order by nm_agama");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			if(v==null) {
    				v = new Vector();
    				li = v.listIterator();
    			}
    			do {
    				String nm_agama = ""+rs.getString(1);
        			li.add(nm_agama);
    			}
    			while(rs.next());
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

    public static Vector getListJenjangLulusan() {
    	//0 negara
    	//1 Prov
    	//2 kota
    	//3 kecamatan
    	
    	Vector v = null;
    	ListIterator li = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT KDJEN,KODE,GELAR FROM JENJANG_STUDI order by KDJEN");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			if(v==null) {
    				v = new Vector();
    				li = v.listIterator();
    			}
    			do {
    				String kdjen = ""+rs.getString(1);
    				String kode = ""+rs.getString(2);
    				String gelar = ""+rs.getString(3);
        			li.add(kdjen+"`"+kode+"`"+gelar);
    			}
    			while(rs.next());
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

    
    public static String getIdWilayah(String nm_wil) {
    	String id_will=null;
    	Vector v = null;
    	ListIterator li = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT id_wil FROM wilayah where nm_wil=?");	
    		stmt.setString(1, nm_wil);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			id_will = new String(""+rs.getString(1));
    			id_will = id_will.trim();
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
    	return id_will;
    }
}

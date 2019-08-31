package beans.dbase.spmi;

import beans.dbase.SearchDb;
import beans.tools.*;
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
 * Session Bean implementation class SearchSpmi
 */
@Stateless
@LocalBean
public class SearchSpmi extends SearchDb {
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
    public SearchSpmi() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchSpmi(String operatorNpm) {
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
    public SearchSpmi(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    
    public Vector getListStandar() {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select * from STANDARD_TABLE a inner join STANDARD_MASTER_TABLE b on a.ID_MASTER_STD=b.ID_MASTER_STD ");
        	rs = stmt.executeQuery();
        	while(rs.next()) {
        		int id_std = rs.getInt("ID_STD");
        		int id_master_std = rs.getInt("ID_MASTER_STD");
        		String ket_tipe_master = rs.getString("b.KET_TIPE_STD");
        		int id_tipe_std = rs.getInt("ID_TIPE_STD");
        		String ket_tipe_std = rs.getString("a.KET_TIPE_STD");
        		li.add(id_std+"`"+id_master_std+"`"+ket_tipe_master+"`"+id_tipe_std+"`"+ket_tipe_std);
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
    
    public String getInfoStandarAsLabel(Vector SearchSpmi_getListStandar, String target_id_std) {
    	String label = "";
    	boolean match = false;
    	ListIterator li = SearchSpmi_getListStandar.listIterator();
    	while(li.hasNext() && !match) {
    		String brs = (String)li.next();
    		if(brs.startsWith(target_id_std+"`")) {
    			match = true;
    			StringTokenizer st = new StringTokenizer(brs,"`");
        		while(st.hasMoreTokens()) {
        			String id_std = st.nextToken();
        			String id_master_std = st.nextToken();
        			String ket_tipe_master = st.nextToken();
        			String id_tipe_std = st.nextToken();
        			String ket_tipe_std = st.nextToken();
        			//get init master 
        			//st = new StringTokenizer(ket_tipe_master);
        			//while(st.hasMoreTokens()) {
        			//	String tkn = st.nextToken();
        			//	label = label + tkn.subString(0,1);
        			//}
        			label = ket_tipe_master.replace("STANDAR", "STD")+"/"+ket_tipe_std.replace("STANDAR", "");
        			
        		}	
    		}
    		
    	}
    	return label;
    }
    
    public Vector getListTipeStandar(int master_id) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select * from STANDARD_TABLE where ID_MASTER_STD=? order by KET_TIPE_STD");
        	stmt.setInt(1, master_id);
        	rs = stmt.executeQuery();
        	while(rs.next()) {
        		int id_std = rs.getInt("ID_STD");
        		int id_tipe_std = rs.getInt("ID_TIPE_STD");
        		String ket = rs.getString("KET_TIPE_STD");
        		li.add(id_std+"`"+id_tipe_std+"`"+ket);
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
    
    public Vector getListNamaDokument() {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select * from STANDARD_DOKUMEN order by NAMA_DOKUMEN");
        	rs = stmt.executeQuery();
        	while(rs.next()) {
        		//int id = rs.getInt("ID");
        		String nama = rs.getString("NAMA_DOKUMEN");
        		//String info = ""+rs.getString("INFO_DOKUMEN");
        		//if(Checker.isStringNullOrEmpty(info)) {
        		//	info = "null";
        		//}
        		li.add(nama);
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
    
    public Vector getListParameter() {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select NM_PARAM from PARAMETER_CAPAIAN order by NM_PARAM");
        	rs = stmt.executeQuery();
        	while(rs.next()) {
        		//int id = rs.getInt("ID");
        		String nama = rs.getString(1);
        		//String info = ""+rs.getString("INFO_DOKUMEN");
        		//if(Checker.isStringNullOrEmpty(info)) {
        		//	info = "null";
        		//}
        		li.add(nama);
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
    
    public Vector getListMasterStandar() {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select * from STANDARD_MASTER_TABLE order by KET_TIPE_STD");
        	rs = stmt.executeQuery();
        	while(rs.next()) {
        		int id = rs.getInt("ID_MASTER_STD");
        		String ket = rs.getString("KET_TIPE_STD");
        		li.add(id+"`"+ket);
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

    
    public int getIdStd(int id_master, int id_tipe) {
    	int id=0;
    	try {
        		//String ipAddr =  request.getRemoteAddr();
    		Vector vIns = new Vector();
    		ListIterator lins = vIns.listIterator();
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select ID_STD from STANDARD_TABLE  where ID_MASTER_STD=? and ID_TIPE_STD=?");
        	stmt.setInt(1, id_master);
        	stmt.setInt(2, id_tipe);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		id = rs.getInt(1);
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
    	return id;
    }
}

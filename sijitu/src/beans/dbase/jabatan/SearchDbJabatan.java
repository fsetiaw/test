package beans.dbase.jabatan;

import beans.dbase.SearchDb;
import beans.tools.*;
import java.sql.Connection;
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
 * Session Bean implementation class SearchDbJabatan
 */
@Stateless
@LocalBean
public class SearchDbJabatan extends SearchDb {
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
    public SearchDbJabatan() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbJabatan(String operatorNpm) {
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
    public SearchDbJabatan(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public Vector getListTitleJabatan() {
    	//Vector v = null;
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
		//	get
			stmt = con.prepareStatement("select NAMA_JABATAN,SINGKATAN from JABATAN where AKTIF=? order by TIPE_GROUP,NAMA_JABATAN");
			stmt.setBoolean(1, true);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					String  nmj = ""+rs.getString(1);
					String  sin = ""+rs.getString(2);
					li.add(nmj+"`"+sin);
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
    
    public Vector getListTitleJabatanIndividu() {
    	//Vector v = null;
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
		//	get
			stmt = con.prepareStatement("select NAMA_JABATAN,SINGKATAN from JABATAN where AKTIF=? and TIPE_GROUP=?");
			stmt.setBoolean(1, true);
			stmt.setBoolean(2, false);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					String  nmj = ""+rs.getString(1);
					String  sin = ""+rs.getString(2);
					li.add(nmj+"`"+sin);
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
    
    public Vector getListTitleJabatanKelompok() {
    	//Vector v = null;
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
		//	get
			stmt = con.prepareStatement("select NAMA_JABATAN,SINGKATAN from JABATAN where AKTIF=? and TIPE_GROUP=?");
			stmt.setBoolean(1, true);
			stmt.setBoolean(2, true);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					String  nmj = ""+rs.getString(1);
					String  sin = ""+rs.getString(2);
					li.add(nmj+"`"+sin);
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
    
    
    public Vector getListAvalablePihakTerkait_v1(boolean tipe_grup) {
    	//Vector v = null;
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
		//	get
			stmt = con.prepareStatement("select NAMA_JABATAN from JABATAN where AKTIF=? and TIPE_GROUP=? order by NAMA_JABATAN");
			stmt.setBoolean(1, true);
			stmt.setBoolean(2, tipe_grup);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					String  nmj = ""+rs.getString(1);
					nmj = nmj.trim();
					if(!Checker.isStringNullOrEmpty(nmj)) {
						li.add(nmj);	
					}
					
				}
				while(rs.next());
			}
			if(v!=null) {
				v = beans.tools.Tool.removeDuplicateFromVector(v);
			}
    	}
    	catch (NamingException e) {
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
    	return v;
    }
    
    
    public Vector getListAvalablePihakTerkait_v1(boolean tipe_grup, String list_allow_jabatan) {
    	//Vector v = null;
    	if(!list_allow_jabatan.startsWith("`")) {
    		list_allow_jabatan = "`"+list_allow_jabatan;
    	}
    	if(!list_allow_jabatan.endsWith("`")) {
    		list_allow_jabatan = list_allow_jabatan+"`";
    	}
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
		//	get
			stmt = con.prepareStatement("select NAMA_JABATAN from JABATAN where AKTIF=? and TIPE_GROUP=? order by NAMA_JABATAN");
			stmt.setBoolean(1, true);
			stmt.setBoolean(2, tipe_grup);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					String  nmj = ""+rs.getString(1);
					nmj = nmj.trim();
					if(!Checker.isStringNullOrEmpty(nmj)) {
						li.add(nmj);	
					}
					
				}
				while(rs.next());
			}
			if(v!=null) {
				v = beans.tools.Tool.removeDuplicateFromVector(v);
				//filter hanya yg ada di list allow jabatan
				li = v.listIterator();
				while(li.hasNext()) {
					String tkn = (String)li.next();
					if(!list_allow_jabatan.contains("`"+tkn+"`")) {
						li.remove();
					}
				}
			}
			if(v!=null) {
				Collections.sort(v);
			}
			
    	}
    	catch (NamingException e) {
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
    	return v;
    }
    
    
    public Vector getListAvalablePihakTerkait_v1() {
    	//Vector v = null;
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
		//	get
			stmt = con.prepareStatement("select NAMA_JABATAN,TIPE_GROUP from JABATAN where AKTIF=? order by TIPE_GROUP,NAMA_JABATAN");
			stmt.setBoolean(1, true);

			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					String  nmj = ""+rs.getString(1);
					String  grp = ""+rs.getString(2);
					nmj = nmj.trim();
					if(!Checker.isStringNullOrEmpty(nmj)) {
						li.add(nmj+"`"+grp);	
					}
					
				}
				while(rs.next());
			}
			if(v!=null) {
				v = beans.tools.Tool.removeDuplicateFromVector(v);
			}
    	}
    	catch (NamingException e) {
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
    	return v;
    }
    
    /*
     * deprecated
     */
    public Vector getListJobGroup() {
    	//Vector v = null;
    	/*
    	Vector v = null;
    	ListIterator li = null;
    	String tmp = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
		//	get
			stmt = con.prepareStatement("select distinct TKN_GROUP from JABATAN where AKTIF=?");
			stmt.setBoolean(1, true);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				tmp = new String();
				do {
					tmp = ""+rs.getString(1);
					tmp=tmp.trim();
					while(rs.next()) {
						tmp = tmp+","+rs.getString(1);
					}
				}
				while(rs.next());
				
				StringTokenizer st = new StringTokenizer(tmp,",");
				while(st.hasMoreTokens()) {
					String tkn_name = st.nextToken();
					tkn_name = tkn_name.trim();
					if(!Checker.isStringNullOrEmpty(tkn_name)) {
						li.add(tkn_name);	
					}
					
				}
			}
			if(v!=null) {
				v = beans.tools.Tool.removeDuplicateFromVector(v);
			}
    	}
    	catch (NamingException e) {
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
    	*/
       	Vector v = getListTitleJabatanKelompok();
    	return v;
    }

    public Vector getCurrentJabatan(long target_objid) {
    	//Vector v = null;
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
		//	get
			stmt = con.prepareStatement("select KDKMP,KDPST,NM_JOB from STRUKTURAL where OBJID=? and AKTIF=?");
			stmt.setLong(1, target_objid);
			stmt.setBoolean(2, true);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					String  kdkmp = ""+rs.getString(1);
					String  kdpst = ""+rs.getString(2);
					String  nmjob = ""+rs.getString(3);
					
					li.add(nmjob+"`"+kdpst+"`"+kdkmp);
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
}

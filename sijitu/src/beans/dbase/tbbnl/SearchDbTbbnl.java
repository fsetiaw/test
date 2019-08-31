package beans.dbase.tbbnl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.util.Comparator;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.util.Collections;

import org.apache.tomcat.jdbc.pool.*;

import beans.dbase.SearchDb;
import beans.login.InitSessionUsr;
import beans.sistem.AskSystem;
import beans.setting.*;
import beans.tools.*;

import java.util.LinkedHashSet;


/**
 * Session Bean implementation class SearchDbTbbnl
 */
@Stateless
@LocalBean
public class SearchDbTbbnl extends SearchDb {
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
     * Default constructor. 
     */
    public SearchDbTbbnl() {
        // TODO Auto-generated constructor stub
    	
    }
    
    public SearchDbTbbnl(String operatorNpm) {
        // TODO Auto-generated constructor stub
    	super(operatorNpm);
    	this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
    }

    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * DEPRECATED 
     * DIGANTI Getter.getAngkaPenilaian(prev_thsms, v_kdpst)
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    public Vector getInfoTabelNilaiYgBerlakuPerKdpst(Vector vObjStringDgnTknPertamaKdpstValue) {
    	/*
    	 * if usrId < 0; berarti unverified usr jharusnya cuma pada saat login
    	 */
    	//String thsms_inp_nilai = Checker.getThsmsInputNilai();
    	//Vector v = new Vector();
    	//ListIterator li = v.listIterator();
    	if(vObjStringDgnTknPertamaKdpstValue!=null && vObjStringDgnTknPertamaKdpstValue.size()>0) {
    		ListIterator li1 = vObjStringDgnTknPertamaKdpstValue.listIterator();
    		
    		try {
        		//String ipAddr =  request.getRemoteAddr();
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? order by NLAKHTBBNL");
        		stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where KDPSTTBBNL=? and ACTIVE=? order by NLAKHTBBNL");
        		//
        		while(li1.hasNext()) {
        			String brs = (String)li1.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String kdpst = st.nextToken();
        			stmt.setString(1, kdpst);
            		stmt.setBoolean(2, true);
            		rs = stmt.executeQuery();
            		String tkn_nilai_bobot ="null-null";
            		//boolean first = true;
            		if(rs.next()) {
            			tkn_nilai_bobot = "";
            			do {
            				String nlakh =""+rs.getString(1);
            				nlakh = nlakh.replace("-", "tandaMin");
                			String bobot = ""+rs.getDouble(2);
                			tkn_nilai_bobot =tkn_nilai_bobot + nlakh+"-"+bobot+"-";
            			} while(rs.next());
            		}
            		li1.set(brs+"`"+tkn_nilai_bobot);
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

    	}
    	    	return vObjStringDgnTknPertamaKdpstValue;
    }	
    
    public Vector getInfoTabelNilaiYgBerlakuPerKdpstAtThsmsInputNilai() {
    	Vector vf = null;
    	ListIterator lif = null;
    	Vector vp = Getter.getListProdiBasedOnObject(); //li.add(kdpst+"`"+kdfak+"`"+kdjen+"`"+nmpst+"`"+kdkmp+"`"+idobj);
    	try {
    		String thsms_nilai =  Checker.getThsmsInputNilai();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		//stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? order by NLAKHTBBNL");
    		stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and ACTIVE=? order by BOBOTTBBNL desc,NLAKHTBBNL");
    		stmt.setString(1, thsms_nilai);
    		ListIterator lip = vp.listIterator();
    		while(lip.hasNext()) {
    			if(vf == null) {
    				vf = new Vector();
    				lif = vf.listIterator();
    			}
    			String brs = (String)lip.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kdpst = st.nextToken();
    			String kdfak = st.nextToken();
    			String kdjen = st.nextToken();
    			kdjen = Converter.getDetailKdjen(kdjen);
    			String nmpst = st.nextToken();
    			String kdkmp = st.nextToken();
    			String idobj = st.nextToken();
    			stmt.setString(2, kdpst);
    			stmt.setBoolean(3, true);
    			rs = stmt.executeQuery();
    			String list_nilai_bobot = "";
    			if(rs.next()) { 
    				do {
    					String nilai = rs.getString(1);
    					String bobot = ""+rs.getFloat(2);
    					list_nilai_bobot = list_nilai_bobot+"`"+nilai+"`"+bobot;
    				}
    				while(rs.next());
    				lif.add(kdpst+"`"+nmpst+"`"+kdjen+"`"+kdkmp+"`"+idobj+list_nilai_bobot);
    			}
    			else {
    				lif.add(kdpst+"`"+nmpst+"`"+kdjen+"`"+kdkmp+"`"+idobj);
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
    	return vf;
    }
    
    public Vector getInfoTabelNilaiYgBerlakuPerKdpstAtThsmsInputNilai(String filter_list_kdpst) {
    	Vector vf = null;
    	ListIterator lif = null;
    	if(filter_list_kdpst!=null && !Checker.isStringNullOrEmpty(filter_list_kdpst)) {
    		Vector vp = Getter.getListProdiBasedOnObject(); //li.add(kdpst+"`"+kdfak+"`"+kdjen+"`"+nmpst+"`"+kdkmp+"`"+idobj);
        	try {
        		String thsms_nilai =  Checker.getThsmsInputNilai();
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		//stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? order by NLAKHTBBNL");
        		stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and ACTIVE=? order by BOBOTTBBNL desc,NLAKHTBBNL");
        		stmt.setString(1, thsms_nilai);
        		ListIterator lip = vp.listIterator();
        		while(lip.hasNext()) {
        			if(vf == null) {
        				vf = new Vector();
        				lif = vf.listIterator();
        			}
        			String brs = (String)lip.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String kdpst = st.nextToken();
        			String kdfak = st.nextToken();
        			String kdjen = st.nextToken();
        			kdjen = Converter.getDetailKdjen(kdjen);
        			String nmpst = st.nextToken();
        			String kdkmp = st.nextToken();
        			String idobj = st.nextToken();
        			if(filter_list_kdpst.contains("`"+kdpst+"`")) {
        				stmt.setString(2, kdpst);
            			stmt.setBoolean(3, true);
            			rs = stmt.executeQuery();
            			String list_nilai_bobot = "";
            			if(rs.next()) { 
            				do {
            					String nilai = rs.getString(1);
            					String bobot = ""+rs.getFloat(2);
            					list_nilai_bobot = list_nilai_bobot+"`"+nilai+"`"+bobot;
            				}
            				while(rs.next());
            				lif.add(kdpst+"`"+nmpst+"`"+kdjen+"`"+kdkmp+"`"+idobj+list_nilai_bobot);
            				//System.out.println
            			}
            			else {
            				lif.add(kdpst+"`"+nmpst+"`"+kdjen+"`"+kdkmp+"`"+idobj);
            			}	
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
    	}
    		
    	return vf;
    }
    
    public Vector getInfoTabelNilaiYgBerlaku(String target_thsms, String target_kdpst) {
    	//bila at thsms tidak ada, cari info terkini
    	Vector vf = null;
    	ListIterator lif = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		//stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? order by NLAKHTBBNL");
    		stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and ACTIVE=? order by BOBOTTBBNL desc,NLAKHTBBNL");
    		stmt.setString(1, target_thsms);
    		stmt.setString(2, target_kdpst);
			stmt.setBoolean(3, true);
			rs = stmt.executeQuery();
			String list_nilai_bobot = "";
			if(rs.next()) { 
				do {
					String nilai = rs.getString(1);
					String bobot = ""+rs.getFloat(2);
					list_nilai_bobot = list_nilai_bobot+"`"+nilai+"`"+bobot;
				}
				while(rs.next());
				if(vf==null) {
					vf = new Vector();
					lif = vf.listIterator();
				}
				lif.add(target_kdpst+"`"+list_nilai_bobot);
			}
			else {
				//cari thsms yg terkini
				stmt = con.prepareStatement("SELECT THSMSTBBNL from TBBNL where KDPSTTBBNL=? and ACTIVE=?  order by THSMSTBBNL desc limit 1");
				stmt.setString(1, target_kdpst);
				stmt.setBoolean(2, true);
				rs = stmt.executeQuery();
				rs.next();
				target_thsms = rs.getString(1);
				
				stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and ACTIVE=? order by BOBOTTBBNL desc,NLAKHTBBNL");
	    		stmt.setString(1, target_thsms);
	    		stmt.setString(2, target_kdpst);
				stmt.setBoolean(3, true);
				rs = stmt.executeQuery();
				list_nilai_bobot = "";
				if(rs.next()) { 
					do {
						String nilai = rs.getString(1);
						String bobot = ""+rs.getFloat(2);
						list_nilai_bobot = list_nilai_bobot+"`"+nilai+"`"+bobot;
					}
					while(rs.next());
					if(vf==null) {
						vf = new Vector();
						lif = vf.listIterator();
					}
					lif.add(target_kdpst+"`"+list_nilai_bobot);
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
    	return vf;
    }
    
    
    public Vector getInfoTabelNilaiYgBerlaku(String target_thsms, String target_kdpst, Connection con) {
    	//bila at thsms tidak ada, cari info terkini
    	Vector vf = null;
    	ListIterator lif = null;
    	try {
    		
    		
    		//stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? order by NLAKHTBBNL");
    		stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and ACTIVE=? order by BOBOTTBBNL desc,NLAKHTBBNL");
    		stmt.setString(1, target_thsms);
    		stmt.setString(2, target_kdpst);
			stmt.setBoolean(3, true);
			rs = stmt.executeQuery();
			String list_nilai_bobot = "";
			if(rs.next()) { 
				do {
					String nilai = rs.getString(1);
					String bobot = ""+rs.getFloat(2);
					list_nilai_bobot = list_nilai_bobot+"`"+nilai+"`"+bobot;
				}
				while(rs.next());
				if(vf==null) {
					vf = new Vector();
					lif = vf.listIterator();
				}
				lif.add(target_kdpst+"`"+list_nilai_bobot);
			}
			else {
				//cari thsms yg terkini
				stmt = con.prepareStatement("SELECT THSMSTBBNL from TBBNL where KDPSTTBBNL=? and ACTIVE=?  order by THSMSTBBNL desc limit 1");
				stmt.setString(1, target_kdpst);
				stmt.setBoolean(2, true);
				rs = stmt.executeQuery();
				rs.next();
				target_thsms = rs.getString(1);
				
				stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and ACTIVE=? order by BOBOTTBBNL desc,NLAKHTBBNL");
	    		stmt.setString(1, target_thsms);
	    		stmt.setString(2, target_kdpst);
				stmt.setBoolean(3, true);
				rs = stmt.executeQuery();
				list_nilai_bobot = "";
				if(rs.next()) { 
					do {
						String nilai = rs.getString(1);
						String bobot = ""+rs.getFloat(2);
						list_nilai_bobot = list_nilai_bobot+"`"+nilai+"`"+bobot;
					}
					while(rs.next());
					if(vf==null) {
						vf = new Vector();
						lif = vf.listIterator();
					}
					lif.add(target_kdpst+"`"+list_nilai_bobot);
				}
			}
    		
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	} 
    	finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    //if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	return vf;
    }
    
    
    public static double getBobotNilaiYgBerlaku(String target_thsms, String target_kdpst, String nlakh, Connection con) {
    	//bila at thsms tidak ada, cari info terkini
    	PreparedStatement stmt =null;
    	ResultSet rs=null;
    	Vector vf = null;
    	ListIterator lif = null;
    	double bobot = 0;
    	try {
    		
    		try {
    			double nilai = Double.parseDouble(nlakh);
    			stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and ACTIVE=? and NILAI_MIN<=? and NILAI_MAX>=? order by BOBOTTBBNL desc,NLAKHTBBNL");
    			stmt.setString(1, target_thsms);
        		stmt.setString(2, target_kdpst);
    			stmt.setBoolean(3, true);
    			stmt.setDouble(4, nilai);
    			stmt.setDouble(5, nilai);
    		}
    		catch(Exception e) {
    			stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and ACTIVE=? and NLAKHTBBNL=? order by BOBOTTBBNL desc,NLAKHTBBNL");
    			stmt.setString(1, target_thsms);
        		stmt.setString(2, target_kdpst);
    			stmt.setBoolean(3, true);
    			stmt.setString(4, nlakh);
    		}
    		//stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? order by NLAKHTBBNL");
    		
    		
			rs = stmt.executeQuery();
			String list_nilai_bobot = "";
			if(rs.next()) { 
				String nilai = rs.getString(1);
				bobot = rs.getDouble(2);
			}
			else {
				//cari thsms yg terkini
				stmt = con.prepareStatement("SELECT THSMSTBBNL from TBBNL where KDPSTTBBNL=? and ACTIVE=?  order by THSMSTBBNL desc limit 1");
				stmt.setString(1, target_kdpst);
				stmt.setBoolean(2, true);
				rs = stmt.executeQuery();
				rs.next();
				target_thsms = rs.getString(1);
				
				try {
	    			double nilai = Double.parseDouble(nlakh);
	    			stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and ACTIVE=? and NILAI_MIN<=? and NILAI_MAX>=? order by BOBOTTBBNL desc,NLAKHTBBNL");
	    			stmt.setString(1, target_thsms);
	        		stmt.setString(2, target_kdpst);
	    			stmt.setBoolean(3, true);
	    			stmt.setDouble(4, nilai);
	    			stmt.setDouble(5, nilai);
	    		}
	    		catch(Exception e) {
	    			stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and ACTIVE=? and NLAKHTBBNL=? order by BOBOTTBBNL desc,NLAKHTBBNL");
	    			stmt.setString(1, target_thsms);
	        		stmt.setString(2, target_kdpst);
	    			stmt.setBoolean(3, true);
	    			stmt.setString(4, nlakh);
	    		}
	   
				rs = stmt.executeQuery();
				list_nilai_bobot = "";
				if(rs.next()) { 
					String nilai = rs.getString(1);
					bobot = rs.getDouble(2);
				}
			}
    		
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	} 
    	finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    //if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	return bobot;
    }
    
    public static String getNlakhDanNilaiDanBobotNilaiYgBerlaku(String target_thsms, String target_kdpst, String nlakh_nilai, Connection con) {
    	//bila at thsms tidak ada, cari info terkini
    	PreparedStatement stmt =null;
    	ResultSet rs=null;
    	Vector vf = null;
    	String nlakh = null;
    	ListIterator lif = null;
    	double bobot = 0,nilai_min=0,nilai_max=0, nilai_angka=-10;//minus kalo nlakh yg diberikan
    	try {
    		
    		try {
    			//System.out.println("ini");
    			nilai_angka = Double.parseDouble(nlakh_nilai);
    			stmt = con.prepareStatement("SELECT NILAI_MIN,NILAI_MAX,NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and ACTIVE=? and NILAI_MIN<=? and NILAI_MAX>=? and NILAI_MIN<>NILAI_MAX order by BOBOTTBBNL desc,NLAKHTBBNL");
    			//System.out.println("SELECT NILAI_MIN,NILAI_MAX,NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and ACTIVE=? and NILAI_MIN<=? and NILAI_MAX>=? and NILAI_MIN<>NILAI_MAX order by BOBOTTBBNL desc,NLAKHTBBNL");
    			stmt.setString(1, target_thsms);
        		stmt.setString(2, target_kdpst);
    			stmt.setBoolean(3, true);
    			stmt.setDouble(4, nilai_angka);
    			stmt.setDouble(5, nilai_angka);
    		}
    		catch(Exception e) {
    			//System.out.println("itu");
    			nlakh = new String(nlakh_nilai);
    			stmt = con.prepareStatement("SELECT NILAI_MIN,NILAI_MAX,NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and ACTIVE=? and NLAKHTBBNL=? order by BOBOTTBBNL desc,NLAKHTBBNL");
    			stmt.setString(1, target_thsms);
        		stmt.setString(2, target_kdpst);
    			stmt.setBoolean(3, true);
    			stmt.setString(4, nlakh);
    		}
    		//stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? order by NLAKHTBBNL");
    		
    		
			rs = stmt.executeQuery();
			if(rs.next()) { 
				nilai_min = rs.getDouble(1);
				nilai_max = rs.getDouble(2);
				if(nlakh==null) {
					nlakh = rs.getString(3);	
				}
				bobot = rs.getDouble(4);
				if(nilai_angka<0) {
					nilai_angka = (nilai_max-nilai_min)/2;
				}
			}
			else {
				//cari thsms yg terkini
				nlakh = null;
				stmt = con.prepareStatement("SELECT THSMSTBBNL from TBBNL where KDPSTTBBNL=? and ACTIVE=?  order by THSMSTBBNL desc limit 1");
				stmt.setString(1, target_kdpst);
				stmt.setBoolean(2, true);
				rs = stmt.executeQuery();
				rs.next();
				target_thsms = rs.getString(1);
				
				try {
					nilai_angka = Double.parseDouble(nlakh_nilai);
	    			stmt = con.prepareStatement("SELECT NILAI_MIN,NILAI_MAX,NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and ACTIVE=? and NILAI_MIN<=? and NILAI_MAX>=? and NILAI_MIN<>NILAI_MAX order by BOBOTTBBNL desc,NLAKHTBBNL");
	    			stmt.setString(1, target_thsms);
	        		stmt.setString(2, target_kdpst);
	    			stmt.setBoolean(3, true);
	    			stmt.setDouble(4, nilai_angka);
	    			stmt.setDouble(5, nilai_angka);
	    		}
	    		catch(Exception e) {
	    			nlakh = new String(nlakh_nilai);
	    			stmt = con.prepareStatement("SELECT NILAI_MIN,NILAI_MAX,NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and ACTIVE=? and NLAKHTBBNL=? order by BOBOTTBBNL desc,NLAKHTBBNL");
	    			stmt.setString(1, target_thsms);
	        		stmt.setString(2, target_kdpst);
	    			stmt.setBoolean(3, true);
	    			stmt.setString(4, nlakh);
	    		}
	    		
				rs = stmt.executeQuery();
				if(rs.next()) { 
					nilai_min = rs.getDouble(1);
					nilai_max = rs.getDouble(2);
					if(nlakh==null) {
						nlakh = rs.getString(3);	
					}
					bobot = rs.getDouble(4);
					if(nilai_angka<0) {
						nilai_angka = (nilai_max-nilai_min)/2;
					}
				}
			}
    		
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	} 
    	finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    //if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	return nlakh+"`"+nilai_angka+"`"+bobot;
    }
}

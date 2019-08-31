package beans.dbase.overview.maintenance;

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

import beans.tools.Checker;
import beans.tools.Converter;

/**
 * Session Bean implementation class Maintenance
 */
@Stateless
@LocalBean
public class MaintenanceOverview {
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
    public MaintenanceOverview() {
        // TODO Auto-generated constructor stub
    }
    
    public int initializeTableOverviewSebaranTrlsm_step1(String target_thsms) {
    	int updated=0;
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select DISTINCT ID_OBJ,KDPST from OBJECT where OBJ_NAME='MHS'");
			rs = stmt.executeQuery();
			while(rs.next()) {
				int idobj = rs.getInt(1);
				String kdpst = rs.getString(2);
				li.add(idobj+"`"+kdpst);
			}
			stmt=con.prepareStatement("insert ignore into OVERVIEW_SEBARAN_TRLSM(ID_OBJ,THSMS,KDPST)values(?,?,?)");
			li = v.listIterator();
			while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String idobj = st.nextToken(); 
    			String kdpst = st.nextToken(); 
    			stmt.setInt(1, Integer.parseInt(idobj));
    			stmt.setString(2, target_thsms);
    			stmt.setString(3, kdpst);
    			updated = updated+stmt.executeUpdate();
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
    	return updated;
    }
    
    public int maintenaceCountDataMhsGivenStmhs_step2(Vector v_scope_id, String stmhs, String target_thsms) {
    	//Vector vf = null;
    	int updated=0;
    	String tipe_pengajuan = Converter.convertStmhsJadiTipePengajuan(stmhs);
    	
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		ListIterator li = v_scope_id.listIterator();
    		String nu_scope = "";
    		
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			//select total stmhs : termasuk kelas f
        		stmt = con.prepareStatement("SELECT count(*) FROM TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and ID_OBJ=? and STMHS=?");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			if(st.hasMoreTokens()) {
        				String kmp = st.nextToken(); //kode kampus IGNOREnu_scope 
        				nu_scope = new String(kmp);
        				while(st.hasMoreTokens()) {
        					String idobj = st.nextToken();
        					stmt.setString(1, target_thsms);
        					stmt.setInt(2, Integer.parseInt(idobj));
        					stmt.setString(3, stmhs);
        					rs = stmt.executeQuery();
        					int counter = 0;
        					if(rs.next()) {
        						counter = rs.getInt(1);
        					}
        					nu_scope = nu_scope+"`"+idobj+","+counter;
        				}
        				li.set(nu_scope);
        				//System.out.println("nu_scope="+nu_scope);
        			}
        		}
        		if(v_scope_id!=null && v_scope_id.size()>0) {
            		li = v_scope_id.listIterator();
            		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_"+tipe_pengajuan+"_REQ=? where ID_OBJ=? and THSMS=?");
            		while(li.hasNext()) {
            			String brs = (String)li.next();
            			StringTokenizer st = new StringTokenizer(brs,"`");
            			if(st.hasMoreTokens()) {
            				String kmp = st.nextToken(); //kode kampus IGNOREnu_scope 
            				nu_scope = new String(kmp);
            				while(st.hasMoreTokens()) {
            					String id_count = st.nextToken();
            					StringTokenizer st1 = new StringTokenizer(id_count,",");
            					String id = st1.nextToken();
            					String count = st1.nextToken();
            					stmt.setInt(1, Integer.parseInt(count));
            					stmt.setInt(2, Integer.parseInt(id));
            					stmt.setString(3, target_thsms);
            					updated=stmt.executeUpdate();
            				}
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
    	
    	return updated;
    }
    
    public int maintenaceCountDataMhsGivenStmhsInProgress_step3(Vector v_scope_id, String stmhs, String target_thsms) {
    	//Vector vf = null;
    	int updated=0;
    	String tipe_pengajuan = Converter.convertStmhsJadiTipePengajuan(stmhs);
    	
    	
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		ListIterator li = v_scope_id.listIterator();
    		String nu_scope = "";
    		
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
        		stmt = con.prepareStatement("SELECT count(CREATOR_NPM) FROM TOPIK_PENGAJUAN inner join CIVITAS on CREATOR_NPM=NPMHSMSMHS where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN='"+tipe_pengajuan+"' and CREATOR_OBJ_ID=? and LOCKED=false and REJECTED is null and BATAL=false ");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			if(st.hasMoreTokens()) {
        				String kmp = st.nextToken(); //kode kampus IGNOREnu_scope 
        				nu_scope = new String(kmp);
        				while(st.hasMoreTokens()) {
        					String idobj = st.nextToken();
        					//System.out.println("idobj="+idobj);
        					stmt.setString(1, target_thsms);
        					stmt.setInt(2, Integer.parseInt(idobj));
        					rs = stmt.executeQuery();
        					int counter = 0;
        					if(rs.next()) {
        						counter = rs.getInt(1);
        					}
        					nu_scope = nu_scope+"`"+idobj+","+counter;
        				}
        				li.set(nu_scope);
        				//System.out.println("nu_scope in progress3="+nu_scope);
        			}
        		}
        		
        		if(v_scope_id!=null && v_scope_id.size()>0) {
            		li = v_scope_id.listIterator();
            		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_"+tipe_pengajuan+"_REQ=TOT_"+tipe_pengajuan+"_REQ+?,TOT_"+tipe_pengajuan+"_REQ_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
            		while(li.hasNext()) {
            			String brs = (String)li.next();
            			StringTokenizer st = new StringTokenizer(brs,"`");
            			if(st.hasMoreTokens()) {
            				String kmp = st.nextToken(); //kode kampus IGNOREnu_scope 
            				nu_scope = new String(kmp);
            				while(st.hasMoreTokens()) {
            					String id_count = st.nextToken();
            					StringTokenizer st1 = new StringTokenizer(id_count,",");
            					String id = st1.nextToken();
            					String count = st1.nextToken();
            					stmt.setInt(1, Integer.parseInt(count));
            					stmt.setInt(2, Integer.parseInt(count));
            					stmt.setInt(3, Integer.parseInt(id));
            					stmt.setString(4, target_thsms);
            					updated=stmt.executeUpdate();
            				}
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
    	
    	return updated;
    }
    
    public int maintenaceUpdateListMhsGivenStmhsInProgress_step4(Vector v_scope_id, String stmhs, String target_thsms) {
    	//Vector vf = null;
    	int updated = 0;
    	Vector v_npm=null;
    	ListIterator lin = null;
    	String tipe_pengajuan = Converter.convertStmhsJadiTipePengajuan(stmhs);
    	
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		ListIterator li = v_scope_id.listIterator();
    		String nu_scope = "";
    		
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
        		stmt = con.prepareStatement("SELECT CREATOR_NPM FROM TOPIK_PENGAJUAN inner join CIVITAS on CREATOR_NPM=NPMHSMSMHS where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN='"+tipe_pengajuan+"' and CREATOR_OBJ_ID=? and LOCKED=false and REJECTED is null and BATAL=false ");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			if(st.hasMoreTokens()) {
        				String tkn_npm = "";
        				String kmp = st.nextToken(); //kode kampus IGNOREnu_scope 
        				nu_scope = new String(kmp);
        				while(st.hasMoreTokens()) {
        					String idobj = st.nextToken();
        					//System.out.println("idobj="+idobj);
        					stmt.setString(1, target_thsms);
        					stmt.setInt(2, Integer.parseInt(idobj));
        					rs = stmt.executeQuery();
        					//int counter = 0;
        					while(rs.next()) {
        						String npm = rs.getString(1);
        						tkn_npm = tkn_npm+","+npm;
        					}
        					if(!Checker.isStringNullOrEmpty(tkn_npm)) {
        						nu_scope = nu_scope+"`"+idobj+"-"+tkn_npm.substring(1, tkn_npm.length());//hilangin koma	
        					}
        					else {
        						nu_scope = nu_scope+"`"+idobj+"-null";
        					}
        					
        				}
        				li.set(nu_scope);
        				//System.out.println("nu_scope in progress4="+nu_scope);
        			}
        		}
        		
        		//tipe_pengajuan
        		if(v_scope_id!=null && v_scope_id.size()>0) {
            		li = v_scope_id.listIterator();
            		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
            		while(li.hasNext()) {
            			String brs = (String)li.next();
            			StringTokenizer st = new StringTokenizer(brs,"`");
            			if(st.hasMoreTokens()) {
            				String kmp = st.nextToken(); //kode kampus IGNOREnu_scope 
            				//nu_scope = new String(kmp);
            				while(st.hasMoreTokens()) {
            					String brs1 = st.nextToken();
            					StringTokenizer st1 = new StringTokenizer(brs1,"-");
            					String id = st1.nextToken();
            					String tkn_npm = st1.nextToken();
            					if(Checker.isStringNullOrEmpty(tkn_npm)) {
            						stmt.setNull(1, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(1, tkn_npm);	
            					}
            					
            					stmt.setInt(2, Integer.parseInt(id));
            					stmt.setString(3, target_thsms);
            					updated=updated+stmt.executeUpdate();
            				}
            			}
            			else {
            				
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
    	
    	return updated;
    }
}

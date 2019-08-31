package beans.dbase.mhs.maintenance;

import beans.dbase.mhs.UpdateDbInfoMhs;
import beans.tools.Checker;

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
 * Session Bean implementation class MaintenanceUpdateDb
 */
@Stateless
@LocalBean
public class MaintenanceUpdateDb extends UpdateDbInfoMhs {
	String operatorNpm;
	String tknOperatorNickname;
	String operatorNmm;
	boolean petugas;
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;   
    /**
     * @see UpdateDbInfoMhs#UpdateDbInfoMhs()
     */
    public MaintenanceUpdateDb() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDbInfoMhs#UpdateDbInfoMhs(String)
     */
    public MaintenanceUpdateDb(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.tknOperatorNickname = getTknOprNickname();
    	//System.out.println("tknOperatorNickname1="+this.tknOperatorNickname);
    	this.petugas = cekApaUsrPetugas();
        // TODO Auto-generated constructor stub
    }

    
    public int deleteRecordAfterOut(String target_thsms) {
    	int updated = 0;
    	if(!Checker.isStringNullOrEmpty(target_thsms)) {
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		Vector v = null;
        		ListIterator li = null;
        		//proses lulus
        		{
        			String stmhs = "L";
            		stmt = con.prepareStatement("select NPMHS from TRLSM where THSMS=? and STMHS=?");
            		stmt.setString(1, target_thsms);
            		stmt.setString(2, stmhs);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			v = new Vector();
            			li = v.listIterator();
            			do {
            				String npmhs = rs.getString(1);
            				li.add(npmhs);
            			}
            			while(rs.next());
            			if(v!=null) {
            				//delete trlsm
            				stmt = con.prepareStatement("delete from TRLSM where NPMHS=? and THSMS>?");
            				li = v.listIterator();
            				while(li.hasNext()) {
            					String npmhs = (String)li.next();
            					stmt.setString(1, npmhs);
            					stmt.setString(2, target_thsms);
            					updated = updated + stmt.executeUpdate();
            				}
            			}
            			if(v!=null) {
            				//delete trlsm
            				stmt = con.prepareStatement("delete from TRNLM where NPMHSTRNLM=? and THSMSTRNLM>?");
            				li = v.listIterator();
            				while(li.hasNext()) {
            					String npmhs = (String)li.next();
            					stmt.setString(1, npmhs);
            					stmt.setString(2, target_thsms);
            					updated = updated + stmt.executeUpdate();
            				}
            			}
            			if(v!=null) {
            				//delete trakm
            				stmt = con.prepareStatement("delete from TRAKM where NPMHSTRAKM=? and THSMSTRAKM>?");
            				li = v.listIterator();
            				while(li.hasNext()) {
            					String npmhs = (String)li.next();
            					stmt.setString(1, npmhs);
            					stmt.setString(2, target_thsms);
            					updated = updated + stmt.executeUpdate();
            				}
            			}
            			if(v!=null) {
            				//delete trakm
            				stmt = con.prepareStatement("delete from KRS_NOTIFICATION where THSMS_TARGET>? and NPM_SENDER=?");
            				li = v.listIterator();
            				while(li.hasNext()) {
            					String npmhs = (String)li.next();
            					stmt.setString(1, target_thsms);
            					stmt.setString(2, npmhs);
            					updated = updated + stmt.executeUpdate();
            				}
            			}
            			if(v!=null) {
            				stmt = con.prepareStatement("delete from DAFTAR_ULANG where THSMS>? and NPMHS=?");
            				li = v.listIterator();
            				while(li.hasNext()) {
            					String npmhs = (String)li.next();
            					stmt.setString(1, target_thsms);
            					stmt.setString(2, npmhs);
            					updated = updated + stmt.executeUpdate();
            				}
            			}
            			if(v!=null) {
            				//stmt = con.prepareStatement("update TOPIK_PENGAJUAN set BATAL=?,LOCKED=?,SHOW_AT_TARGET=?,SHOW_AT_CREATOR=? where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=? and TIPE_PENGAJUAN<>?");
            				stmt = con.prepareStatement("update TOPIK_PENGAJUAN set BATAL=?,LOCKED=?,SHOW_AT_TARGET=?,SHOW_AT_CREATOR=? where TARGET_THSMS_PENGAJUAN>? and CREATOR_NPM=?");
            				li = v.listIterator();
            				while(li.hasNext()) {
            					String npmhs = (String)li.next();
            					stmt.setBoolean(1, true);
            					stmt.setBoolean(2, true);
            					stmt.setNull(3, java.sql.Types.VARCHAR);
            					stmt.setBoolean(4, false);
            					stmt.setString(5, target_thsms);
            					stmt.setString(6, npmhs);
            					//stmt.setString(7, stmhs);
            					updated = updated + stmt.executeUpdate();
            				}
            			}
            		}
        		}
        		
        		//proses keluar
        		{
        			String stmhs = "K";
            		stmt = con.prepareStatement("select NPMHS from TRLSM where THSMS=? and STMHS=?");
            		stmt.setString(1, target_thsms);
            		stmt.setString(2, stmhs);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			v = new Vector();
            			li = v.listIterator();
            			do {
            				String npmhs = rs.getString(1);
            				li.add(npmhs);
            			}
            			while(rs.next());
            			if(v!=null) {
            				//delete trlsm
            				stmt = con.prepareStatement("delete from TRLSM where NPMHS=? and THSMS>?");
            				li = v.listIterator();
            				while(li.hasNext()) {
            					String npmhs = (String)li.next();
            					stmt.setString(1, npmhs);
            					stmt.setString(2, target_thsms);
            					updated = updated + stmt.executeUpdate();
            				}
            			}
            			if(v!=null) {
            				//delete trnlm
            				stmt = con.prepareStatement("delete from TRNLM where NPMHSTRNLM=? and THSMSTRNLM>=?");
            				li = v.listIterator();
            				while(li.hasNext()) {
            					String npmhs = (String)li.next();
            					stmt.setString(1, npmhs);
            					stmt.setString(2, target_thsms);
            					updated = updated + stmt.executeUpdate();
            				}
            			}	
            				
            			if(v!=null) {
            				//delete trakm
            				stmt = con.prepareStatement("delete from TRAKM where NPMHSTRAKM=? and THSMSTRAKM>=?");
            				li = v.listIterator();
            				while(li.hasNext()) {
            					String npmhs = (String)li.next();
            					stmt.setString(1, npmhs);
            					stmt.setString(2, target_thsms);
            					updated = updated + stmt.executeUpdate();
            				}
            			}
            			if(v!=null) {
            				//delete trakm
            				stmt = con.prepareStatement("delete from KRS_NOTIFICATION where THSMS_TARGET>=? and NPM_SENDER=?");
            				li = v.listIterator();
            				while(li.hasNext()) {
            					String npmhs = (String)li.next();
            					stmt.setString(1, target_thsms);
            					stmt.setString(2, npmhs);
            					updated = updated + stmt.executeUpdate();
            				}
            			}
            			if(v!=null) {
            				stmt = con.prepareStatement("delete from DAFTAR_ULANG where THSMS>=? and NPMHS=?");
            				li = v.listIterator();
            				while(li.hasNext()) {
            					String npmhs = (String)li.next();
            					stmt.setString(1, target_thsms);
            					stmt.setString(2, npmhs);
            					updated = updated + stmt.executeUpdate();
            				}
            			}
            			if(v!=null) {
            				//stmt = con.prepareStatement("update TOPIK_PENGAJUAN set BATAL=?,LOCKED=?,SHOW_AT_TARGET=?,SHOW_AT_CREATOR=? where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=? and TIPE_PENGAJUAN<>?");
            				stmt = con.prepareStatement("update TOPIK_PENGAJUAN set BATAL=?,LOCKED=?,SHOW_AT_TARGET=?,SHOW_AT_CREATOR=? where TARGET_THSMS_PENGAJUAN>? and CREATOR_NPM=?");
            				li = v.listIterator();
            				while(li.hasNext()) {
            					String npmhs = (String)li.next();
            					stmt.setBoolean(1, true);
            					stmt.setBoolean(2, true);
            					stmt.setNull(3, java.sql.Types.VARCHAR);
            					stmt.setBoolean(4, false);
            					stmt.setString(5, target_thsms);
            					stmt.setString(6, npmhs);
            					//stmt.setString(7, stmhs);
            					updated = updated + stmt.executeUpdate();
            				}
            			}
            		}
        		}
        		
        		//proses do
        		{
        			String stmhs = "D";
            		stmt = con.prepareStatement("select NPMHS from TRLSM where THSMS=? and STMHS=?");
            		stmt.setString(1, target_thsms);
            		stmt.setString(2, stmhs);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			v = new Vector();
            			li = v.listIterator();
            			do {
            				String npmhs = rs.getString(1);
            				li.add(npmhs);
            			}
            			while(rs.next());
            			if(v!=null) {
            				//delete trlsm
            				stmt = con.prepareStatement("delete from TRLSM where NPMHS=? and THSMS>?");
            				li = v.listIterator();
            				while(li.hasNext()) {
            					String npmhs = (String)li.next();
            					stmt.setString(1, npmhs);
            					stmt.setString(2, target_thsms);
            					updated = updated + stmt.executeUpdate();
            				}
            			}
            			if(v!=null) {
            				//delete trnlm
            				stmt = con.prepareStatement("delete from TRNLM where NPMHSTRNLM=? and THSMSTRNLM>=?");
            				li = v.listIterator();
            				while(li.hasNext()) {
            					String npmhs = (String)li.next();
            					stmt.setString(1, npmhs);
            					stmt.setString(2, target_thsms);
            					updated = updated + stmt.executeUpdate();
            				}
            			}
            			if(v!=null) {
            				//delete trakm
            				stmt = con.prepareStatement("delete from TRAKM where NPMHSTRAKM=? and THSMSTRAKM>=?");
            				li = v.listIterator();
            				while(li.hasNext()) {
            					String npmhs = (String)li.next();
            					stmt.setString(1, npmhs);
            					stmt.setString(2, target_thsms);
            					updated = updated + stmt.executeUpdate();
            				}
            			}
            			if(v!=null) {
            				//delete trakm
            				stmt = con.prepareStatement("delete from KRS_NOTIFICATION where THSMS_TARGET>=? and NPM_SENDER=?");
            				li = v.listIterator();
            				while(li.hasNext()) {
            					String npmhs = (String)li.next();
            					stmt.setString(1, target_thsms);
            					stmt.setString(2, npmhs);
            					updated = updated + stmt.executeUpdate();
            				}
            			}
            			if(v!=null) {
            				stmt = con.prepareStatement("delete from DAFTAR_ULANG where THSMS>=? and NPMHS=?");
            				li = v.listIterator();
            				while(li.hasNext()) {
            					String npmhs = (String)li.next();
            					stmt.setString(1, target_thsms);
            					stmt.setString(2, npmhs);
            					updated = updated + stmt.executeUpdate();
            				}
            			}
            			if(v!=null) {
            				//stmt = con.prepareStatement("update TOPIK_PENGAJUAN set BATAL=?,LOCKED=?,SHOW_AT_TARGET=?,SHOW_AT_CREATOR=? where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=? and TIPE_PENGAJUAN<>?");
            				stmt = con.prepareStatement("update TOPIK_PENGAJUAN set BATAL=?,LOCKED=?,SHOW_AT_TARGET=?,SHOW_AT_CREATOR=? where TARGET_THSMS_PENGAJUAN>? and CREATOR_NPM=?");
            				li = v.listIterator();
            				while(li.hasNext()) {
            					String npmhs = (String)li.next();
            					stmt.setBoolean(1, true);
            					stmt.setBoolean(2, true);
            					stmt.setNull(3, java.sql.Types.VARCHAR);
            					stmt.setBoolean(4, false);
            					stmt.setString(5, target_thsms);
            					stmt.setString(6, npmhs);
            					//stmt.setString(7, stmhs);
            					updated = updated + stmt.executeUpdate();
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
    
    public int deleteErrorRec(Vector v_MaintenaceSearchDb_getListNpmMhsYgAdaStatusTrlsmSebelumSmawl) {
    	int upd = 0;
    	StringTokenizer st = null;
    	if(v_MaintenaceSearchDb_getListNpmMhsYgAdaStatusTrlsmSebelumSmawl!=null && v_MaintenaceSearchDb_getListNpmMhsYgAdaStatusTrlsmSebelumSmawl.size()>0) {
    		ListIterator li = v_MaintenaceSearchDb_getListNpmMhsYgAdaStatusTrlsmSebelumSmawl.listIterator();
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//THSMS`KODE PRODI`NPMHS
        		String sql= "delete from TRLSM where THSMS=? and NPMHS=?";
        		stmt=con.prepareStatement(sql);
        		
        				
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			String thsms = st.nextToken();
        			String kdpst = st.nextToken();
        			String npmhs = st.nextToken();
        			stmt.setString(1, thsms);
        			stmt.setString(2, npmhs);
        			upd = upd+stmt.executeUpdate();
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
    	return upd;
	}
}

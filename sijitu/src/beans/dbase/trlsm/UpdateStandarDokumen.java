package beans.dbase.trlsm;

import beans.dbase.UpdateDb;
import beans.tools.Checker;
import beans.tools.Getter;

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
 * Session Bean implementation class UpdateStandarDokumen
 */
@Stateless
@LocalBean
public class UpdateStandarDokumen extends UpdateDb {
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
     * @see UpdateDb#UpdateDb()
     */
    public UpdateStandarDokumen() {
        super();
        // TODO Auto-generated constructor stub
    }
      
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateStandarDokumen(String operatorNpm) {
    	 super(operatorNpm);
         this.operatorNpm = operatorNpm;
     	this.operatorNmm = getNmmOperator();
     	this.petugas = cekApaUsrPetugas();
     	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    
    public int tambahTipeDokumenMutu(String nm_doc) {
    	
    	int upd = 0;
    	if(!Checker.isStringNullOrEmpty(nm_doc)) {
    		
    		try {
        		Vector v_kdpst_kdfak_kdjen_nmpst=Getter.getListProdi();
        		Vector v_code_campus_name_campus_nickname_campus=Getter.getListAllKampus(true, Checker.getThsmsNow());
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			String sql = "insert ignore into STANDARD_DOKUMEN(NAMA_DOKUMEN)values(?)";
    			stmt = con.prepareStatement(sql);
    			stmt.setString(1, nm_doc.trim().toUpperCase());
    			upd = stmt.executeUpdate();	
    		} 
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
    		catch (Exception e) {}
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}	
    	}
    	
    	return upd;
    		
    }
    
    public int updateNamaDokumenMutu(String[] nm_doc_ori, String[] nu_nm_doc) {
    	
    	int upd = 0;
    	if(nm_doc_ori!=null && nm_doc_ori.length>0 && nm_doc_ori.length==nu_nm_doc.length) {
    		
    		try {
        		Vector v_kdpst_kdfak_kdjen_nmpst=Getter.getListProdi();
        		Vector v_code_campus_name_campus_nickname_campus=Getter.getListAllKampus(true, Checker.getThsmsNow());
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			String sql = "update STANDARD_DOKUMEN set NAMA_DOKUMEN=? where NAMA_DOKUMEN=?";
    			stmt = con.prepareStatement(sql);
    			for(int i=0;i<nm_doc_ori.length;i++) {
    				stmt.setString(1, nu_nm_doc[i].trim().toUpperCase());
    				stmt.setString(2, nm_doc_ori[i].trim().toUpperCase());
        			upd = upd+stmt.executeUpdate();	
    			}
    				
    		} 
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
    		catch (Exception e) {}
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}	
    	}
    	
    	return upd;
    		
    }

}

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
public class CekTableMaintenance {
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
    public CekTableMaintenance() {
        // TODO Auto-generated constructor stub
    }
    
    public Vector getListErrorInfo(String scope_kdpst) {
    	Vector v = null;
    	if(!Checker.isStringNullOrEmpty(scope_kdpst)) {
    		v = new Vector();
    		String seperator = Checker.getSeperatorYgDigunakan(scope_kdpst);
    		StringTokenizer st = new StringTokenizer(scope_kdpst,seperator);
    		String sql = "";
    		while(st.hasMoreTokens()) {
    			sql = sql+"KDPST="+st.nextToken();
    			if(st.hasMoreTokens()) {
    				sql = sql+" or ";
    			}
    		}
        	ListIterator li = v.listIterator();
        	try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from TABEL_ERROR_INFO where ("+sql+") order by KDPST,UPDTM");
    			//System.out.println("select * from TABEL_ERROR_INFO where ("+sql+") order by KDPST,UPDTM");
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String nmm_table = rs.getString("TABLE_NAME");
    				String kdpst = rs.getString("KDPST");
    				if(Checker.isStringNullOrEmpty(kdpst)) {
    					kdpst = "null";
    				}
    				String updtm = ""+rs.getTimestamp("UPDTM");
    				String err_info = rs.getString("ERROR_INFO");
    				li.add(kdpst+"~"+nmm_table+"~"+updtm+"~"+err_info);
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
    	
    	return v;
    }
    
    public boolean adaError(String scope_kdpst) {
    	
    	boolean ada=false;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from TABEL_ERROR_INFO limit 1");
			rs=stmt.executeQuery();
			if(rs.next()) {
				ada = true;
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
    	return ada;
    }
}

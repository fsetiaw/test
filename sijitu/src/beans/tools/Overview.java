package beans.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;

import beans.dbase.trnlm.SearchDbTrnlm;
import beans.setting.Constants;
import beans.sistem.AskSystem;
//import beans.tools.Checker;
import java.util.Vector;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.StringTokenizer;
import java.math.BigDecimal;
import javax.servlet.http.HttpSession;
/**
 * Session Bean implementation class Checker
 */
@Stateless
@LocalBean
public class Overview {

    /**
     * Default constructor. 
     */
    public Overview() {
        // TODO Auto-generated constructor stub
    }
 
    
    public static java.sql.Connection createConnection() {
    	Connection con=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();	
    	} 
        catch (NamingException e) {
        	e.printStackTrace();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        } 
    	return con;
    }
   
    public static int getTotalMhsAktif(String target_thsms, Vector v_scope_kdpst, String target_kdkmp, boolean include_malaikat, Connection con) {
    	int tot_mhs_aktif=0;     
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	ListIterator li=null;
    	boolean exit_close_con=false;
    	try {
    		if(con==null) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();	
        		exit_close_con=true;
    		}
    		String sql_scope_kdpst = "";
    		String sql_scope_id = "";
    		li = v_scope_kdpst.listIterator();
    		while(li.hasNext()) {
    			String kdpst = (String)li.next();
    			sql_scope_kdpst=sql_scope_kdpst+"KDPST='"+kdpst+"'";
    			if(li.hasNext()) {
    				sql_scope_kdpst=sql_scope_kdpst+" OR ";
    			}
    		}
    		if(!Checker.isStringNullOrEmpty(sql_scope_kdpst)) {
    			sql_scope_kdpst="("+sql_scope_kdpst+")";
    		}
    		stmt = con.prepareStatement("select distinct ID_OBJ from OBJECT where "+sql_scope_kdpst+" and KODE_KAMPUS_DOMISILI='"+target_kdkmp+"'");
    		//System.out.println("sql==select distinct ID_OBJ from OBJECT where "+sql_scope_kdpst+" and KODE_KAMPUS_DOMISILI='"+target_kdkmp+"'");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			sql_scope_id="ID_OBJ="+rs.getString(1);
    			while(rs.next()) {
    				sql_scope_id=sql_scope_id+" OR ID_OBJ="+rs.getString(1);
    			}
    		}
    		String sql = "select count(A.NPMHSTRNLM) from " + 
    				"(select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM='"+target_thsms+"') A inner join " + 
    				"CIVITAS B on A.NPMHSTRNLM=B.NPMHSMSMHS where ("+sql_scope_id+") ";
    		if(!include_malaikat) {
    			sql = sql + " and MALAIKAT=false";
    		}
    		//System.out.println("sql=="+sql);
    		stmt = con.prepareStatement(sql);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			tot_mhs_aktif = rs.getInt(1);	
    		}
    		
    		//System.out.println("tot_mhs_aktif="+tot_mhs_aktif);
    		
    		
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
		    if(exit_close_con) {
		    	if (con!=null) try { con.close();} catch (Exception ignore){}	
		    }
		    
        }	
    	return tot_mhs_aktif;
    }
    
    
    public static int getTotalMhsCutiDanNonaktif(String target_thsms, Vector v_scope_kdpst, String target_kdkmp, boolean include_malaikat, Connection con) {
    	int tot_mhs_cuti=0;     
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	ListIterator li=null;
    	boolean exit_close_con=false;
    	try {
    		if(con==null) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();	
        		exit_close_con=true;
    		}
    		String sql_scope_kdpst = "";
    		String sql_scope_id = "";
    		li = v_scope_kdpst.listIterator();
    		while(li.hasNext()) {
    			String kdpst = (String)li.next();
    			sql_scope_kdpst=sql_scope_kdpst+"KDPST='"+kdpst+"'";
    			if(li.hasNext()) {
    				sql_scope_kdpst=sql_scope_kdpst+" OR ";
    			}
    		}
    		if(!Checker.isStringNullOrEmpty(sql_scope_kdpst)) {
    			sql_scope_kdpst="("+sql_scope_kdpst+")";
    		}
    		stmt = con.prepareStatement("select distinct ID_OBJ from OBJECT where "+sql_scope_kdpst+" and KODE_KAMPUS_DOMISILI='"+target_kdkmp+"'");
    		//System.out.println("sql==select distinct ID_OBJ from OBJECT where "+sql_scope_kdpst+" and KODE_KAMPUS_DOMISILI='"+target_kdkmp+"'");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			sql_scope_id="ID_OBJ="+rs.getString(1);
    			while(rs.next()) {
    				sql_scope_id=sql_scope_id+" OR ID_OBJ="+rs.getString(1);
    			}
    		}
    		String sql="select count(distinct NPMHS) from TRLSM  inner join CIVITAS on NPMHS=NPMHSMSMHS where ("+sql_scope_id+") and (STMHS='C' OR STMHS='N') and THSMS='"+target_thsms+"' ";
    		if(!include_malaikat) {
    			sql = sql + " and MALAIKAT=false";
    		}
    		//System.out.println("sql=="+sql);
    		stmt = con.prepareStatement(sql);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			tot_mhs_cuti = rs.getInt(1);	
    		}
    		
    		//System.out.println("tot_mhs_cuti="+tot_mhs_cuti);
    		
    		
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
		    if(exit_close_con) {
		    	if (con!=null) try { con.close();} catch (Exception ignore){}	
		    }
        }	
    	return tot_mhs_cuti;
    }
    
    
}

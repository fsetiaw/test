package beans.dbase.trnlp;

import beans.dbase.SearchDb;
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
 * Session Bean implementation class SearchDbTrnlp
 */
@Stateless
@LocalBean
public class SearchDbTrnlp extends SearchDb {
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
    public SearchDbTrnlp() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbTrnlp(String operatorNpm) {
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
    public SearchDbTrnlp(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public int getTotSksdi(String npmhs) {
    	int tot_sks = 0;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select SKSMKTRNLP from TRNLP where NPMHSTRNLP=?");
			stmt.setString(1, npmhs);
			rs = stmt.executeQuery();
			while(rs.next()) {
				tot_sks = tot_sks+rs.getInt(1);
			}
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return tot_sks;
	}
    
    /*
     * DEPRECATED 
     * HARUSNYA di SearchDbInfoMhs
     */
    public String getListMhsPindahan() {
    	String tkn_npmhs = null;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select NPMHSMSMHS from CIVITAS where STPIDMSMHS='P'");
			rs = stmt.executeQuery();
			while(rs.next()) {
				String npmhs = rs.getString(1);
				if(tkn_npmhs==null) {
					tkn_npmhs = new String(npmhs);
				}
				else {
					tkn_npmhs = tkn_npmhs+"`"+npmhs;
				}
				
			}
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return tkn_npmhs;
	}
    
    


}

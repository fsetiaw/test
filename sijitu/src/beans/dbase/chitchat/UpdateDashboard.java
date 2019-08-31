package beans.dbase.chitchat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

import beans.dbase.SearchDb;

/**
 * Session Bean implementation class UpdateDashboard
 */
@Stateless
@LocalBean
public class UpdateDashboard extends SearchDb {
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
    public UpdateDashboard() {
        // TODO Auto-generated constructor stub
    }
    
    public UpdateDashboard(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    
    public void autoRegisterUrNpmhs() {
    	//Vector v = null;
    	StringTokenizer st = null;
    	String listBaru="";
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/CHITCHAT");
			con = ds.getConnection();
			stmt = con.prepareStatement("insert IGNORE INTO MEMBER(NPM,FULL_NAME)values(?,?)");
			stmt.setString(1, this.operatorNpm);
			stmt.setString(2, this.operatorNmm.toUpperCase());
			stmt.executeUpdate();
			
			stmt = con.prepareStatement("select ID from MEMBER where NPM=?");
			stmt.setString(1, this.operatorNpm);
			rs = stmt.executeQuery();
			rs.next();
			long member_id = rs.getLong(1);
			//stmt.executeUpdate();
		//	get
			stmt = con.prepareStatement("INSERT IGNORE INTO DASHBOARD(MEMBER_ID)values(?)");
			stmt.setLong(1, member_id);
			stmt.executeUpdate();
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

}

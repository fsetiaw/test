package beans.dbase.feeder.importer;

import beans.dbase.SearchDb;
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
 * Session Bean implementation class BobotNilaiImporter
 */
@Stateless
@LocalBean
public class CleansDataDb extends SearchDb {
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
    public CleansDataDb() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public CleansDataDb(String operatorNpm) {
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
    public CleansDataDb(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

    public Vector filterTableBobotNilai() {
    	//String thsms_now = Checker.getThsmsNow();
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/FEEDER_IMPORTER");
			con = ds.getConnection();
			stmt = con.prepareStatement("select id_sms from sms");
			rs = stmt.executeQuery();
			while(rs.next()) {
				if(v==null) {
					v = new Vector();
					li = v.listIterator();
				}
				li.add(rs.getString(1));;
			}
    		if(v!=null) {
    			li = v.listIterator();
    			String sql = "";
    			while(li.hasNext()) {
    				String id = (String)li.next();
    				sql = sql+"id_sms<>'"+id+"'";
    				if(li.hasNext()) {
    					sql = sql +" and ";
    				}
    			}
    			sql = "delete from bobot_nilai where "+sql;
    			stmt = con.prepareStatement(sql);
    			int i = stmt.executeUpdate();
    			System.out.println(sql+" = "+i);
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

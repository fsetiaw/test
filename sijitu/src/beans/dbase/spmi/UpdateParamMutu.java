package beans.dbase.spmi;

import beans.dbase.UpdateDb;
import beans.tools.Checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class UpdateParamMutu
 */
@Stateless
@LocalBean
public class UpdateParamMutu extends UpdateDb {
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
    public UpdateParamMutu() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateParamMutu(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    
    public int addNuParam(String nama_param) {
    	int updated=0;
    	try {
    		if(!Checker.isStringNullOrEmpty(nama_param)) {
    			Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	stmt = con.prepareStatement("insert ignore into PARAMETER_CAPAIAN(NM_PARAM)values('"+nama_param.trim().toUpperCase()+"')");
            	updated = stmt.executeUpdate();
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
}

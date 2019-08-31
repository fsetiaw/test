package beans.dbase.mhs.kurikulum;

import beans.dbase.mhs.UpdateDbInfoMhs;
import beans.tools.Checker;

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

/**
 * Session Bean implementation class UpdateDbInfoKurikulum
 */
@Stateless
@LocalBean
public class UpdateDbInfoKurikulum extends UpdateDbInfoMhs {
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
    public UpdateDbInfoKurikulum() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDbInfoMhs#UpdateDbInfoMhs(String)
     */
    public UpdateDbInfoKurikulum(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.tknOperatorNickname = getTknOprNickname();
    	//System.out.println("tknOperatorNickname1="+this.tknOperatorNickname);
    	this.petugas = cekApaUsrPetugas();
        // TODO Auto-generated constructor stub
    }
    
    public int setKoMhs(String tkn_npm, int idko) {
    	int updated = 0;
    	if(!Checker.isStringNullOrEmpty(tkn_npm)) {
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("UPDATE EXT_CIVITAS set KRKLMMSMHS=? where NPMHSMSMHS=?");
        		String seperator = Checker.getSeperatorYgDigunakan(tkn_npm);
        		StringTokenizer st = new StringTokenizer(tkn_npm,seperator);
        		while(st.hasMoreTokens()) {
        			String npmhs = st.nextToken();
        			stmt.setInt(1, idko);
        			stmt.setString(2, npmhs);
        			updated = updated + stmt.executeUpdate();
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

package beans.dbase.market_place.pulsa;

import beans.dbase.SearchDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class ToolMip
 */
@Stateless
@LocalBean
public class ToolMip extends SearchDb {
	String operatorNpm;
	String operatorNmm;
	String tknOperatorNickname;
	boolean petugas;
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;  
	static final String mlink_usr_id = "javra";;
	static final String app_tkn = "1208-x980-1a5c111";
	static final String pin = "6633";
	/**
     * @see SearchDb#SearchDb()
     */
    public ToolMip() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public ToolMip(String operatorNpm) {
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
    public ToolMip(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public String getMlink_usr_id() {
    	return mlink_usr_id;
    }
    
    public String getApp_tkn() {
    	return app_tkn;
    }
    
    public String GenSign(String trx_id) {
    	String signatur = null;
    	try {
    		signatur = mlink_usr_id+trx_id+app_tkn;
    		signatur = DigestUtils.shaHex(signatur);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	return signatur;
    }	

}

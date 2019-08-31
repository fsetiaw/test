package beans.dbase.visi_misi_pt;

import beans.dbase.UpdateDb;
import beans.tools.Checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class UpdateVisiMisiPt
 */
@Stateless
@LocalBean
public class UpdateVisiMisiPt extends UpdateDb {
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
    public UpdateVisiMisiPt() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateVisiMisiPt(String operatorNpm) {
        super(operatorNpm);
        // TODO Auto-generated constructor stub
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
    }
    
    public int updateVisiMisiTujuanNilaiPt(String visi,String misi,String tujuan,String nilai) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		String kdpti = Checker.getKdpti();
    		//System.out.println("kdpti="+kdpti);
    		stmt = con.prepareStatement("update VISI_MISI_PT set VISI=?,MISI=?,TUJUAN=?,NILAI=? where KDPTI=?");
    		
    		int i=1;
    		if(Checker.isStringNullOrEmpty(visi)) {
    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(i++, visi);
    		}
    		if(Checker.isStringNullOrEmpty(misi)) {
    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(i++, misi);
    		}
    		if(Checker.isStringNullOrEmpty(tujuan)) {
    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(i++, tujuan);
    		}
    		if(Checker.isStringNullOrEmpty(nilai)) {
    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(i++, nilai);
    		}
    		stmt.setString(i++, kdpti);
    		updated = stmt.executeUpdate();
    		if(updated<1) {
    			i=1;
    			stmt = con.prepareStatement("INSERT INTO VISI_MISI_PT(KDPTI,VISI,MISI,TUJUAN,NILAI,AKTIF)values(?,?,?,?,?,?)");
    			stmt.setString(i++, kdpti);
    			if(Checker.isStringNullOrEmpty(visi)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, visi);
        		}
        		if(Checker.isStringNullOrEmpty(misi)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, misi);
        		}
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, tujuan);
        		}
        		if(Checker.isStringNullOrEmpty(nilai)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, nilai);
        		}
        		stmt.setBoolean(i++, true);
        		updated = stmt.executeUpdate();
    		}
    	} 
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
		catch (Exception ex) {
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

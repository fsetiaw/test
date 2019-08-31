package beans.dbase.trlsm.maintenance;

import beans.dbase.SearchDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ListIterator;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class SearchMaintenanceTrlsm
 */
@Stateless
@LocalBean
public class SearchMaintenanceTrlsm extends SearchDb {
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
    public SearchMaintenanceTrlsm() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchMaintenanceTrlsm(String operatorNpm) {
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
    public SearchMaintenanceTrlsm(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public Vector getUnsyncDataAntaraPengajuanDanTrsm() {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("SELECT TARGET_THSMS_PENGAJUAN as 'THSMS PENGAJUAN',TIPE_PENGAJUAN as 'JENIS PENGAJUAN',CREATOR_NPM as NPM,CREATOR_NMM as NAMA  FROM TOPIK_PENGAJUAN A LEFT join TRLSM B on (CREATOR_NPM=NPMHS AND TARGET_THSMS_PENGAJUAN=THSMS) where (TIPE_PENGAJUAN='KELULUSAN' OR TIPE_PENGAJUAN='KELUAR' OR TIPE_PENGAJUAN='PINDAH_PRODI' OR TIPE_PENGAJUAN='CUTI' OR TIPE_PENGAJUAN='DO') AND LOCKED=TRUE AND REJECTED IS NULL AND NPMHS IS NULL");
			rs = stmt.executeQuery();
			while(rs.next()) {
				String nmmhs = ""+rs.getString("NMMHSMSMHS");
				String nimhs = ""+rs.getString("NIMHSMSMHS");
				String tglls = ""+rs.getDate("TGLLS");
				li.add(nmmhs+"`"+nimhs+"`"+tglls);
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
    	return v;
	}	

}

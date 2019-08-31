package beans.dbase.penelitian;

import beans.dbase.UpdateDb;
import beans.sistem.AskSystem;

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
 * Session Bean implementation class UpdateDbRiwayatKaryaIlmiah
 */
@Stateless
@LocalBean
public class UpdateDbRiwayatKaryaIlmiah extends UpdateDb {
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
     * @see UpdateDb#UpdateDb()
     */
    public UpdateDbRiwayatKaryaIlmiah() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbRiwayatKaryaIlmiah(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.tknOperatorNickname = getTknOprNickname();
    	this.petugas = cekApaUsrPetugas();
        // TODO Auto-generated constructor stub
    }
    
    public int prosesPengajuanUjianAkhirMhs(String thsms_pengajuan,String npmhs,String kdpst,String info_karya_ilmiah) {
    	int updated = 0;
    	if(info_karya_ilmiah!=null) {
    		StringTokenizer st2 = new StringTokenizer(info_karya_ilmiah,"~");
        	String tipe_ujian = st2.nextToken();
        	String judul = st2.nextToken();
        	String nama_file = st2.nextToken();
        	int bln = AskSystem.getCurrentMonth_tanpaMinus1();
        	try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		/*
        		 * UPDATE DULU:
        		 * update row yg belum filename == null,artinya pertama kali,  kalo tidak ada yg null maka lakukan
        		 * insert, jadi riwayat perbaikan tesisnya selalu ada (filename boleh kosong utk malaikat)
        		 * Nantinya setiap krs ada MK jenis sidang maka otomatis diinsert disini, sebagai data blawl
        		 */
        		stmt = con.prepareStatement("UPDATE RIWAYAT_KARYA_ILMIAH set FILENAME=?,JUDUL=? where THSMS=? and NPMHS=? and TIPE=? and JUDUL is null");
        		stmt.setString(1, nama_file);
        		stmt.setString(2, judul);
        		stmt.setString(3, thsms_pengajuan);
        		stmt.setString(4, npmhs);
        		stmt.setString(5, tipe_ujian);
        		updated = stmt.executeUpdate();
        		if(updated<1) {
        			//go insert
        			stmt = con.prepareStatement("insert into RIWAYAT_KARYA_ILMIAH (THSMS,NPMHS,KDPST,TIPE,FILENAME,JUDUL,BULAN)values(?,?,?,?,?,?,?)");
            		stmt.setString(1, thsms_pengajuan);
            		stmt.setString(2, npmhs);
            		stmt.setString(3, kdpst);
            		stmt.setString(4, tipe_ujian);
            		stmt.setString(5, nama_file);
            		stmt.setString(6, judul);
            		stmt.setInt(7, bln);
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
    	}
    	return updated;
    }
    

}

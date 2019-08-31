package beans.dbase.spmi.riwayat.pengendalian;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Tool;

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
 * Session Bean implementation class SrcHistPeningkatan
 */
@Stateless
@LocalBean
public class SrcHistPeningkatan_or extends SearchDb {
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
    public SrcHistPeningkatan_or() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SrcHistPeningkatan_or(String operatorNpm) {
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
    public SrcHistPeningkatan_or(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    //
    
    public Vector getListRiwayatPeningkatan(int versi_id, int std_isi_id, int norut_man, int offset, int limit) {
    	/*
    	 * std_isi_id menentukan apakah ini generik ato untuk kdpst tertentu
    	 */
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
    		String sql = "select * from RIWAYAT_PENINGKATAN A inner join STANDARD_MANUAL_PENINGKATAN B on (A.VERSI_ID=B.VERSI_ID AND A.STD_ISI_ID=B.STD_ISI_ID AND A.NORUT=B.NORUT) where A.VERSI_ID=? AND A.STD_ISI_ID=? AND A.NORUT=? order by A.ID_PLAN desc limit ?,?";
    		stmt = con.prepareStatement(sql);
    		stmt.setInt(1, versi_id);
    		stmt.setInt(2, std_isi_id);
    		stmt.setInt(3, norut_man);
    		stmt.setInt(4, offset);
    		stmt.setInt(5, limit);
    		rs = stmt.executeQuery();
    		boolean first = true;
    		while(rs.next()) {
    			if(first) {
    				first = false;
    				v = new Vector();
    				li = v.listIterator();
    			}
    			String id_plan= rs.getString("A.ID_PLAN");
    			String tgl_sta= rs.getString("A.TGL_STA_KEGIATAN");
    			String waktu_sta= rs.getString("A.WAKTU_STA_KEGIATAN");
    			String tgl_end= rs.getString("A.TGL_END_KEGIATAN");
    			String waktu_end= rs.getString("A.WAKTU_END_KEGIATAN");
    			String stamp_sta= rs.getString("A.TIMESTAMP_KEGIATAN_STA");
    			String stamp_end= rs.getString("A.TIMESTAMP_KEGIATAN_END");
    			String nama_kegiatan= rs.getString("A.NAMA_KEGIATAN");
    			String jenis_kegiatan= rs.getString("A.JENIS_KEGIATAN");
    			String tujuan_kegiatan= rs.getString("A.KETERANGAN_SINGKAT_TUJUAN_KEGIATAN");
    			String isi_kegiatan= rs.getString("A.ISI_KEGIATAN");
    			String tkn_job_tanggung= rs.getString("A.PENANGGUNG_JAWAB_KEGIATAN");
    			String tkn_job_target= rs.getString("A.AUDIENCE_KEGIATAN");
    			String tkn_dok_kegiatan= rs.getString("A.DOKUMEN_KEGIATAN");
    			String hasil_kegiatan= rs.getString("A.KETERANGAN_HASIL_KEGIATAN");
    			String kegiatan_started= rs.getString("A.KEGIATAN_STARTED");
    			String kegiatan_ended= rs.getString("A.KEGIATAN_SELESAI");
    			String note_kegiatan= rs.getString("A.NOTE");
    			String tgl_rumus_set= rs.getString("A.TGL_RUMUS");
    			String tgl_cek_set= rs.getString("A.TGL_CEK");
    			String tgl_stuju_set= rs.getString("A.TGL_STUJU");
    			String tgl_tetap_set= rs.getString("A.TGL_TETAP");
    			String tgl_kendali_set= rs.getString("A.TGL_KENDALI");
    			String tgl_next_kegiatan= rs.getString("A.TGL_NEXT_KEGIATAN");
    			String waktu_next_kegiatan= rs.getString("A.WAKTU_NEXT_KEGIATAN");
    			
    			String tgl_sta_std = rs.getString("B.TGL_STA");
    			String tgl_end_std = rs.getString("B.TGL_END");
    			String tkn_jab_rumus = rs.getString("B.TKN_JAB_PERUMUS");
    			String tkn_jab_cek = rs.getString("B.TKN_JAB_PERIKSA");
    			String tkn_jab_stuju = rs.getString("B.TKN_JAB_SETUJU");
    			String tkn_jab_tetap = rs.getString("B.TKN_JAB_PENETAP");
    			String tkn_jab_kendali = rs.getString("B.TKN_JAB_KENDALI");
    			String tujuan = rs.getString("B.TUJUAN");
    			String lingkup = rs.getString("B.LINGKUP");
    			String definisi = rs.getString("B.DEFINISI");
    			String prosedur = rs.getString("B.PROSEDUR");
    			String kuali= rs.getString("B.KUALIFIKASI");
    			String doc = rs.getString("B.DOKUMEN");
    			String ref = rs.getString("B.REFERENSI");
    			
    			String tgl_rumus_std_set= rs.getString("A.TGL_RUMUS_STD");
    			String tgl_cek_std_set= rs.getString("A.TGL_CEK_STD");
    			String tgl_stuju_std_set= rs.getString("A.TGL_STUJU_STD");
    			String tgl_tetap_std_set= rs.getString("A.TGL_TETAP_STD");
    			String tgl_kendali_std_set= rs.getString("A.TGL_KENDALI_STD");
    			
    			String tmp = id_plan+"~"+versi_id+"~"+std_isi_id+"~"+norut_man+"~"+tgl_sta+"~"+waktu_sta+"~"+tgl_end+"~"+waktu_end+"~"+stamp_sta+"~"+stamp_end+"~"+nama_kegiatan+"~"+jenis_kegiatan+"~"+tujuan_kegiatan+"~"+isi_kegiatan+"~"+tkn_job_tanggung+"~"+tkn_job_target+"~"+tkn_dok_kegiatan+"~"+hasil_kegiatan+"~"+kegiatan_started+"~"+kegiatan_ended+"~"+note_kegiatan+"~"+tgl_rumus_set+"~"+tgl_cek_set+"~"+tgl_stuju_set+"~"+tgl_tetap_set+"~"+tgl_kendali_set+"~"+tgl_next_kegiatan+"~"+waktu_next_kegiatan+"~"+tgl_sta_std+"~"+tgl_end_std+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendali+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kuali+"~"+doc+"~"+ref+"~"+tgl_rumus_std_set+"~"+tgl_cek_std_set+"~"+tgl_stuju_std_set+"~"+tgl_tetap_std_set+"~"+tgl_kendali_std_set;
    			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
    			//System.out.println("TMP="+tmp);
    			li.add(tmp);
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

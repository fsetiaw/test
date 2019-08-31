package beans.dbase.spmi.riwayat.pengendalian;

import beans.dbase.UpdateDb;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class UpdHistPeningkatan
 */
@Stateless
@LocalBean
public class UpdHistPeningkatan_ori extends UpdateDb {
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
    public UpdHistPeningkatan_ori() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdHistPeningkatan_ori(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }

    public int updateRiwayatPeningkatan(String id_plan,String id_versi,String id_std_isi,String norut_man,String tgl_sta,String waktu_sta,String nama_kegiatan,String jenis_kegiatan,String tujuan_kegiatan,String isi_kegiatan,String[] job_jawab,String[]job_target,String[]dok_kegiatan,String status_kegiatan_sdh_mulai) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//coba update dulu
        	if(Checker.isStringNullOrEmpty(id_plan)) {
        		System.out.println("insert mode--");
        		String sql = "INSERT INTO RIWAYAT_PENINGKATAN(VERSI_ID,STD_ISI_ID,NORUT,TGL_STA_KEGIATAN,WAKTU_STA_KEGIATAN,TIMESTAMP_KEGIATAN_STA,NAMA_KEGIATAN,JENIS_KEGIATAN,KETERANGAN_SINGKAT_TUJUAN_KEGIATAN,ISI_KEGIATAN,PENANGGUNG_JAWAB_KEGIATAN,AUDIENCE_KEGIATAN,DOKUMEN_KEGIATAN,KEGIATAN_STARTED)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        		stmt = con.prepareStatement(sql);
        		int i=1;
        		//VERSI_ID
        		stmt.setInt(i++, Integer.parseInt(id_versi));
        		//STD_ISI_ID
        		stmt.setInt(i++, Integer.parseInt(id_std_isi));
        		//NORUT
        		stmt.setInt(i++, Integer.parseInt(norut_man));
        		//TGL_STA_KEGIATAN
        		if(Checker.isStringNullOrEmpty(tgl_sta)) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			tgl_sta = Converter.autoConvertDateFormat(tgl_sta, "-");
            		stmt.setDate(i++, java.sql.Date.valueOf(tgl_sta));
        		}
        		
        		//WAKTU_STA_KEGIATAN
        		if(Checker.isStringNullOrEmpty(waktu_sta)) {
        			stmt.setNull(i++, java.sql.Types.TIME);
        		}
        		else {
        			stmt.setTime(i++, Time.valueOf(waktu_sta+":00"));
        		}
        		
        		//TIMESTAMP_KEGIATAN_STA
        		stmt.setTimestamp(i++, AskSystem.getCurrentTimestamp());
        		//NAMA_KEGIATAN
        		stmt.setString(i++, nama_kegiatan);
        		//JENIS_KEGIATAN
        		stmt.setString(i++, jenis_kegiatan);
        		//KETERANGAN_SINGKAT_TUJUAN_KEGIATAN
        		stmt.setString(i++, tujuan_kegiatan);
        		//ISI_KEGIATAN
        		stmt.setString(i++, isi_kegiatan);
        		//PENANGGUNG_JAWAB_KEGIATAN
        		if(job_jawab!=null && job_jawab.length>0) {
        			String tmp = "";
        			for(int j=0;j<job_jawab.length;j++) {
        				tmp = tmp + job_jawab[j];
        				if(j+1<job_jawab.length) {
        					tmp = tmp +"`";
        				}
        			}
        			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "`");
        			stmt.setString(i++, tmp);
        		}
        		else {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		//AUDIENCE_KEGIATAN
        		if(job_target!=null && job_target.length>0) {
        			String tmp = "";
        			for(int j=0;j<job_target.length;j++) {
        				tmp = tmp + job_target[j];
        				if(j+1<job_target.length) {
        					tmp = tmp +"`";
        				}
        			}
        			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "`");
        			stmt.setString(i++, tmp);
        		}
        		else {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		//DOKUMEN_KEGIATAN
        		if(dok_kegiatan!=null && dok_kegiatan.length>0) {
        			String tmp = "";
        			for(int j=0;j<dok_kegiatan.length;j++) {
        				tmp = tmp + dok_kegiatan[j];
        				if(j+1<dok_kegiatan.length) {
        					tmp = tmp +"`";
        				}
        			}
        			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "`");
        			stmt.setString(i++, tmp);
        		}
        		else {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		//KEGIATAN_STARTED
        		if(Checker.isStringNullOrEmpty(status_kegiatan_sdh_mulai)) {
        			stmt.setNull(i++, java.sql.Types.BOOLEAN);
        		}
        		else {
        			if(status_kegiatan_sdh_mulai.equalsIgnoreCase("true")||status_kegiatan_sdh_mulai.equalsIgnoreCase("1")) {
        				stmt.setBoolean(i++, true);
        			}
        			else if(status_kegiatan_sdh_mulai.equalsIgnoreCase("false")||status_kegiatan_sdh_mulai.equalsIgnoreCase("0")) {
        				stmt.setBoolean(i++, false);
        			}
        		}
        		updated = stmt.executeUpdate();
        		
        	}
        	else {
        		System.out.println("update mode--"); 
        		//String sql = "UPDATE RIWAYAT_PENINGKATAN set TGL_STA_KEGIATAN=?,WAKTU_STA_KEGIATAN=?,TIMESTAMP_KEGIATAN_STA=?,NAMA_KEGIATAN=?,JENIS_KEGIATAN=?,KETERANGAN_SINGKAT_TUJUAN_KEGIATAN=?,ISI_KEGIATAN=?,PENANGGUNG_JAWAB_KEGIATAN=?,AUDIENCE_KEGIATAN=?,DOKUMEN_KEGIATAN=?,KEGIATAN_STARTED=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?";
        		String sql = "UPDATE RIWAYAT_PENINGKATAN set TGL_STA_KEGIATAN=?,WAKTU_STA_KEGIATAN=?,TIMESTAMP_KEGIATAN_STA=?,NAMA_KEGIATAN=?,JENIS_KEGIATAN=?,KETERANGAN_SINGKAT_TUJUAN_KEGIATAN=?,ISI_KEGIATAN=?,PENANGGUNG_JAWAB_KEGIATAN=?,AUDIENCE_KEGIATAN=?,DOKUMEN_KEGIATAN=?,KEGIATAN_STARTED=? where ID_PLAN=?";
        		stmt = con.prepareStatement(sql);
        		int i=1;
        		//TGL_STA_KEGIATAN
        		if(Checker.isStringNullOrEmpty(tgl_sta)) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			tgl_sta = Converter.autoConvertDateFormat(tgl_sta, "-");
            		stmt.setDate(i++, java.sql.Date.valueOf(tgl_sta));
        		}
        		
        		//WAKTU_STA_KEGIATAN
        		if(Checker.isStringNullOrEmpty(waktu_sta)) {
        			stmt.setNull(i++, java.sql.Types.TIME);
        		}
        		else {
        			stmt.setTime(i++, Time.valueOf(waktu_sta+":00"));
        		}
        		
        		//TIMESTAMP_KEGIATAN_STA
        		stmt.setTimestamp(i++, AskSystem.getCurrentTimestamp());
        		//NAMA_KEGIATAN
        		stmt.setString(i++, nama_kegiatan);
        		//JENIS_KEGIATAN
        		stmt.setString(i++, jenis_kegiatan);
        		//KETERANGAN_SINGKAT_TUJUAN_KEGIATAN
        		stmt.setString(i++, tujuan_kegiatan);
        		//ISI_KEGIATAN
        		stmt.setString(i++, isi_kegiatan);
        		//PENANGGUNG_JAWAB_KEGIATAN
        		if(job_jawab!=null && job_jawab.length>0) {
        			String tmp = "";
        			for(int j=0;j<job_jawab.length;j++) {
        				tmp = tmp + job_jawab[j];
        				if(j+1<job_jawab.length) {
        					tmp = tmp +"`";
        				}
        			}
        			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "`");
        			stmt.setString(i++, tmp);
        		}
        		else {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		//AUDIENCE_KEGIATAN
        		if(job_target!=null && job_target.length>0) {
        			String tmp = "";
        			for(int j=0;j<job_target.length;j++) {
        				tmp = tmp + job_target[j];
        				if(j+1<job_target.length) {
        					tmp = tmp +"`";
        				}
        			}
        			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "`");
        			stmt.setString(i++, tmp);
        		}
        		else {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		//DOKUMEN_KEGIATAN
        		if(dok_kegiatan!=null && dok_kegiatan.length>0) {
        			String tmp = "";
        			for(int j=0;j<dok_kegiatan.length;j++) {
        				tmp = tmp + dok_kegiatan[j];
        				if(j+1<dok_kegiatan.length) {
        					tmp = tmp +"`";
        				}
        			}
        			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "`");
        			stmt.setString(i++, tmp);
        		}
        		else {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		//KEGIATAN_STARTED
        		if(Checker.isStringNullOrEmpty(status_kegiatan_sdh_mulai)) {
        			stmt.setNull(i++, java.sql.Types.BOOLEAN);
        		}
        		else {
        			if(status_kegiatan_sdh_mulai.equalsIgnoreCase("true")||status_kegiatan_sdh_mulai.equalsIgnoreCase("1")) {
        				stmt.setBoolean(i++, true);
        			}
        			else if(status_kegiatan_sdh_mulai.equalsIgnoreCase("false")||status_kegiatan_sdh_mulai.equalsIgnoreCase("0")) {
        				stmt.setBoolean(i++, false);
        			}
        		}
        		//ID_PLAN
        		stmt.setInt(i++, Integer.parseInt(id_plan));
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
    
    
    public int updateRiwayatHasilPeningkatan(String id_plan, String hasil, String note, String tgl_end, String waktu_end, String sebagai_tgl_penetapan, String versi_id, String std_isi_id, String norut_man) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//coba update dulu
        	
        	//System.out.println("update mode"); 
        	if(sebagai_tgl_penetapan!=null&&sebagai_tgl_penetapan.contains("_std")) {
        		/*
        		 * bedanya yg diupdate tgl manual ato tgl std
        		 * UPDATE TGL TSTD
        		 */
        		String sql = "UPDATE RIWAYAT_PENINGKATAN set KETERANGAN_HASIL_KEGIATAN=?,NOTE=?,TGL_END_KEGIATAN=?,WAKTU_END_KEGIATAN=?,TIMESTAMP_KEGIATAN_END=?,KEGIATAN_SELESAI=?,TGL_RUMUS_STD=?,TGL_CEK_STD=?,TGL_STUJU_STD=?,TGL_TETAP_STD=?,TGL_KENDALI_STD=? where ID_PLAN=?";
        		stmt = con.prepareStatement(sql);
        		int i=1;
        		//KETERANGAN_HASIL_KEGIATAN=?,
        		stmt.setString(i++, hasil);
        		//NOTE=?,
        		if(Checker.isStringNullOrEmpty(note)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, note);
        		}	
        		//TGL_END_KEGIATAN=?,
        		boolean ended = false;
        		if(Checker.isStringNullOrEmpty(tgl_end)) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			tgl_end = Converter.autoConvertDateFormat(tgl_end, "-");
            		stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
            		ended = true;
        		}
        		
        		//WAKTU_END_KEGIATAN=?,
        		if(Checker.isStringNullOrEmpty(waktu_end)) {
        			stmt.setNull(i++, java.sql.Types.TIME);
        		}
        		else {
        			stmt.setTime(i++, Time.valueOf(waktu_end+":00"));
        		}
        		//TIMESTAMP_KEGIATAN_END=?,KEGIATAN_SELESAI=?,
        		if(ended) {
        			stmt.setTimestamp(i++, AskSystem.getCurrentTimestamp());
        			stmt.setBoolean(i++, true);
        		}
        		else {
        			stmt.setNull(i++, java.sql.Types.TIMESTAMP);
        			stmt.setBoolean(i++, false);
        		}	
        		//TGL_RUMUS=?,TGL_CEK=?,TGL_STUJU=?,TGL_TETAP=?,TGL_KENDALI=? where ID_PLAN=?";
        		if(sebagai_tgl_penetapan.equalsIgnoreCase("no")) {
        			stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
        		}
        		else if(sebagai_tgl_penetapan.equalsIgnoreCase("rumus_std")) {
        				stmt.setBoolean(i++, true);
        				stmt.setBoolean(i++, false);
        				stmt.setBoolean(i++, false);
        				stmt.setBoolean(i++, false);
        				stmt.setBoolean(i++, false);
        		}
        		else if(sebagai_tgl_penetapan.equalsIgnoreCase("cek_std")) {
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, true);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    			}
    			else if(sebagai_tgl_penetapan.equalsIgnoreCase("stuju_std")) {
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, true);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    			}
    			else if(sebagai_tgl_penetapan.equalsIgnoreCase("tetap_std")) {
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, true);
    				stmt.setBoolean(i++, false);
    			}
    			else if(sebagai_tgl_penetapan.equalsIgnoreCase("kendali_std")) {
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, true);
    			}
        		
        		stmt.setInt(i++, Integer.parseInt(id_plan));
        		
        		updated = stmt.executeUpdate();	
            	if(updated>0 && !Checker.isStringNullOrEmpty(sebagai_tgl_penetapan) && !sebagai_tgl_penetapan.equalsIgnoreCase("no")) {
            		i=1;
            		if(sebagai_tgl_penetapan.equalsIgnoreCase("rumus_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENINGKATAN set TGL_RUMUS_STD=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        				stmt.setInt(i++, Integer.parseInt(norut_man));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("cek_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENINGKATAN set TGL_CEK_STD=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        				
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("stuju_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENINGKATAN set TGL_STUJU_STD=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("tetap_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENINGKATAN set TGL_TETAP_STD=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("kendali_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENINGKATAN set TGL_KENDALI_STD=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        			}
            		stmt.executeUpdate();	
            	}
        	}
        	else {
        		// UPDATE TGL MANUAL
        		String sql = "UPDATE RIWAYAT_PENINGKATAN set KETERANGAN_HASIL_KEGIATAN=?,NOTE=?,TGL_END_KEGIATAN=?,WAKTU_END_KEGIATAN=?,TIMESTAMP_KEGIATAN_END=?,KEGIATAN_SELESAI=?,TGL_RUMUS=?,TGL_CEK=?,TGL_STUJU=?,TGL_TETAP=?,TGL_KENDALI=? where ID_PLAN=?";
        		stmt = con.prepareStatement(sql);
        		int i=1;
        		//KETERANGAN_HASIL_KEGIATAN=?,
        		stmt.setString(i++, hasil);
        		//NOTE=?,
        		if(Checker.isStringNullOrEmpty(note)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, note);
        		}	
        		//TGL_END_KEGIATAN=?,
        		boolean ended = false;
        		if(Checker.isStringNullOrEmpty(tgl_end)) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			tgl_end = Converter.autoConvertDateFormat(tgl_end, "-");
            		stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
            		ended = true;
        		}
        		
        		//WAKTU_END_KEGIATAN=?,
        		if(Checker.isStringNullOrEmpty(waktu_end)) {
        			stmt.setNull(i++, java.sql.Types.TIME);
        		}
        		else {
        			stmt.setTime(i++, Time.valueOf(waktu_end+":00"));
        		}
        		//TIMESTAMP_KEGIATAN_END=?,KEGIATAN_SELESAI=?,
        		if(ended) {
        			stmt.setTimestamp(i++, AskSystem.getCurrentTimestamp());
        			stmt.setBoolean(i++, true);
        		}
        		else {
        			stmt.setNull(i++, java.sql.Types.TIMESTAMP);
        			stmt.setBoolean(i++, false);
        		}	
        		//TGL_RUMUS=?,TGL_CEK=?,TGL_STUJU=?,TGL_TETAP=?,TGL_KENDALI=? where ID_PLAN=?";
        		if(sebagai_tgl_penetapan.equalsIgnoreCase("no")) {
        			stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
        		}
        		else if(sebagai_tgl_penetapan.equalsIgnoreCase("rumus")) {
        			stmt.setBoolean(i++, true);
        			stmt.setBoolean(i++, false);
        			stmt.setBoolean(i++, false);
        			stmt.setBoolean(i++, false);
        			stmt.setBoolean(i++, false);
        		}
        		else if(sebagai_tgl_penetapan.equalsIgnoreCase("cek")) {
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, true);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    			}
    			else if(sebagai_tgl_penetapan.equalsIgnoreCase("stuju")) {
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, true);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    			}
    			else if(sebagai_tgl_penetapan.equalsIgnoreCase("tetap")) {
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, true);
    				stmt.setBoolean(i++, false);
    			}
    			else if(sebagai_tgl_penetapan.equalsIgnoreCase("kendali")) {
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, false);
    				stmt.setBoolean(i++, true);
    			}
        		
        		stmt.setInt(i++, Integer.parseInt(id_plan));
        		
        		updated = stmt.executeUpdate();	
            	if(updated>0 && !Checker.isStringNullOrEmpty(sebagai_tgl_penetapan) && !sebagai_tgl_penetapan.equalsIgnoreCase("no")) {
            		i=1;
            		if(sebagai_tgl_penetapan.equalsIgnoreCase("rumus")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENINGKATAN set TGL_RUMUS=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        				stmt.setInt(i++, Integer.parseInt(norut_man));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("cek")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENINGKATAN set TGL_CEK=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        				
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("stuju")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENINGKATAN set TGL_STUJU=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("tetap")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENINGKATAN set TGL_TETAP=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("kendali")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENINGKATAN set TGL_KENDALI=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        			}
            		stmt.executeUpdate();	
            	}
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

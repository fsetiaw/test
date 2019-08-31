package beans.dbase.spmi;

import beans.dbase.UpdateDb;
import beans.dbase.spmi.form.UpdateForm;
import beans.dbase.spmi.request.SearchRequest;
import beans.dbase.spmi.request.UpdateRequest;
import beans.sistem.AskSystem;
import beans.tools.Checker;
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
 * Session Bean implementation class UpdateManualMutu
 */
@Stateless
@LocalBean
public class UpdateManualMutu extends UpdateDb {
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
    public UpdateManualMutu() {
        super();
        //TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateManualMutu(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        //TODO Auto-generated constructor stub
    }
    
    public int toogleAktifasiMan(String tipe_manual, int norut_man, int id_versi, int id_std_isi, boolean current_value) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(current_value==true) {
        		//lanjut set end standard, tidak bisa diaktifkan kembali, harus buat ersi baru
        		java.sql.Date end_dt = AskSystem.getTodayDate(); 
        		if(tipe_manual.contains("eval")||tipe_manual.contains("EVAL")) {
        			stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI set TGL_END=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
            		stmt.setDate(1, end_dt);
            		stmt.setInt(2, id_versi);
            		stmt.setInt(3, id_std_isi);
            		stmt.setInt(4, norut_man);
            		updated = stmt.executeUpdate();	
        		}
        		else if(tipe_manual.contains("ngendal")||tipe_manual.contains("NGENDAL")||tipe_manual.contains("kendal")||tipe_manual.contains("KENDAL")) {
        			stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN set TGL_END=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
            		stmt.setDate(1, end_dt);
            		stmt.setInt(2, id_versi);
            		stmt.setInt(3, id_std_isi);
            		stmt.setInt(4, norut_man);
            		updated = stmt.executeUpdate();	
        		}
        		else if(tipe_manual.contains("PLAN")||tipe_manual.contains("plan")||tipe_manual.contains("rencana")||tipe_manual.contains("RENCANA")) {
        			stmt = con.prepareStatement("update STANDARD_MANUAL_PERENCANAAN set TGL_END=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
            		stmt.setDate(1, end_dt);
            		stmt.setInt(2, id_versi);
            		stmt.setInt(3, id_std_isi);
            		stmt.setInt(4, norut_man);
            		updated = stmt.executeUpdate();	
        		}
        		else if(tipe_manual.contains("LAKSA")||tipe_manual.contains("laksa")||tipe_manual.contains("pelaksanaan")||tipe_manual.contains("PELAKSANAAN")) {
        			stmt = con.prepareStatement("update STANDARD_MANUAL_PELAKSANAAN set TGL_END=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
            		stmt.setDate(1, end_dt);
            		stmt.setInt(2, id_versi);
            		stmt.setInt(3, id_std_isi);
            		stmt.setInt(4, norut_man);
            		updated = stmt.executeUpdate();	
        		}
        		else if(tipe_manual.contains("ningkat")||tipe_manual.contains("NINGKAT")||tipe_manual.contains("peningkatan")||tipe_manual.contains("PENINGKATAN")) {
        			stmt = con.prepareStatement("update STANDARD_MANUAL_PENINGKATAN set TGL_END=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
            		stmt.setDate(1, end_dt);
            		stmt.setInt(2, id_versi);
            		stmt.setInt(3, id_std_isi);
            		stmt.setInt(4, norut_man);
            		updated = stmt.executeUpdate();	
        		}
        	}
        	else {
        		/*
        		 * YG DAPAT DIAKTIFKAN ADALAH STD YG BELUM PERNAH AKTIF SEBELUMNYA [TGL_STOP_AKTIF SUDAH TERISI]
            	 * 
            	 * Jadi CEK DULU APA TGL STOP masih null
            	 */	
        		java.sql.Date sta_dt = AskSystem.getTodayDate(); 
        		if(tipe_manual.contains("eval")||tipe_manual.contains("EVAL")) {
        			stmt = con.prepareStatement("select TGL_END from STANDARD_MANUAL_EVALUASI where VERSI_ID=? and STD_ISI_ID=? and NORUT=? and TGL_END is null");
        			stmt.setInt(1, id_versi);
            		stmt.setInt(2, id_std_isi);
            		stmt.setInt(3, norut_man);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			//lanjut aktifkan
            			
                		//stmt = con.prepareStatement("update STANDARD_ISI_TABLE set AKTIF=?,TGL_MULAI_AKTIF=? where ID=?");
                		stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI set TGL_STA=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
                		stmt.setDate(1, sta_dt);
                		stmt.setInt(2, id_versi);
                		stmt.setInt(3, id_std_isi);
                		stmt.setInt(4, norut_man);
                		updated = stmt.executeUpdate();	
            		}
            		else {
            			//SUDAH KADALUARSA : BUAT VERS BARU
            		}
        		}
        		else if(tipe_manual.contains("ngendal")||tipe_manual.contains("NGENDAL")||tipe_manual.contains("kendal")||tipe_manual.contains("KENDAL")) {
        			stmt = con.prepareStatement("select TGL_END from STANDARD_MANUAL_PENGENDALIAN where VERSI_ID=? and STD_ISI_ID=? and NORUT=? and TGL_END is null");
        			stmt.setInt(1, id_versi);
            		stmt.setInt(2, id_std_isi);
            		stmt.setInt(3, norut_man);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			//lanjut aktifkan
            			
                		//stmt = con.prepareStatement("update STANDARD_ISI_TABLE set AKTIF=?,TGL_MULAI_AKTIF=? where ID=?");
                		stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN set TGL_STA=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
                		stmt.setDate(1, sta_dt);
                		stmt.setInt(2, id_versi);
                		stmt.setInt(3, id_std_isi);
                		stmt.setInt(4, norut_man);
                		updated = stmt.executeUpdate();	
            		}
            		else {
            			//SUDAH KADALUARSA : BUAT VERS BARU
            		}
        		}
        		else if(tipe_manual.contains("PLAN")||tipe_manual.contains("plan")||tipe_manual.contains("rencana")||tipe_manual.contains("RENCANA")) {
        			stmt = con.prepareStatement("select TGL_END from STANDARD_MANUAL_PERENCANAAN where VERSI_ID=? and STD_ISI_ID=? and NORUT=? and TGL_END is null");
        			stmt.setInt(1, id_versi);
            		stmt.setInt(2, id_std_isi);
            		stmt.setInt(3, norut_man);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			//lanjut aktifkan
            			
                		//stmt = con.prepareStatement("update STANDARD_ISI_TABLE set AKTIF=?,TGL_MULAI_AKTIF=? where ID=?");
                		stmt = con.prepareStatement("update STANDARD_MANUAL_PERENCANAAN set TGL_STA=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
                		stmt.setDate(1, sta_dt);
                		stmt.setInt(2, id_versi);
                		stmt.setInt(3, id_std_isi);
                		stmt.setInt(4, norut_man);
                		updated = stmt.executeUpdate();	
            		}
            		else {
            			//SUDAH KADALUARSA : BUAT VERS BARU
            		}
        		}
        		else if(tipe_manual.contains("LAKSA")||tipe_manual.contains("laksa")||tipe_manual.contains("pelaksanaan")||tipe_manual.contains("PELAKSANAAN")) {
        			stmt = con.prepareStatement("select TGL_END from STANDARD_MANUAL_PELAKSANAAN where VERSI_ID=? and STD_ISI_ID=? and NORUT=? and TGL_END is null");
        			stmt.setInt(1, id_versi);
            		stmt.setInt(2, id_std_isi);
            		stmt.setInt(3, norut_man);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			//lanjut aktifkan
            			
                		//stmt = con.prepareStatement("update STANDARD_ISI_TABLE set AKTIF=?,TGL_MULAI_AKTIF=? where ID=?");
                		stmt = con.prepareStatement("update STANDARD_MANUAL_PELAKSANAAN set TGL_STA=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
                		stmt.setDate(1, sta_dt);
                		stmt.setInt(2, id_versi);
                		stmt.setInt(3, id_std_isi);
                		stmt.setInt(4, norut_man);
                		updated = stmt.executeUpdate();	
            		}
            		else {
            			//SUDAH KADALUARSA : BUAT VERS BARU
            		}
        		}
        		else if(tipe_manual.contains("ningkat")||tipe_manual.contains("NINGKAT")||tipe_manual.contains("peningkatan")||tipe_manual.contains("PENINGKATAN")) {
        			stmt = con.prepareStatement("select TGL_END from STANDARD_MANUAL_PENINGKATAN where VERSI_ID=? and STD_ISI_ID=? and NORUT=? and TGL_END is null");
        			stmt.setInt(1, id_versi);
            		stmt.setInt(2, id_std_isi);
            		stmt.setInt(3, norut_man);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			//lanjut aktifkan
            			
                		//stmt = con.prepareStatement("update STANDARD_ISI_TABLE set AKTIF=?,TGL_MULAI_AKTIF=? where ID=?");
                		stmt = con.prepareStatement("update STANDARD_MANUAL_PENINGKATAN set TGL_STA=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
                		stmt.setDate(1, sta_dt);
                		stmt.setInt(2, id_versi);
                		stmt.setInt(3, id_std_isi);
                		stmt.setInt(4, norut_man);
                		updated = stmt.executeUpdate();	
            		}
            		else {
            			//SUDAH KADALUARSA : BUAT VERS BARU
            		}
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
    
    
    public int toogleAktifasiManUmum(String tipe_manual, int id_versi, int id_std, boolean status_aktif) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(status_aktif==true) {
        		//lanjut set end standard, tidak bisa diaktifkan kembali, harus buat ersi baru
        		/*
        		 * versi manual selalu sama, jadi kalo di stop maka seluruh manual ppepee di stop dan di copy sebagai manual versi
        		 * baru dan baru kemudian di edit
        		 */
        		java.sql.Date end_dt = AskSystem.getTodayDate(); 
        		//if(tipe_manual.contains("eval")||tipe_manual.contains("EVAL")) {
        		//stop 
        		int nu_versi=id_std+1;
        		stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI_UMUM set TGL_END=? where VERSI_ID=? and ID_STD=?");
            	stmt.setDate(1, end_dt);
            	stmt.setInt(2, id_versi);
            	stmt.setInt(3, id_std);
            	updated = stmt.executeUpdate();	
            	StringTokenizer st = null;
            	String sql = "SELECT TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_EVALUASI_UMUM where VERSI_ID=? and ID_STD=?";
            	stmt = con.prepareStatement(sql);
            	stmt.setInt(1, id_versi);
            	stmt.setInt(2, id_std);
            	rs = stmt.executeQuery();
            	String tmp = "";
            	if(rs.next()) {
            		int i=1;
            		//TKN_JAB_PERUMUS
            		String tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		String tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		String tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		String tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		String tkn_jab_kendali = rs.getString(i++);
            		//TKN_JAB_PETUGAS_LAP
            		String tkn_jab_lap = rs.getString(i++);
            		//TUJUAN
            		String tujuan = rs.getString(i++);
            		//LINGKUP
            		String lingkup = rs.getString(i++);
            		//DEFINISI
            		String definisi = rs.getString(i++);
            		//PROSEDUR
            		String prosedur = rs.getString(i++);
            		//KUALIFIKASI
            		String kualifikasi = rs.getString(i++);
            		//DOKUMEN
            		String dokumen = rs.getString(i++);
            		//REFERENSI
            		String referesi = rs.getString(i++);
            		tmp = tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendali+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokumen+"~"+referesi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		
            	}
            	if(!Checker.isStringNullOrEmpty(tmp)) {
            		//insert sebagai versi baru
            		st = new StringTokenizer(tmp,"~");
            		//TKN_JAB_PERUMUS
            		String tkn_jab_rumus = st.nextToken();
            		//TKN_JAB_PERIKSA
            		String tkn_jab_cek = st.nextToken();
            		//TKN_JAB_SETUJU
            		String tkn_jab_stuju = st.nextToken();
            		//TKN_JAB_PENETAP
            		String tkn_jab_tetap = st.nextToken();
            		//TKN_JAB_KENDALI
            		String tkn_jab_kendali = st.nextToken();
            		//TKN_JAB_PETUGAS_LAP
            		String tkn_jab_lap = st.nextToken();
            		//TUJUAN
            		String tujuan = st.nextToken();
            		//LINGKUP
            		String lingkup = st.nextToken();
            		//DEFINISI
            		String definisi = st.nextToken();
            		//PROSEDUR
            		String prosedur = st.nextToken();
            		//KUALIFIKASI
            		String kualifikasi = st.nextToken();
            		//DOKUMEN
            		String dokumen = st.nextToken();
            		//REFERENSI
            		String referensi = st.nextToken();
            		sql = "INSERT INTO STANDARD_MANUAL_EVALUASI_UMUM(VERSI_ID,ID_STD,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            		stmt = con.prepareStatement(sql);
            		int i=1;
            		//VERSI_ID,
            		stmt.setInt(i++, nu_versi);
            		//ID_STD
            		stmt.setInt(i++, id_std);
            		//TKN_JAB_PERUMUS
            		stmt.setString(i++, tkn_jab_rumus);
            		//TKN_JAB_PERIKSA
            		stmt.setString(i++, tkn_jab_cek);
            		//TKN_JAB_SETUJU
            		stmt.setString(i++, tkn_jab_stuju);
            		//TKN_JAB_PENETAP
            		stmt.setString(i++, tkn_jab_tetap);
            		//TKN_JAB_KENDALI
            		stmt.setString(i++, tkn_jab_kendali);
            		//TKN_JAB_PETUGAS_LAP
            		stmt.setString(i++, tkn_jab_lap);
            		//TUJUAN
            		stmt.setString(i++, tujuan);
            		//LINGKUP)
            		stmt.setString(i++, lingkup);
            		//DEFINISI
            		stmt.setString(i++, definisi);
            		//PROSEDUR
            		stmt.setString(i++, prosedur);
            		//KUALIFIKASI
            		stmt.setString(i++, kualifikasi);
            		//DOKUMEN
            		stmt.setString(i++, dokumen);
            		//REFERENSI
            		stmt.setString(i++, referensi);
            		stmt.executeUpdate();
            	}
        		//}
        		//else if(tipe_manual.contains("ngendal")||tipe_manual.contains("NGENDAL")||tipe_manual.contains("kendal")||tipe_manual.contains("KENDAL")) {
        		stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN_UMUM set TGL_END=? where VERSI_ID=? and ID_STD=?");
            	stmt.setDate(1, end_dt);
            	stmt.setInt(2, id_versi);
            	stmt.setInt(3, id_std);
            	updated = stmt.executeUpdate();	
            	st = null;
            	sql = "SELECT TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PENGENDALIAN_UMUM where VERSI_ID=? and ID_STD=?";
            	stmt = con.prepareStatement(sql);
            	stmt.setInt(1, id_versi);
            	stmt.setInt(2, id_std);
            	rs = stmt.executeQuery();
            	tmp = "";
            	if(rs.next()) {
            		int i=1;
            		//TKN_JAB_PERUMUS
            		String tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		String tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		String tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		String tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		String tkn_jab_kendali = rs.getString(i++);
            		//TKN_JAB_PETUGAS_LAP
            		String tkn_jab_lap = rs.getString(i++);
            		//TUJUAN
            		String tujuan = rs.getString(i++);
            		//LINGKUP
            		String lingkup = rs.getString(i++);
            		//DEFINISI
            		String definisi = rs.getString(i++);
            		//PROSEDUR
            		String prosedur = rs.getString(i++);
            		//KUALIFIKASI
            		String kualifikasi = rs.getString(i++);
            		//DOKUMEN
            		String dokumen = rs.getString(i++);
            		//REFERENSI
            		String referesi = rs.getString(i++);
            		tmp = tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendali+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokumen+"~"+referesi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		
            	}
            	if(!Checker.isStringNullOrEmpty(tmp)) {
            		//insert sebagai versi baru
            		st = new StringTokenizer(tmp,"~");
            		//TKN_JAB_PERUMUS
            		String tkn_jab_rumus = st.nextToken();
            		//TKN_JAB_PERIKSA
            		String tkn_jab_cek = st.nextToken();
            		//TKN_JAB_SETUJU
            		String tkn_jab_stuju = st.nextToken();
            		//TKN_JAB_PENETAP
            		String tkn_jab_tetap = st.nextToken();
            		//TKN_JAB_KENDALI
            		String tkn_jab_kendali = st.nextToken();
            		//TKN_JAB_PETUGAS_LAP
            		String tkn_jab_lap = st.nextToken();
            		//TUJUAN
            		String tujuan = st.nextToken();
            		//LINGKUP
            		String lingkup = st.nextToken();
            		//DEFINISI
            		String definisi = st.nextToken();
            		//PROSEDUR
            		String prosedur = st.nextToken();
            		//KUALIFIKASI
            		String kualifikasi = st.nextToken();
            		//DOKUMEN
            		String dokumen = st.nextToken();
            		//REFERENSI
            		String referensi = st.nextToken();
            		sql = "INSERT INTO STANDARD_MANUAL_PENGENDALIAN_UMUM(VERSI_ID,ID_STD,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            		stmt = con.prepareStatement(sql);
            		int i=1;
            		//VERSI_ID,
            		stmt.setInt(i++, nu_versi);
            		//ID_STD
            		stmt.setInt(i++, id_std);
            		//TKN_JAB_PERUMUS
            		stmt.setString(i++, tkn_jab_rumus);
            		//TKN_JAB_PERIKSA
            		stmt.setString(i++, tkn_jab_cek);
            		//TKN_JAB_SETUJU
            		stmt.setString(i++, tkn_jab_stuju);
            		//TKN_JAB_PENETAP
            		stmt.setString(i++, tkn_jab_tetap);
            		//TKN_JAB_KENDALI
            		stmt.setString(i++, tkn_jab_kendali);
            		//TKN_JAB_PETUGAS_LAP
            		stmt.setString(i++, tkn_jab_lap);
            		//TUJUAN
            		stmt.setString(i++, tujuan);
            		//LINGKUP)
            		stmt.setString(i++, lingkup);
            		//DEFINISI
            		stmt.setString(i++, definisi);
            		//PROSEDUR
            		stmt.setString(i++, prosedur);
            		//KUALIFIKASI
            		stmt.setString(i++, kualifikasi);
            		//DOKUMEN
            		stmt.setString(i++, dokumen);
            		//REFERENSI
            		stmt.setString(i++, referensi);
            		stmt.executeUpdate();
            	}
            	
            	
            	//}
        		//else if(tipe_manual.contains("PLAN")||tipe_manual.contains("plan")||tipe_manual.contains("rencana")||tipe_manual.contains("RENCANA")) {
        		stmt = con.prepareStatement("update STANDARD_MANUAL_PERENCANAAN_UMUM set TGL_END=? where VERSI_ID=? and ID_STD=?");
            	stmt.setDate(1, end_dt);
            	stmt.setInt(2, id_versi);
            	stmt.setInt(3, id_std);
            	updated = stmt.executeUpdate();
            	st = null;
            	sql = "SELECT TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PERENCANAAN_UMUM where VERSI_ID=? and ID_STD=?";
            	stmt = con.prepareStatement(sql);
            	stmt.setInt(1, id_versi);
            	stmt.setInt(2, id_std);
            	rs = stmt.executeQuery();
            	tmp = "";
            	if(rs.next()) {
            		int i=1;
            		//TKN_JAB_PERUMUS
            		String tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		String tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		String tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		String tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		String tkn_jab_kendali = rs.getString(i++);
            		//TKN_JAB_PETUGAS_LAP
            		String tkn_jab_lap = rs.getString(i++);
            		//TUJUAN
            		String tujuan = rs.getString(i++);
            		//LINGKUP
            		String lingkup = rs.getString(i++);
            		//DEFINISI
            		String definisi = rs.getString(i++);
            		//PROSEDUR
            		String prosedur = rs.getString(i++);
            		//KUALIFIKASI
            		String kualifikasi = rs.getString(i++);
            		//DOKUMEN
            		String dokumen = rs.getString(i++);
            		//REFERENSI
            		String referesi = rs.getString(i++);
            		tmp = tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendali+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokumen+"~"+referesi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		
            	}
            	if(!Checker.isStringNullOrEmpty(tmp)) {
            		//insert sebagai versi baru
            		st = new StringTokenizer(tmp,"~");
            		//TKN_JAB_PERUMUS
            		String tkn_jab_rumus = st.nextToken();
            		//TKN_JAB_PERIKSA
            		String tkn_jab_cek = st.nextToken();
            		//TKN_JAB_SETUJU
            		String tkn_jab_stuju = st.nextToken();
            		//TKN_JAB_PENETAP
            		String tkn_jab_tetap = st.nextToken();
            		//TKN_JAB_KENDALI
            		String tkn_jab_kendali = st.nextToken();
            		//TKN_JAB_PETUGAS_LAP
            		String tkn_jab_lap = st.nextToken();
            		//TUJUAN
            		String tujuan = st.nextToken();
            		//LINGKUP
            		String lingkup = st.nextToken();
            		//DEFINISI
            		String definisi = st.nextToken();
            		//PROSEDUR
            		String prosedur = st.nextToken();
            		//KUALIFIKASI
            		String kualifikasi = st.nextToken();
            		//DOKUMEN
            		String dokumen = st.nextToken();
            		//REFERENSI
            		String referensi = st.nextToken();
            		sql = "INSERT INTO STANDARD_MANUAL_PERENCANAAN_UMUM(VERSI_ID,ID_STD,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            		stmt = con.prepareStatement(sql);
            		int i=1;
            		//VERSI_ID,
            		stmt.setInt(i++, nu_versi);
            		//ID_STD
            		stmt.setInt(i++, id_std);
            		//TKN_JAB_PERUMUS
            		stmt.setString(i++, tkn_jab_rumus);
            		//TKN_JAB_PERIKSA
            		stmt.setString(i++, tkn_jab_cek);
            		//TKN_JAB_SETUJU
            		stmt.setString(i++, tkn_jab_stuju);
            		//TKN_JAB_PENETAP
            		stmt.setString(i++, tkn_jab_tetap);
            		//TKN_JAB_KENDALI
            		stmt.setString(i++, tkn_jab_kendali);
            		//TKN_JAB_PETUGAS_LAP
            		stmt.setString(i++, tkn_jab_lap);
            		//TUJUAN
            		stmt.setString(i++, tujuan);
            		//LINGKUP)
            		stmt.setString(i++, lingkup);
            		//DEFINISI
            		stmt.setString(i++, definisi);
            		//PROSEDUR
            		stmt.setString(i++, prosedur);
            		//KUALIFIKASI
            		stmt.setString(i++, kualifikasi);
            		//DOKUMEN
            		stmt.setString(i++, dokumen);
            		//REFERENSI
            		stmt.setString(i++, referensi);
            		stmt.executeUpdate();
            	}	
        		//}
        		//else if(tipe_manual.contains("LAKSA")||tipe_manual.contains("laksa")||tipe_manual.contains("pelaksanaan")||tipe_manual.contains("PELAKSANAAN")) {
        		stmt = con.prepareStatement("update STANDARD_MANUAL_PELAKSANAAN_UMUM set TGL_END=? where VERSI_ID=? and ID_STD=?");
            	stmt.setDate(1, end_dt);
            	stmt.setInt(2, id_versi);
            	stmt.setInt(3, id_std);
            	updated = stmt.executeUpdate();
            	st = null;
            	sql = "SELECT TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PELAKSANAAN_UMUM where VERSI_ID=? and ID_STD=?";
            	stmt = con.prepareStatement(sql);
            	stmt.setInt(1, id_versi);
            	stmt.setInt(2, id_std);
            	rs = stmt.executeQuery();
            	tmp = "";
            	if(rs.next()) {
            		int i=1;
            		//TKN_JAB_PERUMUS
            		String tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		String tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		String tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		String tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		String tkn_jab_kendali = rs.getString(i++);
            		//TKN_JAB_PETUGAS_LAP
            		String tkn_jab_lap = rs.getString(i++);
            		//TUJUAN
            		String tujuan = rs.getString(i++);
            		//LINGKUP
            		String lingkup = rs.getString(i++);
            		//DEFINISI
            		String definisi = rs.getString(i++);
            		//PROSEDUR
            		String prosedur = rs.getString(i++);
            		//KUALIFIKASI
            		String kualifikasi = rs.getString(i++);
            		//DOKUMEN
            		String dokumen = rs.getString(i++);
            		//REFERENSI
            		String referesi = rs.getString(i++);
            		tmp = tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendali+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokumen+"~"+referesi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		
            	}
            	if(!Checker.isStringNullOrEmpty(tmp)) {
            		//insert sebagai versi baru
            		st = new StringTokenizer(tmp,"~");
            		//TKN_JAB_PERUMUS
            		String tkn_jab_rumus = st.nextToken();
            		//TKN_JAB_PERIKSA
            		String tkn_jab_cek = st.nextToken();
            		//TKN_JAB_SETUJU
            		String tkn_jab_stuju = st.nextToken();
            		//TKN_JAB_PENETAP
            		String tkn_jab_tetap = st.nextToken();
            		//TKN_JAB_KENDALI
            		String tkn_jab_kendali = st.nextToken();
            		//TKN_JAB_PETUGAS_LAP
            		String tkn_jab_lap = st.nextToken();
            		//TUJUAN
            		String tujuan = st.nextToken();
            		//LINGKUP
            		String lingkup = st.nextToken();
            		//DEFINISI
            		String definisi = st.nextToken();
            		//PROSEDUR
            		String prosedur = st.nextToken();
            		//KUALIFIKASI
            		String kualifikasi = st.nextToken();
            		//DOKUMEN
            		String dokumen = st.nextToken();
            		//REFERENSI
            		String referensi = st.nextToken();
            		sql = "INSERT INTO STANDARD_MANUAL_PELAKSANAAN_UMUM(VERSI_ID,ID_STD,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            		stmt = con.prepareStatement(sql);
            		int i=1;
            		//VERSI_ID,
            		stmt.setInt(i++, nu_versi);
            		//ID_STD
            		stmt.setInt(i++, id_std);
            		//TKN_JAB_PERUMUS
            		stmt.setString(i++, tkn_jab_rumus);
            		//TKN_JAB_PERIKSA
            		stmt.setString(i++, tkn_jab_cek);
            		//TKN_JAB_SETUJU
            		stmt.setString(i++, tkn_jab_stuju);
            		//TKN_JAB_PENETAP
            		stmt.setString(i++, tkn_jab_tetap);
            		//TKN_JAB_KENDALI
            		stmt.setString(i++, tkn_jab_kendali);
            		//TKN_JAB_PETUGAS_LAP
            		stmt.setString(i++, tkn_jab_lap);
            		//TUJUAN
            		stmt.setString(i++, tujuan);
            		//LINGKUP)
            		stmt.setString(i++, lingkup);
            		//DEFINISI
            		stmt.setString(i++, definisi);
            		//PROSEDUR
            		stmt.setString(i++, prosedur);
            		//KUALIFIKASI
            		stmt.setString(i++, kualifikasi);
            		//DOKUMEN
            		stmt.setString(i++, dokumen);
            		//REFERENSI
            		stmt.setString(i++, referensi);
            		stmt.executeUpdate();
            	}
        		//}
        		//else if(tipe_manual.contains("ningkat")||tipe_manual.contains("NINGKAT")||tipe_manual.contains("peningkatan")||tipe_manual.contains("PENINGKATAN")) {
        		stmt = con.prepareStatement("update STANDARD_MANUAL_PENINGKATAN_UMUM set TGL_END=? where VERSI_ID=? and ID_STD=?");
            	stmt.setDate(1, end_dt);
            	stmt.setInt(2, id_versi);
            	stmt.setInt(3, id_std);
            	updated = stmt.executeUpdate();
            	st = null;
            	sql = "SELECT TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PENINGKATAN_UMUM where VERSI_ID=? and ID_STD=?";
            	stmt = con.prepareStatement(sql);
            	stmt.setInt(1, id_versi);
            	stmt.setInt(2, id_std);
            	rs = stmt.executeQuery();
            	tmp = "";
            	if(rs.next()) {
            		int i=1;
            		//TKN_JAB_PERUMUS
            		String tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		String tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		String tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		String tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		String tkn_jab_kendali = rs.getString(i++);
            		//TKN_JAB_PETUGAS_LAP
            		String tkn_jab_lap = rs.getString(i++);
            		//TUJUAN
            		String tujuan = rs.getString(i++);
            		//LINGKUP
            		String lingkup = rs.getString(i++);
            		//DEFINISI
            		String definisi = rs.getString(i++);
            		//PROSEDUR
            		String prosedur = rs.getString(i++);
            		//KUALIFIKASI
            		String kualifikasi = rs.getString(i++);
            		//DOKUMEN
            		String dokumen = rs.getString(i++);
            		//REFERENSI
            		String referesi = rs.getString(i++);
            		tmp = tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendali+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokumen+"~"+referesi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		
            	}
            	if(!Checker.isStringNullOrEmpty(tmp)) {
            		//insert sebagai versi baru
            		st = new StringTokenizer(tmp,"~");
            		//TKN_JAB_PERUMUS
            		String tkn_jab_rumus = st.nextToken();
            		//TKN_JAB_PERIKSA
            		String tkn_jab_cek = st.nextToken();
            		//TKN_JAB_SETUJU
            		String tkn_jab_stuju = st.nextToken();
            		//TKN_JAB_PENETAP
            		String tkn_jab_tetap = st.nextToken();
            		//TKN_JAB_KENDALI
            		String tkn_jab_kendali = st.nextToken();
            		//TKN_JAB_PETUGAS_LAP
            		String tkn_jab_lap = st.nextToken();
            		//TUJUAN
            		String tujuan = st.nextToken();
            		//LINGKUP
            		String lingkup = st.nextToken();
            		//DEFINISI
            		String definisi = st.nextToken();
            		//PROSEDUR
            		String prosedur = st.nextToken();
            		//KUALIFIKASI
            		String kualifikasi = st.nextToken();
            		//DOKUMEN
            		String dokumen = st.nextToken();
            		//REFERENSI
            		String referensi = st.nextToken();
            		sql = "INSERT INTO STANDARD_MANUAL_PENINGKATAN_UMUM(VERSI_ID,ID_STD,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            		stmt = con.prepareStatement(sql);
            		int i=1;
            		//VERSI_ID,
            		stmt.setInt(i++, nu_versi);
            		//ID_STD
            		stmt.setInt(i++, id_std);
            		//TKN_JAB_PERUMUS
            		stmt.setString(i++, tkn_jab_rumus);
            		//TKN_JAB_PERIKSA
            		stmt.setString(i++, tkn_jab_cek);
            		//TKN_JAB_SETUJU
            		stmt.setString(i++, tkn_jab_stuju);
            		//TKN_JAB_PENETAP
            		stmt.setString(i++, tkn_jab_tetap);
            		//TKN_JAB_KENDALI
            		stmt.setString(i++, tkn_jab_kendali);
            		//TKN_JAB_PETUGAS_LAP
            		stmt.setString(i++, tkn_jab_lap);
            		//TUJUAN
            		stmt.setString(i++, tujuan);
            		//LINGKUP)
            		stmt.setString(i++, lingkup);
            		//DEFINISI
            		stmt.setString(i++, definisi);
            		//PROSEDUR
            		stmt.setString(i++, prosedur);
            		//KUALIFIKASI
            		stmt.setString(i++, kualifikasi);
            		//DOKUMEN
            		stmt.setString(i++, dokumen);
            		//REFERENSI
            		stmt.setString(i++, referensi);
            		stmt.executeUpdate();
            	}
        		//}
            		
        	}
        	else {
        		/*
        		 * YG DAPAT DIAKTIFKAN ADALAH STD YG BELUM PERNAH AKTIF SEBELUMNYA [TGL_STOP_AKTIF SUDAH TERISI]
            	 * 
            	 * Jadi CEK DULU APA TGL STOP masih null
            	 */	
        		java.sql.Date sta_dt = AskSystem.getTodayDate(); 
        		if(tipe_manual.contains("eval")||tipe_manual.contains("EVAL")) {
        			stmt = con.prepareStatement("select TGL_END from STANDARD_MANUAL_EVALUASI_UMUM where VERSI_ID=? and ID_STD=? and TGL_END is null");
        			stmt.setInt(1, id_versi);
        			stmt.setInt(2, id_std);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			//lanjut aktifkan
            			
                		//stmt = con.prepareStatement("update STANDARD_ISI_TABLE set AKTIF=?,TGL_MULAI_AKTIF=? where ID=?");
                		stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI_UMUM set TGL_STA=? where VERSI_ID=? and ID_STD=?");
                		stmt.setDate(1, sta_dt);
                		stmt.setInt(2, id_versi);
                		stmt.setInt(3, id_std);
                		updated = stmt.executeUpdate();	
            		}
            		else {
            			//SUDAH KADALUARSA : BUAT VERS BARU
            		}
        		}
        		else if(tipe_manual.contains("ngendal")||tipe_manual.contains("NGENDAL")||tipe_manual.contains("kendal")||tipe_manual.contains("KENDAL")) {
        			stmt = con.prepareStatement("select TGL_END from STANDARD_MANUAL_PENGENDALIAN_UMUM where VERSI_ID=? and ID_STD=? and TGL_END is null");
        			stmt.setInt(1, id_versi);
        			stmt.setInt(2, id_std);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			//lanjut aktifkan
            			
                		//stmt = con.prepareStatement("update STANDARD_ISI_TABLE set AKTIF=?,TGL_MULAI_AKTIF=? where ID=?");
                		stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN_UMUM set TGL_STA=? where VERSI_ID=? and ID_STD=?");
                		stmt.setDate(1, sta_dt);
                		stmt.setInt(2, id_versi);
                		stmt.setInt(3, id_std);
                		updated = stmt.executeUpdate();	
            		}
            		else {
            			//SUDAH KADALUARSA : BUAT VERS BARU
            		}
        		}
        		else if(tipe_manual.contains("PLAN")||tipe_manual.contains("plan")||tipe_manual.contains("rencana")||tipe_manual.contains("RENCANA")) {
        			stmt = con.prepareStatement("select TGL_END from STANDARD_MANUAL_PERENCANAAN_UMUM where VERSI_ID=? and ID_STD=? and TGL_END is null");
        			stmt.setInt(1, id_versi);
        			stmt.setInt(2, id_std);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			//lanjut aktifkan
            			
                		//stmt = con.prepareStatement("update STANDARD_ISI_TABLE set AKTIF=?,TGL_MULAI_AKTIF=? where ID=?");
                		stmt = con.prepareStatement("update STANDARD_MANUAL_PERENCANAAN_UMUM set TGL_STA=? where VERSI_ID=? and ID_STD=?");
                		stmt.setDate(1, sta_dt);
                		stmt.setInt(2, id_versi);
                		stmt.setInt(3, id_std);
                		updated = stmt.executeUpdate();	
            		}
            		else {
            			//SUDAH KADALUARSA : BUAT VERS BARU
            		}
        		}
        		else if(tipe_manual.contains("LAKSA")||tipe_manual.contains("laksa")||tipe_manual.contains("pelaksanaan")||tipe_manual.contains("PELAKSANAAN")) {
        			stmt = con.prepareStatement("select TGL_END from STANDARD_MANUAL_PELAKSANAAN_UMUM where VERSI_ID=? and ID_STD=? and TGL_END is null");
        			stmt.setInt(1, id_versi);
        			stmt.setInt(2, id_std);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			//lanjut aktifkan
            			
                		//stmt = con.prepareStatement("update STANDARD_ISI_TABLE set AKTIF=?,TGL_MULAI_AKTIF=? where ID=?");
                		stmt = con.prepareStatement("update STANDARD_MANUAL_PELAKSANAAN_UMUM set TGL_STA=? where VERSI_ID=? and ID_STD=?");
                		stmt.setDate(1, sta_dt);
                		stmt.setInt(2, id_versi);
                		stmt.setInt(3, id_std);
                		updated = stmt.executeUpdate();	
            		}
            		else {
            			//SUDAH KADALUARSA : BUAT VERS BARU
            		}
        		}
        		else if(tipe_manual.contains("ningkat")||tipe_manual.contains("NINGKAT")||tipe_manual.contains("peningkatan")||tipe_manual.contains("PENINGKATAN")) {
        			stmt = con.prepareStatement("select TGL_END from STANDARD_MANUAL_PENINGKATAN_UMUM where VERSI_ID=? and ID_STD=? and TGL_END is null");
        			stmt.setInt(1, id_versi);
        			stmt.setInt(2, id_std);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			//lanjut aktifkan
            			
                		//stmt = con.prepareStatement("update STANDARD_ISI_TABLE set AKTIF=?,TGL_MULAI_AKTIF=? where ID=?");
                		stmt = con.prepareStatement("update STANDARD_MANUAL_PENINGKATAN_UMUM set TGL_STA=? where VERSI_ID=? and ID_STD=?");
                		stmt.setDate(1, sta_dt);
                		stmt.setInt(2, id_versi);
                		stmt.setInt(3, id_std);
                		updated = stmt.executeUpdate();	
            		}
            		else {
            			//SUDAH KADALUARSA : BUAT VERS BARU
            		}
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

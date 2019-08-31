package beans.dbase.spmi.manual;

import beans.dbase.UpdateDb;
import beans.sistem.AskSystem;
import beans.tools.Checker;

import java.io.File;
import java.io.IOException;
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

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class UpdateManual
 */
@Stateless
@LocalBean
public class UpdateManual extends UpdateDb {
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
    public UpdateManual() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateManual(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    

    
    public int updateManualPengendalian(String mode, String no_urut_manual, String versi_id, String std_isi_id, String kdpst,String id_versi,String id_std_isi,String id_std,String at_menu_dash,String id_kendali,String tipe_sarpras,String catat_civitas,String fwdto,String[]job_rumus,	String[]job_cek,String[]job_stuju,String[]job_tetap,String[]job_kendali,String tujuan,String lingkup,String prosedur,String kuali,String doc,String ref,String definisi) {
    	int upd = 0;
    	int norut = 0;
    	try {
    		
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(mode.equalsIgnoreCase("insert")) {
        		//System.out.println("insert mode");
        		//insert manual baru
        		//1. cek no_urut terakhir
        		stmt = con.prepareStatement("select NORUT from STANDARD_MANUAL_PENGENDALIAN where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc limit 1");
        		stmt.setInt(1, Integer.parseInt(versi_id));
        		stmt.setInt(2, Integer.parseInt(std_isi_id));
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			norut = rs.getInt(1);
        		}
        		norut++;
        		String sql = "INSERT INTO STANDARD_MANUAL_PENGENDALIAN (VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
        		stmt = con.prepareStatement(sql);
        		
        		int i=1;
        		//VERSI_ID,
        		stmt.setInt(i++, Integer.parseInt(versi_id));
        		//STD_ISI_ID,
        		stmt.setInt(i++, Integer.parseInt(std_isi_id));
        		//NORUT,
        		stmt.setInt(i++, norut);
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		upd = stmt.executeUpdate();
        	}
        	else if(mode.equalsIgnoreCase("edit")) {
        		//update manual yg ada
        		//System.out.println("edit mode");
        		String sql = "UPDATE STANDARD_MANUAL_PENGENDALIAN SET TGL_STA=?,TGL_END=?,TKN_JAB_PERUMUS=?,TKN_JAB_PERIKSA=?,TKN_JAB_SETUJU=?,TKN_JAB_PENETAP=?,TKN_JAB_KENDALI=?,TUJUAN=?,LINGKUP=?,DEFINISI=?,PROSEDUR=?,KUALIFIKASI=?,DOKUMEN=?,REFERENSI=? WHERE VERSI_ID=? AND STD_ISI_ID=? AND NORUT=?";
        		int i=1;
        		stmt = con.prepareStatement(sql);
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		//VERSI_ID
        		stmt.setInt(i++, Integer.parseInt(versi_id));
        		//STD_ISI_ID
        		stmt.setInt(i++, Integer.parseInt(std_isi_id));
        		//NORUT=?";
        		stmt.setInt(i++, Integer.parseInt(no_urut_manual));
        		upd= stmt.executeUpdate();
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
    	//System.out.println("updated="+upd);
    	return upd;
    }	
    
    public int updateManualEvaluasi(String mode, String no_urut_manual, String versi_id, String std_isi_id, String kdpst,String id_versi,String id_std_isi,String id_std,String at_menu_dash,String id_kendali,String tipe_sarpras,String catat_civitas,String fwdto,String[]job_rumus,	String[]job_cek,String[]job_stuju,String[]job_tetap,String[]job_kendali,String[]job_survey,String tujuan,String lingkup,String prosedur,String kuali,String doc,String ref,String definisi) {
    	int upd = 0;
    	int norut = 0;
    	try {
    		
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(mode.equalsIgnoreCase("insert")) {
        		//System.out.println("insert mode");
        		//insert manual baru
        		//1. cek no_urut terakhir
        		stmt = con.prepareStatement("select NORUT from STANDARD_MANUAL_EVALUASI where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc limit 1");
        		stmt.setInt(1, Integer.parseInt(versi_id));
        		stmt.setInt(2, Integer.parseInt(std_isi_id));
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			norut = rs.getInt(1);
        		}
        		norut++;
        		String sql = "INSERT INTO STANDARD_MANUAL_EVALUASI (VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
        		stmt = con.prepareStatement(sql);
        		
        		int i=1;
        		//VERSI_ID,
        		stmt.setInt(i++, Integer.parseInt(versi_id));
        		//STD_ISI_ID,
        		stmt.setInt(i++, Integer.parseInt(std_isi_id));
        		//NORUT,
        		stmt.setInt(i++, norut);
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SURVEY
        		if(job_survey==null||job_survey.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_survey.length;j++) {
        				tmp = tmp+job_survey[j];
        				if(j+1<job_survey.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		upd = stmt.executeUpdate();
        	}
        	else if(mode.equalsIgnoreCase("edit")) {
        		//update manual yg ada
        		//System.out.println("edit mode");
        		String sql = "UPDATE STANDARD_MANUAL_EVALUASI SET TGL_STA=?,TGL_END=?,TKN_JAB_PERUMUS=?,TKN_JAB_PERIKSA=?,TKN_JAB_SETUJU=?,TKN_JAB_PENETAP=?,TKN_JAB_KENDALI=?,TKN_JAB_PETUGAS_LAP=?,TUJUAN=?,LINGKUP=?,DEFINISI=?,PROSEDUR=?,KUALIFIKASI=?,DOKUMEN=?,REFERENSI=? WHERE VERSI_ID=? AND STD_ISI_ID=? AND NORUT=?";
        		int i=1;
        		stmt = con.prepareStatement(sql);
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SURVEY
        		if(job_survey==null||job_survey.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_survey.length;j++) {
        				tmp = tmp+job_survey[j];
        				if(j+1<job_survey.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		//VERSI_ID
        		stmt.setInt(i++, Integer.parseInt(versi_id));
        		//STD_ISI_ID
        		stmt.setInt(i++, Integer.parseInt(std_isi_id));
        		//NORUT=?";
        		stmt.setInt(i++, Integer.parseInt(no_urut_manual));
        		upd= stmt.executeUpdate();
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
    	//System.out.println("updated="+upd);
    	return upd;
    }	
    
    
    public int updateManualPerencanaan(String mode, String no_urut_manual, String versi_id, String std_isi_id, String kdpst,String id_versi,String id_std_isi,String id_std,String at_menu_dash,String id_kendali,String tipe_sarpras,String catat_civitas,String fwdto,String[]job_rumus,	String[]job_cek,String[]job_stuju,String[]job_tetap,String[]job_kendali,String[]job_survey,String tujuan,String lingkup,String prosedur,String kuali,String doc,String ref,String definisi) {
    	int upd = 0;
    	int norut = 0;
    	try {
    		
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(mode.equalsIgnoreCase("insert")) {
        		//System.out.println("insert mode");
        		//insert manual baru
        		//1. cek no_urut terakhir
        		stmt = con.prepareStatement("select NORUT from STANDARD_MANUAL_PERENCANAAN where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc limit 1");
        		stmt.setInt(1, Integer.parseInt(versi_id));
        		stmt.setInt(2, Integer.parseInt(std_isi_id));
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			norut = rs.getInt(1);
        		}
        		norut++;
        		String sql = "INSERT INTO STANDARD_MANUAL_PERENCANAAN (VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
        		stmt = con.prepareStatement(sql);
        		
        		int i=1;
        		//VERSI_ID,
        		stmt.setInt(i++, Integer.parseInt(versi_id));
        		//STD_ISI_ID,
        		stmt.setInt(i++, Integer.parseInt(std_isi_id));
        		//NORUT,
        		stmt.setInt(i++, norut);
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SURVEY
        		if(job_survey==null||job_survey.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_survey.length;j++) {
        				tmp = tmp+job_survey[j];
        				if(j+1<job_survey.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		upd = stmt.executeUpdate();
        	}
        	else if(mode.equalsIgnoreCase("edit")) {
        		//update manual yg ada
        		//System.out.println("edit mode");
        		String sql = "UPDATE STANDARD_MANUAL_PERENCANAAN SET TGL_STA=?,TGL_END=?,TKN_JAB_PERUMUS=?,TKN_JAB_PERIKSA=?,TKN_JAB_SETUJU=?,TKN_JAB_PENETAP=?,TKN_JAB_KENDALI=?,TKN_JAB_PETUGAS_LAP=?,TUJUAN=?,LINGKUP=?,DEFINISI=?,PROSEDUR=?,KUALIFIKASI=?,DOKUMEN=?,REFERENSI=? WHERE VERSI_ID=? AND STD_ISI_ID=? AND NORUT=?";
        		int i=1;
        		stmt = con.prepareStatement(sql);
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SURVEY
        		if(job_survey==null||job_survey.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_survey.length;j++) {
        				tmp = tmp+job_survey[j];
        				if(j+1<job_survey.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		//VERSI_ID
        		stmt.setInt(i++, Integer.parseInt(versi_id));
        		//STD_ISI_ID
        		stmt.setInt(i++, Integer.parseInt(std_isi_id));
        		//NORUT=?";
        		stmt.setInt(i++, Integer.parseInt(no_urut_manual));
        		upd= stmt.executeUpdate();
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
    	//System.out.println("updated="+upd);
    	return upd;
    }	
    
    
    public int updateManualPerencanaan_v1(String mode, String kdpst,String id_versi,String id_std,String at_menu_dash,String id_kendali,String tipe_sarpras,String catat_civitas,String fwdto,String[]job_rumus,	String[]job_cek,String[]job_stuju,String[]job_tetap,String[]job_kendali,String[]job_survey,String tujuan,String lingkup,String prosedur,String kuali,String doc,String ref,String definisi) {
    	int upd = 0;
    	int no_versi = 0;
    	//System.out.println("id_versi=="+id_versi);
    	try {
    		
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(mode.equalsIgnoreCase("insert")) {
        		//System.out.println("insert mode");
        		//insert manual baru
        		//1. cek no_urut terakhir
        		stmt = con.prepareStatement("select VERSI_ID from STANDARD_MANUAL_PERENCANAAN_UMUM where ID_STD=? order by VERSI_ID desc limit 1");
        		stmt.setInt(1, Integer.parseInt(id_std));
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			no_versi = rs.getInt(1);// == no_versi
        		}
        		no_versi++;
        		String sql = "INSERT INTO STANDARD_MANUAL_PERENCANAAN_UMUM (VERSI_ID,ID_STD,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
        		stmt = con.prepareStatement(sql);
        		
        		int i=1;
        		//VERSI_ID,
        		stmt.setInt(i++, no_versi);
        		//ID_STD,
        		stmt.setInt(i++, Integer.parseInt(id_std));
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SURVEY
        		if(job_survey==null||job_survey.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_survey.length;j++) {
        				tmp = tmp+job_survey[j];
        				if(j+1<job_survey.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		upd = stmt.executeUpdate();
        	}
        	else if(mode.equalsIgnoreCase("edit")) {
        		//update manual yg ada
        		//System.out.println("edit mode");
        		String sql = "UPDATE STANDARD_MANUAL_PERENCANAAN_UMUM SET TGL_STA=?,TGL_END=?,TKN_JAB_PERUMUS=?,TKN_JAB_PERIKSA=?,TKN_JAB_SETUJU=?,TKN_JAB_PENETAP=?,TKN_JAB_KENDALI=?,TKN_JAB_PETUGAS_LAP=?,TUJUAN=?,LINGKUP=?,DEFINISI=?,PROSEDUR=?,KUALIFIKASI=?,DOKUMEN=?,REFERENSI=? WHERE VERSI_ID=? AND ID_STD=?";
        		int i=1;
        		stmt = con.prepareStatement(sql);
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SURVEY
        		if(job_survey==null||job_survey.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_survey.length;j++) {
        				tmp = tmp+job_survey[j];
        				if(j+1<job_survey.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		//VERSI_ID
        		stmt.setInt(i++, Integer.parseInt(id_versi));
        		//STD_ISI_ID
        		stmt.setInt(i++, Integer.parseInt(id_std));
        		upd= stmt.executeUpdate();
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
    	//System.out.println("updated="+upd);
    	return upd;
    }	
    
    public int updateManualPelaksanaan_v1(String mode, String kdpst,String id_versi,String id_std,String at_menu_dash,String id_kendali,String tipe_sarpras,String catat_civitas,String fwdto,String[]job_rumus,	String[]job_cek,String[]job_stuju,String[]job_tetap,String[]job_kendali,String[]job_survey,String tujuan,String lingkup,String prosedur,String kuali,String doc,String ref,String definisi) {
    	int upd = 0;
    	int no_versi = 0;
    	//System.out.println("id_versi=="+id_versi);
    	try {
    		
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(mode.equalsIgnoreCase("insert")) {
        		//System.out.println("insert mode");
        		//insert manual baru
        		//1. cek no_urut terakhir
        		stmt = con.prepareStatement("select VERSI_ID from STANDARD_MANUAL_PELAKSANAAN_UMUM where ID_STD=? order by VERSI_ID desc limit 1");
        		stmt.setInt(1, Integer.parseInt(id_std));
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			no_versi = rs.getInt(1);// == no_versi
        		}
        		no_versi++;
        		String sql = "INSERT INTO STANDARD_MANUAL_PELAKSANAAN_UMUM (VERSI_ID,ID_STD,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
        		stmt = con.prepareStatement(sql);
        		
        		int i=1;
        		//VERSI_ID,
        		stmt.setInt(i++, no_versi);
        		//ID_STD,
        		stmt.setInt(i++, Integer.parseInt(id_std));
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SURVEY
        		if(job_survey==null||job_survey.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_survey.length;j++) {
        				tmp = tmp+job_survey[j];
        				if(j+1<job_survey.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		upd = stmt.executeUpdate();
        	}
        	else if(mode.equalsIgnoreCase("edit")) {
        		//update manual yg ada
        		//System.out.println("edit mode");
        		String sql = "UPDATE STANDARD_MANUAL_PELAKSANAAN_UMUM SET TGL_STA=?,TGL_END=?,TKN_JAB_PERUMUS=?,TKN_JAB_PERIKSA=?,TKN_JAB_SETUJU=?,TKN_JAB_PENETAP=?,TKN_JAB_KENDALI=?,TKN_JAB_PETUGAS_LAP=?,TUJUAN=?,LINGKUP=?,DEFINISI=?,PROSEDUR=?,KUALIFIKASI=?,DOKUMEN=?,REFERENSI=? WHERE VERSI_ID=? AND ID_STD=?";
        		int i=1;
        		stmt = con.prepareStatement(sql);
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SURVEY
        		if(job_survey==null||job_survey.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_survey.length;j++) {
        				tmp = tmp+job_survey[j];
        				if(j+1<job_survey.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		//VERSI_ID
        		stmt.setInt(i++, Integer.parseInt(id_versi));
        		//STD_ISI_ID
        		stmt.setInt(i++, Integer.parseInt(id_std));
        		upd= stmt.executeUpdate();
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
    	//System.out.println("updated="+upd);
    	return upd;
    }	
    
    
    public int updateManualPeningkatan_v1(String mode, String kdpst,String id_versi,String id_std,String at_menu_dash,String id_kendali,String tipe_sarpras,String catat_civitas,String fwdto,String[]job_rumus,	String[]job_cek,String[]job_stuju,String[]job_tetap,String[]job_kendali,String[]job_survey,String tujuan,String lingkup,String prosedur,String kuali,String doc,String ref,String definisi) {
    	int upd = 0;
    	int no_versi = 0;
    	//System.out.println("id_versi=="+id_versi);
    	try {
    		
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(mode.equalsIgnoreCase("insert")) {
        		//System.out.println("insert mode");
        		//insert manual baru
        		//1. cek no_urut terakhir
        		stmt = con.prepareStatement("select VERSI_ID from STANDARD_MANUAL_PENINGKATAN_UMUM where ID_STD=? order by VERSI_ID desc limit 1");
        		stmt.setInt(1, Integer.parseInt(id_std));
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			no_versi = rs.getInt(1);// == no_versi
        		}
        		no_versi++;
        		String sql = "INSERT INTO STANDARD_MANUAL_PENINGKATAN_UMUM (VERSI_ID,ID_STD,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
        		stmt = con.prepareStatement(sql);
        		
        		int i=1;
        		//VERSI_ID,
        		stmt.setInt(i++, no_versi);
        		//ID_STD,
        		stmt.setInt(i++, Integer.parseInt(id_std));
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SURVEY
        		if(job_survey==null||job_survey.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_survey.length;j++) {
        				tmp = tmp+job_survey[j];
        				if(j+1<job_survey.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		upd = stmt.executeUpdate();
        	}
        	else if(mode.equalsIgnoreCase("edit")) {
        		//update manual yg ada
        		//System.out.println("edit mode");
        		String sql = "UPDATE STANDARD_MANUAL_PENINGKATAN_UMUM SET TGL_STA=?,TGL_END=?,TKN_JAB_PERUMUS=?,TKN_JAB_PERIKSA=?,TKN_JAB_SETUJU=?,TKN_JAB_PENETAP=?,TKN_JAB_KENDALI=?,TKN_JAB_PETUGAS_LAP=?,TUJUAN=?,LINGKUP=?,DEFINISI=?,PROSEDUR=?,KUALIFIKASI=?,DOKUMEN=?,REFERENSI=? WHERE VERSI_ID=? AND ID_STD=?";
        		int i=1;
        		stmt = con.prepareStatement(sql);
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SURVEY
        		if(job_survey==null||job_survey.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_survey.length;j++) {
        				tmp = tmp+job_survey[j];
        				if(j+1<job_survey.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		//VERSI_ID
        		stmt.setInt(i++, Integer.parseInt(id_versi));
        		//STD_ISI_ID
        		stmt.setInt(i++, Integer.parseInt(id_std));
        		upd= stmt.executeUpdate();
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
    	//System.out.println("updated="+upd);
    	return upd;
    }	
    
    
    public int updateManualEvaluasi_v1(String mode, String kdpst,String id_versi,String id_std,String at_menu_dash,String id_kendali,String tipe_sarpras,String catat_civitas,String fwdto,String[]job_rumus,	String[]job_cek,String[]job_stuju,String[]job_tetap,String[]job_kendali,String[]job_survey,String tujuan,String lingkup,String prosedur,String kuali,String doc,String ref,String definisi) {
    	int upd = 0;
    	int no_versi = 0;
    	//System.out.println("id_versi=="+id_versi);
    	try {
    		
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(mode.equalsIgnoreCase("insert")) {
        		//System.out.println("insert mode");
        		//insert manual baru
        		//1. cek no_urut terakhir
        		stmt = con.prepareStatement("select VERSI_ID from STANDARD_MANUAL_EVALUASI_UMUM where ID_STD=? order by VERSI_ID desc limit 1");
        		stmt.setInt(1, Integer.parseInt(id_std));
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			no_versi = rs.getInt(1);// == no_versi
        		}
        		no_versi++;
        		String sql = "INSERT INTO STANDARD_MANUAL_EVALUASI_UMUM (VERSI_ID,ID_STD,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
        		stmt = con.prepareStatement(sql);
        		
        		int i=1;
        		//VERSI_ID,
        		stmt.setInt(i++, no_versi);
        		//ID_STD,
        		stmt.setInt(i++, Integer.parseInt(id_std));
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SURVEY
        		if(job_survey==null||job_survey.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_survey.length;j++) {
        				tmp = tmp+job_survey[j];
        				if(j+1<job_survey.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		upd = stmt.executeUpdate();
        	}
        	else if(mode.equalsIgnoreCase("edit")) {
        		//update manual yg ada
        		//System.out.println("edit mode");
        		String sql = "UPDATE STANDARD_MANUAL_EVALUASI_UMUM SET TGL_STA=?,TGL_END=?,TKN_JAB_PERUMUS=?,TKN_JAB_PERIKSA=?,TKN_JAB_SETUJU=?,TKN_JAB_PENETAP=?,TKN_JAB_KENDALI=?,TKN_JAB_PETUGAS_LAP=?,TUJUAN=?,LINGKUP=?,DEFINISI=?,PROSEDUR=?,KUALIFIKASI=?,DOKUMEN=?,REFERENSI=? WHERE VERSI_ID=? AND ID_STD=?";
        		int i=1;
        		stmt = con.prepareStatement(sql);
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SURVEY
        		if(job_survey==null||job_survey.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_survey.length;j++) {
        				tmp = tmp+job_survey[j];
        				if(j+1<job_survey.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		//VERSI_ID
        		stmt.setInt(i++, Integer.parseInt(id_versi));
        		//STD_ISI_ID
        		stmt.setInt(i++, Integer.parseInt(id_std));
        		upd= stmt.executeUpdate();
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
    	//System.out.println("updated="+upd);
    	return upd;
    }	
    
    public int updateManualPengendalian_v1(String mode, String kdpst,String id_versi,String id_std,String at_menu_dash,String id_kendali,String tipe_sarpras,String catat_civitas,String fwdto,String[]job_rumus,	String[]job_cek,String[]job_stuju,String[]job_tetap,String[]job_kendali,String[]job_survey,String tujuan,String lingkup,String prosedur,String kuali,String doc,String ref,String definisi) {
    	int upd = 0;
    	int no_versi = 0;
    	//System.out.println("id_versi=="+id_versi);
    	try {
    		
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(mode.equalsIgnoreCase("insert")) {
        		//System.out.println("insert mode");
        		//insert manual baru
        		//1. cek no_urut terakhir
        		stmt = con.prepareStatement("select VERSI_ID from STANDARD_MANUAL_PENGENDALIAN_UMUM where ID_STD=? order by VERSI_ID desc limit 1");
        		stmt.setInt(1, Integer.parseInt(id_std));
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			no_versi = rs.getInt(1);// == no_versi
        		}
        		no_versi++;
        		String sql = "INSERT INTO STANDARD_MANUAL_PENGENDALIAN_UMUM (VERSI_ID,ID_STD,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
        		stmt = con.prepareStatement(sql);
        		
        		int i=1;
        		//VERSI_ID,
        		stmt.setInt(i++, no_versi);
        		//ID_STD,
        		stmt.setInt(i++, Integer.parseInt(id_std));
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SURVEY
        		if(job_survey==null||job_survey.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_survey.length;j++) {
        				tmp = tmp+job_survey[j];
        				if(j+1<job_survey.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		upd = stmt.executeUpdate();
        	}
        	else if(mode.equalsIgnoreCase("edit")) {
        		//update manual yg ada
        		//System.out.println("edit mode");
        		String sql = "UPDATE STANDARD_MANUAL_PENGENDALIAN_UMUM SET TGL_STA=?,TGL_END=?,TKN_JAB_PERUMUS=?,TKN_JAB_PERIKSA=?,TKN_JAB_SETUJU=?,TKN_JAB_PENETAP=?,TKN_JAB_KENDALI=?,TKN_JAB_PETUGAS_LAP=?,TUJUAN=?,LINGKUP=?,DEFINISI=?,PROSEDUR=?,KUALIFIKASI=?,DOKUMEN=?,REFERENSI=? WHERE VERSI_ID=? AND ID_STD=?";
        		int i=1;
        		stmt = con.prepareStatement(sql);
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SURVEY
        		if(job_survey==null||job_survey.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_survey.length;j++) {
        				tmp = tmp+job_survey[j];
        				if(j+1<job_survey.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		//VERSI_ID
        		stmt.setInt(i++, Integer.parseInt(id_versi));
        		//STD_ISI_ID
        		stmt.setInt(i++, Integer.parseInt(id_std));
        		upd= stmt.executeUpdate();
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
    	//System.out.println("updated="+upd);
    	return upd;
    }	
    
    
    public int updateManualPelaksanaan(String mode, String no_urut_manual, String versi_id, String std_isi_id, String kdpst,String id_versi,String id_std_isi,String id_std,String at_menu_dash,String id_kendali,String tipe_sarpras,String catat_civitas,String fwdto,String[]job_rumus,	String[]job_cek,String[]job_stuju,String[]job_tetap,String[]job_kendali,String[]job_survey,String tujuan,String lingkup,String prosedur,String kuali,String doc,String ref,String definisi) {
    	int upd = 0;
    	int norut = 0;
    	try {
    		
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(mode.equalsIgnoreCase("insert")) {
        		//System.out.println("insert mode");
        		//insert manual baru
        		//1. cek no_urut terakhir
        		stmt = con.prepareStatement("select NORUT from STANDARD_MANUAL_PELAKSANAAN where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc limit 1");
        		stmt.setInt(1, Integer.parseInt(versi_id));
        		stmt.setInt(2, Integer.parseInt(std_isi_id));
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			norut = rs.getInt(1);
        		}
        		norut++;
        		String sql = "INSERT INTO STANDARD_MANUAL_PELAKSANAAN (VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
        		stmt = con.prepareStatement(sql);
        		
        		int i=1;
        		//VERSI_ID,
        		stmt.setInt(i++, Integer.parseInt(versi_id));
        		//STD_ISI_ID,
        		stmt.setInt(i++, Integer.parseInt(std_isi_id));
        		//NORUT,
        		stmt.setInt(i++, norut);
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SURVEY
        		if(job_survey==null||job_survey.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_survey.length;j++) {
        				tmp = tmp+job_survey[j];
        				if(j+1<job_survey.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		upd = stmt.executeUpdate();
        	}
        	else if(mode.equalsIgnoreCase("edit")) {
        		//update manual yg ada
        		//System.out.println("edit mode");
        		String sql = "UPDATE STANDARD_MANUAL_PELAKSANAAN SET TGL_STA=?,TGL_END=?,TKN_JAB_PERUMUS=?,TKN_JAB_PERIKSA=?,TKN_JAB_SETUJU=?,TKN_JAB_PENETAP=?,TKN_JAB_KENDALI=?,TKN_JAB_PETUGAS_LAP=?,TUJUAN=?,LINGKUP=?,DEFINISI=?,PROSEDUR=?,KUALIFIKASI=?,DOKUMEN=?,REFERENSI=? WHERE VERSI_ID=? AND STD_ISI_ID=? AND NORUT=?";
        		int i=1;
        		stmt = con.prepareStatement(sql);
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SURVEY
        		if(job_survey==null||job_survey.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_survey.length;j++) {
        				tmp = tmp+job_survey[j];
        				if(j+1<job_survey.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		//VERSI_ID
        		stmt.setInt(i++, Integer.parseInt(versi_id));
        		//STD_ISI_ID
        		stmt.setInt(i++, Integer.parseInt(std_isi_id));
        		//NORUT=?";
        		stmt.setInt(i++, Integer.parseInt(no_urut_manual));
        		upd= stmt.executeUpdate();
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
    	//System.out.println("updated="+upd);
    	return upd;
    }
    
    public int updateManualPeningkatan(String mode, String no_urut_manual, String versi_id, String std_isi_id, String kdpst,String id_versi,String id_std_isi,String id_std,String at_menu_dash,String id_kendali,String tipe_sarpras,String catat_civitas,String fwdto,String[]job_rumus,	String[]job_cek,String[]job_stuju,String[]job_tetap,String[]job_kendali,String[]job_survey,String tujuan,String lingkup,String prosedur,String kuali,String doc,String ref,String definisi) {
    	int upd = 0;
    	int norut = 0;
    	try {
    		
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(mode.equalsIgnoreCase("insert")) {
        		//System.out.println("insert mode");
        		//insert manual baru
        		//1. cek no_urut terakhir
        		stmt = con.prepareStatement("select NORUT from STANDARD_MANUAL_PENINGKATAN where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc limit 1");
        		stmt.setInt(1, Integer.parseInt(versi_id));
        		stmt.setInt(2, Integer.parseInt(std_isi_id));
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			norut = rs.getInt(1);
        		}
        		norut++;
        		String sql = "INSERT INTO STANDARD_MANUAL_PENINGKATAN (VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
        		stmt = con.prepareStatement(sql);
        		
        		int i=1;
        		//VERSI_ID,
        		stmt.setInt(i++, Integer.parseInt(versi_id));
        		//STD_ISI_ID,
        		stmt.setInt(i++, Integer.parseInt(std_isi_id));
        		//NORUT,
        		stmt.setInt(i++, norut);
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SURVEY
        		if(job_survey==null||job_survey.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_survey.length;j++) {
        				tmp = tmp+job_survey[j];
        				if(j+1<job_survey.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		upd = stmt.executeUpdate();
        	}
        	else if(mode.equalsIgnoreCase("edit")) {
        		//update manual yg ada
        		//System.out.println("edit mode");
        		String sql = "UPDATE STANDARD_MANUAL_PENINGKATAN SET TGL_STA=?,TGL_END=?,TKN_JAB_PERUMUS=?,TKN_JAB_PERIKSA=?,TKN_JAB_SETUJU=?,TKN_JAB_PENETAP=?,TKN_JAB_KENDALI=?,TKN_JAB_PETUGAS_LAP=?,TUJUAN=?,LINGKUP=?,DEFINISI=?,PROSEDUR=?,KUALIFIKASI=?,DOKUMEN=?,REFERENSI=? WHERE VERSI_ID=? AND STD_ISI_ID=? AND NORUT=?";
        		int i=1;
        		stmt = con.prepareStatement(sql);
        		//TGL_STA
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TGL_END
        		stmt.setNull(i++,java.sql.Types.DATE);
        		//TKN_JAB_PERUMUS
        		if(job_rumus==null||job_rumus.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_rumus.length;j++) {
        				tmp = tmp+job_rumus[j];
        				if(j+1<job_rumus.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PERIKSA
        		if(job_cek==null||job_cek.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_cek.length;j++) {
        				tmp = tmp+job_cek[j];
        				if(j+1<job_cek.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SETUJU
        		if(job_stuju==null||job_stuju.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_stuju.length;j++) {
        				tmp = tmp+job_stuju[j];
        				if(j+1<job_stuju.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_PENETAP
        		if(job_tetap==null||job_tetap.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_tetap.length;j++) {
        				tmp = tmp+job_tetap[j];
        				if(j+1<job_tetap.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_KENDALI
        		if(job_kendali==null||job_kendali.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_kendali.length;j++) {
        				tmp = tmp+job_kendali[j];
        				if(j+1<job_kendali.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TKN_JAB_SURVEY
        		if(job_survey==null||job_survey.length<1) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			String tmp = "";
        			for(int j=0;j<job_survey.length;j++) {
        				tmp = tmp+job_survey[j];
        				if(j+1<job_survey.length) {
        					tmp = tmp+"`";
        				}
        			}
        			stmt.setString(i++, tmp);
        		}
        		//TUJUAN
        		if(Checker.isStringNullOrEmpty(tujuan)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(tujuan.contains("\n")) {
        				tujuan = tujuan.replace("\n", "<br>");
        			}
        			stmt.setString(i++, tujuan);
        		}
        		
        		//LINGKUP
        		if(Checker.isStringNullOrEmpty(lingkup)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(lingkup.contains("\n")) {
        				lingkup = lingkup.replace("\n", "<br>");
        			}
        			stmt.setString(i++, lingkup);
        		}
        		//DEFINISI
        		if(Checker.isStringNullOrEmpty(definisi)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(definisi.contains("\n")) {
        				definisi = definisi.replace("\n", "<br>");
        			}
        			stmt.setString(i++, definisi);
        		}
        		//PROSEDUR
        		if(Checker.isStringNullOrEmpty(prosedur)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(prosedur.contains("\n")) {
        				prosedur = prosedur.replace("\n", "<br>");
        			}
        			stmt.setString(i++, prosedur);
        		}
        		//KUALIFIKASI
        		if(Checker.isStringNullOrEmpty(kuali)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(kuali.contains("\n")) {
        				kuali = kuali.replace("\n", "<br>");
        			}
        			stmt.setString(i++, kuali);
        		}
        		//DOKUMEN
        		if(Checker.isStringNullOrEmpty(doc)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(doc.contains("\n")) {
        				doc = doc.replace("\n", "<br>");
        			}
        			stmt.setString(i++, doc);
        		}
        		//REFERENSI
        		if(Checker.isStringNullOrEmpty(ref)) {
        			stmt.setNull(i++,java.sql.Types.VARCHAR);
        		}
        		else {
        			while(ref.contains("\n")) {
        				ref = ref.replace("\n", "<br>");
        			}
        			stmt.setString(i++, ref);
        		}
        		//VERSI_ID
        		stmt.setInt(i++, Integer.parseInt(versi_id));
        		//STD_ISI_ID
        		stmt.setInt(i++, Integer.parseInt(std_isi_id));
        		//NORUT=?";
        		stmt.setInt(i++, Integer.parseInt(no_urut_manual));
        		upd= stmt.executeUpdate();
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
    	//System.out.println("updated="+upd);
    	return upd;
    }
    
    public int updateManualStandard(String id_kendali,int id_versi,int id_std_isi,int norut,String capaian,String controled_proses,String manual_pengawasan,java.sql.Date tgl_sta,java.sql.Date tgl_end,int repitisi_pengawasan,String unit_pengawasan,String nm_job_pengawas,String nm_job_input_data,String tkn_id_uu,String tkn_id_permen, String tipe_sarpras, boolean catat_civitas) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//cek apa norut sudah ada, kalo ada update kalo blum insert
        	//stmt = con.prepareStatement("select ID_KENDALI from STANDARD_MANUAL_PENGENDALIAN where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        	//stmt.setInt(1, id_versi);
        	//stmt.setInt(2, id_std_isi);
        	//stmt.setInt(3, norut);
        	//rs = stmt.executeQuery();
        	int i=1;
        	//if(rs.next()) {
        	if(!Checker.isStringNullOrEmpty(id_kendali)) {
        		//System.out.println("id_kendal="+id_kendali);
        		//update
        		
        		//int id_kendali = rs.getInt(1);
        		String tmp = "TARGET_CAPAIAN_KONDISI=?,PROSES_DAN_OBJEK_YG_DIAWASI=?,KEGIATAN_PENGAWASAN=?,TGL_STA=?,TGL_END=?,BESARAN_PERIODE_ANTARA_PENGAWASAN=?,PERIODE_UNIT_PENGAWASAN=?,NAMA_JABATAN_PENGAWAS=?,TKN_ID_UU_TERKAIT=?,TKN_ID_PERMEN_TERKAIT=?,NAMA_JABATAN_INPUT_AWAL=?,TIPE_DATA_SARPRAS=?,CATAT_DATA_CIVITAS=?";
        		stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN set "+tmp+" where ID_KENDALI=?");
        		//capaian
        		if(Checker.isStringNullOrEmpty(capaian)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, capaian.trim());
        		}
        		//String controled_proses,
        		if(Checker.isStringNullOrEmpty(controled_proses)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, controled_proses.trim());
        		}
        		//String manual_pengawasan,
        		if(Checker.isStringNullOrEmpty(manual_pengawasan)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, manual_pengawasan.trim());
        		}
        		//java.sql.Date tgl_sta,
        		if(tgl_sta==null) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, tgl_sta);
        		}
        		//java.sql.Date tgl_end,
        		if(tgl_end==null) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, tgl_end);
        		}
        		//int repitisi_pengawasan,
        		stmt.setInt(i++, repitisi_pengawasan);
        		
        		//String unit_pengawasan,
        		if(Checker.isStringNullOrEmpty(unit_pengawasan)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, unit_pengawasan);
        		}
        		//String nm_job_pengawas,
        		if(Checker.isStringNullOrEmpty(nm_job_pengawas)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, nm_job_pengawas);
        		}
        		
        		//String tkn_id_uu,
        		if(Checker.isStringNullOrEmpty(tkn_id_uu)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, tkn_id_uu);
        		}
        		//String tkn_id_permen) {
        		if(Checker.isStringNullOrEmpty(tkn_id_permen)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, tkn_id_permen);
        		}
        		//String nm_job_input_data,
        		if(Checker.isStringNullOrEmpty(nm_job_input_data)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, nm_job_input_data);
        		}
        		//String sarpras,
        		if(Checker.isStringNullOrEmpty(tipe_sarpras)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, tipe_sarpras);
        		}
        		//catat_civitas,
        		stmt.setBoolean(i++, catat_civitas);
        		
        		
        		
        		stmt.setInt(i++,Integer.parseInt(id_kendali));
        		updated = stmt.executeUpdate();
        	}
        	else {
        		//insert
        		String tmp = "VERSI_ID,STD_ISI_ID,NORUT,TARGET_CAPAIAN_KONDISI,PROSES_DAN_OBJEK_YG_DIAWASI,KEGIATAN_PENGAWASAN,TGL_STA,TGL_END,BESARAN_PERIODE_ANTARA_PENGAWASAN,PERIODE_UNIT_PENGAWASAN,NAMA_JABATAN_PENGAWAS,TKN_ID_UU_TERKAIT,TKN_ID_PERMEN_TERKAIT,NAMA_JABATAN_INPUT_AWAL,TIPE_DATA_SARPRAS,CATAT_DATA_CIVITAS";
        		String tmp1 = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
        		stmt = con.prepareStatement("insert into STANDARD_MANUAL_PENGENDALIAN ("+tmp+") values("+tmp1+")");
        		//versi id 
        		stmt.setInt(i++, id_versi);
        		stmt.setInt(i++, id_std_isi);
        		stmt.setInt(i++, norut);
        		//capaian
        		if(Checker.isStringNullOrEmpty(capaian)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, capaian.trim());
        		}
        		//String controled_proses,
        		if(Checker.isStringNullOrEmpty(controled_proses)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, controled_proses.trim());
        		}
        		//String manual_pengawasan,
        		if(Checker.isStringNullOrEmpty(manual_pengawasan)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, manual_pengawasan.trim());
        		}
        		//java.sql.Date tgl_sta,
        		if(tgl_sta==null) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, tgl_sta);
        		}
        		//java.sql.Date tgl_end,
        		if(tgl_end==null) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, tgl_end);
        		}
        		//int repitisi_pengawasan,
        		stmt.setInt(i++, repitisi_pengawasan);
        		
        		//String unit_pengawasan,
        		if(Checker.isStringNullOrEmpty(unit_pengawasan)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, unit_pengawasan);
        		}
        		//String nm_job_pengawas,
        		if(Checker.isStringNullOrEmpty(nm_job_pengawas)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, nm_job_pengawas);
        		}
        		
        		//String tkn_id_uu,
        		if(Checker.isStringNullOrEmpty(tkn_id_uu)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, tkn_id_uu);
        		}
        		//String tkn_id_permen) {
        		if(Checker.isStringNullOrEmpty(tkn_id_permen)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, tkn_id_permen);
        		}
        		//String nm_job_input_data,
        		if(Checker.isStringNullOrEmpty(nm_job_input_data)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, nm_job_input_data);
        		}
        		//String tipe_sarpras,
        		if(Checker.isStringNullOrEmpty(tipe_sarpras)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, tipe_sarpras);
        		}
        		//catat_civitas
        		stmt.setBoolean(i++, catat_civitas);
        		
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

    public int hapusManualStandard(int id_kendali) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("delete  from STANDARD_MANUAL_PENGENDALIAN where ID_KENDALI=?");
        	stmt.setInt(1, id_kendali);
        	updated = stmt.executeUpdate();
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
    
    public int copyManual(String ppepp, String source_id_versi, String source_id_std, String token_target_id_std) {
    	int upd = 0;
    	int norut = 0;
    	try {
    		if(!Checker.isStringNullOrEmpty(ppepp) && !Checker.isStringNullOrEmpty(token_target_id_std)) {
    			Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();	
            	String target_table = "";
            	String riwayat_table = "";
            	if(ppepp.equalsIgnoreCase("penetapan")||ppepp.equalsIgnoreCase("perencanaan")) {
            		target_table = "STANDARD_MANUAL_PERENCANAAN_UMUM";
            		riwayat_table = "RIWAYAT_PERENCANAAN_UMUM";
            	}
            	else if(ppepp.equalsIgnoreCase("pelaksanaan")) {
            		target_table = "STANDARD_MANUAL_PELAKSANAAN_UMUM";
            		riwayat_table = "RIWAYAT_PELAKSANAAN_UMUM";
            	}
            	else if(ppepp.equalsIgnoreCase("evaluasi")) {
            		target_table = "STANDARD_MANUAL_EVALUASI_UMUM";
            		riwayat_table = "RIWAYAT_EVALUASI_UMUM";
            	}
            	else if(ppepp.equalsIgnoreCase("pengendalian")) {
            		target_table = "STANDARD_MANUAL_PENGENDALIAN_UMUM";
            		riwayat_table = "RIWAYAT_PENGENDALIAN_UMUM";
            	}
            	else if(ppepp.equalsIgnoreCase("peningkatan")) {
            		target_table = "STANDARD_MANUAL_PENINGKATAN_UMUM";
            		riwayat_table = "RIWAYAT_PENINGKATAN_UMUM";
            	}
            	int updated=0;
            	//delete prev_value target id_std
            	//stmt = con.prepareStatement("delete from "+riwayat_table+" where ID_STD<>? and VERSI_ID=?");
            	//System.out.println("delete from "+riwayat_table+" where ID_STD<>? and VERSI_ID=?");
            	//stmt.setInt(1, Integer.parseInt(source_id_std));
            	//stmt.setInt(2, Integer.parseInt(source_id_versi));
            	//System.out.println("id_std = "+id_std);
            	//System.out.println("id_versi = "+id_versi);
            	//int updated = stmt.executeUpdate();
            	//System.out.println("updated = "+updated);
            	stmt = con.prepareStatement("delete from "+riwayat_table+" where ID_STD<>? and VERSI_ID=?");
            	stmt.setInt(1, Integer.parseInt(source_id_std));
            	stmt.setInt(2, Integer.parseInt(source_id_versi));
            	updated = stmt.executeUpdate();
            	
            	stmt = con.prepareStatement("delete from "+target_table+" where ID_STD<>? and VERSI_ID=?");
            	stmt.setInt(1, Integer.parseInt(source_id_std));
            	stmt.setInt(2, Integer.parseInt(source_id_versi));
            	updated = stmt.executeUpdate();
            	//System.out.println("updated = "+updated);
            	
            	String sql="insert into "+target_table+" (VERSI_ID, ID_STD, TGL_STA, TGL_END, TKN_JAB_PERUMUS, TKN_JAB_PERIKSA, TKN_JAB_SETUJU, TKN_JAB_PENETAP, TKN_JAB_KENDALI, TKN_JAB_PETUGAS_LAP, TUJUAN, LINGKUP, DEFINISI, PROSEDUR, KUALIFIKASI, DOKUMEN, REFERENSI, TGL_RUMUS, TGL_CEK, TGL_STUJU, TGL_TETAP, TGL_KENDALI, TGL_RUMUS_STD, TGL_CEK_STD, TGL_STUJU_STD, TGL_TETAP_STD, TGL_KENDALI_STD)"+
            	            "select VERSI_ID, ?, TGL_STA, TGL_END, TKN_JAB_PERUMUS, TKN_JAB_PERIKSA, TKN_JAB_SETUJU, TKN_JAB_PENETAP, TKN_JAB_KENDALI, TKN_JAB_PETUGAS_LAP, TUJUAN, LINGKUP, DEFINISI, PROSEDUR, KUALIFIKASI, DOKUMEN, REFERENSI, TGL_RUMUS, TGL_CEK, TGL_STUJU, TGL_TETAP, TGL_KENDALI, TGL_RUMUS_STD, TGL_CEK_STD, TGL_STUJU_STD, TGL_TETAP_STD, TGL_KENDALI_STD from "+target_table+" where ID_STD=? and VERSI_ID=?";
            	
            	StringTokenizer st = null;
            	String pemisah = Checker.getSeperatorYgDigunakan(token_target_id_std);
            	if(Checker.isStringNullOrEmpty(pemisah)) {
            		st = new StringTokenizer(token_target_id_std);	
            	}
            	else {
            		st = new StringTokenizer(token_target_id_std,pemisah);	
            	}
            	//System.out.println("sql="+sql);
            	stmt = con.prepareStatement(sql);
            	while(st.hasMoreTokens()) {
            		String target_id_std = st.nextToken();
            		if(!source_id_std.equalsIgnoreCase(target_id_std)) {
            			stmt.setInt(1, Integer.parseInt(target_id_std));
                		stmt.setInt(2, Integer.parseInt(source_id_std));
                		stmt.setInt(3, Integer.parseInt(source_id_versi));
                		upd = upd+stmt.executeUpdate();	
                		//System.out.println("upd="+sql);
            		}
            	}
            	//insert std_tipe_version table (set tgl rumus dsb)
            	stmt = con.prepareStatement("select * from STANDARD_TIPE_VERSION where ID_STD=? and ID_VERSI=?");
            	stmt.setInt(1, Integer.parseInt(source_id_std));
        		stmt.setInt(2, Integer.parseInt(source_id_versi));
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		String TKN_JAB_PERUMUS = rs.getString(3);
            		String TKN_JAB_PERIKSA = rs.getString(4);
            		String TKN_JAB_SETUJU = rs.getString(5);
            		String TKN_JAB_PENETAP = rs.getString(6);
            		String TKN_JAB_KENDALI = rs.getString(7);
            		String TKN_JAB_PETUGAS_LAP = rs.getString(8);
            		String tgl_sta = rs.getString(14);
            		String tgl_end = rs.getString(15);
            		String TGL_KEGIATAN_PERUMUSAN_STD_END = rs.getString(16);
            		String TGL_KEGIATAN_PEMERIKSAAN_STD_END = rs.getString(17);
            		String TGL_KEGIATAN_PERSETUJUAN_STD_END = rs.getString(18);
            		String TGL_KEGIATAN_PENETAPAN_STD_END = rs.getString(19);
            		
            		sql = "update STANDARD_TIPE_VERSION set TKN_JAB_PERUMUS=?,TKN_JAB_PERIKSA=?,TKN_JAB_SETUJU=?,TKN_JAB_PENETAP=?,TKN_JAB_KENDALI=?,TKN_JAB_PETUGAS_LAP=?,TGL_STA=?,TGL_END=?,TGL_KEGIATAN_PERUMUSAN_STD_END=?,TGL_KEGIATAN_PEMERIKSAAN_STD_END=?,TGL_KEGIATAN_PERSETUJUAN_STD_END=?,TGL_KEGIATAN_PENETAPAN_STD_END=? where ID_VERSI=? and ID_STD<>?";
                	st = null;
                	stmt = con.prepareStatement(sql);
                	int i = 1;
                	if(Checker.isStringNullOrEmpty(TKN_JAB_PERUMUS)) {
                		stmt.setNull(i++, java.sql.Types.VARCHAR);
                	}
                	else {
                		stmt.setString(i++, TKN_JAB_PERUMUS);
                	}
                	if(Checker.isStringNullOrEmpty(TKN_JAB_PERIKSA)) {
                		stmt.setNull(i++, java.sql.Types.VARCHAR);
                	}
                	else {
                		stmt.setString(i++, TKN_JAB_PERIKSA);
                	}
                	if(Checker.isStringNullOrEmpty(TKN_JAB_SETUJU)) {
                		stmt.setNull(i++, java.sql.Types.VARCHAR);
                    }
                	else {
                		stmt.setString(i++, TKN_JAB_SETUJU);
                    }
                	if(Checker.isStringNullOrEmpty(TKN_JAB_PENETAP)) {
                		stmt.setNull(i++, java.sql.Types.VARCHAR);
                    }
                	else {
                		stmt.setString(i++, TKN_JAB_PENETAP);
                	}
                	if(Checker.isStringNullOrEmpty(TKN_JAB_KENDALI)) {
                		stmt.setNull(i++, java.sql.Types.VARCHAR);
                	}
                	else {
                		stmt.setString(i++, TKN_JAB_KENDALI);
                	}
                	if(Checker.isStringNullOrEmpty(TKN_JAB_PETUGAS_LAP)) {
                		stmt.setNull(i++, java.sql.Types.VARCHAR);
                	}
                	else {
                		stmt.setString(i++, TKN_JAB_PETUGAS_LAP);
                	}
                	if(Checker.isStringNullOrEmpty(tgl_sta)) {
                		stmt.setNull(i++, java.sql.Types.DATE);
                	}
                	else {
                		//stmt.setDate(i++, java.sql.Date.valueOf(tgl_sta));
                		stmt.setNull(i++, java.sql.Types.DATE);
                	}
                	if(Checker.isStringNullOrEmpty(tgl_end)) {
                		stmt.setNull(i++, java.sql.Types.DATE);
                	}
                	else {
                		stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
                	}
                	if(Checker.isStringNullOrEmpty(TGL_KEGIATAN_PERUMUSAN_STD_END)) {
                		stmt.setNull(i++, java.sql.Types.DATE);
                	}
                	else {
                		stmt.setDate(i++, java.sql.Date.valueOf(TGL_KEGIATAN_PERUMUSAN_STD_END));
                	}
                	if(Checker.isStringNullOrEmpty(TGL_KEGIATAN_PEMERIKSAAN_STD_END)) {
                		stmt.setNull(i++, java.sql.Types.DATE);
                	}
                	else {
                		stmt.setDate(i++, java.sql.Date.valueOf(TGL_KEGIATAN_PEMERIKSAAN_STD_END));
                	}
                	if(Checker.isStringNullOrEmpty(TGL_KEGIATAN_PERSETUJUAN_STD_END)) {
                		stmt.setNull(i++, java.sql.Types.DATE);
                	}
                	else {
                		stmt.setDate(i++, java.sql.Date.valueOf(TGL_KEGIATAN_PERSETUJUAN_STD_END));
                	}
                	if(Checker.isStringNullOrEmpty(TGL_KEGIATAN_PENETAPAN_STD_END)) {
                		stmt.setNull(i++, java.sql.Types.DATE);
                	}
                	else {
                		stmt.setDate(i++, java.sql.Date.valueOf(TGL_KEGIATAN_PENETAPAN_STD_END));
                	}
                	stmt.setInt(i++, Integer.parseInt(source_id_versi));
                	stmt.setInt(i++, Integer.parseInt(source_id_std));
                	updated = updated + stmt.executeUpdate();
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
    	return upd;
    }	
    
    public int copyRiwayatKegiatanManual(String ppepp, String source_id_versi, String source_id_std, String token_target_id_std) {
    	int upd = 0;
    	int norut = 0;
    	try {
    		if(!Checker.isStringNullOrEmpty(ppepp) && !Checker.isStringNullOrEmpty(token_target_id_std)) {
    			Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();	
            	String target_table = "";
            	String riwayat_table = "";
            	if(ppepp.equalsIgnoreCase("penetapan")||ppepp.equalsIgnoreCase("perencanaan")) {
            		target_table = "STANDARD_MANUAL_PERENCANAAN_UMUM";
            		riwayat_table = "RIWAYAT_PERENCANAAN_UMUM";
            	}
            	else if(ppepp.equalsIgnoreCase("pelaksanaan")) {
            		target_table = "STANDARD_MANUAL_PELAKSANAAN_UMUM";
            		riwayat_table = "RIWAYAT_PELAKSANAAN_UMUM";
            	}
            	else if(ppepp.equalsIgnoreCase("evaluasi")) {
            		target_table = "STANDARD_MANUAL_EVALUASI_UMUM";
            		riwayat_table = "RIWAYAT_EVALUASI_UMUM";
            	}
            	else if(ppepp.equalsIgnoreCase("pengendalian")) {
            		target_table = "STANDARD_MANUAL_PENGENDALIAN_UMUM";
            		riwayat_table = "RIWAYAT_PENGENDALIAN_UMUM";
            	}
            	else if(ppepp.equalsIgnoreCase("peningkatan")) {
            		target_table = "STANDARD_MANUAL_PENINGKATAN_UMUM";
            		riwayat_table = "RIWAYAT_PENINGKATAN_UMUM";
            	}
            	//delete prev_value target id_std
            	stmt = con.prepareStatement("delete from "+riwayat_table+" where ID_STD<>? and VERSI_ID=?");
            	//System.out.println("delete from "+riwayat_table+" where ID_STD<>? and VERSI_ID=?");
            	stmt.setInt(1, Integer.parseInt(source_id_std));
            	stmt.setInt(2, Integer.parseInt(source_id_versi));
            	//System.out.println("id_std = "+id_std);
            	//System.out.println("id_versi = "+id_versi);
            	int updated = stmt.executeUpdate();
            	//System.out.println("updated = "+updated);
            	//stmt = con.prepareStatement("delete from "+target_table+" where ID_STD<>? and VERSI_ID=?");
            	//System.out.println("delete from "+target_table+" where ID_STD<>? and VERSI_ID=?");
            	//stmt.setInt(1, Integer.parseInt(source_id_std));
            	//stmt.setInt(2, Integer.parseInt(source_id_versi));
            	//System.out.println("id_std = "+id_std);
            	//System.out.println("id_versi = "+id_versi);
            	//updated = stmt.executeUpdate();
            	//System.out.println("updated = "+updated);
            	
            	String sql="insert into "+riwayat_table+" (VERSI_ID, ID_STD, TGL_STA_KEGIATAN, WAKTU_STA_KEGIATAN, TGL_END_KEGIATAN, WAKTU_END_KEGIATAN, TIMESTAMP_KEGIATAN_STA, TIMESTAMP_KEGIATAN_END, NAMA_KEGIATAN, JENIS_KEGIATAN, KETERANGAN_SINGKAT_TUJUAN_KEGIATAN, ISI_KEGIATAN, PENANGGUNG_JAWAB_KEGIATAN, AUDIENCE_KEGIATAN, DOKUMEN_KEGIATAN, KETERANGAN_HASIL_KEGIATAN, KEGIATAN_STARTED, KEGIATAN_SELESAI, NOTE, TGL_RUMUS, TGL_CEK, TGL_STUJU, TGL_TETAP, TGL_KENDALI, TGL_NEXT_KEGIATAN, WAKTU_NEXT_KEGIATAN, TGL_RUMUS_STD, TGL_CEK_STD, TGL_STUJU_STD, TGL_TETAP_STD, TGL_KENDALI_STD)"+
            	            "select VERSI_ID, ?, TGL_STA_KEGIATAN, WAKTU_STA_KEGIATAN, TGL_END_KEGIATAN, WAKTU_END_KEGIATAN, TIMESTAMP_KEGIATAN_STA, TIMESTAMP_KEGIATAN_END, NAMA_KEGIATAN, JENIS_KEGIATAN, KETERANGAN_SINGKAT_TUJUAN_KEGIATAN, ISI_KEGIATAN, PENANGGUNG_JAWAB_KEGIATAN, AUDIENCE_KEGIATAN, DOKUMEN_KEGIATAN, KETERANGAN_HASIL_KEGIATAN, KEGIATAN_STARTED, KEGIATAN_SELESAI, NOTE, TGL_RUMUS, TGL_CEK, TGL_STUJU, TGL_TETAP, TGL_KENDALI, TGL_NEXT_KEGIATAN, WAKTU_NEXT_KEGIATAN, TGL_RUMUS_STD, TGL_CEK_STD, TGL_STUJU_STD, TGL_TETAP_STD, TGL_KENDALI_STD from "+riwayat_table+" where ID_STD=? and VERSI_ID=?";
            	
            	StringTokenizer st = null;
            	String pemisah = Checker.getSeperatorYgDigunakan(token_target_id_std);
            	if(Checker.isStringNullOrEmpty(pemisah)) {
            		st = new StringTokenizer(token_target_id_std);	
            	}
            	else {
            		st = new StringTokenizer(token_target_id_std,pemisah);	
            	}
            	//System.out.println("sql="+sql);
            	stmt = con.prepareStatement(sql);
            	while(st.hasMoreTokens()) {
            		String target_id_std = st.nextToken();
            		if(!source_id_std.equalsIgnoreCase(target_id_std)) {
            			stmt.setInt(1, Integer.parseInt(target_id_std));
                		stmt.setInt(2, Integer.parseInt(source_id_std));
                		stmt.setInt(3, Integer.parseInt(source_id_versi));
                		upd = upd+stmt.executeUpdate();	
                		//System.out.println("upd="+sql);
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
    	return upd;
    }	
    

}

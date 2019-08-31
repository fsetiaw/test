package beans.dbase.spmi.manual;

import beans.dbase.SearchDb;
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
 * Session Bean implementation class SearchManual
 */
@Stateless
@LocalBean
public class SearchManual extends SearchDb {
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
    public SearchManual() {
        super();
        //TODO Auto-generated constructor stub
    }
    
    /**
     * @see SearchDb#SearchDb(Connection)
     */
    public SearchManual(Connection con) {
        super(con);
        //TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchManual(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        //TODO Auto-generated constructor stub
    }
    
    public Vector searchListManualPengendalian(int id_versi, int id_std_isi) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PENGENDALIAN where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		v = new Vector();
        		li = v.listIterator();
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		int std_isi_id = rs.getInt(i++);
        		//NORUT
        		int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		String tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
            	while(rs.next()) {
            		i = 1;
            		//VERSI_ID
            		versi_id = rs.getInt(i++);
            		//STD_ISI_ID
            		std_isi_id = rs.getInt(i++);
            		//NORUT
            		norut = rs.getInt(i++);
            		//TGL_STA
            		tgl_sta = rs.getDate(i++);
            		//TGL_END
            		tgl_end = rs.getDate(i++);
            		//TKN_JAB_PERUMUS
            		tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		tkn_jab_kendal = rs.getString(i++);
            		//TKN_JAB_PETUGAS_LAP
            		tkn_jab_lap = rs.getString(i++);
            		//TUJUAN
            		tujuan = rs.getString(i++);
            		//LINGKUP
            		lingkup = rs.getString(i++);
            		//DEFINISI
            		definisi = rs.getString(i++);
            		//PROSEDUR
            		prosedur = rs.getString(i++);
            		//KUALIFIKASI
            		kualifikasi = rs.getString(i++);
            		//DOKUMEN
            		dokuman = rs.getString(i++);
            		//REFERENSI
            		referensi = rs.getString(i++);     
            		tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		li.add(tmp);
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
    	return v;
    }
    
    public Vector searchListManualPengendalianUmum(int id_std) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT VERSI_ID,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PENGENDALIAN_UMUM where ID_STD=? order by VERSI_ID desc";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		v = new Vector();
        		li = v.listIterator();
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		//int std_isi_id = rs.getInt(i++);
        		//NORUT
        		//int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		String tmp = versi_id+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
            	while(rs.next()) {
            		i = 1;
            		//VERSI_ID
            		versi_id = rs.getInt(i++);
            		//STD_ISI_ID
            		//std_isi_id = rs.getInt(i++);
            		//NORUT
            		//norut = rs.getInt(i++);
            		//TGL_STA
            		tgl_sta = rs.getDate(i++);
            		//TGL_END
            		tgl_end = rs.getDate(i++);
            		//TKN_JAB_PERUMUS
            		tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		tkn_jab_kendal = rs.getString(i++);
            		//TKN_JAB_PETUGAS_LAP
            		tkn_jab_lap = rs.getString(i++);
            		//TUJUAN
            		tujuan = rs.getString(i++);
            		//LINGKUP
            		lingkup = rs.getString(i++);
            		//DEFINISI
            		definisi = rs.getString(i++);
            		//PROSEDUR
            		prosedur = rs.getString(i++);
            		//KUALIFIKASI
            		kualifikasi = rs.getString(i++);
            		//DOKUMEN
            		dokuman = rs.getString(i++);
            		//REFERENSI
            		referensi = rs.getString(i++);     
            		tmp = versi_id+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		li.add(tmp);
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
    	return v;
    }
    /*
    public Vector searchListManualPengendalianUmum(int id_std) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT VERSI_ID,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PENGENDALIAN_UMUM where ID_STD=?  order by VERSI_ID desc";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		v = new Vector();
        		li = v.listIterator();
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		//int std_isi_id = rs.getInt(i++);
        		//NORUT
        		//int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		String tmp = versi_id+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
            	while(rs.next()) {
            		i = 1;
            		//VERSI_ID
            		versi_id = rs.getInt(i++);
            		//STD_ISI_ID
            		//std_isi_id = rs.getInt(i++);
            		//NORUT
            		//norut = rs.getInt(i++);
            		//TGL_STA
            		tgl_sta = rs.getDate(i++);
            		//TGL_END
            		tgl_end = rs.getDate(i++);
            		//TKN_JAB_PERUMUS
            		tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		tkn_jab_kendal = rs.getString(i++);
            		//TKN_JAB_PETUGAS_LAP
            		tkn_jab_lap = rs.getString(i++);
            		//TUJUAN
            		tujuan = rs.getString(i++);
            		//LINGKUP
            		lingkup = rs.getString(i++);
            		//DEFINISI
            		definisi = rs.getString(i++);
            		//PROSEDUR
            		prosedur = rs.getString(i++);
            		//KUALIFIKASI
            		kualifikasi = rs.getString(i++);
            		//DOKUMEN
            		dokuman = rs.getString(i++);
            		//REFERENSI
            		referensi = rs.getString(i++);     
            		tmp = versi_id+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		li.add(tmp);
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
    	return v;
    }
    */
    public String searchListManualPengendalianUmum(int id_versi, int id_std) {
    	String tmp = null;
    	//ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PENGENDALIAN_UMUM where VERSI_ID=? and ID_STD=?";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		//VERSI_ID
        		//int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		//int std_isi_id = rs.getInt(i++);
        		//NORUT
        		//int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		tmp = id_versi+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
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
    	return tmp;
    }
    
    

    
    public Vector searchListManualEvaluasi(int id_versi, int id_std_isi) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_EVALUASI where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		v = new Vector();
        		li = v.listIterator();
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		int std_isi_id = rs.getInt(i++);
        		//NORUT
        		int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		String tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
            	while(rs.next()) {
            		i = 1;
            		//VERSI_ID
            		versi_id = rs.getInt(i++);
            		//STD_ISI_ID
            		std_isi_id = rs.getInt(i++);
            		//NORUT
            		norut = rs.getInt(i++);
            		//TGL_STA
            		tgl_sta = rs.getDate(i++);
            		//TGL_END
            		tgl_end = rs.getDate(i++);
            		//TKN_JAB_PERUMUS
            		tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		tkn_jab_kendal = rs.getString(i++);
            		//TKN_JAB_PETUGAS_LAP
            		tkn_jab_lap = rs.getString(i++);
            		//TUJUAN
            		tujuan = rs.getString(i++);
            		//LINGKUP
            		lingkup = rs.getString(i++);
            		//DEFINISI
            		definisi = rs.getString(i++);
            		//PROSEDUR
            		prosedur = rs.getString(i++);
            		//KUALIFIKASI
            		kualifikasi = rs.getString(i++);
            		//DOKUMEN
            		dokuman = rs.getString(i++);
            		//REFERENSI
            		referensi = rs.getString(i++);     
            		tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		li.add(tmp);
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
    	return v;
    }	
    
    public Vector searchListManualEvaluasiUmum(int id_std) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT VERSI_ID,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_EVALUASI_UMUM where ID_STD=?  order by VERSI_ID desc";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		v = new Vector();
        		li = v.listIterator();
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		//int std_isi_id = rs.getInt(i++);
        		//NORUT
        		//int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		String tmp = versi_id+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
            	while(rs.next()) {
            		i = 1;
            		//VERSI_ID
            		versi_id = rs.getInt(i++);
            		//TGL_STA
            		tgl_sta = rs.getDate(i++);
            		//TGL_END
            		tgl_end = rs.getDate(i++);
            		//TKN_JAB_PERUMUS
            		tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		tkn_jab_kendal = rs.getString(i++);
            		//TKN_JAB_PETUGAS_LAP
            		tkn_jab_lap = rs.getString(i++);
            		//TUJUAN
            		tujuan = rs.getString(i++);
            		//LINGKUP
            		lingkup = rs.getString(i++);
            		//DEFINISI
            		definisi = rs.getString(i++);
            		//PROSEDUR
            		prosedur = rs.getString(i++);
            		//KUALIFIKASI
            		kualifikasi = rs.getString(i++);
            		//DOKUMEN
            		dokuman = rs.getString(i++);
            		//REFERENSI
            		referensi = rs.getString(i++);     
            		tmp = versi_id+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		li.add(tmp);
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
    	return v;
    }	
    
    public Vector searchListManualPerencanaan(int id_versi, int id_std_isi) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PERENCANAAN where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		v = new Vector();
        		li = v.listIterator();
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		int std_isi_id = rs.getInt(i++);
        		//NORUT
        		int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		String tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
            	while(rs.next()) {
            		i = 1;
            		//VERSI_ID
            		versi_id = rs.getInt(i++);
            		//STD_ISI_ID
            		std_isi_id = rs.getInt(i++);
            		//NORUT
            		norut = rs.getInt(i++);
            		//TGL_STA
            		tgl_sta = rs.getDate(i++);
            		//TGL_END
            		tgl_end = rs.getDate(i++);
            		//TKN_JAB_PERUMUS
            		tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		tkn_jab_kendal = rs.getString(i++);
            		//TKN_JAB_PETUGAS_LAP
            		tkn_jab_lap = rs.getString(i++);
            		//TUJUAN
            		tujuan = rs.getString(i++);
            		//LINGKUP
            		lingkup = rs.getString(i++);
            		//DEFINISI
            		definisi = rs.getString(i++);
            		//PROSEDUR
            		prosedur = rs.getString(i++);
            		//KUALIFIKASI
            		kualifikasi = rs.getString(i++);
            		//DOKUMEN
            		dokuman = rs.getString(i++);
            		//REFERENSI
            		referensi = rs.getString(i++);     
            		tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		li.add(tmp);
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
    	return v;
    }	
    
    public Vector searchListManualPerencanaanUmum(int id_std) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT VERSI_ID,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PERENCANAAN_UMUM where ID_STD=? order by VERSI_ID desc";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		v = new Vector();
        		li = v.listIterator();
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		String tmp = versi_id+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
            	while(rs.next()) {
            		i = 1;
            		//VERSI_ID
            		versi_id = rs.getInt(i++);
            		//TGL_STA
            		tgl_sta = rs.getDate(i++);
            		//TGL_END
            		tgl_end = rs.getDate(i++);
            		//TKN_JAB_PERUMUS
            		tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		tkn_jab_kendal = rs.getString(i++);
            		//TKN_JAB_PETUGAS_LAP
            		tkn_jab_lap = rs.getString(i++);
            		//TUJUAN
            		tujuan = rs.getString(i++);
            		//LINGKUP
            		lingkup = rs.getString(i++);
            		//DEFINISI
            		definisi = rs.getString(i++);
            		//PROSEDUR
            		prosedur = rs.getString(i++);
            		//KUALIFIKASI
            		kualifikasi = rs.getString(i++);
            		//DOKUMEN
            		dokuman = rs.getString(i++);
            		//REFERENSI
            		referensi = rs.getString(i++);     
            		tmp = versi_id+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		li.add(tmp);
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
    	return v;
    }	
    
    public Vector searchListManualPelaksanaanUmum(int id_std) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT VERSI_ID,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PELAKSANAAN_UMUM where ID_STD=? order by VERSI_ID desc";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		v = new Vector();
        		li = v.listIterator();
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		String tmp = versi_id+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
            	while(rs.next()) {
            		i = 1;
            		//VERSI_ID
            		versi_id = rs.getInt(i++);
            		//TGL_STA
            		tgl_sta = rs.getDate(i++);
            		//TGL_END
            		tgl_end = rs.getDate(i++);
            		//TKN_JAB_PERUMUS
            		tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		tkn_jab_kendal = rs.getString(i++);
            		//TKN_JAB_PETUGAS_LAP
            		tkn_jab_lap = rs.getString(i++);
            		//TUJUAN
            		tujuan = rs.getString(i++);
            		//LINGKUP
            		lingkup = rs.getString(i++);
            		//DEFINISI
            		definisi = rs.getString(i++);
            		//PROSEDUR
            		prosedur = rs.getString(i++);
            		//KUALIFIKASI
            		kualifikasi = rs.getString(i++);
            		//DOKUMEN
            		dokuman = rs.getString(i++);
            		//REFERENSI
            		referensi = rs.getString(i++);     
            		tmp = versi_id+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		li.add(tmp);
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
    	return v;
    }
    
    

    
    public Vector searchListManualPeningkatanUmum(int id_std) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT VERSI_ID,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PENINGKATAN_UMUM where ID_STD=? order by VERSI_ID desc";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		v = new Vector();
        		li = v.listIterator();
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		String tmp = versi_id+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
            	while(rs.next()) {
            		i = 1;
            		//VERSI_ID
            		versi_id = rs.getInt(i++);
            		//TGL_STA
            		tgl_sta = rs.getDate(i++);
            		//TGL_END
            		tgl_end = rs.getDate(i++);
            		//TKN_JAB_PERUMUS
            		tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		tkn_jab_kendal = rs.getString(i++);
            		//TKN_JAB_PETUGAS_LAP
            		tkn_jab_lap = rs.getString(i++);
            		//TUJUAN
            		tujuan = rs.getString(i++);
            		//LINGKUP
            		lingkup = rs.getString(i++);
            		//DEFINISI
            		definisi = rs.getString(i++);
            		//PROSEDUR
            		prosedur = rs.getString(i++);
            		//KUALIFIKASI
            		kualifikasi = rs.getString(i++);
            		//DOKUMEN
            		dokuman = rs.getString(i++);
            		//REFERENSI
            		referensi = rs.getString(i++);     
            		tmp = versi_id+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		li.add(tmp);
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
    	return v;
    }	
    
    public Vector searchListManualPelaksanaan(int id_versi, int id_std_isi) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PELAKSANAAN where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		v = new Vector();
        		li = v.listIterator();
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		int std_isi_id = rs.getInt(i++);
        		//NORUT
        		int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		String tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
            	while(rs.next()) {
            		i = 1;
            		//VERSI_ID
            		versi_id = rs.getInt(i++);
            		//STD_ISI_ID
            		std_isi_id = rs.getInt(i++);
            		//NORUT
            		norut = rs.getInt(i++);
            		//TGL_STA
            		tgl_sta = rs.getDate(i++);
            		//TGL_END
            		tgl_end = rs.getDate(i++);
            		//TKN_JAB_PERUMUS
            		tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		tkn_jab_kendal = rs.getString(i++);
            		//TKN_JAB_PETUGAS_LAP
            		tkn_jab_lap = rs.getString(i++);
            		//TUJUAN
            		tujuan = rs.getString(i++);
            		//LINGKUP
            		lingkup = rs.getString(i++);
            		//DEFINISI
            		definisi = rs.getString(i++);
            		//PROSEDUR
            		prosedur = rs.getString(i++);
            		//KUALIFIKASI
            		kualifikasi = rs.getString(i++);
            		//DOKUMEN
            		dokuman = rs.getString(i++);
            		//REFERENSI
            		referensi = rs.getString(i++);     
            		tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		li.add(tmp);
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
    	return v;
    }	
    
    public Vector searchListManualPeningkatan(int id_versi, int id_std_isi) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PENINGKATAN where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		v = new Vector();
        		li = v.listIterator();
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		int std_isi_id = rs.getInt(i++);
        		//NORUT
        		int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		String tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
            	while(rs.next()) {
            		i = 1;
            		//VERSI_ID
            		versi_id = rs.getInt(i++);
            		//STD_ISI_ID
            		std_isi_id = rs.getInt(i++);
            		//NORUT
            		norut = rs.getInt(i++);
            		//TGL_STA
            		tgl_sta = rs.getDate(i++);
            		//TGL_END
            		tgl_end = rs.getDate(i++);
            		//TKN_JAB_PERUMUS
            		tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		tkn_jab_kendal = rs.getString(i++);
            		//TKN_JAB_PETUGAS_LAP
            		tkn_jab_lap = rs.getString(i++);
            		//TUJUAN
            		tujuan = rs.getString(i++);
            		//LINGKUP
            		lingkup = rs.getString(i++);
            		//DEFINISI
            		definisi = rs.getString(i++);
            		//PROSEDUR
            		prosedur = rs.getString(i++);
            		//KUALIFIKASI
            		kualifikasi = rs.getString(i++);
            		//DOKUMEN
            		dokuman = rs.getString(i++);
            		//REFERENSI
            		referensi = rs.getString(i++);     
            		tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		li.add(tmp);
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
    	return v;
    }	
    
    public Vector searchListManualPelaksanaan_expired(int id_versi, int id_std_isi) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PELAKSANAAN where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		v = new Vector();
        		li = v.listIterator();
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		int std_isi_id = rs.getInt(i++);
        		//NORUT
        		int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		String tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
            	while(rs.next()) {
            		i = 1;
            		//VERSI_ID
            		versi_id = rs.getInt(i++);
            		//STD_ISI_ID
            		std_isi_id = rs.getInt(i++);
            		//NORUT
            		norut = rs.getInt(i++);
            		//TGL_STA
            		tgl_sta = rs.getDate(i++);
            		//TGL_END
            		tgl_end = rs.getDate(i++);
            		//TKN_JAB_PERUMUS
            		tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		tkn_jab_kendal = rs.getString(i++);
            		//TKN_JAB_PETUGAS_LAP
            		tkn_jab_lap = rs.getString(i++);
            		//TUJUAN
            		tujuan = rs.getString(i++);
            		//LINGKUP
            		lingkup = rs.getString(i++);
            		//DEFINISI
            		definisi = rs.getString(i++);
            		//PROSEDUR
            		prosedur = rs.getString(i++);
            		//KUALIFIKASI
            		kualifikasi = rs.getString(i++);
            		//DOKUMEN
            		dokuman = rs.getString(i++);
            		//REFERENSI
            		referensi = rs.getString(i++);     
            		tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		li.add(tmp);
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
    	return v;
    }	
    
    public Vector getManualEvaluasiAktifAtoLatestDraft(int id_versi, int id_std_isi) {
    	/*
    	 * bila atidak ada yg aktif (tgl_sta), maka pilih versi terakhir
    	 */
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//cek apa ada yg aktif
        	String sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_EVALUASI where VERSI_ID=? and STD_ISI_ID=? and TGL_STA is not null and TGL_END is null order by TGL_STA desc";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		v = new Vector();
        		li = v.listIterator();
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		int std_isi_id = rs.getInt(i++);
        		//NORUT
        		int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		String tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);	
        	}
        	else {
        		//cek draft
        		sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_EVALUASI where VERSI_ID=? and STD_ISI_ID=? and  TGL_END is null order by NORUT desc";
            	stmt = con.prepareStatement(sql);
            	stmt.setInt(1, id_versi);
            	stmt.setInt(2, id_std_isi);
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		int i = 1;
            		v = new Vector();
            		li = v.listIterator();
            		//VERSI_ID
            		int versi_id = rs.getInt(i++);
            		//STD_ISI_ID
            		int std_isi_id = rs.getInt(i++);
            		//NORUT
            		int norut = rs.getInt(i++);
            		//TGL_STA
            		java.sql.Date tgl_sta = rs.getDate(i++);
            		//TGL_END
            		java.sql.Date tgl_end = rs.getDate(i++);
            		//TKN_JAB_PERUMUS
            		String tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		String tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		String tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		String tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		String tkn_jab_kendal = rs.getString(i++);
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
            		String dokuman = rs.getString(i++);
            		//REFERENSI
            		String referensi = rs.getString(i++);
            		String tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		li.add(tmp);	
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
    	return v;
    }	
    
    public Vector getManualPerencanaanAktifAtoLatestDraft(int id_versi, int id_std_isi) {
    	/*
    	 * bila atidak ada yg aktif (tgl_sta), maka pilih versi terakhir
    	 */
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//cek apa ada yg aktif
        	String sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PERENCANAAN where VERSI_ID=? and STD_ISI_ID=? and TGL_STA is not null and TGL_END is null order by TGL_STA desc";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		v = new Vector();
        		li = v.listIterator();
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		int std_isi_id = rs.getInt(i++);
        		//NORUT
        		int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		String tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);	
        	}
        	else {
        		//cek draft
        		sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PERENCANAAN where VERSI_ID=? and STD_ISI_ID=? and  TGL_END is null order by NORUT desc";
            	stmt = con.prepareStatement(sql);
            	stmt.setInt(1, id_versi);
            	stmt.setInt(2, id_std_isi);
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		int i = 1;
            		v = new Vector();
            		li = v.listIterator();
            		//VERSI_ID
            		int versi_id = rs.getInt(i++);
            		//STD_ISI_ID
            		int std_isi_id = rs.getInt(i++);
            		//NORUT
            		int norut = rs.getInt(i++);
            		//TGL_STA
            		java.sql.Date tgl_sta = rs.getDate(i++);
            		//TGL_END
            		java.sql.Date tgl_end = rs.getDate(i++);
            		//TKN_JAB_PERUMUS
            		String tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		String tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		String tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		String tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		String tkn_jab_kendal = rs.getString(i++);
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
            		String dokuman = rs.getString(i++);
            		//REFERENSI
            		String referensi = rs.getString(i++);
            		String tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		li.add(tmp);	
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
    	return v;
    }
    
    public Vector getManualPeningkatanAktifAtoLatestDraft(int id_versi, int id_std_isi) {
    	/*
    	 * bila atidak ada yg aktif (tgl_sta), maka pilih versi terakhir
    	 */
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//cek apa ada yg aktif
        	String sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PENINGKATAN where VERSI_ID=? and STD_ISI_ID=? and TGL_STA is not null and TGL_END is null order by TGL_STA desc";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		v = new Vector();
        		li = v.listIterator();
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		int std_isi_id = rs.getInt(i++);
        		//NORUT
        		int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		String tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);	
        	}
        	else {
        		//cek draft
        		sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PENINGKATAN where VERSI_ID=? and STD_ISI_ID=? and  TGL_END is null order by NORUT desc";
            	stmt = con.prepareStatement(sql);
            	stmt.setInt(1, id_versi);
            	stmt.setInt(2, id_std_isi);
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		int i = 1;
            		v = new Vector();
            		li = v.listIterator();
            		//VERSI_ID
            		int versi_id = rs.getInt(i++);
            		//STD_ISI_ID
            		int std_isi_id = rs.getInt(i++);
            		//NORUT
            		int norut = rs.getInt(i++);
            		//TGL_STA
            		java.sql.Date tgl_sta = rs.getDate(i++);
            		//TGL_END
            		java.sql.Date tgl_end = rs.getDate(i++);
            		//TKN_JAB_PERUMUS
            		String tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		String tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		String tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		String tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		String tkn_jab_kendal = rs.getString(i++);
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
            		String dokuman = rs.getString(i++);
            		//REFERENSI
            		String referensi = rs.getString(i++);
            		String tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		li.add(tmp);	
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
    	return v;
    }
    
    public Vector getManualPelaksanaanAktifAtoLatestDraft(int id_versi, int id_std_isi) {
    	/*
    	 * bila atidak ada yg aktif (tgl_sta), maka pilih versi terakhir
    	 */
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//cek apa ada yg aktif
        	String sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PELAKSANAAN where VERSI_ID=? and STD_ISI_ID=? and TGL_STA is not null and TGL_END is null order by TGL_STA desc";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		v = new Vector();
        		li = v.listIterator();
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		int std_isi_id = rs.getInt(i++);
        		//NORUT
        		int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		String tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);	
        	}
        	else {
        		//cek draft
        		sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PELAKSANAAN where VERSI_ID=? and STD_ISI_ID=? and  TGL_END is null order by NORUT desc";
            	stmt = con.prepareStatement(sql);
            	stmt.setInt(1, id_versi);
            	stmt.setInt(2, id_std_isi);
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		int i = 1;
            		v = new Vector();
            		li = v.listIterator();
            		//VERSI_ID
            		int versi_id = rs.getInt(i++);
            		//STD_ISI_ID
            		int std_isi_id = rs.getInt(i++);
            		//NORUT
            		int norut = rs.getInt(i++);
            		//TGL_STA
            		java.sql.Date tgl_sta = rs.getDate(i++);
            		//TGL_END
            		java.sql.Date tgl_end = rs.getDate(i++);
            		//TKN_JAB_PERUMUS
            		String tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		String tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		String tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		String tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		String tkn_jab_kendal = rs.getString(i++);
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
            		String dokuman = rs.getString(i++);
            		//REFERENSI
            		String referensi = rs.getString(i++);
            		String tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		li.add(tmp);	
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
    	return v;
    }
    
    public Vector getManualPengendalianAktifAtoLatestDraft(int id_versi, int id_std_isi) {
    	/*
    	 * bila atidak ada yg aktif (tgl_sta), maka pilih versi terakhir
    	 */
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//cek apa ada yg aktif
        	String sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PENGENDALIAN where VERSI_ID=? and STD_ISI_ID=? and TGL_STA is not null and TGL_END is null order by TGL_STA desc";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		v = new Vector();
        		li = v.listIterator();
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		int std_isi_id = rs.getInt(i++);
        		//NORUT
        		int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		String tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);	
        	}
        	else {
        		//cek draft
        		sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PENGENDALIAN where VERSI_ID=? and STD_ISI_ID=? and  TGL_END is null order by NORUT desc";
            	stmt = con.prepareStatement(sql);
            	stmt.setInt(1, id_versi);
            	stmt.setInt(2, id_std_isi);
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		int i = 1;
            		v = new Vector();
            		li = v.listIterator();
            		//VERSI_ID
            		int versi_id = rs.getInt(i++);
            		//STD_ISI_ID
            		int std_isi_id = rs.getInt(i++);
            		//NORUT
            		int norut = rs.getInt(i++);
            		//TGL_STA
            		java.sql.Date tgl_sta = rs.getDate(i++);
            		//TGL_END
            		java.sql.Date tgl_end = rs.getDate(i++);
            		//TKN_JAB_PERUMUS
            		String tkn_jab_rumus = rs.getString(i++);
            		//TKN_JAB_PERIKSA
            		String tkn_jab_cek = rs.getString(i++);
            		//TKN_JAB_SETUJU
            		String tkn_jab_stuju = rs.getString(i++);
            		//TKN_JAB_PENETAP
            		String tkn_jab_tetap = rs.getString(i++);
            		//TKN_JAB_KENDALI
            		String tkn_jab_kendal = rs.getString(i++);
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
            		String dokuman = rs.getString(i++);
            		//REFERENSI
            		String referensi = rs.getString(i++);
            		String tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		li.add(tmp);	
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
    	return v;
    }	
    
    public String searchListManualEvaluasi(int id_versi, int id_std_isi, int norut_man) {
    	String tmp = null;
    	//ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_EVALUASI where VERSI_ID=? and STD_ISI_ID=? and NORUT=?";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	stmt.setInt(3, norut_man);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		int std_isi_id = rs.getInt(i++);
        		//NORUT
        		int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
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
    	return tmp;
    }	
    
    public String searchListManualEvaluasiUmum(int id_versi, int id_std) {
    	String tmp = null;
    	//ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_EVALUASI_UMUM where VERSI_ID=? and ID_STD=?";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		//VERSI_ID
        		//int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		//int std_isi_id = rs.getInt(i++);
        		//NORUT
        		//int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		tmp = id_versi+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
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
    	return tmp;
    }	
    
    public String searchListManualPerencanaan(int id_versi, int id_std_isi, int norut_man) {
    	String tmp = null;
    	//ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PERENCANAAN where VERSI_ID=? and STD_ISI_ID=? and NORUT=?";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	stmt.setInt(3, norut_man);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		int std_isi_id = rs.getInt(i++);
        		//NORUT
        		int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
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
    	return tmp;
    }
    
    public String searchListManualPerencanaanUmum(int id_versi, int id_std) {
    	String tmp = null;
    	//ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PERENCANAAN_UMUM where VERSI_ID=? and ID_STD=?";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		//VERSI_ID
        		//int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		//int std_isi_id = rs.getInt(i++);
        		//NORUT
        		//int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		tmp = id_versi+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
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
    	return tmp;
    }
    
    public String searchListManualPelaksanaanUmum(int id_versi, int id_std) {
    	String tmp = null;
    	//ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PELAKSANAAN_UMUM where VERSI_ID=? and ID_STD=?";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		//VERSI_ID
        		//int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		//int std_isi_id = rs.getInt(i++);
        		//NORUT
        		//int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		tmp = id_versi+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
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
    	return tmp;
    }
    
    public String searchListManualPeningkatanUmum(int id_versi, int id_std) {
    	String tmp = null;
    	//ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PENINGKATAN_UMUM where VERSI_ID=? and ID_STD=?";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		//VERSI_ID
        		//int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		//int std_isi_id = rs.getInt(i++);
        		//NORUT
        		//int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		tmp = id_versi+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
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
    	return tmp;
    }
    
    public String searchListManualPelaksanaan(int id_versi, int id_std_isi, int norut_man) {
    	String tmp = null;
    	//ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PELAKSANAAN where VERSI_ID=? and STD_ISI_ID=? and NORUT=?";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	stmt.setInt(3, norut_man);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		int std_isi_id = rs.getInt(i++);
        		//NORUT
        		int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
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
    	return tmp;
    }	
    
    
    public String searchListManualPeningkatan(int id_versi, int id_std_isi, int norut_man) {
    	String tmp = null;
    	//ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI FROM STANDARD_MANUAL_PENINGKATAN where VERSI_ID=? and STD_ISI_ID=? and NORUT=?";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	stmt.setInt(3, norut_man);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i = 1;
        		//VERSI_ID
        		int versi_id = rs.getInt(i++);
        		//STD_ISI_ID
        		int std_isi_id = rs.getInt(i++);
        		//NORUT
        		int norut = rs.getInt(i++);
        		//TGL_STA
        		java.sql.Date tgl_sta = rs.getDate(i++);
        		//TGL_END
        		java.sql.Date tgl_end = rs.getDate(i++);
        		//TKN_JAB_PERUMUS
        		String tkn_jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String tkn_jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String tkn_jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String tkn_jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String tkn_jab_kendal = rs.getString(i++);
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
        		String dokuman = rs.getString(i++);
        		//REFERENSI
        		String referensi = rs.getString(i++);
        		tmp = versi_id+"~"+std_isi_id+"~"+norut+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
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
    	return tmp;
    }	
    
    public Vector searchManualStandardPengendalian_v1(int id_versi, int id_std_isi) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select * from STANDARD_MANUAL_PENGENDALIAN where VERSI_ID=? and STD_ISI_ID=? order by NORUT,ID_KENDALI");
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		int id_kendali = rs.getInt("ID_KENDALI");
        		int norut = rs.getInt("NORUT");
        		String target_kondisi  = ""+rs.getString("TARGET_CAPAIAN_KONDISI");
        		String target_proses_dan_obj	= ""+rs.getString("PROSES_DAN_OBJEK_YG_DIAWASI");
        		String manual_kegiatan = ""+rs.getString("KEGIATAN_PENGAWASAN");
        		String tgl_sta = ""+rs.getDate("TGL_STA");
        		String tgl_end = ""+rs.getDate("TGL_END");
        		int interval_pengawasan = rs.getInt("BESARAN_PERIODE_ANTARA_PENGAWASAN");
        		String unit_interval_pengawasan = ""+rs.getString("PERIODE_UNIT_PENGAWASAN");
        		String jabatan_pengawas = rs.getString("NAMA_JABATAN_PENGAWAS");
        		String tkn_id_uu = rs.getString("TKN_ID_UU_TERKAIT");
        		String tkn_id_permen = rs.getString("TKN_ID_PERMEN_TERKAIT");
        		String jabatan_inputer = rs.getString("NAMA_JABATAN_INPUT_AWAL");
        		String tipe_sarpras = rs.getString("TIPE_DATA_SARPRAS");
        		String catat_civitas = rs.getString("CATAT_DATA_CIVITAS");
        		li.add(id_kendali+"`"+id_versi+"`"+id_std_isi+"`"+norut+"`"+target_kondisi+"`"+target_proses_dan_obj+"`"+manual_kegiatan+"`"+interval_pengawasan+"`"+unit_interval_pengawasan+"`"+jabatan_pengawas+"`"+tkn_id_uu+"`"+tkn_id_permen+"`"+jabatan_inputer+"`"+tipe_sarpras+"`"+catat_civitas);
        		while(rs.next()) {
        			id_kendali = rs.getInt("ID_KENDALI");
            		norut = rs.getInt("NORUT");
            		target_kondisi  = ""+rs.getString("TARGET_CAPAIAN_KONDISI");
            		target_proses_dan_obj	= ""+rs.getString("PROSES_DAN_OBJEK_YG_DIAWASI");
            		manual_kegiatan = ""+rs.getString("KEGIATAN_PENGAWASAN");
            		tgl_sta = ""+rs.getDate("TGL_STA");
            		tgl_end = ""+rs.getDate("TGL_END");
            		interval_pengawasan = rs.getInt("BESARAN_PERIODE_ANTARA_PENGAWASAN");
            		unit_interval_pengawasan = ""+rs.getString("PERIODE_UNIT_PENGAWASAN");
            		jabatan_pengawas = rs.getString("NAMA_JABATAN_PENGAWAS");
            		tkn_id_uu = rs.getString("TKN_ID_UU_TERKAIT");
            		tkn_id_permen = rs.getString("TKN_ID_PERMEN_TERKAIT");
            		jabatan_inputer = rs.getString("NAMA_JABATAN_INPUT_AWAL");
            		tipe_sarpras = rs.getString("TIPE_DATA_SARPRAS");
            		catat_civitas = rs.getString("CATAT_DATA_CIVITAS");
            		li.add(id_kendali+"`"+id_versi+"`"+id_std_isi+"`"+norut+"`"+target_kondisi+"`"+target_proses_dan_obj+"`"+manual_kegiatan+"`"+interval_pengawasan+"`"+unit_interval_pengawasan+"`"+jabatan_pengawas+"`"+tkn_id_uu+"`"+tkn_id_permen+"`"+jabatan_inputer+"`"+tipe_sarpras+"`"+catat_civitas);  		
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
    	return v;
    }	
    
    public Vector searchManualStandardPengendalianHasilEvaluasi_v1(int id_versi, int id_std_isi) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select * from STANDARD_MANUAL_PENGENDALIAN_EVALUASI where VERSI_ID=? and STD_ISI_ID=? order by NORUT,ID_KENDALI_EVALUASI");
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		int id_kendali = rs.getInt("ID_KENDALI_EVALUASI");
        		int norut = rs.getInt("NORUT");
        		String target_kondisi  = ""+rs.getString("TARGET_CAPAIAN_KONDISI");
        		String target_proses_dan_obj	= ""+rs.getString("PROSES_DAN_OBJEK_YG_DIAWASI");
        		String manual_kegiatan = ""+rs.getString("KEGIATAN_PENGAWASAN");
        		String tgl_sta = ""+rs.getDate("TGL_STA");
        		String tgl_end = ""+rs.getDate("TGL_END");
        		int interval_pengawasan = rs.getInt("BESARAN_PERIODE_ANTARA_PENGAWASAN");
        		String unit_interval_pengawasan = ""+rs.getString("PERIODE_UNIT_PENGAWASAN");
        		String jabatan_pengawas = rs.getString("NAMA_JABATAN_PENGAWAS");
        		String tkn_id_uu = rs.getString("TKN_ID_UU_TERKAIT");
        		String tkn_id_permen = rs.getString("TKN_ID_PERMEN_TERKAIT");
        		String jabatan_inputer = rs.getString("NAMA_JABATAN_INPUT_AWAL");
        		String tipe_sarpras = rs.getString("TIPE_DATA_SARPRAS");
        		String catat_civitas = rs.getString("CATAT_DATA_CIVITAS");
        		
        		String no_doc= rs.getString("NO_KODE_MANUAL_DOKUMEN");
        		String rasionale = rs.getString("RASIONALE");
        		String tkn_jab_rumus = rs.getString("TOKEN_JABATAN_PERUMUS");
        		String tkn_nmm_rumus = rs.getString("TOKEN_NAMA_PERUMUS");
        		String tgl_rumus = rs.getString("TANGGAL_RUMUSAN");
        		String tkn_jab_cek = rs.getString("TOKEN_JABATAN_PERIKSA");
        		String tkn_nmm_cek = rs.getString("TOKEN_NAMA_PERIKSA");
        		String tgl_cek = rs.getString("TANGGAL_PERIKSA");
        		String tkn_jab_stuju = rs.getString("TOKEN_JABATAN_PERSTUJUAN");
        		String tkn_nmm_stuju = rs.getString("TOKEN_NAMA_PERSETUJUAN");
        		String tgl_stuju = rs.getString("TANGGAL_PERSETUJUAN");
        		String tkn_jab_tetap = rs.getString("TOKEN_JABATAN_PENETAPAN");
        		String tkn_nmm_tetap = rs.getString("TOKEN_NAMA_PENETAPAN");
        		String tgl_tetap = rs.getString("TANGGAL_PENETAPAN");  
        		String tkn_jab_kendal = rs.getString("TOKEN_JABATAN_PENGENDALI");
        		String tkn_nmm_kendal = rs.getString("TOKEN_NAMA_PENGENDALI");
        		String tgl_kendal = rs.getString("TANGGAL_PENGENDALI");
        		String tujuan_manual = rs.getString("TUJUAN_DOKUMEN_MANUAL");
        		String lingkup_manual = rs.getString("LINGKUP_DOKUMEN_MANUAL");
        		String definisi_manual = rs.getString("DEFINISI_DOKUMEN_MANUAL");
        		String kualifikasi_jab_manual = rs.getString("KUALIFIKASI_PEJABAT_MANUAL");
        		String dokumen_terkait = rs.getString("DOKUMEN_TERKAIT");
        		String referensi = rs.getString("REFERENSI");
        		
        		
        		String tmp =id_kendali+"`"+id_versi+"`"+id_std_isi+"`"+norut+"`"+target_kondisi+"`"+target_proses_dan_obj+"`"+manual_kegiatan+"`"+interval_pengawasan+"`"+unit_interval_pengawasan+"`"+jabatan_pengawas+"`"+tkn_id_uu+"`"+tkn_id_permen+"`"+jabatan_inputer+"`"+tipe_sarpras+"`"+catat_civitas+"`"+no_doc+"`"+rasionale+"`"+tkn_jab_rumus+"`"+tkn_nmm_rumus+"`"+tgl_rumus+"`"+tkn_jab_cek+"`"+tkn_nmm_cek+"`"+tgl_cek+"`"+tkn_jab_stuju+"`"+tkn_nmm_stuju+"`"+tgl_stuju+"`"+tkn_jab_tetap+"`"+tkn_nmm_tetap+"`"+tgl_tetap+"`"+tkn_jab_kendal+"`"+tkn_nmm_kendal+"`"+tgl_kendal+"`"+tujuan_manual+"`"+lingkup_manual+"`"+definisi_manual+"`"+kualifikasi_jab_manual+"`"+dokumen_terkait+"`"+referensi; 
        		if(tmp.startsWith("`")) {
        			tmp = "null"+tmp;
        		}
        		if(tmp.endsWith("`")) {
        			tmp = tmp+"null";
        		}
        		while(tmp.contains("``")) {
        			tmp = tmp.replace("``", "`null`");
        		}
        		li.add(tmp);
        		while(rs.next()) {
        			id_kendali = rs.getInt("ID_KENDALI");
            		norut = rs.getInt("NORUT");
            		target_kondisi  = ""+rs.getString("TARGET_CAPAIAN_KONDISI");
            		target_proses_dan_obj	= ""+rs.getString("PROSES_DAN_OBJEK_YG_DIAWASI");
            		manual_kegiatan = ""+rs.getString("KEGIATAN_PENGAWASAN");
            		tgl_sta = ""+rs.getDate("TGL_STA");
            		tgl_end = ""+rs.getDate("TGL_END");
            		interval_pengawasan = rs.getInt("BESARAN_PERIODE_ANTARA_PENGAWASAN");
            		unit_interval_pengawasan = ""+rs.getString("PERIODE_UNIT_PENGAWASAN");
            		jabatan_pengawas = rs.getString("NAMA_JABATAN_PENGAWAS");
            		tkn_id_uu = rs.getString("TKN_ID_UU_TERKAIT");
            		tkn_id_permen = rs.getString("TKN_ID_PERMEN_TERKAIT");
            		jabatan_inputer = rs.getString("NAMA_JABATAN_INPUT_AWAL");
            		tipe_sarpras = rs.getString("TIPE_DATA_SARPRAS");
            		catat_civitas = rs.getString("CATAT_DATA_CIVITAS");
            		
            		tmp = id_kendali+"`"+id_versi+"`"+id_std_isi+"`"+norut+"`"+target_kondisi+"`"+target_proses_dan_obj+"`"+manual_kegiatan+"`"+interval_pengawasan+"`"+unit_interval_pengawasan+"`"+jabatan_pengawas+"`"+tkn_id_uu+"`"+tkn_id_permen+"`"+jabatan_inputer+"`"+tipe_sarpras+"`"+catat_civitas+"`"+no_doc+"`"+rasionale+"`"+tkn_jab_rumus+"`"+tkn_nmm_rumus+"`"+tgl_rumus+"`"+tkn_jab_cek+"`"+tkn_nmm_cek+"`"+tgl_cek+"`"+tkn_jab_stuju+"`"+tkn_nmm_stuju+"`"+tgl_stuju+"`"+tkn_jab_tetap+"`"+tkn_nmm_tetap+"`"+tgl_tetap+"`"+tkn_jab_kendal+"`"+tkn_nmm_kendal+"`"+tgl_kendal+"`"+tujuan_manual+"`"+lingkup_manual+"`"+definisi_manual+"`"+kualifikasi_jab_manual+"`"+dokumen_terkait+"`"+referensi;
            		if(tmp.startsWith("`")) {
            			tmp = "null"+tmp;
            		}
            		if(tmp.endsWith("`")) {
            			tmp = tmp+"null";
            		}
            		while(tmp.contains("``")) {
            			tmp = tmp.replace("``", "`null`");
            		}
            		li.add(tmp);  		
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
    	return v;
    }	
    

    /* 
    public Vector searchManualStandardPengendalian_v1(int id_kendali) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select * from STANDARD_MANUAL_PENGENDALIAN inner join STANDARD_ISI_TABLE on  where ID_KENDALI=?");
        	stmt.setInt(1, id_kendali);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		//int id_kendali = rs.getInt("ID_KENDALI");
        		int id_versi = rs.getInt("VERSI_ID");
        		int id_std_isi = rs.getInt("STD_ISI_ID");
        		int norut = rs.getInt("NORUT");
        		String target_kondisi  = ""+rs.getString("TARGET_CAPAIAN_KONDISI");
        		String target_proses_dan_obj	= ""+rs.getString("PROSES_DAN_OBJEK_YG_DIAWASI");
        		String manual_kegiatan = ""+rs.getString("KEGIATAN_PENGAWASAN");
        		String tgl_sta = ""+rs.getDate("TGL_STA");
        		String tgl_end = ""+rs.getDate("TGL_END");
        		int interval_pengawasan = rs.getInt("BESARAN_PERIODE_ANTARA_PENGAWASAN");
        		String unit_interval_pengawasan = ""+rs.getString("PERIODE_UNIT_PENGAWASAN");
        		String jabatan_pengawas = rs.getString("NAMA_JABATAN_PENGAWAS");
        		String tkn_id_uu = rs.getString("TKN_ID_UU_TERKAIT");
        		String tkn_id_permen = rs.getString("TKN_ID_PERMEN_TERKAIT");
        		String jabatan_inputer = rs.getString("NAMA_JABATAN_INPUT_AWAL");
        		li.add(id_kendali+"`"+id_versi+"`"+id_std_isi+"`"+norut+"`"+target_kondisi+"`"+target_proses_dan_obj+"`"+manual_kegiatan+"`"+interval_pengawasan+"`"+unit_interval_pengawasan+"`"+jabatan_pengawas+"`"+tkn_id_uu+"`"+tkn_id_permen+"`"+jabatan_inputer);
        		
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
    */
    
    /*
     * deprecated, sudah tidak terpakai
     */
    public Vector prepInfoFormSureveyPengendalian(int id_kendali) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select * from STANDARD_MANUAL_PENGENDALIAN where ID_KENDALI=?");
        	stmt.setInt(1, id_kendali);
        	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		int id_versi = rs.getInt("VERSI_ID");
        		li.add(""+id_versi);
        		int id_std_isi = rs.getInt("STD_ISI_ID");
        		li.add(""+id_std_isi);
        		int norut = rs.getInt("NORUT");
        		li.add(""+norut);
        		String target_kondisi  = ""+rs.getString("TARGET_CAPAIAN_KONDISI");
        		li.add(""+target_kondisi);
        		String target_proses_dan_obj	= ""+rs.getString("PROSES_DAN_OBJEK_YG_DIAWASI");
        		li.add(""+target_proses_dan_obj);
        		String manual_kegiatan = ""+rs.getString("KEGIATAN_PENGAWASAN");
        		li.add(""+manual_kegiatan);
        		String tgl_sta = ""+rs.getDate("TGL_STA");//ignore ikut ke paretntya tabel standard_isi
        		li.add(""+tgl_sta);
        		String tgl_end = ""+rs.getDate("TGL_END");//ignore ikut ke paretntya tabel standard_isi
        		li.add(""+tgl_end);
        		int interval_pengawasan = rs.getInt("BESARAN_PERIODE_ANTARA_PENGAWASAN");
        		li.add(""+interval_pengawasan);
        		String unit_interval_pengawasan = ""+rs.getString("PERIODE_UNIT_PENGAWASAN");
        		li.add(""+unit_interval_pengawasan);
        		String jabatan_pengawas = rs.getString("NAMA_JABATAN_PENGAWAS");
        		li.add(""+jabatan_pengawas);
        		String tkn_id_uu = rs.getString("TKN_ID_UU_TERKAIT");
        		li.add(""+tkn_id_uu);
        		String tkn_id_permen = rs.getString("TKN_ID_PERMEN_TERKAIT");
        		li.add(""+tkn_id_permen);
        		String jabatan_inputer = rs.getString("NAMA_JABATAN_INPUT_AWAL");
        		li.add(""+jabatan_inputer);
        		
        		//get info std_isi
        		stmt = con.prepareStatement("select ID_STD,PERNYATAAN_STD,TGL_STA,TGL_END,TARGET_THSMS_1,TARGET_THSMS_2,TARGET_THSMS_3,TARGET_THSMS_4,TARGET_THSMS_5,TARGET_THSMS_6,PIHAK_TERKAIT,DOKUMEN_TERKAIT,TKN_INDIKATOR,TARGET_PERIOD_START,UNIT_PERIOD_USED,LAMA_NOMINAL_PER_PERIOD,TARGET_THSMS_1_UNIT,TARGET_THSMS_2_UNIT,TARGET_THSMS_3_UNIT,TARGET_THSMS_4_UNIT,TARGET_THSMS_5_UNIT,TARGET_THSMS_6_UNIT,PIHAK_MONITOR,TKN_PARAMETER,A.AKTIF from STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on ID=ID_STD_ISI where ID=? and ID_VERSI=?");
        		stmt.setInt(1, (id_std_isi));
        		stmt.setInt(2, (id_versi));
        		rs = stmt.executeQuery();
        		rs.next();
        		int i=1;
        		int id_std = rs.getInt(i++);
        		li.add(""+id_std);
        		String pernyataan_isi_std = ""+rs.getString(i++);
        		li.add(""+pernyataan_isi_std);
        		String stgl_sta = ""+rs.getDate(i++);
        		li.add(""+stgl_sta);
        		String stgl_end = ""+rs.getDate(i++);
        		li.add(""+stgl_end);
        		String thsms1 = ""+rs.getString(i++);
        		li.add(""+thsms1);
        		String thsms2 = ""+rs.getString(i++);
        		li.add(""+thsms2);
        		String thsms3 = ""+rs.getString(i++);
        		li.add(""+thsms3);
        		String thsms4 = ""+rs.getString(i++);
        		li.add(""+thsms4);
        		String thsms5 = ""+rs.getString(i++);
        		li.add(""+thsms5);
        		String thsms6 = ""+rs.getString(i++);
        		li.add(""+thsms6);
        		String pihak_terkait = ""+rs.getString(i++);
        		li.add(""+pihak_terkait);
        		String doc_terkait = ""+rs.getString(i++);
        		li.add(""+doc_terkait);
        		String indikator = ""+rs.getString(i++);
        		li.add(""+indikator);
        		String target_thsms_sta = ""+rs.getString(i++);
        		li.add(""+target_thsms_sta);
        		String satuan_periode = ""+rs.getString(i++);
        		li.add(""+satuan_periode);
        		String lama_per_period = ""+rs.getInt(i++);
        		li.add(""+lama_per_period);
        		String target_unit_thsms1 = ""+rs.getString(i++);
        		li.add(""+target_unit_thsms1);
        		String target_unit_thsms2 = ""+rs.getString(i++);
        		li.add(""+target_unit_thsms2);
        		String target_unit_thsms3 = ""+rs.getString(i++);
        		li.add(""+target_unit_thsms3);
        		String target_unit_thsms4 = ""+rs.getString(i++);
        		li.add(""+target_unit_thsms4);
        		String target_unit_thsms5 = ""+rs.getString(i++);
        		li.add(""+target_unit_thsms5);
        		String target_unit_thsms6 = ""+rs.getString(i++);
        		li.add(""+target_unit_thsms6);
        		String monitoree = ""+rs.getString(i++);
        		li.add(""+monitoree);
        		String param = ""+rs.getString(i++);
        		li.add(""+param);
        		String aktif = ""+rs.getBoolean(i++);
        		li.add(""+aktif);
        		//get info dari versi
        		
        		
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
    
    public Vector prepInfoFormPerencanaan(int versi_id, int std_isi_id, int norut_man) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select * from STANDARD_MANUAL_EVALUASI where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        	stmt.setInt(1, versi_id);
        	stmt.setInt(2, std_isi_id);
        	stmt.setInt(3, norut_man);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		//int id_versi = rs.getInt("VERSI_ID");
        		li.add(""+versi_id);
        		//int id_std_isi = rs.getInt("STD_ISI_ID");
        		li.add(""+std_isi_id);
        		//int norut = rs.getInt("NORUT");
        		li.add(""+norut_man);
        		String tgl_sta = ""+rs.getDate("TGL_STA");
        		li.add(""+tgl_sta);
        		String tgl_end = ""+rs.getDate("TGL_END");
        		li.add(""+tgl_end);
        		String tkn_jab_rumus = rs.getString("TKN_JAB_PERUMUS");
        		li.add(""+tkn_jab_rumus);
        		String tkn_jab_cek = rs.getString("TKN_JAB_PERIKSA");
        		li.add(""+tkn_jab_cek);
        		String tkn_jab_stuju = rs.getString("TKN_JAB_SETUJU");
        		li.add(""+tkn_jab_stuju);
        		String tkn_jab_tetap = rs.getString("TKN_JAB_PENETAP");
        		li.add(""+tkn_jab_tetap);
        		String tkn_jab_kendali = rs.getString("TKN_JAB_KENDALI");
        		li.add(""+tkn_jab_kendali);
        		
        		String tujuan = rs.getString("TUJUAN");
        		li.add(""+tujuan);
        		String lingkup = rs.getString("LINGKUP");
        		li.add(""+lingkup);
        		String definisi = rs.getString("DEFINISI");
        		li.add(""+definisi);
        		String prosedur = rs.getString("PROSEDUR");
        		li.add(""+prosedur);
        		String kuali = rs.getString("KUALIFIKASI");
        		li.add(""+kuali);
        		String doc = rs.getString("DOKUMEN");
        		li.add(""+doc);
        		String ref = rs.getString("REFERENSI");
        		li.add(""+ref);
        		
        		//get info std_isi
        		stmt = con.prepareStatement("select ID_STD,PERNYATAAN_STD,TGL_STA,TGL_END,TARGET_THSMS_1,TARGET_THSMS_2,TARGET_THSMS_3,TARGET_THSMS_4,TARGET_THSMS_5,TARGET_THSMS_6,PIHAK_TERKAIT,DOKUMEN_TERKAIT,TKN_INDIKATOR,TARGET_PERIOD_START,UNIT_PERIOD_USED,LAMA_NOMINAL_PER_PERIOD,TARGET_THSMS_1_UNIT,TARGET_THSMS_2_UNIT,TARGET_THSMS_3_UNIT,TARGET_THSMS_4_UNIT,TARGET_THSMS_5_UNIT,TARGET_THSMS_6_UNIT,PIHAK_MONITOR,TKN_PARAMETER,A.AKTIF from STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on ID=ID_STD_ISI where ID=? and ID_VERSI=?");
        		stmt.setInt(1, (std_isi_id));
        		stmt.setInt(2, (versi_id));
        		rs = stmt.executeQuery();
        		rs.next();
        		int i=1;
        		int id_std = rs.getInt(i++);
        		li.add(""+id_std);
        		String pernyataan_isi_std = ""+rs.getString(i++);
        		li.add(""+pernyataan_isi_std);
        		String stgl_sta = ""+rs.getDate(i++);
        		li.add(""+stgl_sta);
        		String stgl_end = ""+rs.getDate(i++);
        		li.add(""+stgl_end);
        		String thsms1 = ""+rs.getString(i++);
        		li.add(""+thsms1);
        		String thsms2 = ""+rs.getString(i++);
        		li.add(""+thsms2);
        		String thsms3 = ""+rs.getString(i++);
        		li.add(""+thsms3);
        		String thsms4 = ""+rs.getString(i++);
        		li.add(""+thsms4);
        		String thsms5 = ""+rs.getString(i++);
        		li.add(""+thsms5);
        		String thsms6 = ""+rs.getString(i++);
        		li.add(""+thsms6);
        		String pihak_terkait = ""+rs.getString(i++);
        		li.add(""+pihak_terkait);
        		String doc_terkait = ""+rs.getString(i++);
        		li.add(""+doc_terkait);
        		String indikator = ""+rs.getString(i++);
        		li.add(""+indikator);
        		String target_thsms_sta = ""+rs.getString(i++);
        		li.add(""+target_thsms_sta);
        		String satuan_periode = ""+rs.getString(i++);
        		li.add(""+satuan_periode);
        		String lama_per_period = ""+rs.getInt(i++);
        		li.add(""+lama_per_period);
        		String target_unit_thsms1 = ""+rs.getString(i++);
        		li.add(""+target_unit_thsms1);
        		String target_unit_thsms2 = ""+rs.getString(i++);
        		li.add(""+target_unit_thsms2);
        		String target_unit_thsms3 = ""+rs.getString(i++);
        		li.add(""+target_unit_thsms3);
        		String target_unit_thsms4 = ""+rs.getString(i++);
        		li.add(""+target_unit_thsms4);
        		String target_unit_thsms5 = ""+rs.getString(i++);
        		li.add(""+target_unit_thsms5);
        		String target_unit_thsms6 = ""+rs.getString(i++);
        		li.add(""+target_unit_thsms6);
        		String monitoree = ""+rs.getString(i++);
        		li.add(""+monitoree);
        		String param = ""+rs.getString(i++);
        		li.add(""+param);
        		String aktif = ""+rs.getBoolean(i++);
        		li.add(""+aktif);
        		//get info dari versi
        		
        		
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
    
    
    public Vector prepInfoFormEvaluasi(int versi_id, int std_isi_id, int norut_man) {
    	//System.out.println("versi_id="+versi_id);
    	//System.out.println("std_isi_id="+std_isi_id);
    	//System.out.println("norut_man="+norut_man);
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select * from STANDARD_MANUAL_EVALUASI where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        	stmt.setInt(1, versi_id);
        	stmt.setInt(2, std_isi_id);
        	stmt.setInt(3, norut_man);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		//int id_versi = rs.getInt("VERSI_ID");
        		li.add(""+versi_id);
        		//int id_std_isi = rs.getInt("STD_ISI_ID");
        		li.add(""+std_isi_id);
        		//int norut = rs.getInt("NORUT");
        		li.add(""+norut_man);
        		String tgl_sta = ""+rs.getDate("TGL_STA");
        		li.add(""+tgl_sta);
        		String tgl_end = ""+rs.getDate("TGL_END");
        		li.add(""+tgl_end);
        		String tkn_jab_rumus = rs.getString("TKN_JAB_PERUMUS");
        		li.add(""+tkn_jab_rumus);
        		String tkn_jab_cek = rs.getString("TKN_JAB_PERIKSA");
        		li.add(""+tkn_jab_cek);
        		String tkn_jab_stuju = rs.getString("TKN_JAB_SETUJU");
        		li.add(""+tkn_jab_stuju);
        		String tkn_jab_tetap = rs.getString("TKN_JAB_PENETAP");
        		li.add(""+tkn_jab_tetap);
        		String tkn_jab_kendali = rs.getString("TKN_JAB_KENDALI");
        		li.add(""+tkn_jab_kendali);
        		
        		String tujuan = rs.getString("TUJUAN");
        		li.add(""+tujuan);
        		String lingkup = rs.getString("LINGKUP");
        		li.add(""+lingkup);
        		String definisi = rs.getString("DEFINISI");
        		li.add(""+definisi);
        		String prosedur = rs.getString("PROSEDUR");
        		li.add(""+prosedur);
        		String kuali = rs.getString("KUALIFIKASI");
        		li.add(""+kuali);
        		String doc = rs.getString("DOKUMEN");
        		li.add(""+doc);
        		String ref = rs.getString("REFERENSI");
        		li.add(""+ref);
        		
        		//get info std_isi
        		stmt = con.prepareStatement("select ID_STD,PERNYATAAN_STD,TGL_STA,TGL_END,TARGET_THSMS_1,TARGET_THSMS_2,TARGET_THSMS_3,TARGET_THSMS_4,TARGET_THSMS_5,TARGET_THSMS_6,PIHAK_TERKAIT,DOKUMEN_TERKAIT,TKN_INDIKATOR,TARGET_PERIOD_START,UNIT_PERIOD_USED,LAMA_NOMINAL_PER_PERIOD,TARGET_THSMS_1_UNIT,TARGET_THSMS_2_UNIT,TARGET_THSMS_3_UNIT,TARGET_THSMS_4_UNIT,TARGET_THSMS_5_UNIT,TARGET_THSMS_6_UNIT,PIHAK_MONITOR,TKN_PARAMETER,A.AKTIF from STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on ID=ID_STD_ISI where ID=? and ID_VERSI=?");
        		stmt.setInt(1, (std_isi_id));
        		stmt.setInt(2, (versi_id));
        		rs = stmt.executeQuery();
        		rs.next();
        		int i=1;
        		int id_std = rs.getInt(i++);
        		li.add(""+id_std);
        		String pernyataan_isi_std = ""+rs.getString(i++);
        		li.add(""+pernyataan_isi_std);
        		String stgl_sta = ""+rs.getDate(i++);
        		li.add(""+stgl_sta);
        		String stgl_end = ""+rs.getDate(i++);
        		li.add(""+stgl_end);
        		String thsms1 = ""+rs.getString(i++);
        		li.add(""+thsms1);
        		String thsms2 = ""+rs.getString(i++);
        		li.add(""+thsms2);
        		String thsms3 = ""+rs.getString(i++);
        		li.add(""+thsms3);
        		String thsms4 = ""+rs.getString(i++);
        		li.add(""+thsms4);
        		String thsms5 = ""+rs.getString(i++);
        		li.add(""+thsms5);
        		String thsms6 = ""+rs.getString(i++);
        		li.add(""+thsms6);
        		String pihak_terkait = ""+rs.getString(i++);
        		li.add(""+pihak_terkait);
        		String doc_terkait = ""+rs.getString(i++);
        		li.add(""+doc_terkait);
        		String indikator = ""+rs.getString(i++);
        		li.add(""+indikator);
        		String target_thsms_sta = ""+rs.getString(i++);
        		li.add(""+target_thsms_sta);
        		String satuan_periode = ""+rs.getString(i++);
        		li.add(""+satuan_periode);
        		String lama_per_period = ""+rs.getInt(i++);
        		li.add(""+lama_per_period);
        		String target_unit_thsms1 = ""+rs.getString(i++);
        		li.add(""+target_unit_thsms1);
        		String target_unit_thsms2 = ""+rs.getString(i++);
        		li.add(""+target_unit_thsms2);
        		String target_unit_thsms3 = ""+rs.getString(i++);
        		li.add(""+target_unit_thsms3);
        		String target_unit_thsms4 = ""+rs.getString(i++);
        		li.add(""+target_unit_thsms4);
        		String target_unit_thsms5 = ""+rs.getString(i++);
        		li.add(""+target_unit_thsms5);
        		String target_unit_thsms6 = ""+rs.getString(i++);
        		li.add(""+target_unit_thsms6);
        		String monitoree = ""+rs.getString(i++);
        		li.add(""+monitoree);
        		String param = ""+rs.getString(i++);
        		li.add(""+param);
        		String aktif = ""+rs.getBoolean(i++);
        		li.add(""+aktif);
        		//get info dari versi
        		
        		
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
    
    
    public Vector getSurveyorDanEvaluator(int id_kendali) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select NAMA_JABATAN_INPUT_AWAL,NAMA_JABATAN_PENGAWAS from STANDARD_MANUAL_PENGENDALIAN where ID_KENDALI=?");
        	stmt.setInt(1, id_kendali);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		String surveyor = rs.getString(1);
        		String evaluator = rs.getString(2);
        		li.add(surveyor);
        		li.add(evaluator);
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
    
    public Vector getSurveyYgBlumDiEvaluasi() {
    	Vector v = null;
    	ListIterator li = null;
    	v = getSurveyYgBlumDiEvaluasi(null);
    	if(v!=null && v.size()<1) {
    		v = null;
    	}
    	/*
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//stmt = con.prepareStatement("SELECT TGL_SIDAK,KDPST_KENDALI,NAMA_JABATAN_PENGAWAS,NAMA_JABATAN_INPUT_AWAL FROM (STANDARD_RIWAYAT_PENGENDALIAN a inner join STANDARD_MANUAL_PENGENDALIAN c on a.ID_KENDALI=c.ID_KENDALI) left join STANDARD_RIWAYAT_EVALUASI b on ID_HIST=ID_HIST_KENDAL where ID_HIST_KENDAL is null;");

        	stmt = con.prepareStatement("SELECT TGL_SIDAK,KDPST_KENDALI,NAMA_JABATAN_PENGAWAS,NAMA_JABATAN_INPUT_AWAL,PROSES_DAN_OBJEK_YG_DIAWASI FROM (STANDARD_RIWAYAT_PENGENDALIAN a inner join STANDARD_MANUAL_PENGENDALIAN c on a.ID_KENDALI=c.ID_KENDALI) left join STANDARD_RIWAYAT_EVALUASI b on ID_HIST=ID_HIST_KENDAL where ID_HIST_KENDAL is null;");
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		String tgl_sidak = ""+rs.getDate(1);
        		String kdpst = ""+rs.getString(2);
        		String controller = ""+rs.getString(3);
        		String surveyor = ""+rs.getString(4);
        		String param = ""+rs.getString(5);
        		String tmp = tgl_sidak+"~"+kdpst+"~"+controller+"~"+surveyor+"~"+param;
        		li.add(tmp.replace("~~", "~null~"));
        		while(rs.next()) {
        			tgl_sidak = ""+rs.getDate(1);
            		kdpst = ""+rs.getString(2);
            		controller = ""+rs.getString(3);
            		surveyor = ""+rs.getString(4);
            		param = ""+rs.getString(5);
            		tmp = tgl_sidak+"~"+kdpst+"~"+controller+"~"+surveyor+"~"+param;
            		li.add(tmp.replace("~~", "~null~"));
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
    	*/
    	return v;
    }	
    
    public Vector getSurveyYgBlumDiEvaluasi(String target_kdpst) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//stmt = con.prepareStatement("SELECT TGL_SIDAK,KDPST_KENDALI,NAMA_JABATAN_PENGAWAS,NAMA_JABATAN_INPUT_AWAL FROM (STANDARD_RIWAYAT_PENGENDALIAN a inner join STANDARD_MANUAL_PENGENDALIAN c on a.ID_KENDALI=c.ID_KENDALI) left join STANDARD_RIWAYAT_EVALUASI b on ID_HIST=ID_HIST_KENDAL where ID_HIST_KENDAL is null;");
        	if(Checker.isStringNullOrEmpty(target_kdpst)) {
        		stmt = con.prepareStatement("SELECT TGL_SIDAK,KDPST_KENDALI,NAMA_JABATAN_PENGAWAS,NAMA_JABATAN_INPUT_AWAL,PROSES_DAN_OBJEK_YG_DIAWASI,a.ID_HIST,a.ID_KENDALI,c.VERSI_ID,c.STD_ISI_ID,e.ID_STD,e.ID_MASTER_STD,e.ID_TIPE_STD,e.KET_TIPE_STD FROM (STANDARD_RIWAYAT_PENGENDALIAN a inner join STANDARD_MANUAL_PENGENDALIAN c on a.ID_KENDALI=c.ID_KENDALI) left join STANDARD_RIWAYAT_EVALUASI b on ID_HIST=ID_HIST_KENDAL inner join STANDARD_ISI_TABLE d on STD_ISI_ID=ID inner join STANDARD_TABLE e on d.ID_STD=e.ID_STD where NEXT_TGL_SIDAK=?");
        	}
        	else {
        		stmt = con.prepareStatement("SELECT TGL_SIDAK,KDPST_KENDALI,NAMA_JABATAN_PENGAWAS,NAMA_JABATAN_INPUT_AWAL,PROSES_DAN_OBJEK_YG_DIAWASI,a.ID_HIST,a.ID_KENDALI,c.VERSI_ID,c.STD_ISI_ID,e.ID_STD,e.ID_MASTER_STD,e.ID_TIPE_STD,e.KET_TIPE_STD FROM (STANDARD_RIWAYAT_PENGENDALIAN a inner join STANDARD_MANUAL_PENGENDALIAN c on a.ID_KENDALI=c.ID_KENDALI) left join STANDARD_RIWAYAT_EVALUASI b on ID_HIST=ID_HIST_KENDAL inner join STANDARD_ISI_TABLE d on STD_ISI_ID=ID inner join STANDARD_TABLE e on d.ID_STD=e.ID_STD where ID_HIST_KENDAL is null and KDPST_KENDALI=?");
        		stmt.setString(1, target_kdpst);
        	}
        	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		//TGL_SIDAK,
        		String tgl_sidak = ""+rs.getDate(1);
        		//KDPST_KENDALI,
        		String kdpst = ""+rs.getString(2);
        		//NAMA_JABATAN_PENGAWAS,
        		String controller = ""+rs.getString(3);
        		//NAMA_JABATAN_INPUT_AWAL,
        		String surveyor = ""+rs.getString(4);
        		//PROSES_DAN_OBJEK_YG_DIAWASI,
        		String param = ""+rs.getString(5);
        		//a.ID_HIST,
        		String id_hist = ""+rs.getInt(6);
        		//a.ID_KENDALI,
        		String id_kendali = ""+rs.getInt(7);
        		//c.VERSI_ID,
        		String id_versi = ""+rs.getInt(8);
        		//c.STD_ISI_ID,
        		String id_std_isi = ""+rs.getInt(9);
        		//e.ID_STD,
        		String id_std = ""+rs.getInt(10);
        		//e.ID_MASTER_STD,
        		String id_master_std = ""+rs.getInt(11);
        		//e.ID_TIPE_STD,
        		String id_tipe_std = ""+rs.getInt(12);
        		//e.KET_TIPE_STD
        		String ket_tipe_std = ""+rs.getString(13);
        		String tmp = tgl_sidak+"~"+kdpst+"~"+controller+"~"+surveyor+"~"+param+"~"+id_hist+"~"+id_kendali+"~"+id_versi+"~"+id_std_isi+"~"+id_std+"~"+id_master_std+"~"+id_tipe_std+"~"+ket_tipe_std;
        		li.add(tmp.replace("~~", "~null~"));
        		while(rs.next()) {
        			tgl_sidak = ""+rs.getDate(1);
            		kdpst = ""+rs.getString(2);
            		controller = ""+rs.getString(3);
            		surveyor = ""+rs.getString(4);
            		param = ""+rs.getString(5);
            		id_hist = ""+rs.getInt(6);
            		id_kendali = ""+rs.getInt(7);
            		id_versi = ""+rs.getInt(8);
            		id_std_isi = ""+rs.getInt(9);
            		//e.ID_STD,
            		id_std = ""+rs.getInt(10);
            		//e.ID_MASTER_STD,
            		id_master_std = ""+rs.getInt(11);
            		//e.ID_TIPE_STD,
            		id_tipe_std = ""+rs.getInt(12);
            		//e.KET_TIPE_STD
            		ket_tipe_std = ""+rs.getString(13);
            		tmp = tgl_sidak+"~"+kdpst+"~"+controller+"~"+surveyor+"~"+param+"~"+id_hist+"~"+id_kendali+"~"+id_versi+"~"+id_std_isi+"~"+id_std+"~"+id_master_std+"~"+id_tipe_std+"~"+ket_tipe_std;
            		li.add(tmp.replace("~~", "~null~"));
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
    	return v;
    }

    public Vector getSurveyYgAkanDatang(String target_kdpst) {
    	Vector v = null;
    	ListIterator li = null;
    	java.sql.Date today_dt= AskSystem.getTodayDate();
    	//System.out.println("today_dt="+today_dt);
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//stmt = con.prepareStatement("SELECT TGL_SIDAK,KDPST_KENDALI,NAMA_JABATAN_PENGAWAS,NAMA_JABATAN_INPUT_AWAL FROM (STANDARD_RIWAYAT_PENGENDALIAN a inner join STANDARD_MANUAL_PENGENDALIAN c on a.ID_KENDALI=c.ID_KENDALI) left join STANDARD_RIWAYAT_EVALUASI b on ID_HIST=ID_HIST_KENDAL where ID_HIST_KENDAL is null;");
        	if(Checker.isStringNullOrEmpty(target_kdpst)) {
        		stmt = con.prepareStatement("SELECT TGL_SIDAK,KDPST_KENDALI,NAMA_JABATAN_PENGAWAS,NAMA_JABATAN_INPUT_AWAL,PROSES_DAN_OBJEK_YG_DIAWASI,a.ID_HIST,a.ID_KENDALI,c.VERSI_ID,c.STD_ISI_ID,e.ID_STD,e.ID_MASTER_STD,e.ID_TIPE_STD,e.KET_TIPE_STD,NEXT_TGL_SIDAK FROM (STANDARD_RIWAYAT_PENGENDALIAN a inner join STANDARD_MANUAL_PENGENDALIAN c on a.ID_KENDALI=c.ID_KENDALI) left join STANDARD_RIWAYAT_EVALUASI b on ID_HIST=ID_HIST_KENDAL inner join STANDARD_ISI_TABLE d on STD_ISI_ID=ID inner join STANDARD_TABLE e on d.ID_STD=e.ID_STD where NEXT_TGL_SIDAK>?");
        		stmt.setDate(1, today_dt);
        	}
        	else {
        		stmt = con.prepareStatement("SELECT TGL_SIDAK,KDPST_KENDALI,NAMA_JABATAN_PENGAWAS,NAMA_JABATAN_INPUT_AWAL,PROSES_DAN_OBJEK_YG_DIAWASI,a.ID_HIST,a.ID_KENDALI,c.VERSI_ID,c.STD_ISI_ID,e.ID_STD,e.ID_MASTER_STD,e.ID_TIPE_STD,e.KET_TIPE_STD,NEXT_TGL_SIDAK FROM (STANDARD_RIWAYAT_PENGENDALIAN a inner join STANDARD_MANUAL_PENGENDALIAN c on a.ID_KENDALI=c.ID_KENDALI) left join STANDARD_RIWAYAT_EVALUASI b on ID_HIST=ID_HIST_KENDAL inner join STANDARD_ISI_TABLE d on STD_ISI_ID=ID inner join STANDARD_TABLE e on d.ID_STD=e.ID_STD where NEXT_TGL_SIDAK>? and KDPST_KENDALI=?");
        		stmt.setDate(1, today_dt);
        		stmt.setString(2, target_kdpst);
        	}
        	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		//TGL_SIDAK,
        		String tgl_sidak = ""+rs.getDate(1);
        		//KDPST_KENDALI,
        		String kdpst = ""+rs.getString(2);
        		//NAMA_JABATAN_PENGAWAS,
        		String controller = ""+rs.getString(3);
        		//NAMA_JABATAN_INPUT_AWAL,
        		String surveyor = ""+rs.getString(4);
        		//PROSES_DAN_OBJEK_YG_DIAWASI,
        		String param = ""+rs.getString(5);
        		//a.ID_HIST,
        		String id_hist = ""+rs.getInt(6);
        		//a.ID_KENDALI,
        		String id_kendali = ""+rs.getInt(7);
        		//c.VERSI_ID,
        		String id_versi = ""+rs.getInt(8);
        		//c.STD_ISI_ID,
        		String id_std_isi = ""+rs.getInt(9);
        		//e.ID_STD,
        		String id_std = ""+rs.getInt(10);
        		//e.ID_MASTER_STD,
        		String id_master_std = ""+rs.getInt(11);
        		//e.ID_TIPE_STD,
        		String id_tipe_std = ""+rs.getInt(12);
        		//e.KET_TIPE_STD
        		String ket_tipe_std = ""+rs.getString(13);
        		String tgl_next_sidak = ""+rs.getDate(14);
        		String tmp = tgl_sidak+"~"+kdpst+"~"+controller+"~"+surveyor+"~"+param+"~"+id_hist+"~"+id_kendali+"~"+id_versi+"~"+id_std_isi+"~"+id_std+"~"+id_master_std+"~"+id_tipe_std+"~"+ket_tipe_std+"~"+tgl_next_sidak;
        		li.add(tmp.replace("~~", "~null~"));
        		while(rs.next()) {
        			tgl_sidak = ""+rs.getDate(1);
            		kdpst = ""+rs.getString(2);
            		controller = ""+rs.getString(3);
            		surveyor = ""+rs.getString(4);
            		param = ""+rs.getString(5);
            		id_hist = ""+rs.getInt(6);
            		id_kendali = ""+rs.getInt(7);
            		id_versi = ""+rs.getInt(8);
            		id_std_isi = ""+rs.getInt(9);
            		//e.ID_STD,
            		id_std = ""+rs.getInt(10);
            		//e.ID_MASTER_STD,
            		id_master_std = ""+rs.getInt(11);
            		//e.ID_TIPE_STD,
            		id_tipe_std = ""+rs.getInt(12);
            		//e.KET_TIPE_STD
            		ket_tipe_std = ""+rs.getString(13);
            		tgl_next_sidak = ""+rs.getDate(14);
            		tmp = tgl_sidak+"~"+kdpst+"~"+controller+"~"+surveyor+"~"+param+"~"+id_hist+"~"+id_kendali+"~"+id_versi+"~"+id_std_isi+"~"+id_std+"~"+id_master_std+"~"+id_tipe_std+"~"+ket_tipe_std+"~"+tgl_next_sidak;
            		li.add(tmp.replace("~~", "~null~"));
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
    	return v;
    }
    
    public Vector getSurveyYgBelumDilaksanakan(String target_kdpst) {
    	Vector v = null;
    	ListIterator li = null;
    	java.sql.Date today_dt= AskSystem.getTodayDate();
    	//System.out.println("today_dt="+today_dt);
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//stmt = con.prepareStatement("SELECT TGL_SIDAK,KDPST_KENDALI,NAMA_JABATAN_PENGAWAS,NAMA_JABATAN_INPUT_AWAL FROM (STANDARD_RIWAYAT_PENGENDALIAN a inner join STANDARD_MANUAL_PENGENDALIAN c on a.ID_KENDALI=c.ID_KENDALI) left join STANDARD_RIWAYAT_EVALUASI b on ID_HIST=ID_HIST_KENDAL where ID_HIST_KENDAL is null;");
        	if(Checker.isStringNullOrEmpty(target_kdpst)) {
        		stmt = con.prepareStatement("SELECT TGL_SIDAK,KDPST_KENDALI,NAMA_JABATAN_PENGAWAS,NAMA_JABATAN_INPUT_AWAL,PROSES_DAN_OBJEK_YG_DIAWASI,a.ID_HIST,a.ID_KENDALI,c.VERSI_ID,c.STD_ISI_ID,e.ID_STD,e.ID_MASTER_STD,e.ID_TIPE_STD,e.KET_TIPE_STD,NEXT_TGL_SIDAK FROM (STANDARD_RIWAYAT_PENGENDALIAN a inner join STANDARD_MANUAL_PENGENDALIAN c on a.ID_KENDALI=c.ID_KENDALI) left join STANDARD_RIWAYAT_EVALUASI b on ID_HIST=ID_HIST_KENDAL inner join STANDARD_ISI_TABLE d on STD_ISI_ID=ID inner join STANDARD_TABLE e on d.ID_STD=e.ID_STD where NEXT_TGL_SIDAK<? order by NEXT_TGL_SIDAK desc limit 250");
        		stmt.setDate(1, today_dt);
        	}
        	else {
        		stmt = con.prepareStatement("SELECT TGL_SIDAK,KDPST_KENDALI,NAMA_JABATAN_PENGAWAS,NAMA_JABATAN_INPUT_AWAL,PROSES_DAN_OBJEK_YG_DIAWASI,a.ID_HIST,a.ID_KENDALI,c.VERSI_ID,c.STD_ISI_ID,e.ID_STD,e.ID_MASTER_STD,e.ID_TIPE_STD,e.KET_TIPE_STD,NEXT_TGL_SIDAK FROM (STANDARD_RIWAYAT_PENGENDALIAN a inner join STANDARD_MANUAL_PENGENDALIAN c on a.ID_KENDALI=c.ID_KENDALI) left join STANDARD_RIWAYAT_EVALUASI b on ID_HIST=ID_HIST_KENDAL inner join STANDARD_ISI_TABLE d on STD_ISI_ID=ID inner join STANDARD_TABLE e on d.ID_STD=e.ID_STD where NEXT_TGL_SIDAK<? and KDPST_KENDALI=? order by NEXT_TGL_SIDAK desc limit 250");
        		stmt.setDate(1, today_dt);
        		stmt.setString(2, target_kdpst);
        	}
        	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		//TGL_SIDAK,
        		String tgl_sidak = ""+rs.getDate(1);
        		//KDPST_KENDALI,
        		String kdpst = ""+rs.getString(2);
        		//NAMA_JABATAN_PENGAWAS,
        		String controller = ""+rs.getString(3);
        		//NAMA_JABATAN_INPUT_AWAL,
        		String surveyor = ""+rs.getString(4);
        		//PROSES_DAN_OBJEK_YG_DIAWASI,
        		String param = ""+rs.getString(5);
        		//a.ID_HIST,
        		String id_hist = ""+rs.getInt(6);
        		//a.ID_KENDALI,
        		String id_kendali = ""+rs.getInt(7);
        		//c.VERSI_ID,
        		String id_versi = ""+rs.getInt(8);
        		//c.STD_ISI_ID,
        		String id_std_isi = ""+rs.getInt(9);
        		//e.ID_STD,
        		String id_std = ""+rs.getInt(10);
        		//e.ID_MASTER_STD,
        		String id_master_std = ""+rs.getInt(11);
        		//e.ID_TIPE_STD,
        		String id_tipe_std = ""+rs.getInt(12);
        		//e.KET_TIPE_STD
        		String ket_tipe_std = ""+rs.getString(13);
        		String tgl_next_sidak = ""+rs.getDate(14);
        		String tmp = tgl_sidak+"~"+kdpst+"~"+controller+"~"+surveyor+"~"+param+"~"+id_hist+"~"+id_kendali+"~"+id_versi+"~"+id_std_isi+"~"+id_std+"~"+id_master_std+"~"+id_tipe_std+"~"+ket_tipe_std+"~"+tgl_next_sidak;
        		li.add(tmp.replace("~~", "~null~"));
        		while(rs.next()) {
        			tgl_sidak = ""+rs.getDate(1);
            		kdpst = ""+rs.getString(2);
            		controller = ""+rs.getString(3);
            		surveyor = ""+rs.getString(4);
            		param = ""+rs.getString(5);
            		id_hist = ""+rs.getInt(6);
            		id_kendali = ""+rs.getInt(7);
            		id_versi = ""+rs.getInt(8);
            		id_std_isi = ""+rs.getInt(9);
            		//e.ID_STD,
            		id_std = ""+rs.getInt(10);
            		//e.ID_MASTER_STD,
            		id_master_std = ""+rs.getInt(11);
            		//e.ID_TIPE_STD,
            		id_tipe_std = ""+rs.getInt(12);
            		//e.KET_TIPE_STD
            		ket_tipe_std = ""+rs.getString(13);
            		tgl_next_sidak = ""+rs.getDate(14);
            		tmp = tgl_sidak+"~"+kdpst+"~"+controller+"~"+surveyor+"~"+param+"~"+id_hist+"~"+id_kendali+"~"+id_versi+"~"+id_std_isi+"~"+id_std+"~"+id_master_std+"~"+id_tipe_std+"~"+ket_tipe_std+"~"+tgl_next_sidak;
            		li.add(tmp.replace("~~", "~null~"));
        		}
        	}
        	if(v!=null && v.size()>0) {
        		Vector v_clone = (Vector)v.clone();
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"~");
        			String tgl_sidak = st.nextToken();
        			String kdpst = st.nextToken();
        			String controller = st.nextToken();
        			String surveyor = st.nextToken();
        			String param = st.nextToken();
        			String id_hist = st.nextToken();
        			String id_kendali = st.nextToken();
        			String id_versi = st.nextToken();
        			String id_std_isi = st.nextToken();
        			String id_std = st.nextToken();
        			String id_master_std = st.nextToken();
        			String id_tipe_std = st.nextToken();
        			String ket_tipe_std = st.nextToken();
        			String tgl_next_sidak = st.nextToken();
        			boolean match = false;
        			ListIterator lic = v_clone.listIterator();
        			while(lic.hasNext() && !match) {
        				String brs1 = (String)lic.next();
        				if(brs1.startsWith(tgl_next_sidak)) {
        					match = true;
        					li.remove();
        				}
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
    	return v;
    }
    
    public int getVersiTerakhirManualPerencanaanTermasukDraft(int id_std) {
    	int versi=0;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select VERSI_ID from STANDARD_MANUAL_PERENCANAAN_UMUM where ID_STD=? order by VERSI_ID desc limit 1");
        	stmt.setInt(1, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		versi = rs.getInt(1);
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
    	return versi;
    }	
}

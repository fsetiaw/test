package beans.dbase.spmi;

import beans.dbase.SearchDb;
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
 * Session Bean implementation class SearchManualMutu
 */
@Stateless
@LocalBean
public class SearchManualMutu extends SearchDb {
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
    public SearchManualMutu() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchManualMutu(String operatorNpm) {
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
    public SearchManualMutu(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    /*
     * deprecated, krn manual diaktifasi manual tiap butir std
     */
    public Boolean apaAdaManualYgAktif(int id_versi, int id_std_isi) {
    	boolean ada=false;;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select NORUT from STANDARD_MANUAL_PERENCANAAN where TGL_STA is not null and TGL_END is null and VERSI_ID=? and STD_ISI_ID=?");
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ada = true;
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
    	return ada;
    }
    
    public Boolean apaSeluruhManualPpeppSudahAktif(int id_versi, int id_std_isi) {
    	boolean sudah=true;;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//perencanaan
        	stmt = con.prepareStatement("select TGL_STA from STANDARD_MANUAL_PERENCANAAN_UMUM where TGL_STA is not null and TGL_END is null and VERSI_ID=? and ID_STD=?");
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	rs = stmt.executeQuery();
        	if(!rs.next()) {
        		sudah = false;
        	}
        	//pelaksanaan
        	if(sudah) {
        		stmt = con.prepareStatement("select TGL_STA from STANDARD_MANUAL_PELAKSANAAN_UMUM where TGL_STA is not null and TGL_END is null and VERSI_ID=? and ID_STD=?");
            	stmt.setInt(1, id_versi);
            	stmt.setInt(2, id_std_isi);
            	rs = stmt.executeQuery();
            	if(!rs.next()) {
            		sudah = false;
            	}
        	}
        	//evaluasi
        	if(sudah) {
        		stmt = con.prepareStatement("select TGL_STA from STANDARD_MANUAL_EVALUASI_UMUM where TGL_STA is not null and TGL_END is null and VERSI_ID=? and ID_STD=?");
            	stmt.setInt(1, id_versi);
            	stmt.setInt(2, id_std_isi);
            	rs = stmt.executeQuery();
            	if(!rs.next()) {
            		sudah = false;
            	}
        	}
        	//pelaksanaan
        	if(sudah) {
        		stmt = con.prepareStatement("select TGL_STA from STANDARD_MANUAL_PENGENDALIAN_UMUM where TGL_STA is not null and TGL_END is null and VERSI_ID=? and ID_STD=?");
            	stmt.setInt(1, id_versi);
            	stmt.setInt(2, id_std_isi);
            	rs = stmt.executeQuery();
            	if(!rs.next()) {
            		sudah = false;
            	}
        	}
        	//pelaksanaan
        	if(sudah) {
        		stmt = con.prepareStatement("select TGL_STA from STANDARD_MANUAL_PENINGKATAN_UMUM where TGL_STA is not null and TGL_END is null and VERSI_ID=? and ID_STD=?");
            	stmt.setInt(1, id_versi);
            	stmt.setInt(2, id_std_isi);
            	rs = stmt.executeQuery();
            	if(!rs.next()) {
            		sudah = false;
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
    	return sudah;
    }
    

    public int autoInsertManual(int id_versi, int id_std_isi, String pilih_ppepp) {
    	//System.out.println("masuk autoInsertManual");
    	int updated=0;
    	int norut_man=1;//karena di cek hanya blum ada manual= jadi versi 1
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(!Checker.isStringNullOrEmpty(pilih_ppepp)) {
        		//cek apa item ini sudah ada maualnya
        		String sql = "select VERSI_ID from STANDARD_MANUAL_"+pilih_ppepp.toUpperCase().trim()+" where VERSI_ID=? and STD_ISI_ID=? and NORUT=?";
        		stmt = con.prepareStatement(sql);
        		stmt.setInt(1, id_versi);
        		stmt.setInt(2, id_std_isi);
        		stmt.setInt(3, norut_man);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			//sudah ada ignore ajah
        			//System.out.println("ignore");
        		}
        		else {
        			//belum ada
        			//1.cari apakah sudah ada manual dari item lain yg sejenis = id_std sama
        			//a.get ID_STD
        			stmt = con.prepareStatement("select ID_STD from STANDARD_ISI_TABLE where ID=?");
        			stmt.setInt(1, id_std_isi);
        			rs = stmt.executeQuery();
        			rs.next();
        			int id_std = rs.getInt(1);
        			//b.search apa sdh pernah ada maualnya
        			sql = "SELECT * FROM STANDARD_ISI_TABLE A inner join STANDARD_MANUAL_"+pilih_ppepp.toUpperCase().trim()+" B on A.ID=B.STD_ISI_ID where A.ID_STD=? order by B.VERSI_ID desc,B.NORUT desc";
        			stmt=con.prepareStatement(sql);
        			stmt.setInt(1, id_std);
        			rs = stmt.executeQuery();
        			if(!rs.next()) {
        				//belum ada jadi memang harus buat dari nol
        				//System.out.println("harus buat dari nol");
        			}
        			else {
        				//sudah ada jadi copypaste
        				
        				int i=1;
        				//A.ID
        				String a_id=rs.getString("A.ID");
        				//A.ID_STD
        				String a_id_std=rs.getString("ID_STD");
        				//A.PERNYATAAN_STD
        				String a_pernyataan_std=rs.getString("A.PERNYATAAN_STD");
        				//A.NO_BUTIR
        				String a_no_butir=rs.getString("A.NO_BUTIR");
        				//A.KDPST
        				String a_kdpst=rs.getString("A.KDPST");
        				//A.RASIONALE
        				String a_rsionale=rs.getString("A.RASIONALE");
        				//A.AKTIF
        				String a_aktif=rs.getString("A.AKTIF");
        				//A.TGL_MULAI_AKTIF
        				String a_tgl_sta_aktif=rs.getString("A.TGL_MULAI_AKTIF");
        				//A.TGL_STOP_AKTIF
        				String a_tgl_end_aktif=rs.getString("A.TGL_STOP_AKTIF");
        				//A.SCOPE
        				String a_scope=rs.getString("A.SCOPE");
        				//A.TIPE_PROSES_PENGAWASAN
        				String b_tipe_pengawasan=rs.getString("A.TIPE_PROSES_PENGAWASAN");
        				//B.VERSI_ID
        				String b_versi_id=rs.getString("B.VERSI_ID");
        				//B.STD_ISI_ID
        				String b_std_isi_id=rs.getString("B.STD_ISI_ID");
        				//B.NORUT
        				String b_norut=rs.getString("B.NORUT");
        				//B.TGL_STA
        				String b_tgl_sta=rs.getString("B.TGL_STA");
        				//B.TGL_END
        				String b_tgl_end=rs.getString("B.TGL_END");
        				//B.TKN_JAB_PERUMUS
        				String b_tkn_jab_rumus=rs.getString("B.TKN_JAB_PERUMUS");
        				//B.TKN_JAB_PERIKSA
        				String b_tkn_jab_cek=rs.getString("B.TKN_JAB_PERIKSA");
        				//B.TKN_JAB_SETUJU
        				String b_tkn_jab_stuju=rs.getString("B.TKN_JAB_SETUJU");
        				//B.TKN_JAB_PENETAP
        				String b_tkn_jab_tetap=rs.getString("B.TKN_JAB_PENETAP");
        				//B.TKN_JAB_KENDALI
        				String b_tkn_jab_kendali=rs.getString("B.TKN_JAB_KENDALI");
        				//B.TKN_JAB_PETUGAS_LAP
        				String b_tkn_jab_lap=rs.getString("B.TKN_JAB_PETUGAS_LAP");
        				//B.TUJUAN
        				String b_tujuan=rs.getString("B.TUJUAN");
        				//B.LINGKUP
        				String b_lingkup=rs.getString("B.LINGKUP");
        				//B.DEFINISI
        				String b_definisi=rs.getString("B.DEFINISI");
        				//B.PROSEDUR
        				String b_prosedur=rs.getString("B.PROSEDUR");
        				//B.KUALIFIKASI
        				String b_kuali=rs.getString("B.KUALIFIKASI");
        				//B.DOKUMEN
        				String b_dok=rs.getString("B.DOKUMEN");
        				//B.REFERENSI
        				String b_ref=rs.getString("B.REFERENSI");
        				//B.TGL_RUMUS
        				String b_boolean_tgl_rumus=rs.getString("B.TGL_RUMUS");
        				//B.TGL_CEK
        				String b_boolean_tgl_cek=rs.getString("B.TGL_CEK");
        				//B.TGL_STUJU
        				String b_boolean_tgl_stuju=rs.getString("B.TGL_STUJU");
        				//B.TGL_TETAP
        				String b_boolean_tgl_tetap=rs.getString("B.TGL_TETAP");
        				//B.TGL_KENDALI
        				String b_boolean_tgl_kendali=rs.getString("B.TGL_KENDALI");
        				//B.TGL_RUMUS_STD
        				String b_boolean_tgl_rumus_std=rs.getString("B.TGL_RUMUS_STD");
        				//B.TGL_CEK_STD
        				String b_boolean_tgl_cek_std=rs.getString("B.TGL_CEK_STD");
        				//B.TGL_STUJU_STD
        				String b_boolean_tgl_stuju_std=rs.getString("B.TGL_STUJU_STD");
        				//B.TGL_TETAP_STD
        				String b_boolean_tgl_tetap_std=rs.getString("B.TGL_TETAP_STD");
        				//B.TGL_KENDALI_STD
        				String b_boolean_tgl_kendali_std=rs.getString("B.TGL_KENDALI_STD");
        				
        				//System.out.println("copy paste dari diskontinue");
    					sql = "INSERT INTO STANDARD_MANUAL_"+pilih_ppepp.toUpperCase().trim()+"(VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI,TGL_RUMUS,TGL_CEK,TGL_STUJU,TGL_TETAP,TGL_KENDALI,TGL_RUMUS_STD,TGL_CEK_STD,TGL_STUJU_STD,TGL_TETAP_STD,TGL_KENDALI_STD)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    					stmt = con.prepareStatement(sql);
    					i=1;
        				if(Checker.isStringNullOrEmpty(a_tgl_end_aktif)) {
        					//either aktif ato blum pernah aktif jadinya copy seluruhnya
        					//System.out.println("copy paste dari yg belum aktif/sdg aktif");
        				
        					//sql = "INSERT INTO STANDARD_MANUAL_"+pilih_ppepp.toUpperCase().trim()+"(VERSI_ID,STD_ISI_ID,NORUT,TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI,TGL_RUMUS,TGL_CEK,TGL_STUJU,TGL_TETAP,TGL_KENDALI,TGL_RUMUS_STD,TGL_CEK_STD,TGL_STUJU_STD,TGL_TETAP_STD,TGL_KENDALI_STD)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        					//stmt = con.prepareStatement(sql);
        					//i=0;
        					//B.VERSI_ID
            				stmt.setInt(i++, 1);
            				//B.STD_ISI_ID
            				stmt.setInt(i++, id_std_isi);
            				//B.NORUT = belum ada = 1
            				stmt.setInt(i++, 1);
            				//B.TGL_STA
            				if(Checker.isStringNullOrEmpty(b_tgl_sta)) {
            					stmt.setNull(i++, java.sql.Types.DATE);
            				}
            				else {
            					stmt.setDate(i++, java.sql.Date.valueOf(b_tgl_sta));
            				}
            				//B.TGL_END
            				if(Checker.isStringNullOrEmpty(b_tgl_end)) {
            					stmt.setNull(i++, java.sql.Types.DATE);
            				}
            				else {
            					stmt.setDate(i++, java.sql.Date.valueOf(b_tgl_end));
            				}
            				//B.TKN_JAB_PERUMUS
            				if(Checker.isStringNullOrEmpty(b_tkn_jab_rumus)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_tkn_jab_rumus);
            				}
            				//B.TKN_JAB_PERIKSA
            				if(Checker.isStringNullOrEmpty(b_tkn_jab_cek)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_tkn_jab_cek);
            				}
            				//B.TKN_JAB_SETUJU
            				if(Checker.isStringNullOrEmpty(b_tkn_jab_stuju)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_tkn_jab_stuju);
            				}
            				//B.TKN_JAB_PENETAP
            				if(Checker.isStringNullOrEmpty(b_tkn_jab_tetap)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_tkn_jab_tetap);
            				}
            				//B.TKN_JAB_KENDALI
            				if(Checker.isStringNullOrEmpty(b_tkn_jab_kendali)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_tkn_jab_kendali);
            				}
            				//B.TKN_JAB_PETUGAS_LAP
            				if(Checker.isStringNullOrEmpty(b_tkn_jab_lap)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_tkn_jab_lap);
            				}
            				//B.TUJUAN
            				if(Checker.isStringNullOrEmpty(b_tujuan)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_tujuan);
            				}
            				//B.LINGKUP
            				if(Checker.isStringNullOrEmpty(b_lingkup)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_lingkup);
            				}
            				//B.DEFINISI
            				if(Checker.isStringNullOrEmpty(b_definisi)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_definisi);
            				}
            				//B.PROSEDUR
            				if(Checker.isStringNullOrEmpty(b_prosedur)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_prosedur);
            				}
            				//B.KUALIFIKASI
            				if(Checker.isStringNullOrEmpty(b_kuali)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_kuali);
            				}
            				//B.DOKUMEN
            				if(Checker.isStringNullOrEmpty(b_dok)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_dok);
            				}
            				//B.REFERENSI
            				if(Checker.isStringNullOrEmpty(b_ref)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_ref);
            				}
            				//B.TGL_RUMUS
            				if(Checker.isStringNullOrEmpty(b_boolean_tgl_rumus)) {
            					stmt.setNull(i++, java.sql.Types.DATE);
            				}
            				else {
            					stmt.setDate(i++, java.sql.Date.valueOf(b_boolean_tgl_rumus));
            				}
            				//B.TGL_CEK
            				if(Checker.isStringNullOrEmpty(b_boolean_tgl_cek)) {
            					stmt.setNull(i++, java.sql.Types.DATE);
            				}
            				else {
            					stmt.setDate(i++, java.sql.Date.valueOf(b_boolean_tgl_cek));
            				}
            				//B.TGL_STUJU
            				if(Checker.isStringNullOrEmpty(b_boolean_tgl_stuju)) {
            					stmt.setNull(i++, java.sql.Types.DATE);
            				}
            				else {
            					stmt.setDate(i++, java.sql.Date.valueOf(b_boolean_tgl_stuju));
            				}
            				//B.TGL_TETAP
            				if(Checker.isStringNullOrEmpty(b_boolean_tgl_tetap)) {
            					stmt.setNull(i++, java.sql.Types.DATE);
            				}
            				else {
            					stmt.setDate(i++, java.sql.Date.valueOf(b_boolean_tgl_tetap));
            				}
            				//B.TGL_KENDALI
            				if(Checker.isStringNullOrEmpty(b_boolean_tgl_kendali)) {
            					stmt.setNull(i++, java.sql.Types.DATE);
            				}
            				else {
            					stmt.setDate(i++, java.sql.Date.valueOf(b_boolean_tgl_kendali));
            				}
            				/*
            				 * krn butir stabdar di play individualli jadi set null
            				 */
            				
            				//B.TGL_RUMUS_STD
            				if(Checker.isStringNullOrEmpty(b_boolean_tgl_rumus_std)) {
            					stmt.setNull(i++, java.sql.Types.DATE);
            				}
            				else {
            					//stmt.setDate(i++, java.sql.Date.valueOf(b_boolean_tgl_rumus_std));
            					stmt.setNull(i++, java.sql.Types.DATE);
            				}
            				//B.TGL_CEK_STD
            				if(Checker.isStringNullOrEmpty(b_boolean_tgl_cek_std)) {
            					stmt.setNull(i++, java.sql.Types.DATE);
            				}
            				else {
            					//stmt.setDate(i++, java.sql.Date.valueOf(b_boolean_tgl_cek_std));
            					stmt.setNull(i++, java.sql.Types.DATE);
            				}
            				//B.TGL_STUJU_STD
            				if(Checker.isStringNullOrEmpty(b_boolean_tgl_stuju_std)) {
            					stmt.setNull(i++, java.sql.Types.DATE);
            				}
            				else {
            					//stmt.setDate(i++, java.sql.Date.valueOf(b_boolean_tgl_stuju_std));
            					stmt.setNull(i++, java.sql.Types.DATE);
            				}
            				//B.TGL_TETAP_STD
            				if(Checker.isStringNullOrEmpty(b_boolean_tgl_tetap_std)) {
            					stmt.setNull(i++, java.sql.Types.DATE);
            				}
            				else {
            					//stmt.setDate(i++, java.sql.Date.valueOf(b_boolean_tgl_tetap_std));
            					stmt.setNull(i++, java.sql.Types.DATE);
            				}
            				//B.TGL_KENDALI_STD
            				if(Checker.isStringNullOrEmpty(b_boolean_tgl_kendali_std)) {
            					stmt.setNull(i++, java.sql.Types.DATE);
            				}
            				else {
            					//stmt.setDate(i++, java.sql.Date.valueOf(b_boolean_tgl_kendali_std));
            					stmt.setNull(i++, java.sql.Types.DATE);
            				}
            				updated = stmt.executeUpdate();
        				}
        				else {
        					//System.out.println("copy paste dari diskontinue");
        					//sudah diskontinue, jadi jangan copy tgl sta, peneta[an dll dan boolean tgl
        					
        					//B.VERSI_ID
            				stmt.setInt(i++, 1);
            				//B.STD_ISI_ID
            				stmt.setInt(i++, id_std_isi);
            				//B.NORUT = belum ada = 1
            				stmt.setInt(i++, 1);
            				//B.TGL_STA
            				stmt.setNull(i++, java.sql.Types.DATE);
            				//B.TGL_END
            				stmt.setNull(i++, java.sql.Types.DATE);
            				//B.TKN_JAB_PERUMUS
            				if(Checker.isStringNullOrEmpty(b_tkn_jab_rumus)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_tkn_jab_rumus);
            				}
            				//B.TKN_JAB_PERIKSA
            				if(Checker.isStringNullOrEmpty(b_tkn_jab_cek)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_tkn_jab_cek);
            				}
            				//B.TKN_JAB_SETUJU
            				if(Checker.isStringNullOrEmpty(b_tkn_jab_stuju)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_tkn_jab_stuju);
            				}
            				//B.TKN_JAB_PENETAP
            				if(Checker.isStringNullOrEmpty(b_tkn_jab_tetap)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_tkn_jab_tetap);
            				}
            				//B.TKN_JAB_KENDALI
            				if(Checker.isStringNullOrEmpty(b_tkn_jab_kendali)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_tkn_jab_kendali);
            				}
            				//B.TKN_JAB_PETUGAS_LAP
            				if(Checker.isStringNullOrEmpty(b_tkn_jab_lap)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_tkn_jab_lap);
            				}
            				//B.TUJUAN
            				if(Checker.isStringNullOrEmpty(b_tujuan)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_tujuan);
            				}
            				//B.LINGKUP
            				if(Checker.isStringNullOrEmpty(b_lingkup)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_lingkup);
            				}
            				//B.DEFINISI
            				if(Checker.isStringNullOrEmpty(b_definisi)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_definisi);
            				}
            				//B.PROSEDUR
            				if(Checker.isStringNullOrEmpty(b_prosedur)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_prosedur);
            				}
            				//B.KUALIFIKASI
            				if(Checker.isStringNullOrEmpty(b_kuali)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_kuali);
            				}
            				//B.DOKUMEN
            				if(Checker.isStringNullOrEmpty(b_dok)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_dok);
            				}
            				//B.REFERENSI
            				if(Checker.isStringNullOrEmpty(b_ref)) {
            					stmt.setNull(i++, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(i++, b_ref);
            				}
            				//B.TGL_RUMUS
            				stmt.setNull(i++, java.sql.Types.DATE);
            				//B.TGL_CEK
            				stmt.setNull(i++, java.sql.Types.DATE);
            				//B.TGL_STUJU
            				stmt.setNull(i++, java.sql.Types.DATE);
            				//B.TGL_TETAP
            				stmt.setNull(i++, java.sql.Types.DATE);
            				//B.TGL_KENDALI
            				stmt.setNull(i++, java.sql.Types.DATE);
            				//B.TGL_RUMUS_STD
            				stmt.setNull(i++, java.sql.Types.DATE);
            				//B.TGL_CEK_STD
            				stmt.setNull(i++, java.sql.Types.DATE);
            				//B.TGL_STUJU_STD
            				stmt.setNull(i++, java.sql.Types.DATE);
            				//B.TGL_TETAP_STD
            				stmt.setNull(i++, java.sql.Types.DATE);
            				//B.TGL_KENDALI_STD
            				stmt.setNull(i++, java.sql.Types.DATE);
            				updated = stmt.executeUpdate();
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
    	return updated;
    }
    
    public Boolean apaManualSudahAdaTglPemeriksaan(int id_versi, int id_std_isi, int norut_man) {
    	boolean ada=false;;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_CEK from RIWAYAT_PERENCANAAN where VERSI_ID=? and STD_ISI_ID=? and NORUT=? and TGL_CEK=true");
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	stmt.setInt(3, norut_man);

        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ada = true;
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
    	return ada;
    }
    
    public Boolean apaManualSudahAdaTglPemeriksaanUmum(int id_versi, int id_std) {
    	boolean ada=false;;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_CEK from RIWAYAT_PERENCANAAN_UMUM where VERSI_ID=? and ID_STD=? and TGL_CEK=true");
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std);
           	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ada = true;
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
    	return ada;
    }
    
    public Boolean apaManualSudahAdaTglPersetujuan(int id_versi, int id_std_isi, int norut_man) {
    	boolean ada=false;;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_STUJU from RIWAYAT_PERENCANAAN where VERSI_ID=? and STD_ISI_ID=? and NORUT=? and TGL_STUJU=true");
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	stmt.setInt(3, norut_man);

        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ada = true;
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
    	return ada;
    }
    
    public Boolean apaManualSudahAdaTglPenetapan(int id_versi, int id_std_isi, int norut_man) {
    	boolean ada=false;;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_TETAP from RIWAYAT_PERENCANAAN where VERSI_ID=? and STD_ISI_ID=? and NORUT=? and TGL_TETAP=true");
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	stmt.setInt(3, norut_man);

        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ada = true;
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
    	return ada;
    }
    
    public Boolean apaManualSudahAdaTglPerumusan(int id_versi, int id_std_isi, int norut_man) {
    	boolean ada=false;;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_RUMUS from RIWAYAT_PERENCANAAN where VERSI_ID=? and STD_ISI_ID=? and NORUT=? and TGL_RUMUS=true");
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	stmt.setInt(3, norut_man);

        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ada = true;
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
    	return ada;
    }
    
    public Boolean apaManualSudahAdaTglPerumusanUmum(int id_versi, int id_std) {
    	boolean ada=false;;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_RUMUS from RIWAYAT_PERENCANAAN_UMUM where VERSI_ID=? and ID_STD=? and TGL_RUMUS=true");
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std);
        	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ada = true;
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
    	return ada;
    }
    
    public Boolean apaManualSudahAdaTglPersetujuanUmum(int id_versi, int id_std) {
    	boolean ada=false;;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_STUJU from RIWAYAT_PERENCANAAN_UMUM where VERSI_ID=? and ID_STD=? and TGL_STUJU=true");
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std);
        	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ada = true;
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
    	return ada;
    }

    public Boolean apaManualSudahAdaTglPenetapanUmum(int id_versi, int id_std) {
    	boolean ada=false;;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_TETAP from RIWAYAT_PERENCANAAN_UMUM where VERSI_ID=? and ID_STD=? and TGL_TETAP=true");
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ada = true;
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
    	return ada;
    }
    /*
     * UDAH ADA DI SearchManual.searchListManualPerencanaanUmum
    public Vector getInfoManual(String pilih_ppepp, int id_versi, int id_std) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(pilih_ppepp.equalsIgnoreCase("penetapan")||pilih_ppepp.equalsIgnoreCase("perencanaan")) {
        		stmt = con.prepareStatement("SELECT TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI,TGL_RUMUS,TGL_CEK,TGL_STUJU,TGL_TETAP,TGL_KENDALI,TGL_RUMUS_STD,TGL_CEK_STD,TGL_STUJU_STD,TGL_TETAP_STD,TGL_KENDALI_STD from STANDARD_MANUAL_PERENCANAAN_UMUM where VERSI_ID=? and ID_STD=?");	
        	}
        	else if(pilih_ppepp.equalsIgnoreCase("pelaksanaan")) {
        		stmt = con.prepareStatement("SELECT TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI,TGL_RUMUS,TGL_CEK,TGL_STUJU,TGL_TETAP,TGL_KENDALI,TGL_RUMUS_STD,TGL_CEK_STD,TGL_STUJU_STD,TGL_TETAP_STD,TGL_KENDALI_STD from STANDARD_MANUAL_PELAKSANAAN_UMUM where VERSI_ID=? and ID_STD=?");	
        	}
        	else if(pilih_ppepp.equalsIgnoreCase("evaluasi")) {
        		stmt = con.prepareStatement("SELECT TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI,TGL_RUMUS,TGL_CEK,TGL_STUJU,TGL_TETAP,TGL_KENDALI,TGL_RUMUS_STD,TGL_CEK_STD,TGL_STUJU_STD,TGL_TETAP_STD,TGL_KENDALI_STD from STANDARD_MANUAL_EVALUASI_UMUM where VERSI_ID=? and ID_STD=?");	
        	}
        	else if(pilih_ppepp.equalsIgnoreCase("pengendalian")) {
        		stmt = con.prepareStatement("SELECT TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI,TGL_RUMUS,TGL_CEK,TGL_STUJU,TGL_TETAP,TGL_KENDALI,TGL_RUMUS_STD,TGL_CEK_STD,TGL_STUJU_STD,TGL_TETAP_STD,TGL_KENDALI_STD from STANDARD_MANUAL_PENGENDALIAN_UMUM where VERSI_ID=? and ID_STD=?");	
        	}
        	else if(pilih_ppepp.equalsIgnoreCase("peningkatan")) {
        		stmt = con.prepareStatement("SELECT TGL_STA,TGL_END,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,TUJUAN,LINGKUP,DEFINISI,PROSEDUR,KUALIFIKASI,DOKUMEN,REFERENSI,TGL_RUMUS,TGL_CEK,TGL_STUJU,TGL_TETAP,TGL_KENDALI,TGL_RUMUS_STD,TGL_CEK_STD,TGL_STUJU_STD,TGL_TETAP_STD,TGL_KENDALI_STD from STANDARD_MANUAL_PENINGKATAN_UMUM where VERSI_ID=? and ID_STD=?");	
        	}
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		int i=1;
        		//TGL_STA
        		String tgl_sta = rs.getString(i++);
        		//TGL_END
        		String tgl_end = rs.getString(i++);
        		//TKN_JAB_PERUMUS
        		String jab_rumus = rs.getString(i++);
        		//TKN_JAB_PERIKSA
        		String jab_cek = rs.getString(i++);
        		//TKN_JAB_SETUJU
        		String jab_stuju = rs.getString(i++);
        		//TKN_JAB_PENETAP
        		String jab_tetap = rs.getString(i++);
        		//TKN_JAB_KENDALI
        		String jab_ketgl_standal = rs.getString(i++);
        		//TKN_JAB_PETUGAS_LAP
        		String jab_lap = rs.getString(i++);
        		//TUJUAN
        		String tujuan = rs.getString(i++);
        		//LINGKUP
        		String lingkup = rs.getString(i++);
        		//DEFINISI
        		String definisi = rs.getString(i++);
        		//PROSEDUR
        		String prosedur = rs.getString(i++);
        		//KUALIFIKASI
        		String kuali = rs.getString(i++);
        		//DOKUMEN
        		String dok = rs.getString(i++);
        		//REFERENSI
        		String ref = rs.getString(i++);
        		//TGL_RUMUS
        		String tgl_rumus = rs.getString(i++);
        		//TGL_CEK
        		String tgl_cek = rs.getString(i++);
        		//TGL_STUJU
        		String tgl_stuju = rs.getString(i++);
        		//TGL_TETAP
        		String tgl_tetap = rs.getString(i++);
        		//TGL_KENDALI
        		String tgl_kendal = rs.getString(i++);
        		//TGL_RUMUS_STD
        		String tgl_rumus_std = rs.getString(i++);
        		//TGL_CEK_STD
        		String tgl_cek_std = rs.getString(i++);
        		//TGL_STUJU_STD
        		String tgl_stuju_std = rs.getString(i++);
        		//TGL_TETAP_STD
        		String tgl_tetap_std = rs.getString(i++);
        		//TGL_KENDALI_STD
        		String tgl_kendal_std = rs.getString(i++);
        		String tmp = tgl_sta+"~"+tgl_end+"~"+jab_rumus+"~"+jab_cek+"~"+jab_stuju+"~"+jab_tetap+"~"+jab_ketgl_standal+"~"+jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kuali+"~"+dok+"~"+ref+"~"+tgl_rumus+"~"+tgl_cek+"~"+tgl_stuju+"~"+tgl_tetap+"~"+tgl_kendal+"~"+tgl_rumus_std+"~"+tgl_cek_std+"~"+tgl_stuju_std+"~"+tgl_tetap_std+"~"+tgl_kendal_std;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		v=new Vector();
        		li = v.listIterator();
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
	*/
    
    public Vector getOverviewStatusAktifManual() {
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("SELECT distinct A.ID_MASTER_STD, A.KET_TIPE_STD,B.ID_STD,B.ID_TIPE_STD,B.KET_TIPE_STD FROM STANDARD_MASTER_TABLE A inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD  where A.ID_MASTER_STD>0 order by A.ID_MASTER_STD,B.ID_STD,B.ID_TIPE_STD");
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		String tmp = null;
        		do {
        			String id_master_std = rs.getString(1);
        			String ket_master_std = rs.getString(2);
        			String id_std = rs.getString(3);
        			String id_tipe_std = rs.getString(4);
        			String ket_tipe_std = rs.getString(5);
        			tmp = new String(id_master_std+"~"+ket_master_std+"~"+id_std+"~"+id_tipe_std+"~"+ket_tipe_std);
        			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        			li.add(tmp);
        		}
        		while(rs.next());
        		
        		stmt=con.prepareStatement("SELECT VERSI_ID,TGL_STA FROM STANDARD_MANUAL_PERENCANAAN_UMUM where ID_STD=? and TGL_STA is not null and TGL_END is null");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"~");
        			String id_master_std = st.nextToken();
        			String ket_master_std = st.nextToken();
        			String id_std = st.nextToken();
        			String id_tipe_std = st.nextToken();
        			String ket_tipe_std = st.nextToken();
        			stmt.setInt(1, Integer.parseInt(id_std));
            		rs = stmt.executeQuery();
            		String tgl_sta = "null";
            		String id_versi = "null";
            		if(rs.next()) {
            			id_versi=rs.getString(1);
            			tgl_sta=rs.getString(2);
            		}
            		tmp = new String(id_master_std+"~"+ket_master_std+"~"+id_std+"~"+id_tipe_std+"~"+ket_tipe_std+"~"+tgl_sta+"~"+id_versi);
        			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        			li.set(tmp);
        			//System.out.println(tmp);
        		}
        		
        		stmt=con.prepareStatement("SELECT VERSI_ID,TGL_STA FROM STANDARD_MANUAL_PELAKSANAAN_UMUM where ID_STD=? and TGL_STA is not null and TGL_END is null");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"~");
        			String id_master_std = st.nextToken();
        			String ket_master_std = st.nextToken();
        			String id_std = st.nextToken();
        			String id_tipe_std = st.nextToken();
        			String ket_tipe_std = st.nextToken();
        			//String tgl_sta_plan = st.nextToken();
            		//String id_versi_plan = st.nextToken();
            		String tgl_sta_do = "null";
            		String id_versi_do = "null";
            		stmt.setInt(1, Integer.parseInt(id_std));
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			id_versi_do=rs.getString(1);
            			tgl_sta_do=rs.getString(2);
            		}
            		tmp = new String(brs+"~"+tgl_sta_do+"~"+id_versi_do);
        			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        			li.set(tmp);
        			//System.out.println(tmp);
        		}
        		
        		stmt=con.prepareStatement("SELECT VERSI_ID,TGL_STA FROM STANDARD_MANUAL_EVALUASI_UMUM where ID_STD=? and TGL_STA is not null and TGL_END is null");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"~");
        			String id_master_std = st.nextToken();
        			String ket_master_std = st.nextToken();
        			String id_std = st.nextToken();
        			String id_tipe_std = st.nextToken();
        			String ket_tipe_std = st.nextToken();
        			//String tgl_sta_plan = st.nextToken();
            		//String id_versi_plan = st.nextToken();
            		//String tgl_sta_do = st.nextToken();
            		//String id_versi_do = st.nextToken();
            		String tgl_sta_eval = "null";
            		String id_versi_eval = "null";
            		stmt.setInt(1, Integer.parseInt(id_std));
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			id_versi_eval=rs.getString(1);
            			tgl_sta_eval=rs.getString(2);
            		}
            		tmp = new String(brs+"~"+tgl_sta_eval+"~"+id_versi_eval);
        			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        			li.set(tmp);
        			//System.out.println(tmp);
        		}
        		
        		stmt=con.prepareStatement("SELECT VERSI_ID,TGL_STA FROM STANDARD_MANUAL_PENGENDALIAN_UMUM where ID_STD=? and TGL_STA is not null and TGL_END is null");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"~");
        			String id_master_std = st.nextToken();
        			String ket_master_std = st.nextToken();
        			String id_std = st.nextToken();
        			String id_tipe_std = st.nextToken();
        			String ket_tipe_std = st.nextToken();
        			//String tgl_sta_plan = st.nextToken();
            		//String id_versi_plan = st.nextToken();
            		//String tgl_sta_do = st.nextToken();
            		//String id_versi_do = st.nextToken();
            		String tgl_sta_kendal = "null";
            		String id_versi_kendal = "null";
            		stmt.setInt(1, Integer.parseInt(id_std));
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			id_versi_kendal=rs.getString(1);
            			tgl_sta_kendal=rs.getString(2);
            		}
            		tmp = new String(brs+"~"+tgl_sta_kendal+"~"+id_versi_kendal);
        			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        			li.set(tmp);
        			//System.out.println(tmp);
        		}
        		
        		stmt=con.prepareStatement("SELECT VERSI_ID,TGL_STA FROM STANDARD_MANUAL_PENINGKATAN_UMUM where ID_STD=? and TGL_STA is not null and TGL_END is null");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"~");
        			String id_master_std = st.nextToken();
        			String ket_master_std = st.nextToken();
        			String id_std = st.nextToken();
        			String id_tipe_std = st.nextToken();
        			String ket_tipe_std = st.nextToken();
        			//String tgl_sta_plan = st.nextToken();
            		//String id_versi_plan = st.nextToken();
            		//String tgl_sta_do = st.nextToken();
            		//String id_versi_do = st.nextToken();
            		String tgl_sta_upg = "null";
            		String id_versi_upg = "null";
            		stmt.setInt(1, Integer.parseInt(id_std));
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			id_versi_upg=rs.getString(1);
            			tgl_sta_upg=rs.getString(2);
            		}
            		tmp = new String(brs+"~"+tgl_sta_upg+"~"+id_versi_upg);
        			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        			li.set(tmp);
        			//System.out.println(tmp);
        		}
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
    	return v;
    }
}

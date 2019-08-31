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
import java.util.Collections;
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
 * Session Bean implementation class UpdateStandarMutu
 */
@Stateless
@LocalBean
public class UpdateStandarMutu extends UpdateDb {
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
    public UpdateStandarMutu() {
        super();
        //TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateStandarMutu(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        //TODO Auto-generated constructor stub
    }
    
    public int toogleAktifasi(int id_std_isi,boolean current_value) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(current_value==true) {
        		//lanjut set end standard, tidak bisa diaktifkan kembali, harus buat ersi baru
        		java.sql.Date end_dt = AskSystem.getTodayDate(); 
        		stmt = con.prepareStatement("update STANDARD_ISI_TABLE set AKTIF=?,TGL_STOP_AKTIF=? where ID=?");
        		stmt.setBoolean(1, false);
        		stmt.setDate(2, end_dt);
        		stmt.setInt(3, id_std_isi);
        		updated = stmt.executeUpdate();
        	}
        	else {
        		/*
        		 * YG DAPAT DIAKTIFKAN ADALAH STD YG BELUM PERNAH AKTIF SEBELUMNYA [TGL_STOP_AKTIF SUDAH TERISI]
            	 * 
            	 * Jadi CEK DULU APA TGL STOP masih null
            	 */	
        		stmt = con.prepareStatement("select TGL_STOP_AKTIF from STANDARD_ISI_TABLE where ID=? and TGL_STOP_AKTIF is null");
        		stmt.setInt(1, id_std_isi);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			//lanjut aktifkan
        			java.sql.Date sta_dt = AskSystem.getTodayDate(); 
            		stmt = con.prepareStatement("update STANDARD_ISI_TABLE set AKTIF=?,TGL_MULAI_AKTIF=? where ID=?");
            		stmt.setBoolean(1, true);
            		stmt.setDate(2, sta_dt);
            		stmt.setInt(3, id_std_isi);
            		updated = stmt.executeUpdate();
        		}
        		else {
        			//SUDAH KADALUARSA : BUAT VERS BARU
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
    
    public int deleteButirStd(int id_std_isi) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "delete from STANDARD_ISI_TABLE where ID=?";
        	stmt = con.prepareStatement(sql);
    		stmt.setInt(1, id_std_isi);
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
    
    public int copyStandard(String id_std_isi, String id_versi, String mode_value_baru_revisi_kdpst) {
    	int id_std_isi_baru = 0;
    	int updated = 0;
    	//System.out.println("lanjut");
    	//java.sql.Date tgl_sta = ToolSpmi.getTglStaIfStandardAktif(id_std, id_versi)
    	try {
    		Vector v = null;
    		SearchRequest sr = new SearchRequest();
			v =  sr.getInfoStd_v1(Integer.parseInt(id_std_isi),Integer.parseInt(id_versi));
			ListIterator li = v.listIterator();
			String brs = (String)li.next();
			//System.out.println("baris = "+brs);
			StringTokenizer st = new StringTokenizer(brs,"`");
			
			//===========MASTER DATA===================
			//id+"`"+
			String id = st.nextToken();
			//id_std+"`"+
			String id_std = st.nextToken();
			//isi+"`"+
			String isi = st.nextToken();
			if(Checker.isStringNullOrEmpty(isi)) {
				isi = "";
			}
			//butir+"`"+
			String butir = st.nextToken();
			//std_kdpst+"`"+
			String kdpst_std = st.nextToken();
			//rasionale+"`"+
			String rasionale = st.nextToken();
			//id_versi+"`"+
			String versi = st.nextToken();
			//id_declare+"`"+
			String id_declare = st.nextToken();
			//id_do+"`"+
			String id_do = st.nextToken();
			//id_eval+"`"+
			String id_eval = st.nextToken();
			//id_control+"`"+
			String id_control = st.nextToken();
			//id_upgrade+"`"+
			String id_upgrade = st.nextToken();
			//tglsta+"`"+
			String tglsta = st.nextToken();
			//tglend+"`"+
			String tglend = st.nextToken();
			//thsms1+"`"+
			String thsms1 = st.nextToken();
			//thsms2+"`"+
			String thsms2 = st.nextToken();
			//thsms3+"`"+
			String thsms3 = st.nextToken();
			//thsms4+"`"+
			String thsms4 = st.nextToken();
			//thsms5+"`"+
			String thsms5 = st.nextToken();
			//thsms6+"`"+
			String thsms6 = st.nextToken();
			//pihak+"`"+
			String pihak = st.nextToken();
			String[]pihak_terkait = null;
			if(!Checker.isStringNullOrEmpty(pihak)) {
				StringTokenizer stt = null;
				if(pihak.contains("`")){
					stt = new StringTokenizer(pihak,"`");
				}
				else {
					stt = new StringTokenizer(pihak,",");
				}
				pihak_terkait = new String[stt.countTokens()];
				for(int i=0;i<stt.countTokens();i++) {
					pihak_terkait[i]=stt.nextToken();
				}
			}
			//dokumen+"`"+
			String tkn_doc = st.nextToken();
			String[]doc_terkait = null;
			if(!Checker.isStringNullOrEmpty(tkn_doc)) {
				StringTokenizer stt = null;
				if(tkn_doc.contains("`")){
					stt = new StringTokenizer(tkn_doc,"`");
				}
				else {
					stt = new StringTokenizer(tkn_doc,",");
				}
				doc_terkait = new String[stt.countTokens()];
				for(int i=0;i<stt.countTokens();i++) {
					doc_terkait[i]=stt.nextToken();
				}
			}
			//tkn_indikator+"`"+
			String tkn_indikator = st.nextToken();
			//norut+"`"+
			String norut = st.nextToken();
			
			//target_period_start+"`"
			String periode_awal = st.nextToken();
			//+unit_period+"`"+
			String unit_period = st.nextToken();
			//lama_per_period+"`"+
			String lama_per_period = st.nextToken();
			//target_unit1+"`"+
			String target_unit1 = st.nextToken();
			//target_unit2+"`"+
			String target_unit2 = st.nextToken();
			//target_unit3+"`"+
			String target_unit3 = st.nextToken();
			//target_unit4+"`"+
			String target_unit4 = st.nextToken();
			//target_unit5+"`"+
			String target_unit5 = st.nextToken();
			//target_unit6+"`"+
			String target_unit6 = st.nextToken();
			//tkn_param+"`"+
			String tkn_param = "null";
			if(st.hasMoreTokens()) {
				tkn_param = st.nextToken();
			}	
			//id_master+"`"+
			String id_master = "null";
			if(Checker.isStringNullOrEmpty(id_master) && st.hasMoreTokens()) {
				id_master = st.nextToken();
			}
			//id_tipe+"`"+
			String id_tipe = "null";
			if(Checker.isStringNullOrEmpty(id_tipe) && st.hasMoreTokens()) {
				id_tipe = st.nextToken();
			}
			//tkn_pengawas+"`"+
			String tkn_pengawas = "null";
			if(Checker.isStringNullOrEmpty(tkn_pengawas) && st.hasMoreTokens()) {
				tkn_pengawas = st.nextToken();
			}
			//scope+"`"+
			String cakupan_std = "null";
			if(Checker.isStringNullOrEmpty(cakupan_std) && st.hasMoreTokens()) {
				cakupan_std = st.nextToken();
			}
			//tipe_survey;
			String tipe_proses_pengawasan = "null";
			if(Checker.isStringNullOrEmpty(tipe_proses_pengawasan) && st.hasMoreTokens()) {
				tipe_proses_pengawasan = st.nextToken();
			}
			//===========END MASTER DATA===================
			UpdateRequest ur = new UpdateRequest();
    		UpdateForm uf = new UpdateForm();
    		
    		if(mode_value_baru_revisi_kdpst.equalsIgnoreCase("baru")) {
				//copy utk std baru
    			updated = uf.insertNuStandarIsi(pihak_terkait,null , isi, doc_terkait, rasionale);
			}
			else if(mode_value_baru_revisi_kdpst.equalsIgnoreCase("revisi")) {
				//copy up revisi standardnya non aktif dulu
				updated =uf.revisiStandarIsi( brs);
			}
			else {
				//copy untuk prodi
				//mode_value_baru_revisi_kdpst value untuk else ini  target_kdpst
				updated =uf.copyStandarIsi(mode_value_baru_revisi_kdpst, brs);
			}
    		
    		//updated = uf.insertNuStandarIsi(pihak_terkait,null , isi, doc_terkait, rasionale);
    		//System.out.println("updated="+updated);
    		if(updated>0) {
    			Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	//get id master undecider
            	stmt = con.prepareStatement("select ID_STD from STANDARD_MASTER_TABLE a inner join STANDARD_TABLE b on a.ID_MASTER_STD=b.ID_MASTER_STD where b.KET_TIPE_STD='BELUM DITENTUKAN'");
            	rs = stmt.executeQuery();
            	rs.next();
            	int id_undecided_std = rs.getInt(1);
            	//System.out.println("id_undecided_std="+id_undecided_std);
            	//get ID inputan terakhir
            	stmt = con.prepareStatement("select ID from STANDARD_ISI_TABLE order by ID desc limit 1");
            	rs = stmt.executeQuery();
            	rs.next();
            	id_std_isi_baru = rs.getInt(1);
            	//System.out.println("id_std_isi_baru="+id_std_isi_baru);
            	if(mode_value_baru_revisi_kdpst.equalsIgnoreCase("baru")) {
					//copy utk std baru
					//bedanya STANDARD_ISI_TABLE.ID_STD dikosongin karena buat std baru
					//int i = uf.insertNuStandarIsi(pihak_terkait,null , isi, doc_terkait, rasionale);
					//standar baru jdai belum ditentukan
					updated = ur.ubahUsulanJadiStandar(id_undecided_std,""+id_std_isi_baru,rasionale,isi,unit_period,lama_per_period,periode_awal,thsms1,target_unit1,thsms2,target_unit1,thsms3,target_unit1,thsms4,target_unit1,thsms5,target_unit1,thsms6,target_unit1,tkn_param,tkn_doc,versi,pihak,tkn_pengawas,norut,tkn_indikator,cakupan_std,tipe_proses_pengawasan);
				}
				else if(mode_value_baru_revisi_kdpst.equalsIgnoreCase("revisi")) {
					//copy up revisi standardnya non aktif dulu
					//versi = ""+(Integer.parseInt(versi)+1);
					//updated = ur.ubahUsulanJadiStandar(id_undecided_std,""+id_std_isi_baru,rasionale,isi,unit_period,lama_per_period,periode_awal,thsms1,target_unit1,thsms2,target_unit1,thsms3,target_unit1,thsms4,target_unit1,thsms5,target_unit1,thsms6,target_unit1,tkn_param,tkn_doc,versi,pihak,tkn_pengawas,norut,tkn_indikator,cakupan_std,tipe_proses_pengawasan);
					/*
					 * DEPRECATED : sudah langsung diupdate diatas
					 */
				}
				else {
					//copy untuk prodi
					/*
					 * DEPRECATED : sudah langsung diupdate diatas
					 */
					//updated = ur.ubahUsulanJadiStandar(mode_value_baru_revisi_kdpst,id_undecided_std,""+id_std_isi_baru,rasionale,isi,unit_period,lama_per_period,periode_awal,thsms1,target_unit1,thsms2,target_unit1,thsms3,target_unit1,thsms4,target_unit1,thsms5,target_unit1,thsms6,target_unit1,tkn_param,tkn_doc,versi,pihak,tkn_pengawas,norut,tkn_indikator,cakupan_std,tipe_proses_pengawasan);
					
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
    	return id_std_isi_baru;
    }
    
    public int editStandarMutu(int id_std,String id_versi,String rasionale_std,String pihak_resposible,String def_std,String ref_std, String tkn_job_rumus, String tkn_job_cek, String tkn_job_stuju, String tkn_job_tetap, String tkn_job_kendali, String tkn_job_lap,String dok_terkait_std) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("UPDATE STANDARD_TIPE_VERSION SET TKN_JAB_PERUMUS=?,TKN_JAB_PERIKSA=?,TKN_JAB_SETUJU=?,TKN_JAB_PENETAP=?,TKN_JAB_KENDALI=?,TKN_JAB_PETUGAS_LAP=?,RASIONALE=?,PIHAK_TERKAIT_MENCAPAI_STD=?,DEFINISI=?,REFERENSI=?,DOKUMEN_TERKAIT=? WHERE ID_STD=? and ID_VERSI=?");
        	int i = 1;
        	//TKN_JAB_PERUMUS
        	if(Checker.isStringNullOrEmpty(tkn_job_rumus)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, tkn_job_rumus);
        	}
        	//TKN_JAB_PERIKSA
        	if(Checker.isStringNullOrEmpty(tkn_job_cek)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, tkn_job_cek);
        	}
        	//TKN_JAB_SETUJU
        	if(Checker.isStringNullOrEmpty(tkn_job_stuju)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, tkn_job_stuju);
        	}
        	//TKN_JAB_PENETAP
        	if(Checker.isStringNullOrEmpty(tkn_job_tetap)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, tkn_job_tetap);
        	}
        	//TKN_JAB_KENDALI
        	if(Checker.isStringNullOrEmpty(tkn_job_kendali)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, tkn_job_kendali);
        	}
        	//TKN_JAB_PETUGAS_LAP
        	if(Checker.isStringNullOrEmpty(tkn_job_lap)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, tkn_job_lap);
        	}
        	//RASIONALE
        	if(Checker.isStringNullOrEmpty(rasionale_std)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, rasionale_std);
        	}
        	//PIHAK_TERKAIT_MENCAPAI_STD
        	if(Checker.isStringNullOrEmpty(pihak_resposible)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, pihak_resposible);
        	}
        	//DEFINISI
        	if(Checker.isStringNullOrEmpty(def_std)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, def_std);
        	}
        	//REFERENSI
        	if(Checker.isStringNullOrEmpty(ref_std)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, ref_std);
        	}
        	//DOKUMEN_TERKAIT=?
        	if(Checker.isStringNullOrEmpty(dok_terkait_std)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, dok_terkait_std);
        	}
        	stmt.setInt(i++, id_std);
        	stmt.setInt(i++, Integer.parseInt(id_versi));
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
    
    public int setPihakTerkaitDraftMenjadiFinal(String id_std, String id_versi) {
    	int updated=0;
    	try {
    		Vector v = null;
    		ListIterator li = null;
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt=con.prepareStatement("select TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI from STANDARD_MANUAL_PERENCANAAN_UMUM where ID_STD="+id_std+" and TGL_TETAP is not null order by VERSI_ID desc limit 1");
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		li.add(rs.getString(1));
        		li.add(rs.getString(2));
        		li.add(rs.getString(3));
        		li.add(rs.getString(4));
        		li.add(rs.getString(5));
        	}
        	
        	if(v!=null) {
        		stmt = con.prepareStatement("update STANDARD_TIPE_VERSION set TKN_JAB_PERUMUS=?,TKN_JAB_PERIKSA=?,TKN_JAB_SETUJU=?,TKN_JAB_PENETAP=?,TKN_JAB_KENDALI=? where ID_STD="+id_std+" and ID_VERSI="+id_versi);
        		li = v.listIterator();
        		int i=1;
        		while(li.hasNext()) {
        			String tmp =(String)li.next();
        			if(Checker.isStringNullOrEmpty(tmp)) {
        				stmt.setNull(i++, java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(i++, tmp.toUpperCase());
        			}
        		}
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
    
    public int toogleAktifasiStdUmum(int id_versi, int id_std) {
    	java.sql.Date now = AskSystem.getTodayDate();
    	String thsms_now =Checker.getThsmsNow();
    	int updated=0;
    	boolean aktif = false;
    	boolean expired = false;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_STA,TGL_END from STANDARD_TIPE_VERSION where ID_STD=? and ID_VERSI=?");
        	stmt.setInt(1, id_std);
        	stmt.setInt(2, id_versi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		java.sql.Date tgl_sta=rs.getDate(1);
        		java.sql.Date tgl_end=rs.getDate(2);
        		if(tgl_sta!=null) {
        			aktif=true;
        		}
        		if(tgl_end!=null) {
        			expired=true;
        		}
        	}
        	if(!expired) {
        		if(!aktif) {
        			//general part: artinya standar ini di berlakukan untuk seluruh prodi (status aktif all prodi!!!)
        			stmt = con.prepareStatement("update STANDARD_TIPE_VERSION set TGL_STA=? where ID_STD=? and ID_VERSI=?");
        			stmt.setDate(1, now);
        			stmt.setInt(2, id_std);
                	stmt.setInt(3, id_versi);
                	updated = stmt.executeUpdate();
                	//update standar isi
                	//yg diaktifkan hanya butir yg aktif saja. non aktif berarti sudah tidak digunakan
                	stmt = con.prepareStatement("update STANDARD_ISI_TABLE set TGL_MULAI_AKTIF=? where ID_STD=? and AKTIF=true");
                	stmt.setDate(1, now);
        			stmt.setInt(2, id_std);
        			updated = updated + stmt.executeUpdate();
        			//standar version tidak perlu diupdate defaultnya aktif
        		}
        		else {
        			int nu_id_versi = id_versi+1;
        			//general part: artinya standar ini di berhentikan untuk seluruh prodi
        			//1. set tggl end
        			stmt = con.prepareStatement("update STANDARD_TIPE_VERSION set TGL_END=? where ID_STD=? and ID_VERSI=?");
        			stmt.setDate(1, now);
        			stmt.setInt(2, id_std);
                	stmt.setInt(3, id_versi);
                	updated = stmt.executeUpdate();
                	//System.out.println("1="+updated);
                	//copy untuk based versi berikutnya (draft baru)
                	stmt = con.prepareStatement("SELECT TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,RASIONALE,PIHAK_TERKAIT_MENCAPAI_STD,DEFINISI,REFERENSI,DOKUMEN_TERKAIT FROM STANDARD_TIPE_VERSION where ID_STD=? and ID_VERSI=?");
                	stmt.setInt(1, id_std);
    				stmt.setInt(2, id_versi);
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
    					//RASIONALE
    					String rasionale = rs.getString(i++);
    					//PIHAK_TERKAIT_MENCAPAI_STD
    					String pihak = rs.getString(i++);
    					//DEFINISI
    					String definisi = rs.getString(i++);
    					//REFERENSI
    					String referensi = rs.getString(i++);
    					//DOKUMEN_TERKAIT
    					String dokumen = rs.getString(i++);
    					tmp = tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendali+"~"+tkn_jab_lap+"~"+rasionale+"~"+pihak+"~"+definisi+"~"+referensi+"~"+dokumen;
    					tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
    				}
    				else {
    					tmp = "null~null~null~null~null~null~null~null~null~null~null";
    				}
                	//create versi berikutnya (draft baru)
    				StringTokenizer st = new StringTokenizer(tmp,"~");
                	int i=1;
    				stmt = con.prepareStatement("insert into STANDARD_TIPE_VERSION(ID_STD,ID_VERSI,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,RASIONALE,PIHAK_TERKAIT_MENCAPAI_STD,DEFINISI,REFERENSI,DOKUMEN_TERKAIT)values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                	stmt.setInt(i++, id_std);
    				stmt.setInt(i++, nu_id_versi);
    				while(st.hasMoreTokens()) {
    					String temp = st.nextToken();
    					if(Checker.isStringNullOrEmpty(temp)) {
    						stmt.setNull(i++, java.sql.Types.VARCHAR);
    					}
    					else {
    						stmt.setString(i++, temp);
    					}
    				}
    				updated = updated+stmt.executeUpdate();
    				//System.out.println("2="+updated);
    				//BAGIAN URUSAN STANDAR ISI
    				//a.copy std isi sesuai dgn versi yg lama baik yg kdpst null (general) atau yg spesifik dan yg aktif saja
    				//stmt = con.prepareStatement("SELECT A.ID,A.PERNYATAAN_STD,A.NO_BUTIR,A.KDPST,A.RASIONALE,A.SCOPE,A.TIPE_PROSES_PENGAWASAN,ID_MANUAL_PENETAPAN,B.ID_MANUAL_PELAKSANAAN,B.ID_MANUAL_EVALUASI,B.ID_MANUAL_PENGENDALIAN,B.ID_MANUAL_PENINGKATAN,B.TARGET_THSMS_1,B.TARGET_THSMS_2,B.TARGET_THSMS_3,B.TARGET_THSMS_4,B.TARGET_THSMS_5,B.TARGET_THSMS_6,B.PIHAK_TERKAIT,B.DOKUMEN_TERKAIT,B.TKN_INDIKATOR,B.NO_URUT_TAMPIL,B.TARGET_PERIOD_START,B.UNIT_PERIOD_USED,B.LAMA_NOMINAL_PER_PERIOD,B.TARGET_THSMS_1_UNIT,B.TARGET_THSMS_2_UNIT,B.TARGET_THSMS_3_UNIT,B.TARGET_THSMS_4_UNIT,B.TARGET_THSMS_5_UNIT,B.TARGET_THSMS_6_UNIT,B.PIHAK_MONITOR,B.TKN_PARAMETER,B.STRATEGI FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on A.ID=B.ID_STD_ISI where A.ID_STD=? and B.ID_VERSI=? and A.AKTIF=true and B.AKTIF=true and A.KDPST is null");
    				stmt = con.prepareStatement("SELECT A.ID,A.PERNYATAAN_STD,A.NO_BUTIR,A.KDPST,A.RASIONALE,A.SCOPE,A.TIPE_PROSES_PENGAWASAN,ID_MANUAL_PENETAPAN,B.ID_MANUAL_PELAKSANAAN,B.ID_MANUAL_EVALUASI,B.ID_MANUAL_PENGENDALIAN,B.ID_MANUAL_PENINGKATAN,B.TARGET_THSMS_1,B.TARGET_THSMS_2,B.TARGET_THSMS_3,B.TARGET_THSMS_4,B.TARGET_THSMS_5,B.TARGET_THSMS_6,B.PIHAK_TERKAIT,B.DOKUMEN_TERKAIT,B.TKN_INDIKATOR,B.NO_URUT_TAMPIL,B.TARGET_PERIOD_START,B.UNIT_PERIOD_USED,B.LAMA_NOMINAL_PER_PERIOD,B.TARGET_THSMS_1_UNIT,B.TARGET_THSMS_2_UNIT,B.TARGET_THSMS_3_UNIT,B.TARGET_THSMS_4_UNIT,B.TARGET_THSMS_5_UNIT,B.TARGET_THSMS_6_UNIT,B.PIHAK_MONITOR,B.TKN_PARAMETER,B.STRATEGI FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on A.ID=B.ID_STD_ISI where A.ID_STD=? and B.ID_VERSI=? and A.AKTIF=true and B.AKTIF=true");
    				stmt.setInt(1, id_std);
    				stmt.setInt(2,id_versi);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					Vector v = new Vector();
    					ListIterator li = v.listIterator();
    					do {
    						i=1;
    						//id
    						String id = rs.getString(i++);
    						//PERNYATAAN_STD
    						String isi_std = rs.getString(i++);
    						//NO_BUTIR
    						String butir = rs.getString(i++);
    						//KDPST
    						String kdpst = rs.getString(i++);
    						//RASIONALE
    						String rasionale = rs.getString(i++);
    						String scope = rs.getString(i++);
    						//TIPE_PROSES_PENGAWASAN
    						String tipe_awas = rs.getString(i++);
    						//B.ID_MANUAL_PENETAPAN
    						String id_man_tetap = rs.getString(i++);
    						//B.ID_MANUAL_PELAKSANAAN
    						String id_man_do = rs.getString(i++);
    						//B.ID_MANUAL_EVALUASI
    						String id_man_eval = rs.getString(i++);
    						//B.ID_MANUAL_PENGENDALIAN
    						String id_man_kendali = rs.getString(i++);
    						//B.ID_MANUAL_PENINGKATAN
    						String id_man_tingkat = rs.getString(i++);
    						//B.TARGET_THSMS_1
    						String target1 = rs.getString(i++);
    						//B.TARGET_THSMS_2
    						String target2 = rs.getString(i++);
    						//B.TARGET_THSMS_3
    						String target3 = rs.getString(i++);
    						//B.TARGET_THSMS_4
    						String target4 = rs.getString(i++);
    						//B.TARGET_THSMS_5
    						String target5 = rs.getString(i++);
    						//B.TARGET_THSMS_6
    						String target6 = rs.getString(i++);
    						//B.PIHAK_TERKAIT
    						String pihak = rs.getString(i++);
    						//B.DOKUMEN_TERKAIT
    						String dok = rs.getString(i++);
    						//B.TKN_INDIKATOR
    						String indikator = rs.getString(i++);
    						//B.NO_URUT_TAMPIL
    						String norut_tampil = rs.getString(i++);
    						//B.TARGET_PERIOD_START
    						String sta_period = rs.getString(i++);
    						//B.UNIT_PERIOD_USED
    						String unit_used = rs.getString(i++);
    						//B.LAMA_NOMINAL_PER_PERIOD
    						String lama_per_period = rs.getString(i++);
    						//B.TARGET_THSMS_1_UNIT
    						String thsms1 = rs.getString(i++);
    						//B.TARGET_THSMS_2_UNIT
    						String thsms2 = rs.getString(i++);
    						//B.TARGET_THSMS_3_UNIT
    						String thsms3 = rs.getString(i++);
    						//B.TARGET_THSMS_4_UNIT
    						String thsms4 = rs.getString(i++);
    						//B.TARGET_THSMS_5_UNIT
    						String thsms5 = rs.getString(i++);
    						//B.TARGET_THSMS_6_UNIT
    						String thsms6 = rs.getString(i++);
    						//B.PIHAK_MONITOR
    						String petugas_lap = rs.getString(i++);
    						//B.TKN_PARAMETER
    						String param = rs.getString(i++);
    						//B.STRATEGI
    						String strategi = rs.getString(i++);
    						
    						tmp = id+"~"+isi_std+"~"+kdpst+"~"+rasionale+"~"+scope+"~"+tipe_awas+"~"+id_man_tetap+"~"+id_man_do+"~"+id_man_eval+"~"+id_man_kendali+"~"+id_man_tingkat+"~"+target1+"~"+target2+"~"+target3+"~"+target4+"~"+target5+"~"+target6+"~"+pihak+"~"+dok+"~"+indikator+"~"+norut_tampil+"~"+sta_period+"~"+unit_used+"~"+lama_per_period+"~"+thsms1+"~"+thsms2+"~"+thsms3+"~"+thsms4+"~"+thsms5+"~"+thsms6+"~"+petugas_lap+"~"+param+"~"+strategi;
							tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
							li.add(tmp);
    						
    					}
    					while(rs.next());
    					
    					if(v!=null) {
    						//sort berdasarkan id, asalnya berdasarkan isi_std
    						Collections.sort(v);
    						
    						//expired all butir std terdahulu @ standard_version
    						li = v.listIterator();
    						stmt = con.prepareStatement("UPDATE STANDARD_VERSION set AKTIF=false where ID_STD_ISI=?");
    						while(li.hasNext()) {
    							String brs = (String) li.next();
    							st = new StringTokenizer(brs,"~");
    							//id
        						String id = st.nextToken();
        						stmt.setInt(1, Integer.parseInt(id));
        						updated=updated+stmt.executeUpdate();
    						}
    						
    						//expired all butir std terdahulu @ standard_isi_table
    						li = v.listIterator();
    						stmt = con.prepareStatement("UPDATE STANDARD_ISI_TABLE set AKTIF=false,TGL_STOP_AKTIF='"+now+"' where ID=?");
    						while(li.hasNext()) {
    							String brs = (String) li.next();
    							st = new StringTokenizer(brs,"~");
    							//id
        						String id = st.nextToken();
        						//PERNYATAAN_STD
        						String isi_std = st.nextToken();
        						//KDPST
        						String kdpst = st.nextToken();
        						//RASIONALE
        						String rasionale = st.nextToken();
        						//SCOPE
        						String scope = st.nextToken();
        						//TIPE_PROSES_PENGAWASAN
        						String tipe_awas = st.nextToken();
        						
        						stmt.setInt(1, Integer.parseInt(id));
        						updated=updated+stmt.executeUpdate();
    						}
    						//System.out.println("3="+updated);
    						//insert biar dengan id baru
    						//default setaip std isi yg diinput = aktif (false artinya kadaluarsa)
    						li = v.listIterator();
    						stmt = con.prepareStatement("INSERT INTO STANDARD_ISI_TABLE(ID_STD,PERNYATAAN_STD,NO_BUTIR,KDPST,RASIONALE,SCOPE,TIPE_PROSES_PENGAWASAN)values(?,?,?,?,?,?,?)");
    						while(li.hasNext()) {
    							String brs = (String) li.next();
    							//System.out.println("brs="+brs);
    							st = new StringTokenizer(brs,"~");
    							//id
        						String id = st.nextToken();
        						//PERNYATAAN_STD
        						String isi_std = st.nextToken();
        						//KDPST
        						String kdpst = st.nextToken();
        						//RASIONALE
        						String rasionale = st.nextToken();
        						//SCOPE
        						String scope = st.nextToken();
        						//TIPE_PROSES_PENGAWASAN
        						String tipe_awas = st.nextToken();
        						//id_man_tetap
        						String id_man_tetap = st.nextToken();
        						//id_man_do
        						String id_man_do = st.nextToken();
        						//id_man_eval
        						String id_man_eval = st.nextToken();
        						//id_man_kendali
        						String id_man_kendali = st.nextToken();
        						//id_man_tingkat
        						String id_man_tingkat = st.nextToken();
        						//target1
        						String target1 = st.nextToken();
        						//target2
        						String target2 = st.nextToken();
        						//target3
        						String target3 = st.nextToken();
        						//target4
        						String target4 = st.nextToken();
        						//target5
        						String target5 = st.nextToken();
        						//target6
        						String target6 = st.nextToken();
        						//pihak
        						String pihak = st.nextToken();
        						//dok
        						String dok = st.nextToken();
        						//indikator
        						String indikator = st.nextToken();
        						//norut_tampil
        						String norut_tampil = st.nextToken();
        						//sta_period
        						String sta_period = st.nextToken();
        						//unit_used
        						String unit_used = st.nextToken();
        						//lama_per_period
        						String lama_per_period = st.nextToken();
        						//thsms1
        						String thsms1 = st.nextToken();
        						//thsms2
        						String thsms2 = st.nextToken();
        						//thsms3
        						String thsms3 = st.nextToken();
        						//thsms4
        						String thsms4 = st.nextToken();
        						//thsms5
        						String thsms5 = st.nextToken();
        						//thsms6
        						String thsms6 = st.nextToken();
        						//petugas_lap
        						String petugas_lap = st.nextToken();
        						//param
        						String param = st.nextToken();
        						//strategi;
        						String strategi = st.nextToken();
        						
        						//ID_STD
        						stmt.setInt(1, id_std);
        						//PERNYATAAN_STD
        						stmt.setString(2, isi_std);
        						//NO_BUTIR
        						//if(Checker.isStringNullOrEmpty(butir)) {
        							stmt.setNull(3, java.sql.Types.INTEGER);
        						//}
        						//else {
        						//	stmt.setInt(3, Integer.parseInt(butir));
        						//}	
        						//KDPST
        						if(Checker.isStringNullOrEmpty(kdpst)) {
        							stmt.setNull(4, java.sql.Types.VARCHAR);
        						}
        						else {
        							stmt.setString(4, kdpst);
        						}
        						//RASIONALE
        						if(Checker.isStringNullOrEmpty(rasionale)) {
        							stmt.setNull(5, java.sql.Types.VARCHAR);
        						}
        						else {
        							stmt.setString(5, rasionale);
        						}
        						//SCOPE
        						if(Checker.isStringNullOrEmpty(scope)) {
        							stmt.setNull(6, java.sql.Types.VARCHAR);
        						}
        						else {
        							stmt.setString(6, scope);
        						}
        						//TIPE_PROSES_PENGAWASAN)
        						if(Checker.isStringNullOrEmpty(tipe_awas)) {
        							stmt.setNull(7, java.sql.Types.VARCHAR);
        						}
        						else {
        							stmt.setString(7, tipe_awas);
        						}
        						updated=updated+stmt.executeUpdate();
    						}
    						//System.out.println("4="+updated);
    						//get id std isi yg baru
    						li = v.listIterator();
    						stmt = con.prepareStatement("select ID from STANDARD_ISI_TABLE where ID_STD=? and PERNYATAAN_STD=? and AKTIF=true");
    						while(li.hasNext()) {
    							String brs = (String) li.next();
    							st = new StringTokenizer(brs,"~");
    							//id
        						String id = st.nextToken();
        						//PERNYATAAN_STD
        						String isi_std = st.nextToken();
        						//KDPST
        						String kdpst = st.nextToken();
        						//RASIONALE
        						String rasionale = st.nextToken();
        						//SCOPE
        						String scope = st.nextToken();
        						//TIPE_PROSES_PENGAWASAN
        						String tipe_awas = st.nextToken();
        						//id_man_tetap
        						String id_man_tetap = st.nextToken();
        						//id_man_do
        						String id_man_do = st.nextToken();
        						//id_man_eval
        						String id_man_eval = st.nextToken();
        						//id_man_kendali
        						String id_man_kendali = st.nextToken();
        						//id_man_tingkat
        						String id_man_tingkat = st.nextToken();
        						//target1
        						String target1 = st.nextToken();
        						//target2
        						String target2 = st.nextToken();
        						//target3
        						String target3 = st.nextToken();
        						//target4
        						String target4 = st.nextToken();
        						//target5
        						String target5 = st.nextToken();
        						//target6
        						String target6 = st.nextToken();
        						//pihak
        						String pihak = st.nextToken();
        						//dok
        						String dok = st.nextToken();
        						//indikator
        						String indikator = st.nextToken();
        						//norut_tampil
        						String norut_tampil = st.nextToken();
        						//sta_period
        						String sta_period = st.nextToken();
        						//unit_used
        						String unit_used = st.nextToken();
        						//lama_per_period
        						String lama_per_period = st.nextToken();
        						//thsms1
        						String thsms1 = st.nextToken();
        						//thsms2
        						String thsms2 = st.nextToken();
        						//thsms3
        						String thsms3 = st.nextToken();
        						//thsms4
        						String thsms4 = st.nextToken();
        						//thsms5
        						String thsms5 = st.nextToken();
        						//thsms6
        						String thsms6 = st.nextToken();
        						//petugas_lap
        						String petugas_lap = st.nextToken();
        						//param
        						String param = st.nextToken();
        						//strategi;
        						String strategi = st.nextToken();
        						
        						stmt.setInt(1,id_std);
        						stmt.setString(2, isi_std);
        						rs = stmt.executeQuery();
        						rs.next();
        						int nu_id_std_isi = rs.getInt(1);
        						li.set(nu_id_std_isi+"~"+isi_std+"~"+kdpst+"~"+rasionale+"~"+scope+"~"+tipe_awas+"~"+id_man_tetap+"~"+id_man_do+"~"+id_man_eval+"~"+id_man_kendali+"~"+id_man_tingkat+"~"+target1+"~"+target2+"~"+target3+"~"+target4+"~"+target5+"~"+target6+"~"+pihak+"~"+dok+"~"+indikator+"~"+norut_tampil+"~"+sta_period+"~"+unit_used+"~"+lama_per_period+"~"+thsms1+"~"+thsms2+"~"+thsms3+"~"+thsms4+"~"+thsms5+"~"+thsms6+"~"+petugas_lap+"~"+param+"~"+strategi);
    						}
    						
    						//insert std isi yg baru
    						li = v.listIterator();
    						stmt = con.prepareStatement("INSERT INTO STANDARD_VERSION(ID_VERSI,ID_STD_ISI,ID_MANUAL_PENETAPAN,ID_MANUAL_PELAKSANAAN,ID_MANUAL_EVALUASI,ID_MANUAL_PENGENDALIAN,ID_MANUAL_PENINGKATAN,TARGET_THSMS_1,TARGET_THSMS_2,TARGET_THSMS_3,TARGET_THSMS_4,TARGET_THSMS_5,TARGET_THSMS_6,PIHAK_TERKAIT,DOKUMEN_TERKAIT,TKN_INDIKATOR,NO_URUT_TAMPIL,TARGET_PERIOD_START,UNIT_PERIOD_USED,LAMA_NOMINAL_PER_PERIOD,TARGET_THSMS_1_UNIT,TARGET_THSMS_2_UNIT,TARGET_THSMS_3_UNIT,TARGET_THSMS_4_UNIT,TARGET_THSMS_5_UNIT,TARGET_THSMS_6_UNIT,PIHAK_MONITOR,TKN_PARAMETER,AKTIF,KDPST,STRATEGI)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"); 
    						while(li.hasNext()) {
    							String brs = (String) li.next();
    							st = new StringTokenizer(brs,"~");
    							//nu_id_std_isi
        						String nu_id_std_isi = st.nextToken();
    							//PERNYATAAN_STD
        						String isi_std = st.nextToken();
        						//KDPST
        						String kdpst = st.nextToken();
        						//RASIONALE
        						String rasionale = st.nextToken();
        						//SCOPE
        						String scope = st.nextToken();
        						//TIPE_PROSES_PENGAWASAN
        						String tipe_awas = st.nextToken();
        						//id_man_tetap
        						String id_man_tetap = st.nextToken();
        						//id_man_do
        						String id_man_do = st.nextToken();
        						//id_man_eval
        						String id_man_eval = st.nextToken();
        						//id_man_kendali
        						String id_man_kendali = st.nextToken();
        						//id_man_tingkat
        						String id_man_tingkat = st.nextToken();
        						//target1
        						String target1 = st.nextToken();
        						//target2
        						String target2 = st.nextToken();
        						//target3
        						String target3 = st.nextToken();
        						//target4
        						String target4 = st.nextToken();
        						//target5
        						String target5 = st.nextToken();
        						//target6
        						String target6 = st.nextToken();
        						//pihak
        						String pihak = st.nextToken();
        						//dok
        						String dok = st.nextToken();
        						//indikator
        						String indikator = st.nextToken();
        						//norut_tampil
        						String norut_tampil = st.nextToken();
        						//sta_period
        						String sta_period = st.nextToken();
        						sta_period = thsms_now;
        						//unit_used
        						String unit_used = st.nextToken();
        						if(!Checker.isStringNullOrEmpty(unit_used)) {
        							if(unit_used.equalsIgnoreCase("thn")) {
        								if(!Checker.isStringNullOrEmpty(sta_period)) {
        									sta_period = sta_period.substring(0, 4);	
        								}
        							}
        						}
        						//lama_per_period
        						String lama_per_period = st.nextToken();
        						//thsms1
        						String unit1 = st.nextToken();
        						//thsms2
        						String unit2 = st.nextToken();
        						//thsms3
        						String unit3 = st.nextToken();
        						//thsms4
        						String unit4 = st.nextToken();
        						//thsms5
        						String unit5 = st.nextToken();
        						//thsms6
        						String unit6 = st.nextToken();
        						//petugas_lap
        						String petugas_lap = st.nextToken();
        						//param
        						String param = st.nextToken();
        						//strategi;
        						String strategi = st.nextToken();
        						i=1;
        						//ID_VERSI
        						stmt.setInt(i++, nu_id_versi);
        						//ID_STD_ISI
        						stmt.setInt(i++, Integer.parseInt(nu_id_std_isi));
        						//ID_MANUAL_PENETAPAN
        						if(Checker.isStringNullOrEmpty(id_man_tetap)) {
        							stmt.setNull(i++, java.sql.Types.DECIMAL);
        						}
        						else {
        							stmt.setInt(i++,Integer.parseInt(id_man_tetap));
        						}
        						//ID_MANUAL_PELAKSANAAN
        						if(Checker.isStringNullOrEmpty(id_man_do)) {
        							stmt.setNull(i++, java.sql.Types.DECIMAL);
        						}
        						else {
        							stmt.setInt(i++,Integer.parseInt(id_man_do));
        						}
        						//ID_MANUAL_EVALUASI
        						if(Checker.isStringNullOrEmpty(id_man_eval)) {
        							stmt.setNull(i++, java.sql.Types.DECIMAL);
        						}
        						else {
        							stmt.setInt(i++,Integer.parseInt(id_man_eval));
        						}
        						//ID_MANUAL_PENGENDALIAN
        						if(Checker.isStringNullOrEmpty(id_man_kendali)) {
        							stmt.setNull(i++, java.sql.Types.DECIMAL);
        						}
        						else {
        							stmt.setInt(i++,Integer.parseInt(id_man_kendali));
        						}
        						//ID_MANUAL_PENINGKATAN
        						if(Checker.isStringNullOrEmpty(id_man_tingkat)) {
        							stmt.setNull(i++, java.sql.Types.DECIMAL);
        						}
        						else {
        							stmt.setInt(i++,Integer.parseInt(id_man_tingkat));
        						}
        						//TARGET_THSMS_1
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        						//TARGET_THSMS_2
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        						//TARGET_THSMS_3
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        						//TARGET_THSMS_4
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        						//TARGET_THSMS_5
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        						//TARGET_THSMS_6
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        						//PIHAK_TERKAIT
        						if(Checker.isStringNullOrEmpty(pihak)) {
        							stmt.setNull(i++, java.sql.Types.VARCHAR);
        						}
        						else {
        							stmt.setString(i++,pihak);
        						}
        						//DOKUMEN_TERKAIT
        						if(Checker.isStringNullOrEmpty(dok)) {
        							stmt.setNull(i++, java.sql.Types.VARCHAR);
        						}
        						else {
        							stmt.setString(i++,dok);
        						}
        						//TKN_INDIKATOR
        						if(Checker.isStringNullOrEmpty(indikator)) {
        							stmt.setNull(i++, java.sql.Types.VARCHAR);
        						}
        						else {
        							stmt.setString(i++,indikator);
        						}
        						//NO_URUT_TAMPIL
        						//if(Checker.isStringNullOrEmpty(pihak)) {
        							//stmt.setNull(i++, java.sql.Types.VARCHAR);
        						//}
        						//else {
        							stmt.setInt(i++,0);
        						//}
        						//TARGET_PERIOD_START
        						if(Checker.isStringNullOrEmpty(sta_period)) {
        							stmt.setNull(i++, java.sql.Types.VARCHAR);
        						}
        						else {
        							stmt.setString(i++,sta_period);
        						}
        						//UNIT_PERIOD_USED
        						if(Checker.isStringNullOrEmpty(unit_used)) {
        							stmt.setNull(i++, java.sql.Types.VARCHAR);
        						}
        						else {
        							stmt.setString(i++,unit_used);
        						}
        						//LAMA_NOMINAL_PER_PERIOD
        						if(Checker.isStringNullOrEmpty(lama_per_period)) {
        							stmt.setNull(i++, java.sql.Types.INTEGER);
        						}
        						else {
        							stmt.setInt(i++,Integer.parseInt(lama_per_period));
        						}
        						//TARGET_THSMS_1_UNIT
        						if(Checker.isStringNullOrEmpty(unit1)) {
        							stmt.setNull(i++, java.sql.Types.VARCHAR);
        						}
        						else {
        							stmt.setString(i++,unit1);
        						}
        						//TARGET_THSMS_2_UNIT
        						if(Checker.isStringNullOrEmpty(unit2)) {
        							stmt.setNull(i++, java.sql.Types.VARCHAR);
        						}
        						else {
        							stmt.setString(i++,unit2);
        						}
        						//TARGET_THSMS_3_UNIT
        						if(Checker.isStringNullOrEmpty(unit3)) {
        							stmt.setNull(i++, java.sql.Types.VARCHAR);
        						}
        						else {
        							stmt.setString(i++,unit3);
        						}
        						//TARGET_THSMS_4_UNIT
        						if(Checker.isStringNullOrEmpty(unit4)) {
        							stmt.setNull(i++, java.sql.Types.VARCHAR);
        						}
        						else {
        							stmt.setString(i++,unit4);
        						}
        						//TARGET_THSMS_5_UNIT
        						if(Checker.isStringNullOrEmpty(unit5)) {
        							stmt.setNull(i++, java.sql.Types.VARCHAR);
        						}
        						else {
        							stmt.setString(i++,unit5);
        						}
        						//TARGET_THSMS_6_UNIT
        						if(Checker.isStringNullOrEmpty(unit6)) {
        							stmt.setNull(i++, java.sql.Types.VARCHAR);
        						}
        						else {
        							stmt.setString(i++,unit6);
        						}
        						//PIHAK_MONITOR
        						if(Checker.isStringNullOrEmpty(petugas_lap)) {
        							stmt.setNull(i++, java.sql.Types.VARCHAR);
        						}
        						else {
        							stmt.setString(i++,petugas_lap);
        						}
        						//TKN_PARAMETER
        						if(Checker.isStringNullOrEmpty(param)) {
        							stmt.setNull(i++, java.sql.Types.VARCHAR);
        						}
        						else {
        							stmt.setString(i++,param);
        						}
        						//AKTIF
        						stmt.setBoolean(i++, true);
        						//KDPST
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        						//STRATEGI
        						if(Checker.isStringNullOrEmpty(strategi)) {
        							stmt.setNull(i++, java.sql.Types.VARCHAR);
        						}
        						else {
        							stmt.setString(i++,strategi);
        						}
        						updated = updated+stmt.executeUpdate();
    						}
    						//System.out.println("5="+updated);
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

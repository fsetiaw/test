package beans.dbase.mhs.data_pribadi;

import beans.dbase.mhs.SearchDbInfoMhs;
import beans.tools.Checker;

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
 * Session Bean implementation class SearchDbInfoMhsDataPri
 */
@Stateless
@LocalBean
public class SearchDbInfoMhsDataPri extends SearchDbInfoMhs {
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
     * @see SearchDbInfoMhs#SearchDbInfoMhs()
     */
    public SearchDbInfoMhsDataPri() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDbInfoMhs#SearchDbInfoMhs(String)
     */
    public SearchDbInfoMhsDataPri(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDbInfoMhs#SearchDbInfoMhs(Connection)
     */
    public SearchDbInfoMhsDataPri(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public String getDataPribadi(String npmhs) {
    	//Vector v = null;
    	StringTokenizer st = null;
    	String info=null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select NMMHSMSMHS,TPLHRMSMHS,TGLHRMSMHS,KDJEKMSMHS,NISNMSMHS,KEWARGANEGARAAN,NIKTPMSMHS,NOSIMMSMHS,PASPORMSMHS,MALAIKAT,STTUSMSMHS,EMAILMSMHS,NOHPEMSMHS,ALMRMMSMHS,NRTRMMSMHS,NRWRMMSMHS,PROVRMMSMHS,PROVRMIDWIL,KOTRMMSMHS,KOTRMIDWIL,KECRMMSMHS,KECRMIDWIL,KELRMMSMHS,DUSUNMSMHS,POSRMMSMHS,TELRMMSMHS,NEGLHMSMHS,AGAMAMSMHS from CIVITAS inner join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS where CIVITAS.NPMHSMSMHS=?");
			stmt.setString(1, npmhs);
			rs = stmt.executeQuery();
			if(rs.next()) {
				info = new String();
				String nmmhs = ""+rs.getString("NMMHSMSMHS");
				String tplhr = ""+rs.getString("TPLHRMSMHS");
				String tglhr = ""+rs.getDate("TGLHRMSMHS");
				String kdjek = ""+rs.getString("KDJEKMSMHS");
				String nisn = ""+rs.getString("NISNMSMHS");
				String warganegara = ""+rs.getString("KEWARGANEGARAAN");
				String niktp = ""+rs.getString("NIKTPMSMHS");
				String nosim = ""+rs.getString("NOSIMMSMHS");
				String paspo = ""+rs.getString("PASPORMSMHS");
				String angel = ""+rs.getString("MALAIKAT");
				String sttus = ""+rs.getString("STTUSMSMHS");
				String email = ""+rs.getString("EMAILMSMHS");
				String nohpe = ""+rs.getString("NOHPEMSMHS");
				String almrm = ""+rs.getString("ALMRMMSMHS");
				String no_rt = ""+rs.getString("NRTRMMSMHS");
				String no_rw = ""+rs.getString("NRWRMMSMHS");
				String prorm = ""+rs.getString("PROVRMMSMHS");
				String proid = ""+rs.getString("PROVRMIDWIL");
				String kotrm = ""+rs.getString("KOTRMMSMHS");
				String kotid = ""+rs.getString("KOTRMIDWIL");
				String kecrm = ""+rs.getString("KECRMMSMHS");
				String kecid = ""+rs.getString("KECRMIDWIL");
				String keltm = ""+rs.getString("KELRMMSMHS");
				String dusun = ""+rs.getString("DUSUNMSMHS");
				String posrm = ""+rs.getString("POSRMMSMHS");
				String telrm = ""+rs.getString("TELRMMSMHS");
				String nglhr = ""+rs.getString("NEGLHMSMHS");
				String agama = ""+rs.getString("AGAMAMSMHS");
				//String nidk = ""+rs.getString("NIDK");
				//String nup = ""+rs.getString("NUP");
				
				info = nmmhs+"`"+tplhr+"`"+tglhr+"`"+kdjek+"`"+nisn+"`"+warganegara+"`"+niktp+"`"+nosim+"`"+paspo+"`"+angel+"`"+sttus+"`"+email+"`"+nohpe+"`"+almrm+"`"+no_rt+"`"+no_rw+"`"+prorm+"`"+proid+"`"+kotrm+"`"+kotid+"`"+kecrm+"`"+kecid+"`"+keltm+"`"+dusun+"`"+posrm+"`"+telrm+"`"+nglhr+"`"+agama;
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
    	return info;
    }
    
    public String getDataOrtuDanWali(String npmhs) {
    	//Vector v = null;
    	StringTokenizer st = null;
    	String info=null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select NAMA_AYAH,TGLHR_AYAH,TPLHR_AYAH,LULUSAN_AYAH,NOHAPE_AYAH,KERJA_AYAH,GAJI_AYAH,NIK_AYAH,KANDUNG_AYAH,NAMA_IBU,TGLHR_IBU,TPLHR_IBU,LULUSAN_IBU,NOHAPE_IBU,KERJA_IBU,GAJI_IBU,NIK_IBU,KANDUNG_IBU,NAMA_WALI,TGLHR_WALI,TPLHR_WALI,LULUSAN_WALI,NOHAPE_WALI,KERJA_WALI,GAJI_WALI,NIK_WALI,HUBUNGAN_WALI,NAMA_DARURAT1,NOHAPE_DARURAT1,HUBUNGAN_DARURAT1,NAMA_DARURAT2,NOHAPE_DARURAT2,HUBUNGAN_DARURAT2 from CIVITAS where NPMHSMSMHS=?");
			stmt.setString(1, npmhs);
			rs = stmt.executeQuery();
			if(rs.next()) {
				info = new String();
				
				String nmmay = ""+rs.getString("NAMA_AYAH");
				String tglay = ""+rs.getString("TGLHR_AYAH");
				String tplay = ""+rs.getString("TPLHR_AYAH");
				String llsay = ""+rs.getString("LULUSAN_AYAH");
				String hpeay = ""+rs.getString("NOHAPE_AYAH");
				String jobay = ""+rs.getString("KERJA_AYAH");
				String payay = ""+rs.getString("GAJI_AYAH");
				String nikay = ""+rs.getString("NIK_AYAH");
				String rilay = ""+rs.getBoolean("KANDUNG_AYAH");
				
				String nmmbu = ""+rs.getString("NAMA_IBU");
				String tglbu = ""+rs.getString("TGLHR_IBU");
				String tplbu = ""+rs.getString("TPLHR_IBU");
				String llsbu = ""+rs.getString("LULUSAN_IBU");
				String hpebu = ""+rs.getString("NOHAPE_IBU");
				String jobbu = ""+rs.getString("KERJA_IBU");
				String paybu = ""+rs.getString("GAJI_IBU");
				String nikbu = ""+rs.getString("NIK_IBU");
				String rilbu = ""+rs.getBoolean("KANDUNG_IBU");
			////,,,,,,,,,,,,NAMA_DARURAT2,NOHAPE_DARURAT2,HUBUNGAN_DARURAT2
				String nmmwa = ""+rs.getString("NAMA_WALI");
				String tglwa = ""+rs.getString("TGLHR_WALI");
				String tplwa = ""+rs.getString("TPLHR_WALI");
				String llswa = ""+rs.getString("LULUSAN_WALI");
				String hpewa = ""+rs.getString("NOHAPE_WALI");
				String jobwa = ""+rs.getString("KERJA_WALI");
				String paywa = ""+rs.getString("GAJI_WALI");
				String nikwa = ""+rs.getString("NIK_WALI");
				String hubwa = ""+rs.getString("HUBUNGAN_WALI");
				
				String nmer1 = ""+rs.getString("NAMA_DARURAT1");
				String hper1 = ""+rs.getString("NOHAPE_DARURAT1");
				String hber1 = ""+rs.getString("HUBUNGAN_DARURAT1");
				
				String nmer2 = ""+rs.getString("NAMA_DARURAT2");
				String hper2 = ""+rs.getString("NOHAPE_DARURAT2");
				String hber2 = ""+rs.getString("HUBUNGAN_DARURAT2");
				
				
				info = nmmay+"`"+tglay+"`"+tplay+"`"+llsay+"`"+hpeay+"`"+jobay+"`"+payay+"`"+nikay+"`"+rilay+"`"+nmmbu+"`"+tglbu+"`"+tplbu+"`"+llsbu+"`"+hpebu+"`"+jobbu+"`"+paybu+"`"+nikbu+"`"+rilbu+"`"+nmmwa+"`"+tglwa+"`"+tplwa+"`"+llswa+"`"+hpewa+"`"+jobwa+"`"+paywa+"`"+nikwa+"`"+hubwa+"`"+nmer1+"`"+hper1+"`"+hber1+"`"+nmer2+"`"+hper2+"`"+hber2;
				info = Checker.pnn_v1(info);
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
    	return info;
    }
    
    public String getDataKemahasiswaan(String npmhs) {
    	//Vector v = null;
    	StringTokenizer st = null;
    	String info=null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select NIMHSMSMHS,SHIFTMSMHS,TAHUNMSMHS,SMAWLMSMHS,BTSTUMSMHS,ASSMAMSMHS,STPIDMSMHS,NOPRMMSMHS,NOKP1MSMHS,NOKP2MSMHS,NOKP3MSMHS,NOKP4MSMHS,KRKLMMSMHS,NPM_PA,NMM_PA,SKSDIMSMHS,ASNIMMSMHS,ASPTIMSMHS,ASJENMSMHS,ASPSTMSMHS,ASPTI_UNLISTED,ASPTI_KDPTI,ASPST_KDPST from CIVITAS inner join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS where CIVITAS.NPMHSMSMHS=?");
			stmt.setString(1, npmhs);
			rs = stmt.executeQuery();
			if(rs.next()) {
				info = new String();
				
				String nimhs = ""+rs.getString("NIMHSMSMHS");
				if(Checker.isStringNullOrEmpty(nimhs)) {
					nimhs="null";
				}
				String shift = ""+rs.getString("SHIFTMSMHS");
				String tahun = ""+rs.getString("TAHUNMSMHS");
				String smawl = ""+rs.getString("SMAWLMSMHS");
				String btstu = ""+rs.getString("BTSTUMSMHS");
				String assma = ""+rs.getString("ASSMAMSMHS");
				String stpid = ""+rs.getString("STPIDMSMHS");
				String noprm = ""+rs.getBoolean("NOPRMMSMHS");
				String nokp1 = ""+rs.getString("NOKP1MSMHS");
				String nokp2 = ""+rs.getString("NOKP2MSMHS");
				String nokp3 = ""+rs.getString("NOKP3MSMHS");
				String nokp4 = ""+rs.getString("NOKP4MSMHS");
				
				String krklm = ""+rs.getString("KRKLMMSMHS");
				String npm_pa = ""+rs.getString("NPM_PA");
				String nmm_pa = ""+rs.getString("NMM_PA");
				if(Checker.isStringNullOrEmpty(nmm_pa)) {
					nmm_pa="null";
				}
				String sksdi = ""+rs.getInt("SKSDIMSMHS");
				String asnim = ""+rs.getString("ASNIMMSMHS");
				String aspti = ""+rs.getString("ASPTIMSMHS");
				String asjen = ""+rs.getString("ASJENMSMHS");
				String aspst = ""+rs.getString("ASPSTMSMHS");
				String aspti_unlisted = ""+rs.getString("ASPTI_UNLISTED");
				String aspti_kdpti = ""+rs.getString("ASPTI_KDPTI");
				String aspst_kdpst = ""+rs.getString("ASPST_KDPST");
				if(Checker.isStringNullOrEmpty(aspst_kdpst)) {
					aspst_kdpst = "null";
				}
				info = nimhs+"`"+shift+"`"+tahun+"`"+smawl+"`"+btstu+"`"+assma+"`"+stpid+"`"+noprm+"`"+nokp1+"`"+nokp2+"`"+nokp3+"`"+nokp4+"`"+krklm+"`"+npm_pa+"`"+nmm_pa+"`"+sksdi+"`"+asnim+"`"+aspti+"`"+asjen+"`"+aspst+"`"+aspti_unlisted+"`"+aspti_kdpti+"`"+aspst_kdpst;
				info = Checker.pnn_v1(info);
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
    	return info;
    }
    
    
    public String getDataAsDosen(String npmhs) {
    	//Vector v = null;
    	StringTokenizer st = null;
    	String info=null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from EXT_CIVITAS_DATA_DOSEN where NPMHS=?");
			stmt.setString(1, npmhs);
			rs = stmt.executeQuery();
			if(rs.next()) {
				info = new String();
				
				String kdpst = ""+rs.getString("KDPST");
				//String npmhs = ""+rs.getString("NPMHS");
				String local = ""+rs.getString("NODOS_LOCAL");
				String gelar_depan = ""+rs.getString("GELAR_DEPAN");
				String gelar_belakang = ""+rs.getString("GELAR_BELAKANG");
				String nidn = ""+rs.getString("NIDNN");
				String tipe_id = ""+rs.getString("TIPE_ID");
				String no_id = ""+rs.getString("NOMOR_ID");
				String status = ""+rs.getString("STATUS");
				String pt_s1 = ""+rs.getString("PT_S1");
				String jur_s1 = ""+rs.getString("JURUSAN_S1");
				String kdpst_s1 = ""+rs.getString("KDPST_S1");
				String gelar_s1 = ""+rs.getString("GELAR_S1");
				String bidil_s1 = ""+rs.getString("TKN_BIDANG_KEAHLIAN_S1");
				String noija_s1 = ""+rs.getString("NOIJA_S1");
				String tglls_s1 = ""+rs.getDate("TGLLS_S1");
				String file_ija_s1 = ""+rs.getString("FILE_IJA_S1");
				String judul_s1 = ""+rs.getString("JUDUL_TA_S1");
				String pt_s2 = ""+rs.getString("PT_S2");
				String jur_s2 = ""+rs.getString("JURUSAN_S2");
				String kdpst_s2 = ""+rs.getString("KDPST_S2");
				String gelar_s2 = ""+rs.getString("GELAR_S2");
				String bidil_s2 = ""+rs.getString("TKN_BIDANG_KEAHLIAN_S2");
				String noija_s2 = ""+rs.getString("NOIJA_S2");
				String tglls_s2 = ""+rs.getDate("TGLLS_S2");
				String file_ija_s2 = ""+rs.getString("FILE_IJA_S2");
				String judul_s2 = ""+rs.getString("JUDUL_TA_S2");
				String pt_s3 = ""+rs.getString("PT_S3");
				String jur_s3 = ""+rs.getString("JURUSAN_S3");
				String kdpst_s3 = ""+rs.getString("KDPST_S3");
				String gelar_s3 = ""+rs.getString("GELAR_S3");
				String bidil_s3 = ""+rs.getString("TKN_BIDANG_KEAHLIAN_S3");
				String noija_s3 = ""+rs.getString("NOIJA_S3");
				String tglls_s3 = ""+rs.getDate("TGLLS_S3");
				String file_ija_s3 = ""+rs.getString("FILE_IJA_S3");
				String judul_s3 = ""+rs.getString("JUDUL_TA_S3");
				String pt_gb = ""+rs.getString("PT_PROF");
				String jur_gb = ""+rs.getString("JURUSAN_PROF");
				String kdpst_gb = ""+rs.getString("KDPST_PROF");
				String gelar_gb = ""+rs.getString("GELAR_PROF");
				String bidil_gb = ""+rs.getString("TKN_BIDANG_KEAHLIAN_PROF");
				String noija_gb = ""+rs.getString("NOIJA_PROF");
				String tglls_gb = ""+rs.getDate("TGLLS_PROF");
				String file_ija_gb = ""+rs.getString("FILE_IJA_PROF");
				String judul_gb = ""+rs.getString("JUDUL_TA_PROF");
				String tot_kum = ""+rs.getInt("TOTAL_KUM");
				String jja_dikti = ""+rs.getString("JABATAN_AKADEMIK_DIKTI");
				String jja_local = ""+rs.getString("JABATAN_AKADEMIK_LOCAL");
				String jab_struk = ""+rs.getString("JABATAN_STRUKTURAL");
				String tipe_ika = ""+rs.getString("IKATAN_KERJA_DOSEN");
				String tgl_in = ""+rs.getDate("TANGGAL_MULAI_KERJA");
				String tgl_out = ""+rs.getDate("TANGGAL_KELUAR_KERJA");
				String serdos = ""+rs.getString("SERDOS");
				String kdpti_home = ""+rs.getString("KDPTI_HOMEBASE");
				String kdpst_home = ""+rs.getString("KDPST_HOMEBASE");
				String email_org = ""+rs.getString("EMAIL_INSTITUSI");
				String pangkat_gol = ""+rs.getString("PANGKAT_GOLONGAN");
				String catatan_riwayat = ""+rs.getString("CATATAN_RIWAYAT");
				String ktp_sim_paspo = ""+rs.getString("TIPE_KTP");
				String no_ktp_sim_paspo = ""+rs.getString("NO_KTP");
				String nik = ""+rs.getString("NIK");
				String nip = ""+rs.getString("NIP");
				String niy_nigk = ""+rs.getString("NIY_NIGK");
				String nuptk = ""+rs.getString("NUPTK");
				String nsdmi = ""+rs.getString("NSDMI");
				String nidk = ""+rs.getString("NIDK");
				String nup = ""+rs.getString("NUP");
				
				
				info = kdpst+"`"+npmhs+"`"+local+"`"+gelar_depan+"`"+gelar_belakang+"`"+nidn+"`"+tipe_id+"`"+no_id+"`"+status+"`"+pt_s1+"`"+jur_s1+"`"+kdpst_s1+"`"+gelar_s1+"`"+bidil_s1+"`"+noija_s1+"`"+tglls_s1+"`"+file_ija_s1+"`"+judul_s1+"`"+pt_s2+"`"+jur_s2+"`"+kdpst_s2+"`"+gelar_s2+"`"+bidil_s2+"`"+noija_s2+"`"+tglls_s2+"`"+file_ija_s2+"`"+judul_s2+"`"+pt_s3+"`"+jur_s3+"`"+kdpst_s3+"`"+gelar_s3+"`"+bidil_s3+"`"+noija_s3+"`"+tglls_s3+"`"+file_ija_s3+"`"+judul_s3+"`"+pt_gb+"`"+jur_gb+"`"+kdpst_gb+"`"+gelar_gb+"`"+bidil_gb+"`"+noija_gb+"`"+tglls_gb+"`"+file_ija_gb+"`"+judul_gb+"`"+tot_kum+"`"+jja_dikti+"`"+jja_local+"`"+jab_struk+"`"+tipe_ika+"`"+tgl_in+"`"+tgl_out+"`"+serdos+"`"+kdpti_home+"`"+kdpst_home+"`"+email_org+"`"+pangkat_gol+"`"+catatan_riwayat+"`"+ktp_sim_paspo+"`"+no_ktp_sim_paspo+"`"+nik+"`"+nip+"`"+niy_nigk+"`"+nuptk+"`"+nsdmi+"`"+nidk+"`"+nup;
				info = Checker.pnn_v1(info);
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
    	return info;
    }

}

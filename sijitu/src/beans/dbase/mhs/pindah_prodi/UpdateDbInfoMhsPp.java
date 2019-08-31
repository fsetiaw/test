package beans.dbase.mhs.pindah_prodi;

import beans.dbase.mhs.UpdateDbInfoMhs;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Constant;
import beans.tools.Converter;
import beans.tools.Getter;

import java.io.File;
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

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class UpdateDbInfoMhsPp
 */
@Stateless
@LocalBean
public class UpdateDbInfoMhsPp extends UpdateDbInfoMhs {
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
     * @see UpdateDbInfoMhs#UpdateDbInfoMhs()
     */
    public UpdateDbInfoMhsPp() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDbInfoMhs#UpdateDbInfoMhs(String)
     */
    public UpdateDbInfoMhsPp(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.tknOperatorNickname = getTknOprNickname();
    	//System.out.println("tknOperatorNickname1="+this.tknOperatorNickname);
    	this.petugas = cekApaUsrPetugas();
        // TODO Auto-generated constructor stub
    }
    
    public void updateFolderPath(String npm_lama, String path_folder_lama, String npm_baru, String path_folder_baru) {
    	//String folder_lama = Constant.getVelueFromConstantTable("ROOT_PATH_BUKTI_PYMNT").replace("target_npmhs", npm_lama);
    	//String folder_baru = Constant.getVelueFromConstantTable("ROOT_PATH_BUKTI_PYMNT").replace("target_npmhs", npm_baru);
    	try {
    		File old_dir = new File(path_folder_lama);
            File new_dir = new File(path_folder_baru);
    		if(old_dir.exists()) {
    			if(old_dir.isDirectory()) {
    				if(new_dir.exists()) {
    					//copy files ke folder baru
    					if(new_dir.list().length>0){
    						FileUtils.copyDirectory(old_dir, new_dir);
        				}
    				}
    				else {
    					old_dir.renameTo(new_dir);
    				}
    			} else {
    				new_dir.mkdir();//create nuw directory
    			}
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void updateFolderPathMhsPindahProdi(String npm_lama, String npm_baru) {
    	String root_folder = Constant.getVelueFromConstantTable("ROOT_PATH_FOLDER_MHS");
    	//System.out.println("updateFolderPathMhsPindahProdi="+npm_lama+" vs "+npm_baru);
    	try {
    		//1. update folder pymnt
    		String path_folder_lama = Constant.getVelueFromConstantTable("ROOT_PATH_BUKTI_PYMNT").replace("target_npmhs", npm_lama);
        	String path_folder_baru = Constant.getVelueFromConstantTable("ROOT_PATH_BUKTI_PYMNT").replace("target_npmhs", npm_baru);
        	updateFolderPath(npm_lama, path_folder_lama, npm_baru, path_folder_baru);
        	
    		//2, proses rename foto lama jadi baru
    		String folder_foto_lama = Constant.getVelueFromConstantTable("PATH_FOLDER_POTO_MHS").replace("ROOT_PATH_FOLDER_MHS", root_folder).replace("npmhs", npm_lama);
        	String folder_foto_baru = Constant.getVelueFromConstantTable("PATH_FOLDER_POTO_MHS").replace("ROOT_PATH_FOLDER_MHS", root_folder).replace("npmhs", npm_baru);
        	
    		File old_dir = new File(folder_foto_lama);
            File new_dir = new File(folder_foto_baru);
    		if(old_dir.exists()) {
    			if(old_dir.isDirectory()) {
    				File[] listOfFiles = old_dir.listFiles();
    				for (int i = 0; i < listOfFiles.length; i++) {
    					if (listOfFiles[i].isFile()) {
    				        String old_file_name = listOfFiles[i].getName();
    				        String new_file_name = old_file_name.replace(npm_lama, npm_baru);
    				        File newFileName =new File(new_file_name);
    				        listOfFiles[i].renameTo(newFileName);
    					} 
    				}
    				
    			}
    			
    			//3. setelah di copy baru ubah root foldernya
    			String root_folder_mhs_lama = root_folder+"/"+npm_lama;
            	String root_folder_msh_baru = root_folder+"/"+npm_baru;
                updateFolderPath(npm_lama, root_folder_mhs_lama, npm_baru, root_folder_msh_baru);
    		}	
    		else {
    				//ignore karena folder lama belum ada (no data)
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    public boolean pindahProdiMhsBaruDaftar(Vector v_SearchDbInfoMhs_getDataProfileCivitasAndExtMhs, String kode_prodi_baru) {
    	boolean sukses=false;
    	try {
    		
    		ListIterator li = v_SearchDbInfoMhs_getDataProfileCivitasAndExtMhs.listIterator();
    		String info_civitas = (String)li.next();
    		String info_ext_civitas = (String)li.next();
    		
    		
    		//kdjen = getKdjen(kdpst_tamu);
    		if(!Checker.isStringNullOrEmpty(info_civitas)) {
    			//String info = id+"`"+idobj+"`"+kdpti+"`"+kdjen+"`"+kdpst+"`"+npmhs+"`"+nimhs+"`"+nmmhs+"`"+shift+"`"+tplhr+"`"+tglhr+"`"+kdjek+"`"+tahun+"`"+smawl+"`"+btstu+"`"+assma+"`"+tgmsk+"`"+tglls+"`"+stmhs+"`"+stpid+"`"+sksdi+"`"+asnim+"`"+aspti+"`"+asjen+"`"+aspst+"`"+bistu+"`"+peksb+"`"+nmpek+"`"+ptpek+"`"+pspek+"`"+noprm+"`"+nokp1+"`"+nokp2+"`"+nokp3+"`"+nokp4+"`"+gelom+"`"+nama_ayah+"`"+tglhr_ayah+"`"+tplhr_ayah+"`"+lulus_ayah+"`"+hape_ayah+"`"+kerja_ayah+"`"+gaji_ayah+"`"+nik_ayah+"`"+kandung_ayah+"`"+nama_ibu+"`"+tglhr_ibu+"`"+tplhr_ibu+"`"+lulus_ibu+"`"+hape_ibu+"`"+kerja_ibu+"`"+gaji_ibu+"`"+nik_ibu+"`"+kandung_ibu+"`"+nama_wali+"`"+tglhr_wali+"`"+tplhr_wali+"`"+lulus_wali+"`"+hape_wali+"`"+kerja_wali+"`"+gaji_wali+"`"+nik_wali+"`"+hub_wali+"`"+nama_emg1+"`"+hape_emg1+"`"+hub_emg1+"`"+nama_emg2+"`"+hape_emg2+"`"+hub_emg2+"`"+nisn+"`"+warga+"`"+nonik+"`"+nosim+"`"+paspor+"`"+angel;
    			StringTokenizer st = new StringTokenizer(info_civitas,"`");
    			String init_id = st.nextToken();
        		String init_idobj=st.nextToken();
        		String kdpti=st.nextToken();
        		String old_kdjen=st.nextToken();
        		String old_kdpst=st.nextToken();
        		String old_npmhs=st.nextToken();
        		String old_nimhs=st.nextToken();
        		//System.out.println("init_idobj="+init_idobj);
        		String kode_kmp = Getter.getDomisiliKampus(Integer.parseInt(init_idobj));
        		//System.out.println("kode_kmp="+kode_kmp);
        		//System.out.println("kode_prodi_baru="+kode_prodi_baru);
        		String kdjen = Checker.getKdjen(kode_prodi_baru);
        		String idobj_prodi_baru = Checker.getObjidMhsProdi(kode_prodi_baru, kode_kmp);
    			//String idobj = ""+default_idobj_tamu;//sama default_idobj_tamu
        		String thsms = getThsmsPmb();
        		String npm_prodi_baru = generateNpm(thsms,kode_prodi_baru);
        		//System.out.println("npm_prodi_baru="+npm_prodi_baru);
        		//System.out.println("old_npmhs="+old_npmhs);
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//hapus trnlm dan trakm dan trlsm
        		stmt=con.prepareStatement("delete from TRNLM where NPMHSTRNLM=?");
        		stmt.setString(1, old_npmhs);
        		stmt.executeUpdate();
        		stmt=con.prepareStatement("delete from TRAKM where NPMHSTRAKM=?");
        		stmt.setString(1, old_npmhs);
        		stmt.executeUpdate();
        		stmt=con.prepareStatement("delete from TRLSM where NPMHS=?");
        		stmt.setString(1, old_npmhs);
        		stmt.executeUpdate();
        		stmt = con.prepareStatement("UPDATE CIVITAS SET ID_OBJ=?,KDPTIMSMHS=?,KDJENMSMHS=?,KDPSTMSMHS=?,NPMHSMSMHS=?,NIMHSMSMHS=?,NMMHSMSMHS=?,SHIFTMSMHS=?,TPLHRMSMHS=?,TGLHRMSMHS=?,KDJEKMSMHS=?,TAHUNMSMHS=?,SMAWLMSMHS=?,BTSTUMSMHS=?,ASSMAMSMHS=?,TGMSKMSMHS=?,TGLLSMSMHS=?,STMHSMSMHS=?,STPIDMSMHS=?,SKSDIMSMHS=?,ASNIMMSMHS=?,ASPTIMSMHS=?,ASJENMSMHS=?,ASPSTMSMHS=?,BISTUMSMHS=?,PEKSBMSMHS=?,NMPEKMSMHS=?,PTPEKMSMHS=?,PSPEKMSMHS=?,NOPRMMSMHS=?,NOKP1MSMHS=?,NOKP2MSMHS=?,NOKP3MSMHS=?,NOKP4MSMHS=?,GELOMMSMHS=?,NAMA_AYAH=?,TGLHR_AYAH=?,TPLHR_AYAH=?,LULUSAN_AYAH=?,NOHAPE_AYAH=?,KERJA_AYAH=?,GAJI_AYAH=?,NIK_AYAH=?,KANDUNG_AYAH=?,NAMA_IBU=?,TGLHR_IBU=?,TPLHR_IBU=?,LULUSAN_IBU=?,NOHAPE_IBU=?,KERJA_IBU=?,GAJI_IBU=?,NIK_IBU=?,KANDUNG_IBU=?,NAMA_WALI=?,TGLHR_WALI=?,TPLHR_WALI=?,LULUSAN_WALI=?,NOHAPE_WALI=?,KERJA_WALI=?,GAJI_WALI=?,NIK_WALI=?,HUBUNGAN_WALI=?,NAMA_DARURAT1=?,NOHAPE_DARURAT1=?,HUBUNGAN_DARURAT1=?,NAMA_DARURAT2=?,NOHAPE_DARURAT2=?,HUBUNGAN_DARURAT2=?,NISNMSMHS=?,KEWARGANEGARAAN=?,NIKTPMSMHS=?,NOSIMMSMHS=?,PASPORMSMHS=?,MALAIKAT=? WHERE NPMHSMSMHS=?");
        		int i=1;
        		int ins = 0;
        		//1
        		stmt.setLong(i++,Long.parseLong(idobj_prodi_baru));
        		//2
        		stmt.setString(i++,Constants.getKdpti());
        		//3
        		stmt.setString(i++,kdjen);
        		//4
        		stmt.setString(i++,kode_prodi_baru);
        		//5
        		stmt.setString(i++,npm_prodi_baru);
        		//6
        		
        		String NIMHSMSMHS = "null";//untuk nim baru set null
        		if(NIMHSMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NIMHSMSMHS);
        		}
        		String NMMHSMSMHS = st.nextToken();
        		if(NMMHSMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NMMHSMSMHS);
        		}
        		String SHIFTMSMHS = st.nextToken();
        		if(NIMHSMSMHS.equalsIgnoreCase("null")) {
        			stmt.setString(i++, "N/A");
        		}
        		else {
        			stmt.setString(i++, SHIFTMSMHS);
        		}
        		String TPLHRMSMHS = st.nextToken();
        		if(TPLHRMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, TPLHRMSMHS);
        		}
        		String TGLHRMSMHS = st.nextToken();
        		//System.out.println("TGLHRMSMHS="+TGLHRMSMHS);
        		if(TGLHRMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(TGLHRMSMHS));
        		}
        		String KDJEKMSMHS = st.nextToken();
        		if(KDJEKMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, KDJEKMSMHS);
        		}
        		String TAHUNMSMHS = st.nextToken();
        		if(TAHUNMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, TAHUNMSMHS);
        		}
        		String SMAWLMSMHS = st.nextToken();
        		if(SMAWLMSMHS.equalsIgnoreCase("null")) {
        			stmt.setString(i++, thsms);//default sama dengan thsms saat isi buku tamu
        		}
        		else {
        			stmt.setString(i++, SMAWLMSMHS);
        		}
        		String BTSTUMSMHS = st.nextToken();
        		if(BTSTUMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, BTSTUMSMHS);
        		}
        		String ASSMAMSMHS = st.nextToken();
        		if(ASSMAMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, ASSMAMSMHS);
        		}
        		String TGMSKMSMHS = st.nextToken();
        		if(TGMSKMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(TGMSKMSMHS));
        		}
        		String TGLLSMSMHS = st.nextToken();
        		if(TGLLSMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(TGLLSMSMHS));
        		}
        		String STMHSMSMHS = st.nextToken();
        		if(STMHSMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, STMHSMSMHS);
        		}
        		String STPIDMSMHS = st.nextToken();
        		if(STPIDMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, STPIDMSMHS);
        		}
        		String SKSDIMSMHS = st.nextToken();
        		if(SKSDIMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.DOUBLE);
        		}
        		else {
        			stmt.setDouble(i++, Double.parseDouble(SKSDIMSMHS));
        		}
        		String ASNIMMSMHS = st.nextToken();
        		if(ASNIMMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, ASNIMMSMHS);
        		}
        		String ASPTIMSMHS = st.nextToken();
        		if(ASPTIMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, ASPTIMSMHS);
        		}
        		String ASJENMSMHS = st.nextToken();
        		if(ASJENMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, ASJENMSMHS);
        		}
        		String ASPSTMSMHS = st.nextToken();
        		if(ASPSTMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, ASPSTMSMHS);
        		}
        		String BISTUMSMHS = st.nextToken();
        		if(BISTUMSMHS.equalsIgnoreCase("null")) {
        			stmt.setString(i++, "BIAYA MANDIRI"); //default value
        		}
        		else {
        			stmt.setString(i++, BISTUMSMHS);
        		}
        		String PEKSBMSMHS = st.nextToken();
        		if(PEKSBMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, PEKSBMSMHS);
        		}
        		String NMPEKMSMHS = st.nextToken();
        		if(NMPEKMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NMPEKMSMHS);
        		}
        		String PTPEKMSMHS = st.nextToken();
        		if(PTPEKMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, PTPEKMSMHS);
        		}
        		String PSPEKMSMHS = st.nextToken();
        		if(PSPEKMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, PSPEKMSMHS);
        		}
        		String NOPRMMSMHS = st.nextToken();
        		if(NOPRMMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOPRMMSMHS);
        		}
        		String NOKP1MSMHS = st.nextToken();
        		if(NOKP1MSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOKP1MSMHS);
        		}
        		String NOKP2MSMHS = st.nextToken();
        		if(NOKP2MSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOKP2MSMHS);
        		}
        		String NOKP3MSMHS = st.nextToken();
        		if(NOKP3MSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOKP3MSMHS);
        		}
        		String NOKP4MSMHS = st.nextToken();
        		if(NOKP4MSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOKP4MSMHS);
        		}
        		String GELOMMSMHS = st.nextToken();
        		if(GELOMMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, GELOMMSMHS);
        		}
        		String NAMA_AYAH = st.nextToken();
        		if(NAMA_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NAMA_AYAH);
        		}
        		String TGLHR_AYAH = st.nextToken();
        		if(TGLHR_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(TGLHR_AYAH));
        		}
        		String TPLHR_AYAH = st.nextToken();
        		if(TPLHR_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, TPLHR_AYAH);
        		}
        		String LULUSAN_AYAH = st.nextToken();
        		if(LULUSAN_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, LULUSAN_AYAH);
        		}
        		String NOHAPE_AYAH = st.nextToken();
        		if(NOHAPE_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOHAPE_AYAH);
        		}
        		String KERJA_AYAH = st.nextToken();
        		if(KERJA_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, KERJA_AYAH);
        		}
        		String GAJI_AYAH = st.nextToken();
        		if(GAJI_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, GAJI_AYAH);
        		}
        		String NIK_AYAH = st.nextToken();
        		if(NIK_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NIK_AYAH);
        		}
        		String KANDUNG_AYAH = st.nextToken();
        		if(KANDUNG_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.BOOLEAN);
        		}
        		else {
        			stmt.setBoolean(i++, Boolean.parseBoolean(KANDUNG_AYAH));
        		}
        		String NAMA_IBU = st.nextToken();
        		if(NAMA_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NAMA_IBU);
        		}
        		String TGLHR_IBU = st.nextToken();
        		if(TGLHR_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(TGLHR_IBU));
        		}
        		String TPLHR_IBU = st.nextToken();
        		if(TPLHR_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, TPLHR_IBU);
        		}
        		String LULUSAN_IBU = st.nextToken();
        		if(LULUSAN_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, LULUSAN_IBU);
        		}
        		String NOHAPE_IBU = st.nextToken();
        		if(NOHAPE_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOHAPE_IBU);
        		}
        		String KERJA_IBU = st.nextToken();
        		if(KERJA_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, KERJA_IBU);
        		}
        		String GAJI_IBU = st.nextToken();
        		if(GAJI_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, GAJI_IBU);
        		}
        		String NIK_IBU = st.nextToken();
        		if(NIK_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NIK_IBU);
        		}
        		String KANDUNG_IBU = st.nextToken();
        		if(KANDUNG_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.BOOLEAN);
        		}
        		else {
        			stmt.setBoolean(i++, Boolean.parseBoolean(KANDUNG_IBU));
        		}
        		String NAMA_WALI = st.nextToken();
        		if(NAMA_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NAMA_WALI);
        		}
        		String TGLHR_WALI = st.nextToken();
        		if(TGLHR_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(TGLHR_WALI));
        		}
        		String TPLHR_WALI = st.nextToken();
        		if(TPLHR_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, TPLHR_WALI);
        		}
        		String LULUSAN_WALI = st.nextToken();
        		if(LULUSAN_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, LULUSAN_WALI);
        		}
        		String NOHAPE_WALI = st.nextToken();
        		if(NOHAPE_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOHAPE_WALI);
        		}
        		String KERJA_WALI = st.nextToken();
        		if(KERJA_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, KERJA_WALI);
        		}
        		String GAJI_WALI = st.nextToken();
        		if(GAJI_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, GAJI_WALI);
        		}
        		String NIK_WALI = st.nextToken();
        		if(NIK_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NIK_WALI);
        		}
        		String HUBUNGAN_WALI = st.nextToken();
        		if(HUBUNGAN_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, HUBUNGAN_WALI);
        		}
        		String NAMA_DARURAT1 = st.nextToken();
        		if(NAMA_DARURAT1.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NAMA_DARURAT1);
        		}
        		String NOHAPE_DARURAT1 = st.nextToken();
        		if(NOHAPE_DARURAT1.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOHAPE_DARURAT1);
        		}
        		String HUBUNGAN_DARURAT1 = st.nextToken();
        		if(HUBUNGAN_DARURAT1.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, HUBUNGAN_DARURAT1);
        		}
        		String NAMA_DARURAT2 = st.nextToken();
        		if(NAMA_DARURAT2.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NAMA_DARURAT2);
        		}
        		String NOHAPE_DARURAT2 = st.nextToken();
        		if(NOHAPE_DARURAT2.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOHAPE_DARURAT2);
        		}
        		String HUBUNGAN_DARURAT2 = st.nextToken();
        		if(HUBUNGAN_DARURAT2.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, HUBUNGAN_DARURAT2);
        		}
        		String NISNMSMHS = st.nextToken();
        		if(NISNMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NISNMSMHS);
        		}
        		String KEWARGANEGARAAN = st.nextToken();
        		if(KEWARGANEGARAAN.equalsIgnoreCase("null")) {
        			stmt.setString(i++, "INDONESIA");//default
        		}
        		else {
        			stmt.setString(i++, KEWARGANEGARAAN);
        		}
        		String NIKTPMSMHS = st.nextToken();
        		if(NIKTPMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NIKTPMSMHS);
        		}
        		String NOSIMMSMHS = st.nextToken();
        		if(NOSIMMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOSIMMSMHS);
        		}
        		String PASPORMSMHS = st.nextToken();
        		if(PASPORMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, PASPORMSMHS);
        		}
        		String MALAIKAT = st.nextToken();
        		if(MALAIKAT!=null) {
        			stmt.setBoolean(i++, Boolean.parseBoolean(MALAIKAT));
        		}
        		else {
        			stmt.setBoolean(i++, false);
        		}
        		stmt.setString(i++, old_npmhs);
        		ins = stmt.executeUpdate();
        		//System.out.println("npm_tamu="+npm_tamu);
        		//System.out.println("npm="+npm);
        		//System.out.println("ins="+ins);
        		if(ins>0) {
        			sukses = true;
        			/*
        			 * UPDATE PAYMENT
        			 */
        			stmt = con.prepareStatement("update PYMNT set NPMHSPYMNT=? where NPMHSPYMNT=?");
        			stmt.setString(1, npm_prodi_baru);
        			stmt.setString(2, old_npmhs);
        			stmt.executeUpdate();
        			
        			stmt = con.prepareStatement("update PYMNT_TRANSIT set NPMHSPYMNT=? where NPMHSPYMNT=?");
        			stmt.setString(1, npm_prodi_baru);
        			stmt.setString(2, old_npmhs);
        			stmt.executeUpdate();
        			/*
        			 * CHANGE OLD FOLDER
        			 */
        			updateFolderPathMhsPindahProdi(old_npmhs, npm_prodi_baru);
        			//update EXT_CIVITAS bilai sukese insert CIVITAS
        			stmt = con.prepareStatement("UPDATE EXT_CIVITAS SET KDPSTMSMHS=?,NPMHSMSMHS=?,STTUSMSMHS=?,EMAILMSMHS=?,NOHPEMSMHS=?,ALMRMMSMHS=?,NRTRMMSMHS=?,NRWRMMSMHS=?,PROVRMMSMHS=?,PROVRMIDWIL=?,KOTRMMSMHS=?,KOTRMIDWIL=?,KECRMMSMHS=?,KECRMIDWIL=?,KELRMMSMHS=?,DUSUNMSMHS=?,POSRMMSMHS=?,TELRMMSMHS=?,ALMKTMSMHS=?,KOTKTMSMHS=?,POSKTMSMHS=?,TELKTMSMHS=?,JBTKTMSMHS=?,BIDKTMSMHS=?,JENKTMSMHS=?,NMMSPMSMHS=?,ALMSPMSMHS=?,POSSPMSMHS=?,KOTSPMSMHS=?,NEGSPMSMHS=?,TELSPMSMHS=?,NEGLHMSMHS=?,AGAMAMSMHS=?,KRKLMMSMHS=?,TTLOGMSMHS=?,TMLOGMSMHS=?,DTLOGMSMHS=?,IDPAKETBEASISWA=?,NPM_PA=?,NMM_PA=?,PETUGAS=?,ASAL_SMA=?,KOTA_SMA=?,LULUS_SMA=? WHERE NPMHSMSMHS=?");
        			if(ins>0) {
        				i = 1;
        	    		st = new StringTokenizer(info_ext_civitas,"`");
        	    		String old_kdpst_e=st.nextToken();
        	    		String old_npmhs_e=st.nextToken();
        	    		
        	    		//String KDPSTMSMHS = st.nextToken();
        	    		stmt.setString(i++, kode_prodi_baru);//1
        	    		//String NPMHSMSMHS = st.nextToken();
        	    		stmt.setString(i++, npm_prodi_baru);//2
        	    		
        	    		String STTUSMSMHS = st.nextToken();//3
        	    		if(Checker.isStringNullOrEmpty(STTUSMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, STTUSMSMHS);
        	    		}
        	    		String EMAILMSMHS = st.nextToken();//4
        	    		if(Checker.isStringNullOrEmpty(EMAILMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, EMAILMSMHS);
        	    		}
        	    		String NOHPEMSMHS = st.nextToken();//5
        	    		if(Checker.isStringNullOrEmpty(NOHPEMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, NOHPEMSMHS);
        	    		}
        	    		String ALMRMMSMHS = st.nextToken();//6
        	    		if(Checker.isStringNullOrEmpty(ALMRMMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, ALMRMMSMHS);
        	    		}
        	    		
        	    		String KOTRMMSMHS = st.nextToken();//11
        	    		String POSRMMSMHS = st.nextToken();//17
        	    		String TELRMMSMHS = st.nextToken();//18
        	    		String ALMKTMSMHS = st.nextToken();//19
        	    		String KOTKTMSMHS = st.nextToken();//20
        	    		String POSKTMSMHS = st.nextToken();//21
        	    		String TELKTMSMHS = st.nextToken();//22
        	    		String JBTKTMSMHS = st.nextToken();//23
        	    		String BIDKTMSMHS = st.nextToken();//24
        	    		String JENKTMSMHS = st.nextToken();//25
        	    		String NMMSPMSMHS = st.nextToken();//26
        	    		String ALMSPMSMHS = st.nextToken();//27
        	    		String POSSPMSMHS = st.nextToken();//28
        	    		String KOTSPMSMHS = st.nextToken();//29
        	    		String NEGSPMSMHS = st.nextToken();//30
        	    		String TELSPMSMHS = st.nextToken();//31
        	    		String NEGLHMSMHS = st.nextToken();//32
        	    		String AGAMAMSMHS = st.nextToken();//33
        	    		String KRKLMMSMHS = st.nextToken();//34
        	    		String TTLOGMSMHS = st.nextToken();//35
        	    		String TMLOGMSMHS = st.nextToken();//36
        	    		String DTLOGMSMHS = st.nextToken();//36
        	    		String IDPAKETBEASISWA = st.nextToken();//37
        	    		String NPM_PA = st.nextToken();//38
        	    		String NMM_PA = st.nextToken();//39
        	    		String PETUGAS = st.nextToken();//40
        	    		String ASAL_SMA = st.nextToken();//41
        	    		String KOTA_SMA = st.nextToken();//42
        	    		String LULUS_SMA = st.nextToken();//43
        	    		
        	    		String NRTRMMSMHS = st.nextToken();//7
        	    		if(Checker.isStringNullOrEmpty(NRTRMMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, NRTRMMSMHS);
        	    		}
        	    		String NRWRMMSMHS = st.nextToken();//8
        	    		if(Checker.isStringNullOrEmpty(NRWRMMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, NRWRMMSMHS);
        	    		}
        	    		String PROVRMMSMHS = st.nextToken();//9
        	    		if(Checker.isStringNullOrEmpty(PROVRMMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, PROVRMMSMHS);
        	    		}
        	    		String PROVRMIDWIL = st.nextToken();//10
        	    		if(Checker.isStringNullOrEmpty(PROVRMIDWIL)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, PROVRMIDWIL);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(KOTRMMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, KOTRMMSMHS);
        	    		}
        	    		String KOTRMIDWIL = st.nextToken();//12
        	    		if(Checker.isStringNullOrEmpty(KOTRMIDWIL)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, KOTRMIDWIL);
        	    		}
        	    		String KECRMMSMHS = st.nextToken();//13
        	    		if(Checker.isStringNullOrEmpty(KECRMMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, KECRMMSMHS);
        	    		}
        	    		String KECRMIDWIL = st.nextToken();//14
        	    		if(Checker.isStringNullOrEmpty(KECRMIDWIL)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, KECRMIDWIL);
        	    		}
        	    		String KELRMMSMHS = st.nextToken();//15
        	    		if(Checker.isStringNullOrEmpty(KELRMMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, KELRMMSMHS);
        	    		}
        	    		String DUSUNMSMHS = st.nextToken();//16
        	    		if(Checker.isStringNullOrEmpty(DUSUNMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, DUSUNMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(POSRMMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, POSRMMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(TELRMMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, TELRMMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(ALMKTMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, ALMKTMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(KOTKTMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, KOTKTMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(POSKTMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, POSKTMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(TELKTMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, TELKTMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(JBTKTMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, JBTKTMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(BIDKTMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, BIDKTMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(JENKTMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, JENKTMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(NMMSPMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, NMMSPMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(ALMSPMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, ALMSPMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(POSSPMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, POSSPMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(KOTSPMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, KOTSPMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(NEGSPMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, NEGSPMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(TELSPMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, TELSPMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(NEGLHMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, NEGLHMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(AGAMAMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, AGAMAMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(KRKLMMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, KRKLMMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(TTLOGMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, TTLOGMSMHS);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(TMLOGMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.INTEGER);
        	    		}
        	    		else {
        	    			stmt.setInt(i++, Integer.parseInt(TMLOGMSMHS));
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(DTLOGMSMHS)) {
        	    			stmt.setNull(i++, java.sql.Types.TIMESTAMP);
        	    		}
        	    		else {
        	    			stmt.setInt(i++, Integer.parseInt(DTLOGMSMHS));
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(IDPAKETBEASISWA)) {
        	    			stmt.setLong(i++, java.sql.Types.INTEGER);
        	    		}
        	    		else {
        	    			stmt.setLong(i++, Long.parseLong(IDPAKETBEASISWA));
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(NPM_PA)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, NPM_PA);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(NMM_PA)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, NMM_PA);
        	    		}
        	    		
        	    		//System.out.println("PETUGAS="+PETUGAS);
        	    		if(Checker.isStringNullOrEmpty(PETUGAS)) {
        	    			stmt.setBoolean(i++, false);
        	    			//System.out.println("one");;
        	    		}
        	    		else {
        	    			//System.out.println("two");;
        	    			stmt.setBoolean(i++, Boolean.parseBoolean(PETUGAS));
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(ASAL_SMA)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, ASAL_SMA);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(KOTA_SMA)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, KOTA_SMA);
        	    		}
        	    		
        	    		if(Checker.isStringNullOrEmpty(LULUS_SMA)) {
        	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
        	    		}
        	    		else {
        	    			stmt.setString(i++, LULUS_SMA);
        	    		}
        	    		stmt.setString(i++, old_npmhs_e);//44
        	    		stmt.executeUpdate();
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
    	return sukses;
    }
    
    
    public String pindahProdiMhsLama(Vector v_SearchDbInfoMhs_getDataProfileCivitasAndExtMhs, String kode_prodi_baru) {
    	//return npm_baru
    	
    	String nunpm = insertCivitasBaru(v_SearchDbInfoMhs_getDataProfileCivitasAndExtMhs, kode_prodi_baru, getThsmsPmb());
    	/*
    	boolean sukses=false;
    	try {
    		
    		ListIterator li = v_SearchDbInfoMhs_getDataProfileCivitasAndExtMhs.listIterator();
    		String info_civitas = (String)li.next();
    		String info_ext_civitas = (String)li.next();
    		
    		if(!Checker.isStringNullOrEmpty(info_civitas)) {
    			StringTokenizer st = new StringTokenizer(info_civitas,"`");
    			String init_id = st.nextToken();
        		String init_idobj=st.nextToken();
        		String kdpti=st.nextToken();
        		String old_kdjen=st.nextToken();
        		String old_kdpst=st.nextToken();
        		String old_npmhs=st.nextToken();
        		String old_nimhs=st.nextToken();
        		String kode_kmp = Getter.getDomisiliKampus(Integer.parseInt(init_idobj));
        		String kdjen = Checker.getKdjen(kode_prodi_baru);
        		String idobj_prodi_baru = Checker.getObjidMhsProdi(kode_prodi_baru, kode_kmp);
        		String thsms = getThsmsPmb();
        		String npm_prodi_baru = generateNpm(thsms,kode_prodi_baru);
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("INSERT INTO CIVITAS(ID_OBJ,KDPTIMSMHS,KDJENMSMHS,KDPSTMSMHS,NPMHSMSMHS,NIMHSMSMHS,NMMHSMSMHS,SHIFTMSMHS,TPLHRMSMHS,TGLHRMSMHS,KDJEKMSMHS,TAHUNMSMHS,SMAWLMSMHS,BTSTUMSMHS,ASSMAMSMHS,TGMSKMSMHS,STMHSMSMHS,STPIDMSMHS,SKSDIMSMHS,ASNIMMSMHS,ASPTIMSMHS,ASJENMSMHS,ASPSTMSMHS,BISTUMSMHS,PEKSBMSMHS,NMPEKMSMHS,PTPEKMSMHS,PSPEKMSMHS,GELOMMSMHS,NAMA_AYAH,TGLHR_AYAH,TPLHR_AYAH,LULUSAN_AYAH,NOHAPE_AYAH,KERJA_AYAH,GAJI_AYAH,NIK_AYAH,KANDUNG_AYAH,NAMA_IBU,TGLHR_IBU,TPLHR_IBU,LULUSAN_IBU,NOHAPE_IBU,KERJA_IBU,GAJI_IBU,NIK_IBU,KANDUNG_IBU,NAMA_WALI,TGLHR_WALI,TPLHR_WALI,LULUSAN_WALI,NOHAPE_WALI,KERJA_WALI,GAJI_WALI,NIK_WALI,HUBUNGAN_WALI,NAMA_DARURAT1,NOHAPE_DARURAT1,HUBUNGAN_DARURAT1,NAMA_DARURAT2,NOHAPE_DARURAT2,HUBUNGAN_DARURAT2,NISNMSMHS,KEWARGANEGARAAN,NIKTPMSMHS,NOSIMMSMHS,PASPORMSMHS,MALAIKAT)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        		int i=1;
        		int ins = 0;
        		//1
        		stmt.setLong(i++,Long.parseLong(idobj_prodi_baru));
        		//2
        		stmt.setString(i++,Constants.getKdpti());
        		//3
        		stmt.setString(i++,kdjen);
        		//4
        		stmt.setString(i++,kode_prodi_baru);
        		//5
        		stmt.setString(i++,npm_prodi_baru);
        		//6
        		String NIMHSMSMHS = "null";//untuk nim baru set null
        		if(NIMHSMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NIMHSMSMHS);
        		}
        		//7
        		String NMMHSMSMHS = st.nextToken();
        		if(NMMHSMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NMMHSMSMHS);
        		}
        		//8
        		String SHIFTMSMHS = st.nextToken();
        		if(NIMHSMSMHS.equalsIgnoreCase("null")) {
        			stmt.setString(i++, "N/A");
        		}
        		else {
        			stmt.setString(i++, SHIFTMSMHS);
        		}
        		//9
        		String TPLHRMSMHS = st.nextToken();
        		if(TPLHRMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, TPLHRMSMHS);
        		}
        		//10
        		String TGLHRMSMHS = st.nextToken();
        		//System.out.println("TGLHRMSMHS="+TGLHRMSMHS);
        		if(TGLHRMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(TGLHRMSMHS));
        		}
        		//11
        		String KDJEKMSMHS = st.nextToken();
        		if(KDJEKMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, KDJEKMSMHS);
        		}
        		//12
        		String TAHUNMSMHS = st.nextToken();
        		stmt.setString(i++, thsms.substring(0, 4));
        		
        		//13
        		String SMAWLMSMHS = st.nextToken();
        		stmt.setString(i++, thsms);
        		
        		//14
        		String BTSTUMSMHS = st.nextToken();
        		if(BTSTUMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, BTSTUMSMHS);
        		}
        		//15
        		String ASSMAMSMHS = st.nextToken();
        		if(ASSMAMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, ASSMAMSMHS);
        		}
        		//16
        		String TGMSKMSMHS = st.nextToken();
        		if(TGMSKMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(TGMSKMSMHS));
        		}
        		//17
        		String TGLLSMSMHS = st.nextToken();

        		
        		String STMHSMSMHS = st.nextToken();
        		if(STMHSMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, STMHSMSMHS);
        		}
        		//19
        		String STPIDMSMHS = st.nextToken();
        		stmt.setString(i++, "P");
        		
        		//20
        		String SKSDIMSMHS = st.nextToken();
        		stmt.setNull(i++, java.sql.Types.DOUBLE);
        		
        		//21
        		String ASNIMMSMHS = st.nextToken();
        		stmt.setString(i++, old_npmhs);
        		
        		//22
        		String ASPTIMSMHS = st.nextToken();
        		stmt.setString(i++, Constants.getKdpti());
        		
        		//23
        		String ASJENMSMHS = st.nextToken();
        		stmt.setString(i++, old_kdjen);
        		//24
        		String ASPSTMSMHS = st.nextToken();
        		stmt.setString(i++, old_kdpst);
        		//25
        		String BISTUMSMHS = st.nextToken();
        		if(BISTUMSMHS.equalsIgnoreCase("null")) {
        			stmt.setString(i++, "BIAYA MANDIRI"); //default value
        		}
        		else {
        			stmt.setString(i++, BISTUMSMHS);
        		}
        		//26
        		String PEKSBMSMHS = st.nextToken();
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        		
        		//27
        		String NMPEKMSMHS = st.nextToken();
        		if(NMPEKMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NMPEKMSMHS);
        		}
        		//28
        		String PTPEKMSMHS = st.nextToken();
        		if(PTPEKMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, PTPEKMSMHS);
        		}
        		//29
        		String PSPEKMSMHS = st.nextToken();
        		if(PSPEKMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, PSPEKMSMHS);
        		}
        		
        		String noprm = st.nextToken();
        		String noprm1 = st.nextToken();
        		String noprm2 = st.nextToken();
        		String noprm3 = st.nextToken();
        		String noprm4 = st.nextToken();
        		//30
        		String GELOMMSMHS = st.nextToken();
        		if(GELOMMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, GELOMMSMHS);
        		}
        		//31
        		
        		String NAMA_AYAH = st.nextToken();
        		if(NAMA_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
            		
        		}
        		else {
        			stmt.setString(i++, NAMA_AYAH);
        		}
        		//32
        		String TGLHR_AYAH = st.nextToken();
        		if(TGLHR_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(TGLHR_AYAH));
        		}
        		//38
        		String TPLHR_AYAH = st.nextToken();
        		if(TPLHR_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, TPLHR_AYAH);
        		}
        		//39
        		String LULUSAN_AYAH = st.nextToken();
        		if(LULUSAN_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, LULUSAN_AYAH);
        		}
        		//40
        		String NOHAPE_AYAH = st.nextToken();
        		if(NOHAPE_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOHAPE_AYAH);
        		}
        		//41
        		String KERJA_AYAH = st.nextToken();
        		if(KERJA_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, KERJA_AYAH);
        		}
        		//42
        		String GAJI_AYAH = st.nextToken();
        		if(GAJI_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, GAJI_AYAH);
        		}
        		//43
        		String NIK_AYAH = st.nextToken();
        		if(NIK_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NIK_AYAH);
        		}
        		//44
        		String KANDUNG_AYAH = st.nextToken();
        		if(KANDUNG_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.BOOLEAN);
        		}
        		else {
        			stmt.setBoolean(i++, Boolean.parseBoolean(KANDUNG_AYAH));
        		}
        		//45
        		String NAMA_IBU = st.nextToken();
        		if(NAMA_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NAMA_IBU);
        		}
        		//46
        		String TGLHR_IBU = st.nextToken();
        		if(TGLHR_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(TGLHR_IBU));
        		}
        		//47
        		String TPLHR_IBU = st.nextToken();
        		if(TPLHR_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, TPLHR_IBU);
        		}
        		//48
        		String LULUSAN_IBU = st.nextToken();
        		if(LULUSAN_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, LULUSAN_IBU);
        		}
        		//49
        		String NOHAPE_IBU = st.nextToken();
        		if(NOHAPE_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOHAPE_IBU);
        		}
        		//50
        		String KERJA_IBU = st.nextToken();
        		if(KERJA_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, KERJA_IBU);
        		}
        		//51
        		String GAJI_IBU = st.nextToken();
        		if(GAJI_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, GAJI_IBU);
        		}
        		//52
        		String NIK_IBU = st.nextToken();
        		if(NIK_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NIK_IBU);
        		}
        		//53
        		String KANDUNG_IBU = st.nextToken();
        		if(KANDUNG_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.BOOLEAN);
        		}
        		else {
        			stmt.setBoolean(i++, Boolean.parseBoolean(KANDUNG_IBU));
        		}
        		//54
        		String NAMA_WALI = st.nextToken();
        		if(NAMA_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NAMA_WALI);
        		}
        		//55
        		String TGLHR_WALI = st.nextToken();
        		if(TGLHR_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(TGLHR_WALI));
        		}
        		//56
        		String TPLHR_WALI = st.nextToken();
        		if(TPLHR_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, TPLHR_WALI);
        		}
        		//57
        		String LULUSAN_WALI = st.nextToken();
        		if(LULUSAN_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, LULUSAN_WALI);
        		}
        		//58
        		String NOHAPE_WALI = st.nextToken();
        		if(NOHAPE_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOHAPE_WALI);
        		}
        		//59
        		String KERJA_WALI = st.nextToken();
        		if(KERJA_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, KERJA_WALI);
        		}
        		//60
        		String GAJI_WALI = st.nextToken();
        		if(GAJI_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, GAJI_WALI);
        		}
        		//61
        		String NIK_WALI = st.nextToken();
        		if(NIK_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NIK_WALI);
        		}
        		//62
        		String HUBUNGAN_WALI = st.nextToken();
        		if(HUBUNGAN_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, HUBUNGAN_WALI);
        		}
        		//63
        		String NAMA_DARURAT1 = st.nextToken();
        		if(NAMA_DARURAT1.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NAMA_DARURAT1);
        		}
        		//64
        		String NOHAPE_DARURAT1 = st.nextToken();
        		if(NOHAPE_DARURAT1.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOHAPE_DARURAT1);
        		}
        		//65
        		String HUBUNGAN_DARURAT1 = st.nextToken();
        		if(HUBUNGAN_DARURAT1.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, HUBUNGAN_DARURAT1);
        		}
        		//66
        		String NAMA_DARURAT2 = st.nextToken();
        		if(NAMA_DARURAT2.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NAMA_DARURAT2);
        		}
        		//67
        		String NOHAPE_DARURAT2 = st.nextToken();
        		if(NOHAPE_DARURAT2.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOHAPE_DARURAT2);
        		}
        		//68
        		String HUBUNGAN_DARURAT2 = st.nextToken();
        		if(HUBUNGAN_DARURAT2.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, HUBUNGAN_DARURAT2);
        		}
        		//69
        		String NISNMSMHS = st.nextToken();
        		if(NISNMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NISNMSMHS);
        		}
        		//70
        		String KEWARGANEGARAAN = st.nextToken();
        		if(KEWARGANEGARAAN.equalsIgnoreCase("null")) {
        			stmt.setString(i++, "INDONESIA");//default
        		}
        		else {
        			stmt.setString(i++, KEWARGANEGARAAN);
        		}
        		//71
        		String NIKTPMSMHS = st.nextToken();
        		if(NIKTPMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NIKTPMSMHS);
        		}
        		//72
        		String NOSIMMSMHS = st.nextToken();
        		if(NOSIMMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOSIMMSMHS);
        		}
        		//73
        		String PASPORMSMHS = st.nextToken();
        		if(PASPORMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, PASPORMSMHS);
        		}
        		//74
        		String MALAIKAT = st.nextToken();
        		if(MALAIKAT!=null) {
        			stmt.setBoolean(i++, Boolean.parseBoolean(MALAIKAT));
        		}
        		else {
        			stmt.setBoolean(i++, false);
        		}
        		ins = stmt.executeUpdate();	
        		
       			//update EXT_CIVITAS bilai sukese insert CIVITAS
    			stmt = con.prepareStatement("INSERT INTO EXT_CIVITAS (KDPSTMSMHS,NPMHSMSMHS,STTUSMSMHS,EMAILMSMHS,NOHPEMSMHS,ALMRMMSMHS,NRTRMMSMHS,NRWRMMSMHS,PROVRMMSMHS,PROVRMIDWIL,KOTRMMSMHS,KOTRMIDWIL,KECRMMSMHS,KECRMIDWIL,KELRMMSMHS,DUSUNMSMHS,POSRMMSMHS,TELRMMSMHS,ALMKTMSMHS,KOTKTMSMHS,POSKTMSMHS,TELKTMSMHS,JBTKTMSMHS,BIDKTMSMHS,JENKTMSMHS,NMMSPMSMHS,ALMSPMSMHS,POSSPMSMHS,KOTSPMSMHS,NEGSPMSMHS,TELSPMSMHS,NEGLHMSMHS,AGAMAMSMHS,IDPAKETBEASISWA,NPM_PA,NMM_PA,PETUGAS,ASAL_SMA,KOTA_SMA,LULUS_SMA)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    			if(ins>0) {
    				nunpm = new String(npm_prodi_baru);
    				
    				i = 1;
    	    		st = new StringTokenizer(info_ext_civitas,"`");
    	    		String old_kdpst_e=st.nextToken();
    	    		String old_npmhs_e=st.nextToken();
    	    		
    	    		stmt.setString(i++, kode_prodi_baru);//1
    	    		stmt.setString(i++, npm_prodi_baru);//2
    	    		
    	    		String STTUSMSMHS = st.nextToken();//3
    	    		if(Checker.isStringNullOrEmpty(STTUSMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, STTUSMSMHS);
    	    		}
    	    		String EMAILMSMHS = st.nextToken();//4
    	    		if(Checker.isStringNullOrEmpty(EMAILMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, EMAILMSMHS);
    	    		}
    	    		String NOHPEMSMHS = st.nextToken();//5
    	    		if(Checker.isStringNullOrEmpty(NOHPEMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, NOHPEMSMHS);
    	    		}
    	    		String ALMRMMSMHS = st.nextToken();//6
    	    		if(Checker.isStringNullOrEmpty(ALMRMMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, ALMRMMSMHS);
    	    		}
    	    		
    	    		String KOTRMMSMHS = st.nextToken();//11
    	    		String POSRMMSMHS = st.nextToken();//17
    	    		String TELRMMSMHS = st.nextToken();//18
    	    		String ALMKTMSMHS = st.nextToken();//19
    	    		String KOTKTMSMHS = st.nextToken();//20
    	    		String POSKTMSMHS = st.nextToken();//21
    	    		String TELKTMSMHS = st.nextToken();//22
    	    		String JBTKTMSMHS = st.nextToken();//23
    	    		String BIDKTMSMHS = st.nextToken();//24
    	    		String JENKTMSMHS = st.nextToken();//25
    	    		String NMMSPMSMHS = st.nextToken();//26
    	    		String ALMSPMSMHS = st.nextToken();//27
    	    		String POSSPMSMHS = st.nextToken();//28
    	    		String KOTSPMSMHS = st.nextToken();//29
    	    		String NEGSPMSMHS = st.nextToken();//30
    	    		String TELSPMSMHS = st.nextToken();//31
    	    		String NEGLHMSMHS = st.nextToken();//32
    	    		String AGAMAMSMHS = st.nextToken();//33
    	    		String KRKLMMSMHS = st.nextToken();//34
    	    		String TTLOGMSMHS = st.nextToken();//35
    	    		String TMLOGMSMHS = st.nextToken();//36
    	    		String DTLOGMSMHS = st.nextToken();//36
    	    		String IDPAKETBEASISWA = st.nextToken();//37
    	    		String NPM_PA = st.nextToken();//38
    	    		String NMM_PA = st.nextToken();//39
    	    		String PETUGAS = st.nextToken();//40
    	    		String ASAL_SMA = st.nextToken();//41
    	    		String KOTA_SMA = st.nextToken();//42
    	    		String LULUS_SMA = st.nextToken();//43
    	    		
    	    		String NRTRMMSMHS = st.nextToken();//7
    	    		if(Checker.isStringNullOrEmpty(NRTRMMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, NRTRMMSMHS);
    	    		}
    	    		String NRWRMMSMHS = st.nextToken();//8
    	    		if(Checker.isStringNullOrEmpty(NRWRMMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, NRWRMMSMHS);
    	    		}
    	    		String PROVRMMSMHS = st.nextToken();//9
    	    		if(Checker.isStringNullOrEmpty(PROVRMMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, PROVRMMSMHS);
    	    		}
    	    		String PROVRMIDWIL = st.nextToken();//10
    	    		if(Checker.isStringNullOrEmpty(PROVRMIDWIL)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, PROVRMIDWIL);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(KOTRMMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, KOTRMMSMHS);
    	    		}
    	    		String KOTRMIDWIL = st.nextToken();//12
    	    		if(Checker.isStringNullOrEmpty(KOTRMIDWIL)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, KOTRMIDWIL);
    	    		}
    	    		String KECRMMSMHS = st.nextToken();//13
    	    		if(Checker.isStringNullOrEmpty(KECRMMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, KECRMMSMHS);
    	    		}
    	    		String KECRMIDWIL = st.nextToken();//14
    	    		if(Checker.isStringNullOrEmpty(KECRMIDWIL)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, KECRMIDWIL);
    	    		}
    	    		String KELRMMSMHS = st.nextToken();//15
    	    		if(Checker.isStringNullOrEmpty(KELRMMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, KELRMMSMHS);
    	    		}
    	    		String DUSUNMSMHS = st.nextToken();//16
    	    		if(Checker.isStringNullOrEmpty(DUSUNMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, DUSUNMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(POSRMMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, POSRMMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(TELRMMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, TELRMMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(ALMKTMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, ALMKTMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(KOTKTMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, KOTKTMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(POSKTMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, POSKTMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(TELKTMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, TELKTMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(JBTKTMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, JBTKTMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(BIDKTMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, BIDKTMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(JENKTMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, JENKTMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(NMMSPMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, NMMSPMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(ALMSPMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, ALMSPMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(POSSPMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, POSSPMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(KOTSPMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, KOTSPMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(NEGSPMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, NEGSPMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(TELSPMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, TELSPMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(NEGLHMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, NEGLHMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(AGAMAMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, AGAMAMSMHS);
    	    		}
    	    		
    	    		    	    		
    	    		if(Checker.isStringNullOrEmpty(IDPAKETBEASISWA)) {
    	    			stmt.setLong(i++, java.sql.Types.INTEGER);
    	    		}
    	    		else {
    	    			stmt.setLong(i++, Long.parseLong(IDPAKETBEASISWA));
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(NPM_PA)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, NPM_PA);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(NMM_PA)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, NMM_PA);
    	    		}
    	    		
    	    		//System.out.println("PETUGAS="+PETUGAS);
    	    		if(Checker.isStringNullOrEmpty(PETUGAS)) {
    	    			stmt.setBoolean(i++, false);
    	    			//System.out.println("one");;
    	    		}
    	    		else {
    	    			//System.out.println("two");;
    	    			stmt.setBoolean(i++, Boolean.parseBoolean(PETUGAS));
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(ASAL_SMA)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, ASAL_SMA);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(KOTA_SMA)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, KOTA_SMA);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(LULUS_SMA)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, LULUS_SMA);
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
    	*/
    	return nunpm;
    }
    
    
    
    public String insertCivitasBaru(Vector v_SearchDbInfoMhs_getDataProfileCivitasAndExtMhs, String kode_prodi_baru, String smawl) {
    	//return npm_baru
    	String nunpm=null;
    	boolean sukses=false;
    	try {
    		
    		ListIterator li = v_SearchDbInfoMhs_getDataProfileCivitasAndExtMhs.listIterator();
    		String info_civitas = (String)li.next();
    		String info_ext_civitas = (String)li.next();
    		
    		
    		//kdjen = getKdjen(kdpst_tamu);
    		if(!Checker.isStringNullOrEmpty(info_civitas)) {
    			//String info = id+"`"+idobj+"`"+kdpti+"`"+kdjen+"`"+kdpst+"`"+npmhs+"`"+nimhs+"`"+nmmhs+"`"+shift+"`"+tplhr+"`"+tglhr+"`"+kdjek+"`"+tahun+"`"+smawl+"`"+btstu+"`"+assma+"`"+tgmsk+"`"+tglls+"`"+stmhs+"`"+stpid+"`"+sksdi+"`"+asnim+"`"+aspti+"`"+asjen+"`"+aspst+"`"+bistu+"`"+peksb+"`"+nmpek+"`"+ptpek+"`"+pspek+"`"+noprm+"`"+nokp1+"`"+nokp2+"`"+nokp3+"`"+nokp4+"`"+gelom+"`"+nama_ayah+"`"+tglhr_ayah+"`"+tplhr_ayah+"`"+lulus_ayah+"`"+hape_ayah+"`"+kerja_ayah+"`"+gaji_ayah+"`"+nik_ayah+"`"+kandung_ayah+"`"+nama_ibu+"`"+tglhr_ibu+"`"+tplhr_ibu+"`"+lulus_ibu+"`"+hape_ibu+"`"+kerja_ibu+"`"+gaji_ibu+"`"+nik_ibu+"`"+kandung_ibu+"`"+nama_wali+"`"+tglhr_wali+"`"+tplhr_wali+"`"+lulus_wali+"`"+hape_wali+"`"+kerja_wali+"`"+gaji_wali+"`"+nik_wali+"`"+hub_wali+"`"+nama_emg1+"`"+hape_emg1+"`"+hub_emg1+"`"+nama_emg2+"`"+hape_emg2+"`"+hub_emg2+"`"+nisn+"`"+warga+"`"+nonik+"`"+nosim+"`"+paspor+"`"+angel;
    			
    			//System.out.println("info_civitas="+info_civitas);
    			StringTokenizer st = new StringTokenizer(info_civitas,"`");
    			String init_id = st.nextToken();
        		String init_idobj=st.nextToken();
        		String kdpti=st.nextToken();
        		String old_kdjen=st.nextToken();
        		String old_kdpst=st.nextToken();
        		String old_npmhs=st.nextToken();
        		String old_nimhs=st.nextToken();
        		//System.out.println("init_idobj="+init_idobj);
        		String kode_kmp = Getter.getDomisiliKampus(Integer.parseInt(init_idobj));
        		//System.out.println("kode_kmp="+kode_kmp);
        		//System.out.println("kode_prodi_baru="+kode_prodi_baru);
        		String kdjen = Checker.getKdjen(kode_prodi_baru);
        		String idobj_prodi_baru = Checker.getObjidMhsProdi(kode_prodi_baru, kode_kmp);
    			//String idobj = ""+default_idobj_tamu;//sama default_idobj_tamu
        		String thsms = new String(smawl);
        		String npm_prodi_baru = generateNpm(thsms,kode_prodi_baru);
        		
        		//System.out.println("npm_prodi_baru="+npm_prodi_baru);
        		//System.out.println("old_npmhs="+old_npmhs);
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		/*
        		 * ISERT CIVITAS
        		 */
        		stmt = con.prepareStatement("INSERT INTO CIVITAS(ID_OBJ,KDPTIMSMHS,KDJENMSMHS,KDPSTMSMHS,NPMHSMSMHS,NIMHSMSMHS,NMMHSMSMHS,SHIFTMSMHS,TPLHRMSMHS,TGLHRMSMHS,KDJEKMSMHS,TAHUNMSMHS,SMAWLMSMHS,BTSTUMSMHS,ASSMAMSMHS,TGMSKMSMHS,STMHSMSMHS,STPIDMSMHS,SKSDIMSMHS,ASNIMMSMHS,ASPTIMSMHS,ASJENMSMHS,ASPSTMSMHS,BISTUMSMHS,PEKSBMSMHS,NMPEKMSMHS,PTPEKMSMHS,PSPEKMSMHS,GELOMMSMHS,NAMA_AYAH,TGLHR_AYAH,TPLHR_AYAH,LULUSAN_AYAH,NOHAPE_AYAH,KERJA_AYAH,GAJI_AYAH,NIK_AYAH,KANDUNG_AYAH,NAMA_IBU,TGLHR_IBU,TPLHR_IBU,LULUSAN_IBU,NOHAPE_IBU,KERJA_IBU,GAJI_IBU,NIK_IBU,KANDUNG_IBU,NAMA_WALI,TGLHR_WALI,TPLHR_WALI,LULUSAN_WALI,NOHAPE_WALI,KERJA_WALI,GAJI_WALI,NIK_WALI,HUBUNGAN_WALI,NAMA_DARURAT1,NOHAPE_DARURAT1,HUBUNGAN_DARURAT1,NAMA_DARURAT2,NOHAPE_DARURAT2,HUBUNGAN_DARURAT2,NISNMSMHS,KEWARGANEGARAAN,NIKTPMSMHS,NOSIMMSMHS,PASPORMSMHS,MALAIKAT)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        		int i=1;
        		int ins = 0;
        		//1
        		stmt.setLong(i++,Long.parseLong(idobj_prodi_baru));
        		//2
        		stmt.setString(i++,Constants.getKdpti());
        		//3
        		stmt.setString(i++,kdjen);
        		//4
        		stmt.setString(i++,kode_prodi_baru);
        		//5
        		stmt.setString(i++,npm_prodi_baru);
        		
        		//6
        		String NIMHSMSMHS = "null";//untuk nim baru set null
        		if(NIMHSMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NIMHSMSMHS);
        		}
        		//7
        		String NMMHSMSMHS = st.nextToken();
        		if(NMMHSMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NMMHSMSMHS);
        		}
        		//8
        		String SHIFTMSMHS = st.nextToken();
        		if(NIMHSMSMHS.equalsIgnoreCase("null")) {
        			stmt.setString(i++, "N/A");
        		}
        		else {
        			stmt.setString(i++, SHIFTMSMHS);
        		}
        		//9
        		String TPLHRMSMHS = st.nextToken();
        		if(TPLHRMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, TPLHRMSMHS);
        		}
        		//10
        		String TGLHRMSMHS = st.nextToken();
        		//System.out.println("TGLHRMSMHS="+TGLHRMSMHS);
        		if(TGLHRMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(TGLHRMSMHS));
        		}
        		//11
        		String KDJEKMSMHS = st.nextToken();
        		if(KDJEKMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, KDJEKMSMHS);
        		}
        		//12
        		String TAHUNMSMHS = st.nextToken();
        		stmt.setString(i++, thsms.substring(0, 4));
        		
        		//13
        		String SMAWLMSMHS = st.nextToken();
        		stmt.setString(i++, thsms);
        		
        		//14
        		String BTSTUMSMHS = st.nextToken();
        		if(BTSTUMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, BTSTUMSMHS);
        		}
        		//15
        		String ASSMAMSMHS = st.nextToken();
        		if(ASSMAMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, ASSMAMSMHS);
        		}
        		//16
        		String TGMSKMSMHS = st.nextToken();
        		if(TGMSKMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(TGMSKMSMHS));
        		}
        		//17
        		String TGLLSMSMHS = st.nextToken();
        		//TIDAK DI PROSES 
        		//if(TGLLSMSMHS.equalsIgnoreCase("null")) {
        		//	stmt.setNull(i++, java.sql.Types.DATE);
        		//}
        		//else {
        		//	stmt.setDate(i++, Converter.formatDateBeforeInsert(TGLLSMSMHS));
        		//}
        		
        		String STMHSMSMHS = st.nextToken();
        		if(STMHSMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, STMHSMSMHS);
        		}
        		//19
        		String STPIDMSMHS = st.nextToken();
        		stmt.setString(i++, "P");
        		
        		//20
        		String SKSDIMSMHS = st.nextToken();
        		stmt.setNull(i++, java.sql.Types.DOUBLE);
        		
        		//21
        		String ASNIMMSMHS = st.nextToken();
        		stmt.setString(i++, old_npmhs);
        		
        		//22
        		String ASPTIMSMHS = st.nextToken();
        		stmt.setString(i++, Constants.getKdpti());
        		
        		//23
        		String ASJENMSMHS = st.nextToken();
        		stmt.setString(i++, old_kdjen);
        		//24
        		String ASPSTMSMHS = st.nextToken();
        		stmt.setString(i++, old_kdpst);
        		//25
        		String BISTUMSMHS = st.nextToken();
        		if(BISTUMSMHS.equalsIgnoreCase("null")) {
        			stmt.setString(i++, "BIAYA MANDIRI"); //default value
        		}
        		else {
        			stmt.setString(i++, BISTUMSMHS);
        		}
        		//26
        		String PEKSBMSMHS = st.nextToken();
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        		
        		//27
        		String NMPEKMSMHS = st.nextToken();
        		if(NMPEKMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NMPEKMSMHS);
        		}
        		//28
        		String PTPEKMSMHS = st.nextToken();
        		if(PTPEKMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, PTPEKMSMHS);
        		}
        		//29
        		String PSPEKMSMHS = st.nextToken();
        		if(PSPEKMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, PSPEKMSMHS);
        		}
        		
        		String noprm = st.nextToken();
        		String noprm1 = st.nextToken();
        		String noprm2 = st.nextToken();
        		String noprm3 = st.nextToken();
        		String noprm4 = st.nextToken();
        		//30
        		String GELOMMSMHS = st.nextToken();
        		if(GELOMMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, GELOMMSMHS);
        		}
        		//31
        		
        		String NAMA_AYAH = st.nextToken();
        		if(NAMA_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
            		
        		}
        		else {
        			stmt.setString(i++, NAMA_AYAH);
        		}
        		//32
        		String TGLHR_AYAH = st.nextToken();
        		if(TGLHR_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(TGLHR_AYAH));
        		}
        		//38
        		String TPLHR_AYAH = st.nextToken();
        		if(TPLHR_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, TPLHR_AYAH);
        		}
        		//39
        		String LULUSAN_AYAH = st.nextToken();
        		if(LULUSAN_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, LULUSAN_AYAH);
        		}
        		//40
        		String NOHAPE_AYAH = st.nextToken();
        		if(NOHAPE_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOHAPE_AYAH);
        		}
        		//41
        		String KERJA_AYAH = st.nextToken();
        		if(KERJA_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, KERJA_AYAH);
        		}
        		//42
        		String GAJI_AYAH = st.nextToken();
        		if(GAJI_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, GAJI_AYAH);
        		}
        		//43
        		String NIK_AYAH = st.nextToken();
        		if(NIK_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NIK_AYAH);
        		}
        		//44
        		String KANDUNG_AYAH = st.nextToken();
        		if(KANDUNG_AYAH.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.BOOLEAN);
        		}
        		else {
        			stmt.setBoolean(i++, Boolean.parseBoolean(KANDUNG_AYAH));
        		}
        		//45
        		String NAMA_IBU = st.nextToken();
        		if(NAMA_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NAMA_IBU);
        		}
        		//46
        		String TGLHR_IBU = st.nextToken();
        		if(TGLHR_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(TGLHR_IBU));
        		}
        		//47
        		String TPLHR_IBU = st.nextToken();
        		if(TPLHR_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, TPLHR_IBU);
        		}
        		//48
        		String LULUSAN_IBU = st.nextToken();
        		if(LULUSAN_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, LULUSAN_IBU);
        		}
        		//49
        		String NOHAPE_IBU = st.nextToken();
        		if(NOHAPE_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOHAPE_IBU);
        		}
        		//50
        		String KERJA_IBU = st.nextToken();
        		if(KERJA_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, KERJA_IBU);
        		}
        		//51
        		String GAJI_IBU = st.nextToken();
        		if(GAJI_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, GAJI_IBU);
        		}
        		//52
        		String NIK_IBU = st.nextToken();
        		if(NIK_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NIK_IBU);
        		}
        		//53
        		String KANDUNG_IBU = st.nextToken();
        		if(KANDUNG_IBU.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.BOOLEAN);
        		}
        		else {
        			stmt.setBoolean(i++, Boolean.parseBoolean(KANDUNG_IBU));
        		}
        		//54
        		String NAMA_WALI = st.nextToken();
        		if(NAMA_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NAMA_WALI);
        		}
        		//55
        		String TGLHR_WALI = st.nextToken();
        		if(TGLHR_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(TGLHR_WALI));
        		}
        		//56
        		String TPLHR_WALI = st.nextToken();
        		if(TPLHR_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, TPLHR_WALI);
        		}
        		//57
        		String LULUSAN_WALI = st.nextToken();
        		if(LULUSAN_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, LULUSAN_WALI);
        		}
        		//58
        		String NOHAPE_WALI = st.nextToken();
        		if(NOHAPE_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOHAPE_WALI);
        		}
        		//59
        		String KERJA_WALI = st.nextToken();
        		if(KERJA_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, KERJA_WALI);
        		}
        		//60
        		String GAJI_WALI = st.nextToken();
        		if(GAJI_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, GAJI_WALI);
        		}
        		//61
        		String NIK_WALI = st.nextToken();
        		if(NIK_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NIK_WALI);
        		}
        		//62
        		String HUBUNGAN_WALI = st.nextToken();
        		if(HUBUNGAN_WALI.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, HUBUNGAN_WALI);
        		}
        		//63
        		String NAMA_DARURAT1 = st.nextToken();
        		if(NAMA_DARURAT1.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NAMA_DARURAT1);
        		}
        		//64
        		String NOHAPE_DARURAT1 = st.nextToken();
        		if(NOHAPE_DARURAT1.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOHAPE_DARURAT1);
        		}
        		//65
        		String HUBUNGAN_DARURAT1 = st.nextToken();
        		if(HUBUNGAN_DARURAT1.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, HUBUNGAN_DARURAT1);
        		}
        		//66
        		String NAMA_DARURAT2 = st.nextToken();
        		if(NAMA_DARURAT2.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NAMA_DARURAT2);
        		}
        		//67
        		String NOHAPE_DARURAT2 = st.nextToken();
        		if(NOHAPE_DARURAT2.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOHAPE_DARURAT2);
        		}
        		//68
        		String HUBUNGAN_DARURAT2 = st.nextToken();
        		if(HUBUNGAN_DARURAT2.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, HUBUNGAN_DARURAT2);
        		}
        		//69
        		String NISNMSMHS = st.nextToken();
        		if(NISNMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NISNMSMHS);
        		}
        		//70
        		String KEWARGANEGARAAN = st.nextToken();
        		if(KEWARGANEGARAAN.equalsIgnoreCase("null")) {
        			stmt.setString(i++, "INDONESIA");//default
        		}
        		else {
        			stmt.setString(i++, KEWARGANEGARAAN);
        		}
        		//71
        		String NIKTPMSMHS = st.nextToken();
        		if(NIKTPMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NIKTPMSMHS);
        		}
        		//72
        		String NOSIMMSMHS = st.nextToken();
        		if(NOSIMMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, NOSIMMSMHS);
        		}
        		//73
        		String PASPORMSMHS = st.nextToken();
        		if(PASPORMSMHS.equalsIgnoreCase("null")) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, PASPORMSMHS);
        		}
        		//74
        		String MALAIKAT = st.nextToken();
        		if(MALAIKAT!=null) {
        			stmt.setBoolean(i++, Boolean.parseBoolean(MALAIKAT));
        		}
        		else {
        			stmt.setBoolean(i++, false);
        		}
        		ins = stmt.executeUpdate();	
        		
       			//update EXT_CIVITAS bilai sukese insert CIVITAS
    			stmt = con.prepareStatement("INSERT INTO EXT_CIVITAS (KDPSTMSMHS,NPMHSMSMHS,STTUSMSMHS,EMAILMSMHS,NOHPEMSMHS,ALMRMMSMHS,NRTRMMSMHS,NRWRMMSMHS,PROVRMMSMHS,PROVRMIDWIL,KOTRMMSMHS,KOTRMIDWIL,KECRMMSMHS,KECRMIDWIL,KELRMMSMHS,DUSUNMSMHS,POSRMMSMHS,TELRMMSMHS,ALMKTMSMHS,KOTKTMSMHS,POSKTMSMHS,TELKTMSMHS,JBTKTMSMHS,BIDKTMSMHS,JENKTMSMHS,NMMSPMSMHS,ALMSPMSMHS,POSSPMSMHS,KOTSPMSMHS,NEGSPMSMHS,TELSPMSMHS,NEGLHMSMHS,AGAMAMSMHS,IDPAKETBEASISWA,NPM_PA,NMM_PA,PETUGAS,ASAL_SMA,KOTA_SMA,LULUS_SMA)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    			if(ins>0) {
    				nunpm = new String(npm_prodi_baru);
    				
    				i = 1;
    	    		st = new StringTokenizer(info_ext_civitas,"`");
    	    		String old_kdpst_e=st.nextToken();
    	    		String old_npmhs_e=st.nextToken();
    	    		
    	    		//String KDPSTMSMHS = st.nextToken();
    	    		stmt.setString(i++, kode_prodi_baru);//1
    	    		//String NPMHSMSMHS = st.nextToken();
    	    		stmt.setString(i++, npm_prodi_baru);//2
    	    		
    	    		String STTUSMSMHS = st.nextToken();//3
    	    		if(Checker.isStringNullOrEmpty(STTUSMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, STTUSMSMHS);
    	    		}
    	    		String EMAILMSMHS = st.nextToken();//4
    	    		if(Checker.isStringNullOrEmpty(EMAILMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, EMAILMSMHS);
    	    		}
    	    		String NOHPEMSMHS = st.nextToken();//5
    	    		if(Checker.isStringNullOrEmpty(NOHPEMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, NOHPEMSMHS);
    	    		}
    	    		String ALMRMMSMHS = st.nextToken();//6
    	    		if(Checker.isStringNullOrEmpty(ALMRMMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, ALMRMMSMHS);
    	    		}
    	    		
    	    		String KOTRMMSMHS = st.nextToken();//11
    	    		String POSRMMSMHS = st.nextToken();//17
    	    		String TELRMMSMHS = st.nextToken();//18
    	    		String ALMKTMSMHS = st.nextToken();//19
    	    		String KOTKTMSMHS = st.nextToken();//20
    	    		String POSKTMSMHS = st.nextToken();//21
    	    		String TELKTMSMHS = st.nextToken();//22
    	    		String JBTKTMSMHS = st.nextToken();//23
    	    		String BIDKTMSMHS = st.nextToken();//24
    	    		String JENKTMSMHS = st.nextToken();//25
    	    		String NMMSPMSMHS = st.nextToken();//26
    	    		String ALMSPMSMHS = st.nextToken();//27
    	    		String POSSPMSMHS = st.nextToken();//28
    	    		String KOTSPMSMHS = st.nextToken();//29
    	    		String NEGSPMSMHS = st.nextToken();//30
    	    		String TELSPMSMHS = st.nextToken();//31
    	    		String NEGLHMSMHS = st.nextToken();//32
    	    		String AGAMAMSMHS = st.nextToken();//33
    	    		String KRKLMMSMHS = st.nextToken();//34
    	    		String TTLOGMSMHS = st.nextToken();//35
    	    		String TMLOGMSMHS = st.nextToken();//36
    	    		String DTLOGMSMHS = st.nextToken();//36
    	    		String IDPAKETBEASISWA = st.nextToken();//37
    	    		String NPM_PA = st.nextToken();//38
    	    		String NMM_PA = st.nextToken();//39
    	    		String PETUGAS = st.nextToken();//40
    	    		String ASAL_SMA = st.nextToken();//41
    	    		String KOTA_SMA = st.nextToken();//42
    	    		String LULUS_SMA = st.nextToken();//43
    	    		
    	    		String NRTRMMSMHS = st.nextToken();//7
    	    		if(Checker.isStringNullOrEmpty(NRTRMMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, NRTRMMSMHS);
    	    		}
    	    		String NRWRMMSMHS = st.nextToken();//8
    	    		if(Checker.isStringNullOrEmpty(NRWRMMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, NRWRMMSMHS);
    	    		}
    	    		String PROVRMMSMHS = st.nextToken();//9
    	    		if(Checker.isStringNullOrEmpty(PROVRMMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, PROVRMMSMHS);
    	    		}
    	    		String PROVRMIDWIL = st.nextToken();//10
    	    		if(Checker.isStringNullOrEmpty(PROVRMIDWIL)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, PROVRMIDWIL);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(KOTRMMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, KOTRMMSMHS);
    	    		}
    	    		String KOTRMIDWIL = st.nextToken();//12
    	    		if(Checker.isStringNullOrEmpty(KOTRMIDWIL)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, KOTRMIDWIL);
    	    		}
    	    		String KECRMMSMHS = st.nextToken();//13
    	    		if(Checker.isStringNullOrEmpty(KECRMMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, KECRMMSMHS);
    	    		}
    	    		String KECRMIDWIL = st.nextToken();//14
    	    		if(Checker.isStringNullOrEmpty(KECRMIDWIL)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, KECRMIDWIL);
    	    		}
    	    		String KELRMMSMHS = st.nextToken();//15
    	    		if(Checker.isStringNullOrEmpty(KELRMMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, KELRMMSMHS);
    	    		}
    	    		String DUSUNMSMHS = st.nextToken();//16
    	    		if(Checker.isStringNullOrEmpty(DUSUNMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, DUSUNMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(POSRMMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, POSRMMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(TELRMMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, TELRMMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(ALMKTMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, ALMKTMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(KOTKTMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, KOTKTMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(POSKTMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, POSKTMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(TELKTMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, TELKTMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(JBTKTMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, JBTKTMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(BIDKTMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, BIDKTMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(JENKTMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, JENKTMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(NMMSPMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, NMMSPMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(ALMSPMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, ALMSPMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(POSSPMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, POSSPMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(KOTSPMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, KOTSPMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(NEGSPMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, NEGSPMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(TELSPMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, TELSPMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(NEGLHMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, NEGLHMSMHS);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(AGAMAMSMHS)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, AGAMAMSMHS);
    	    		}
    	    		
    	    		    	    		
    	    		if(Checker.isStringNullOrEmpty(IDPAKETBEASISWA)) {
    	    			stmt.setLong(i++, java.sql.Types.INTEGER);
    	    		}
    	    		else {
    	    			stmt.setLong(i++, Long.parseLong(IDPAKETBEASISWA));
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(NPM_PA)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, NPM_PA);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(NMM_PA)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, NMM_PA);
    	    		}
    	    		
    	    		//System.out.println("PETUGAS="+PETUGAS);
    	    		if(Checker.isStringNullOrEmpty(PETUGAS)) {
    	    			stmt.setBoolean(i++, false);
    	    			//System.out.println("one");;
    	    		}
    	    		else {
    	    			//System.out.println("two");;
    	    			stmt.setBoolean(i++, Boolean.parseBoolean(PETUGAS));
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(ASAL_SMA)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, ASAL_SMA);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(KOTA_SMA)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, KOTA_SMA);
    	    		}
    	    		
    	    		if(Checker.isStringNullOrEmpty(LULUS_SMA)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, LULUS_SMA);
    	    		}
    	    		
    	    		stmt.executeUpdate();
    	    	}	
    			else {
    				nunpm = null;
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
    	return nunpm;
    }
    
    
    
    public void withdrawCashPadaAcountNpmLama(String old_npmhs, String old_kdpst) {
    	//return npm_baru
    	
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		/*
    		 * get data keu
    		 */
    		stmt = con.prepareStatement("select AMONTPYMNT from PYMNT where NPMHSPYMNT=? and VOIDDPYMNT=?");
    		stmt.setString(1, old_npmhs);
    		stmt.setBoolean(2, false);
    		rs = stmt.executeQuery();
    		double tot_pymnt = 0;
    		while(rs.next()) {
    			tot_pymnt = tot_pymnt+rs.getDouble(1);
    		}
    		if(tot_pymnt>0) {
    			/*
    			 * NORUT &b NOKOD otomatis diproses di dlam funngsi
    			 */
    			insertPymntTable(old_kdpst,old_npmhs,null,"PINDAH PRODI","ROBOT",""+(-tot_pymnt),"N/A","N/A",this.operatorNpm,this.operatorNmm,null);
    			
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
}

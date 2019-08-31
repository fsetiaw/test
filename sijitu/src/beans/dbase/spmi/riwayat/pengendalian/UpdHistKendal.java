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
import java.util.Vector;
import java.util.ListIterator;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class UpdHistKendal
 */
@Stateless
@LocalBean
public class UpdHistKendal extends UpdateDb {
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
    public UpdHistKendal() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdHistKendal(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }

    public int updateRiwayatPengendalianAmi(String id_eval,String tgl, String time, String rasionale, String tindakan, String npm_kendali, String ada_pelanggaran, String jenis_pelanggaran, String next_tgl_survey, String next_waktu_survey) {
    	int updated=0;
    	//System.out.println("ada_pelanggaran="+ada_pelanggaran);
    	//System.out.println("jenis_pelanggaran="+jenis_pelanggaran);
    	if(!Checker.isStringNullOrEmpty(id_eval)) {
    		try {
        		Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	//update next survey di tabel RIWAYAT_EVALUASI_AMI
            	stmt = con.prepareStatement("update RIWAYAT_EVALUASI_AMI set TGL_NEXT_EVAL=?,WAKTU_NEXT_EVAL=? where ID_EVAL=?");
            	if(Checker.isStringNullOrEmpty(next_tgl_survey)) {
            		stmt.setNull(1, java.sql.Types.DATE);
            	}
            	else {
            		try {
            			java.sql.Date dtt=java.sql.Date.valueOf(Converter.autoConvertDateFormat(next_tgl_survey, "-"));
            			stmt.setDate(1, dtt);
            		}
            		catch(Exception e) {
            			stmt.setNull(1, java.sql.Types.DATE);
            		}
            	}
            	if(Checker.isStringNullOrEmpty(next_waktu_survey)) {
            		stmt.setNull(2, java.sql.Types.TIME);
            	}
            	else {
            		stmt.setTime(2, Time.valueOf(next_waktu_survey+":00"));
            	}
            	stmt.setInt(3, Integer.parseInt(id_eval));
            	updated= stmt.executeUpdate();
            	//System.out.println("next_tgl_survey="+next_tgl_survey+" --> "+updated);
            	
            	//coba update dulu
            	String sql = "update ignore RIWAYAT_PENGENDALIAN_AMI set RASIONALE_TINDAKAN=?,TINDAKAN_PENGENDALIAN=?,TGL_KENDALI=?,WAKTU_KENDAL=?,NPM_PENGENDALI=?,PELANGGARAN=?,JENIS_PELANGGARAN=? where ID_EVAL=?";
            	stmt = con.prepareStatement(sql);
            	
            	int i = 1;
            	
            	if(Checker.isStringNullOrEmpty(rasionale)) {
            		stmt.setNull(i++, java.sql.Types.VARCHAR);
            	}
            	else {
            		while(rasionale.contains("\n")) {
            			rasionale = rasionale.replace("\n", "<br>");
            		}
            		stmt.setString(i++, rasionale);
            	}
            	//4.HASIL_ANALISA=?,
            	if(Checker.isStringNullOrEmpty(tindakan)) {
            		stmt.setNull(i++, java.sql.Types.VARCHAR);
            	}
            	else {
            		while(tindakan.contains("\n")) {
            			tindakan = tindakan.replace("\n", "<br>");
            		}
            		stmt.setString(i++, tindakan);
            	}
            	
            	if(Checker.isStringNullOrEmpty(tgl)) {
            		stmt.setNull(i++, java.sql.Types.DATE);
            	}
            	else {
            		stmt.setDate(i++, Converter.formatDateBeforeInsert(tgl));
            	}
            
            	if(Checker.isStringNullOrEmpty(time)) {
            		stmt.setNull(i++, java.sql.Types.TIME);
            	}
            	else {
            		stmt.setTime(i++, Time.valueOf(time+":00"));
            	}
            	if(Checker.isStringNullOrEmpty(npm_kendali)) {
            		stmt.setNull(i++, java.sql.Types.VARCHAR);
            	}
            	else {
            		stmt.setString(i++, npm_kendali);
            	}
            	stmt.setBoolean(i++, Boolean.valueOf(ada_pelanggaran));
            	if(Checker.isStringNullOrEmpty(jenis_pelanggaran)) {
            		stmt.setNull(i++, java.sql.Types.VARCHAR);
            	}
            	else {
            		stmt.setString(i++, jenis_pelanggaran);
            	}
            	
            	stmt.setInt(i++, Integer.parseInt(id_eval));
            	updated = stmt.executeUpdate();
            	
            	if(updated<1) {
            		i=1;
            		sql = "INSERT INTO RIWAYAT_PENGENDALIAN_AMI(ID_EVAL,RASIONALE_TINDAKAN,TINDAKAN_PENGENDALIAN,TGL_KENDALI,WAKTU_KENDAL,NPM_PENGENDALI,PELANGGARAN,JENIS_PELANGGARAN)values(?,?,?,?,?,?,?,?)";
            		stmt = con.prepareStatement(sql);
                	//1.ID_KENDALI,
                	stmt.setInt(i++, Integer.parseInt(id_eval));
                	
                	
                	if(Checker.isStringNullOrEmpty(rasionale)) {
                		stmt.setNull(i++, java.sql.Types.VARCHAR);
                	}
                	else {
                		stmt.setString(i++, rasionale);
                	}
                	//7.HASIL_ANALISA,
                	if(Checker.isStringNullOrEmpty(tindakan)) {
                		stmt.setNull(i++, java.sql.Types.VARCHAR);
                	}
                	else {
                		stmt.setString(i++, tindakan);
                	}
                	//2.TGL_SIDAK,
                	if(Checker.isStringNullOrEmpty(tgl)) {
                		stmt.setNull(i++, java.sql.Types.DATE);
                	}
                	else {
                		stmt.setDate(i++, Converter.formatDateBeforeInsert(tgl));
                	}
                	//WAKTU_SIDAK,
                	if(Checker.isStringNullOrEmpty(time)) {
                		stmt.setNull(i++, java.sql.Types.TIME);
                	}
                	else {
                		stmt.setTime(i++, Time.valueOf(time+":00"));
                	}
                	
                	if(Checker.isStringNullOrEmpty(npm_kendali)) {
                		stmt.setNull(i++, java.sql.Types.VARCHAR);
                	}
                	else {
                		stmt.setString(i++, npm_kendali);
                	}
                	
                	stmt.setBoolean(i++, Boolean.valueOf(ada_pelanggaran));
                	if(Checker.isStringNullOrEmpty(jenis_pelanggaran)) {
                		stmt.setNull(i++, java.sql.Types.VARCHAR);
                	}
                	else {
                		stmt.setString(i++, jenis_pelanggaran);
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
            finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        	    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        	    if (con!=null) try { con.close();} catch (Exception ignore){}
        	}	
    	}
    	return updated;
    }
    
    
    public int updateRiwayatPengendalianAmiLevelUniv(String id_eval,String tgl, String time, String rasionale, String tindakan, String npm_kendali, String ada_pelanggaran, String jenis_pelanggaran, String next_tgl_survey, String next_waktu_survey, Vector v_list_kdpst) {
    	int updated=0;
    	//System.out.println("ada_pelanggaran="+ada_pelanggaran);
    	//System.out.println("jenis_pelanggaran="+jenis_pelanggaran);
    	if(!Checker.isStringNullOrEmpty(id_eval)) {
    		ListIterator li = v_list_kdpst.listIterator();
    		try {
        		Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	//get info eval
            	stmt = con.prepareStatement("select VERSI_ID,STD_ISI_ID,TGL_EVAL,WAKTU_EVAL from RIWAYAT_EVALUASI_AMI where ID_EVAL=?");
            	stmt.setInt(1, Integer.parseInt(id_eval));
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		int versid = rs.getInt(1);
            		int idstdisi =  rs.getInt(2);
            		java.sql.Date tgl_eval = rs.getDate(3);
            		java.sql.Time time_eval = rs.getTime(4);
            		
            		
            		//update next survey di tabel RIWAYAT_EVALUASI_AMI
                	stmt = con.prepareStatement("update RIWAYAT_EVALUASI_AMI set TGL_NEXT_EVAL=?,WAKTU_NEXT_EVAL=? where VERSI_ID=? and STD_ISI_ID=? and TGL_EVAL=? and WAKTU_EVAL=?");
                	if(Checker.isStringNullOrEmpty(next_tgl_survey)) {
                		stmt.setNull(1, java.sql.Types.DATE);
                	}
                	else {
                		try {
                			java.sql.Date dtt=java.sql.Date.valueOf(Converter.autoConvertDateFormat(next_tgl_survey, "-"));
                			stmt.setDate(1, dtt);
                		}
                		catch(Exception e) {
                			stmt.setNull(1, java.sql.Types.DATE);
                		}
                	}
                	if(Checker.isStringNullOrEmpty(next_waktu_survey)) {
                		stmt.setNull(2, java.sql.Types.TIME);
                	}
                	else {
                		stmt.setTime(2, Time.valueOf(next_waktu_survey+":00"));
                	}
                	stmt.setInt(3, versid);
                	stmt.setInt(4, idstdisi);
                	stmt.setDate(5, tgl_eval);
                	stmt.setTime(6, time_eval);
                	updated= stmt.executeUpdate();
                	
                	//get list all_id_eval
                	Vector v_ideval = new Vector();
                	li = v_ideval.listIterator();
                	stmt = con.prepareStatement("select ID_EVAL from RIWAYAT_EVALUASI_AMI where VERSI_ID=? and STD_ISI_ID=? and TGL_EVAL=? and WAKTU_EVAL=?");
                	stmt.setInt(1, versid);
                	stmt.setInt(2, idstdisi);
                	stmt.setDate(3, tgl_eval);
                	stmt.setTime(4, time_eval);
                	rs = stmt.executeQuery();
                	while(rs.next()) {
                		li.add(""+rs.getInt(1));
                	}
                	
                	//update dulu
                	li = v_ideval.listIterator();
                	String sql = "update ignore RIWAYAT_PENGENDALIAN_AMI set RASIONALE_TINDAKAN=?,TINDAKAN_PENGENDALIAN=?,TGL_KENDALI=?,WAKTU_KENDAL=?,NPM_PENGENDALI=?,PELANGGARAN=?,JENIS_PELANGGARAN=? where ID_EVAL=?";
                	stmt = con.prepareStatement(sql);
                	while(li.hasNext()) {
                		String target_id_eval = (String)li.next();
                		
                    	int i = 1;
                    	
                    	if(Checker.isStringNullOrEmpty(rasionale)) {
                    		stmt.setNull(i++, java.sql.Types.VARCHAR);
                    	}
                    	else {
                    		while(rasionale.contains("\n")) {
                    			rasionale = rasionale.replace("\n", "<br>");
                    		}
                    		stmt.setString(i++, rasionale);
                    	}
                    	//4.HASIL_ANALISA=?,
                    	if(Checker.isStringNullOrEmpty(tindakan)) {
                    		stmt.setNull(i++, java.sql.Types.VARCHAR);
                    	}
                    	else {
                    		while(tindakan.contains("\n")) {
                    			tindakan = tindakan.replace("\n", "<br>");
                    		}
                    		stmt.setString(i++, tindakan);
                    	}
                    	
                    	if(Checker.isStringNullOrEmpty(tgl)) {
                    		stmt.setNull(i++, java.sql.Types.DATE);
                    	}
                    	else {
                    		stmt.setDate(i++, Converter.formatDateBeforeInsert(tgl));
                    	}
                    
                    	if(Checker.isStringNullOrEmpty(time)) {
                    		stmt.setNull(i++, java.sql.Types.TIME);
                    	}
                    	else {
                    		stmt.setTime(i++, Time.valueOf(time+":00"));
                    	}
                    	if(Checker.isStringNullOrEmpty(npm_kendali)) {
                    		stmt.setNull(i++, java.sql.Types.VARCHAR);
                    	}
                    	else {
                    		stmt.setString(i++, npm_kendali);
                    	}
                    	stmt.setBoolean(i++, Boolean.valueOf(ada_pelanggaran));
                    	if(Checker.isStringNullOrEmpty(jenis_pelanggaran)) {
                    		stmt.setNull(i++, java.sql.Types.VARCHAR);
                    	}
                    	else {
                    		stmt.setString(i++, jenis_pelanggaran);
                    	}
                    	
                    	stmt.setInt(i++, Integer.parseInt(target_id_eval));
                    	updated = stmt.executeUpdate();
                    	if(updated>0) {
                    		li.remove();
                    	}
                	}
                	//insert sisanya
                	li = v_ideval.listIterator();
                	sql = "INSERT INTO RIWAYAT_PENGENDALIAN_AMI(ID_EVAL,RASIONALE_TINDAKAN,TINDAKAN_PENGENDALIAN,TGL_KENDALI,WAKTU_KENDAL,NPM_PENGENDALI,PELANGGARAN,JENIS_PELANGGARAN)values(?,?,?,?,?,?,?,?)";
            		stmt = con.prepareStatement(sql);
                	while(li.hasNext()) {
                		String target_id_eval = (String)li.next();
                		int i=1;
                		
                    	//1.ID_KENDALI,
                    	stmt.setInt(i++, Integer.parseInt(target_id_eval));
                    	
                    	
                    	if(Checker.isStringNullOrEmpty(rasionale)) {
                    		stmt.setNull(i++, java.sql.Types.VARCHAR);
                    	}
                    	else {
                    		stmt.setString(i++, rasionale);
                    	}
                    	//7.HASIL_ANALISA,
                    	if(Checker.isStringNullOrEmpty(tindakan)) {
                    		stmt.setNull(i++, java.sql.Types.VARCHAR);
                    	}
                    	else {
                    		stmt.setString(i++, tindakan);
                    	}
                    	//2.TGL_SIDAK,
                    	if(Checker.isStringNullOrEmpty(tgl)) {
                    		stmt.setNull(i++, java.sql.Types.DATE);
                    	}
                    	else {
                    		stmt.setDate(i++, Converter.formatDateBeforeInsert(tgl));
                    	}
                    	//WAKTU_SIDAK,
                    	if(Checker.isStringNullOrEmpty(time)) {
                    		stmt.setNull(i++, java.sql.Types.TIME);
                    	}
                    	else {
                    		stmt.setTime(i++, Time.valueOf(time+":00"));
                    	}
                    	
                    	if(Checker.isStringNullOrEmpty(npm_kendali)) {
                    		stmt.setNull(i++, java.sql.Types.VARCHAR);
                    	}
                    	else {
                    		stmt.setString(i++, npm_kendali);
                    	}
                    	
                    	stmt.setBoolean(i++, Boolean.valueOf(ada_pelanggaran));
                    	if(Checker.isStringNullOrEmpty(jenis_pelanggaran)) {
                    		stmt.setNull(i++, java.sql.Types.VARCHAR);
                    	}
                    	else {
                    		stmt.setString(i++, jenis_pelanggaran);
                    	}
                    	updated = stmt.executeUpdate();
                	}	
            	}
            	
            	
            	//System.out.println("next_tgl_survey="+next_tgl_survey+" --> "+updated);
            	
            	//coba update dulu
            	
            	
            	//if(updated<1) {
            		
            	//}
            	
            	
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
    
    public int updateRiwayatPengendalianUmum(String id_plan,String id_versi,String id_std,String tgl_sta,String waktu_sta,String nama_kegiatan,String jenis_kegiatan,String tujuan_kegiatan,String isi_kegiatan,String[] job_jawab,String[]job_target,String[]dok_kegiatan,String status_kegiatan_sdh_mulai) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//coba update dulu
        	if(Checker.isStringNullOrEmpty(id_plan)) {
        		//System.out.println("insert mode--");
        		String sql = "INSERT INTO RIWAYAT_PENGENDALIAN_UMUM(VERSI_ID,ID_STD,TGL_STA_KEGIATAN,WAKTU_STA_KEGIATAN,TIMESTAMP_KEGIATAN_STA,NAMA_KEGIATAN,JENIS_KEGIATAN,KETERANGAN_SINGKAT_TUJUAN_KEGIATAN,ISI_KEGIATAN,PENANGGUNG_JAWAB_KEGIATAN,AUDIENCE_KEGIATAN,DOKUMEN_KEGIATAN,KEGIATAN_STARTED)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        		stmt = con.prepareStatement(sql);
        		int i=1;
        		//VERSI_ID
        		stmt.setInt(i++, Integer.parseInt(id_versi));
        		//ID_STD
        		stmt.setInt(i++, Integer.parseInt(id_std));
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
        		//System.out.println("update mode--"); 
        		//String sql = "UPDATE RIWAYAT_EVALUASI set TGL_STA_KEGIATAN=?,WAKTU_STA_KEGIATAN=?,TIMESTAMP_KEGIATAN_STA=?,NAMA_KEGIATAN=?,JENIS_KEGIATAN=?,KETERANGAN_SINGKAT_TUJUAN_KEGIATAN=?,ISI_KEGIATAN=?,PENANGGUNG_JAWAB_KEGIATAN=?,AUDIENCE_KEGIATAN=?,DOKUMEN_KEGIATAN=?,KEGIATAN_STARTED=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?";
        		String sql = "UPDATE RIWAYAT_PENGENDALIAN_UMUM set TGL_STA_KEGIATAN=?,WAKTU_STA_KEGIATAN=?,TIMESTAMP_KEGIATAN_STA=?,NAMA_KEGIATAN=?,JENIS_KEGIATAN=?,KETERANGAN_SINGKAT_TUJUAN_KEGIATAN=?,ISI_KEGIATAN=?,PENANGGUNG_JAWAB_KEGIATAN=?,AUDIENCE_KEGIATAN=?,DOKUMEN_KEGIATAN=?,KEGIATAN_STARTED=? where ID_PLAN=?";
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
    
    public int updateRiwayatPengendalian(String id_plan,String id_versi,String id_std_isi,String norut_man,String tgl_sta,String waktu_sta,String nama_kegiatan,String jenis_kegiatan,String tujuan_kegiatan,String isi_kegiatan,String[] job_jawab,String[]job_target,String[]dok_kegiatan,String status_kegiatan_sdh_mulai) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//coba update dulu
        	if(Checker.isStringNullOrEmpty(id_plan)) {
        		//System.out.println("insert mode--");
        		String sql = "INSERT INTO RIWAYAT_PENGENDALIAN(VERSI_ID,STD_ISI_ID,NORUT,TGL_STA_KEGIATAN,WAKTU_STA_KEGIATAN,TIMESTAMP_KEGIATAN_STA,NAMA_KEGIATAN,JENIS_KEGIATAN,KETERANGAN_SINGKAT_TUJUAN_KEGIATAN,ISI_KEGIATAN,PENANGGUNG_JAWAB_KEGIATAN,AUDIENCE_KEGIATAN,DOKUMEN_KEGIATAN,KEGIATAN_STARTED)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
        		//System.out.println("update mode--"); 
        		//String sql = "UPDATE RIWAYAT_PENGENDALIAN set TGL_STA_KEGIATAN=?,WAKTU_STA_KEGIATAN=?,TIMESTAMP_KEGIATAN_STA=?,NAMA_KEGIATAN=?,JENIS_KEGIATAN=?,KETERANGAN_SINGKAT_TUJUAN_KEGIATAN=?,ISI_KEGIATAN=?,PENANGGUNG_JAWAB_KEGIATAN=?,AUDIENCE_KEGIATAN=?,DOKUMEN_KEGIATAN=?,KEGIATAN_STARTED=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?";
        		String sql = "UPDATE RIWAYAT_PENGENDALIAN set TGL_STA_KEGIATAN=?,WAKTU_STA_KEGIATAN=?,TIMESTAMP_KEGIATAN_STA=?,NAMA_KEGIATAN=?,JENIS_KEGIATAN=?,KETERANGAN_SINGKAT_TUJUAN_KEGIATAN=?,ISI_KEGIATAN=?,PENANGGUNG_JAWAB_KEGIATAN=?,AUDIENCE_KEGIATAN=?,DOKUMEN_KEGIATAN=?,KEGIATAN_STARTED=? where ID_PLAN=?";
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
    
    
    
    public int deleteOneSurvey(String id_hist) {
    	int updated=0;
    	if(!Checker.isStringNullOrEmpty(id_hist)) {
    		try {
    			int int_id_hist = Integer.parseInt(id_hist);
        		Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	//coba update dulu
            	stmt = con.prepareStatement("delete from STANDARD_RIWAYAT_PENGENDALIAN where ID_HIST=?");
            	stmt.setInt(1, int_id_hist);
            	updated = stmt.executeUpdate();
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
    	}
    	return updated;
    }
    
    
    public int updateRiwayatHasilPengendalian_v1(String id_plan, String hasil, String note, String tgl_end, String waktu_end, String sebagai_tgl_penetapan, String versi_id, String id_std) {
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
        		String sql = "UPDATE RIWAYAT_PENGENDALIAN_UMUM set KETERANGAN_HASIL_KEGIATAN=?,NOTE=?,TGL_END_KEGIATAN=?,WAKTU_END_KEGIATAN=?,TIMESTAMP_KEGIATAN_END=?,KEGIATAN_SELESAI=?,TGL_RUMUS_STD=?,TGL_CEK_STD=?,TGL_STUJU_STD=?,TGL_TETAP_STD=?,TGL_KENDALI_STD=? where ID_PLAN=?";
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
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN_UMUM set TGL_RUMUS_STD=? where VERSI_ID=? and ID_STD=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(id_std));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("cek_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN_UMUM set TGL_CEK_STD=? where VERSI_ID=? and ID_STD=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(id_std));
        				
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("stuju_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN_UMUM set TGL_STUJU_STD=? where VERSI_ID=? and ID_STD=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(id_std));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("tetap_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN_UMUM set TGL_TETAP_STD=? where VERSI_ID=? and ID_STD=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(id_std));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("kendali_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN_UMUM set TGL_KENDALI_STD=? where VERSI_ID=? and ID_STD=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(id_std));
        			}
            		stmt.executeUpdate();	
            	}
        	}
        	else {
        		// UPDATE TGL MANUAL
        		String sql = "UPDATE RIWAYAT_PENGENDALIAN_UMUM set KETERANGAN_HASIL_KEGIATAN=?,NOTE=?,TGL_END_KEGIATAN=?,WAKTU_END_KEGIATAN=?,TIMESTAMP_KEGIATAN_END=?,KEGIATAN_SELESAI=?,TGL_RUMUS=?,TGL_CEK=?,TGL_STUJU=?,TGL_TETAP=?,TGL_KENDALI=? where ID_PLAN=?";
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
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN_UMUM set TGL_RUMUS=? where VERSI_ID=? and ID_STD=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(id_std));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("cek")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN_UMUM set TGL_CEK=? where VERSI_ID=? and ID_STD=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(id_std));
        				
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("stuju")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN_UMUM set TGL_STUJU=? where VERSI_ID=? and ID_STD=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(id_std));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("tetap")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN_UMUM set TGL_TETAP=? where VERSI_ID=? and ID_STD=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(id_std));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("kendali")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN_UMUM set TGL_KENDALI=? where VERSI_ID=? and ID_STD=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(id_std));
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
    
    public int updateRiwayatHasilPengendalian(String id_plan, String hasil, String note, String tgl_end, String waktu_end, String sebagai_tgl_penetapan, String versi_id, String std_isi_id, String norut_man) {
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
        		String sql = "UPDATE RIWAYAT_PENGENDALIAN set KETERANGAN_HASIL_KEGIATAN=?,NOTE=?,TGL_END_KEGIATAN=?,WAKTU_END_KEGIATAN=?,TIMESTAMP_KEGIATAN_END=?,KEGIATAN_SELESAI=?,TGL_RUMUS_STD=?,TGL_CEK_STD=?,TGL_STUJU_STD=?,TGL_TETAP_STD=?,TGL_KENDALI_STD=? where ID_PLAN=?";
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
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN set TGL_RUMUS_STD=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        				stmt.setInt(i++, Integer.parseInt(norut_man));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("cek_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN set TGL_CEK_STD=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        				
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("stuju_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN set TGL_STUJU_STD=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("tetap_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN set TGL_TETAP_STD=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("kendali_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN set TGL_KENDALI_STD=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        			}
            		stmt.executeUpdate();	
            	}
        	}
        	else {
        		// UPDATE TGL MANUAL
        		String sql = "UPDATE RIWAYAT_PENGENDALIAN set KETERANGAN_HASIL_KEGIATAN=?,NOTE=?,TGL_END_KEGIATAN=?,WAKTU_END_KEGIATAN=?,TIMESTAMP_KEGIATAN_END=?,KEGIATAN_SELESAI=?,TGL_RUMUS=?,TGL_CEK=?,TGL_STUJU=?,TGL_TETAP=?,TGL_KENDALI=? where ID_PLAN=?";
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
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN set TGL_RUMUS=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        				stmt.setInt(i++, Integer.parseInt(norut_man));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("cek")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN set TGL_CEK=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        				
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("stuju")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN set TGL_STUJU=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("tetap")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN set TGL_TETAP=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("kendali")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_PENGENDALIAN set TGL_KENDALI=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
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

package beans.dbase.spmi.riwayat.pengendalian;

import beans.dbase.UpdateDb;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Tool;
import java.util.Vector;
import java.util.ListIterator;
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
 * Session Bean implementation class UpdHistEval
 */
@Stateless
@LocalBean
public class UpdHistEval extends UpdateDb {
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
    public UpdHistEval() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdHistEval(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }

    public int updateRiwayatPengendalian(String id_kendali,String tgl, String time, String periode_ke, double nilai_ril, String sikon, String analisa, String rekomendasi, String npm_pengawas, String target_val, String unit_used, String kdpst_kendali, String id_sarpras, String ket_tipe_sarpras, String target_civitas_npm, String next_tgl_survey, String next_time_survey) {
    	int updated=0;
    	if(!Checker.isStringNullOrEmpty(id_kendali)) {
    		try {
        		Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	//coba update dulu
            	stmt = con.prepareStatement("update ignore STANDARD_RIWAYAT_PENGENDALIAN set PERIODE_SIDAK=?,NILAI_CAPAIAN_RIL_SURVEY=?,SIKON_AT_SIDAK=?,HASIL_ANALISA=?,REKOMENDASI=?,NPM_PENGAWAS=?,NILAI_TARGET_CAPAIAN=?,UNIT_USED_BY_TARGET=?,NPM_CIVITAS_YG_DIAWASI=?,KET_TIPE_SARPRAS=?,ID_SARPRAS=?,KDPST_KENDALI=?,NEXT_TGL_SIDAK=?,NEXT_WAKTU_SIDAK=? where ID_KENDALI=? and TGL_SIDAK=? and WAKTU_SIDAK=?");
            	int i = 1;
            	//1.PERIODE_SIDAK=?,
            	if(Checker.isStringNullOrEmpty(periode_ke)) {
            		stmt.setNull(i++, java.sql.Types.INTEGER);
            	}
            	else {
            		stmt.setInt(i++, Integer.parseInt(periode_ke));
            	}
            	//2.NILAI_CAPAIAN_RIL_SURVEY=?,
            	stmt.setDouble(i++, nilai_ril);
            	//3.SIKON_AT_SIDAK=?,
            	if(Checker.isStringNullOrEmpty(sikon)) {
            		stmt.setNull(i++, java.sql.Types.VARCHAR);
            	}
            	else {
            		while(sikon.contains("\n")) {
            			sikon = sikon.replace("\n", "<br>");
            		}
            		stmt.setString(i++, sikon);
            	}
            	//4.HASIL_ANALISA=?,
            	if(Checker.isStringNullOrEmpty(analisa)) {
            		stmt.setNull(i++, java.sql.Types.VARCHAR);
            	}
            	else {
            		while(analisa.contains("\n")) {
            			analisa = analisa.replace("\n", "<br>");
            		}
            		stmt.setString(i++, analisa);
            	}
            	//5.REKOMENDASI=?,
            	if(Checker.isStringNullOrEmpty(rekomendasi)) {
            		stmt.setNull(i++, java.sql.Types.VARCHAR);
            	}
            	else {
            		while(rekomendasi.contains("\n")) {
            			rekomendasi = rekomendasi.replace("\n", "<br>");
            		}
            		stmt.setString(i++, rekomendasi);
            	}
            	//6.NPM_PENGAWAS=? where 
            	if(Checker.isStringNullOrEmpty(npm_pengawas)) {
            		stmt.setNull(i++, java.sql.Types.VARCHAR);
            	}
            	else {
            		stmt.setString(i++, npm_pengawas);
            	}
            	//7.target nili 
            	if(Checker.isStringNullOrEmpty(target_val)) {
            		stmt.setDouble(i++, java.sql.Types.DOUBLE);
            	}
            	else {
            		stmt.setDouble(i++, Double.parseDouble(target_val));
            	}
            	//8.NPM_PENGAWAS=? where 
            	if(Checker.isStringNullOrEmpty(unit_used)) {
            		stmt.setNull(i++, java.sql.Types.VARCHAR);
            	}
            	else {
            		stmt.setString(i++, unit_used);
            	}
            	//9 NPM_CIVITAS_YG_DIAWASI
            	if(Checker.isStringNullOrEmpty(target_civitas_npm)) {
            		stmt.setNull(i++, java.sql.Types.VARCHAR);
            	}
            	else {
            		stmt.setString(i++, target_civitas_npm);
            	}
            	//10 KET_TIPE_SARPRAS
            	if(Checker.isStringNullOrEmpty(ket_tipe_sarpras)) {
            		stmt.setNull(i++, java.sql.Types.VARCHAR);
            	}
            	else {
            		stmt.setString(i++, ket_tipe_sarpras);
            	}
            	//11 id_SARPRAS
            	if(Checker.isStringNullOrEmpty(id_sarpras)) {
            		stmt.setNull(i++, java.sql.Types.INTEGER);
            	}
            	else {
            		try {
            			int tmp_id = Integer.parseInt(id_sarpras);
            			stmt.setInt(i++, tmp_id);
            		}
            		catch(Exception e) {
            			stmt.setNull(i++, java.sql.Types.INTEGER);
            		}
            	}
            	//11 KDPST_KENDALI
            	if(Checker.isStringNullOrEmpty(kdpst_kendali)) {
            		stmt.setNull(i++, java.sql.Types.VARCHAR);
            	}
            	else {
            		stmt.setString(i++, kdpst_kendali);
            	}
            	//12.NEXT_TGL_SIDAK=? and 
            	if(Checker.isStringNullOrEmpty(next_tgl_survey)) {
            		stmt.setNull(i++, java.sql.Types.DATE);
            	}
            	else {
            		stmt.setDate(i++, Converter.formatDateBeforeInsert(next_tgl_survey));
            	}
            	//13.NEXT_WAKTU_SIDAK=?");
            	if(Checker.isStringNullOrEmpty(next_time_survey)) {
            		stmt.setNull(i++, java.sql.Types.TIME);
            	}
            	else {
            		stmt.setTime(i++, Time.valueOf(next_time_survey+":00"));
            	}
            	
            	
            	
            	//14.ID_KENDALI=? and 
            	stmt.setInt(i++, Integer.parseInt(id_kendali));
            	//15.TGL_SIDAK=? and 
            	if(Checker.isStringNullOrEmpty(tgl)) {
            		stmt.setNull(i++, java.sql.Types.DATE);
            	}
            	else {
            		stmt.setDate(i++, Converter.formatDateBeforeInsert(tgl));
            	}
            	//16.WAKTU_SIDAK=?");
            	if(Checker.isStringNullOrEmpty(time)) {
            		stmt.setNull(i++, java.sql.Types.TIME);
            	}
            	else {
            		stmt.setTime(i++, Time.valueOf(time+":00"));
            	}
            	updated = stmt.executeUpdate();
            	
            	if(updated<1) {
            		i=1;
            		stmt = con.prepareStatement("INSERT INTO STANDARD_RIWAYAT_PENGENDALIAN(ID_KENDALI,TGL_SIDAK,WAKTU_SIDAK,PERIODE_SIDAK,NILAI_CAPAIAN_RIL_SURVEY,SIKON_AT_SIDAK,HASIL_ANALISA,REKOMENDASI,NPM_PENGAWAS,NILAI_TARGET_CAPAIAN,UNIT_USED_BY_TARGET,NPM_CIVITAS_YG_DIAWASI,KET_TIPE_SARPRAS,ID_SARPRAS,KDPST_KENDALI,NEXT_TGL_SIDAK,NEXT_WAKTU_SIDAK)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                	//1.ID_KENDALI,
                	stmt.setInt(i++, Integer.parseInt(id_kendali));
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
                	//4.PERIODE_SIDAK,
                	if(Checker.isStringNullOrEmpty(periode_ke)) {
                		stmt.setNull(i++, java.sql.Types.INTEGER);
                	}
                	else {
                		stmt.setInt(i++, Integer.parseInt(periode_ke));
                	}
                	//5.NILAI_CAPAIAN_RIL_SURVEY,
                	stmt.setDouble(i++, nilai_ril);
                	//6.SIKON_AT_SIDAK,
                	if(Checker.isStringNullOrEmpty(sikon)) {
                		stmt.setNull(i++, java.sql.Types.VARCHAR);
                	}
                	else {
                		stmt.setString(i++, sikon);
                	}
                	//7.HASIL_ANALISA,
                	if(Checker.isStringNullOrEmpty(analisa)) {
                		stmt.setNull(i++, java.sql.Types.VARCHAR);
                	}
                	else {
                		stmt.setString(i++, analisa);
                	}
                	//8.REKOMENDASI,
                	if(Checker.isStringNullOrEmpty(rekomendasi)) {
                		stmt.setNull(i++, java.sql.Types.VARCHAR);
                	}
                	else {
                		stmt.setString(i++, rekomendasi);
                	}
                	//9.NPM_PENGAWAS
                	if(Checker.isStringNullOrEmpty(npm_pengawas)) {
                		stmt.setNull(i++, java.sql.Types.VARCHAR);
                	}
                	else {
                		stmt.setString(i++, npm_pengawas);
                	}
                	//10.NPM_PENGAWAS
                	if(Checker.isStringNullOrEmpty(target_val)) {
                		stmt.setNull(i++, java.sql.Types.DOUBLE);
                	}
                	else {
                		stmt.setDouble(i++, Double.parseDouble(target_val));
                	}
                	//11.NPM_PENGAWAS
                	if(Checker.isStringNullOrEmpty(unit_used)) {
                		stmt.setNull(i++, java.sql.Types.VARCHAR);
                	}
                	else {
                		stmt.setString(i++, unit_used);
                	}
                	//12 NPM_CIVITAS_YG_DIAWASI
                	if(Checker.isStringNullOrEmpty(target_civitas_npm)) {
                		stmt.setNull(i++, java.sql.Types.VARCHAR);
                	}
                	else {
                		stmt.setString(i++, target_civitas_npm);
                	}
                	//13 KET_TIPE_SARPRAS
                	if(Checker.isStringNullOrEmpty(ket_tipe_sarpras)) {
                		stmt.setNull(i++, java.sql.Types.VARCHAR);
                	}
                	else {
                		stmt.setString(i++, ket_tipe_sarpras);
                	}
                	//14 id_SARPRAS
                	if(Checker.isStringNullOrEmpty(id_sarpras)) {
                		stmt.setNull(i++, java.sql.Types.INTEGER);
                	}
                	else {
                		try {
                			int tmp_id = Integer.parseInt(id_sarpras);
                			stmt.setInt(i++, tmp_id);
                		}
                		catch(Exception e) {
                			stmt.setNull(i++, java.sql.Types.INTEGER);
                		}
                	}
                	//15 KDPST_KENDALI
                	if(Checker.isStringNullOrEmpty(kdpst_kendali)) {
                		stmt.setNull(i++, java.sql.Types.VARCHAR);
                	}
                	else {
                		stmt.setString(i++, kdpst_kendali);
                	}
                	//16.next_TGL_SIDAK,
                	if(Checker.isStringNullOrEmpty(next_tgl_survey)) {
                		stmt.setNull(i++, java.sql.Types.DATE);
                	}
                	else {
                		stmt.setDate(i++, Converter.formatDateBeforeInsert(next_tgl_survey));
                	}
                	//next_WAKTU_SIDAK,
                	if(Checker.isStringNullOrEmpty(next_time_survey)) {
                		stmt.setNull(i++, java.sql.Types.TIME);
                	}
                	else {
                		stmt.setTime(i++, Time.valueOf(next_time_survey+":00"));
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
    
    
    
    public int updateRiwayatEvaluasiAmi(String id_versi, String id_std_isi, String norut_man, String npm_eval, String tgl, String waktu, String sikon, String analisa, String rekomendasi, String next_tgl_eval, String next_waktu_eval, String target_value, String ril_value, String kdpst) {
    	
    	//System.out.println("="+);
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	
        	int i=1;
        	String sql = "INSERT INTO RIWAYAT_EVALUASI_AMI(VERSI_ID,STD_ISI_ID,NORUT,NPM_EVALUATOR,TGL_EVAL,WAKTU_EVAL,KONDISI,ANALISA,REKOMENDASI,TGL_NEXT_EVAL,WAKTU_NEXT_EVAL,TARGET_VALUE,REAL_VALUE,KDPST)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    		stmt = con.prepareStatement(sql);
    		//VERSI_ID
    		stmt.setInt(i++, Integer.parseInt(id_versi));
    		//STD_ISI_ID
    		stmt.setInt(i++, Integer.parseInt(id_std_isi));
    		//NORUT
    		stmt.setInt(i++, Integer.parseInt(norut_man));
    		//NPM_EVALUATOR
    		stmt.setString(i++, npm_eval);
    		//TGL_EVAL
    		tgl = Converter.autoConvertDateFormat(tgl, "-");
    		//System.out.println("tgl="+tgl);
    		stmt.setDate(i++,java.sql.Date.valueOf(tgl));
    		//WAKTU_EVAL
    		if(Checker.isStringNullOrEmpty(waktu)) {
        		stmt.setNull(i++, java.sql.Types.TIME);
        	}
        	else {
        		stmt.setTime(i++, Time.valueOf(waktu+":00"));
        	}

    		//KONDISI
    		if(Checker.isStringNullOrEmpty(sikon)) {
    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    		}
    		else {
    			while(sikon.contains("\n")) {
    				sikon = sikon.replace("\n", "<br>");
    			}
    			sikon = sikon.trim();
    			stmt.setString(i++, sikon);
    		}
    		//ANALISA
    		if(Checker.isStringNullOrEmpty(analisa)) {
    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    		}
    		else {
    			while(analisa.contains("\n")) {
    				analisa = analisa.replace("\n", "<br>");
    			}
    			analisa = analisa.trim();
    			stmt.setString(i++, analisa);
    		}
    		//REKOMENDASI
    		if(Checker.isStringNullOrEmpty(rekomendasi)) {
    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    		}
    		else {
    			while(rekomendasi.contains("\n")) {
    				rekomendasi = rekomendasi.replace("\n", "<br>");
    			}
    			sikon = sikon.trim();
    			stmt.setString(i++, sikon);
    		}
    		//TGL_NEXT_EVAL
    		next_tgl_eval = Converter.autoConvertDateFormat(next_tgl_eval, "-");
    		//System.out.println("next_tgl_eval="+next_tgl_eval);
    		stmt.setDate(i++,java.sql.Date.valueOf(next_tgl_eval));
    		//WAKTU_NEXT_EVAL
    		if(Checker.isStringNullOrEmpty(next_waktu_eval)) {
        		stmt.setNull(i++, java.sql.Types.TIME);
        	}
        	else {
        		stmt.setTime(i++, Time.valueOf(next_waktu_eval+":00"));
        	}
    		//TARGET_VALUE
    		stmt.setString(i++, target_value);
    		//REAL_VALUE
    		stmt.setString(i++, ril_value);
    		//kdpst
    		stmt.setString(i++, kdpst);
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
    
    
    
     
    //public int updateRiwayatEvaluasiAmi_v1(String id_versi, String id_std_isi, String npm_eval, String tgl, String waktu, String sikon, String analisa, String rekomendasi, String next_tgl_eval, String next_waktu_eval, String target_value, String ril_value, String kdpst) {
    public int updateRiwayatEvaluasiAmi_v1(String id_versi, String id_std_isi, String npm_eval, String tgl, String waktu, String sikon, String analisa, String rekomendasi, String target_value, String ril_value, String kdpst) {	
    	//System.out.println("="+);
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//get norut terakhir
        	int norut=0;
        	stmt = con.prepareStatement("select count(VERSI_ID) from  RIWAYAT_EVALUASI_AMI where VERSI_ID=? and STD_ISI_ID=?");
        	//VERSI_ID
    		stmt.setInt(1, Integer.parseInt(id_versi));
    		//STD_ISI_ID
    		stmt.setInt(2, Integer.parseInt(id_std_isi));
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			norut = rs.getInt(1);
    		}
    		norut++;
        	int i=1;
        	//String sql = "INSERT INTO RIWAYAT_EVALUASI_AMI(VERSI_ID,STD_ISI_ID,NORUT,NPM_EVALUATOR,TGL_EVAL,WAKTU_EVAL,KONDISI,ANALISA,REKOMENDASI,TGL_NEXT_EVAL,WAKTU_NEXT_EVAL,TARGET_VALUE,REAL_VALUE,KDPST)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        	String sql = "INSERT INTO RIWAYAT_EVALUASI_AMI(VERSI_ID,STD_ISI_ID,NORUT,NPM_EVALUATOR,TGL_EVAL,WAKTU_EVAL,KONDISI,ANALISA,REKOMENDASI,TARGET_VALUE,REAL_VALUE,KDPST)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
    		stmt = con.prepareStatement(sql);
    		//VERSI_ID
    		stmt.setInt(i++, Integer.parseInt(id_versi));
    		//STD_ISI_ID
    		stmt.setInt(i++, Integer.parseInt(id_std_isi));
    		//NORUT
    		stmt.setInt(i++, norut);
    		//NPM_EVALUATOR
    		stmt.setString(i++, npm_eval);
    		//TGL_EVAL
    		tgl = Converter.autoConvertDateFormat(tgl, "-");
    		//System.out.println("tgl="+tgl);
    		stmt.setDate(i++,java.sql.Date.valueOf(tgl));
    		//WAKTU_EVAL
    		if(Checker.isStringNullOrEmpty(waktu)) {
        		stmt.setNull(i++, java.sql.Types.TIME);
        	}
        	else {
        		stmt.setTime(i++, Time.valueOf(waktu+":00"));
        	}

    		//KONDISI
    		if(Checker.isStringNullOrEmpty(sikon)) {
    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    		}
    		else {
    			while(sikon.contains("\n")) {
    				sikon = sikon.replace("\n", "<br>");
    			}
    			sikon = sikon.trim();
    			stmt.setString(i++, sikon);
    		}
    		//ANALISA
    		if(Checker.isStringNullOrEmpty(analisa)) {
    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    		}
    		else {
    			while(analisa.contains("\n")) {
    				analisa = analisa.replace("\n", "<br>");
    			}
    			analisa = analisa.trim();
    			stmt.setString(i++, analisa);
    		}
    		//REKOMENDASI
    		if(Checker.isStringNullOrEmpty(rekomendasi)) {
    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    		}
    		else {
    			while(rekomendasi.contains("\n")) {
    				rekomendasi = rekomendasi.replace("\n", "<br>");
    			}
    			rekomendasi = rekomendasi.trim();
    			stmt.setString(i++, rekomendasi);
    		}
    		/*TGL_NEXT_EVAL
    		next_tgl_eval = Converter.autoConvertDateFormat(next_tgl_eval, "-");
    		//System.out.println("next_tgl_eval="+next_tgl_eval);
    		stmt.setDate(i++,java.sql.Date.valueOf(next_tgl_eval));
    		//WAKTU_NEXT_EVAL
    		if(Checker.isStringNullOrEmpty(next_waktu_eval)) {
        		stmt.setNull(i++, java.sql.Types.TIME);
        	}
        	else {
        		stmt.setTime(i++, Time.valueOf(next_waktu_eval+":00"));
        	}
        	*/
    		//TARGET_VALUE
    		stmt.setString(i++, target_value);
    		//REAL_VALUE
    		stmt.setString(i++, ril_value);
    		//kdpst
    		stmt.setString(i++, kdpst);
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
    
    
    public int updateRiwayatEvaluasiAmiLevelUniv_v1(String id_versi, String id_std_isi, String npm_eval, String tgl, String waktu, String sikon, String analisa, String rekomendasi, String target_value, String ril_value, Vector v_list_kdpst) {	
    	//System.out.println("="+);
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//get norut terakhir
        	int norut=0;
        	stmt = con.prepareStatement("select count(VERSI_ID) from  RIWAYAT_EVALUASI_AMI where VERSI_ID=? and STD_ISI_ID=?");
        	//VERSI_ID
    		stmt.setInt(1, Integer.parseInt(id_versi));
    		//STD_ISI_ID
    		stmt.setInt(2, Integer.parseInt(id_std_isi));
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			norut = rs.getInt(1);
    		}
    		norut++;
        	
        	//String sql = "INSERT INTO RIWAYAT_EVALUASI_AMI(VERSI_ID,STD_ISI_ID,NORUT,NPM_EVALUATOR,TGL_EVAL,WAKTU_EVAL,KONDISI,ANALISA,REKOMENDASI,TGL_NEXT_EVAL,WAKTU_NEXT_EVAL,TARGET_VALUE,REAL_VALUE,KDPST)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        	String sql = "INSERT INTO RIWAYAT_EVALUASI_AMI(VERSI_ID,STD_ISI_ID,NORUT,NPM_EVALUATOR,TGL_EVAL,WAKTU_EVAL,KONDISI,ANALISA,REKOMENDASI,TARGET_VALUE,REAL_VALUE,KDPST)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
    		stmt = con.prepareStatement(sql);
    		ListIterator li = v_list_kdpst.listIterator();
    		while(li.hasNext()) {
    			int i=1;
    			String kdpst = (String)li.next();
    			//VERSI_ID
        		stmt.setInt(i++, Integer.parseInt(id_versi));
        		//STD_ISI_ID
        		stmt.setInt(i++, Integer.parseInt(id_std_isi));
        		//NORUT
        		stmt.setInt(i++, norut);
        		//NPM_EVALUATOR
        		stmt.setString(i++, npm_eval);
        		//TGL_EVAL
        		tgl = Converter.autoConvertDateFormat(tgl, "-");
        		//System.out.println("tgl="+tgl);
        		stmt.setDate(i++,java.sql.Date.valueOf(tgl));
        		//WAKTU_EVAL
        		if(Checker.isStringNullOrEmpty(waktu)) {
            		stmt.setNull(i++, java.sql.Types.TIME);
            	}
            	else {
            		stmt.setTime(i++, Time.valueOf(waktu+":00"));
            	}

        		//KONDISI
        		if(Checker.isStringNullOrEmpty(sikon)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			while(sikon.contains("\n")) {
        				sikon = sikon.replace("\n", "<br>");
        			}
        			sikon = sikon.trim();
        			stmt.setString(i++, sikon);
        		}
        		//ANALISA
        		if(Checker.isStringNullOrEmpty(analisa)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			while(analisa.contains("\n")) {
        				analisa = analisa.replace("\n", "<br>");
        			}
        			analisa = analisa.trim();
        			stmt.setString(i++, analisa);
        		}
        		//REKOMENDASI
        		if(Checker.isStringNullOrEmpty(rekomendasi)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			while(rekomendasi.contains("\n")) {
        				rekomendasi = rekomendasi.replace("\n", "<br>");
        			}
        			sikon = sikon.trim();
        			stmt.setString(i++, sikon);
        		}
        		/*TGL_NEXT_EVAL
        		next_tgl_eval = Converter.autoConvertDateFormat(next_tgl_eval, "-");
        		//System.out.println("next_tgl_eval="+next_tgl_eval);
        		stmt.setDate(i++,java.sql.Date.valueOf(next_tgl_eval));
        		//WAKTU_NEXT_EVAL
        		if(Checker.isStringNullOrEmpty(next_waktu_eval)) {
            		stmt.setNull(i++, java.sql.Types.TIME);
            	}
            	else {
            		stmt.setTime(i++, Time.valueOf(next_waktu_eval+":00"));
            	}
            	*/
        		//TARGET_VALUE
        		stmt.setString(i++, target_value);
        		//REAL_VALUE
        		stmt.setString(i++, ril_value);
        		//kdpst
        		stmt.setString(i++, kdpst);
            	updated = updated+stmt.executeUpdate();
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
    
    
    public int updateRiwayatEvaluasi(String id_plan,String id_versi,String id_std_isi,String norut_man,String tgl_sta,String waktu_sta,String nama_kegiatan,String jenis_kegiatan,String tujuan_kegiatan,String isi_kegiatan,String[] job_jawab,String[]job_target,String[]dok_kegiatan,String status_kegiatan_sdh_mulai) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//coba update dulu
        	if(Checker.isStringNullOrEmpty(id_plan)) {
        		//System.out.println("insert mode--");
        		String sql = "INSERT INTO RIWAYAT_EVALUASI(VERSI_ID,STD_ISI_ID,NORUT,TGL_STA_KEGIATAN,WAKTU_STA_KEGIATAN,TIMESTAMP_KEGIATAN_STA,NAMA_KEGIATAN,JENIS_KEGIATAN,KETERANGAN_SINGKAT_TUJUAN_KEGIATAN,ISI_KEGIATAN,PENANGGUNG_JAWAB_KEGIATAN,AUDIENCE_KEGIATAN,DOKUMEN_KEGIATAN,KEGIATAN_STARTED)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
        		//String sql = "UPDATE RIWAYAT_EVALUASI set TGL_STA_KEGIATAN=?,WAKTU_STA_KEGIATAN=?,TIMESTAMP_KEGIATAN_STA=?,NAMA_KEGIATAN=?,JENIS_KEGIATAN=?,KETERANGAN_SINGKAT_TUJUAN_KEGIATAN=?,ISI_KEGIATAN=?,PENANGGUNG_JAWAB_KEGIATAN=?,AUDIENCE_KEGIATAN=?,DOKUMEN_KEGIATAN=?,KEGIATAN_STARTED=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?";
        		String sql = "UPDATE RIWAYAT_EVALUASI set TGL_STA_KEGIATAN=?,WAKTU_STA_KEGIATAN=?,TIMESTAMP_KEGIATAN_STA=?,NAMA_KEGIATAN=?,JENIS_KEGIATAN=?,KETERANGAN_SINGKAT_TUJUAN_KEGIATAN=?,ISI_KEGIATAN=?,PENANGGUNG_JAWAB_KEGIATAN=?,AUDIENCE_KEGIATAN=?,DOKUMEN_KEGIATAN=?,KEGIATAN_STARTED=? where ID_PLAN=?";
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
    
    public int updateRiwayatEvaluasiUmum(String id_plan,String id_versi,String id_std,String tgl_sta,String waktu_sta,String nama_kegiatan,String jenis_kegiatan,String tujuan_kegiatan,String isi_kegiatan,String[] job_jawab,String[]job_target,String[]dok_kegiatan,String status_kegiatan_sdh_mulai) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//coba update dulu
        	if(Checker.isStringNullOrEmpty(id_plan)) {
        		//System.out.println("insert mode--");
        		String sql = "INSERT INTO RIWAYAT_EVALUASI_UMUM(VERSI_ID,ID_STD,TGL_STA_KEGIATAN,WAKTU_STA_KEGIATAN,TIMESTAMP_KEGIATAN_STA,NAMA_KEGIATAN,JENIS_KEGIATAN,KETERANGAN_SINGKAT_TUJUAN_KEGIATAN,ISI_KEGIATAN,PENANGGUNG_JAWAB_KEGIATAN,AUDIENCE_KEGIATAN,DOKUMEN_KEGIATAN,KEGIATAN_STARTED)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
        		String sql = "UPDATE RIWAYAT_EVALUASI_UMUM set TGL_STA_KEGIATAN=?,WAKTU_STA_KEGIATAN=?,TIMESTAMP_KEGIATAN_STA=?,NAMA_KEGIATAN=?,JENIS_KEGIATAN=?,KETERANGAN_SINGKAT_TUJUAN_KEGIATAN=?,ISI_KEGIATAN=?,PENANGGUNG_JAWAB_KEGIATAN=?,AUDIENCE_KEGIATAN=?,DOKUMEN_KEGIATAN=?,KEGIATAN_STARTED=? where ID_PLAN=?";
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
    
    public int updateRiwayatHasilEvaluasi(String id_plan, String hasil, String note, String tgl_end, String waktu_end, String sebagai_tgl_penetapan, String versi_id, String std_isi_id, String norut_man) {
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
        		String sql = "UPDATE RIWAYAT_EVALUASI set KETERANGAN_HASIL_KEGIATAN=?,NOTE=?,TGL_END_KEGIATAN=?,WAKTU_END_KEGIATAN=?,TIMESTAMP_KEGIATAN_END=?,KEGIATAN_SELESAI=?,TGL_RUMUS_STD=?,TGL_CEK_STD=?,TGL_STUJU_STD=?,TGL_TETAP_STD=?,TGL_KENDALI_STD=? where ID_PLAN=?";
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
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI set TGL_RUMUS_STD=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        				stmt.setInt(i++, Integer.parseInt(norut_man));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("cek_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI set TGL_CEK_STD=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        				
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("stuju_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI set TGL_STUJU_STD=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("tetap_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI set TGL_TETAP_STD=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("kendali_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI set TGL_KENDALI_STD=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        			}
            		stmt.executeUpdate();	
            	}
        	}
        	else {
        		// UPDATE TGL MANUAL
        		String sql = "UPDATE RIWAYAT_EVALUASI set KETERANGAN_HASIL_KEGIATAN=?,NOTE=?,TGL_END_KEGIATAN=?,WAKTU_END_KEGIATAN=?,TIMESTAMP_KEGIATAN_END=?,KEGIATAN_SELESAI=?,TGL_RUMUS=?,TGL_CEK=?,TGL_STUJU=?,TGL_TETAP=?,TGL_KENDALI=? where ID_PLAN=?";
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
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI set TGL_RUMUS=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        				stmt.setInt(i++, Integer.parseInt(norut_man));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("cek")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI set TGL_CEK=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        				
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("stuju")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI set TGL_STUJU=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("tetap")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI set TGL_TETAP=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(std_isi_id));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("kendali")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI set TGL_KENDALI=? where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
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
    
    public int updateRiwayatHasilEvaluasi_v1(String id_plan, String hasil, String note, String tgl_end, String waktu_end, String sebagai_tgl_penetapan, String versi_id, String id_std) {
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
        		String sql = "UPDATE RIWAYAT_EVALUASI_UMUM set KETERANGAN_HASIL_KEGIATAN=?,NOTE=?,TGL_END_KEGIATAN=?,WAKTU_END_KEGIATAN=?,TIMESTAMP_KEGIATAN_END=?,KEGIATAN_SELESAI=?,TGL_RUMUS_STD=?,TGL_CEK_STD=?,TGL_STUJU_STD=?,TGL_TETAP_STD=?,TGL_KENDALI_STD=? where ID_PLAN=?";
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
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI_UMUM set TGL_RUMUS_STD=? where VERSI_ID=? and ID_STD=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(id_std));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("cek_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI_UMUM set TGL_CEK_STD=? where VERSI_ID=? and ID_STD=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(id_std));
        				
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("stuju_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI_UMUM set TGL_STUJU_STD=? where VERSI_ID=? and ID_STD=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(id_std));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("tetap_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI_UMUM set TGL_TETAP_STD=? where VERSI_ID=? and ID_STD=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(id_std));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("kendali_std")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI_UMUM set TGL_KENDALI_STD=? where VERSI_ID=? and ID_STD=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(id_std));
        			}
            		stmt.executeUpdate();	
            	}
        	}
        	else {
        		// UPDATE TGL MANUAL
        		String sql = "UPDATE RIWAYAT_EVALUASI_UMUM set KETERANGAN_HASIL_KEGIATAN=?,NOTE=?,TGL_END_KEGIATAN=?,WAKTU_END_KEGIATAN=?,TIMESTAMP_KEGIATAN_END=?,KEGIATAN_SELESAI=?,TGL_RUMUS=?,TGL_CEK=?,TGL_STUJU=?,TGL_TETAP=?,TGL_KENDALI=? where ID_PLAN=?";
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
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI_UMUM set TGL_RUMUS=? where VERSI_ID=? and ID_STD=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(id_std));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("cek")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI_UMUM set TGL_CEK=? where VERSI_ID=? and ID_STD=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(id_std));
        				
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("stuju")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI_UMUM set TGL_STUJU=? where VERSI_ID=? and ID_STD=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(id_std));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("tetap")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI_UMUM set TGL_TETAP=? where VERSI_ID=? and ID_STD=?");
        				stmt.setDate(i++, java.sql.Date.valueOf(tgl_end));
        				stmt.setInt(i++, Integer.parseInt(versi_id));
        				stmt.setInt(i++, Integer.parseInt(id_std));
        			}
        			else if(sebagai_tgl_penetapan.equalsIgnoreCase("kendali")) {
        				stmt = con.prepareStatement("update STANDARD_MANUAL_EVALUASI_UMUM set TGL_KENDALI=? where VERSI_ID=? and ID_STD=?");
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
}

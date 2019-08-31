package beans.dbase.spmi.request;

import beans.dbase.UpdateDb;
import beans.dbase.spmi.ToolSpmi;
import beans.tools.Checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class UpdateRequest
 */
@Stateless
@LocalBean
public class UpdateRequest extends UpdateDb {
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
    public UpdateRequest() {
        super();
        //TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateRequest(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        //TODO Auto-generated constructor stub
    }
    
    

    
    public int ubahUsulanJadiStandar(int id_std,String id_std_isi,String rasionale,String isi_std,String periode_unit_used,String lama_per_period,String periode_awal,String target1,String unit1,String target2,String unit2,String target3,String unit3,String target4,String unit4,String target5,String unit5,String target6,String unit6,String tkn_variable,String tkn_doc,String versi,String pihak,String pihak_mon,String norut, String tkn_indikator, String cakupan_std, String tipe_proses_pengawasan) {
    	int updated=0;
    	try {
    		updated = ubahUsulanJadiStandar(null , id_std, id_std_isi, rasionale, isi_std, periode_unit_used, lama_per_period, periode_awal, target1, unit1, target2, unit2, target3, unit3, target4, unit4, target5, unit5, target6, unit6, tkn_variable, tkn_doc, versi, pihak, pihak_mon, norut, tkn_indikator, cakupan_std, tipe_proses_pengawasan);
    		/*
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	
        	
        	//upd STANDARD_VERSION
        	stmt=con.prepareStatement("update STANDARD_VERSION set TARGET_THSMS_1=?,TARGET_THSMS_2=?,TARGET_THSMS_3=?,TARGET_THSMS_4=?,TARGET_THSMS_5=?,TARGET_THSMS_6=?,PIHAK_TERKAIT=?,DOKUMEN_TERKAIT=?,TKN_INDIKATOR=?,TARGET_PERIOD_START=?,UNIT_PERIOD_USED=?,LAMA_NOMINAL_PER_PERIOD=?,TARGET_THSMS_1_UNIT=?,TARGET_THSMS_2_UNIT=?,TARGET_THSMS_3_UNIT=?,TARGET_THSMS_4_UNIT=?,TARGET_THSMS_5_UNIT=?,TARGET_THSMS_6_UNIT=?,PIHAK_MONITOR=?,TKN_PARAMETER=? where ID_VERSI=? and ID_STD_ISI=?");
        	stmt.setString(1, target1);
        	stmt.setString(2, target2);
        	stmt.setString(3, target3);
        	stmt.setString(4, target4);
        	stmt.setString(5, target5);
        	stmt.setString(6, target6);
        	stmt.setString(7, pihak);
        	stmt.setString(8, tkn_doc);
        	stmt.setString(9, tkn_indikator);
        	stmt.setString(10, periode_awal);
        	stmt.setString(11, periode_unit_used);
        	stmt.setString(12, lama_per_period);
        	stmt.setString(13, unit1);
        	stmt.setString(14, unit2);
        	stmt.setString(15, unit3);
        	stmt.setString(16, unit4);
        	stmt.setString(17, unit5);
        	stmt.setString(18, unit6);
        	stmt.setString(19, pihak_mon);
        	stmt.setString(20, tkn_variable);
        	stmt.setInt(21, 1);
        	stmt.setInt(22, Integer.parseInt(id_std_isi));
        	updated = updated+stmt.executeUpdate();
        	
        	//upd STANDARD_ISI_TABLE
        	stmt=con.prepareStatement("update STANDARD_ISI_TABLE set ID_STD=?,PERNYATAAN_STD=?,RASIONALE=?,SCOPE=?,TIPE_PROSES_PENGAWASAN=? where ID=?");
        	if(id_std<1) {
        		stmt.setNull(1, java.sql.Types.INTEGER);
        	}
        	else {
        		stmt.setInt(1, id_std);	
        	}
        	
        	stmt.setString(2, isi_std);
        	stmt.setString(3, rasionale);
        	stmt.setString(4, cakupan_std);
        	stmt.setString(5, tipe_proses_pengawasan);
        	stmt.setInt(6, Integer.parseInt(id_std_isi));
        	updated = updated+stmt.executeUpdate();
        	*/
    	//}
    	//catch (NamingException e) {
        //	e.printStackTrace();
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
    
    
    public int ubahUsulanJadiStandar(String target_kdpst,int id_std,String id_std_isi,String rasionale,String isi_std,String periode_unit_used,String lama_per_period,String periode_awal,String target1,String unit1,String target2,String unit2,String target3,String unit3,String target4,String unit4,String target5,String unit5,String target6,String unit6,String tkn_variable,String tkn_doc,String versi,String pihak,String pihak_mon,String norut, String tkn_indikator, String cakupan_std, String tipe_proses_pengawasan) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	
        	
        	//upd STANDARD_VERSION
        	stmt=con.prepareStatement("update STANDARD_VERSION set TARGET_THSMS_1=?,TARGET_THSMS_2=?,TARGET_THSMS_3=?,TARGET_THSMS_4=?,TARGET_THSMS_5=?,TARGET_THSMS_6=?,PIHAK_TERKAIT=?,DOKUMEN_TERKAIT=?,TKN_INDIKATOR=?,TARGET_PERIOD_START=?,UNIT_PERIOD_USED=?,LAMA_NOMINAL_PER_PERIOD=?,TARGET_THSMS_1_UNIT=?,TARGET_THSMS_2_UNIT=?,TARGET_THSMS_3_UNIT=?,TARGET_THSMS_4_UNIT=?,TARGET_THSMS_5_UNIT=?,TARGET_THSMS_6_UNIT=?,PIHAK_MONITOR=?,TKN_PARAMETER=? where ID_VERSI=? and ID_STD_ISI=?");
        	stmt.setString(1, target1);
        	stmt.setString(2, target2);
        	stmt.setString(3, target3);
        	stmt.setString(4, target4);
        	stmt.setString(5, target5);
        	stmt.setString(6, target6);
        	stmt.setString(7, pihak);
        	stmt.setString(8, tkn_doc);
        	stmt.setString(9, tkn_indikator);
        	stmt.setString(10, periode_awal);
        	stmt.setString(11, periode_unit_used);
        	stmt.setString(12, lama_per_period);
        	stmt.setString(13, unit1);
        	stmt.setString(14, unit2);
        	stmt.setString(15, unit3);
        	stmt.setString(16, unit4);
        	stmt.setString(17, unit5);
        	stmt.setString(18, unit6);
        	stmt.setString(19, pihak_mon);
        	stmt.setString(20, tkn_variable);
        	if(Checker.isStringNullOrEmpty(versi)) {
        		stmt.setInt(21, 1);
        	}
        	else {
        		try {
        			stmt.setInt(21, Integer.parseInt(versi));
        		}
        		catch(Exception e) {
        			stmt.setInt(21, 1);
        		}
        	}
        	//System.out.println("version--"+versi);
        	stmt.setInt(22, Integer.parseInt(id_std_isi));
        	updated = updated+stmt.executeUpdate();
        	//System.out.println("updated--"+updated);
        	//upd STANDARD_ISI_TABLE
        	if(Checker.isStringNullOrEmpty(target_kdpst)) {
        		stmt=con.prepareStatement("update STANDARD_ISI_TABLE set ID_STD=?,PERNYATAAN_STD=?,RASIONALE=?,SCOPE=?,TIPE_PROSES_PENGAWASAN=? where ID=?");
        		if(id_std<1) {
            		stmt.setNull(1, java.sql.Types.INTEGER);
            	}
            	else {
            		stmt.setInt(1, id_std);	
            	}
            	
            	stmt.setString(2, isi_std);
            	stmt.setString(3, rasionale);
            	stmt.setString(4, cakupan_std);
            	stmt.setString(5, tipe_proses_pengawasan);
            	stmt.setInt(6, Integer.parseInt(id_std_isi));
            	updated = updated+stmt.executeUpdate();
        	}
        	else {
        		stmt=con.prepareStatement("update STANDARD_ISI_TABLE set ID_STD=?,PERNYATAAN_STD=?,KDPST=?,RASIONALE=?,SCOPE=?,TIPE_PROSES_PENGAWASAN=? where ID=?");
        		if(id_std<1) {
            		stmt.setNull(1, java.sql.Types.INTEGER);
            	}
            	else {
            		stmt.setInt(1, id_std);	
            	}
            	
            	stmt.setString(2, isi_std);
            	stmt.setString(3, target_kdpst);
            	stmt.setString(4, rasionale);
            	stmt.setString(5, cakupan_std);
            	stmt.setString(6, tipe_proses_pengawasan);
            	stmt.setInt(7, Integer.parseInt(id_std_isi));
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
    
    public int ubahUsulanJadiStandarSingle(int id_std,String id_std_isi,String isi_std, String id_versi_std_terkini) {
    	int updated=0;
    	boolean aktif = false;
    	java.sql.Date tgl_sta = ToolSpmi.getTglStaIfStandardAktif(id_std, Integer.parseInt(id_versi_std_terkini));
    	if(tgl_sta!=null) {
    		aktif = true;
    	}
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//cek versi berapa
        	if(!aktif) {
        		stmt = con.prepareStatement("update STANDARD_ISI_TABLE set ID_STD=?,PERNYATAAN_STD=?,AKTIF=?,TGL_MULAI_AKTIF=?,SCOPE=?,TIPE_PROSES_PENGAWASAN=? where ID=?");
            	stmt.setInt(1, id_std);
            	stmt.setString(2, isi_std);
            	stmt.setBoolean(3, false);
            	stmt.setNull(4, java.sql.Types.DATE);
            	stmt.setString(5, "prodi");
            	stmt.setString(6, "std");
            	stmt.setInt(7, Integer.parseInt(id_std_isi));
            	updated = stmt.executeUpdate();
            	stmt = con.prepareStatement("update STANDARD_VERSION set AKTIF=? where ID_VERSI=? and ID_STD_ISI=?");
            	stmt.setBoolean(1, false);
            	stmt.setInt(2, Integer.parseInt(id_versi_std_terkini));
            	stmt.setInt(3,  Integer.parseInt(id_std_isi));
            	updated = stmt.executeUpdate();
        	}
        	else {
        		stmt = con.prepareStatement("update STANDARD_ISI_TABLE set ID_STD=?,PERNYATAAN_STD=?,AKTIF=?,TGL_MULAI_AKTIF=?,SCOPE=?,TIPE_PROSES_PENGAWASAN=? where ID=?");
            	stmt.setInt(1, id_std);
            	stmt.setString(2, isi_std);
            	stmt.setBoolean(3, true);
            	stmt.setDate(4, tgl_sta);
            	stmt.setString(5, "prodi");
            	stmt.setString(6, "std");
            	stmt.setInt(7, Integer.parseInt(id_std_isi));
            	updated = stmt.executeUpdate();
            	stmt = con.prepareStatement("update STANDARD_VERSION set AKTIF=? where ID_VERSI=? and ID_STD_ISI=?");
            	stmt.setBoolean(1, true);
            	stmt.setInt(2, Integer.parseInt(id_versi_std_terkini));
            	stmt.setInt(3,  Integer.parseInt(id_std_isi));
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
    
    
    public int UbahUnknownJdStd(int id_std,int versi_std_terkini,String id_std_isi,String isi_std,String cakupan_std) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("update STANDARD_ISI_TABLE set ID_STD=?,PERNYATAAN_STD=?,SCOPE=?,AKTIF=?,TIPE_PROSES_PENGAWASAN=? where ID=?");
        	stmt.setInt(1,id_std);
        	stmt.setString(2, isi_std.trim());
        	stmt.setString(3, cakupan_std);
        	stmt.setBoolean(4, true);
        	stmt.setString(5, "std");
        	stmt.setInt(6, Integer.parseInt(id_std_isi));
        	updated=stmt.executeUpdate();
        	stmt=con.prepareStatement("update STANDARD_VERSION set ID_VERSI=? where ID_STD_ISI=?");
        	stmt.setInt(1, versi_std_terkini);
        	stmt.setInt(2, Integer.parseInt(id_std_isi));
        	updated = updated+stmt.executeUpdate();
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
    
    public int updateIsiStandarSingle(int id_std,String id_std_isi,String rasionale,String isi_std,String periode_unit_used,String lama_per_period,String periode_awal,String target1,String unit1,String target2,String unit2,String target3,String unit3,String target4,String unit4,String target5,String unit5,String target6,String unit6,String tkn_variable,String tkn_doc,String versi,String pihak,String pihak_mon, String tkn_indikator, String cakupan_std, String tipe_proses_pengawasan, String strategi) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	
        	
        	//upd STANDARD_VERSION
        	stmt=con.prepareStatement("update STANDARD_VERSION set TARGET_THSMS_1=?,TARGET_THSMS_2=?,TARGET_THSMS_3=?,TARGET_THSMS_4=?,TARGET_THSMS_5=?,TARGET_THSMS_6=?,PIHAK_TERKAIT=?,DOKUMEN_TERKAIT=?,TKN_INDIKATOR=?,TARGET_PERIOD_START=?,UNIT_PERIOD_USED=?,LAMA_NOMINAL_PER_PERIOD=?,TARGET_THSMS_1_UNIT=?,TARGET_THSMS_2_UNIT=?,TARGET_THSMS_3_UNIT=?,TARGET_THSMS_4_UNIT=?,TARGET_THSMS_5_UNIT=?,TARGET_THSMS_6_UNIT=?,PIHAK_MONITOR=?,TKN_PARAMETER=?,STRATEGI=? where ID_VERSI=? and ID_STD_ISI=?");
        	int i=1;
        	stmt.setString(i++, target1);
        	stmt.setString(i++, target2);
        	stmt.setString(i++, target3);
        	stmt.setString(i++, target4);
        	stmt.setString(i++, target5);
        	stmt.setString(i++, target6);
        	if(Checker.isStringNullOrEmpty(pihak)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, pihak);	
        	}
        	//stmt.setString(i++, tkn_doc);
        	if(Checker.isStringNullOrEmpty(tkn_doc)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, tkn_doc);	
        	}
        	//stmt.setString(i++, tkn_indikator);
        	if(Checker.isStringNullOrEmpty(tkn_indikator)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, tkn_indikator);	
        	}
        	//stmt.setString(i++, periode_awal);
        	if(Checker.isStringNullOrEmpty(periode_awal)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, periode_awal);	
        	}
        	//stmt.setString(i++, periode_unit_used);
        	if(Checker.isStringNullOrEmpty(periode_unit_used)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, periode_unit_used);	
        	}
        	//stmt.setString(i++, lama_per_period);
        	if(Checker.isStringNullOrEmpty(lama_per_period)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, lama_per_period);	
        	}
        	//stmt.setString(i++, unit1);
        	if(Checker.isStringNullOrEmpty(unit1)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, unit1);	
        	}
        	//stmt.setString(i++, unit2);
        	if(Checker.isStringNullOrEmpty(unit2)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, unit2);	
        	}
        	//stmt.setString(i++, unit3);
        	if(Checker.isStringNullOrEmpty(unit3)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, unit3);	
        	}
        	//stmt.setString(i++, unit4);
        	if(Checker.isStringNullOrEmpty(unit4)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, unit4);	
        	}
        	//stmt.setString(i++, unit5);
        	if(Checker.isStringNullOrEmpty(unit5)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, unit5);	
        	}
        	//stmt.setString(i++, unit6);
        	if(Checker.isStringNullOrEmpty(unit6)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, unit6);	
        	}
        	//stmt.setString(i++, pihak_mon);
        	if(Checker.isStringNullOrEmpty(pihak_mon)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, pihak_mon);	
        	}
        	//stmt.setString(i++, tkn_variable);
        	if(Checker.isStringNullOrEmpty(tkn_variable)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, tkn_variable);	
        	}
        	//stmt.setString(i++, strategi);
        	if(Checker.isStringNullOrEmpty(strategi)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, strategi);	
        	}
        	
        	stmt.setInt(i++, Integer.parseInt(versi));
        	//System.out.println("version--"+versi);
        	stmt.setInt(i++, Integer.parseInt(id_std_isi));
        	updated = updated+stmt.executeUpdate();
        	//System.out.println("updated--"+updated);
        	//upd STANDARD_ISI_TABLE
        	i=1;
        	stmt=con.prepareStatement("update STANDARD_ISI_TABLE set ID_STD=?,PERNYATAAN_STD=?,RASIONALE=?,SCOPE=?,TIPE_PROSES_PENGAWASAN=? where ID=?");
    		if(id_std<1) {
        		stmt.setNull(i++, java.sql.Types.INTEGER);
        	}
        	else {
        		stmt.setInt(i++, id_std);	
        	}
        	
        	//stmt.setString(2, isi_std);
        	if(Checker.isStringNullOrEmpty(isi_std)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, isi_std);	
        	}
        	//stmt.setString(3, rasionale);
        	if(Checker.isStringNullOrEmpty(rasionale)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, rasionale);	
        	}
        	//stmt.setString(4, cakupan_std);
        	if(Checker.isStringNullOrEmpty(cakupan_std)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, cakupan_std);	
        	}
        	//stmt.setString(5, tipe_proses_pengawasan);
        	if(Checker.isStringNullOrEmpty(tipe_proses_pengawasan)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, tipe_proses_pengawasan);	
        	}
        	stmt.setInt(6, Integer.parseInt(id_std_isi));
        	updated = updated+stmt.executeUpdate();
        	
        	
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
    
    public int updateParameterDanIndikatorStandar(int id_std,String id_std_isi,String isi_std,String periode_unit_used,String lama_per_period,String periode_awal,String target1,String unit1,String target2,String unit2,String target3,String unit3,String target4,String unit4,String target5,String unit5,String target6,String unit6,String tkn_variable,String versi,String norut, String tkn_indikator) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	
        	
        	//upd STANDARD_VERSION
        	stmt=con.prepareStatement("update STANDARD_VERSION set TARGET_THSMS_1=?,TARGET_THSMS_2=?,TARGET_THSMS_3=?,TARGET_THSMS_4=?,TARGET_THSMS_5=?,TARGET_THSMS_6=?,TKN_INDIKATOR=?,TARGET_PERIOD_START=?,UNIT_PERIOD_USED=?,LAMA_NOMINAL_PER_PERIOD=?,TARGET_THSMS_1_UNIT=?,TARGET_THSMS_2_UNIT=?,TARGET_THSMS_3_UNIT=?,TARGET_THSMS_4_UNIT=?,TARGET_THSMS_5_UNIT=?,TARGET_THSMS_6_UNIT=?,TKN_PARAMETER=? where ID_VERSI=? and ID_STD_ISI=?");
        	//TARGET_THSMS_1=?,
        	stmt.setString(1, target1);
        	//TARGET_THSMS_2=?,
        	stmt.setString(2, target2);
        	//TARGET_THSMS_3=?,
        	stmt.setString(3, target3);
        	//TARGET_THSMS_4=?,
        	stmt.setString(4, target4);
        	//TARGET_THSMS_5=?,
        	stmt.setString(5, target5);
        	//TARGET_THSMS_6=?,
        	stmt.setString(6, target6);
        	//TKN_INDIKATOR=?,
        	stmt.setString(7, tkn_indikator);
        	//TARGET_PERIOD_START=?,
        	stmt.setString(8, periode_awal);
        	//UNIT_PERIOD_USED=?,
        	stmt.setString(9, periode_unit_used);
        	//LAMA_NOMINAL_PER_PERIOD=?,
        	stmt.setString(10, lama_per_period);
        	//TARGET_THSMS_1_UNIT=?,
        	stmt.setString(11, unit1);
        	//TARGET_THSMS_2_UNIT=?,
        	stmt.setString(12, unit2);
        	//TARGET_THSMS_3_UNIT=?,
        	stmt.setString(13, unit3);
        	//TARGET_THSMS_4_UNIT=?,
        	stmt.setString(14, unit4);
        	//TARGET_THSMS_5_UNIT=?,
        	stmt.setString(15, unit5);
        	//TARGET_THSMS_6_UNIT=?,
        	stmt.setString(16, unit6);
        	//TKN_PARAMETER=? where ID_VERSI=? and ID_STD_ISI=?");
        	stmt.setString(17, tkn_variable);
        	if(Checker.isStringNullOrEmpty(versi)) {
        		stmt.setInt(18, 1);
        	}
        	else {
        		try {
        			stmt.setInt(18, Integer.parseInt(versi));
        		}
        		catch(Exception e) {
        			stmt.setInt(18, 1);
        		}
        	}
        	//System.out.println("version--"+versi);
        	stmt.setInt(19, Integer.parseInt(id_std_isi));
        	updated = updated+stmt.executeUpdate();
        	//System.out.println("updated--"+updated);
        	//upd STANDARD_ISI_TABLE
        	
        	/*
        	 * TIDAK DIPERBOLEHKAN UPDATE YANG LAINNYA KARENA HANYA PARAMETER DAN INDIKATOR SAJA
        	 */
        	/*
        	String target_kdpst =""; 
        	if(Checker.isStringNullOrEmpty(target_kdpst)) {
        		//harusnya tidak perlu ada target kdpst karena IDnya beda kalo spesifikprodi 
        		stmt=con.prepareStatement("update STANDARD_ISI_TABLE set ID_STD=?,PERNYATAAN_STD=?,SCOPE=? where ID=?");
        		if(id_std<1) {
            		stmt.setNull(1, java.sql.Types.INTEGER);
            	}
            	else {
            		stmt.setInt(1, id_std);	
            	}
            	
            	stmt.setString(2, isi_std);
            	//stmt.setString(3, rasionale);
            	stmt.setString(3, cakupan_std);
            	//stmt.setString(5, tipe_proses_pengawasan);
            	stmt.setInt(4, Integer.parseInt(id_std_isi));
            	updated = updated+stmt.executeUpdate();
        	}
        	else {
        		stmt=con.prepareStatement("update STANDARD_ISI_TABLE set ID_STD=?,PERNYATAAN_STD=?,KDPST=?,SCOPE=? where ID=?");
        		if(id_std<1) {
            		stmt.setNull(1, java.sql.Types.INTEGER);
            	}
            	else {
            		stmt.setInt(1, id_std);	
            	}
            	
            	stmt.setString(2, isi_std);
            	stmt.setString(3, target_kdpst);
            	//stmt.setString(4, rasionale);
            	stmt.setString(4, cakupan_std);
            	//stmt.setString(6, tipe_proses_pengawasan);
            	stmt.setInt(5, Integer.parseInt(id_std_isi));
            	updated = updated+stmt.executeUpdate();
        	}
        	*/
        	
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

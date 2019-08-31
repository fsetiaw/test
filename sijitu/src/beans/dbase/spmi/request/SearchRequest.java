package beans.dbase.spmi.request;

import beans.dbase.SearchDb;
import beans.tools.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ListIterator;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class SearchRequest
 */
@Stateless
@LocalBean
public class SearchRequest extends SearchDb {
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
    public SearchRequest() {
        super();
        //TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchRequest(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        //TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(Connection)
     */
    public SearchRequest(Connection con) {
        super(con);
        //TODO Auto-generated constructor stub
    }

    public Vector getListNuStdReq() {
    	Vector v = null;
    	try {
        		//String ipAddr =  request.getRemoteAddr();
    		Vector vIns = new Vector();
    		ListIterator lins = vIns.listIterator();
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select ID,ID_STD,PERNYATAAN_STD,NO_BUTIR,KDPST,RASIONALE from STANDARD_ISI_TABLE where ID_STD is null and TGL_MULAI_AKTIF is null");
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ListIterator li = null;
        		if(v==null) {
        			v = new Vector();
        			li = v.listIterator();
        		}	
        		int id = rs.getInt(1);
        		int id_std = rs.getInt(2);
        		String isi = ""+rs.getString(3);
        		int butir = rs.getInt(4); 
        		String kdpst = ""+rs.getString(5);
        		String rasionale = ""+rs.getString(6);
        		if(Checker.isStringNullOrEmpty(rasionale)) {
        			rasionale="null";
        		}
        		String tmp = id+"`"+id_std+"`"+isi+"`"+butir+"`"+kdpst+"`"+rasionale;
        		tmp = tmp.replace("``", "`null`");
        		li.add(tmp);
        		while(rs.next()) {
        			id = rs.getInt(1);
            		id_std = rs.getInt(2);
            		isi = ""+rs.getString(3);
            		butir = rs.getInt(4); 
            		kdpst = ""+rs.getString(5);
            		rasionale = ""+rs.getString(6);
            		if(Checker.isStringNullOrEmpty(rasionale)) {
            			rasionale="null";
            		}
            		tmp = id+"`"+id_std+"`"+isi+"`"+butir+"`"+kdpst+"`"+rasionale;
            		tmp = tmp.replace("``", "`null`");
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

    
    public Vector getListNuStdReq(String id_standar_isi) {
    	Vector v = null;
    	
    	try {
    		int id_std_isi = Integer.parseInt(id_standar_isi);
        		//String ipAddr =  request.getRemoteAddr();
    		Vector vIns = new Vector();
    		ListIterator lins = vIns.listIterator();
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select ID,ID_STD,PERNYATAAN_STD,NO_BUTIR,KDPST,RASIONALE from STANDARD_ISI_TABLE where ID_STD=?");
        	stmt.setInt(1, id_std_isi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ListIterator li = null;
        		if(v==null) {
        			v = new Vector();
        			li = v.listIterator();
        		}	
        		int id = rs.getInt(1);
        		int id_std = rs.getInt(2);
        		String isi = ""+rs.getString(3);
        		int butir = rs.getInt(4); 
        		String kdpst = ""+rs.getString(5);
        		String rasionale = ""+rs.getString(6);
        		if(Checker.isStringNullOrEmpty(rasionale)) {
        			rasionale="null";
        		}
        		String tmp = id+"`"+id_std+"`"+isi+"`"+butir+"`"+kdpst+"`"+rasionale;
        		tmp = tmp.replace("``", "`null`");
        		li.add(tmp);
        		while(rs.next()) {
        			id = rs.getInt(1);
            		id_std = rs.getInt(2);
            		isi = ""+rs.getString(3);
            		butir = rs.getInt(4); 
            		kdpst = ""+rs.getString(5);
            		rasionale = ""+rs.getString(6);
            		if(Checker.isStringNullOrEmpty(rasionale)) {
            			rasionale="null";
            		}
            		tmp = id+"`"+id_std+"`"+isi+"`"+butir+"`"+kdpst+"`"+rasionale;
            		tmp = tmp.replace("``", "`null`");
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
    
    public Vector getInfoStd(int id_std_isi) {
    	/*
    	 * get list standar seluruh revisi
    	 */
    	
    	Vector v = null;
    	try {
    		v = getInfoStd_v1(id_std_isi,-1);
    		/*
        		//String ipAddr =  request.getRemoteAddr();
    		Vector vIns = new Vector();
    		ListIterator lins = vIns.listIterator();
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select * from STANDARD_ISI_TABLE inner join STANDARD_VERSION on ID=ID_STD_ISI where ID=?");
        	stmt.setInt(1, id_std_isi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ListIterator li = null;
        		if(v==null) {
        			v = new Vector();
        			li = v.listIterator();
        		}	
        		int id = rs.getInt("ID"); //id std isi
        		int id_std = rs.getInt("ID_STD");
        		//int id_master = rs.getInt("ID_MASTER_STD");
        		//int id_tipe = rs.getInt("ID_TIPE_STD");
        		String isi = ""+rs.getString("PERNYATAAN_STD");
        		int butir = rs.getInt("NO_BUTIR"); 
        		String kdpst = ""+rs.getString("KDPST");
        		String rasionale = ""+rs.getString("RASIONALE");
        		
        		String id_versi = ""+rs.getInt("ID_VERSI");
        		String id_declare = ""+rs.getString("ID_MANUAL_PENETAPAN");
        		String id_do = ""+rs.getString("ID_MANUAL_PELAKSANAAN");
        		String id_eval = ""+rs.getString("ID_MANUAL_EVALUASI");
        		String id_control = ""+rs.getString("ID_MANUAL_PENGENDALIAN");
        		String id_upgrade = ""+rs.getString("ID_MANUAL_PENINGKATAN");
        		String tglsta = ""+rs.getString("TGL_STA");
        		String tglend = ""+rs.getString("TGL_END");
        		String thsms1 = ""+rs.getString("TARGET_THSMS_1");
        		String thsms2 = ""+rs.getString("TARGET_THSMS_2");
        		String thsms3 = ""+rs.getString("TARGET_THSMS_3");
        		String thsms4 = ""+rs.getString("TARGET_THSMS_4");
        		String thsms5 = ""+rs.getString("TARGET_THSMS_5");
        		String thsms6 = ""+rs.getString("TARGET_THSMS_6");
        		String pihak = ""+rs.getString("PIHAK_TERKAIT");
        		pihak = pihak.replace("`", ",");
        		String dokumen = ""+rs.getString("DOKUMEN_TERKAIT");
        		dokumen = dokumen.replace("`", ",");
        		String tkn_indikator = ""+rs.getString("TKN_INDIKATOR");
        		String norut = ""+rs.getString("NO_URUT_TAMPIL");
        		//String unit_target = ""+rs.getString("TARGET_PERIOD_UNIT");
        		//String satuan_target = ""+rs.getString("TARGET_SATUAN");
        		String target_period_start = ""+rs.getString("TARGET_PERIOD_START");
        		String unit_period = ""+rs.getString("UNIT_PERIOD_USED");
        		String lama_per_period = ""+rs.getInt("LAMA_NOMINAL_PER_PERIOD");
        		String target_unit1 = ""+rs.getString("TARGET_THSMS_1_UNIT");
        		String target_unit2 = ""+rs.getString("TARGET_THSMS_2_UNIT");
        		String target_unit3 = ""+rs.getString("TARGET_THSMS_3_UNIT");
        		String target_unit4 = ""+rs.getString("TARGET_THSMS_4_UNIT");
        		String target_unit5 = ""+rs.getString("TARGET_THSMS_5_UNIT");
        		String target_unit6 = ""+rs.getString("TARGET_THSMS_6_UNIT");
        		String tkn_param = ""+rs.getString("TKN_PARAMETER");
        		String tkn_pengawas = ""+rs.getString("PIHAK_MONITOR");
        		String scope = ""+rs.getString("SCOPE");
        		String tipe_survey = ""+rs.getString("TIPE_PROSES_PENGAWASAN");
        		
        		if(Checker.isStringNullOrEmpty(tipe_survey)) {
        			scope="null";//default value
        		}
        		
        		//add info id_master_std & id_tipe_std
        		stmt = con.prepareStatement("select ID_MASTER_STD,ID_TIPE_STD from STANDARD_TABLE where ID_STD=?");
        		stmt.setInt(1, id_std);
        		rs = stmt.executeQuery();
        		String id_master = "null";
        		String id_tipe = "null";
        		if(rs.next()) {
        			id_master = ""+rs.getInt(1);
            		id_tipe = ""+rs.getInt(1);	
        		}
        		
        
        		String tmp = id+"`"+id_std+"`"+isi+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+id_versi+"`"+id_declare+"`"+id_do+"`"+id_eval+"`"+id_control+"`"+id_upgrade+"`"+tglsta+"`"+tglend+"`"+thsms1+"`"+thsms2+"`"+thsms3+"`"+thsms4+"`"+thsms5+"`"+thsms6+"`"+pihak+"`"+dokumen+"`"+tkn_indikator+"`"+norut+"`"+target_period_start+"`"+unit_period+"`"+lama_per_period+"`"+target_unit1+"`"+target_unit2+"`"+target_unit3+"`"+target_unit4+"`"+target_unit5+"`"+target_unit6+"`"+tkn_param+"`"+id_master+"`"+id_tipe+"`"+tkn_pengawas+"`"+scope+"`"+tipe_survey;
        		tmp = tmp.replace("``", "`null`");
        		tmp = tmp.replace("``", "`null`");
        		tmp = tmp.replace("``", "`null`");
        		tmp = tmp.replace("``", "`null`");
        		tmp = tmp.replace("``", "`null`");
        		li.add(tmp);

        		
        	}
        	*/
    	}
        catch (Exception e) {
        	e.printStackTrace();
        }
        //catch (SQLException ex) {
        //	ex.printStackTrace();
        //} 
        finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    	    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    	    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	return v;
    }
    
    
    public Vector getInfoStd_v1(int id_std_isi, int id_versi_target) {
    	/*
    	 * get list standar specific revisi
    	 */
    	//System.out.println("id_std_isi="+id_std_isi);
    	//System.out.println("id_versi_target="+id_versi_target);
    	Vector v = null;
    	try {
        		//String ipAddr =  request.getRemoteAddr();
    		Vector vIns = new Vector();
    		ListIterator lins = vIns.listIterator();
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(id_versi_target<0) {
        		stmt = con.prepareStatement("select * from STANDARD_ISI_TABLE inner join STANDARD_VERSION on ID=ID_STD_ISI where ID=?");
            	stmt.setInt(1, id_std_isi);	
        	}
        	else {
        		stmt = con.prepareStatement("select * from STANDARD_ISI_TABLE inner join STANDARD_VERSION on ID=ID_STD_ISI where ID=? and ID_VERSI=?");
            	stmt.setInt(1, id_std_isi);
            	stmt.setInt(2, id_versi_target); 	
        	}
        	
        	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ListIterator li = null;
        		if(v==null) {
        			v = new Vector();
        			li = v.listIterator();
        		}	
        		int std_isi_id = rs.getInt("ID"); //id std isi
        		int id_std = rs.getInt("ID_STD");
        		//int id_master = rs.getInt("ID_MASTER_STD");
        		//int id_tipe = rs.getInt("ID_TIPE_STD");
        		String isi = ""+rs.getString("PERNYATAAN_STD");
        		int butir = rs.getInt("NO_BUTIR"); 
        		String std_kdpst = ""+rs.getString("STANDARD_ISI_TABLE.KDPST");
        		String rasionale = ""+rs.getString("RASIONALE");
        		
        		String id_versi = ""+rs.getInt("ID_VERSI");
        		String id_declare = ""+rs.getString("ID_MANUAL_PENETAPAN");
        		String id_do = ""+rs.getString("ID_MANUAL_PELAKSANAAN");
        		String id_eval = ""+rs.getString("ID_MANUAL_EVALUASI");
        		String id_control = ""+rs.getString("ID_MANUAL_PENGENDALIAN");
        		String id_upgrade = ""+rs.getString("ID_MANUAL_PENINGKATAN");
        		String tglsta = ""+rs.getString("TGL_MULAI_AKTIF");
        		//System.out.println("tglsta="+tglsta);
        		String tglend = ""+rs.getString("TGL_STOP_AKTIF");
        		String thsms1 = ""+rs.getString("TARGET_THSMS_1");
        		String thsms2 = ""+rs.getString("TARGET_THSMS_2");
        		String thsms3 = ""+rs.getString("TARGET_THSMS_3");
        		String thsms4 = ""+rs.getString("TARGET_THSMS_4");
        		String thsms5 = ""+rs.getString("TARGET_THSMS_5");
        		String thsms6 = ""+rs.getString("TARGET_THSMS_6");
        		String pihak = ""+rs.getString("PIHAK_TERKAIT");
        		pihak = pihak.replace("`", ",");
        		String dokumen = ""+rs.getString("DOKUMEN_TERKAIT");
        		dokumen = dokumen.replace("`", ",");
        		String tkn_indikator = ""+rs.getString("TKN_INDIKATOR");
        		String norut = ""+rs.getString("NO_URUT_TAMPIL");
        		//String unit_target = ""+rs.getString("TARGET_PERIOD_UNIT");
        		//String satuan_target = ""+rs.getString("TARGET_SATUAN");
        		String target_period_start = ""+rs.getString("TARGET_PERIOD_START");
        		String unit_period = ""+rs.getString("UNIT_PERIOD_USED");
        		String lama_per_period = ""+rs.getInt("LAMA_NOMINAL_PER_PERIOD");
        		String target_unit1 = ""+rs.getString("TARGET_THSMS_1_UNIT");
        		String target_unit2 = ""+rs.getString("TARGET_THSMS_2_UNIT");
        		String target_unit3 = ""+rs.getString("TARGET_THSMS_3_UNIT");
        		String target_unit4 = ""+rs.getString("TARGET_THSMS_4_UNIT");
        		String target_unit5 = ""+rs.getString("TARGET_THSMS_5_UNIT");
        		String target_unit6 = ""+rs.getString("TARGET_THSMS_6_UNIT");
        		String tkn_param = ""+rs.getString("TKN_PARAMETER");
        		String tkn_pengawas = ""+rs.getString("PIHAK_MONITOR");
        		String scope = ""+rs.getString("SCOPE");
        		String tipe_survey = ""+rs.getString("TIPE_PROSES_PENGAWASAN");
        		String aktif = ""+rs.getBoolean("AKTIF");
        		String kdpst = ""+rs.getString("KDPST");
        		String strategi = ""+rs.getString("STRATEGI");
        		
        		
        		if(Checker.isStringNullOrEmpty(tipe_survey)) {
        			scope="null";//default value
        		}
        		
        		//add info id_master_std & id_tipe_std
        		stmt = con.prepareStatement("select ID_MASTER_STD,ID_TIPE_STD from STANDARD_TABLE where ID_STD=?");
        		stmt.setInt(1, id_std);
        		rs = stmt.executeQuery();
        		String id_master = "null";
        		String id_tipe = "null";
        		if(rs.next()) {
        			id_master = ""+rs.getInt(1);
            		id_tipe = ""+rs.getInt(1);	
        		}
        		
        
        		String tmp = std_isi_id+"`"+id_std+"`"+isi+"`"+butir+"`"+std_kdpst+"`"+rasionale+"`"+id_versi+"`"+id_declare+"`"+id_do+"`"+id_eval+"`"+id_control+"`"+id_upgrade+"`"+tglsta+"`"+tglend+"`"+thsms1+"`"+thsms2+"`"+thsms3+"`"+thsms4+"`"+thsms5+"`"+thsms6+"`"+pihak+"`"+dokumen+"`"+tkn_indikator+"`"+norut+"`"+target_period_start+"`"+unit_period+"`"+lama_per_period+"`"+target_unit1+"`"+target_unit2+"`"+target_unit3+"`"+target_unit4+"`"+target_unit5+"`"+target_unit6+"`"+tkn_param+"`"+id_master+"`"+id_tipe+"`"+tkn_pengawas+"`"+scope+"`"+tipe_survey+"`"+aktif+"`"+kdpst+"`"+strategi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "`");
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
    
    

}

package beans.dbase.spmi.form;

import beans.dbase.UpdateDb;
import beans.dbase.spmi.ToolSpmi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;
import beans.tools.Checker;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class UpdateForm
 */
@Stateless
@LocalBean
public class UpdateForm extends UpdateDb {
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
    public UpdateForm() {
        super();
        //TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateForm(String operatorNpm) {
    	super(operatorNpm);
    	this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        //TODO Auto-generated constructor stub
    }
    

    
    public int insertNuStandarIsi(String[]pihak_terkait,String[]group_terkait , String isi_std, String[]doc_terkait, String rasionale) {
    	int id_std_isi_created=0;
    	try {
    		id_std_isi_created = insertNuStandarIsi(pihak_terkait,group_terkait ,isi_std,doc_terkait,rasionale,-1);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	/*
    	String tkn_doc=null;
    	if(!Checker.isStringNullOrEmpty(rasionale)) {
    		try {
        		//String ipAddr =  request.getRemoteAddr();
    		if(doc_terkait!=null && doc_terkait.length>0) {
    			
    			for(int i=0;i<doc_terkait.length;i++) {
    				if(!Checker.isStringNullOrEmpty(doc_terkait[i])) {
    					if(tkn_doc==null) {
        					tkn_doc = new String(doc_terkait[i]);
        				}
        				else {
        					tkn_doc=tkn_doc+"`"+doc_terkait[i];
        				}
    				}
    				
    			}
    		}
    		Vector vIns = new Vector();
    		ListIterator lins = vIns.listIterator();
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("insert into STANDARD_ISI_TABLE(PERNYATAAN_STD,RASIONALE)values(?,?)");
        	stmt.setString(1, isi_std);
        	if(Checker.isStringNullOrEmpty(isi_std)) {
    			stmt.setString(1, "null");
    		}
    		else {
    			stmt.setString(1, isi_std);
    		}
        	//if(Checker.isStringNullOrEmpty(rasionale)) {
    		//	stmt.setNull(2, java.sql.Types.VARCHAR);
    		//}
    		//else {
    		stmt.setString(2, rasionale);
    		//}
        	int i = stmt.executeUpdate();
        	if(i>0) {
        		//berhasil input
        		//GET ID
        		stmt = con.prepareStatement("select ID from STANDARD_ISI_TABLE order by ID desc limit 1");
        		rs = stmt.executeQuery();
        		rs.next();
        		int id = rs.getInt(1);
        		
        		//KARENA INPUT BARU JADI INI ADALAH VERSI 1
        		stmt=con.prepareStatement("insert into STANDARD_VERSION(ID_VERSI,ID_STD_ISI,PIHAK_TERKAIT,DOKUMEN_TERKAIT)values(?,?,?,?)");
        		stmt.setInt(1, 1);;//vers 1
        		stmt.setInt(2, id);
        		String pihak = null;
        		if(pihak_terkait!=null && pihak_terkait.length>0) {
        			for(int j=0;j<pihak_terkait.length;j++) {
        				
        				if(pihak==null) {
        					pihak = new String(pihak_terkait[j].trim());
        				}
        				else {
        					pihak = pihak +"`"+ pihak_terkait[j].trim();
        				}
        			}
        		}
        		if(group_terkait!=null && group_terkait.length>0) {
        			for(int j=0;j<group_terkait.length;j++) {
        				
        				if(pihak==null) {
        					pihak = new String(group_terkait[j].trim());
        				}
        				else {
        					pihak = pihak +"`"+ group_terkait[j].trim();
        				}
        			}
        		}
        		if(Checker.isStringNullOrEmpty(pihak)) {
        			stmt.setNull(3, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(3, pihak);
        		}
        		if(Checker.isStringNullOrEmpty(tkn_doc)) {
        			stmt.setNull(4, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(4, tkn_doc);
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
    	*/
    	return id_std_isi_created;
    }
    
    public int addDraftIsiStd(int id_std, String isi_std, int versi_std,String scope) {
    	int updated=0;
    	try {
    		java.sql.Date tgl_sta = ToolSpmi.getTglStaIfStandardAktif(id_std, versi_std);
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("insert into STANDARD_ISI_TABLE(ID_STD,PERNYATAAN_STD,SCOPE,TIPE_PROSES_PENGAWASAN,TGL_MULAI_AKTIF)values(?,?,?,?,?)");
			stmt.setInt(1, id_std);
			stmt.setString(2, isi_std);
			stmt.setString(3, scope);
			stmt.setString(4, "std");
			if(tgl_sta==null) {
				stmt.setNull(5,java.sql.Types.DATE);
			}
			else {
				stmt.setDate(5, tgl_sta);
			}
			updated = stmt.executeUpdate();
			if(updated>0) {
				stmt = con.prepareStatement("select ID from STANDARD_ISI_TABLE where ID_STD=? order by ID desc limit 1");
				stmt.setInt(1, id_std);
				rs = stmt.executeQuery();
				rs.next();
				int id_std_isi = rs.getInt(1);
				stmt=con.prepareStatement("insert into STANDARD_VERSION(ID_VERSI,ID_STD_ISI)values(?,?)");
				stmt.setInt(1, versi_std);
				stmt.setInt(2, id_std_isi);
				updated = updated+stmt.executeUpdate();
				//cek jika status keaktifan std saat butir ini diisi,
				//bila sedang aktif tambahkan tanggal mulai aktif pada 
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
    
    public int insertNuStandarIsi(String[]pihak_terkait,String[]group_terkait , String isi_std, String[]doc_terkait, String rasionale, int versi) {
    	int id_std_isi_created=0;
    	String tkn_doc=null;
    	if(!Checker.isStringNullOrEmpty(rasionale)) {
    		try {
        		//String ipAddr =  request.getRemoteAddr();
    			if(doc_terkait!=null && doc_terkait.length>0) {
    			
    				for(int i=0;i<doc_terkait.length;i++) {
    					if(!Checker.isStringNullOrEmpty(doc_terkait[i])) {
    						if(tkn_doc==null) {
    							tkn_doc = new String(doc_terkait[i].trim());
    						}
    						else {
    							tkn_doc=tkn_doc+"`"+doc_terkait[i].trim();
    						}
    					}
    				
    				}
    			}
    			Vector vIns = new Vector();
    			ListIterator lins = vIns.listIterator();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("insert into STANDARD_ISI_TABLE(PERNYATAAN_STD,RASIONALE,AKTIF)values(?,?,?)");
    			stmt.setString(1, isi_std);
    			if(Checker.isStringNullOrEmpty(isi_std)) {
    				stmt.setString(1, "null");
    			}
    			else {
    				stmt.setString(1, isi_std.trim());
    			}
    			//if(Checker.isStringNullOrEmpty(rasionale)) {
    			//	stmt.setNull(2, java.sql.Types.VARCHAR);
    			//}
    		//else {
    			stmt.setString(2, rasionale.trim());
    			stmt.setBoolean(3, false);
    			//}
    			int i = stmt.executeUpdate();
    			//System.out.println("updated i = "+i);
    			if(i>0) {
        		//berhasil input
        		//GET ID
    				stmt = con.prepareStatement("select ID from STANDARD_ISI_TABLE order by ID desc limit 1");
    				rs = stmt.executeQuery();
    				rs.next();
    				id_std_isi_created = rs.getInt(1);
    				//System.out.println("updated id = "+id_std_isi_created);
        		//cek versi yg mo diinput
    				stmt=con.prepareStatement("insert into STANDARD_VERSION(ID_VERSI,ID_STD_ISI,PIHAK_TERKAIT,DOKUMEN_TERKAIT,AKTIF)values(?,?,?,?,?)");
    				if(versi<1) {
    					stmt.setInt(1, 1);;//vers 1
    				}
    				else {
    					stmt.setInt(1, versi);;//vers 1	
    				}
    				
    				stmt.setInt(2, id_std_isi_created);
    				String pihak = null;
    				if(pihak_terkait!=null && pihak_terkait.length>0) {
    					for(int j=0;j<pihak_terkait.length;j++) {
    						//System.out.println("pihak_terkait["+j+"]="+pihak_terkait[j]);
    						if(!Checker.isStringNullOrEmpty(pihak_terkait[j])) {
    							if(pihak==null) {
        							
        							pihak = new String(pihak_terkait[j].trim());
        						}
        						else {
        							pihak = pihak +"`"+ pihak_terkait[j].trim();
        						}	
							}
    						
    					}
    				}
    				if(group_terkait!=null && group_terkait.length>0) {
    					for(int j=0;j<group_terkait.length;j++) {
        				
    						if(pihak==null) {
    							pihak = new String(group_terkait[j].trim());
    						}
    						else {
    							pihak = pihak +"`"+ group_terkait[j].trim();
    						}
    					}
    				}
    				if(Checker.isStringNullOrEmpty(pihak)) {
    					stmt.setNull(3, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(3, pihak);
    				}
    				if(Checker.isStringNullOrEmpty(tkn_doc)) {
    					stmt.setNull(4, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(4, tkn_doc);
    				}
    				stmt.setBoolean(5, false);
    				stmt.executeUpdate();
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
    	
    	return id_std_isi_created;
    }
    
    public int copyStandarIsi(String target_kdpst, String token_getInfoStd) {
    	int id_std_isi_created=0;
    	//String tkn_doc=null;
    	
    	StringTokenizer st = new StringTokenizer(token_getInfoStd,"`");
		
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
		//System.out.println("tglsta="+tglsta);
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
		/*
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
		*/
		//dokumen+"`"+
		String tkn_doc = st.nextToken();
		/*
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
		*/
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
		
		
    	try {
    		boolean std_sdh_ada = false;
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		String sql = "SELECT count(*) FROM STANDARD_ISI_TABLE where TRIM(PERNYATAAN_STD)=? and KDPST=? and SCOPE=?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, isi.trim());
			if(Checker.isStringNullOrEmpty(target_kdpst)) {
    			//berarti copy untuk buat standar baru agar mempermudah karena banyak kemiripan pada dokumen / pihak terkait,
    			//namun isi_std & kdpst tidak boleh sama, karena tidak boleh ada duplikate, yg berbeda adalah versi!!!
				stmt.setNull(2, java.sql.Types.VARCHAR);
    		}
    		else {
    			//berarti copy untuk buat standar baru KHUSUS UNTUK PRODI TERTENTU agar mempermudah karena banyak kemiripan pada dokumen / pihak terkait,
    			//namun isi_std & kdpst tidak boleh sama, karena tidak boleh ada duplikate, yg berbeda adalah versi!!!
    			stmt.setString(2, target_kdpst);
    		}
			//check scope/cakupan standar
			if(Checker.isStringNullOrEmpty(cakupan_std)) {
    			stmt.setNull(3, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(3, cakupan_std);
    		}
				
			rs = stmt.executeQuery();
			int counter = 0;
			if(rs.next()) {
				counter = rs.getInt(1);
			}
				
				
			int updated=0;
			if(counter>0) {
					//standar isi ini sudah ada, tidak bisa insert (DUPLICATE);
			}
			else {
				//go ahead insert
				Vector vIns = new Vector();
	    		ListIterator lins = vIns.listIterator();
				
				sql = "INSERT INTO STANDARD_ISI_TABLE(ID_STD,PERNYATAAN_STD,KDPST,RASIONALE,AKTIF,TGL_MULAI_AKTIF,TGL_STOP_AKTIF,SCOPE,TIPE_PROSES_PENGAWASAN)values(?,?,?,?,?,?,?,?,?)"; 
				stmt = con.prepareStatement(sql);
				int i=1;
				//ID_STD,
				if(Checker.isStringNullOrEmpty(id_std)) {
					stmt.setNull(i++, java.sql.Types.INTEGER);
				}
				else {
					stmt.setInt(i++,Integer.parseInt(id_std));	
				}
				//PERNYATAAN_STD,
				if(Checker.isStringNullOrEmpty(isi)) {
					stmt.setNull(i++, java.sql.Types.VARCHAR);
				}
				else {
					stmt.setString(i++, isi);
				}
				//KDPST,
				if(Checker.isStringNullOrEmpty(target_kdpst)) {
					stmt.setNull(i++,java.sql.Types.VARCHAR);
				}
				else {
					stmt.setString(i++, target_kdpst);
				}
				//RASIONALE,
				if(Checker.isStringNullOrEmpty(rasionale)) {
					stmt.setNull(i++, java.sql.Types.VARCHAR);
				}
				else {
					stmt.setString(i++, rasionale.trim());
				}
				//AKTIF,
				stmt.setBoolean(i++,true); //aktif = artinya master isi std blum didiskontinue sama kepala spmi
				
				if(!Checker.isStringNullOrEmpty(tglsta)&&Checker.isStringNullOrEmpty(tglend)) {
					//posisi standard sedang aktif pas copy jadi ikut langsung aktif
					//TGL_MULAI_AKTIF,
					stmt.setDate(i++, java.sql.Date.valueOf(tglsta));
					//TGL_STOP_AKTIF,
					stmt.setNull(i++, java.sql.Types.DATE);
				}
				else {
					//diluar itu berarti standard blum aktif jadi set null
					//TGL_MULAI_AKTIF,
					stmt.setNull(i++, java.sql.Types.DATE);
					//TGL_STOP_AKTIF,
					stmt.setNull(i++, java.sql.Types.DATE);
				
				}
				
				//SCOPE,
				if(Checker.isStringNullOrEmpty(target_kdpst)) {
					if(Checker.isStringNullOrEmpty(cakupan_std)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, cakupan_std);
					}
					
				}
				else {
					stmt.setString(i++, "prodi"); // kalo ada target_kdpst = scope prodi
				}
				//TIPE_PROSES_PENGAWASAN
				if(Checker.isStringNullOrEmpty(tipe_proses_pengawasan)) {	
					stmt.setNull(i++, java.sql.Types.VARCHAR);
				}
				else {
					stmt.setString(i++, tipe_proses_pengawasan);
				}
					
				updated = stmt.executeUpdate();
				
				if(updated>0) {
        		//berhasil input
        		//GET ID
    				stmt = con.prepareStatement("select ID from STANDARD_ISI_TABLE order by ID desc limit 1");
    				rs = stmt.executeQuery();
    				rs.next();
    				id_std_isi_created = rs.getInt(1);
    				//System.out.println("updated id = "+id_std_isi_created);
        		//versi 1 = krn ini yg pertama
    				stmt=con.prepareStatement("INSERT INTO STANDARD_VERSION(ID_VERSI,ID_STD_ISI,ID_MANUAL_PENETAPAN,ID_MANUAL_PELAKSANAAN,ID_MANUAL_EVALUASI,ID_MANUAL_PENGENDALIAN,ID_MANUAL_PENINGKATAN,TGL_STA,TGL_END,TARGET_THSMS_1,TARGET_THSMS_2,TARGET_THSMS_3,TARGET_THSMS_4,TARGET_THSMS_5,TARGET_THSMS_6,PIHAK_TERKAIT,DOKUMEN_TERKAIT,TKN_INDIKATOR,NO_URUT_TAMPIL,TARGET_PERIOD_START,UNIT_PERIOD_USED,LAMA_NOMINAL_PER_PERIOD,TARGET_THSMS_1_UNIT,TARGET_THSMS_2_UNIT,TARGET_THSMS_3_UNIT,TARGET_THSMS_4_UNIT,TARGET_THSMS_5_UNIT,TARGET_THSMS_6_UNIT,PIHAK_MONITOR,TKN_PARAMETER,AKTIF,KDPST)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    				i=1;
    				//ID_VERSI
    				stmt.setInt(i++, 1); //copy = jadi versi 1
    				//ID_STD_ISI
    				if(Checker.isStringNullOrEmpty(""+id_std_isi_created)) {
    					stmt.setNull(i++, java.sql.Types.INTEGER);
    				}
    				else {
    					stmt.setInt(i++, id_std_isi_created);
    				}
    				//ID_MANUAL_PENETAPAN
    				stmt.setNull(i++, java.sql.Types.VARCHAR);//copy = blum ada manual
    				//ID_MANUAL_PELAKSANAAN
    				stmt.setNull(i++, java.sql.Types.VARCHAR);//copy = blum ada manual
    				//ID_MANUAL_EVALUASI
    				stmt.setNull(i++, java.sql.Types.VARCHAR);//copy = blum ada manual
    				//ID_MANUAL_PENGENDALIAN
    				stmt.setNull(i++, java.sql.Types.VARCHAR);//copy = blum ada manual
    				//ID_MANUAL_PENINGKATAN
    				stmt.setNull(i++, java.sql.Types.VARCHAR);//copy = blum ada manual
    				//TGL_STA
    				stmt.setNull(i++, java.sql.Types.DATE);
    				//TGL_END
    				stmt.setNull(i++, java.sql.Types.DATE);
    				//TARGET_THSMS_1
    				if(Checker.isStringNullOrEmpty(thsms1)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, thsms1);
    				}
    				//TARGET_THSMS_2
    				if(Checker.isStringNullOrEmpty(thsms2)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, thsms2);
    				}
    				//TARGET_THSMS_3
    				if(Checker.isStringNullOrEmpty(thsms3)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, thsms3);
    				}
    				//TARGET_THSMS_4
    				if(Checker.isStringNullOrEmpty(thsms4)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, thsms4);
    				}
    				//TARGET_THSMS_5
    				if(Checker.isStringNullOrEmpty(thsms5)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, thsms5);
    				}
    				//TARGET_THSMS_6
    				if(Checker.isStringNullOrEmpty(thsms6)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, thsms6);
    				}
    				//PIHAK_TERKAIT
    				if(Checker.isStringNullOrEmpty(pihak)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, pihak);
    				}
    				//DOKUMEN_TERKAIT
    				if(Checker.isStringNullOrEmpty(tkn_doc)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, tkn_doc);
    				}
    				//TKN_INDIKATOR
    				if(Checker.isStringNullOrEmpty(tkn_indikator)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, tkn_indikator);
    				}
    				//NO_URUT_TAMPIL
    				stmt.setInt(i++, 0);
    				//TARGET_PERIOD_START
    				if(Checker.isStringNullOrEmpty(periode_awal)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, periode_awal);
    				}
    				//UNIT_PERIOD_USED
    				if(Checker.isStringNullOrEmpty(unit_period)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, unit_period);
    				}
    				//LAMA_NOMINAL_PER_PERIOD
    				if(Checker.isStringNullOrEmpty(lama_per_period)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, lama_per_period);
    				}
    				//TARGET_THSMS_1_UNIT
    				if(Checker.isStringNullOrEmpty(target_unit1)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, target_unit1);
    				}
    				//TARGET_THSMS_2_UNIT
    				if(Checker.isStringNullOrEmpty(target_unit2)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, target_unit2);
    				}
    				//TARGET_THSMS_3_UNIT
    				if(Checker.isStringNullOrEmpty(target_unit3)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, target_unit3);
    				}
    				//TARGET_THSMS_4_UNIT
    				if(Checker.isStringNullOrEmpty(target_unit4)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, target_unit4);
    				}
    				//TARGET_THSMS_5_UNIT
    				if(Checker.isStringNullOrEmpty(target_unit5)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, target_unit4);
    				}
    				//TARGET_THSMS_6_UNIT
    				if(Checker.isStringNullOrEmpty(target_unit6)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, target_unit6);
    				}
    				//PIHAK_MONITOR
    				if(Checker.isStringNullOrEmpty(pihak)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, pihak);
    				}
    				//TKN_PARAMETER
    				if(Checker.isStringNullOrEmpty(tkn_param)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, tkn_param);
    				}
    				//AKTIF
    				stmt.setBoolean(i++, true);
    				//KDPST)
    				stmt.setString(i++, target_kdpst);
    				
    				stmt.executeUpdate();
    			}
    		}
    	}catch (NamingException e) {
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
    	return id_std_isi_created;
    }

    public int revisiStandarIsi(String token_getInfoStd) {
    	int id_std_isi_created=0;
    	//String tkn_doc=null;
    	
    	StringTokenizer st = new StringTokenizer(token_getInfoStd,"`");
		
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
		/*
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
		*/
		//dokumen+"`"+
		String tkn_doc = st.nextToken();
		/*
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
		*/
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
		
		
    	try {
    		boolean std_sdh_ada = false;
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		String sql = "SELECT count(*) FROM STANDARD_ISI_TABLE where TRIM(PERNYATAAN_STD)=? and KDPST=? and SCOPE=?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, isi.trim());
			if(Checker.isStringNullOrEmpty(kdpst_std)) {
    			//berarti copy untuk buat standar baru agar mempermudah karena banyak kemiripan pada dokumen / pihak terkait,
    			//namun isi_std & kdpst tidak boleh sama, karena tidak boleh ada duplikate, yg berbeda adalah versi!!!
				stmt.setNull(2, java.sql.Types.VARCHAR);
    		}
    		else {
    			//berarti copy untuk buat standar baru KHUSUS UNTUK PRODI TERTENTU agar mempermudah karena banyak kemiripan pada dokumen / pihak terkait,
    			//namun isi_std & kdpst tidak boleh sama, karena tidak boleh ada duplikate, yg berbeda adalah versi!!!
    			stmt.setString(2, kdpst_std);
    		}
			//check scope/cakupan standar
			if(Checker.isStringNullOrEmpty(cakupan_std)) {
    			stmt.setNull(3, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(3, cakupan_std);
    		}
				
			rs = stmt.executeQuery();
			int counter = 0;
			if(rs.next()) {
				counter = rs.getInt(1);
			}
				
				
			int updated=0;
			if(counter>0) {
					//standar isi ini sudah ada, tidak bisa insert (DUPLICATE);
			}
			else {
				//go ahead insert
				Vector vIns = new Vector();
	    		ListIterator lins = vIns.listIterator();
				
				sql = "INSERT INTO STANDARD_ISI_TABLE(ID_STD,PERNYATAAN_STD,KDPST,RASIONALE,AKTIF,TGL_MULAI_AKTIF,TGL_STOP_AKTIF,SCOPE,TIPE_PROSES_PENGAWASAN)values(?,?,?,?,?,?,?,?,?)"; 
				stmt = con.prepareStatement(sql);
				int i=1;
				//ID_STD,
				if(Checker.isStringNullOrEmpty(id_std)) {
					stmt.setNull(i++, java.sql.Types.INTEGER);
				}
				else {
					stmt.setInt(i++,Integer.parseInt(id_std));	
				}
				//PERNYATAAN_STD,
				if(Checker.isStringNullOrEmpty(isi)) {
					stmt.setNull(i++, java.sql.Types.VARCHAR);
				}
				else {
					stmt.setString(i++, isi);
				}
				//KDPST,
				if(Checker.isStringNullOrEmpty(kdpst_std)) {
					stmt.setNull(i++,java.sql.Types.VARCHAR);
				}
				else {
					stmt.setString(i++, kdpst_std);
				}
				//RASIONALE,
				if(Checker.isStringNullOrEmpty(rasionale)) {
					stmt.setNull(i++, java.sql.Types.VARCHAR);
				}
				else {
					stmt.setString(i++, rasionale.trim());
				}
				//AKTIF,
				stmt.setBoolean(i++,true); //aktif = master isi  standar belum didiskontinue ma kepala SPMI 
				//TGL_MULAI_AKTIF,
				stmt.setNull(i++, java.sql.Types.DATE);
				//TGL_STOP_AKTIF,
				stmt.setNull(i++, java.sql.Types.DATE);
				//SCOPE,
				if(Checker.isStringNullOrEmpty(kdpst_std)) {
					if(Checker.isStringNullOrEmpty(cakupan_std)) {
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(i++, cakupan_std);
					}
					
				}
				else {
					stmt.setString(i++, "prodi"); // kalo ada target_kdpst = scope prodi
				}
				//TIPE_PROSES_PENGAWASAN
				if(Checker.isStringNullOrEmpty(tipe_proses_pengawasan)) {	
					stmt.setNull(i++, java.sql.Types.VARCHAR);
				}
				else {
					stmt.setString(i++, tipe_proses_pengawasan);
				}
					
				updated = stmt.executeUpdate();
				
				if(updated>0) {
        		//berhasil input
        		//GET ID
    				stmt = con.prepareStatement("select ID from STANDARD_ISI_TABLE order by ID desc limit 1");
    				rs = stmt.executeQuery();
    				rs.next();
    				id_std_isi_created = rs.getInt(1);
    				//System.out.println("updated id = "+id_std_isi_created);
        		//versi 1 = krn ini yg pertama
    				stmt=con.prepareStatement("INSERT INTO STANDARD_VERSION(ID_VERSI,ID_STD_ISI,ID_MANUAL_PENETAPAN,ID_MANUAL_PELAKSANAAN,ID_MANUAL_EVALUASI,ID_MANUAL_PENGENDALIAN,ID_MANUAL_PENINGKATAN,TGL_STA,TGL_END,TARGET_THSMS_1,TARGET_THSMS_2,TARGET_THSMS_3,TARGET_THSMS_4,TARGET_THSMS_5,TARGET_THSMS_6,PIHAK_TERKAIT,DOKUMEN_TERKAIT,TKN_INDIKATOR,NO_URUT_TAMPIL,TARGET_PERIOD_START,UNIT_PERIOD_USED,LAMA_NOMINAL_PER_PERIOD,TARGET_THSMS_1_UNIT,TARGET_THSMS_2_UNIT,TARGET_THSMS_3_UNIT,TARGET_THSMS_4_UNIT,TARGET_THSMS_5_UNIT,TARGET_THSMS_6_UNIT,PIHAK_MONITOR,TKN_PARAMETER,AKTIF,KDPST)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    				i=1;
    				//ID_VERSI
    				stmt.setInt(i++, Integer.parseInt(versi)+1); //copy = jadi versi 1
    				//ID_STD_ISI
    				if(Checker.isStringNullOrEmpty(""+id_std_isi_created)) {
    					stmt.setNull(i++, java.sql.Types.INTEGER);
    				}
    				else {
    					stmt.setInt(i++, id_std_isi_created);
    				}
    				//ID_MANUAL_PENETAPAN
    				stmt.setNull(i++, java.sql.Types.VARCHAR);//copy = blum ada manual
    				//ID_MANUAL_PELAKSANAAN
    				stmt.setNull(i++, java.sql.Types.VARCHAR);//copy = blum ada manual
    				//ID_MANUAL_EVALUASI
    				stmt.setNull(i++, java.sql.Types.VARCHAR);//copy = blum ada manual
    				//ID_MANUAL_PENGENDALIAN
    				stmt.setNull(i++, java.sql.Types.VARCHAR);//copy = blum ada manual
    				//ID_MANUAL_PENINGKATAN
    				stmt.setNull(i++, java.sql.Types.VARCHAR);//copy = blum ada manual
    				//TGL_STA
    				stmt.setNull(i++, java.sql.Types.DATE);
    				//TGL_END
    				stmt.setNull(i++, java.sql.Types.DATE);
    				//TARGET_THSMS_1
    				if(Checker.isStringNullOrEmpty(thsms1)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, thsms1);
    				}
    				//TARGET_THSMS_2
    				if(Checker.isStringNullOrEmpty(thsms2)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, thsms2);
    				}
    				//TARGET_THSMS_3
    				if(Checker.isStringNullOrEmpty(thsms3)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, thsms3);
    				}
    				//TARGET_THSMS_4
    				if(Checker.isStringNullOrEmpty(thsms4)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, thsms4);
    				}
    				//TARGET_THSMS_5
    				if(Checker.isStringNullOrEmpty(thsms5)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, thsms5);
    				}
    				//TARGET_THSMS_6
    				if(Checker.isStringNullOrEmpty(thsms6)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, thsms6);
    				}
    				//PIHAK_TERKAIT
    				if(Checker.isStringNullOrEmpty(pihak)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, pihak);
    				}
    				//DOKUMEN_TERKAIT
    				if(Checker.isStringNullOrEmpty(tkn_doc)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, tkn_doc);
    				}
    				//TKN_INDIKATOR
    				if(Checker.isStringNullOrEmpty(tkn_indikator)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, tkn_indikator);
    				}
    				//NO_URUT_TAMPIL
    				stmt.setInt(i++, 0);
    				//TARGET_PERIOD_START
    				if(Checker.isStringNullOrEmpty(periode_awal)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, periode_awal);
    				}
    				//UNIT_PERIOD_USED
    				if(Checker.isStringNullOrEmpty(unit_period)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, unit_period);
    				}
    				//LAMA_NOMINAL_PER_PERIOD
    				if(Checker.isStringNullOrEmpty(lama_per_period)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, lama_per_period);
    				}
    				//TARGET_THSMS_1_UNIT
    				if(Checker.isStringNullOrEmpty(target_unit1)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, target_unit1);
    				}
    				//TARGET_THSMS_2_UNIT
    				if(Checker.isStringNullOrEmpty(target_unit2)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, target_unit2);
    				}
    				//TARGET_THSMS_3_UNIT
    				if(Checker.isStringNullOrEmpty(target_unit3)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, target_unit3);
    				}
    				//TARGET_THSMS_4_UNIT
    				if(Checker.isStringNullOrEmpty(target_unit4)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, target_unit4);
    				}
    				//TARGET_THSMS_5_UNIT
    				if(Checker.isStringNullOrEmpty(target_unit5)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, target_unit4);
    				}
    				//TARGET_THSMS_6_UNIT
    				if(Checker.isStringNullOrEmpty(target_unit6)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, target_unit6);
    				}
    				//PIHAK_MONITOR
    				if(Checker.isStringNullOrEmpty(pihak)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, pihak);
    				}
    				//TKN_PARAMETER
    				if(Checker.isStringNullOrEmpty(tkn_param)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, tkn_param);
    				}
    				//AKTIF
    				stmt.setBoolean(i++, true);
    				//KDPST)
    				if(Checker.isStringNullOrEmpty(kdpst_std)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, kdpst_std);	
    				}
    				
    				
    				stmt.executeUpdate();
    			}
    		}
    	}catch (NamingException e) {
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
    	return id_std_isi_created;
    }

    
    public int updateNuStandarIsi(String id_std_isi,String[]pihak_terkait, String isi_std, String[]doc_terkait, String rasionale) {
    	int updated=0;
    	try {
    		updated = updateNuStandarIsi(id_std_isi, pihak_terkait, isi_std, doc_terkait, rasionale, 1);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	/*
    	String tkn_doc=null;
    	if(!Checker.isStringNullOrEmpty(rasionale)) {
    		try {
        		//String ipAddr =  request.getRemoteAddr();
    		if(doc_terkait!=null && doc_terkait.length>0) {
    			
    			for(int i=0;i<doc_terkait.length;i++) {
    				if(!Checker.isStringNullOrEmpty(doc_terkait[i])) {
    					if(tkn_doc==null) {
        					tkn_doc = new String(doc_terkait[i]);
        				}
        				else {
        					tkn_doc=tkn_doc+"`"+doc_terkait[i];
        				}
    				}
    				
    			}
    		}
    		Vector vIns = new Vector();
    		ListIterator lins = vIns.listIterator();
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//stmt = con.prepareStatement("insert into STANDARD_ISI_TABLE(PERNYATAAN_STD,RASIONALE)values(?,?)");
        	stmt = con.prepareStatement("update STANDARD_ISI_TABLE set PERNYATAAN_STD=?,RASIONALE=? where ID=?");
        	if(Checker.isStringNullOrEmpty(isi_std)) {
    			stmt.setString(1, "null");
    		}
    		else {
    			stmt.setString(1, isi_std);
    		}
        	//if(Checker.isStringNullOrEmpty(rasionale)) {
    		//	stmt.setNull(2, java.sql.Types.VARCHAR);
    		//}
    		//else {
    		stmt.setString(2, rasionale);
    		//}
        	stmt.setInt(3, Integer.parseInt(id_std_isi));
        	int i = stmt.executeUpdate();
        	
        	
        		
        	//KARENA INPUT BARU JADI INI ADALAH VERSI 1
        	//update versi pertama 
        	stmt=con.prepareStatement("update STANDARD_VERSION set PIHAK_TERKAIT=?,DOKUMEN_TERKAIT=? where ID_VERSI=1 and ID_STD_ISI=?");
        	//stmt=con.prepareStatement("insert into STANDARD_VERSION(ID_VERSI,ID_STD_ISI,PIHAK_TERKAIT,DOKUMEN_TERKAIT)values(?,?,?,?)");
        	
        	String pihak = null;
        	if(pihak_terkait!=null && pihak_terkait.length>0) {
        		for(int j=0;j<pihak_terkait.length;j++) {
        			if(pihak==null) {
        				pihak = new String(pihak_terkait[j].trim());
        			}
        			else {
        				pihak = pihak +"`"+ pihak_terkait[j].trim();
        			}
        		}
        	}
        	if(Checker.isStringNullOrEmpty(pihak)) {
        		stmt.setNull(1, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(1, pihak);
        	}
        	if(Checker.isStringNullOrEmpty(tkn_doc)) {
        		stmt.setNull(2, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(2, tkn_doc);
        	}
        	stmt.setInt(3, Integer.parseInt(id_std_isi));
        		
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
    	}
    	*/
    	return updated;
    }
    
    public int updateNuStandarIsi(String id_std_isi,String[]pihak_terkait, String isi_std, String[]doc_terkait, String rasionale, int versi) {
    	int updated=0;
    	String tkn_doc=null;
    	if(!Checker.isStringNullOrEmpty(rasionale)) {
    		try {
        		//String ipAddr =  request.getRemoteAddr();
    			if(doc_terkait!=null && doc_terkait.length>0) {
    			
    				for(int i=0;i<doc_terkait.length;i++) {
    					if(!Checker.isStringNullOrEmpty(doc_terkait[i])) {
    						if(tkn_doc==null) {
    							tkn_doc = new String(doc_terkait[i]);
    						}	
    						else {
    							tkn_doc=tkn_doc+"`"+doc_terkait[i];
    						}
    					}
    					
    				}
    			}
    			Vector vIns = new Vector();
    			ListIterator lins = vIns.listIterator();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
        	//	stmt = con.prepareStatement("insert into STANDARD_ISI_TABLE(PERNYATAAN_STD,RASIONALE)values(?,?)");
    			stmt = con.prepareStatement("update STANDARD_ISI_TABLE set PERNYATAAN_STD=?,RASIONALE=? where ID=?");
    			if(Checker.isStringNullOrEmpty(isi_std)) {
    				stmt.setString(1, "null");
    			}
    			else {
    				stmt.setString(1, isi_std);
    			}
    			//if(Checker.isStringNullOrEmpty(rasionale)) {
    			//	stmt.setNull(2, java.sql.Types.VARCHAR);
    			//}
    			//else {
    			stmt.setString(2, rasionale);
    		//	}
    			stmt.setInt(3, Integer.parseInt(id_std_isi));
    			int i = stmt.executeUpdate();
        	
        	
        		
    			//KARENA INPUT BARU JADI INI ADALAH VERSI 1
    			//update versi pertama 
    			if(versi<2) { 
    				versi = 1;
    			}
    			
    			stmt=con.prepareStatement("update STANDARD_VERSION set PIHAK_TERKAIT=?,DOKUMEN_TERKAIT=? where ID_VERSI="+versi+" and ID_STD_ISI=?");
    			//stmt=con.prepareStatement("insert into STANDARD_VERSION(ID_VERSI,ID_STD_ISI,PIHAK_TERKAIT,DOKUMEN_TERKAIT)values(?,?,?,?)");
        	
    			String pihak = null;
    			if(pihak_terkait!=null && pihak_terkait.length>0) {
    				for(int j=0;j<pihak_terkait.length;j++) {
    					if(pihak==null) {
    						pihak = new String(pihak_terkait[j].trim());
    					}
    					else {
    						pihak = pihak +"`"+ pihak_terkait[j].trim();
    					}
    				}
    			}
    			if(Checker.isStringNullOrEmpty(pihak)) {
    				stmt.setNull(1, java.sql.Types.VARCHAR);
    			}
    			else {
    				stmt.setString(1, pihak);
    			}
    			if(Checker.isStringNullOrEmpty(tkn_doc)) {
    				stmt.setNull(2, java.sql.Types.VARCHAR);
    			}
    			else {
    				stmt.setString(2, tkn_doc);
    			}
    			stmt.setInt(3, Integer.parseInt(id_std_isi));
    			
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
    	}
    	
    	return updated;
    }

}

package beans.dbase.spmi.riwayat.pengendalian;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.Converter;
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
 * Session Bean implementation class SrcHistKendal
 */
@Stateless
@LocalBean
public class SrcHistKendal extends SearchDb {
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
    public SrcHistKendal() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SrcHistKendal(String operatorNpm) {
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
    public SrcHistKendal(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    
    /*
     * deprecated,  jangan di[ake lagi
     */
    public Vector getListRiwayatPengendalian(int id_kendali, Vector v_cmd_scope_id, String tkn_list_jabatan_user) {
    	if(!Checker.isStringNullOrEmpty(tkn_list_jabatan_user)) {
    		if(!tkn_list_jabatan_user.startsWith("`")) {
    			tkn_list_jabatan_user = "`"+tkn_list_jabatan_user;
    		}
    		if(!tkn_list_jabatan_user.endsWith("`")) {
    			tkn_list_jabatan_user = tkn_list_jabatan_user+"`";
    		}
    	}
    	//System.out.println("tkn_list_jabatan_user="+tkn_list_jabatan_user);
    	
    	Vector v = null;
    	ListIterator li = null;
    	if(v_cmd_scope_id!=null && v_cmd_scope_id.size()>0) {
    		Vector v_cmd_scope = new Vector();
    		String tkn_scope_kdpst = "`";
    		v_cmd_scope = Converter.convertVscopeidToKdpst(v_cmd_scope_id);
    		v_cmd_scope = Converter.convertVscopeKdpstToDistinctInfoKdpst(v_cmd_scope, "KDPSTMSPST");
    		ListIterator lis = v_cmd_scope.listIterator();
    		while(lis.hasNext()) {
    			String kdpst = (String)lis.next();
    			tkn_scope_kdpst = tkn_scope_kdpst+kdpst+"`";
    		}
    		//System.out.println("tkn_scope_kdpst="+tkn_scope_kdpst);
    		try {
        		String id_hist = null;
        		String tgl_sidak = null;
        		String time_sidak = null;
    			String timestamp = null;
    			String periode_ke = null;
    			String nilai_ril_capaian = null;
    			String sikon = null;
    			String analisa =null;
    			String rekomendasi = null;
    			String npm_surveyer = null;
    			String target_capaian = null;
    			String unit_used = null;
    			String npm_civitas_underwatch = null;
    			String tipe_sarpras_underwatch = null;
    			String sarpras_underwatch = null;
    			String kdpst_underwatch = null;
    			String next_tgl_sidak = null;
        		String next_time_sidak = null;
    			//tabel eval
    			String id_eval=null;
    			String npm_eval=null;
    			String tgl_eval=null;
    			String waktu_eval=null;
    			String timestamp_eval=null;
    			String rasionale_eval=null;
    			String tindakan_eval=null;
    			//tabel manual
    			//VERSI_ID,STD_ISI_ID,TARGET_CAPAIAN_KONDISI,NAMA_JABATAN_PENGAWAS,NAMA_JABATAN_INPUT_AWAL,TIPE_DATA_SARPRAS,CATAT_DATA_CIVITAS
    			String versi_id_man = null;
    			String std_isi_id_man = null;
    			String jab_pengendali_man = null;
    			String jab_surveyor_man = null;
    			String target_kondisi_man = null;
    			String tipe_sarpras_man = null;
    			String catat_civitas_man = null;
        		
            	Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	stmt = con.prepareStatement("select * from STANDARD_RIWAYAT_PENGENDALIAN a left join STANDARD_RIWAYAT_EVALUASI b on ID_HIST=ID_HIST_KENDAL where a.ID_KENDALI=? order by TGL_SIDAK desc,WAKTU_SIDAK desc");
            	stmt.setInt(1, id_kendali);
            	rs = stmt.executeQuery();
            	
            	if(rs.next()) {
            		v = new Vector();
            		li = v.listIterator();
            		do {
            			Vector v_tmp = new Vector();
                    	ListIterator li_tmp = v_tmp.listIterator();
                    	id_hist = ""+rs.getInt("ID_HIST");
                		li_tmp.add(id_hist);
                		tgl_sidak = ""+rs.getDate("TGL_SIDAK");
                		li_tmp.add(tgl_sidak);
            			time_sidak = ""+rs.getTime("WAKTU_SIDAK");
            			li_tmp.add(time_sidak);
            			timestamp = ""+rs.getTimestamp("SIDAK_TIMESTAMP");
            			li_tmp.add(timestamp);
            			periode_ke = ""+rs.getInt("PERIODE_SIDAK");
            			li_tmp.add(periode_ke);
            			nilai_ril_capaian = ""+rs.getDouble("NILAI_CAPAIAN_RIL_SURVEY");
            			li_tmp.add(nilai_ril_capaian);
            			sikon = ""+rs.getString("SIKON_AT_SIDAK");
            			li_tmp.add(sikon);
            			analisa =""+rs.getString("HASIL_ANALISA");
            			li_tmp.add(analisa);
            			rekomendasi = ""+rs.getString("REKOMENDASI");
            			li_tmp.add(rekomendasi);
            			npm_surveyer = ""+rs.getString("NPM_PENGAWAS");
            			li_tmp.add(npm_surveyer);
            			target_capaian = ""+rs.getDouble("NILAI_TARGET_CAPAIAN");
            			li_tmp.add(target_capaian);
            			unit_used = ""+rs.getString("UNIT_USED_BY_TARGET");
            			li_tmp.add(unit_used);
            			npm_civitas_underwatch = ""+rs.getString("NPM_CIVITAS_YG_DIAWASI");
            			li_tmp.add(npm_civitas_underwatch);
            			tipe_sarpras_underwatch = ""+rs.getString("KET_TIPE_SARPRAS");
            			li_tmp.add(tipe_sarpras_underwatch);
            			sarpras_underwatch = ""+rs.getInt("ID_SARPRAS");
            			li_tmp.add(sarpras_underwatch);
            			kdpst_underwatch = ""+rs.getString("KDPST_KENDALI");
            			li_tmp.add(kdpst_underwatch);
            			
            			
            			//String target_kondisi = ""+rs.getString("TARGET_CAPAIAN_KONDISI");
            			//EVAL TABLE
            			id_eval=""+rs.getInt("ID_EVAL");
            			li_tmp.add(id_eval);
            			npm_eval=""+rs.getString("NPM_EVALUATOR");
            			li_tmp.add(npm_eval);
            			tgl_eval=""+rs.getDate("TGL_EVAL");
            			li_tmp.add(tgl_eval);
            			waktu_eval=""+rs.getTime("WAKTU_EVAL");
            			li_tmp.add(waktu_eval);
            			timestamp_eval=""+rs.getTimestamp("EVAL_TIMESTAMP");
            			li_tmp.add(timestamp_eval);
            			rasionale_eval=""+rs.getString("RASIONALE_EVAL");
            			//v_tmp.add(rasionale_eval);
            			li_tmp.add(rasionale_eval);
            			tindakan_eval=""+rs.getString("TINDAKAN_EVAL");
            			//v_tmp.add(tindakan_eval);
            			li_tmp.add(tindakan_eval);
            			next_tgl_sidak = ""+rs.getDate("NEXT_TGL_SIDAK");
                		li_tmp.add(next_tgl_sidak);
            			next_time_sidak = ""+rs.getTime("NEXT_WAKTU_SIDAK");
            			li_tmp.add(next_time_sidak);
            		
            			//System.out.println(v_tmp.size());
            			li.add(v_tmp);
            		}
            		while(rs.next());
            		
            	}
            	//System.out.println(v.size());
            	if(v!=null && v.size()>0) {
            		
            		stmt = con.prepareStatement("select VERSI_ID,STD_ISI_ID,TARGET_CAPAIAN_KONDISI,NAMA_JABATAN_PENGAWAS,NAMA_JABATAN_INPUT_AWAL,TIPE_DATA_SARPRAS,CATAT_DATA_CIVITAS from STANDARD_MANUAL_PENGENDALIAN  where ID_KENDALI=?");
                	
            		li = v.listIterator();
            		while(li.hasNext()) {
            			Vector v_tmp = (Vector)li.next();
                		ListIterator li_tmp = v_tmp.listIterator();
                		while(li_tmp.hasNext()) {
                			id_hist = (String)li_tmp.next();
                			tgl_sidak = (String)li_tmp.next();
                    		time_sidak = (String)li_tmp.next();
                			timestamp = (String)li_tmp.next();
                			periode_ke = (String)li_tmp.next();
                			nilai_ril_capaian = (String)li_tmp.next();
                			sikon = (String)li_tmp.next();
                			analisa = (String)li_tmp.next();
                			rekomendasi = (String)li_tmp.next();
                			npm_surveyer = (String)li_tmp.next();
                			target_capaian = (String)li_tmp.next();
                			unit_used = (String)li_tmp.next();
                			npm_civitas_underwatch = (String)li_tmp.next();
                			tipe_sarpras_underwatch = (String)li_tmp.next();
                			sarpras_underwatch = (String)li_tmp.next();
                			kdpst_underwatch = (String)li_tmp.next();
                			
                			id_eval= (String)li_tmp.next();
                			npm_eval= (String)li_tmp.next();
                			tgl_eval= (String)li_tmp.next();
                			waktu_eval= (String)li_tmp.next();
                			timestamp_eval= (String)li_tmp.next();
                			rasionale_eval= (String)li_tmp.next();
                			tindakan_eval= (String)li_tmp.next();
                			
                			next_tgl_sidak = (String)li_tmp.next();
                    		next_time_sidak = (String)li_tmp.next();
                    		
                			stmt.setInt(1, id_kendali);
                			rs = stmt.executeQuery();
                        	if(rs.next()) {
                        		//VERSI_ID,STD_ISI_ID,TARGET_CAPAIAN_KONDISI,NAMA_JABATAN_PENGAWAS,NAMA_JABATAN_INPUT_AWAL,TIPE_DATA_SARPRAS,CATAT_DATA_CIVITAS
                        		versi_id_man = ""+rs.getInt("VERSI_ID");
                        		if(Checker.isStringNullOrEmpty(versi_id_man)) {
                        			versi_id_man="null";
                        		}
                        		
                    			std_isi_id_man = ""+rs.getInt("STD_ISI_ID");
                        		if(Checker.isStringNullOrEmpty(std_isi_id_man)) {
                        			std_isi_id_man="null";
                        		}
                        		
                    			jab_pengendali_man = rs.getString("NAMA_JABATAN_PENGAWAS");
                        		if(Checker.isStringNullOrEmpty(jab_pengendali_man)) {
                        			jab_pengendali_man="null";
                        		}
                        		
                    			jab_surveyor_man = rs.getString("NAMA_JABATAN_INPUT_AWAL");
                        		if(Checker.isStringNullOrEmpty(jab_surveyor_man)) {
                        			jab_surveyor_man="null";
                        		}
                        		
                        		target_kondisi_man = rs.getString("TARGET_CAPAIAN_KONDISI");
                        		if(Checker.isStringNullOrEmpty(target_kondisi_man)) {
                        			target_kondisi_man="null";
                        		}
                        		
                        		tipe_sarpras_man = rs.getString("TIPE_DATA_SARPRAS");
                        		if(Checker.isStringNullOrEmpty(tipe_sarpras_man)) {
                        			tipe_sarpras_man="null";
                        		}
                        		
                        		catat_civitas_man = ""+rs.getBoolean("CATAT_DATA_CIVITAS");
                        		//System.out.println("catat_civitas_man at bean = "+catat_civitas_man);
                        	}
                        	li_tmp.add(""+versi_id_man);
                        	li_tmp.add(""+std_isi_id_man);
                        	li_tmp.add(""+jab_pengendali_man);
                        	li_tmp.add(""+jab_surveyor_man);
                        	li_tmp.add(""+target_kondisi_man);
                        	li_tmp.add(""+tipe_sarpras_man);
                        	li_tmp.add(""+catat_civitas_man);
                		}
                		li.set(v_tmp);
            		}
            		
            	}
            	
            	//System.out.println(v.size());
            	//filter scopenya disini
            	/*
            	if(v!=null && v.size()>0) {
            		stmt = con.prepareStatement("select PIHAK_TERKAIT,PIHAK_MONITOR from STANDARD_VERSION where ID_VERSI=? and ID_STD_ISI=?");
                	
            		li = v.listIterator();
            		while(li.hasNext()) {
            			Vector v_tmp = (Vector)li.next();
                		ListIterator li_tmp = v_tmp.listIterator();
                		while(li_tmp.hasNext()) {
                			id_hist = (String)li_tmp.next();
                			tgl_sidak = (String)li_tmp.next();
                    		time_sidak = (String)li_tmp.next();
                			timestamp = (String)li_tmp.next();
                			periode_ke = (String)li_tmp.next();
                			nilai_ril_capaian = (String)li_tmp.next();
                			sikon = (String)li_tmp.next();
                			analisa = (String)li_tmp.next();
                			rekomendasi = (String)li_tmp.next();
                			npm_surveyer = (String)li_tmp.next();
                			target_capaian = (String)li_tmp.next();
                			unit_used = (String)li_tmp.next();
                			npm_civitas_underwatch = (String)li_tmp.next();
                			tipe_sarpras_underwatch = (String)li_tmp.next();
                			sarpras_underwatch = (String)li_tmp.next();
                			kdpst_underwatch = (String)li_tmp.next();
                			
                			id_eval= (String)li_tmp.next();
                			npm_eval= (String)li_tmp.next();
                			tgl_eval= (String)li_tmp.next();
                			waktu_eval= (String)li_tmp.next();
                			timestamp_eval= (String)li_tmp.next();
                			rasionale_eval= (String)li_tmp.next();
                			tindakan_eval= (String)li_tmp.next();
                			
                			next_tgl_sidak = (String)li_tmp.next();
                    		next_time_sidak = (String)li_tmp.next();
                    		
                			versi_id_man= (String)li_tmp.next();
                			std_isi_id_man= (String)li_tmp.next();
                			jab_pengendali_man= (String)li_tmp.next();
                			jab_surveyor_man= (String)li_tmp.next();
                			//System.out.println("jab_pengendali_man="+jab_pengendali_man);
                			//System.out.println("jab_surveyor_man="+jab_surveyor_man);
                			target_kondisi_man= (String)li_tmp.next();
                			tipe_sarpras_man= (String)li_tmp.next();
                			catat_civitas_man= (String)li_tmp.next();
                			String list_pihak_terkait_std = "null";
                			String list_pihak_pengawas_std = "null";
                			//cek scope prodi
                        	if(!Checker.isStringNullOrEmpty(kdpst_underwatch) && !tkn_scope_kdpst.contains("`"+kdpst_underwatch+"`")) {
                        		li.remove();
                        		//System.out.println("remove");
                        	}
                        	else {
                        		//cek apa ada keterkaitan
                        		stmt.setInt(1, Integer.parseInt(versi_id_man));
                        		stmt.setInt(2, Integer.parseInt(std_isi_id_man));
                        		rs = stmt.executeQuery();
                        		if(rs.next()) {
                        			list_pihak_terkait_std = rs.getString(1);
                        			list_pihak_pengawas_std = rs.getString(2);
                        		}
                        		//System.out.println("list_pihak_terkait_std="+list_pihak_terkait_std);
                        		//System.out.println("list_pihak_pengawas_std="+list_pihak_pengawas_std);
                        		boolean terkait = false;
                        		if(!Checker.isStringNullOrEmpty(list_pihak_terkait_std)) {
                        			StringTokenizer st = null;
                        			if(list_pihak_terkait_std.contains("`")) {
                        				st = new StringTokenizer(list_pihak_terkait_std,"`");
                        			}
                        			else {
                        				//pake koma
                        				st = new StringTokenizer(list_pihak_terkait_std,",");
                        			}
                        			while(st.hasMoreTokens() && !terkait) {
                        				String jab_terkait = st.nextToken();
                        				if(tkn_list_jabatan_user.contains(jab_terkait)) {
                        					terkait = true;
                        				}
                        			}
                        			if(!terkait) {
                        				if(!Checker.isStringNullOrEmpty(list_pihak_pengawas_std)) {
                                			st = null;
                                			if(list_pihak_pengawas_std.contains("`")) {
                                				st = new StringTokenizer(list_pihak_pengawas_std,"`");
                                			}
                                			else {
                                				//pake koma
                                				st = new StringTokenizer(list_pihak_pengawas_std,",");
                                			}
                                			while(st.hasMoreTokens() && !terkait) {
                                				String jab_terkait = st.nextToken();
                                				if(tkn_list_jabatan_user.contains(jab_terkait)) {
                                					terkait = true;
                                				}
                                			}
                                		}
                        			}
                        		}
                        		if(!terkait) {
                        			li.remove();
                        		}
                        	}
                		}
            		}	            		
            		
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
    	}
    	return v;
    }	
    
    public Vector getListRiwayatPengendalianAmi(int std_versi_id, int std_isi_id, int offset, int limit, String target_kdpst) {
    	/*
    	 * std_isi_id menentukan apakah ini generik ato untuk kdpst tertentu
    	 */
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
    		//String sql = "SELECT * FROM STANDARD_MANUAL_PENGENDALIAN A inner join RIWAYAT_EVALUASI_AMI B on (A.VERSI_ID=B.VERSI_ID and A.STD_ISI_ID=B.STD_ISI_ID and A.NORUT=B.NORUT) left join RIWAYAT_PENGENDALIAN_AMI C on B.ID_EVAL=C.ID_EVAL where  A.VERSI_ID=? and A.STD_ISI_ID=? and A.NORUT=? AND B.KDPST=? order by B.ID_EVAL desc limit ?,?";
    		String sql = "SELECT * FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on A.ID=B.ID_STD_ISI inner join RIWAYAT_EVALUASI_AMI C on (C.VERSI_ID=B.ID_VERSI and C.STD_ISI_ID=B.ID_STD_ISI) left join RIWAYAT_PENGENDALIAN_AMI D on C.ID_EVAL=D.ID_EVAL where A.ID=? and B.ID_VERSI=? and C.KDPST=? order by C.ID_EVAL desc limit ?,?";
    		
    		stmt = con.prepareStatement(sql);
    		stmt.setInt(1, std_isi_id);
    		stmt.setInt(2, std_versi_id);
    		stmt.setString(3, target_kdpst);
    		stmt.setInt(4, offset);
    		stmt.setInt(5, limit);
    		rs = stmt.executeQuery();
    		boolean first = true;
    		while(rs.next()) {
    			if(first) {
    				first = false;
    				v = new Vector();
    				li = v.listIterator();
    			}
    			String id_eval= rs.getString("C.ID_EVAL");
    			//String versi_id= rs.getString("VERSI_ID");
    			//String std_isi_id= rs.getString("STD_ISI_ID");
    			String norut = rs.getString("C.NORUT");
    			String npm_eval= rs.getString("C.NPM_EVALUATOR");
    			String tgl_eval= rs.getString("C.TGL_EVAL");
    			String waktu_eval= rs.getString("C.WAKTU_EVAL");
    			String eval_timestamp= rs.getString("C.EVAL_TIMESTAMP");
    			String sikon = rs.getString("C.KONDISI");
    			String analisa = rs.getString("C.ANALISA");
    			String rekomendasi= rs.getString("C.REKOMENDASI");
    			String tgl_next_eval= rs.getString("C.TGL_NEXT_EVAL");
    			String waktu_next_eval= rs.getString("C.WAKTU_NEXT_EVAL");
    			String target_val= rs.getString("C.TARGET_VALUE");
    			String real_val= rs.getString("C.REAL_VALUE");
    			
    			String tgl_sta = rs.getString("B.TGL_STA");
    			String tgl_end = rs.getString("B.TGL_END");
    			String target_thsms1 = rs.getString("B.TARGET_THSMS_1");
    			String target_thsms2 = rs.getString("B.TARGET_THSMS_2");
    			String target_thsms3 = rs.getString("B.TARGET_THSMS_3");
    			String target_thsms4 = rs.getString("B.TARGET_THSMS_4");
    			String target_thsms5 = rs.getString("B.TARGET_THSMS_5");
    			String target_thsms6 = rs.getString("B.TARGET_THSMS_6");
    			String target_unit = rs.getString("B.TARGET_THSMS_1_UNIT");
    			String aktif = rs.getString("B.AKTIF");
    			
    			String id_kendal = rs.getString("D.ID_KENDALI");
    			String rasionale_kendal = rs.getString("D.RASIONALE_TINDAKAN");
    			String tindakan_kendal = rs.getString("D.TINDAKAN_PENGENDALIAN");
    			String tgl_kendal = rs.getString("D.TGL_KENDALI");
    			String waktu_kendal = rs.getString("D.WAKTU_KENDAL");
    			String npm_kendal = rs.getString("D.NPM_PENGENDALI");
    			String kendal_timstamp = rs.getString("D.KENDALI_TIMESTAMP");
    			String ada_pelanggaran = rs.getString("D.PELANGGARAN");
    			String jenis_pelanggaran = rs.getString("D.JENIS_PELANGGARAN");
    			
    			
    			String tmp = id_eval+"~"+std_versi_id+"~"+std_isi_id+"~"+norut+"~"+npm_eval+"~"+tgl_eval+"~"+waktu_eval+"~"+eval_timestamp+"~"+sikon+"~"+analisa+"~"+rekomendasi+"~"+tgl_next_eval+"~"+waktu_next_eval+"~"+target_val+"~"+real_val+"~"+tgl_sta+"~"+tgl_end+"~"+target_thsms1+"~"+target_thsms2+"~"+target_thsms3+"~"+target_thsms4+"~"+target_thsms5+"~"+target_thsms6+"~"+target_unit+"~"+aktif+"~"+id_kendal+"~"+rasionale_kendal+"~"+tindakan_kendal+"~"+tgl_kendal+"~"+waktu_kendal+"~"+npm_kendal+"~"+kendal_timstamp+"~"+ada_pelanggaran+"~"+jenis_pelanggaran;
    			Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
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
    
    public Vector getListRiwayatPengendalian(int versi_id, int std_isi_id, int norut_man, int offset, int limit) {
    	/*
    	 * std_isi_id menentukan apakah ini generik ato untuk kdpst tertentu
    	 */
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
    		String sql = "select * from RIWAYAT_PENGENDALIAN A inner join STANDARD_MANUAL_PENGENDALIAN B on (A.VERSI_ID=B.VERSI_ID AND A.STD_ISI_ID=B.STD_ISI_ID AND A.NORUT=B.NORUT) where A.VERSI_ID=? AND A.STD_ISI_ID=? AND A.NORUT=? order by A.ID_PLAN desc limit ?,?";
    		stmt = con.prepareStatement(sql);
    		stmt.setInt(1, versi_id);
    		stmt.setInt(2, std_isi_id);
    		stmt.setInt(3, norut_man);
    		stmt.setInt(4, offset);
    		stmt.setInt(5, limit);
    		rs = stmt.executeQuery();
    		boolean first = true;
    		while(rs.next()) {
    			if(first) {
    				first = false;
    				v = new Vector();
    				li = v.listIterator();
    			}
    			String id_plan= rs.getString("A.ID_PLAN");
    			String tgl_sta= rs.getString("A.TGL_STA_KEGIATAN");
    			String waktu_sta= rs.getString("A.WAKTU_STA_KEGIATAN");
    			String tgl_end= rs.getString("A.TGL_END_KEGIATAN");
    			String waktu_end= rs.getString("A.WAKTU_END_KEGIATAN");
    			String stamp_sta= rs.getString("A.TIMESTAMP_KEGIATAN_STA");
    			String stamp_end= rs.getString("A.TIMESTAMP_KEGIATAN_END");
    			String nama_kegiatan= rs.getString("A.NAMA_KEGIATAN");
    			String jenis_kegiatan= rs.getString("A.JENIS_KEGIATAN");
    			String tujuan_kegiatan= rs.getString("A.KETERANGAN_SINGKAT_TUJUAN_KEGIATAN");
    			String isi_kegiatan= rs.getString("A.ISI_KEGIATAN");
    			String tkn_job_tanggung= rs.getString("A.PENANGGUNG_JAWAB_KEGIATAN");
    			String tkn_job_target= rs.getString("A.AUDIENCE_KEGIATAN");
    			String tkn_dok_kegiatan= rs.getString("A.DOKUMEN_KEGIATAN");
    			String hasil_kegiatan= rs.getString("A.KETERANGAN_HASIL_KEGIATAN");
    			String kegiatan_started= rs.getString("A.KEGIATAN_STARTED");
    			String kegiatan_ended= rs.getString("A.KEGIATAN_SELESAI");
    			String note_kegiatan= rs.getString("A.NOTE");
    			String tgl_rumus_set= rs.getString("A.TGL_RUMUS");
    			String tgl_cek_set= rs.getString("A.TGL_CEK");
    			String tgl_stuju_set= rs.getString("A.TGL_STUJU");
    			String tgl_tetap_set= rs.getString("A.TGL_TETAP");
    			String tgl_kendali_set= rs.getString("A.TGL_KENDALI");
    			String tgl_next_kegiatan= rs.getString("A.TGL_NEXT_KEGIATAN");
    			String waktu_next_kegiatan= rs.getString("A.WAKTU_NEXT_KEGIATAN");
    			
    			String tgl_sta_std = rs.getString("B.TGL_STA");
    			String tgl_end_std = rs.getString("B.TGL_END");
    			String tkn_jab_rumus = rs.getString("B.TKN_JAB_PERUMUS");
    			String tkn_jab_cek = rs.getString("B.TKN_JAB_PERIKSA");
    			String tkn_jab_stuju = rs.getString("B.TKN_JAB_SETUJU");
    			String tkn_jab_tetap = rs.getString("B.TKN_JAB_PENETAP");
    			String tkn_jab_kendali = rs.getString("B.TKN_JAB_KENDALI");
    			String tujuan = rs.getString("B.TUJUAN");
    			String lingkup = rs.getString("B.LINGKUP");
    			String definisi = rs.getString("B.DEFINISI");
    			String prosedur = rs.getString("B.PROSEDUR");
    			String kuali= rs.getString("B.KUALIFIKASI");
    			String doc = rs.getString("B.DOKUMEN");
    			String ref = rs.getString("B.REFERENSI");
    			
    			String tgl_rumus_std_set= rs.getString("A.TGL_RUMUS_STD");
    			String tgl_cek_std_set= rs.getString("A.TGL_CEK_STD");
    			String tgl_stuju_std_set= rs.getString("A.TGL_STUJU_STD");
    			String tgl_tetap_std_set= rs.getString("A.TGL_TETAP_STD");
    			String tgl_kendali_std_set= rs.getString("A.TGL_KENDALI_STD");
    			
    			String tmp = id_plan+"~"+versi_id+"~"+std_isi_id+"~"+norut_man+"~"+tgl_sta+"~"+waktu_sta+"~"+tgl_end+"~"+waktu_end+"~"+stamp_sta+"~"+stamp_end+"~"+nama_kegiatan+"~"+jenis_kegiatan+"~"+tujuan_kegiatan+"~"+isi_kegiatan+"~"+tkn_job_tanggung+"~"+tkn_job_target+"~"+tkn_dok_kegiatan+"~"+hasil_kegiatan+"~"+kegiatan_started+"~"+kegiatan_ended+"~"+note_kegiatan+"~"+tgl_rumus_set+"~"+tgl_cek_set+"~"+tgl_stuju_set+"~"+tgl_tetap_set+"~"+tgl_kendali_set+"~"+tgl_next_kegiatan+"~"+waktu_next_kegiatan+"~"+tgl_sta_std+"~"+tgl_end_std+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendali+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kuali+"~"+doc+"~"+ref+"~"+tgl_rumus_std_set+"~"+tgl_cek_std_set+"~"+tgl_stuju_std_set+"~"+tgl_tetap_std_set+"~"+tgl_kendali_std_set;
    			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
    			//System.out.println("TMP="+tmp);
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
    
    
    public Vector getListRiwayatPengendalianUmum(int versi_id, int id_std, int offset, int limit) {
    	/*
    	 * std_isi_id menentukan apakah ini generik ato untuk kdpst tertentu
    	 */
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
    		String sql = "select * from RIWAYAT_PENGENDALIAN_UMUM A inner join STANDARD_MANUAL_PENGENDALIAN_UMUM B on (A.VERSI_ID=B.VERSI_ID AND A.ID_STD=B.ID_STD) where A.VERSI_ID=? AND A.ID_STD=? order by A.ID_PLAN desc limit ?,?";
    		stmt = con.prepareStatement(sql);
    		stmt.setInt(1, versi_id);
    		stmt.setInt(2, id_std);
    		stmt.setInt(3, offset);
    		stmt.setInt(4, limit);
    		rs = stmt.executeQuery();
    		boolean first = true;
    		while(rs.next()) {
    			if(first) {
    				first = false;
    				v = new Vector();
    				li = v.listIterator();
    			}
    			String id_plan= rs.getString("A.ID_PLAN");
    			String tgl_sta= rs.getString("A.TGL_STA_KEGIATAN");
    			String waktu_sta= rs.getString("A.WAKTU_STA_KEGIATAN");
    			String tgl_end= rs.getString("A.TGL_END_KEGIATAN");
    			String waktu_end= rs.getString("A.WAKTU_END_KEGIATAN");
    			String stamp_sta= rs.getString("A.TIMESTAMP_KEGIATAN_STA");
    			String stamp_end= rs.getString("A.TIMESTAMP_KEGIATAN_END");
    			String nama_kegiatan= rs.getString("A.NAMA_KEGIATAN");
    			String jenis_kegiatan= rs.getString("A.JENIS_KEGIATAN");
    			String tujuan_kegiatan= rs.getString("A.KETERANGAN_SINGKAT_TUJUAN_KEGIATAN");
    			String isi_kegiatan= rs.getString("A.ISI_KEGIATAN");
    			String tkn_job_tanggung= rs.getString("A.PENANGGUNG_JAWAB_KEGIATAN");
    			String tkn_job_target= rs.getString("A.AUDIENCE_KEGIATAN");
    			String tkn_dok_kegiatan= rs.getString("A.DOKUMEN_KEGIATAN");
    			String hasil_kegiatan= rs.getString("A.KETERANGAN_HASIL_KEGIATAN");
    			String kegiatan_started= rs.getString("A.KEGIATAN_STARTED");
    			String kegiatan_ended= rs.getString("A.KEGIATAN_SELESAI");
    			String note_kegiatan= rs.getString("A.NOTE");
    			String tgl_rumus_set= rs.getString("A.TGL_RUMUS");
    			String tgl_cek_set= rs.getString("A.TGL_CEK");
    			String tgl_stuju_set= rs.getString("A.TGL_STUJU");
    			String tgl_tetap_set= rs.getString("A.TGL_TETAP");
    			String tgl_kendali_set= rs.getString("A.TGL_KENDALI");
    			String tgl_next_kegiatan= rs.getString("A.TGL_NEXT_KEGIATAN");
    			String waktu_next_kegiatan= rs.getString("A.WAKTU_NEXT_KEGIATAN");
    			
    			String tgl_sta_std = rs.getString("B.TGL_STA");
    			String tgl_end_std = rs.getString("B.TGL_END");
    			String tkn_jab_rumus = rs.getString("B.TKN_JAB_PERUMUS");
    			String tkn_jab_cek = rs.getString("B.TKN_JAB_PERIKSA");
    			String tkn_jab_stuju = rs.getString("B.TKN_JAB_SETUJU");
    			String tkn_jab_tetap = rs.getString("B.TKN_JAB_PENETAP");
    			String tkn_jab_kendali = rs.getString("B.TKN_JAB_KENDALI");
    			String tujuan = rs.getString("B.TUJUAN");
    			String lingkup = rs.getString("B.LINGKUP");
    			String definisi = rs.getString("B.DEFINISI");
    			String prosedur = rs.getString("B.PROSEDUR");
    			String kuali= rs.getString("B.KUALIFIKASI");
    			String doc = rs.getString("B.DOKUMEN");
    			String ref = rs.getString("B.REFERENSI");
    			
    			String tgl_rumus_std_set= rs.getString("A.TGL_RUMUS_STD");
    			String tgl_cek_std_set= rs.getString("A.TGL_CEK_STD");
    			String tgl_stuju_std_set= rs.getString("A.TGL_STUJU_STD");
    			String tgl_tetap_std_set= rs.getString("A.TGL_TETAP_STD");
    			String tgl_kendali_std_set= rs.getString("A.TGL_KENDALI_STD");
    			
    			String tmp = id_plan+"~"+versi_id+"~"+id_std+"~"+tgl_sta+"~"+waktu_sta+"~"+tgl_end+"~"+waktu_end+"~"+stamp_sta+"~"+stamp_end+"~"+nama_kegiatan+"~"+jenis_kegiatan+"~"+tujuan_kegiatan+"~"+isi_kegiatan+"~"+tkn_job_tanggung+"~"+tkn_job_target+"~"+tkn_dok_kegiatan+"~"+hasil_kegiatan+"~"+kegiatan_started+"~"+kegiatan_ended+"~"+note_kegiatan+"~"+tgl_rumus_set+"~"+tgl_cek_set+"~"+tgl_stuju_set+"~"+tgl_tetap_set+"~"+tgl_kendali_set+"~"+tgl_next_kegiatan+"~"+waktu_next_kegiatan+"~"+tgl_sta_std+"~"+tgl_end_std+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendali+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kuali+"~"+doc+"~"+ref+"~"+tgl_rumus_std_set+"~"+tgl_cek_std_set+"~"+tgl_stuju_std_set+"~"+tgl_tetap_std_set+"~"+tgl_kendali_std_set;
    			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
    			//System.out.println("TMP="+tmp);
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

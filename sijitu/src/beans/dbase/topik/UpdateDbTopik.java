package beans.dbase.topik;

import beans.dbase.UpdateDb;
import beans.tools.AddHocFunction;
import beans.tools.Checker;
import beans.tools.Getter;

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
 * Session Bean implementation class UpdateDbTopik
 */
@Stateless
@LocalBean
public class UpdateDbTopik extends UpdateDb {
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
    public UpdateDbTopik() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbTopik(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    
    /*
     * untuk PENGAJUAN TOPIK HARUS DENGAN NPM TIDAK OBJ ID, OBJ ID UNTUK SPT PENGUMUMAN
     * return value : 
     * String null bila tidak ada masalah
     * ato return String msg, misalkan pengajuan cuti sudah di approved dan locked & pengajuan dobel
     */
    public String postTopikPengajuanCuti(String target_thsms, String kdpst, String npmhs, String alasan, String nmmhs) {
    	int i=0;
    	String msg = null;
    	try {
           	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//cek apa sudah ada pengajuan sebelumnya yg tidak di reject
        	//
        	stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TIPE_PENGAJUAN=? and TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=?");
        	stmt.setString(1, "CUTI");
        	stmt.setString(2, target_thsms);
        	stmt.setString(3, npmhs);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		//System.out.println("1");
        		//sudah diajukan
        		//
        		int id = rs.getInt("ID");
        		String rejected = rs.getString("REJECTED");
        		boolean lock = rs.getBoolean("LOCKED");
        		String approved = rs.getString("APPROVED");
        		//1.cek apa rejected 
        		if(rejected!=null && !Checker.isStringNullOrEmpty(rejected)) {
        			//pernah direjected - kalo rejected != empty berarti otomati locked
        			//insert pengajuan baru , thus msg == null
        		}
        		else {
        			//rejected null or empty
        			//2. cek apa sudah disetujui
        			if(approved!=null && !Checker.isStringNullOrEmpty(approved)) {
        				if(lock) {
        					//sudah disetujui
        					msg = new String("Pengajuan cuti untuk tahun/sms "+target_thsms+" sudah diajukan dan sudah disetujui");
        				}
        				else {
        					//inprogress
        					msg = new String("Pengajuan cuti untuk tahun/sms "+target_thsms+" sudah diajukan dan dalam proses persetujuan");
        	        		
        				}
        				stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_CREATOR=?");
    	        		stmt.setBoolean(1, false); //tidak ditampilkan notifikasi, hanya update saja yg merubah statusnya
    	        		stmt.executeUpdate();
        			}
        			else {
        				//insert pengajuan = cmd==null
        			}
        		}
        		
        	}
        	
        	if(msg==null) {
        		//System.out.println("2 "+kdpst);
        		//cek tabel cuti rule, sipa approveenya = tkn_objid
        		stmt = con.prepareStatement("select TKN_VERIFICATOR,TKN_VERIFICATOR_ID from CUTI_RULES where THSMS=? and KDPST=?");
        		stmt.setString(++i, target_thsms);
        		stmt.setString(++i, kdpst);
        		rs = stmt.executeQuery();
        		if(!rs.next()) {
        			
        			//System.out.println("3");
        			msg = new String("Aturan Cuti untuk tahun/sms "+target_thsms+" belum diisi");
        			//System.out.println("msg="+msg);
        		}
        		else {
        			//System.out.println("4");
        			String tkn_target_obj_nickmane = rs.getString("TKN_VERIFICATOR");
        			String tkn_target_objid = rs.getString("TKN_VERIFICATOR_ID");
        			
        			//post pengajuna
        			//sebelum insert cek dulu apa ada record pengajuan yg masih aktif
        			i=0;
        			stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_NPM=? and LOCKED=? and REJECTED=? and BATAL=?");
        			stmt.setString(++i, target_thsms);
        			stmt.setString(++i, "CUTI");
        			stmt.setString(++i, npmhs);
        			stmt.setBoolean(++i, false);
        			stmt.setNull(++i, java.sql.Types.VARCHAR);
        			stmt.setBoolean(++i, false);
        			rs = stmt.executeQuery();
        			if(!rs.next()) {
        				i=0;
                		stmt = con.prepareStatement("INSERT INTO TOPIK_PENGAJUAN(TARGET_THSMS_PENGAJUAN,TIPE_PENGAJUAN,ISI_TOPIK_PENGAJUAN,TOKEN_TARGET_OBJ_NICKNAME,TOKEN_TARGET_OBJID,CREATOR_OBJ_ID,CREATOR_NPM,CREATOR_NMM,SHOW_AT_TARGET,SHOW_AT_CREATOR,CREATOR_KDPST)values(?,?,?,?,?,?,?,?,?,?,?)");
                		stmt.setString(++i, target_thsms);
                		stmt.setString(++i, "CUTI");
                		stmt.setString(++i, alasan);
                		stmt.setString(++i, tkn_target_obj_nickmane);
                		stmt.setString(++i, tkn_target_objid);
                		stmt.setLong(++i,Checker.getObjectId(npmhs));
                		stmt.setString(++i, npmhs);
                		stmt.setString(++i, nmmhs);
                		stmt.setString(++i, tkn_target_objid);
                		stmt.setBoolean(++i, false);//tidak ditampilkan notifikasi, hanya update saja yg merubah statusnya
                		stmt.setString(++i, kdpst);
                		stmt.executeUpdate();
        			}
        			
        			//update overview table
            		long npm_id = Checker.getObjectId(npmhs);
            		//cek apa sudah ada record utk objid terkait
            		stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
            		stmt.setLong(1, npm_id);
            		stmt.setString(2, target_thsms);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			int tot_cuti_req = rs.getInt("TOT_CUTI_REQ");
            			int tot_cuti_req_unapproved = rs.getInt("TOT_CUTI_REQ_UNAPPROVED"); 
            			String list_npm_unapproved = ""+rs.getString("LIST_NPM_CUTI_UNAPPROVED");
            			//karena add req
            			if(list_npm_unapproved==null || Checker.isStringNullOrEmpty(list_npm_unapproved) || (list_npm_unapproved!=null && !list_npm_unapproved.contains(npmhs))) {
            				if(list_npm_unapproved==null || Checker.isStringNullOrEmpty(list_npm_unapproved)) {
            					list_npm_unapproved = new String(npmhs);	
            				}
            				else {
            					list_npm_unapproved = list_npm_unapproved+","+npmhs;
                				if(list_npm_unapproved.startsWith(",")) {
                					list_npm_unapproved = list_npm_unapproved.substring(1, list_npm_unapproved.length());
                				}
            				}
            				tot_cuti_req++;
        					tot_cuti_req_unapproved++;
        					stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_CUTI_REQ=?,TOT_CUTI_REQ_UNAPPROVED=?,LIST_NPM_CUTI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
            				stmt.setInt(1, tot_cuti_req);
            				stmt.setInt(2, tot_cuti_req_unapproved);
            				stmt.setString(3, list_npm_unapproved);
            				stmt.setLong(4, npm_id);
            				stmt.setString(5, target_thsms);
            				stmt.executeUpdate();
            			}
            			else {
            				//if(list_npm_unapproved.contains(npmhs)) {
            					//sudah ada, ignore ngga perlu di update
            				/*
            				}
            				else {
            					tot_cuti_req++;
            					tot_cuti_req_unapproved++;
            					list_npm_unapproved = list_npm_unapproved+","+npmhs;
                				if(list_npm_unapproved.startsWith(",")) {
                					list_npm_unapproved = list_npm_unapproved.substring(1, list_npm_unapproved.length());
                				}
                				stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_CUTI_REQ=?,TOT_CUTI_REQ_UNAPPROVED=?,LIST_NPM_CUTI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
                				stmt.setInt(1, tot_cuti_req);
                				stmt.setInt(2, tot_cuti_req_unapproved);
                				stmt.setString(3, list_npm_unapproved);
                				stmt.setLong(4, npm_id);
                				stmt.setString(5, target_thsms);
                				stmt.executeUpdate();
            				}
            				*/
            			}
            			
            			//tot_cuti_req++; //ngga jadi ditambah krn = yang sudah di approved
            			//tot_cuti_req_unapproved++;
            			
            		}
            		else {
            			//insert
            			stmt = con.prepareStatement("insert into OVERVIEW_SEBARAN_TRLSM (ID_OBJ,THSMS,KDPST,TOT_CUTI_REQ,TOT_CUTI_REQ_UNAPPROVED,LIST_NPM_CUTI_UNAPPROVED)values(?,?,?,?,?,?)");
            			stmt.setLong(1, npm_id);
            			stmt.setString(2, target_thsms);
            			stmt.setString(3, kdpst);
            			stmt.setInt(4, 1);
            			stmt.setInt(5, 1);
            			stmt.setString(6, npmhs);
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
    	return msg;
    }
    
    public String postTopikPengajuan(String target_thsms, String kdpst, String npmhs, String alasan, String nmmhs, String table_rule_nm) {
    	int i=0;
    	String msg = null;
    	try {
           	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//cek apa sudah ada pengajuan sebelumnya yg tidak di reject
        	//
        	String type_pengajuan = table_rule_nm.replace("_RULES", "");
        	String title_pengajuan = type_pengajuan.replace("_", " ");
        	
        	
        	String keterangan = "Pengajuan "+title_pengajuan.toLowerCase();
        	//CEK APA SUDAH PERNAH DIAJUKAN, TIDAK PERLU MIKIR THSMSNYA YG PENTING BELUM KEPROSES
        	//stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TIPE_PENGAJUAN=? and TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=? and LOCKED=false and BATAL=false");
        	stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TIPE_PENGAJUAN=? and CREATOR_NPM=? and LOCKED=false and BATAL=false");
        	stmt.setString(1, type_pengajuan);
        	//stmt.setString(2, target_thsms);
        	stmt.setString(2, npmhs);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		//System.out.println("1");
        		//sudah diajukan
        		//
        		int id = rs.getInt("ID");
        		String rejected = rs.getString("REJECTED");
        		boolean lock = rs.getBoolean("LOCKED");
        		String approved = rs.getString("APPROVED");
        		//1.cek apa rejected 
        		if(rejected!=null && !Checker.isStringNullOrEmpty(rejected)) {
        			//pernah direjected - kalo rejected != empty berarti otomati locked
        			//insert pengajuan baru , thus msg == null
        		}
        		else {
        			//rejected null or empty
        			//2. cek apa sudah disetujui
        			if(approved!=null && !Checker.isStringNullOrEmpty(approved)) {
        				if(lock) {
        					//sudah disetujui
        					msg = new String(keterangan+" untuk tahun/sms "+target_thsms+" sudah diajukan dan sudah disetujui");
        				}
        				else {
        					//inprogress
        					msg = new String(keterangan+" untuk tahun/sms "+target_thsms+" sudah diajukan dan dalam proses persetujuan");
        	        		
        				}
        				stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_CREATOR=?");
    	        		stmt.setBoolean(1, false); //tidak ditampilkan notifikasi, hanya update saja yg merubah statusnya
    	        		stmt.executeUpdate();
        			}
        			else {
        				//insert pengajuan = cmd==null
        			}
        		}
        		
        	}
        	
        	if(msg==null) {
        		//System.out.println("2 "+kdpst);
        		//cek tabel cuti rule, sipa approveenya = tkn_objid
        		stmt = con.prepareStatement("select TKN_JABATAN_VERIFICATOR,TKN_VERIFICATOR_ID from "+table_rule_nm+" where THSMS=? and KDPST=?");
        		stmt.setString(++i, target_thsms);
        		stmt.setString(++i, kdpst);
        		rs = stmt.executeQuery();
        		if(!rs.next()) {
        			
        			//System.out.println("3");
        			msg = new String("Aturan "+type_pengajuan.toLowerCase()+" untuk tahun/sms "+target_thsms+" belum diisi");
        			//System.out.println("msg="+msg);
        		}
        		else {
        			//System.out.println("4");
        			String tkn_target_obj_nickmane = rs.getString("TKN_JABATAN_VERIFICATOR");
        			String tkn_target_objid = rs.getString("TKN_VERIFICATOR_ID");
        			
            		if(tkn_target_objid.contains("null")||tkn_target_obj_nickmane.contains("null")) {
            			msg = new String("Pastikan tabel approvee sudah diisi, harap hubungi admin");
            		}
            		
            		if(msg==null) {
            			//post pengajuna
            			//sebelum insert cek dulu apa ada record pengajuan yg masih aktif
            			i=0;
            			stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_NPM=? and LOCKED=? and REJECTED=? and BATAL=?");
            			stmt.setString(++i, target_thsms);
            			stmt.setString(++i, type_pengajuan);
            			stmt.setString(++i, npmhs);
            			stmt.setBoolean(++i, false);
            			stmt.setNull(++i, java.sql.Types.VARCHAR);
            			stmt.setBoolean(++i, false);
            			rs = stmt.executeQuery();
            			if(!rs.next()) {
            				i=0;
                    		stmt = con.prepareStatement("INSERT INTO TOPIK_PENGAJUAN(TARGET_THSMS_PENGAJUAN,TIPE_PENGAJUAN,ISI_TOPIK_PENGAJUAN,TOKEN_TARGET_OBJ_NICKNAME,TOKEN_TARGET_OBJID,CREATOR_OBJ_ID,CREATOR_NPM,CREATOR_NMM,SHOW_AT_TARGET,SHOW_AT_CREATOR,CREATOR_KDPST)values(?,?,?,?,?,?,?,?,?,?,?)");
                    		stmt.setString(++i, target_thsms);
                    		stmt.setString(++i, type_pengajuan);
                    		stmt.setString(++i, alasan);
                    		stmt.setString(++i, tkn_target_obj_nickmane);
                    		stmt.setString(++i, tkn_target_objid);
                    		stmt.setLong(++i,Checker.getObjectId(npmhs));
                    		stmt.setString(++i, npmhs);
                    		stmt.setString(++i, nmmhs);
                    		stmt.setString(++i, tkn_target_objid);
                    		stmt.setBoolean(++i, false);//tidak ditampilkan notifikasi, hanya update saja yg merubah statuija
            				
                    		stmt.setString(++i, kdpst);
                    		stmt.executeUpdate();
            			}
            			
            			//update overview table
                		long npm_id = Checker.getObjectId(npmhs);
                		//cek apa sudah ada record utk objid terkait
                		stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
                		stmt.setLong(1, npm_id);
                		stmt.setString(2, target_thsms);
                		rs = stmt.executeQuery();
                		if(rs.next()) {
                			int tot_cuti_req = rs.getInt("TOT_NPM_"+type_pengajuan+"_REQ");
                			int tot_cuti_req_unapproved = rs.getInt("TOT_NPM_"+type_pengajuan+"_REQ_UNAPPROVED"); 
                			String list_npm_unapproved = ""+rs.getString("LIST_NPM_"+type_pengajuan+"_UNAPPROVED");
                			//karena add req
                			if(list_npm_unapproved==null || Checker.isStringNullOrEmpty(list_npm_unapproved) || (list_npm_unapproved!=null && !list_npm_unapproved.contains(npmhs))) {
                				if(list_npm_unapproved==null || Checker.isStringNullOrEmpty(list_npm_unapproved)) {
                					list_npm_unapproved = new String(npmhs);	
                				}
                				else {
                					list_npm_unapproved = list_npm_unapproved+","+npmhs;
                    				if(list_npm_unapproved.startsWith(",")) {
                    					list_npm_unapproved = list_npm_unapproved.substring(1, list_npm_unapproved.length());
                    				}
                				}
                				tot_cuti_req++;
            					tot_cuti_req_unapproved++;
            					stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_NPM_"+type_pengajuan+"_REQ=?,TOT_NPM_"+type_pengajuan+"_REQ_UNAPPROVED=?,LIST_NPM_"+type_pengajuan+"_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
                				stmt.setInt(1, tot_cuti_req);
                				stmt.setInt(2, tot_cuti_req_unapproved);
                				stmt.setString(3, list_npm_unapproved);
                				stmt.setLong(4, npm_id);
                				stmt.setString(5, target_thsms);
                				stmt.executeUpdate();
                			}
                			
                		}
                		else {
                			//insert
                			stmt = con.prepareStatement("insert into OVERVIEW_SEBARAN_TRLSM (ID_OBJ,THSMS,KDPST,TOT_"+type_pengajuan+"_REQ,TOT_"+type_pengajuan+"_REQ_UNAPPROVED,LIST_NPM_"+type_pengajuan+"_UNAPPROVED)values(?,?,?,?,?,?)");
                			stmt.setLong(1, npm_id);
                			stmt.setString(2, target_thsms);
                			stmt.setString(3, kdpst);
                			stmt.setInt(4, 1);
                			stmt.setInt(5, 1);
                			stmt.setString(6, npmhs);
                			stmt.executeUpdate();
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
    	return msg;
    }
    
    
    /*
     * 
     */
    public String postTopikPengajuanPP(String nama_pengajuan_yg_juga_nama_tabel_rules, String target_thsms, String npmhs, String alasan, String nmmhs, String kdpst_origin, String kdpst_target, String kmp_origin, String kmp_target) {
    	//origin-[KTU`67][Ka. BAAK`58][Ka. BAUK`63]
    	//target-[KTU`50][Ka. BAAK`58][Ka. BAUK`63]
    	//System.out.println("masuk");
    	String tipe_pengajuan = nama_pengajuan_yg_juga_nama_tabel_rules.replace("_RULES", "");
    	int i=0;
    	String msg = null;
    	try {
           	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//cek apa sudah ada pengajuan sebelumnya yg tidak di reject
        	//
        	stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TIPE_PENGAJUAN=? and TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=?");
        	stmt.setString(1, "PP");
        	stmt.setString(2, target_thsms);
        	stmt.setString(3, npmhs);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		//System.out.println("1");
        		//sudah diajukan
        		//
        		int id = rs.getInt("ID");
        		String rejected = rs.getString("REJECTED");
        		boolean lock = rs.getBoolean("LOCKED");
        		String approved = rs.getString("APPROVED");
        		//1.cek apa rejected 
        		if(rejected!=null && !Checker.isStringNullOrEmpty(rejected)) {
        			//pernah direjected - kalo rejected != empty berarti otomati locked
        			//insert pengajuan baru , thus msg == null
        		}
        		else {
        			//rejected null or empty
        			//2. cek apa sudah disetujui
        			if(approved!=null && !Checker.isStringNullOrEmpty(approved)) {
        				if(lock) {
        					//sudah disetujui
        					msg = new String("Pengajuan cuti untuk tahun/sms "+target_thsms+" sudah diajukan dan sudah disetujui");
        				}
        				else {
        					//inprogress
        					msg = new String("Pengajuan cuti untuk tahun/sms "+target_thsms+" sudah diajukan dan dalam proses persetujuan");
        	        		
        				}
        				stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_CREATOR=?");
    	        		stmt.setBoolean(1, false); //tidak ditampilkan notifikasi, hanya update saja yg merubah statusnya
    	        		stmt.executeUpdate();
        			}
        			else {
        				//insert pengajuan = cmd==null
        			}
        		}
        		
        	}
        	//System.out.println("msg = "+msg);
        	if(msg==null) {
        		//System.out.println("2 "+kdpst);
        		
        		//get verificator
        		
        		Vector v_verificator_origin_target = Getter.getVerificatorFromTableRule(nama_pengajuan_yg_juga_nama_tabel_rules, target_thsms, kdpst_origin, kdpst_target, kmp_origin, kmp_target);
        		ListIterator li = v_verificator_origin_target.listIterator();
        		String info_origin = null;
        		String info_target = null;
        		while(li.hasNext()) {
        			String brs1 = (String)li.next();
        			info_origin = new String(brs1);
        			String brs2 = (String)li.next();
        			info_target = new String(brs2);
        			//System.out.println(info_origin+" -vs- "+info_target);
        			if(brs1.contains("null")&&brs2.contains("null")) {
        				msg = new String("Pastikan jabatan pada prodi asal & tujuan sudah diisi");
        			}
        			else if(brs1.contains("null")&&!brs2.contains("null")) {
        				msg = new String("Pastikan jabatan pada prodi asal sudah diisi");
        			}
        			else if(!brs1.contains("null")&&brs2.contains("null")) {
        				msg = new String("Pastikan jabatan pada prodi tujuan sudah diisi");
        			}
        		}
        		/*
        		stmt = con.prepareStatement("select TKN_VERIFICATOR,TKN_VERIFICATOR_ID from CUTI_RULES where THSMS=? and KDPST=?");
        		stmt.setString(++i, target_thsms);
        		stmt.setString(++i, kdpst);
        		rs = stmt.executeQuery();
        		if(!rs.next()) {
        			
        			//System.out.println("3");
        			msg = new String("Aturan Cuti untuk tahun/sms "+target_thsms+" belum diisi");
        			//System.out.println("msg="+msg);
        		}
        		else {
        		*/
        		//System.out.println("msg1 = "+msg);
        		if(msg==null) { //artinya no error jabatan yang diperlukan telah ada objidnya
        			//System.out.println("4");
        			//String tkn_target_obj_nickmane = rs.getString("TKN_VERIFICATOR");
        			//String tkn_target_objid = rs.getString("TKN_VERIFICATOR_ID");
        			//process origin
        			if(tipe_pengajuan.equalsIgnoreCase("PINDAH_PRODI")) {
        				info_origin = info_origin.replace("`", ",");
            			info_origin = info_origin.replace("][", "`");
            			info_origin = info_origin.replace("[", "");
            			info_origin = info_origin.replace("]", "");
            			StringTokenizer st = new StringTokenizer(info_origin,"`");
            			String info_origin_jabatan = new String();
            			String info_origin_id = new String();
            			while(st.hasMoreTokens()) {
            				String tmp = st.nextToken();
            				StringTokenizer st1 = new StringTokenizer(tmp,",");
            				String job = st1.nextToken(); //remove
            				info_origin_jabatan = info_origin_jabatan+"["+job+"]";
            				String list_objid = new String();
            				while(st1.hasMoreTokens()) {
            					String objid = st1.nextToken();
            					list_objid = list_objid+objid;
            					if(st1.hasMoreTokens()) {
            						list_objid = list_objid+"`";
            					}
            				}
            				info_origin_id = info_origin_id +"["+list_objid+"]";
            			}
            			//process target
            			info_target = info_target.replace("`", ",");
            			info_target = info_target.replace("][", "`");
            			info_target = info_target.replace("[", "");
            			info_target = info_target.replace("]", "");
            			st = new StringTokenizer(info_target,"`");
            			String info_target_jabatan = new String();
            			String info_target_id = new String();
            			while(st.hasMoreTokens()) {
            				String tmp = st.nextToken();
            				StringTokenizer st1 = new StringTokenizer(tmp,",");
            				String job = st1.nextToken(); //remove
            				info_target_jabatan = info_target_jabatan+"["+job+"]";
            				String list_objid = new String();
            				while(st1.hasMoreTokens()) {
            					String objid = st1.nextToken();
            					list_objid = list_objid+objid;
            					if(st1.hasMoreTokens()) {
            						list_objid = list_objid+"`";
            					}
            				}
            				info_target_id = info_target_id +"["+list_objid+"]";
            			}
            			
            			//post pengajuna
            			//sebelum insert cek dulu apa ada record pengajuan yg masih aktif
            			i=0;
            			//
            			stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_NPM=? and LOCKED=? and REJECTED=? and BATAL=?");
            			stmt.setString(++i, target_thsms);
            			stmt.setString(++i, tipe_pengajuan);
            			stmt.setString(++i, npmhs);
            			stmt.setBoolean(++i, false);
            			stmt.setNull(++i, java.sql.Types.VARCHAR);
            			stmt.setBoolean(++i, false);
            			rs = stmt.executeQuery();
            			if(!rs.next()) {
            				//System.out.println("insert");
            				i=0;
                    		stmt = con.prepareStatement("INSERT INTO TOPIK_PENGAJUAN(TARGET_THSMS_PENGAJUAN,TIPE_PENGAJUAN,ISI_TOPIK_PENGAJUAN,TOKEN_TARGET_OBJ_NICKNAME,TOKEN_TARGET_OBJID,CREATOR_OBJ_ID,CREATOR_NPM,CREATOR_NMM,SHOW_AT_TARGET,SHOW_AT_CREATOR,CREATOR_KDPST)values(?,?,?,?,?,?,?,?,?,?,?)");
                    		stmt.setString(++i, target_thsms);
                    		stmt.setString(++i, tipe_pengajuan);
                    		stmt.setString(++i, alasan);
                    		stmt.setString(++i, info_target_jabatan);
                    		stmt.setString(++i, info_target_id);
                    		stmt.setLong(++i,Checker.getObjectId(npmhs));
                    		stmt.setString(++i, npmhs);
                    		stmt.setString(++i, nmmhs);
                    		stmt.setString(++i, info_target_id);
                    		stmt.setBoolean(++i, false);//tidak ditampilkan notifikasi, hanya update saja yg merubah statusnya
                    		stmt.setString(++i, kdpst_origin);
                    		stmt.executeUpdate();
            			}
            			//else {
            			//	//System.out.println("sudag ahad");
            			//}
            			
            			//update overview table
            			//origin
                		long npm_id = Checker.getObjectId(npmhs);
                		//cek apa sudah ada record utk objid terkait
                		stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
                		stmt.setLong(1, npm_id);
                		stmt.setString(2, target_thsms);
                		rs = stmt.executeQuery();
                		if(rs.next()) {
                			//tinggal update saja
                			/*
                			 * KARENA BILA SUDAH ADA PENGAJUAN IN PROGRESS MAKA AKAN DI BLOK servlets.pengajuan.PrepPengajuanPp_baru
                			 */
                			
                			int tot_out_req = rs.getInt("TOT_NPM_PINDAH_PRODI_OUT");
                			//int tot_cuti_req_unapproved = rs.getInt("TOT_PP_REQ_UNAPPROVED"); 
                			
                			//HARUSNYA NPMNYA NGGA MUNGKIN ADA KRN SDH DIBLOK DIATAS, TP TERUSIN AJA KRN INI CODING YG LAMA
                			String list_npm_unapproved = ""+rs.getString("LIST_NPM_PINDAH_PRODI_UNAPPROVED");
                			//karena add req
                			if(list_npm_unapproved==null || Checker.isStringNullOrEmpty(list_npm_unapproved) || (list_npm_unapproved!=null && !list_npm_unapproved.contains(npmhs))) {
                				if(list_npm_unapproved==null || Checker.isStringNullOrEmpty(list_npm_unapproved)) {
                					list_npm_unapproved = new String(npmhs);	
                					
                				}
                				else {
                					list_npm_unapproved = list_npm_unapproved+","+npmhs;
                    				if(list_npm_unapproved.startsWith(",")) {
                    					list_npm_unapproved = list_npm_unapproved.substring(1, list_npm_unapproved.length());
                    				}
                				}
                				tot_out_req++;
            					//tot_cuti_req_unapproved++;
            					//stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_CUTI_REQ=?,TOT_CUTI_REQ_UNAPPROVED=?,LIST_NPM_CUTI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
                				stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_NPM_PINDAH_PRODI_OUT=?,LIST_NPM_PINDAH_PRODI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
                				stmt.setInt(1, tot_out_req);
                				stmt.setString(2, list_npm_unapproved);
                				stmt.setLong(3, npm_id);
                				stmt.setString(4, target_thsms);
                				stmt.executeUpdate();
                			}
                			else {
                				//if(list_npm_unapproved.contains(npmhs)) {
                					//sudah ada, ignore ngga perlu di update
               
                			}
                			
                			//tot_cuti_req++; //ngga jadi ditambah krn = yang sudah di approved
                			//tot_cuti_req_unapproved++;
                			
                		}
                		else {
                			//insert origin info
                			stmt = con.prepareStatement("insert into OVERVIEW_SEBARAN_TRLSM (ID_OBJ,THSMS,KDPST,TOT_NPM_PINDAH_PRODI_OUT,LIST_NPM_PINDAH_PRODI_UNAPPROVED)values(?,?,?,?,?)");
                			stmt.setLong(1, npm_id);
                			stmt.setString(2, target_thsms);
                			stmt.setString(3, kdpst_origin);
                			stmt.setInt(4, 1);
                			stmt.setString(5, npmhs);
                			stmt.executeUpdate();
                		}
                		
                		//process target info
                		//cek apa di target udah adaa
                		//1.get objid target kdpst
                		String objid_mhs_target_kdpst = Checker.getObjidMhsProdi(kdpst_target, kmp_target);

                		if(objid_mhs_target_kdpst!=null) {
                			stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
                        	stmt.setLong(1, Long.parseLong(objid_mhs_target_kdpst));
                        	stmt.setString(2, target_thsms);
                        	rs = stmt.executeQuery();
                        	if(rs.next()) {
                    			//tinggal update saja
                    			/*
                    			 * KARENA BILA SUDAH ADA PENGAJUAN IN PROGRESS MAKA AKAN DI BLOK servlets.pengajuan.PrepPengajuanPp_baru
                    			 */
                    			
                    			int tot_in_req = rs.getInt("TOT_NPM_PINDAH_PRODI_IN");
                    			//int tot_cuti_req_unapproved = rs.getInt("TOT_PP_REQ_UNAPPROVED"); 
                    			
                    			//HARUSNYA NPMNYA NGGA MUNGKIN ADA KRN SDH DIBLOK DIATAS, TP TERUSIN AJA KRN INI CODING YG LAMA
                    			String list_npm_unapproved = ""+rs.getString("LIST_NPM_PINDAH_PRODI_UNAPPROVED");
                    			//karena add req
                    			if(list_npm_unapproved==null || Checker.isStringNullOrEmpty(list_npm_unapproved) || (list_npm_unapproved!=null && !list_npm_unapproved.contains(npmhs))) {
                    				if(list_npm_unapproved==null || Checker.isStringNullOrEmpty(list_npm_unapproved)) {
                    					list_npm_unapproved = new String(npmhs);	
                    					
                    				}
                    				else {
                    					list_npm_unapproved = list_npm_unapproved+","+npmhs;
                        				if(list_npm_unapproved.startsWith(",")) {
                        					list_npm_unapproved = list_npm_unapproved.substring(1, list_npm_unapproved.length());
                        				}
                    				}
                    				tot_in_req++;
                    							//tot_cuti_req_unapproved++;
                					//stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_CUTI_REQ=?,TOT_CUTI_REQ_UNAPPROVED=?,LIST_NPM_CUTI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
                    				stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_NPM_PINDAH_PRODI_IN=?,LIST_NPM_PINDAH_PRODI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
                    				stmt.setInt(1, tot_in_req);
                    				stmt.setString(2, list_npm_unapproved);
                    				stmt.setLong(3, Long.parseLong(objid_mhs_target_kdpst));
                    				stmt.setString(4, target_thsms);
                    				stmt.executeUpdate();
                    			}
                    			else {
                    				//if(list_npm_unapproved.contains(npmhs)) {
                    					//sudah ada, ignore ngga perlu di update
                   
                    			}
                    			
                    			//tot_cuti_req++; //ngga jadi ditambah krn = yang sudah di approved
                    			//tot_cuti_req_unapproved++;
                    			
                    		}
                        	else {
                        		//insert origin info
                        		stmt = con.prepareStatement("insert into OVERVIEW_SEBARAN_TRLSM (ID_OBJ,THSMS,KDPST,TOT_NPM_PINDAH_PRODI_IN,LIST_NPM_PINDAH_PRODI_UNAPPROVED)values(?,?,?,?,?)");
                        		stmt.setLong(1, Long.parseLong(objid_mhs_target_kdpst));
                        		stmt.setString(2, target_thsms);
                        		stmt.setString(3, kdpst_target);
                        		stmt.setInt(4, 1);
                        		stmt.setString(5, npmhs);
                        		stmt.executeUpdate();
                        	}
                		}//end if(objid_mhs_target_kdpst!=null)
                		else {
                			//System.out.println("objid target tidak ada");
                		}
        			}
        			else {
        				//pengajuan lainnya 
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
    	return msg;
    }
    
    public String cekNpmProdiBaru(String nu_npm, String old_kdpst, String npmAlias) {
    	/*
    	 * return null if valid
    	 */
    	boolean valid = false;
    	String msg = null;
    	try {
           	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select KDPSTMSMHS from CIVITAS where NPMHSMSMHS=?");
        	stmt.setString(1, nu_npm);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		String kdpst = rs.getString(1);
        		if(kdpst.equalsIgnoreCase(old_kdpst)) {
        			msg = new String("Harap no "+npmAlias+" pada prodi baru diisi dengan benar");
        		}
        	}
        	else {
        		msg = new String("Harap no "+npmAlias+" pada prodi baru diisi dengan benar");
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
    	return msg;
    }
    
    public String updatePengajuanAtTabelOverview(String tipe_pengajuan, String target_thsms, String npmhs,  String kdpst_target, String kmp_target, String in_out_for_pindah_prodi) {
    	try {
           	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	
        	//update overview table
			//origin
    		long npm_id = Checker.getObjectId(npmhs);
    		String objid_mhs_target_kdpst = Checker.getObjidMhsProdi(kdpst_target, kmp_target);
    		//cek apa sudah ada record utk objid terkait
    		//stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
    	//	stmt.setLong(1, npm_id);
    		//stmt.setString(2, target_thsms);
    		//rs = stmt.executeQuery();
    		
    		if(tipe_pengajuan.equalsIgnoreCase("PINDAH_PRODI")) {
    			//tinggal update saja
    			/*
    			 * KARENA BILA SUDAH ADA PENGAJUAN IN PROGRESS MAKA AKAN DI BLOK servlets.pengajuan.PrepPengajuanPp_baru
    			 */
    			
    			
    			if(in_out_for_pindah_prodi.equalsIgnoreCase("in")) {
    				stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
    	    		stmt.setLong(1, Long.parseLong(objid_mhs_target_kdpst));
    	    		stmt.setString(2, target_thsms);
    	    		rs = stmt.executeQuery();
    	    		if(rs.next()) {
    	    			int tot_in_req = rs.getInt("TOT_NPM_PINDAH_PRODI_IN");
        	    		//int tot_cuti_req_unapproved = rs.getInt("TOT_PP_REQ_UNAPPROVED"); 
        	    			
        	    		//HARUSNYA NPMNYA NGGA MUNGKIN ADA KRN SDH DIBLOK DIATAS, TP TERUSIN AJA KRN INI CODING YG LAMA
        	    		String list_npm_unapproved = ""+rs.getString("LIST_NPM_PINDAH_PRODI_UNAPPROVED");
        	    		if(list_npm_unapproved.endsWith(",")) {
        	    			list_npm_unapproved = list_npm_unapproved.substring(0,list_npm_unapproved.length()-1);
        	    		}
        	    		//karena add req
        	    		if(list_npm_unapproved==null || Checker.isStringNullOrEmpty(list_npm_unapproved) || (list_npm_unapproved!=null && !list_npm_unapproved.contains(npmhs+"-in"))) {
        	    			if(list_npm_unapproved==null || Checker.isStringNullOrEmpty(list_npm_unapproved)) {
        	    				list_npm_unapproved = new String(npmhs)+"-in";	//agar ketauan di dia unapproved in or out
        	    				
        	    			}
        	    			else {
        	    				list_npm_unapproved = list_npm_unapproved+","+npmhs+"-in";	//agar ketauan di dia unapproved in or out
        	        			if(list_npm_unapproved.startsWith(",")) {
        	        				list_npm_unapproved = list_npm_unapproved.substring(1, list_npm_unapproved.length());
        	        			}
        	    			}
        	    			tot_in_req++;
        					//tot_cuti_req_unapproved++;
        					//stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_CUTI_REQ=?,TOT_CUTI_REQ_UNAPPROVED=?,LIST_NPM_CUTI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
        	    			stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_NPM_PINDAH_PRODI_IN=?,LIST_NPM_PINDAH_PRODI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
        	    			stmt.setInt(1, tot_in_req);
        	    			stmt.setString(2, list_npm_unapproved);
        	    			stmt.setLong(3, Long.parseLong(objid_mhs_target_kdpst));
        	    			stmt.setString(4, target_thsms);
        	   				stmt.executeUpdate();
        	   			}
        	   			else {
        	   				if(list_npm_unapproved.contains(npmhs+"-in")) {
        	   					//sudah ada, ignore ngga perlu di update
        	       	    	}
        	   			}
    	    		}
    	    		else {
    	    			stmt = con.prepareStatement("insert into OVERVIEW_SEBARAN_TRLSM (ID_OBJ,THSMS,KDPST,TOT_NPM_PINDAH_PRODI_IN,LIST_NPM_PINDAH_PRODI_UNAPPROVED)values(?,?,?,?,?)");
    	    			stmt.setLong(1, Long.parseLong(objid_mhs_target_kdpst));
        	    		stmt.setString(2, target_thsms);
        	    		stmt.setString(3, kdpst_target);
        	    		stmt.setInt(4, 1);
    	    			stmt.setString(5, npmhs+"-in");
    	    			stmt.executeUpdate();
    	    		}
    				
    			}	
    			else if(in_out_for_pindah_prodi.equalsIgnoreCase("out")) {
    				stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
    	    		stmt.setLong(1, npm_id);
    	    		stmt.setString(2, target_thsms);
    	    		rs = stmt.executeQuery();
    	    		if(rs.next()) {
    	    			int tot_out_req = rs.getInt("TOT_NPM_PINDAH_PRODI_OUT");
        	    		//int tot_cuti_req_unapproved = rs.getInt("TOT_PP_REQ_UNAPPROVED"); 
        	    		
        	    		//HARUSNYA NPMNYA NGGA MUNGKIN ADA KRN SDH DIBLOK DIATAS, TP TERUSIN AJA KRN INI CODING YG LAMA
        	    		String list_npm_unapproved = ""+rs.getString("LIST_NPM_PINDAH_PRODI_UNAPPROVED");
        	    		if(list_npm_unapproved.endsWith(",")) {
        	    			list_npm_unapproved = list_npm_unapproved.substring(0,list_npm_unapproved.length()-1);
        	    		}
        	    		//karena add req
        	    		if(list_npm_unapproved==null || Checker.isStringNullOrEmpty(list_npm_unapproved) || (list_npm_unapproved!=null && !list_npm_unapproved.contains(npmhs+"-out"))) {
        	    			if(list_npm_unapproved==null || Checker.isStringNullOrEmpty(list_npm_unapproved)) {
        	    				list_npm_unapproved = new String(npmhs)+"-out";	//agar ketauan di dia unapproved in or out
        	    				
        	    			}
        	    			else {
        	    				list_npm_unapproved = list_npm_unapproved+","+npmhs+"-out";	//agar ketauan di dia unapproved in or out
        	        			if(list_npm_unapproved.startsWith(",")) {
        	        				list_npm_unapproved = list_npm_unapproved.substring(1, list_npm_unapproved.length());
        	        			}
        	    			}
        	    			tot_out_req++;
        					//tot_cuti_req_unapproved++;
        					//stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_CUTI_REQ=?,TOT_CUTI_REQ_UNAPPROVED=?,LIST_NPM_CUTI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
        	    			stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_NPM_PINDAH_PRODI_OUT=?,LIST_NPM_PINDAH_PRODI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
        	    			stmt.setInt(1, tot_out_req);
        	    			stmt.setString(2, list_npm_unapproved);
        	    			stmt.setLong(3, npm_id);
        	    			stmt.setString(4, target_thsms);
        	    			stmt.executeUpdate();
        	    		}
        	    		else {
        	    			if(list_npm_unapproved.contains(npmhs+"-out")) {
        	    				//sudah ada, ignore ngga perlu di update
        	       	    	}
        	    			
        	    			//tot_cuti_req++; //ngga jadi ditambah krn = yang sudah di approved
        	    			//tot_cuti_req_unapproved++;
        	    			
        	    		}
    	    		}
    				else {
    	    			//insert origin info
    	    		
    					stmt = con.prepareStatement("insert into OVERVIEW_SEBARAN_TRLSM (ID_OBJ,THSMS,KDPST,TOT_NPM_PINDAH_PRODI_OUT,LIST_NPM_PINDAH_PRODI_UNAPPROVED)values(?,?,?,?,?)");
    					stmt.setLong(1, npm_id);
        	    		stmt.setString(2, target_thsms);
        	    		stmt.setString(3, kdpst_target);
        	    		stmt.setInt(4, 1);
    	    			stmt.setString(5, npmhs+"-out");
    	    			stmt.executeUpdate();
    	    		}	
    	    	}	
    		}
    		else {
    			//pengajuan lainnya
    			stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
	    		stmt.setLong(1, Long.parseLong(objid_mhs_target_kdpst));
	    		stmt.setString(2, target_thsms);
	    		rs = stmt.executeQuery();
	    		if(rs.next()) {
	    			//cek apa sdh ada kalo sdh ada update saja
	    			int tot_req = rs.getInt("TOT_"+tipe_pengajuan+"_REQ");
	        		int tot_req_unapproved = rs.getInt("TOT_"+tipe_pengajuan+"_REQ_UNAPPROVED");
	        		String list_npm_unappproved = rs.getString("LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED");
	        		if(list_npm_unappproved==null || !list_npm_unappproved.contains(npmhs)) {
	        			stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_"+tipe_pengajuan+"_REQ=?,TOT_"+tipe_pengajuan+"_REQ_UNAPPROVED=?,LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
	        			++tot_req;
	            		++tot_req_unapproved;
	            		stmt.setInt(1, tot_req);
	            		stmt.setInt(2, tot_req_unapproved);
	            		if(list_npm_unappproved==null || Checker.isStringNullOrEmpty(list_npm_unappproved)) {
	            			list_npm_unappproved = new String(npmhs);
	            		}
	            		else {
	            			list_npm_unappproved = list_npm_unappproved +","+npmhs; 
	            		}
	            		stmt.setString(3,list_npm_unappproved);
	            		
	            		stmt.setLong(4, npm_id);
	            		stmt.setString(5,target_thsms);
	        			int i = stmt.executeUpdate();
	        		}
	        		else {
	        			//ignore krn npmnya sdh tercatat
	        		}
	    		}
	    		else {
	    			stmt = con.prepareStatement("insert into OVERVIEW_SEBARAN_TRLSM (ID_OBJ,THSMS,KDPST,TOT_"+tipe_pengajuan+"_REQ,TOT_"+tipe_pengajuan+"_REQ_UNAPPROVED,LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED)values(?,?,?,?,?,?)");
	    			stmt.setLong(1, npm_id);
    	    		stmt.setString(2, target_thsms);
    	    		stmt.setString(3, kdpst_target);
    	    		stmt.setInt(4, 1);
    	    		stmt.setInt(5, 1);
	    			stmt.setString(6, npmhs);
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
    	return "";
    }	
    
    public String postTopikPengajuan(String nama_pengajuan_yg_juga_nama_tabel_rules, String target_thsms, String npmhs, String alasan, String nmmhs, String kdpst_origin, String kdpst_target, String kmp_origin, String kmp_target) {
    	//origin-[KTU`67][Ka. BAAK`58][Ka. BAUK`63]
    	//target-[KTU`50][Ka. BAAK`58][Ka. BAUK`63]
    	//System.out.println("masuk");
    	String tipe_pengajuan = nama_pengajuan_yg_juga_nama_tabel_rules.replace("_RULES", "");
    	int i=0;
    	String msg = null;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//cek apa sudah ada pengajuan sebelumnya yg tidak di reject
        	//
        	stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TIPE_PENGAJUAN=? and TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=?");
        	stmt.setString(1, tipe_pengajuan);
        	stmt.setString(2, target_thsms);
        	stmt.setString(3, npmhs);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		//System.out.println("1");
        		//sudah diajukan
        		//
        		int id = rs.getInt("ID");
        		String rejected = rs.getString("REJECTED");
        		boolean lock = rs.getBoolean("LOCKED");
        		String approved = rs.getString("APPROVED");
        		//1.cek apa rejected 
        		if(rejected!=null && !Checker.isStringNullOrEmpty(rejected)) {
        			//pernah direjected - kalo rejected != empty berarti otomati locked
        			//insert pengajuan baru , thus msg == null
        		}
        		else {
        			//rejected null or empty
        			//2. cek apa sudah disetujui
        			if(approved!=null && !Checker.isStringNullOrEmpty(approved)) {
        				if(lock) {
        					//sudah disetujui
        					msg = new String("Pengajuan "+nama_pengajuan_yg_juga_nama_tabel_rules+" untuk tahun/sms "+target_thsms+" sudah diajukan dan sudah disetujui");
        				}
        				else {
        					//inprogress
        					msg = new String("Pengajuan "+nama_pengajuan_yg_juga_nama_tabel_rules+" untuk tahun/sms "+target_thsms+" sudah diajukan dan dalam proses persetujuan");
        	        		
        				}
        				stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_CREATOR=?");
    	        		stmt.setBoolean(1, false); //tidak ditampilkan notifikasi, hanya update saja yg merubah statusnya
    	        		stmt.executeUpdate();
        			}
        			else {
        				//insert pengajuan = cmd==null
        			}
        		}
        		
        	}
        	
        	//System.out.println("msg = "+msg);
        	if(msg==null) {
        		//System.out.println("2 "+kdpst);
        		
        		//get verificator
        		
        		Vector v_verificator_origin_target = Getter.getVerificatorFromTableRule(nama_pengajuan_yg_juga_nama_tabel_rules, target_thsms, kdpst_origin, kdpst_target, kmp_origin, kmp_target);
        		//System.out.println("v_verificator_origin_target="+v_verificator_origin_target);
        		
        		String info_merge =Getter.mergeVerificator(v_verificator_origin_target);// untuk kasus seperti pindah prodi 2 prodi terkait
        		//System.out.println("info_merge="+info_merge);
        		ListIterator li = v_verificator_origin_target.listIterator();
        		String info_origin = null;
        		String info_target = null;
        		if(li.hasNext()) {
        			String brs1 = (String)li.next();
        			info_origin = new String(brs1);
        			
        		}
        		if(kdpst_target!=null) {
        			if(li.hasNext()) {
            			String brs2 = (String)li.next();
            			info_target = new String(brs2);
            		}
        			if(info_origin.contains("null")&&!info_target.contains("null")) {
            			msg = new String("Pastikan jabatan pada prodi asal sudah diisi");
            		}
            		else if(!info_origin.contains("null")&&info_target.contains("null")) {
            			msg = new String("Pastikan jabatan pada prodi tujuan sudah diisi");
            		}
            		else {
            			if(info_origin.contains("null")&&info_target.contains("null")) {
            				msg = new String("Pastikan jabatan pada prodi asal dan tujuan sudah diisi");
            			}
            			
            		}
        		}
        		else {
        			//pengajuan single target prodi - non pindah prodi
        			if(info_origin.contains("null")) {
            			msg = new String("Pastikan jabatan pada prodi sudah diisi");
            		}
        		}
        		/*
        		//System.out.println(info_origin+" -vs- "+info_target);
        		if(info_target!=null) {
        			if(info_origin.contains("null")&&!info_target.contains("null")) {
            			msg = new String("Pastikan jabatan pada prodi asal sudah diisi");
            		}
            		else if(!info_origin.contains("null")&&info_target.contains("null")) {
            			msg = new String("Pastikan jabatan pada prodi tujuan sudah diisi");
            		}
            		else {
            			if(info_origin.contains("null")&&info_target.contains("null")) {
            				msg = new String("Pastikan jabatan pada prodi asal dan tujuan sudah diisi");
            			}
            			
            		}
        		}
        		else {
        			if(info_origin.contains("null")) {
            			msg = new String("Pastikan jabatan pada prodi asal sudah diisi");
            		}
        		}
        		*/
        		/*
        		stmt = con.prepareStatement("select TKN_VERIFICATOR,TKN_VERIFICATOR_ID from CUTI_RULES where THSMS=? and KDPST=?");
        		stmt.setString(++i, target_thsms);
        		stmt.setString(++i, kdpst);
        		rs = stmt.executeQuery();
        		if(!rs.next()) {
        			
        			//System.out.println("3");
        			msg = new String("Aturan Cuti untuk tahun/sms "+target_thsms+" belum diisi");
        			//System.out.println("msg="+msg);
        		}
        		else {
        		*/
        		//System.out.println("msg1 = "+msg);
        		if(msg==null) { //artinya no error jabatan yang diperlukan telah ada objidnya
        			//System.out.println("4");
        			//String tkn_target_obj_nickmane = rs.getString("TKN_VERIFICATOR");
        			//String tkn_target_objid = rs.getString("TKN_VERIFICATOR_ID");
        			//process origin
        			//if(tipe_pengajuan.equalsIgnoreCase("PINDAH_PRODI")) {
        			info_merge = info_merge.replace("`", ",");
        			info_merge = info_merge.replace("][", "`");
        			info_merge = info_merge.replace("[", "");
        			info_merge = info_merge.replace("]", "");
        			StringTokenizer st = new StringTokenizer(info_merge,"`");
        			String info_merge_jabatan = new String();
        			String info_merge_id = new String();
        			while(st.hasMoreTokens()) {
        				String tmp = st.nextToken();
        				StringTokenizer st1 = new StringTokenizer(tmp,",");
        				String job = st1.nextToken(); //remove
        				info_merge_jabatan = info_merge_jabatan+"["+job+"]";
        				String list_objid = new String();
        				while(st1.hasMoreTokens()) {
        					String objid = st1.nextToken();
        					list_objid = list_objid+objid;
        					if(st1.hasMoreTokens()) {
        						list_objid = list_objid+",";
        					}
        				}
        				info_merge_id = info_merge_id +"["+list_objid+"]";
        			}
        			
        			
        			//post pengajuna
        			//sebelum insert cek dulu apa ada record pengajuan yg masih aktif
        			i=0;
        			//
        			stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_NPM=? and LOCKED=? and REJECTED=? and BATAL=?");
        			stmt.setString(++i, target_thsms);
        			stmt.setString(++i, tipe_pengajuan);
        			stmt.setString(++i, npmhs);
        			stmt.setBoolean(++i, false);
        			stmt.setNull(++i, java.sql.Types.VARCHAR);
        			stmt.setBoolean(++i, false);
        			rs = stmt.executeQuery();
        			//kalo belum ada aja diinsert kalo udah ada yah dicuekin aja
        			if(!rs.next()) {
        				//System.out.println("insert");
        				i=0;
                		stmt = con.prepareStatement("INSERT INTO TOPIK_PENGAJUAN(TARGET_THSMS_PENGAJUAN,TIPE_PENGAJUAN,ISI_TOPIK_PENGAJUAN,TOKEN_TARGET_OBJ_NICKNAME,TOKEN_TARGET_OBJID,CREATOR_OBJ_ID,CREATOR_NPM,CREATOR_NMM,SHOW_AT_TARGET,SHOW_AT_CREATOR,CREATOR_KDPST,TARGET_KDPST)values(?,?,?,?,?,?,?,?,?,?,?,?)");
                		stmt.setString(++i, target_thsms);
                		stmt.setString(++i, tipe_pengajuan);
                		stmt.setString(++i, alasan);
                		stmt.setString(++i, info_merge_jabatan);
                		stmt.setString(++i, info_merge_id);
                		stmt.setLong(++i,Checker.getObjectId(npmhs));
                		stmt.setString(++i, npmhs);
                		stmt.setString(++i, nmmhs);
                		stmt.setString(++i, info_merge_id.replace(",", "]["));//untuk show at target , ngga perlu boleh ` krn individual id
                		stmt.setBoolean(++i, false);//tidak ditampilkan notifikasi, hanya update saja yg merubah statusnya
                		stmt.setString(++i, kdpst_origin);
                		if(Checker.isStringNullOrEmpty(kdpst_target)) {
                			stmt.setNull(++i, java.sql.Types.VARCHAR);
                		}
                		else {
                			stmt.setString(++i, kdpst_target);
                		}
                		stmt.executeUpdate();
        			}
            			//else {
            			//	//System.out.println("sudag ahad");
            			//}
            			
            			
        			//}
        			//else {
        				//pengajuan lainnya 
        			//}
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
    	return msg;
    }
    
    public String postTopikPengajuanKelulusan(String target_thsms, String kdpst, String npmhs, String alasan, String nmmhs, String table_rule_nm) {
    	int i=0;
    	String msg = null;
    	try {
           	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//cek apa sudah ada pengajuan sebelumnya yg tidak di reject
        	//
        	String type_pengajuan = table_rule_nm.replace("_RULES", "");
        	String title_pengajuan = type_pengajuan.replace("_", " ");
        	
        	
        	String keterangan = "Pengajuan "+title_pengajuan.toLowerCase();
        	//String tipe = "LULUS";
        	stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TIPE_PENGAJUAN=? and TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=?");
        	stmt.setString(1, type_pengajuan);
        	stmt.setString(2, target_thsms);
        	stmt.setString(3, npmhs);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		//System.out.println("1");
        		//sudah diajukan
        		//
        		int id = rs.getInt("ID");
        		String rejected = rs.getString("REJECTED");
        		boolean lock = rs.getBoolean("LOCKED");
        		String approved = rs.getString("APPROVED");
        		//1.cek apa rejected 
        		if(rejected!=null && !Checker.isStringNullOrEmpty(rejected)) {
        			//pernah direjected - kalo rejected != empty berarti otomati locked
        			//insert pengajuan baru , thus msg == null
        		}
        		else {
        			//rejected null or empty
        			//2. cek apa sudah disetujui
        			if(approved!=null && !Checker.isStringNullOrEmpty(approved)) {
        				if(lock) {
        					//sudah disetujui
        					msg = new String(keterangan+" untuk tahun/sms "+target_thsms+" sudah diajukan dan sudah disetujui");
        				}
        				else {
        					//inprogress
        					msg = new String(keterangan+" untuk tahun/sms "+target_thsms+" sudah diajukan dan dalam proses persetujuan");
        	        		
        				}
        				stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_CREATOR=?");
    	        		stmt.setBoolean(1, false); //tidak ditampilkan notifikasi, hanya update saja yg merubah statusnya
    	        		stmt.executeUpdate();
        			}
        			else {
        				//insert pengajuan = cmd==null
        			}
        		}
        		
        	}
        	
        	if(msg==null) {
        		//System.out.println("2 "+kdpst);
        		//cek tabel cuti rule, sipa approveenya = tkn_objid
        		stmt = con.prepareStatement("select TKN_JABATAN_VERIFICATOR,TKN_VERIFICATOR_ID from "+table_rule_nm+" where THSMS=? and KDPST=?");
        		stmt.setString(++i, target_thsms);
        		stmt.setString(++i, kdpst);
        		rs = stmt.executeQuery();
        		if(!rs.next()) {
        			
        			//System.out.println("3");
        			msg = new String("Aturan "+type_pengajuan.toLowerCase()+" untuk tahun/sms "+target_thsms+" belum diisi");
        			//System.out.println("msg="+msg);
        		}
        		else {
        			//System.out.println("4");
        			String tkn_target_obj_nickmane = rs.getString("TKN_JABATAN_VERIFICATOR");
        			String tkn_target_objid = rs.getString("TKN_VERIFICATOR_ID");
        			
            		if(tkn_target_objid.contains("null")||tkn_target_obj_nickmane.contains("null")) {
            			msg = new String("Pastikan tabel approvee sudah diisi, harap hubungi admin");
            		}
            		
            		if(msg==null) {
            			//post pengajuna
            			//sebelum insert cek dulu apa ada record pengajuan yg masih aktif
            			i=0;
            			stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_NPM=? and LOCKED=? and REJECTED=? and BATAL=?");
            			stmt.setString(++i, target_thsms);
            			stmt.setString(++i, type_pengajuan);
            			stmt.setString(++i, npmhs);
            			stmt.setBoolean(++i, false);
            			stmt.setNull(++i, java.sql.Types.VARCHAR);
            			stmt.setBoolean(++i, false);
            			rs = stmt.executeQuery();
            			if(!rs.next()) {
            				i=0;
                    		stmt = con.prepareStatement("INSERT INTO TOPIK_PENGAJUAN(TARGET_THSMS_PENGAJUAN,TIPE_PENGAJUAN,ISI_TOPIK_PENGAJUAN,TOKEN_TARGET_OBJ_NICKNAME,TOKEN_TARGET_OBJID,CREATOR_OBJ_ID,CREATOR_NPM,CREATOR_NMM,SHOW_AT_TARGET,SHOW_AT_CREATOR,CREATOR_KDPST)values(?,?,?,?,?,?,?,?,?,?,?)");
                    		stmt.setString(++i, target_thsms);
                    		stmt.setString(++i, type_pengajuan);
                    		stmt.setString(++i, alasan);
                    		stmt.setString(++i, tkn_target_obj_nickmane);
                    		stmt.setString(++i, tkn_target_objid);
                    		stmt.setLong(++i,Checker.getObjectId(npmhs));
                    		stmt.setString(++i, npmhs);
                    		stmt.setString(++i, nmmhs);
                    		stmt.setString(++i, tkn_target_objid);
                    		stmt.setBoolean(++i, false);//tidak ditampilkan notifikasi, hanya update saja yg merubah statuija
            				
                    		stmt.setString(++i, kdpst);
                    		stmt.executeUpdate();
            			}
            			
            			//update overview table
                		long npm_id = Checker.getObjectId(npmhs);
                		//cek apa sudah ada record utk objid terkait
                		stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
                		stmt.setLong(1, npm_id);
                		stmt.setString(2, target_thsms);
                		rs = stmt.executeQuery();
                		if(rs.next()) {
                			int tot_cuti_req = rs.getInt("TOT_"+type_pengajuan+"_REQ");
                			int tot_cuti_req_unapproved = rs.getInt("TOT_"+type_pengajuan+"_REQ_UNAPPROVED"); 
                			String list_npm_unapproved = ""+rs.getString("LIST_NPM_"+type_pengajuan+"_UNAPPROVED");
                			//karena add req
                			if(list_npm_unapproved==null || Checker.isStringNullOrEmpty(list_npm_unapproved) || (list_npm_unapproved!=null && !list_npm_unapproved.contains(npmhs))) {
                				if(list_npm_unapproved==null || Checker.isStringNullOrEmpty(list_npm_unapproved)) {
                					list_npm_unapproved = new String(npmhs);	
                				}
                				else {
                					list_npm_unapproved = list_npm_unapproved+","+npmhs;
                    				if(list_npm_unapproved.startsWith(",")) {
                    					list_npm_unapproved = list_npm_unapproved.substring(1, list_npm_unapproved.length());
                    				}
                				}
                				tot_cuti_req++;
            					tot_cuti_req_unapproved++;
            					stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_"+type_pengajuan+"_REQ=?,TOT_"+type_pengajuan+"_REQ_UNAPPROVED=?,LIST_NPM_"+type_pengajuan+"_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
                				stmt.setInt(1, tot_cuti_req);
                				stmt.setInt(2, tot_cuti_req_unapproved);
                				stmt.setString(3, list_npm_unapproved);
                				stmt.setLong(4, npm_id);
                				stmt.setString(5, target_thsms);
                				stmt.executeUpdate();
                			}
                			
                		}
                		else {
                			//insert
                			stmt = con.prepareStatement("insert into OVERVIEW_SEBARAN_TRLSM (ID_OBJ,THSMS,KDPST,TOT_"+type_pengajuan+"_REQ,TOT_"+type_pengajuan+"_REQ_UNAPPROVED,LIST_NPM_"+type_pengajuan+"_UNAPPROVED)values(?,?,?,?,?,?)");
                			stmt.setLong(1, npm_id);
                			stmt.setString(2, target_thsms);
                			stmt.setString(3, kdpst);
                			stmt.setInt(4, 1);
                			stmt.setInt(5, 1);
                			stmt.setString(6, npmhs);
                			stmt.executeUpdate();
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
    	return msg;
    }

    
    public String postTopikPengajuanPindahProdi(String objid_mhs_target_kdpsting,String target_thsms, String kdpst, String npmhs, String alasan, String nmmhs) {
    	int i=0;
    	String msg = null;
    	try {
           	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//cek apa sudah ada pengajuan sebelumnya yg tidak di reject
        	//
        	stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TIPE_PENGAJUAN=? and TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=?");
        	stmt.setString(1, "PP");
        	stmt.setString(2, target_thsms);
        	stmt.setString(3, npmhs);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		//System.out.println("1");
        		//sudah diajukan
        		//
        		int id = rs.getInt("ID");
        		String rejected = rs.getString("REJECTED");
        		boolean lock = rs.getBoolean("LOCKED");
        		String approved = rs.getString("APPROVED");
        		//1.cek apa rejected 
        		if(rejected!=null && !Checker.isStringNullOrEmpty(rejected)) {
        			//pernah direjected - kalo rejected != empty berarti otomati locked
        			//insert pengajuan baru , thus msg == null
        		}
        		else {
        			//rejected null or empty
        			//2. cek apa sudah disetujui
        			if(approved!=null && !Checker.isStringNullOrEmpty(approved)) {
        				if(lock) {
        					//sudah disetujui
        					msg = new String("Pengajuan cuti untuk tahun/sms "+target_thsms+" sudah diajukan dan sudah disetujui");
        				}
        				else {
        					//inprogress
        					msg = new String("Pengajuan cuti untuk tahun/sms "+target_thsms+" sudah diajukan dan dalam proses persetujuan");
        	        		
        				}
        				stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_CREATOR=?");
    	        		stmt.setBoolean(1, false); //tidak ditampilkan notifikasi, hanya update saja yg merubah statusnya
    	        		stmt.executeUpdate();
        			}
        			else {
        				//insert pengajuan = cmd==null
        			}
        		}
        		
        	}
        	
        	if(msg==null) {
        		//System.out.println("2 "+kdpst);
        		//cek tabel cuti rule, sipa approveenya = tkn_objid
        		stmt = con.prepareStatement("select TKN_VERIFICATOR,TKN_VERIFICATOR_ID from CUTI_RULES where THSMS=? and KDPST=?");
        		stmt.setString(++i, target_thsms);
        		stmt.setString(++i, kdpst);
        		rs = stmt.executeQuery();
        		if(!rs.next()) {
        			
        			//System.out.println("3");
        			msg = new String("Aturan Cuti untuk tahun/sms "+target_thsms+" belum diisi");
        			//System.out.println("msg="+msg);
        		}
        		else {
        			//System.out.println("4");
        			String tkn_target_obj_nickmane = rs.getString("TKN_VERIFICATOR");
        			String tkn_target_objid = rs.getString("TKN_VERIFICATOR_ID");
        			
        			//post pengajuna
        			//sebelum insert cek dulu apa ada record pengajuan yg masih aktif
        			i=0;
        			stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_NPM=? and LOCKED=? and REJECTED=? and BATAL=?");
        			stmt.setString(++i, target_thsms);
        			stmt.setString(++i, "PP");
        			stmt.setString(++i, npmhs);
        			stmt.setBoolean(++i, false);
        			stmt.setNull(++i, java.sql.Types.VARCHAR);
        			stmt.setBoolean(++i, false);
        			rs = stmt.executeQuery();
        			if(!rs.next()) {
        				i=0;
                		stmt = con.prepareStatement("INSERT INTO TOPIK_PENGAJUAN(TARGET_THSMS_PENGAJUAN,TIPE_PENGAJUAN,ISI_TOPIK_PENGAJUAN,TOKEN_TARGET_OBJ_NICKNAME,TOKEN_TARGET_OBJID,CREATOR_OBJ_ID,CREATOR_NPM,CREATOR_NMM,SHOW_AT_TARGET,SHOW_AT_CREATOR,CREATOR_KDPST)values(?,?,?,?,?,?,?,?,?,?,?)");
                		stmt.setString(++i, target_thsms);
                		stmt.setString(++i, "PP");
                		stmt.setString(++i, alasan);
                		stmt.setString(++i, tkn_target_obj_nickmane);
                		stmt.setString(++i, tkn_target_objid);
                		stmt.setLong(++i,Checker.getObjectId(npmhs));
                		stmt.setString(++i, npmhs);
                		stmt.setString(++i, nmmhs);
                		stmt.setString(++i, tkn_target_objid);
                		stmt.setBoolean(++i, false);//tidak ditampilkan notifikasi, hanya update saja yg merubah statusnya
                		stmt.setString(++i, kdpst);
                		stmt.executeUpdate();
        			}
        			
        			//update overview table
            		long npm_id = Checker.getObjectId(npmhs);
            		//cek apa sudah ada record utk objid terkait
            		stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
            		stmt.setLong(1, npm_id);
            		stmt.setString(2, target_thsms);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			int tot_cuti_req = rs.getInt("TOT_PP_REQ");
            			int tot_cuti_req_unapproved = rs.getInt("TOT_PP_REQ_UNAPPROVED"); 
            			String list_npm_unapproved = ""+rs.getString("LIST_NPM_CUTI_UNAPPROVED");
            			//karena add req
            			if(list_npm_unapproved==null || Checker.isStringNullOrEmpty(list_npm_unapproved) || (list_npm_unapproved!=null && !list_npm_unapproved.contains(npmhs))) {
            				if(list_npm_unapproved==null || Checker.isStringNullOrEmpty(list_npm_unapproved)) {
            					list_npm_unapproved = new String(npmhs);	
            				}
            				else {
            					list_npm_unapproved = list_npm_unapproved+","+npmhs;
                				if(list_npm_unapproved.startsWith(",")) {
                					list_npm_unapproved = list_npm_unapproved.substring(1, list_npm_unapproved.length());
                				}
            				}
            				tot_cuti_req++;
        					tot_cuti_req_unapproved++;
        					stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_CUTI_REQ=?,TOT_CUTI_REQ_UNAPPROVED=?,LIST_NPM_CUTI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
            				stmt.setInt(1, tot_cuti_req);
            				stmt.setInt(2, tot_cuti_req_unapproved);
            				stmt.setString(3, list_npm_unapproved);
            				stmt.setLong(4, npm_id);
            				stmt.setString(5, target_thsms);
            				stmt.executeUpdate();
            			}
            			else {
            				//if(list_npm_unapproved.contains(npmhs)) {
            					//sudah ada, ignore ngga perlu di update
            				/*
            				}
            				else {
            					tot_cuti_req++;
            					tot_cuti_req_unapproved++;
            					list_npm_unapproved = list_npm_unapproved+","+npmhs;
                				if(list_npm_unapproved.startsWith(",")) {
                					list_npm_unapproved = list_npm_unapproved.substring(1, list_npm_unapproved.length());
                				}
                				stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_CUTI_REQ=?,TOT_CUTI_REQ_UNAPPROVED=?,LIST_NPM_CUTI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
                				stmt.setInt(1, tot_cuti_req);
                				stmt.setInt(2, tot_cuti_req_unapproved);
                				stmt.setString(3, list_npm_unapproved);
                				stmt.setLong(4, npm_id);
                				stmt.setString(5, target_thsms);
                				stmt.executeUpdate();
            				}
            				*/
            			}
            			
            			//tot_cuti_req++; //ngga jadi ditambah krn = yang sudah di approved
            			//tot_cuti_req_unapproved++;
            			
            		}
            		else {
            			//insert
            			stmt = con.prepareStatement("insert into OVERVIEW_SEBARAN_TRLSM (ID_OBJ,THSMS,KDPST,TOT_CUTI_REQ,TOT_CUTI_REQ_UNAPPROVED,LIST_NPM_CUTI_UNAPPROVED)values(?,?,?,?,?,?)");
            			stmt.setLong(1, npm_id);
            			stmt.setString(2, target_thsms);
            			stmt.setString(3, kdpst);
            			stmt.setInt(4, 1);
            			stmt.setInt(5, 1);
            			stmt.setString(6, npmhs);
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
    	return msg;
    }
    
    
    public int cancelPengajuan(String target_npmhs, String target_thsms, String tipe_pengajuan, long usr_obj_id) {
    	int i=0; 
    	//System.out.println("~"+target_npmhs+" "+target_thsms+" "+tipe_pengajuan+" "+usr_obj_id);
    	try {
           	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//get id topik
        	stmt = con.prepareStatement("select ID,TOKEN_TARGET_OBJID from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_NPM=? and LOCKED=?");
    		stmt.setString(1, target_thsms);
    		stmt.setString(2, tipe_pengajuan);
    		stmt.setString(3, target_npmhs);
    		stmt.setBoolean(4, false);
    		rs = stmt.executeQuery();
    		rs.next();
    		long id = rs.getLong(1);
    		String tkn_target_objid = rs.getString(2);
    		//insert  subtopik cancel
    		stmt=con.prepareStatement("insert into SUBTOPIK_PENGAJUAN (ID_TOPIK,ISI_SUBTOPIK,CREATOR_OBJ_ID,CREATOR_NPM,VERDICT)values(?,?,?,?,?)");
    		stmt.setLong(1,id);
    		stmt.setString(2, "BATAL PERMOHONAN");
    		stmt.setLong(3,usr_obj_id);
    		stmt.setString(4, this.operatorNpm);
    		stmt.setString(5, "BATAL");
    		stmt.executeUpdate();
    		
    		//update topik 
    		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_TARGET=?,SHOW_AT_CREATOR=?,LOCKED=?,BATAL=? where ID=?");
    		stmt.setString(1, tkn_target_objid.replace(",", "]["));
    		stmt.setBoolean(2, false);
    		stmt.setBoolean(3, true);
    		stmt.setBoolean(4, true);
    		stmt.setLong(5,id);
    		i = stmt.executeUpdate();
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
    	return i;
    }			
    	
    public int updatePengajuanAtTabelOverview(String target_npmhs, String target_thsms, String tipe_pengajuan, long usr_obj_id) {
    	int i = 0;
    	try {
    		//update table overview
    		//TOT CUTI REQ = selain yg ditolak
    		//jadi kalo di tolak/batal TOT CUTI REQ - 1 
    		//TOT_CUTI_REQ_UNAPPROVED -1 krn kalo ditolak langsung ke lock = artinya sudah final verdict
    		//if(creator_obj_id_topik==null || Checker.isStringNullOrEmpty(creator_obj_id_topik) || creator_obj_id_topik.equalsIgnoreCase("0")) {
    		String	creator_obj_id_topik = ""+Checker.getObjectId(target_npmhs);
    		if(tipe_pengajuan.equalsIgnoreCase("PINDAH_PRODI")) {
    			
        		//}
        		//stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
    			stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED like ? and THSMS=?");
        		stmt.setString(1, "%"+target_npmhs+"%");
        		stmt.setString(2, target_thsms);
        		rs = stmt.executeQuery();
        		String kdpst_origin = "";
        		String kdpst_target = "";
        		//int tot_in_origin
        		while(rs.next()) {
        			
        		}
        		
        		/* 
        		int tot_req = rs.getInt("TOT_CUTI_REQ");
        		int tot_req_unapproved = rs.getInt("TOT_CUTI_REQ_UNAPPROVED");
        		String list_npm_unappproved = rs.getString("LIST_NPM_CUTI_UNAPPROVED");
        		list_npm_unappproved = list_npm_unappproved.replace(target_npmhs, "");
        		list_npm_unappproved = list_npm_unappproved.replace(",,", "");
        		if(list_npm_unappproved.startsWith(",")) {
        			list_npm_unappproved = list_npm_unappproved.substring(1, list_npm_unappproved.length());
        		}
        		if(list_npm_unappproved.endsWith(",")) {
        			list_npm_unappproved = list_npm_unappproved.substring(0, list_npm_unappproved.length()-1);
        		}
        		
        		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_CUTI_REQ=?,TOT_CUTI_REQ_UNAPPROVED=?,LIST_NPM_CUTI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
        		--tot_req;
        		if(tot_req<0) {
        			tot_req=0;
        		}
        		--tot_req_unapproved;
        		if(tot_req_unapproved<0) {
        			tot_req_unapproved=0;
        		}
        		stmt.setInt(1, tot_req);
        		stmt.setInt(2, tot_req_unapproved);
        		if(Checker.isStringNullOrEmpty(list_npm_unappproved)) {
        			stmt.setNull(3, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(3,list_npm_unappproved);
        		}
        		stmt.setLong(4, Long.parseLong(creator_obj_id_topik));
        		stmt.setString(5,target_thsms);
    			i = stmt.executeUpdate();
    			*/
    		}
    		else {
    			int tot_req = rs.getInt("TOT_"+tipe_pengajuan+"_REQ");
        		int tot_req_unapproved = rs.getInt("TOT_CUTI_REQ_UNAPPROVED");
        		String list_npm_unappproved = rs.getString("LIST_NPM_CUTI_UNAPPROVED");
        		list_npm_unappproved = list_npm_unappproved.replace(target_npmhs, "");
        		list_npm_unappproved = list_npm_unappproved.replace(",,", "");
        		if(list_npm_unappproved.startsWith(",")) {
        			list_npm_unappproved = list_npm_unappproved.substring(1, list_npm_unappproved.length());
        		}
        		if(list_npm_unappproved.endsWith(",")) {
        			list_npm_unappproved = list_npm_unappproved.substring(0, list_npm_unappproved.length()-1);
        		}
        		
        		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_CUTI_REQ=?,TOT_CUTI_REQ_UNAPPROVED=?,LIST_NPM_CUTI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
        		--tot_req;
        		if(tot_req<0) {
        			tot_req=0;
        		}
        		--tot_req_unapproved;
        		if(tot_req_unapproved<0) {
        			tot_req_unapproved=0;
        		}
        		stmt.setInt(1, tot_req);
        		stmt.setInt(2, tot_req_unapproved);
        		if(Checker.isStringNullOrEmpty(list_npm_unappproved)) {
        			stmt.setNull(3, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(3,list_npm_unappproved);
        		}
        		stmt.setLong(4, Long.parseLong(creator_obj_id_topik));
        		stmt.setString(5,target_thsms);
    			i = stmt.executeUpdate();
    		}
    		
    		
    	}
        
        catch (SQLException ex) {
        	ex.printStackTrace();
        } 
        finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    	    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    	    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	return i;
    }
    
    public int updateBatalPengajuanAtTabelOverview(String target_npmhs, String target_thsms, String tipe_pengajuan, long usr_obj_id) {
    	int i = 0;
    	try {
    		String	creator_obj_id_topik = ""+Checker.getObjectId(target_npmhs);
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
    		//update table overview
    		//TOT CUTI REQ = selain yg ditolak
    		//jadi kalo di tolak/batal TOT CUTI REQ - 1 
    		//TOT_CUTI_REQ_UNAPPROVED -1 krn kalo ditolak langsung ke lock = artinya sudah final verdict
    		//if(creator_obj_id_topik==null || Checker.isStringNullOrEmpty(creator_obj_id_topik) || creator_obj_id_topik.equalsIgnoreCase("0")) {
    		
    		
        	stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED like ? and THSMS=?");
    		
    		if(tipe_pengajuan.equalsIgnoreCase("PINDAH_PRODI")) {
    			//System.out.println("ngga boleh kesini");
        		//update origin info
    			
    			
        		stmt.setString(1, "%"+target_npmhs+"-out%");
        		stmt.setString(2, target_thsms);
        		rs = stmt.executeQuery();
        		
        		
        		if(rs.next()) { // harusnya cuma da 1 row data doang
        			long idobj =rs.getLong("ID_OBJ");
        			String list_npm = rs.getString("LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED");
        			int tot_npm_out = rs.getInt("TOT_NPM_"+tipe_pengajuan+"_OUT");
        			list_npm = list_npm.replace(target_npmhs+"-out", "");
        			if(list_npm.endsWith("`") || list_npm.endsWith(",")) {
        				list_npm = list_npm.substring(0, list_npm.length()-1);
        			}
        			if(list_npm.contains("`")) {
        				list_npm = list_npm.replace("``", "`");
        			}
        			else if(list_npm.contains(",")) {
        				list_npm = list_npm.replace(",,", ",");
        			}
        			
        			stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_NPM_"+tipe_pengajuan+"_OUT=?,LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED=? where ID_OBJ=? and THSMS=? ");
        			if((tot_npm_out-1)==0) {
        				stmt.setNull(1, java.sql.Types.INTEGER);
        			}
        			else {
        				stmt.setInt(1, tot_npm_out-1);	
        			}
        			
        			if(Checker.isStringNullOrEmpty(list_npm)) {
        				stmt.setNull(2, java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(2, list_npm);	
        			}
        			stmt.setLong(3, idobj);
        			stmt.setString(4, target_thsms);
        			stmt.executeUpdate();
        		}
        		//update target info
    			stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED like ? and THSMS=?");
        		stmt.setString(1, "%"+target_npmhs+"-in%");
        		stmt.setString(2, target_thsms);
        		rs = stmt.executeQuery();
        		
        		
        		if(rs.next()) { // harusnya cuma da 1 row data doang
        			long idobj =rs.getLong("ID_OBJ");
        			String list_npm = rs.getString("LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED");
        			int tot_npm_in = rs.getInt("TOT_NPM_"+tipe_pengajuan+"_IN");
        			list_npm = list_npm.replace(target_npmhs+"-in", "");
        			if(list_npm.endsWith("`") || list_npm.endsWith(",")) {
        				list_npm = list_npm.substring(0, list_npm.length()-1);
        			}
        			if(list_npm.contains("`")) {
        				list_npm = list_npm.replace("``", "`");
        			}
        			else if(list_npm.contains(",")) {
        				list_npm = list_npm.replace(",,", ",");
        			}
        			
        			stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_NPM_"+tipe_pengajuan+"_IN=?,LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED=? where ID_OBJ=? and THSMS=? ");
        			
        			if((tot_npm_in-1)==0) {
        				stmt.setNull(1, java.sql.Types.INTEGER);
        			}
        			else {
        				stmt.setInt(1, tot_npm_in-1);	
        			}
        			
        			if(Checker.isStringNullOrEmpty(list_npm)) {
        				stmt.setNull(2, java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(2, list_npm);	
        			}
        			stmt.setLong(3, idobj);
        			stmt.setString(4, target_thsms);
        			stmt.executeUpdate();
        		}
        		
        		/* 
        		int tot_req = rs.getInt("TOT_CUTI_REQ");
        		int tot_req_unapproved = rs.getInt("TOT_CUTI_REQ_UNAPPROVED");
        		String list_npm_unappproved = rs.getString("LIST_NPM_CUTI_UNAPPROVED");
        		list_npm_unappproved = list_npm_unappproved.replace(target_npmhs, "");
        		list_npm_unappproved = list_npm_unappproved.replace(",,", "");
        		if(list_npm_unappproved.startsWith(",")) {
        			list_npm_unappproved = list_npm_unappproved.substring(1, list_npm_unappproved.length());
        		}
        		if(list_npm_unappproved.endsWith(",")) {
        			list_npm_unappproved = list_npm_unappproved.substring(0, list_npm_unappproved.length()-1);
        		}
        		
        		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_CUTI_REQ=?,TOT_CUTI_REQ_UNAPPROVED=?,LIST_NPM_CUTI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
        		--tot_req;
        		if(tot_req<0) {
        			tot_req=0;
        		}
        		--tot_req_unapproved;
        		if(tot_req_unapproved<0) {
        			tot_req_unapproved=0;
        		}
        		stmt.setInt(1, tot_req);
        		stmt.setInt(2, tot_req_unapproved);
        		if(Checker.isStringNullOrEmpty(list_npm_unappproved)) {
        			stmt.setNull(3, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(3,list_npm_unappproved);
        		}
        		stmt.setLong(4, Long.parseLong(creator_obj_id_topik));
        		stmt.setString(5,target_thsms);
    			i = stmt.executeUpdate();
    			*/
    		}
    		else {
    			stmt.setString(1, "%"+target_npmhs+"%");
        		stmt.setString(2, target_thsms);
        		rs = stmt.executeQuery();
        		rs.next();
    			//System.out.println("harus kesisni2");
    			int tot_req = rs.getInt("TOT_"+tipe_pengajuan+"_REQ");
    			//System.out.println("harus kesisni3");
        		int tot_req_unapproved = rs.getInt("TOT_"+tipe_pengajuan+"_REQ_UNAPPROVED");
        		String list_npm_unappproved = rs.getString("LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED");
        		list_npm_unappproved = list_npm_unappproved.replace(target_npmhs, "");
        		list_npm_unappproved = list_npm_unappproved.replace(",,", "");
        		if(list_npm_unappproved.startsWith(",")) {
        			list_npm_unappproved = list_npm_unappproved.substring(1, list_npm_unappproved.length());
        		}
        		if(list_npm_unappproved.endsWith(",")) {
        			list_npm_unappproved = list_npm_unappproved.substring(0, list_npm_unappproved.length()-1);
        		}
        		
        		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_"+tipe_pengajuan+"_REQ=?,TOT_"+tipe_pengajuan+"_REQ_UNAPPROVED=?,LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
        		--tot_req;
        		if(tot_req<0) {
        			tot_req=0;
        		}
        		--tot_req_unapproved;
        		if(tot_req_unapproved<0) {
        			tot_req_unapproved=0;
        		}
        		stmt.setInt(1, tot_req);
        		stmt.setInt(2, tot_req_unapproved);
        		if(Checker.isStringNullOrEmpty(list_npm_unappproved)) {
        			stmt.setNull(3, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(3,list_npm_unappproved);
        		}
        		//System.out.println(tot_req+" , "+tot_req_unapproved+" , "+list_npm_unappproved);;
        		stmt.setLong(4, Long.parseLong(creator_obj_id_topik));
        		stmt.setString(5,target_thsms);
    			i = stmt.executeUpdate();
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
    	return i;
    }
    
    public String approvalPengajuanCuti(String id_topik, int approvee_objid, String approvee_npm, String approval_verdict, String alasan) {
    	int i=0; 
    	String msg = null;
    	try {
           	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where ID=?");
    		stmt.setLong(1, Long.parseLong(id_topik));
    		rs = stmt.executeQuery();
    		rs.next();
    		
			String thsms_pengajuan_topik=""+rs.getString("TARGET_THSMS_PENGAJUAN");
			String tipe_pengajuan_topik=""+rs.getString("TIPE_PENGAJUAN");
			String isi_topik_pengajuan_topik=""+rs.getString("ISI_TOPIK_PENGAJUAN");
			String tkn_target_objnickname_topik=""+rs.getString("TOKEN_TARGET_OBJ_NICKNAME");
			String tkn_target_objid_topik=""+rs.getString("TOKEN_TARGET_OBJID");
			String target_npm_topik=""+rs.getString("TARGET_NPM");
			String creator_obj_id_topik=""+rs.getLong("CREATOR_OBJ_ID");
			String creator_npm_topik=""+rs.getString("CREATOR_NPM");
			String creator_nmm_topik=""+rs.getString("CREATOR_NMM");
			String shwow_at_target_topik=""+rs.getString("SHOW_AT_TARGET");
			String show_at_creator_topik=""+rs.getBoolean("SHOW_AT_CREATOR");
			String updtm_topik=""+rs.getTimestamp("UPDTM");
			String approved_topik=""+rs.getString("APPROVED");
			String locked_topik=""+rs.getBoolean("LOCKED");
			String rejected_topik=""+rs.getString("REJECTED");
			
        	if(approval_verdict.contains("TOLAK")) { 
        		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_TARGET=?,SHOW_AT_CREATOR=?,LOCKED=?,REJECTED=? where ID=?");
        		int j=1;
        		//kalo ditolak maka hide at target = SHOW_AT_TARGET set null, karena kalo udah ada yg cancel yg belum ngasih verdic ngga perlu liat
        		//hidden at target
        		stmt.setNull(j++,java.sql.Types.VARCHAR);
        		/*
        		if(shwow_at_target_topik==null || Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        			stmt.setNull(j++,java.sql.Types.VARCHAR);
        		}
        		else {
        			shwow_at_target_topik = shwow_at_target_topik.replace("["+approvee_objid+"]","");
        			if(Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        				stmt.setNull(j++,java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(j++,shwow_at_target_topik);	
        			}
        		}
        		*/
        		stmt.setBoolean(j++, true);
        		stmt.setBoolean(j++, true);//locked
        		//if contain aprooved objid disini
        		if(rejected_topik==null || Checker.isStringNullOrEmpty(rejected_topik)) {
        			rejected_topik = new String("["+approvee_objid+"]");
        		}
        		else if(!rejected_topik.contains("["+approvee_objid+"]")) {
        			rejected_topik = rejected_topik+"["+approvee_objid+"]";
        			
        		}
        		stmt.setString(j++, rejected_topik);
        		
        		
        		stmt.setLong(j++, Long.parseLong(id_topik));
        		stmt.executeUpdate();//update topik
        		//2. insert subtopik
        		j=1;
        		stmt = con.prepareStatement("insert into SUBTOPIK_PENGAJUAN(ID_TOPIK,ISI_SUBTOPIK,CREATOR_OBJ_ID,CREATOR_NPM,VERDICT)values(?,?,?,?,?)");
        		stmt.setLong(j++, Long.parseLong(id_topik));
        		stmt.setString(j++, alasan);
        		stmt.setInt(j++, approvee_objid);
        		stmt.setString(j++, approvee_npm);
        		stmt.setString(j++, approval_verdict);
        		stmt.executeUpdate();
        		//update table overview
        		//TOT CUTI REQ = selain yg ditolak
        		//jadi kalo di tolak/batal TOT CUTI REQ - 1 
        		//TOT_CUTI_REQ_UNAPPROVED -1 krn kalo ditolak langsung ke lock = artinya sudah final verdict
        		if(creator_obj_id_topik==null || Checker.isStringNullOrEmpty(creator_obj_id_topik) || creator_obj_id_topik.equalsIgnoreCase("0")) {
        			creator_obj_id_topik = ""+Checker.getObjectId(creator_npm_topik);
        		}
        		stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
        		stmt.setLong(1, Long.parseLong(creator_obj_id_topik));
        		stmt.setString(2, thsms_pengajuan_topik);
        		rs = stmt.executeQuery();
        		rs.next();
        		int tot_req = rs.getInt("TOT_CUTI_REQ");
        		int tot_req_unapproved = rs.getInt("TOT_CUTI_REQ_UNAPPROVED");
        		String list_npm_unappproved = rs.getString("LIST_NPM_CUTI_UNAPPROVED");
        		list_npm_unappproved = list_npm_unappproved.replace(creator_npm_topik, "");
        		list_npm_unappproved = list_npm_unappproved.replace(",,", "");
        		if(list_npm_unappproved.startsWith(",")) {
        			list_npm_unappproved = list_npm_unappproved.substring(1, list_npm_unappproved.length());
        		}
        		if(list_npm_unappproved.endsWith(",")) {
        			list_npm_unappproved = list_npm_unappproved.substring(0, list_npm_unappproved.length()-1);
        		}
        		
        		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_CUTI_REQ=?,TOT_CUTI_REQ_UNAPPROVED=?,LIST_NPM_CUTI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
        		--tot_req;
        		if(tot_req<0) {
        			tot_req=0;
        		}
        		--tot_req_unapproved;
        		if(tot_req_unapproved<0) {
        			tot_req_unapproved=0;
        		}
        		stmt.setInt(1, tot_req);
        		stmt.setInt(2, tot_req_unapproved);
        		if(Checker.isStringNullOrEmpty(list_npm_unappproved)) {
        			stmt.setNull(3, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(3,list_npm_unappproved);
        		}
        		stmt.setLong(4, Long.parseLong(creator_obj_id_topik));
        		stmt.setString(5,thsms_pengajuan_topik);
    			stmt.executeUpdate();
        	}
        	else if(approval_verdict.contains("TERIMA")) { 
        		//terima
        		//tkn_target_objid_topik = id approvees
        		//set approved topik value
        		
        		
        		
        		//if contain aprooved objid disini
        		if(approved_topik==null || Checker.isStringNullOrEmpty(approved_topik)) {
        			approved_topik = new String("["+approvee_objid+"]");
        		}
        		else if(!approved_topik.contains("["+approvee_objid+"]")) {
        			approved_topik = approved_topik+"["+approvee_objid+"]";
        		}
        		
        		//cek sudah approved semua ato belum
        		boolean all_approved = AddHocFunction.isAllApproved(id_topik, tkn_target_objid_topik);
        		/*
        		String tkn_requird_approvee_id = new String(tkn_target_objid_topik);
        		tkn_requird_approvee_id = tkn_requird_approvee_id.replace("]", "`");
        		tkn_requird_approvee_id = tkn_requird_approvee_id.replace("[", "");
        		//System.out.println("tkn_target_objid_topik="+tkn_requird_approvee_id);
        		StringTokenizer st = new StringTokenizer(tkn_requird_approvee_id,"`");
        		boolean all_approved = true;
        		//System.out.println("approved_topik="+approved_topik);
        		if(st.hasMoreTokens()) {
        			while(st.hasMoreTokens() && all_approved) {
        				String required_id_approvee = st.nextToken();
        				//System.out.println("required_id_approvee="+required_id_approvee);
        				if(!approved_topik.contains("["+required_id_approvee+"]")) {
        					all_approved = false;
        				}
        			}
        		}
        		else {
        			all_approved = false;
        		}
        		*/
        		//System.out.println("all_approved="+all_approved);
        		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_TARGET=?,SHOW_AT_CREATOR=?,LOCKED=?,APPROVED=? where ID=?");
        		int j=1;
        		//hidden at target
        		if(shwow_at_target_topik==null || Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        			stmt.setNull(j++,java.sql.Types.VARCHAR);
        		}
        		else {
        			shwow_at_target_topik = shwow_at_target_topik.replace("["+approvee_objid+"]","");
        			if(Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        				stmt.setNull(j++,java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(j++,shwow_at_target_topik);	
        			}
        			
        		}
        		stmt.setBoolean(j++, true);
        		if(all_approved) {
        			stmt.setBoolean(j++, true);//locked - belum approved semua
        		}
        		else {
        			stmt.setBoolean(j++, false);//locked - belum approved semua	
        		}
        		
        		
        		stmt.setString(j++, approved_topik);
        		stmt.setLong(j++, Long.parseLong(id_topik));
        		stmt.executeUpdate();//update topik
        		//2. insert subtopik
        		j=1;
        		stmt = con.prepareStatement("insert into SUBTOPIK_PENGAJUAN(ID_TOPIK,ISI_SUBTOPIK,CREATOR_OBJ_ID,CREATOR_NPM,VERDICT)values(?,?,?,?,?)");
        		stmt.setLong(j++, Long.parseLong(id_topik));
        		stmt.setString(j++, approval_verdict);
        		stmt.setInt(j++, approvee_objid);
        		stmt.setString(j++, approvee_npm);
        		stmt.setString(j++, approval_verdict);
        		stmt.executeUpdate();
        		
        		if(all_approved) {
        			//update table overview
            		//TOT CUTI REQ = selain yg ditolak
            		//jadi kalo di tolak/batal TOT CUTI REQ - 1 
            		//TOT_CUTI_REQ_UNAPPROVED -1 krn kalo ditolak langsung ke lock = artinya sudah final verdict
            		if(creator_obj_id_topik==null || Checker.isStringNullOrEmpty(creator_obj_id_topik) || creator_obj_id_topik.equalsIgnoreCase("0")) {
            			creator_obj_id_topik = ""+Checker.getObjectId(creator_npm_topik);
            		}
            		stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
            		stmt.setLong(1, Long.parseLong(creator_obj_id_topik));
            		stmt.setString(2, thsms_pengajuan_topik);
            		rs = stmt.executeQuery();
            		rs.next();
            		int tot_req = rs.getInt("TOT_CUTI_REQ");
            		int tot_req_unapproved = rs.getInt("TOT_CUTI_REQ_UNAPPROVED");
            		String list_npm_unappproved = rs.getString("LIST_NPM_CUTI_UNAPPROVED");
            		list_npm_unappproved = list_npm_unappproved.replace(creator_npm_topik, "");
            		list_npm_unappproved = list_npm_unappproved.replace(",,", "");
            		if(list_npm_unappproved.startsWith(",")) {
            			list_npm_unappproved = list_npm_unappproved.substring(1, list_npm_unappproved.length());
            		}
            		if(list_npm_unappproved.endsWith(",")) {
            			list_npm_unappproved = list_npm_unappproved.substring(0, list_npm_unappproved.length()-1);
            		}
            		--tot_req_unapproved;
            		if(tot_req_unapproved<0) {
            			tot_req_unapproved=0;
            		}
            		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_CUTI_REQ=?,TOT_CUTI_REQ_UNAPPROVED=?,LIST_NPM_CUTI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
            		stmt.setInt(1, tot_req);
            		stmt.setInt(2, tot_req_unapproved);
            		if(Checker.isStringNullOrEmpty(list_npm_unappproved)) {
            			stmt.setNull(3, java.sql.Types.VARCHAR);
            		}
            		else {
            			stmt.setString(3,list_npm_unappproved);
            		}
            		stmt.setLong(4, Long.parseLong(creator_obj_id_topik));
            		stmt.setString(5,thsms_pengajuan_topik);
        			stmt.executeUpdate();
        		}
        		//else {
        			
        		//}
        	}
        	else if(approval_verdict.contains("RESET")) {
        		//remove verdict dari sub topik
        		stmt = con.prepareStatement("delete from SUBTOPIK_PENGAJUAN where ID_TOPIK=? and CREATOR_OBJ_ID=?");
        		stmt.setLong(1, Long.parseLong(id_topik));
        		stmt.setInt(2, approvee_objid);
        		stmt.executeUpdate();
        		//update topik
        		//1.show at creator
        		//2.show at target & show at creator
        		//3.remove verdicr dari approved & rejected
        		
    			
        		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_TARGET=?,SHOW_AT_CREATOR=?,APPROVED=?,REJECTED=? where ID=?");
        		if(shwow_at_target_topik==null || Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        			shwow_at_target_topik = new String("["+approvee_objid+"]");
        		}
        		else if(!shwow_at_target_topik.contains("["+approvee_objid+"]")) {
        			shwow_at_target_topik = shwow_at_target_topik+"["+approvee_objid+"]";
        		}
        		stmt.setString(1, shwow_at_target_topik);
        		stmt.setBoolean(2, true);
        		if(approved_topik==null || Checker.isStringNullOrEmpty(approved_topik)) {
        			stmt.setNull(3, java.sql.Types.VARCHAR);
        		}
        		else {
        			approved_topik = approved_topik.replace("["+approvee_objid+"]", "");
        			if(Checker.isStringNullOrEmpty(approved_topik)) {
        				stmt.setNull(3, java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(3, approved_topik);
        			}
        			
        		}
        		if(rejected_topik==null || Checker.isStringNullOrEmpty(rejected_topik)) {
        			stmt.setNull(4, java.sql.Types.VARCHAR);
        		}
        		else {
        			rejected_topik = rejected_topik.replace("["+approvee_objid+"]", "");
        			if(Checker.isStringNullOrEmpty(rejected_topik)) {
        				stmt.setNull(4, java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(4, rejected_topik);
        			}
        			
        		}
        		stmt.setLong(5, Long.parseLong(id_topik));
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
    	return msg;
    }
    
    public String approvalPengajuanKelulusan(String id_topik, int approvee_objid, String approvee_npm, String approval_verdict, String alasan) {
    	int i=0; 
    	String msg = null;
    	try {
           	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where ID=?");
    		stmt.setLong(1, Long.parseLong(id_topik));
    		rs = stmt.executeQuery();
    		rs.next();
    		
			String thsms_pengajuan_topik=""+rs.getString("TARGET_THSMS_PENGAJUAN");
			String tipe_pengajuan_topik=""+rs.getString("TIPE_PENGAJUAN");
			String isi_topik_pengajuan_topik=""+rs.getString("ISI_TOPIK_PENGAJUAN");
			String tkn_target_objnickname_topik=""+rs.getString("TOKEN_TARGET_OBJ_NICKNAME");
			String tkn_target_objid_topik=""+rs.getString("TOKEN_TARGET_OBJID");
			String target_npm_topik=""+rs.getString("TARGET_NPM");
			String creator_obj_id_topik=""+rs.getLong("CREATOR_OBJ_ID");
			String creator_npm_topik=""+rs.getString("CREATOR_NPM");
			String creator_nmm_topik=""+rs.getString("CREATOR_NMM");
			String shwow_at_target_topik=""+rs.getString("SHOW_AT_TARGET");
			String show_at_creator_topik=""+rs.getBoolean("SHOW_AT_CREATOR");
			String updtm_topik=""+rs.getTimestamp("UPDTM");
			String approved_topik=""+rs.getString("APPROVED");
			String locked_topik=""+rs.getBoolean("LOCKED");
			String rejected_topik=""+rs.getString("REJECTED");
			
        	if(approval_verdict.contains("TOLAK")) { 
        		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_TARGET=?,SHOW_AT_CREATOR=?,LOCKED=?,REJECTED=? where ID=?");
        		int j=1;
        		//kalo ditolak maka hide at target = SHOW_AT_TARGET set null, karena kalo udah ada yg cancel yg belum ngasih verdic ngga perlu liat
        		//hidden at target
        		stmt.setNull(j++,java.sql.Types.VARCHAR);
        		/*
        		if(shwow_at_target_topik==null || Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        			stmt.setNull(j++,java.sql.Types.VARCHAR);
        		}
        		else {
        			shwow_at_target_topik = shwow_at_target_topik.replace("["+approvee_objid+"]","");
        			if(Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        				stmt.setNull(j++,java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(j++,shwow_at_target_topik);	
        			}
        		}
        		*/
        		stmt.setBoolean(j++, true);
        		stmt.setBoolean(j++, true);//locked
        		//if contain aprooved objid disini
        		if(rejected_topik==null || Checker.isStringNullOrEmpty(rejected_topik)) {
        			rejected_topik = new String("["+approvee_objid+"]");
        		}
        		else if(!rejected_topik.contains("["+approvee_objid+"]")) {
        			rejected_topik = rejected_topik+"["+approvee_objid+"]";
        			
        		}
        		stmt.setString(j++, rejected_topik);
        		
        		
        		stmt.setLong(j++, Long.parseLong(id_topik));
        		stmt.executeUpdate();//update topik
        		//2. insert subtopik
        		j=1;
        		stmt = con.prepareStatement("insert into SUBTOPIK_PENGAJUAN(ID_TOPIK,ISI_SUBTOPIK,CREATOR_OBJ_ID,CREATOR_NPM,VERDICT)values(?,?,?,?,?)");
        		stmt.setLong(j++, Long.parseLong(id_topik));
        		stmt.setString(j++, alasan);
        		stmt.setInt(j++, approvee_objid);
        		stmt.setString(j++, approvee_npm);
        		stmt.setString(j++, approval_verdict);
        		stmt.executeUpdate();
        		//update table overview
        		//TOT CUTI REQ = selain yg ditolak
        		//jadi kalo di tolak/batal TOT CUTI REQ - 1 
        		//TOT_CUTI_REQ_UNAPPROVED -1 krn kalo ditolak langsung ke lock = artinya sudah final verdict
        		if(creator_obj_id_topik==null || Checker.isStringNullOrEmpty(creator_obj_id_topik) || creator_obj_id_topik.equalsIgnoreCase("0")) {
        			creator_obj_id_topik = ""+Checker.getObjectId(creator_npm_topik);
        		}
        		stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
        		stmt.setLong(1, Long.parseLong(creator_obj_id_topik));
        		stmt.setString(2, thsms_pengajuan_topik);
        		rs = stmt.executeQuery();
        		rs.next();
        		int tot_req = rs.getInt("TOT_"+tipe_pengajuan_topik+"_REQ");
        		int tot_req_unapproved = rs.getInt("TOT_"+tipe_pengajuan_topik+"_REQ_UNAPPROVED");
        		String list_npm_unappproved = rs.getString("LIST_NPM_"+tipe_pengajuan_topik+"_UNAPPROVED");
        		list_npm_unappproved = list_npm_unappproved.replace(creator_npm_topik, "");
        		list_npm_unappproved = list_npm_unappproved.replace(",,", "");
        		if(list_npm_unappproved.startsWith(",")) {
        			list_npm_unappproved = list_npm_unappproved.substring(1, list_npm_unappproved.length());
        		}
        		if(list_npm_unappproved.endsWith(",")) {
        			list_npm_unappproved = list_npm_unappproved.substring(0, list_npm_unappproved.length()-1);
        		}
        		
        		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_"+tipe_pengajuan_topik+"_REQ=?,TOT_"+tipe_pengajuan_topik+"_REQ_UNAPPROVED=?,LIST_NPM_"+tipe_pengajuan_topik+"_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
        		--tot_req;
        		if(tot_req<0) {
        			tot_req=0;
        		}
        		--tot_req_unapproved;
        		if(tot_req_unapproved<0) {
        			tot_req_unapproved=0;
        		}
        		stmt.setInt(1, tot_req);
        		stmt.setInt(2, tot_req_unapproved);
        		if(Checker.isStringNullOrEmpty(list_npm_unappproved)) {
        			stmt.setNull(3, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(3,list_npm_unappproved);
        		}
        		stmt.setLong(4, Long.parseLong(creator_obj_id_topik));
        		stmt.setString(5,thsms_pengajuan_topik);
    			stmt.executeUpdate();
        	}
        	else if(approval_verdict.contains("TERIMA")) { 
        		//terima
        		//tkn_target_objid_topik = id approvees
        		//set approved topik value
        		
        		
        		
        		//if contain aprooved objid disini
        		if(approved_topik==null || Checker.isStringNullOrEmpty(approved_topik)) {
        			approved_topik = new String("["+approvee_objid+"]");
        		}
        		else if(!approved_topik.contains("["+approvee_objid+"]")) {
        			approved_topik = approved_topik+"["+approvee_objid+"]";
        		}
        		
        		//cek sudah approved semua ato belum
        		boolean all_approved = AddHocFunction.isAllApproved(id_topik, tkn_target_objid_topik);
        		/*
        		String tkn_requird_approvee_id = new String(tkn_target_objid_topik);
        		tkn_requird_approvee_id = tkn_requird_approvee_id.replace("]", "`");
        		tkn_requird_approvee_id = tkn_requird_approvee_id.replace("[", "");
        		//System.out.println("tkn_target_objid_topik="+tkn_requird_approvee_id);
        		StringTokenizer st = new StringTokenizer(tkn_requird_approvee_id,"`");
        		boolean all_approved = true;
        		//System.out.println("approved_topik="+approved_topik);
        		if(st.hasMoreTokens()) {
        			while(st.hasMoreTokens() && all_approved) {
        				String required_id_approvee = st.nextToken();
        				//System.out.println("required_id_approvee="+required_id_approvee);
        				if(!approved_topik.contains("["+required_id_approvee+"]")) {
        					all_approved = false;
        				}
        			}
        		}
        		else {
        			all_approved = false;
        		}
        		*/
        		//System.out.println("all_approved="+all_approved);
        		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_TARGET=?,SHOW_AT_CREATOR=?,LOCKED=?,APPROVED=? where ID=?");
        		int j=1;
        		//hidden at target
        		if(shwow_at_target_topik==null || Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        			stmt.setNull(j++,java.sql.Types.VARCHAR);
        		}
        		else {
        			shwow_at_target_topik = shwow_at_target_topik.replace("["+approvee_objid+"]","");
        			if(Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        				stmt.setNull(j++,java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(j++,shwow_at_target_topik);	
        			}
        			
        		}
        		stmt.setBoolean(j++, true);
        		if(all_approved) {
        			stmt.setBoolean(j++, true);//locked - belum approved semua
        		}
        		else {
        			stmt.setBoolean(j++, false);//locked - belum approved semua	
        		}
        		
        		
        		stmt.setString(j++, approved_topik);
        		stmt.setLong(j++, Long.parseLong(id_topik));
        		stmt.executeUpdate();//update topik
        		//2. insert subtopik
        		j=1;
        		stmt = con.prepareStatement("insert into SUBTOPIK_PENGAJUAN(ID_TOPIK,ISI_SUBTOPIK,CREATOR_OBJ_ID,CREATOR_NPM,VERDICT)values(?,?,?,?,?)");
        		stmt.setLong(j++, Long.parseLong(id_topik));
        		stmt.setString(j++, approval_verdict);
        		stmt.setInt(j++, approvee_objid);
        		stmt.setString(j++, approvee_npm);
        		stmt.setString(j++, approval_verdict);
        		stmt.executeUpdate();
        		
        		if(all_approved) {
        			//update table overview
            		//TOT CUTI REQ = selain yg ditolak
            		//jadi kalo di tolak/batal TOT CUTI REQ - 1 
            		//TOT_CUTI_REQ_UNAPPROVED -1 krn kalo ditolak langsung ke lock = artinya sudah final verdict
            		if(creator_obj_id_topik==null || Checker.isStringNullOrEmpty(creator_obj_id_topik) || creator_obj_id_topik.equalsIgnoreCase("0")) {
            			creator_obj_id_topik = ""+Checker.getObjectId(creator_npm_topik);
            		}
            		stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
            		stmt.setLong(1, Long.parseLong(creator_obj_id_topik));
            		stmt.setString(2, thsms_pengajuan_topik);
            		rs = stmt.executeQuery();
            		rs.next();
            		int tot_req = rs.getInt("TOT_"+tipe_pengajuan_topik+"_REQ");
            		int tot_req_unapproved = rs.getInt("TOT_"+tipe_pengajuan_topik+"_REQ_UNAPPROVED");
            		String list_npm_unappproved = rs.getString("LIST_NPM_"+tipe_pengajuan_topik+"_UNAPPROVED");
            		list_npm_unappproved = list_npm_unappproved.replace(creator_npm_topik, "");
            		list_npm_unappproved = list_npm_unappproved.replace(",,", "");
            		if(list_npm_unappproved.startsWith(",")) {
            			list_npm_unappproved = list_npm_unappproved.substring(1, list_npm_unappproved.length());
            		}
            		if(list_npm_unappproved.endsWith(",")) {
            			list_npm_unappproved = list_npm_unappproved.substring(0, list_npm_unappproved.length()-1);
            		}
            		--tot_req_unapproved;
            		if(tot_req_unapproved<0) {
            			tot_req_unapproved=0;
            		}
            		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_"+tipe_pengajuan_topik+"_REQ=?,TOT_"+tipe_pengajuan_topik+"_REQ_UNAPPROVED=?,LIST_NPM_"+tipe_pengajuan_topik+"_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
            		stmt.setInt(1, tot_req);
            		stmt.setInt(2, tot_req_unapproved);
            		if(Checker.isStringNullOrEmpty(list_npm_unappproved)) {
            			stmt.setNull(3, java.sql.Types.VARCHAR);
            		}
            		else {
            			stmt.setString(3,list_npm_unappproved);
            		}
            		stmt.setLong(4, Long.parseLong(creator_obj_id_topik));
            		stmt.setString(5,thsms_pengajuan_topik);
        			stmt.executeUpdate();
        		}
        		//else {
        			
        		//}
        	}
        	else if(approval_verdict.contains("RESET")) {
        		//remove verdict dari sub topik
        		stmt = con.prepareStatement("delete from SUBTOPIK_PENGAJUAN where ID_TOPIK=? and CREATOR_OBJ_ID=?");
        		stmt.setLong(1, Long.parseLong(id_topik));
        		stmt.setInt(2, approvee_objid);
        		stmt.executeUpdate();
        		//update topik
        		//1.show at creator
        		//2.show at target & show at creator
        		//3.remove verdicr dari approved & rejected
        		
    			
        		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_TARGET=?,SHOW_AT_CREATOR=?,APPROVED=?,REJECTED=? where ID=?");
        		if(shwow_at_target_topik==null || Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        			shwow_at_target_topik = new String("["+approvee_objid+"]");
        		}
        		else if(!shwow_at_target_topik.contains("["+approvee_objid+"]")) {
        			shwow_at_target_topik = shwow_at_target_topik+"["+approvee_objid+"]";
        		}
        		stmt.setString(1, shwow_at_target_topik);
        		stmt.setBoolean(2, true);
        		if(approved_topik==null || Checker.isStringNullOrEmpty(approved_topik)) {
        			stmt.setNull(3, java.sql.Types.VARCHAR);
        		}
        		else {
        			approved_topik = approved_topik.replace("["+approvee_objid+"]", "");
        			if(Checker.isStringNullOrEmpty(approved_topik)) {
        				stmt.setNull(3, java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(3, approved_topik);
        			}
        			
        		}
        		if(rejected_topik==null || Checker.isStringNullOrEmpty(rejected_topik)) {
        			stmt.setNull(4, java.sql.Types.VARCHAR);
        		}
        		else {
        			rejected_topik = rejected_topik.replace("["+approvee_objid+"]", "");
        			if(Checker.isStringNullOrEmpty(rejected_topik)) {
        				stmt.setNull(4, java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(4, rejected_topik);
        			}
        			
        		}
        		stmt.setLong(5, Long.parseLong(id_topik));
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
    	return msg;
    }
    
    public String approvalPengajuan(String id_topik, int approvee_objid, String approvee_npm, String approval_verdict, String alasan, String full_table_rules_nm) {
    	int i=0; 
    	String msg = null;
    	String tipe_pengajuan = full_table_rules_nm.replace("_RULES", "");
    	String title_pengajuan = full_table_rules_nm.replace("_", " ");
    	try {
           	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where ID=?");
        	stmt.setLong(1, Long.parseLong(id_topik));
    		rs = stmt.executeQuery();
    		rs.next();
    		
			String thsms_pengajuan_topik=""+rs.getString("TARGET_THSMS_PENGAJUAN");
			String tipe_pengajuan_topik=""+rs.getString("TIPE_PENGAJUAN");
			String isi_topik_pengajuan_topik=""+rs.getString("ISI_TOPIK_PENGAJUAN");
			String tkn_target_objnickname_topik=""+rs.getString("TOKEN_TARGET_OBJ_NICKNAME");
			String tkn_target_objid_topik=""+rs.getString("TOKEN_TARGET_OBJID");
			
			String target_npm_topik=""+rs.getString("TARGET_NPM");
			String creator_obj_id_topik=""+rs.getLong("CREATOR_OBJ_ID");
			String creator_npm_topik=""+rs.getString("CREATOR_NPM");
			String creator_nmm_topik=""+rs.getString("CREATOR_NMM");
			String shwow_at_target_topik=""+rs.getString("SHOW_AT_TARGET");
			String show_at_creator_topik=""+rs.getBoolean("SHOW_AT_CREATOR");
			String updtm_topik=""+rs.getTimestamp("UPDTM");
			String approved_topik=""+rs.getString("APPROVED");
			//System.out.println("approved_topik="+approved_topik);
			String locked_topik=""+rs.getBoolean("LOCKED");
			String rejected_topik=""+rs.getString("REJECTED");
			
        	if(approval_verdict.contains("TOLAK")) { 
        		//System.out.println("ditolak");
        		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_TARGET=?,SHOW_AT_CREATOR=?,LOCKED=?,REJECTED=? where ID=?");
        		int j=1;
        		//kalo ditolak maka hide at target = SHOW_AT_TARGET set null, karena kalo udah ada yg cancel yg belum ngasih verdic ngga perlu liat
        		//hidden at target
        		stmt.setNull(j++,java.sql.Types.VARCHAR);
        		/*
        		if(shwow_at_target_topik==null || Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        			stmt.setNull(j++,java.sql.Types.VARCHAR);
        		}
        		else {
        			shwow_at_target_topik = shwow_at_target_topik.replace("["+approvee_objid+"]","");
        			if(Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        				stmt.setNull(j++,java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(j++,shwow_at_target_topik);	
        			}
        		}
        		*/
        		stmt.setBoolean(j++, true);
        		stmt.setBoolean(j++, true);//locked
        		//if contain aprooved objid disini
        		if(rejected_topik==null || Checker.isStringNullOrEmpty(rejected_topik)) {
        			rejected_topik = new String("["+approvee_objid+"]");
        		}
        		else if(!rejected_topik.contains("["+approvee_objid+"]")) {
        			rejected_topik = rejected_topik+"["+approvee_objid+"]";
        			
        		}
        		stmt.setString(j++, rejected_topik);
        		
        		
        		stmt.setLong(j++, Long.parseLong(id_topik));
        		stmt.executeUpdate();//update topik
        		//2. insert subtopik
        		j=1;
        		stmt = con.prepareStatement("insert into SUBTOPIK_PENGAJUAN(ID_TOPIK,ISI_SUBTOPIK,CREATOR_OBJ_ID,CREATOR_NPM,VERDICT)values(?,?,?,?,?)");
        		stmt.setLong(j++, Long.parseLong(id_topik));
        		stmt.setString(j++, alasan);
        		stmt.setInt(j++, approvee_objid);
        		stmt.setString(j++, approvee_npm);
        		stmt.setString(j++, approval_verdict);
        		stmt.executeUpdate();
        		//update table overview
        		//TOT CUTI REQ = selain yg ditolak
        		//jadi kalo di tolak/batal TOT CUTI REQ - 1 
        		//TOT_CUTI_REQ_UNAPPROVED -1 krn kalo ditolak langsung ke lock = artinya sudah final verdict
        		if(creator_obj_id_topik==null || Checker.isStringNullOrEmpty(creator_obj_id_topik) || creator_obj_id_topik.equalsIgnoreCase("0")) {
        			creator_obj_id_topik = ""+Checker.getObjectId(creator_npm_topik);
        		}
        		stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
        		stmt.setLong(1, Long.parseLong(creator_obj_id_topik));
        		stmt.setString(2, thsms_pengajuan_topik);
        		rs = stmt.executeQuery();
        		rs.next();
        		if(tipe_pengajuan.equalsIgnoreCase("PINDAH_PRODI")) {
        			stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where THSMS=? and LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED like ?");
        			stmt.setString(1, thsms_pengajuan_topik);
        			stmt.setString(2, "%"+creator_npm_topik+"-%");
        			rs = stmt.executeQuery();
        			Vector v = new Vector();
        			ListIterator li = v.listIterator();
        			while(rs.next()) {
        				String idobj = ""+rs.getInt("ID_OBJ");
        				int out = rs.getInt("TOT_NPM_"+tipe_pengajuan+"_OUT");
        				out=out-1;
        				int in = rs.getInt("TOT_NPM_"+tipe_pengajuan+"_IN");
        				in=in-1;
        				String list = rs.getString("LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED");
        				list = list.replace(creator_npm_topik+"-out", "");
        				list = list.replace(creator_npm_topik+"-in", "");
        				list = list.replace(",,", "");
        				if(list.endsWith(",")) {
        					list = list.substring(0, list.length()-1);
        				}
        				if(Checker.isStringNullOrEmpty(list)) {
        					list = "null";
        				}
        				li.add(idobj+"`"+out+"`"+in+"`"+list);
        			}
        			
        			
        			
            		
            		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_NPM_PINDAH_PRODI_OUT=?,TOT_NPM_PINDAH_PRODI_IN=?,LIST_NPM_PINDAH_PRODI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
            		li= v.listIterator();
            		while(li.hasNext()) {
            			String brs = (String)li.next();
            			StringTokenizer st = new StringTokenizer(brs,"`");
            			String idobj = st.nextToken();
            			String out = st.nextToken();
            			String in = st.nextToken();
            			String list = st.nextToken();
            			if(Checker.isStringNullOrEmpty(out)||Integer.parseInt(out)<1) {
            				stmt.setNull(1, java.sql.Types.INTEGER);	
            			}
            			else {
            				stmt.setInt(1, Integer.parseInt(out));
            			}
            			if(Checker.isStringNullOrEmpty(in)||Integer.parseInt(in)<1) {
            				stmt.setNull(2, java.sql.Types.INTEGER);	
            			}
            			else {
            				stmt.setInt(2, Integer.parseInt(in));
            			}
                		if(Checker.isStringNullOrEmpty(list)) {
                			stmt.setNull(3, java.sql.Types.VARCHAR);
                		}
                		else {
                			stmt.setString(3, list);
                		}
                		stmt.setInt(4,Integer.parseInt(idobj));
                		stmt.setString(5, thsms_pengajuan_topik);
                		stmt.executeUpdate();
                		
            		}
            		
        		}
        		else {
        			int tot_req = rs.getInt("TOT_"+tipe_pengajuan+"_REQ");
            		int tot_req_unapproved = rs.getInt("TOT_"+tipe_pengajuan+"_REQ_UNAPPROVED");
            		String list_npm_unappproved = rs.getString("LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED");
            		list_npm_unappproved = list_npm_unappproved.replace(creator_npm_topik, "");
            		list_npm_unappproved = list_npm_unappproved.replace(",,", "");
            		if(list_npm_unappproved.startsWith(",")) {
            			list_npm_unappproved = list_npm_unappproved.substring(1, list_npm_unappproved.length());
            		}
            		if(list_npm_unappproved.endsWith(",")) {
            			list_npm_unappproved = list_npm_unappproved.substring(0, list_npm_unappproved.length()-1);
            		}
            		
            		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_"+tipe_pengajuan+"_REQ=?,TOT_"+tipe_pengajuan+"_REQ_UNAPPROVED=?,LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
            		--tot_req;
            		if(tot_req<0) {
            			tot_req=0;
            		}
            		--tot_req_unapproved;
            		if(tot_req_unapproved<0) {
            			tot_req_unapproved=0;
            		}
            		stmt.setInt(1, tot_req);
            		stmt.setInt(2, tot_req_unapproved);
            		if(Checker.isStringNullOrEmpty(list_npm_unappproved)) {
            			stmt.setNull(3, java.sql.Types.VARCHAR);
            		}
            		else {
            			stmt.setString(3,list_npm_unappproved);
            		}
            		stmt.setLong(4, Long.parseLong(creator_obj_id_topik));
            		stmt.setString(5,thsms_pengajuan_topik);
        			stmt.executeUpdate();
        		}
        		
        	}
        	else if(approval_verdict.contains("TERIMA")) { 
        		//terima
        		//tkn_target_objid_topik = id approvees
        		//set approved topik value
        		
        		
        		//System.out.println("diterima");
        		//siapa yg sudah approved, bila current approvee id belum ada maka tambahkan
        		if(approved_topik==null || Checker.isStringNullOrEmpty(approved_topik)) { //id approved
        			approved_topik = new String("["+approvee_objid+"]");
        		}
        		else if(!approved_topik.contains("["+approvee_objid+"]")) {
        			approved_topik = approved_topik+"["+approvee_objid+"]";
        		}
        		
        		//cek sudah approved semua ato belum
        		//System.out.println("tkn_target_objid_topik="+tkn_target_objid_topik);
        		//System.out.println("approved_topik="+approved_topik);
        		boolean all_approved = AddHocFunction.isAllApproved(id_topik, tkn_target_objid_topik);
        		/*
        		String tkn_requird_approvee_id = new String(tkn_target_objid_topik);
        		tkn_requird_approvee_id = tkn_requird_approvee_id.replace("]", "`");
        		tkn_requird_approvee_id = tkn_requird_approvee_id.replace("[", "");
        		//System.out.println("tkn_target_objid_topik="+tkn_requird_approvee_id);
        		StringTokenizer st = new StringTokenizer(tkn_requird_approvee_id,"`");
        		boolean all_approved = true;
        		//System.out.println("approved_topik="+approved_topik);
        		if(st.hasMoreTokens()) {
        			while(st.hasMoreTokens() && all_approved) {
        				String required_id_approvee = st.nextToken();
        				//System.out.println("required_id_approvee="+required_id_approvee);
        				if(!approved_topik.contains("["+required_id_approvee+"]")) {
        					all_approved = false;
        				}
        			}
        		}
        		else {
        			all_approved = false;
        		}
        		*/
        		//System.out.println("all_approved="+all_approved);
        		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_TARGET=?,SHOW_AT_CREATOR=?,LOCKED=?,APPROVED=? where ID=?");
        		int j=1;
        		//hidden at target
        		if(shwow_at_target_topik==null || Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        			stmt.setNull(j++,java.sql.Types.VARCHAR);
        		}
        		else {
        			shwow_at_target_topik = shwow_at_target_topik.replace("["+approvee_objid+"]","");
        			if(Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        				stmt.setNull(j++,java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(j++,shwow_at_target_topik);	
        			}
        			
        		}
        		stmt.setBoolean(j++, true);
        		if(all_approved && !tipe_pengajuan.equalsIgnoreCase("PINDAH_PRODI")) {
        			//kalo pindah prodi butuh input npm baru untuk ock
        			stmt.setBoolean(j++, true);//locked - belum approved semua
        		}
        		else {
        			stmt.setBoolean(j++, false);//locked - belum approved semua	
        		}
        		
        		
        		stmt.setString(j++, approved_topik);
        		stmt.setLong(j++, Long.parseLong(id_topik));
        		stmt.executeUpdate();//update topik
        		//2. insert subtopik
        		j=1;
        		stmt = con.prepareStatement("insert into SUBTOPIK_PENGAJUAN(ID_TOPIK,ISI_SUBTOPIK,CREATOR_OBJ_ID,CREATOR_NPM,VERDICT)values(?,?,?,?,?)");
        		stmt.setLong(j++, Long.parseLong(id_topik));
        		stmt.setString(j++, approval_verdict);
        		stmt.setInt(j++, approvee_objid);
        		stmt.setString(j++, approvee_npm);
        		stmt.setString(j++, approval_verdict);
        		stmt.executeUpdate();
        		//System.out.println("all approved juga? "+all_approved);
        		if(all_approved) {
        			//update table overview bila all approved
            
            		if(creator_obj_id_topik==null || Checker.isStringNullOrEmpty(creator_obj_id_topik) || creator_obj_id_topik.equalsIgnoreCase("0")) {
            			creator_obj_id_topik = ""+Checker.getObjectId(creator_npm_topik);
            		}
            		
            		//if(tipe_pengajuan.equalsIgnoreCase("PINDAH_PRODI")||tipe_pengajuan.equalsIgnoreCase("AKTIF_KEMBALI")) {
            		if(tipe_pengajuan.equalsIgnoreCase("PINDAH_PRODI")) {
            		/*
            			 * PROSES DIBAWAH PINDAH KE BAWAH
            			 * else if(approval_verdict.contains("proses_npm_pp")) {
            			 */
            			/*
            			stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where THSMS=? and LIST_NPM_PINDAH_PRODI_UNAPPROVED like ? ");
                		
                		stmt.setString(1, thsms_pengajuan_topik);
                		stmt.setString(2, "%"+creator_npm_topik+"-%");
                		rs = stmt.executeQuery();
                		
                		String tmp ="";
                		while(rs.next()) {
                			String id  = ""+rs.getInt("ID_OBJ");
                			String list = ""+rs.getString("LIST_NPM_PINDAH_PRODI_UNAPPROVED");
                			list = list.replace(creator_npm_topik+"-out", "");
                			list = list.replace(creator_npm_topik+"-in", "");
                			list = list.replace(",,", ",");
                			if(list.endsWith(",")) {
                				list = list.substring(0,list.length()-1);
                			}
                			if(Checker.isStringNullOrEmpty(list)) {
                				list = "null";
                			}
                			tmp = tmp+id+"`"+list+"`";
                		}
                		
                		
                		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set LIST_NPM_PINDAH_PRODI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
                		StringTokenizer st = new StringTokenizer(tmp,"`");
                		while(st.hasMoreTokens()) {
                			String id = st.nextToken();
                			String list = st.nextToken();
                			stmt.setString(1,list);
                			stmt.setInt(2, Integer.parseInt(id));
                			stmt.setString(3, thsms_pengajuan_topik);
                			stmt.executeUpdate();
                		}
                		*/
            		}
            		else {
            			stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
                		stmt.setLong(1, Long.parseLong(creator_obj_id_topik));
                		stmt.setString(2, thsms_pengajuan_topik);
                		rs = stmt.executeQuery();
                		rs.next();
                		int tot_req = rs.getInt("TOT_"+tipe_pengajuan+"_REQ");
                		int tot_req_unapproved = rs.getInt("TOT_"+tipe_pengajuan+"_REQ_UNAPPROVED");
                		String list_npm_unappproved = rs.getString("LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED");
                		list_npm_unappproved = list_npm_unappproved.replace(creator_npm_topik, "");
                		list_npm_unappproved = list_npm_unappproved.replace(",,", "");
                		if(list_npm_unappproved.startsWith(",")) {
                			list_npm_unappproved = list_npm_unappproved.substring(1, list_npm_unappproved.length());
                		}
                		if(list_npm_unappproved.endsWith(",")) {
                			list_npm_unappproved = list_npm_unappproved.substring(0, list_npm_unappproved.length()-1);
                		}
                		--tot_req_unapproved;
                		if(tot_req_unapproved<0) {
                			tot_req_unapproved=0;
                		}
                		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_"+tipe_pengajuan+"_REQ=?,TOT_"+tipe_pengajuan+"_REQ_UNAPPROVED=?,LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
                		stmt.setInt(1, tot_req);
                		stmt.setInt(2, tot_req_unapproved);
                		if(Checker.isStringNullOrEmpty(list_npm_unappproved)) {
                			stmt.setNull(3, java.sql.Types.VARCHAR);
                		}
                		else {
                			stmt.setString(3,list_npm_unappproved);
                		}
                		stmt.setLong(4, Long.parseLong(creator_obj_id_topik));
                		stmt.setString(5,thsms_pengajuan_topik);
            			stmt.executeUpdate();
            		}
            		
        		}
        		//else {
        			
        		//}
        	}
        	else if(approval_verdict.contains("RESET")) {
        		//remove verdict dari sub topik
        		stmt = con.prepareStatement("delete from SUBTOPIK_PENGAJUAN where ID_TOPIK=? and CREATOR_OBJ_ID=?");
        		stmt.setLong(1, Long.parseLong(id_topik));
        		stmt.setInt(2, approvee_objid);
        		stmt.executeUpdate();
        		//update topik
        		//1.show at creator
        		//2.show at target & show at creator
        		//3.remove verdicr dari approved & rejected
        		
    			
        		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_TARGET=?,SHOW_AT_CREATOR=?,APPROVED=?,REJECTED=? where ID=?");
        		if(shwow_at_target_topik==null || Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        			shwow_at_target_topik = new String("["+approvee_objid+"]");
        		}
        		else if(!shwow_at_target_topik.contains("["+approvee_objid+"]")) {
        			shwow_at_target_topik = shwow_at_target_topik+"["+approvee_objid+"]";
        		}
        		stmt.setString(1, shwow_at_target_topik);
        		stmt.setBoolean(2, true);
        		if(approved_topik==null || Checker.isStringNullOrEmpty(approved_topik)) {
        			stmt.setNull(3, java.sql.Types.VARCHAR);
        		}
        		else {
        			approved_topik = approved_topik.replace("["+approvee_objid+"]", "");
        			//System.out.println("approved_topik1="+approved_topik);
        			if(Checker.isStringNullOrEmpty(approved_topik)) {
        				stmt.setNull(3, java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(3, approved_topik);
        			}
        			
        		}
        		if(rejected_topik==null || Checker.isStringNullOrEmpty(rejected_topik)) {
        			stmt.setNull(4, java.sql.Types.VARCHAR);
        		}
        		else {
        			rejected_topik = rejected_topik.replace("["+approvee_objid+"]", "");
        			if(Checker.isStringNullOrEmpty(rejected_topik)) {
        				stmt.setNull(4, java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(4, rejected_topik);
        			}
        			
        		}
        		stmt.setLong(5, Long.parseLong(id_topik));
        		stmt.executeUpdate();
        	}
        	else if(approval_verdict.contains("proses_npm_pp")) {
        		
        		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set LOCKED=? where ID=?");
        		int j=1;
        		/*
        		//hidden at target
        		if(shwow_at_target_topik==null || Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        			stmt.setNull(j++,java.sql.Types.VARCHAR);
        		}
        		else {
        			shwow_at_target_topik = shwow_at_target_topik.replace("["+approvee_objid+"]","");
        			if(Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        				stmt.setNull(j++,java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(j++,shwow_at_target_topik);	
        			}
        			
        		}
        		*/
     
        	
        		stmt.setBoolean(j++, true);//locked - 
        		stmt.setLong(j++, Long.parseLong(id_topik));
        		stmt.executeUpdate();//update topik
        		//2. insert subtopik
        		/*
        		j=1;
        		stmt = con.prepareStatement("insert into SUBTOPIK_PENGAJUAN(ID_TOPIK,ISI_SUBTOPIK,CREATOR_OBJ_ID,CREATOR_NPM,VERDICT)values(?,?,?,?,?)");
        		stmt.setLong(j++, Long.parseLong(id_topik));
        		stmt.setString(j++, approval_verdict);
        		stmt.setInt(j++, approvee_objid);
        		stmt.setString(j++, approvee_npm);
        		stmt.setString(j++, approval_verdict);
        		stmt.executeUpdate();
        		*/
    			stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where THSMS=? and LIST_NPM_PINDAH_PRODI_UNAPPROVED like ? ");
        		//System.out.println("thsms_pengajuan_topik="+thsms_pengajuan_topik);
        		//System.out.println("creator_npm_topik="+creator_npm_topik);
        		stmt.setString(1, thsms_pengajuan_topik);
        		stmt.setString(2, "%"+creator_npm_topik+"-%");
        		rs = stmt.executeQuery();
        		
        		String tmp ="";
        		while(rs.next()) {
        			String id  = ""+rs.getInt("ID_OBJ");
        			String list = ""+rs.getString("LIST_NPM_PINDAH_PRODI_UNAPPROVED");
        			list = list.replace(creator_npm_topik+"-out", "");
        			list = list.replace(creator_npm_topik+"-in", "");
        			list = list.replace(",,", ",");
        			if(list.endsWith(",")) {
        				list = list.substring(0,list.length()-1);
        			}
        			if(Checker.isStringNullOrEmpty(list)) {
        				list = "null";
        			}
        			tmp = tmp+id+"`"+list+"`";
        		}
        		
        		
        		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set LIST_NPM_PINDAH_PRODI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
        		StringTokenizer st = new StringTokenizer(tmp,"`");
        		while(st.hasMoreTokens()) {
        			String id = st.nextToken();
        			String list = st.nextToken();
        			if(list==null || Checker.isStringNullOrEmpty(list)) {
        				stmt.setNull(1, java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(1,list);	
        			}
        			
        			stmt.setInt(2, Integer.parseInt(id));
        			stmt.setString(3, thsms_pengajuan_topik);
        			stmt.executeUpdate();
        		}
        		
        	}
        	else if(approval_verdict.contains("pindah_at_smawl")) {
        		//HAPUS TOPIK, karena pindah di awal maka npm lama dihilangkan 
        		stmt = con.prepareStatement("delete from TOPIK_PENGAJUAN where ID=?");
        		int j=1;
        		stmt.setLong(j++, Long.parseLong(id_topik));
        		stmt.executeUpdate();//update topik
        		
        		
    			stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where THSMS=? and LIST_NPM_PINDAH_PRODI_UNAPPROVED like ? ");
        		//System.out.println("thsms_pengajuan_topik="+thsms_pengajuan_topik);
        		//System.out.println("creator_npm_topik="+creator_npm_topik);
        		stmt.setString(1, thsms_pengajuan_topik);
        		stmt.setString(2, "%"+creator_npm_topik+"-%");
        		rs = stmt.executeQuery();
        		
        		String tmp ="";
        		while(rs.next()) {
        			String id  = ""+rs.getInt("ID_OBJ");
        			String list = ""+rs.getString("LIST_NPM_PINDAH_PRODI_UNAPPROVED");
        			list = list.replace(creator_npm_topik+"-out", "");
        			list = list.replace(creator_npm_topik+"-in", "");
        			list = list.replace(",,", ",");
        			if(list.endsWith(",")) {
        				list = list.substring(0,list.length()-1);
        			}
        			if(Checker.isStringNullOrEmpty(list)) {
        				list = "null";
        			}
        			tmp = tmp+id+"`"+list+"`";
        		}
        		
        		
        		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set LIST_NPM_PINDAH_PRODI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
        		StringTokenizer st = new StringTokenizer(tmp,"`");
        		while(st.hasMoreTokens()) {
        			String id = st.nextToken();
        			String list = st.nextToken();
        			if(list==null || Checker.isStringNullOrEmpty(list)) {
        				stmt.setNull(1, java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(1,list);	
        			}
        			
        			stmt.setInt(2, Integer.parseInt(id));
        			stmt.setString(3, thsms_pengajuan_topik);
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
    	return msg;
    }
    

}

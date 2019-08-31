package beans.dbase.mhs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import beans.dbase.UpdateDb;
import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.Tool;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;
/**
 * Session Bean implementation class UpdateDbInfoMhs
 */
@Stateless
@LocalBean
public class UpdateDbInfoMhs extends UpdateDb {
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
     * @see UpdateDb#UpdateDb()
     */
    public UpdateDbInfoMhs() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbInfoMhs(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.tknOperatorNickname = getTknOprNickname();
    	//System.out.println("tknOperatorNickname1="+this.tknOperatorNickname);
    	this.petugas = cekApaUsrPetugas();
        // TODO Auto-generated constructor stub
    }
    
    public void updateTabelDaftarUlang(String getListMhsTrnlm, String thsms) {
    	Vector v = new Vector();;
    	ListIterator li = v.listIterator();
    	//istThsmsNpmhs =listThsmsNpmhs+"$"+kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stmhs;
    	StringTokenizer st = new StringTokenizer(getListMhsTrnlm,"$");
    	String needByGetProfile = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	select bila ada remove
    		//stmt = con.prepareStatement("select * from DAFTAR_ULANG where KDPST=? and NPMHS=?");
    		stmt = con.prepareStatement("select * from DAFTAR_ULANG where THSMS=? and KDPST=? and NPMHS=?");
    		while(st.hasMoreTokens()) {
    			String kdpst = st.nextToken();
    			String npmhs = st.nextToken();
    			String nimhs = st.nextToken();
    			String nmmhs = st.nextToken();
    			String smawl = st.nextToken();
    			String stmhs = st.nextToken();
    			stmt.setString(1, thsms);
    			stmt.setString(2, kdpst);
    			stmt.setString(3, npmhs);
    			rs = stmt.executeQuery();
    			if(!rs.next()) {
    				li.add(kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stmhs);
    			}
    		}
    		
    		//insert ke daftar ulang
    		
    		stmt = con.prepareStatement("insert into DAFTAR_ULANG(THSMS,KDPST,NPMHS)values(?,?,?)");
    		li = v.listIterator();
    		String list_npm_inserted = "";
    		while(li.hasNext()) {
    			
    			String brs = (String)li.next();
    			st = new StringTokenizer(brs,"$");
    			String kdpst = st.nextToken();
    			String npmhs = st.nextToken();
    			String nimhs = st.nextToken();
    			String nmmhs = st.nextToken();
    			String smawl = st.nextToken();
    			String stmhs = st.nextToken();
    			stmt.setString(1, thsms);
    			stmt.setString(2, kdpst);
    			stmt.setString(3, npmhs);
    			stmt.executeUpdate();
    			list_npm_inserted = list_npm_inserted+"`"+npmhs+"`"+kdpst;
    		}
    		
    		//kalo ada yg diinsert == update Notification
    		if(!Checker.isStringNullOrEmpty(list_npm_inserted)) { 
    			//update notification
    			st = new StringTokenizer(list_npm_inserted,"`");
    			list_npm_inserted = new String();
    			stmt = con.prepareStatement("select ID_OBJ from CIVITAS where NPMHSMSMHS=?");
    			while(st.hasMoreTokens()) {
    				String npmhs = st.nextToken();
    				String kdpst = st.nextToken();
    				stmt.setString(1, npmhs);
    				rs = stmt.executeQuery();
    				rs.next();
    				long idobj = rs.getLong(1); 
    				list_npm_inserted = list_npm_inserted+npmhs+"`"+kdpst+"`"+idobj;
    				if(st.hasMoreTokens()) {
    					list_npm_inserted = list_npm_inserted+"`";
    				}
    			}
    			String list_need_insert = "";
    			String list_need_update = "";
    			if(!Checker.isStringNullOrEmpty(list_npm_inserted)) { 
    				st = new StringTokenizer(list_npm_inserted,"`");
        			stmt = con.prepareStatement("select * from DAFTAR_ULANG_NOTIFICATION where THSMS=? and ID_OBJ=?");
        			while(st.hasMoreTokens()) {
        				String npmhs = st.nextToken();
        				String kdpst = st.nextToken();
        				String idobj = st.nextToken();
        				stmt.setString(1, thsms);
        				stmt.setInt(2, Integer.parseInt(idobj));
        				rs = stmt.executeQuery();
        				if(rs.next()) {
        					String list_npm_wip = rs.getString("LIST_NPM_INPROGRESS");
        					boolean ada_pengajuan = rs.getBoolean("ADA_PENGAJUAN_INPROGRESS");
        					if(list_npm_wip==null) {
        						list_npm_wip = new String(npmhs);
        						list_need_update = list_need_update+"`"+npmhs+"`"+kdpst+"`"+idobj+"`"+list_npm_wip;
        					}
        					else if(!list_npm_wip.contains(npmhs)){
        						if(Checker.isStringNullOrEmpty(list_npm_wip)) {
        							list_npm_wip = new String(npmhs);
        							list_need_update = list_need_update+"`"+npmhs+"`"+kdpst+"`"+idobj+"`"+list_npm_wip;
        						}
        						else {
        							list_npm_wip = list_npm_wip+","+npmhs;
        							list_need_update = list_need_update+"`"+npmhs+"`"+kdpst+"`"+idobj+"`"+list_npm_wip;
        						}
        					}
        					else {
        						if(!ada_pengajuan) { //update juga set ada pengajun = true
        							list_need_update = list_need_update+"`"+npmhs+"`"+kdpst+"`"+idobj+"`"+list_npm_wip;
        						}
        					}
        				}
        				else {
        					list_need_insert = list_need_insert+"`"+npmhs+"`"+kdpst+"`"+idobj;
        				}
        			}	
        			
        			//update part
        			if(!Checker.isStringNullOrEmpty(list_need_update)) {
        				st = new StringTokenizer(list_need_update,"`");
        				stmt = con.prepareStatement("update DAFTAR_ULANG_NOTIFICATION set ADA_PENGAJUAN_INPROGRESS=?,LIST_NPM_INPROGRESS=? where THSMS=? and ID_OBJ=?");
        				while(st.hasMoreTokens()) {
        					String npmhs = st.nextToken();
            				String kdpst = st.nextToken();
            				String idobj = st.nextToken();
            				String list_npm_wip = st.nextToken();	
            				stmt.setBoolean(1, true);
            				stmt.setString(2, list_npm_wip);
            				stmt.setString(3, thsms);
            				stmt.setLong(4, Long.parseLong(idobj));
            				stmt.executeUpdate();
        				}
        				
        				
        			}
        			//insert part
        			if(!Checker.isStringNullOrEmpty(list_need_insert)) {
        				st = new StringTokenizer(list_need_update,"`");
        				stmt = con.prepareStatement("insert into DAFTAR_ULANG_NOTIFICATION(THSMS,ID_OBJ,ADA_PENGAJUAN_INPROGRESS,LIST_NPM_INPROGRESS)values(?,?,?,?)");
        				while(st.hasMoreTokens()) {
        					String npmhs = st.nextToken();
            				String kdpst = st.nextToken();
            				String idobj = st.nextToken();
            				
            				stmt.setString(1, thsms);
            				stmt.setLong(2, Long.parseLong(idobj));
            				stmt.setBoolean(3, true);
            				stmt.setString(4, npmhs);
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
    	//return needByGetProfile;
    }
    
    public int setNimEqualsNpm(String starting_smawl) {
    	int updated = 0;
    	
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("UPDATE CIVITAS set NIMHSMSMHS=NPMHSMSMHS where SMAWLMSMHS>=?");
    		stmt.setString(1, starting_smawl);
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
    
    
    
    /*
     * DEPRECATED
     * PAKE V1
     */
    public void updateDaftarUlangTableByOperator(String kdpst, String npmhs) {
    	long objid = Checker.getObjectId(npmhs);
    	String thsms_herregistrasi=Checker.getThsmsHeregistrasi();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from DAFTAR_ULANG_RULES where THSMS=? and KDPST=?");
    		stmt.setString(1, thsms_herregistrasi);
    		stmt.setString(2, kdpst);
    		rs = stmt.executeQuery();
    		rs.next();
    		String tkn_ver = rs.getString("TKN_VERIFICATOR");
    		StringTokenizer st = new StringTokenizer(tkn_ver,",");
    		String verificator_match_nickname = null;
    		boolean match = false;
    		while(st.hasMoreTokens() && !match) {
    			String tmp_nickname = st.nextToken();
    			if(this.tknOperatorNickname.contains(tmp_nickname)) {
    				verificator_match_nickname = ""+tmp_nickname;
    				match = true;
    			}
    		}
    		if(!match) {
    			verificator_match_nickname = Constants.getDefaultNicknameUntukHeregistrasiByOperator();
    		}
    		stmt = con.prepareStatement("select * from DAFTAR_ULANG where THSMS=? and KDPST=? and NPMHS=?");
    		stmt.setString(1, thsms_herregistrasi);
    		stmt.setString(2, kdpst);
    		stmt.setString(3, npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			//sudah ada yg insert dan approve
    			String tgl_pengajuan = ""+rs.getTimestamp("TGL_PENGAJUAN");
    			String tkn_apr = ""+rs.getString("TOKEN_APPROVAL");
    			if(!tkn_apr.contains(verificator_match_nickname)) {
    				//artinya anda belum pernah approve--proses lanjutkan
    				stmt = con.prepareStatement("UPDATE DAFTAR_ULANG SET TOKEN_APPROVAL=? where THSMS=? and KDPST=? and NPMHS=?,");
    				if(tkn_apr!=null && !Checker.isStringNullOrEmpty(tkn_apr)) {
    					stmt.setString(1, "#"+this.operatorNpm+"#"+verificator_match_nickname+"#"+AskSystem.getCurrentTimestamp());
    				}
    				else {
    					stmt.setString(1, this.operatorNpm+"#"+verificator_match_nickname+"#"+AskSystem.getCurrentTimestamp());
    				}
    				stmt.setString(2, thsms_herregistrasi);
    				stmt.setString(3, kdpst);
    				stmt.setString(4, npmhs);
    				stmt.executeUpdate();
    			}
    		}
    		else {
    			//belum pernah ajukan jadi insert
    			stmt = con.prepareStatement("INSERT INTO DAFTAR_ULANG (THSMS,KDPST,NPMHS,TGL_PENGAJUAN,TOKEN_APPROVAL,ID_OBJ)values(?,?,?,?,?,?)");
    			stmt.setString(1,thsms_herregistrasi );
    			stmt.setString(2, kdpst);
    			stmt.setString(3, npmhs);
    			stmt.setTimestamp(4, AskSystem.getCurrentTimestamp());
    			stmt.setString(5, this.operatorNpm+"#"+verificator_match_nickname+"#"+AskSystem.getCurrentTimestamp());
    			stmt.setLong(6, objid);
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
    
    //public void updateDaftarUlangTableByOperator_v1(String kdpst, String npmhs, String target_thsms, Vector v_scope_id) {
    public void updateDaftarUlangTableByOperator_v1(String kdpst, String npmhs, String target_thsms) {
    	/*
    	 * fungsi ini hanya dipakai saat pengajuan awal
    	 */
    	long objid = Checker.getObjectId(npmhs);
    	String kmp_dom = Getter.getDomisiliKampus(npmhs);
    	//String thsms_herregistrasi=Checker.getThsmsHeregistrasi();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from DAFTAR_ULANG where THSMS=? and KDPST=? and NPMHS=?");
    		stmt.setString(1, target_thsms);
    		stmt.setString(2, kdpst);
    		stmt.setString(3, npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			/*
    			//fungsi ini hanya dipakai saat pengajuan awal, jadi ngga mungkin ada
    			*/
    		}
    		else {
    			//get approvee rule
    			
    			stmt = con.prepareStatement("select TKN_JABATAN_VERIFICATOR,TKN_VERIFICATOR_ID from HEREGISTRASI_RULES where THSMS=? and KDPST=? and KODE_KAMPUS=?");
    			stmt.setString(1, target_thsms);
    			stmt.setString(2, kdpst);	
    			stmt.setString(3, kmp_dom);	
    			rs = stmt.executeQuery();
    			rs.next();
    			String tkn_jab = rs.getString(1);
    			String tkn_id = rs.getString(2);
    			
    			//belum pernah ajukan jadi insert
    			stmt = con.prepareStatement("INSERT INTO DAFTAR_ULANG (THSMS,KDPST,NPMHS,TGL_PENGAJUAN,ID_OBJ,TKN_ID_APPROVAL,TKN_JABATAN_APPROVAL,SHOW_AT_ID)values(?,?,?,?,?,?,?,?)");
    			stmt.setString(1,target_thsms );
    			stmt.setString(2, kdpst);
    			stmt.setString(3, npmhs);
    			stmt.setTimestamp(4, AskSystem.getCurrentTimestamp());
    			//stmt.setString(5, this.operatorNpm+"#"+verificator_match_nickname+"#"+AskSystem.getCurrentTimestamp());
    			stmt.setLong(5, objid);
    			stmt.setString(6,tkn_id );
    			stmt.setString(7,tkn_jab );
    			stmt.setString(8,tkn_id );
    			stmt.executeUpdate();
    			
    			//cek apa ini mhs baru yg melakukan pendaftaran (mhs baru & bukan mhs aktif kembali)
    			
    			int tot_noshow = 0;
    			String list_npm_no_show = null;
    			
    			stmt = con.prepareStatement("select TOT_NO_SHOW,LIST_NPM_NO_SHOW from OVERVIEW_SEBARAN_TRLSM where THSMS=? and ID_OBJ=?");
    			stmt.setString(1, target_thsms);
    			stmt.setLong(2, objid);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				
    				tot_noshow = rs.getInt(1);
    				list_npm_no_show = new String(""+rs.getString(2));
    				
    				if(!Checker.isStringNullOrEmpty(list_npm_no_show)) {
    					list_npm_no_show = ","+list_npm_no_show+",";
    					if(list_npm_no_show.contains(","+npmhs+",")) {
    						list_npm_no_show = list_npm_no_show.substring(1,list_npm_no_show.length()-1);
    						//karena sudah mengajukan dafatar ulang maka hapus dari list no shw
    						list_npm_no_show = list_npm_no_show.replace(npmhs, "");
    						list_npm_no_show = list_npm_no_show.replace(",,", "");
    						if(list_npm_no_show.startsWith(",")) {
    							list_npm_no_show = list_npm_no_show.substring(1,list_npm_no_show.length());
    						}
    						if(list_npm_no_show.endsWith(",")) {
    							list_npm_no_show = list_npm_no_show.substring(0,list_npm_no_show.length()-1);
    						}
    						tot_noshow--;
    						
    						//update table overciew
    						stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_NO_SHOW=?,LIST_NPM_NO_SHOW=? where ID_OBJ=? and THSMS=?");
    	    				stmt.setInt(1, tot_noshow);
    	    				if(list_npm_no_show==null || Checker.isStringNullOrEmpty(list_npm_no_show)) {
    	    					stmt.setNull(2, java.sql.Types.VARCHAR);
    	    				}
    	    				else {
    	    					stmt.setString(2, list_npm_no_show);
    	    				}
    	    				stmt.setLong(3,objid);
    	    				stmt.setString(4, target_thsms);
    	    				stmt.executeUpdate();
    						
        				}
    				}
    				
    			}
    			else {
    				//ignore bukan mhs baru
    			}
    			
    			//update table overview
    			stmt = con.prepareStatement("select TOT_DAFTAR_ULANG_REQ,TOT_DAFTAR_ULANG_REQ_UNAPPROVED,LIST_NPM_DAFTAR_ULANG_UNAPPROVED from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
    			stmt.setLong(1, objid);
    			stmt.setString(2, target_thsms);
    			rs = stmt.executeQuery();
    			rs.next();
    			int tot_req = rs.getInt(1);
    			int tot_unapproved = rs.getInt(2);
    			String list_npm = rs.getString(3);
    			if(list_npm==null || Checker.isStringNullOrEmpty(list_npm) || !list_npm.contains(npmhs)) {
    				//update
    				stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_DAFTAR_ULANG_REQ=?,TOT_DAFTAR_ULANG_REQ_UNAPPROVED=?,LIST_NPM_DAFTAR_ULANG_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
    				stmt.setInt(1, ++tot_req);
    				stmt.setInt(2, ++tot_unapproved);
    				if(list_npm==null || Checker.isStringNullOrEmpty(list_npm)) {
    					list_npm = new String(npmhs);
    				}
    				else {
    					list_npm = list_npm+","+npmhs;
    				}
    				stmt.setString(3, list_npm);
    				stmt.setLong(4,objid);
    				stmt.setString(5, target_thsms);
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
    }
    
    public void updateCekListTabelDaftarUlang(String[]lisNpmUsrIdUsrNic, String thsms_registrasi) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	select bila ada remove
    		stmt = con.prepareStatement("select * from DAFTAR_ULANG where THSMS=? and KDPST=? and NPMHS=?");
    		for(int i=0;i<lisNpmUsrIdUsrNic.length;i++) {
    			//kdpst+"#"+npmhs+"#"+validUsr.getNpm()+"#"+nickMatch 
    			StringTokenizer st = new StringTokenizer(lisNpmUsrIdUsrNic[i],"#");
    			//System.out.println("lisNpmUsrIdUsrNic[i]="+lisNpmUsrIdUsrNic[i]);
    			String kdpst = st.nextToken();
    			String npmhs = st.nextToken();
    			String npmOperator = st.nextToken();
    			String nickname = st.nextToken();
    			stmt.setString(1, thsms_registrasi);
    			stmt.setString(2, kdpst);
    			stmt.setString(3, npmhs);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				String tknApr= rs.getString("TOKEN_APPROVAL");
    				if(tknApr==null || !tknApr.contains(nickname)) {
    					li.add(lisNpmUsrIdUsrNic[i]);
    				}
    			}
    		}
    		
    		stmt = con.prepareStatement("update DAFTAR_ULANG set TOKEN_APPROVAL=concat(ifnull(TOKEN_APPROVAL,?),?) where THSMS=? and KDPST=? and NPMHS=?");
    		li = v.listIterator();
    		while(li.hasNext()){
    			String brs = (String)li.next();
    			//System.out.println("brs"+brs);
    			StringTokenizer st = new StringTokenizer(brs,"#");
    			String kdpst = st.nextToken();
    			String npmhs = st.nextToken();
    			String npmOperator = st.nextToken();
    			String nickname = st.nextToken();
    			String valueIfNull = "";
    			String valueNotNull = "#"+npmOperator+"#"+nickname+"#"+AskSystem.getCurrentTimestamp();
    			stmt.setString(1, valueIfNull);
    			stmt.setString(2, valueNotNull);
    			stmt.setString(3, thsms_registrasi);
    			stmt.setString(4, kdpst);
    			stmt.setString(5, npmhs);
    			stmt.executeUpdate();
    		}
    		//cek all approved
    		li = v.listIterator();
    		while(li.hasNext()){
    			String brs = (String)li.next();
    			//System.out.println("brs"+brs);
    			StringTokenizer st = new StringTokenizer(brs,"#");
    			String kdpst = st.nextToken();
    			String npmhs = st.nextToken();
    			String psn = Checker.sudahDaftarUlang(kdpst, npmhs, thsms_registrasi);
    			if(psn!=null) {//remove stil in progress
    				li.remove();
    			}
    		}
    		if(v!=null && v.size()>0) {
    			li = v.listIterator();
    			stmt = con.prepareStatement("update DAFTAR_ULANG set ALL_APPROVED=? where THSMS=? and NPMHS=?");
        		while(li.hasNext()){
        			String brs = (String)li.next();
        			//System.out.println("brs"+brs);
        			StringTokenizer st = new StringTokenizer(brs,"#");
        			String kdpst = st.nextToken();
        			String npmhs = st.nextToken();
        			stmt.setBoolean(1, true);
        			stmt.setString(2, thsms_registrasi);
        			stmt.setString(3, npmhs);
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
    	//return needByGetProfile;
    }
    
    
    
    public void updateStatusDaftarUlangTable(String thsms_registrasi, String npmhs, String opr_npm, String jabatan, String id_jabatan, String tkn_jab_apr_needed) {
    	
    	
    	
    	long objid = Checker.getObjectId(npmhs);
    	String kmp_dom = Getter.getDomisiliKampus(npmhs);
    	boolean all_approved = false;
    	boolean show_at_mhs = true; //setiap update - always show at mhs
    	tkn_jab_apr_needed = tkn_jab_apr_needed.replace("][", "`");
    	tkn_jab_apr_needed = tkn_jab_apr_needed.replace("[", "");
    	tkn_jab_apr_needed = tkn_jab_apr_needed.replace("]", "");
    	StringTokenizer st = new StringTokenizer(tkn_jab_apr_needed,"`");
    	String last_jab_approvee_needed = "";
    	while(st.hasMoreTokens()) {
    		last_jab_approvee_needed = st.nextToken();
    	}
    	if(jabatan!=null && !Checker.isStringNullOrEmpty(jabatan)) {
    		//ngga boleh ada bracket
    		jabatan = jabatan.replace("[", "");
    		jabatan = jabatan.replace("]", "");
    	}
    	//System.out.println(jabatan +" vs "+last_jab_approvee_needed);
    	if(jabatan.equalsIgnoreCase(last_jab_approvee_needed)) {
    		all_approved = true;
    	}
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cek apa sudah pernah approval
    		boolean sudah_approved = false;
        	stmt = con.prepareStatement("select TOKEN_APPROVAL from DAFTAR_ULANG where THSMS=? and NPMHS=?");
    		stmt.setString(1, thsms_registrasi);
    		stmt.setString(2, npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String tkn_approval = ""+rs.getString(1);
    			if(tkn_approval.contains(jabatan)) {
    				sudah_approved = true;
    			}
    		}
    		//	select bila ada remove
    		/*
    		 * COLOMN SHOW_AT_ID = tidak jadi digunakan, karena otomatis hilang bila all_approved
    		 */
    		if(!sudah_approved) {
    			stmt = con.prepareStatement("update DAFTAR_ULANG set TOKEN_APPROVAL=concat(ifnull(TOKEN_APPROVAL,?),?),ID_OBJ=?,ALL_APPROVED=?,SHOW_AT_CREATOR=? where THSMS=? and NPMHS=?");
        		String valueIfNull = "";
    			String valueNotNull = "#"+opr_npm+"#"+jabatan+"#"+AskSystem.getCurrentTimestamp();
    			stmt.setString(1, valueIfNull);
    			stmt.setString(2, valueNotNull);
    			stmt.setLong(3, objid);
    			stmt.setBoolean(4, all_approved);
    			stmt.setBoolean(5, show_at_mhs);
    			stmt.setString(6, thsms_registrasi);
    			stmt.setString(7, npmhs);
    			stmt.executeUpdate();  
    			if(all_approved) {
    				//update table overview
    				stmt = con.prepareStatement("select TOT_DAFTAR_ULANG_REQ,TOT_DAFTAR_ULANG_REQ_UNAPPROVED,LIST_NPM_DAFTAR_ULANG_UNAPPROVED from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
        			stmt.setLong(1, objid);
        			stmt.setString(2, thsms_registrasi);
        			rs = stmt.executeQuery();
        			rs.next();
        			int tot_req = rs.getInt(1);
        			int tot_unapproved = rs.getInt(2);
        			String list_npm = rs.getString(3);
        			list_npm = list_npm.replace(npmhs, "");
        			list_npm = list_npm.replace(",,", ",");
        			if(list_npm.startsWith(",")) {
        				list_npm = list_npm.substring(1, list_npm.length());
        			}
        			if(list_npm.endsWith(",")) {
        				list_npm = list_npm.substring(0, list_npm.length()-1);
        			}
        			
        			//update
        			stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_DAFTAR_ULANG_REQ=?,TOT_DAFTAR_ULANG_REQ_UNAPPROVED=?,LIST_NPM_DAFTAR_ULANG_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
        			stmt.setInt(1, tot_req);//total request tetep
        			stmt.setInt(2, --tot_unapproved);//unapproved -1
        			if(list_npm==null || Checker.isStringNullOrEmpty(list_npm)) {
        				stmt.setNull(3, java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(3, list_npm);
        			}
        			
        			stmt.setLong(4,objid);
        			stmt.setString(5, thsms_registrasi);
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
    	//return needByGetProfile;
    }  
    
    
    public void updateStatusDaftarUlangTable_v1(String tkn_jabatan_approvee,String tkn_id_approvee,String[]info_approval, String opr_npm) {
    	StringTokenizer st = null, st1=null;
    	boolean all_approved = false;
    	String tkn_show_at_id = null;
    	String waiting_for_whom_id=null;
    	String thsms_registrasi = Checker.getThsmsHeregistrasi();
    	Vector v_all_approved = new Vector();
    	ListIterator lia =v_all_approved.listIterator();
    	if(info_approval!=null) {
    		try {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("update DAFTAR_ULANG set TOKEN_APPROVAL=concat(ifnull(TOKEN_APPROVAL,?),?),ID_OBJ=?,ALL_APPROVED=?,SHOW_AT_CREATOR=? where THSMS=? and NPMHS=?");
        		
    			
        		for(int i=0;i<info_approval.length;i++) {
        			//<%=target_idobj %>~<%=npmhs %>~<%=job %>~<%=wait_for_whom %>~<%=my_objid %>
        			st = new StringTokenizer(info_approval[i],"~");
        			String target_idobj = st.nextToken();
        			String target_npmhs = st.nextToken();
        			String target_my_jabatan = st.nextToken();
        			String waiting_for_whom = st.nextToken();
        			String my_idobj = st.nextToken();
        			String valueIfNull = "";
        			String valueNotNull = "#"+opr_npm+"#"+target_my_jabatan+"#"+AskSystem.getCurrentTimestamp();
        			
        			st1 = new StringTokenizer(waiting_for_whom,"`");
        			waiting_for_whom = "`";
        			while(st1.hasMoreTokens()) {
        				String remaining = st1.nextToken();
        				if(!remaining.equalsIgnoreCase(target_my_jabatan)) {
        					waiting_for_whom = waiting_for_whom+remaining;
        				}
        			}
        			waiting_for_whom=waiting_for_whom+"`";
        			while(waiting_for_whom.contains("``")) {
        				waiting_for_whom=waiting_for_whom.replace("``", "");
        			}
        			if(Checker.isStringNullOrEmpty(waiting_for_whom)) {
        				all_approved = true;
        				lia.add(target_idobj+"~"+target_npmhs);
        			}
        			else {
        				if(waiting_for_whom.startsWith("`")) {
            				waiting_for_whom = waiting_for_whom.substring(1,waiting_for_whom.length());
            			}
            			if(waiting_for_whom.endsWith("`")) {
            				waiting_for_whom = waiting_for_whom.substring(0,waiting_for_whom.length()-1);
            			}
            			//cari waiting_for_whom_id
            			st1 = new StringTokenizer(waiting_for_whom,"`");
            			while(st1.hasMoreTokens()) {
            				//tkn_jabatan_approvee,String tkn_id_approvee
            				String remaining_job = st1.nextToken();
            				waiting_for_whom_id = new String(tkn_id_approvee.substring(1, tkn_id_approvee.length()-1));
            				String tmp_tkn = new String(tkn_jabatan_approvee.substring(1, tkn_jabatan_approvee.length()-1));
            				StringTokenizer stj = new StringTokenizer(tmp_tkn,"][");
            				StringTokenizer stid = new StringTokenizer(waiting_for_whom_id,"][");
            				waiting_for_whom_id="`";
            				while(stj.hasMoreTokens()) {
            					String jab = stj.nextToken();
            					String id = stid.nextToken();
            					if(jab.equalsIgnoreCase(remaining_job)) {
            						waiting_for_whom_id=waiting_for_whom_id+id+"`";
            					}
            				}
            				System.out.println("waiting_for_whom_id="+waiting_for_whom_id);
            				if(!Checker.isStringNullOrEmpty(waiting_for_whom_id)&&!waiting_for_whom_id.equalsIgnoreCase("`")) {
            					waiting_for_whom_id = "["+waiting_for_whom_id.substring(1, waiting_for_whom_id.length()-1)+"]";
            					while(waiting_for_whom_id.contains("`")) {
            						waiting_for_whom_id=waiting_for_whom_id.replace("`", "][");
            					}
            				}
            			}
            			while(waiting_for_whom.contains("`")) {
            				waiting_for_whom=waiting_for_whom.replace("`", "][");
            			}
            			waiting_for_whom = "["+waiting_for_whom+"]";
        			}
        			
        			boolean show_at_mhs = true;
        			stmt.setString(1, valueIfNull);
        			stmt.setString(2, valueNotNull);
        			stmt.setLong(3, Integer.parseInt(target_idobj));
        			stmt.setBoolean(4, all_approved);
        			stmt.setBoolean(5, show_at_mhs);
        			stmt.setString(6, thsms_registrasi);
        			stmt.setString(7, target_npmhs);
        			int upd = stmt.executeUpdate(); 
        			//System.out.println("waiting_for_whom="+waiting_for_whom);
        			//System.out.println("waiting_for_whom_id="+waiting_for_whom_id);
        		}
        		
        		
        		
        		int tot_approved = v_all_approved.size();
        		lia =v_all_approved.listIterator();
        		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_DAFTAR_ULANG_REQ=?,TOT_DAFTAR_ULANG_REQ_UNAPPROVED=?,LIST_NPM_DAFTAR_ULANG_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
        		if(lia.hasNext()) {
        			String brs = (String)lia.next();
        			StringTokenizer st2 = new StringTokenizer(brs,"~");
        			String target_idobj = st2.nextToken();
        			String target_npmhs = st2.nextToken();
        			
        			stmt = con.prepareStatement("select TOT_DAFTAR_ULANG_REQ,TOT_DAFTAR_ULANG_REQ_UNAPPROVED,LIST_NPM_DAFTAR_ULANG_UNAPPROVED from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
        			stmt.setLong(1, Integer.parseInt(target_idobj));
        			stmt.setString(2, thsms_registrasi);
        			rs = stmt.executeQuery();
        			rs.next();
        			int tot_req = rs.getInt(1);
        			int tot_unapproved = rs.getInt(2);
        			String list_npm = rs.getString(3);
        			tot_unapproved = tot_unapproved-tot_approved;
        			if(tot_unapproved<0) {
        				tot_unapproved=0;
        			}
        			
        			
        			lia =v_all_approved.listIterator();
        			while(lia.hasNext()) {
        				brs = (String)lia.next();
            			st2 = new StringTokenizer(brs,"~");
            			target_idobj = st2.nextToken();
            			target_npmhs = st2.nextToken();
            			
            			list_npm = list_npm.replace(target_npmhs, "");
            			list_npm = list_npm.replace(",,", ",");
            			if(list_npm.startsWith(",")) {
            				list_npm = list_npm.substring(1, list_npm.length());
            			}
            			if(list_npm.endsWith(",")) {
            				list_npm = list_npm.substring(0, list_npm.length()-1);
            			}
        			}
        			
        			stmt.setInt(1, tot_req);//total request tetep
        			stmt.setInt(2, tot_unapproved);//unapproved -1
        			if(list_npm==null || Checker.isStringNullOrEmpty(list_npm)) {
        				stmt.setNull(3, java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(3, list_npm);
        			}
        			
        			stmt.setLong(4,Integer.parseInt(target_idobj));
        			stmt.setString(5, thsms_registrasi);
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
    	/*
    	
    	boolean show_at_mhs = true; //setiap update - always show at mhs
    	tkn_jab_apr_needed = tkn_jab_apr_needed.replace("][", "`");
    	tkn_jab_apr_needed = tkn_jab_apr_needed.replace("[", "");
    	tkn_jab_apr_needed = tkn_jab_apr_needed.replace("]", "");
    	StringTokenizer st = new StringTokenizer(tkn_jab_apr_needed,"`");
    	String last_jab_approvee_needed = "";
    	while(st.hasMoreTokens()) {
    		last_jab_approvee_needed = st.nextToken();
    	}
    	if(jabatan!=null && !Checker.isStringNullOrEmpty(jabatan)) {
    		//ngga boleh ada bracket
    		jabatan = jabatan.replace("[", "");
    		jabatan = jabatan.replace("]", "");
    	}
    	//System.out.println(jabatan +" vs "+last_jab_approvee_needed);
    	if(jabatan.equalsIgnoreCase(last_jab_approvee_needed)) {
    		all_approved = true;
    	}
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cek apa sudah pernah approval
    		boolean sudah_approved = false;
        	stmt = con.prepareStatement("select TOKEN_APPROVAL from DAFTAR_ULANG where THSMS=? and NPMHS=?");
    		stmt.setString(1, thsms_registrasi);
    		stmt.setString(2, npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String tkn_approval = ""+rs.getString(1);
    			if(tkn_approval.contains(jabatan)) {
    				sudah_approved = true;
    			}
    		}
    		//	select bila ada remove
    		
    		 * COLOMN SHOW_AT_ID = tidak jadi digunakan, karena otomatis hilang bila all_approved
    		
    		if(!sudah_approved) {
    			stmt = con.prepareStatement("update DAFTAR_ULANG set TOKEN_APPROVAL=concat(ifnull(TOKEN_APPROVAL,?),?),ID_OBJ=?,ALL_APPROVED=?,SHOW_AT_CREATOR=? where THSMS=? and NPMHS=?");
        		String valueIfNull = "";
    			String valueNotNull = "#"+opr_npm+"#"+jabatan+"#"+AskSystem.getCurrentTimestamp();
    			stmt.setString(1, valueIfNull);
    			stmt.setString(2, valueNotNull);
    			stmt.setLong(3, objid);
    			stmt.setBoolean(4, all_approved);
    			stmt.setBoolean(5, show_at_mhs);
    			stmt.setString(6, thsms_registrasi);
    			stmt.setString(7, npmhs);
    			stmt.executeUpdate();  
    			if(all_approved) {
    				//update table overview
    				stmt = con.prepareStatement("select TOT_DAFTAR_ULANG_REQ,TOT_DAFTAR_ULANG_REQ_UNAPPROVED,LIST_NPM_DAFTAR_ULANG_UNAPPROVED from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
        			stmt.setLong(1, objid);
        			stmt.setString(2, thsms_registrasi);
        			rs = stmt.executeQuery();
        			rs.next();
        			int tot_req = rs.getInt(1);
        			int tot_unapproved = rs.getInt(2);
        			String list_npm = rs.getString(3);
        			list_npm = list_npm.replace(npmhs, "");
        			list_npm = list_npm.replace(",,", ",");
        			if(list_npm.startsWith(",")) {
        				list_npm = list_npm.substring(1, list_npm.length());
        			}
        			if(list_npm.endsWith(",")) {
        				list_npm = list_npm.substring(0, list_npm.length()-1);
        			}
        			
        			//update
        			stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_DAFTAR_ULANG_REQ=?,TOT_DAFTAR_ULANG_REQ_UNAPPROVED=?,LIST_NPM_DAFTAR_ULANG_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
        			stmt.setInt(1, tot_req);//total request tetep
        			stmt.setInt(2, --tot_unapproved);//unapproved -1
        			if(list_npm==null || Checker.isStringNullOrEmpty(list_npm)) {
        				stmt.setNull(3, java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(3, list_npm);
        			}
        			
        			stmt.setLong(4,objid);
        			stmt.setString(5, thsms_registrasi);
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
    	//return needByGetProfile;
    }  
    
    public void setNullCekListTabelDaftarUlang(String[]lisNpmUsrIdUsrNic, String thsms_registrasi) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	select bila ada remove only objek usr saat ini
    		//stmt = con.prepareStatement("update DAFTAR_ULANG set TOKEN_APPROVAL=? where THSMS=?");
    		//stmt.setNull(1, java.sql.Types.VARCHAR);
    		//stmt.setString(2, thsms_registrasi);
    		//stmt.executeUpdate();
    		String nuVal="";
    		//1. proces yg di uncheck
    		stmt = con.prepareStatement("select * from DAFTAR_ULANG WHERE THSMS=?");
    		stmt.setString(1, thsms_registrasi);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdpst0 = rs.getString("KDPST");
    			String npmhs0 = rs.getString("NPMHS");
    			String tknApr0 = rs.getString("TOKEN_APPROVAL");
    			boolean match = false;
    			if(lisNpmUsrIdUsrNic!=null && lisNpmUsrIdUsrNic.length>0) {
    				for(int i=0;i<lisNpmUsrIdUsrNic.length  && !match;i++) {
        			//kdpst+"#"+npmhs+"#"+validUsr.getNpm()+"#"+nickMatch 
    					StringTokenizer st = new StringTokenizer(lisNpmUsrIdUsrNic[i],"#");
        			//System.out.println("lisNpmUsrIdUsrNic[i]="+lisNpmUsrIdUsrNic[i]);
    					while(st.hasMoreTokens()) {
    						String kdpst = st.nextToken();
    						String npmhs = st.nextToken();
    						String npmOperator = st.nextToken();
    						String nickname = st.nextToken();
    						if(npmhs.equalsIgnoreCase(npmhs0)) {
    							match = true;
    						}
    					}
        			
    				}	
    				if(!match) {
    					li.add(kdpst0+"$"+npmhs0+"$"+tknApr0);
    				}
    			}
    			else{
    				li.add(kdpst0+"$"+npmhs0+"$"+tknApr0);
    			}
    		}
    		
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"$");
    			String kdpst = st.nextToken();
    			String npmhs = st.nextToken();
    			String tknApr = st.nextToken();
    			if(tknApr!=null && !Checker.isStringNullOrEmpty(tknApr)) {
					StringTokenizer stmp = new StringTokenizer(tknApr,"#");
					boolean first = true;
					while(stmp.hasMoreTokens()) {
						String oprNpm = stmp.nextToken();
						String oprNick = stmp.nextToken();
						String tmstm = stmp.nextToken();
						//System.out.print("tknOperatorNickname=");
						//System.out.println(this.tknOperatorNickname);
						//System.out.println("oprNick="+oprNick);
						if(!this.tknOperatorNickname.contains(oprNick)) {
							if(first) {
								first = false;
								nuVal=oprNpm+"#"+oprNick+"#"+tmstm;
							}
							else {
								nuVal=nuVal+"#"+oprNpm+"#"+oprNick+"#"+tmstm;
							}
						}	
					}
				}
				else {
					nuVal=tknApr;
				}
				if(Checker.isStringNullOrEmpty(nuVal)) {
					nuVal="null";
				}
				li.set(kdpst+"$"+npmhs+"$"+nuVal);
    		}
    		
    		
    		stmt = con.prepareStatement("update DAFTAR_ULANG set TOKEN_APPROVAL=? where THSMS=?and KDPST=? and NPMHS=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"$");
    			String kdpst = st.nextToken();
    			String npmhs = st.nextToken();
    			String nuValTknApr = st.nextToken();
    			//System.out.println("nuValTknApr="+nuValTknApr);
    			if(Checker.isStringNullOrEmpty(nuValTknApr)) {
    				stmt.setNull(1,java.sql.Types.VARCHAR);
    			}
    			else {
    				stmt.setString(1,nuValTknApr);
    			}	
    			stmt.setString(2,thsms_registrasi);
    			stmt.setString(3,kdpst);
    			stmt.setString(4,npmhs);
    			stmt.executeUpdate();
    		}
    		
    		//2.process yg di cek
    		v = new Vector();
    		stmt=con.prepareStatement("select * from DAFTAR_ULANG where THSMS=? and KDPST=? and NPMHS=?");
    		if(lisNpmUsrIdUsrNic!=null && lisNpmUsrIdUsrNic.length>0) {
    			for(int i=0;i<lisNpmUsrIdUsrNic.length;i++) {
    			//kdpst+"#"+npmhs+"#"+validUsr.getNpm()+"#"+nickMatch 
    				StringTokenizer st = new StringTokenizer(lisNpmUsrIdUsrNic[i],"#");
    				//System.out.println("lisNpmUsrIdUsrNic[i]="+lisNpmUsrIdUsrNic[i]);
    				boolean first = true;
    				while(st.hasMoreTokens()) {
    					String kdpst = st.nextToken();
    					String npmhs = st.nextToken();
    					String npmOperator = st.nextToken();
    					String nickname = st.nextToken();
    			
    					stmt.setString(1, thsms_registrasi);
    					stmt.setString(2, kdpst);
    					stmt.setString(3, npmhs);
    					//System.out.println(thsms_registrasi+","+kdpst+","+npmhs);
    					rs = stmt.executeQuery();
    					//System.out.println("query");
    					rs.next();
    					String tknApr=rs.getString("TOKEN_APPROVAL");
    					//System.out.println("tknApr="+tknApr);
    					if(tknApr!=null && !Checker.isStringNullOrEmpty(tknApr) && tknApr.contains(nickname)) {
    						StringTokenizer stmp = new StringTokenizer(tknApr,"#");
    						while(stmp.hasMoreTokens()) {
    							String oprNpm = stmp.nextToken();
    							String oprNick = stmp.nextToken();
    							String tmstm = stmp.nextToken();
    							//System.out.print("tknOperatorNickname=");
    							
    							//System.out.println(this.tknOperatorNickname);
    							//System.out.println("oprNick="+oprNick);
    							if(!this.tknOperatorNickname.contains(oprNick)) {
    								if(first) {
    									first = false;
    									nuVal=oprNpm+"#"+oprNick+"#"+tmstm;
    								}
    								else {
    									nuVal=nuVal+"#"+oprNpm+"#"+oprNick+"#"+tmstm;
    								}
    							}	
    						}
    					}
    					else {
    						nuVal=tknApr;
    					}
    					if(Checker.isStringNullOrEmpty(nuVal)) {
    						nuVal="null";
    					}
    					li.add(kdpst+"$"+npmhs+"$"+npmOperator+"$"+nickname+"$"+nuVal);
    					//System.out.println("add="+kdpst+"$"+npmhs+"$"+npmOperator+"$"+nickname+"$"+nuVal);
    				}
    			}
    		}	
    		//else {
    			
    		//}
    		
    		stmt = con.prepareStatement("update DAFTAR_ULANG set TOKEN_APPROVAL=? where THSMS=?and KDPST=? and NPMHS=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();//System.out.println("brs2="+brs);
    			StringTokenizer st = new StringTokenizer(brs,"$");
    			String kdpst = st.nextToken();
    			String npmhs = st.nextToken();
    			String npmOperator = st.nextToken();
    			String nickname = st.nextToken();
    			String nuValTknApr = st.nextToken();
    			//System.out.println("nuValTknApr="+nuValTknApr);
    			if(Checker.isStringNullOrEmpty(nuValTknApr)) {
    				stmt.setNull(1,java.sql.Types.VARCHAR);
    			}
    			else {
    				stmt.setString(1,nuValTknApr);
    			}	
    			stmt.setString(2,thsms_registrasi);
    			stmt.setString(3,kdpst);
    			stmt.setString(4,npmhs);
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
    	//return needByGetProfile;
    }
    

	
    public int updateStatusKartuUjian(String thsms,String kdpst, String npmhs, String targetUjian, String nuTknKartuUjianValue, 	String nuTknApprKartuUjianValue, String nuTknStatusValue) {
    	int upd=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	select bila ada remove
    		stmt = con.prepareStatement("update DAFTAR_ULANG set TOKEN_KARTU_UJIAN=?,TOKEN_APPROVAL_KARTU_UJIAN=?,STATUS_APPROVAL_KARTU_UJIAN=? where THSMS=? and KDPST=? and NPMHS=?");
    		stmt.setString(1, nuTknKartuUjianValue);
    		stmt.setString(2, nuTknApprKartuUjianValue);
    		stmt.setString(3, nuTknStatusValue);
    		stmt.setString(4, thsms);
    		stmt.setString(5, kdpst);
    		stmt.setString(6, npmhs);
    		//System.out.println(thsms+","+kdpst+","+npmhs+","+targetUjian+","+nuTknKartuUjianValue+","+nuTknApprKartuUjianValue+","+nuTknStatusValue);
    		upd = stmt.executeUpdate();
    		//System.out.println("updated upd = "+upd);
    		
    		//stmt.executeUpdate();
    		//con.commit();
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
    	return upd;
    }
    
    public void pindahAngkatan(String npm_lama, String smawl_baru) {
    	String kdpst = Checker.getKdpst(npm_lama);
    	//long idobj = Checker.getObjectId(npm_lama);
    	String nu_npm = this.generateNpm(smawl_baru, kdpst);
    	//System.out.println("nu_npm="+nu_npm+" "+smawl_baru);
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//1.upd CIVITAS
    		stmt = con.prepareStatement("update CIVITAS set NPMHSMSMHS=?,TAHUNMSMHS=?,SMAWLMSMHS=? where NPMHSMSMHS=?");
    		stmt.setString(1, nu_npm);
    		stmt.setString(2, smawl_baru.substring(0,4));
    		stmt.setString(3, smawl_baru);
    		stmt.setString(4, npm_lama);
    		stmt.executeUpdate();
    		//1.ECT_CIVITAS
    		stmt = con.prepareStatement("update EXT_CIVITAS set NPMHSMSMHS=? where NPMHSMSMHS=?");
    		stmt.setString(1, nu_npm);
    		stmt.setString(2, npm_lama);
    		stmt.executeUpdate();
    		//2.KRS_NOTIFICATION
    		stmt = con.prepareStatement("update KRS_NOTIFICATION set NPM_SENDER=? where NPM_SENDER=?");
    		stmt.setString(1, nu_npm);
    		stmt.setString(2, npm_lama);
    		stmt.executeUpdate();
    		//2.KRS_NOTIFICATION
    		stmt = con.prepareStatement("update KRS_NOTIFICATION set NPM_RECEIVER=? where NPM_RECEIVER=?");
    		stmt.setString(1, nu_npm);
    		stmt.setString(2, npm_lama);
    		stmt.executeUpdate();
    		//3.TOPIK
    		stmt = con.prepareStatement("update TOPIK set NPMHSCREATOR=? where NPMHSCREATOR=?");
    		stmt.setString(1, nu_npm);
    		stmt.setString(2, npm_lama);
    		stmt.executeUpdate();
    		//3.TOPIK_PENGAJUAN
    		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set CREATOR_NPM=? where CREATOR_NPM=?");
    		stmt.setString(1, nu_npm);
    		stmt.setString(2, npm_lama);
    		stmt.executeUpdate();
    		//3.SUBTOPIK
    		stmt = con.prepareStatement("update SUBTOPIK set NPMHSSENDER=? where NPMHSSENDER=?");
    		stmt.setString(1, nu_npm);
    		stmt.setString(2, npm_lama);
    		stmt.executeUpdate();
    		//3.SUBTOPIK
    		stmt = con.prepareStatement("update SUBTOPIK set NPMHSRECEIVER=? where NPMHSRECEIVER=?");
    		stmt.setString(1, nu_npm);
    		stmt.setString(2, npm_lama);
    		stmt.executeUpdate();
    		//3.SUBTOPIK_PENGAJUAN
    		stmt = con.prepareStatement("update SUBTOPIK_PENGAJUAN set CREATOR_NPM=? where CREATOR_NPM=?");
    		stmt.setString(1, nu_npm);
    		stmt.setString(2, npm_lama);
    		stmt.executeUpdate();
    		//3.SURVEY_DOSEN
    		stmt = con.prepareStatement("update SURVEY_DOSEN set NPMHS=? where NPMHS=?");
    		stmt.setString(1, nu_npm);
    		stmt.setString(2, npm_lama);
    		stmt.executeUpdate();
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
    
    public Vector mergeDataCivitas(String npmYgMoDiMerge, String npmTujuanMerge) {
    	Vector v = new Vector();
    	//System.out.println("npmYgMoDiMerge="+npmYgMoDiMerge);
    	//System.out.println("npmTujuanMerge="+npmTujuanMerge);
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select KDPSTMSMHS,NMMHSMSMHS from CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1, npmTujuanMerge);
    		rs = stmt.executeQuery();
    		rs.next();
    		String kdpstTujuanMerge = rs.getString(1);
    		String nmmhsTujuanMerge = rs.getString(2);
    		
    		//get nimhs di npmlama
    		stmt = con.prepareStatement("select NIMHSMSMHS from CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1, npmYgMoDiMerge);
    		rs = stmt.executeQuery();
    		rs.next();
    		String nimhsAtNpmLama = rs.getString(1);
    		
    		if(!Checker.isStringNullOrEmpty(nimhsAtNpmLama)) {
    			stmt = con.prepareStatement("update CIVITAS set NIMHSMSMHS=? where NPMHSMSMHS=?");
        		stmt.setString(1, nimhsAtNpmLama);
        		stmt.setString(2, npmTujuanMerge);
        		stmt.executeUpdate();	
    		}
    		
    		
    		//UPDATE TABLE KRS NOTIFICATION
    		stmt = con.prepareStatement("update KRS_NOTIFICATION set NPM_RECEIVER=?,NMM_RECEIVER=? where NPM_RECEIVER=?");
    		stmt.setString(1, npmTujuanMerge);
    		stmt.setString(2, nmmhsTujuanMerge);
    		stmt.setString(3, npmYgMoDiMerge);
    		stmt.executeUpdate();
    		
    		stmt = con.prepareStatement("update KRS_NOTIFICATION set NPM_SENDER=?,NMM_SENDER=? where NPM_SENDER=?");
    		stmt.setString(1, npmTujuanMerge);
    		stmt.setString(2, nmmhsTujuanMerge);
    		stmt.setString(3, npmYgMoDiMerge);
    		stmt.executeUpdate();
    		
    		
    		//update pa
    		stmt = con.prepareStatement("update EXT_CIVITAS set NPM_PA=?,NMM_PA=? where NPM_PA=?");
    		stmt.setString(1, npmTujuanMerge);
    		stmt.setString(2, nmmhsTujuanMerge);
    		stmt.setString(3, npmYgMoDiMerge);
    		stmt.executeUpdate();
    		//	get data dari npmYgMoDiMerge
    		/*1. get info dari tabel civitas -- ngga jadi tidak dibutuuhkan
    		
    		stmt = con.prepareStatement("select * from CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1, npmYgMoDiMerge);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String id = ""+rs.getInt("ID");
    			String id_obj = ""+rs.getInt("ID_OBJ");
    			String kdpti = ""+rs.getString("KDPTIMSMHS");
    			String kdjen = ""+rs.getString("KDJENMSMHS");
    			String kdpst = ""+rs.getString("KDPSTMSMHS");
    			String npmhs = ""+rs.getString("NPMHSMSMHS");
    			String nimhs = ""+rs.getString("NIMHSMSMHS");
    			String nmmhs = ""+rs.getString("NMMHSMSMHS");
    			String shift = ""+rs.getString("SHIFTMSMHS");
    			String tplhr = ""+rs.getString("TPLHRMSMHS");
    			String tglhr = ""+rs.getDate("TGLHRMSMHS");
    			String kdjek = ""+rs.getString("KDJEKMSMHS");
    			String tahun = ""+rs.getString("TAHUNMSMHS");
    			String smawl = ""+rs.getString("SMAWLMSMHS");
    			String btstu = ""+rs.getString("BTSTUMSMHS");
    			String assma = ""+rs.getString("ASSMAMSMHS");
    			String tgmsk = ""+rs.getDate("TGMSKMSMHS");
    			String tglls = ""+rs.getDate("TGLLSMSMHS");
    			String stmhs = ""+rs.getString("STMHSMSMHS");
    			String stpid = ""+rs.getString("STPIDMSMHS");
    			String sksdi = ""+rs.getFloat("SKSDIMSMHS");
    			String asnim = ""+rs.getString("ASNIMMSMHS");
    			String aspti = ""+rs.getString("ASPTIMSMHS");
    			String asjen = ""+rs.getString("ASJENMSMHS");
    			String aspst = ""+rs.getString("ASPSTMSMHS");
    			String bistu = ""+rs.getString("BISTUMSMHS");
    			String peksb = ""+rs.getString("PEKSBMSMHS");
    			String nmpek = ""+rs.getString("NMPEKMSMHS");
    			String ptpek = ""+rs.getString("PTPEKMSMHS");
    			String pspek = ""+rs.getString("PSPEKMSMHS");
    			String noprm = ""+rs.getString("NOPRMMSMHS");
    			String nokp1 = ""+rs.getString("NOKP1MSMHS");
    			String nokp2 = ""+rs.getString("NOKP2MSMHS");
    			String nokp3 = ""+rs.getString("NOKP3MSMHS");
    			String nokp4 = ""+rs.getString("NOKP4MSMHS");
    			String updtm = ""+rs.getTimestamp("UPDTMMSMHS");
    			String gelom = ""+rs.getString("GELOMMSMHS");
    			
    			li.add(id+"`"+id_obj+"`"+kdpti+"`"+kdjen+"`"+kdpst+"`"+npmhs+"`"+nimhs+"`"+nmmhs+"`"+shift+"`"+tplhr+"`"+tglhr+"`"+kdjek+"`"+tahun+"`"+smawl+"`"+btstu+"`"+assma+"`"+tgmsk+"`"+tglls+"`"+stmhs+"`"+stpid+"`"+sksdi+"`"+asnim+"`"+aspti+"`"+asjen+"`"+aspst+"`"+bistu+"`"+peksb+"`"+nmpek+"`"+ptpek+"`"+pspek+"`"+noprm+"`"+nokp1+"`"+nokp2+"`"+nokp3+"`"+nokp4+"`"+updtm+"`"+gelom);
    		
    		}
    		else {
    			li.add("null");
    		}
    		
    		//1.tabel EXT_CIVITAS -- ngga jadi
    		stmt = con.prepareStatement("select * from EXT_CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1, npmYgMoDiMerge);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String kdpst = ""+rs.getString("KDPSTMSMHS");
        	    String npmhs = ""+rs.getString("NPMHSMSMHS");
        	    String sttus = ""+rs.getString("STTUSMSMHS");
        	    String email = ""+rs.getString("EMAILMSMHS");
        	    String nohape = ""+rs.getString("NOHPEMSMHS");
        	    String almrm = ""+rs.getString("ALMRMMSMHS");
        	    String kotrm = ""+rs.getString("KOTRMMSMHS");
        	    String posrm = ""+rs.getString("POSRMMSMHS");
        	    String telrm = ""+rs.getString("TELRMMSMHS");
        	    String almkt = ""+rs.getString("ALMKTMSMHS");
        	    String kotkt = ""+rs.getString("KOTKTMSMHS");
        	    String poskt = ""+rs.getString("POSKTMSMHS");
        	    String telkt = ""+rs.getString("TELKTMSMHS");
        	    String jbtkt = ""+rs.getString("JBTKTMSMHS");
        	    String bidkt = ""+rs.getString("BIDKTMSMHS");
        	    String jenkt = ""+rs.getString("JENKTMSMHS");
        	    String nmmsp = ""+rs.getString("NMMSPMSMHS");
        	    String almsp = ""+rs.getString("ALMSPMSMHS");
        	    String possp = ""+rs.getString("POSSPMSMHS");
        	    String kotsp = ""+rs.getString("KOTSPMSMHS");
        	    String negsp = ""+rs.getString("NEGSPMSMHS");
        	    String telsp = ""+rs.getString("TELSPMSMHS");
        	    String neglh = ""+rs.getString("NEGLHMSMHS");
        	    String agama = ""+rs.getString("AGAMAMSMHS");
        	    String krklm = ""+rs.getString("KRKLMMSMHS");
        	    String ttlog = ""+rs.getInt("TTLOGMSMHS");
        	    String tmlog = ""+rs.getInt("TMLOGMSMHS");
        	    String dtlog = ""+rs.getTimestamp("DTLOGMSMHS");
        	    String updtm = ""+rs.getTimestamp("UPDTMMSMHS");
        	    String idpaketbea = ""+rs.getInt("IDPAKETBEASISWA");
        	    String npmpa = ""+rs.getString("NPM_PA");
        	    String nmmpa = ""+rs.getString("NMM_PA");
        	    String petugas = ""+rs.getBoolean("PETUGAS");
        	    String assma = ""+rs.getString("ASAL_SMA");
        	    String kotsma = ""+rs.getString("KOTA_SMA");
        	    String thLulusSma = ""+rs.getString("LULUS_SMA");
        	    li.add(kdpst+"`"+npmhs+"`"+sttus+"`"+email+"`"+nohape+"`"+almrm+"`"+kotrm+"`"+posrm+"`"+telrm+"`"+almkt+"`"+kotkt+"`"+poskt+"`"+telkt+"`"+jbtkt+"`"+bidkt+"`"+jenkt+"`"+nmmsp+"`"+almsp+"`"+possp+"`"+kotsp+"`"+negsp+"`"+telsp+"`"+neglh+"`"+agama+"`"+krklm+"`"+ttlog+"`"+tmlog+"`"+dtlog+"`"+updtm+"`"+idpaketbea+"`"+npmpa+"`"+nmmpa+"`"+petugas+"`"+assma+"`"+kotsma+"`"+thLulusSma);
    		}
    		else {
    			li.add("null");
    		}
    		*/
    		//1.tabel EXT_CIVITAS_DATA_DOSEM
    		stmt = con.prepareStatement("select * from EXT_CIVITAS_DATA_DOSEN where NPMHS=?");
    		stmt.setString(1, npmYgMoDiMerge);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String kdpst = ""+rs.getString("KDPST");
    			if(Checker.isStringNullOrEmpty(kdpst)) {
    				kdpst="null";
        	    }
        	    String npmhs = ""+rs.getString("NPMHS");
        	    String nodos_loco = ""+rs.getString("NODOS_LOCAL");
        	    String gelardp = ""+rs.getString("GELAR_DEPAN");
        	    String gelarbl = ""+rs.getString("GELAR_BELAKANG");
        	    String nidnn = ""+rs.getString("NIDNN");
        	    String tipeid = ""+rs.getString("TIPE_ID");
        	    String nomid = ""+rs.getString("NOMOR_ID");
        	    String status = ""+rs.getString("STATUS");
        	    String pt1 = ""+rs.getString("PT_S1");
        	    String jur1 = ""+rs.getString("JURUSAN_S1");
        	    String kdpst1 = ""+rs.getString("KDPST_S1");
        	    String gelar1 = ""+rs.getString("GELAR_S1");
        	    String bidil1 = ""+rs.getString("TKN_BIDANG_KEAHLIAN_S1");
        	    String noija1 = ""+rs.getString("NOIJA_S1");
        	    String tglls1 = ""+rs.getDate("TGLLS_S1");
        	    String fileija1 = ""+rs.getString("FILE_IJA_S1");
        	    String judul1 = ""+rs.getString("JUDUL_TA_S1");
        	    String pt2 = ""+rs.getString("PT_S2");
        	    String jur2 = ""+rs.getString("JURUSAN_S2");
        	    String kdpst2 = ""+rs.getString("KDPST_S2");
        	    String gelar2 = ""+rs.getString("GELAR_S2");
        	    String bidil2 = ""+rs.getString("TKN_BIDANG_KEAHLIAN_S2");
        	    String noija2 = ""+rs.getString("NOIJA_S2");
        	    String tglls2 = ""+rs.getDate("TGLLS_S2");
        	    String fileija2 = ""+rs.getString("FILE_IJA_S2");
        	    String judul2 = ""+rs.getString("JUDUL_TA_S2");
        	    String pt3 = ""+rs.getString("PT_S3");
        	    String jur3 = ""+rs.getString("JURUSAN_S3");
        	    String kdpst3 = ""+rs.getString("KDPST_S3");
        	    String gelar3 = ""+rs.getString("GELAR_S3");
        	    String bidil3 = ""+rs.getString("TKN_BIDANG_KEAHLIAN_S3");
        	    String noija3 = ""+rs.getString("NOIJA_S3");
        	    String tglls3 = ""+rs.getDate("TGLLS_S3");
        	    String fileija3 = ""+rs.getString("FILE_IJA_S3");
        	    String judul3 = ""+rs.getString("JUDUL_TA_S3");
        	    String pt4 = ""+rs.getString("PT_PROF");
        	    String jur4 = ""+rs.getString("JURUSAN_PROF");
        	    String kdpst4 = ""+rs.getString("KDPST_PROF");
        	    String gelar4 = ""+rs.getString("GELAR_PROF");
        	    String bidil4 = ""+rs.getString("TKN_BIDANG_KEAHLIAN_PROF");
        	    String noija4 = ""+rs.getString("NOIJA_PROF");
        	    String tglls4 = ""+rs.getDate("TGLLS_PROF");
        	    String fileija4 = ""+rs.getString("FILE_IJA_PROF");
        	    String judul4 = ""+rs.getString("JUDUL_TA_PROF");
        	    
        	    String ttkum = ""+rs.getInt("TOTAL_KUM");
        	    String jabak = ""+rs.getString("JABATAN_AKADEMIK_DIKTI");
        	    String jabak_loco = ""+rs.getString("JABATAN_AKADEMIK_LOCAL");
        	    String jabstruk = ""+rs.getString("JABATAN_STRUKTURAL");
        	    String ika = ""+rs.getString("IKATAN_KERJA_DOSEN");
        	    String tgmsk = ""+rs.getDate("TANGGAL_MULAI_KERJA");
        	    String tgklr = ""+rs.getDate("TANGGAL_KELUAR_KERJA");
        	    String serdos = ""+rs.getString("SERDOS");
        	    String kdpti_homebase = ""+rs.getString("KDPTI_HOMEBASE");
        	    String kdpst_homebase = ""+rs.getString("KDPST_HOMEBASE");
        	    String email_loco = ""+rs.getString("EMAIL_INSTITUSI");
        	    String pangkat = ""+rs.getString("PANGKAT_GOLONGAN");
        	    String note_history = ""+rs.getString("CATATAN_RIWAYAT");
        	    String tipe_id = ""+rs.getString("TIPE_KTP");
        	    String no_id = ""+rs.getString("NO_KTP");
        	    if(Checker.isStringNullOrEmpty(no_id)) {
        	    	no_id="null";
        	    }
        	    String tmp =kdpst+"`"+npmhs+"`"+nodos_loco+"`"+gelardp+"`"+gelarbl+"`"+nidnn+"`"+tipeid+"`"+nomid+"`"+status+"`"+pt1+"`"+jur1+"`"+kdpst1+"`"+gelar1+"`"+bidil1+"`"+noija1+"`"+tglls1+"`"+fileija1+"`"+judul1+"`"+pt2+"`"+jur2+"`"+kdpst2+"`"+gelar2+"`"+bidil2+"`"+noija2+"`"+tglls2+"`"+fileija2+"`"+judul2+"`"+pt3+"`"+jur3+"`"+kdpst3+"`"+gelar3+"`"+bidil3+"`"+noija3+"`"+tglls3+"`"+fileija3+"`"+judul3+"`"+pt4+"`"+jur4+"`"+kdpst4+"`"+gelar4+"`"+bidil4+"`"+noija4+"`"+tglls4+"`"+fileija4+"`"+judul4+"`"+ttkum+"`"+jabak+"`"+jabak_loco+"`"+jabstruk+"`"+ika+"`"+tgmsk+"`"+tgklr+"`"+serdos+"`"+kdpti_homebase+"`"+kdpst_homebase+"`"+email_loco+"`"+pangkat+"`"+note_history+"`"+tipe_id+"`"+no_id;
        	    tmp = tmp.replace("``", "`null`");
        	    li.add(tmp);
    		}
    		else {
    			li.add("null");
    		}

    		
    		//2.tabel TRNLM
    		stmt = con.prepareStatement("select * from TRNLM where NPMHSTRNLM=?");
    		stmt.setString(1, npmYgMoDiMerge);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String brs = "";
    			do {
    				String thsms = ""+rs.getString("THSMSTRNLM");
    				if(Checker.isStringNullOrEmpty(thsms)) {
    					thsms="null";
            	    }
        			String kdpti = ""+rs.getString("KDPTITRNLM");
        			String kdjen = ""+rs.getString("KDJENTRNLM");
        			String kdpst = ""+rs.getString("KDPSTTRNLM");
        			String npmhs = ""+rs.getString("NPMHSTRNLM");
        			String kdkmk = ""+rs.getString("KDKMKTRNLM");
        			String nilai = ""+rs.getFloat("NILAITRNLM");
        			String nlakh = ""+rs.getString("NLAKHTRNLM");
        			String bobot = ""+rs.getFloat("BOBOTTRNLM");
        			String skskmk = ""+rs.getInt("SKSMKTRNLM");
        			String kelas = ""+rs.getString("KELASTRNLM");
        			String updtm = ""+rs.getString("UPDTMTRNLM");
        			String krsdown = ""+rs.getBoolean("KRSDONWLOADED");
        			String khsdown = ""+rs.getBoolean("KHSDONWLOADED");
        			String bak_apr = ""+rs.getBoolean("BAK_APPROVAL");
        			String shift_kls = ""+rs.getString("SHIFTTRNLM");
        			String pa_apr = ""+rs.getBoolean("PA_APPROVAL");
        			String note = ""+rs.getString("NOTE");
        			String lock = ""+rs.getBoolean("LOCKTRNLM");
        			String bauk_apr = ""+rs.getBoolean("BAUK_APPROVAL");
        			String idkmk = ""+rs.getInt("IDKMKTRNLM");
        			String add_req = ""+rs.getBoolean("ADD_REQ");
        			String drp_req = ""+rs.getBoolean("DRP_REQ");
        			String npm_pa = ""+rs.getString("NPM_PA");
        			String npm_bak = ""+rs.getString("NPM_BAK");
        			String npm_baa = ""+rs.getString("NPM_BAA");
        			String npm_bauk = ""+rs.getString("NPM_BAUK");
        			String baa_apr = ""+rs.getBoolean("BAA_APPROVAL");
        			String ktu_apr = ""+rs.getBoolean("KTU_APPROVAL");
        			String dkn_apr = ""+rs.getBoolean("DEKAN_APPROVAL");
        			String npm_ktu = ""+rs.getString("NPM_KTU");
        			String npm_dkn = ""+rs.getString("NPM_DEKAN");
        			String lockMhs = ""+rs.getBoolean("LOCKMHS");
        			String kode_kmp = ""+rs.getString("KODE_KAMPUS");
        			String cuid = ""+rs.getInt("CLASS_POOL_UNIQUE_ID");
        			if(Checker.isStringNullOrEmpty(cuid)) {
        				cuid="null";
            	    }
        			
        			brs = brs+thsms+"`"+kdpti+"`"+kdjen+"`"+kdpst+"`"+npmhs+"`"+kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+skskmk+"`"+kelas+"`"+updtm+"`"+krsdown+"`"+khsdown+"`"+bak_apr+"`"+shift_kls+"`"+pa_apr+"`"+note+"`"+lock+"`"+bauk_apr+"`"+idkmk+"`"+add_req+"`"+drp_req+"`"+npm_pa+"`"+npm_bak+"`"+npm_baa+"`"+npm_bauk+"`"+baa_apr+"`"+ktu_apr+"`"+dkn_apr+"`"+npm_ktu+"`"+npm_dkn+"`"+lockMhs+"`"+kode_kmp+"`"+cuid+"`";
        		}
    			while(rs.next());
    			brs = brs.replace("``", "`null`");
    			li.add(brs);
    		}
    		else {
    			li.add("null");
    		}
    		
    		
    		//3.tabel TRNLP
    		stmt = con.prepareStatement("select * from TRNLP where NPMHSTRNLP=?");
    		stmt.setString(1, npmYgMoDiMerge);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String brs = "";
    			do {
    				String thsms = ""+rs.getString("THSMSTRNLP");
    				if(Checker.isStringNullOrEmpty(thsms)) {
    					thsms="null";
            	    }
    				String kdpst = ""+rs.getString("KDPSTTRNLP");
    				String npmhs = ""+rs.getString("NPMHSTRNLP");
    				String kdkmk = ""+rs.getString("KDKMKTRNLP");
    				String nlakh = ""+rs.getString("NLAKHTRNLP");
    				String bobot = ""+rs.getFloat("BOBOTTRNLP");
    				String sksmk = ""+rs.getInt("SKSMKTRNLP");
    				String kdkmk_as = ""+rs.getString("KDKMKASALP");
    				String nakmk_as = ""+rs.getString("NAKMKASALP");
    				String sksmk_as = ""+rs.getInt("SKSMKASAL");
    				String keter = ""+rs.getString("KETERTRNLP");
    				String updtm = ""+rs.getTimestamp("UPDTMTRNLP");
    				String transfered = ""+rs.getBoolean("TRANSFERRED");
    				String approved = ""+rs.getString("APPROVED");
    				if(Checker.isStringNullOrEmpty(approved)) {
    					approved="null";
            	    }
    				brs = brs+thsms+"`"+kdpst+"`"+npmhs+"`"+kdkmk+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+kdkmk_as+"`"+nakmk_as+"`"+sksmk_as+"`"+keter+"`"+updtm+"`"+transfered+"`"+approved+"`";
    			}
    			while(rs.next());
    			brs = brs.replace("``", "`null`");
    			li.add(brs);
    		}
    		else {
    			li.add("null");
    		}

    		
    		//4.tabel TRLSM
    		stmt = con.prepareStatement("select * from TRLSM where NPMHS=?");
    		stmt.setString(1, npmYgMoDiMerge);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String brs = "";
    			do {
    				String thsms = ""+rs.getString("THSMS");
    				if(Checker.isStringNullOrEmpty(thsms)) {
    					thsms="null";
            	    }
        			String kdpst = ""+rs.getString("KDPST");
        			String npmhs = ""+rs.getString("NPMHS");
        			String stmhs = ""+rs.getString("STMHS");
        			String tgl_aju = ""+rs.getTimestamp("TGL_PENGAJUAN");
        			String tkn_apr = ""+rs.getString("TOKEN_APPROVEE");
        			if(Checker.isStringNullOrEmpty(tkn_apr)) {
        				tkn_apr="null";
            	    }
        			brs = brs + thsms+"`"+kdpst+"`"+npmhs+"`"+stmhs+"`"+tgl_aju+"`"+tkn_apr+"`";
    			}
    			while(rs.next());
    			brs = brs.replace("``", "`null`");
    			li.add(brs);
    		}
    		else {
    			li.add("null");
    		}
    		
    		//start process merging
    		if(v!=null && v.size()>0) {
    			try {
    				li = v.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				//System.out.println("brs1 = "+brs);
        				if(!brs.equalsIgnoreCase("null")) {
        					//1. merge ext data dosen date
        					//stmt = con.prepareStatement("UPDATE CIVITAS SET ID=?,ID_OBJ=?,KDPTIMSMHS=?,KDJENMSMHS=?,KDPSTMSMHS=?,NPMHSMSMHS=?,NIMHSMSMHS=?,NMMHSMSMHS=?,SHIFTMSMHS=?,TPLHRMSMHS=?,TGLHRMSMHS=?,KDJEKMSMHS=?,TAHUNMSMHS=?,SMAWLMSMHS=?,BTSTUMSMHS=?,ASSMAMSMHS=?,TGMSKMSMHS=?,TGLLSMSMHS=?,STMHSMSMHS=?,STPIDMSMHS=?,SKSDIMSMHS=?,ASNIMMSMHS=?,ASPTIMSMHS=?,ASJENMSMHS=?,ASPSTMSMHS=?,BISTUMSMHS=?,PEKSBMSMHS=?,NMPEKMSMHS=?,PTPEKMSMHS=?,PSPEKMSMHS=?,NOPRMMSMHS=?,NOKP1MSMHS=?,NOKP2MSMHS=?,NOKP3MSMHS=?,NOKP4MSMHS=?,UPDTMMSMHS=?,GELOMMSMHS=? WHERE NPMHSMSMHS=?");
        					stmt = con.prepareStatement("UPDATE EXT_CIVITAS_DATA_DOSEN SET NODOS_LOCAL=?,GELAR_DEPAN=?,GELAR_BELAKANG=?,NIDNN=?,TIPE_ID=?,NOMOR_ID=?,STATUS=?,PT_S1=?,JURUSAN_S1=?,KDPST_S1=?,GELAR_S1=?,TKN_BIDANG_KEAHLIAN_S1=?,NOIJA_S1=?,TGLLS_S1=?,FILE_IJA_S1=?,JUDUL_TA_S1=?,PT_S2=?,JURUSAN_S2=?,KDPST_S2=?,GELAR_S2=?,TKN_BIDANG_KEAHLIAN_S2=?,NOIJA_S2=?,TGLLS_S2=?,FILE_IJA_S2=?,JUDUL_TA_S2=?,PT_S3=?,JURUSAN_S3=?,KDPST_S3=?,GELAR_S3=?,TKN_BIDANG_KEAHLIAN_S3=?,NOIJA_S3=?,TGLLS_S3=?,FILE_IJA_S3=?,JUDUL_TA_S3=?,PT_PROF=?,JURUSAN_PROF=?,KDPST_PROF=?,GELAR_PROF=?,TKN_BIDANG_KEAHLIAN_PROF=?,NOIJA_PROF=?,TGLLS_PROF=?,FILE_IJA_PROF=?,JUDUL_TA_PROF=?,TOTAL_KUM=?,JABATAN_AKADEMIK_DIKTI=?,JABATAN_AKADEMIK_LOCAL=?,JABATAN_STRUKTURAL=?,IKATAN_KERJA_DOSEN=?,TANGGAL_MULAI_KERJA=?,TANGGAL_KELUAR_KERJA=?,SERDOS=?,KDPTI_HOMEBASE=?,KDPST_HOMEBASE=?,EMAIL_INSTITUSI=?,PANGKAT_GOLONGAN=?,CATATAN_RIWAYAT=?,TIPE_KTP=?,NO_KTP=? WHERE KDPST=? AND NPMHS=?");
        					StringTokenizer st = new StringTokenizer(brs,"`");
        					
        					int i = 1;
        					String kdpst = st.nextToken();
        	        	    String npmhs = st.nextToken();
        	        	    String nodos_loco = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(nodos_loco)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, nodos_loco);
        					}
        	        	    String gelardp = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(gelardp)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, gelardp);
        					}
        	        	    String gelarbl = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(gelarbl)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, gelarbl);
        					}
        	        	    String nidnn = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(nidnn)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, nidnn);
        					}
        	        	    String tipeid = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(tipeid)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, tipeid);
        					}
        	        	    String nomid = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(nomid)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, nomid);
        					}
        	        	    String status = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(status)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, status);
        					}
        	        	    String pt1 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(pt1)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, pt1);
        					}
        	        	    String jur1 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(jur1)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, jur1);
        					}
        	        	    String kdpst1 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(kdpst1)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdpst1);
        					}
        	        	    String gelar1 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(gelar1)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, gelar1);
        					}
        	        	    String bidil1 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(bidil1)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, bidil1);
        					}
        	        	    String noija1 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(noija1)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, noija1);
        					}
        	        	    String tglls1 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(tglls1)) {
        						stmt.setNull(i++, java.sql.Types.DATE);
        					}
        					else {
        						stmt.setDate(i++, java.sql.Date.valueOf(tglls1));
        					}
        	        	    String fileija1 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(fileija1)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, fileija1);
        					}
        	        	    String judul1 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(judul1)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, judul1);
        					}
        	        	    String pt2 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(pt2)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, pt2);
        					}
        	        	    String jur2 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(jur2)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, jur2);
        					}
        	        	    String kdpst2 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(kdpst2)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdpst2);
        					}
        	        	    String gelar2 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(gelar2)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, gelar2);
        					}
        	        	    String bidil2 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(bidil2)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, bidil2);
        					}
        	        	    String noija2 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(noija2)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, noija2);
        					}
        	        	    String tglls2 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(tglls2)) {
        						stmt.setNull(i++, java.sql.Types.DATE);
        					}
        					else {
        						stmt.setDate(i++, java.sql.Date.valueOf(tglls2));
        					}
        	        	    String fileija2 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(fileija2)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, fileija2);
        					}
        	        	    String judul2 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(judul2)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, judul2);
        					}
        	        	    String pt3 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(pt3)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, pt3);
        					}
        	        	    String jur3 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(jur3)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, jur3);
        					}
        	        	    String kdpst3 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(kdpst3)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdpst3);
        					}
        	        	    String gelar3 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(gelar3)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, gelar3);
        					}
        	        	    String bidil3 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(bidil3)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, bidil3);
        					}
        	        	    String noija3 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(noija3)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, noija3);
        					}
        	        	    String tglls3 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(tglls3)) {
        						stmt.setNull(i++, java.sql.Types.DATE);
        					}
        					else {
        						stmt.setDate(i++, java.sql.Date.valueOf(tglls3));
        					}
        	        	    String fileija3 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(fileija3)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, fileija3);
        					}
        	        	    String judul3 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(judul3)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, judul3);
        					}
        	        	    String pt4 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(pt4)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, pt4);
        					}
        	        	    String jur4 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(jur4)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, jur4);
        					}
        	        	    String kdpst4 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(kdpst4)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdpst4);
        					}
        	        	    String gelar4 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(gelar4)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, gelar4);
        					}
        	        	    String bidil4 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(bidil4)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, bidil4);
        					}
        	        	    String noija4 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(noija4)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, noija4);
        					}
        	        	    String tglls4 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(tglls4)) {
        						stmt.setNull(i++, java.sql.Types.DATE);
        					}
        					else {
        						stmt.setDate(i++, java.sql.Date.valueOf(tglls4));
        					}
        	        	    String fileija4 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(fileija4)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, fileija4);
        					}
        	        	    String judul4 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(judul4)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, judul4);
        					}
        	        	    String ttkum =  st.nextToken();//""+rs.getInt("TOTAL_KUM");
        	        	    if(Checker.isStringNullOrEmpty(ttkum)) {
        						stmt.setNull(i++, java.sql.Types.INTEGER);
        					}
        					else {
        						stmt.setInt(i++, Integer.parseInt(ttkum));
        					}
        	        	    String jabak = st.nextToken();// ""+rs.getString("JABATAN_AKADEMIK_DIKTI");
        	        	    if(Checker.isStringNullOrEmpty(jabak)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, jabak);
        					}
        	        	    String jabak_loco = st.nextToken();// ""+rs.getString("JABATAN_AKADEMIK_LOCAL");
        	        	    if(Checker.isStringNullOrEmpty(jabak_loco)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, jabak_loco);
        					}
        	        	    String jabstruk = st.nextToken();// ""+rs.getString("JABATAN_STRUKTURAL");
        	        	    if(Checker.isStringNullOrEmpty(jabstruk)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, jabstruk);
        					}
        	        	    String ika = st.nextToken();// ""+rs.getString("IKATAN_KERJA_DOSEN");
        	        	    if(Checker.isStringNullOrEmpty(ika)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, ika);
        					}
        	        	    String tgmsk = st.nextToken();// ""+rs.getDate("TANGGAL_MULAI_KERJA");
        	        	    if(Checker.isStringNullOrEmpty(tgmsk)) {
        						stmt.setNull(i++, java.sql.Types.DATE);
        					}
        					else {
        						stmt.setDate(i++, java.sql.Date.valueOf(tgmsk));
        					}
        	        	    String tgklr = st.nextToken();// ""+rs.getDate("TANGGAL_KELUAR_KERJA");
        	        	    //System.out.println("tgklr = "+tgklr);
        	        	    if(Checker.isStringNullOrEmpty(tgklr)) {
        						stmt.setNull(i++, java.sql.Types.DATE);
        					}
        					else {
        						stmt.setDate(i++, java.sql.Date.valueOf(tgklr));
        					}
        	        	    String serdos = st.nextToken();// ""+rs.getString("SERDOS");
        	        	    if(Checker.isStringNullOrEmpty(serdos)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, serdos);
        					}
        	        	    String kdpti_homebase = st.nextToken();// ""+rs.getString("KDPTI_HOMEBASE");
        	        	    if(Checker.isStringNullOrEmpty(kdpti_homebase)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdpti_homebase);
        					}
        	        	    String kdpst_homebase = st.nextToken();// ""+rs.getString("KDPST_HOMEBASE");
        	        	    if(Checker.isStringNullOrEmpty(kdpst_homebase)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdpst_homebase);
        					}
        	        	    String email_loco = st.nextToken();// ""+rs.getString("EMAIL_INSTITUSI");
        	        	    if(Checker.isStringNullOrEmpty(email_loco)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, email_loco);
        					}
        	        	    String pangkat = st.nextToken();// ""+rs.getString("PANGKAT_GOLONGAN");
        	        	    if(Checker.isStringNullOrEmpty(pangkat)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, pangkat);
        					}
        	        	    String note_history = st.nextToken();// ""+rs.getString("CATATAN_RIWAYAT");
        	        	    if(Checker.isStringNullOrEmpty(note_history)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, note_history);
        					}
        	        	    String tipe_id = st.nextToken();// ""+rs.getString("TIPE_KTP");
        	        	    if(Checker.isStringNullOrEmpty(tipe_id)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, tipe_id);
        					}
        	        	    String no_id = st.nextToken();// ""+rs.getString("NO_KTP");
        	        	    if(Checker.isStringNullOrEmpty(no_id)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, no_id);
        					}
        	        	    //where 
        	        	    stmt.setString(i++, kdpstTujuanMerge);
        	        	    stmt.setString(i++, npmTujuanMerge);
        	        	    int j = stmt.executeUpdate();
        	        	    //System.out.println("update data dosen = "+j);
        	        	    if(j<1) { //belum ada data jadi harus insert
        	        	    	stmt = con.prepareStatement("INSERT INTO EXT_CIVITAS_DATA_DOSEN (KDPST,NPMHS,NODOS_LOCAL,GELAR_DEPAN,GELAR_BELAKANG,NIDNN,TIPE_ID,NOMOR_ID,STATUS,PT_S1,JURUSAN_S1,KDPST_S1,GELAR_S1,TKN_BIDANG_KEAHLIAN_S1,NOIJA_S1,TGLLS_S1,FILE_IJA_S1,JUDUL_TA_S1,PT_S2,JURUSAN_S2,KDPST_S2,GELAR_S2,TKN_BIDANG_KEAHLIAN_S2,NOIJA_S2,TGLLS_S2,FILE_IJA_S2,JUDUL_TA_S2,PT_S3,JURUSAN_S3,KDPST_S3,GELAR_S3,TKN_BIDANG_KEAHLIAN_S3,NOIJA_S3,TGLLS_S3,FILE_IJA_S3,JUDUL_TA_S3,PT_PROF,JURUSAN_PROF,KDPST_PROF,GELAR_PROF,TKN_BIDANG_KEAHLIAN_PROF,NOIJA_PROF,TGLLS_PROF,FILE_IJA_PROF,JUDUL_TA_PROF,TOTAL_KUM,JABATAN_AKADEMIK_DIKTI,JABATAN_AKADEMIK_LOCAL,JABATAN_STRUKTURAL,IKATAN_KERJA_DOSEN,TANGGAL_MULAI_KERJA,TANGGAL_KELUAR_KERJA,SERDOS,KDPTI_HOMEBASE,KDPST_HOMEBASE,EMAIL_INSTITUSI,PANGKAT_GOLONGAN,CATATAN_RIWAYAT,TIPE_KTP,NO_KTP) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        	        	    	
        	        	    	//(KDPST,NPMHS,NODOS_LOCAL,GELAR_DEPAN,GELAR_BELAKANG,NIDNN,TIPE_ID,NOMOR_ID,STATUS,PT_S1,JURUSAN_S1,KDPST_S1,GELAR_S1,TKN_BIDANG_KEAHLIAN_S1,NOIJA_S1,TGLLS_S1,FILE_IJA_S1,JUDUL_TA_S1,PT_S2,JURUSAN_S2,KDPST_S2,GELAR_S2,TKN_BIDANG_KEAHLIAN_S2,NOIJA_S2,TGLLS_S2,FILE_IJA_S2,JUDUL_TA_S2,PT_S3,JURUSAN_S3,KDPST_S3,GELAR_S3,TKN_BIDANG_KEAHLIAN_S3,NOIJA_S3,TGLLS_S3,FILE_IJA_S3,JUDUL_TA_S3,PT_PROF,JURUSAN_PROF,KDPST_PROF,GELAR_PROF,TKN_BIDANG_KEAHLIAN_PROF,NOIJA_PROF,TGLLS_PROF,FILE_IJA_PROF,JUDUL_TA_PROF,TOTAL_KUM,JABATAN_AKADEMIK_DIKTI,JABATAN_AKADEMIK_LOCAL,JABATAN_STRUKTURAL,IKATAN_KERJA_DOSEN,TANGGAL_MULAI_KERJA,TANGGAL_KELUAR_KERJA,SERDOS,KDPTI_HOMEBASE,KDPST_HOMEBASE,EMAIL_INSTITUSI,PANGKAT_GOLONGAN,CATATAN_RIWAYAT,TIPE_KTP,NO_KTP)
        	        	    	//Stringkdpst = st.nextToken();
        	        	    	i=1;
        	        	    	if(Checker.isStringNullOrEmpty(kdpstTujuanMerge)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdpstTujuanMerge);
            					}
            	        	    //String npmhs = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(npmTujuanMerge)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, npmTujuanMerge);
            					}
            	        	    //String nodos_loco = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(nodos_loco)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, nodos_loco);
            					}
            	        	    //String gelardp = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(gelardp)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, gelardp);
            					}
            	        	    //String gelarbl = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(gelarbl)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, gelarbl);
            					}
            	        	    //String nidnn = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(nidnn)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, nidnn);
            					}
            	        	    //String tipeid = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(tipeid)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, tipeid);
            					}
            	        	    //String nomid = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(nomid)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, nomid);
            					}
            	        	    //String status = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(status)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, status);
            					}
            	        	    //String pt1 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(pt1)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, pt1);
            					}
            	        	    //String jur1 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(jur1)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, jur1);
            					}
            	        	    //String kdpst1 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(kdpst1)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdpst1);
            					}
            	        	    //String gelar1 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(gelar1)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, gelar1);
            					}
            	        	    //String bidil1 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(bidil1)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, bidil1);
            					}
            	        	    //String noija1 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(noija1)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, noija1);
            					}
            	        	    //String tglls1 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(tglls1)) {
            						stmt.setNull(i++, java.sql.Types.DATE);
            					}
            					else {
            						stmt.setDate(i++, java.sql.Date.valueOf(tglls1));
            					}
            	        	    //String fileija1 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(fileija1)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, fileija1);
            					}
            	        	    //String judul1 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(judul1)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, judul1);
            					}
            	        	    //String pt2 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(pt2)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, pt2);
            					}
            	        	    //String jur2 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(jur2)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, jur2);
            					}
            	        	    //String kdpst2 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(kdpst2)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdpst2);
            					}
            	        	    //String gelar2 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(gelar2)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, gelar2);
            					}
            	        	    //String bidil2 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(bidil2)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, bidil2);
            					}
            	        	    //String noija2 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(noija2)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, noija2);
            					}
            	        	    //String tglls2 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(tglls2)) {
            						stmt.setNull(i++, java.sql.Types.DATE);
            					}
            					else {
            						stmt.setDate(i++, java.sql.Date.valueOf(tglls2));
            					}
            	        	    //String fileija2 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(fileija2)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, fileija2);
            					}
            	        	    //String judul2 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(judul2)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, judul2);
            					}
            	        	    //String pt3 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(pt3)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, pt3);
            					}
            	        	    //String jur3 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(jur3)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, jur3);
            					}
            	        	    //String kdpst3 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(kdpst3)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdpst3);
            					}
            	        	    //String gelar3 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(gelar3)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, gelar3);
            					}
            	        	    //String bidil3 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(bidil3)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, bidil3);
            					}
            	        	    //String noija3 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(noija3)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, noija3);
            					}
            	        	    //String tglls3 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(tglls3)) {
            						stmt.setNull(i++, java.sql.Types.DATE);
            					}
            					else {
            						stmt.setDate(i++, java.sql.Date.valueOf(tglls3));
            					}
            	        	    //String fileija3 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(fileija3)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, fileija3);
            					}
            	        	    //String judul3 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(judul3)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, judul3);
            					}
            	        	    //String pt4 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(pt4)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, pt4);
            					}
            	        	    //String jur4 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(jur4)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, jur4);
            					}
            	        	    //String kdpst4 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(kdpst4)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdpst4);
            					}
            	        	    //String gelar4 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(gelar4)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, gelar4);
            					}
            	        	    //String bidil4 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(bidil4)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, bidil4);
            					}
            	        	    //String noija4 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(noija4)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, noija4);
            					}
            	        	    //String tglls4 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(tglls4)) {
            						stmt.setNull(i++, java.sql.Types.DATE);
            					}
            					else {
            						stmt.setDate(i++, java.sql.Date.valueOf(tglls4));
            					}
            	        	    //String fileija4 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(fileija4)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, fileija4);
            					}
            	        	    //String judul4 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(judul4)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, judul4);
            					}
            	        	    //String ttkum =  st.nextToken();//""+rs.getInt("TOTAL_KUM");
            	        	    if(Checker.isStringNullOrEmpty(ttkum)) {
            						stmt.setNull(i++, java.sql.Types.INTEGER);
            					}
            					else {
            						stmt.setInt(i++, Integer.parseInt(ttkum));
            					}
            	        	    //String jabak = st.nextToken();// ""+rs.getString("JABATAN_AKADEMIK_DIKTI");
            	        	    if(Checker.isStringNullOrEmpty(jabak)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, jabak);
            					}
            	        	    //String jabak_loco = st.nextToken();// ""+rs.getString("JABATAN_AKADEMIK_LOCAL");
            	        	    if(Checker.isStringNullOrEmpty(jabak_loco)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, jabak_loco);
            					}
            	        	    //String jabstruk = st.nextToken();// ""+rs.getString("JABATAN_STRUKTURAL");
            	        	    if(Checker.isStringNullOrEmpty(jabstruk)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, jabstruk);
            					}
            	        	    //String ika = st.nextToken();// ""+rs.getString("IKATAN_KERJA_DOSEN");
            	        	    if(Checker.isStringNullOrEmpty(ika)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, ika);
            					}
            	        	    //String tgmsk = st.nextToken();// ""+rs.getDate("TANGGAL_MULAI_KERJA");
            	        	    if(Checker.isStringNullOrEmpty(tgmsk)) {
            						stmt.setNull(i++, java.sql.Types.DATE);
            					}
            					else {
            						stmt.setDate(i++, java.sql.Date.valueOf(tgmsk));
            					}
            	        	    //String tgklr = st.nextToken();// ""+rs.getDate("TANGGAL_KELUAR_KERJA");
            	        	    //System.out.println("tgklr = "+tgklr);
            	        	    if(Checker.isStringNullOrEmpty(tgklr)) {
            						stmt.setNull(i++, java.sql.Types.DATE);
            					}
            					else {
            						stmt.setDate(i++, java.sql.Date.valueOf(tgklr));
            					}
            	        	    //String serdos = st.nextToken();// ""+rs.getString("SERDOS");
            	        	    if(Checker.isStringNullOrEmpty(serdos)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, serdos);
            					}
            	        	    //String kdpti_homebase = st.nextToken();// ""+rs.getString("KDPTI_HOMEBASE");
            	        	    if(Checker.isStringNullOrEmpty(kdpti_homebase)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdpti_homebase);
            					}
            	        	    //String kdpst_homebase = st.nextToken();// ""+rs.getString("KDPST_HOMEBASE");
            	        	    if(Checker.isStringNullOrEmpty(kdpst_homebase)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdpst_homebase);
            					}
            	        	    //String email_loco = st.nextToken();// ""+rs.getString("EMAIL_INSTITUSI");
            	        	    if(Checker.isStringNullOrEmpty(email_loco)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, email_loco);
            					}
            	        	    //String pangkat = st.nextToken();// ""+rs.getString("PANGKAT_GOLONGAN");
            	        	    if(Checker.isStringNullOrEmpty(pangkat)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, pangkat);
            					}
            	        	    //String note_history = st.nextToken();// ""+rs.getString("CATATAN_RIWAYAT");
            	        	    if(Checker.isStringNullOrEmpty(note_history)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, note_history);
            					}
            	        	    //String tipe_id = st.nextToken();// ""+rs.getString("TIPE_KTP");
            	        	    if(Checker.isStringNullOrEmpty(tipe_id)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, tipe_id);
            					}
            	        	    //String no_id = st.nextToken();// ""+rs.getString("NO_KTP");
            	        	    if(Checker.isStringNullOrEmpty(no_id)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, no_id);
            					}
            	        	    j = stmt.executeUpdate();
            	        	    //System.out.println("inerst dat dos = "+j);
        	        	    }
        	        	    
        				}
        				
        				
        				//2. merge trnlm 
        				String tkn_thsms = null;
        				brs = (String)li.next();
        				//System.out.println("brs2 = "+brs);
        				if(!brs.equalsIgnoreCase("null")) {
        					stmt = con.prepareStatement("INSERT ignore INTO TRNLM (THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,KELASTRNLM,KRSDONWLOADED,KHSDONWLOADED,BAK_APPROVAL,SHIFTTRNLM,PA_APPROVAL,NOTE,LOCKTRNLM,BAUK_APPROVAL,IDKMKTRNLM,ADD_REQ,DRP_REQ,NPM_PA,NPM_BAK,NPM_BAA,NPM_BAUK,BAA_APPROVAL,KTU_APPROVAL,DEKAN_APPROVAL,NPM_KTU,NPM_DEKAN,LOCKMHS,KODE_KAMPUS,CLASS_POOL_UNIQUE_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        					//System.out.println("ok in  ");
        					
        					StringTokenizer st = new StringTokenizer(brs,"`");
        					
        					
        					while(st.hasMoreTokens()) {
        						int i = 1;
        						String thsms = st.nextToken();// ""+rs.getString("THSMSTRNLM");
        						//System.out.println("thsms= "+thsms);
            					if(Checker.isStringNullOrEmpty(thsms)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, thsms);
            					}
                    			String kdpti = st.nextToken();// ""+rs.getString("KDPTITRNLM");
                    			if(Checker.isStringNullOrEmpty(kdpti)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdpti);
            					}
                    			String kdjen =  st.nextToken();// ""+rs.getString("KDJENTRNLM");
                    			if(Checker.isStringNullOrEmpty(kdjen)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdjen);
            					}
                    			String kdpst =  st.nextToken();// ""+rs.getString("KDPSTTRNLM");
                    			if(Checker.isStringNullOrEmpty(kdpstTujuanMerge)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdpstTujuanMerge);
            					}
                    			String npmhs =  st.nextToken();// ""+rs.getString("NPMHSTRNLM");
                    			//System.out.println("npmhs="+npmhs+" -> "+npmTujuanMerge);
                    			if(Checker.isStringNullOrEmpty(npmTujuanMerge)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, npmTujuanMerge);
            					}
                    			String kdkmk =  st.nextToken();// ""+rs.getString("KDKMKTRNLM");
                    			if(Checker.isStringNullOrEmpty(kdkmk)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdkmk);
            					}
                    			String nilai =  st.nextToken();// ""+rs.getFloat("NILAITRNLM");
                    			if(Checker.isStringNullOrEmpty(nilai)) {
            						stmt.setNull(i++, java.sql.Types.FLOAT);
            					}
            					else {
            						stmt.setFloat(i++, Float.parseFloat(nilai));
            					}
                    			String nlakh =  st.nextToken();// ""+rs.getString("NLAKHTRNLM");
                    			if(Checker.isStringNullOrEmpty(nlakh)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, nlakh);
            					}
                    			String bobot =  st.nextToken();// ""+rs.getFloat("BOBOTTRNLM");
                    			if(Checker.isStringNullOrEmpty(bobot)) {
            						stmt.setNull(i++, java.sql.Types.FLOAT);
            					}
            					else {
            						stmt.setFloat(i++, Float.parseFloat(bobot));
            					}
                    			String skskmk =  st.nextToken();// ""+rs.getInt("SKSMKTRNLM");
                    			if(Checker.isStringNullOrEmpty(skskmk)) {
            						stmt.setNull(i++, java.sql.Types.INTEGER);
            					}
            					else {
            						stmt.setInt(i++, Integer.parseInt(skskmk));
            					}
                    			String kelas =  st.nextToken();// ""+rs.getString("KELASTRNLM");
                    			if(Checker.isStringNullOrEmpty(kelas)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kelas);
            					}
                    			String updtm =  st.nextToken();// ""+rs.getString("UPDTMTRNLM");
                    			//if(Checker.isStringNullOrEmpty(note_history)) {
            					//	stmt.setNull(i++, java.sql.Types.VARCHAR);
            					//}
            					//else {
            					//	stmt.setString(i++, note_history);
            					//}
                    			String krsdown =  st.nextToken();// ""+rs.getBoolean("KRSDONWLOADED");
                    			if(Checker.isStringNullOrEmpty(krsdown)) {
            						stmt.setNull(i++, java.sql.Types.BOOLEAN);
            					}
            					else {
            						stmt.setBoolean(i++, Boolean.parseBoolean(krsdown));
            					}
                    			String khsdown =  st.nextToken();// ""+rs.getBoolean("KHSDONWLOADED");
                    			if(Checker.isStringNullOrEmpty(khsdown)) {
            						stmt.setNull(i++, java.sql.Types.BOOLEAN);
            					}
            					else {
            						stmt.setBoolean(i++, Boolean.parseBoolean(khsdown));
            					}
                    			String bak_apr =  st.nextToken();// ""+rs.getBoolean("BAK_APPROVAL");
                    			if(Checker.isStringNullOrEmpty(bak_apr)) {
            						stmt.setNull(i++, java.sql.Types.BOOLEAN);
            					}
            					else {
            						stmt.setBoolean(i++, Boolean.parseBoolean(bak_apr));
            					}
                    			String shift_kls =  st.nextToken();// ""+rs.getString("SHIFTTRNLM");
                    			if(Checker.isStringNullOrEmpty(shift_kls)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, shift_kls);
            					}
                    			String pa_apr =  st.nextToken();// ""+rs.getBoolean("PA_APPROVAL");
                    			if(Checker.isStringNullOrEmpty(pa_apr)) {
            						stmt.setNull(i++, java.sql.Types.BOOLEAN);
            					}
            					else {
            						stmt.setBoolean(i++, Boolean.parseBoolean(pa_apr));
            					}
                    			String note =  st.nextToken();// ""+rs.getString("NOTE");
                    			if(Checker.isStringNullOrEmpty(note)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, note);
            					}
                    			String lock =  st.nextToken();// ""+rs.getBoolean("LOCKTRNLM");
                    			if(Checker.isStringNullOrEmpty(lock)) {
            						stmt.setNull(i++, java.sql.Types.BOOLEAN);
            					}
            					else {
            						stmt.setBoolean(i++, Boolean.parseBoolean(lock));
            					}
                    			String bauk_apr =  st.nextToken();// ""+rs.getBoolean("BAUK_APPROVAL");
                    			if(Checker.isStringNullOrEmpty(bauk_apr)) {
            						stmt.setNull(i++, java.sql.Types.BOOLEAN);
            					}
            					else {
            						stmt.setBoolean(i++, Boolean.parseBoolean(bauk_apr));
            					}
                    			String idkmk =  st.nextToken();// ""+rs.getInt("IDKMKTRNLM");
                    			if(Checker.isStringNullOrEmpty(idkmk)) {
            						stmt.setNull(i++, java.sql.Types.INTEGER);
            					}
            					else {
            						stmt.setInt(i++, Integer.parseInt(idkmk));
            					}
                    			String add_req =  st.nextToken();// ""+rs.getBoolean("ADD_REQ");
                    			if(Checker.isStringNullOrEmpty(add_req)) {
            						stmt.setNull(i++, java.sql.Types.BOOLEAN);
            					}
            					else {
            						stmt.setBoolean(i++, Boolean.parseBoolean(add_req));
            					}
                    			String drp_req =  st.nextToken();// ""+rs.getBoolean("DRP_REQ");
                    			if(Checker.isStringNullOrEmpty(drp_req)) {
            						stmt.setNull(i++, java.sql.Types.BOOLEAN);
            					}
            					else {
            						stmt.setBoolean(i++, Boolean.parseBoolean(drp_req));
            					}
                    			String npm_pa =  st.nextToken();// ""+rs.getString("NPM_PA");
                    			if(Checker.isStringNullOrEmpty(npm_pa)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, npm_pa);
            					}
                    			String npm_bak =  st.nextToken();// ""+rs.getString("NPM_BAK");
                    			if(Checker.isStringNullOrEmpty(npm_bak)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, npm_bak);
            					}
                    			String npm_baa =  st.nextToken();// ""+rs.getString("NPM_BAA");
                    			if(Checker.isStringNullOrEmpty(npm_baa)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, npm_baa);
            					}
                    			String npm_bauk =  st.nextToken();// ""+rs.getString("NPM_BAUK");
                    			if(Checker.isStringNullOrEmpty(npm_bauk)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, npm_bauk);
            					}
                    			String baa_apr =  st.nextToken();// ""+rs.getBoolean("BAA_APPROVAL");
                    			if(Checker.isStringNullOrEmpty(baa_apr)) {
            						stmt.setNull(i++, java.sql.Types.BOOLEAN);
            					}
            					else {
            						stmt.setBoolean(i++, Boolean.parseBoolean(baa_apr));
            					}
                    			String ktu_apr =  st.nextToken();// ""+rs.getBoolean("KTU_APPROVAL");
                    			if(Checker.isStringNullOrEmpty(ktu_apr)) {
            						stmt.setNull(i++, java.sql.Types.BOOLEAN);
            					}
            					else {
            						stmt.setBoolean(i++, Boolean.parseBoolean(ktu_apr));
            					}
                    			String dkn_apr =  st.nextToken();// ""+rs.getBoolean("DEKAN_APPROVAL");
                    			if(Checker.isStringNullOrEmpty(dkn_apr)) {
            						stmt.setNull(i++, java.sql.Types.BOOLEAN);
            					}
            					else {
            						stmt.setBoolean(i++, Boolean.parseBoolean(dkn_apr));
            					}
                    			String npm_ktu =  st.nextToken();// ""+rs.getString("NPM_KTU");
                    			if(Checker.isStringNullOrEmpty(npm_ktu)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, npm_ktu);
            					}
                    			String npm_dkn =  st.nextToken();// ""+rs.getString("NPM_DEKAN");
                    			if(Checker.isStringNullOrEmpty(npm_dkn)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, npm_dkn);
            					}
                    			String lockMhs =  st.nextToken();// ""+rs.getBoolean("LOCKMHS");
                    			if(Checker.isStringNullOrEmpty(lockMhs)) {
            						stmt.setNull(i++, java.sql.Types.BOOLEAN);
            					}
            					else {
            						stmt.setBoolean(i++, Boolean.parseBoolean(lockMhs));
            					}
                    			String kode_kmp =  st.nextToken();// ""+rs.getString("KODE_KAMPUS");
                    			if(Checker.isStringNullOrEmpty(kode_kmp)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kode_kmp);
            					}
                    			String cuid =  st.nextToken();// ""+rs.getInt("CLASS_POOL_UNIQUE_ID");
                    			if(Checker.isStringNullOrEmpty(cuid) || cuid.equalsIgnoreCase("0")) {
            						stmt.setNull(i++, java.sql.Types.INTEGER);
            					}
            					else {
            						stmt.setInt(i++, Integer.parseInt(cuid));
            					}
                    			//System.out.println("masuk="+i);
                    			try {
                    				int j = stmt.executeUpdate();
                    				if(j>0) {
                    					if(tkn_thsms==null) {
                    						tkn_thsms = new String(thsms+"`"+kdpst+"`"+npmTujuanMerge);
                    					}
                    					else {
                    						tkn_thsms=tkn_thsms+"`"+thsms+"`"+kdpst+"`"+npmTujuanMerge;
                    					}
                    				}
                    				//System.out.println("j="+j);
                    			}
                    			catch(Exception esq) {
                    				esq.printStackTrace();;
                    				//ignore
                    			}
        					}
        					
        					
        					if(tkn_thsms!=null) {
        						stmt = con.prepareStatement("INSERT ignore into TRAKM(THSMSTRAKM,KDPTITRAKM,KDJENTRAKM,KDPSTTRAKM,NPMHSTRAKM,SKSEMTRAKM,NLIPSTRAKM,SKSTTTRAKM,NLIPKTRAKM)values(?,?,?,?,?,?,?,?,?)");
        						StringTokenizer st1 = new StringTokenizer(tkn_thsms,"`");
        						while(st1.hasMoreTokens()) {
        							String tmp_thsms = st1.nextToken();
        							String tmp_kdpst = st1.nextToken();
        							String tmp_npmhs = st1.nextToken();
        		        			stmt.setString(1, tmp_thsms);
        		            		stmt.setString(2, Constants.getKdpti());
        		            		stmt.setNull(3,java.sql.Types.VARCHAR);
        		            		stmt.setString(4, tmp_kdpst);
        		            		stmt.setString(5, tmp_npmhs);
        		            		stmt.setInt(6, 0);
        		            		stmt.setDouble(7, 0);
        		            		stmt.setInt(8, 0);
        		            		stmt.setDouble(9, 0);
        		            		stmt.executeUpdate();
        		        		}
        					}
        					
        					
        					/*
        					 * 
        		while(lid.hasNext()) {
        			
        		}
        					 */
        				}
        				
        				//3. trmlp
        				brs = (String)li.next();
        				//System.out.println("brs3 = "+brs);
        				if(!brs.equalsIgnoreCase("null")) {
        					stmt = con.prepareStatement("INSERT INTO TRNLP (THSMSTRNLP,KDPSTTRNLP,NPMHSTRNLP,KDKMKTRNLP,NLAKHTRNLP,BOBOTTRNLP,SKSMKTRNLP,KDKMKASALP,NAKMKASALP,SKSMKASAL,KETERTRNLP,TRANSFERRED,APPROVED)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        					
        					StringTokenizer st = new StringTokenizer(brs,"`");
        					while(st.hasMoreTokens()) {
        						int i = 1;
            					String thsms = st.nextToken();//""+rs.getString("THSMSTRNLP");
            					if(Checker.isStringNullOrEmpty(thsms)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, thsms);
            					}
                				String kdpst = st.nextToken();// ""+rs.getString("KDPSTTRNLP");
                				if(Checker.isStringNullOrEmpty(kdpstTujuanMerge)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdpstTujuanMerge);
            					}
                				String npmhs = st.nextToken();// ""+rs.getString("NPMHSTRNLP");
                				if(Checker.isStringNullOrEmpty(npmTujuanMerge)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, npmTujuanMerge);
            					}
                				String kdkmk = st.nextToken();// ""+rs.getString("KDKMKTRNLP");
                				if(Checker.isStringNullOrEmpty(kdkmk)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdkmk);
            					}
                				String nlakh = st.nextToken();// ""+rs.getString("NLAKHTRNLP");
                				if(Checker.isStringNullOrEmpty(nlakh)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, nlakh);
            					}
                				String bobot = st.nextToken();// ""+rs.getFloat("BOBOTTRNLP");
                				if(Checker.isStringNullOrEmpty(bobot)) {
            						stmt.setNull(i++, java.sql.Types.FLOAT);
            					}
            					else {
            						stmt.setFloat(i++, Float.parseFloat(bobot));
            					}
                				String sksmk = st.nextToken();// ""+rs.getInt("SKSMKTRNLP");
                				if(Checker.isStringNullOrEmpty(sksmk)) {
            						stmt.setNull(i++, java.sql.Types.INTEGER);
            					}
            					else {
            						stmt.setInt(i++, Integer.parseInt(sksmk));
            					}
                				String kdkmk_as = st.nextToken();// ""+rs.getString("KDKMKASALP");
                				if(Checker.isStringNullOrEmpty(kdkmk_as)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdkmk_as);
            					}
                				String nakmk_as = st.nextToken();// ""+rs.getString("NAKMKASALP");
                				if(Checker.isStringNullOrEmpty(nakmk_as)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, nakmk_as);
            					}
                				String sksmk_as = st.nextToken();// ""+rs.getInt("SKSMKASAL");
                				if(Checker.isStringNullOrEmpty(sksmk_as)) {
            						stmt.setNull(i++, java.sql.Types.INTEGER);
            					}
            					else {
            						stmt.setInt(i++, Integer.parseInt(sksmk_as));
            					}
                				String keter = st.nextToken();// ""+rs.getString("KETERTRNLP");
                				if(Checker.isStringNullOrEmpty(keter)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, keter);
            					}
                				String updtm = st.nextToken();// ""+rs.getTimestamp("UPDTMTRNLP"); -- ignore not used
                				String transfered = st.nextToken();// ""+rs.getBoolean("TRANSFERRED");
                				if(Checker.isStringNullOrEmpty(transfered)) {
            						stmt.setNull(i++, java.sql.Types.BOOLEAN);
            					}
            					else {
            						stmt.setBoolean(i++, Boolean.parseBoolean(transfered));
            					}
                				String approved = st.nextToken();// ""+rs.getString("APPROVED");
                				if(Checker.isStringNullOrEmpty(approved)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, approved);
            					}
                				try {
                					int j = stmt.executeUpdate();
                					//System.out.println("j="+j);
                				}
                				catch (Exception e) {
                					
                				}
        					}
        				}	
        				
        				
        				
        				//trnsm
        				brs = (String)li.next();
        				//System.out.println("brs4 = "+brs);
        				if(!brs.equalsIgnoreCase("null")) {
        					stmt = con.prepareStatement("INSERT INTO TRLSM (THSMS,KDPST,NPMHS,STMHS,TGL_PENGAJUAN,TOKEN_APPROVEE) VALUES (?,?,?,?,?,?)");
        					
        					StringTokenizer st = new StringTokenizer(brs,"`");
        					
        					int i = 1;
        					String thsms = st.nextToken();//""+rs.getString("THSMS");
        					if(Checker.isStringNullOrEmpty(thsms)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, thsms);
        					}
                			String kdpst = st.nextToken();// ""+rs.getString("KDPST");
                			if(Checker.isStringNullOrEmpty(kdpstTujuanMerge)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdpstTujuanMerge);
        					}
                			String npmhs = st.nextToken();// ""+rs.getString("NPMHS");
                			if(Checker.isStringNullOrEmpty(npmTujuanMerge)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, npmTujuanMerge);
        					}
                			String stmhs = st.nextToken();// ""+rs.getString("STMHS");
                			if(Checker.isStringNullOrEmpty(stmhs)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, stmhs);
        					}
                			String tgl_aju = st.nextToken();// ""+rs.getTimestamp("TGL_PENGAJUAN");
                			if(Checker.isStringNullOrEmpty(tgl_aju)) {
        						stmt.setNull(i++, java.sql.Types.TIMESTAMP);
        					}
        					else {
        						stmt.setTimestamp(i++, Timestamp.valueOf(tgl_aju));
        					}
                			String tkn_apr = st.nextToken();// ""+rs.getString("TOKEN_APPROVEE");
                			if(Checker.isStringNullOrEmpty(tkn_apr)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, tkn_apr);
        					}
                			try {
                				int j = stmt.executeUpdate();
                				//System.out.println("j="+j);
                			}
                			catch (Exception e) {}
        				}	
        				
        				//update pymnt && 
        				java.sql.Timestamp ts = AskSystem.getCurrentTimestamp();
        				stmt = con.prepareStatement("UPDATE PYMNT set KDPSTPYMNT=?,NPMHSPYMNT=?,ASAL_NPM_TRANSAKSI=?,MERGE_TIME=? where NPMHSPYMNT=?");
        				stmt.setString(1, kdpstTujuanMerge);
        				stmt.setString(2, npmTujuanMerge);
        				stmt.setString(3, npmYgMoDiMerge);
        				stmt.setTimestamp(4, ts);
        				stmt.setString(5, npmYgMoDiMerge);
        				stmt.executeUpdate();
        				//update pymnt_transit
        				stmt = con.prepareStatement("UPDATE PYMNT_TRANSIT set KDPSTPYMNT=?,NPMHSPYMNT=?,ASAL_NPM_TRANSAKSI=?,MERGE_TIME=? where NPMHSPYMNT=?");
        				stmt.setString(1, kdpstTujuanMerge);
        				stmt.setString(2, npmTujuanMerge);
        				stmt.setString(3, npmYgMoDiMerge);
        				stmt.setTimestamp(4, ts);
        				stmt.setString(5, npmYgMoDiMerge);
        				stmt.executeUpdate();
        				//update CLASS_POOL
        				stmt = con.prepareStatement("update CLASS_POOL set NPMDOS=? where NPMDOS=? and THSMS=?");
        				stmt.setString(1, npmTujuanMerge);
        				stmt.setString(2, npmYgMoDiMerge);
        				stmt.setString(3, Checker.getThsmsNow());
        				stmt.executeUpdate();
        			}
    	    	//catch (NamingException e) {
    	    	//	e.printStackTrace();
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
    
    public String updateNpmPddikti(String[]npmhs, String[]nimhs) {
    	//return list_nim conflic
    	String list_conflict_nimhs = null;
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cek apa nimhs pernah digunakan
    		
    		stmt = con.prepareStatement("select NPMHSMSMHS from CIVITAS where NIMHSMSMHS=?");
    		for(int i=0;i<npmhs.length;i++) {
    			if(!Checker.isStringNullOrEmpty(nimhs[i])) {
    				stmt.setString(1, nimhs[i]);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				boolean match = false;
        				String tmp_list = new String(nimhs[i]);
        				do {
        					String npm_used = rs.getString(1);
        					if(npm_used.equalsIgnoreCase(npmhs[i])) {
        						match = true;
        					}
        					else {
        						tmp_list = tmp_list+","+npm_used;
        					}
        				}
        				while(rs.next() && !match);
        				if(!match) {
        					//nim sudah ada yg mengghunakan
        					if(list_conflict_nimhs==null) {
        						list_conflict_nimhs = new String(tmp_list);
        					}
        					else {
        						list_conflict_nimhs = list_conflict_nimhs+"`"+tmp_list;
        					}
        				}
        				else {
        					//match ignore krn npm tersebut memang sudah menggunakan nim tersebut
        				}
        			}
        			else {
        				//nim belum pernah dipake=aman
        				li.add(npmhs[i]+"`"+nimhs[i]);
        			}
    			}
    			else {
    				//set null
    				li.add(npmhs[i]+"`null");
    			}
    		}
    		
    		//update
    		//System.out.println("v.size="+v.size());
    		if(v.size()>0) {
    			li = v.listIterator();
    			stmt = con.prepareStatement("UPDATE CIVITAS set NIMHSMSMHS=? where NPMHSMSMHS=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				//System.out.println("baris="+brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String target_npm = st.nextToken();
    				String target_nim = st.nextToken();
    				
    				if(Checker.isStringNullOrEmpty(target_nim)) {
    					stmt.setNull(1, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(1, target_nim);	
    				}
    				stmt.setString(2, target_npm);
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
    	return list_conflict_nimhs;
    }
    
    public int setBasedThsmsData(String based_thsms) {
    	int updated = 0;
    	String acuan_thsms = Tool.returnNextThsmsGivenTpAntara(based_thsms);
    	SearchDbInfoMhs sdim = new SearchDbInfoMhs();
    	String tkn_mhs_aktif = sdim.getMhsAktifVersiPddikti(acuan_thsms);
    	StringTokenizer st =  new StringTokenizer(tkn_mhs_aktif,"`");
    	//System.out.println("mhs_aktif="+st.countTokens());
    	Vector v_sisa_mhs = sdim.getSeluruhMhsYgStatusnyaBelumKeluarFromTheBeginning(based_thsms);
    	if(tkn_mhs_aktif!=null) {
			st = new StringTokenizer(tkn_mhs_aktif,"`");
			while(st.hasMoreTokens()) {
				boolean match = false;
				String npmhs = st.nextToken();
				ListIterator li = v_sisa_mhs.listIterator();
				while(li.hasNext() && !match) {
					String npm = (String)li.next();
					if(npmhs.equalsIgnoreCase(npm)) {
						match = true;
					}
				}
				if(!match) {
					//System.out.println(npmhs + " tidak ada");
				}
			}
			//System.out.println(st.countTokens());
		}
    	if(v_sisa_mhs!=null) {
    		//System.out.println("v_sisa_mhs--"+v_sisa_mhs.size());
    		ListIterator li = v_sisa_mhs.listIterator();
    		while(li.hasNext()) {
    			String npmhs = (String)li.next();
    			if(tkn_mhs_aktif.contains("`"+npmhs+"`")) {
    				li.remove();
    			}
    		}
    		//System.out.println("v_sisa_mhs_final--"+v_sisa_mhs.size());
    	}
    	
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		if(v_sisa_mhs!=null) {
    			stmt = con.prepareStatement("select KDPSTMSMHS,KDJENMSMHS from CIVITAS where NPMHSMSMHS=?");
        		//System.out.println("v_sisa_mhs--"+v_sisa_mhs.size());
        		ListIterator li = v_sisa_mhs.listIterator();
        		while(li.hasNext()) {
        			String npmhs = (String)li.next();
        			stmt.setString(1, npmhs);
        			rs = stmt.executeQuery();
        			rs.next();
        			li.set(npmhs+"`"+rs.getString(1)+"`"+rs.getString(2));
        		}
        		stmt = con.prepareStatement("insert ignore into TRLSM(THSMS,KDPST,NPMHS,STMHS)values(?,?,?,?)");
        		li = v_sisa_mhs.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			
        			st = new StringTokenizer(brs,"`");
        			String npmhs = st.nextToken();
        			String kdpst = st.nextToken();
        			String kdjen = st.nextToken();
        			stmt.setString(1, based_thsms);
        			stmt.setString(2, kdpst);
        			stmt.setString(3, npmhs);
        			if(kdpst.equalsIgnoreCase("UNREG")) {
        				stmt.setString(4, "L");
        			}
        			else {
        				stmt.setString(4, "K");	
        			}
        			
        			updated = updated+stmt.executeUpdate(); 
        			//System.out.println(brs+"="+updated);
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
    
    
    public int setBtstuMhsAktif(String target_thsms) {
    	int updated = 0;
    	Vector v = null, v_lewat_btstu=null;
    	ListIterator li = null, lio = null;
    	SearchDbInfoMhs sdim = new SearchDbInfoMhs();
    	String tkn_mhs_aktif = sdim.getMhsAktifVersiPddikti(target_thsms);
    	if(tkn_mhs_aktif!=null) {
    		StringTokenizer st = new StringTokenizer(tkn_mhs_aktif,"`");	
    		v = new Vector();
    		li = v.listIterator();
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select a.KDPSTMSMHS,a.KDJENMSMHS,SMAWLMSMHS,LAMA_BATAS_STUDI from CIVITAS a inner join BATAS_STUDI_PARAM b on a.ID_OBJ=b.ID_OBJ where NPMHSMSMHS=? order by THSMS limit 1");
        		while(st.hasMoreTokens()) {
        			String npmhs = st.nextToken();
        			stmt.setString(1, npmhs);
        			rs = stmt.executeQuery();
        			rs.next();
        			String kdpst = ""+rs.getString(1);
        			String kdjen = ""+rs.getString(2);
        			String smawl = ""+rs.getString(3);
        			String lama_studi_max = ""+rs.getString(4);
        			//System.out.println(npmhs+"`"+kdjen+"`"+smawl+"`"+lama_studi_max);
        			String thsms_dikeluarin = (Integer.parseInt(smawl.substring(0, 4))+(Integer.parseInt(lama_studi_max)/2))+smawl.substring(4, 5);
        			String btstu = Tool.returnPrevThsmsGivenTpAntara(thsms_dikeluarin);
        			li.add(npmhs+"`"+kdjen+"`"+smawl+"`"+lama_studi_max+"`"+thsms_dikeluarin+"`"+btstu);
        			
        		}
        		//update btstu
        		stmt = con.prepareStatement("update CIVITAS set BTSTUMSMHS=? where NPMHSMSMHS=?");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			//System.out.println("baris = "+brs);
        			st = new StringTokenizer(brs,"`");
        			String npmhs = st.nextToken();
        			String kdjen = st.nextToken();
        			String smawl = st.nextToken();
        			String lama_studi_max = st.nextToken();
        			String thsms_dikeluarin = st.nextToken();
        			String btstu = st.nextToken();
        			stmt.setString(1, btstu);
        			stmt.setString(2, npmhs);
        			updated = updated+stmt.executeUpdate();
        			//System.out.println("update "+brs+" = "+updated);
        			//if(thsms_dikeluarin.equalsIgnoreCase(target_thsms)) {
        			//	if(v_lewat_btstu==null) {
        			//		v_lewat_btstu = new Vector();
        			//		lio = v_lewat_btstu.listIterator();
        			//	}
        			//	lio.add(npmhs+"`"+kdjen+"`"+smawl+"`"+lama_studi_max+"`"+thsms_dikeluarin);
        			//}
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
    
    
    public int setBtstuMhsPerAngkatan(String smawl) {
    	int updated = 0;
    	Vector v = null, v_lewat_btstu=null;
    	ListIterator li = null, lio = null;
    	SearchDbInfoMhs sdim = new SearchDbInfoMhs();
    	
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
        	con = ds.getConnection();
        	
        	stmt = con.prepareStatement("select NPMHSMSMHS from CIVITAS a inner join OBJECT b on a.ID_OBJ=b.ID_OBJ where OBJ_NAME='MHS' and SMAWLMSMHS=?  order by NPMHSMSMHS");
        	//stmt = con.prepareStatement("select NPMHSMSMHS from CIVITAS where SMAWLMSMHS=? and KDPSTMSMHS>'1001' order by NPMHSMSMHS");
        	stmt.setString(1, smawl);
        	rs = stmt.executeQuery();
        	String tkn_npm = null;
        	if(rs.next()) {
        		String npmhs = rs.getString(1);
        		tkn_npm = new String(npmhs);
        		while(rs.next()) {
        			npmhs = rs.getString(1);
            		tkn_npm = tkn_npm+"`"+npmhs;
        		}	
        	}	
        	if(tkn_npm!=null) {
        		StringTokenizer st = new StringTokenizer(tkn_npm,"`");
        		stmt = con.prepareStatement("select a.KDPSTMSMHS,a.KDJENMSMHS,SMAWLMSMHS,LAMA_BATAS_STUDI from CIVITAS a inner join BATAS_STUDI_PARAM b on a.ID_OBJ=b.ID_OBJ where NPMHSMSMHS=? order by THSMS limit 1");
        		while(st.hasMoreTokens()) {
        			String npmhs = st.nextToken();
        			stmt.setString(1, npmhs);
        			rs = stmt.executeQuery();
        			rs.next();
        			String kdpst = ""+rs.getString(1);
        			String kdjen = ""+rs.getString(2);
        			smawl = ""+rs.getString(3);
        			String lama_studi_max = ""+rs.getString(4);
        			//System.out.println(npmhs+"`"+kdjen+"`"+smawl+"`"+lama_studi_max);
        			String thsms_dikeluarin = (Integer.parseInt(smawl.substring(0, 4))+(Integer.parseInt(lama_studi_max)/2))+smawl.substring(4, 5);
        			String btstu = Tool.returnPrevThsmsGivenTpAntara(thsms_dikeluarin);
        			if(v==null) {
        				v =new Vector();
        				li = v.listIterator();
        			}
        			li.add(npmhs+"`"+kdjen+"`"+smawl+"`"+lama_studi_max+"`"+thsms_dikeluarin+"`"+btstu);
        			
        		}
        		//update btstu
        		stmt = con.prepareStatement("update CIVITAS set BTSTUMSMHS=? where NPMHSMSMHS=?");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			//System.out.println("baris = "+brs);
        			st = new StringTokenizer(brs,"`");
        			String npmhs = st.nextToken();
        			String kdjen = st.nextToken();
        			smawl = st.nextToken();
        			String lama_studi_max = st.nextToken();
        			String thsms_dikeluarin = st.nextToken();
        			String btstu = st.nextToken();
        			stmt.setString(1, btstu);
        			stmt.setString(2, npmhs);
        			updated = updated+stmt.executeUpdate();
        			//System.out.println("update "+brs+" = "+updated);
        			//if(thsms_dikeluarin.equalsIgnoreCase(target_thsms)) {
        			//	if(v_lewat_btstu==null) {
        			//		v_lewat_btstu = new Vector();
        			//		lio = v_lewat_btstu.listIterator();
        			//	}
        			//	lio.add(npmhs+"`"+kdjen+"`"+smawl+"`"+lama_studi_max+"`"+thsms_dikeluarin);
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
    	return updated;
	}
    
    
    public int forcedKeluarMhsLewatBtstu(String target_thsms) {
    	int updated = 0;
    	Vector v = null, v_lewat_btstu=null;
    	ListIterator li = null, lio = null;
    	SearchDbInfoMhs sdim = new SearchDbInfoMhs();
    	//mo dikeluarin at target thsms maka yg dicek mhs aktif thsms-1
    	String prev_thsms = Tool.returnPrevThsmsGivenTpAntara(target_thsms);
    	//System.out.println("prev_thsms = "+prev_thsms);
    	String tkn_mhs_aktif = sdim.getMhsAktifVersiPddikti(prev_thsms);
    	if(tkn_mhs_aktif!=null) {
    		StringTokenizer st = new StringTokenizer(tkn_mhs_aktif,"`");	
    		v = new Vector();
    		li = v.listIterator();
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
        		con = ds.getConnection();
        		//stmt = con.prepareStatement("select a.KDPSTMSMHS,a.KDJENMSMHS,SMAWLMSMHS,LAMA_BATAS_STUDI from CIVITAS a inner join BATAS_STUDI_PARAM b on a.ID_OBJ=b.ID_OBJ where NPMHSMSMHS=? order by THSMS limit 1");
        		//cek apa ada status keluar / lulus at thsms-1
        		stmt = con.prepareStatement("select NPMHS from TRLSM where THSMS=? and NPMHS=? and (STMHS='L' or STMHS='D')");
        		//System.out.println("aktif="+st.countTokens());
        		while(st.hasMoreTokens()) {
        			String npmhs = st.nextToken();
        			stmt.setString(1, prev_thsms);
        			stmt.setString(2, npmhs);
        			rs = stmt.executeQuery();
        			if(!rs.next()) {
        				li.add(npmhs);
        			}
        		}
        		if(v!=null) {
        			//belum out
            		//System.out.println("mhs yg belum out = "+v.size());
            		li = v.listIterator();
            		stmt = con.prepareStatement("select BTSTUMSMHS from CIVITAS where NPMHSMSMHS=?");
            		while(li.hasNext()) {
            			String npmhs = (String)li.next();
            			stmt.setString(1, npmhs);
            			rs = stmt.executeQuery();
            			rs.next();
            			String btstu = rs.getString(1);
            			if(btstu.compareToIgnoreCase(target_thsms)>=0) {
            				li.remove();
            			}
            			
            		}
            		//System.out.println("mhs lewat btstu = "+v.size());
            		Vector v_ada_krs = null;
            		ListIterator lia = null;
            		if(v!=null) {
            			//boolean ada_krs = false;
            			//cek yg ada krsnya at thsms dan bukan malaikat
            			//stmt = con.prepareStatement("select MALAIKAT,NPMHSTRNLM from CIVITAS inner join TRNLM on NPMHSMSMHS=NPMHSTRNLM where THSMS")
            			stmt = con.prepareStatement("select NPMHSTRNLM from  TRNLM where THSMSTRNLM=? and NPMHSTRNLM=? limit 1");
                		li = v.listIterator();
                		while(li.hasNext()) {
                			String npmhs = (String)li.next();
                			stmt.setString(1, target_thsms);
                			stmt.setString(2, npmhs);
                			rs = stmt.executeQuery();
                			if(rs.next()) {
                				if(v_ada_krs==null) {
                					lia = v_ada_krs.listIterator();
                				}
                				lia.add(npmhs);
                				//ada_krs = true;
                				li.remove();
                				//System.out.println(npmhs+" ada krs at "+target_thsms);
                			}
                			
                		}
                		
                		if(v!=null) {
                			Vector v_ins = new Vector();
                			ListIterator lins = v_ins.listIterator();
                			//System.out.println("siap insrt = "+v.size());
                			stmt = con.prepareStatement("update TRLSM set STMHS=? where THSMS=? and NPMHS=?");
                    		li = v.listIterator();
                    		int upd = 0, ins = 0;
                    		while(li.hasNext()) {
                    			String npmhs = (String)li.next();
                    			stmt.setString(1, "K");
                    			stmt.setString(2, target_thsms);
                    			stmt.setString(3, npmhs);
                    			int i = stmt.executeUpdate();
                    			upd = upd + i;
                    			if(i<1) {
                    				lins.add(npmhs);
                    			}
                    			else {
                    				//System.out.println("who got updated: "+npmhs);
                    			}
                    			
                    		}
                    		//System.out.println("data updated : "+upd);
                    		if(v_ins.size()>0) {
                    			lins = v_ins.listIterator();
                        		while(lins.hasNext()) {
                        			String npmhs = (String)lins.next();
                        			String kdpst = Checker.getKdpst(npmhs);
                        			lins.set(npmhs+"`"+kdpst);
                        		}	
                    			stmt = con.prepareStatement("insert into TRLSM(THSMS,KDPST,NPMHS,STMHS)values(?,?,?,?)");
                    			lins = v_ins.listIterator();
                        		while(lins.hasNext()) {
                        			String brs = (String)lins.next();
                        			st = new StringTokenizer(brs,"`");
                        			//System.out.println("baris="+brs);
                        			String npmhs = st.nextToken();
                        			String kdpst = st.nextToken();
                        			stmt.setString(1, target_thsms);
                        			stmt.setString(2, kdpst);
                        			stmt.setString(3, npmhs);
                        			stmt.setString(4, "K");
                        			int i = stmt.executeUpdate();
                        			ins = ins + i;
                        			if(i>0) {
                        				lins.remove();
                        			}
                        		}
                        		//System.out.println("data inserted : "+ins);
                        		if(v_ins==null || v_ins.size()<1) {
                        			//System.out.println("data GAGAL inserted : 0");
                        		}
                        		else {
                        			//System.out.println("data GAGAL inserted : "+v_ins.size());
                        		}
                    		}
                    		
                		}
                		if(v_ada_krs!=null) {
                			//System.out.println("ada krs = "+v_ada_krs.size());
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
    	}	 
    	return updated;
    }
}

package beans.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.jdbc.pool.DataSource;
//import org.apache.tomcat.jni.File;

/**
 * Session Bean implementation class AddHocFunction
 */
@Stateless
@LocalBean
public class AddHocFunction {

    /**
     * Default constructor. 
     */
    public AddHocFunction() {
        // TODO Auto-generated constructor stub
    }
    
/*
    public static void finalizeStatusTopikPengajuan(String thsms_pengajuan, String tipe_pengajuan) {
    	//update topik pengajuan
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("update TOPIK_PENGAJUAN set BATAL=?,LOCKED=?,SHOW_AT_TARGET=?,SHOW_AT_CREATOR=? where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=? and TIPE_PENGAJUAN<>?");
			for(int i=0;i<thsms_stmhs.length;i++) {
				if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
					st = new StringTokenizer(thsms_stmhs[i],"`");
					String thsms = st.nextToken();
					String stmhs = st.nextToken();
					//if(stmhs!=null && (stmhs.equalsIgnoreCase("C")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D")||stmhs.equalsIgnoreCase("L"))) {
					if(stmhs!=null && (stmhs.equalsIgnoreCase("C")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D")||stmhs.equalsIgnoreCase("L")||stmhs.equalsIgnoreCase("P"))) {
						stmt.setBoolean(1, false);
						stmt.setBoolean(2, true);
						stmt.setNull(3, java.sql.Types.VARCHAR);
						stmt.setBoolean(4, false);
						stmt.setString(5, thsms);
						stmt.setString(6, npmhs);
						if(stmhs.equalsIgnoreCase("C")) {
							stmt.setString(7, "CUTI");	
						}
						else if(stmhs.equalsIgnoreCase("K")) {
							stmt.setString(7, "KELUAR");
						}
						else if(stmhs.equalsIgnoreCase("D")) {
							stmt.setString(7, "DO");
						}
						else if(stmhs.equalsIgnoreCase("L")) {
							stmt.setString(7, "KELULUSAN");
						}
						else if(stmhs.equalsIgnoreCase("P")) {
							stmt.setString(7, "PINDAH_PRODI");
						}
						
						stmt.executeUpdate();
					}
				}
			}	
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    }
 */   
    public static void finalizePindahProdi(String npm_asal, String npm_prodi_baru, String kdpst_asal, String kode_univ_asal) {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//1. set npm asal at civitas
			stmt = con.prepareStatement("update CIVITAS set ASNIMMSMHS=?,ASPTIMSMHS=?,ASPSTMSMHS=? where NPMHSMSMHS=?");
			stmt.setString(1, npm_asal);
			stmt.setString(2, kode_univ_asal);
			stmt.setString(3, kdpst_asal);
			stmt.setString(4, npm_prodi_baru);
			//System.out.println(npm_asal+"-"+kode_univ_asal+"-"+kdpst_asal+"-"+npm_prodi_baru);
			stmt.executeUpdate();
			//2. set status keluar untuk npm lama
			//3. set status aktif pada prodi baru
			//4. copy pembayaran dari yg lama ke npm baru
			//5. buat transaksi creddit pada npm lama sehingga balance 0;
			//6. update password
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	
    	
    }
    
    public static void deleteNpmYgTidakAdaDiCivitas(String nm_tabel) {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//1. set npm asal at civitas
			stmt = con.prepareStatement("select CREATOR_NPM from "+nm_tabel+" left join CIVITAS on CREATOR_NPM=NPMHSMSMHS  where NPMHSMSMHS is NULL;");
			rs = stmt.executeQuery();
			Vector v = new Vector();
			ListIterator li = v.listIterator();
			while(rs.next()) {
				String npmhs = rs.getString(1);
				li.add(npmhs);
			}
			if(v.size()>0) {
				stmt = con.prepareStatement("delete from "+nm_tabel+" where CREATOR_NPM=?");
				li = v.listIterator();
				while(li.hasNext()) {
					String npmhs = (String)li.next();
					stmt.setString(1, npmhs);
					int i = stmt.executeUpdate();
					//System.out.println("deleted "+npmhs+" = "+i );
				}	
			}
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	
    	
    }
    

    
    public static Vector addInfoUsrPwd(Vector vSearchDbInfoDaftarUlang_getListMhsYgSdhMengajukanPengajuanDaftarUlang) {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector vf = null;
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		
			//1. set npm asal at civitas
			if(vSearchDbInfoDaftarUlang_getListMhsYgSdhMengajukanPengajuanDaftarUlang!=null && vSearchDbInfoDaftarUlang_getListMhsYgSdhMengajukanPengajuanDaftarUlang.size()>0) {
				//System.out.println("vlist="+v_list.size());
				vf =new Vector();
				ListIterator lif = vf.listIterator();
				ListIterator li = vSearchDbInfoDaftarUlang_getListMhsYgSdhMengajukanPengajuanDaftarUlang.listIterator();
				ListIterator lit = null;
				Context initContext  = new InitialContext();
				Context envContext  = (Context)initContext.lookup("java:/comp/env");
				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
				con = ds.getConnection();
				stmt = con.prepareStatement("select USR_NAME,USR_PWD from USR_DAT where ID=?");
				while(li.hasNext()) {
					String kmp = (String)li.next();
					lif.add(kmp);
					Vector vtmp = (Vector)li.next();
					lit = vtmp.listIterator();
					while(lit.hasNext()) {
						String idobj = (String)lit.next();
						String list_mhs = (String)lit.next();
						StringTokenizer st = new StringTokenizer(list_mhs,"`");
						//System.out.println("list_mhs="+list_mhs);
						if(st.countTokens()>1) {
							list_mhs = "null";
							while(st.hasMoreTokens()) {
								if(list_mhs.equalsIgnoreCase("null")) {
									list_mhs = "";
								}
								String id = st.nextToken();
								String smawl = st.nextToken();
								String npmhs = st.nextToken();
								String nmmhs = st.nextToken();
								stmt.setLong(1, Long.parseLong(id));
								rs = stmt.executeQuery();
								String usr = "null";
								String pwd = "null";
								if(rs.next()) {
									usr = rs.getString(1);
									pwd = rs.getString(2);
								}
								list_mhs = list_mhs+"`"+id+"`"+smawl+"`"+npmhs+"`"+nmmhs+"`"+usr+"`"+pwd;
							}
							lit.set(list_mhs);
							//System.out.println("set ="+list_mhs);
						}
						
					}
					li.set(vtmp);
					
					
				}
			}
			//3. set status aktif pada prodi baru
			//4. copy pembayaran dari yg lama ke npm baru
			//5. buat transaksi creddit pada npm lama sehingga balance 0;
			//6. update password
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return vSearchDbInfoDaftarUlang_getListMhsYgSdhMengajukanPengajuanDaftarUlang;
    	
    }
    
    public static Vector findIdobjKdpstError() {
    	Vector vf = null;
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//1. set npm asal at civitas
			stmt = con.prepareStatement("select ID_OBJ,KDPSTMSMHS,NPMHSMSMHS from CIVITAS");
			rs=stmt.executeQuery();
			Vector v_list_civitas = null;
			if(rs.next()) {
				v_list_civitas = new Vector();
				ListIterator li = v_list_civitas.listIterator();
				do {
					String id = ""+rs.getInt(1);
					String kdpst = ""+rs.getString(2);
					li.add(id+"`"+kdpst);
				}
				while(rs.next());
				v_list_civitas = Tool.removeDuplicateFromVector(v_list_civitas);
			}
			
			if(v_list_civitas!=null && v_list_civitas.size()>0) {
				stmt = con.prepareStatement("select KDPST from OBJECT where ID_OBJ=?");
				ListIterator li = v_list_civitas.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String id = st.nextToken();
					String kdpst = st.nextToken();
					stmt.setInt(1, Integer.parseInt(id));
					rs = stmt.executeQuery();
					rs.next();
					String obj_kdpst = rs.getString(1);
					if(obj_kdpst.equalsIgnoreCase(kdpst)) {
						li.remove();
					}
					else {
						li.set(brs+"`"+obj_kdpst);
					}
				}
				if(v_list_civitas!=null && v_list_civitas.size()>0) {
					vf = new Vector(v_list_civitas);
					ListIterator lif = vf.listIterator();
					stmt = con.prepareStatement("select NPMHSMSMHS,NMMHSMSMHS from CIVITAS where ID_OBJ=? and KDPSTMSMHS=?");
					while(lif.hasNext()) {
						String brs = (String)lif.next();
						StringTokenizer st = new StringTokenizer(brs,"`");
						String id = st.nextToken();
						String kdpst = st.nextToken();
						String obj_kdpst = st.nextToken();
						stmt.setInt(1, Integer.parseInt(id));
						stmt.setString(2, kdpst);
						rs = stmt.executeQuery();
						while(rs.next()) {
							String npm = rs.getString(1);
							String nmm = rs.getString(2);
							lif.set(brs+"`"+npm+"`"+nmm);
						}
					}
				}
				
			}
			//2. set status keluar untuk npm lama
			//3. set status aktif pada prodi baru
			//4. copy pembayaran dari yg lama ke npm baru
			//5. buat transaksi creddit pada npm lama sehingga balance 0;
			//6. update password
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	
    	return vf;
    }
    
    
    
    public static void updateTableRule(String full_table_rule_name) {
    	//System.out.println(full_table_rule_name);
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
			con = ds.getConnection();
        	stmt = con.prepareStatement("select * from "+full_table_rule_name+" where THSMS=? and TKN_JABATAN_VERIFICATOR is not null");
        	//String thsms_now = Checker.getThsmsNow();
        	String thsms_her = Checker.getThsmsHeregistrasi();
        	stmt.setString(1, thsms_her);
        	//stmt.setString(2, thsms_her);
        	rs = stmt.executeQuery();
        	Vector v = new Vector();
        	ListIterator li = v.listIterator();
        	while(rs.next()) {
        		String thsms = rs.getString("THSMS");
        		String kdpst = rs.getString("KDPST");
        		String tkn_jabatan = rs.getString("TKN_JABATAN_VERIFICATOR");
        		String urutan = ""+rs.getBoolean("URUTAN");
        		String kmp = rs.getString("KODE_KAMPUS");
        		li.add(thsms+"`"+kdpst+"`"+tkn_jabatan+"`"+urutan+"`"+kmp);
        	}
        	li = v.listIterator();
        	stmt = con.prepareStatement("select * from JABATAN inner join STRUKTURAL on NAMA_JABATAN=NM_JOB where KDPST=? and KDKMP=? and (NAMA_JABATAN=? or SINGKATAN=?) and STRUKTURAL.AKTIF=?");
        	//System.out.println("vsie="+v.size());
        	while(li.hasNext()) {
        		String brs = (String)li.next();
        		//System.out.println("brs1="+brs);
        		StringTokenizer st = new StringTokenizer(brs,"`");
        		String thsms = st.nextToken();
        		String kdpst = st.nextToken();
        		String tkn_jabatan = st.nextToken();
        		tkn_jabatan = tkn_jabatan.replace("][", "`");
        		tkn_jabatan = tkn_jabatan.replace("]", "");
        		tkn_jabatan = tkn_jabatan.replace("[", "");
        		String urutan = st.nextToken();
        		String kmp = st.nextToken();
        		String tmp = "";
        		st = new StringTokenizer(tkn_jabatan,"`");
        		while(st.hasMoreTokens()) {
        			String job_nm = st.nextToken();
        			stmt.setString(1,kdpst);
            		stmt.setString(2,kmp);
            		stmt.setString(3,job_nm);
            		stmt.setString(4,job_nm);
            		stmt.setBoolean(5,true);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			long objid = rs.getLong("OBJID");
            			tmp = tmp +"["+objid+"]";
            		}
            		else {
            			tmp = tmp +"[null]";
            		}
        		}
        		li.set(brs+"`"+tmp);
        		//if(full_table_rule_name.equalsIgnoreCase("KELAS_PERKULIAHAN_RULES")) {
        		//	//System.out.println("kelas=="+brs+"`"+tmp);
        		//}
        		//System.out.println(brs+"`"+tmp);
        	}
        	li = v.listIterator();
        	stmt = con.prepareStatement("update "+full_table_rule_name+" set TKN_JABATAN_VERIFICATOR=?,TKN_VERIFICATOR_ID=? where THSMS=? and KDPST=? and KODE_KAMPUS=?");
        	while(li.hasNext()) {
        		String brs = (String)li.next();
        		StringTokenizer st = new StringTokenizer(brs,"`");
        		String thsms = st.nextToken();
        		String kdpst = st.nextToken();
        		String tkn_jabatan = st.nextToken();
        		if(!Checker.isStringNullOrEmpty(tkn_jabatan)) {
        			if(!tkn_jabatan.endsWith("]")) {
        				tkn_jabatan = tkn_jabatan.trim()+"]";
        			}
        		}
        		String urutan = st.nextToken();
        		String kmp = st.nextToken();
        		String tkn_id = st.nextToken();
        		if(!Checker.isStringNullOrEmpty(tkn_id)) {
        			if(!tkn_id.endsWith("]")) {
        				tkn_id = tkn_id.trim()+"]";
        			}
        		}
        		stmt.setString(1, tkn_jabatan);
        		stmt.setString(2, tkn_id);
        		stmt.setString(3, thsms);
        		stmt.setString(4, kdpst);
        		stmt.setString(5, kmp);
        		stmt.executeUpdate();
        	}
        	
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	
    	
    }
    
    /*
     * HARUSNYA BELUM PERNAH DIPAKE - UNFINISHED
     */
    public static int hitungTotSemesterDariSamwlSampaiTargetThsmsDikurangNonAktifTrlsm(String smawl, String target_thsms) {
    	int tot_sms = 0;
    	while(smawl.compareToIgnoreCase(target_thsms)<=0) {
    		tot_sms++;
    		smawl = Tool.returnNextThsmsGivenTpAntara(smawl);
    	}
    	return tot_sms;
    }
    
    public static Vector hitungTotSemesterDariSamwlSampaiTargetThsmsDikurangNonAktifTrlsm_v1(String tkn_npm, String target_thsms) {
    	String based_thsms_mulai_pencatatan = "20152";
    	StringTokenizer st = null;
    	String url=null;     
    	Vector v = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	if(!Checker.isStringNullOrEmpty(tkn_npm)) {
    		v = new Vector();
    		ListIterator li = v.listIterator();
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    			con = ds.getConnection();
            	stmt = con.prepareStatement("select SMAWLMSMHS,MALAIKAT from CIVITAS where NPMHSMSMHS=?");
            	st = new StringTokenizer(tkn_npm,"`");
            	while(st.hasMoreTokens()) {
            		String npmhs = st.nextToken();
            		stmt.setString(1, npmhs);
            		rs = stmt.executeQuery();
            		rs.next();
            		String smawl = rs.getString(1);
            		String malaikat = ""+rs.getBoolean(2); 
            		int tot_sms = 0;
            		if(!Checker.isStringNullOrEmpty(smawl)) {
            			
            	    	while(smawl.compareToIgnoreCase(target_thsms)<=0) {
            	    		tot_sms++;
            	    		smawl = Tool.returnNextThsmsGivenTpAntara(smawl);
            	    	}
            		}
            		li.add(npmhs+"`"+malaikat+"`"+smawl+"`"+tot_sms);
            	}
            	
            	
            	//cek berapa sms non aktif;
            	
            	if(v!=null) {
            		
            		stmt = con.prepareStatement("select THSMS from TRLSM where NPMHS=? and (STMHS='C' or STMHS='N') and THSMS>=? and THSMS<?");
            		li = v.listIterator();
            		while(li.hasNext()) {
            			String tkn_thsms_non_aktif = "null";
            			String brs = (String)li.next();
            			st = new StringTokenizer(brs,"`");
            			String npmhs = st.nextToken();
            			String angel = st.nextToken();
            			String smawl = st.nextToken();
            			String ttsms = st.nextToken();
            			
            			stmt.setString(1, npmhs);
            			stmt.setString(2, based_thsms_mulai_pencatatan);
            			stmt.setString(3, target_thsms);
            			int sms_non_aktif=0;
            			rs = stmt.executeQuery();
            			if(rs.next()) {
            				tkn_thsms_non_aktif = "";
            				boolean first = true;
            				do {
            					String thsms = rs.getString(1);
            					if(first) {
            						first = false;
            						tkn_thsms_non_aktif = new String(thsms);
            					}
            					else {
            						tkn_thsms_non_aktif = tkn_thsms_non_aktif+","+new String(thsms);
            					}
            					sms_non_aktif++;
            				}
            				while(rs.next());
            			}
            			//li.set(brs+"`"+sms_non_aktif+"`"+tkn_thsms_non_aktif);
            			
            			li.set(npmhs+"`"+(Integer.parseInt(ttsms)-sms_non_aktif));
            			
            		}
            	}
    		}
        	catch(ConcurrentModificationException e) {
        		e.printStackTrace();
        	}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
        	catch(Exception e) {
        		e.printStackTrace();
        	}
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}
    	}
    	return v;
    }
    
    public static void initializeTableRule(String full_table_rule_name, boolean urutan) {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
			con = ds.getConnection();
        	//stmt = con.prepareStatement("select * from "+full_table_rule_name+" where THSMS=? or THSMS=?");
			String thsms_her = Checker.getThsmsHeregistrasi();
        	
        	stmt = con.prepareStatement("select * from OBJECT where OBJ_NICKNAME like ?")	;
        	stmt.setString(1, "MHS%");
        	rs = stmt.executeQuery();
        	Vector v = new Vector();
        	ListIterator li = v.listIterator();
        	//String sql_tmp = null;
        	while(rs.next()) {
        		
        		String idobj = ""+rs.getLong("ID_OBJ");
        		String kdpst = rs.getString("KDPST");
        		String kmp = rs.getString("KODE_KAMPUS_DOMISILI");
        		li.add(idobj+"`"+kdpst+"`"+kmp);
        		
        	}
        	
        	stmt = con.prepareStatement("select * from "+full_table_rule_name+" where THSMS=?");
        	Vector v_delete = new Vector();
        	ListIterator lid = v_delete.listIterator();
        	stmt.setString(1,thsms_her);
        	rs = stmt.executeQuery();
        	while(rs.next()) {
        		String kdpst = ""+rs.getString("KDPST");
        		String kode = ""+rs.getString("KODE_KAMPUS");
        		lid.add(kdpst+"`"+kode);
        		//System.out.println("add "+kdpst+"`"+kode);
        	}
        	if(v_delete.size()>0) {
        		//System.out.println(full_table_rule_name+"-v_delete size="+v_delete.size());
        		stmt = con.prepareStatement("select * from OBJECT where KDPST=? and KODE_KAMPUS_DOMISILI=?");
        		lid = v_delete.listIterator();
        		while(lid.hasNext()) {
        			String brs = (String)lid.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String kdpst = st.nextToken();
        			String kode = st.nextToken();
        			stmt.setString(1, kdpst);
        			stmt.setString(2, kode);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				lid.remove();
        				//System.out.println("remove "+kdpst+"`"+kode);
        			}
        		}
        		if(v_delete.size()>0) {
        			//System.out.println("v_delete siz2e="+v_delete.size());
        			lid = v_delete.listIterator();
        			stmt = con.prepareStatement("delete from "+full_table_rule_name+" where THSMS=? and KDPST=? and KODE_KAMPUS=?");
        			while(lid.hasNext()) {
            			String brs = (String)lid.next();
            			StringTokenizer st = new StringTokenizer(brs,"`");
            			String kdpst = st.nextToken();
            			String kode = st.nextToken();
            			
            			stmt.setString(1, thsms_her);
            			stmt.setString(2, kdpst);
            			stmt.setString(3, kode);
            			//System.out.println("deltet "+kdpst+"`"+kode);
            			stmt.executeUpdate();
            		}
        		}
        	}
        	String thsms = Checker.getThsmsNow();		
        	li = v.listIterator();
        	stmt = con.prepareStatement("update "+full_table_rule_name+" set URUTAN=? where THSMS=? and KDPST=? and KODE_KAMPUS=?");
        	while(li.hasNext()) {
        		String brs = (String)li.next();
        		StringTokenizer st = new StringTokenizer(brs,"`");
        		String idobj = st.nextToken();
        		String kdpst = st.nextToken();
        		String kmp = st.nextToken();
        		stmt.setBoolean(1, urutan);
        		stmt.setString(2, thsms);
        		stmt.setString(3, kdpst);
        		stmt.setString(4, kmp);
        		int i = stmt.executeUpdate();
        		if(i>0) {
        			li.remove();
        		}
        	}
        	li = v.listIterator();
        	stmt = con.prepareStatement("insert into "+full_table_rule_name+" (THSMS,KDPST,URUTAN,KODE_KAMPUS)VALUES(?,?,?,?)");
        	while(li.hasNext()) {
        		String brs = (String)li.next();
        		StringTokenizer st = new StringTokenizer(brs,"`");
        		String idobj = st.nextToken();
        		String kdpst = st.nextToken();
        		String kmp = st.nextToken();
        		
        		stmt.setString(1, thsms);
        		stmt.setString(2, kdpst);
        		stmt.setBoolean(3, urutan);
        		stmt.setString(4, kmp);
        		stmt.executeUpdate();       
        	}
        	
        	
        	thsms = Checker.getThsmsHeregistrasi();		
        	li = v.listIterator();
        	stmt = con.prepareStatement("update "+full_table_rule_name+" set URUTAN=? where THSMS=? and KDPST=? and KODE_KAMPUS=?");
        	while(li.hasNext()) {
        		String brs = (String)li.next();
        		StringTokenizer st = new StringTokenizer(brs,"`");
        		String idobj = st.nextToken();
        		String kdpst = st.nextToken();
        		String kmp = st.nextToken();
        		stmt.setBoolean(1, urutan);
        		stmt.setString(2, thsms);
        		stmt.setString(3, kdpst);
        		stmt.setString(4, kmp);
        		int i = stmt.executeUpdate();
        		if(i>0) {
        			li.remove();
        		}
        	}
        	li = v.listIterator();
        	stmt = con.prepareStatement("insert into "+full_table_rule_name+" (THSMS,KDPST,URUTAN,KODE_KAMPUS)VALUES(?,?,?,?)");
        	while(li.hasNext()) {
        		String brs = (String)li.next();
        		StringTokenizer st = new StringTokenizer(brs,"`");
        		String idobj = st.nextToken();
        		String kdpst = st.nextToken();
        		String kmp = st.nextToken();
        		
        		stmt.setString(1, thsms);
        		stmt.setString(2, kdpst);
        		stmt.setBoolean(3, urutan);
        		stmt.setString(4, kmp);
        		stmt.executeUpdate();       
        	}
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	
    	
    }
    
    public static Vector addInfoKdkmk(Vector v) {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Long uid = null;
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	if(v!=null && v.size()>0) {
    		ListIterator li = v.listIterator();
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			stmt = con.prepareStatement("select KDKMKMAKUL from MAKUL where IDKMKMAKUL=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"||");
    				st.nextToken();//nakmk1
    				st.nextToken();//smsmk1
    				st.nextToken();//cmd1
    				st.nextToken();//idkur1
    				String idkmk = st.nextToken();//idkmk1
    				stmt.setLong(1,Long.parseLong(idkmk));
    				rs = stmt.executeQuery();
    				rs.next();
    				String kdkmk = rs.getString("KDKMKMAKUL");
    				li.set(brs+"||"+kdkmk);
    			}
    			
    			
        	}
        	catch(ConcurrentModificationException e) {
        		e.printStackTrace();
        	}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
        	catch(Exception e) {
        		e.printStackTrace();
        	}
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}
    	}
    	
    	return v;
    }
    
   public static void syncTabelDaftarUlangWithHeregistrasiRule() {
    	String thsms_her = Checker.getThsmsHeregistrasi();
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Long uid = null;
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
			con = ds.getConnection();
			
			stmt = con.prepareStatement("select ID_OBJ from DAFTAR_ULANG where THSMS=? and TKN_ID_APPROVAL like ?");
			stmt.setString(1, thsms_her);
			stmt.setString(2, "%null%");
			rs = stmt.executeQuery();
			Vector v = null;
			ListIterator li = null;
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					int idobj = rs.getInt("ID_OBJ");
					li.add(""+idobj);	
				}
				while(rs.next());
			}
			if(v!=null) {
				v = Tool.removeDuplicateFromVector(v);
				li = v.listIterator();
				//String list_info="";
				while(li.hasNext()) {
					String target_id = (String)li.next();
					String prodi_kmp = Getter.getKodeProdiDanKampus(Integer.parseInt(target_id));
					li.set(target_id+"`"+prodi_kmp);
				}
				
				stmt = con.prepareStatement("select TKN_JABATAN_VERIFICATOR,TKN_VERIFICATOR_ID from HEREGISTRASI_RULES where THSMS=? and KDPST=? and KODE_KAMPUS=?");
				li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String id = st.nextToken();
					String prodi = st.nextToken();
					String kmp = st.nextToken();
					stmt.setString(1, thsms_her);
					stmt.setString(2, prodi);
					stmt.setString(3, kmp);
					rs = stmt.executeQuery();
					if(rs.next()) {
						String jab = rs.getString(1);
						if(jab==null||Checker.isStringNullOrEmpty(jab)) {
							jab = "[null]";
						}
						String list_id = rs.getString(2);
						if(list_id==null||Checker.isStringNullOrEmpty(list_id)) {
							list_id = "[null]";
						}
						li.set(brs+"`"+jab+"`"+list_id);
					}
					else {
						li.set(brs+"`[null]`[null]");
					}
				}
				stmt = con.prepareStatement("update DAFTAR_ULANG set TKN_ID_APPROVAL=?,TKN_JABATAN_APPROVAL=? where THSMS=? and ID_OBJ=?");
				li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String id = st.nextToken();
					String prodi = st.nextToken();
					String kmp = st.nextToken();
					String tkn_jab = st.nextToken();
					String tkn_id = st.nextToken();
					stmt.setString(1, tkn_id);
					stmt.setString(2, tkn_jab);
					stmt.setString(3, thsms_her);
					stmt.setInt(4, Integer.parseInt(id));
					stmt.executeUpdate();
				}	
				
			}
			

    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	
    	//return v;
    }
    
    
    public static Vector addInfoKelasEmpty(Vector vGab) {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Long uid = null;
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	if(vGab!=null && vGab.size()>0) {
    		ListIterator li = vGab.listIterator();
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			stmt = con.prepareStatement("select NPMHSTRNLM from TRNLM where CLASS_POOL_UNIQUE_ID=? limit 1");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"$");
    				
    				String nakmk1 = st.nextToken();
    				String idkmk1 = st.nextToken();
    				String idkur1 = st.nextToken();
    				String kdkmk1 = st.nextToken();
    				String thsms1 = st.nextToken();
    				String kdpst1 = st.nextToken();
    				String shift1 = st.nextToken();
    				String norutKlsPll1 = st.nextToken();
    				String initNpmInput1 = st.nextToken();
    				String latestNpmUpdate1 = st.nextToken();
    				String latesStatusInfo1 = st.nextToken();
    				String currAvailStatus1 = st.nextToken();
    				String locked1 = st.nextToken();
    				String npmdos1 = st.nextToken();
    				String nodos1 = st.nextToken();
    				String npmasdos1 = st.nextToken();
    				String noasdos1 = st.nextToken();
    				String canceled1 = st.nextToken();
    				String kodeKelas1 = st.nextToken();
    				String kodeRuang1 = st.nextToken();
    				String kodeGedung1 = st.nextToken();
    				String kodeKampus1 = st.nextToken();
    				String tknHrTime1 = st.nextToken();
    				String nmdos1 = st.nextToken();
    				String nmasdos1 = st.nextToken();
    				String enrolled1 = st.nextToken();
    				String maxEnrolled1 = st.nextToken();
    				String minEnrolled1 = st.nextToken();
    				String subKeterKdkmk1 = st.nextToken();
    				String initReqTime1 = st.nextToken();
    				String tknNpmApr1 = st.nextToken();
    				String tknAprTime1 = st.nextToken();
    				String targetTtmhs1 = st.nextToken();
    				String passed1 = st.nextToken();
    				String rejected1 = st.nextToken();
    				String konsen1 = st.nextToken();
    				String nmpst1 = st.nextToken();
    				String kodeGabungan = st.nextToken();
    				String cuid1 = st.nextToken();
    				stmt.setLong(1, Long.parseLong(cuid1));
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					li.set(brs+"$false");
    				}
    				else {
    					li.set(brs+"$true");
    				}
    				
    			}
    			
    			
        	}
        	catch(ConcurrentModificationException e) {
        		e.printStackTrace();
        	}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
        	catch(Exception e) {
        		e.printStackTrace();
        	}
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}
    	}
    	
    	return vGab;
    }

    
    public static Vector modifHistKrsForMenuEdit(Vector vHistKrsKhs, String tkn_allow_shift_kelas) {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Long uid = null;
    	String thsms_now = Checker.getThsmsKrs();
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	if(vHistKrsKhs!=null && vHistKrsKhs.size()>0) {
    		ListIterator li = vHistKrsKhs.listIterator();
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			stmt = con.prepareStatement("select * from CLASS_POOL where THSMS=? and IDKMK=? and CANCELED=?");
    			while(li.hasNext()) {
    				//String brs = (String) li.next();
    				String thsms = (String) li.next();
					String kdkmk = (String) li.next();
					String nakmk = (String) li.next();
					String nlakh = (String) li.next();
					String bobot = (String) li.next();
					String sksmk = (String) li.next();
					String kelas = (String) li.next();
					String sksem = (String) li.next();
					String nlips = (String) li.next();
					String skstt = (String) li.next();
					String nlipk = (String) li.next();	
					String shift = (String) li.next();	
					String krsdown = (String) li.next();//tambahan baru
					String khsdown = (String) li.next();//tambahan baru
					String bakprove = (String) li.next();//tambahan baru
					String paprove = (String) li.next();//tambahan baru
					String note = (String) li.next();//tambahan baru
					String lock = (String) li.next();//tambahan baru
					String baukprove = (String) li.next();//tambahan baru
					//tambahan
					String idkmk = (String) li.next();
					String addReq = (String) li.next();
					String drpReq = (String) li.next();
					String npmPa = (String) li.next();
					String npmBak = (String) li.next();
					String npmBaa = (String) li.next();
					String npmBauk = (String) li.next();
					String baaProve = (String) li.next();
					String ktuProve = (String) li.next();
					String dknProve = (String) li.next();
					String npmKtu = (String) li.next();
					String npmDekan = (String) li.next();
					String lockMhs = (String) li.next();
					String kodeKampus = (String) li.next();
					String cuid = (String) li.next();
					
					stmt.setString(1, thsms_now);
					stmt.setLong(2, Long.parseLong(idkmk));
					stmt.setBoolean(3, false);
					rs = stmt.executeQuery();
					boolean alternative_cuid_avail = false;
					while(rs.next()) {
						String kdpst_ = ""+rs.getString("KDPST");
						String shift_ = ""+rs.getString("SHIFT");
						String nopll_ = ""+rs.getString("NORUT_KELAS_PARALEL");
						String npmdos_ = ""+rs.getString("NPMDOS");
						String nodos_ = ""+rs.getString("NODOS");
						String npmasdos_ = ""+rs.getString("NPMASDOS");
						String noasdos_ = ""+rs.getString("NOASDOS");
						String kmp_ = ""+rs.getString("KODE_KAMPUS");
						String nmmdos_ = ""+rs.getString("NMMDOS");
						String nmmasdos_ = ""+rs.getString("NMMASDOS");
						String sub_keter_kdkmk_ = ""+rs.getString("SUB_KETER_KDKMK");
						String cuid_ = ""+rs.getLong("UNIQUE_ID");
						if(tkn_allow_shift_kelas.contains(shift_)) {
							cuid = cuid+"`"+kdpst_+"`"+shift_+"`"+nopll_+"`"+npmdos_+"`"+nodos_+"`"+npmasdos_+"`"+noasdos_+"`"+kmp_+"`"+nmmdos_+"`"+nmmasdos_+"`"+sub_keter_kdkmk_+"`"+cuid_;	
							alternative_cuid_avail = true;
						}
						
					}
					if(alternative_cuid_avail) {
						li.set(cuid);	
					}
					
					String npmdos = (String) li.next();
					String nodos = (String) li.next();
					String npmasdos = (String) li.next();
					String noasdos = (String) li.next();
					String nmmdos = (String) li.next();
					String nmmasdos = (String) li.next(); 
					    				//System.out.println("-- "+cuid);
    			}
        	}
        	catch(ConcurrentModificationException e) {
        		e.printStackTrace();
        	}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
        	catch(Exception e) {
        		e.printStackTrace();
        	}
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}
    	}
    	
    	return vHistKrsKhs;
    }
    
    public static void getListObjIdYgBlumAdaDiPilihanCakupanKelas(String target_thsms) {
    	String []tipe=null;
    	Vector v = new Vector();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get list obj mhs
    		stmt = con.prepareStatement("SELECT * from OBJECT where OBJ_NAME=?");
    		stmt.setString(1,"MHS");
    		rs = stmt.executeQuery();
    		ListIterator li = v.listIterator();
    		while(rs.next()) {
    			String id_obj = ""+rs.getInt("ID_OBJ");
    			String kdpst = ""+rs.getString("KDPST");
    			String kd_kmp = ""+rs.getString("KODE_KAMPUS_DOMISILI");
    			li.add(id_obj+"`"+kdpst+"`"+kd_kmp);
    		}
    		
    		stmt = con.prepareStatement("select * from PILIH_KELAS_RULES where THSMS=? and ID_OBJ_MHS=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs  = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String id_obj = st.nextToken();
    			String kdpst = st.nextToken();
    			String kd_kmp = st.nextToken();
    			stmt.setString(1, target_thsms);
    			stmt.setLong(2, Long.parseLong(id_obj));
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				li.remove();
    			}
    		}
    		
    		stmt = con.prepareStatement("insert into PILIH_KELAS_RULES (THSMS,KDPST,ID_OBJ_MHS,KODE_KAMPUS)values(?,?,?,?)");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			while(li.hasNext()) {
        			String brs  = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String id_obj = st.nextToken();
        			String kdpst = st.nextToken();
        			String kd_kmp = st.nextToken();
        			stmt.setString(1, target_thsms);
        			stmt.setString(2, kdpst);
        			stmt.setLong(3, Long.parseLong(id_obj));
        			stmt.setString(4, kd_kmp);
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
    	//return tipe;
    }
    
    
    public static String getAndSyncStmhsBetweenTrlsmAndMsmhs(String kdpst,String npmhs) {
    	return getAndSyncStmhsBetweenTrlsmAndMsmhs_v1(kdpst,npmhs);
    	/*
    	 * STATUS STMHSMSMHS ADALAH KUNCI UNTUK MENENTUKAN SCKOPE AKSES, SEPERTI INS KRS,DSB
    	 
    	String status_akhir = null;
    	String thsms_now = Checker.getThsmsNow();
    	//String thsms_krs = Checker.getThsmsKrs();
    	String psn = Checker.sudahDaftarUlang(kdpst, npmhs);//menggunakan thsms_heregistrasi
    	boolean sdu = false;
    	if(psn==null) {
    		sdu = true;
    	}
    	//System.out.println("sdu="+sdu);
    	String thsms_her = Checker.getThsmsHeregistrasi();
    	//System.out.println("thsms_her="+thsms_her);
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get curr stmhsmsmhs from civitas table
    		stmt = con.prepareStatement("SELECT STMHSMSMHS from CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1,npmhs);
    		rs = stmt.executeQuery();
    		rs.next();
    		String stmhsmsmhs = ""+rs.getString("STMHSMSMHS");
    		
    		
    		String latest_thsmstrlsm = null;
    		String latest_stmhstrlsm = null;
    		
    		
    		//cek status terakhir di trlsm apa pernah lulus
    		stmt = con.prepareStatement("select * from TRLSM where NPMHS=? order by THSMS ");
    		stmt.setString(1, npmhs);
    		rs = stmt.executeQuery();
    		boolean stop = false;
    		if(rs.next() && !stop) {
    			latest_thsmstrlsm = new String(""+rs.getString("THSMS"));
    			latest_stmhstrlsm = new String(""+rs.getString("STMHS"));
    			if(latest_stmhstrlsm.equalsIgnoreCase("D")||latest_stmhstrlsm.equalsIgnoreCase("K")||latest_stmhstrlsm.equalsIgnoreCase("L")) {
    				stop = true;
    			}
    		}
    		
    		//}
    		//cek status terakhir aktif dari trnlm tidak dari heregistrasi
    		String latest_thsmstrnlm = null;
    		stmt = con.prepareStatement("select THSMSTRNLM from TRNLM where NPMHSTRNLM=? order by THSMSTRNLM desc limit 1");
    		stmt.setString(1, npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			latest_thsmstrnlm = new String(""+rs.getString("THSMSTRNLM"));
    		}
    	
    		
    		//if(stmhsmsmhs.equalsIgnoreCase("null")) {
    		//System.out.println("q"+latest_thsmstrlsm);
    		//System.out.println("w"+latest_stmhstrlsm);
    		//System.out.println("e"+latest_thsmstrnlm);
    		if(true) {
    			//System.out.println("1");
    			//belum ada status di CIVITAS
    			//1. cek latest trlsm apa pernah keluar
    			if(latest_stmhstrlsm==null) {
    				//System.out.println("2");
    				//belum ada record di trlsm
    				if(latest_thsmstrnlm==null) {
    					//System.out.println("3");
    					//juga tidak ada record krs
    					if(sdu) {
    						//System.out.println("4");
    						//tapi sudah daftar ulang - jadi set aktif
    						//set status A
        					stmt = con.prepareStatement("update CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
        					stmt.setString(1, "A");
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        					status_akhir = new String("A");
    					}
    					else {
    						//set status N
    						//System.out.println("5");
        					stmt = con.prepareStatement("update CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
        					stmt.setString(1, "N");
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        					status_akhir = new String("N");
    					}
    					
    				}
    				else {
    					//ada record krs
    					//System.out.println("6");
    					if(latest_thsmstrnlm.compareToIgnoreCase(thsms_now)>=1 || sdu) {
    						//System.out.println("7");
    						stmt = con.prepareStatement("update CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
        					stmt.setString(1, "A");
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        					status_akhir = new String("A");
    					}
    					else {
    						//System.out.println("8");
    						//krs terakhir < thsms now
    						//set status N
        					stmt = con.prepareStatement("update CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
        					stmt.setString(1, "N");
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        					status_akhir = new String("N");
    					}
    				}
    			}
    			else {
    				//ada reccord di trlsm
    				//System.out.println("9");
    				if(latest_stmhstrlsm.equalsIgnoreCase("K")||latest_stmhstrlsm.equalsIgnoreCase("D")) {
    					//System.out.println("10");
    					//status terakhir keluar
    					//update status CIVITAS
    					stmt = con.prepareStatement("update CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
    					stmt.setString(1, latest_stmhstrlsm);
    					stmt.setString(2, npmhs);
    					stmt.executeUpdate();
    					//tinggal mikir return valuenya - looking fo errrors
    					if(latest_thsmstrnlm==null) {
    						//System.out.println("11");
    						//ngga ada record krs -,no error
    						status_akhir = new String(latest_stmhstrlsm);
    					}
    					else {
    						//System.out.println("12");
    						//ada krs
    						if(latest_thsmstrlsm.equalsIgnoreCase("0")) {
    							//System.out.println("13");
    							//ngga usah di compare dengan thsmstrnlm krn ini hasil migrasi epsebd
    							status_akhir = new String(latest_stmhstrlsm);
    						}
    						else {
    							//System.out.println("14");
    							//cek thsms krs terakhir >= trslm keluar = kalo keluar mgga boleh ada krs pada sms tersebut 
        						if(latest_thsmstrnlm.compareToIgnoreCase(latest_thsmstrlsm)>=0) {
        							//System.out.println("15");
        							status_akhir = new String("E`"+latest_stmhstrlsm+"`Ada krs at THSMS > THSMS keluar");
        						}
        						else if(sdu) {
        							//System.out.println("16");
        							status_akhir = new String("E`"+latest_stmhstrlsm+"`Status keluar tapi sudah daftar ulang");
        						}
        						else if(latest_thsmstrnlm.compareToIgnoreCase(thsms_now)>=0) {
        							//System.out.println("17");
        							status_akhir = new String("E`"+latest_stmhstrlsm+"`Status keluar tapi ada krs at THSMS NOW");
        						}
        						else {
        							//no error
        							//System.out.println("18");
        							status_akhir = new String(latest_stmhstrlsm);
        						}
    						}
    					}
    				}
    				else if(latest_stmhstrlsm.equalsIgnoreCase("L")) {
    					//System.out.println("19");
    					stmt = con.prepareStatement("update CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
    					stmt.setString(1, latest_stmhstrlsm);
    					stmt.setString(2, npmhs);
    					stmt.executeUpdate();
    					
    					//tinggal mikir return valuenya - looking fo errrors
    					if(latest_thsmstrnlm==null) {
    						//System.out.println("20");
    						//ngga ada record krs -,no error
    						status_akhir = new String(latest_stmhstrlsm);
    					}
    					else {
    						//ada krs
    						//System.out.println("21");
    						if(latest_thsmstrlsm.equalsIgnoreCase("0")) {
    							//System.out.println("22");
    							//ngga usah di compare dengan thsmstrnlm krn ini hasil migrasi epsebd
    							status_akhir = new String(latest_stmhstrlsm);
    						}
    						else {
    							//System.out.println("23");
    							//cek thsms krs terakhir >= trslm keluar = kalo keluar mgga boleh ada krs pada sms tersebut 
        						if(latest_thsmstrnlm.compareToIgnoreCase(latest_thsmstrlsm)>0) {
        							//System.out.println("24");
        							status_akhir = new String("E`"+latest_stmhstrlsm+"`Ada krs at THSMS > THSMS lulus");
        						}
        						else if(latest_thsmstrnlm.compareToIgnoreCase(thsms_now)>0) {
        							//System.out.println("25");
        							status_akhir = new String("E`"+latest_stmhstrlsm+"`Status lulus tapi ada krs at THSMS LULUS");
        						}
        						else if(sdu && thsms_her.compareToIgnoreCase(latest_thsmstrlsm)>0) {
        							//System.out.println("26");
        							status_akhir = new String("E`"+latest_stmhstrlsm+"`Status lulus tapi sudah daftar ulang untuk sms depan");
        						}
        						else {
        							//System.out.println("27");
        							//no error
        							status_akhir = new String(latest_stmhstrlsm);
        						}
    						}
    						
    					}
    				}
    				else {
    					//non aktif / CUTI
    					//System.out.println("28");
    					if(sdu || (latest_thsmstrnlm!=null && latest_thsmstrnlm.compareToIgnoreCase(thsms_now)>=0)) {
    						//System.out.println("29");
    						stmt = con.prepareStatement("update CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
        					stmt.setString(1, "A");
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        					status_akhir = new String("A");
    					}
    					else if(latest_thsmstrlsm!=null && latest_thsmstrlsm.compareToIgnoreCase(thsms_now)>=0) {
    						//System.out.println("30");
    						stmt = con.prepareStatement("update CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
        					stmt.setString(1, latest_stmhstrlsm);
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        					status_akhir = new String(latest_stmhstrlsm);
    					}
    					else {
    						//System.out.println("31");
    						//bukan thsms saat ini jadi non aktif
							stmt = con.prepareStatement("update CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
        					stmt.setString(1, "N");
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        					status_akhir = new String("N");
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
        	
    	return status_akhir;
    	*/
    }
    
    
    public static String getAndSyncStmhsBetweenTrlsmAndMsmhs_v1(String kdpst,String npmhs) {
    	/*
    	 * STATUS STMHSMSMHS ADALAH KUNCI UNTUK MENENTUKAN SCKOPE AKSES, SEPERTI INS KRS,DSB
    	 */
    	String status_akhir = null;
    	String info_status_akhir = null;
    	String thsms_now = Checker.getThsmsNow();
    	//String thsms_krs = Checker.getThsmsKrs();
    	//String psn = Checker.sudahDaftarUlang(kdpst, npmhs);//menggunakan thsms_heregistrasi\\
    	boolean sdu = false;
    	sdu = Checker.sudahMengajukanDaftarUlang(kdpst, npmhs);//menggunakan thsms_heregistrasi
    	if(sdu) {
			info_status_akhir = new String("Proses daftar ulang");
		}
    	//if(psn==null) {
    	//	sdu = true;
    	//}
    	//System.out.println("sdu="+sdu);
    	boolean sdh_out = false;
    	String thsms_her = Checker.getThsmsHeregistrasi();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cek trlsm
    		//1. cek apa pernah keluar/lulus
    		String status_trlsm_at_thsms_reg = "null";
    		boolean ada_krs_at_thsms_reg = false;
    		String status_akhir_trlsm = "null";
    		String thsms_status_akhir_trlsm = "null";
    		String tipe_out = "";
    		String error_status_akhir_trlsm = "";
    		//cek apa ad krs at thsms_reg
    		stmt = con.prepareStatement("select THSMSTRNLM from TRNLM where NPMHSTRNLM=? and THSMSTRNLM=?  limit 1");
    		stmt.setString(1, npmhs);
    		stmt.setString(2, thsms_her);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			ada_krs_at_thsms_reg=true;	
    		}
    		//cek status trlsnm at thsms_reg
    		stmt = con.prepareStatement("select STMHS from TRLSM where NPMHS=? and THSMS=?");
    		stmt.setString(1, npmhs);
    		stmt.setString(2, thsms_her);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			status_trlsm_at_thsms_reg = rs.getString(1);
    		}
    		//System.out.println("status_trlsm_at_thsms_reg="+status_trlsm_at_thsms_reg);
    		stmt = con.prepareStatement("select THSMS,STMHS from TRLSM where NPMHS=? and STMHS<>? order by THSMS desc limit 1");
    		stmt.setString(1, npmhs);
    		stmt.setString(2, "N");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			thsms_status_akhir_trlsm = rs.getString(1);
    			status_akhir_trlsm = rs.getString(2);
    			if(!Checker.isStringNullOrEmpty(status_akhir_trlsm) && (status_akhir_trlsm.equalsIgnoreCase("K") || status_akhir_trlsm.equalsIgnoreCase("L") || status_akhir_trlsm.equalsIgnoreCase("D") || status_akhir_trlsm.equalsIgnoreCase("P"))) {
    				sdh_out = true;
    				tipe_out = new String(status_akhir_trlsm);
    			}
    		}
    		//System.out.println("status_akhir_trlsm="+status_akhir_trlsm);
    		//System.out.println("thsms_status_akhir_trlsm="+thsms_status_akhir_trlsm);
    		//System.out.println("status_akhir_trlsm="+status_akhir_trlsm);
    		//cek apa pernah keluar pd thsms sebelumnya (kasus dikeluarin di > 1 thsms)
    		if(!Checker.isStringNullOrEmpty(thsms_status_akhir_trlsm)) {
    			stmt = con.prepareStatement("select STMHS from TRLSM where NPMHS=? and (STMHS=? or STMHS=? or STMHS=? or STMHS=?) and THSMS<?");
        		stmt.setString(1, npmhs);
        		stmt.setString(2, "L");
        		stmt.setString(3, "K");
        		stmt.setString(4, "D");
        		stmt.setString(5, "P");
        		stmt.setString(6, thsms_status_akhir_trlsm);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			error_status_akhir_trlsm = "ERROR: Ada Status Baru Setelah Status KELUAR/LULUS";
        			sdh_out = true;
        			tipe_out = rs.getString(1);
        		}	
    		}
    		
    		
    		String status_akhir_trnlm = "null";
    		String thsms_status_akhir_trnlm = "null";
    		String error_status_akhir_trnlm = "";
    		stmt = con.prepareStatement("select distinct THSMSTRNLM from TRNLM where NPMHSTRNLM=? order by THSMSTRNLM desc limit 1");
    		stmt.setString(1, npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			status_akhir_trnlm = "A";
    			thsms_status_akhir_trnlm = rs.getString(1);
    		}
    		//System.out.println("thsms_status_akhir_trnlm="+thsms_status_akhir_trnlm);
    		
    		//if(Checker.isStringNullOrEmpty(thsms_status_akhir_trnlm) || thsms_status_akhir_trlsm.compareToIgnoreCase(thsms_status_akhir_trnlm)>0) {
    		if(Checker.isStringNullOrEmpty(thsms_status_akhir_trnlm)) {
    			//System.out.println("kesini");
    			//ngga punya krs ato data trlsm lebih terkini
    			if(Checker.isStringNullOrEmpty(thsms_status_akhir_trlsm)) {
    				//ngga punya pengajuan
    				status_akhir = new String("N");
    				if(sdu) {
    					info_status_akhir = new String("Proses daftar ulang");
    				}
    				else {
    					if(Checker.isStringNullOrEmpty(error_status_akhir_trlsm)) {
    						error_status_akhir_trlsm = new String("Tidak ada riwayat KRS & Pengajuan");
    					}
    					else {
    						error_status_akhir_trlsm = error_status_akhir_trlsm+"`Tidak ada riwayat KRS & Pengajuan";
    					}
    				}
    			}
    			else {
    				//tidak ada krs && ada data di trlsm
    				status_akhir = new String("N");
    				if(thsms_status_akhir_trlsm.compareToIgnoreCase(thsms_her)<0) {
    					if(sdh_out) {
        					//case < thsms_reg
        					status_akhir = new String(tipe_out);
        					if(sdu) {
            					//case < thsms_reg
        						error_status_akhir_trlsm = new String("ERROR: Status sudah LULUS/KELUAR tapi melakukan Heregistrasi");
            				}
        				}
    					else {
    						if(sdu) {
    							info_status_akhir = new String("Proses daftar ulang");
    						}
    					}
					}
    				else if(thsms_status_akhir_trlsm.compareToIgnoreCase(thsms_her)==0) {
    					status_akhir = new String(status_akhir_trlsm);
    					if(sdh_out) {
        					status_akhir = new String(tipe_out);
        					if(sdu) {
            					//case < thsms_reg
        						error_status_akhir_trlsm = new String("ERROR: Status sudah LULUS/KELUAR tapi melakukan Heregistrasi");
            				}
        				}
    					else if(status_akhir.equalsIgnoreCase("C")) {
    						if(sdu) {
            					//case < thsms_reg
        						error_status_akhir_trlsm = new String("ERROR: Status sedang CUTI tapi mengajukan Heregistrasi");
            				}
    					}
    					else {
    						if(sdu) {
    							info_status_akhir = new String("Proses daftar ulang");
    						}
    					}
    				}
    				else if(thsms_status_akhir_trlsm.compareToIgnoreCase(thsms_her)>0) {
    					if(!Checker.isStringNullOrEmpty(status_trlsm_at_thsms_reg)) {
    						status_akhir = new String(status_trlsm_at_thsms_reg);
    						if(status_trlsm_at_thsms_reg.equalsIgnoreCase("L")||status_trlsm_at_thsms_reg.equalsIgnoreCase("D")||status_trlsm_at_thsms_reg.equalsIgnoreCase("K")) {
    							error_status_akhir_trlsm = new String("ERROR: Status sudah Keluar/Lulus tapi ada status lagi di thsms > "+thsms_her);
    						}
    						if(status_trlsm_at_thsms_reg.equalsIgnoreCase("C")) {
    							if(sdu) {
    								error_status_akhir_trlsm = new String("ERROR: Status sedang CUTI tapi mengajukan Heregistrasi");
    							}
     						}
    						else {
        						if(sdu) {
        							info_status_akhir = new String("Proses daftar ulang");
        						}
        					}
    					}
    				}
    			}	
    		}
    		else {
    			//ada data krs
    			if(Checker.isStringNullOrEmpty(thsms_status_akhir_trlsm)) {
    				//tidaj ada data trlsm
    				//System.out.println("thsms_status_akhir_trnlm="+thsms_status_akhir_trnlm);
    				//System.out.println("thsms_her="+thsms_her);
    				//System.out.println(thsms_status_akhir_trnlm.compareToIgnoreCase(thsms_her));
    				if(thsms_status_akhir_trnlm.compareToIgnoreCase(thsms_her)<0) {
    					status_akhir = new String("N");
    				}
    				else if(thsms_status_akhir_trnlm.compareToIgnoreCase(thsms_her)==0) {
    					status_akhir = new String("A");
    				}
    				else if(thsms_status_akhir_trnlm.compareToIgnoreCase(thsms_her)>0) {
    					status_akhir = new String("N");
    					if(ada_krs_at_thsms_reg) {
    						status_akhir = new String("A");
    					}
    					if(ada_krs_at_thsms_reg) {
							status_akhir = new String("A");
							info_status_akhir = "null";
    					}
    					//error_status_akhir_trlsm = new String("WARNING: Ada KRS di THSMS > "+thsms_her);
    				}
    			}
    			else {
    				//ada krs && ada TRLSM
    				if(thsms_status_akhir_trlsm.compareToIgnoreCase(thsms_status_akhir_trnlm)<0) {
        				//data trnlm lebih terkini
    					
    					if(thsms_status_akhir_trnlm.compareToIgnoreCase(thsms_her)<0) { //otomatis sdu juga false
        					status_akhir = new String("N");
        					if(sdh_out) {
            					//case < thsms_reg
            					status_akhir = new String(tipe_out);
            					error_status_akhir_trlsm = new String("ERROR: Status "+status_akhir+" tapi memiliki KRS di "+thsms_status_akhir_trnlm);
            				}
        				}
        				else if(thsms_status_akhir_trnlm.compareToIgnoreCase(thsms_her)==0) {
        					status_akhir = new String("A");
        					if(sdh_out) {
            					//sudah out di thsms sebelumnya
            					status_akhir = new String(tipe_out);
            					error_status_akhir_trlsm = new String("ERROR: Status "+status_akhir+" tapi memiliki KRS di "+thsms_status_akhir_trnlm);
            				}
        				}
        				else if(thsms_status_akhir_trnlm.compareToIgnoreCase(thsms_her)>0) {
        					if(!Checker.isStringNullOrEmpty(status_trlsm_at_thsms_reg)) {
        						//ada status at thsms_reg
        						status_akhir = new String(status_trlsm_at_thsms_reg);
        						if(status_trlsm_at_thsms_reg.equalsIgnoreCase("L")||status_trlsm_at_thsms_reg.equalsIgnoreCase("D")||status_trlsm_at_thsms_reg.equalsIgnoreCase("K")) {
        							error_status_akhir_trlsm = new String("ERROR: Status sudah Keluar/Lulus tapi ada KRS lagi di thsms > "+thsms_her);
        						}
        						if(status_trlsm_at_thsms_reg.equalsIgnoreCase("C")) {
        							if(sdu) {
        								error_status_akhir_trlsm = new String("ERROR: Status sedang CUTI tapi mengajukan Heregistrasi");
        							}
        							else if(ada_krs_at_thsms_reg) {
        								error_status_akhir_trlsm = new String("ERROR: Status sedang CUTI tapi memiliki KRS");
                					}
         						}
        						else {
            						if(sdu) {
            							info_status_akhir = new String("Proses daftar ulang");
            						}
            						if(ada_krs_at_thsms_reg) {
            							status_akhir = new String("A");
            							info_status_akhir = "null";
                					}
            					}
        					}
        					else {
        						//tidak ada status at thsms_reg
        						status_akhir = new String("N");
        						if(sdu) {
        							info_status_akhir = new String("Proses daftar ulang");
        						}
        						if(ada_krs_at_thsms_reg) {
        							status_akhir = new String("A");
        							info_status_akhir = "null";
            					}
        					}
        					
        				}
    				}
        			else if(thsms_status_akhir_trlsm.compareToIgnoreCase(thsms_status_akhir_trnlm)==0) {
        				//ada status && krs pada periode yg sama
        				if(thsms_status_akhir_trlsm.compareToIgnoreCase(thsms_her)<0) {//sdu otomatis false
        					status_akhir = new String("N"); //< thsms_reg s0 default = N
        					
        					if(status_akhir_trlsm.equalsIgnoreCase("L")) {
        						status_akhir = new String(status_akhir_trlsm);
        					}
        					else if(status_akhir_trlsm.equalsIgnoreCase("K")||status_akhir_trlsm.equalsIgnoreCase("D")) {
        						status_akhir = new String(thsms_status_akhir_trlsm);
        						error_status_akhir_trlsm = new String("WARNING: Status Keluar tapi memiliki KRS di "+thsms_status_akhir_trlsm);
        					}
        					else if(status_akhir_trlsm.equalsIgnoreCase("C")) {
        						//status_akhir = new String(thsms_status_akhir_trlsm);
        						error_status_akhir_trlsm = new String("WARNING: Status CUTI tapi memiliki KRS di "+thsms_status_akhir_trlsm);
        					}
        				}
        				else if(thsms_status_akhir_trlsm.compareToIgnoreCase(thsms_her)==0) {
        					status_akhir = new String("A");//ada krs so default aktif
        					//ada status dan krs at thsms_reg
        					if(status_trlsm_at_thsms_reg.equalsIgnoreCase("L")) {
        						//no problemo: lulus & ada krs
        					}
        					else if(status_trlsm_at_thsms_reg.equalsIgnoreCase("K")||status_trlsm_at_thsms_reg.equalsIgnoreCase("D")) {
        						error_status_akhir_trlsm = new String("ERROR: Status KELUAR/DO tanpa memiliki KRS di "+thsms_her);
        					}
        					else if(status_trlsm_at_thsms_reg.equalsIgnoreCase("C")) {
        						error_status_akhir_trlsm = new String("ERROR: Status CUTI tapi memiliki KRS di "+thsms_her);
        					}
        				}
        				else if(thsms_status_akhir_trlsm.compareToIgnoreCase(thsms_her)>0) {
        					if(!Checker.isStringNullOrEmpty(status_trlsm_at_thsms_reg)) {
        						//ada status && krs thsms_reg 
        						
        						status_akhir = new String(status_trlsm_at_thsms_reg);
        						if(status_trlsm_at_thsms_reg.equalsIgnoreCase("L")||status_trlsm_at_thsms_reg.equalsIgnoreCase("D")||status_trlsm_at_thsms_reg.equalsIgnoreCase("K")) {
        							error_status_akhir_trlsm = new String("ERROR: Status sudah Keluar/Lulus tapi ada KRS lagi di thsms > "+thsms_her);
        						}
        						if(status_trlsm_at_thsms_reg.equalsIgnoreCase("C")) {
        							if(sdu) {
        								error_status_akhir_trlsm = new String("ERROR: Status sedang CUTI tapi mengajukan Heregistrasi");
        							}
        							else if(ada_krs_at_thsms_reg) {
        								error_status_akhir_trlsm = new String("ERROR: Status sedang CUTI tapi memiliki KRS");
                					}
         						}
        						else {
            						if(sdu) {
            							info_status_akhir = new String("Proses daftar ulang");
            						}
            						if(ada_krs_at_thsms_reg) {
            							status_akhir = new String("A");
            							info_status_akhir = "null";
                					}
            					}
        					}
        					else {
        						//tidak ada status di thsms_reg
        						status_akhir = new String("N");
        						if(sdu) {
        							info_status_akhir = new String("Proses daftar ulang");
        						}
        						if(ada_krs_at_thsms_reg) {
        							status_akhir = new String("A");
        							info_status_akhir = "null";
            					}
        					}
        				}
        				
        			}
        			else if(thsms_status_akhir_trlsm.compareToIgnoreCase(thsms_status_akhir_trnlm)>0) {
        				//data terakhir ada di trlsm
        				if(thsms_status_akhir_trlsm.compareToIgnoreCase(thsms_her)<0) {//sdu otomatis false
        					status_akhir = new String(status_akhir_trlsm);
        					//System.out.println("status_akhir="+status_akhir);
        					if(status_akhir.equalsIgnoreCase("L")) {
        						error_status_akhir_trlsm = new String("ERROR: Status LULUS tanpa ada KRS");
        					}
        					else if(status_akhir.equalsIgnoreCase("C")||status_akhir.equalsIgnoreCase("A")) {
        						status_akhir = new String("N");
        					}
        				}
        				else if(thsms_status_akhir_trlsm.compareToIgnoreCase(thsms_her)==0) {
        					status_akhir = new String(status_akhir_trlsm);
        					if(status_akhir.equalsIgnoreCase("L")) {
        						error_status_akhir_trlsm = new String("ERROR: Status LULUS tanpa ada KRS");
        					}
        					else if(status_akhir.equalsIgnoreCase("K")||status_akhir.equalsIgnoreCase("D")) {
        						if(sdu) {
        							error_status_akhir_trlsm = new String("ERROR: Status KELUAR/DO tapi mengajukan heregistrasi");
        						}
        					}
        				}
        				else if(thsms_status_akhir_trlsm.compareToIgnoreCase(thsms_her)>0) {
        					if(!Checker.isStringNullOrEmpty(status_trlsm_at_thsms_reg)) {
        						//ada status at thsms_reg
        						status_akhir = new String(status_trlsm_at_thsms_reg);
        						if(status_trlsm_at_thsms_reg.equalsIgnoreCase("L")||status_trlsm_at_thsms_reg.equalsIgnoreCase("D")||status_trlsm_at_thsms_reg.equalsIgnoreCase("K")) {
        							error_status_akhir_trlsm = new String("ERROR: Status sudah Keluar/Lulus tapi ada Status lagi di thsms > "+thsms_status_akhir_trlsm);
        						}
        						if(status_trlsm_at_thsms_reg.equalsIgnoreCase("C")) {
        							if(sdu) {
        								error_status_akhir_trlsm = new String("ERROR: Status sedang CUTI tapi mengajukan Heregistrasi");
        							}
        							else if(ada_krs_at_thsms_reg) {
        								error_status_akhir_trlsm = new String("ERROR: Status sedang CUTI tapi memiliki KRS");
                					}
         						}
        						else {
            						if(sdu) {
            							info_status_akhir = new String("Proses daftar ulang");
            						}
            						if(ada_krs_at_thsms_reg) {
            							status_akhir = new String("A");
            							info_status_akhir = "null";
                					}
            					}
        					}
        					else {
        						status_akhir = new String("N");
        						if(sdu) {
        							info_status_akhir = new String("Proses daftar ulang");
        						}
        						if(ada_krs_at_thsms_reg) {
        							status_akhir = new String("A");
        							info_status_akhir = "null";
            					}
        					}
        				}
        			}
    			}
    			
    		}
    		
    		//if(status_akhir.equalsIgnoreCase("N") && sdu) {
    		//	status_akhir = new String("A");
    		//}
    		//update stmhs
    		//System.out.println("status_akhir="+status_akhir);
    		stmt = con.prepareStatement("update CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
    		if(Checker.isStringNullOrEmpty(status_akhir)) {
    			stmt.setString(1, "N");
    		}
    		else {
    			stmt.setString(1, status_akhir);
    		}
    		stmt.setString(2, npmhs);
    		stmt.executeUpdate();
    		
    		
    		if(Checker.isStringNullOrEmpty(error_status_akhir_trlsm)) {
    			status_akhir = status_akhir+"`"+info_status_akhir+"`null";	
    		}
    		else {
    			status_akhir = status_akhir+"`"+info_status_akhir+"`"+error_status_akhir_trlsm;	
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
    	return status_akhir;
    }
    
    public static boolean isAllApproved(long topik_id, String tipe_pengajuan) {
    	boolean all_approved = false;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where ID=?");
    		stmt.setLong(1, topik_id);
    		rs = stmt.executeQuery();
    		rs.next();
    		String tkn_target_approvee_id = ""+rs.getString("TOKEN_TARGET_OBJID");
    		String tkn_approvee_id_yg_sdh_approved = ""+rs.getString("APPROVED");
    		all_approved= isAllApproved(""+topik_id, tkn_target_approvee_id);
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
    	return all_approved;
    }
    

    public static boolean isAllApproved(String id_topik_pengajuan, String tkn_requird_approvee_id) {
    	//System.out.println("id_topik_pengajuan="+id_topik_pengajuan);
    	//System.out.println("tkn_requird_approvee_id="+tkn_requird_approvee_id);
    	boolean all_approved = true;
    	while(tkn_requird_approvee_id.contains("][")) {
    		tkn_requird_approvee_id = tkn_requird_approvee_id.replace("][", "]`[");	
    	}
    	
    	StringTokenizer st = null;
		//tkn_requird_approvee_id = tkn_requird_approvee_id.replace(",", "`");
		Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
		try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from SUBTOPIK_PENGAJUAN where ID_TOPIK=?");
    		stmt.setLong(1,Long.parseLong(id_topik_pengajuan));
    		rs = stmt.executeQuery();
    		boolean first = true;
    		while(rs.next()&&all_approved) {
    			String approval_obj_id = rs.getString("CREATOR_OBJ_ID");
    			//System.out.println("approval_obj_id="+approval_obj_id);
    			String verdict = rs.getString("VERDICT");
    			if(verdict!=null && (verdict.contains("TOLAK")||verdict.contains("tolak"))) {
    				all_approved=false;
    			}
    			else {
    				//cek selama required token id still not empty
    				if(!Checker.isStringNullOrEmpty(tkn_requird_approvee_id)) {
    					//System.out.println("sisa approvee0="+tkn_requird_approvee_id);
    					
    					st = new StringTokenizer(tkn_requird_approvee_id,"`");
    					while(st.hasMoreTokens()) {
    						String list_token_id = st.nextToken();
    						//System.out.println("list_token_id="+list_token_id);
    						if(list_token_id.contains("["+approval_obj_id+"]") ||
        					   list_token_id.contains(","+approval_obj_id+",") ||
        					   list_token_id.contains(","+approval_obj_id+"]") ||
        					   list_token_id.contains("["+approval_obj_id+",") 
        					   ) {
        						tkn_requird_approvee_id = tkn_requird_approvee_id.replace(list_token_id, "");
        						while(tkn_requird_approvee_id.contains("``")) {
        							tkn_requird_approvee_id = tkn_requird_approvee_id.replace("``", "`");
        						}
        					}
    					}
    				}
    			}
    			//System.out.println("sisa approvee="+tkn_requird_approvee_id);
    		}
    		//System.out.println("approvee left="+tkn_requird_approvee_id);
    		if(all_approved) {
    			while(tkn_requird_approvee_id.contains("`")) {
    				tkn_requird_approvee_id = tkn_requird_approvee_id.replace("`", "");
    			}
    			//System.out.println("approvee left akhir="+tkn_requird_approvee_id);
    			if(!Checker.isStringNullOrEmpty(tkn_requird_approvee_id)) {
    				all_approved = false;
    			}
    			
    			//System.out.println("all_approved="+all_approved);
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
		

		return all_approved;
    }
    
    public static String siapaIni(String tkn_target_objnickname, String tkn_target_objid, String target_objid, String remove_word) {
    	tkn_target_objnickname = tkn_target_objnickname.replace("[", "");
    	tkn_target_objnickname = tkn_target_objnickname.replace("]", "`");
    	
    	tkn_target_objid = tkn_target_objid.replace("[", "");
    	tkn_target_objid = tkn_target_objid.replace("]", "`");
    	//System.out.println("adhoc tkn_target_objid="+tkn_target_objid);
    	//System.out.println("adhoc tkn_target_objnickname="+tkn_target_objnickname);
    	//System.out.println("adhoc target_objid="+target_objid);
    	StringTokenizer st1 = new StringTokenizer(tkn_target_objid,"`");
    	StringTokenizer st2 = new StringTokenizer(tkn_target_objnickname,"`");
    	String tkn_nick = null;
    	String list_role = null;
    	//boolean match = false;
    	/*
    	 * TIDAK PAKE BOOLEAN MATCH KARENA ADA KEMUNGKINAN ID INI MEMILIKI BEBERAP ROLE SPT ,ktu & KAPRODI
    	 */
    	boolean first = true;
    	while(st1.hasMoreTokens()) {
    		String tkn_id = st1.nextToken();
    		tkn_nick = new String(st2.nextToken());
    		if(remove_word!=null) {
    			tkn_nick = tkn_nick.replace(remove_word, "");	
    		}
    		
    		//if(tkn_id.equalsIgnoreCase(target_objid)) {
    		if(tkn_id.equalsIgnoreCase(target_objid) || tkn_id.contains(target_objid+",") || tkn_id.contains(","+target_objid)) {
    			if(first) {
    				first = false;
    				list_role = new String(tkn_nick);
    			}
    			else {
    				list_role = list_role+"`"+tkn_nick;
    			}
    			//match = true;
    		}
    	}
    	
    	//return tkn_nick;
    	return list_role;
    }
    
    public static void cekTknAprDaftarUlang(String target_thsms) {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector vf = null;
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select NPMHS,TOKEN_APPROVAL from DAFTAR_ULANG where THSMS=?");
    		stmt.setString(1, target_thsms);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			vf = new Vector();
    			ListIterator li = vf.listIterator();
    			do {
    				String npmhs = rs.getString(1);
    				String tkn = ""+rs.getString(2);
    				if(tkn.startsWith("#")) {
    					tkn = tkn.substring(1, tkn.length());
    				}
    				if(tkn.endsWith("#")) {
    					tkn = tkn.substring(0, tkn.length()-1);
    				}
    				StringTokenizer st = new StringTokenizer(tkn,"#");
    				boolean revise = false;
    				String revisi = null;
    				if(st.countTokens()>3) {
    					
    					String npm_apr = st.nextToken();
    					String jabatan_apr = st.nextToken();
    					String list_jabatan_yg_sdg_approved = new String("`"+jabatan_apr+"`");
    					String time_apr = st.nextToken();
    					revisi = new String(npm_apr+"#"+jabatan_apr+"#"+time_apr+"#");
    					//boolean same_approvee = false;
    					while(st.hasMoreTokens()) {
    						String next_npm_apr = st.nextToken();
        					String next_jabatan_apr = st.nextToken();
        					String next_time_apr = st.nextToken();
        					if(list_jabatan_yg_sdg_approved.equalsIgnoreCase("`"+next_jabatan_apr+"`")) {
        						//sudah approved- revise
        						revise = true;
        					}
        					else {
        						list_jabatan_yg_sdg_approved = list_jabatan_yg_sdg_approved+next_jabatan_apr+"`";
        						revisi = revisi+npm_apr+"#"+jabatan_apr+"#"+time_apr+"#";
        					}
    					}
    					if(revisi.endsWith("#")) {
    						revisi = revisi.substring(0, revisi.length()-1);
        				}
    					
    				}
    				if(revise) {
    					li.add(npmhs);
    					li.add(revisi);
    				}
    			}
    			while(rs.next());
    			
    			if(vf!=null && vf.size()>0) {
    				li = vf.listIterator();
    				stmt=con.prepareStatement("update DAFTAR_ULANG set TOKEN_APPROVAL=? where THSMS=? and NPMHS=?");
    				while(li.hasNext()){
    					String npm = (String)li.next();
    					String revisi = (String)li.next();
    					stmt.setString(1, revisi);
    					stmt.setString(2, target_thsms);
    					stmt.setString(3, npm);
    					stmt.executeUpdate();
    				}
    			}
    		}
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	
    	
    }
    
    public static void cekIdobjAtDaftarUlangTable(String target_thsms) {
    	/*
    	 * UNTUK ANTISIPASI KRN IDOBJ kok bisa berubah jadi KDPST
    	 */
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector vf = null;
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select NPMHS from DAFTAR_ULANG where THSMS=?");
    		stmt.setString(1, target_thsms);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			vf = new Vector();
    			ListIterator li = vf.listIterator();
    			do {
    				String npmhs = rs.getString(1);
    				li.add(npmhs);
    			}
    			while(rs.next());
    			
    			if(vf!=null && vf.size()>0) {
    				li = vf.listIterator();
    				//stmt=con.prepareStatement("update DAFTAR_ULANG set TOKEN_APPROVAL=? where THSMS=? and NPMHS=?");
    				while(li.hasNext()){
    					String npmhs = (String)li.next();
    					String idobj = ""+Checker.getObjectId(npmhs);
    					li.set(npmhs+"`"+idobj);
    				}
    				
    				li = vf.listIterator();
    				stmt=con.prepareStatement("update DAFTAR_ULANG set ID_OBJ=? where THSMS=? and NPMHS=?");
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String npmhs = st.nextToken();
    					String idobj = st.nextToken();
    					stmt.setInt(1,Integer.parseInt(idobj));
    					stmt.setString(2, target_thsms);
    					stmt.setString(3, npmhs);
    					stmt.executeUpdate();
    				}		
    			}	
    		}
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	
    	
    }
    
    /*
    public static void cekAndSyncPengajuan(String target_thsms, String cuti_or_keluar_or_do_or_kelulusan) {
    	String kode_pengajuan = "";
    	if(cuti_or_keluar_or_do_or_kelulusan.equalsIgnoreCase("CUTI")) {
    		kode_pengajuan = "C";
    	}
    	else if(cuti_or_keluar_or_do_or_kelulusan.equalsIgnoreCase("DO")) {
    		kode_pengajuan = "D";
    	}
    	else if(cuti_or_keluar_or_do_or_kelulusan.equalsIgnoreCase("KELUAR")) {
    		kode_pengajuan = "K";
    	}
    	else if(cuti_or_keluar_or_do_or_kelulusan.equalsIgnoreCase("KELULUSAN")) {
    		kode_pengajuan = "L";
    	}
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector vf = null;
    	ListIterator li = null;
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select ID_OBJ,TOT_"+cuti_or_keluar_or_do_or_kelulusan+"_REQ,TOT_"+cuti_or_keluar_or_do_or_kelulusan+"_REQ_UNAPPROVED,LIST_NPM_"+cuti_or_keluar_or_do_or_kelulusan+"_UNAPPROVED from OVERVIEW_SEBARAN_TRLSM where THSMS=? order by KDPST");
    		stmt.setString(1, target_thsms);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			if(vf==null) {
    				vf = new Vector();
    				li = vf.listIterator();
    			}
    			String idobj = ""+rs.getInt(1);
    			String tot_req = ""+rs.getInt(2);
    			String tot_unapproved = ""+rs.getInt(3);
    			String list_npm_unapproved = ""+rs.getString(4);
    			li.add(idobj+"`"+tot_req+"`"+tot_unapproved+"`"+list_npm_unapproved);
    		}
    		if(vf!=null) {
    			stmt = con.prepareStatement("select * from TRLSM where THSMS=? and NPMHS=? and STMHS=?");
				li = vf.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					//System.out.println("ori="+brs);
					StringTokenizer st = new StringTokenizer(brs,"`");
					int idobj = Integer.parseInt(st.nextToken());
					int tot_req = Integer.parseInt(st.nextToken());
					int tot_unapproved = Integer.parseInt(st.nextToken());
					String list_npm_unapproved = st.nextToken();
					st = new StringTokenizer(list_npm_unapproved,",");
					while(st.hasMoreTokens()) {
						String npmhs = st.nextToken();
						stmt.setString(1, target_thsms);
						stmt.setString(2, npmhs);
						stmt.setString(3, kode_pengajuan);
						rs = stmt.executeQuery();
						if(rs.next()) {
							list_npm_unapproved = list_npm_unapproved.replace(npmhs, "");
							list_npm_unapproved = list_npm_unapproved.replace(",", "");
							if(list_npm_unapproved.startsWith(",")) {
								list_npm_unapproved = list_npm_unapproved.substring(1, list_npm_unapproved.length());
							}
							else if(list_npm_unapproved.endsWith(",")) {
								list_npm_unapproved = list_npm_unapproved.substring(0, list_npm_unapproved.length()-1);
							}
							tot_unapproved--;
						}
					}
					if(tot_unapproved<0) {
						tot_unapproved=0;
					}
					if(Checker.isStringNullOrEmpty(list_npm_unapproved)) {
						list_npm_unapproved = "null";
					}
					li.set(idobj+"`"+tot_req+"`"+tot_unapproved+"`"+list_npm_unapproved);
					//System.out.println("set="+idobj+"`"+tot_req+"`"+tot_unapproved+"`"+list_npm_unapproved);
				}
			}
    		if(vf!=null) {
    			stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_"+cuti_or_keluar_or_do_or_kelulusan+"_REQ=?,TOT_"+cuti_or_keluar_or_do_or_kelulusan+"_REQ_UNAPPROVED=?,LIST_NPM_"+cuti_or_keluar_or_do_or_kelulusan+"_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
    			//System.out.println("update OVERVIEW_SEBARAN_TRLSM set TOT_"+cuti_or_keluar_or_do_or_kelulusan+"_REQ=?,TOT_"+cuti_or_keluar_or_do_or_kelulusan+"_REQ_UNAPPROVED=?,LIST_NPM_"+cuti_or_keluar_or_do_or_kelulusan+"_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
				li = vf.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					int idobj = Integer.parseInt(st.nextToken());
					int tot_req = Integer.parseInt(st.nextToken());
					int tot_unapproved = Integer.parseInt(st.nextToken());
					String list_npm_unapproved = st.nextToken();
					stmt.setInt(1, tot_req);
					stmt.setInt(2, tot_unapproved);
					if(Checker.isStringNullOrEmpty(list_npm_unapproved)) {
						stmt.setNull(3, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(3, list_npm_unapproved);	
					}
					
					stmt.setInt(4, idobj);
					stmt.setString(5, target_thsms);
					stmt.executeUpdate();
					//System.out.print("update "+brs);
					//System.out.print("="+);
				}
    		}	
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	
    	
    }
    */
    
    public static void syncChitChatMemberTable(String alphabet) {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector vf = null;
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select NPMHSMSMHS,NMMHSMSMHS from CIVITAS  where NMMHSMSMHS like '"+alphabet+"%' order by NMMHSMSMHS");
    		
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			vf = new Vector();
    			ListIterator li = vf.listIterator();
    			do {
    				String npmhs = rs.getString(1);
    				String nmmhs = ""+rs.getString(2);
    				
    				li.add(npmhs);
    				li.add(nmmhs);
    				
    			}
    			while(rs.next());
    			
    			if(vf!=null && vf.size()>0) {
    				//System.out.println("v size="+vf.size());
    				ds = (DataSource)envContext.lookup("jdbc/CHITCHAT");
    	    		con = ds.getConnection();
    	    		//stmt = con.prepareStatement("select NPMHSMSMHS,NMMHSMSMHS from CIVITAS order by NMMHSMSMHS");
    				li = vf.listIterator();
    				int i = 1;
    				stmt=con.prepareStatement("insert IGNORE into MEMBER(NPM,FULL_NAME)values(?,?)");
    				while(li.hasNext()){
    					String npmhs = (String)li.next();
    					String nmmhs = (String)li.next();
    					stmt.setString(1, npmhs);
    					stmt.setString(2, nmmhs);
    					
    					int j= stmt.executeUpdate();
    					//System.out.println(i+++". insert "+npmhs+"-"+nmmhs+"="+j);
    				}
    			}
    		}
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	
    	
    }
    
    public static Vector syncProdiDgnNilaiTunda(String thsms) {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector vf = null;
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select NLAKHTRNLM from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=? and (NLAKHTRNLM=? or NLAKHTRNLM is null) and KODE_KAMPUS=? limit 1");
    		
    		Vector v_scope_id = Getter.returnListProdiOnlySortByKampusWithListIdobj();
    		Vector v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
    		//lif.add(kmp+list_idobj);
    		ListIterator li=v_scope_id.listIterator();
    		ListIterator lik=v_scope_kdpst.listIterator();
    		vf = new Vector();
    		ListIterator lif = vf.listIterator();
    		while(lik.hasNext()) {
    			String tmp = "";
    			String brs = (String)li.next();
    			String brsk = (String)lik.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			StringTokenizer stk = new StringTokenizer(brsk,"`");
    			String kmp1 = st.nextToken();
    			String kmp = stk.nextToken();
    			tmp = tmp+kmp;
    			while(stk.hasMoreTokens()) {
    				String idobj = st.nextToken();
    				String kdpst = stk.nextToken();
    				stmt.setString(1, thsms);
    				stmt.setString(2, kdpst);
    				stmt.setString(3, "T");
    				stmt.setString(4, kmp);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					tmp = tmp+"`"+idobj+"-true";
    				}
    				else {
    					tmp = tmp+"`"+idobj+"-false";
    				}
    			}
    			lif.add(tmp);
    			//System.out.println(brs);
    		}
    		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set NILAI_TUNDA=? where ID_OBJ=? and THSMS=?");
    		lif = vf.listIterator();
    		while(lif.hasNext()) {
    			String brs = (String)lif.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kmp = st.nextToken();
    			while(st.hasMoreTokens()) {
    				String brs1 = st.nextToken();
    				StringTokenizer st1 = new StringTokenizer(brs1,"-");
    				String idobj = st1.nextToken();
    				String tunda = st1.nextToken();
    				stmt.setBoolean(1, Boolean.parseBoolean(tunda));
    				stmt.setLong(2, Long.parseLong(idobj));
    				stmt.setString(3, thsms);
    				//System.out.print(tunda+"-"+idobj+"-"+thsms);
    				int j = stmt.executeUpdate();
    				//System.out.println("="+j);
    			}
    		}
    		
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return vf;
    }
    

    
    public static Vector syncTrlsnDgnTrnlm() {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector vf = null;
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("select NPMHS,STMHS from TRLSM where THSMS=? and (STMHS='D' or STMHS='K' or STMHS='C' or STMHS='N')");
    		String thsms = Checker.getThsmsHeregistrasi();
    		stmt.setString(1, thsms);
    		Vector v = new Vector();
    		ListIterator li = v.listIterator();
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String npmhs = rs.getString(1);
    			String stmhs = rs.getString(2);
    			li.add(thsms+"`"+npmhs+"`"+stmhs);
    			//System.out.println(thsms+"--"+npmhs+"--"+stmhs);
    		}
    		if(!thsms.equalsIgnoreCase(Checker.getThsmsNow())) {
    			thsms = Checker.getThsmsNow();
    			stmt.setString(1, thsms);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String npmhs = rs.getString(1);
        			String stmhs = rs.getString(2);
        			li.add(thsms+"`"+npmhs+"`"+stmhs);
        			//System.out.println(thsms+"--"+npmhs+"--"+stmhs);
        		}
    		}
    		if(!thsms.equalsIgnoreCase(Checker.getThsmsInputNilai())) {
    			thsms = Checker.getThsmsInputNilai();
    			stmt.setString(1, thsms);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String npmhs = rs.getString(1);
        			String stmhs = rs.getString(2);
        			li.add(thsms+"`"+npmhs+"`"+stmhs);
        			//System.out.println(thsms+"--"+npmhs+"--"+stmhs);
        		}
    		}
    		if(!thsms.equalsIgnoreCase(Checker.getThsmsPengajuanStmhs())) {
    			thsms = Checker.getThsmsPengajuanStmhs();
    			stmt.setString(1, thsms);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String npmhs = rs.getString(1);
        			String stmhs = rs.getString(2);
        			li.add(thsms+"`"+npmhs+"`"+stmhs);
        			//System.out.println(thsms+"--"+npmhs+"--"+stmhs);
        		}
    		}
    		if(v!=null) {
    			v = Tool.removeDuplicateFromVector(v);
    			stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM=? and NPMHSTRAKM=?");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			thsms = st.nextToken();
        			String npmhs = st.nextToken();
        			String stmhs = st.nextToken();
        			stmt.setString(1, thsms);
        			stmt.setString(2, npmhs);
        			int i = stmt.executeUpdate();
        			//System.out.println("akm "+thsms+"-"+npmhs+"="+i);
        		}
        		//khusus untuk yg keluar
        		stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM>? and NPMHSTRAKM=?");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			thsms = st.nextToken();
        			String npmhs = st.nextToken();
        			String stmhs = st.nextToken();
        			if(stmhs.equalsIgnoreCase("D")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("L")) {
        				stmt.setString(1, thsms);
            			stmt.setString(2, npmhs);
            			int i = stmt.executeUpdate();
        			}
        			
        			//System.out.println("akm "+thsms+"-"+npmhs+"="+i);
        		}
        		
        		stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			thsms = st.nextToken();
        			String npmhs = st.nextToken();
        			stmt.setString(1, thsms);
        			stmt.setString(2, npmhs);
        			int i = stmt.executeUpdate();
        			//System.out.println(thsms+"-"+npmhs+"="+i);
        		}
        		
        		stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM>? and NPMHSTRNLM=?");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			thsms = st.nextToken();
        			String npmhs = st.nextToken();
        			String stmhs = st.nextToken();
        			if(stmhs.equalsIgnoreCase("D")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("L")) {
        				stmt.setString(1, thsms);
            			stmt.setString(2, npmhs);
            			int i = stmt.executeUpdate();
        			}
        		}
    		}
    		
    		
    		//li = v.listIterator();
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return vf;
    }
    
    public static Vector syncTrlsnDgnTopikPengajuan() {
    	/*
    	 * YANG DI CEK CUMA CUTI DOANG ---KAN STATUS KELUAR YG LAINNYA TIDAK MELALAUI PENGAJUAN, KELUAR aja belum tentu vi a pengajuan
    	 */
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector vf = null;
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		//1. get llist dari trlsn kemudian cek ke pengajuan, yg tidak ada pengajuannya di hapu
    		stmt = con.prepareStatement("select NPMHS,STMHS from TRLSM where THSMS=? and (STMHS='C')");
    		//String thsms = Checker.getThsmsHeregistrasi();
    		String thsms = Checker.getThsmsPengajuanStmhs(); //pastikan yg dipake thsms
    		stmt.setString(1, thsms);
    		Vector v = new Vector();
    		ListIterator li = v.listIterator();
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String npmhs = rs.getString(1);
    			String stmhs = rs.getString(2);
    			li.add(thsms+"`"+npmhs+"`"+stmhs);
    			//System.out.println(thsms+"--"+npmhs+"--"+stmhs);
    		}
    		
    		/*
    		 * MULAI SEKARANG GUNAKAN SATU THSMS
    		if(!thsms.equalsIgnoreCase(Checker.getThsmsNow())) {
    			thsms = Checker.getThsmsNow();
    			stmt.setString(1, thsms);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String npmhs = rs.getString(1);
        			String stmhs = rs.getString(2);
        			li.add(thsms+"`"+npmhs+"`"+stmhs);
        			//System.out.println(thsms+"--"+npmhs+"--"+stmhs);
        		}
    		}
    		if(!thsms.equalsIgnoreCase(Checker.getThsmsInputNilai())) {
    			thsms = Checker.getThsmsInputNilai();
    			stmt.setString(1, thsms);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String npmhs = rs.getString(1);
        			String stmhs = rs.getString(2);
        			li.add(thsms+"`"+npmhs+"`"+stmhs);
        			//System.out.println(thsms+"--"+npmhs+"--"+stmhs);
        		}
    		}
    		if(!thsms.equalsIgnoreCase(Checker.getThsmsPengajuanStmhs())) {
    			thsms = Checker.getThsmsPengajuanStmhs();
    			stmt.setString(1, thsms);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String npmhs = rs.getString(1);
        			String stmhs = rs.getString(2);
        			li.add(thsms+"`"+npmhs+"`"+stmhs);
        			//System.out.println(thsms+"--"+npmhs+"--"+stmhs);
        		}
    		}
    		*/
    		if(v!=null) {
    			v = Tool.removeDuplicateFromVector(v);
    			stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_NPM=?");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			thsms = st.nextToken();
        			stmt.setString(1, thsms);
        			String npmhs = st.nextToken();
        			String stmhs = st.nextToken();
        			if(stmhs.equalsIgnoreCase("C")) {
        				stmt.setString(2, "CUTI");
        			}
        			stmt.setString(3, npmhs);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				li.remove();
        			}	//System.out.println("akm "+thsms+"-"+npmhs+"="+i);
        		}
        		/*
        		 * Kenapa ini dulu dihapus?? proses diatas tidak di fiilter kalo memang sudah di aappproved
        		 * dan bermasalah kalo di paksa cuti oleh sistem krn tidak ada kabar
        		
        		if(v!=null && v.size()>0) {
        			stmt = con.prepareStatement("delete from TRLSM where THSMS=? and NPMHS=? and STMHS=?");
            		li = v.listIterator();
            		while(li.hasNext()) {
            			String brs = (String)li.next();
            			StringTokenizer st = new StringTokenizer(brs,"`");
            			thsms = st.nextToken();
            			String npmhs = st.nextToken();
            			String stmhs = st.nextToken();
            			stmt.setString(1, thsms);
            			stmt.setString(2, npmhs);
            			stmt.setString(3, stmhs);
            			int i = stmt.executeUpdate();
            			//System.out.println("delete trlsm "+thsms+"-"+npmhs+"-"+npmhs+"="+i);
            		}
        		}
        		 */
    		}
    		//2. cek topik pengajun lalu yg SUDAH DI APPROVED ccek apa ada di trlsm, yg belum ada diins
    		stmt = con.prepareStatement("select TIPE_PENGAJUAN,CREATOR_NPM,CREATOR_KDPST from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and (TIPE_PENGAJUAN='CUTI' or TIPE_PENGAJUAN='KELUAR' or TIPE_PENGAJUAN='KELULUSAN' or TIPE_PENGAJUAN='DO') and LOCKED=? and BATAL=? and APPROVED is not null");
    		//thsms = Checker.getThsmsHeregistrasi();
    		stmt.setString(1, thsms);
    		stmt.setBoolean(2, true);
    		stmt.setBoolean(3, false);
    		v = new Vector();
    		li = v.listIterator();
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String stmhs = rs.getString(1);
    			String npmhs = rs.getString(2);
    			String kdpst = rs.getString(3);
    			if(stmhs.equalsIgnoreCase("CUTI")) {
    				stmhs = "C";
    			}
    			else if(stmhs.equalsIgnoreCase("KELUAR")) {
    				stmhs = "K";
    			}
    			else if(stmhs.equalsIgnoreCase("KELULUSAN")) {
    				stmhs = "L";
    			}
    			else if(stmhs.equalsIgnoreCase("DO")) {
    				stmhs = "D";
    			}
    			//String 
    			li.add(thsms+"`"+npmhs+"`"+stmhs+"`"+kdpst);
    			//System.out.println(thsms+"--"+npmhs+"--"+stmhs);
    		}
    		/*
    		if(!thsms.equalsIgnoreCase(Checker.getThsmsNow())) {
    			thsms = Checker.getThsmsNow();
    			stmt.setString(1, thsms);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String stmhs = rs.getString(1);
        			String npmhs = rs.getString(2);
        			String kdpst = rs.getString(3);
        			if(stmhs.equalsIgnoreCase("CUTI")) {
        				stmhs = "C";
        			}
        			else if(stmhs.equalsIgnoreCase("KELUAR")) {
        				stmhs = "K";
        			}
        			else if(stmhs.equalsIgnoreCase("KELULUSAN")) {
        				stmhs = "L";
        			}
        			else if(stmhs.equalsIgnoreCase("DO")) {
        				stmhs = "D";
        			}
        			//String 
        			li.add(thsms+"`"+npmhs+"`"+stmhs+"`"+kdpst);
        		}
    		}
    		if(!thsms.equalsIgnoreCase(Checker.getThsmsInputNilai())) {
    			thsms = Checker.getThsmsInputNilai();
    			stmt.setString(1, thsms);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String stmhs = rs.getString(1);
        			String npmhs = rs.getString(2);
        			String kdpst = rs.getString(3);
        			if(stmhs.equalsIgnoreCase("CUTI")) {
        				stmhs = "C";
        			}
        			else if(stmhs.equalsIgnoreCase("KELUAR")) {
        				stmhs = "K";
        			}
        			else if(stmhs.equalsIgnoreCase("KELULUSAN")) {
        				stmhs = "L";
        			}
        			else if(stmhs.equalsIgnoreCase("DO")) {
        				stmhs = "D";
        			}
        			//String 
        			li.add(thsms+"`"+npmhs+"`"+stmhs+"`"+kdpst);
        		}
    		}
    		if(!thsms.equalsIgnoreCase(Checker.getThsmsPengajuanStmhs())) {
    			thsms = Checker.getThsmsPengajuanStmhs();
    			stmt.setString(1, thsms);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String stmhs = rs.getString(1);
        			String npmhs = rs.getString(2);
        			String kdpst = rs.getString(3);
        			if(stmhs.equalsIgnoreCase("CUTI")) {
        				stmhs = "C";
        			}
        			else if(stmhs.equalsIgnoreCase("KELUAR")) {
        				stmhs = "K";
        			}
        			else if(stmhs.equalsIgnoreCase("KELULUSAN")) {
        				stmhs = "L";
        			}
        			else if(stmhs.equalsIgnoreCase("DO")) {
        				stmhs = "D";
        			}
        			//String 
        			li.add(thsms+"`"+npmhs+"`"+stmhs+"`"+kdpst);
        		}
    		}
    		*/
    		if(v!=null) {
    			v = Tool.removeDuplicateFromVector(v);
    			stmt = con.prepareStatement("INSERT IGNORE INTO TRLSM (THSMS,KDPST,NPMHS,STMHS)values(?,?,?,?)");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			//thsms = st.nextToken();
        			thsms = st.nextToken();
        			String npmhs = st.nextToken();
        			String stmhs = st.nextToken();
        			String kdpst = st.nextToken();
        			stmt.setString(1, thsms);
        			stmt.setString(2, kdpst);
        			stmt.setString(3, npmhs);
        			stmt.setString(4, stmhs);
        			int i = stmt.executeUpdate();
        			//System.out.println("insert trlsm "+thsms+"-"+npmhs+"-"+npmhs+"="+i);
        		}	
    		}
    		
    		
    		//li = v.listIterator();
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return vf;
    }
    
    public static Vector tryFixErrorDariPenggabunganKlsProses(Vector v_SearchDbMk_findMkError) {
    	Vector v_unable_to_fix = new Vector();
    	ListIterator liu = v_unable_to_fix.listIterator();
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	if(v_SearchDbMk_findMkError!=null && v_SearchDbMk_findMkError.size()>0) {
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    			con = ds.getConnection();
    			//1. set npm asal at civitas
    			stmt = con.prepareStatement("select IDKMKTRNLM from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=? and KDKMKTRNLM=?");
    			ListIterator li = v_SearchDbMk_findMkError.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				//System.out.println(brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String thsms = st.nextToken();
    				String kdpst = st.nextToken();
    				String kdkmk = st.nextToken();
    				stmt.setString(1, thsms);
    				stmt.setString(2, kdpst);
    				stmt.setString(3, kdkmk);
    				rs = stmt.executeQuery();
    				rs.next();
    				int idkmk = rs.getInt(1);
    				li.set(brs+"`"+idkmk);
    			}
    			stmt = con.prepareStatement("SELECT KODE_PENGGABUNGAN FROM USG.CLASS_POOL where THSMS=? and IDKMK=?");
    			li = v_SearchDbMk_findMkError.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String thsms = st.nextToken();
    				String kdpst = st.nextToken();
    				String kdkmk = st.nextToken();
    				String idkmk_cp = st.nextToken();
    				stmt.setString(1, thsms);
    				stmt.setString(2, idkmk_cp);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					String kode_gb = rs.getString(1);
    					li.set(brs+"`"+kode_gb);
    				}
    				else {
    					//ngga ada kode penggabungan MAKA NON FIXABLE
    					li.remove();
    					liu.add(brs);
    				}
    			}	
    			if(v_SearchDbMk_findMkError!=null && v_SearchDbMk_findMkError.size()>0) {
    				stmt = con.prepareStatement("SELECT IDKMK FROM USG.CLASS_POOL where THSMS=? and KODE_PENGGABUNGAN=? and KDPST=?");
    				li = v_SearchDbMk_findMkError.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				String thsms = st.nextToken();
        				String kdpst = st.nextToken();
        				String kdkmk = st.nextToken();
        				String idkmk_cp = st.nextToken();
        				String kode_gab = st.nextToken();
        				stmt.setString(1, thsms);
        				stmt.setString(2, kode_gab);
        				stmt.setString(3, kdpst);
        				rs = stmt.executeQuery();
        				if(rs.next()) {
        					int idkmk_sesuai_prodi = rs.getInt(1);
        					li.set(brs+"`"+idkmk_sesuai_prodi);
        				}
        				else {
        					//unfixable
        					li.remove();
        					liu.add(brs);
        				}
        			}	
    			}
    			if(v_SearchDbMk_findMkError!=null && v_SearchDbMk_findMkError.size()>0) {
    				stmt = con.prepareStatement("SELECT * from MAKUL where IDKMKMAKUL=?");
    				li = v_SearchDbMk_findMkError.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				String thsms = st.nextToken();
        				String kdpst = st.nextToken();
        				String kdkmk = st.nextToken();
        				String idkmk_cp = st.nextToken();
        				String kode_gab = st.nextToken();
        				String idkmk_sesuai_prodi = st.nextToken();
        				stmt.setLong(1, Long.parseLong(idkmk_sesuai_prodi));
        				rs = stmt.executeQuery();
        				rs.next();
        				String kdkmk_sesuai_prodi = rs.getString("KDKMKMAKUL");
        				int sksmk_sesuai_prodi = rs.getInt("SKSTMMAKUL")+rs.getInt("SKSPRMAKUL")+rs.getInt("SKSLPMAKUL");
        				li.set(brs+"`"+kdkmk_sesuai_prodi+"`"+sksmk_sesuai_prodi);
        			}
        			
        			stmt = con.prepareStatement("UPDATE IGNORE TRNLM set KDKMKTRNLM=?,SKSMKTRNLM=?,IDKMKTRNLM=? where THSMSTRNLM=? and  KDPSTTRNLM=? and KDKMKTRNLM=?");
        			li = v_SearchDbMk_findMkError.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				//System.out.print(brs);
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				String thsms = st.nextToken();
        				String kdpst = st.nextToken();
        				String kdkmk = st.nextToken();
        				String idkmk_cp = st.nextToken();
        				String kode_gab = st.nextToken();
        				String idkmk_sesuai_prodi = st.nextToken();
        				String kdkmk_sesuai_prodi = st.nextToken();
        				String sksmk_sesuai_prodi = st.nextToken();
        				stmt.setString(1, kdkmk_sesuai_prodi);
        				stmt.setInt(2, Integer.parseInt(sksmk_sesuai_prodi));
        				stmt.setInt(3, Integer.parseInt(idkmk_sesuai_prodi));
        				stmt.setString(4, thsms);
        				stmt.setString(5, kdpst);
        				stmt.setString(6, kdkmk);
        				int i = stmt.executeUpdate();
        				//System.out.println("="+i);
        			}
        			//proses diatas suka ada yg 
        			stmt = con.prepareStatement("DELETE FROM TRNLM where THSMSTRNLM=? and  KDPSTTRNLM=? and KDKMKTRNLM=?");
        			li = v_SearchDbMk_findMkError.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				//System.out.print(brs);
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				String thsms = st.nextToken();
        				String kdpst = st.nextToken();
        				String kdkmk = st.nextToken();
        				String idkmk_cp = st.nextToken();
        				String kode_gab = st.nextToken();
        				String idkmk_sesuai_prodi = st.nextToken();
        				String kdkmk_sesuai_prodi = st.nextToken();
        				String sksmk_sesuai_prodi = st.nextToken();
        				stmt.setString(1, thsms);
        				stmt.setString(2, kdpst);
        				stmt.setString(3, kdkmk);
        				int i = stmt.executeUpdate();
        				//System.out.println("="+i);
        			}
    			}
        	}
        	catch(ConcurrentModificationException e) {
        		e.printStackTrace();
        	}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
        	catch(Exception e) {
        		e.printStackTrace();
        	}
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}
    	}
    	if(v_unable_to_fix.size()<1) {
    		v_unable_to_fix = null;
    	}
    	return v_unable_to_fix;
    }
    
    public static Vector getNpmYgAdaDiTrlsmTapiTidakAdaDiTopikPengajuan(String stmhs, boolean update_overview_table) {
    	Vector v = null;
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		String based_thsms = Constant.getValue("BASED_THSMS");
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//1. set npm asal at civitas
			stmt = con.prepareStatement("SELECT distinct THSMS,KDPST,NPMHS,NMMHSMSMHS,SMAWLMSMHS FROM TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS left join TOPIK_PENGAJUAN on NPMHS=CREATOR_NPM where THSMS>=? and STMHS=? and CREATOR_NPM is null;");
			stmt.setString(1, based_thsms);
			stmt.setString(2, stmhs);
			rs = stmt.executeQuery();
			v = new Vector();
			ListIterator li = v.listIterator();
			while(rs.next()) {
				String thsms = rs.getString(1);
				String kdpst = rs.getString(2);
				String npmhs = rs.getString(3);
				String nmmhs = rs.getString(4);
				String smawl = rs.getString(5);
				li.add(npmhs+"`"+kdpst+"`"+nmmhs+"`"+smawl+"`"+thsms);
			}
			if(v.size()>0) {
				li = v.listIterator();
				while(li.hasNext()) {
					String baris = (String)li.next();
					StringTokenizer st = new StringTokenizer(baris,"`");
					String npmhs = st.nextToken();
					long idobj = Checker.getObjectId(npmhs);
					li.set(baris+"`"+idobj);
				}
				
				/*
				 * KALO Mo DITAMBAHKAN TMP TABEL UNTUK LIST MHSny disini
				 */
				if(update_overview_table) {
					if(stmhs.equalsIgnoreCase("L")) {
						//reset prev value 
						stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set ERROR_TRLSM_VS_PENGAJUAN=? where THSMS>=?");
						stmt.setBoolean(1, false);
						stmt.setString(2, based_thsms);
						stmt.executeUpdate();
						
						stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set ERROR_TRLSM_VS_PENGAJUAN=? where THSMS=? and ID_OBJ=?");
					}
					else {
						//reserved buat yg laen
					}
					li = v.listIterator();
					while(li.hasNext()) {
						String baris = (String)li.next();
						//System.out.println(baris);
						StringTokenizer st = new StringTokenizer(baris,"`");
						String npmhs = st.nextToken();
						String kdpst = st.nextToken();
						String nmmhs = st.nextToken();
						String smawl = st.nextToken();
						String thsms = st.nextToken();
						String idobj = st.nextToken();
						stmt.setBoolean(1, true);
						stmt.setString(2, thsms);
						stmt.setLong(3, Long.parseLong(idobj));
						stmt.executeUpdate();
					}	
				}
				
			}
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return v;
    	
    }
 
    public static String returnUrlExtForPassingParamForProfileCivitasr(String npmhs) {
    	//System.out.println("npmhs="+npmhs);
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String url_ext="";
    	int objId = 0;
		String nmm = null;
		
		String kdpst = null;
		int obj_lvl =  0;
		String cmd =  "dashboard";
		//String submit = null;
		String malaikat = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//System.out.println("npmgs = "+npmhs);
    		String sql_cmd="select a.ID_OBJ,OBJ_LEVEL,KDPSTMSMHS,NMMHSMSMHS,MALAIKAT from CIVITAS a inner join OBJECT b on a.ID_OBJ=b.ID_OBJ where NPMHSMSMHS=?";
    		stmt = con.prepareStatement(sql_cmd);
    		stmt.setString(1, npmhs);
    		rs = stmt.executeQuery();
    		rs.next();
    		objId = rs.getInt(1);
    		obj_lvl =  rs.getInt(2);
    		kdpst = rs.getString(3);
    		nmm = rs.getString(4);
    		malaikat = rs.getString(5);
    		url_ext="cmd="+cmd+"&id_obj="+objId+"&nmm="+nmm+"&npm="+npmhs+"&kdpst="+kdpst+"&obj_lvl="+obj_lvl+"&malaikat="+malaikat;
    		
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return url_ext;
    	
    
    }
    
    
    public static void sinkTopikPengajuanRequestDgnTabelOverviewSebaranTrlsm(String tipe_pengajuan, String target_thsms) {
    	boolean show_angel = Checker.showAngel();
    	//System.out.println("show angel="+show_angel);
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	int tot_req = 0;
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
			con = ds.getConnection();
			Vector v = new Vector();
			ListIterator li = v.listIterator();
			//1. get distinct id_obj
			if(show_angel) {
				stmt = con.prepareStatement("select distinct CREATOR_OBJ_ID from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=?");	
			}
			else {
				stmt = con.prepareStatement("select distinct CREATOR_OBJ_ID from TOPIK_PENGAJUAN inner join CIVITAS on CREATOR_NPM=NPMHSMSMHS where TARGET_THSMS_PENGAJUAN=? and MALAIKAT=false");
			}
			stmt.setString(1, target_thsms);
			rs = stmt.executeQuery();
			while(rs.next()) {
				li.add(""+rs.getInt(1));
			}
			if(v.size()>0) {
				li = v.listIterator();
				if(show_angel) {
					stmt = con.prepareStatement("select count(*) from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_OBJ_ID=? and BATAL=?");
				}
				else {
					stmt = con.prepareStatement("select count(*) from TOPIK_PENGAJUAN inner join CIVITAS on CREATOR_NPM=NPMHSMSMHS where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_OBJ_ID=? and BATAL=? and MALAIKAT=false");
				}
				while(li.hasNext()) {
					String idobj = (String)li.next();
					stmt.setString(1, target_thsms);
					stmt.setString(2, tipe_pengajuan);
					stmt.setLong(3, Long.parseLong(idobj));
					stmt.setBoolean(4, false);
					rs = stmt.executeQuery();
					int i = 0;
					if(rs.next()) {
						i = rs.getInt(1);
					}
					li.set(idobj+"`"+i);
					tot_req = tot_req+i;
					
				}
				
				//2. get list unapproved
				li = v.listIterator();
				if(show_angel) {
					stmt = con.prepareStatement("select CREATOR_NPM from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_OBJ_ID=? and LOCKED=? and BATAL=? and REJECTED is null ");	
				}
				else {
					stmt = con.prepareStatement("select CREATOR_NPM from TOPIK_PENGAJUAN inner join CIVITAS on CREATOR_NPM=NPMHSMSMHS where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_OBJ_ID=? and LOCKED=? and BATAL=? and REJECTED is null and MALAIKAT=false");
				}
				
				while(li.hasNext()) {
					String tkn_npm = null;
					int tot_mhs = 0;
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String idobj = st.nextToken();
					String tot = st.nextToken();
					stmt.setString(1, target_thsms);
					stmt.setString(2, tipe_pengajuan);
					stmt.setLong(3, Long.parseLong(idobj));
					stmt.setBoolean(4, false);
					stmt.setBoolean(5, false);
					rs = stmt.executeQuery();
					while(rs.next()) {
						tot_mhs++;
						String npmhs = rs.getString(1);
						if(tkn_npm == null) {
							tkn_npm = new String(npmhs);
						}
						else {
							tkn_npm = tkn_npm +","+npmhs;
						}
					}
					li.set(brs+"`"+tot_mhs+"`"+tkn_npm);
					//System.out.println(brs+"`"+tot_mhs+"`"+tkn_npm);
				}
				//reset data sebelumnya, sebelum di update dgn yg baru, karena kalo ngga ada mhs ril ngga ke ikut ke update
				stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_"+tipe_pengajuan.toUpperCase()+"_REQ=?,TOT_"+tipe_pengajuan.toUpperCase()+"_REQ_UNAPPROVED=?,LIST_NPM_"+tipe_pengajuan.toUpperCase()+"_UNAPPROVED=? where THSMS=?");
				stmt.setLong(1,0);
				stmt.setLong(2,0);
				stmt.setNull(3, java.sql.Types.VARCHAR);
				stmt.setString(4, target_thsms);
				stmt.executeUpdate();
				//update overview tabe;
				li = v.listIterator();
				stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_"+tipe_pengajuan.toUpperCase()+"_REQ=?,TOT_"+tipe_pengajuan.toUpperCase()+"_REQ_UNAPPROVED=?,LIST_NPM_"+tipe_pengajuan.toUpperCase()+"_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String idobj = st.nextToken();
					String ttreq = st.nextToken();
					String ttwip = st.nextToken();
					String tkn_wip = st.nextToken();
					stmt.setLong(1,Long.parseLong(ttreq));
					stmt.setLong(2,Long.parseLong(ttwip));
					if(Checker.isStringNullOrEmpty(tkn_wip)) {
						stmt.setNull(3, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(3, tkn_wip);
					}
					stmt.setLong(4, Long.parseLong(idobj));
					stmt.setString(5, target_thsms);
					
					stmt.executeUpdate();
				}
			}
			//System.out.println("tot="+tot_req);
			
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	
    	
    }
    
    
    public static int hapusPengajuanPindahProdiTanpaTujuan(String tipe_pengajuan, String target_thsms) {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	int updated = 0;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			Vector v = new Vector();
			ListIterator li = v.listIterator();
			stmt = con.prepareStatement("delete from TOPIK_PENGAJUAN where TIPE_PENGAJUAN='PINDAH_PRODI' and TARGET_KDPST is null and TARGET_THSMS_PENGAJUAN=?");	
			stmt.setString(1, target_thsms);
			updated = stmt.executeUpdate();	
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return updated;
    	
    }
    
    
    
    public static int populateTableTopikPengajuanKartuUjian(String target_thsms) {
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	int updated = 0;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			Vector v = null;
			ListIterator li = null;
			stmt = con.prepareStatement("select KDPST,NPMHS,b.ID_OBJ,NMMHSMSMHS from DAFTAR_ULANG a inner join CIVITAS b on NPMHS=NPMHSMSMHS where THSMS=?");	
			stmt.setString(1, target_thsms);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					String kdpst = rs.getString(1);
					String npmhs = rs.getString(2);
					String idobj = rs.getString(3);
					String nmmhs = rs.getString(4);
					li.add(idobj+"~"+kdpst+"~"+npmhs+"~"+nmmhs);
				}
				while(rs.next());
			}
			if(v!=null) {
				//get approval info
				stmt = con.prepareStatement("select TKN_JABATAN_VERIFICATOR,TKN_VERIFICATOR_ID from KARTU_PESERTA_UJIAN_RULES where THSMS=? and KDPST=?");
				li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"~");
					String idobj = st.nextToken();
					String kdpst = st.nextToken();
					String npmhs = st.nextToken();
					String nmmhs = st.nextToken();
					stmt.setString(1, target_thsms);
					stmt.setString(2, kdpst);
					rs = stmt.executeQuery();
					if(rs.next()) {
						String tkn_jabatan = rs.getString(1);
						String tkn_objid = rs.getString(2); //objid verificator
						li.set(brs+"~"+tkn_jabatan+"~"+tkn_objid);
					}
					else {
						li.remove();
					}
				}
				//kalo null berarti blum ada rulesnua
				if(v!=null) {
					Vector vf = new Vector();
					ListIterator lif = vf.listIterator();
					//get tipe ujian 
					stmt = con.prepareStatement("select KODE_UJIAN,NAMA_UJIAN from TIPE_KARTU_UJIAN where KDPST=? and AKTIF=?");
					
					li = v.listIterator();
					while(li.hasNext()) {
						String brs = (String)li.next();
						StringTokenizer st = new StringTokenizer(brs,"~");
						String idobj = st.nextToken();
						String kdpst = st.nextToken();
						String npmhs = st.nextToken();
						String nmmhs = st.nextToken();
						String tkn_jabat = st.nextToken();
						String tkn_objid = st.nextToken();
						stmt.setString(1, kdpst);
						stmt.setBoolean(2, true);
						rs = stmt.executeQuery();
						if(rs.next()) {
							do {
								String kode_uji = rs.getString(1);
								String nmm_uji = rs.getString(2);
								lif.add(brs+"~"+kode_uji+"~"+nmm_uji);	
							}
							while(rs.next());
						}
						else {
							li.remove();
						}
					}	
					if(vf.size()>0) {
						//kalo kosong berarti tipe ujian blum diisi
						
						//1. update dulu
						String sql_cmd = "update TOPIK_PENGAJUAN_KARTU_UJIAN set TOKEN_TARGET_OBJ_NICKNAME=?,TOKEN_TARGET_OBJID=?,SHOW_AT_TARGET=? where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and ISI_TOPIK_PENGAJUAN=? and CREATOR_NPM=?";
						stmt = con.prepareStatement(sql_cmd);
						li = vf.listIterator();
						while(li.hasNext()) {
							String brs = (String)li.next();
							StringTokenizer st = new StringTokenizer(brs,"~");
							String idobj = st.nextToken();
							String kdpst = st.nextToken();
							String npmhs = st.nextToken();
							String nmmhs = st.nextToken();
							String tkn_jabat = st.nextToken();
							String tkn_objid = st.nextToken();
							String kode_uji = st.nextToken();
							String nmm_uji = st.nextToken();
							stmt.setString(1, tkn_jabat);
							stmt.setString(2, tkn_objid);
							stmt.setBoolean(3, true);
							stmt.setString(4, target_thsms);
							stmt.setString(5, kode_uji);
							stmt.setString(6, nmm_uji);
							stmt.setString(7, npmhs);
							int i = stmt.executeUpdate();
							if(i>0) {
								updated++;
								li.remove();
							}
						}
						if(vf!=null && vf.size()>0) {
							//sisa gagal update == insert
							sql_cmd = "insert ignore into TOPIK_PENGAJUAN_KARTU_UJIAN (TARGET_THSMS_PENGAJUAN,TIPE_PENGAJUAN,ISI_TOPIK_PENGAJUAN,TOKEN_TARGET_OBJ_NICKNAME,TOKEN_TARGET_OBJID,CREATOR_OBJ_ID,CREATOR_NPM,CREATOR_NMM,SHOW_AT_TARGET,CREATOR_KDPST)" + 
									"values(?,?,?,?,?,?,?,?,?,?)";
							stmt = con.prepareStatement(sql_cmd);
							li = vf.listIterator();
							while(li.hasNext()) {
								String brs = (String)li.next();
								StringTokenizer st = new StringTokenizer(brs,"~");
								String idobj = st.nextToken();
								String kdpst = st.nextToken();
								String npmhs = st.nextToken();
								String nmmhs = st.nextToken();
								String tkn_jabat = st.nextToken();
								String tkn_objid = st.nextToken();
								String kode_uji = st.nextToken();
								String nmm_uji = st.nextToken();
								
								stmt.setString(1, target_thsms);
								stmt.setString(2, kode_uji);
								stmt.setString(3, nmm_uji);
								stmt.setString(4, tkn_jabat);
								stmt.setString(5, tkn_objid);
								stmt.setInt(6, Integer.parseInt(idobj));
								stmt.setString(7, npmhs);
								stmt.setString(8, nmmhs);
								stmt.setBoolean(9, true);
								stmt.setString(10, kdpst);
								updated = updated + stmt.executeUpdate();
								//System.out.println("updated="+updated);
							}
						}
						
						
					}
				}
			}
			
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return updated;
    	
    }

   public static void hapusFileAtTmpFolder() {
	   
    	File tmp_folder = new File(Constant.getVelueFromConstantTable("FOLDER_TMP"));
    	
    	try {
    		FileUtils.cleanDirectory(tmp_folder); 	
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		
    	
    }
}

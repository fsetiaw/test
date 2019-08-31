package beans.dbase.pengajuan;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.Converter;

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

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class SearchDbPengajuan
 */
@Stateless
@LocalBean
public class SearchDbPengajuan extends SearchDb {
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
    public SearchDbPengajuan() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbPengajuan(String operatorNpm) {
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
    public SearchDbPengajuan(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    
    /*
     * !!DEPRECATED - ganti v1
     */
    public Vector getListNpmAndStatusPengajuan(String target_thsms, String fullname_table_rules, Vector v_scope_id) {
//    	boolean show_angel = Checker.showAngel();
    	String tipe_pengajuan = fullname_table_rules.replace("_RULES", "");
    	String title_pengajuan = tipe_pengajuan.replace("_", " ");
    	Vector v = null;
    	ListIterator li = null;
    	ListIterator lid = null;
    	ListIterator lip = null;
    	if(v_scope_id!=null) {
    		v = new Vector();
    		li = v.listIterator();
    		//System.out.println("v_scope_id-"+v_scope_id.size());
    		Vector v_scope_prodi = Converter.convertVscopeidToKdpst(v_scope_id);
    		//System.out.println("v_scope_prodi-"+v_scope_prodi.size());
    		lid = v_scope_id.listIterator();
    		lip = v_scope_prodi.listIterator();
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			//stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_OBJ_ID=? and BATAL=? and (LOCKED=? and REJECTED IS NOT NULL)");
    			stmt = con.prepareStatement("select CREATOR_NPM,CREATOR_NMM,UPDTM,APPROVED,LOCKED,REJECTED,MALAIKAT from TOPIK_PENGAJUAN inner join CIVITAS on CREATOR_NPM=NPMHSMSMHS  where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_OBJ_ID=? and BATAL=?");
    			while(lid.hasNext()) {
    				String brs1 = (String)lid.next();
    				String brs2 = (String)lip.next();
    				StringTokenizer st1 = new StringTokenizer(brs1,"`");
    				StringTokenizer st2 = new StringTokenizer(brs2,"`");
    				String kmp = st1.nextToken();
    				li.add(kmp);
    				Vector vtmp = new Vector();
    				ListIterator litmp = vtmp.listIterator();
    				st2.nextToken(); //idem kmp
    				if(st1.hasMoreTokens()) {
    					
    					while(st1.hasMoreTokens()) {
    						String list_mhs_info = "";
    						String id = st1.nextToken();
    						String kdpst = st2.nextToken();
    						stmt.setString(1, target_thsms);
    						stmt.setString(2, tipe_pengajuan);
    						stmt.setInt(3, Integer.parseInt(id));
    						stmt.setBoolean(4, false);
    						//stmt.setBoolean(5, true);
    						rs = stmt.executeQuery();
    						while(rs.next()) {
    							String npm = rs.getString("CREATOR_NPM");
    							String nmm = rs.getString("CREATOR_NMM");
    							String updtm = ""+rs.getTimestamp("UPDTM");
    							String approved = ""+rs.getString("APPROVED");
    							String locked = ""+rs.getBoolean("LOCKED");
    							String rejected = ""+rs.getString("REJECTED");
    							String malaikat = ""+rs.getBoolean("MALAIKAT");
    							//if(approved==null || Checker.isStringNullOrEmpty(approved)) {
    							//	approved = "false";
    								//rejected = "true";
    							//}
    							//else if(!Checker.isStringNullOrEmpty(rejected)) {
    							//	rejected = "true";
    							//}
    							//else if(!Checker.isStringNullOrEmpty(approved) && (locked!=null&&locked.equalsIgnoreCase("true"))){
    							//	approved = "true";
    							//}
    							list_mhs_info = list_mhs_info+"`"+npm+"`"+nmm+"`"+approved+"`"+locked+"`"+rejected+"`"+updtm+"`"+malaikat;
    						}
    						litmp.add(kmp+"`"+id+"`"+kdpst+"`"+list_mhs_info);
    					}
    				}
    				li.add(vtmp);
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
    		finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		    if (con!=null) try { con.close();} catch (Exception ignore){}
        	}
    	}
    	//System.out.println("v size--"+v.size());
    	return v;
    }
    
    public Vector getListNpmAndStatusPengajuan_v1(String target_thsms, String fullname_table_rules, Vector v_scope_id, String target_kampus) {
    	boolean show_angel = Checker.showAngel();
    	String tipe_pengajuan = fullname_table_rules.replace("_RULES", "");
    	String title_pengajuan = tipe_pengajuan.replace("_", " ");
    	Vector v = null;
    	ListIterator li = null;
    	ListIterator lid = null;
    	ListIterator lip = null;
    	int tot=0;
    	if(v_scope_id!=null) {
    		v = new Vector();
    		li = v.listIterator();
    		//System.out.println("v_scope_id-"+v_scope_id.size());
    		//System.out.println("target_kampus="+target_kampus);
    		Vector v_scope_prodi = Converter.convertVscopeidToKdpst(v_scope_id,target_kampus);
    		//System.out.println("v_scope_prodi-"+v_scope_prodi.size());
    		lid = v_scope_id.listIterator();
    		lip = v_scope_prodi.listIterator();
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			//stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_OBJ_ID=? and BATAL=? and (LOCKED=? and REJECTED IS NOT NULL)");
    			if(show_angel) {
    				stmt = con.prepareStatement("select CREATOR_OBJ_ID,CREATOR_NPM,CREATOR_NMM,UPDTM,APPROVED,LOCKED,REJECTED,MALAIKAT from TOPIK_PENGAJUAN inner join CIVITAS on CREATOR_NPM=NPMHSMSMHS inner join OBJECT on CREATOR_OBJ_ID=OBJECT.ID_OBJ where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_OBJ_ID=? and BATAL=? and KODE_KAMPUS_DOMISILI=?");
    			}
    			else {
    				stmt = con.prepareStatement("select CREATOR_OBJ_ID,CREATOR_NPM,CREATOR_NMM,UPDTM,APPROVED,LOCKED,REJECTED,MALAIKAT from TOPIK_PENGAJUAN inner join CIVITAS on CREATOR_NPM=NPMHSMSMHS inner join OBJECT on CREATOR_OBJ_ID=OBJECT.ID_OBJ where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_OBJ_ID=? and BATAL=? and KODE_KAMPUS_DOMISILI=? and MALAIKAT=false");	
    			}
    			
    			while(lid.hasNext() && lip.hasNext()) {
    				String brs1 = (String)lid.next();
    				//System.out.println("baris1="+brs1);
    				String brs2 = (String)lip.next();
    				//System.out.println("baris2="+brs2);
    				StringTokenizer st1 = new StringTokenizer(brs1,"`");
    				StringTokenizer st2 = new StringTokenizer(brs2,"`");
    				String kmp = st1.nextToken();
    				li.add(kmp);
    				Vector vtmp = new Vector();
    				ListIterator litmp = vtmp.listIterator();
    				st2.nextToken(); //idem kmp
    				if(st1.hasMoreTokens()) {
    					
    					while(st1.hasMoreTokens()&&st2.hasMoreTokens()) {
    						String list_mhs_info = "";
    						String id = st1.nextToken();
    						String kdpst = st2.nextToken();
    						stmt.setString(1, target_thsms);
    						stmt.setString(2, tipe_pengajuan);
    						stmt.setInt(3, Integer.parseInt(id));
    						stmt.setBoolean(4, false);
    						stmt.setString(5, target_kampus);
    						//stmt.setBoolean(5, true);
    						rs = stmt.executeQuery();
    						while(rs.next()) {
    							tot++;
    							//String objid = ""+rs.getLong(1);
    							String npm = rs.getString("CREATOR_NPM");
    							String nmm = rs.getString("CREATOR_NMM");
    							String updtm = ""+rs.getTimestamp("UPDTM");
    							String approved = ""+rs.getString("APPROVED");
    							String locked = ""+rs.getBoolean("LOCKED");
    							String rejected = ""+rs.getString("REJECTED");
    							String malaikat = ""+rs.getBoolean("MALAIKAT");
    							//if(approved==null || Checker.isStringNullOrEmpty(approved)) {
    							//	approved = "false";
    							//	rejected = "true";
    							//}
    							//else {
    							//	approved = "true";
    							//	rejected = "false";
    							//}
    							list_mhs_info = list_mhs_info+"`"+npm+"`"+nmm+"`"+approved+"`"+locked+"`"+rejected+"`"+updtm+"`"+malaikat;
    						}
    						litmp.add(kmp+"`"+id+"`"+kdpst+"`"+list_mhs_info);
    						System.out.println("iki tot= "+kmp+"`"+id+"`"+kdpst+"`"+tot);
    					}
    				}
    				li.add(vtmp);
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
    		finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		    if (con!=null) try { con.close();} catch (Exception ignore){}
        	}
    	}
    	//System.out.println("v size--"+tot);
    	return v;
    }
    
    public String get1stAtKmpYgAdaIsinya(String target_thsms, String fullname_table_rules, Vector v_scope_id) {
    	String tipe_pengajuan = fullname_table_rules.replace("_RULES", "");
    	String title_pengajuan = tipe_pengajuan.replace("_", " ");
    	Vector v = null;
    	ListIterator li = null;
    	ListIterator lid = null;
    	ListIterator lip = null;
    	boolean ada_mhs = false;
    	String at_kmp = null;
    	if(v_scope_id!=null) {
    		v = new Vector();
    		li = v.listIterator();
    		//System.out.println("v_scope_id-"+v_scope_id.size());
    		Vector v_scope_prodi = Converter.convertVscopeidToKdpst(v_scope_id);
    		//System.out.println("v_scope_prodi-"+v_scope_prodi.size());
    		lid = v_scope_id.listIterator();
    		lip = v_scope_prodi.listIterator();
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			//stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_OBJ_ID=? and BATAL=? and (LOCKED=? and REJECTED IS NOT NULL)");
    			stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_OBJ_ID=? and BATAL=?");
    			while(lid.hasNext() && !ada_mhs) {
    				String brs1 = (String)lid.next();
    				String brs2 = (String)lip.next();
    				StringTokenizer st1 = new StringTokenizer(brs1,"`");
    				StringTokenizer st2 = new StringTokenizer(brs2,"`");
    				String kmp = st1.nextToken();
    				li.add(kmp);
    				//Vector vtmp = new Vector();
    				//ListIterator litmp = vtmp.listIterator();
    				st2.nextToken(); //idem kmp
    				if(st1.hasMoreTokens()) {
    					
    					while(st1.hasMoreTokens() && !ada_mhs) {
    						String list_mhs_info = "";
    						String id = st1.nextToken();
    						String kdpst = st2.nextToken();
    						stmt.setString(1, target_thsms);
    						stmt.setString(2, tipe_pengajuan);
    						stmt.setInt(3, Integer.parseInt(id));
    						stmt.setBoolean(4, false);
    						//stmt.setBoolean(5, true);
    						rs = stmt.executeQuery();
    						while(rs.next() && !ada_mhs) {
    							String npm = rs.getString("CREATOR_NPM");
    							String nmm = rs.getString("CREATOR_NMM");
    							String updtm = ""+rs.getTimestamp("UPDTM");
    							String approved = ""+rs.getString("APPROVED");
    							String locked = ""+rs.getBoolean("LOCKED");
    							String rejected = ""+rs.getString("REJECTED");
    							if(approved==null || Checker.isStringNullOrEmpty(approved)) {
    								approved = "false";
    								rejected = "true";
    							}
    							else {
    								ada_mhs = true;
    								approved = "true";
    								rejected = "false";
    								at_kmp = new String(kmp);
    							}
    							//list_mhs_info = list_mhs_info+"`"+npm+"`"+nmm+"`"+approved+"`"+locked+"`"+rejected+"`"+updtm;
    						}
    						//litmp.add(kmp+"`"+id+"`"+kdpst+"`"+list_mhs_info);
    					}
    				}
    				//li.add(vtmp);
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
    		finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		    if (con!=null) try { con.close();} catch (Exception ignore){}
        	}
    	}
    	return at_kmp;
    }
    
    
    public Vector getNpmYgAdaDiTrlsmTapiTidakAdaDiTopikPengajuan(Vector v_scope_id, int limit_per_page, int page, int search_range, String starting_smawl) {
    	Vector vout = new Vector();
    	ListIterator lout = vout.listIterator();
    	// search_range = before +1
    	int range_page = search_range/limit_per_page; //range_page = angka dibawah pad google search 
    	boolean ada_prev = false;
    	boolean ada_next = false;
    	if(page-3>0) {
    		ada_prev = true;
    	}
    	
    	try {
    		String addon_cmd = "";
			ListIterator li = v_scope_id.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				//System.out.println("bare="+brs);
				StringTokenizer st = new StringTokenizer(brs,"`");
				String kmp = st.nextToken();
				boolean sy_mhs = false;
				while(st.hasMoreTokens()) {
					String id = st.nextToken();
					if(id.equalsIgnoreCase("own")) {
						//sbg mhs
						sy_mhs = true;
					}
					else {
						//sbg approval ato monitor
						if(addon_cmd.contains("ID_OBJ")) {
    						//klao ada value dari kampus lainnya
    						addon_cmd = addon_cmd+" OR ID_OBJ="+id;
    					}
    					else {
    						//first record
    						addon_cmd = addon_cmd+"ID_OBJ="+id;	
    					}
					}

				}
			}	
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			String cmd = "SELECT distinct THSMS,KDPST,NPMHS,NMMHSMSMHS FROM USG.TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS left join TOPIK_PENGAJUAN on NPMHS=CREATOR_NPM where THSMS>=? and STMHS='L' and CREATOR_NPM is null and ("+addon_cmd+") order by KDPST,NPMHS limit ?,?";
			//String cmd="SELECT CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS,CIVITAS.NMMHSMSMHS,CIVITAS.SMAWLMSMHS FROM CIVITAS  inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ left join TRNLP on NPMHSMSMHS=NPMHSTRNLP where SMAWLMSMHS>=? and STPIDMSMHS='P' and NPMHSTRNLP is null and  ("+addon_cmd+")  order by CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS limit ?,?";
			stmt = con.prepareStatement(cmd);
			//System.out.println("cmd="+cmd);
			stmt.setString(1,starting_smawl);
			stmt.setInt(2, (page-1)*limit_per_page);
			//System.out.println("offset="+(page-1)*limit_per_page);
			stmt.setInt(3, search_range+1);
			//System.out.println("search_range="+(search_range+1));
			rs = stmt.executeQuery();
			int tot_row = 0;
			if(rs!=null) {
				rs.last();
				tot_row = rs.getRow();
				rs.beforeFirst();
			}
			
			if(tot_row>range_page) {
				ada_next = true;
			}
			
			//lout.add("THSMS`PRODI`NPM`NAMA`SMAWL");
			lout.add(ada_prev+"`"+page+"/"+range_page+"`"+ada_next+"`"+tot_row);
			//System.out.println(ada_prev+"`"+page+"/"+range_page+"`"+ada_next);
			int tot_dat = 0;
			while(rs.next() && (tot_dat < limit_per_page)) {
				String thsms = rs.getString(1);
				String kdpst = rs.getString(2);
				String npmhs = rs.getString(3);
				String nmmhs = rs.getString(4);
				//String smawl = rs.getString(5);
				tot_dat++;
				lout.add(kdpst+"`"+npmhs+"`"+nmmhs+"`"+thsms);
				//System.out.println(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl);
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
    	return vout;
    }

}

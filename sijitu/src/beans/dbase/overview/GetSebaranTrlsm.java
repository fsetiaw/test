package beans.dbase.overview;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.Constant;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
 * Session Bean implementation class GetSebaranTrlsm
 */
@Stateless
@LocalBean
public class GetSebaranTrlsm extends SearchDb {
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
    public GetSebaranTrlsm() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public GetSebaranTrlsm(String operatorNpm) {
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
    public GetSebaranTrlsm(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public Vector getJumRequestDaftarUlang(Vector v_returnScopeProdiOnlySortByKampusWithListIdobj, String target_thsms) {
    	Vector vf = null;
    	//format scope 122 22201 MHS_TEKNIK_SIPIL_KAMPUS_JAMSOSTEK 122 C JST
    	/*
    	
    	*/
    	ListIterator lif = null;
    	ListIterator li = null;
    	/*
    	 * 
    	 */
    	try {
    		if(v_returnScopeProdiOnlySortByKampusWithListIdobj!=null && v_returnScopeProdiOnlySortByKampusWithListIdobj.size()>0) {
    			li = v_returnScopeProdiOnlySortByKampusWithListIdobj.listIterator();
    			vf = new Vector();
    			lif = vf.listIterator();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		//	seperate each kampus
    			
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kmp = st.nextToken();
    				String sql_list_objid = null;
    				if(st.hasMoreTokens()) {
    					sql_list_objid = new String();
    					do {
    						String objid = st.nextToken();
    						sql_list_objid = sql_list_objid+"ID_OBJ="+objid;
    						if(st.hasMoreElements()) {
    							sql_list_objid = sql_list_objid + " OR ";
    						}
    					}
    					while(st.hasMoreTokens());
    				}
    				if(sql_list_objid!=null) {
    					//sql_list_objid = "ID_OBJ=109";
    					//System.out.println(sql_list_objid);
    					stmt = con.prepareStatement("select COUNT(*) from DAFTAR_ULANG inner join CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and ("+sql_list_objid+")");
    					stmt.setString(1, target_thsms);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						long counter = rs.getInt(1);
    						lif.add(kmp+" "+counter);
    					}
    					else {
    						lif.add(kmp+" 0");
    					}
    				}
    				else {
    				//ignore = harus ada scope id_obj
    					lif.add(kmp+" 0");
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
    	return vf;
    }
    
    public Vector getNpmRequestDaftarUlang(Vector v_returnScopeProdiOnlySortByKampusWithListIdobj, String target_thsms) {
    	Vector vf = null;
    	//format scope 122 22201 MHS_TEKNIK_SIPIL_KAMPUS_JAMSOSTEK 122 C JST
    	/*
    	
    	*/
    	ListIterator lif = null;
    	ListIterator li = null;
    	/*
    	 * 
    	 */
    	try {
    		if(v_returnScopeProdiOnlySortByKampusWithListIdobj!=null && v_returnScopeProdiOnlySortByKampusWithListIdobj.size()>0) {
    			li = v_returnScopeProdiOnlySortByKampusWithListIdobj.listIterator();
    			vf = new Vector();
    			lif = vf.listIterator();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		//	seperate each kampus
    			
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kmp = st.nextToken();
    				String sql_list_objid = null;
    				if(st.hasMoreTokens()) {
    					sql_list_objid = new String();
    					do {
    						String objid = st.nextToken();
    						sql_list_objid = sql_list_objid+"ID_OBJ="+objid;
    						if(st.hasMoreElements()) {
    							sql_list_objid = sql_list_objid + " OR ";
    						}
    					}
    					while(st.hasMoreTokens());
    				}
    				if(sql_list_objid!=null) {
    					//sql_list_objid = "ID_OBJ=109";
    					//System.out.println(sql_list_objid);
    					stmt = con.prepareStatement("select DISTINCT NPMHS from DAFTAR_ULANG inner join CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and ("+sql_list_objid+")");
    					stmt.setString(1, target_thsms);
    					rs = stmt.executeQuery();
    					String tkn_npm = "";
    					while(rs.next()) {
    						tkn_npm = tkn_npm+"`"+rs.getString(1);
    						
    					}
    					lif.add(kmp+tkn_npm);
    				}
    				else {
    				//ignore = harus ada scope id_obj
    					lif.add(kmp);
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
    	return vf;
    }
    
    
    public Vector getSummaryDaftarUlangAndNoKrs(Vector v_returnScopeProdiOnlySortByKampusWithListIdobj, String target_thsms) {
    	/*
    	 * MERUPAKAN SUPERSET FN()
    	 * public Vector getSummaryNoKrs(Vector v_returnScopeProdiOnlySortByKampusWithListIdobj, String target_thsms) 
    	 * JADI PERHATIKAN BILA ADA PERUBAHAN DISNI APA ADA EFEK DIFUNGSI SATUNTA
    	 */
    	Vector vf = null;
    	Vector v_list_npm_approved = new Vector();
    	ListIterator liapproved = v_list_npm_approved.listIterator();
    	//format scope 122 22201 MHS_TEKNIK_SIPIL_KAMPUS_JAMSOSTEK 122 C JST
    	Vector v_kdpst_tot = null;
    	Vector v_kdpst_tot_unapproved = null;
    	Vector v_kdpst_npm_unapproved = null;
    	ListIterator litmp = null;
    	ListIterator lif = null;
    	ListIterator li = null;
    	//Vector v_daftar_ulang_rules = Checker.getRuleDaftarUlang(target_thsms);//li.add(kdpst+"`"+kdkmp+"`"+tknVerificatorNickname+"`"+urut);
    	/*
    	 * 
    	 */
    	try {
    		if(v_returnScopeProdiOnlySortByKampusWithListIdobj!=null && v_returnScopeProdiOnlySortByKampusWithListIdobj.size()>0) {
    			li = v_returnScopeProdiOnlySortByKampusWithListIdobj.listIterator();
    			vf = new Vector();
    			lif = vf.listIterator();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    			con = ds.getConnection();
    		//	seperate each kampus
    			int tot_req = 0;
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kmp = st.nextToken();
    				String sql_list_objid = null;
    				if(st.hasMoreTokens()) {
    					sql_list_objid = new String();
    					do {
    						String objid = st.nextToken();
    						sql_list_objid = sql_list_objid+"CIVITAS.ID_OBJ="+objid;
    						if(st.hasMoreElements()) {
    							sql_list_objid = sql_list_objid + " OR ";
    						}
    					}
    					while(st.hasMoreTokens());
    				}
    				if(sql_list_objid!=null) {
    					//sql_list_objid = "ID_OBJ=109";
    					//System.out.println(sql_list_objid);
    					stmt = con.prepareStatement("select * from DAFTAR_ULANG inner join CIVITAS on NPMHS=NPMHSMSMHS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where THSMS=? and ("+sql_list_objid+")");
    					stmt.setString(1, target_thsms);
    					rs = stmt.executeQuery();
    					String tkn_info_civ = "";
    					if(rs.next()) {
    						do {
    							tkn_info_civ = tkn_info_civ+"`"+rs.getString("NPMHS")+","+rs.getString("KDPSTMSMHS")+","+rs.getString("KODE_KAMPUS_DOMISILI")+","+rs.getString("TOKEN_APPROVAL")+","+rs.getLong("CIVITAS.ID_OBJ")+","+rs.getBoolean("ALL_APPROVED");
    						}
    						while(rs.next());
    						lif.add(kmp+tkn_info_civ);
    						//System.out.println(kmp+tkn_info_civ);
    					}
    					else {
    						lif.add(kmp);
    					}
    				}
    				else {
    				//ignore = harus ada scope id_obj
    					lif.add(kmp);
    				}
    			}
    				
    			
    			//itung tot_daftarulang_req
    			Vector v_unapproved = new Vector();
    			ListIterator liu = v_unapproved.listIterator();
    			lif = vf.listIterator();
    			//litmp = v_kdpst_tot.listIterator();
    			while(lif.hasNext()) {
    				String brs = (String)lif.next();
    				//System.out.println("original = "+brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kmp = st.nextToken();
    				String list_unapproved = new String(kmp);
    				while(st.hasMoreTokens()) {
    					String tkn_info_civ = st.nextToken();
    					
    					//System.out.println("tkn_info_civ = "+tkn_info_civ);
    					StringTokenizer st2 = new StringTokenizer(tkn_info_civ,",");
    					String npm = st2.nextToken();
    					String kdpst = st2.nextToken();
    					String kode_kmp = st2.nextToken();
    					String tkn_approved = st2.nextToken();
    					String id_obj = st2.nextToken();
    					String approved = st2.nextToken();
    					if(v_kdpst_tot==null) {
    						//first recoed
    						v_kdpst_tot = new Vector();
    						litmp = v_kdpst_tot.listIterator();
    						litmp.add(id_obj+"`"+kmp+"`"+kdpst+"`"+1);
    					}
    					else {
    						litmp = v_kdpst_tot.listIterator();
    						boolean match = false;
    						while(litmp.hasNext() && !match) {
    							String info=(String)litmp.next();
    							//System.out.println(info+" vs "+id_obj);
    							if(info.startsWith(id_obj+"`")) {
    								//System.out.println("true");
    								match = true;
    								StringTokenizer stt = new StringTokenizer(info,"`");
    								String tmp_idobj = stt.nextToken();
    								String tmp_kmp = stt.nextToken();
    								String tmp_kdpst = stt.nextToken();
    								String tmp_tot = stt.nextToken();
    								litmp.set(tmp_idobj+"`"+tmp_kmp+"`"+tmp_kdpst+"`"+(Integer.parseInt(tmp_tot)+1));
    								if(tmp_idobj.equalsIgnoreCase("102")) {
    									//System.out.println("info="+npm);
    									//System.out.println("upd="+tmp_idobj+"`"+tmp_kmp+"`"+tmp_kdpst+"`"+(Integer.parseInt(tmp_tot)+1));
    								}
    							}
    						}
    						if(!match) {
    							//belum ada record
    							litmp.add(id_obj+"`"+kmp+"`"+kdpst+"`"+1);
    						}
    					}
    					/*
    					ListIterator li3 = v_daftar_ulang_rules.listIterator();
    					boolean kdpst_kmp_match = false;
    					String tkn_approval_required = "";
    					while(li3.hasNext() && !kdpst_kmp_match) {
    						String brs_rules = (String)li3.next();
    						//System.out.println("rules="+brs_rules);
    						StringTokenizer st3 = new StringTokenizer(brs_rules,"`");
    						String kdpst_rules=st3.nextToken();
    						String kdkmp_rules=st3.nextToken();
    						String tknVerificatorNickname_rules=st3.nextToken();
    						String urut_rules=st3.nextToken();
    						if((kdpst.equalsIgnoreCase(kdpst_rules))&&(kode_kmp.equalsIgnoreCase(kdkmp_rules))) {
    							kdpst_kmp_match = true;
    							tkn_approval_required = new String(tknVerificatorNickname_rules);
    						}
    					}
    					if(kdpst_kmp_match) {
    						//System.out.println("tkn_approval_required="+tkn_approval_required);
    						StringTokenizer st4 = new StringTokenizer(tkn_approval_required,",");
    						boolean all_approved = true;
    						while(st4.hasMoreTokens()&&all_approved) {
    							String approvee = st4.nextToken();
    							if(!tkn_approved.contains(approvee)) {
    								all_approved = false;
    							}
    						}
    						*/
    					
    					//filter unapproved
    					boolean all_approved = Boolean.parseBoolean(approved);
    					if(!all_approved) {
    						list_unapproved=list_unapproved+"`"+id_obj+","+npm+","+kdpst+","+kode_kmp+","+tkn_approved;
    						if(v_kdpst_tot_unapproved==null) {
    							v_kdpst_tot_unapproved = new Vector();
    							ListIterator liun = v_kdpst_tot_unapproved.listIterator();
    							liun.add(id_obj+"`"+kmp+"`"+kdpst+"`"+1);
    							
    							//v_kdpst_npm_unapproved = new Vector();
    							//ListIterator liunpm = v_kdpst_npm_unapproved.listIterator();
    							//liunpm.add(id_obj+"`"+kmp+"`"+kdpst+"`"+npm);
    								
    						}
       	    				else {
       	    					ListIterator liun = v_kdpst_tot_unapproved.listIterator();
    	    					boolean match = false;
    	    					while(liun.hasNext() && !match) {
    	    						String info=(String)liun.next();
    	    						if(info.startsWith(id_obj+"`")) {
    	    							match = true;
    	    							StringTokenizer stt = new StringTokenizer(info,"`");
    	    							String tmp_idobj = stt.nextToken();
    	    							String tmp_kmp = stt.nextToken();
    	    							String tmp_kdpst = stt.nextToken();
    	    							String tmp_tot = stt.nextToken();
    	    							liun.set(tmp_idobj+"`"+tmp_kmp+"`"+tmp_kdpst+"`"+(Integer.parseInt(tmp_tot)+1));
    	    						}
    	    					}
    	    					if(!match) {
    	    						//belum ada record
    	    						liun.add(id_obj+"`"+kmp+"`"+kdpst+"`"+1);
    	    					}
    	    					
       	    				}	
    						//list npm
    						if(v_kdpst_npm_unapproved==null) {
    							v_kdpst_npm_unapproved = new Vector();
    							ListIterator liunpm = v_kdpst_npm_unapproved.listIterator();
    							liunpm.add(id_obj+"`"+kmp+"`"+kdpst+"`"+npm);
    								
    						}
    						else {
    							ListIterator liunpm = v_kdpst_npm_unapproved.listIterator();
    	    					boolean match = false;
    	    					while(liunpm.hasNext() && !match) {
    	    						String info=(String)liunpm.next();
    	    						if(info.startsWith(id_obj+"`")) {
    	    							match = true;
    	    							StringTokenizer stt = new StringTokenizer(info,"`");
    	    							String tmp_idobj = stt.nextToken();
    	    							String tmp_kmp = stt.nextToken();
    	    							String tmp_kdpst = stt.nextToken();
    	    							String tmp_tot = stt.nextToken();
    	    							liunpm.set(tmp_idobj+"`"+tmp_kmp+"`"+tmp_kdpst+"`"+tmp_tot+","+npm);
    	    						}
    	    					}
    	    					if(!match) {
    	    						//belum ada record
    	    						liunpm.add(id_obj+"`"+kmp+"`"+kdpst+"`"+npm);
    	    					}
    	    				}
    							
    					}
    					else {
    						liapproved.add(id_obj+"`"+npm+"`"+kdpst+"`"+kode_kmp+"`"+tkn_approved);
    					}
    					/*
    				}
    				
    				else {
    					//rulenya ngga ada jadi dianggap belum approed
    					//System.out.println("tkn_approval_required=null");
    					list_unapproved=list_unapproved+"`"+id_obj+","+npm+","+kdpst+","+kode_kmp+","+tkn_approved;
    					if(v_kdpst_tot_unapproved==null) {
							v_kdpst_tot_unapproved = new Vector();
							ListIterator liun = v_kdpst_tot_unapproved.listIterator();
							liun.add(id_obj+"`"+kmp+"`"+kdpst+"`"+1);
							
						}
   	    				else {
   	    					ListIterator liun = v_kdpst_tot_unapproved.listIterator();
	    					boolean match = false;
	    					while(liun.hasNext() && !match) {
	    						String info=(String)liun.next();
	    						if(info.startsWith(id_obj+"`")) {
	    							match = true;
	    							StringTokenizer stt = new StringTokenizer(info,"`");
	    							String tmp_idobj = stt.nextToken();
	    							String tmp_kmp = stt.nextToken();
	    							String tmp_kdpst = stt.nextToken();
	    							String tmp_tot = stt.nextToken();
	    							liun.set(tmp_idobj+"`"+tmp_kmp+"`"+tmp_kdpst+"`"+(Integer.parseInt(tmp_tot)+1));
	    						}
	    					}
	    					if(!match) {
	    						//belum ada record
	    						liun.add(id_obj+"`"+kmp+"`"+kdpst+"`"+1);
	    					}
	    				}
    					
    						
    					if(v_kdpst_npm_unapproved==null) {
							v_kdpst_npm_unapproved = new Vector();
							ListIterator liunpm = v_kdpst_npm_unapproved.listIterator();
							liunpm.add(id_obj+"`"+kmp+"`"+kdpst+"`"+npm);
							
						}
   	    				else {
   	    					ListIterator liunpm = v_kdpst_npm_unapproved.listIterator();
	    					boolean match = false;
	    					while(liunpm.hasNext() && !match) {
	    						String info=(String)liunpm.next();
	    						if(info.startsWith(id_obj+"`")) {
	    							match = true;
	    							StringTokenizer stt = new StringTokenizer(info,"`");
	    							String tmp_idobj = stt.nextToken();
	    							String tmp_kmp = stt.nextToken();
	    							String tmp_kdpst = stt.nextToken();
	    							String tmp_tot = stt.nextToken();
	    							liunpm.set(tmp_idobj+"`"+tmp_kmp+"`"+tmp_kdpst+"`"+tmp_tot+","+npm);
	    						}
	    					}
	    					if(!match) {
	    						//belum ada record
	    						liunpm.add(id_obj+"`"+kmp+"`"+kdpst+"`"+npm);
	    					}
	    				}
    				}
    					*/
    				}
    				lif.set(list_unapproved);
    				//System.out.println("final="+list_unapproved);
    			}
    			//update table OVERVIEW_SEBARAN_TRLSM
    			
    			//reset value OVERVIEW_SEBARAN_TRLSM, biar diisi dengan yg baru
    			if(v_returnScopeProdiOnlySortByKampusWithListIdobj!=null && v_returnScopeProdiOnlySortByKampusWithListIdobj.size()>0) {
    				stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_DAFTAR_ULANG_REQ=?,TOT_DAFTAR_ULANG_REQ_UNAPPROVED=?,LIST_NPM_DAFTAR_ULANG_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
    				li = v_returnScopeProdiOnlySortByKampusWithListIdobj.listIterator();
        			//vf = new Vector();
        			//lif = vf.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				String kmp = st.nextToken();
        				String sql_list_objid = null;
        				if(st.hasMoreTokens()) {
        					do {
        						String objid = st.nextToken();
        						stmt.setInt(1, 0);
        						stmt.setInt(2, 0);
        						stmt.setNull(3, java.sql.Types.VARCHAR);
        						stmt.setInt(4, Integer.parseInt(objid));
        						stmt.setString(5, target_thsms);
        						//System.out.println("reseting "+objid);
        						stmt.executeUpdate();
        					}
        					while(st.hasMoreTokens());
        				}
        			}
    			}	

    			
    			if(v_kdpst_tot!=null && v_kdpst_tot.size()>0) {
    				litmp = v_kdpst_tot.listIterator();
    				
    				//cek update dulu
    				stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_DAFTAR_ULANG_REQ=? where ID_OBJ=? and THSMS=?");
    				while(litmp.hasNext()) {
    					String baris = (String)litmp.next();
    					StringTokenizer st = new StringTokenizer(baris,"`");
    					String id = st.nextToken();
    					String kmp = st.nextToken();
    					String kdpst = st.nextToken();
    					String tot = st.nextToken();
    					stmt.setInt(1,Integer.parseInt(tot));
    					stmt.setLong(2, Long.parseLong(id));
    					stmt.setString(3, target_thsms);
    					int i = 0;
    					i = stmt.executeUpdate();
    					//System.out.println("updating "+kmp+" "+kdpst+" = "+tot);
    					if(i>0) {
    						litmp.remove();
    					}
    				}
    				//insert the rest
    				stmt = con.prepareStatement("insert into OVERVIEW_SEBARAN_TRLSM(ID_OBJ,THSMS,KDPST,TOT_DAFTAR_ULANG_REQ)values(?,?,?,?)");
    				if(v_kdpst_tot.size()>0) {
        				litmp = v_kdpst_tot.listIterator();
        				while(litmp.hasNext()) {
        					String baris = (String)litmp.next();
        					//System.out.println("--"+baris);
        					StringTokenizer st = new StringTokenizer(baris,"`");
        					String id = st.nextToken();
        					String kmp = st.nextToken();
        					String kdpst = st.nextToken();
        					String tot = st.nextToken();
        					
        					stmt.setLong(1, Long.parseLong(id));
        					stmt.setString(2, target_thsms);
        					stmt.setString(3, kdpst);
        					stmt.setInt(4,Integer.parseInt(tot));
        					int i = 0;
        					stmt.executeUpdate();
        			
        				}
    				}
    				//yg laennya update krn dah diinsert diatas recordnya
    				if(v_kdpst_tot_unapproved!=null && v_kdpst_tot_unapproved.size()>0) {
        				litmp = v_kdpst_tot_unapproved.listIterator();
        				
        				//cek update dulu
        				stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_DAFTAR_ULANG_REQ_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
        				while(litmp.hasNext()) {
        					String baris = (String)litmp.next();
        					StringTokenizer st = new StringTokenizer(baris,"`");
        					String id = st.nextToken();
        					String kmp = st.nextToken();
        					String kdpst = st.nextToken();
        					String tot = st.nextToken();
        					stmt.setInt(1,Integer.parseInt(tot));
        					stmt.setLong(2, Long.parseLong(id));
        					stmt.setString(3, target_thsms);
        					int i = 0;
        					i = stmt.executeUpdate();
        					if(i>0) {
        						litmp.remove();
        					}
        				}
    				}	
    				if(v_kdpst_tot_unapproved!=null && v_kdpst_npm_unapproved.size()>0) {
        				litmp = v_kdpst_npm_unapproved.listIterator();
        				
        				//cek update dulu
        				stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set LIST_NPM_DAFTAR_ULANG_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
        				while(litmp.hasNext()) {
        					String baris = (String)litmp.next();
        					StringTokenizer st = new StringTokenizer(baris,"`");
        					String id = st.nextToken();
        					String kmp = st.nextToken();
        					String kdpst = st.nextToken();
        					String listnpm = st.nextToken();
        					if(listnpm!=null && !Checker.isStringNullOrEmpty(listnpm)) {
        						stmt.setString(1,listnpm);
            					stmt.setLong(2, Long.parseLong(id));
            					stmt.setString(3, target_thsms);
            					int i = 0;
            					i = stmt.executeUpdate();
            					if(i>0) {
            						litmp.remove();
            					}
        					}
        					
        				}
    				}
    				
    			}
    			
    			//cek yg sudah daftar ulang tp ngga ada krs
    			if(v_list_npm_approved!=null && v_list_npm_approved.size()>0) {
    				stmt = con.prepareStatement("select NPMHSTRNLM from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=? and CLASS_POOL_UNIQUE_ID is not null");
    				liapproved = v_list_npm_approved.listIterator();
    				while(liapproved.hasNext()) {
    					String brs = (String)liapproved.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String idobj = st.nextToken();
    					String npm = st.nextToken();
    					String kdpst = st.nextToken();
    					String kode_kmp = st.nextToken();
    					String tkn_approved = st.nextToken();
    					stmt.setString(1, target_thsms);
    					stmt.setString(2, npm);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						liapproved.remove();
    					}
    					else {
    						//ngga punya krs
    						liapproved.set(kdpst+"`"+idobj+"`"+kode_kmp+"`"+npm);
    					}
    				}
    				//filter per kdpst
    				if(v_list_npm_approved!=null && v_list_npm_approved.size()>0) {
    					Vector vtmp = new Vector();
    					litmp = vtmp.listIterator();
    					v_list_npm_approved = Tool.removeDuplicateFromVector(v_list_npm_approved);
    					liapproved = v_list_npm_approved.listIterator();
    					boolean first = true;
    					String list_npm_no_krs = "";
    					String prev_objid = null;
						String prev_kdpst = null;
    					do {
    						String baris = (String)liapproved.next();
    						//System.out.println("baris="+baris);
    						StringTokenizer stt = new StringTokenizer(baris,"`");
    						String kdpst = stt.nextToken();
    						String objid = stt.nextToken();
    						String kmp = stt.nextToken();
    						String npm = stt.nextToken();
    						
    						if(first) {
    							first = false;
    							prev_objid=new String(objid);
    							prev_kdpst=new String(kdpst);
    							list_npm_no_krs = new String(npm);
    						}
    						else {
    							if(objid.equalsIgnoreCase(prev_objid)) {
    								list_npm_no_krs = list_npm_no_krs+","+npm;
    							}
    							else {
    								litmp.add(prev_objid+"`"+prev_kdpst+"`"+list_npm_no_krs);
    								//beda obj
    								prev_objid=new String(objid);
        							prev_kdpst=new String(kdpst);
        							list_npm_no_krs = new String(npm);
    							}
    						}
    						if(!liapproved.hasNext()) {
    							litmp.add(prev_objid+"`"+prev_kdpst+"`"+list_npm_no_krs);
    						}
    					}
    					while(liapproved.hasNext());
    					
    					//update table overview
    					//reset column LIST_NPM_DAFTAR_ULANG_BUT_NO_KRS sebelum update;
    					stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set LIST_NPM_DAFTAR_ULANG_BUT_NO_KRS=? where THSMS=?");
    					stmt.setNull(1, java.sql.Types.VARCHAR);
    					stmt.setString(2, target_thsms);
    					stmt.executeUpdate();
    					if(vtmp!=null && vtmp.size()>0) {
    						litmp = vtmp.listIterator();
    						stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set LIST_NPM_DAFTAR_ULANG_BUT_NO_KRS=? where ID_OBJ=? and THSMS=?");
    						while(litmp.hasNext()) {
    							String brs = (String)litmp.next();
    							//System.out.println("bar="+brs);
    							StringTokenizer st = new StringTokenizer(brs,"`");
    							String idobj = st.nextToken();
    							String kdpst = st.nextToken();
    							String list_npm = st.nextToken();
    							stmt.setString(1, list_npm);
    							stmt.setInt(2, Integer.parseInt(idobj));
    							stmt.setString(3, target_thsms);
    							int i = stmt.executeUpdate();
    							if(i>0) {
    								litmp.remove();
    							}
    						}
    						//insert
    						if(vtmp!=null && vtmp.size()>0) {
        						litmp = vtmp.listIterator();
        						stmt = con.prepareStatement("insert into  OVERVIEW_SEBARAN_TRLSM (ID_OBJ,THSMS,KDPST,LIST_NPM_DAFTAR_ULANG_BUT_NO_KRS)values(?,?,?,?)");
        						while(litmp.hasNext()) {
        							String brs = (String)litmp.next();
        							StringTokenizer st = new StringTokenizer(brs,"`");
        							String idobj = st.nextToken();
        							String kdpst = st.nextToken();
        							String list_npm = st.nextToken();
        							stmt.setInt(1, Integer.parseInt(idobj));
        							stmt.setString(2, target_thsms);
        							stmt.setString(3, kdpst);
        							stmt.setString(4, list_npm);
        							
        							
        							int i = stmt.executeUpdate();
        							
        						} 
    						}	
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

    
    public Vector getSummaryNoKrs(Vector v_returnScopeProdiOnlySortByKampusWithListIdobj, String target_thsms) {
    	/*
    	 * MERUPAKAN SUBSET FN()
    	 * public Vector getSummaryDaftarUlangAndNoKrs(Vector v_returnScopeProdiOnlySortByKampusWithListIdobj, String target_thsms) 
    	 * JADI PERHATIKAN BILA ADA PERUBAHAN DISNI APA ADA EFEK DIFUNGSI SATUNTA
    	 */
    	Vector vf = null;
    	Vector v_list_npm_approved = new Vector();
    	ListIterator liapproved = v_list_npm_approved.listIterator();
    	//format scope 122 22201 MHS_TEKNIK_SIPIL_KAMPUS_JAMSOSTEK 122 C JST
    	Vector v_kdpst_tot = null;
    	Vector v_kdpst_tot_unapproved = null;
    	Vector v_kdpst_npm_unapproved = null;
    	ListIterator litmp = null;
    	ListIterator lif = null;
    	ListIterator li = null;
    	//Vector v_daftar_ulang_rules = Checker.getRuleDaftarUlang(target_thsms);//li.add(kdpst+"`"+kdkmp+"`"+tknVerificatorNickname+"`"+urut);
    	/*
    	 * 
    	 */
    	try {
    		if(v_returnScopeProdiOnlySortByKampusWithListIdobj!=null && v_returnScopeProdiOnlySortByKampusWithListIdobj.size()>0) {
    			li = v_returnScopeProdiOnlySortByKampusWithListIdobj.listIterator();
    			vf = new Vector();
    			lif = vf.listIterator();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		//	seperate each kampus
    			int tot_req = 0;
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kmp = st.nextToken();
    				String sql_list_objid = null;
    				if(st.hasMoreTokens()) {
    					sql_list_objid = new String();
    					do {
    						String objid = st.nextToken();
    						sql_list_objid = sql_list_objid+"CIVITAS.ID_OBJ="+objid;
    						if(st.hasMoreElements()) {
    							sql_list_objid = sql_list_objid + " OR ";
    						}
    					}
    					while(st.hasMoreTokens());
    				}
    				if(sql_list_objid!=null) {
    					//sql_list_objid = "ID_OBJ=109";
    					//System.out.println(sql_list_objid);
    					stmt = con.prepareStatement("select * from DAFTAR_ULANG inner join CIVITAS on NPMHS=NPMHSMSMHS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where THSMS=? and ("+sql_list_objid+")");
    					stmt.setString(1, target_thsms);
    					rs = stmt.executeQuery();
    					String tkn_info_civ = "";
    					if(rs.next()) {
    						do {
    							tkn_info_civ = tkn_info_civ+"`"+rs.getString("NPMHS")+","+rs.getString("KDPSTMSMHS")+","+rs.getString("KODE_KAMPUS_DOMISILI")+","+rs.getString("TOKEN_APPROVAL")+","+rs.getLong("CIVITAS.ID_OBJ")+","+rs.getBoolean("ALL_APPROVED");
    						}
    						while(rs.next());
    						lif.add(kmp+tkn_info_civ);
    					}
    					else {
    						lif.add(kmp);
    					}
    				}
    				else {
    				//ignore = harus ada scope id_obj
    					lif.add(kmp);
    				}
    			}
    				
    			//filter unapproved
    			Vector v_unapproved = new Vector();
    			ListIterator liu = v_unapproved.listIterator();
    			lif = vf.listIterator();
    			//litmp = v_kdpst_tot.listIterator();
    			while(lif.hasNext()) {
    				String brs = (String)lif.next();
    				//System.out.println("original = "+brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kmp = st.nextToken();
    				String list_unapproved = new String(kmp);
    				while(st.hasMoreTokens()) {
    					String tkn_info_civ = st.nextToken();
    					
    					//System.out.println("tkn_info_civ = "+tkn_info_civ);
    					StringTokenizer st2 = new StringTokenizer(tkn_info_civ,",");
    					String npm = st2.nextToken();
    					String kdpst = st2.nextToken();
    					String kode_kmp = st2.nextToken();
    					String tkn_approved = st2.nextToken();
    					String id_obj = st2.nextToken();
    					String approved = st2.nextToken();
    					if(v_kdpst_tot==null) {
    						//first recoed
    						v_kdpst_tot = new Vector();
    						litmp = v_kdpst_tot.listIterator();
    						litmp.add(id_obj+"`"+kmp+"`"+kdpst+"`"+1);
    					}
    					else {
    						litmp = v_kdpst_tot.listIterator();
    						boolean match = false;
    						while(litmp.hasNext() && !match) {
    							String info=(String)litmp.next();
    							if(info.startsWith(id_obj+"`")) {
    								match = true;
    								StringTokenizer stt = new StringTokenizer(info,"`");
    								String tmp_idobj = stt.nextToken();
    								String tmp_kmp = stt.nextToken();
    								String tmp_kdpst = stt.nextToken();
    								String tmp_tot = stt.nextToken();
    								litmp.set(tmp_idobj+"`"+tmp_kmp+"`"+tmp_kdpst+"`"+(Integer.parseInt(tmp_tot)+1));
    							}
    						}
    						if(!match) {
    							//belum ada record
    							litmp.add(id_obj+"`"+kmp+"`"+kdpst+"`"+1);
    						}
    					}
    					/*
    					ListIterator li3 = v_daftar_ulang_rules.listIterator();
    					boolean kdpst_kmp_match = false;
    					String tkn_approval_required = "";
    					while(li3.hasNext() && !kdpst_kmp_match) {
    						String brs_rules = (String)li3.next();
    						//System.out.println("rules="+brs_rules);
    						StringTokenizer st3 = new StringTokenizer(brs_rules,"`");
    						String kdpst_rules=st3.nextToken();
    						String kdkmp_rules=st3.nextToken();
    						String tknVerificatorNickname_rules=st3.nextToken();
    						String urut_rules=st3.nextToken();
    						if((kdpst.equalsIgnoreCase(kdpst_rules))&&(kode_kmp.equalsIgnoreCase(kdkmp_rules))) {
    							kdpst_kmp_match = true;
    							tkn_approval_required = new String(tknVerificatorNickname_rules);
    						}
    					}
    					if(kdpst_kmp_match) {
    						//System.out.println("tkn_approval_required="+tkn_approval_required);
    						StringTokenizer st4 = new StringTokenizer(tkn_approval_required,",");
    						boolean all_approved = true;
    						while(st4.hasMoreTokens()&&all_approved) {
    							String approvee = st4.nextToken();
    							if(!tkn_approved.contains(approvee)) {
    								all_approved = false;
    							}
    						}
    					*/	
    					boolean all_approved = Boolean.parseBoolean(approved);
    					if(!all_approved) {
    							    							
    					}
    					else {
    						liapproved.add(id_obj+"`"+npm+"`"+kdpst+"`"+kode_kmp+"`"+tkn_approved);
    					}
    					//}
    					//else {
    						//rulenya ngga ada jadi dianggap belum approed
    						//System.out.println("tkn_approval_required=null");
    						
    					//}
    				}
    				//lif.set(list_unapproved);
    				//System.out.println("final="+list_unapproved);
    			}
    			//update table OVERVIEW_SEBARAN_TRLSM
    			
    			//reset value OVERVIEW_SEBARAN_TRLSM, biar diisi dengan yg baru
    			

    			
    			vf=null;
    			
    			//cek yg sudah daftar ulang tp ngga ada krs
    			if(v_list_npm_approved!=null && v_list_npm_approved.size()>0) {
    				stmt = con.prepareStatement("select NPMHSTRNLM from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=? and CLASS_POOL_UNIQUE_ID is not null");
    				liapproved = v_list_npm_approved.listIterator();
    				while(liapproved.hasNext()) {
    					String brs = (String)liapproved.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String idobj = st.nextToken();
    					String npm = st.nextToken();
    					String kdpst = st.nextToken();
    					String kode_kmp = st.nextToken();
    					String tkn_approved = st.nextToken();
    					stmt.setString(1, target_thsms);
    					stmt.setString(2, npm);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						liapproved.remove();
    					}
    					else {
    						//ngga punya krs
    						liapproved.set(kdpst+"`"+idobj+"`"+kode_kmp+"`"+npm);
    					}
    				}
    				//filter per kdpst
    				if(v_list_npm_approved!=null && v_list_npm_approved.size()>0) {
    					Vector vtmp = new Vector();
    					litmp = vtmp.listIterator();
    					v_list_npm_approved = Tool.removeDuplicateFromVector(v_list_npm_approved);
    					liapproved = v_list_npm_approved.listIterator();
    					boolean first = true;
    					String list_npm_no_krs = "";
    					String prev_objid = null;
						String prev_kdpst = null;
    					do {
    						String baris = (String)liapproved.next();
    						//System.out.println("baris="+baris);
    						StringTokenizer stt = new StringTokenizer(baris,"`");
    						String kdpst = stt.nextToken();
    						String objid = stt.nextToken();
    						String kmp = stt.nextToken();
    						String npm = stt.nextToken();
    						
    						if(first) {
    							first = false;
    							prev_objid=new String(objid);
    							prev_kdpst=new String(kdpst);
    							list_npm_no_krs = new String(npm);
    						}
    						else {
    							if(objid.equalsIgnoreCase(prev_objid)) {
    								list_npm_no_krs = list_npm_no_krs+","+npm;
    							}
    							else {
    								litmp.add(prev_objid+"`"+prev_kdpst+"`"+list_npm_no_krs);
    								//beda obj
    								prev_objid=new String(objid);
        							prev_kdpst=new String(kdpst);
        							list_npm_no_krs = new String(npm);
    							}
    						}
    						if(!liapproved.hasNext()) {
    							litmp.add(prev_objid+"`"+prev_kdpst+"`"+list_npm_no_krs);
    						}
    					}
    					while(liapproved.hasNext());
    					if(vtmp!=null) {
    						vf = new Vector(vtmp);
    					}
    					//update table overview
    					//reset column LIST_NPM_DAFTAR_ULANG_BUT_NO_KRS sebelum update;
    					stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set LIST_NPM_DAFTAR_ULANG_BUT_NO_KRS=? where THSMS=?");
    					stmt.setNull(1, java.sql.Types.VARCHAR);
    					stmt.setString(2, target_thsms);
    					stmt.executeUpdate();
    					if(vtmp!=null && vtmp.size()>0) {
    						litmp = vtmp.listIterator();
    						stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set LIST_NPM_DAFTAR_ULANG_BUT_NO_KRS=? where ID_OBJ=? and THSMS=?");
    						while(litmp.hasNext()) {
    							String brs = (String)litmp.next();
    							//System.out.println("bar="+brs);
    							StringTokenizer st = new StringTokenizer(brs,"`");
    							String idobj = st.nextToken();
    							String kdpst = st.nextToken();
    							String list_npm = st.nextToken();
    							//System.out.println(idobj+" list = "+list_npm);
    							stmt.setString(1, list_npm);
    							stmt.setInt(2, Integer.parseInt(idobj));
    							stmt.setString(3, target_thsms);
    							int i = stmt.executeUpdate();
    							if(i>0) {
    								litmp.remove();
    							}
    						}
    						//insert
    						if(vtmp!=null && vtmp.size()>0) {
        						litmp = vtmp.listIterator();
        						stmt = con.prepareStatement("insert into  OVERVIEW_SEBARAN_TRLSM (ID_OBJ,THSMS,KDPST,LIST_NPM_DAFTAR_ULANG_BUT_NO_KRS)values(?,?,?,?)");
        						while(litmp.hasNext()) {
        							String brs = (String)litmp.next();
        							StringTokenizer st = new StringTokenizer(brs,"`");
        							String idobj = st.nextToken();
        							String kdpst = st.nextToken();
        							String list_npm = st.nextToken();
        							stmt.setInt(1, Integer.parseInt(idobj));
        							stmt.setString(2, target_thsms);
        							stmt.setString(3, kdpst);
        							stmt.setString(4, list_npm);
        							
        							
        							int i = stmt.executeUpdate();
        							
        						} 
    						}	
    					}
    				}
    			}
    		}	
    		if(vf!=null) {
    			Vector vtmp = new Vector();
    			litmp = vtmp.listIterator();
    			//sort by kampus
    			
    			//lif = 115`93402`9340212100006,9340213100004,9340213100011,9340213100014,9340213200002
    			//li = JST`121`122`123`124`125`126`127`137`108`129`130`131`112`113`134`120`136`135
    			
    			li = v_returnScopeProdiOnlySortByKampusWithListIdobj.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st1 = new StringTokenizer(brs,"`");
    				
    				//tkn1 = kode_kmp
    				String list_info_per_kmp = new String(st1.nextToken());
    				while(st1.hasMoreTokens()) {
    					//boolean first = true;
    					String kmp_idobj_member = st1.nextToken();
    					//cek apa ada di vf
    					lif = vf.listIterator();
    					while(lif.hasNext()) {
    						String baris = (String)lif.next();
    						if(baris.startsWith(kmp_idobj_member)) {
    							
    							StringTokenizer st2 = new StringTokenizer(baris);
    							String idobj = st2.nextToken("`");
    							String kdpst = st2.nextToken("`");
    							if(st2.hasMoreTokens()) {
    								//berati list mhsnya
    								baris = st2.nextToken();
    								st2 = new StringTokenizer(baris,",");
    								while(st2.hasMoreTokens()) {
    									//if(first) {
    									//	first = false;
    										list_info_per_kmp = list_info_per_kmp+"`"+idobj+","+st2.nextToken()+","+kdpst;
    									//}
    									//else {
    									//	list_info_per_kmp = list_info_per_kmp+","+idobj+","+st2.nextToken()+","+kdpst;	
    									//}
    									
    								}
    							}
    						}
    					}
    				}
    				//System.out.println("list_info_per_kmp="+list_info_per_kmp);
    				litmp.add(list_info_per_kmp);
    				list_info_per_kmp = null;
    			}
    			vf = new Vector(vtmp);
    		}
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
    
    
    
    public Vector getJumMhsSthms(Vector v_returnScopeProdiOnlySortByKampusWithListIdobj, String target_thsms, String status_stmhs) {
    	Vector vf = null;
    	//format scope 122 22201 MHS_TEKNIK_SIPIL_KAMPUS_JAMSOSTEK 122 C JST
    	/*
    	
    	*/
    	ListIterator lif = null;
    	ListIterator li = null;
    	/*
    	 * 
    	 */
    	try {
    		if(v_returnScopeProdiOnlySortByKampusWithListIdobj!=null && v_returnScopeProdiOnlySortByKampusWithListIdobj.size()>0) {
    			li = v_returnScopeProdiOnlySortByKampusWithListIdobj.listIterator();
    			vf = new Vector();
    			lif = vf.listIterator();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		//	seperate each kampus
    			
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kmp = st.nextToken();
    				String sql_list_objid = null;
    				if(st.hasMoreTokens()) {
    					sql_list_objid = new String();
    					do {
    						String objid = st.nextToken();
    						sql_list_objid = sql_list_objid+"ID_OBJ="+objid;
    						if(st.hasMoreElements()) {
    							sql_list_objid = sql_list_objid + " OR ";
    						}
    					}
    					while(st.hasMoreTokens());
    				}
    				if(sql_list_objid!=null) {
    					//sql_list_objid = "ID_OBJ=109";
    					//System.out.println(sql_list_objid);
    					stmt = con.prepareStatement("select COUNT(*) from TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and STMHS=? and ("+sql_list_objid+")");
    					stmt.setString(1, target_thsms);
    					stmt.setString(2, status_stmhs);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						long counter = rs.getInt(1);
    						lif.add(kmp+" "+counter);
    					}
    					else {
    						lif.add(kmp+" 0");
    					}
    				}
    				else {
    				//ignore = harus ada scope id_obj
    					lif.add(kmp+" 0");
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
    	return vf;
    }
    
    
    public Vector getJumMhsAktif(Vector v_returnScopeProdiOnlySortByKampusWithListIdobj, String target_thsms) {
    	//mahasiswa aktif = mahasiswa yg sudah daatr ulang dan ada krsnya
    	Vector vf = null;
    	//format scope 122 22201 MHS_TEKNIK_SIPIL_KAMPUS_JAMSOSTEK 122 C JST
    	/*
    	
    	*/
    	ListIterator lif = null;
    	ListIterator li = null;
    	/*
    	 * 
    	 */
    	try {
    		if(v_returnScopeProdiOnlySortByKampusWithListIdobj!=null && v_returnScopeProdiOnlySortByKampusWithListIdobj.size()>0) {
    			li = v_returnScopeProdiOnlySortByKampusWithListIdobj.listIterator();
    			vf = new Vector();
    			lif = vf.listIterator();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		//	seperate each kampus
    			
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kmp = st.nextToken();
    				String sql_list_objid = null;
    				if(st.hasMoreTokens()) {
    					sql_list_objid = new String();
    					do {
    						String objid = st.nextToken();
    						sql_list_objid = sql_list_objid+"ID_OBJ="+objid;
    						if(st.hasMoreElements()) {
    							sql_list_objid = sql_list_objid + " OR ";
    						}
    					}
    					while(st.hasMoreTokens());
    				}
    				if(sql_list_objid!=null) {
    					//sql_list_objid = "ID_OBJ=109";
    					//System.out.println(sql_list_objid);
    					stmt = con.prepareStatement("select COUNT(DISTINCT NPMHSMSMHS) from DAFTAR_ULANG inner join CIVITAS on NPMHS=NPMHSMSMHS  inner join TRNLM on NPMHS=NPMHSTRNLM where THSMS=? and THSMSTRNLM=? and ("+sql_list_objid+")");
    					stmt.setString(1, target_thsms);
    					stmt.setString(2, target_thsms);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						long counter = rs.getInt(1);
    						lif.add(kmp+" "+counter);
    					}
    					else {
    						lif.add(kmp+" 0");
    					}
    				}
    				else {
    				//ignore = harus ada scope id_obj
    					lif.add(kmp+" 0");
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
    	return vf;
    }
    
    public Vector getNpmMhsAktif(Vector v_returnScopeProdiOnlySortByKampusWithListIdobj, String target_thsms) {
    	//mahasiswa aktif = mahasiswa yg sudah daatr ulang dan ada krsnya
    	Vector vf = null;
    	//format scope 122 22201 MHS_TEKNIK_SIPIL_KAMPUS_JAMSOSTEK 122 C JST
    	/*
    	
    	*/
    	ListIterator lif = null;
    	ListIterator li = null;
    	/*
    	 * 
    	 */
    	try {
    		if(v_returnScopeProdiOnlySortByKampusWithListIdobj!=null && v_returnScopeProdiOnlySortByKampusWithListIdobj.size()>0) {
    			li = v_returnScopeProdiOnlySortByKampusWithListIdobj.listIterator();
    			vf = new Vector();
    			lif = vf.listIterator();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		//	seperate each kampus
    			
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kmp = st.nextToken();
    				String sql_list_objid = null;
    				if(st.hasMoreTokens()) {
    					sql_list_objid = new String();
    					do {
    						String objid = st.nextToken();
    						sql_list_objid = sql_list_objid+"ID_OBJ="+objid;
    						if(st.hasMoreElements()) {
    							sql_list_objid = sql_list_objid + " OR ";
    						}
    					}
    					while(st.hasMoreTokens());
    				}
    				if(sql_list_objid!=null) {
    					//sql_list_objid = "ID_OBJ=109";
    					//System.out.println(sql_list_objid);
    					stmt = con.prepareStatement("select DISTINCT NPMHSMSMHS from DAFTAR_ULANG inner join CIVITAS on NPMHS=NPMHSMSMHS  inner join TRNLM on NPMHS=NPMHSTRNLM where THSMS=? and THSMSTRNLM=? and ("+sql_list_objid+")");
    					stmt.setString(1, target_thsms);
    					stmt.setString(2, target_thsms);
    					rs = stmt.executeQuery();
    					String tkn_npm = "";
    					while(rs.next()) {
    						tkn_npm = tkn_npm+"`"+rs.getString(1);
    						
    					}
    					lif.add(kmp+tkn_npm);
    				}
    				else {
    				//ignore = harus ada scope id_obj
    					lif.add(kmp);
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
    	return vf;
    }
    
    
    
    public Vector getSummaryMhsGivenStatus(Vector v_returnScopeProdiOnlySortByKampusWithListIdobj, String target_thsms, String stmhs) {
    	//target_thsms = "20152";
    	Vector v1 = null,vf = null;
    	ListIterator li = null,li1 = null;
    	if(v_returnScopeProdiOnlySortByKampusWithListIdobj!=null && v_returnScopeProdiOnlySortByKampusWithListIdobj.size()>0) {
    		v1 = new Vector();
    		li1 = v1.listIterator();
    		li = v_returnScopeProdiOnlySortByKampusWithListIdobj.listIterator();
    		try {	
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    			con = ds.getConnection();
    			/*
    			 * RESET PREVIOUS OVERVIEW_SEBARAN_TRLSM table value
    			 */
    			stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_"+stmhs+"_REQ=?,TOT_"+stmhs+"_REQ_UNAPPROVED=?,lIST_NPM_"+stmhs+"_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
    			while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String kmp = st.nextToken();
        			
        			while(st.hasMoreTokens()) {
        				String objid = st.nextToken();
        				stmt.setInt(1, 0);
        				stmt.setInt(2, 0);
        				stmt.setNull(3, java.sql.Types.VARCHAR);
        				stmt.setLong(4, Long.parseLong(objid));
        				stmt.setString(5,target_thsms);
        				stmt.executeUpdate();
        			}
    			}	
    			li = v_returnScopeProdiOnlySortByKampusWithListIdobj.listIterator();
    			
        		while(li.hasNext()) {
        			
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String kmp = st.nextToken();
        			String sql_list_objid = null;
        			if(st.hasMoreTokens()) {
        				sql_list_objid = new String();
        				do {
        					String objid = st.nextToken();
        					sql_list_objid = sql_list_objid+"CIVITAS.ID_OBJ="+objid;
        					if(st.hasMoreElements()) {
        						sql_list_objid = sql_list_objid + " OR ";
        					}
        				}
        				while(st.hasMoreTokens());
        			}
        			if(sql_list_objid!=null) {
        				Vector v_pengajuan_identik = new Vector();
        				ListIterator lip = v_pengajuan_identik.listIterator();
    					//sql_list_objid = "ID_OBJ=109";
    					//System.out.println(sql_list_objid);
        				
    					String cmd = "select TOPIK_PENGAJUAN.ID,TARGET_THSMS_PENGAJUAN,TIPE_PENGAJUAN,APPROVED,REJECTED,LOCKED,CREATOR_NPM,CREATOR_KDPST,KODE_KAMPUS_DOMISILI,CIVITAS.ID_OBJ from TOPIK_PENGAJUAN inner join CIVITAS on CREATOR_NPM=NPMHSMSMHS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and BATAL=? and ("+sql_list_objid+") order by TARGET_THSMS_PENGAJUAN,TIPE_PENGAJUAN,CIVITAS.ID_OBJ,CREATOR_KDPST,CREATOR_NPM,UPDTM";
    					stmt = con.prepareStatement(cmd);
    					stmt.setString(1, target_thsms);
    					stmt.setString(2, stmhs);
    					//System.out.println("cmd="+cmd);
    					stmt.setBoolean(3, false);
    					rs = stmt.executeQuery();
    					String tkn_info_civ = "";
    					
    					String prev_baris = null;
    					/*
    					 * agar ngga dobel 
    					 */
    					int counter=0;
    					boolean first = true;
    					if(rs.next()) {
    						do {
    							boolean dobel = false;
    							String id = ""+rs.getLong("TOPIK_PENGAJUAN.ID");
    							String list_id_approved = ""+rs.getString("APPROVED");
    							String list_id_rejected = ""+rs.getString("REJECTED");
    							boolean locked = rs.getBoolean("LOCKED");
    							String npmhs_mhs = ""+rs.getString("CREATOR_NPM");
    							String kdpst_mhs = ""+rs.getString("CREATOR_KDPST");
    							String kmp_dom = ""+rs.getString("KODE_KAMPUS_DOMISILI");
    							String idobj_mhs = ""+rs.getLong("CIVITAS.ID_OBJ");
    							String current_baris = null;
    							if(first) {
    								first = false;
    								prev_baris = new String(target_thsms+"`"+stmhs+"`"+list_id_approved+"`"+list_id_rejected+"`"+locked+"`"+npmhs_mhs);
    							}
    							else {
    								current_baris = new String(target_thsms+"`"+stmhs+"`"+list_id_approved+"`"+list_id_rejected+"`"+locked+"`"+npmhs_mhs);
    								//System.out.println(prev_baris);
    								//System.out.println(current_baris);
    								if(prev_baris.equalsIgnoreCase(current_baris)) {
    									dobel = true;
    								}
    								//System.out.println(dobel);
    								prev_baris = new String(current_baris);
    							}
    							if(!dobel) {
    								counter++;
    								if(locked) {
        								if(Checker.isStringNullOrEmpty(list_id_rejected)) {
        									//berarti diterima tidak ada yg rejected
        									tkn_info_civ = tkn_info_civ+"`"+kdpst_mhs+",TERIMA,"+npmhs_mhs+","+kmp_dom+","+idobj_mhs;
        								}
        								else {
        									//ditolak
        									tkn_info_civ = tkn_info_civ+"`"+kdpst_mhs+",TOLAK,"+npmhs_mhs+","+kmp_dom+","+idobj_mhs;
        								}
        							}
        							else {
        								tkn_info_civ = tkn_info_civ+"`"+kdpst_mhs+",INPROGRESS,"+npmhs_mhs+","+kmp_dom+","+idobj_mhs;
        							}
    							}
    							//if(dobel) {
    							else {
    								lip.add(id);
    							}
    						}
    						while(rs.next());
    						li1.add(kmp+tkn_info_civ);
    						
    						//delete dobel
    						if(v_pengajuan_identik!=null && v_pengajuan_identik.size()>0) {
    							stmt = con.prepareStatement("delete from TOPIK_PENGAJUAN where ID=?");
    							lip = v_pengajuan_identik.listIterator();
        						//System.out.println("counter="+counter);
        						while(lip.hasNext()) {
        							String id = (String)lip.next();
        							stmt.setLong(1, Long.parseLong(id));
        							stmt.executeUpdate();
        						}
    						}
    						
    					}
    					else {
    						li1.add(kmp);
    					}
    					//System.out.println("v_pengajuan_identik="+v_pengajuan_identik.size());
    				}
    				else {
    				//ignore = harus ada scope id_obj
    					li1.add(kmp);
    				}
        			
        		}
        		
        		if(v1!=null) {
        			vf = new Vector();
        			//Vector v_npm_inprogress = new Vector();
        			//ListIterator lit = v_tot_inprogress.listIterator();
        			ListIterator lif = vf.listIterator();
        			Collections.sort(v1);
        			li1 = v1.listIterator();
            		while(li1.hasNext()) {
            			String brs = (String)li1.next();
            			//System.out.println("brs stmhs="+brs);
            			StringTokenizer st = new StringTokenizer(brs,"`");
            			String kmp = st.nextToken();
            			
            			if(!st.hasMoreTokens()) {
            				//0 pengajuan utk kampus terkait
            				//update = reset OVERVIEW TABLE to 0 pengajuan untuk scopeid kampus terkait
            				ListIterator litmp = v_returnScopeProdiOnlySortByKampusWithListIdobj.listIterator();
            				boolean match = false;
            				while(litmp.hasNext() && !match) {
            					String info_tmp = (String)litmp.next();
            					//System.out.println("info_tmp="+info_tmp);
            					StringTokenizer stt = new StringTokenizer(info_tmp,"`");
            					String kode_kmp = stt.nextToken();
            					if(kmp.equalsIgnoreCase(kode_kmp)) {
            						match = true;
            						if(stt.hasMoreTokens()) {
            							String cmd_tmp = null;
            							while(stt.hasMoreTokens()) {
            								String id_tmp = stt.nextToken();
            								if(cmd_tmp==null) {
            									cmd_tmp = new String("ID_OBJ="+id_tmp);
            								}
            								else {
            									cmd_tmp = cmd_tmp+" OR ID_OBJ="+id_tmp;
            								}
            							}
            							cmd_tmp = "update OVERVIEW_SEBARAN_TRLSM set TOT_"+stmhs.toUpperCase()+"_REQ=0,TOT_"+stmhs.toUpperCase()+"_REQ_UNAPPROVED=0,LIST_NPM_"+stmhs.toUpperCase()+"_UNAPPROVED=? where THSMS=? and ("+cmd_tmp+")";
            							//System.out.println("cmd_tmp = "+cmd_tmp);
            							stmt = con.prepareStatement(cmd_tmp);
            							stmt.setNull(1, java.sql.Types.VARCHAR);
            							stmt.setString(2,target_thsms);
            							stmt.executeUpdate();
            						}
            						else {
            							//ignore do nothing ngga ada scope obj id untuk kampus ini
            						}
            					}
            				}
            			}	
            			else {
            				//PST`20201,TERIMA,2020100000046,PST,101`20201,TERIMA,2020100000046,PST,101`20201,INPROGRESS2020100000046,PST,101`20201,TERIMA,2020112100004,PST,101`55201,INPROGRESS5520100000140,PST,107
            				int tot_pengajuan = 0;
            				int tot_inprogress = 0;
            				String list_npm = null;
            				String prev_kdpst = null;
            				String prev_idobj = null;
            				
            				while(st.hasMoreTokens()) {
            					//first record
            					
            					String info = st.nextToken();
            					//System.out.println("info="+info);
            					StringTokenizer st1 = new StringTokenizer(info,",");
            					if(!info.contains("TOLAK")) {
            						//System.out.println("!tolak");
            						//tot pengajuan adalah selain yg ditolak
            						
            						String kdpst_mhs = st1.nextToken();
            						String verdict = st1.nextToken();
            						String npmhs_mhs = st1.nextToken();
            						String kmp_mhs = st1.nextToken();
            						String idobj_mhs = st1.nextToken();
            						
            						
            						if(prev_idobj==null) {
            							//first token
            							tot_pengajuan++;
            							prev_kdpst = new String(kdpst_mhs);
                						prev_idobj = new String(idobj_mhs);
                						if(info.contains("INPROGRESS")) {
                							tot_inprogress++;
                							list_npm = new String(npmhs_mhs);
                						}
            						}
            						else {
            							if(prev_idobj.equalsIgnoreCase(idobj_mhs)) {
            								//belum ada perubahan
            								tot_pengajuan++;
            								if(info.contains("INPROGRESS")) {
                    							
                    							if(list_npm==null) {
                    								tot_inprogress = 1; 
                									list_npm = new String(npmhs_mhs);
                								}
                								else {
                									tot_inprogress++;
                									list_npm = list_npm+","+npmhs_mhs;	
                								}
                    						}
            								
            									
            							}
            							else {
            								if(list_npm==null || Checker.isStringNullOrEmpty(list_npm)) {
                								lif.add(kmp+"`"+prev_kdpst+"`"+prev_idobj+"`"+tot_pengajuan+"`0`null");//ngga ada list_npm inprogress
                							}
                							else {
                								lif.add(kmp+"`"+prev_kdpst+"`"+prev_idobj+"`"+tot_pengajuan+"`"+tot_inprogress+"`"+list_npm);	
                							}
            								//ganti objek
            								list_npm=null;
            								tot_pengajuan=1;
                							tot_inprogress=0;
            								prev_kdpst = new String(kdpst_mhs);
                    						prev_idobj = new String(idobj_mhs);
                    						if(info.contains("INPROGRESS")) {
                    							tot_inprogress++;
                    							list_npm = new String(npmhs_mhs);
                    						}
            							}
            						}
            					}	
            					if(!st.hasMoreTokens()) {
            						if(list_npm==null || Checker.isStringNullOrEmpty(list_npm)) {
            							lif.add(kmp+"`"+prev_kdpst+"`"+prev_idobj+"`"+tot_pengajuan+"`0`null");//ngga ada list_npm inprogress
        							}
        							else {
        								lif.add(kmp+"`"+prev_kdpst+"`"+prev_idobj+"`"+tot_pengajuan+"`"+tot_inprogress+"`"+list_npm);	
        							}
            					}
            				}
            			}
            		}
        		}
        		
        		if(vf!=null && vf.size()>0) {
        			ListIterator lif = vf.listIterator();
        			//coba update dulu kali aja udah ada
        			//System.out.println("value stmhs = "+stmhs);
        			stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_"+stmhs+"_REQ=?,TOT_"+stmhs+"_REQ_UNAPPROVED=?,lIST_NPM_"+stmhs+"_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
        			while(lif.hasNext()) {
        				String brs = (String)lif.next();
        				//System.out.println("baris = "+brs);
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				String kmp = st.nextToken();
        				String kdpst = st.nextToken();
        				String idobj = st.nextToken();
        				String tot_pengajuan = st.nextToken();
        				String tot_inprog = st.nextToken();
        				String list_npm_inprog = st.nextToken();
        				stmt.setInt(1, Integer.parseInt(tot_pengajuan));
        				stmt.setInt(2, Integer.parseInt(tot_inprog));
        				if(Integer.parseInt(tot_inprog)>0 && !Checker.isStringNullOrEmpty(list_npm_inprog)) {
        					stmt.setString(3, list_npm_inprog);
        				}
        				else {
        					stmt.setNull(3, java.sql.Types.VARCHAR);
        				}
        				stmt.setInt(4, Integer.parseInt(idobj));
        				stmt.setString(5, target_thsms);
        				int i = 0;
        				i = stmt.executeUpdate();
        				if(i>0) {
        					lif.remove();
        				}
            				
        			}
        			//insert sisanya 
        			if(vf!=null && vf.size()>0) {
        				stmt = con.prepareStatement("insert into OVERVIEW_SEBARAN_TRLSM(ID_OBJ,THSMS,KDPST,TOT_"+stmhs+"_REQ,TOT_"+stmhs+"_REQ_UNAPPROVED,LIST_NPM_"+stmhs+"_UNAPPROVED)values(?,?,?,?,?,?)");
            			lif = vf.listIterator();
            			while(lif.hasNext()) {
            				String brs = (String)lif.next();
            				StringTokenizer st = new StringTokenizer(brs,"`");
            				String kmp = st.nextToken();
            				String kdpst = st.nextToken();
            				String idobj = st.nextToken();
            				String tot_pengajuan = st.nextToken();
            				String tot_inprog = st.nextToken();
            				String list_npm_inprog = st.nextToken();
            				stmt.setInt(1, Integer.parseInt(idobj));
            				stmt.setString(2, target_thsms);
            				stmt.setString(3, kdpst);
            				stmt.setInt(4, Integer.parseInt(tot_pengajuan));
            				stmt.setInt(5, Integer.parseInt(tot_inprog));
            				if(Checker.isStringNullOrEmpty(list_npm_inprog)) {
            					stmt.setNull(6, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(6, list_npm_inprog);
            				}
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
    	}
    	
    	return vf;
    }
    
    public void updateOverviewSebaranTrlsmTable(String target_thsms, String kdpti) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    	//get list kdpst prodi
    	//Vector v_prodi = Getter.getListProdi();//li.add(kdpst+"`"+kdfak+"`"+kdjen+"`"+nmpst);
    	//Vector v_kmp = Getter.getListAllKampus(); //li.add(code_campus+"`"+name_campus+"`"+nickname_campus);
    		//System.out.println("1");
    		
    		Vector v_scope = Getter.returnListProdiOnlySortByKampusWithListIdobj();
    		//INITIALIZE OVERVIEW_SEBARAN_TRLSM
    		initializeTableOverview(v_scope, target_thsms);
    		System.out.println("1");
    		Vector v_info_daftar_ulang = getSummaryDaftarUlangAndNoKrs(v_scope, target_thsms);
    		System.out.println("2");
    		Vector v_info_cuti = getSummaryMhsGivenStatus(v_scope, target_thsms, "CUTI");
    		System.out.println("3");
    		Vector v_info_keluar = getSummaryMhsGivenStatus(v_scope, target_thsms, "KELUAR");
    		System.out.println("4");
    		Vector v_info_do = getSummaryMhsGivenStatus(v_scope, target_thsms, "DO");
    		System.out.println("5");
    		Vector v_info_lu = getSummaryMhsGivenStatus(v_scope, target_thsms, "KELULUSAN");
    		System.out.println("6");
    		getSummaryMhsAktifPerKampusPerKdpst(v_scope,target_thsms); //DULU BUAT APA INI YA??
    		System.out.println("7");
    		updateStatusPengajuanPerkuliahan(target_thsms);
    		System.out.println("8");
    		syncDataPmb(target_thsms,kdpti);
    		System.out.println("9");
        	//System.out.println("3");
        	
 			
     	}
     	catch (Exception e) {
     		e.printStackTrace();
     	}
     	finally {
     		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
     		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
     		if (con!=null) try { con.close();} catch (Exception ignore){}
     	}   		
    }
    
    
    public void syncDataPmb(String target_thsms, String kdpti) {
    	//System.out.println("start");
    	
    	try {
			
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
			con = ds.getConnection();
			//reset prev value
			int k = 0;
			stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_PENDAFTARAN_REQ=?,TOT_NO_SHOW=?,LIST_NPM_NO_SHOW=? where THSMS=?");
			stmt.setInt(1, 0);
			stmt.setInt(2, 0);
			stmt.setNull(3, java.sql.Types.VARCHAR);
			stmt.setString(4, target_thsms);
			k = stmt.executeUpdate();
			//System.out.println("k1="+k);
			
			//get total mahasiwa baru dan bukan mhs lama aktif kembali
			int tot_pendafatar = 0;
			int no_show = 0;
			String list_npm = null;
			String list_idobj = null;
			stmt = con.prepareStatement("select CIVITAS.ID_OBJ,KDPSTMSMHS,NPMHSMSMHS,OBJ_NICKNAME from CIVITAS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.id_obj where SMAWLMSMHS=? and (ASPTIMSMHS IS NULL or ASPTIMSMHS<>?)");
			stmt.setString(1, target_thsms);
			stmt.setString(2, kdpti);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				tot_pendafatar++;
				String idobj = ""+rs.getInt(1);
				String kdpst = rs.getString(2);
				String npmhs = rs.getString(3);
				String nick = rs.getString(4);
				if(nick.contains("MHS")) {
					if(list_npm==null) {
						list_npm = new String(npmhs);
						list_idobj = new String(idobj);
					}
					else {
						list_npm = list_npm+","+npmhs;
						list_idobj = list_idobj+","+idobj;
					}
				}
				
			}
			if(tot_pendafatar>0) {
				StringTokenizer st = new StringTokenizer(list_npm,",");
				StringTokenizer st1 = new StringTokenizer(list_idobj,",");
				//update total pendaftar
				stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_PENDAFTARAN_REQ=TOT_PENDAFTARAN_REQ+1 where ID_OBJ=? and THSMS=?");

				while(st.hasMoreTokens()) {
					String npm = st.nextToken();
					String idobj = st1.nextToken();
					stmt.setInt(1, Integer.parseInt(idobj));
					stmt.setString(2, target_thsms);
					k = stmt.executeUpdate();
					//System.out.println("k1="+k);
				}
				
				//cek apa sudah daftar ulang
				st = new StringTokenizer(list_npm,",");
				st1 = new StringTokenizer(list_idobj,",");
				list_npm = null;
				list_idobj = null;
				//cek apa sudah daftar ulang
				stmt = con.prepareStatement("select NPMHS from DAFTAR_ULANG where THSMS=? and NPMHS=?");
				while(st.hasMoreTokens()) {
					String npm = st.nextToken();
					String idobj = st1.nextToken();
					stmt.setString(1, target_thsms);
					stmt.setString(2, npm);
					rs = stmt.executeQuery();
					if(rs.next()) {
						//sudah daftar ulang = sudah show
					}
					else {
						//no show
						no_show++;
						if(list_npm==null) {
							list_npm = new String(npm);
							list_idobj = new String(idobj);
						}
						else {
							list_npm = list_npm+","+npm;
							list_idobj = list_idobj+","+idobj;
						}
					}
				}
				if(no_show>0) {
					stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_NO_SHOW=TOT_NO_SHOW+1,LIST_NPM_NO_SHOW=CONCAT_WS(' ',LIST_NPM_NO_SHOW,?) where ID_OBJ=? and THSMS=?");
					st = new StringTokenizer(list_npm,",");
					st1 = new StringTokenizer(list_idobj,",");
					while(st.hasMoreTokens()) {
						String npm = st.nextToken();
						String idobj = st1.nextToken();
						stmt.setString(1, npm+",");
						stmt.setInt(2, Integer.parseInt(idobj));
						stmt.setString(3, target_thsms);
						k = stmt.executeUpdate();
						//System.out.println("k1="+k);
					}
				}
			}	
			
    	}
     	catch (Exception e) {
     		e.printStackTrace();
     	}
     	finally {
     		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
     		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
     		if (con!=null) try { con.close();} catch (Exception ignore){}
     	}
    }	
    
    public void updateStatusPengajuanPerkuliahan(String target_thsms) {
    	//System.out.println("start");
    	try {
			
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
			con = ds.getConnection();
			//krn table overview sudah diinitialize jadi tinggal process update saja
			//1.UPDATE table set belum mengajukan = true && inprogress = false
			stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set BELUM_MENGAJUKAN_KELAS_PERKULIAHAN=?,PENGAJUKAN_KELAS_PERKULIAHAN_INPROGRESS=? where THSMS=?");
			stmt.setBoolean(1, true);
			stmt.setBoolean(2, false);
			stmt.setString(3, target_thsms);
			stmt.executeUpdate();
			
			//2. cek CLASS_POOL prodi yg sudah ada pengajuan
			Vector v = new Vector();
			ListIterator li = v.listIterator();
			stmt = con.prepareStatement("select KDPST,KODE_KAMPUS from CLASS_POOL where THSMS=?");
			stmt.setString(1, target_thsms);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String kdpst = rs.getString(1);
				String kmp = rs.getString(2);
				li.add(kdpst+"`"+kmp);
			}
			if(v.size()>0) {
				v = Tool.removeDuplicateFromVector(v);
				li = v.listIterator();
				//update overview set sudah mengajukan = true
				//1.get objid dulu
				stmt = con.prepareStatement("select ID_OBJ from OBJECT where KDPST=? and KODE_KAMPUS_DOMISILI=?");
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String kdpst = st.nextToken();
					String kmp = st.nextToken();
					stmt.setString(1,kdpst);
					stmt.setString(2,kmp);
					rs = stmt.executeQuery();
					rs.next();
					int id = rs.getInt(1);
					li.set(brs+"`"+id);
				}
				
				li = v.listIterator();
				//update overview set BELUM_MENGAJUKAN_KELAS_PERKULIAHAN = false - berarti sudah menhajukan
				stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set BELUM_MENGAJUKAN_KELAS_PERKULIAHAN=?,PENGAJUKAN_KELAS_PERKULIAHAN_INPROGRESS=? where ID_OBJ=? and THSMS=?");
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String kdpst = st.nextToken();
					String kmp = st.nextToken();
					String id = st.nextToken();
					stmt.setBoolean(1, false);// false = ada pengajuan
					stmt.setBoolean(2, true);//wip = true
					stmt.setInt(3,Integer.parseInt(id));
					stmt.setString(4, target_thsms);
					stmt.executeUpdate();
				}	
			}
			
			//3. cek yg proses pengajuan sudah complete
			// completer = locked && !rejected ato passed  && !canceled
			v =new Vector();
			li = v.listIterator();
			stmt = con.prepareStatement("select KDPST,KODE_KAMPUS from CLASS_POOL where THSMS=? and LOCKED=? and CANCELED=? and REJECTED=?");
			stmt.setString(1, target_thsms);
			stmt.setBoolean(2, true);
			stmt.setBoolean(3, false);
			stmt.setBoolean(4, false);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String kdpst = rs.getString(1);
				String kmp = rs.getString(2);
				li.add(kdpst+"`"+kmp);
			}
			v = Tool.removeDuplicateFromVector(v);
			if(v.size()>0) {
				v = Tool.removeDuplicateFromVector(v);
				li = v.listIterator();
				//update overview set sudah mengajukan = true
				//1.get objid dulu
				stmt = con.prepareStatement("select ID_OBJ from OBJECT where KDPST=? and KODE_KAMPUS_DOMISILI=?");
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String kdpst = st.nextToken();
					String kmp = st.nextToken();
					stmt.setString(1,kdpst);
					stmt.setString(2,kmp);
					rs = stmt.executeQuery();
					rs.next();
					int id = rs.getInt(1);
					li.set(brs+"`"+id);
				}
				
				li = v.listIterator();
				//update overview set sudah mengajukan = true
				stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set PENGAJUKAN_KELAS_PERKULIAHAN_INPROGRESS=? where ID_OBJ=? and THSMS=?");
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String kdpst = st.nextToken();
					String kmp = st.nextToken();
					String id = st.nextToken();
					stmt.setBoolean(1, false);
					stmt.setInt(2,Integer.parseInt(id));
					stmt.setString(3, target_thsms);
					stmt.executeUpdate();
				}
			}	

		}
     	catch (Exception e) {
     		e.printStackTrace();
     	}
     	finally {
     		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
     		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
     		if (con!=null) try { con.close();} catch (Exception ignore){}
     	}  
    	//System.out.println("end");
    } 
    
    
    public void initializeTableOverview(Vector v_scope_id, String target_thsms) {
    	
    	
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	ArrayList v1 = new ArrayList();
    	ListIterator li1 = v1.listIterator();
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		Vector v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
    		ListIterator li2 = v_scope_kdpst.listIterator();
    		ListIterator lis = v_scope_id.listIterator();
    		try {
    				
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			//1. cek apa sdh ada rec di table
    			stmt = con.prepareStatement("select ID_OBJ from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
    			while(lis.hasNext()) {
    				String brs2 = (String)li2.next();
    				String brs = (String)lis.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				StringTokenizer st2 = new StringTokenizer(brs2,"`");
    				String kmp = st.nextToken();
    				String kmp2 = st2.nextToken();
    				String sql_addon = null;
    				while(st.hasMoreTokens()) {
    					String tkn_id = st.nextToken();
    					String tkn_kdpst = st2.nextToken();
    					stmt.setInt(1, Integer.parseInt(tkn_id));
    					stmt.setString(2, target_thsms);
    					rs = stmt.executeQuery();
    					if(!rs.next()) {
    						li1.add(tkn_id+"`"+tkn_kdpst);
    						//System.out.println("add "+tkn_id);
    					}
    				}
    			}
    			if(v1.size()>0) {
    				li1 = v1.listIterator();
    				stmt = con.prepareStatement("insert into OVERVIEW_SEBARAN_TRLSM(ID_OBJ,THSMS,KDPST)values(?,?,?)");
    				while(li1.hasNext()) {
    					String brs = (String)li1.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String id = st.nextToken();
    					String kdpst = st.nextToken();
    					stmt.setInt(1, Integer.parseInt(id));
    					stmt.setString(2, target_thsms);
    					stmt.setString(3, kdpst);
    					stmt.executeUpdate();
    				}
    			}
         	}
         	catch (Exception e) {
         		e.printStackTrace();
         	}
         	finally {
         		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
         		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
         		if (con!=null) try { con.close();} catch (Exception ignore){}
         	}
    		
    	}
    
    }    

    public void initializeTableOverview(String target_thsms) {
    	
    	
    	//Vector v = new Vector();
    	ListIterator li = null;
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	ListIterator li1 = null;

    	Vector v_list_id = Getter.returnListProdiOnlySortByKampusWithListIdobj();
    	Vector v_list_kdpst = Converter.convertVscopeidToKdpst(v_list_id);
    	try {
			
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			li = v_list_id.listIterator();
			li1 = v_list_kdpst.listIterator();
			//1. cek apa sdh ada rec di table
			stmt = con.prepareStatement("select ID_OBJ from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
			while(li.hasNext()) {
				String brs = (String)li.next();
				String brs1 = (String)li1.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				StringTokenizer st1 = new StringTokenizer(brs1,"`");
				String kmp = st.nextToken();
				st1.nextToken();
				while(st.hasMoreTokens()) {
					String tkn_id = st.nextToken();
					String tkn_kdpst = st1.nextToken();
					stmt.setInt(1, Integer.parseInt(tkn_id));
					stmt.setString(2, target_thsms);
					rs = stmt.executeQuery();
					if(!rs.next()) {
						lif.add(tkn_id+"`"+tkn_kdpst);
						//System.out.println("add "+tkn_id);
					}
				}
			}
			if(vf.size()>0) {
				lif = vf.listIterator();
				stmt = con.prepareStatement("insert into OVERVIEW_SEBARAN_TRLSM(ID_OBJ,THSMS,KDPST)values(?,?,?)");
				while(li1.hasNext()) {
					String brs = (String)lif.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String id = st.nextToken();
					String kdpst = st.nextToken();
					stmt.setInt(1, Integer.parseInt(id));
					stmt.setString(2, target_thsms);
					stmt.setString(3, kdpst);
					stmt.executeUpdate();
				}
			}
     	}
     	catch (Exception e) {
     		e.printStackTrace();
     	}
     	finally {
     		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
     		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
     		if (con!=null) try { con.close();} catch (Exception ignore){}
     	}
    
    }    

    
    public Vector getInfoOverviewSebaranTrlsmTable(Vector v_scope_id,String target_thsms) {
    	/*
    	 * set SHOW MALAIKAT TIDAK DISINI, KRN DISINI HANYA MEMBACA OVERVIEW TABEL
    	 */
    			
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		ListIterator lis = v_scope_id.listIterator();
    		try {
    				
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			while(lis.hasNext()) {
    				String brs = (String)lis.next();
    				Vector vtmp = new Vector();
    				ListIterator lit = vtmp.listIterator();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kmp = st.nextToken();
    				String sql_addon = null;
    				if(st.hasMoreTokens()) {
    					sql_addon =new String(); 
    					do {
    						String tkn_id = st.nextToken();
    						sql_addon = sql_addon+"ID_OBJ="+tkn_id;
    						if(st.hasMoreTokens()) {
    							sql_addon = sql_addon+" OR ";
    						}
    					}
    					while(st.hasMoreTokens());
    				}
    				if(sql_addon!=null) {
    					stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where THSMS=? and ("+sql_addon+")");
    					stmt.setString(1, target_thsms);
    					rs = stmt.executeQuery();
    					boolean ada_rec = false;
    					while(rs.next()) {
    						ada_rec = true;
    						String kdpst = ""+rs.getString("KDPST");
    						String tot_du = ""+rs.getInt("TOT_DAFTAR_ULANG_REQ");
    						String tot_du_wip = ""+rs.getInt("TOT_DAFTAR_ULANG_REQ_UNAPPROVED");
    						String list_npm_du_wip = ""+rs.getString("LIST_NPM_DAFTAR_ULANG_UNAPPROVED");
    						String tot_cu = ""+rs.getInt("TOT_CUTI_REQ");
    						String tot_cu_wip = ""+rs.getInt("TOT_CUTI_REQ_UNAPPROVED");
    						String list_npm_cu_wip = ""+rs.getString("LIST_NPM_CUTI_UNAPPROVED");
    						String tot_ou = ""+rs.getInt("TOT_KELUAR_REQ");
    						String tot_ou_wip = ""+rs.getInt("TOT_KELUAR_REQ_UNAPPROVED");
    						String list_npm_ou_wip = ""+rs.getString("LIST_NPM_KELUAR_UNAPPROVED");
    						String tot_do = ""+rs.getInt("TOT_DO_REQ");
    						String tot_do_wip = ""+rs.getInt("TOT_DO_REQ_UNAPPROVED");
    						String list_npm_do_wip = ""+rs.getString("LIST_NPM_DO_UNAPPROVED");
    						String tot_mhs_aktif = ""+rs.getInt("TOT_MHS_AKTIF");
    						String list_npm_du_no_krs = ""+rs.getString("LIST_NPM_DAFTAR_ULANG_BUT_NO_KRS");
    						String tot_pp_out = ""+rs.getInt("TOT_NPM_PINDAH_PRODI_OUT");
    						String tot_pp_in = ""+rs.getInt("TOT_NPM_PINDAH_PRODI_IN");
    						String list_npm_pp_wip = ""+rs.getString("LIST_NPM_PINDAH_PRODI_UNAPPROVED");
    						String no_pengajuan_kls_req = ""+rs.getBoolean("BELUM_MENGAJUKAN_KELAS_PERKULIAHAN");
    						String pengajuan_kls_req_wip = ""+rs.getBoolean("PENGAJUKAN_KELAS_PERKULIAHAN_INPROGRESS");
    						String no_kegiatan_perkuliahan = ""+rs.getBoolean("TIDAK_ADA_PERKULIAHAN");
    						String tot_lu = ""+rs.getInt("TOT_KELULUSAN_REQ");
    						String tot_lu_wip = ""+rs.getInt("TOT_KELULUSAN_REQ_UNAPPROVED");
    						String list_npm_lu_wip = ""+rs.getString("LIST_NPM_KELULUSAN_UNAPPROVED");
    						String tot_pmb = ""+rs.getInt("TOT_PENDAFTARAN_REQ");
    						String tot_noshow = ""+rs.getInt("TOT_NO_SHOW");
    						String list_npm_noshow = ""+rs.getString("LIST_NPM_NO_SHOW");
    						String belum_ada_ada_penyetaraan = ""+rs.getBoolean("KRS_PINDAHAN");
    						String ada_nilai_tunda = ""+rs.getBoolean("NILAI_TUNDA");
    						lit.add(kdpst+"`"+tot_du+"`"+tot_du_wip+"`"+list_npm_du_wip+"`"+tot_cu+"`"+tot_cu_wip+"`"+list_npm_cu_wip+"`"+tot_ou+"`"+tot_ou_wip+"`"+list_npm_ou_wip+"`"+tot_do+"`"+tot_do_wip+"`"+list_npm_do_wip+"`"+tot_mhs_aktif+"`"+list_npm_du_no_krs+"`"+tot_pp_out+"`"+tot_pp_in+"`"+list_npm_pp_wip+"`"+tot_pmb+"`"+tot_noshow+"`"+list_npm_noshow+"`"+belum_ada_ada_penyetaraan+"`"+ada_nilai_tunda+"`"+no_pengajuan_kls_req+"`"+pengajuan_kls_req_wip+"`"+no_kegiatan_perkuliahan+"`"+tot_lu+"`"+tot_lu_wip+"`"+list_npm_lu_wip);
    					}
    					if(ada_rec) {
    						li.add(kmp);
        					li.add(vtmp);	
        					
    					}
    					
    				}
    				
    			}
         	}
         	catch (Exception e) {
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
    
    public Vector getSummaryMhsAktifPerKampus(Vector v_scope_id,String target_thsms) {
    	/*
    	 * AKTIF = ada krs & daftar ulang sudah di approved
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		ListIterator lis = v_scope_id.listIterator();
    		try {
    				
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			while(lis.hasNext()) {
    				String brs = (String)lis.next();
    				Vector vtmp = new Vector();
    				ListIterator lit = vtmp.listIterator();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kmp = st.nextToken();
    				String sql_addon = null;
    				if(st.hasMoreTokens()) {
    					sql_addon =new String(); 
    					do {
    						String tkn_id = st.nextToken();
    						sql_addon = sql_addon+"ID_OBJ="+tkn_id;
    						if(st.hasMoreTokens()) {
    							sql_addon = sql_addon+" OR ";
    						}
    					}
    					while(st.hasMoreTokens());
    				}
    				if(sql_addon!=null) {
    					stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM inner join CIVITAS on NPMHSTRNLM=NPMHSMSMHS where THSMSTRNLM=? and  ("+sql_addon+")");
    					stmt.setString(1, target_thsms);
    					rs = stmt.executeQuery();
    					boolean ada_rec = false;
    					while(rs.next()) {
    						ada_rec = true;
    						String npmhs = ""+rs.getString(1);
    						lit.add(npmhs);
    					}
    					if(ada_rec) {
    						li.add(kmp);
        					li.add(vtmp);	
    					}
    					
    					//cek apa sudah daftar ulang dan sudah di approved
    					li = v.listIterator();
    					while(li.hasNext()) {
    						li.next();
    						vtmp = (Vector)li.next();
    						lit = vtmp.listIterator();
    						while(lit.hasNext()) {
    							String npmhs = (String)lit.next();
    							if(Checker.sudahDaftarUlang(null, npmhs, target_thsms)!=null) {
    								lit.remove();
    							}
    						}
    						li.set(vtmp);
    					}
    					
    				}
    				
    			}
         	}
         	catch (Exception e) {
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
    
    public void getSummaryMhsAktifPerKampusPerKdpst(Vector v_scope_id,String target_thsms) {
    	/*
    	 * AKTIF = ada krs & daftar ulang sudah di approved
    	 */
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		ListIterator lis = v_scope_id.listIterator();
    		try {
    				
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    			con = ds.getConnection();
    			
    			while(lis.hasNext()) {
    				String brs = (String)lis.next();
    				Vector vtmp = new Vector();
    				ListIterator lit = vtmp.listIterator();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kmp = st.nextToken();
    				String sql_addon = null;
    				if(st.hasMoreTokens()) {
    					sql_addon =new String(); 
    					do {
    						String tkn_id = st.nextToken();
    						sql_addon = sql_addon+"ID_OBJ="+tkn_id;
    						if(st.hasMoreTokens()) {
    							sql_addon = sql_addon+" OR ";
    						}
    					}
    					while(st.hasMoreTokens());
    				}
    				if(sql_addon!=null) {
    					stmt = con.prepareStatement("select * from TRNLM inner join CIVITAS on NPMHSTRNLM=NPMHSMSMHS where THSMSTRNLM=? and  ("+sql_addon+") order by KDPSTTRNLM");
    					stmt.setString(1, target_thsms);
    					rs = stmt.executeQuery();
    					boolean ada_rec = false;
    					while(rs.next()) {
    						ada_rec = true;
    						String kdpst = ""+rs.getString("KDPSTTRNLM");
    						String npmhs = ""+rs.getString("NPMHSTRNLM");
    						String idobj = ""+rs.getLong("ID_OBJ");
    						 
    						lit.add(npmhs+"`"+kdpst+"`"+idobj);
    						//System.out.println("--"+npmhs+"`"+kdpst+"`"+idobj);
    					}
    					if(ada_rec) {
    						//li.add(kmp);
    						//System.out.println("vtmp1="+vtmp.size());
    						vtmp = Tool.removeDuplicateFromVector(vtmp);
    						//System.out.println("vtmp2="+vtmp.size());
        					li.add(vtmp);	
    					}
    					
    					//cek apa sudah daftar ulang dan sudah di approved
    					li = v.listIterator();
    					while(li.hasNext()) {
    						//li.next();
    						vtmp = (Vector)li.next();
    						lit = vtmp.listIterator();
    						while(lit.hasNext()) {
    							String brs1 = (String)lit.next();
    							StringTokenizer st1 = new StringTokenizer(brs1,"`");
    							String npmhs = st1.nextToken();
    							String kdpst = st1.nextToken();
    							String idobj = st1.nextToken();
    							//if(Checker.sudahDaftarUlang(null, npmhs, target_thsms)!=null) { //belum dafatar ulang / inprogress
    							if(!Checker.sudahDaftarUlang(null, npmhs, target_thsms).startsWith("true")) { //belum dafatar ulang / inprogress
    								lit.remove();
    								//if(idobj.equalsIgnoreCase("102")) {
    								//	//System.out.println("remove "+brs1);
    								//}
    								
    							}
    							/*
    							else {
    								if(idobj.equalsIgnoreCase("102")) {
    									//System.out.println("keep "+brs1);
    								}
    							}
    							*/
    						}
    						li.set(vtmp);
    					}
    					//fileter per kdpst
    					li = v.listIterator();
    					while(li.hasNext()) {
    						//String kode_kmp = (String)li.next();
    						vtmp = (Vector)li.next();
    						lit = vtmp.listIterator();
    						if(lit.hasNext()) {
    							int tot = 0;
    							boolean first = true;
    							String prev_kdpst = "";
    							String prev_idobj = "";
    							do {
    								String brs1 = (String)lit.next();
    								//System.out.println("`="+brs1);
        							StringTokenizer st1 = new StringTokenizer(brs1,"`");
        							String npmhs = st1.nextToken();
        							String kdpst = st1.nextToken();
        							String idobj = st1.nextToken();
        							if(first) {
        								first = false;
        								tot++;
        								prev_kdpst = new String(kdpst);
        								prev_idobj = new String(idobj);
        							}
        							else {
        								if(prev_kdpst.equalsIgnoreCase(kdpst)) {
        									//belum berubah
        									tot++;
        								}
        								else {
        									lif.add(prev_idobj+"`"+prev_kdpst+"`"+tot);
        									//berubah
        									tot = 1;
        									prev_kdpst = new String(kdpst);
            								prev_idobj = new String(idobj);
        								}
        							}
        							if(!lit.hasNext()) {
        								lif.add(prev_idobj+"`"+prev_kdpst+"`"+tot);
        								//tutup
        							}
    							}
    							while(lit.hasNext());
    						}
    					}
    				}
    			}
    			int total = 0;
    			
    			if(vf!=null && vf.size()>0) {
    				lif = vf.listIterator();
    				//coba update table OVERVIEW_SEBARAN_TRLSM
    				stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_MHS_AKTIF=? where ID_OBJ=? and THSMS=?");
    				while(lif.hasNext()) {
    					String brs = (String)lif.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String idobj = st.nextToken();
    					String kdpst = st.nextToken();
    					String tot = st.nextToken();
    					stmt.setInt(1, Integer.parseInt(tot));
    					stmt.setInt(2, Integer.parseInt(idobj));
    					stmt.setString(3, target_thsms);
    					int i = stmt.executeUpdate();
    					if(i>0) {
    						lif.remove();
    					}
    					total = total+Integer.parseInt(tot);
    				}
    				//sisa diinsert
    				if(vf!=null && vf.size()>0) {
    					lif = vf.listIterator();
    					stmt = con.prepareStatement("insert into OVERVIEW_SEBARAN_TRLSM (ID_OBJ,THSMS,KDPST,TOT_MHS_AKTIF)values(?,?,?,?)");
        				while(lif.hasNext()) {
        					String brs = (String)lif.next();
        					StringTokenizer st = new StringTokenizer(brs,"`");
        					String idobj = st.nextToken();
        					String kdpst = st.nextToken();
        					String tot = st.nextToken();
        					stmt.setInt(1, Integer.parseInt(idobj));
        					stmt.setString(2, target_thsms);
        					stmt.setString(3, kdpst);
        					stmt.setInt(4, Integer.parseInt(tot));
        					stmt.executeUpdate();
        				}	
    				}
        				
    			}
    			//System.out.println("total="+total);
         	}
         	catch (Exception e) {
         		e.printStackTrace();
         	}
         	finally {
         		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
         		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
         		if (con!=null) try { con.close();} catch (Exception ignore){}
         	}
    		
    	}
    	//return vf; 		
    }
    
    
    
    public Vector getListNpmMhsAktif(String target_thsms, String target_kdpst) {
    	//semua prodi, termasuk kelas f
    	Vector vf = null;
    	ListIterator lif = null;
    	ListIterator li = null;
    	try {
    		if(!Checker.isStringNullOrEmpty(target_thsms)) {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			if(Checker.isStringNullOrEmpty(target_kdpst)) {
    				//semua prodi
    				//check trlsm
        			stmt = con.prepareStatement("select distinct NPMHS from TRLSM where THSMS=? and (STMHS='C' or STMHS='N' or STMHS='L')");
        			stmt.setString(1, target_thsms);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				vf = new Vector();
        				lif = vf.listIterator();
        				do {
        					String npmhs = rs.getString(1);
        					lif.add(npmhs);
        				}
        				while(rs.next());
        			}
        			//check trnlm
        			stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=?");
        			stmt.setString(1, target_thsms);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				if(vf == null) {
        					vf = new Vector();
            				lif = vf.listIterator();	
        				}
        				
        				do {
        					String npmhs = rs.getString(1);
        					lif.add(npmhs);
        				}
        				while(rs.next());
        			}	
    			}
    			else {
    				//semua prodi
    				//check trlsm
        			stmt = con.prepareStatement("select distinct NPMHS from TRLSM where THSMS=? and KDPST=? and (STMHS='C' or STMHS='N' or STMHS='L')");
        			stmt.setString(1, target_thsms);
        			stmt.setString(2, target_kdpst);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				vf = new Vector();
        				lif = vf.listIterator();
        				do {
        					String npmhs = rs.getString(1);
        					lif.add(npmhs);
        				}
        				while(rs.next());
        			}
        			//check trnlm
        			stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=?");
        			stmt.setString(1, target_thsms);
        			stmt.setString(2, target_kdpst);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				if(vf == null) {
        					vf = new Vector();
            				lif = vf.listIterator();	
        				}
        				
        				do {
        					String npmhs = rs.getString(1);
        					lif.add(npmhs);
        				}
        				while(rs.next());
        			}	
    			}
    			if(vf!=null && vf.size()>1) {
    				vf = Tool.removeDuplicateFromVector(vf);	
    			}
    			
    		}	
    	}
    	catch (NamingException e) {
    		e.printStackTrace();
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	} 
    	catch (Exception e){
    		
    	}
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	}   
    	return vf;
    }
}    
    	
    	
    	 

package beans.dbase.mhs.maintenance;

import beans.dbase.mhs.SearchDbInfoMhs;
import beans.tools.Checker;
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
import org.terracotta.quartz.collections.ToolkitDSHolder;

/**
 * Session Bean implementation class MaintenaceSearchDb
 */
@Stateless
@LocalBean
public class MaintenaceSearchDb extends SearchDbInfoMhs {
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
     * @see SearchDbInfoMhs#SearchDbInfoMhs()
     */
    public MaintenaceSearchDb() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDbInfoMhs#SearchDbInfoMhs(String)
     */
    public MaintenaceSearchDb(String operatorNpm) {
    	super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDbInfoMhs#SearchDbInfoMhs(Connection)
     */
    public MaintenaceSearchDb(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    
    
    
    public Vector cekMhsInputMultipleTimes(String smawl) {
    	Vector v = null;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select TGLHRMSMHS,NMMHSMSMHS from CIVITAS where SMAWLMSMHS=? and KDPSTMSMHS>? order by TGLHRMSMHS,NMMHSMSMHS");
			stmt.setString(1, smawl);
			stmt.setString(2, "1001");
			rs = stmt.executeQuery();
			//li.add("NIK  `   NAMA   `   TGL LAHIR   `   KODE PRODI   `   NPMHS");
			if(rs.next()) {
				String prev_tglhr = ""+rs.getDate(1);
				String prev_nmmhs = rs.getString(2);
				prev_tglhr = prev_tglhr.trim();
				prev_nmmhs = prev_nmmhs.trim();
				while(rs.next()) {
				//do {
					String tglhr = ""+rs.getDate(1);
					String nmmhs = rs.getString(2);
					tglhr = tglhr.trim();
					nmmhs = nmmhs.trim();
					//System.out.println(prev_tglhr+" vs "+tglhr);
					if(prev_tglhr.equalsIgnoreCase(tglhr) && prev_nmmhs.equalsIgnoreCase(nmmhs)) {
						if(v==null) {
							v = new Vector();
							li = v.listIterator();
						}
						//System.out.println("match");
						li.add(nmmhs+"`"+tglhr);
					}
					else {
						prev_tglhr =new String(tglhr);
						prev_nmmhs =new String(nmmhs);
					}
				}
				//while(rs.next());
				
			}
			if(v!=null) {
				v = beans.tools.Tool.removeDuplicateFromVector(v);	
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
    
    public Vector cekMhsDenganTglhrSama(String smawl) {
    	Vector v = null;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select TGLHRMSMHS,NMMHSMSMHS,NPMHSMSMHS,KDPSTMSMHS,TPLHRMSMHS from CIVITAS where SMAWLMSMHS=? and KDPSTMSMHS>? order by TGLHRMSMHS,NMMHSMSMHS");
			stmt.setString(1, smawl);
			stmt.setString(2, "1001");
			rs = stmt.executeQuery();
			//li.add("NIK  `   NAMA   `   TGL LAHIR   `   KODE PRODI   `   NPMHS");
			if(rs.next()) {
				String prev_tglhr = ""+rs.getDate(1);
				String prev_nmmhs = rs.getString(2);
				String prev_npmhs = rs.getString(3);
				String prev_kdpst = rs.getString(4);
				String prev_tplhr = rs.getString(5);
				prev_tglhr = prev_tglhr.trim();
				prev_nmmhs = prev_nmmhs.trim();
				prev_tplhr = prev_tplhr.trim();
				while(rs.next()) {
				//do {
					String tglhr = ""+rs.getDate(1);
					String nmmhs = rs.getString(2);
					String npmhs = rs.getString(3);
					String kdpst = rs.getString(4);
					String tplhr = rs.getString(5);
					tglhr = tglhr.trim();
					nmmhs = nmmhs.trim();
					tplhr = tplhr.trim();
					//System.out.println(prev_tglhr+" vs "+tglhr);
					//if(prev_tglhr.equalsIgnoreCase(tglhr) && prev_nmmhs.equalsIgnoreCase(nmmhs)) {
					if(prev_tglhr.equalsIgnoreCase(tglhr)) {
						if(v==null) {
							v = new Vector();
							li = v.listIterator();
						}
						//System.out.println("match");
						li.add(prev_kdpst+"`"+prev_npmhs+"`"+prev_nmmhs+"`"+prev_tglhr+"`"+prev_tplhr.toUpperCase());
						li.add(kdpst+"`"+npmhs+"`"+nmmhs+"`"+tglhr+"`"+tplhr.toUpperCase());
					}
					else {
						prev_tglhr =new String(tglhr);
						prev_nmmhs =new String(nmmhs);
						prev_npmhs =new String(npmhs);
						prev_kdpst =new String(kdpst);
						prev_tplhr =new String(tplhr);
					}
				}
				//while(rs.next());
				
			}
			if(v!=null) {
				v = beans.tools.Tool.removeDuplicateFromVector(v);	
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
    
    
    
    public Vector cekErrorKo(String thsms, Vector v_scope_id) {
    	Vector v = null;
    	ListIterator li = null;
    	Vector vf = null;
    	ListIterator lif = null;
    	StringTokenizer st = null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		String sql_cmd = "";
    		li = v_scope_id.listIterator();
    		while(li.hasNext()) {
                 String info_scope = (String)li.next();
                 st = new StringTokenizer(info_scope,"`");
                 st.nextToken();
                 while(st.hasMoreTokens()) {
                      String idobj = st.nextToken();
                      sql_cmd = sql_cmd+"ID_OBJ="+idobj;
                      if(st.hasMoreTokens()) {
                           sql_cmd = sql_cmd+" or ";
                      }
                 }
                 if(li.hasNext()) {
                	 sql_cmd = sql_cmd+" or ";
                 }
    		}
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select distinct KDPSTMSMHS,NPMHSTRNLM,NMMHSMSMHS from TRNLM inner join CIVITAS on NPMHSTRNLM=NPMHSMSMHS where THSMSTRNLM=? and ("+sql_cmd+")");
    			//System.out.println("select distinct KDPSTMSMHS,NPMHSTRNLM,NMMHSMSMHS from TRNLM inner join CIVITAS on NPMHSTRNLM=NPMHSMSMHS where THSMSTRNLM=? and ("+sql_cmd+")");
    			stmt.setString(1, thsms);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v = new Vector();
    				li = v.listIterator();
    				String kdpst = rs.getString(1);
    				String npmhs = rs.getString(2);
    				String nmmhs = rs.getString(3);
    				li.add(kdpst+"`"+npmhs+"`"+nmmhs);
    				while(rs.next()) {
    					kdpst = rs.getString(1);
        				npmhs = rs.getString(2);
        				nmmhs = rs.getString(3);
        				li.add(kdpst+"`"+npmhs+"`"+nmmhs);
    				}
    			}
    			//add mhs smawl = target_thsms yg blum ada krsnya at target_thsms
    			stmt = con.prepareStatement("select distinct KDPSTMSMHS,NPMHSMSMHS,NMMHSMSMHS from CIVITAS  where KDPSTMSMHS>'1000' and KDPSTMSMHS<>'UNREG' and SMAWLMSMHS=? and ("+sql_cmd+")");
    			stmt.setString(1, thsms);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				if(v==null) {
    					v = new Vector();	
    				}
    				li = v.listIterator();
    				String kdpst = rs.getString(1);
    				String npmhs = rs.getString(2);
    				String nmmhs = rs.getString(3);
    				li.add(kdpst+"`"+npmhs+"`"+nmmhs);
    				while(rs.next()) {
    					kdpst = rs.getString(1);
        				npmhs = rs.getString(2);
        				nmmhs = rs.getString(3);
        				li.add(kdpst+"`"+npmhs+"`"+nmmhs);
    				}
    			}
    			v = Tool.removeDuplicateFromVector(v);
    			//CEK NULL KO
    			if(v!=null && v.size()>0) {
    				li = v.listIterator();
    				stmt = con.prepareStatement("select KRKLMMSMHS from EXT_CIVITAS where NPMHSMSMHS=?");
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					st = new StringTokenizer(brs,"`");
    					String kdpst = st.nextToken();
    					String npmhs = st.nextToken();
    					String nmmhs = st.nextToken();
    					stmt.setString(1, npmhs);
    					rs = stmt.executeQuery();
    					String krklm = "null";
    					if(rs.next()) {
    						krklm = ""+rs.getString(1);
    						if(Checker.isStringNullOrEmpty(krklm)) {
    							krklm = new String("null");
    							if(vf == null) {
    								vf = new Vector();
    								lif = vf.listIterator();
    							}
    							lif.add(brs+"`KO belum ditentukan");
    							li.remove();
    						}
    						else {
    							li.set(brs+"`"+krklm);
    						}
    						
    					}
    					else {
    						if(vf == null) {
								vf = new Vector();
								lif = vf.listIterator();
							}
    						lif.add(brs+"`KO belum ditentukan");
							li.remove();
    					}
    				}
    			}
    			//CEK KO PRODI LAIN
    			if(v!=null && v.size()>0) {
    				li = v.listIterator();
    				stmt = con.prepareStatement("select NMKURKRKLM,STKURKRKLM from KRKLM where KDPSTKRKLM=? and IDKURKRKLM=?");
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					st = new StringTokenizer(brs,"`");
    					String kdpst = st.nextToken();
    					String npmhs = st.nextToken();
    					String nmmhs = st.nextToken();
    					String krklm_id = st.nextToken();
    					stmt.setString(1, kdpst);
    					stmt.setInt(2, Integer.parseInt(krklm_id));
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						String sta_kur = rs.getString(1);
    						if(sta_kur.equalsIgnoreCase("H")) {
    							if(vf == null) {
    								vf = new Vector();
    								lif = vf.listIterator();
    							}
    							lif.add(kdpst+"`"+npmhs+"`"+nmmhs+"`Menggunakan KO yg sudah tidak aktif");
    						}
    					}
    					else {
    						if(vf == null) {
								vf = new Vector();
								lif = vf.listIterator();
							}
    						lif.add(kdpst+"`"+npmhs+"`"+nmmhs+"`Menggunakan KO Prodi lain");
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
        	catch (Exception ex) {
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
    
    
    public Vector cekKoError(String smawl) {
    	Vector v = null;
    	Vector vf = null;
    	StringTokenizer st = null;
    	ListIterator lif = null, li=null;
    	
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select a.KDPSTMSMHS,a.NPMHSMSMHS,KRKLMMSMHS from CIVITAS a inner join EXT_CIVITAS b on a.NPMHSMSMHS=b.NPMHSMSMHS where SMAWLMSMHS=? and a.KDPSTMSMHS>'1001' order by NPMHSMSMHS");
			stmt.setString(1, smawl);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v =new Vector();
				li = v.listIterator();
				String kdpst = rs.getString(1);
				String npmhs = rs.getString(2);
				String krklm = rs.getString(3);
				li.add(kdpst+"`"+npmhs+"`"+krklm);
				while(rs.next()) {
					kdpst = rs.getString(1);
					npmhs = rs.getString(2);
					krklm = rs.getString(3);
					li.add(kdpst+"`"+npmhs+"`"+krklm);
				}
				//System.out.println("v size = "+v.size());
				li = v.listIterator();
				stmt = con.prepareStatement("select NMKURKRKLM from KRKLM where IDKURKRKLM=? and KDPSTKRKLM=? limit 1");
				while(li.hasNext()) {
					String brs = (String)li.next();
					//System.out.println("baris = "+brs);
					st = new StringTokenizer(brs,"`");
					kdpst = st.nextToken();
					npmhs = st.nextToken();
					krklm = st.nextToken();
					if(beans.tools.Checker.isStringNullOrEmpty(krklm)) {
						if(vf==null) {
							vf = new Vector();
							lif = vf.listIterator();
						}
						lif.add("KO tidak valid`"+npmhs+" ["+krklm+"]");
					}
					else {
						stmt.setInt(1, Integer.parseInt(krklm));
						stmt.setString(2, kdpst);
						rs = stmt.executeQuery();
						if(!rs.next()) {
							if(vf==null) {
								vf = new Vector();
								lif = vf.listIterator();
							}
							lif.add("KO tidak valid`"+npmhs+" ["+krklm+"]");
						}
					}
					
				}
				
				
			}
			
			
			//li.add("NIK  `   NAMA   `   TGL LAHIR   `   KODE PRODI   `   NPMHS");
			
			if(vf!=null) {
				vf = beans.tools.Tool.removeDuplicateFromVector(vf);	
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
    	return vf;
    }
    
    
    public Vector errMhsSdhKeluarAdaRecordBaru(String thsms, Vector v_getSebaranTrlsm_getListNpmMhsAktif) {
    	//Vector v = null;
    	//Vector vf = null;
    	StringTokenizer st = null;
    	ListIterator lif = null, li=null;
    	if(v_getSebaranTrlsm_getListNpmMhsAktif!=null && v_getSebaranTrlsm_getListNpmMhsAktif.size()>0) {
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();	
    			stmt = con.prepareStatement("select THSMS,STMHS from TRLSM where THSMS<? and NPMHS=? and (STMHS='D' or STMHS='L' or STMHS='K') limit 1");
    			li = v_getSebaranTrlsm_getListNpmMhsAktif.listIterator();
    			while(li.hasNext()) {
    				String npmhs = (String)li.next();
    				//System.out.print(npmhs);
    				stmt.setString(1,thsms);
    				stmt.setString(2,npmhs);
    				rs = stmt.executeQuery();
    				if(!rs.next()) {
    					li.remove();
    					//System.out.println(" removed");
    				}
    				else {
    					String tmp_thsms = ""+rs.getString(1);
    					String tmp_stmhs = ""+rs.getString(2);
    					String tmp = npmhs+"`"+tmp_thsms+"`"+tmp_stmhs;
    					while(tmp.contains("``")) {
    						tmp = tmp.replace("``", "`null`");
    					}
    					if(tmp.endsWith("`")) {
    						tmp = tmp + "null";
    					}
    					li.set(tmp);
    					//System.out.println(" set "+tmp);
    				}
    			}
    			//System.out.println("v_getSebaranTrlsm_getListNpmMhsAktif="+v_getSebaranTrlsm_getListNpmMhsAktif.size());
    			if(v_getSebaranTrlsm_getListNpmMhsAktif!=null && v_getSebaranTrlsm_getListNpmMhsAktif.size()>0) {
    				li = v_getSebaranTrlsm_getListNpmMhsAktif.listIterator();
    				stmt = con.prepareStatement("select KDPSTMSMHS,NMMHSMSMHS,NIMHSMSMHS from CIVITAS where NPMHSMSMHS=?");
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					//System.out.println("baris="+brs);
    					st = new StringTokenizer(brs,"`");
    					String npmhs1 = st.nextToken();
    					String thsms1 = st.nextToken();
    					String stmhs1 = st.nextToken();
    					stmt.setString(1, npmhs1);
    					rs = stmt.executeQuery();
    					rs.next();
    					String kdpst = rs.getString(1);
    					String nmmhs = rs.getString(2);
    					String nimhs = rs.getString(3);
    					li.set(kdpst+"`"+npmhs1+"`"+nmmhs+"`"+nimhs+"`"+stmhs1+"`"+thsms1);
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
    	}
    	if(v_getSebaranTrlsm_getListNpmMhsAktif!=null && v_getSebaranTrlsm_getListNpmMhsAktif.size()<1) {
    		v_getSebaranTrlsm_getListNpmMhsAktif = null;
    	}
    	return v_getSebaranTrlsm_getListNpmMhsAktif;
    }
    
    public Vector getListNpmMhsYgAdaStatusTrlsmSebelumSmawl(String target_kode_stmhs, String starting_smawl_yang_akan_dicek) {
    	Vector v = null;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			String sql = "select THSMS,KDPST,NPMHS from TRLSM A inner join CIVITAS B on NPMHS=NPMHSMSMHS where STMHS=? and THSMS<SMAWLMSMHS and SMAWLMSMHS>=?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, target_kode_stmhs);
			stmt.setString(2, starting_smawl_yang_akan_dicek);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					String thsms = rs.getString(1);
					String kdpst = rs.getString(2);
					String npmhs = rs.getString(3);	
					li.add(thsms+"`"+kdpst+"`"+npmhs);
					//System.out.println(thsms+"`"+kdpst+"`"+npmhs);
				}
				while(rs.next());
				
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

}

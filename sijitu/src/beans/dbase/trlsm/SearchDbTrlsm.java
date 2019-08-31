package beans.dbase.trlsm;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;
import java.sql.ResultSetMetaData;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class SearchDbTrlsm
 */
@Stateless
@LocalBean
public class SearchDbTrlsm extends SearchDb {
	String operatorNpm;
	String operatorNmm;
	String tknOperatorNickname;
	boolean petugas;
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds; 
	String tamplete_colom_excel_ijazah_versi_full_editor="THSMS as TAHUN_SEMESTER_LULUS,KDPST as KODE_PRODI,NPMHS as NOMOR_POKOK_MAHASISWA,NIMHSMSMHS as NOMOR_INDUK_MAHASISWA,NMMHSMSMHS as NAMA,STMHS,SKSTT as TOTAL_SKS,NLIPK as NILAI_IPK,NOSKR as NO_SK_REKTOR,TGLLS as TGL_LULUS,TGL_WISUDA,JUDUL as JUDUL_TUGAS_AKHIR, NOIJA as NOMOR_IJAZAH,NIRL,TGL_TERBIT_IJA as TGL_TERBIT_IJAZAH";
	String tamplete_colom_excel_ijazah_versi_sub_editor= "THSMS as TAHUN_SEMESTER_LULUS,KDPST as KODE_PRODI,NPMHS as NOMOR_POKOK_MAHASISWA,NIMHSMSMHS as NOMOR_INDUK_MAHASISWA,NMMHSMSMHS as NAMA,STMHS,SKSTT as TOTAL_SKS,NLIPK as NILAI_IPK,NOSKR as NO_SK_REKTOR,TGLLS as TGL_LULUS,TGL_WISUDA,JUDUL as JUDUL_TUGAS_AKHIR";
    /**
     * @see SearchDb#SearchDb()
     */
    public SearchDbTrlsm() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbTrlsm(String operatorNpm) {
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
    public SearchDbTrlsm(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
   
    public Vector getListLulusanTanpaMkAkhir(String thsms_lulus) {
    	Vector v = null;
    	ListIterator li = null;
    	if(!Checker.isStringNullOrEmpty(thsms_lulus)) {
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("SELECT KDPST,NPMHS,NMMHSMSMHS,KRKLMMSMHS FROM TRLSM inner join EXT_CIVITAS a on NPMHS=a.NPMHSMSMHS left join CIVITAS b on NPMHS=b.NPMHSMSMHS where THSMS=? and STMHS=?");
    			stmt.setString(1, thsms_lulus);
    			stmt.setString(2, "L");
    			rs = stmt.executeQuery();
				if(rs.next()) {
					v = new Vector();
					li = v.listIterator();
					do {
						String kdpst = ""+rs.getString(1);
						String npmhs = ""+rs.getString(2);
						String nmmhs = ""+rs.getString(3);
						String krklm = ""+rs.getString(4);
						li.add(kdpst+"`"+npmhs+"`"+nmmhs+"`"+krklm);	
					}
					while(rs.next());
					
				}
				//get info makul akhir
    			if(v!=null && v.size()>0) {
    				stmt = con.prepareStatement("SELECT IDKMKMAKUL,KDKMKMAKUL FROM MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where FINAL_MK=true and IDKURMAKUR=?");
    				li = v.listIterator();
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					String seperator = Checker.getSeperatorYgDigunakan(brs);
    					StringTokenizer st = new StringTokenizer(brs,seperator);
    					while(st.hasMoreTokens()) {
    						String kdpst = st.nextToken();
    						String npmhs = st.nextToken();
    						String nmmhs = st.nextToken();
    						String krklm = st.nextToken();
    						if(Checker.isStringNullOrEmpty(krklm)) {
    							li.set(kdpst+"`"+npmhs+"`"+nmmhs+"`K.O. Mhs belum ditentukan");
    						}
    						else {
    							try {
    								int kur = Integer.parseInt(krklm);
    								stmt.setInt(1, kur);
    								rs = stmt.executeQuery();
    								if(rs.next()) {
    									String idkmk = rs.getString(1);
    									String kdkmk = rs.getString(2);
    									String tmp = kdpst+"`"+npmhs+"`"+nmmhs+"`"+idkmk+"`"+kdkmk;
    									while(tmp.contains("``")) {
    										tmp = tmp.replace("``", "`null`");
    									}
    									if(tmp.endsWith("`")) {
    										tmp = tmp + "null";
    									}
    									if(tmp.endsWith("null")) { //kdkmk harus ada
    										li.set(kdpst+"`"+npmhs+"`"+nmmhs+"`Kode MK akhir K.O. is null");
    									}
    									else {
    										li.set(kdpst+"`"+npmhs+"`"+nmmhs+"`"+idkmk+"`"+kdkmk);	
    									}
    									
    								}
    								else {
    									li.set(kdpst+"`"+npmhs+"`"+nmmhs+"`MK akhir K.O. belum ditentukan");
    								}
    							}
    							catch(Exception e) {
    								li.set(kdpst+"`"+npmhs+"`"+nmmhs+"`Id Kurikulum tidak terdaftar");
    							}
    							
    						}
    					}
    				}
    			}
    			//cek krs apa ada mk final
    			//get info makul akhir
    			if(v!=null && v.size()>0) {
    				stmt = con.prepareStatement("SELECT THSMSTRNLM FROM TRNLM where THSMSTRNLM=? and NPMHSTRNLM=? and (KDKMKTRNLM=? or IDKMKTRNLM=?) limit 1");
    				li = v.listIterator();
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					String seperator = Checker.getSeperatorYgDigunakan(brs);
    					StringTokenizer st = new StringTokenizer(brs,seperator);
    					if(st.countTokens()==5) {
    						while(st.hasMoreTokens()) {
        						String kdpst = st.nextToken();
        						String npmhs = st.nextToken();
        						String nmmhs = st.nextToken();
        						String idkmk = st.nextToken();	
        						String kdkmk = st.nextToken();
        						if(Checker.isStringNullOrEmpty(idkmk)) {
        							idkmk = "-212";
        						}
        						stmt.setString(1, thsms_lulus);
        						stmt.setString(2, npmhs);
        						stmt.setString(3, kdkmk);
        						stmt.setInt(4, Integer.parseInt(idkmk));
        						rs = stmt.executeQuery();
        						if(rs.next()) {
        							li.remove();
        						}
        						else {
        							li.set(kdpst+"`"+npmhs+"`"+nmmhs+"`Mk final tidak ada di KRS");
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
    

    public Vector getListMhsGivenStmhs(Vector v_scope_id, String thsms, String stmhs) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		ListIterator lis  = v_scope_id.listIterator();
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select KDPST,NPMHS,NMMHSMSMHS from TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and ID_OBJ=? and STMHS=? order by NPMHS");
    			while(lis.hasNext()) {
    				String brs = (String)lis.next();
    				//System.out.println("barisan=="+brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				st.nextToken();//kdkmp
    				while(st.hasMoreTokens()) {
    					String idobj = st.nextToken();
    					stmt.setString(1, thsms);
    					stmt.setInt(2, Integer.parseInt(idobj));
    					stmt.setString(3, stmhs);
    					rs = stmt.executeQuery();
    					while(rs.next()) {
    						String kdpst = ""+rs.getString(1);
    						String npmhs = ""+rs.getString(2);
    						String nmmhs = ""+rs.getString(3);
    						li.add(kdpst+"`"+npmhs+"`"+nmmhs);
    					}
    				}
    			}
    			if(v!=null && v.size()>1) {
    				Collections.sort(v);
    			}
        	}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
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
    
    
    public Vector getListMhsGivenStmhsTiapKampus(String target_thsms, Vector v_scope_id, String tkn_stmhs, boolean angel_included) {
    	Vector v_scope_kdpst = null;
    	int tot_mhs = 0;
    	int tot_wip = 0;
    	ListIterator li = null;
    	if(v_scope_id!=null && v_scope_id.size()>0 && (tkn_stmhs!=null && tkn_stmhs.trim().length()>0)) {
    		try {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		StringTokenizer st = null;
        		if(tkn_stmhs.contains("`")) {
        			st = new StringTokenizer(tkn_stmhs,"`");
        		}
        		else if(tkn_stmhs.contains(",")) {
        			st = new StringTokenizer(tkn_stmhs,",");
        		}
        		else if(tkn_stmhs.contains("~")) {
        			st = new StringTokenizer(tkn_stmhs,"~");
        		}
        		else {
        			st = new StringTokenizer(tkn_stmhs);
        		}
        		String scope_stmhs = "";
        		while(st.hasMoreTokens()) {
                	String stm = st.nextToken();
                	scope_stmhs = scope_stmhs+"STMHS='"+stm+"'";
                    if(st.hasMoreTokens()) {
                    	scope_stmhs = scope_stmhs+" or ";
                    }
                }
        		
        		v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
            	li = v_scope_kdpst.listIterator();
                while(li.hasNext()) {
                	String sql_cmd = null;
                    String info_scope = (String)li.next();
                    String list_npm = null; //per kampus soalnya
                    st = new StringTokenizer(info_scope,"`");
                    if(st.countTokens()>1) {
                    	sql_cmd = new String("");

                        String kdkmp=st.nextToken();
                        while(st.hasMoreTokens()) {
                        	String kdpst = st.nextToken();
                            sql_cmd = sql_cmd+"A.KDPST='"+kdpst+"'";
                            if(st.hasMoreTokens()) {
                            	sql_cmd = sql_cmd+" or ";
                            }
                        }
                        
                        if(!Checker.isStringNullOrEmpty(sql_cmd)) {
                        	//sql_cmd = new String("select distinct KDPST,NPMHS,NMMHSMSMHS,SMAWLMSMHS,ALL_APPROVED from DAFTAR_ULANG inner join CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and ALL_APPROVED=? and ("+sql_cmd+") order by KDPST,ALL_APPROVED,NPMHS");
                        	if(angel_included) {
                        		sql_cmd = new String("select A.KDPST as PRODI,A.NPMHS as NPM,NMMHSMSMHS as NAMA,SMAWLMSMHS as ANGKATAN,STMHS as STATUS from TRLSM A inner join CIVITAS B on NPMHS=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ where THSMS=? and ("+scope_stmhs+") and ("+sql_cmd+") and KODE_KAMPUS_DOMISILI=? order by A.NPMHS");
                        	}
                        	else {
                        		sql_cmd = new String("select A.KDPST as PRODI,A.NPMHS as NPM,NMMHSMSMHS as NAMA,SMAWLMSMHS as ANGKATAN,STMHS as STATUS from TRLSM A inner join CIVITAS B on NPMHS=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ where THSMS=? and ("+scope_stmhs+") and MALAIKAT=false and ("+sql_cmd+") and KODE_KAMPUS_DOMISILI=? order by A.NPMHS");
                        	}
                        	//System.out.println("sql = "+sql_cmd);
                        	stmt = con.prepareStatement(sql_cmd);
                    		stmt.setString(1, target_thsms);
                    		//stmt.setString(2, stmhs);
                    		stmt.setString(2, kdkmp);
                    		rs = stmt.executeQuery();
                    		while(rs.next()) {
                        		
                        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
                        		String kdpst = ""+rs.getString(1);
                        		String npmhs = ""+rs.getString(2);
                        		String nmmhs = ""+rs.getString(3);
                        		String smawl = ""+rs.getString(4);
                        		String status = ""+rs.getString(5);
                        		//String approved = ""+rs.getBoolean(5);
                        		if(list_npm==null) {
                        			//list_npm=new String(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved);
                        			list_npm=new String(kdpst+","+npmhs+","+nmmhs+","+smawl+","+status);
                        		}
                        		else {
                        			//list_npm=list_npm+"`"+kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved;
                        			list_npm=list_npm+","+kdpst+","+npmhs+","+nmmhs+","+smawl+","+status;
                        		}
                        		//li.add(kdpst);
                        		li.set(info_scope+"~"+list_npm);
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
    	}
    	
    	return v_scope_kdpst;
    }
    
    public Vector listLulusan(String kdpst, String thsms) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and KDPST=? and STMHS=?");
			stmt.setString(1, thsms);
			stmt.setString(2, kdpst);
			stmt.setString(3, "L");
			rs = stmt.executeQuery();
			while(rs.next()) {
				String nmmhs = ""+rs.getString("NMMHSMSMHS");
				String nimhs = ""+rs.getString("NIMHSMSMHS");
				String tglls = ""+rs.getDate("TGLLS");
				li.add(nmmhs+"`"+nimhs+"`"+tglls);
			}
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return v;
	}	
    
    public Vector getRiwayatTrlsmFromSmawTilNow(String npmhs, String kdpst) {
    	Vector v = null;
    	String thsms_now = Checker.getThsmsHeregistrasi();
    	long objid = Checker.getObjectId(npmhs);
    	ListIterator li = null;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			
			stmt = con.prepareStatement("select SMAWLMSMHS from CIVITAS where NPMHSMSMHS=?");
			stmt.setString(1, npmhs);
			rs = stmt.executeQuery();
			rs.next();
			String smawl = rs.getString(1);
			v = Tool.returnTokensListThsms_v1(smawl, thsms_now, kdpst);
			if(v!=null) {
				stmt = con.prepareStatement("select * from TRLSM where THSMS=? and NPMHS=?");
				li = v.listIterator();
				while(li.hasNext()) {
					String thsms =(String)li.next();
					stmt.setString(1, thsms);
					stmt.setString(2, npmhs);
					rs = stmt.executeQuery();
					String tmp = "";
					if(rs.next()) {
						String stmhs = ""+rs.getString("STMHS");
						String note = ""+rs.getString("NOTE");
						if(Checker.isStringNullOrEmpty(note.trim())) {
							tmp = thsms+"`"+stmhs+"`null";
							//li.set();
						}
						else {
							tmp = thsms+"`"+stmhs+"`"+note;
							//li.set(thsms+"`"+stmhs+"`"+note);	
						}
						
						//System.out.println("="+thsms+"`"+stmhs+"`"+note);
					}
					else {
						tmp = thsms+"`N`null";
						//li.set(thsms+"`N`null");
						//System.out.println("="+thsms+"`N`null");
					}
					if(tmp.startsWith("`")) {
						tmp = "null`"+tmp;
					}
					if(tmp.endsWith("`")) {
						tmp = tmp+"`null";
					}
					while(tmp.contains("``")) {
						tmp = tmp.replace("``", "`null`");
					}
					li.set(tmp);
				}	
			}		
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return v;
	}	
    		
    public Vector cekNoijaEmpty(Vector v_scope_kdpst, String thsms, boolean full_editor) {
    	Vector v = null;
    	ListIterator li = null;
    	if(v_scope_kdpst!=null && v_scope_kdpst.size()>0) {
    		
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			String sql_cmd = "";
        		li = v_scope_kdpst.listIterator();
                while(li.hasNext()) {
                    String info_scope = (String)li.next();
                    StringTokenizer st = new StringTokenizer(info_scope,"`");
                    st.nextToken();
                    while(st.hasMoreTokens()) {
                         String kdpst = st.nextToken();
                         sql_cmd = sql_cmd+"KDPST='"+kdpst+"'";
                         if(st.hasMoreTokens()) {
                              sql_cmd = sql_cmd+" or ";
                         }
                    }
                    if(li.hasNext()) {
                    	sql_cmd = sql_cmd+" or ";	
                    }
                }
                
                
                
                if(Checker.isStringNullOrEmpty(thsms)) {
                	
                	if(full_editor) {
                		sql_cmd = "select "+tamplete_colom_excel_ijazah_versi_full_editor+" from TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS where STMHS='L' and NOIJA is null and ("+sql_cmd+")";	
                	}
                	else {
                		sql_cmd = "select "+tamplete_colom_excel_ijazah_versi_sub_editor+" from TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS where STMHS='L' and NOIJA is null and ("+sql_cmd+")";
                	}
                	stmt = con.prepareStatement(sql_cmd);
                }
                else {
                	if(full_editor) {
                		sql_cmd = "select "+tamplete_colom_excel_ijazah_versi_full_editor+" from TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS where STMHS='L' and NOIJA is null and ("+sql_cmd+") and THSMS=?";	
                	}
                	else {
                		sql_cmd = "select "+tamplete_colom_excel_ijazah_versi_sub_editor+" from TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS where STMHS='L' and NOIJA is null and ("+sql_cmd+") and THSMS=?";
                	}
                	//sql_cmd = "select THSMS as TAHUN_SEMESTER_LULUS,KDPST as KODE_PRODI,NPMHS as NOMOR_POKOK_MAHASISWA,NIMHSMSMHS as NOMOR_INDUK_MAHASISWA,NMMHSMSMHS as NAMA,STMHS,SKSTT as TOTAL_SKS,NLIPK as NILAI_IPK,NOSKR as NO_SK_REKTOR,TGLLS as TGL_LULUS,TGL_WISUDA,NOIJA as NOMOR_IJAZAH,NIRL,TGL_TERBIT_IJA as TANGGAL_TERBIT_IJAZAH from TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS where STMHS='L' and NOIJA is null and ("+sql_cmd+") and THSMS=?";
                	stmt = con.prepareStatement(sql_cmd);
                	stmt.setString(1, thsms);
                }
                
                //System.out.println("sql_cmd="+sql_cmd);
    			
    			rs = stmt.executeQuery();
    			if(rs!=null) {
    				rs = stmt.executeQuery();
    				ResultSetMetaData rsmd = rs.getMetaData();
    				int columnsNumber = rsmd.getColumnCount();
    				String col_label = null;
    				String col_name = null;
    				String col_type = null;
    				for(int i=1;i<=columnsNumber;i++) {
    					String col_name_tmp = rsmd.getColumnName(i);
    					String col_label_tmp = rsmd.getColumnLabel(i);
    					String info = new String(col_name_tmp);
    					if(Checker.isStringNullOrEmpty(col_label_tmp)) {
    						if(col_name_tmp.contains("TGL")) {
    							info = col_name_tmp+"\n[dd/mm/yyyy]";
    						}
    					}
    					else {
    						info = new String(col_label_tmp);
    						if(col_label_tmp.contains("TGL")) {
    							info = col_label_tmp+"\n[dd/mm/yyyy]";
    						}
    					}
    					
        				if(col_label==null) {
        					//col_label = new String("NO`"+rsmd.getColumnLabel(i)+"["+rsmd.getColumnName(i)+"]");
        					col_label = new String("NO`"+info);
        					col_type = new String("string`");
        				}
        				else {
        					//col_label = col_label+"`"+rsmd.getColumnLabel(i)+"["+rsmd.getColumnName(i)+"]";
        					col_label = col_label+"`"+info;
        				}
        				
        				//if(col_name==null) {
        				//	col_name = new String(rsmd.getColumnName(i));
        				//	col_type = new String("`");
        				//}
        				//else {
        				//	col_name = col_name+"`"+rsmd.getColumnName(i);
        				//}
        				
        				//get col type
        				int type = rsmd.getColumnType(i);
        				if(type == java.sql.Types.DATE) {
        					col_type = col_type+"date`";
        				}
        				else if(type == java.sql.Types.DECIMAL || type == java.sql.Types.DOUBLE || type == java.sql.Types.FLOAT ) {
        					col_type = col_type+"double`";
        				}
        				else if(type == java.sql.Types.INTEGER || type == java.sql.Types.BIGINT || type == java.sql.Types.NUMERIC || type == java.sql.Types.SMALLINT) {
        					col_type = col_type+"long`";
        				}
        				else if(type == java.sql.Types.VARCHAR || type == java.sql.Types.LONGNVARCHAR || type == java.sql.Types.LONGVARCHAR || type == java.sql.Types.CHAR || type == java.sql.Types.NCHAR) {
        					col_type = col_type+"string`";
        				}
        				else if(type == java.sql.Types.TIME) {
        					col_type = col_type+"time`";
        				}
        				else if(type == java.sql.Types.BOOLEAN || type == java.sql.Types.TINYINT) {
        					col_type = col_type+"boolean`";
        				}
        				else if(type == java.sql.Types.TIMESTAMP) {
        					col_type = col_type+"timestamp`";
        				}
    				}
    				if(v==null) {
    					v = new Vector();
        				li=v.listIterator();
        				li.add(col_type);
        				li.add(col_label);
        				//li.add(col_name);
    				}
    				
    				String brs = null;
    				int norut = 0;
            		while(rs.next()) {
            			norut++;
            			for(int i=1;i<=columnsNumber;i++) {
            				
            				String tmp = "";
            				/*
            				 * ADA 2 metode cek column type, karena diupdate yg baruan adalah cara yg diatas, belum tau mana yg lebih efektif
            				 */
            				col_type = rsmd.getColumnTypeName(i);
            				
            				if(col_type.equalsIgnoreCase("VARCHAR")||col_type.equalsIgnoreCase("TEXT")||col_type.startsWith("CHAR")) {
            					tmp = ""+rs.getString(i);
            				}
            				else if(col_type.equalsIgnoreCase("TINYINT")) {
            					tmp = ""+rs.getBoolean(i);
            				}
            				else if(col_type.contains("INT")||col_type.contains("LONG")) {
            					tmp = ""+rs.getLong(i);
            				}
            				else if(col_type.equalsIgnoreCase("DATE")) {
            					tmp = ""+rs.getDate(i);
            				}
            				else if(col_type.equalsIgnoreCase("DECIMAL")||col_type.equalsIgnoreCase("DOUBLE")||col_type.equalsIgnoreCase("FLOAT")) {
            					tmp = ""+rs.getDouble(i);
            				}
            				else if(col_type.equalsIgnoreCase("TIMESTAMP")||col_type.equalsIgnoreCase("DATETIME")) {
            					tmp = ""+rs.getTimestamp(i);
            				}
            				
            				if(brs==null) {
            					if(Checker.isStringNullOrEmpty(tmp)) {
            						brs = new String(norut+"`null");
            					}
            					else {
            						brs = new String(norut+"`"+tmp);
            					}
            				}
            				else {
            					if(Checker.isStringNullOrEmpty(tmp)) {
            						brs = brs +"`null";
            					}
            					else {
            						brs = brs+"`"+tmp;
            					}
            				}	
            			}
            			li.add(brs);
            			brs = null;
            		}
    			}
    			
        	}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
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
    
    public Vector getDataTrlsm(Vector v_scope_kdpst, String thsms, boolean full_editor) {
    	Vector v = null;
    	ListIterator li = null;
    	if(v_scope_kdpst!=null && v_scope_kdpst.size()>0) {
    		
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			String sql_cmd = "";
        		li = v_scope_kdpst.listIterator();
                while(li.hasNext()) {
                    String info_scope = (String)li.next();
                    StringTokenizer st = new StringTokenizer(info_scope,"`");
                    st.nextToken();
                    while(st.hasMoreTokens()) {
                         String kdpst = st.nextToken();
                         sql_cmd = sql_cmd+"KDPST='"+kdpst+"'";
                         if(st.hasMoreTokens()) {
                              sql_cmd = sql_cmd+" or ";
                         }
                    }
                    if(li.hasNext()) {
                    	sql_cmd = sql_cmd+" or ";	
                    }
                }
                
                
                
                if(Checker.isStringNullOrEmpty(thsms)) {
                	
                	if(full_editor) {
                		sql_cmd = "select "+tamplete_colom_excel_ijazah_versi_full_editor+" from TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS where STMHS='L' and ("+sql_cmd+") order by THSMS,KDPST,NOIJA";	
                	}
                	else {
                		sql_cmd = "select "+tamplete_colom_excel_ijazah_versi_sub_editor+" from TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS where STMHS='L' and ("+sql_cmd+")  order by THSMS,KDPST,NOIJA";
                	}
                	stmt = con.prepareStatement(sql_cmd);
                }
                else {
                	if(full_editor) {
                		sql_cmd = "select "+tamplete_colom_excel_ijazah_versi_full_editor+" from TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS where STMHS='L' and ("+sql_cmd+") and THSMS=?  order by THSMS,KDPST,NOIJA";	
                	}
                	else {
                		sql_cmd = "select "+tamplete_colom_excel_ijazah_versi_sub_editor+" from TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS where STMHS='L' and ("+sql_cmd+") and THSMS=?  order by THSMS,KDPST,NOIJA";
                	}
                	//sql_cmd = "select THSMS as TAHUN_SEMESTER_LULUS,KDPST as KODE_PRODI,NPMHS as NOMOR_POKOK_MAHASISWA,NIMHSMSMHS as NOMOR_INDUK_MAHASISWA,NMMHSMSMHS as NAMA,STMHS,SKSTT as TOTAL_SKS,NLIPK as NILAI_IPK,NOSKR as NO_SK_REKTOR,TGLLS as TGL_LULUS,TGL_WISUDA,NOIJA as NOMOR_IJAZAH,NIRL,TGL_TERBIT_IJA as TANGGAL_TERBIT_IJAZAH from TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS where STMHS='L' and NOIJA is null and ("+sql_cmd+") and THSMS=?";
                	stmt = con.prepareStatement(sql_cmd);
                	stmt.setString(1, thsms);
                }
                
                //System.out.println("sql_cmd="+sql_cmd);
    			
    			rs = stmt.executeQuery();
    			if(rs!=null) {
    				rs = stmt.executeQuery();
    				ResultSetMetaData rsmd = rs.getMetaData();
    				int columnsNumber = rsmd.getColumnCount();
    				String col_label = null;
    				String col_name = null;
    				String col_type = null;
    				for(int i=1;i<=columnsNumber;i++) {
    					String col_name_tmp = rsmd.getColumnName(i);
    					String col_label_tmp = rsmd.getColumnLabel(i);
    					String info = new String(col_name_tmp);
    					if(Checker.isStringNullOrEmpty(col_label_tmp)) {
    						if(col_name_tmp.contains("TGL")) {
    							info = col_name_tmp+"\n[dd/mm/yyyy]";
    						}
    					}
    					else {
    						info = new String(col_label_tmp);
    						if(col_label_tmp.contains("TGL")) {
    							info = col_label_tmp+"\n[dd/mm/yyyy]";
    						}
    					}
    					
        				if(col_label==null) {
        					//col_label = new String("NO`"+rsmd.getColumnLabel(i)+"["+rsmd.getColumnName(i)+"]");
        					col_label = new String("NO`"+info);
        					col_type = new String("string`");
        				}
        				else {
        					//col_label = col_label+"`"+rsmd.getColumnLabel(i)+"["+rsmd.getColumnName(i)+"]";
        					col_label = col_label+"`"+info;
        				}
        				
        				//if(col_name==null) {
        				//	col_name = new String(rsmd.getColumnName(i));
        				//	col_type = new String("`");
        				//}
        				//else {
        				//	col_name = col_name+"`"+rsmd.getColumnName(i);
        				//}
        				
        				//get col type
        				int type = rsmd.getColumnType(i);
        				if(type == java.sql.Types.DATE) {
        					col_type = col_type+"date`";
        				}
        				else if(type == java.sql.Types.DECIMAL || type == java.sql.Types.DOUBLE || type == java.sql.Types.FLOAT ) {
        					col_type = col_type+"double`";
        				}
        				else if(type == java.sql.Types.INTEGER || type == java.sql.Types.BIGINT || type == java.sql.Types.NUMERIC || type == java.sql.Types.SMALLINT) {
        					col_type = col_type+"long`";
        				}
        				else if(type == java.sql.Types.VARCHAR || type == java.sql.Types.LONGNVARCHAR || type == java.sql.Types.LONGVARCHAR || type == java.sql.Types.CHAR || type == java.sql.Types.NCHAR) {
        					col_type = col_type+"string`";
        				}
        				else if(type == java.sql.Types.TIME) {
        					col_type = col_type+"time`";
        				}
        				else if(type == java.sql.Types.BOOLEAN || type == java.sql.Types.TINYINT) {
        					col_type = col_type+"boolean`";
        				}
        				else if(type == java.sql.Types.TIMESTAMP) {
        					col_type = col_type+"timestamp`";
        				}
    				}
    				if(v==null) {
    					v = new Vector();
        				li=v.listIterator();
        				li.add(col_type);
        				li.add(col_label);
        				//li.add(col_name);
    				}
    				
    				String brs = null;
    				int norut = 0;
            		while(rs.next()) {
            			norut++;
            			for(int i=1;i<=columnsNumber;i++) {
            				
            				String tmp = "";
            				/*
            				 * ADA 2 metode cek column type, karena diupdate yg baruan adalah cara yg diatas, belum tau mana yg lebih efektif
            				 */
            				col_type = rsmd.getColumnTypeName(i);
            				
            				if(col_type.equalsIgnoreCase("VARCHAR")||col_type.equalsIgnoreCase("TEXT")||col_type.startsWith("CHAR")) {
            					tmp = ""+rs.getString(i);
            				}
            				else if(col_type.equalsIgnoreCase("TINYINT")) {
            					tmp = ""+rs.getBoolean(i);
            				}
            				else if(col_type.contains("INT")||col_type.contains("LONG")) {
            					tmp = ""+rs.getLong(i);
            				}
            				else if(col_type.equalsIgnoreCase("DATE")) {
            					tmp = ""+rs.getDate(i);
            				}
            				else if(col_type.equalsIgnoreCase("DECIMAL")||col_type.equalsIgnoreCase("DOUBLE")||col_type.equalsIgnoreCase("FLOAT")) {
            					tmp = ""+rs.getDouble(i);
            				}
            				else if(col_type.equalsIgnoreCase("TIMESTAMP")||col_type.equalsIgnoreCase("DATETIME")) {
            					tmp = ""+rs.getTimestamp(i);
            				}
            				
            				if(brs==null) {
            					if(Checker.isStringNullOrEmpty(tmp)) {
            						brs = new String(norut+"`null");
            					}
            					else {
            						brs = new String(norut+"`"+tmp);
            					}
            				}
            				else {
            					if(Checker.isStringNullOrEmpty(tmp)) {
            						brs = brs +"`null";
            					}
            					else {
            						brs = brs+"`"+tmp;
            					}
            				}	
            			}
            			li.add(brs);
            			brs = null;
            		}
    			}
    			
        	}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
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
    
    public Vector cekThsmsTanpaKabar(Vector v_npmhs_smawl, String thsms_max) {
    	//Vector v = null;
    	ListIterator li = null;
    	StringTokenizer st = null;
    	if(v_npmhs_smawl!=null && v_npmhs_smawl.size()>0) {
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=? limit 1"); 
    			li = v_npmhs_smawl.listIterator();
    			
    			while(li.hasNext()) {
    				String thsms_no_krs = null;
    				String brs = (String)li.next(); 
    				st = new StringTokenizer(brs,"`");
    				String npmhs = st.nextToken();
    				String smawl = st.nextToken();
    				//System.out.println("target = "+brs);
    				while(smawl.compareToIgnoreCase(thsms_max)<=0) {
    					stmt.setString(1, smawl);
    					stmt.setString(2, npmhs);
    					rs = stmt.executeQuery();
    					if(!rs.next()) {
    						if(thsms_no_krs==null) {
    							thsms_no_krs = new String(smawl);
    						}
    						else {
    							thsms_no_krs = thsms_no_krs+"`"+smawl;
    						}
    					}
    					smawl = Tool.returnNextThsmsGivenTpAntara(smawl);
    				}
    				if(thsms_no_krs==null) {
    					li.remove();
    				}
    				else {
    					li.set(brs+"`"+thsms_no_krs);
    				}
    			}
    			//cek trlsm
    			if(v_npmhs_smawl!=null && v_npmhs_smawl.size()>0) {
    				stmt = con.prepareStatement("select STMHS from TRLSM where THSMS=? and NPMHS=? limit 1");
    				li = v_npmhs_smawl.listIterator();
    				
        			while(li.hasNext()) {
        				
        				String brs = (String)li.next(); 
        				st = new StringTokenizer(brs,"`");
        				String npmhs = st.nextToken();
        				String smawl = st.nextToken();
        				String tkn_thsms_no_news = null;
        				boolean out = false;
        				while(st.hasMoreTokens() && !out) {
        					String thsms_tested = st.nextToken();
        					stmt.setString(1, thsms_tested);
        					stmt.setString(2, npmhs);
        					rs = stmt.executeQuery();
        					if(rs.next()) {
        						String stmhs = rs.getString(1);
        						if(stmhs.equalsIgnoreCase("L")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D")) {
        							out = true;
        						}
        					}
        					else {
        						if(tkn_thsms_no_news==null) {
        							tkn_thsms_no_news = new String(thsms_tested);
        						}
        						else {
        							tkn_thsms_no_news = tkn_thsms_no_news+"`"+thsms_tested;	
        						}
        					}
        				}
        				if(tkn_thsms_no_news==null) {
        					li.remove();
        				}
        				else {
        					li.set(npmhs+"`"+tkn_thsms_no_news);
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
    		catch (Exception e) {
    			e.printStackTrace();
    		}
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}
    	}
    	
    	return v_npmhs_smawl;
	}	
    
    public static Vector getStandarRiwayatTrlsmMhs(String npmhs,Connection con) {
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	Vector v=null;
    	ListIterator li = null; 
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			//get mata kuliah yg ada nilai tunda
			stmt = con.prepareStatement("SELECT THSMS,KDPST,NPMHS,STMHS from TRLSM where NPMHS=? order by THSMS");
			stmt.setString(1, npmhs);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					int i=1;
					String thsms = ""+rs.getString(i++);
					String kdpst = ""+rs.getString(i++);
					npmhs = ""+rs.getString(i++);
					String stmhs = ""+rs.getString(i++);
					
					li.add(thsms+"`"+kdpst+"`"+npmhs+"`"+stmhs);
				}
				while(rs.next());	
			}
    	}
		catch (Exception ex) {
			ex.printStackTrace();
		} 
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			//if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return v;
    }
    
    
    public Vector getDataLulusan(String tkn_kdpst, String tkn_thsms) {
    	Vector v=null;
    	ListIterator li = null;
    	if(!Checker.isStringNullOrEmpty(tkn_thsms)) {
    		tkn_thsms = tkn_thsms.trim();
    		while(tkn_thsms.contains(" ")) {
    			tkn_thsms = tkn_thsms.replace(" ", "");
    		}
    		
    		tkn_kdpst = "KDPSTMSMHS='"+tkn_kdpst;
    		tkn_kdpst = tkn_kdpst.replace(" ", "' or KDPSTMSMHS='");
    		tkn_kdpst = tkn_kdpst+"'";
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    			con = ds.getConnection();
    			if(tkn_thsms.contains("<")||tkn_thsms.contains(">")||tkn_thsms.contains("=")) {
    				if(tkn_thsms.length()==6) {
    					tkn_thsms = tkn_thsms.substring(0, 1)+"'"+tkn_thsms.substring(1, 6)+"'";
    					stmt=con.prepareStatement("select THSMS,KDPTIMSMHS,KDPSTMSMHS,NIMHSMSMHS,NMMHSMSMHS,SKSTT,TGLLS,TGL_WISUDA,TPLHRMSMHS,TGLHRMSMHS,NPMHSMSMHS,NIRL,TGLRE,NOSKR,JUDUL,NOIJA,TGL_TERBIT_IJA  from CIVITAS left join TRLSM on NPMHSMSMHS=NPMHS where STMHS='L' and ("+tkn_kdpst+") and THSMS"+tkn_thsms);
    					//System.out.println("select THSMS,KDPTIMSMHS,KDPSTMSMHS,NIMHSMSMHS,NMMHSMSMHS,SKSTT,TGLRE,TGL_WISUDA,TPLHRMSMHS,TGLHRMSMHS,NPMHSMSMHS,NIRL  from CIVITAS left join TRLSM on NPMHSMSMHS=NPMHS where STMHS='L' and ("+tkn_kdpst+") and THSMS"+tkn_thsms);
    				}
    				else if(tkn_thsms.length()==7) {
    					tkn_thsms = tkn_thsms.substring(0, 2)+"'"+tkn_thsms.substring(2, 7)+"'";
    					stmt=con.prepareStatement("select THSMS,KDPTIMSMHS,KDPSTMSMHS,NIMHSMSMHS,NMMHSMSMHS,SKSTT,TGLLS,TGL_WISUDA,TPLHRMSMHS,TGLHRMSMHS,NPMHSMSMHS,NIRL,TGLRE,NOSKR,JUDUL,NOIJA,TGL_TERBIT_IJA  from CIVITAS left join TRLSM on NPMHSMSMHS=NPMHS where STMHS='L' and ("+tkn_kdpst+") and THSMS"+tkn_thsms);
    					//System.out.println("select THSMS,KDPTIMSMHS,KDPSTMSMHS,NIMHSMSMHS,NMMHSMSMHS,SKSTT,TGLRE,TGL_WISUDA,TPLHRMSMHS,TGLHRMSMHS,NPMHSMSMHS,NIRL  from CIVITAS left join TRLSM on NPMHSMSMHS=NPMHS where STMHS='L' and ("+tkn_kdpst+") and THSMS"+tkn_thsms);
    				}
    				else if(tkn_thsms.length()>7) {
    					String list_tkn_thsms = Tool.returnTokenThsms(tkn_thsms,null);
    					list_tkn_thsms = "THSMS='"+list_tkn_thsms;
    					list_tkn_thsms = list_tkn_thsms.replace(" ", "' or THSMS='");
    					list_tkn_thsms = list_tkn_thsms+"'";
    					stmt=con.prepareStatement("select THSMS,KDPTIMSMHS,KDPSTMSMHS,NIMHSMSMHS,NMMHSMSMHS,SKSTT,TGLLS,TGL_WISUDA,TPLHRMSMHS,TGLHRMSMHS,NPMHSMSMHS,NIRL,TGLRE,NOSKR,JUDUL,NOIJA,TGL_TERBIT_IJA  from CIVITAS left join TRLSM on NPMHSMSMHS=NPMHS where STMHS='L' and ("+tkn_kdpst+") and ("+list_tkn_thsms+")");
    					//System.out.println("select THSMS,KDPTIMSMHS,KDPSTMSMHS,NIMHSMSMHS,NMMHSMSMHS,SKSTT,TGLRE,TGL_WISUDA,TPLHRMSMHS,TGLHRMSMHS,NPMHSMSMHS,NIRL  from CIVITAS left join TRLSM on NPMHSMSMHS=NPMHS where STMHS='L' and ("+tkn_kdpst+") and ("+list_tkn_thsms+")");
    				}
    			}
    			else {
    				String seperator = Checker.getSeperatorYgDigunakan(tkn_thsms);
    				StringTokenizer st = new StringTokenizer(tkn_thsms,seperator);
    				String list_tkn_thsms = null;
    				while(st.hasMoreTokens()) {
    					String token = st.nextToken();
    					if(list_tkn_thsms==null) {
    						list_tkn_thsms = new String(token);
    					}
    					else {
    						list_tkn_thsms = list_tkn_thsms+" "+token;
    					}	
    				}
    				list_tkn_thsms = "THSMS='"+list_tkn_thsms;
					list_tkn_thsms = list_tkn_thsms.replace(" ", "' or THSMS='");
					list_tkn_thsms = list_tkn_thsms+"'";
					stmt=con.prepareStatement("select THSMS,KDPTIMSMHS,KDPSTMSMHS,NIMHSMSMHS,NMMHSMSMHS,SKSTT,TGLLS,TGL_WISUDA,TPLHRMSMHS,TGLHRMSMHS,NPMHSMSMHS,NIRL,TGLRE,NOSKR,JUDUL,NOIJA,TGL_TERBIT_IJA  from CIVITAS left join TRLSM on NPMHSMSMHS=NPMHS where STMHS='L' and ("+tkn_kdpst+") and ("+list_tkn_thsms+")");
					//System.out.println("select THSMS,KDPTIMSMHS,KDPSTMSMHS,NIMHSMSMHS,NMMHSMSMHS,SKSTT,TGLRE,TGL_WISUDA,TPLHRMSMHS,TGLHRMSMHS,NPMHSMSMHS,NIRL  from CIVITAS left join TRLSM on NPMHSMSMHS=NPMHS where STMHS='L' and ("+tkn_kdpst+") and ("+list_tkn_thsms+")");
    			}
    			rs= stmt.executeQuery();
    			if(rs.next()) {
    				String nlipk="0";
    				v = new Vector();
    				li = v.listIterator();
    				do {
    					String thsms = rs.getString(1);
        				String kdpti = rs.getString(2);
        				String kdpst = rs.getString(3);
        				String nimhs = rs.getString(4);
        				String nmmhs = rs.getString(5);
        				String skstt = rs.getString(6);
        				String tglls = rs.getString(7);
        				if(!Checker.isStringNullOrEmpty(tglls)) {
        					tglls = Converter.formatDdSlashMmSlashYy(tglls);
        				}
        				String tglwis = rs.getString(8);
        				if(!Checker.isStringNullOrEmpty(tglwis)) {
        					tglwis = Converter.formatDdSlashMmSlashYy(tglwis);
        				}
        				String tplhr = rs.getString(9);
        				String tglhr = rs.getString(10);
        				if(!Checker.isStringNullOrEmpty(tglhr)) {
        					tglhr = Converter.formatDdSlashMmSlashYy(tglhr);
        				}
        				String npmhs = rs.getString(11);
        				String nirl = rs.getString(12);
        				String tglskr = rs.getString(13);
        				if(!Checker.isStringNullOrEmpty(tglskr)) {
        					tglls = Converter.formatDdSlashMmSlashYy(tglskr);
        				}
        				String noskr = rs.getString(14);
        				String judul = rs.getString(15);
        				String noija = rs.getString(16);
        				String tgl_cetak = rs.getString(17);
        				
        				li.add(thsms+"`"+kdpti+"`"+kdpst+"`"+nimhs+"`"+nmmhs+"`"+skstt+"`"+nlipk+"`"+tglls+"`"+tglwis+"`"+tplhr+"`"+tglhr+"`"+npmhs+"`"+nirl+"`"+tglskr+"`"+noskr+"`"+judul+"`"+noija+"`"+tgl_cetak);
    				}
    				while(rs.next());
    				//get skstttrakm
    				stmt = con.prepareStatement("select SKSTTTRAKM,NLIPKTRAKM from TRAKM where NPMHSTRAKM=? and THSMSTRAKM=?");
    				li = v.listIterator();
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String thsms=st.nextToken();
    					String kdpti=st.nextToken();
    					String kdpst=st.nextToken();
    					String nimhs=st.nextToken();
    					String nmmhs=st.nextToken();
    					String skstt=st.nextToken();
    					nlipk=st.nextToken();
    					String tglls=st.nextToken();
    					String tglwis=st.nextToken();
    					String tplhr=st.nextToken();
    					String tglhr=st.nextToken();
    					String npmhs=st.nextToken();
    					String nirl=st.nextToken();
    					String tglskr=st.nextToken();
    					String noskr=st.nextToken();
    					String judul=st.nextToken();
    					String noija=st.nextToken();
    					String tgl_cetak=st.nextToken();
    					stmt.setString(1, npmhs);
    					stmt.setString(2, thsms);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						skstt = rs.getString(1);
    						nlipk = rs.getString(2);
    						try {
    							nlipk= ""+(Math.round(Double.parseDouble(nlipk) * 100.0) / 100.0);
    						}
    						catch(Exception e) {
    							nlipk="0.0";
    						}
    						
    						li.set(thsms+"`"+kdpti+"`"+kdpst+"`"+nimhs+"`"+nmmhs+"`"+skstt+"`"+nlipk+"`"+tglls+"`"+tglwis+"`"+tplhr+"`"+tglhr+"`"+npmhs+"`"+nirl+"`"+tglskr+"`"+noskr+"`"+judul+"`"+noija+"`"+tgl_cetak);
    					}
    					//System.out.println("baris = "+brs);
    				}
    				//update skstt & nlipk pada TRLSM
    				stmt = con.prepareStatement("update TRLSM set SKSTT=?,NLIPK=? where NPMHS=? and THSMS=? and STMHS='L'");
    				li = v.listIterator();
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String thsms=st.nextToken();
    					String kdpti=st.nextToken();
    					String kdpst=st.nextToken();
    					String nimhs=st.nextToken();
    					String nmmhs=st.nextToken();
    					String skstt=st.nextToken();
    					nlipk=st.nextToken();
    					String tglls=st.nextToken();
    					String tglwis=st.nextToken();
    					String tplhr=st.nextToken();
    					String tglhr=st.nextToken();
    					String npmhs=st.nextToken();
    					String nirl=st.nextToken();
    					String tglskr=st.nextToken();
    					String noskr=st.nextToken();
    					String judul=st.nextToken();
    					String noija=st.nextToken();
    					String tgl_cetak=st.nextToken();
    					try {
    						stmt.setInt(1,Integer.parseInt(skstt));
    					}
    					catch(Exception e) {
    						stmt.setInt(1,0);
    					}
    					try {
    						stmt.setDouble(2,Double.parseDouble(nlipk));
    					}
    					catch(Exception e) {
    						stmt.setDouble(2,0);
    					}
    					stmt.setString(3, npmhs);
    					stmt.setString(4, thsms);
    					stmt.executeUpdate();
    				}	
    			}
    			
    			
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
    	
    	return v;
    }
    
    
    public Vector getListNpmMhsCutiDanNonAktif(String thsms) {
    	Vector v=null;
    	ListIterator li = null;
    	if(!Checker.isStringNullOrEmpty(thsms)) {
    		
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select distinct NPMHS from TRLSM where THSMS=? and (STMHS='C' or STMHS='N')");
    			stmt.setString(1, thsms);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v = new Vector();
    				li = v.listIterator();
    				do {
    					String npmhs = ""+rs.getString(1);
    					li.add(npmhs);
    				}	
    				while(rs.next());
    			}
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
    	
    	return v;
    }
    
    public Vector getListNpmMhsCutiDanNonAktif(String thsms, String kdpst) {
    	//kalo kdpst null = semua = getListNpmMhsCutiDanNonAktif(String thsms)
    	Vector v=null;
    	ListIterator li = null;
    	if(!Checker.isStringNullOrEmpty(thsms)) {
    		if(Checker.isStringNullOrEmpty(kdpst)) {
    			v = getListNpmMhsCutiDanNonAktif(thsms);
    		}
    		else {
    			try {
        			Context initContext  = new InitialContext();
        			Context envContext  = (Context)initContext.lookup("java:/comp/env");
        			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        			con = ds.getConnection();
        			stmt = con.prepareStatement("select distinct NPMHS from TRLSM where THSMS=? and KDPST=? and (STMHS='C' or STMHS='N')");
        			stmt.setString(1, thsms);
        			stmt.setString(2, kdpst);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				v = new Vector();
        				li = v.listIterator();
        				do {
        					String npmhs = ""+rs.getString(1);
        					li.add(npmhs);
        				}	
        				while(rs.next());
        			}
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
    		
    	}
    	
    	return v;
    }
}

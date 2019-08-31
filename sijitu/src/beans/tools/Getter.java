package beans.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import beans.setting.Constants;
import beans.tools.filter.FilterKampus;
//import servlets.Get.PengajuanAu.$missing$;

/**
 * Session Bean implementation class Getter
 */
@Stateless
@LocalBean
public class Getter {

    /**
     * Default constructor. 
     */
    public Getter() {
        // TODO Auto-generated constructor stub
    }
    
    public static Vector get_v_scope_id_keseluruhan() {
    	Vector v=null;   
    	ListIterator li=null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT distinct KODE_KAMPUS_DOMISILI from OBJECT where OBJ_NAME='MHS'");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			li.add(rs.getString(1));
    			while(rs.next()) {
    				li.add(rs.getString(1));
    			}
    		}
    		if(v!=null && v.size()>0) {
    			stmt = con.prepareStatement("select ID_OBJ from OBJECT where KODE_KAMPUS_DOMISILI=? and OBJ_NAME='MHS' order by ID_OBJ");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String tkn_id = null;
    				String kmp=(String)li.next();
    				stmt.setString(1, kmp);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					String id = ""+rs.getInt(1);
    					tkn_id = new String(id);
    					while(rs.next()) {
    						id = ""+rs.getInt(1);
    						tkn_id = tkn_id+"`"+id;
    					}
    				}
    				li.set(kmp+"`"+tkn_id);
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
    
    public static String getNamaTipeStandar(String id_master, String id_tipe_std) {
    	String getNamaTipeStandar = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	boolean valid = true;
    	try {
    		Integer.parseInt(id_master);
    		Integer.parseInt(id_tipe_std);
    	}
    	catch(Exception e) {
    		valid = false;
    	}
    	try {
    		if(valid) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//cretae NPM auto increment
        		stmt = con.prepareStatement("SELECT KET_TIPE_STD FROM STANDARD_TABLE where ID_MASTER_STD=? and ID_TIPE_STD=?");
        		stmt.setInt(1, Integer.parseInt(id_master));
        		stmt.setInt(2, Integer.parseInt(id_tipe_std));
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			getNamaTipeStandar = rs.getString(1);
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
    	return getNamaTipeStandar;
    }
    
    public static Vector getListJabatanStruktural() {
    	Vector v=null;   
    	ListIterator li=null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		/*
    		 * NAMA JOB == ALIAS (memang redundant)
    		 */
    		//stmt = con.prepareStatement("SELECT distinct NM_JOB,ALIAS_NM_JOB from STRUKTURAL order by ALIAS_NM_JOB");
    		stmt = con.prepareStatement("SELECT distinct NAMA_JABATAN,SINGKATAN from JABATAN order by NAMA_JABATAN");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				String nm_job = rs.getString(1);
    				String alias = new String(nm_job);
    				String singkatan =	rs.getString(2);
    				String tmp = nm_job+"~"+alias+"~"+singkatan;
    				tmp = Tool.cleanAndTrimToken(tmp, "~");
    				li.add(tmp);
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
        finally {
        	if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
        }	
    	return v;
    }
    
    public static Vector getListDokumenMutu() {
    	Vector v=null;   
    	ListIterator li=null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT NAMA_DOKUMEN from STANDARD_DOKUMEN order by NAMA_DOKUMEN");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				String nm_doc = rs.getString(1);
    				String tmp = nm_doc;
    				li.add(tmp);
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
        finally {
        	if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
        }	
    	return v;
    }
    
    
    public static int getIdJabatanTerakhir() {
    	int id = 0;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT ID_JAB  from JABATAN order by ID_JAB desc limit 1");
    		rs = stmt.executeQuery();
    		rs.next();
    		id = rs.getInt(1);
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
    	return id;
    }

    
    public static Vector getMetodePelaksanaanKuliah() {
    	Vector v = null;
    	ListIterator li = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT METODE from METODE_PELAKSANAAN_KULIAH order by ID");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String metod = rs.getString(1);
    			if(v==null) {
    				v =new Vector();
    				li = v.listIterator();
    			}
    			li.add(metod);
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

    
    public static String getListPersonalFolder() {
    	String list_folder = "";;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT VALUE FROM CONSTANT where KETERANGAN=?");
    		stmt.setString(1,"FOLDER_FILE_MHS");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			list_folder = rs.getString("VALUE");
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
    	return list_folder;
    }
    
    
    public static Vector getListNpmhsMalaikatKrsBerdasarkanAktifSmsLalu(String target_thsms, Vector v_scope_id) {
    	ListIterator li = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector v_scope_kdpst=null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		
        		v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
            	li = v_scope_kdpst.listIterator();
                while(li.hasNext()) {
                	String sql_cmd = null;
                    String info_scope = (String)li.next();
                    String list_npm = null; //per kampus soalnya
                    StringTokenizer st = new StringTokenizer(info_scope,"`");
                    if(st.countTokens()>1) {
                    	sql_cmd = new String("");

                        String kdkmp=st.nextToken();
                        while(st.hasMoreTokens()) {
                        	String kdpst = st.nextToken();
                            sql_cmd = sql_cmd+"KDPSTTRNLM='"+kdpst+"'";
                            if(st.hasMoreTokens()) {
                            	sql_cmd = sql_cmd+" or ";
                            }
                        }
                        
                        if(!Checker.isStringNullOrEmpty(sql_cmd)) {
                        	//sql_cmd = new String("select distinct KDPST,NPMHS,NMMHSMSMHS,SMAWLMSMHS,ALL_APPROVED from DAFTAR_ULANG inner join CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and ALL_APPROVED=? and ("+sql_cmd+") order by KDPST,ALL_APPROVED,NPMHS");
                        	sql_cmd = new String("select distinct KDPSTTRNLM,NPMHSTRNLM,NMMHSMSMHS,SMAWLMSMHS from TRNLM A inner join CIVITAS B on NPMHSTRNLM=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ where THSMSTRNLM=? and ("+sql_cmd+") and MALAIKAT=? and KODE_KAMPUS_DOMISILI=? order by KDPSTTRNLM,NPMHSTRNLM");
                    		stmt = con.prepareStatement(sql_cmd);
                    		stmt.setString(1, target_thsms);
                    		stmt.setBoolean(2, true);
                    		stmt.setString(3, kdkmp);
                    		rs = stmt.executeQuery();
                    		while(rs.next()) {
                        		
                        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
                        		String kdpst = ""+rs.getString(1);
                        		String npmhs = ""+rs.getString(2);
                        		String nmmhs = ""+rs.getString(3);
                        		String smawl = ""+rs.getString(4);
                        		//String approved = ""+rs.getBoolean(5);
                        		if(list_npm==null) {
                        			//list_npm=new String(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved);
                        			list_npm=new String(kdpst+","+npmhs+","+nmmhs+","+smawl);
                        		}
                        		else {
                        			//list_npm=list_npm+"`"+kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved;
                        			list_npm=list_npm+","+kdpst+","+npmhs+","+nmmhs+","+smawl;
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
    
    public static String getListTipeSarpras() {
    	String dlist = null;;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * from SARPRAS_TIPE");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			dlist = new String(rs.getString(1));
    			while(rs.next()) {
    				dlist = dlist+"`"+ rs.getString(1);
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
    	return dlist;
    }
    
    public static Vector getListDetilInfoSarpras() {
    	Vector v_list = null;;
    	ListIterator liv = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT ID,TIPE_SARPRAS,SUBTIPE_SARPRAS,KODE_SARPRAS,NAMA_SARPRAS,DIMENSI,LUAS,LOKASI_KDKMP,LOKASI_GEDUNG,LOKASI_LANTAI from SARPRAS order by NAMA_SARPRAS");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			v_list = new Vector();
    			liv = v_list.listIterator();
    			
    			String id = ""+rs.getInt(1);
    			String tipe_sar = ""+rs.getString(2);
    			String sub_tipe_sar = ""+rs.getString(3);
    			String kode_sar = ""+rs.getString(4);
    			String nama_sar = ""+rs.getString(5);
    			String dimensi = ""+rs.getString(6);
    			String luas = ""+rs.getDouble(7);
    			String kdkmp = ""+rs.getString(8);
    			String gedung = ""+rs.getString(9);
    			String lantai = ""+rs.getString(10);
    			String brs = id+"`"+tipe_sar+"`"+sub_tipe_sar+"`"+kode_sar+"`"+nama_sar+"`"+dimensi+"`"+luas+"`"+kdkmp+"`"+gedung+"`"+lantai;
    			liv.add(brs);
    			while(rs.next()) {
    				id = ""+rs.getInt(1);
        			tipe_sar = ""+rs.getString(2);
        			sub_tipe_sar = ""+rs.getString(3);
        			kode_sar = ""+rs.getString(4);
        			nama_sar = ""+rs.getString(5);
        			dimensi = ""+rs.getString(6);
        			luas = ""+rs.getDouble(7);
        			kdkmp = ""+rs.getString(8);
        			gedung = ""+rs.getString(9);
        			lantai = ""+rs.getString(10);
        			brs = id+"`"+tipe_sar+"`"+sub_tipe_sar+"`"+kode_sar+"`"+nama_sar+"`"+dimensi+"`"+luas+"`"+kdkmp+"`"+gedung+"`"+lantai;
        			liv.add(brs);
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
    	return v_list;
    }
    
    public String getPathFolderFotoMhs(String npmhs) {
    	String path = null;;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		//Context initContext  = new InitialContext();
    		//Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		//ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		//con = ds.getConnection();
    		String root_path_folder_mhs = Constant.getVelueFromConstantTable("ROOT_PATH_FOLDER_MHS");
    		String path_folder_foto_mhs = Constant.getVelueFromConstantTable("PATH_FOLDER_POTO_MHS");
    		path_folder_foto_mhs = path_folder_foto_mhs.replace("ROOT_PATH_FOLDER_MHS", root_path_folder_mhs);
    		path_folder_foto_mhs = path_folder_foto_mhs.replace("npmhs", npmhs);
    		path = new String(path_folder_foto_mhs);
    	}
        catch (Exception ex) {
        	ex.printStackTrace();
        } 
        //finally {
        //	if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		 //   if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		 //   if (con!=null) try { con.close();} catch (Exception ignore){}
        //}	
    	return path;
    }
    

    /*
     * DEPRECATED pake V1 yg ada nilai min dan max
     */
    public static String getAngkaPenilaian(String thsms, String kdpst) {
    	String list_nilai = null;;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	/*
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and ACTIVE=true");
    		stmt.setString(1,thsms);
    		stmt.setString(2,kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			
    			do {
    				String nilai = rs.getString(1);
    				String bobot = ""+rs.getInt(2);
    				if(list_nilai==null) {
    					list_nilai = new String(nilai+"`"+bobot);
    				}
    				else {
    					list_nilai = list_nilai+"`"+nilai+"`"+bobot;
    				}
    				
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
        finally {
        	if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
        }
        */	
    	Vector v = getAngkaPenilaian_v1(thsms, kdpst);
    	ListIterator li = v.listIterator();
    	while(li.hasNext()) {
    		String brs = (String)li.next();
    		StringTokenizer st = new StringTokenizer(brs,"`");
    		String nilai = st.nextToken();
    		String bobot = st.nextToken();
    		if(list_nilai==null) {
				list_nilai = new String(nilai+"`"+bobot);
			}
			else {
				list_nilai = list_nilai+"`"+nilai+"`"+bobot;
			}
    	}
    	return list_nilai;
    }
    
    public static Vector getAngkaPenilaian_v1(String thsms, String kdpst) {
    	Vector v= null;;
    	ListIterator li = null;
    	
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL,NILAI_MIN,NILAI_MAX from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and ACTIVE=true");
    		stmt.setString(1,thsms);
    		stmt.setString(2,kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			if(v==null) {
					v = new Vector();
					li = v.listIterator();
				}
    			do {
    				String nilai_huruf = rs.getString(1);
    				String bobot = ""+rs.getDouble(2);
    				String min = ""+rs.getDouble(3);
    				String max = ""+rs.getDouble(4);
    				
    				li.add(nilai_huruf+"`"+bobot+"`"+min+"`"+max);
    			}
    			while(rs.next());
    		}
    		else {
    			//ambil dari thsms terakhir/terkini
    			stmt = con.prepareStatement("SELECT THSMSTBBNL from TBBNL where KDPSTTBBNL=? and ACTIVE=true order by THSMSTBBNL desc limit 1");
    			stmt.setString(1,kdpst);
        		rs = stmt.executeQuery();
        		rs.next();
        		thsms = rs.getString(1);
        		stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL,NILAI_MIN,NILAI_MAX from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and ACTIVE=true");
        		stmt.setString(1,thsms);
        		stmt.setString(2,kdpst);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			if(v==null) {
    					v = new Vector();
    					li = v.listIterator();
    				}
        			do {
        				String nilai_huruf = rs.getString(1);
        				String bobot = ""+rs.getDouble(2);
        				String min = ""+rs.getDouble(3);
        				String max = ""+rs.getDouble(4);
        				
        				li.add(nilai_huruf+"`"+bobot+"`"+min+"`"+max);
        			}
        			while(rs.next());
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
    
    public static Vector getListAgama() {
    	Vector v_agama = null;;
    	ListIterator li = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from agama");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			if(v_agama==null) {
    				v_agama = new Vector();
    				li = v_agama.listIterator();
    			}
    			long id = rs.getLong("id_agama");
    			String nm = rs.getString("nm_agama");
    			li.add(id+"`"+nm);
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
    	return v_agama;
    }
    
    public static String getKdpstKdjenTamu(int default_objid_tamu) {
    	String info = null;;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT KDPSTMSPST,KDJENMSPST from MSPST inner join OBJECT on KDPSTMSPST=KDPST where ID_OBJ=?");
    		stmt.setInt(1, default_objid_tamu);
    		rs = stmt.executeQuery();
    		rs.next();
    		String kdpst = rs.getString(1);
    		String kdjen = rs.getString(2);
    		
    		info = kdpst+"`"+kdjen;
    		
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
    	return info;
    }
    
    public static String getKodeProdiDanKampus(int objid) {
    	String info = null;;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT KDPST,KODE_KAMPUS_DOMISILI from OBJECT where ID_OBJ=?");
    		stmt.setInt(1,objid);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			info = rs.getString(1)+"`"+rs.getString(2);
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
    	return info;
    }  
    
    /*
    public static String getIdWilKecamatan(String nm_kecamatan) {
    	//System.out.println("nm_kecamatan="+nm_kecamatan);
    	String info = null;;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT id_wil from wilayah where nm_wil like ?");
    		stmt.setString(1,"%"+nm_kecamatan+"%");
    		rs = stmt.executeQuery();
    		rs.next();
    		info = rs.getString(1).trim();
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
    	return info;
    }  
    */
    
    public static String mergeVerificator(Vector verificator) {
    	ListIterator li = verificator.listIterator();
    	Vector v_tmp = new Vector();
    	ListIterator lit = v_tmp.listIterator();
    	StringTokenizer st = null;
    	String merge = null;
    	//maksimum verificator 2 seperti kasus pindah prodi
    	boolean first = true;
    	while(li.hasNext()) {
    		String tmp = "";
    		if(first) {
    			//pertama = input semua ke dlm vector
    			first = false;
    			tmp = (String)li.next();
    			
    			//System.out.println("set1 ="+tmp);
    			tmp = tmp.replace("][", "$" );
    			tmp = tmp.replace("[", "" );
    			tmp = tmp.replace("]", "" );
    			st = new StringTokenizer(tmp,"$");
    			while(st.hasMoreTokens()) {
    				lit.add(st.nextToken());
    			}
    		}
    		else {
    			//ke dua dst , kompare                       
    			tmp = (String)li.next();
    			
    			//System.out.println("set2 ="+tmp);
    			tmp = tmp.replace("][", "$" );
    			tmp = tmp.replace("[", "" );
    			tmp = tmp.replace("]", "" );
    			st = new StringTokenizer(tmp,"$");
    			while(st.hasMoreTokens()) {
    				String tkn = st.nextToken();//value yg mo dicompare 
    				
        			//cek apa udah ada di vector;
        			lit = v_tmp.listIterator();
        			boolean match = false;
        			while(lit.hasNext() && !match) {
        				String baris = (String)lit.next();
        				baris = baris+"`"; //ditutup pake kutip untuk comparasi di bawah 
        				StringTokenizer st1 = new StringTokenizer(tkn,"`");
        				String jabatan = st1.nextToken();
        				if(baris.startsWith(jabatan+"`")) {
        					match = true;
        					//jabatan cocok sekarang cek apa objid sudah ada
        					//boolean ada = false;
        					while(st1.hasMoreTokens()) {
        						String objid = st1.nextToken();
        						if(!baris.contains("`"+objid+"`")) {
        							
        							baris = baris + objid+"`";
        							
        						}
        					}
        					lit.set(baris); //update data
        				}
        				
        			}
        			if(!match) {
        				//insert berarti verificator baru
        				lit.add(tkn);
        			}
        			
    			}
    			
    		}
    		
    		
    	}
    	lit = v_tmp.listIterator();
    	if(lit.hasNext()) {
    		merge = new String("[");
    		do {
    			String tmp = (String)lit.next();
    			if(tmp.endsWith("`")) {
    				tmp = tmp.substring(0, tmp.length()-1);
    			}
    			merge = merge+tmp+"]";
    			if(lit.hasNext()) {
    				merge = merge + "[";
    			}
    		}
    		while(lit.hasNext());
    		//System.out.println((String)lit.next());
    	}
    	return merge;
    }
    
    public static Vector getVerificatorFromTableRule(String full_rule_table_name, String target_thsms, String kdpst_origin, String kdpst_target, String kmp_origin, String kmp_target) {
    	/*
    	 * MAKSIMUM VERIVIKATOR ADA 2 SPT PADA PINDAH PRODI DIMANA 2 PRODI TERKAIT
    	 * KALO UMUMNYA 1 AJA
    	 */
    	//System.out.println("kdpst_orti vs target = "+kdpst_origin+" = "+kdpst_target);
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get Info from origin
    		stmt = con.prepareStatement("SELECT * from "+full_rule_table_name+" where THSMS=? and KDPST=? and KODE_KAMPUS=?");
    		stmt.setString(1,target_thsms);
    		stmt.setString(2,kdpst_origin);
    		stmt.setString(3,kmp_origin);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String tkn_jabatan_verificator = rs.getString("TKN_JABATAN_VERIFICATOR");
    			li.add(tkn_jabatan_verificator);
    		}
    		else {
    			li.add("[null]");
    		}
    		
    		//get Info from target - kasus spt pindah prodi
    		if(kdpst_target!=null && !Checker.isStringNullOrEmpty(kdpst_target)) {
    			stmt = con.prepareStatement("SELECT * from "+full_rule_table_name+" where THSMS=? and KDPST=? and KODE_KAMPUS=?");
        		stmt.setString(1,target_thsms);
        		stmt.setString(2,kdpst_target);
        		stmt.setString(3,kmp_target);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			String tkn_jabatan_verificator = rs.getString("TKN_JABATAN_VERIFICATOR");
        			li.add(tkn_jabatan_verificator);
        		}
        		else {
        			li.add("[null]");
        		}	
    		}
    		
    		
    		
    		//get obj id from tabel struktural &  jabatan
    		if(v.size()>0){
    			//stmt = con.prepareStatement("select * from JABATAN inner join STRUKTURAL on NAMA_JABATAN=NM_JOB where JABATAN.AKTIF=? and STRUKTURAL.AKTIF=? and (NAMA_JABATAN=? or SINGKATAN=?) and KDPST=?");
    			stmt = con.prepareStatement("select distinct OBJID from JABATAN inner join STRUKTURAL on NAMA_JABATAN=NM_JOB where JABATAN.AKTIF=? and STRUKTURAL.AKTIF=? and (NAMA_JABATAN=? or SINGKATAN=?) and KDPST=?");
        		li = v.listIterator();
        	    //first origin
        		String origin = (String)li.next();
        		String nu_origin_value="";
        		origin = origin.replace("][", "`");
        		origin = origin.replace("]", "");
        		origin = origin.replace("[", "");
        		//System.out.println("origin = "+origin);
        		StringTokenizer st = new StringTokenizer(origin,"`");
        		while(st.hasMoreTokens()) {
        			String singkatan_jabatan = st.nextToken();
        	    	stmt.setBoolean(1, true);
        	    	stmt.setBoolean(2, true);
        	        stmt.setString(3, singkatan_jabatan);
        	        stmt.setString(4, singkatan_jabatan);
        	        stmt.setString(5, kdpst_origin);
        	        rs = stmt.executeQuery();
        	        if(rs.next()) {
        	        	String list_objid = "";
        	        	do {
        	        		long objid = rs.getLong("OBJID");
        	        		list_objid = list_objid+"`"+objid;
        	        	}
        	        	while(rs.next());
        	        	nu_origin_value = nu_origin_value+"["+singkatan_jabatan+list_objid+"]";
        	        	//li.set("["+singkatan_jabatan+list_objid+"]");
        	        }
        	        else {
        	        	nu_origin_value = nu_origin_value+"[null]";
        	        	//li.set("["+singkatan_jabatan+"`null]");
        	        }
        		}
        		li.set(nu_origin_value);
        		//System.out.println("(nu_origin_value);="+nu_origin_value);
        		if(li.hasNext()) {
        			//2nd target - spt kasus pindah prodi
            		//System.out.println("dua");
            		String target = (String)li.next();
            		String nu_target_value="";
            		target = target.replace("][", "`");
            		target = target.replace("]", "");
            		target = target.replace("[", "");
            		//System.out.println("target = "+target);
            		st = new StringTokenizer(target,"`");
            		while(st.hasMoreTokens()) {
            			String singkatan_jabatan = st.nextToken();
            	    	stmt.setBoolean(1, true);
            	    	stmt.setBoolean(2, true);
            	        stmt.setString(3, singkatan_jabatan);
            	        stmt.setString(4, singkatan_jabatan);
            	        stmt.setString(5, kdpst_target);
            	        rs = stmt.executeQuery();
            	        if(rs.next()) {
            	        	String list_objid = "";
            	        	do {
            	        		long objid = rs.getLong("OBJID");
            	        		list_objid = list_objid+"`"+objid;
            	        	}
            	        	while(rs.next());
            	        	nu_target_value = nu_target_value+"["+singkatan_jabatan+list_objid+"]";
            	        	//li.set("["+singkatan_jabatan+list_objid+"]");
            	        }
            	        else {
            	        	nu_target_value = nu_target_value+"[null]";
            	        	li.set("["+singkatan_jabatan+"`null]");
            	        }
            		}
            		li.set(nu_target_value);
            		//System.out.println("(nu_target_value);="+nu_target_value);
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
    
    public static String getDefaultNpmNamaBerdasarkanJabatan(String thsms, String kdpst, String jabatan) {
    	/*
    	 * DEAFAULT = 1 orang outputnya
    	 */
    	String npm_nmm_sinkatan = null;;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from JABATAN where NAMA_JABATAN=?");
    		stmt.setString(1, jabatan);
    		rs = stmt.executeQuery();
    		rs.next();
    		String singkatan = rs.getString("SINGKATAN");
    		//cretae NPM auto increment
    		//stmt = con.prepareStatement("SELECT JABATAN.SINGKATAN,STRUKTUR_ORG.NPM,CIVITAS.NMMHSMSMHS from CIVITAS inner join STRUKTUR_ORG on NPM=NPMHSMSMHS where THSMS=? and KDPST=? and NAMA_JABATAN=? and DEFAULT=?");
    		stmt = con.prepareStatement("SELECT NPM,NMMHSMSMHS from STRUKTUR_ORG inner join CIVITAS on NPM=NPMHSMSMHS where THSMS=? and KDPST=? and NAMA_JABATAN=? and DEFAULT_VALUE=?");
    		stmt.setString(1,thsms);
    		stmt.setString(2,kdpst);
    		stmt.setString(3,jabatan);
    		stmt.setBoolean(4,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			npm_nmm_sinkatan = new String(rs.getString("NPM")+"`"+rs.getString("NMMHSMSMHS")+"`"+singkatan);
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
    	return npm_nmm_sinkatan;
    }
    
    public static String getDefaultNpmNamaBerdasarkanJabatan(String kdpst, String jabatan) {
    	/*
    	 * DEAFAULT = 1 orang outputnya
    	 */
    	String npm_nmm_sinkatan = null;;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select SINGKATAN,OBJID from JABATAN inner join STRUKTURAL on NAMA_JABATAN=NM_JOB where NAMA_JABATAN=? and KDPST=?");
    		stmt.setString(1, jabatan);
    		stmt.setString(2, kdpst);
    		rs = stmt.executeQuery();
    		rs.next();
    		String singkatan = rs.getString("SINGKATAN");
    		long objid = rs.getLong("OBJID");
    		stmt = con.prepareStatement("select NPMHSMSMHS,NMMHSMSMHS from CIVITAS where ID_OBJ=?");
    		stmt.setLong(1, objid);
    		rs =stmt.executeQuery();
    		rs.next();
    		String npmhs = rs.getString(1); 
    		String nmmhs = rs.getString(2); 
    		npm_nmm_sinkatan = npmhs+"`"+nmmhs+"`"+singkatan;
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
    	return npm_nmm_sinkatan;
    }
    
    public static String getMasterTampletAbsenPath() {
    	String path = "";;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT VALUE FROM CONSTANT where KETERANGAN=?");
    		stmt.setString(1,"FOLDER_MASTER_ABSEN");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			path = rs.getString("VALUE");
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
    	return path;
    }
    
    public static String getMasterTampletAbsenUtsPath() {
    	String path = "";;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT VALUE FROM CONSTANT where KETERANGAN=?");
    		stmt.setString(1,"FOLDER_MASTER_ABSEN_UTS");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			path = rs.getString("VALUE");
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
    	return path;
    }
    
    public static String getMasterTampletAbsenUasPath() {
    	String path = "";;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT VALUE FROM CONSTANT where KETERANGAN=?");
    		stmt.setString(1,"FOLDER_MASTER_ABSEN_UAS");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			path = rs.getString("VALUE");
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
    	return path;
    }
    
    public static String getTmpFolderPath() {
    	String path = "";;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT VALUE FROM CONSTANT where KETERANGAN=?");
    		stmt.setString(1,"FOLDER_TMP");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			path = rs.getString("VALUE");
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
    	return path;
    }
    
    public static String getListHiddenFolder() {
    	String list_folder = "";;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT VALUE FROM CONSTANT where KETERANGAN=?");
    		stmt.setString(1,"HIDDEN_FOLDER");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			list_folder = rs.getString("VALUE");
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
    	return list_folder;
    }
    
    public static String getKodeKonversiShift(String shift_keter_value) {
    	String kode="N/A";     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT KODE_KONVERSI FROM SHIFT where KETERANGAN=?");
    		stmt.setString(1,shift_keter_value);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			kode = rs.getString("KODE_KONVERSI");
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
    	return kode;
    }
    
    public static String[] getVisiMisiTujuan(String kdpst) {
    	String []info=new String[3];     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT VISI,MISI,TUJUAN from VISI_MISI where KDPST=?");
    		stmt.setString(1,kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			info[0] = ""+rs.getString("VISI");
    			info[1] = ""+rs.getString("MISI");
    			info[2] = ""+rs.getString("TUJUAN");
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
    	return info;
    }
    
    public static String[] getVisiMisiTujuanNilaiPt(String kdpti) {
    	String []info=new String[4];     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT VISI,MISI,TUJUAN,NILAI from VISI_MISI_PT where KDPTI=? and AKTIF=?");
    		stmt.setString(1,kdpti);
    		stmt.setBoolean(2,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			info[0] = ""+rs.getString("VISI");
    			info[1] = ""+rs.getString("MISI");
    			info[2] = ""+rs.getString("TUJUAN");
    			info[3] = ""+rs.getString("NILAI");
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
    	return info;
    }
    
    
    public static String getSmawlCivitas(String npmhs) {
    	String smawl = new String();     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT SMAWLMSMHS from CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1,npmhs);
    		rs = stmt.executeQuery();
    		rs.next();
    		smawl = rs.getString(1);
    		
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
    	return smawl;
    }
    
    
    public static String[] getTipeKartuUjuanFromConstant() {
    	String []tipe=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * from CONSTANT where KETERANGAN=?");
    		stmt.setString(1,"TIPE_KARTU_UJIAN");
    		rs = stmt.executeQuery();
    		rs.next();
    		String token_tipe = rs.getString("VALUE");
    		StringTokenizer st = new StringTokenizer(token_tipe);
    		int size = st.countTokens();
    		tipe = new String[size];
    		
    		for(int i=0;i<size;i++) {
    			tipe[i]=st.nextToken();
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
    	return tipe;
    }
    

    
    public static String currentStatusAkhirPengajuanPadaTabelPengajuanUa(Vector vRiwayatPengajuan, String rule_tkn_approvee_id, String tkn_approvee_nickname) {
    	String status_akhir = "PROSES PENGAJUAN"; //defaut value = dengan value saat pengajuan pertama kali dibuat
    	String tkn_who_missing = getSiapaYgBlumNgasihTindakanPengajuanUa(vRiwayatPengajuan, rule_tkn_approvee_id, tkn_approvee_nickname);
    	String approval_status = getStatusPengajuanUa(vRiwayatPengajuan, rule_tkn_approvee_id, tkn_approvee_nickname);
    	//System.out.println("approval_status="+approval_status);
    	StringTokenizer st1 = new StringTokenizer(tkn_who_missing,"-");
		StringTokenizer st2 = new StringTokenizer(rule_tkn_approvee_id,"-");
		if(st1.countTokens()==st2.countTokens()) {
			//belum ada yg ngaish tindakan
			//nothing change status tetap PROSES PENGAJUAN
		}
		else if(st1.countTokens()<1) {
			//semua sudh ngasih tindakan
			if(approval_status.equalsIgnoreCase("tolak")) {
				status_akhir = "Ditolak";
			}
			else if(approval_status.equalsIgnoreCase("terima")) {
				status_akhir = "Diterima";
			}
			else if(approval_status.equalsIgnoreCase("proses")) {
				status_akhir = "PROSES PENILAIAN";
			}
		}
		else if(st1.countTokens()>0 && (st1.countTokens()<st2.countTokens())) {
			//baru sebagian
			if(approval_status.equalsIgnoreCase("tolak")) {
				status_akhir = "Ditolak";
			}
			else if(approval_status.equalsIgnoreCase("terima")) {
				status_akhir = "PROSES PENILAIAN";
			}
			else if(approval_status.equalsIgnoreCase("proses")) {
				status_akhir = "PROSES PENILAIAN";
			}
			
		}
    	return status_akhir;
    }
    
    
    public static String getSiapaYgBlumNgasihTindakanPengajuanUa(Vector vRiwayatPengajuan, String rule_tkn_approvee_id, String tkn_approvee_nickname) {
    	/* 
    	 * /56/-/62/1/ rule tkn id
    	   /SEKRETARIAT PASCA/-/BIRO KEUANGAN PASCA/ADMIN/ = rule tkn nic
    	 * 
    	 */
    	String who = "";
    	StringTokenizer st = null;
    	ListIterator li = null;
    	StringTokenizer st1 = new StringTokenizer(rule_tkn_approvee_id,"-");
		StringTokenizer st2 = new StringTokenizer(tkn_approvee_nickname,"-");
    	if(vRiwayatPengajuan!=null && vRiwayatPengajuan.size()>0) {
    		try {
    			//StringTokenizer st1 = new StringTokenizer(rule_tkn_approvee_id,"-");
    			//StringTokenizer st2 = new StringTokenizer(tkn_approvee_nickname,"-");
				while(st1.hasMoreTokens()) {
					String tkn_id = st1.nextToken();
					String tkn_nick = st2.nextToken();
					boolean match = false;
					li = vRiwayatPengajuan.listIterator();
	    			while(li.hasNext() && !match) {
	    				String brs = (String)li.next();
	    				//lif.add(id_ri+"`"+id+"`"+npm_approvee+"`"+status+"`"+updtm+"`"+komen+"`"+approvee_id+"`"+approvee_nickname);
	    				st = new StringTokenizer(brs,"`");
	    				String id_ri = st.nextToken();
	    				String id = st.nextToken();
	    				String npm_approvee = st.nextToken();
	    				String status = st.nextToken();
	    				String updtm = st.nextToken();
	    				String komen = st.nextToken();
	    				String approvee_id = st.nextToken();
	    				String approvee_nickname = st.nextToken();
	    				//System.out.println("tkn_id=="+tkn_id);
	    				//System.out.println("approvee_id=="+approvee_id);
	    				if(tkn_id.contains("/"+approvee_id+"/")) {
						//sudah ada tindakan
	    					match = true;
	    				}
					}
	    			if(!match) {
	    				who = who + tkn_nick +"-";
	    			}
				}	

        	}
            catch (Exception ex) {
            	ex.printStackTrace();
            }	
    	}
    	else {
    		//belum ada yg ngasih tindahan
    		while(st2.hasMoreTokens()) {
    			who = who + st2.nextToken() +"-";
    		}
    	}
    	 
        
    	return who;
    }
    
    public static String getStatusPengajuanUa(Vector vRiwayatPengajuan, String rule_tkn_approvee_id, String rule_tkn_approvee_nickname) {
    	/* 
    	 * /56/-/62/1/ rule tkn id
    	   /SEKRETARIAT PASCA/-/BIRO KEUANGAN PASCA/ADMIN/ = rule tkn nic
    	 * 
    	 */
    	String tkn_latest_verdict = "";
    	boolean all_yes = false;
    	String approved = "proses";
    	String who = "";
    	StringTokenizer st = null;
    	ListIterator li = null;
    	if(vRiwayatPengajuan!=null && vRiwayatPengajuan.size()>0) {
    		try {
    			StringTokenizer st1 = new StringTokenizer(rule_tkn_approvee_id,"-");
    			StringTokenizer st2 = new StringTokenizer(rule_tkn_approvee_nickname,"-");
				while(st1.hasMoreTokens()) {
					String tkn_id = st1.nextToken();
					String tkn_nick = st2.nextToken();
					boolean match = false;
					li = vRiwayatPengajuan.listIterator();
	    			while(li.hasNext() && !match) {
	    				String brs = (String)li.next();
	    				//lif.add(id_ri+"`"+id+"`"+npm_approvee+"`"+status+"`"+updtm+"`"+komen+"`"+approvee_id+"`"+approvee_nickname);
	    				st = new StringTokenizer(brs,"`");
	    				String id_ri = st.nextToken();
	    				String id = st.nextToken();
	    				String npm_approvee = st.nextToken();
	    				String status = st.nextToken();
	    				if(status.equalsIgnoreCase("tolak")) {
	    					all_yes = false;
	    				}
	    				String updtm = st.nextToken();
	    				String komen = st.nextToken();
	    				String approvee_id = st.nextToken();
	    				String approvee_nickname = st.nextToken();
	    				
	    				//System.out.println("tkn_id=="+tkn_id);
	    				//System.out.println("approvee_id=="+approvee_id);
	    				if(tkn_id.contains("/"+approvee_id+"/")) {
						//sudah ada tindakan
	    					match = true;
	    				}
					}
	    			if(!match) {
	    				who = who + tkn_nick +"-";
	    			}
				}	
				//System.out.println("woh="+who);
				if(Checker.isStringNullOrEmpty(who)) { //artinya semua approved
					if(all_yes) {
						approved = "terima";
					}
					else {
						//berarti ada yg nolak
						
						String tkn_verdict = ""; 
						boolean first = true;
						li = vRiwayatPengajuan.listIterator();
			    		while(li.hasNext()) {
			    			String brs = (String)li.next();
			    			//System.out.println("brss="+brs);
			    			//lif.add(id_ri+"`"+id+"`"+npm_approvee+"`"+status+"`"+updtm+"`"+komen+"`"+approvee_id+"`"+approvee_nickname);
			    			st = new StringTokenizer(brs,"`");
			    			String id_ri = st.nextToken();
			    			//System.out.println("id_ri="+id_ri);
			    			String id = st.nextToken();
			    			String npm_approvee = st.nextToken();
			    			String status = st.nextToken();
			    			String updtm = st.nextToken();
			    			String komen = st.nextToken();
			    			String approvee_id = st.nextToken();
			    			String approvee_nickname = st.nextToken();
			    			if(first) {
			    				first = false;
			    				tkn_verdict = "/"+approvee_id+"/"+status;
			    			}
			    			else {
			    				if(tkn_verdict.contains("/"+approvee_id+"/")) {
			    					StringTokenizer st3 = new StringTokenizer(tkn_verdict);
			    					while(st3.hasMoreTokens()) {
			    						String tkn = st3.nextToken();
			    						if(tkn.contains("/"+approvee_id+"/")) {
			    							tkn_verdict = tkn_verdict.replace(tkn,"/"+approvee_id+"/"+status);
			    						}
			    					}
			    				}
			    				else {
			    					tkn_verdict = tkn_verdict+" /"+approvee_id+"/"+status;
			    				}
			    			}
						}
			    		if(tkn_verdict.contains("TOLAK") || tkn_verdict.contains("tolak")) {
			    			approved = "tolak";
			    		}
			    		else {
			    			approved = "terima";
			    		}
					}
				}
				else {
				//belum semua approve
				//namun fungsi ini blum membedakan anatara belum sama ada sama sekali atau sdh ada yg ngasih tindakan.	
					String tkn_verdict = ""; 
					boolean first = true;
					li = vRiwayatPengajuan.listIterator();
		    		while(li.hasNext()) {
		    			String brs = (String)li.next();
		    			//System.out.println("brss_="+brs);
		    			//lif.add(id_ri+"`"+id+"`"+npm_approvee+"`"+status+"`"+updtm+"`"+komen+"`"+approvee_id+"`"+approvee_nickname);
		    			st = new StringTokenizer(brs,"`");
		    			String id_ri = st.nextToken();
		    			//System.out.println("id_ri_="+id_ri);
		    			String id = st.nextToken();
		    			String npm_approvee = st.nextToken();
		    			String status = st.nextToken();
		    			String updtm = st.nextToken();
		    			String komen = st.nextToken();
		    			String approvee_id = st.nextToken();
		    			String approvee_nickname = st.nextToken();
		    			if(first) {
		    				//System.out.println("firts = "+status);
		    				first = false;
		    				tkn_verdict = "/"+approvee_id+"/"+status;
		    			}
		    			else {
		    				//System.out.println("tkn_verdict contains = "+tkn_verdict);
		    				//System.out.println("not firts = "+status);
		    				if(tkn_verdict.contains("/"+approvee_id+"/")) {
		    					StringTokenizer st3 = new StringTokenizer(tkn_verdict);
		    					while(st3.hasMoreTokens()) {
		    						String tkn = st3.nextToken();
		    						//System.out.println("tkn3 contains = "+tkn);
		    						if(tkn.contains("/"+approvee_id+"/")) {
		    							tkn_verdict = tkn_verdict.replace(tkn,"/"+approvee_id+"/"+status);
		    							//System.out.println("tkn_verdict1 contains = "+tkn_verdict);
		    						}
		    					}
		    				}
		    				else {
		    					tkn_verdict = tkn_verdict+" /"+approvee_id+"/"+status;
		    				}
		    			}
					}
		    		//System.out.println("tkn_verdict2 contains = "+tkn_verdict);
		    		if(tkn_verdict.contains("TOLAK") || tkn_verdict.contains("tolak")) {
		    			approved = "tolak";
		    		}
		    		else {
		    			approved = "terima";
		    		}
				}
        	}
            catch (Exception ex) {
            	ex.printStackTrace();
            }	
    	}
    	return approved;
    }
    
    public static String isTindakanPengajuanUaSdhDiwakilkan(Vector vRiwayatPengajuan, String rule_tkn_approvee_id, long oper_obj_id) {
    	/* 
    	 * /56/-/62/1/ rule tkn id
    	   /SEKRETARIAT PASCA/-/BIRO KEUANGAN PASCA/ADMIN/ = rule tkn nic
    	 * 
    	 */
    	String oleh_siapa = "false/null";
    	boolean sdh = false;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	if(vRiwayatPengajuan!=null && vRiwayatPengajuan.size()>0) {
    		try {
    			StringTokenizer st1 = new StringTokenizer(rule_tkn_approvee_id,"-");
    			//StringTokenizer st2 = new StringTokenizer(tkn_approvee_nickname,"-");
				while(st1.hasMoreTokens()) {
					String tkn_id = st1.nextToken();
					//String tkn_nick = st2.nextToken();
					//System.out.println("tkn_id11="+tkn_id);
					boolean match = false;
					li = vRiwayatPengajuan.listIterator();
	    			while(li.hasNext() && !match) {
	    				String brs = (String)li.next();
	    				//lif.add(id_ri+"`"+id+"`"+npm_approvee+"`"+status+"`"+updtm+"`"+komen+"`"+approvee_id+"`"+approvee_nickname);
	    				st = new StringTokenizer(brs,"`");
	    				String id_ri = st.nextToken();
	    				String id = st.nextToken();
	    				String npm_approvee = st.nextToken();
	    				String status = st.nextToken();
	    				String updtm = st.nextToken();
	    				String komen = st.nextToken();
	    				String approvee_id = st.nextToken();
	    				String approvee_nickname = st.nextToken();
	    				//System.out.println("tkn_id=="+tkn_id);
	    				//System.out.println("approvee_id=="+approvee_id);
	    				if(tkn_id.contains("/"+approvee_id+"/") && tkn_id.contains("/"+oper_obj_id+"/") && !approvee_id.equalsIgnoreCase(""+oper_obj_id)) {
						//sudah diwakilkan
	    					sdh = true;
	    					match = true;
	    					oleh_siapa = true+"/"+approvee_nickname;
	    				}
					}
	    			//if(!match) {
	    			//	who = who + tkn_nick +"-";
	    			//}
				}	

        	}
            catch (Exception ex) {
            	ex.printStackTrace();
            }	
    	}
    	 
        
    	return oleh_siapa;
    }    
    
    public static String getKdjenKurikulum(String idkur) {
    	String kdpst="N/A"; 
    	String kdjen = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT KDPSTKRKLM FROM KRKLM where IDKURKRKLM=?");
    		stmt.setInt(1,Integer.parseInt(idkur));
    		rs = stmt.executeQuery();
    		rs.next();
    		kdpst = rs.getString(1);
    		
    		stmt = con.prepareStatement("select KDJENMSPST from MSPST where KDPSTMSPST=?");
    		stmt.setString(1, kdpst);
    		rs = stmt.executeQuery();
    		rs.next();
    		kdjen = new String(rs.getString(1));
    		
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
    	//System.out.println("kdjen kur = "+kdjen);
    	return kdjen;
    }
    
    
    public static Vector getKurikulumAktif(String kdpst) {
    	//String kdpst="N/A";
    	String thsms_now = Checker.getThsmsNow();
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("select * from KRKLM where KDPSTKRKLM=? and (STKURKRKLM='A' or (STARTTHSMS<=? and (ENDEDTHSMS>=? or ENDEDTHSMS is null)))");
    		stmt.setString(1, kdpst);
    		stmt.setString(2, thsms_now);
    		stmt.setString(3, thsms_now);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String idkur = ""+rs.getInt("IDKURKRKLM");
        		String nmkur = ""+rs.getString("NMKURKRKLM");
        		String start = ""+rs.getString("STARTTHSMS");
        		String end = ""+rs.getString("ENDEDTHSMS");
        		String stkur  = ""+rs.getString("STKURKRKLM");
        		String skstt  = ""+rs.getInt("SKSTTKRKLM");
        		String smstt  = ""+rs.getInt("SMSTTKRKLM");
        		li.add(idkur+"`"+nmkur+"`"+stkur+"`"+start+"`"+end+"`"+skstt+"`"+smstt);
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
    	//System.out.println("kdjen kur = "+kdjen);
    	return v;
    }
    
    
    public static Vector getListKurikulum(String kdpst,boolean aktif) {
    	//String kdpst="N/A";
    	String thsms_now = Checker.getThsmsNow();
    	Vector v = new Vector();
    	if(aktif) {
    		v=getKurikulumAktif(kdpst);
    	}
    	else {
    		ListIterator li = v.listIterator();
        	Connection con=null;
        	PreparedStatement stmt=null;
        	ResultSet rs=null;
        	DataSource ds=null;
        	try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//cretae NPM auto increment
        		stmt = con.prepareStatement("select * from KRKLM where KDPSTKRKLM=? order by STARTTHSMS");
        		stmt.setString(1, kdpst);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String idkur = ""+rs.getInt("IDKURKRKLM");
            		String nmkur = ""+rs.getString("NMKURKRKLM");
            		String start = ""+rs.getString("STARTTHSMS");
            		String end = ""+rs.getString("ENDEDTHSMS");
            		String stkur  = ""+rs.getString("STKURKRKLM");
            		String skstt  = ""+rs.getInt("SKSTTKRKLM");
            		String smstt  = ""+rs.getInt("SMSTTKRKLM");
            		
            		li.add(idkur+"`"+nmkur+"`"+stkur+"`"+start+"`"+end+"`"+skstt+"`"+smstt);
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
    		
    	//System.out.println("kdjen kur = "+kdjen);
    	return v;
    }
    
    public static Vector getListJenisMakulUjian(String kdpst) {
    	Vector v = getKurikulumAktif(kdpst);
    	Vector vUa = new Vector();
    	ListIterator li,li1 = null;
    	String list = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	if(v!=null && v.size()>0) {
    		//System.out.println("vKurAktif size = = "+v.size());
    		li = v.listIterator();
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("SELECT * from JENIS_MAKUL where KETERANGAN like ? or KETERANGAN like ?");
        		stmt.setString(1, "%UJIAN%");
        		stmt.setString(2, "%ujian%");
        		rs = stmt.executeQuery();
        		rs.next();
        		String kode_makul_ujian = ""+rs.getString("KODE_JENIS");
        		//System.out.println("kode_makul_ujian="+kode_makul_ujian);
        		stmt = con.prepareStatement("SELECT * from MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=? and JENISMAKUL=?");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			//System.out.println("brs="+brs);
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String idkur = st.nextToken();
        			String nmkur = st.nextToken();
        			String stkur = st.nextToken();
        			String start = st.nextToken();
        			String end = st.nextToken();
        			stmt.setInt(1,Integer.parseInt(idkur));
        			stmt.setString(2, kode_makul_ujian);
        			rs =stmt.executeQuery();
        			if(rs.next()) {
        				li1 = vUa.listIterator();
        				do {
        					String idkmk = ""+rs.getInt("IDKMKMAKUL");
        					String kdkmk = rs.getString("KDKMKMAKUL");
        					String nakmk = rs.getString("NAKMKMAKUL");
        					String jenisMakul = rs.getString("JENISMAKUL");
        					li1.add(idkmk+"`"+kdkmk+"`"+nakmk);
        				} 
        				while(rs.next());
        			}
        		}
        		vUa = Tool.removeDuplicateFromVector(vUa);

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
    		
    	return vUa;
    }
    
    
    public static String getListStmhs() {
    	String tkn_stmhs_kode = "";
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT VALUE from CONSTANT where KETERANGAN='STMHS'");
    		rs = stmt.executeQuery();
    		rs.next();
    		tkn_stmhs_kode = ""+rs.getString(1);
    		

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
    	
    		
    	return tkn_stmhs_kode;
    }
    
    
    
    public static String getListKategoriBahanAjar(String kdpst) {
    	//String kdpst="N/A"; 
    	//String kdjen = getKdjenKurikulum(idkur);
    	String tkn_tipe = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT distinct TIPE FROM KATEGORI_BAHAN_AJAR where KDPST=? and AKTIF=?");
    		stmt.setString(1,kdpst);
    		stmt.setBoolean(2,true);
    		rs = stmt.executeQuery();
    		
    		if(rs.next()) {
    			tkn_tipe = new String();
    			do {
    				tkn_tipe = tkn_tipe+rs.getString("TIPE")+"`";
    			}while(rs.next());
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
    	//System.out.println("tkn_tipe = "+tkn_tipe);
    	return tkn_tipe;
    }
    
    
    public static Vector getListJenisMakul() {
    	//String kdpst="N/A"; 
    	//String kdjen = getKdjenKurikulum(idkur);
    	String tkn_tipe = null;
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
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT ID,KETERANGAN,KODE_JENIS from JENIS_MAKUL order by KODE_JENIS");
    		
    		rs = stmt.executeQuery();
    		
    		if(rs.next()) {
    			ListIterator li = v.listIterator();
    			do {
    				String id = ""+rs.getInt("ID");
					String keter = ""+rs.getString("KETERANGAN");
					String kode = ""+rs.getString("KODE_JENIS");
					li.add(id+"`"+keter+"`"+kode);
    			}while(rs.next());
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
    
    
    
    public static String getDomisiliKampus(String npmhs) {
    	String kodeKampus="N/A";     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("SELECT KODE_KAMPUS_DOMISILI from OBJECT inner join CIVITAS on OBJECT.ID_OBJ=CIVITAS.ID_OBJ where NPMHSMSMHS=?");
    		stmt.setString(1,npmhs);
    		rs = stmt.executeQuery();
    		rs.next();
    		kodeKampus = rs.getString(1);
    		
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
    	return kodeKampus;
    }
    
    
    public static String getDomisiliKampus(int int_id_obj) {
    	String kodeKampus="N/A";     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("SELECT KODE_KAMPUS_DOMISILI from OBJECT  where ID_OBJ=?");
    		stmt.setInt(1,int_id_obj);
    		rs = stmt.executeQuery();
    		rs.next();
    		kodeKampus = rs.getString(1);
    		
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
    	return kodeKampus;
    }

    
    public static Vector getListAllKampus() {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("SELECT * from KAMPUS");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String code_campus = ""+rs.getString("KODE_KAMPUS");
    			String name_campus = ""+rs.getString("NAMA_KAMPUS");
    			String nickname_campus = ""+rs.getString("NICKNAME_KAMPUS");
    			li.add(code_campus+"`"+name_campus+"`"+nickname_campus);
    			
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
    
    public static Vector getListAllKampus(boolean aktif_kampus_only, String target_thsms) {
    	String tkn_kampus_tutup = FilterKampus.getListKampusNonActive(target_thsms);
    	if(!Checker.isStringNullOrEmpty(tkn_kampus_tutup)) {
    		tkn_kampus_tutup = "`"+tkn_kampus_tutup+"`";
    	}
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("SELECT * from KAMPUS");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String code_campus = ""+rs.getString("KODE_KAMPUS");
    			String name_campus = ""+rs.getString("NAMA_KAMPUS");
    			String nickname_campus = ""+rs.getString("NICKNAME_KAMPUS");
    			if(!Checker.isStringNullOrEmpty(tkn_kampus_tutup)) {
    				if(!tkn_kampus_tutup.contains("`"+code_campus+"`")) {
    					li.add(code_campus+"`"+name_campus+"`"+nickname_campus);
    				}
    			}
    			else {
    				li.add(code_campus+"`"+name_campus+"`"+nickname_campus);
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

    public static String getScopeKampus(Vector validUsr_returnScopeProdiOnlySortByKampusWithListIdobj3var) {
    	String list_kampus = null;
    	if(validUsr_returnScopeProdiOnlySortByKampusWithListIdobj3var!=null && validUsr_returnScopeProdiOnlySortByKampusWithListIdobj3var.size()>0) {
    		ListIterator li = validUsr_returnScopeProdiOnlySortByKampusWithListIdobj3var.listIterator();
    		while(li.hasNext()) {
    			Vector v_combine = (Vector) li.next();
    			Vector v_approvee= (Vector) li.next(); //tidak dibutuhkan
    			li = v_combine.listIterator();
    			if(li.hasNext()) {
    				list_kampus = new String("");
    				do {
    					String brs = (String)li.next();
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				String kode_kmp = st.nextToken();
        				String nama_kmp = Converter.getNamaKampus(kode_kmp);
        				list_kampus = list_kampus+"["+kode_kmp+","+nama_kmp+"]";
        				if(li.hasNext()) {
        					list_kampus = list_kampus+"`";
        				}
    				}
    				while(li.hasNext());
    			}
    		}
    	}
    	return list_kampus;
    }
    
    public static Vector returnListProdiOnlySortByKampusWithListIdobj() {
    	Vector v_prodi = getListProdi();//li.add(kdpst+"`"+kdfak+"`"+kdjen+"`"+nmpst);
    	Vector v_kmp = getListAllKampus(); //li.add(code_campus+"`"+name_campus+"`"+nickname_campus);
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	String tkn_kdjen = null;
    	ListIterator li = null, li1 = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		String cmd_list = "";
    		li = v_prodi.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kdpst = st.nextToken();
    			cmd_list = cmd_list+"KDPST='"+kdpst+"'";
    			if(li.hasNext()) {
    				cmd_list=cmd_list+" or ";
    			}
    		}
    		stmt = con.prepareStatement("SELECT ID_OBJ from OBJECT where ("+cmd_list+") and KODE_KAMPUS_DOMISILI=? order by KDPST");
    		li1 = v_kmp.listIterator();
    		while(li1.hasNext()) {
    			String brs = (String)li1.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kmp = st.nextToken();
    			stmt.setString(1, kmp);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				String list_idobj = "";
    				do {
    					list_idobj=list_idobj+"`"+rs.getLong(1);
    				}
    				while(rs.next());
    				lif.add(kmp+list_idobj);
    			}
    			else {
    				lif.add(kmp);
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
    

    
    public static Vector returnListProdiOnlySortByKampusWithListIdobj(boolean aktif_kampus_only, String target_thsms) {
    	Vector v_prodi = getListProdi();//li.add(kdpst+"`"+kdfak+"`"+kdjen+"`"+nmpst);
    	Vector v_kmp = null;
    	if(aktif_kampus_only) {
    		v_kmp = getListAllKampus(true,target_thsms);
//    		/code_campus+"`"+name_campus+"`"+nickname_campus
    	}
    	else {
    		v_kmp = getListAllKampus();
    		//code_campus+"`"+name_campus+"`"+nickname_campus
    	}
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	String tkn_kdjen = null;
    	ListIterator li = null, li1 = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		String cmd_list = "";
    		li = v_prodi.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kdpst = st.nextToken();
    			cmd_list = cmd_list+"KDPST='"+kdpst+"'";
    			if(li.hasNext()) {
    				cmd_list=cmd_list+" or ";
    			}
    		}
    		stmt = con.prepareStatement("SELECT ID_OBJ from OBJECT where ("+cmd_list+") and KODE_KAMPUS_DOMISILI=? order by KDPST");
    		li1 = v_kmp.listIterator();
    		while(li1.hasNext()) {
    			String brs = (String)li1.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kmp = st.nextToken();
    			stmt.setString(1, kmp);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				String list_idobj = "";
    				do {
    					list_idobj=list_idobj+"`"+rs.getLong(1);
    				}
    				while(rs.next());
    				lif.add(kmp+list_idobj);
    			}
    			else {
    				lif.add(kmp);
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
    
    public static String  getListKdjenProdi() {
    	String tkn_kdjen = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("SELECT distinct KDJENMSPST from MSPST where KDJENMSPST<=? && KDJENMSPST<>? order by KDJENMSPST");
    		stmt.setString(1, "G");
    		stmt.setString(2, "0");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			tkn_kdjen = new String();
    			do {
    				tkn_kdjen = tkn_kdjen+rs.getString(1)+"`";	
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
        finally {
        	if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
        }	
    	return tkn_kdjen;
    }

    public static String getListProdiDalam1Fakultas(String kdpst) {
    	String tkn_prodi="N/A";   
    	String info_fak = Checker.getFakInfo(kdpst);
    	StringTokenizer st = new StringTokenizer(info_fak);
    	String kdfak = st.nextToken();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("SELECT KDPSTMSPST from MSPST where KDFAKMSPST=?");
    		stmt.setString(1,kdfak);
    		rs = stmt.executeQuery();
    		if(!rs.next()) {
    			tkn_prodi = "null";	
    		}
    		else {
    			tkn_prodi = "";
    			tkn_prodi = tkn_prodi+rs.getString("KDPSTMSPST")+",";
    			while(rs.next()) {
        			tkn_prodi = tkn_prodi+rs.getString("KDPSTMSPST")+",";
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
    	return tkn_prodi;
    }
    
    public static String getListProdiDariLainFakultas(String tkn_fak) {
    	String tkn_prodi="N/A";   
    	if(tkn_fak!=null && new StringTokenizer(tkn_fak).countTokens()>0) {
    		StringTokenizer st = new StringTokenizer(tkn_fak);
        	
        	Connection con=null;
        	PreparedStatement stmt=null;
        	ResultSet rs=null;
        	DataSource ds=null;
        	try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		tkn_prodi = null;	
        		stmt = con.prepareStatement("SELECT KDPSTMSPST from MSPST where KDFAKMSPST=?");
        		while(st.hasMoreTokens()) {
        			String kdfak = st.nextToken();
        			stmt.setString(1,kdfak);
            		rs = stmt.executeQuery();
            		boolean first = true;
            		while(rs.next()) {
            			if(first) {
            				first = false;
            				if(tkn_prodi==null) {
            					tkn_prodi = "";
            				}	
            			}
            			else {
            				tkn_prodi = tkn_prodi+rs.getString("KDPSTMSPST")+",";
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
    		
    	return tkn_prodi;
    }
    
    public static Vector addKodeKampusToVscope(Vector vScope) {
    	     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	if(vScope!=null && vScope.size()>0) {
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//cretae NPM auto increment
        		stmt = con.prepareStatement("SELECT KODE_KAMPUS_DOMISILI FROM OBJECT where ID_OBJ=?");
        		ListIterator li = vScope.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs);
    				String idObj = st.nextToken();
    				String kdpst = st.nextToken();
    				String nmfak = st.nextToken().replace("MHS_", "");
    				String obLvl = st.nextToken();
    				String kdjen = st.nextToken();
    				stmt.setInt(1, Integer.parseInt(idObj));
    				rs = stmt.executeQuery();
    				rs.next();
    				li.set(brs+" "+rs.getString("KODE_KAMPUS_DOMISILI"));
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
    		
    	return vScope;
    }
    /*
     * deprecated
     */
    public static Vector getListDosen() {
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM MSDOS order by NMDOS");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String nmdos  = rs.getString("NMDOS");
    			String nodos  = rs.getString("nodos");
    			li.add(nodos+"__"+nmdos);
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
    
    /*
     * updated tambahan npm
     */
    public static Vector getListDosen_v1() {
    	Vector v = new Vector();
    	v = getListDosen_v1(false);
    	/*
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT NPMHSMSMHS,NMMHSMSMHS,NIDNN,NOMOR_ID FROM CIVITAS inner join EXT_CIVITAS_DATA_DOSEN on NPMHSMSMHS=NPMHS order by NMMHSMSMHS");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String npmdos  = ""+rs.getString("NMMHSMSMHS");
    			String nmdos  = ""+rs.getString("NMMHSMSMHS");
    			String nodos  = ""+rs.getString("NIDNN");
    			String nomor  = ""+rs.getString("NOMOR_ID");
    			if(!Checker.isStringNullOrEmpty(nodos)||!Checker.isStringNullOrEmpty(nomor)) {
    				li.add(nmdos+"`"+nodos+"`"+nomor+"`"+npmdos);
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
    	
    	return v;	
    }
    
    public static Vector getListDosen_v1(boolean dosen_nidn_only) {
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT NPMHSMSMHS,NMMHSMSMHS,NIDNN,NOMOR_ID,NIDK,NUP FROM CIVITAS inner join EXT_CIVITAS_DATA_DOSEN on NPMHSMSMHS=NPMHS order by NMMHSMSMHS");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String npmdos  = ""+rs.getString("NPMHSMSMHS");
    			String nmdos  = ""+rs.getString("NMMHSMSMHS");
    			String nodos  = ""+rs.getString("NIDNN");
    			String nomor  = ""+rs.getString("NOMOR_ID");//??? ngga tau nomor apa nin\
    			String nidk  = ""+rs.getString("NIDK");
    			String nup  = ""+rs.getString("NUP");
    			
    			if(dosen_nidn_only) {
    				if(!Checker.isStringNullOrEmpty(nodos)||!Checker.isStringNullOrEmpty(nidk)||!Checker.isStringNullOrEmpty(nup)) {
        				li.add(nmdos+"`"+nodos+"`"+nomor+"`"+npmdos+"`"+nidk+"`"+nup);
        			}
    			}
    			else {
    				li.add(nmdos+"`"+nodos+"`"+nomor+"`"+npmdos+"`"+nidk+"`"+nup);
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

    /*
     * DEPRECATED
     * KARENA KALO YANG INI TIDAK MIKIRIN KAMPUS
     * GANTI getListProdiBasedOnObject()
     */
    public static Vector getListProdi() {
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM MSPST where KDJENMSPST=? or KDJENMSPST=? or KDJENMSPST=? or KDJENMSPST=? or KDJENMSPST=? or KDJENMSPST=? or KDJENMSPST=? order by NMPSTMSPST");
    		int i=1;
    		stmt.setString(i++, "A");
    		stmt.setString(i++, "B");
    		stmt.setString(i++, "C");
    		stmt.setString(i++, "D");
    		stmt.setString(i++, "E");
    		stmt.setString(i++, "F");
    		stmt.setString(i++, "G");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdpst  = rs.getString("KDPSTMSPST");
    			String kdfak  = rs.getString("KDFAKMSPST");
    			String kdjen  = rs.getString("KDJENMSPST");
    			String nmpst  = rs.getString("NMPSTMSPST");
    			li.add(kdpst+"`"+kdfak+"`"+kdjen+"`"+nmpst);
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
    
    public static Vector getListKdpst() {
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT distinct KDPSTMSPST FROM MSPST where KDJENMSPST=? or KDJENMSPST=? or KDJENMSPST=? or KDJENMSPST=? or KDJENMSPST=? or KDJENMSPST=? or KDJENMSPST=? order by KDPSTMSPST");
    		int i=1;
    		stmt.setString(i++, "A");
    		stmt.setString(i++, "B");
    		stmt.setString(i++, "C");
    		stmt.setString(i++, "D");
    		stmt.setString(i++, "E");
    		stmt.setString(i++, "F");
    		stmt.setString(i++, "G");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdpst  = rs.getString("KDPSTMSPST");
    			li.add(kdpst);
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
    
    public static Vector getListKdpstKdjenNmpstNmjen() {
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT distinct KDPSTMSPST,NMPSTMSPST,KDJENMSPST FROM MSPST where KDJENMSPST<'H' and KDJENMSPST>'0' order by NMPSTMSPST");
    		int i=1;
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdpst  = rs.getString(1);
    			String nmpst  = rs.getString(2);
    			String kdjen  = rs.getString(3);
    			String nmjen = "";
    			if(kdjen.equalsIgnoreCase("A")) {
    				nmjen = "[S-3]";
    			}
    			else if(kdjen.equalsIgnoreCase("B")) {
    				nmjen = "[S-2]";
    			} 
    			else if(kdjen.equalsIgnoreCase("C")) {
    				nmjen = "[S-1]";
    			}
    			else if(kdjen.equalsIgnoreCase("D")) {
    				nmjen = "[D-4]";
    			}
    			else if(kdjen.equalsIgnoreCase("E")) {
    				nmjen = "[D-3]";
    			}
    			else if(kdjen.equalsIgnoreCase("F")) {
    				nmjen = "[D-2]";
    			}
    			else if(kdjen.equalsIgnoreCase("G")) {
    				nmjen = "[D-1]";
    			}
    			
    			li.add(kdpst.trim()+"~"+kdjen.trim()+"~"+nmpst.trim()+"~"+nmjen.trim());
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
    
    public static Vector getListProdiBasedOnObject() {
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM OBJECT INNER JOIN MSPST ON KDPST=KDPSTMSPST WHERE OBJ_NICKNAME LIKE 'MHS%' ORDER BY KDPST,KODE_KAMPUS_DOMISILI");

    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdpst  = ""+rs.getString("KDPSTMSPST");
    			String kdfak  = ""+rs.getString("KDFAKMSPST");
    			String kdjen  = ""+rs.getString("KDJENMSPST");
    			String nmpst  = ""+rs.getString("NMPSTMSPST");
    			String idobj  = ""+rs.getLong("ID_OBJ");
    			String kdkmp  = ""+rs.getString("KODE_KAMPUS_DOMISILI");
    			
    			li.add(kdpst+"`"+kdfak+"`"+kdjen+"`"+nmpst+"`"+kdkmp+"`"+idobj);
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
    
    
	public static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

	  public static JSONObject readJsonObjFromUrl(String url) throws IOException, JSONException {
		//url = url.replace("&#x2f;", "/");  
		url = url.replace(" ", "%20");
		url = Constants.getAlamatIp()+url;
	    InputStream is = new URL(url).openStream();
	    String jsonText = null;
	    JSONObject json = null;
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      jsonText = readAll(rd);
	      json = new JSONObject(jsonText);
	    } 
	    catch(JSONException je) {
	    	je.printStackTrace();
	    }
	    finally {
	      is.close();
	    }
	    return json;
	  }
	  
	  
	  
	  public static JSONArray readJsonArrayFromUrl(String url) throws IOException{
		  	//url = url.replace("&#x2f;", "/");  url mulai dari /v1
		  	url = url.replace(" ", "%20");
		  	url = url.replace("|", "%7C");
		  	url = url.replace("?", "%3F");

		  	url = Constants.getAlamatIp()+url;
		  	//System.out.println("url="+url);
		    InputStream is = new URL(url).openStream();
		    JSONArray jsoa = null;
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      //System.out.println("jsonText="+jsonText);
		      jsoa = new JSONArray(jsonText);
		      
		    } 
		    catch(JSONException je) {
		    	je.printStackTrace();
		    }
		    finally {
		      is.close();
		    }
		    return jsoa;
	  }
	  
	  public static String readStringFromUrl(String url) throws IOException {
		  	//url = url.replace("&#x2f;", "/");  
		  	url = url.replace(" ", "%20");
		  	url = Constants.getAlamatIp()+url;
		    InputStream is = new URL(url).openStream();
		    String jsonText = null;
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      jsonText = readAll(rd);
		    } 
		    finally {
		      is.close();
		    }
		    return jsonText;
	  }
	  
	  public static String getListPembimbingAkademik(String tkn_kdpst) {
	    	//String kdpst="N/A"; 
	    	//String kdjen = getKdjenKurikulum(idkur);
	    	String tkn_npmPa = null;
	    	Connection con=null;
	    	PreparedStatement stmt=null;
	    	ResultSet rs=null;
	    	DataSource ds=null;
	    	if(tkn_kdpst!=null && !Checker.isStringNullOrEmpty(tkn_kdpst)) {
	    		try {
		    		Context initContext  = new InitialContext();
		    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
		    		con = ds.getConnection();
		    		//cretae NPM auto increment
		    		
		    		stmt = con.prepareStatement("SELECT distinct NPM_PA FROM EXT_CIVITAS WHERE KDPSTMSMHS=?");
		    		StringTokenizer st = new StringTokenizer(tkn_kdpst,"`");
		    		if(st.hasMoreTokens()) {
		    			tkn_npmPa = new String();
		    			while(st.hasMoreTokens()) {
		    				String kdpst = st.nextToken();
			    			stmt.setString(1,kdpst);
				    		rs = stmt.executeQuery();
				    		
				    		if(rs.next()) {		
				    			do {
				    				String npm_pa = rs.getString(1);
				    				//System.out.println("npm_pa="+npm_pa);
				    				if(npm_pa!=null && !tkn_npmPa.contains(npm_pa)) {
				    					tkn_npmPa = tkn_npmPa+npm_pa+"`";	
				    				}
				    			}while(rs.next());
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
	    		
	    	//System.out.println("tkn_tipe = "+tkn_tipe);
	    	return tkn_npmPa;
	    }
    
	  
	  public static int getObjLvl(String target_npm) {
	    	//String kdpst="N/A"; 
	    	//String kdjen = getKdjenKurikulum(idkur);
	    int obj_lvl = -1;
	    Connection con=null;
	    PreparedStatement stmt=null;
	    ResultSet rs=null;
	    DataSource ds=null;
	    	
	    try {
		   	Context initContext  = new InitialContext();
		   	Context envContext  = (Context)initContext.lookup("java:/comp/env");
		   	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
		   	con = ds.getConnection();
		    		//cretae NPM auto increment
		    		
	    	stmt = con.prepareStatement("SELECT OBJ_LEVEL FROM CIVITAS INNER JOIN OBJECT ON CIVITAS.ID_OBJ=OBJECT.ID_OBJ WHERE NPMHSMSMHS=?");
		   	stmt.setString(1, target_npm);
		   	rs = stmt.executeQuery();
		   	rs.next();
		   	obj_lvl	= rs.getInt(1);	
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
	  
	    		
	    	//System.out.println("tkn_tipe = "+tkn_tipe);
	   	return obj_lvl;
	}

	public static Vector getListNpmMalaikat() {
		Vector v = null;
		ListIterator li = null;
	    Connection con=null;
	    PreparedStatement stmt=null;
	   	ResultSet rs=null;
	   	DataSource ds=null;
	   	try {
	   		Context initContext  = new InitialContext();
	   		Context envContext  = (Context)initContext.lookup("java:/comp/env");
	   		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
	   		con = ds.getConnection();
	   		//cretae NPM auto increment
	   		stmt = con.prepareStatement("SELECT KDPSTMSMHS,NPMHSMSMHS from CIVITAS where MALAIKAT=?");
	   		stmt.setBoolean(1,true);
	   		rs = stmt.executeQuery();
	   		while(rs.next()) {
	   			String kdpst = rs.getString(1);
	   			String npm = rs.getString(2);
	   			if(v == null) {
	   				v = new Vector();
	   				li = v.listIterator();
	   			}
	   			li.add(kdpst+"`"+npm);
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
	
    public static String smawlAngkatanPertama(String kdpst) {
    	String smawl_ang_1=null;
    	Connection con=null;
	    PreparedStatement stmt=null;
	   	ResultSet rs=null;
	   	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select SMAWLMSMHS from CIVITAS where KDPSTMSMHS=? and SMAWLMSMHS is not null order by SMAWLMSMHS limit 1");
			stmt.setString(1, kdpst);
			rs = stmt.executeQuery();
			if(rs.next()) {
				smawl_ang_1 = rs.getString(1);
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
    	return smawl_ang_1;
    }
    
    
    public static String listAllNamaJabatanOnly(int objid) {
    	
    	String tkn_jab=null;
    	Connection con=null;
	    PreparedStatement stmt=null;
	   	ResultSet rs=null;
	   	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select distinct NM_JOB from STRUKTURAL where OBJID=? and AKTIF=?");
			stmt.setInt(1, objid);
			stmt.setBoolean(2,true);
			rs = stmt.executeQuery();
			if(rs.next()) {
				String nm_job = rs.getString(1);
				tkn_jab = new String(nm_job);
				while(rs.next()) {
					nm_job = rs.getString(1);
					tkn_jab = tkn_jab+"`"+nm_job;
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
    	return tkn_jab;
    }
    
    public static Vector getListMyJabatan(int objid) {
    	
    	Vector v = null;
    	ListIterator li = null;
    	Connection con=null;
	    PreparedStatement stmt=null;
	   	ResultSet rs=null;
	   	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("SELECT KDKMP,STRUKTURAL.KDPST,NM_JOB,SINGKATAN,ID_JAB,ID as ID_STRUK,OBJECT.ID_OBJ as target_idobj,OBJ_DESC as target_nm_obj from JABATAN inner join STRUKTURAL on NAMA_JABATAN=NM_JOB inner join OBJECT on (STRUKTURAL.KDKMP=KODE_KAMPUS_DOMISILI and STRUKTURAL.KDPST=OBJECT.KDPST) where JABATAN.AKTIF=true and STRUKTURAL.AKTIF=true and OBJID=?");
			stmt.setInt(1, objid);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					String kdkmp = rs.getString(1);
					String kdpst = rs.getString(2);
					String nm_job = rs.getString(3);
					String short_job = rs.getString(4);
					String id_jab = rs.getString(5);
					String id_struk = rs.getString(6);
					String target_idobj = rs.getString(7);
					String target_nm_obj = rs.getString(8);
					String tmp = kdkmp+"~"+kdpst+"~"+nm_job+"~"+short_job+"~"+id_jab+"~"+id_struk+"~"+target_idobj+"~"+target_nm_obj;
					tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
					li.add(tmp);
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
    	finally {
        	if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
        }
    	return v;
    }
    

}

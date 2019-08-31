package beans.tools;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import org.apache.tomcat.jdbc.pool.DataSource;

import beans.setting.Constants;

import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.ListIterator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
/**
 * Session Bean implementation class Converter
 */
@Stateless
@LocalBean
public class Converter {

    /**
     * Default constructor. 
     */
    public Converter() {
        //TODO Auto-generated constructor stub
    }

    public static java.sql.Date formatDateBeforeInsert(String tanggal) {
    	java.sql.Date dt = null;
    	if(!Checker.isStringNullOrEmpty(tanggal)) {
    		try {
    			tanggal = tanggal.replace("/", "-");
            	StringTokenizer st = new StringTokenizer(tanggal,"-");
            	String tgl = st.nextToken();
            	String bln = st.nextToken();
            	String thn = st.nextToken();
            	if(tgl.length()==4) {
            		String tmp_thn = ""+tgl;
            		tgl = ""+thn;
            		thn = ""+tmp_thn;
            	}
            	dt = java.sql.Date.valueOf(thn+"-"+bln+"-"+tgl);	
    		}
    		catch(Exception e) {
    			
    		}
    			
    	}
    	
    	return dt;
    }
    
    public static String ubahKeformatTahunAkademik(String thsms) {
    	
    	String th = thsms.substring(0,4);
    	return th = th+"/"+(Integer.parseInt(th)+1);
    	
    }
    
    public static String getSemesterGenapGanjil(String thsms) {
    	
    	String sms = thsms.substring(4,thsms.length());
    	if(sms.equalsIgnoreCase("1")) {
    		sms = "GANJIL";
    	}
    	else {
    		sms = "GENAP";
    	}
    	return sms;
    	
    }
    
    public static String thsmsForCompare(String thsms) {
    	if(thsms.contains("A")) {
    		thsms = thsms.substring(0,4)+"1A";
    	}
    	else if(thsms.contains("B")) {
    		thsms = thsms.substring(0,4)+"2B";
    	}
    	return thsms;
    }
    
    
    public static String convertStmhsJadiTipePengajuan(String stmhs) {
    	String tipe_pengajuan = "";
    	if(stmhs.equalsIgnoreCase("K")) {
    		tipe_pengajuan = "KELUAR";
    	}
    	else if(stmhs.equalsIgnoreCase("C")) {
    		tipe_pengajuan = "CUTI";
    	}
    	else if(stmhs.equalsIgnoreCase("L")) {
    		tipe_pengajuan = "KELULUSAN";
    	}
    	else if(stmhs.equalsIgnoreCase("D")) {
    		tipe_pengajuan = "DO";
    	}
    	return tipe_pengajuan;
    }
    
    public static String convertTipePengajuanJadiKodeStmhs(String nama_pengajuan) {
    	String stmhs = "";
    	if(nama_pengajuan.equalsIgnoreCase("KELUAR")) {
    		stmhs = "K";
    	}
    	else if(nama_pengajuan.equalsIgnoreCase("CUTI")) {
    		stmhs = "C";
    	}
    	else if(nama_pengajuan.equalsIgnoreCase("KELULUSAN")) {
    		stmhs = "L";
    	}
    	else if(nama_pengajuan.equalsIgnoreCase("DO")) {
    		stmhs = "D";
    	}
    	return stmhs;
    }
    
    public static java.sql.Time formatTimeBeforeInsert(String time) {
    	java.sql.Time tm = null;
    	StringTokenizer st = new StringTokenizer(time,":");
    	if(!Checker.isStringNullOrEmpty(time) && st.countTokens()>1) {
    		try {
    			long ss = 0;
    			long mm = 0;
    			long hh = 0;
    			
    			hh = Long.parseLong(st.nextToken());
    			mm = Long.parseLong(st.nextToken());
    			if(st.hasMoreTokens()) {
    				ss = Long.parseLong(st.nextToken());
    			}
            	
            	tm = java.sql.Time.valueOf(hh+":"+mm+":"+ss);	
    		}
    		catch(Exception e) {
    			
    		}
    			
    	}
    	
    	return tm;
    }
    
    public static String reformatSqlDateToTglBlnThn(String tanggalDalamFormatSql) {
    	//java.sql.Date dt = null;
    	//tanggalDalamFormatSql = tanggalDalamFormatSql.replace("/", "-");
    	StringTokenizer st = new StringTokenizer(tanggalDalamFormatSql,"-");
    	String thn = st.nextToken();
    	String bln = st.nextToken();
    	String tgl = st.nextToken();
    	tanggalDalamFormatSql = ""+tgl+"/"+bln+"/"+thn;
    	
    	return tanggalDalamFormatSql;
    }
    
    public static String convertTandaKoma(String stmn) {
    	if(stmn.contains(",")) {
    		stmn = stmn.replaceAll(",", "tandaKoma");
    	}
    	return stmn;
    }
    
    public static String revertTandaKoma(String stmn) {
    	if(stmn.contains("tandaKoma")) {
    		stmn = stmn.replaceAll("tandaKoma", ",");
    	}
    	return stmn;
    }
    
    public static String formatDdSlashMmSlashYy(String tanggal) {
    	//java.sql.Date dt = null;
    	if(!Checker.isStringNullOrEmpty(tanggal)) {
    		tanggal = tanggal.replace("-", "/");
    		StringTokenizer st = new StringTokenizer(tanggal,"/");
    		String tgl = st.nextToken();
    		String bln = st.nextToken();
    		String thn = st.nextToken();
    		if(tgl.length()==4) {
    			String tmp_thn = ""+tgl;
    			tgl = ""+thn;
    			thn = ""+tmp_thn;
    		}
    		tanggal = (tgl+"/"+bln+"/"+thn);
    	}
    	return tanggal;
    }

    
    public static String convertThsms(String thsms) {
    	String tahun = thsms.substring(0,4);
		String sms = thsms.substring(4,thsms.length());
		String keter_thsms="";
		if(sms.equalsIgnoreCase("1")) {
			//keter_thsms = "SMS GANJIL / "+tahun+"#&"+tahun+"1";
			keter_thsms = tahun+"1 (SMS GANJIL)#&"+tahun+"1";
		}
		else {
			if(sms.equalsIgnoreCase("1A")||sms.equalsIgnoreCase("A")) {
				//keter_thsms = "SMS ANTARA I / "+tahun+"#&"+tahun+"A";
				keter_thsms = tahun+"1 (SMS antara I)#&"+tahun+"A";
			}
			else {
				if(sms.equalsIgnoreCase("2")) {
					//keter_thsms = "SMS GENAP / "+tahun+"#&"+tahun+"2";
					keter_thsms = tahun+"2 (SMS GENAP)#&"+tahun+"2";
				}
				else {
					if(sms.equalsIgnoreCase("2B")||sms.equalsIgnoreCase("B")) {
						//keter_thsms = "SMS ANTARA II / "+tahun+"#&"+tahun+"B";
						keter_thsms = tahun+"2 (SMS antara II)#&"+tahun+"B";
					}
				}
			}
		}
		return keter_thsms;
    }

    public static String convertThsmsKeterOnlyFromatThnAkademik(String thsms) {
    	String keter_thsms="";
    	if(thsms!=null && !thsms.equalsIgnoreCase("null")&& !thsms.equalsIgnoreCase("NULL")&& !thsms.equalsIgnoreCase("n/a")&& !thsms.equalsIgnoreCase("N/A")) {
    		String tahun = thsms.substring(0,4);
    		String sms = thsms.substring(4,thsms.length());
    		
    		if(sms.equalsIgnoreCase("1")) {
			//keter_thsms = "SMS GANJIL / "+tahun+"#&"+tahun+"1";
    			keter_thsms = "GANJIL "+tahun+"/"+(Integer.valueOf(tahun).intValue()+1);
    		}
    		else {
    			if(sms.equalsIgnoreCase("1A")||sms.equalsIgnoreCase("A")) {
				//keter_thsms = "SMS ANTARA I / "+tahun+"#&"+tahun+"A";
    				keter_thsms = "ANTARA I "+tahun+"/"+(Integer.valueOf(tahun).intValue()+1);
    			}
    			else {
    				if(sms.equalsIgnoreCase("2")) {
					//keter_thsms = "SMS GENAP / "+tahun+"#&"+tahun+"2";
    					keter_thsms = "GENAP "+tahun+"/"+(Integer.valueOf(tahun).intValue()+1);
    				}
    				else {
    					if(sms.equalsIgnoreCase("2B")||sms.equalsIgnoreCase("B")) {
						//keter_thsms = "SMS ANTARA II / "+tahun+"#&"+tahun+"B";
    						keter_thsms = "ANTARA II "+tahun+"/"+(Integer.valueOf(tahun).intValue()+1);
    					}
    				}	
				}
			}
		}
    	else {
    		keter_thsms="N/A";
    	}
		return keter_thsms;
    }
    
    
    public static String convertThsmsKeterOnly(String thsms) {
    	//System.out.println("thsms="+thsms);
    	String keter_thsms="";
    	try {
    		if(thsms!=null && !thsms.equalsIgnoreCase("null")&& !thsms.equalsIgnoreCase("NULL")&& !thsms.equalsIgnoreCase("n/a")&& !thsms.equalsIgnoreCase("N/A")) {
        		String tahun = thsms.substring(0,4);
        		String sms = thsms.substring(4,thsms.length());
        		
        		if(sms.equalsIgnoreCase("1")) {
    			//keter_thsms = "SMS GANJIL / "+tahun+"#&"+tahun+"1";
        			keter_thsms = tahun+"1 (SMS GANJIL)";
        		}
        		else {
        			if(sms.equalsIgnoreCase("1A")||sms.equalsIgnoreCase("A")) {
    				//keter_thsms = "SMS ANTARA I / "+tahun+"#&"+tahun+"A";
        				keter_thsms = tahun+"1 (SMS Pendek)";
        			}
        			else {
        				if(sms.equalsIgnoreCase("2")) {
    					//keter_thsms = "SMS GENAP / "+tahun+"#&"+tahun+"2";
        					keter_thsms = tahun+"2 (SMS GENAP)";
        				}
        				else {
        					if(sms.equalsIgnoreCase("2B")||sms.equalsIgnoreCase("B")) {
    						//keter_thsms = "SMS ANTARA II / "+tahun+"#&"+tahun+"B";
        						keter_thsms = tahun+"2 (SMS Pendek)";
        					}
        				}	
    				}
    			}
    		}
        	else {
        		keter_thsms="N/A";
        	}
    	}
    	catch(Exception e) {
    		//System.out.println("thsms="+thsms);
    	}
    	
		return keter_thsms;
    }
    
    public static String convertThsmsValueOnly(String thsms) {
    	if(thsms!=null && thsms.length()>5) {
    		thsms = thsms.substring(0,4)+thsms.substring(5,6);
    	}
		return thsms;
    }

    
    
    public static String convertKdjek(String kdjek) {
    	String[]gender = Constants.getOptionGender();
    	boolean match = false;
    	for(int i=0;i<gender.length && !match;i++) {
    		StringTokenizer st = new StringTokenizer(gender[i]);
    		String val = st.nextToken();
    		String ket = st.nextToken();
    		if(val.equalsIgnoreCase(kdjek)) {
    			match = true;
    			kdjek = ket;
    		}
    	}
    	return kdjek.toUpperCase();
    }

    
    public static String getDetailKdjen(String kdjen) {
    	String jenjang = "null";
    	if(kdjen.equalsIgnoreCase("A")) {
    		jenjang = "S-3";
    	}
    	else {
    		if(kdjen.equalsIgnoreCase("B")) {
    			jenjang = "S-2";
        	}
        	else {
        		if(kdjen.equalsIgnoreCase("C")) {
        			jenjang = "S-1";
            	}
            	else {
            		if(kdjen.equalsIgnoreCase("D")) {
            			jenjang = "D-IV";
                	}
                	else {
                		if(kdjen.equalsIgnoreCase("E")) {
                			jenjang = "D-III";
                    	}
                    	else {
                    		if(kdjen.equalsIgnoreCase("G")) {
                    			jenjang = "D-II";
                        	}
                        	else {
                        		if(kdjen.equalsIgnoreCase("H")) {
                        			jenjang = "D-I";
                            	}
                            	else {
                            		
                            	}
                        	}
                    	}
                	}
            	}
        	}
    	}
    	return jenjang;
    }
    
    public static String getKeterTipeIka(String kode_tipe_ika) {
    	String []list_ika = Constants.getTipeIkatanKerjaDosen();
    	String keter = null;
    	boolean match = false;
    	for(int i=0;i<list_ika.length && !match;i++) {
    		StringTokenizer st = new StringTokenizer(list_ika[i],"-");
    		String kode = st.nextToken();
    		keter = st.nextToken();
    		if(kode.equalsIgnoreCase(kode_tipe_ika)) {
    			match = true;
    		}
    	}
    	return keter;
    }
    /*
     * deprecated
     */
    public static String getDetailKdpst(String kdpst) {
    	String info = "null#&null";
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
    		stmt = con.prepareStatement("SELECT * FROM MSPST where KDPSTMSPST=?");
    		stmt.setString(1, kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String nmpst = ""+rs.getString("NMPSTMSPST");
    			String kdjen = ""+rs.getString("KDJENMSPST");
    			info = nmpst+"#&"+kdjen;
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
    
    
    public static Vector convertVscopeidToKdpst(Vector v_scope_id) {
    	Vector v_scope_kdpst = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		v_scope_kdpst = new Vector();
    		ListIterator li1 = v_scope_id.listIterator();
    		ListIterator li2 = v_scope_kdpst.listIterator();
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//cretae NPM auto increment
        		stmt = con.prepareStatement("SELECT KDPST FROM OBJECT where ID_OBJ=?");
        		while(li1.hasNext()) {
        			String new_line = "";
        			String brs = (String)li1.next();
        			//System.out.println(brs);
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String kmp = st.nextToken();
        			new_line = new String(kmp);
        			while(st.hasMoreTokens()) {
        				String id = st.nextToken();
        				stmt.setInt(1,Integer.parseInt(id));
        				rs = stmt.executeQuery();
        				rs.next();
        				String kdpst = ""+rs.getString(1);
        				new_line = new_line+"`"+kdpst;
        			}
        			li2.add(new_line);
        			//System.out.println(new_line);
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
    
    
    public static Vector convertVscopeidToTokenDistinctKdpst(Vector v_scope_id) {
    	Vector v_scope_kdpst = null;
    	String tkn_distinct_kdpst = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		v_scope_kdpst = new Vector();
    		ListIterator li1 = v_scope_id.listIterator();
    		ListIterator li2 = v_scope_kdpst.listIterator();
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//cretae NPM auto increment
        		stmt = con.prepareStatement("SELECT KDPST FROM OBJECT where ID_OBJ=?");
        		while(li1.hasNext()) {
        			String new_line = "";
        			String brs = (String)li1.next();
        			//System.out.println(brs);
        			StringTokenizer st = new StringTokenizer(brs);
        			String id = st.nextToken();
        			stmt.setInt(1,Integer.parseInt(id));
        			rs = stmt.executeQuery();
        			rs.next();
        			String kdpst = ""+rs.getString(1);
        			li2.add(kdpst);
        			
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
    	try {
    		if(v_scope_kdpst!=null) {
    			v_scope_kdpst = Tool.removeDuplicateFromVector(v_scope_kdpst);
    		}
    	}
    	catch(Exception e) {}
    	return v_scope_kdpst;
    }
    
    public static Vector convertVscopeKdpstToDistinctInfoKdpst(Vector v_scope_kdpst, String target_tkn_nama_colom_dariTableMSPST_pake_koma_or_null_for_all_col) {
    	Vector v_list_info_kdpst = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	if(v_scope_kdpst!=null && v_scope_kdpst.size()>0) {
    		try {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//cretae NPM auto increment
        		if(Checker.isStringNullOrEmpty(target_tkn_nama_colom_dariTableMSPST_pake_koma_or_null_for_all_col)) {
        			stmt = con.prepareStatement("SELECT * FROM MSPST where KDPSTMSPST=?");
        		}
        		else {
        			stmt = con.prepareStatement("SELECT "+target_tkn_nama_colom_dariTableMSPST_pake_koma_or_null_for_all_col+" FROM MSPST where KDPSTMSPST=?");	
        		}
        		
        		v_list_info_kdpst = new Vector();
        		ListIterator lif = v_list_info_kdpst.listIterator();
    			ListIterator li = v_scope_kdpst.listIterator();
    			while(li.hasNext()) {
    				String baris = (String)li.next();
    				//System.out.println("baris spmi="+baris);
    				StringTokenizer st = new StringTokenizer(baris,"`");
    				st.nextToken(); //ignore kdkmp
    				while(st.hasMoreTokens()) {
    					String kdpst = st.nextToken();
    					stmt.setString(1, kdpst);
    					rs = stmt.executeQuery();
    					ResultSetMetaData rsmd = rs.getMetaData();
    					int columnsNumber = rsmd.getColumnCount();
    	        		String col_label = null;
    	        		for(int i=1;i<=columnsNumber;i++) {
    	    				String col_name = rsmd.getColumnName(i);
    	    				if(col_label==null) {
    	    					col_label = new String(col_name);
    	    				}
    	    				else {
    	    					col_label = col_label+"`"+col_name;
    	    				}
    	    			}
    	        		//if(v==null) {
    	    			//	v = new Vector();
    	    			//	li=v.listIterator();
    	    			//	li.add(col_label);
    	    			//}
    	    			
    	        		//System.out.println("columnsNumber="+columnsNumber);
    	        		String brs = null;
    	        		while(rs.next()) {
    	        			for(int i=1;i<=columnsNumber;i++) {
    	        				String tmp = "";
    	        				//int col_tipe = rsmd.getColumnType(i);
    	        				//if(col_tipe == Types.DATE)
    	        				String col_type = rsmd.getColumnTypeName(i);
    	        				
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
    	        						brs = new String("null");
    	        					}
    	        					else {
    	        						brs = new String(tmp);
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
    	        			lif.add(brs);
    	        			brs = null;
    	        		}
    				}
    			}
    			try {
    				v_list_info_kdpst = Tool.removeDuplicateFromVector(v_list_info_kdpst);	
    			}
    			catch(Exception e) {
    				//System.out.println("adda error di /ToUnivSatyagama/src/servlets/Router/RouteBasedOnScopeKdpst.java");
    				v_list_info_kdpst = new Vector();
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
    	
    	return v_list_info_kdpst;
    }
    
    public static Vector convertVscopeidToKdpst(Vector v_scope_id, String filter_based_on_kode_kampus) {
    	Vector v_scope_kdpst = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		v_scope_kdpst = new Vector();
    		ListIterator li1 = v_scope_id.listIterator();
    		ListIterator li2 = v_scope_kdpst.listIterator();
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//cretae NPM auto increment
        		stmt = con.prepareStatement("SELECT KDPST FROM OBJECT where ID_OBJ=? and KODE_KAMPUS_DOMISILI=?");
        		while(li1.hasNext()) {
        			String new_line = "";
        			String brs = (String)li1.next();
        			//System.out.println(brs);
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String kmp = st.nextToken();
        			new_line = new String(kmp);
        			while(st.hasMoreTokens()) {
        				String id = st.nextToken();
        				stmt.setInt(1,Integer.parseInt(id));
        				stmt.setString(2, filter_based_on_kode_kampus);
        				rs = stmt.executeQuery();
        				if(rs.next()) {
        					String kdpst = ""+rs.getString(1);
        					new_line = new_line+"`"+kdpst;
        				}
        			}
        			li2.add(new_line);
        			//System.out.println(new_line);
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
    
    

    
    public static String getNickKampus(String kdkmp) {
    	String nmkmp = null;
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
    		stmt = con.prepareStatement("SELECT NICKNAME_KAMPUS FROM KAMPUS where KODE_KAMPUS=?");
    		stmt.setString(1, kdkmp);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			nmkmp = new String(""+rs.getString(1));
    			if(Checker.isStringNullOrEmpty(nmkmp)) {
    				nmkmp = null;
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
    	return nmkmp;
    }

    public static String getNamaKampus(String kdkmp) {
    	String nmkmp = null;
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
    		stmt = con.prepareStatement("SELECT NAMA_KAMPUS FROM KAMPUS where KODE_KAMPUS=?");
    		stmt.setString(1, kdkmp);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			nmkmp = new String(""+rs.getString(1));
    			if(Checker.isStringNullOrEmpty(nmkmp)) {
    				nmkmp = null;
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
    	return nmkmp;
    }

    
    public static String getDetailKdpst_v1(String kdpst) {
    	String info = "null#&null";
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
    		stmt = con.prepareStatement("SELECT * FROM MSPST where KDPSTMSPST=?");
    		stmt.setString(1, kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String nmpst = ""+rs.getString("NMPSTMSPST");
    			String kdjen = ""+rs.getString("KDJENMSPST");
    			info = nmpst+" ("+getDetailKdjen(kdjen)+")";
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
    
    
    public static String printShift(String keter_shift, String kdpst) {
    	String masked_shift = "";
    	String kdjen = Checker.getKdjen(kdpst);
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	if(!Checker.isStringNullOrEmpty(keter_shift)) {
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//cretae NPM auto increment
        		stmt = con.prepareStatement("SELECT KODE_KONVERSI FROM SHIFT where KETERANGAN=? and TOKEN_KDJEN_AVAILABILITY like ?");
        		stmt.setString(1, keter_shift);
        		stmt.setString(2, "%"+kdjen+"%");
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			masked_shift = ""+rs.getString(1);
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
    	
    	return masked_shift;
    }
    
    public static String printKrklm(String str_idkur) {
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String keter = "";
    	
    	if(!Checker.isStringNullOrEmpty(str_idkur)) {
    		try {
    			int idkur = Integer.parseInt(str_idkur);
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//cretae NPM auto increment
        		stmt = con.prepareStatement("select * from KRKLM where IDKURKRKLM=?");
        		stmt.setInt(1, idkur);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			String nmkur = ""+rs.getString("NMKURKRKLM");
            		String start = ""+rs.getString("STARTTHSMS");
            		String end = ""+rs.getString("ENDEDTHSMS");
            		String stkur  = ""+rs.getString("STKURKRKLM");
            		String skstt  = ""+rs.getInt("SKSTTKRKLM");
            		String smstt  = ""+rs.getInt("SMSTTKRKLM");
            		
            		keter = nmkur+" ["+skstt+" sks / "+smstt+" sms] ";
            		if(stkur.equalsIgnoreCase("A")) {
            			keter = keter +"[AKTIF]";
            		}
            		else {
            			keter = keter +"[EXPIRED]";
            		}
        		}
    		}
    		catch(NumberFormatException e) {
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
    	return keter;
    }
    
    public static String printKetStatusPindahanOrBaru(String kode) {
    	String keterangan_status = "";
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	if(!Checker.isStringNullOrEmpty(kode)) {
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//cretae NPM auto increment
        		stmt = con.prepareStatement("SELECT VALUE FROM CONSTANT where KETERANGAN=?");
        		stmt.setString(1, "TIPE_MHS");
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			keterangan_status = ""+rs.getString(1);
        			StringTokenizer st = new StringTokenizer(keterangan_status,"`");
        			while(st.hasMoreTokens()) {
        				String tkn = st.nextToken();
        				if(tkn.startsWith(kode.toUpperCase()+"-")) {
        					st = new StringTokenizer(tkn,"-");
        					st.nextToken();
        					keterangan_status = st.nextToken();
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
    	
    	return keterangan_status;
    }
    
    public static String getNamaKdpst(String kdpst) {
    	String nmpst = null;
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
    		stmt = con.prepareStatement("SELECT * FROM MSPST where KDPSTMSPST=?");
    		stmt.setString(1, kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			nmpst = ""+rs.getString("NMPSTMSPST");
    			//String kdjen = ""+rs.getString("KDJENMSPST");
    			//info = nmpst+"#&"+kdjen;
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
    	return nmpst;
    }  
    
    public static String getNamaKdpstDanJenjang(String kdpst) {
    	String nmpst = null;
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
    		stmt = con.prepareStatement("SELECT KDJENMSPST,NMPSTMSPST FROM MSPST where KDPSTMSPST=?");
    		stmt.setString(1, kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			kdjen = ""+rs.getString("KDJENMSPST");
    			nmpst = ""+rs.getString("NMPSTMSPST");
    			kdjen = getDetailKdjen(kdjen);
    			nmpst = nmpst+" ["+kdjen+"]";
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
    	return nmpst;
    }  
    
    public static Vector getNamaKdpstDanJenjang(Vector v_kdpst) {
    	String nmpst = null;
    	String kdjen = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector v_tmp = null;
    	ListIterator li = null;
    	if(v_kdpst!=null && v_kdpst.size()>0) {
    		v_tmp = (Vector)v_kdpst.clone();
    		li = v_tmp.listIterator();
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//cretae NPM auto increment
        		stmt = con.prepareStatement("SELECT KDJENMSPST,NMPSTMSPST FROM MSPST where KDPSTMSPST=?");
        		while(li.hasNext()) {
        			String kdpst = (String)li.next();
        			stmt.setString(1, kdpst);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			kdjen = ""+rs.getString("KDJENMSPST");
            			nmpst = ""+rs.getString("NMPSTMSPST");
            			kdjen = getDetailKdjen(kdjen);
            			nmpst = nmpst+" ["+kdjen+"]";
            			li.set(nmpst);
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
    	return v_tmp;
    }  
    
    public static String getNamaKdpst(int idobj) {
    	String nmpst = null;
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
    		stmt = con.prepareStatement("SELECT * FROM MSPST inner join OBJECT on KDPSTMSPST=KDPST where ID_OBJ=?");
    		stmt.setInt(1, idobj);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			nmpst = new String(""+rs.getString("NMPSTMSPST"));
    			//String kdjen = ""+rs.getString("KDJENMSPST");
    			//info = nmpst+"#&"+kdjen;
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
    	return nmpst;
    }  
   
    
    public static String getKonsentrasiKurikulum(String idkur_str) {
    	/*
    	 * return null untuk yg ngga ada konsentrasi
    	 */
    	int idkur = Integer.parseInt(idkur_str);
    	String konsen = null;
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
    		stmt = con.prepareStatement("SELECT KONSENTRASI FROM KRKLM where IDKURKRKLM=?");
    		stmt.setInt(1, idkur);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			konsen = rs.getString(1);
    			//String kdjen = ""+rs.getString("KDJENMSPST");
    			//info = nmpst+"#&"+kdjen;
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
    	return konsen;
    }    
    
    public static String getNamaFakultas(String kdpst) {
    	String nmfak = null;
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
    		stmt = con.prepareStatement("SELECT NMFAKMSFAK FROM MSFAK inner join MSPST on KDFAKMSFAK=KDFAKMSPST where KDPSTMSPST=?");
    		stmt.setString(1, kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			nmfak = ""+rs.getString("NMFAKMSFAK");
    			//String kdjen = ""+rs.getString("KDJENMSPST");
    			//info = nmpst+"#&"+kdjen;
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
    	return nmfak;
    }     
    

    public static String getKdjen(String kdpst) {
    	String info = "null#&null";
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
    		stmt = con.prepareStatement("SELECT * FROM MSPST where KDPSTMSPST=?");
    		stmt.setString(1, kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			//String nmpst = ""+rs.getString("NMPSTMSPST");
    			String kdjen = ""+rs.getString("KDJENMSPST");
    			info = kdjen;
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
    
    
    public static String prepNumberString(String numberString) {
    	//remove white space
    	StringTokenizer st = new StringTokenizer(numberString);
    	return st.nextToken();
    }
    public static String convertStpid(String stpid) {
    	String[]tipe = Constants.getTipeCivitas();
    	boolean match = false;
    	for(int i=0;i<tipe.length && !match;i++) {
    		StringTokenizer st = new StringTokenizer(tipe[i]);
    		String val = st.nextToken();
    		String ket = st.nextToken();
    		if(val.equalsIgnoreCase(stpid)) {
    			match = true;
    			stpid = ket;
    		}
    	}
    	return stpid.toUpperCase();
    }

    
    public static int convertNamaBulanToInt(String nama_bulan) {
    	int bln = 0;
    	if(nama_bulan.equalsIgnoreCase("januari")||nama_bulan.equalsIgnoreCase("january")||nama_bulan.equalsIgnoreCase("jan")) {
    		bln=1;
    	}
    	if(nama_bulan.equalsIgnoreCase("feb")||nama_bulan.equalsIgnoreCase("februari")||nama_bulan.equalsIgnoreCase("february")||nama_bulan.equalsIgnoreCase("pebruari")) {
    		bln=2;
    	}
    	if(nama_bulan.equalsIgnoreCase("maret")||nama_bulan.equalsIgnoreCase("march")||nama_bulan.equalsIgnoreCase("mar")) {
    		bln=3;
    	}
    	if(nama_bulan.equalsIgnoreCase("april")||nama_bulan.equalsIgnoreCase("apr")) {
    		bln=4;
    	}
    	if(nama_bulan.equalsIgnoreCase("mei")||nama_bulan.equalsIgnoreCase("may")) {
    		bln=5;
    	}
    	if(nama_bulan.equalsIgnoreCase("jun")||nama_bulan.equalsIgnoreCase("juni")||nama_bulan.equalsIgnoreCase("jun")) {
    		bln=6;
    	}
    	if(nama_bulan.equalsIgnoreCase("jul")||nama_bulan.equalsIgnoreCase("july")||nama_bulan.equalsIgnoreCase("juli")) {
    		bln=7;
    	}
    	if(nama_bulan.equalsIgnoreCase("aug")||nama_bulan.equalsIgnoreCase("agustus")||nama_bulan.equalsIgnoreCase("august")) {
    		bln=8;
    	}
    	if(nama_bulan.equalsIgnoreCase("sept")||nama_bulan.equalsIgnoreCase("sep")||nama_bulan.equalsIgnoreCase("september")) {
    		bln=9;
    	}
    	if(nama_bulan.equalsIgnoreCase("okt")||nama_bulan.equalsIgnoreCase("oct")||nama_bulan.equalsIgnoreCase("oktober")||nama_bulan.equalsIgnoreCase("october")) {
    		bln=10;
    	}
    	if(nama_bulan.equalsIgnoreCase("nov")||nama_bulan.equalsIgnoreCase("november")||nama_bulan.equalsIgnoreCase("nopember")) {
    		bln=11;
    	}
    	if(nama_bulan.equalsIgnoreCase("desember")||nama_bulan.equalsIgnoreCase("december")) {
    		bln=12;
    	}
    	return bln;
    }
    /*
     * pindah ke Tool
     
    public static String gantikanSpecialChar(String brs) {
    	brs = brs.replace("&", "tandaDan");
    	brs = brs.replace("#","tandaPagar");
    	return brs;
    }
    
    public static String kembalikanSpecialChar(String brs) {
    	brs = brs.replace("tandaDan","&");
    	brs = brs.replace("tandaPagar","#");
    	return brs;
    }
    */
    public static String convertIntToNamaBulan(int month) {
    	String namaBulan = "";
    	if(month==1) {
    		namaBulan = "JANUARI";
    	}
    	if(month==2) {
    		namaBulan = "FEBRUARI";
    	}
    	if(month==3) {
    		namaBulan = "MARET";
    	}
    	if(month==4) {
    		namaBulan = "APRIL";
    	}
    	if(month==5) {
    		namaBulan = "MEI";
    	}
    	if(month==6) {
    		namaBulan = "JUNI";
    	}
    	if(month==7) {
    		namaBulan = "JULI";
    	}
    	if(month==8) {
    		namaBulan = "AGUSTUS";
    	}
    	if(month==9) {
    		namaBulan = "SEPTEMBER";
    	}
    	if(month==10) {
    		namaBulan = "OKTOBER";
    	}
    	if(month==11) {
    		namaBulan = "NOVEMBER";
    	}
    	if(month==12) {
    		namaBulan = "DESEMBER";
    	}
    	return namaBulan;
    }
    
    public static String convertFormatTanggalKeFormatDeskriptif(String tglToString) {
    	StringTokenizer st = new StringTokenizer(tglToString,"-");
    	String dd = "",mm="",yy="";
    	String tmp = "";
    	if(st.countTokens()==3) {
    		yy = st.nextToken();
    		mm = st.nextToken();
    		dd = st.nextToken();
    		int month = Integer.valueOf(mm).intValue();
    		int day = Integer.valueOf(dd).intValue();
    		mm=convertIntToNamaBulan(month);
    		mm=beans.tools.Tool.capFirstLetterInWord(mm);
    		tmp = dd+" "+mm+" "+yy;
    	}
    	return tmp;
    }
    
    public static String getObjLvlGiven(String kdpst) {
    	//fungsi ini mengambil objec lvl utk KDPST tertulis (1 kdpst bisa ada beberapa level)
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String tkn_obj_lvl = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM OBJECT where KDPST=?");
    		stmt.setString(1, kdpst);
    		rs = stmt.executeQuery();
    		tkn_obj_lvl = "";
    		while(rs.next()) {
    			tkn_obj_lvl = tkn_obj_lvl + rs.getInt("OBJ_LEVEL")+",";
    		}
    		if(tkn_obj_lvl.length()>0) {
    			tkn_obj_lvl = tkn_obj_lvl.substring(0,tkn_obj_lvl.length()-1);
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
    	return tkn_obj_lvl;	
    }
    
    public static String prepForInputTextToDb(String text) {
    	//convert return char \n -> <br/>;
    	text = text.replace("\n", "<br/>");
    	return text;
    }
    
    public static String reversePrepForInputTextToDb(String text) {
    	//convert return char \n -> <br/>;
    	text = text.replace("<br/>","\n");
    	return text;
    }
    
    public static Vector getPilihanShiftYgAktif(String kdjen) {
    	//fungsi ini mengambil objec lvl utk KDPST tertulis (1 kdpst bisa ada beberapa level)
    	Vector v = new Vector();
    	if(kdjen!=null) {
    		kdjen = kdjen.toLowerCase();
    		String url=null;     
    		Connection con=null;
    		PreparedStatement stmt=null;
    		ResultSet rs=null;
    		DataSource ds=null;
    		String tkn_obj_lvl = null;
    	
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		//cretae NPM auto increment
    			stmt = con.prepareStatement("SELECT * FROM SHIFT where AKTIF=?");
    			stmt.setBoolean(1, true);
    			rs = stmt.executeQuery();
    			ListIterator li = v.listIterator();
    			while(rs.next()) {
    				String ket = rs.getString("KETERANGAN");
    				//ket = ket.toUpperCase();
    				String shift = rs.getString("SHIFT");
    				String hari = rs.getString("HARI");
    				String tkn_kdjen = rs.getString("TOKEN_KDJEN_AVAILABILITY");
    				tkn_kdjen = tkn_kdjen.toLowerCase();
    				String konversi_kod = rs.getString("KODE_KONVERSI");
    				if(tkn_kdjen.contains(kdjen)) {
    					li.add(ket+"#&"+shift+"#&"+hari+"#&"+konversi_kod);
    				}	
    			}
    			Collections.sort(v);
    			li = v.listIterator();
    			String tmp = "";
    			while(li.hasNext()) {
    				String brs = (String) li.next();
    				if(brs.startsWith("N/A")||brs.startsWith("n/a")) {
    					tmp = ""+brs;
    					li.remove();
    				}
    			}
    			li.add(tmp);
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

    public static String getAlphabetUntukNorut(int norut) {
    	//A = 1
    	String huruf = null;
    	switch (norut) {
        	case 1:  huruf = "A";
                 break;
        	case 2:  huruf = "B";
            	break ; 
        	case 3:  huruf = "C";
        		break;
        	case 4:  huruf = "D";
        		break;
        	case 5:  huruf = "E";
        		break;
        	case 6:  huruf = "F";
        		break;
        	case 7:  huruf = "G";
        		break;
        	case 8:  huruf = "H";
        		break;
        	case 9:  huruf = "I";
        		break;
        	case 10:  huruf = "J";
        		break;
        	case 11:  huruf = "K";
        		break;
        	case 12:  huruf = "L";
        		break;
        	case 13:  huruf = "M";
        		break;
        	case 14:  huruf = "N";
        		break;
        	case 15:  huruf = "O";
        		break;
        	case 16:  huruf = "P";
        		break;
        	case 17:  huruf = "Q";
        		break;
        	case 18:  huruf = "R";
        		break;
        	case 19:  huruf = "S";
        		break;
        	case 20:  huruf = "T";
        		break;
        	case 21:  huruf = "U";
        		break;
        	case 22:  huruf = "V";
        		break;
        	case 23:  huruf = "W";
        		break;
        	case 24:  huruf = "X";
        		break;
        	case 25:  huruf = "Y";
        		break;
        	case 26:  huruf = "Z";
        		break;
        	default:  huruf = "null";
        		break;
    	}	
    	return huruf;
    }
    
    public static String getDateFromTimestamp(String timestamp_value) {
    	String tgl_section = null;
    	if(timestamp_value!=null && !Checker.isStringNullOrEmpty(timestamp_value)) {
    		StringTokenizer st = new StringTokenizer(timestamp_value);
    		tgl_section = st.nextToken();
    	}
    	if(tgl_section!=null && !Checker.isStringNullOrEmpty(tgl_section)) {
    		return formatDdSlashMmSlashYy(tgl_section);
    	}
    	else {
    		return null;
    	}
    	
    }
    
    public static String npmAlias() {
    	return "NPM";
    }
    
    public static String getAlias(String kode) {
    	String alias = null;
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
			stmt = con.prepareStatement("SELECT ALIAS FROM ALIAS where KODE=?");
			stmt.setString(1, kode);
			rs = stmt.executeQuery();
			if(rs.next()) {
				alias = new String(rs.getString(1));	
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
    	return alias;
    }
    
    public static java.util.Date parseDate(String date_or_datetime) {
        try {
        	date_or_datetime = date_or_datetime.replace("/", "-");
        	//seperate time
        	if(date_or_datetime.contains(" ")) {
        		StringTokenizer st = new StringTokenizer(date_or_datetime);
        		date_or_datetime = st.nextToken();
        	}
            return new SimpleDateFormat("yyyy-MM-dd").parse(date_or_datetime);
        } catch (Exception e) {
            return null;
        }
     }

    public static long getHowManyMenitAgo(java.util.Date date1) {
    	TimeUnit timeUnit = TimeUnit.MINUTES;
    	//System.out.println((new java.util.Date()).getTime());
        long diffInMillies = (new java.util.Date()).getTime() - date1.getTime();
        return TimeUnit.MINUTES.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
    public static long getHowManyHourAgo(java.util.Date date1) {
    	TimeUnit timeUnit = TimeUnit.HOURS;
        long diffInMillies = (new java.util.Date()).getTime() - date1.getTime();
        return TimeUnit.HOURS.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
    public static long getHowManyDayAgo(java.util.Date date1) {
    	TimeUnit timeUnit = TimeUnit.DAYS;
        long diffInMillies = (new java.util.Date()).getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
    
    
    public static String getHowLongAgo(String timestamp_format) {
    	float value=0;
    	String ext = "menit lalu";
    	String val="";
    	try {
    		DecimalFormat df2 = new DecimalFormat( "#,###,###,###" );
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	    //java d = sdf.parse("Mon May 27 11:46:15 IST 2013");
    	    java.util.Date dt_created = sdf.parse(timestamp_format);
        	value = getHowManyMenitAgo(dt_created);
        	//System.out.println("valuemn="+value);
        	
        	if(value>525948) {
        		ext = "tahun lalu";
        		value=value/525948;
        		value = new Float(df2.format(value)).floatValue();
        		val = value+" "+ext;
        	}
        	else if(value>43829) {
        		ext = "bulan lalu";
        		value=value/43829;
        		value = new Float(df2.format(value)).floatValue();
        		

        		val = value+" "+ext;
        	}
        	else if(value>10080) {
        		ext = "minggu lalu";
        		value=value/10080;
        		value = new Float(df2.format(value)).floatValue();
        		val = value+" "+ext;
        		
        	}
        	else if(value>1440) {
        		ext = "hari lalu";
        		value=value/1440;
        		value = new Float(df2.format(value)).floatValue();
        		val = value+" "+ext;
        		
        	}
        	else if(value>60) {
        		ext = "jam lalu";
        		value=value/60;
        		value = new Float(df2.format(value)).floatValue();
        		val = value+" "+ext;
        	}
        	else if(value>1) {
        		ext = "menit lalu";
        		val = value+" "+ext;
        		
        	}
        	else {
        		val = "baru saja";
        	}
    	}
    	catch(Exception e){}
    	
    	
       return val.replace(".0", "");
    }
    
    public static String prepStringForUrlPassing(String msg) {
    	/*
    	 * UPDATE DISINI UPDATE JUGA DI REST.CHECKER
    	 */
    	if(msg!=null) {
    		msg = msg.replace("\r\n", "%0A");
    		msg = msg.replace("\n", "%0A");
    		msg = msg.replace("\r", "%0A");
    		msg = msg.replace("@", "%40");
    		msg = msg.replace("\"", "%22");
    		msg = msg.replace(";", "%3B");
    		msg = msg.replace("`", "%60");
    		msg = msg.replace("#", "%23");
    		msg = msg.replace("%", "%25");
    	}
    	return msg;
    }
    
    public static String prepStringFromUrlPassingToScreen(String msg) {
    	if(msg!=null) {
    		msg = msg.replaceAll("%0A", "<br/>");
    		msg = msg.replaceAll("%40", "@");
    		msg = msg.replaceAll("%22", "\"");
    		msg = msg.replaceAll("%3B", ";");
    		msg = msg.replaceAll("%60", "`");
    		msg = msg.replaceAll("%23", "#");
    		msg = msg.replaceAll("%25", "%");
    		msg = msg.replaceAll("%0D", "");
    		msg = msg.replaceAll("%26", "&");
    		msg = msg.replaceAll("%2F", "/");
    		msg = msg.replaceAll("%3C", "<");
    		msg = msg.replaceAll("%3E", ">");
    		msg = msg.replaceAll("%5B", "[");
    		msg = msg.replaceAll("%5D", "]");
    		msg = msg.replaceAll("%7B", "{");
    		msg = msg.replaceAll("%7D", "}");
    		msg = msg.replaceAll("%7C", "|");
    		msg = msg.replaceAll("%5C", "\\");
    		msg = msg.replaceAll("%3F", "?");
    		msg = msg.replaceAll("%21", "!");
    		msg = msg.replaceAll("%24", "$");
    		msg = msg.replaceAll("%5E", "^");
    		msg = msg.replaceAll("%2B", "+");
    		msg = msg.replaceAll("%3D", "=");
    		msg = msg.replaceAll("%7E", "~");
    		msg = msg.replaceAll("%28", "(");
    		msg = msg.replaceAll("%29", ")");
    	//	msg = msg.replace("\n", "<br/>");
    	//	msg = msg.replace("\r", "<br/>");
    	}
    	return msg;
    }
    
    public static String convertDateToLocalStringFormat(java.sql.Date dt) {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	String value = null;
        try {
        	value = sdf.format(dt);
        } catch (Exception e) {}
        return value;
     }
    
    public static boolean cekInputTglValidity(String tgl) {
    	boolean valid = true;
    	try {
    		tgl = tgl.trim();
    		tgl = tgl.replace("\\", "/");
    		tgl = tgl.replace("-", "/");		
    		StringTokenizer st = new StringTokenizer(tgl,"/");
    		String dd = st.nextToken();
    		String mm = st.nextToken();
    		String thn = st.nextToken();
    		java.sql.Date test = java.sql.Date.valueOf(thn+"-"+mm+"-"+dd);
    		
    	}
    	catch(Exception e) {
    		valid = false;
    	}
    	return valid;
    }
    
    public static boolean cekInputWaktuValidity(String hh_mm) {
    	boolean valid = true;
    	try {
    		hh_mm = hh_mm.trim();
    		StringTokenizer st = new StringTokenizer(hh_mm,":");
    		String hh = st.nextToken();
    		String mm = st.nextToken();
    		int jam = Integer.parseInt(hh);
    		int mnt = Integer.parseInt(mm);
    		if(jam>24 || jam<0) {
    			valid = false;
    		}
    		if(mnt>60 || mnt<0) {
    			valid = false;
    		}
    	}
    	catch(Exception e) {
    		valid = false;
    	}
    	return valid;
    }
    
    public static boolean cekInputIntegerValidity(String target, String min, String max) {
    	boolean valid = true;
    	try {
    		target = target.trim();
    		int bilangan = Integer.parseInt(target);
    		if(!Checker.isStringNullOrEmpty(min)) {
    			if(bilangan<Integer.parseInt(min)) {
        			valid = false;
        		}	
    		}
    		if(!Checker.isStringNullOrEmpty(max)) {
    			if(bilangan>Integer.parseInt(max)) {
        			valid = false;
        		}	
    		}
    		
    	}
    	catch(Exception e) {
    		valid = false;
    	}
    	return valid;
    }
    
    public static boolean cekInputDoubleValidity(String target, String min, String max) {
    	boolean valid = true;
    	try {
    		target = target.trim();
    		double bilangan = Double.parseDouble(target);
    		if(!Checker.isStringNullOrEmpty(min)) {
    			if(bilangan<Double.parseDouble(min)) {
        			valid = false;
        		}	
    		}
    		if(!Checker.isStringNullOrEmpty(max)) {
    			if(bilangan>Double.parseDouble(max)) {
        			valid = false;
        		}	
    		}
    		
    	}
    	catch(Exception e) {
    		valid = false;
    	}
    	return valid;
    }
    
    public static Vector getListKdpst(Vector isu_getScopeObjScope_vFinal) {
    	Vector v_kdpst = null;
    	ListIterator li = null, lik=null;
    	if(isu_getScopeObjScope_vFinal!=null && isu_getScopeObjScope_vFinal.size()>0) {
    		//li.add(idobj+" "+kdpst+" "+objdes+" "+objlvl+" "+kdjen+" "+kmpdom);
    		v_kdpst=new Vector();
    		lik = v_kdpst.listIterator();
    		li = isu_getScopeObjScope_vFinal.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			//System.out.println(brs);
    			String kdpst = Tool.getTokenKe(brs, 2);
    			//System.out.println(kdpst);
    			lik.add(kdpst);
    		}
    		try {
    			v_kdpst = Tool.removeDuplicateFromVector(v_kdpst);	
    		}
    		catch(Exception e) {}
    		
    	}
    	return v_kdpst;
    }
    
    public static boolean cekInputStringValidity(String target, int min_length) {
    	boolean valid = true;
    	try {
    		target = target.trim();
    		if(target.length()<min_length) {
    			valid=false;
    		}
    	}
    	catch(Exception e) {
    		valid = false;
    	}
    	return valid;
    }
    
    public static boolean cekInputStringArrayValidity(String []target, int min_length) {
    	boolean valid = false;
    	try {
    		if(target!=null && target.length>0) {
    			if(min_length<1) {
    				//yg penting array not null, ngga pedili kosong
    				valid = true;
    			}
    			else {
    				for(int i=0;i<target.length&&!valid;i++) {
    					if(target[0]!=null&&target[0].length()>0) {
    						valid = true;
    					}
    				}
    			}
    		}
    		
    	}
    	catch(Exception e) {
    		valid = false;
    	}
    	return valid;
    }
    
    public static String autoConvertDateFormat(String inp_target_date, String ubah_format_pemisah) {
    	StringTokenizer st = null;
    	if(!Checker.isStringNullOrEmpty(inp_target_date)) {
    		if(ubah_format_pemisah.equalsIgnoreCase("/")) { //jadi format dd/mm/yyyy
    			if(inp_target_date.contains("-")) {
    				st = new StringTokenizer(inp_target_date,"-");
    				String thn = st.nextToken();
    				String bln = st.nextToken();
    				String tgl = st.nextToken();
    				inp_target_date = tgl+"/"+bln+"/"+thn;
    			}
    			else {
    				//ignore format sudah sesuai yg diinginkan
    			}
    		}
    		else if(ubah_format_pemisah.equalsIgnoreCase("-")) {
    			if(inp_target_date.contains("/")) {
    				st = new StringTokenizer(inp_target_date,"/");
    				String tgl = st.nextToken();
    				String bln = st.nextToken();
    				String thn = st.nextToken();
    				inp_target_date = thn+"-"+bln+"-"+tgl;
    			}
    			else {
    				//ignore format sudah sesuai yg diinginkan
    			}
    		}
    	}
    	return inp_target_date;
    }

    public static String convertTokenHurufPertamaBesar(String token, String char_pemisah) {
    	String tmp = null;
    	if(!Checker.isStringNullOrEmpty(token)) {
    		tmp = new String();
    		StringTokenizer st = null;
    		if(Checker.isStringNullOrEmpty(char_pemisah)) {
    			st = new StringTokenizer(token);
    		}
    		else {
    			st = new StringTokenizer(token,char_pemisah);
    		}
    		while(st.hasMoreTokens()) {
    			String word = st.nextToken();
    			tmp = tmp + word.substring(0, 1).toUpperCase()+word.substring(1,word.length()).toLowerCase();
    			if(st.hasMoreTokens()) {
    				tmp = tmp +" ";
    			}
    		}
    	}
    	return tmp;
    }
    
    public static String convertToDoubleDigit(double value) {
    	StringTokenizer stt = new StringTokenizer(""+value,".");
		String int_val = stt.nextToken();
		String dec_val = stt.nextToken();
		if(Double.parseDouble(dec_val)>0) {
			return ""+Math.round(value*100.0)/100.0;
		}
		else {
			return int_val;
		}
    	
    	
    }
    
    public static String convertToPercentage(double pembilang, double pembagi) {
    	double val=0;
    	if(pembagi>0) {
    		val = (pembilang/pembagi)*100;	
    	}
    	
    	return convertToDoubleDigit(val) ;
    	
    }

    
}

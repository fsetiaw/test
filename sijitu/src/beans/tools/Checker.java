package beans.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;

import beans.dbase.trnlm.SearchDbTrnlm;
import beans.setting.Constants;
import beans.sistem.AskSystem;
//import beans.tools.Checker;
import java.util.Vector;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.StringTokenizer;
import java.math.BigDecimal;
import javax.servlet.http.HttpSession;
/**
 * Session Bean implementation class Checker
 */
@Stateless
@LocalBean
public class Checker {

    /**
     * Default constructor. 
     */
    public Checker() {
        // TODO Auto-generated constructor stub
    }
/*   
    public boolean doIHaveAccesToThisEntity(Vector vScope,String targetObjectLevel,String iNpm,String targetNpm) {
    	boolean iDo = false;
    	ListIterator liScope = vScope.listIterator();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		if(vScope.size()==1) {
    			//cek apa only own = artinya boleh ngecek hanya punyaynya sendiri
    			String lvlScope = (String) liScope.next();
    			StringTokenizer st = new StringTokenizer(lvlScope);
    			if(st.countTokens()==1) {
    				String tkn = st.nextToken();
    				if(tkn.equalsIgnoreCase("own")) {
    					if(iNpm.equalsIgnoreCase(targetNpm)) {
    						iDo = true;
    					}
    					else {
    						iDo = false;
    					}
    				}
    				else {
    					
    				}
    			}
    			else {
    				//kalo bukan own  berarti mus be objek lvl
    				//if()
    			}
    		}
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		
    		con = ds.getConnection();

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
*/   
    /*
     * ada di GETTER
    
    public static String getObjKampusHomeBase(int id_obj) {
    	
    	String url=null, kode_kmp=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Long uid = null;
    	
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			
			stmt = con.prepareStatement("select KODE_KAMPUS_DOMISILI from OBJECT where ID_OBJ=?");
			stmt.setInt(1,id_obj);
			rs = stmt.executeQuery();
			kode_kmp = new String(""+rs.getString(1));
			
			
			
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
    	return kode_kmp;
    }	
     */
   
    public static boolean isTableRuleSdhDiisi(String thsms, String kdpst, String nama_table, String kmp_domisili) {
    	boolean sudah = false;;     
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
    		stmt = con.prepareStatement("SELECT TKN_VERIFICATOR_ID from "+nama_table+" where THSMS=? and KDPST=? and KODE_KAMPUS=?");
    		stmt.setString(1,thsms);
    		stmt.setString(2,kdpst);
    		stmt.setString(3,kmp_domisili);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String tkn = rs.getString(1);
    			if(!Checker.isStringNullOrEmpty(tkn)) {
    				sudah = true;
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
    	return sudah;
    }
    
    
    public static String getListPosisiJabatan() {
    	String tkn_posisi = null;     
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
    		stmt = con.prepareStatement("SELECT NM_LEVEL_JABATAN from JABATAN_LEVEL");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String tkn = rs.getString(1);
    			tkn_posisi = new String(tkn);
    			while(rs.next()) {
    				tkn_posisi = tkn_posisi+"~"+rs.getString(1);
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
    	return tkn_posisi;
    }
    
    public static Vector getListTipeKartuUjianAktif(String kdpst, String tkn_col) {
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
    		if(Checker.isStringNullOrEmpty(kdpst)) {
    			stmt = con.prepareStatement("SELECT distinct "+tkn_col+" from TIPE_KARTU_UJIAN");	
    		}
    		else {
    			stmt = con.prepareStatement("SELECT distinct "+tkn_col+" from TIPE_KARTU_UJIAN where KDPST=?");
    			stmt.setString(1, kdpst);
    		}
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String tmp = "";
    			StringTokenizer st = new StringTokenizer(tkn_col,",");
    			for(int i=0;i<st.countTokens();) {
    				String token = rs.getString(1);
    				tmp = tmp + token;
    				if(++i<st.countTokens()) {
    					tmp = tmp+"~";
    				}
    			}
    			if(v==null) {
    				v = new Vector();
    				li = v.listIterator();
    			}
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
    
    public static String getRootSharedFolder() {
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
    		stmt.setString(1,"FOLDER_BERSAMA");
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
    
    public static String getNmmhs(String npmhs) {
    	String nmmhs = null;;     
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
    		stmt = con.prepareStatement("SELECT NMMHSMSMHS FROM CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1,npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			nmmhs = new String(rs.getString(1)) ;
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
    	return nmmhs;
    }
    
    public static boolean showAngel() {
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	boolean show = false;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT ANGEL FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			show = rs.getBoolean(1);
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
    	return show;
    }
    /*/
     * DEPERCATED pake f() getKodeSmsAntaraYgDigunakan
     */
    public static int getTotSmsAntaraYgDigunakan(String thsms, String kdpst) {
    	/*
    	 * DEPREKATED : updated cek tot_sms_antara (1/2) dan Kode_sms_antara (A/B/AB)
    	 * 
    	 * KONSEP BARU CUKUP CEK kode
    	 */
    	int tot_sms_antara = 0;     
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
    		stmt = con.prepareStatement("SELECT TOT_SMS_ANTARA from RIWAYAT_SMS_ANTARA where TAHUN=? and KDPST=?");
    		stmt.setString(1,thsms.substring(0,4));
    		stmt.setString(2,kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			tot_sms_antara = rs.getInt(1);
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
    	return tot_sms_antara;
    }
    
    
    public static String getKodeSmsAntaraYgDigunakan(String thsms, String kdpst) {
    	/*
    	 * return value = A/B/AB
    	 * A = winter = setelah ganjil
    	 * B = summer = setelah genap
    	 */
    	String kode_sms_antara = null;     
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
    		stmt = con.prepareStatement("SELECT KODE_SMS_ANTARA from RIWAYAT_SMS_ANTARA where TAHUN=? and KDPST=?");
    		stmt.setString(1,thsms.substring(0,4));
    		stmt.setString(2,kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			kode_sms_antara = rs.getString(1);
    		}
    		if(Checker.isSmsPendek(kode_sms_antara)) {
    			kode_sms_antara=null;
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
    	return kode_sms_antara;
    }
    
    
    public static Long getClassUID(String kdkmk, String thsms, String npmMhs) {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Long uid = null;
    	
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			
			stmt = con.prepareStatement("select CLASS_POOL_UNIQUE_ID from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=?");
			stmt.setString(1,thsms);
			stmt.setString(2,npmMhs);
			stmt.setString(3,kdkmk);
			rs = stmt.executeQuery();
			if(rs.next()) {
				uid  = rs.getLong(1);
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
    	return uid;
    }
    
    public static long getGroupId(String group_nickname) {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	long uid = -1;
    	
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/CHITCHAT");
			con = ds.getConnection();
			
			stmt = con.prepareStatement("select GROUP_ID from STRUKTURAL_GROUP where GROUP_NICKNAME=?");
			stmt.setString(1,group_nickname);
			rs = stmt.executeQuery();
			if(rs.next()) {
				uid  = rs.getLong(1);
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
    	return uid;
    }
    
    
    public static String getKonsentrasiKurikulum(String idkur) {
    	
    	String konsen=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Long uid = null;
    	
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			
			stmt = con.prepareStatement("select KONSENTRASI from KRKLM where IDKURKRKLM=?");
			stmt.setInt(1,Integer.parseInt(idkur));
			
			rs = stmt.executeQuery();
			if(rs.next()) {
				konsen  = rs.getString(1);
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
    	return konsen;
    }
    
    public static String getKdpti() {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String kode = null;
    	
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			
			stmt = con.prepareStatement("select VALUE from CONSTANT where KETERANGAN=?");
			stmt.setString(1,"KODE_KAMPUS");
			rs = stmt.executeQuery();
			if(rs.next()) {
				kode  = new String(rs.getString(1));
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
    	return kode;
    }
    /*
    public static Vector returnScopeApprovee(String nama_table_rules) {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Long uid = null;
    	
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			
			stmt = con.prepareStatement("select DISTINCT TKN_JABATAN_VERIFICATOR from "+nama_table_rules);
			rs = stmt.executeQuery();
			if(rs.next()) {
				String tmp = "";
				do {
					tmp = tmp+rs.getString(1);
				}
				while(rs.next());
				if(tmp!=null && !Checker.isStringNullOrEmpty(tmp)) {
					tmp = tmp.replace("][", "`");
					tmp = tmp.replace("[", "`");
					tmp = tmp.replace("]", "`");
					Vector vtmp = new Vector();
					ListIterator li = vtmp.listIterator();
					StringTokenizer st= new StringTokenizer(tmp,"`");
					while(st.hasMoreTokens()) {
						li.add(st.nextToken());
					}
					//filter biar dapet distinct nama jabatan
					vtmp = Tool.removeDuplicateFromVector(vtmp);
					li = vtmp.listIterator();
					stmt = con.prepareStatement("select NAMA_JABATAN from JABATAN where SINGKATAN=? or NAMA_JABATAN=?");
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
    	return uid;
    }
    */	
    
    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
    
    
    public static boolean isStringNullOrEmpty(String word) {
    	boolean empty = true;
    	if(word!=null) {
    		word = word.trim();
    		if(!word.equalsIgnoreCase("null/") && !word.equalsIgnoreCase("null") && !word.equalsIgnoreCase("NULL") && word.trim().length()>0) {
				empty = false;
			}
    		
    		/*
    		if(st.countTokens()>0) {
    			String tkn = st.nextToken();
    			tkn = tkn.trim();
    			if(!tkn.equalsIgnoreCase("null") && !tkn.equalsIgnoreCase("NULL")) {
    				empty = false;
    			}
    		}
    		*/
    	}
    	//System.out.println("checker word="+word+" "+empty);
    	return empty;
    }

    public static boolean bilanganGanjilKah(String value) {
    	boolean benar = false;
    	BigDecimal bg1, bg2;

        bg1 = new BigDecimal(value);
        bg2 = new BigDecimal("2");

	// BigDecimal array bg stores result of bg1/bg2
        BigDecimal bg[] = bg1.divideAndRemainder(bg2);
        if(!bg[1].equals(BigDecimal.ZERO)) {
        	benar=true;
        }
        return benar;
    }
    /*
     * DEPRECATED PAKE VERSI 1
     */
    public static String pnn(String word) {//printnotnull
    	String tmp="";
    	if(word!=null && !Checker.isStringNullOrEmpty(word)) {
    		
    		StringTokenizer st = new StringTokenizer(word);
    		if(st.countTokens()>0) {
    			while(st.hasMoreTokens()) {
    				String tkn = st.nextToken();
    				if(tkn.equalsIgnoreCase("null")) {
    					tmp = tmp+"";
    				}
    				else {
    					tmp=tmp+tkn;
    				}
    				if(st.hasMoreTokens()) {
    					tmp = tmp+" ";
    				}
    			}
    			if(isStringNullOrEmpty(tmp)) {
    				word="";
    			}
    		}
    		else {
    			word="";
    		}
    	}
    	else {
    		word="";
    	}
    	
    	return word;
    }
    
    public static String pnn_v1(String word) {//printnotnull
    	String tmp="";
    	if(word!=null && !Checker.isStringNullOrEmpty(word)) {
    		
    		StringTokenizer st = new StringTokenizer(word);
    		if(st.countTokens()>0) {
    			while(st.hasMoreTokens()) {
    				String tkn = st.nextToken();
    				if(tkn.equalsIgnoreCase("null")) {
    					tmp = tmp+"";
    				}
    				else {
    					tmp=tmp+tkn;
    				}
    				if(st.hasMoreTokens()) {
    					tmp = tmp+" ";
    				}
    			}
    			if(isStringNullOrEmpty(tmp)) {
    				word="";
    			}
    		}
    		else {
    			word="";
    		}
    	}
    	else {
    		word="";
    	}
    	word = word.trim();
    	word = Tool.buatSatuSpasiAntarKata(word);
    	while(word.contains("``")) {
    		word = word.replace("``", "`null`");
		}	
    	return word;
    }   
    
    public static String pnn_asIs(String word) {//printnotnull
    	if(word==null) {
    		word="";
    	}
    	return word.trim();
    }
    
    public static String pnn_v1(String word, String ifnull) {//printnotnull
    	String tmp="";
    	if(word!=null && !Checker.isStringNullOrEmpty(word)) {
    		
    		StringTokenizer st = new StringTokenizer(word);
    		if(st.countTokens()>0) {
    			while(st.hasMoreTokens()) {
    				String tkn = st.nextToken();
    				if(tkn.equalsIgnoreCase("null")) {
    					tmp = tmp+"";
    				}
    				else {
    					tmp=tmp+tkn;
    				}
    				if(st.hasMoreTokens()) {
    					tmp = tmp+" ";
    				}
    			}
    			if(isStringNullOrEmpty(tmp)) {
    				word=new String(ifnull);
    			}
    		}
    		else {
    			word=new String(ifnull);
    		}
    	}
    	else {
    		word=new String(ifnull);
    	}
    	word = word.trim();
    	word = Tool.buatSatuSpasiAntarKata(word);
    	while(word.contains("``")) {
    		word = word.replace("``", "`null`");
		}	
    	return word;
    }   
    
    public static String pnn_web(String word, String ifnull) {//printnotnull
    	String tmp="";
    	if(word!=null && !Checker.isStringNullOrEmpty(word)) {
    		
    		StringTokenizer st = new StringTokenizer(word);
    		if(st.countTokens()>0) {
    			while(st.hasMoreTokens()) {
    				String tkn = st.nextToken();
    				if(tkn.equalsIgnoreCase("null")) {
    					tmp = tmp+"";
    				}
    				else {
    					tmp=tmp+tkn;
    				}
    				if(st.hasMoreTokens()) {
    					tmp = tmp+" ";
    				}
    			}
    			if(isStringNullOrEmpty(tmp)) {
    				word=new String(ifnull);
    			}
    		}
    		else {
    			word=new String(ifnull);
    		}
    		while(word.contains("\n")) {
        		word = word.replace("\n", "<br>");
    		}
    	}
    	else {
    		word=new String(ifnull);
    	}
    	word = word.trim();
    	word = Tool.buatSatuSpasiAntarKata(word);
    	while(word.contains("``")) {
    		word = word.replace("``", "`null`");
		}
    	
    	return word;
    }   
    
    public static String pnn(String word, String container) {//printnotnull
    	String tmp="";
    	if(word!=null && !Checker.isStringNullOrEmpty(word)) {
    		StringTokenizer st = new StringTokenizer(word);
    		if(st.countTokens()>0) {
    			while(st.hasMoreTokens()) {
    				String tkn = st.nextToken();
    				if(tkn.equalsIgnoreCase("null")) {
    					tmp = tmp+"";
    				}
    				else {
    					tmp=tmp+tkn;
    				}
    				if(st.hasMoreTokens()) {
    					tmp = tmp+" ";
    				}
    			}
    			if(isStringNullOrEmpty(tmp)) {
    				word="";
    			}
    			else {
    				if(!isStringNullOrEmpty(tmp) && container.length()>1) {
    					//berarti model braket
    					word = container.substring(0, 1)+word+container.substring(1, 2);
    				}
    				else if(!isStringNullOrEmpty(tmp) && container.length()==1) {
    					word = word+container;
    				}
    			}
    		}
    		else {
    			word="";
    		}
    	}
    	else {
    		word="";
    	}
    	return word;
    }

    
    public static String getThsmsPmb() {
    	String thsms =null;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT * FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			thsms = rs.getString("THSMS_PMB");
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
    	return thsms;	
    }
    
    
    public static String getThsmsPengajuanStmhs() {
    	
    	String thsms =null;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT THSMS_LULUS FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			thsms = rs.getString(1);
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
        return thsms;
    	//return getThsmsHeregistrasi();	
    }
    
    public static String getRootPathDir(String name_code) {
    	
    	String path=null;     
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
    		stmt = con.prepareStatement("SELECT REAL_ROOT_FOLDER FROM MASTER_ARSIP_TABLE where NAMA_ROOT_FOLDER=?");
    		stmt.setString(1, name_code);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			path = new String(rs.getString(1));
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
    
    
    public static String getBahanAjarTargetPath(String tipe_bahan_ajar, String kdpst, String kdkmk, String npm, String idkur) {
    	
    	String path=null;     
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
    		stmt = con.prepareStatement("SELECT PATH from KATEGORI_BAHAN_AJAR where TIPE=? and KDPST=?");
    		stmt.setString(1, tipe_bahan_ajar);
    		stmt.setString(2, kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			path = new String(rs.getString(1));
    			path = path.replace("kdpst", kdpst);
    			path = path.replace("kdkmk", kdkmk);
    			path = path.replace("idKur", idkur);
    			path = path.replace("npmDosen", npm);
    			
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

public static String getRootFolderIndividualMhs(String npmhs) {
    	
    	String path=null;     
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
    		stmt = con.prepareStatement("SELECT REAL_ROOT_FOLDER FROM MASTER_ARSIP_TABLE where NAMA_ROOT_FOLDER=?");
    		stmt.setString(1, "ARSIP MHS");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			path = new String(rs.getString(1))+"/"+npmhs;
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
    public static String getNamaPaketBeasiswa(int idpaket) {
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String nmmpaket = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM BEASISWA where IDPAKETBEASISWA=?");
    		stmt.setLong(1,idpaket);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			nmmpaket = rs.getString("NAMAPAKET");
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
    	return nmmpaket;	
    }
    
    public static String getListPaketBeasiswa() {
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String list = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM BEASISWA");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			int idpaket = rs.getInt("IDPAKETBEASISWA");
    			String nmmpaket = rs.getString("NAMAPAKET");
    			if(list==null) {
    				list = new String();
    			}
    			list = list +idpaket+"`"+nmmpaket+"`";
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
    	return list.substring(0,list.length()-1);	
    }
   
    public static boolean wajibDaftarUlangUntukIsiKrs() {
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	boolean value = false;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT REGISTRASI_FOR_KRS FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			value = rs.getBoolean(1);
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
    	return value;	
    }

    
    public static String getThsmsKrsWhiteList(String kdpst, String npmhs) {
    	String tkn_thsms =null;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT * FROM KRS_WHITE_LIST where KDPST=? and NPMHS=?");
    		stmt.setString(1,kdpst);
    		stmt.setString(2,npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			tkn_thsms = rs.getString("TOKEN_THSMS");
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
    	return tkn_thsms;	
    }
    
    public static boolean getIsOperatorAllowInsKrs() {
    	boolean allow =false;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT OPERATOR_ALLOW_INS_KRS FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			allow = rs.getBoolean("OPERATOR_ALLOW_INS_KRS");
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
    	return allow;	
    }
    
    public static boolean getIsOperatorAllowEditNilai() {
    	boolean allow =false;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT OPERATOR_ALLOW_EDIT_NILAI FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			allow = rs.getBoolean("OPERATOR_ALLOW_EDIT_NILAI");
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
    	return allow;	
    }
    
    public static boolean getIsOperatorAllowEditNilaiAllThsms() {
    	boolean allow =false;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT ALLOW_EDIT_NILAI_ALL_THSMS FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			allow = rs.getBoolean("ALLOW_EDIT_NILAI_ALL_THSMS");
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
    	return allow;	
    }
    
    public static String getThsmsHeregistrasi() {
    	String thsms =null;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT * FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			thsms = rs.getString("THSMS_HEREGISTRASI");
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
    	return thsms;	
    }
    
    public static String getThsmsInputNilai() {
    	String thsms =null;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT THSMS_INP_NILAI_MK FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			thsms = rs.getString("THSMS_INP_NILAI_MK");
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
    	return thsms;	
    }
    
    public static String getThsmsKrs() {
    	String thsms =null;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT * FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			thsms = rs.getString("THSMS_KRS");
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
    	return thsms;	
    }    
    /*
     * redundan
     
    public static Vector getListAvailShift(String kdjen) {
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
    		stmt = con.prepareStatement("select * from SHIFT where TOKEN_KDJEN_AVAILABILITY like ? and AKTIF=true order by KETERANGAN");
    		stmt.setString(1,"%"+kdjen+"%");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String desc = ""+rs.getString("KETERANGAN");
    			String shift = ""+rs.getString("SHIFT");
    			String hari = ""+rs.getString("HARI");
    			String value = ""+rs.getString("KODE_KONVERSI");
    			li.add(desc+","+shift+","+hari+","+value);
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
    */
    public static boolean isMhsAllowPengajuanKrs() {
    	boolean allow =false;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT MHS_ALLOW_PENGAJUAN_KRS,TGL_MULAI_PENGAJUAN_KRS,TGL_AKHIR_PENGAJUAN_KRS FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		java.sql.Date tmp = java.sql.Date.valueOf(AskSystem.getCurrentDate());
    		java.sql.Date sta = null;
    		java.sql.Date end = null;
    		if(rs.next()) {
    			//allow = rs.getBoolean("MHS_ALLOW_PENGAJUAN_KRS");
    			sta = rs.getDate("TGL_MULAI_PENGAJUAN_KRS");
    			end = rs.getDate("TGL_AKHIR_PENGAJUAN_KRS");
    			if(tmp.compareTo(sta)>=0 && tmp.compareTo(end)<=0) {
    				allow = true;
    			}
    			else {
    				allow = false;
    			}
    		}
    		//System.out.println("allow="+allow);
    		//update col mhs_allow = deprecated krn dulu blum pake range tanggal
    		stmt = con.prepareStatement("update CALENDAR set MHS_ALLOW_PENGAJUAN_KRS=? where AKTIF=?");
    		stmt.setBoolean(1, allow);
    		stmt.setBoolean(2, true);
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
    	return allow;	
    }  
    
    /*
     * kedepannya bisa ada
     * getAllowedKdpstPilihKelas, dst sampai kampus
     */
    public static String getAllowedShiftPilihKelas( String target_thsms, String npmhs, String target_kdpst) {
    	boolean allow =false;
    	long idObj = getObjectId(npmhs);
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String keterangan_shift = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * from PILIH_KELAS_RULES where THSMS=? and ID_OBJ_MHS=?");
    		stmt.setString(1,target_thsms);
    		stmt.setLong(2, idObj);
    		rs = stmt.executeQuery();
    		rs.next();
    		//boolean shift_only = rs.getBoolean("SHIFT_ONLY"); // shift only jgn dipake aja
    		boolean all_shift = rs.getBoolean("ALL_SHIFT");
    		boolean all_prodi = rs.getBoolean("ALL_PRODI");
    		boolean all_fak = rs.getBoolean("ALL_FAKULTAS");
    		boolean all_kmp = rs.getBoolean("ALL_KAMPUS");
    		String tkn_prodi = ""+rs.getString("TKN_PRODI");
    		String tkn_fak = ""+rs.getString("TKN_FAKULTAS");
    		String tkn_kmp = ""+rs.getString("TKN_KAMPUS");
    		String tkn_shift = ""+rs.getString("TKN_SHIFT"); //value =  KETERANGAN SHIFT pd table SHIFT
    		String npm_whitelist_shift = ""+rs.getString("NPMHS_WHITELIST_SHIFT");
    		String npm_whitelist_prodi = ""+rs.getString("NPMHS_WHITELIST_PRODI");
    		String npm_whitelist_fak = ""+rs.getString("NPMHS_WHITELIST_FAK");
    		String npm_whitelist_kmp = ""+rs.getString("NPMHS_WHITELIST_KMP");
    		if(all_shift && (npm_whitelist_shift!=null && npm_whitelist_shift.contains(npmhs))) {
    			
    			all_shift = false;
    			
    		}
    		else if(!all_shift && (npm_whitelist_shift!=null && npm_whitelist_shift.contains(npmhs))) {
    			all_shift = true;
    		}
    		
    		if(!all_shift) {
    			//berarti yg boleh shift pada profile mhs
    			keterangan_shift = new String(Checker.getShiftMhs(npmhs));
    		}
    		else {
    			if(tkn_shift==null || Checker.isStringNullOrEmpty(tkn_shift)) {
    				//berarti semua shift yg ada di table SHIFt = sesuai dgn kdjennya
    				keterangan_shift = new String("");
    				String kdjen = Checker.getKdjen(target_kdpst);
    				stmt = con.prepareStatement("select * from SHIFT where TOKEN_KDJEN_AVAILABILITY like ? and AKTIF=?");
    				stmt.setString(1, "%"+kdjen+"%");
    				stmt.setBoolean(2, true);
    				rs = stmt.executeQuery();
    				while(rs.next()) {
    					String keter = rs.getString("KETERANGAN");
    					keterangan_shift = keterangan_shift+keter+"`";
    				}
    			}
    			else {
    				//berarti yang diperbolehkan yg ada pada token shift
    				//value shitf disini = KETERANGN pada table shift
    				keterangan_shift = new String(tkn_shift);
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
    	return keterangan_shift;	
    }  
    
    

    
    public static String getThsmsBukaKelas() {
    	String thsms =null;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT * FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			thsms = rs.getString("THSMS_BUKA_KELAS");
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
    	return thsms;	
    }    
    
    public static int getSksmk(String idkmk) {
    	int sksmk = 0;
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
    		stmt = con.prepareStatement("SELECT * FROM MAKUL where IDKMKMAKUL=?");
    		stmt.setLong(1,Long.valueOf(idkmk).longValue());
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			sksmk = rs.getInt("SKSTMMAKUL")+rs.getInt("SKSPRMAKUL")+rs.getInt("SKSLPMAKUL");
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
    	return sksmk;	
    }
    
    
    public static boolean isUsrNameAvailable(String usrnm, String npm) {
    	//cek apa udah ada yg punya, kalaupun ada apa punya sendiri
    	boolean avail = false;
    	String id_usr = null;
    	Connection con = null;
    	ResultSet rs = null;
    	DataSource ds = null;
    	PreparedStatement stmt = null;
    	try {

    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		//ds = (DataSource)envContext.lookup("jdbc/USER");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT * FROM USR_DAT where USR_NAME=?");
    		stmt.setString(1, usrnm);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			id_usr = ""+rs.getInt("ID");
    		} 	
    		else {
    			avail = true;
    		}
    		if(id_usr!=null && !avail) {
    			String nonpm = "";
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select * from CIVITAS where ID=?");
        		stmt.setInt(1, Integer.valueOf(id_usr).intValue());
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			nonpm = rs.getString("NPMHSMSMHS");
        			if(nonpm.equalsIgnoreCase(npm)) {
        				avail=true;
        			}
        			else {
        				avail=false;
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
    	return avail;
    }
    
    public static String getThsmsNow() {
    	String thsms =null;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT * FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			thsms = rs.getString("THSMS");
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
    	return thsms;	
    }

    
    public static String getObjName(int idObj) {
    	//cek apa udah ada yg punya, kalaupun ada apa punya sendiri
    	String objName = null;
    	Connection con = null;
    	DataSource ds = null;
    	ResultSet rs = null;
    	PreparedStatement stmt = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		//ds = (DataSource)envContext.lookup("jdbc/USER");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT OBJ_NAME FROM OBJECT where ID_OBJ=?");
    		stmt.setLong(1, idObj);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			objName = ""+rs.getString("OBJ_NAME");
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
    	return objName;
    }
    
    public static String getObjDesc(int idObj) {
    	//cek apa udah ada yg punya, kalaupun ada apa punya sendiri
    	String objName = null;
    	Connection con = null;
    	DataSource ds = null;
    	ResultSet rs = null;
    	PreparedStatement stmt = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		//ds = (DataSource)envContext.lookup("jdbc/USER");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT OBJ_DESC FROM OBJECT where ID_OBJ=?");
    		stmt.setLong(1, idObj);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			objName = ""+rs.getString(1);
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
    	return objName;
    }
    /*
     * DEPRECATED - PAKE YG STRING NPMHS
     */
    public static String getObjNickname(int idObj) {
    	//cek apa udah ada yg punya, kalaupun ada apa punya sendiri
    	String objName = null;
    	ResultSet rs = null;
    	Connection con = null;
    	DataSource ds = null;
    	PreparedStatement stmt = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		//ds = (DataSource)envContext.lookup("jdbc/USER");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT OBJ_NICKNAME FROM OBJECT where ID_OBJ=?");
    		stmt.setLong(1, idObj);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			objName = ""+rs.getString("OBJ_NICKNAME");
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
    	return objName;
    }
    
   /* 
    public static String adaDiMoodle(String npmhs) {
    	//cek apa udah ada yg punya, kalaupun ada apa punya sendiri
    	String objName = null;
    	String pwd = null;
    	ResultSet rs = null;
    	Connection con = null;
    	DataSource ds = null;
    	PreparedStatement stmt = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc/MyMoodle");
    		//ds = (DataSource)envContext.lookup("jdbc/USER");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT * from mdl_user where username=?");
    		stmt.setString(1, npmhs+"_Usr");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			pwd = new String(rs.getString("password"));
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
    	return pwd;
    }
    */
    public static String getObjNickname(String npmhs) {
    	//cek apa udah ada yg punya, kalaupun ada apa punya sendiri
    	String objName = null;
    	long idObj = 0;
    	Connection con = null;
    	DataSource ds = null;
    	PreparedStatement stmt = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT OBJECT.ID_OBJ FROM OBJECT inner join CIVITAS on OBJECT.ID_OBJ=CIVITAS.ID_OBJ where NPMHSMSMHS=?");
    		stmt.setString(1,npmhs);
    		ResultSet rs = stmt.executeQuery();
    		if(rs.next()) {
    			idObj = rs.getLong(1);	
    			stmt = con.prepareStatement("SELECT OBJ_NICKNAME FROM OBJECT where ID_OBJ=?");
        		stmt.setLong(1, idObj);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			objName = ""+rs.getString("OBJ_NICKNAME");
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
    		if (con!=null) {
    			try {
    				con.close();
    			}
    			catch (Exception ignore) {
    				//System.out.println(ignore);
    			}
    		}
    	}
    	return objName;
    }
    
    public static String getCurPa(String npmhs) {
    	//cek apa udah ada yg punya, kalaupun ada apa punya sendiri
    	String infoCurPa = null;
    	Connection con = null;
    	DataSource ds = null;
    	PreparedStatement stmt = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		//ds = (DataSource)envContext.lookup("jdbc/USER");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from EXT_CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1, npmhs);
    		ResultSet rs = stmt.executeQuery();
    		if(rs.next()) {
    			infoCurPa = rs.getString("NPM_PA")+","+rs.getString("NMM_PA");
    		}
    	}
    	catch (NamingException e) {
    		e.printStackTrace();
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	} 
    	finally {
    		if (con!=null) {
    			try {
    				con.close();
    			}
    			catch (Exception ignore) {
    				//System.out.println(ignore);
    			}
    		}
    	}
    	return infoCurPa;
    }    
   
    public static String getListShift() {
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String tmp="";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM SHIFT where AKTIF=? order by KETERANGAN");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String uniqKeter = rs.getString("KETERANGAN");
    			String shift = rs.getString("SHIFT");
    			String hari = rs.getString("HARI");
    			String tkn_kdjen = rs.getString("TOKEN_KDJEN_AVAILABILITY");
    			String keterKonversi = rs.getString("KODE_KONVERSI");
    			
    			tmp = tmp + uniqKeter+"#"+shift+"#"+hari+"#"+tkn_kdjen+"#"+keterKonversi+"#";
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
    	return tmp;	
    }
    
    public static Vector getListShiftInVector() {
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String tmp="";
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM SHIFT where AKTIF=? order by KETERANGAN");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				String uniqKeter = rs.getString("KETERANGAN");
        			String shift = rs.getString("SHIFT");
        			String hari = rs.getString("HARI");
        			String tkn_kdjen = rs.getString("TOKEN_KDJEN_AVAILABILITY");
        			String keterKonversi = rs.getString("KODE_KONVERSI");
        			
        			tmp = uniqKeter+"#"+shift+"#"+hari+"#"+tkn_kdjen+"#"+keterKonversi+"#";
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
    
    
    public static String getShiftMhs(String npmhs) {
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String shift = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT SHIFTMSMHS FROM CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1,npmhs);
    		rs = stmt.executeQuery();
    		rs.next();
    		shift = rs.getString(1);
    		
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
    	return shift;	
    }
    
    public static long getObjectId(String npmhs) {
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	long idObj = -100;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT OBJECT.ID_OBJ FROM OBJECT inner join CIVITAS on OBJECT.ID_OBJ=CIVITAS.ID_OBJ where NPMHSMSMHS=?");
    		stmt.setString(1,npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			idObj = rs.getLong(1);	
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
    	return idObj;	
    }
    /*
     * return negative bila no match
     */
    public static long getObjectId(String kdpst, String kmp) {
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	long idObj = -100;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		if(kmp==null) {
    			stmt = con.prepareStatement("select ID_OBJ from OBJECT where KDPST=? order by ID_OBJ");
        		stmt.setString(1,kdpst);
        		
    		}
    		else {
    			stmt = con.prepareStatement("select ID_OBJ from OBJECT where KDPST=? and KODE_KAMPUS_DOMISILI=?");
        		stmt.setString(1,kdpst);
        		stmt.setString(2,kmp);	
    		}
    		
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			idObj = rs.getLong(1);	
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
    	return idObj;	
    }
    
    public static boolean isNpmInWhitelistShift(long id_obj, String target_npm) {
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	boolean value = false;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT NPMHS_WHITELIST_SHIFT FROM PILIH_KELAS_RULES where ID_OBJ_MHS=? ");
    		stmt.setLong(1,id_obj);
    		rs = stmt.executeQuery();
    		rs.next();
    		String tkn_shift = rs.getString(1);
    		if(tkn_shift.contains("ALL")||tkn_shift.contains("all")||tkn_shift.contains(target_npm)) {
    			value = true;
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
    	return value;	
    }
    
    public static String getListShift(String kdjen) {
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String tmp="";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("select * from SHIFT where TOKEN_KDJEN_AVAILABILITY like ? and AKTIF=true order by KETERANGAN");
    		//stmt = con.prepareStatement("SELECT * FROM SHIFT where AKTIF=? order by KETERANGAN");
    		stmt.setString(1,"%"+kdjen+"%");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String uniqKeter = rs.getString("KETERANGAN");
    			String shift = rs.getString("SHIFT");
    			String hari = rs.getString("HARI");
    			String tkn_kdjen = rs.getString("TOKEN_KDJEN_AVAILABILITY");
    			String keterKonversi = rs.getString("KODE_KONVERSI");
    			//if(tkn_kdjen.contains(kdjen)) {
    				tmp = tmp + uniqKeter+"#"+shift+"#"+hari+"#"+tkn_kdjen+"#"+keterKonversi+"#";
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
    	return tmp;	
    }    
 
    public static String getKdjen(String kdpst) {
    	//System.out.println("kdpst11="+kdpst);
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String kdjen="";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT KDJENMSPST FROM MSPST where KDPSTMSPST=?");
    		stmt.setString(1,kdpst);
    		rs = stmt.executeQuery();
    		rs.next();
    		kdjen = rs.getString("KDJENMSPST");
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
    	return kdjen;	
    } 
    
    public static String getKdjen(String kdpst, Connection con) {
    	//System.out.println("kdpst11="+kdpst);
    	//Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	//DataSource ds=null;
    	String kdjen="";
    	try {
    		//Context initContext  = new InitialContext();
    		//Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		//ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		//con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT KDJENMSPST FROM MSPST where KDPSTMSPST=?");
    		stmt.setString(1,kdpst);
    		rs = stmt.executeQuery();
    		rs.next();
    		kdjen = rs.getString("KDJENMSPST");
    	} 
        //catch (NamingException e) {
        //	e.printStackTrace();
        //}
        catch (SQLException ex) {
        	ex.printStackTrace();
        } 
        finally {
        	if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    //if (con!=null) try { con.close();} catch (Exception ignore){}
        }
    	return kdjen;	
    } 
    
    public static Boolean isAllowRequestBukaKelas(String kdpst) {
    	String thsmsPmb = getThsmsPmb();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	boolean boleh=false;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM CALENDAR inner join CALENDAR_BUKA_KELAS on CALENDAR.ID=CALENDAR_BUKA_KELAS.ID_CALENDAR where AKTIF=? and CALENDAR_BUKA_KELAS.KDPST=?");
    		stmt.setBoolean(1,true);
    		stmt.setString(2, kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			boleh = rs.getBoolean("ALLOW_REQ_KELAS");
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
    	return boleh;	
    }     
    
    
    /*
     * DEPRECATED Getter.getListDosen_v1
     */
    public static Vector getListDosenPengajar(String tkn_kdpst_sperate_by_koma_OR_set_bull_for_no_kdpst) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	//System.out.println("kdpst11=="+kdpst);
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		String sql = "SELECT A.*,B.* FROM EXT_CIVITAS_DATA_DOSEN A " + 
    				"left join MSDOS B " + 
    				" on (NODOS_LOCAL=NODOS OR NIDNN=NIDN) " + 
    				" where A.KDPST_HOMEBASE=? or TKN_KDPST_TEACH like ?";
    		if(Checker.isStringNullOrEmpty(tkn_kdpst_sperate_by_koma_OR_set_bull_for_no_kdpst)) {
    			sql = "SELECT A.*,B.* FROM EXT_CIVITAS_DATA_DOSEN A " + 
        				"left join MSDOS B " + 
        				" on (NODOS_LOCAL=NODOS OR NIDNN=NIDN) ";
    		}
    		else {
    			StringTokenizer st = new StringTokenizer(tkn_kdpst_sperate_by_koma_OR_set_bull_for_no_kdpst,",");
    			String scope = "(";
    			while(st.hasMoreTokens()) {
    				String kdpst = st.nextToken();
    				scope=scope+"A.KDPST_HOMEBASE='"+kdpst+"' or TKN_KDPST_TEACH like '%"+kdpst+"%'";
    				if(st.hasMoreTokens()) {
    					scope = scope+" OR ";
    				}
    			}
    			scope = scope+")";
    			sql = sql + " where "+scope;
    		}
    		//stmt = con.prepareStatement("SELECT * FROM MSDOS where KDPST_HOMEBASE=? or TKN_KDPST_TEACH like ?");
    		stmt = con.prepareStatement(sql);
    		//if(!Checker.isStringNullOrEmpty(kdpst_set_bull_for_no_kdpst)) {
    		//	stmt.setString(1,kdpst_set_bull_for_no_kdpst);
        	//	stmt.setString(2,"%"+kdpst_set_bull_for_no_kdpst+"%");	
    		//}
    		
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String nodos = ""+rs.getString("NODOS");
    			if(Checker.isStringNullOrEmpty(nodos)) {
    				nodos = "null";
    			}
    			String nidn = ""+rs.getString("NIDN");
    			if(Checker.isStringNullOrEmpty(nidn)) {
    				nidn = "null";
    			}
    			String noKtp = ""+rs.getString("NO_KTP");
    			if(Checker.isStringNullOrEmpty(noKtp)) {
    				noKtp = "null";
    			}
    			String kdptiHome = ""+rs.getString("A.KDPTI_HOMEBASE");
    			if(Checker.isStringNullOrEmpty(kdptiHome)) {
    				kdptiHome = "null";
    			}
    			String kdpstHome = ""+rs.getString("A.KDPST_HOMEBASE");
    			if(Checker.isStringNullOrEmpty(kdpstHome)) {
    				kdpstHome = "null";
    			}
    			String nmdos = ""+rs.getString("NMDOS");
    			if(Checker.isStringNullOrEmpty(nmdos)) {
    				nmdos = "null";
    			}
    			String gelar = ""+rs.getString("GELAR");
    			if(Checker.isStringNullOrEmpty(gelar)) {
    				gelar = "null";
    			}
    			String smawl = ""+rs.getString("SMAWL");
    			if(Checker.isStringNullOrEmpty(smawl)) {
    				smawl = "null";
    			}
    			String kdpstAjar = ""+rs.getString("TKN_KDPST_TEACH");
    			if(Checker.isStringNullOrEmpty(kdpstAjar)) {
    				kdpstAjar = "null";
    			}
    			String email = ""+rs.getString("EMAIL");
    			if(Checker.isStringNullOrEmpty(email)) {
    				email = "null";
    			}
    			String tknTelp = ""+rs.getString("TKN_TELP");
    			if(Checker.isStringNullOrEmpty(tknTelp)) {
    				tknTelp = "null";
    			}
    			String tknHp = ""+rs.getString("TKN_HP");
    			if(Checker.isStringNullOrEmpty(tknHp)) {
    				tknHp = "null";
    			}
    			String status = ""+rs.getString("STATUS");
    			if(Checker.isStringNullOrEmpty(status)) {
    				status = "null";
    			}
    			String npmhs = ""+rs.getString("NPMHS");
    			if(Checker.isStringNullOrEmpty(npmhs)) {
    				npmhs = "null";
    			}
    			String tmp = nodos+"||"+nidn+"||"+noKtp+"||"+kdptiHome+"||"+kdpstHome+"||"+nmdos+"||"+gelar+"||"+smawl+"||"+kdpstAjar+"||"+email+"||"+tknTelp+"||"+tknHp+"||"+status+"||"+npmhs;
    			//System.out.println("tmpo="+tmp);
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
    
    public static String getObjidKdpstDosen(String nmpst_utk_dosen) {
    	String objid_kdpst = "";
    	
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	//System.out.println("kdpst11=="+kdpst);
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("SELECT KDPSTMSPST,ID_OBJ FROM MSPST inner join OBJECT on KDPSTMSPST=KDPST where NMPSTMSPST=?");
    		stmt.setString(1, nmpst_utk_dosen);
    		rs = stmt.executeQuery();
    		rs.next();
    		objid_kdpst = rs.getLong(2)+"-"+rs.getString(1);
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
    	return objid_kdpst;	
    }     
  
    public static String[] getOptKeterMakul(String[]infoKelasDosen,String[]infoKelasMhs,String kdpst) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	if(infoKelasDosen!=null && infoKelasDosen.length>0) {
    	//System.out.println("kdpst11=="+kdpst);
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		
    			stmt = con.prepareStatement("SELECT OPTKETER FROM MAKUL where IDKMKMAKUL=?");
    			for(int i=0;i<infoKelasDosen.length;i++) {
    				String tmp  = infoKelasDosen[i];
    				StringTokenizer st = new StringTokenizer(tmp,"||");
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String shift = st.nextToken();
    				String idkmk = st.nextToken();
    				String norut = st.nextToken();
    				String nodos = st.nextToken();
    				String nmdos = st.nextToken();
    				stmt.setString(1, idkmk);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					String optKeter = ""+rs.getString("OPTKETER");
    					if(!isStringNullOrEmpty(optKeter)) {
    						infoKelasDosen[i]=infoKelasDosen[i]+"||yesketer||"+optKeter;
    					}
    					else {
    						infoKelasDosen[i]=infoKelasDosen[i]+"||noketer||null||"+infoKelasMhs[i];
    					}
    				}
    				else {
    					infoKelasDosen[i]=infoKelasDosen[i]+"||noketer||null||"+infoKelasMhs[i];
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
    	return infoKelasDosen;	
    }     
    
    /*
     * depricated - ganti pake kodeKampus
     */
    public static String getRuleBukaKelas(String kdpst) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String thsmsPmb = Checker.getThsmsBukaKelas();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String tmp = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("select * from CLASS_POOL_RULES where THSMS=? and KDPST=?");
    		stmt.setString(1, thsmsPmb);
    		stmt.setString(2, kdpst);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String tknVerificator = ""+rs.getString("TKN_VERIFICATOR");
    			StringTokenizer st = new StringTokenizer(tknVerificator,",");
    			tmp=thsmsPmb+"||"+kdpst+"||"+tknVerificator+"||"+st.countTokens();
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
    	return tmp;	
    }
    
    public static Vector getRuleDaftarUlang_deprecated_ganti_tabel(String target_thsms) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String tmp = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("select * from DAFTAR_ULANG_RULES where THSMS=? order by KDPST,KODE_KAMPUS");
    		stmt.setString(1, target_thsms);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdpst = ""+rs.getString("KDPST");
    			String tknVerificatorNickname = ""+rs.getString("TKN_VERIFICATOR");
    			boolean urut = rs.getBoolean("URUTAN");
    			String kdkmp = ""+rs.getString("KODE_KAMPUS");
    			li.add(kdpst+"`"+kdkmp+"`"+tknVerificatorNickname+"`"+urut);
    			
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
    
    public static String getRuleBukaKelas(String kdpst, String kodeKampus) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String thsmsPmb = Checker.getThsmsBukaKelas();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String tmp = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("select * from CLASS_POOL_RULES where THSMS=? and KDPST=? and KODE_KAMPUS=?");
    		stmt.setString(1, thsmsPmb);
    		stmt.setString(2, kdpst);
    		stmt.setString(3, kodeKampus);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String tknVerificator = ""+rs.getString("TKN_VERIFICATOR");
    			StringTokenizer st = new StringTokenizer(tknVerificator,",");
    			tmp=thsmsPmb+"||"+kdpst+"||"+tknVerificator+"||"+st.countTokens();
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
    	return tmp;	
    }     
    
    
    
    public static String getNickNameKampus(String kodeKampus) {
    	
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String nickname = null;
    	if(kodeKampus==null || Checker.isStringNullOrEmpty(kodeKampus)) {
    		nickname = kodeKampus+"`DATA ERROR";
    	}
    	else {
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		stmt = con.prepareStatement("select NICKNAME_KAMPUS from KAMPUS where KODE_KAMPUS=?");
        		stmt.setString(1, kodeKampus);
        		rs = stmt.executeQuery();
        		rs.next();
        		nickname = kodeKampus+"`"+rs.getString("NICKNAME_KAMPUS");
        		
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
    		
    	return nickname;	
    }    
    
    
    public static Vector getNickNameKampus(Vector vKodeKampus) {
    	
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String nickname = null;
    	if(vKodeKampus!=null && vKodeKampus.size()>0) {
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		stmt = con.prepareStatement("select NICKNAME_KAMPUS from KAMPUS where KODE_KAMPUS=?");
        		ListIterator li = vKodeKampus.listIterator();
        		while(li.hasNext()) {
        			String kodeKampus = (String)li.next();
        			//System.out.println("kodeKampus="+kodeKampus);
        			stmt.setString(1, kodeKampus);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			nickname = kodeKampus+"`"+rs.getString("NICKNAME_KAMPUS");	
            		}
            		else {
            			nickname = kodeKampus+"`DATA ERROR";
            		}
            		li.set(nickname);
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
    		
    	return vKodeKampus;	
    }    
    
    public static Vector addNakmkAndSemesToVbk(Vector vBk) {
    	ListIterator liBk = vBk.listIterator();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String tmp = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		//stmt = con.prepareStatement("select * from MAKUL where IDKMKMAKUL=?");
    		stmt = con.prepareStatement("select * from MAKUL inner join MAKUR on IDKMKMAKUL=IDKMKMAKUR where IDKMKMAKUL=? and IDKURMAKUR=?");
			while(liBk.hasNext()) {
				boolean match = false;
				String brs = (String)liBk.next();
				//System.out.println(">>"+brs);
				StringTokenizer st1 = new StringTokenizer(brs,"||");
				//System.out.println("countToekns >"+st1.countTokens());
				/*
				 * dibutuhkan untuk fungsi go back
				 */
				//System.out.println("checker tokens = "+st1.countTokens());
				if(st1.countTokens()==34) {
					String cmd1 = st1.nextToken();
					String idkur1 = st1.nextToken();
					String idkmk1 = st1.nextToken();
    	    		String thsms1 = st1.nextToken();
    	    		String kdpst1 = st1.nextToken();
    	    		String shift1 = st1.nextToken();
    	    		String noKlsPll1 = st1.nextToken();
    	    		String initNpmInput1 = st1.nextToken();
    	    		String latestNpmUpdate1 = st1.nextToken();
    	    		String latestStatusInfo1 = st1.nextToken();
    	    		String locked1 = st1.nextToken();
    	    		String npmdos1 = st1.nextToken();
    	    		String nodos1 = st1.nextToken();
    	    		String npmasdos1 = st1.nextToken();
    	    		String noasdos1 = st1.nextToken();
    	    		String cancel = st1.nextToken();
    	    		String kodeKelas1 = st1.nextToken();
    	    		String kodeRuang1 = st1.nextToken();
    	    		String kodeGedung1 = st1.nextToken();
    	    		String kodeKampus1 = st1.nextToken();
    	    		String tknHrTime1 = st1.nextToken();
    	    		String nmmdos1 = st1.nextToken();
    	    		String nmmasdos1 = st1.nextToken();
    	    		String enrolled1 = st1.nextToken();
    	    		String maxEnrol1 = st1.nextToken();
    	    		String minEnrol1 = st1.nextToken();
    	    		String subKeterMk1 = st1.nextToken();
    	    		String initReqTime1 = st1.nextToken();
    	    		String tknNpmApproval1 = st1.nextToken();
    	    		String tknApprovalTime1 = st1.nextToken();
    	    		String targetTotMhs1 = st1.nextToken();
    	    		String passed1 = st1.nextToken();
    	    		String rejected1 = st1.nextToken();
    	    		String konsen1 = st1.nextToken();
    	    		//System.out.println("idkmk1,idkur1="+idkmk1+","+idkur1);
					stmt.setLong(1,Long.valueOf(idkmk1).longValue());
					stmt.setLong(2,Long.valueOf(idkur1).longValue());
					rs = stmt.executeQuery();
					rs.next();
					String nakmk = rs.getString("NAKMKMAKUL");
					String smsmk = rs.getString("SEMESMAKUR");
					liBk.set(nakmk+"||"+smsmk+"||"+brs);
				}
				else {
					tmp = "";
					String nakmk1 = st1.nextToken();
					String smsmk1 = st1.nextToken();
					String cmd1 = st1.nextToken();
					tmp = tmp+cmd1+"||";
					String idkur1 = st1.nextToken();
					tmp = tmp+idkur1+"||";
					String idkmk1 = st1.nextToken();
					tmp = tmp+idkmk1+"||";
					String thsms1 = st1.nextToken();
					tmp = tmp+thsms1+"||";
					String kdpst1 = st1.nextToken();
					tmp = tmp+kdpst1+"||";
					String shift1 = st1.nextToken();
					tmp = tmp+shift1+"||";
					String noKlsPll1 = st1.nextToken();
					tmp = tmp+noKlsPll1+"||";
					String initNpmInput1 = st1.nextToken();
					tmp = tmp+initNpmInput1+"||";
					String latestNpmUpdate1 = st1.nextToken();
					tmp = tmp+latestNpmUpdate1+"||";
					String latestStatusInfo1 = st1.nextToken();
					tmp = tmp+latestStatusInfo1+"||";
					
					String locked1 = st1.nextToken();
					tmp = tmp+locked1+"||";
					
					String npmdos1 = st1.nextToken();
					tmp = tmp+npmdos1+"||";
					String nodos1 = st1.nextToken();
					tmp = tmp+nodos1+"||";
					String npmasdos1 = st1.nextToken();
					tmp = tmp+npmasdos1+"||";
					String noasdos1 = st1.nextToken();
					tmp = tmp+noasdos1+"||";
					String cancel = st1.nextToken();
					tmp = tmp+cancel+"||";
					
					String kodeKelas1 = st1.nextToken();
					tmp = tmp+kodeKelas1+"||";
					String kodeRuang1 = st1.nextToken();
					tmp = tmp+kodeRuang1+"||";
					String kodeGedung1 = st1.nextToken();
					tmp = tmp+kodeGedung1+"||";
					String kodeKampus1 = st1.nextToken();
					tmp = tmp+kodeKampus1+"||";
					String tknHrTime1 = st1.nextToken();
					tmp = tmp+tknHrTime1+"||";
					String nmmdos1 = st1.nextToken();
					tmp = tmp+nmmdos1+"||";
					String nmmasdos1 = st1.nextToken();
					tmp = tmp+nmmasdos1+"||";
					String enrolled1 = st1.nextToken();
					tmp = tmp+enrolled1+"||";
					String maxEnrol1 = st1.nextToken();
					tmp = tmp+maxEnrol1+"||";
					String minEnrol1 = st1.nextToken();
					tmp = tmp+minEnrol1+"||";
					String subKeterMk1 = st1.nextToken();
					tmp = tmp+subKeterMk1+"||";
					String initReqTime1 = st1.nextToken();
					tmp = tmp+initReqTime1+"||";
					String tknNpmApproval1 = st1.nextToken();
					tmp = tmp+tknNpmApproval1+"||";
					
					String tknApprovalTime1 = st1.nextToken();
					tmp = tmp+tknApprovalTime1+"||";
					
					String targetTotMhs1 = st1.nextToken();
					tmp = tmp+targetTotMhs1+"||";;
					
					String passed1 = st1.nextToken();
					tmp = tmp+passed1+"||";
					String rejected1 = st1.nextToken();
					tmp = tmp+rejected1+"||";
					String konsen1 = st1.nextToken();
					tmp = tmp+konsen1;
					//System.out.println("idkmk1,idkur1="+idkmk1+","+idkur1);
					stmt.setLong(1,Long.valueOf(idkmk1).longValue());
					stmt.setLong(2,Long.valueOf(idkur1).longValue());
					rs = stmt.executeQuery();
					rs.next();
					String nakmk = rs.getString("NAKMKMAKUL");
					String smsmk = rs.getString("SEMESMAKUR");
					liBk.set(nakmk+"||"+smsmk+"||"+tmp);
				}
			}
			
			//stmt = con.prepareStatement("select * from MAKUR where IDKMKMAKUR=? && ");
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
    	return vBk;	
    }     
    
    public static String sudahDaftarUlang(String kdpst, String npmhs) {
    	/*
    	 * artinya sudah di approved oleh semua pihak
    	 */
    	String pesan = null;
    	//boolean status = false;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String nickname = null;
    	boolean all_approved = false;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String thsms_heregistrasi = getThsmsHeregistrasi(); 	
        	stmt = con.prepareStatement("SELECT ALL_APPROVED FROM DAFTAR_ULANG where THSMS=? and NPMHS=?");
        	stmt.setString(1,thsms_heregistrasi);
        	stmt.setString(2, npmhs);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		all_approved = rs.getBoolean(1);
        		if(all_approved) {
        			pesan = null;
        		}
        		else {
        			pesan = new String("SEDANG DALAM PROSES");
        		}
        	}
        	else {
        		pesan = "HARAP MELAKUKAN PENDAFTARAN ULANG TERLEBIH DAHULU";
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
    	return pesan;	
    }
    
    
    public static boolean sudahMengajukanDaftarUlang(String kdpst, String npmhs) {
    	/*
    	 * artinya npm sudah ada di tabel daftar ulang
    	 */
    	boolean sudah = false;
    	//boolean status = false;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String nickname = null;
    	boolean all_approved = false;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String thsms_heregistrasi = getThsmsHeregistrasi(); 	
        	stmt = con.prepareStatement("SELECT NPMHS FROM DAFTAR_ULANG where THSMS=? and NPMHS=?");
        	stmt.setString(1,thsms_heregistrasi);
        	stmt.setString(2, npmhs);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		sudah = true;
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
    	return sudah;	
    }
    
    public static String sudahDaftarUlang(String kdpst, String npmhs, String target_thsms) {
    	/*
    	 * FUNGSI INI HANYA DIPAKE 
    	 * artinya sudah di approved oleh semua pihak
    	 */
    	String pesan = null;
    	//boolean status = false;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String nickname = null;
    	boolean all_approved = false;
    	boolean show = false;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//String thsms_heregistrasi = getThsmsHeregistrasi(); 	
        	stmt = con.prepareStatement("SELECT ALL_APPROVED,SHOW_AT_CREATOR FROM DAFTAR_ULANG where THSMS=? and NPMHS=?");
        	stmt.setString(1,target_thsms);
        	stmt.setString(2, npmhs);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		all_approved = rs.getBoolean(1);
        		show = rs.getBoolean(2);
        		
        		if(all_approved) {
        			pesan = new String(all_approved+"`"+show+"`DONE");
        		}
        		else {
        			pesan = new String(all_approved+"`"+show+"`SEDANG DALAM PROSES");
        		}
        	}
        	else {
        		pesan = "HARAP MELAKUKAN PENDAFTARAN ULANG TERLEBIH DAHULU";
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
    	return pesan;	
    }
    
    public static String getTglPengajuanDaftarUlang(String kdpst, String npmhs, String target_thsms) {
    	/*
    	 * FUNGSI INI HANYA DIPAKE 
    	 * artinya sudah di approved oleh semua pihak
    	 */
    	String pesan = null;
    	//boolean status = false;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String nickname = null;
    	boolean all_approved = false;
    	boolean show = false;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//String thsms_heregistrasi = getThsmsHeregistrasi(); 	
        	stmt = con.prepareStatement("SELECT TGL_PENGAJUAN FROM DAFTAR_ULANG where THSMS=? and NPMHS=?");
        	stmt.setString(1,target_thsms);
        	stmt.setString(2, npmhs);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		pesan = ""+rs.getTimestamp(1);
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
    	return pesan;	
    }
    
    public static String sudahDaftarUlang_deprecated(String kdpst, String npmhs) {
    	/*
    	 * artinya sudah di approved oleh semua pihak
    	 */
    	String pesan = null;
    	//boolean status = false;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String nickname = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String thsms_heregistrasi = getThsmsHeregistrasi(); 	
        	stmt = con.prepareStatement("SELECT * FROM DAFTAR_ULANG where THSMS=? and NPMHS=?");
        	stmt.setString(1,thsms_heregistrasi);
        	stmt.setString(2, npmhs);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		String tkn_approval = ""+rs.getString("TOKEN_APPROVAL");
        		stmt = con.prepareStatement("select TKN_VERIFICATOR from DAFTAR_ULANG_RULES where THSMS=? and KDPST=?");
        		stmt.setString(1, thsms_heregistrasi);
        		stmt.setString(2, kdpst);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			String tkn_verificator = ""+rs.getString("TKN_VERIFICATOR");
        			if(tkn_verificator!=null) {
        				StringTokenizer st = new StringTokenizer(tkn_verificator,",");
        				//boolean incomplete = false;
        				while(st.hasMoreTokens()) {
        					String aprovee = st.nextToken();
        					if(!tkn_approval.contains(aprovee)) {
        					//	incomplete = true;
        						if(pesan == null) {
        							pesan = new String("PROSES DAFTAR ULANG BELUM SELESAI, HARAP HUBUNGI : <br>"+aprovee.replace("OPERATOR", ""));	
        						}
        						else {
        							pesan = pesan + "<br>"+aprovee.replace("OPERATOR", "");
        						}
        						 
        					}
        				}
        			}
        		}
        		else {
        			pesan = null;
        		}
        	}
        	else {
        		pesan = "HARAP MELAKUKAN PENDAFTARAN ULANG TERLEBIH DAHULU";
        	}
        	/*
        	if(pesan==null) {
        		//long objid = getObjectId(npmhs);
        		String npmhs_nickname = getObjNickname((int)getObjectId(npmhs));
        		if(npmhs_nickname.contains("MHS")||npmhs_nickname.contains("mhs")) {
        			stmt = con.prepareStatement("UPDATE CIVITAS SET STMHSMSMHS='A' where NPMHSMSMHS=?");
        			stmt.setString(1, npmhs);
        			stmt.executeUpdate();
        		}
        		//update stmhsmsmhs = A
        		
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
    	return pesan;	
    }
    
    
    public static String getKdpst(String npmhs) {
    	String kdpst = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String nickname = null;
    	if(npmhs!=null && !Checker.isStringNullOrEmpty(npmhs)) {
    		try {
            	Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	 	
            	stmt = con.prepareStatement("SELECT KDPSTMSMHS from CIVITAS where NPMHSMSMHS=?");
            	stmt.setString(1, npmhs);
            	rs = stmt.executeQuery();
            	rs.next();
            	kdpst = new String(rs.getString(1));
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
    		
    	return kdpst;
    }
    
    public static String getNimhs(String npmhs) {
    	String nimhs = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String nickname = null;
    	if(npmhs!=null && !Checker.isStringNullOrEmpty(npmhs)) {
    		try {
            	Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	 	
            	stmt = con.prepareStatement("SELECT NIMHSMSMHS from CIVITAS where NPMHSMSMHS=?");
            	stmt.setString(1, npmhs);
            	rs = stmt.executeQuery();
            	rs.next();
            	nimhs = rs.getString(1);
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
    		
    	return nimhs;
    }
    
    public static String getSmawl(String npmhs) {
    	String smawl = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String nickname = null;
    	if(npmhs!=null && !Checker.isStringNullOrEmpty(npmhs)) {
    		try {
            	Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	 	
            	stmt = con.prepareStatement("SELECT SMAWLMSMHS from CIVITAS where NPMHSMSMHS=?");
            	stmt.setString(1, npmhs);
            	rs = stmt.executeQuery();
            	rs.next();
            	smawl = new String(rs.getString(1));
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
    		
    	return smawl;
    }
    
    
    public static String sudahDaftarUlang_deprecated(String kdpst, String npmhs, String target_thsms) {
    	/*
    	 * artinya sudah di approved oleh semua pihak
    	 * return null bila semua sudah approved
    	 */
    	
    	if(kdpst==null || Checker.isStringNullOrEmpty(kdpst)) {
    		kdpst = new String(getKdpst(npmhs));
    	}
    	String pesan = null;
    	//boolean status = false;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String nickname = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String thsms_heregistrasi = new String(target_thsms); 	
        	stmt = con.prepareStatement("SELECT * FROM DAFTAR_ULANG where THSMS=? and NPMHS=?");
        	stmt.setString(1,thsms_heregistrasi);
        	stmt.setString(2, npmhs);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		String tkn_approval = ""+rs.getString("TOKEN_APPROVAL");
        		stmt = con.prepareStatement("select TKN_VERIFICATOR from DAFTAR_ULANG_RULES where THSMS=? and KDPST=?");
        		stmt.setString(1, thsms_heregistrasi);
        		stmt.setString(2, kdpst);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			String tkn_verificator = ""+rs.getString("TKN_VERIFICATOR");
        			if(tkn_verificator!=null) {
        				StringTokenizer st = new StringTokenizer(tkn_verificator,",");
        				//boolean incomplete = false;
        				while(st.hasMoreTokens()) {
        					String aprovee = st.nextToken();
        					if(!tkn_approval.contains(aprovee)) {
        					//	incomplete = true;
        						if(pesan == null) {
        							pesan = new String("PROSES DAFTAR ULANG BELUM SELESAI, HARAP HUBUNGI : <br>"+aprovee.replace("OPERATOR", ""));	
        						}
        						else {
        							pesan = pesan + "<br>"+aprovee.replace("OPERATOR", "");
        						}
        						 
        					}
        				}
        			}
        		}
        		else {
        			pesan = null;
        		}
        	}
        	else {
        		pesan = "HARAP MELAKUKAN PENDAFTARAN ULANG TERLEBIH DAHULU";
        	}
        	/*
        	if(pesan==null) {
        		//long objid = getObjectId(npmhs);
        		String npmhs_nickname = getObjNickname((int)getObjectId(npmhs));
        		if(npmhs_nickname.contains("MHS")||npmhs_nickname.contains("mhs")) {
        			stmt = con.prepareStatement("UPDATE CIVITAS SET STMHSMSMHS='A' where NPMHSMSMHS=?");
        			stmt.setString(1, npmhs);
        			stmt.executeUpdate();
        		}
        		//update stmhsmsmhs = A
        		
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
    	return pesan;	
    }    

    /*
     * FUNGSI UNTUK MHS ONLY
     */
    public static boolean sudahDaftarUlang(HttpSession session) { //fungsi utk mhs ponly
    	/*
    	 * KALO sdu yg dicek adalah user itu sendiri, jadi digunakan bila yg login adalah mhs, jadi dia update dirri sendiri
    	 */
    	boolean sdu = false;
    	String brs = (String)session.getAttribute("sdu");
    	//System.out.println("brs_sdu="+brs);
    	if(brs.startsWith("true")) {
    		sdu = true;
    	}
    	return sdu;
    }
    
    public static boolean isThisMyRole(HttpSession session, String target_jabatan) { //fungsi utk mhs ponly
    	Vector v_jabatan = (Vector) session.getAttribute("v_jabatan");
    	boolean match = false;
    	if(v_jabatan!=null) {
    		ListIterator li = v_jabatan.listIterator();
    		while(li.hasNext() && !match) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String nama = st.nextToken();
    			String singkatan = st.nextToken();
    			if(target_jabatan.equalsIgnoreCase(nama)||target_jabatan.equalsIgnoreCase(singkatan)) {
    				match = true;
    			}
    		}
    	}
    	return match;
    }
    /*
     * FUNGSI UNTUK MHS ONLY
     */
    public static boolean sudahMengajukanDaftarUlang(HttpSession session) {
    	/*
    	 * KALO sdu yg dicek adalah user itu sendiri, jadi digunakan bila yg login adalah mhs, jadi dia update dirri sendiri
    	 */
    	boolean sdu = true;
    	String brs = (String)session.getAttribute("sdu");
    	//System.out.println("brs_sdu="+brs);
    	if(brs.startsWith("HARAP")) { // belum mengajukan samasaeklai
    		sdu = false;
    	}
    	return sdu;
    }
    /*
     * FUNGSI UNTUK MHS ONLY
     */
    public static boolean showNotificasiDaftarUlang(HttpSession session) {
    	/*
    	 * KALO sdu yg dicek adalah user itu sendiri, jadi digunakan bila yg login adalah mhs, jadi dia update dirri sendiri
    	 */
    	boolean show = false;
    	String brs = (String)session.getAttribute("sdu");
    	
    	StringTokenizer st = new StringTokenizer(brs,"`");
    	if(st.countTokens()==1) {//belum perbah mengajukan jadi show
    		show=true;
    	}
    	else {
    		st.nextToken();
        	String stat = st.nextToken();//tkn ke dua value show
        	show = Boolean.parseBoolean(stat);	
    	}
    	return show;
    }
    
    
    public static String getCurrStmhs(HttpSession session) {
    	/*
    	 * KALO sdu yg dicek adalah user itu sendiri, jadi digunakan bila yg login adalah mhs, jadi dia update dirri sendiri
    	 */
    	//boolean sdu = false;
    	String curr_stmhsmsmhs = (String)session.getAttribute("curr_stmhsmsmhs");
    	
    	return curr_stmhsmsmhs;
    }
    
    public static String yourApproveNicknameAtPengajuanUa(long idObjPengaju, String usrNpm) {
    	//System.out.println("idObjPengaju="+idObjPengaju);
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String MyNick = null;
    	long myObjId = Checker.getObjectId(usrNpm);
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String thsms_heregistrasi = getThsmsHeregistrasi(); 	
        	stmt = con.prepareStatement("SELECT * FROM PENGAJUAN_UA_RULES where IDOBJ=?");
        	stmt.setLong(1,idObjPengaju);
        	rs = stmt.executeQuery();
        	rs.next();
        	String tkn_approvee_id = rs.getString("TKN_APPROVEE_ID");
        	String tkn_approvee_niknem = rs.getString("TKN_APPROVEE_NICKNAME");
        	StringTokenizer st = new StringTokenizer(tkn_approvee_id,"`");
        	StringTokenizer st1 = new StringTokenizer(tkn_approvee_niknem,"`");
        	boolean match = false;
        	while(st.hasMoreTokens()&&!match) {
        		String tkn_id = st.nextToken();
        		String tkn_nick = st1.nextToken();
        		//System.out.println("tkn_id="+tkn_id);
        		//System.out.println("tkn_nick="+tkn_nick);
        		if(tkn_id.contains("/"+myObjId+"/")) {
        			StringTokenizer stt = new StringTokenizer(tkn_id,"/");
        			StringTokenizer stt1 = new StringTokenizer(tkn_nick,"/");
        			while(stt.hasMoreTokens()&&!match) {
        				String tmpId = stt.nextToken();
        				MyNick = new String(stt1.nextToken());
        				if(tmpId.equalsIgnoreCase(""+myObjId)) {
        					match = true;
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
    	return MyNick;	
    }    
    
    public static Boolean isKelasGabungan(String cuid) {
    	boolean gabungan = true;
    	SearchDbTrnlm sdt = new SearchDbTrnlm();
    	gabungan = sdt.isKelasGabungan(cuid);
    	return gabungan;
    }
    
    
    public static String getFakInfo(String kdpst) {
    	String kdfak =null;
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
    		stmt = con.prepareStatement("SELECT * FROM MSPST inner join MSFAK on KDFAKMSPST=KDFAKMSFAK where KDPSTMSPST=?");
    		stmt.setString(1,kdpst);
    		rs = stmt.executeQuery();
    		if(!rs.next()) {
    			//System.out.println("error @Checker.getFakInfo, col fak pada MSPST tidak diisi");
    		}
    		else {
    			kdfak = ""+rs.getString("KDFAKMSPST");
    			nmfak = ""+rs.getString("NMFAKMSFAK");
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
    	return kdfak+" "+nmfak;	
    }
    
    /*
     * DEPRECATED
     * USE YG PAKE OBJEK ID
     */
    public static String getSistemPerkuliahan(String  objId, String kdpst) {
    	String siskul = getSistemPerkuliahan_v1(objId);
    	/*      
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String siskul = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT KODE_KAMPUS_DOMISILI from OBJECT  where ID_OBJ=?");
    		stmt.setInt(1, Integer.parseInt(objId));
    		rs = stmt.executeQuery();
    		rs.next();
    		String kode_kmp = rs.getString(1);
    		
    		stmt = con.prepareStatement("SELECT * from CALENDAR_BUKA_KELAS where KDPST=? and (KODE_KAMPUS=? or KODE_KAMPUS=?)");
    		stmt.setString(1, kdpst);
    		stmt.setString(2, kode_kmp);
    		stmt.setString(3, kode_kmp);
    		rs = stmt.executeQuery();
    		if(!rs.next()) {
    			stmt.setString(1, kdpst);
        		stmt.setString(2, "ALL");
        		stmt.setString(3, "all");
        		rs=stmt.executeQuery();
        		rs.next();
        		siskul = new String(rs.getString("SISTEM_PERKULIAHAN"));
    		}
    		else {
    			siskul = new String(rs.getString("SISTEM_PERKULIAHAN"));
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
    	return siskul;	
    }
    
    public static String getSistemPerkuliahan_v1(String  objId) {
	      
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String siskul = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT SISTEM_PERKULIAHAN from OBJECT  where ID_OBJ=?");
    		stmt.setInt(1, Integer.parseInt(objId));
    		rs = stmt.executeQuery();
    		rs.next();
    		siskul = new String(""+rs.getString("SISTEM_PERKULIAHAN"));
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
    	return siskul;	
    }
    
    public static boolean am_i_stu(HttpSession session) {
    	String am_i_stu = (String) session.getAttribute("ObjGenderAndNickname");
    	if(am_i_stu.contains("MHS")) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public static String getObjidMhsProdi(String  kdpst, String kdkmp) {
	      
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String objid = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT ID_OBJ from OBJECT where KDPST=? and OBJ_NAME=? and KODE_KAMPUS_DOMISILI=?");
    		stmt.setString(1, kdpst);
    		stmt.setString(2, "MHS");
    		stmt.setString(3, kdkmp);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			objid = ""+ rs.getLong(1);	
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
    	return objid;	
    }
    
    
    
    public static boolean amIdosen(String npmhs, boolean memiliki_nidnn) { 
    	/*
    	 * remember fungsi ini juga ada di isu
    	 */
	    boolean match=false;  
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String objid = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT NIDNN FROM CIVITAS a inner join EXT_CIVITAS_DATA_DOSEN b on NPMHSMSMHS=NPMHS where NPMHS=?");
    		stmt.setString(1, npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			if(memiliki_nidnn) {
    				String nidnn = rs.getString(1);
    				//System.out.println("nidnn="+nidnn);
        			if(!Checker.isStringNullOrEmpty(nidnn)) {
        				//yes memiliki nidn
        				match = true;
        			}
    			}
    			else {
    				//yes, sy dosen tanpe ngecek punya nidnn ato blum
    				match = true;
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
    	return match;	
    }
    
    
    public static boolean amI(String tkn_nama_jabatan, int my_objid) { 
    	/*
    	 * remember fungsi ini juga ada di isu
    	 */
	    boolean match=false;  
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String objid = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT ID from JABATAN inner join STRUKTURAL on NAMA_JABATAN=NM_JOB where JABATAN.AKTIF=? and STRUKTURAL.AKTIF=? and NM_JOB=? and OBJID=? limit 1");
    		
    		StringTokenizer st = new StringTokenizer(tkn_nama_jabatan,"`");
    		while(st.hasMoreTokens()&&!match) {
    			String nama_jabatan = st.nextToken();
    			stmt.setBoolean(1, true);
        		stmt.setBoolean(2, true);
        		stmt.setString(3, nama_jabatan);
        		stmt.setInt(4, my_objid);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			match = true;
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
    	return match;	
    }
    
    public static boolean amI(String tkn_nama_jabatan, int my_objid, int target_id_obj) { 
    	/*
    	 * remember fungsi ini juga ada di isu
    	 */
	    boolean match=false;  
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String objid = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get info target 
    		stmt = con.prepareStatement("select KDPST,KODE_KAMPUS_DOMISILI from OBJECT where ID_OBJ=?");
    		stmt.setInt(1, target_id_obj);
    		rs = stmt.executeQuery();
    		rs.next();
    		String target_kdpst = rs.getString(1);
    		String target_kdkmp = rs.getString(2);
    		
    		//stmt = con.prepareStatement("SELECT ID from JABATAN inner join STRUKTURAL on NAMA_JABATAN=NM_JOB where JABATAN.AKTIF=? and STRUKTURAL.AKTIF=? and NM_JOB=? and OBJID=? limit 1");
    		stmt = con.prepareStatement("SELECT ID from JABATAN inner join STRUKTURAL on NAMA_JABATAN=NM_JOB where JABATAN.AKTIF=? and STRUKTURAL.AKTIF=? and (NM_JOB=? or SINGKATAN=?) and OBJID=? and STRUKTURAL.KDKMP='"+target_kdkmp+"' and STRUKTURAL.KDPST='"+target_kdpst+"' limit 1");
    		StringTokenizer st = new StringTokenizer(tkn_nama_jabatan,"`");
    		while(st.hasMoreTokens()&&!match) {
    			String nama_jabatan = st.nextToken();
    			//System.out.println("nama_jabatan="+nama_jabatan);
    			stmt.setBoolean(1, true);
        		stmt.setBoolean(2, true);
        		stmt.setString(3, nama_jabatan);
        		stmt.setString(4, nama_jabatan);
        		stmt.setInt(5, my_objid);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			match = true;
        		}
        		//System.out.println("match="+match);
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
    	return match;	
    }
    
    
    public static String listIdobjForJabatan(String tkn_jabatan, int target_idobj) {
    	String tkn_approvee=null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String objid = null;
    	if(!Checker.isStringNullOrEmpty(tkn_jabatan)) {
    		//System.out.println("awal tkn_jabatan="+tkn_jabatan);
    		tkn_jabatan = Tool.removeKurungTutup(tkn_jabatan); //pemisah ` always
    		//System.out.println("anu tkn_jabatan="+tkn_jabatan);
    		String char_pemisah = Checker.getSeperatorYgDigunakan(tkn_jabatan);
    		if(Checker.isStringNullOrEmpty(char_pemisah)) {
    			char_pemisah=new String("`");
    		}
    		StringTokenizer st = new StringTokenizer(tkn_jabatan,char_pemisah);
    		
    		//System.out.println("anu target_idobj="+target_idobj);
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		String sql = "SELECT DISTINCT B.OBJID from JABATAN A inner join STRUKTURAL B on A.NAMA_JABATAN=B.NM_JOB inner join OBJECT C on (B.KDPST=C.KDPST and B.KDKMP=C.KODE_KAMPUS_DOMISILI) where A.AKTIF=true and B.AKTIF=true and (NAMA_JABATAN=? or SINGKATAN=? or ALIAS_NM_JOB=?) and C.ID_OBJ="+target_idobj;
        		stmt = con.prepareStatement(sql);
        		//System.out.println("sqlj="+sql);
        		while(st.hasMoreTokens()) {
        			String tmp_tkn_approvee=null; 
        			String jabatan = st.nextToken();
        			stmt.setString(1, jabatan);
        			stmt.setString(2, jabatan);
        			stmt.setString(3, jabatan);
        			rs = stmt.executeQuery();
        			while(rs.next()) {
        				if(Checker.isStringNullOrEmpty(tmp_tkn_approvee)) {
        					tmp_tkn_approvee = new String("`");
        				}
        				String id =rs.getString(1);
        				if(!tmp_tkn_approvee.contains("`"+id+"`")) {
        					tmp_tkn_approvee = tmp_tkn_approvee+id+"`";	
        				}	
        			}
        			if(!Checker.isStringNullOrEmpty(tmp_tkn_approvee)) {
        				tmp_tkn_approvee="["+tmp_tkn_approvee.substring(1, tmp_tkn_approvee.length()-1)+"]";
        			}
        			if(tkn_approvee==null) {
        				tkn_approvee = new String();
        			}
        			tkn_approvee = tkn_approvee+tmp_tkn_approvee;
        		}
        		/*
        		if(!Checker.isStringNullOrEmpty(tkn_approvee)) {
        			if(tkn_approvee.startsWith("`")) {
        				tkn_approvee = tkn_approvee.substring(1,tkn_approvee.length());
        			}
        			if(tkn_approvee.endsWith("`")) {
        				tkn_approvee = tkn_approvee.substring(0,tkn_approvee.length()-1);
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
    	
    	return tkn_approvee;
    }
    
    
    public static String listVerificatorHeregistrasi(String target_thsms, int target_idobj) {
    	String tkn_approvee=null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String objid = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		String sql = "SELECT TKN_JABATAN_VERIFICATOR FROM HEREGISTRASI_RULES A inner join OBJECT B on (A.KDPST=B.KDPST and A.KODE_KAMPUS=B.KODE_KAMPUS_DOMISILI) where B.ID_OBJ=? and A.THSMS=?";
    		stmt = con.prepareStatement(sql);
    		stmt.setInt(1, target_idobj);
			stmt.setString(2, target_thsms);
			rs = stmt.executeQuery();
    		if(rs.next()) {
    			String tmp = rs.getString(1);
    			tkn_approvee = new String(tmp);
    			//System.out.println("tmp = "+tmp);
    			
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
    	return tkn_approvee;
    }
    
    public static boolean amI(String target_tkn_nama_jabatan, Vector v_getter_getListMyJabatan, String target_idobj) {
    	//System.out.println("target_tkn_nama_jabatan="+target_tkn_nama_jabatan);
    	//System.out.println("v_getter_getListMyJabatan----="+v_getter_getListMyJabatan.size());
    	StringTokenizer st = null;
	    boolean match=false;
	    StringTokenizer st1=null;   
    	if(v_getter_getListMyJabatan!=null && v_getter_getListMyJabatan.size()>0) {
    		//System.out.println("sinting");
    		try {
    			//System.out.println("konyol");
    			String char_pemisah="`"; //wajib pake ini
    			/*
    			if(target_tkn_nama_jabatan!=null) {
    				//System.out.println("gokil");
    				target_tkn_nama_jabatan = Tool.removeKurungTutup(target_tkn_nama_jabatan);
    				char_pemisah=Checker.getSeperatorYgDigunakan(target_tkn_nama_jabatan);
    				
    				//System.out.println("char_pemisah="+char_pemisah);
    				
    			*/
    			//System.out.println("aneh");
    			ListIterator li = v_getter_getListMyJabatan.listIterator();
    			while(li.hasNext()&&!match) {
    				//System.out.println("ajainb");
    				String brs = (String)li.next();
    				//System.out.println("baris="+brs);
    				st = new StringTokenizer(brs,"~");
    				String kdkmp=st.nextToken();
    				String kdpst=st.nextToken();
    				String nm_job=st.nextToken();
    				String short_job=st.nextToken();
    				String id_jab=st.nextToken();
    				String id_struk=st.nextToken();
    				String tujuan_idobj=st.nextToken();
    				String tujuan_nm_obj=st.nextToken();
    				st1 = new StringTokenizer(target_tkn_nama_jabatan,char_pemisah);
    			
    				while(st1.hasMoreTokens()&& !match) {
    					String target_job = st1.nextToken();
    					//System.out.println(target_job+" vs "+nm_job+" vs "+short_job);
    					if((target_job.equalsIgnoreCase(nm_job)||target_job.equalsIgnoreCase(short_job))&&target_idobj.equalsIgnoreCase(tujuan_idobj)) {
    						match = true;
    					}
    					//System.out.println("match="+match);
    				}
    			}
    		}
    		catch(Exception e) {e.printStackTrace();}
    	}
    	
    	return match;	
    }
    
    public static boolean isNamaKurikulumAda(String nama_krklm, String nama_konsentrasi) { 
    	/*
    	 * remember fungsi ini juga ada di isu
    	 */
	    boolean match=false;  
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String objid = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		if(Checker.isStringNullOrEmpty(nama_konsentrasi)) {
    			stmt = con.prepareStatement("select NMKURKRKLM from KRKLM where NMKURKRKLM=? limit 1");
    			stmt.setString(1, nama_krklm.trim());
    		}
    		else {
    			stmt = con.prepareStatement("select NMKURKRKLM from KRKLM where NMKURKRKLM=? and KONSENTRASI=? limit 1");
    			stmt.setString(1, nama_krklm.trim());
    			stmt.setString(2, nama_konsentrasi.trim());
    		}
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			match = true;
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
    	return match;	
    }
    
    public static boolean checkIfContains(String subjek, long test_char, String pemisah) {
    	boolean yup = false;
    	if(!subjek.startsWith(pemisah)) {
    		subjek = pemisah+subjek;
    	}
    	if(!subjek.endsWith(pemisah)) {
    		subjek = subjek+pemisah;
    	}
    	if(subjek.contains(pemisah+test_char+pemisah)) {
    		yup = true;
    	}
    	return yup;
    }
    
    public static boolean isSmsPendek(String target_thsms) {
    	boolean sms_pendek = false;
    	if(!Checker.isStringNullOrEmpty(target_thsms)) {
    		if(target_thsms.contains("a")||target_thsms.contains("A")||target_thsms.contains("b")||target_thsms.contains("B")) {
    			sms_pendek = true;
    		}
    	}
    	return sms_pendek;
    }
    
    public static String getSeperatorYgDigunakan(String token_kata) {
    	/*
    	 * 
    	 */
    	String seperator =null;
    	if(!Checker.isStringNullOrEmpty(token_kata)) {
    		if(token_kata.contains("`")) {
        		seperator = new String("`");
        	}
    		else if(token_kata.contains("#")) {
        		seperator = new String("#");
        	}
        	else if(token_kata.contains("~")) {
        		seperator = new String("~");
        	}
        	else if(token_kata.contains("||")) {
        		seperator = new String("||");
        	}
        	else if(token_kata.contains("$")) {
        		seperator = new String("$");
        	}
        	else if(token_kata.contains(";")) {
        		seperator = new String(";");
        	}
        	else if(token_kata.contains("/")) {
        		seperator = new String("/");
        	}
        	else if(token_kata.contains("-")) {
        		seperator = new String("-");
        	}
        	else if(token_kata.contains(",")) {//koma taro paling belakang aja, taku t ada nama pake gelar, jadi komlikasi deh
        		seperator = new String(",");
        	}
        	else {
        		seperator = new String(" ");
        	}
    	}
    	else {
    		seperator = new String(" ");
    	}
    	
    	return seperator;
    }
    
    public static boolean isWilayahValid(int lvl_wil, String tkn_nm_id_wil_prop, String tkn_nm_id_wil_kot, String tkn_nm_id_wil_kec) {
    	String id_indo = "000000"; //lvl 0
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	StringTokenizer st = null;
    	boolean valid = false;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select id_wil from wilayah where id_wil=? and id_induk_wilayah=?");
    		//cretae NPM auto increment
    		if(lvl_wil==1 && tkn_nm_id_wil_prop!=null) { //prop
    			st = new StringTokenizer(tkn_nm_id_wil_prop,"`");
    			String nm_wil_prop = st.nextToken();
    			String id_wil_prop = st.nextToken();
    			stmt.setString(1, id_wil_prop);
    			stmt.setString(2, id_indo);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				valid = true;
    			}
    			 
    		}
    		else if(lvl_wil==2 && tkn_nm_id_wil_prop!=null && tkn_nm_id_wil_kot!=null) { //kota kab
    			//harus ada id prop
    			st = new StringTokenizer(tkn_nm_id_wil_prop,"`");
    			String nm_wil_prop = st.nextToken();
    			String id_wil_prop = st.nextToken();
    			stmt.setString(1, id_wil_prop);
    			stmt.setString(2, id_indo);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				st = new StringTokenizer(tkn_nm_id_wil_kot,"`");
        			String nm_wil_kot = st.nextToken();
        			String id_wil_kot = st.nextToken();
        			stmt.setString(1, id_wil_kot);
        			stmt.setString(2, id_wil_prop);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				valid = true;
        			}
    			}
    		}
    		else if(lvl_wil==3 && tkn_nm_id_wil_prop!=null && tkn_nm_id_wil_kot!=null && tkn_nm_id_wil_kec!=null ) { //kota kab
    			st = new StringTokenizer(tkn_nm_id_wil_prop,"`");
    			String nm_wil_prop = st.nextToken();
    			String id_wil_prop = st.nextToken();
    			stmt.setString(1, id_wil_prop);
    			stmt.setString(2, id_indo);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				st = new StringTokenizer(tkn_nm_id_wil_kot,"`");
        			String nm_wil_kot = st.nextToken();
        			String id_wil_kot = st.nextToken();
        			stmt.setString(1, id_wil_kot);
        			stmt.setString(2, id_wil_prop);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				tkn_nm_id_wil_kec = tkn_nm_id_wil_kec.replace("[", "`");
        				tkn_nm_id_wil_kec = tkn_nm_id_wil_kec.replace("]", "");
        				st = new StringTokenizer(tkn_nm_id_wil_kec,"`");
            			String nm_wil_kec = st.nextToken();
            			String id_wil_kec = st.nextToken();
            			stmt.setString(1, id_wil_kec);
            			stmt.setString(2, id_wil_kot);
            			rs = stmt.executeQuery();
            			if(rs.next()) {
            				valid = true;
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
    	return valid;
    }
    

    public static String getTahunNow() {
    	String thsms_now = Checker.getThsmsNow();
    	if(thsms_now.endsWith("1")) {
    		thsms_now = thsms_now.substring(0,4);
    	}
    	else {
    		thsms_now = thsms_now.substring(0,4);
    		thsms_now = ""+(1+Integer.parseInt(thsms_now));
    	}
    	return thsms_now;
    }

    public static String needApprovalFromWhomToHeregistrasi(String tkn_approval_needed, String hist_approval) {
    	if(!Checker.isStringNullOrEmpty(tkn_approval_needed)) {
    		while(tkn_approval_needed.contains("][")) {
    			tkn_approval_needed=tkn_approval_needed.replace("][", "`"); 
    		}
    		while(tkn_approval_needed.contains("]")||tkn_approval_needed.contains("[")) {
    			tkn_approval_needed=tkn_approval_needed.replace("]", "");
    			tkn_approval_needed=tkn_approval_needed.replace("[", "");
    		}
    		StringTokenizer st = new StringTokenizer(tkn_approval_needed,"`");
    		tkn_approval_needed=null;
    		while(st.hasMoreTokens()) {
    			String who = st.nextToken();
    			if(!hist_approval.contains("#"+who+"#")) {
    				if(tkn_approval_needed==null) {
    					tkn_approval_needed = new String(who);
    				}
    				else {
    					tkn_approval_needed=tkn_approval_needed+"`"+who;
    				}
    			}
    		}
    	}
    	return tkn_approval_needed;
    }
    
    public static boolean mhsPindahan(String npmhs) {
    	
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	StringTokenizer st = null;
    	boolean pindahan = false;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select STPIDMSMHS from CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1, npmhs);
    		rs = stmt.executeQuery();
    		rs.next();
    		String stpid = rs.getString(1);
    		if(stpid!=null && stpid.equalsIgnoreCase("P")) {
    			pindahan=true;
    		}
    		//cretae NPM auto increment
    		
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
    	return pindahan;
    }
    
    public static String getAspti(String npmhs) {
    	
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	StringTokenizer st = null;
    	String aspti = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select ASPTI_KDPTI from CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1, npmhs);
    		rs = stmt.executeQuery();
    		rs.next();
    		aspti = rs.getString(1);
    		if(Checker.isStringNullOrEmpty(aspti)) {
    			aspti=null;
    		}
    		//cretae NPM auto increment
    		
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
    	return aspti;
    }
    
public static String getAspst(String npmhs) {
    	
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	StringTokenizer st = null;
    	String aspti = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select ASPST_KDPST from CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1, npmhs);
    		rs = stmt.executeQuery();
    		rs.next();
    		aspti = rs.getString(1);
    		if(Checker.isStringNullOrEmpty(aspti)) {
    			aspti=null;
    		}
    		//cretae NPM auto increment
    		
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
    	return aspti;
    }

}

package beans.dbase.krklm;

import beans.dbase.SearchDb;
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
 * Session Bean implementation class SearchDbKrklm
 */
@Stateless
@LocalBean
public class SearchDbKrklm extends SearchDb {
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
    public SearchDbKrklm() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbKrklm(String operatorNpm) {
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
    public SearchDbKrklm(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public Vector getListKurikulum(String kdpst) {
    	Vector v = null;
    	try {
    		v = Getter.getListKurikulum(kdpst,false);
    		//li.add(idkur+"`"+nmkur+"`"+stkur+"`"+start+"`"+end+"`"+skstt+"`"+smstt);
    	}
    	catch(Exception e){}
    	return v;
    }
    
    public Vector getListInfoKrklm(String kdpst) {
    	//seluruh kurikulum aktif dan pasif
    	Vector vf = null;
    	ListIterator li = null;
    	Vector v = null;
    	ListIterator lif = null;
    	StringTokenizer st = null;
    	try {
    		v = getListKurikulum(kdpst);
    		if(v!=null) {
    			vf = new Vector();
    			li = v.listIterator();
    			lif = vf.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				st = new StringTokenizer(brs,"`");
    				int idkur = Integer.parseInt(st.nextToken());
    				Vector v_tmp = getInfoKrklm(idkur);
    				lif.add(new Vector(v_tmp));
    				v_tmp = null;
    			}
    		}
    		//li.add(idkur+"`"+nmkur+"`"+stkur+"`"+start+"`"+end+"`"+skstt+"`"+smstt);
    	}
    	catch(Exception e){}
    	return vf;
    }
    
    
    public String validateKurikulum(int id_kur) {
    	int counter=0;
    	String err_msg=null;
		try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//#1. cek apakah semes makul sudah terisi semua
    		String sql_cmd = "SELECT SEMESMAKUR FROM MAKUR where IDKURMAKUR=? and (SEMESMAKUR is null or SEMESMAKUR<?) limit 1";
    		stmt = con.prepareStatement(sql_cmd);
    		stmt.setInt(1, id_kur);
    		stmt.setInt(2, 1);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			counter++;
    			if(err_msg==null) {
    				err_msg = counter+". Semester tiap matakuliah wajib diisi.<br>";
    			}
    			else {
    				err_msg = err_msg+counter+". Semester tiap matakuliah wajib diisi.<br>";
    			}
    		}
    		//#2. cek apakah sudah memiliki MK AKHIR
    		sql_cmd = "SELECT count(FINAL_MK) FROM MAKUR where IDKURMAKUR=? and FINAL_MK=true";
    		stmt = con.prepareStatement(sql_cmd); 
    		stmt.setInt(1, id_kur);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			int count = rs.getInt(1);
    			//System.out.println("count="+count);
    			if(count!=1) {
    				counter++;
    				if(err_msg==null) {
    					if(count<1) {
    						err_msg = counter+". Kurikulum Belum memiliki Matakuliah Penentu Kelulusan / Akhir<br>";
    					}
    					else {
    						err_msg = counter+". Kurikulum memiliki Matakuliah Penentu Kelulusan / Akhir Lebih dari 1 (Satu)<br>";
    					}
        				
        			}
        			else {
        				if(count<1) {
    						err_msg = err_msg+counter+". Kurikulum Belum memiliki Matakuliah Penentu Kelulusan / Akhir<br>";
    					}
    					else {
    						err_msg = err_msg+counter+". Kurikulum memiliki Matakuliah Penentu Kelulusan / Akhir Lebih dari 1 (Satu)<br>";
    					}
        			}	
    			}
    		}
    		//3. cek total sks mk >= skstt krklm
    		sql_cmd = "SELECT sum(SKSTMMAKUL+SKSLPMAKUL+SKSPRMAKUL+SKSLBMAKUL+SKSIMMAKUL) as SKSTT_WIP,SKSTTKRKLM,SKSTTWAJIB,SKSTTPILIH FROM MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL inner join KRKLM on IDKURMAKUR=IDKURKRKLM  where IDKURMAKUR=?";
    		stmt = con.prepareStatement(sql_cmd); 
    		stmt.setInt(1, id_kur);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			int skstt_wip = rs.getInt(1);
    			int skstt_kur = rs.getInt(2);
    			int skstt_wjb = rs.getInt(3);
    			int skstt_pil = rs.getInt(4);
    			if((skstt_wjb+skstt_pil)!=skstt_kur) {
    				counter++;
    				if(err_msg==null) {
    					err_msg = counter+". SKS wajib + pilihan tidak sama dengan total sks kurikulum<br>";
    				}
    				else {
    					err_msg = err_msg+counter+". SKS wajib + pilihan tidak sama dengan total sks kurikulum<br>";
    				}
    			}
    			
    			if(skstt_wip<skstt_kur && skstt_kur>0) {
    				counter++;
    				if(err_msg==null) {
    					if(skstt_kur<1) {
    						err_msg = counter+". SKS total Kurikulum : 0 sks<br>";
    					}
    					else if(skstt_wip<skstt_kur) {
    						err_msg = counter+". Total sks matakuliah < total sks kurikulum<br>";
    					}
        			}
        			else {
        				if(skstt_kur<1) {
    						err_msg = err_msg+counter+". SKS total Kurikulum : 0 sks<br>";
    					}
    					else if(skstt_wip<skstt_kur) {
    						err_msg = err_msg+counter+". Total sks matakuliah < total sks kurikulum<br>";
    					}
        			}	
    			}
    		}
    		
    		//#3. cek apakah tot sks MK wajib sudah terpenuhi
    		sql_cmd = "SELECT sum(SKSTMMAKUL+SKSLPMAKUL+SKSPRMAKUL+SKSLBMAKUL+SKSIMMAKUL) as SKSTTWAJIB_WIP,SKSTTKRKLM,SKSTTWAJIB,SKSTTPILIH FROM MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL inner join KRKLM on IDKURMAKUR=IDKURKRKLM  where IDKURMAKUR=? and WAJIB=?";
    		stmt = con.prepareStatement(sql_cmd); 
    		stmt.setInt(1, id_kur);
    		stmt.setBoolean(2, true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			int skstt_wajib_wip = rs.getInt(1);
    			int skstt = rs.getInt(2);
    			int skstt_wajib = rs.getInt(3);
    			int skstt_pilih = rs.getInt(4);
    			
    			if(skstt_wajib_wip<skstt_wajib) {
    				counter++;
    				if(err_msg==null) {
    					err_msg = counter+". SKS mata kuliah wajib kurang / tidak sesuai dengan rancangan Kurikulum<br>";
    				}
    				else {
    					err_msg = err_msg+counter+". SKS mata kuliah wajib kurang / tidak sesuai dengan rancangan Kurikulum<br>";
    				}
    			}
    			
    		}
    		//#4. cek apakah tot sks MK pilihan sudah terpenuhi
    		sql_cmd = "SELECT sum(SKSTMMAKUL+SKSLPMAKUL+SKSPRMAKUL+SKSLBMAKUL+SKSIMMAKUL) as SKSTTPILIH_WIP,SKSTTKRKLM,SKSTTWAJIB,SKSTTPILIH FROM MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL inner join KRKLM on IDKURMAKUR=IDKURKRKLM  where IDKURMAKUR=? and WAJIB=?";
    		stmt = con.prepareStatement(sql_cmd); 
    		stmt.setInt(1, id_kur);
    		stmt.setBoolean(2, false);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			int skstt_pilih_wip = rs.getInt(1);
    			int skstt = rs.getInt(2);
    			int skstt_wajib = rs.getInt(3);
    			int skstt_pilih = rs.getInt(4);
    			
    			if(skstt_pilih_wip<skstt_pilih) {
    				counter++;
    				if(err_msg==null) {
    					err_msg = counter+". SKS mata kuliah pilihan kurang / tidak sesuai dengan rancangan Kurikulum<br>";
    				}
    				else {
    					err_msg = err_msg+counter+". SKS mata kuliah pilihan kurang / tidak sesuai dengan rancangan Kurikulum<br>";
    				}
    			}
    			
    		}
    		//#5. cek apakah tot sks sms 1&2 sudah sesuai
    		stmt = con.prepareStatement("select VALUE from CONSTANT where KETERANGAN=?");
    		stmt.setString(1, "sks_max_thn_pertama");
    		rs = stmt.executeQuery();
    		rs.next();
    		int sks_max_thn1 = Integer.parseInt(rs.getString(1));
    		sql_cmd = "SELECT sum(SKSTMMAKUL+SKSLPMAKUL+SKSPRMAKUL+SKSLBMAKUL+SKSIMMAKUL) as SKSTT FROM MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL inner join KRKLM on IDKURMAKUR=IDKURKRKLM  where IDKURMAKUR=? and SEMESMAKUR=?";
    		stmt = con.prepareStatement(sql_cmd); 
    		//sms 1
    		
    		stmt.setInt(1, id_kur);
    		stmt.setInt(2, 1);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			int skstt = rs.getInt(1);
    			
    			if(skstt>sks_max_thn1) {
    				counter++;
    				if(err_msg==null) {
    					err_msg = counter+". Total sks semester 1 (satu) melebihi batas maximum "+sks_max_thn1+" sks <br>";
    				}
    				else {
    					err_msg = err_msg+counter+". Total sks semester 1 (satu) melebihi batas maximum "+sks_max_thn1+" sks <br>";
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
		return err_msg;
    }
    
    public Vector getInfoKrklm(int krklm_id) {
    	//satu kurikulum only
    	Vector v = null;
    	ListIterator li = null;
		try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		String sql_cmd = "SELECT NMKURKRKLM,SKSTTKRKLM,SMSTTKRKLM,IDKMKMAKUL,KDKMKMAKUL,SEMESMAKUR,SKSTMMAKUL,SKSPRMAKUL,SKSLPMAKUL,SKSLBMAKUL,SKSIMMAKUL,JENISMAKUL,FINAL_MK FROM KRKLM inner join MAKUR on IDKURKRKLM=IDKURMAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURKRKLM=? order by SEMESMAKUR,FINAL_MK";
    		stmt = con.prepareStatement(sql_cmd); 
    		stmt.setInt(1, krklm_id);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			String nmkur = ""+rs.getString("NMKURKRKLM");
    			String skstt = ""+rs.getInt("SKSTTKRKLM");
    			String smstt = ""+rs.getInt("SMSTTKRKLM");
    			String idkmk = ""+rs.getLong("IDKMKMAKUL");
    			String kdkmk = ""+rs.getString("KDKMKMAKUL");
    			String semes = ""+rs.getString("SEMESMAKUR");
    			int skstm = rs.getInt("SKSTMMAKUL");
    			int skspr = rs.getInt("SKSPRMAKUL");
    			int skslp = rs.getInt("SKSLPMAKUL");
    			int skslb = rs.getInt("SKSLBMAKUL");
    			int sksim = rs.getInt("SKSIMMAKUL");
    			int sksmk = skstm+skspr+skslp+skslb+sksim;
    			String jenis = ""+rs.getString("JENISMAKUL");
    			String final_mk = ""+rs.getBoolean("FINAL_MK");
    			String info_krklm = krklm_id+"`"+nmkur+"`"+skstt+"`"+smstt;
    			//String info_mk = semes+"`"+idkmk+"`"+kdkmk+"`"+skstm+"`"+skspr+"`"+skslp+"`"+skslb+"`"+sksim+"`"+sksmk+"`"+jenis+"`"+final_mk;
    			li.add(info_krklm);
    			
    			do {
    				nmkur = ""+rs.getString("NMKURKRKLM");
        			skstt = ""+rs.getInt("SKSTTKRKLM");
        			smstt = ""+rs.getInt("SMSTTKRKLM");
        			idkmk = ""+rs.getLong("IDKMKMAKUL");
        			kdkmk = ""+rs.getString("KDKMKMAKUL");
        			semes = ""+rs.getString("SEMESMAKUR");
        			skstm = rs.getInt("SKSTMMAKUL");
        			skspr = rs.getInt("SKSPRMAKUL");
        			skslp = rs.getInt("SKSLPMAKUL");
        			skslb = rs.getInt("SKSLBMAKUL");
        			sksim = rs.getInt("SKSIMMAKUL");
        			sksmk = skstm+skspr+skslp+skslb+sksim;
        			jenis = ""+rs.getString("JENISMAKUL");
        			final_mk = ""+rs.getBoolean("FINAL_MK");
        			//String info_krklm = krklm_id+"`"+nmkur+"`"+skstt+"`"+smstt;
        			String info_mk = semes+"`"+idkmk+"`"+kdkmk+"`"+skstm+"`"+skspr+"`"+skslp+"`"+skslb+"`"+sksim+"`"+sksmk+"`"+jenis+"`"+final_mk;
        			li.add(info_mk);
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
    
    public static String getNamaDanSksttKrklm(int krklm_id) {
    	//satu kurikulum only
    	String nmkur = null;
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	
    	Vector v = null;
    	ListIterator li = null;
		try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		String sql_cmd = "SELECT NMKURKRKLM FROM KRKLM where IDKURKRKLM=?";
    		stmt = con.prepareStatement(sql_cmd); 
    		stmt.setInt(1, krklm_id);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			nmkur = ""+rs.getString("NMKURKRKLM");
    			
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
		return nmkur;
    }
    
    
    public static Vector getMkTipeSkripsi(int krklm_id, String tkn_col_nmm) {
    	//satu kurikulum only
    	Vector v_list_mk = null;
    	String nmkur = null;
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	
    	Vector v = null;
    	ListIterator li = null;
		try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		StringTokenizer st = new StringTokenizer(tkn_col_nmm,",");
    		int tot_token = st.countTokens();
    		String sql_cmd = "SELECT "+tkn_col_nmm+" FROM MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=? and JENISMAKUL=5 and STKMKMAKUL='A';";
    		stmt = con.prepareStatement(sql_cmd); 
    		stmt.setInt(1, krklm_id);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			v_list_mk = new Vector();
    			li = v_list_mk.listIterator();
    			do {
    				String tmp = null;
    				for(int i=1;i<=tot_token;i++) {
    					if(tmp ==null) {
    						tmp = new String(rs.getString(i));
    					}
    					else {
    						tmp = tmp +"~"+rs.getString(i);
    					}	
    				}
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
		return v_list_mk;
    }
    
    public Vector getMkGivenSmsAndKrklmId(int target_sms, int krklm_id) {
    	//satu kurikulum only
    	Vector v_list_mk = null;
    	ListIterator li = null;
		try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get maks semester
    		stmt = con.prepareStatement("SELECT NMKURKRKLM,SKSTTKRKLM,SMSTTKRKLM,IDKMKMAKUL,KDKMKMAKUL,SEMESMAKUR,SKSTMMAKUL,SKSPRMAKUL,SKSLPMAKUL,SKSLBMAKUL,SKSIMMAKUL,JENISMAKUL,FINAL_MK FROM KRKLM inner join MAKUR on IDKURKRKLM=IDKURMAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURKRKLM=? order by SEMESMAKUR desc,FINAL_MK limit 1");
    		stmt.setInt(1, krklm_id);
    		rs = stmt.executeQuery();
    		rs.next();
    		v_list_mk = new Vector();
    		li = v_list_mk.listIterator();
    		
    		int i=1;
    		String nmkur_mk = ""+rs.getString(i++);
    		String skstt_mk = ""+rs.getInt(i++);
    		int smstt_mk = rs.getInt(i++);
    		String idkmk_mk = ""+rs.getInt(i++);
    		String kdkmk_mk = ""+rs.getString(i++);
    		String semes_mk = ""+rs.getString(i++);
    		int skstm_mk = rs.getInt(i++);
    		int skspr_mk = rs.getInt(i++);
    		int skslp_mk = rs.getInt(i++);
    		int skslb_mk = rs.getInt(i++);
    		int sksim_mk = rs.getInt(i++);
    		int sksmk_mk = skstm_mk+skspr_mk+skslp_mk+skslb_mk+sksim_mk;
    		String jenis_mk = ""+rs.getString(i++);
    		String final_mk = ""+rs.getBoolean(i++);
    		
    		
    		if(target_sms<=smstt_mk) {
    			//sesuai
    			String sql_cmd = "SELECT NMKURKRKLM,SKSTTKRKLM,SMSTTKRKLM,IDKMKMAKUL,KDKMKMAKUL,SEMESMAKUR,SKSTMMAKUL,SKSPRMAKUL,SKSLPMAKUL,SKSLBMAKUL,SKSIMMAKUL,JENISMAKUL,FINAL_MK FROM KRKLM inner join MAKUR on IDKURKRKLM=IDKURMAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURKRKLM=? and SEMESMAKUR=? order by SEMESMAKUR,FINAL_MK";
        		stmt = con.prepareStatement(sql_cmd); 
        		stmt.setInt(1, krklm_id);
        		stmt.setInt(2, target_sms);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			i=1;
        			nmkur_mk = ""+rs.getString(i++);
            		skstt_mk = ""+rs.getInt(i++);
            		smstt_mk = rs.getInt(i++);
            		idkmk_mk = ""+rs.getInt(i++);
            		kdkmk_mk = ""+rs.getString(i++);
            		semes_mk = ""+rs.getString(i++);
            		skstm_mk = rs.getInt(i++);
            		skspr_mk = rs.getInt(i++);
            		skslp_mk = rs.getInt(i++);
            		skslb_mk = rs.getInt(i++);
            		sksim_mk = rs.getInt(i++);
            		sksmk_mk = skstm_mk+skspr_mk+skslp_mk+skslb_mk+sksim_mk;
            		jenis_mk = ""+rs.getString(i++);
            		final_mk = ""+rs.getBoolean(i++);
            		li.add(kdkmk_mk+"`"+sksmk_mk+"`"+idkmk_mk);
        		}
    		}
    		else {
    			//mk final only
    			li.add(kdkmk_mk+"`"+sksmk_mk+"`"+idkmk_mk);
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
		return v_list_mk;
    }
    
    public Vector getListKurikulumProdi(String kdpst) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		if(con==null || con.isClosed()) {
    		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from KRKLM where KDPSTKRKLM=? order by IDKURKRKLM");
    			stmt.setString(1, kdpst);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				int idkur = rs.getInt("IDKURKRKLM");
    				String kdkur = rs.getString("NMKURKRKLM");
    				String stkur = rs.getString("STKURKRKLM");
    				String thsms1 = rs.getString("STARTTHSMS");
    				String thsms2 = rs.getString("ENDEDTHSMS");
    				String konsen = rs.getString("KONSENTRASI");
    				
    				String skstt = rs.getString("SKSTTKRKLM");
    				String smstt = rs.getString("SMSTTKRKLM");
    				String wajib = rs.getString("SKSTTWAJIB");
    				String option = rs.getString("SKSTTPILIH");
    				li.add(idkur+","+kdkur+","+stkur+","+thsms1+","+thsms2+","+konsen+","+skstt+","+smstt+","+wajib+","+option);
    			}
    		}
    		else {
    			stmt = con.prepareStatement("select * from KRKLM where KDPSTKRKLM=? order by IDKURKRKLM");
    			stmt.setString(1, kdpst);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				int idkur = rs.getInt("IDKURKRKLM");
    				String kdkur = rs.getString("NMKURKRKLM");
    				String stkur = rs.getString("STKURKRKLM");
    				String thsms1 = rs.getString("STARTTHSMS");
    				String thsms2 = rs.getString("ENDEDTHSMS");
    				String konsen = rs.getString("KONSENTRASI");
    				
    				String skstt = rs.getString("SKSTTKRKLM");
    				String smstt = rs.getString("SMSTTKRKLM");
    				String wajib = rs.getString("SKSTTWAJIB");
    				String option = rs.getString("SKSTTPILIH");
    				li.add(idkur+","+kdkur+","+stkur+","+thsms1+","+thsms2+","+konsen+","+skstt+","+smstt+","+wajib+","+option);
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

}

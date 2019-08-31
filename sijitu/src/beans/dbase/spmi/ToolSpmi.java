package beans.dbase.spmi;

import beans.dbase.SearchDb;
import beans.dbase.spmi.riwayat.pengendalian.SrcHistEvaluasi;
import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Constant;
import beans.tools.Converter;
import beans.tools.Tool;

import java.io.File;
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
 * Session Bean implementation class Tool
 */
@Stateless
@LocalBean
public class ToolSpmi extends SearchDb {
	String operatorNpm;
	String operatorNmm;
	String tknOperatorNickname;
	boolean petugas;
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;  
	//String kode_univ = "USG";
	String kode_univ;
	final static String sqlGetTotQandA = "select D.ID " + 
			"	from STANDARD_MASTER_TABLE A " + 
			"    inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD " + 
			"    inner join STANDARD_ISI_TABLE C on B.ID_STD=C.ID_STD " + 
			"    inner join STANDARD_ISI_QUESTION D on C.ID=D.ID_STD_ISI " + 
			"    where A.ID_MASTER_STD=? and C.AKTIF=true"; 
    /**
     * @see SearchDb#SearchDb()
     */
    public ToolSpmi() {
        super();
        this.kode_univ = getKodeSingkatNamaPerguruanTinggi();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public ToolSpmi(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
    	this.kode_univ = getKodeSingkatNamaPerguruanTinggi();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(Connection)
     */
    public ToolSpmi(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    /*
     * deprecated
     */
    public int cekThsmsNowMasukPeriodeKeberapa(int id_std_isi, int id_versi) {
    	int target_periode_ke = 0;
    	String thsms_now = Checker.getThsmsNow();
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TARGET_THSMS_1,TARGET_THSMS_2,TARGET_THSMS_3,TARGET_THSMS_4,TARGET_THSMS_5,TARGET_THSMS_6,TARGET_THSMS_1_UNIT,UNIT_PERIOD_USED,LAMA_NOMINAL_PER_PERIOD,TARGET_PERIOD_START from STANDARD_ISI_TABLE  inner join STANDARD_VERSION on ID=ID_STD_ISI where ID_VERSI=? and ID_STD_ISI=?");
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	rs = stmt.executeQuery();
        	rs.next();
        	String target1 = ""+rs.getString(1);
        	String target2 = ""+rs.getString(2);
        	String target3 = ""+rs.getString(3);
        	String target4 = ""+rs.getString(4);
        	String target5 = ""+rs.getString(5);
        	String target6 = ""+rs.getString(6);
        	String unit_target = ""+rs.getString(7);
        	String thn_apa_sms = ""+rs.getString(8);
        	String lama_interval = ""+rs.getString(9);
        	String initial_period = ""+rs.getString(10);
        	String sta_period = new String(initial_period);
        	String end_period = new String(sta_period);
        	
        	//cek unit yg digunakan
        	if(thn_apa_sms.equalsIgnoreCase("sms")) {
        		if(initial_period.length()==5) {
        			//cari TARGET PERIODE KE BERAPA dgn membandingkan dgn thsms now
        			boolean match = false;
        			//loop maximum of 6 target periode
        			for(target_periode_ke=0;target_periode_ke<6 && !match;) {
        				target_periode_ke++;
        				//get range periode target
        				//System.out.println("iter ke=="+target_periode_ke);
        				
        				//hitung end period
        				for(int j=1;j<Integer.parseInt(lama_interval);j++) {
        					end_period = Tool.returnNextThsmsGivenTpAntara(end_period);
        				}
        				//check thsms now masuk ke rang sta & end
        				//System.out.println(thsms_now+" vs "+sta_period);
        				//System.out.println(thsms_now+" vs "+end_period);
        				if(thsms_now.compareToIgnoreCase(sta_period)>=0 && thsms_now.compareToIgnoreCase(end_period)<=0) {
        					match = true;
        				}
        				
        				if(!match) {
        					//prep sta period
            				sta_period = Tool.returnNextThsmsGivenTpAntara(end_period);
            				end_period = new String(sta_period);
            				//System.out.println("next  ter sta_priod "+sta_period);
            				//System.out.println("next  ter sta_priod "+end_period);	
        				}
        				
        			}
        			
        		}
        		else {
        			//tidak valid
        		}
        	}
        	else if(thn_apa_sms.equalsIgnoreCase("thn")) {
        		int int_tahun_now = Integer.parseInt(AskSystem.getCurrentYear());
        		//System.out.println("int_thn_now="+int_tahun_now);
        		if(initial_period.length()==4) {
        			int int_sta_period = Integer.parseInt(initial_period);
                	int int_end_period = int_sta_period;
            		
            		
        			//cari TARGET PERIODE KE BERAPA dgn membandingkan dgn thsms now
        			boolean match = false;
        			//loop maximum of 6 target periode
        			for(target_periode_ke=0;target_periode_ke<6 && !match;) {
        				target_periode_ke++;
        				//get range periode target
        				//System.out.println("iter ke=="+target_periode_ke);
        				
        				//hitung end period
        				for(int j=1;j<Integer.parseInt(lama_interval);j++) {
        					//end_period = Tool.returnNextThsmsGivenTpAntara(end_period);
        					int_end_period++;
        				}
        				//check thsms now masuk ke rang sta & end
        				//System.out.println(thsms_now+" vs "+sta_period);
        				//System.out.println(thsms_now+" vs "+end_period);
        				//if(thsms_now.compareToIgnoreCase(sta_period)>=0 && thsms_now.compareToIgnoreCase(end_period)<=0) {
        				if(int_tahun_now>=int_sta_period && int_tahun_now<=int_end_period) {
        					match = true;
        				}
        				
        				if(!match) {
        					//prep sta period
            				//sta_period = Tool.returnNextThsmsGivenTpAntara(end_period);
            				//end_period = new String(sta_period);
        					int_sta_period++;
        					int_end_period++;
            				//System.out.println("next  ter sta_priod "+sta_period);
            				//System.out.println("next  ter sta_priod "+end_period);	
        				}
        				
        			}
        			
        		}
        		else {
        			//tidak valid
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
    	return target_periode_ke;
    }
    
    public static int getNorutTargetParamIndikatorPeriod(String starting_period, String unit_period, String lama_per_period) {
    	int norut=0;
    	int jarak = Integer.parseInt(lama_per_period);
    	String thsms_now = Checker.getThsmsNow();
    	String tahun_now = thsms_now.substring(0, 4);
    	if(unit_period.equalsIgnoreCase("thn")) {
    		starting_period = starting_period.substring(0, 4);
    		if(starting_period.equalsIgnoreCase(tahun_now)) {
    			norut=1;
    		}
    		else {
    			int thn_now = Integer.parseInt(tahun_now);
    			int thn_sta = Integer.parseInt(starting_period);
    			for(norut=1;thn_now>thn_sta;norut++) {
    				thn_sta = thn_sta + jarak;
    			}
    		}
    	}
    	else if(unit_period.equalsIgnoreCase("sms")) {
    		if(starting_period.length()==4) {
    			starting_period = starting_period+"1";
    		}
    		if(starting_period.equalsIgnoreCase(thsms_now)) {
    			norut=1;
    		}
    		else {
    			
    			for(norut=1;thsms_now.compareToIgnoreCase(starting_period)>0;norut++) {
    				for(int i=0;i<jarak;i++) {
    					starting_period=Tool.returnNextThsmsGivenTpAntara(starting_period);
    				}
    			}
    		}
    	}
    	return norut;
    }
    
    
    public int getNorutTerakhirManEvaluasiPelaksanaanStd(int std_isi_id, int versi_std_isi) {
    	int norut=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select NORUT from STANDARD_MANUAL_EVALUASI where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc limit 1");
    		stmt.setInt(1, versi_std_isi);
    		stmt.setInt(2, std_isi_id);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			norut = rs.getInt(1);
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
    	
    	return norut;
    }
    
    public boolean isStandardAdaYgAktif(int id_std) {
    	boolean aktif=false;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from STANDARD_TIPE_VERSION where ID_STD=? and TGL_STA is not null and TGL_END is null");
    		stmt.setInt(1, id_std);
    		//stmt.setInt(2, id_versi);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			//String tgl_sta = ""+rs.getString("TGL_STA");
    			//String tgl_end = ""+rs.getString("TGL_END");
    			//if(!Checker.isStringNullOrEmpty(tgl_sta)&&Checker.isStringNullOrEmpty(tgl_end)) {
    				aktif = true;
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
    	
    	return aktif;
    }
    
    public static java.sql.Date getTglStaIfStandardAktif(int id_std, int id_versi) {
    	java.sql.Date tgl_sta_aktif=null;
    	Connection con1=null;
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con1 = ds1.getConnection();
    		stmt1 = con1.prepareStatement("select * from STANDARD_TIPE_VERSION where ID_STD=? and ID_VERSI=?");
    		stmt1.setInt(1, id_std);
    		stmt1.setInt(2, id_versi);
    		rs1 = stmt1.executeQuery();
    		if(rs1.next()) {
    			String tgl_sta = ""+rs1.getString("TGL_STA");
    			String tgl_end = ""+rs1.getString("TGL_END");
    			if(!Checker.isStringNullOrEmpty(tgl_sta)&&Checker.isStringNullOrEmpty(tgl_end)) {
    				tgl_sta_aktif = java.sql.Date.valueOf(tgl_sta);
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
        	if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
		    if (con1!=null) try { con1.close();} catch (Exception ignore){}
        }	
    	
    	return tgl_sta_aktif;
    }
    
    
    
    public int getNorutTerakhirManEvaluasiPelaksanaanStdUmum(int id_std) {
    	int norut=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select VERSI_ID from STANDARD_MANUAL_EVALUASI_UMUM where ID_STD=? order by VERSI_ID desc limit 1");
    		stmt.setInt(1, id_std);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			norut = rs.getInt(1);
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
    	
    	return norut;
    }
    


    
    public int getNorutTerakhirManPerencanaanStd(int std_isi_id, int versi_std_isi) {
    	int norut=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select NORUT from STANDARD_MANUAL_PERENCANAAN where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc limit 1");
    		stmt.setInt(1, versi_std_isi);
    		stmt.setInt(2, std_isi_id);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			norut = rs.getInt(1);
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
    	
    	return norut;
    }

    
    public int getNorutTerakhirManPerencanaanStdUmum(int id_std) {
    	int norut=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select VERSI_ID from STANDARD_MANUAL_PERENCANAAN_UMUM where ID_STD=? order by VERSI_ID desc limit 1");
    		stmt.setInt(1, id_std);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			norut = rs.getInt(1);
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
    	
    	return norut;
    }
    
    public int getNorutTerakhirManPelaksanaanStdUmum(int id_std) {
    	int norut=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select VERSI_ID from STANDARD_MANUAL_PELAKSANAAN_UMUM where ID_STD=? order by VERSI_ID desc limit 1");
    		stmt.setInt(1, id_std);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			norut = rs.getInt(1);
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
    	
    	return norut;
    }
    
    public int getNorutTerakhirManPelaksanaanStd(int std_isi_id, int versi_std_isi) {
    	int norut=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select NORUT from STANDARD_MANUAL_PELAKSANAAN where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc limit 1");
    		stmt.setInt(1, versi_std_isi);
    		stmt.setInt(2, std_isi_id);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			norut = rs.getInt(1);
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
    	
    	return norut;
    }
    
    
    public int getNorutTerakhirManPeningkatanStd(int std_isi_id, int versi_std_isi) {
    	int norut=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select NORUT from STANDARD_MANUAL_PENINGKATAN where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc limit 1");
    		stmt.setInt(1, versi_std_isi);
    		stmt.setInt(2, std_isi_id);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			norut = rs.getInt(1);
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
    	
    	return norut;
    }
    
    public int getNorutTerakhirManPeningkatanStdUmum(int id_std) {
    	int norut=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select VERSI_ID from STANDARD_MANUAL_PENINGKATAN_UMUM where ID_STD=? order by VERSI_ID desc limit 1");
    		stmt.setInt(1, id_std);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			norut = rs.getInt(1);
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
    	
    	return norut;
    }
    
    public int getNorutTerakhirManPengendalianPelaksanaanStdUmum(int id_std) {
    	int norut=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select VERSI_ID from STANDARD_MANUAL_PENGENDALIAN_UMUM where ID_STD=? order by VERSI_ID desc limit 1");
    		stmt.setInt(1, id_std);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			norut = rs.getInt(1);
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
    	
    	return norut;
    }
    
    public int getNorutTerakhirManPengendalianPelaksanaanStd(int std_isi_id, int versi_std_isi) {
    	int norut=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select NORUT from STANDARD_MANUAL_PENGENDALIAN where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc limit 1");
    		stmt.setInt(1, versi_std_isi);
    		stmt.setInt(2, std_isi_id);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			norut = rs.getInt(1);
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
    	
    	return norut;
    }
    
    public String getPenomoranDokMutu(String id_std_isi, String id_versi, String tipe_dok, String siklus_proses) {
    	String final_kode=null;
    	
    	try {
    		String kode_master_std = "-";
    		String nomor_std = "-";
    		String nama_master_std = "-";
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		String sql = "SELECT KODE,C.ID_TIPE_STD,C.KET_TIPE_STD FROM STANDARD_VERSION A inner join STANDARD_ISI_TABLE B on A.ID_STD_ISI=B.ID inner join STANDARD_TABLE C on B.ID_STD=C.ID_STD inner join STANDARD_MASTER_TABLE D on C.ID_MASTER_STD=D.ID_MASTER_STD where A.ID_VERSI=? and A.ID_STD_ISI=?";
    		stmt = con.prepareStatement(sql);
    		stmt.setInt(1, Integer.parseInt(id_versi));
    		stmt.setInt(2, Integer.parseInt(id_std_isi));
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			kode_master_std = rs.getString(1);
    			nomor_std = rs.getString(2);
    		}
    		
    		if(tipe_dok.equalsIgnoreCase("STANDARD")||tipe_dok.equalsIgnoreCase("STANDAR")) {
    			tipe_dok = "STD";
    		}
    		else if(tipe_dok.equalsIgnoreCase("MANUAL")||tipe_dok.equalsIgnoreCase("MANUAL")) {
    			tipe_dok = "MNL";
    		}
    		
    		if(siklus_proses.contains("RUMUS")||siklus_proses.contains("rumus")) {
    			siklus_proses = "prms";
    		}
    		else if(siklus_proses.contains("LAKSA")||siklus_proses.contains("laksa")) {
    			siklus_proses = "plks";
    		}
    		else if(siklus_proses.contains("RENCANA")||siklus_proses.contains("rencana")) {
    			siklus_proses = "prnc";
    		}
    		else if(siklus_proses.contains("NETAP")||siklus_proses.contains("netap")) {
    			siklus_proses = "pntp";
    		}
    		else if(siklus_proses.contains("EVAL")||siklus_proses.contains("eval")) {
    			siklus_proses = "eval";
    		}
    		else if(siklus_proses.contains("DALI")||siklus_proses.contains("dali")) {
    			siklus_proses = "pgdl";
    		}
    		else if(siklus_proses.contains("NINGKAT")||siklus_proses.contains("ningkat")) {
    			siklus_proses = "pnkt";
    		}
    		final_kode = kode_univ.toLowerCase()+"/SPMI/"+tipe_dok+"/"+tipe_dok.substring(0, 1)+"."+siklus_proses+"."+kode_master_std+"."+nomor_std;
    	}
    	catch(Exception e) {
    		
    	}
    	return final_kode;
    }
    
    public String getPenomoranDokMutuUmum(String id_std, String tipe_dok, String siklus_proses) {
    	String final_kode=null;
    	
    	try {
    		String kode_master_std = "-";
    		String nomor_std = "-";
    		String nama_master_std = "-";
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//String sql = "SELECT KODE,C.ID_TIPE_STD,C.KET_TIPE_STD FROM STANDARD_VERSION A inner join STANDARD_ISI_TABLE B on A.ID_STD_ISI=B.ID inner join STANDARD_TABLE C on B.ID_STD=C.ID_STD inner join STANDARD_MASTER_TABLE D on C.ID_MASTER_STD=D.ID_MASTER_STD where A.ID_VERSI=? and A.ID_STD_ISI=?";
    		String sql = "SELECT KODE,C.ID_TIPE_STD,C.KET_TIPE_STD FROM STANDARD_TABLE C inner join STANDARD_MASTER_TABLE D on C.ID_MASTER_STD=D.ID_MASTER_STD where C.ID_STD=?";
    		stmt = con.prepareStatement(sql);
    		stmt.setInt(1, Integer.parseInt(id_std));
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			kode_master_std = rs.getString(1);
    			nomor_std = rs.getString(2);
    		}
    		
    		if(tipe_dok.equalsIgnoreCase("STANDARD")||tipe_dok.equalsIgnoreCase("STANDAR")) {
    			tipe_dok = "STD";
    		}
    		else if(tipe_dok.equalsIgnoreCase("MANUAL")||tipe_dok.equalsIgnoreCase("MANUAL")) {
    			tipe_dok = "MNL";
    		}
    		
    		if(siklus_proses.contains("RUMUS")||siklus_proses.contains("rumus")) {
    			siklus_proses = "prms";
    		}
    		else if(siklus_proses.contains("LAKSA")||siklus_proses.contains("laksa")) {
    			siklus_proses = "plks";
    		}
    		else if(siklus_proses.contains("RENCANA")||siklus_proses.contains("rencana")) {
    			siklus_proses = "prnc";
    		}
    		else if(siklus_proses.contains("NETAP")||siklus_proses.contains("netap")) {
    			siklus_proses = "pntp";
    		}
    		else if(siklus_proses.contains("EVAL")||siklus_proses.contains("eval")) {
    			siklus_proses = "eval";
    		}
    		else if(siklus_proses.contains("DALI")||siklus_proses.contains("dali")) {
    			siklus_proses = "pgdl";
    		}
    		else if(siklus_proses.contains("NINGKAT")||siklus_proses.contains("ningkat")) {
    			siklus_proses = "pnkt";
    		}
    		final_kode = kode_univ.toLowerCase()+"/SPMI/"+tipe_dok+"/"+tipe_dok.substring(0, 1)+"."+siklus_proses+"."+kode_master_std+"."+nomor_std;
    	}
    	catch(Exception e) {
    		
    	}
    	return final_kode;
    }
    
    public String getPenomoranStandarMutuUmum(String id_std) {
    	String final_kode=null;
    	String tipe_dok = "STANDAR";
    	try {
    		String kode_master_std = "-";
    		String nomor_std = "-";
    		String nama_master_std = "-";
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//String sql = "SELECT KODE,C.ID_TIPE_STD,C.KET_TIPE_STD FROM STANDARD_VERSION A inner join STANDARD_ISI_TABLE B on A.ID_STD_ISI=B.ID inner join STANDARD_TABLE C on B.ID_STD=C.ID_STD inner join STANDARD_MASTER_TABLE D on C.ID_MASTER_STD=D.ID_MASTER_STD where A.ID_VERSI=? and A.ID_STD_ISI=?";
    		String sql = "SELECT KODE,C.ID_TIPE_STD,C.KET_TIPE_STD FROM STANDARD_TABLE C inner join STANDARD_MASTER_TABLE D on C.ID_MASTER_STD=D.ID_MASTER_STD where C.ID_STD=?";
    		stmt = con.prepareStatement(sql);
    		stmt.setInt(1, Integer.parseInt(id_std));
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			kode_master_std = rs.getString(1);
    			nomor_std = rs.getString(2);
    		}
    		
    		if(tipe_dok.equalsIgnoreCase("STANDARD")||tipe_dok.equalsIgnoreCase("STANDAR")) {
    			tipe_dok = "STD";
    		}
    		else if(tipe_dok.equalsIgnoreCase("MANUAL")||tipe_dok.equalsIgnoreCase("MANUAL")) {
    			tipe_dok = "MNL";
    		}
    		
    		final_kode = kode_univ.toLowerCase()+"/SPMI/"+tipe_dok+"/"+kode_master_std+"."+nomor_std;
    	}
    	catch(Exception e) {
    		
    	}
    	return final_kode;
    }

    
    public String getPenetapanTanggalManual(int versi_std_isi, int std_isi_id, int norut_man, String tipe_proses_manual, String pilih_ppepp) {
    	String dt = null; 
    	String nm_tabel = "";
    	if(pilih_ppepp.equalsIgnoreCase("perencanaan")) {
    		nm_tabel="PERENCANAAN";
    	}
    	else if(pilih_ppepp.equalsIgnoreCase("pelaksanaan")) {
    		nm_tabel="PELAKSANAAN";
    	}
    	else if(pilih_ppepp.equalsIgnoreCase("evaluasi")) {
    		nm_tabel="EVALUASI";
    	}
    	else if(pilih_ppepp.equalsIgnoreCase("pengendalian")) {
    		nm_tabel="PENGENDALIAN";
    	}
    	else if(pilih_ppepp.equalsIgnoreCase("peningkatan")) {
    		nm_tabel="PENINGKATAN";
    	}
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		if(tipe_proses_manual.contains("rumus")) {
    			//stmt = con.prepareStatement("select TGL_END_KEGIATAN from RIWAYAT_"+nm_tabel+" where VERSI_ID=? and STD_ISI_ID=? and NORUT=? and TGL_RUMUS=true");
    			stmt = con.prepareStatement("select TGL_RUMUS from STANDARD_MANUAL_"+nm_tabel+" where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
    		}
    		else if(tipe_proses_manual.contains("eriksa")) {
    			//stmt = con.prepareStatement("select TGL_END_KEGIATAN from RIWAYAT_"+nm_tabel+" where VERSI_ID=? and STD_ISI_ID=? and NORUT=? and TGL_CEK=true");
    			stmt = con.prepareStatement("select TGL_CEK from STANDARD_MANUAL_"+nm_tabel+" where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
    		}
    		else if(tipe_proses_manual.contains("setuju")) {
    			//stmt = con.prepareStatement("select TGL_END_KEGIATAN from RIWAYAT_"+nm_tabel+" where VERSI_ID=? and STD_ISI_ID=? and NORUT=? and TGL_STUJU=true");
    			stmt = con.prepareStatement("select TGL_STUJU from STANDARD_MANUAL_"+nm_tabel+" where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
    		}
    		else if(tipe_proses_manual.contains("etap")) {
    			//stmt = con.prepareStatement("select TGL_END_KEGIATAN from RIWAYAT_"+nm_tabel+" where VERSI_ID=? and STD_ISI_ID=? and NORUT=? and TGL_TETAP=true");
    			stmt = con.prepareStatement("select TGL_TETAP from STANDARD_MANUAL_"+nm_tabel+" where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
    		}
    		else if(tipe_proses_manual.contains("dali")) {
    			//stmt = con.prepareStatement("select TGL_END_KEGIATAN from RIWAYAT_"+nm_tabel+" where VERSI_ID=? and STD_ISI_ID=? and NORUT=? and TGL_KENDALI=true");
    			stmt = con.prepareStatement("select TGL_KENDALI from STANDARD_MANUAL_"+nm_tabel+" where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
    		}
    		
    		stmt.setInt(1, versi_std_isi);
    		stmt.setInt(2, std_isi_id);
    		stmt.setInt(3, norut_man);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			dt = rs.getString(1);
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
    	
    	return dt;
    }
    
    
    public String getPenetapanTanggalManualUmum(int id_versi, int id_std, String tipe_proses_manual, String pilih_ppepp) {
    	String dt = null; 
    	String nm_tabel = "";
    	if(pilih_ppepp.equalsIgnoreCase("perencanaan")) {
    		nm_tabel="PERENCANAAN";
    	}
    	else if(pilih_ppepp.equalsIgnoreCase("pelaksanaan")) {
    		nm_tabel="PELAKSANAAN";
    	}
    	else if(pilih_ppepp.equalsIgnoreCase("evaluasi")) {
    		nm_tabel="EVALUASI";
    	}
    	else if(pilih_ppepp.equalsIgnoreCase("pengendalian")) {
    		nm_tabel="PENGENDALIAN";
    	}
    	else if(pilih_ppepp.equalsIgnoreCase("peningkatan")) {
    		nm_tabel="PENINGKATAN";
    	}
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		if(tipe_proses_manual.contains("rumus")) {
    			//stmt = con.prepareStatement("select TGL_END_KEGIATAN from RIWAYAT_"+nm_tabel+" where VERSI_ID=? and STD_ISI_ID=? and NORUT=? and TGL_RUMUS=true");
    			stmt = con.prepareStatement("select TGL_RUMUS from STANDARD_MANUAL_"+nm_tabel+"_UMUM where VERSI_ID=? and ID_STD=?");
    		}
    		else if(tipe_proses_manual.contains("eriksa")) {
    			//stmt = con.prepareStatement("select TGL_END_KEGIATAN from RIWAYAT_"+nm_tabel+" where VERSI_ID=? and STD_ISI_ID=? and NORUT=? and TGL_CEK=true");
    			stmt = con.prepareStatement("select TGL_CEK from STANDARD_MANUAL_"+nm_tabel+"_UMUM where VERSI_ID=? and ID_STD=?");
    		}
    		else if(tipe_proses_manual.contains("setuju")) {
    			//stmt = con.prepareStatement("select TGL_END_KEGIATAN from RIWAYAT_"+nm_tabel+" where VERSI_ID=? and STD_ISI_ID=? and NORUT=? and TGL_STUJU=true");
    			stmt = con.prepareStatement("select TGL_STUJU from STANDARD_MANUAL_"+nm_tabel+"_UMUM where VERSI_ID=? and ID_STD=?");
    		}
    		else if(tipe_proses_manual.contains("etap")) {
    			//stmt = con.prepareStatement("select TGL_END_KEGIATAN from RIWAYAT_"+nm_tabel+" where VERSI_ID=? and STD_ISI_ID=? and NORUT=? and TGL_TETAP=true");
    			stmt = con.prepareStatement("select TGL_TETAP from STANDARD_MANUAL_"+nm_tabel+"_UMUM where VERSI_ID=? and ID_STD=?");
    		}
    		else if(tipe_proses_manual.contains("dali")) {
    			//stmt = con.prepareStatement("select TGL_END_KEGIATAN from RIWAYAT_"+nm_tabel+" where VERSI_ID=? and STD_ISI_ID=? and NORUT=? and TGL_KENDALI=true");
    			stmt = con.prepareStatement("select TGL_KENDALI from STANDARD_MANUAL_"+nm_tabel+"_UMUM where VERSI_ID=? and ID_STD=?");
    		}
    		
    		stmt.setInt(1, id_versi);
    		stmt.setInt(2, id_std);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			dt = rs.getString(1);
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
    	
    	return dt;
    }
    
    
    /*
    public String getRootManualSpmiFolder(String id_std) {
    	String root_manual_spmi_folder="/home/cg2/spmi/"+id_std+"/manual/";
    	return root_manual_spmi_folder;
    }
    public String getRootStandarSpmiFolder(String id_std) {
    	String root_standar_spmi_folder="/home/cg2/spmi/"+id_std+"/standar/";
    	return root_standar_spmi_folder;
    }
    
    public String getRootSpmiFolder(String id_std) {
    	String root_spmi_folder="/home/cg2/spmi/"+id_std;
    	return root_spmi_folder;
    }
    */
    
    public String getRootManualSpmiFolder(String id_versi, String ppepp) {
    	//System.out.println("kode_univ.toLowerCase()="+kode_univ.toLowerCase());
    	//System.out.println("id_versi="+id_versi);
    	String root_manual_spmi_folder="/home/"+kode_univ.toLowerCase()+"/spmi/"+id_versi+"/"+ppepp.toLowerCase()+"/manual/";
    	return root_manual_spmi_folder;
    }
    
    public String getRootStandarSpmiFolder(String id_versi, String ppepp) {
    	String root_standar_spmi_folder="/home/"+kode_univ.toLowerCase()+"/spmi/"+id_versi+"/"+ppepp.toLowerCase()+"/standar/";
    	return root_standar_spmi_folder;
    }
    
    public String getRootStandarAmiFolder() {
    	String root_standar_ami_folder="/home/"+kode_univ.toLowerCase()+"/spmi/ami/";
    	return root_standar_ami_folder;
    }
    
    public String getRootDokMutuFolder() {
    	String root_dokumen_mutu_folder="/home/"+kode_univ.toLowerCase()+"/spmi/dokumen/";
    	return root_dokumen_mutu_folder;
    }
    
    public String getRootSpmiFolder(String id_versi, String ppepp) {
    	String root_spmi_folder="/home/"+kode_univ.toLowerCase()+"/spmi/"+id_versi+"/"+ppepp.toLowerCase()+"/";
    	return root_spmi_folder;
    }
    
    public int getNorutTerakhirManual(String versi_id, String std_isi_id, String pilih_ppepp) {
    	int norut=1;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		if(pilih_ppepp.equalsIgnoreCase("perencanaan")) {
    			stmt=con.prepareStatement("select NORUT from STANDARD_MANUAL_PERENCANAAN where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc limit 1");
    			stmt.setInt(1, Integer.parseInt(versi_id));
    			stmt.setInt(2, Integer.parseInt(std_isi_id));
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				norut = rs.getInt(1);
    			}
    		}
    		else if(pilih_ppepp.equalsIgnoreCase("pelaksanaan")) {
    			stmt=con.prepareStatement("select NORUT from STANDARD_MANUAL_PELAKSANAAN where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc limit 1");
    			stmt.setInt(1, Integer.parseInt(versi_id));
    			stmt.setInt(2, Integer.parseInt(std_isi_id));
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				norut = rs.getInt(1);
    			}
    		}
    		else if(pilih_ppepp.equalsIgnoreCase("evaluasi")) {
    			stmt=con.prepareStatement("select NORUT from STANDARD_MANUAL_EVALUASI where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc limit 1");
    			stmt.setInt(1, Integer.parseInt(versi_id));
    			stmt.setInt(2, Integer.parseInt(std_isi_id));
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				norut = rs.getInt(1);
    			}
    		}
    		else if(pilih_ppepp.equalsIgnoreCase("pengendalian")) {
    			stmt=con.prepareStatement("select NORUT from STANDARD_MANUAL_PENGENDALIAN where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc limit 1");
    			stmt.setInt(1, Integer.parseInt(versi_id));
    			stmt.setInt(2, Integer.parseInt(std_isi_id));
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				norut = rs.getInt(1);
    			}
    		}
    		else if(pilih_ppepp.equalsIgnoreCase("peningkatan")) {
    			stmt=con.prepareStatement("select NORUT from STANDARD_MANUAL_PENINGKATAN where VERSI_ID=? and STD_ISI_ID=? order by NORUT desc limit 1");
    			stmt.setInt(1, Integer.parseInt(versi_id));
    			stmt.setInt(2, Integer.parseInt(std_isi_id));
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				norut = rs.getInt(1);
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
    	
    	return norut;
    }
    
    public int getNorutTerakhirManualUmum(String id_std, String pilih_ppepp) {
    	int norut=1;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		if(pilih_ppepp.equalsIgnoreCase("perencanaan")) {
    			stmt=con.prepareStatement("select VERSI_ID from STANDARD_MANUAL_PERENCANAAN_UMUM where ID_STD=? order by VERSI_ID desc limit 1");
    			stmt.setInt(1, Integer.parseInt(id_std));
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				norut = rs.getInt(1);
    			}
    		}
    		else if(pilih_ppepp.equalsIgnoreCase("pelaksanaan")) {
    			stmt=con.prepareStatement("select VERSI_ID from STANDARD_MANUAL_PELAKSANAAN_UMUM where ID_STD=? order by VERSI_ID desc limit 1");
    			stmt.setInt(1, Integer.parseInt(id_std));
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				norut = rs.getInt(1);
    			}
    		}
    		else if(pilih_ppepp.equalsIgnoreCase("evaluasi")) {
    			stmt=con.prepareStatement("select VERSI_ID from STANDARD_MANUAL_EVALUASI_UMUM where ID_STD=? order by VERSI_ID desc limit 1");
    			stmt.setInt(1, Integer.parseInt(id_std));
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				norut = rs.getInt(1);
    			}
    		}
    		else if(pilih_ppepp.equalsIgnoreCase("pengendalian")) {
    			stmt=con.prepareStatement("select VERSI_ID from STANDARD_MANUAL_PENGENDALIAN_UMUM where ID_STD=? order by VERSI_ID desc limit 1");
    			stmt.setInt(1, Integer.parseInt(id_std));
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				norut = rs.getInt(1);
    			}
    		}
    		else if(pilih_ppepp.equalsIgnoreCase("peningkatan")) {
    			stmt=con.prepareStatement("select VERSI_ID from STANDARD_MANUAL_PENINGKATAN_UMUM where ID_STD=? order by VERSI_ID desc limit 1");
    			stmt.setInt(1, Integer.parseInt(id_std));
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				norut = rs.getInt(1);
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
    	
    	return norut;
    }
    
    /*
     * deprecated
     */
    public void createFolderStructure(String id_std_isi, String id_versi, String kdpst) {
    	String root_prodi_spmi_folder="/home/"+kode_univ.toLowerCase()+"/spmi/"+id_std_isi+"/"+kdpst+"/"+id_versi;
    	//perencanaan
    	int rev_akhir  = getNorutTerakhirManual(id_versi,id_std_isi,"perencanaan");
    	String dir_subdir = null;
    	for(int i=1;i<=rev_akhir;i++) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/perencanaan/rev_"+i+"/";
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		 new File(dir_subdir).mkdirs();
    	}
        //pelaksanaan 
    	rev_akhir  = getNorutTerakhirManual(id_versi,id_std_isi,"pelaksanaan");
    	dir_subdir = null;
    	for(int i=1;i<=rev_akhir;i++) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/pelaksanaan/rev_"+i+"/";
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		 new File(dir_subdir).mkdirs();
    	}
    	//evaluasi
    	rev_akhir  = getNorutTerakhirManual(id_versi,id_std_isi,"evaluasi");
    	dir_subdir = null;
    	for(int i=1;i<=rev_akhir;i++) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/evaluasi/rev_"+i+"/";
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		 new File(dir_subdir).mkdirs();
    	}
    	//pengendalian
    	rev_akhir  = getNorutTerakhirManual(id_versi,id_std_isi,"pengendalian");
    	dir_subdir = null;
    	for(int i=1;i<=rev_akhir;i++) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/pengendalian/rev_"+i+"/";
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		 new File(dir_subdir).mkdirs();
    	}
    	//peningkatan
    	rev_akhir  = getNorutTerakhirManual(id_versi,id_std_isi,"peningkatan");
    	dir_subdir = null;
    	for(int i=1;i<=rev_akhir;i++) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/peningkatan/rev_"+i+"/";
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		 new File(dir_subdir).mkdirs();
    	}
       
    }
    
    
    public void createSubFolderManualPerencanaan(String id_std_isi, String id_versi, String norut_man, String kdpst, String jenis_kegiatan, String nmm_kegiatan) {
    	String root_prodi_spmi_folder="/home/"+kode_univ.toLowerCase()+"/spmi/"+id_std_isi+"/"+kdpst+"/"+id_versi;
    	//perencanaan
    	while(nmm_kegiatan.contains(" ")) {
    		nmm_kegiatan = nmm_kegiatan.replace(" ", "_");
    	}
    	String dir_subdir = ""; 
    	if(jenis_kegiatan.contains("rumus")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/perencanaan/rev_"+norut_man.trim()+"/perumusan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	}
    	else if(jenis_kegiatan.contains("cek")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/perencanaan/rev_"+norut_man.trim()+"/pemeriksaan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	}
    	else if(jenis_kegiatan.contains("stuju")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/perencanaan/rev_"+norut_man.trim()+"/persetujuan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	} 
    	else if(jenis_kegiatan.contains("tetap")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/perencanaan/rev_"+norut_man.trim()+"/penetapan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	} 
		//System.out.println("dir_subdir="+dir_subdir);
    }	
    
    public void createSubFolderManualPelaksanaan(String id_std_isi, String id_versi, String norut_man, String kdpst, String jenis_kegiatan, String nmm_kegiatan) {
    	String root_prodi_spmi_folder="/home/"+kode_univ.toLowerCase()+"/spmi/"+id_std_isi+"/"+kdpst+"/"+id_versi;
    	//perencanaan
    	while(nmm_kegiatan.contains(" ")) {
    		nmm_kegiatan = nmm_kegiatan.replace(" ", "_");
    	}
    	String dir_subdir = ""; 
    	if(jenis_kegiatan.contains("rumus")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/pelaksanaan/rev_"+norut_man.trim()+"/perumusan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	}
    	else if(jenis_kegiatan.contains("cek")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/pelaksanaan/rev_"+norut_man.trim()+"/pemeriksaan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	}
    	else if(jenis_kegiatan.contains("stuju")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/pelaksanaan/rev_"+norut_man.trim()+"/persetujuan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	} 
    	else if(jenis_kegiatan.contains("tetap")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/pelaksanaan/rev_"+norut_man.trim()+"/penetapan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	} 
		//System.out.println("dir_subdir="+dir_subdir);
    }	
    
    public void createSubFolderManualPeningkatan(String id_std_isi, String id_versi, String norut_man, String kdpst, String jenis_kegiatan, String nmm_kegiatan) {
    	String root_prodi_spmi_folder="/home/"+kode_univ.toLowerCase()+"/spmi/"+id_std_isi+"/"+kdpst+"/"+id_versi;
    	//perencanaan
    	while(nmm_kegiatan.contains(" ")) {
    		nmm_kegiatan = nmm_kegiatan.replace(" ", "_");
    	}
    	String dir_subdir = ""; 
    	if(jenis_kegiatan.contains("rumus")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/Peningkatan/rev_"+norut_man.trim()+"/perumusan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	}
    	else if(jenis_kegiatan.contains("cek")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/Peningkatan/rev_"+norut_man.trim()+"/pemeriksaan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	}
    	else if(jenis_kegiatan.contains("stuju")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/Peningkatan/rev_"+norut_man.trim()+"/persetujuan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	} 
    	else if(jenis_kegiatan.contains("tetap")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/Peningkatan/rev_"+norut_man.trim()+"/penetapan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	} 
		//System.out.println("dir_subdir="+dir_subdir);
    }	
    
    public void createSubFolderManualEvaluasi(String id_std_isi, String id_versi, String norut_man, String kdpst, String jenis_kegiatan, String nmm_kegiatan) {
    	String root_prodi_spmi_folder="/home/"+kode_univ.toLowerCase()+"/spmi/"+id_std_isi+"/"+kdpst+"/"+id_versi;
    	//perencanaan
    	while(nmm_kegiatan.contains(" ")) {
    		nmm_kegiatan = nmm_kegiatan.replace(" ", "_");
    	}
    	String dir_subdir = ""; 
    	if(jenis_kegiatan.contains("rumus")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/evaluasi/rev_"+norut_man.trim()+"/perumusan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	}
    	else if(jenis_kegiatan.contains("cek")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/evaluasi/rev_"+norut_man.trim()+"/pemeriksaan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	}
    	else if(jenis_kegiatan.contains("stuju")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/evaluasi/rev_"+norut_man.trim()+"/persetujuan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	} 
    	else if(jenis_kegiatan.contains("tetap")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/evaluasi/rev_"+norut_man.trim()+"/penetapan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	} 
		//System.out.println("dir_subdir="+dir_subdir);
    }
    
    
    public void createSubFolderManualPengendalian(String id_std_isi, String id_versi, String norut_man, String kdpst, String jenis_kegiatan, String nmm_kegiatan) {
    	String root_prodi_spmi_folder="/home/"+kode_univ.toLowerCase()+"/spmi/"+id_std_isi+"/"+kdpst+"/"+id_versi;
    	//perencanaan
    	while(nmm_kegiatan.contains(" ")) {
    		nmm_kegiatan = nmm_kegiatan.replace(" ", "_");
    	}
    	String dir_subdir = ""; 
    	if(jenis_kegiatan.contains("rumus")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/pengendalian/rev_"+norut_man.trim()+"/perumusan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	}
    	else if(jenis_kegiatan.contains("cek")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/pengendalian/rev_"+norut_man.trim()+"/pemeriksaan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	}
    	else if(jenis_kegiatan.contains("stuju")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/pengendalian/rev_"+norut_man.trim()+"/persetujuan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	} 
    	else if(jenis_kegiatan.contains("tetap")) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/pengendalian/rev_"+norut_man.trim()+"/penetapan/"+nmm_kegiatan.trim();
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();	
    	} 
		//System.out.println("dir_subdir="+dir_subdir);
    }
    

    public void createFolderStructureManual(String id_std_isi, String id_versi, String kdpst) {
    	String root_prodi_spmi_folder="/home/"+kode_univ.toLowerCase()+"/spmi/"+id_std_isi+"/"+kdpst+"/"+id_versi;
    	//System.out.println("root_prodi_spmi_folder="+root_prodi_spmi_folder);
    	//perencanaan
    	int rev_akhir  = getNorutTerakhirManual(id_versi,id_std_isi,"perencanaan");
    	String dir_subdir = null;
    	for(int i=1;i<=rev_akhir;i++) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/perencanaan/rev_"+i+"/perumusan";
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();
    		dir_subdir = root_prodi_spmi_folder+"/manual/perencanaan/rev_"+i+"/pemeriksaan";
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();
    		dir_subdir = root_prodi_spmi_folder+"/manual/perencanaan/rev_"+i+"/persetujuan";
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();
    		dir_subdir = root_prodi_spmi_folder+"/manual/perencanaan/rev_"+i+"/penetapan";
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		new File(dir_subdir).mkdirs();
    	}
        //pelaksanaan 
    	rev_akhir  = getNorutTerakhirManual(id_versi,id_std_isi,"pelaksanaan");
    	dir_subdir = null;
    	for(int i=1;i<=rev_akhir;i++) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/pelaksanaan/rev_"+i+"/";
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		 new File(dir_subdir).mkdirs();
    	}
    	//evaluasi
    	rev_akhir  = getNorutTerakhirManual(id_versi,id_std_isi,"evaluasi");
    	dir_subdir = null;
    	for(int i=1;i<=rev_akhir;i++) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/evaluasi/rev_"+i+"/";
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		 new File(dir_subdir).mkdirs();
    	}
    	//pengendalian
    	rev_akhir  = getNorutTerakhirManual(id_versi,id_std_isi,"pengendalian");
    	dir_subdir = null;
    	for(int i=1;i<=rev_akhir;i++) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/pengendalian/rev_"+i+"/";
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		 new File(dir_subdir).mkdirs();
    	}
    	//peningkatan
    	rev_akhir  = getNorutTerakhirManual(id_versi,id_std_isi,"peningkatan");
    	dir_subdir = null;
    	for(int i=1;i<=rev_akhir;i++) {
    		dir_subdir = root_prodi_spmi_folder+"/manual/peningkatan/rev_"+i+"/";
    		while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
    		 new File(dir_subdir).mkdirs();
    	}
       
    }
    
    public void createFolderStructureDocMutu(Vector v_ListKdpstKdjenNmpstNmjen, Vector v_ListDokumenMutu) {
    	String root_dokumen_mutu_folder = getRootDokMutuFolder();
    	new File(root_dokumen_mutu_folder).mkdirs();	
    	if(v_ListKdpstKdjenNmpstNmjen!=null && v_ListKdpstKdjenNmpstNmjen.size()>0 && v_ListDokumenMutu!=null && v_ListDokumenMutu.size()>0) {
    		ListIterator li = v_ListKdpstKdjenNmpstNmjen.listIterator();
        	ListIterator li2 = null;
    		StringTokenizer st = null;
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			st = new StringTokenizer(brs,"~");
    			String kdpst = st.nextToken();
    			String kdjen = st.nextToken();
    			String nmpst = st.nextToken();
    			String nmjen = st.nextToken();
    			String dir_subdir = root_dokumen_mutu_folder+nmpst.trim()+" "+nmjen.toUpperCase().trim();
    			new File(dir_subdir).mkdirs();
    			
    			//System.out.println("create00="+dir_subdir);
    			li2 = v_ListDokumenMutu.listIterator();
        		while(li2.hasNext()) {
        			String brs2 = (String)li2.next();
        			//System.out.println(brs2);
        			st =new StringTokenizer(brs2,"~");
        			String nm_doc = st.nextToken();
        			while(nm_doc.contains("/")) {
        				nm_doc = nm_doc.replace("/", " ATAU ");
        			}
        			while(nm_doc.contains("  ")) {
        				nm_doc = nm_doc.replace("  ", " ");
        			}
        			String sub_subdir = dir_subdir.trim()+"/"+nm_doc.toUpperCase();
        			//System.out.println("create01="+sub_subdir);
        			new File(sub_subdir).mkdirs();
        			new File(sub_subdir+"/dokumentasi").mkdirs();
        			new File(sub_subdir+"/notulen").mkdirs();
        			new File(sub_subdir+"/SK").mkdirs();
        		}	
    		}
    		
    	}
    	
    	/*
    	if(ppepp!=null && ppepp.equalsIgnoreCase("penetapan")) {
			String dir_subdir = root_manual_spmi_folder+"/perumusan";
			while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
			//System.out.println("dir_subdir="+dir_subdir);
        	new File(dir_subdir).mkdirs();
        	dir_subdir = root_manual_spmi_folder+"/pemeriksaan";
        	while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
        	new File(dir_subdir).mkdirs();
        	dir_subdir = root_manual_spmi_folder+"/persetujuan";
        	while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
        	new File(dir_subdir).mkdirs();
        	dir_subdir = root_manual_spmi_folder+"/penetapan";
        	while(dir_subdir.contains("//")) {
				dir_subdir = dir_subdir.replace("//", "/");
			}
        	new File(dir_subdir).mkdirs();	
		}
		*/   
    }		
    	
    public void renameFolderStructureDocMutu(Vector v_ListKdpstKdjenNmpstNmjen, String[] nm_doc_ori, String[] nu_nm_doc) {
    	String root_dokumen_mutu_folder = getRootDokMutuFolder();
    	new File(root_dokumen_mutu_folder).mkdirs();	
    	if(v_ListKdpstKdjenNmpstNmjen!=null && v_ListKdpstKdjenNmpstNmjen.size()>0 && nm_doc_ori!=null && nm_doc_ori.length>0 && nu_nm_doc!=null && nu_nm_doc.length>0 && (nm_doc_ori.length==nu_nm_doc.length)) {
    		ListIterator li = v_ListKdpstKdjenNmpstNmjen.listIterator();
        	ListIterator li2 = null;
    		StringTokenizer st = null;
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			st = new StringTokenizer(brs,"~");
    			String kdpst = st.nextToken();
    			String kdjen = st.nextToken();
    			String nmpst = st.nextToken();
    			String nmjen = st.nextToken();
    			String dir_subdir = root_dokumen_mutu_folder+nmpst.trim()+" "+nmjen.toUpperCase().trim();
    			//new File(dir_subdir).mkdirs();
    			for(int i=0;i<nm_doc_ori.length;i++) {
    				//pastikan tidak ada forward slash
    				while(nm_doc_ori[i].contains("/")) {
    					nm_doc_ori[i] = nm_doc_ori[i].replace("/", " ATAU ");
    				}
    				while(nm_doc_ori[i].contains("  ")) {
    					nm_doc_ori[i] = nm_doc_ori[i].replace("  ", " ");
    				}
    				
    				while(nu_nm_doc[i].contains("/")) {
    					nu_nm_doc[i] = nu_nm_doc[i].replace("/", " ATAU ");
    				}
    				while(nu_nm_doc[i].contains("  ")) {
    					nu_nm_doc[i] = nu_nm_doc[i].replace("  ", " ");
    				}
    				if(!nm_doc_ori[i].toUpperCase().trim().equalsIgnoreCase(nu_nm_doc[i].toUpperCase().trim())) {
    					String old_dir = dir_subdir.trim()+"/"+nm_doc_ori[i].toUpperCase().trim();	
    					String new_dir = dir_subdir.trim()+"/"+nu_nm_doc[i].toUpperCase().trim();	
    					File dir = new File(old_dir);
    					File nu_dir = new File(new_dir);
    					dir.renameTo(nu_dir);
    				}
    			}
    		}
    		
    	}
 
    }	
    
    public void createFolderStructureManualUmum(String id_versi, String ppepp) {
    	if(true) {
    		
    		//pilih_ppepp = pilih_ppepp.toLowerCase().trim();
    		//String root_manual_spmi_folder="/home/cg2/spmi/"+id_std+"/"+id_versi;
    		String root_manual_spmi_folder=getRootManualSpmiFolder(id_versi,ppepp);
    		//System.out.println("root_manual_spmi_folder="+root_manual_spmi_folder);
    		if(ppepp!=null && ppepp.equalsIgnoreCase("penetapan")) {
    			String dir_subdir = root_manual_spmi_folder+"/perumusan";
    			while(dir_subdir.contains("//")) {
    				dir_subdir = dir_subdir.replace("//", "/");
    			}
    			//System.out.println("dir_subdir="+dir_subdir);
            	new File(dir_subdir).mkdirs();
            	dir_subdir = root_manual_spmi_folder+"/pemeriksaan";
            	while(dir_subdir.contains("//")) {
    				dir_subdir = dir_subdir.replace("//", "/");
    			}
            	new File(dir_subdir).mkdirs();
            	dir_subdir = root_manual_spmi_folder+"/persetujuan";
            	while(dir_subdir.contains("//")) {
    				dir_subdir = dir_subdir.replace("//", "/");
    			}
            	new File(dir_subdir).mkdirs();
            	dir_subdir = root_manual_spmi_folder+"/penetapan";
            	while(dir_subdir.contains("//")) {
    				dir_subdir = dir_subdir.replace("//", "/");
    			}
            	new File(dir_subdir).mkdirs();	
    		}
    		else  {
    			/*
    			 *untuk sekarang hanya untuk siklus penetapan saja 
    			 */
    		}
    		
    		//int ver_akhir_std = getNorutTerakhirStd(id_std);
    		//if(ver_akhir_std==0) {
    		//	ver_akhir_std=1;
    		//}
    		/*
    		int ver_akhir = getNorutTerakhirManualUmum(id_versi,"perencanaan");
        	//System.out.println("root_prodi_spmi_folder="+root_prodi_spmi_folder);
        	//perencanaan
        	//int rev_akhir  = getNorutTerakhirManual(id_versi,id_std_isi,"perencanaan");
        	String dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_manual_spmi_folder+"/penetapan/ver_"+i+"/perumusan";
            	new File(dir_subdir).mkdirs();
            	dir_subdir = root_manual_spmi_folder+"/penetapan/ver_"+i+"/pemeriksaan";
            	new File(dir_subdir).mkdirs();
            	dir_subdir = root_manual_spmi_folder+"/penetapan/ver_"+i+"/persetujuan";
            	new File(dir_subdir).mkdirs();
            	dir_subdir = root_manual_spmi_folder+"/penetapan/ver_"+i+"/penetapan";
            	new File(dir_subdir).mkdirs();	
        	}
        	
        	
        	
        	//pelaksanaan 
        	ver_akhir  = getNorutTerakhirManualUmum(id_versi,"pelaksanaan");
        	dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_manual_spmi_folder+"/pelaksanaan/ver_"+i+"";
        		 new File(dir_subdir).mkdirs();
        	}
        	
        	//evaluasi 
        	ver_akhir  = getNorutTerakhirManualUmum(id_versi,"evaluasi");
        	dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_manual_spmi_folder+"/evaluasi/ver_"+i+"";
        		 new File(dir_subdir).mkdirs();
        	}
        	//pengendalian 
        	ver_akhir  = getNorutTerakhirManualUmum(id_versi,"pengendalian");
        	dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_manual_spmi_folder+"/pengendalian/ver_"+i+"";
        		 new File(dir_subdir).mkdirs();
        	}
        	//peningkatan 
        	ver_akhir  = getNorutTerakhirManualUmum(id_versi,"peningkatan");
        	dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_manual_spmi_folder+"/peningkatan/ver_"+i+"";
        		 new File(dir_subdir).mkdirs();
        	}
        	*/
    	}
    }
    /*
    public void createFolderStructureSiklusPenetapanUmum(String id_std) {
    	if(true) {
    		
    		//pilih_ppepp = pilih_ppepp.toLowerCase().trim();
    		//String root_manual_spmi_folder="/home/cg2/spmi/"+id_std+"/"+id_versi;
    		String root_manual_spmi_folder=getRootManualSpmiFolder(id_std);
    		//int ver_akhir_std = getNorutTerakhirStd(id_std);
    		//if(ver_akhir_std==0) {
    		//	ver_akhir_std=1;
    		//}
    		int ver_akhir = getNorutTerakhirManualUmum(id_std,"perencanaan");
        	//System.out.println("root_prodi_spmi_folder="+root_prodi_spmi_folder);
        	//perencanaan
        	//int rev_akhir  = getNorutTerakhirManual(id_versi,id_std_isi,"perencanaan");
        	String dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_manual_spmi_folder+"/penetapan/ver_"+i+"/perumusan";
            	new File(dir_subdir).mkdirs();
            	dir_subdir = root_manual_spmi_folder+"/penetapan/ver_"+i+"/pemeriksaan";
            	new File(dir_subdir).mkdirs();
            	dir_subdir = root_manual_spmi_folder+"/penetapan/ver_"+i+"/persetujuan";
            	new File(dir_subdir).mkdirs();
            	dir_subdir = root_manual_spmi_folder+"/penetapan/ver_"+i+"/penetapan";
            	new File(dir_subdir).mkdirs();	
        	}
        	
        	
        	
        	//pelaksanaan 
        	ver_akhir  = getNorutTerakhirManualUmum(id_std,"pelaksanaan");
        	dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_manual_spmi_folder+"/pelaksanaan/ver_"+i+"";
        		 new File(dir_subdir).mkdirs();
        	}
        	
        	//evaluasi 
        	ver_akhir  = getNorutTerakhirManualUmum(id_std,"evaluasi");
        	dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_manual_spmi_folder+"/evaluasi/ver_"+i+"";
        		 new File(dir_subdir).mkdirs();
        	}
        	//pengendalian 
        	ver_akhir  = getNorutTerakhirManualUmum(id_std,"pengendalian");
        	dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_manual_spmi_folder+"/pengendalian/ver_"+i+"";
        		 new File(dir_subdir).mkdirs();
        	}
        	//peningkatan 
        	ver_akhir  = getNorutTerakhirManualUmum(id_std,"peningkatan");
        	dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_manual_spmi_folder+"/peningkatan/ver_"+i+"";
        		 new File(dir_subdir).mkdirs();
        	}
    	}
    }
    */
   /* 
    public void createFolderStructureStandarUmum(String id_std) {
    	if(true) {
    		
    		//pilih_ppepp = pilih_ppepp.toLowerCase().trim();
    		//String root_manual_spmi_folder="/home/cg2/spmi/"+id_std+"/"+id_versi;
    		String root_standar_spmi_folder=getRootStandarSpmiFolder(id_std);
    		//int ver_akhir_std = getNorutTerakhirStd(id_std);
    		//if(ver_akhir_std==0) {
    		//	ver_akhir_std=1;
    		//}
    		int ver_akhir = getNorutTerakhirStd(id_std);
    		if(ver_akhir==0) {
    			ver_akhir=1;
    		}
        	//System.out.println("root_prodi_spmi_folder="+root_prodi_spmi_folder);
        	//perencanaan
        	//int rev_akhir  = getNorutTerakhirManual(id_versi,id_std_isi,"perencanaan");
        	String dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_standar_spmi_folder+"/std_ver_"+i+"/perencanaan/perumusan";
            	new File(dir_subdir).mkdirs();
            	dir_subdir = root_standar_spmi_folder+"/std_ver_"+i+"/perencanaan/pemeriksaan";
            	new File(dir_subdir).mkdirs();
            	dir_subdir = root_standar_spmi_folder+"/std_ver_"+i+"/perencanaan/persetujuan";
            	new File(dir_subdir).mkdirs();
            	dir_subdir = root_standar_spmi_folder+"/std_ver_"+i+"/perencanaan/penetapan";
            	new File(dir_subdir).mkdirs();	
        	}
        	
        	
        	
        	//pelaksanaan 
        	//ver_akhir  = getNorutTerakhirManualUmum(id_std,"pelaksanaan");
        	dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_standar_spmi_folder+"/std_ver_"+i+"/pelaksanaan";
        		new File(dir_subdir).mkdirs();
        	}
        	
        	//evaluasi 
        	//ver_akhir  = getNorutTerakhirManualUmum(id_std,"evaluasi");
        	dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_standar_spmi_folder+"/std_ver_"+i+"/evaluasi";
        		 new File(dir_subdir).mkdirs();
        	}
        	//pengendalian 
        	//ver_akhir  = getNorutTerakhirManualUmum(id_std,"pengendalian");
        	dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_standar_spmi_folder+"/std_ver_"+i+"/pengendalian";
        		 new File(dir_subdir).mkdirs();
        	}
        	//peningkatan 
        	//ver_akhir  = getNorutTerakhirManualUmum(id_std,"peningkatan");
        	dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_standar_spmi_folder+"/std_ver_"+i+"/peningkatan";
        		 new File(dir_subdir).mkdirs();
        	}
    	}
    }
    */
    
    public void createFolderStructureStandarUmum(String id_versi, String ppepp) {
    	//System.out.println("id_versi="+id_versi);
    	//System.out.println("ppepp="+ppepp);
    	if(true) {
    		String root_standar_spmi_folder=getRootStandarSpmiFolder(id_versi,ppepp);
    		//System.out.println("root_standar_spmi_folder="+root_standar_spmi_folder);
    		if(ppepp!=null && ppepp.equalsIgnoreCase("penetapan")) {
    			String dir_subdir = root_standar_spmi_folder+"/perumusan";
    			while(dir_subdir.contains("//")) {
    				dir_subdir = dir_subdir.replace("//", "/");
    			}
    			//System.out.println("dir_subdir_std="+dir_subdir);
            	new File(dir_subdir).mkdirs();
            	dir_subdir = root_standar_spmi_folder+"/pemeriksaan";
            	while(dir_subdir.contains("//")) {
    				dir_subdir = dir_subdir.replace("//", "/");
    			}
            	new File(dir_subdir).mkdirs();
            	dir_subdir = root_standar_spmi_folder+"/persetujuan";
            	while(dir_subdir.contains("//")) {
    				dir_subdir = dir_subdir.replace("//", "/");
    			}
            	new File(dir_subdir).mkdirs();
            	dir_subdir = root_standar_spmi_folder+"/penetapan";
            	while(dir_subdir.contains("//")) {
    				dir_subdir = dir_subdir.replace("//", "/");
    			}
            	new File(dir_subdir).mkdirs();	
    		}
    		else if(ppepp!=null && ppepp.equalsIgnoreCase("pelaksanaan")) {
    			
    			String root_pelaksanaan_spmi_folder = getRootStandarSpmiFolder(id_versi,ppepp)+"kegiatan";
    			//System.out.println("root_pelaksanaan_spmi_folder="+root_pelaksanaan_spmi_folder);
    		}
    		else if(ppepp!=null && ppepp.equalsIgnoreCase("evaluasi")) {
    			String root_pelaksanaan_spmi_folder = getRootStandarSpmiFolder(id_versi,ppepp)+"kegiatan";
    			//System.out.println("root_pelaksanaan_spmi_folder_evaluasi="+root_pelaksanaan_spmi_folder);
    		}
    		else if(ppepp!=null && ppepp.equalsIgnoreCase("pengendalian")) {
    			String root_pelaksanaan_spmi_folder = getRootStandarSpmiFolder(id_versi,ppepp)+"kegiatan";
    			//System.out.println("root_pelaksanaan_spmi_folder_evaluasi="+root_pelaksanaan_spmi_folder);
    		}
    		else if(ppepp!=null && ppepp.equalsIgnoreCase("peningkatan")) {
    			String root_pelaksanaan_spmi_folder = getRootStandarSpmiFolder(id_versi,ppepp)+"kegiatan";
    			//System.out.println("root_pelaksanaan_spmi_folder_evaluasi="+root_pelaksanaan_spmi_folder);
    		}
    		/*
    		//pilih_ppepp = pilih_ppepp.toLowerCase().trim();
    		//String root_manual_spmi_folder="/home/cg2/spmi/"+id_std+"/"+id_versi;
    		String root_manual_spmi_folder=getRootManualSpmiFolder(id_std);
    		//int ver_akhir_std = getNorutTerakhirStd(id_std);
    		//if(ver_akhir_std==0) {
    		//	ver_akhir_std=1;
    		//}
    		int ver_akhir = getNorutTerakhirManualUmum(id_std,"perencanaan");
        	//System.out.println("root_prodi_spmi_folder="+root_prodi_spmi_folder);
        	//perencanaan
        	//int rev_akhir  = getNorutTerakhirManual(id_versi,id_std_isi,"perencanaan");
        	String dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_manual_spmi_folder+"/standar/penetapan/ver_"+i+"/perumusan";
            	new File(dir_subdir).mkdirs();
            	dir_subdir = root_manual_spmi_folder+"/standar/penetapan/ver_"+i+"/pemeriksaan";
            	new File(dir_subdir).mkdirs();
            	dir_subdir = root_manual_spmi_folder+"/standar/penetapan/ver_"+i+"/persetujuan";
            	new File(dir_subdir).mkdirs();
            	dir_subdir = root_manual_spmi_folder+"/standar/penetapan/ver_"+i+"/penetapan";
            	new File(dir_subdir).mkdirs();	
        	}
        	
        	
        	
        	//pelaksanaan 
        	ver_akhir  = getNorutTerakhirManualUmum(id_std,"pelaksanaan");
        	dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_manual_spmi_folder+"/standar/pelaksanaan/ver_"+i+"";
        		 new File(dir_subdir).mkdirs();
        	}
        	
        	//evaluasi 
        	ver_akhir  = getNorutTerakhirManualUmum(id_std,"evaluasi");
        	dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_manual_spmi_folder+"/standar/evaluasi/ver_"+i+"";
        		 new File(dir_subdir).mkdirs();
        	}
        	//pengendalian 
        	ver_akhir  = getNorutTerakhirManualUmum(id_std,"pengendalian");
        	dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_manual_spmi_folder+"/standar/pengendalian/ver_"+i+"";
        		 new File(dir_subdir).mkdirs();
        	}
        	//peningkatan 
        	ver_akhir  = getNorutTerakhirManualUmum(id_std,"peningkatan");
        	dir_subdir = null;
        	for(int i=1;i<=ver_akhir;i++) {
        		dir_subdir = root_manual_spmi_folder+"/standar/peningkatan/ver_"+i+"";
        		 new File(dir_subdir).mkdirs();
        	}
        	*/
    	}
    }
    
    public int getNorutTerakhirStd(String id_std) {
    	int tot=0;
    	if(true) {
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select ID_VERSI from STANDARD_TIPE_VERSION where ID_STD=? order by ID_VERSI DESC limit 1");
        		stmt.setInt(1, Integer.parseInt(id_std));
        		if(rs.next()) {
        			tot = rs.getInt(1);
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
    	return tot;
    }		
    
    public Vector getRiwayatKegiatanManual(String id_std,String pilih_ppepp) {
    	Vector v = null;
    	ListIterator li = null;
    	if(!Checker.isStringNullOrEmpty(pilih_ppepp)) {
    		if(pilih_ppepp.equalsIgnoreCase("penetapan")) {
    			pilih_ppepp = "perencanaan";
    		}
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt=con.prepareStatement("select ID_PLAN,VERSI_ID,JENIS_KEGIATAN,NAMA_KEGIATAN from RIWAYAT_"+pilih_ppepp.trim().toUpperCase()+"_UMUM where ID_STD=? order by VERSI_ID,ID_PLAN");
    			stmt.setInt(1,Integer.parseInt(id_std));
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v = new Vector();
    				li = v.listIterator();
    				do {
    					String tmp = "";
    					tmp = rs.getString(1);
    					tmp = tmp+"~"+rs.getString(2);
    					tmp = tmp+"~"+rs.getString(3);
    					tmp = tmp+"~"+rs.getString(4);
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
    	}
    	return v;
    }	
    
    public Vector getRiwayatKegiatanManual_v1(String id_std,String pilih_ppepp) {
    	Vector v = null;
    	ListIterator li = null;
    	if(!Checker.isStringNullOrEmpty(pilih_ppepp)) {
    		if(pilih_ppepp.equalsIgnoreCase("penetapan")) {
    			pilih_ppepp = "perencanaan";
    		}
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt=con.prepareStatement("select ID_PLAN,VERSI_ID,JENIS_KEGIATAN,NAMA_KEGIATAN,TGL_STA_KEGIATAN from RIWAYAT_"+pilih_ppepp.trim().toUpperCase()+"_UMUM where ID_STD=? order by VERSI_ID,ID_PLAN");
    			stmt.setInt(1,Integer.parseInt(id_std));
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v = new Vector();
    				li = v.listIterator();
    				do {
    					String tmp = "";
    					tmp = rs.getString(1);
    					tmp = tmp+"~"+rs.getString(2);
    					tmp = tmp+"~"+rs.getString(3);
    					tmp = tmp+"~"+rs.getString(4);
    					tmp = tmp+"~"+rs.getString(5);
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
    	}
    	return v;
    }
    
    
    public Vector getRiwayatKegiatanAmi_v1(String kdpst) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt=con.prepareStatement("SELECT ID,KDPST,NAMA_KEGIATAN_AMI,TGL_RENCANA_AMI,KETUA_TIM,TKN_ANGGOTA_TIM,TKN_CAKUPAN_ID_MASTER_STD,TGL_RIL_AMI,TKN_CAKUPAN_MASTER_STD,TGL_RIL_AMI_DONE FROM AUDIT_MUTU_INTERNAL where KDPST=? order by TGL_RENCANA_AMI");
			stmt.setString(1,kdpst);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					String tmp = "";
					tmp = rs.getString(1);
					tmp = tmp+"~"+rs.getString(2);
					tmp = tmp+"~"+rs.getString(3);
					tmp = tmp+"~"+rs.getString(4);
					tmp = tmp+"~"+rs.getString(5);
					tmp = tmp+"~"+rs.getString(6);
					tmp = tmp+"~"+rs.getString(7);
					tmp = tmp+"~"+rs.getString(8);
					tmp = tmp+"~"+rs.getString(9);
					tmp = tmp+"~"+rs.getString(10);
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
    
    
    public Vector getRiwayatKegiatanMonitoring(String id_std,String id_versi, String kdpst) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt=con.prepareStatement("SELECT B.ID_STD,A.STD_ISI_ID,A.NORUT,A.TGL_EVAL,A.WAKTU_EVAL,D.KET_TIPE_STD,C.KET_TIPE_STD,B.PERNYATAAN_STD FROM RIWAYAT_EVALUASI_AMI A inner join STANDARD_ISI_TABLE B on A.STD_ISI_ID=B.ID inner join STANDARD_TABLE C on B.ID_STD=C.ID_STD inner join STANDARD_MASTER_TABLE D on C.ID_MASTER_STD=D.ID_MASTER_STD where A.KDPST=? and B.ID_STD=? and A.VERSI_ID=? order by A.TGL_EVAL,A.WAKTU_EVAL;");
			stmt.setString(1,kdpst);
			stmt.setInt(2,Integer.parseInt(id_std));
			stmt.setInt(3,Integer.parseInt(id_versi));
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					String tmp = "";
					/*
					 * 	B.ID_STD
						A.STD_ISI_ID
						A.NORUT
						A.TGL_EVAL
						A.WAKTU_EVAL
						D.KET_TIPE_STD
						C.KET_TIPE_STD
						B.PERNYATAAN_STD
					 */
					tmp = rs.getString(1);
					tmp = tmp+"~"+rs.getString(2);
					tmp = tmp+"~"+rs.getString(3);
					tmp = tmp+"~"+rs.getString(4);
					tmp = tmp+"~"+rs.getString(5);
					tmp = tmp+"~"+rs.getString(6);
					tmp = tmp+"~"+rs.getString(7);
					tmp = tmp+"~"+rs.getString(8);
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
    

    
    public void createFolderRiwayatKegiatanUmum(String id_versi, String pilih_ppepp) {
    	if(true) {
    		String root_manual_spmi_folder=getRootManualSpmiFolder(id_versi,pilih_ppepp);
    		while(root_manual_spmi_folder.contains("//")) {
    			root_manual_spmi_folder = root_manual_spmi_folder.replace("//", "/");
			}
    		new File(root_manual_spmi_folder).mkdirs();
    		//System.out.println("--root_manual_spmi_folder="+root_manual_spmi_folder);
    		String root_standar_spmi_folder=getRootStandarSpmiFolder(id_versi,pilih_ppepp);
    		while(root_standar_spmi_folder.contains("//")) {
    			root_standar_spmi_folder = root_standar_spmi_folder.replace("//", "/");
			}
    		new File(root_standar_spmi_folder).mkdirs();
    		//System.out.println("--root_standar_spmi_folder="+root_standar_spmi_folder);
    		//System.out.println("--pilih_ppepp="+pilih_ppepp);
        	Vector v = getRiwayatKegiatanManual_v1(id_versi,pilih_ppepp);
        	if(v!=null&&v.size()>0) {
        		ListIterator li = v.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			//System.out.println("baris="+brs);
        			StringTokenizer st = new StringTokenizer(brs,"~");
        			//ID_PLAN,ID_VERSI,JENIS_KEGIATAN,NAMA_KEGIATAN
        			String id_plan = st.nextToken();
        			String id_versi_man = st.nextToken();
        			String jenis_kegiatan = st.nextToken();
        			String nama_kegiatan = st.nextToken();
        			String tgl_sta_kegiatan = st.nextToken();
        			//System.out.println("jenis_kegiatan="+jenis_kegiatan);
        			if(jenis_kegiatan.endsWith("_std")) {
        				//standar
        				if(pilih_ppepp.equalsIgnoreCase("perencanaan")||pilih_ppepp.equalsIgnoreCase("penetapan")) {
            				if(jenis_kegiatan.contains("rumus")||jenis_kegiatan.contains("RUMUS")) {
            					String dir_subdir = null;
            		        	dir_subdir = root_standar_spmi_folder+"/perumusan/"+tgl_sta_kegiatan.trim()+"_"+nama_kegiatan.trim()+"/";
            		        	while(dir_subdir.contains("//")) {
            		        		dir_subdir = dir_subdir.replace("//", "/");
            					}
            		        	new File(dir_subdir).mkdirs();
            				}
            				else if(jenis_kegiatan.contains("riksa")||jenis_kegiatan.contains("RIKSA")||jenis_kegiatan.contains("cek")||jenis_kegiatan.contains("CEK")) {
            					String dir_subdir = null;
            					dir_subdir = root_standar_spmi_folder+"/pemeriksaan/"+tgl_sta_kegiatan.trim()+"_"+nama_kegiatan.trim()+"/";
            					while(dir_subdir.contains("//")) {
            		        		dir_subdir = dir_subdir.replace("//", "/");
            					}
            		        	new File(dir_subdir).mkdirs();
            				}
            				else if(jenis_kegiatan.contains("tujua")||jenis_kegiatan.contains("TUJUA")||jenis_kegiatan.contains("stuju")||jenis_kegiatan.contains("STUJU")) {
            					String dir_subdir = null;
            					dir_subdir = root_standar_spmi_folder+"/persetujuan/"+tgl_sta_kegiatan.trim()+"_"+nama_kegiatan.trim()+"/";
            					while(dir_subdir.contains("//")) {
            		        		dir_subdir = dir_subdir.replace("//", "/");
            					}
            		        	new File(dir_subdir).mkdirs();
            				}
            				else if(jenis_kegiatan.contains("netap")||jenis_kegiatan.contains("NETAP")||jenis_kegiatan.contains("tetap")||jenis_kegiatan.contains("TETAP")) {
            					String dir_subdir = null;
            					dir_subdir = root_standar_spmi_folder+"/penetapan/"+tgl_sta_kegiatan.trim()+"_"+nama_kegiatan.trim()+"/";
            					while(dir_subdir.contains("//")) {
            		        		dir_subdir = dir_subdir.replace("//", "/");
            					}
            		        	new File(dir_subdir).mkdirs();
            				}
            			}
            			else {
            				String dir_subdir = null;
        		        	dir_subdir = root_standar_spmi_folder+"/"+tgl_sta_kegiatan.trim()+"_"+nama_kegiatan.trim()+"/";
        		        	while(dir_subdir.contains("//")) {
        		        		dir_subdir = dir_subdir.replace("//", "/");
        					}
        		        	new File(dir_subdir).mkdirs();
            			}
        			}
        			else {
        				if(pilih_ppepp.equalsIgnoreCase("perencanaan")||pilih_ppepp.equalsIgnoreCase("penetapan")) {
            				if(jenis_kegiatan.contains("rumus")||jenis_kegiatan.contains("RUMUS")) {
            					String dir_subdir = null;
            		        	dir_subdir = root_manual_spmi_folder+"/perumusan/"+tgl_sta_kegiatan.trim()+"_"+nama_kegiatan.trim()+"/";
            		        	while(dir_subdir.contains("//")) {
            		        		dir_subdir = dir_subdir.replace("//", "/");
            					}
            		        	new File(dir_subdir).mkdirs();
            				}
            				else if(jenis_kegiatan.contains("riksa")||jenis_kegiatan.contains("RIKSA")||jenis_kegiatan.contains("cek")||jenis_kegiatan.contains("CEK")) {
            					String dir_subdir = null;
            					dir_subdir = root_manual_spmi_folder+"/pemeriksaan/"+tgl_sta_kegiatan.trim()+"_"+nama_kegiatan.trim()+"/";
            					while(dir_subdir.contains("//")) {
            		        		dir_subdir = dir_subdir.replace("//", "/");
            					}
            		        	new File(dir_subdir).mkdirs();
            				}
            				else if(jenis_kegiatan.contains("tujua")||jenis_kegiatan.contains("TUJUA")||jenis_kegiatan.contains("stuju")||jenis_kegiatan.contains("STUJU")) {
            					String dir_subdir = null;
            					dir_subdir = root_manual_spmi_folder+"/persetujuan/"+tgl_sta_kegiatan.trim()+"_"+nama_kegiatan.trim()+"/";
            					while(dir_subdir.contains("//")) {
            		        		dir_subdir = dir_subdir.replace("//", "/");
            					}
            		        	new File(dir_subdir).mkdirs();
            				}
            				else if(jenis_kegiatan.contains("netap")||jenis_kegiatan.contains("NETAP")||jenis_kegiatan.contains("tetap")||jenis_kegiatan.contains("TETAP")) {
            					String dir_subdir = null;
            					dir_subdir = root_manual_spmi_folder+"/penetapan/"+tgl_sta_kegiatan.trim()+"_"+nama_kegiatan.trim()+"/";
            					while(dir_subdir.contains("//")) {
            		        		dir_subdir = dir_subdir.replace("//", "/");
            					}
            		        	new File(dir_subdir).mkdirs();
            				}
            			}
            			else {
            				String dir_subdir = null;
        		        	dir_subdir = root_manual_spmi_folder+"/"+tgl_sta_kegiatan.trim()+"_"+nama_kegiatan.trim()+"/";
        		        	while(dir_subdir.contains("//")) {
        		        		dir_subdir = dir_subdir.replace("//", "/");
        					}
        		        	new File(dir_subdir).mkdirs();
            			}
        			}
        			
        		}
        	}
    	}
    }
    
    
    public void createFolderRiwayatKegiatanAmi(String kdpst) {
    	if(true) {
    		
    		String root_standar_ami_folder=getRootStandarAmiFolder();
    		Vector v = getRiwayatKegiatanAmi_v1(kdpst);
        	if(v!=null&&v.size()>0) {
        		ListIterator li = v.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			//System.out.println("baris="+brs);
        			StringTokenizer st = new StringTokenizer(brs,"~");
        			//ID,KDPST,NAMA_KEGIATAN_AMI,TGL_RENCANA_AMI,KETUA_TIM,TKN_ANGGOTA_TIM,TKN_CAKUPAN_ID_MASTER_STD,TGL_RIL_AMI,TKN_CAKUPAN_MASTER_STD,TGL_RIL_AMI_DONE
        			String id_ami = st.nextToken();
        			String kdpst_ami = st.nextToken();
        			String nama_ami = st.nextToken();
        			String tgl_plan_ami = st.nextToken();
        			String ketua_ami = st.nextToken();
        			String anggota_ami = st.nextToken();
        			String id_scope_std = st.nextToken();
        			String tgl_ril_ami = st.nextToken();
        			String nm_scope_std = st.nextToken();
        			String tgl_done_ami = st.nextToken();
        			
        			String dir_subdir = null;
        			dir_subdir = root_standar_ami_folder+"/"+Converter.getNamaKdpstDanJenjang(kdpst_ami)+"/"+nama_ami.trim()+"_["+id_ami.trim()+"]"+tgl_plan_ami.trim();
        			while(dir_subdir.contains("//")) {
		        		dir_subdir = dir_subdir.replace("//", "/");
					}
        			//System.out.println(dir_subdir);
        			new File(dir_subdir).mkdirs();
        		}
        	}
    	}
    }
    
    
    public void createFolderRiwayatKegiatanMonitoring(String id_std, String id_versi, String kdpst) {
    	String nm_prodi = Converter.getNamaKdpstDanJenjang(kdpst);
    	String root_standar_spmi_folder=getRootStandarSpmiFolder(id_versi,"pelaksanaan");
    	//System.out.println("--root_standar_spmi_folder="+root_standar_spmi_folder);
        Vector v = getRiwayatKegiatanMonitoring(id_std,id_versi, kdpst);
        if(v!=null&&v.size()>0) {
        	ListIterator li = v.listIterator();
        	while(li.hasNext()) {
        		String brs = (String)li.next();
        		//System.out.println("baris="+brs);
        		StringTokenizer st = new StringTokenizer(brs,"~");
        		st.nextToken(); //id_std ignore
				String id_std_isi = st.nextToken();
				String norut = st.nextToken();
				String tgl_eval = st.nextToken();
				String waktu_eval = st.nextToken();
				String nm_rumpun_std = st.nextToken();
				String nm_standar = st.nextToken();
				String isi_std = st.nextToken();
				int max = isi_std.length();
				if(max>255) {
					max = 255;
				}	
				String dir_subdir = null;
		       	dir_subdir = root_standar_spmi_folder.trim()+"/monitoring/"+nm_prodi.trim()+"/"+nm_rumpun_std.trim()+"/"+nm_standar.trim()+"/"+isi_std.substring(0, 200)+"  . . .  "+isi_std.substring(isi_std.length()-43, isi_std.length()).trim()+"/"+tgl_eval.trim();
		       	while(dir_subdir.contains("//")) {
	        		dir_subdir = dir_subdir.replace("//", "/");
				}
		       	//System.out.println("cretae = "+dir_subdir);
		       	new File(dir_subdir).mkdirs();
        	}	
        }
    }
    
    
    public static String cetakJenisKegiatanManualDanStandar(String jenis_kegiatan) {
    	String tmp = "";
    	if(jenis_kegiatan!=null && jenis_kegiatan.equalsIgnoreCase("rumus")) {
    		tmp = "PERUMUSAN MANUAL";
    	}
    	else if(jenis_kegiatan!=null && jenis_kegiatan.equalsIgnoreCase("rumus_std")) {
    		tmp = "PERUMUSAN STANDAR";
    	}
    	else if(jenis_kegiatan!=null && jenis_kegiatan.equalsIgnoreCase("cek")) {
    		tmp = "PEMERIKSAAN MANUAL";
    	}
    	else if(jenis_kegiatan!=null && jenis_kegiatan.equalsIgnoreCase("cek_std")) {
    		tmp = "PEMERIKSAAN STANDAR";
    	}
    	else if(jenis_kegiatan!=null && jenis_kegiatan.equalsIgnoreCase("stuju")) {
    		tmp = "PERSETUJUAN MANUAL";
    	}
    	else if(jenis_kegiatan!=null && jenis_kegiatan.equalsIgnoreCase("stuju_std")) {
    		tmp = "PERSETUJUAN STANDAR";
    	}
    	else if(jenis_kegiatan!=null && jenis_kegiatan.equalsIgnoreCase("tetap")) {
    		tmp = "PENETAPAN MANUAL";
    	}
    	else if(jenis_kegiatan!=null && jenis_kegiatan.equalsIgnoreCase("tetap_std")) {
    		tmp = "PENETAPAN STANDAR";
    	}
    	else if(jenis_kegiatan!=null && jenis_kegiatan.equalsIgnoreCase("kendali")) {
    		tmp = "PENGENDALIAN MANUAL";
    	}
    	else if(jenis_kegiatan!=null && jenis_kegiatan.equalsIgnoreCase("kendali_std")) {
    		tmp = "PENGENDALIAN STANDAR";
    	}
    	return tmp;
    }
    
    public java.sql.Date getTglStartManual(int id_versi, int id_std_isi, int norut_man, String pilih_ppepp) {
    	java.sql.Date dt = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt=con.prepareStatement("select TGL_STA from STANDARD_MANUAL_"+pilih_ppepp.toUpperCase().trim()+" where VERSI_ID=? and STD_ISI_ID=? and NORUT=?");
    		stmt.setInt(1, id_versi);
    		stmt.setInt(2, id_std_isi);
    		stmt.setInt(3, norut_man);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			dt = rs.getDate(1);
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
    	return dt;
    }
    
    
    public java.sql.Date getTglStartManualUmum(int id_versi, int id_std, String pilih_ppepp) {
    	java.sql.Date dt = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt=con.prepareStatement("select TGL_STA from STANDARD_MANUAL_"+pilih_ppepp.toUpperCase().trim()+"_UMUM where VERSI_ID=? and ID_STD=?");
    		stmt.setInt(1, id_versi);
    		stmt.setInt(2, id_std);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			dt = rs.getDate(1);
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
    	return dt;
    }
    
    public boolean apaSudahAdaPenetapanManual(int id_versi, int id_std_isi) {
    	/*
    	 * semua penetapan ditentukan di proses PERENCANAAN
    	 */
    	boolean ada = false;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt=con.prepareStatement("SELECT A.TGL_TETAP FROM STANDARD_MANUAL_PERENCANAAN A left join RIWAYAT_PERENCANAAN B on (A.VERSI_ID=B.VERSI_ID and A.STD_ISI_ID=B.STD_ISI_ID and A.NORUT=B.NORUT) where A.VERSI_ID=? and A.STD_ISI_ID=? and A.TGL_END is null and A.TGL_TETAP is not null and B.TGL_TETAP=true");
    		stmt.setInt(1, id_versi);
    		stmt.setInt(2, id_std_isi);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			ada = true;
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
    	return ada;
    }
    
    
    public boolean apaSudahAdaPenetapanManualUmum(int id_versi, int id_std) {
    	/*
    	 * semua penetapan ditentukan di proses PERENCANAAN
    	 */
    	boolean ada = false;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt=con.prepareStatement("SELECT A.TGL_TETAP FROM STANDARD_MANUAL_PERENCANAAN_UMUM A left join RIWAYAT_PERENCANAAN_UMUM B on (A.VERSI_ID=B.VERSI_ID and A.ID_STD=B.ID_STD) where A.VERSI_ID=? and A.ID_STD=? and A.TGL_END is null and A.TGL_TETAP is not null and B.TGL_TETAP=true");
    		stmt.setInt(1, id_versi);
    		stmt.setInt(2, id_std);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			ada = true;
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
    	return ada;
    }
    
    
    public boolean apaManualSudahAktifUmum(int id_versi, int id_std, String pilih_ppepp) {
    	/*
    	 * semua penetapan ditentukan di proses PERENCANAAN
    	 */
    	boolean sudah = false;
    	try {
    		if(!Checker.isStringNullOrEmpty(pilih_ppepp)) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt=con.prepareStatement("SELECT TGL_STA FROM STANDARD_MANUAL_"+pilih_ppepp.trim().toUpperCase()+"_UMUM where VERSI_ID=? and ID_STD=?");	
        		stmt.setInt(1, id_versi);
        		stmt.setInt(2, id_std);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			java.sql.Date dt = rs.getDate(1);
        			if(dt!=null) {
        				sudah=true;
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
    	return sudah;
    }
    
    public boolean apaManualSudahExpiredUmum(int id_versi, int id_std, String pilih_ppepp) {
    	/*
    	 * semua penetapan ditentukan di proses PERENCANAAN
    	 */
    	boolean sudah = false;
    	try {
    		if(!Checker.isStringNullOrEmpty(pilih_ppepp)) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt=con.prepareStatement("SELECT TGL_END FROM STANDARD_MANUAL_"+pilih_ppepp.trim().toUpperCase()+"_UMUM where VERSI_ID=? and ID_STD=?");	
        		stmt.setInt(1, id_versi);
        		stmt.setInt(2, id_std);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			java.sql.Date dt = rs.getDate(1);
        			if(dt!=null) {
        				sudah=true;
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
    	return sudah;
    }
    
    public String getStatusKegiatanAmi(int id_ami) {
    	//belum,sedang,selesai 
    	String status="belum";
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_RIL_AMI,TGL_RIL_AMI_DONE from AUDIT_MUTU_INTERNAL where ID=?");
        	stmt.setInt(1, id_ami);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		String tgl_sta = rs.getString(1);
        		String tgl_end = rs.getString(2);
        		if(!Checker.isStringNullOrEmpty(tgl_end)) {
        			status = "selesai";
        		}
        		else {
        			if(!Checker.isStringNullOrEmpty(tgl_sta)) {
            			status = "sedang";
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
    	return status;
    }
    
    public static Vector removeDuplicatePernyataanIsiStdForPrepFormAmi(Vector v) {
    	
    	Vector v1=null;
    	if(v!=null && v.size()>0) {
    		try {
    			int i=0;
        		v1 = (Vector) v.clone();
        		ListIterator li = v1.listIterator();
        		if(li.hasNext()) {
        			i++;
        			String brs = (String)li.next();
        			//System.out.println("brs "+i+". "+brs);
        			StringTokenizer st = new StringTokenizer(brs,"~");
        			String prev_norut_question = st.nextToken();
        			String prev_bobot_ignore = st.nextToken();
        			String prev_kode = st.nextToken();
        			String prev_id_master_std = st.nextToken();
        			String prev_id_std = st.nextToken();
        			String prev_id_tipe_std = st.nextToken();
        			String prev_id_std_isi = st.nextToken();
        			String prev_ket_master_std = st.nextToken();
        			String prev_ket_standar = st.nextToken();
        			String prev_tgl_sta = st.nextToken();
        			String prev_tgl_end = st.nextToken();
        			String prev_isi_std = st.nextToken();
        			String prev_butir = st.nextToken();
        			String prev_kdpst = st.nextToken();
        			String prev_rasionale = st.nextToken();
        			String prev_aktif = st.nextToken();
        			String prev_tgl_activated = st.nextToken();
        			String prev_tgl_deactivated = st.nextToken();
        			String prev_scope = st.nextToken();
        			String prev_tipe_awas = st.nextToken();
        			String prev_question = st.nextToken();
        			String prev_answer = st.nextToken();
        			String prev_bobot = st.nextToken();
        			String prev_id_question = st.nextToken();
        			
        			//System.out.println(i+". "+prev_isi_std+"-"+prev_question+"-"+prev_id_question+"-"+prev_kdpst+"["+prev_id_std+"-"+prev_id_tipe_std+"-"+prev_id_std_isi+"]");
        			while(li.hasNext()) {
        				i++;
        				brs = (String)li.next();
        				//System.out.println("brs "+i+". "+brs);
            			st = new StringTokenizer(brs,"~");
            			String norut_question = st.nextToken();
            			String bobot_ignore = st.nextToken();
            			String kode = st.nextToken();
            			String id_master_std = st.nextToken();
            			String id_std = st.nextToken();
            			String id_tipe_std = st.nextToken();
            			String id_std_isi = st.nextToken();
            			String ket_master_std = st.nextToken();
            			String ket_standar = st.nextToken();
            			String tgl_sta = st.nextToken();
            			String tgl_end = st.nextToken();
            			String isi_std = st.nextToken();
            			String butir = st.nextToken();
            			String kdpst = st.nextToken();
            			String rasionale = st.nextToken();
            			String aktif = st.nextToken();
            			String tgl_activated = st.nextToken();
            			String tgl_deactivated = st.nextToken();
            			String scope = st.nextToken();
            			String tipe_awas = st.nextToken();
            			String question = st.nextToken();
            			String answer = st.nextToken();
            			String bobot = st.nextToken();
            			String id_question = st.nextToken();
            			
            			//System.out.println(i+". "+isi_std+"-"+question+"-"+id_question+"-"+kdpst+"["+id_std+"-"+id_tipe_std+"-"+id_std_isi+"]");
            			if(prev_isi_std.equalsIgnoreCase(isi_std)) {
            				if(!prev_kdpst.equalsIgnoreCase(kdpst)) {
            					li.remove();
                				//System.out.println("remove");	
            				}
            				else {
            					
            				}
            				
            			}
            			else {
            				prev_isi_std = new String(isi_std);
                			prev_kdpst = new String(kdpst);	
                			//System.out.println("keep");
            			}
            			
        			}
        		}
        		
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        	}	
    	}
    	
    	return v1;
    }
    
    
    public static int getTotalQandA(int id_ami, int id_master, boolean ami_belum_dilaksanakan, Connection connect) {
    	int tot=0;
    	Connection con1=null;
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	try {
    		if(connect==null) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con1 = ds1.getConnection();	
    		}
    		else {
    			//System.out.println("con1 = connect");
    			con1 = connect;
    		}
    		
    		//cretae NPM auto increment
    		if(!ami_belum_dilaksanakan) {
    			//jika sudah / sedang dilaksanakan ambil dari hasil
    			stmt1 = con1.prepareStatement("SELECT count(ID_AMI) from AUDIT_MUTU_INTERNAL_HASIL where ID_AMI=? and ID_MASTER_STD=?");
        		stmt1.setInt(1, id_ami);
        		stmt1.setInt(2, id_master);
        		rs1 = stmt1.executeQuery();
        		if(rs1.next()) {
        			tot = rs1.getInt(1);
        		}	
    		}
    		else {
    			/*
    			String sql = "select D.ID " + 
    					"	from STANDARD_MASTER_TABLE A " + 
    					"    inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD " + 
    					"    inner join STANDARD_ISI_TABLE C on B.ID_STD=C.ID_STD " + 
    					"    inner join STANDARD_ISI_QUESTION D on C.ID=D.ID_STD_ISI " + 
    					"    where A.ID_MASTER_STD=?";
    			*/		
    			stmt1 = con1.prepareStatement(sqlGetTotQandA);
        		stmt1.setInt(1, id_master);
        		rs1 = stmt1.executeQuery();
        		while(rs1.next()) {
        			tot++;
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
        	if(connect==null) {
        		if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
    		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
    		    if (con1!=null) try { con1.close();} catch (Exception ignore){}	
        	}
        	
        }	
    	return tot;
    }
    
    public static int getTotalQandA(int id_ami,boolean ami_belum_dilaksanakan) {
    	int tot=0;
    	Connection con1=null;
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con1 = ds1.getConnection();
    		//cretae NPM auto increment
    		if(!ami_belum_dilaksanakan) {
    			//jika sudah / sedang dilaksanakan ambil dari hasil
    			stmt1 = con1.prepareStatement("SELECT count(ID_AMI) from AUDIT_MUTU_INTERNAL_HASIL where ID_AMI=?");
        		stmt1.setInt(1, id_ami);
        		rs1 = stmt1.executeQuery();
        		if(rs1.next()) {
        			tot = rs1.getInt(1);
        		}
    		}	
    		else {
    			stmt1 = con1.prepareStatement("select TKN_CAKUPAN_ID_MASTER_STD from AUDIT_MUTU_INTERNAL where ID=?");
    			stmt1.setInt(1, id_ami);
        		rs1 = stmt1.executeQuery();
        		if(rs1.next()) {
        			String cakupan_master_id = rs1.getString(1);
        			StringTokenizer st = new StringTokenizer(cakupan_master_id,",");
        			while(st.hasMoreTokens()) {
        				String master_id = st.nextToken();
        				//System.out.println("master_id="+master_id);
        				tot = tot+getTotalQandA(id_ami, Integer.parseInt(master_id), ami_belum_dilaksanakan, con1);
        				//System.out.println("subtot="+tot);
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
        	if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
		    if (con1!=null) try { con1.close();} catch (Exception ignore){}
        }	
    	return tot;
    }
    
    
    public static double getHasilPenilaian(int id_ami) {
    	double tot=0;
    	Connection con1=null;
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con1 = ds1.getConnection();
    		stmt1 = con1.prepareStatement("SELECT SUM(BOBOT) from AUDIT_MUTU_INTERNAL_HASIL where ID_AMI=?");
    		stmt1.setInt(1, id_ami);
    		rs1 = stmt1.executeQuery();
    		if(rs1.next()) {
    			tot = rs1.getDouble(1);
    		}
    	} 
        catch (NamingException e) {
        	e.printStackTrace();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        } 
        finally {
        	if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
		    if (con1!=null) try { con1.close();} catch (Exception ignore){}
        }	
    	return tot;
    }
    
    public static double getHasilPenilaian(int id_ami, int id_master_std) {
    	double tot=0;
    	Connection con1=null;
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con1 = ds1.getConnection();
    		stmt1 = con1.prepareStatement("SELECT SUM(BOBOT) from AUDIT_MUTU_INTERNAL_HASIL where ID_AMI=? and ID_MASTER_STD=?");
    		stmt1.setInt(1, id_ami);
    		stmt1.setInt(2, id_master_std);
    		
    		rs1 = stmt1.executeQuery();
    		if(rs1.next()) {
    			tot = rs1.getDouble(1);
    		}
    	} 
        catch (NamingException e) {
        	e.printStackTrace();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        } 
        finally {
        	if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
		    if (con1!=null) try { con1.close();} catch (Exception ignore){}
        }	
    	return tot;
    }
    
    public static int getPelanggaranHasilPenilaian(int id_ami) {
    	int tot=0;
    	Connection con1=null;
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con1 = ds1.getConnection();
    		stmt1 = con1.prepareStatement("SELECT SUM(PELANGGARAN) from AUDIT_MUTU_INTERNAL_HASIL where ID_AMI=?");
    		stmt1.setInt(1, id_ami);
    		rs1 = stmt1.executeQuery();
    		if(rs1.next()) {
    			tot = rs1.getInt(1);
    		}
    	} 
        catch (NamingException e) {
        	e.printStackTrace();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        } 
        finally {
        	if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
		    if (con1!=null) try { con1.close();} catch (Exception ignore){}
        }	
    	return tot;
    }
    
    public static double getAvailMaxPenilaian(int id_ami) {
    	double tot_bobot_avail=0;
    	Connection con1=null; 
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con1 = ds1.getConnection();
    		stmt1 = con1.prepareStatement("select TKN_CAKUPAN_ID_MASTER_STD from AUDIT_MUTU_INTERNAL where ID=?");
			stmt1.setInt(1, id_ami);
    		rs1 = stmt1.executeQuery();
    		String tkn_id_que = "";
    		if(rs1.next()) {
    			String cakupan_master_id = rs1.getString(1);
    			StringTokenizer st = new StringTokenizer(cakupan_master_id,",");
    			while(st.hasMoreTokens()) {
    				String master_id = st.nextToken();
    				stmt1 = con1.prepareStatement(sqlGetTotQandA);
            		stmt1.setInt(1, Integer.parseInt(master_id));
            		rs1 = stmt1.executeQuery();
            		
            		while(rs1.next()) {
            			tkn_id_que = tkn_id_que+" tt.ID_QUESTION="+rs1.getString(1)+" OR";
            		}
    			}
    		}
    		if(!Checker.isStringNullOrEmpty(tkn_id_que)) {
    			if(tkn_id_que.endsWith("OR")) {
    				tkn_id_que = tkn_id_que.substring(0,tkn_id_que.length()-2);
    			}	
    			String sql = "SELECT SUM(tt.BOBOT) " + 
    					"FROM STANDARD_ISI_ANSWER tt " + 
    					"INNER JOIN " + 
    					"    (SELECT ID_QUESTION, MAX(BOBOT) AS MaxBobot " + 
    					"    FROM STANDARD_ISI_ANSWER " + 
    					"    GROUP BY ID_QUESTION) groupedtt  " + 
    					"ON tt.ID_QUESTION = groupedtt.ID_QUESTION  " + 
    					"AND tt.BOBOT = groupedtt.MaxBobot " + 
    					"where ("+tkn_id_que+")";	
    			stmt1 = con1.prepareStatement(sql);
        		rs1 = stmt1.executeQuery();
        		if(rs1.next()) {
        			tot_bobot_avail = rs1.getDouble(1);
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
        	if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
		    if (con1!=null) try { con1.close();} catch (Exception ignore){}
        }		
    	return tot_bobot_avail;
    }
    
    public static double getAvailMaxPenilaian(int id_ami, int id_master) {
    	/*
    	 * id_ami = sama sekali tidak dipakai
    	 */
    	double tot_bobot_avail=0;
    	Connection con1=null; 
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con1 = ds1.getConnection();
    		String sql = "select  E.ID_MASTER_STD,SUM(MAX_BOBOT) from" + 
    				"	(select A.ID_MASTER_STD,A.ID_STD,A.ID_TIPE_STD,B.ID as ID_STD_ISI,MAX(BOBOT) as MAX_BOBOT  from STANDARD_TABLE A" + 
    				"	inner join STANDARD_ISI_TABLE B on A.ID_STD=B.ID_STD" + 
    				"    inner join STANDARD_ISI_QUESTION C on B.ID=C.ID_STD_ISI" + 
    				"    inner join STANDARD_ISI_ANSWER D on C.ID=D.ID_QUESTION where A.ID_MASTER_STD=?" + 
    				"    group by A.ID_MASTER_STD,A.ID_STD,B.ID,D.ID_QUESTION) as E" + 
    				"    group by E.ID_MASTER_STD";
    		stmt1 = con1.prepareStatement(sql);
    		stmt1.setInt(1, id_master);
    		
    		rs1 = stmt1.executeQuery();
    		if(rs1.next()) {
    			tot_bobot_avail = rs1.getDouble(2);
    		}
    		
    	} 
        catch (NamingException e) {
        	e.printStackTrace();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        } 
        finally {
        	if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
		    if (con1!=null) try { con1.close();} catch (Exception ignore){}
        }		
    	return tot_bobot_avail;
    }
    
    
    public static double getAvailMinPenilaian(int id_ami) {
    	double tot_bobot_avail=0;
    	Connection con1=null; 
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con1 = ds1.getConnection();
    		stmt1 = con1.prepareStatement("select TKN_CAKUPAN_ID_MASTER_STD from AUDIT_MUTU_INTERNAL where ID=?");
			stmt1.setInt(1, id_ami);
    		rs1 = stmt1.executeQuery();
    		String tkn_id_que = "";
    		if(rs1.next()) {
    			String cakupan_master_id = rs1.getString(1);
    			StringTokenizer st = new StringTokenizer(cakupan_master_id,",");
    			while(st.hasMoreTokens()) {
    				String master_id = st.nextToken();
    				stmt1 = con1.prepareStatement(sqlGetTotQandA);
            		stmt1.setInt(1, Integer.parseInt(master_id));
            		rs1 = stmt1.executeQuery();
            		
            		while(rs1.next()) {
            			tkn_id_que = tkn_id_que+" tt.ID_QUESTION="+rs1.getString(1)+" OR";
            		}
    			}
    		}
    		if(!Checker.isStringNullOrEmpty(tkn_id_que)) {
    			if(tkn_id_que.endsWith("OR")) {
    				tkn_id_que = tkn_id_que.substring(0,tkn_id_que.length()-2);
    			}	
    			String sql = "SELECT SUM(tt.BOBOT) " + 
    					"FROM STANDARD_ISI_ANSWER tt " + 
    					"INNER JOIN " + 
    					"    (SELECT ID_QUESTION, MIN(BOBOT) AS MinBobot " + 
    					"    FROM STANDARD_ISI_ANSWER " + 
    					"    GROUP BY ID_QUESTION) groupedtt  " + 
    					"ON tt.ID_QUESTION = groupedtt.ID_QUESTION  " + 
    					"AND tt.BOBOT = groupedtt.MinBobot " + 
    					"where ("+tkn_id_que+")";	
    			stmt1 = con1.prepareStatement(sql);
        		rs1 = stmt1.executeQuery();
        		if(rs1.next()) {
        			tot_bobot_avail = rs1.getDouble(1);
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
        	if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
		    if (con1!=null) try { con1.close();} catch (Exception ignore){}
        }		
    	return tot_bobot_avail;
    }
    
    public static String getTotalPenilaianAmi(int id_ami,boolean ami_belum_dilaksanakan) {
    	double tot_bobot_avail=0,curr_tot_bobot=0,bobot=0;
    	Connection con1=null; 
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con1 = ds1.getConnection();
    		
    		if(!ami_belum_dilaksanakan) {
    			stmt1 = con1.prepareStatement("SELECT TKN_BOBOT,BOBOT from AUDIT_MUTU_INTERNAL_HASIL where ID_AMI=?");
        		stmt1.setInt(1, id_ami);
        		rs1 = stmt1.executeQuery();
        		while(rs1.next()) {
        			String tmp = rs1.getString(1);
        			curr_tot_bobot=curr_tot_bobot+rs1.getDouble(2);
        			StringTokenizer st = new StringTokenizer(tmp,"`");
        			while(st.hasMoreTokens()) {
        				bobot = Double.parseDouble(st.nextToken());
        			}
        			tot_bobot_avail=tot_bobot_avail+bobot;
        		}
    		}
    		else {
    			
        				//System.out.println("master_id="+master_id);
        				//tot_bobot_avail = tot_bobot_avail+getTotalPenilaianAmi(id_ami, Integer.parseInt(master_id), ami_belum_dilaksanakan, con1);
        				//System.out.println("subtot="+tot);
        			
        		
    		}
    		
    	} 
        catch (NamingException e) {
        	e.printStackTrace();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        } 
        finally {
        	if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
		    if (con1!=null) try { con1.close();} catch (Exception ignore){}
        }	
    	return curr_tot_bobot+"/"+tot_bobot_avail;
    }
    
    public static String getTotalPenilaianAmi(int id_ami, int id_master, boolean ami_belum_dilaksanakan, Connection connect) {
    	double tot_bobot_avail=0,curr_tot_bobot=0,bobot=0;
    	Connection con1=null;
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con1 = ds1.getConnection();
    		//cretae NPM auto increment
    		stmt1 = con1.prepareStatement("SELECT TKN_BOBOT,BOBOT from AUDIT_MUTU_INTERNAL_HASIL where ID_AMI=? and ID_MASTER_STD=?");
    		stmt1.setInt(1, id_ami);
    		stmt1.setInt(2, id_master);
    		rs1 = stmt1.executeQuery();
    		while(rs1.next()) {
    			String tmp = rs1.getString(1);
    			curr_tot_bobot=curr_tot_bobot+rs1.getDouble(2);
    			StringTokenizer st = new StringTokenizer(tmp,"`");
    			while(st.hasMoreTokens()) {
    				bobot = Double.parseDouble(st.nextToken());
    			}
    			tot_bobot_avail=tot_bobot_avail+bobot;
    		}
    		
    	} 
        catch (NamingException e) {
        	e.printStackTrace();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        } 
        finally {
        	if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
		    if (con1!=null) try { con1.close();} catch (Exception ignore){}
        }	
    	return curr_tot_bobot+"/"+tot_bobot_avail;
    }
    
    public static int getTotalPelanggaran(int id_ami, int id_master) {
    	int tot=0;
    	Connection con1=null;
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con1 = ds1.getConnection();
    		//cretae NPM auto increment
    		stmt1 = con1.prepareStatement("SELECT count(PELANGGARAN) from AUDIT_MUTU_INTERNAL_HASIL where ID_AMI=? and ID_MASTER_STD=? and PELANGGARAN=true");
    		stmt1.setInt(1, id_ami);
    		stmt1.setInt(2, id_master);
    		rs1 = stmt1.executeQuery();
    		if(rs1.next()) {
    			tot = rs1.getInt(1);
    		}
    		
    	} 
        catch (NamingException e) {
        	e.printStackTrace();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        } 
        finally {
        	if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
		    if (con1!=null) try { con1.close();} catch (Exception ignore){}
        }	
    	return tot;
    }
    
    public static int getTotalPelanggaran(int id_ami) {
    	int tot=0;
    	Connection con1=null;
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con1 = ds1.getConnection();
    		//cretae NPM auto increment
    		stmt1 = con1.prepareStatement("SELECT count(PELANGGARAN) from AUDIT_MUTU_INTERNAL_HASIL where ID_AMI=? and PELANGGARAN=true");
    		stmt1.setInt(1, id_ami);
    		rs1 = stmt1.executeQuery();
    		if(rs1.next()) {
    			tot = rs1.getInt(1);
    		}
    		
    	} 
        catch (NamingException e) {
        	e.printStackTrace();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        } 
        finally {
        	if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
		    if (con1!=null) try { con1.close();} catch (Exception ignore){}
        }	
    	return tot;
    }
    
    
    public static Vector getDataKalenderMutu() {
    	Vector v = null;
    	ListIterator li = null;
    	Connection con1=null;
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con1 = ds1.getConnection();
    		//cretae NPM auto increment
    		stmt1 = con1.prepareStatement("SELECT * from CALENDAR_MUTU ORDER BY TGL_PLAN");
    		rs1 = stmt1.executeQuery();
    		if(rs1.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				/*
    				String id_ami = rs1.getString("ID_AMI");
    				String id_ami = rs1.getString("ID_PLAN_PERENCANAAN");
    				String id_ami = rs1.getString("ID_PLAN_PELAKSANAAN");
    				String id_ami = rs1.getString("ID_PLAN_EVALUASI");
    				String id_ami = rs1.getString("ID_PLAN_PENGENDALIAN");
    				String id_ami = rs1.getString("ID_PLAN_PENINGKATAN");
    				String id_ami = rs1.getString("ID_EVAL");
    				String id_ami = rs1.getString("ID_KENDALI");
    				String id_ami = rs1.getString("ID_AMI");
    				*/
    				String tgl_plan = rs1.getString("TGL_PLAN");
    				String tgl_ril = rs1.getString("TGL_RIL");
    				String tgl_done = rs1.getString("TGL_DONE");
    				String nm_kegiatan = rs1.getString("NAMA_KEGIATAN");
    				String ket_kegiatan = rs1.getString("KETERANGAN_KEGIATAN");
    				String waktu_plan = rs1.getString("WAKTU_PLAN");
    				String waktu_ril = rs1.getString("WAKTU_RIL");
    				String waktu_end = rs1.getString("WAKTU_END");
    				String id_eval = rs1.getString("ID_EVAL");
    				String id_kendal = rs1.getString("ID_KENDALI");
    				String tgl_kendal = rs1.getString("TGL_KENDAL");
    				String tgl_next_eval = rs1.getString("TGL_NEXT_EVAL");
    				
    				String tmp = tgl_plan+"~"+tgl_ril+"~"+tgl_done+"~"+nm_kegiatan+"~"+ket_kegiatan+"~"+waktu_plan+"~"+waktu_ril+"~"+waktu_end+"~"+id_eval+"~"+id_kendal+"~"+tgl_kendal+"~"+tgl_next_eval;
    				tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
    				li.add(tmp);
    			}
    			while(rs1.next());
    		}
    		
    	} 
        catch (NamingException e) {
        	e.printStackTrace();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        } 
        finally {
        	if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
		    if (con1!=null) try { con1.close();} catch (Exception ignore){}
        }	
    	return v;
    }
    
    
    public static int refreshKalenderMutu() {
    	int upd=0;
    	int norut_reminder=0;
    	String sql = "";
    	Vector v = null;
    	ListIterator li = null;
    	Connection con1=null;
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con1 = ds1.getConnection();
    		//delete
    		stmt1 = con1.prepareStatement("delete from CALENDAR_MUTU");
    		stmt1.executeUpdate();
    		//get no id_plan < 0 terakhir untuk dipake oleh notifikasi
    		stmt1=con1.prepareStatement("select ID_EVAL from CALENDAR_MUTU limit 1");
    		rs1 = stmt1.executeQuery();
    		if(rs1.next() ) {
    			norut_reminder = rs1.getInt(1);
    		}
    		//norut_reminder = norut_reminder-1;		
    		
    		/*
    		 * KEGIATAN AMI
    		 */
    		sql = "SELECT * from AUDIT_MUTU_INTERNAL ORDER BY TGL_RENCANA_AMI";
    		stmt1=con1.prepareStatement(sql);
    		rs1 = stmt1.executeQuery();
    		if(rs1.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				String id_ami = rs1.getString("ID");
    				String kdpst = rs1.getString("KDPST");
    				String tgl_plan = rs1.getString("TGL_RENCANA_AMI");
    				String tgl_ril = rs1.getString("TGL_RIL_AMI");
    				String tgl_done = rs1.getString("TGL_RIL_AMI_DONE");
    				String nm_kegiatan = "AMI PRODI "+Converter.getNamaKdpstDanJenjang(kdpst);
    				String ket_kegiatan = "Pelaksanaan Audit Mutu Internal terhadap prodi "+Converter.getNamaKdpstDanJenjang(kdpst);
    				String tmp = id_ami+"~"+kdpst+"~"+tgl_plan+"~"+tgl_ril+"~"+tgl_done+"~"+nm_kegiatan+"~"+ket_kegiatan;
    				tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
    				li.add(tmp);
    			}
    			while(rs1.next());
    			
    			if(v!=null && v.size()>0) {
        			//insert ke calender
        			sql="INSERT INTO CALENDAR_MUTU(ID_AMI,ID_PLAN_PERENCANAAN,ID_PLAN_PELAKSANAAN,ID_PLAN_EVALUASI,ID_PLAN_PENGENDALIAN,ID_PLAN_PENINGKATAN,TGL_PLAN,TGL_RIL,TGL_DONE,NAMA_KEGIATAN,KETERANGAN_KEGIATAN,KDPST_AUDITEE)values" + 
        					"(?,?,?,?,?,?,?,?,?,?,?,?)";
        			stmt1=con1.prepareStatement(sql);
        			//ID_AMI,ID_PLAN_PERENCANAAN,ID_PLAN_PELAKSANAAN,ID_PLAN_EVALUASI,ID_PLAN_PENGENDALIAN,ID_PLAN_PENINGKATAN,TGL_PLAN,TGL_RIL,TGL_DONE,NAMA_KEGIATAN,KETERANGAN_KEGIATAN,KDPST_AUDITEE
        			li = v.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				StringTokenizer st = new StringTokenizer(brs,"~");
        				String id_ami = st.nextToken();
        				String kdpst = st.nextToken();
        				String tgl_plan = st.nextToken();
        				String tgl_ril = st.nextToken();
        				String tgl_done = st.nextToken();
        				String nm_kegiatan = st.nextToken();
        				String ket_kegiatan = st.nextToken();
        				int i=1;
        				//ID_AMI
        				stmt1.setInt(i++, Integer.parseInt(id_ami));
        				//ID_PLAN_PERENCANAAN
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_PELAKSANAAN
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_EVALUASI
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_PENGENDALIAN
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_PENINGKATAN
        				stmt1.setInt(i++, 0);
        				//TGL_PLAN
        				stmt1.setDate(i++,java.sql.Date.valueOf(tgl_plan));
        				//TGL_RIL
        				try {
        					stmt1.setDate(i,java.sql.Date.valueOf(tgl_ril));
        				}
        				catch(Exception e) {
        					stmt1.setNull(i, java.sql.Types.DATE);
        				}
        				i++;
        				
        				//TGL_DONE
        				try {
        					stmt1.setDate(i,java.sql.Date.valueOf(tgl_done));
        				}
        				catch(Exception e) {
        					stmt1.setNull(i, java.sql.Types.DATE);
        				}
        				i++;
        				
        				//NAMA_KEGIATAN
        				stmt1.setString(i++, nm_kegiatan);
        				//KETERANGAN_KEGIATAN
        				stmt1.setString(i++, ket_kegiatan);
        				//KDPST_AUDITEE
        				if(Checker.isStringNullOrEmpty(kdpst)) {
        					stmt1.setNull(i++,java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt1.setString(i++, kdpst);	
        				}
        				upd=upd+stmt1.executeUpdate();
        			}
        		}
    		}
    		
    		/*
    		 * END KEGIATAN AMI
    		 * 
    		 * 
    		 * RIWAYAT PERENCANAAN
    		 */
    		v=null;
    		sql = "SELECT * FROM RIWAYAT_PERENCANAAN_UMUM A inner join STANDARD_TABLE B on A.ID_STD=B.ID_STD order by TGL_STA_KEGIATAN,WAKTU_STA_KEGIATAN";
    		stmt1=con1.prepareStatement(sql);
    		rs1 = stmt1.executeQuery();
    		if(rs1.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				String id_plan = rs1.getString("ID_PLAN");
    				//String kdpst = rs1.getString("KDPST");
    				String tgl_plan = rs1.getString("TGL_STA_KEGIATAN");
    				String waktu_plan = rs1.getString("WAKTU_STA_KEGIATAN");
    				String tgl_ril = tgl_plan;
    				String waktu_ril = waktu_plan;
    				String tgl_end = rs1.getString("TGL_END_KEGIATAN");
    				String waktu_end = rs1.getString("WAKTU_END_KEGIATAN");
    				String nama_kegiatan = rs1.getString("NAMA_KEGIATAN");
    				String jenis_kegiatan = rs1.getString("JENIS_KEGIATAN");
    				String nama_std = rs1.getString("KET_TIPE_STD");
    				String id_std = rs1.getString("A.ID_STD");
    				String ket_kegiatan = getKeteranganKegiatanPerencanaan(jenis_kegiatan);
    				//System.out.println("jenis_kegiatan="+jenis_kegiatan);
    				//System.out.println("ket_kegiatan="+ket_kegiatan);
    				if(!Checker.isStringNullOrEmpty(ket_kegiatan)) {
    					ket_kegiatan = "Kegiatan "+ket_kegiatan+" "+nama_std;
    				}
    				String tmp = id_plan+"~"+tgl_ril+"~"+waktu_ril+"~"+tgl_end+"~"+waktu_end+"~"+nama_kegiatan+"~"+ket_kegiatan;
    				tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
    				li.add(tmp);
    			}
    			while(rs1.next());
    			
    			if(v!=null && v.size()>0) {
        			//insert ke calender
        			sql="INSERT INTO CALENDAR_MUTU(ID_AMI,ID_PLAN_PERENCANAAN,ID_PLAN_PELAKSANAAN,ID_PLAN_EVALUASI,ID_PLAN_PENGENDALIAN,ID_PLAN_PENINGKATAN,TGL_PLAN,TGL_RIL,TGL_DONE,NAMA_KEGIATAN,KETERANGAN_KEGIATAN,WAKTU_PLAN,WAKTU_RIL,WAKTU_END)values" + 
        					"(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        			stmt1=con1.prepareStatement(sql);
        			//ID_AMI,ID_PLAN_PERENCANAAN,ID_PLAN_PELAKSANAAN,ID_PLAN_EVALUASI,ID_PLAN_PENGENDALIAN,ID_PLAN_PENINGKATAN,TGL_PLAN,TGL_RIL,TGL_DONE,NAMA_KEGIATAN,KETERANGAN_KEGIATAN,KDPST_AUDITEE
        			li = v.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				
        				StringTokenizer st = new StringTokenizer(brs,"~");
        				String id_plan = st.nextToken();
        				String tgl_ril = st.nextToken();
        				String waktu_ril = st.nextToken();
        				String tgl_end = st.nextToken();
        				String waktu_end = st.nextToken();
        				String nama_kegiatan = st.nextToken();
        				String ket_kegiatan = st.nextToken();
        				
        				int i=1;
        				//ID_AMI
        				stmt1.setInt(i++,0);
        				//ID_PLAN_PERENCANAAN
        				stmt1.setInt(i++,  Integer.parseInt(id_plan));
        				//ID_PLAN_PELAKSANAAN
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_EVALUASI
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_PENGENDALIAN
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_PENINGKATAN
        				stmt1.setInt(i++, 0);
        				//TGL_PLAN
        				stmt1.setDate(i++,java.sql.Date.valueOf(tgl_ril));
        				//TGL_RIL
        				try {
        					stmt1.setDate(i,java.sql.Date.valueOf(tgl_ril));
        				}
        				catch(Exception e) {
        					stmt1.setNull(i, java.sql.Types.DATE);
        				}
        				i++;
        				
        				//TGL_DONE
        				try {
        					stmt1.setDate(i,java.sql.Date.valueOf(tgl_end));
        				}
        				catch(Exception e) {
        					stmt1.setNull(i, java.sql.Types.DATE);
        				}
        				i++;
        				
        				//NAMA_KEGIATAN
        				stmt1.setString(i++, "["+nama_kegiatan+"]");
        				//KETERANGAN_KEGIATAN
        				stmt1.setString(i++, ket_kegiatan);
        				//waktu
        				if(Checker.isStringNullOrEmpty(waktu_ril)) {
    						//plan
    						stmt1.setNull(i++, java.sql.Types.TIME);
    						//ril
    						stmt1.setNull(i++, java.sql.Types.TIME);
    						
    					}
    					else {
    						//plan
    						stmt1.setTime(i++, java.sql.Time.valueOf(waktu_ril));
    						//ril
    						stmt1.setTime(i++, java.sql.Time.valueOf(waktu_ril));
    					}
        				if(Checker.isStringNullOrEmpty(waktu_end)) {
        					stmt1.setNull(i++, java.sql.Types.TIME);
        				}
        				else {
        					stmt1.setTime(i++, java.sql.Time.valueOf(waktu_end));
        				}
        				
        				upd=upd+stmt1.executeUpdate();
        			}
        		}
        		
    		}
    		/*
    		 * END RIWAYAT PERENCANAAN
    		 * 
    		 * 
    		 * RIWAYAT PELAKSANAAN
    		 */
    		v=null;
    		sql = "SELECT * FROM RIWAYAT_PELAKSANAAN_UMUM A inner join STANDARD_TABLE B on A.ID_STD=B.ID_STD order by TGL_STA_KEGIATAN,WAKTU_STA_KEGIATAN";
    		stmt1=con1.prepareStatement(sql);
    		rs1 = stmt1.executeQuery();
    		if(rs1.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				String id_plan = rs1.getString("ID_PLAN");
    				//String kdpst = rs1.getString("KDPST");
    				String tgl_plan = rs1.getString("TGL_STA_KEGIATAN");
    				String waktu_plan = rs1.getString("WAKTU_STA_KEGIATAN");
    				String tgl_ril = tgl_plan;
    				String waktu_ril = waktu_plan;
    				String tgl_end = rs1.getString("TGL_END_KEGIATAN");
    				String waktu_end = rs1.getString("WAKTU_END_KEGIATAN");
    				String nama_kegiatan = rs1.getString("NAMA_KEGIATAN");
    				String jenis_kegiatan = rs1.getString("JENIS_KEGIATAN");
    				String nama_std = rs1.getString("KET_TIPE_STD");
    				String id_std = rs1.getString("A.ID_STD");
    				String ket_kegiatan = "Kegiatan Pelaksanaan "+nama_std;
    				//System.out.println("jenis_kegiatan="+jenis_kegiatan);
    				//System.out.println("ket_kegiatan="+ket_kegiatan);
    				//if(!Checker.isStringNullOrEmpty(ket_kegiatan)) {
    				//	ket_kegiatan = "Kegiatan "+ket_kegiatan+" "+nama_std;
    				//}
    				String tmp = id_plan+"~"+tgl_ril+"~"+waktu_ril+"~"+tgl_end+"~"+waktu_end+"~"+nama_kegiatan+"~"+ket_kegiatan;
    				tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
    				li.add(tmp);
    			}
    			while(rs1.next());
    			
    			if(v!=null && v.size()>0) {
        			//insert ke calender
        			sql="INSERT INTO CALENDAR_MUTU(ID_AMI,ID_PLAN_PERENCANAAN,ID_PLAN_PELAKSANAAN,ID_PLAN_EVALUASI,ID_PLAN_PENGENDALIAN,ID_PLAN_PENINGKATAN,TGL_PLAN,TGL_RIL,TGL_DONE,NAMA_KEGIATAN,KETERANGAN_KEGIATAN,WAKTU_PLAN,WAKTU_RIL,WAKTU_END)values" + 
        					"(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        			stmt1=con1.prepareStatement(sql);
        			//ID_AMI,ID_PLAN_PERENCANAAN,ID_PLAN_PELAKSANAAN,ID_PLAN_EVALUASI,ID_PLAN_PENGENDALIAN,ID_PLAN_PENINGKATAN,TGL_PLAN,TGL_RIL,TGL_DONE,NAMA_KEGIATAN,KETERANGAN_KEGIATAN,KDPST_AUDITEE
        			li = v.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				
        				StringTokenizer st = new StringTokenizer(brs,"~");
        				String id_plan = st.nextToken();
        				String tgl_ril = st.nextToken();
        				String waktu_ril = st.nextToken();
        				String tgl_end = st.nextToken();
        				String waktu_end = st.nextToken();
        				String nama_kegiatan = st.nextToken();
        				String ket_kegiatan = st.nextToken();
        				
        				int i=1;
        				//ID_AMI
        				stmt1.setInt(i++,0);
        				//ID_PLAN_PERENCANAAN
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_PELAKSANAAN
        				stmt1.setInt(i++, Integer.parseInt(id_plan));
        				//ID_PLAN_EVALUASI
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_PENGENDALIAN
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_PENINGKATAN
        				stmt1.setInt(i++, 0);
        				//TGL_PLAN
        				stmt1.setDate(i++,java.sql.Date.valueOf(tgl_ril));
        				//TGL_RIL
        				try {
        					stmt1.setDate(i,java.sql.Date.valueOf(tgl_ril));
        				}
        				catch(Exception e) {
        					stmt1.setNull(i, java.sql.Types.DATE);
        				}
        				i++;
        				
        				//TGL_DONE
        				try {
        					stmt1.setDate(i,java.sql.Date.valueOf(tgl_end));
        				}
        				catch(Exception e) {
        					stmt1.setNull(i, java.sql.Types.DATE);
        				}
        				i++;
        				
        				//NAMA_KEGIATAN
        				stmt1.setString(i++, "["+nama_kegiatan+"]");
        				//KETERANGAN_KEGIATAN
        				stmt1.setString(i++, ket_kegiatan);
        				//waktu
        				if(Checker.isStringNullOrEmpty(waktu_ril)) {
    						//plan
    						stmt1.setNull(i++, java.sql.Types.TIME);
    						//ril
    						stmt1.setNull(i++, java.sql.Types.TIME);
    						
    					}
    					else {
    						//plan
    						stmt1.setTime(i++, java.sql.Time.valueOf(waktu_ril));
    						//ril
    						stmt1.setTime(i++, java.sql.Time.valueOf(waktu_ril));
    					}
        				if(Checker.isStringNullOrEmpty(waktu_end)) {
        					stmt1.setNull(i++, java.sql.Types.TIME);
        				}
        				else {
        					stmt1.setTime(i++, java.sql.Time.valueOf(waktu_end));
        				}
        				
        				upd=upd+stmt1.executeUpdate();
        			}
        		}
        		
    		}
    		/*
    		 * END RIWAYAT PELAKSANAAN
    		 * 
    		 * 
    		 * RIWAYAT EVALUASI
    		 */
    		v=null;
    		sql = "SELECT * FROM RIWAYAT_EVALUASI_UMUM A inner join STANDARD_TABLE B on A.ID_STD=B.ID_STD order by TGL_STA_KEGIATAN,WAKTU_STA_KEGIATAN";
    		stmt1=con1.prepareStatement(sql);
    		rs1 = stmt1.executeQuery();
    		if(rs1.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				String id_plan = rs1.getString("ID_PLAN");
    				//String kdpst = rs1.getString("KDPST");
    				String tgl_plan = rs1.getString("TGL_STA_KEGIATAN");
    				String waktu_plan = rs1.getString("WAKTU_STA_KEGIATAN");
    				String tgl_ril = tgl_plan;
    				String waktu_ril = waktu_plan;
    				String tgl_end = rs1.getString("TGL_END_KEGIATAN");
    				String waktu_end = rs1.getString("WAKTU_END_KEGIATAN");
    				String nama_kegiatan = rs1.getString("NAMA_KEGIATAN");
    				String jenis_kegiatan = rs1.getString("JENIS_KEGIATAN");
    				String nama_std = rs1.getString("KET_TIPE_STD");
    				String id_std = rs1.getString("A.ID_STD");
    				String ket_kegiatan = "Kegiatan Evaluasi "+nama_std;
    				//System.out.println("jenis_kegiatan="+jenis_kegiatan);
    				//System.out.println("ket_kegiatan="+ket_kegiatan);
    				//if(!Checker.isStringNullOrEmpty(ket_kegiatan)) {
    				//	ket_kegiatan = "Kegiatan "+ket_kegiatan+" "+nama_std;
    				//}
    				String tmp = id_plan+"~"+tgl_ril+"~"+waktu_ril+"~"+tgl_end+"~"+waktu_end+"~"+nama_kegiatan+"~"+ket_kegiatan;
    				tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
    				li.add(tmp);
    			}
    			while(rs1.next());
    			
    			if(v!=null && v.size()>0) {
        			//insert ke calender
        			sql="INSERT INTO CALENDAR_MUTU(ID_AMI,ID_PLAN_PERENCANAAN,ID_PLAN_PELAKSANAAN,ID_PLAN_EVALUASI,ID_PLAN_PENGENDALIAN,ID_PLAN_PENINGKATAN,TGL_PLAN,TGL_RIL,TGL_DONE,NAMA_KEGIATAN,KETERANGAN_KEGIATAN,WAKTU_PLAN,WAKTU_RIL,WAKTU_END)values" + 
        					"(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        			stmt1=con1.prepareStatement(sql);
        			//ID_AMI,ID_PLAN_PERENCANAAN,ID_PLAN_PELAKSANAAN,ID_PLAN_EVALUASI,ID_PLAN_PENGENDALIAN,ID_PLAN_PENINGKATAN,TGL_PLAN,TGL_RIL,TGL_DONE,NAMA_KEGIATAN,KETERANGAN_KEGIATAN,KDPST_AUDITEE
        			li = v.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				
        				StringTokenizer st = new StringTokenizer(brs,"~");
        				String id_plan = st.nextToken();
        				String tgl_ril = st.nextToken();
        				String waktu_ril = st.nextToken();
        				String tgl_end = st.nextToken();
        				String waktu_end = st.nextToken();
        				String nama_kegiatan = st.nextToken();
        				String ket_kegiatan = st.nextToken();
        				
        				int i=1;
        				//ID_AMI
        				stmt1.setInt(i++,0);
        				//ID_PLAN_PERENCANAAN
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_PELAKSANAAN
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_EVALUASI
        				stmt1.setInt(i++, Integer.parseInt(id_plan));
        				//ID_PLAN_PENGENDALIAN
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_PENINGKATAN
        				stmt1.setInt(i++, 0);
        				//TGL_PLAN
        				stmt1.setDate(i++,java.sql.Date.valueOf(tgl_ril));
        				//TGL_RIL
        				try {
        					stmt1.setDate(i,java.sql.Date.valueOf(tgl_ril));
        				}
        				catch(Exception e) {
        					stmt1.setNull(i, java.sql.Types.DATE);
        				}
        				i++;
        				
        				//TGL_DONE
        				try {
        					stmt1.setDate(i,java.sql.Date.valueOf(tgl_end));
        				}
        				catch(Exception e) {
        					stmt1.setNull(i, java.sql.Types.DATE);
        				}
        				i++;
        				
        				//NAMA_KEGIATAN
        				stmt1.setString(i++, "["+nama_kegiatan+"]");
        				//KETERANGAN_KEGIATAN
        				stmt1.setString(i++, ket_kegiatan);
        				//waktu
        				if(Checker.isStringNullOrEmpty(waktu_ril)) {
    						//plan
    						stmt1.setNull(i++, java.sql.Types.TIME);
    						//ril
    						stmt1.setNull(i++, java.sql.Types.TIME);
    						
    					}
    					else {
    						//plan
    						stmt1.setTime(i++, java.sql.Time.valueOf(waktu_ril));
    						//ril
    						stmt1.setTime(i++, java.sql.Time.valueOf(waktu_ril));
    					}
        				if(Checker.isStringNullOrEmpty(waktu_end)) {
        					stmt1.setNull(i++, java.sql.Types.TIME);
        				}
        				else {
        					stmt1.setTime(i++, java.sql.Time.valueOf(waktu_end));
        				}
        				
        				upd=upd+stmt1.executeUpdate();
        			}
        		}
        		
    		}
    		/*
    		 * END RIWAYAT EVALUASI
    		 * 
    		 * 
    		 * RIWAYAT PENGENDALIAN
    		 */
    		v=null;
    		sql = "SELECT * FROM RIWAYAT_PENGENDALIAN_UMUM A inner join STANDARD_TABLE B on A.ID_STD=B.ID_STD order by TGL_STA_KEGIATAN,WAKTU_STA_KEGIATAN";
    		stmt1=con1.prepareStatement(sql);
    		rs1 = stmt1.executeQuery();
    		if(rs1.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				String id_plan = rs1.getString("ID_PLAN");
    				//String kdpst = rs1.getString("KDPST");
    				String tgl_plan = rs1.getString("TGL_STA_KEGIATAN");
    				String waktu_plan = rs1.getString("WAKTU_STA_KEGIATAN");
    				String tgl_ril = tgl_plan;
    				String waktu_ril = waktu_plan;
    				String tgl_end = rs1.getString("TGL_END_KEGIATAN");
    				String waktu_end = rs1.getString("WAKTU_END_KEGIATAN");
    				String nama_kegiatan = rs1.getString("NAMA_KEGIATAN");
    				String jenis_kegiatan = rs1.getString("JENIS_KEGIATAN");
    				String nama_std = rs1.getString("KET_TIPE_STD");
    				String id_std = rs1.getString("A.ID_STD");
    				String ket_kegiatan = "Kegiatan Evaluasi "+nama_std;
    				//System.out.println("jenis_kegiatan="+jenis_kegiatan);
    				//System.out.println("ket_kegiatan="+ket_kegiatan);
    				//if(!Checker.isStringNullOrEmpty(ket_kegiatan)) {
    				//	ket_kegiatan = "Kegiatan "+ket_kegiatan+" "+nama_std;
    				//}
    				String tmp = id_plan+"~"+tgl_ril+"~"+waktu_ril+"~"+tgl_end+"~"+waktu_end+"~"+nama_kegiatan+"~"+ket_kegiatan;
    				tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
    				li.add(tmp);
    			}
    			while(rs1.next());
    			
    			if(v!=null && v.size()>0) {
        			//insert ke calender
        			sql="INSERT INTO CALENDAR_MUTU(ID_AMI,ID_PLAN_PERENCANAAN,ID_PLAN_PELAKSANAAN,ID_PLAN_EVALUASI,ID_PLAN_PENGENDALIAN,ID_PLAN_PENINGKATAN,TGL_PLAN,TGL_RIL,TGL_DONE,NAMA_KEGIATAN,KETERANGAN_KEGIATAN,WAKTU_PLAN,WAKTU_RIL,WAKTU_END)values" + 
        					"(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        			stmt1=con1.prepareStatement(sql);
        			//ID_AMI,ID_PLAN_PERENCANAAN,ID_PLAN_PELAKSANAAN,ID_PLAN_EVALUASI,ID_PLAN_PENGENDALIAN,ID_PLAN_PENINGKATAN,TGL_PLAN,TGL_RIL,TGL_DONE,NAMA_KEGIATAN,KETERANGAN_KEGIATAN,KDPST_AUDITEE
        			li = v.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				
        				StringTokenizer st = new StringTokenizer(brs,"~");
        				String id_plan = st.nextToken();
        				String tgl_ril = st.nextToken();
        				String waktu_ril = st.nextToken();
        				String tgl_end = st.nextToken();
        				String waktu_end = st.nextToken();
        				String nama_kegiatan = st.nextToken();
        				String ket_kegiatan = st.nextToken();
        				
        				int i=1;
        				//ID_AMI
        				stmt1.setInt(i++,0);
        				//ID_PLAN_PERENCANAAN
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_PELAKSANAAN
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_EVALUASI
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_PENGENDALIAN
        				stmt1.setInt(i++, Integer.parseInt(id_plan));
        				//ID_PLAN_PENINGKATAN
        				stmt1.setInt(i++, 0);
        				//TGL_PLAN
        				stmt1.setDate(i++,java.sql.Date.valueOf(tgl_ril));
        				//TGL_RIL
        				try {
        					stmt1.setDate(i,java.sql.Date.valueOf(tgl_ril));
        				}
        				catch(Exception e) {
        					stmt1.setNull(i, java.sql.Types.DATE);
        				}
        				i++;
        				
        				//TGL_DONE
        				try {
        					stmt1.setDate(i,java.sql.Date.valueOf(tgl_end));
        				}
        				catch(Exception e) {
        					stmt1.setNull(i, java.sql.Types.DATE);
        				}
        				i++;
        				
        				//NAMA_KEGIATAN
        				stmt1.setString(i++, "["+nama_kegiatan+"]");
        				//KETERANGAN_KEGIATAN
        				stmt1.setString(i++, ket_kegiatan);
        				//waktu
        				if(Checker.isStringNullOrEmpty(waktu_ril)) {
    						//plan
    						stmt1.setNull(i++, java.sql.Types.TIME);
    						//ril
    						stmt1.setNull(i++, java.sql.Types.TIME);
    						
    					}
    					else {
    						//plan
    						stmt1.setTime(i++, java.sql.Time.valueOf(waktu_ril));
    						//ril
    						stmt1.setTime(i++, java.sql.Time.valueOf(waktu_ril));
    					}
        				if(Checker.isStringNullOrEmpty(waktu_end)) {
        					stmt1.setNull(i++, java.sql.Types.TIME);
        				}
        				else {
        					stmt1.setTime(i++, java.sql.Time.valueOf(waktu_end));
        				}
        				
        				
        				upd=upd+stmt1.executeUpdate();
        			}
        		}
        		
    		}
    		/*
    		 * END RIWAYAT PENGENDALIAN
    		 * 
    		 * 
    		 * RIWAYAT PENINGKATAN
    		 */
    		v=null;
    		sql = "SELECT * FROM RIWAYAT_PENINGKATAN_UMUM A inner join STANDARD_TABLE B on A.ID_STD=B.ID_STD order by TGL_STA_KEGIATAN,WAKTU_STA_KEGIATAN";
    		stmt1=con1.prepareStatement(sql);
    		rs1 = stmt1.executeQuery();
    		if(rs1.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				String id_plan = rs1.getString("ID_PLAN");
    				//String kdpst = rs1.getString("KDPST");
    				String tgl_plan = rs1.getString("TGL_STA_KEGIATAN");
    				String waktu_plan = rs1.getString("WAKTU_STA_KEGIATAN");
    				String tgl_ril = tgl_plan;
    				String waktu_ril = waktu_plan;
    				String tgl_end = rs1.getString("TGL_END_KEGIATAN");
    				String waktu_end = rs1.getString("WAKTU_END_KEGIATAN");
    				String nama_kegiatan = rs1.getString("NAMA_KEGIATAN");
    				String jenis_kegiatan = rs1.getString("JENIS_KEGIATAN");
    				String nama_std = rs1.getString("KET_TIPE_STD");
    				String id_std = rs1.getString("A.ID_STD");
    				String ket_kegiatan = "Kegiatan Evaluasi "+nama_std;
    				//System.out.println("jenis_kegiatan="+jenis_kegiatan);
    				//System.out.println("ket_kegiatan="+ket_kegiatan);
    				//if(!Checker.isStringNullOrEmpty(ket_kegiatan)) {
    				//	ket_kegiatan = "Kegiatan "+ket_kegiatan+" "+nama_std;
    				//}
    				String tmp = id_plan+"~"+tgl_ril+"~"+waktu_ril+"~"+tgl_end+"~"+waktu_end+"~"+nama_kegiatan+"~"+ket_kegiatan;
    				tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
    				li.add(tmp);
    			}
    			while(rs1.next());
    			
    			if(v!=null && v.size()>0) {
        			//insert ke calender
        			sql="INSERT INTO CALENDAR_MUTU(ID_AMI,ID_PLAN_PERENCANAAN,ID_PLAN_PELAKSANAAN,ID_PLAN_EVALUASI,ID_PLAN_PENGENDALIAN,ID_PLAN_PENINGKATAN,TGL_PLAN,TGL_RIL,TGL_DONE,NAMA_KEGIATAN,KETERANGAN_KEGIATAN,WAKTU_PLAN,WAKTU_RIL,WAKTU_END)values" + 
        					"(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        			stmt1=con1.prepareStatement(sql);
        			//ID_AMI,ID_PLAN_PERENCANAAN,ID_PLAN_PELAKSANAAN,ID_PLAN_EVALUASI,ID_PLAN_PENGENDALIAN,ID_PLAN_PENINGKATAN,TGL_PLAN,TGL_RIL,TGL_DONE,NAMA_KEGIATAN,KETERANGAN_KEGIATAN,KDPST_AUDITEE
        			li = v.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				
        				StringTokenizer st = new StringTokenizer(brs,"~");
        				String id_plan = st.nextToken();
        				String tgl_ril = st.nextToken();
        				String waktu_ril = st.nextToken();
        				String tgl_end = st.nextToken();
        				String waktu_end = st.nextToken();
        				String nama_kegiatan = st.nextToken();
        				String ket_kegiatan = st.nextToken();
        				
        				int i=1;
        				//ID_AMI
        				stmt1.setInt(i++,0);
        				//ID_PLAN_PERENCANAAN
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_PELAKSANAAN
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_EVALUASI
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_PENGENDALIAN
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_PENINGKATAN
        				stmt1.setInt(i++, Integer.parseInt(id_plan));
        				//TGL_PLAN
        				stmt1.setDate(i++,java.sql.Date.valueOf(tgl_ril));
        				//TGL_RIL
        				try {
        					stmt1.setDate(i,java.sql.Date.valueOf(tgl_ril));
        				}
        				catch(Exception e) {
        					stmt1.setNull(i, java.sql.Types.DATE);
        				}
        				i++;
        				
        				//TGL_DONE
        				try {
        					stmt1.setDate(i,java.sql.Date.valueOf(tgl_end));
        				}
        				catch(Exception e) {
        					stmt1.setNull(i, java.sql.Types.DATE);
        				}
        				i++;
        				
        				//NAMA_KEGIATAN
        				stmt1.setString(i++, "["+nama_kegiatan+"]");
        				//KETERANGAN_KEGIATAN
        				stmt1.setString(i++, ket_kegiatan);
        				//waktu
        				if(Checker.isStringNullOrEmpty(waktu_ril)) {
    						//plan
    						stmt1.setNull(i++, java.sql.Types.TIME);
    						//ril
    						stmt1.setNull(i++, java.sql.Types.TIME);
    						
    					}
    					else {
    						//plan
    						stmt1.setTime(i++, java.sql.Time.valueOf(waktu_ril));
    						//ril
    						stmt1.setTime(i++, java.sql.Time.valueOf(waktu_ril));
    					}
        				if(Checker.isStringNullOrEmpty(waktu_end)) {
        					stmt1.setNull(i++, java.sql.Types.TIME);
        				}
        				else {
        					stmt1.setTime(i++, java.sql.Time.valueOf(waktu_end));
        				}
        				
        				upd=upd+stmt1.executeUpdate();
        			}
        		}
        		
    		}
    		
    		/*
    		 * END RIWAYAT PENINGKATAN
    		 * 
    		 * 
    		 * RIWAYAT MONITOING
    		 */
    		v=null;
    		sql = "SELECT A.ID_EVAL,A.TGL_EVAL,A.WAKTU_EVAL,A.TGL_NEXT_EVAL,A.KDPST,B.PERNYATAAN_STD,C.KET_TIPE_STD,D.ID_KENDALI,D.TGL_KENDALI FROM RIWAYAT_EVALUASI_AMI A inner join STANDARD_ISI_TABLE B on A.STD_ISI_ID=B.ID inner join STANDARD_TABLE C on B.ID_STD=C.ID_STD left join RIWAYAT_PENGENDALIAN_AMI D on A.ID_EVAL=D.ID_EVAL";
    		stmt1=con1.prepareStatement(sql);
    		rs1 = stmt1.executeQuery();
    		if(rs1.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				int i=1;
    				String id_eval = rs1.getString(i++);
    				String tgl_eval = rs1.getString(i++);
    				String waktu_eval = rs1.getString(i++);
    				String tgl_next_eval = rs1.getString(i++);
    				String kdpst = rs1.getString(i++);
    				String isi_std = rs1.getString(i++);
    				String nama_std = rs1.getString(i++);
    				String id_kendali = rs1.getString(i++);
    				String tgl_kendali = rs1.getString(i++);
    				String nama_kegiatan = "MONITORING "+nama_std.toUpperCase();
    				String ket_kegiatan = "Survey pelaksanaan butir standar : "+isi_std+".";
    				//if(Checker.isStringNullOrEmpty(id_kendali)) {
    				//	ket_kegiatan=ket_kegiatan+" nuline!!! Belum Ada Pengendalian Hasil Survey !!!";
    				//}
    				//System.out.println("jenis_kegiatan="+jenis_kegiatan);
    				//System.out.println("ket_kegiatan="+ket_kegiatan);
    				//if(!Checker.isStringNullOrEmpty(ket_kegiatan)) {
    				//	ket_kegiatan = "Kegiatan "+ket_kegiatan+" "+nama_std;
    				//}
    				String tmp = id_eval+"~"+id_kendali+"~"+tgl_eval+"~"+waktu_eval+"~"+tgl_next_eval+"~"+tgl_kendali+"~"+nama_kegiatan+"~"+ket_kegiatan;
    				tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
    				li.add(tmp);
    			}
    			while(rs1.next());
    			
    			if(v!=null && v.size()>0) {
        			//insert ke calender
        			sql="INSERT INTO CALENDAR_MUTU(ID_AMI,ID_PLAN_PERENCANAAN,ID_PLAN_PELAKSANAAN,ID_PLAN_EVALUASI,ID_PLAN_PENGENDALIAN,ID_PLAN_PENINGKATAN,ID_EVAL,ID_KENDALI,TGL_PLAN,TGL_RIL,TGL_DONE,NAMA_KEGIATAN,KETERANGAN_KEGIATAN,WAKTU_PLAN,WAKTU_RIL,WAKTU_END,TGL_KENDAL,TGL_NEXT_EVAL)values" + 
        					"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        			stmt1=con1.prepareStatement(sql);
        			//ID_AMI,ID_PLAN_PERENCANAAN,ID_PLAN_PELAKSANAAN,ID_PLAN_EVALUASI,ID_PLAN_PENGENDALIAN,ID_PLAN_PENINGKATAN,TGL_PLAN,TGL_RIL,TGL_DONE,NAMA_KEGIATAN,KETERANGAN_KEGIATAN,KDPST_AUDITEE
        			li = v.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				StringTokenizer st = new StringTokenizer(brs,"~");
        				String id_eval = st.nextToken();
        				String id_kendali = st.nextToken();
        				String tgl_eval = st.nextToken();
        				String waktu_eval = st.nextToken();
        				String tgl_next_eval = st.nextToken();
        				String tgl_kendali = st.nextToken();
        				String nama_kegiatan = st.nextToken();
        				String ket_kegiatan = st.nextToken();
        				
        				int i=1;
        				//ID_AMI
        				stmt1.setInt(i++,0);
        				//ID_PLAN_PERENCANAAN
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_PELAKSANAAN
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_EVALUASI
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_PENGENDALIAN
        				stmt1.setInt(i++, 0);
        				//ID_PLAN_PENINGKATAN
        				stmt1.setInt(i++, 0);
        				//ID_EVAL
        				stmt1.setInt(i++, Integer.parseInt(id_eval));
        				//ID_KENDALI
        				try {
        					stmt1.setInt(i, Integer.parseInt(id_kendali));
        				}
        				catch (Exception e) {
        					stmt1.setInt(i, 0);
        				}
        				i++;
        				//TGL_PLAN
        				stmt1.setDate(i++,java.sql.Date.valueOf(tgl_eval));
        				//TGL_RIL
        				try {
        					stmt1.setDate(i,java.sql.Date.valueOf(tgl_eval));
        				}
        				catch(Exception e) {
        					stmt1.setNull(i, java.sql.Types.DATE);
        				}
        				i++;
        				
        				//TGL_DONE = tgl kendali
        				try {
        					stmt1.setDate(i,java.sql.Date.valueOf(tgl_kendali));
        				}
        				catch(Exception e) {
        					stmt1.setNull(i, java.sql.Types.DATE);
        				}
        				i++;
        				
        				//NAMA_KEGIATAN
        				stmt1.setString(i++, "["+nama_kegiatan+"]");
        				//KETERANGAN_KEGIATAN
        				stmt1.setString(i++, ket_kegiatan);
        				//waktu
        				if(Checker.isStringNullOrEmpty(waktu_eval)) {
    						//plan
    						stmt1.setNull(i++, java.sql.Types.TIME);
    						//ril
    						stmt1.setNull(i++, java.sql.Types.TIME);
    						
    					}
    					else {
    						//plan
    						stmt1.setTime(i++, java.sql.Time.valueOf(waktu_eval));
    						//ril
    						stmt1.setTime(i++, java.sql.Time.valueOf(waktu_eval));
    					}
        				//waktu end=tidak ada waktu kendal
        				//if(Checker.isStringNullOrEmpty(waktu_eval)) {
        					stmt1.setNull(i++, java.sql.Types.TIME);
        				//}
        				//else {
        				//	stmt1.setTime(i++, java.sql.Time.valueOf(waktu_eval));
        				//}
        				
        				try {
        					stmt1.setDate(i,java.sql.Date.valueOf(tgl_kendali));
        				}
        				catch(Exception e) {
        					stmt1.setNull(i, java.sql.Types.DATE);
        				}
        				i++;
        				try {
        					stmt1.setDate(i,java.sql.Date.valueOf(tgl_next_eval));
        				}
        				catch(Exception e) {
        					stmt1.setNull(i, java.sql.Types.DATE);
        				}
        				
        				i++;
        				
        				upd=upd+stmt1.executeUpdate();
        				
        				try {
        					//REMINDER monitoring berkala
        					java.sql.Date.valueOf(tgl_next_eval);
        					i=1;
        					norut_reminder--;
            				//ID_AMI
            				stmt1.setInt(i++,0);
            				//ID_PLAN_PERENCANAAN
            				stmt1.setInt(i++, 0);
            				//ID_PLAN_PELAKSANAAN
            				stmt1.setInt(i++, 0);
            				//ID_PLAN_EVALUASI
            				stmt1.setInt(i++, 0);
            				//ID_PLAN_PENGENDALIAN
            				stmt1.setInt(i++, 0);
            				//ID_PLAN_PENINGKATAN
            				stmt1.setInt(i++, 0);
            				//ID_EVAL
            				stmt1.setInt(i++, norut_reminder);
            				//ID_KENDALI
            				stmt1.setInt(i++, 0);
            				
            				//TGL_PLAN
            				stmt1.setDate(i++,java.sql.Date.valueOf(tgl_next_eval));
            				//TGL_RIL
            				stmt1.setNull(i++, java.sql.Types.DATE);
            				
            				
            				//TGL_DONE = tgl kendali
            				stmt1.setNull(i++, java.sql.Types.DATE);
            				
            				
            				//NAMA_KEGIATAN
            				stmt1.setString(i++, "[RENCANA KEGIATAN "+nama_kegiatan+"]");
            				//KETERANGAN_KEGIATAN
            				ket_kegiatan = ket_kegiatan.substring(0, ket_kegiatan.length()-1)+", kelanjutan dari kegiatan monitoring tanggal: "+Converter.autoConvertDateFormat(tgl_eval, "/");
            				stmt1.setString(i++, ket_kegiatan);
            				//waktu
            				//plan
        					stmt1.setNull(i++, java.sql.Types.TIME);
        					//ril
        					stmt1.setNull(i++, java.sql.Types.TIME);
        					
            				//waktu end=tidak ada waktu kendal
            				//if(Checker.isStringNullOrEmpty(waktu_eval)) {
            				stmt1.setNull(i++, java.sql.Types.TIME);
            				//}
            				//else {
            				//	stmt1.setTime(i++, java.sql.Time.valueOf(waktu_eval));
            				//}
            				//tgl_kendali
            				stmt1.setNull(i++, java.sql.Types.DATE);
            				//tgl_next_eval
            				stmt1.setNull(i, java.sql.Types.DATE);
            				
            				
            				upd=upd+stmt1.executeUpdate();
        				}
        				catch(Exception e) {}
        				
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
        	if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
		    if (con1!=null) try { con1.close();} catch (Exception ignore){}
        }	
    	return upd;
    }
    
    public static String getKeteranganKegiatanPerencanaan(String jenis_kegiatan) {
    	String ket="";
    	if(!Checker.isStringNullOrEmpty(jenis_kegiatan)) {
    		if(jenis_kegiatan.contains("rumus")) {
    			ket = "Perumusan";
    		}
    		else if(jenis_kegiatan.contains("cek")) {
    			ket = "Pemeriksaan";
    		}
    		else if(jenis_kegiatan.contains("stuju")) {
    			ket = "Persetujuan";
    		}
    		else if(jenis_kegiatan.contains("tetap")) {
    			ket = "Penetatan";
    		}
    		if(jenis_kegiatan.endsWith("_std")||jenis_kegiatan.endsWith("_STD")) {
    			ket = ket+""; //ngga usah krn bis itu nama standar
    		}
    		else {
    			ket = ket+" Manual";
    		}
    	}
    	return ket;
    }
    
    public static String getNmMasterDanNamaStandar(int id_std_isi) {
    	String tmp = null;
    	
    	ListIterator li = null;
    	Connection con1=null;
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con1 = ds1.getConnection();
    		stmt1=con1.prepareStatement("SELECT C.KET_TIPE_STD,B.KET_TIPE_STD FROM STANDARD_ISI_TABLE A inner join STANDARD_TABLE B on A.ID_STD=B.ID_STD inner join STANDARD_MASTER_TABLE C on B.ID_MASTER_STD=C.ID_MASTER_STD where A.ID=?");
    		stmt1.setInt(1, id_std_isi);
    		rs1 = stmt1.executeQuery();
    		rs1.next();
    		String nm_master = rs1.getString(1);
    		String nm_standar = rs1.getString(2);
    		tmp = new String(nm_master+"~"+nm_standar);
    		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
    	} 
        catch (NamingException e) {
        	e.printStackTrace();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        } 
        finally {
        	if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
		    if (con1!=null) try { con1.close();} catch (Exception ignore){}
        }	
    	return tmp;
    }
    
    public static Vector getIdDanNmMasterStandar() {
    	Vector v = null;
    	
    	ListIterator li = null;
    	Connection con1=null;
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con1 = ds1.getConnection();
    		stmt1=con1.prepareStatement("SELECT * from STANDARD_MASTER_TABLE where ID_MASTER_STD>0");
    		rs1 = stmt1.executeQuery();
    		if(rs1.next()) {
    			v = new Vector();;
    			li = v.listIterator();
    			do {
    				String id_master = rs1.getString(1);
            		String nm_master = rs1.getString(2);
            		String tmp = new String(id_master+"~"+nm_master);
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		li.add(tmp);	
    			}
    			while(rs1.next());
    		}
    		
    	} 
        catch (NamingException e) {
        	e.printStackTrace();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        } 
        finally {
        	if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
		    if (con1!=null) try { con1.close();} catch (Exception ignore){}
        }	
    	return v;
    }
    
    public static Vector getIdDanNmStandar(int id_master_std) {
    	Vector v = null;
    	
    	ListIterator li = null;
    	Connection con1=null;
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con1 = ds1.getConnection();
    		stmt1=con1.prepareStatement("SELECT ID_STD,KET_TIPE_STD FROM STANDARD_TABLE where ID_MASTER_STD=? order by ID_TIPE_STD");
    		stmt1.setInt(1, id_master_std);
    		rs1 = stmt1.executeQuery();
    		if(rs1.next()) {
    			v = new Vector();;
    			li = v.listIterator();
    			do {
    				String id_std = rs1.getString(1);
            		String nm_master = rs1.getString(2);
            		String tmp = new String(id_std+"~"+nm_master);
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            		li.add(tmp);	
    			}
    			while(rs1.next());
    		}
    		
    	} 
        catch (NamingException e) {
        	e.printStackTrace();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        } 
        finally {
        	if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
		    if (con1!=null) try { con1.close();} catch (Exception ignore){}
        }	
    	return v;
    }
    
    public static Vector adjustDbSijitu() {
    	Vector v = null;
    	
    	ListIterator li = null;
    	Connection con1=null;
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con1 = ds1.getConnection();
    		stmt1=con1.prepareStatement("select ID_OBJ,SCOPE_KAMPUS,KODE_KAMPUS_DOMISILI from OBJECT");
    		rs1 = stmt1.executeQuery();
    		if(rs1.next()) {
    			v = new Vector();;
    			li = v.listIterator();
    			do {
    				String idobj = rs1.getString(1);
            		String scope = rs1.getString(2);
            		String dom = rs1.getString(3);
            		if(!Checker.isStringNullOrEmpty(scope)) {
            			StringTokenizer st = new StringTokenizer(scope,"#");
            			String tkn_scope = "";
            			while(st.hasMoreTokens()) {
            				st.nextToken();
            				tkn_scope = tkn_scope+dom.trim();
            				if(st.hasMoreTokens()) {
            					tkn_scope=tkn_scope+"#";
            				}
            			}
            			tkn_scope=tkn_scope+"#";
            			li.add(idobj+"~"+tkn_scope+"~"+dom);
            			//System.out.println(idobj+"~"+tkn_scope+"~"+dom);
            		}
            		if(v!=null) {
            			li = v.listIterator();
            			stmt1 = con1.prepareStatement("update OBJECT set SCOPE_KAMPUS=? where ID_OBJ=?");
            			while(li.hasNext()) {
            				String brs = (String)li.next();
            				StringTokenizer st = new StringTokenizer(brs,"~");
            				idobj = st.nextToken();
            				scope = st.nextToken();
            				stmt1.setString(1,scope);
            				stmt1.setInt(2, Integer.parseInt(idobj));
            				stmt1.executeUpdate();
            			}
            		}
    			}
    			while(rs1.next());
    		}
    		
    	} 
        catch (NamingException e) {
        	e.printStackTrace();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        } 
        finally {
        	if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
		    if (con1!=null) try { con1.close();} catch (Exception ignore){}
        }	
    	return v;
    }
}



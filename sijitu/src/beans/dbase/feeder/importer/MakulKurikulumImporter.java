package beans.dbase.feeder.importer;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.Tool;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * Session Bean implementation class KapasitasImporter
 */
@Stateless
@LocalBean
public class MakulKurikulumImporter extends SearchDb {
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
    public MakulKurikulumImporter() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public MakulKurikulumImporter(String operatorNpm) {
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
    public MakulKurikulumImporter(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

    public Vector getDataKrklm() {
    	
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from KRKLM");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String idkur = ""+rs.getLong("IDKURKRKLM");
    			String kdpst = ""+rs.getString("KDPSTKRKLM");
    			String nmkur = ""+rs.getString("NMKURKRKLM");
    			String stkur = ""+rs.getString("STKURKRKLM");
    			if(Checker.isStringNullOrEmpty(stkur)) {
    				stkur="null";
    			}
    			String start = ""+rs.getString("STARTTHSMS");
    			if(Checker.isStringNullOrEmpty(start)) {
    				start="null";
    			}
    			String ended = ""+rs.getString("ENDEDTHSMS");
    			if(Checker.isStringNullOrEmpty(ended)) {
    				ended="null";
    			}
    			//String targ = ""+rs.getString("TARGTKRKLM");
    			String skstt = ""+rs.getInt("SKSTTKRKLM");
    			if(Checker.isStringNullOrEmpty(skstt)) {
    				skstt="0";
    			}
    			String smstt = ""+rs.getInt("SMSTTKRKLM");
    			if(Checker.isStringNullOrEmpty(smstt)) {
    				smstt="0";
    			}
    			String konsen = ""+rs.getString("KONSENTRASI");
    			//if(Checker.isStringNullOrEmpty(konsen)) {
    			//	konsen="null";
    			//}
    			//else {
    			//	nmkur=nmkur+"["+konsen.toUpperCase()+"]";
    			//}
    			if(v==null) {
    				v =new Vector();
    				li = v.listIterator();
    			}
    			if(!kdpst.equalsIgnoreCase("57301") && !kdpst.equalsIgnoreCase("57302")) {
    				li.add(idkur+"`"+kdpst+"`"+nmkur+"`"+stkur+"`"+start+"`"+ended+"`"+skstt+"`"+smstt+"`"+konsen);	
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
    	return v;
    }
    
    public Vector getDataMakur() {
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	Vector v = getDataKrklm();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=? order by SEMESMAKUR");
    		
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			lif.add(brs);
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String idkur = st.nextToken();
    			String kdpst = st.nextToken();
    			String nmkur = st.nextToken();
    			System.out.println(nmkur);
    			String stkur = st.nextToken();
    			String start = st.nextToken();
    			String ended = st.nextToken();
    			String skstt = st.nextToken();
    			String smstt = st.nextToken();
    			String konsen = st.nextToken();
    			stmt.setLong(1, Long.parseLong(idkur));
    			rs = stmt.executeQuery();
    			Vector v_makul = new Vector();
    			ListIterator lim = v_makul.listIterator();
    			while(rs.next()) {
    				String semes = ""+rs.getInt("SEMESMAKUR");
    				String mk_akhir = ""+rs.getBoolean("FINAL_MK");
    				String kdkmk = ""+rs.getString("KDKMKMAKUL");
    				String nakmk = ""+rs.getString("NAKMKMAKUL");
    				int skstm = rs.getInt("SKSTMMAKUL");
    				int skspr = rs.getInt("SKSPRMAKUL");
    				int skslp = rs.getInt("SKSLPMAKUL");
    				int sksmk = skstm+skspr+skslp;
    				String jenis = ""+rs.getString("JENISMAKUL");
    				lim.add(semes+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp+"`"+sksmk+"`"+jenis+"`"+mk_akhir);
    			}
    			System.out.println("nmkur="+nmkur+" , "+v_makul.size());
    			lif.add(v_makul);
    			if(v_makul!=null) {
    				System.out.println(v_makul.size());
    			}
    			else {
    				System.out.println("v_makul=null");
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
    	return vf;
    }
    
    
    public Vector importMakulKrklm() {
    	Vector v_err = new Vector();
    	ListIterator lier= v_err.listIterator();
    	//ListIterator lio = v_out.listIterator();
    	Vector v = getDataMakur();
    	if(v!=null && v.size()>0) {
    		ListIterator li = v.listIterator();
        	try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc/FEEDER_IMPORTER");
    			con = ds.getConnection();
    			//get id_sms
    			stmt = con.prepareStatement("select id_sms,id_jenj_didik from sms where kode_prodi=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String idkur = st.nextToken();
    				String kdpst = st.nextToken();
    				if(kdpst.equalsIgnoreCase("88888")) {
    					kdpst = "86208";
    				}
    				String nmkur = st.nextToken();
    				String stkur = st.nextToken();
    				String start = st.nextToken();
    				String ended = st.nextToken();
    				String skstt = st.nextToken();
    				String smstt = st.nextToken();
    				String konsen = st.nextToken();
    				stmt.setString(1, kdpst);
    				rs = stmt.executeQuery();
    				rs.next();
    				String id_sms = rs.getString(1);
    				String id_jen = ""+rs.getLong(2);
    				li.set(brs+"`"+id_sms+"`"+id_jen);
    				Vector v1 = (Vector)li.next();
    			}
    			
    			//get id_kurikulum_sp
    			li = v.listIterator();
    			stmt = con.prepareStatement("select id_kurikulum_sp from kurikulum where nm_kurikulum_sp=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String idkur = st.nextToken();
    				String kdpst = st.nextToken();
    				String nmkur = st.nextToken();
    				String stkur = st.nextToken();
    				String start = st.nextToken();
    				String ended = st.nextToken();
    				String skstt = st.nextToken();
    				String smstt = st.nextToken();
    				String konsen = st.nextToken();
    				String id_sms = st.nextToken();
    				String id_jen = st.nextToken();
    				if(!Checker.isStringNullOrEmpty(konsen)) {
    					stmt.setString(1, nmkur+"["+konsen+"]"); //kalo ada konsen formatnya begitu di importer
    					System.out.println("nmkur="+nmkur+"["+konsen+"]");
    				}
    				else {
    					stmt.setString(1, nmkur);
    					System.out.println("nmkur="+nmkur);
    				}
    				
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					String id_kur_sp = rs.getString(1);
    					li.set(brs+"`"+id_kur_sp);
    					
    				}
    				else {
    					//KEMUNGKINANA KURIKULUM STATUSNYA DIHAPUS JADI TIDAK ADA DI IMPORTER
    				}
    				Vector v1 = (Vector)li.next();
    				
    			}
    			
    			
    			//get id_makul
    			int norut=0;
    			stmt = con.prepareStatement("select * from mata_kuliah where id_sms=? and kode_mk=? and sks_mk=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				//System.out.println("baris="+brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String idkur = st.nextToken();
    				String kdpst = st.nextToken();
    				String nmkur = st.nextToken();
    				String stkur = st.nextToken();
    				String start = st.nextToken();
    				String ended = st.nextToken();
    				String skstt = st.nextToken();
    				String smstt = st.nextToken();
    				String konsen = st.nextToken();
    				String id_sms = st.nextToken();
    				String id_jen = st.nextToken();
    				if(st.hasMoreTokens()) { //kalo KURIKULUM YG DIHAPUS JADI NGGA ADA DI IMPORTER
    					String id_kur = st.nextToken();
        				Vector v1 = (Vector)li.next();
        				ListIterator li1 = v1.listIterator();
        				while(li1.hasNext()) {
        					String brs1 = (String)li1.next();
        					st = new StringTokenizer(brs1,"`");
        					//System.out.println("brs1="+brs1);
        					String semes = st.nextToken();
        					String kdkmk = st.nextToken();
        					String nakmk = st.nextToken();
        					String skstm = st.nextToken();
        					String skspr = st.nextToken();
        					String skslp = st.nextToken();
        					String sksmk = st.nextToken();
        					String jenis = st.nextToken();
        					String mk_akhir = st.nextToken();
        					stmt.setString(1, id_sms);
        					stmt.setString(2, kdkmk);
        					stmt.setLong(3, Long.parseLong(sksmk));
        					rs = stmt.executeQuery();
        					rs.next();
        					String id_mk = rs.getString("id_mk");
        					li1.set(brs1+"`"+id_mk);
        					//if(nmkur.contains("AGRIBIS_1")) {
        					//	norut++;
        					//	System.out.println(norut+". "+brs1+"`"+id_mk);
        					//}
        					//
        				}	
    				}
    				else {
    					Vector v1 = (Vector)li.next();
    				}
    				
    			}
    			
    			//coba update dulu
    			norut=0;
    			Vector v_ins = new Vector();
    			ListIterator lins = v_ins.listIterator();
    			stmt = con.prepareStatement("update mata_kuliah_kurikulum set smt=?,sks_mk=?,sks_tm=?,sks_prak=?,sks_prak_lap=?,sks_sim=?,a_wajib=? where id_kurikulum_sp=? and id_mk=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				//System.out.println("baris="+brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String idkur = st.nextToken();
    				String kdpst = st.nextToken();
    				String nmkur = st.nextToken();
    				String stkur = st.nextToken();
    				String start = st.nextToken();
    				String ended = st.nextToken();
    				String skstt = st.nextToken();
    				String smstt = st.nextToken();
    				String konsen = st.nextToken();
    				String id_sms = st.nextToken();
    				String id_jen = st.nextToken();
    				if(st.hasMoreTokens()) {
    					String id_kur = st.nextToken();
        				Vector v1 = (Vector)li.next();
        				ListIterator li1 = v1.listIterator();
        				while(li1.hasNext()) {
        					String brs1 = (String)li1.next();
        					st = new StringTokenizer(brs1,"`");
        					//System.out.println("brs1="+brs1);
        					String semes = st.nextToken();
        					String kdkmk = st.nextToken();
        					String nakmk = st.nextToken();
        					String skstm = st.nextToken();
        					String skspr = st.nextToken();
        					String skslp = st.nextToken();
        					String sksmk = st.nextToken();
        					String jenis = st.nextToken();
        					String mk_akhir = st.nextToken();
        					String id_mk = st.nextToken();
        					stmt.setDouble(1, Double.parseDouble(semes));
        					stmt.setDouble(2, Double.parseDouble(sksmk));
        					stmt.setDouble(3, Double.parseDouble(skstm));
        					stmt.setDouble(4, Double.parseDouble(skspr));
        					stmt.setDouble(5, Double.parseDouble(skslp));
        					stmt.setDouble(6, 0);
        					stmt.setBoolean(7, true);
        					stmt.setString(8, id_kur);
        					stmt.setString(9, id_mk);
        					int i = stmt.executeUpdate();
        					if(i<1) {
        						lins.add(id_kur+"`"+id_mk+"`"+semes+"`"+sksmk+"`"+skstm+"`"+skspr+"`"+skslp+"`0`true");
        						//if(nmkur.contains("AGRIBIS_1")) {
            					//	norut++;
            					//	System.out.println("."+norut+". "+id_kur+"`"+id_mk+"`"+semes+"`"+sksmk+"`"+skstm+"`"+skspr+"`"+skslp+"`0`true");
            					//}
        					}
        				}	
    				}
    				else {
    					Vector v1 = (Vector)li.next();
    				}
    			}
    			
    			
    			norut=0;
    			stmt= con.prepareStatement("INSERT INTO mata_kuliah_kurikulum (id_kurikulum_sp,id_mk,smt,sks_mk,sks_tm,sks_prak,sks_prak_lap,sks_sim,a_wajib) values (?,?,?,?,?,?,?,?,?)");
    			lins = v_ins.listIterator();
    			while(lins.hasNext()) {
    				String brs = (String)lins.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				System.out.println("baris="+brs);
    				String id_kur = st.nextToken();
    				String id_mk = st.nextToken();
    				String semes = st.nextToken();
    				String sksmk = st.nextToken();
    				String skstm = st.nextToken();
    				String skspr = st.nextToken();
    				String skslp = st.nextToken();
    				String sksim = st.nextToken();
    				String wajib = st.nextToken();
    				stmt.setString(1, id_kur);
    				stmt.setString(2, id_mk);
    				stmt.setDouble(3, Double.parseDouble(semes));
    				stmt.setDouble(4, Double.parseDouble(sksmk));
    				stmt.setDouble(5, Double.parseDouble(skstm));
    				stmt.setDouble(6, Double.parseDouble(skspr));
    				stmt.setDouble(7, Double.parseDouble(skslp));
    				stmt.setDouble(8, Double.parseDouble(sksim));
    				stmt.setBoolean(9, Boolean.parseBoolean(wajib));
    				int i = stmt.executeUpdate();
    				//if(id_kur.contains("32028464-9f7d-4d59-b83b-798ff6be150d")) {
					//	norut++;
					//	System.out.println("insert."+norut+". "+id_kur+"`"+id_mk+"`"+semes+"`"+sksmk+"`"+skstm+"`"+skspr+"`"+skslp+"`0`true="+i);
					//}
    				if(i<1) {
    					lier.add(brs);
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
    	
    	return v_err;  
    	
    }
    

}

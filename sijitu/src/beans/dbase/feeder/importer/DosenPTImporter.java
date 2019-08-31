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
public class DosenPTImporter extends SearchDb {
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
    public DosenPTImporter() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public DosenPTImporter(String operatorNpm) {
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
    public DosenPTImporter(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

    public Vector getDataKelasPoll(String target_thsms) {
    	//Tool.buatSatuSpasiAntarKata(brs)
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from CLASS_POOL WHERE THSMS=? order by KODE_PENGGABUNGAN, CANCELED");
    		stmt.setString(1, target_thsms);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String idkur = ""+rs.getLong("IDKUR");
    			String idkmk = ""+rs.getLong("IDKMK");
    			//String thsms = ""+rs.getString("THSMS");
    			String kdpst = ""+rs.getString("KDPST");
    			if(kdpst.equalsIgnoreCase("88888")) {
    				kdpst = "86208";
    			}
    			String shift = ""+rs.getString("SHIFT");
    			if(Checker.isStringNullOrEmpty(shift)) {
    				shift="null";
    			}
    			String nopll = ""+rs.getLong("NORUT_KELAS_PARALEL");
    			String npmdos = ""+rs.getString("NPMDOS");
    			if(Checker.isStringNullOrEmpty(npmdos)) {
    				npmdos="null";
    			}
    			String nodos = ""+rs.getString("NODOS");
    			if(Checker.isStringNullOrEmpty(nodos)) {
    				nodos="null";
    			}
    			String cancel = ""+rs.getBoolean("CANCELED");
    			String kelas = ""+rs.getString("KODE_KELAS");
    			if(Checker.isStringNullOrEmpty(kelas)) {
    				kelas="null";
    			}
    			String ruang = ""+rs.getString("KODE_RUANG");
    			if(Checker.isStringNullOrEmpty(ruang)) {
    				ruang="null";
    			}
    			String gdung = ""+rs.getString("KODE_GEDUNG");
    			if(Checker.isStringNullOrEmpty(gdung)) {
    				gdung="null";
    			}
    			String kdkmp = ""+rs.getString("KODE_KAMPUS");
    			if(Checker.isStringNullOrEmpty(kdkmp)) {
    				kdkmp="null";
    			}
    			String jadwal = ""+rs.getString("TKN_HARI_TIME");
    			if(Checker.isStringNullOrEmpty(jadwal)) {
    				jadwal="null";
    			}
    			String nmdos = ""+rs.getString("NMMDOS");
    			if(Checker.isStringNullOrEmpty(nmdos)) {
    				nmdos="null";
    			}
    			String kode_gab = ""+rs.getString("KODE_PENGGABUNGAN");
    			if(Checker.isStringNullOrEmpty(kode_gab)) {
    				kode_gab="null";
    			}
    			String uid = ""+rs.getLong("UNIQUE_ID");		
    			
    			if(v==null) {
    				v =new Vector();
    				li = v.listIterator();
    			}
    			if(!kdpst.equalsIgnoreCase("57301") && !kdpst.equalsIgnoreCase("57302")) {
    				li.add(idkur+"`"+idkmk+"`"+kdpst+"`"+shift+"`"+nopll+"`"+npmdos+"`"+nodos+"`"+kelas+"`"+ruang+"`"+gdung+"`"+kdkmp+"`"+jadwal+"`"+nmdos+"`"+kode_gab+"`"+uid);
    				//System.out.println(idkur+"`"+idkmk+"`"+kdpst+"`"+shift+"`"+nopll+"`"+npmdos+"`"+nodos+"`"+kelas+"`"+ruang+"`"+gdung+"`"+kdkmp+"`"+jadwal+"`"+nmdos+"`"+kode_gab+"`"+uid);
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
    
    public Vector getDataTrakdEpsbed(String target_thsms) {
    	//Tool.buatSatuSpasiAntarKata(brs)
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from TRAKD_EPSBED WHERE THSMSTRAKD=? order by KDPSTTRAKD,KDKMKTRAKD,KELASTRAKD");
    		stmt.setString(1, target_thsms);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdpst = ""+rs.getString("KDPSTTRAKD");
    			//if(kdpst.equalsIgnoreCase("88888")) {
    			//	kdpst = "86208";
    			//}
    			String nodos = ""+rs.getString("NODOSTRAKD");
    			String nmdos = ""+rs.getString("NMDOSTRAKD");
    			String kdkmk = ""+rs.getString("KDKMKTRAKD");
    			String nakmk = ""+rs.getString("NAKMKTRAKD");
    			String kelas = ""+rs.getString("KELASTRAKD");
    			String sksmk = ""+rs.getInt("SKSMKTRAKD");
    			String surat = ""+rs.getString("SURAT_TUGAS");

    			if(!kdpst.equalsIgnoreCase("57301") && !kdpst.equalsIgnoreCase("57302")) {
    				String tmp = kdpst+"`"+kdkmk+"`"+nakmk+"`"+nodos+"`"+nmdos+"`"+kelas+"`"+sksmk+"`"+surat;
    				tmp = tmp.replace("``", "`null`");
    				if(v==null) {
    					v = new Vector();
    					li = v.listIterator();
    				}
    				li.add(tmp);
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
    	//Vector v = getDataKrklm();
    	//ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=? order by SEMESMAKUR");
    		
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
    
    
    public Vector importDosenPt(String thsms) {
    	Vector v_err = new Vector();
    	ListIterator lier= v_err.listIterator();
    	//ListIterator lio = v_out.listIterator();
    	Vector v = getDataTrakdEpsbed(thsms);
    	if(v!=null && v.size()>0) {
    		ListIterator li = v.listIterator();
        	try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc/FEEDER_IMPORTER");
    			con = ds.getConnection();
    			//get id_sms
    			stmt = con.prepareStatement("select id_sms from sms where kode_prodi=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdpst = st.nextToken();
    				if(kdpst.equalsIgnoreCase("88888")) {
    					kdpst = "86208";
    	    		}
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String nodos = st.nextToken();
    				String nmdos = st.nextToken();
    				String kelas = st.nextToken();
    				String sksmk = st.nextToken();
    				String surat = st.nextToken();
    				stmt.setString(1, kdpst);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					String id_sms = rs.getString(1);
    					li.set(brs+"`"+id_sms);
    				}
    			}
    			stmt = con.prepareStatement("select id_mk,sks_tm,sks_prak,sks_prak_lap,sks_sim from mata_kuliah where id_sms=? and kode_mk=? and nm_mk like ? and sks_mk=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				//System.out.println(brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdpst = st.nextToken();
    				if(kdpst.equalsIgnoreCase("88888")) {
    					kdpst = "86208";
    	    		}
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String nodos = st.nextToken();
    				String nmdos = st.nextToken();
    				String kelas = st.nextToken();
    				String sksmk = st.nextToken();
    				String surat = st.nextToken();
    				String id_sms = st.nextToken();
    				stmt.setString(1, id_sms);
    				stmt.setString(2, kdkmk);
    				
    				stmt.setString(3, Tool.buatSatuSpasiAntarKata(nakmk)+"%");
    				stmt.setInt(4, Integer.parseInt(sksmk));
    				rs = stmt.executeQuery();
    				rs.next();
    				String id_mk = rs.getString(1);
    				String sks_tm = ""+rs.getInt(2);
    				String sks_prak = ""+rs.getInt(3);
    				String sks_prak_lap = ""+rs.getInt(4);
    				String sks_sim = ""+rs.getInt(5);
    				li.set(brs+"`"+id_mk+"`"+sks_tm+"`"+sks_prak+"`"+sks_prak_lap+"`"+sks_sim);
    				//
    			}
    			
    			stmt =con.prepareStatement("update dosen_pt set tgl_srt_tgs=?,tgl_srt_tgs=?,tmt_srt_tgs=?,tgl_ptk_keluar=?,id_stat_pegawai=?,id_jns_keluar=?,id_ikatan_kerja=? where id_sms=? and id_sdm=?");
    			
    			String tgl_srt_tgs=null;
    			String tmt_srt_tgs=null;
    			String tgl_ptk_keluar=null;
    			String id_stat_pegawai=null;
    			String id_jns_keluar=null;
    			String id_ikatan_kerja=null;
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdpst = st.nextToken();
    				if(kdpst.equalsIgnoreCase("88888")) {
    					kdpst = "86208";
    	    		}
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String nodos = st.nextToken();
    				String nmdos = st.nextToken();
    				String kelas = st.nextToken();
    				String sksmk = st.nextToken();
    				String surat = st.nextToken();
    				String id_sms = st.nextToken();
    				String id_mk = st.nextToken();
    				String sks_tm = st.nextToken();
    				String sks_prak = st.nextToken();
    				String sks_prak_lap = st.nextToken();
    				String sks_sim = st.nextToken();
    				//(id_sms,id_smt,nm_kls,sks_mk,sks_tm,sks_prak,sks_prak_lap,sks_sim,id_mk)
    				stmt.setString(1, id_sms);
    				stmt.setString(2, thsms);
    				stmt.setString(3, kelas);
    				stmt.setInt(4, Integer.parseInt(sksmk));
    				stmt.setInt(5, Integer.parseInt(sks_tm));
    				stmt.setInt(6, Integer.parseInt(sks_prak));
    				stmt.setInt(7, Integer.parseInt(sks_prak_lap));
    				stmt.setInt(8, Integer.parseInt(sks_sim));
    				stmt.setString(9, id_mk);
    				//System.out.print(brs+"=");
    				int i = stmt.executeUpdate();
    				//System.out.println(i);
    				
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

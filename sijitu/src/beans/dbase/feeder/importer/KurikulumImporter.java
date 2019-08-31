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
public class KurikulumImporter extends SearchDb {
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
    public KurikulumImporter() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public KurikulumImporter(String operatorNpm) {
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
    public KurikulumImporter(Connection con) {
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
    		stmt = con.prepareStatement("select * from KRKLM where STKURKRKLM='A'");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
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
    			if(Checker.isStringNullOrEmpty(konsen)) {
    				konsen="null";
    			}
    			else {
    				nmkur=nmkur+"["+konsen.toUpperCase()+"]";
    			}
    			if(v==null) {
    				v =new Vector();
    				li = v.listIterator();
    			}
    			li.add(kdpst+"`"+nmkur+"`"+stkur+"`"+start+"`"+ended+"`"+skstt+"`"+smstt+"`"+konsen);
    			
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
    
    public Vector importKrklm() {
    	Vector v_err = new Vector();
    	ListIterator lie = v_err.listIterator();
    	//ListIterator lio = v_out.listIterator();
    	Vector v = getDataKrklm();
    	
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
    				String brs=(String)li.next();
    				//li.add(kdpst+"`"+nmkur+"`"+stkur+"`"+start+"`"+ended+"`"+skstt+"`"+sksmt+"`"+konsen);
    				StringTokenizer st = new StringTokenizer(brs,"`");
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
    				if(rs.next()) {
    					li.set(brs+"`"+rs.getString(1)+"`"+rs.getInt(2));
    				}
    				else {
    					System.out.println("removed = "+brs );
    					li.remove();
    				}
    			}
    			
    			Vector v_ins = new Vector();
    			ListIterator lins = v_ins.listIterator();
    			Vector v_upd = new Vector();
    			ListIterator liup = v_upd.listIterator();
    			//cek apa sudah ada di feeder, sekalian menentukan update / insert
    			stmt = con.prepareStatement("select id_kurikulum_sp from kurikulum where nm_kurikulum_sp=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdpst = st.nextToken();
    				String nmkur = st.nextToken();
    				stmt.setString(1, nmkur.toUpperCase());
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					String id_kur = rs.getString(1);
    					liup.add(brs+"`"+id_kur);
    				}
    				else {
    					lins.add(brs);
    				}
    				
    			}
    			
    			if(v_upd.size()>0) {
    				stmt = con.prepareStatement("update kurikulum set jml_sem_normal=?,jml_sks_lulus=?,jml_sks_wajib=?,jml_sks_pilihan=? where id_kurikulum_sp=?");
    				liup = v_upd.listIterator();
    				while(liup.hasNext()) {
    					String brs = (String) liup.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String kdpst = st.nextToken();
        				String nmkur = st.nextToken();
        				String stkur = st.nextToken();
        				String start = st.nextToken();
        				String ended = st.nextToken();
        				String skstt = st.nextToken();
        				String smstt = st.nextToken();
        				String konsen = st.nextToken();
        				String id_sms = st.nextToken();
        				String id_jenj_didik = st.nextToken();
        				String id_kur = st.nextToken();
        				stmt.setFloat(1,Float.parseFloat(smstt));
        				stmt.setFloat(2,Float.parseFloat(skstt));
        				stmt.setFloat(3, 0);
        				stmt.setFloat(4, 0);
        				stmt.setString(5, id_kur);
        				int i = stmt.executeUpdate();
        				if(i<1) {
        					lie.add("gagal update = "+brs);
        				}
    				}
    			}
    			
    			if(v_ins.size()>0) {
    				int no_id = 0;
    				stmt = con.prepareStatement("insert into kurikulum(id_kurikulum_sp,id_sms,id_jenj_didik,id_smt,nm_kurikulum_sp,jml_sem_normal,jml_sks_lulus,jml_sks_wajib,jml_sks_pilihan,tmp1)values(?,?,?,?,?,?,?,?,?,?)");
    				lins = v_ins.listIterator();
    				while(lins.hasNext()) {
    					String brs = (String) lins.next();
    					//System.out.println("brs="+brs);
    					StringTokenizer st = new StringTokenizer(brs,"`");
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
        				//String id_kur = st.nextToken();
        				stmt.setString(1,""+no_id);
        				stmt.setString(2,id_sms);
        				stmt.setString(3,id_jen);
        				stmt.setString(4,"20152");
        				stmt.setString(5,nmkur);
        				stmt.setDouble(6, Double.parseDouble(smstt));
        				stmt.setDouble(7, Double.parseDouble(skstt));
        				stmt.setDouble(8, 0);
        				stmt.setDouble(9, 0);
        				stmt.setString(10,""+no_id++);
        				int i = stmt.executeUpdate();
        				if(i<1) {
        					lie.add("gagal insert = "+brs);
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
    	
    	return v_err;  
    	
    }
    
}

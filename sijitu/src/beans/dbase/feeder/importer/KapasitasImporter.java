package beans.dbase.feeder.importer;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.Getter;

import java.sql.Connection;
import java.sql.Date;
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
 * Session Bean implementation class KapasitasImporter
 */
@Stateless
@LocalBean
public class KapasitasImporter extends SearchDb {
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
    public KapasitasImporter() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public KapasitasImporter(String operatorNpm) {
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
    public KapasitasImporter(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

    public Vector getDataKapasitas(String thsms) {
    	String thsms_now = Checker.getThsmsNow();
    	Vector v = new Vector();
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		v = Getter.getListProdi();
    		stmt = con.prepareStatement("select * from KAPASITAS where THSMS=? and KDPST=?");
    		stmt.setString(1, thsms);
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kdpst = st.nextToken();
    			if(kdpst.equalsIgnoreCase("88888")) {
    				kdpst = "86208";
    			}
    			stmt.setString(1, thsms);
    			stmt.setString(2, kdpst);
    			rs = stmt.executeQuery();
    			//Vector vtmp = new Vector();
    			//ListIterator lit = vtmp.listIterator();
    			//lit.add(kdpst);
    			//System.out.println(kdpst);
    			if(rs.next()) {
    				double target_mhs_baru = rs.getDouble("target_mhs_baru");
    				double calon_ikut_seleksi = rs.getDouble("calon_ikut_seleksi");
    				double calon_lulus_seleksi = rs.getDouble("calon_lulus_seleksi");
    				double daftar_sbg_mhs = rs.getDouble("daftar_sbg_mhs");
    				double pst_undur_diri = rs.getDouble("pst_undur_diri");
    				String tgl_awal_kul = ""+rs.getDate("tgl_awal_kul");
    				String tgl_akhir_kul = ""+rs.getDate("tgl_akhir_kul");
    				double jml_mgu_kul = rs.getDouble("jml_mgu_kul");
    				String metode_kul = ""+rs.getString("metode_kul");
    				String metode_kul_eks = ""+rs.getString("metode_kul_eks");
    				
    				li.set(thsms+"`"+kdpst+"`"+target_mhs_baru+"`"+calon_ikut_seleksi+"`"+calon_lulus_seleksi+"`"+daftar_sbg_mhs+"`"+pst_undur_diri+"`"+tgl_awal_kul+"`"+tgl_akhir_kul+"`"+jml_mgu_kul+"`"+metode_kul+"`"+metode_kul_eks);
    				    				
    				//System.out.println(nlakh+"`"+bobot+"`"+nlmin+"`"+nlmax+"`"+tgsta+"`"+tgend);
    			}
    			else {
    				li.set(thsms+"`"+kdpst+"`0`0`0`0`0`null`null`0`null`null");
    			}
    			//li.set(vtmp);
    			
    			
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
    
    public Vector importCapacity(String thsms) {
    	Vector v_out = new Vector();
    	ListIterator lio = v_out.listIterator();
    	//ListIterator lio = v_out.listIterator();
    	Vector v = getDataKapasitas(thsms);
    	if(v!=null && v.size()>0) {
    		ListIterator li = v.listIterator();
        	try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc/FEEDER_IMPORTER");
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select id_sms from sms where kode_prodi=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				//li.set(thsms+"`"+kdpst+"`"+target_mhs_baru+"`"+calon_ikut_seleksi+"`"+calon_lulus_seleksi+"`"+daftar_sbg_mhs+"`"+pst_undur_diri+"`"+tgl_awal_kul+"`"+tgl_akhir_kul+"`"+jml_mgu_kul+"`"+metode_kul+"`"+metode_kul_eks);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				thsms = st.nextToken();
    				String kdpst = st.nextToken();
    				
    				stmt.setString(1, kdpst);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					String id_sms = rs.getString(1);
    					li.set(id_sms+"`"+brs);
    				}
    				else {
    					li.remove();
    					lio.add("no_match`"+brs);
    				}
    			}
    			Vector v_ins = null;
    			ListIterator lin = null;
    			stmt = con.prepareStatement("update daya_tampung set target_mhs_baru=?,calon_ikut_seleksi=?,calon_lulus_seleksi=?,daftar_sbg_mhs=?,pst_undur_diri=?,tgl_awal_kul=?,tgl_akhir_kul=?,jml_mgu_kul=?,metode_kul=?,metode_kul_eks=? where id_smt=? and id_sms=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String idsms = st.nextToken();
    				thsms = st.nextToken();
    				String kdpst = st.nextToken();
    				String target_mhs_baru = st.nextToken();
    				String calon_ikut_seleksi = st.nextToken();
    				String calon_lulus_seleksi = st.nextToken();
    				String daftar_sbg_mhs = st.nextToken();
    				String pst_undur_diri = st.nextToken();
    				String tgl_awal_kul = st.nextToken();
    				String tgl_akhir_kul = st.nextToken();
    				String jml_mgu_kul = st.nextToken();
    				String metode_kul = st.nextToken();
    				String metode_kul_eks = st.nextToken();
    				stmt.setDouble(1, Double.parseDouble(target_mhs_baru));
    				stmt.setDouble(2, Double.parseDouble(calon_ikut_seleksi));
    				stmt.setDouble(3, Double.parseDouble(calon_lulus_seleksi));
    				stmt.setDouble(4, Double.parseDouble(daftar_sbg_mhs));
    				stmt.setDouble(5, Double.parseDouble(pst_undur_diri));
    				if(Checker.isStringNullOrEmpty(tgl_awal_kul)) {
    					stmt.setNull(6, java.sql.Types.DATE);
    				}
    				else {
    					stmt.setDate(6, Date.valueOf(tgl_awal_kul));
    				}
    				if(Checker.isStringNullOrEmpty(tgl_akhir_kul)) {
    					stmt.setNull(7, java.sql.Types.DATE);
    				}
    				else {
    					stmt.setDate(7, Date.valueOf(tgl_akhir_kul));
    				}
    				stmt.setDouble(8, Double.parseDouble(jml_mgu_kul));
    				if(Checker.isStringNullOrEmpty(metode_kul)) {
    					stmt.setNull(9, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(9, metode_kul);	
    				}
    				if(Checker.isStringNullOrEmpty(metode_kul_eks)) {
    					stmt.setNull(10, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(10, metode_kul_eks);	
    				}
    				//where 
    				stmt.setString(11, thsms);
    				stmt.setString(12, idsms);
    				int i = stmt.executeUpdate();
    				if(i<1) {
    					if(v_ins==null) {
    						v_ins = new Vector();
    						lin = v_ins.listIterator();
    					}
    					lin.add(brs);
    					//System.out.println("insert = "+brs);
        			
    				}
    			}	//
    			
    			if(v_ins!=null && v_ins.size()>0) {
    				lin = v_ins.listIterator();
    				stmt = con.prepareStatement("INSERT INTO daya_tampung (id_smt,id_sms,target_mhs_baru,calon_ikut_seleksi,calon_lulus_seleksi,daftar_sbg_mhs,pst_undur_diri,tgl_awal_kul,tgl_akhir_kul,jml_mgu_kul,metode_kul,metode_kul_eks)values(?,?,?,?,?,?,?,?,?,?,?,?)");		
    				while(lin.hasNext()) {
    					String brs = (String)lin.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
        				String idsms = st.nextToken();
        				thsms = st.nextToken();
        				String kdpst = st.nextToken();
        				String target_mhs_baru = st.nextToken();
        				String calon_ikut_seleksi = st.nextToken();
        				String calon_lulus_seleksi = st.nextToken();
        				String daftar_sbg_mhs = st.nextToken();
        				String pst_undur_diri = st.nextToken();
        				String tgl_awal_kul = st.nextToken();
        				String tgl_akhir_kul = st.nextToken();
        				String jml_mgu_kul = st.nextToken();
        				String metode_kul = st.nextToken();
        				String metode_kul_eks = st.nextToken();
        				
        				stmt.setString(1, thsms);
        				stmt.setString(2, idsms);
        				stmt.setDouble(3,Double.parseDouble(target_mhs_baru));
        				stmt.setDouble(4,Double.parseDouble(calon_ikut_seleksi));
        				stmt.setDouble(5,Double.parseDouble(calon_lulus_seleksi));
        				stmt.setDouble(6,Double.parseDouble(daftar_sbg_mhs));
        				stmt.setDouble(7,Double.parseDouble(pst_undur_diri));
        				if(Checker.isStringNullOrEmpty(tgl_awal_kul)) {
        					stmt.setNull(8, java.sql.Types.DATE);
        				}
        				else {
        					stmt.setDate(8, java.sql.Date.valueOf(tgl_awal_kul));
        				}
        				if(Checker.isStringNullOrEmpty(tgl_akhir_kul)) {
        					stmt.setNull(9, java.sql.Types.DATE);
        				}
        				else {
        					stmt.setDate(9, java.sql.Date.valueOf(tgl_akhir_kul));
        				}
        				stmt.setDouble(10,Double.parseDouble(jml_mgu_kul));
        				if(Checker.isStringNullOrEmpty(metode_kul)) {
        					stmt.setNull(11, java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt.setString(11, metode_kul);
        				}
        				if(Checker.isStringNullOrEmpty(metode_kul_eks)) {
        					stmt.setNull(12, java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt.setString(12, metode_kul_eks);
        				}
        				
        				int i = stmt.executeUpdate();
        				if(i<1) {
        					lio.add("GAGAL INSERT= "+brs);
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
    	return v_out;  
    	
    }
    
}

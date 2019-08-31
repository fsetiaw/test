package beans.dbase.feeder.importer;

import beans.dbase.SearchDb;
import beans.tools.Checker;

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
 * Session Bean implementation class SyncDataDosen
 */
@Stateless
@LocalBean
public class SyncDataDosen extends SearchDb {
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
    public SyncDataDosen() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SyncDataDosen(String operatorNpm) {
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
    public SyncDataDosen(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public Vector syncDataDosen(Vector v_list_dosen_at_system) {
    	//li.add(id_obj+"||"+npm+"||"+nmm);
    	int tot_updated = 0;
    	Vector v_err= new Vector();
    	ListIterator lier = v_err.listIterator();
    	if(v_list_dosen_at_system!=null && v_list_dosen_at_system.size()>0) {
    		ListIterator li = null;
        	
        	try {
        		Context initContext  = new InitialContext();    		
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc/FEEDER_IMPORTER");
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select nik,nip,niy_nigk,nuptk,nidn,nsdmi from dosen where nidn=? or nm_sdm like ?");
        		li = v_list_dosen_at_system.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"||");
        			String idobj = st.nextToken();
        			String npmhs = st.nextToken();
        			String nmmhs = st.nextToken();
        			//System.out.println(nmmhs);
        			String nidnn = st.nextToken();
        			stmt.setString(1, nidnn);
        			stmt.setString(2, nmmhs+"%");
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				String nik = rs.getString(1);
        				String nip = rs.getString(2);
        				String niy_nigk = rs.getString(3);
        				String nuptk = rs.getString(4);
        				String nidn = rs.getString(5);
        				String nsdm = rs.getString(6);
        				
        				li.set(npmhs+"`"+nik+"`"+nip+"`"+niy_nigk+"`"+nuptk+"`"+nidn+"`"+nsdm+"`"+nmmhs+"`"+nidnn);
        			}
        			else {
        				
        				li.remove();
        				lier.add(brs+" = no match");
        			}
        		}
        		
        		//UPDATE TRAKD_EPSBED
        		if(v_list_dosen_at_system!=null && v_list_dosen_at_system.size()>0) {
        			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            		con = ds.getConnection();
            		stmt = con.prepareStatement("update TRAKD_EPSBED set NODOSTRAKD=? where NODOSTRAKD=? or NMDOSTRAKD like ?");
        			li = v_list_dosen_at_system.listIterator();
        			
            		while(li.hasNext()) {
            			int i = 0;
            			String brs = (String)li.next();
            			StringTokenizer st = new StringTokenizer(brs,"`");
            			//li.set(npmhs+"`"+nik+"`"+nip+"`"+niy_nigk+"`"+nuptk+"`"+nidn+"`"+nsdm);
            			String npmhs = st.nextToken();
            			String nik = st.nextToken();
            			String nip = st.nextToken();
            			String niy_nigk = st.nextToken();
            			String nuptk = st.nextToken();
            			String nidn = st.nextToken();
            			String nsdm = st.nextToken();
            			String nmmhs = st.nextToken();
            			String nidnn = st.nextToken(); //old value at system
            			if(!nidn.equalsIgnoreCase(nidnn)) {
            				stmt.setString(++i, nidn);
                			stmt.setString(++i, nidnn);
                			stmt.setString(++i, nmmhs+"%");
                			i = stmt.executeUpdate();	
            			}
            			
            		}
        		}	
        		
        		if(v_list_dosen_at_system!=null && v_list_dosen_at_system.size()>0) {
        			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            		con = ds.getConnection();
            		stmt = con.prepareStatement("update EXT_CIVITAS_DATA_DOSEN set NIDNN=?,NOMOR_ID=?,NIK=?,NIP=?,NIY_NIGK=?,NUPTK=?,NSDMI=? where NPMHS=?");
        			li = v_list_dosen_at_system.listIterator();
        			
            		while(li.hasNext()) {
            			int i = 0;
            			String brs = (String)li.next();
            			StringTokenizer st = new StringTokenizer(brs,"`");
            			//li.set(npmhs+"`"+nik+"`"+nip+"`"+niy_nigk+"`"+nuptk+"`"+nidn+"`"+nsdm);
            			String npmhs = st.nextToken();
            			String nik = st.nextToken();
            			String nip = st.nextToken();
            			String niy_nigk = st.nextToken();
            			String nuptk = st.nextToken();
            			String nidn = st.nextToken();
            			String nsdm = st.nextToken();
            			String nmmhs = st.nextToken();
            			String nidnn = st.nextToken(); //old value at system
            			if(Checker.isStringNullOrEmpty(nidn)) {
            				stmt.setNull(++i, java.sql.Types.VARCHAR);
            			}
            			else {
            				stmt.setString(++i, nidn);
            			}
            			//nomor id ngga dipake = set null
            			stmt.setNull(++i, java.sql.Types.VARCHAR);
            			if(Checker.isStringNullOrEmpty(nik)) {
            				stmt.setNull(++i, java.sql.Types.VARCHAR);
            			}
            			else {
            				stmt.setString(++i, nik);
            			}
            			if(Checker.isStringNullOrEmpty(nip)) {
            				stmt.setNull(++i, java.sql.Types.VARCHAR);
            			}
            			else {
            				stmt.setString(++i, nip);
            			}
            			if(Checker.isStringNullOrEmpty(niy_nigk)) {
            				stmt.setNull(++i, java.sql.Types.VARCHAR);
            			}
            			else {
            				stmt.setString(++i, niy_nigk);
            			}
            			if(Checker.isStringNullOrEmpty(nuptk)) {
            				stmt.setNull(++i, java.sql.Types.VARCHAR);
            			}
            			else {
            				stmt.setString(++i, nuptk);
            			}
            			if(Checker.isStringNullOrEmpty(nsdm)) {
            				stmt.setNull(++i, java.sql.Types.VARCHAR);
            			}
            			else {
            				stmt.setString(++i, nsdm);
            			}
            			stmt.setString(++i, npmhs);
            			i = stmt.executeUpdate();
            			if(i<1) {
            				lier.add(brs+" = GAGAL DI-UPDATE");
            			}
            			else {
            				tot_updated++;
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
        	catch (Exception ex) {
        		ex.printStackTrace();
        	} 
        	finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        		if (con!=null) try { con.close();} catch (Exception ignore){}
        	} 	
    	}
    	lier.add("TOTAL DATA UPDATED = "+tot_updated);
    	return v_err;
    }

}

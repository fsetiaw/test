package beans.dbase.feeder.importer;

import beans.dbase.SearchDb;
import beans.tools.Checker;
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
 * Session Bean implementation class BobotNilaiImporter
 */
@Stateless
@LocalBean
public class BobotNilaiImporter extends SearchDb {
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
    public BobotNilaiImporter() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public BobotNilaiImporter(String operatorNpm) {
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
    public BobotNilaiImporter(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

    public Vector getDataTbbnl(String target_thsms) {
    	
    	Vector v = new Vector();
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		v = Getter.getListProdi();
    		stmt = con.prepareStatement("select * from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and ACTIVE=? order by BOBOTTBBNL desc,NLAKHTBBNL");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kdpst = st.nextToken();
    			stmt.setString(1, target_thsms);
    			stmt.setString(2, kdpst);
    			stmt.setBoolean(3, true);
    			rs = stmt.executeQuery();
    			Vector vtmp = new Vector();
    			ListIterator lit = vtmp.listIterator();
    			lit.add(kdpst); //record 1 
    			//System.out.println(kdpst);
    			while(rs.next()) {
    				String nlakh = ""+rs.getString("NLAKHTBBNL");
    				String bobot = ""+rs.getFloat("BOBOTTBBNL");
    				String nlmin = ""+rs.getFloat("NILAI_MIN");
    				String nlmax = ""+rs.getFloat("NILAI_MAX");
    				String tgsta = ""+rs.getDate("TGL_MULAI");
    				String tgend = ""+rs.getDate("TGL_AKHIR");
    				lit.add(nlakh+"`"+bobot+"`"+nlmin+"`"+nlmax+"`"+tgsta+"`"+tgend);
    				//System.out.println(nlakh+"`"+bobot+"`"+nlmin+"`"+nlmax+"`"+tgsta+"`"+tgend);
    			}
    			li.set(vtmp);
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
    
    public Vector importBobotNilai(String target_thsms) {
    	int id = 0;
    	String default_tgl_sta = "2012-07-07";
    	Vector v_out = new Vector();
    	ListIterator lio = v_out.listIterator();
    	Vector v = getDataTbbnl(target_thsms);
    	if(v!=null && v.size()>0) {
    		ListIterator li = v.listIterator();
        	try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc/FEEDER_IMPORTER");
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select id_sms from sms where kode_prodi=?");
    			while(li.hasNext()) {
    				Vector vtmp = (Vector)li.next();
    				ListIterator lit = vtmp.listIterator();
    				String kdpst = (String)lit.next();
    				if(kdpst.equalsIgnoreCase("88888")) {
    					kdpst = "86208";
    				}
    				stmt.setString(1, kdpst);
    				rs = stmt.executeQuery();
    				String id_sms = null;
    				if(rs.next()) {
    					id_sms = new String(rs.getString(1));
    					lit.set(kdpst+"`"+id_sms);
    					System.out.println(kdpst+"`"+id_sms);
    				}
    				else {
    					lio.add(kdpst+" tidak ada di tabel sms");;
    					
    				}
    			}
    			li = v.listIterator();
    			Vector vins = null;
    			ListIterator lins = null;
    			stmt = con.prepareStatement("update bobot_nilai set bobot_nilai_min=?,bobot_nilai_maks=?,nilai_indeks=?,tgl_mulai_efektif=?,tgl_akhir_efektif=? where id_sms=? and nilai_huruf=?");
    			while(li.hasNext()) {
    				Vector vtmp = (Vector)li.next();
    				ListIterator lit = vtmp.listIterator();
    				String brs = (String)lit.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String kdpst = st.nextToken();
					String id_sms = null;
					if(st.hasMoreTokens()) {
						id_sms = new String(st.nextToken());
						while(lit.hasNext()) {
	    					brs = (String)lit.next();
	    					st = new StringTokenizer(brs,"`");
	    					String nlakh = st.nextToken();
	    					String bobot = st.nextToken();
	    					String nlmin = st.nextToken();
	    					String nlmax = st.nextToken();
	    					String tgsta = st.nextToken();
	    					String tgend = st.nextToken();
	    					int i = 1;
	    					stmt.setDouble(i++,Double.parseDouble(nlmin));
	    					stmt.setDouble(i++,Double.parseDouble(nlmax));
	    					stmt.setDouble(i++,Double.parseDouble(bobot));
	    					if(!Checker.isStringNullOrEmpty(tgsta)) {
	    						stmt.setDate(i++,java.sql.Date.valueOf(tgsta));
	    					}
	    					else {	
	    						stmt.setDate(i++,java.sql.Date.valueOf(default_tgl_sta));//ngga boleh null
	    					}
	    					if(!Checker.isStringNullOrEmpty(tgend)) {
	    						stmt.setDate(i++,java.sql.Date.valueOf(tgend));
	    					}
	    					else {
	    						stmt.setNull(i++, java.sql.Types.DATE);
	    					}
	    					stmt.setString(i++, id_sms);
	    					stmt.setString(i++, nlakh);
	    					
	    					i = stmt.executeUpdate();
	    					System.out.println("update "+kdpst+"`"+brs+" = "+i);
	    					if(i<1) {
	    						if(vins==null) {
	    							vins = new Vector();
	    							lins = vins.listIterator();
	    						}
	    						lins.add(kdpst+"`"+id_sms+"`"+brs);
	    					}
	    				}	
    				}	
    			}
    			if(vins!=null) {
    				stmt = con.prepareStatement("insert into bobot_nilai(kode_bobot_nilai,id_sms,nilai_huruf,bobot_nilai_min,bobot_nilai_maks,nilai_indeks,tgl_mulai_efektif,tgl_akhir_efektif)values(?,?,?,?,?,?,?,?)");
    				lins = vins.listIterator();
    				while(lins.hasNext()) {
    					String brs = (String)lins.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String kdpst = st.nextToken();
    					String id_sms = st.nextToken();
    					String nlakh = st.nextToken();
    					String bobot = st.nextToken();
    					String nlmin = st.nextToken();
    					String nlmax = st.nextToken();
    					String tgsta = st.nextToken();
    					String tgend = st.nextToken();
    					int i = 1;
    					stmt.setString(i++, ""+id++);
    					stmt.setString(i++, id_sms);
    					stmt.setString(i++, nlakh);
    					stmt.setDouble(i++,Double.parseDouble(nlmin));
    					stmt.setDouble(i++,Double.parseDouble(nlmax));
    					stmt.setDouble(i++,Double.parseDouble(bobot));
    					if(!Checker.isStringNullOrEmpty(tgsta)) {
    						stmt.setDate(i++,java.sql.Date.valueOf(tgsta));
    					}
    					else {
    						stmt.setDate(i++,java.sql.Date.valueOf(default_tgl_sta));//ngga boleh null
    					}
    					if(!Checker.isStringNullOrEmpty(tgend)) {
    						stmt.setDate(i++,java.sql.Date.valueOf(tgend));
    					}
    					else {
    						stmt.setNull(i++, java.sql.Types.DATE);
    					}
    					i = stmt.executeUpdate();
    					System.out.println("insert "+brs+" = "+i);
    					if(i<1) {
    						lio.add("gagal insert= "+kdpst+"`"+id_sms+"`"+brs);
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

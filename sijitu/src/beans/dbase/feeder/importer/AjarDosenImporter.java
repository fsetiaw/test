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
public class AjarDosenImporter extends SearchDb {
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
    public AjarDosenImporter() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public AjarDosenImporter(String operatorNpm) {
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
    public AjarDosenImporter(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
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


    			if(!kdpst.equalsIgnoreCase("57301") && !kdpst.equalsIgnoreCase("57302")) {
    				String tmp = kdpst+"`"+kdkmk+"`"+nakmk+"`"+nodos+"`"+nmdos+"`"+kelas+"`"+sksmk;
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
    
    
    public Vector importAjarDosen(String thsms) {
    	Vector v_err = new Vector();
    	ListIterator lier= v_err.listIterator();
    	//ListIterator lio = v_out.listIterator();
    	boolean no_error = true;
    	Vector v = getDataTrakdEpsbed(thsms);
    	if(v!=null && v.size()>0) {
    		System.out.println("v_size_init="+v.size());
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
    				//filter yg udah ada nodos ajah
    				if(Checker.isStringNullOrEmpty(nodos)) {
    					li.remove();
    				}
    				else {
    					stmt.setString(1, kdpst);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						String id_sms = rs.getString(1);
    						li.set(brs+"`"+id_sms);
    					}
    				}
    			}
    			stmt = con.prepareStatement("select id_kls,kelas_kuliah.id_mk,kelas_kuliah.sks_tm,kelas_kuliah.sks_prak,kelas_kuliah.sks_prak_lap,kelas_kuliah.sks_sim from kelas_kuliah inner join mata_kuliah on kelas_kuliah.id_mk=mata_kuliah.id_mk where kelas_kuliah.id_sms=? and nm_kls=? and kode_mk=? and nm_mk like ? and kelas_kuliah.sks_mk=?");
    			//stmt = con.prepareStatement("select id_mk,sks_tm,sks_prak,sks_prak_lap,sks_sim from mata_kuliah where id_sms=? and kode_mk=? and nm_mk like ? and sks_mk=?");
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
    				String id_sms = st.nextToken();
    				stmt.setString(1, id_sms);
    				stmt.setString(2, kelas);
    				stmt.setString(3, kdkmk);
    				stmt.setString(4, Tool.buatSatuSpasiAntarKata(nakmk)+"%");
    				stmt.setInt(5, Integer.parseInt(sksmk));
    				rs = stmt.executeQuery();
    				rs.next();
    				
    				String id_kls = ""+rs.getString(1);
    				String id_mk = ""+rs.getString(2);
    				String sks_tm = ""+rs.getInt(3);
    				String sks_prak = ""+rs.getInt(4);
    				String sks_prak_lap = ""+rs.getInt(5);
    				String sks_sim = ""+rs.getInt(6);
    				li.set(brs+"`"+id_mk+"`"+id_kls+"`"+sks_tm+"`"+sks_prak+"`"+sks_prak_lap+"`"+sks_sim);
    				//
    			}
    			//get info dosen
    			li = v.listIterator();
    			stmt = con.prepareStatement("select dosen.id_sdm,id_reg_ptk from dosen inner join dosen_pt on dosen.id_sdm=dosen_pt.id_sdm where nidn=? or nip=? or niy_nigk=? or nuptk=? ");
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
    				String id_sms = st.nextToken();
    				String id_mk = st.nextToken();
    				String id_kls = st.nextToken();
    				String sks_tm = st.nextToken();
    				String sks_prak = st.nextToken();
    				String sks_prak_lap = st.nextToken();
    				String sks_sim = st.nextToken();
    				
    				String id_sdm = "null";
					String id_reg_ptk = "null";
    				stmt.setString(1, nodos);
    				stmt.setString(2, nodos);
    				stmt.setString(3, nodos);
    				stmt.setString(4, nodos);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					id_sdm = ""+rs.getString(1);
    					id_reg_ptk = ""+rs.getString(2);
    					li.set(brs+"`"+id_sdm+"`"+id_reg_ptk);
    				}
    				else {
    					
    					lier.add("["+kdkmk+"] "+nakmk+" = "+nmdos+" ["+nodos+"] tidak ditemukan di table dosen_pt");
    					//System.out.println(nmdos+" ["+nodos+"] tidak ditemukan di table dosen_pt ");
    					li.remove();
    					no_error = false;
    				}
    			}
    			//System.out.println("v_size="+v.size());
    			int tot_updated=0;
    			int tot_inserted=0;
    			if(no_error) {
    				Vector v_ins = null;
        			ListIterator lins = null;
        			li = v.listIterator();
        			stmt = con.prepareStatement("update ajar_dosen set sks_subst_tot=?,sks_tm_subst=?,sks_prak_subst=?,sks_prak_lap_subst=?,sks_sim_subst=?,jml_tm_renc=?,jml_tm_real=? where id_reg_ptk=? and id_kls=?");
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
        				String id_sms = st.nextToken();
        				String id_mk = st.nextToken();
        				String id_kls = st.nextToken();
        				String sks_tm = st.nextToken();
        				String sks_prak = st.nextToken();
        				String sks_prak_lap = st.nextToken();
        				String sks_sim = st.nextToken();
        				String id_sdm = st.nextToken();
    					String id_reg_ptk = st.nextToken();
    					stmt.setDouble(1, Double.parseDouble(sksmk));
    					stmt.setDouble(2, Double.parseDouble(sks_tm));
    					stmt.setDouble(3, Double.parseDouble(sks_prak));
    					stmt.setDouble(4, Double.parseDouble(sks_prak_lap));
    					stmt.setDouble(5, Double.parseDouble(sks_sim));
    					stmt.setDouble(6, Double.parseDouble("14"));
    					stmt.setDouble(7, Double.parseDouble("14"));
    					stmt.setString(8, id_reg_ptk);
    					stmt.setString(9, id_kls);
    					int i = stmt.executeUpdate();
    					
    					if(i<1) {
    						if(v_ins==null) {
    							v_ins = new Vector();
    							lins = v_ins.listIterator();
    						}
    						lins.add(brs);
    					}
    					else {
    						tot_updated++;
    					}
        			}	
        			//System.out.println("tot_updated="+tot_updated);
        			
        			
        			
        			li = v_ins.listIterator();
        			int noid=0;
        			stmt = con.prepareStatement("insert into ajar_dosen(id_ajar,id_subst,id_jns_eval,id_reg_ptk,id_kls,sks_subst_tot,sks_tm_subst,sks_prak_subst,sks_prak_lap_subst,sks_sim_subst,jml_tm_renc,jml_tm_real)values(?,?,?,?,?,?,?,?,?,?,?,?)");
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
        				String id_sms = st.nextToken();
        				String id_mk = st.nextToken();
        				String id_kls = st.nextToken();
        				String sks_tm = st.nextToken();
        				String sks_prak = st.nextToken();
        				String sks_prak_lap = st.nextToken();
        				String sks_sim = st.nextToken();
        				
        				String id_sdm = st.nextToken();
    					String id_reg_ptk = st.nextToken();
    					stmt.setString(1, ""+noid++);
    					stmt.setNull(2, java.sql.Types.VARCHAR);
    					stmt.setInt(3, 1);
    					stmt.setString(4, id_reg_ptk);
    					stmt.setString(5, id_kls);
    					stmt.setDouble(6, Double.parseDouble(sksmk));
    					stmt.setDouble(7, Double.parseDouble(sks_tm));
    					stmt.setDouble(8, Double.parseDouble(sks_prak));
    					stmt.setDouble(9, Double.parseDouble(sks_prak_lap));
    					stmt.setDouble(10, Double.parseDouble(sks_sim));
    					stmt.setDouble(11, Double.parseDouble("14"));
    					stmt.setDouble(12, Double.parseDouble("14"));
    					int i = stmt.executeUpdate();
    					if(i<1) {
    						lier.add(brs+" - GAGAL DIINPUT");
    					}
    					else {
    						tot_inserted++;
    					}
        			}
    			}
    				
    			lier.add("<h2>TOTAL UPDATED : "+tot_updated+"</h2>");
    			lier.add("<h2>TOTAL INSERTED : "+tot_inserted+"</h2>");
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

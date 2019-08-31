package beans.dbase.jabatan;

import beans.dbase.UpdateDb;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
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
import org.codehaus.jettison.json.JSONArray;

/**
 * Session Bean implementation class UpdateDbJabatan
 */
@Stateless
@LocalBean
public class UpdateDbJabatan extends UpdateDb {
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
     * @see UpdateDb#UpdateDb()
     */
    public UpdateDbJabatan() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbJabatan(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    
    public void updProfileJabatan(String[] prodi, String[] kmp, long objid_target, String default_kampus_objid_target) {
    	
    	
    	if(prodi!=null && kmp!=null) {
    		
    		try {
    			Context initContext  = new InitialContext();
				Context envContext  = (Context)initContext.lookup("java:/comp/env");
				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
				con = ds.getConnection();
				//delete prev record
				stmt = con.prepareStatement("delete from STRUKTURAL where OBJID=?");
				stmt.setLong(1, objid_target);
				stmt.executeUpdate();
				
				
				stmt = con.prepareStatement("insert into STRUKTURAL(KDKMP,KDPST,NM_JOB,OBJID)values(?,?,?,?)");
				for(int i=0;i<prodi.length;i++) {
					StringTokenizer st = new StringTokenizer(prodi[i],"`");
					//System.out.println("info="+prodi[i]);
					String job = st.nextToken();
					//System.out.println("job="+job);
					//gett scope kampus utuk job diatas
					Vector v_kmp = new Vector();
					for(int j=0;j<kmp.length;j++) {
						ListIterator litmp = v_kmp.listIterator();
						StringTokenizer st1 = new StringTokenizer(kmp[j],"`");
						
						String job1 = st1.nextToken();
						if(job1.equalsIgnoreCase(job)) {
							while(st1.hasMoreTokens()) {
								litmp.add(st1.nextToken());
								//System.out.println(j+".info2="+kmp[j]);
							}
						}	
					}
					
					v_kmp = Tool.removeDuplicateFromVector(v_kmp);
					if(v_kmp!=null &&v_kmp.size()>0) {
						while(st.hasMoreTokens()) {
							String kdpst = st.nextToken();
							ListIterator litmp = v_kmp.listIterator();
							while(litmp.hasNext()) {
								String kode = (String)litmp.next();
								
								stmt.setString(1, kode);
								stmt.setString(2, kdpst);
								stmt.setString(3, job.toUpperCase());
								stmt.setLong(4, objid_target);
								stmt.executeUpdate();	
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
    		catch (Exception e) {}
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}	
        }
    }	
    	
    public int resetProfileJabatan(long objid_target) {
    	
    	int upd = 0;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//delete prev record
			stmt = con.prepareStatement("delete from STRUKTURAL where OBJID=?");
			stmt.setLong(1, objid_target);
			upd = stmt.executeUpdate();
			
			
			
		} 
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
		catch (Exception e) {}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	return upd;
    		
    }
    
    
    public int tambahJabatanStruktural(String posisi_jabatan, String nm_jabatan, String norut_posisi_jabatan) {
    	
    	int upd = 0;
    	if(!Checker.isStringNullOrEmpty(nm_jabatan)) {
    		nm_jabatan = nm_jabatan.toUpperCase();
    		if(!Checker.isStringNullOrEmpty(posisi_jabatan)) {
    			nm_jabatan = posisi_jabatan+" "+nm_jabatan;
        	}
    		if(!Checker.isStringNullOrEmpty(norut_posisi_jabatan)) {
    			nm_jabatan = nm_jabatan+" "+norut_posisi_jabatan;
        	}
    		try {
        		Vector v_kdpst_kdfak_kdjen_nmpst=Getter.getListProdi();
        		Vector v_code_campus_name_campus_nickname_campus=Getter.getListAllKampus(true, Checker.getThsmsNow());
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select ID_JAB from JABATAN order by ID_JAB desc limit 1");
    			rs = stmt.executeQuery();
    			int last_id_jab=0;
    			if(rs.next()) {
    				last_id_jab = rs.getInt(1);
    			}
    			stmt = con.prepareStatement("INSERT INTO JABATAN(NAMA_JABATAN,AKTIF,TIPE_GROUP,ID_JAB)values(?,?,?,?)");
    			stmt.setString(1, nm_jabatan);
    			stmt.setBoolean(2, true);
    			if(nm_jabatan.startsWith("STAFF")) {
					stmt.setBoolean(3, true);
				}
				else {
					stmt.setBoolean(3, false);
				}
    			stmt.setInt(4, last_id_jab+1);
    			stmt.executeUpdate();
    			stmt = con.prepareStatement("INSERT INTO STRUKTURAL(KDKMP,KDPST,NM_JOB,AKTIF,ONE_PERSON_JOB,ALIAS_NM_JOB)values(?,?,?,?,?,?)");
    			ListIterator li_kmp = v_code_campus_name_campus_nickname_campus.listIterator();
    			while(li_kmp.hasNext()) {
    				String brs = (String)li_kmp.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdkmp = st.nextToken();
    				String nmkmp = st.nextToken();
    				String nickkmp = st.nextToken();
    				ListIterator li_kdpst = v_kdpst_kdfak_kdjen_nmpst.listIterator();
    				while(li_kdpst.hasNext()) {
    					brs = (String)li_kdpst.next();
    					st = new StringTokenizer(brs,"`");
    					String kdpst = st.nextToken();
    					String kdfak = st.nextToken();
    					String kdjen = st.nextToken();
    					String nmpst = st.nextToken();
    					stmt.setString(1, kdkmp);
    					stmt.setString(2, kdpst);
    					stmt.setString(3, nm_jabatan.trim().toUpperCase());
    					stmt.setBoolean(4, true);
    					if(nm_jabatan.startsWith("STAFF")) {
    						stmt.setBoolean(5, false);
    					}
    					else {
    						stmt.setBoolean(5, true);
    					}
    					stmt.setString(6, nm_jabatan.trim().toUpperCase());
    					upd = upd+stmt.executeUpdate();
    				}
    			}	
    		} 
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
    		catch (Exception e) {}
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}	
    	}
    	
    	return upd;
    		
    }
	
    public int editNamaJabatan(String[]nama_original_jabatan,String[]posisi_jabatan,String[]jabatan,String[]urutan_posisi_jabatan) {
    	
    	int upd = 0;
    	if(nama_original_jabatan!=null && nama_original_jabatan.length>0) {
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			//update tabel jabatan dulu
    			//stmt = con.prepareStatement("update STRUKTURAL set NM_JOB=? where NM_JOB=?");
    			stmt = con.prepareStatement("update JABATAN set NAMA_JABATAN=? where NAMA_JABATAN=?");
    			String nm_jabatan_baru=null;
    			for(int i=0;i<posisi_jabatan.length;i++) {
    				nm_jabatan_baru=null;
    				if(!Checker.isStringNullOrEmpty(posisi_jabatan[i])) {
    					nm_jabatan_baru = new String(posisi_jabatan[i].toUpperCase().trim());
    				}
    				if(Checker.isStringNullOrEmpty(nm_jabatan_baru)) {
    					nm_jabatan_baru = new String(jabatan[i].trim());
    				}
    				else {
    					nm_jabatan_baru = nm_jabatan_baru+" "+jabatan[i].trim();
    				}
    				if(!Checker.isStringNullOrEmpty(urutan_posisi_jabatan[i])) {
    					nm_jabatan_baru = nm_jabatan_baru+" "+urutan_posisi_jabatan[i].trim();
    				}
    				if(!Checker.isStringNullOrEmpty(nm_jabatan_baru)) {
    					stmt.setString(1, nm_jabatan_baru);
        				stmt.setString(2, nama_original_jabatan[i]);
        				upd = upd + stmt.executeUpdate();
        				//System.out.println("0nm_jabatan_baru="+nm_jabatan_baru);
        				//System.out.println("0nama_original_jabatan[i]="+nama_original_jabatan[i]);
        				//System.out.println("0upd="+upd);	
    				}
    			}
    			
    			stmt = con.prepareStatement("update STRUKTURAL set NM_JOB=?,ALIAS_NM_JOB=? where NM_JOB=?");
    			for(int i=0;i<posisi_jabatan.length;i++) {
    				nm_jabatan_baru=null;
    				if(!Checker.isStringNullOrEmpty(posisi_jabatan[i])) {
    					nm_jabatan_baru = new String(posisi_jabatan[i].toUpperCase().trim());
    				}
    				if(Checker.isStringNullOrEmpty(nm_jabatan_baru)) {
    					nm_jabatan_baru = new String(jabatan[i].trim());
    				}
    				else {
    					nm_jabatan_baru = nm_jabatan_baru+" "+jabatan[i].trim();
    				}
    				if(!Checker.isStringNullOrEmpty(urutan_posisi_jabatan[i])) {
    					nm_jabatan_baru = nm_jabatan_baru+" "+urutan_posisi_jabatan[i].trim();
    				}
    				if(!Checker.isStringNullOrEmpty(nm_jabatan_baru)) {
    					stmt.setString(1, nm_jabatan_baru);
    					stmt.setString(2, nm_jabatan_baru);
    					stmt.setString(3, nama_original_jabatan[i]);
        				upd = upd + stmt.executeUpdate();
        				//System.out.println("1nm_jabatan_baru="+nm_jabatan_baru);
        				//System.out.println("1nama_original_jabatan[i]="+nama_original_jabatan[i]);
        				//System.out.println("1upd="+upd);	
    				}
    				
    				
    			}
    		} 
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
    		catch (Exception e) {}
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}
    	}
    		
    	return upd;
    		
    }
    
}	    



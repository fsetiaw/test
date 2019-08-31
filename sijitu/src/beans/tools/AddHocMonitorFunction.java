package beans.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;
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
 * Session Bean implementation class AddHocFunction
 */
@Stateless
@LocalBean
public class AddHocMonitorFunction {

    /**
     * Default constructor. 
     */
    public AddHocMonitorFunction() {
        // TODO Auto-generated constructor stub
    }
    
    public static Vector cekDataYgAdaDiExtCivitasTpTidakAdaDiCivitas() {
    	Vector v = null;
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//1. set npm asal at civitas
			stmt = con.prepareStatement("SELECT * FROM CIVITAS right join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS where CIVITAS.NPMHSMSMHS is null");
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				ListIterator li = v.listIterator();
				do {
					String kdpst = rs.getString("EXT_CIVITAS.KDPSTMSMHS");
					String npmhs = rs.getString("EXT_CIVITAS.NPMHSMSMHS");
					String updtm = ""+rs.getTimestamp("EXT_CIVITAS.UPDTMMSMHS");
					li.add(kdpst+"`"+npmhs+"`"+updtm);
				}
				while(rs.next());
			}
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return v;
    }
    
    public static void deleteDataOrphanageExtCivitas() {
    	Vector v = null;
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//1. set npm asal at civitas
			stmt = con.prepareStatement("SELECT * FROM CIVITAS right join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS where CIVITAS.NPMHSMSMHS is null");
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				ListIterator li = v.listIterator();
				do {
					String kdpst = rs.getString("EXT_CIVITAS.KDPSTMSMHS");
					String npmhs = rs.getString("EXT_CIVITAS.NPMHSMSMHS");
					
					li.add(kdpst+"`"+npmhs);
				}
				while(rs.next());
			}
			if(v!=null && v.size()>0) {
				
				stmt = con.prepareStatement("DELETE from EXT_CIVITAS where KDPSTMSMHS=? and NPMHSMSMHS=?");
				ListIterator li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String kdpst = st.nextToken();
					String npmhs = st.nextToken();
					stmt.setString(1, kdpst);
					stmt.setString(2, npmhs);
					stmt.executeUpdate();
				}
			}
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	
    }
    
    public static int getKonflikOnePersonJobErrorInfo() {
    	Vector v = null;
    	ListIterator li = null;
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	int upd=0;
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//1. set npm asal at civitas
			stmt = con.prepareStatement("SELECT distinct NM_JOB,KDPST FROM STRUKTURAL where ONE_PERSON_JOB=?");
			stmt.setBoolean(1, true);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					String nm_job = rs.getString(1);
					String kdpst = rs.getString(2);
					li.add(nm_job+"~"+kdpst);
				}
				while(rs.next());
			}
			//cek jabatan tunggal yg kelebihan ato belum di assign
			if(v!=null) {
				String sql = "SELECT count(distinct OBJID) FROM STRUKTURAL where ONE_PERSON_JOB=true and KDPST=? and NM_JOB=?";
				stmt = con.prepareStatement(sql);
				li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"~");
					String job = st.nextToken();
					String kdpst = st.nextToken();
					stmt.setString(1, kdpst);
					stmt.setString(2, job);
					rs = stmt.executeQuery();
					int total=0;
					if(rs.next()) {
						total = rs.getInt(1);
					}
					if(total!=1) {
						li.set(brs+"~"+total);
					}
					else {
						li.remove();
					}
				}
			}
			//get idobj yg kelebihan assign
			if(v!=null) {
				String sql = "SELECT distinct OBJID,OBJ_DESC FROM STRUKTURAL A inner join OBJECT B on OBJID=ID_OBJ where ONE_PERSON_JOB=true and A.KDPST=? and NM_JOB=?";
				stmt = con.prepareStatement(sql);
				li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"~");
					String job = st.nextToken();
					String kdpst = st.nextToken();
					int total = Integer.parseInt(st.nextToken());
					if(total>1) {
						stmt.setString(1, kdpst);
						stmt.setString(2, job);
						rs = stmt.executeQuery();
						String tmp = "";
						if(rs.next()) {
							tmp = rs.getString(2);
							while(rs.next()) {
								tmp = tmp+"`"+rs.getString(2);
							}
						}
						li.set(brs+"~"+tmp);
					}
				}
				//delete TABEL_ERROR_INFO prev value untuk table STUKTURAL
				stmt = con.prepareStatement("delete from TABEL_ERROR_INFO where TABLE_NAME=?");
				stmt.setString(1, "STRUKTURAL");
				stmt.executeUpdate();
				
				stmt = con.prepareStatement("INSERT IGNORE INTO TABEL_ERROR_INFO(TABLE_NAME,ERROR_INFO,KDPST)values(?,?,?)");
				String err_info="";
				li = v.listIterator();
				while(li.hasNext()) {
					int i=1;
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"~");
					String job = st.nextToken();
					String kdpst = st.nextToken();
					int total = Integer.parseInt(st.nextToken());
					if(total>1) {
						err_info = "<b>"+job.toUpperCase()+" untuk Prodi "+Converter.getNamaKdpst(kdpst)+" dijabat lebih dari 1 orang.</b><br>";
						String tkn_objnm = st.nextToken();
						while(tkn_objnm.contains("`")) {
							i++;
							tkn_objnm=tkn_objnm.replace("`", "<br>"+i+".&nbsp");
						}
						err_info = err_info+"1.&nbsp"+tkn_objnm;
						//li.set(brs+"~"+tmp);
					}
					else {
						err_info = ""+job.toUpperCase()+" untuk Prodi "+Converter.getNamaKdpst(kdpst)+" belum ditentukan";
					}
					stmt.setString(1, "STRUKTURAL");
					stmt.setString(2, err_info);
					stmt.setString(3, kdpst);
					upd=stmt.executeUpdate();
				}
			}
			else {
				//no err
				//delete TABEL_ERROR_INFO prev value untuk table STUKTURAL
				stmt = con.prepareStatement("delete from TABEL_ERROR_INFO where TABLE_NAME=?");
				stmt.setString(1, "STRUKTURAL");
				stmt.executeUpdate();
			}
			
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return upd;
    }
    
 }

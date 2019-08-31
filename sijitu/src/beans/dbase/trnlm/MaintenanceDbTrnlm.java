package beans.dbase.trnlm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

import beans.tools.Tool;

/**
 * Session Bean implementation class MaintenanceDbTrnlm
 */
@Stateless
@LocalBean
public class MaintenanceDbTrnlm extends UpdateDbTrnlm {
	String operatorNpm;
	String operatorNmm;
	String tknOperatorNickname;
	boolean petugas;
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;
	Random randomNumberGenerator;  
    /**
     * @see UpdateDbTrnlm#UpdateDbTrnlm()
     */
    public MaintenanceDbTrnlm() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDbTrnlm#UpdateDbTrnlm(String)
     */
    public MaintenanceDbTrnlm(String operatorNpm) {
        super(operatorNpm);
        randomNumberGenerator = new Random();
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    

    
    public String maintenanceTrnlmTrakmMhsYgSdhOut(int limit, int offset) {
    	
    	int updated = 0;
    	int tot_data = 0;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select THSMS,NPMHS,STMHS from TRLSM where STMHS='D' or STMHS='L' or STMHS='K' order by THSMS,STMHS,NPMHS limit ?,?");
    		stmt.setInt(1, offset);
    		stmt.setInt(2, limit);
    		rs = stmt.executeQuery();
    		Vector v_grad = null;
    		Vector v_kick = null;
    		if(rs.next()) {
    			v_grad = new Vector();
    			v_kick = new Vector();
    			ListIterator lig = v_grad.listIterator();
    			ListIterator lik = v_kick.listIterator();
    			do {
    				String thsms = ""+rs.getString(1);
    				String npmhs = ""+rs.getString(2);
    				String stmhs = ""+rs.getString(3);
    				if(stmhs.equalsIgnoreCase("L")) {
    					lig.add(thsms+"`"+npmhs+"`"+stmhs);
    				}
    				else {
    					lik.add(thsms+"`"+npmhs+"`"+stmhs);	
    				}
    				
    			}
    			while(rs.next());
    			tot_data = v_grad.size()+v_kick.size();
    		}
    		if(v_grad!=null && v_grad.size()>0) {
    			stmt = con.prepareStatement("delete from TRLSM where NPMHS=? and THSMS>?");
    			ListIterator li = v_grad.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String thsms = st.nextToken();
    				String npmhs = st.nextToken();
    				String stmhs = st.nextToken();
    				stmt.setString(1, npmhs);
    				stmt.setString(2, thsms);
    				updated = updated + stmt.executeUpdate();
    			}
    			stmt = con.prepareStatement("delete from TRNLM where NPMHSTRNLM=? and THSMSTRNLM>?");
    			li = v_grad.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String thsms = st.nextToken();
    				String npmhs = st.nextToken();
    				String stmhs = st.nextToken();
    				stmt.setString(1, npmhs);
    				stmt.setString(2, thsms);
    				updated = updated + stmt.executeUpdate();
    			}
    			stmt = con.prepareStatement("delete from TRAKM where NPMHSTRAKM=? and THSMSTRAKM>?");
    			li = v_grad.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String thsms = st.nextToken();
    				String npmhs = st.nextToken();
    				String stmhs = st.nextToken();
    				stmt.setString(1, npmhs);
    				stmt.setString(2, thsms);
    				updated = updated + stmt.executeUpdate();
    			}
    		}
    		if(v_kick!=null && v_kick.size()>0) {
    			stmt = con.prepareStatement("delete from TRLSM where NPMHS=? and THSMS>?");
    			ListIterator li = v_kick.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String thsms = st.nextToken();
    				String npmhs = st.nextToken();
    				String stmhs = st.nextToken();
    				stmt.setString(1, npmhs);
    				stmt.setString(2, thsms);
    				updated = updated + stmt.executeUpdate();
    			}
    			stmt = con.prepareStatement("delete from TRNLM where NPMHSTRNLM=? and THSMSTRNLM>=?");
    			li = v_kick.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String thsms = st.nextToken();
    				String npmhs = st.nextToken();
    				String stmhs = st.nextToken();
    				stmt.setString(1, npmhs);
    				stmt.setString(2, thsms);
    				updated = updated + stmt.executeUpdate();
    			}
    			stmt = con.prepareStatement("delete from TRAKM where NPMHSTRAKM=? and THSMSTRAKM>=?");
    			li = v_kick.listIterator();
    			while(li.hasNext()) { 
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String thsms = st.nextToken();
    				String npmhs = st.nextToken();
    				String stmhs = st.nextToken();
    				stmt.setString(1, npmhs);
    				stmt.setString(2, thsms); 
    				updated = updated + stmt.executeUpdate();
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
    	return tot_data+"`"+updated;
    	
    }
    
    public Vector cekErrorMakulYgTidakTerdafarPadaProdi(String thsms, boolean makul_aktif_only) {
    	Vector v= null;
    	int updated = 0;
    	int tot_data = 0;
    	ListIterator li = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select distinct KDPSTTRNLM,KDKMKTRNLM from TRNLM where THSMSTRNLM=?");
    		
    		stmt.setString(1, thsms);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			String kdpst = rs.getString(1);
    			String kdkmk = rs.getString(2);
    			li.add(kdpst+"`"+kdkmk);
    			while(rs.next()) {
    				kdpst = rs.getString(1);
        			kdkmk = rs.getString(2);
        			li.add(kdpst+"`"+kdkmk);
    			}
    		}
    		if(v!=null) {
    			if(makul_aktif_only) {
    				stmt = con.prepareStatement("select IDKMKMAKUL from MAKUL where KDPSTMAKUL=? and KDKMKMAKUL=? and STKMKMAKUL='A' limit 1");
        		}
        		else {
        			stmt = con.prepareStatement("select IDKMKMAKUL from MAKUL where KDPSTMAKUL=? and KDKMKMAKUL=? limit 1");
        		}
        		
    			
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdpst = st.nextToken();
        			String kdkmk = st.nextToken();
        			//System.out.print(brs+"=");
        			stmt.setString(1, kdpst);
        			stmt.setString(2, kdkmk);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				li.remove();
        				//System.out.println("ada");
        			}
        			else {
        				//System.out.println("NOPE");
        				li.set("MATA KULIAH TIDAK ADA DI PRODINYA`"+kdpst+","+kdkmk);
        			}
    			}
    		}
    		if(v!=null) {
        		v = beans.tools.Tool.removeDuplicateFromVector(v);
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
    
    public Vector cekErrorKonflikAntaraKrsDgnStmhs(String thsms) {
    	Vector v= null;
    	String prev_thsms = Tool.returnPrevThsmsGivenTpAntara(thsms);
    	String thsms_2 = Tool.returnPrevThsmsGivenTpAntara(prev_thsms);
    	String thsms_3 = Tool.returnPrevThsmsGivenTpAntara(thsms_2);
    	ListIterator li = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		//scope target mahasiswwa adalah mhs yg ada krs pada target thsms only
    		stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=?");
    		stmt.setString(1, thsms);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			li.add(rs.getString(1));
    			while(rs.next()) {
    				li.add(rs.getString(1));
    			}
    		}
    		//System.out.println("sisni");
    		if(v!=null) {
    			//System.out.println(v.size());
    			li = v.listIterator();
    			stmt = con.prepareStatement("select THSMS,STMHS from TRLSM where THSMS>=? and THSMS<=? and NPMHS=? and (STMHS='D' or STMHS='K' or STMHS='L') limit 1");
    			while(li.hasNext()) {
    				String npmhs = (String)li.next();
    				stmt.setString(1, thsms_3);
    				stmt.setString(2, thsms);
        			stmt.setString(3, npmhs);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				//System.out.println("match");
        				String thsms_lsm = rs.getString(1);
        				String stmhs = rs.getString(2);
        				if(stmhs.equalsIgnoreCase("L") && thsms_lsm.equalsIgnoreCase(thsms)) {
        					//ignore krn thsms lulus boleh ada krsnya
        				}
        				else {
        					String status = "";
        					if(stmhs.equalsIgnoreCase("K")) {
        						status = "'Keluar'";
        					}
        					else if(stmhs.equalsIgnoreCase("D")) {
        						status = "'D.O.'";
        					}
        					else if(stmhs.equalsIgnoreCase("L")) {
        						status = "'Lulus'";
        					}
        					li.set(npmhs+"`Mahasiswa memiliki krs setelah statusnya "+status+" pada "+thsms_lsm);	
        				}
        				
        			}
        			else {
        				li.remove();
        			}
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
    	
    	return v;
    	
    }
}

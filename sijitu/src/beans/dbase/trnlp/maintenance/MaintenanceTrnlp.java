package beans.dbase.trnlp.maintenance;

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
 * Session Bean implementation class UpdateSksmkTrnlp
 */
@Stateless
@LocalBean
public class MaintenanceTrnlp {
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
     * Default constructor. 
     */
    public MaintenanceTrnlp() {
        // TODO Auto-generated constructor stub
    }
    
    public int fixSksmkTrnlp(String smawl) {
    	//smawl hanya digunakan untuk update sksdi
    	int updated = 0;
    	Vector v =new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT NPMHSTRNLP,KDKMKTRNLP,KRKLMMSMHS FROM TRNLP inner join EXT_CIVITAS on NPMHSTRNLP=NPMHSMSMHS where SKSMKTRNLP is null and KDKMKTRNLP is not null");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String npmhs = rs.getString(1);
    			String kdkmk = rs.getString(2);
    			String krklm = ""+rs.getInt(3);
    			li.add(npmhs+"`"+kdkmk+"`"+krklm);
    		}
    		stmt = con.prepareStatement("select (sum(SKSTMMAKUL)+sum(SKSPRMAKUL)+sum(SKSLPMAKUL)+sum(SKSLBMAKUL)+sum(SKSIMMAKUL)) from MAKUL inner join MAKUR on IDKMKMAKUL=IDKMKMAKUR where KDKMKMAKUL=? and IDKURMAKUR=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String npmhs=st.nextToken();
    			String kdkmk=st.nextToken();
    			String krklm=st.nextToken();
    			stmt.setString(1, kdkmk);
    			stmt.setInt(2, Integer.parseInt(krklm));
    			rs = stmt.executeQuery();
    			int sksmk = 0;
    			if(rs.next()) {
    				sksmk = rs.getInt(1);
    			}
    			li.set(brs+"`"+sksmk);
    		}
    		
    		//update sksmk
    		stmt = con.prepareStatement("update TRNLP set SKSMKTRNLP=? where NPMHSTRNLP=? and KDKMKTRNLP=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String npmhs=st.nextToken();
    			String kdkmk=st.nextToken();
    			String krklm=st.nextToken();
    			String sksmk=st.nextToken();
    			stmt.setInt(1, Integer.parseInt(sksmk));
    			stmt.setString(2, npmhs);
    			stmt.setString(3, kdkmk);
    			stmt.executeUpdate();
    			//System.out.println(brs);
    		}
    		
    		if(smawl!=null) {
    			v = new Vector();
    			li = v.listIterator();
    			stmt=con.prepareStatement("select NPMHSMSMHS from CIVITAS where STPIDMSMHS=? and SMAWLMSMHS=?");
    			stmt.setString(1, "P");
    			stmt.setString(2, smawl);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String npmhs = rs.getString(1);
    				li.add(npmhs);
    			}
    			stmt = con.prepareStatement("select sum(SKSMKTRNLP) from TRNLP where NPMHSTRNLP=? and TRANSFERRED=?");
    			li= v.listIterator();
    			while(li.hasNext()) {
    				String npmhs = (String)li.next();
    				stmt.setString(1, npmhs);
    				stmt.setBoolean(2, true);
    				rs = stmt.executeQuery();
    				int sksdi = 0;
    				if(rs.next()) {
    					sksdi = rs.getInt(1);
    				}
    				li.set(npmhs+"`"+sksdi);
    				
    			}
    			stmt = con.prepareStatement("update CIVITAS set SKSDIMSMHS=? where NPMHSMSMHS=?");
    			li= v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String npmhs = st.nextToken();
    				String sksdi = st.nextToken();
    				stmt.setInt(1, Integer.parseInt(sksdi));
    				stmt.setString(2, npmhs);
    				updated = updated+stmt.executeUpdate();
    				//System.out.println("updated "+brs+"="+updated);
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
    	return updated;
    }
    
    public int fixSksmkTrnlp(String kdpst, String npmhs) {
    	//VERSI untu perorangan
    	int updated = 0;
    	Vector v =new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT NPMHSTRNLP,KDKMKTRNLP,KRKLMMSMHS FROM TRNLP inner join EXT_CIVITAS on NPMHSTRNLP=NPMHSMSMHS where NPMHSTRNLP=? and SKSMKTRNLP is null and KDKMKTRNLP is not null");
    		stmt.setString(1, npmhs);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			npmhs = rs.getString(1);
    			String kdkmk = rs.getString(2);
    			String krklm = ""+rs.getInt(3);
    			li.add(npmhs+"`"+kdkmk+"`"+krklm);
    		}
    		stmt = con.prepareStatement("select (sum(SKSTMMAKUL)+sum(SKSPRMAKUL)+sum(SKSLPMAKUL)+sum(SKSLBMAKUL)+sum(SKSIMMAKUL)) from MAKUL inner join MAKUR on IDKMKMAKUL=IDKMKMAKUR where KDKMKMAKUL=? and IDKURMAKUR=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			npmhs=st.nextToken();
    			String kdkmk=st.nextToken();
    			String krklm=st.nextToken();
    			stmt.setString(1, kdkmk);
    			stmt.setInt(2, Integer.parseInt(krklm));
    			rs = stmt.executeQuery();
    			int sksmk = 0;
    			if(rs.next()) {
    				sksmk = rs.getInt(1);
    			}
    			li.set(brs+"`"+sksmk);
    		}
    		
    		//update sksmk
    		stmt = con.prepareStatement("update TRNLP set SKSMKTRNLP=? where NPMHSTRNLP=? and KDKMKTRNLP=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			npmhs=st.nextToken();
    			String kdkmk=st.nextToken();
    			String krklm=st.nextToken();
    			String sksmk=st.nextToken();
    			stmt.setInt(1, Integer.parseInt(sksmk));
    			stmt.setString(2, npmhs);
    			stmt.setString(3, kdkmk);
    			stmt.executeUpdate();
    			//System.out.println(brs);
    		}
    		
    		//fix sksdi
    		v = new Vector();
    		li = v.listIterator();
    		li.add(npmhs);
    		
    		stmt = con.prepareStatement("select sum(SKSMKTRNLP) from TRNLP where NPMHSTRNLP=? and TRANSFERRED=?");
    		li= v.listIterator();
    		while(li.hasNext()) {
    			npmhs = (String)li.next();
    			stmt.setString(1, npmhs);
    			stmt.setBoolean(2, true);
    			rs = stmt.executeQuery();
    			int sksdi = 0;
    			if(rs.next()) {
    				sksdi = rs.getInt(1);
    			}
    			li.set(npmhs+"`"+sksdi);
    				
    		}
    		stmt = con.prepareStatement("update CIVITAS set SKSDIMSMHS=? where NPMHSMSMHS=?");
    		li= v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			npmhs = st.nextToken();
    			String sksdi = st.nextToken();
    			stmt.setInt(1, Integer.parseInt(sksdi));
    			stmt.setString(2, npmhs);
    			updated = updated+stmt.executeUpdate();
    			//System.out.println("updated "+brs+"="+updated);
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
    	return updated;
    }

}

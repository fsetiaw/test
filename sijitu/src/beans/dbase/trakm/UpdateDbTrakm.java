package beans.dbase.trakm;

import beans.dbase.UpdateDb;
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
 * Session Bean implementation class UpdateDbTrakm
 */
@Stateless
@LocalBean
public class UpdateDbTrakm extends UpdateDb {
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
    public UpdateDbTrakm() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbTrakm(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    
    public int updTrakmSksttRobot(Vector v_npmhs_skstt, String target_thsms) {
    	int updated = 0;
    	StringTokenizer st = null;
    	try {
    		if(v_npmhs_skstt!=null && v_npmhs_skstt.size()>0) {
    			ListIterator li = v_npmhs_skstt.listIterator();
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("update TRAKM set SKSTTROBOT=? where THSMSTRAKM=? and NPMHSTRAKM=?");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			String npmhs = st.nextToken();
        			String skstt = st.nextToken();
        			stmt.setInt(1, Integer.parseInt(skstt));
        			stmt.setString(2, target_thsms);
        			stmt.setString(3, npmhs);
        			updated = updated + stmt.executeUpdate();
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
    	return updated;
    }
    
    public int setSksttBerdasarExcelWisudawan(String target_thsms) {
    	int updated = 0;
    	StringTokenizer st = null;
    	try {
    		if(!Checker.isStringNullOrEmpty(target_thsms)) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select SKSTT,NPMHS from TRLSM where THSMS=? and STMHS='L'");
        		stmt.setString(1, target_thsms);
        		rs = stmt.executeQuery();
        		Vector v = null;
        		if(rs.next()) {
        			v = new Vector();
        			ListIterator li = v.listIterator();
        			do {
        				String skstt = rs.getString(1);
        				String npmhs = rs.getString(2);
        				li.add(npmhs+"`"+skstt);
        			}
        			while(rs.next());
        			if(v!=null) {
        				li = v.listIterator();
        				stmt = con.prepareStatement("update TRAKM set SKSTTTRAKM=? where THSMSTRAKM=? and NPMHSTRAKM=?");
        				while(li.hasNext()) {
        					String brs = (String)li.next();
        					st = new StringTokenizer(brs,"`");
        					String npmhs = st.nextToken();
        					String skstt = st.nextToken();
        					try {
        						int tot_sks = Integer.parseInt(skstt);
        						if(tot_sks>0) {
        							stmt.setInt(1, tot_sks);
        							stmt.setString(2, target_thsms);
        							stmt.setString(3, npmhs);
        							updated = updated + stmt.executeUpdate();
        						}
        					}
        					catch(Exception e){}
        					
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
		catch (Exception ex) {
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

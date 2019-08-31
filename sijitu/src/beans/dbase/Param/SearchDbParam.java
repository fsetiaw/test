package beans.dbase.Param;

import beans.dbase.SearchDb;
import beans.tools.Checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Vector;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.owasp.esapi.ESAPI;

import java.util.StringTokenizer;
/**
 * Session Bean implementation class SearchDbParam
 */
@Stateless
@LocalBean
public class SearchDbParam extends SearchDb {
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
    public SearchDbParam() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbParam(String operatorNpm) {
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
    public SearchDbParam(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

   /*
    public Vector getObjNicknameFromScopeUpd7des2012(Vector vInfoObj) {
    	
    	if(vInfoObj!=null && vInfoObj.size()>0) {
    		ListIterator li = vInfoObj.listIterator();
    		try {
    			
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select * from OBJECT where ID_OBJ=?");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs);
        			String idObj = st.nextToken();
    				String kdpst = st.nextToken();
    				String ketObj = st.nextToken();
    				String objLvl = st.nextToken();
    				String kdjen = st.nextToken();
    				stmt.setLong(1,Long.valueOf(idObj).longValue());
    				rs = stmt.executeQuery();
    				rs.next();
    				String nickname = ""+rs.getString("OBJ_NICKNAME");
    				if(Checker.isStringNullOrEmpty(nickname)) {
    					nickname="null";
    				}
    				li.set(idObj+"$"+kdpst+"$"+ketObj+"$"+objLvl+"$"+kdjen+"$"+nickname);
    				//System.out.println(idObj+"$"+kdpst+"$"+ketObj+"$"+objLvl+"$"+kdjen+"$"+nickname);
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
    	return vInfoObj;
    }
	*/
    public Vector getCurrentRule(String target_thsms, String full_table_rules_name) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from "+full_table_rules_name+" where THSMS=?");
			stmt.setString(1, target_thsms);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
	    		li = v.listIterator();
	    		do {
	    			String kdpst = ""+rs.getString("KDPST");
					String tkn_ver = ""+rs.getString("TKN_JABATAN_VERIFICATOR");
					String tkn_ver_id = ""+rs.getString("TKN_VERIFICATOR_ID");
					String urutan = ""+rs.getBoolean("URUTAN");
					String kd_kmp = ""+rs.getString("KODE_KAMPUS");
					li.add(kdpst+"`"+tkn_ver+"`"+tkn_ver_id+"`"+urutan+"`"+kd_kmp);
				
	    		} while(rs.next());	
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
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	return v;
    }
    
    public Vector getInfoStruktural() {
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from STRUKTURAL where AKTIF=? order by NM_JOB,KDPST,KDKMP");
			stmt.setBoolean(1, true);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
	    		li = v.listIterator();
	    		do {
	    			String id = ""+rs.getLong("ID");
					String kdkmp = ""+rs.getString("KDKMP");
					String kdpst = ""+rs.getString("KDPST");
					String nm_job = ""+rs.getString("NM_JOB");
					String objid = ""+rs.getLong("OBJID");
					String tgsta = ""+rs.getDate("TANGGAL_MULAI");
					String tgend = ""+rs.getDate("TANGGAL_END");
					li.add(id+"`"+kdkmp+"`"+kdpst+"`"+nm_job+"`"+objid+"`"+tgsta+"`"+tgend);
				
	    		} while(rs.next());	
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
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	return v;
    }
    
}

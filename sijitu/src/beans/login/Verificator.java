package beans.login;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import beans.login.InitSessionUsr;
import beans.tools.Checker;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class Verificator
 */
@Stateless
@LocalBean
public class Verificator {

	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds; 

    /**
     * Default constructor. 
     */
    public Verificator() {
        // TODO Auto-generated constructor stub
    }

    public Verificator(Connection con) {
        // TODO Auto-generated constructor stub
    	this.con = con;
    }
    
    public String verifiedIsServerUnderMaintenanceAndIsUsrAllow(String usr, String pwd) {
    	String status="maintenance";
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
    		stmt = con.prepareStatement("select UNDER_MAINTENANCE,NPM_ALLOW_UNDER_MAINTENANCE from CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		rs.next();
    		boolean under_maintenance = rs.getBoolean("UNDER_MAINTENANCE");
    		String npm_allow = ""+rs.getString("NPM_ALLOW_UNDER_MAINTENANCE");
    		if(under_maintenance) {
    			if(!Checker.isStringNullOrEmpty(npm_allow)) {
    				stmt = con.prepareStatement("select NPMHSMSMHS from CIVITAS inner join USR_DAT on CIVITAS.ID=USR_DAT.ID where USR_NAME=? and USR_PWD=?");
    				stmt.setString(1, usr);
    				stmt.setString(2, pwd);
    				rs = stmt.executeQuery();
    				String npmhs = null;
    				if(rs.next()) {
    					npmhs = ""+rs.getString("NPMHSMSMHS");
    				}
    				if(npm_allow.contains(npmhs)) {
    					status = "lanjut";
    				}
    			}	
    		}
    		else {
    			status = "lanjut";
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
    	return status;
    }    
    
    public String verifiedUsrPwd(String usr, String pwd) {
    	String info=null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			//con = ds.getConnection();
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from USR_DAT where USR_NAME=? and USR_PWD=?");
    		stmt.setString(1,usr);
    		stmt.setString(2,pwd);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			int id = rs.getInt("ID");
    			String schema = rs.getString("SCHEMA_OWNER");
    			String redirect = rs.getString("FORCE_REDIRECT");
    			info = id+" "+schema+" "+redirect;
    		}
    		//System.out.println("info="+info);
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
        //finally {
        //	if (con!=null) {
        //		try {
        //			con.close();
        //		}
        //		catch (Exception ignore) {
         //   		//System.out.println(ignore);
        //		}
        //	}
        //}
    	return info;
    }
/*    
    public InitSessionUsr connectToSchema(String schema, String id) {
    	String info=null;
    	InitSessionUsr isu=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc/"+schema);
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from CIVITAS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where ID=?");
    		stmt.setInt(1,Integer.valueOf(id).intValue());
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			int id_obj = rs.getInt("ID_OBJ");
    			int obj_lvl = rs.getInt("OBJ_LEVEL");
    			String obj_nm = rs.getString("OBJ_NAME");
    			String fullname = rs.getString("NMMHSMSMHS");
    			String npm = rs.getString("NPMHSMSMHS");
    			String access_level_conditional=rs.getString("ACCESS_LEVEL_CONDITIONAL");
    			String access_level=rs.getString("ACCESS_LEVEL");
    			isu = new InitSessionUsr(fullname,npm,schema,obj_lvl,access_level_conditional,access_level,id_obj);
    		    //System.out.println("onject "+obj_nm);
    		}
        } 
        catch (NamingException e) {
        	e.printStackTrace();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        } 
        finally {
        	if (con!=null) {
        		try {
        			con.close();
        		}
        		catch (Exception ignore) {
            		//System.out.println(ignore);
        		}
        	}
        }
    	return isu;
    }
    */
    
    public InitSessionUsr connectToSchema(String schema, String id) {
    	//String info=null;
    	InitSessionUsr isu=null;
    	isu = connectToSchema_v1(schema, id);
    	/*
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
    		stmt = con.prepareStatement("select * from CIVITAS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where ID=?");
    		stmt.setInt(1,Integer.valueOf(id).intValue());
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			//id tidak dimasukan krn menggunakan npm
    			int id_obj = rs.getInt("ID_OBJ");
    			int obj_lvl = rs.getInt("OBJ_LEVEL");
    			String obj_nm = rs.getString("OBJ_NAME");
    			String kdpst = rs.getString("KDPSTMSMHS");
    			String fullname = rs.getString("NMMHSMSMHS");
    			String npm = rs.getString("NPMHSMSMHS");
    			String access_level_conditional=rs.getString("ACCESS_LEVEL_CONDITIONAL");
    			String access_level=rs.getString("ACCESS_LEVEL");
    			//String access_level=rs.getString("ACCESS_LEVEL");
    			isu = new InitSessionUsr(fullname,kdpst,npm,schema,obj_lvl,access_level_conditional,access_level,id_obj);
    		    //System.out.println("onject "+obj_nm);
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
    	*/
        //finally {
        //	if (con!=null) {
        //		try {
        //			con.close();
        //		}
        //		catch (Exception ignore) {
         //   		//System.out.println(ignore);
        //		}
        //	}
        //}
    	return isu;
    }
    
    public InitSessionUsr connectToSchema_v1(String schema, String id) {
    	//String info=null;
    	InitSessionUsr isu=null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
    		stmt = con.prepareStatement("select * from CIVITAS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where ID=?");
    		stmt.setInt(1,Integer.valueOf(id).intValue());
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			//id tidak dimasukan krn menggunakan npm
    			int id_obj = rs.getInt("ID_OBJ");
    			int obj_lvl = rs.getInt("OBJ_LEVEL");
    			String obj_nm = rs.getString("OBJ_NAME");
    			String kdpst = rs.getString("KDPSTMSMHS");
    			String fullname = rs.getString("NMMHSMSMHS");
    			String npm = rs.getString("NPMHSMSMHS");
    			String access_level_conditional=rs.getString("ACCESS_LEVEL_CONDITIONAL");
    			String access_level=rs.getString("ACCESS_LEVEL");
    			String obj_nick = rs.getString("OBJ_NICKNAME");
    			/*
    			 * latest addition : 23Des2017
    			 */
    			String hak_akses=""+rs.getString("HAK_AKSES");
    			String scope_kmp=""+rs.getString("SCOPE_KAMPUS");
    			String kmp_dom=""+rs.getString("KODE_KAMPUS_DOMISILI");
    			String sis_kul=""+rs.getString("SISTEM_PERKULIAHAN");
    			
    			
    			
    			isu = new InitSessionUsr(fullname,kdpst,npm,schema,obj_lvl,access_level_conditional,access_level,id_obj,obj_nick, hak_akses, scope_kmp, kmp_dom, sis_kul);
    			//isu = new InitSessionUsr(fullname,kdpst,npm,schema,obj_lvl,access_level_conditional,access_level,id_obj);
    			//System.out.println("conditional "+isu.getAccessLevelConditional());
    		    //System.out.println("access"+isu.getAccessLevel());
    		    //System.out.println("hak_akses "+isu.getHak_akses());
    		    //System.out.println("scopeKmp"+isu.getScope_kmp());
    		    //System.out.println("dom "+isu.getKode_kmp_dom());
    		    //System.out.println("siskul"+isu.getSistem_perkuliahan());
    		    
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
    	return isu;
    }
}

package beans.dbase.mhs.kurikulum;

import beans.dbase.mhs.SearchDbInfoMhs;
import java.sql.Connection;
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
 * Session Bean implementation class SearchDbInfoKurikulum
 */
@Stateless
@LocalBean
public class SearchDbInfoKurikulum extends SearchDbInfoMhs {
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
     * @see SearchDbInfoMhs#SearchDbInfoMhs()
     */
    public SearchDbInfoKurikulum() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDbInfoMhs#SearchDbInfoMhs(String)
     */
    public SearchDbInfoKurikulum(String operatorNpm) {
    	super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDbInfoMhs#SearchDbInfoMhs(Connection)
     */
    public SearchDbInfoKurikulum(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public Vector getListNpmMhsAktifDgnKurikulumnya(String thsms, String kdpst) {
    	//Vector v = null;
    	Vector v_npm_list = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
		    //get npm yg ada krs
			v_npm_list = getListMhsYgAdaKrsGivenThsms(thsms, kdpst);
			//get kurikulum masing2 mhs
			if(v_npm_list!=null && v_npm_list.size()>0) {
				stmt = con.prepareStatement("select KRKLMMSMHS from EXT_CIVITAS where NPMHSMSMHS=?");
				ListIterator li = v_npm_list.listIterator();
				while(li.hasNext()) {
					String npmhs = (String)li.next();
					stmt.setString(1, npmhs);
					rs = stmt.executeQuery();
					if(rs.next()) {
						String krklm = ""+rs.getString(1);
						li.set(krklm+"`"+npmhs);
					}
					else {
						li.set("null`"+npmhs);
					}
				}
				
				Collections.sort(v_npm_list);
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
    	return v_npm_list;
    }
    
    public static int getKurIdMhs(String npmhs) {
    	int idkur = -1;;     
    	Connection con1=null;
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con1 = ds1.getConnection();
    		//cretae NPM auto increment
    		stmt1 = con1.prepareStatement("SELECT KRKLMMSMHS from EXT_CIVITAS where NPMHSMSMHS=?");
    		stmt1.setString(1,npmhs);
    		rs1 = stmt1.executeQuery();
    		if(rs1.next()) {
    			idkur = rs1.getInt(1);
    		}
    	} 
        catch (NamingException e) {
        	e.printStackTrace();
        }
        catch (SQLException ex) {
        	ex.printStackTrace();
        } 
        finally {
        	if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
		    if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
		    if (con1!=null) try { con1.close();} catch (Exception ignore){}
        }	
    	return idkur;
    }

}

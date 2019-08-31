package beans.dbase.mhs.btstu;

import beans.dbase.UpdateDb;

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
 * Session Bean implementation class UpdateDbInfoMhsBtstu
 */
@Stateless
@LocalBean
public class UpdateDbInfoMhsBtstu extends UpdateDb {
	String operatorNpm;
	String tknOperatorNickname;
	String operatorNmm;
	boolean petugas;
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;
    /**
     * @see UpdateDb#UpdateDb()
     */
    public UpdateDbInfoMhsBtstu() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbInfoMhsBtstu(String operatorNpm) {
    	super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.tknOperatorNickname = getTknOprNickname();
    	//System.out.println("tknOperatorNickname1="+this.tknOperatorNickname);
    	this.petugas = cekApaUsrPetugas();
        // TODO Auto-generated constructor stub
    }
    
    
    
    public int insertStmhstrlsmMhsLewatBatasStudi(Vector v_SearchDbInfoMhsBtstu_getListMhsAktifLewatBtstu, String target_thsms, String target_kode_stmhs, String note) {
    	//kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+batas
    	int upd = 0;
    	if(v_SearchDbInfoMhsBtstu_getListMhsAktifLewatBtstu!=null && v_SearchDbInfoMhsBtstu_getListMhsAktifLewatBtstu.size()>0) {
    		ListIterator li = v_SearchDbInfoMhsBtstu_getListMhsAktifLewatBtstu.listIterator();
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//	select bila ada remove
        		//stmt = con.prepareStatement("select * from DAFTAR_ULANG where KDPST=? and NPMHS=?");
        		stmt = con.prepareStatement("UPDATE CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String kdpst = st.nextToken();
        			String npmhs = st.nextToken();
        			stmt.setString(1, target_kode_stmhs);
        			stmt.setString(2, npmhs);
        			upd = upd+stmt.executeUpdate();
        		}	
        		//System.out.println("upd="+upd);
        		upd=0;
        		
        		li = v_SearchDbInfoMhsBtstu_getListMhsAktifLewatBtstu.listIterator();
        		stmt = con.prepareStatement("insert ignore into TRLSM(THSMS,KDPST,NPMHS,STMHS,NOTE)values(?,?,?,?,?)");
        		//System.out.println("v_SearchDbInfoMhsBtstu_getListMhsAktifLewatBtstu-1="+v_SearchDbInfoMhsBtstu_getListMhsAktifLewatBtstu.size());
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String kdpst = st.nextToken();
        			String npmhs = st.nextToken();
        			//String kdpst = st.nextToken();
        			//String kdpst = st.nextToken();
        			//String kdpst = st.nextToken();
        			stmt.setString(1, target_thsms);
        			stmt.setString(2, kdpst);
        			stmt.setString(3, npmhs);
        			stmt.setString(4, target_kode_stmhs);
        			stmt.setString(5, note);
        			int i = stmt.executeUpdate();
        			if(i>0) {
        				upd++;
        				li.remove();
        			}
        		}
        		
        		if(v_SearchDbInfoMhsBtstu_getListMhsAktifLewatBtstu!=null && v_SearchDbInfoMhsBtstu_getListMhsAktifLewatBtstu.size()>0) {
        			//System.out.println("v_SearchDbInfoMhsBtstu_getListMhsAktifLewatBtstu-2="+v_SearchDbInfoMhsBtstu_getListMhsAktifLewatBtstu.size());
            		li = v_SearchDbInfoMhsBtstu_getListMhsAktifLewatBtstu.listIterator();
            		stmt = con.prepareStatement("update TRLSM set STMHS=?,NOTE=? where THSMS=? and NPMHS=?");
            		while(li.hasNext()) {
            			String brs = (String)li.next();
            			StringTokenizer st = new StringTokenizer(brs,"`");
            			String kdpst = st.nextToken();
            			String npmhs = st.nextToken();
            			stmt.setString(1, target_kode_stmhs);
            			stmt.setString(2, note);
            			stmt.setString(3, target_thsms);
            			stmt.setString(4, npmhs);
            			upd = upd + stmt.executeUpdate();
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
    	return upd;
    }
}

package beans.dbase.daftarUlang;

import beans.dbase.UpdateDb;
import beans.tools.Tool;

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
 * Session Bean implementation class UpdateDaftarUlangTable
 */
@Stateless
@LocalBean
public class UpdateDaftarUlangTable extends UpdateDb {
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
    public UpdateDaftarUlangTable() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDaftarUlangTable(String operatorNpm) {
    	super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    
    public int syncDaftarUlangDenganHeregistrasiRule(String thsms) {
    	int upd=0;
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		String sql = "SELECT A.KDPST,A.KODE_KAMPUS,A.TKN_JABATAN_VERIFICATOR,A.URUTAN,B.ID_OBJ FROM HEREGISTRASI_RULES A inner join OBJECT B on (A.KDPST=B.KDPST and A.KODE_KAMPUS=B.KODE_KAMPUS_DOMISILI) where A.THSMS=?";
    		stmt = con.prepareStatement(sql);
    		stmt.setString(1, thsms);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				int i=1;
    				String kdpst = rs.getString(i++);
    				String kdkmp = rs.getString(i++);
    				String tkn_jab = rs.getString(i++);
    				String urutan = rs.getString(i++);
    				String target_idobj = rs.getString(i++);
    				String tmp = kdkmp+"~"+kdpst+"~"+tkn_jab+"~"+urutan+"~"+target_idobj;
    				tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
    				li.add(tmp);
    			}
    			while(rs.next());
    			
    			//update table daftar ulang
    			stmt = con.prepareStatement("update DAFTAR_ULANG set TKN_JABATAN_APPROVAL=? where THSMS=? and ID_OBJ=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"~");
    				String kdkmp = st.nextToken();
    				String kdpst = st.nextToken();
    				String tkn_jab = st.nextToken();
    				String urutan = st.nextToken();
    				String target_idobj = st.nextToken();
    				stmt.setString(1, tkn_jab);
    				stmt.setString(2, thsms);
    				stmt.setInt(3, Integer.parseInt(target_idobj));
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
    	finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return upd;
	}
	
}

package beans.dbase.krklm;

import beans.dbase.SearchDb;
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
 * Session Bean implementation class UpdateDbKrklm
 */
@Stateless
@LocalBean
public class UpdateDbKrklm extends UpdateDb {
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
    public UpdateDbKrklm() {
        super();
        // TODO Auto-generated constructor stub
        
    }
    
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbKrklm(String operatorNpm) {
        super(operatorNpm);
        // TODO Auto-generated constructor stub
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
    }
    
    public int insertNewKurikulum(String kdpst,String kdkur,String sta_thsms,String konsen,int tot_sks_wajib,int tot_sks_opt,int tot_semes) {
    	
    	int updated=0;
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("INSERT INTO KRKLM(KDPSTKRKLM,NMKURKRKLM,STARTTHSMS,STKURKRKLM,SKSTTKRKLM,SMSTTKRKLM,SKSTTWAJIB,SKSTTPILIH,KONSENTRASI) VALUES (?,?,?,?,?,?,?,?,?)");
    		stmt.setString(1,kdpst);
    		stmt.setString(2,kdkur);
    		if(sta_thsms!=null && !sta_thsms.equalsIgnoreCase("null")) {
    			stmt.setString(3, sta_thsms);
    		}
    		else {
    			stmt.setNull(3, java.sql.Types.VARCHAR);
    		}
    		stmt.setString(4, "N");
    		stmt.setInt(5,tot_sks_opt+tot_sks_wajib);
    		stmt.setInt(6, tot_semes);
    		stmt.setInt(7, tot_sks_wajib);
    		stmt.setInt(8, tot_sks_opt);
    		if(Checker.isStringNullOrEmpty(konsen)) {
    			stmt.setNull(9, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(9, konsen);
    		}
    		
    		updated = stmt.executeUpdate();
    		
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
    
    public int aktifkanMatakuliahKrklm(int idkur) {
    	int upd = 0;
    	String tkn_idkmk=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT IDKMKMAKUL FROM MAKUL inner join MAKUR on IDKMKMAKUL=IDKMKMAKUR where IDKURMAKUR=?");
    		stmt.setInt(1, idkur);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			tkn_idkmk = new String();
    			do {
    				tkn_idkmk = tkn_idkmk+rs.getInt(1)+"`";
    			}
    			while(rs.next());
    		}
    		if(tkn_idkmk!=null) {
    			stmt = con.prepareStatement("UPDATE MAKUL set STKMKMAKUL=? where IDKMKMAKUL=?");
    			StringTokenizer st = new StringTokenizer(tkn_idkmk,"`");
    			while(st.hasMoreTokens()) {
    				String idkmk = st.nextToken();
        			try {
        				stmt.setString(1, "A");
            			stmt.setInt(2, Integer.parseInt(idkmk));
            			upd = upd+stmt.executeUpdate();
        			}
        			catch(Exception e) {}	
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
    
    //idkur+","+kdkur+","+stkur+","+thsms1+","+thsms2+","+konsen+","+skstt+","+smstt+","+wajib+","+option
    public int updateKurikulum(String kdpst, String idkur, String kdkur, String stkur, String thsms1, String thsms2, String konsen, String skstt, String smstt, String wajib, String pilihan){
    	kdkur = kdkur.toUpperCase().trim();
    	int updated=0;
    	//System.out.println("idkur--"+idkur);
    	//System.out.println("kdkur--"+kdkur);
    	//nakmk = nakmk.toUpperCase();
    	SearchDb sdb = new SearchDb();
    	String thsms_now = sdb.getThsmsAktif();
    	stkur = stkur.trim();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//updtae recorrd
    		int i=1;
    		stmt = con.prepareStatement("update KRKLM set KDPSTKRKLM=?,NMKURKRKLM=?,STKURKRKLM=?,STARTTHSMS=?,ENDEDTHSMS=?,TARGTKRKLM=?,SKSTTKRKLM=?,SMSTTKRKLM=?,KONSENTRASI=?,SKSTTWAJIB=?,SKSTTPILIH=? where IDKURKRKLM=?");
    		//1
    		stmt.setString(i++,kdpst);
    		//2
    		if(kdkur!=null) {
    			stmt.setString(i++,kdkur.toUpperCase().trim());
    		}
    		else {
    			stmt.setNull(i++,java.sql.Types.VARCHAR);
    		}
    		//3
    		stmt.setString(i++,stkur);
    		//4
    		if(thsms1!=null) {
    			stmt.setString(i++,thsms1);
    		}
    		else {
    			stmt.setNull(i++,java.sql.Types.VARCHAR);
    		}
    		//5
    		if(thsms2!=null) {
    			stmt.setString(i++,thsms2);
    		}
    		else {
    			stmt.setNull(i++,java.sql.Types.VARCHAR);
    		}
    		//6 target angkatan sudah tidak digunakan
    		stmt.setNull(i++,java.sql.Types.VARCHAR);
    		//7
    		if(Checker.isStringNullOrEmpty(skstt)) {
    			stmt.setInt(i++, 0);
    		}
    		else {
    			try {
    				stmt.setInt(i++, Integer.parseInt(skstt));	
    			}
    			catch(Exception e) {
    				stmt.setInt(i++, 0);
    			}
    			
    		}
    		//8
    		if(Checker.isStringNullOrEmpty(smstt)) {
    			stmt.setInt(i++, 0);
    		}
    		else {
    			try {
    				stmt.setInt(i++, Integer.parseInt(smstt));	
    			}
    			catch(Exception e) {
    				stmt.setInt(i++, 0);
    			}
    			
    		}
    		
    		//9
    		if(Checker.isStringNullOrEmpty(konsen)) {
    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(i++, konsen.toUpperCase().trim());
    		}
    		//10
    		if(Checker.isStringNullOrEmpty(wajib)) {
    			stmt.setInt(i++, 0);
    		}
    		else {
    			try {
    				stmt.setInt(i++, Integer.parseInt(wajib));	
    			}
    			catch(Exception e) {
    				stmt.setInt(i++, 0);
    			}
    			
    		}
    		//11
    		if(Checker.isStringNullOrEmpty(pilihan)) {
    			stmt.setInt(i++, 0);
    		}
    		else {
    			try {
    				stmt.setInt(i++, Integer.parseInt(pilihan));	
    			}
    			catch(Exception e) {
    				stmt.setInt(i++, 0);
    			}
    			
    		}
    		//12
    		stmt.setLong(i++, Long.valueOf(idkur).longValue());
    		updated = stmt.executeUpdate();
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

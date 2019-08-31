package beans.dbase.spmi;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ListIterator;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class SearchQandA
 */
@Stateless
@LocalBean
public class SearchQandA extends SearchDb {
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
    public SearchQandA() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchQandA(String operatorNpm) {
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
    public SearchQandA(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public String getPertanyaan(String id_std_isi_question) {
    	String question = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("SELECT QUESTION,TKN_NM_DOK,NOTE from STANDARD_ISI_QUESTION where ID="+id_std_isi_question);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		question = ""+rs.getString(1);
    			//String list_dok_terkait = ""+rs.getString(i++);
    			//String note = ""+rs.getString(i++);
    			
    			//String tmp = question+"~"+list_dok_terkait+"~"+note;
    			//tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
    			//li.add(tmp);
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
    	return question;
    }
    
    public Vector getListIdJawabanNilaiDariPertanyaan(String id_std_isi_question) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("SELECT ID,ANSWER,BOBOT from STANDARD_ISI_ANSWER where ID_QUESTION="+id_std_isi_question+" order by ID");
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		do {
        			String id = rs.getString(1);
        			String jawab = rs.getString(2);
        			String nilai = rs.getString(3);
        			String tmp = id+"~"+jawab+"~"+nilai;
        			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        			li.add(tmp);
        		}
        		while(rs.next());
        		
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
    	return v;
    }

    public String getDokTerkaitPertanyaan(String id_std_isi_question) {
    	String dok = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("SELECT QUESTION,TKN_NM_DOK,NOTE from STANDARD_ISI_QUESTION where ID="+id_std_isi_question);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		dok = ""+rs.getString(2);
    			//String list_dok_terkait = ""+rs.getString(i++);
    			//String note = ""+rs.getString(i++);
    			
    			//String tmp = question+"~"+list_dok_terkait+"~"+note;
    			//tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
    			//li.add(tmp);
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
    	return dok;
    }
    
    public Vector getQandA(int std_isi_id) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
    		//String sql = "select * from RIWAYAT_EVALUASI_AMI A inner join STANDARD_MANUAL_EVALUASI B on (A.VERSI_ID=B.VERSI_ID AND A.STD_ISI_ID=B.STD_ISI_ID AND A.NORUT=B.NORUT) where A.VERSI_ID=? AND A.STD_ISI_ID=? AND A.NORUT=? AND A.KDPST=? order by A.ID_EVAL desc limit ?,?";
        	String sql = "SELECT A.ID,A.QUESTION,A.TKN_NM_DOK,A.NOTE,B.ID,B.ANSWER,B.BOBOT FROM STANDARD_ISI_QUESTION A inner join STANDARD_ISI_ANSWER B on A.ID=B.ID_QUESTION where A.ID_STD_ISI=? order by A.ID";
    		stmt=con.prepareStatement(sql);
    		stmt.setInt(1, std_isi_id);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			boolean first = true;
    			v = new Vector();
    			li = v.listIterator();
    			int counter=0;
    			do {
    				counter++;
    				int idque = rs.getInt(1);
    				String question = ""+rs.getString(2);
    				String tkn_doc = ""+rs.getString(3);
    				if(Checker.isStringNullOrEmpty(tkn_doc)) {
    					tkn_doc = "null";
    				}
    				String note = ""+rs.getString(4);
    				if(Checker.isStringNullOrEmpty(note)) {
    					note = "null";
    				}
    				int idans = rs.getInt(5);
    				String answer = ""+rs.getString(6);
    				if(Checker.isStringNullOrEmpty(answer)) {
    					answer = "null";
    				}
    				double bobot = rs.getDouble(7);
    				li.add(idque+"~"+question+"~"+tkn_doc+"~"+note+"~"+answer+"~"+bobot);
    			}
    			while(rs.next());
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
    	return v;
    }
    
}

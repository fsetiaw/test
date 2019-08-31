package beans.dbase.pesan;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.Converter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;
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
 * Session Bean implementation class GetRiwayatPesanTamu
 */
@Stateless
@LocalBean
public class HistoryPesanTamu extends SearchDb {
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
    public HistoryPesanTamu() {
        // TODO Auto-generated constructor stub
    }
    
    public HistoryPesanTamu(String operatorNpm) {
    	super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
    }
    
    public HistoryPesanTamu(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public Vector getRiwayatPesan(int offset, int limit, Vector v_scope_id, String tkn_my_nick) {
    	Vector v = null;
    	//System.out.println("sampe");
    	//System.out.println(v_scope_id.size());
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		//get id_obj tamu sesuai dengan operatornya
    		String tkn_sql = null;
    		ListIterator li = v_scope_id.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			//System.out.println(brs);
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kmp = st.nextToken();
    			while(st.hasMoreTokens()) {
    				String id = st.nextToken();
    				String obj_desc = Checker.getObjDesc(Integer.parseInt(id));
    				if(obj_desc.contains("tamu")||obj_desc.contains("TAMU")) {
    					if(tkn_sql==null) {
        					tkn_sql = new String("CRETOROBJECTID="+id);
        				}
        				else {
        					tkn_sql = tkn_sql+" or CRETOROBJECTID="+id;
        				}
    				}
    			}
    		}
    		/*
    		Vector v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
    		ListIterator li = v_scope_kdpst.listIterator();
    		boolean match = false;
    		String tkn_sql = null;
    		while(li.hasNext()&&!match) {
    			String brs = (String)li.next();
    			if(brs.contains("`65001")||brs.contains("`65101")||brs.contains("`61101")) {
    				if(tkn_sql==null) {
    					tkn_sql = new String("TARGETOBJECTNICKNAME like '%TU PASCA%'");
    				}
    				else {
    					tkn_sql = tkn_sql+" or TARGETOBJECTNICKNAME like '%TU PASCA%'";
    				}
    			}
    			else {
    				if(tkn_sql==null) {
    					tkn_sql = new String("TARGETOBJECTNICKNAME like '%PMB SATYA%'");
    				}
    				else {
    					tkn_sql = tkn_sql+" or TARGETOBJECTNICKNAME like '%PMB SATYA%'";
    				}
    			}
    		}
    		*/
    		if(tkn_sql!=null) {
    			try {
                	Context initContext  = new InitialContext();
            		Context envContext  = (Context)initContext.lookup("java:/comp/env");
            		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            		con = ds.getConnection();
            		
            		String sqlcmd = "SELECT NMMHSCREATOR,TOPIKCONTENT,MARKEDASREADATTARGET,UPDTMTOPIK,TARGETOBJECTNICKNAME FROM TOPIK where ("+tkn_sql+") order by TOPIK.IDTOPIK desc limit ?,? ";
            		stmt = con.prepareStatement(sqlcmd);	
            		
            		//System.out.println("sqlcmd="+sqlcmd);
            		stmt.setInt(1, offset);
            		stmt.setInt(2, limit);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			v = new Vector();
            			li = v.listIterator();
            			do {
            				String nmmhs = rs.getString(1);
            				String topik = rs.getString(2);
            				String reed = ""+rs.getBoolean(3);
            				String updtm = ""+rs.getTimestamp(4);
            				String target_nickname = rs.getString(5);
            				li.add(nmmhs+"`"+topik+"`"+reed+"`"+updtm+"`"+target_nickname);
            			}
            			while(rs.next());
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
    		}	
    	}
    	return v;
    }

}

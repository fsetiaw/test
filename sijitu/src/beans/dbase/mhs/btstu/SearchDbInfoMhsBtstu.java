package beans.dbase.mhs.btstu;

import beans.dbase.mhs.SearchDbInfoMhs;
import beans.tools.Checker;
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
 * Session Bean implementation class SearchDbInfoMhsBtstu
 */
@Stateless
@LocalBean
public class SearchDbInfoMhsBtstu extends SearchDbInfoMhs {
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
    public SearchDbInfoMhsBtstu() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDbInfoMhs#SearchDbInfoMhs(String)
     */
    public SearchDbInfoMhsBtstu(String operatorNpm) {
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
    public SearchDbInfoMhsBtstu(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public Vector getInfoBtstuMhs(String thsms, String kdpst) {
    	//Vector v = null;
    	if(thsms.contains("A")) {
    		thsms = thsms.substring(0,4)+"1";
    	}
    	else if(thsms.contains("B")) {
    		thsms = thsms.substring(0,4)+"2";
    	}
    	StringTokenizer st = null;
    	Vector v = null;
    	try {
    		
			v = getListMhsYgAdaKrsGivenThsms(thsms, kdpst);
			if(v!=null && v.size()>0){
				Context initContext  = new InitialContext();
				Context envContext  = (Context)initContext.lookup("java:/comp/env");
				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
				con = ds.getConnection();
			//	get
				stmt = con.prepareStatement("select ID_OBJ,NMMHSMSMHS,SMAWLMSMHS from CIVITAS where NPMHSMSMHS=?");
				ListIterator li = v.listIterator();
				while(li.hasNext()) {
					String npmhs = (String)li.next();
					stmt.setString(1, npmhs);
					rs = stmt.executeQuery();
					rs.next();
					String idobj = ""+rs.getInt(1);
					String nmmhs = rs.getString(2);
					String smawl = rs.getString(3);
					li.set(npmhs+"`"+nmmhs+"`"+smawl+"`"+idobj);		
				}
				li = v.listIterator();
				while(li.hasNext()){
					String brs = (String)li.next();
					st = new StringTokenizer(brs,"`");
					String npmhs = st.nextToken();
					String nmmhs = st.nextToken();
					String smawl = st.nextToken();
					String idobj = st.nextToken();
					String batas = Tool.calcBatasStudi(smawl, Integer.parseInt(idobj));
					li.set(brs+"`"+batas);
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
    	return v;
    }
    
    public Vector getListMhsLewatBtstu(String thsms, String kdpst) {
    	/*
    	 * beda colom dgn fungsi dibawah
    	 */
    	if(thsms.contains("A")) {
    		thsms = thsms.substring(0,4)+"1";
    	}
    	else if(thsms.contains("B")) {
    		thsms = thsms.substring(0,4)+"2";
    	}
    	Vector v = null;
    	try {
    		v = getInfoBtstuMhs(thsms,kdpst);
    		if(v!=null && v.size()>0) {
    			ListIterator li = v.listIterator();
    			while(li.hasNext()) {
    				String brs=(String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String npmhs = st.nextToken();
    				String nmmhs = st.nextToken();
    				String smawl = st.nextToken();
    				String idobj = st.nextToken();
    				String batas = st.nextToken();
    				if(thsms.compareToIgnoreCase(batas)<=0) {
    					li.remove();
    				}
    			}
    		}
    	}
    	//catch (NamingException e) {
    	//	e.printStackTrace();
    	//}
    	catch (Exception ex) {
    		ex.printStackTrace();
    	} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	}   
    	return v;
    }
    
    
    public Vector getListMhsAktifLewatBtstu(String thsms_now, String target_kdpst, boolean malaikat_included) {
    	/*
    	 * beda colom dgn fungsi diatas
    	 */
    	if(thsms_now.contains("A")) {
    		thsms_now = thsms_now.substring(0,4)+"1";
    	}
    	else if(thsms_now.contains("B")) {
    		thsms_now = thsms_now.substring(0,4)+"2";
    	}
    	Vector v = null,v1=null,v2=null;
    	try {
    		SearchDbInfoMhs sdi = new SearchDbInfoMhs();
			v1 = sdi.getListNpmhsYgAdaDiTrnlm(thsms_now, target_kdpst, true);
			v2 = sdi.getListNpmhsYgCutiOrLulus(thsms_now, target_kdpst, true);
			v = Tool.combine2VectorSameStructureAndRemoveDuplicate(v1, v2);
    		if(v!=null && v.size()>0) {
    			ListIterator li = v.listIterator();
    			while(li.hasNext()) {
    				String brs=(String)li.next();
    				//kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+btstu;
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdpst = st.nextToken();
    				String npmhs = st.nextToken();
    				String nmmhs = st.nextToken();
    				String smawl = st.nextToken();
    				String batas = st.nextToken();
    				if(Checker.isStringNullOrEmpty(batas)||thsms_now.compareToIgnoreCase(batas)<=0) {
    					li.remove();
    				}
    				
    			}
    		}
    	}
    	//catch (NamingException e) {
    	//	e.printStackTrace();
    	//}
    	catch (Exception ex) {
    		ex.printStackTrace();
    	} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	}   
    	return v;
    }
    
    public Vector getListMhsLewatBtstuNpmhOnly(String thsms, String kdpst) {
    	Vector v = getListMhsLewatBtstu(thsms, kdpst);
    	if(v!=null&&v.size()>0) {
    		ListIterator li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String npmhs = st.nextToken();
    			li.set(npmhs);
    		}
    	}
    	return v;
    }

}

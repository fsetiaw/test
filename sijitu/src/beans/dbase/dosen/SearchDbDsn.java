package beans.dbase.dosen;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;
import java.util.Vector;
import java.util.Collections;
import java.util.ListIterator;
import java.util.StringTokenizer;
/**
 * Session Bean implementation class SearchDbDsn
 */
@Stateless
@LocalBean
public class SearchDbDsn extends SearchDb {
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
    public SearchDbDsn() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbDsn(String operatorNpm) {
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
    public SearchDbDsn(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    /*
     * fungsi in tmp saja..!!!!! jangan dipake
     */
    public Vector getListKaprodi(String kdpst_dosen) {
    	Vector v = new Vector();
    	ListIterator li = null;
    	try {
    		li = v.listIterator();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from CIVITAS where KDPSTMSMHS=? order by NMMHSMSMHS");
    		stmt.setString(1, kdpst_dosen);
    		rs = stmt.executeQuery();
    		//String tmp = "";
    		while(rs.next()) {
    			String id_obj = ""+rs.getString("ID_OBJ");
    			String npm = ""+rs.getString("NPMHSMSMHS");
    			String nmm = ""+rs.getString("NMMHSMSMHS");
    			
    			li.add(id_obj+"||"+npm+"||"+nmm);
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
    
    
    public Vector getListDosenHidup() {
    	Vector v = new Vector();
    	ListIterator li = null;
    	try {
    		li = v.listIterator();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select ID_OBJ,NPMHSMSMHS,NMMHSMSMHS,NIDNN from CIVITAS inner join EXT_CIVITAS_DATA_DOSEN on NPMHSMSMHS=NPMHS where STATUS<>? order by NMMHSMSMHS");
    		stmt.setString(1, "AL");
    		rs = stmt.executeQuery();
    		//String tmp = "";
    		while(rs.next()) {
    			String id_obj = ""+rs.getString("ID_OBJ");
    			String npm = ""+rs.getString("NPMHSMSMHS");
    			String nmm = ""+rs.getString("NMMHSMSMHS");
    			String nidn = ""+rs.getString("NIDNN");
    			li.add(id_obj+"||"+npm+"||"+nmm+"||"+nidn);
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
    
    public Vector getListDosenAktif() {
    	Vector v = new Vector();
    	ListIterator li = null;
    	try {
    		li = v.listIterator();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select ID_OBJ,NPMHSMSMHS,NMMHSMSMHS,NIDNN from CIVITAS inner join EXT_CIVITAS_DATA_DOSEN on NPMHSMSMHS=NPMHS where STATUS=? order by NMMHSMSMHS");
    		stmt.setString(1, "A");
    		rs = stmt.executeQuery();
    		//String tmp = "";
    		while(rs.next()) {
    			String id_obj = ""+rs.getString("ID_OBJ");
    			String npm = ""+rs.getString("NPMHSMSMHS");
    			String nmm = ""+rs.getString("NMMHSMSMHS");
    			String nidn = ""+rs.getString("NIDNN");
    			li.add(id_obj+"||"+npm+"||"+nmm+"||"+nidn);
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
    
    public Vector getListDosenAlive() {
    	Vector v = new Vector();
    	ListIterator li = null;
    	try {
    		li = v.listIterator();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select ID_OBJ,NPMHSMSMHS,NMMHSMSMHS,NIDNN from CIVITAS inner join EXT_CIVITAS_DATA_DOSEN on NPMHSMSMHS=NPMHS where STATUS<>? order by NMMHSMSMHS");
    		stmt.setString(1, "AL");
    		rs = stmt.executeQuery();
    		//String tmp = "";
    		while(rs.next()) {
    			String id_obj = ""+rs.getString("ID_OBJ");
    			String npm = ""+rs.getString("NPMHSMSMHS");
    			String nmm = ""+rs.getString("NMMHSMSMHS");
    			String nidn = ""+rs.getString("NIDNN");
    			li.add(id_obj+"||"+npm+"||"+nmm+"||"+nidn);
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
    /*
     * depreccated
     */
    public Vector getListInfoDosen_v1(String listKdpstDollarSeperator) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		/*
    		 * get total login
    		 */
    		String sql = "(";
    		if(listKdpstDollarSeperator!=null) {
    			StringTokenizer st = new StringTokenizer(listKdpstDollarSeperator,"$");
    			while(st.hasMoreTokens()) {
    				String kdpst = st.nextToken();
    				sql = sql + "TKN_KDPST_TEACH like '%"+kdpst+"%'";
    				if(st.hasMoreTokens()) {
    					sql = sql + " or ";
    				}
    			}
    			sql = sql+")";
    			sql = "select * from MSDOS where "+sql+" and STATUS=?";
    			//System.out.println("ini="+sql);
    			stmt = con.prepareStatement(sql);
        		stmt.setString(1,"A");
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String nodos = ""+rs.getString ("NODOS");
        			if(Checker.isStringNullOrEmpty(nodos)) {
        				nodos="null";
        			}
        			String npmdos = ""+rs.getString ("NPMDOS");
        			if(Checker.isStringNullOrEmpty(npmdos)) {
        				npmdos="null";
        			}
        			String nidndos = ""+rs.getString ("NIDN");
        			if(Checker.isStringNullOrEmpty(nidndos)) {
        				nidndos="null";
        			}
        			String noKtp = ""+rs.getString("NO_KTP");
        			if(Checker.isStringNullOrEmpty(noKtp)) {
        				noKtp="null";
        			}
        			String ptihbase = ""+rs.getString("KDPTI_HOMEBASE");
        			if(Checker.isStringNullOrEmpty(ptihbase)) {
        				ptihbase="null";
        			}
        			String psthbase = ""+rs.getString("KDPST_HOMEBASE");
        			if(Checker.isStringNullOrEmpty(psthbase)) {
        				psthbase="null";
        			}
        			String nmdos = ""+rs.getString("NMDOS");
        			if(Checker.isStringNullOrEmpty(nmdos)) {
        				nmdos="null";
        			}
        			String gelar = ""+rs.getString("GELAR");
        			if(Checker.isStringNullOrEmpty(gelar)) {
        				gelar="null";
        			}
        			String smawl = ""+rs.getString("SMAWL");
        			if(Checker.isStringNullOrEmpty(smawl)) {
        				smawl="null";
        			}
        			String tknKdpstAjar = ""+rs.getString("TKN_KDPST_TEACH");
        			if(Checker.isStringNullOrEmpty(tknKdpstAjar)) {
        				tknKdpstAjar="null";
        			}
        			String email = ""+rs.getString("EMAIL");
        			if(Checker.isStringNullOrEmpty(email)) {
        				email="null";
        			}
        			String tknTelp = ""+rs.getString("TKN_TELP");
        			if(Checker.isStringNullOrEmpty(tknTelp)) {
        				tknTelp="null";
        			}
        			String tknHp = ""+rs.getString("TKN_HP");
        			if(Checker.isStringNullOrEmpty(tknHp)) {
        				tknHp="null";
        			}
        			String status = ""+rs.getString("STATUS");
        			if(Checker.isStringNullOrEmpty(status)) {
        				status="null";
        			}
        			li.add(nodos+"$"+npmdos+"$"+nidndos+"$"+noKtp+"$"+ptihbase+"$"+psthbase+"$"+nmdos+"$"+gelar+"$"+smawl+"$"+tknKdpstAjar+"$"+email+"$"+tknTelp+"$"+tknHp+"$"+status);
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
    	//System.out.println(v.size());
    	return v;
    }

    public Vector getListInfoDosen_v2(String listKdpstDollarSeperator) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		/*
    		 * get total login
    		 */
    		String sql = "(";
    		if(listKdpstDollarSeperator!=null) {
    			StringTokenizer st = new StringTokenizer(listKdpstDollarSeperator,"$");
    			while(st.hasMoreTokens()) {
    				String kdpst = st.nextToken();
    				sql = sql + "TKN_KDPST_TEACH like '%"+kdpst+"%'";
    				if(st.hasMoreTokens()) {
    					sql = sql + " or ";
    				}
    			}
    			sql = sql+")";
    			sql = "select * from MSDOS where "+sql+" and STATUS=?";
    			//System.out.println("ini="+sql);
    			stmt = con.prepareStatement(sql);
        		stmt.setString(1,"A");
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String nodos = ""+rs.getString ("NODOS");
        			if(Checker.isStringNullOrEmpty(nodos)) {
        				nodos="null";
        			}
        			String npmdos = ""+rs.getString ("NPMDOS");
        			if(Checker.isStringNullOrEmpty(npmdos)) {
        				npmdos="null";
        			}
        			String nidndos = ""+rs.getString ("NIDN");
        			if(Checker.isStringNullOrEmpty(nidndos)) {
        				nidndos="null";
        			}
        			String noKtp = ""+rs.getString("NO_KTP");
        			if(Checker.isStringNullOrEmpty(noKtp)) {
        				noKtp="null";
        			}
        			String ptihbase = ""+rs.getString("KDPTI_HOMEBASE");
        			if(Checker.isStringNullOrEmpty(ptihbase)) {
        				ptihbase="null";
        			}
        			String psthbase = ""+rs.getString("KDPST_HOMEBASE");
        			if(Checker.isStringNullOrEmpty(psthbase)) {
        				psthbase="null";
        			}
        			String nmdos = ""+rs.getString("NMDOS");
        			if(Checker.isStringNullOrEmpty(nmdos)) {
        				nmdos="null";
        			}
        			String gelar = ""+rs.getString("GELAR");
        			if(Checker.isStringNullOrEmpty(gelar)) {
        				gelar="null";
        			}
        			String smawl = ""+rs.getString("SMAWL");
        			if(Checker.isStringNullOrEmpty(smawl)) {
        				smawl="null";
        			}
        			String tknKdpstAjar = ""+rs.getString("TKN_KDPST_TEACH");
        			if(Checker.isStringNullOrEmpty(tknKdpstAjar)) {
        				tknKdpstAjar="null";
        			}
        			String email = ""+rs.getString("EMAIL");
        			if(Checker.isStringNullOrEmpty(email)) {
        				email="null";
        			}
        			String tknTelp = ""+rs.getString("TKN_TELP");
        			if(Checker.isStringNullOrEmpty(tknTelp)) {
        				tknTelp="null";
        			}
        			String tknHp = ""+rs.getString("TKN_HP");
        			if(Checker.isStringNullOrEmpty(tknHp)) {
        				tknHp="null";
        			}
        			String status = ""+rs.getString("STATUS");
        			if(Checker.isStringNullOrEmpty(status)) {
        				status="null";
        			}
        			li.add(nodos+"$"+npmdos+"$"+nidndos+"$"+noKtp+"$"+ptihbase+"$"+psthbase+"$"+nmdos+"$"+gelar+"$"+smawl+"$"+tknKdpstAjar+"$"+email+"$"+tknTelp+"$"+tknHp+"$"+status);
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
    	//System.out.println(v.size());
    	return v;
    }
    
    public Vector getListInfoTrakd(String thsms) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT * FROM TRAKD where THSMSTRAKD=? order by NAKMKTRAKD,KDKMKTRAKD,SHIFTTRAKD,KELASTRAKD");
    		stmt.setString(1, thsms);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			//String thsms = ""+rs.getString("THSMSTRAKD");
    			String kdpst = ""+rs.getString("KDPSTTRAKD");
    			String nodos = ""+rs.getString("NODOSTRAKD");
    			String nmdos = ""+rs.getString("NMDOSTRAKD");
    			String idkmk = ""+rs.getLong("IDKMKTRAKD");
    			String kdkmk = ""+rs.getString("KDKMKTRAKD");
    			String nakmk = ""+rs.getString("NAKMKTRAKD");
    			String kelas = ""+rs.getString("KELASTRAKD");
    			String sksmk = ""+rs.getLong("SKSMKTRAKD");
    			String shift = ""+rs.getString("SHIFTTRAKD");
    			li.add(thsms+"`"+kdpst+"`"+nodos+"`"+nmdos+"`"+idkmk+"`"+kdkmk+"`"+nakmk+"`"+kelas+"`"+sksmk+"`"+shift);
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
    	//System.out.println(v.size());
    	return v;
    }
    
    public Vector hitungBebanMengajarVersiEpsbed(String thsms) {
    	Vector v = null;
    	ListIterator li = null;
    	Vector vf = null;
    	ListIterator lif = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT DISTINCT NODOSTRAKD,NMDOSTRAKD from TRAKD_EPSBED where THSMSTRAKD=?");
    		stmt.setString(1, thsms);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			//String thsms = ""+rs.getString("THSMSTRAKD");
    			//String kdpst = ""+rs.getString("KDPSTTRAKD");
    			String nodos = ""+rs.getString("NODOSTRAKD");
    			String nmdos = ""+rs.getString("NMDOSTRAKD");
    			//String idkmk = ""+rs.getLong("IDKMKTRAKD");
    			//String kdkmk = ""+rs.getString("KDKMKTRAKD");
    			//String nakmk = ""+rs.getString("NAKMKTRAKD");
    			//String kelas = ""+rs.getString("KELASTRAKD");
    			//String sksmk = ""+rs.getLong("SKSMKTRAKD");
    			//String shift = ""+rs.getString("SHIFTTRAKD");
    			if(v==null) {
    				v = new Vector();
    				li = v.listIterator();
    			}
    			li.add(nodos+"`"+nmdos);
    			
    		}
    		if(v!=null) {
    			vf = new Vector();
    			lif = vf.listIterator();
    			li = v.listIterator();
    			stmt = con.prepareStatement("select * from TRAKD_EPSBED where THSMSTRAKD=? and NMDOSTRAKD=? order by NAKMKTRAKD");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st= new StringTokenizer(brs,"`");
    				String nodos = st.nextToken();
    				String nmdos = st.nextToken();
    				stmt.setString(1, thsms);
    				stmt.setString(2, nmdos);
    				rs = stmt.executeQuery();
    				Vector vtmp = new Vector();
    				ListIterator lit = vtmp.listIterator();
    				
    				if(rs.next()) {
    					String kdkmk = rs.getString("KDKMKTRAKD");
    					String nakmk = rs.getString("NAKMKTRAKD");
    					String kelas = rs.getString("KELASTRAKD");
    					String sksmk = ""+rs.getInt("SKSMKTRAKD");
    					String ttmhs = ""+rs.getInt("TTMHSTRAKD");
    					lit.add(nakmk+"`"+kdkmk+"`"+kelas+"`"+sksmk+"`"+ttmhs);
    				}
    				Collections.sort(vtmp);
    				lif.add(brs);
    				lif.add(vtmp);
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
    	//System.out.println(v.size());
    	return v;
    }
    
    
    public Vector showDistinctDsn(Vector v_getListDosenPengajar) {
    	Vector vf = null;
    	if(v_getListDosenPengajar!=null && v_getListDosenPengajar.size()>0) {
    		vf = new Vector();
    		ListIterator lif = vf.listIterator();
    		try {
        		ListIterator li = v_getListDosenPengajar.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String kdpst = st.nextToken();
        			String nodos = st.nextToken();
        			String nmdos = st.nextToken();
        			String thsms = st.nextToken();
        			String surat = st.nextToken();
        			lif.add(nodos+"`"+nmdos);
        		}
        		vf = Tool.removeDuplicateFromVector(vf);
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        	}
    	}
    	return vf;
    }
    
    public Vector getListDosenPengajar(Vector v_scope_kdpst, String target_thsms) {
    	
    	Vector v = null;
    	String sql_cmd = "";
    	ListIterator li = null;
    	if(v_scope_kdpst!=null) {
    		li = v_scope_kdpst.listIterator();
            while(li.hasNext()) {
            	String info_scope = (String)li.next();
                StringTokenizer st = new StringTokenizer(info_scope,"`");
                st.nextToken();
                while(st.hasMoreTokens()) {
                     String kdpst = st.nextToken();
                     sql_cmd = sql_cmd+"KDPSTTRAKD='"+kdpst+"'";
                     if(st.hasMoreTokens()) {
                          sql_cmd = sql_cmd+" or ";
                     }
                }
                if(li.hasNext()) {
                	sql_cmd = sql_cmd+" or ";
                }
            }
        }
    	if(!Checker.isStringNullOrEmpty(sql_cmd)) {
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		System.out.println("SELECT DISTINCT KDPSTTRAKD,NODOSTRAKD,NMDOSTRAKD from TRAKD_EPSBED where THSMSTRAKD=? and ("+sql_cmd+")");
        		stmt = con.prepareStatement("SELECT DISTINCT KDPSTTRAKD,NODOSTRAKD,NMDOSTRAKD from TRAKD_EPSBED where THSMSTRAKD=? and ("+sql_cmd+")");
        		stmt.setString(1, target_thsms);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String kdpst = ""+rs.getString("KDPSTTRAKD");
        			String nodos = ""+rs.getString("NODOSTRAKD");
        			String nmdos = ""+rs.getString("NMDOSTRAKD");
        			if(v==null) {
        				v = new Vector();
        				li = v.listIterator();
        			}
        			if(!Checker.isStringNullOrEmpty(nodos)) {
        				li.add(kdpst+"`"+nodos+"`"+nmdos);	
        			}
        			
        		}
        		
        		stmt = con.prepareStatement("SELECT DISTINCT KDPSTTRAKD,NODOSTRAKD,NMDOSTRAKD from TRAKD where THSMSTRAKD=? and ("+sql_cmd+")");
        		stmt.setString(1, target_thsms);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String kdpst = ""+rs.getString("KDPSTTRAKD");
        			String nodos = ""+rs.getString("NODOSTRAKD");
        			String nmdos = ""+rs.getString("NMDOSTRAKD");
        			if(v==null) {
        				v = new Vector();
        				li = v.listIterator();
        			}
        			if(!Checker.isStringNullOrEmpty(nodos)) {
        				li.add(kdpst+"`"+nodos+"`"+nmdos);	
        			}
        		}
        		
        		if(v!=null && v.size()>0) {
        			v = Tool.removeDuplicateFromVector(v);
        			li = v.listIterator();
        			//cek trakd
        			stmt = con.prepareStatement("SELECT THSMSTRAKD,SURAT_TUGAS from TRAKD where KDPSTTRAKD=? and NODOSTRAKD=? and SURAT_TUGAS is not null order by THSMSTRAKD desc limit 1");
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				String kdpst = st.nextToken();
        				String nodos = st.nextToken();
            			String nmdos = st.nextToken();
            			stmt.setString(1, kdpst);
            			stmt.setString(2, nodos);
            			rs = stmt.executeQuery();
            			if(rs.next()) {
            				String thsms = rs.getString(1);
            				String surat = rs.getString(2);
            				li.set(brs+"`"+thsms+"`"+surat);
            			}
            			else {
            				li.set(brs+"`null`null");
            			}
        			}
        			
        			//cek trakd_epbed yg masih null
        			//surat untuk dosen&kdpst yg sama  akan sama antara kedua tabel 
        			//cek trakd
        			li = v.listIterator();
        			stmt = con.prepareStatement("SELECT THSMSTRAKD,SURAT_TUGAS from TRAKD_EPSBED where KDPSTTRAKD=? and NODOSTRAKD=? and SURAT_TUGAS is not null order by THSMSTRAKD desc limit 1");
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				String kdpst = st.nextToken();
        				String nodos = st.nextToken();
            			String nmdos = st.nextToken();
            			String thsms = st.nextToken();
            			String surat = st.nextToken();
            			if(!Checker.isStringNullOrEmpty(thsms)) {
            				if(thsms.compareToIgnoreCase(target_thsms)!=0) {
            					stmt.setString(1, kdpst);
                    			stmt.setString(2, nodos);
                    			rs = stmt.executeQuery();
                    			if(rs.next()) {
                    				thsms = rs.getString(1);
                    				surat = rs.getString(2);
                    				li.set(kdpst+"`"+nodos+"`"+nmdos+"`"+thsms+"`"+surat);
                    			}
            				}
            			}
            			else {
            				stmt.setString(1, kdpst);
                			stmt.setString(2, nodos);
                			rs = stmt.executeQuery();
                			if(rs.next()) {
                				thsms = rs.getString(1);
                				surat = rs.getString(2);
                				li.set(kdpst+"`"+nodos+"`"+nmdos+"`"+thsms+"`"+surat);
                			}
            			}
        			}
        			
        			//filter mana yg wajib diisi
        			li = v.listIterator();
        			stmt = con.prepareStatement("SELECT THSMSTRAKD from TRAKD_EPSBED where THSMSTRAKD=? and KDPSTTRAKD=? and NODOSTRAKD=?");
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				String kdpst = st.nextToken();
        				String nodos = st.nextToken();
            			String nmdos = st.nextToken();
            			String thsms = st.nextToken();
            			String surat = st.nextToken();
            			stmt.setString(1, target_thsms);
            			stmt.setString(2, kdpst);
            			stmt.setString(3, nodos);
            			rs = stmt.executeQuery();
            			if(rs.next()) {
            				li.set(brs+"`yes");
            			}
            			else {
            				li.set(brs+"`no");
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
    	}
    	
    	//System.out.println(v.size());
    	return v;
    }

    
}

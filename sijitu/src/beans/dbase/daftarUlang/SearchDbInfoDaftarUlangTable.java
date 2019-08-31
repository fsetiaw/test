package beans.dbase.daftarUlang;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;

import beans.dbase.SearchDb;
import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.DateFormater;
import beans.tools.Tool;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.EncodingException;

import java.sql.Timestamp;

/**
 * Session Bean implementation class SearchDbInfoDaftarUlangTable
 */
@Stateless
@LocalBean
public class SearchDbInfoDaftarUlangTable extends SearchDb {
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
    public SearchDbInfoDaftarUlangTable() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbInfoDaftarUlangTable(String operatorNpm) {
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
    public SearchDbInfoDaftarUlangTable(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public Vector getListMhsYgSdhMengajukanPengajuanDaftarUlang(String target_thsms,Vector v_scope_id) { // belum tentu all approved
    	Vector vf = null;
    	ListIterator lif = null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//	get
        		stmt = con.prepareStatement("select * from DAFTAR_ULANG INNER JOIN CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and DAFTAR_ULANG.ID_OBJ=? order by SMAWLMSMHS,NMMHSMSMHS");
        		ListIterator li = v_scope_id.listIterator();
        		while(li.hasNext()) {
        			if(vf==null) {
        				vf = new Vector();
        				lif = vf.listIterator();
        			}
        			String brs = (String)li.next();
        			//System.out.println(brs);
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String kmp = st.nextToken();
        			if(st.hasMoreTokens()) {
        				lif.add(kmp);
        				Vector vtmp = new Vector();
        				ListIterator litmp = vtmp.listIterator();
        				while(st.hasMoreTokens()) {
            				String idobj = st.nextToken();
            				//lif.add(idobj);
            				//System.out.println("add idobj="+idobj);
            				stmt.setString(1, target_thsms);
            				stmt.setInt(2, Integer.parseInt(idobj));
            				rs = stmt.executeQuery();
            				String list_mhs = null;
            				while(rs.next()) {
            					if(list_mhs==null) {
            						list_mhs = new String();
            					}
            					String id = ""+rs.getLong("ID");
            					String smawl = ""+rs.getString("SMAWLMSMHS");
            					String npmhs = ""+rs.getString("NPMHSMSMHS");
            					String nmmhs = ""+rs.getString("NMMHSMSMHS");
            					list_mhs = list_mhs+"`"+id+"`"+smawl+"`"+npmhs+"`"+nmmhs;
            				}
            				if(list_mhs!=null) {
            					litmp.add(idobj);
            					litmp.add(list_mhs);
            					//System.out.println("add mhs="+list_mhs);
            				}
            				else {
            					litmp.add(idobj);
            					litmp.add("null");
            					//System.out.println("add mhs= null");
            				}
            				
            			}
        				lif.add(vtmp);
        			}
        			else {
        				//kaloo ngga ada list idobj ignoer saja
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
    	
    	return vf;
    }
    
    public Vector getListMhsYgSdhMengajukanPengajuanDaftarUlang(String target_thsms,String target_kdpst) { // belum tentu all approved
    	/*
    	 * digunakan untuk cetak kartu ujian
    	 */

    	Vector vf = null;
    	ListIterator lif = null;
    	if(!Checker.isStringNullOrEmpty(target_kdpst)) {
    		//System.out.println("target_kdpst = "+target_kdpst);
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//	get
        		if(target_kdpst.equalsIgnoreCase("all")) {
        			stmt = con.prepareStatement("select KDPST,NPMHS,NMMHSMSMHS,ALL_APPROVED,NIMHSMSMHS from DAFTAR_ULANG INNER JOIN CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? order by KDPST,NMMHSMSMHS");
        			stmt.setString(1, target_thsms);
        		}
        		else {
        			stmt = con.prepareStatement("select KDPST,NPMHS,NMMHSMSMHS,ALL_APPROVED,NIMHSMSMHS from DAFTAR_ULANG INNER JOIN CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and KDPST=? order by KDPST,NMMHSMSMHS");
        			stmt.setString(1, target_thsms);
    				stmt.setString(2, target_kdpst);
        		}
        		rs = stmt.executeQuery();
				if(rs.next()) {
					vf = new Vector();
					lif = vf.listIterator();
					do {
						String kdpst = rs.getString(1);
						String npmhs = rs.getString(2);
						String nmmhs = rs.getString(3);
						String approved = rs.getString(4);
						String nimhs = rs.getString(5);
						lif.add(kdpst+"`"+npmhs+"`"+nmmhs+"`"+nimhs+"`"+approved);
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
    	}
    	
    	return vf;
    }
    
    
    public String getInfoDaftarUlangFilterByScope(String thsms,Vector vFilterScope) {
    	Vector v = new Vector();
    	String finalList="";
    	ListIterator li = v.listIterator();
    	String listThsmsNpmhs = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select * from DAFTAR_ULANG INNER JOIN CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? order by KDPST,NPMHS");
    		stmt.setString(1,thsms);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdpst = rs.getString("KDPST");
    			String npmhs = rs.getString("NPMHS");
    			String nimhs = rs.getString("NIMHSMSMHS");
    			String nmmhs = rs.getString("NMMHSMSMHS");
    			String smawl = rs.getString("SMAWLMSMHS");
    			String stpid = rs.getString("STPIDMSMHS");
    			String tglAju = rs.getString("TGL_PENGAJUAN");
    			String tknApr = rs.getString("TOKEN_APPROVAL");
    			String idObj = ""+rs.getInt("ID_OBJ");
    			boolean approved = rs.getBoolean("ALL_APPROVED");
    			String tkn_jab_apr_needed = ""+rs.getString("TKN_JABATAN_APPROVAL");
    			li.add(kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stpid+"$"+tglAju+"$"+tknApr+"$"+idObj+"$"+tkn_jab_apr_needed+"$"+approved);
    			//System.out.println("add="+kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stpid+"$"+tglAju+"$"+tknApr+"$"+idObj);
    		}
    		
    		
    		//filter dengan scope user
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"$");
    			String kdpst=st.nextToken();
    			String npmhs=st.nextToken();
    			String nimhs=st.nextToken();
    			String nmmhs=st.nextToken();
    			String smawl=st.nextToken();
    			String stpid=st.nextToken();
    			String tglAju=st.nextToken();
    			String tknApr=st.nextToken();
    			String idObj=st.nextToken();
    			String tkn_jab_apr_needed = st.nextToken();
    			String approved=st.nextToken();
    			ListIterator lif = vFilterScope.listIterator();
    			boolean match = false;
    			while(lif.hasNext() && !match) {
    				String brs1 = (String)lif.next();
    				////System.out.println("lif=="+brs1);
    				st = new StringTokenizer(brs1);
    				String id_obj_scope = st.nextToken();
    				/*
    				 * filter diganti dgn obj id ajah
    				 */
    				//String kdpst1=st.nextToken();
    				//if(kdpst.equalsIgnoreCase(kdpst1)) {
    				
    				if(idObj.equalsIgnoreCase(id_obj_scope)) {
    					match = true;
    					////System.out.println(idObj+" <> "+id_obj_scope);
    				}
    			}
    			if(!match) {
    				li.remove();
    			}
    		}
    		/*
    		boolean first = true;
    		stmt=con.prepareStatement("select * from DAFTAR_ULANG_RULES where THSMS=? and KDPST=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			////System.out.println("bss="+brs);
    			StringTokenizer st = new StringTokenizer(brs,"$");
    			String kdpst=st.nextToken();
    			String npmhs=st.nextToken();
    			String nimhs=st.nextToken();
    			String nmmhs=st.nextToken();
    			String smawl=st.nextToken();
    			String stpid=st.nextToken();
    			String tglAju=st.nextToken();
    			String tknApr=st.nextToken();
    			String idObj=st.nextToken();
    			stmt.setString(1,thsms);
    			stmt.setString(2,kdpst);
    			
    			
    			rs = stmt.executeQuery();
    			rs.next();//kalo error berarti tabelnya belum diinput utk thsms terkait
    			String tknVerObj = rs.getString("TKN_VERIFICATOR");
    			String urutan = ""+rs.getBoolean("URUTAN");
    			if(first) {
    				first = false;
    				finalList = brs+"$"+tknVerObj+"$"+urutan;
    			}
    			else {
    				finalList = finalList+brs+"$"+tknVerObj+"$"+urutan;
    			}
    			if(li.hasNext()) {
    				finalList=finalList+"$";
    			}
    			//li.set(brs+"$"+tknVerObj+"$"+urutan);
    		}
    		*/
    		stmt = con.prepareStatement("select NMPSTMSPST from MSPST where KDPSTMSPST=?");
    		String nuFinalList = "";
    		boolean first = true;
    		StringTokenizer st  = new StringTokenizer(finalList,"$");
    		while(st.hasMoreTokens()) {
    			String kdpst=st.nextToken();
    			String npmhs=st.nextToken();
    			String nimhs=st.nextToken();
    			String nmmhs=st.nextToken();
    			String smawl=st.nextToken();
    			String stpid=st.nextToken();
    			String tglAju=st.nextToken();
    			String tknApr=st.nextToken();
    			String idObj=st.nextToken();
    			String tkn_jab_apr_needed=st.nextToken();
    			String approved=st.nextToken();
    			stmt.setString(1,kdpst);
    			rs=stmt.executeQuery();
    			rs.next();
    			String nmpst = rs.getString("NMPSTMSPST");
    			if(first) {
    				first = false;
    				nuFinalList = nmpst+"$"+kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stpid+"$"+tglAju+"$"+tknApr+"$"+tkn_jab_apr_needed+"$"+approved+"$"+idObj;
    			}
    			else {
    				nuFinalList = nuFinalList+nmpst+"$"+kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stpid+"$"+tglAju+"$"+tknApr+"$"+tkn_jab_apr_needed+"$"+approved+"$"+idObj;
    			}
    			if(st.hasMoreTokens()) {
    				nuFinalList=nuFinalList+"$";
    			}
    		}
    		finalList=nuFinalList;
    		
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
    	return finalList;
    }
    /*
     * deprecated pake v1
     */
    public String getInfoDaftarUlangFilterByScopeAndUnapproved(String thsms,Vector vFilterScope) {
    	Vector v = new Vector();
    	String finalList="";
    	ListIterator li = v.listIterator();
    	String listThsmsNpmhs = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select * from DAFTAR_ULANG INNER JOIN CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? order by KDPST,NPMHS");
    		stmt.setString(1,thsms);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdpst = rs.getString("KDPST");
    			String npmhs = rs.getString("NPMHS");
    			String nimhs = rs.getString("NIMHSMSMHS");
    			String nmmhs = rs.getString("NMMHSMSMHS");
    			String smawl = rs.getString("SMAWLMSMHS");
    			String stpid = rs.getString("STPIDMSMHS");
    			String tglAju = rs.getString("TGL_PENGAJUAN");
    			String tknApr = rs.getString("TOKEN_APPROVAL");
    			String idObj = ""+rs.getInt("ID_OBJ");
    			li.add(kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stpid+"$"+tglAju+"$"+tknApr+"$"+idObj);
    		}
    		//filter yg blum approved only
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"$");
    			String kdpst=st.nextToken();
    			String npmhs=st.nextToken();
    			String nimhs=st.nextToken();
    			String nmmhs=st.nextToken();
    			String smawl=st.nextToken();
    			String stpid=st.nextToken();
    			String tglAju=st.nextToken();
    			String tknApr=st.nextToken();
    			String idObj=st.nextToken();
    			String msg = Checker.sudahDaftarUlang(kdpst, npmhs, thsms);
    			if(msg==null) {
    				li.remove();
    			}
    		}
    		//filter dengan scope user
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"$");
    			String kdpst=st.nextToken();
    			String npmhs=st.nextToken();
    			String nimhs=st.nextToken();
    			String nmmhs=st.nextToken();
    			String smawl=st.nextToken();
    			String stpid=st.nextToken();
    			String tglAju=st.nextToken();
    			String tknApr=st.nextToken();
    			String idObj=st.nextToken();
    			ListIterator lif = vFilterScope.listIterator();
    			boolean match = false;
    			while(lif.hasNext() && !match) {
    				String brs1 = (String)lif.next();
    				////System.out.println("lif=="+brs1);
    				st = new StringTokenizer(brs1);
    				String id_obj_scope = st.nextToken();
    				/*
    				 * filter diganti dgn obj id ajah
    				 */
    				//String kdpst1=st.nextToken();
    				//if(kdpst.equalsIgnoreCase(kdpst1)) {
    				
    				if(idObj.equalsIgnoreCase(id_obj_scope)) {
    					match = true;
    					////System.out.println(idObj+" <> "+id_obj_scope);
    				}
    			}
    			if(!match) {
    				li.remove();
    			}
    		}
    		
    		boolean first = true;
    		stmt=con.prepareStatement("select * from DAFTAR_ULANG_RULES where THSMS=? and KDPST=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			////System.out.println("bss="+brs);
    			StringTokenizer st = new StringTokenizer(brs,"$");
    			String kdpst=st.nextToken();
    			String npmhs=st.nextToken();
    			String nimhs=st.nextToken();
    			String nmmhs=st.nextToken();
    			String smawl=st.nextToken();
    			String stpid=st.nextToken();
    			String tglAju=st.nextToken();
    			String tknApr=st.nextToken();
    			String idObj=st.nextToken();
    			stmt.setString(1,thsms);
    			stmt.setString(2,kdpst);
    			
    			
    			rs = stmt.executeQuery();
    			rs.next();//kalo error berarti tabelnya belum diinput utk thsms terkait
    			String tknVerObj = rs.getString("TKN_VERIFICATOR");
    			String urutan = ""+rs.getBoolean("URUTAN");
    			if(first) {
    				first = false;
    				finalList = brs+"$"+tknVerObj+"$"+urutan;
    			}
    			else {
    				finalList = finalList+brs+"$"+tknVerObj+"$"+urutan;
    			}
    			if(li.hasNext()) {
    				finalList=finalList+"$";
    			}
    			//li.set(brs+"$"+tknVerObj+"$"+urutan);
    		}
    		stmt = con.prepareStatement("select NMPSTMSPST from MSPST where KDPSTMSPST=?");
    		String nuFinalList = "";
    		first = true;
    		StringTokenizer st  = new StringTokenizer(finalList,"$");
    		while(st.hasMoreTokens()) {
    			String kdpst=st.nextToken();
    			String npmhs=st.nextToken();
    			String nimhs=st.nextToken();
    			String nmmhs=st.nextToken();
    			String smawl=st.nextToken();
    			String stpid=st.nextToken();
    			String tglAju=st.nextToken();
    			String tknApr=st.nextToken();
    			String idObj=st.nextToken();
    			String tknVerObj=st.nextToken();
    			String urutan=st.nextToken();
    			stmt.setString(1,kdpst);
    			rs=stmt.executeQuery();
    			rs.next();
    			String nmpst = rs.getString("NMPSTMSPST");
    			if(first) {
    				first = false;
    				nuFinalList = nmpst+"$"+kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stpid+"$"+tglAju+"$"+tknApr+"$"+tknVerObj+"$"+urutan+"$"+idObj;
    			}
    			else {
    				nuFinalList = nuFinalList+nmpst+"$"+kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stpid+"$"+tglAju+"$"+tknApr+"$"+tknVerObj+"$"+urutan+"$"+idObj;
    			}
    			if(st.hasMoreTokens()) {
    				nuFinalList=nuFinalList+"$";
    			}
    		}
    		finalList=nuFinalList;
    		
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
    	return finalList;
    }
    
    
    /*
     * deprecated PAKE VERSI 1
     * ada penambahan kode kampus domisili
     */
    public Vector getInfoDaftarUlangFilterByScopeFinale(String target_thsms,Vector v_scope_id, boolean use_approval) {
    	/*
    	 * DIGABUNGKAN
    	 * getInfoDaftarUlangFilterByScope_v1(String target_thsms,Vector v_scope_id)
    	 */
    	ListIterator li = v_scope_id.listIterator();
    	Vector vcombine = (Vector)li.next();
    	li = vcombine.listIterator();
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		//	get
    		if(use_approval) {
    			stmt = con.prepareStatement("select * from DAFTAR_ULANG INNER JOIN CIVITAS on (DAFTAR_ULANG.ID_OBJ=CIVITAS.ID_OBJ) and (NPMHS=NPMHSMSMHS) where THSMS=? and DAFTAR_ULANG.ID_OBJ=? and ALL_APPROVED=? order by NPMHS");
    		}
    		else {
    			stmt = con.prepareStatement("select * from DAFTAR_ULANG INNER JOIN CIVITAS on (DAFTAR_ULANG.ID_OBJ=CIVITAS.ID_OBJ) and (NPMHS=NPMHSMSMHS) where THSMS=? and DAFTAR_ULANG.ID_OBJ=? order by NPMHS");
    		}
    		
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kmp = st.nextToken();
    			String list_npm = "";
    			
    			Vector vprodi = new Vector();
    			ListIterator lip = vprodi.listIterator();
    			while(st.hasMoreTokens()) {
    				String idobj = st.nextToken();
    				
        			stmt.setString(1,target_thsms);
        			stmt.setInt(2, Integer.parseInt(idobj));
        			if(use_approval) {
        				stmt.setBoolean(3, false);	
        			}
        			
        			rs = stmt.executeQuery();
        			Vector vmhs = new Vector();
        			ListIterator lim = vmhs.listIterator();
        			String tmp = "";
        			while(rs.next()) {
        				String kdpst = ""+rs.getString("KDPST");
        				String npmhs = ""+rs.getString("NPMHS");
        				String nimhs = ""+rs.getString("NIMHSMSMHS");
        				String nmmhs = ""+rs.getString("NMMHSMSMHS");
        				String stpid = ""+rs.getString("STPIDMSMHS");
        				String asnim = ""+rs.getString("ASNIMMSMHS");
        				if(Checker.isStringNullOrEmpty(asnim)) {
        					asnim="N/A";
        				}
        				if(Checker.isStringNullOrEmpty(nimhs)) {
        					nimhs="null";
        				}
        				String reqdt = ""+rs.getTimestamp("TGL_PENGAJUAN");
        				String tkn_apr_hist = ""+rs.getString("TOKEN_APPROVAL");
        				String tkn_id_approval = ""+rs.getString("TKN_ID_APPROVAL");//fix = yang diperlukan (disiis pada proses pengajuan)
        				String tkn_job_approval = ""+rs.getString("TKN_JABATAN_APPROVAL");//idem ma diatas
        				String show_at_id = ""+rs.getBoolean("SHOW_AT_ID");
        				String show_at_creator = ""+rs.getBoolean("SHOW_AT_CREATOR");
        				//tmp = kdpst+"`"+npmhs+"`"+nmmhs+"`"+reqdt+"`"+tkn_apr_hist+"`"+tkn_id_approval+"`"+tkn_job_approval+"`"+show_at_id+"`"+show_at_creator;
        				tmp = kdpst+"`"+npmhs+"`"+nmmhs+"`"+reqdt+"`"+tkn_apr_hist+"`"+tkn_id_approval+"`"+tkn_job_approval+"`"+show_at_id+"`"+show_at_creator+"`"+stpid+"`"+asnim+"`"+nimhs;
        				tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "`");
        				lim.add(tmp);
        			}
        			lip.add(idobj);
        			lip.add(vmhs);
    			}
    			lif.add(kmp);
    			lif.add(vprodi);
    		}
    		//lif.add(vkmp);
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
    	return vf;
    }
    
    
    
    public Vector getInfoDaftarUlangFilterByScopeFinale_v1(String target_thsms,Vector v_scope_id, boolean use_approval) {
    	/*
    	 * DIGABUNGKAN
    	 * getInfoDaftarUlangFilterByScope_v1(String target_thsms,Vector v_scope_id)
    	 */
    	ListIterator li = v_scope_id.listIterator();
    	Vector vcombine = (Vector)li.next();
    	li = vcombine.listIterator();
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		//	get
    		if(use_approval) {
    			stmt = con.prepareStatement("select DAFTAR_ULANG.KDPST,NPMHS,NIMHSMSMHS,NMMHSMSMHS,STPIDMSMHS,ASNIMMSMHS,TGL_PENGAJUAN,TOKEN_APPROVAL,TKN_ID_APPROVAL,TKN_JABATAN_APPROVAL,SHOW_AT_ID,SHOW_AT_CREATOR,KODE_KAMPUS_DOMISILI from DAFTAR_ULANG INNER JOIN CIVITAS on (DAFTAR_ULANG.ID_OBJ=CIVITAS.ID_OBJ and NPMHS=NPMHSMSMHS) inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where THSMS=? and DAFTAR_ULANG.ID_OBJ=? and ALL_APPROVED=? order by KODE_KAMPUS_DOMISILI,NPMHS");
    		}
    		else {
    			stmt = con.prepareStatement("select DAFTAR_ULANG.KDPST,NPMHS,NIMHSMSMHS,NMMHSMSMHS,STPIDMSMHS,ASNIMMSMHS,TGL_PENGAJUAN,TOKEN_APPROVAL,TKN_ID_APPROVAL,TKN_JABATAN_APPROVAL,SHOW_AT_ID,SHOW_AT_CREATOR,KODE_KAMPUS_DOMISILI from DAFTAR_ULANG INNER JOIN CIVITAS on (DAFTAR_ULANG.ID_OBJ=CIVITAS.ID_OBJ and NPMHS=NPMHSMSMHS) inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where THSMS=? and DAFTAR_ULANG.ID_OBJ=? order by KODE_KAMPUS_DOMISILI,NPMHS");
    		}
    		
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kmp = st.nextToken();
    			String list_npm = "";
    			
    			Vector vprodi = new Vector();
    			ListIterator lip = vprodi.listIterator();
    			while(st.hasMoreTokens()) {
    				String idobj = st.nextToken();
    				
        			stmt.setString(1,target_thsms);
        			stmt.setInt(2, Integer.parseInt(idobj));
        			if(use_approval) {
        				stmt.setBoolean(3, false);	
        			}
        			
        			rs = stmt.executeQuery();
        			Vector vmhs = new Vector();
        			ListIterator lim = vmhs.listIterator();
        			String tmp = "";
        			while(rs.next()) {
        				String kdpst = ""+rs.getString("KDPST");
        				String npmhs = ""+rs.getString("NPMHS");
        				String nimhs = ""+rs.getString("NIMHSMSMHS");
        				String nmmhs = ""+rs.getString("NMMHSMSMHS");
        				String stpid = ""+rs.getString("STPIDMSMHS");
        				String asnim = ""+rs.getString("ASNIMMSMHS");
        				if(Checker.isStringNullOrEmpty(asnim)) {
        					asnim="N/A";
        				}
        				if(Checker.isStringNullOrEmpty(nimhs)) {
        					nimhs="null";
        				}
        				String reqdt = ""+rs.getTimestamp("TGL_PENGAJUAN");
        				String tkn_apr_hist = ""+rs.getString("TOKEN_APPROVAL");
        				String tkn_id_approval = ""+rs.getString("TKN_ID_APPROVAL");//fix = yang diperlukan (disiis pada proses pengajuan)
        				String tkn_job_approval = ""+rs.getString("TKN_JABATAN_APPROVAL");//idem ma diatas
        				String show_at_id = ""+rs.getBoolean("SHOW_AT_ID");
        				String show_at_creator = ""+rs.getBoolean("SHOW_AT_CREATOR");
        				String kd_kmp = ""+rs.getBoolean("KODE_KAMPUS_DOMISILI");
        				
        				//tmp = kdpst+"`"+npmhs+"`"+nmmhs+"`"+reqdt+"`"+tkn_apr_hist+"`"+tkn_id_approval+"`"+tkn_job_approval+"`"+show_at_id+"`"+show_at_creator;
        				tmp = kdpst+"`"+npmhs+"`"+nmmhs+"`"+reqdt+"`"+tkn_apr_hist+"`"+tkn_id_approval+"`"+tkn_job_approval+"`"+show_at_id+"`"+show_at_creator+"`"+stpid+"`"+asnim+"`"+nimhs+"`"+kd_kmp;
        				tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "`");
        				lim.add(tmp);
        			}
        			lip.add(idobj);
        			lip.add(vmhs);
    			}
    			lif.add(kmp);
    			lif.add(vprodi);
    		}
    		//lif.add(vkmp);
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
    	return vf;
    }
    
    public Vector getInfoDaftarUlangFilterByScopeAndUnapproved_v1(String target_thsms,Vector v_scope_id) {
    	/*
    	 * structur nya harus sama
    	 * getInfoDaftarUlangFilterByScope_v1(String target_thsms,Vector v_scope_id)
    	 */
    	
    	/*
    	ListIterator li = v_scope_id.listIterator();
    	Vector vcombine = (Vector)li.next();
    	li = vcombine.listIterator();
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select * from DAFTAR_ULANG INNER JOIN CIVITAS on (DAFTAR_ULANG.ID_OBJ=CIVITAS.ID_OBJ) and (NPMHS=NPMHSMSMHS) where THSMS=? and DAFTAR_ULANG.ID_OBJ=? and ALL_APPROVED=? order by NPMHS");
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kmp = st.nextToken();
    			String list_npm = "";
    			
    			Vector vprodi = new Vector();
    			ListIterator lip = vprodi.listIterator();
    			while(st.hasMoreTokens()) {
    				String idobj = st.nextToken();
    				
        			stmt.setString(1,target_thsms);
        			stmt.setInt(2, Integer.parseInt(idobj));
        			stmt.setBoolean(3, false);
        			rs = stmt.executeQuery();
        			Vector vmhs = new Vector();
        			ListIterator lim = vmhs.listIterator();
        			String tmp = "";
        			while(rs.next()) {
        				String kdpst = ""+rs.getString("KDPST");
        				String npmhs = ""+rs.getString("NPMHS");
        				String nimhs = ""+rs.getString("NIMHSMSMHS");
        				String nmmhs = ""+rs.getString("NMMHSMSMHS");
        				String stpid = ""+rs.getString("STPIDMSMHS");
        				String asnim = ""+rs.getString("ASNIMMSMHS");
        				if(Checker.isStringNullOrEmpty(asnim)) {
        					asnim="N/A";
        				}
        				if(Checker.isStringNullOrEmpty(nimhs)) {
        					nimhs="null";
        				}
        				String reqdt = ""+rs.getTimestamp("TGL_PENGAJUAN");
        				String tkn_apr_hist = ""+rs.getString("TOKEN_APPROVAL");
        				String tkn_id_approval = ""+rs.getString("TKN_ID_APPROVAL");//fix = yang diperlukan (disiis pada proses pengajuan)
        				String tkn_job_approval = ""+rs.getString("TKN_JABATAN_APPROVAL");//idem ma diatas
        				String show_at_id = ""+rs.getBoolean("SHOW_AT_ID");
        				String show_at_creator = ""+rs.getBoolean("SHOW_AT_CREATOR");
        				//tmp = kdpst+"`"+npmhs+"`"+nmmhs+"`"+reqdt+"`"+tkn_apr_hist+"`"+tkn_id_approval+"`"+tkn_job_approval+"`"+show_at_id+"`"+show_at_creator;
        				tmp = kdpst+"`"+npmhs+"`"+nmmhs+"`"+reqdt+"`"+tkn_apr_hist+"`"+tkn_id_approval+"`"+tkn_job_approval+"`"+show_at_id+"`"+show_at_creator+"`"+stpid+"`"+asnim+"`"+nimhs;
        				tmp = tmp.replace("``", "`null`");
        				tmp = tmp.replace("` `", "`null`");
        				lim.add(tmp);
        			}
        			lip.add(idobj);
        			lip.add(vmhs);
    			}
    			lif.add(kmp);
    			lif.add(vprodi);
    		}
    		//lif.add(vkmp);
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
    	Vector vf = getInfoDaftarUlangFilterByScopeFinale(target_thsms,v_scope_id, true);
    	return vf;
    }
    
    public Vector getInfoDaftarUlangFilterByScope_v1(String target_thsms,Vector v_scope_id) {
    	/*
    	 * FUNDGSI INI DIPANGGIL DARI HOME NOTIFIKASI
    	 * JADI HANYA YG BELUM APPROVED SAJA
    	 */
    	/*
    	ListIterator li = v_scope_id.listIterator();
    	Vector vcombine = (Vector)li.next();
    	li = vcombine.listIterator();
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		//stmt = con.prepareStatement("select * from DAFTAR_ULANG INNER JOIN CIVITAS on (DAFTAR_ULANG.ID_OBJ=CIVITAS.ID_OBJ) and (NPMHS=NPMHSMSMHS) where THSMS=? and DAFTAR_ULANG.ID_OBJ=? and ALL_APPROVED=? order by NPMHS");
    		stmt = con.prepareStatement("select * from DAFTAR_ULANG INNER JOIN CIVITAS on (DAFTAR_ULANG.ID_OBJ=CIVITAS.ID_OBJ) and (NPMHS=NPMHSMSMHS) where THSMS=? and DAFTAR_ULANG.ID_OBJ=? order by NPMHS");
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kmp = st.nextToken();
    			String list_npm = "";
    			
    			Vector vprodi = new Vector();
    			ListIterator lip = vprodi.listIterator();
    			while(st.hasMoreTokens()) {
    				String idobj = st.nextToken();
    				
        			stmt.setString(1,target_thsms);
        			stmt.setInt(2, Integer.parseInt(idobj));
        			//stmt.setBoolean(3, false);
        			rs = stmt.executeQuery();
        			Vector vmhs = new Vector();
        			ListIterator lim = vmhs.listIterator();
        			String tmp = "";
        			while(rs.next()) {
        				String kdpst = ""+rs.getString("KDPST");
        				String npmhs = ""+rs.getString("NPMHS");
        				String nimhs = ""+rs.getString("NIMHSMSMHS");
        				String nmmhs = ""+rs.getString("NMMHSMSMHS");
        				String stpid = ""+rs.getString("STPIDMSMHS");
        				String asnim = ""+rs.getString("ASNIMMSMHS");
        				if(Checker.isStringNullOrEmpty(asnim)) {
        					asnim="N/A";
        				}
        				if(Checker.isStringNullOrEmpty(nimhs)) {
        					nimhs="null";
        				}
        				String reqdt = ""+rs.getTimestamp("TGL_PENGAJUAN");
        				String tkn_apr_hist = ""+rs.getString("TOKEN_APPROVAL");
        				String tkn_id_approval = ""+rs.getString("TKN_ID_APPROVAL");//fix = yang diperlukan (disiis pada proses pengajuan)
        				String tkn_job_approval = ""+rs.getString("TKN_JABATAN_APPROVAL");//idem ma diatas
        				String show_at_id = ""+rs.getBoolean("SHOW_AT_ID");
        				String show_at_creator = ""+rs.getBoolean("SHOW_AT_CREATOR");
        				tmp = kdpst+"`"+npmhs+"`"+nmmhs+"`"+reqdt+"`"+tkn_apr_hist+"`"+tkn_id_approval+"`"+tkn_job_approval+"`"+show_at_id+"`"+show_at_creator+"`"+stpid+"`"+asnim+"`"+nimhs;
        				tmp = tmp.replace("``", "`null`");
        				tmp = tmp.replace("` `", "`null`");
        				lim.add(tmp);
        			}
        			lip.add(idobj);
        			lip.add(vmhs);
    			}
    			lif.add(kmp);
    			lif.add(vprodi);
    		}
    		//lif.add(vkmp);
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
    	/* STRUKTUR
    	 * 		lif.add(kmp);String
    			lif.add(vprodi);Vector
    				lip.add(idobj);String
        			lip.add(vmhs);Vector
    	 */
    	Vector vf = getInfoDaftarUlangFilterByScopeFinale(target_thsms,v_scope_id, false);
    	return vf;
    }
    
    public String getDistinctNicknameDaftarUlangApprovee(String thsms) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String list_tkn_approvee = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select TKN_VERIFICATOR from DAFTAR_ULANG_RULES where THSMS=?");
    		stmt.setString(1,thsms);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String tkn_approvee = rs.getString(1);
    			StringTokenizer st = new StringTokenizer(tkn_approvee,",");
    			while(st.hasMoreTokens()) {
    				li.add(st.nextToken());
    			}
    		}
    		v = Tool.removeDuplicateFromVector(v);
    		li = v.listIterator();
    		while(li.hasNext()) {
    			list_tkn_approvee = list_tkn_approvee+","+(String)li.next();
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
    	return list_tkn_approvee;
    }
    
    public Vector getListMhsYgSudahDaftarUlangAndApproved(String thsms, String kdpst) {
    	/*
    	 * BELUM BERDASARKAN KODE KAMPUS
    	 * PALING @ JSPNYA DIKASIH MENU TAB PILIHAN KAMPUS MANA
    	 */
    	//Vector vListApproveeNickname = new Vector();
    	Vector vListMhs = new Vector();
    	ListIterator li = null;
    	String list_tkn_approvee = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get approvee
    		stmt = con.prepareStatement("select TKN_VERIFICATOR from DAFTAR_ULANG_RULES where THSMS=? and KDPST=?");
    		stmt.setString(1,thsms);
    		stmt.setString(2,kdpst);
    		//stmt.setString(3,kode_kmp);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			list_tkn_approvee = rs.getString(1);
    		}
    		////System.out.println("list_tkn_approvee="+list_tkn_approvee);
    		//2. get list npm  from daftar ulang
    		vListMhs = new Vector();
    		li = vListMhs.listIterator();
    		stmt = con.prepareStatement("select NPMHS,TOKEN_APPROVAL from DAFTAR_ULANG where THSMS=? and KDPST=?");
    		stmt.setString(1,thsms);
    		stmt.setString(2,kdpst);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String npmhs = rs.getString(1);
    			////System.out.println("npmhs1="+npmhs);
    			String tkn_approval_approved = rs.getString(2);
    			boolean approved = true;
    			StringTokenizer st = new StringTokenizer(list_tkn_approvee,",");
    			while(st.hasMoreTokens()) {
    				String nick_approvee = st.nextToken();
    				if(!tkn_approval_approved.contains(nick_approvee)) {
    					approved = false;
    				}
    			}
    			if(approved) {
    				li.add(npmhs);
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
    	return vListMhs;
    }
    
    public String sudahMengajukanHeregistrasi_deprecated(String thsms, String npmhs) {
    	/*
    	 * RETURN NULL BILA BELUM MENGAJUKAN
    	 * RETURN APPROVED BILA ALL_APPROVED
    	 * RETURN INPROGRESS
    	 */
    	String msg = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get approvee
    		stmt = con.prepareStatement("select ALL_APPROVED from DAFTAR_ULANG where THSMS=? and NPMHS=?");
    		stmt.setString(1,thsms);
    		stmt.setString(2,npmhs);
    		//stmt.setString(3,kode_kmp);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			boolean approved = rs.getBoolean(1);
    			if(approved) {
    				msg = new String("approved");
    			}
    			else {
    				msg = new String("inprogress");
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
    	return msg;
    }
    

    
    public Vector cekStatusPengajuan(String thsms, String npmhs, String kdpst, String kode_kmp) {
    	//System.out.println("==============================================");
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String msg = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cek daftar ulang apa ada approval
    		stmt = con.prepareStatement("select TOKEN_APPROVAL,ALL_APPROVED from DAFTAR_ULANG where THSMS=? and KDPST=? and NPMHS=?");
    		stmt.setString(1, thsms);
    		stmt.setString(2, kdpst);
    		stmt.setString(3, npmhs);
    		rs = stmt.executeQuery();
    		rs.next();
    		String tkn_approval = rs.getString(1);
    		//System.out.println("tkn_approval="+tkn_approval);
    		boolean all_approved = rs.getBoolean(2);
    		//get rules
    		stmt = con.prepareStatement("select TKN_JABATAN_VERIFICATOR,TKN_VERIFICATOR_ID,URUTAN from HEREGISTRASI_RULES where THSMS=? and KDPST=? and KODE_KAMPUS=?");
    		stmt.setString(1,thsms);
    		stmt.setString(2,kdpst);
    		stmt.setString(3,kode_kmp);
    		rs = stmt.executeQuery();
    		String tkn_job = null;
			String tkn_id = null;
			boolean urutan = false;
    		if(rs.next()) {
    			tkn_job = rs.getString(1);
    			tkn_id = rs.getString(2);
    			urutan = rs.getBoolean(3);
    			//default urutan
    			li.add(tkn_job+"`"+tkn_id);//baris pertama: required approval dari tabel rule
    			//System.out.println("add="+tkn_job+"`"+tkn_id);
    		}
    		else {
    			li.add("[null]`[null]");
    			//System.out.println("add=[null]`[null]");
    		}
    		
    		//cek berdasar riwayat approval
    		if(tkn_approval==null || Checker.isStringNullOrEmpty(tkn_approval)) {
    			/*
    			 * DO NOTHING KRAN VALUE = li.add(tkn_job+"`"+tkn_id);
    			 */
    		}
    		else {
    			//#0000712100001#KTU#2016-07-03 14:26:18.298
    			//System.out.println("tkn_job="+tkn_job);
    			//System.out.println("tkn_approval="+tkn_approval);
    			//[KTU][Ka. BAUK]
    			String list_approvee = new String(tkn_job);
    			list_approvee = list_approvee.replace("][", "`");
    			list_approvee = list_approvee.replace("[", "");
    			list_approvee = list_approvee.replace("]", "");
    			String list_id = new String(tkn_id);
    			list_id = list_id.replace("][", "`");
    			list_id = list_id.replace("[", "");
    			list_id = list_id.replace("]", "");
    			
    			StringTokenizer st = new StringTokenizer(list_approvee,"`");
    			StringTokenizer st1 = new StringTokenizer(list_id,"`");
    			String jabatan_approvee = null;
    			String id_approvee = null;
    			boolean match = true;
    			int counter = 0;
    			while(st.hasMoreTokens() && match) {
    				jabatan_approvee = st.nextToken();
    				id_approvee = st1.nextToken();
    				counter++;
    				//bandingkan dengan
    				StringTokenizer st2 = new StringTokenizer(tkn_approval,"#");
    				match = false; 
        			for(int i=0; i<counter && st2.hasMoreTokens();i++) {
        				String npm_approvee = st2.nextToken();
            			String jabatan_approved = st2.nextToken();
            			String time_stamp = st2.nextToken();
            			if((i==counter-1)&&jabatan_approved.equalsIgnoreCase(jabatan_approved)) {
            				match = true;
            				li.add("["+jabatan_approved+"]");//1 TOKEN BILA APPROVED BY
            				//System.out.println("add=["+jabatan_approved+"]");
            			}
        			}
    			}
    			if(match && !st.hasMoreTokens()) {
    				//System.out.println("all_approved");
    				li.add("done");//BILA SELESAI
    				//System.out.println("add=done");
    			}
    			else {
    				//System.out.println(jabatan_approvee+"-"+id_approvee);
    				li.add("["+jabatan_approvee+"]`["+id_approvee+"]");//2 TKN - BILA MENUNGGU PERSETUJUAN
    				//System.out.println("add=["+jabatan_approvee+"]`["+id_approvee+"]");
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
    	//System.out.println("===========================end=================");
    	return v;
    }
    
    

}

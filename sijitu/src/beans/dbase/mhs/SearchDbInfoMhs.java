package beans.dbase.mhs;

import beans.dbase.SearchDb;
import beans.tools.AddHocFunction;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.Vector;
import java.util.StringTokenizer;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class SearchDbInfoMhs
 */
@Stateless
@LocalBean
public class SearchDbInfoMhs extends SearchDb {
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
    public SearchDbInfoMhs() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbInfoMhs(String operatorNpm) {
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
    public SearchDbInfoMhs(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public String getNmmhsGiven(String listNpmByComa, String kdpst) {
    	//Vector v = null;
    	
    	StringTokenizer st = null;
    	String listBaru="";
    	try {
    		if(listNpmByComa!=null && (st = new StringTokenizer(listNpmByComa,",")).countTokens()>0) {
    			
    		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		//	get
    			stmt = con.prepareStatement("select NMMHSMSMHS from CIVITAS where KDPSTMSMHS=? and NPMHSMSMHS=?");
    			while(st.hasMoreTokens()) {
    				String npmhs = st.nextToken();
    				stmt.setString(1, kdpst);
    				stmt.setString(2, npmhs);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				String nmmhs = rs.getString("NMMHSMSMHS");
        				listBaru=listBaru+npmhs+"-"+nmmhs;
        			}
        			else {
        				listBaru=listBaru+npmhs+"-ERROR TDK ADA NAMA";
        			}
        			if(st.hasMoreTokens()) {
        				listBaru=listBaru+",";
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
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	}   
    	return listBaru;
    }
    
    

    
    
    public Vector cekMhsInputMultipleTimesBasedTglhr(String smawl, Vector v_scope_id, String tkn_output_colomn_nm, String tkn_col_type, boolean prodi_only) {
    	StringTokenizer st = null;
    	
    	int tot_tkn_col = 0;
    	String brs_header = null;
    	if(tkn_output_colomn_nm!=null) {
    		st = new StringTokenizer(tkn_output_colomn_nm,",");
    		tot_tkn_col = st.countTokens();
    		while(st.hasMoreTokens()) {
    			if(brs_header==null) {
    				brs_header = new String(st.nextToken());
    			}
    			else {
    				brs_header = brs_header +"`"+st.nextToken();
    			}
    		}
    	}
    	Vector v = null;
    	
    	ListIterator li = null;
    	try {
    		String addon_cmd = "";
			li = v_scope_id.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				st = new StringTokenizer(brs,"`");
				String kmp = st.nextToken();
				boolean sy_mhs = false;
				while(st.hasMoreTokens()) {
					String id = st.nextToken();
					if(id.equalsIgnoreCase("own")) {
						//sbg mhs
						sy_mhs = true;
					}
					else {
						//sbg approval ato monitor
						if(addon_cmd.contains("ID_OBJ")) {
    						//klao ada value dari kampus lainnya
    						addon_cmd = addon_cmd+" OR ID_OBJ="+id;
    					}
    					else {
    						//first record
    						addon_cmd = addon_cmd+"ID_OBJ="+id;	
    					}
					}
				}
			}	
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			String sql_cmd = "";
			if(prodi_only) {
				//CIVITAS a inner join OBJECT b on a.ID_OBJ=b.ID_OBJ where SMAWLMSMHS=? and OBJ_NAME='MHS'
				sql_cmd = "select "+tkn_output_colomn_nm+" from CIVITAS a inner join OBJECT b on a.ID_OBJ=b.ID_OBJ  where SMAWLMSMHS=? and ("+addon_cmd+") and OBJ_NAME='MHS' order by TGLHRMSMHS,NMMHSMSMHS";
				//sql_cmd = "select "+tkn_output_colomn_nm+" from CIVITAS where SMAWLMSMHS=? and ("+addon_cmd+") and KDPSTMSMHS>'1000' order by TGLHRMSMHS,NMMHSMSMHS";
				stmt = con.prepareStatement(sql_cmd);
			}
			else {
				sql_cmd = "select "+tkn_output_colomn_nm+" from CIVITAS where SMAWLMSMHS=? and ("+addon_cmd+") order by TGLHRMSMHS,NMMHSMSMHS";
				stmt = con.prepareStatement(sql_cmd);	
			}
			////System.out.println("sql_cmd="+sql_cmd);
			stmt.setString(1, smawl);
			////System.out.println("smawl="+smawl);
			rs = stmt.executeQuery();
			boolean first = true;
			String prev_tglhr = "";
			String prev_brs = "";
			String tglhr = "";
			boolean match = false;
			//li.add("NIK  `   NAMA   `   TGL LAHIR   `   KODE PRODI   `   NPMHS");
			while(rs.next()) {
				
				////System.out.println("masuk");
				////System.out.println("tot_tkn_col="+tot_tkn_col);
				String brs = null;
				for(int i=0;i<tot_tkn_col;i++) {
					String tmp = rs.getString((i+1));
					////System.out.println(i+". tmp="+tmp);
					if(brs == null) {	
						
						brs = new String(tmp.trim());
					}
					else {
						brs = brs +"`"+ tmp.trim();
					}
					
				}
				
				if(first) {
					first = false;
					v = new Vector();
					li = v.listIterator();
					li.add(tkn_col_type);
					li.add(brs_header);
					prev_tglhr = rs.getString("TGLHRMSMHS");
					prev_brs = new String(brs);
				}
				else {
					tglhr = rs.getString("TGLHRMSMHS");
					if(prev_tglhr.equalsIgnoreCase(tglhr)) {
						match = true;
					}
					else {
						prev_tglhr = new String(tglhr);
						prev_brs = new String(brs);
					}
				}
				if(match) {
					li.add(prev_brs.trim());
					li.add(brs.trim());
					match = false;
				}
				
				
				////System.out.println("baris . "+brs);
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
    
    
    
    public String getNmmhsDanProdiGiven(String listNpmByComa) {
    	//Vector v = null;
    	StringTokenizer st = null;
    	String listBaru="";
    	try {
    		if(listNpmByComa!=null && (st = new StringTokenizer(listNpmByComa,",")).countTokens()>0) {
    			
    		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		//	get
    			stmt = con.prepareStatement("select NMMHSMSMHS,KDPSTMSMHS from CIVITAS where NPMHSMSMHS=?");
    			while(st.hasMoreTokens()) {
    				String npmhs = st.nextToken();
    				stmt.setString(1, npmhs);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				String nmmhs = rs.getString("NMMHSMSMHS");
        				String kdpst = rs.getString("KDPSTMSMHS");
        				listBaru=listBaru+npmhs+"-"+nmmhs+"-"+kdpst;
        			}
        			else {
        				listBaru=listBaru+npmhs+"-ERROR TDK ADA NAMA-ERROR TDK ADA KDPST";
        			}
        			if(st.hasMoreTokens()) {
        				listBaru=listBaru+",";
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
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	}   
    	return listBaru;
    }
    
    
    
    /*
     * DEPRECATED JANGAN DIPAKE - PAKE VERSI 1
     */
    public Vector getListMhsYgSdhHeregistrasi(String thsms,Vector vScopeViewWhoRegister) {
    	Vector v = getListMhsYgSdhHeregistrasi_v1(thsms,vScopeViewWhoRegister);
    	/*
    	Vector v = null;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	String sql = null;
    	if(vScopeViewWhoRegister!=null && vScopeViewWhoRegister.size()>0) {
    		sql = "";
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		li = vScopeViewWhoRegister.listIterator();
        		while(li.hasNext()) {
        			String kdpst = (String)li.next();
        			sql = sql + "DAFTAR_ULANG.KDPST='"+kdpst+"'";
        			if(li.hasNext()) {
        				sql = sql+" OR ";
        			}
        		}
        		v = new Vector();
        		li = v.listIterator();
        		sql = "SELECT * FROM DAFTAR_ULANG INNER JOIN DAFTAR_ULANG_RULES dur1 ON DAFTAR_ULANG.THSMS = dur1.THSMS INNER JOIN DAFTAR_ULANG_RULES dur2 ON DAFTAR_ULANG.KDPST = dur2.KDPST WHERE DAFTAR_ULANG.THSMS='"+thsms+"' AND ("+sql+")";
        		////System.out.println("sql=>"+sql);
        		stmt = con.prepareStatement(sql);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String kdpst_ = rs.getString("KDPST");
        			String npmhs_ = rs.getString("NPMHS");
        			String tknApr = rs.getString("TOKEN_APPROVAL");// yg sudah veriied 
        			if(Checker.isStringNullOrEmpty(tknApr)) {
        				tknApr = "null";
        			}
        			String tknVer = rs.getString("TKN_VERIFICATOR");//verificator heregitrasi
        			if(Checker.isStringNullOrEmpty(tknVer)) {
        				tknVer = "null";
        			}
        			String tknKartuUjian = rs.getString("TOKEN_KARTU_UJIAN");
        			if(Checker.isStringNullOrEmpty(tknKartuUjian)) {
        				tknKartuUjian = "null";
        			}
        			String tknApprKartuUjian = rs.getString("TOKEN_APPROVAL_KARTU_UJIAN");
        			if(Checker.isStringNullOrEmpty(tknApprKartuUjian)) {
        				tknApprKartuUjian = "null";
        			}
        			String tknStatus = rs.getString("STATUS_APPROVAL_KARTU_UJIAN");
        			if(Checker.isStringNullOrEmpty(tknStatus)) {
        				tknStatus = "null";
        			}
        			////System.out.println("^."+kdpst_+"||"+npmhs_+"||"+tknApr+"||"+tknVer+"||"+tknKartuUjian+"||"+tknApprKartuUjian);
        			//jika verificator tidak komplit, exclude
        			if(tknApr!=null && !Checker.isStringNullOrEmpty(tknApr)) {
        				boolean pass = true;
            			////System.out.println("tknVer=="+tknVer);
            			st = new StringTokenizer(tknVer,",");
            			while(st.hasMoreTokens() && pass) {
            				String nickNameVerificator = st.nextToken();
            				////System.out.println("tknVer==vs nickNameVerificator=="+tknVer+" vs "+nickNameVerificator);
            				if(!tknApr.contains(nickNameVerificator)) {
            					pass = false;
            				}
            			}
            			if(pass) {
            				////System.out.println("pass");
            				li.add(kdpst_+"||"+npmhs_+"||"+tknApr+"||"+tknVer+"||"+tknKartuUjian+"||"+tknApprKartuUjian+"||"+tknStatus);
            				//if(npmhs_.equalsIgnoreCase("2020113100003")) {
            					////System.out.println("disini == "+kdpst_+"||"+npmhs_+"||"+tknApr+"||"+tknVer+"||"+tknKartuUjian+"||"+tknApprKartuUjian+"||"+tknStatus);
            				//}
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
        	finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        		if (con!=null) try { con.close();} catch (Exception ignore){}
        	}
    	}
    	*/  
    	return v;
    }
    
    public static Vector searchCivitas(String nmmhs, java.sql.Date tglhr) {
    	
    	String url1=null;     
    	Connection con1=null;
    	PreparedStatement stmt1=null;
    	ResultSet rs1=null;
    	DataSource ds1=null;
    	Vector v = null;
    	ListIterator li = null;
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds1 = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con1 = ds1.getConnection();
			//1. set npm asal at civitas
			stmt1 = con1.prepareStatement("SELECT KDPSTMSMHS,NPMHSMSMHS,SMAWLMSMHS,STPIDMSMHS,MALAIKAT FROM CIVITAS a inner join OBJECT b on a.ID_OBJ=b.ID_OBJ where OBJ_DESC like 'MHS%' and NMMHSMSMHS=? and TGLHRMSMHS=?");
			stmt1.setString(1, nmmhs);
			stmt1.setDate(2, tglhr);
			rs1 = stmt1.executeQuery();
			if(rs1.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					String kdpst = ""+rs1.getString(1);
					String npmhs = ""+rs1.getString(2);
					String smawl = ""+rs1.getString(3);
					String stpid = ""+rs1.getString(4);
					String malaikat = ""+rs1.getBoolean(5);
					li.add(kdpst+"`"+npmhs+"`"+nmmhs+"`"+tglhr+"`"+smawl+"`"+stpid+"`"+malaikat);
				}
				while(rs1.next());
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
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs1!=null) try  { rs1.close(); } catch (Exception ignore){}
			if (stmt1!=null) try  { stmt1.close(); } catch (Exception ignore){}
			if (con1!=null) try { con1.close();} catch (Exception ignore){}
		}
    	return v;
    }
        		
    
    public Vector getListMhsYgSdhHeregistrasi_v1(String thsms,Vector vScopeViewWhoRegister) {
    	Vector v = null;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	String sql = null;
    	if(vScopeViewWhoRegister!=null && vScopeViewWhoRegister.size()>0) {
    		sql = "";
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		li = vScopeViewWhoRegister.listIterator();
        		while(li.hasNext()) {
        			String kdpst = (String)li.next();
        			sql = sql + "DAFTAR_ULANG.KDPST='"+kdpst+"'";
        			if(li.hasNext()) {
        				sql = sql+" OR ";
        			}
        		}
        		v = new Vector();
        		li = v.listIterator();
        		//sql = "SELECT * FROM DAFTAR_ULANG INNER JOIN DAFTAR_ULANG_RULES dur1 ON DAFTAR_ULANG.THSMS = dur1.THSMS INNER JOIN DAFTAR_ULANG_RULES dur2 ON DAFTAR_ULANG.KDPST = dur2.KDPST WHERE DAFTAR_ULANG.THSMS='"+thsms+"' AND ("+sql+")";
        		sql = "SELECT * FROM DAFTAR_ULANG  WHERE THSMS='"+thsms+"' AND ("+sql+") AND ALL_APPROVED=true";
        		////System.out.println("sql=>"+sql);
        		stmt = con.prepareStatement(sql);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String kdpst_ = rs.getString("KDPST");
        			String npmhs_ = rs.getString("NPMHS");
        			String tknApr = rs.getString("TOKEN_APPROVAL");// yg sudah veriied 
        			if(Checker.isStringNullOrEmpty(tknApr)) {
        				tknApr = "null";
        			}
        			//String tknVer = rs.getString("TKN_VERIFICATOR");//verificator heregitrasi
        			String tknVer = rs.getString("TKN_JABATAN_APPROVAL");//verificator heregitrasi
        			if(Checker.isStringNullOrEmpty(tknVer)) {
        				tknVer = "null";
        			}
        			String tknKartuUjian = rs.getString("TOKEN_KARTU_UJIAN");
        			if(Checker.isStringNullOrEmpty(tknKartuUjian)) {
        				tknKartuUjian = "null";
        			}
        			String tknApprKartuUjian = rs.getString("TOKEN_APPROVAL_KARTU_UJIAN");
        			if(Checker.isStringNullOrEmpty(tknApprKartuUjian)) {
        				tknApprKartuUjian = "null";
        			}
        			String tknStatus = rs.getString("STATUS_APPROVAL_KARTU_UJIAN");
        			if(Checker.isStringNullOrEmpty(tknStatus)) {
        				tknStatus = "null";
        			}
        			/*
        			 * UPDATED semua pengecekan dibawah ini sudah tidak diperlukan lagi semenjak sql nya ada ALL_approved = true
        			 */
        			li.add(kdpst_+"||"+npmhs_+"||"+tknApr+"||"+tknVer+"||"+tknKartuUjian+"||"+tknApprKartuUjian+"||"+tknStatus);
        			/*
        			if(tknApr!=null && !Checker.isStringNullOrEmpty(tknApr)) {
        				boolean pass = true;
            			////System.out.println("tknVer=="+tknVer);
            			st = new StringTokenizer(tknVer,",");
            			while(st.hasMoreTokens() && pass) {
            				String nickNameVerificator = st.nextToken();
            				////System.out.println("tknVer==vs nickNameVerificator=="+tknVer+" vs "+nickNameVerificator);
            				if(!tknApr.contains(nickNameVerificator)) {
            					pass = false;
            				}
            			}
            			if(pass) {
            				////System.out.println("pass");
            				li.add(kdpst_+"||"+npmhs_+"||"+tknApr+"||"+tknVer+"||"+tknKartuUjian+"||"+tknApprKartuUjian+"||"+tknStatus);
            				//if(npmhs_.equalsIgnoreCase("2020113100003")) {
            					////System.out.println("disini == "+kdpst_+"||"+npmhs_+"||"+tknApr+"||"+tknVer+"||"+tknKartuUjian+"||"+tknApprKartuUjian+"||"+tknStatus);
            				//}
            			}	
            			
        			}
        			*/
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
    	   
    	return v;
    }
    

    
    
    public Vector getRuleKartuUjian(String thsms,String targetUjian, Vector vGetListMhsYgSdhHeregistrasi) {
    	//Vector v = null;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	String sql = null;
    	if(vGetListMhsYgSdhHeregistrasi!=null && vGetListMhsYgSdhHeregistrasi.size()>0) {
    		sql = "";
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select TKN_VERIFICATOR_KARTU FROM KARTU_UJIAN_RULES WHERE THSMS=? and KDPST=? and TIPE_UJIAN=?");
        		li = vGetListMhsYgSdhHeregistrasi.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			////System.out.println("brs--"+brs);
        			//li.add(kdpst_+"||"+npmhs_+"||"+tknApr+"||"+tknVer+"||"+tknKartuUjian+"||"+tknApprKartuUjian+"||"+tknStatus);
        			st = new StringTokenizer(brs,"||");
        			String kdpst = st.nextToken();
        			String npmhs = st.nextToken();
        			String tknApr = st.nextToken();
        			String tknVer = st.nextToken();
        			String tknKartuUjian = st.nextToken();
        			String tknApprKartuUjian = st.nextToken();
        			String tknStatus = st.nextToken();
        			stmt.setString(1, thsms);
        			stmt.setString(2, kdpst);
        			stmt.setString(3, targetUjian);
        			rs = stmt.executeQuery();
        			rs.next();
        			String approvee = ""+rs.getString("TKN_VERIFICATOR_KARTU");
        			
        			li.set(brs+"||"+approvee);
        			//if(npmhs.equalsIgnoreCase("2020113100003")) {
        			//	////System.out.println("baris == "+brs+"||"+approvee);
        			//}
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
    	   
    	return vGetListMhsYgSdhHeregistrasi;
    }          

    
    public Vector getTotPembayaran(Vector vGetRuleKartuUjian) {
    	Vector v = null;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	String sql = null;
    	if(vGetRuleKartuUjian!=null && vGetRuleKartuUjian.size()>0) {
    		sql = "";
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select AMONTPYMNT FROM PYMNT WHERE KDPSTPYMNT=? AND NPMHSPYMNT=? AND VOIDDPYMNT=? and NPM_APPROVEE IS NOT NULL");
        		li = vGetRuleKartuUjian.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			//li.add(kdpst_+"||"+npmhs_+"||"+tknApr+"||"+tknVer);
        			st = new StringTokenizer(brs,"||");
        			String kdpst = st.nextToken();
        			String npmhs = st.nextToken();
        			String tknApr = st.nextToken();
        			String tknVer = st.nextToken();
        			String tknKartuUjian = st.nextToken();
        			String tknApprKartuUjian = st.nextToken();
        			String tknStatus = st.nextToken();
        			String tknRulesApproveeKartu = st.nextToken();
        			
        			stmt.setString(1, kdpst);
        			stmt.setString(2, npmhs);
        			stmt.setBoolean(3, false);
        			rs = stmt.executeQuery();
        			double tot = 0;
        			while(rs.next()) {
        				double sub = rs.getDouble("AMONTPYMNT");
        				////System.out.println("Rp."+sub);
        				tot = tot+sub;
        			}
        			li.set(brs+"||"+tot);
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
    	   
    	return vGetRuleKartuUjian;
    }
    
    public String getNpmMhsGiven(String kdpst, String nmmhs, String tplhr, String tglhr, String smawl) {
    	String npmhs = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select NPMHSMSMHS from CIVITAS where KDPSTMSMHS=? and NMMHSMSMHS=? and TPLHRMSMHS=? and TGLHRMSMHS=? and SMAWLMSMHS=?");
    		stmt.setString(1, kdpst);
    		stmt.setString(2, nmmhs.toUpperCase());
    		if(Checker.isStringNullOrEmpty(tplhr)) {
    			stmt.setNull(3, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(3, tplhr);
    		}	
    		if(Checker.isStringNullOrEmpty(tglhr)) {
    			stmt.setNull(4, java.sql.Types.DATE);
    		}
    		else {
    			stmt.setDate(4, java.sql.Date.valueOf(tglhr));
    		}
    		stmt.setString(5, smawl);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			npmhs = new String(rs.getString(1));
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
        return npmhs;
    }	

    
    public Vector getInfoProfileMhs(Vector vGetTotPembayaran) {
    	//Vector v = null;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	String sql = null;
    	if(vGetTotPembayaran!=null && vGetTotPembayaran.size()>0) {
    		sql = "";
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select * from CIVITAS where KDPSTMSMHS=? and NPMHSMSMHS=?");
        		li = vGetTotPembayaran.listIterator();
        		int rut = 0;
        		while(li.hasNext()) {
        			rut++;
        			String brs = (String)li.next();
        			//li.add(kdpst_+"||"+npmhs_+"||"+tknApr+"||"+tknVer+"||"+tot);
        			st = new StringTokenizer(brs,"||");
        			////System.out.println(rut+". "+st.countTokens()+"-"+brs);
        			String kdpst = st.nextToken();
        			String npmhs = st.nextToken();
        			String tknApr = st.nextToken();
        			String tknVer = st.nextToken();
        			String tknKartuUjian = st.nextToken();
        			String tknApprKartuUjian = st.nextToken();
        			String tknStatus = st.nextToken();
        			String tknRulesApproveeKartu = st.nextToken();
        			String tot = st.nextToken();
        			stmt.setString(1, kdpst);
        			stmt.setString(2, npmhs);
        			rs = stmt.executeQuery();
        			if(rs.next()) { // masalah FK waktu mhs dihapus di civiatas tabel tidak dihapus di tabel daftar ulang 
        				String nimhs = ""+rs.getString("NIMHSMSMHS");
            			if(Checker.isStringNullOrEmpty(nimhs)) {
            				nimhs = "null";
            			}
            			String nmmhs = ""+rs.getString("NMMHSMSMHS");
            			if(Checker.isStringNullOrEmpty(nmmhs)) {
            				nmmhs = "null";
            			}
            			String shift = ""+rs.getString("SHIFTMSMHS");
            			if(Checker.isStringNullOrEmpty(shift)) {
            				shift = "null";
            			}
            			String smawl = ""+rs.getString("SMAWLMSMHS");
            			if(Checker.isStringNullOrEmpty(smawl)) {
            				smawl = "null";
            			}
            			String stpid = ""+rs.getString("STPIDMSMHS");
            			if(Checker.isStringNullOrEmpty(stpid)) {
            				stpid = "null";
            			}
            			String gel = ""+rs.getString("GELOMMSMHS");
            			if(Checker.isStringNullOrEmpty(gel)) {
            				gel = "null";
            			}
            			li.set(brs+"||"+nimhs+"||"+nmmhs+"||"+shift+"||"+smawl+"||"+stpid+"||"+gel);
        			}
        			else {
        				li.remove();
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
    	   
    	return vGetTotPembayaran;
    } 
    
    public Vector prosesStatusApprovalKartuUjian(String targetUjian,Vector vGetInfoProfileMhs) {
    	/*
    	 * proses mo opo toh??/
    	 * hanya untuk menentukan status akhir kartu ujian apakah siap dicetak ato menunggu validasi
    	 * 
    	 */
    			
    	
    	StringTokenizer st = null;
    	ListIterator li = null;
    	String sql = null;
    	if(vGetInfoProfileMhs!=null && vGetInfoProfileMhs.size()>0) {

        	li = vGetInfoProfileMhs.listIterator();
        	int io =0;
        	while(li.hasNext()) {
        		io++;
        		String brs = (String)li.next();
        		
        			//li.add(kdpst_+"||"+npmhs_+"||"+tknApr+"||"+tknVer+"||"+tot);
        		st = new StringTokenizer(brs,"||");
        		//if(brs.contains("2020100000120")) {
        		//	////System.out.println("tokens=="+st.countTokens());
        		//}
        		////System.out.println(io+".countTokens = "+st.countTokens());
        		if(st.countTokens()==15) {
        			String kdpst = st.nextToken();
            		String npmhs = st.nextToken();
            		//if(npmhs.equalsIgnoreCase("2020100000120")) {
            		//	////System.out.println(io+".baris = "+brs);
            		//}
            		String tknApr = st.nextToken();
            		String tknVer = st.nextToken();
            		String tknKartuUjian = st.nextToken();
            		String tknApprKartuUjian = st.nextToken();
            		String tknStatus = st.nextToken();
            		String tknRulesApproveeKartu = st.nextToken();
            		
            		String tot = st.nextToken();
            		String nimhs = st.nextToken();
            		String nmmhs = st.nextToken();
            		String shift = st.nextToken();
            		String smawl = st.nextToken();
            		String stpid = st.nextToken();
            		String gel = st.nextToken();
            		////System.out.println("-done-");
            		//cek status apa sudah di approve pa blum
            		//1.cek urutan target ujian lalu sek statusnya
            		//  kalo dah ada di tknKartuUjian berarti harus ada juga di tknApprKartuUjian dan status
            		//String status = ""
            		int norut = 0;
            		String status = "";
            		if(tknKartuUjian!=null && !Checker.isStringNullOrEmpty(tknKartuUjian)) {
            			////System.out.println("-tknKartuUjian-"+tknKartuUjian);
            			////System.out.println("-targetUjian-"+targetUjian);
            			st = new StringTokenizer(tknKartuUjian,",");
                		
                		boolean match = false;
                		while(st.hasMoreTokens() && !match) {
                			norut++;
                			String tmpUjian = st.nextToken();
                			if(tmpUjian.equalsIgnoreCase(targetUjian)) {
                				match = true;
                			}
                		}
                		////System.out.println("-match-"+match);
                		if(match){
                			//get Status
                			/*
                			 * ini adalah STATUS AKHIR
                			 */
                			////System.out.println("-tknStatus-"+tknStatus);
                			////System.out.println("-norut-"+norut);
                			st = new StringTokenizer(tknStatus,"#");
                			int tokens = st.countTokens();
                			if(tokens>0) {
                				if(tokens>=norut) {
                					//sudah ada status sebelumnya
                					for(int k=0;k<norut;k++) {
                						status = st.nextToken();
                					}
                					//kalo null == status menunggu validasi
                					if(Checker.isStringNullOrEmpty(status)) {
                						status = "Menunggu Validasi";
                					}
                				}
                				else {
                					//berarti belum ada statusnya - jadi pasti status akhirnya = menunggu validasi
                					status = "Menunggu Validasi";
                				}
                				
                			}
                			else {
                				status = "Menunggu Validasi";
                			}
                			/*
                			if(st.countTokens()<norut) {
                				status = "Menunggu Validasi";
                			}
                			else if(st.countTokens()==norut) {
                				for(int i=0;i<norut;i++) {
                        			status = st.nextToken();
                        		}
                			}
                			*/
                			//for(int i=0;i<norut;i++) {
                			//	status = st.nextToken();
                			//}
                		}
                		else {
                			//blum ada approval untuk ujian ini
                			status = "Menunggu Validasi";
                		}
            		}
            		else {
            			//blum pernah ada approval untuk ujian apapun
            			status = "Menunggu Validasi";
            		}
            		
            		li.set(brs+"||"+status);
            		//if(npmhs.equalsIgnoreCase("2020100000120")) {
            			////System.out.println("status == "+status);
            			////System.out.println("li.add == "+brs+"||"+status);
            		//}
        		}
        		else {
        			li.remove();;
        			////System.out.println("brs == remove");
        		}
        		
        	}
    	}
    	   
    	return vGetInfoProfileMhs;
    } 
    
    
    public String prepForGetProfileMhs(String npmhs, String kdpst) {
    	//Vector v = null;
    	String needByGetProfile = "";
    	try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		//	get
    			stmt = con.prepareStatement("select * from CIVITAS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where KDPSTMSMHS=? and NPMHSMSMHS=?");
    			stmt.setString(1, kdpst);
    			stmt.setString(2, npmhs);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			String nmmhs = ""+rs.getString("NMMHSMSMHS");
        			//String npmhs = rs.getString("NMMHSMSMHS");
            		//String cmd = ""+rs.getString("NMMHSMSMHS");
            		String nimhs = ""+rs.getString("NIMHSMSMHS");
            		String stmhs = ""+rs.getString("STMHSMSMHS");
            		//String kdpst = rs.getString("NMMHSMSMHS");
            		String id_obj = ""+rs.getLong("ID_OBJ");
            		String obj_lvl = ""+rs.getLong("OBJ_LEVEL");
            		needByGetProfile = nmmhs+"||"+nimhs+"||"+stmhs+"||"+id_obj+"||"+obj_lvl;
    				
        		}
        		else {
        			////System.out.println("beans.dbase.mhs.prepForGetProfileMhs(String npmhs, String kdpst)");
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
    	return needByGetProfile;
    }
    
    
    public String prepForGetProfileMhs(String npmhs) {
    	//Vector v = null;
    	String needByGetProfile = "";
    	try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		//	get
    			stmt = con.prepareStatement("select * from CIVITAS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where NPMHSMSMHS=?");
    			stmt.setString(1, npmhs);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			String kdpst = rs.getString("KDPSTMSMHS");
        			String nmmhs = ""+rs.getString("NMMHSMSMHS");
        			
        			//String npmhs = rs.getString("NMMHSMSMHS");
            		//String cmd = ""+rs.getString("NMMHSMSMHS");
            		String nimhs = ""+rs.getString("NIMHSMSMHS");
            		String stmhs = ""+rs.getString("STMHSMSMHS");
            		
            		String id_obj = ""+rs.getLong("ID_OBJ");
            		String obj_lvl = ""+rs.getLong("OBJ_LEVEL");
            		needByGetProfile = nmmhs+"||"+nimhs+"||"+stmhs+"||"+id_obj+"||"+obj_lvl+"||"+kdpst;
    				
        		}
        		else {
        			////System.out.println("beans.dbase.mhs.prepForGetProfileMhs(String npmhs, String kdpst)");
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
    	return needByGetProfile;
    }

    public Vector getKrsSmsThsms(String thsms, String kdpst, String npmhs) {
    	Vector v = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select KDKMKTRNLM,NAKMKMAKUL from TRNLM inner join MAKUL on IDKMKTRNLM=IDKMKMAKUL where THSMSTRNLM=? and KDPSTTRNLM=? and NPMHSTRNLM=?");
    		stmt.setString(1, thsms);
    		stmt.setString(2, kdpst);
    		stmt.setString(3, npmhs);
        	rs = stmt.executeQuery();
        	v = new Vector();
        	ListIterator li = v.listIterator();
        	while(rs.next()) {
        		String kdkmk = rs.getString("KDKMKTRNLM");
        		String nakmk = rs.getString("NAKMKMAKUL");
        		li.add(kdkmk+"$"+nakmk);
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

    //DEPRECATED-pake yg ada angel includer
    public Vector getListNpmhsYgAdaDiTrnlm(String thsms) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=? order by NPMHSTRNLM");
    		stmt.setString(1, thsms);
    		rs = stmt.executeQuery();
        	while(rs.next()) {
        		if(v==null) {
        			v = new Vector();
        			li = v.listIterator();
        		}
        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
        		String npmhs = ""+rs.getString("NPMHSTRNLM");
        		//li.add(kdpst);
        		li.add(npmhs);
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
     * DEPRECATED - pake yg ada scope dan angel_includer
     */
    public Vector getListNpmhsYgAdaDiTrnlm(String thsms, String kdpst) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=? order by NPMHSTRNLM");
    		stmt.setString(1, thsms);
    		stmt.setString(2, kdpst);
    		rs = stmt.executeQuery();
        	while(rs.next()) {
        		if(v==null) {
        			v = new Vector();
        			li = v.listIterator();
        		}
        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
        		String npmhs = ""+rs.getString("NPMHSTRNLM");
        		//li.add(kdpst);
        		li.add(npmhs);
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
     * DEPRECATED - pake yg ada scope dan angel_includer
     */
    public String getListMhsTrnlm(String thsms) {
    	Vector v = getListNpmhsYgAdaDiTrnlm(thsms);
    	if(v==null) {
    		v = new Vector();
    	}
    	ListIterator li = v.listIterator();
    	String listThsmsNpmhs = "";
    	try {
    		
    		//	get
    		/* diganti ma proses ini diatas
    		 * Vector v = getListNpmhsYgAdaDiTrnlm(thsms);
    		 
    		stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=? order by KDPSTTRNLM,NPMHSTRNLM");
    		stmt.setString(1, thsms);
    		rs = stmt.executeQuery();
        	while(rs.next()) {
        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
        		String npmhs = ""+rs.getString("NPMHSTRNLM");
        		//li.add(kdpst);
        		li.add(npmhs);
        	}
        	*/
        	if(v.size()>0) {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select * from CIVITAS WHERE NPMHSMSMHS=?");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			//String kdpst = (String)li.next();
        			String npmhs = (String)li.next();
        			stmt.setString(1, npmhs);
        			//stmt.setString(2, npmhs);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				listThsmsNpmhs = listThsmsNpmhs+"$";
        				String kdpst = ""+rs.getString("KDPSTMSMHS");
        				String nmmhs = ""+rs.getString("NMMHSMSMHS");
        				if(Checker.isStringNullOrEmpty(nmmhs)) {
        					nmmhs="null";
        				}
        				String nimhs = ""+rs.getString("NIMHSMSMHS");
        				if(Checker.isStringNullOrEmpty(nimhs)) {
        					nimhs="null";
        				}
        				String smawl = ""+rs.getString("SMAWLMSMHS");
        				if(Checker.isStringNullOrEmpty(smawl)) {
        					smawl="null";
        				}
        				String stmhs = ""+rs.getString("STMHSMSMHS");
        				if(Checker.isStringNullOrEmpty(stmhs)) {
        					stmhs="null";
        				}
        				listThsmsNpmhs =listThsmsNpmhs+"$"+kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stmhs;
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
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	}   
    	return listThsmsNpmhs;
    }
    
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * DEPRECATED GANTI YG byObjId
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    public String getListMhsDaftarUlang(String thsms) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String listThsmsNpmhs = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select distinct NPMHS from DAFTAR_ULANG where THSMS=? order by KDPST,NPMHS");
    		stmt.setString(1, thsms);
    		rs = stmt.executeQuery();
        	while(rs.next()) {
        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
        		String npmhs = ""+rs.getString("NPMHS");
        		//li.add(kdpst);
        		li.add(npmhs);
        	}
        	if(v.size()>0) {
        		stmt = con.prepareStatement("select * from CIVITAS WHERE NPMHSMSMHS=?");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			//String kdpst = (String)li.next();
        			String npmhs = (String)li.next();
        			stmt.setString(1, npmhs);
        			//stmt.setString(2, npmhs);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				listThsmsNpmhs = listThsmsNpmhs+"$";
        				String kdpst = ""+rs.getString("KDPSTMSMHS");
        				String nmmhs = ""+rs.getString("NMMHSMSMHS");
        				if(Checker.isStringNullOrEmpty(nmmhs)) {
        					nmmhs="null";
        				}
        				String nimhs = ""+rs.getString("NIMHSMSMHS");
        				if(Checker.isStringNullOrEmpty(nimhs)) {
        					nimhs="null";
        				}
        				String smawl = ""+rs.getString("SMAWLMSMHS");
        				if(Checker.isStringNullOrEmpty(smawl)) {
        					smawl="null";
        				}
        				String stmhs = ""+rs.getString("STMHSMSMHS");
        				if(Checker.isStringNullOrEmpty(stmhs)) {
        					stmhs="null";
        				}
        				listThsmsNpmhs =listThsmsNpmhs+"$"+kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stmhs;
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
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	}   
    	return listThsmsNpmhs;
    }
    
    
    public String getListMhsDaftarUlang_byObjid(String thsms,Vector vScope_id) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String listThsmsNpmhs = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select distinct NPMHS from DAFTAR_ULANG where THSMS=? order by KDPST,NPMHS");
    		stmt.setString(1, thsms);
    		rs = stmt.executeQuery();
        	while(rs.next()) {
        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
        		String npmhs = ""+rs.getString("NPMHS");
        		//li.add(kdpst);
        		li.add(npmhs);
        	}
        	if(v.size()>0) {
        		stmt = con.prepareStatement("select * from CIVITAS WHERE NPMHSMSMHS=?");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			//String kdpst = (String)li.next();
        			String npmhs = (String)li.next();
        			stmt.setString(1, npmhs);
        			//stmt.setString(2, npmhs);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				listThsmsNpmhs = listThsmsNpmhs+"$";
        				String kdpst = ""+rs.getString("KDPSTMSMHS");
        				String nmmhs = ""+rs.getString("NMMHSMSMHS");
        				if(Checker.isStringNullOrEmpty(nmmhs)) {
        					nmmhs="null";
        				}
        				String nimhs = ""+rs.getString("NIMHSMSMHS");
        				if(Checker.isStringNullOrEmpty(nimhs)) {
        					nimhs="null";
        				}
        				String smawl = ""+rs.getString("SMAWLMSMHS");
        				if(Checker.isStringNullOrEmpty(smawl)) {
        					smawl="null";
        				}
        				String stmhs = ""+rs.getString("STMHSMSMHS");
        				if(Checker.isStringNullOrEmpty(stmhs)) {
        					stmhs="null";
        				}
        				listThsmsNpmhs =listThsmsNpmhs+"$"+kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stmhs;
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
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	}   
    	return listThsmsNpmhs;
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
    			li.add(kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stpid+"$"+tglAju+"$"+tknApr+"$"+idObj);
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
    
    public String getListTipeObj() {
    	//Vector v = null;
    	String tokn = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select * from OBJECT order by OBJ_DESC");
    		
    		boolean first = true;
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			if(first) {
    				first = false;
    				tokn = "";
    			}
    			else {
    				tokn = tokn+"$";
    			}
    			String idObj = ""+rs.getLong("ID_OBJ");
    			if(Checker.isStringNullOrEmpty(idObj)) {
    				idObj = "null";
    			}
    			String kdpst = ""+rs.getString("KDPST");
    			if(Checker.isStringNullOrEmpty(kdpst)) {
    				kdpst = "null";
    			}
				String objName = ""+rs.getString("OBJ_NAME");
				if(Checker.isStringNullOrEmpty(objName)) {
					objName = "null";
    			}
				String objDesc = ""+rs.getString("OBJ_DESC");
				if(Checker.isStringNullOrEmpty(objDesc)) {
					objDesc = "null";
    			}
				String objLvl = ""+rs.getString("OBJ_LEVEL");
				if(Checker.isStringNullOrEmpty(objLvl)) {
					objLvl = "null";
    			}
				String acl = ""+rs.getString("ACCESS_LEVEL_CONDITIONAL");
				if(Checker.isStringNullOrEmpty(acl)) {
    				acl = "null";
    			}
				String al = ""+rs.getString("ACCESS_LEVEL");
				if(Checker.isStringNullOrEmpty(al)) {
    				al = "null";
    			}
				String dvalue = ""+rs.getString("DEFAULT_VALUE");
				if(Checker.isStringNullOrEmpty(dvalue)) {
					dvalue = "null";
    			}
				String nicknm = ""+rs.getString("OBJ_NICKNAME");
				if(Checker.isStringNullOrEmpty(nicknm)) {
					nicknm = "null";
    			}
				
				
    			tokn = tokn + idObj+"$"+kdpst+"$"+objName+"$"+objDesc+"$"+objLvl+"$"+acl+"$"+al+"$"+dvalue+"$"+nicknm;
	
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
    	return tokn;
    }
    
    public String getInfoPA(String kdpst,String npmhs) {
    	//Vector v = null;
    	String tokn = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select NPM_PA,NMM_PA from EXT_CIVITAS where KDPSTMSMHS=? and NPMHSMSMHS=?");
    		stmt.setString(1,kdpst);
    		stmt.setString(2, npmhs);
    		rs = stmt.executeQuery();
    		boolean first = true;
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String npmPa = ""+rs.getString("NPM_PA");
    			String nmmPa = ""+rs.getString("NMM_PA");
    			tokn = npmPa+"|"+nmmPa;
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
    	return tokn;
    }
    
    public String getInfoKrklm(String kdpst,String npmhs) {
    	//Vector v = null;
    	String krklm = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select KRKLMMSMHS from EXT_CIVITAS where KDPSTMSMHS=? and NPMHSMSMHS=?");
    		stmt.setString(1,kdpst);
    		stmt.setString(2, npmhs);
    		rs = stmt.executeQuery();
    		boolean first = true;
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			krklm = ""+rs.getInt("KRKLMMSMHS");
    			if(krklm==null || Checker.isStringNullOrEmpty(krklm) || krklm.equalsIgnoreCase("0")) {
    				krklm = "null";
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
    	return krklm; //return 0 kalo blum ditentukan
    }
    
    public Vector getStatusMhsPerThsms(Vector vThsms, String npmhs) {
    	//Vector v = null;
    	ListIterator li = vThsms.listIterator();
    	try {
    		
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select KDKMKTRNLM from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=? limit 1");
			while(li.hasNext()) {
				String thsms=(String)li.next();
				stmt.setString(1, thsms);
				stmt.setString(2, npmhs);
				rs = stmt.executeQuery();
				if(rs.next()) {
					li.set(thsms+"`A");
				}
				else {
					li.set(thsms+"`null");
				}
			}
			li = vThsms.listIterator();
			StringTokenizer st = null;
			stmt = con.prepareStatement("select * from TRLSM where THSMS=? and NPMHS=?");
					
			while(li.hasNext()) {
				String brs = (String)li.next();
				if(brs.contains("null")||brs.contains("NULL")) {
					st = new StringTokenizer(brs,"`");
					String thsms = st.nextToken();
					stmt.setString(1, thsms);
					stmt.setString(2, npmhs);
					rs = stmt.executeQuery();
					if(rs.next()) {
						String stmhs = ""+rs.getString("STMHS");
						if(!Checker.isStringNullOrEmpty(stmhs)) {
							li.set(thsms+'`'+stmhs);
						}
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
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	}   
    	return vThsms;
    }
    
    public Vector getDataProfileCivitasAndExtMhs(String npmhs) {
    	/*
    	 * digunakan untuk mhs aktif kembali 
    	 * dengan mengcopy data lama dan diinsert ke npm baru
    	 */
    	Vector v = null;
    	StringTokenizer st = null;
    	String listBaru="";
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
		//	get
			stmt = con.prepareStatement("select * from CIVITAS where NPMHSMSMHS=?");
			stmt.setString(1, npmhs);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				ListIterator li = v.listIterator();
				String id = ""+rs.getLong("ID");
				String idobj = ""+rs.getLong("ID_OBJ");
				String kdpti = ""+rs.getLong("KDPTIMSMHS");
				String kdjen = ""+rs.getString("KDJENMSMHS");
				String kdpst = ""+rs.getString("KDPSTMSMHS");
				//String npmhs = ""+rs.getString("NPMHSMSMHS");
				String nimhs = ""+rs.getString("NIMHSMSMHS");
				String nmmhs = ""+rs.getString("NMMHSMSMHS");
				nmmhs = nmmhs.replace("`", "'");
				String shift = ""+rs.getString("SHIFTMSMHS");
				String tplhr = ""+rs.getString("TPLHRMSMHS");
				String tglhr = ""+rs.getDate("TGLHRMSMHS");
				String kdjek = ""+rs.getString("KDJEKMSMHS");
				String tahun = ""+rs.getString("TAHUNMSMHS");
				String smawl = ""+rs.getString("SMAWLMSMHS");
				String btstu = ""+rs.getString("BTSTUMSMHS");
				String assma = ""+rs.getString("ASSMAMSMHS");
				String tgmsk = ""+rs.getDate("TGMSKMSMHS");
				String tglls = ""+rs.getDate("TGLLSMSMHS");
				String stmhs = ""+rs.getString("STMHSMSMHS");
				String stpid = ""+rs.getString("STPIDMSMHS");
				String sksdi = ""+rs.getFloat("SKSDIMSMHS");
				String asnim = ""+rs.getString("ASNIMMSMHS");
				String aspti = ""+rs.getString("ASPTIMSMHS");
				String asjen = ""+rs.getString("ASJENMSMHS");
				String aspst = ""+rs.getString("ASPSTMSMHS");
				String bistu = ""+rs.getString("BISTUMSMHS");
				String peksb = ""+rs.getString("PEKSBMSMHS");
				String nmpek = ""+rs.getString("NMPEKMSMHS");
				String ptpek = ""+rs.getString("PTPEKMSMHS");
				String pspek = ""+rs.getString("PSPEKMSMHS");
				String noprm = ""+rs.getString("NOPRMMSMHS");
				String nokp1 = ""+rs.getString("NOKP1MSMHS");
				String nokp2 = ""+rs.getString("NOKP2MSMHS");
				String nokp3 = ""+rs.getString("NOKP3MSMHS");
				String nokp4 = ""+rs.getString("NOKP4MSMHS");
				//String updtm = ""+rs.getTimestamp("UPDTMMSMHS");
				String gelom = ""+rs.getString("GELOMMSMHS");
				
				//after feeder
				String nama_ayah = ""+rs.getString("NAMA_AYAH");
				String tglhr_ayah = ""+rs.getDate("TGLHR_AYAH");
				String tplhr_ayah = ""+rs.getString("TPLHR_AYAH");
				String lulus_ayah = ""+rs.getString("LULUSAN_AYAH");
				String hape_ayah = ""+rs.getString("NOHAPE_AYAH");
				String kerja_ayah = ""+rs.getString("KERJA_AYAH");
				String gaji_ayah = ""+rs.getString("GAJI_AYAH");
				String nik_ayah = ""+rs.getString("NIK_AYAH");
				String kandung_ayah = ""+rs.getBoolean("KANDUNG_AYAH");
				String nama_ibu = ""+rs.getString("NAMA_IBU");
				String tglhr_ibu = ""+rs.getDate("TGLHR_IBU");
				String tplhr_ibu = ""+rs.getString("TPLHR_IBU");
				String lulus_ibu = ""+rs.getString("LULUSAN_IBU");
				String hape_ibu = ""+rs.getString("NOHAPE_IBU");
				String kerja_ibu = ""+rs.getString("KERJA_IBU");
				String gaji_ibu = ""+rs.getString("GAJI_IBU");
				String nik_ibu = ""+rs.getString("NIK_IBU");
				String kandung_ibu = ""+rs.getBoolean("KANDUNG_IBU");
				String nama_wali = ""+rs.getString("NAMA_WALI");
				String tglhr_wali = ""+rs.getDate("TGLHR_WALI");
				String tplhr_wali = ""+rs.getString("TPLHR_WALI");
				String lulus_wali = ""+rs.getString("LULUSAN_WALI");
				String hape_wali = ""+rs.getString("NOHAPE_WALI");
				String kerja_wali = ""+rs.getString("KERJA_WALI");
				String gaji_wali = ""+rs.getString("GAJI_WALI");
				String nik_wali = ""+rs.getString("NIK_WALI");
				String hub_wali = ""+rs.getString("HUBUNGAN_WALI");
				String nama_emg1 = ""+rs.getString("NAMA_DARURAT1");
				String hape_emg1 = ""+rs.getString("NOHAPE_DARURAT1");
				String hub_emg1 = ""+rs.getString("HUBUNGAN_DARURAT1");
				String nama_emg2 = ""+rs.getString("NAMA_DARURAT2");
				String hape_emg2 = ""+rs.getString("NOHAPE_DARURAT2");
				String hub_emg2 = ""+rs.getString("HUBUNGAN_DARURAT2");
				String nisn = ""+rs.getString("NISNMSMHS");
				String warga = ""+rs.getString("KEWARGANEGARAAN");
				String nonik = ""+rs.getString("NIKTPMSMHS");
				String nosim = ""+rs.getString("NOSIMMSMHS");
				String paspor = ""+rs.getString("PASPORMSMHS");
				String angel = ""+rs.getBoolean("MALAIKAT");
				String blocked= ""+rs.getBoolean("BLOCKED");
				String aspti_unlisted= ""+rs.getString("ASPTI_UNLISTED");
				String aspti_kdpti= ""+rs.getString("ASPTI_KDPTI");
				String aspst_kdpst = ""+rs.getString("ASPST_KDPST");
				if(Checker.isStringNullOrEmpty(aspst_kdpst)) {
					aspst_kdpst = "null";
				}
				
				String info = id+"`"+idobj+"`"+kdpti+"`"+kdjen+"`"+kdpst+"`"+npmhs+"`"+nimhs+"`"+nmmhs+"`"+shift+"`"+tplhr+"`"+tglhr+"`"+kdjek+"`"+tahun+"`"+smawl+"`"+btstu+"`"+assma+"`"+tgmsk+"`"+tglls+"`"+stmhs+"`"+stpid+"`"+sksdi+"`"+asnim+"`"+aspti+"`"+asjen+"`"+aspst+"`"+bistu+"`"+peksb+"`"+nmpek+"`"+ptpek+"`"+pspek+"`"+noprm+"`"+nokp1+"`"+nokp2+"`"+nokp3+"`"+nokp4+"`"+gelom+"`"+nama_ayah+"`"+tglhr_ayah+"`"+tplhr_ayah+"`"+lulus_ayah+"`"+hape_ayah+"`"+kerja_ayah+"`"+gaji_ayah+"`"+nik_ayah+"`"+kandung_ayah+"`"+nama_ibu+"`"+tglhr_ibu+"`"+tplhr_ibu+"`"+lulus_ibu+"`"+hape_ibu+"`"+kerja_ibu+"`"+gaji_ibu+"`"+nik_ibu+"`"+kandung_ibu+"`"+nama_wali+"`"+tglhr_wali+"`"+tplhr_wali+"`"+lulus_wali+"`"+hape_wali+"`"+kerja_wali+"`"+gaji_wali+"`"+nik_wali+"`"+hub_wali+"`"+nama_emg1+"`"+hape_emg1+"`"+hub_emg1+"`"+nama_emg2+"`"+hape_emg2+"`"+hub_emg2+"`"+nisn+"`"+warga+"`"+nonik+"`"+nosim+"`"+paspor+"`"+angel+"`"+blocked+"`"+aspti_unlisted+"`"+aspti_kdpti+"`"+aspst_kdpst;
				info = info.replace("``", "`null`");
				info = info.replace("` `", "`null`");
				li.add(info);
				////System.out.println("info="+info);
				stmt = con.prepareStatement("select * from EXT_CIVITAS where NPMHSMSMHS=?");
				stmt.setString(1, npmhs);
				rs = stmt.executeQuery();
				if(rs.next()) {
					String kdpst_e = ""+rs.getString("KDPSTMSMHS");
				    String npmhs_e = ""+rs.getString("NPMHSMSMHS");
				    String sttus_e = ""+rs.getString("STTUSMSMHS");
				    String email_e = ""+rs.getString("EMAILMSMHS");
				    String nohpe_e = ""+rs.getString("NOHPEMSMHS");
				    String almrm_e = ""+rs.getString("ALMRMMSMHS");
				    String kotrm_e = ""+rs.getString("KOTRMMSMHS");
				    String posrm_e = ""+rs.getString("POSRMMSMHS");
				    String telrm_e = ""+rs.getString("TELRMMSMHS");
				    String almkt_e = ""+rs.getString("ALMKTMSMHS");
				    String kotkt_e = ""+rs.getString("KOTKTMSMHS");
				    String poskt_e = ""+rs.getString("POSKTMSMHS");
				    String telkt_e = ""+rs.getString("TELKTMSMHS");
				    String jbtkt_e = ""+rs.getString("JBTKTMSMHS");
				    String bidkt_e = ""+rs.getString("BIDKTMSMHS");
				    String jenkt_e = ""+rs.getString("JENKTMSMHS");
				    String nmmsp_e = ""+rs.getString("NMMSPMSMHS");
				    String almsp_e = ""+rs.getString("ALMSPMSMHS");
				    String possp_e = ""+rs.getString("POSSPMSMHS");
				    String kotsp_e = ""+rs.getString("KOTSPMSMHS");
				    String negsp_e = ""+rs.getString("NEGSPMSMHS");
				    String telsp_e = ""+rs.getString("TELSPMSMHS");
				    String neglh_e = ""+rs.getString("NEGLHMSMHS");
				    String agama_e = ""+rs.getString("AGAMAMSMHS");
				    String krklm_e = ""+rs.getString("KRKLMMSMHS");
				    String ttlog_e = ""+rs.getInt("TTLOGMSMHS");
				    String tmlog_e = ""+rs.getInt("TMLOGMSMHS");
				    String dtlog_e = ""+rs.getTimestamp("DTLOGMSMHS");
				   // String updtm_e = ""+rs.getTimestamp("UPDTMMSMHS");
				    String idtipebea_e = ""+rs.getInt("IDPAKETBEASISWA");
				    String npmpa_e = ""+rs.getString("NPM_PA");
				    String nmmpa_e = ""+rs.getString("NMM_PA");
				    String oper_e = ""+rs.getBoolean("PETUGAS");
				    String sma_e = ""+rs.getString("ASAL_SMA");
				    String smakt_e = ""+rs.getString("KOTA_SMA");
				    String lulusma_e = ""+rs.getString("LULUS_SMA");
			
				    //after  feeder
				    String nortrm_e = ""+rs.getString("NRTRMMSMHS");
					String norwrm_e = ""+rs.getString("NRWRMMSMHS");
					String provrm_e = ""+rs.getString("PROVRMMSMHS");
					String provid_e = ""+rs.getString("PROVRMIDWIL");
					String kotid_e = ""+rs.getString("KOTRMIDWIL");
					String kecrm_e = ""+rs.getString("KECRMMSMHS");
					String kecid_e = ""+rs.getString("KECRMIDWIL");
					String kelrm_e = ""+rs.getString("KELRMMSMHS");
					String dusun_e = ""+rs.getString("DUSUNMSMHS");
					if(Checker.isStringNullOrEmpty(dusun_e)) {
						dusun_e = "null";
					}
				    String info_e = kdpst_e+"`"+npmhs_e+"`"+sttus_e+"`"+email_e+"`"+nohpe_e+"`"+almrm_e+"`"+kotrm_e+"`"+posrm_e+"`"+telrm_e+"`"+almkt_e+"`"+kotkt_e+"`"+poskt_e+"`"+telkt_e+"`"+jbtkt_e+"`"+bidkt_e+"`"+jenkt_e+"`"+nmmsp_e+"`"+almsp_e+"`"+possp_e+"`"+kotsp_e+"`"+negsp_e+"`"+telsp_e+"`"+neglh_e+"`"+agama_e+"`"+krklm_e+"`"+ttlog_e+"`"+tmlog_e+"`"+dtlog_e+"`"+idtipebea_e+"`"+npmpa_e+"`"+nmmpa_e+"`"+oper_e+"`"+sma_e+"`"+smakt_e+"`"+lulusma_e+"`"+nortrm_e+"`"+norwrm_e+"`"+provrm_e+"`"+provid_e+"`"+kotid_e+"`"+kecrm_e+"`"+kecid_e+"`"+kelrm_e+"`"+dusun_e;
				    
				    
				    info_e = info_e.replace("``", "`null`");
				    info_e = info_e.replace("` `", "`null`");
					li.add(info_e);
					////System.out.println("info_e="+info_e);
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
    
    

    
    public String belumAdakrsPenyetaraan(Vector v_scope_id) {
    	String thsms_now = Checker.getThsmsNow();
    	//Vector v_mhs = null;
    	boolean sy_mhs = false;
    	//ListIterator lim = null;
    	String list_npm = null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {

        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			String addon_cmd = "";
    			ListIterator li = v_scope_id.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kmp = st.nextToken();
    				
    				while(st.hasMoreTokens()) {
    					String id = st.nextToken();
    					if(id.equalsIgnoreCase("own")) {
    						//sbg mhs
    						sy_mhs = true;
    					}
    					else {
    						//sbg approval ato monitor
    						if(addon_cmd.contains("ID_OBJ")) {
        						//klao ada value dari kampus lainnya
        						addon_cmd = addon_cmd+" OR ID_OBJ="+id;
        					}
        					else {
        						//first record
        						addon_cmd = addon_cmd+"ID_OBJ="+id;	
        					}
    					}

    				}
    			}	
    			if(!sy_mhs) {
					
	    			String cmd = "";
	    			if(addon_cmd!=null && !Checker.isStringNullOrEmpty(addon_cmd)) {
	    				cmd = "select distinct NPMHSMSMHS from CIVITAS inner join TRNLM on NPMHSMSMHS=NPMHSTRNLM where THSMSTRNLM=? and STPIDMSMHS='P' and MALAIKAT=? and ("+addon_cmd+")";
	    				////System.out.println("cmd="+cmd);
	            		stmt = con.prepareStatement(cmd);
	            		stmt.setString(1,thsms_now);
	            		stmt.setBoolean(2, false);
	            		rs = stmt.executeQuery();
	            		while(rs.next()) {
	            			String npmhs = rs.getString(1);
	            			if(list_npm==null) {
	            				list_npm = new String(npmhs);
	            				//lim = v_mhs.listIterator();
	            			}
	            			else {
	            				list_npm = list_npm+","+npmhs;	
	            			}
	            			
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
        	finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        		if (con!=null) try { con.close();} catch (Exception ignore){}
        	}   

    	}
    	return list_npm;
    }
    
    
    public Vector listMhsMalaikat(String smawl, String stpid, String kdpst) {
    	Vector v = null;
    	ListIterator li = null;
    	try {

    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select NPMHSMSMHS,NMMHSMSMHS from CIVITAS where KDPSTMSMHS=? and STPIDMSMHS=? and SMAWLMSMHS=? and MALAIKAT=? order by NPMHSMSMHS");
			stmt.setString(1, kdpst);
			stmt.setString(2, stpid);
			stmt.setString(3, smawl);
			stmt.setBoolean(4, true);
			rs = stmt.executeQuery();
			while(rs.next()) {
				if(v==null) {
					v = new Vector();
					li = v.listIterator();
				}
				String npmhs = rs.getString(1);
				String nmmhs = rs.getString(2);
				li.add(npmhs+"`"+nmmhs);
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
    
    
    public Vector cekApaVectorNimhsTdakAdaDiSistem(Vector v_nimhs) {
    	Vector v_not_found = null;
    	ListIterator lin = null;
    	if(v_nimhs!=null && v_nimhs.size()>0) {
    		try {
    			ListIterator li = v_nimhs.listIterator();
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select NPMHSMSMHS,NMMHSMSMHS from CIVITAS where NIMHSMSMHS=?");
    			while(li.hasNext()) {
    				String nimhs = (String)li.next();
    				stmt.setString(1, nimhs);
    				rs = stmt.executeQuery();
    				if(!rs.next()) {
    					if(v_not_found==null) {
    						v_not_found = new Vector();
    						lin = v_not_found.listIterator();
    					}
    					lin.add(nimhs);
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
    	return v_not_found;
    }
    /*
     * DEPRECATED - pake yg ada scope dan angel_includer
     */
    public Vector getListMhsYgAdaKrsGivenThsms(String thsms, String kdpst) {
    	Vector v_npm = null;
    	ListIterator lin = null;
    	try {
			
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			/*
			 * 1. CEK MHS YG ADA KRS
			 */
			//stmt = con.prepareStatement("select distinct NPMHSTRNLM,KRKLMMSMHS from TRNLM inner join EXT_CIVITAS on NPMHSTRNLM=NPMHSMSMHS where THSMSTRNLM=? and KDPSTTRNLM=? order by KRKLMMSMHS");
			stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM  where THSMSTRNLM=? and KDPSTTRNLM=?");
			stmt.setString(1, thsms);
			stmt.setString(2, kdpst);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String npmhs = rs.getString(1);
				//String krklm = rs.getString(2);
				if(v_npm==null) {
					v_npm = new Vector();
					lin = v_npm.listIterator();
				}
				lin.add(npmhs);
			}
			/*
			 * 2. FILTER YG SUDAH KELUAR
			 */
			if(v_npm!=null && v_npm.size()>0) {
				stmt = con.prepareStatement("select STMHS from TRLSM where THSMS=? and NPMHS=?");
				lin = v_npm.listIterator();
				while(lin.hasNext()) {
					//String brs = (String)lin.next();
					//StringTokenizer st = new StringTokenizer(brs,"`");
					//String kur = st.nextToken();
					String npm = (String)lin.next();
					stmt.setString(1, thsms);
					stmt.setString(2, npm);
					rs = stmt.executeQuery();
					if(rs.next()) {
						String stmhs = rs.getString(1);
						if(stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("L")||stmhs.equalsIgnoreCase("D")||stmhs.equalsIgnoreCase("DO")) {
							lin.remove();
						}
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
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	return v_npm;
    }
    
    public Vector getMhsDgnDataProfilIncomplete(Vector v_scope_id, int limit_per_page, int page, int search_range, String starting_smawl) {
    	Vector vout = new Vector();
    	ListIterator lout = vout.listIterator();
    	// search_range = before +1
    	int range_page = search_range/limit_per_page; //range_page = angka dibawah pad google search 
    	boolean ada_prev = false;
    	boolean ada_next = false;
    	if(page-3>0) {
    		ada_prev = true;
    	}
    	
    	try {
    		String addon_cmd = "";
			ListIterator li = v_scope_id.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				////System.out.println("bare="+brs);
				StringTokenizer st = new StringTokenizer(brs,"`");
				String kmp = st.nextToken();
				boolean sy_mhs = false;
				while(st.hasMoreTokens()) {
					String id = st.nextToken();
					if(id.equalsIgnoreCase("own")) {
						//sbg mhs
						sy_mhs = true;
					}
					else {
						//sbg approval ato monitor
						if(addon_cmd.contains("ID_OBJ")) {
    						//klao ada value dari kampus lainnya
    						addon_cmd = addon_cmd+" OR OBJECT.ID_OBJ="+id;
    					}
    					else {
    						//first record
    						addon_cmd = addon_cmd+"OBJECT.ID_OBJ="+id;	
    					}
					}
				}
			}	
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			String cmd = "SELECT CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS,CIVITAS.NMMHSMSMHS,CIVITAS.SMAWLMSMHS from CIVITAS inner join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where SMAWLMSMHS>=? and (CIVITAS.TPLHRMSMHS is NULL or CIVITAS.TGLHRMSMHS is NULL or CIVITAS.NAMA_IBU is NULL or CIVITAS.NAMA_AYAH is NULL or CIVITAS.KEWARGANEGARAAN is NULL or CIVITAS.NIKTPMSMHS is NULL or EXT_CIVITAS.EMAILMSMHS is NULL or EXT_CIVITAS.NOHPEMSMHS is NULL or EXT_CIVITAS.ALMRMMSMHS is NULL or EXT_CIVITAS.PROVRMMSMHS is NULL or EXT_CIVITAS.PROVRMIDWIL is NULL or EXT_CIVITAS.KOTRMMSMHS is NULL or EXT_CIVITAS.KOTRMIDWIL is NULL or EXT_CIVITAS.KECRMMSMHS is NULL or EXT_CIVITAS.KECRMIDWIL is NULL or EXT_CIVITAS.KELRMMSMHS is NULL or EXT_CIVITAS.TELRMMSMHS is NULL or EXT_CIVITAS.NEGLHMSMHS is NULL) and  ("+addon_cmd+")  order by CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS limit ?,?";
			stmt = con.prepareStatement(cmd);
			////System.out.println("cmd="+cmd);
			stmt.setString(1,starting_smawl);
			stmt.setInt(2, (page-1)*limit_per_page);
			////System.out.println("offset="+(page-1)*limit_per_page);
			stmt.setInt(3, search_range+1);
			////System.out.println("search_range="+(search_range+1));
			rs = stmt.executeQuery();
			int tot_row = 0;
			if(rs!=null) {
				rs.last();
				tot_row = rs.getRow();
				rs.beforeFirst();
			}
			
			if(tot_row>range_page) {
				ada_next = true;
			}
			
			
			lout.add(ada_prev+"`"+page+"/"+range_page+"`"+ada_next+"`"+tot_row);
			////System.out.println(ada_prev+"`"+page+"/"+range_page+"`"+ada_next);
			int tot_dat = 0;
			while(rs.next() && (tot_dat < limit_per_page)) {
				String kdpst = rs.getString(1);
				String npmhs = rs.getString(2);
				String nmmhs = rs.getString(3);
				String smawl = rs.getString(4);
				tot_dat++;
				lout.add(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl);
				////System.out.println(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl);
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
    	return vout;
    }

    public Vector getMhsDgnDataProfilIncomplete_v1(Vector v_scope_id, int limit_per_page, int page, int search_range, String starting_smawl, int starting_page_shown, int ending_page_shown) {
    	Vector v_list_npm = new Vector();
    	ListIterator lout = v_list_npm.listIterator();
    	boolean next_page = false;
    	boolean prev_page = false;
    	if(page>ending_page_shown) {
    		next_page = true;
    	}
    	if(page<starting_page_shown) {
    		prev_page = true;
    	}
        	
    	try {
    		String addon_cmd = "";
			ListIterator li = v_scope_id.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				String kmp = st.nextToken();
				boolean sy_mhs = false;
				while(st.hasMoreTokens()) {
					String id = st.nextToken();
					if(id.equalsIgnoreCase("own")) {
						//sbg mhs
						sy_mhs = true;
					}
					else {
						//sbg approval ato monitor
						if(addon_cmd.contains("ID_OBJ")) {
    						//klao ada value dari kampus lainnya
    						addon_cmd = addon_cmd+" OR OBJECT.ID_OBJ="+id;
    					}
    					else {
    						//first record
    						addon_cmd = addon_cmd+"OBJECT.ID_OBJ="+id;	
    					}
					}
				}
			}	
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			String cmd = "SELECT CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS,CIVITAS.NMMHSMSMHS,CIVITAS.SMAWLMSMHS from CIVITAS inner join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where SMAWLMSMHS>=? and (CIVITAS.TPLHRMSMHS is NULL or CIVITAS.TGLHRMSMHS is NULL or CIVITAS.NAMA_IBU is NULL or CIVITAS.NAMA_AYAH is NULL or CIVITAS.KEWARGANEGARAAN is NULL or CIVITAS.NIKTPMSMHS is NULL or EXT_CIVITAS.EMAILMSMHS is NULL or EXT_CIVITAS.NOHPEMSMHS is NULL or EXT_CIVITAS.ALMRMMSMHS is NULL or EXT_CIVITAS.PROVRMMSMHS is NULL or EXT_CIVITAS.PROVRMIDWIL is NULL or EXT_CIVITAS.KOTRMMSMHS is NULL or EXT_CIVITAS.KOTRMIDWIL is NULL or EXT_CIVITAS.KECRMMSMHS is NULL or EXT_CIVITAS.KECRMIDWIL is NULL or EXT_CIVITAS.KELRMMSMHS is NULL or EXT_CIVITAS.TELRMMSMHS is NULL or EXT_CIVITAS.NEGLHMSMHS is NULL) and  ("+addon_cmd+")  order by CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS limit ?,?";
			stmt = con.prepareStatement(cmd);
			if(page==0||page==1) {
				//fist time, calling dari home
				//limitnya = search range, krn pertama menentukan jumlah maks page ke liatan eq.1 s/d 9 
				stmt.setString(1,starting_smawl);
				stmt.setInt(2, 0);
				stmt.setInt(3, search_range+1);
			}
			else if(next_page) {
				//panggilan sudah dari NEXT navigasi
				//limitnya = search range, krn  menentukan jumlah maks page ke liatan eq.1 s/d 9 
				stmt.setString(1,starting_smawl);
				stmt.setInt(2, (page-1)*limit_per_page);
				stmt.setInt(3, search_range+1);	
			}
			else {
				//prev_page masuk ke sini, tidak seperti next_page dimana kita harus ngecek masih ada berapa sisanya
				//kalo prev data kita bermain pada hal navigasi saja
				
				//panggilan sudah dari navigasi
				//limitnya = limit_per_page, agar prosesnya cepat jadi atPage * limit_per_page 
				stmt.setString(1,starting_smawl);
				stmt.setInt(2, (page-1)*limit_per_page);
				stmt.setInt(3, limit_per_page);	
			}
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				String kdpst = rs.getString(1);
				String npmhs = rs.getString(2);
				String nmmhs = rs.getString(3);
				String smawl = rs.getString(4);
				lout.add(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl);
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
    	return v_list_npm;
    }

    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * DEPRECATED - DONOT USED
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    public Vector getMhsPindahanDataIncomplete(Vector v_scope_id, int limit_per_page, int page, int search_range, String starting_smawl) {
    	Vector vout = new Vector();
    	ListIterator lout = vout.listIterator();
    	// search_range = before +1
    	int range_page = search_range/limit_per_page; //range_page = angka dibawah pad google search 
    	boolean ada_prev = false;
    	boolean ada_next = false;
    	if(page-3>0) {
    		ada_prev = true;
    	}
    	
    	try {
    		String addon_cmd = "";
			ListIterator li = v_scope_id.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				////System.out.println("bare="+brs);
				StringTokenizer st = new StringTokenizer(brs,"`");
				String kmp = st.nextToken();
				boolean sy_mhs = false;
				while(st.hasMoreTokens()) {
					String id = st.nextToken();
					if(id.equalsIgnoreCase("own")) {
						//sbg mhs
						sy_mhs = true;
					}
					else {
						//sbg approval ato monitor
						if(addon_cmd.contains("ID_OBJ")) {
    						//klao ada value dari kampus lainnya
    						addon_cmd = addon_cmd+" OR OBJECT.ID_OBJ="+id;
    					}
    					else {
    						//first record
    						addon_cmd = addon_cmd+"OBJECT.ID_OBJ="+id;	
    					}
					}

				}
			}	
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//String cmd = "SELECT CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS,CIVITAS.NMMHSMSMHS,CIVITAS.SMAWLMSMHS from CIVITAS inner join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where SMAWLMSMHS>=? and (CIVITAS.TPLHRMSMHS is NULL or CIVITAS.TGLHRMSMHS is NULL or CIVITAS.NAMA_IBU is NULL or CIVITAS.NAMA_AYAH is NULL or CIVITAS.KEWARGANEGARAAN is NULL or CIVITAS.NIKTPMSMHS is NULL or EXT_CIVITAS.EMAILMSMHS is NULL or EXT_CIVITAS.NOHPEMSMHS is NULL or EXT_CIVITAS.ALMRMMSMHS is NULL or EXT_CIVITAS.PROVRMMSMHS is NULL or EXT_CIVITAS.PROVRMIDWIL is NULL or EXT_CIVITAS.KOTRMMSMHS is NULL or EXT_CIVITAS.KOTRMIDWIL is NULL or EXT_CIVITAS.KECRMMSMHS is NULL or EXT_CIVITAS.KECRMIDWIL is NULL or EXT_CIVITAS.KELRMMSMHS is NULL or EXT_CIVITAS.TELRMMSMHS is NULL or EXT_CIVITAS.NEGLHMSMHS is NULL) and  ("+addon_cmd+")  order by CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS limit ?,?";
			String cmd="SELECT CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS,CIVITAS.NMMHSMSMHS,CIVITAS.SMAWLMSMHS FROM CIVITAS  inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ left join TRNLP on NPMHSMSMHS=NPMHSTRNLP where SMAWLMSMHS>=? and STPIDMSMHS='P' and NPMHSTRNLP is null and  ("+addon_cmd+")  order by CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS limit ?,?";
			stmt = con.prepareStatement(cmd);
			////System.out.println("cmd="+cmd);
			stmt.setString(1,starting_smawl);
			stmt.setInt(2, (page-1)*limit_per_page);
			////System.out.println("offset="+(page-1)*limit_per_page);
			stmt.setInt(3, search_range+1);
			////System.out.println("search_range="+(search_range+1));
			rs = stmt.executeQuery();
			int tot_row = 0;
			if(rs!=null) {
				rs.last();
				tot_row = rs.getRow();
				rs.beforeFirst();
			}
			
			if(tot_row>range_page) {
				ada_next = true;
			}
			
			
			lout.add(ada_prev+"`"+page+"/"+range_page+"`"+ada_next+"`"+tot_row);
			////System.out.println(ada_prev+"`"+page+"/"+range_page+"`"+ada_next);
			int tot_dat = 0;
			while(rs.next() && (tot_dat < limit_per_page)) {
				String kdpst = rs.getString(1);
				String npmhs = rs.getString(2);
				String nmmhs = rs.getString(3);
				String smawl = rs.getString(4);
				tot_dat++;
				lout.add(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl);
				////System.out.println(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl);
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
    	return vout;
    }
    
    public Vector getMhsPindahanDataIncomplete_v1(Vector v_scope_id, int limit_per_page, int page, int search_range, String starting_smawl, int starting_page_shown, int ending_page_shown) {
    	Vector vout = new Vector();
    	ListIterator lout = vout.listIterator();
    	// search_range = before +1
    	boolean next_page = false;
    	boolean prev_page = false;
    	if(page>ending_page_shown) {
    		next_page = true;
    	}
    	if(page<starting_page_shown) {
    		prev_page = true;
    	}
    	
    	try {
    		String addon_cmd = "";
			ListIterator li = v_scope_id.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				////System.out.println("bare="+brs);
				StringTokenizer st = new StringTokenizer(brs,"`");
				String kmp = st.nextToken();
				boolean sy_mhs = false;
				while(st.hasMoreTokens()) {
					String id = st.nextToken();
					if(id.equalsIgnoreCase("own")) {
						//sbg mhs
						sy_mhs = true;
					}
					else {
						//sbg approval ato monitor
						if(addon_cmd.contains("ID_OBJ")) {
    						//klao ada value dari kampus lainnya
    						addon_cmd = addon_cmd+" OR OBJECT.ID_OBJ="+id;
    					}
    					else {
    						//first record
    						addon_cmd = addon_cmd+"OBJECT.ID_OBJ="+id;	
    					}
					}

				}
			}	
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//String cmd = "SELECT CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS,CIVITAS.NMMHSMSMHS,CIVITAS.SMAWLMSMHS from CIVITAS inner join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where SMAWLMSMHS>=? and (CIVITAS.TPLHRMSMHS is NULL or CIVITAS.TGLHRMSMHS is NULL or CIVITAS.NAMA_IBU is NULL or CIVITAS.NAMA_AYAH is NULL or CIVITAS.KEWARGANEGARAAN is NULL or CIVITAS.NIKTPMSMHS is NULL or EXT_CIVITAS.EMAILMSMHS is NULL or EXT_CIVITAS.NOHPEMSMHS is NULL or EXT_CIVITAS.ALMRMMSMHS is NULL or EXT_CIVITAS.PROVRMMSMHS is NULL or EXT_CIVITAS.PROVRMIDWIL is NULL or EXT_CIVITAS.KOTRMMSMHS is NULL or EXT_CIVITAS.KOTRMIDWIL is NULL or EXT_CIVITAS.KECRMMSMHS is NULL or EXT_CIVITAS.KECRMIDWIL is NULL or EXT_CIVITAS.KELRMMSMHS is NULL or EXT_CIVITAS.TELRMMSMHS is NULL or EXT_CIVITAS.NEGLHMSMHS is NULL) and  ("+addon_cmd+")  order by CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS limit ?,?";
			String cmd="SELECT CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS,CIVITAS.NMMHSMSMHS,CIVITAS.SMAWLMSMHS FROM CIVITAS  inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ left join TRNLP on NPMHSMSMHS=NPMHSTRNLP where SMAWLMSMHS>=? and STPIDMSMHS='P' and NPMHSTRNLP is null and  ("+addon_cmd+")  order by CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS limit ?,?";
			stmt = con.prepareStatement(cmd);
			if(page==0||page==1) {
				//fist time, calling dari home
				//limitnya = search range, krn pertama menentukan jumlah maks page ke liatan eq.1 s/d 9 
				stmt.setString(1,starting_smawl);
				stmt.setInt(2, 0);
				stmt.setInt(3, search_range+1);
			}
			else if(next_page) {
				//panggilan sudah dari NEXT navigasi
				//limitnya = search range, krn  menentukan jumlah maks page ke liatan eq.1 s/d 9 
				stmt.setString(1,starting_smawl);
				stmt.setInt(2, (page-1)*limit_per_page);
				stmt.setInt(3, search_range+1);	
			}
			else {
				//prev_page masuk ke sini, tidak seperti next_page dimana kita harus ngecek masih ada berapa sisanya
				//kalo prev data kita bermain pada hal navigasi saja
				
				//panggilan sudah dari navigasi
				//limitnya = limit_per_page, agar prosesnya cepat jadi atPage * limit_per_page 
				stmt.setString(1,starting_smawl);
				stmt.setInt(2, (page-1)*limit_per_page);
				stmt.setInt(3, limit_per_page);	
			}
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				String kdpst = rs.getString(1);
				String npmhs = rs.getString(2);
				String nmmhs = rs.getString(3);
				String smawl = rs.getString(4);
				lout.add(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl);
				////System.out.println(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl);
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
    	return vout;
    }
    
    
    public Vector getMhsPindahanDataIncomplete_v1(Vector v_scope_id) {
    	Vector vout = new Vector();
    	ListIterator lout = vout.listIterator();
    	// search_range = before +1
    	String thsms_reg = Checker.getThsmsHeregistrasi();
    	String thsms_reg_1 = Tool.returnPrevThsmsGivenTpAntara(thsms_reg);
    	try {
    		String addon_cmd = "";
			ListIterator li = v_scope_id.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				////System.out.println("bare="+brs);
				StringTokenizer st = new StringTokenizer(brs,"`");
				String kmp = st.nextToken();
				boolean sy_mhs = false;
				while(st.hasMoreTokens()) {
					String id = st.nextToken();
					if(id.equalsIgnoreCase("own")) {
						//sbg mhs
						sy_mhs = true;
					}
					else {
						//sbg approval ato monitor
						if(addon_cmd.contains("ID_OBJ")) {
    						//klao ada value dari kampus lainnya
    						addon_cmd = addon_cmd+" OR OBJECT.ID_OBJ="+id;
    					}
    					else {
    						//first record
    						addon_cmd = addon_cmd+"OBJECT.ID_OBJ="+id;	
    					}
					}

				}
			}	
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//String cmd = "SELECT CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS,CIVITAS.NMMHSMSMHS,CIVITAS.SMAWLMSMHS from CIVITAS inner join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where SMAWLMSMHS>=? and (CIVITAS.TPLHRMSMHS is NULL or CIVITAS.TGLHRMSMHS is NULL or CIVITAS.NAMA_IBU is NULL or CIVITAS.NAMA_AYAH is NULL or CIVITAS.KEWARGANEGARAAN is NULL or CIVITAS.NIKTPMSMHS is NULL or EXT_CIVITAS.EMAILMSMHS is NULL or EXT_CIVITAS.NOHPEMSMHS is NULL or EXT_CIVITAS.ALMRMMSMHS is NULL or EXT_CIVITAS.PROVRMMSMHS is NULL or EXT_CIVITAS.PROVRMIDWIL is NULL or EXT_CIVITAS.KOTRMMSMHS is NULL or EXT_CIVITAS.KOTRMIDWIL is NULL or EXT_CIVITAS.KECRMMSMHS is NULL or EXT_CIVITAS.KECRMIDWIL is NULL or EXT_CIVITAS.KELRMMSMHS is NULL or EXT_CIVITAS.TELRMMSMHS is NULL or EXT_CIVITAS.NEGLHMSMHS is NULL) and  ("+addon_cmd+")  order by CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS limit ?,?";
			String cmd="SELECT CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS,CIVITAS.NMMHSMSMHS,CIVITAS.SMAWLMSMHS FROM CIVITAS  inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ left join TRNLP on NPMHSMSMHS=NPMHSTRNLP where SMAWLMSMHS>=? and STPIDMSMHS='P' and NPMHSTRNLP is null and  ("+addon_cmd+")  order by CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS";
			stmt = con.prepareStatement(cmd);
			stmt.setString(1,thsms_reg_1);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				String kdpst = rs.getString(1);
				String npmhs = rs.getString(2);
				String nmmhs = rs.getString(3);
				String smawl = rs.getString(4);
				lout.add(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl);
				////System.out.println(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl);
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
    	return vout;
    }
    
    
    public Vector getMhsPindahanDataIncomplete_part2(Vector v_scope_id, String starting_smawl) {
    	Vector vout = null;
    	ListIterator lout = null;
    	// search_range = before +1
 
    	
    	try {
    		String addon_cmd = "";
			ListIterator li = v_scope_id.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				////System.out.println("bare="+brs);
				StringTokenizer st = new StringTokenizer(brs,"`");
				String kmp = st.nextToken();
				boolean sy_mhs = false;
				while(st.hasMoreTokens()) {
					String id = st.nextToken();
					if(id.equalsIgnoreCase("own")) {
						//sbg mhs
						sy_mhs = true;
					}
					else {
						//sbg approval ato monitor
						if(addon_cmd.contains("ID_OBJ")) {
    						//klao ada value dari kampus lainnya
    						addon_cmd = addon_cmd+" OR OBJECT.ID_OBJ="+id;
    					}
    					else {
    						//first record
    						addon_cmd = addon_cmd+"OBJECT.ID_OBJ="+id;	
    					}
					}

				}
			}	
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//get list NPM mhs Pindahan
			String cmd="SELECT distinct CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS,CIVITAS.NMMHSMSMHS,CIVITAS.SMAWLMSMHS FROM CIVITAS  inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ inner join TRNLP on NPMHSMSMHS=NPMHSTRNLP where SMAWLMSMHS>=? and STPIDMSMHS='P' and NPMHSTRNLP is not null and  ("+addon_cmd+")  order by CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS";
			stmt = con.prepareStatement(cmd);
			stmt.setString(1,starting_smawl);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				vout=new Vector();
				lout=vout.listIterator();
				String kdpst = rs.getString(1);
				String npmhs = rs.getString(2);
				String nmmhs = rs.getString(3);
				String smawl = rs.getString(4);
				lout.add(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl);
				while(rs.next()) {
					kdpst = rs.getString(1);
					npmhs = rs.getString(2);
					nmmhs = rs.getString(3);
					smawl = rs.getString(4);
					lout.add(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl);
				}
				////System.out.println(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl);
				////System.out.println("`"+npmhs+"`");
			}
			////System.out.println("vout1="+vout.size());
			//stmt = con.prepareStatement("select distinct NPMHSMSMHS from CIVITAS");
			//String cmd = "SELECT CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS,CIVITAS.NMMHSMSMHS,CIVITAS.SMAWLMSMHS from CIVITAS inner join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where SMAWLMSMHS>=? and (CIVITAS.TPLHRMSMHS is NULL or CIVITAS.TGLHRMSMHS is NULL or CIVITAS.NAMA_IBU is NULL or CIVITAS.NAMA_AYAH is NULL or CIVITAS.KEWARGANEGARAAN is NULL or CIVITAS.NIKTPMSMHS is NULL or EXT_CIVITAS.EMAILMSMHS is NULL or EXT_CIVITAS.NOHPEMSMHS is NULL or EXT_CIVITAS.ALMRMMSMHS is NULL or EXT_CIVITAS.PROVRMMSMHS is NULL or EXT_CIVITAS.PROVRMIDWIL is NULL or EXT_CIVITAS.KOTRMMSMHS is NULL or EXT_CIVITAS.KOTRMIDWIL is NULL or EXT_CIVITAS.KECRMMSMHS is NULL or EXT_CIVITAS.KECRMIDWIL is NULL or EXT_CIVITAS.KELRMMSMHS is NULL or EXT_CIVITAS.TELRMMSMHS is NULL or EXT_CIVITAS.NEGLHMSMHS is NULL) and  ("+addon_cmd+")  order by CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS limit ?,?";
			//String cmd="SELECT CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS,CIVITAS.NMMHSMSMHS,CIVITAS.SMAWLMSMHS FROM CIVITAS  inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ inner join TRNLP on NPMHSMSMHS=NPMHSTRNLP where SMAWLMSMHS>=? and STPIDMSMHS='P' and NPMHSTRNLP is null and  ("+addon_cmd+")  order by CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS limit ?,?";
			if(vout!=null && vout.size()>0) {
				//part 2 
				//cek apa sudah ada yg disetarakan
				stmt = con.prepareStatement("select SKSMKASAL from TRNLP where NPMHSTRNLP=? and TRANSFERRED=? limit 1");
				lout = vout.listIterator();
				
				while(lout.hasNext()) {
					int sksdi = 0;
					String brs = (String)lout.next();
					String npmhs = Tool.getTokenKe(brs, 2, "`");
					stmt.setString(1, npmhs);
					stmt.setBoolean(2, true);
					rs = stmt.executeQuery();
					if(rs.next()) {
						lout.remove();
					}
					////System.out.println(npmhs+"="+sksdi);
					//if(sksdi>0) {
						
						////System.out.println("remove "+npmhs);
					//}
				}
				
			}
			////System.out.println("vout2="+vout.size());
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
    	return vout;
    }
    
    public Vector getListNpmhsGivenStmhs(String kdpst, String stmhs, int limit, int offset) {
    	Vector v = null;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select NPMHS from TRLSM where KDPST=? and STMHS=?  order by NPMHS limit ?,?");
    			stmt.setString(1, kdpst);
    			stmt.setString(2, stmhs);
    			stmt.setInt(3, offset);
    			stmt.setInt(4, limit);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v = new Vector();
    				li = v.listIterator();
    				do {
    					li.add(rs.getString(1));
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
    
    public Vector getListNpmhsGiven(String kdpst, int limit, int offset) {
    	Vector v_npm = null;
    	ListIterator lin = null;
    	try {
			
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select distinct NPMHSMSMHS from CIVITAS  where KDPSTMSMHS=? order BY NPMHSMSMHS limit ?,?");
			stmt.setString(1, kdpst);
			stmt.setInt(2, offset);
			stmt.setInt(3, limit);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String npmhs = rs.getString(1);
				//String krklm = rs.getString(2);
				if(v_npm==null) {
					v_npm = new Vector();
					lin = v_npm.listIterator();
				}
				lin.add(npmhs);
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
    	return v_npm;
    }
    
    public Vector getListNpmhsGiven(String kdpst,String smawl, int limit, int offset) {
    	Vector v_npm = null;
    	ListIterator lin = null;
    	try {
			
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select distinct NPMHSMSMHS from CIVITAS  where KDPSTMSMHS=? and SMAWLMSMHS=? order BY NPMHSMSMHS limit ?,?");
			stmt.setString(1, kdpst);
			stmt.setString(2, smawl);
			stmt.setInt(3, offset);
			stmt.setInt(4, limit);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String npmhs = rs.getString(1);
				//String krklm = rs.getString(2);
				if(v_npm==null) {
					v_npm = new Vector();
					lin = v_npm.listIterator();
				}
				lin.add(npmhs);
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
    	return v_npm;
    }
    
    
    public Vector getListNpmhsGiven(String smawl) {
    	Vector v_npm = null;
    	ListIterator lin = null;
    	try {
			
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//CIVITAS a inner join OBJECT b on a.ID_OBJ=b.ID_OBJ where SMAWLMSMHS=? and OBJ_NAME='MHS'
			stmt = con.prepareStatement("select distinct NPMHSMSMHS from CIVITAS a inner join OBJECT b on a.ID_OBJ=b.ID_OBJ  where SMAWLMSMHS=? and OBJ_NAME='MHS' order BY NPMHSMSMHS");
			//stmt = con.prepareStatement("select distinct NPMHSMSMHS from CIVITAS  where SMAWLMSMHS=? and KDPSTMSMHS>'1001'order BY NPMHSMSMHS");
			stmt.setString(1, smawl);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String npmhs = rs.getString(1);
				//String krklm = rs.getString(2);
				if(v_npm==null) {
					v_npm = new Vector();
					lin = v_npm.listIterator();
				}
				lin.add(npmhs);
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
    	return v_npm;
    }
    
    public Vector getInfoMhs(Vector v_npmhs, String tkn_col_name, String tkn_col_type) {
    	if(v_npmhs!=null) {
    		ListIterator li = null;
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		String char_pemisah = "`";
        		if(tkn_col_name.contains(",")) {
        			char_pemisah = ",";
        		}
        		StringTokenizer st = new StringTokenizer(tkn_col_name,char_pemisah);
        		
        		
        		int counter = st.countTokens();
        		String sql_cmd = null;
        		while(st.hasMoreTokens()) {
        			String tkn = st.nextToken();
        			if(sql_cmd==null) {
        				sql_cmd = new String(tkn);
        			}
        			else {
        				sql_cmd = sql_cmd+","+tkn;
        			}
        		}
        		//	get
        		if(sql_cmd!=null) {
        			sql_cmd = "select "+sql_cmd+" from CIVITAS inner join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS where CIVITAS.NPMHSMSMHS=?";
        		}
        		else {
        			sql_cmd = "select * from CIVITAS inner join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS where CIVITAS.NPMHSMSMHS=?";
        		}
        		stmt = con.prepareStatement(sql_cmd);
        		li = v_npmhs.listIterator();
        		while(li.hasNext()) {
        			String npmhs = (String)li.next();
        			String info = new String(npmhs);
        			stmt.setString(1, npmhs);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				if(tkn_col_type.contains(",")) {
                			char_pemisah = ",";
                		}
                		StringTokenizer stt = new StringTokenizer(tkn_col_type,char_pemisah);
        				for(int i=1; i<=counter;i++) {
        					String tipe = stt.nextToken();
        					if(tipe.equalsIgnoreCase("s")||tipe.equalsIgnoreCase("String")) {
        						info = info +"`"+rs.getString(i);	
        					}
        					else if(tipe.equalsIgnoreCase("i")||tipe.equalsIgnoreCase("int")||tipe.equalsIgnoreCase("integer")) {
        						info = info +"`"+rs.getInt(i);
        					}
        					else if(tipe.equalsIgnoreCase("d")||tipe.equalsIgnoreCase("double")) {
        						info = info +"`"+rs.getDouble(i);
        					}
        					else if(tipe.equalsIgnoreCase("date")||tipe.equalsIgnoreCase("dt")||tipe.equalsIgnoreCase("tgl")) {
        						info = info +"`"+rs.getDate(i);
        					}
        					else if(tipe.equalsIgnoreCase("float")||tipe.equalsIgnoreCase("f")) {
        						info = info +"`"+rs.getFloat(i);
        					}
        					else if(tipe.equalsIgnoreCase("long")||tipe.equalsIgnoreCase("l")) {
        						info = info +"`"+rs.getLong(i);
        					}
        				}
        				li.set(info);
        			}
        			else {
        				//ignore value cuma npm doan (1 token 0nly)
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
    	return v_npmhs;
    }
    
    
    public Vector getTotSmsLamaStudiDariSmawlDanTotalNonAktif(Vector v_npmhs_smawl_stpid_krklm, String target_thsms) {
    	if(v_npmhs_smawl_stpid_krklm!=null) {
    		ListIterator li = null;
    		StringTokenizer st = null;
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select THSMS from TRLSM where THSMS<=? and NPMHS=? and (STMHS='C' or STMHS='N')");
        		li = v_npmhs_smawl_stpid_krklm.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			if(st.hasMoreTokens()) {
        				String npmhs = st.nextToken();
        				String smawl = st.nextToken();
        				String stpid = st.nextToken();
        				String krklm = st.nextToken();
        				int tot_sms = AddHocFunction.hitungTotSemesterDariSamwlSampaiTargetThsmsDikurangNonAktifTrlsm(smawl, target_thsms);
        				stmt.setString(1, target_thsms);
        				stmt.setString(2, npmhs);
        				rs = stmt.executeQuery();
        				int tot_sms_non_aktif = 0;
        				while(rs.next()) {
        					tot_sms_non_aktif++;
        				}
        				brs = brs+"`"+tot_sms+"`"+tot_sms_non_aktif;
        				////System.out.println(brs+"`"+tot_sms+"`"+tot_sms_non_aktif);
        			}
        			li.set(brs);
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
    	return v_npmhs_smawl_stpid_krklm;
    }
    

    
    

    
    public Vector getSeluruhMhsYgStatusnyaBelumKeluarFromTheBeginning(String based_thsms) {
    	/*
    	 * DIPAKAI SAAT MENENTUKAN BASED THSMS, JADI MHS YG SUDAH TIDAK DI TRAK LAGI DI EPSBED TAPI
    	 * STATUSNYA BELUM ADA, HARUS DIKELUARIN
    	 */
    	ListIterator li = null;
		StringTokenizer st = null;
		Vector v = null;
		String list_npm = null;
		try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		//1.cek trnlm
    		//CIVITAS a inner join OBJECT b on a.ID_OBJ=b.ID_OBJ where SMAWLMSMHS=? and OBJ_NAME='MHS'
    		stmt = con.prepareStatement("select NPMHSMSMHS FROM CIVITAS a inner join OBJECT b on a.ID_OBJ=b.ID_OBJ WHERE OBJ_NAME='MHS'");
    		//stmt = con.prepareStatement("select NPMHSMSMHS FROM CIVITAS WHERE KDPSTMSMHS>'1000'");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			if(v==null) {
    				v = new Vector();
    				li = v.listIterator();
    			}
    			String npmhs = rs.getString(1);
    			li.add(npmhs);
    		}
    		////System.out.println("tot_mhs = "+v.size());
    		if(v!=null) {
    			stmt = con.prepareStatement("select STMHS from TRLSM where THSMS<=? and NPMHS=? and (STMHS='K' || STMHS='D' || STMHS='L') limit 1");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String npmhs = (String)li.next();
    				stmt.setString(1, based_thsms); //MAKA DIGUNAKAN THSMS<=?
    				stmt.setString(2, npmhs);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					li.remove();
    					////System.out.println(npmhs+" removed");
    				}
    			}
    		}
    		////System.out.println("tot_sisa_mhs = "+v.size());
    		//2.cek trlsm
    		
    		
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
    
    public Vector addInfoKrklmAndTotSmsAktif(String tkn_npm, String target_thsms) {
    	ListIterator li = null;
		StringTokenizer st = null;
		Vector v = null;
		String list_npm = null;
		try {
			if(tkn_npm!=null) {
				v = new Vector();
				li = v.listIterator();
				st = new StringTokenizer(tkn_npm,"`");
				Context initContext  = new InitialContext();
	    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
	    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
	    		con = ds.getConnection();
	    		stmt = con.prepareStatement("SELECT SMAWLMSMHS,MALAIKAT,KRKLMMSMHS FROM CIVITAS a inner join EXT_CIVITAS b on a.NPMHSMSMHS=b.NPMHSMSMHS where a.NPMHSMSMHS=?");
	    		while(st.hasMoreTokens()) {
	    			String npmhs = st.nextToken();
	    			stmt.setString(1, npmhs);	
	    			rs = stmt.executeQuery();
	    			String smawl = "null";
	    			String angel = "null";
	    			String krklm = "null";
	    			if(rs.next()) {
	    				smawl = ""+rs.getString(1);
	    				//Converter.
	    				angel = ""+rs.getString(2);
	    				krklm = ""+rs.getString(3);
	    			}
	    			li.add(npmhs+"`"+angel+"`"+krklm);
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
    
    public Vector addInfoKrklm(Vector v_npmhs_dll) {
    	if(v_npmhs_dll!=null) {
    		ListIterator li = v_npmhs_dll.listIterator();;
    		StringTokenizer st = null;
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select KRKLMMSMHS from EXT_CIVITAS where NPMHSMSMHS=?");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			String npmhs = st.nextToken();
        			stmt.setString(1, npmhs);
        			rs = stmt.executeQuery();
        			rs.next();
        			String krklm = ""+rs.getString(1);
        			li.set(brs+"`"+krklm);
        			
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
    	return v_npmhs_dll;
    }
    
    public Vector addInfoIdobjKdjenKdpstKodeKmp(Vector v_npmhs_dll) {
    	if(v_npmhs_dll!=null) {
    		ListIterator li = v_npmhs_dll.listIterator();;
    		StringTokenizer st = null;
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select a.ID_OBJ,KDPTIMSMHS,KDJENMSMHS,KDPSTMSMHS,KODE_KAMPUS_DOMISILI from CIVITAS a inner join OBJECT b on a.ID_OBJ=b.ID_OBJ where NPMHSMSMHS=?");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			String npmhs = st.nextToken();
        			stmt.setString(1, npmhs);
        			rs = stmt.executeQuery();
        			rs.next();
        			String id_obj = ""+rs.getInt(1);
        			String kdpti = ""+rs.getString(2);
        			String kdjen = ""+rs.getString(3);
        			String kdpst = ""+rs.getString(4);
        			String kdkmp = ""+rs.getString(5);
        			li.set(brs+"`"+id_obj+"`"+kdpti+"`"+kdjen+"`"+kdpst+"`"+kdkmp);
        			
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
    	return v_npmhs_dll;
    }
    
    /*
     * DEPRECATED - pake yg ada scope dan angel_includer
     */
    public String getMhsAktifVersiPddikti(String target_thsms, String kdpst) {
    	
    			
    	////System.out.println("mhs aktif "+target_thsms);
    	ListIterator li = null;
		StringTokenizer st = null;
		Vector v = null;
		String list_npm = null;
		try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		//1.cek trnlm
    		if(kdpst!=null && kdpst.equalsIgnoreCase("999999")) {
    			////System.out.println("kesisni kan");
    			//stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM inner join CIVITAS on NPMHSTRNLM=NPMHSMSMHS where  (THSMSTRNLM=?) and MALAIKAT=?");
    			stmt = con.prepareStatement("select distinct NPMHSMSMHS from CIVITAS  inner join TRNLM on NPMHSMSMHS=NPMHSTRNLM  where MALAIKAT=? and THSMSTRNLM=?");
    			
        		stmt.setBoolean(1, true);
        		stmt.setString(2, target_thsms);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			if(v==null) {
        				v = new Vector();
        				li = v.listIterator();
        				list_npm = new String("`");
        			}
        			String npmhs = rs.getString(1);
        			list_npm = list_npm+npmhs+"`";
        		}
        		//CIVITAS a inner join OBJECT b on a.ID_OBJ=b.ID_OBJ where SMAWLMSMHS=? and OBJ_NAME='MHS'
        		stmt = con.prepareStatement("SELECT NPMHSMSMHS FROM CIVITAS a inner join OBJECT b on a.ID_OBJ=b.ID_OBJ where SMAWLMSMHS=? and OBJ_NAME='MHS' and MALAIKAT=?");
        		//stmt = con.prepareStatement("SELECT NPMHSMSMHS FROM CIVITAS where SMAWLMSMHS=? and KDPSTMSMHS>'1000' and MALAIKAT=?");
        		stmt.setString(1, target_thsms);
        		stmt.setBoolean(2, true);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			if(v==null) {
        				v = new Vector();
        				li = v.listIterator();
        				list_npm = new String("`");
        			}
        			String npmhs = rs.getString(1);
        			if(!list_npm.contains(npmhs)) {
        				list_npm = list_npm+npmhs+"`";	
        			}
        		}	
        		//st = new StringTokenizer(list_npm,"`");
        		////System.out.println("malaikat = "+st.countTokens());
    		}
    		else {
    			////System.out.println("ke sana kan");
    			stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=?");
        		stmt.setString(1, target_thsms);
        		stmt.setString(2, kdpst);	
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			if(v==null) {
        				v = new Vector();
        				li = v.listIterator();
        				list_npm = new String("`");
        			}
        			String npmhs = rs.getString(1);
        			list_npm = list_npm+npmhs+"`";
        		}
        		stmt = con.prepareStatement("SELECT NPMHSMSMHS FROM CIVITAS where SMAWLMSMHS=? and KDPSTMSMHS=?");
        		stmt.setString(1, target_thsms);
        		stmt.setString(2, kdpst);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			if(v==null) {
        				v = new Vector();
        				li = v.listIterator();
        				list_npm = new String("`");
        			}
        			String npmhs = rs.getString(1);
        			if(!list_npm.contains(npmhs)) {
        				list_npm = list_npm+npmhs+"`";	
        			}
        		}	
    		}
    		
    		
    		//2.cek trlsm (AKTIF = distinct trnlm + (N + C)@thsms -KELUAR(thsms-1)
    		if(kdpst!=null && kdpst.equalsIgnoreCase("999999")) {
    			stmt = con.prepareStatement("select NPMHS from TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and (STMHS=? or STMHS=?) and MALAIKAT=?");
        		stmt.setString(1, target_thsms);
        		stmt.setString(2, "C");
        		stmt.setString(3, "N");
        		stmt.setBoolean(4, true);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			if(v==null) {
        				v = new Vector();
        				li = v.listIterator();
        				list_npm = new String("`");
        			}
        			String npmhs = rs.getString(1);
        			if(!list_npm.contains("`"+npmhs+"`")) {
        				list_npm = list_npm+npmhs+"`";	
        			}
        			
        		}
    			
    		}
    		else {
    			stmt = con.prepareStatement("select NPMHS from TRLSM where THSMS=? and (STMHS=? or STMHS=?)");
        		stmt.setString(1, target_thsms);
        		stmt.setString(2, "C");
        		stmt.setString(3, "N");
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			if(v==null) {
        				v = new Vector();
        				li = v.listIterator();
        				list_npm = new String("`");
        			}
        			String npmhs = rs.getString(1);
        			if(!list_npm.contains("`"+npmhs+"`")) {
        				list_npm = list_npm+npmhs+"`";	
        			}
        			
        		}	
    		}
    		
    		
    		if(list_npm!=null) {
    			//CEK BILA PERNAH DATA LULUS DI THSMS DULU-2NYA
    			stmt = con.prepareStatement("select NPMHS from TRLSM where THSMS<? and NPMHS=? and (STMHS=? or STMHS=? or STMHS=?) limit 1"); 
    			st = new StringTokenizer(list_npm,"`");
    			list_npm = null;
    			while(st.hasMoreTokens()) {
    				String npmhs = st.nextToken();
    				stmt.setString(1, target_thsms);
    				stmt.setString(2, npmhs);
    				stmt.setString(3, "K");
    				stmt.setString(4, "D");
    				stmt.setString(5, "L");
    				rs = stmt.executeQuery();
    				if(!rs.next()) {
    					if(list_npm==null) {
    						list_npm = new String(npmhs+"`");
    					}
    					else {
    						list_npm = list_npm+npmhs+"`";
    					}
    				}
    			}
    		}
    		//list_npm = list_npm.substring(1,list_npm.length()-1);
    		
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
    	
    	return list_npm;
    }
    
    public String getMhsAktifVersiPddikti(String target_thsms) {
    	/*
    	 * Versi ini : berdasarkan krs & digunakan pada saat based thsms setelah itu pake versi dibawah ini
    	 */
    	////System.out.println("mhs aktif "+target_thsms);
    	ListIterator li = null;
		StringTokenizer st = null;
		Vector v = null;
		String list_npm = null;
		try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		//1.cek trnlm
    		stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=?");
    		stmt.setString(1, target_thsms);
    		rs = stmt.executeQuery();
    		
    		while(rs.next()) {
    			if(v==null) {
    				v = new Vector();
    				li = v.listIterator();
    				list_npm = new String("`");
    			}
    			String npmhs = rs.getString(1);
    			list_npm = list_npm+npmhs+"`";
    		}
    		//pastikan mhs yg smawl = target_thsms juga dihitung mhs aktif
    		//CIVITAS a inner join OBJECT b on a.ID_OBJ=b.ID_OBJ where SMAWLMSMHS=? and OBJ_NAME='MHS'
    		stmt = con.prepareStatement("SELECT NPMHSMSMHS FROM CIVITAS a inner join OBJECT b on a.ID_OBJ=b.ID_OBJ where SMAWLMSMHS=? and OBJ_NAME='MHS'");
    		//stmt = con.prepareStatement("SELECT NPMHSMSMHS FROM CIVITAS where SMAWLMSMHS=? and KDPSTMSMHS>'1000'");
    		stmt.setString(1, target_thsms);
    		//stmt.setString(2, kdpst);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			if(v==null) {
    				v = new Vector();
    				li = v.listIterator();
    				list_npm = new String("`");
    			}
    			String npmhs = rs.getString(1);
    			if(!list_npm.contains(npmhs)) {
    				list_npm = list_npm+npmhs+"`";	
    			}
    		}	
    		//2.cek trlsm (AKTIF = distinct trnlm + (N + C)@thsms 
    		stmt = con.prepareStatement("select NPMHS from TRLSM where THSMS=? and (STMHS=? or STMHS=?)");
    		stmt.setString(1, target_thsms);
    		stmt.setString(2, "C");
    		stmt.setString(3, "N");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			if(v==null) {
    				v = new Vector();
    				li = v.listIterator();
    				list_npm = new String("`");
    			}
    			String npmhs = rs.getString(1);
    			if(!list_npm.contains("`"+npmhs+"`")) {
    				list_npm = list_npm+npmhs+"`";	
    			}
    			
    		}
    		
    		if(list_npm!=null) {
    			//CEK BILA PERNAH DATA LULUS DI THSMS DULU-2NYA
    			stmt = con.prepareStatement("select NPMHS from TRLSM where THSMS<? and NPMHS=? and (STMHS=? or STMHS=? or STMHS=?) limit 1"); 
    			st = new StringTokenizer(list_npm,"`");
    			list_npm = null;
    			while(st.hasMoreTokens()) {
    				String npmhs = st.nextToken();
    				stmt.setString(1, target_thsms);
    				stmt.setString(2, npmhs);
    				stmt.setString(3, "K");
    				stmt.setString(4, "D");
    				stmt.setString(5, "L");
    				rs = stmt.executeQuery();
    				if(!rs.next()) {
    					if(list_npm==null) {
    						list_npm = new String(npmhs+"`");
    					}
    					else {
    						list_npm = list_npm+npmhs+"`";
    					}
    				}
    			}
    		}
    		//list_npm = list_npm.substring(1,list_npm.length()-1);
    		
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
    	
    	return list_npm;
    }
    
    
    /*
     * DEPRECATED - pake v1
     */
    public Vector getMhsAktif(String target_thsms) {
    	
    	ListIterator li = null;
		StringTokenizer st = null;
		Vector v = null;
		String thsms_1 = Tool.returnPrevThsmsGivenTpAntara(target_thsms);
		String list_npm = getMhsAktifVersiPddikti(thsms_1);
		/*
		 * getMhsAktifVersiPddikti(thsms) = mhs krs+cuti+nonaktif-(sdh out < thsms) 
		 */
		if(list_npm!=null) {
			st = new StringTokenizer(list_npm,"`");
			try {
	    		Context initContext  = new InitialContext();
	    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
	    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
	    		con = ds.getConnection();
	    		//1.minus  yg sudah keluar/lulus/di thsms lalu, krn getMhsAktifVersiPddikti(thsms) tidak dihitung
	    		int counter=1;
	    		stmt = con.prepareStatement("select THSMS from TRLSM where THSMS=? and NPMHS=? and (STMHS='L' or STMHS='K' or STMHS='D') limit 1");
	    		while(st.hasMoreTokens()) {
	    			String npmhs = st.nextToken();
	    			////System.out.println(counter+++". "+npmhs);
	    			stmt.setString(1, thsms_1);
	    			stmt.setString(2, npmhs);
	    			rs = stmt.executeQuery();
	    			if(!rs.next()) {
	    				if(v==null) {
	    					v = new Vector();
	    					li = v.listIterator();
	    				}
	    				li.add(npmhs);
	    			}
	    		}
	    		//2. tambah mahasiswa baru target_thsms
	    		stmt = con.prepareStatement("select NPMHSMSMHS from CIVITAS a inner join OBJECT b on a.ID_OBJ=b.ID_OBJ where SMAWLMSMHS=? and OBJ_NAME='MHS'");
	    		//stmt = con.prepareStatement("select NPMHSMSMHS from CIVITAS where SMAWLMSMHS=? and KDPSTMSMHS>'1001'");
	    		//select NPMHSMSMHS from CIVITAS a inner join OBJECT b on a.ID_OBJ=b.ID_OBJ where SMAWLMSMHS='20161' and OBJ_NAME='MHS'
	    		stmt.setString(1, target_thsms);
	    		rs = stmt.executeQuery();
	    		while(rs.next()) {
	    			String npmhs = rs.getString(1);
	    			if(v==null) {
    					v = new Vector();
    					li = v.listIterator();
    				}
	    			
	    			li.add(npmhs);
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
    	return v;
    }
    

    

    
    public Vector getMhsAktifStatusQuo(String target_thsms) {
    	
    	ListIterator li = null;
		StringTokenizer st = null;
		Vector v = getMhsAktif(target_thsms);
		if(v!=null) {
			li = v.listIterator();
			try {
	    		Context initContext  = new InitialContext();
	    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
	    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
	    		con = ds.getConnection();
	    		//1.cek apa ada keterangan di trlsm
	    		//int counter=1;
	    		stmt = con.prepareStatement("select THSMS from TRLSM where THSMS=? and NPMHS=? and (STMHS='C' or STMHS='N' or STMHS='K') limit 1");
	    		while(li.hasNext()) {
	    			String npmhs = (String)li.next();
	    			stmt.setString(1, target_thsms);
	    			stmt.setString(2, npmhs);
	    			rs = stmt.executeQuery();
	    			if(rs.next()) {
	    				li.remove();
	    			}
	    		}
	    		//2. cek apa ada krs
	    		li = v.listIterator();
	    		stmt = con.prepareStatement("select THSMSTRNLM from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=? limit 1");
	    		while(li.hasNext()) {
	    			String npmhs = (String)li.next();
	    			stmt.setString(1, target_thsms);
	    			stmt.setString(2, npmhs);
	    			rs = stmt.executeQuery();
	    			if(rs.next()) {
	    				li.remove();
	    			}
	    		}	
	    		//3. add info malaikat
	    		if(v!=null) {
	    			stmt = con.prepareStatement("select KDJENMSMHS,KDPSTMSMHS,NMMHSMSMHS,NIMHSMSMHS,SMAWLMSMHS,STPIDMSMHS,MALAIKAT from CIVITAS where NPMHSMSMHS=?");
	    			li = v.listIterator();
	    			while(li.hasNext()) {
	    				String npmhs = (String)li.next();
	    				stmt.setString(1, npmhs);
	    				rs = stmt.executeQuery();
	    				String kdjen = "null";
	    				String kdpst = "null";
	    				String nmmhs = "null";
	    				String nimhs = "null";
	    				String smawl = "null";
	    				String stpid = "null";
	    				String angel = "null";
	    				if(rs.next()) {
	    					kdjen = rs.getString(1);
	    					kdpst = rs.getString(2);
	    					nmmhs = rs.getString(3);
	    					nimhs = rs.getString(4);
	    					smawl = rs.getString(5);
	    					stpid = rs.getString(6);
	    					angel = ""+rs.getBoolean(7);
	    					
	    				}
	    				li.set(kdpst+"`"+npmhs+"`"+nmmhs+"`"+nimhs+"`"+smawl+"`"+stpid+"`"+angel+"`"+kdjen);
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
    	return v;
    }
    
    
    public Vector getMhsAktifStatusQuo(String target_thsms, boolean filter_if_true_malaikat_if_false_ril) {
    	
    	ListIterator li = null;
		StringTokenizer st = null;
		Vector v = getMhsAktif(target_thsms);
		//System.out.println("getMhsAktif="+v.size());
		if(v!=null) {
			li = v.listIterator();
			try {
	    		Context initContext  = new InitialContext();
	    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
	    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
	    		con = ds.getConnection();
	    		//1.cek apa ada keterangan di trlsm
	    		//int counter=1;
	    		stmt = con.prepareStatement("select THSMS from TRLSM where THSMS=? and NPMHS=? and (STMHS='C' or STMHS='N' or STMHS='K') limit 1");
	    		while(li.hasNext()) {
	    			String npmhs = (String)li.next();
	    			stmt.setString(1, target_thsms);
	    			stmt.setString(2, npmhs);
	    			rs = stmt.executeQuery();
	    			if(rs.next()) {
	    				li.remove();
	    			}
	    		}
	    		//System.out.println("getMhsAktif1="+v.size());
	    		//2. cek apa ada krs
	    		li = v.listIterator();
	    		stmt = con.prepareStatement("select THSMSTRNLM from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=? limit 1");
	    		while(li.hasNext()) {
	    			String npmhs = (String)li.next();
	    			stmt.setString(1, target_thsms);
	    			stmt.setString(2, npmhs);
	    			rs = stmt.executeQuery();
	    			if(rs.next()) {
	    				li.remove();
	    			}
	    		}	
	    		//System.out.println("getMhsAktif2="+v.size());
	    		//3. add info malaikat
	    		if(v!=null) {
	    			stmt = con.prepareStatement("select KDJENMSMHS,KDPSTMSMHS,NMMHSMSMHS,NIMHSMSMHS,SMAWLMSMHS,STPIDMSMHS,MALAIKAT from CIVITAS where NPMHSMSMHS=?");
	    			li = v.listIterator();
	    			while(li.hasNext()) {
	    				String npmhs = (String)li.next();
	    				stmt.setString(1, npmhs);
	    				rs = stmt.executeQuery();
	    				String kdjen = "null";
	    				String kdpst = "null";
	    				String nmmhs = "null";
	    				String nimhs = "null";
	    				String smawl = "null";
	    				String stpid = "null";
	    				String angel = "null";
	    				if(rs.next()) {
	    					kdjen = rs.getString(1);
	    					kdpst = rs.getString(2);
	    					nmmhs = rs.getString(3);
	    					nimhs = rs.getString(4);
	    					smawl = rs.getString(5);
	    					stpid = rs.getString(6);
	    					angel = ""+rs.getBoolean(7);
	    					
	    				}
	    				if(filter_if_true_malaikat_if_false_ril) {
	    					//malaiakt
	    					if(angel.equalsIgnoreCase("true")) {
	    						li.set(kdpst+"`"+npmhs+"`"+nmmhs+"`"+nimhs+"`"+smawl+"`"+stpid+"`"+angel+"`"+kdjen);		
	    					}
	    					else {
	    						li.remove();
	    					}
	    				}
	    				else {
	    					//mhs rill
	    					if(angel.equalsIgnoreCase("false")) {
	    						li.set(kdpst+"`"+npmhs+"`"+nmmhs+"`"+nimhs+"`"+smawl+"`"+stpid+"`"+angel+"`"+kdjen);		
	    					}
	    					else {
	    						li.remove();
	    					}
	    				}
	    				
	    			}
	    			//System.out.println("getMhsAktif3="+v.size());
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
    	return v;
    }
    
    public Vector getListNpmAndKoMhsBasedOnStpid(String smawl, String stpid) {
    	Vector v = null;
    	ListIterator li = null;
		StringTokenizer st = null;
		
		try {
	    	Context initContext  = new InitialContext();
	    	Context envContext  = (Context)initContext.lookup("java:/comp/env");
	    	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
	    	con = ds.getConnection();
	    	//1.cek apa ada keterangan di trlsm
	    	//int counter=1;
	    	//stmt = con.prepareStatement("select NPMHSMSMHS,KRKLMMSMHS from CIVITAS inner join EXT_CIVITAS where STPIDMSMHS=? and SMAWLMSMHS=?");
	    	stmt = con.prepareStatement("select NPMHSMSMHS from CIVITAS where STPIDMSMHS=? and SMAWLMSMHS=?");
	    	stmt.setString(1, stpid);
	    	stmt.setString(2, smawl);
	    	if(rs.next()) {
	    		v = new Vector();
	    		li = v.listIterator();
	    		String npmhs = rs.getString(1);
    			li.add(npmhs);
	    		do {
	    			npmhs = rs.getString(1);
	    			li.add(npmhs);
	    			
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
    
    /*
     * Versi terbaru 
     * get list mahasiswa yg ada trnlm sesuai scope dan angel_included
     */
    public Vector getListNpmhsYgAdaDiTrnlm(String target_thsms, Vector v_scope_id, boolean angel_included) {
    	Vector v_scope_kdpst = null;
    	ListIterator li = null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		
        		v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
            	
                //boolean first = true;
                li = v_scope_kdpst.listIterator();
                while(li.hasNext()) {
                	String sql_cmd = null;
                    String info_scope = (String)li.next();
                    String list_npm = null; //per kampus soalnya
                    StringTokenizer st = new StringTokenizer(info_scope,"`");
                    if(st.countTokens()>1) {
                    //	if(first) {
                      //         first = false;
                    	sql_cmd = new String("");
                        //}
                        //else {
                        //       sql_cmd = sql_cmd+" or ";
                        //}
                        String kdkmp = st.nextToken();
                        while(st.hasMoreTokens()) {
                        	String kdpst = st.nextToken();
                            sql_cmd = sql_cmd+"KDPSTTRNLM='"+kdpst+"'";
                            if(st.hasMoreTokens()) {
                            	sql_cmd = sql_cmd+" or ";
                            }
                        }
                        if(!Checker.isStringNullOrEmpty(sql_cmd)) {
                        	if(angel_included) {
                    			sql_cmd = new String("select distinct KDPSTTRNLM,NPMHSTRNLM from TRNLM inner join CIVITAS A on NPMHSTRNLM=NPMHSMSMHS inner join OBJECT B on A.ID_OBJ=B.ID_OBJ where THSMSTRNLM=? and ("+sql_cmd+") and KODE_KAMPUS_DOMISILI=? order by KDPSTTRNLM,NPMHSTRNLM;");
                    			stmt = con.prepareStatement(sql_cmd);
                    			stmt.setString(1, target_thsms);
                    			stmt.setString(2, kdkmp);
                    		}
                    		else {
                    			sql_cmd = new String("select distinct KDPSTTRNLM,NPMHSTRNLM from TRNLM inner join CIVITAS A on NPMHSTRNLM=NPMHSMSMHS inner join OBJECT B on A.ID_OBJ=B.ID_OBJ where THSMSTRNLM=? and KODE_KAMPUS_DOMISILI=? and MALAIKAT=? and ("+sql_cmd+") order by KDPSTTRNLM,NPMHSTRNLM;");
                    			////System.out.println("sqlcmd="+sql_cmd);
                    			stmt = con.prepareStatement(sql_cmd);
                    			stmt.setString(1, target_thsms);
                    			stmt.setString(2, kdkmp);
                    			stmt.setBoolean(3, false);
                    		}
                    		rs = stmt.executeQuery();
                        	while(rs.next()) {
                        		
                        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
                        		String kdpst = ""+rs.getString(1);
                        		String npmhs = ""+rs.getString(2);
                        		if(list_npm==null) {
                        			list_npm=new String(kdpst+","+npmhs);
                        		}
                        		else {
                        			list_npm=list_npm+","+kdpst+","+npmhs;
                        		}
                        		//li.add(kdpst);
                        		li.set(info_scope+"~"+list_npm);
                        	}
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
        	finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        		if (con!=null) try { con.close();} catch (Exception ignore){}
        	}       	
    	}
    	
    	return v_scope_kdpst;
    }
    
    
    public Vector getListNpmhsYgAdaDiTrnlm(String target_thsms, String kdpst, boolean angel_included) {
    	ListIterator li = null;
    	Vector v_listed=null;
    	
    	try {
			Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		
    		if(!Checker.isStringNullOrEmpty(kdpst)) {
    			String sql_cmd = "KDPSTTRNLM='"+kdpst+"'";
                if(!Checker.isStringNullOrEmpty(sql_cmd)) {
                	if(angel_included) {
            			sql_cmd = new String("select distinct KDPSTTRNLM,NPMHSTRNLM,NMMHSMSMHS,SMAWLMSMHS,BTSTUMSMHS from TRNLM inner join CIVITAS A on NPMHSTRNLM=NPMHSMSMHS inner join OBJECT B on A.ID_OBJ=B.ID_OBJ where THSMSTRNLM=? and ("+sql_cmd+")  order by KDPSTTRNLM,NPMHSTRNLM;");
            			stmt = con.prepareStatement(sql_cmd);
            			stmt.setString(1, target_thsms);
            			
            		}
            		else {
            			sql_cmd = new String("select distinct KDPSTTRNLM,NPMHSTRNLM,NMMHSMSMHS,SMAWLMSMHS,BTSTUMSMHS from TRNLM inner join CIVITAS A on NPMHSTRNLM=NPMHSMSMHS inner join OBJECT B on A.ID_OBJ=B.ID_OBJ where THSMSTRNLM=? and MALAIKAT=? and ("+sql_cmd+") order by KDPSTTRNLM,NPMHSTRNLM;");
            			////System.out.println("sqlcmd="+sql_cmd);
            			stmt = con.prepareStatement(sql_cmd);
            			stmt.setString(1, target_thsms);
            			stmt.setBoolean(2, false);
            		}
            		rs = stmt.executeQuery();
            		boolean first = true;
                	while(rs.next()) {
                		String list_npm = "";
                		//String kdpst = ""+rs.getString("KDPSTTRNLM");
                		kdpst = ""+rs.getString(1);
                		String npmhs = ""+rs.getString(2);
                		String nmmhs = ""+rs.getString(3);
                		String smawl = ""+rs.getString(4);
                		String btstu = ""+rs.getString(5);
                		if(first) {
                			first = false;
                			v_listed = new Vector();
                			li = v_listed.listIterator();
                			
                		}
                		list_npm=kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+btstu;
                		if(list_npm.startsWith("`")) {
                			list_npm = "null"+list_npm;
                		}
                		if(list_npm.endsWith("`")) {
                			list_npm = list_npm+"null";
                		}
                		while(list_npm.contains("``")) {
                			list_npm=list_npm.replace("``", "`null`");
                		}
                		li.add(list_npm);
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
        finally {
        	if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        	if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        	if (con!=null) try { con.close();} catch (Exception ignore){}
        }       	
    	
    	
    	return v_listed;
    }
    
    public Vector getListNpmhsYgCutiOrLulus(String target_thsms, String kdpst, boolean angel_included) {
    	ListIterator li = null;
    	Vector v_listed=null;
    	
    	try {
			Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		
    		if(!Checker.isStringNullOrEmpty(kdpst)) {
    			String sql_cmd = "T.KDPST='"+kdpst+"'";
                if(!Checker.isStringNullOrEmpty(sql_cmd)) {
                	if(angel_included) {
            			sql_cmd = new String("select distinct T.KDPST,NPMHS,NMMHSMSMHS,SMAWLMSMHS,BTSTUMSMHS from TRLSM T inner join CIVITAS A on NPMHS=NPMHSMSMHS inner join OBJECT B on A.ID_OBJ=B.ID_OBJ where THSMS=? and (STMHS='C' or STMHS='L') and ("+sql_cmd+")");
            			
            		}
            		else {
            			sql_cmd = new String("select distinct T.KDPST,NPMHS,NMMHSMSMHS,SMAWLMSMHS,BTSTUMSMHS from TRLSM T inner join CIVITAS A on NPMHS=NPMHSMSMHS inner join OBJECT B on A.ID_OBJ=B.ID_OBJ where THSMS=? and (STMHS='C' or STMHS='L') and ("+sql_cmd+") and MALAIKAT=false");
            			
            		}
                	stmt = con.prepareStatement(sql_cmd);
                	stmt.setString(1, target_thsms);
            		rs = stmt.executeQuery();
            		boolean first = true;
                	while(rs.next()) {
                		String list_npm = "";
                		//String kdpst = ""+rs.getString("KDPSTTRNLM");
                		kdpst = ""+rs.getString(1);
                		String npmhs = ""+rs.getString(2);
                		String nmmhs = ""+rs.getString(3);
                		String smawl = ""+rs.getString(4);
                		String btstu = ""+rs.getString(5);
                		if(first) {
                			first = false;
                			v_listed = new Vector();
                			li = v_listed.listIterator();
                			
                		}
                		list_npm=kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+btstu;
                		if(list_npm.startsWith("`")) {
                			list_npm = "null"+list_npm;
                		}
                		if(list_npm.endsWith("`")) {
                			list_npm = list_npm+"null";
                		}
                		while(list_npm.contains("``")) {
                			list_npm=list_npm.replace("``", "`null`");
                		}
                		li.add(list_npm);
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
        finally {
        	if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        	if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        	if (con!=null) try { con.close();} catch (Exception ignore){}
        }       	
    	
    	
    	return v_listed;
    }
    
    /*
     * versi terbaru
     * get list npm given token status c`k`d
     */
    public Vector getListNpmhsGivenTknStmhs(String target_thsms, Vector v_scope_id, boolean angel_included, String tkn_stmhs) {
    	Vector v_scope_kdpst = null;
    	ListIterator li = null;
    	if(v_scope_id!=null && v_scope_id.size()>0 && !Checker.isStringNullOrEmpty(tkn_stmhs)) {
    		try {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
            	li = v_scope_kdpst.listIterator();
                while(li.hasNext()) {
                	String sql_cmd = null;
                    String info_scope = (String)li.next();
                    String list_npm = null; //per kampus soalnya
                    StringTokenizer st = new StringTokenizer(info_scope,"`");
                    if(st.countTokens()>1) {
                    //	if(first) {
                      //         first = false;
                    	sql_cmd = new String("");
                        //}
                        //else {
                        //       sql_cmd = sql_cmd+" or ";
                        //}
                        String kdkmp = st.nextToken();
                        while(st.hasMoreTokens()) {
                        	String kdpst = st.nextToken();
                            sql_cmd = sql_cmd+"A.KDPST='"+kdpst+"'";
                            if(st.hasMoreTokens()) {
                            	sql_cmd = sql_cmd+" or ";
                            }
                        }
                        String scope_stmhs = null;
                        st = null;
                        if(tkn_stmhs.contains("`")) {
                        	st = new StringTokenizer(tkn_stmhs,"`");
                        }
                        else if(tkn_stmhs.contains(",")) {
                        	st = new StringTokenizer(tkn_stmhs,",");
                        }
                        else if(tkn_stmhs.contains("~")) {
                        	st = new StringTokenizer(tkn_stmhs,"~");
                        }
                        if(st.hasMoreTokens() ) {
                        	scope_stmhs = new String("STMHS='"+st.nextToken()+"'");
                        	while(st.hasMoreTokens()) {
                        		scope_stmhs = scope_stmhs+" or STMHS='"+st.nextToken()+"'";
                        	}
                        }
                        if(!Checker.isStringNullOrEmpty(sql_cmd)) {
                        	if(angel_included) {
                        		sql_cmd = new String("select distinct A.KDPST,A.NPMHS from TRLSM A inner join CIVITAS B on NPMHS=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ where A.THSMS=? and ("+sql_cmd+") and ("+scope_stmhs+") and KODE_KAMPUS_DOMISILI=? order by KDPST,NPMHS");
                    			stmt = con.prepareStatement(sql_cmd);
                    			stmt.setString(1, target_thsms);
                    			stmt.setString(2, kdkmp);
                    			////System.out.println("cmd=select distinct KDPST,NPMHS from TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS where THSMST=? and ("+sql_cmd+") and ("+scope_stmhs+") order by NPMHS");
                    		}
                    		else {
                    			sql_cmd = new String("select distinct A.KDPST,A.NPMHS from TRLSM A inner join CIVITAS B on NPMHS=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ where A.THSMS=? and MALAIKAT=? and ("+sql_cmd+") and ("+scope_stmhs+") and KODE_KAMPUS_DOMISILI=?  order by KDPST,NPMHS");
                    			stmt = con.prepareStatement(sql_cmd);
                    			////System.out.println("cmd=select distinct KDPST,NPMHS from TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and MALAIKAT=? and ("+sql_cmd+") and ("+scope_stmhs+") order by NPMHS");
                    			stmt.setString(1, target_thsms);
                    			stmt.setBoolean(2, false);
                    			stmt.setString(3, kdkmp);
                    		}
                    		rs = stmt.executeQuery();
                        	while(rs.next()) {
                        		
                        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
                        		String kdpst = ""+rs.getString(1);
                        		String npmhs = ""+rs.getString(2);
                        		if(list_npm==null) {
                        			list_npm=new String(kdpst+","+npmhs);
                        		}
                        		else {
                        			list_npm=list_npm+","+kdpst+","+npmhs;
                        		}
                        		//li.add(kdpst);
                        		li.set(info_scope+"~"+list_npm);
                        	}
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
        	finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        		if (con!=null) try { con.close();} catch (Exception ignore){}
        	}       	
    	}
    	
    	return v_scope_kdpst;
    }
    
    
    public Vector getListNpmhsPengajuanDaftarUlang(String target_thsms, Vector v_scope_id) {
    	Vector v_scope_kdpst = null;
    	int tot_mhs = 0;
    	int tot_wip = 0;
    	ListIterator li = null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		
        		v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
            	li = v_scope_kdpst.listIterator();
                while(li.hasNext()) {
                	String sql_cmd = null;
                    String info_scope = (String)li.next();
                    String list_npm = null; //per kampus soalnya
                    StringTokenizer st = new StringTokenizer(info_scope,"`");
                    if(st.countTokens()>1) {
                    	sql_cmd = new String("");

                        String kdkmp = st.nextToken();
                        while(st.hasMoreTokens()) {
                        	String kdpst = st.nextToken();
                            sql_cmd = sql_cmd+"A.KDPST='"+kdpst+"'";
                            if(st.hasMoreTokens()) {
                            	sql_cmd = sql_cmd+" or ";
                            }
                        }
                        
                        if(!Checker.isStringNullOrEmpty(sql_cmd)) {
                        	//sql_cmd = new String("select distinct KDPST,NPMHS,NMMHSMSMHS,SMAWLMSMHS,ALL_APPROVED from DAFTAR_ULANG inner join CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and ("+sql_cmd+") order by KDPST,ALL_APPROVED,NPMHS");
                        	sql_cmd = new String("select distinct A.KDPST,NPMHS,NMMHSMSMHS,SMAWLMSMHS from DAFTAR_ULANG A inner join CIVITAS B on NPMHS=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ where THSMS=? and ("+sql_cmd+") and KODE_KAMPUS_DOMISILI=? order by A.KDPST,NPMHS");
                    		////System.out.println("sql == "+sql_cmd);;
                        	stmt = con.prepareStatement(sql_cmd);
                    		stmt.setString(1, target_thsms);
                    		stmt.setString(2, kdkmp);
                    		rs = stmt.executeQuery();
                    		while(rs.next()) {
                        		
                        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
                        		String kdpst = ""+rs.getString(1);
                        		String npmhs = ""+rs.getString(2);
                        		String nmmhs = ""+rs.getString(3);
                        		String smawl = ""+rs.getString(4);
                        		//String approved = ""+rs.getBoolean(5);
                        		if(list_npm==null) {
                        			//list_npm=new String(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved);
                        			list_npm=new String(kdpst+","+npmhs+","+nmmhs+","+smawl);
                        		}
                        		else {
                        			//list_npm=list_npm+"`"+kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved;
                        			list_npm=list_npm+","+kdpst+","+npmhs+","+nmmhs+","+smawl;
                        		}
                        		//li.add(kdpst);
                        		li.set(info_scope+"~"+list_npm);
                        	}
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
        	finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        		if (con!=null) try { con.close();} catch (Exception ignore){}
        	}       	
    	}
    	
    	return v_scope_kdpst;
    }
    
    
    public Vector getListNpmhsPengajuanDaftarUlangWip(String target_thsms, Vector v_scope_id) {
    	Vector v_scope_kdpst = null;
    	int tot_mhs = 0;
    	int tot_wip = 0;
    	ListIterator li = null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		
        		v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
            	li = v_scope_kdpst.listIterator();
                while(li.hasNext()) {
                	String sql_cmd = null;
                    String info_scope = (String)li.next();
                    String list_npm = null; //per kampus soalnya
                    StringTokenizer st = new StringTokenizer(info_scope,"`");
                    if(st.countTokens()>1) {
                    	sql_cmd = new String("");

                        String kdkmp=st.nextToken();
                        while(st.hasMoreTokens()) {
                        	String kdpst = st.nextToken();
                            sql_cmd = sql_cmd+"A.KDPST='"+kdpst+"'";
                            if(st.hasMoreTokens()) {
                            	sql_cmd = sql_cmd+" or ";
                            }
                        }
                        
                        if(!Checker.isStringNullOrEmpty(sql_cmd)) {
                        	//sql_cmd = new String("select distinct KDPST,NPMHS,NMMHSMSMHS,SMAWLMSMHS,ALL_APPROVED from DAFTAR_ULANG inner join CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and ALL_APPROVED=? and ("+sql_cmd+") order by KDPST,ALL_APPROVED,NPMHS");
                        	sql_cmd = new String("select distinct A.KDPST,NPMHS,NMMHSMSMHS,SMAWLMSMHS from DAFTAR_ULANG A inner join CIVITAS B on NPMHS=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ where THSMS=? and ALL_APPROVED=? and ("+sql_cmd+") and KODE_KAMPUS_DOMISILI=? order by A.KDPST,NPMHS");
                    		stmt = con.prepareStatement(sql_cmd);
                    		stmt.setString(1, target_thsms);
                    		stmt.setBoolean(2, false);
                    		stmt.setString(3, kdkmp);
                    		rs = stmt.executeQuery();
                    		while(rs.next()) {
                        		
                        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
                        		String kdpst = ""+rs.getString(1);
                        		String npmhs = ""+rs.getString(2);
                        		String nmmhs = ""+rs.getString(3);
                        		String smawl = ""+rs.getString(4);
                        		//String approved = ""+rs.getBoolean(5);
                        		if(list_npm==null) {
                        			//list_npm=new String(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved);
                        			list_npm=new String(kdpst+","+npmhs+","+nmmhs+","+smawl);
                        		}
                        		else {
                        			//list_npm=list_npm+"`"+kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved;
                        			list_npm=list_npm+","+kdpst+","+npmhs+","+nmmhs+","+smawl;
                        		}
                        		//li.add(kdpst);
                        		li.set(info_scope+"~"+list_npm);
                        	}
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
        	finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        		if (con!=null) try { con.close();} catch (Exception ignore){}
        	}       	
    	}
    	
    	return v_scope_kdpst;
    }
    
    public Vector getListNpmhsPengajuanPindahProdiOut(String target_thsms, Vector v_scope_id) {
    	Vector v_scope_kdpst = null;
    	int tot_mhs = 0;
    	int tot_wip = 0;
    	ListIterator li = null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		
        		v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
            	li = v_scope_kdpst.listIterator();
                while(li.hasNext()) {
                	String sql_cmd = null;
                    String info_scope = (String)li.next();
                    String list_npm = null; //per kampus soalnya
                    StringTokenizer st = new StringTokenizer(info_scope,"`");
                    if(st.countTokens()>1) {
                    	sql_cmd = new String("");

                        String kdkmp=st.nextToken();
                        while(st.hasMoreTokens()) {
                        	String kdpst = st.nextToken();
                            sql_cmd = sql_cmd+"KDPSTMSMHS='"+kdpst+"'";
                            if(st.hasMoreTokens()) {
                            	sql_cmd = sql_cmd+" or ";
                            }
                        }
                        
                        if(!Checker.isStringNullOrEmpty(sql_cmd)) {
                        	//sql_cmd = SELECT KDPSTMSMHS,NPMHSMSMHS,NMMHSMSMHS,SMAWLMSMHS,TARGET_KDPST,LOCKED  FROM USG.TOPIK_PENGAJUAN inner join CIVITAS on CREATOR_NPM=NPMHSMSMHS  where BATAL=false and REJECTED is null and TARGET_THSMS_PENGAJUAN='20162' and  TIPE_PENGAJUAN='PINDAH_PRODI';
                        	sql_cmd = new String("select KDPSTMSMHS,NPMHSMSMHS,NMMHSMSMHS,SMAWLMSMHS,TARGET_KDPST,LOCKED FROM USG.TOPIK_PENGAJUAN inner join CIVITAS B on CREATOR_NPM=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ  where BATAL=false and REJECTED is null and TARGET_KDPST is not null and TARGET_THSMS_PENGAJUAN=? and  TIPE_PENGAJUAN='PINDAH_PRODI' and ("+sql_cmd+") and KODE_KAMPUS_DOMISILI=? order by KDPSTMSMHS,LOCKED,NPMHSMSMHS");
                        	////System.out.println("sql cmd="+sql_cmd);
                    		stmt = con.prepareStatement(sql_cmd);
                    		stmt.setString(1, target_thsms);
                    		stmt.setString(2, kdkmp);
                    		rs = stmt.executeQuery();
                    		while(rs.next()) {
                        		
                        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
                        		String kdpst = ""+rs.getString(1);
                        		String npmhs = ""+rs.getString(2);
                        		String nmmhs = ""+rs.getString(3);
                        		String smawl = ""+rs.getString(4);
                        		String target_kdpst = ""+rs.getString(5);
                        		String locked = ""+rs.getString(6);
                        		
                        		
                        		//String approved = ""+rs.getBoolean(5);
                        		if(list_npm==null) {
                        			//list_npm=new String(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved);
                        			list_npm=new String(kdpst+","+npmhs+","+nmmhs+","+smawl+","+target_kdpst+","+locked);
                        		}
                        		else {
                        			//list_npm=list_npm+"`"+kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved;
                        			list_npm=list_npm+","+kdpst+","+npmhs+","+nmmhs+","+smawl+","+target_kdpst+","+locked;
                        		}
                        		//li.add(kdpst);
                        		li.set(info_scope+"~"+list_npm);
                        	}
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
        	finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        		if (con!=null) try { con.close();} catch (Exception ignore){}
        	}       	
    	}
    	
    	return v_scope_kdpst;
    }
    
    public Vector getListNpmhsPengajuanPindahProdiIn(String target_thsms, Vector v_scope_id) {
    	Vector v_scope_kdpst = null;
    	int tot_mhs = 0;
    	int tot_wip = 0;
    	ListIterator li = null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		
        		v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
            	li = v_scope_kdpst.listIterator();
                while(li.hasNext()) {
                	String sql_cmd = null;
                    String info_scope = (String)li.next();
                    String list_npm = null; //per kampus soalnya
                    StringTokenizer st = new StringTokenizer(info_scope,"`");
                    if(st.countTokens()>1) {
                    	sql_cmd = new String("");

                        String kdkmp=st.nextToken();
                        while(st.hasMoreTokens()) {
                        	String kdpst = st.nextToken();
                            sql_cmd = sql_cmd+"TARGET_KDPST='"+kdpst+"'";
                            if(st.hasMoreTokens()) {
                            	sql_cmd = sql_cmd+" or ";
                            }
                        }
                        
                        if(!Checker.isStringNullOrEmpty(sql_cmd)) {
                        	//sql_cmd = SELECT KDPSTMSMHS,NPMHSMSMHS,NMMHSMSMHS,SMAWLMSMHS,TARGET_KDPST,LOCKED  FROM USG.TOPIK_PENGAJUAN inner join CIVITAS on CREATOR_NPM=NPMHSMSMHS  where BATAL=false and REJECTED is null and TARGET_THSMS_PENGAJUAN='20162' and  TIPE_PENGAJUAN='PINDAH_PRODI';
                        	sql_cmd = new String("select KDPSTMSMHS,NPMHSMSMHS,NMMHSMSMHS,SMAWLMSMHS,TARGET_KDPST,LOCKED FROM USG.TOPIK_PENGAJUAN inner join CIVITAS B on CREATOR_NPM=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ  where BATAL=false and REJECTED is null and TARGET_THSMS_PENGAJUAN=? and  TIPE_PENGAJUAN='PINDAH_PRODI' and ("+sql_cmd+") and KODE_KAMPUS_DOMISILI=? order by KDPSTMSMHS,LOCKED,NPMHSMSMHS");
                        	////System.out.println("sql cmd="+sql_cmd);
                    		stmt = con.prepareStatement(sql_cmd);
                    		stmt.setString(1, target_thsms);
                    		stmt.setString(2, kdkmp);
                    		rs = stmt.executeQuery();
                    		while(rs.next()) {
                        		
                        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
                        		String kdpst = ""+rs.getString(1);
                        		String npmhs = ""+rs.getString(2);
                        		String nmmhs = ""+rs.getString(3);
                        		String smawl = ""+rs.getString(4);
                        		String target_kdpst = ""+rs.getString(5);
                        		String locked = ""+rs.getString(6);
                        		
                        		
                        		//String approved = ""+rs.getBoolean(5);
                        		if(list_npm==null) {
                        			//list_npm=new String(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved);
                        			list_npm=new String(kdpst+","+npmhs+","+nmmhs+","+smawl+","+target_kdpst+","+locked);
                        		}
                        		else {
                        			//list_npm=list_npm+"`"+kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved;
                        			list_npm=list_npm+","+kdpst+","+npmhs+","+nmmhs+","+smawl+","+target_kdpst+","+locked;
                        		}
                        		//li.add(kdpst);
                        		li.set(info_scope+"~"+list_npm);
                        	}
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
        	finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        		if (con!=null) try { con.close();} catch (Exception ignore){}
        	}       	
    	}
    	
    	return v_scope_kdpst;
    }
    
    
    public Vector getListNpmhsPengajuanPindahProdiWip(String target_thsms, Vector v_scope_id) {
    	Vector v_scope_kdpst = null;
    	int tot_mhs = 0;
    	int tot_wip = 0;
    	ListIterator li = null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		
        		v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
            	li = v_scope_kdpst.listIterator();
                while(li.hasNext()) {
                	String sql_cmd = null;
                    String info_scope = (String)li.next();
                    String list_npm = null; //per kampus soalnya
                    StringTokenizer st = new StringTokenizer(info_scope,"`");
                    if(st.countTokens()>1) {
                    	sql_cmd = new String("");

                        String kdkmp=st.nextToken();
                        while(st.hasMoreTokens()) {
                        	String kdpst = st.nextToken();
                            sql_cmd = sql_cmd+"TARGET_KDPST='"+kdpst+"' or CREATOR_KDPST='"+kdpst+"'";
                            if(st.hasMoreTokens()) {
                            	sql_cmd = sql_cmd+" or ";
                            }
                        }
                        
                        if(!Checker.isStringNullOrEmpty(sql_cmd)) {
                        	//sql_cmd = SELECT KDPSTMSMHS,NPMHSMSMHS,NMMHSMSMHS,SMAWLMSMHS,TARGET_KDPST,LOCKED  FROM USG.TOPIK_PENGAJUAN inner join CIVITAS on CREATOR_NPM=NPMHSMSMHS  where BATAL=false and REJECTED is null and TARGET_THSMS_PENGAJUAN='20162' and  TIPE_PENGAJUAN='PINDAH_PRODI';
                        	sql_cmd = new String("select KDPSTMSMHS,NPMHSMSMHS,NMMHSMSMHS,SMAWLMSMHS,TARGET_KDPST,LOCKED FROM USG.TOPIK_PENGAJUAN inner join CIVITAS B on CREATOR_NPM=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ  where LOCKED=false and BATAL=false and REJECTED is null and TARGET_KDPST is not null and TARGET_THSMS_PENGAJUAN=? and  TIPE_PENGAJUAN='PINDAH_PRODI' and ("+sql_cmd+") and KODE_KAMPUS_DOMISILI=? order by KDPSTMSMHS,LOCKED,NPMHSMSMHS");
                        	////System.out.println("sql cmd="+sql_cmd);
                    		stmt = con.prepareStatement(sql_cmd);
                    		stmt.setString(1, target_thsms);
                    		stmt.setString(2, kdkmp);
                    		rs = stmt.executeQuery();
                    		while(rs.next()) {
                        		
                        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
                        		String kdpst = ""+rs.getString(1);
                        		String npmhs = ""+rs.getString(2);
                        		String nmmhs = ""+rs.getString(3);
                        		String smawl = ""+rs.getString(4);
                        		String target_kdpst = ""+rs.getString(5);
                        		String locked = ""+rs.getString(6);
                        		
                        		
                        		//String approved = ""+rs.getBoolean(5);
                        		if(list_npm==null) {
                        			//list_npm=new String(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved);
                        			list_npm=new String(kdpst+","+npmhs+","+nmmhs+","+smawl+","+target_kdpst+","+locked);
                        		}
                        		else {
                        			//list_npm=list_npm+"`"+kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved;
                        			list_npm=list_npm+","+kdpst+","+npmhs+","+nmmhs+","+smawl+","+target_kdpst+","+locked;
                        		}
                        		//li.add(kdpst);
                        		li.set(info_scope+"~"+list_npm);
                        	}
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
        	finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        		if (con!=null) try { con.close();} catch (Exception ignore){}
        	}       	
    	}
    	
    	return v_scope_kdpst;
    }
    
    public Vector getListNpmhsMhsBaru(String target_angkatan, Vector v_scope_id, boolean angel_included) {
    	Vector v_scope_kdpst = null;
    	ListIterator li = null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
            	
                //boolean first = true;
                li = v_scope_kdpst.listIterator();
                while(li.hasNext()) {
                	String sql_cmd = null;
                    String info_scope = (String)li.next();
                    String list_npm = null; //per kampus soalnya
                    StringTokenizer st = new StringTokenizer(info_scope,"`");
                    if(st.countTokens()>1) {
                    //	if(first) {
                      //         first = false;
                    	sql_cmd = new String("");
                        //}
                        //else {
                        //       sql_cmd = sql_cmd+" or ";
                        //}
                        String kdkmp = st.nextToken();
                        while(st.hasMoreTokens()) {
                        	String kdpst = st.nextToken();
                            sql_cmd = sql_cmd+"KDPSTMSMHS='"+kdpst+"'";
                            if(st.hasMoreTokens()) {
                            	sql_cmd = sql_cmd+" or ";
                            }
                        }
                        if(!Checker.isStringNullOrEmpty(sql_cmd)) {
                        	if(angel_included) {
                    			sql_cmd = new String("select distinct KDPSTMSMHS,NPMHSMSMHS,STPIDMSMHS,KDJEKMSMHS from CIVITAS A inner join OBJECT B on A.ID_OBJ=B.ID_OBJ where SMAWLMSMHS=? and ("+sql_cmd+") and KODE_KAMPUS_DOMISILI=? order by KDPSTMSMHS,STPIDMSMHS,NPMHSMSMHS");
                    			stmt = con.prepareStatement(sql_cmd);
                    			stmt.setString(1, target_angkatan);
                    			stmt.setString(2, kdkmp);
                    		}
                    		else {
                    			
                    			sql_cmd = new String("select distinct KDPSTMSMHS,NPMHSMSMHS,STPIDMSMHS,KDJEKMSMHS from CIVITAS A inner join OBJECT B on A.ID_OBJ=B.ID_OBJ where SMAWLMSMHS=? and ("+sql_cmd+") and MALAIKAT=? and KODE_KAMPUS_DOMISILI=? order by KDPSTMSMHS,STPIDMSMHS,NPMHSMSMHS");
                    			////System.out.println("sqlcmd="+sql_cmd);
                    			stmt = con.prepareStatement(sql_cmd);
                    			stmt.setString(1, target_angkatan);
                    			stmt.setBoolean(2, false);
                    			stmt.setString(3, kdkmp);
                    		}
                    		rs = stmt.executeQuery();
                        	while(rs.next()) {
                        		
                        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
                        		String kdpst = ""+rs.getString(1);
                        		String npmhs = ""+rs.getString(2);
                        		String stpid = ""+rs.getString(3);
                        		String kdjek = ""+rs.getString(4);
                        		if(list_npm==null) {
                        			list_npm=new String(kdpst+","+npmhs+","+stpid+","+kdjek);
                        		}
                        		else {
                        			list_npm=list_npm+","+kdpst+","+npmhs+","+stpid+","+kdjek;
                        		}
                        		//li.add(kdpst);
                        		li.set(info_scope+"~"+list_npm);
                        	}
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
        	finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        		if (con!=null) try { con.close();} catch (Exception ignore){}
        	}       	
    	}
    	
    	return v_scope_kdpst;
    }
    
    public Vector getListNpmhsPengajuanKelulusan(String target_thsms, Vector v_scope_id, boolean show_angel) {
    	Vector v_scope_kdpst = null;
    	int tot_mhs = 0;
    	int tot_wip = 0;
    	ListIterator li = null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		
        		v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
            	li = v_scope_kdpst.listIterator();
                while(li.hasNext()) {
                	String sql_cmd = null;
                    String info_scope = (String)li.next();
                    String list_npm = null; //per kampus soalnya
                    StringTokenizer st = new StringTokenizer(info_scope,"`");
                    if(st.countTokens()>1) {
                    	sql_cmd = new String("");

                        String kdkmp=st.nextToken();
                        while(st.hasMoreTokens()) {
                        	String kdpst = st.nextToken();
                            sql_cmd = sql_cmd+"KDPSTMSMHS='"+kdpst+"'";
                            if(st.hasMoreTokens()) {
                            	sql_cmd = sql_cmd+" or ";
                            }
                        }
                        
                        if(!Checker.isStringNullOrEmpty(sql_cmd)) {
                        	if(show_angel) {
                        		sql_cmd = new String("SELECT KDPSTMSMHS as PRODI,NPMHSMSMHS as NPM,NMMHSMSMHS as NAMA,SMAWLMSMHS as ANGKATAN,LOCKED as DISETUJUI FROM TOPIK_PENGAJUAN A inner join CIVITAS B on CREATOR_NPM=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN='KELULUSAN' and REJECTED is null and BATAL=? and KODE_KAMPUS_DOMISILI=? and ("+sql_cmd+") ORDER BY KDPSTMSMHS,LOCKED,NPMHSMSMHS");
                        		
                        	}
                        	else {
                        		sql_cmd = new String("SELECT KDPSTMSMHS as PRODI,NPMHSMSMHS as NPM,NMMHSMSMHS as NAMA,SMAWLMSMHS as ANGKATAN,LOCKED as DISETUJUI FROM TOPIK_PENGAJUAN A inner join CIVITAS B on CREATOR_NPM=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN='KELULUSAN' and REJECTED is null and BATAL=? and MALAIKAT=false and KODE_KAMPUS_DOMISILI=? and ("+sql_cmd+") ORDER BY KDPSTMSMHS,LOCKED,NPMHSMSMHS");
                        		
                        	}
                        	////System.out.println("sql0 = "+sql_cmd);
                        	stmt = con.prepareStatement(sql_cmd);
                    		stmt.setString(1, target_thsms);
                    		stmt.setBoolean(2, false);
                    		stmt.setString(3, kdkmp);
                    		rs = stmt.executeQuery();
                    		while(rs.next()) {
                        		
                        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
                        		String kdpst = ""+rs.getString(1);
                        		String npmhs = ""+rs.getString(2);
                        		String nmmhs = ""+rs.getString(3);
                        		String smawl = ""+rs.getString(4);
                        		String locked = ""+rs.getBoolean(5);
                        		//String approved = ""+rs.getBoolean(5);
                        		if(list_npm==null) {
                        			//list_npm=new String(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved);
                        			list_npm=new String(kdpst+","+npmhs+","+nmmhs+","+smawl+","+locked);
                        		}
                        		else {
                        			//list_npm=list_npm+"`"+kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved;
                        			list_npm=list_npm+","+kdpst+","+npmhs+","+nmmhs+","+smawl+","+locked;
                        		}
                        		//li.add(kdpst);
                        		li.set(info_scope+"~"+list_npm);
                        	}
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
        	finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        		if (con!=null) try { con.close();} catch (Exception ignore){}
        	}       	
    	}
    	
    	return v_scope_kdpst;
    }
    
    public Vector getListNpmhsPengajuanKelulusanWip(String target_thsms, Vector v_scope_id, boolean show_angel) {
    	Vector v_scope_kdpst = null;
    	int tot_mhs = 0;
    	int tot_wip = 0;
    	ListIterator li = null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		
        		v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
            	li = v_scope_kdpst.listIterator();
                while(li.hasNext()) {
                	String sql_cmd = null;
                    String info_scope = (String)li.next();
                    String list_npm = null; //per kampus soalnya
                    StringTokenizer st = new StringTokenizer(info_scope,"`");
                    if(st.countTokens()>1) {
                    	sql_cmd = new String("");

                        String kdkmp=st.nextToken();
                        while(st.hasMoreTokens()) {
                        	String kdpst = st.nextToken();
                            sql_cmd = sql_cmd+"KDPSTMSMHS='"+kdpst+"'";
                            if(st.hasMoreTokens()) {
                            	sql_cmd = sql_cmd+" or ";
                            }
                        }
                        
                        if(!Checker.isStringNullOrEmpty(sql_cmd)) {
                        	//sql_cmd = new String("select distinct KDPST,NPMHS,NMMHSMSMHS,SMAWLMSMHS,ALL_APPROVED from DAFTAR_ULANG inner join CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and ALL_APPROVED=? and ("+sql_cmd+") order by KDPST,ALL_APPROVED,NPMHS");
                        	if(show_angel) {
                        		sql_cmd = new String("SELECT KDPSTMSMHS as PRODI,NPMHSMSMHS as NPM,NMMHSMSMHS as NAMA,SMAWLMSMHS as ANGKATAN,LOCKED as DISETUJUI FROM TOPIK_PENGAJUAN A inner join CIVITAS B on CREATOR_NPM=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN='KELULUSAN' and REJECTED is null and BATAL=? and KODE_KAMPUS_DOMISILI=? and LOCKED=false and ("+sql_cmd+") ORDER BY KDPSTMSMHS,NPMHSMSMHS");
                        		
                        	}
                        	else {
                        		sql_cmd = new String("SELECT KDPSTMSMHS as PRODI,NPMHSMSMHS as NPM,NMMHSMSMHS as NAMA,SMAWLMSMHS as ANGKATAN,LOCKED as DISETUJUI FROM TOPIK_PENGAJUAN A inner join CIVITAS B on CREATOR_NPM=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN='KELULUSAN' and REJECTED is null and BATAL=? and MALAIKAT=false and KODE_KAMPUS_DOMISILI=? and LOCKED=false and ("+sql_cmd+") ORDER BY KDPSTMSMHS,NPMHSMSMHS");
                        			
                        	}
                        	////System.out.println("sql = "+sql_cmd);
                        	stmt = con.prepareStatement(sql_cmd);
                    		stmt.setString(1, target_thsms);
                    		stmt.setBoolean(2, false);
                    		stmt.setString(3, kdkmp);
                    		rs = stmt.executeQuery();
                    		while(rs.next()) {
                        		
                        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
                        		String kdpst = ""+rs.getString(1);
                        		String npmhs = ""+rs.getString(2);
                        		String nmmhs = ""+rs.getString(3);
                        		String smawl = ""+rs.getString(4);
                        		String locked = ""+rs.getBoolean(5);
                        		//String approved = ""+rs.getBoolean(5);
                        		if(list_npm==null) {
                        			//list_npm=new String(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved);
                        			list_npm=new String(kdpst+","+npmhs+","+nmmhs+","+smawl+","+locked);
                        		}
                        		else {
                        			//list_npm=list_npm+"`"+kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved;
                        			list_npm=list_npm+","+kdpst+","+npmhs+","+nmmhs+","+smawl+","+locked;
                        		}
                        		//li.add(kdpst);
                        		li.set(info_scope+"~"+list_npm);
                        	}
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
        	finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        		if (con!=null) try { con.close();} catch (Exception ignore){}
        	}       	
    	}
    	
    	return v_scope_kdpst;
    }
    
    public Vector getListNpmhsPengajuanKeluarDo(String target_thsms, Vector v_scope_id, boolean show_angel) {
    	//System.out.println("kok behini");
    	Vector v_scope_kdpst = null;
    	int tot_mhs = 0;
    	int tot_wip = 0;
    	ListIterator li = null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		
        		v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
            	li = v_scope_kdpst.listIterator();
                while(li.hasNext()) {
                	Vector v_tmp = new Vector();
                	ListIterator litmp = v_tmp.listIterator();
                	String sql_cmd = null;
                    String info_scope = (String)li.next();
                    String list_npm = null; //per kampus soalnya
                    StringTokenizer st = new StringTokenizer(info_scope,"`");
                    if(st.countTokens()>1) {
                    	String sql_cmd0 = new String("");

                        String kdkmp=st.nextToken();
                        while(st.hasMoreTokens()) {
                        	String kdpst = st.nextToken();
                            sql_cmd0 = sql_cmd0+"KDPSTMSMHS='"+kdpst+"'";
                            if(st.hasMoreTokens()) {
                            	sql_cmd0 = sql_cmd0+" or ";
                            }
                        }
                        //System.out.println("sql_cmd0 = "+sql_cmd0);
                        if(!Checker.isStringNullOrEmpty(sql_cmd0)) {
                        	//get pengajuan yg sudah di approved
                        	// PAKE  TARGET_KDPST is null  karena kita filter yg asli keluar UNIV bukan pindah prodi
                        	
                        	if(show_angel) {
                        		sql_cmd = new String("SELECT KDPSTMSMHS as PRODI,NPMHSMSMHS as NPM,NMMHSMSMHS as NAMA,SMAWLMSMHS as ANGKATAN,LOCKED as DISETUJUI,TIPE_PENGAJUAN as 'TIPE KELUAR',MALAIKAT as 'MHS VALID' FROM TOPIK_PENGAJUAN A inner join CIVITAS B on CREATOR_NPM=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ where TARGET_THSMS_PENGAJUAN=? and BATAL=false and TARGET_KDPST is null and ("+sql_cmd0+") and (TIPE_PENGAJUAN='DO' or TIPE_PENGAJUAN='KELUAR') and REJECTED is null and KODE_KAMPUS_DOMISILI=? order by TIPE_PENGAJUAN,NPMHSMSMHS");
                        		
                        	}
                        	else {
                        		sql_cmd = new String("SELECT KDPSTMSMHS as PRODI,NPMHSMSMHS as NPM,NMMHSMSMHS as NAMA,SMAWLMSMHS as ANGKATAN,LOCKED as DISETUJUI,TIPE_PENGAJUAN as 'TIPE KELUAR',MALAIKAT as 'MHS VALID' FROM TOPIK_PENGAJUAN A inner join CIVITAS B on CREATOR_NPM=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ where TARGET_THSMS_PENGAJUAN=? and BATAL=false and TARGET_KDPST is null and MALAIKAT=false and ("+sql_cmd0+") and (TIPE_PENGAJUAN='DO' or TIPE_PENGAJUAN='KELUAR') and REJECTED is null and KODE_KAMPUS_DOMISILI=?  order by TIPE_PENGAJUAN,NPMHSMSMHS");
                        			
                        	}
                        	//System.out.println("sql = "+sql_cmd);
                        	stmt = con.prepareStatement(sql_cmd);
                    		stmt.setString(1, target_thsms);
                    		stmt.setString(2, kdkmp);
                    		rs = stmt.executeQuery();
                    		while(rs.next()) {
                        		
                        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
                        		String kdpst = ""+rs.getString(1);
                        		String npmhs = ""+rs.getString(2);
                        		String nmmhs = ""+rs.getString(3);
                        		String smawl = ""+rs.getString(4);
                        		String locked = ""+rs.getBoolean(5);
                        		String tipe_out = ""+rs.getString(6);
                        		String angel = ""+rs.getBoolean(7);
                        		//String approved = ""+rs.getBoolean(5);
                        		//if(list_npm==null) {
                        			//list_npm=new String(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved);
                        		//	list_npm=new String(kdpst+","+npmhs+","+nmmhs+","+smawl+","+locked+","+tipe_out+","+angel);
                        		//}
                        		//else {
                        			//list_npm=list_npm+"`"+kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved;
                        		//	list_npm=list_npm+","+kdpst+","+npmhs+","+nmmhs+","+smawl+","+locked+","+tipe_out+","+angel;
                        		//}
                        		//li.add(kdpst);
                        		//li.set(info_scope+"~"+list_npm);
                        		litmp.add(kdpst+","+npmhs+","+nmmhs+","+smawl+","+locked+","+tipe_out+","+angel);
                        		//System.out.println("add: "+kdpst+","+npmhs+","+nmmhs+","+smawl+","+locked+","+tipe_out+","+angel);
                        	}
                    		
                    		//khusus if show malaikat cek add juga yg di trlsm kemudian remove duplicate
                    		if(show_angel) {
                    			sql_cmd = "select A.KDPST,NPMHS,NMMHSMSMHS,SMAWLMSMHS,STMHS,MALAIKAT from TRLSM A inner join CIVITAS B on A.NPMHS=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ where A.THSMS=? and ("+sql_cmd0+") and (STMHS='D' or STMHS='DO' or STMHS='K') and KODE_KAMPUS_DOMISILI=? order by STMHS,NPMHS";
                    			//System.out.println("sql_cmd1="+sql_cmd);
                    			stmt = con.prepareStatement(sql_cmd);
                        		stmt.setString(1, target_thsms);
                        		stmt.setString(2, kdkmp);
                        		rs = stmt.executeQuery();
                        		while(rs.next()) {
                            		String kdpst = ""+rs.getString(1);
                            		String npmhs = ""+rs.getString(2);
                            		String nmmhs = ""+rs.getString(3);
                            		String smawl = ""+rs.getString(4);
                            		String stmhs = ""+rs.getString(5);
                            		String angel = ""+rs.getBoolean(6);
                            		String tipe_out = "DO";
                            		if(stmhs.equalsIgnoreCase("K")) {
                            			tipe_out = "KELUAR";
                            		}
                            		//String approved = ""+rs.getBoolean(5);
                            		//if(list_npm==null) {
                            			//list_npm=new String(kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved);
                            		//	list_npm=new String(kdpst+","+npmhs+","+nmmhs+","+smawl+",true,"+tipe_out+","+angel);
                            		//}
                            		//else {
                            			//list_npm=list_npm+"`"+kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+approved;
                            		//	list_npm=list_npm+","+kdpst+","+npmhs+","+nmmhs+","+smawl+",true,"+tipe_out+","+angel;
                            		//}
                            		//li.add(kdpst);
                            		//li.add(info_scope+"~"+list_npm);
                            		litmp.add(kdpst+","+npmhs+","+nmmhs+","+smawl+",true,"+tipe_out+","+angel);
                            		//System.out.println("add: "+kdpst+","+npmhs+","+nmmhs+","+smawl+",true,"+tipe_out+","+angel);
                            	}
                    		}
                    		if(v_tmp!=null && v_tmp.size()>0) {
                    			v_tmp = Tool.removeDuplicateFromVector(v_tmp);
                    			
                    			litmp = v_tmp.listIterator();
                    			while(litmp.hasNext()) {
                    				String brs = (String)litmp.next();
                    				if(list_npm==null) {
                    					list_npm=new String(brs);
                    				}
                    				else {
                    					list_npm=list_npm+","+brs;
                    				}
                    			}
                    			li.set(info_scope+"~"+list_npm);
                    			//System.out.println("-- "+info_scope+"~"+list_npm);
                    		}
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
    	
    	return v_scope_kdpst;
    }

}

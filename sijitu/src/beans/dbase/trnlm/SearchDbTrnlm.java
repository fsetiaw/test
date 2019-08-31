package beans.dbase.trnlm;

import beans.dbase.SearchDb;
import beans.tools.Checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import beans.tools.*;
import org.apache.tomcat.jdbc.pool.DataSource;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;
import java.util.Collections;
/**
 * Session Bean implementation class SearchDbTrnlm
 */
@Stateless
@LocalBean
public class SearchDbTrnlm extends SearchDb {
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
    public SearchDbTrnlm() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbTrnlm(String operatorNpm) {
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
    public SearchDbTrnlm(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }


    public Vector addJumMhs_ver1(Vector vListInfoKelas) {
    	/*
    	 * unreusable cuma adhock fix
    	 */
    	if(vListInfoKelas!=null && vListInfoKelas.size()>0) {
    		/*
			 * tambah jumlah mhs perkelas ke dalam brs;
			 */
			ListIterator li = vListInfoKelas.listIterator();
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			//String sqlstmt = "select NPMHSTRNLM,KODE_KAMPUS_DOMISILI from TRNLM inner join CIVITAS c on NPMHSTRNLM=NPMHSMSMHS inner join OBJECT o on c.ID_OBJ = o.ID_OBJ";
    			//sqlstmt = sqlstmt + " where THSMSTRNLM=? and KDPSTTRNLM=? and KDKMKTRNLM=?";
    			//stmt = con.prepareStatement("select NPMHSTRNLM,KODE_KAMPUS from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=? and KDKMKTRNLM=?");
    			//stmt = con.prepareStatement("select NPMHSTRNLM,KODE_KAMPUS from TRNLM where CLASS_POOL_UNIQUE_ID=? or CUID_INIT=?");
    			stmt = con.prepareStatement("select NPMHSTRNLM,KODE_KAMPUS from TRNLM where CLASS_POOL_UNIQUE_ID=?");
    		    //stmt = con.prepareStatement(sqlstmt);
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"$");
        			String kodeGabungan=st.nextToken();
        			String idkmk1=st.nextToken();
        			String idkur1=st.nextToken();
        			String kdkmk1=st.nextToken();
        			String nakmk1=st.nextToken();
        			String thsms1=st.nextToken();
        			String kdpst1=st.nextToken();
        			String shift1=st.nextToken();
        			String norutKlsPll1=st.nextToken();
        			String initNpmInput1=st.nextToken();
        			String latestNpmUpdate1=st.nextToken();
        			String latesStatusInfo1=st.nextToken();
        			String currAvailStatus1=st.nextToken();
        			String locked1=st.nextToken();
        			String npmdos1=st.nextToken();
        			String nodos1=st.nextToken();
        			String npmasdos1=st.nextToken();
        			String noasdos1=st.nextToken();
        			String canceled1=st.nextToken();
        			String kodeKelas1=st.nextToken();
        			String kodeRuang1=st.nextToken();
        			String kodeGedung1=st.nextToken();
        			String kodeKampus1=st.nextToken();
        			String tknHrTime1=st.nextToken();
        			String nmdos1=st.nextToken();
        			String nmasdos1=st.nextToken();
        			String enrolled1=st.nextToken();
        			String maxEnrolled1=st.nextToken();
        			String minEnrolled1=st.nextToken();
        			String subKeterKdkmk1=st.nextToken();
        			String initReqTime1=st.nextToken();
        			String tknNpmApr1=st.nextToken();
        			String tknAprTime1=st.nextToken();
        			String targetTtmhs1=st.nextToken();
        			String passed1=st.nextToken();
       				String rejected1=st.nextToken();
       				String konsen1=st.nextToken();
       				String nmpst1=st.nextToken();
       				String cuid1=st.nextToken();
       				//stmt.setString(1,thsms1);
       				//stmt.setString(2,kdpst1);
       				//stmt.setString(3,kdkmk1);
       				stmt.setLong(1,Long.parseLong(cuid1));
       				//stmt.setLong(2,Long.parseLong(cuid1));
       				rs = stmt.executeQuery();
       				//int i = 0;
       				String listNpm = null;
       				Vector vTmp = new Vector();
       				ListIterator liTmp = vTmp.listIterator();
       				while(rs.next()) {
       					String kodeKampusDomisili = ""+rs.getString("KODE_KAMPUS");
       					String npmhs = rs.getString("NPMHSTRNLM");
       					if(kodeKampus1.equalsIgnoreCase(kodeKampusDomisili)) {
       						liTmp.add(npmhs);
       					}
       				}
       				//vTmp = Tool.removeDuplicateFromVector(vTmp);
       				liTmp = vTmp.listIterator();
       				while(liTmp.hasNext()) {
       					if(listNpm==null) {
       						listNpm="";
       					}
       					String npmhs = (String)liTmp.next();
       					listNpm = listNpm+npmhs;
       					if(liTmp.hasNext()) {
       						listNpm = listNpm+",";
       					}	
       				}	
       				//	if(listNpm==null) {
       				//		listNpm = "";
       				//	}
       				//	String npmhs = rs.getString("NPMHSTRNLM");
       				//	listNpm = listNpm+","+npmhs;
       					//i++;
       				//}
       				if(listNpm==null) {
       					listNpm = "null";
       				}
       				//System.out.println("listNpm="+listNpm);
       				li.set(brs+"$"+listNpm);
       				//System.out.println("listNpm="+listNpm);
       				//System.out.println(thsms1+"$"+kdpst1+"$"+kdkmk1);
       				//System.out.println(brs+"$"+i);
    			}
    			
    		}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
    		catch (Exception e) {
    			e.printStackTrace();
    		}
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}
    	}
    	return vListInfoKelas;
    }
    
    
    /*
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * DEPRICATED
     * pake VER1
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    public Vector addJumMhs(Vector vListInfoKelas) {
    	/*
    	 * unreusable cuma adhock fix
    	 */
    	if(vListInfoKelas!=null && vListInfoKelas.size()>0) {
    		/*
			 * tambah jumlah mhs perkelas ke dalam brs;
			 */
			ListIterator li = vListInfoKelas.listIterator();
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			//String sqlstmt = "select NPMHSTRNLM,KODE_KAMPUS_DOMISILI from TRNLM inner join CIVITAS c on NPMHSTRNLM=NPMHSMSMHS inner join OBJECT o on c.ID_OBJ = o.ID_OBJ";
    			//sqlstmt = sqlstmt + " where THSMSTRNLM=? and KDPSTTRNLM=? and KDKMKTRNLM=?";
    			stmt = con.prepareStatement("select * from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=? and KDKMKTRNLM=?");
    		    //stmt = con.prepareStatement(sqlstmt);
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"$");
        			String kodeGabungan=st.nextToken();
        			String idkmk1=st.nextToken();
        			String idkur1=st.nextToken();
        			String kdkmk1=st.nextToken();
        			String nakmk1=st.nextToken();
        			String thsms1=st.nextToken();
        			String kdpst1=st.nextToken();
        			String shift1=st.nextToken();
        			String norutKlsPll1=st.nextToken();
        			String initNpmInput1=st.nextToken();
        			String latestNpmUpdate1=st.nextToken();
        			String latesStatusInfo1=st.nextToken();
        			String currAvailStatus1=st.nextToken();
        			String locked1=st.nextToken();
        			String npmdos1=st.nextToken();
        			String nodos1=st.nextToken();
        			String npmasdos1=st.nextToken();
        			String noasdos1=st.nextToken();
        			String canceled1=st.nextToken();
        			String kodeKelas1=st.nextToken();
        			String kodeRuang1=st.nextToken();
        			String kodeGedung1=st.nextToken();
        			String kodeKampus1=st.nextToken();
        			String tknHrTime1=st.nextToken();
        			String nmdos1=st.nextToken();
        			String nmasdos1=st.nextToken();
        			String enrolled1=st.nextToken();
        			String maxEnrolled1=st.nextToken();
        			String minEnrolled1=st.nextToken();
        			String subKeterKdkmk1=st.nextToken();
        			String initReqTime1=st.nextToken();
        			String tknNpmApr1=st.nextToken();
        			String tknAprTime1=st.nextToken();
        			String targetTtmhs1=st.nextToken();
        			String passed1=st.nextToken();
       				String rejected1=st.nextToken();
       				String konsen1=st.nextToken();
       				String nmpst1=st.nextToken();
       				stmt.setString(1,thsms1);
       				stmt.setString(2,kdpst1);
       				stmt.setString(3,kdkmk1);
       				rs = stmt.executeQuery();
       				//int i = 0;
       				String listNpm = null;
       				
       				Vector vTmp = new Vector();
       				ListIterator liTmp = vTmp.listIterator();
       				while(rs.next()) {
       					String kodeKampusDomisili = ""+rs.getString("KODE_KAMPUS");
       					String npmhs = rs.getString("NPMHSTRNLM");
       					if(kodeKampus1.equalsIgnoreCase(kodeKampusDomisili)) {
       						liTmp.add(npmhs);
       					}
       				}
       				vTmp = Tool.removeDuplicateFromVector(vTmp);
       				liTmp = vTmp.listIterator();
       				while(liTmp.hasNext()) {
       					if(listNpm==null) {
       						listNpm="";
       					}
       					String npmhs = (String)liTmp.next();
       					listNpm = listNpm+npmhs;
       					if(liTmp.hasNext()) {
       						listNpm = listNpm+",";
       					}	
       				}	
       				/*
       				while(rs.next()) {
       					if(listNpm==null) {
       						listNpm = "";
       					}
       					String npmhs = rs.getString("NPMHSTRNLM");
       					listNpm = listNpm+","+npmhs;
       					//i++;
       				}
       				*/
       				if(listNpm==null||Checker.isStringNullOrEmpty(listNpm)) {
       					listNpm = "null";
       				}
       				//System.out.println("listNpm="+listNpm);
       				li.set(brs+"$"+listNpm);
       				//System.out.println("listNpm="+listNpm);
       				//System.out.println(thsms1+"$"+kdpst1+"$"+kdkmk1);
       				//System.out.println(brs+"$"+i);
    			}
    			/*
    			stmt = con.prepareStatement("select KODE_KAMPUS_DOMISILI from CIVITAS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where KDPSTMSMHS=? and NPMHSMSMHS=?");
    			li = vListInfoKelas.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"$");
        			String kodeGabungan=st.nextToken();
        			String idkmk1=st.nextToken();
        			String idkur1=st.nextToken();
        			String kdkmk1=st.nextToken();
        			String nakmk1=st.nextToken();
        			String thsms1=st.nextToken();
        			String kdpst1=st.nextToken();
        			String shift1=st.nextToken();
        			String norutKlsPll1=st.nextToken();
        			String initNpmInput1=st.nextToken();
        			String latestNpmUpdate1=st.nextToken();
        			String latesStatusInfo1=st.nextToken();
        			String currAvailStatus1=st.nextToken();
        			String locked1=st.nextToken();
        			String npmdos1=st.nextToken();
        			String nodos1=st.nextToken();
        			String npmasdos1=st.nextToken();
        			String noasdos1=st.nextToken();
        			String canceled1=st.nextToken();
        			String kodeKelas1=st.nextToken();
        			String kodeRuang1=st.nextToken();
        			String kodeGedung1=st.nextToken();
        			String kodeKampus1=st.nextToken();
        			String tknHrTime1=st.nextToken();
        			String nmdos1=st.nextToken();
        			String nmasdos1=st.nextToken();
        			String enrolled1=st.nextToken();
        			String maxEnrolled1=st.nextToken();
        			String minEnrolled1=st.nextToken();
        			String subKeterKdkmk1=st.nextToken();
        			String initReqTime1=st.nextToken();
        			String tknNpmApr1=st.nextToken();
        			String tknAprTime1=st.nextToken();
        			String targetTtmhs1=st.nextToken();
        			String passed1=st.nextToken();
       				String rejected1=st.nextToken();
       				String nmpst1=st.nextToken();
       				String listNpm1=st.nextToken();
       				StringTokenizer st2 = new StringTokenizer(listNpm1,",");
       				listNpm1 = null;
       				boolean first = true;;
       				while(st2.hasMoreTokens()) {
       					String npm = st2.nextToken();
       					stmt.setString(1,kdpst1);
       					stmt.setString(2,npm);
       					rs = stmt.executeQuery();
       					String kampusDomisili = null;
       					
       					if(rs.next()) {
       						kampusDomisili = ""+rs.getString("KODE_KAMPUS_DOMISILI");
       						if(kodeKampus1.equalsIgnoreCase(kampusDomisili)) {
       							if(first) {
       								listNpm1="";
       								first = false;
       								listNpm1=npm;
       							}
       							else {
       								listNpm1=listNpm1+","+npm;
       							}
       						}
       					}
       					else {
       						listNpm1="null";
       					}
       				}
       				
       				li.set(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+nmpst1+"$"+listNpm1);
    			}
    			*/	
    		}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
    		catch (Exception e) {
    			e.printStackTrace();	
    		}
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}
    	}
    	return vListInfoKelas;
    }
    
    /*
     * !!!!!!!!!!!!!!!!!!!!!!!
     * DEPRICATED
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!
     */		
    public Vector addJumMhsYgSudahHeregistrasi(Vector vListInfoKelas) {

    	/*
    	 * unreusable cuma adhock fix
    	 */
    	if(vListInfoKelas!=null && vListInfoKelas.size()>0) {
    		/*
			 * tambah jumlah mhs perkelas ke dalam brs;
			 */
			ListIterator li = vListInfoKelas.listIterator();
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select NPMHS from DAFTAR_ULANG where THSMS=? and NPMHS=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				//System.out.println("brs="+brs);
    				StringTokenizer st = new StringTokenizer(brs,"$");
        			String kodeGabungan=st.nextToken();
        			String idkmk1=st.nextToken();
        			String idkur1=st.nextToken();
        			String kdkmk1=st.nextToken();
        			String nakmk1=st.nextToken();
        			String thsms1=st.nextToken();
        			String kdpst1=st.nextToken();
        			String shift1=st.nextToken();
        			String norutKlsPll1=st.nextToken();
        			String initNpmInput1=st.nextToken();
        			String latestNpmUpdate1=st.nextToken();
        			String latesStatusInfo1=st.nextToken();
        			String currAvailStatus1=st.nextToken();
        			String locked1=st.nextToken();
        			String npmdos1=st.nextToken();
        			String nodos1=st.nextToken();
        			String npmasdos1=st.nextToken();
        			String noasdos1=st.nextToken();
        			String canceled1=st.nextToken();
        			String kodeKelas1=st.nextToken();
        			String kodeRuang1=st.nextToken();
        			String kodeGedung1=st.nextToken();
        			String kodeKampus1=st.nextToken();
        			String tknHrTime1=st.nextToken();
        			String nmdos1=st.nextToken();
        			String nmasdos1=st.nextToken();
        			String enrolled1=st.nextToken();
        			String maxEnrolled1=st.nextToken();
        			String minEnrolled1=st.nextToken();
        			String subKeterKdkmk1=st.nextToken();
        			String initReqTime1=st.nextToken();
        			String tknNpmApr1=st.nextToken();
        			String tknAprTime1=st.nextToken();
        			String targetTtmhs1=st.nextToken();
        			String passed1=st.nextToken();
       				String rejected1=st.nextToken();
       				String konsen1=st.nextToken();
       				String nmpst1=st.nextToken();
       				String listNpm=st.nextToken();
       				StringTokenizer st1 = new StringTokenizer(listNpm,",");
       				String listNpmHer = null;
       				while(st1.hasMoreTokens()) {
       					String npm = st1.nextToken();
       					stmt.setString(1,thsms1);
       					stmt.setString(2,npm);
       					rs = stmt.executeQuery();
       					
       					if(rs.next()) {
       						if(listNpmHer==null) {
       							listNpmHer = "";
       						}
       						listNpmHer = listNpmHer+","+npm;
       					}
       				}
       				if(listNpmHer==null) {
       					listNpmHer = "null";
       				}
       				li.set(brs+"$"+listNpmHer);
       				//System.out.println("listNpmHer="+listNpmHer);
       				//System.out.println(thsms1+"$"+kdpst1+"$"+kdkmk1);
       				//System.out.println(brs+"$"+i);
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
    	return vListInfoKelas;
    }
    
    
    public Vector addJumMhsYgSudahHeregistrasi_v1(Vector vListInfoKelas) {

    	/*
    	 * unreusable cuma adhock fix
    	 */
    	if(vListInfoKelas!=null && vListInfoKelas.size()>0) {
    		/*
			 * tambah jumlah mhs perkelas ke dalam brs;
			 */
			ListIterator li = vListInfoKelas.listIterator();
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select NPMHS from DAFTAR_ULANG where THSMS=? and NPMHS=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				//System.out.println("brs="+brs);
    				StringTokenizer st = new StringTokenizer(brs,"$");
        			String kodeGabungan=st.nextToken();
        			String idkmk1=st.nextToken();
        			String idkur1=st.nextToken();
        			String kdkmk1=st.nextToken();
        			String nakmk1=st.nextToken();
        			String thsms1=st.nextToken();
        			String kdpst1=st.nextToken();
        			String shift1=st.nextToken();
        			String norutKlsPll1=st.nextToken();
        			String initNpmInput1=st.nextToken();
        			String latestNpmUpdate1=st.nextToken();
        			String latesStatusInfo1=st.nextToken();
        			String currAvailStatus1=st.nextToken();
        			String locked1=st.nextToken();
        			String npmdos1=st.nextToken();
        			String nodos1=st.nextToken();
        			String npmasdos1=st.nextToken();
        			String noasdos1=st.nextToken();
        			String canceled1=st.nextToken();
        			String kodeKelas1=st.nextToken();
        			String kodeRuang1=st.nextToken();
        			String kodeGedung1=st.nextToken();
        			String kodeKampus1=st.nextToken();
        			String tknHrTime1=st.nextToken();
        			String nmdos1=st.nextToken();
        			String nmasdos1=st.nextToken();
        			String enrolled1=st.nextToken();
        			String maxEnrolled1=st.nextToken();
        			String minEnrolled1=st.nextToken();
        			String subKeterKdkmk1=st.nextToken();
        			String initReqTime1=st.nextToken();
        			String tknNpmApr1=st.nextToken();
        			String tknAprTime1=st.nextToken();
        			String targetTtmhs1=st.nextToken();
        			String passed1=st.nextToken();
       				String rejected1=st.nextToken();
       				String konsen1=st.nextToken();
       				String nmpst1=st.nextToken();
       				String cuid1=st.nextToken();
       				String listNpm=st.nextToken();
       				
       				
       				
       				
       				StringTokenizer st1 = new StringTokenizer(listNpm,",");
       				String listNpmHer = null;
       				while(st1.hasMoreTokens()) {
       					String npm = st1.nextToken();
       					stmt.setString(1,thsms1);
       					stmt.setString(2,npm);
       					rs = stmt.executeQuery();
       					
       					if(rs.next()) {
       						if(listNpmHer==null) {
       							listNpmHer = "";
       						}
       						listNpmHer = listNpmHer+","+npm;
       					}
       				}
       				if(listNpmHer==null) {
       					listNpmHer = "null";
       				}
       				li.set(brs+"$"+listNpmHer);
       				//System.out.println("listNpmHer="+listNpmHer);
       				//System.out.println(thsms1+"$"+kdpst1+"$"+kdkmk1);
       				//System.out.println(brs+"$"+i);
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
    	return vListInfoKelas;
    }

    public Vector getListMhs(Vector vMhsContainer, String thsms, String idkmk, String shift) {
    	ListIterator li = vMhsContainer.listIterator();
		try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=? and IDKMKTRNLM=? and SHIFTTRNLM=?");
			stmt.setString(1,thsms);
			stmt.setString(2,idkmk);
			stmt.setString(3,shift);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String npmhs = rs.getString("NPMHSTRNLM");
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
    	return vMhsContainer;
    }	
    
    
    public Vector getListMhs(String cuid) {
    	Vector vListMhs = null;
		try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select KDPSTMSMHS,NPMHSTRNLM,NMMHSMSMHS,SMAWLMSMHS,THSMSTRNLM from TRNLM inner join CIVITAS on NPMHSTRNLM=NPMHSMSMHS where CLASS_POOL_UNIQUE_ID=? order by NMMHSMSMHS");
			stmt.setLong(1,Long.parseLong(cuid));
			rs = stmt.executeQuery();
			if(rs.next()) {
				vListMhs = new Vector();
				ListIterator li = vListMhs.listIterator();
				do {
					String kdpst = rs.getString("KDPSTMSMHS");
					String npmhs = rs.getString("NPMHSTRNLM");
					String nmmhs = rs.getString("NMMHSMSMHS");
					String smawl = rs.getString("SMAWLMSMHS");
					String thsms = rs.getString("THSMSTRNLM");
					li.add(nmmhs+"`"+npmhs+"`"+kdpst+"`"+smawl+"`"+thsms);	
				}
				while(rs.next());
				li = vListMhs.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String kdpst = st.nextToken();
					String npmhs = st.nextToken();
					String nmmhs = st.nextToken();
					String smawl = st.nextToken();
					String thsms = st.nextToken();
					String status = Checker.sudahDaftarUlang(kdpst, npmhs, thsms);
					//System.out.println(nmmhs+"=="+status);
					li.set(brs+"`"+status);
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
    	return vListMhs;
    }	
    
    public Boolean isKelasGabungan(String cuid) {
    	
    	boolean gabungan = true;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from CLASS_POOL where UNIQUE_ID=?");
			stmt.setLong(1, Long.parseLong(cuid));
			rs = stmt.executeQuery();
			rs.next();
			int idkur = rs.getInt("IDKUR");
			int idkmk = rs.getInt("IDKMK");
			String thsms  = rs.getString("THSMS");
			String kdpst  = rs.getString("KDPST");
			String shift  = rs.getString("SHIFT");
			String kode_kmp = ""+rs.getString("KODE_KAMPUS");
			String kode_gab = ""+rs.getString("KODE_PENGGABUNGAN");

			if(kode_gab==null || Checker.isStringNullOrEmpty(kode_gab)) {
				gabungan = false;
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
    	return gabungan;
    }
    
    public Vector getListMhs_v1(String cuid) {
    	Vector vListMhs = null;
		try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from CLASS_POOL where UNIQUE_ID=?");
			stmt.setLong(1, Long.parseLong(cuid));
			rs = stmt.executeQuery();
			rs.next();
			int idkur = rs.getInt("IDKUR");
			int idkmk = rs.getInt("IDKMK");
			String thsms  = rs.getString("THSMS");
			String kdpst  = rs.getString("KDPST");
			String shift  = rs.getString("SHIFT");
			String kode_kmp = ""+rs.getString("KODE_KAMPUS");
			String kode_gab = ""+rs.getString("KODE_PENGGABUNGAN");

			if(kode_gab==null || Checker.isStringNullOrEmpty(kode_gab)) {
				//bukan kelas gabungan
				stmt = con.prepareStatement("update TRNLM set CLASS_POOL_UNIQUE_ID=?,CUID_INIT=? where THSMSTRNLM=? and KDPSTTRNLM=? and SHIFTTRNLM=? and IDKMKTRNLM=?");
				stmt.setLong(1, Long.parseLong(cuid));
				stmt.setNull(2, java.sql.Types.INTEGER);
				stmt.setString(3, thsms);
				stmt.setString(4, kdpst);
				stmt.setString(5, shift);
				stmt.setInt(6, idkmk);
				stmt.executeUpdate();
				
				//stmt = con.prepareStatement("select KDPSTMSMHS,NPMHSTRNLM,NMMHSMSMHS,SMAWLMSMHS,THSMSTRNLM from TRNLM inner join CIVITAS on NPMHSTRNLM=NPMHSMSMHS where CLASS_POOL_UNIQUE_ID=? order by NMMHSMSMHS");
				stmt = con.prepareStatement("select KDPSTMSMHS,NPMHSTRNLM,NIMHSMSMHS,NMMHSMSMHS,SMAWLMSMHS,THSMSTRNLM from TRNLM inner join CIVITAS on NPMHSTRNLM=NPMHSMSMHS where THSMSTRNLM=? and KDPSTTRNLM=? and SHIFTTRNLM=? and IDKMKTRNLM=? order by NMMHSMSMHS");
				//stmt.setLong(1,Long.parseLong(cuid));
				stmt.setString(1, thsms);
				stmt.setString(2, kdpst);
				stmt.setString(3, shift);
				stmt.setInt(4, idkmk);
				rs = stmt.executeQuery();
				if(rs.next()) {
					vListMhs = new Vector();
					ListIterator li = vListMhs.listIterator();
					do {
						kdpst = rs.getString("KDPSTMSMHS");
						String npmhs = ""+rs.getString("NPMHSTRNLM");
						String nimhs = ""+rs.getString("NIMHSMSMHS");
						String nmmhs = ""+rs.getString("NMMHSMSMHS");
						String smawl = ""+rs.getString("SMAWLMSMHS");
						thsms = rs.getString("THSMSTRNLM");
						//System.out.println("i."+nmmhs+"`"+npmhs+"`"+kdpst+"`"+smawl+"`"+thsms+"`"+nimhs);
						li.add(nmmhs+"`"+npmhs+"`"+kdpst+"`"+smawl+"`"+thsms+"`"+nimhs);	
					}
					while(rs.next());
					li = vListMhs.listIterator();
					while(li.hasNext()) {
						String brs = (String)li.next();
						StringTokenizer st = new StringTokenizer(brs,"`");
						//li.add(nmmhs+"`"+npmhs+"`"+kdpst+"`"+smawl+"`"+thsms+"`"+nimhs);
						String nmm = st.nextToken();
						String npm = st.nextToken();
						String kdp = st.nextToken();
						String sma = st.nextToken();
						String ths = st.nextToken();
						String nim = st.nextToken();
						
						
						//thsms = st.nextToken();
						String status = Checker.sudahDaftarUlang(kdp, npm, ths);
						li.set(nmm+"`"+npm+"`"+kdp+"`"+sma+"`"+ths+"`"+status+"`"+nim);
						//System.out.println(nmmhs+"=="+status);
						//li.set(brs+"`"+status);
					}
					
				}
			}
			else {
				//kelas gabungan
				vListMhs = new Vector();
				ListIterator li = vListMhs.listIterator();
				long open_cuid = 0;
				Vector vt =new Vector();
				ListIterator lit = vt.listIterator();
				stmt = con.prepareStatement("select * from CLASS_POOL where THSMS=? and KODE_PENGGABUNGAN=? order by CANCELED");
				stmt.setString(1, thsms);
				stmt.setString(2, kode_gab);
				rs = stmt.executeQuery();
				boolean first = true;
				while(rs.next()) {
					idkur = rs.getInt("IDKUR");
					idkmk = rs.getInt("IDKMK");
					thsms  = rs.getString("THSMS");
					kdpst  = rs.getString("KDPST");
					shift  = rs.getString("SHIFT");
					kode_kmp = ""+rs.getString("KODE_KAMPUS");
					kode_gab = ""+rs.getString("KODE_PENGGABUNGAN");
					boolean cancel = rs.getBoolean("CANCELED");
					long uniq_id = rs.getLong("UNIQUE_ID");
					lit.add(idkur+"`"+idkmk+"`"+thsms+"`"+kdpst+"`"+shift+"`"+kode_kmp+"`"+cancel+"`"+uniq_id);
					if(first) {
						first = false;
						open_cuid = uniq_id;
					}
				}
				lit = vt.listIterator();
				while(lit.hasNext()) {
					String brs = (String)lit.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					idkur = Integer.parseInt(st.nextToken());
					idkmk = Integer.parseInt(st.nextToken());
					thsms = st.nextToken();
					kdpst = st.nextToken();
					shift = st.nextToken();
					kode_kmp = st.nextToken();
					String cancel = st.nextToken();
					String uniq_id = st.nextToken();
					
					stmt = con.prepareStatement("update TRNLM set CLASS_POOL_UNIQUE_ID=?,CUID_INIT=? where THSMSTRNLM=? and KDPSTTRNLM=? and SHIFTTRNLM=? and IDKMKTRNLM=?");
					stmt.setLong(1, open_cuid);
					if(open_cuid==Long.parseLong(uniq_id)) {
						stmt.setNull(2, java.sql.Types.INTEGER);	
					}
					else {
						stmt.setLong(2, Long.parseLong(uniq_id));
					}
					
					stmt.setString(3, thsms);
					stmt.setString(4, kdpst);
					stmt.setString(5, shift);
					stmt.setInt(6, idkmk);
					stmt.executeUpdate();
					
					//stmt = con.prepareStatement("select KDPSTMSMHS,NPMHSTRNLM,NMMHSMSMHS,SMAWLMSMHS,THSMSTRNLM from TRNLM inner join CIVITAS on NPMHSTRNLM=NPMHSMSMHS where CLASS_POOL_UNIQUE_ID=? order by NMMHSMSMHS");
					stmt = con.prepareStatement("select KDPSTMSMHS,NPMHSTRNLM,NIMHSMSMHS,NMMHSMSMHS,SMAWLMSMHS,THSMSTRNLM from TRNLM inner join CIVITAS on NPMHSTRNLM=NPMHSMSMHS where THSMSTRNLM=? and KDPSTTRNLM=? and SHIFTTRNLM=? and IDKMKTRNLM=? order by NMMHSMSMHS");
					//stmt.setLong(1,Long.parseLong(cuid));
					stmt.setString(1, thsms);
					stmt.setString(2, kdpst);
					stmt.setString(3, shift);
					stmt.setInt(4, idkmk);
					rs = stmt.executeQuery();
					if(rs.next()) {
						//vListMhs = new Vector();
						//ListIterator li = vListMhs.listIterator();
						do {
							kdpst = rs.getString("KDPSTMSMHS");
							String npmhs = rs.getString("NPMHSTRNLM");
							String nimhs = rs.getString("NIMHSMSMHS");
							//System.out.println("nim="+nimhs);
							String nmmhs = rs.getString("NMMHSMSMHS");
							String smawl = rs.getString("SMAWLMSMHS");
							thsms = rs.getString("THSMSTRNLM");
							li.add(nmmhs+"`"+npmhs+"`"+kdpst+"`"+smawl+"`"+thsms+"`"+nimhs);
							//System.out.println("add="+nmmhs+"`"+npmhs+"`"+kdpst+"`"+smawl+"`"+thsms+"`"+nimhs);
						}
						while(rs.next());
					}
				}	
				li = vListMhs.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					//System.out.println("ybrs."+brs);
							//li.add(nmmhs+"`"+npmhs+"`"+kdpst+"`"+smawl+"`"+thsms+"`"+nimhs);
					String nmm = st.nextToken();
					String npm = st.nextToken();
					String kdp = st.nextToken();
					String sma = st.nextToken();
					String ths = st.nextToken();
					String nim = st.nextToken();
					//System.out.println("nim="+nim);
							
							//thsms = st.nextToken();
					String status = Checker.sudahDaftarUlang(kdp, npm, ths);
					//System.out.println("status="+status);
					li.set(nmm+"`"+npm+"`"+kdp+"`"+sma+"`"+ths+"`"+status+"`"+nim);
					//System.out.println("y."+nmm+"`"+npm+"`"+kdp+"`"+sma+"`"+ths+"`"+status+"`"+nim);
							
				}		
							
							//kdpst = st.nextToken();
							//String npmhs = st.nextToken();
							//String nmmhs = st.nextToken();
							//String smawl = st.nextToken();
							//thsms = st.nextToken();
							//String status = Checker.sudahDaftarUlang(kdpst, npmhs, thsms);
							//System.out.println(nmmhs+"=="+status);
							//li.set(brs+"`"+status);
					//	}
						
					//}
				//}
			}
			
			/*
			stmt = con.prepareStatement("select KDPSTMSMHS,NPMHSTRNLM,NMMHSMSMHS,SMAWLMSMHS,THSMSTRNLM from TRNLM inner join CIVITAS on NPMHSTRNLM=NPMHSMSMHS where CLASS_POOL_UNIQUE_ID=? order by NMMHSMSMHS");
			stmt.setLong(1,Long.parseLong(cuid));
			rs = stmt.executeQuery();
			if(rs.next()) {
				vListMhs = new Vector();
				ListIterator li = vListMhs.listIterator();
				do {
					String kdpst = rs.getString("KDPSTMSMHS");
					String npmhs = rs.getString("NPMHSTRNLM");
					String nmmhs = rs.getString("NMMHSMSMHS");
					String smawl = rs.getString("SMAWLMSMHS");
					String thsms = rs.getString("THSMSTRNLM");
					li.add(nmmhs+"`"+npmhs+"`"+kdpst+"`"+smawl+"`"+thsms);	
				}
				while(rs.next());
				li = vListMhs.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String kdpst = st.nextToken();
					String npmhs = st.nextToken();
					String nmmhs = st.nextToken();
					String smawl = st.nextToken();
					String thsms = st.nextToken();
					String status = Checker.sudahDaftarUlang(kdpst, npmhs, thsms);
					//System.out.println(nmmhs+"=="+status);
					li.set(brs+"`"+status);
				}
				
			}
			*/
			if(vListMhs!=null) {
				vListMhs = Tool.removeDuplicateFromVector(vListMhs);
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
    
    public Vector getListMhsDanCurrNilaiMknya(String thsms, String idkmk,String uniqueId, String kdkmk, String nakmk, String shift) {
    	/*
    	 * getList mahasiswa berdasarkan class_pool_uniqueId kelas or jika class_pool_uniqueId=null
    	 * cari berdasarkan kdkmk & nakmk
    	 */
    	Vector v = new Vector();
    	
    	
    	ListIterator li = v.listIterator();
		try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//yg terkini pake unique id
			//yg dulu pake idkmk shift,dts
			//stmt = con.prepareStatement("select NPMHSTRNLM,NILAITRNLM,NLAKHTRNLM,NLAKH_BY_DOSEN from TRNLM where (THSMSTRNLM=? and CLASS_POOL_UNIQUE_ID=?) or ((THSMSTRNLM=? and IDKMKTRNLM=? and SHIFTTRNLM=?))");
			stmt = con.prepareStatement("select NPMHSTRNLM,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM,NLAKH_BY_DOSEN from TRNLM where (CLASS_POOL_UNIQUE_ID=?) or ((THSMSTRNLM=? and IDKMKTRNLM=? and SHIFTTRNLM=?))");
			//stmt.setString(1,thsms);
			stmt.setLong(1,Long.parseLong(uniqueId));
			stmt.setString(2,thsms);
			stmt.setLong(3,Long.parseLong(idkmk));
			stmt.setString(4,shift);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String npmhs = ""+rs.getString("NPMHSTRNLM");
				String nilai = ""+rs.getFloat("NILAITRNLM");
				String nlakh = ""+rs.getString("NLAKHTRNLM");
				String bobot = ""+rs.getDouble("BOBOTTRNLM");
				String nlakhByDosen = ""+rs.getBoolean("NLAKH_BY_DOSEN");
				li.add(npmhs+"`"+nilai+"`"+nlakh+"`"+nlakhByDosen+"`"+bobot);
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
		//System.out.println("v size="+v.size());
    	return v;
    }	

    
    public Vector getInfoMhsTambahan(Vector vMhsContainer) {

		try {
			if(vMhsContainer!=null && vMhsContainer.size()>0) {
				ListIterator li = vMhsContainer.listIterator();
				Context initContext  = new InitialContext();
				Context envContext  = (Context)initContext.lookup("java:/comp/env");
				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
				con = ds.getConnection();
				stmt = con.prepareStatement("select * from CIVITAS where NPMHSMSMHS=?");
				
				while(li.hasNext()) {
					String npmhs = (String)li.next();
					stmt.setString(1,npmhs);
					rs = stmt.executeQuery();
					if(rs.next()) {
						String kdpst = ""+rs.getString("KDPSTMSMHS");
						String nmmhs = ""+rs.getString("NMMHSMSMHS");
						String smawl = ""+rs.getString("SMAWLMSMHS");
						String nimhs = ""+rs.getString("NIMHSMSMHS");
						li.set(nmmhs+"`"+npmhs+"`"+kdpst+"`"+smawl+"`"+nimhs);
					}
					else {
						li.remove();
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
    	return vMhsContainer;
    }	
 
    public Vector filterHanyaMhsYgSdhDaftarUlang(Vector vMhsContainer, String thsms, Vector vMhsUnHeregContainment) {
    	
    	if(thsms==null || Checker.isStringNullOrEmpty(thsms)) {
    		thsms = Checker.getThsmsNow();
    	}
		try {
			if(vMhsContainer!=null && vMhsContainer.size()>0) {
				
				Context initContext  = new InitialContext();
				Context envContext  = (Context)initContext.lookup("java:/comp/env");
				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
				con = ds.getConnection();
				stmt = con.prepareStatement("select * from DAFTAR_ULANG_RULES where THSMS=?");
				stmt.setString(1,thsms);
				rs = stmt.executeQuery();
				Vector vRules = new Vector();
				ListIterator lir = vRules.listIterator();
				while(rs.next()) {
					String kdpst = rs.getString("KDPST");
					String tkn_verifikasi = rs.getString("TKN_VERIFICATOR");
					lir.add(kdpst+"`"+tkn_verifikasi);
				}
				
				stmt = con.prepareStatement("select * from DAFTAR_ULANG where THSMS=? and KDPST=? and NPMHS=?");
				ListIterator li = vMhsContainer.listIterator();
				ListIterator li1 = vMhsUnHeregContainment.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					//System.out.println("error="+brs);
					
					//HASANAH`8888811100002`88888`20111`11250650014
					StringTokenizer st = new StringTokenizer(brs,"`");
					if(st.hasMoreTokens()) {
						st.nextToken();
						String npmhs = st.nextToken();
						String kdpst = st.nextToken();
						stmt.setString(1,thsms);
						stmt.setString(2,kdpst);
						stmt.setString(3,npmhs);
						//System.out.println("thsms="+thsms+", npmhs="+npmhs);
						rs = stmt.executeQuery();
						if(rs.next()) {
							//npmhs sudah ada di table DAFTAR ULANG
							String tkn_approval = rs.getString("TOKEN_APPROVAL");
							lir = vRules.listIterator();
							boolean match = false;
							while(lir.hasNext() && !match) {
								String brs1 = (String)lir.next();
								StringTokenizer st1 = new StringTokenizer(brs1,"`");
								String kdpst1 = st1.nextToken();
								String tkn_ver1 = st1.nextToken();
								if(kdpst1.equalsIgnoreCase(kdpst)) {
									match = true;
									st1 = new StringTokenizer(tkn_ver1,",");
									boolean passed = true;
									while(st1.hasMoreTokens() && passed) {
										String verificator = st1.nextToken();
										if(!tkn_approval.toUpperCase().contains(verificator.toUpperCase())) {
											passed = false;
										}
									}
									if(!passed) {
										li.remove();
										li1.add(brs);
									}
								}
								
							}
						}
						else {
							//npmhs belum ada di table DAFTAR ULANG
							li.remove();
							li1.add(brs);
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
    	return vMhsContainer;
    }	
    
    
    public Vector getProgressPengisianKrs() {
    	String target_thsms = Checker.getThsmsKrs();
    	Vector v = null;
		try {
			
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=? order by KDPSTTRNLM");
			stmt.setString(1,target_thsms);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				ListIterator li = v.listIterator();
				do {
					String kdpst = rs.getString("KDPSTTRNLM");
					String npmhs = rs.getString("NPMHSTRNLM");
					li.add(kdpst+"`"+npmhs);
				}
				while(li.hasNext());
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
    
    public Vector getListNpmhsOnTrnlm(String thsms, String kdpst) {
    	
    	Vector v = null;
    	ListIterator li = null;
		try {
			
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=?");
			stmt.setString(1, thsms);
			stmt.setString(2, kdpst);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					li.add(rs.getString("NPMHSTRNLM"));
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
    
    
    public Vector getListMhsBasedOnCuid(long cuid) {
    	
    	Vector v = null;
    	ListIterator li = null;
		try {
			
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//1. get namados
			stmt = con.prepareStatement("select NMMDOS from CLASS_POOL where UNIQUE_ID=?");
			stmt.setLong(1, cuid);
			rs = stmt.executeQuery();
			rs.next();
			String nmmdos = ""+rs.getString("NMMDOS");
			stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where CLASS_POOL_UNIQUE_ID=? or CUID_INIT=?  order by KDPSTTRNLM,NPMHSTRNLM");
			stmt.setLong(1, cuid);
			stmt.setLong(2, cuid);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				
				do {
					String npmhs = rs.getString("NPMHSTRNLM");
					//nmmdos = rs.getString("NMMDOS");
					li.add(npmhs);
				}
				while(rs.next());
				//v = Tool.removeDuplicateFromVector(v);
				//add info mhs
				//var for searvh
				//id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=dashboard 
				stmt = con.prepareStatement("select * from CIVITAS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where NPMHSMSMHS=?");
				li = v.listIterator();
				
				while(li.hasNext()) {
					String npmhs = (String)li.next();
					stmt.setString(1, npmhs);
					rs = stmt.executeQuery();
					rs.next();
					String id_obj = ""+rs.getLong("CIVITAS.ID_OBJ");
					String kdpst = ""+rs.getString("KDPSTMSMHS");
					String nmmhs = ""+rs.getString("NMMHSMSMHS");
					String smawl = ""+rs.getString("SMAWLMSMHS");
					String obj_lvl = ""+rs.getLong("OBJECT.OBJ_LEVEL");
					li.set(nmmhs+"`"+npmhs+"`"+kdpst+"`"+smawl+"`"+id_obj+"`"+obj_lvl);
					
				}
				Collections.sort(v);
				//System.out.println("v1="+v.size());
				v.add(0, nmmdos);
				//System.out.println("v2="+v.size());
			}
			
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
		//catch (Exception ex) {
		//	ex.printStackTrace();
		//} 
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	return v;
    }	
 
/*
 * deprecated
 */
    public Vector getListKelasKuliahForFeeder(String thsms) {
    	
    	Vector v = null;
    	ListIterator li = null;
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
		try {
			
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from TRNLM where THSMSTRNLM=? order by KDPSTTRNLM");
			stmt.setString(1, thsms);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					String tmp =rs.getString("KDPSTTRNLM")+"`"+rs.getString("KDKMKTRNLM");
					String kls = ""+rs.getString("KELASTRNLM");
					if(Checker.isStringNullOrEmpty(kls)) {
						kls = "null";
					}
					tmp = tmp+"`"+kls;
					li.add(tmp);
					//System.out.println(tmp);
				}
				while(rs.next());
			}
			if(v!=null) {
				stmt = con.prepareStatement("select * from MAKUL where KDPSTMAKUL=? and KDKMKMAKUL=? and STKMKMAKUL=?");
				v = Tool.removeDuplicateFromVector(v);
				Collections.sort(v);
				li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String kdpst = st.nextToken();
					String kdkmk = st.nextToken();
					String kelas = st.nextToken();
					stmt.setString(1, kdpst);
					stmt.setString(2, kdkmk);
					stmt.setString(3, "A");//aktif
					//System.out.println(kdpst+"-"+kdkmk);
					rs = stmt.executeQuery();
					rs.next();
					String nakmk = rs.getString("NAKMKMAKUL");
					String sksmk = ""+(rs.getInt("SKSTMMAKUL")+rs.getInt("SKSPRMAKUL")+rs.getInt("SKSLPMAKUL"));
					li.set(brs+"`"+nakmk+"`"+sksmk);
				}	
			}
			
			//get Shift
			if(v!=null) {
				stmt = con.prepareStatement("select SHIFTMSMHS from CIVITAS inner join TRNLM on NPMHSMSMHS=NPMHSTRNLM where THSMSTRNLM=? and KDPSTTRNLM=? and KDKMKTRNLM=?");
				li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String kdpst = st.nextToken();
					String kdkmk = st.nextToken();
					String kelas = st.nextToken();
					String nakmk = st.nextToken();
					String sksmk = st.nextToken();
					stmt.setString(1, thsms);
					stmt.setString(2, kdpst);
					stmt.setString(3, kdkmk);
					rs = stmt.executeQuery();
					while(rs.next()) {
						String shift = rs.getString(1);
						lif.add(kdpst+"`"+kdkmk+"`"+shift+"`"+kelas+"`"+nakmk+"`"+sksmk);	
					}
					
				}
				//System.out.println("vfsize = "+vf.size());
				Collections.sort(vf);
				vf=Tool.removeDuplicateFromVector(vf);
				if(vf!=null) {
					stmt = con.prepareStatement("insert ignore into TRAKD (THSMSTRAKD,KDPSTTRAKD,KDKMKTRAKD,NAKMKTRAKD,KELASTRAKD,SKSMKTRAKD,SHIFTTRAKD) values (?,?,?,?,?,?,?)");
					lif = vf.listIterator();
					while(lif.hasNext()) {
						String brs = (String)lif.next();
						StringTokenizer st = new StringTokenizer(brs,"`");
						String kdpst = st.nextToken();
						String kdkmk = st.nextToken();
						String shift = st.nextToken();
						String kelas = st.nextToken();
						String nakmk = st.nextToken();
						String sksmk = st.nextToken();
						stmt.setString(1, thsms);
						stmt.setString(2, kdpst);
						stmt.setString(3, kdkmk);
						stmt.setString(4, nakmk);
						stmt.setString(5, kelas);
						stmt.setInt(6, Integer.parseInt(sksmk));
						stmt.setString(7, shift);
						int i = stmt.executeUpdate();
						//System.out.println(brs+" = "+i);
					}	
				}
				
				//System.out.println("INSERT TRAKD DONE");
			}
		}
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
    
    public Vector getListKelasKuliahForFeeder_v1(String thsms) {
    	
    	Vector v = null;
    	ListIterator li = null;
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
		try {
			
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
			con = ds.getConnection();
			stmt = con.prepareStatement("select distinct KDPSTTRNLM,KDKMKTRNLM from TRNLM where THSMSTRNLM=? order by KDPSTTRNLM");
			stmt.setString(1, thsms);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					String tmp =rs.getString("KDPSTTRNLM")+"`"+rs.getString("KDKMKTRNLM");
					li.add(tmp);
				}
				while(rs.next());
			}
			//get jumlah mhs / kdkmk
			if(v!=null) {
				stmt = con.prepareStatement("select count(*) from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=?  and KDKMKTRNLM=?");
				li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String kdpst = st.nextToken();
					String kdkmk = st.nextToken();
					stmt.setString(1, thsms);
					stmt.setString(2, kdpst);
					stmt.setString(3, kdkmk);
					rs = stmt.executeQuery();
					rs.next();
					int count = rs.getInt(1);
					li.set(brs+"`"+count);
				}
			}
			if(v!=null) {
				stmt = con.prepareStatement("select * from MAKUL where KDPSTMAKUL=? and KDKMKMAKUL=? and STKMKMAKUL=?");
				v = Tool.removeDuplicateFromVector(v);
				Collections.sort(v);
				li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String kdpst = st.nextToken();
					String kdkmk = st.nextToken();
					String count = st.nextToken();
					stmt.setString(1, kdpst);
					stmt.setString(2, kdkmk);
					stmt.setString(3, "A");//aktif
					//System.out.println(kdpst+"-"+kdkmk);
					rs = stmt.executeQuery();
					rs.next();
					String nakmk = rs.getString("NAKMKMAKUL");
					String sksmk = ""+(rs.getInt("SKSTMMAKUL")+rs.getInt("SKSPRMAKUL")+rs.getInt("SKSLPMAKUL"));
					li.set(brs+"`"+nakmk+"`"+sksmk);
				}	
			}
			
			if(v!=null) {
				li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					//20201`FTI 1103`9`ILMU BUDAYA SOSIAL DASAR`3
					String kdpst = st.nextToken();
					String kdkmk = st.nextToken();
					String ttmhs = st.nextToken();
					String nakmk = st.nextToken();
					String sksmk = st.nextToken();
					int total = Integer.parseInt(ttmhs);
					int tot_kls = 1;
					if(nakmk.contains("AKHIR")||kdkmk.equalsIgnoreCase("UKL")||kdkmk.equalsIgnoreCase("SUP")||kdkmk.equalsIgnoreCase("SHP")||kdkmk.equalsIgnoreCase("SHP1")||kdkmk.equalsIgnoreCase("SHP2")||kdkmk.equalsIgnoreCase("DST")||kdkmk.equalsIgnoreCase("DST1")||kdkmk.equalsIgnoreCase("DST2")||kdkmk.equalsIgnoreCase("TS")) {
						if(Checker.getKdjen(kdpst).equalsIgnoreCase("A")||Checker.getKdjen(kdpst).equalsIgnoreCase("B")) {
							if(total > 20) {
								do {
									tot_kls++;
								}	
								while(total>(tot_kls*20));
							}
						}
						else {
							if(total > 20) {
								do {
									tot_kls++;
								}	
								while(total>(tot_kls*20));
							}
						}
					}
					else {
						if(Integer.parseInt(kdpst)<57000) {
							if(total > 35) {
								do {
									tot_kls++;
								}	
								while(total>(tot_kls*35));
							
							}
						}
						else {
							if(total > 45) {
								do {
									tot_kls++;
								}	
								while(total>(tot_kls*45));
							}
						}	
					}
					for(int i=1;i<=tot_kls;i++) {
						lif.add(brs+"`"+tot_kls+"`"+i);
					}
				}	
					//insert 
				lif = vf.listIterator();
				stmt = con.prepareStatement("INSERT IGNORE INTO TRAKD_EPSBED(THSMSTRAKD,KDPSTTRAKD,KDKMKTRAKD,NAKMKTRAKD,KELASTRAKD,SKSMKTRAKD,TTMHSTRAKD)VALUES(?,?,?,?,?,?,?)");
				while(lif.hasNext()) {
					String brs = (String)lif.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					//20201`FTI 1103`9`ILMU BUDAYA SOSIAL DASAR`3
					String kdpst = st.nextToken();
					String kdkmk = st.nextToken();
					String ttmhs = st.nextToken();
					String nakmk = st.nextToken();
					String sksmk = st.nextToken();
					String ttkls = st.nextToken();
					String kode_kls = st.nextToken();
					stmt.setString(1, thsms);
					stmt.setString(2, kdpst);
					stmt.setString(3, kdkmk);
					stmt.setString(4, nakmk);
					stmt.setString(5, kode_kls);
					stmt.setInt(6, Integer.parseInt(sksmk));
					int tot_mhs_per_kelas = Integer.parseInt(ttmhs);
					int tot_kelas = Integer.parseInt(ttkls);
					if(tot_mhs_per_kelas>0 && tot_kelas>0) {
						tot_mhs_per_kelas = tot_mhs_per_kelas/tot_kelas;
					}
					stmt.setInt(7, tot_mhs_per_kelas);
					stmt.executeUpdate();
				}
				//cari info dosen
				lif = vf.listIterator();
				stmt = con.prepareStatement("select NODOSTRAKD,NMDOSTRAKD,SURAT_TUGAS from TRAKD_EPSBED where THSMSTRAKD=? and KDPSTTRAKD=? and KDKMKTRAKD=? and KELASTRAKD=?");
				while(lif.hasNext()) {
					String brs = (String)lif.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					//20201`FTI 1103`9`ILMU BUDAYA SOSIAL DASAR`3
					String kdpst = st.nextToken();
					String kdkmk = st.nextToken();
					String ttmhs = st.nextToken();
					String nakmk = st.nextToken();
					String sksmk = st.nextToken();
					String ttkls = st.nextToken();
					String kode_kls = st.nextToken();
					stmt.setString(1, thsms);
					stmt.setString(2, kdpst);
					stmt.setString(3, kdkmk);
					stmt.setString(4, kode_kls);
					rs = stmt.executeQuery();
					if(rs.next()) {
						String nodos = rs.getString(1);
						String nmdos = rs.getString(2);
						String surat = rs.getString(3);
						lif.set(brs+"`"+nodos+"`"+nmdos+"`"+surat);
					}
					else {
						lif.set(brs+"`null`null`null");
					}
					
				}
			}	
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		} 
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	return vf;
    }
    
    
    public Vector getListKelasKuliahForFeeder_v1(String thsms, String tkn_kdpst) {
    	
    	Vector v = null;
    	ListIterator li = null;
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	String seperator = " ";
    	if(tkn_kdpst.contains(",")) {
    		seperator = ",";
    	}
    	else if(tkn_kdpst.contains("`")) {
    		seperator = "`";
    	}
    	else if(tkn_kdpst.contains("~")) {
    		seperator = "~";
    	}
    	String scope_kdpst = null;
    	StringTokenizer st = new StringTokenizer(tkn_kdpst,seperator);
    	if(st.hasMoreTokens()) {
    		scope_kdpst = new String("KDPSTTRNLM='"+st.nextToken()+"'");
    		while(st.hasMoreTokens()) {
    			scope_kdpst = scope_kdpst+" or KDPSTTRNLM='"+st.nextToken()+"'";
    		}
    	}
		try {
			
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
			con = ds.getConnection();
			if(scope_kdpst==null) {
				stmt = con.prepareStatement("select distinct KDPSTTRNLM,KDKMKTRNLM from TRNLM where THSMSTRNLM=? order by KDPSTTRNLM");	
			}
			else {
				stmt = con.prepareStatement("select distinct KDPSTTRNLM,KDKMKTRNLM from TRNLM where THSMSTRNLM=? and ("+scope_kdpst+")order by KDPSTTRNLM");
			}
			//System.out.println("select distinct KDPSTTRNLM,KDKMKTRNLM from TRNLM where THSMSTRNLM=? and ("+scope_kdpst+") order by KDPSTTRNLM");
			stmt.setString(1, thsms);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					String tmp =rs.getString("KDPSTTRNLM")+"`"+rs.getString("KDKMKTRNLM");
					li.add(tmp);
				}
				while(rs.next());
			}
			//get jumlah mhs / kdkmk
			if(v!=null) {
				stmt = con.prepareStatement("select count(*) from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=?  and KDKMKTRNLM=?");
				li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					st = new StringTokenizer(brs,"`");
					String kdpst = st.nextToken();
					String kdkmk = st.nextToken();
					stmt.setString(1, thsms);
					stmt.setString(2, kdpst);
					stmt.setString(3, kdkmk);
					rs = stmt.executeQuery();
					rs.next();
					int count = rs.getInt(1);
					li.set(brs+"`"+count);
				}
			}
			if(v!=null) {
				stmt = con.prepareStatement("select * from MAKUL where KDPSTMAKUL=? and KDKMKMAKUL=? and STKMKMAKUL=?");
				v = Tool.removeDuplicateFromVector(v);
				Collections.sort(v);
				li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					st = new StringTokenizer(brs,"`");
					String kdpst = st.nextToken();
					String kdkmk = st.nextToken();
					String count = st.nextToken();
					stmt.setString(1, kdpst);
					stmt.setString(2, kdkmk);
					stmt.setString(3, "A");//aktif
					//System.out.println(kdpst+"-"+kdkmk);
					rs = stmt.executeQuery();
					rs.next();
					String nakmk = rs.getString("NAKMKMAKUL");
					String sksmk = ""+(rs.getInt("SKSTMMAKUL")+rs.getInt("SKSPRMAKUL")+rs.getInt("SKSLPMAKUL"));
					li.set(brs+"`"+nakmk+"`"+sksmk);
				}	
			}
			
			if(v!=null) {
				li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					st = new StringTokenizer(brs,"`");
					//20201`FTI 1103`9`ILMU BUDAYA SOSIAL DASAR`3
					String kdpst = st.nextToken();
					String kdkmk = st.nextToken();
					String ttmhs = st.nextToken();
					String nakmk = st.nextToken();
					String sksmk = st.nextToken();
					int total = Integer.parseInt(ttmhs);
					int tot_kls = 1;
					if(nakmk.contains("AKHIR")||kdkmk.equalsIgnoreCase("UKL")||kdkmk.equalsIgnoreCase("SUP")||kdkmk.equalsIgnoreCase("SHP")||kdkmk.equalsIgnoreCase("SHP1")||kdkmk.equalsIgnoreCase("SHP2")||kdkmk.equalsIgnoreCase("DST")||kdkmk.equalsIgnoreCase("DST1")||kdkmk.equalsIgnoreCase("DST2")||kdkmk.equalsIgnoreCase("TS")) {
						if(Checker.getKdjen(kdpst).equalsIgnoreCase("A")||Checker.getKdjen(kdpst).equalsIgnoreCase("B")) {
							if(total > 20) {
								do {
									tot_kls++;
								}	
								while(total>(tot_kls*20));
							}
						}
						else {
							if(total > 20) {
								do {
									tot_kls++;
								}	
								while(total>(tot_kls*20));
							}
						}
					}
					else {
						if(Integer.parseInt(kdpst)<57000) {
							if(total > 35) {
								do {
									tot_kls++;
								}	
								while(total>(tot_kls*35));
							
							}
						}
						else {
							if(total > 45) {
								do {
									tot_kls++;
								}	
								while(total>(tot_kls*45));
							}
						}	
					}
					for(int i=1;i<=tot_kls;i++) {
						lif.add(brs+"`"+tot_kls+"`"+i);
					}
				}	
					//insert 
				lif = vf.listIterator();
				stmt = con.prepareStatement("INSERT IGNORE INTO TRAKD_EPSBED(THSMSTRAKD,KDPSTTRAKD,KDKMKTRAKD,NAKMKTRAKD,KELASTRAKD,SKSMKTRAKD,TTMHSTRAKD)VALUES(?,?,?,?,?,?,?)");
				while(lif.hasNext()) {
					String brs = (String)lif.next();
					st = new StringTokenizer(brs,"`");
					//20201`FTI 1103`9`ILMU BUDAYA SOSIAL DASAR`3
					String kdpst = st.nextToken();
					String kdkmk = st.nextToken();
					String ttmhs = st.nextToken();
					String nakmk = st.nextToken();
					String sksmk = st.nextToken();
					String ttkls = st.nextToken();
					String kode_kls = st.nextToken();
					stmt.setString(1, thsms);
					stmt.setString(2, kdpst);
					stmt.setString(3, kdkmk);
					stmt.setString(4, nakmk);
					stmt.setString(5, kode_kls);
					stmt.setInt(6, Integer.parseInt(sksmk));
					int tot_mhs_per_kelas = Integer.parseInt(ttmhs);
					int tot_kelas = Integer.parseInt(ttkls);
					if(tot_mhs_per_kelas>0 && tot_kelas>0) {
						tot_mhs_per_kelas = tot_mhs_per_kelas/tot_kelas;
					}
					stmt.setInt(7, tot_mhs_per_kelas);
					stmt.executeUpdate();
				}
				//cari info dosen
				lif = vf.listIterator();
				stmt = con.prepareStatement("select NODOSTRAKD,NMDOSTRAKD,SURAT_TUGAS from TRAKD_EPSBED where THSMSTRAKD=? and KDPSTTRAKD=? and KDKMKTRAKD=? and KELASTRAKD=?");
				while(lif.hasNext()) {
					String brs = (String)lif.next();
					st = new StringTokenizer(brs,"`");
					//20201`FTI 1103`9`ILMU BUDAYA SOSIAL DASAR`3
					String kdpst = st.nextToken();
					String kdkmk = st.nextToken();
					String ttmhs = st.nextToken();
					String nakmk = st.nextToken();
					String sksmk = st.nextToken();
					String ttkls = st.nextToken();
					String kode_kls = st.nextToken();
					stmt.setString(1, thsms);
					stmt.setString(2, kdpst);
					stmt.setString(3, kdkmk);
					stmt.setString(4, kode_kls);
					rs = stmt.executeQuery();
					if(rs.next()) {
						String nodos = rs.getString(1);
						String nmdos = rs.getString(2);
						String surat = rs.getString(3);
						lif.set(brs+"`"+nodos+"`"+nmdos+"`"+surat);
					}
					else {
						lif.set(brs+"`null`null`null");
					}
					
				}
			}	
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		} 
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	return vf;
    }

    public Vector addInfoDaftarMahasiswaDanDaftarkanKeTrnlmEpsbed(Vector v_getListKelasKuliahForFeeder_v1, String target_thsms) {
    	Vector v=null;
    	Vector v_mhs=null;
    	ListIterator lim = null; 
    	try {
    		if(v_getListKelasKuliahForFeeder_v1!=null && v_getListKelasKuliahForFeeder_v1.size()>0) {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    			con = ds.getConnection();
    			v = new Vector();
    			ListIterator li = v.listIterator();
    			ListIterator liv = v_getListKelasKuliahForFeeder_v1.listIterator();
    			stmt = con.prepareStatement("select NPMHSTRNLM from TRNLM where THSMSTRNLM=? and KDKMKTRNLM=? and KDPSTTRNLM=? order by KDPSTTRNLM,NPMHSTRNLM limit ?,?");
    			int sisa_kls=0, offset=0;
    			double sisa_mhs = 0;
    			v_mhs = new Vector();
    			lim = v_mhs.listIterator();
    			while(liv.hasNext()) {
    				String brs = (String)liv.next();
    				//System.out.println("brs="+brs);
    				li.add(brs);
					StringTokenizer st = new StringTokenizer(brs,"`");
					String kdpst = st.nextToken();
					String kdkmk = st.nextToken();
					String ttmhs = st.nextToken();
					String nakmk = st.nextToken();
					String sksmk = st.nextToken();
					String ttkls = st.nextToken();
					String kode_kls = st.nextToken();
					String nodos = st.nextToken();
					String nmdos = st.nextToken();
					String surat_tugas = st.nextToken();
					//
					stmt.setString(1, target_thsms);
					stmt.setString(2, kdkmk);
					stmt.setString(3, kdpst);
					if(ttkls.equalsIgnoreCase("1")) {
						stmt.setInt(4,0);
						stmt.setInt(5,Integer.parseInt(ttmhs)+5);
						rs = stmt.executeQuery();
						while(rs.next()) {
							String npmhs = ""+rs.getString(1);
							lim.add(npmhs);
						}
						
					}
					else {
						//total kelas > 1
						if(kode_kls.equalsIgnoreCase("1")) {
							int mhs_kelas = (int)Math.ceil(Double.parseDouble(ttmhs)/Integer.parseInt(ttkls));
							stmt.setInt(4,offset);
							stmt.setInt(5,mhs_kelas);
							rs = stmt.executeQuery();
							while(rs.next()) {
								String npmhs = ""+rs.getString(1);
								lim.add(npmhs);
							}
							//li.add(v_mhs);
							sisa_mhs = Integer.parseInt(ttmhs)-mhs_kelas;
							sisa_kls = Integer.parseInt(ttkls)-1;
							offset = mhs_kelas;
							//prev_mhs_kls = mhs_kelas;
						}
						else {
							int mhs_kelas = (int)Math.ceil(sisa_mhs/sisa_kls);
							stmt.setInt(4,offset);
							stmt.setInt(5,mhs_kelas);
							rs = stmt.executeQuery();
							while(rs.next()) {
								String npmhs = ""+rs.getString(1);
								lim.add(npmhs);
							}
							//li.add(v_mhs);
							sisa_mhs = sisa_mhs-mhs_kelas;
							sisa_kls = sisa_kls-1;
							offset = offset+mhs_kelas;
						}	
						//jika sisa mhs 
					}
					li.add(v_mhs);
					//reset v_mhs
					v_mhs = new Vector();
					lim = v_mhs.listIterator();
					//reset jika nextnya adalah mk baru
					if(sisa_kls<1) {
						offset=0;
						sisa_mhs=0;
					}
    			}
    		}
    	}
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
    
    public Vector getKrsYgMemilikiNilaiTunda() {
    	
    	Vector v=null;
    	ListIterator li = null; 
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
			con = ds.getConnection();
			//get mata kuliah yg ada nilai tunda
			//stmt = con.prepareStatement("SELECT THSMSTRNLM,NPMHSTRNLM,KDKMKTRNLM,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,NILAIROBOT,NLAKHROBOT,BOBOTROBOT FROM USG.TRNLM where (NLAKHTRNLM is null or NLAKHTRNLM='T') and (NLAKHROBOT is null or NLAKHROBOT='T')");
			stmt = con.prepareStatement("SELECT THSMSTRNLM,KDJENTRNLM,NPMHSTRNLM,KDKMKTRNLM,SKSMKTRNLM FROM USG.TRNLM where (NLAKHTRNLM is null or NLAKHTRNLM='T') and (NLAKHROBOT is null or NLAKHROBOT='T')");
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					int i=1;
					String thsms = ""+rs.getString(i++);
					String kdjen = ""+rs.getString(i++);
					String npmhs = ""+rs.getString(i++);
					String kdkmk = ""+rs.getString(i++);
					//String nilai = ""+rs.getDouble(i++);
					//String nlakh = ""+rs.getString(i++);
					//String bobot = ""+rs.getDouble(i++);
					String sksmk = ""+rs.getString(i++);
					//String nilai_r = ""+rs.getDouble(i++);
					//String nlakh_r = ""+rs.getString(i++);
					//String bobot_r = ""+rs.getDouble(i++);
					li.add(thsms+"`"+kdjen+"`"+npmhs+"`"+kdkmk+"`"+"`"+sksmk);
				}
				while(rs.next());
				
			}
    	}
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
    
    public Vector getKrsYgMemilikiNilaiTunda(String target_thsms) {
    	
    	Vector v=null;
    	ListIterator li = null; 
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
			con = ds.getConnection();
			//get mata kuliah yg ada nilai tunda
			//stmt = con.prepareStatement("SELECT THSMSTRNLM,NPMHSTRNLM,KDKMKTRNLM,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,NILAIROBOT,NLAKHROBOT,BOBOTROBOT FROM USG.TRNLM where (NLAKHTRNLM is null or NLAKHTRNLM='T') and (NLAKHROBOT is null or NLAKHROBOT='T')");
			stmt = con.prepareStatement("SELECT THSMSTRNLM,KDJENTRNLM,NPMHSTRNLM,KDKMKTRNLM,SKSMKTRNLM FROM USG.TRNLM where THSMSTRNLM=? and (NLAKHTRNLM is null or NLAKHTRNLM='T') and (NLAKHROBOT is null or NLAKHROBOT='T')");
			stmt.setString(1,target_thsms);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					int i=1;
					String thsms = ""+rs.getString(i++);
					String kdjen = ""+rs.getString(i++);
					String npmhs = ""+rs.getString(i++);
					String kdkmk = ""+rs.getString(i++);
					//String nilai = ""+rs.getDouble(i++);
					//String nlakh = ""+rs.getString(i++);
					//String bobot = ""+rs.getDouble(i++);
					String sksmk = ""+rs.getString(i++);
					//String nilai_r = ""+rs.getDouble(i++);
					//String nlakh_r = ""+rs.getString(i++);
					//String bobot_r = ""+rs.getDouble(i++);
					li.add(thsms+"`"+kdjen+"`"+npmhs+"`"+kdkmk+"`"+"`"+sksmk);
				}
				while(rs.next());
				
			}
    	}
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
    
    public Vector getKrsYgMemilikiNilaiTunda(int limit) {
    	
    	Vector v=null;
    	ListIterator li = null; 
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
			con = ds.getConnection();
			//get mata kuliah yg ada nilai tunda
			//stmt = con.prepareStatement("SELECT THSMSTRNLM,NPMHSTRNLM,KDKMKTRNLM,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,NILAIROBOT,NLAKHROBOT,BOBOTROBOT FROM USG.TRNLM where (NLAKHTRNLM is null or NLAKHTRNLM='T') and (NLAKHROBOT is null or NLAKHROBOT='T')");
			stmt = con.prepareStatement("SELECT THSMSTRNLM,KDJENTRNLM,NPMHSTRNLM,KDKMKTRNLM,SKSMKTRNLM FROM USG.TRNLM where (NLAKHTRNLM is null or NLAKHTRNLM='T') and (NLAKHROBOT is null or NLAKHROBOT='T') limit=?");
			stmt.setInt(1,limit);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					int i=1;
					String thsms = ""+rs.getString(i++);
					String kdjen = ""+rs.getString(i++);
					String npmhs = ""+rs.getString(i++);
					String kdkmk = ""+rs.getString(i++);
					//String nilai = ""+rs.getDouble(i++);
					//String nlakh = ""+rs.getString(i++);
					//String bobot = ""+rs.getDouble(i++);
					String sksmk = ""+rs.getString(i++);
					//String nilai_r = ""+rs.getDouble(i++);
					//String nlakh_r = ""+rs.getString(i++);
					//String bobot_r = ""+rs.getDouble(i++);
					li.add(thsms+"`"+kdjen+"`"+npmhs+"`"+kdkmk+"`"+"`"+sksmk);
				}
				while(rs.next());
				
			}
    	}
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
    
    
    public Vector getKrsYgMemilikiNilaiDibawahMinimal(String thsms, double bobot_minimal, int limit, int offset) {
    	
    	Vector v=null;
    	ListIterator li = null; 
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
			con = ds.getConnection();
			//get mata kuliah yg ada nilai tunda
			//stmt = con.prepareStatement("SELECT THSMSTRNLM,NPMHSTRNLM,KDKMKTRNLM,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,NILAIROBOT,NLAKHROBOT,BOBOTROBOT FROM USG.TRNLM where (NLAKHTRNLM is null or NLAKHTRNLM='T') and (NLAKHROBOT is null or NLAKHROBOT='T')");
			stmt = con.prepareStatement("SELECT THSMSTRNLM,KDJENTRNLM,NPMHSTRNLM,KDKMKTRNLM,SKSMKTRNLM FROM TRNLM where THSMSTRNLM=? and (NLAKHROBOT<>'T' or NLAKHROBOT is null or BOBOTROBOT<?) limit ?,?");
			//ambil semuanya , nanti di overide lg utk mk ts yg masih mengulang dengan nilai E
			stmt.setString(1, thsms);
			stmt.setDouble(2, bobot_minimal);
			stmt.setInt(3,offset);
			stmt.setInt(4,limit);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					int i=1;
					thsms = ""+rs.getString(i++);
					String kdjen = ""+rs.getString(i++);
					String npmhs = ""+rs.getString(i++);
					String kdkmk = ""+rs.getString(i++);
					//String nilai = ""+rs.getDouble(i++);
					//String nlakh = ""+rs.getString(i++);
					//String bobot = ""+rs.getDouble(i++);
					String sksmk = ""+rs.getString(i++);
					//String nilai_r = ""+rs.getDouble(i++);
					//String nlakh_r = ""+rs.getString(i++);
					//String bobot_r = ""+rs.getDouble(i++);
					li.add(thsms+"`"+kdjen+"`"+npmhs+"`"+kdkmk+"`"+sksmk);
				}
				while(rs.next());
				
			}
    	}
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
    
    public static Vector getStandarRiwayatKrsMhs(String npmhs,Connection con) {
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	Vector v=null;
    	ListIterator li = null; 
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			//get mata kuliah yg ada nilai tunda
			//stmt = con.prepareStatement("SELECT THSMSTRNLM,NPMHSTRNLM,KDKMKTRNLM,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,NILAIROBOT,NLAKHROBOT,BOBOTROBOT FROM USG.TRNLM where (NLAKHTRNLM is null or NLAKHTRNLM='T') and (NLAKHROBOT is null or NLAKHROBOT='T')");
			stmt = con.prepareStatement("SELECT THSMSTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM from TRNLM where NPMHSTRNLM=? order by THSMSTRNLM");
			stmt.setString(1, npmhs);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				li = v.listIterator();
				do {
					int i=1;
					String thsms = ""+rs.getString(i++);
					String kdpst = ""+rs.getString(i++);
					npmhs = ""+rs.getString(i++);
					String kdkmk = ""+rs.getString(i++);
					String nilai = ""+rs.getDouble(i++);
					String nlakh = ""+rs.getString(i++);
					String bobot = ""+rs.getDouble(i++);
					String sksmk = ""+rs.getString(i++);
					//String nilai_r = ""+rs.getDouble(i++);
					//String nlakh_r = ""+rs.getString(i++);
					//String bobot_r = ""+rs.getDouble(i++);
					li.add(thsms+"`"+kdpst+"`"+npmhs+"`"+kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk);
				}
				while(rs.next());
				
			}
    	}
		catch (Exception ex) {
			ex.printStackTrace();
		} 
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			//if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return v;
    }
    	

}


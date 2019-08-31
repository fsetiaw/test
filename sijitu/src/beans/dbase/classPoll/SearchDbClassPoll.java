package beans.dbase.classPoll;

import beans.dbase.SearchDb;

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

import java.util.ConcurrentModificationException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;
import java.util.Collections;
/**
 * Session Bean implementation class SearchDbClassPoll
 */
@Stateless
@LocalBean
public class SearchDbClassPoll extends SearchDb {
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
    public SearchDbClassPoll() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbClassPoll(String operatorNpm) {
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
    public SearchDbClassPoll(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

/*
 * deprecated : pake yg dengan scope kampus
 */
    public Vector getDistinctClassPerKdpst(String thsms, String kdpst) {
    	/*
    	 * tambah filter scope scope kampus - scope "viewAbsen"
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KDPST=?");
			stmt.setString(1, thsms);
			stmt.setString(2, kdpst);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				String tmp = "";
				String kode_gabung = ""+rs.getString("KODE_PENGGABUNGAN");
				String idkur = ""+rs.getLong("IDKUR");
				String idkmk = ""+rs.getLong("IDKMK");
				String shift = ""+rs.getString("SHIFT");
				String nopll = ""+rs.getString("NORUT_KELAS_PARALEL");
				String npmdos = ""+rs.getString("NPMDOS");
				String nmmdos = ""+rs.getString("NMMDOS");
				String kdkmk = ""+rs.getString("KDKMKMAKUL");
				String nakmk = ""+rs.getString("NAKMKMAKUL");
				String cancel = ""+rs.getBoolean("CANCELED");
				String kodeKampus = ""+rs.getString("KODE_KAMPUS");
				tmp = kode_gabung+"`"+nakmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+idkmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kodeKampus;
				li.add(tmp);
			}
			Collections.sort(v);
			
			v = Tool.removeDuplicateFromVector(v);
			li = v.listIterator();
			//proses kalo ada kelas gabungan
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KODE_PENGGABUNGAN=?");
			while(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				String kode_gabung = ""+st.nextToken();
				if(kode_gabung!=null && !Checker.isStringNullOrEmpty(kode_gabung)) {
					stmt.setString(1, thsms);
					stmt.setString(2, kode_gabung);
					rs = stmt.executeQuery();
					String tmp = "";
					while(rs.next()) {
						String kod_gabung = ""+rs.getString("KODE_PENGGABUNGAN");
						String idkur = ""+rs.getLong("IDKUR");
						String idkmk = ""+rs.getLong("IDKMK");
						String shift = ""+rs.getString("SHIFT");
						String nopll = ""+rs.getString("NORUT_KELAS_PARALEL");
						String npmdos = ""+rs.getString("NPMDOS");
						String nmmdos = ""+rs.getString("NMMDOS");
						String kdkmk = ""+rs.getString("KDKMKMAKUL");
						String nakmk = ""+rs.getString("NAKMKMAKUL");
						String cancel = ""+rs.getBoolean("CANCELED");
						String kodeKampus = ""+rs.getString("KODE_KAMPUS");
						tmp = tmp+kod_gabung+"`"+nakmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+idkmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kodeKampus+"||";
					}
					li.set(tmp);
				}
				
			}
			//stmt = con.prepareStatement("");
			
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
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	
    	return v;
    }

    /*
     * NGGA DIPAKE LAGI ganti KELAS_PERKIULIAHAN_RULES
     */
    public Vector getClassPollRules(Vector vScope, String kode_kampus) {
    	//Vector v = new Vector();
    	//ListIterator li = v.listIterator();
    	if(vScope!=null && vScope.size()>0) {
    		try {
        		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from CLASS_POOL_RULES where KDPST=? and KODE_KAMPUS=? order by THSMS DESC limit 1");
    			ListIterator liv = vScope.listIterator();
    			while(liv.hasNext()) {
    				String brs = (String)liv.next();
    				StringTokenizer st = new StringTokenizer(brs);
    				String idObj = st.nextToken();
    				String kdpst = st.nextToken();
    				String keter = st.nextToken();
    				String lvl = st.nextToken();
    				String kdjen = st.nextToken();
    				stmt.setString(1, kdpst);
    				stmt.setString(2, kode_kampus);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					String tkn_verificator = ""+rs.getString("TKN_VERIFICATOR");
    					String urutan = ""+rs.getBoolean("URUTAN");
    					
    					tkn_verificator = tkn_verificator.replace(" ", "_");
    					liv.set(brs+" "+tkn_verificator+" "+urutan+" "+kode_kampus);
    				}
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
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}	
    	}
    		
    	
    	return vScope;
    }
    
    /*
     * deprecated : ada tambahan cuid pada _v1
     */
    public Vector getDistinctClassPerKdpst(String thsms, String kdpst, String tknScopeKampus) {
    	/*
    	 * tambah filter scope scope kampus - scope "viewAbsen"
    	 */
    	
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//System.out.println(thsms);
	    	//System.out.println(kdpst);
	    	//System.out.println(tknScopeKampus);
			//get list kelas dari class polll
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KDPST=?");
			stmt.setString(1, thsms);
			stmt.setString(2, kdpst);
			rs = stmt.executeQuery();
			//System.out.println("tmp0");
			while(rs.next()) {
				String tmp = "";
				String kode_gabung = ""+rs.getString("KODE_PENGGABUNGAN");
				String idkur = ""+rs.getLong("IDKUR");
				String idkmk = ""+rs.getLong("IDKMK");
				String shift = ""+rs.getString("SHIFT");
				String nopll = ""+rs.getString("NORUT_KELAS_PARALEL");
				String npmdos = ""+rs.getString("NPMDOS");
				String nmmdos = ""+rs.getString("NMMDOS");
				String kdkmk = ""+rs.getString("KDKMKMAKUL");
				String nakmk = ""+rs.getString("NAKMKMAKUL");
				String cancel = ""+rs.getBoolean("CANCELED");
				String kodeKampus = ""+rs.getString("KODE_KAMPUS");
				if(tknScopeKampus.contains(kodeKampus)) {
					tmp = kode_gabung+"`"+nakmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+idkmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kodeKampus;
					li.add(tmp);	
					//System.out.println("tmp1="+tmp);
				}
			}
			Collections.sort(v);
			
			//v = Tool.removeDuplicateFromVector(v);
			li = v.listIterator();
			//proses kalo ada kelas gabungan
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KODE_PENGGABUNGAN=?");
			while(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				String kode_gabung = ""+st.nextToken();
				if(kode_gabung!=null && !Checker.isStringNullOrEmpty(kode_gabung)) {
					stmt.setString(1, thsms);
					stmt.setString(2, kode_gabung);
					rs = stmt.executeQuery();
					String tmp = "";
					while(rs.next()) {
						String kod_gabung = ""+rs.getString("KODE_PENGGABUNGAN");
						String idkur = ""+rs.getLong("IDKUR");
						String idkmk = ""+rs.getLong("IDKMK");
						String shift = ""+rs.getString("SHIFT");
						String nopll = ""+rs.getString("NORUT_KELAS_PARALEL");
						String npmdos = ""+rs.getString("NPMDOS");
						String nmmdos = ""+rs.getString("NMMDOS");
						String kdkmk = ""+rs.getString("KDKMKMAKUL");
						String nakmk = ""+rs.getString("NAKMKMAKUL");
						String cancel = ""+rs.getBoolean("CANCELED");
						String kodeKampus = ""+rs.getString("KODE_KAMPUS");
						tmp = tmp+kod_gabung+"`"+nakmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+idkmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kodeKampus+"||";
					}
					li.set(tmp);
					//System.out.println(tmp);
				}
				
			}
			//v = Tool.removeDuplicateFromVector(v);
			/*
			 * get jumlah mhs
			 */
			//String thsmsNow = Checker.getThsmsNow();
			
			li = v.listIterator();
			
			stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=? AND KDKMKTRNLM=? AND KELASTRNLM=? AND SHIFTTRNLM=? AND KODE_KAMPUS=?");
			while(li.hasNext()) {
				String brs = (String)li.next();
				String nuTmp = "";
				StringTokenizer st = new StringTokenizer(brs,"||");
				Vector vTmp =new Vector();
				ListIterator liTmp = vTmp.listIterator();
				if(st.countTokens()>1) { //kelas gabungan
					while(st.hasMoreTokens()) {
						String tmp = st.nextToken();
						StringTokenizer stt = new StringTokenizer(tmp,"`");
						//tmp = tmp+kod_gabung+"`"+nakmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+idkmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kodeKampus+"||";
						String kod_gabung = stt.nextToken();
						String nakmk = stt.nextToken();
						String nopll = stt.nextToken();
						String shift = stt.nextToken();
						String idkur = stt.nextToken();
						String idkmk = stt.nextToken();
						String npmdos = stt.nextToken();
						String nmmdos = stt.nextToken();
						String kdkmk = stt.nextToken();
						String cancel = stt.nextToken();
						kdpst = stt.nextToken();
						String kodeKampus = stt.nextToken();
						stmt.setString(1,thsms);
						stmt.setString(2,kdkmk);
						stmt.setString(3,nopll);
						stmt.setString(4,shift);
						stmt.setString(5,kodeKampus);
						rs = stmt.executeQuery();
						//nuTmp = "";
						while(rs.next()) {
							String npmhs = rs.getString("NPMHSTRNLM");
							liTmp.add(npmhs);
						}
						//keep looping adding npmhs dgn kode gabung yg sama tapi beda MK
					}
					String listNpm = "";
					if(vTmp.size()<1) {
						listNpm = "null";
					}
					else {
						vTmp = Tool.removeDuplicateFromVector(vTmp);
						liTmp = vTmp.listIterator();
						while(liTmp.hasNext()) {
							String npm = (String)liTmp.next();
							listNpm = listNpm+npm;
							if(liTmp.hasNext()) {
								listNpm = listNpm+",";
							}
						}	
					}
					//nuTmp = nuTmp+brs+"`"+listNpm+"||";
					
					 //tambah info
					 
					nuTmp=null;
					st = new StringTokenizer(brs,"||");
					while(st.hasMoreTokens()) {
						String tmp = st.nextToken();
						//StringTokenizer stt = new StringTokenizer(tmp,"`");
						//String kod_gabung = stt.nextToken();
						//String idkur = stt.nextToken();
						//String idkmk = stt.nextToken();
						//String shift = stt.nextToken();
						//String nopll = stt.nextToken();
						//String npmdos = stt.nextToken();
						//String nmmdos = stt.nextToken();
						//String kdkmk = stt.nextToken();
						//String nakmk = stt.nextToken();
						//String cancel = stt.nextToken();
						//String kodeKampus = stt.nextToken();
						nuTmp = nuTmp+tmp+"`"+listNpm+"||";
					}
					//System.out.println("nuTmp="+nuTmp);
					li.set(nuTmp);
				}
				else {
					StringTokenizer stt = new StringTokenizer(brs,"`");
					String kod_gabung = stt.nextToken();
					String nakmk = stt.nextToken();
					String nopll = stt.nextToken();
					String shift = stt.nextToken();
					String idkur = stt.nextToken();
					String idkmk = stt.nextToken();
					String npmdos = stt.nextToken();
					String nmmdos = stt.nextToken();
					String kdkmk = stt.nextToken();
					String cancel = stt.nextToken();
					kdpst = stt.nextToken();
					String kodeKampus = stt.nextToken();
					stmt.setString(1,thsms);
					stmt.setString(2,kdkmk);
					stmt.setString(3,nopll);
					stmt.setString(4,shift);
					stmt.setString(5,kodeKampus);
					rs = stmt.executeQuery();
					
					while(rs.next()) {
						String npmhs = rs.getString("NPMHSTRNLM");
						liTmp.add(npmhs);
					}
					String listNpm = "";
					
					if(vTmp.size()<1) {
						listNpm = "null";
					}
					else {
						vTmp = Tool.removeDuplicateFromVector(vTmp);
						liTmp = vTmp.listIterator();
						while(liTmp.hasNext()) {
							String npm = (String)liTmp.next();
							listNpm = listNpm+npm;
							if(liTmp.hasNext()) {
								listNpm = listNpm+",";
							}
						}	
					}
					//System.out.println("nuTmp1="+brs+"`"+listNpm);
					li.set(brs+"`"+listNpm);
				}		
			}
    
			//stmt = con.prepareStatement("");
			
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
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	
    	return v;
    }
    
    
    public Vector getDistinctClassPerKdpst_v1(String thsms, String kdpst, String tknScopeKampus) {
    	/*
    	 * tambah filter scope scope kampus - scope "viewAbsen"
    	 */
    	
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//System.out.println(thsms);
	    	//System.out.println(kdpst);
	    	//System.out.println(tknScopeKampus);
			//get list kelas dari class polll
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KDPST=?");
			stmt.setString(1, thsms);
			stmt.setString(2, kdpst);
			rs = stmt.executeQuery();
			//System.out.println("tmp0");
			while(rs.next()) {
				String tmp = "";
				String kode_gabung = ""+rs.getString("KODE_PENGGABUNGAN");
				String idkur = ""+rs.getLong("IDKUR");
				String idkmk = ""+rs.getLong("IDKMK");
				String shift = ""+rs.getString("SHIFT");
				String nopll = ""+rs.getString("NORUT_KELAS_PARALEL");
				String npmdos = ""+rs.getString("NPMDOS");
				String nmmdos = ""+rs.getString("NMMDOS");
				String kdkmk = ""+rs.getString("KDKMKMAKUL");
				String nakmk = ""+rs.getString("NAKMKMAKUL");
				String cancel = ""+rs.getBoolean("CANCELED");
				String kodeKampus = ""+rs.getString("KODE_KAMPUS");
				String cuid = ""+rs.getString("UNIQUE_ID");
				if(tknScopeKampus.contains(kodeKampus)) {
					tmp = kode_gabung+"`"+nakmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+idkmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kodeKampus+"`"+cuid;
					li.add(tmp);	
					//System.out.println("tmp1="+tmp);
				}
			}
			Collections.sort(v);
			
			//v = Tool.removeDuplicateFromVector(v);
			li = v.listIterator();
			//proses kalo ada kelas gabungan
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KODE_PENGGABUNGAN=?");
			while(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				String kode_gabung = ""+st.nextToken();
				if(kode_gabung!=null && !Checker.isStringNullOrEmpty(kode_gabung)) {
					stmt.setString(1, thsms);
					stmt.setString(2, kode_gabung);
					rs = stmt.executeQuery();
					String tmp = "";
					while(rs.next()) {
						String kod_gabung = ""+rs.getString("KODE_PENGGABUNGAN");
						String idkur = ""+rs.getLong("IDKUR");
						String idkmk = ""+rs.getLong("IDKMK");
						String shift = ""+rs.getString("SHIFT");
						String nopll = ""+rs.getString("NORUT_KELAS_PARALEL");
						String npmdos = ""+rs.getString("NPMDOS");
						String nmmdos = ""+rs.getString("NMMDOS");
						String kdkmk = ""+rs.getString("KDKMKMAKUL");
						String nakmk = ""+rs.getString("NAKMKMAKUL");
						String cancel = ""+rs.getBoolean("CANCELED");
						String kodeKampus = ""+rs.getString("KODE_KAMPUS");
						String cuid = ""+rs.getString("UNIQUE_ID");
						tmp = tmp+kod_gabung+"`"+nakmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+idkmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kodeKampus+"`"+cuid+"||";
					}
					li.set(tmp);
					//System.out.println(tmp);
				}
				
			}
			//v = Tool.removeDuplicateFromVector(v);
			/*
			 * get jumlah mhs
			 */
			//String thsmsNow = Checker.getThsmsNow();
			
			li = v.listIterator();
			
			stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=? AND KDKMKTRNLM=? AND KELASTRNLM=? AND SHIFTTRNLM=? AND KODE_KAMPUS=?");
			while(li.hasNext()) {
				String brs = (String)li.next();
				String nuTmp = "";
				StringTokenizer st = new StringTokenizer(brs,"||");
				Vector vTmp =new Vector();
				ListIterator liTmp = vTmp.listIterator();
				if(st.countTokens()>1) { //kelas gabungan
					while(st.hasMoreTokens()) {
						String tmp = st.nextToken();
						StringTokenizer stt = new StringTokenizer(tmp,"`");
						//tmp = tmp+kod_gabung+"`"+nakmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+idkmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kodeKampus+"||";
						String kod_gabung = stt.nextToken();
						String nakmk = stt.nextToken();
						String nopll = stt.nextToken();
						String shift = stt.nextToken();
						String idkur = stt.nextToken();
						String idkmk = stt.nextToken();
						String npmdos = stt.nextToken();
						String nmmdos = stt.nextToken();
						String kdkmk = stt.nextToken();
						String cancel = stt.nextToken();
						kdpst = stt.nextToken();
						String kodeKampus = stt.nextToken();
						String cuid = stt.nextToken();
						stmt.setString(1,thsms);
						stmt.setString(2,kdkmk);
						stmt.setString(3,nopll);
						stmt.setString(4,shift);
						stmt.setString(5,kodeKampus);
						rs = stmt.executeQuery();
						//nuTmp = "";
						while(rs.next()) {
							String npmhs = rs.getString("NPMHSTRNLM");
							liTmp.add(npmhs);
						}
						//keep looping adding npmhs dgn kode gabung yg sama tapi beda MK
					}
					String listNpm = "";
					if(vTmp.size()<1) {
						listNpm = "null";
					}
					else {
						vTmp = Tool.removeDuplicateFromVector(vTmp);
						liTmp = vTmp.listIterator();
						while(liTmp.hasNext()) {
							String npm = (String)liTmp.next();
							listNpm = listNpm+npm;
							if(liTmp.hasNext()) {
								listNpm = listNpm+",";
							}
						}	
					}
					//nuTmp = nuTmp+brs+"`"+listNpm+"||";
					
					 //tambah info
					 
					nuTmp=null;
					st = new StringTokenizer(brs,"||");
					while(st.hasMoreTokens()) {
						String tmp = st.nextToken();
						//StringTokenizer stt = new StringTokenizer(tmp,"`");
						//String kod_gabung = stt.nextToken();
						//String idkur = stt.nextToken();
						//String idkmk = stt.nextToken();
						//String shift = stt.nextToken();
						//String nopll = stt.nextToken();
						//String npmdos = stt.nextToken();
						//String nmmdos = stt.nextToken();
						//String kdkmk = stt.nextToken();
						//String nakmk = stt.nextToken();
						//String cancel = stt.nextToken();
						//String kodeKampus = stt.nextToken();
						nuTmp = nuTmp+tmp+"`"+listNpm+"||";
					}
					//System.out.println("nuTmp="+nuTmp);
					li.set(nuTmp);
				}
				else {
					StringTokenizer stt = new StringTokenizer(brs,"`");
					String kod_gabung = stt.nextToken();
					String nakmk = stt.nextToken();
					String nopll = stt.nextToken();
					String shift = stt.nextToken();
					String idkur = stt.nextToken();
					String idkmk = stt.nextToken();
					String npmdos = stt.nextToken();
					String nmmdos = stt.nextToken();
					String kdkmk = stt.nextToken();
					String cancel = stt.nextToken();
					kdpst = stt.nextToken();
					String kodeKampus = stt.nextToken();
					String cuid = stt.nextToken();
					stmt.setString(1,thsms);
					stmt.setString(2,kdkmk);
					stmt.setString(3,nopll);
					stmt.setString(4,shift);
					stmt.setString(5,kodeKampus);
					rs = stmt.executeQuery();
					
					while(rs.next()) {
						String npmhs = rs.getString("NPMHSTRNLM");
						liTmp.add(npmhs);
					}
					String listNpm = "";
					
					if(vTmp.size()<1) {
						listNpm = "null";
					}
					else {
						vTmp = Tool.removeDuplicateFromVector(vTmp);
						liTmp = vTmp.listIterator();
						while(liTmp.hasNext()) {
							String npm = (String)liTmp.next();
							listNpm = listNpm+npm;
							if(liTmp.hasNext()) {
								listNpm = listNpm+",";
							}
						}	
					}
					//System.out.println("nuTmp1="+brs+"`"+listNpm);
					li.set(brs+"`"+listNpm);
				}		
			}
    
			//stmt = con.prepareStatement("");
			
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
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	
    	return v;
    }
    
    public Vector getDistinctClassPerKdpst_v1_simple(String thsms, String kdpst, String tknScopeKampus) {
    	/*
    	 * output : list seluruh kelas kecuali yang di cancel & tidak ada kode penggabungan
    	 */
    	
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//System.out.println(thsms);
	    	//System.out.println(kdpst);
	    	//System.out.println(tknScopeKampus);
			/*
			 * 1. Get kelas non penggabungan yang tidak di cancel
			 */
			//stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KDPST=? order by KODE_PENGGABUNGAN,CANCELED,IDKMK");
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KDPST=? and CANCELED=? and KODE_PENGGABUNGAN is null");
			stmt.setString(1, thsms);
			stmt.setString(2, kdpst);
			stmt.setBoolean(3,false);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String tmp = "";
				//String kode_gabung = ""+rs.getString("KODE_PENGGABUNGAN");
				
				
				String idkur_cp = ""+rs.getInt("IDKUR");
				String idkmk_cp = ""+rs.getInt("IDKMK");
				String thsms_cp = ""+rs.getString("THSMS");
				String kdpst_cp = ""+rs.getString("KDPST");
				String shift_cp = ""+rs.getString("SHIFT");
				String nopll_cp = ""+rs.getInt("NORUT_KELAS_PARALEL");
				String init_npm_inp_cp = ""+rs.getString("INIT_NPM_INPUT");
				String lates_npm_upd_cp = ""+rs.getString("LATEST_NPM_UPDATE");
				String lates_stat_cp = ""+rs.getString("LATEST_STATUS_INFO");
				String cur_avail_stat_cp = ""+rs.getString("CURR_AVAIL_STATUS");
				String locked_cp = ""+rs.getBoolean("LOCKED");
				String npmdos_cp = ""+rs.getString("NPMDOS");
				String nodos_cp = ""+rs.getString("NODOS");
				String npmasdos_cp = ""+rs.getString("NPMASDOS");
				String noasdos_cp = ""+rs.getString("NOASDOS");
				String canceled_cp = ""+rs.getBoolean("CANCELED");
				String kode_kls_cp = ""+rs.getString("KODE_KELAS");
				String kode_ruang_cp = ""+rs.getString("KODE_RUANG");
				String kode_gedung_cp = ""+rs.getString("KODE_GEDUNG");
				String kode_kampus_cp = ""+rs.getString("KODE_KAMPUS");
				String tkn_hr_tm_cp = ""+rs.getString("TKN_HARI_TIME");
				String nmmdos_cp = ""+rs.getString("NMMDOS");
				String nmmasdos_cp = ""+rs.getString("NMMASDOS");
				String enrolled_cp = ""+rs.getInt("ENROLLED");
				String max_enrolled_cp = ""+rs.getInt("MAX_ENROLLED");
				String min_enrolled_cp = ""+rs.getInt("MIN_ENROLLED");
				String sub_keter_kdmk_cp = ""+rs.getString("SUB_KETER_KDKMK");
				String init_req_tm_cp = ""+rs.getTimestamp("INIT_REQ_TIME");
				String tkn_npm_approval_cp = ""+rs.getString("TKN_NPM_APPROVAL");
				String tkn_apr_tm_cp = ""+rs.getString("TKN_APPROVAL_TIME");
				String target_ttmhs_cp = ""+rs.getInt("TARGET_TTMHS");
				String passed_cp = ""+rs.getBoolean("PASSED");
				String rejected_cp = ""+rs.getBoolean("REJECTED");
				String kode_gabung_cp = ""+rs.getString("KODE_PENGGABUNGAN");
				String kode_gabung_univ_cp = ""+rs.getString("KODE_GABUNGAN_UNIV");
				String cuid_cp = ""+rs.getLong("UNIQUE_ID");
				
				if(tknScopeKampus.contains(kode_kampus_cp)) {
					//tmp = idkmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+nakmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kodeKampus+"`"+cuid;
					tmp = idkur_cp+","+shift_cp+","+nopll_cp+","+cur_avail_stat_cp+","+npmdos_cp+","+npmasdos_cp+","+canceled_cp+","+kode_kls_cp+","+kode_ruang_cp+","+kode_gedung_cp+","+kode_kampus_cp+","+tkn_hr_tm_cp+","+nmmdos_cp+","+nmmasdos_cp+","+enrolled_cp+","+max_enrolled_cp+","+min_enrolled_cp+","+kode_gabung_cp+","+cuid_cp+","+idkmk_cp;
					li.add(tmp);	
				}
			}
			/*
			 * 2. Get kelas penggabungan 
			 * kalo kelas penggabungan tidak perlu ada filter kampus, fak , dsb karena disatukan jadi avail to take
			 */
			stmt = con.prepareStatement("select distinct KODE_PENGGABUNGAN from CLASS_POOL where THSMS=? and KDPST=? and KODE_PENGGABUNGAN is not null");
			stmt.setString(1, thsms);
			stmt.setString(2, kdpst);
			rs = stmt.executeQuery();
			String list_kode = null;
			if(rs.next()) {
				list_kode = new String();
				do {
					list_kode = list_kode+rs.getString("KODE_PENGGABUNGAN")+"`";
				}
				while(rs.next());
			}
			//System.out.println("list_kode="+list_kode);
			if(list_kode!=null && !Checker.isStringNullOrEmpty(list_kode)) {
				stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=?  and KODE_PENGGABUNGAN=? order by CANCELED,SHIFT");
				StringTokenizer st = new StringTokenizer(list_kode,"`");
				while(st.hasMoreTokens()) {
					String tmp = "";
					String kode = st.nextToken();
					stmt.setString(1, thsms);
					stmt.setLong(2, Long.parseLong(kode));
					rs = stmt.executeQuery();
					while(rs.next()) {
						String idkur_cp = ""+rs.getInt("IDKUR");
						String idkmk_cp = ""+rs.getInt("IDKMK");
						String thsms_cp = ""+rs.getString("THSMS");
						String kdpst_cp = ""+rs.getString("KDPST");
						String shift_cp = ""+rs.getString("SHIFT");
						String nopll_cp = ""+rs.getInt("NORUT_KELAS_PARALEL");
						String init_npm_inp_cp = ""+rs.getString("INIT_NPM_INPUT");
						String lates_npm_upd_cp = ""+rs.getString("LATEST_NPM_UPDATE");
						String lates_stat_cp = ""+rs.getString("LATEST_STATUS_INFO");
						String cur_avail_stat_cp = ""+rs.getString("CURR_AVAIL_STATUS");
						String locked_cp = ""+rs.getBoolean("LOCKED");
						String npmdos_cp = ""+rs.getString("NPMDOS");
						String nodos_cp = ""+rs.getString("NODOS");
						String npmasdos_cp = ""+rs.getString("NPMASDOS");
						String noasdos_cp = ""+rs.getString("NOASDOS");
						String canceled_cp = ""+rs.getBoolean("CANCELED");
						String kode_kls_cp = ""+rs.getString("KODE_KELAS");
						String kode_ruang_cp = ""+rs.getString("KODE_RUANG");
						String kode_gedung_cp = ""+rs.getString("KODE_GEDUNG");
						String kode_kampus_cp = ""+rs.getString("KODE_KAMPUS");
						String tkn_hr_tm_cp = ""+rs.getString("TKN_HARI_TIME");
						String nmmdos_cp = ""+rs.getString("NMMDOS");
						String nmmasdos_cp = ""+rs.getString("NMMASDOS");
						String enrolled_cp = ""+rs.getInt("ENROLLED");
						String max_enrolled_cp = ""+rs.getInt("MAX_ENROLLED");
						String min_enrolled_cp = ""+rs.getInt("MIN_ENROLLED");
						String sub_keter_kdmk_cp = ""+rs.getString("SUB_KETER_KDKMK");
						String init_req_tm_cp = ""+rs.getTimestamp("INIT_REQ_TIME");
						String tkn_npm_approval_cp = ""+rs.getString("TKN_NPM_APPROVAL");
						String tkn_apr_tm_cp = ""+rs.getString("TKN_APPROVAL_TIME");
						String target_ttmhs_cp = ""+rs.getInt("TARGET_TTMHS");
						String passed_cp = ""+rs.getBoolean("PASSED");
						String rejected_cp = ""+rs.getBoolean("REJECTED");
						String kode_gabung_cp = ""+rs.getString("KODE_PENGGABUNGAN");
						String kode_gabung_univ_cp = ""+rs.getString("KODE_GABUNGAN_UNIV");
						String cuid_cp = ""+rs.getLong("UNIQUE_ID");
						
						
						
						//if(tknScopeKampus.contains(kodeKampus)) {
						//shift2+","+norutKelasParalel2+","+currStatus2+","+npmdos2+","+npmasdos2+","+canceled2+","+kodeKelas2+","+kodeRuang2+","+kodeGedung2+","+kodeKampus2+","+tknDayTime2+","+nmmdos2+","+nmmasdos2+","+enrolled2+","+maxEnrolled2+","+minEnrolled2);
						//shift_cp+","+nopll_cp+","+cur_avail_stat_cp+","+npmdos_cp+","+npmasdos_cp+","+canceled_cp+","+kode_kls_cp+","+kode_ruang_cp+","+kode_gedung_cp+","+kode_kampus_cp+","+tkn_hr_tm_cp+","+nmmdos_cp+","+nmmasdos_cp+","+enrolled_cp+","+max_enrolled_cp+","+min_enrolled_cp);
						tmp = tmp+idkur_cp+","+shift_cp+","+nopll_cp+","+cur_avail_stat_cp+","+npmdos_cp+","+npmasdos_cp+","+canceled_cp+","+kode_kls_cp+","+kode_ruang_cp+","+kode_gedung_cp+","+kode_kampus_cp+","+tkn_hr_tm_cp+","+nmmdos_cp+","+nmmasdos_cp+","+enrolled_cp+","+max_enrolled_cp+","+min_enrolled_cp+","+kode_gabung_cp+","+cuid_cp+","+idkmk_cp+"||";	
					}
					li.add(tmp);	
				}
				
			}
			Collections.sort(v);

			
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
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	
    	 //li = v.listIterator();
    	 //while(li.hasNext()) {
    	//	 //System.out.println("oke="+(String)li.next());
    	 //}
    	
    	return v;
    }
    
    
    /*
     * PRUBAHAN DISINI HARUS DIRUBAH DI adhockAddInfoMkToVsdh
     */
    public Vector adhockAddInfoMkToVblm(Vector vBlmOrSdh, String thsms_target, String kdpst) {
    	/*
    	 * tambah detail makul
    	 * + alternativ kdkmk = matakuliah sama yg ada pada prodi fakltas atau kampus lainnya
    	 */
    	if(vBlmOrSdh!=null && vBlmOrSdh.size()>0) {
    		ListIterator li = vBlmOrSdh.listIterator();
    		
	    	
	    	try {
				Context initContext  = new InitialContext();
				Context envContext  = (Context)initContext.lookup("java:/comp/env");
				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
				con = ds.getConnection();
				stmt = con.prepareStatement("select * from CLASS_POOL where IDKMK=? and THSMS=? and KDPST=? and SHIFT=? and NORUT_KELAS_PARALEL=?");
				while(li.hasNext()) {
					String brs = (String)li.next();
					//System.out.println("barisx="+brs);
					StringTokenizer st=new StringTokenizer(brs,",");
					String idkmk=st.nextToken();
					String kdkmk=st.nextToken();
					String nakmk=st.nextToken();
					//nakmk = nakmk.replace("tandaKoma", ",");
					String skstm=st.nextToken();
					String skspr=st.nextToken();
					String skslp=st.nextToken();
					String semes=st.nextToken();
					String tmp = "";
					boolean first = true;
					while(st.hasMoreTokens()) {
						//liTmp.add(shift2+","+norutKelasParalel2+","+currStatus2+","+npmdos2+","+npmasdos2+","+canceled2+","+kodeKelas2+","+kodeRuang2+","+kodeGedung2+","+kodeKampus2+","+tknDayTime2+","+nmmdos2+","+nmmasdos2+","+enrolled2+","+maxEnrolled2+","+minEnrolled2);
						
						String shift = st.nextToken();
						String norutKelasParalel = st.nextToken();
						String currStatus = st.nextToken();
						String npmdos = st.nextToken();
						String npmasdos = st.nextToken();
						String canceled = st.nextToken();
						String kodeKelas = st.nextToken();
						String kodeRuang = st.nextToken();
						String kodeGedung = st.nextToken();
						String kodeKampus = st.nextToken();
						String tknDayTime = st.nextToken();
						String nmmdos = st.nextToken();
						String nmmasdos = st.nextToken();
						String enrolled = st.nextToken();
						String maxEnrolled = st.nextToken();
						String minEnrolled = st.nextToken();
					
						stmt.setLong(1, Long.parseLong(idkmk));
						stmt.setString(2, thsms_target);
						stmt.setString(3,kdpst);
						stmt.setString(4, shift);
						stmt.setInt(5, Integer.parseInt(norutKelasParalel));
						rs = stmt.executeQuery();
						rs.next();//harus ada di class pool
						String subKeterKdkmk =""+rs.getString("SUB_KETER_KDKMK");
						String uniqueId = ""+rs.getLong("UNIQUE_ID");
						if(first) {
							first = false;
							tmp = tmp+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+uniqueId;
						}
						else {
							tmp = tmp+","+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+uniqueId;
						}
					}

					li.set(idkmk+","+kdkmk+","+nakmk+","+skstm+","+skspr+","+skslp+","+semes+","+tmp);
				}
				
				/*update 13Maret2015
				//add info idkkmk alternatif
				li = vBlmOrSdh.listIterator();
				stmt = con.prepareStatement("select * from MAKUL where IDKMKMAKUL=?");
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,",");
					String idkmk=st.nextToken();
					String kdkmk=st.nextToken();
					String nakmk=st.nextToken();
					String skstm=st.nextToken();
					String skspr=st.nextToken();
					String skslp=st.nextToken();
					String semes=st.nextToken();
					String tmp ="";
					boolean first = true;
					while(st.hasMoreTokens()) {
						String shift = st.nextToken();
						String norutKelasParalel = st.nextToken();
						String currStatus = st.nextToken();
						String npmdos = st.nextToken();
						String npmasdos = st.nextToken();
						String canceled = st.nextToken();
						String kodeKelas = st.nextToken();
						String kodeRuang = st.nextToken();
						String kodeGedung = st.nextToken();
						String kodeKampus = st.nextToken();
						String tknDayTime = st.nextToken();
						String nmmdos = st.nextToken();
						String nmmasdos = st.nextToken();
						String enrolled = st.nextToken();
						String maxEnrolled = st.nextToken();
						String minEnrolled = st.nextToken();
						String subKeterKdkmk = st.nextToken();
						stmt.setLong(1, Long.parseLong(idkmk));
						rs = stmt.executeQuery();
						rs.next();
						String idMakulSama = ""+rs.getString("ID_MAKUL_SAMA");
						idMakulSama = idMakulSama.replace(","," ");
						if(first) {
							first = false;
							tmp = tmp+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+idMakulSama;
						}
						else {
							tmp = tmp+","+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+idMakulSama;
						}
						li.set(idkmk+","+kdkmk+","+nakmk+","+skstm+","+skspr+","+skslp+","+semes+","+tmp);
						//if(idkmk.equalsIgnoreCase("205")) {
						//	//System.out.println(idkmk+","+kdkmk+","+nakmk+","+skstm+","+skspr+","+skslp+","+semes+","+tmp);
						//}
					}
					
				}
				//System.out.println("done");
				//end update 13Maret2015
				 * 
				 */
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
				if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
				if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
				if (con!=null) try { con.close();} catch (Exception ignore){}
			}	
    	}
    	
    	
    	return vBlmOrSdh;
    }
    
    
    public Vector adhockAddInfoMkToVblm_v1(Vector vBlmOrSdh, String thsms_target, String kdpst) {
    	/*
    	 * tambah detail makul
    	 * + alternativ kdkmk = matakuliah sama yg ada pada prodi fakltas atau kampus lainnya
    	 */
    	if(vBlmOrSdh!=null && vBlmOrSdh.size()>0) {
    		ListIterator li = vBlmOrSdh.listIterator();
    		
	    	
	    	try {
				Context initContext  = new InitialContext();
				Context envContext  = (Context)initContext.lookup("java:/comp/env");
				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
				con = ds.getConnection();
				//stmt = con.prepareStatement("select * from CLASS_POOL where IDKMK=? and THSMS=? and KDPST=? and SHIFT=? and NORUT_KELAS_PARALEL=?");
				stmt = con.prepareStatement("select * from CLASS_POOL where UNIQUE_ID=?");
				while(li.hasNext()) {
					String brs = (String)li.next();
					//System.out.println("barisx="+brs);
					StringTokenizer st=new StringTokenizer(brs,",");
					String idkmk=st.nextToken();
					String kdkmk=st.nextToken();
					String nakmk=st.nextToken();
					//nakmk = nakmk.replace("tandaKoma", ",");
					String skstm=st.nextToken();
					String skspr=st.nextToken();
					String skslp=st.nextToken();
					String semes=st.nextToken();
					String tmp = "";
					boolean first = true;
					while(st.hasMoreTokens()) {
						//liTmp.add(shift2+","+norutKelasParalel2+","+currStatus2+","+npmdos2+","+npmasdos2+","+canceled2+","+kodeKelas2+","+kodeRuang2+","+kodeGedung2+","+kodeKampus2+","+tknDayTime2+","+nmmdos2+","+nmmasdos2+","+enrolled2+","+maxEnrolled2+","+minEnrolled2);
						String idkur = st.nextToken();
						String shift = st.nextToken();
						String norutKelasParalel = st.nextToken();
						String currStatus = st.nextToken();
						String npmdos = st.nextToken();
						String npmasdos = st.nextToken();
						String canceled = st.nextToken();
						String kodeKelas = st.nextToken();
						String kodeRuang = st.nextToken();
						String kodeGedung = st.nextToken();
						String kodeKampus = st.nextToken();
						String tknDayTime = st.nextToken();
						String nmmdos = st.nextToken();
						String nmmasdos = st.nextToken();
						String enrolled = st.nextToken();
						String maxEnrolled = st.nextToken();
						String minEnrolled = st.nextToken();
						String kode_gab_cp = st.nextToken();
						String cuid_cp = st.nextToken();
						String idkmk_cp = st.nextToken();
					
						stmt.setLong(1, Long.parseLong(cuid_cp));
						//stmt.setString(2, thsms_target);
					//	stmt.setString(3,kdpst);
					//	stmt.setString(4, shift);
					//	stmt.setInt(5, Integer.parseInt(norutKelasParalel));
						rs = stmt.executeQuery();
						rs.next();//harus ada di class pool
						String subKeterKdkmk =""+rs.getString("SUB_KETER_KDKMK");
						//String uniqueId = ""+rs.getLong("UNIQUE_ID");
						if(first) {
							first = false;
							tmp = tmp+idkur+","+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+cuid_cp+","+idkmk_cp+","+subKeterKdkmk+","+kode_gab_cp;
						}
						else {
							tmp = tmp+","+idkur+","+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+cuid_cp+","+idkmk_cp+","+subKeterKdkmk+","+kode_gab_cp;
						}
					}

					li.set(idkmk+","+kdkmk+","+nakmk+","+skstm+","+skspr+","+skslp+","+semes+","+tmp);
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
				if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
				if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
				if (con!=null) try { con.close();} catch (Exception ignore){}
			}	
    	}
    	
    	
    	return vBlmOrSdh;
    }

    
    public Vector adhockAddInfoMkToVsdh_v1(Vector vBlmOrSdh, String thsms_target, String kdpst) {
    	/*
    	 * REMINDER!!!!
    	 * vBlm TIDAK SAMA STRUKTURNYA DENGAN vSdh
    	 */
    	if(vBlmOrSdh!=null && vBlmOrSdh.size()>0) {
    		ListIterator li = vBlmOrSdh.listIterator();
    		
	    	
	    	try {
				Context initContext  = new InitialContext();
				Context envContext  = (Context)initContext.lookup("java:/comp/env");
				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
				con = ds.getConnection();
				//stmt = con.prepareStatement("select * from CLASS_POOL where IDKMK=? and THSMS=? and KDPST=? and SHIFT=? and NORUT_KELAS_PARALEL=?");
				stmt = con.prepareStatement("select * from CLASS_POOL where UNIQUE_ID=?");
				while(li.hasNext()) {
					String brs = (String)li.next();
					//System.out.println("barisx="+brs);
					StringTokenizer st=new StringTokenizer(brs,",");
					String idkmk=st.nextToken();
					String thsms=st.nextToken();
					String kdkmk=st.nextToken();
					String nakmk=st.nextToken();
					//nakmk = nakmk.replace("tandaKoma", ",");
					String nlakh=st.nextToken();
					String bobot=st.nextToken();
					String cuid=st.nextToken();
					String cuid_init=st.nextToken();
					String tmp="";
					boolean first = true;
					while(st.hasMoreTokens()) {
						//liTmp.add(shift2+","+norutKelasParalel2+","+currStatus2+","+npmdos2+","+npmasdos2+","+canceled2+","+kodeKelas2+","+kodeRuang2+","+kodeGedung2+","+kodeKampus2+","+tknDayTime2+","+nmmdos2+","+nmmasdos2+","+enrolled2+","+maxEnrolled2+","+minEnrolled2);
						String idkur = st.nextToken();
						String shift = st.nextToken();
						String norutKelasParalel = st.nextToken();
						String currStatus = st.nextToken();
						String npmdos = st.nextToken();
						String npmasdos = st.nextToken();
						String canceled = st.nextToken();
						String kodeKelas = st.nextToken();
						String kodeRuang = st.nextToken();
						String kodeGedung = st.nextToken();
						String kodeKampus = st.nextToken();
						String tknDayTime = st.nextToken();
						String nmmdos = st.nextToken();
						String nmmasdos = st.nextToken();
						String enrolled = st.nextToken();
						String maxEnrolled = st.nextToken();
						String minEnrolled = st.nextToken();
						String kode_gab_cp = st.nextToken();
						String cuid_cp = st.nextToken();
						String idkmk_cp = st.nextToken();
					
						stmt.setLong(1, Long.parseLong(cuid_cp));
						/*
						stmt.setLong(1, Long.parseLong(idkmk));
						stmt.setString(2, thsms_target);
						stmt.setString(3,kdpst);
						stmt.setString(4, shift);
						stmt.setInt(5, Integer.parseInt(norutKelasParalel));
						*/
						rs = stmt.executeQuery();
						rs.next();//harus ada di class pool
						String subKeterKdkmk =""+rs.getString("SUB_KETER_KDKMK");
						//String uniqueId = ""+rs.getLong("UNIQUE_ID");
						
						
						if(first) {
							first = false;
							tmp = tmp+idkur+","+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+cuid_cp+","+idkmk_cp+","+subKeterKdkmk+","+kode_gab_cp;
						}
						else {
							tmp = tmp+","+idkur+","+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+cuid_cp+","+idkmk_cp+","+subKeterKdkmk+","+kode_gab_cp;
						}
						
					}

					li.set(idkmk+","+thsms+","+kdkmk+","+nakmk+","+nlakh+","+bobot+","+cuid+","+cuid_init+","+tmp);
				}
				
				
				/*update 13Maret2015
				//add info idkkmk alternatif
				li = vBlmOrSdh.listIterator();
				stmt = con.prepareStatement("select * from MAKUL where IDKMKMAKUL=?");
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,",");
					String idkmk=st.nextToken();
					String kdkmk=st.nextToken();
					String nakmk=st.nextToken();
					String skstm=st.nextToken();
					String skspr=st.nextToken();
					String skslp=st.nextToken();
					String semes=st.nextToken();
					String tmp ="";
					boolean first = true;
					while(st.hasMoreTokens()) {
						String shift = st.nextToken();
						String norutKelasParalel = st.nextToken();
						String currStatus = st.nextToken();
						String npmdos = st.nextToken();
						String npmasdos = st.nextToken();
						String canceled = st.nextToken();
						String kodeKelas = st.nextToken();
						String kodeRuang = st.nextToken();
						String kodeGedung = st.nextToken();
						String kodeKampus = st.nextToken();
						String tknDayTime = st.nextToken();
						String nmmdos = st.nextToken();
						String nmmasdos = st.nextToken();
						String enrolled = st.nextToken();
						String maxEnrolled = st.nextToken();
						String minEnrolled = st.nextToken();
						String subKeterKdkmk = st.nextToken();
						stmt.setLong(1, Long.parseLong(idkmk));
						rs = stmt.executeQuery();
						rs.next();
						String idMakulSama = ""+rs.getString("ID_MAKUL_SAMA");
						idMakulSama = idMakulSama.replace(","," ");
						if(first) {
							first = false;
							tmp = tmp+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+idMakulSama;
						}
						else {
							tmp = tmp+","+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+idMakulSama;
						}
						li.set(idkmk+","+kdkmk+","+nakmk+","+skstm+","+skspr+","+skslp+","+semes+","+tmp);
						//if(idkmk.equalsIgnoreCase("205")) {
						//	//System.out.println(idkmk+","+kdkmk+","+nakmk+","+skstm+","+skspr+","+skslp+","+semes+","+tmp);
						//}
					}
					
				}
				//System.out.println("done");
				//end update 13Maret2015
				*/
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
				if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
				if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
				if (con!=null) try { con.close();} catch (Exception ignore){}
			}	
    	}
    	
    	
    	return vBlmOrSdh;
    }    
    
    /*
     * PRUBAHAN DISINI HARUS DIRUBAH DI adhockAddInfoMkToVblm
     */
    public Vector adhockAddInfoMkToVsdh(Vector vBlmOrSdh, String thsms_target, String kdpst) {
    	if(vBlmOrSdh!=null && vBlmOrSdh.size()>0) {
    		ListIterator li = vBlmOrSdh.listIterator();
    		
	    	
	    	try {
				Context initContext  = new InitialContext();
				Context envContext  = (Context)initContext.lookup("java:/comp/env");
				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
				con = ds.getConnection();
				stmt = con.prepareStatement("select * from CLASS_POOL where IDKMK=? and THSMS=? and KDPST=? and SHIFT=? and NORUT_KELAS_PARALEL=?");
				while(li.hasNext()) {
					String brs = (String)li.next();
					//System.out.println("barisx="+brs);
					StringTokenizer st=new StringTokenizer(brs,",");
					String idkmk=st.nextToken();
					String thsms=st.nextToken();
					String kdkmk=st.nextToken();
					String nakmk=st.nextToken();
					//nakmk = nakmk.replace("tandaKoma", ",");
					String nlakh=st.nextToken();
					String bobot=st.nextToken();
					String tmp="";
					boolean first = true;
					while(st.hasMoreTokens()) {
						//liTmp.add(shift2+","+norutKelasParalel2+","+currStatus2+","+npmdos2+","+npmasdos2+","+canceled2+","+kodeKelas2+","+kodeRuang2+","+kodeGedung2+","+kodeKampus2+","+tknDayTime2+","+nmmdos2+","+nmmasdos2+","+enrolled2+","+maxEnrolled2+","+minEnrolled2);
						String shift = st.nextToken();
						String norutKelasParalel = st.nextToken();
						String currStatus = st.nextToken();
						String npmdos = st.nextToken();
						String npmasdos = st.nextToken();
						String canceled = st.nextToken();
						String kodeKelas = st.nextToken();
						String kodeRuang = st.nextToken();
						String kodeGedung = st.nextToken();
						String kodeKampus = st.nextToken();
						String tknDayTime = st.nextToken();
						String nmmdos = st.nextToken();
						String nmmasdos = st.nextToken();
						String enrolled = st.nextToken();
						String maxEnrolled = st.nextToken();
						String minEnrolled = st.nextToken();
						stmt.setLong(1, Long.parseLong(idkmk));
						stmt.setString(2, thsms_target);
						stmt.setString(3,kdpst);
						stmt.setString(4, shift);
						stmt.setInt(5, Integer.parseInt(norutKelasParalel));
						rs = stmt.executeQuery();
						rs.next();//harus ada di class pool
						String subKeterKdkmk =""+rs.getString("SUB_KETER_KDKMK");
						String uniqueId = ""+rs.getLong("UNIQUE_ID");
						
						
						if(first) {
							first = false;
							tmp = tmp+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+uniqueId;
						}
						else {
							tmp = tmp+","+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+uniqueId;
						}
						
					}

					li.set(idkmk+","+thsms+","+kdkmk+","+nakmk+","+nlakh+","+bobot+","+tmp);
				}
				
				
				/*update 13Maret2015
				//add info idkkmk alternatif
				li = vBlmOrSdh.listIterator();
				stmt = con.prepareStatement("select * from MAKUL where IDKMKMAKUL=?");
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,",");
					String idkmk=st.nextToken();
					String kdkmk=st.nextToken();
					String nakmk=st.nextToken();
					String skstm=st.nextToken();
					String skspr=st.nextToken();
					String skslp=st.nextToken();
					String semes=st.nextToken();
					String tmp ="";
					boolean first = true;
					while(st.hasMoreTokens()) {
						String shift = st.nextToken();
						String norutKelasParalel = st.nextToken();
						String currStatus = st.nextToken();
						String npmdos = st.nextToken();
						String npmasdos = st.nextToken();
						String canceled = st.nextToken();
						String kodeKelas = st.nextToken();
						String kodeRuang = st.nextToken();
						String kodeGedung = st.nextToken();
						String kodeKampus = st.nextToken();
						String tknDayTime = st.nextToken();
						String nmmdos = st.nextToken();
						String nmmasdos = st.nextToken();
						String enrolled = st.nextToken();
						String maxEnrolled = st.nextToken();
						String minEnrolled = st.nextToken();
						String subKeterKdkmk = st.nextToken();
						stmt.setLong(1, Long.parseLong(idkmk));
						rs = stmt.executeQuery();
						rs.next();
						String idMakulSama = ""+rs.getString("ID_MAKUL_SAMA");
						idMakulSama = idMakulSama.replace(","," ");
						if(first) {
							first = false;
							tmp = tmp+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+idMakulSama;
						}
						else {
							tmp = tmp+","+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+idMakulSama;
						}
						li.set(idkmk+","+kdkmk+","+nakmk+","+skstm+","+skspr+","+skslp+","+semes+","+tmp);
						//if(idkmk.equalsIgnoreCase("205")) {
						//	//System.out.println(idkmk+","+kdkmk+","+nakmk+","+skstm+","+skspr+","+skslp+","+semes+","+tmp);
						//}
					}
					
				}
				//System.out.println("done");
				//end update 13Maret2015
				*/
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
				if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
				if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
				if (con!=null) try { con.close();} catch (Exception ignore){}
			}	
    	}
    	
    	
    	return vBlmOrSdh;
    }

    
    /*
     * Ngga tau yg make siapa
     * kemungkinana sih DEPRECATED
     */
    public Vector getListKelasYgSedangDiajar(Vector vScopeKdpst,String scopeHakAkses, String scopeKmp, String npmUsr) {
    	/*
    	 * get list kelas yang harus diajarkan dan harus dinilai
    	 * jadi berdasarkan THSMS_INP_NILAI_MK columnn pada CALENDER tabel
    	 * return v length 0 bila kosong
    	 */
    	
    	/*
		 * untuk scope KDPST, bila own artinya dia dosen jadi hanya kelas yang diajar
		 * untuk ktu kdpst, dst
		 */
    	Vector v = new Vector();
    	ListIterator li = null;
    	//hanya untuk pembuatan krn admin ngga ngajar jadi dirubah sama yg ngajar
    	//di comment after programs works
    	/*
    	if(npmUsr.equalsIgnoreCase("0000000000001")) {
    	 
    		npmUsr = "0001113200006";//nuzelmar
    	}
    	*/
    	
    	
    	try {
    		
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			String thsms_inp_nilai = null;
			stmt = con.prepareStatement("select  THSMS_INP_NILAI_MK from CALENDAR where AKTIF=true");
			rs = stmt.executeQuery();
			rs.next();
			thsms_inp_nilai = rs.getString(1);
			
			//cek apa usr adalah only dosen = kdpstscope = own
	    	if(vScopeKdpst!=null && vScopeKdpst.size()==1) {
	    		//System.out.println("masuk sinidosen="+npmUsr);
	    		li = vScopeKdpst.listIterator();
	    		String scope = (String)li.next(); 
	    		if(scope.contains("own")) {
	    			//yes dosen 
	    			
	    			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and CANCELED=? and NPMDOS=?");
	    			stmt.setString(1, thsms_inp_nilai);
	    			stmt.setBoolean(2,false);
	    			stmt.setString(3, npmUsr);
	    		}
	    	}
	    	else {
	    		//berarti non dosen - 
	    		//scope  kdpst
	    		//System.out.println("NONdosen");
	    		li = vScopeKdpst.listIterator();
	    		String sql = "(";
	    		while(li.hasNext()) {
	    			String kdpst = (String)li.next();
	    			sql = sql+"KDPST='"+kdpst+"'";
	    			if(li.hasNext()) {
	    				sql = sql+" OR ";
	    			}
	    		}
	    		sql = sql+")";
	    		//System.out.println("sql1 = "+sql);
	    		//scope kampus
	    		//System.out.println("scopeKmp="+scopeKmp);
	    		StringTokenizer st = new StringTokenizer(scopeKmp,",");
	    		String sql1 = "(";
	    		while(st.hasMoreTokens()) {
	    			String kmp = st.nextToken();
	    			sql1 = sql1+"KODE_KAMPUS='"+kmp+"'";
	    			if(st.hasMoreTokens()) {
	    				sql1 = sql1+" OR ";
	    			}
	    		}
	    		sql1 = sql1+")";
	    		//System.out.println("sql2 = "+sql1);
	    		//System.out.println("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and CANCELED=? and "+sql+" AND "+sql1);
	    		stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and CANCELED=? and "+sql+" AND "+sql1);
	    		stmt.setString(1, thsms_inp_nilai);
    			stmt.setBoolean(2,false);
	    	}
			li = v.listIterator();
			rs = stmt.executeQuery();
			/*
			 * get all kelas dibuka at thsms
			 */
			while(rs.next()) {
				String idkur = ""+rs.getLong("IDKUR");
				String idkmk = ""+rs.getLong("IDKMK");
				//thsms ignoeee
				String kdpst = ""+rs.getString("KDPST");
				String shift = ""+rs.getString("SHIFT");
				String noKlsPll = ""+rs.getInt("NORUT_KELAS_PARALEL");
				String npm_pertama_input = ""+rs.getString("INIT_NPM_INPUT");
				String npm_terakhir_updat = ""+rs.getString("LATEST_NPM_UPDATE");
				String status_akhir = ""+rs.getString("LATEST_STATUS_INFO");
				String curr_avail_status = ""+rs.getString("CURR_AVAIL_STATUS");
				String locked_or_editable = ""+rs.getBoolean("LOCKED");
				String npmdos = ""+rs.getString("NPMDOS");
				String nodos = ""+rs.getString("NODOS");
				String npmasdos = ""+rs.getString("NPMASDOS");
				String noasdos = ""+rs.getString("NOASDOS");
				String canceled = ""+rs.getBoolean("CANCELED");
				String kode_kelas = ""+rs.getString("KODE_KELAS");
				String kode_ruang = ""+rs.getString("KODE_RUANG");
				String kode_gedung = ""+rs.getString("KODE_GEDUNG");
				String kode_kampus = ""+rs.getString("KODE_KAMPUS");
				String tkn_day_time = ""+rs.getString("TKN_HARI_TIME");
				String nmmdos = ""+rs.getString("NMMDOS");
				String nmmasdos = ""+rs.getString("NMMASDOS");
				String enrolled = ""+rs.getInt("ENROLLED");//jum mhs
				String max_enrolled = ""+rs.getInt("MAX_ENROLLED");//jum mhs
				String min_enrolled = ""+rs.getInt("MIN_ENROLLED");//jum mhs
				String sub_keter_kdkmk = ""+rs.getString("SUB_KETER_KDKMK");
				String init_req_time = ""+rs.getTimestamp("INIT_REQ_TIME");
				String tkn_npm_approval = ""+rs.getString("TKN_NPM_APPROVAL");
				String tkn_approval_time = ""+rs.getString("TKN_APPROVAL_TIME");
				String target_ttmhs = ""+rs.getInt("TARGET_TTMHS");
				String passed = ""+rs.getBoolean("PASSED");
				String rejected = ""+rs.getBoolean("REJECTED");
				String kode_gabung_kls = ""+rs.getString("KODE_PENGGABUNGAN");
				String kode_gabung_kmp = ""+rs.getString("KODE_GABUNGAN_UNIV");
				String unique_id = ""+rs.getLong("UNIQUE_ID");
				String kdkmk = ""+rs.getString("KDKMKMAKUL");
				String nakmk = ""+rs.getString("NAKMKMAKUL");
				String skstm = ""+rs.getInt("SKSTMMAKUL");
				String skspr = ""+rs.getInt("SKSPRMAKUL");
				String skslp = ""+rs.getInt("SKSLPMAKUL");
				li.add(idkur+"`"+idkmk+"`"+thsms_inp_nilai+"`"+kdpst+"`"+shift+"`"+noKlsPll+"`"+npm_pertama_input+"`"+npm_terakhir_updat+"`"+status_akhir+"`"+curr_avail_status+"`"+locked_or_editable+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+canceled+"`"+kode_kelas+"`"+kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
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
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return v;
    }

    
    public Vector getListKelasYgSedangDiajarAtThsmsNow() {
    	String thsmsNow = Checker.getThsmsNow();
    	Vector v = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS=? and CANCELED=? order by KODE_KAMPUS,IDKMK,SHIFT");
			//stmt = con.prepareStatement("select  * from CLASS_POOL where THSMS=? and NPMDOS=? and CANCELED=? order by KODE_KAMPUS,IDKMK,SHIFT");
			stmt.setString(1, thsmsNow);
			stmt.setString(2, this.operatorNpm);
			stmt.setBoolean(3, false);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				ListIterator li = v.listIterator();
				do {
					String idkur = ""+rs.getLong("IDKUR");
					String idkmk = ""+rs.getLong("IDKMK");
					//thsms ignoeee
					String kdpst = ""+rs.getString("KDPST");
					String shift = ""+rs.getString("SHIFT");
					String noKlsPll = ""+rs.getInt("NORUT_KELAS_PARALEL");
					String npm_pertama_input = ""+rs.getString("INIT_NPM_INPUT");
					String npm_terakhir_updat = ""+rs.getString("LATEST_NPM_UPDATE");
					String status_akhir = ""+rs.getString("LATEST_STATUS_INFO");
					String curr_avail_status = ""+rs.getString("CURR_AVAIL_STATUS");
					String locked_or_editable = ""+rs.getBoolean("LOCKED");
					String npmdos = ""+rs.getString("NPMDOS");
					String nodos = ""+rs.getString("NODOS");
					String npmasdos = ""+rs.getString("NPMASDOS");
					String noasdos = ""+rs.getString("NOASDOS");
					String canceled = ""+rs.getBoolean("CANCELED");
					String kode_kelas = ""+rs.getString("KODE_KELAS");
					String kode_ruang = ""+rs.getString("KODE_RUANG");
					String kode_gedung = ""+rs.getString("KODE_GEDUNG");
					String kode_kampus = ""+rs.getString("KODE_KAMPUS");
					String tkn_day_time = ""+rs.getString("TKN_HARI_TIME");
					String nmmdos = ""+rs.getString("NMMDOS");
					String nmmasdos = ""+rs.getString("NMMASDOS");
					String enrolled = ""+rs.getInt("ENROLLED");//jum mhs
					String max_enrolled = ""+rs.getInt("MAX_ENROLLED");//jum mhs
					String min_enrolled = ""+rs.getInt("MIN_ENROLLED");//jum mhs
					String sub_keter_kdkmk = ""+rs.getString("SUB_KETER_KDKMK");
					String init_req_time = ""+rs.getTimestamp("INIT_REQ_TIME");
					String tkn_npm_approval = ""+rs.getString("TKN_NPM_APPROVAL");
					String tkn_approval_time = ""+rs.getString("TKN_APPROVAL_TIME");
					String target_ttmhs = ""+rs.getInt("TARGET_TTMHS");
					String passed = ""+rs.getBoolean("PASSED");
					String rejected = ""+rs.getBoolean("REJECTED");
					String kode_gabung_kls = ""+rs.getString("KODE_PENGGABUNGAN");
					String kode_gabung_kmp = ""+rs.getString("KODE_GABUNGAN_UNIV");
					String unique_id = ""+rs.getLong("UNIQUE_ID");
					String kdkmk = ""+rs.getString("KDKMKMAKUL");
					String nakmk = ""+rs.getString("NAKMKMAKUL");
					String skstm = ""+rs.getInt("SKSTMMAKUL");
					String skspr = ""+rs.getInt("SKSPRMAKUL");
					String skslp = ""+rs.getInt("SKSLPMAKUL");
					li.add(idkur+"`"+idkmk+"`"+thsmsNow+"`"+kdpst+"`"+shift+"`"+noKlsPll+"`"+npm_pertama_input+"`"+npm_terakhir_updat+"`"+status_akhir+"`"+curr_avail_status+"`"+locked_or_editable+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+canceled+"`"+kode_kelas+"`"+kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
				}
				while(rs.next());
				//System.out.println("jum tot kelas ajar = "+v.size());
			}
			//add konsentrasi
			if(v!=null && v.size()>0) {
				stmt = con.prepareStatement("select KONSENTRASI from KRKLM where IDKURKRKLM=?");
				ListIterator li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					//
					String idkur = st.nextToken();
					stmt.setInt(1, Integer.parseInt(idkur));
					rs = stmt.executeQuery();
					rs.next();
					String konsen = ""+rs.getString("KONSENTRASI");
					li.set(brs+"`"+konsen);
					//String idkmk+"`"+thsmsNow+"`"+kdpst+"`"+shift+"`"+noKlsPll+"`"+npm_pertama_input+"`"+npm_terakhir_updat+"`"+status_akhir+"`"+curr_avail_status+"`"+locked_or_editable+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+canceled+"`"+kode_kelas+"`"+kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
				}
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
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return v;
    }
    /*
    public Vector getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya(Vector vScopeKdpst) {
    	String thsms_now = Checker.getThsmsNow();
    	Vector vListKls = new Vector();
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//1. cek kelas yg diajar sendiri
			//stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS=? and CANCELED=? order by KODE_KAMPUS,KDPST,IDKMK,SHIFT");
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS=? and CANCELED=?");
			stmt.setString(1, thsms_now);
			stmt.setString(2, this.operatorNpm);
			stmt.setBoolean(3, false);
			rs = stmt.executeQuery();
			ListIterator lis = vListKls.listIterator();
			
			while(rs.next()) {
				String nakmk = ""+rs.getString("NAKMKMAKUL");
				String idkur  = ""+rs.getLong("IDKUR");
				String idkmk = ""+rs.getLong("IDKMK");				
				String thsms = ""+rs.getString("THSMS");
				String kdpst = ""+rs.getString("KDPST");
				String shift = ""+rs.getString("SHIFT");
				String nopll = ""+rs.getInt("NORUT_KELAS_PARALEL");
				String initNpmInp = ""+rs.getString("INIT_NPM_INPUT");
				String lasNpmUpd = ""+rs.getString("LATEST_NPM_UPDATE");
				String lasStatInf = ""+rs.getString("LATEST_STATUS_INFO");
				String curAvailStat = ""+rs.getString("CURR_AVAIL_STATUS");
				String locked = ""+rs.getBoolean("LOCKED");
				String npmdos = ""+rs.getString("NPMDOS");
				String nodos = ""+rs.getString("NODOS");
				String npmasdos = ""+rs.getString("NPMASDOS");
				String noasdos = ""+rs.getString("NOASDOS");
				String batal = ""+rs.getBoolean("CANCELED");
				String kodeKls = ""+rs.getString("KODE_KELAS");
				String kodeRuang = ""+rs.getString("KODE_RUANG");
				String kodeGdg = ""+rs.getString("KODE_GEDUNG");
				String kodeKmp = ""+rs.getString("KODE_KAMPUS");
				String tknDayTime = ""+rs.getString("TKN_HARI_TIME");
				String nmmDos = ""+rs.getString("NMMDOS");			
				String nmmAsdos = ""+rs.getString("NMMASDOS");
				String totEnrol = ""+rs.getInt("ENROLLED");
				String maxEnrol = ""+rs.getInt("MAX_ENROLLED");
				String minEnrol = ""+rs.getInt("MIN_ENROLLED");
				String subKeterKdkmk = ""+rs.getString("SUB_KETER_KDKMK");
				String initReqTime = ""+rs.getTimestamp("INIT_REQ_TIME");
				String tknNpmApr = ""+rs.getString("TKN_NPM_APPROVAL");
				String tknAprTime = ""+rs.getString("TKN_APPROVAL_TIME");
				String targetTtmhs = ""+rs.getInt("TARGET_TTMHS");
				String passed = ""+rs.getBoolean("PASSED");
				String rejected = ""+rs.getBoolean("REJECTED");
				String kodeGabung = ""+rs.getString("KODE_PENGGABUNGAN");
				String kodeGabungUniv = ""+rs.getString("KODE_GABUNGAN_UNIV");
				String cuid = ""+rs.getLong("UNIQUE_ID");
				String kdkmk = ""+rs.getString("KDKMKMAKUL");
				lis.add(kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid+"`"+kdkmk);

			}
			//2. get kelas yg tidak diajar tapi boleh ikutan update
			/*
			 * kalo own skip process ini;
			 */
    /*
			if(vScopeKdpst!=null && vScopeKdpst.size()>0) {
				//String sql_cmd = "select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS<>? and CANCELED=? and (";
				String sql_cmd = "select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS<>? and CANCELED=?";
				ListIterator li = vScopeKdpst.listIterator();
				//System.out.println("sql_cmd = "+sql_cmd);
				boolean own_only = false;
				if(li.hasNext() && !own_only) {
					//64 00007 TU_EKONOMI 64 0
					sql_cmd = sql_cmd+" and (";
					do {
						String brs = (String)li.next();
						//System.out.println("brs__="+brs);
						if(brs.contains("own") || brs.contains("OWN")) {
							own_only = true;
						}
						else {
							String kdpst = Tool.getTokenKe(brs, 2);
							//System.out.println("sckpe kdpst = "+kdpst);
							sql_cmd = sql_cmd + "KDPST='"+kdpst+"'";
							if(li.hasNext()) {
								sql_cmd = sql_cmd + " OR ";
							}
						}
					}
					while(li.hasNext() && !own_only);
					sql_cmd = sql_cmd + ")";
				}
				/*
				while(li.hasNext()) {
					String kdpst = (String)li.next();
					//64 00007 TU_EKONOMI 64 0
					
				}
				*/
				
				//System.out.println("sql_cmd = "+sql_cmd);
				//sql_cmd =sql_cmd.replace("and ()", "");//kalo own
				//System.out.println("sql_cmd after= "+sql_cmd);
    /*
				if(!own_only) {
					stmt = con.prepareStatement(sql_cmd);
					stmt.setString(1, thsms_now);
					stmt.setString(2, this.operatorNpm);
					stmt.setBoolean(3, false);
					rs = stmt.executeQuery();
					while(rs.next()) {
						//System.out.println("rs.next()");
						String nakmk = ""+rs.getString("NAKMKMAKUL");
						String idkur  = ""+rs.getLong("IDKUR");
						String idkmk = ""+rs.getLong("IDKMK");				
						String thsms = ""+rs.getString("THSMS");
						String kdpst = ""+rs.getString("KDPST");
						String shift = ""+rs.getString("SHIFT");
						String nopll = ""+rs.getInt("NORUT_KELAS_PARALEL");
						String initNpmInp = ""+rs.getString("INIT_NPM_INPUT");
						String lasNpmUpd = ""+rs.getString("LATEST_NPM_UPDATE");
						String lasStatInf = ""+rs.getString("LATEST_STATUS_INFO");
						String curAvailStat = ""+rs.getString("CURR_AVAIL_STATUS");
						String locked = ""+rs.getBoolean("LOCKED");
						String npmdos = ""+rs.getString("NPMDOS");
						String nodos = ""+rs.getString("NODOS");
						String npmasdos = ""+rs.getString("NPMASDOS");
						String noasdos = ""+rs.getString("NOASDOS");
						String batal = ""+rs.getBoolean("CANCELED");
						String kodeKls = ""+rs.getString("KODE_KELAS");
						String kodeRuang = ""+rs.getString("KODE_RUANG");
						String kodeGdg = ""+rs.getString("KODE_GEDUNG");
						String kodeKmp = ""+rs.getString("KODE_KAMPUS");
						String tknDayTime = ""+rs.getString("TKN_HARI_TIME");
						String nmmDos = ""+rs.getString("NMMDOS");			
						String nmmAsdos = ""+rs.getString("NMMASDOS");
						String totEnrol = ""+rs.getInt("ENROLLED");
						String maxEnrol = ""+rs.getInt("MAX_ENROLLED");
						String minEnrol = ""+rs.getInt("MIN_ENROLLED");
						String subKeterKdkmk = ""+rs.getString("SUB_KETER_KDKMK");
						String initReqTime = ""+rs.getTimestamp("INIT_REQ_TIME");
						String tknNpmApr = ""+rs.getString("TKN_NPM_APPROVAL");
						String tknAprTime = ""+rs.getString("TKN_APPROVAL_TIME");
						String targetTtmhs = ""+rs.getInt("TARGET_TTMHS");
						String passed = ""+rs.getBoolean("PASSED");
						String rejected = ""+rs.getBoolean("REJECTED");
						String kodeGabung = ""+rs.getString("KODE_PENGGABUNGAN");
						String kodeGabungUniv = ""+rs.getString("KODE_GABUNGAN_UNIV");
						String cuid = ""+rs.getLong("UNIQUE_ID");
						String kdkmk = ""+rs.getString("KDKMKMAKUL");	
						lis.add(kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid+"`"+kdkmk);

					}
				}
				
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
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	Collections.sort(vListKls);
    	return vListKls;
    }
    */
    
    public Vector getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya(Vector vScopeKdpst, String target_thsms) {
    	//String thsms_now = Checker.getThsmsNow();
    	Vector vListKls = new Vector();
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//1. cek kelas yg diajar sendiri
			//stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS=? and CANCELED=? order by KODE_KAMPUS,KDPST,IDKMK,SHIFT");
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS=? and CANCELED=?");
			stmt.setString(1, target_thsms);
			stmt.setString(2, this.operatorNpm);
			stmt.setBoolean(3, false);
			rs = stmt.executeQuery();
			ListIterator lis = vListKls.listIterator();
			
			while(rs.next()) {
				String nakmk = ""+rs.getString("NAKMKMAKUL");
				String idkur  = ""+rs.getLong("IDKUR");
				String idkmk = ""+rs.getLong("IDKMK");				
				String thsms = ""+rs.getString("THSMS");
				String kdpst = ""+rs.getString("KDPST");
				String shift = ""+rs.getString("SHIFT");
				String nopll = ""+rs.getInt("NORUT_KELAS_PARALEL");
				String initNpmInp = ""+rs.getString("INIT_NPM_INPUT");
				String lasNpmUpd = ""+rs.getString("LATEST_NPM_UPDATE");
				String lasStatInf = ""+rs.getString("LATEST_STATUS_INFO");
				String curAvailStat = ""+rs.getString("CURR_AVAIL_STATUS");
				String locked = ""+rs.getBoolean("LOCKED");
				String npmdos = ""+rs.getString("NPMDOS");
				String nodos = ""+rs.getString("NODOS");
				String npmasdos = ""+rs.getString("NPMASDOS");
				String noasdos = ""+rs.getString("NOASDOS");
				String batal = ""+rs.getBoolean("CANCELED");
				String kodeKls = ""+rs.getString("KODE_KELAS");
				String kodeRuang = ""+rs.getString("KODE_RUANG");
				String kodeGdg = ""+rs.getString("KODE_GEDUNG");
				String kodeKmp = ""+rs.getString("KODE_KAMPUS");
				String tknDayTime = ""+rs.getString("TKN_HARI_TIME");
				String nmmDos = ""+rs.getString("NMMDOS");			
				String nmmAsdos = ""+rs.getString("NMMASDOS");
				String totEnrol = ""+rs.getInt("ENROLLED");
				String maxEnrol = ""+rs.getInt("MAX_ENROLLED");
				String minEnrol = ""+rs.getInt("MIN_ENROLLED");
				String subKeterKdkmk = ""+rs.getString("SUB_KETER_KDKMK");
				String initReqTime = ""+rs.getTimestamp("INIT_REQ_TIME");
				String tknNpmApr = ""+rs.getString("TKN_NPM_APPROVAL");
				String tknAprTime = ""+rs.getString("TKN_APPROVAL_TIME");
				String targetTtmhs = ""+rs.getInt("TARGET_TTMHS");
				String passed = ""+rs.getBoolean("PASSED");
				String rejected = ""+rs.getBoolean("REJECTED");
				String kodeGabung = ""+rs.getString("KODE_PENGGABUNGAN");
				String kodeGabungUniv = ""+rs.getString("KODE_GABUNGAN_UNIV");
				String cuid = ""+rs.getLong("UNIQUE_ID");
				String kdkmk = ""+rs.getString("KDKMKMAKUL");
				lis.add(kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid+"`"+kdkmk);

			}
			//2. get kelas yg tidak diajar tapi boleh ikutan update
			/*
			 * kalo own skip process ini;
			 */
			if(vScopeKdpst!=null && vScopeKdpst.size()>0) {
				//String sql_cmd = "select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS<>? and CANCELED=? and (";
				String sql_cmd = "select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS<>? and CANCELED=?";
				ListIterator li = vScopeKdpst.listIterator();
				//System.out.println("sql_cmd = "+sql_cmd);
				boolean own_only = false;
				if(li.hasNext() && !own_only) {
					//64 00007 TU_EKONOMI 64 0
					sql_cmd = sql_cmd+" and (";
					do {
						String brs = (String)li.next();
						//System.out.println("brs__="+brs);
						if(brs.contains("own") || brs.contains("OWN")) {
							own_only = true;
						}
						else {
							String kdpst = Tool.getTokenKe(brs, 2);
							//System.out.println("sckpe kdpst = "+kdpst);
							sql_cmd = sql_cmd + "KDPST='"+kdpst+"'";
							if(li.hasNext()) {
								sql_cmd = sql_cmd + " OR ";
							}
						}
					}
					while(li.hasNext() && !own_only);
					sql_cmd = sql_cmd + ")";
				}
				/*
				while(li.hasNext()) {
					String kdpst = (String)li.next();
					//64 00007 TU_EKONOMI 64 0
					
				}
				*/
				
				//System.out.println("sql_cmd = "+sql_cmd);
				//sql_cmd =sql_cmd.replace("and ()", "");//kalo own
				//System.out.println("sql_cmd after= "+sql_cmd);
				if(!own_only) {
					stmt = con.prepareStatement(sql_cmd);
					stmt.setString(1, target_thsms);
					stmt.setString(2, this.operatorNpm);
					stmt.setBoolean(3, false);
					rs = stmt.executeQuery();
					while(rs.next()) {
						//System.out.println("rs.next()");
						String nakmk = ""+rs.getString("NAKMKMAKUL");
						String idkur  = ""+rs.getLong("IDKUR");
						String idkmk = ""+rs.getLong("IDKMK");				
						String thsms = ""+rs.getString("THSMS");
						String kdpst = ""+rs.getString("KDPST");
						String shift = ""+rs.getString("SHIFT");
						String nopll = ""+rs.getInt("NORUT_KELAS_PARALEL");
						String initNpmInp = ""+rs.getString("INIT_NPM_INPUT");
						String lasNpmUpd = ""+rs.getString("LATEST_NPM_UPDATE");
						String lasStatInf = ""+rs.getString("LATEST_STATUS_INFO");
						String curAvailStat = ""+rs.getString("CURR_AVAIL_STATUS");
						String locked = ""+rs.getBoolean("LOCKED");
						String npmdos = ""+rs.getString("NPMDOS");
						String nodos = ""+rs.getString("NODOS");
						String npmasdos = ""+rs.getString("NPMASDOS");
						String noasdos = ""+rs.getString("NOASDOS");
						String batal = ""+rs.getBoolean("CANCELED");
						String kodeKls = ""+rs.getString("KODE_KELAS");
						String kodeRuang = ""+rs.getString("KODE_RUANG");
						String kodeGdg = ""+rs.getString("KODE_GEDUNG");
						String kodeKmp = ""+rs.getString("KODE_KAMPUS");
						String tknDayTime = ""+rs.getString("TKN_HARI_TIME");
						String nmmDos = ""+rs.getString("NMMDOS");			
						String nmmAsdos = ""+rs.getString("NMMASDOS");
						String totEnrol = ""+rs.getInt("ENROLLED");
						String maxEnrol = ""+rs.getInt("MAX_ENROLLED");
						String minEnrol = ""+rs.getInt("MIN_ENROLLED");
						String subKeterKdkmk = ""+rs.getString("SUB_KETER_KDKMK");
						String initReqTime = ""+rs.getTimestamp("INIT_REQ_TIME");
						String tknNpmApr = ""+rs.getString("TKN_NPM_APPROVAL");
						String tknAprTime = ""+rs.getString("TKN_APPROVAL_TIME");
						String targetTtmhs = ""+rs.getInt("TARGET_TTMHS");
						String passed = ""+rs.getBoolean("PASSED");
						String rejected = ""+rs.getBoolean("REJECTED");
						String kodeGabung = ""+rs.getString("KODE_PENGGABUNGAN");
						String kodeGabungUniv = ""+rs.getString("KODE_GABUNGAN_UNIV");
						String cuid = ""+rs.getLong("UNIQUE_ID");
						String kdkmk = ""+rs.getString("KDKMKMAKUL");	
						lis.add(kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid+"`"+kdkmk);

					}
				}
				
			}
			//filter hanya yg ada mhsnya aja
			if(vListKls!=null && vListKls.size()>0) {
				lis = vListKls.listIterator();
				stmt = con.prepareStatement("select CLASS_POOL_UNIQUE_ID from TRNLM where CLASS_POOL_UNIQUE_ID=?");
				while(lis.hasNext()) {
					String brs = (String)lis.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					//lis.add(kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid+"`"+kdkmk);
					String kdpst = st.nextToken();
					String nakmk = st.nextToken();
					String idkur = st.nextToken();
					String idkmk = st.nextToken();
					String shift = st.nextToken();
					String thsms = st.nextToken();
					String nopll = st.nextToken();
					String initNpmInp = st.nextToken();
					String lasNpmUpd = st.nextToken();
					String lasStatInf = st.nextToken();
					String curAvailStat = st.nextToken();
					String locked = st.nextToken();
					String npmdos = st.nextToken();
					String nodos = st.nextToken();
					String npmasdos = st.nextToken();
					String noasdos = st.nextToken();
					String batal = st.nextToken();
					String kodeKls = st.nextToken();
					String kodeRuang = st.nextToken();
					String kodeGdg = st.nextToken();
					String kodeKmp = st.nextToken();
					String tknDayTime = st.nextToken();
					String nmmDos = st.nextToken();
					String nmmAsdos = st.nextToken();
					String totEnrol = st.nextToken();
					String maxEnrol = st.nextToken();
					String minEnrol = st.nextToken();
					String subKeterKdkmk = st.nextToken();
					String initReqTime = st.nextToken();
					String tknNpmApr = st.nextToken();
					String tknAprTime = st.nextToken();
					String targetTtmhs = st.nextToken();
					String passed = st.nextToken();
					String rejected = st.nextToken();
					String kodeGabung = st.nextToken();
					String kodeGabungUniv = st.nextToken();
					String cuid = st.nextToken();
					String kdkmk = st.nextToken();
					stmt.setLong(1, Long.parseLong(cuid));
					rs = stmt.executeQuery();
					if(!rs.next()) {
						lis.remove();
					}
				}
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
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	Collections.sort(vListKls);
    	return vListKls;
    }
    
    public Vector getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya(Vector vScopeKdpst, String target_thsms, String target_kdpst, String target_shift) {
    	//String thsms_now = Checker.getThsmsNow();
    	//System.out.println("target_thsms="+target_thsms);
    	Vector vListKls = new Vector();
    	boolean ada_akses = false;
    	boolean own_only = false;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//1. cek kelas yg diajar sendiri
			//stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS=? and CANCELED=? order by KODE_KAMPUS,KDPST,IDKMK,SHIFT");
			if(Checker.isStringNullOrEmpty(target_kdpst)) {
				//HANYA MENGAJAR & BUKAN SKEDUL EDITOR "SAD"
				stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS=? and CANCELED=?");
			}
			else if(target_kdpst.equalsIgnoreCase("all")) {
				if(target_shift.equalsIgnoreCase("all")) {
					stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS=? and CANCELED=?");
				}
				else {
					stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and SHIFT='"+target_shift+"' and NPMDOS=? and CANCELED=?");
				}
			}
			else {
				if(target_shift.equalsIgnoreCase("all")) {
					stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KDPST='"+target_kdpst+"' and NPMDOS=? and CANCELED=?");
				}
				else {
					stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KDPST='"+target_kdpst+"' and SHIFT='"+target_shift+"' and NPMDOS=? and CANCELED=?");
				}
			}
			//stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS=? and CANCELED=?");
			stmt.setString(1, target_thsms);
			stmt.setString(2, this.operatorNpm);
			stmt.setBoolean(3, false);
			rs = stmt.executeQuery();
			ListIterator lis = vListKls.listIterator();
			
			while(rs.next()) {
				String nakmk = ""+rs.getString("NAKMKMAKUL");
				String idkur  = ""+rs.getLong("IDKUR");
				String idkmk = ""+rs.getLong("IDKMK");				
				String thsms = ""+rs.getString("THSMS");
				String kdpst = ""+rs.getString("KDPST");
				String shift = ""+rs.getString("SHIFT");
				String nopll = ""+rs.getInt("NORUT_KELAS_PARALEL");
				String initNpmInp = ""+rs.getString("INIT_NPM_INPUT");
				String lasNpmUpd = ""+rs.getString("LATEST_NPM_UPDATE");
				String lasStatInf = ""+rs.getString("LATEST_STATUS_INFO");
				String curAvailStat = ""+rs.getString("CURR_AVAIL_STATUS");
				String locked = ""+rs.getBoolean("LOCKED");
				String npmdos = ""+rs.getString("NPMDOS");
				String nodos = ""+rs.getString("NODOS");
				String npmasdos = ""+rs.getString("NPMASDOS");
				String noasdos = ""+rs.getString("NOASDOS");
				String batal = ""+rs.getBoolean("CANCELED");
				String kodeKls = ""+rs.getString("KODE_KELAS");
				String kodeRuang = ""+rs.getString("KODE_RUANG");
				String kodeGdg = ""+rs.getString("KODE_GEDUNG");
				String kodeKmp = ""+rs.getString("KODE_KAMPUS");
				String tknDayTime = ""+rs.getString("TKN_HARI_TIME");
				String nmmDos = ""+rs.getString("NMMDOS");			
				String nmmAsdos = ""+rs.getString("NMMASDOS");
				String totEnrol = ""+rs.getInt("ENROLLED");
				String maxEnrol = ""+rs.getInt("MAX_ENROLLED");
				String minEnrol = ""+rs.getInt("MIN_ENROLLED");
				String subKeterKdkmk = ""+rs.getString("SUB_KETER_KDKMK");
				String initReqTime = ""+rs.getTimestamp("INIT_REQ_TIME");
				String tknNpmApr = ""+rs.getString("TKN_NPM_APPROVAL");
				String tknAprTime = ""+rs.getString("TKN_APPROVAL_TIME");
				String targetTtmhs = ""+rs.getInt("TARGET_TTMHS");
				String passed = ""+rs.getBoolean("PASSED");
				String rejected = ""+rs.getBoolean("REJECTED");
				String kodeGabung = ""+rs.getString("KODE_PENGGABUNGAN");
				String kodeGabungUniv = ""+rs.getString("KODE_GABUNGAN_UNIV");
				String cuid = ""+rs.getLong("UNIQUE_ID");
				String kdkmk = ""+rs.getString("KDKMKMAKUL");
				lis.add(kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid+"`"+kdkmk);
			}
			//2. cek untukk kelas yg diajar orang laen
			if(vScopeKdpst!=null && vScopeKdpst.size()>0) {
				ListIterator li = vScopeKdpst.listIterator();
				String scope_cmd = "";
				if(li.hasNext()) {	
					do {
						String brs = (String)li.next();
						//System.out.println("brs__="+brs);
						if(brs.contains("own") || brs.contains("OWN")) {
							own_only = true;
						}
						else {
							
							String kdpst = Tool.getTokenKe(brs, 2);
							//System.out.println("sckpe kdpst = "+kdpst);
							scope_cmd = scope_cmd + "KDPST='"+kdpst+"'";
							if(li.hasNext()) {
								scope_cmd = scope_cmd + " OR ";
							}
						}
					}
					while(li.hasNext() && !own_only);
				}
				//System.out.println("own_only="+own_only);
				//jika own_only  ato ngga ada skope skip proses ini
				if(!own_only && !Checker.isStringNullOrEmpty(scope_cmd)) {
					//String sql_cmd = "select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS<>? and CANCELED=? and (";
					//String sql_cmd = "select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS<>? and CANCELED=?";
					if(target_kdpst.equalsIgnoreCase("all")) {
						if(target_shift.equalsIgnoreCase("all")) {
							stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and ("+scope_cmd+") and NPMDOS<>? and CANCELED=?");
							//System.out.println("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and ("+scope_cmd+") and NPMDOS<>? and CANCELED=?");
						}
						else {
							stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and ("+scope_cmd+") and SHIFT='"+target_shift+"' and NPMDOS<>? and CANCELED=?");
						}	
					}
					else {
						if(target_shift.equalsIgnoreCase("all")) {
							stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KDPST='"+target_kdpst+"' and NPMDOS<>? and CANCELED=?");
						}
						else {
							stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KDPST='"+target_kdpst+"' and SHIFT='"+target_shift+"' and NPMDOS<>? and CANCELED=?");
						}
					}
					
					//stmt = con.prepareStatement(sql_cmd);
					stmt.setString(1, target_thsms);
					stmt.setString(2, this.operatorNpm);
					stmt.setBoolean(3, false);
					rs = stmt.executeQuery();
					while(rs.next()) {
						//System.out.println("rs.next()");
						String nakmk = ""+rs.getString("NAKMKMAKUL");
						String idkur  = ""+rs.getLong("IDKUR");
						String idkmk = ""+rs.getLong("IDKMK");				
						String thsms = ""+rs.getString("THSMS");
						String kdpst = ""+rs.getString("KDPST");
						String shift = ""+rs.getString("SHIFT");
						String nopll = ""+rs.getInt("NORUT_KELAS_PARALEL");
						String initNpmInp = ""+rs.getString("INIT_NPM_INPUT");
						String lasNpmUpd = ""+rs.getString("LATEST_NPM_UPDATE");
						String lasStatInf = ""+rs.getString("LATEST_STATUS_INFO");
						String curAvailStat = ""+rs.getString("CURR_AVAIL_STATUS");
						String locked = ""+rs.getBoolean("LOCKED");
						String npmdos = ""+rs.getString("NPMDOS");
						String nodos = ""+rs.getString("NODOS");
						String npmasdos = ""+rs.getString("NPMASDOS");
						String noasdos = ""+rs.getString("NOASDOS");
						String batal = ""+rs.getBoolean("CANCELED");
						String kodeKls = ""+rs.getString("KODE_KELAS");
						String kodeRuang = ""+rs.getString("KODE_RUANG");
						String kodeGdg = ""+rs.getString("KODE_GEDUNG");
						String kodeKmp = ""+rs.getString("KODE_KAMPUS");
						String tknDayTime = ""+rs.getString("TKN_HARI_TIME");
						String nmmDos = ""+rs.getString("NMMDOS");			
						String nmmAsdos = ""+rs.getString("NMMASDOS");
						String totEnrol = ""+rs.getInt("ENROLLED");
						String maxEnrol = ""+rs.getInt("MAX_ENROLLED");
						String minEnrol = ""+rs.getInt("MIN_ENROLLED");
						String subKeterKdkmk = ""+rs.getString("SUB_KETER_KDKMK");
						String initReqTime = ""+rs.getTimestamp("INIT_REQ_TIME");
						String tknNpmApr = ""+rs.getString("TKN_NPM_APPROVAL");
						String tknAprTime = ""+rs.getString("TKN_APPROVAL_TIME");
						String targetTtmhs = ""+rs.getInt("TARGET_TTMHS");
						String passed = ""+rs.getBoolean("PASSED");
						String rejected = ""+rs.getBoolean("REJECTED");
						String kodeGabung = ""+rs.getString("KODE_PENGGABUNGAN");
						String kodeGabungUniv = ""+rs.getString("KODE_GABUNGAN_UNIV");
						String cuid = ""+rs.getLong("UNIQUE_ID");
						String kdkmk = ""+rs.getString("KDKMKMAKUL");	
						lis.add(kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid+"`"+kdkmk);

					}
					
					if(vListKls!=null && vListKls.size()>0) {
						lis = vListKls.listIterator();
						stmt = con.prepareStatement("select CLASS_POOL_UNIQUE_ID from TRNLM where CLASS_POOL_UNIQUE_ID=?");
						while(lis.hasNext()) {
							String brs = (String)lis.next();
							StringTokenizer st = new StringTokenizer(brs,"`");
							//lis.add(kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid+"`"+kdkmk);
							String kdpst = st.nextToken();
							String nakmk = st.nextToken();
							String idkur = st.nextToken();
							String idkmk = st.nextToken();
							String shift = st.nextToken();
							String thsms = st.nextToken();
							String nopll = st.nextToken();
							String initNpmInp = st.nextToken();
							String lasNpmUpd = st.nextToken();
							String lasStatInf = st.nextToken();
							String curAvailStat = st.nextToken();
							String locked = st.nextToken();
							String npmdos = st.nextToken();
							String nodos = st.nextToken();
							String npmasdos = st.nextToken();
							String noasdos = st.nextToken();
							String batal = st.nextToken();
							String kodeKls = st.nextToken();
							String kodeRuang = st.nextToken();
							String kodeGdg = st.nextToken();
							String kodeKmp = st.nextToken();
							String tknDayTime = st.nextToken();
							String nmmDos = st.nextToken();
							String nmmAsdos = st.nextToken();
							String totEnrol = st.nextToken();
							String maxEnrol = st.nextToken();
							String minEnrol = st.nextToken();
							String subKeterKdkmk = st.nextToken();
							String initReqTime = st.nextToken();
							String tknNpmApr = st.nextToken();
							String tknAprTime = st.nextToken();
							String targetTtmhs = st.nextToken();
							String passed = st.nextToken();
							String rejected = st.nextToken();
							String kodeGabung = st.nextToken();
							String kodeGabungUniv = st.nextToken();
							String cuid = st.nextToken();
							String kdkmk = st.nextToken();
							stmt.setLong(1, Long.parseLong(cuid));
							rs = stmt.executeQuery();
							if(!rs.next()) {
								lis.remove();
							}
						}
					}
				}
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
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	Collections.sort(vListKls);
    	return vListKls;
    }
    
    
    public Vector getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya(Vector vScopeKdpst, String target_thsms, int limit, int offset) {
    	//String thsms_now = Checker.getThsmsNow();
    	//System.out.println("keisni");
    	Vector vListKls = new Vector();
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//1. cek kelas yg diajar sendiri
			//stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS=? and CANCELED=? order by KODE_KAMPUS,KDPST,IDKMK,SHIFT");
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS=? and CANCELED=? limit ?,?");
			stmt.setString(1, target_thsms);
			stmt.setString(2, this.operatorNpm);
			stmt.setBoolean(3, false);
			stmt.setInt(4, offset);
			stmt.setInt(5, limit);
			rs = stmt.executeQuery();
			ListIterator lis = vListKls.listIterator();
			
			while(rs.next()) {
				String nakmk = ""+rs.getString("NAKMKMAKUL");
				String idkur  = ""+rs.getLong("IDKUR");
				String idkmk = ""+rs.getLong("IDKMK");				
				String thsms = ""+rs.getString("THSMS");
				String kdpst = ""+rs.getString("KDPST");
				String shift = ""+rs.getString("SHIFT");
				String nopll = ""+rs.getInt("NORUT_KELAS_PARALEL");
				String initNpmInp = ""+rs.getString("INIT_NPM_INPUT");
				String lasNpmUpd = ""+rs.getString("LATEST_NPM_UPDATE");
				String lasStatInf = ""+rs.getString("LATEST_STATUS_INFO");
				String curAvailStat = ""+rs.getString("CURR_AVAIL_STATUS");
				String locked = ""+rs.getBoolean("LOCKED");
				String npmdos = ""+rs.getString("NPMDOS");
				String nodos = ""+rs.getString("NODOS");
				String npmasdos = ""+rs.getString("NPMASDOS");
				String noasdos = ""+rs.getString("NOASDOS");
				String batal = ""+rs.getBoolean("CANCELED");
				String kodeKls = ""+rs.getString("KODE_KELAS");
				String kodeRuang = ""+rs.getString("KODE_RUANG");
				String kodeGdg = ""+rs.getString("KODE_GEDUNG");
				String kodeKmp = ""+rs.getString("KODE_KAMPUS");
				String tknDayTime = ""+rs.getString("TKN_HARI_TIME");
				String nmmDos = ""+rs.getString("NMMDOS");			
				String nmmAsdos = ""+rs.getString("NMMASDOS");
				String totEnrol = ""+rs.getInt("ENROLLED");
				String maxEnrol = ""+rs.getInt("MAX_ENROLLED");
				String minEnrol = ""+rs.getInt("MIN_ENROLLED");
				String subKeterKdkmk = ""+rs.getString("SUB_KETER_KDKMK");
				String initReqTime = ""+rs.getTimestamp("INIT_REQ_TIME");
				String tknNpmApr = ""+rs.getString("TKN_NPM_APPROVAL");
				String tknAprTime = ""+rs.getString("TKN_APPROVAL_TIME");
				String targetTtmhs = ""+rs.getInt("TARGET_TTMHS");
				String passed = ""+rs.getBoolean("PASSED");
				String rejected = ""+rs.getBoolean("REJECTED");
				String kodeGabung = ""+rs.getString("KODE_PENGGABUNGAN");
				String kodeGabungUniv = ""+rs.getString("KODE_GABUNGAN_UNIV");
				String cuid = ""+rs.getLong("UNIQUE_ID");
				String kdkmk = ""+rs.getString("KDKMKMAKUL");
				lis.add(kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid+"`"+kdkmk);

			}
			//2. get kelas yg tidak diajar tapi boleh ikutan update
			/*
			 * kalo own skip process ini;
			 */
			if(vScopeKdpst!=null && vScopeKdpst.size()>0) {
				//String sql_cmd = "select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS<>? and CANCELED=? and (";
				String sql_cmd = "select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS<>? and CANCELED=?";
				ListIterator li = vScopeKdpst.listIterator();
				//System.out.println("sql_cmd = "+sql_cmd);
				boolean own_only = false;
				if(li.hasNext() && !own_only) {
					//64 00007 TU_EKONOMI 64 0
					sql_cmd = sql_cmd+" and (";
					do {
						String brs = (String)li.next();
						//System.out.println("brs__="+brs);
						if(brs.contains("own") || brs.contains("OWN")) {
							own_only = true;
						}
						else {
							String kdpst = Tool.getTokenKe(brs, 2);
							//System.out.println("sckpe kdpst = "+kdpst);
							sql_cmd = sql_cmd + "KDPST='"+kdpst+"'";
							if(li.hasNext()) {
								sql_cmd = sql_cmd + " OR ";
							}
						}
					}
					while(li.hasNext() && !own_only);
					sql_cmd = sql_cmd + ")";
				}
				/*
				while(li.hasNext()) {
					String kdpst = (String)li.next();
					//64 00007 TU_EKONOMI 64 0
					
				}
				*/
				
				//System.out.println("sql_cmd = "+sql_cmd);
				//sql_cmd =sql_cmd.replace("and ()", "");//kalo own
				//System.out.println("sql_cmd after= "+sql_cmd);
				if(!own_only) {
					stmt = con.prepareStatement(sql_cmd);
					stmt.setString(1, target_thsms);
					stmt.setString(2, this.operatorNpm);
					stmt.setBoolean(3, false);
					rs = stmt.executeQuery();
					while(rs.next()) {
						//System.out.println("rs.next()");
						String nakmk = ""+rs.getString("NAKMKMAKUL");
						String idkur  = ""+rs.getLong("IDKUR");
						String idkmk = ""+rs.getLong("IDKMK");				
						String thsms = ""+rs.getString("THSMS");
						String kdpst = ""+rs.getString("KDPST");
						String shift = ""+rs.getString("SHIFT");
						String nopll = ""+rs.getInt("NORUT_KELAS_PARALEL");
						String initNpmInp = ""+rs.getString("INIT_NPM_INPUT");
						String lasNpmUpd = ""+rs.getString("LATEST_NPM_UPDATE");
						String lasStatInf = ""+rs.getString("LATEST_STATUS_INFO");
						String curAvailStat = ""+rs.getString("CURR_AVAIL_STATUS");
						String locked = ""+rs.getBoolean("LOCKED");
						String npmdos = ""+rs.getString("NPMDOS");
						String nodos = ""+rs.getString("NODOS");
						String npmasdos = ""+rs.getString("NPMASDOS");
						String noasdos = ""+rs.getString("NOASDOS");
						String batal = ""+rs.getBoolean("CANCELED");
						String kodeKls = ""+rs.getString("KODE_KELAS");
						String kodeRuang = ""+rs.getString("KODE_RUANG");
						String kodeGdg = ""+rs.getString("KODE_GEDUNG");
						String kodeKmp = ""+rs.getString("KODE_KAMPUS");
						String tknDayTime = ""+rs.getString("TKN_HARI_TIME");
						String nmmDos = ""+rs.getString("NMMDOS");			
						String nmmAsdos = ""+rs.getString("NMMASDOS");
						String totEnrol = ""+rs.getInt("ENROLLED");
						String maxEnrol = ""+rs.getInt("MAX_ENROLLED");
						String minEnrol = ""+rs.getInt("MIN_ENROLLED");
						String subKeterKdkmk = ""+rs.getString("SUB_KETER_KDKMK");
						String initReqTime = ""+rs.getTimestamp("INIT_REQ_TIME");
						String tknNpmApr = ""+rs.getString("TKN_NPM_APPROVAL");
						String tknAprTime = ""+rs.getString("TKN_APPROVAL_TIME");
						String targetTtmhs = ""+rs.getInt("TARGET_TTMHS");
						String passed = ""+rs.getBoolean("PASSED");
						String rejected = ""+rs.getBoolean("REJECTED");
						String kodeGabung = ""+rs.getString("KODE_PENGGABUNGAN");
						String kodeGabungUniv = ""+rs.getString("KODE_GABUNGAN_UNIV");
						String cuid = ""+rs.getLong("UNIQUE_ID");
						String kdkmk = ""+rs.getString("KDKMKMAKUL");	
						lis.add(kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid+"`"+kdkmk);

					}
				}
				
			}
			//filter hanya yg ada mhsnya aja
			if(vListKls!=null && vListKls.size()>0) {
				lis = vListKls.listIterator();
				stmt = con.prepareStatement("select CLASS_POOL_UNIQUE_ID from TRNLM where CLASS_POOL_UNIQUE_ID=?");
				while(lis.hasNext()) {
					String brs = (String)lis.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					//lis.add(kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid+"`"+kdkmk);
					String kdpst = st.nextToken();
					String nakmk = st.nextToken();
					String idkur = st.nextToken();
					String idkmk = st.nextToken();
					String shift = st.nextToken();
					String thsms = st.nextToken();
					String nopll = st.nextToken();
					String initNpmInp = st.nextToken();
					String lasNpmUpd = st.nextToken();
					String lasStatInf = st.nextToken();
					String curAvailStat = st.nextToken();
					String locked = st.nextToken();
					String npmdos = st.nextToken();
					String nodos = st.nextToken();
					String npmasdos = st.nextToken();
					String noasdos = st.nextToken();
					String batal = st.nextToken();
					String kodeKls = st.nextToken();
					String kodeRuang = st.nextToken();
					String kodeGdg = st.nextToken();
					String kodeKmp = st.nextToken();
					String tknDayTime = st.nextToken();
					String nmmDos = st.nextToken();
					String nmmAsdos = st.nextToken();
					String totEnrol = st.nextToken();
					String maxEnrol = st.nextToken();
					String minEnrol = st.nextToken();
					String subKeterKdkmk = st.nextToken();
					String initReqTime = st.nextToken();
					String tknNpmApr = st.nextToken();
					String tknAprTime = st.nextToken();
					String targetTtmhs = st.nextToken();
					String passed = st.nextToken();
					String rejected = st.nextToken();
					String kodeGabung = st.nextToken();
					String kodeGabungUniv = st.nextToken();
					String cuid = st.nextToken();
					String kdkmk = st.nextToken();
					stmt.setLong(1, Long.parseLong(cuid));
					rs = stmt.executeQuery();
					if(!rs.next()) {
						lis.remove();
					}
				}
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
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	Collections.sort(vListKls);
    	return vListKls;
    }    
    /*
     * ada dua versi
     * yg satunya pake scope, kalo ini seluruh universitas
     */
    public Vector getListOpenedClass(String target_thsms) {
    	Vector v = null;
    	//ListIterator li = v.listIterator();
    	
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and CANCELED=? order by KDPST, IDKMK, SHIFT");
			stmt.setString(1, target_thsms);
			stmt.setBoolean(2, false);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v=new Vector();
				ListIterator li = v.listIterator();
				do {
					String idkur = ""+rs.getLong("IDKUR");
					String idkmk = ""+rs.getLong("IDKMK");
					//thsms ignoeee
					String kdpst = ""+rs.getString("KDPST");
					String shift = ""+rs.getString("SHIFT");
					String noKlsPll = ""+rs.getInt("NORUT_KELAS_PARALEL");
					String npm_pertama_input = ""+rs.getString("INIT_NPM_INPUT");
					String npm_terakhir_updat = ""+rs.getString("LATEST_NPM_UPDATE");
					String status_akhir = ""+rs.getString("LATEST_STATUS_INFO");
					String curr_avail_status = ""+rs.getString("CURR_AVAIL_STATUS");
					String locked_or_editable = ""+rs.getBoolean("LOCKED");
					String npmdos = ""+rs.getString("NPMDOS");
					String nodos = ""+rs.getString("NODOS");
					String npmasdos = ""+rs.getString("NPMASDOS");
					String noasdos = ""+rs.getString("NOASDOS");
					String canceled = ""+rs.getBoolean("CANCELED");
					String kode_kelas = ""+rs.getString("KODE_KELAS");
					String kode_ruang = ""+rs.getString("KODE_RUANG");
					String kode_gedung = ""+rs.getString("KODE_GEDUNG");
					String kode_kampus = ""+rs.getString("KODE_KAMPUS");
					String tkn_day_time = ""+rs.getString("TKN_HARI_TIME");
					String nmmdos = ""+rs.getString("NMMDOS");
					String nmmasdos = ""+rs.getString("NMMASDOS");
					String enrolled = ""+rs.getInt("ENROLLED");//jum mhs
					String max_enrolled = ""+rs.getInt("MAX_ENROLLED");//jum mhs
					String min_enrolled = ""+rs.getInt("MIN_ENROLLED");//jum mhs
					String sub_keter_kdkmk = ""+rs.getString("SUB_KETER_KDKMK");
					String init_req_time = ""+rs.getTimestamp("INIT_REQ_TIME");
					String tkn_npm_approval = ""+rs.getString("TKN_NPM_APPROVAL");
					String tkn_approval_time = ""+rs.getString("TKN_APPROVAL_TIME");
					String target_ttmhs = ""+rs.getInt("TARGET_TTMHS");
					String passed = ""+rs.getBoolean("PASSED");
					String rejected = ""+rs.getBoolean("REJECTED");
					String kode_gabung_kls = ""+rs.getString("KODE_PENGGABUNGAN");
					String kode_gabung_kmp = ""+rs.getString("KODE_GABUNGAN_UNIV");
					String unique_id = ""+rs.getLong("UNIQUE_ID");
					String kdkmk = ""+rs.getString("KDKMKMAKUL");
					String nakmk = ""+rs.getString("NAKMKMAKUL");
					String skstm = ""+rs.getInt("SKSTMMAKUL");
					String skspr = ""+rs.getInt("SKSPRMAKUL");
					String skslp = ""+rs.getInt("SKSLPMAKUL");
					
					li.add(idkur+"`"+idkmk+"`"+kdpst+"`"+shift+"`"+noKlsPll+"`"+npm_pertama_input+"`"+npm_terakhir_updat+"`"+status_akhir+"`"+curr_avail_status+"`"+locked_or_editable+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+canceled+"`"+kode_kelas+"`"+kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
			
				}
				while(rs.next());
			}	
			/*
			stmt = con.prepareStatement("select distinct UNIQUE_ID from CLASS_POOL where THSMS=? and CANCELED=? order by KDPST, IDKMK");
			stmt.setString(1, target_thsms);
			stmt.setBoolean(2, false);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				ListIterator li = v.listIterator();
				do {
					String cuid = ""+rs.getLong(1);
					//System.out.println(cuid);
					li.add(cuid);
				}
				while(rs.next());
			}
			*/
			//if(v!=null && v.size()>0) {
				//get info kelas berdasarkan CUID
				
			//}
    			
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
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    		
    	
    	return v;
    }
    
    public Vector getListOpenedClass(String target_thsms, Vector vlist_scope_viewAbsen) {
    	//vlist_scope_viewAbsen baris format = 114 65201 MHS_ILMU_PEMERINTAHAN 114 C
    	Vector v = null;
    	//ListIterator li = v.listIterator();
    	if(vlist_scope_viewAbsen!=null && vlist_scope_viewAbsen.size()>0) {
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			ListIterator li = vlist_scope_viewAbsen.listIterator(); 
    			String sql = "";
    			while(li.hasNext()) {
    				
    				String brs = (String)li.next();
    				String kdpst = Tool.getTokenKe(brs, 2);
    				sql = sql + "KDPST='"+kdpst+"'";
    				if(li.hasNext()) {
    					sql = sql +" or ";
    				}
    			}
    			
    			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and ("+sql+") and CANCELED=? order by KDPST, IDKMK, SHIFT");
    			stmt.setString(1, target_thsms);
    			stmt.setBoolean(2, false);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v=new Vector();
    				li = v.listIterator();
    				do {
    					String idkur = ""+rs.getLong("IDKUR");
    					String idkmk = ""+rs.getLong("IDKMK");
    					//thsms ignoeee
    					String kdpst = ""+rs.getString("KDPST");
    					String shift = ""+rs.getString("SHIFT");
    					String noKlsPll = ""+rs.getInt("NORUT_KELAS_PARALEL");
    					String npm_pertama_input = ""+rs.getString("INIT_NPM_INPUT");
    					String npm_terakhir_updat = ""+rs.getString("LATEST_NPM_UPDATE");
    					String status_akhir = ""+rs.getString("LATEST_STATUS_INFO");
    					String curr_avail_status = ""+rs.getString("CURR_AVAIL_STATUS");
    					String locked_or_editable = ""+rs.getBoolean("LOCKED");
    					String npmdos = ""+rs.getString("NPMDOS");
    					String nodos = ""+rs.getString("NODOS");
    					String npmasdos = ""+rs.getString("NPMASDOS");
    					String noasdos = ""+rs.getString("NOASDOS");
    					String canceled = ""+rs.getBoolean("CANCELED");
    					String kode_kelas = ""+rs.getString("KODE_KELAS");
    					String kode_ruang = ""+rs.getString("KODE_RUANG");
    					String kode_gedung = ""+rs.getString("KODE_GEDUNG");
    					String kode_kampus = ""+rs.getString("KODE_KAMPUS");
    					String tkn_day_time = ""+rs.getString("TKN_HARI_TIME");
    					String nmmdos = ""+rs.getString("NMMDOS");
    					String nmmasdos = ""+rs.getString("NMMASDOS");
    					String enrolled = ""+rs.getInt("ENROLLED");//jum mhs
    					String max_enrolled = ""+rs.getInt("MAX_ENROLLED");//jum mhs
    					String min_enrolled = ""+rs.getInt("MIN_ENROLLED");//jum mhs
    					String sub_keter_kdkmk = ""+rs.getString("SUB_KETER_KDKMK");
    					String init_req_time = ""+rs.getTimestamp("INIT_REQ_TIME");
    					String tkn_npm_approval = ""+rs.getString("TKN_NPM_APPROVAL");
    					String tkn_approval_time = ""+rs.getString("TKN_APPROVAL_TIME");
    					String target_ttmhs = ""+rs.getInt("TARGET_TTMHS");
    					String passed = ""+rs.getBoolean("PASSED");
    					String rejected = ""+rs.getBoolean("REJECTED");
    					String kode_gabung_kls = ""+rs.getString("KODE_PENGGABUNGAN");
    					String kode_gabung_kmp = ""+rs.getString("KODE_GABUNGAN_UNIV");
    					String unique_id = ""+rs.getLong("UNIQUE_ID");
    					String kdkmk = ""+rs.getString("KDKMKMAKUL");
    					String nakmk = ""+rs.getString("NAKMKMAKUL");
    					String skstm = ""+rs.getInt("SKSTMMAKUL");
    					String skspr = ""+rs.getInt("SKSPRMAKUL");
    					String skslp = ""+rs.getInt("SKSLPMAKUL");
    					
    					li.add(idkur+"`"+idkmk+"`"+kdpst+"`"+shift+"`"+noKlsPll+"`"+npm_pertama_input+"`"+npm_terakhir_updat+"`"+status_akhir+"`"+curr_avail_status+"`"+locked_or_editable+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+canceled+"`"+kode_kelas+"`"+kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
    			
    				}
    				while(rs.next());
    			}	
    			/*
    			stmt = con.prepareStatement("select distinct UNIQUE_ID from CLASS_POOL where THSMS=? and CANCELED=? order by KDPST, IDKMK");
    			stmt.setString(1, target_thsms);
    			stmt.setBoolean(2, false);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v = new Vector();
    				ListIterator li = v.listIterator();
    				do {
    					String cuid = ""+rs.getLong(1);
    					//System.out.println(cuid);
    					li.add(cuid);
    				}
    				while(rs.next());
    			}
    			*/
    			//if(v!=null && v.size()>0) {
    				//get info kelas berdasarkan CUID
    				
    			//}
        			
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
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}
    	}
    	
    		
    	
    	return v;
    }
    
    
    public Vector getListOpenedClassYgAdaMhsnyaOnly(String target_thsms, Vector vlist_scope_viewAbsen) {
    	//vlist_scope_viewAbsen baris format = 114 65201 MHS_ILMU_PEMERINTAHAN 114 C
    	Vector v = null;
    	//System.out.println("target_thsms="+target_thsms);
    	//ListIterator li = v.listIterator();
    	if(vlist_scope_viewAbsen!=null && vlist_scope_viewAbsen.size()>0) {
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			ListIterator li = vlist_scope_viewAbsen.listIterator(); 
    			String sql = "";
    			while(li.hasNext()) {
    				
    				String brs = (String)li.next();
    				
    				//System.out.println("brs="+brs);
    				String kdpst = Tool.getTokenKe(brs, 2);
    				sql = sql + "KDPST='"+kdpst+"'";
    				if(li.hasNext()) {
    					sql = sql +" or ";
    				}
    			}
    			//System.out.println("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and ("+sql+") and CANCELED=? order by KDPST, IDKMK, SHIFT");
    			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and ("+sql+") and CANCELED=? order by KDPST, IDKMK, SHIFT");
    			stmt.setString(1, target_thsms);
    			stmt.setBoolean(2, false);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v=new Vector();
    				li = v.listIterator();
    				do {
    					String idkur = ""+rs.getLong("IDKUR");
    					String idkmk = ""+rs.getLong("IDKMK");
    					//thsms ignoeee
    					String kdpst = ""+rs.getString("KDPST");
    					String shift = ""+rs.getString("SHIFT");
    					String noKlsPll = ""+rs.getInt("NORUT_KELAS_PARALEL");
    					String npm_pertama_input = ""+rs.getString("INIT_NPM_INPUT");
    					String npm_terakhir_updat = ""+rs.getString("LATEST_NPM_UPDATE");
    					String status_akhir = ""+rs.getString("LATEST_STATUS_INFO");
    					String curr_avail_status = ""+rs.getString("CURR_AVAIL_STATUS");
    					String locked_or_editable = ""+rs.getBoolean("LOCKED");
    					String npmdos = ""+rs.getString("NPMDOS");
    					String nodos = ""+rs.getString("NODOS");
    					String npmasdos = ""+rs.getString("NPMASDOS");
    					String noasdos = ""+rs.getString("NOASDOS");
    					String canceled = ""+rs.getBoolean("CANCELED");
    					String kode_kelas = ""+rs.getString("KODE_KELAS");
    					String kode_ruang = ""+rs.getString("KODE_RUANG");
    					String kode_gedung = ""+rs.getString("KODE_GEDUNG");
    					String kode_kampus = ""+rs.getString("KODE_KAMPUS");
    					String tkn_day_time = ""+rs.getString("TKN_HARI_TIME");
    					String nmmdos = ""+rs.getString("NMMDOS");
    					String nmmasdos = ""+rs.getString("NMMASDOS");
    					String enrolled = ""+rs.getInt("ENROLLED");//jum mhs
    					String max_enrolled = ""+rs.getInt("MAX_ENROLLED");//jum mhs
    					String min_enrolled = ""+rs.getInt("MIN_ENROLLED");//jum mhs
    					String sub_keter_kdkmk = ""+rs.getString("SUB_KETER_KDKMK");
    					String init_req_time = ""+rs.getTimestamp("INIT_REQ_TIME");
    					String tkn_npm_approval = ""+rs.getString("TKN_NPM_APPROVAL");
    					String tkn_approval_time = ""+rs.getString("TKN_APPROVAL_TIME");
    					String target_ttmhs = ""+rs.getInt("TARGET_TTMHS");
    					String passed = ""+rs.getBoolean("PASSED");
    					String rejected = ""+rs.getBoolean("REJECTED");
    					String kode_gabung_kls = ""+rs.getString("KODE_PENGGABUNGAN");
    					String kode_gabung_kmp = ""+rs.getString("KODE_GABUNGAN_UNIV");
    					String unique_id = ""+rs.getLong("UNIQUE_ID");
    					//System.out.println("unique_id="+unique_id);
    					String kdkmk = ""+rs.getString("KDKMKMAKUL");
    					String nakmk = ""+rs.getString("NAKMKMAKUL");
    					String skstm = ""+rs.getInt("SKSTMMAKUL");
    					String skspr = ""+rs.getInt("SKSPRMAKUL");
    					String skslp = ""+rs.getInt("SKSLPMAKUL");
    					
    					li.add(idkur+"`"+idkmk+"`"+kdpst+"`"+shift+"`"+noKlsPll+"`"+npm_pertama_input+"`"+npm_terakhir_updat+"`"+status_akhir+"`"+curr_avail_status+"`"+locked_or_editable+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+canceled+"`"+kode_kelas+"`"+kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
    			
    				}
    				while(rs.next());
    				//System.out.println("vsize="+v.size());
    				if(v!=null && v.size()>0) {
    					li = v.listIterator();
    					stmt = con.prepareStatement("select CLASS_POOL_UNIQUE_ID from TRNLM where THSMSTRNLM=? and CLASS_POOL_UNIQUE_ID=?");
    					while(li.hasNext()) {
    						String brs = (String)li.next();
    						StringTokenizer st = new StringTokenizer(brs,"`");
    						String idkur=st.nextToken();
    						String idkmk=st.nextToken();
    						String kdpst=st.nextToken();
    						String shift=st.nextToken();
    						String noKlsPll=st.nextToken();
    						String npm_pertama_input=st.nextToken();
    						String npm_terakhir_updat=st.nextToken();
    						String status_akhir=st.nextToken();
    						String curr_avail_status=st.nextToken();
    						String locked_or_editable=st.nextToken();
    						String npmdos=st.nextToken();
    						String nodos=st.nextToken();
    						String npmasdos=st.nextToken();
    						String noasdos=st.nextToken();
    						String canceled=st.nextToken();
    						String kode_kelas=st.nextToken();
    						String kode_ruang=st.nextToken();
    						String kode_gedung=st.nextToken();
    						String kode_kampus=st.nextToken();
    						String tkn_day_time=st.nextToken();
    						String nmmdos=st.nextToken();
    						String nmmasdos=st.nextToken();
    						String enrolled=st.nextToken();
    						String max_enrolled=st.nextToken();
    						String min_enrolled=st.nextToken();
    						String sub_keter_kdkmk=st.nextToken();
    						String init_req_time=st.nextToken();
    						String tkn_npm_approval=st.nextToken();
    						String tkn_approval_time=st.nextToken();
    						String target_ttmhs=st.nextToken();
    						String passed=st.nextToken();
    						String rejected=st.nextToken();
    						String kode_gabung_kls=st.nextToken();
    						String kode_gabung_kmp=st.nextToken();
    						String unique_id= st.nextToken();
    						//System.out.println("---"+target_thsms+"  "+unique_id);
    						stmt.setString(1, target_thsms);
    						stmt.setLong(2, Long.parseLong(unique_id));
    						rs = stmt.executeQuery();
    						
    						if(!rs.next()) {
    							li.remove();
    						}
    					}
    				}
    			}	
    			//System.out.println("vfsize="+v.size());
    			/*
    			stmt = con.prepareStatement("select distinct UNIQUE_ID from CLASS_POOL where THSMS=? and CANCELED=? order by KDPST, IDKMK");
    			stmt.setString(1, target_thsms);
    			stmt.setBoolean(2, false);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v = new Vector();
    				ListIterator li = v.listIterator();
    				do {
    					String cuid = ""+rs.getLong(1);
    					//System.out.println(cuid);
    					li.add(cuid);
    				}
    				while(rs.next());
    			}
    			*/
    			//if(v!=null && v.size()>0) {
    				//get info kelas berdasarkan CUID
    				
    			//}
        			
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
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}
    	}
    	
    		
    	
    	return v;
    }
    
    public Vector getListOpenedClassYgAdaMhsnyaOnly(String target_thsms, String target_kdpst, Vector vlist_scope_viewAbsen) {
    	//vlist_scope_viewAbsen baris format = 114 65201 MHS_ILMU_PEMERINTAHAN 114 C
    	Vector v = null;
    	//System.out.println("target_thsms="+target_thsms);
    	//ListIterator li = v.listIterator();
    	if(vlist_scope_viewAbsen!=null && vlist_scope_viewAbsen.size()>0) {
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			ListIterator li = vlist_scope_viewAbsen.listIterator(); 
    			String sql = "";
    			while(li.hasNext()) {
    				
    				String brs = (String)li.next();
    				
    				//System.out.println("brs="+brs);
    				String kdpst = Tool.getTokenKe(brs, 2);
    				sql = sql + "KDPST='"+kdpst+"'";
    				if(li.hasNext()) {
    					sql = sql +" or ";
    				}
    			}
    			//System.out.println("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and ("+sql+") and CANCELED=? order by KDPST, IDKMK, SHIFT");
    			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KDPST='"+target_kdpst+"' and ("+sql+") and CANCELED=? order by KDPST, IDKMK, SHIFT");
    			stmt.setString(1, target_thsms);
    			stmt.setBoolean(2, false);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v=new Vector();
    				li = v.listIterator();
    				do {
    					String idkur = ""+rs.getLong("IDKUR");
    					String idkmk = ""+rs.getLong("IDKMK");
    					//thsms ignoeee
    					String kdpst = ""+rs.getString("KDPST");
    					String shift = ""+rs.getString("SHIFT");
    					String noKlsPll = ""+rs.getInt("NORUT_KELAS_PARALEL");
    					String npm_pertama_input = ""+rs.getString("INIT_NPM_INPUT");
    					String npm_terakhir_updat = ""+rs.getString("LATEST_NPM_UPDATE");
    					String status_akhir = ""+rs.getString("LATEST_STATUS_INFO");
    					String curr_avail_status = ""+rs.getString("CURR_AVAIL_STATUS");
    					String locked_or_editable = ""+rs.getBoolean("LOCKED");
    					String npmdos = ""+rs.getString("NPMDOS");
    					String nodos = ""+rs.getString("NODOS");
    					String npmasdos = ""+rs.getString("NPMASDOS");
    					String noasdos = ""+rs.getString("NOASDOS");
    					String canceled = ""+rs.getBoolean("CANCELED");
    					String kode_kelas = ""+rs.getString("KODE_KELAS");
    					String kode_ruang = ""+rs.getString("KODE_RUANG");
    					String kode_gedung = ""+rs.getString("KODE_GEDUNG");
    					String kode_kampus = ""+rs.getString("KODE_KAMPUS");
    					String tkn_day_time = ""+rs.getString("TKN_HARI_TIME");
    					String nmmdos = ""+rs.getString("NMMDOS");
    					String nmmasdos = ""+rs.getString("NMMASDOS");
    					String enrolled = ""+rs.getInt("ENROLLED");//jum mhs
    					String max_enrolled = ""+rs.getInt("MAX_ENROLLED");//jum mhs
    					String min_enrolled = ""+rs.getInt("MIN_ENROLLED");//jum mhs
    					String sub_keter_kdkmk = ""+rs.getString("SUB_KETER_KDKMK");
    					String init_req_time = ""+rs.getTimestamp("INIT_REQ_TIME");
    					String tkn_npm_approval = ""+rs.getString("TKN_NPM_APPROVAL");
    					String tkn_approval_time = ""+rs.getString("TKN_APPROVAL_TIME");
    					String target_ttmhs = ""+rs.getInt("TARGET_TTMHS");
    					String passed = ""+rs.getBoolean("PASSED");
    					String rejected = ""+rs.getBoolean("REJECTED");
    					String kode_gabung_kls = ""+rs.getString("KODE_PENGGABUNGAN");
    					String kode_gabung_kmp = ""+rs.getString("KODE_GABUNGAN_UNIV");
    					String unique_id = ""+rs.getLong("UNIQUE_ID");
    					//System.out.println("unique_id="+unique_id);
    					String kdkmk = ""+rs.getString("KDKMKMAKUL");
    					String nakmk = ""+rs.getString("NAKMKMAKUL");
    					String skstm = ""+rs.getInt("SKSTMMAKUL");
    					String skspr = ""+rs.getInt("SKSPRMAKUL");
    					String skslp = ""+rs.getInt("SKSLPMAKUL");
    					
    					li.add(idkur+"`"+idkmk+"`"+kdpst+"`"+shift+"`"+noKlsPll+"`"+npm_pertama_input+"`"+npm_terakhir_updat+"`"+status_akhir+"`"+curr_avail_status+"`"+locked_or_editable+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+canceled+"`"+kode_kelas+"`"+kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
    			
    				}
    				while(rs.next());
    				//System.out.println("vsize="+v.size());
    				if(v!=null && v.size()>0) {
    					li = v.listIterator();
    					stmt = con.prepareStatement("select CLASS_POOL_UNIQUE_ID from TRNLM where THSMSTRNLM=? and CLASS_POOL_UNIQUE_ID=?");
    					while(li.hasNext()) {
    						String brs = (String)li.next();
    						StringTokenizer st = new StringTokenizer(brs,"`");
    						String idkur=st.nextToken();
    						String idkmk=st.nextToken();
    						String kdpst=st.nextToken();
    						String shift=st.nextToken();
    						String noKlsPll=st.nextToken();
    						String npm_pertama_input=st.nextToken();
    						String npm_terakhir_updat=st.nextToken();
    						String status_akhir=st.nextToken();
    						String curr_avail_status=st.nextToken();
    						String locked_or_editable=st.nextToken();
    						String npmdos=st.nextToken();
    						String nodos=st.nextToken();
    						String npmasdos=st.nextToken();
    						String noasdos=st.nextToken();
    						String canceled=st.nextToken();
    						String kode_kelas=st.nextToken();
    						String kode_ruang=st.nextToken();
    						String kode_gedung=st.nextToken();
    						String kode_kampus=st.nextToken();
    						String tkn_day_time=st.nextToken();
    						String nmmdos=st.nextToken();
    						String nmmasdos=st.nextToken();
    						String enrolled=st.nextToken();
    						String max_enrolled=st.nextToken();
    						String min_enrolled=st.nextToken();
    						String sub_keter_kdkmk=st.nextToken();
    						String init_req_time=st.nextToken();
    						String tkn_npm_approval=st.nextToken();
    						String tkn_approval_time=st.nextToken();
    						String target_ttmhs=st.nextToken();
    						String passed=st.nextToken();
    						String rejected=st.nextToken();
    						String kode_gabung_kls=st.nextToken();
    						String kode_gabung_kmp=st.nextToken();
    						String unique_id= st.nextToken();
    						//System.out.println("---"+target_thsms+"  "+unique_id);
    						stmt.setString(1, target_thsms);
    						stmt.setLong(2, Long.parseLong(unique_id));
    						rs = stmt.executeQuery();
    						
    						if(!rs.next()) {
    							li.remove();
    						}
    					}
    				}
    			}	
    			//System.out.println("vfsize="+v.size());
    			/*
    			stmt = con.prepareStatement("select distinct UNIQUE_ID from CLASS_POOL where THSMS=? and CANCELED=? order by KDPST, IDKMK");
    			stmt.setString(1, target_thsms);
    			stmt.setBoolean(2, false);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v = new Vector();
    				ListIterator li = v.listIterator();
    				do {
    					String cuid = ""+rs.getLong(1);
    					//System.out.println(cuid);
    					li.add(cuid);
    				}
    				while(rs.next());
    			}
    			*/
    			//if(v!=null && v.size()>0) {
    				//get info kelas berdasarkan CUID
    				
    			//}
        			
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
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}
    	}
    	
    		
    	
    	return v;
    }
    
    public Vector getListOpenedClassYgAdaMhsnyaOnly(String target_thsms, Vector vlist_scope_viewAbsen, String target_shift) {
    	//vlist_scope_viewAbsen baris format = 114 65201 MHS_ILMU_PEMERINTAHAN 114 C
    	Vector v = null;
    	//System.out.println("target_thsms="+target_thsms);
    	//ListIterator li = v.listIterator();
    	if(vlist_scope_viewAbsen!=null && vlist_scope_viewAbsen.size()>0) {
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			ListIterator li = vlist_scope_viewAbsen.listIterator(); 
    			String sql = "";
    			while(li.hasNext()) {
    				
    				String brs = (String)li.next();
    				
    				//System.out.println("brs="+brs);
    				String kdpst = Tool.getTokenKe(brs, 2);
    				sql = sql + "KDPST='"+kdpst+"'";
    				if(li.hasNext()) {
    					sql = sql +" or ";
    				}
    			}
    			//System.out.println("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and ("+sql+") and CANCELED=? order by KDPST, IDKMK, SHIFT");
    			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and SHIFT='"+target_shift+"' and ("+sql+") and CANCELED=? order by KDPST, IDKMK, SHIFT");
    			stmt.setString(1, target_thsms);
    			stmt.setBoolean(2, false);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v=new Vector();
    				li = v.listIterator();
    				do {
    					String idkur = ""+rs.getLong("IDKUR");
    					String idkmk = ""+rs.getLong("IDKMK");
    					//thsms ignoeee
    					String kdpst = ""+rs.getString("KDPST");
    					String shift = ""+rs.getString("SHIFT");
    					String noKlsPll = ""+rs.getInt("NORUT_KELAS_PARALEL");
    					String npm_pertama_input = ""+rs.getString("INIT_NPM_INPUT");
    					String npm_terakhir_updat = ""+rs.getString("LATEST_NPM_UPDATE");
    					String status_akhir = ""+rs.getString("LATEST_STATUS_INFO");
    					String curr_avail_status = ""+rs.getString("CURR_AVAIL_STATUS");
    					String locked_or_editable = ""+rs.getBoolean("LOCKED");
    					String npmdos = ""+rs.getString("NPMDOS");
    					String nodos = ""+rs.getString("NODOS");
    					String npmasdos = ""+rs.getString("NPMASDOS");
    					String noasdos = ""+rs.getString("NOASDOS");
    					String canceled = ""+rs.getBoolean("CANCELED");
    					String kode_kelas = ""+rs.getString("KODE_KELAS");
    					String kode_ruang = ""+rs.getString("KODE_RUANG");
    					String kode_gedung = ""+rs.getString("KODE_GEDUNG");
    					String kode_kampus = ""+rs.getString("KODE_KAMPUS");
    					String tkn_day_time = ""+rs.getString("TKN_HARI_TIME");
    					String nmmdos = ""+rs.getString("NMMDOS");
    					String nmmasdos = ""+rs.getString("NMMASDOS");
    					String enrolled = ""+rs.getInt("ENROLLED");//jum mhs
    					String max_enrolled = ""+rs.getInt("MAX_ENROLLED");//jum mhs
    					String min_enrolled = ""+rs.getInt("MIN_ENROLLED");//jum mhs
    					String sub_keter_kdkmk = ""+rs.getString("SUB_KETER_KDKMK");
    					String init_req_time = ""+rs.getTimestamp("INIT_REQ_TIME");
    					String tkn_npm_approval = ""+rs.getString("TKN_NPM_APPROVAL");
    					String tkn_approval_time = ""+rs.getString("TKN_APPROVAL_TIME");
    					String target_ttmhs = ""+rs.getInt("TARGET_TTMHS");
    					String passed = ""+rs.getBoolean("PASSED");
    					String rejected = ""+rs.getBoolean("REJECTED");
    					String kode_gabung_kls = ""+rs.getString("KODE_PENGGABUNGAN");
    					String kode_gabung_kmp = ""+rs.getString("KODE_GABUNGAN_UNIV");
    					String unique_id = ""+rs.getLong("UNIQUE_ID");
    					//System.out.println("unique_id="+unique_id);
    					String kdkmk = ""+rs.getString("KDKMKMAKUL");
    					String nakmk = ""+rs.getString("NAKMKMAKUL");
    					String skstm = ""+rs.getInt("SKSTMMAKUL");
    					String skspr = ""+rs.getInt("SKSPRMAKUL");
    					String skslp = ""+rs.getInt("SKSLPMAKUL");
    					
    					li.add(idkur+"`"+idkmk+"`"+kdpst+"`"+shift+"`"+noKlsPll+"`"+npm_pertama_input+"`"+npm_terakhir_updat+"`"+status_akhir+"`"+curr_avail_status+"`"+locked_or_editable+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+canceled+"`"+kode_kelas+"`"+kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
    			
    				}
    				while(rs.next());
    				//System.out.println("vsize="+v.size());
    				if(v!=null && v.size()>0) {
    					li = v.listIterator();
    					stmt = con.prepareStatement("select CLASS_POOL_UNIQUE_ID from TRNLM where THSMSTRNLM=? and CLASS_POOL_UNIQUE_ID=?");
    					while(li.hasNext()) {
    						String brs = (String)li.next();
    						StringTokenizer st = new StringTokenizer(brs,"`");
    						String idkur=st.nextToken();
    						String idkmk=st.nextToken();
    						String kdpst=st.nextToken();
    						String shift=st.nextToken();
    						String noKlsPll=st.nextToken();
    						String npm_pertama_input=st.nextToken();
    						String npm_terakhir_updat=st.nextToken();
    						String status_akhir=st.nextToken();
    						String curr_avail_status=st.nextToken();
    						String locked_or_editable=st.nextToken();
    						String npmdos=st.nextToken();
    						String nodos=st.nextToken();
    						String npmasdos=st.nextToken();
    						String noasdos=st.nextToken();
    						String canceled=st.nextToken();
    						String kode_kelas=st.nextToken();
    						String kode_ruang=st.nextToken();
    						String kode_gedung=st.nextToken();
    						String kode_kampus=st.nextToken();
    						String tkn_day_time=st.nextToken();
    						String nmmdos=st.nextToken();
    						String nmmasdos=st.nextToken();
    						String enrolled=st.nextToken();
    						String max_enrolled=st.nextToken();
    						String min_enrolled=st.nextToken();
    						String sub_keter_kdkmk=st.nextToken();
    						String init_req_time=st.nextToken();
    						String tkn_npm_approval=st.nextToken();
    						String tkn_approval_time=st.nextToken();
    						String target_ttmhs=st.nextToken();
    						String passed=st.nextToken();
    						String rejected=st.nextToken();
    						String kode_gabung_kls=st.nextToken();
    						String kode_gabung_kmp=st.nextToken();
    						String unique_id= st.nextToken();
    						//System.out.println("---"+target_thsms+"  "+unique_id);
    						stmt.setString(1, target_thsms);
    						stmt.setLong(2, Long.parseLong(unique_id));
    						rs = stmt.executeQuery();
    						
    						if(!rs.next()) {
    							li.remove();
    						}
    					}
    				}
    			}	
    			//System.out.println("vfsize="+v.size());
    			/*
    			stmt = con.prepareStatement("select distinct UNIQUE_ID from CLASS_POOL where THSMS=? and CANCELED=? order by KDPST, IDKMK");
    			stmt.setString(1, target_thsms);
    			stmt.setBoolean(2, false);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v = new Vector();
    				ListIterator li = v.listIterator();
    				do {
    					String cuid = ""+rs.getLong(1);
    					//System.out.println(cuid);
    					li.add(cuid);
    				}
    				while(rs.next());
    			}
    			*/
    			//if(v!=null && v.size()>0) {
    				//get info kelas berdasarkan CUID
    				
    			//}
        			
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
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}
    	}
    	
    		
    	
    	return v;
    }
    
    public Vector getListOpenedClassYgAdaMhsnyaOnly(String target_thsms, String target_kdpst, String target_shift, Vector vlist_scope_viewAbsen) {
    	//vlist_scope_viewAbsen baris format = 114 65201 MHS_ILMU_PEMERINTAHAN 114 C
    	Vector v = null;
    	if(target_shift.equalsIgnoreCase("all")) {
    		//System.out.println("1");
    		if(target_kdpst.equalsIgnoreCase("all")) {
    			//System.out.println("1A");
    			v = getListOpenedClassYgAdaMhsnyaOnly(target_thsms, vlist_scope_viewAbsen);
    		}
    		else {
    			//System.out.println("1B");
    			v = getListOpenedClassYgAdaMhsnyaOnly(target_thsms, target_kdpst, vlist_scope_viewAbsen);
    		}
    	}
    	else {
    		//System.out.println("2");
    		if(target_kdpst.equalsIgnoreCase("all")) {
    			//System.out.println("2A");
    			v = getListOpenedClassYgAdaMhsnyaOnly(target_thsms, vlist_scope_viewAbsen, target_shift);
    		}
    		else {
    			//System.out.println("2B");
    			v = getListOpenedClassYgAdaMhsnyaOnly(target_thsms, target_kdpst, vlist_scope_viewAbsen);
    			//li.add(idkur+"`"+idkmk+"`"+kdpst+"`"+shift+"`"+noKlsPll+"`"+npm_pertama_input+"`"+npm_terakhir_updat+"`"+status_akhir+"`"+curr_avail_status+"`"+locked_or_editable+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+canceled+"`"+kode_kelas+"`"+kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
    			ListIterator li =v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				if(!brs.contains("`"+target_shift+"`")) {
    					li.remove();
    				}
    			}
    			
    		}
    	}
    	return v;
    }
    
    
    //ganti ke SearchDbManiNotification. listProdiYgBelumMengajukanKelasPerkuliahan_v1 
    public Vector listProdiYgBelumMengajukanKelasPerkuliahan_deprecated(Vector v_scope_id, String target_thsms) {
    	//Vector v = null;
    	/*
    	 *  vScopeUa = scope pengajuan = owner & monitoree
    	 *  vScopeUaA = scope approval
    	 */
    	Vector vf = null;
    	Vector v_scope_kdpst = null;
    	Vector v_list_yg_blm = null;;
    	String thsms_buka_kls = Checker.getThsmsBukaKelas();
    	boolean ada =false;
    	ListIterator li = null;
    	ListIterator li1 = null;
    	ListIterator lif = null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		li1 = v_scope_id.listIterator();
    		v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
    		try {

        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			String addon_cmd = "";
    			
    			//cek sebagai yg punya scope
    			Vector v_kdpst_kmp = new Vector();
    			li = v_scope_kdpst.listIterator();
    			stmt = con.prepareStatement("select IDKUR from CLASS_POOL where THSMS=? and KDPST=? and KODE_KAMPUS=? and CANCELED=?");
    			boolean first = true;
    			while(li.hasNext()) {
    				
    				String brs = (String)li.next();
    				String brs1 = (String)li1.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				StringTokenizer st1 = new StringTokenizer(brs1,"`");
    				String kmp = st.nextToken();
    				String kmp1 = st1.nextToken();
    				//System.out.println("kmp1="+kmp1);
    				while(st.hasMoreTokens()) {
    					if(first) {
    						first = false;
    						vf = new Vector();
    						lif = vf.listIterator();
    					}
    					String kdpst = st.nextToken();
    					String id = st1.nextToken();
    					//System.out.println(kdpst+"`"+id);

    					stmt.setString(1,target_thsms);
    					stmt.setString(2,kdpst);
    					stmt.setString(3,kmp);
    					stmt.setBoolean(4, false);
    	    			rs = stmt.executeQuery();
    	    			if(!rs.next()) {
    	    				//lif.add(kmp+"`"+kdpst+"`"+nmpst+"`"+kdjen+"`"+id);
    	    				//System.out.println("nggada="+kmp+"`"+kdpst+"`"+id);
    	    				lif.add(kmp+"`"+kdpst+"`"+id);
    	    			}
    	    			else {
    	    				//System.out.println("ada="+kmp+"`"+kdpst+"`"+id);
    	    			}
    				}
    			}
    			//System.out.println("vf1="+vf.size());
    			if(vf!=null && vf.size()>0) {
    				
    				lif = vf.listIterator();
    				while(lif.hasNext()) {
    					String brs = (String)lif.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String kmp = st.nextToken();
    					String kdpst = st.nextToken();
    					String kdjen = Checker.getKdjen(kdpst);
    					String nmpst = Converter.getNamaKdpst(kdpst);
    					String id = st.nextToken();
    					lif.set(kmp+"`"+kdpst+"`"+nmpst+"`"+kdjen+"`"+id);
    				}
    				//filter yg memang tidak ada perkuliahan data berdasarkan table overvie
    				lif = vf.listIterator();
    				stmt = con.prepareStatement("select TIDAK_ADA_PERKULIAHAN from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
    				while(lif.hasNext()) {
    					String brs = (String)lif.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String kmp = st.nextToken();
    					String kdpst = st.nextToken();
    					String kdjen = st.nextToken();
    					String nmpst = st.nextToken();
    					String id = st.nextToken();
    					stmt.setInt(1, Integer.parseInt(id));
    					stmt.setString(2, target_thsms);
    					rs = stmt.executeQuery();
    					rs.next();
    					boolean no_perkuliahan = rs.getBoolean(1);
    					if(no_perkuliahan) {
    						lif.remove();
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
    	//System.out.println("vf="+vf.size());
    	return vf;
    }

    public Vector getAStatusPengajuanKelas(Vector vreturnScopeProdiOnlySortByKampusWithListIdobj, String target_thsms) {
    	//Vector v = null;
    	/*
    	 *  vScopeUa = scope pengajuan = owner & monitoree
    	 *  vScopeUaA = scope approval
    	 */
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	Vector v_scope_id_combine = null;
    	Vector v_approval = null;
    	//Vector vf = null;
    	if(vreturnScopeProdiOnlySortByKampusWithListIdobj!=null && vreturnScopeProdiOnlySortByKampusWithListIdobj.size()>0) {
			lif = vreturnScopeProdiOnlySortByKampusWithListIdobj.listIterator();
			v_scope_id_combine = (Vector)lif.next();
			v_approval = (Vector)lif.next();

		}
    	if(v_scope_id_combine!=null && v_scope_id_combine.size()>0) {
    		//System.out.println("kesini");
    		lif = vf.listIterator();
    		Vector v_scope_kdpst_combine = Converter.convertVscopeidToKdpst(v_scope_id_combine);
    		try {

        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			String addon_cmd = "";
    			
    			//cek pengajuan yg ada di class pool sesuaio scope
    			
    			ListIterator li = v_scope_kdpst_combine.listIterator();
    			ListIterator li1 = v_scope_id_combine.listIterator();
    			stmt = con.prepareStatement("select * from CLASS_POOL where THSMS=? and KDPST=? and KODE_KAMPUS=? and CANCELED=? limit 1");
    			while(li.hasNext()) {
    				String baris = (String)li.next();
    				String baris1 = (String)li1.next();
    				StringTokenizer st = new StringTokenizer(baris,"`");
    				StringTokenizer st1 = new StringTokenizer(baris1,"`");
    				String kmp = st.nextToken();
    				String kmp1 = st1.nextToken();
    				while(st.hasMoreTokens()) {
    					String kdpst = st.nextToken();
    					String id = st1.nextToken();
    					stmt.setString(1, target_thsms);
    					stmt.setString(2, kdpst);
    					stmt.setString(3, kmp);
    					stmt.setBoolean(4,false);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						String locked = ""+rs.getBoolean("LOCKED");
    						String passed = ""+rs.getBoolean("PASSED");
    						String reject = ""+rs.getBoolean("REJECTED");
    						lif.add(id+"`"+kdpst+"`"+kmp+"`"+locked+"`"+passed+"`"+reject);
    						//System.out.println("brs1=="+id+"`"+kdpst+"`"+kmp+"`"+locked+"`"+passed+"`"+reject);
    					}
    				}
    				//System.out.println(baris);
    			}
    			if(vf.size()>0) {
    				//cek rules
    				//System.out.println("kesini2");
    				stmt = con.prepareStatement("select * from KELAS_PERKULIAHAN_RULES where THSMS=? and KDPST=? and KODE_KAMPUS=?");
    				Vector v_rule = new Vector();
    				ListIterator lit = v_rule.listIterator();
    				lif = vf.listIterator();
    				while(lif.hasNext()) {
    					String brs = (String)lif.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String id = st.nextToken();
    					String kdpst = st.nextToken();
    					String kmp = st.nextToken();
    					String locked = st.nextToken();
    					String passed = st.nextToken();
    					String reject = st.nextToken();
    					stmt.setString(1, target_thsms);
    					stmt.setString(2, kdpst);
    					stmt.setString(3, kmp);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						String tkn_jab = ""+rs.getString("TKN_JABATAN_VERIFICATOR");
    						String tkn_id = ""+rs.getString("TKN_VERIFICATOR_ID");
    						String urut = ""+rs.getString("URUTAN");
    						lit.add(id+"`"+kdpst+"`"+kmp+"`"+tkn_jab+"`"+tkn_id+"`"+urut);
    						//System.out.println("brs1=="+id+"`"+kdpst+"`"+kmp+"`"+tkn_jab+"`"+tkn_id+"`"+urut);
    					}
    					else {
    						lit.add("null");
    						//System.out.println("brs1==null");
    					}
    				}
    				//cek riwayat approval
    				String temp = "";
    				stmt = con.prepareStatement("select * from CLASS_POOL_APPROVAL where THSMS=? and ID_OBJ=? order by ID");
    				lif = vf.listIterator();
    				while(lif.hasNext()) {
    					String brs = (String)lif.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String id = st.nextToken();
    					String kdpst = st.nextToken();
    					String kmp = st.nextToken();
    					String locked = st.nextToken();
    					String passed = st.nextToken();
    					String reject = st.nextToken();
    					lit = v_rule.listIterator();
						boolean match = false;
						while(lit.hasNext() && !match) {
							String baris = (String)lit.next();
							//System.out.println("brs vs baris = "+brs+" vs "+baris);
							StringTokenizer st1  = new StringTokenizer(baris,"`");
							String id1 = st1.nextToken();
							String kdpst1 = st1.nextToken();
							String kmp1 = st1.nextToken();
							String tkn_jab1 = st1.nextToken();
							String tmp = new String(tkn_jab1);
							tkn_jab1 = tkn_jab1.replace("][", "`");
							tkn_jab1 = tkn_jab1.replace("[", "`");
							tkn_jab1 = tkn_jab1.replace("]", "`");
							String tkn_id1 = st1.nextToken();
							String tmp2 = new String(tkn_id1);
							tkn_id1 = tkn_id1.replace("][", "`");
							tkn_id1 = tkn_id1.replace("[", "`");
							tkn_id1 = tkn_id1.replace("]", "`");
							String urut1 = st1.nextToken();
							if(id.equalsIgnoreCase(id1)) {
								match = true;
								StringTokenizer st2 = new StringTokenizer(tkn_jab1,"`");
								//get first approal
								String job = st2.nextToken();
								st2 = new StringTokenizer(tkn_id1,"`");
								String objid = st2.nextToken();
								temp = brs+"`"+tmp+"`"+tmp2+"`"+job+"`"+objid;
								//lif.set(brs+"`"+tmp+"`"+tmp2+"`"+job+"`"+objid);
							}
							
						}
						if(!match) {
							temp = brs+"`null`null`null`null";
							//lif.set(brs+"`null`null`null`null");
						}
    					
    					stmt.setString(1, target_thsms);
    					stmt.setInt(2, Integer.parseInt(id));
    					rs = stmt.executeQuery();
    					if(!rs.next()) {
    						//belum pernah ada approval == cek approval pertama
    						//return String
    						/*
    						lit = v_rule.listIterator();
    						boolean match = false;
    						while(lit.hasNext() && !match) {
    							String baris = (String)lit.next();
    							//System.out.println("brs vs baris = "+brs+" vs "+baris);
    							StringTokenizer st1  = new StringTokenizer(baris,"`");
    							String id1 = st1.nextToken();
    							String kdpst1 = st1.nextToken();
    							String kmp1 = st1.nextToken();
    							String tkn_jab1 = st1.nextToken();
    							String tmp = new String(tkn_jab1);
    							tkn_jab1 = tkn_jab1.replace("][", "`");
    							tkn_jab1 = tkn_jab1.replace("[", "`");
    							tkn_jab1 = tkn_jab1.replace("]", "`");
    							String tkn_id1 = st1.nextToken();
    							String tmp2 = new String(tkn_id1);
    							tkn_id1 = tkn_id1.replace("][", "`");
    							tkn_id1 = tkn_id1.replace("[", "`");
    							tkn_id1 = tkn_id1.replace("]", "`");
    							String urut1 = st1.nextToken();
    							if(id.equalsIgnoreCase(id1)) {
    								match = true;
    								StringTokenizer st2 = new StringTokenizer(tkn_jab1,"`");
    								//get first approal
    								String job = st2.nextToken();
    								st2 = new StringTokenizer(tkn_id1,"`");
    								String objid = st2.nextToken();
    								lif.set(brs+"`"+tmp+"`"+tmp2+"`"+job+"`"+objid);
    							}
    							
    						}
    						if(!match) {
    							lif.set(brs+"`null`null`null`null");
    						}
    						*/
    						lif.set(temp);
    					}
    					else {
    						//sudah ada record retunv VECTOR bukan spt format dibawah ini
    						//lif.set(brs+"`null`null`null`null");
    						Vector vtemp = new Vector();
    						ListIterator litmp = vtemp.listIterator();
    						litmp.add(temp);//baris pertama info dari class_pool
    						//baris berikutnya dari riwayat class pool approval
    						
    						do {
    							String id_row = ""+rs.getLong("ID");
    							String commen = ""+rs.getString("COMMENT");
    							if(Checker.isStringNullOrEmpty(commen)) {
    								commen = new String("null");
    							}
    							String approved = ""+rs.getBoolean("APPROVED"); //nilai akhir ini harus sama dengan nilai dari class pool pada baris pertama
    							String cur_id = ""+rs.getInt("CURRENT_ID_APPROVAL");
    							String cur_job= ""+rs.getString("CURRENT_JABATAN_APPROVAL");
    							String next_id= ""+rs.getInt("ID_APPROVAL_NEEDED");
    							String next_job= ""+rs.getString("JABATAN_APPROVAL_NEEDED");
    							String done = ""+rs.getBoolean("ALL_APPROVED");
    							String updtm = ""+rs.getTimestamp("UPDTM");
    							litmp.add(id_row+"`"+commen+"`"+approved+"`"+cur_id+"`"+cur_job+"`"+next_id+"`"+next_job+"`"+done+"`"+updtm);
    						}
    						while(rs.next());
    						lif.set(vtemp);
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
    	//System.out.println("vf="+vf.size());
    	return vf;
    }
 
    public Vector getInfoListKelasYgDiajukan(String thsms, String kdpst, String kode_kmp) {
    	Vector vf = null;
    	ListIterator lif = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//stmt = con.prepareStatement("select * from CLASS_POOL inner join KRKLM on IDKUR=IDKURKRKLM where THSMS=? and CANCELED=? order by KDPST");
		    //get kelas gabungan
			stmt = con.prepareStatement("select distinct KODE_PENGGABUNGAN from CLASS_POOL where THSMS=? and KDPST=? and KODE_KAMPUS=?");
			stmt.setString(1, thsms);
			stmt.setString(2, kdpst);
			stmt.setString(3, kode_kmp);
			rs = stmt.executeQuery();
			String list_kode_gab = null;
			if(rs.next()) {
				list_kode_gab = new String();
				do {
					list_kode_gab = list_kode_gab+"`"+rs.getString("KODE_PENGGABUNGAN"); 
				}
				while(rs.next());
			}
			if(list_kode_gab!=null) {//berarti ada kelas gabungan
				if(vf==null) {
					vf = new Vector();
					lif = vf.listIterator();
				}
				
				StringTokenizer st = new StringTokenizer(list_kode_gab,"`");
				stmt = con.prepareStatement("select * from CLASS_POOL inner join KRKLM on IDKUR=IDKURKRKLM where THSMS=? and KODE_PENGGABUNGAN=? order by CANCELED");
				while(st.hasMoreTokens()) {
					String kd_gab = st.nextToken();
					stmt.setString(1, thsms);
					stmt.setString(2, kd_gab);
					rs = stmt.executeQuery();
					boolean first = true;
					String tmp = "";
					while(rs.next()) {
						String idkur = ""+rs.getLong("IDKUR");
			    		String idkmk = ""+rs.getLong("IDKMK");
			    		//String thsms = ""+rs.getString("THSMS");
			    		//String kdpst = ""+rs.getString("KDPST");
			    		String shift = ""+rs.getString("SHIFT");
			    		String noKlsPll = ""+rs.getInt("NORUT_KELAS_PARALEL");
			    		String initNpmInput = ""+rs.getString("INIT_NPM_INPUT");
			    		//String latestNpmUpdate = ""+rs.getString("LATEST_NPM_UPDATE");
			    		//String latestStatusInfo = ""+rs.getString("LATEST_STATUS_INFO");
			    		String locked = ""+rs.getBoolean("LOCKED");
			    		String npmdos = ""+rs.getString("NPMDOS");
			    		String nodos = ""+rs.getString("NODOS");
			    		String npmasdos = ""+rs.getString("NPMASDOS");
			    		String noasdos = ""+rs.getString("NOASDOS");
			    		String cancel = ""+rs.getBoolean("CANCELED");
			    		String kodeKelas = ""+rs.getString("KODE_KELAS");
			    		String kodeRuang = ""+rs.getString("KODE_RUANG");
			    		String kodeGedung = ""+rs.getString("KODE_GEDUNG");
			    		String kodeKampus = ""+rs.getString("KODE_KAMPUS");
			    		String tknHrTime = ""+rs.getString("TKN_HARI_TIME");
			    		String nmmdos = ""+rs.getString("NMMDOS");
			    		String nmmasdos = ""+rs.getString("NMMASDOS");
			    		String enrolled = ""+rs.getInt("ENROLLED");
			    		String maxEnrol = ""+rs.getInt("MAX_ENROLLED");
			    		String minEnrol = ""+rs.getInt("MIN_ENROLLED");
			    		String subKeterMk = ""+rs.getString("SUB_KETER_KDKMK");
			    		String initReqTime=""+rs.getTimestamp("INIT_REQ_TIME");
			    		//String tknNpmApproval = ""+rs.getString("TKN_NPM_APPROVAL");
			    		//String tknApprovalTime = ""+rs.getString("TKN_APPROVAL_TIME");
			    		String targetTotMhs = ""+rs.getInt("TARGET_TTMHS");
			    		String passed = ""+rs.getBoolean("PASSED");
			    		String rejected = ""+rs.getBoolean("REJECTED");
			    		String konsen = ""+rs.getString("KONSENTRASI");
			    		String kode_gabung = ""+rs.getString("KODE_PENGGABUNGAN");
			    		//String kode_gabung_univ = ""+rs.getString("KODE_GABUNGAN_UNIV");
			    		String cuid = ""+rs.getLong("UNIQUE_ID");
						//String tmp = idkur+"||"+idkmk+"||"+thsms+"||"+kdpst+"||"+shift+"||"+noKlsPll+"||"+initNpmInput+"||"+latestNpmUpdate+"||"+latestStatusInfo+"||"+locked+"||"+npmdos+"||"+nodos+"||"+npmasdos+"||"+noasdos+"||"+cancel+"||"+kodeKelas+"||"+kodeRuang+"||"+kodeGedung+"||"+kodeKampus+"||"+tknHrTime+"||"+nmmdos+"||"+nmmasdos+"||"+enrolled+"||"+maxEnrol+"||"+minEnrol+"||"+subKeterMk+"||"+initReqTime+"||"+tknNpmApproval+"||"+tknApprovalTime+"||"+targetTotMhs+"||"+passed+"||"+rejected+"||"+konsen;
			    		if(first) {
			    			first = false;
			    			tmp = idkur+"||"+idkmk+"||"+thsms+"||"+kdpst+"||"+shift+"||"+noKlsPll+"||"+initNpmInput+"||"+locked+"||"+npmdos+"||"+nodos+"||"+npmasdos+"||"+noasdos+"||"+cancel+"||"+kodeKelas+"||"+kodeRuang+"||"+kodeGedung+"||"+kodeKampus+"||"+tknHrTime+"||"+nmmdos+"||"+nmmasdos+"||"+enrolled+"||"+maxEnrol+"||"+minEnrol+"||"+subKeterMk+"||"+initReqTime+"||"+targetTotMhs+"||"+passed+"||"+rejected+"||"+konsen+"||"+kode_gabung+"||"+cuid;
			    		}
			    		else {
			    			tmp = tmp+"`"+idkur+"||"+idkmk+"||"+thsms+"||"+kdpst+"||"+shift+"||"+noKlsPll+"||"+initNpmInput+"||"+locked+"||"+npmdos+"||"+nodos+"||"+npmasdos+"||"+noasdos+"||"+cancel+"||"+kodeKelas+"||"+kodeRuang+"||"+kodeGedung+"||"+kodeKampus+"||"+tknHrTime+"||"+nmmdos+"||"+nmmasdos+"||"+enrolled+"||"+maxEnrol+"||"+minEnrol+"||"+subKeterMk+"||"+initReqTime+"||"+targetTotMhs+"||"+passed+"||"+rejected+"||"+konsen+"||"+kode_gabung+"||"+cuid;
			    		}
					}
					//System.out.println("add="+tmp);
					if(!Checker.isStringNullOrEmpty(tmp)) {
						lif.add(tmp);	
					}
					
				}
			}
			
			stmt = con.prepareStatement("select * from CLASS_POOL inner join KRKLM on IDKUR=IDKURKRKLM where THSMS=? and KDPST=? and KODE_KAMPUS=? and KODE_PENGGABUNGAN is NULL");
			stmt.setString(1, thsms);
			stmt.setString(2, kdpst);
			stmt.setString(3, kode_kmp);
			rs = stmt.executeQuery();
			if(rs.next()) {
				if(vf==null) {
					vf = new Vector();
					lif = vf.listIterator();
				}
				
				do {
					String idkur = ""+rs.getLong("IDKUR");
		    		String idkmk = ""+rs.getLong("IDKMK");
		    		//String thsms = ""+rs.getString("THSMS");
		    		//String kdpst = ""+rs.getString("KDPST");
		    		String shift = ""+rs.getString("SHIFT");
		    		String noKlsPll = ""+rs.getInt("NORUT_KELAS_PARALEL");
		    		String initNpmInput = ""+rs.getString("INIT_NPM_INPUT");
		    		//String latestNpmUpdate = ""+rs.getString("LATEST_NPM_UPDATE");
		    		//String latestStatusInfo = ""+rs.getString("LATEST_STATUS_INFO");
		    		String locked = ""+rs.getBoolean("LOCKED");
		    		String npmdos = ""+rs.getString("NPMDOS");
		    		String nodos = ""+rs.getString("NODOS");
		    		String npmasdos = ""+rs.getString("NPMASDOS");
		    		String noasdos = ""+rs.getString("NOASDOS");
		    		String cancel = ""+rs.getBoolean("CANCELED");
		    		String kodeKelas = ""+rs.getString("KODE_KELAS");
		    		String kodeRuang = ""+rs.getString("KODE_RUANG");
		    		String kodeGedung = ""+rs.getString("KODE_GEDUNG");
		    		String kodeKampus = ""+rs.getString("KODE_KAMPUS");
		    		String tknHrTime = ""+rs.getString("TKN_HARI_TIME");
		    		String nmmdos = ""+rs.getString("NMMDOS");
		    		String nmmasdos = ""+rs.getString("NMMASDOS");
		    		String enrolled = ""+rs.getInt("ENROLLED");
		    		String maxEnrol = ""+rs.getInt("MAX_ENROLLED");
		    		String minEnrol = ""+rs.getInt("MIN_ENROLLED");
		    		String subKeterMk = ""+rs.getString("SUB_KETER_KDKMK");
		    		String initReqTime=""+rs.getTimestamp("INIT_REQ_TIME");
		    		//String tknNpmApproval = ""+rs.getString("TKN_NPM_APPROVAL");
		    		//String tknApprovalTime = ""+rs.getString("TKN_APPROVAL_TIME");
		    		String targetTotMhs = ""+rs.getInt("TARGET_TTMHS");
		    		String passed = ""+rs.getBoolean("PASSED");
		    		String rejected = ""+rs.getBoolean("REJECTED");
		    		String konsen = ""+rs.getString("KONSENTRASI");
		    		String kode_gabung = ""+rs.getString("KODE_PENGGABUNGAN");
		    		//String kode_gabung_univ = ""+rs.getString("KODE_GABUNGAN_UNIV");
		    		String cuid = ""+rs.getLong("UNIQUE_ID");
					//String tmp = idkur+"||"+idkmk+"||"+thsms+"||"+kdpst+"||"+shift+"||"+noKlsPll+"||"+initNpmInput+"||"+latestNpmUpdate+"||"+latestStatusInfo+"||"+locked+"||"+npmdos+"||"+nodos+"||"+npmasdos+"||"+noasdos+"||"+cancel+"||"+kodeKelas+"||"+kodeRuang+"||"+kodeGedung+"||"+kodeKampus+"||"+tknHrTime+"||"+nmmdos+"||"+nmmasdos+"||"+enrolled+"||"+maxEnrol+"||"+minEnrol+"||"+subKeterMk+"||"+initReqTime+"||"+tknNpmApproval+"||"+tknApprovalTime+"||"+targetTotMhs+"||"+passed+"||"+rejected+"||"+konsen;
		    		String tmp = idkur+"||"+idkmk+"||"+thsms+"||"+kdpst+"||"+shift+"||"+noKlsPll+"||"+initNpmInput+"||"+locked+"||"+npmdos+"||"+nodos+"||"+npmasdos+"||"+noasdos+"||"+cancel+"||"+kodeKelas+"||"+kodeRuang+"||"+kodeGedung+"||"+kodeKampus+"||"+tknHrTime+"||"+nmmdos+"||"+nmmasdos+"||"+enrolled+"||"+maxEnrol+"||"+minEnrol+"||"+subKeterMk+"||"+initReqTime+"||"+targetTotMhs+"||"+passed+"||"+rejected+"||"+konsen+"||"+kode_gabung+"||"+cuid;
		    		if(!Checker.isStringNullOrEmpty(tmp)) {
						lif.add(tmp);	
					}

				}
				while(rs.next());
				
			}
			//add nama mk
			if(vf!=null && vf.size()>0) {
				stmt = con.prepareStatement("select KDKMKMAKUL,NAKMKMAKUL,SKSTMMAKUL,SKSPRMAKUL,SKSLPMAKUL,JENISMAKUL,STKMKMAKUL,OPTKETER,KDKMK_AT_PDPT,ID_MAKUL_SAMA from MAKUL where IDKMKMAKUL=?");
				lif = vf.listIterator();
				while(lif.hasNext()) {
					String brs = (String)lif.next();
					
					//cek apa kelas gabungan
					if(brs.contains("`")) { //berarti kelas gabungan
						//System.out.println("kelas gabungan");
						StringTokenizer st = new StringTokenizer(brs,"`");
						String tmp = "";
						while(st.hasMoreTokens()) {
							String brs1 = (String)st.nextToken();
							StringTokenizer st1 = new StringTokenizer(brs1,"||");
							String idkur = st1.nextToken();
							String idkmk = st1.nextToken();
							thsms = st1.nextToken();
							kdpst = st1.nextToken();
							String shift = st1.nextToken();
							String noKlsPll = st1.nextToken();
							String initNpmInput = st1.nextToken();
							String locked = st1.nextToken();
							String npmdos = st1.nextToken();
							String nodos = st1.nextToken();
							String npmasdos = st1.nextToken();
							String noasdos = st1.nextToken();
							String cancel = st1.nextToken();
							String kodeKelas = st1.nextToken();
							String kodeRuang = st1.nextToken();
							String kodeGedung = st1.nextToken();
							String kodeKampus = st1.nextToken();
							String tknHrTime = st1.nextToken();
							String nmmdos = st1.nextToken();
							String nmmasdos = st1.nextToken();
							String enrolled = st1.nextToken();
							String maxEnrol = st1.nextToken();
							String minEnrol = st1.nextToken();
							String subKeterMk = st1.nextToken();
							String initReqTime = st1.nextToken();
							String targetTotMhs = st1.nextToken();
							String passed = st1.nextToken();
							String rejected = st1.nextToken();
							String konsen = st1.nextToken();
							String kode_gabung = st1.nextToken();
							String cuid = st1.nextToken();
							stmt.setInt(1, Integer.parseInt(idkmk));
							rs = stmt.executeQuery();
							rs.next();
							String kdkmk = ""+rs.getString("KDKMKMAKUL");
							String nakmk = ""+rs.getString("NAKMKMAKUL");
							int skstm = rs.getInt("SKSTMMAKUL");
							int skspr = rs.getInt("SKSPRMAKUL");
							int skslp = rs.getInt("SKSLPMAKUL");
							int skstt = skstm+skspr+skslp;
							String jenis = ""+rs.getString("JENISMAKUL");
							String stkmk = ""+rs.getString("STKMKMAKUL");
							String opt_keter = ""+rs.getString("OPTKETER");
							String kode_at_pdpt = ""+rs.getString("KDKMK_AT_PDPT");
							String id_makul_sama = ""+rs.getString("ID_MAKUL_SAMA");
//							+"||"+skstm+"||"+skspr+"||"+skslp+"||"+skstt+"||"+jenis+"||"+stkmk+"||"+opt_keter+"||"+kode_at_pdpt+"||"+id_makul_sama
							tmp = tmp+nakmk+"||"+kdkmk+"||"+idkur+"||"+idkmk+"||"+thsms+"||"+kdpst+"||"+shift+"||"+noKlsPll+"||"+initNpmInput+"||"+locked+"||"+npmdos+"||"+nodos+"||"+npmasdos+"||"+noasdos+"||"+cancel+"||"+kodeKelas+"||"+kodeRuang+"||"+kodeGedung+"||"+kodeKampus+"||"+tknHrTime+"||"+nmmdos+"||"+nmmasdos+"||"+enrolled+"||"+maxEnrol+"||"+minEnrol+"||"+subKeterMk+"||"+initReqTime+"||"+targetTotMhs+"||"+passed+"||"+rejected+"||"+konsen+"||"+kode_gabung+"||"+cuid+"||"+skstm+"||"+skspr+"||"+skslp+"||"+skstt+"||"+jenis+"||"+stkmk+"||"+opt_keter+"||"+kode_at_pdpt+"||"+id_makul_sama;
							tmp = tmp.replace("|| ||", "||null||");
							tmp = tmp.replace("||||", "||null||");
							if(st.hasMoreTokens()) {
								tmp = tmp + "`";
							}
						}
						//System.out.println(x);
						lif.set(tmp);
					}
					else {
						//single class / bukan kelas gabungan
						//System.out.println("kelas single");
						StringTokenizer st1 = new StringTokenizer(brs,"||");
						String idkur = st1.nextToken();
						String idkmk = st1.nextToken();
						thsms = st1.nextToken();
						kdpst = st1.nextToken();
						String shift = st1.nextToken();
						String noKlsPll = st1.nextToken();
						String initNpmInput = st1.nextToken();
						String locked = st1.nextToken();
						String npmdos = st1.nextToken();
						String nodos = st1.nextToken();
						String npmasdos = st1.nextToken();
						String noasdos = st1.nextToken();
						String cancel = st1.nextToken();
						String kodeKelas = st1.nextToken();
						String kodeRuang = st1.nextToken();
						String kodeGedung = st1.nextToken();
						String kodeKampus = st1.nextToken();
						String tknHrTime = st1.nextToken();
						String nmmdos = st1.nextToken();
						String nmmasdos = st1.nextToken();
						String enrolled = st1.nextToken();
						String maxEnrol = st1.nextToken();
						String minEnrol = st1.nextToken();
						String subKeterMk = st1.nextToken();
						String initReqTime = st1.nextToken();
						String targetTotMhs = st1.nextToken();
						String passed = st1.nextToken();
						String rejected = st1.nextToken();
						String konsen = st1.nextToken();
						String kode_gabung = st1.nextToken();
						String cuid = st1.nextToken();
						stmt.setInt(1, Integer.parseInt(idkmk));
						rs = stmt.executeQuery();
						rs.next();
						String kdkmk = ""+rs.getString("KDKMKMAKUL");
						String nakmk = ""+rs.getString("NAKMKMAKUL");
						int skstm = rs.getInt("SKSTMMAKUL");
						int skspr = rs.getInt("SKSPRMAKUL");
						int skslp = rs.getInt("SKSLPMAKUL");
						int skstt = skstm+skspr+skslp;
						String jenis = ""+rs.getString("JENISMAKUL");
						String stkmk = ""+rs.getString("STKMKMAKUL");
						String opt_keter = ""+rs.getString("OPTKETER");
						String kode_at_pdpt = ""+rs.getString("KDKMK_AT_PDPT");
						String id_makul_sama = ""+rs.getString("ID_MAKUL_SAMA");
//						+"||"+skstm+"||"+skspr+"||"+skslp+"||"+skstt+"||"+jenis+"||"+stkmk+"||"+opt_keter+"||"+kode_at_pdpt+"||"+id_makul_sama
						String tmp = nakmk+"||"+kdkmk+"||"+idkur+"||"+idkmk+"||"+thsms+"||"+kdpst+"||"+shift+"||"+noKlsPll+"||"+initNpmInput+"||"+locked+"||"+npmdos+"||"+nodos+"||"+npmasdos+"||"+noasdos+"||"+cancel+"||"+kodeKelas+"||"+kodeRuang+"||"+kodeGedung+"||"+kodeKampus+"||"+tknHrTime+"||"+nmmdos+"||"+nmmasdos+"||"+enrolled+"||"+maxEnrol+"||"+minEnrol+"||"+subKeterMk+"||"+initReqTime+"||"+targetTotMhs+"||"+passed+"||"+rejected+"||"+konsen+"||"+kode_gabung+"||"+cuid+"||"+skstm+"||"+skspr+"||"+skslp+"||"+skstt+"||"+jenis+"||"+stkmk+"||"+opt_keter+"||"+kode_at_pdpt+"||"+id_makul_sama;
						tmp = tmp.replace("|| ||", "||null||");
						tmp = tmp.replace("||||", "||null||");
						lif.set(tmp);
					}
				}
			}
    		if(vf!=null && vf.size()>0) {
    			Collections.sort(vf);
    			//get jumlah mhs
    			lif = vf.listIterator();
    			stmt = con.prepareStatement("select count(DISTINCT NPMHSTRNLM) from TRNLM where THSMSTRNLM=? and CLASS_POOL_UNIQUE_ID=?");
    			while(lif.hasNext()) {
    				String brs = (String)lif.next();
    				StringTokenizer st = new StringTokenizer(brs,"||");
    				String nakmk = st.nextToken();
    				String kdkmk = st.nextToken();
    				String idkur = st.nextToken();
					String idkmk = st.nextToken();
					thsms = st.nextToken();
					kdpst = st.nextToken();
					String shift = st.nextToken();
					String noKlsPll = st.nextToken();
					String initNpmInput = st.nextToken();
					String locked = st.nextToken();
					String npmdos = st.nextToken();
					String nodos = st.nextToken();
					String npmasdos = st.nextToken();
					String noasdos = st.nextToken();
					String cancel = st.nextToken();
					String kodeKelas = st.nextToken();
					String kodeRuang = st.nextToken();
					String kodeGedung = st.nextToken();
					String kodeKampus = st.nextToken();
					String tknHrTime = st.nextToken();
					String nmmdos = st.nextToken();
					String nmmasdos = st.nextToken();
					String enrolled = st.nextToken();
					String maxEnrol = st.nextToken();
					String minEnrol = st.nextToken();
					String subKeterMk = st.nextToken();
					String initReqTime = st.nextToken();
					String targetTotMhs = st.nextToken();
					String passed = st.nextToken();
					String rejected = st.nextToken();
					String konsen = st.nextToken();
					String kode_gabung = st.nextToken();
					String cuid = st.nextToken();
					    				stmt.setString(1, thsms);
    				stmt.setLong(2, Long.parseLong(cuid));
    				rs = stmt.executeQuery();
    				int tot_mhs = 0;
    				if(rs.next()) {
    					tot_mhs = rs.getInt(1);
        				//lif.set(tot_mhs+"||"+brs);
    				}
    				//else {
    				lif.set(tot_mhs+"||"+brs);
    				//}
    				//System.out.println(brs);
					//System.out.println(nakmk+"="+idkur+"="+idkmk+"="+cuid+"=="+tot_mhs);
					//System.out.println("set = "+);
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
    	//System.out.println("done");
    	return vf;
    }
    
    public Vector getKelasYgMasihAdaNilaiTunda(String targetThsms, Vector v_list_scope_obj_id) {
    	Vector v = getListOpenedClassYgAdaMhsnyaOnly(targetThsms, v_list_scope_obj_id);
    	if(v!=null) {
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select NPMHSTRNLM from TRNLM where CLASS_POOL_UNIQUE_ID=? and NLAKHTRNLM=? limit 1");
    			
    			ListIterator li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String idkur = st.nextToken();
    				String idkmk = st.nextToken();
    				String kdpst = st.nextToken();
    				String shift = st.nextToken();
    				String noKlsPll = st.nextToken();
    				String npm_pertama_input = st.nextToken();
    				String npm_terakhir_updat = st.nextToken();
    				String status_akhir = st.nextToken();
    				String curr_avail_status = st.nextToken();
    				String locked_or_editable = st.nextToken();
    				String npmdos = st.nextToken();
    				String nodos = st.nextToken();
    				String npmasdos = st.nextToken();
    				String noasdos = st.nextToken();
    				String canceled = st.nextToken();
    				String kode_kelas = st.nextToken();
    				String kode_ruang = st.nextToken();
    				String kode_gedung = st.nextToken();
    				String kode_kampus = st.nextToken();
    				String tkn_day_time = st.nextToken();
    				String nmmdos = st.nextToken();
    				String nmmasdos = st.nextToken();
    				String enrolled = st.nextToken();
    				String max_enrolled = st.nextToken();
    				String min_enrolled = st.nextToken();
    				String sub_keter_kdkmk = st.nextToken();
    				String init_req_time = st.nextToken();
    				String tkn_npm_approval = st.nextToken();
    				String tkn_approval_time = st.nextToken();
    				String target_ttmhs = st.nextToken();
    				String passed = st.nextToken();
    				String rejected = st.nextToken();
    				String kode_gabung_kls = st.nextToken();
    				String kode_gabung_kmp = st.nextToken();
    				String unique_id = st.nextToken();
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String skstm = st.nextToken();
    				String skspr = st.nextToken();
    				String skslp = st.nextToken();
    				stmt.setLong(1, Long.parseLong(unique_id));
    				stmt.setString(2, "T");
    				rs = stmt.executeQuery();
    				if(!rs.next()) {
    					li.remove();
    				}
    				else {
    					//re arranged sesuai dengan /ToUnivSatyagama/WebContent/InnerFrame/Perkuliahan/Nilai/dashPenilaian.jsp
    					li.set(kdpst+"`"+
    					nakmk+"`"+
    					idkur+"`"+
    					idkmk+"`"+
    					shift+"`"+
    					targetThsms+"`"+
    					noKlsPll+"`"+
    					npm_pertama_input+"`"+
    					npm_terakhir_updat+"`"+
    					status_akhir+"`"+
    					curr_avail_status+"`"+
    					locked_or_editable+"`"+
    					npmdos+"`"+
    					nodos+"`"+
    					npmasdos+"`"+
    					noasdos+"`"+
    					canceled+"`"+
    					kode_kelas+"`"+
    					kode_ruang+"`"+
    					kode_gedung+"`"+
    					kode_kampus+"`"+
    					tkn_day_time+"`"+
    					nmmdos+"`"+
    					nmmasdos+"`"+
    					enrolled+"`"+
    					max_enrolled+"`"+
    					min_enrolled+"`"+
    					sub_keter_kdkmk+"`"+
    					init_req_time+"`"+
    					tkn_npm_approval+"`"+
    					tkn_approval_time+"`"+
    					target_ttmhs+"`"+
    					passed+"`"+
    					rejected+"`"+
    					kode_gabung_kls+"`"+
    					kode_gabung_kmp+"`"+
    					unique_id+"`"+
    					kdkmk);
    					//konsen ditambah pas di jsp aja
    				}
    			}
    			if(v!=null) {
    				Collections.sort(v);
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
    	return v;
    }
    
    public Vector getKelasYgMasihAdaNilaiTunda_v1(String startingThsms, Vector v_scope_kdpst, int sta_limit, int range_limit) {
    	Vector v = null;
    	if(v_scope_kdpst!=null && v_scope_kdpst.size()>0) {
    		String scope_kdpst = "(";
    		ListIterator li = v_scope_kdpst.listIterator();
    		while(li.hasNext()) {
    			String kdpst = (String)li.next();
    			scope_kdpst=scope_kdpst+"A.KDPST='"+kdpst+"'";
    			if(li.hasNext()) {
    				scope_kdpst=scope_kdpst+" OR ";
    			}
    		}
    		scope_kdpst=scope_kdpst+")";
    		String sql = "select DISTINCT A.THSMS,A.KDPST,D.NMPSTMSPST,D.KODE_JENJANG,B.KDKMKMAKUL,B.NAKMKMAKUL,A.SHIFT,A.NMMDOS,A.NPMDOS,A.UNIQUE_ID,A.KODE_PENGGABUNGAN,B.IDKMKMAKUL FROM CLASS_POOL A " + 
        			"inner join MAKUL B on A.IDKMK=B.IDKMKMAKUL " + 
        			"inner join TRNLM C on A.UNIQUE_ID=C.CLASS_POOL_UNIQUE_ID " + 
        			"inner join MSPST D on A.KDPST=D.KDPSTMSPST " +
        			"where "+scope_kdpst+" and A.CANCELED=false and A.THSMS>='"+startingThsms+"' and NLAKHTRNLM='T' order by A.THSMS,B.NAKMKMAKUL,A.KDPST,A.SHIFT desc,A.KODE_PENGGABUNGAN desc limit "+sta_limit+","+range_limit;
    		//System.out.println("sql="+sql);
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement(sql);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v = new Vector();
    				li = v.listIterator();
    				do {
    					int i=1;
    					String thsms = rs.getString(i++);
    					String kdpst = rs.getString(i++);
    					String nmpst = rs.getString(i++);
    					String kdjen = rs.getString(i++);
    					String kdkmk = rs.getString(i++);
    					String nakmk = rs.getString(i++);
    					String shift = rs.getString(i++);
    					String nmdos = rs.getString(i++);
    					String npmdos = rs.getString(i++);
    					String cuid = rs.getString(i++);
    					String kode_gabung = rs.getString(i++);
    					String idkmk = rs.getString(i++);
    					String tmp = thsms+"~"+kdpst+"~"+nmpst+"~"+kdjen+"~"+kdkmk+"~"+nakmk+"~"+shift+"~"+nmdos+"~"+npmdos+"~"+cuid+"~"+kode_gabung+"~"+idkmk;
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
    		catch (Exception ex) {
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
}

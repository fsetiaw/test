package beans.dbase.makul;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;

import beans.sistem.AskSystem;
import beans.tools.*;
import beans.dbase.UpdateDb;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;
//import org.apache.commons.lang3;
/**
 * Session Bean implementation class UpdateDbMk
 */
@Stateless
@LocalBean
public class UpdateDbMk extends UpdateDb {
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
    public UpdateDbMk() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbMk(String operatorNpm) {
    	super(operatorNpm);
    	this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }

    
    public void updateClassPoolGrouping(Vector vGroup,String thsmsTarget, int noUrutKodeTerakhir) {
    	/*
    	 * if usrId < 0; berarti unverified usr jharusnya cuma pada saat login
    	 */
    	String nmmhs = null;
    	try {
    		//System.out.println("kok thsmsTarget="+thsmsTarget);
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		if(vGroup!=null && vGroup.size()>0) {
    			Vector vKode = new Vector();
    			ListIterator likd = vKode.listIterator();
    			//System.out.println("vkode size="+vKode.size());
    			//reseting kode yg ada di dbase
    			int norutKode=++noUrutKodeTerakhir;
    			stmt = con.prepareStatement("update ignore CLASS_POOL set KODE_PENGGABUNGAN=? WHERE THSMS=? AND KODE_PENGGABUNGAN=?");
    			/*
    			 * HARUSNYA NGGA MUNGKIN BISA MASUK KESINI
    			 */
    			if(vKode!=null && vKode.size()>0) {
    				likd = vKode.listIterator();
    				
    				while(likd.hasNext()) {
    					String old_kode = (String)likd.next();
    					//System.out.println("old_kode="+old_kode);
    					stmt.setString(1,""+norutKode);
    					stmt.setString(2, thsmsTarget);
    					stmt.setString(3, old_kode);
    					//cek kalo yg mo diinput mo ngikut yg udah da di db = inputnya numeric, ikut perubahanya
    					ListIterator lig = vGroup.listIterator();
    	    			//System.out.println("#size="+vGroup.size());
    					boolean match = false;
    	    			while(lig.hasNext()&&!match) {
    	    				String brs = (String)lig.next();
    	    				//System.out.println("#"+brs);
    	    				StringTokenizer st = new StringTokenizer(brs,"#");

    	    				String prev_kode = st.nextToken();
    	    				String tknInfoKelas = st.nextToken();
    	    				String prev_inti = st.nextToken();
    	    				st = new StringTokenizer(tknInfoKelas,"$");
    	    				String prev_kdpst = st.nextToken();
    	    				String prev_idkmk = st.nextToken();
    	    				String prev_shift = st.nextToken();
    	    				String prev_kllpll = st.nextToken();
    	    				String prev_idkur = st.nextToken();
    	    				String prev_cuid = st.nextToken();
    	    				if(prev_kode.equalsIgnoreCase(old_kode)) {
    	    					match = true;
    	    					lig.set(""+norutKode+"#"+tknInfoKelas+"#"+prev_inti+"#"+prev_idkur);
    	    				}
    	    			}	
    					norutKode++;
    				}
    			}
    			//=====kode diatas harusnua ngga akan pernah ke execute, KENAPA BISA ADA YA???====
    			
    			//System.out.println("LANGSUNG KE SINI norutKode="+norutKode);
    			//update data yg baru
    			//stmt = con.prepareStatement("update ignore CLASS_POOL set KODE_PENGGABUNGAN=?,CANCELED=? where THSMS=? and KDPST=? and IDKMK=? and SHIFT=? and NORUT_KELAS_PARALEL=? and IDKUR=?");
    			stmt = con.prepareStatement("update ignore CLASS_POOL set KODE_PENGGABUNGAN=?,CANCELED=? where UNIQUE_ID=?");
    			ListIterator lig = vGroup.listIterator();
    			//System.out.println("#size="+vGroup.size());
    			int k=0;
    			if(lig.hasNext()) {
    				String brs = (String)lig.next();
    				//System.out.println(++k+"#"+brs);
    				StringTokenizer st = new StringTokenizer(brs,"#");

    				String prev_kode = st.nextToken();
    				String tknInfoKelas = st.nextToken();
    				String prev_inti = st.nextToken();
    				
    				st = new StringTokenizer(tknInfoKelas,"$");
    				String prev_kdpst = st.nextToken();
    				String prev_idkmk = st.nextToken();
    				String prev_shift = st.nextToken();
    				String prev_kllpll = st.nextToken();
    				String prev_idkur = st.nextToken();
    				String prev_cuid = st.nextToken();
    				if(Checker.isNumeric(prev_kode)) {
    					stmt.setString(1, prev_kode);
    				}
    				else {
    					stmt.setString(1, ""+norutKode);
    				}	
    				if(prev_inti.equalsIgnoreCase("null")) {
    					stmt.setBoolean(2, true);
    				}
    				else {
    					stmt.setBoolean(2, false);
    				}
    				//stmt.setString(3, thsmsTarget);
    				//stmt.setString(4, prev_kdpst);
    				//stmt.setString(5, prev_idkmk);
    				//stmt.setString(6, prev_shift);
    				//stmt.setString(7, prev_kllpll);
    				//stmt.setLong(8, Long.parseLong(curr_idkur));
    				stmt.setLong(3, Long.parseLong(prev_cuid));
    				stmt.executeUpdate();
    				while(lig.hasNext()) {
    					brs = (String)lig.next();
    					//System.out.println(++k+"#"+brs);
        				st = new StringTokenizer(brs,"#");
        				String curr_kode = st.nextToken();
        				tknInfoKelas = st.nextToken();
        				String curr_inti = st.nextToken();
        				
        				st = new StringTokenizer(tknInfoKelas,"$");
        				String curr_kdpst = st.nextToken();
        				String curr_idkmk = st.nextToken();
        				String curr_shift = st.nextToken();
        				String curr_kllpll = st.nextToken();
        				String curr_idkur = st.nextToken();
        				String curr_cuid = st.nextToken();
        				if(curr_kode.equalsIgnoreCase(prev_kode)) {
        					if(Checker.isNumeric(curr_kode)) {
        						stmt.setString(1, curr_kode);
        					}
        					else {
        						stmt.setString(1, ""+norutKode);
        					}
        					
            				if(curr_inti.equalsIgnoreCase("null")) {
            					stmt.setBoolean(2, true);
            				}
            				else {
            					stmt.setBoolean(2, false);
            				}
            				//stmt.setString(3, thsmsTarget);
            				//stmt.setString(4, curr_kdpst);
            				//stmt.setString(5, curr_idkmk);
            				//stmt.setString(6, curr_shift);
            				//stmt.setString(7, curr_kllpll);
            				//stmt.setLong(8, Long.parseLong(curr_idkur));
            				stmt.setLong(3, Long.parseLong(curr_cuid));
            				//System.out.println("update "+curr_idkmk+"-"+curr_shift+"-"+curr_inti);
            				//System.out.println(stmt.executeUpdate());
        				}
        				else {
        					//pergantian kode
        					//System.out.println("pergantian kode="+norutKode);
        					if(Checker.isNumeric(curr_kode)) {
        						//System.out.println("masuk sini");
        						stmt.setString(1, curr_kode);
        					}
        					else {
        						norutKode++;
        						//System.out.println("kode baru="+norutKode);
        						stmt.setString(1, ""+norutKode);
        					}
        					
        					
            				if(curr_inti.equalsIgnoreCase("null")) {
            					stmt.setBoolean(2, true);
            				}
            				else {
            					stmt.setBoolean(2, false);
            				}
            				//stmt.setString(3, thsmsTarget);
            				//stmt.setString(4, curr_kdpst);
            				//stmt.setString(5, curr_idkmk);
            				//stmt.setString(6, curr_shift);
            				//stmt.setString(7, curr_kllpll);
            				//stmt.setLong(8, Long.parseLong(curr_idkur));
            				stmt.setLong(3, Long.parseLong(curr_cuid));
            				//System.out.println("update "+curr_idkmk+"-"+curr_shift+"-"+curr_inti);
            				//System.out.println(stmt.executeUpdate());
            				prev_kode = ""+curr_kode;
            				prev_kdpst = ""+curr_kdpst;
            				prev_idkmk = ""+curr_idkmk;
            				prev_shift = ""+curr_shift;
            				prev_kllpll = ""+curr_kllpll;
            				prev_inti = ""+curr_inti;
            				prev_cuid = ""+curr_cuid;
        				}
        				stmt.executeUpdate();
        				//if(!lig.hasNext()) {
        					
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
    
    public void resetClassPoolGrouping(Vector vScope,String thsmsTarget) {
    	
    	String nmmhs = null;
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//reset
    		String sql = "update ignore CLASS_POOL set KODE_PENGGABUNGAN=?,CANCELED=? where THSMS=? and (";
    		//stmt = con.prepareStatement("update CLASS_POOL set KODE_PENGGABUNGAN=?,CANCELED=? where THSMS=? and KDPST=? and IDKMK=? and SHIFT=? and NORUT_KELAS_PARALEL=?");
    		
    		ListIterator li = vScope.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs);
    			st.nextToken();
    			String kdpst = st.nextToken();
    			sql = sql+"KDPST='"+kdpst+"'";
    			if(li.hasNext()) {
    				sql = sql+" or ";
    			}
    			
    		}
    		sql = sql+")";
    		//System.out.println(sql);
    		stmt = con.prepareStatement(sql);
    		stmt.setNull(1, java.sql.Types.VARCHAR);
    		stmt.setBoolean(2, false);
    		stmt.setString(3, thsmsTarget);
    		stmt.executeUpdate();
    		
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
    
    
    public void resetTrnlmPenggabungan(Vector vScope,String thsmsTarget) {
    	//String thsms_now = Checker.getThsmsNow();
    	String nmmhs = null;
    	//Vector v = null;
    	ListIterator li = null;
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//1. get cuid cancel & cuid 
    		//stmt = con.prepareStatement("select * from CLASS_POOL where THSMS=? and KODE_PENGGABUNGAN is not NULL order by KODE_PENGGABUNGAN,CANCELED");
    		if(vScope!=null && vScope.size()>0) {
    			String sql = "select CUID_INIT from TRNLM where THSMSTRNLM=? and (";//and CUID_INIT is not NULL ";
    			li = vScope.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs);
        			st.nextToken();
        			String kdpst = st.nextToken();
        			sql = sql+"KDPSTTRNLM='"+kdpst+"'";
        			if(li.hasNext()) {
        				sql = sql+" or ";
        			}
        			
        		}
        		sql = sql+") and CUID_INIT is not NULL";
        		//System.out.println("sql="+sql);
        		stmt = con.prepareStatement(sql);
        		stmt.setString(1, thsmsTarget);
        		rs = stmt.executeQuery();
        		String list_cuid_init = null;
        		if(rs.next()) {
        			list_cuid_init = new String();
        			do {
        				list_cuid_init = list_cuid_init+rs.getLong(1)+"`";
        			}
        			while(rs.next());
        		}
        		
        		//kalo ada trnlm yg pernah digabung
        		//1. get informasi dari keas pool sbg data untuk mereset data trnlm
        		
        		if(list_cuid_init!=null && !Checker.isStringNullOrEmpty(list_cuid_init)) {
        			//System.out.println("pit1");
        			Vector vInfoCuid = new Vector();
        			li = vInfoCuid.listIterator();
        			StringTokenizer st = new StringTokenizer(list_cuid_init,"`");
        			stmt=con.prepareStatement("select IDKUR,IDKMK,SHIFT,NORUT_KELAS_PARALEL,KODE_KAMPUS,KDKMKMAKUL,SKSTMMAKUL,SKSPRMAKUL,SKSLPMAKUL from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where UNIQUE_ID=? and THSMS=?");
        			while(st.hasMoreTokens()) {
        				String init_cuid = st.nextToken();
        				stmt.setLong(1, Long.parseLong(init_cuid));
        				stmt.setString(2, thsmsTarget);
        				rs = stmt.executeQuery();
        				rs.next();
        				int idkur = rs.getInt("IDKUR");
        				int idkmk = rs.getInt("IDKMK");
        				//String kdpst = rs.getString("KDPST");
        				String shift = rs.getString("SHIFT");
        				int nopll = rs.getInt("NORUT_KELAS_PARALEL");
        				String kd_kmp = rs.getString("KODE_KAMPUS");
        				String kdkmk = rs.getString("KDKMKMAKUL");
        				int skstm = rs.getInt("SKSTMMAKUL");
        				int skspr = rs.getInt("SKSPRMAKUL");
        				int skslp = rs.getInt("SKSLPMAKUL");
        				String tmp = idkur+"`"+idkmk+"`"+shift+"`"+nopll+"`"+kd_kmp+"`"+kdkmk+"`"+(skstm+skslp+skspr)+"`"+init_cuid;
        				tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "`");
        				//System.out.println("tmp="+tmp);
        				li.add(tmp);
        			}
        			if(vInfoCuid.size()>0) {
        				//reset trnlm to init value
        				//System.out.println("pit2");
        				stmt = con.prepareStatement("update ignore TRNLM set KDKMKTRNLM=?,SKSMKTRNLM=?,KELASTRNLM=?,SHIFTTRNLM=?,IDKMKTRNLM=?,KODE_KAMPUS=?,CLASS_POOL_UNIQUE_ID=?,CUID_INIT=? where CUID_INIT=?");
        				li = vInfoCuid.listIterator();
        				while(li.hasNext()) {
        					String brs = (String)li.next();
        					//System.out.println("brs="+brs);
        					st = new StringTokenizer(brs,"`");
        					String idkur = st.nextToken();
        					String idkmk = st.nextToken();
        					String shift = st.nextToken();
        					String nopll = st.nextToken();
        					String kd_kmp = st.nextToken();
        					String kdkmk = st.nextToken();
        					String sksmk = st.nextToken();
        					String cuid_init = st.nextToken();
        					int i=1;
        					stmt.setString(i++,kdkmk);
        					stmt.setInt(i++, Integer.parseInt(sksmk));
        					stmt.setString(i++,nopll);
        					stmt.setString(i++,shift);
        					stmt.setInt(i++, Integer.parseInt(idkmk));
        					stmt.setString(i++,kd_kmp);
        					stmt.setLong(i++, Long.parseLong(cuid_init));
        					stmt.setNull(i++, java.sql.Types.INTEGER);
        					stmt.setLong(i++, Long.parseLong(cuid_init));
        					stmt.executeUpdate();
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
    
    /*
     * !!!!!!!!!!!!!!!!!!!!!!!!!
     * DEPRICATED = ganti v1
     * !!!!!!!!!!!!!!!!!!!!!!!!
     */
    public void updateCuidTrnlm() {
    	String thsms_now = Checker.getThsmsNow();
    	String nmmhs = null;
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//1. get cuid cancel & cuid 
    		stmt = con.prepareStatement("select * from CLASS_POOL where THSMS=? and KODE_PENGGABUNGAN is not NULL order by KODE_PENGGABUNGAN,CANCELED");
    		stmt.setString(1, thsms_now);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				
    				String kode_gabung = ""+rs.getString("KODE_PENGGABUNGAN");
    				String cancel = ""+rs.getBoolean("CANCELED");
    				String cuid = ""+rs.getLong("UNIQUE_ID");
    				//String shift = ""+rs.getString("SHIFT");
    				//String nopll = ""+rs.getInt("NORUT_KELAS_PARALEL");
    				li.add(kode_gabung+"`"+cancel+"`"+cuid);//yg pertama adalah kelas inti
    				
    			}
    			while(rs.next());
    		}
    		//2. reset cuid  sebelum update yg baru. seperti proses penggabungan = reset penggabungan seperti blum pernah digabung lalu upadte kode penggabungan yg baru
    		stmt = con.prepareStatement("select CUID_INIT from TRNLM inner join CLASS_POOL on CUID_INIT=UNIQUE_ID where CUID_INIT is not null and THSMS=?");
    		stmt.setString(1, thsms_now);
    		rs = stmt.executeQuery();
    		String tkn_cuid_init = null;
    		if(rs.next()) {
    			tkn_cuid_init = new String("");
    			do {
    				tkn_cuid_init = tkn_cuid_init+rs.getLong(1)+"`";
    			}while(rs.next());	
    		}
    		//2.a. balikin nilai CUID_INIT TRNLM.CLASS_POOL_UNIQUE_ID lalu SET CUID_INT set null
    		if(!Checker.isStringNullOrEmpty(tkn_cuid_init)) {
    			stmt = con.prepareStatement("update TRNLM set CLASS_POOL_UNIQUE_ID=?,CUID_INIT=? where CUID_INIT=?");
    			StringTokenizer st = new StringTokenizer(tkn_cuid_init,"`");
    			while(st.hasMoreTokens()) {
    				String cuid_init = st.nextToken();
    				stmt.setLong(1, Long.parseLong(cuid_init));
    				stmt.setNull(2, java.sql.Types.INTEGER);
    				stmt.setLong(3, Long.parseLong(cuid_init));
    				stmt.executeUpdate();
    			}
    		}
    		//isi CUID init di TRNLM untuk kebutuhan bila mo direset maka ada datanya
    		if(v!=null && v.size()>0) {
    			li = v.listIterator();
    			stmt = con.prepareStatement("update TRNLM set CLASS_POOL_UNIQUE_ID=?,CUID_INIT=? where CLASS_POOL_UNIQUE_ID=?");
    			String brs1 = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs1,"`");
				String prev_kode_gab = st.nextToken();
				String prev_cancel = st.nextToken();
				String prev_cuid = st.nextToken(); 
				String cuid_kelas_inti = new String(prev_cuid);
				//baris pertama tidak ada update karena kelas inti 
    			do {
    				String brs = (String)li.next();
    				st = new StringTokenizer(brs,"`");
    				String kode_gab = st.nextToken();
    				String cancel = st.nextToken();
    				String cuid = st.nextToken();
    				if(prev_kode_gab.equalsIgnoreCase(kode_gab)) {
    					//masih satu kelompok
    					if(cancel.equalsIgnoreCase("true")) {
    						//ini adalah kelas yg di cancel jadi harus diupdate
    						stmt.setLong(1, Long.parseLong(cuid_kelas_inti));
    						stmt.setLong(2, Long.parseLong(cuid));
    						stmt.setLong(3, Long.parseLong(cuid));
    						stmt.executeUpdate();
    					}
    					else {
    						//ngga mungkin ksini krn kalo cancel = false pilihannya baris pertama atau perubahan kode gabung 
    					}
    				}
    				else {
    					//pergantian kode gabung, baris pertama jadi = cuid_kelas_inti yg baru
    					cuid_kelas_inti = new String(cuid);
    					prev_kode_gab = new String(kode_gab);
    				}
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
    }    
    
    
    public void updateCuidTrnlm_v1() {
    	String thsms_now = Checker.getThsmsNow();
    	String nmmhs = null;
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//1. get cuid cancel & cuid 
    		stmt = con.prepareStatement("select * from CLASS_POOL where THSMS=? and KODE_PENGGABUNGAN is not NULL order by KODE_PENGGABUNGAN,CANCELED");
    		stmt.setString(1, thsms_now);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				
    				String kode_gabung = ""+rs.getString("KODE_PENGGABUNGAN");
    				String cancel = ""+rs.getBoolean("CANCELED");
    				String cuid = ""+rs.getLong("UNIQUE_ID");
    				String shift = ""+rs.getString("SHIFT");
    				String nopll = ""+rs.getInt("NORUT_KELAS_PARALEL");
    				li.add(kode_gabung+"`"+cancel+"`"+cuid+"`"+shift+"`"+nopll);//yg pertama adalah kelas inti
    				//System.out.println("kod = "+kode_gabung+"`"+cancel+"`"+cuid+"`"+shift+"`"+nopll);
    			}
    			while(rs.next());
    		}
    		
    		/*
    		 * fungsi reset diwawah sudah digantikan dgn 
    		 * resetTrnlmPenggabungan(vScope,thsmsTarget);
    		 */
    		//2. reset cuid  sebelum update yg baru. seperti proses penggabungan = reset penggabungan seperti blum pernah digabung lalu upadte kode penggabungan yg baru
    		/*
    		stmt = con.prepareStatement("select CUID_INIT from TRNLM inner join CLASS_POOL on CUID_INIT=UNIQUE_ID where CUID_INIT is not null and THSMS=?");
    		stmt.setString(1, thsms_now);
    		rs = stmt.executeQuery();
    		String tkn_cuid_init = null;
    		if(rs.next()) {
    			tkn_cuid_init = new String("");
    			do {
    				tkn_cuid_init = tkn_cuid_init+rs.getLong(1)+"`";
    			}while(rs.next());	
    		}
    		//2.a. balikin nilai CUID_INIT TRNLM.CLASS_POOL_UNIQUE_ID lalu SET CUID_INT set null
    		if(!Checker.isStringNullOrEmpty(tkn_cuid_init)) {
    			stmt = con.prepareStatement("update TRNLM set CLASS_POOL_UNIQUE_ID=?,CUID_INIT=? where CUID_INIT=?");
    			StringTokenizer st = new StringTokenizer(tkn_cuid_init,"`");
    			while(st.hasMoreTokens()) {
    				String cuid_init = st.nextToken();
    				stmt.setLong(1, Long.parseLong(cuid_init));
    				stmt.setNull(2, java.sql.Types.INTEGER);
    				stmt.setLong(3, Long.parseLong(cuid_init));
    				stmt.executeUpdate();
    			}
    		}
    		*/
    		//isi CUID init di TRNLM untuk kebutuhan bila mo direset maka ada datanya
    		/*
    		 * kenapa yg diupdate hanya SHIFT dan KELAS, karena ada kemungkinan kelasnya digabungkan dengan keals prodi lain, sedaangkan 
    		 * KRS dia harus tetap mengikuti KOnya sehingga idkmk dan kdkmk tidak diganti
    		 * yang diganti hanya yg menunjukan kelas yg diikuti thus shift dan kelas pll
    		 */
    		if(v!=null && v.size()>0) {
    			li = v.listIterator();
    			stmt = con.prepareStatement("update TRNLM set CLASS_POOL_UNIQUE_ID=?,CUID_INIT=?,SHIFTTRNLM=?,KELASTRNLM=? where CLASS_POOL_UNIQUE_ID=?");
    			String brs1 = (String)li.next();
    			//System.out.println("baris11="+brs1);
				StringTokenizer st = new StringTokenizer(brs1,"`");
				String prev_kode_gab = st.nextToken();
				String prev_cancel = st.nextToken();
				String prev_cuid = st.nextToken(); 
				String prev_shift = st.nextToken(); 
				String prev_nopll = st.nextToken(); 
				String cuid_kelas_inti = new String(prev_cuid);
				String shift_kelas_inti = new String(prev_shift);
				String nopll_kelas_inti = new String(prev_nopll);
				//baris pertama tidak ada update karena kelas inti
    			do {
    				String brs = (String)li.next();
    				st = new StringTokenizer(brs,"`");
    				String kode_gab = st.nextToken();
    				String cancel = st.nextToken();
    				String cuid = st.nextToken();
    				String shift = st.nextToken(); 
    				String nopll = st.nextToken(); 
    				if(prev_kode_gab.equalsIgnoreCase(kode_gab)) {
    					//masih satu kelompok
    					if(cancel.equalsIgnoreCase("true")) {
    						//ini adalah kelas yg di cancel jadi harus diupdate
    						stmt.setLong(1, Long.parseLong(cuid_kelas_inti));
    						stmt.setLong(2, Long.parseLong(cuid));
    						//stmt.setLong(3, Long.parseLong(cuid));
    						stmt.setString(3, shift_kelas_inti);
    						stmt.setString(4, nopll_kelas_inti);
    						stmt.setLong(5, Long.parseLong(cuid));
    						stmt.executeUpdate();
    					}
    					else {
    						//ngga mungkin ksini krn kalo cancel = false pilihannya baris pertama atau perubahan kode gabung 
    					}
    				}
    				else {
    					//pergantian kode gabung, baris pertama jadi = cuid_kelas_inti yg baru
    					cuid_kelas_inti = new String(cuid);
    					shift_kelas_inti = new String(shift);
    					nopll_kelas_inti = new String(nopll);
    					prev_kode_gab = new String(kode_gab);
    				}
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
    }    
    
    public void dropKelasDariCp(String[]listKelas) {
    	String target_thsms = Checker.getThsmsBukaKelas(); 
    	try {
    		if(listKelas!=null && listKelas.length>0) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//reset
        		stmt = con.prepareStatement("delete from CLASS_POOL where IDKUR=? and IDKMK=? and THSMS=? and KDPST=? and SHIFT=? and NORUT_KELAS_PARALEL=?");
        		for(int i=0;i<listKelas.length;i++) {
        			StringTokenizer st = new StringTokenizer(listKelas[i],",");
        			String kdpst = st.nextToken();
        			String idkmk = st.nextToken();
        			String idkur = st.nextToken();
        			String kdkmk = st.nextToken();
        			String shift = st.nextToken();
        			String klspll = st.nextToken();
        			stmt.setInt(1,Integer.parseInt(idkur));
        			stmt.setInt(2,Integer.parseInt(idkmk));
        			stmt.setString(3,target_thsms);
        			stmt.setString(4,kdpst);
        			stmt.setString(5,shift);
        			stmt.setInt(6,Integer.parseInt(klspll));
        			stmt.executeUpdate();
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
    
    public void ajukanPenggabunganKelas(String thsms, Vector vScope) {
    	if(vScope!=null && vScope.size()>0) {
    		try {
        		//String ipAddr =  request.getRemoteAddr();
    			Vector vIns = new Vector();
    			Vector vUpd = new Vector();
    			ListIterator liins = vIns.listIterator();
    			ListIterator liupd = vUpd.listIterator();
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//1.cek terlebih dahulu apakah sudah ada record utk thsms terkait
        		//Vector vScope = getScopeUpd7des2012("reqBukaKelas");
        		stmt = con.prepareStatement("select * from KELAS_GABUNGAN where THSMS=? and KDPST=?");
        		ListIterator li = vScope.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs);
        			st.nextToken();
        			String kdpst = st.nextToken();
        			stmt.setString(1, thsms);
        			stmt.setString(2, kdpst);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				liupd.add(kdpst);
        			}
        			else {
        				liins.add(kdpst);
        			}
        		}
        		//jika ins maka langsung insert
        		//jika upd harus dicek status sudah  lock atu tidak
        		
        		//proses insert
        		stmt = con.prepareStatement("insert into KELAS_GABUNGAN(THSMS,KDPST,NPM_OPERATOR,REQUESTED_TIME)values(?,?,?,?)");
        		liins= vIns.listIterator();
        		while(liins.hasNext()) {
        			String kdpst = (String)liins.next();
        			stmt.setString(1, thsms);
        			stmt.setString(2, kdpst);
        			stmt.setString(3, operatorNpm);
        			stmt.setTimestamp(4, AskSystem.getCurrentTimestamp());
        			stmt.executeUpdate();
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
    }
    
    public int getNoUrutKodeTerakhir(String thsmsTarget) {
    	String highest = "0";
    	//int i = 0;
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//reset
    		stmt = con.prepareStatement("select distinct KODE_PENGGABUNGAN from CLASS_POOL where THSMS=?");
    		stmt.setString(1, thsmsTarget);
    		rs = stmt.executeQuery();
    		
    		while(rs.next()) {
    			String kode = ""+rs.getString("KODE_PENGGABUNGAN");
    			if(!Checker.isStringNullOrEmpty(kode)) {
    				if(Integer.valueOf(kode).intValue()>Integer.valueOf(highest).intValue()) {
    					highest=""+kode;
    				}
    			}
    			
    		}
    		if(Checker.isStringNullOrEmpty(highest)) {
    			highest="0";
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
    	return Integer.valueOf(highest).intValue();
    }    
    
    
    public int updateMakul(String idkmk,String kdpst,String kdkmk,String nakmk,int skstm,int skspr,int skslp,int sksim,int skslb,String nodos,String metode_kuliah,String kdwpl){
    	int updated = 0;
    	kdkmk = kdkmk.toUpperCase();
    	nakmk = nakmk.toUpperCase();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//updtae recorrd
    		//stmt = con.prepareStatement("update MAKUL set NAKMKMAKUL=?,SKSTMMAKUL=?,SKSPRMAKUL=?,SKSLPMAKUL=?,KDWPLMAKUL=?,JENISMAKUL=?,NODOSMAKUL=?,STKMKMAKUL=? where KDPSTMAKUL=? and KDKMKMAKUL=?");
    		stmt = con.prepareStatement("update MAKUL set KDKMKMAKUL=?,NAKMKMAKUL=?,SKSTMMAKUL=?,SKSPRMAKUL=?,SKSLPMAKUL=?,SKSIMMAKUL=?,SKSLBMAKUL=?,NODOSMAKUL=?,METODE_KULIAH=?,KDWPLMAKUL=?,JENISMAKUL=? where IDKMKMAKUL=?");
    		if(kdkmk!=null) {
    			stmt.setString(1,kdkmk);
    		}
    		else {
    			stmt.setNull(1,java.sql.Types.VARCHAR);
    		}
    		if(nakmk!=null) {
    			stmt.setString(2,nakmk);
    		}
    		else {
    			stmt.setNull(2,java.sql.Types.VARCHAR);
    		}
    		stmt.setInt(3,skstm);
    		
    		stmt.setInt(4,skspr);
    		stmt.setInt(5,skslp);
    		stmt.setInt(6,sksim);
    		stmt.setInt(7,skslb);
    		
    		/*
    		if(kdwpl!=null) {
    			stmt.setString(6,kdwpl);
    		}
    		else {
    			stmt.setNull(6,java.sql.Types.VARCHAR);
    		}
    		if(jenis!=null) {
    			stmt.setString(7,jenis);
    		}
    		else {
    			stmt.setNull(7,java.sql.Types.VARCHAR);
    		}
    		*/
    		if(nodos!=null) {
    			stmt.setString(8,nodos);
    		}
    		else {
    			stmt.setNull(8,java.sql.Types.VARCHAR);
    		}
    		if(metode_kuliah!=null) {
    			stmt.setString(9,metode_kuliah);
    		}
    		else {
    			stmt.setNull(9,java.sql.Types.VARCHAR);
    		}
    		stmt.setString(10,kdwpl);
    		stmt.setString(11,"0");
    		//System.out.println("idkmk="+idkmk);
    		stmt.setInt(12, Integer.valueOf(idkmk).intValue());
    		//stmt.setString(10, kdkmk);
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

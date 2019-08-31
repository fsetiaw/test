package beans.dbase.trnlp;

import beans.dbase.UpdateDb;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Constant;
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
 * Session Bean implementation class UpdateDbTrnlp
 */
@Stateless
@LocalBean
public class UpdateDbTrnlp extends UpdateDb {
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
    public UpdateDbTrnlp() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbTrnlp(String operatorNpm) {
    	super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    
    public Vector copyStpidDariNpmPertamaOnTheList(String list_npm) {
    	
    	StringTokenizer st = null;
    	ListIterator li = null;
    	Vector v = null;
    	try {
    		if(list_npm!=null && !Checker.isStringNullOrEmpty(list_npm)) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		st = new StringTokenizer(list_npm,"`");
        		String npm_no_uno = st.nextToken();
        		stmt = con.prepareStatement("select * from TRNLP where NPMHSTRNLP=?");
        		stmt.setString(1, npm_no_uno);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			do {
        				String thsms = ""+rs.getString("THSMSTRNLP");
        				String kdpst = ""+rs.getString("KDPSTTRNLP");
        				String npmhs = ""+rs.getString("NPMHSTRNLP");
        				String kdkmk = ""+rs.getString("KDKMKTRNLP");
        				String nlakh = ""+rs.getString("NLAKHTRNLP");
        				String bobot = ""+rs.getFloat("BOBOTTRNLP");
        				String sksmk = ""+rs.getInt("SKSMKTRNLP");
        				String kdkmk_asal = ""+rs.getString("KDKMKASALP");
        				String nakmk_asal = ""+rs.getString("NAKMKASALP");
        				String sksmk_asal = ""+rs.getInt("SKSMKASAL");
        				String keter = ""+rs.getString("KETERTRNLP");
        				String trans = ""+rs.getBoolean("TRANSFERRED");
        				String setuj = ""+rs.getString("APPROVED");
        				if(v==null) {
        					v = new Vector();
        					li = v.listIterator();
        				}
        				li.add(thsms+"`"+kdpst+"`"+npmhs+"`"+kdkmk+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+kdkmk_asal+"`"+nakmk_asal+"`"+sksmk_asal+"`"+keter+"`"+trans+"`"+setuj);
        			} 
        			while(rs.next());
        		}
        		if(v!=null && v.size()>0) {
        			stmt = con.prepareStatement("INSERT IGNORE INTO TRNLP(THSMSTRNLP,KDPSTTRNLP,NPMHSTRNLP,KDKMKTRNLP,NLAKHTRNLP,BOBOTTRNLP,SKSMKTRNLP,KDKMKASALP,NAKMKASALP,SKSMKASAL,KETERTRNLP,TRANSFERRED,APPROVED)values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        			while(st.hasMoreTokens()) {
        				String target_npmhs = st.nextToken();
        				//System.out.println("target_npmhs="+target_npmhs);
        				li = v.listIterator();
            			while(li.hasNext()) {
            				String brs = (String)li.next();
            				StringTokenizer st1 = new StringTokenizer(brs,"`");
            				String thsms = st1.nextToken();
            				String kdpst = st1.nextToken();
            				String npmhs = st1.nextToken();
            				String kdkmk = st1.nextToken();
            				String nlakh = st1.nextToken();
            				String bobot = st1.nextToken();
            				String sksmk = st1.nextToken();
            				String kdkmk_asal = st1.nextToken();
            				String nakmk_asal = st1.nextToken();
            				String sksmk_asal = st1.nextToken();
            				String keter = st1.nextToken();
            				String trans = st1.nextToken();
            				String setuj = st1.nextToken();
            				int i =1;
            				stmt.setString(i++, thsms);
            				stmt.setString(i++, kdpst);
            				stmt.setString(i++, target_npmhs);
            				stmt.setString(i++, kdkmk);
            				stmt.setString(i++, "T");
            				stmt.setFloat(i++, 0);
            				stmt.setInt(i++, Integer.parseInt(sksmk));
            				stmt.setString(i++, kdkmk_asal);
            				stmt.setString(i++, nakmk_asal);
            				stmt.setInt(i++, Integer.parseInt(sksmk_asal));
            				stmt.setString(i++, keter);
            				stmt.setBoolean(i++, Boolean.parseBoolean(trans));
            				stmt.setString(i++, setuj);
            				stmt.executeUpdate();
            			}	
        			}
        			
        			stmt = con.prepareStatement("UPDATE TRNLP set KDKMKTRNLP=?,NLAKHTRNLP=?,BOBOTTRNLP=?,SKSMKTRNLP=?,NAKMKASALP=?,SKSMKASAL=?,KETERTRNLP=?,TRANSFERRED=?,APPROVED=? where THSMSTRNLP=? and NPMHSTRNLP=? and KDKMKASALP=?");
        			st = new StringTokenizer(list_npm,"`");
            		npm_no_uno = st.nextToken(); // mamster data
        			while(st.hasMoreTokens()) {
                		String target_npmhs = st.nextToken();
                		li = v.listIterator();
              			while(li.hasNext()) {
              				String brs = (String)li.next();
            				StringTokenizer st1 = new StringTokenizer(brs,"`");
              				String thsms = st1.nextToken();
                    		String kdpst = st1.nextToken();
                    		String npmhs = st1.nextToken();
                    		String kdkmk = st1.nextToken();
                    		String nlakh = st1.nextToken();
                    		String bobot = st1.nextToken();
                    		String sksmk = st1.nextToken();
                    		String kdkmk_asal = st1.nextToken();
                    		String nakmk_asal = st1.nextToken();
                    		String sksmk_asal = st1.nextToken();
                    		String keter = st1.nextToken();
                    		String trans = st1.nextToken();
                    		String setuj = st1.nextToken();
                    		int i =1;
                    		stmt = con.prepareStatement("UPDATE TRNLP set KDKMKTRNLP=?,NLAKHTRNLP=?,BOBOTTRNLP=?,SKSMKTRNLP=?,NAKMKASALP=?,SKSMKASAL=?,KETERTRNLP=?,TRANSFERRED=?,APPROVED=? where THSMSTRNLP=? and KDPSTTRNLP=? and NPMHSTRNLP=? and KDKMKASALP=?");
                    		stmt.setString(i++, kdkmk);
                    		stmt.setString(i++, "T");
                    		stmt.setFloat(i++, 0);
                    		stmt.setInt(i++, Integer.parseInt(sksmk));
                    		stmt.setString(i++, nakmk_asal);
                    		stmt.setInt(i++, Integer.parseInt(sksmk_asal));
                    		stmt.setString(i++, keter);
                    		stmt.setBoolean(i++, Boolean.parseBoolean(trans));
                    		stmt.setString(i++, setuj);
                    		stmt.setString(i++, thsms);
                    		stmt.setString(i++, kdpst);
                    		stmt.setString(i++, target_npmhs);
                    		stmt.setString(i++, kdkmk_asal);
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
    
    
    
    public Vector updNilaiTrnlp(String list_npm) {
    	
    	StringTokenizer st = null;
    	ListIterator li = null;
    	Vector v = null;
    	try {
    		if(list_npm!=null && !Checker.isStringNullOrEmpty(list_npm)) {
    			v = new Vector();
    			li = v.listIterator();
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select ASNIMMSMHS,ASPTIMSMHS from CIVITAS where NPMHSMSMHS=?");
        		
        		st = new StringTokenizer(list_npm,"`");
        		while(st.hasMoreTokens()) {
        			String target_npm = st.nextToken();
        			stmt.setString(1, target_npm);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				String asnim = ""+rs.getString(1);
        				String aspti = ""+rs.getString(2);
        				
        				if(!Checker.isStringNullOrEmpty(asnim) && !Checker.isStringNullOrEmpty(aspti) && aspti.equalsIgnoreCase(Constants.getKdpti())) {
        					li.add(target_npm+"`"+asnim+"`"+aspti);
        				}
        			}
        		}
        		if(v!=null && v.size()>0) {
        			stmt = con.prepareStatement("select * from TRNLP where NPMHSTRNLP=? and TRANSFERRED=?");
        			li = v.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				st = new StringTokenizer(brs,"`");
        				String npmhs = st.nextToken();
        				String asnim = st.nextToken();
        				String aspti = st.nextToken();
        				stmt.setString(1, npmhs);
        				stmt.setBoolean(2, true);
        				rs = stmt.executeQuery();
        				while(rs.next()) {
        					String kdkmk = ""+rs.getString("KDKMKTRNLP");
        					brs = brs+"`"+kdkmk;
        				}
        				li.set(brs);
        				//System.out.println("brs="+brs);
        			}
        			//ambil nilai dari npm terdahuku
        			stmt  = con.prepareStatement("select NLAKHTRNLM,BOBOTTRNLM from TRNLM where NPMHSTRNLM=? and KDKMKTRNLM=? order by NLAKHTRNLM limit 1");
        			li = v.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				st = new StringTokenizer(brs,"`");
        				String npmhs = st.nextToken();
        				String asnim = st.nextToken();
        				String aspti = st.nextToken();
        				String tmp = npmhs+"`"+asnim+"`"+aspti;
        				while(st.hasMoreTokens()) {
        					String kdkmk = st.nextToken();
        					stmt.setString(1, asnim);
        					stmt.setString(2, kdkmk);
        					rs = stmt.executeQuery();
        					rs.next();
        					String nlakh = ""+rs.getString("NLAKHTRNLM");
        					double bobot = rs.getDouble("BOBOTTRNLM");
        					tmp = tmp+"`"+kdkmk+"`"+nlakh+"`"+bobot;
        				}
        				li.set(tmp);
        				//System.out.println(tmp);
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
    	return v;
    }
    
    
    public void insertTrnlpFromTxt(String source_file, String thsms) {
    	
    	Vector v = Tool.bacaFileTxt(source_file, thsms);
    	if(v!=null) {
    		try {
        		Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
           		con = ds.getConnection();
           		stmt = con.prepareStatement("select NPMHSMSMHS from CIVITAS where NIMHSMSMHS=?");
           		ListIterator li = v.listIterator();
           		while(li.hasNext()) {
           			String brs = (String)li.next();
           			StringTokenizer st = new StringTokenizer(brs,"`");
           			String norut = st.nextToken();
           			String kdpst = st.nextToken();
           			String nimhs = st.nextToken();
           			String nmmhs = st.nextToken();
           			String smawl = st.nextToken();
           			String stpid = st.nextToken();
           			stmt.setString(1, nimhs);
           			rs = stmt.executeQuery();
           			//System.out.println(brs);
           			rs.next();
           			String npmhs = rs.getString(1);
           			li.set(brs+"`"+npmhs);
           			String brs_mk = (String)li.next();
           			//System.out.println(brs_mk);
           			if(!brs_mk.equalsIgnoreCase("null")) {
           				brs_mk = brs_mk.replace("`ISPNIKASI", "");
           				st = new StringTokenizer(brs_mk,"`");
           				while(st.hasMoreTokens()) {
           					String kdkmk = st.nextToken();
           					String nlakh = st.nextToken();
           					String bobot = st.nextToken();
           					String nakmk = st.nextToken();
           					String sksmk = st.nextToken();
           					
           				}
           			}	
           		}
           		/*
           		stmt = con.prepareStatement("delete from TRNLP where NPMHSTRNLP=?");
           		li = v.listIterator();
           		while(li.hasNext()) {
           			String brs = (String)li.next();
           			StringTokenizer st = new StringTokenizer(brs,"`");
           			String norut = st.nextToken();
           			String kdpst = st.nextToken();
           			String nimhs = st.nextToken();
           			String nmmhs = st.nextToken();
           			String smawl = st.nextToken();
           			String stpid = st.nextToken();
           			String npmhs = st.nextToken();
           			stmt.setString(1, npmhs);
           			stmt.executeUpdate();
           			String brs_mk = (String)li.next();
           			if(!brs_mk.equalsIgnoreCase("null")) {
           				st = new StringTokenizer(brs_mk,"`");
           				while(st.hasMoreTokens()) {
           					String kdkmk = st.nextToken();
           					String nlakh = st.nextToken();
           					String bobot = st.nextToken();
           					String nakmk = st.nextToken();
           					String sksmk = st.nextToken();
           					
           				}
           			}	
           		}
           		*/
           		stmt = con.prepareStatement("INSERT ignore INTO TRNLP(THSMSTRNLP,KDPSTTRNLP,NPMHSTRNLP,NLAKHTRNLP,BOBOTTRNLP,KDKMKASALP,NAKMKASALP,SKSMKASAL) VALUES (?,?,?,?,?,?,?,?)");
           		li = v.listIterator();
           		while(li.hasNext()) {
           			String brs = (String)li.next();
           			StringTokenizer st = new StringTokenizer(brs,"`");
           			String norut = st.nextToken();
           			String kdpst = st.nextToken();
           			String nimhs = st.nextToken();
           			String nmmhs = st.nextToken();
           			String smawl = st.nextToken();
           			String stpid = st.nextToken();
           			String npmhs = st.nextToken();
           			
           			String brs_mk = (String)li.next();
           			if(!brs_mk.equalsIgnoreCase("null")) {
           				st = new StringTokenizer(brs_mk,"`");
           				while(st.hasMoreTokens()) {
           					String kdkmk = st.nextToken();
           					String nlakh = st.nextToken();
           					String bobot = st.nextToken();
           					String nakmk = st.nextToken();
           					String sksmk = st.nextToken();
           					stmt.setString(1, smawl);
           					if(kdpst.equalsIgnoreCase("86208")) {
           						stmt.setString(2, "88888");
           					}
           					else {
           						stmt.setString(2, kdpst);	
           					}
           					
           					stmt.setString(3, npmhs);
           					stmt.setString(4, nlakh);
           					stmt.setFloat(5, Float.parseFloat(bobot));
           					stmt.setString(6, kdkmk);
           					stmt.setString(7, nakmk.toUpperCase());
           					stmt.setLong(8, Long.parseLong(sksmk));
           					//System.out.print("ins ="+npmhs+" "+kdkmk);
           					int i = stmt.executeUpdate();
           					//System.out.println(" = "+i);
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
    	
    }
    
    public int hitungDanUpdateSksdi(String list_npm) {
    	
    	/*
    	 * menghitung sksdi berdasarkan makul yg sudah  disetarakan /kdkmktrnlp != null 
    	 */
    	StringTokenizer st = null;
    	int updated = 0;
    	if(!Checker.isStringNullOrEmpty(list_npm)) {
    		Vector v = new Vector();
    		ListIterator li = v.listIterator();
    		try {
        		Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
           		con = ds.getConnection();
           		stmt = con.prepareStatement("select SKSMKTRNLP from TRNLP where NPMHSTRNLP=? and KDKMKTRNLP is not null");
           		st = new StringTokenizer(list_npm,"`");
           		while(st.hasMoreTokens()) {
           			int sksdi = 0;
           			String npmhs = st.nextToken();
           			stmt.setString(1, npmhs);
           			rs = stmt.executeQuery();
           			while(rs.next()) {
           				sksdi = sksdi+rs.getInt(1);
           			}
           			li.add(npmhs+"`"+sksdi);
           		}
           		if(v!=null && v.size()>0) {
           			li = v.listIterator();
           			stmt = con.prepareStatement("update CIVITAS set SKSDIMSMHS=? where NPMHSMSMHS=?");
           			while(li.hasNext()) {
           				String brs = (String)li.next();
           				st = new StringTokenizer(brs,"`");
           				String npmhs = st.nextToken();
           				String sksdi = st.nextToken();
           				int sks_transfer= 0;
           				try {
           					sks_transfer = Integer.parseInt(sksdi);
           				}
           				catch(Exception e) {}
           				if(sks_transfer>0) {
           					stmt.setInt(1, sks_transfer);
           					stmt.setString(2, npmhs);
           					updated = updated + stmt.executeUpdate();
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
    	
    	return updated;
    }
    
    public int hitungDanUpdateSksdiBerdasarkanMkAsal(String list_npm) {
    	
    	/*
    	 * !!!!! INI BELUM TENTU MATAKULIAH YG SUDAH DISETARAKAN!!!!!!
    	 * menghitung sksdi berdasarkan makul dari pt asal
    	 * 
    	 * kalo yg sudah disetarakan gunakan : hitungDanUpdateSksdi
    	 */
    	StringTokenizer st = null;
    	int updated = 0;
    	if(!Checker.isStringNullOrEmpty(list_npm)) {
    		Vector v = new Vector();
    		ListIterator li = v.listIterator();
    		try {
        		Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
           		con = ds.getConnection();
           		stmt = con.prepareStatement("select SKSMKTRNLP from TRNLP where NPMHSTRNLP=?");
           		st = new StringTokenizer(list_npm,"`");
           		while(st.hasMoreTokens()) {
           			int sksdi = 0;
           			String npmhs = st.nextToken();
           			stmt.setString(1, npmhs);
           			rs = stmt.executeQuery();
           			while(rs.next()) {
           				sksdi = sksdi+rs.getInt(1);
           			}
           			li.add(npmhs+"`"+sksdi);
           		}
           		if(v!=null && v.size()>0) {
           			li = v.listIterator();
           			stmt = con.prepareStatement("update CIVITAS set SKSDIMSMHS=? where NPMHSMSMHS=?");
           			while(li.hasNext()) {
           				String brs = (String)li.next();
           				st = new StringTokenizer(brs,"`");
           				String npmhs = st.nextToken();
           				String sksdi = st.nextToken();
           				int sks_transfer= 0;
           				try {
           					sks_transfer = Integer.parseInt(sksdi);
           				}
           				catch(Exception e) {}
           				if(sks_transfer>0) {
           					stmt.setInt(1, sks_transfer);
           					stmt.setString(2, npmhs);
           					updated = updated + stmt.executeUpdate();
           				}
           				//System.out.println("update "+brs+" = "+updated);
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
    	
    	return updated;
    }
    
    public int updateSksmkTrnlp(String smawl, String npmhs) {
    	int updated=0;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	Vector v = null;
    	try {
    		if(Checker.isStringNullOrEmpty(npmhs)) {
    			//proses berdasarkan angkatam
    			v = new Vector();
    			li = v.listIterator();
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
        		con = ds.getConnection();
        		//get list npmh
        		stmt = con.prepareStatement("select ASNIMMSMHS,ASPTIMSMHS from CIVITAS where NPMHSMSMHS=?");
        		
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
    	return updated;
	}
    
    
    public int copyMataKulishAsal(String npmhs_baru) {
    	int updated=0;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	Vector v = null;
    	try {
    		if(!Checker.isStringNullOrEmpty(npmhs_baru)) {
    			//proses berdasarkan angkatam
    			v = new Vector();
    			li = v.listIterator();
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
        		con = ds.getConnection();
        		//get list npmh
        		stmt = con.prepareStatement("select KDPSTMSMHS,ASNIMMSMHS,ASPTIMSMHS,SMAWLMSMHS from CIVITAS where NPMHSMSMHS=?");
        		stmt.setString(1, npmhs_baru);
        		rs = stmt.executeQuery();
        		String kdpst = null;
        		String asnim = null;
        		String smawl = null;
        		if(rs.next()) {
        			String kdpti = rs.getString(3);
        			if(!Checker.isStringNullOrEmpty(kdpti) &&  kdpti.equalsIgnoreCase(Constants.getKdpti())) {
        				asnim = new String(rs.getString(2));	
        				kdpst = new String(rs.getString(1));
        				smawl = new String(rs.getString(4));
        			}
        		}
        		
        		if(!Checker.isStringNullOrEmpty(asnim)) {
        			stmt=con.prepareStatement("select KDKMKTRNLM,NAKMKMAKUL,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,IDKMKTRNLM from CIVITAS inner join TRNLM on NPMHSMSMHS=NPMHSTRNLM inner join MAKUL on IDKMKTRNLM=IDKMKMAKUL where NPMHSMSMHS=?");
        			stmt.setString(1, asnim);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				v = new Vector();
        				li = v.listIterator();
        				String kdkmk = rs.getString(1);
        				String nakmk = rs.getString(2);
        				double nilai = rs.getDouble(3);
        				String nlakh = rs.getString(4);
        				double bobot = rs.getDouble(5);
        				int sksmk = rs.getInt(6);
        				int idkmk = rs.getInt(7);
        				String tmp = kdkmk+"`"+nakmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+idkmk;
        				li.add(tmp.replace("``", "`null`"));
        				while(rs.next()) {
        					kdkmk = rs.getString(1);
            				nakmk = rs.getString(2);
            				nilai = rs.getDouble(3);
            				nlakh = rs.getString(4);
            				bobot = rs.getDouble(5);
            				sksmk = rs.getInt(6);
            				idkmk = rs.getInt(7);
            				tmp = kdkmk+"`"+nakmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+idkmk;
            				li.add(tmp.replace("``", "`null`"));
        				}
        			}
        			if(v!=null && v.size()>0) {
        				li = v.listIterator();
        				stmt = con.prepareStatement("insert ignore into TRNLP(THSMSTRNLP,KDPSTTRNLP,NPMHSTRNLP,KDKMKASALP,NAKMKASALP,SKSMKASAL,NLAKHTRNLP,BOBOTTRNLP)values(?,?,?,?,?,?,?,?)");
        				while(li.hasNext()) {
        					String brs = (String)li.next();
        					st = new StringTokenizer(brs,"`");
        					String kdkmk=st.nextToken();
        					String nakmk=st.nextToken();
        					String nilai=st.nextToken();
        					String nlakh=st.nextToken();
        					String bobot=st.nextToken();
        					String sksmk=st.nextToken();
        					String idkmk=st.nextToken();
        					stmt.setString(1, smawl);
        					stmt.setString(2, kdpst);
        					stmt.setString(3, npmhs_baru);
        					stmt.setString(4, kdkmk);
        					stmt.setString(5, nakmk);
        					stmt.setInt(6, Integer.parseInt(sksmk));
        					stmt.setString(7, nlakh);
        					stmt.setDouble(8, Double.parseDouble(bobot));
        					updated = updated+stmt.executeUpdate();
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
    	return updated;
	}
}

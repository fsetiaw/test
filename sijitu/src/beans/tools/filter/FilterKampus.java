package beans.tools.filter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;
import java.util.StringTokenizer;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

import beans.tools.Checker;

/**
 * Session Bean implementation class FilterKampus
 */
@Stateless
@LocalBean
public class FilterKampus {

    /**
     * Default constructor. 
     */
    public FilterKampus() {
        // TODO Auto-generated constructor stub
    }

    public static String getListKampusNonActive(String target_thsms) {
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String tkn_kmp=null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select distinct KODE_KAMPUS from KAMPUS_NONAKTIF");
			rs = stmt.executeQuery();
			while(rs.next()) {
				String kdkmp = rs.getString(1);
				if(tkn_kmp==null) {
					tkn_kmp=new String(kdkmp);
				}
				else {
					tkn_kmp = tkn_kmp+"`"+kdkmp;
				}
			}
			if(tkn_kmp!=null) {
				//System.out.println("tkn_kmp_init="+tkn_kmp);
				StringTokenizer st = new StringTokenizer(tkn_kmp,"`");
				tkn_kmp = null;
				stmt = con.prepareStatement("select * from KAMPUS_NONAKTIF where KODE_KAMPUS=? order by THSMS_START_NONAKTIF desc limit 1");
				while(st.hasMoreTokens()) {
					String kdkmp = st.nextToken();
					stmt.setString(1, kdkmp);
					rs = stmt.executeQuery();
					if(rs.next()) {
						/*
						 * thsms_close tidak mungkin null karena harus tandem dengan pembukaan
						 * jadi thsms_close = inital data (awal ditutup))
						 */
						String thsms_close = rs.getString("THSMS_START_NONAKTIF");
						String thsms_reopen = rs.getString("THSMS_REACTIVATED");
						//System.out.println("thsms_close="+thsms_close);
						//System.out.println("thsms_reopen="+thsms_reopen);
						if(Checker.isStringNullOrEmpty(thsms_reopen)) {
							//berarti masih close
							if(thsms_close.compareToIgnoreCase(target_thsms)<=0) {
								//System.out.println("match1");
								if(tkn_kmp==null) {
									tkn_kmp = new String(kdkmp);
								}
								else {
									tkn_kmp = tkn_kmp+"`"+kdkmp;
								}
							}
						}
						else {
							
							if(thsms_close.compareToIgnoreCase(target_thsms)<=0 && thsms_reopen.compareToIgnoreCase(target_thsms)>0) {
								//System.out.println("match2");
								if(tkn_kmp==null) {
									tkn_kmp = new String(kdkmp);
								}
								else {
									tkn_kmp = tkn_kmp+"`"+kdkmp;
								}
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
    	return tkn_kmp;
    }
    	   
    
    public static String kampusAktifOnly(String thsms, String tkn_kode_kampus_to_test) {
    	String char_pemisah_used = null;
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	StringTokenizer st = null;
    	String tkn_aktif_kampus = null; 
    	if(tkn_kode_kampus_to_test!=null) {
    		if(tkn_kode_kampus_to_test.contains(",")) {
    			char_pemisah_used = ",";
    		}
    		else if(tkn_kode_kampus_to_test.contains("`")) {
    			char_pemisah_used = "`";
    		}
    		else if(tkn_kode_kampus_to_test.contains("||")) {
    			char_pemisah_used = "||";
    		}
    		else if(tkn_kode_kampus_to_test.contains("#")) {
    			char_pemisah_used = "#";
    		}
    		else if(tkn_kode_kampus_to_test.contains("$")) {
    			char_pemisah_used = "$";
    		}
    		//tkn_kode_kampus_to_test = tkn_kode_kampus_to_test.replace(char_pemisah_used, "`");
    		tkn_kode_kampus_to_test = tkn_kode_kampus_to_test.toUpperCase();
    		if(char_pemisah_used==null) {
    			st = new StringTokenizer(tkn_kode_kampus_to_test);
    		}
    		else {
    			st = new StringTokenizer(tkn_kode_kampus_to_test,char_pemisah_used);
    		}
//    		/ && (st = new StringTokenizer(tkn_kode_kampus_to_test.toUpperCase().replace(",", "`"),"`")).countTokens()>0) {
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from KAMPUS_NONAKTIF where KODE_KAMPUS=?");
    			while(st.hasMoreTokens()) {
    				String kode_kmp = st.nextToken();
    				stmt.setString(1, kode_kmp);
    				//System.out.println("kode_kmp="+kode_kmp);
    				rs = stmt.executeQuery();
    				boolean buka = true;
    				//pake while, karena bisa ada beberapa rekord (buka-tutup beberapa periode waktu)
    				while(rs.next()) {
    					String thsms_close = rs.getString(1);
    					String thsms_reopen = rs.getString(3);
    					
    					if(thsms_close.compareToIgnoreCase(thsms)<=0) {
    						buka = false;
    					}
    					//System.out.println("close="+thsms_close+" vs "+thsms+" buka="+buka);
    					if(thsms_reopen!=null && (thsms_reopen.compareToIgnoreCase(thsms)<=0)) {
    						buka = true;
    					}
    					//System.out.println("open="+thsms_reopen+" vs "+thsms+" buka="+buka);
    				}
    				if(buka) {
    					if(tkn_aktif_kampus==null) {
    						tkn_aktif_kampus = new String(kode_kmp);
    					}
    					else {
    						if(char_pemisah_used!=null) {
    							tkn_aktif_kampus = tkn_aktif_kampus+char_pemisah_used+kode_kmp;	
    						}
    						else {
    							//tambahkan deteksi char pemisah yg digunakan
    						}
    						
    					}
    				}
    				//System.out.println("tkn_aktif_kampus="+tkn_aktif_kampus);
    			}
    			//1. set npm asal at civitas
    			
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
    	return tkn_aktif_kampus;
    }
}

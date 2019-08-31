package beans.dbase.feeder;

import beans.dbase.UpdateDb;
import beans.setting.Constants;
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
 * Session Bean implementation class UpdateDbFeeder
 */
@Stateless
@LocalBean
public class UpdateDbFeeder extends UpdateDb {
	String operatorNpm;
	String tknOperatorNickname;
	String operatorNmm;
	boolean petugas;
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;
       
    /**
     * @see UpdateDb#UpdateDb()
     */
    public UpdateDbFeeder() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbFeeder(String operatorNpm) {
    	 super(operatorNpm);
         this.operatorNpm = operatorNpm;
     	this.operatorNmm = getNmmOperator();
     	this.tknOperatorNickname = getTknOprNickname();
     	////System.out.println("tknOperatorNickname1="+this.tknOperatorNickname);
     	this.petugas = cekApaUsrPetugas();
        // TODO Auto-generated constructor stub
    }
    
    public void updateKo(String list_npm, String idko) {
    	//System.out.println("list_npm="+list_npm);;
    	
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update EXT_CIVITAS set KRKLMMSMHS=? where NPMHSMSMHS=?");
    		StringTokenizer st = new StringTokenizer(list_npm,",");
    		while(st.hasMoreTokens()) {
    			String npmhs = st.nextToken();
    			stmt.setString(1,idko);
    			stmt.setString(2, npmhs);
    			//System.out.print("npmhs "+npmhs);
    			int i = stmt.executeUpdate();
    			//System.out.println(" = "+i);
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
    	//return needByGetProfile;
    }
    
    
    public Vector hapusKrsMhsYgTidakAdaDiListAktifDiEpsbed(Vector v_sdf_addInfoNpm, String thsms, String kdpst) {
    	//System.out.println("list_npm="+list_npm);;
    	ListIterator li = v_sdf_addInfoNpm.listIterator();
    	Vector v_delete = new Vector();
    	ListIterator lid = v_delete.listIterator();
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=?");
    		stmt.setString(1, thsms);
    		stmt.setString(2, kdpst);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String npmhs = rs.getString(1);
    			//System.out.println("--"+npmhs);
    			li = v_sdf_addInfoNpm.listIterator();
    			boolean match = false;
    			while(li.hasNext() && !match) {
    				String npm_aktif = (String)li.next();
    				
    				if(npm_aktif.startsWith(npmhs)) {
    					match = true;
    				}
    			}
    			if(!match) {
    				//System.out.println("-nomatch-"+npmhs);
    				lid.add(npmhs);
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
    	return v_delete;
    }
    
    
    public void updateShift(String list_npm, String tkn_ttmhs_shift) {
    	
    	
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update CIVITAS set SHIFTMSMHS=? where NPMHSMSMHS=?");
    		StringTokenizer st = new StringTokenizer(list_npm,",");
    		StringTokenizer st1 = new StringTokenizer(tkn_ttmhs_shift,"`");
    		while(st1.hasMoreTokens()) {
    			int norut_mhs=0;
    			String tkn = st1.nextToken();
    			StringTokenizer st2 = new StringTokenizer(tkn,",");
    			String ttmhs = st2.nextToken();
    			String target_shift = st2.nextToken();
    			while(norut_mhs<Integer.parseInt(ttmhs)) {
    				String npm = st.nextToken();
    				norut_mhs++;
    				stmt.setString(1, target_shift);
    				stmt.setString(2, npm);
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
    	//return needByGetProfile;
    }
    
    public void deletePrevKrsTheninsertKrs(Vector V_getMkSesuaiAngkatanPerMhs, String thsms, String kdpst, String kdjen) {
    	
    	
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
    		ListIterator lik = V_getMkSesuaiAngkatanPerMhs.listIterator();
			while(lik.hasNext()) {
				String info_mhs = (String)lik.next();
				StringTokenizer st = new StringTokenizer(info_mhs,"`");
				String krklm = st.nextToken();
	    		String smawl = st.nextToken();
	    		String npmhs = st.nextToken();
	    		String nimhs = st.nextToken();
	    		String nmmhs = st.nextToken();
	    		String shift = st.nextToken();
	    		String stpid = st.nextToken();
				while(lik.hasNext()) {
					String sms = (String)lik.next();
					Vector vtmp = (Vector)lik.next();
					String target_thsms = new String(smawl);
					for(int i=1;i<Integer.parseInt(sms);i++) {
						target_thsms = Tool.returnNextThsmsGivenTpAntara(target_thsms);
					}
					stmt.setString(1, target_thsms);
					stmt.setString(2, npmhs);
					stmt.executeUpdate();
				}
				
				
			}
			
			
			stmt = con.prepareStatement("insert into TRNLM (THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,KELASTRNLM,IDKMKTRNLM)values(?,?,?,?,?,?,?,?,?,?,?)");
    		lik = V_getMkSesuaiAngkatanPerMhs.listIterator();
			while(lik.hasNext()) {
				String info_mhs = (String)lik.next();
				StringTokenizer st = new StringTokenizer(info_mhs,"`");
				st.nextToken();
				st.nextToken();
				String npmhs = st.nextToken();
				//stmt.setString(1, thsms);
				//stmt.setString(2, npmhs);
				//stmt.executeUpdate();
				while(lik.hasNext()) {
					String sms = (String)lik.next();
					Vector vtmp = (Vector)lik.next();
					ListIterator lit = vtmp.listIterator();
					while(lit.hasNext()) {
						String info_mk = (String)lit.next();
						st = new StringTokenizer(info_mk,"`");
						String idkmk = st.nextToken();
						String kdkmk = st.nextToken();
						String nakmk = st.nextToken();
						String ttsks = st.nextToken();
						String skripsi = st.nextToken();
						String THSMSTRNLM = new String(thsms);
						String KDPTITRNLM = Constants.getKdpti();
						String KDJENTRNLM = new String(kdjen);
						String KDPSTTRNLM = new String(kdpst);
						String NPMHSTRNLM = new String(npmhs);
						String KDKMKTRNLM = new String(kdkmk);
						String NLAKHTRNLM = "T";
						String BOBOTTRNLM = "0";
						String SKSMKTRNLM = new String(ttsks);
						String KELASTRNLM = "1";
						String IDKMKTRNLM = new String(idkmk);
						stmt.setString(1,THSMSTRNLM);
						stmt.setString(2,KDPTITRNLM);
						stmt.setString(3,KDJENTRNLM);
						stmt.setString(4,KDPSTTRNLM);
						stmt.setString(5,NPMHSTRNLM);
						stmt.setString(6,KDKMKTRNLM);
						stmt.setString(7,NLAKHTRNLM);
						stmt.setDouble(8,Double.parseDouble(BOBOTTRNLM));
						stmt.setInt(9,Integer.parseInt(SKSMKTRNLM));
						stmt.setString(10,KELASTRNLM);
						stmt.setInt(11,Integer.parseInt(IDKMKTRNLM));
						stmt.executeUpdate();	
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
    	//return needByGetProfile;
    }
    
    public void deletePrevKrsTheninsertKrs_v1(Vector V_getMkSesuaiAngkatanPerMhs, String kdpst, String kdjen) {
    	
    	
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
    		ListIterator lik = V_getMkSesuaiAngkatanPerMhs.listIterator();
    		while(lik.hasNext()) {
				String info_mhs = (String)lik.next();
				StringTokenizer st = new StringTokenizer(info_mhs,"`");
				String krklm = st.nextToken();
	    		String smawl = st.nextToken();
	    		String npmhs = st.nextToken();
	    		String nimhs = st.nextToken();
	    		String nmmhs = st.nextToken();
	    		String shift = st.nextToken();
	    		String stpid = st.nextToken();
				//System.out.println("info_mhs="+info_mhs);
				
				boolean nu_iter = false;
				while(lik.hasNext() && !nu_iter) {
					String sms = (String)lik.next();
					if(sms.contains("`")) {
						nu_iter = true;
						lik.previous();
					}
					else {
						String target_thsms = Tool.returnThsmsGivenSmaleAndSmsKe(smawl, Integer.parseInt(sms));
						stmt.setString(1, target_thsms);
						stmt.setString(2, npmhs);
						stmt.executeUpdate();	
						//System.out.println("sms="+sms);
						Vector vtmp = (Vector)lik.next();
						//ListIterator lit = vtmp.listIterator();
						//int no = 0;
						//while(lit.hasNext()) {
						//	no++;
						//	String info_mk = (String)lit.next();
						//	out.print("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp ("+sms+")"+info_mk+"<br>");
						//}	
					}
					
				}
			}
    		
			stmt = con.prepareStatement("insert into TRNLM (THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,KELASTRNLM,IDKMKTRNLM)values(?,?,?,?,?,?,?,?,?,?,?)");
    		lik = V_getMkSesuaiAngkatanPerMhs.listIterator();
    		while(lik.hasNext()) {
				String info_mhs = (String)lik.next();
				StringTokenizer st = new StringTokenizer(info_mhs,"`");
				String krklm = st.nextToken();
	    		String smawl = st.nextToken();
	    		String npmhs = st.nextToken();
	    		String nimhs = st.nextToken();
	    		String nmmhs = st.nextToken();
	    		String shift = st.nextToken();
	    		String stpid = st.nextToken();
				//System.out.println("info_mhs="+info_mhs);
				
				boolean nu_iter = false;
				while(lik.hasNext() && !nu_iter) {
					String sms = (String)lik.next();
					if(sms.contains("`")) {
						nu_iter = true;
						lik.previous();
					}
					else {
						String target_thsms = Tool.returnThsmsGivenSmaleAndSmsKe(smawl, Integer.parseInt(sms));
						Vector vtmp = (Vector)lik.next();
						ListIterator lit = vtmp.listIterator();
						while(lit.hasNext()) {
							String info_mk = (String)lit.next();
							st = new StringTokenizer(info_mk,"`");
							String idkmk = st.nextToken();
							String kdkmk = st.nextToken();
							String nakmk = st.nextToken();
							String ttsks = st.nextToken();
							String skripsi = st.nextToken();
							String THSMSTRNLM = new String(target_thsms);
							String KDPTITRNLM = Constants.getKdpti();
							String KDJENTRNLM = new String(kdjen);
							String KDPSTTRNLM = new String(kdpst);
							String NPMHSTRNLM = new String(npmhs);
							String KDKMKTRNLM = new String(kdkmk);
							String NLAKHTRNLM = "T";
							String BOBOTTRNLM = "0";
							String SKSMKTRNLM = new String(ttsks);
							String KELASTRNLM = "1";
							String IDKMKTRNLM = new String(idkmk);
							stmt.setString(1,THSMSTRNLM);
							stmt.setString(2,KDPTITRNLM);
							stmt.setString(3,KDJENTRNLM);
							stmt.setString(4,KDPSTTRNLM);
							stmt.setString(5,NPMHSTRNLM);
							stmt.setString(6,KDKMKTRNLM);
							stmt.setString(7,NLAKHTRNLM);
							stmt.setDouble(8,Double.parseDouble(BOBOTTRNLM));
							stmt.setInt(9,Integer.parseInt(SKSMKTRNLM));
							stmt.setString(10,KELASTRNLM);
							stmt.setInt(11,Integer.parseInt(IDKMKTRNLM));
							stmt.executeUpdate();	
						}	
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
    	//return needByGetProfile;
    }
    
    public void insertTrlsmDariLayarBiru(Vector V_getInfoTrlsmFromLayarBiru, String thsms, String kdpst) {
    	
    	if(V_getInfoTrlsmFromLayarBiru!=null) {
    		try {
        		Context initContext  = new InitialContext();    		
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("insert into TRLSM (THSMS,KDPST,NPMHS,STMHS)values(?,?,?,?)");
        		ListIterator li = V_getInfoTrlsmFromLayarBiru.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String npmhs = st.nextToken();
        			String stmhs = st.nextToken();
        			stmt.setString(1, thsms);
        			stmt.setString(2, kdpst);
        			stmt.setString(3, npmhs);
        			stmt.setString(4, stmhs);
        			//System.out.print(brs);
        			int i  = stmt.executeUpdate();
        			//System.out.println("="+i);
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
    	}
    	   
    	//return needByGetProfile;
    }
    
    public void insertKrs(Vector v_getMkSesuaiAngkatanPerMhsDariSms1, String tkn_target_thsms, String kdpst) {
    	//Vector vf = new Vector();
    	//ListIterator lif = vf.listIterator();
    	String kdjen = Checker.getKdjen(kdpst);
    	if(v_getMkSesuaiAngkatanPerMhsDariSms1!=null && v_getMkSesuaiAngkatanPerMhsDariSms1.size()>0) {
    		try {
    			ListIterator li = v_getMkSesuaiAngkatanPerMhsDariSms1.listIterator();
        		Context initContext  = new InitialContext();    		
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("insert ignore into TRNLM (THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,KELASTRNLM,IDKMKTRNLM)values(?,?,?,?,?,?,?,?,?,?,?)");
        		ListIterator li2 = null;
        		
        		while(li.hasNext()) {
        			Object o = (Object)li.next();
        			if(o instanceof String) {
        				String brs1 = (String)o;
        				if(brs1.length()>10) {//biar diignore aja krn ini cuma pembatas info semesterke-
        					//System.out.println(brs1+"<br>");
        				}
        			}
        			else if(o instanceof Vector) {
        				Vector v_krs = (Vector)o;
        				  if(v_krs!=null && v_krs.size()>0) {
        					  li2 = v_krs.listIterator();
        					  while(li2.hasNext()) {
        						  String brs3 = (String)li2.next();
        						  StringTokenizer st = new StringTokenizer(tkn_target_thsms,"`");
        						  boolean match = false;
        						  while(st.hasMoreTokens() && !match) {
        							  String target_thsms = st.nextToken();
        							  if(brs3.startsWith(target_thsms)) {
        								  match = true;
        							  }
        						  }
        						  if(match) {
        							  //System.out.print(brs3+"="); 
        							  //20161`2020100000116`849`TE 4134`TUGAS AKHIR/SKRIPSI`4`true<br>
        							  st = new StringTokenizer(brs3,"`");
        							 
        							  String thsms = st.nextToken();
        							  String npmhs = st.nextToken();
        							  String idkmk = st.nextToken();
        							  String kdkmk = st.nextToken();
        							  String nakmk = st.nextToken();
        							  String sksmk = st.nextToken();
        							  String mk_akhir = st.nextToken();
        							  stmt.setString(1,thsms);
        							  stmt.setString(2,"031031");
        							  stmt.setString(3,kdjen);
        							  stmt.setString(4,kdpst);
        							  stmt.setString(5,npmhs);
        							  stmt.setString(6,kdkmk);
        							  stmt.setString(7,"T");
        							  stmt.setDouble(8,0);
        							  stmt.setInt(9,Integer.parseInt(sksmk));
        							  stmt.setString(10,"1");
        							  stmt.setInt(11,Integer.parseInt(idkmk));
        							  int i = stmt.executeUpdate();	
        							  //System.out.println(i);
        						  }
        						  
        					  }
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
    	}
    	
    	
    }
}

package beans.dbase.notification;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.Constant;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.ToJson;
import beans.tools.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.codehaus.jettison.json.JSONArray;

/**
 * Session Bean implementation class SearchDbMainNotification
 */
@Stateless
@LocalBean
public class SearchDbMainNotification extends SearchDb {
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
    public SearchDbMainNotification() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbMainNotification(String operatorNpm) {
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
    public SearchDbMainNotification(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public boolean getPengajuanUa(Vector vScopeUa, Vector vScopeUaA, int oprObjId) {
    	//Vector v = null;
    	/*
    	 *  vScopeUa = scope pengajuan = owner & monitoree
    	 *  vScopeUaA = scope approval
    	 */
    	boolean ada_pengajuan =false;
    	try {

    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
    		StringTokenizer st = null;
    		//bagian pengajuan dan monitoree
    		if(vScopeUa!=null && vScopeUa.size()>0) {
    			//System.out.println("vscope ua not null");
    			ListIterator li = vScopeUa.listIterator();	
    			String kdpst = "";
    			String sql_stmt = "";
    			kdpst = (String)li.next();
    			//System.out.println("kdpst ua ="+kdpst);
    			String usrObjNick = ""+this.tknOperatorNickname;//
    			if(kdpst.equalsIgnoreCase("own")) { 
    				/*
    				 * kalo own hanya untuk mhs , kalo monitoree pake scope
    				 */
    				//if(usrObjNick.contains("MHS")||usrObjNick.contains("mhs")) {
    					//bagian untul mahasiswa
    				//System.out.println("masuk owner");
    				sql_stmt = "select * from EXT_PENGAJUAN_UA where NPMHS=? and SHOW_OWNER=?"; 
					stmt = con.prepareStatement(sql_stmt);	
					stmt.setString(1, this.operatorNpm);
					stmt.setBoolean(2, true);
					rs = stmt.executeQuery();
					if(rs.next()) {
    					ada_pengajuan = true;
    				}
    				//}
    			}//end own
    			else { 
    				//bagian monitoree
    				kdpst = Tool.getTokenKe(kdpst, 1);
    				String sql_cmd = "IDOBJ="+kdpst+"";
    				
    				do {
    					if(li.hasNext()) {
    						kdpst = (String)li.next();
        					kdpst = Tool.getTokenKe(kdpst, 1);
        					sql_cmd = sql_cmd+" or IDOBJ="+kdpst+"";	
    					}
    				}
    				while(li.hasNext());
    				sql_stmt = "select * from EXT_PENGAJUAN_UA where SHOW_MONITOREE=? and ("+sql_cmd+") limit 1";
    				//System.out.println(sql_stmt);
    				stmt = con.prepareStatement(sql_stmt);
    				stmt.setBoolean(1, true);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					ada_pengajuan = true;
    				}
    			}
    		}
    		else {
    			//System.out.println("vscope ua  null");
    		}
    		
    		if(!ada_pengajuan) {
    			//System.out.println("masuk sini dong");
    			//kalo masih blum ada pengajuan - cek bagian approvee
    			if(vScopeUaA!=null && vScopeUaA.size()>0) {
    				//System.out.println("vscope UAA not null");
        			ListIterator li = vScopeUaA.listIterator();	
        			String kdpst = "";
        			String sql_stmt = "";
        			kdpst = (String)li.next();
        			String usrObjNick = ""+this.tknOperatorNickname;
        			if(kdpst.equalsIgnoreCase("own") && false) {//berarti penguji ujian
        				/*
        				 * UTK SAAT INI BELUM DIGUNAKAN KAREN APPROVAL HANYA VIA BAA & BAUK
        				 */
        				
        				//cari npmmhs yg jatahnya
        				stmt = con.prepareStatement("select NPMHSMSMSH from CIVITAS where STMHSMSMHS='A' and (NOPRMMSMHS=? or NOKP1MSMHS=? or NOKP2MSMHS=? or NOKP3MSMHS=? or NOKP4MSMHS=?)");
    					stmt.setString(1, this.operatorNpm);
    					stmt.setString(2, this.operatorNpm);
    					stmt.setString(3, this.operatorNpm);
    					stmt.setString(4, this.operatorNpm);
    					rs = stmt.executeQuery();
    					Vector vNpm = new Vector();;
    					ListIterator liNpm = vNpm.listIterator();
    					while(rs.next()) {
    						String npm = rs.getString(1);
    						liNpm.add(npm);
    					}
    					
    					if(vNpm.size()>0) {
    						
        					stmt = con.prepareStatement("select * from EXT_PENGAJUAN_UA where NPMHS=? and SHOW_APPROVEE=?");	
    						liNpm = vNpm.listIterator();
    						while(liNpm.hasNext() && !ada_pengajuan) {
    							String npm = (String)liNpm.next();
    							stmt.setString(1, this.operatorNpm);
    	    					stmt.setBoolean(2, true);
    	    					rs = stmt.executeQuery();
    	    					if(rs.next()) {
    	    						ada_pengajuan = true;
    	    					}
    						}
    					}	
        			}
        			else {
        				/*
        				 * YG DITAMPILKAN ADALAH PENGAJUAN YG BELUM DEBERIKAN TINDAKAN OLEH 
        				 * PIHAK TERKAIT, BILA BISA ADA PENGGANTI BLUM DIPERHITUNGKAN, DIA
        				 * SEPERTI MONITOREE DAN BARU HILANG BILA SUDAH ADA JADWAL UJIAN
        				 */
        				kdpst = Tool.getTokenKe(kdpst, 1);
        				String sql_cmd = "IDOBJ="+kdpst+"";
        				do {
        					if(li.hasNext()) {
        						kdpst = (String)li.next();	
            					kdpst = Tool.getTokenKe(kdpst, 1);
            					sql_cmd = sql_cmd+" or IDOBJ="+kdpst+"";	
        					}
        				}
        				while(li.hasNext());
        				sql_stmt = "select * from EXT_PENGAJUAN_UA where (TKN_ID_APPROVEE is NULL or TKN_ID_APPROVEE NOT LIKE ?) and ("+sql_cmd+") limit 1";
        				//System.out.println(sql_stmt);
        				stmt = con.prepareStatement(sql_stmt);
        				stmt.setString(1, "%/"+oprObjId+"`%");
        				//System.out.println("%/"+oprObjId+"`%");
        				rs = stmt.executeQuery();
        				if(rs.next()) {
        					ada_pengajuan = true;
        				}
        				else {
        					//System.out.println("nope next");
        				}
        			}
        				
    			}
    			else {
    				//System.out.println("vscope UAA  null");
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
    	return ada_pengajuan;
    }
    //deprecated
    public boolean getNotificationPengajuanCuti() {
    	//Vector v = null;
    	/*
    	 *  vScopeUa = scope pengajuan = owner & monitoree
    	 *  vScopeUaA = scope approval
    	 */
    	long thisObjId = Checker.getObjectId(this.operatorNpm);
    	boolean ada_pengajuan =false;
    	try {

    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//cek sebagai approvee
    		stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TIPE_PENGAJUAN=? and TOKEN_TARGET_OBJID like ? and SHOW_AT_TARGET like ? ");
    		stmt.setString(1, "CUTI");
    		stmt.setString(2, "%["+thisObjId+"]%");
    		stmt.setString(3, "%["+thisObjId+"]%");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			ada_pengajuan = true;
    		}
    		if(!ada_pengajuan) {
    			//check sbg creator
    			stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TIPE_PENGAJUAN=? and CREATOR_NPM=? and SHOW_AT_CREATOR=? ");
    			stmt.setString(1, "CUTI");
        		stmt.setString(2, this.operatorNpm);
        		stmt.setBoolean(3, true);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			ada_pengajuan = true;
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
    	return ada_pengajuan;
    }
    
    
    public boolean cekTableRulesForNullApproveeId(Vector v_scope_id) {
    	String thsms_reg = Checker.getThsmsHeregistrasi();
    	boolean show = false;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {

        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			/*
    			 * cek yg ada di OBJID saja
    			 * 
    			 */
    			Vector v = new Vector();
    			ListIterator li = v.listIterator();
    			stmt = con.prepareStatement("select * from OBJECT where SISTEM_PERKULIAHAN is not null");
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String id = ""+rs.getInt("ID_OBJ");
    				String kdpst = ""+rs.getString("KDPST");
    				String kode = ""+rs.getString("KODE_KAMPUS_DOMISILI");
    				li.add(id+"`"+kdpst+"`"+kode);
    			}
    			//filter sesuai scope
    			li  = v.listIterator();
				while(li.hasNext()) {
					String baris = (String)li.next();
					boolean match = false;
					ListIterator lis = v_scope_id.listIterator();
	    			while(lis.hasNext() && !match) {
	    				String brs = (String)lis.next();
	    				StringTokenizer st = new StringTokenizer(brs,"`");
	    				String kmp = st.nextToken();
	    				while(st.hasMoreTokens() && !match) {
	    					String scope_id = st.nextToken();
	    					if(baris.startsWith(scope_id)) {
	    						match = true;
	    					}
	    				}
	    			}
	    			if(!match) {
	    				li.remove();
	    			}
				}
    			
    				
    			
    			if(v.size()>0) {
    				stmt = con.prepareStatement("select * from HEREGISTRASI_RULES where THSMS=? and KDPST=? and KODE_KAMPUS=? and TKN_VERIFICATOR_ID like ? limit 1");
    				li=v.listIterator();
    				while(li.hasNext() && !show) {
    					String brs = (String)li.next();
    					//System.out.println("--="+brs);
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String id = st.nextToken();
    					String kdpst = st.nextToken();
    					String kode = st.nextToken();
    					stmt.setString(1, thsms_reg);
    					stmt.setString(2, kdpst);
    					stmt.setString(3, kode);
    					stmt.setString(4, "%null%");
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						show = true;
    					}
    				}
    				if(!show) {
    					stmt = con.prepareStatement("select * from PINDAH_PRODI_RULES where THSMS=? and KDPST=? and KODE_KAMPUS=? and TKN_VERIFICATOR_ID like ? limit 1");
    					li=v.listIterator();
    					while(li.hasNext() && !show) {
    						String brs = (String)li.next();
    						StringTokenizer st = new StringTokenizer(brs,"`");
    						String id = st.nextToken();
    						String kdpst = st.nextToken();
    						String kode = st.nextToken();
    						stmt.setString(1, thsms_reg);
    						stmt.setString(2, kdpst);
    						stmt.setString(3, kode);
    						stmt.setString(4, "%null%");
    						rs = stmt.executeQuery();
    						if(rs.next()) {
    							show = true;
    						}
    					}
    				}
    				if(!show) {
    					stmt = con.prepareStatement("select * from CUTI_RULES where THSMS=? and KDPST=? and KODE_KAMPUS=? and TKN_VERIFICATOR_ID like ? limit 1");
    					li=v.listIterator();
    					while(li.hasNext() && !show) {
    						String brs = (String)li.next();
    						StringTokenizer st = new StringTokenizer(brs,"`");
    						String id = st.nextToken();
    						String kdpst = st.nextToken();
    						String kode = st.nextToken();
    						stmt.setString(1, thsms_reg);
    						stmt.setString(2, kdpst);
    						stmt.setString(3, kode);
    						stmt.setString(4, "%null%");
    						rs = stmt.executeQuery();
    						if(rs.next()) {
    							show = true;
    						}
    					}
    				}
    				if(!show) {
    					stmt = con.prepareStatement("select * from KELAS_PERKULIAHAN_RULES where THSMS=? and KDPST=? and KODE_KAMPUS=? and TKN_VERIFICATOR_ID like ? limit 1");
    					li=v.listIterator();
    					while(li.hasNext() && !show) {
    						String brs = (String)li.next();
    						StringTokenizer st = new StringTokenizer(brs,"`");
    						String id = st.nextToken();
    						String kdpst = st.nextToken();
    						String kode = st.nextToken();
    						stmt.setString(1, thsms_reg);
    						stmt.setString(2, kdpst);
    						stmt.setString(3, kode);
    						stmt.setString(4, "%null%");
    						rs = stmt.executeQuery();
    						if(rs.next()) {
    							show = true;
    						}
    					}
    				}
    				if(!show) {
    	    			stmt = con.prepareStatement("select * from DAFTAR_ULANG where TKN_ID_APPROVAL like ? limit 1");
    	    			stmt.setString(1, "%null%");
    		    		rs = stmt.executeQuery();
    		    		if(rs.next()) {
    		    			show = true;
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
    	return show;
    }
    
    public Vector cekTableRulesForNullApproveeId_complete(Vector v_scope_id, String thsms_reg) {
    	//String thsms_reg = Checker.getThsmsHeregistrasi();
    	Vector v = new Vector();;
    	Vector vf = null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {

        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			/*
    			 * cek yg ada di OBJID saja
    			 * 
    			 */
    			
    			ListIterator li = v.listIterator();
    			stmt = con.prepareStatement("select * from OBJECT where SISTEM_PERKULIAHAN is not null");
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String id = ""+rs.getInt("ID_OBJ");
    				String kdpst = ""+rs.getString("KDPST");
    				String kode = ""+rs.getString("KODE_KAMPUS_DOMISILI");
    				li.add(id+"`"+kdpst+"`"+kode);
    			}
    			//filter sesuai scope
    			li  = v.listIterator();
				while(li.hasNext()) {
					String baris = (String)li.next();
					boolean match = false;
					ListIterator lis = v_scope_id.listIterator();
	    			while(lis.hasNext() && !match) {
	    				String brs = (String)lis.next();
	    				StringTokenizer st = new StringTokenizer(brs,"`");
	    				String kmp = st.nextToken();
	    				while(st.hasMoreTokens() && !match) {
	    					String scope_id = st.nextToken();
	    					if(baris.startsWith(scope_id)) {
	    						match = true;
	    					}
	    				}
	    			}
	    			if(!match) {
	    				li.remove();
	    			}
				}
    			
    				
    			
    			if(v.size()>0) {
    				vf = new Vector();
    				ListIterator lif = vf.listIterator();
    				String list = "HEREGISTRASI_RULES";
    				stmt = con.prepareStatement("select * from HEREGISTRASI_RULES where THSMS=? and KDPST=? and KODE_KAMPUS=? and TKN_VERIFICATOR_ID like ?");
    				li=v.listIterator();
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					//System.out.println("--="+brs);
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String id = st.nextToken();
    					String kdpst = st.nextToken();
    					String kode = st.nextToken();
    					stmt.setString(1, thsms_reg);
    					stmt.setString(2, kdpst);
    					stmt.setString(3, kode);
    					stmt.setString(4, "%null%");
    					rs = stmt.executeQuery();
    					while(rs.next()) {
    						//String kdpst = ""+rs.getString("KDPST");
    						String tkn_jab = ""+rs.getString("TKN_JABATAN_VERIFICATOR");
    						String tkn_id = ""+rs.getString("TKN_VERIFICATOR_ID");
    						String urut = ""+rs.getBoolean("URUTAN");
    						//String kmp = ""+rs.getString("KODE_KAMPUS");
    						list = list+"`"+brs+"`"+tkn_jab+"`"+tkn_id;
    					}
    				}
    				lif.add(list);
    				//if(!show) {
    				stmt = con.prepareStatement("select * from PINDAH_PRODI_RULES where THSMS=? and KDPST=? and KODE_KAMPUS=? and TKN_VERIFICATOR_ID like ? limit 1");
					li=v.listIterator();
					list = "PINDAH_PRODI_RULES";
					while(li.hasNext()) {
						String brs = (String)li.next();
						StringTokenizer st = new StringTokenizer(brs,"`");
						String id = st.nextToken();
						String kdpst = st.nextToken();
						String kode = st.nextToken();
						stmt.setString(1, thsms_reg);
						stmt.setString(2, kdpst);
						stmt.setString(3, kode);
						stmt.setString(4, "%null%");
						rs = stmt.executeQuery();
						while(rs.next()) {
    						//String kdpst = ""+rs.getString("KDPST");
    						String tkn_jab = ""+rs.getString("TKN_JABATAN_VERIFICATOR");
    						String tkn_id = ""+rs.getString("TKN_VERIFICATOR_ID");
    						String urut = ""+rs.getBoolean("URUTAN");
    						//String kmp = ""+rs.getString("KODE_KAMPUS");
    						list = list+"`"+brs+"`"+tkn_jab+"`"+tkn_id;
    					}
    				}
    				lif.add(list);
    				//}
    				stmt = con.prepareStatement("select * from CUTI_RULES where THSMS=? and KDPST=? and KODE_KAMPUS=? and TKN_VERIFICATOR_ID like ? limit 1");
					li=v.listIterator();
					list = "CUTI_RULES";
					while(li.hasNext()) {
						String brs = (String)li.next();
						StringTokenizer st = new StringTokenizer(brs,"`");
						String id = st.nextToken();
						String kdpst = st.nextToken();
						String kode = st.nextToken();
						stmt.setString(1, thsms_reg);
						stmt.setString(2, kdpst);
						stmt.setString(3, kode);
						stmt.setString(4, "%null%");
						rs = stmt.executeQuery();
						while(rs.next()) {
    						//String kdpst = ""+rs.getString("KDPST");
    						String tkn_jab = ""+rs.getString("TKN_JABATAN_VERIFICATOR");
    						String tkn_id = ""+rs.getString("TKN_VERIFICATOR_ID");
    						String urut = ""+rs.getBoolean("URUTAN");
    						//String kmp = ""+rs.getString("KODE_KAMPUS");
    						list = list+"`"+brs+"`"+tkn_jab+"`"+tkn_id;
    					}
					}
					lif.add(list);
					
					stmt = con.prepareStatement("select * from KELAS_PERKULIAHAN_RULES where THSMS=? and KDPST=? and KODE_KAMPUS=? and TKN_VERIFICATOR_ID like ? limit 1");
					li=v.listIterator();
					list = "KELAS_PERKULIAHAN_RULES";
					while(li.hasNext()) {
						String brs = (String)li.next();
						StringTokenizer st = new StringTokenizer(brs,"`");
						String id = st.nextToken();
						String kdpst = st.nextToken();
						String kode = st.nextToken();
						stmt.setString(1, thsms_reg);
						stmt.setString(2, kdpst);
						stmt.setString(3, kode);
						stmt.setString(4, "%null%");
						rs = stmt.executeQuery();
						while(rs.next()) {
    						//String kdpst = ""+rs.getString("KDPST");
    						String tkn_jab = ""+rs.getString("TKN_JABATAN_VERIFICATOR");
    						String tkn_id = ""+rs.getString("TKN_VERIFICATOR_ID");
    						String urut = ""+rs.getBoolean("URUTAN");
    						//String kmp = ""+rs.getString("KODE_KAMPUS");
    						list = list+"`"+brs+"`"+tkn_jab+"`"+tkn_id;
    					}
					}
					lif.add(list);
					
					list = "DAFTAR_ULANG";
					stmt = con.prepareStatement("select * from DAFTAR_ULANG where TKN_ID_APPROVAL like ? limit 1");
	    			stmt.setString(1, "%null%");
		    		rs = stmt.executeQuery();
		    		while(rs.next()) {
						//String kdpst = ""+rs.getString("KDPST");
		    			String kdpst = ""+rs.getString("KDPST");
		    			String id = ""+rs.getInt("ID_OBJ");
						String tkn_id = ""+rs.getString("TKN_ID_APPROVAL");
						String tkn_jab = ""+rs.getString("TKN_JABATAN_APPROVAL");
						
						//String kmp = ""+rs.getString("KODE_KAMPUS");
						list = list+"`"+id+"`"+kdpst+"`"+tkn_jab+"`"+tkn_id;
					}
		    		lif.add(list);
				
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
    
    public boolean isTherePengajuanCuti(Vector v_scope_id) {
    	//Vector v = null;
    	/*
    	 *  vScopeUa = scope pengajuan = owner & monitoree
    	 *  vScopeUaA = scope approval
    	 */
    	
    	String thsms_now = Checker.getThsmsNow();
    	boolean ada_pengajuan =false;
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
    					
    					
    					//if(st.hasMoreTokens()) {
    					//	addon_cmd = addon_cmd+" OR ";
    					//}
    				}
    				if(!sy_mhs) {
    					//cek sebagai approvee
    	    			String cmd = "";
    	    			if(addon_cmd!=null && !Checker.isStringNullOrEmpty(addon_cmd)) {
    	    				cmd = "select ID_OBJ from OVERVIEW_SEBARAN_TRLSM where THSMS=? and  LIST_NPM_CUTI_UNAPPROVED is not null and ("+addon_cmd+") limit 1";
    	    				//System.out.println("cmd="+cmd);
    	            		stmt = con.prepareStatement(cmd);
    	            		stmt.setString(1,thsms_now);
    	            		rs = stmt.executeQuery();
    	            		if(rs.next()) {
    	            			ada_pengajuan = true;
    	            		}
    	    			}
    				}
    				else {
    					stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_NPM=? and SHOW_AT_CREATOR=?");
    					stmt.setString(1, thsms_now);
    					stmt.setString(2, "CUTI");
    					stmt.setString(3, this.operatorNpm);
    					stmt.setBoolean(4, true);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						ada_pengajuan=true;
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
    	return ada_pengajuan;
    }
    
    public boolean isThereNilaiTunda(Vector v_scope_id) {
    	
    	
    	String thsms_now = Checker.getThsmsInputNilai();
    	boolean ada_nilai_tunda =false;
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
    					
    					
    					//if(st.hasMoreTokens()) {
    					//	addon_cmd = addon_cmd+" OR ";
    					//}
    				}
    				if(!sy_mhs) {
    					//cek sebagai approvee
    	    			String cmd = "";
    	    			if(addon_cmd!=null && !Checker.isStringNullOrEmpty(addon_cmd)) {
    	    				cmd = "select ID_OBJ from OVERVIEW_SEBARAN_TRLSM where THSMS=? and  NILAI_TUNDA=? and ("+addon_cmd+") limit 1";
    	    				//System.out.println("cmd="+cmd);
    	            		stmt = con.prepareStatement(cmd);
    	            		stmt.setString(1,thsms_now);
    	            		stmt.setBoolean(2, true);
    	            		rs = stmt.executeQuery();
    	            		if(rs.next()) {
    	            			ada_nilai_tunda = true;
    	            		}
    	    			}
    				}
    				else {
    					//gnore bila mhs
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
    	return ada_nilai_tunda;
    }
    
    public boolean belumAdakrsPenyetaraan(Vector v_scope_id, String starting_smawl) {
    	//String thsms_now = Checker.getThsmsNow();
    	boolean ada =false;
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
        						addon_cmd = addon_cmd+" OR CIVITAS.ID_OBJ="+id;
        					}
        					else {
        						//first record
        						addon_cmd = addon_cmd+"CIVITAS.ID_OBJ="+id;	
        					}
    					}

    				}
    				if(!sy_mhs) {
    					//cek sebagai approvee
    	    			String cmd = "";
    	    			if(addon_cmd!=null && !Checker.isStringNullOrEmpty(addon_cmd)) {
    	    				//cmd = "select ID_OBJ from OVERVIEW_SEBARAN_TRLSM where THSMS=? and  KRS_PINDAHAN=? and ("+addon_cmd+") limit 1";
    	    				cmd="SELECT CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS,CIVITAS.NMMHSMSMHS,CIVITAS.SMAWLMSMHS FROM CIVITAS  left join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ left join TRNLP on NPMHSMSMHS=NPMHSTRNLP where SMAWLMSMHS>=? and STPIDMSMHS='P' and NPMHSTRNLP is null and  ("+addon_cmd+")  order by CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS limit 1";
    	    				//cmd="SELECT NPMHSMSMHS FROM CIVITAS left join TRNLP on NPMHSMSMHS=NPMHSTRNLP where STPIDMSMHS='P' and NPMHSTRNLP is null and ("+addon_cmd+") limit 1";
    	    				stmt = con.prepareStatement(cmd);
    	    				stmt.setString(1,starting_smawl);
    	            		
    	            		rs = stmt.executeQuery();
    	            		if(rs.next()) {
    	            			ada = true;
    	            		}
    	    			}
    				}
    				/*
    				else {
    					stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_NPM=? and SHOW_AT_CREATOR=?");
    					stmt.setString(1, thsms_now);
    					stmt.setString(2, "CUTI");
    					stmt.setString(3, this.operatorNpm);
    					stmt.setBoolean(4, true);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						ada_pengajuan=true;
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
    	return ada;
    }
    
    public boolean adaMakulAsalTpBlumDisetarakan(Vector v_scope_id, String starting_smawl) {
    	//String thsms_now = Checker.getThsmsNow();
    	boolean ada =false;
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
        						addon_cmd = addon_cmd+" OR CIVITAS.ID_OBJ="+id;
        					}
        					else {
        						//first record
        						addon_cmd = addon_cmd+"CIVITAS.ID_OBJ="+id;	
        					}
    					}

    				}
    				if(!sy_mhs) {
    					//cek sebagai approvee
    	    			String cmd = "";
    	    			Vector v = new Vector();
    	    			ListIterator li1 = v.listIterator();
    	    			if(addon_cmd!=null && !Checker.isStringNullOrEmpty(addon_cmd)) {
    	    				
    	    				//cmd = "select ID_OBJ from OVERVIEW_SEBARAN_TRLSM where THSMS=? and  KRS_PINDAHAN=? and ("+addon_cmd+") limit 1";
    	    				//cmd="SELECT CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS,CIVITAS.NMMHSMSMHS,CIVITAS.SMAWLMSMHS FROM CIVITAS  left join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ left join TRNLP on NPMHSMSMHS=NPMHSTRNLP where SMAWLMSMHS>=? and STPIDMSMHS='P' and NPMHSTRNLP is null and  ("+addon_cmd+")  order by CIVITAS.KDPSTMSMHS,CIVITAS.NPMHSMSMHS limit 1";
    	    				cmd = "SELECT distinct NPMHSMSMHS FROM CIVITAS a inner join OBJECT b on a.ID_OBJ=b.ID_OBJ where OBJ_NAME='MHS' and STPIDMSMHS='P' and SMAWLMSMHS>=?";
    	    				//cmd="SELECT NPMHSMSMHS FROM CIVITAS left join TRNLP on NPMHSMSMHS=NPMHSTRNLP where STPIDMSMHS='P' and NPMHSTRNLP is null and ("+addon_cmd+") limit 1";
    	    				stmt = con.prepareStatement(cmd);
    	    				stmt.setString(1,starting_smawl);
    	            		
    	            		rs = stmt.executeQuery();
    	            		while(rs.next()) {
    	            			String npmhs = rs.getString(1); 
    	            			li1.add(npmhs);
    	            			//System.out.println("mhs pindahan="+npmhs);
    	            		}
    	    			}
    	    			if(v.size()>0) {
    	    				li1 = v.listIterator();
    	    				stmt = con.prepareStatement("select KDKMKTRNLP from TRNLP where NPMHSTRNLP=? and TRANSFERRED=true limit 1");
    	    				while(li1.hasNext()&&!ada) {
    	    					String npmhs = (String)li1.next();
    	    					stmt.setString(1, npmhs);
    	    					rs = stmt.executeQuery();
    	    					if(!rs.next()) {
    	    						ada = true;
    	    					}
    	    				}
    	    			}
    				}
    				/*
    				else {
    					stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_NPM=? and SHOW_AT_CREATOR=?");
    					stmt.setString(1, thsms_now);
    					stmt.setString(2, "CUTI");
    					stmt.setString(3, this.operatorNpm);
    					stmt.setBoolean(4, true);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						ada_pengajuan=true;
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
    	return ada;
    }
    
    /*
     * ERROR ini sama dengan cekMhsDgnDataProfilIncomplete
     */
    public boolean cekMhsPindahanBlumAdaPenyetaraan(Vector v_scope_id, String starting_smawl) {
    	//String thsms_now = Checker.getThsmsNow();
    	boolean ada =false;
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
    				if(!sy_mhs) {
    					//cek sebagai approvee
    	    			String cmd = "";
    	    			
    	    			if(addon_cmd!=null && !Checker.isStringNullOrEmpty(addon_cmd)) {
    	    				cmd = "SELECT CIVITAS.NPMHSMSMHS from CIVITAS inner join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where SMAWLMSMHS>=? and (CIVITAS.TPLHRMSMHS is NULL or CIVITAS.TGLHRMSMHS is NULL or CIVITAS.NAMA_IBU is NULL or CIVITAS.NAMA_AYAH is NULL or CIVITAS.KEWARGANEGARAAN is NULL or CIVITAS.NIKTPMSMHS is NULL or EXT_CIVITAS.EMAILMSMHS is NULL or EXT_CIVITAS.NOHPEMSMHS is NULL or EXT_CIVITAS.ALMRMMSMHS is NULL or EXT_CIVITAS.PROVRMMSMHS is NULL or EXT_CIVITAS.PROVRMIDWIL is NULL or EXT_CIVITAS.KOTRMMSMHS is NULL or EXT_CIVITAS.KOTRMIDWIL is NULL or EXT_CIVITAS.KECRMMSMHS is NULL or EXT_CIVITAS.KECRMIDWIL is NULL or EXT_CIVITAS.KELRMMSMHS is NULL or EXT_CIVITAS.TELRMMSMHS is NULL or EXT_CIVITAS.NEGLHMSMHS is NULL) and  ("+addon_cmd+") limit 1";
    	    				stmt = con.prepareStatement(cmd);
    	    				stmt.setString(1, starting_smawl);
    	            		rs = stmt.executeQuery();
    	            		if(rs.next()) {
    	            			ada = true;
    	            		}
    	    			}
    				}
    				/*
    				else {
    					stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_NPM=? and SHOW_AT_CREATOR=?");
    					stmt.setString(1, thsms_now);
    					stmt.setString(2, "CUTI");
    					stmt.setString(3, this.operatorNpm);
    					stmt.setBoolean(4, true);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						ada_pengajuan=true;
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
    	return ada;
    }
    
    public boolean cekMhsDgnDataProfilIncomplete(Vector v_scope_id, String starting_smawl) {
    	//String thsms_now = Checker.getThsmsNow();
    	boolean ada =false;
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
    				if(!sy_mhs) {
    					//cek sebagai approvee
    	    			String cmd = "";
    	    			
    	    			if(addon_cmd!=null && !Checker.isStringNullOrEmpty(addon_cmd)) {
    	    				cmd = "SELECT CIVITAS.NPMHSMSMHS from CIVITAS inner join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where SMAWLMSMHS>=? and (CIVITAS.TPLHRMSMHS is NULL or CIVITAS.TGLHRMSMHS is NULL or CIVITAS.NAMA_IBU is NULL or CIVITAS.NAMA_AYAH is NULL or CIVITAS.KEWARGANEGARAAN is NULL or CIVITAS.NIKTPMSMHS is NULL or EXT_CIVITAS.EMAILMSMHS is NULL or EXT_CIVITAS.NOHPEMSMHS is NULL or EXT_CIVITAS.ALMRMMSMHS is NULL or EXT_CIVITAS.PROVRMMSMHS is NULL or EXT_CIVITAS.PROVRMIDWIL is NULL or EXT_CIVITAS.KOTRMMSMHS is NULL or EXT_CIVITAS.KOTRMIDWIL is NULL or EXT_CIVITAS.KECRMMSMHS is NULL or EXT_CIVITAS.KECRMIDWIL is NULL or EXT_CIVITAS.KELRMMSMHS is NULL or EXT_CIVITAS.TELRMMSMHS is NULL or EXT_CIVITAS.NEGLHMSMHS is NULL) and  ("+addon_cmd+") limit 1";
    	    				stmt = con.prepareStatement(cmd);
    	    				stmt.setString(1, starting_smawl);
    	            		rs = stmt.executeQuery();
    	            		if(rs.next()) {
    	            			ada = true;
    	            		}
    	    			}
    				}
    				/*
    				else {
    					stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_NPM=? and SHOW_AT_CREATOR=?");
    					stmt.setString(1, thsms_now);
    					stmt.setString(2, "CUTI");
    					stmt.setString(3, this.operatorNpm);
    					stmt.setBoolean(4, true);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						ada_pengajuan=true;
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
    	return ada;
    }
 
    /*
    public boolean isTherePengajuanPindahProdi() {
    	//1. apakah saya approvee - cek ke tabel rule terkait
    	
    	
    	String thsms_now = Checker.getThsmsNow();
    	boolean ada_pengajuan =false;
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
    					
    					
    					//if(st.hasMoreTokens()) {
    					//	addon_cmd = addon_cmd+" OR ";
    					//}
    				}
    				if(!sy_mhs) {
    					//cek sebagai approvee
    	    			String cmd = "";
    	    			if(addon_cmd!=null && !Checker.isStringNullOrEmpty(addon_cmd)) {
    	    				cmd = "select ID_OBJ from OVERVIEW_SEBARAN_TRLSM where THSMS=? and  LIST_NPM_CUTI_UNAPPROVED is not null and ("+addon_cmd+") limit 1";
    	    				//System.out.println("cmd="+cmd);
    	            		stmt = con.prepareStatement(cmd);
    	            		stmt.setString(1,thsms_now);
    	            		rs = stmt.executeQuery();
    	            		if(rs.next()) {
    	            			ada_pengajuan = true;
    	            		}
    	    			}
    				}
    				else {
    					stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_NPM=? and SHOW_AT_CREATOR=?");
    					stmt.setString(1, thsms_now);
    					stmt.setString(2, "CUTI");
    					stmt.setString(3, this.operatorNpm);
    					stmt.setBoolean(4, true);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						ada_pengajuan=true;
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
    	return ada_pengajuan;
    }
    */
    
    public boolean isTherePengajuan(String scope_pengajuan_terkait, Vector v_scope_pengajuan_terkait, String full_name_table_rules, String target_thsms, long usr_objid) {
    	//siapa yg berhak mengetahui:
    	//1. mhs terkait
    	//2. approvee
    	//3. yg punya scope terkait
    	boolean ada = false;
    	//System.out.println("target_thsms="+target_thsms+"-"+full_name_table_rules);
    	String tipe_pengajuan = full_name_table_rules.replace("_RULES", "");
    	try {

    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//1. proses mahasiswa
	    	if(!petugas) {
	    		//MHS
	    		/*
	    		stmt = con.prepareStatement("select LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED from OVERVIEW_SEBARAN_TRLSM where THSMS=? and LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED like ? limit 1");
	    		stmt.setString(1, target_thsms);
	    		stmt.setString(2, "%"+operatorNpm+"-%");
	    		rs = stmt.executeQuery();
	    		if(rs.next()) {
	    			ada = true;
	    		}
	    		*/
	    		if(!ada) {
	    			//cek topik bila ada show at creator karen bila ditolak maka otomatis npm terhapus dari overview table sehingga tidak terdetek oleh command diatas
	    			stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_NPM=? and SHOW_AT_CREATOR=?");
	    			stmt.setString(1, target_thsms);
	    			stmt.setString(2, tipe_pengajuan);
	    			stmt.setString(3, operatorNpm);
	    			stmt.setBoolean(4, true);
	    			rs = stmt.executeQuery();
	    			if(rs.next()) {
	    				ada = true;
	    			}
	    		}
	    	}
	    	else {
	    		//3. scopee
	    		//System.out.println("petugas1");
	    		if(v_scope_pengajuan_terkait!=null && v_scope_pengajuan_terkait.size()>0) {
	    			//System.out.println("cek scope");
	    			ListIterator li = v_scope_pengajuan_terkait.listIterator();
	    			String cmd_sql = "select ID_OBJ from OVERVIEW_SEBARAN_TRLSM where (";
	    			boolean first=true;
	    			while(li.hasNext()) {
	    				String brs = (String)li.next();
	    				//System.out.println("brs="+brs);
	    				StringTokenizer st = new StringTokenizer(brs,"`");
	    				if(st.countTokens()>1) {
	    					if(first) {
	    						first = false;
	    					}
	    					else {
	    						cmd_sql = cmd_sql + " OR ";
	    					}
	    					st.nextToken();//ignore nama kampus
		    				while(st.hasMoreTokens()) {
		    					String id = st.nextToken();
		    					cmd_sql = cmd_sql + "ID_OBJ="+id;
		    					if(st.hasMoreTokens()) {
		    						cmd_sql = cmd_sql + " OR ";
		    					}
		    				}
	    				}
	    				//else {
	    				//	
	    				//}
	    				//if(li.hasNext()) {
	    				//	cmd_sql = cmd_sql + " OR ";
	    				//}
	    			}
	    			//System.out.println("line 1022:<br>scope="+scope_pengajuan_terkait+"<br>cmd : "+cmd_sql+") and THSMS=? and LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED is NOT NULL limit 1");
	    			stmt = con.prepareStatement(cmd_sql+") and THSMS=? and LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED is NOT NULL limit 1");
	    			stmt.setString(1,target_thsms);
	    			rs = stmt.executeQuery();
	    			if(rs.next()) {
	    				ada = true;
	    			}
	    			//System.out.println("ada="+ada);
	    		}
	    		else {
	    			//System.out.println("kosong");
	    		}
	    		if(!ada) {
	    			
	    			//2. cek approvee

	    			//System.out.println("cek approvee");
	    			//System.out.println("usr_objid="+usr_objid);
	    			stmt = con.prepareStatement("select KDPST,KODE_KAMPUS from "+full_name_table_rules+" where THSMS=? and TKN_VERIFICATOR_ID like ?");
	    			stmt.setString(1, target_thsms);
	    			stmt.setString(2, "%["+usr_objid+"]%");
	    			rs = stmt.executeQuery();
	    			if(rs.next()) {
	    				//System.out.println("cek approvee next");
	    				Vector v = new Vector();
	    				ListIterator li = v.listIterator();
	    				do {
	    					String kdpst = rs.getString(1);
	    					String kmp = rs.getString(2);
	    					li.add(kdpst+"`"+kmp);
	    				}
	    				while(rs.next());
	    				
	    				li=v.listIterator();
	    				stmt = con.prepareStatement("select ID_OBJ from OBJECT where KDPST=? and KODE_KAMPUS_DOMISILI=?");
	    				while(li.hasNext()) {
	    					String brs = (String)li.next();
	    					StringTokenizer st = new StringTokenizer(brs,"`");
	    					String kdpst = st.nextToken();
	    					String kmp = st.nextToken();
	    					//System.out.println(kdpst+"~"+kmp);
	    					stmt.setString(1,kdpst);
	    					stmt.setString(2,kmp);
	    					rs = stmt.executeQuery();
	    					if(rs.next()) {
		    					long id = rs.getLong(1);
		    					li.set(""+id);	    						
	    					}
	    					else {
	    						li.remove();
	    					}

	    				}
	    				li = v.listIterator();
	    				String cmd_sql = "select ID_OBJ from OVERVIEW_SEBARAN_TRLSM where (";
		    			while(li.hasNext()) {
		    				
		    				String id = (String)li.next();
		    				cmd_sql = cmd_sql + "ID_OBJ="+id;
		    				if(li.hasNext()) {
		    					cmd_sql = cmd_sql + " OR ";
		    				}
		    			
		    			}
		    			//System.out.println("~ "+cmd_sql+") and THSMS=? and LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED is NOT NULL limit 1");
		    			stmt = con.prepareStatement(cmd_sql+") and THSMS=? and LIST_NPM_"+tipe_pengajuan+"_UNAPPROVED is NOT NULL limit 1");
		    			stmt.setString(1,target_thsms);
		    			rs = stmt.executeQuery();
		    			if(rs.next()) {
		    				//System.out.println("next");
		    				ada = true;
		    			}
		    			else {
		    				//System.out.println("!next");
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
    	

    	return ada;
    }
    
    
    public boolean statusPengajuanHidden(String full_name_table_rules, String target_thsms, int usr_objid) {
    	//siapa yg berhak mengetahui:
    	//1. mhs terkait
    	//2. approvee
    	//3. yg punya scope terkait
    	boolean hidden = true;
    	//System.out.println("target_thsms="+target_thsms);
    	String tipe_pengajuan = full_name_table_rules.replace("_RULES", "");
    	try {

    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//1. proses mahasiswa
	    	if(!petugas) {
	    		//System.out.println("saya1 mhs");
	    		stmt = con.prepareStatement("select SHOW_AT_CREATOR from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_NPM=?");
	    		stmt.setString(1, target_thsms);
	    		stmt.setString(2, tipe_pengajuan);
	    		stmt.setString(3, this.operatorNpm);
	    		rs = stmt.executeQuery();
	    		if(rs.next()) {
	    			hidden = !rs.getBoolean(1);
	    		}
	    	}
	    	else {
	    		//System.out.println("saya2 petugas");
	    		//terlihat bila proses blum selesai
	    		stmt = con.prepareStatement("select SHOW_AT_TARGET from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and TOKEN_TARGET_OBJID like ? and LOCKED=?");
	    		stmt.setString(1, target_thsms);
	    		stmt.setString(2, tipe_pengajuan);
	    		stmt.setString(3, "%["+usr_objid+"]%");
	    		stmt.setBoolean(4, false);
	    		rs = stmt.executeQuery();
	    		if(rs.next()) {
	    			hidden = false;
	    		}
	    		hidden =  false;
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
    	
    	//System.out.println("hidden="+hidden);
    	return hidden;
    }
    
    /*
     * DEPRECATED pake v2
     */
    public boolean isThereProdiYgBelumMengajukanKelasPerkuliahan(Vector v_scope_id) {
    	//Vector v = null;
    	/*
    	 *  vScopeUa = scope pengajuan = owner & monitoree
    	 *  vScopeUaA = scope approval
    	 */
    	;
    	String thsms_buka_kls = Checker.getThsmsBukaKelas();
    	boolean ada =false;
    	ListIterator li = null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {

        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			String addon_cmd = "";
    			
    			//cek sebagai approvee
    			Vector v_kdpst_kmp = new Vector();
    			stmt = con.prepareStatement("select KDPST,KODE_KAMPUS from CLASS_POOL where THSMS=? and CANCELED=?");
    			stmt.setString(1,thsms_buka_kls);
    			stmt.setBoolean(2, false);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				li = v_kdpst_kmp.listIterator();
    				do {
    					String kdpst = ""+rs.getString("KDPST");
    					String kmp = ""+rs.getString("KODE_KAMPUS");
    					li.add(kdpst+"`"+kmp);
    				}
    				while(rs.next());
    			}
    			if(v_kdpst_kmp!=null && v_kdpst_kmp.size()>0) {
    				v_kdpst_kmp = Tool.removeDuplicateFromVector(v_kdpst_kmp);
    				li = v_kdpst_kmp.listIterator();
    				String list_id = "";
    				stmt = con.prepareStatement("select ID_OBJ from OBJECT where KDPST=? and KODE_KAMPUS_DOMISILI=?");
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String kdpst = st.nextToken();
    					String kmp = st.nextToken();
    					stmt.setString(1, kdpst);
    					stmt.setString(2, kmp);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						list_id = list_id+"["+rs.getInt(1)+"]";
    					}
    				}
    				if(!Checker.isStringNullOrEmpty(list_id)) {
    					li = v_scope_id.listIterator();
    	    			while(li.hasNext()&&!ada) {
    	    				String brs = (String)li.next();
    	    				StringTokenizer st = new StringTokenizer(brs,"`");
    	    				String kmp = st.nextToken();
    	    				while(st.hasMoreTokens()) {
    	    					String id = st.nextToken();
    	    					if(!list_id.contains("["+id+"]")) {
    	    						ada = true;
    	    						//System.out.println("["+id+"]");
    	    					}
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
    	return ada;
    }
    
    
    public boolean isThereProdiYgBelumMengajukanKelasPerkuliahan_v2(Vector v_scope_kdpst) {

    	;
    	String thsms_buka_kls = Checker.getThsmsBukaKelas();
    	boolean ada =false;
    	ListIterator li = null;
    	
    	if(v_scope_kdpst!=null && v_scope_kdpst.size()>0) {
    		try {

        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			String addon_cmd = "";
    			
    			//1. cek apa ada prodi yg belum mengajuakan
    			stmt = con.prepareStatement("select IDKUR from CLASS_POOL where THSMS=? and KDPST=? and KODE_KAMPUS=?");
    	    	
    	    	li = v_scope_kdpst.listIterator();
    	    	while(li.hasNext() && !ada) {
    	    		String brs = (String)li.next();
    	    		StringTokenizer st = new StringTokenizer(brs,"`");
    	    		String kmp = st.nextToken();
    	    		while(st.hasMoreTokens() && !ada) {
    	    			String kdpst = st.nextToken();
    	    			stmt.setString(1, thsms_buka_kls);
    	    			stmt.setString(2, kdpst);
    	    			stmt.setString(3, kmp);
    	    			rs = stmt.executeQuery();
    	    			if(!rs.next()) {
    	    				ada = true;
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
    	return ada;
    }
    
    
    public boolean cekPengajuanKelasPerkuliahan(Vector v_scope_id) {
    	/*
    	 * ADA 2 OBJECK - 1.REQUESTER (YG MEMILIKI SCOPE & 2. APPROVEE
    	 * 1.cek apakah overview table apa ada prodi yg belum mengajukan kelas utk thsms heregistrasi
    	 * 2.cek apa ada yang masih menunggu proses approval
    	 */
    	
    	String target_thsms = Checker.getThsmsBukaKelas();
    	Vector vtmp = new Vector();
    	ListIterator lit = vtmp.listIterator();
    	String thsms_buka_kls = Checker.getThsmsBukaKelas();
    	boolean ada =false;
    	//System.out.println("ada1="+ada);
    	ListIterator li = null;
    	//1. bagi yg punya scope
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {

        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			String addon_cmd = "";
    			li = v_scope_id.listIterator();
    			//1. cek yg belum ada pengajuan 
    			stmt = con.prepareStatement("select ID_OBJ from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=? and (BELUM_MENGAJUKAN_KELAS_PERKULIAHAN=? and TIDAK_ADA_PERKULIAHAN=?) or PENGAJUKAN_KELAS_PERKULIAHAN_INPROGRESS=? LIMIT 1");
    			while(li.hasNext() && !ada) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kmp = st.nextToken();
    				while(st.hasMoreTokens() && !ada) {
    					String idobj = st.nextToken();
    					//System.out.println("idobj = "+idobj);
    					stmt.setInt(1, Integer.parseInt(idobj));
    					stmt.setString(2, target_thsms);
    					stmt.setBoolean(3,true);
    					stmt.setBoolean(4,false);
    					stmt.setBoolean(5,true);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						ada = true;
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
    	//System.out.println("ada2="+ada);
    	if(!ada) {
    		//2. cek sbg approvee
    		String usr_objid = ""+Checker.getObjectId(this.operatorNpm);
    		ArrayList al = new ArrayList();
    		li = al.listIterator();
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select KDPST,KODE_KAMPUS from KELAS_PERKULIAHAN_RULES where THSMS=? and TKN_VERIFICATOR_ID like ?");
    			stmt.setString(1, target_thsms);
    			stmt.setString(2, "%["+usr_objid+"]%");
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String kdpst = rs.getString(1);
    				String kmp  = rs.getString(2);
    				li.add(kdpst+"`"+kmp);
    			}
    			
    			if(al.size()>0) {
    				li = al.listIterator();
    				stmt = con.prepareStatement("select ID_OBJ from OBJECT where KDPST=? and KODE_KAMPUS_DOMISILI=?");
    				while(li.hasNext()) {
        				String brs = (String)li.next();
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				String kdpst = st.nextToken();
        				String kmp = st.nextToken();
        				stmt.setString(1, kdpst);
        				stmt.setString(2, kmp);
        				rs = stmt.executeQuery();
        				if(rs.next()) {
        					li.set(brs+"`"+rs.getInt(1));
        				}
        				else {
        					li.remove();
        				}
        			}	
    			}
    			if(al.size()>0) {
    				li = al.listIterator();
    				stmt = con.prepareStatement("select ID_OBJ from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=? and (BELUM_MENGAJUKAN_KELAS_PERKULIAHAN=? or PENGAJUKAN_KELAS_PERKULIAHAN_INPROGRESS=?) LIMIT 1");
        			while(li.hasNext() && !ada) {
        				String brs = (String)li.next();
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				String kdpst = st.nextToken();
        				String kmp = st.nextToken();
        				String idobj = st.nextToken();
        				
        				stmt.setInt(1, Integer.parseInt(idobj));
    					stmt.setString(2, target_thsms);
    					stmt.setBoolean(3,true);
    					stmt.setBoolean(4,true);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						ada = true;
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
    	//System.out.println("ada3="+ada);
    	return ada;
    }
    
   
    /*
     * deprecated dipindah ke SearchDbClassPoll
     */
    public Vector listProdiYgBelumMengajukanKelasPerkuliahan_old(Vector v_scope_id) {
    	//Vector v = null;
    	/*
    	 *  vScopeUa = scope pengajuan = owner & monitoree
    	 *  vScopeUaA = scope approval
    	 */
    	Vector v_list_yg_blm = null;;
    	String thsms_buka_kls = Checker.getThsmsBukaKelas();
    	boolean ada =false;
    	ListIterator li = null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {

        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			String addon_cmd = "";
    			
    			//cek sebagai approvee
    			Vector v_kdpst_kmp = new Vector();
    			stmt = con.prepareStatement("select KDPST,KODE_KAMPUS from CLASS_POOL where THSMS=? and CANCELED=?");
    			stmt.setString(1,thsms_buka_kls);
    			stmt.setBoolean(2, false);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				li = v_kdpst_kmp.listIterator();
    				do {
    					String kdpst = ""+rs.getString("KDPST");
    					String kmp = ""+rs.getString("KODE_KAMPUS");
    					li.add(kdpst+"`"+kmp);
    				}
    				while(rs.next());
    			}
    			if(v_kdpst_kmp!=null && v_kdpst_kmp.size()>0) {
    				v_kdpst_kmp = Tool.removeDuplicateFromVector(v_kdpst_kmp);
    				li = v_kdpst_kmp.listIterator();
    				String list_id = "";
    				stmt = con.prepareStatement("select ID_OBJ from OBJECT where KDPST=? and KODE_KAMPUS_DOMISILI=?");
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String kdpst = st.nextToken();
    					String kmp = st.nextToken();
    					stmt.setString(1, kdpst);
    					stmt.setString(2, kmp);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						list_id = list_id+"["+rs.getInt(1)+"]";
    					}
    				}
    				if(!Checker.isStringNullOrEmpty(list_id)) {
    					v_list_yg_blm = new Vector();
    					ListIterator lit = v_list_yg_blm.listIterator();
    					stmt = con.prepareStatement("select * from OBJECT inner join MSPST on KDPST=KDPSTMSPST where ID_OBJ=? order by KODE_KAMPUS_DOMISILI,KDPST");
    					li = v_scope_id.listIterator();
    	    			while(li.hasNext()) {
    	    				String brs = (String)li.next();
    	    				StringTokenizer st = new StringTokenizer(brs,"`");
    	    				String kmp = st.nextToken();
    	    				while(st.hasMoreTokens()) {
    	    					String id = st.nextToken();
    	    					if(!list_id.contains("["+id+"]")) {
    	    						stmt.setInt(1, Integer.parseInt(id));
    	    						rs = stmt.executeQuery();
    	    						rs.next();
    	    						String kmpus = ""+rs.getString("KODE_KAMPUS_DOMISILI");
    	    						String kdpst = ""+rs.getString("KDPSTMSPST");
    	    						String nmpst = ""+rs.getString("NMPSTMSPST");
    	    						String kdjen = ""+rs.getString("KDJENMSPST");
    	    						
    	    						lit.add(kmpus+"`"+kdpst+"`"+nmpst+"`"+kdjen+"`"+id);
    	    						//System.out.println("["+id+"]");
    	    					}
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
    	return v_list_yg_blm;
    }
    
    
    
    public boolean adaPengajuanKrsApprovalUntukSaya(String my_npm) {
    	//Vector v = null;
    	/*
    	 *  vScopeUa = scope pengajuan = owner & monitoree
    	 *  vScopeUaA = scope approval
    	 */
    	
    	String thsms_krs = Checker.getThsmsKrs();
    	boolean ada =false;
    	//ListIterator li = null;
    	try {

    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select ID from KRS_NOTIFICATION where KATEGORI='KRS APPROVAL' and THSMS_TARGET=? and NPM_RECEIVER=? and APPROVED=? and DECLINED=? limit 1");
			stmt.setString(1, thsms_krs);
			stmt.setString(2, my_npm);
			stmt.setBoolean(3, false);
			stmt.setBoolean(4, false);
			//System.out.println("thsms_krs="+thsms_krs);
			//System.out.println("my_npm="+my_npm);
			rs = stmt.executeQuery();
			if(rs.next()) {
				//System.out.println("id krs = "+rs.getInt(1));
				ada = true;
			}
			//System.out.println("ada="+ada);
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

    	
    	return ada;
    }
    
    public boolean adaRequestPymntApproval(Vector vScope_id) {
    	/*
    	 * ambil  list reuquest pembayaran dengan bukti setoran dari pymnt transit table
    	 */
    	boolean ada = false;
    	if(vScope_id!=null && vScope_id.size()>0) {
    		ListIterator li = vScope_id.listIterator();
        	String list_id = null;
        	try {
        		
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			st.nextToken();
        			while(st.hasMoreTokens()) {
        				if(list_id==null) {
        					list_id = new String("ID_OBJ="+st.nextToken());
        				}
        				else {
        					list_id = list_id+" OR ID_OBJ="+st.nextToken();	
        				}
        			}
        		}
        		if(list_id!=null && !Checker.isStringNullOrEmpty(list_id)) {
        			String sql_cmd = "select KUIIDPYMNT from PYMNT_TRANSIT where "+list_id+" limit 1";
        			//System.out.println("sql_cmd="+sql_cmd);
        			Context initContext  = new InitialContext();
            		Context envContext  = (Context)initContext.lookup("java:/comp/env");
            		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            		con = ds.getConnection();
        			stmt = con.prepareStatement(sql_cmd);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				ada = true;
        			}
        		}
        		else {
        			//ignore = ngga ada akses
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
    	return ada;
    }
    
    public boolean adaRequestDaftarUlang(Vector vScope_id) {
    	/*
    	 * ambil  list reuquest pembayaran dengan bukti setoran dari pymnt transit table
    	 */
    	String thsms_reg = Checker.getThsmsHeregistrasi();
    	boolean ada = false;
    	if(vScope_id!=null && vScope_id.size()>0) {
    		ListIterator li = vScope_id.listIterator();
        	String list_id = null;
        	try {
        		
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			st.nextToken();
        			while(st.hasMoreTokens()) {
        				if(list_id==null) {
        					list_id = new String("ID_OBJ="+st.nextToken());
        				}
        				else {
        					list_id = list_id+" OR ID_OBJ="+st.nextToken();	
        				}
        			}
        		}
        		//System.out.println("list_id ="+list_id);
        		if(list_id!=null && !Checker.isStringNullOrEmpty(list_id) && !list_id.contains("own")) {
        			//String sql_cmd = "select ID_OBJ from DAFTAR_ULANG_NOTIFICATION where THSMS=? and ("+list_id+") and LIST_NPM_INPROGRESS is not null limit 1";
        			String sql_cmd = "select ID_OBJ from DAFTAR_ULANG where THSMS=? and ("+list_id+") and ALL_APPROVED=? limit 1";
        			//System.out.println("sql_cmd="+sql_cmd);
        			Context initContext  = new InitialContext();
            		Context envContext  = (Context)initContext.lookup("java:/comp/env");
            		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            		con = ds.getConnection();
        			stmt = con.prepareStatement(sql_cmd);
        			stmt.setString(1, thsms_reg);
        			stmt.setBoolean(2,false);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				ada = true;
        			}
        		}
        		else {
        			//ignore = ngga ada akses
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
    	return ada;
    }
    
    public boolean getNpmAdaDiTrlsmNotAtPengajuan(Vector vScope_id) {
    	/*
    	 * ambil  list reuquest pembayaran dengan bukti setoran dari pymnt transit table
    	 */
    	String based_thsms = Constant.getValue("BASED_THSMS");
    	boolean ada = false;
    	if(vScope_id!=null && vScope_id.size()>0) {
    		ListIterator li = vScope_id.listIterator();
        	String list_id = null;
        	try {
        		
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			st.nextToken();
        			while(st.hasMoreTokens()) {
        				if(list_id==null) {
        					list_id = new String("ID_OBJ="+st.nextToken());
        				}
        				else {
        					list_id = list_id+" OR ID_OBJ="+st.nextToken();	
        				}
        			}
        		}
        		if(list_id!=null && !Checker.isStringNullOrEmpty(list_id)) {
        			String sql_cmd = "select ID_OBJ from OVERVIEW_SEBARAN_TRLSM where THSMS>=? and ERROR_TRLSM_VS_PENGAJUAN=? and ("+list_id+") limit 1";
        			//System.out.println("sql_cmd="+sql_cmd);
        			Context initContext  = new InitialContext();
            		Context envContext  = (Context)initContext.lookup("java:/comp/env");
            		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            		con = ds.getConnection();
        			stmt = con.prepareStatement(sql_cmd);
        			stmt.setString(1, based_thsms);
        			stmt.setBoolean(2, true);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				ada = true;
        			}
        		}
        		else {
        			//ignore = ngga ada akses
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
    	return ada;
    }
    
    public boolean adaTugasSpmi(String cycle_spmi, Vector v_scope_id, int objid) {
    	//cycle_spmi
    	
    	boolean ada = false;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		
    		String tkn_my_jabatan = Getter.listAllNamaJabatanOnly(objid);
    		StringTokenizer stj = new StringTokenizer(tkn_my_jabatan,"`");
    		if(stj.countTokens()>0) {
    			//System.out.println("tkn_my_jabatan="+tkn_my_jabatan);
        		String sql_cmd="";	
        		boolean first = true;
        		Vector v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
        		ListIterator li = v_scope_kdpst.listIterator();
        		li = v_scope_kdpst.listIterator();
                while(li.hasNext()) {
                	String info_scope = (String)li.next();
                	//System.out.println("info_scope="+info_scope);
                    StringTokenizer st = new StringTokenizer(info_scope,"`");
                    if(st.countTokens()>1) {
                    	if(first) {
                    		first = false;
                    	}
                    	else {
                    		sql_cmd = sql_cmd+" or ";
                    	}
                    	st.nextToken();
                        while(st.hasMoreTokens()) {
                        	String kdpst = st.nextToken();
                              sql_cmd = sql_cmd+"KDPST='"+kdpst+"'";
                              if(st.hasMoreTokens()) {
                                   sql_cmd = sql_cmd+" or ";
                              }
                        }
                       //if(li.hasNext()) {
                       //	sql_cmd = sql_cmd+" or ";
                       //}
                    }
                    
                }
                try {
            		sql_cmd = "select NOTIFY_TKN_JABATAN from OVERVIEW_SPMI where "+sql_cmd;
        			//System.out.println("sql_cmd="+sql_cmd);
        			Context initContext  = new InitialContext();
            		Context envContext  = (Context)initContext.lookup("java:/comp/env");
            		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            		con = ds.getConnection();
        			stmt = con.prepareStatement(sql_cmd);
        			//System.out.println("skil commane = "+sql_cmd);
            		rs = stmt.executeQuery();
            		while(rs.next() && !ada) {
            			String tkn_jabatan = rs.getString(1);
            			if(!tkn_jabatan.startsWith("`")) {
            				tkn_jabatan = "`"+tkn_jabatan;
            			}
            			if(!tkn_jabatan.endsWith("`")) {
            				tkn_jabatan = tkn_jabatan+"`";
            			}
            			stj = new StringTokenizer(tkn_my_jabatan,"`");
            			while(!ada && stj.hasMoreTokens()) {
            				String one_of_my_jabatan = stj.nextToken();
            				if(tkn_jabatan.contains("`"+one_of_my_jabatan+"`")) {
            					ada = true;
            				}
            			}
            			//System.out.println("tkn_jabatan="+tkn_jabatan);
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
    		
    	//System.out.println(v.size());
    	return ada;
    }
    
}

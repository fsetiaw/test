package beans.dbase.pengajuan.pindah_prodi;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

/**
 * Session Bean implementation class SearchDbPp
 */
@Stateless
@LocalBean
public class SearchDbPp extends SearchDb {
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
    public SearchDbPp() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbPp(String operatorNpm) {
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
    public SearchDbPp(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public Vector addHocFn(ResultSet rs) {
    	ListIterator li = null;
    	Vector v = null;
    	long opr_obj_id = Checker.getObjectId(this.operatorNpm);
    	try {
    		if(rs!=null && rs.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				String id = ""+rs.getLong("ID");
        			String thsms_pengajuan = ""+rs.getString("TARGET_THSMS_PENGAJUAN");
        		    String tipe_pengajuan = ""+rs.getString("TIPE_PENGAJUAN");
        		    String isi_topik_pengajuan = ""+rs.getString("ISI_TOPIK_PENGAJUAN");
        		    String tkn_target_objnickname = ""+rs.getString("TOKEN_TARGET_OBJ_NICKNAME");
        		    String tkn_target_objid = ""+rs.getString("TOKEN_TARGET_OBJID");
        		    tkn_target_objid = tkn_target_objid.replace("`", "-");
        		    String target_npm = ""+rs.getString("TARGET_NPM");
        		    String creator_obj_id = ""+rs.getLong("CREATOR_OBJ_ID");
        		    String creator_npm = ""+rs.getString("CREATOR_NPM");
        		    String creator_nmm = ""+rs.getString("CREATOR_NMM");
        		    String shwow_at_target = ""+rs.getString("SHOW_AT_TARGET");
        		    //if(Checker.isStringNullOrEmpty(shwow_at_target)) {
        		    //	shwow_at_target = "null";
        		    //}
        		    String show_at_creator = ""+rs.getLong("SHOW_AT_CREATOR");
        		    String updtm = ""+rs.getTimestamp("UPDTM");
        		    String approved = ""+rs.getString("APPROVED");
        		    String locked = ""+rs.getBoolean("LOCKED");
        		    String rejected = ""+rs.getString("REJECTED");
        		    String creator_kdpst = ""+rs.getString("CREATOR_KDPST");
        		    String target_kdpst = ""+rs.getString("TARGET_KDPST");
        		    String batal = ""+rs.getBoolean("BATAL");
        		    //System.out.println("add="+id+"`"+thsms_pengajuan+"`"+tipe_pengajuan+"`"+isi_topik_pengajuan+"`"+tkn_target_objnickname+"`"+tkn_target_objid+"`"+target_npm+"`"+creator_obj_id+"`"+creator_npm+"`"+creator_nmm+"`"+shwow_at_target+"`"+show_at_creator+"`"+updtm+"`"+approved+"`"+locked+"`"+rejected+"`"+creator_kdpst+"`"+target_kdpst+"`"+batal);
        		    li.add(id+"`"+thsms_pengajuan+"`"+tipe_pengajuan+"`"+isi_topik_pengajuan+"`"+tkn_target_objnickname+"`"+tkn_target_objid+"`"+target_npm+"`"+creator_obj_id+"`"+creator_npm+"`"+creator_nmm+"`"+shwow_at_target+"`"+show_at_creator+"`"+updtm+"`"+approved+"`"+locked+"`"+rejected+"`"+creator_kdpst+"`"+target_kdpst+"`"+batal);
    			}
    			while(rs.next());
    			
    			//add info subtopik
    			if(v!=null && v.size()>0) {
    				stmt = con.prepareStatement("select * from SUBTOPIK_PENGAJUAN where ID_TOPIK=?");
    				li = v.listIterator();
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String id = st.nextToken();
    					stmt.setLong(1, Long.parseLong(id));
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						String addhoc_info = "";
    						do {
    							String isi = ""+rs.getString("ISI_SUBTOPIK");
    							String updtm =  ""+rs.getTimestamp("UPDTM");
    							String creator_id = ""+rs.getString("CREATOR_OBJ_ID");
    							String verdict =  ""+rs.getString("VERDICT");
    							addhoc_info = addhoc_info+"`"+isi+"`"+creator_id+"`"+verdict+"`"+updtm;
    						}
    						while(rs.next());
    						li.set(brs+"`"+addhoc_info);
    					}
    				}
    			}
    			//cek yg dibatalin ma mhs, set hidden at target
    			if(v!=null && v.size()>0) {
    				stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_TARGET=? where ID=?");
    				li = v.listIterator();
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String id = st.nextToken();
            			String thsms_pengajuan = st.nextToken();
            		    String tipe_pengajuan = st.nextToken();
            		    String isi_topik_pengajuan = st.nextToken();
            		    String tkn_target_objnickname = st.nextToken();
            		    String tkn_target_objid = st.nextToken();
            		    String target_npm = st.nextToken();
            		    String creator_obj_id = st.nextToken();
            		    String creator_npm = st.nextToken();
            		    String creator_nmm = st.nextToken();
            		    String shwow_at_target = st.nextToken();
            		    String show_at_creator = st.nextToken();
            		    String updtm = st.nextToken();
            		    String approved = st.nextToken();
            		    String locked = st.nextToken();
            		    String rejected = st.nextToken();
            		    String creator_kdpst = st.nextToken();
            		    String target_kdpst = st.nextToken();
            		    String batal = st.nextToken();
            		    if(batal.equalsIgnoreCase("true")) {
            		    	shwow_at_target = shwow_at_target.replace("["+opr_obj_id+"]", "");
            		    	if(Checker.isStringNullOrEmpty(shwow_at_target)) {
            		    		stmt.setNull(1, java.sql.Types.VARCHAR);
            		    	}
            		    	else {
            		    		stmt.setString(1, shwow_at_target);
            		    	}
            		    	
            		    	stmt.setLong(2, Long.parseLong(id));
            		    	stmt.executeUpdate();
            		    }
    				}
    			}	
    		}
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	}
    	/*
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	*/
    	return v;
    }
    
    /*
     * DEPRICATED PAKE YG VERSI 2 
     * KARENA KRANG COMMAND
     */
    public Vector getStatusPpRequest_v1(String target_thsms, String target_npmhs, boolean show, boolean am_i_stu, boolean boleh_liat_berdasarkan_scope, Vector v_tipe_pengajuan_scope) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			if(show) {
				if(am_i_stu) {
					//berarti untuk notifikasi jadi hanya yg show at creator
					stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=? and SHOW_AT_CREATOR=?");	//show at creator hanya untuk notificasi dashboard
					stmt.setString(1,target_thsms);
		    		stmt.setString(2, target_npmhs);
		    		stmt.setBoolean(3, show);	
		    		rs = stmt.executeQuery();
		    		v = addHocFn(rs);
		    		//show berarti dari home maka update set show_atCreator = false
		    		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_CREATOR=? where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=? and SHOW_AT_CREATOR=?");
		    		stmt.setBoolean(1, !show);
		    		stmt.setString(2,target_thsms);
		    		stmt.setString(3, target_npmhs);
		    		stmt.setBoolean(4, show);	
		    		stmt.executeUpdate();
				}
				else {
					//untuk operator
					//dari notification = jadi yg show yg idobj termasuk ke dalam scope
					//get pengauan yg belum lock & batal
					//System.out.println("masuk sii");
					if(v_tipe_pengajuan_scope!=null && v_tipe_pengajuan_scope.size()>0) {
						String cmd = null;
						li = v_tipe_pengajuan_scope.listIterator();
						while(li.hasNext()) {
							String scope_info = (String)li.next();
							//System.out.println("scope_info = "+scope_info);
							StringTokenizer st = new StringTokenizer(scope_info,"`");
							String kmp = st.nextToken();
							while(st.hasMoreTokens()) {
								String id = st.nextToken();
								if(cmd==null) {
									cmd = new String("CREATOR_OBJ_ID="+id);
								}
								else {
									cmd = cmd+" OR CREATOR_OBJ_ID="+id;
								}
							}	
						}
						
						cmd = "select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and LOCKED=? and BATAL=? and ("+cmd+")";
						//System.out.println("command = "+cmd);
						stmt = con.prepareStatement(cmd);
						//stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and SHOW_AT_TARGET like ?");	
			    		stmt.setString(1, target_thsms);
			    		stmt.setBoolean(2, false);
			    		stmt.setBoolean(3, false);
			    		rs = stmt.executeQuery();
			    		v = addHocFn(rs);
					}
					
					
					
		    		
				}
			}
			else {
				//liat riwayat individual
				
				if(am_i_stu) {
					stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=?");
					stmt.setString(1,target_thsms);
		    		stmt.setString(2, target_npmhs);
		    		rs = stmt.executeQuery();
		    		v = addHocFn(rs);
				}
				else {
					//target_obj atau yg scopenya termasuk mhs terkait
					//liat riwayet  
					if(boleh_liat_berdasarkan_scope) {
						stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=?");	
						stmt.setString(1, target_thsms);
						stmt.setString(2, target_npmhs);
			    		rs = stmt.executeQuery();
			    		v = addHocFn(rs);
					}
					else {
						//cuma target aja
						stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TOKEN_TARGET_OBJID like ?");	
						stmt.setString(1, target_thsms);
			    		stmt.setString(2, "%["+Checker.getObjectId(this.operatorNpm)+"]%");
			    		rs = stmt.executeQuery();
			    		v = addHocFn(rs);
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
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	
    	return v;
    }
    
    public Vector getStatusPpRequest_v2(String target_thsms, String target_npmhs, boolean show, boolean am_i_stu, boolean boleh_liat_berdasarkan_scope, Vector v_tipe_pengajuan_scope, String nama_table_rules) {
    	Vector v = null;
    	ListIterator li = null;
    	Vector v1 = null;
    	ListIterator li1 = null;
    	String tipe_pengajuan = nama_table_rules.replace("_RULES", "");
    	String title_pengajuan = tipe_pengajuan.replace("_", " ");
    	//System.out.println(tipe_pengajuan+"--"+title_pengajuan);
    	//System.out.println("show="+show);
    	//System.out.println("target_thsms="+target_thsms);
    	//System.out.println("target_npmhs="+target_npmhs);
    	//System.out.println("am_i_stu="+am_i_stu);
    	//System.out.println("nama_table_rules"+nama_table_rules);
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			if(show) {
				if(am_i_stu) {
					//System.out.println("stu 1");;
					//berarti untuk notifikasi jadi hanya yg show at creator
					stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN>=? and TIPE_PENGAJUAN=? and CREATOR_NPM=? and SHOW_AT_CREATOR=?");	//show at creator hanya untuk notificasi dashboard
					stmt.setString(1,target_thsms);
					stmt.setString(2,tipe_pengajuan);
		    		stmt.setString(3, target_npmhs);
		    		stmt.setBoolean(4, show);	
		    		rs = stmt.executeQuery();
		    		v = addHocFn(rs);
		    		//show berarti dari home maka update set show_atCreator = false
		    		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_CREATOR=? where TARGET_THSMS_PENGAJUAN>=? and TIPE_PENGAJUAN=? and CREATOR_NPM=? and SHOW_AT_CREATOR=?");
		    		//stmt.setBoolean(1, !show);
		    		stmt.setBoolean(1, false);
		    		stmt.setString(2,target_thsms);
		    		stmt.setString(3,tipe_pengajuan);
		    		stmt.setString(4, target_npmhs);
		    		stmt.setBoolean(5, show);	
		    		stmt.executeUpdate();
				}
				else {
					//System.out.println("stu 2");;
					//untuk operator
					//dari notification = jadi yg show yg idobj termasuk ke dalam scope
					//get pengauan yg belum lock & batal
					//System.out.println("masuk sii");
					if(v_tipe_pengajuan_scope!=null && v_tipe_pengajuan_scope.size()>0) {
						//System.out.println("2a");;
						String cmd = null;
						li = v_tipe_pengajuan_scope.listIterator();
						while(li.hasNext()) {
							String scope_info = (String)li.next();
							//System.out.println("scope_info = "+scope_info);
							StringTokenizer st = new StringTokenizer(scope_info,"`");
							String kmp = st.nextToken();
							while(st.hasMoreTokens()) {
								String id = st.nextToken();
								if(cmd==null) {
									cmd = new String("CREATOR_OBJ_ID="+id);
								}
								else {
									cmd = cmd+" OR CREATOR_OBJ_ID="+id;
								}
							}	
						}
						
						cmd = "select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN>=? and TIPE_PENGAJUAN=? and LOCKED=? and BATAL=? and ("+cmd+")";
						//System.out.println("command = "+cmd);
						//System.out.println(tipe_pengajuan);
						if (con==null) {
							con = ds.getConnection();
						}
						stmt = con.prepareStatement(cmd);
						//stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and SHOW_AT_TARGET like ?");	
			    		stmt.setString(1, target_thsms);
			    		stmt.setString(2,tipe_pengajuan);
			    		stmt.setBoolean(3, false);
			    		stmt.setBoolean(4, false);
			    		rs = stmt.executeQuery();
			    		v = addHocFn(rs);
					}
					if(v!=null) {
						//System.out.println("vsize="+v.size());
					}
					else {
						//System.out.println("vsize=null");
					}
					//
					/*
					 * else dihilangkan karena operator punya scope tertentu, tapi juga sbg approvee yg tidak termasuk pada scope tersebut
					 */
					//else {
					//System.out.println("2b");;
					//bagi yg tidak ada scope command - berarti dia approvee only hide previuos batal & lock
					
					//Context initContext  = new InitialContext();
					//Context envContext  = (Context)initContext.lookup("java:/comp/env");
					if(con.isClosed()) {
						//ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
						con = ds.getConnection();
					}
					
					
					stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN>=? and TIPE_PENGAJUAN=? and (TOKEN_TARGET_OBJID like ? or TOKEN_TARGET_OBJID like ? or TOKEN_TARGET_OBJID like ? or TOKEN_TARGET_OBJID like ?) and LOCKED=? and BATAL=?");	
					stmt.setString(1, target_thsms);
					stmt.setString(2,tipe_pengajuan);
		    		stmt.setString(3, "%["+Checker.getObjectId(this.operatorNpm)+"]%");//inget multiple id possible, jadi cek juga posisi id spt berikut
		    		stmt.setString(4, "%["+Checker.getObjectId(this.operatorNpm)+",%");
		    		stmt.setString(5, "%,"+Checker.getObjectId(this.operatorNpm)+"]%");
		    		stmt.setString(6, "%,"+Checker.getObjectId(this.operatorNpm)+",%");
		    		stmt.setBoolean(7,false);
		    		stmt.setBoolean(8,false);
		    		rs = stmt.executeQuery();
		    		v1 = addHocFn(rs);	
		    	
					
		    		
		    		if(v!=null) {
		    			li = v.listIterator();
		    			while(li.hasNext()) {
		    				li.next();
		    			}
		    		}
		    		
		    		if(v1!=null && v1.size()>0) {
		    			if(v!=null) {
			    			li1 = v1.listIterator();
				    		while(li1.hasNext()) {
				    			li.add((String)li1.next());
				    		}
				    		v = Tool.removeDuplicateFromVector(v);
			    		}
			    		else {
			    			v =new Vector(v1);
			    		}
			    		
		    		}
		    		if(v!=null) {
						//System.out.println("vsize2b="+v.size());
						
					}
					else {
						//System.out.println("vsize2b=null");
					}
		    		
				}
			}
			else {
				//liat riwayat individual
				
				if(am_i_stu) {
					//System.out.println("stu 3");;
					if(con.isClosed()) {
						//ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
						con = ds.getConnection();
					}
					stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN>=? and TIPE_PENGAJUAN=? and CREATOR_NPM=?");
					stmt.setString(1,target_thsms);
					stmt.setString(2,nama_table_rules.replace("_RULES", ""));
		    		stmt.setString(3, target_npmhs);
		    		rs = stmt.executeQuery();
		    		v = addHocFn(rs);
				}
				else {
					//System.out.println("stu 4");;
					//liat riwayet  
					//System.out.println("4");;
					if(boleh_liat_berdasarkan_scope) {
						//System.out.println("4");;
						if(con.isClosed()) {
							//ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
							con = ds.getConnection();
						}
						stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN>=? and TIPE_PENGAJUAN=? and CREATOR_NPM=?");	
						stmt.setString(1, target_thsms);
						stmt.setString(2,nama_table_rules.replace("_RULES", ""));
						stmt.setString(3, target_npmhs);
			    		rs = stmt.executeQuery();
			    		v = addHocFn(rs);
					}
					else {
						//cuma target aja - harusnya ngga bisa kesini krn yg target aja berarrti hanya dari notificasi
						//System.out.println("5");;
						/*
						 * 
						stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and (TOKEN_TARGET_OBJID like ? or TOKEN_TARGET_OBJID like ? or TOKEN_TARGET_OBJID like ? or TOKEN_TARGET_OBJID like ?) and LOCKED=? and BATAL=?");	
						stmt.setString(1, target_thsms);
						stmt.setString(2,tipe_pengajuan_samadengan_nama_table_rules.replace("_RULES", ""));
			    		stmt.setString(3, "%["+Checker.getObjectId(this.operatorNpm)+"]%");//inget multiple id possible, jadi cek juga posisi id spt berikut
			    		stmt.setString(4, "%["+Checker.getObjectId(this.operatorNpm)+",%");
			    		stmt.setString(5, "%,"+Checker.getObjectId(this.operatorNpm)+"]%");
			    		stmt.setString(6, "%,"+Checker.getObjectId(this.operatorNpm)+",%");
			    		stmt.setBoolean(7,false);
			    		stmt.setBoolean(8,false);
			    		rs = stmt.executeQuery();
						 
						stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and TOKEN_TARGET_OBJID like ?");	
						stmt.setString(1, target_thsms);
						stmt.setString(2,tipe_pengajuan_samadengan_nama_table_rules.replace("_RULES", ""));
			    		stmt.setString(3, "%["+Checker.getObjectId(this.operatorNpm)+"]%");
			    		rs = stmt.executeQuery();
			    		v = addHocFn(rs);
			    		*/
					}
				}
	    	}
			if(v!=null) {
				//System.out.println("vsize2b="+v.size());
				v = Tool.removeDuplicateFromVector(v);
				/*
				li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					//System.out.println("->"+brs);
				}
				*/
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
    	catch (Exception e) {}
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	
    	return v;
    }
    
    public Vector getCurrentRule(String target_thsms) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from PINDAH_PRODI_RULES where THSMS=?");
			stmt.setString(1, target_thsms);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
	    		li = v.listIterator();
	    		do {
	    			String kdpst = ""+rs.getString("KDPST");
					String tkn_ver = ""+rs.getString("TKN_JABATAN_VERIFICATOR");
					String tkn_ver_id = ""+rs.getString("TKN_VERIFICATOR_ID");
					String urutan = ""+rs.getBoolean("URUTAN");
					String kd_kmp = ""+rs.getString("KODE_KAMPUS");
					li.add(kdpst+"`"+tkn_ver+"`"+tkn_ver_id+"`"+urutan+"`"+kd_kmp);
				
	    		} while(rs.next());	
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
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	return v;
    }	
}

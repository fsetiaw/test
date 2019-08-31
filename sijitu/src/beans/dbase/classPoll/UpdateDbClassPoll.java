package beans.dbase.classPoll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.Vector;
import java.util.ListIterator;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Collections;
import org.apache.tomcat.jdbc.pool.DataSource;

import beans.dbase.UpdateDb;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Tool;

/**
 * Session Bean implementation class UpdateDb
 */
@Stateless
@LocalBean
public class UpdateDbClassPoll extends UpdateDb{
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
     * Default constructor. 
     */
    public UpdateDbClassPoll() {
        // TODO Auto-generated constructor stub
    }

    public UpdateDbClassPoll(String operatorNpm) {
        // TODO Auto-generated constructor stub
    	super(operatorNpm);
    	this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
    }

    public void updateClassPollRules(String thsms, String kode_kampus, String[]kdpst, String[]alur, String[]kdpst_urut) {
    	int i=0;
    	if(kdpst!=null && kdpst.length>0) {
    		//System.out.println("sampe sini juga");
    		try {
    			Vector v = new Vector();
    			ListIterator li = v.listIterator();
        		//String ipAddr =  request.getRemoteAddr();
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//update kalo blum ada recordnya lalu diinsert
        		stmt = con.prepareStatement("update CLASS_POOL_RULES set TKN_VERIFICATOR=? where THSMS=? and KDPST=? and KODE_KAMPUS=?");
        		for(int j=0;j<kdpst.length;j++) {
        			if(alur[j]==null || Checker.isStringNullOrEmpty(alur[j])) {
        				stmt.setNull(1, java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(1, alur[j]);
        			}
        			stmt.setString(2, thsms);
        			stmt.setString(3, kdpst[j]);
        			stmt.setString(4, kode_kampus);
        			i=0;
        			//System.out.println(j+". "+alur[j]+" , "+thsms+" , "+kdpst[j]+" , "+kode_kampus);
        			i = stmt.executeUpdate();
        			//System.out.println("i="+i);
        			if(i<1) {
        				li.add(alur[j]+"`"+kdpst[j]);
        			}
        		}
        		//bila ada yg butuh diinsert
        		if(v!=null && v.size()>0) {
        			stmt = con.prepareStatement("insert into CLASS_POOL_RULES(THSMS,KDPST,TKN_VERIFICATOR,URUTAN,KODE_KAMPUS)VALUES(?,?,?,?,?)");
        			li = v.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				String alur_ = st.nextToken();
        				String kdpst_ = st.nextToken();
        				stmt.setString(1, thsms);
        				stmt.setString(2, kdpst_);
        				if(alur_==null || Checker.isStringNullOrEmpty(alur_)) {
        					stmt.setNull(3,java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt.setString(3, alur_);
        				}
        				stmt.setBoolean(4, false);
        				stmt.setString(5, kode_kampus);
        				stmt.executeUpdate();
        			}
        		}
        		//reset uutan dulu
        		stmt = con.prepareStatement("update CLASS_POOL_RULES set URUTAN=? where THSMS=? and KODE_KAMPUS=?");
        		stmt.setBoolean(1, false);
        		stmt.setString(2, thsms);
        		stmt.setString(3, kode_kampus);
        		stmt.executeUpdate();
        		//update urutan
        		if(kdpst_urut!=null && kdpst_urut.length>0) {
        			stmt = con.prepareStatement("update CLASS_POOL_RULES set URUTAN=? where THSMS=? and KDPST=? and KODE_KAMPUS=?");
        			for(int j=0;j<kdpst_urut.length;j++) {
        				stmt.setBoolean(1, true);
        				stmt.setString(2, thsms);
        				stmt.setString(3, kdpst_urut[j]);
        				stmt.setString(4, kode_kampus);
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
    		
    	//return i;
    }	    
    
    public void updateProdiNoPerkuliahan(String[]kmp_prodi, String target_thsms) {
    	if(kmp_prodi!=null && kmp_prodi.length>0) {
    		try {
    			
        		//String ipAddr =  request.getRemoteAddr();
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		
        		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TIDAK_ADA_PERKULIAHAN=? where ID_OBJ=? and THSMS=?");
        		for(int i=0;i<kmp_prodi.length;i++) {
        			StringTokenizer st = new StringTokenizer(kmp_prodi[i],"`");
        			String id = st.nextToken();
        			//System.out.println(kmp_prodi[i]);
        			if(!id.equalsIgnoreCase("null")) {
        				stmt.setBoolean(1, true);
            			stmt.setInt(2, Integer.parseInt(id));
            			stmt.setString(3, target_thsms);
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
    }
    
    public void prosesApprovalKelasPerkuliahan(String thsms, String verdict, String info, String alasan, int usr_obj_id) {
    	int i=0;
    	try {
			Vector v = new Vector();
			ListIterator li = v.listIterator();
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		StringTokenizer st = new StringTokenizer(info,"`");
	  		String id = st.nextToken();
			String kdpst = st.nextToken();
			String kmp = st.nextToken();
			String locked = st.nextToken();
			String passed = st.nextToken();
			String reject = st.nextToken();
			String list_job_approvee = st.nextToken();
			String list_id_approvee = st.nextToken();
			String current_job_approvee = st.nextToken();
			String current_id_approvee = st.nextToken();
			//System.out.println("ifo="+info);

    		
    		if(verdict.equalsIgnoreCase("terima")) {
    			
				
				//2.update CLASS_POOL_APPROVAL
    			stmt = con.prepareStatement("INSERT INTO CLASS_POOL_APPROVAL(ID_OBJ,THSMS,KDPST,KODE_KAMPUS,COMMENT,APPROVED,CURRENT_ID_APPROVAL,CURRENT_JABATAN_APPROVAL,ID_APPROVAL_NEEDED,JABATAN_APPROVAL_NEEDED,ALL_APPROVED)values(?,?,?,?,?,?,?,?,?,?,?)");
    			i = 1;
    			stmt.setInt(i++, Integer.parseInt(id));
    			stmt.setString(i++, thsms);
    			stmt.setString(i++, kdpst);
    			stmt.setString(i++, kmp);
    			if(alasan==null || Checker.isStringNullOrEmpty(alasan)) {
    				stmt.setNull(i++, java.sql.Types.VARCHAR);
    			}
    			else {
    				stmt.setString(i++, alasan);	
    			}
    			
    			stmt.setBoolean(i++, true);
    			
    			// penerimamaan maka needed approvee = next token berikutnya bila ada, kalo ngga ada = allapproved
    			//stmt.setInt(i++, Integer.parseInt(current_id_approvee));
    			/*
    			 * TIDAK LAGi MEMAKAI current_id_approvee krn bisa >1 id (id1,id2,dst)
    			 */
    			stmt.setInt(i++, usr_obj_id);
    			
    			stmt.setString(i++, current_job_approvee);
    			
    			String next_job_approvee = null;
    			String next_id_approvee = null;
    			//ifo=115`93402`PST`false`false`true`[KTU][KAPRODI][DEKAN][Ka. BAAK][WAREK I]`[50][50][20][58][20]`KTU`50
    			String list_job_approvee_1 = new String(list_job_approvee);
    			list_job_approvee_1 = list_job_approvee_1.replace("][", "`");
    			list_job_approvee_1 = list_job_approvee_1.replace("[", "`");
    			list_job_approvee_1 = list_job_approvee_1.replace("]", "`");
    			StringTokenizer st1 = new StringTokenizer(list_job_approvee_1,"`");
    			String list_id_approvee_1 = new String(list_id_approvee);
    			list_id_approvee_1 = list_id_approvee_1.replace("][", "`");
    			list_id_approvee_1 = list_id_approvee_1.replace("[", "`");
    			list_id_approvee_1 = list_id_approvee_1.replace("]", "`");
    			StringTokenizer st12 = new StringTokenizer(list_id_approvee_1,"`");
    			boolean all_approved = false;
    			boolean match = false;
    			while(st1.hasMoreTokens() && !match) {
    				String tkn_job = st1.nextToken();
    				st12.nextToken();
    				if(tkn_job.equalsIgnoreCase(current_job_approvee)) {
    					match = true; //match -- berarti next approvee nextnya
    				}
    			}
    			if(st1.hasMoreTokens()) {
    				next_job_approvee = new String(st1.nextToken());
    				next_id_approvee = new String(st12.nextToken());
    				stmt.setInt(i++, Integer.parseInt(next_id_approvee));
        			stmt.setString(i++, next_job_approvee);
        			stmt.setBoolean(i++, false);
        			
    			}
    			else {
    				//alll approved
    				all_approved = true;
    				stmt.setNull(i++, java.sql.Types.INTEGER);
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        			stmt.setBoolean(i++, true);
    			}
    			stmt.executeUpdate();
    		
    			
    			//1. update classpool table	
    			//stmt = con.prepareStatement("update CLASS_POOL set LATEST_STATUS_INFO=?,LOCKED=?,TKN_NPM_APPROVAL=?,TKN_APPROVAL_TIME=?,REJECTED=?,PASSED=? where KDPST=? and THSMS=?");
    			stmt = con.prepareStatement("update CLASS_POOL set LATEST_STATUS_INFO=?,LOCKED=?,REJECTED=?,PASSED=? where KDPST=? and THSMS=? and KODE_KAMPUS=?");
    			if(all_approved) {
    				stmt.setNull(1, java.sql.Types.VARCHAR);
    				stmt.setBoolean(2, true);
    				stmt.setBoolean(3, false);
    				stmt.setBoolean(4, true);
    				stmt.setString(5, kdpst);
    				stmt.setString(6, thsms);
    				stmt.setString(7, kmp);
    				stmt.executeUpdate();
    				//update OVERVIEW_SEBARAN_TRLSM
    				stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set BELUM_MENGAJUKAN_KELAS_PERKULIAHAN=?,PENGAJUKAN_KELAS_PERKULIAHAN_INPROGRESS=?,TIDAK_ADA_PERKULIAHAN=? where ID_OBJ=? and THSMS=?");
    				stmt.setBoolean(1, false);
    				stmt.setBoolean(2, false);
    				stmt.setBoolean(3, false);
    				stmt.setInt(4, Integer.parseInt(id));
    				//System.out.println("idi="+id);
    				stmt.setString(5, thsms);
    				stmt.executeUpdate();
    			}
    			else {
    				stmt.setNull(1, java.sql.Types.VARCHAR);
    				stmt.setBoolean(2, false);
    				stmt.setBoolean(3, false);
    				stmt.setBoolean(4, true);
    				stmt.setString(5, kdpst);
    				stmt.setString(6, thsms);
    				stmt.setString(7, kmp);	
    				stmt.executeUpdate();
    			}
    			
    			
    		}
    		else if(verdict.equalsIgnoreCase("tolak")) {
    		//1. update classpool table	
    			stmt = con.prepareStatement("update CLASS_POOL set LATEST_STATUS_INFO=?,REJECTED=?,PASSED=? where KDPST=? and THSMS=? and KODE_KAMPUS=?");
    			stmt.setNull(1, java.sql.Types.VARCHAR);
    			stmt.setBoolean(2, true);
    			stmt.setBoolean(3, false);
    			stmt.setString(4, kdpst);
    			stmt.setString(5, thsms);
    			stmt.setString(6, kmp);
    			stmt.executeUpdate();
    		//2.update CLASS_POOL_APPROVAL
    			stmt = con.prepareStatement("INSERT INTO CLASS_POOL_APPROVAL(ID_OBJ,THSMS,KDPST,KODE_KAMPUS,COMMENT,APPROVED,CURRENT_ID_APPROVAL,CURRENT_JABATAN_APPROVAL,ID_APPROVAL_NEEDED,JABATAN_APPROVAL_NEEDED,ALL_APPROVED)values(?,?,?,?,?,?,?,?,?,?,?)");
    			i = 1;
    			stmt.setInt(i++, Integer.parseInt(id));
    			stmt.setString(i++, thsms);
    			stmt.setString(i++, kdpst);
    			stmt.setString(i++, kmp);
    			stmt.setString(i++, alasan);
    			stmt.setBoolean(i++, false);
    			//penolakan needed approvee = usr saat ini
    			//bila penerimamaan maka needed approvee = next token berikutnya bila ada, kalo ngga ada = allapproved
    			stmt.setInt(i++, Integer.parseInt(current_id_approvee));
    			stmt.setString(i++, current_job_approvee);
    			stmt.setInt(i++, Integer.parseInt(current_id_approvee));
    			stmt.setString(i++, current_job_approvee);
    			stmt.setBoolean(i++, false);
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
    
    public void updateJadwalRuanganKelas(String kelas_cuid,String gedung,String room,String[]hari,String[]time) {
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		Vector vid =new Vector();
    		Vector vit =new Vector();
    		ListIterator lid = vid.listIterator();
    		ListIterator lit = vit.listIterator();
    		//System.out.println("hari-leng="+hari.length);
			if(hari!=null && hari.length>0 ) {
				String day_time = "";
				
				for(int i=0;i<hari.length;i++) {
					//System.out.println("hari-"+i+"="+hari[i]);
					if(hari[i].equalsIgnoreCase("sen")) {
						lid.add("Sn");;
						lit.add(time[0]);
						
					}
					else if(hari[i].equalsIgnoreCase("sel")) {
						lid.add("Sl");;
						lit.add(time[1]);
					}
					else if(hari[i].equalsIgnoreCase("rab")) {
						lid.add("Rb");;
						lit.add(time[2]);
					}
					else if(hari[i].equalsIgnoreCase("kam")) {
						lid.add("Km");;
						lit.add(time[3]);
					}
					else if(hari[i].equalsIgnoreCase("jum")) {
						lid.add("Jm");;
						lit.add(time[4]);
					}
					else if(hari[i].equalsIgnoreCase("sab")) {
						lid.add("Sb");;
						lit.add(time[5]);
					}
					else if(hari[i].equalsIgnoreCase("min")) {
						
						lid.add("Mn");;
						lit.add(time[6]);
					}
					
					
				}
				
				lit = vit.listIterator();
				while(lit.hasNext()) {
					String brs = (String)lit.next();
					if(Checker.isStringNullOrEmpty(brs)) {
						lit.remove();
					}
				}
				Vector vtmp= Tool.removeDuplicateFromVector(vit);
			
				if(vtmp.size()>0) {
					if(vtmp.size()==1) {
						lit = vtmp.listIterator();
						lid = vid.listIterator();
						while(lid.hasNext()) {
							String dd = (String)lid.next();
							day_time = day_time+dd;
							if(lid.hasNext()) {
								day_time = day_time+",";
							}
						}
						if(!Checker.isStringNullOrEmpty((String)vit.get(0))) {//at(0) bisa empty soalnya
							//waktu sama untuk tiap pertemuan	
							day_time = day_time+"/"+(String)lit.next();
						}
						
					}
					else {
						lit = vit.listIterator();
						lid = vid.listIterator();
						while(lid.hasNext()) {
							String dd = (String)lid.next();
							String hr = (String)lit.next();
							day_time = day_time+dd+"/"+hr+"";
							if(lid.hasNext()) {
								day_time = day_time+",";
							}
						}
					}
				}
				else {
					//tidak ada info jam //  kayaknya ngga akan pernah kesini krn masuk ke empty valud diaytas
					lid = vid.listIterator();
					while(lid.hasNext()) {
						String dd = (String)lid.next();
						day_time = day_time+dd;
						if(lid.hasNext()) {
							day_time = day_time+",";
						}
					}
				}
				//System.out.println("day_time="+day_time);
				//System.out.println("kelas_cuid="+kelas_cuid);
				
	    		stmt = con.prepareStatement("update CLASS_POOL set KODE_RUANG=?,KODE_GEDUNG=?,TKN_HARI_TIME=? where UNIQUE_ID=?");
	    		if(room==null || Checker.isStringNullOrEmpty(room)) {
	    			stmt.setNull(1, java.sql.Types.VARCHAR);
	    		}
	    		else {
	    			stmt.setString(1, room);
	    		}
	    		if(gedung==null || Checker.isStringNullOrEmpty(gedung)) {
	    			stmt.setNull(2, java.sql.Types.VARCHAR);
	    		}
	    		else {
	    			stmt.setString(2, gedung);
	    		}
	    		if(day_time==null || Checker.isStringNullOrEmpty(day_time)) {
	    			stmt.setNull(3, java.sql.Types.VARCHAR);
	    		}
	    		else {
	    			stmt.setString(3, day_time);
	    		}
	    		stmt.setLong(4, Long.parseLong(kelas_cuid));
	    		stmt.executeUpdate();
			}
			else {
				//ngga ada jadwa = reset
				stmt = con.prepareStatement("update CLASS_POOL set KODE_RUANG=?,KODE_GEDUNG=?,TKN_HARI_TIME=? where UNIQUE_ID=?");
	    		stmt.setNull(1, java.sql.Types.VARCHAR);
	    		stmt.setNull(2, java.sql.Types.VARCHAR);
	    		stmt.setNull(3, java.sql.Types.VARCHAR);
	    		stmt.setLong(4, Long.parseLong(kelas_cuid));
	    		stmt.executeUpdate();
			}
    		//String ipAddr =  request.getRemoteAddr();
    		
    		
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
    
    public int resetPersetujuanPengajuanKelas(String target_thsms, String id_obj_prodi, String target_kdpst) {
    	int upd=0;
    	try {
			
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		
    		stmt = con.prepareStatement("delete from CLASS_POOL_APPROVAL where THSMS=? and ID_OBJ=?");
    		stmt.setString(1, target_thsms);
    		stmt.setInt(2, Integer.parseInt(id_obj_prodi));
    		upd = stmt.executeUpdate();
    		if(upd>0) {
    			stmt = con.prepareStatement("update CLASS_POOL set LOCKED=false where THSMS=? and KDPST=?");
    			stmt.setString(1, target_thsms);
    			stmt.setString(2, target_kdpst);
    			upd = upd+stmt.executeUpdate();
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
    	return upd;
    }
    
    
    public int fixPenomoranKelasParalel(String target_thsms) {
    	int upd=0;
    	try {
			
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		String sql ="SELECT IDKMK,KDPST,SHIFT,NORUT_KELAS_PARALEL,UNIQUE_ID FROM CLASS_POOL " +
    				"where THSMS='"+target_thsms+"' and REJECTED=false and CANCELED=false " + 
    				"order by KDPST,IDKMK,UNIQUE_ID";
    		stmt = con.prepareStatement(sql);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			Vector v = new Vector();
    			ListIterator li = v.listIterator();
    			do {
    				String idkmk = rs.getString(1);
    				String kdpst = rs.getString(2);
    				String shift = rs.getString(3);
    				String norut = rs.getString(4);
    				String cuid = rs.getString(5);
    				int no_urut=1;
    				li.add(cuid+"~"+no_urut);
    				while(rs.next()) {
    					String next_idkmk = rs.getString(1);
        				String next_kdpst = rs.getString(2);
        				String next_shift = rs.getString(3);
        				String next_norut = rs.getString(4);
        				String next_cuid = rs.getString(5);
        				if(next_idkmk.equalsIgnoreCase(idkmk) &&
        					next_shift.equalsIgnoreCase(shift)	
        				) {
        					//pelajaran yg sama dalam satu shift = paralel
        					no_urut++;
        				}
        				else {
        					idkmk = new String(next_idkmk);
            				shift = new String(next_shift);
            				
        					no_urut=1;
        				}
        				li.add(next_cuid+"~"+no_urut);
    				}
    			}
    			while(rs.next());
    			//update no kls pll
    			li = v.listIterator();
    			stmt = con.prepareStatement("update CLASS_POOL set NORUT_KELAS_PARALEL=? where UNIQUE_ID=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"~");
    				String cuid = st.nextToken();
    				String nopll = st.nextToken();
    				stmt.setInt(1, Integer.parseInt(nopll));
    				stmt.setInt(2, Integer.parseInt(cuid));
    				
    				upd= upd+stmt.executeUpdate();
    				//System.out.println("upd "+brs+" = "+upd);
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
    	return upd;
    }
    
    public int updateDosenAjar(String[]info) {
    	int upd=0;
    	if(info!=null && info.length>0) {
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		String sql = "update CLASS_POOL set NPMDOS=?,NMMDOS=? where UNIQUE_ID=?";
        		stmt = con.prepareStatement(sql);
        		for(int i=0;i<info.length;i++) {
        			StringTokenizer st = new StringTokenizer(info[i],"~");
        			String cuid = st.nextToken();
        			String nidn = st.nextToken();
        			String npmdos = st.nextToken();
        			String nmmdos = st.nextToken();
        			stmt.setString(1, npmdos);
        			stmt.setString(2, nmmdos);
        			stmt.setInt(3, Integer.parseInt(cuid));
        			upd = upd+stmt.executeUpdate();
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
    	
    	return upd;
    }
 }

package beans.dbase.trakm;

import beans.dbase.SearchDb;
import beans.dbase.krklm.SearchDbKrklm;
import beans.dbase.trnlp.SearchDbTrnlp;
import beans.setting.Constants;
import beans.tools.Checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ListIterator;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class HitungKhs
 */
@Stateless
@LocalBean
public class HitungKhs extends SearchDb {
	String operatorNpm;
	String operatorNmm;
	String tknOperatorNickname;
	boolean petugas;
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;    
	Random randomNumberGenerator;   
    /**
     * @see SearchDb#SearchDb()
     */
    public HitungKhs() {
        super();
        randomNumberGenerator = new Random();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public HitungKhs(String operatorNpm) {
        super(operatorNpm);
        randomNumberGenerator = new Random();
        this.operatorNpm = operatorNpm;
     	this.operatorNmm = getNmmOperator();
     	this.petugas = cekApaUsrPetugas();
     	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(Connection)
     */
    public HitungKhs(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public Vector listLulusan(String kdpst, String thsms) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from TRLSM inner join CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and KDPST=? and STMHS=?");
			stmt.setString(1, thsms);
			stmt.setString(2, kdpst);
			stmt.setString(3, "L");
			rs = stmt.executeQuery();
			while(rs.next()) {
				String nmmhs = ""+rs.getString("NMMHSMSMHS");
				String nimhs = ""+rs.getString("NIMHSMSMHS");
				String tglls = ""+rs.getDate("TGLLS");
				li.add(nmmhs+"`"+nimhs+"`"+tglls);
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
    	return v;
	}	
    
    /*
     * DEPRECATED JANGAN PERNAH DIPAKE
     * 	 
     * DUPLICAT ada kembarannya di SearchDb jadi kalo diupdate hrs dua2nya
     */
    //public void updateIndividualTrakm(String kdpst, String npmhs) {
    public void hitungTrakmSemesteran(String kdpst, String target_thsms) {
    	DecimalFormat df = new DecimalFormat("#.##");
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
			con = ds.getConnection();
			stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=?");
			String tkn_npm = null;
			stmt.setString(1, target_thsms);
			stmt.setString(2, kdpst);
			rs = stmt.executeQuery();
			int counter=0;
			while(rs.next()) {
				//counter++;
				String npmhs = rs.getString(1);
				if(tkn_npm==null) {
					tkn_npm = new String(npmhs);
				}
				else {
					tkn_npm = tkn_npm+"`"+npmhs;
				}
				//System.out.println(counter+". "+npmhs);
			}
			//get krs khs tiap semester
			Vector v = null;
			stmt = con.prepareStatement("select KDKMKTRNLM,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,NILAIROBOT,NLAKHROBOT,BOBOTROBOT from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
			if(tkn_npm!=null) {
				StringTokenizer st = new StringTokenizer(tkn_npm,"`");
				v = new Vector();
				ListIterator li = v.listIterator();
				while(st.hasMoreTokens()) {
					String npmhs = st.nextToken();
					li.add(npmhs);
					stmt.setString(1, target_thsms);
					stmt.setString(2, npmhs);
					rs = stmt.executeQuery();
					String tkn_krs = null;
					double tot_sks = 0;
					double tot_pembagi = 0;
					double tot_nilai = 0;
					double tot_sks_robot = 0;
					double tot_nilai_robot = 0;
					//tot_pembagi_robot = tot_sks, krn semua sudah dikasih nilai
					while(rs.next()) {
						String kdkmk = ""+rs.getString("KDKMKTRNLM");
						String nilai = ""+rs.getDouble("NILAITRNLM");
						String nlakh = ""+rs.getString("NLAKHTRNLM");
						double bobot = rs.getDouble("BOBOTTRNLM");
						int sksmk = rs.getInt("SKSMKTRNLM");
						String nilai_r = ""+rs.getDouble("NILAIROBOT");
						String nlakh_r = ""+rs.getString("NLAKHROBOT");
						double bobot_r = rs.getDouble("BOBOTROBOT");
						tot_sks = tot_sks + sksmk;
						if(bobot>0 || !nlakh.equalsIgnoreCase("T")) {
							tot_nilai = tot_nilai + (bobot*sksmk);
							tot_nilai_robot = tot_nilai_robot+(bobot*sksmk);
							tot_pembagi = tot_pembagi+sksmk;
						}
						else {
							//belum dikasih nilai, jadi ambil dari kolom robot
							tot_nilai_robot = tot_nilai_robot + (bobot_r*sksmk);
						}
						if(tkn_krs==null) {
							tkn_krs = new String();
							tkn_krs = kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+nilai_r+"`"+nlakh_r+"`"+bobot_r;
						}
						else {
							tkn_krs = tkn_krs+"`"+kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+nilai_r+"`"+nlakh_r+"`"+bobot_r;
						}
					}
					li.add(tkn_krs);
					
					String ips_robot = df.format(tot_nilai_robot/tot_sks);
					String ips = "0.0";
					if(tot_pembagi>0) {
						//beda tot_pembagi = kalo ada nilai tunda (bukan kolom robot) maka sks mk tersebut tidak diikutkan sbg pembagi
						ips = df.format(tot_nilai/tot_pembagi); 
					}
					
					li.add(tot_sks+"`"+tot_nilai+"`"+ips+"`"+ips_robot);
				}
			}
			
			if(v!=null && v.size()>0) {
				ListIterator li = v.listIterator();
				while(li.hasNext()) {
					String npmhs = (String) li.next();
					//System.out.println(npmhs);
					String tkn_krs = (String) li.next();
					//System.out.println(tkn_krs);
					String tkn_penilaian = (String) li.next();
					//System.out.println(tkn_penilaian);
					StringTokenizer st = new StringTokenizer(tkn_penilaian,"`");
					String tot_sks = st.nextToken();
					String tot_nilai = st.nextToken();
					String tot_ips = st.nextToken();
					String tot_ips_robot = st.nextToken();
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
    
   
    
    public void hitungRiwayatTrakmMhs(Vector v_npmhs) {
    	//part 1 : perhitungan nilai semester
    	if(v_npmhs!=null) {
    		ListIterator linpm = v_npmhs.listIterator();
    		//hitung seluruh thsms
    		String tkn_thsms = null;
        	DecimalFormat df = new DecimalFormat("#.##");
        	int norut = 0;
        	try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    			con = ds.getConnection();
    			
    			while(linpm.hasNext()) {
    				norut++;
    				String npmhs = (String)linpm.next();
    				//System.out.println(norut+". hitung nilai semestare npm=="+npmhs);
    				String thsms = null;
    				tkn_thsms = null;
    	        	String kdpst = Checker.getKdpst(npmhs);
    	        	String kdjen = Checker.getKdjen(kdpst);
    	        	String kdpti = Checker.getKdpti();
    	        	
    	        	//1. HAPUS NILAI RIWAYAT SEBELUMNYA KARENA MO DIUPDATE 
        			//stmt = con.prepareStatement("delete from TRAKM where NPMHSTRAKM=?");
        			//stmt.setString(1, npmhs);
        			//stmt.executeUpdate();
        			
        			
        			stmt = con.prepareStatement("select distinct THSMSTRNLM from TRNLM where THSMSTRNLM is not null and NPMHSTRNLM=? order by THSMSTRNLM");
        			
        			stmt.setString(1, npmhs);
        			rs = stmt.executeQuery();
        			int counter=0;
        			while(rs.next()) {
        				//counter++;
        				
        				thsms = rs.getString(1);
        				//System.out.println("kok thsms="+thsms);
        				if(tkn_thsms==null) {
        					tkn_thsms = new String(thsms);
        				}
        				else {
        					tkn_thsms = tkn_thsms+"`"+thsms;
        				}//System.out.println(counter+". "+npmhs);
        			}
        			//System.out.println("tkn_thsms="+tkn_thsms);
        			//get krs khs tiap semester
        			Vector v = null;
        			stmt = con.prepareStatement("select KDKMKTRNLM,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,NILAIROBOT,NLAKHROBOT,BOBOTROBOT from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
        			if(!Checker.isStringNullOrEmpty(tkn_thsms)) {
        				StringTokenizer st = new StringTokenizer(tkn_thsms,"`");
        				v = new Vector();
        				ListIterator li = v.listIterator();
        				while(st.hasMoreTokens()) {
        					thsms = st.nextToken();
        					li.add(thsms);
        					stmt.setString(1, thsms);
        					stmt.setString(2, npmhs);
        					rs = stmt.executeQuery();
        					String tkn_krs = null;
        					double tot_sks = 0;
        					double tot_pembagi = 0;
        					double tot_nilai = 0;
        					double tot_sks_robot = 0;
        					double tot_nilai_robot = 0;
        					//tot_pembagi_robot = tot_sks, krn semua sudah dikasih nilai
        					while(rs.next()) {
        						String kdkmk = ""+rs.getString("KDKMKTRNLM");
        						String nilai = ""+rs.getDouble("NILAITRNLM");
        						String nlakh = ""+rs.getString("NLAKHTRNLM");
        						double bobot = rs.getDouble("BOBOTTRNLM");
        						int sksmk = rs.getInt("SKSMKTRNLM");
        						String nilai_r = ""+rs.getDouble("NILAIROBOT");
        						String nlakh_r = ""+rs.getString("NLAKHROBOT");
        						double bobot_r = rs.getDouble("BOBOTROBOT");
        						tot_sks = tot_sks + sksmk;
        						if(bobot>0 || !nlakh.equalsIgnoreCase("T")) {
        							tot_nilai = tot_nilai + (bobot*sksmk);
        							tot_nilai_robot = tot_nilai_robot+(bobot*sksmk);
        							tot_pembagi = tot_pembagi+sksmk;
        						}
        						else {
        							//belum dikasih nilai, jadi ambil dari kolom robot
        							tot_nilai_robot = tot_nilai_robot + (bobot_r*sksmk);
        						}
        						if(tkn_krs==null) {
        							tkn_krs = new String();
        							tkn_krs = kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+nilai_r+"`"+nlakh_r+"`"+bobot_r;
        						}
        						else {
        							tkn_krs = tkn_krs+"`"+kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+nilai_r+"`"+nlakh_r+"`"+bobot_r;
        						}
        					}
        					li.add(tkn_krs);
        					
        					String ips_robot = df.format(tot_nilai_robot/tot_sks);
        					String ips = "0.0";
        					if(tot_pembagi>0) {
        						ips = df.format(tot_nilai/tot_pembagi);
        					}
        					
        					li.add(tot_sks+"`"+tot_nilai+"`"+ips+"`"+ips_robot);
        				}
        			}
        			
        			
        			if(v!=null && v.size()>0) {
        				Vector v_ins = null;
        				ListIterator lins = null;
        				ListIterator li = v.listIterator();
        				//coba update dulu
        				int i = 0;
        				stmt = con.prepareStatement("update TRAKM set SKSEMTRAKM=?,NLIPSTRAKM=?,SKSEMROBOT=?,NLIPSROBOT=? where THSMSTRAKM=? and NPMHSTRAKM=?");
        				while(li.hasNext()) {
        					thsms = (String) li.next();
        					//System.out.println("update="+thsms);
        					String tkn_krs = (String) li.next();
        					//System.out.println(tkn_krs);
        					String tkn_penilaian = (String) li.next();
        					//System.out.println(tkn_penilaian);
        					StringTokenizer st = new StringTokenizer(tkn_penilaian,"`");
        					String tot_sks = st.nextToken();
        					String tot_nilai = st.nextToken();
        					String tot_ips = st.nextToken();
        					String tot_ips_robot = st.nextToken();
        					stmt.setInt(1, (int) Double.parseDouble(tot_sks));
        					stmt.setDouble(2, Double.parseDouble(tot_ips));
        					stmt.setInt(3, (int) Double.parseDouble(tot_sks));
        					stmt.setDouble(4, Double.parseDouble(tot_ips_robot));
        					stmt.setString(5 , thsms);
        					stmt.setString(6 , npmhs);
        					i=0;
        					i=stmt.executeUpdate();
        					//System.out.println("updated="+i);
        					if(i<1) {
        						//insert
        						if(v_ins==null) {
        							v_ins = new Vector();
        							lins = v_ins.listIterator();
        						}
        						lins.add(thsms);
        						lins.add(tkn_krs);
        						lins.add(tkn_penilaian);
        					}
        				}
        				
        				if(v_ins!=null) {
        					lins = v_ins.listIterator();
        					stmt = con.prepareStatement("insert into TRAKM (THSMSTRAKM,KDPTITRAKM,KDJENTRAKM,KDPSTTRAKM,NPMHSTRAKM,SKSEMTRAKM,NLIPSTRAKM,SKSEMROBOT,NLIPSROBOT)values(?,?,?,?,?,?,?,?,?)");
        					while(lins.hasNext()) {
        						thsms = (String) lins.next();
        						//System.out.println("insert="+thsms);
        						String tkn_krs = (String) lins.next();
        						//System.out.println(tkn_krs);
        						String tkn_penilaian = (String) lins.next();
        						//System.out.println(tkn_penilaian);
        						StringTokenizer st = new StringTokenizer(tkn_penilaian,"`");
        						String tot_sks = st.nextToken();
        						String tot_nilai = st.nextToken();
        						String tot_ips = st.nextToken();
        						String tot_ips_robot = st.nextToken();
        						stmt.setInt(1, (int) Double.parseDouble(tot_sks));
        						stmt.setDouble(2, Double.parseDouble(tot_ips));
        						stmt.setInt(3, (int) Double.parseDouble(tot_sks));
        						stmt.setDouble(4, Double.parseDouble(tot_ips_robot));
        						stmt.setString(1, thsms);
        						stmt.setString(2, kdpti);
        						stmt.setString(3, kdjen);
        						stmt.setString(4, kdpst);
        						stmt.setString(5, npmhs);
        						stmt.setInt(6, (int) Double.parseDouble(tot_sks));
        						stmt.setDouble(7, Double.parseDouble(tot_ips));
        						stmt.setInt(8, (int) Double.parseDouble(tot_sks));
        						stmt.setDouble(9, Double.parseDouble(tot_ips_robot));
        						stmt.executeUpdate();
        					}
        					
        				}
        			}
        			//part 2 : perhitungan nilai comulatif        			
        			hitungRiwayatTrakmComulativeMhs(npmhs, tkn_thsms, con);	
    			}//end while
    			
    			
    			
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
    
    
    public void hitungSksemDanNlipsBasedOnTrnlmRilDanRobot(Vector v_npmhs, String thsms) {
    	//part 1 : perhitungan nilai semester
    	if(v_npmhs!=null) {
    		StringTokenizer st = null;
    		ListIterator linpm = v_npmhs.listIterator();
    		//hitung seluruh thsms
    		String tkn_thsms = null;
        	DecimalFormat df = new DecimalFormat("#.##");
        	int norut = 0;
        	try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    			con = ds.getConnection();
    			
    			while(linpm.hasNext()) {
    				norut++;
    				String npmhs = (String)linpm.next();
    				st = new StringTokenizer(npmhs,"`");
    				npmhs = st.nextToken();
    				//System.out.println(norut+". hitung nilai semestare npm=="+npmhs);
    				//String thsms = null;
    				tkn_thsms = thsms;
    	        	String kdpst = Checker.getKdpst(npmhs);
    	        	String kdjen = Checker.getKdjen(kdpst);
    	        	String kdpti = Checker.getKdpti();
    	        	
    	        	//1. HAPUS NILAI RIWAYAT SEBELUMNYA KARENA MO DIUPDATE 
        			//stmt = con.prepareStatement("delete from TRAKM where NPMHSTRAKM=?");
        			//stmt.setString(1, npmhs);
        			//stmt.executeUpdate();
        			
        			/*
        			 * di fungsi ini hanya 1 target thsms 
        			stmt = con.prepareStatement("select distinct THSMSTRNLM from TRNLM where THSMSTRNLM is not null and NPMHSTRNLM=? order by THSMSTRNLM");
        			
        			stmt.setString(1, npmhs);
        			rs = stmt.executeQuery();
        			int counter=0;
        			while(rs.next()) {
        				//counter++;
        				
        				thsms = rs.getString(1);
        				//System.out.println("kok thsms="+thsms);
        				if(tkn_thsms==null) {
        					tkn_thsms = new String(thsms);
        				}
        				else {
        					tkn_thsms = tkn_thsms+"`"+thsms;
        				}//System.out.println(counter+". "+npmhs);
        			}
        			*/
        			//System.out.println("tkn_thsms="+tkn_thsms);
        			//get krs khs tiap semester
        			Vector v = null;
        			stmt = con.prepareStatement("select KDKMKTRNLM,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,NILAIROBOT,NLAKHROBOT,BOBOTROBOT from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
        			if(!Checker.isStringNullOrEmpty(tkn_thsms)) {
        				st = new StringTokenizer(tkn_thsms,"`");
        				v = new Vector();
        				ListIterator li = v.listIterator();
        				while(st.hasMoreTokens()) {
        					thsms = st.nextToken();
        					li.add(thsms);
        					stmt.setString(1, thsms);
        					stmt.setString(2, npmhs);
        					rs = stmt.executeQuery();
        					String tkn_krs = null;
        					double tot_sks = 0;
        					double tot_pembagi = 0;
        					double tot_nilai = 0;
        					double tot_sks_robot = 0;
        					double tot_nilai_robot = 0;
        					//tot_pembagi_robot = tot_sks, krn semua sudah dikasih nilai
        					while(rs.next()) {
        						String kdkmk = ""+rs.getString("KDKMKTRNLM");
        						String nilai = ""+rs.getDouble("NILAITRNLM");
        						String nlakh = ""+rs.getString("NLAKHTRNLM");
        						double bobot = rs.getDouble("BOBOTTRNLM");
        						int sksmk = rs.getInt("SKSMKTRNLM");
        						String nilai_r = ""+rs.getDouble("NILAIROBOT");
        						String nlakh_r = ""+rs.getString("NLAKHROBOT");
        						double bobot_r = rs.getDouble("BOBOTROBOT");
        						tot_sks = tot_sks + sksmk;
        						
    							if(!nlakh.equalsIgnoreCase("T")) {
        							//tot_nilai = tot_nilai + (bobot*sksmk);
        							//tot_nilai_robot = tot_nilai_robot+(bobot*sksmk);
        							tot_pembagi = tot_pembagi+sksmk;
        							if(nlakh.equalsIgnoreCase("E")||bobot>2.95) {
            							//biasanya yg dapet E pelajaran TUGAS AKHIR jadi Nilai robot ahrus ikut
        								//nilai asli karena nanti kalo ada nilainua berarti harusnya lulus dong
        								//(PAKE NILAI ASLI)
            							tot_nilai_robot = tot_nilai_robot + (bobot*sksmk);
            						}
        							else if(bobot<2.95) {
        								tot_nilai_robot = tot_nilai_robot + (bobot_r*sksmk);
        								
        							}
        						}
    							else {
    								//nilai tunda harus pake robot
    								tot_nilai_robot = tot_nilai_robot + (bobot_r*sksmk);
    								tot_pembagi = tot_pembagi+sksmk;
    							}
        						
        						if(tkn_krs==null) {
        							tkn_krs = new String();
        							tkn_krs = kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+nilai_r+"`"+nlakh_r+"`"+bobot_r;
        						}
        						else {
        							tkn_krs = tkn_krs+"`"+kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+nilai_r+"`"+nlakh_r+"`"+bobot_r;
        						}
        						
        						
        					}
        					li.add(tkn_krs);
        					if(npmhs.equalsIgnoreCase("2320116200001")) {
        						//System.out.println("tot_nilai_robot="+tot_nilai_robot);
    							//System.out.println("tkn_krs="+tkn_krs);
    						}
        					//ips robot always dibagi total sks karena semua nilai sudah dikasih
        					String ips_robot = df.format(tot_nilai_robot/tot_sks);
        					String ips = "0.0";
        					//ips = df.format(tot_nilai/tot_sks);
        					if(tot_pembagi>0) {
        						ips = df.format(tot_nilai/tot_pembagi);
        					}
        					
        					li.add(tot_sks+"`"+tot_nilai+"`"+ips+"`"+ips_robot);
        					if(npmhs.equalsIgnoreCase("2320116200001")) {
    							//System.out.println("tkn_krs-1="+tot_sks+"`"+tot_nilai+"`"+ips+"`"+ips_robot);
    						}
        				}
        			}
        			
        			
        			if(v!=null && v.size()>0) {
        				Vector v_ins = null;
        				ListIterator lins = null;
        				ListIterator li = v.listIterator();
        				//coba update dulu
        				int i = 0;
        				stmt = con.prepareStatement("update TRAKM set SKSEMTRAKM=?,NLIPSTRAKM=?,SKSEMROBOT=?,NLIPSROBOT=? where THSMSTRAKM=? and NPMHSTRAKM=?");
        				while(li.hasNext()) {
        					thsms = (String) li.next();
        					//System.out.println("update="+thsms);
        					String tkn_krs = (String) li.next();
        					//System.out.println(tkn_krs);
        					String tkn_penilaian = (String) li.next();
        					//System.out.println(tkn_penilaian);
        					st = new StringTokenizer(tkn_penilaian,"`");
        					String tot_sks = st.nextToken();
        					String tot_nilai = st.nextToken();
        					String tot_ips = st.nextToken();
        					String tot_ips_robot = st.nextToken();
        					stmt.setInt(1, (int) Double.parseDouble(tot_sks));
        					stmt.setDouble(2, Double.parseDouble(tot_ips));
        					stmt.setInt(3, (int) Double.parseDouble(tot_sks));
        					stmt.setDouble(4, Double.parseDouble(tot_ips_robot));
        					stmt.setString(5 , thsms);
        					stmt.setString(6 , npmhs);
        					i=0;
        					i=stmt.executeUpdate();
        					if(npmhs.equalsIgnoreCase("2320116200001")) {
        						//System.out.println("update "+npmhs+" "+tkn_penilaian+" = "+i );
        					}
        					
        					if(i<1) {
        						//insert
        						if(v_ins==null) {
        							v_ins = new Vector();
        							lins = v_ins.listIterator();
        						}
        						lins.add(thsms);
        						lins.add(tkn_krs);
        						lins.add(tkn_penilaian);
        					}
        				}
        				
        				if(v_ins!=null) {
        					lins = v_ins.listIterator();
        					stmt = con.prepareStatement("insert into TRAKM (THSMSTRAKM,KDPTITRAKM,KDJENTRAKM,KDPSTTRAKM,NPMHSTRAKM,SKSEMTRAKM,NLIPSTRAKM,SKSEMROBOT,NLIPSROBOT)values(?,?,?,?,?,?,?,?,?)");
        					while(lins.hasNext()) {
        						thsms = (String) lins.next();
        						//System.out.println("insert="+thsms);
        						String tkn_krs = (String) lins.next();
        						//System.out.println(tkn_krs);
        						String tkn_penilaian = (String) lins.next();
        						//System.out.println(tkn_penilaian);
        						st = new StringTokenizer(tkn_penilaian,"`");
        						String tot_sks = st.nextToken();
        						String tot_nilai = st.nextToken();
        						String tot_ips = st.nextToken();
        						String tot_ips_robot = st.nextToken();
        						stmt.setInt(1, (int) Double.parseDouble(tot_sks));
        						stmt.setDouble(2, Double.parseDouble(tot_ips));
        						stmt.setInt(3, (int) Double.parseDouble(tot_sks));
        						stmt.setDouble(4, Double.parseDouble(tot_ips_robot));
        						stmt.setString(1, thsms);
        						stmt.setString(2, kdpti);
        						stmt.setString(3, kdjen);
        						stmt.setString(4, kdpst);
        						stmt.setString(5, npmhs);
        						stmt.setInt(6, (int) Double.parseDouble(tot_sks));
        						stmt.setDouble(7, Double.parseDouble(tot_ips));
        						stmt.setInt(8, (int) Double.parseDouble(tot_sks));
        						stmt.setDouble(9, Double.parseDouble(tot_ips_robot));
        						stmt.executeUpdate();
        					}
        					
        				}
        			}
        			//part 2 : perhitungan nilai comulatif
        			//untuk saat ini skstt ada fungsi swendiri dan nlipk kasih random untuk col robot
        			//hitungRiwayatTrakmComulativeMhs(npmhs, tkn_thsms, con);	
    			}//end while
    			
    			
    			
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
    
    
    public void hitungRiwayatTrakmComulativeMhs(String npmhs, String tkn_thsms, Connection con) {
    	//System.out.println("tkn_thsms="+tkn_thsms);
    	if(!Checker.isStringNullOrEmpty(npmhs)&&!Checker.isStringNullOrEmpty(tkn_thsms)) {
    		//ListIterator linpm = v_npmhs.listIterator();
    		//hitung seluruh thsms
    		
        	DecimalFormat df = new DecimalFormat("#.##");
        	int norut = 0;
        	try {
        		//Context initContext  = new InitialContext();
    			
    			
        		String thsms = "";
    	        String kdpst = Checker.getKdpst(npmhs);
    	        String kdjen = Checker.getKdjen(kdpst);
    	        String kdpti = Checker.getKdpti();
    	        	
    	        int init_tot_sks = 0;
    	        double init_tot_nilai = 0;
    	        int init_tot_pembagi = 0;
     	        //1. get data pindahan
    	        stmt = con.prepareStatement("select BOBOTTRNLP,SKSMKTRNLP from TRNLP where NPMHSTRNLP=?");
    	        stmt.setString(1, npmhs);
    	        rs = stmt.executeQuery();
    	        while(rs.next()) {
    	        	float bobot = rs.getFloat(1);
    	        	int sksmk = rs.getInt(2);
    	        	init_tot_sks = init_tot_sks+sksmk;
    	        	init_tot_nilai = init_tot_nilai+(bobot * sksmk);
    	        	init_tot_pembagi = init_tot_pembagi + sksmk;
    	        }
        			
        		/*
        			stmt = con.prepareStatement("select distinct THSMSTRNLM from TRNLM where THSMSTRNLM is not null and NPMHSTRNLM=? order by THSMSTRNLM");
        			String tkn_thsms = null;
        			stmt.setString(1, npmhs);
        			rs = stmt.executeQuery();
        			int counter=0;
        			while(rs.next()) {
        				//counter++;
        				thsms = rs.getString(1);
        				if(tkn_thsms==null) {
        					tkn_thsms = new String(thsms);
        				}
        				else {
        					tkn_thsms = tkn_thsms+"`"+thsms;
        				}//System.out.println(counter+". "+npmhs);
        			}
        			*/
        			//get krs khs tiap semester
    	        
    	        
        		Vector v = null;
        		stmt = con.prepareStatement("select KDKMKTRNLM,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,NILAIROBOT,NLAKHROBOT,BOBOTROBOT from TRNLM where NPMHSTRNLM=? and THSMSTRNLM<=? order by KDKMKTRNLM,BOBOTTRNLM desc");
        		if(!Checker.isStringNullOrEmpty(tkn_thsms)) {
        			StringTokenizer st = new StringTokenizer(tkn_thsms,"`");
        			v = new Vector();
        			ListIterator li = v.listIterator();
        			while(st.hasMoreTokens()) {
        				thsms = st.nextToken();
        				int tot_sks = init_tot_sks;
            	        double tot_nilai = init_tot_nilai;
            	        int tot_pembagi = init_tot_pembagi;
            	        double tot_nilai_robot = init_tot_nilai;
        				//System.out.println("target_thsms = "+thsms);
        				li.add(thsms);
        				stmt.setString(1, npmhs);
        				stmt.setString(2, thsms);
        				rs = stmt.executeQuery();
        				String tkn_krs = null;
        				//double tot_sks = 0;
        				//double tot_pembagi = 0;
        				//double tot_nilai = 0;
        				//double tot_sks_robot = tot_sks;
        				
        				String prev_kdkmk = "";
        				
        				//tot_pembagi_robot = tot_sks, krn semua sudah dikasih nilai
        				boolean first = true;
        				while(rs.next()) {
        					if(first) {
        						//System.out.println("1st iter");
        						first = false;
        						
        						prev_kdkmk = ""+rs.getString("KDKMKTRNLM");
            					String nilai = ""+rs.getDouble("NILAITRNLM");
            					String nlakh = ""+rs.getString("NLAKHTRNLM");
            					double bobot = rs.getDouble("BOBOTTRNLM");
            					int sksmk = rs.getInt("SKSMKTRNLM");
            					String nilai_r = ""+rs.getDouble("NILAIROBOT");
            					String nlakh_r = ""+rs.getString("NLAKHROBOT");
            					double bobot_r = rs.getDouble("BOBOTROBOT");
            					tot_sks = tot_sks + sksmk;
            					if(bobot>0 || !nlakh.equalsIgnoreCase("T")) {
            						tot_nilai = tot_nilai + (bobot*sksmk);
            						tot_nilai_robot = tot_nilai_robot+(bobot*sksmk);
            						tot_pembagi = tot_pembagi+sksmk;
            					}
            					else {
            						//belum dikasih nilai, jadi ambil dari kolom robot
            						tot_nilai_robot = tot_nilai_robot + (bobot_r*sksmk);
            					}
            					if(tkn_krs==null) {
            						tkn_krs = new String();
            						tkn_krs = prev_kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+nilai_r+"`"+nlakh_r+"`"+bobot_r;
            					}
            					else {
            						tkn_krs = tkn_krs+"`"+prev_kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+nilai_r+"`"+nlakh_r+"`"+bobot_r;
            					}
        					}
        					else {
        						String kdkmk = ""+rs.getString("KDKMKTRNLM");
            					String nilai = ""+rs.getDouble("NILAITRNLM");
            					String nlakh = ""+rs.getString("NLAKHTRNLM");
            					double bobot = rs.getDouble("BOBOTTRNLM");
            					int sksmk = rs.getInt("SKSMKTRNLM");
            					String nilai_r = ""+rs.getDouble("NILAIROBOT");
            					String nlakh_r = ""+rs.getString("NLAKHROBOT");
            					double bobot_r = rs.getDouble("BOBOTROBOT");
            					
            					if(!kdkmk.equalsIgnoreCase(prev_kdkmk)) {
            						prev_kdkmk = new String(kdkmk);
            						tot_sks = tot_sks + sksmk;
                					if(bobot>0 || !nlakh.equalsIgnoreCase("T")) {
                						tot_nilai = tot_nilai + (bobot*sksmk);
                						tot_nilai_robot = tot_nilai_robot+(bobot*sksmk);
                						tot_pembagi = tot_pembagi+sksmk;
                					}
                					else {
                						//belum dikasih nilai, jadi ambil dari kolom robot
                						tot_nilai_robot = tot_nilai_robot + (bobot_r*sksmk);
                					}
                					if(tkn_krs==null) {
                						tkn_krs = new String();
                						tkn_krs = kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+nilai_r+"`"+nlakh_r+"`"+bobot_r;
                					}
                					else {
                						tkn_krs = tkn_krs+"`"+kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+nilai_r+"`"+nlakh_r+"`"+bobot_r;
                					}
            					}
            					else {
            						//skip karena nilai tertinggi untuk mk yg sama pada di posisi pertama
            					}
        					}
        				}
        				//System.out.println("tkn_krs="+tkn_krs);
        				li.add(tkn_krs);
        				
        				String ips_robot = df.format(tot_nilai_robot/tot_sks);
        				String ips = "0.0";
        				if(tot_pembagi>0) {
        					ips = df.format(tot_nilai/tot_pembagi);
        				}
        				
        				li.add(tot_sks+"`"+tot_nilai+"`"+ips+"`"+ips_robot);
        			}
        		}
        			
        			
        		if(v!=null && v.size()>0) {
        			Vector v_ins = null;
        			ListIterator lins = null;
        			ListIterator li = v.listIterator();
        			//coba update dulu
        			int i = 0;
        			stmt = con.prepareStatement("update TRAKM set SKSTTTRAKM=?,NLIPKTRAKM=?,SKSTTROBOT=?,NLIPKROBOT=? where THSMSTRAKM=? and NPMHSTRAKM=?");
        			while(li.hasNext()) {
        				thsms = (String) li.next();
        				//System.out.println("update total="+thsms);
        				String tkn_krs = (String) li.next();
        				//System.out.println(tkn_krs);
        				String tkn_penilaian = (String) li.next();
        				//System.out.println(tkn_penilaian);
        				StringTokenizer st = new StringTokenizer(tkn_penilaian,"`");
        				String tot_sks_str = st.nextToken();
        				String tot_nilai_str = st.nextToken();
        				String tot_ips = st.nextToken();
        				String tot_ips_robot = st.nextToken();
        				stmt.setInt(1, (int) Double.parseDouble(tot_sks_str));
        				stmt.setDouble(2, Double.parseDouble(tot_ips));
        				stmt.setInt(3, (int) Double.parseDouble(tot_sks_str));
        				stmt.setDouble(4, Double.parseDouble(tot_ips_robot));
        				stmt.setString(5 , thsms);
        				stmt.setString(6 , npmhs);
        				i=0;
        				i=stmt.executeUpdate();
        			//System.out.println("updated="+i);
        				if(i<1) {
        				//insert
        					if(v_ins==null) {
        						v_ins = new Vector();
        						lins = v_ins.listIterator();
        					}
        					lins.add(thsms);
        					lins.add(tkn_krs);
        					lins.add(tkn_penilaian);
        				}
        			}
        			
        			if(v_ins!=null) {
        				lins = v_ins.listIterator();
        				stmt = con.prepareStatement("insert into TRAKM (THSMSTRAKM,KDPTITRAKM,KDJENTRAKM,KDPSTTRAKM,NPMHSTRAKM,SKSTTTRAKM,NLIPKTRAKM,SKSTTROBOT,NLIPKROBOT)values(?,?,?,?,?,?,?,?,?)");
        				while(lins.hasNext()) {
        					thsms = (String) lins.next();
        				//System.out.println("insert="+thsms);
        					String tkn_krs = (String) lins.next();
        				//System.out.println(tkn_krs);
        					String tkn_penilaian = (String) lins.next();
        				//System.out.println(tkn_penilaian);
        					StringTokenizer st = new StringTokenizer(tkn_penilaian,"`");
        					String tot_sks_str = st.nextToken();
        					String tot_nilai_str = st.nextToken();
        					String tot_ips = st.nextToken();
        					String tot_ips_robot = st.nextToken();
        					stmt.setInt(1, (int) Double.parseDouble(tot_sks_str));
        					stmt.setDouble(2, Double.parseDouble(tot_ips));
        					stmt.setInt(3, (int) Double.parseDouble(tot_sks_str));
        					stmt.setDouble(4, Double.parseDouble(tot_ips_robot));
        					stmt.setString(1, thsms);
        					stmt.setString(2, kdpti);
        					stmt.setString(3, kdjen);
        					stmt.setString(4, kdpst);
        					stmt.setString(5, npmhs);
        					stmt.setInt(6, (int) Double.parseDouble(tot_sks_str));
        					stmt.setDouble(7, Double.parseDouble(tot_ips));
        					stmt.setInt(8, (int) Double.parseDouble(tot_sks_str));
        					stmt.setDouble(9, Double.parseDouble(tot_ips_robot));
        					stmt.executeUpdate();
        				}
        				
        			}
        		
        		}
        	}
        	//catch (NamingException e) {
    		//	e.printStackTrace();
    		//}
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
    
    
    
    
    public Vector hitungSksttTrakmRobotBilaSelamaLamaStudiDianggapAktif(Vector v_dbInfoMhs_getTotSmsLamaStudiDariSmawlDanTotalNonAktif) {
    	/*
    	 * FUNGSI INI MENCARI NILAI TOTAL SKS UNTUK TRAKM ROBOT PADA TARGET THSMS (DALAM HAL INI TERHITUNG DARI SAMWL + LAMA STUDI)
    	 */
    	ListIterator li = null,li1=null;
    	StringTokenizer st =null;
    	try {
    		SearchDbTrnlp sdt = new SearchDbTrnlp();
    		SearchDbKrklm sdk = new SearchDbKrklm();
    		if(v_dbInfoMhs_getTotSmsLamaStudiDariSmawlDanTotalNonAktif!=null && v_dbInfoMhs_getTotSmsLamaStudiDariSmawlDanTotalNonAktif.size()>0) {
    			li = v_dbInfoMhs_getTotSmsLamaStudiDariSmawlDanTotalNonAktif.listIterator();
    			while(li.hasNext()) {
    				int skstt_trakm = 0;
    				String brs = (String)li.next();
    				st = new StringTokenizer(brs,"`");
    				String npmhs = st.nextToken();
    				String smawl = st.nextToken();
    				String stpid = st.nextToken();
    				String idkur = st.nextToken();
    				String lama_studi = st.nextToken();
    				//untuk saat ini dianggap aktif semua
    				String sms_tidak_aktif = st.nextToken(); 
    				int tot_sks_penyetaraan = 0;
    				if(stpid.equalsIgnoreCase("P")) {
    					
    					tot_sks_penyetaraan = sdt.getTotSksdi(npmhs);
    				}
    				Vector v_info_krklm = sdk.getInfoKrklm(Integer.parseInt(idkur));
    				if(v_info_krklm!=null && v_info_krklm.size()>0) {
    					li1 = v_info_krklm.listIterator();
    					String info_krklm = (String)li1.next();
    					st = new StringTokenizer(info_krklm,"`");
    					idkur = st.nextToken();
    					String nmkur = st.nextToken();
    					String skstt = st.nextToken();
    					String smstt = st.nextToken();
    					//bila lama styudi > total sms krklm maka langsung saja total sks kurikulum
    					if(Integer.parseInt(lama_studi)>=Integer.parseInt(smstt)) {
    						skstt_trakm = Integer.parseInt(skstt);
    					}
    					else {
    						boolean lanjut = true;
    						skstt_trakm = tot_sks_penyetaraan;
    						while(li1.hasNext() && lanjut) {
        						String info_mk = (String)li1.next();
        						st = new StringTokenizer(info_mk,"`");
        						
        						String semes = st.nextToken();
        						String idkmk = st.nextToken();
        						String kdkmk = st.nextToken();
        						String skstm = st.nextToken();
        						String skspr = st.nextToken();
        						String skslp = st.nextToken();
        						String skslb = st.nextToken();
        						String sksim = st.nextToken();
        						String sksmk = st.nextToken();
        						String jenis = st.nextToken();
        						String final_mk = st.nextToken();
        						if(Integer.parseInt(semes)<=Integer.parseInt(lama_studi)) {
        							skstt_trakm = skstt_trakm+Integer.parseInt(sksmk);
        							if(skstt_trakm>=Integer.parseInt(skstt)) {
        								skstt_trakm = Integer.parseInt(skstt);
        								lanjut = false;
        							}
        							//System.out.println(info_mk);
        						}
        						else {
        							lanjut = false;
        						}
        					}
    					}
    					
    				}
    				//System.out.println("final = "+brs+"`"+tot_sks_penyetaraan+"`"+skstt_trakm);
    				//li.set(brs+"`"+tot_sks_penyetaraan+"`"+skstt_trakm);
    				//disingkat aja
    				li.set(npmhs+"`"+skstt_trakm);
    				skstt_trakm = 0;
    				tot_sks_penyetaraan = 0;
    			}
    		}
    	}
    	catch(Exception e){}
    	/*
    	catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
		finally {
			if (con!=null) {
				try {
					con.close();
				}
				catch (Exception ignore) {
					//System.out.println(ignore);
				}
			}
		}
		*/
    	return v_dbInfoMhs_getTotSmsLamaStudiDariSmawlDanTotalNonAktif;
    }
    
    public int sinkNlipkDanKasihNlipkRobotDiatasMinimalBilaNlipkDibawa(Vector v_npmhs, String thsms) {
    	int counter = 0;
    	Vector v = new Vector();
		ListIterator li = v.listIterator();
    	if(v_npmhs!=null) {
    		StringTokenizer st = null;
    		ListIterator linpm = v_npmhs.listIterator();
    		//hitung seluruh thsms
    		String tkn_thsms = null;
        	DecimalFormat df = new DecimalFormat("#.##");
        	int norut = 0;
        	try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    			con = ds.getConnection();
    			
    			while(linpm.hasNext()) {
    				norut++;
    				String npmhs = (String)linpm.next();
    				st = new StringTokenizer(npmhs,"`");
    				npmhs = st.nextToken();
    				String smawl = st.nextToken();
    				//System.out.println(norut+". hitung nilai semestare npm=="+npmhs);
    				//String thsms = null;
    				tkn_thsms = thsms;
    	        	String kdpst = Checker.getKdpst(npmhs);
    	        	String kdjen = Checker.getKdjen(kdpst);
    	        	String kdpti = Checker.getKdpti();
    	        	
    	        	
        			
        			stmt = con.prepareStatement("select * from TRAKM where THSMSTRAKM=? and NPMHSTRAKM=?");
        			if(!Checker.isStringNullOrEmpty(tkn_thsms)) {
        				st = new StringTokenizer(tkn_thsms,"`");
        				
        				while(st.hasMoreTokens()) {
        					thsms = st.nextToken();
        					//li.add(thsms);
        					stmt.setString(1, thsms);
        					stmt.setString(2, npmhs);
        					rs = stmt.executeQuery();
        					
        					while(rs.next()) {
        						int sksem = rs.getInt("SKSEMTRAKM");
        						double nlips = rs.getDouble("NLIPSTRAKM");
        						int skstt = rs.getInt("SKSTTTRAKM");
        						double nlipk = rs.getDouble("NLIPKTRAKM");
        						
        						int sksem_r = rs.getInt("SKSEMROBOT");
        						double nlips_r = rs.getDouble("NLIPSROBOT");
        						int skstt_r = rs.getInt("SKSTTROBOT");
        						double nlipk_r = rs.getDouble("NLIPKROBOT");
        						
    							if(smawl.equalsIgnoreCase(thsms)) {
    								skstt = sksem;
    								sksem_r= sksem;
    								skstt_r= sksem;
    								
    								if(nlips>2.85) {
    									nlips_r = nlips;
    									nlipk_r = nlips;
    									nlipk = nlips;
    								}
    							}
    							else {
    								if(nlipk>2.99) {
        								nlipk_r = nlipk;
        							}
    								else {
    									if(nlipk_r<3) {
    										int randomInt = randomNumberGenerator.nextInt(5);
    										if(randomInt==0) {
    											nlipk_r=3;	
    										}
    										else if(randomInt==1) {
    											nlipk_r=3.15;
    										}
    										else if(randomInt==2) {
    											nlipk_r=3.2;
    										}
    										else if(randomInt==3) {
    											nlipk_r=3.17;
    										}
    										else if(randomInt==4) {
    											nlipk_r=3.21;
    										}
    									}
    								}
    							}
    							li.add(npmhs+"`"+sksem+"`"+nlips+"`"+skstt+"`"+nlipk+"`"+sksem_r+"`"+nlips_r+"`"+skstt_r+"`"+nlipk_r);
        					}
        					
        				}
        			}
    			}//end while
    			
    			if(v!=null && v.size()>0) {
    				stmt = con.prepareStatement("UPDATE TRAKM set SKSEMTRAKM=?,NLIPSTRAKM=?,SKSTTTRAKM=?,NLIPKTRAKM=?,SKSEMROBOT=?,NLIPSROBOT=?,SKSTTROBOT=?,NLIPKROBOT=? where THSMSTRAKM=? and NPMHSTRAKM=?");
    				li = v.listIterator();
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					st = new StringTokenizer(brs,"`");
    					String npmhs = st.nextToken();
    					String sksem = st.nextToken();
    					String nlips = st.nextToken();
    					String skstt = st.nextToken();
    					String nlipk = st.nextToken();
    					String sksem_r = st.nextToken();
    					String nlips_r = st.nextToken();
    					String skstt_r = st.nextToken();
    					String nlipk_r = st.nextToken();
    					stmt.setInt(1, Integer.parseInt(sksem));
    					stmt.setDouble(2, Double.parseDouble(nlips));
    					stmt.setInt(3, Integer.parseInt(skstt));
    					stmt.setDouble(4, Double.parseDouble(nlipk));
    					stmt.setInt(5, Integer.parseInt(sksem_r));
    					stmt.setDouble(6, Double.parseDouble(nlips_r));
    					stmt.setInt(7, Integer.parseInt(skstt_r));
    					stmt.setDouble(8, Double.parseDouble(nlipk_r));
    					stmt.setString(9, thsms);
    					stmt.setString(10, npmhs);
    					int i = stmt.executeUpdate();
    					//System.out.println("-update- "+brs+"="+i);
    					counter = counter +i;
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
    	return counter;
    }
    
}

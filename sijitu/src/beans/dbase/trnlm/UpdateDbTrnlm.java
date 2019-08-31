package beans.dbase.trnlm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import beans.dbase.UpdateDb;
import beans.dbase.krklm.SearchDbKrklm;
import beans.dbase.makul.SearchDbMk;
import beans.dbase.tbbnl.SearchDbTbbnl;
import beans.setting.Constants;
import beans.tools.AddHocFunction;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.Tool;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;

import org.apache.axis.utils.StringUtils;
import org.apache.tomcat.jdbc.pool.DataSource;




/**
 * Session Bean implementation class UpdateDbTrnlm
 */
@Stateless
@LocalBean
public class UpdateDbTrnlm extends UpdateDb {
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
     * @see UpdateDb#UpdateDb()
     */
    public UpdateDbTrnlm() {
        super();
        randomNumberGenerator = new Random();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbTrnlm(String operatorNpm) {
        super(operatorNpm);
        randomNumberGenerator = new Random();
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    
    
    public boolean insertKrsNonClasspoll(String[]info_kelas, String npmhs, String thsms, String kdpst) {
    	//System.out.println("target cuid = "+cuid);
    	/*
    	 * kalo update non thsms krs maka set nlakh by dosen = true
    	 */
    	
    	boolean updated = false;
    	String kdjen = Checker.getKdjen(kdpst);
    	String kdpti = Constants.getKdpti();
    	//<%=cuid_awal+"`"+kdpst_+"`"+shift_+"`"+nopll_+"`"+npmdos_+"`"+nodos_+"`"+npmasdos_+"`"+noasdos_+"`"+kmp_+"`"+nmmdos_+"`"+nmmasdos_+"`"+sub_keter_kdkmk_+"`"+cuid%>
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
    		stmt.setString(1,thsms);
    		stmt.setString(2,npmhs);
    		stmt.executeUpdate();
    		
    		stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM=? and NPMHSTRAKM=?");
    		stmt.setString(1,thsms);
    		stmt.setString(2,npmhs);
    		stmt.executeUpdate();
    		
    		//insert 
    		stmt=con.prepareStatement("insert into TRNLM(THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,IDKMKTRNLM,NLAKH_BY_DOSEN)values(?,?,?,?,?,?,?,?,?,?,?)");
    		for(int i = 0; i< info_kelas.length;i++) {
    			StringTokenizer st = new StringTokenizer(info_kelas[i],"`");
    			if(st.countTokens()==5) {
    				int j=1;
    				String nlakh = st.nextToken();
        			String bobot = st.nextToken();
        			String idkmk = st.nextToken();
        			String kdkmk = st.nextToken();
        			String skstt = st.nextToken();
        			if(!nlakh.equalsIgnoreCase("T")) {
        				//harus sdh punya nilai akhirnya karena ini krs lama
        				stmt.setString(j++, thsms);
            			stmt.setString(j++, kdpti);
            			stmt.setString(j++, kdjen);
            			stmt.setString(j++, kdpst);
            			stmt.setString(j++, npmhs);
            			stmt.setString(j++, kdkmk);
            			stmt.setString(j++, nlakh);
            			stmt.setFloat(j++, Float.parseFloat(bobot));
            			stmt.setInt(j++, Integer.parseInt(skstt));
            			stmt.setInt(j++, Integer.parseInt(idkmk));
            			stmt.setBoolean(j++, true);
            			j=0;
            			j = stmt.executeUpdate();
            			if(j>0) {
            				updated = true;
            			}
        			}
        			
    			}	
    		}
    		if(updated) {
    			stmt = con.prepareStatement("INSERT into TRAKM(THSMSTRAKM,KDPTITRAKM,KDJENTRAKM,KDPSTTRAKM,NPMHSTRAKM,SKSEMTRAKM,NLIPSTRAKM,SKSTTTRAKM,NLIPKTRAKM)values(?,?,?,?,?,?,?,?,?)");
        		
    			
    			stmt.setString(1, thsms);
        		stmt.setString(2, kdpti);
        		stmt.setString(3,kdjen);
        		stmt.setString(4, kdpst);
        		stmt.setString(5, npmhs);
        		stmt.setInt(6, 0);
        		stmt.setDouble(7, 0);
        		stmt.setInt(8, 0);
        		stmt.setDouble(9, 0);
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
    	return updated;
    }

    /*
     * depricated
     */
    public void insertKrs(Vector vContinuSistemAdjustment,String npmhs, String kdpst) {
    	//linfo.add(TargetThsmsKrs+","+idkmk[i]+","+npm+","+kdpst+","+obj_lvl);
    	/*
    	 * fungsi ini menghapus prev record di trnlm baru kemudian insert yg baru
    	 */
    	if(vContinuSistemAdjustment!=null && vContinuSistemAdjustment.size()>0) {
    		try {
    			String kdjen = Checker.getKdjen(kdpst);
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		//get default shift
        		stmt = con.prepareStatement("select SHIFTMSMHS from CIVITAS where NPMHSMSMHS=?");
        		stmt.setString(1, npmhs);
        		rs = stmt.executeQuery();
        		rs.next();
        		String shiftKelas = rs.getString("SHIFTMSMHS");
        		if(shiftKelas==null) {
        			shiftKelas="N/A";
        		}
        		
        		int k =1;									   
        		Vector vDistincThsms = new Vector();
        		ListIterator lid = vDistincThsms.listIterator();
        		stmt = con.prepareStatement("insert ignore into TRNLM(THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,KELASTRNLM,SHIFTTRNLM,IDKMKTRNLM)values(?,?,?,?,?,?,?,?,?,?,?,?) ");
        		ListIterator litmp = vContinuSistemAdjustment.listIterator();
        		while(litmp.hasNext()) {
        			//System.out.println("pre-insert");
        			String thsms = (String)litmp.next();
        			if(!Checker.isStringNullOrEmpty(thsms)) {
        				lid.add(thsms);	
        			}
        			String kdkmk = (String)litmp.next();
        			String nakmkTrnlm = (String)litmp.next();
        			
        			String nlakhTrnlm = (String)litmp.next();
        			String bobotTrnlm = (String)litmp.next();
        			String sksmk = (String)litmp.next();
        			String noKlsPll = (String)litmp.next();
        			String sksemTrnlm = (String)litmp.next();
        			String nlipsTrnlm = (String)litmp.next();
        			String sksttTrnlm = (String)litmp.next();
        			String nlipkTrnlm = (String)litmp.next();	
        			String shiftTrnlm = (String)litmp.next();	
        			String krsdownTrnlm = (String)litmp.next();//tambahan baru
        			String khsdownTrnlm = (String)litmp.next();//tambahan baru
        			String bakproveTrnlm = (String)litmp.next();//tambahan baru
        			String paproveTrnlm = (String)litmp.next();//tambahan baru
        			String noteTrnlm = (String)litmp.next();//tambahan baru
        			String lockTrnlm = (String)litmp.next();//tambahan baru
        			String baukproveTrnlm = (String)litmp.next();//tambahan baru
					//tambahan
        			String idkmk = (String)litmp.next();
        			String addReqTrnlm = (String)litmp.next();
        			String drpReqTrnlm = (String)litmp.next();
        			String npmPaTrnlm = (String)litmp.next();
        			String npmBakTrnlm = (String)litmp.next();
        			String npmBaaTrnlm = (String)litmp.next();
        			String npmBaukTrnlm = (String)litmp.next();
        			String baaProveTrnlm = (String)litmp.next();
        			String ktuProveTrnlm = (String)litmp.next();
        			String dknProveTrnlm = (String)litmp.next();
        			String npmKtuTrnlm = (String)litmp.next();
        			String npmDekanTrnlm = (String)litmp.next();
        			String lockMhsTrnlm = (String)litmp.next();
        			String kodeKampusTrnlm = (String)litmp.next();
        			String cuid_trnlm = (String)litmp.next();
					String cuid_init_trnlm = (String)litmp.next();
					String npmdos_trnlm = (String)litmp.next();
					String nodos_trnlm = (String)litmp.next();
					String npmasdos_trnlm = (String)litmp.next();
					String noasdos_trnlm = (String)litmp.next();
					String nmmdos_trnlm = (String)litmp.next();
					String nmmasdos_trnlm = (String)litmp.next(); 
        			//System.out.println(k+++"."+thsms+"-"+kdkmk+"-"+idkmk);
        			
        			
        			//System.out.println("step1");
        			//thsms = (String)li2.next();
        			//String idkmk = (String)li2.next();
        			//npmhs = (String)li2.next();
        			//kdpst = (String)li2.next();
        			//String objLvl = (String)li2.next();
        			//String kdkmk = (String)li2.next();
        			//String sksmk = (String)li2.next();
        			//String noKlsPll = (String)li2.next();
        			if(noKlsPll==null || Checker.isStringNullOrEmpty(noKlsPll)) {
        				noKlsPll="00";
        			}
        			//String currStatus = (String)li2.next();
        			//String npmdos = (String)li2.next();
        			//String npmasdos = (String)li2.next();
        			//1THSMSTRNLM,2KDPTITRNLM,3KDJENTRNLM,4KDPSTTRNLM,5NPMHSTRNLM,6KDKMKTRNLM,7NLAKHTRNLM,8BOBOTTRNLM,9SKSMKTRNLM,10KELASTRNLM,11SHIFTTRNLM
        			stmt.setString(1,thsms);
        			stmt.setString(2,Constants.getKdpti());
        			//stmt.setNull(3,java.sql.Types.VARCHAR);
        			stmt.setString(3,kdjen.toUpperCase());
        			stmt.setString(4,kdpst);
        			stmt.setString(5,npmhs);
        			stmt.setString(6,kdkmk);
        			stmt.setString(7,"T");
        			stmt.setDouble(8,0);
        			if(Checker.isStringNullOrEmpty(sksmk)) {
        				sksmk = "0";
        			}
        			stmt.setInt(9,Integer.valueOf(sksmk).intValue());
        			stmt.setString(10,noKlsPll);
        			stmt.setString(11, shiftKelas);
        			if(idkmk!=null && idkmk.equalsIgnoreCase("0")) {
        				stmt.setNull(12, java.sql.Types.INTEGER);
        			}
        			else {
        				if(Checker.isStringNullOrEmpty(idkmk) || idkmk.equalsIgnoreCase("N/A")) {
        					stmt.setNull(12, java.sql.Types.INTEGER);
        				}
        				else {
        					stmt.setInt(12,Integer.valueOf(idkmk).intValue());
        				}
        			}	
        			//System.out.println("insert thsms/kdkmk = "+thsms+"/"+kdkmk);
        			//if(!Checker.isStringNullOrEmpty(kdkmk)) {
        				int o = stmt.executeUpdate();	
        			//}
        			//System.out.println("step1b = "+o);
        		}
        		//System.out.println("step2");
        		vDistincThsms = Tool.removeDuplicateFromVector(vDistincThsms);
        		lid = vDistincThsms.listIterator();
        		stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM=? and KDPSTTRAKM=? and NPMHSTRAKM=?");
        		while(lid.hasNext()) {
        			String thsms = (String)lid.next();
        			stmt.setString(1, thsms);
            		stmt.setString(2, kdpst);
            		stmt.setString(3, npmhs);
            		stmt.executeUpdate();
        		}
        		//harus insert trakm juga
        		//delete prev record
        		
        		//System.out.println("step3");
        		//insert fresh record
        		lid = vDistincThsms.listIterator();
        		stmt = con.prepareStatement("INSERT into TRAKM(THSMSTRAKM,KDPTITRAKM,KDJENTRAKM,KDPSTTRAKM,NPMHSTRAKM,SKSEMTRAKM,NLIPSTRAKM,SKSTTTRAKM,NLIPKTRAKM)values(?,?,?,?,?,?,?,?,?)");
        		while(lid.hasNext()) {
        			String thsms = (String)lid.next();
        			stmt.setString(1, thsms);
            		stmt.setString(2, Constants.getKdpti());
            		stmt.setNull(3,java.sql.Types.VARCHAR);
            		stmt.setString(4, kdpst);
            		stmt.setString(5, npmhs);
            		stmt.setInt(6, 0);
            		stmt.setDouble(7, 0);
            		stmt.setInt(8, 0);
            		stmt.setDouble(9, 0);
            		stmt.executeUpdate();
        		}
        		
        		//System.out.println("step4");
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
    
    public int updateNilaiDanKrsPascaBasedOnExcel(Vector v_riwayat_excel) {
    	int updated = 0;
    	String target_kdpst = "";
    	String target_kdjen = "";
		String target_thsms = "";
    	String kdpti = Constants.getKdpti();
    	String target_npmhs = "";
    	if(v_riwayat_excel!=null && v_riwayat_excel.size()>0) {
    		ListIterator li = v_riwayat_excel.listIterator();
    		//filter untuk krsdoang (krn v_hist ada data trlsm juga)
    		boolean first_row_title = true;
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			if(first_row_title) {
    				//hapus header
    				first_row_title = false;
    				li.remove();
    			}
    			else {
    				if(brs.endsWith("`null`null`null")) {
    					StringTokenizer st = new StringTokenizer(brs,"`");
            			target_thsms = st.nextToken();
            			target_kdpst = st.nextToken();
            			target_npmhs = st.nextToken();
        				li.remove();
        			}
    				else {
    					StringTokenizer st = new StringTokenizer(brs,"`");
            			target_thsms = st.nextToken();
            			target_kdpst = st.nextToken();
            			target_npmhs = st.nextToken();
    				}
    				
    			}
    			
    		}
    		target_kdjen = Checker.getKdjen(target_kdpst);
    		if(v_riwayat_excel!=null && v_riwayat_excel.size()>0) {
    			SearchDbTbbnl udb = new SearchDbTbbnl();
    			try {
        			Context initContext  = new InitialContext();
            		Context envContext  = (Context)initContext.lookup("java:/comp/env");
            		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
            		con = ds.getConnection();
            		Vector v_riwayat_yg_ada_disistem_skrg = SearchDbTrnlm.getStandarRiwayatKrsMhs(target_npmhs,con);
            		li = v_riwayat_excel.listIterator();
            		while(li.hasNext()) {
            			String brs = (String)li.next();
            			StringTokenizer st = new StringTokenizer(brs,"`");
            			target_thsms = st.nextToken();
            			target_kdpst = st.nextToken();
            			target_npmhs = st.nextToken();
            			String kdkmk = st.nextToken();
            			String nlakh = st.nextToken();
            			String bobot = st.nextToken();
            			String sks = st.nextToken();
            			String nlakh_nilai_bobot = SearchDbTbbnl.getNlakhDanNilaiDanBobotNilaiYgBerlaku(target_thsms, target_kdpst, nlakh, con);
            			String tmp = target_thsms+"`"+target_kdpst+"`"+target_npmhs+"`"+kdkmk+"`"+nlakh_nilai_bobot+"`"+sks; //nilai_bobot = nilai`bobot
            			li.set(tmp);
            		}	
            		//String kdjen = Checker.getKdjen(target_kdpst,con);
            		//bandingkan dgn krs yg ada sekarang, hapus mk yg tidak ada di list atas
            		//stmt = con.prepareStatement(sql)
            		Vector v_del = null;
            		ListIterator lid = null;
            		//Vector v_riwayat_yg_ada_disistem_skrg = SearchDbTrnlm.getStandarRiwayatKrsMhs(target_npmhs,con);
            		if(v_riwayat_yg_ada_disistem_skrg!=null && v_riwayat_yg_ada_disistem_skrg.size()>0) {
            			ListIterator li_tmp = v_riwayat_yg_ada_disistem_skrg.listIterator();
            			while(li_tmp.hasNext()) {
            				String old_hist = (String)li_tmp.next();
            				//li.add(thsms+"`"+kdpst+"`"+npmhs+"`"+kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk);
            				li = v_riwayat_excel.listIterator();
            				boolean match = false;
                    		while(li.hasNext() && !match) {
                    			String brs = (String)li.next();
                    			StringTokenizer st = new StringTokenizer(brs,"`");
                    			target_thsms = st.nextToken();
                    			target_kdpst = st.nextToken();
                    			target_npmhs = st.nextToken();
                    			String kdkmk = st.nextToken();
                    			//String nlakh = st.nextToken();
                    			//String bobot = st.nextToken();
                    			//String sks = st.nextToken();
                    			//String kdjen = Checker.getKdjen(target_kdpst,con);
                    			if(old_hist.startsWith(target_thsms+"`") &&  (old_hist.contains("`"+kdkmk+"`")||old_hist.contains("`"+kdkmk.toUpperCase()+"`")||old_hist.contains("`"+kdkmk.toLowerCase()+"`"))) {
                    				match = true;
                    			}
                    			if(!li.hasNext() && !match) {
                    				if(v_del==null) {
                    					v_del = new Vector();
                    					lid = v_del.listIterator();
                    				}
                    				lid.add(old_hist);
                    			}
                    		}	
                    	}
            		}
            		
            		
            		//delete yg ngga ada di excel
            		if(v_del!=null && v_del.size()>0) {
                		stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=?");
            			lid = v_del.listIterator();
            			while(lid.hasNext()) {
            				String hapus = (String)lid.next();
            				//20172`61101`6110117100001`DP08`0.0`T`0.0`3
            				StringTokenizer st = new StringTokenizer(hapus,"`");
            				String thsms = st.nextToken();
            				String kdpst = st.nextToken();
            				String npmhs = st.nextToken();
            				String kdkmk = st.nextToken();
            				stmt.setString(1, thsms);
            				stmt.setString(2, npmhs);
            				stmt.setString(3, kdkmk);
            				updated = stmt.executeUpdate();
            			}
            		}
            		
            		if(v_riwayat_excel!=null && v_riwayat_excel.size()>0) {
            			Vector v_ins = null;
            			ListIterator lins = null;
            			li = v_riwayat_excel.listIterator();
            			//coba update nilai
            			stmt = con.prepareStatement("update TRNLM set NILAITRNLM=?,NLAKHTRNLM=?,BOBOTTRNLM=? where THSMSTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=?");
                		while(li.hasNext()) {
                			String brs = (String)li.next();
                			StringTokenizer st = new StringTokenizer(brs,"`");
                			target_thsms = st.nextToken();
                			target_kdpst = st.nextToken();
                			target_npmhs = st.nextToken();
                			String kdkmk = st.nextToken();
                			String nlakh = st.nextToken();
                			String nilai = st.nextToken();
                			String bobot = st.nextToken();
                			String sks = st.nextToken();
                			stmt.setDouble(1, Double.parseDouble(nilai));
                			stmt.setString(2, nlakh);
                			stmt.setDouble(3, Double.parseDouble(bobot));
                			stmt.setString(4, target_thsms);
                			stmt.setString(5, target_npmhs);
                			stmt.setString(6, kdkmk);
                			updated = 0;
                			updated = stmt.executeUpdate();
                			if(updated<=0) {
                				if(v_ins==null) {
                					v_ins = new Vector();
                					lins = v_ins.listIterator();
                				}
                				lins.add(brs);
                			}
                		}
                		
                		if(v_ins!=null && v_ins.size()>0) {
                			//stmt = con.prepareStatement("insert into TRNLM(THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,SHIFTTRNLM,IDKMKTRNLM)values(?,?,?,?,?,?,?,?,?,?,?,?)");
                			//get idkmk
                			stmt = con.prepareStatement("select IDKMKMAKUL from MAKUL where KDPSTMAKUL=? and KDKMKMAKUL=?");
                			lins = v_ins.listIterator();
                			while(lins.hasNext()) {
                    			String brs = (String)lins.next();
                    			StringTokenizer st = new StringTokenizer(brs,"`");
                    			target_thsms = st.nextToken();
                    			target_kdpst = st.nextToken();
                    			target_npmhs = st.nextToken();
                    			String kdkmk = st.nextToken();
                    			String nlakh = st.nextToken();
                    			String nilai = st.nextToken();
                    			String bobot = st.nextToken();
                    			String sks = st.nextToken();
                    			stmt.setString(1, target_kdpst);
                    			stmt.setString(2, kdkmk);
                    			rs = stmt.executeQuery();
                    			rs.next();
                    			brs = brs+"`"+rs.getLong(1);
                    			lins.set(brs);
                			}	
                			
                			stmt = con.prepareStatement("insert into TRNLM(THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,IDKMKTRNLM)values(?,?,?,?,?,?,?,?,?,?,?)");
                			lins = v_ins.listIterator();
                			while(lins.hasNext()) {
                    			String brs = (String)lins.next();
                    			StringTokenizer st = new StringTokenizer(brs,"`");
                    			target_thsms = st.nextToken();
                    			target_kdpst = st.nextToken();
                    			target_npmhs = st.nextToken();
                    			String kdkmk = st.nextToken();
                    			String nlakh = st.nextToken();
                    			String nilai = st.nextToken();
                    			String bobot = st.nextToken();
                    			String sks = st.nextToken();
                    			String idkmk = st.nextToken();
                    			int i = 1;
                    			//THSMSTRNLM,
                    			stmt.setString(i++, target_thsms);
                    			//KDPTITRNLM,
                    			stmt.setString(i++, kdpti);
                    			//KDJENTRNLM,
                    			stmt.setString(i++, target_kdjen);
                    			//KDPSTTRNLM,
                    			stmt.setString(i++, target_kdpst);
                    			//NPMHSTRNLM,
                    			stmt.setString(i++, target_npmhs);
                    			//KDKMKTRNLM,
                    			stmt.setString(i++, kdkmk);
                    			//NILAITRNLM,
                    			stmt.setDouble(i++, Double.parseDouble(nilai));
                    			//NLAKHTRNLM,
                    			stmt.setString(i++, nlakh);
                    			//BOBOTTRNLM,
                    			stmt.setDouble(i++, Double.parseDouble(bobot));
                    			//SKSMKTRNLM,
                    			stmt.setInt(i++, Integer.parseInt(sks));
                    			//SHIFTTRNLM,
                    			//stmt.setString(i++, x);
                    			//IDKMKTRNLM
                    			stmt.setLong(i++, Long.parseLong(idkmk));
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
    		
    		
    	}
    	return updated;
    }
    
    
    public void insertKrs_v1(Vector vContinuSistemAdjustment,String npmhs, String kdpst) {
    	//linfo.add(TargetThsmsKrs+","+idkmk[i]+","+npm+","+kdpst+","+obj_lvl);
    	/*
    	 * fungsi ini menghapus prev record di trnlm baru kemudian insert yg baru
    	 * TIDAK BOLEH DIGUNAKAN UNTUK CONTINUOUS KRN NILAI KEHAPUS
    	 */
    	if(vContinuSistemAdjustment!=null && vContinuSistemAdjustment.size()>0) {
    		try {
    			String kdjen = Checker.getKdjen(kdpst);
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		//get default shift
        		stmt = con.prepareStatement("select SHIFTMSMHS from CIVITAS where NPMHSMSMHS=?");
        		stmt.setString(1, npmhs);
        		rs = stmt.executeQuery();
        		rs.next();
        		String shiftKelas = rs.getString("SHIFTMSMHS");
        		if(shiftKelas==null) {
        			shiftKelas="N/A";
        		}
        		
        											   
        		Vector vDistincThsms = new Vector();
        		ListIterator lid = vDistincThsms.listIterator();
        		stmt = con.prepareStatement("insert into TRNLM(THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,KELASTRNLM,SHIFTTRNLM,IDKMKTRNLM)values(?,?,?,?,?,?,?,?,?,?,?,?) ");
        		ListIterator litmp = vContinuSistemAdjustment.listIterator();
        		while(litmp.hasNext()) {
        			//System.out.println("pre-insert");
        			String thsms = (String)litmp.next();
        			lid.add(thsms);
        			String kdkmk = (String)litmp.next();
        			String nakmkTrnlm = (String)litmp.next();
        			String nlakhTrnlm = (String)litmp.next();
        			String bobotTrnlm = (String)litmp.next();
        			String sksmk = (String)litmp.next();
        			String noKlsPll = (String)litmp.next();
        			String sksemTrnlm = (String)litmp.next();
        			String nlipsTrnlm = (String)litmp.next();
        			String sksttTrnlm = (String)litmp.next();
        			String nlipkTrnlm = (String)litmp.next();	
        			String shiftTrnlm = (String)litmp.next();	
        			String krsdownTrnlm = (String)litmp.next();//tambahan baru
        			String khsdownTrnlm = (String)litmp.next();//tambahan baru
        			String bakproveTrnlm = (String)litmp.next();//tambahan baru
        			String paproveTrnlm = (String)litmp.next();//tambahan baru
        			String noteTrnlm = (String)litmp.next();//tambahan baru
        			String lockTrnlm = (String)litmp.next();//tambahan baru
        			String baukproveTrnlm = (String)litmp.next();//tambahan baru
					//tambahan
        			String idkmk = (String)litmp.next();
        			String addReqTrnlm = (String)litmp.next();
        			String drpReqTrnlm = (String)litmp.next();
        			String npmPaTrnlm = (String)litmp.next();
        			String npmBakTrnlm = (String)litmp.next();
        			String npmBaaTrnlm = (String)litmp.next();
        			String npmBaukTrnlm = (String)litmp.next();
        			String baaProveTrnlm = (String)litmp.next();
        			String ktuProveTrnlm = (String)litmp.next();
        			String dknProveTrnlm = (String)litmp.next();
        			String npmKtuTrnlm = (String)litmp.next();
        			String npmDekanTrnlm = (String)litmp.next();
        			String lockMhsTrnlm = (String)litmp.next();
        			String kodeKampusTrnlm = (String)litmp.next();
        			
        			
        			
        			
        			
        			//thsms = (String)li2.next();
        			//String idkmk = (String)li2.next();
        			//npmhs = (String)li2.next();
        			//kdpst = (String)li2.next();
        			//String objLvl = (String)li2.next();
        			//String kdkmk = (String)li2.next();
        			//String sksmk = (String)li2.next();
        			//String noKlsPll = (String)li2.next();
        			if(noKlsPll==null || Checker.isStringNullOrEmpty(noKlsPll)) {
        				noKlsPll="00";
        			}
        			//String currStatus = (String)li2.next();
        			//String npmdos = (String)li2.next();
        			//String npmasdos = (String)li2.next();
        			//1THSMSTRNLM,2KDPTITRNLM,3KDJENTRNLM,4KDPSTTRNLM,5NPMHSTRNLM,6KDKMKTRNLM,7NLAKHTRNLM,8BOBOTTRNLM,9SKSMKTRNLM,10KELASTRNLM,11SHIFTTRNLM
        			stmt.setString(1,thsms);
        			stmt.setString(2,Constants.getKdpti());
        			//stmt.setNull(3,java.sql.Types.VARCHAR);
        			stmt.setString(3,kdjen.toUpperCase());
        			stmt.setString(4,kdpst);
        			stmt.setString(5,npmhs);
        			stmt.setString(6,kdkmk);
        			stmt.setString(7,"T");
        			stmt.setDouble(8,0);
        			stmt.setInt(9,Integer.valueOf(sksmk).intValue());
        			stmt.setString(10,noKlsPll);
        			stmt.setString(11, shiftKelas);
        			if(idkmk!=null && idkmk.equalsIgnoreCase("0")) {
        				stmt.setNull(12, java.sql.Types.INTEGER);
        			}
        			else {
        				if(Checker.isStringNullOrEmpty(idkmk)) {
        					stmt.setNull(12, java.sql.Types.INTEGER);
        				}
        				else {
        					stmt.setInt(12,Integer.valueOf(idkmk).intValue());
        				}
        			}	
        			//System.out.println("insert thsms/kdkmk = "+thsms+"/"+kdkmk);
        			stmt.executeUpdate();
        		}
        		
        		vDistincThsms = Tool.removeDuplicateFromVector(vDistincThsms);
        		lid = vDistincThsms.listIterator();
        		stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM=? and KDPSTTRAKM=? and NPMHSTRAKM=?");
        		while(lid.hasNext()) {
        			String thsms = (String)lid.next();
        			stmt.setString(1, thsms);
            		stmt.setString(2, kdpst);
            		stmt.setString(3, npmhs);
            		stmt.executeUpdate();
        		}
        		//harus insert trakm juga
        		//delete prev record
        		
        		
        		//insert fresh record
        		lid = vDistincThsms.listIterator();
        		stmt = con.prepareStatement("INSERT into TRAKM(THSMSTRAKM,KDPTITRAKM,KDJENTRAKM,KDPSTTRAKM,NPMHSTRAKM,SKSEMTRAKM,NLIPSTRAKM,SKSTTTRAKM,NLIPKTRAKM)values(?,?,?,?,?,?,?,?,?)");
        		while(lid.hasNext()) {
        			String thsms = (String)lid.next();
        			stmt.setString(1, thsms);
            		stmt.setString(2, Constants.getKdpti());
            		stmt.setNull(3,java.sql.Types.VARCHAR);
            		stmt.setString(4, kdpst);
            		stmt.setString(5, npmhs);
            		stmt.setInt(6, 0);
            		stmt.setDouble(7, 0);
            		stmt.setInt(8, 0);
            		stmt.setDouble(9, 0);
            		stmt.executeUpdate();
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

    
    public void deleteKrs(Vector vGetRiwayatTrlsm, String npmhs) {
    	if(vGetRiwayatTrlsm!=null && vGetRiwayatTrlsm.size()>0) {
    		try {
        		//String ipAddr =  request.getRemoteAddr();
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//stmt = con.prepareStatement("delete from TRAKM where NPMHSTRAKM=?");
        		//stmt.setString(1,npmhs);
        		//stmt.executeUpdate();
        		
        		//delete @ thsmstrlsm
        		stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM=? and NPMHSTRAKM=?");
    			ListIterator litmp = vGetRiwayatTrlsm.listIterator();
    			
    			boolean done = false;
    			while(litmp.hasNext() && !done) {
    				String brs = (String)litmp.next();
    				//System.out.println("baris vTrlsm="+brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String thsmsTrlms = st.nextToken();
    				String stmhsTrlms = st.nextToken();
    				if(stmhsTrlms.equalsIgnoreCase("C")||stmhsTrlms.equalsIgnoreCase("N")) {
    					stmt.setString(1, thsmsTrlms);
    					stmt.setString(2, npmhs);
    					stmt.executeUpdate();
    				}
    				else if(stmhsTrlms.equalsIgnoreCase("K")||stmhsTrlms.equalsIgnoreCase("D")||stmhsTrlms.equalsIgnoreCase("P")){ //P=pindah jurusan
    					stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM>=? and NPMHSTRAKM=?");
    					stmt.setString(1, thsmsTrlms);
    					stmt.setString(2, npmhs);
    					int i = stmt.executeUpdate();
    					//System.out.println("delete TRNLM "+thsmsTrlms+" - "+npmhs+" = "+i);
    					done = true;
    				}
    				else if(stmhsTrlms.equalsIgnoreCase("L")){ 
    					stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM>? and NPMHSTRAKM=?");
    					stmt.setString(1, thsmsTrlms);
    					stmt.setString(2, npmhs);
    					int i = stmt.executeUpdate();
    					//System.out.println("delete TRNLM "+thsmsTrlms+" - "+npmhs+" = "+i);
    					done = true;
    				}
    			}
    			
    			stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
    			litmp = vGetRiwayatTrlsm.listIterator();
    			
    			done = false;
    			while(litmp.hasNext() && !done) {
    				String brs = (String)litmp.next();
    				//System.out.println("baris vTrlsm="+brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String thsmsTrlms = st.nextToken();
    				String stmhsTrlms = st.nextToken();
    				if(stmhsTrlms.equalsIgnoreCase("C")||stmhsTrlms.equalsIgnoreCase("N")) {
    					stmt.setString(1, thsmsTrlms);
    					stmt.setString(2, npmhs);
    					stmt.executeUpdate();
    				}
    				else if(stmhsTrlms.equalsIgnoreCase("K")||stmhsTrlms.equalsIgnoreCase("D")||stmhsTrlms.equalsIgnoreCase("P")){ //P=pindah jurusan
    					stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM>=? and NPMHSTRNLM=?");
    					stmt.setString(1, thsmsTrlms);
    					stmt.setString(2, npmhs);
    					int i = stmt.executeUpdate();
    					//hapus juga riwayat trlsm pasca keluar
    					stmt = con.prepareStatement("delete from TRLSM where THSMS>? and NPMHS=?");
    					stmt.setString(1, thsmsTrlms);
    					stmt.setString(2, npmhs);
    					i = stmt.executeUpdate();
    					done = true;
    				}
    				else if(stmhsTrlms.equalsIgnoreCase("L")){ 
    					stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM>? and NPMHSTRNLM=?");
    					stmt.setString(1, thsmsTrlms);
    					stmt.setString(2, npmhs);
    					int i = stmt.executeUpdate();
    					//hapus juga riwayat trlsm pasca keluar
    					stmt = con.prepareStatement("delete from TRLSM where THSMS>? and NPMHS=?");
    					stmt.setString(1, thsmsTrlms);
    					stmt.setString(2, npmhs);
    					i = stmt.executeUpdate();
    					done = true;
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
    
    /*
     * !!!!
     * HARUSNYA IDKMK tidak dibutuhkan karena sudah pake CUID
     * kasih nilai minus saja
     */
    public void updateNilaiPerKelas(String thsms, String kdkmk, int idkmk, String[] nilai_value, String npmdos, String saya_dosennya, String cuid, String[]kdpst, String[]npmhs) {
    	//System.out.println("nilai_value  = "+nilai_value.length);
    	//System.out.println("kdpst  = "+kdpst.length);
    	//System.out.println("npmhs  = "+npmhs.length);
    	boolean syDsnNya = false;
    	//Vector v_nilai = new Vector();
    	//ListIterator li = v_nilai.listIterator();
    	Vector v_tmp = null;
    	ListIterator lit = null;
    	if(saya_dosennya!=null && saya_dosennya.equalsIgnoreCase("true")) {
    		syDsnNya = true;
    	}
    	try {
    		
    		if(kdpst!=null && kdpst.length>0) {
    			for(int i=0;i<kdpst.length;i++) {
    				//nilai_huruf+"`"+bobot+"`"+min+"`"+max
    				v_tmp = Getter.getAngkaPenilaian_v1(thsms, kdpst[i]);
    				lit = v_tmp.listIterator();
    				String brs = new String(kdpst[i]);
    				while(lit.hasNext()) {
    					brs = brs +"`"+(String)lit.next();
    				}
    				//System.out.println("bar="+brs);
    				//li.add(brs);
    				kdpst[i]=new String(brs);
    			}
    		}
    		
    		
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//if(syDsnNya) {
    		if(Checker.isStringNullOrEmpty(cuid)) {
    			stmt = con.prepareStatement("update TRNLM set NILAITRNLM=?,NLAKHTRNLM=?,BOBOTTRNLM=?,NLAKH_BY_DOSEN=? where (THSMSTRNLM=? and NPMHSTRNLM=? and IDKMKTRNLM=?)");
    		}
    		else {
    			stmt = con.prepareStatement("update TRNLM set NILAITRNLM=?,NLAKHTRNLM=?,BOBOTTRNLM=?,NLAKH_BY_DOSEN=? where (THSMSTRNLM=? and NPMHSTRNLM=? and IDKMKTRNLM=?) or (CLASS_POOL_UNIQUE_ID=? and NPMHSTRNLM=?)");	
    		}
    		
    		if(nilai_value!=null && nilai_value.length>0) {
    			for(int i=0;i<nilai_value.length;i++) {
    				String nlakh = "T";
					double bobot_akhir = 0;
					double nilai_akhir = 0;
    				//if(StringUtils.isNotEmpty(nilai_value[i]) && StringUtils.isNumeric(nilai_value[i])) {
    				if(org.apache.commons.lang3.StringUtils.isNotEmpty(nilai_value[i])) {
    					//System.out.println("nilai_value-"+i+" = "+nilai_value[i]);
    					StringTokenizer st = new StringTokenizer(kdpst[i],"`");
    					if(Tool.isDouble(nilai_value[i])) {
        					//System.out.println("is numeric");
        					nilai_value[i] = nilai_value[i].replace(",", ".");
        					st.nextToken();//ignore kdpst
        					boolean match = false;
        					while(st.hasMoreTokens() && !match) {
        						String nilai_huruf = st.nextToken();
        						String bobot = st.nextToken();
        						String min = st.nextToken();
        						String max = st.nextToken();
        						if((Double.parseDouble(nilai_value[i])>=Double.parseDouble(min))&&(Double.parseDouble(nilai_value[i])<=Double.parseDouble(max))) {
        							match = true;
        							bobot_akhir = Double.parseDouble(bobot);
        							nlakh = new String(nilai_huruf);
        							nilai_akhir = Double.parseDouble(nilai_value[i]);
        							//System.out.println("match="+nlakh+"-"+bobot_akhir);
        						}
        					}
        				}
    					else {
    						//System.out.println("is huruf");
    						st.nextToken();//ignore kdpst
        					boolean match = false;
        					while(st.hasMoreTokens() && !match) {
        						String nilai_huruf = st.nextToken();
        						String bobot = st.nextToken();
        						String min = st.nextToken();
        						String max = st.nextToken();
        						if(nilai_value[i].equalsIgnoreCase(nilai_huruf)) {
        							match = true;
        							bobot_akhir = Double.parseDouble(bobot);
        							nlakh = new String(nilai_huruf);
        							nilai_akhir = Double.parseDouble(max);
        						}
        					}
    					}
    				}
    				;
    				stmt.setDouble(1, nilai_akhir);
    				stmt.setString(2, nlakh);
    				stmt.setDouble(3, bobot_akhir);
    				stmt.setBoolean(4, syDsnNya);
    				stmt.setString(5, thsms);
    				stmt.setString(6, npmhs[i]);
    				stmt.setInt(7, idkmk);
    				if(!Checker.isStringNullOrEmpty(cuid)) {
    					stmt.setLong(8, Long.parseLong(cuid));
    					stmt.setString(9, npmhs[i]);
    				}	
    				int upd = stmt.executeUpdate();
    				//System.out.println("nilai == "+nilai_akhir+","+nlakh+","+npmhs[i]+"="+upd);
    				
    			}
    		}
    		/*
    		if(npm_nlakh_bobot!=null && npm_nlakh_bobot.length>0) {
    			for(int i=0;i<npm_nlakh_bobot.length;i++) {
    				//System.out.print("ino nilai= "+i+"."+npm_nlakh_bobot[i]);
    				StringTokenizer st = new StringTokenizer(npm_nlakh_bobot[i],",");
    				String npmhs = st.nextToken();
    				String nlakh = st.nextToken();
    				String bobot = st.nextToken();
    				int j=1;
    				stmt.setString(1, nlakh);
    				stmt.setDouble(2, Double.parseDouble(bobot));
    				stmt.setBoolean(3, syDsnNya);
    				stmt.setString(4, thsms);
    				stmt.setString(5, npmhs);
    				stmt.setInt(6, idkmk);
    				if(!Checker.isStringNullOrEmpty(cuid)) {
    					stmt.setLong(7, Long.parseLong(cuid));
    					stmt.setString(8, npmhs);
    				}	
    				int l = stmt.executeUpdate();
    				//System.out.println(" > "+l);
    			}
    		}
    		*/	
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
    
    public void updateNilaiPerIndividu(String[]info, String target_npmhs) {
    	//System.out.println("target cuid = "+cuid);
    	//<%=prev_thsms+"`"+prev_idkmk+"`"+prev_kdkmk+"`"+nilai+"`"+bobot %>
    	if(info!=null && info.length>0) {
    		try {
        		//String ipAddr =  request.getRemoteAddr();
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//if(syDsnNya) {
        		stmt = con.prepareStatement("update TRNLM set NLAKHTRNLM=?,BOBOTTRNLM=? where THSMSTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=?");
        		//kita pake kdkmk krn idkmk ada emungkinan null
        		for(int i=0;i<info.length;i++) {
        			StringTokenizer st = new StringTokenizer(info[i],"`");
        			//System.out.println(info[i]);
        			String thsms = st.nextToken();
        			String idkmk = st.nextToken();
        			String kdkmk = st.nextToken();
        			String nilai = st.nextToken();
        			String bobot = st.nextToken();
        			stmt.setString(1, nilai);
        			stmt.setDouble(2, Double.parseDouble(bobot));
        			stmt.setString(3, thsms);
        			stmt.setString(4, target_npmhs);
        			stmt.setString(5, kdkmk);
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
    
    public void kelasKrsAdjustmen(String[]info_kelas, String npmhs) {
    	//System.out.println("target cuid = "+cuid);
    	//<%=cuid_awal+"`"+kdpst_+"`"+shift_+"`"+nopll_+"`"+npmdos_+"`"+nodos_+"`"+npmasdos_+"`"+noasdos_+"`"+kmp_+"`"+nmmdos_+"`"+nmmasdos_+"`"+sub_keter_kdkmk_+"`"+cuid%>
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update TRNLM set SHIFTTRNLM=?,CLASS_POOL_UNIQUE_ID=? where NPMHSTRNLM=? and CLASS_POOL_UNIQUE_ID=?");
    		for(int i = 0; i< info_kelas.length;i++) {
    			StringTokenizer st = new StringTokenizer(info_kelas[i],"`");
    			
    			String cuid_awal = st.nextToken();
    			String kdpst_ = st.nextToken();
    			String shift_ = st.nextToken();
    			String nopll_ = st.nextToken();
    			String npmdos_ = st.nextToken();
    			String nodos_ = st.nextToken();
    			String npmasdos_ = st.nextToken();
    			String noasdos_ = st.nextToken();
    			String kmp_ = st.nextToken();
    			String nmmdos_ = st.nextToken();
    			String nmmasdos_ = st.nextToken();
    			String sub_keter_kdkmk_ = st.nextToken();
    			String cuid_ = st.nextToken();
    			stmt.setString(1, shift_);
    			stmt.setLong(2, Long.parseLong(cuid_));
    			stmt.setString(3, npmhs);
    			stmt.setLong(4, Long.parseLong(cuid_awal));
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
    
    public void sinkCuidIdWithClassPoll(String target_thsms, String scope_cmd, Vector vscope_id) {
    	//System.out.println("target cuid = "+cuid);
    	//<%=cuid_awal+"`"+kdpst_+"`"+shift_+"`"+nopll_+"`"+npmdos_+"`"+nodos_+"`"+npmasdos_+"`"+noasdos_+"`"+kmp_+"`"+nmmdos_+"`"+nmmasdos_+"`"+sub_keter_kdkmk_+"`"+cuid%>
    	try {
    		
    		//String ipAddr =  request.getRemoteAddr();
    		Vector v1 = new Vector();
    		ListIterator li1 = v1.listIterator();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from CLASS_POOL where THSMS=? and KDPST=? and KODE_KAMPUS=? order by KODE_PENGGABUNGAN desc");
    		ListIterator li = vscope_id.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kd_kmp = st.nextToken();
    			while(st.hasMoreTokens()) {
    				String kdpst = st.nextToken();
    				stmt.setString(1, target_thsms);
    				stmt.setString(2, kdpst);
    				//stmt.setBoolean(3, false);
    				stmt.setString(3, kd_kmp);
    				rs = stmt.executeQuery();
    				//String master_cuid = "";
    				while(rs.next()) {
    					
    					String idkmk = ""+rs.getLong("IDKMK");
    					String shift = ""+rs.getString("SHIFT");
    					String nopll = ""+rs.getString("NORUT_KELAS_PARALEL");
    					String kode_gabung =  ""+rs.getString("KODE_PENGGABUNGAN");
    					String cuid = ""+rs.getLong("UNIQUE_ID");
    					String canceled = ""+rs.getBoolean("CANCELED");
    					String tmp = kode_gabung+"`"+canceled+"`"+kd_kmp+"`"+kdpst+"`"+idkmk+"`"+shift+"`"+nopll+"`"+cuid;
    					tmp = tmp.replace("true", "batal");
    					tmp = tmp.replace("false", "aktif");
    					li1.add(tmp);  					
    				}
    			}
    		}
    		Collections.sort(v1);
    		
    		//1.reset cuid trnlm to null
    		stmt = con.prepareStatement("UPDATE TRNLM set CLASS_POOL_UNIQUE_ID=?,CUID_INIT=? where THSMSTRNLM=?");
    		stmt.setNull(1, java.sql.Types.INTEGER);
    		stmt.setNull(2, java.sql.Types.INTEGER);
    		stmt.setString(3, target_thsms);
    		stmt.executeUpdate();
    		//update cuit
    		li1 = v1.listIterator();
    		String prev_kode_gab = "";
    		String master_cuid = "";
    		stmt = con.prepareStatement("update TRNLM set CLASS_POOL_UNIQUE_ID=?,CUID_INIT=? where THSMSTRNLM=? and KDPSTTRNLM=? and KODE_KAMPUS=? and SHIFTTRNLM=? and IDKMKTRNLM=?");
    		while(li1.hasNext()) {
    			String brs = (String)li1.next();
    			// kode_gabung+"`"+canceled+"`"+kd_kmp+"`"+kdpst+"`"+idkmk+"`"+shift+"`"+nopll+"`"+cuid
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kode_gabung=st.nextToken();
    			String canceled=st.nextToken();
    			String kd_kmp=st.nextToken();
    			String kdpst=st.nextToken();
    			String idkmk=st.nextToken();
    			String shift=st.nextToken();
    			String nopll=st.nextToken();
    			String cuid=st.nextToken();
    			if(kode_gabung.equalsIgnoreCase("null")) {
    				stmt.setLong(1, Long.parseLong(cuid));
    				stmt.setLong(2, Long.parseLong(cuid));
    			}
    			else {
    				if(Checker.isStringNullOrEmpty(prev_kode_gab)) {
    					prev_kode_gab = new String(kode_gabung);
    					master_cuid = new String(cuid);
    					stmt.setLong(1, Long.parseLong(cuid));
        				stmt.setLong(2, Long.parseLong(cuid));
    				}
    				else {
    					if(kode_gabung.equalsIgnoreCase(prev_kode_gab)) {
    						//kelas yg ditutup
    						stmt.setLong(1, Long.parseLong(master_cuid));
    	    				stmt.setLong(2, Long.parseLong(cuid));
    					}
    					else {
    						//kelas aktif baru
    						prev_kode_gab = new String(kode_gabung);
    						master_cuid = new String(cuid);
    						stmt.setLong(1, Long.parseLong(cuid));
            				stmt.setLong(2, Long.parseLong(cuid));
    					}
    				}
    			}
    			//THSMSTRNLM=? and KDPSTTRNLM=? and KODE_KAMPUS=? and SHIFTTRNLM=? and IDKMKTRNLM=?");
    			stmt.setString(3, target_thsms);
    			stmt.setString(4, kdpst);
    			stmt.setString(5, kd_kmp);
    			stmt.setString(6, shift);
    			stmt.setLong(7, Long.parseLong(idkmk));
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
    
    public void updateNilaiMalaikat(String target_thsms) {
    	Random randomGenerator = new Random();
    	
    	try {
    		Vector v = new Vector();
			ListIterator li = v.listIterator();
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//if(syDsnNya) {
    		stmt = con.prepareStatement("select NPMHSTRNLM,IDKMKTRNLM,KRKLMMSMHS from CIVITAS inner join TRNLM on NPMHSMSMHS=NPMHSTRNLM inner join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS where MALAIKAT=? and THSMSTRNLM=?");
    		stmt.setBoolean(1, true);
    		stmt.setString(2, target_thsms);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String npmhs = rs.getString("NPMHSTRNLM");
    			Long idkmk = rs.getLong("IDKMKTRNLM");
    			String krklm = rs.getString("KRKLMMSMHS");
    			int randomInt = randomGenerator.nextInt(10);
    			String nlakh = "A`4";
    			if(randomInt>6) {
    				nlakh = "B`3";	
    			}
    			li.add(npmhs+"`"+idkmk+"`"+krklm+"`"+nlakh);
    			
    		}
    		//kita pake kdkmk krn idkmk ada emungkinan null
    		stmt = con.prepareStatement("select FINAL_MK from MAKUR where IDKMKMAKUR=? and IDKURMAKUR=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String npmhs=st.nextToken();
    			String idkmk=st.nextToken();
    			String krklm=st.nextToken();
    			String nlakh=st.nextToken();
    			String bobot=st.nextToken();
    			stmt.setLong(1, Long.parseLong(idkmk));
    			stmt.setLong(2, Long.parseLong(krklm));
    			rs = stmt.executeQuery();
    			rs.next();
    			boolean mk_akhir = rs.getBoolean(1);
    			if(mk_akhir) {
    				li.set(npmhs+"`"+idkmk+"`"+krklm+"`E`0");
    				
    			}
    		}
    		stmt = con.prepareStatement("update TRNLM set NLAKHTRNLM=?,BOBOTTRNLM=? where THSMSTRNLM=? and NPMHSTRNLM=? and IDKMKTRNLM=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			//System.out.print("brs="+brs);
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String npmhs=st.nextToken();
    			String idkmk=st.nextToken();
    			String krklm=st.nextToken();
    			String nlakh=st.nextToken();
    			String bobot=st.nextToken();
    			stmt.setString(1, nlakh);
    			stmt.setDouble(2, Double.parseDouble(bobot));
    			stmt.setString(3, target_thsms);
    			stmt.setString(4, npmhs);
    			stmt.setLong(5, Long.parseLong(idkmk));
    			int i = stmt.executeUpdate();
    			
    			//System.out.print(npmhs+"`"+idkmk+"`"+krklm+"`"+nlakh+"`"+bobot+"=");
    			//System.out.println(i);
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
    
    public String nextRandomNilai(String kdjen) {
    	String nlakh_bobot = "";
    	int randomInt = randomNumberGenerator.nextInt(11);
    	try {
    		if(!kdjen.equalsIgnoreCase("A") && !kdjen.equalsIgnoreCase("B")) {
    			if(randomInt>8) {
    				nlakh_bobot = "A`4";	
    			}
    			else if(randomInt>6 && randomInt<9) {
    				nlakh_bobot = "A-`3.5";	
    			}
    			else if(randomInt>3 && randomInt<7) {
    				nlakh_bobot = "B+`3.25";
    			}
    			else if(randomInt>0 && randomInt<4) {
    				nlakh_bobot = "B`3";
    			}
    			else if(randomInt==0) {
    				nlakh_bobot = "B-`2.75";
    			}
    		}
    		else {
    			//pasca
    			if(randomInt>8) {
    				nlakh_bobot = "A`4";	
    			}
    			else if(randomInt>6 && randomInt<9) {
    				nlakh_bobot = "A-`3.5";	
    			}
    			else if(randomInt>3 && randomInt<7) {
    				nlakh_bobot = "B+`3.25";
    			}
    			else if(randomInt>=0 && randomInt<4) {
    				nlakh_bobot = "B`3";
    			}
    		}
    		
    			
    		
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	return nlakh_bobot;	
    }
    
    
    public void deleteMkTrnlpYgAdaDiTrnlm(Vector vTrnlp, String npmhs) {
    	if(vTrnlp!=null && vTrnlp.size()>0) {
    		try {
        		//String ipAddr =  request.getRemoteAddr();
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("delete from TRNLM where NPMHSTRNLM=? and (KDKMKTRNLM=? or KDKMKTRNLM=? or KDKMKTRNLM is null)");
        		ListIterator litmp = vTrnlp.listIterator();
				boolean match = false;
				while(litmp.hasNext() && !match) {
					String thsmsTrnlp = (String)litmp.next();
					String kdkmkTrnlp = (String)litmp.next();
					String nakmkTrnlp = (String)litmp.next();
					String nlakhTrnlp = (String)litmp.next();
					String bobotTrnlp = (String)litmp.next();
					String kdaslTrnlp = (String)litmp.next();
					String nmaslTrnlp = (String)litmp.next();
					String sksmkTrnlp = (String)litmp.next();
					String totSksTransferedTrnlp = (String)litmp.next();
					String sksasTrnlp = (String)litmp.next();
					String transferredTrnlp = (String)litmp.next();
					stmt.setString(1, npmhs);
					stmt.setString(2, kdkmkTrnlp);
					stmt.setString(3, "null");
					//System.out.print("hapus "+npmhs+"-"+kdkmkTrnlp);
					int k = stmt.executeUpdate();
					//System.out.println("="+k);
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
    
    public void geserThsmsMakulKrsBanBerjalan(String token_info, String npmhs) {
    	if(!Checker.isStringNullOrEmpty(token_info)) {
    		try {
    			String kdpst = Checker.getKdpst(npmhs);
        		//String ipAddr =  request.getRemoteAddr();
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("update ignore TRNLM set THSMSTRNLM=? where THSMSTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=?");
        		//System.out.println(token_info);
        		//20112`1032`MU8`20132
        		Vector v_nu_thsms = new Vector();
        		ListIterator linu = v_nu_thsms.listIterator();
        		Vector v_old_thsms = new Vector();
        		ListIterator lio = v_old_thsms.listIterator();
        		StringTokenizer st = new StringTokenizer(token_info,"`");
        		while(st.hasMoreTokens()) {
        			String init_thsms = st.nextToken();
        			String idkmk = st.nextToken();
        			String kdkmk = st.nextToken();
        			String target_thsms = st.nextToken();
        			stmt.setString(1, target_thsms);
        			stmt.setString(2, init_thsms);
        			stmt.setString(3, npmhs);
        			stmt.setString(4, kdkmk);
        			stmt.executeUpdate();
        			linu.add(target_thsms);;
        			lio.add(init_thsms);
        		}
        		
        		stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=?");
        		//System.out.println(token_info);
        		//20112`1032`MU8`20132
        		st = new StringTokenizer(token_info,"`");
        		while(st.hasMoreTokens()) {
        			String init_thsms = st.nextToken();
        			String idkmk = st.nextToken();
        			String kdkmk = st.nextToken();
        			String target_thsms = st.nextToken();
        			stmt.setString(1, init_thsms);
        			stmt.setString(2, npmhs);
        			stmt.setString(3, kdkmk);
        			//System.out.print(init_thsms+"-"+npmhs+"-"+kdkmk+"=");
        			int i = stmt.executeUpdate();
        			//System.out.println(i);
        		}
        		
        		linu = v_nu_thsms.listIterator();
        		if(linu.hasNext()) {
        			stmt = con.prepareStatement("INSERT ignore into TRAKM(THSMSTRAKM,KDPTITRAKM,KDJENTRAKM,KDPSTTRAKM,NPMHSTRAKM,SKSEMTRAKM,NLIPSTRAKM,SKSTTTRAKM,NLIPKTRAKM)values(?,?,?,?,?,?,?,?,?)");
            		while(linu.hasNext()) {
            			String thsms = (String)linu.next();
            			stmt.setString(1, thsms);
                		stmt.setString(2, Constants.getKdpti());
                		stmt.setNull(3,java.sql.Types.VARCHAR);
                		stmt.setString(4, kdpst);
                		stmt.setString(5, npmhs);
                		stmt.setInt(6, 0);
                		stmt.setDouble(7, 0);
                		stmt.setInt(8, 0);
                		stmt.setDouble(9, 0);
                		stmt.executeUpdate();
            		}
        		}
        		
        		lio = v_old_thsms.listIterator();
        		if(lio.hasNext()) {
        			stmt = con.prepareStatement("select * from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
        			while(lio.hasNext()) {
        				String thsms = (String)lio.next();
        				stmt.setString(1, thsms);
        				stmt.setString(2, npmhs);
        				rs = stmt.executeQuery();
        				if(rs.next()) {
        					lio.remove();//masih ada mk lain di thsams
        				}
        			}
        			
        		}
        		if(v_old_thsms!=null) {
        			lio = v_old_thsms.listIterator();
            		if(lio.hasNext()) {
            			stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM=? and NPMHSTRAKM=?");
            			while(lio.hasNext()) {
            				String thsms = (String)lio.next();
            				stmt.setString(1, thsms);
                    		stmt.setString(2, npmhs);
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
    	}
    		
    }
    
    public void copyKrsDariNpmPertamaKhusus20152_20161(String token_npm) {
    	if(!Checker.isStringNullOrEmpty(token_npm)) {
    		try {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select * from TRNLM where (THSMSTRNLM=? or THSMSTRNLM=?) and NPMHSTRNLM=? order by THSMSTRNLM");
        		StringTokenizer st = new StringTokenizer(token_npm,"`");
        		String npm_source = st.nextToken();
        		stmt.setString(1, "20152");
        		stmt.setString(2, "20161");
        		stmt.setString(3, npm_source);
        		rs = stmt.executeQuery();
        		Vector v = new Vector();
        		ListIterator li = v.listIterator();
        		while(rs.next()) {
        			String thsms = ""+rs.getString("THSMSTRNLM");
        			String kdpti = ""+rs.getString("KDPTITRNLM");
        			String kdjen = ""+rs.getString("KDJENTRNLM");
        			String kdpst = ""+rs.getString("KDPSTTRNLM");
        			String kdkmk = ""+rs.getString("KDKMKTRNLM");
            	    String nilai = ""+rs.getFloat("NILAITRNLM");
            	    String nlakh = ""+rs.getString("NLAKHTRNLM");
            	    String bobot = ""+rs.getFloat("BOBOTTRNLM");
            	    String sksmk = ""+rs.getInt("SKSMKTRNLM");
            	    String kelas = ""+rs.getString("KELASTRNLM");
            	    String shift = ""+rs.getString("SHIFTTRNLM");
            	    String idkmk = ""+rs.getInt("IDKMKTRNLM");
        			li.add(thsms+"`"+kdpti+"`"+kdjen+"`"+kdpst+"`"+kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+kelas+"`"+shift+"`"+idkmk);
        		}
        		if(v.size()>0 && st.hasMoreTokens()) {
        			stmt = con.prepareStatement("insert ignore into TRNLM(THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,KELASTRNLM,SHIFTTRNLM,IDKMKTRNLM)values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        			
        			while(st.hasMoreTokens()) {
        				li = v.listIterator();
        				String target_npm = st.nextToken();
        				while(li.hasNext()) {
            				String brs = (String)li.next();
            				StringTokenizer st1 = new StringTokenizer(brs,"`");
            				String thsms = st1.nextToken();
            				String kdpti = st1.nextToken();
            				String kdjen = st1.nextToken();
            				String kdpst = st1.nextToken();
            				String kdkmk = st1.nextToken();
            				String nilai = st1.nextToken();
            				String nlakh = st1.nextToken();
            				String bobot = st1.nextToken();
            				String sksmk = st1.nextToken();
            				String kelas = st1.nextToken();
            				String shift = st1.nextToken();
            				String idkmk = st1.nextToken();
            				stmt.setString(1,thsms);
            				stmt.setString(2,kdpti);
            				stmt.setString(3,kdjen);
            				stmt.setString(4,kdpst);
            				stmt.setString(5,target_npm);
            				stmt.setString(6,kdkmk);
            				stmt.setDouble(7,Double.parseDouble(nilai));
                    	    stmt.setString(8,nlakh);
                    	    stmt.setDouble(9,Double.parseDouble(bobot));
                    	    stmt.setInt(10, Integer.parseInt(sksmk));
                    	    stmt.setString(11,kelas);
                    	    stmt.setString(12,shift);
                    	    stmt.setInt(13,Integer.parseInt(idkmk));
                    	    //System.out.print(target_npm+" insert="+brs);
                    	    int i = stmt.executeUpdate();
                    	    //System.out.println("="+i);
            			}	
        			}
        			
        			st = new StringTokenizer(token_npm,"`");
            		npm_source = st.nextToken();
        			while(st.hasMoreTokens()) {
        				li = v.listIterator();
        				stmt = con.prepareStatement("INSERT ignore into TRAKM(THSMSTRAKM,KDPTITRAKM,KDJENTRAKM,KDPSTTRAKM,NPMHSTRAKM,SKSEMTRAKM,NLIPSTRAKM,SKSTTTRAKM,NLIPKTRAKM)values(?,?,?,?,?,?,?,?,?)");
        				String target_npm = st.nextToken();
        				while(li.hasNext()) {
            				String brs = (String)li.next();
            				StringTokenizer st1 = new StringTokenizer(brs,"`");
            				String thsms = st1.nextToken();
            				String kdpti = st1.nextToken();
            				String kdjen = st1.nextToken();
            				String kdpst = st1.nextToken();
            				String kdkmk = st1.nextToken();
            				String nilai = st1.nextToken();
            				String nlakh = st1.nextToken();
            				String bobot = st1.nextToken();
            				String sksmk = st1.nextToken();
            				String kelas = st1.nextToken();
            				String shift = st1.nextToken();
            				String idkmk = st1.nextToken();
            				stmt.setString(1, thsms);
                    		stmt.setString(2, kdpti);
                    		stmt.setString(3,kdjen);
                    		stmt.setString(4, kdpst);
                    		stmt.setString(5, target_npm);
                    		stmt.setInt(6, 0);
                    		stmt.setDouble(7, 0);
                    		stmt.setInt(8, 0);
                    		stmt.setDouble(9, 0);
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
    	}
    		
    }
    
    
    public void updateTrnlm(Vector vThsmsNpmhsKdpstKdkmkIdkmkFinalmkNlakhBobot) {
    	if(vThsmsNpmhsKdpstKdkmkIdkmkFinalmkNlakhBobot!=null) {
    		ListIterator li = vThsmsNpmhsKdpstKdkmkIdkmkFinalmkNlakhBobot.listIterator();
    		try {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("update TRNLM set NLAKHTRNLM=?,BOBOTTRNLM=? where THSMSTRNLM=? and NPMHSTRNLM=? and IDKMKTRNLM=?");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String thsms = st.nextToken();
        			String npmhs = st.nextToken();
        			String kdpst = st.nextToken();
        			String kdkmk = st.nextToken();
        			String idkmk = st.nextToken();
        			String finalmk = st.nextToken();
        			String nlakh = st.nextToken();
        			String bobot = st.nextToken();
        			stmt.setString(1, nlakh);
        			stmt.setDouble(2, Double.parseDouble(bobot));
        			stmt.setString(3, thsms);
        			stmt.setString(4, npmhs);
        			stmt.setInt(5, Integer.parseInt(idkmk));
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
    
    public int updateTrnlmKelasEpsbed(Vector v_sdt_addInfoDaftarMahasiswaDanDaftarkanKeTrnlmEpsbed, String target_thsms) {
    	int updated = 0;
    	if(v_sdt_addInfoDaftarMahasiswaDanDaftarkanKeTrnlmEpsbed!=null) {
    		ListIterator li = v_sdt_addInfoDaftarMahasiswaDanDaftarkanKeTrnlmEpsbed.listIterator();
    		try {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
        		con = ds.getConnection();
        		stmt = con.prepareStatement("update TRNLM set KELASEPSBED=? where THSMSTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=?");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			Vector v_mhs = (Vector)li.next();
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
					stmt.setString(1, kode_kls);
        			stmt.setString(2, target_thsms);
        			stmt.setString(4, kdkmk);
					ListIterator lit = v_mhs.listIterator();
					while(lit.hasNext()) {
						String npmhs = (String)lit.next();
						stmt.setString(3, npmhs);
						updated = updated+stmt.executeUpdate();
						//System.out.println("update "+npmhs+"="+updated);
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
    	return updated;
    }
    
    public int updateNilaiKolomRobot(Vector v_searchDbTrnlm_getKrsYgMemilikiNilaiTunda) {
    	int counter = 0;
    	
    	if(v_searchDbTrnlm_getKrsYgMemilikiNilaiTunda!=null && v_searchDbTrnlm_getKrsYgMemilikiNilaiTunda.size()>0) {
    		StringTokenizer st = null;
    		try {
        		//String ipAddr =  request.getRemoteAddr();
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
        		con = ds.getConnection();
        		stmt = con.prepareStatement("update TRNLM set NLAKHROBOT=?,BOBOTROBOT=? where THSMSTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=?");
        		ListIterator li = v_searchDbTrnlm_getKrsYgMemilikiNilaiTunda.listIterator();
        		while(li.hasNext()) {
        			//li.add(thsms+"`"+kdjen+"`"+npmhs+"`"+kdkmk+"`"+"`"+sksmk);
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			String thsms = st.nextToken();
        			String kdjen = st.nextToken();
        			String npmhs = st.nextToken();
        			String kdkmk = st.nextToken();
        			String sksmk = st.nextToken();
        			String nlakh_bobot = nextRandomNilai(kdjen);
        			st = new StringTokenizer(nlakh_bobot,"`");
        			String nlakh = st.nextToken();
        			String bobot = st.nextToken();
        			stmt.setString(1, nlakh);
        			stmt.setDouble(2, Double.parseDouble(bobot));
        			stmt.setString(3, thsms);
        			stmt.setString(4, npmhs);
        			stmt.setString(5, kdkmk);
        			int i = stmt.executeUpdate();
        			//System.out.println(counter+"."+brs+" = "+nlakh+"_"+bobot+" = "+i);
        			if(i>0) {
        				counter++;
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
    
    public int updateNilaiKolomRobotDgnNilai(Vector v_searchDbTrnlm_getKrsYgMemilikiNilaiTunda, String tkn_nlakh_bobot_nilai) {
    	int counter = 0;
    	
    	if(v_searchDbTrnlm_getKrsYgMemilikiNilaiTunda!=null && v_searchDbTrnlm_getKrsYgMemilikiNilaiTunda.size()>0) {
    		StringTokenizer st = null;
    		try {
        		//String ipAddr =  request.getRemoteAddr();
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
        		con = ds.getConnection();
        		stmt = con.prepareStatement("update TRNLM set NLAKHROBOT=?,BOBOTROBOT=? where THSMSTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=?");
        		ListIterator li = v_searchDbTrnlm_getKrsYgMemilikiNilaiTunda.listIterator();
        		while(li.hasNext()) {
        			//li.add(thsms+"`"+kdjen+"`"+npmhs+"`"+kdkmk+"`"+"`"+sksmk);
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			String thsms = st.nextToken();
        			String kdjen = st.nextToken();
        			String npmhs = st.nextToken();
        			String kdkmk = st.nextToken();
        			String sksmk = st.nextToken();
        			//String nlakh_bobot = nextRandomNilai(kdjen);
        			st = new StringTokenizer(tkn_nlakh_bobot_nilai,"`");
        			String nlakh = st.nextToken();
        			String bobot = st.nextToken();
        			String nilai = null;
        			if(st.hasMoreTokens()) {
        				nilai = new String(st.nextToken());
        			}
        			stmt.setString(1, nlakh);
        			stmt.setDouble(2, Double.parseDouble(bobot));
        			stmt.setString(3, thsms);
        			stmt.setString(4, npmhs);
        			stmt.setString(5, kdkmk);
        			int i = stmt.executeUpdate();
        			//System.out.println(counter+"."+brs+" = "+nlakh+"_"+bobot+" = "+i);
        			if(i>0) {
        				counter++;
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
    
    
    public int kasihNilaiTundaUntukMkFinalAtAllThsmsAtKolomRobot() {
    	int counter = 0;
    	SearchDbMk sdm = new SearchDbMk();
    	Vector v = sdm.getListMkFinalAllKdpst();
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		
    		
    		if(v.size()>0) {
    			stmt = con.prepareStatement("update TRNLM set NILAIROBOT=0,NLAKHROBOT='T',BOBOTROBOT=0 where KDPSTTRNLM=? and KDKMKTRNLM=? and SKSMKTRNLM=?");
    			ListIterator li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String idkmk = st.nextToken();
        			String kdpst = st.nextToken();
        			String kdkmk = st.nextToken();
        			String sksmk = st.nextToken();
        			stmt.setString(1,kdpst);
        			stmt.setString(2,kdkmk);
        			stmt.setString(3,sksmk);
        			int i  = stmt.executeUpdate();
        			if(i>0) {
        				counter = counter + i;
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
    	return counter;
    }
    
    
    public int kasihNilaiTundaUntukMkFinalAtAllThsmsAtKolomRobot(String target_thsms) {
    	int counter = 0;
    	SearchDbMk sdm = new SearchDbMk();
    	Vector v = sdm.getListMkFinalAllKdpst();
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		
    		
    		if(v.size()>0) {
    			stmt = con.prepareStatement("update TRNLM set NILAIROBOT=0,NLAKHROBOT='T',BOBOTROBOT=0 where THSMSTRNLM=? and KDPSTTRNLM=? and KDKMKTRNLM=? and SKSMKTRNLM=?");
    			ListIterator li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String idkmk = st.nextToken();
        			String kdpst = st.nextToken();
        			String kdkmk = st.nextToken();
        			String sksmk = st.nextToken();
        			stmt.setString(1,target_thsms);
        			stmt.setString(2,kdpst);
        			stmt.setString(3,kdkmk);
        			stmt.setString(4,sksmk);
        			int i  = stmt.executeUpdate();
        			if(i>0) {
        				counter = counter + i;
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
    	return counter;
    }
    
    public int kasihNilaiMengulangUntukMkFinalAtAllThsmsAtKolomRobot() {
    	int counter = 0;
    	SearchDbMk sdm = new SearchDbMk();
    	Vector v = sdm.getListMkFinalAllKdpst();
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		
    		
    		if(v.size()>0) {
    			stmt = con.prepareStatement("update TRNLM set NILAIROBOT=0,NLAKHROBOT='E',BOBOTROBOT=0 where KDPSTTRNLM=? and KDKMKTRNLM=? and SKSMKTRNLM=?");
    			ListIterator li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String idkmk = st.nextToken();
        			String kdpst = st.nextToken();
        			String kdkmk = st.nextToken();
        			String sksmk = st.nextToken();
        			stmt.setString(1,kdpst);
        			stmt.setString(2,kdkmk);
        			stmt.setString(3,sksmk);
        			int i  = stmt.executeUpdate();
        			if(i>0) {
        				counter = counter + i;
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
    	return counter;
    }
    
    public int kasihNilaiMengulangUntukMkFinalAtAllThsmsAtKolomRobot(String target_thsms) {
    	int counter = 0;
    	SearchDbMk sdm = new SearchDbMk();
    	Vector v = sdm.getListMkFinalAllKdpst();
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		
    		
    		if(v.size()>0) {
    			stmt = con.prepareStatement("update TRNLM set NILAIROBOT=0,NLAKHROBOT='E',BOBOTROBOT=0 where THSMSTRNLM=? and KDPSTTRNLM=? and KDKMKTRNLM=? and SKSMKTRNLM=?");
    			ListIterator li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String idkmk = st.nextToken();
        			String kdpst = st.nextToken();
        			String kdkmk = st.nextToken();
        			String sksmk = st.nextToken();
        			stmt.setString(1,target_thsms);
        			stmt.setString(2,kdpst);
        			stmt.setString(3,kdkmk);
        			stmt.setString(4,sksmk);
        			int i  = stmt.executeUpdate();
        			if(i>0) {
        				counter = counter + i;
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
    	return counter;
    }
    
    public int kasihNilaiMkFinalAtThsmsKelulusanAtKolomRobot() {
    	int counter = 0;
    	SearchDbMk sdm = new SearchDbMk();
    	Vector v_mk_final = sdm.getListMkFinalAllKdpst();
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	ListIterator lim = null;
    	StringTokenizer st = null;
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT THSMS,KDJENMSPST,KDPST,NPMHS FROM TRLSM inner join MSPST on KDPST=KDPSTMSPST where STMHS='L'");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			int i = 1;
    			String thsms = ""+rs.getInt(i++);
    			String kdjen = ""+rs.getString(i++);
    			String kdpst = ""+rs.getString(i++);
    			String npmhs = ""+rs.getString(i++);
    			String tkn_mk_finale = null;
    			lim = v_mk_final.listIterator();
    			while(lim.hasNext()) {
    				String brs = (String)lim.next();
    				st = new StringTokenizer(brs,"`");
    				//li.add(idkmk+"`"+kdpst+"`"+kdkmk+"`"+sksmk);
    				String idkmk_mkf = st.nextToken();
    				String kdpst_mkf = st.nextToken();
    				String kdkmk_mkf = st.nextToken();
    				String sksmk_mkf = st.nextToken();
    				if(kdpst.equalsIgnoreCase(kdpst_mkf)) {
    					String nlakh_bobot = nextRandomNilai(kdjen);
    					if(tkn_mk_finale==null) {
    						tkn_mk_finale=new String(brs+"`"+nlakh_bobot);
    					}
    					else {
    						tkn_mk_finale = tkn_mk_finale+"`"+brs+"`"+nlakh_bobot;
    					}
    				}
    			}
    			li.add(thsms+"`"+kdjen+"`"+kdpst+"`"+npmhs+"`"+tkn_mk_finale);
    		}
    		if(v.size()>0) {
    			counter = 0;
    			stmt = con.prepareStatement("update TRNLM set NILAIROBOT=0,NLAKHROBOT=?,BOBOTROBOT=? where THSMSTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				st = new StringTokenizer(brs,"`");
    				String thsms = st.nextToken();
    				String kdjen = st.nextToken();
    				String kdpst = st.nextToken();
    				String npmhs = st.nextToken();
    				while(st.hasMoreTokens()) {
    					String idkmk_mkf = st.nextToken();
        				String kdpst_mkf = st.nextToken();
        				String kdkmk_mkf = st.nextToken();
        				String sksmk_mkf = st.nextToken();
        				String nlakh_robot = st.nextToken();
        				String bobot_robot = st.nextToken();
        				stmt.setString(1, nlakh_robot);
        				stmt.setDouble(2, Double.parseDouble(bobot_robot));
        				stmt.setString(3, thsms);
        				stmt.setString(4, npmhs);
        				stmt.setString(5, kdkmk_mkf);
        				int i = stmt.executeUpdate();
        				if(i>0) {
        					counter = counter +i;
        				}
    				}
    				//System.out.println("bris="+brs);
    			}	
    			/*
    			stmt = con.prepareStatement("update TRNLM set NLAKHROBOT=?,BOBOTROBOT=? where KDPSTTRNLM=? and KDKMKTRNLM=? and SKSMKTRNLM=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String idkmk = st.nextToken();
        			String kdpst = st.nextToken();
        			String kdkmk = st.nextToken();
        			String sksmk = st.nextToken();
        			stmt.setString(1,kdpst);
        			stmt.setString(2,kdkmk);
        			stmt.setString(3,sksmk);
        			int i  = stmt.executeUpdate();
        			if(i>0) {
        				counter = counter + i;
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
    	return counter;
    }
    
    
    public int kasihNilaiMkFinalAtThsmsKelulusanAtKolomRobot(String target_thsms) {
    	int counter = 0;
    	SearchDbMk sdm = new SearchDbMk();
    	Vector v_mk_final = sdm.getListMkFinalAllKdpst();
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	ListIterator lim = null;
    	StringTokenizer st = null;
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT THSMS,KDJENMSPST,KDPST,NPMHS FROM TRLSM inner join MSPST on KDPST=KDPSTMSPST where THSMS=? and STMHS='L'");
    		stmt.setString(1, target_thsms);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			int i = 1;
    			String thsms = ""+rs.getInt(i++);
    			String kdjen = ""+rs.getString(i++);
    			String kdpst = ""+rs.getString(i++);
    			String npmhs = ""+rs.getString(i++);
    			String tkn_mk_finale = null;
    			lim = v_mk_final.listIterator();
    			while(lim.hasNext()) {
    				String brs = (String)lim.next();
    				st = new StringTokenizer(brs,"`");
    				//li.add(idkmk+"`"+kdpst+"`"+kdkmk+"`"+sksmk);
    				String idkmk_mkf = st.nextToken();
    				String kdpst_mkf = st.nextToken();
    				String kdkmk_mkf = st.nextToken();
    				String sksmk_mkf = st.nextToken();
    				if(kdpst.equalsIgnoreCase(kdpst_mkf)) {
    					String nlakh_bobot = nextRandomNilai(kdjen);
    					if(tkn_mk_finale==null) {
    						tkn_mk_finale=new String(brs+"`"+nlakh_bobot);
    					}
    					else {
    						tkn_mk_finale = tkn_mk_finale+"`"+brs+"`"+nlakh_bobot;
    					}
    				}
    			}
    			li.add(thsms+"`"+kdjen+"`"+kdpst+"`"+npmhs+"`"+tkn_mk_finale);
    		}
    		if(v.size()>0) {
    			counter = 0;
    			stmt = con.prepareStatement("update TRNLM set NILAIROBOT=0,NLAKHROBOT=?,BOBOTROBOT=? where THSMSTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				st = new StringTokenizer(brs,"`");
    				String thsms = st.nextToken();
    				String kdjen = st.nextToken();
    				String kdpst = st.nextToken();
    				String npmhs = st.nextToken();
    				while(st.hasMoreTokens()) {
    					String idkmk_mkf = st.nextToken();
        				String kdpst_mkf = st.nextToken();
        				String kdkmk_mkf = st.nextToken();
        				String sksmk_mkf = st.nextToken();
        				String nlakh_robot = st.nextToken();
        				String bobot_robot = st.nextToken();
        				stmt.setString(1, nlakh_robot);
        				stmt.setDouble(2, Double.parseDouble(bobot_robot));
        				stmt.setString(3, thsms);
        				stmt.setString(4, npmhs);
        				stmt.setString(5, kdkmk_mkf);
        				int i = stmt.executeUpdate();
        				if(i>0) {
        					counter = counter +i;
        				}
    				}
    				//System.out.println("bris="+brs);
    			}	
    			/*
    			stmt = con.prepareStatement("update TRNLM set NLAKHROBOT=?,BOBOTROBOT=? where KDPSTTRNLM=? and KDKMKTRNLM=? and SKSMKTRNLM=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String idkmk = st.nextToken();
        			String kdpst = st.nextToken();
        			String kdkmk = st.nextToken();
        			String sksmk = st.nextToken();
        			stmt.setString(1,kdpst);
        			stmt.setString(2,kdkmk);
        			stmt.setString(3,sksmk);
        			int i  = stmt.executeUpdate();
        			if(i>0) {
        				counter = counter + i;
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
    	return counter;
    }
    
    
    public int updateDataFromFormKrsWhitelist(String tkn_input_value) {
    	int upd=0;
    	String thsms_krs = Checker.getThsmsKrs();
    	try {
    		if(tkn_input_value!=null) {
				
				StringTokenizer st = new StringTokenizer(tkn_input_value,"`");
				
				String kdpst=st.nextToken();
				String npmhs=st.nextToken();
				String tkn_thsms=st.nextToken();
				if(!Checker.isStringNullOrEmpty(kdpst) && !Checker.isStringNullOrEmpty(npmhs)) {
					Context initContext  = new InitialContext();
		    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
		    		con = ds.getConnection();
		    		
					String cmd = "UPDATE KRS_WHITE_LIST SET "
							+"TOKEN_THSMS=? where "//1
							+"KDPST=? and "//2
							+"NPMHS=?";//3
					stmt = con.prepareStatement(cmd);
					if(Checker.isStringNullOrEmpty(tkn_thsms) ) {
						stmt.setNull(1, java.sql.Types.VARCHAR);
					}
					else {
						stmt.setString(1, tkn_thsms);	
					}
					
					stmt.setString(2, kdpst);
					stmt.setString(3, npmhs);
					upd = stmt.executeUpdate();
					if(upd<1) {
						//belum ada prev record jadi harsu di iins
						cmd = "insert into KRS_WHITE_LIST (KDPST,NPMHS,TOKEN_THSMS) values (?,?,?)";
						stmt = con.prepareStatement(cmd);
						stmt.setString(1, kdpst);
						stmt.setString(2, npmhs);
						//stmt.setString(3, tkn_thsms);
						if(Checker.isStringNullOrEmpty(tkn_thsms) ) {
							stmt.setNull(3, java.sql.Types.VARCHAR);
						}
						else {
							stmt.setString(3, tkn_thsms);	
						}
						
						upd=stmt.executeUpdate();
					}
					
					//release lock di trnlm 
					if(upd>0) {
						stmt=con.prepareStatement("update TRNLM set LOCKTRNLM=?,LOCKMHS=? where NPMHSTRNLM=? and THSMSTRNLM=?");
						stmt.setBoolean(1,false);
						stmt.setBoolean(2,false);
						stmt.setString(3, npmhs);
						String seperator = Checker.getSeperatorYgDigunakan(tkn_thsms);
						//System.out.println("thsms=="+tkn_thsms+"&&"+seperator);
						st = new StringTokenizer(tkn_thsms,seperator);
						while(st.hasMoreTokens()) {
							String thsms_target = st.nextToken();
							stmt.setString(4, thsms_target);
							stmt.executeUpdate();
						}	
					}
				}
				
				/*
				//cek ynpm yg udah mo direset untuk dilock kembali
				String tkn_npm = null;
				stmt = con.prepareStatement("select distinct NPMHS from KRS_WHITE_LIST where TOKEN_THSMS is null");
				rs = stmt.executeQuery();
				while(rs.next()) {
					if(tkn_npm==null) {
						tkn_npm = rs.getString(1);
					}
					else {
						tkn_npm = tkn_npm+","+rs.getString(1);	
					}	
				}
				if(tkn_npm!=null) {
					st = new StringTokenizer(tkn_npm,",");
					stmt = con.prepareStatement("update TRNLM set LOCKTRNLM=?,LOCKMHS=? where NPMHSTRNLM=? and THSMSTRNLM<?");
					while(st.hasMoreTokens()) {
						String npm = st.nextToken();
						stmt.setBoolean(1,false);
						stmt.setBoolean(2,false);
						stmt.setString(3, npm);
						stmt.setString(4, thsms_krs);
					}
				}
				//delete yg null thsms
				stmt = con.prepareStatement("delete from KRS_WHITE_LIST where TOKEN_THSMS is null");
				stmt.executeUpdate();
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
    	return upd;
    }
    
    
    public int resetNullThsmsKrsWhitelist() {
    	int upd=0;
    	String thsms_krs = Checker.getThsmsKrs();
    	try {
    		//cek ynpm yg udah mo direset untuk dilock kembali
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
			String tkn_npm = null;
			stmt = con.prepareStatement("select distinct NPMHS from KRS_WHITE_LIST where TOKEN_THSMS is null");
			rs = stmt.executeQuery();
			while(rs.next()) {
				if(tkn_npm==null) {
					tkn_npm = rs.getString(1);
				}
				else {
					tkn_npm = tkn_npm+","+rs.getString(1);	
				}	
			}
			if(tkn_npm!=null) {
				StringTokenizer st = new StringTokenizer(tkn_npm,",");
				stmt = con.prepareStatement("update TRNLM set LOCKTRNLM=?,LOCKMHS=? where NPMHSTRNLM=? and THSMSTRNLM<?");
				while(st.hasMoreTokens()) {
					String npm = st.nextToken();
					stmt.setBoolean(1,true);
					stmt.setBoolean(2,true);
					stmt.setString(3, npm);
					stmt.setString(4, thsms_krs);
				}
			}
			//delete yg null thsms
			stmt = con.prepareStatement("delete from KRS_WHITE_LIST where TOKEN_THSMS is null");
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
    	return upd;
    }
    
    public int deleteAllThsmsKrsWhitelist(double after_number_days) {
    	int upd=0;
    	String thsms_krs = Checker.getThsmsKrs();
    	String list_npm = "";
    	java.sql.Timestamp curr_stamp = Tool.getCurrentTimestamp();
    	//System.out.println("curr_time = "+curr_stamp);
    	try {
    		//cek ynpm yg udah mo direset untuk dilock kembali
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
			stmt = con.prepareStatement("select * from KRS_WHITE_LIST");
			rs = stmt.executeQuery();
			while(rs.next()) {
				java.sql.Timestamp old_date = rs.getTimestamp("UPDTM");
				//System.out.println("old_date = "+old_date);
				long minute_diff = Tool.compareTimestamp("mm", old_date, curr_stamp);
				//System.out.println("minute_diff = "+minute_diff);
				//System.out.println(after_number_days*24*60);
				if(minute_diff>=(after_number_days*24*60)) {
					list_npm = list_npm+","+rs.getString("NPMHS");
				}
			}
			if(!Checker.isStringNullOrEmpty(list_npm)) {
				StringTokenizer st = new StringTokenizer(list_npm,",");
				stmt = con.prepareStatement("delete from KRS_WHITE_LIST where NPMHS=?");
				while(st.hasMoreTokens()) {
					String npmhs = st.nextToken();
					stmt.setString(1, npmhs);
					upd = upd+stmt.executeUpdate();
				}
				st = new StringTokenizer(list_npm,",");
				stmt = con.prepareStatement("update TRNLM set LOCKTRNLM=?,LOCKMHS=? where NPMHSTRNLM=? and THSMSTRNLM<?");
				while(st.hasMoreTokens()) {
					String npm = st.nextToken();
					stmt.setBoolean(1,true);
					stmt.setBoolean(2,true);
					stmt.setString(3, npm);
					stmt.setString(4, thsms_krs);
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
    
    
    public int insertKrsBasedOnKoAtThsms(Vector v_nphs_target_smsmk_info_ko, String target_thsms) {
    	int updated = 0;
    	String kdpti_def = Constants.getKdpti();
    	if(v_nphs_target_smsmk_info_ko!=null) {
    		Vector v_ins = new Vector();
        	ListIterator lins = v_ins.listIterator();
    		SearchDbKrklm sdk = new SearchDbKrklm();
    		ListIterator li=v_nphs_target_smsmk_info_ko.listIterator();
    		StringTokenizer st = null;
        	try {
        		//String ipAddr =  request.getRemoteAddr();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			lins.add(brs);
        			//System.out.println("insert baris="+brs);
        			st = new StringTokenizer(brs,"`");
        			String npmhs = st.nextToken();
        			String target_sms = st.nextToken();
        			String id_krklm = st.nextToken();
        			
        			Vector v_tmp = sdk.getMkGivenSmsAndKrklmId(Integer.parseInt(target_sms), Integer.parseInt(id_krklm));
        			if(v_tmp==null) {
        				v_tmp = new Vector();
        			}
        			lins.add(v_tmp);
        		}
        		
        		if(v_ins!=null) {
        			Context initContext  = new InitialContext();
            		Context envContext  = (Context)initContext.lookup("java:/comp/env");
            		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
            		con = ds.getConnection();
        			
        			lins = v_ins.listIterator();
        			while(lins.hasNext()) {
        				String brs = (String)lins.next();
        				//System.out.println("baris = "+brs);
        				st = new StringTokenizer(brs,"`");
        				String npmhs = st.nextToken();
        				String mk_sms_ke = st.nextToken();
        				String id_krklm = st.nextToken();
        				String id_obj = st.nextToken();
            			String kdpti = st.nextToken();
            			if(Checker.isStringNullOrEmpty(kdpti)) {
            				kdpti = new String(kdpti_def);
            			}
            			String kdjen = st.nextToken();
            			String kdpst = st.nextToken();
            			String kdkmp = st.nextToken();
        				//1.delete prev value
            			stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
            			stmt.setString(1, target_thsms);
            			stmt.setString(2, npmhs);
            			stmt.executeUpdate();
            			//System.out.println(brs);
        				stmt = con.prepareStatement("insert into TRNLM(THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,SKSMKTRNLM,IDKMKTRNLM,KODE_KAMPUS)values(?,?,?,?,?,?,?,?,?)");
        				Vector v_tmp = (Vector)lins.next();
        				li = v_tmp.listIterator();
        				while(li.hasNext()) {
        					String brs1 = (String)li.next();
        					//coba update dulu
        					st = new StringTokenizer(brs1,"`");
        					String kdkmk = st.nextToken();
        					String sksmk = st.nextToken();
        					String idkmk = st.nextToken();
        					stmt.setString(1, target_thsms);
        					stmt.setString(2, kdpti);
        					stmt.setString(3, kdjen);
        					stmt.setString(4, kdpst);
        					stmt.setString(5, npmhs);
        					stmt.setString(6, kdkmk);
        					stmt.setInt(7, Integer.parseInt(sksmk));
        					stmt.setInt(8, Integer.parseInt(idkmk));
        					stmt.setString(9, kdkmp);
        					updated = updated + stmt.executeUpdate();
        					//System.out.println(npmhs+"`"+target_thsms+"-->"+brs1+" = "+updated);
        				}
        			}
        		}
        	}
        	catch (Exception e) {
        		e.printStackTrace();
        	}
        	
    	}
    	
    	return updated;
    }
}

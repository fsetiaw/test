package beans.dbase.Param;

import beans.dbase.UpdateDb;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
 * Session Bean implementation class UpdateDbParam
 */
@Stateless
@LocalBean
public class UpdateDbParam extends UpdateDb {
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
    public UpdateDbParam() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbParam(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    
    public int copyObjParam(long targetObjId, long basedObjId, String nu_own_inbox_id, String nu_tu_id, String nu_baa_id, String nu_bak_id, String nu_mhs_id, String nu_scp_kmp, String kode_kmp_dom, String sis_kul) {
    	String tkn_look_up = new String();
    	boolean first = true;
    	if(nu_own_inbox_id!=null && !Checker.isStringNullOrEmpty(nu_own_inbox_id)) {
    		if(first) {
    			first = false;
    		}
    		else {
    			if(!tkn_look_up.startsWith("`")) {
    				tkn_look_up = tkn_look_up + "`";
    			}
    		}
    		tkn_look_up = tkn_look_up+nu_own_inbox_id;
    	}
    	
    	if(nu_tu_id!=null && !Checker.isStringNullOrEmpty(nu_tu_id)) {
    		if(first) {
    			first = false;
    		}
    		else {
    			if(!tkn_look_up.startsWith("`")) {
    				tkn_look_up = tkn_look_up + "`";
    			}
    		}
    		tkn_look_up = tkn_look_up+nu_tu_id;
    	}
    	
    	if(nu_baa_id!=null && !Checker.isStringNullOrEmpty(nu_baa_id)) {
    		if(first) {
    			first = false;
    		}
    		else {
    			if(!tkn_look_up.startsWith("`")) {
    				tkn_look_up = tkn_look_up + "`";
    			}
    		}
    		tkn_look_up = tkn_look_up+nu_baa_id;
    	}
    	
    	if(nu_bak_id!=null && !Checker.isStringNullOrEmpty(nu_bak_id)) {
    		if(first) {
    			first = false;
    		}
    		else {
    			if(!tkn_look_up.startsWith("`")) {
    				tkn_look_up = tkn_look_up + "`";
    			}
    		}
    		tkn_look_up = tkn_look_up+nu_bak_id;
    	}
    	
    	if(nu_mhs_id!=null && !Checker.isStringNullOrEmpty(nu_mhs_id)) {
    		if(first) {
    			first = false;
    		}
    		else {
    			if(!tkn_look_up.startsWith("`")) {
    				tkn_look_up = tkn_look_up + "`";
    			}
    		}
    		tkn_look_up = tkn_look_up+nu_mhs_id;
    	}
    	
    	//System.out.println("tkn_look_up="+tkn_look_up);
    	//tkn_look_up=1|1`2|2`1|1`2|2`1|1`2|2`1|1`2|2`1|1`2|2
    	int i=0;
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from OBJECT where ID_OBJ=?");
    		stmt.setLong(1, basedObjId);
    		rs = stmt.executeQuery();
    		rs.next();
    		String alc = rs.getString("ACCESS_LEVEL_CONDITIONAL");
    		String nu_alc = replaceTknScope(alc, tkn_look_up);
    		
    		String al = rs.getString("ACCESS_LEVEL");
    		String dv = rs.getString("DEFAULT_VALUE");
    		//String on = rs.getString("OBJ_NICKNAME");
    		String ha = rs.getString("HAK_AKSES");
    		String sk = rs.getString("SCOPE_KAMPUS");
    		String nu_sk = nuScopeKampus(nu_alc, nu_scp_kmp);
    		if(nu_sk==null || Checker.isStringNullOrEmpty(nu_sk)) {
    			nu_sk = new String(sk);
    		}
    		String kkd = rs.getString("KODE_KAMPUS_DOMISILI");
    		if(kode_kmp_dom==null || Checker.isStringNullOrEmpty(kode_kmp_dom)) {
    			kode_kmp_dom = new String(kkd);
    		}
    		String sp = rs.getString("SISTEM_PERKULIAHAN");
    		//StringTokenizer st = new StringTokenizer(alc,"#");
    		//System.out.println(st.countTokens());
    		//st = new StringTokenizer(sk,"#");
    		//System.out.println(st.countTokens());
    		//replaceTknScope(alc, tkn_look_up);
    		if(nu_alc!=null && !Checker.isStringNullOrEmpty(nu_alc)) {
    			stmt = con.prepareStatement("update OBJECT set ACCESS_LEVEL_CONDITIONAL=?,ACCESS_LEVEL=?,DEFAULT_VALUE=?,HAK_AKSES=?,SCOPE_KAMPUS=?,KODE_KAMPUS_DOMISILI=?,SISTEM_PERKULIAHAN=? where ID_OBJ=?");
    			int norut=1;
    			stmt.setString(norut++,nu_alc);
    			//System.out.println("alc = "+alc);
    			stmt.setString(norut++,al);
    			//System.out.println("al = "+al);
    			if(dv==null || Checker.isStringNullOrEmpty(dv)) {
    				stmt.setNull(norut++,java.sql.Types.VARCHAR);
    			}
    			else {
    				stmt.setString(norut++,dv);
    			}
    			//System.out.println("dv = "+dv);
    			if(ha==null || Checker.isStringNullOrEmpty(ha)) {
    				stmt.setNull(norut++,java.sql.Types.VARCHAR);
    			}
    			else {
    				stmt.setString(norut++,ha);
    			}
    			//System.out.println("ha = "+ha);
    			if(nu_sk==null || Checker.isStringNullOrEmpty(nu_sk)) {
    				stmt.setNull(norut++,java.sql.Types.VARCHAR);
    			}
    			else {
    				stmt.setString(norut++,nu_sk);
    			}
    			//System.out.println("nu_sk = "+nu_sk);
    			if(kode_kmp_dom==null || Checker.isStringNullOrEmpty(kode_kmp_dom)) {
    				stmt.setNull(norut++,java.sql.Types.VARCHAR);
    			}
    			else {
    				stmt.setString(norut++,kode_kmp_dom);
    			}
    			//System.out.println("kode_kmp_dom = "+kode_kmp_dom);
    			if(sis_kul==null || Checker.isStringNullOrEmpty(sis_kul)) {
    				stmt.setNull(norut++,java.sql.Types.VARCHAR);
    			}
    			else {
    				stmt.setString(norut++,sis_kul);
    			}
    			//System.out.println("sis_kul = "+sis_kul);
    			stmt.setLong(norut++,targetObjId);
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
    	return i;
    }
    
    public String replaceTknScope(String tkn_asal, String tkn_lookup) {
    	//tkn_look_up=1|1`2|2`1|1`2|2`1|1`2|2`1|1`2|2`1|1`2|2
    	//System.out.println("tkn_asal = "+tkn_asal);
    	String edited = null;
    	if((tkn_asal!=null && !Checker.isStringNullOrEmpty(tkn_asal)) && (tkn_lookup!=null && !Checker.isStringNullOrEmpty(tkn_lookup))) {
    		edited = new String();
    		StringTokenizer stl1 = null;
    		StringTokenizer stl2 = null;
    		StringTokenizer st1 = new StringTokenizer(tkn_asal,"#");
    		while(st1.hasMoreTokens()) {
    			String tokens = st1.nextToken();
    			StringTokenizer st2 = new StringTokenizer(tokens,",");
    			while(st2.hasMoreTokens()) {
    				String original_single_token_with_sign = st2.nextToken();
    				String single_token_val = new String(original_single_token_with_sign);
    				single_token_val = single_token_val.replace("<", "");
    				single_token_val = single_token_val.replace(">", "");
    				single_token_val = single_token_val.replace("=", "");
    				single_token_val = single_token_val.replace(" ", "");
    				//System.out.println("original_single_token_with_sign="+original_single_token_with_sign);
    				//cek ke token lookup apa single_token harus di replace
    				boolean match = false;
    				stl1 = new StringTokenizer(tkn_lookup,"`");
    				while(stl1.hasMoreTokens() && !match) {
    					String a_pair_tkn_lookup = stl1.nextToken();
    					//harus pair (old|nu)
    					stl2 = new StringTokenizer(a_pair_tkn_lookup,"|");
    					if(stl2.countTokens()==2) {
    						String old_val = stl2.nextToken();
    						String nu_val = stl2.nextToken();
    						if(single_token_val.equalsIgnoreCase(old_val)) {
    							match = true;
    							
    							//replace with nu_val;
    							//forget about original sign -- tiban ajah
    							//String edited_single_token_with_sign = original_single_token_with_sign.replace(old_val, nu_val);
    							String edited_single_token_with_sign = new String(nu_val);
    							edited=edited+edited_single_token_with_sign;
    						}
    					}
    					else {
    						//skip aja = value tidak komplit
    					}
    					
    				}
    				if(!match) {
    					edited = edited+original_single_token_with_sign;
					}
    				if(st2.hasMoreTokens()) {
    					//String edited add koma
    					edited = edited+",";
    				}
    			}
    			//tidak seperti st2, st1 selalu diakhiri # 
    			//
    			edited = edited+"#";
    		}
    	}
    	else if((tkn_asal!=null && !Checker.isStringNullOrEmpty(tkn_asal)) && (tkn_lookup==null || Checker.isStringNullOrEmpty(tkn_lookup))) {
    		edited = new String(tkn_asal);
    	}
    	//System.out.println("edited = "+edited);
    	return edited;
    }
    
    public String nuScopeKampus(String tkn_alc, String nu_scp_kmp) {
    	//tkn_look_up=1|1`2|2`1|1`2|2`1|1`2|2`1|1`2|2`1|1`2|2
    	//System.out.println("tkn_asal = "+tkn_asal);
    	String edited = null;
    	if(tkn_alc!=null && !Checker.isStringNullOrEmpty(tkn_alc)) {
    		edited = new String();
    		StringTokenizer stl1 = null;
    		StringTokenizer stl2 = null;
    		StringTokenizer st1 = new StringTokenizer(tkn_alc,"#");
    		
    		while(st1.hasMoreTokens()) {
    			st1.nextToken();
    			edited = edited+nu_scp_kmp+"#";	
    		}
    	}
    	//System.out.println("edited = "+edited);
    	return edited;
    }
    	
    public String updatePindahProdiRules(String[]job,String[]prodi,String[]kmp, String target_thsms) {
    	
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		String list_kdpst = new String();
			String list_kmp = new String();
			Vector v_tmp = new Vector();
			ListIterator lit = v_tmp.listIterator();
			for(int i=0;i<prodi.length;i++) {
				StringTokenizer st = new StringTokenizer(prodi[i],"`");
				while(st.hasMoreTokens()) {
					lit.add(st.nextToken());
				}
			}
			v_tmp = Tool.removeDuplicateFromVector(v_tmp);
			lit = v_tmp.listIterator();
			while(lit.hasNext()) {
				list_kdpst = list_kdpst+(String)lit.next();
				if(lit.hasNext()) {
					list_kdpst = list_kdpst+"`";
				}
			}
			v_tmp = new Vector();
			lit = v_tmp.listIterator();
			for(int i=0;i<kmp.length;i++) {
				StringTokenizer st = new StringTokenizer(kmp[i],"`");
				while(st.hasMoreTokens()) {
					lit.add(st.nextToken());
				}
			}
			v_tmp = Tool.removeDuplicateFromVector(v_tmp);
			lit = v_tmp.listIterator();
			while(lit.hasNext()) {
				list_kmp = list_kmp+(String)lit.next();
				if(lit.hasNext()) {
					list_kmp = list_kmp+"`";
				}
			}
    		//System.out.println("list_prodi="+list_kdpst);
    		//System.out.println("list_kmp="+list_kmp);
    		StringTokenizer stp = new StringTokenizer(list_kdpst,"`");
    		
    		//delete prev record
    		stmt = con.prepareStatement("delete from PINDAH_PRODI_RULES where THSMS=? and KDPST=? and KODE_KAMPUS=?");
    		while(stp.hasMoreTokens()) {
    			stmt.setString(1, target_thsms);
    			stmt.setString(2, stp.nextToken());
    			StringTokenizer stk = new StringTokenizer(list_kmp,"`");
    			while(stk.hasMoreTokens()) {
    				stmt.setString(3, stk.nextToken());
    				stmt.executeUpdate();
    			}
    		}
    		
    		//process jabatan - get singkatan kali ada
    		stmt = con.prepareStatement("select SINGKATAN from JABATAN where NAMA_JABATAN=? and AKTIF=?");
    		String list_jabatan_short = new String();
    		String list_jabatan = new String();
    		for(int i=0;i<job.length;i++) {
    			stmt.setString(1, job[i]);
    			list_jabatan=list_jabatan+"`"+job[i];
    			stmt.setBoolean(2, true);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				String singkatan = ""+rs.getString(1);
    				if(Checker.isStringNullOrEmpty(singkatan)) {
    					list_jabatan_short=list_jabatan_short+"["+job[i]+"]";
    				}
    				else {
    					list_jabatan_short=list_jabatan_short+"["+singkatan+"]";
    				}
    			}
    			else {
    				list_jabatan_short=list_jabatan_short+"[null]"; //error 
    			}
    		}
    		//get id active untuk tiap jabatan
    		stmt = con.prepareStatement("select STRUKTURAL.OBJID from STRUKTURAL inner join JABATAN on NM_JOB=NAMA_JABATAN where NM_JOB=? and KDKMP=? and KDPST=? and STRUKTURAL.AKTIF=?");
    		Vector vf = new Vector();
    		ListIterator lif = vf.listIterator();
    		String tmp_list_job = "";
    		StringTokenizer stt = new StringTokenizer(list_jabatan,"`");
    		while(stt.hasMoreTokens()) {
    			tmp_list_job = tmp_list_job+"["+stt.nextToken()+"]";
    		}
    		stp = new StringTokenizer(list_kdpst,"`");
    		while(stp.hasMoreTokens()) {
    			
    			String target_prodi = stp.nextToken();
    			
    			StringTokenizer stk = new StringTokenizer(list_kmp,"`");
    			while(stk.hasMoreTokens()) {
    				String baris = new String();
    				baris = baris + "`"+target_prodi;
    				String target_kmp = stk.nextToken();
    				baris = baris + "`"+target_kmp+"`"+list_jabatan_short+"`";
    				StringTokenizer st3 = new StringTokenizer(list_jabatan,"`");
    				String tmp = new String();
    				while(st3.hasMoreTokens()) {
    					String nama_jabatan = st3.nextToken();
    					stmt.setString(1, nama_jabatan);
    					stmt.setString(2, target_kmp);
    					stmt.setString(3, target_prodi);
    					stmt.setBoolean(4, true);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						boolean first = true;
    						do {
    							String id = ""+rs.getLong(1);
    							if(first) {
    								first = false;
    								tmp = new String(id);
    							}
    							else {
    								tmp = tmp+"`"+id;
    							}
    						}
    						while(rs.next());
    					}
    					else {
    						tmp = "null";
    					}
    					tmp = "["+tmp+"]";
    					baris = baris+tmp;
    				}
    				
    				lif.add(baris);
    			}
    		}
    		
    		
    		//insert new one
    		stmt = con.prepareStatement("insert into PINDAH_PRODI_RULES(THSMS,KDPST,TKN_JABATAN_VERIFICATOR,TKN_VERIFICATOR_ID,URUTAN,KODE_KAMPUS)values(?,?,?,?,?,?)");
    		lif = vf.listIterator();
    		while(lif.hasNext()) {
    			String brs = (String)lif.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String target_kdpst = st.nextToken();
    			String target_kamps = st.nextToken();
    			String info_verificator = st.nextToken();
    			String verificator_id = st.nextToken();
    			stmt.setString(1, target_thsms);
    			stmt.setString(2, target_kdpst);
    			stmt.setString(3, info_verificator);
    			stmt.setString(4, verificator_id);
    			stmt.setBoolean(5, false);
    			stmt.setString(6, target_kamps);
    			stmt.executeUpdate();
    			
    		}
    	}
    	//catch (NamingException e) {
    	//	e.printStackTrace();
    	//}
    	//catch (SQLException ex) {
    	//	ex.printStackTrace();
    	//}
    	catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	return "";
    }
    
    public String updateKelasKuliahRulesUrutan(String[]urutan_job,String[]job,String[]prodi,String[]kmp, String target_thsms) {
    	
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		String list_kdpst = new String();
			String list_kmp = new String();
			Vector v_tmp = new Vector();
			ListIterator lit = v_tmp.listIterator();
			for(int i=0;i<prodi.length;i++) {
				StringTokenizer st = new StringTokenizer(prodi[i],"`");
				while(st.hasMoreTokens()) {
					lit.add(st.nextToken());
				}
			}
			v_tmp = Tool.removeDuplicateFromVector(v_tmp);
			lit = v_tmp.listIterator();
			while(lit.hasNext()) {
				list_kdpst = list_kdpst+(String)lit.next();
				if(lit.hasNext()) {
					list_kdpst = list_kdpst+"`";
				}
			}
			v_tmp = new Vector();
			lit = v_tmp.listIterator();
			for(int i=0;i<kmp.length;i++) {
				StringTokenizer st = new StringTokenizer(kmp[i],"`");
				while(st.hasMoreTokens()) {
					lit.add(st.nextToken());
				}
			}
			v_tmp = Tool.removeDuplicateFromVector(v_tmp);
			lit = v_tmp.listIterator();
			while(lit.hasNext()) {
				list_kmp = list_kmp+(String)lit.next();
				if(lit.hasNext()) {
					list_kmp = list_kmp+"`";
				}
			}
    		//System.out.println("list_prodi="+list_kdpst);
    		//System.out.println("list_kmp="+list_kmp);
    		StringTokenizer stp = new StringTokenizer(list_kdpst,"`");
    		
    		//delete prev record
    		stmt = con.prepareStatement("delete from KELAS_PERKULIAHAN_RULES where THSMS=? and KDPST=? and KODE_KAMPUS=?");
    		while(stp.hasMoreTokens()) {
    			stmt.setString(1, target_thsms);
    			stmt.setString(2, stp.nextToken());
    			StringTokenizer stk = new StringTokenizer(list_kmp,"`");
    			while(stk.hasMoreTokens()) {
    				stmt.setString(3, stk.nextToken());
    				stmt.executeUpdate();
    			}
    		}
    		
    		//process jabatan - get singkatan kali ada
    		stmt = con.prepareStatement("select SINGKATAN from JABATAN where NAMA_JABATAN=? and AKTIF=?");
    		String list_jabatan_short = new String();
    		String list_jabatan = new String();
    		
    		if(urutan_job!=null && urutan_job.length>0) {
    			/*
    			 * KHUSUS UNTUK FORM URUTAN
    			 */
    			String[] tmp = new String[job.length];
        		for(int i=0;i<urutan_job.length;i++) {
        			if(Integer.parseInt(urutan_job[i])>0) {
        				//System.out.println(i+".  "+urutan_job[i]+"~"+job[i]);
        				tmp[Integer.parseInt(urutan_job[i])-1]=new String(job[i]);
        			}
        		}	
        		int new_size = 0;
        		for(int i=0;i<tmp.length && tmp[i]!=null;i++) {
        			new_size++;
        		}
        		job = new String[new_size];
        		//System.out.println("nusie="+new_size);
        		for(int i=0;i<tmp.length && tmp[i]!=null;i++) {
        			job[i]= new String(tmp[i]);
        		}
    		}
    			
    		for(int i=0;i<job.length;i++) {	
    			//System.out.println(i+".job[]="+job[i]);
    			stmt.setString(1, job[i]);
    			list_jabatan=list_jabatan+"`"+job[i];
    			stmt.setBoolean(2, true);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				String singkatan = ""+rs.getString(1);
    				if(Checker.isStringNullOrEmpty(singkatan)) {
    					list_jabatan_short=list_jabatan_short+"["+job[i]+"]";
    				}
    				else {
    					list_jabatan_short=list_jabatan_short+"["+singkatan+"]";
    				}
    			}
    			else {
    				list_jabatan_short=list_jabatan_short+"[null]"; //error 
    			}
    		}
    		//get id active untuk tiap jabatan
    		stmt = con.prepareStatement("select STRUKTURAL.OBJID from STRUKTURAL inner join JABATAN on NM_JOB=NAMA_JABATAN where NM_JOB=? and KDKMP=? and KDPST=? and STRUKTURAL.AKTIF=?");
    		Vector vf = new Vector();
    		ListIterator lif = vf.listIterator();
    		String tmp_list_job = "";
    		StringTokenizer stt = new StringTokenizer(list_jabatan,"`");
    		while(stt.hasMoreTokens()) {
    			tmp_list_job = tmp_list_job+"["+stt.nextToken()+"]";
    		}
    		stp = new StringTokenizer(list_kdpst,"`");
    		while(stp.hasMoreTokens()) {
    			
    			String target_prodi = stp.nextToken();
    			
    			StringTokenizer stk = new StringTokenizer(list_kmp,"`");
    			while(stk.hasMoreTokens()) {
    				String baris = new String();
    				baris = baris + "`"+target_prodi;
    				String target_kmp = stk.nextToken();
    				baris = baris + "`"+target_kmp+"`"+list_jabatan_short+"`";
    				StringTokenizer st3 = new StringTokenizer(list_jabatan,"`");
    				String tmp = new String();
    				while(st3.hasMoreTokens()) {
    					String nama_jabatan = st3.nextToken();
    					stmt.setString(1, nama_jabatan);
    					stmt.setString(2, target_kmp);
    					stmt.setString(3, target_prodi);
    					stmt.setBoolean(4, true);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						boolean first = true;
    						do {
    							String id = ""+rs.getLong(1);
    							if(first) {
    								first = false;
    								tmp = new String(id);
    							}
    							else {
    								tmp = tmp+"`"+id;
    							}
    						}
    						while(rs.next());
    					}
    					else {
    						tmp = "null";
    					}
    					tmp = "["+tmp+"]";
    					baris = baris+tmp;
    				}
    				
    				lif.add(baris);
    			}
    		}
    		
    		
    		//insert new one
    		stmt = con.prepareStatement("insert into KELAS_PERKULIAHAN_RULES(THSMS,KDPST,TKN_JABATAN_VERIFICATOR,TKN_VERIFICATOR_ID,URUTAN,KODE_KAMPUS)values(?,?,?,?,?,?)");
    		lif = vf.listIterator();
    		while(lif.hasNext()) {
    			String brs = (String)lif.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String target_kdpst = st.nextToken();
    			String target_kamps = st.nextToken();
    			String info_verificator = st.nextToken();
    			String verificator_id = st.nextToken();
    			stmt.setString(1, target_thsms);
    			stmt.setString(2, target_kdpst);
    			stmt.setString(3, info_verificator);
    			stmt.setString(4, verificator_id);
    			stmt.setBoolean(5, true);
    			stmt.setString(6, target_kamps);
    			stmt.executeUpdate();
    			
    		}
    		
    	}
    	//catch (NamingException e) {
    	//	e.printStackTrace();
    	//}
    	//catch (SQLException ex) {
    	//	ex.printStackTrace();
    	//}
    	catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	return "";
    }
    
    
    public String updateTableRules(String[]urutan_job,String[]job,String[]prodi,String[]kmp, String target_thsms, String full_name_table_rules) {
    	
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		String list_kdpst = new String();
			String list_kmp = new String();
			Vector v_tmp = new Vector();
			ListIterator lit = v_tmp.listIterator();
			//get distinct kdpst
			for(int i=0;i<prodi.length;i++) {
				StringTokenizer st = new StringTokenizer(prodi[i],"`");
				while(st.hasMoreTokens()) {
					lit.add(st.nextToken());
				}
			}
			v_tmp = Tool.removeDuplicateFromVector(v_tmp);
			//convert jadi token string
			lit = v_tmp.listIterator();
			while(lit.hasNext()) {
				list_kdpst = list_kdpst+(String)lit.next();
				if(lit.hasNext()) {
					list_kdpst = list_kdpst+"`";
				}
			}
			//end get distinct kdpst
			v_tmp = new Vector();
			lit = v_tmp.listIterator();
			for(int i=0;i<kmp.length;i++) {
				StringTokenizer st = new StringTokenizer(kmp[i],"`");
				while(st.hasMoreTokens()) {
					lit.add(st.nextToken());
				}
			}
			v_tmp = Tool.removeDuplicateFromVector(v_tmp);
			lit = v_tmp.listIterator();
			while(lit.hasNext()) {
				list_kmp = list_kmp+(String)lit.next();
				if(lit.hasNext()) {
					list_kmp = list_kmp+"`";
				}
			}
    		//System.out.println("list_prodi="+list_kdpst);
    		//System.out.println("full_name_table_rules="+full_name_table_rules);
    		StringTokenizer stp = new StringTokenizer(list_kdpst,"`");
    		
    		//delete prev record
    		stmt = con.prepareStatement("delete from "+full_name_table_rules+" where THSMS=? and KDPST=? and KODE_KAMPUS=?");
    		while(stp.hasMoreTokens()) {
    			stmt.setString(1, target_thsms);
    			stmt.setString(2, stp.nextToken());
    			StringTokenizer stk = new StringTokenizer(list_kmp,"`");
    			while(stk.hasMoreTokens()) {
    				stmt.setString(3, stk.nextToken());
    				stmt.executeUpdate();
    			}
    		}
    		
    		//process jabatan - get singkatan kali ada
    		//NOTE: SETIAP JABATAN HARUS ADA DI TABEL JABATAN
    		stmt = con.prepareStatement("select SINGKATAN from JABATAN where NAMA_JABATAN=? and AKTIF=?");
    		String list_jabatan_short = new String();
    		String list_jabatan = new String();
    		
    		if(urutan_job!=null && urutan_job.length>0) {
    			/*
    			 * KHUSUS UNTUK FORM URUTAN
    			 */
    			String[] tmp = new String[job.length];
        		for(int i=0;i<urutan_job.length;i++) {
        			if(Integer.parseInt(urutan_job[i])>0) {
        				//System.out.println(i+".  "+urutan_job[i]+"~"+job[i]);
        				tmp[Integer.parseInt(urutan_job[i])-1]=new String(job[i]);
        			}
        		}	
        		int new_size = 0;
        		for(int i=0;i<tmp.length && tmp[i]!=null;i++) {
        			new_size++;
        		}
        		job = new String[new_size];
        		//System.out.println("nusie="+new_size);
        		for(int i=0;i<tmp.length && tmp[i]!=null;i++) {
        			job[i]= new String(tmp[i]);
        		}
    		}
    			
    		for(int i=0;i<job.length;i++) {	
    			//System.out.println(i+".job[]="+job[i]);
    			stmt.setString(1, job[i]);
    			list_jabatan=list_jabatan+"`"+job[i];
    			stmt.setBoolean(2, true);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				//System.out.println("exected");
    				String singkatan = ""+rs.getString(1);
    				if(Checker.isStringNullOrEmpty(singkatan)) {
    					list_jabatan_short=list_jabatan_short+"["+job[i]+"]";
    				}
    				else {
    					list_jabatan_short=list_jabatan_short+"["+singkatan+"]";
    				}
    			}
    			else {
    				list_jabatan_short=list_jabatan_short+"[null]"; //error 
    			}
    			//System.out.println("list_jabatan_short="+list_jabatan_short);
				
    		}
    		//get id active untuk tiap jabatan
    		stmt = con.prepareStatement("select STRUKTURAL.OBJID from STRUKTURAL inner join JABATAN on NM_JOB=NAMA_JABATAN where NM_JOB=? and KDKMP=? and KDPST=? and STRUKTURAL.AKTIF=?");
    		Vector vf = new Vector();
    		ListIterator lif = vf.listIterator();
    		String tmp_list_job = "";
    		StringTokenizer stt = new StringTokenizer(list_jabatan,"`");
    		while(stt.hasMoreTokens()) {
    			tmp_list_job = tmp_list_job+"["+stt.nextToken()+"]";
    		}
    		stp = new StringTokenizer(list_kdpst,"`");
    		while(stp.hasMoreTokens()) {
    			
    			String target_prodi = stp.nextToken();
    			
    			StringTokenizer stk = new StringTokenizer(list_kmp,"`");
    			while(stk.hasMoreTokens()) {
    				String baris = new String();
    				baris = baris + "`"+target_prodi;
    				String target_kmp = stk.nextToken();
    				baris = baris + "`"+target_kmp+"`"+list_jabatan_short+"`";
    				StringTokenizer st3 = new StringTokenizer(list_jabatan,"`");
    				String tmp = new String();
    				while(st3.hasMoreTokens()) {
    					String nama_jabatan = st3.nextToken();
    					stmt.setString(1, nama_jabatan);
    					stmt.setString(2, target_kmp);
    					stmt.setString(3, target_prodi);
    					stmt.setBoolean(4, true);
    					//System.out.println(nama_jabatan+"`"+target_kmp+"`"+target_prodi);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						boolean first = true;
    						boolean jabatan_non_ketua = true;
    						/*JABATAN KETUA BISA DOBEL, HARUS NGASIH WARNING BILA JABATAN KETUA DOBEL
    						if(nama_jabatan.toLowerCase().contains("ketua")||nama_jabatan.toLowerCase().contains("kepala")) {
    							jabatan_non_ketua = false;
    						}
    						*/
    						do {
    							String id = ""+rs.getLong(1);
    							if(first) {
    								first = false;
    								tmp = new String(id);
    							}
    							else {
    								//tmp = tmp+"`"+id;
    								tmp = tmp+","+id;
    							}
    						}
    						while(rs.next() && jabatan_non_ketua);
    					}
    					else {
    						tmp = "null";
    					}
    					tmp = "["+tmp+"]";
    					baris = baris+tmp;
    				}
    				
    				lif.add(baris);
    				//System.out.println("baris="+baris);
    			}
    		}
    		
    		
    		//insert new one
    		stmt = con.prepareStatement("insert into "+full_name_table_rules+"(THSMS,KDPST,TKN_JABATAN_VERIFICATOR,TKN_VERIFICATOR_ID,URUTAN,KODE_KAMPUS)values(?,?,?,?,?,?)");
    		lif = vf.listIterator();
    		while(lif.hasNext()) {
    			String brs = (String)lif.next();
    			//System.out.println("baris="+brs);
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String target_kdpst = st.nextToken();
    			String target_kamps = st.nextToken();
    			String info_verificator = st.nextToken();
    			if(!Checker.isStringNullOrEmpty(info_verificator)) {
    				if(!info_verificator.endsWith("]")) {
    					info_verificator = info_verificator.trim()+"]";
    				}
    			}
    			String verificator_id = st.nextToken();
    			//System.out.println("verificator_id="+verificator_id);
    			if(!Checker.isStringNullOrEmpty(verificator_id)) {
    				if(!verificator_id.endsWith("]")) {
    					verificator_id = verificator_id.trim()+"]";
    				}
    			}
    			stmt.setString(1, target_thsms);
    			stmt.setString(2, target_kdpst);
    			stmt.setString(3, info_verificator);
    			/*
    			if(verificator_id!=null) {
    				while(verificator_id.contains("-")) {
        				verificator_id = verificator_id.replace("-", "`");
        			}	
    			}
    			*/
    			stmt.setString(4, verificator_id);
    			stmt.setBoolean(5, true);
    			stmt.setString(6, target_kamps);
    			stmt.executeUpdate();
    			
    		}
    		
    		//update TOPIK_PENGAJUAN utk transaksi yg blum ke lock
    		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set TOKEN_TARGET_OBJ_NICKNAME=?,TOKEN_TARGET_OBJID=?,SHOW_AT_TARGET=?,APPROVED=? where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_KDPST=? and LOCKED=? and BATAL=?");
    		lif = vf.listIterator();
    		while(lif.hasNext()) {
    			String brs = (String)lif.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String target_kdpst = st.nextToken();
    			String target_kamps = st.nextToken();
    			String info_verificator = st.nextToken();
    			if(!Checker.isStringNullOrEmpty(info_verificator)) {
    				if(!info_verificator.endsWith("]")) {
    					info_verificator = info_verificator.trim()+"]";
    				}
    			}
    			String verificator_id = st.nextToken();
    			/*
    			if(verificator_id!=null) {
    				while(verificator_id.contains("-")) {
        				verificator_id = verificator_id.replace("-", "`");
        			}	
    			}
    			*/
    			if(!Checker.isStringNullOrEmpty(verificator_id)) {
    				if(!verificator_id.endsWith("]")) {
    					verificator_id = verificator_id.trim()+"]";
    				}
    			}
    			stmt.setString(1, info_verificator);
    			stmt.setString(2, verificator_id);
    			stmt.setString(3, verificator_id);
    			stmt.setNull(4, java.sql.Types.VARCHAR);
    			stmt.setString(5, target_thsms);
    			stmt.setString(6, full_name_table_rules.replace("_RULES", ""));
    			stmt.setString(7, target_kdpst);
    			stmt.setBoolean(8, false);
    			stmt.setBoolean(9, false);
    			stmt.executeUpdate();
    			
    		}
    		
    	}
    	//catch (NamingException e) {
    	//	e.printStackTrace();
    	//}
    	//catch (SQLException ex) {
    	//	ex.printStackTrace();
    	//}
    	catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	return "";
    }
    
    public void updateChatGroup(String[]job,String[]prodi,String[]kmp, String target_gorup_chat_id) {
    	
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		//System.out.println("start");
    		//System.out.println(job[0]);
    		//System.out.println(prodi[0]);
    		//System.out.println(kmp[0]);
    		//System.out.println(target_gorup_chat_id);
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		Vector v = new Vector();
    		ListIterator li = v.listIterator();

    		//1. get siapa yg menjabat
    		stmt = con.prepareStatement("select OBJID,NPMHSMSMHS from STRUKTURAL inner join CIVITAS on OBJID=ID_OBJ where NM_JOB=? and KDPST=? and KDKMP=?");
    		for(int i=0;i<job.length;i++) {
    			StringTokenizer st1 = new StringTokenizer(job[i],"`");
    			while(st1.hasMoreTokens()) {
    				String tkn_job = st1.nextToken();
    				stmt.setString(1, tkn_job);
        			for(int j=0;j<prodi.length;j++) {
        				StringTokenizer st2 = new StringTokenizer(prodi[j],"`");
            			while(st2.hasMoreTokens()) {
            				String tkn_prodi = st2.nextToken();
            				stmt.setString(2, tkn_prodi);
            				for(int k=0;k<kmp.length;k++) {
            					StringTokenizer st3 = new StringTokenizer(kmp[k],"`");
                    			while(st3.hasMoreTokens()) {
                    				String tkn_kmp = st3.nextToken();
                    				stmt.setString(3, tkn_kmp);
                    				rs = stmt.executeQuery();
                    				//System.out.println(tkn_job+"-"+tkn_prodi+"-"+tkn_kmp);
                					while(rs.next()) {
                						String npmhs = rs.getString("NPMHSMSMHS");
                						long objid = rs.getLong("OBJID");
                						li.add(npmhs);
                					}	
                    			}
            	    		}		
            			}
            		}
    			}
    				
    		}
    		//System.out.println("v.size="+v.size());
    		if(v.size()>0) {
    			v = Tool.removeDuplicateFromVector(v);
        		li = v.listIterator();
        		String list_npm = "";
        		
        		while(li.hasNext()) {
        			String npm = (String)li.next();
        			list_npm = list_npm+npm;
        			if(li.hasNext()) {
        				list_npm = list_npm+",";
        			}
        			
        		}
        		//System.out.println("list_npm="+list_npm);
        		Getter.readJsonArrayFromUrl("/v1/citcat/group/upd/struktural_group/"+target_gorup_chat_id+"/list_npm_member/"+list_npm);
        		
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

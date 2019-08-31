package beans.dbase.spmi.riwayat.ami;

import beans.dbase.SearchDb;
import beans.dbase.spmi.ToolSpmi;
import beans.tools.Checker;
import beans.tools.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
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
 * Session Bean implementation class SearchAmi
 */
@Stateless
@LocalBean
public class SearchAmi extends SearchDb {
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
    public SearchAmi() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchAmi(String operatorNpm) {
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
    public SearchAmi(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    //deprecated pake yg dibawah
    public Vector getRiwayatAmi(String kdpst) {
    	Vector v = new Vector();
    	ListIterator li=v.listIterator();
    	li.add("ignore`"+kdpst);
    	v= getRiwayatAmi(v,false, false);
    	/*
    	try {
        		//String ipAddr =  request.getRemoteAddr();
    		
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT ID,KDPST,NAMA_KEGIATAN_AMI,TGL_RENCANA_AMI,KETUA_TIM,TKN_ANGGOTA_TIM,TKN_CAKUPAN_ID_MASTER_STD,TGL_RIL_AMI,TKN_CAKUPAN_MASTER_STD,TGL_RIL_AMI_DONE FROM AUDIT_MUTU_INTERNAL where KDPST=? order by TGL_RENCANA_AMI desc";
        	stmt = con.prepareStatement(sql);
        	stmt.setString(1, kdpst);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		ListIterator li = v.listIterator();
        		do {
        			int i=1;
        			String id = rs.getString(i++);
        			kdpst = rs.getString(i++);
        			String nm_act = rs.getString(i++);
        			String tgl_plan = rs.getString(i++);
        			String ketua = rs.getString(i++);
        			String anggota = rs.getString(i++);
        			String id_cakupan_std = rs.getString(i++);
        			String tgl_ril = rs.getString(i++);
        			String ket_cakupan_std = rs.getString(i++);
        			String tgl_ril_done = rs.getString(i++);
        			String tmp = id+"~"+kdpst+"~"+nm_act+"~"+tgl_plan+"~"+ketua+"~"+anggota+"~"+id_cakupan_std+"~"+tgl_ril+"~"+ket_cakupan_std+"~"+tgl_ril_done;
        			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        			//System.out.println("tmp = "+tmp);
        			li.add(tmp);
        		}
        		while(rs.next());
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
    	*/
    	return v;
    }
    
    
    public Vector getRiwayatAmi(Vector v_scope_kdpst_spmi, boolean yg_telah_selesai_only, boolean yg_sudah_dimulai_only) {
    	Vector v = null;
    	try {
        		//String ipAddr =  request.getRemoteAddr();
    		
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//String sql = "SELECT ID,KDPST,NAMA_KEGIATAN_AMI,TGL_RENCANA_AMI,KETUA_TIM,TKN_ANGGOTA_TIM,TKN_CAKUPAN_ID_MASTER_STD,TGL_RIL_AMI,TKN_CAKUPAN_MASTER_STD,TGL_RIL_AMI_DONE FROM AUDIT_MUTU_INTERNAL where KDPST=? order by TGL_RENCANA_AMI desc";
        	
        	if(v_scope_kdpst_spmi!=null && v_scope_kdpst_spmi.size()>0) {
        		ListIterator lis = v_scope_kdpst_spmi.listIterator();
        		boolean masuk_scope=false;
        		String scope = " (";
        		while(lis.hasNext()) {
        			String brs=(String)lis.next();
        			String kdpst = Tool.getTokenKe(brs, 2, "`");
        			scope = scope+"KDPST='"+kdpst+"'";
        			if(lis.hasNext()) {
        				scope = scope + " OR ";
        			}
        			else {
        				scope = scope + ")";
        			}
        		}
        		String sql = "";
        		
            	if(yg_telah_selesai_only) {
            		sql = "SELECT ID,KDPST,NAMA_KEGIATAN_AMI,TGL_RENCANA_AMI,KETUA_TIM,TKN_ANGGOTA_TIM,TKN_CAKUPAN_ID_MASTER_STD,TGL_RIL_AMI,TKN_CAKUPAN_MASTER_STD,TGL_RIL_AMI_DONE FROM AUDIT_MUTU_INTERNAL inner join MSPST on KDPST=KDPSTMSPST where TGL_RIL_AMI_DONE is not null and "+scope+" order by KDPST,TGL_RIL_AMI";
            	}
            	else if(yg_sudah_dimulai_only) {
            		sql = "SELECT ID,KDPST,NAMA_KEGIATAN_AMI,TGL_RENCANA_AMI,KETUA_TIM,TKN_ANGGOTA_TIM,TKN_CAKUPAN_ID_MASTER_STD,TGL_RIL_AMI,TKN_CAKUPAN_MASTER_STD,TGL_RIL_AMI_DONE FROM AUDIT_MUTU_INTERNAL inner join MSPST on KDPST=KDPSTMSPST where TGL_RIL_AMI is not null and "+scope+" order by KDPST,TGL_RIL_AMI";
            	}
            	else {
            		sql = "SELECT ID,KDPST,NAMA_KEGIATAN_AMI,TGL_RENCANA_AMI,KETUA_TIM,TKN_ANGGOTA_TIM,TKN_CAKUPAN_ID_MASTER_STD,TGL_RIL_AMI,TKN_CAKUPAN_MASTER_STD,TGL_RIL_AMI_DONE FROM AUDIT_MUTU_INTERNAL inner join MSPST on KDPST=KDPSTMSPST where "+scope+" order by KDPST,TGL_RIL_AMI";
            	}
            	
            	stmt = con.prepareStatement(sql);
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		v = new Vector();
            		ListIterator li = v.listIterator();
            		do {
            			int i=1;
            			String id = rs.getString(i++);
            			String kdpst = rs.getString(i++);
            			String nm_act = rs.getString(i++);
            			String tgl_plan = rs.getString(i++);
            			String ketua = rs.getString(i++);
            			String anggota = rs.getString(i++);
            			String id_cakupan_std = rs.getString(i++);
            			String tgl_ril = rs.getString(i++);
            			String ket_cakupan_std = rs.getString(i++);
            			String tgl_ril_done = rs.getString(i++);
            			String tmp = id+"~"+kdpst+"~"+nm_act+"~"+tgl_plan+"~"+ketua+"~"+anggota+"~"+id_cakupan_std+"~"+tgl_ril+"~"+ket_cakupan_std+"~"+tgl_ril_done;
            			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            			//System.out.println("tmp = "+tmp);
            			li.add(tmp);
            		}
            		while(rs.next());
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
    	return v;
    }

    
    public Vector getOverviewRiwayatAmi(Vector v_scope_kdpst_spmi) {
    	Vector v = null;
    	try {
        		//String ipAddr =  request.getRemoteAddr();
    		
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//String sql = "SELECT ID,KDPST,NAMA_KEGIATAN_AMI,TGL_RENCANA_AMI,KETUA_TIM,TKN_ANGGOTA_TIM,TKN_CAKUPAN_ID_MASTER_STD,TGL_RIL_AMI,TKN_CAKUPAN_MASTER_STD,TGL_RIL_AMI_DONE FROM AUDIT_MUTU_INTERNAL where KDPST=? order by TGL_RENCANA_AMI desc";
        	
        	if(v_scope_kdpst_spmi!=null && v_scope_kdpst_spmi.size()>0) {
        		ListIterator lis = v_scope_kdpst_spmi.listIterator();
        		boolean masuk_scope=false;
        		String scope = " (";
        		while(lis.hasNext()) {
        			String brs=(String)lis.next();
        			String kdpst = Tool.getTokenKe(brs, 2, "`");
        			scope = scope+"B.KDPST='"+kdpst+"'";
        			if(lis.hasNext()) {
        				scope = scope + " OR ";
        			}
        			else {
        				scope = scope + ")";
        			}
        		}
        		String sql = "";
        		
            	sql="select C.*,NMPSTMSPST,KODE_JENJANG from " +
            			"("+ 
            			"SELECT " + 
            			"	B.KDPST,B.ID as ID_AMI,B.TGL_RIL_AMI_DONE,A.ID_MASTER_STD,A.NAMA_MASTER_STD,SUM(A.TOT_QUESTION) as TOT_QUESTION, SUM(A.TOT_HASIL_PENILAIAN) as TOT_PENILAIAN, SUM(A.MAX_NILAI_PENILAIAN) as MAX_NILAI" + 
            			"    FROM AUDIT_MUTU_INTERNAL_NILAI_AKHIR as A" + 
            			"	inner join AUDIT_MUTU_INTERNAL B on A.ID_AMI=B.ID where " +scope+ 
            			"	group by B.ID,A.ID_MASTER_STD,A.NAMA_MASTER_STD" + 
            			"    order by B.KDPST,A.ID_MASTER_STD,B.TGL_RIL_AMI_DONE desc" +
            			") C inner join MSPST on C.KDPST=KDPSTMSPST ";
            			
            	
            	stmt = con.prepareStatement(sql);
            	//System.out.println("sql="+sql);
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		v = new Vector();
            		ListIterator li = v.listIterator();
            		do {
            			int i=1;
            			String kdpst = rs.getString(i++);
            			String id_ami = rs.getString(i++);
            			String tgl_done = rs.getString(i++);
            			String id_master = rs.getString(i++);
            			String nm_master = rs.getString(i++);
            			String tot_qa = rs.getString(i++);
            			String tot_nilai = rs.getString(i++);
            			String max_nilai = rs.getString(i++);
            			String nmpst = rs.getString(i++);
            			String kdjen = rs.getString(i++);
            			
            			String tmp = kdpst+"~"+id_ami+"~"+tgl_done+"~"+id_master+"~"+nm_master+"~"+tot_qa+"~"+tot_nilai+"~"+max_nilai+"~"+nmpst+"~"+kdjen;
            			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            			//System.out.println("tmp = "+tmp);
            			li.add(tmp);
            			//with screening older value
            			while(rs.next()) {
            				i=1;
            				String next_kdpst = rs.getString(i++);
                			String next_id_ami = rs.getString(i++);
                			String next_tgl_done = rs.getString(i++);
                			String next_id_master = rs.getString(i++);
                			String next_nm_master = rs.getString(i++);
                			String next_tot_qa = rs.getString(i++);
                			String next_tot_nilai = rs.getString(i++);
                			String next_max_nilai = rs.getString(i++);
                			String next_nmpst = rs.getString(i++);
                			String next_kdjen = rs.getString(i++);
                			if(next_kdpst.equalsIgnoreCase(kdpst) && next_id_master.equalsIgnoreCase(id_master)) {
                				//older value maka ignore
                			}
                			else {
                				tmp = next_kdpst+"~"+next_id_ami+"~"+next_tgl_done+"~"+next_id_master+"~"+next_nm_master+"~"+next_tot_qa+"~"+next_tot_nilai+"~"+next_max_nilai+"~"+next_nmpst+"~"+next_kdjen;
                    			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
                    			li.add(tmp);
                    			//System.out.println("tmp = "+tmp);
                    			//replace with nu value
                    			kdpst = new String(next_kdpst);
                    			id_ami = new String(next_id_ami);
                    			tgl_done = new String(next_tgl_done);
                    			id_master = new String(next_id_master);
                    			nm_master = new String(next_nm_master);
                    			tot_qa = new String(next_tot_qa);
                    			tot_nilai = new String(next_tot_nilai);
                    			max_nilai = new String(next_max_nilai);
                    			nmpst = new String(next_nmpst);
                    			kdjen = new String(next_kdjen);
                			}
            			}
            		}
            		while(rs.next());
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
    	return v;
    }
    
    
    public Vector getOverviewRiwayatAmiByMasterId(Vector v_scope_kdpst_spmi, int id_master_std) {
    	Vector v = null;
    	try {
        		//String ipAddr =  request.getRemoteAddr();
    		
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//String sql = "SELECT ID,KDPST,NAMA_KEGIATAN_AMI,TGL_RENCANA_AMI,KETUA_TIM,TKN_ANGGOTA_TIM,TKN_CAKUPAN_ID_MASTER_STD,TGL_RIL_AMI,TKN_CAKUPAN_MASTER_STD,TGL_RIL_AMI_DONE FROM AUDIT_MUTU_INTERNAL where KDPST=? order by TGL_RENCANA_AMI desc";
        	
        	if(v_scope_kdpst_spmi!=null && v_scope_kdpst_spmi.size()>0) {
        		ListIterator lis = v_scope_kdpst_spmi.listIterator();
        		boolean masuk_scope=false;
        		String scope = " (";
        		while(lis.hasNext()) {
        			String brs=(String)lis.next();
        			String kdpst = Tool.getTokenKe(brs, 2, "`");
        			scope = scope+"C.KDPST='"+kdpst+"'";
        			if(lis.hasNext()) {
        				scope = scope + " OR ";
        			}
        			else {
        				scope = scope + ")";
        			}
        		}
        		String sql = "";
        		
            	sql="select F.*,NMPSTMSPST,KODE_JENJANG from " + 
            			"(" + 
            			"	SELECT C.KDPST,A.ID_AMI,A.ID_MASTER_STD,A.ID_STD,A.ID_TIPE_STD,A.NAMA_MASTER_STD,A.KET_TIPE_STD,sum(A.BOBOT),count(B.ID_STD_ISI) as TOT_QUE,C.TGL_RIL_AMI_DONE" + 
            			"	FROM " + 
            			"	AUDIT_MUTU_INTERNAL C" + 
            			"	inner join " + 
            			"    (" + 
            			"		select X.*,Y.KET_TIPE_STD from AUDIT_MUTU_INTERNAL_HASIL X" + 
            			"        inner join STANDARD_TABLE Y on (X.ID_MASTER_STD=Y.ID_MASTER_STD and X.ID_TIPE_STD=Y.ID_TIPE_STD)   " + 
            			"	) A on C.ID=A.ID_AMI" + 
            			"	inner join " + 
            			"	STANDARD_ISI_QUESTION B on A.ID_QUESTION=B.ID" + 
            			"    where "+scope+" AND A.ID_MASTER_STD=? and C.TGL_RIL_AMI_DONE is not null" + 
            			"    group by C.ID,A.ID_MASTER_STD,A.NAMA_MASTER_STD,A.ID_STD,A.ID_TIPE_STD,A.KET_TIPE_STD" + 
            			"    order by C.KDPST,A.ID_STD,A.ID_MASTER_STD,C.TGL_RIL_AMI_DONE desc,A.ID_AMI desc" + 
            			") F inner join MSPST on F.KDPST=KDPSTMSPST ";
            			
            	//System.out.println(sql);
            	stmt = con.prepareStatement(sql);
            	stmt.setInt(1, id_master_std);
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		v = new Vector();
            		ListIterator li = v.listIterator();
            		do {
            			int i=1;
            			
            			String kdpst = rs.getString(i++);
            			String id_ami = rs.getString(i++);
            			String id_master = rs.getString(i++);
            			String id_std = rs.getString(i++);
            			String id_tipe = rs.getString(i++);
            			String nm_master = rs.getString(i++);
            			String nm_std = rs.getString(i++);
            			String tot_nilai = rs.getString(i++);
            			String tot_qa = rs.getString(i++);
            			String tgl_done = rs.getString(i++);
            			String nmpst = rs.getString(i++);
            			String kdjen = rs.getString(i++);
            			
            			
            			String tmp = kdpst+"~"+id_ami+"~"+id_master+"~"+id_std+"~"+id_tipe+"~"+nm_master+"~"+nm_std+"~"+tot_nilai+"~"+tot_qa+"~"+tgl_done+"~"+nmpst+"~"+kdjen+"~"+0;
            			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
            			//System.out.println("tmp = "+tmp);
            			li.add(tmp);
            			//with screening older value
            			while(rs.next()) {
            				i=1;
            				String next_kdpst = rs.getString(i++);
                			String next_id_ami = rs.getString(i++);
                			String next_id_master = rs.getString(i++);
                			String next_id_std = rs.getString(i++);
                			String next_id_tipe = rs.getString(i++);
                			String next_nm_master = rs.getString(i++);
                			String next_nm_std = rs.getString(i++);
                			String next_tot_nilai = rs.getString(i++);
                			String next_tot_qa = rs.getString(i++);
                			String next_tgl_done = rs.getString(i++);
                			String next_nmpst = rs.getString(i++);
                			String next_kdjen = rs.getString(i++);
                			if(next_kdpst.equalsIgnoreCase(kdpst) && next_id_std.equalsIgnoreCase(id_std)) {
                				//older value maka ignore
                			}
                			else {
                				tmp = next_kdpst+"~"+next_id_ami+"~"+next_id_master+"~"+next_id_std+"~"+next_id_tipe+"~"+next_nm_master+"~"+next_nm_std+"~"+next_tot_nilai+"~"+next_tot_qa+"~"+next_tgl_done+"~"+next_nmpst+"~"+next_kdjen+"~"+0;
                    			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
                    			//System.out.println("tmp1 = "+tmp);
                    			li.add(tmp);
                    			
                    			//replace with nu value
                    			kdpst = new String(next_kdpst);
                    			id_ami = new String(next_id_ami);
                    			id_master = new String(next_id_master);
                    			id_std = new String(next_id_std);
                    			id_tipe = new String(next_id_tipe);
                    			nm_master = new String(next_nm_master);
                    			nm_std = new String(next_nm_std);
                    			tot_nilai = new String(next_tot_nilai);
                    			tot_qa = new String(next_tot_qa);
                    			tgl_done = new String(next_tgl_done);
                    			nmpst = new String(next_nmpst);
                    			kdjen = new String(next_kdjen);
                			}
            			}
            		}
            		while(rs.next());
            		
            		//add max nilai
            		if(v!=null) {
            			li = v.listIterator();
            			sql = "SELECT A.KDPST,B.ID_STD,B.ID_STD_ISI,B.TKN_BOBOT" + 
            					" FROM" + 
            					"	AUDIT_MUTU_INTERNAL A " + 
            					"    inner join " + 
            					"    AUDIT_MUTU_INTERNAL_HASIL B on A.ID=B.ID_AMI" + 
            					"	where A.ID=? and ID_STD=?";
            			stmt = con.prepareStatement(sql);
            			//System.out.println(sql);
            			while(li.hasNext()) {
            				String brs = (String)li.next();
            				//System.out.println("-"+brs);
            				StringTokenizer st = new StringTokenizer(brs,"~");
            				String kdpst = st.nextToken();
                			String id_ami = st.nextToken();
                			String id_master = st.nextToken();
                			String id_std = st.nextToken();
                			String id_tipe = st.nextToken();
                			String nm_master = st.nextToken();
                			String nm_std = st.nextToken();
                			String tot_nilai = st.nextToken();
                			String tot_qa = st.nextToken();
                			String tgl_done = st.nextToken();
                			String nmpst = st.nextToken();
                			String kdjen = st.nextToken();
                			String nilai_max = st.nextToken();
                			stmt.setInt(1,Integer.parseInt(id_ami));
                			stmt.setInt(2,Integer.parseInt(id_std));
                			rs = stmt.executeQuery();
                			double max_nilai = 0;
                			if(rs.next()) {
                				do {
                    				String tkn_bobot = rs.getString(4);
                    				st = new StringTokenizer(tkn_bobot,"`");
                    				double max_bobot=0;
                    				while(st.hasMoreTokens()) {
                    					double val = Double.parseDouble(st.nextToken());
                    					if(val>max_bobot) {
                    						max_bobot = val;	
                    					}
                    					 
                    				}
                    				max_nilai = max_nilai+max_bobot;
                    			}
                				while(rs.next());
                			}
                			
                			li.set(kdpst+"~"+id_ami+"~"+id_master+"~"+id_std+"~"+id_tipe+"~"+nm_master+"~"+nm_std+"~"+tot_nilai+"~"+tot_qa+"~"+tgl_done+"~"+nmpst+"~"+kdjen+"~"+max_nilai);
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
    	return v;
    }
    

    
    public Vector previewAmiQandA(int id_master_std, boolean include_null_question, String target_kdpst) {
    	Vector v = null;
    	try {
        		//String ipAddr =  request.getRemoteAddr();
    		
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "";
        	if(include_null_question) {
        		//memang TANPA KDPST !!!!! karena ini preview umum
        		//sql = "select G.NORUT,F.IDQ,A.KODE,A.ID_MASTER_STD,B.ID_STD,B.ID_TIPE_STD,C.ID AS ID_STD_ISI,A.KET_TIPE_STD,B.KET_TIPE_STD,B.TGL_STA,B.TGL_END,C.PERNYATAAN_STD,C.NO_BUTIR,C.KDPST,C.RASIONALE,C.AKTIF,C.TGL_MULAI_AKTIF,C.TGL_STOP_AKTIF,C.SCOPE,C.TIPE_PROSES_PENGAWASAN,F.QUESTION,F.ANSWER,F.BOBOT from STANDARD_MASTER_TABLE A inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD inner join STANDARD_ISI_TABLE C on B.ID_STD=C.ID_STD left join (select D.ID as IDQ,ID_STD_ISI,QUESTION,ANSWER,BOBOT from STANDARD_ISI_QUESTION D inner join STANDARD_ISI_ANSWER E on D.ID=E.ID_QUESTION) as F on C.ID=F.ID_STD_ISI left join AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN G on (C.ID=G.ID_STD_ISI and F.IDQ=G.ID_QUESTION) where A.ID_MASTER_STD=? and (G.KDPST=? or G.KDPST is NULL)  order BY -G.NORUT desc,A.ID_MASTER_STD, B.ID_TIPE_STD";
        		/*
        		sql = "select NORUT,IDQ,A.KODE,A.ID_MASTER_STD,B.ID_STD,B.ID_TIPE_STD,C.ID AS ID_STD_ISI,A.KET_TIPE_STD,B.KET_TIPE_STD,B.TGL_STA,B.TGL_END,C.PERNYATAAN_STD,C.NO_BUTIR,C.KDPST,C.RASIONALE,C.AKTIF,C.TGL_MULAI_AKTIF,C.TGL_STOP_AKTIF,C.SCOPE,C.TIPE_PROSES_PENGAWASAN,F.QUESTION,F.ANSWER,F.BOBOT from STANDARD_MASTER_TABLE A inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD inner join STANDARD_ISI_TABLE C on B.ID_STD=C.ID_STD left join (select D.ID as IDQ,ID_STD_ISI,QUESTION,ANSWER,BOBOT from STANDARD_ISI_QUESTION D inner join STANDARD_ISI_ANSWER E on D.ID=E.ID_QUESTION) as F on C.ID=F.ID_STD_ISI left join AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN G on (C.ID=G.ID_STD_ISI and F.IDQ=G.ID_QUESTION) where A.ID_MASTER_STD=? and C.KDPST=? " + 
        				"union all " + 
        				"(select NORUT,IDQ,H.KODE,H.ID_MASTER_STD,I.ID_STD,I.ID_TIPE_STD,J.ID AS ID_STD_ISI,H.KET_TIPE_STD,I.KET_TIPE_STD,I.TGL_STA,I.TGL_END,J.PERNYATAAN_STD,J.NO_BUTIR,J.KDPST,J.RASIONALE,J.AKTIF,J.TGL_MULAI_AKTIF,J.TGL_STOP_AKTIF,J.SCOPE,J.TIPE_PROSES_PENGAWASAN,N.QUESTION,N.ANSWER,N.BOBOT from STANDARD_MASTER_TABLE H inner join STANDARD_TABLE I on H.ID_MASTER_STD=I.ID_MASTER_STD inner join STANDARD_ISI_TABLE J on I.ID_STD=J.ID_STD left join (select L.ID as IDQ,ID_STD_ISI,QUESTION,ANSWER,BOBOT from STANDARD_ISI_QUESTION L inner join STANDARD_ISI_ANSWER M on L.ID=M.ID_QUESTION) as N on J.ID=N.ID_STD_ISI left join AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN O on (J.ID=O.ID_STD_ISI and N.IDQ=O.ID_QUESTION) where H.ID_MASTER_STD=? and J.KDPST is null " + 
        				"AND NOT EXISTS (" + 
        				"select NORUT,IDQ,KODE,P.ID_MASTER_STD,Q.ID_STD,Q.ID_TIPE_STD,R.ID AS ID_STD_ISI,P.KET_TIPE_STD,Q.KET_TIPE_STD,Q.TGL_STA,Q.TGL_END,R.PERNYATAAN_STD,R.NO_BUTIR,R.KDPST,R.RASIONALE,R.AKTIF,R.TGL_MULAI_AKTIF,R.TGL_STOP_AKTIF,R.SCOPE,R.TIPE_PROSES_PENGAWASAN,U.QUESTION,U.ANSWER,U.BOBOT " + 
        				"from STANDARD_MASTER_TABLE P inner join STANDARD_TABLE Q on P.ID_MASTER_STD=Q.ID_MASTER_STD inner join STANDARD_ISI_TABLE R on Q.ID_STD=R.ID_STD left join (select S.ID as IDQ,ID_STD_ISI,QUESTION,ANSWER,BOBOT from STANDARD_ISI_QUESTION S inner join STANDARD_ISI_ANSWER T on S.ID=T.ID_QUESTION) as U on R.ID=U.ID_STD_ISI left join AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN V on (R.ID=V.ID_STD_ISI and U.IDQ=V.ID_QUESTION) where P.ID_MASTER_STD=? and R.KDPST=? order BY -NORUT desc,P.ID_MASTER_STD, Q.ID_TIPE_STD))";
        		*/		
        		//sql = "select NORUT,IDQ,A.KODE,A.ID_MASTER_STD,B.ID_STD,B.ID_TIPE_STD,C.ID AS ID_STD_ISI,A.KET_TIPE_STD,B.KET_TIPE_STD,B.TGL_STA,B.TGL_END,C.PERNYATAAN_STD,C.NO_BUTIR,C.KDPST,C.RASIONALE,C.AKTIF,C.TGL_MULAI_AKTIF,C.TGL_STOP_AKTIF,C.SCOPE,C.TIPE_PROSES_PENGAWASAN,F.QUESTION,F.ANSWER,F.BOBOT from STANDARD_MASTER_TABLE A inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD inner join STANDARD_ISI_TABLE C on B.ID_STD=C.ID_STD left join (select D.ID as IDQ,ID_STD_ISI,QUESTION,ANSWER,BOBOT from STANDARD_ISI_QUESTION D inner join STANDARD_ISI_ANSWER E on D.ID=E.ID_QUESTION order by IDQ,BOBOT) as F on C.ID=F.ID_STD_ISI left join AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN G on (C.ID=G.ID_STD_ISI and F.IDQ=G.ID_QUESTION) where A.ID_MASTER_STD=? and (C.KDPST=? or C.KDPST is NULL) order by PERNYATAAN_STD,KDPST desc";
        		//sql = "select NORUT,IDQ,A.KODE,A.ID_MASTER_STD,B.ID_STD,B.ID_TIPE_STD,C.ID AS ID_STD_ISI,A.KET_TIPE_STD,B.KET_TIPE_STD,B.TGL_STA,B.TGL_END,C.PERNYATAAN_STD,C.NO_BUTIR,C.KDPST,C.RASIONALE,C.AKTIF,C.TGL_MULAI_AKTIF,C.TGL_STOP_AKTIF,C.SCOPE,C.TIPE_PROSES_PENGAWASAN,F.QUESTION,F.ANSWER,F.BOBOT from STANDARD_MASTER_TABLE A inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD inner join STANDARD_ISI_TABLE C on B.ID_STD=C.ID_STD left join (select D.ID as IDQ,ID_STD_ISI,QUESTION,ANSWER,BOBOT from STANDARD_ISI_QUESTION D inner join STANDARD_ISI_ANSWER E on D.ID=E.ID_QUESTION order by IDQ,BOBOT) as F on C.ID=F.ID_STD_ISI left join AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN G on (C.ID=G.ID_STD_ISI and F.IDQ=G.ID_QUESTION) where A.ID_MASTER_STD=? and (C.KDPST=? or C.KDPST is NULL) order by PERNYATAAN_STD,KDPST desc";
        		sql = "select NORUT,IDQ,A.KODE,A.ID_MASTER_STD,B.ID_STD,B.ID_TIPE_STD,C.ID AS ID_STD_ISI,A.KET_TIPE_STD,B.KET_TIPE_STD,B.TGL_STA,B.TGL_END,C.PERNYATAAN_STD,C.NO_BUTIR,G.KDPST,C.RASIONALE,C.AKTIF,C.TGL_MULAI_AKTIF,C.TGL_STOP_AKTIF,C.SCOPE,C.TIPE_PROSES_PENGAWASAN,F.QUESTION,F.ANSWER,F.BOBOT " + 
        				"	from STANDARD_MASTER_TABLE A " + 
        				"	inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD " + 
        				"    inner join STANDARD_ISI_TABLE C on B.ID_STD=C.ID_STD " + 
        				"    left join " + 
        				"		(select D.ID as IDQ,ID_STD_ISI,QUESTION,ANSWER,BOBOT " + 
        				"			from STANDARD_ISI_QUESTION D " + 
        				"            inner join STANDARD_ISI_ANSWER E on D.ID=E.ID_QUESTION " + 
        				"            order by IDQ,BOBOT) as F on C.ID=F.ID_STD_ISI " + 
        				"		left join " + 
        				"			AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN G on " + 
        				"			(C.ID=G.ID_STD_ISI and F.IDQ=G.ID_QUESTION and G.KDPST=?) " + 
        				"            where A.ID_MASTER_STD=? " + 
        				"			order by KDPST desc,NORUT,C.ID_STD";
        		//System.out.println("include null");
        		
            	
            	
        	}
        	else {
        		//System.out.println("not include null");
        		//sql = "select NORUT,IDQ,A.KODE,A.ID_MASTER_STD,B.ID_STD,B.ID_TIPE_STD,C.ID AS ID_STD_ISI,A.KET_TIPE_STD,B.KET_TIPE_STD,B.TGL_STA,B.TGL_END,C.PERNYATAAN_STD,C.NO_BUTIR,C.KDPST,C.RASIONALE,C.AKTIF,C.TGL_MULAI_AKTIF,C.TGL_STOP_AKTIF,C.SCOPE,C.TIPE_PROSES_PENGAWASAN,F.QUESTION,F.ANSWER,F.BOBOT from STANDARD_MASTER_TABLE A inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD inner join STANDARD_ISI_TABLE C on B.ID_STD=C.ID_STD left join (select D.ID as IDQ,ID_STD_ISI,QUESTION,ANSWER,BOBOT from STANDARD_ISI_QUESTION D inner join STANDARD_ISI_ANSWER E on D.ID=E.ID_QUESTION) as F on C.ID=F.ID_STD_ISI left join AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN G on (C.ID=G.ID_STD_ISI and F.IDQ=G.ID_QUESTION) where A.ID_MASTER_STD=? and (C.KDPST=? or C.KDPST is NULL)  and F.QUESTION is not NULL and F.QUESTION<>'' order BY PERNYATAAN_STD,KDPST desc";
        		//sql = "select NORUT,IDQ,A.KODE,A.ID_MASTER_STD,B.ID_STD,B.ID_TIPE_STD,C.ID AS ID_STD_ISI,A.KET_TIPE_STD,B.KET_TIPE_STD,B.TGL_STA,B.TGL_END,C.PERNYATAAN_STD,C.NO_BUTIR,C.KDPST,C.RASIONALE,C.AKTIF,C.TGL_MULAI_AKTIF,C.TGL_STOP_AKTIF,C.SCOPE,C.TIPE_PROSES_PENGAWASAN,F.QUESTION,F.ANSWER,F.BOBOT from STANDARD_MASTER_TABLE A inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD inner join STANDARD_ISI_TABLE C on B.ID_STD=C.ID_STD left join (select D.ID as IDQ,ID_STD_ISI,QUESTION,ANSWER,BOBOT from STANDARD_ISI_QUESTION D inner join STANDARD_ISI_ANSWER E on D.ID=E.ID_QUESTION) as F on C.ID=F.ID_STD_ISI left join AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN G on (C.ID=G.ID_STD_ISI and F.IDQ=G.ID_QUESTION) where A.ID_MASTER_STD=? and (C.KDPST=? or C.KDPST is NULL)  and F.QUESTION is not NULL and F.QUESTION<>'' order BY PERNYATAAN_STD,KDPST desc";
        		sql = "select NORUT,IDQ,A.KODE,A.ID_MASTER_STD,B.ID_STD,B.ID_TIPE_STD,C.ID AS ID_STD_ISI,A.KET_TIPE_STD,B.KET_TIPE_STD,B.TGL_STA,B.TGL_END,C.PERNYATAAN_STD,C.NO_BUTIR,G.KDPST,C.RASIONALE,C.AKTIF,C.TGL_MULAI_AKTIF,C.TGL_STOP_AKTIF,C.SCOPE,C.TIPE_PROSES_PENGAWASAN,F.QUESTION,F.ANSWER,F.BOBOT " + 
        				"	from STANDARD_MASTER_TABLE A " + 
        				"	inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD " + 
        				"    inner join STANDARD_ISI_TABLE C on B.ID_STD=C.ID_STD " + 
        				"    left join " + 
        				"		(select D.ID as IDQ,ID_STD_ISI,QUESTION,ANSWER,BOBOT " + 
        				"			from STANDARD_ISI_QUESTION D " + 
        				"            inner join STANDARD_ISI_ANSWER E on D.ID=E.ID_QUESTION " + 
        				"            order by IDQ,BOBOT) as F on C.ID=F.ID_STD_ISI " + 
        				"		left join " + 
        				"			AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN G on " + 
        				"			(C.ID=G.ID_STD_ISI and F.IDQ=G.ID_QUESTION and G.KDPST=?) " + 
        				"            where A.ID_MASTER_STD=? and F.QUESTION is not NULL and F.QUESTION<>'' " + 
        				"			order by KDPST desc,NORUT,C.ID_STD";
        		//sql = "select G.NORUT,F.IDQ,A.KODE,A.ID_MASTER_STD,B.ID_STD,B.ID_TIPE_STD,C.ID AS ID_STD_ISI,A.KET_TIPE_STD,B.KET_TIPE_STD,B.TGL_STA,B.TGL_END,C.PERNYATAAN_STD,C.NO_BUTIR,C.KDPST,C.RASIONALE,C.AKTIF,C.TGL_MULAI_AKTIF,C.TGL_STOP_AKTIF,C.SCOPE,C.TIPE_PROSES_PENGAWASAN,F.QUESTION,F.ANSWER,F.BOBOT from STANDARD_MASTER_TABLE A inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD inner join STANDARD_ISI_TABLE C on B.ID_STD=C.ID_STD left join (select D.ID as IDQ,ID_STD_ISI,QUESTION,ANSWER,BOBOT from STANDARD_ISI_QUESTION D inner join STANDARD_ISI_ANSWER E on D.ID=E.ID_QUESTION) as F on C.ID=F.ID_STD_ISI left join AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN G on (C.ID=G.ID_STD_ISI and F.IDQ=G.ID_QUESTION) where A.ID_MASTER_STD=? and (G.KDPST=? or G.KDPST is NULL)  and F.QUESTION is not NULL and F.QUESTION<>'' order BY -G.NORUT desc,A.ID_MASTER_STD, B.ID_TIPE_STD";
        	}
        	System.out.println("sql="+sql);
        	//System.out.println("id_master_std=-="+id_master_std);
        	stmt = con.prepareStatement(sql);
        	stmt.setString(1, target_kdpst);
        	stmt.setInt(2, id_master_std);
        	
        	rs = stmt.executeQuery();
        	boolean no_pertanyaan_sdh_pernah_diurut=false;
        	if(rs.next()) {
        		v = new Vector();
        		ListIterator li = v.listIterator();
        		
        		do {
        			int i=1;
        			//G.NORUT,
        			String norut_question = rs.getString(i++);
        			if(norut_question!=null && !no_pertanyaan_sdh_pernah_diurut) {
        				no_pertanyaan_sdh_pernah_diurut=true;
        			}
        			//G.ID_QUESTION
        			String id_question = rs.getString(i++);
        			//A.KODE
        			String kode = rs.getString(i++);
        			//A.ID_MASTER_STD
        			//ignore
        			String id_master = rs.getString(i++);
        			//B.ID_STD
        			String id_std = rs.getString(i++);
        			//B.ID_TIPE_STD
        			String id_tipe_std = rs.getString(i++);
        			//C.ID AS ID_STD_ISI
        			String id_std_isi = rs.getString(i++);
        			//A.KET_TIPE_STD
        			String ket_master_std = rs.getString(i++);
        			//B.KET_TIPE_STD
        			String ket_standar = rs.getString(i++);
        			//B.TGL_STA
        			String tgl_sta = rs.getString(i++);
        			//B.TGL_END
        			String tgl_end = rs.getString(i++);
        			//C.PERNYATAAN_STD
        			String isi_std = rs.getString(i++);
        			//C.NO_BUTIR
        			String butir = rs.getString(i++);
        			//C.KDPST
        			String kdpst = rs.getString(i++);
        			//C.RASIONALE
        			String rasionale = rs.getString(i++);
        			//C.AKTIF
        			String aktif = rs.getString(i++);
        			//C.TGL_MULAI_AKTIF
        			String tgl_activated = rs.getString(i++);
        			//C.TGL_STOP_AKTIF
        			String tgl_deactivated = rs.getString(i++);
        			//C.SCOPE
        			String scope = rs.getString(i++);
        			//C.TIPE_PROSES_PENGAWASAN
        			String tipe_awas = rs.getString(i++);
        			//F.QUESTION
        			String question = rs.getString(i++);
        			//F.ANSWER,
        			String answer = rs.getString(i++);
        			//F.BOBOT
        			String bobot = rs.getString(i++);
        			//add bobot agar sorting sesuai bobot
        			String tmp = norut_question+"~"+bobot+"~"+kode+"~"+id_master_std+"~"+id_std+"~"+id_tipe_std+"~"+id_std_isi+"~"+ket_master_std+"~"+ket_standar+"~"+tgl_sta+"~"+tgl_end+"~"+isi_std+"~"+butir+"~"+kdpst+"~"+rasionale+"~"+aktif+"~"+tgl_activated+"~"+tgl_deactivated+"~"+scope+"~"+tipe_awas+"~"+question+"~"+answer+"~"+bobot+"~"+id_question;
        			//String tmp = norut_question+"~"+kode+"~"+id_master_std+"~"+id_std+"~"+id_tipe_std+"~"+id_std_isi+"~"+ket_master_std+"~"+ket_standar+"~"+tgl_sta+"~"+tgl_end+"~"+isi_std+"~"+butir+"~"+kdpst+"~"+rasionale+"~"+aktif+"~"+tgl_activated+"~"+tgl_deactivated+"~"+scope+"~"+tipe_awas+"~"+question+"~"+answer+"~"+bobot+"~"+id_question;
        			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        			li.add(tmp);
        		}
        		while(rs.next());
        		//System.out.println("no_pertanyaan_sdh_pernah_diurut="+no_pertanyaan_sdh_pernah_diurut);
        		if(v!=null && v.size()>1) {
        			
        			v = ToolSpmi.removeDuplicatePernyataanIsiStdForPrepFormAmi(v);
        		
        			if(!no_pertanyaan_sdh_pernah_diurut) {
        			
        				Vector v_init = null;
        				ListIterator lin=null;
        				/*
        				 * untuk form yang belum pernah diurut maka defaultnya yg sudah ada pertanyaan akan diberikan nomor urut
        				 	*/
        				int norut=1;
        				li = v.listIterator();
        				//System.out.println("start");
            			if(li.hasNext()) {
            				String brs = (String)li.next();
            				StringTokenizer st = new StringTokenizer(brs,"~");
            				//norut_question+"~"+bobot+"~"+kode+"~"+id_master_std+"~"+id_std+"~"+id_tipe_std+"~"+id_std_isi+"~"+ket_master_std+"~"+ket_standar+"~"+tgl_sta+"~"+tgl_end+"~"+isi_std+"~"+butir+"~"+kdpst+"~"+rasionale+"~"+aktif+"~"+tgl_activated+"~"+tgl_deactivated+"~"+scope+"~"+tipe_awas+"~"+question+"~"+answer+"~"+bobot+"~"+id_question;
            				String previous_norut_question = st.nextToken();
            				String previous_bobot = st.nextToken();
            				String previous_kode = st.nextToken();
            				String previous_id_master_std = st.nextToken();
            				String previous_id_std = st.nextToken();
            				String previous_id_tipe_std = st.nextToken();
            				String previous_id_std_isi = st.nextToken();
            				String previous_ket_master_std = st.nextToken();
            				String previous_ket_standar = st.nextToken();
            				String previous_tgl_sta = st.nextToken();
            				String previous_tgl_end = st.nextToken();
            				String previous_isi_std = st.nextToken();
            				String previous_butir = st.nextToken();
            				String previous_kdpst = st.nextToken();
            				String previous_rasionale = st.nextToken();
            				String previous_aktif = st.nextToken();
            				String previous_tgl_activated = st.nextToken();
            				String previous_tgl_deactivated = st.nextToken();
            				String previous_scope = st.nextToken();
            				String previous_tipe_awas = st.nextToken();
            				String previous_question = st.nextToken();
            				String previous_answer = st.nextToken();
            				String prev_bobot = st.nextToken();
            				String previous_id_question = st.nextToken();
            				if(!Checker.isStringNullOrEmpty(previous_question)) {
            					if(v_init==null) {
            						v_init=new Vector();
            						lin = v_init.listIterator();
            					}
            					lin.add(norut+"~"+previous_bobot+"~"+previous_kode+"~"+previous_id_master_std+"~"+previous_id_std+"~"+previous_id_tipe_std+"~"+previous_id_std_isi+"~"+previous_ket_master_std+"~"+previous_ket_standar+"~"+previous_tgl_sta+"~"+previous_tgl_end+"~"+previous_isi_std+"~"+previous_butir+"~"+previous_kdpst+"~"+previous_rasionale+"~"+previous_aktif+"~"+previous_tgl_activated+"~"+previous_tgl_deactivated+"~"+previous_scope+"~"+previous_tipe_awas+"~"+previous_question+"~"+previous_answer+"~"+previous_bobot+"~"+previous_id_question);
            					li.remove();
            				}
            				while(li.hasNext()) {
                				brs = (String)li.next();
                				st = new StringTokenizer(brs,"~");
                				//norut_question+"~"+bobot+"~"+kode+"~"+id_master_std+"~"+id_std+"~"+id_tipe_std+"~"+id_std_isi+"~"+ket_master_std+"~"+ket_standar+"~"+tgl_sta+"~"+tgl_end+"~"+isi_std+"~"+butir+"~"+kdpst+"~"+rasionale+"~"+aktif+"~"+tgl_activated+"~"+tgl_deactivated+"~"+scope+"~"+tipe_awas+"~"+question+"~"+answer+"~"+bobot+"~"+id_question;
                				String current_norut_question = st.nextToken();
                				String current_bobot = st.nextToken();
                				String current_kode = st.nextToken();
                				String current_id_master_std = st.nextToken();
                				String current_id_std = st.nextToken();
                				String current_id_tipe_std = st.nextToken();
                				String current_id_std_isi = st.nextToken();
                				String current_ket_master_std = st.nextToken();
                				String current_ket_standar = st.nextToken();
                				String current_tgl_sta = st.nextToken();
                				String current_tgl_end = st.nextToken();
                				String current_isi_std = st.nextToken();
                				String current_butir = st.nextToken();
                				String current_kdpst = st.nextToken();
                				String current_rasionale = st.nextToken();
                				String current_aktif = st.nextToken();
                				String current_tgl_activated = st.nextToken();
                				String current_tgl_deactivated = st.nextToken();
                				String current_scope = st.nextToken();
                				String current_tipe_awas = st.nextToken();
                				String current_question = st.nextToken();
                				String current_answer = st.nextToken();
                				String curr_bobot = st.nextToken();
                				String current_id_question = st.nextToken();
                				
                				if(current_isi_std.equalsIgnoreCase(previous_isi_std)) {
                					//masih di butir std yg sama = jadi norut sama
                					if(!Checker.isStringNullOrEmpty(current_question)) {
                						if(v_init==null) {
                    						v_init=new Vector();
                    						lin = v_init.listIterator();
                    					}
                						lin.add(norut+"~"+current_bobot+"~"+current_kode+"~"+current_id_master_std+"~"+current_id_std+"~"+current_id_tipe_std+"~"+current_id_std_isi+"~"+current_ket_master_std+"~"+current_ket_standar+"~"+current_tgl_sta+"~"+current_tgl_end+"~"+current_isi_std+"~"+current_butir+"~"+current_kdpst+"~"+current_rasionale+"~"+current_aktif+"~"+current_tgl_activated+"~"+current_tgl_deactivated+"~"+current_scope+"~"+current_tipe_awas+"~"+current_question+"~"+current_answer+"~"+current_bobot+"~"+current_id_question);
                						li.remove();
                    				}
                					else {
                						//ignore
                					}
                				}
                				else {
                					//ganti butir
                					previous_isi_std = new String(current_isi_std);
                					if(!Checker.isStringNullOrEmpty(current_question)) {
                						//sudah ada pertanyaan di butir yg baru = no urut pertanyaan bertambah 
                						norut++;
                						if(v_init==null) {
                    						v_init=new Vector();
                    						lin = v_init.listIterator();
                    					}
                						lin.add(norut+"~"+current_bobot+"~"+current_kode+"~"+current_id_master_std+"~"+current_id_std+"~"+current_id_tipe_std+"~"+current_id_std_isi+"~"+current_ket_master_std+"~"+current_ket_standar+"~"+current_tgl_sta+"~"+current_tgl_end+"~"+current_isi_std+"~"+current_butir+"~"+current_kdpst+"~"+current_rasionale+"~"+current_aktif+"~"+current_tgl_activated+"~"+current_tgl_deactivated+"~"+current_scope+"~"+current_tipe_awas+"~"+current_question+"~"+current_answer+"~"+current_bobot+"~"+current_id_question);
                						li.remove();
                					
                					}
                				}
            				}	
            			}
            			
            			if(v_init!=null) {
            				Vector v_merge = new Vector();
            				if(v!=null && v.size()>0) {
            					v_merge.addAll(v_init);
            					v_merge.addAll(v);
            				}
            				else {
            					v_merge.addAll(v_init);
            				}
            				
            				v = new Vector(v_merge);
            			}
            		
        				
        			}
        			else {
        				//Collections.sort(v);		
        			}
        			
        				
        			//remove bobot agar sorting sesuai bobot
        			li = v.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				StringTokenizer st = new StringTokenizer(brs,"~");
        				int counter = 0;
        				String tmp ="";
        				//System.out.println("jum token = "+st.countTokens());
        				while(st.hasMoreTokens()) {
        					counter++;
        					String token = st.nextToken();
        					if(counter!=2) {
        						tmp = tmp+token+"~";
        					}
        				}
        				tmp = tmp.substring(0, tmp.length()-1);
        				li.set(tmp);
        				
        				//st = new StringTokenizer(tmp,"~");
        				//System.out.println("tmp= "+tmp);
        			}
        			
        			if(no_pertanyaan_sdh_pernah_diurut) {
        				//Collections.sort(v);	
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
    	return v;
    }
    /*
    public Vector getListUrutanPertanyaanAmi(int id_master_std, int id_ami) {
    	Vector v = null;
    	try {
        		//String ipAddr =  request.getRemoteAddr();
    		
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "select A.KODE,A.ID_MASTER_STD,B.ID_STD,B.ID_TIPE_STD,C.ID AS ID_STD_ISI,A.KET_TIPE_STD,B.KET_TIPE_STD,B.TGL_STA,B.TGL_END,C.PERNYATAAN_STD,C.NO_BUTIR,C.KDPST,C.RASIONALE,C.AKTIF,C.TGL_MULAI_AKTIF,C.TGL_STOP_AKTIF,C.SCOPE,C.TIPE_PROSES_PENGAWASAN,D.QUESTION,D.ID as ID_QUESTION from STANDARD_MASTER_TABLE A inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD inner join STANDARD_ISI_TABLE C on B.ID_STD=C.ID_STD inner join STANDARD_ISI_QUESTION D on C.ID=D.ID_STD_ISI  where A.ID_MASTER_STD=? and D.QUESTION is not NULL and D.QUESTION<>'' order BY A.ID_MASTER_STD, B.ID_TIPE_STD";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_master_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		ListIterator li = v.listIterator();
        		
        		do {
        			int i=1;
        			//A.KODE
        			String kode = rs.getString(i++);
        			//A.ID_MASTER_STD
        			//ignore
        			String id_master = rs.getString(i++);
        			//B.ID_STD
        			String id_std = rs.getString(i++);
        			//B.ID_TIPE_STD
        			String id_tipe_std = rs.getString(i++);
        			//C.ID AS ID_STD_ISI
        			String id_std_isi = rs.getString(i++);
        			//A.KET_TIPE_STD
        			String ket_master_std = rs.getString(i++);
        			//B.KET_TIPE_STD
        			String ket_standar = rs.getString(i++);
        			//B.TGL_STA
        			String tgl_sta = rs.getString(i++);
        			//B.TGL_END
        			String tgl_end = rs.getString(i++);
        			//C.PERNYATAAN_STD
        			String isi_std = rs.getString(i++);
        			//C.NO_BUTIR
        			String butir = rs.getString(i++);
        			//C.KDPST
        			String kdpst = rs.getString(i++);
        			//C.RASIONALE
        			String rasionale = rs.getString(i++);
        			//C.AKTIF
        			String aktif = rs.getString(i++);
        			//C.TGL_MULAI_AKTIF
        			String tgl_activated = rs.getString(i++);
        			//C.TGL_STOP_AKTIF
        			String tgl_deactivated = rs.getString(i++);
        			//C.SCOPE
        			String scope = rs.getString(i++);
        			//C.TIPE_PROSES_PENGAWASAN
        			String tipe_awas = rs.getString(i++);
        			//D.QUESTION
        			String question = rs.getString(i++);
        			//D.ID.QUESTION
        			String id_question = rs.getString(i++);
        			String tmp = kode+"~"+id_master_std+"~"+id_std+"~"+id_tipe_std+"~"+id_std_isi+"~"+ket_master_std+"~"+ket_standar+"~"+tgl_sta+"~"+tgl_end+"~"+isi_std+"~"+butir+"~"+kdpst+"~"+rasionale+"~"+aktif+"~"+tgl_activated+"~"+tgl_deactivated+"~"+scope+"~"+tipe_awas+"~"+question+"~"+id_question;
        			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        			li.add(tmp);
        		}
        		while(rs.next());
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
    	return v;
    }
    */
    public Vector getHasilAmiQandA(int id_ami,int id_master) {
    	Vector v = null;
    	try {
        		//String ipAddr =  request.getRemoteAddr();
    		
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "SELECT ID_MASTER_STD,NAMA_MASTER_STD,ID_STD,NAMA_STD,ID_TIPE_STD,ID_STD_ISI,SASARAN_VALUE,RIL_VALUE,DOK_TERKAIT,NORUT_QA,RENCANA_KEGIATAN,ID_QUESTION,QUESTION,TKN_JAWABAN,TKN_BOBOT,PELANGGARAN,TIPE_PELANGGARAN,PENYEBAB_PELANGGARAN,CATATAN_HASIL_AMI,REKOMNDASI_HASIL_AMI,JAWABAN,BOBOT FROM AUDIT_MUTU_INTERNAL_HASIL where ID_AMI=? and ID_MASTER_STD=? order by NORUT_QA";
        	stmt = con.prepareStatement(sql);
        	stmt.setInt(1, id_ami);
        	stmt.setInt(2, id_master);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		
        		v = new Vector();
        		ListIterator li = v.listIterator();
        		do {
        			int i=1;
        			//ID_MASTER_STD
        			String tmp = new String(rs.getString(i++));
        			//NAMA_MASTER_STD
        			tmp = tmp+"~"+rs.getString(i++);
        			//ID_STD
        			tmp = tmp+"~"+rs.getString(i++);
        			//NAMA_STD
        			tmp = tmp+"~"+rs.getString(i++);
        			//ID_TIPE_STD
        			tmp = tmp+"~"+rs.getString(i++);
        			//ID_STD_ISI
        			tmp = tmp+"~"+rs.getString(i++);
        			//SASARAN_VALUE
        			tmp = tmp+"~"+rs.getString(i++);
        			//RIL_VALUE
        			tmp = tmp+"~"+rs.getString(i++);
        			//DOK_TERKAIT
        			tmp = tmp+"~"+rs.getString(i++);
        			//NORUT_QA
        			tmp = tmp+"~"+rs.getString(i++);
        			//RENCANA_KEGIATAN
        			tmp = tmp+"~"+rs.getString(i++);
        			//ID_QUESTION
        			tmp = tmp+"~"+rs.getString(i++);
        			//QUESTION
        			tmp = tmp+"~"+rs.getString(i++);
        			//TKN_JAWABAN
        			tmp = tmp+"~"+rs.getString(i++);
        			//TKN_BOBOT
        			tmp = tmp+"~"+rs.getString(i++);
        			//PELANGGARAN
        			tmp = tmp+"~"+rs.getString(i++);
        			//TIPE_PELANGGARAN
        			tmp = tmp+"~"+rs.getString(i++);
        			//PENYEBAB_PELANGGARAN
        			tmp = tmp+"~"+rs.getString(i++);
        			//CATATAN_HASIL_AMI
        			tmp = tmp+"~"+rs.getString(i++);
        			//REKOMNDASI_HASIL_AMI
        			tmp = tmp+"~"+rs.getString(i++);
        			//JAWABAN
        			tmp = tmp+"~"+rs.getString(i++);
        			//BOBOT
        			tmp = tmp+"~"+rs.getString(i++);
        			//System.out.println("temp = "+tmp);
        			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        			li.add(tmp);
        		}
        		while(rs.next());
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
    	return v;
    }
    
    public Vector prepSummaryRiwayatAmiYgSudahDilakukan(String kdpst) {
    	Vector v = null,vf = null;
    	ListIterator li = null, lif =null;
    	StringTokenizer st1 = null, st2=null;
    	try {
        		//String ipAddr =  request.getRemoteAddr();
    		
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//1.gwt id_ami yg sudah selesai
        	String sql = "SELECT ID,TKN_CAKUPAN_MASTER_STD,TKN_CAKUPAN_ID_MASTER_STD from AUDIT_MUTU_INTERNAL where KDPST=? and TGL_RIL_AMI_DONE is NOT NULL ORDER BY TGL_RIL_AMI_DONE";
        	stmt = con.prepareStatement(sql);
        	stmt.setString(1, kdpst);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		do {
        			String id_ami = rs.getString(1);
            		String tkn_nm_master = rs.getString(2);
            		String tkn_id_master = rs.getString(3);
            		li.add(id_ami+"~"+tkn_nm_master+"~"+tkn_id_master);	
        		}
        		while(rs.next());
        		
        		
        		//2. get total nilai untuk masing2 master_std
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"~");
        			String id_ami = st.nextToken();
            		String tkn_nm_master = st.nextToken();
            		String tkn_id_master = st.nextToken();
            		int total_id_master = 0;
            		st1 = new StringTokenizer(tkn_nm_master,",");
            		st2 = new StringTokenizer(tkn_id_master,",");
            		total_id_master = st1.countTokens();
            		sql = "select ID_AMI,ID_MASTER_STD,sum(BOBOT) as BOBOT" + 
            				"	from AUDIT_MUTU_INTERNAL_HASIL where ID_AMI=? and ID_MASTER_STD=?" + 
            				"	group by ID_AMI,ID_MASTER_STD";
            		stmt = con.prepareStatement(sql);
            		while(st1.hasMoreTokens()) {
            			String nm_master = st1.nextToken();
            			String id_master = st2.nextToken();
            			stmt.setInt(1, Integer.parseInt(id_ami));
            			stmt.setInt(2, Integer.parseInt(id_master));
                		double tot_hasil_penilaian=0;
                		rs = stmt.executeQuery();
                		if(rs.next()) {
                			if(vf==null) {
                				vf = new Vector();
                				lif = vf.listIterator();
                			}
                			tot_hasil_penilaian = rs.getDouble(3);
                		}
                		lif.add(id_ami+"~"+id_master+"~"+nm_master+"~"+tot_hasil_penilaian+"~"+total_id_master);
            		}
            		
        		}
        		
        		//3. get total nilai yg bisa didapat masing2 master_std
        		lif = vf.listIterator();
        		while(lif.hasNext()) {
        			String brs = (String)lif.next();
        			StringTokenizer st = new StringTokenizer(brs,"~");
        			String id_ami = st.nextToken();
            		String id_master = st.nextToken();
            		String nm_master = st.nextToken();
            		String tot_hasil_penilaian = st.nextToken();
            		String total_id_master = st.nextToken();
            		sql = "select  E.ID_MASTER_STD,SUM(MAX_BOBOT) from" + 
            				"	(select A.ID_MASTER_STD,A.ID_STD,A.ID_TIPE_STD,B.ID as ID_STD_ISI,MAX(BOBOT) as MAX_BOBOT  from STANDARD_TABLE A" + 
            				"	inner join STANDARD_ISI_TABLE B on A.ID_STD=B.ID_STD" + 
            				"    inner join STANDARD_ISI_QUESTION C on B.ID=C.ID_STD_ISI" + 
            				"    inner join STANDARD_ISI_ANSWER D on C.ID=D.ID_QUESTION where A.ID_MASTER_STD=?" + 
            				"    group by A.ID_MASTER_STD,A.ID_STD,B.ID,D.ID_QUESTION) as E" + 
            				"    group by E.ID_MASTER_STD";
            		stmt = con.prepareStatement(sql);
            		stmt.setInt(1, Integer.parseInt(id_master));
            		double maximum_penilaian=0;
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			maximum_penilaian = rs.getDouble(2);
            		}
            		lif.set(id_ami+"~"+id_master+"~"+nm_master+"~"+tot_hasil_penilaian+"~"+maximum_penilaian+"~"+total_id_master);
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
    	return vf;
    }
}

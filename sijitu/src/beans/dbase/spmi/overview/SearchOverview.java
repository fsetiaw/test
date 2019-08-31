package beans.dbase.spmi.overview;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
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
 * Session Bean implementation class SearchOverview
 */
@Stateless
@LocalBean
public class SearchOverview extends SearchDb {
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
    public SearchOverview() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchOverview(String operatorNpm) {
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
    public SearchOverview(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public Vector getKegiatanPpeppYgTerlewat() {
    	Vector v=null;
    	ListIterator li=null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//perencanaan
        	stmt = con.prepareStatement("SELECT ID_PLAN,TGL_STA_KEGIATAN,NAMA_KEGIATAN,JENIS_KEGIATAN,KETERANGAN_SINGKAT_TUJUAN_KEGIATAN,ISI_KEGIATAN,PENANGGUNG_JAWAB_KEGIATAN FROM RIWAYAT_PERENCANAAN_UMUM where KEGIATAN_STARTED=false and TGL_STA_KEGIATAN<CURDATE()");
        	rs = stmt.executeQuery();
        	int i=1;
        	while(rs.next()) {
        		i=1;
        		if(v==null) {
        			v = new Vector();
        			li = v.listIterator();
        		}
        		String id_plan = rs.getString(i++);
        		String tgl_plan = rs.getString(i++);
        		String nama_kegiatan = rs.getString(i++);
        		String jenis_kegiatan = rs.getString(i++);
        		String short_ket_kegiatan = rs.getString(i++);
        		String isi_kegiatan = rs.getString(i++);
        		String who_incharge = rs.getString(i++);
        		String tmp = tgl_plan+"~"+id_plan+"~"+nama_kegiatan+"~"+jenis_kegiatan+"~"+short_ket_kegiatan+"~"+isi_kegiatan+"~"+who_incharge+"~perencanaan";
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
        	}
        	//System.out.println("v--size="+v.size());
        	//pelaksanaan
        	stmt = con.prepareStatement("SELECT ID_PLAN,TGL_STA_KEGIATAN,NAMA_KEGIATAN,JENIS_KEGIATAN,KETERANGAN_SINGKAT_TUJUAN_KEGIATAN,ISI_KEGIATAN,PENANGGUNG_JAWAB_KEGIATAN FROM RIWAYAT_PELAKSANAAN_UMUM where KEGIATAN_STARTED=false and TGL_STA_KEGIATAN<CURDATE()");
        	rs = stmt.executeQuery();
        	while(rs.next()) {
        		i=1;
        		if(v==null) {
        			v = new Vector();
        			li = v.listIterator();
        		}
        		String id_plan = rs.getString(i++);
        		String tgl_plan = rs.getString(i++);
        		String nama_kegiatan = rs.getString(i++);
        		String jenis_kegiatan = rs.getString(i++);
        		String short_ket_kegiatan = rs.getString(i++);
        		String isi_kegiatan = rs.getString(i++);
        		String who_incharge = rs.getString(i++);
        		String tmp = tgl_plan+"~"+id_plan+"~"+nama_kegiatan+"~"+jenis_kegiatan+"~"+short_ket_kegiatan+"~"+isi_kegiatan+"~"+who_incharge+"~pelaksanaan";
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
        	}
        	
        	//evaluasi
        	stmt = con.prepareStatement("SELECT ID_PLAN,TGL_STA_KEGIATAN,NAMA_KEGIATAN,JENIS_KEGIATAN,KETERANGAN_SINGKAT_TUJUAN_KEGIATAN,ISI_KEGIATAN,PENANGGUNG_JAWAB_KEGIATAN FROM RIWAYAT_EVALUASI_UMUM where KEGIATAN_STARTED=false and TGL_STA_KEGIATAN<CURDATE()");
        	rs = stmt.executeQuery();
        	while(rs.next()) {
        		i=1;
        		if(v==null) {
        			v = new Vector();
        			li = v.listIterator();
        		}
        		String id_plan = rs.getString(i++);
        		String tgl_plan = rs.getString(i++);
        		String nama_kegiatan = rs.getString(i++);
        		String jenis_kegiatan = rs.getString(i++);
        		String short_ket_kegiatan = rs.getString(i++);
        		String isi_kegiatan = rs.getString(i++);
        		String who_incharge = rs.getString(i++);
        		String tmp = tgl_plan+"~"+id_plan+"~"+nama_kegiatan+"~"+jenis_kegiatan+"~"+short_ket_kegiatan+"~"+isi_kegiatan+"~"+who_incharge+"~evaluasi";
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
        	}
        	
        	//pengendalian
        	stmt = con.prepareStatement("SELECT ID_PLAN,TGL_STA_KEGIATAN,NAMA_KEGIATAN,JENIS_KEGIATAN,KETERANGAN_SINGKAT_TUJUAN_KEGIATAN,ISI_KEGIATAN,PENANGGUNG_JAWAB_KEGIATAN FROM RIWAYAT_PENGENDALIAN_UMUM where KEGIATAN_STARTED=false and TGL_STA_KEGIATAN<CURDATE()");
        	rs = stmt.executeQuery();
        	while(rs.next()) {
        		i=1;
        		if(v==null) {
        			v = new Vector();
        			li = v.listIterator();
        		}
        		String id_plan = rs.getString(i++);
        		String tgl_plan = rs.getString(i++);
        		String nama_kegiatan = rs.getString(i++);
        		String jenis_kegiatan = rs.getString(i++);
        		String short_ket_kegiatan = rs.getString(i++);
        		String isi_kegiatan = rs.getString(i++);
        		String who_incharge = rs.getString(i++);
        		String tmp = tgl_plan+"~"+id_plan+"~"+nama_kegiatan+"~"+jenis_kegiatan+"~"+short_ket_kegiatan+"~"+isi_kegiatan+"~"+who_incharge+"~pengendalian";
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
        	}
        	
        	//peningkatan
        	stmt = con.prepareStatement("SELECT ID_PLAN,TGL_STA_KEGIATAN,NAMA_KEGIATAN,JENIS_KEGIATAN,KETERANGAN_SINGKAT_TUJUAN_KEGIATAN,ISI_KEGIATAN,PENANGGUNG_JAWAB_KEGIATAN FROM RIWAYAT_PENINGKATAN_UMUM where KEGIATAN_STARTED=false and TGL_STA_KEGIATAN<CURDATE()");
        	rs = stmt.executeQuery();
        	while(rs.next()) {
        		i=1;
        		if(v==null) {
        			v = new Vector();
        			li = v.listIterator();
        		}
        		String id_plan = rs.getString(i++);
        		String tgl_plan = rs.getString(i++);
        		String nama_kegiatan = rs.getString(i++);
        		String jenis_kegiatan = rs.getString(i++);
        		String short_ket_kegiatan = rs.getString(i++);
        		String isi_kegiatan = rs.getString(i++);
        		String who_incharge = rs.getString(i++);
        		String tmp = tgl_plan+"~"+id_plan+"~"+nama_kegiatan+"~"+jenis_kegiatan+"~"+short_ket_kegiatan+"~"+isi_kegiatan+"~"+who_incharge+"~peningkatan";
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
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
    
    public Vector getKegiatanAmiYgTerlewat(String kdpst) {
    	Vector v=null;
    	ListIterator li=null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//AMI
        	if(!Checker.isStringNullOrEmpty(kdpst)) {
        		if(kdpst.equalsIgnoreCase("all")) {
            		stmt = con.prepareStatement("SELECT ID,KDPST,NAMA_KEGIATAN_AMI,TGL_RENCANA_AMI,KETUA_TIM,TKN_ANGGOTA_TIM,TKN_CAKUPAN_MASTER_STD FROM AUDIT_MUTU_INTERNAL where TGL_RIL_AMI is null and TGL_RENCANA_AMI<CURDATE()");	
            	}	
        		else {
        			stmt = con.prepareStatement("SELECT ID,KDPST,NAMA_KEGIATAN_AMI,TGL_RENCANA_AMI FROM AUDIT_MUTU_INTERNAL,KETUA_TIM,TKN_ANGGOTA_TIM,TKN_CAKUPAN_MASTER_STD where TGL_RIL_AMI is null and TGL_RENCANA_AMI<CURDATE() and KDPST=?");
        			stmt.setString(1, kdpst);
        		}
        	}
        	
        	rs = stmt.executeQuery();
        	int i=1;
        	while(rs.next()) {
        		if(v==null) {
        			v = new Vector();
        			li = v.listIterator();
        		}
        		i=1;
        		String id = rs.getString(i++);
        		kdpst = rs.getString(i++);
        		String nama_kegiatan = rs.getString(i++);
        		String tgl_rencana = rs.getString(i++);
        		String ketua_tim = rs.getString(i++);
        		String anggota_tim = rs.getString(i++);
        		String cakupan_std = rs.getString(i++);
        		String tmp = id+"~"+kdpst+"~"+nama_kegiatan+"~"+tgl_rencana+"~"+ketua_tim+"~"+anggota_tim+"~"+cakupan_std;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
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
    
    public Vector getKegiatanMonitoringYgBelumAdaKegiatanPengendalian(String kdpst) {
    	Vector v=null;
    	ListIterator li=null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//monitoring
        	//1. blum ada pengendalian
        	stmt = con.prepareStatement("SELECT A.ID_EVAL,B.ID_STD,STD_ISI_ID,TGL_EVAL,NPM_EVALUATOR,A.KDPST,PERNYATAAN_STD,KET_TIPE_STD,KONDISI,ANALISA,REKOMENDASI FROM RIWAYAT_EVALUASI_AMI A inner join STANDARD_ISI_TABLE B on A.STD_ISI_ID=B.ID inner join STANDARD_TABLE C on B.ID_STD=C.ID_STD left join RIWAYAT_PENGENDALIAN_AMI D on A.ID_EVAL=D.ID_EVAL where ID_KENDALI is null and A.KDPST=? order by TGL_EVAL");
        	stmt.setString(1,kdpst);
        	rs = stmt.executeQuery();
        	while(rs.next()) {
        		int i=1;
        		if(v==null) {
        			v = new Vector();
        			li = v.listIterator();
        		}
        		String id_eval = rs.getString(i++);
        		String id_std = rs.getString(i++);
        		String id_std_isi = rs.getString(i++);
        		String tgl_eval = rs.getString(i++);
        		String npm_monitoree = rs.getString(i++);
        		kdpst = rs.getString(i++);
        		String isi_std = rs.getString(i++);
        		String rumpun_std = rs.getString(i++);
        		String kondisi = rs.getString(i++);
        		String analisa = rs.getString(i++);
        		String rekomendasi = rs.getString(i++);
        		
        		String tmp = id_eval+"~"+id_std+"~"+id_std_isi+"~"+kdpst+"~"+tgl_eval+"~"+npm_monitoree+"~"+isi_std+"~"+rumpun_std+"~"+kondisi+"~"+analisa+"~"+rekomendasi;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
        	}
        	if(v!=null) {
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs=(String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"~");
        			String id_eval=st.nextToken();
        			String id_std=st.nextToken();
        			String id_std_isi=st.nextToken();
        			kdpst=st.nextToken();
        			String tgl_eval=st.nextToken();
        			String npm_monitoree=st.nextToken();
        			String isi_std=st.nextToken();
        			String rumpun_std=st.nextToken();
        			String nmm_monitoree=null;
        			if(!Checker.isStringNullOrEmpty(npm_monitoree)) {
        				nmm_monitoree=new String(Checker.getNmmhs(npm_monitoree));
        			}
        			 
        			if(Checker.isStringNullOrEmpty(nmm_monitoree)) {
        				nmm_monitoree="null";
        			}
        			li.set(brs+"~"+nmm_monitoree);
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
    
    public Vector getKegiatanMonitoringYgTerlewat(String kdpst) {
    	Vector v=null;
    	ListIterator li=null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//monitoring
        	//1. blum ada pengendalian
        	stmt = con.prepareStatement("SELECT distinct STD_ISI_ID FROM RIWAYAT_EVALUASI_AMI where KDPST=?");
        	stmt.setString(1,kdpst);
        	rs = stmt.executeQuery();
        	
        	while(rs.next()) {
        		int i=1;
        		if(v==null) {
        			v = new Vector();
        			li = v.listIterator();
        		}
        		String id_std_isi = rs.getString(i++);
        		li.add(id_std_isi);
        	}
        	
        	
        	if(v!=null) {
        		//CARI TGL_NEXT_EVAL TERAKHIR
        		String sql = "SELECT ID_EVAL,TGL_NEXT_EVAL FROM RIWAYAT_EVALUASI_AMI where STD_ISI_ID=? and TGL_NEXT_EVAL is not null and TGL_NEXT_EVAL<=CURDATE() order by TGL_NEXT_EVAL desc limit 1";
        		stmt=con.prepareStatement(sql);
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String id_std_isi=(String)li.next();
        			stmt.setInt(1, Integer.parseInt(id_std_isi));
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				String id_eval = rs.getString(1);
        				String next_eval_dat = rs.getString(2);
        				li.set(id_eval+"~"+id_std_isi+"~"+next_eval_dat);
        			}
        			else {
        				li.remove();
        			}
        		}
        	}
        	
        	if(v!=null) {
        		//CARI sdh ada kegiatan setelah tgl eval TGL_NEXT_EVAL TERAKHIR
        		String sql = "SELECT ID_EVAL FROM RIWAYAT_EVALUASI_AMI where STD_ISI_ID=? and TGL_EVAL>=? limit 1";
        		stmt=con.prepareStatement(sql);
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs=(String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"~");
        			String id_eval = st.nextToken();
        			String id_std_isi = st.nextToken();
        			String tgl_next_eval = st.nextToken();
        			stmt.setInt(1, Integer.parseInt(id_std_isi));
        			stmt.setDate(2, java.sql.Date.valueOf(tgl_next_eval));
        			if(rs.next()) {
        				li.remove();
        			}
        		}
        	}	
        	
        	if(v!=null) {
        		//CARI info kegiatan
        		String sql = "SELECT B.ID_STD,STD_ISI_ID,TGL_EVAL,NPM_EVALUATOR,A.KDPST,PERNYATAAN_STD,KET_TIPE_STD,KONDISI,ANALISA,REKOMENDASI,TGL_NEXT_EVAL FROM RIWAYAT_EVALUASI_AMI A inner join STANDARD_ISI_TABLE B on A.STD_ISI_ID=B.ID inner join STANDARD_TABLE C on B.ID_STD=C.ID_STD where A.ID_EVAL=?";
        		stmt=con.prepareStatement(sql);
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs=(String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"~");
        			String id_eval = st.nextToken();
        			stmt.setInt(1, Integer.parseInt(id_eval));
        			rs = stmt.executeQuery();
                	while(rs.next()) {
                		int i=1;
                		if(v==null) {
                			v = new Vector();
                			li = v.listIterator();
                		}
                		String id_std = rs.getString(i++);
                		String id_std_isi = rs.getString(i++);
                		String tgl_eval = rs.getString(i++);
                		String npm_monitoree = rs.getString(i++);
                		kdpst = rs.getString(i++);
                		String isi_std = rs.getString(i++);
                		String rumpun_std = rs.getString(i++);
                		String kondisi = rs.getString(i++);
                		String analisa = rs.getString(i++);
                		String rekomendasi = rs.getString(i++);
                		String tgl_next_eval = rs.getString(i++);
                		String tmp = id_eval+"~"+id_std+"~"+id_std_isi+"~"+kdpst+"~"+tgl_eval+"~"+npm_monitoree+"~"+isi_std+"~"+rumpun_std+"~"+kondisi+"~"+analisa+"~"+rekomendasi+"~"+tgl_next_eval;
                		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
                		li.set(tmp);
                	}
                	
        		}
        		
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs=(String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"~");
        			String id_eval=st.nextToken();
        			String id_std=st.nextToken();
        			String id_std_isi=st.nextToken();
        			kdpst=st.nextToken();
        			String tgl_eval=st.nextToken();
        			String npm_monitoree=st.nextToken();
        			String isi_std=st.nextToken();
        			String rumpun_std=st.nextToken();
        			String nmm_monitoree=null;
        			if(!Checker.isStringNullOrEmpty(npm_monitoree)) {
        				nmm_monitoree=new String(Checker.getNmmhs(npm_monitoree));
        			}
        			 
        			if(Checker.isStringNullOrEmpty(nmm_monitoree)) {
        				nmm_monitoree="null";
        			}
        			li.set(brs+"~"+nmm_monitoree);
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
    
    public Vector getListIsiStdYgBelumPernahDiMonitor(Vector v_prodi) {
    	Vector v=new Vector();
    	Vector vf=null;
    	ListIterator li=null,li1=null,lif=null;
    	li = v.listIterator();
    	
    	//Vector v_prodi = Getter.returnListProdiOnlySortByKampusWithListIdobj();
    	//v_prodi = Converter.convertVscopeidToKdpst(v_prodi);
    	li1 = v_prodi.listIterator();
    	while(li1.hasNext()) {
    		String brs = (String)li1.next();
    		//System.out.println(brs);
    		StringTokenizer st = new StringTokenizer(brs,"`");
    		st.nextToken();
    		//while(st.hasMoreTokens()) {
    			li.add(st.nextToken());
    		//}
    	}
    	
    		
    	try {
    		v_prodi = Tool.removeDuplicateFromVector(v);
    		//cek standard yang sudah diaktifkan saja	
    		v = null;
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("SELECT B.ID_MASTER_STD,B.ID_STD,B.ID_TIPE_STD,C.ID AS ID_STD_ISI,A.KET_TIPE_STD,B.KET_TIPE_STD,C.PERNYATAAN_STD FROM STANDARD_MASTER_TABLE A inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD inner join STANDARD_ISI_TABLE C on B.ID_STD=C.ID_STD where C.KDPST is null and C.AKTIF=true and C.TGL_MULAI_AKTIF is not null and C.TGL_STOP_AKTIF is null order by A.ID_MASTER_STD,B.ID_TIPE_STD,C.PERNYATAAN_STD");
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		do {
        			int i=1;
        			String id_master_std = rs.getString(i++);
        			String id_std = rs.getString(i++);
        			String id_tipe_std = rs.getString(i++);
        			String id_std_isi = rs.getString(i++);
        			String nm_rumpun_std = rs.getString(i++);
        			String nm_std = rs.getString(i++);
        			String isi_std = rs.getString(i++);

        			li.add(id_master_std+"~"+id_std+"~"+id_tipe_std+"~"+id_std_isi+"~"+nm_rumpun_std+"~"+nm_std+"~"+isi_std);
        		}
        		while(rs.next());
        	}
        	
        	if(v!=null) {
        		vf = new Vector();
        		lif = vf.listIterator();
        		String sql = "SELECT D.ID_EVAL FROM STANDARD_TABLE A inner join STANDARD_TABLE B on A.ID_STD=B.ID_STD inner join STANDARD_ISI_TABLE C on A.ID_STD=C.ID_STD left join RIWAYAT_EVALUASI_AMI D on C.ID=D.STD_ISI_ID where C.PERNYATAAN_STD=? and D.KDPST=? and C.AKTIF=true";
        		stmt = con.prepareStatement(sql);
        		li1 = v_prodi.listIterator();
            	while(li1.hasNext()) {
            		String kdpst = (String)li1.next();
            		//System.out.println("kdpst="+kdpst);
            		li = v.listIterator();
            		lif.add(kdpst); 
            		Vector vtmp = new Vector();
            		ListIterator lit = vtmp.listIterator();
            		while(li.hasNext()) {
            			String brs = (String)li.next();
            			StringTokenizer st = new StringTokenizer(brs,"~");
            			String id_master_std = st.nextToken();
            			String id_std = st.nextToken();
            			String id_tipe_std = st.nextToken();
            			String id_std_isi = st.nextToken();
            			String nm_rumpun_std = st.nextToken();
            			String nm_std = st.nextToken();
            			String isi_std = st.nextToken();
            			stmt.setString(1, isi_std);
            			stmt.setString(2, kdpst);
            			rs=stmt.executeQuery();
            			if(!rs.next()) {
            				lit.add(brs);
            			}
            		}
            		lif.add(vtmp);
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
    	return vf;
    }
    
    

    public Vector getListPelanggaranIsiStd(Vector v_scope_kdpst_spmi) {
    	Vector vf=null;
    	ListIterator lif = null;
    	if(v_scope_kdpst_spmi!=null) {
    		Vector v_list =null;
    		ListIterator lil=null;
    		try {
    			Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	String sql="SELECT DISTINCT STD_ISI_ID FROM RIWAYAT_EVALUASI_AMI A inner join RIWAYAT_PENGENDALIAN_AMI B on A.ID_EVAL=B.ID_EVAL where KDPST=?";
            	//System.out.println("ssql="+sql);
    			
    			ListIterator li = v_scope_kdpst_spmi.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				st.nextToken();
    				String kdpst = st.nextToken();
    				stmt = con.prepareStatement(sql);
    				stmt.setString(1, kdpst);
    				rs = stmt.executeQuery();
    				String tkn_id_isi=null;
    				while(rs.next()) {
    					if(tkn_id_isi==null) {
    						tkn_id_isi = new String(kdpst+"`"+rs.getString(1));
    					}
    					else {
    						tkn_id_isi = tkn_id_isi+"`"+rs.getString(1);
    					}
    				}
    				if(!Checker.isStringNullOrEmpty(tkn_id_isi)) {
    					if(v_list==null) {
        					v_list=new Vector();
        					lil = v_list.listIterator();
        				}
    					lil.add(tkn_id_isi);
    				}
    			}
    			if(v_list!=null) {
    				//get pengendalian ami terkini untuk tiap standar
    				stmt = con.prepareStatement("SELECT * FROM RIWAYAT_EVALUASI_AMI A inner join RIWAYAT_PENGENDALIAN_AMI B on A.ID_EVAL=B.ID_EVAL inner join STANDARD_ISI_TABLE C on A.STD_ISI_ID=C.ID inner join STANDARD_VERSION D on A.STD_ISI_ID=D.ID_STD_ISI  where STD_ISI_ID=? and A.KDPST=? and D.AKTIF=true order by TGL_KENDALI desc, B.ID_EVAL desc limit 1");
    				lil = v_list.listIterator();
    				
    				while(lil.hasNext()) {
    					boolean first=true;
    					Vector vtmp = null;
    					ListIterator litmp=null;
    					String brs = (String)lil.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String kdpst = st.nextToken();
    					while(st.hasMoreTokens()) {
    						String id_isi = st.nextToken();
    						stmt.setInt(1, Integer.parseInt(id_isi));
    						stmt.setString(2, kdpst);
    						rs = stmt.executeQuery();
    						rs.next();
    						String id_eval = ""+rs.getString("ID_EVAL");
    						String id_std_isi = ""+rs.getString("STD_ISI_ID");
    						String id_versi = ""+rs.getString("VERSI_ID");
    						String npm_eval = ""+rs.getString("NPM_EVALUATOR");
    						String tgl_eval = ""+rs.getString("TGL_EVAL");
    						String kondisi_eval = ""+rs.getString("KONDISI");
    						String analisa_eval = ""+rs.getString("ANALISA");
    						String rekomen_eval = ""+rs.getString("REKOMENDASI");
    						String target_val = ""+rs.getString("TARGET_VALUE");
    						String ril_val = ""+rs.getString("REAL_VALUE");
    						String id_kendal = ""+rs.getString("ID_KENDALI");
    						String rasional_kendal = ""+rs.getString("RASIONALE_TINDAKAN");
    						String tindakan_kendal = ""+rs.getString("TINDAKAN_PENGENDALIAN");
    						String tgl_kendal = ""+rs.getString("TGL_KENDALI");
    						String npm_kendal = ""+rs.getString("NPM_PENGENDALI");
    						boolean pelanggaran = rs.getBoolean("PELANGGARAN");
    						String jenis_pelanggaran = ""+rs.getString("JENIS_PELANGGARAN");
    						String isi_std = ""+rs.getString("PERNYATAAN_STD");
    						String unit = ""+rs.getString("TARGET_THSMS_1_UNIT");
    						if(pelanggaran) {
    							if(vtmp==null) {
    								vtmp = new Vector();
    								litmp = vtmp.listIterator();
    							}
    							
    							String tmp = id_eval+"~"+id_std_isi+"~"+id_versi+"~"+npm_eval+"~"+tgl_eval+"~"+kondisi_eval+"~"+analisa_eval+"~"+rekomen_eval+"~"+target_val+"~"+ril_val+"~"+id_kendal+"~"+rasional_kendal+"~"+tindakan_kendal+"~"+tgl_kendal+"~"+npm_kendal+"~"+pelanggaran+"~"+jenis_pelanggaran+"~"+isi_std+"~"+unit;
    							tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
    							litmp.add(tmp);
    						}
    					}
    					if(vtmp!=null) {
    						if(vf==null) {
								vf = new Vector();
								lif = vf.listIterator();
							}
    						lif.add(kdpst);
    						lif.add(vtmp);
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
    	return vf;
    }
    
    public Vector getListProdiYgBelumPernahDilakukanAmi(Vector v_scope_kdpst_spmi) {
    	Vector v=new Vector();
    	ListIterator li=null,li1=null,lif=null;
    	
    	if(v_scope_kdpst_spmi!=null && v_scope_kdpst_spmi.size()>0) {
    		ListIterator lis = v_scope_kdpst_spmi.listIterator();
    		boolean masuk_scope=false;
    		String scope = "AND (";
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
        	try {
        		v = null;
            	Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	String sql = "SELECT DISTINCT KDPSTMSPST,NMPSTMSPST,KDJENMSPST,KODE_JENJANG FROM MSPST A where  NOT EXISTS" + 
            			"	(select distinct B.KDPST,B.TGL_RIL_AMI from AUDIT_MUTU_INTERNAL B where A.KDPSTMSPST=B.KDPST And B.TGL_RIL_AMI is not null "+scope+") " + 
            			"    and A.KDJENMSPST>='A' and A.KDJENMSPST<'Z'";
            	stmt = con.prepareStatement(sql);
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		v = new Vector();
            		li = v.listIterator();
            		do {
            			int i=1;
            			String kdpst = rs.getString(i++);
            			String nmpst = rs.getString(i++);
            			String kdjen = rs.getString(i++);
            			String kode_jen = rs.getString(i++);
                		li.add(kdpst+"~"+nmpst+"~"+kdjen+"~"+kode_jen);
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
        	catch (Exception ex) {
            	ex.printStackTrace();
            } 
            finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        	    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        	    if (con!=null) try { con.close();} catch (Exception ignore){}
        	}
    	}
    	
    	return v;
    }
    
    public Vector getListStandardProdiYgBelumPernahDilakukanAmi(Vector v_scope_kdpst_spmi) {
    	Vector v=null,v1=null;
    	ListIterator li=null,li1=null,lif=null;
    	Vector v_prodi_list_standard_blum_ami = null;
    	if(v_scope_kdpst_spmi!=null && v_scope_kdpst_spmi.size()>0) {
    		ListIterator lis = v_scope_kdpst_spmi.listIterator();
    		boolean masuk_scope=false;
    		String scope = "AND (";
    		while(lis.hasNext()) {
    			String brs=(String)lis.next();
    			String kdpst = Tool.getTokenKe(brs, 2, "`");
    			scope = scope+"KDPSTMSPST='"+kdpst+"'";
    			if(lis.hasNext()) {
    				scope = scope + " OR ";
    			}
    			else {
    				scope = scope + ")";
    			}
    		}
        	try {
        		v = null;
            	Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	//1. get list kdpst
            	String sql = "SELECT DISTINCT KDPSTMSPST,NMPSTMSPST,KDJENMSPST,KODE_JENJANG FROM MSPST A where A.KDJENMSPST>='A' and A.KDJENMSPST<'Z' "+scope;
            	//System.out.println("sql="+sql);
            	stmt = con.prepareStatement(sql);
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		v = new Vector();
            		li = v.listIterator();
            		do {
            			int i=1;
            			String kdpst = rs.getString(i++);
            			String nmpst = rs.getString(i++);
            			String kdjen = rs.getString(i++);
            			String kode_jen = rs.getString(i++);
                		li.add(kdpst+"~"+nmpst+"~"+kdjen+"~"+kode_jen);
                		
            		}
            		while(rs.next());
            	}
            	
            	
            	//get list master std yg aktif
            	//sql = "SELECT * FROM STANDARD_MASTER_TABLE where ID_MASTER_STD>0 and KODE is not null";
            	sql = "SELECT distinct C.ID_MASTER_STD,C.KET_TIPE_STD FROM STANDARD_TIPE_VERSION as A " + 
            			"inner join  " + 
            			"	STANDARD_TABLE B on A.ID_STD=B.ID_STD " + 
            			"inner join STANDARD_MASTER_TABLE C on B.ID_MASTER_STD=C.ID_MASTER_STD " + 
            			"where A.TGL_STA is not null and A.TGL_END is null";
            	stmt = con.prepareStatement(sql);
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		v1 = new Vector();
            		li1 = v1.listIterator();
            		do {
            			int i=1;
            			String id_master = rs.getString(i++);
                    	String nm_master = rs.getString(i++);
                		li1.add(id_master+"~"+nm_master);
            		}
            		while(rs.next());
            	}
            	//cek di tabel ami
            	if(v!=null && v1!=null) {
            		//System.out.println("bener1");
            		ListIterator liv = null;
            		sql = "select KDPST from AUDIT_MUTU_INTERNAL where KDPST=? and "
            				+ "(TKN_CAKUPAN_ID_MASTER_STD like ? or TKN_CAKUPAN_ID_MASTER_STD like ? or TKN_CAKUPAN_ID_MASTER_STD like ? or TKN_CAKUPAN_ID_MASTER_STD=?)";
            		stmt = con.prepareStatement(sql);
            		li = v.listIterator();
            		while(li.hasNext()) {
            			String brs = (String)li.next();
            			//System.out.println("baris="+brs);
            			String list_standard_blum_ami = "";
            			StringTokenizer st = new StringTokenizer(brs,"~");
            			String kdpst = st.nextToken();
            			String nmpst = st.nextToken();
            			String kdjen = st.nextToken();
            			String kode_jen = st.nextToken();
            			li1 = v1.listIterator();
            			while(li1.hasNext()) {
                			brs = (String)li1.next();
                			st = new StringTokenizer(brs,"~");
                			String id_master = st.nextToken();
                			String nm_master = st.nextToken();
                			stmt.setString(1, kdpst);
                			stmt.setString(2, id_master+",%");
                			stmt.setString(3, "%,"+id_master+",%");
                			stmt.setString(4, "%,"+id_master);
                			stmt.setString(5, id_master);
                			rs = stmt.executeQuery();
                			if(!rs.next()) {
                				list_standard_blum_ami=list_standard_blum_ami+nm_master+"~";
                				//System.out.println("tambah="+list_standard_blum_ami);
                			}
                		}
            			if(!Checker.isStringNullOrEmpty(list_standard_blum_ami)) {
            				if(v_prodi_list_standard_blum_ami==null) {
                				v_prodi_list_standard_blum_ami = new Vector();
                				liv = v_prodi_list_standard_blum_ami.listIterator();
                			}	
            				liv.add(nmpst+"~"+kode_jen+"~"+list_standard_blum_ami);
            				//System.out.println("add="+nmpst+"~"+kode_jen+"~"+list_standard_blum_ami);
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
    	
    	return v_prodi_list_standard_blum_ami;
    }
    
    public Vector getListProdiYgSudahPernahDiAmi(Vector v_scope_kdpst_spmi) {
    	Vector v=new Vector();
    	ListIterator li=null,li1=null,lif=null;
    	
    	if(v_scope_kdpst_spmi!=null && v_scope_kdpst_spmi.size()>0) {
    		ListIterator lis = v_scope_kdpst_spmi.listIterator();
    		boolean masuk_scope=false;
    		String scope = "AND (";
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
        	try {
        		v = null;
            	Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	String sql = "select B.KDPSTMSPST,B.NMPSTMSPST,B.KDJENMSPST,B.KODE_JENJANG,A.COUNT from (" + 
            			"		(SELECT KDPST,COUNT(*) as COUNT FROM AUDIT_MUTU_INTERNAL  " + 
            			"		where TGL_RIL_AMI is not null "+scope+" group BY KDPST ORDER BY count DESC) A" + 
            					"	) inner join MSPST B on A.KDPST=B.KDPSTMSPST";
            	stmt = con.prepareStatement(sql);
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		v = new Vector();
            		li = v.listIterator();
            		do {
            			int i=1;
            			String kdpst = rs.getString(i++);
            			String nmpst = rs.getString(i++);
            			String kdjen = rs.getString(i++);
            			String kode_jen = rs.getString(i++);
            			String ami_counter = rs.getString(i++);
                		li.add(kdpst+"~"+nmpst+"~"+kdjen+"~"+kode_jen+"~"+ami_counter);
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
        	catch (Exception ex) {
            	ex.printStackTrace();
            } 
            finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        	    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        	    if (con!=null) try { con.close();} catch (Exception ignore){}
        	}
    	}
    	
    	return v;
    }
}

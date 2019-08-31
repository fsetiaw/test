package beans.dbase.feeder;

import beans.dbase.SearchDb;
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
 * Session Bean implementation class SearchDbFeeder
 */
@Stateless
@LocalBean
public class SearchDbFeeder extends SearchDb {
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
    public SearchDbFeeder() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbFeeder(String operatorNpm) {
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
    public SearchDbFeeder(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public Vector getInfoMalaikatAndReal(String thsms, String alamat_filename_mhs_aktif_with_txt) {
    	Vector v = null;
    	try {
    		////System.out.println("alm="+alamat_filename_mhs_aktif_with_txt);
    		v=Tool.bacaFileTxt(alamat_filename_mhs_aktif_with_txt, thsms);
    		////System.out.println(v.size());
    		ListIterator li = null;
    	
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		if(v!=null) {
    			Vector va = new Vector();
    			ListIterator lia = va.listIterator();
    			Vector vm = new Vector();
    			ListIterator lim = vm.listIterator();
    			stmt = con.prepareStatement("select NPMHSMSMHS,SHIFTMSMHS,MALAIKAT from CIVITAS where NIMHSMSMHS=? order by NIMHSMSMHS");
    			li = v.listIterator();
    	    	while(li.hasNext()) {
    	    		String brs = (String)li.next();
    	    		StringTokenizer st = new StringTokenizer(brs,"`");
    	    		String no = st.nextToken();
    	    		String nimhs = st.nextToken();
    	    		String nmmhs = st.nextToken();
    	    		String smawl = st.nextToken();
    	    		String stpid = st.nextToken();
    	    		////System.out.println("nimhs = "+nimhs);
    	    		stmt.setString(1, nimhs);
    	    		rs = stmt.executeQuery();
    	    		rs.next();
    	    		////System.out.println("sial = "+brs);
    	    		String npmhs = rs.getString(1);
    	    		String shift = rs.getString(2);
    	    		boolean angel = rs.getBoolean(3);
    	    		if(angel) {
    	    			lia.add(shift+"`"+npmhs+"`"+nimhs+"`"+nmmhs+"`"+smawl+"`"+stpid);
    	    		}
    	    		else {
    	    			lim.add(shift+"`"+npmhs+"`"+nimhs+"`"+nmmhs+"`"+smawl+"`"+stpid);	
    	    		}
    	    		
    	    	}
    	    	v = new Vector();
    	    	li = v.listIterator();
    	    	li.add(vm);
    	    	li.add(va);
    	    	////System.out.println(va.size());
        		////System.out.println(vm.size());
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
    	return v;
    }
    
    public Vector filterMhsPerShift(Vector vmhs) {
    	Collections.sort(vmhs);
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		ListIterator lim = null;
    		ListIterator lis = null;
    		if(vmhs!=null) {
    			lim = vmhs.listIterator();
    	    	if(lim.hasNext()) {
    	    		Vector vtmp = new Vector();
    	    		ListIterator lit = vtmp.listIterator();
    	    		String brs = (String)lim.next();
    	    		StringTokenizer st = new StringTokenizer(brs,"`");
    	    		String prev_shift = st.nextToken();
    	    		String prev_npmhs = st.nextToken();
    	    		String prev_nimhs = st.nextToken();
    	    		String prev_nmmhs = st.nextToken();
    	    		String prev_smawl = st.nextToken();
    	    		String prev_stpid = st.nextToken();
    	    		lit.add(brs);
    	    		while(lim.hasNext()) {
    	    			brs = (String)lim.next();
        	    		st = new StringTokenizer(brs,"`");
        	    		String curr_shift = st.nextToken();
        	    		String curr_npmhs = st.nextToken();
        	    		String curr_nimhs = st.nextToken();
        	    		String curr_nmmhs = st.nextToken();
        	    		String curr_smawl = st.nextToken();
        	    		String curr_stpid = st.nextToken();
        	    		if(prev_shift.equalsIgnoreCase(curr_shift)) {
        	    			lit.add(brs);
        	    		}
        	    		else {
        	    			prev_shift = new String(curr_shift);
        	    			lif.add(vtmp);
        	    			vtmp = new Vector();
        	    			lit = vtmp.listIterator();
        	    			lit.add(brs);
        	    		}
        	    		//if(!lim.hasNext()) {
        	    		//	lif.add(vtmp);
        	    		//}
    	    		}
    	    		if(!lim.hasNext()) {
    	    			lif.add(vtmp);
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
    	return vf;
    }
    
    public Vector filterMhsPerAngkatan(Vector vmhs) {
    	Collections.sort(vmhs);
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		ListIterator lim = null;
    		ListIterator lis = null;
    		if(vmhs!=null) {
    			lim = vmhs.listIterator();
    	    	while(lim.hasNext()) {
    	    		String brs = (String)lim.next();
    	    		StringTokenizer st = new StringTokenizer(brs,"`");
    	    		String shift = st.nextToken();
    	    		String npmhs = st.nextToken();
    	    		String nimhs = st.nextToken();
    	    		String nmmhs = st.nextToken();
    	    		String smawl = st.nextToken();
    	    		String stpid = st.nextToken();
    	    		lim.set(smawl+"`"+npmhs+"`"+nimhs+"`"+nmmhs+"`"+shift+"`"+stpid);
    	    	}
    	    	Collections.sort(vmhs);
    	    	lim = vmhs.listIterator();
    	    	if(lim.hasNext()) {
    	    		Vector vtmp = new Vector();
    	    		ListIterator lit = vtmp.listIterator();
    	    		String brs = (String)lim.next();
    	    		StringTokenizer st = new StringTokenizer(brs,"`");
    	    		String prev_smawl = st.nextToken();
    	    		String prev_npmhs = st.nextToken();
    	    		String prev_nimhs = st.nextToken();
    	    		String prev_nmmhs = st.nextToken();
    	    		String prev_shift = st.nextToken();
    	    		String prev_stpid = st.nextToken();
    	    		lit.add(brs);
    	    		while(lim.hasNext()) {
    	    			brs = (String)lim.next();
        	    		st = new StringTokenizer(brs,"`");
    	    			String curr_smawl = st.nextToken();
        	    		String curr_npmhs = st.nextToken();
        	    		String curr_nimhs = st.nextToken();
        	    		String curr_nmmhs = st.nextToken();
        	    		String curr_shift = st.nextToken();
        	    		String curr_stpid = st.nextToken();
        	    		if(prev_smawl.equalsIgnoreCase(curr_smawl)) {
        	    			lit.add(brs);
        	    		}
        	    		else {
        	    			prev_smawl = new String(curr_smawl);
        	    			lif.add(vtmp);
        	    			vtmp = new Vector();
        	    			lit = vtmp.listIterator();
        	    			lit.add(brs);
        	    		}
        	    		//if(!lim.hasNext()) {
        	    		//	lif.add(vtmp);
        	    		//}
    	    		}
    	    		if(!lim.hasNext()) {
    	    			lif.add(vtmp);
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
    	return vf;
    }
    
    
    public Vector filterMhsPerKurikulum(Vector VfilterMhsPerAngkatan) {
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt=con.prepareStatement("select KRKLMMSMHS from EXT_CIVITAS where NPMHSMSMHS=?");
    		ListIterator lim = null;
    		ListIterator lis = null;
    		if(VfilterMhsPerAngkatan!=null) {
    			lim = VfilterMhsPerAngkatan.listIterator();
    	    	while(lim.hasNext()) {
    	    		String brs = (String)lim.next();
    	    		StringTokenizer st = new StringTokenizer(brs,"`");
    	    		String smawl = st.nextToken();
    	    		String npmhs = st.nextToken();
    	    		String nimhs = st.nextToken();
    	    		String nmmhs = st.nextToken();
    	    		String shift = st.nextToken();
    	    		String stpid = st.nextToken();
    	    		stmt.setString(1, npmhs);
    	    		rs = stmt.executeQuery();
    	    		String krklm = "null";
    	    		if(rs.next()) {
    	    			krklm = ""+rs.getString(1);
    	    		}
    	    		lim.set(krklm+"`"+brs);
    	    	}
    	    	Collections.sort(VfilterMhsPerAngkatan);
    	    	lim = VfilterMhsPerAngkatan.listIterator();
    	    	if(lim.hasNext()) {
    	    		Vector vtmp = new Vector();
    	    		ListIterator lit = vtmp.listIterator();
    	    		String brs = (String)lim.next();
    	    		StringTokenizer st = new StringTokenizer(brs,"`");
    	    		String prev_krklm = st.nextToken();
    	    		String prev_smawl = st.nextToken();
    	    		String prev_npmhs = st.nextToken();
    	    		String prev_nimhs = st.nextToken();
    	    		String prev_nmmhs = st.nextToken();
    	    		String prev_shift = st.nextToken();
    	    		String prev_stpid = st.nextToken();
    	    		lit.add(brs);
    	    		while(lim.hasNext()) {
    	    			brs = (String)lim.next();
        	    		st = new StringTokenizer(brs,"`");
        	    		String curr_krklm = st.nextToken();
    	    			String curr_smawl = st.nextToken();
        	    		String curr_npmhs = st.nextToken();
        	    		String curr_nimhs = st.nextToken();
        	    		String curr_nmmhs = st.nextToken();
        	    		String curr_shift = st.nextToken();
        	    		String curr_stpid = st.nextToken();
        	    		if(prev_krklm.equalsIgnoreCase(curr_krklm)) {
        	    			lit.add(brs);
        	    		}
        	    		else {
        	    			prev_krklm = new String(curr_krklm);
        	    			lif.add(vtmp);
        	    			vtmp = new Vector();
        	    			lit = vtmp.listIterator();
        	    			lit.add(brs);
        	    		}
        	    		//if(!lim.hasNext()) {
        	    		//	lif.add(vtmp);
        	    		//}
    	    		}
    	    		if(!lim.hasNext()) {
    	    			lif.add(vtmp);
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
    	return vf;
    }
    
    public Vector getMkSesuaiAngkatanPerMhs_Pasca(Vector filterMhsPerKurikulum, String target_thsms, int idkur) {
    	int sms_akhir =4;
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from MAKUR INNER JOIN MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=? and SEMESMAKUR=?");
    		ListIterator lim = null;
    		ListIterator lis = null;
    		if(filterMhsPerKurikulum!=null) {
    			lim = filterMhsPerKurikulum.listIterator();
    	    	while(lim.hasNext()) {
    	    		String brs = (String)lim.next();
    	    		StringTokenizer st = new StringTokenizer(brs,"`");
    	    		String krklm = st.nextToken();
    	    		String smawl = st.nextToken();
    	    		String npmhs = st.nextToken();
    	    		String nimhs = st.nextToken();
    	    		String nmmhs = st.nextToken();
    	    		String shift = st.nextToken();
    	    		String stpid = st.nextToken();
    	    		Vector v =Tool.returnTokensListThsmsTpAntara(smawl, target_thsms);
    	    		lif.add(brs);
    	    		int sms = v.size();
    	    		lif.add(""+sms);
    	    		Vector vtmp = new Vector();
    	    		ListIterator lit = vtmp.listIterator();
    	    		
    	    		stmt.setInt(1, idkur);
	    			
	    			
    	    		if(sms>=sms_akhir) {
    	    			//skripsi
    	    			//sms = 8; 
    	    			stmt.setString(2, ""+sms_akhir);
    	    			rs = stmt.executeQuery();
    	    			while(rs.next()) {
    	    				String idkmk = ""+rs.getLong("IDKMKMAKUL");
    	    				String kdkmk = rs.getString("KDKMKMAKUL");
    	    				String nakmk = rs.getString("NAKMKMAKUL");
    	    				int ttsks = rs.getInt("SKSTMMAKUL")+rs.getInt("SKSLPMAKUL")+rs.getInt("SKSPRMAKUL");
    	    				boolean skripsi = rs.getBoolean("FINAL_MK");
    	    				if(sms>sms_akhir) {
    	    					//if(skripsi) { 
    	    					//PASTI SKIRIPSE
        	    					lit.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
        	    				//}	
    	    				}
    	    				else {
    	    					lit.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
    	    				}
    	    			}
    	    		}
    	    		else if(sms<sms_akhir) {
    	    			stmt.setString(2, ""+sms);
    	    			rs = stmt.executeQuery();
    	    			while(rs.next()) {
    	    				String idkmk = ""+rs.getLong("IDKMKMAKUL");
    	    				String kdkmk = rs.getString("KDKMKMAKUL");
    	    				String nakmk = rs.getString("NAKMKMAKUL");
    	    				int ttsks = rs.getInt("SKSTMMAKUL")+rs.getInt("SKSLPMAKUL")+rs.getInt("SKSPRMAKUL");
    	    				boolean skripsi = rs.getBoolean("FINAL_MK");
    	    				lit.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
    	    			}
    	    		}
    	    		lif.add(vtmp);
    	    		
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
    	return vf;
    }
    
    public Vector getMkSesuaiAngkatanPerMhs_S1(Vector filterMhsPerKurikulum, String target_thsms, int idkur) {
    	int sms_akhir =8;
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from MAKUR INNER JOIN MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=? and SEMESMAKUR=?");
    		ListIterator lim = null;
    		ListIterator lis = null;
    		if(filterMhsPerKurikulum!=null) {
    			lim = filterMhsPerKurikulum.listIterator();
    	    	while(lim.hasNext()) {
    	    		String brs = (String)lim.next();
    	    		StringTokenizer st = new StringTokenizer(brs,"`");
    	    		String krklm = st.nextToken();
    	    		String smawl = st.nextToken();
    	    		String npmhs = st.nextToken();
    	    		String nimhs = st.nextToken();
    	    		String nmmhs = st.nextToken();
    	    		String shift = st.nextToken();
    	    		String stpid = st.nextToken();
    	    		Vector v =Tool.returnTokensListThsmsTpAntara(smawl, target_thsms);
    	    		lif.add(brs);
    	    		int sms = v.size();
    	    		lif.add(""+sms);
    	    		Vector vtmp = new Vector();
    	    		ListIterator lit = vtmp.listIterator();
    	    		
    	    		stmt.setInt(1, idkur);
	    			
	    			
    	    		if(sms>=sms_akhir) {
    	    			//skripsi
    	    			//sms = 8; 
    	    			stmt.setString(2, ""+sms_akhir);
    	    			rs = stmt.executeQuery();
    	    			while(rs.next()) {
    	    				String idkmk = ""+rs.getLong("IDKMKMAKUL");
    	    				String kdkmk = rs.getString("KDKMKMAKUL");
    	    				String nakmk = rs.getString("NAKMKMAKUL");
    	    				int ttsks = rs.getInt("SKSTMMAKUL")+rs.getInt("SKSLPMAKUL")+rs.getInt("SKSPRMAKUL");
    	    				boolean skripsi = rs.getBoolean("FINAL_MK");
    	    				if(sms>sms_akhir) {
    	    					if(skripsi) {
        	    					lit.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
        	    				}	
    	    				}
    	    				else {
    	    					lit.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
    	    				}
    	    			}
    	    		}
    	    		else if(sms<sms_akhir) {
    	    			stmt.setString(2, ""+sms);
    	    			rs = stmt.executeQuery();
    	    			while(rs.next()) {
    	    				String idkmk = ""+rs.getLong("IDKMKMAKUL");
    	    				String kdkmk = rs.getString("KDKMKMAKUL");
    	    				String nakmk = rs.getString("NAKMKMAKUL");
    	    				int ttsks = rs.getInt("SKSTMMAKUL")+rs.getInt("SKSLPMAKUL")+rs.getInt("SKSPRMAKUL");
    	    				boolean skripsi = rs.getBoolean("FINAL_MK");
    	    				lit.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
    	    			}
    	    		}
    	    		lif.add(vtmp);
    	    		
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
    	return vf;
    }
    
    public Vector getMkSesuaiAngkatanPerMhsDariSms1_Pasca(Vector filterMhsPerKurikulum, String thsms_max, int idkur) {
    	int sms_akhir =4;
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from MAKUR INNER JOIN MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=? and SEMESMAKUR=?");
    		ListIterator lim = null;
    		ListIterator lis = null;
    		if(filterMhsPerKurikulum!=null) {
    			lim = filterMhsPerKurikulum.listIterator();
    	    	while(lim.hasNext()) {
    	    		String brs = (String)lim.next();
    	    		StringTokenizer st = new StringTokenizer(brs,"`");
    	    		String krklm = st.nextToken();
    	    		String smawl = st.nextToken();
    	    		String npmhs = st.nextToken();
    	    		String nimhs = st.nextToken();
    	    		String nmmhs = st.nextToken();
    	    		String shift = st.nextToken();
    	    		String stpid = st.nextToken();
    	    		if(stpid.equalsIgnoreCase("B")) {
    	    			Vector v =Tool.returnTokensListThsmsTpAntara(smawl, thsms_max);
        	    		lif.add(brs);
        	    		int curr_total_sms = v.size();
        	    		
        	    		stmt.setInt(1, idkur);
        	    		int sms_no = 1;
    	    			while(sms_no<=curr_total_sms) {
    	    				Vector vtmp = new Vector();
            	    		ListIterator lit = vtmp.listIterator();
    	    				lif.add(""+sms_no);
    	    				if(sms_no>=sms_akhir) {
    	    	    			//skripsi
    	    	    			//sms = 8; 
    	    	    			stmt.setString(2, ""+sms_akhir);
    	    	    			rs = stmt.executeQuery();
    	    	    			while(rs.next()) {
    	    	    				String idkmk = ""+rs.getLong("IDKMKMAKUL");
    	    	    				String kdkmk = rs.getString("KDKMKMAKUL");
    	    	    				String nakmk = rs.getString("NAKMKMAKUL");
    	    	    				int ttsks = rs.getInt("SKSTMMAKUL")+rs.getInt("SKSLPMAKUL")+rs.getInt("SKSPRMAKUL");
    	    	    				boolean skripsi = rs.getBoolean("FINAL_MK");
    	    	    				if(sms_no>sms_akhir) {
    	    	    					if(skripsi) {
    	        	    					lit.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
    	        	    				}	
    	    	    				}
    	    	    				else {
    	    	    					lit.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
    	    	    				}
    	    	    			}
    	    	    		}
    	    	    		else if(sms_no<sms_akhir) {
    	    	    			stmt.setString(2, ""+sms_no);
    	    	    			rs = stmt.executeQuery();
    	    	    			while(rs.next()) {
    	    	    				String idkmk = ""+rs.getLong("IDKMKMAKUL");
    	    	    				String kdkmk = rs.getString("KDKMKMAKUL");
    	    	    				String nakmk = rs.getString("NAKMKMAKUL");
    	    	    				int ttsks = rs.getInt("SKSTMMAKUL")+rs.getInt("SKSLPMAKUL")+rs.getInt("SKSPRMAKUL");
    	    	    				boolean skripsi = rs.getBoolean("FINAL_MK");
    	    	    				lit.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
    	    	    			}
    	    	    		}
    	    	    		lif.add(vtmp);
    	    	    		sms_no++;
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
    	return vf;
    }
    
    public Vector getMkSesuaiAngkatanPerMhsDariSms1_S1(Vector filterMhsPerKurikulum, String thsms_max, int idkur) {
    	int sms_akhir =8;
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from MAKUR INNER JOIN MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=? and SEMESMAKUR=?");
    		ListIterator lim = null;
    		ListIterator lis = null;
    		if(filterMhsPerKurikulum!=null) {
    			lim = filterMhsPerKurikulum.listIterator();
    	    	while(lim.hasNext()) {
    	    		String brs = (String)lim.next();
    	    		StringTokenizer st = new StringTokenizer(brs,"`");
    	    		String krklm = st.nextToken();
    	    		String smawl = st.nextToken();
    	    		String npmhs = st.nextToken();
    	    		if(npmhs.equalsIgnoreCase("6420109100011")) {
    	    			////System.out.println("npmhs=="+npmhs);
    	    		}
    	    		String nimhs = st.nextToken();
    	    		String nmmhs = st.nextToken();
    	    		String shift = st.nextToken();
    	    		String stpid = st.nextToken();
    	    		if(stpid.equalsIgnoreCase("B")) {
    	    			Vector v =Tool.returnTokensListThsmsTpAntara(smawl, thsms_max);
        	    		lif.add(brs);
        	    		int curr_total_sms = v.size();
        	    		if(npmhs.equalsIgnoreCase("6420109100011")) {
        	    			////System.out.println("curr_total_sms=="+curr_total_sms);
        	    		}
        	    		stmt.setInt(1, idkur);
        	    		int sms_no = 1;
    	    			while(sms_no<=curr_total_sms) {
    	    				if(npmhs.equalsIgnoreCase("6420109100011")) {
            	    			////System.out.println("sms_no=="+sms_no);
            	    		}
    	    				Vector vtmp = new Vector();
            	    		ListIterator lit = vtmp.listIterator();
    	    				lif.add(""+sms_no);
    	    				if(sms_no>=sms_akhir) {
    	    	    			//skripsi
    	    	    			//sms = 8; 
    	    					if(npmhs.equalsIgnoreCase("6420109100011")) {
                	    			////System.out.println("here1-"+sms_no);
                	    		}
    	    	    			stmt.setString(2, ""+sms_akhir);
    	    	    			rs = stmt.executeQuery();
    	    	    			while(rs.next()) {
    	    	    				String idkmk = ""+rs.getLong("IDKMKMAKUL");
    	    	    				String kdkmk = rs.getString("KDKMKMAKUL");
    	    	    				String nakmk = rs.getString("NAKMKMAKUL");
    	    	    				int ttsks = rs.getInt("SKSTMMAKUL")+rs.getInt("SKSLPMAKUL")+rs.getInt("SKSPRMAKUL");
    	    	    				boolean skripsi = rs.getBoolean("FINAL_MK");
    	    	    				if(npmhs.equalsIgnoreCase("6420109100011")) {
                    	    			////System.out.println(nakmk+"-"+idkmk+"-"+skripsi);
                    	    		}
    	    	    				if(sms_no>sms_akhir) {
    	    	    					if(skripsi) {
    	    	    						if(npmhs.equalsIgnoreCase("6420109100011")) {
    	                    	    			////System.out.println("here1a-"+sms_no);
    	                    	    		}
    	        	    					lit.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
    	        	    				}
    	    	    					else {
    	    	    						if(npmhs.equalsIgnoreCase("6420109100011")) {
    	                    	    			////System.out.println("here1b-"+sms_no);
    	                    	    		}
    	    	    					}
    	    	    				}
    	    	    				else {
    	    	    					if(npmhs.equalsIgnoreCase("6420109100011")) {
    	                	    			////System.out.println("here2-"+sms_no);
    	                	    		}
    	    	    					lit.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
    	    	    				}
    	    	    			}
    	    	    		}
    	    	    		else if(sms_no<sms_akhir) {
    	    	    			stmt.setString(2, ""+sms_no);
    	    	    			if(npmhs.equalsIgnoreCase("6420109100011")) {
                	    			////System.out.println("here3-"+sms_no);
                	    		}
    	    	    			rs = stmt.executeQuery();
    	    	    			while(rs.next()) {
    	    	    				String idkmk = ""+rs.getLong("IDKMKMAKUL");
    	    	    				String kdkmk = rs.getString("KDKMKMAKUL");
    	    	    				String nakmk = rs.getString("NAKMKMAKUL");
    	    	    				int ttsks = rs.getInt("SKSTMMAKUL")+rs.getInt("SKSLPMAKUL")+rs.getInt("SKSPRMAKUL");
    	    	    				boolean skripsi = rs.getBoolean("FINAL_MK");
    	    	    				lit.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
    	    	    			}
    	    	    		}
    	    	    		lif.add(vtmp);
    	    	    		sms_no++;
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
    	return vf;
    }
    
    public Vector getMkSesuaiAngkatanPerMhsDariSms1_D3(Vector filterMhsPerKurikulum, String thsms_max, int idkur) {
    	int sms_akhir =6;
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from MAKUR INNER JOIN MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=? and SEMESMAKUR=?");
    		ListIterator lim = null;
    		ListIterator lis = null;
    		if(filterMhsPerKurikulum!=null) {
    			lim = filterMhsPerKurikulum.listIterator();
    	    	while(lim.hasNext()) {
    	    		String brs = (String)lim.next();
    	    		StringTokenizer st = new StringTokenizer(brs,"`");
    	    		String krklm = st.nextToken();
    	    		String smawl = st.nextToken();
    	    		String npmhs = st.nextToken();
    	    		String nimhs = st.nextToken();
    	    		String nmmhs = st.nextToken();
    	    		String shift = st.nextToken();
    	    		String stpid = st.nextToken();
    	    		if(stpid.equalsIgnoreCase("B")) {
    	    			Vector v =Tool.returnTokensListThsmsTpAntara(smawl, thsms_max);
        	    		lif.add(brs);
        	    		int curr_total_sms = v.size();
        	    		
        	    		stmt.setInt(1, idkur);
        	    		int sms_no = 1;
    	    			while(sms_no<=curr_total_sms) {
    	    				Vector vtmp = new Vector();
            	    		ListIterator lit = vtmp.listIterator();
    	    				lif.add(""+sms_no);
    	    				if(sms_no>=sms_akhir) {
    	    	    			//skripsi
    	    	    			//sms = 8; 
    	    	    			stmt.setString(2, ""+sms_akhir);
    	    	    			rs = stmt.executeQuery();
    	    	    			while(rs.next()) {
    	    	    				String idkmk = ""+rs.getLong("IDKMKMAKUL");
    	    	    				String kdkmk = rs.getString("KDKMKMAKUL");
    	    	    				String nakmk = rs.getString("NAKMKMAKUL");
    	    	    				int ttsks = rs.getInt("SKSTMMAKUL")+rs.getInt("SKSLPMAKUL")+rs.getInt("SKSPRMAKUL");
    	    	    				boolean skripsi = rs.getBoolean("FINAL_MK");
    	    	    				if(sms_no>sms_akhir) {
    	    	    					if(skripsi) {
    	        	    					lit.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
    	        	    				}	
    	    	    				}
    	    	    				else {
    	    	    					lit.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
    	    	    				}
    	    	    			}
    	    	    		}
    	    	    		else if(sms_no<sms_akhir) {
    	    	    			stmt.setString(2, ""+sms_no);
    	    	    			rs = stmt.executeQuery();
    	    	    			while(rs.next()) {
    	    	    				String idkmk = ""+rs.getLong("IDKMKMAKUL");
    	    	    				String kdkmk = rs.getString("KDKMKMAKUL");
    	    	    				String nakmk = rs.getString("NAKMKMAKUL");
    	    	    				int ttsks = rs.getInt("SKSTMMAKUL")+rs.getInt("SKSLPMAKUL")+rs.getInt("SKSPRMAKUL");
    	    	    				boolean skripsi = rs.getBoolean("FINAL_MK");
    	    	    				lit.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
    	    	    			}
    	    	    		}
    	    	    		lif.add(vtmp);
    	    	    		sms_no++;
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
    	return vf;
    }
    
    public Vector getMkSesuaiAngkatanPerMhs_D3(Vector filterMhsPerKurikulum, String target_thsms, int idkur) {
    	int sms_akhir = 6;
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from MAKUR INNER JOIN MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=? and SEMESMAKUR=?");
    		ListIterator lim = null;
    		ListIterator lis = null;
    		if(filterMhsPerKurikulum!=null) {
    			lim = filterMhsPerKurikulum.listIterator();
    	    	while(lim.hasNext()) {
    	    		String brs = (String)lim.next();
    	    		StringTokenizer st = new StringTokenizer(brs,"`");
    	    		String krklm = st.nextToken();
    	    		String smawl = st.nextToken();
    	    		String npmhs = st.nextToken();
    	    		String nimhs = st.nextToken();
    	    		String nmmhs = st.nextToken();
    	    		String shift = st.nextToken();
    	    		String stpid = st.nextToken();
    	    		Vector v =Tool.returnTokensListThsmsTpAntara(smawl, target_thsms);
    	    		lif.add(brs);
    	    		int sms = v.size();
    	    		lif.add(""+sms);
    	    		Vector vtmp = new Vector();
    	    		ListIterator lit = vtmp.listIterator();
    	    		
    	    		stmt.setInt(1, idkur);
	    			
	    			
    	    		if(sms>=sms_akhir) {
    	    			//skripsi
    	    			//sms = 8; 
    	    			stmt.setString(2, ""+sms_akhir);
    	    			rs = stmt.executeQuery();
    	    			while(rs.next()) {
    	    				String idkmk = ""+rs.getLong("IDKMKMAKUL");
    	    				String kdkmk = rs.getString("KDKMKMAKUL");
    	    				String nakmk = rs.getString("NAKMKMAKUL");
    	    				int ttsks = rs.getInt("SKSTMMAKUL")+rs.getInt("SKSLPMAKUL")+rs.getInt("SKSPRMAKUL");
    	    				boolean skripsi = rs.getBoolean("FINAL_MK");
    	    				if(sms>sms_akhir) {
    	    					if(skripsi) {
        	    					lit.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
        	    				}	
    	    				}
    	    				else {
    	    					lit.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
    	    				}
    	    			}
    	    		}
    	    		else if(sms<sms_akhir) {
    	    			stmt.setString(2, ""+sms);
    	    			rs = stmt.executeQuery();
    	    			while(rs.next()) {
    	    				String idkmk = ""+rs.getLong("IDKMKMAKUL");
    	    				String kdkmk = rs.getString("KDKMKMAKUL");
    	    				String nakmk = rs.getString("NAKMKMAKUL");
    	    				int ttsks = rs.getInt("SKSTMMAKUL")+rs.getInt("SKSLPMAKUL")+rs.getInt("SKSPRMAKUL");
    	    				boolean skripsi = rs.getBoolean("FINAL_MK");
    	    				lit.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
    	    			}
    	    		}
    	    		lif.add(vtmp);
    	    		
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
    	return vf;
    }
    
    
    public Vector addInfoNpm(Vector v_info_mhs) {
    	//Vector vf = new Vector();
    	//ListIterator lif = vf.listIterator();
    	if(v_info_mhs!=null && v_info_mhs.size()>0) {
    		try {
    			ListIterator li = v_info_mhs.listIterator();
        		Context initContext  = new InitialContext();    		
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select NPMHSMSMHS from CIVITAS where NIMHSMSMHS=?");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String no = st.nextToken();
        			String nimhs = st.nextToken();
        			stmt.setString(1, nimhs);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				String npmhs = rs.getString(1);
            			li.set(npmhs+"`"+brs);	
        			}
        			else {
        				//System.out.println(brs+" tdk ada di system");
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
    	
    	return v_info_mhs;
    }
    
    public Vector cekApaAdaKrsDanCekTrlsm(Vector v_info_mhs, String thsms) {
    	//Vector vf = new Vector();
    	//ListIterator lif = vf.listIterator();
    	if(v_info_mhs!=null && v_info_mhs.size()>0) {
    		try {
    			ListIterator li = v_info_mhs.listIterator();
        		Context initContext  = new InitialContext();    		
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select * from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=? limit 1");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			////System.out.println(brs);
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String npmhs = st.nextToken();
        			String no = st.nextToken();
        			String nimhs = st.nextToken();
        			String nmmhs = st.nextToken();
        			String smawl = st.nextToken();
        			String stpid = st.nextToken();
        			stmt.setString(1, thsms);
        			stmt.setString(2, npmhs);
        			rs = stmt.executeQuery();
        			
        			if(rs.next()) {
        				li.set(npmhs+"`"+smawl+"`"+stpid+"`yes");
        			}
        			else {
        				li.set(npmhs+"`"+smawl+"`"+stpid+"`non");
        			}
        		}
        		stmt = con.prepareStatement("select STMHS from TRLSM where THSMS=? and NPMHS=?");
        		li = v_info_mhs.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String npmhs = st.nextToken();
        			String smawl = st.nextToken();
        			String stpid = st.nextToken();
        			String trnlm = st.nextToken();
        			stmt.setString(1, thsms);
        			stmt.setString(2, npmhs);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				String stmhs = rs.getString(1);
        				li.set(brs+"`"+stmhs);
        			}
        			else {
        				li.set(brs+"`non");
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
    	
    	return v_info_mhs;
    }

    public Vector getInfoTrlsmFromLayarBiru(String thsms, String alamat_filename_mhs_aktif_with_txt) {
    	Vector v = null;
    	try {
    		v=Tool.bacaFileTxt(alamat_filename_mhs_aktif_with_txt, thsms);
    		ListIterator li = null;
    	
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		if(v!=null) {
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				////System.out.println(brs);
    			}
    		}	
    	//	
    	}
    	catch (Exception ex) {
    		ex.printStackTrace();
    	} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	} 
    	return v;
    }
    
    public Vector addInfoKurikulumIndividu(Vector v_info_mhs_baru) {
    	//Vector vf = new Vector();
    	//ListIterator lif = vf.listIterator();
    	if(v_info_mhs_baru!=null && v_info_mhs_baru.size()>0) {
    		try {
    			ListIterator li = v_info_mhs_baru.listIterator();
        		Context initContext  = new InitialContext();    		
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select KRKLMMSMHS from EXT_CIVITAS where NPMHSMSMHS=?");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String npmhs = st.nextToken();
        			String smawl = st.nextToken();
        			String stpid = st.nextToken();
        			//proses yg baru aja
        			if(stpid.equalsIgnoreCase("B")) {
        				stmt.setString(1, npmhs);
            			rs = stmt.executeQuery();
            			rs.next();
            			String idkur = ""+rs.getString(1);
            			//li.set(brs+"`"+idkur);	
            			li.set(idkur+"`"+smawl+"`"+npmhs+"`null`null`null`"+stpid);
        			}
        			else {
        				li.remove();
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
    	
    	return v_info_mhs_baru;
    }
    
    

    
    public Vector getMkSesuaiAngkatanPerMhsDariSms1(Vector filterMhsPerKurikulum, String thsms_max, String kdpst) {
    	String kdjen = Checker.getKdjen(kdpst);
    	int sms_akhir =0;
    	if(kdjen.equalsIgnoreCase("A")) {
    		sms_akhir =4;
    	}
    	else if(kdjen.equalsIgnoreCase("B")) {
    		sms_akhir =4;
    	}
    	else if(kdjen.equalsIgnoreCase("C")) {
    		sms_akhir =8;
    	}
    	else if(kdjen.equalsIgnoreCase("D")) {
    		sms_akhir =8;
    	}
    	else if(kdjen.equalsIgnoreCase("E")) {
    		sms_akhir =6;
    	}
    	////System.out.println("sms_akhir="+sms_akhir);
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from MAKUR INNER JOIN MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=? and SEMESMAKUR=?");
    		ListIterator lim = null;
    		ListIterator lis = null;
    		
    		if(filterMhsPerKurikulum!=null) {
    			lim = filterMhsPerKurikulum.listIterator();
    	    	while(lim.hasNext()) {
    	    		
    	    		String brs = (String)lim.next();
    	    		////System.out.println(brs);
    	    		StringTokenizer st = new StringTokenizer(brs,"`");
    	    		String idkur = st.nextToken();//tidak boleh null
    	    		String smawl = st.nextToken();
    	    		String npmhs = st.nextToken();
    	    		String nimhs = st.nextToken();
    	    		String nmmhs = st.nextToken();
    	    		String shift = st.nextToken();
    	    		String stpid = st.nextToken();
    	    		String thsms_sms = new String(smawl);
    	    		if(stpid.equalsIgnoreCase("B")) { //hanya prose mhs baru
    	    			Vector v =Tool.returnTokensListThsmsTpAntara(smawl, thsms_max);
        	    		lif.add(brs);
        	    		int curr_total_sms = v.size();
        	    		
        	    		stmt.setInt(1, Integer.parseInt(idkur));
        	    		int sms_no = 1;
    	    			while(sms_no<=curr_total_sms) {
    	    				Vector vtmp = new Vector();
            	    		ListIterator lit = vtmp.listIterator();
    	    				lif.add(""+sms_no+"`"+thsms_sms);
    	    				if(sms_no>=sms_akhir) {
    	    	    			//skripsi
    	    	    			//sms = 8; 
    	    	    			stmt.setString(2, ""+sms_akhir);
    	    	    			rs = stmt.executeQuery();
    	    	    			while(rs.next()) {
    	    	    				String idkmk = ""+rs.getLong("IDKMKMAKUL");
    	    	    				String kdkmk = rs.getString("KDKMKMAKUL");
    	    	    				String nakmk = rs.getString("NAKMKMAKUL");
    	    	    				int ttsks = rs.getInt("SKSTMMAKUL")+rs.getInt("SKSLPMAKUL")+rs.getInt("SKSPRMAKUL");
    	    	    				boolean skripsi = rs.getBoolean("FINAL_MK");
    	    	    				if(sms_no>sms_akhir) {
    	    	    					if(skripsi) {
    	        	    					lit.add(thsms_sms+"`"+npmhs+"`"+idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
    	        	    				}	
    	    	    				}
    	    	    				else {
    	    	    					lit.add(thsms_sms+"`"+npmhs+"`"+idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
    	    	    				}
    	    	    			}
    	    	    		}
    	    	    		else if(sms_no<sms_akhir) {
    	    	    			stmt.setString(2, ""+sms_no);
    	    	    			rs = stmt.executeQuery();
    	    	    			while(rs.next()) {
    	    	    				String idkmk = ""+rs.getLong("IDKMKMAKUL");
    	    	    				String kdkmk = rs.getString("KDKMKMAKUL");
    	    	    				String nakmk = rs.getString("NAKMKMAKUL");
    	    	    				int ttsks = rs.getInt("SKSTMMAKUL")+rs.getInt("SKSLPMAKUL")+rs.getInt("SKSPRMAKUL");
    	    	    				boolean skripsi = rs.getBoolean("FINAL_MK");
    	    	    				lit.add(thsms_sms+"`"+npmhs+"`"+idkmk+"`"+kdkmk+"`"+nakmk+"`"+ttsks+"`"+skripsi);
    	    	    			}
    	    	    		}
    	    	    		lif.add(vtmp);
    	    	    		sms_no++;
    	    	    		thsms_sms = Tool.returnNextThsmsGivenTpAntara(thsms_sms);
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
    	return vf;
    }
    
    public Vector getKrsMhsDgnNilaiTunda(Vector Vsdf_addInfoNpm, String thsms) {
    	ListIterator li = null;
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	try {
    		if(Vsdf_addInfoNpm!=null && Vsdf_addInfoNpm.size()>0) {
    			Context initContext  = new InitialContext();    		
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select * from TRNLM where NPMHSTRNLM=? and THSMSTRNLM=?");
    			//2020100000098`10`09710250005`HERDY PANDAPOTAN MARULITUA S`20091`B`dd-mm-yyyy
        		li = Vsdf_addInfoNpm.listIterator();
        		while(li.hasNext()) {
        			Vector v_tmp =new Vector();
        			ListIterator lit = v_tmp.listIterator();
        			Vector v_trnlm =new Vector();
        			ListIterator lit1 = v_trnlm.listIterator();
        			String brs = (String)li.next();
        			//System.out.println("brs1="+brs);
        			lit.add(brs);
        			////
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String npmhs = st.nextToken();
        			stmt.setString(1, npmhs);
        			stmt.setString(2, thsms);
        			rs = stmt.executeQuery();
        			boolean ada_krs = false;
        			boolean addToVector = false;
        			
        			if(rs.next()) {
        				ada_krs = true;
        				do {
        					String kdpst = rs.getString("KDPSTTRNLM");
        					String kdkmk = rs.getString("KDKMKTRNLM");
        					String idkmk = rs.getString("IDKMKTRNLM");
        					String nlakh = rs.getString("NLAKHTRNLM");
        					if(nlakh.equalsIgnoreCase("T")) {
        						addToVector = true;
        						lit1.add(kdpst+"`"+kdkmk+"`"+idkmk);	
        					}
        					
        					////System.out.println("lit1.add"+kdkmk+"`"+kdkmk+"`"+idkmk);
        				}
        				while(rs.next());
        			}
        			else {
        				addToVector = true;
        				lit1.add("ERROR TIDAK ADA KRS");
        				////System.out.println("lit1.addERROR TIDAK ADA KRS");
        			}
        			lit.add(v_trnlm);
        			
        			if(addToVector) {
        				lif.add(v_tmp);
        			}
        		}
        		
        		if (vf!=null) {
        			Vector v = new Vector();
        	    	li = v.listIterator();
        			stmt = con.prepareStatement("select STMHS from TRLSM where THSMS=? and NPMHS=?");
        			lif = vf.listIterator();
        			while(lif.hasNext()) {
        				Vector v_tmp = (Vector)lif.next();
        				ListIterator lit = v_tmp.listIterator();
        				while(lit.hasNext()) {
        					String brs = (String)lit.next();
        					StringTokenizer st = new StringTokenizer(brs,"`");
        					String npmhs = st.nextToken();
        					stmt.setString(1, thsms);
        					stmt.setString(2, npmhs);
        					rs = stmt.executeQuery();
        					String stmhs = "null";
        					if(rs.next()) {
        						stmhs = rs.getString(1);
        					}
        					Vector v_trnlm = (Vector) lit.next();
        					li.add(brs);
        					li.add(stmhs);
        					li.add(v_trnlm);
        				}
        			}
        			vf = v;
        		}
        		
        		if (vf!=null) {
        			stmt = con.prepareStatement("select FINAL_MK from MAKUR where IDKMKMAKUR=?");
        			lif = vf.listIterator();
        			while(lif.hasNext()) {
        				String brs1 = (String)lif.next();
        				//System.out.println("brs1="+brs1);
        				String brs2 = (String)lif.next();
        				//System.out.println("brs2="+brs2);
        				Vector v_trnlm = (Vector) lif.next();
        				ListIterator li1 = v_trnlm.listIterator();
        				while(li1.hasNext()) {
        					String brs3 = (String) li1.next();
        						//System.out.println("brs3="+brs3);
        					if(brs3.contains("ERROR")) {
        						
        					}
        					else {
        						
            					StringTokenizer st = new StringTokenizer(brs3,"`");
            					st.nextToken();
            					st.nextToken();
            					String idkmk = st.nextToken();
            					stmt.setInt(1, Integer.parseInt(idkmk));
            					rs = stmt.executeQuery();
            					rs.next();
            					li1.set(brs3+"`"+rs.getBoolean(1));
            					//System.out.println(brs3);
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
    	return vf;
    }
    
    
    public Vector getKrsMhsDgnNilaiTundaYgLulusAtThsms(String thsms) {
    	ListIterator li = null;
    	Vector Vsdf_addInfoNpm = new Vector();
    	li = Vsdf_addInfoNpm.listIterator();
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	try {
    		if(true) {
    			Context initContext  = new InitialContext();    		
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select NPMHS from TRLSM left join CIVITAS on NPMHS=NPMHSMSMHS where NPMHSMSMHS is null");
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String npm = rs.getString(1);
        			lif.add(npm);
        		}
        		lif = vf.listIterator();
        		stmt = con.prepareStatement("delete from TRLSM where NPMHS=?");
        		while(lif.hasNext()) {
        			String npmhs = (String) lif.next();
        			stmt.setString(1, npmhs);
        			int i = stmt.executeUpdate();
        			//System.out.println("delete "+npmhs+" = "+i );
        		}
        		
        		vf = new Vector();
            	lif = vf.listIterator();
        		
        		stmt = con.prepareStatement("select NPMHS from TRLSM where THSMS=? and STMHS=?");
        		stmt.setString(1, thsms);
        		stmt.setString(2, "L");
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String npmhs = rs.getString(1);
        			li.add(npmhs);
        		}
        		
        		stmt = con.prepareStatement("select * from TRNLM where NPMHSTRNLM=? and THSMSTRNLM=?");
    			//2020100000098`10`09710250005`HERDY PANDAPOTAN MARULITUA S`20091`B`dd-mm-yyyy
        		li = Vsdf_addInfoNpm.listIterator();
        		while(li.hasNext()) {
        			Vector v_tmp =new Vector();
        			ListIterator lit = v_tmp.listIterator();
        			Vector v_trnlm =new Vector();
        			ListIterator lit1 = v_trnlm.listIterator();
        			String brs = (String)li.next();
        			
        			lit.add(brs);
        			////
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String npmhs = st.nextToken();
        			//System.out.println("npmhs="+npmhs);
        			stmt.setString(1, npmhs);
        			stmt.setString(2, thsms);
        			rs = stmt.executeQuery();
        			boolean ada_krs = false;
        			boolean addToVector = false;
        			
        			if(rs.next()) {
        				ada_krs = true;
        				do {
        					String kdpst = rs.getString("KDPSTTRNLM");
        					String kdkmk = rs.getString("KDKMKTRNLM");
        					String idkmk = rs.getString("IDKMKTRNLM");
        					String nlakh = rs.getString("NLAKHTRNLM");
        					if(nlakh.equalsIgnoreCase("T")) {
        						addToVector = true;
        						lit1.add(kdpst+"`"+kdkmk+"`"+idkmk);	
        					}
        					
        					////System.out.println("lit1.add"+kdkmk+"`"+kdkmk+"`"+idkmk);
        				}
        				while(rs.next());
        			}
        			else {
        				addToVector = true;
        				lit1.add("ERROR TIDAK ADA KRS");
        				////System.out.println("lit1.addERROR TIDAK ADA KRS");
        			}
        			lit.add(v_trnlm);
        			
        			if(addToVector) {
        				lif.add(v_tmp);
        			}
        		}
        		
        		if (vf!=null) {
        			Vector v = new Vector();
        	    	li = v.listIterator();
        			stmt = con.prepareStatement("select STMHS from TRLSM where THSMS=? and NPMHS=?");
        			lif = vf.listIterator();
        			while(lif.hasNext()) {
        				Vector v_tmp = (Vector)lif.next();
        				ListIterator lit = v_tmp.listIterator();
        				while(lit.hasNext()) {
        					String brs = (String)lit.next();
        					StringTokenizer st = new StringTokenizer(brs,"`");
        					String npmhs = st.nextToken();
        					stmt.setString(1, thsms);
        					stmt.setString(2, npmhs);
        					rs = stmt.executeQuery();
        					String stmhs = "null";
        					if(rs.next()) {
        						stmhs = rs.getString(1);
        					}
        					Vector v_trnlm = (Vector) lit.next();
        					li.add(brs);
        					li.add(stmhs);
        					li.add(v_trnlm);
        				}
        			}
        			vf = v;
        		}
        		
        		if (vf!=null) {
        			stmt = con.prepareStatement("select FINAL_MK from MAKUR where IDKMKMAKUR=?");
        			lif = vf.listIterator();
        			while(lif.hasNext()) {
        				String brs1 = (String)lif.next();
        				//System.out.println("brs1a="+brs1);
        				String brs2 = (String)lif.next();
        				//System.out.println("brs2a="+brs2);
        				Vector v_trnlm = (Vector) lif.next();
        				ListIterator li1 = v_trnlm.listIterator();
        				while(li1.hasNext()) {
        					String brs3 = (String) li1.next();
        					//System.out.println("brs3="+brs3);
        					if(brs3.contains("ERROR")) {
        						
        					}
        					else {
        						
            					StringTokenizer st = new StringTokenizer(brs3,"`");
            					st.nextToken();
            					st.nextToken();
            					String idkmk = st.nextToken();
            					stmt.setInt(1, Integer.parseInt(idkmk));
            					rs = stmt.executeQuery();
            					rs.next();
            					li1.set(brs3+"`"+rs.getBoolean(1));
            					//System.out.println("brs3="+brs3);
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
    	return vf;
    }


}

package beans.tools;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;
import java.util.LinkedHashSet;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.owasp.esapi.ESAPI;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
/**
 * Session Bean implementation class Tool
 */
@Stateless
@LocalBean
public class ToolKrsKhs {

    /**
     * Default constructor. 
     */
    public ToolKrsKhs() {
        // TODO Auto-generated constructor stub
    }
    
    public static Vector sortRiwayatKrs(Vector V_sdb_getHistoryKrsKhs_v2) {
    	/*
    	 * Fungsi ini berguna untuk sorting riwayat krs berdasarkan thsms + sms pendek
    	 * dimana thsms diakhiri "A" maka ini = winter (thsms ganjil + angka 1 = dibuat 6 digit untuk kebuuhan sorting)
    	 * dan B berati diakhiri 21 (genap + angka 1)
    	 * Setelah sorting selesai thsms pendek dibalik dgn format akhiran A / B
    	 */
    	ListIterator li = null, lit=null;;
    	Vector v_tmp =null;
    	
    	if(V_sdb_getHistoryKrsKhs_v2!=null && V_sdb_getHistoryKrsKhs_v2.size()>0) {
    		li = V_sdb_getHistoryKrsKhs_v2.listIterator();
    		v_tmp = new Vector();
    		lit = v_tmp.listIterator();
    		while(li.hasNext()) {
    			
    			String thsms = (String)li.next();
    			//System.out.println("thsms="+thsms);
    			String kdkmk = (String)li.next();
    			String nakmk = (String)li.next();
				String nlakh = (String)li.next();
				String bobot = (String)li.next();
				String sksmk = (String)li.next();
				String kelas = (String)li.next();
				String sksem = (String)li.next();
				String nlips = (String)li.next();
				String skstt = (String)li.next();
				String nlipk = (String)li.next();	
				String shift = (String)li.next();	
				String krsdown = (String)li.next();
				String khsdown = (String)li.next();
				String bakprove = (String)li.next();
				String paprove = (String)li.next();
				String note = (String)li.next();
				String lock = (String)li.next();
				String baukprove = (String)li.next();
				//tambahan
				String idkmk = (String)li.next();
				String addReq = (String)li.next();
				String drpReq = (String)li.next();
				String npmPa = (String)li.next();
				String npmBak = (String)li.next();
				String npmBaa = (String)li.next();
				String npmBauk = (String)li.next();
				String baaProve = (String)li.next();
				String ktuProve = (String)li.next();
				String dknProve = (String)li.next();
				String npmKtu = (String)li.next();
				String npmDekan = (String)li.next();
				String lockMhs = (String)li.next();
				String kodeKampus = (String)li.next();
				String cuid = (String)li.next();
				String cuid_init = (String)li.next();
				String npmdos = (String)li.next();
				String nodos = (String)li.next();
				String npmasdos = (String)li.next();
				String noasdos = (String)li.next();
				String nmmdos = (String)li.next();
				String nmmasdos = (String)li.next(); 
				String nlakh_by_dsn = (String)li.next();
				
				
				thsms = thsms.replace("A", "11");
				thsms = thsms.replace("B", "21");
				
				String tmp = thsms+"-"+kdkmk+"~"+nakmk+"~"+nlakh+"~"+bobot+"~"+sksmk+"~"+kelas+"~"+sksem+"~"+nlips+"~"+skstt+"~"+nlipk+"~"+shift+"~"+krsdown+"~"+khsdown+"~"+bakprove+"~"+paprove+"~"+note+"~"+lock+"~"+baukprove+"~"+idkmk+"~"+addReq+"~"+drpReq+"~"+npmPa+"~"+npmBak+"~"+npmBaa+"~"+npmBauk+"~"+baaProve+"~"+ktuProve+"~"+dknProve+"~"+npmKtu+"~"+npmDekan+"~"+lockMhs+"~"+kodeKampus+"~"+cuid+"~"+cuid_init+"~"+npmdos+"~"+nodos+"~"+npmasdos+"~"+noasdos+"~"+nmmdos+"~"+nmmasdos+"~"+nlakh_by_dsn;
				while(tmp.contains("~~")) {
					tmp = tmp.replace("~~", "~null~");
				}
				
				//System.out.println("add="+tmp);
				lit.add(tmp);
    		}
    		Collections.sort(v_tmp);
    		
    		V_sdb_getHistoryKrsKhs_v2 = new Vector();
    		li = V_sdb_getHistoryKrsKhs_v2.listIterator();
    		lit = v_tmp.listIterator();
    		while(lit.hasNext()) {
    			String brs = (String)lit.next();
    			StringTokenizer st = new StringTokenizer(brs,"-");
    			String thsms = st.nextToken();
    			brs = brs.substring(thsms.length()+1, brs.length());
    			st = new StringTokenizer(brs,"~");
				String kdkmk = st.nextToken();
				String nakmk = st.nextToken();
				String nlakh = st.nextToken();
				String bobot = st.nextToken();
				String sksmk = st.nextToken();
				String kelas = st.nextToken();
				String sksem = st.nextToken();
				String nlips = st.nextToken();
				String skstt = st.nextToken();
				String nlipk = st.nextToken();	
				String shift = st.nextToken();	
				String krsdown = st.nextToken();
				String khsdown = st.nextToken();
				String bakprove = st.nextToken();
				String paprove = st.nextToken();
				String note = st.nextToken();
				String lock = st.nextToken();
				String baukprove = st.nextToken();
				//tambahan
				String idkmk = st.nextToken();
				String addReq = st.nextToken();
				String drpReq = st.nextToken();
				String npmPa = st.nextToken();
				String npmBak = st.nextToken();
				String npmBaa = st.nextToken();
				String npmBauk = st.nextToken();
				String baaProve = st.nextToken();
				String ktuProve = st.nextToken();
				String dknProve = st.nextToken();
				String npmKtu = st.nextToken();
				String npmDekan = st.nextToken();
				String lockMhs = st.nextToken();
				String kodeKampus = st.nextToken();
				String cuid = st.nextToken();
				String cuid_init = st.nextToken();
				String npmdos = st.nextToken();
				String nodos = st.nextToken();
				String npmasdos = st.nextToken();
				String noasdos = st.nextToken();
				String nmmdos = st.nextToken();
				String nmmasdos = st.nextToken(); 
				String nlakh_by_dsn = st.nextToken();
				
				if(thsms.length()==6) {
					if(thsms.endsWith("11")) {
						thsms = thsms.substring(0, 4)+"A";
					}
					else if(thsms.endsWith("21")) {
						thsms = thsms.substring(0, 4)+"B";
					}
				}
				//String tmp =  thsms+"~"+kdkmk+"~"+nakmk+"~"+nlakh+"~"+bobot+"~"+sksmk+"~"+kelas+"~"+sksem+"~"+nlips+"~"+skstt+"~"+nlipk+"~"+	shift+"~"+	krsdown+"~"+khsdown+"~"+bakprove+"~"+paprove+"~"+note+"~"+lock+"~"+baukprove+"~"+idkmk+"~"+addReq+"~"+drpReq+"~"+npmPa+"~"+npmBak+"~"+npmBaa+"~"+npmBauk+"~"+baaProve+"~"+ktuProve+"~"+dknProve+"~"+npmKtu+"~"+npmDekan+"~"+lockMhs+"~"+kodeKampus+"~"+cuid+"~"+cuid_init+"~"+npmdos+"~"+nodos+"~"+npmasdos+"~"+noasdos+"~"+nmmdos+"~"+nmmasdos+"~"+ nlakh_by_dsn;
    			//lit.set(tmp);
    			li.add(thsms);
				li.add(kdkmk);
				li.add(nakmk);
				li.add(nlakh);
				li.add(bobot);
				li.add(sksmk);
				li.add(kelas);
				li.add(sksem);
				li.add(nlips);
				li.add(skstt);
				li.add(nlipk);	
				li.add(shift);	
				li.add(krsdown);
				li.add(khsdown);
				li.add(bakprove);
				li.add(paprove);
				li.add(note);
				li.add(lock);
				li.add(baukprove);
				//tambahan
				li.add(idkmk);
				li.add(addReq);
				li.add(drpReq);
				li.add(npmPa);
				li.add(npmBak);
				li.add(npmBaa);
				li.add(npmBauk);
				li.add(baaProve);
				li.add(ktuProve);
				li.add(dknProve);
				li.add(npmKtu);
				li.add(npmDekan);
				li.add(lockMhs);
				li.add(kodeKampus);
				li.add(cuid);
				li.add(cuid_init);
				li.add(npmdos);
				li.add(nodos);
				li.add(npmasdos);
				li.add(noasdos);
				li.add(nmmdos);
				li.add(nmmasdos); 
				li.add(nlakh_by_dsn);
    		}
    		
    		//li = v_tmp.listIterator();
    		//while(li.hasNext()) {
    		//	String brs = (String)li.next();
    		//System.out.println(brs);
    		//}
    		
    	}
    	//System.out.println("kok ngga kesini");
    	//li = v_tmp.listIterator();
		//while(li.hasNext()) {
		//	String brs = (String)li.next();
		//System.out.println(brs);
		//}
    	return V_sdb_getHistoryKrsKhs_v2;
    	
    }
    
    

    
 }

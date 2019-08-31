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
import org.apache.poi.ss.usermodel.DataFormatter;
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
public class Tool {

    /**
     * Default constructor. 
     */
    public Tool() {
        // TODO Auto-generated constructor stub
    }
    
    public static Vector combine2VectorSameStructureAndRemoveDuplicate(Vector v1, Vector v2) {
    	Vector v_combine = null;
    	try {
    		if(v1!=null && v2==null) {
        		v_combine = Tool.removeDuplicateFromVector(v1); 
        	}
        	else if(v1==null && v2!=null) {
        		v_combine = Tool.removeDuplicateFromVector(v2); 
        	}
        	else if(v1!=null && v2!=null) {
        		ListIterator li1 = v1.listIterator();
        		ListIterator li2 = v2.listIterator();
        		v_combine = new Vector();
        		ListIterator li = v_combine.listIterator();
        		while(li1.hasNext()) {
        			String brs = (String)li1.next();
        			li.add(brs);
        		}
        		while(li2.hasNext()) {
        			String brs = (String)li2.next();
        			li.add(brs);
        		}
        		v_combine = Tool.removeDuplicateFromVector(v_combine); 
        		Collections.sort(v_combine);
        	}	
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	return v_combine;
    }
    
    public static String cetakWebFormat(String words) {
    	/*
    	if(!Checker.isStringNullOrEmpty(words)) {
    		while(words.contains("\n")) {
    			words = words.replace("\n", "<br>");
			}
    		while(words.contains(" ")) {
    			words = words.replace(" ", "&nbsp");
			}
    	}
    	*/
    	cetakWebFormat(words, "");
    	return words;
    }
    
    public static String cetakWebFormat(String words, String return_word_if_null) {
    	if(!Checker.isStringNullOrEmpty(words)) {
    		while(words.contains("\n")) {
    			words = words.replace("\n", "<br>");
			}
    		while(words.contains(" ")) {
    			words = words.replace(" ", "&nbsp");
			}
    	}
    	else {
    		words=return_word_if_null;
    	}
    	return words;
    }
    
    public static String cetakWebFormatTextarea(String words, String return_word_if_null) {
    	if(!Checker.isStringNullOrEmpty(words)) {
    		words = words.trim();
    		while(words.contains("<br>")) {
    			words = words.replace("<br>","\n");
			}
    		while(words.contains(" ")) {
    			words = words.replace(" ", "&nbsp");
			}
    	}
    	else {
    		words=return_word_if_null;
    	}
    	return words;
    }
    
    public static String cleanAndTrimToken(String tkn, String seperator) {
    	String tmp = null;
    	if(tkn!=null) {
    		//System.out.println("1");
    		tkn = tkn.trim();
    		if(tkn.startsWith(seperator)) { //pastiin awal & akhir bukan seperator
    			tkn = "null"+tkn;
    		}
    		//System.out.println("2");
    		if(tkn.endsWith(seperator)) { //pastiin awal & akhir bukan seperator
    			tkn = tkn+"null";
    		}
    		while(tkn.contains(seperator+seperator)) {
    			tkn=tkn.replaceAll(seperator+seperator, seperator+"null"+seperator);
    		}
    		//System.out.println("6");
    		while(tkn.contains(seperator+" "+seperator)) {
    			tkn=tkn.replaceAll(seperator+" "+seperator, seperator+"null"+seperator);
    		}
    		//System.out.println("7");
    		while(tkn.contains(seperator+"-"+seperator)) {
    			tkn=tkn.replaceAll(seperator+"-"+seperator, seperator+"null"+seperator);
    		}
    		//System.out.println("3");
    		StringTokenizer st = new StringTokenizer(tkn,seperator);
    		tkn = new String();
    		//System.out.println("4");
    		while(st.hasMoreTokens()) {
    			tmp = new String(st.nextToken().trim());
    			if(tmp.equalsIgnoreCase(seperator)) {
    				tmp = "null";
    			}
    			tkn = tkn+tmp;
    			if(st.hasMoreTokens()) {
    				tkn = tkn+seperator;
    			}
    		}
    		//System.out.println("5");
    		
    		//System.out.println("8");
    		if(tkn.endsWith(seperator)) {
    			tkn = tkn+"null";
    		}
    		//System.out.println("9");
    	}
    	return tkn;
    }
    
    public static boolean isThsmsEqualsSmawl(String thsms,String smawl) {
    	boolean sama = false;
    	//ubah jadi lima digit semua
    	if(thsms!=null && thsms.length()==6) {
    		thsms = thsms.substring(0,4)+thsms.substring(5,6);
    	}
    	if(smawl!=null && smawl.length()==6) {
    		smawl = smawl.substring(0,4)+smawl.substring(5,6);
    	}
    	if(thsms!=null && smawl!=null && thsms.equalsIgnoreCase(smawl)) {
    		sama = true;
    	}
    	return sama;
    }
    
    public static java.sql.Date setDefaultBlawl(String thsms_mk_final_pertama) {
    	java.sql.Date tgl = null;
    	String thn = thsms_mk_final_pertama.substring(0, 4);
    	
    	if(thsms_mk_final_pertama.endsWith("1")) {
    		String tmp = thn+"-08-01";
    		tgl = java.sql.Date.valueOf(tmp);
    	}
    	else if(thsms_mk_final_pertama.endsWith("2")) {
    		String tmp = (Integer.parseInt(thn)+1)+"-03-01";
    		tgl = java.sql.Date.valueOf(tmp);
    	}
    	return tgl;
    }
    
    public static java.sql.Date setDefaultBlakh(java.sql.Date tglls) {
    	String tmp = tglls.toString();
    	//System.out.println("tanggal="+tmp);
    	StringTokenizer st = new StringTokenizer(tmp,"-");
    	String thn = st.nextToken();
    	String bln = st.nextToken();
    	String tgl = st.nextToken();
    	
    	return java.sql.Date.valueOf(thn+"-"+bln+"-01");
    }
    
    public static String returnStrNullIfEmpty(String target_string) {
    	if(Checker.isStringNullOrEmpty(target_string)) {
    		target_string = "null";
    	}
    	return target_string;
    }
    
	public static java.sql.Timestamp getCurrentTimestamp() {
		java.sql.Timestamp curr_time = new java.sql.Timestamp(System.currentTimeMillis());
		return curr_time; 
	}
    
    public static long compareTimestamp(String mode, java.sql.Timestamp older_timestamp, java.sql.Timestamp newer_timestamp ) {
		long beda=0;
		try {
    		long diff = newer_timestamp.getTime() - older_timestamp.getTime();
			long diffSeconds = diff / 1000;
			long diffMinutes = diff / (60 * 1000);
			long diffHours = diff / (60 * 60 * 1000);
			long diffDays = diff / (24 * 60 * 60 * 1000);
			if(mode.equalsIgnoreCase("ss")||mode.equalsIgnoreCase("detik")||mode.equalsIgnoreCase("second")) {
				beda = diffSeconds;	
			}
			else if(mode.equalsIgnoreCase("mm")||mode.equalsIgnoreCase("menit")||mode.equalsIgnoreCase("minute")) {
				beda = diffMinutes;
			}
			else if(mode.equalsIgnoreCase("hh")||mode.equalsIgnoreCase("jam")||mode.equalsIgnoreCase("hour")) {
				beda = diffHours;
			}
			else if(mode.equalsIgnoreCase("dd")||mode.equalsIgnoreCase("hari")||mode.equalsIgnoreCase("day")) {
				beda = diffDays;
			}
			
			//System.out.println(beda);
		}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		return beda;
	}
    
    public static void setCellValue(Workbook wb, int sheet_no, String cell_no, String cell_type, String cell_value) {
    	try {
    		CellStyle cellStyle = wb.createCellStyle();
    	    Sheet sheet = wb.getSheetAt(sheet_no);
    	    CreationHelper createHelper = wb.getCreationHelper();
    	    Row row = null;
    	    Cell cell = null;	
    	    cell_no = cell_no.toUpperCase();
    		int kolom_no = cell_no.charAt(0)-'A';
    		int baris_no = Integer.parseInt(cell_no.substring(1, cell_no.length()))-1;
    	    row = sheet.getRow(baris_no);
			if(row == null) {
				row = sheet.createRow(baris_no);
			}
			cell = row.getCell(kolom_no);
			if (cell == null) {
				cell = row.createCell(kolom_no);
			}    
			if(cell_type.equalsIgnoreCase("string")) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if(Checker.isStringNullOrEmpty(cell_value)) {
					cell.setCellType(Cell.CELL_TYPE_BLANK);
				}
				else {
					cell.setCellValue(cell_value);
				}	
			}
			else if(cell_type.equalsIgnoreCase("date")) {
    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yyyy")); 
    			if(Checker.isStringNullOrEmpty(cell_value)) {
					cell.setCellType(Cell.CELL_TYPE_BLANK);
				}
				else {
					cell.setCellValue(java.sql.Date.valueOf(cell_value));
					cell.setCellStyle(cellStyle);
				}
    		}
			else if(cell_type.equalsIgnoreCase("double")||cell_type.equalsIgnoreCase("long")||cell_type.equalsIgnoreCase("int")||cell_type.equalsIgnoreCase("numeric")||cell_type.equalsIgnoreCase("decimal")) {
    			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				if(Checker.isStringNullOrEmpty(cell_value)) {
					cell.setCellType(Cell.CELL_TYPE_BLANK);
				}
				else {
					cell.setCellValue(cell_value);
				}	
    		}
			else if(cell_type.equalsIgnoreCase("time")) {
    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("h:mm:ss")); 
    			if(Checker.isStringNullOrEmpty(cell_value)) {
					cell.setCellType(Cell.CELL_TYPE_BLANK);
				}
				else {
					cell.setCellValue(java.sql.Date.valueOf(cell_value));
					cell.setCellStyle(cellStyle);
				}
    		}
    		else if(cell_type.equalsIgnoreCase("boolean")) {
    			cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
				if(Checker.isStringNullOrEmpty(cell_value)) {
					cell.setCellType(Cell.CELL_TYPE_BLANK);
				}
				else {
					cell.setCellValue(cell_value);
				}
    		}
    		else if(cell_type.equalsIgnoreCase("timestamp")) {
    			if(Checker.isStringNullOrEmpty(cell_value)) {
					cell.setCellType(Cell.CELL_TYPE_BLANK);
				}
				else {
					cell.setCellValue(java.sql.Timestamp.valueOf(cell_value));
					cell.setCellStyle(cellStyle);
				}
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    
    
    /*
     * masih error
     
    public static String removeRedundanPath(String url) {
    	StringTokenizer st = new StringTokenizer(url,"/");
    	String prev="";
    	String nuUrl="";
    	if(st.hasMoreTokens()) {
    		prev = st.nextToken();
    		nuUrl = ""+prev;
    		while(st.hasMoreTokens()) {
    			String current = st.nextToken();
    			if(!current.equalsIgnoreCase(prev)) {
    				nuUrl=nuUrl+"/"+current;
    				prev = ""+current;
    			}
    		}
    	}
    	//nuUrl=nuUrl.replace("//", "/");
    	return nuUrl;
    }
    */
    public static String getTokenKe(String target_string,int norut, String char_pemisah) {
    	String tmp = "";
    	StringTokenizer st = new StringTokenizer(target_string,char_pemisah);
    	if(st.countTokens()>=norut) {
    		for(int i=0;i<norut;i++) {
        		tmp=st.nextToken();
        	}	
    	}
    	
    	return tmp;
    }
    
    
	public static String sortListTkn(String brs) {
    	String tmp = "";
    	if(brs!=null && !Checker.isStringNullOrEmpty(brs)) {
    		String seperator = "`";
    		StringTokenizer st = null;
    		if(brs.contains(seperator)) {
    			st = new StringTokenizer(brs,seperator);
    		}
    		else {
    			seperator=",";
    			if(brs.contains(seperator)) {
        			st = new StringTokenizer(brs,seperator);
        		}
    			else {
    				seperator="-";
        			if(brs.contains(seperator)) {
            			st = new StringTokenizer(brs,seperator);
            		}	
    			}
    		}
    		
    		if(st.hasMoreTokens()) {
    			Vector v = new Vector();
        		ListIterator li = v.listIterator();
        		do {
        			String tkn = st.nextToken();	
        			li.add(tkn);
        		}
        		while(st.hasMoreTokens());
    			Collections.sort(v);
    			li = v.listIterator();
    			while(li.hasNext()) {
    				tmp = tmp +(String)li.next();
    				if(li.hasPrevious()) {
    					tmp = tmp + seperator;
    				}
    			}
    		}
    	}
    	return tmp;
    }
    
    public static String getTokenKe(String target_string,int norut) {
    	String tmp = "";
    	StringTokenizer st = new StringTokenizer(target_string);
    	if(st.countTokens()>=norut) {
    		for(int i=0;i<norut;i++) {
        		tmp=st.nextToken();
        	}
    	}
    	
    	return tmp;
    }
    
    public static Vector removeDuplicateSdb_getMostRecentMsg_with_Sdb_getUnreadMsg(Vector vUnread, Vector vRecent) {
    	ListIterator liu=null, lir=null;
    	Vector vtmp = new Vector();
    	ListIterator litmp = vtmp.listIterator();
    	if(vUnread!=null && vRecent!=null) {
    		liu = vUnread.listIterator();
    		
    		while(liu.hasNext()) {
    			boolean match = false;
    			String unread = (String) liu.next();
    			//System.out.println("unread="+unread);
    			StringTokenizer st = new StringTokenizer(unread,"||");
    			String topik_idTopik = st.nextToken();
    			String topik_conten = st.nextToken();
    			String topik_npmhsCreator = st.nextToken();
    			String topik_nmmhsCreator = st.nextToken();
    			String topik_creatorObjId = st.nextToken();
    			String topik_creatorObjNickname = st.nextToken();
    			String topik_targetKdpst = st.nextToken();
    			String topik_targetNpmhs = st.nextToken();
    			String topik_targetSmawl = st.nextToken();
    			String topik_targetObjId = st.nextToken();
    			String topik_targetObjNickname = st.nextToken();
    			String topik_targetGroupId = st.nextToken();
    			String topik_groupPwd = st.nextToken();
    			String topik_shownToGroupOnly = st.nextToken();
    			String topik_deletedByCreator = st.nextToken();
    			String topik_hidenAtCreator = st.nextToken();
    			String topik_pinedAtCreator = st.nextToken();
    			String topik_markedAsReadAtCreator = st.nextToken();
    			String topik_deletedAtTarget = st.nextToken();
    			String topik_hidenAtTarget = st.nextToken();
    			String topik_pinedAtTarget = st.nextToken();
    			String topik_markedAsReasAsTarget = st.nextToken();
    			String topik_creatorAsAnonymous = st.nextToken();
    			String topik_creatorIsPetugas = st.nextToken();
    			String topik_updtm = st.nextToken();
    			String sub_id = st.nextToken();
    			String sub_idTopik = st.nextToken();
    			String sub_comment = st.nextToken();
    			String sub_npmhsSender = st.nextToken();
    			String sub_nmmhsSender = st.nextToken();
    			String sub_anonymousReply = st.nextToken();
    			String sub_shownToCreatorObly = st.nextToken();
    			String sub_commenterIsPetugas = st.nextToken();
    			String sub_markedAsReadAtCreator = st.nextToken();
    			String sub_markedAsReadAtSender = st.nextToken();
    			String sub_objNicknameSender = st.nextToken();
    			String sub_npmhsReceiver = st.nextToken();
    			String sub_nmmhsReceiver = st.nextToken();
    			String sub_objNicknameReceiver = st.nextToken();
    			String sub_updtm = st.nextToken();
    			lir = vRecent.listIterator();
    			while(lir.hasNext() && !match) {
    				String recent = (String) lir.next();
       				st = new StringTokenizer(recent,"||");
       				String topik_idTopik1 = st.nextToken();
       				String topik_conten1 = st.nextToken();
      				String topik_npmhsCreator1 = st.nextToken();
       				String topik_nmmhsCreator1 = st.nextToken();
       				String topik_creatorObjId1 = st.nextToken();
       				String topik_creatorObjNickname1 = st.nextToken();
       				String topik_targetKdpst1 = st.nextToken();
       				String topik_targetNpmhs1 = st.nextToken();
       				String topik_targetSmawl1 = st.nextToken();
       				String topik_targetObjId1 = st.nextToken();
       				String topik_targetObjNickname1 = st.nextToken();
       				String topik_targetGroupId1 = st.nextToken();
       				String topik_groupPwd1 = st.nextToken();
       				String topik_shownToGroupOnly1 = st.nextToken();
       				String topik_deletedByCreator1 = st.nextToken();
       				String topik_hidenAtCreator1 = st.nextToken();
       				String topik_pinedAtCreator1 = st.nextToken();
       				String topik_markedAsReadAtCreator1 = st.nextToken();
       				String topik_deletedAtTarget1 = st.nextToken();
       				String topik_hidenAtTarget1 = st.nextToken();
       				String topik_pinedAtTarget1 = st.nextToken();
       				String topik_markedAsReasAsTarget1 = st.nextToken();
       				String topik_creatorAsAnonymous1 = st.nextToken();
       				String topik_creatorIsPetugas1 = st.nextToken();
       				String topik_updtm1 = st.nextToken();
       				String sub_id1 = st.nextToken();
       				String sub_idTopik1 = st.nextToken();
       				String sub_comment1 = st.nextToken();
       				String sub_npmhsSender1 = st.nextToken();
       				String sub_nmmhsSender1 = st.nextToken();
       				String sub_anonymousReply1 = st.nextToken();
       				String sub_shownToCreatorObly1 = st.nextToken();
       				String sub_commenterIsPetugas1 = st.nextToken();
       				String sub_markedAsReadAtCreator1 = st.nextToken();
       				String sub_markedAsReadAtSender1 = st.nextToken();
       				String sub_objNicknameSender1 = st.nextToken();
       				String sub_npmhsReceiver1 = st.nextToken();
       				String sub_nmmhsReceiver1 = st.nextToken();
       				String sub_objNicknameReceiver1 = st.nextToken();
       				String sub_updtm1 = st.nextToken();
       				if((topik_idTopik.equalsIgnoreCase(topik_idTopik1) && sub_id.equalsIgnoreCase(sub_id1)) || (sub_id.equalsIgnoreCase(sub_id1) && !sub_id.equalsIgnoreCase("null") && !sub_id.equalsIgnoreCase("0"))  ) {
       				//if(topik_idTopik.equalsIgnoreCase(topik_idTopik1) || sub_id.equalsIgnoreCase(sub_id1)) {
       					//System.out.println("match topik_idTopik="+topik_idTopik+",topik_idTopik1="+topik_idTopik1+",sub_id="+sub_id+",sub_id1="+sub_id1);
       					match = true;
       					lir.remove();
       				}
       				
   				}
    			
    			//kalo untread ngga ada match maka artinya belum pernah dapat balasan jadi harus ditambah ke lir - krn lir hanya ada dari SUBTOPIK
    			//if(!match) {
    			//	litmp.add(unread);
    			//}
    		}
    	}
    	//litmp = vtmp.listIterator();
		//while(litmp.hasNext()) {
		//	String recent = (String) litmp.next();
		//	//System.out.println("lier="+recent);
		//}	
    	return vRecent;			
    }
    
    public static String capFirstLetterInWord(String tkn) {
    	String upd = "";
    	if(tkn!=null) {
    		StringTokenizer st = new StringTokenizer(tkn);
    		while(st.hasMoreTokens()) {
    			String tmp = st.nextToken();
    			String first_letter = tmp.substring(0,1);
    			first_letter = first_letter.toUpperCase();
    			String the_rest = tmp.substring(1,tmp.length());
    			the_rest=the_rest.toLowerCase();
    			upd = upd+first_letter+the_rest;
    			if(st.hasMoreTokens()) {
    				upd = upd+" ";
    			}
    		}
    	}
    	return upd;
    }
    
    public static Vector returnTokensListThsms_v1(String thsms1, String thsms2, String kdpst) {
    	/*
    	 * posisi thsms besar ato kecil = bebas
    	 */
    	
    	Vector v = null;
    	ListIterator li = null;
    	//System.out.println(thsms1+" sd "+thsms2);
    	//System.out.println("2016A".compareToIgnoreCase(thsms2));
    	//System.out.println("20161A".compareToIgnoreCase("20161"));
    	//System.out.println("20161A".compareToIgnoreCase("20162"));
    	//System.out.println("20162B".compareToIgnoreCase("20171"));
    	//System.out.println("20162B".compareToIgnoreCase("20162"));
    	if(thsms1.equalsIgnoreCase(thsms2)) {
    		if(v==null) {
    			v = new Vector();
    			li = v.listIterator();
    			li.add(thsms1);
    			//System.out.println(thsms1);
    		}
    	}
    	else { 
    		String tmp = null;
    		if(thsms1.contains("A")) {
    			thsms1 = thsms1.substring(0, 4)+"1A";
    		}
    		if(thsms2.contains("A")) {
    			thsms1 = thsms1.substring(0, 4)+"1A";
    		}
    		if(thsms1.contains("B")) {
    			thsms1 = thsms1.substring(0, 4)+"2B";
    		}
    		if(thsms2.contains("B")) {
    			thsms2 = thsms2.substring(0, 4)+"1B";
    		}
    		//System.out.println(thsms1+" sd "+thsms2);
    		if(thsms1.compareToIgnoreCase(thsms2)<0) {
    			if(v==null) {
    				v = new Vector();
    				li = v.listIterator();
    				tmp = new String(thsms1);
    				if(tmp.length()==6) {
    					tmp = tmp.substring(0,4)+tmp.substring(5,6);
    				}
    				li.add(tmp);
    				//System.out.println(tmp);
    			}
    			for(;thsms1.compareToIgnoreCase(thsms2)<0;) {
				//int tot_sms_antara = Checker.getTotSmsAntaraYgDigunakan(thsms1, kdpst);
    				thsms1 = Tool.returnNextThsmsGiven_v1(tmp, kdpst);
    				if(thsms1.contains("A")) {
    	    			thsms1 = thsms1.substring(0, 4)+"1A";
    	    		}
    	    		if(thsms1.contains("B")) {
    	    			thsms1 = thsms1.substring(0, 4)+"2B";
    	    		}
    	    		tmp = new String(thsms1);
    	    		if(tmp.length()==6) {
    					tmp = tmp.substring(0,4)+tmp.substring(5,6);
    				}
    				li.add(tmp);
    				//System.out.println(thsms1+" s/d "+thsms2);
    				//System.out.println(tmp);
    			}
    		}	
	    	else if(thsms1.compareToIgnoreCase(thsms2)>0) {
	    		if(v==null) {
	    			v = new Vector();
	    			li = v.listIterator();
	    			li.add(thsms2);
	    			tmp = new String(thsms2);
    				if(tmp.length()==6) {
    					tmp = tmp.substring(0,4)+tmp.substring(5,6);
    				}
    				li.add(tmp);
	    			//System.out.println(tmp);
	    		}
	    		for(;thsms2.compareToIgnoreCase(thsms1)<0;) {
	    			//int tot_sms_antara = Checker.getTotSmsAntaraYgDigunakan(thsms2, kdpst);
	    			thsms2 = Tool.returnNextThsmsGiven_v1(tmp, kdpst);
	    			if(thsms2.contains("A")) {
	    				thsms2 = thsms2.substring(0, 4)+"1A";
    	    		}
    	    		if(thsms2.contains("B")) {
    	    			thsms2 = thsms2.substring(0, 4)+"2B";
    	    		}
    	    		tmp = new String(thsms2);
    	    		if(tmp.length()==6) {
    					tmp = tmp.substring(0,4)+tmp.substring(5,6);
    				}
    				li.add(tmp);
	    			//System.out.println(tmp);
	    		}
	    	}
    	}	
    	return v;
    }
    
    
    
    /*
     * deprecated pake yg ada v1
     */
	public static Vector returnTokensListThsms(String thsms1, String thhsms2, String basedThsms) {
		/*
		 * fungsi ini menghasilkan list berurutan dari thsms terendah sampai terkini ; baris pertama based thsms
		 * input; urutan thsms1&2 boleh dibolak balik
		 * basedThssms = mungkin isi kaya N/A ??
		 */
    	String listThsms="";
    	String starting =null,ending=null;
		if(thsms1.compareToIgnoreCase(thhsms2)==0) {
			starting = ""+thsms1;
			ending = ""+thsms1;
		}
		else {
			if(thsms1.compareToIgnoreCase(thhsms2)<0) {
				starting = ""+thsms1;
				ending = ""+thhsms2;
			}
			else {
				starting = ""+thhsms2;
				ending = ""+thsms1;
			}
		}
		if(starting.equalsIgnoreCase(ending)) {
			//listThsms="starting";
			listThsms=starting;
		}
		else {
			listThsms = listThsms+ending+",";
			while(!ending.equalsIgnoreCase(starting)) {
				ending = returnPrevThsmsGiven(ending);
				listThsms=listThsms+ending;
				if(!ending.equalsIgnoreCase(starting)) {
					listThsms=listThsms+",";
				}
				//System.out.println("listThsms="+listThsms);
			}
			//listThsms=listThsms+","+ending;
		}
		 
		StringTokenizer st = new StringTokenizer(listThsms,",");
		Vector thsmsList = new Vector();
		ListIterator li = 	thsmsList.listIterator();
		
		while(st.hasMoreTokens()) {
			li.add(st.nextToken());
		}
		Collections.sort(thsmsList);
		li = 	thsmsList.listIterator();
		li.add(basedThsms);
		return thsmsList;
    }
	
	/*
     * deprecated pake yg ada v1
     */
	public static Vector returnTokensListThsmsTpAntara(String thsms1, String thhsms2, String basedThsms) {
		/*
		 * fungsi ini menghasilkan list berurutan dari thsms terendah sampai terkini ; baris pertama based thsms
		 * input; urutan thsms1&2 boleh dibolak balik
		 * basedThssms = mungkin isi kaya N/A ??
		 */
    	String listThsms="";
    	String starting =null,ending=null;
		if(thsms1.compareToIgnoreCase(thhsms2)==0) {
			starting = ""+thsms1;
			ending = ""+thsms1;
		}
		else {
			if(thsms1.compareToIgnoreCase(thhsms2)<0) {
				starting = ""+thsms1;
				ending = ""+thhsms2;
			}
			else {
				starting = ""+thhsms2;
				ending = ""+thsms1;
			}
		}
		if(starting.equalsIgnoreCase(ending)) {
			//listThsms="starting";
			listThsms=starting;
		}
		else {
			listThsms = listThsms+ending+",";
			while(!ending.equalsIgnoreCase(starting)) {
				ending = returnPrevThsmsGivenTpAntara(ending);
				listThsms=listThsms+ending;
				if(!ending.equalsIgnoreCase(starting)) {
					listThsms=listThsms+",";
				}
				//System.out.println("listThsms="+listThsms);
			}
			//listThsms=listThsms+","+ending;
		}
		 
		StringTokenizer st = new StringTokenizer(listThsms,",");
		Vector thsmsList = new Vector();
		ListIterator li = 	thsmsList.listIterator();
		
		while(st.hasMoreTokens()) {
			li.add(st.nextToken());
		}
		Collections.sort(thsmsList);
		li = 	thsmsList.listIterator();
		li.add(basedThsms);
		return thsmsList;
    }
	
	/*
     * deprecated pake yg ada v1
     */
	public static Vector returnTokensListThsms(String thsms1, String thhsms2) {
		/*
		 * fungsi ini menghasilkan list berurutan dari thsms terendah sampai terkini ; baris pertama based thsms
		 * input; urutan thsms1&2 boleh dibolak balik
		 */
    	String listThsms="";
    	String starting =null,ending=null;
		if(thsms1.compareToIgnoreCase(thhsms2)==0) {
			starting = ""+thsms1;
			ending = ""+thsms1;
		}
		else {
			if(thsms1.compareToIgnoreCase(thhsms2)<0) {
				starting = ""+thsms1;
				ending = ""+thhsms2;
			}
			else {
				starting = ""+thhsms2;
				ending = ""+thsms1;
			}
		}
		if(starting.equalsIgnoreCase(ending)) {
			listThsms="starting";
		}
		else {
			listThsms = listThsms+ending+",";
			while(!ending.equalsIgnoreCase(starting)) {
				ending = returnPrevThsmsGiven(ending);
				listThsms=listThsms+ending;
				if(!ending.equalsIgnoreCase(starting)) {
					listThsms=listThsms+",";
				}
			}
		}
		 
		StringTokenizer st = new StringTokenizer(listThsms,",");
		Vector thsmsList = new Vector();
		ListIterator li = 	thsmsList.listIterator();
		
		while(st.hasMoreTokens()) {
			li.add(st.nextToken());
		}
		Collections.sort(thsmsList);
//		li = 	thsmsList.listIterator();
//		li.add(basedThsms);
		return thsmsList;
    }
	
	/*
     * deprecated pake yg ada v1
     */
	public static Vector returnTokensListThsmsTpAntara(String thsms1, String thhsms2) {
		/*
		 * fungsi ini menghasilkan list berurutan dari thsms terendah sampai terkini ; baris pertama based thsms
		 * input; urutan thsms1&2 boleh dibolak balik
		 */
    	String listThsms="";
    	String starting =null,ending=null;
		if(thsms1.compareToIgnoreCase(thhsms2)==0) {
			starting = ""+thsms1;
			ending = ""+thsms1;
		}
		else {
			if(thsms1.compareToIgnoreCase(thhsms2)<0) {
				starting = ""+thsms1;
				ending = ""+thhsms2;
			}
			else {
				starting = ""+thhsms2;
				ending = ""+thsms1;
			}
		}
		if(starting.equalsIgnoreCase(ending)) {
			listThsms="starting";
		}
		else {
			listThsms = listThsms+ending+",";
			while(!ending.equalsIgnoreCase(starting)) {
				ending = returnPrevThsmsGivenTpAntara(ending);
				listThsms=listThsms+ending;
				if(!ending.equalsIgnoreCase(starting)) {
					listThsms=listThsms+",";
				}
			}
		}
		 
		StringTokenizer st = new StringTokenizer(listThsms,",");
		Vector thsmsList = new Vector();
		ListIterator li = 	thsmsList.listIterator();
		
		while(st.hasMoreTokens()) {
			li.add(st.nextToken());
		}
		Collections.sort(thsmsList);
//		li = 	thsmsList.listIterator();
//		li.add(basedThsms);
		return thsmsList;
    }
	
	
    /*depricated
    public static String returnPrevThsmsGiven(String smawl) {
		String tahun = smawl.substring(0,4);
		String sms = smawl.substring(4,5);
		String thsms = "";
		if(sms.equalsIgnoreCase("1")) {
			thsms = (Long.valueOf(tahun).longValue()-1)+"2";
		} else {
			thsms = tahun+"1";
		}
		return thsms;
	}
    */
	public static String returnPrevThsmsGiven(String smawl) {
		
		String tahun = smawl.substring(0,4);
		String sms = smawl.substring(4,smawl.length());
		String thsms = "";
		if(sms.equalsIgnoreCase("1")) {
			thsms = (Long.valueOf(tahun).longValue()-1)+"B";
		} else {
			if(sms.equalsIgnoreCase("2B")||sms.equalsIgnoreCase("B")) {
				thsms = tahun+"2";
			}
			else {
				if(sms.equalsIgnoreCase("2")) {
					thsms = tahun+"A";
				}
				else {
					if(sms.equalsIgnoreCase("1A")||sms.equalsIgnoreCase("A")) {
						thsms = tahun+"1";
					}
				}
			}
		}
		return thsms;
	}
	
	public static String returnPrevThsmsGiven_v1(String smawl, String kdpst) {
		/*
		 * fungsi ini pake ngecek sms antara yg digunakan pd given smawl
		 */
	//public static String returnNextThsmsGiven_v1(String smawl, String kdpst) {
    	//int tot_sms_antara = Checker.getTotSmsAntaraYgDigunakan(smawl, kdpst);
    	String kode_sms_antara = Checker.getKodeSmsAntaraYgDigunakan(smawl, kdpst);
		String tahun = smawl.substring(0,4);
		String sms = smawl.substring(4,smawl.length());
		String thsms = "";
		//if(tot_sms_antara==0) {
		if(Checker.isStringNullOrEmpty(kode_sms_antara)) {	
			thsms = returnPrevThsmsGivenTpAntara(smawl);
		}
		else if(kode_sms_antara.equalsIgnoreCase("A")) {
		//else if(tot_sms_antara==1) {
			if(sms.equalsIgnoreCase("1")||sms.equalsIgnoreCase("B")) {
				thsms = (Long.valueOf(tahun).longValue()-1)+"2";
			} else {
				if(sms.equalsIgnoreCase("2")) {
					thsms = tahun+"A";
				}
				else {
					if(sms.equalsIgnoreCase("A")) {
						thsms = tahun+"1";
					}
				}
			}
		}
		else if(kode_sms_antara.equalsIgnoreCase("B")) {
			//else if(tot_sms_antara==1) {
			if(sms.equalsIgnoreCase("1")) {
				thsms = (Long.valueOf(tahun).longValue()-1)+"B";
				//thsms = (Long.valueOf(tahun).longValue()-1)+"2";
			} else {
				if(sms.equalsIgnoreCase("2")||sms.equalsIgnoreCase("A")) {
					thsms = tahun+"1";
				}
				else {
					if(sms.equalsIgnoreCase("B")) {
						thsms = tahun+"2";
					}
				}
			}
		}
		else if(kode_sms_antara.equalsIgnoreCase("AB")||kode_sms_antara.equalsIgnoreCase("BA")) {
		//else if(tot_sms_antara==2) {
			if(sms.equalsIgnoreCase("1")) {
				thsms = (Long.valueOf(tahun).longValue()-1)+"B";
			} else {
				if(sms.equalsIgnoreCase("B")) {
					thsms = tahun+"2";
				}
				else {
					if(sms.equalsIgnoreCase("2")) {
						thsms = tahun+"A";
					}
					else {
						if(sms.equalsIgnoreCase("A")) {
							thsms = tahun+"1";
						}
					}
				}
			}
		}
		
		return thsms;
	}
	
	/*
     * deprecated pake yg ada v1
     */
	public static String returnPrevThsmsGivenTpAntara(String smawl) {
		
		String tahun = smawl.substring(0,4);
		String sms = smawl.substring(4,smawl.length());
		String thsms = "";
		if(sms.equalsIgnoreCase("1")||sms.equalsIgnoreCase("B")) {
			thsms = (Long.valueOf(tahun).longValue()-1)+"2";
		}
		else {
			if(sms.equalsIgnoreCase("2")||sms.equalsIgnoreCase("A")) {
				thsms = tahun+"1";
			}
		}
		return thsms;
	}
	/*depricated
    public static String returnNextThsmsGiven(String smawl) {
		String tahun = smawl.substring(0,4);
		String sms = smawl.substring(4,5);
		String thsms = "";
		if(sms.equalsIgnoreCase("1")) {
			thsms = tahun+"2";
		} else {
			thsms = (Long.valueOf(tahun).longValue()+1)+"1";
		}
		return thsms;
	}
    */
	/*
     * deprecated pake yg ada v1
     */
    public static String returnNextThsmsGiven(String smawl) {
		String tahun = smawl.substring(0,4);
		String sms = smawl.substring(4,smawl.length());
		String thsms = "";
		if(sms.equalsIgnoreCase("1")) {
			thsms = tahun+"A";
		} else {
			if(sms.equalsIgnoreCase("1A")||sms.equalsIgnoreCase("A")) {
				thsms = tahun+"2";
			} else {
				if(sms.equalsIgnoreCase("2")) {
					thsms = tahun+"B";
				} else {
					if(sms.equalsIgnoreCase("2B")||sms.equalsIgnoreCase("B")) {
						thsms = (Long.valueOf(tahun).longValue()+1)+"1";
					}
				}
			}
		}
		return thsms;
	}
    
    public static String returnNextThsmsGiven_v1(String smawl, String kdpst) {
    	//int tot_sms_antara = Checker.getTotSmsAntaraYgDigunakan(smawl, kdpst);
    	String kode_sms_antara = Checker.getKodeSmsAntaraYgDigunakan(smawl, kdpst);
    	String thsms = "";
    	String tahun = smawl.substring(0,4);
		String sms = smawl.substring(4,smawl.length());
    	//if(tot_sms_antara==0) {
    	if(Checker.isStringNullOrEmpty(kode_sms_antara)) {	
    		thsms = returnNextThsmsGivenTpAntara(smawl);
    	}
    	else if(kode_sms_antara.equalsIgnoreCase("A")) {
    	//else if(tot_sms_antara==1) {
    		if(sms.equalsIgnoreCase("1")) {
    			thsms = tahun+"A";
    		} else {
    			if(sms.equalsIgnoreCase("A")) {
    				thsms = tahun+"2";
    			} else {
    				if(sms.equalsIgnoreCase("2")||sms.equalsIgnoreCase("B")) {
    					thsms = (Long.valueOf(tahun).longValue()+1)+"1";
    				}
    			}
    		}
    	}
    	else if(kode_sms_antara.equalsIgnoreCase("B")) {
        	//else if(tot_sms_antara==1) {
        	if(sms.equalsIgnoreCase("1")||sms.equalsIgnoreCase("A")) {
        		thsms = tahun+"2";
        	} else {
        		if(sms.equalsIgnoreCase("2")) {
        			thsms = tahun+"B";
        		} else {
        			if(sms.equalsIgnoreCase("B")) {
        				thsms = (Long.valueOf(tahun).longValue()+1)+"1";
        			}
        		}
        	}
        }
    	else if(kode_sms_antara.equalsIgnoreCase("AB")||kode_sms_antara.equalsIgnoreCase("BA")) {
    		if(sms.equalsIgnoreCase("1")) {
    			thsms = tahun+"A";
    		} else {
    			if(sms.equalsIgnoreCase("A")) {
    				thsms = tahun+"2";
    			} else {
    				if(sms.equalsIgnoreCase("2")) {
    					thsms = tahun+"B";
    				} else {
    					if(sms.equalsIgnoreCase("B")) {
    						thsms = (Long.valueOf(tahun).longValue()+1)+"1";
    					}
    				}
    			}
    		}	
    	}
		return thsms;
	}
    

    
    public static String returnNextPeriodeSpmi(String periode_start, String unit_used_by_periode_start, int besaran_interval_per_period) {
    	String next_periode = new String(periode_start);
    	if(unit_used_by_periode_start.equalsIgnoreCase("sms")) {
    		//System.out.println("keisini");
    		//String smawl = new String(periode_start);
    		//kalo salah input, kelupaan sms = default 1
    		if(next_periode.length()==4) {
    			next_periode = next_periode+"1";
    		}
    		for(int i=0;i<besaran_interval_per_period;i++) {
    			next_periode = returnNextThsmsGivenTpAntara(next_periode);
    		}
    	}
    	else if(unit_used_by_periode_start.equalsIgnoreCase("thn")) {
    		//System.out.println("keisono");
    		if(next_periode.length()>4) {
    			next_periode = next_periode.substring(0, 4);
    		}
    		int tmp_periode = Integer.parseInt(next_periode);
    		for(int i=0;i<besaran_interval_per_period;i++) {
    			tmp_periode++;
    		}
    		next_periode = ""+tmp_periode;
    	}
    	return next_periode;
	}
    
    /*
     * deprecated pake yg ada v1
     */
    public static String returnNextThsmsGivenTpAntara(String smawl) {
		String tahun = smawl.substring(0,4);
		String sms = smawl.substring(4,smawl.length());
		String thsms = "";
		if(sms.equalsIgnoreCase("1")||sms.equalsIgnoreCase("A")) {
			thsms = tahun+"2";
		} else {
			if(sms.equalsIgnoreCase("2")||sms.equalsIgnoreCase("B")) {
				thsms = (Long.valueOf(tahun).longValue()+1)+"1";
			}
		}
		return thsms;
	}
    
    
    public static Vector removeDuplicateFromVector(Vector v)throws Exception {
		Vector v1 = null;
		try {
			if(v!=null && v.size()>0) {
				v1 = new Vector(new LinkedHashSet(v));	
			}
		}
		catch(Exception e) {
		 e.printStackTrace();
		}
		
		
		return v1;
	}

    
    public static int getTotalSoalUjian(String tokenKodeGroupAndListSoal) {
    	StringTokenizer st = null;
    	int soaltt = 0;
    	if(tokenKodeGroupAndListSoal!=null) {
    		if(tokenKodeGroupAndListSoal.contains("#")) {
    			st = new StringTokenizer(tokenKodeGroupAndListSoal,"#");
    		}
    		else {
    			if(tokenKodeGroupAndListSoal.contains("$$")) {
    				st = new StringTokenizer(tokenKodeGroupAndListSoal,"$$");
    			}
    		}
    		if(st!=null && st.countTokens()>0) {
    			while(st.hasMoreTokens()) {
    				String listSoal = (String)st.nextToken();
    				StringTokenizer st1 = new StringTokenizer(listSoal,",");
    				String kodeGroupIgnore = st1.nextToken();
    				while(st1.hasMoreTokens()) {
    					soaltt++;
    					String soalid = st1.nextToken();
    					String nosoal = st1.nextToken();
    				}
    			}
    		}
    	}
    	return soaltt;
    }
    
    public static String createTokenIdSoalAtChapterSesuaiNorutSoalForNavigasiIdSoalInMiddlePos(String tokenKodeGroupAndListSoal,String idSoal) {
    	String tkn = "";
    	String tknBeforMatched = "";
    	//if(Checker.isStringNullOrEmpty(idSoal)) {
    	//System.out.println("ttool="+tokenKodeGroupAndListSoal);
    	//System.out.println("idsoal="+idSoal);
    	String tmp = createTokenIdSoalAtChapterSesuaiNorutSoal(tokenKodeGroupAndListSoal);
    	//System.out.println("tmp1-"+tmp);
    	int norutNavigasiCounter = 0;
    	int counterBeforeMatched = 0;
    	boolean match = false;
    	int norutNavigasi = 0;
    	if(!Checker.isStringNullOrEmpty(idSoal)) {
    		//cek apakah id target idSoal ada di norut 1
    		//boolean startWith1 = false;
    		boolean first = true;
    		StringTokenizer st = new StringTokenizer(tmp,",");
    		String tmp_idSoal = null;
    		String tmp_atChapter = null;
    		boolean idsoalMatched = false;
    		int totalSoalUjian =  getTotalSoalUjian(tokenKodeGroupAndListSoal);
    		//if(totalSoalUjian>15) {
    		if(true) { // ngga ada bedanya antara total soal > 15
    			while(st.hasMoreTokens()&&norutNavigasiCounter<15) {
    				tmp_idSoal = st.nextToken();
					tmp_atChapter = st.nextToken();
					norutNavigasi++;
    				if(first) {
    					first =false;
    					//tmp_idSoal = st.nextToken();
    					//tmp_atChapter = st.nextToken();
    					if(tmp_idSoal.equalsIgnoreCase(idSoal)) {
            			//jika match at token #1
    						//norutNavigasi=1;
    						norutNavigasiCounter++;
    						idsoalMatched=true;
    						tkn = tkn+norutNavigasi+","+tmp_idSoal+","+tmp_atChapter+",";
    					}
    					else {
    						//norutNavigasi++;
    						counterBeforeMatched++;
    						tknBeforMatched=tknBeforMatched+norutNavigasi+","+tmp_idSoal+","+tmp_atChapter+",";
    					}
    				}
    				else {
    				//	norutNavigasi++;
    				//	counter++;
    					//tmp_idSoal = st.nextToken();
    					//tmp_atChapter = st.nextToken();
    					if(idsoalMatched) {
    					//jika sdh mathced
    						//norutNavigasi++;
    						norutNavigasiCounter++;
    						if(norutNavigasiCounter<=15) {
    							tkn = tkn+norutNavigasi+","+tmp_idSoal+","+tmp_atChapter+",";
    						}
    					}
    					else {
    					//matched tidak di token pertama
    						//tmp_idSoal = st.nextToken();
    						//tmp_atChapter = st.nextToken();
    						if(tmp_idSoal.equalsIgnoreCase(idSoal)) {
    							idsoalMatched = true;
    							norutNavigasiCounter++;//tambahan
    							//norutNavigasi++;
    							//System.out.println("match at "+counterBeforeMatched);
    							if(counterBeforeMatched<7) {
    								norutNavigasiCounter = norutNavigasiCounter+counterBeforeMatched;
    								//System.out.println("norutNavigasiCounter at "+norutNavigasiCounter);
    								//norutNavigasi = counterBeforeMatched++;
    								tkn = tknBeforMatched+norutNavigasi+","+tmp_idSoal+","+tmp_atChapter+",";
    							}
    							else {
    								//int sisa = totalSoalUjian - counterBeforeMatched;
    								int j = 0;
    								int atNoNav = counterBeforeMatched-5+15;
    								if(atNoNav<totalSoalUjian) {
    									j=5;
    									//norutNavigasiCounter=counterBeforeMatched-j;
    									//j=counterBeforeMatched-(counterBeforeMatched-5);
    									
    								}
    								else {
    									int sisa = totalSoalUjian - counterBeforeMatched;
    									j=15-sisa;
    									//norutNavigasiCounter=counterBeforeMatched-j;
    								}
    								//norutNavigasiCounter = j;
    								//if(sisa<=6) {
    								
    								//	j=15-sisa;
    								//}
    								//else {
    								//	j=7;
    								//}	
    								//if(sisa<=15) {
    								//System.out.println("j="+j);
    								String tknBeforMatchedReversed = reverseOrder3Tkn(tknBeforMatched);
    								StringTokenizer st1 = new StringTokenizer(tknBeforMatchedReversed,",");
    								String tmp1 = "";
    								//for(j=15-sisa;j>0;j--) {
    								norutNavigasiCounter=0;
    								for(int k=0;k<j;k++) {
    									norutNavigasiCounter++;
    									String norutNavigasi1=st1.nextToken();
    									String tmp_idSoal1=st1.nextToken();
    									String tmp_atChapter1=st1.nextToken();
    									tmp1 = tmp1+norutNavigasi1+","+tmp_idSoal1+","+tmp_atChapter1+",";
    								}
    								tknBeforMatched = reverseOrder3Tkn(tmp1);
    								tkn = tknBeforMatched+norutNavigasi+","+tmp_idSoal+","+tmp_atChapter+",";
    								norutNavigasiCounter++;
    								 
    							}
    						}
    						else {
    							counterBeforeMatched++;
    							tknBeforMatched=tknBeforMatched+norutNavigasi+","+tmp_idSoal+","+tmp_atChapter+",";
    						}
    					}
    				}	
    			}
    		}
    		else {
    			//if total ujian <15
    		}
    	}
    	else {
    		//System.out.println("idsoal null");
    	}
    	
    	return tkn;
	}
    
    public static String reverseOrder3Tkn(String tokenOfThreeSeperatedByKoma) {
    	String reverse="";
    	StringTokenizer st = new StringTokenizer(tokenOfThreeSeperatedByKoma,",");
    	while(st.hasMoreTokens()) {
    		String first = st.nextToken();
    		String second = st.nextToken();
    		String third = st.nextToken();
    		reverse = first+","+second+","+third+","+reverse;
    	}	
    	return reverse;
    }
    
    public static String buatSatuSpasiAntarKata(String brs) {
    	if(brs!=null && brs.length()>0) {
    		StringTokenizer st = new StringTokenizer(brs);
    		brs = new String();
    		while(st.hasMoreTokens()) {
    			brs = brs+st.nextToken();
    			if(st.hasMoreTokens()) {
    				brs = brs+" ";
    			}
    		}
    	}
    	return brs;
    }
    
    public static String createTokenIdSoalAtChapterSesuaiNorutSoal(String tokenKodeGroupAndListSoal) {
    	String tkn="";
    	StringTokenizer st = null;
    	if(tokenKodeGroupAndListSoal!=null) {
    		if(tokenKodeGroupAndListSoal.contains("#")) {
    			st = new StringTokenizer(tokenKodeGroupAndListSoal,"#");
    		}
    		else {
    			if(tokenKodeGroupAndListSoal.contains("$$")) {
    				//tokenKodeGroupAndListSoal=tokenKodeGroupAndListSoal.substring(0,tokenKodeGroupAndListSoal.length()-2);
    				st = new StringTokenizer(tokenKodeGroupAndListSoal,"$$");
    			}
    		}
    		 
    		if(st!=null && st.countTokens()>0) {
    			int atChapter = 0;
    			while(st.hasMoreTokens()) {
    				atChapter++;
    				String tokens = (String)st.nextToken();
    				StringTokenizer st1 = new StringTokenizer(tokens,",");
    				String bagianIgnore = st1.nextToken();
    				while(st1.hasMoreTokens()) {
    					String idSoal = st1.nextToken();
    					String norutIgnore = st1.nextToken();
    					tkn=tkn+idSoal+","+atChapter+",";
    				}
    			}
    		}	
    	}
    	return tkn;
    }
    
    public static int getTotalSoalUjianAtBag(String tokenKodeGroupAndListSoal,String atChapter) {
    	//System.out.println("=="+tokenKodeGroupAndListSoal+"=="+atChapter);
    	StringTokenizer st = null;
    	int soaltt = 0;
    	if(tokenKodeGroupAndListSoal!=null) {
    		if(tokenKodeGroupAndListSoal.contains("#")) {
    			st = new StringTokenizer(tokenKodeGroupAndListSoal,"#");
    		}
    		else {
    			if(tokenKodeGroupAndListSoal.contains("$$")) {
    				//tokenKodeGroupAndListSoal=tokenKodeGroupAndListSoal.substring(0,tokenKodeGroupAndListSoal.length()-2);
    				st = new StringTokenizer(tokenKodeGroupAndListSoal,"$$");
    			}
    		}
    		boolean match = false;
    		if(st!=null && st.countTokens()>0) {
    			int tkntt=st.countTokens();
    			//System.out.println("countTokens="+st.countTokens());
    			for(int i=1;i<=tkntt;i++) {
    				String listSoal = (String)st.nextToken();
    				//System.out.println(i+".listSoal="+listSoal);
    				if(atChapter.equalsIgnoreCase(""+i)) {
    					match = true;
    					//System.out.println("match="+match);
    					
    					StringTokenizer st1 = new StringTokenizer(listSoal,",");
    					String kodeGroupIgnore = st1.nextToken();
    					while(st1.hasMoreTokens()) {
    						soaltt++;
    						String soalid = st1.nextToken();
    						String nosoal = st1.nextToken();
    					}
    				}
    				
    			}
    		}
    	}
    	return soaltt;
    }
    
    public static String gotoDataPrevSoal(String tokenKodeGroupAndListSoal,String idSoal) {
    	String tkn="";
    	//System.out.println("prev="+tokenKodeGroupAndListSoal+">"+idSoal);
    	StringTokenizer st = null;
    	//int soaltt = 0;
    	if(tokenKodeGroupAndListSoal!=null) {
    		if(tokenKodeGroupAndListSoal.contains("#")) {
    			st = new StringTokenizer(tokenKodeGroupAndListSoal,"#");
    		}
    		else {
    			if(tokenKodeGroupAndListSoal.contains("$$")) {
    				//tokenKodeGroupAndListSoal=tokenKodeGroupAndListSoal.substring(0,tokenKodeGroupAndListSoal.length()-2);
    				st = new StringTokenizer(tokenKodeGroupAndListSoal,"$$");
    			}
    		}
    	}	
    	String infoPrevSoal="";
    	boolean match = false;
    	if(st!=null && st.countTokens()>0) {
			int atChapter = 0;
			String tmp_idSoal="";
			while(st.hasMoreTokens()&&!match) {
				atChapter++;
				String tokens = (String)st.nextToken();
				StringTokenizer st1 = new StringTokenizer(tokens,",");
				String bagianIgnore = st1.nextToken();
				while(st1.hasMoreTokens()&&!match) {
					tmp_idSoal = st1.nextToken();
					String tmp_norutIgnore = st1.nextToken();
					if(tmp_idSoal.equalsIgnoreCase(idSoal)) {
						match=true;
					}
					if(!match) {
						infoPrevSoal = tmp_idSoal+","+atChapter;
					}
				}
				
			}
		}
//    	//System.out.println("infoPrevSoal="+infoPrevSoal);
    	return infoPrevSoal;
    }
    
    public static String removeWhiteSpace(String brs) {
    	StringTokenizer st = new StringTokenizer(brs);
    	brs = "";
    	while(st.hasMoreTokens()) {
    		brs=brs+st.nextToken();
    	}
    	return brs;
    	
    }
    
    public static String gantiSpecialChar(String baris) {
    	//filter sebelum  stringtokenizer dengan pembatas "__"
    	String tmp = null;
    	tmp = baris.replace("_","tandaGarisBawah");
    	tmp = tmp.replace("\"","tandaKutipGanda");
    	tmp = tmp.replace("&", "tandaDan");
    	tmp = tmp.replace("#","tandaPagar");
    	return tmp;
    }

    public static String kembaliSpecialChar(String baris) {
    	//filter sebelum  stringtokenizer dengan pembatas "__"
    	String tmp = null;
    	tmp = baris.replace("tandaGarisBawah","_");
    	tmp = tmp.replace("tandaKutipGanda","\"");
    	tmp = tmp.replace("tandaDan","&");
    	tmp = tmp.replace("tandaPagar","#");
    	return tmp;
    }
    /*    
    public static String gotoDataNextSoal(String tokenKodeGroupAndListSoal,String idSoal) {
    	String tkn="";
    	//System.out.println("next="+tokenKodeGroupAndListSoal+">"+idSoal);
    	StringTokenizer st = null;
    	//int soaltt = 0;
    	if(tokenKodeGroupAndListSoal!=null) {
    		if(tokenKodeGroupAndListSoal.contains("#")) {
    			st = new StringTokenizer(tokenKodeGroupAndListSoal,"#");
    		}
    		else {
    			if(tokenKodeGroupAndListSoal.contains("$$")) {
    				//tokenKodeGroupAndListSoal=tokenKodeGroupAndListSoal.substring(0,tokenKodeGroupAndListSoal.length()-2);
    				st = new StringTokenizer(tokenKodeGroupAndListSoal,"$$");
    			}
    		}
    	}	
    	String infoNextSoal="";
    	boolean match = false;
    	boolean afterMatch = false;
    	if(st!=null && st.countTokens()>0) {
			int atChapter = 0;
			String tmp_idSoal="";
			while(st.hasMoreTokens()&&!match) {
				atChapter++;
				String tokens = (String)st.nextToken();
				StringTokenizer st1 = new StringTokenizer(tokens,",");
				String bagianIgnore = st1.nextToken();
				while(st1.hasMoreTokens()&&!match) {
					tmp_idSoal = st1.nextToken();
					String tmp_norutIgnore = st1.nextToken();
					if(tmp_idSoal.equalsIgnoreCase(idSoal)) {
						match=true;
					}
					if(match && st1.hasMoreTokens()) {
						tmp_idSoal = st1.nextToken();
						tmp_norutIgnore = st1.nextToken();
						infoNextSoal = tmp_idSoal+","+atChapter;
					}
					else {
						if(match && !st1.hasMoreTokens()) {
							tokens = (String)st.nextToken();
							st1 = new StringTokenizer(tokens,",");
							bagianIgnore = st1.nextToken();
							while(st1.hasMoreTokens()&&match) {
								tmp_idSoal = st1.nextToken();
								tmp_norutIgnore = st1.nextToken();
								infoNextSoal = tmp_idSoal+","+atChapter;
							}	
						}
					}
				}
			}
		}
    	//System.out.println("infoNextSoal="+infoNextSoal);
    	return infoNextSoal;
    }
*/
    /*
     * depricated = gunakan yg di beans ToolSoalUjian

    public static String gotoDataNextSoal(String tokenKodeGroupAndListSoal,String idSoal) {
    	String tkn="";
    	//System.out.println("next="+tokenKodeGroupAndListSoal+">"+idSoal);
    	StringTokenizer st = null;
    	//int soaltt = 0;
    	if(tokenKodeGroupAndListSoal!=null) {
    		if(tokenKodeGroupAndListSoal.contains("#")) {
    			st = new StringTokenizer(tokenKodeGroupAndListSoal,"#");
    		}
    		else {
    			if(tokenKodeGroupAndListSoal.contains("$$")) {
    				//tokenKodeGroupAndListSoal=tokenKodeGroupAndListSoal.substring(0,tokenKodeGroupAndListSoal.length()-2);
    				st = new StringTokenizer(tokenKodeGroupAndListSoal,"$$");
    			}
    		}
    	}	
    	String infoNextSoal="";
    	boolean match = false;
    	boolean afterMatch = false;
    	if(st!=null && st.countTokens()>0) {
			int atChapter = 0;
			String tmp_idSoal="";
			while(st.hasMoreTokens()&&!match) {
				atChapter++;
				String tokens = (String)st.nextToken();
				StringTokenizer st1 = new StringTokenizer(tokens,",");
				String bagianIgnore = st1.nextToken();
				while(st1.hasMoreTokens()&&!match) {
					tmp_idSoal = st1.nextToken();
					String tmp_norutIgnore = st1.nextToken();
					if(tmp_idSoal.equalsIgnoreCase(idSoal)) {
						match=true;
					}
					if(match && st1.hasMoreTokens()) {
						tmp_idSoal = st1.nextToken();
						tmp_norutIgnore = st1.nextToken();
						infoNextSoal = tmp_idSoal+","+atChapter;
					}
					else {
						if(match && !st1.hasMoreTokens()) {
							atChapter++;
							tokens = (String)st.nextToken();
							st1 = new StringTokenizer(tokens,",");
							bagianIgnore = st1.nextToken();
							//st1.hasMoreTokens()&&match) {
							tmp_idSoal = st1.nextToken();
							tmp_norutIgnore = st1.nextToken();
							infoNextSoal = tmp_idSoal+","+atChapter;
							//}	
						}
					}
				}
			}
			if(!match) {
				//kasus kalo dah di akhir soal maka balik ke no1
				if(tokenKodeGroupAndListSoal!=null) {
		    		if(tokenKodeGroupAndListSoal.contains("#")) {
		    			st = new StringTokenizer(tokenKodeGroupAndListSoal,"#");
		    		}
		    		else {
		    			if(tokenKodeGroupAndListSoal.contains("$$")) {
		    				//tokenKodeGroupAndListSoal=tokenKodeGroupAndListSoal.substring(0,tokenKodeGroupAndListSoal.length()-2);
		    				st = new StringTokenizer(tokenKodeGroupAndListSoal,"$$");
		    			}
		    		}
		    	}	
				atChapter = 0;
				if(st.hasMoreTokens()) {
					atChapter++;
					String tokens = (String)st.nextToken();
					StringTokenizer st1 = new StringTokenizer(tokens,",");
					String bagianIgnore = st1.nextToken();
					tmp_idSoal = st1.nextToken();
					String tmp_norutIgnore = st1.nextToken();
					infoNextSoal = tmp_idSoal+","+atChapter;
				}
			}
		}
    	//System.out.println("infoNextSoal="+infoNextSoal);
    	return infoNextSoal;
    }
         */
    public static String jsobGetString(JSONObject job,String cmd) {
    	String tmp="null";
    	try {
    		tmp = job.getString(cmd);
    	}
    	catch(JSONException e) {
    		e.printStackTrace();
    	}
    	return tmp;
    }
    
    public static String calcBatasStudi(String smawl, int idobj) {
    	String btstu_thsms = null; //thsms terakhir masih boleh kuliah, berikutnya dikeluarkan
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//1. set npm asal at civitas
			stmt = con.prepareStatement("select LAMA_BATAS_STUDI from BATAS_STUDI_PARAM where THSMS=? and ID_OBJ=?");
			stmt.setString(1, smawl);
			stmt.setInt(2, idobj);
			rs = stmt.executeQuery();
			if(rs.next()) {
				btstu_thsms = new String(smawl);
				int sms = rs.getInt(1);
				for(int j=1;j<sms;j++) {
					btstu_thsms = returnNextThsmsGivenTpAntara(btstu_thsms); 
				}
			}
			else {
				//kalo ngga ada cari yg ada / thsms yg laen
				stmt = con.prepareStatement("select LAMA_BATAS_STUDI from BATAS_STUDI_PARAM where ID_OBJ=?");
				//stmt.setString(1, smawl);
				stmt.setInt(1, idobj);
				rs = stmt.executeQuery();
				if(rs.next()) {
					btstu_thsms = new String(smawl);
					int sms = rs.getInt(1);
					for(int j=1;j<sms;j++) {
						btstu_thsms = returnNextThsmsGivenTpAntara(btstu_thsms); 
					}
				}
			}
			//2. set status keluar untuk npm lama
			//3. set status aktif pada prodi baru
			//4. copy pembayaran dari yg lama ke npm baru
			//5. buat transaksi creddit pada npm lama sehingga balance 0;
			//6. update password
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
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return btstu_thsms;
    	
    }
    
    public static String calcBatasStudi(String npmhs) {
    	String btstu_thsms = null;
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select ID_OBJ,SMAWLMSMHS from CIVITAS where NPMHSMSMHS=?");
			stmt.setString(1, npmhs);
			rs = stmt.executeQuery();
			rs.next();
			int idobj = rs.getInt(1);
			String smawl = rs.getString(2);
			//System.out.println(smawl+"--"+idobj);
			btstu_thsms = calcBatasStudi(smawl, idobj);
			//System.out.println(btstu_thsms+"--"+idobj);
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
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return btstu_thsms;
    	
    }
    

	

	/*
	 * PAKE YG LAENNYA KALO INI KHUSUS ALAMATNYA UTK FEEDER PURPOSE
	 */
	public static Vector bacaFileExcel(String fileName, String tkn_col, int starting_row, int tot_row, int tot_targeted__sheet) {
		Vector v = null;
		ListIterator li = null;
		
    	java.io.File file = null;
    	try {
    		file = new File("/home/usg/USG/EPSBED/20152/xls_files/"+fileName+".xls");
    		if(file==null || !file.exists()) {
    			file = new File("/home/usg/USG/EPSBED/20152/xls_files/"+fileName+".xlsx");	
    		}
    	}
    	catch(Exception e) {
    		
    	}
    	boolean empty_row = false;
    	if(file.exists()) {
    		try {
    			InputStream inp = new FileInputStream(file);
    			//InputStream inp = new FileInputStream("workbook.xlsx");
    			Workbook wb = WorkbookFactory.create(inp);
    			int tot_sheet = wb.getNumberOfSheets();
    			for(int k=0;k<tot_targeted__sheet;k++) {
    				for(;!empty_row && starting_row<tot_row;starting_row++) {
    					Sheet sheet = wb.getSheetAt(k);
        				//get soal
        				String one_row = "";
        				StringTokenizer st = new StringTokenizer(tkn_col,"`");
        				while(st.hasMoreTokens()) {
        					int col_no = Integer.parseInt(st.nextToken());
        					Row row = sheet.getRow(starting_row);
        					Cell cell = row.getCell(col_no);
        					String cel_value = "null";
        					switch (cell.getCellType()) {
        					case Cell.CELL_TYPE_STRING:
        						cel_value = ""+cell.getRichStringCellValue().getString();
        						break;
        					case Cell.CELL_TYPE_NUMERIC:
        						if (DateUtil.isCellDateFormatted(cell)) {
        							cel_value = ""+cell.getDateCellValue();
        						} else {
        							cel_value = ""+cell.getNumericCellValue();
        						}
        						break;
        					case Cell.CELL_TYPE_BOOLEAN:
        						cel_value = ""+cell.getBooleanCellValue();
        						break;
        					case Cell.CELL_TYPE_FORMULA:
        						cel_value = ""+cell.getCellFormula();
        						break;
        					default:
        						cel_value = "null";
        					}
        					
        					one_row = one_row+"`"+cel_value;
        				}
        				
        				if(v==null) {
        					v = new Vector();
        					li = v.listIterator();
        				}
        				li.add(one_row);
    					//System.out.println("add="+one_row);
    				}
    			}	
    		}
    		catch (Exception e) {
		   		e.printStackTrace();
		   		empty_row = true;
    		} 	
    	}
    	else {
    		//System.out.println("no fined");
    	}
    	return v;
    }
	
	public static Vector bacaFileExcel(String full_path, String fileName, String tkn_col, int starting_row) {
		/*
		 * colom pertama harus no urut, krn loop akan berhenti kalo ketemu empty first cell, ato null
		 */
		Vector v = null;
		ListIterator li = null;
		//System.out.println("fileName="+fileName);
    	java.io.File file = null;
    	try {
    		if(!fileName.contains(".")) {
    			file = new File(full_path+"/"+fileName+".xls");
    			if(file==null || !file.exists()) {
        			file = new File(full_path+"/"+fileName+".xlsx");	
        		}
    		}
    		else {
    			file = new File(full_path+"/"+fileName);
    		}
    		
    		
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	boolean empty_row = false;
    	if(file.exists()) {
    		//System.out.println("ada");
    		try {
    			InputStream inp = new FileInputStream(file);
    			//InputStream inp = new FileInputStream("workbook.xlsx");
    			Workbook wb = WorkbookFactory.create(inp);
    			int tot_sheet = wb.getNumberOfSheets();
    			//System.out.println("tot_sheet="+tot_sheet);
    			for(int k=0;(k<tot_sheet);k++) {
    				empty_row = false;
    				//System.out.println("sheet_ke="+k);
    				int start_row = starting_row;
    				for(;!empty_row;start_row++) {
    					Sheet sheet = wb.getSheetAt(k);
        				//get soal
        				String one_row = null;
        				boolean first_col = true;
        				StringTokenizer st = new StringTokenizer(tkn_col,"`");
        				while(st.hasMoreTokens() && !empty_row) {
        					int col_no = Integer.parseInt(st.nextToken());
        					Row row = sheet.getRow(start_row);
        					if(row==null) {
        						empty_row = true;
            				}
            				else {
            					Cell cell = row.getCell(col_no);
            					String cel_value = "null";
            					if(cell==null) {
            						if(first_col) {
            							
            							empty_row = true;
            						}
            						else {
            							//ignore bukan col pertama (penanda empty row) yg null,
            							one_row = one_row+"`null";
            						}
            						first_col = false;
            					}
            					else {
                					
                					switch (cell.getCellType()) {
                					case Cell.CELL_TYPE_STRING:
                						cel_value = ""+cell.getRichStringCellValue().getString();
                						break;
                					case Cell.CELL_TYPE_NUMERIC:
                						if (DateUtil.isCellDateFormatted(cell)) {
                							short x=wb.createDataFormat().getFormat("dd/m/yyyy;@");
                							CellStyle dateCellFormat = wb.createCellStyle();
                							dateCellFormat.setDataFormat(x);

                							cell.setCellStyle(dateCellFormat);
                							DataFormatter df = new DataFormatter();
                							cel_value = ""+df.formatCellValue(cell).toString();
                							//cel_value = ""+cell.getDateCellValue();
                						} else {
                							cel_value = ""+cell.getNumericCellValue();
                						}
                						break;
                					case Cell.CELL_TYPE_BOOLEAN:
                						cel_value = ""+cell.getBooleanCellValue();
                						break;
                					case Cell.CELL_TYPE_FORMULA:
                						cel_value = ""+cell.getCellFormula();
                						break;
                					default:
                						cel_value = "null";
                					}
                					if(Checker.isStringNullOrEmpty(cel_value)) {
                						if(one_row==null) {
                							empty_row = true;
                						}
                						else {
                							one_row = one_row+"`null";
                						}	
                					}
                					else {
                						//DataFormatter dataFormatter = new DataFormatter();
                    					//String cellStringValue = dataFormatter.formatCellValue(row.getCell(col_no));
                    					//System.out.println ("Is shows data as show in Excel file" + cellStringValue);
                						if(one_row==null) {
                							one_row = new String("`"+cel_value.replace("`", "'"));
                							//one_row = new String("`"+cellStringValue);
                						}
                						else {
                							one_row = one_row+"`"+cel_value.replace("`", "'");
                							//one_row = one_row+"`"+cellStringValue;
                						}
                					}
            					}
        					}
        					if(first_col) {
        						first_col = false;	
        					}
        				}
        				
        				
        				if(!empty_row) {
        					if(v==null) {
            					v = new Vector();
            					li = v.listIterator();
            				}
        					li.add(one_row);
        					//System.out.println("add="+one_row);
        				}
        				
    					//System.out.println("add="+one_row);
    				}
    				//k++;
    			}	
    		}
    		catch (Exception e) {
		   		e.printStackTrace();
		   		empty_row = true;
    		} 	
    	}
    	else {
    		//System.out.println("no fined");
    	}
    	return v;
    }
	

	
	public static String returnThsmsGivenSmaleAndSmsKe(String smawl, int sms_ke) throws Exception {
		for(int i=1;i<sms_ke;i++) {
			smawl = returnNextThsmsGivenTpAntara(smawl);
		}
		return smawl;
	}
	
	
	public static Vector bacaFileTxt(String fileName, String thsms) {
		Vector v = new Vector();
		ListIterator li = v.listIterator();
		try {
			FileInputStream fstream  = new FileInputStream(fileName);
	 		DataInputStream in = new DataInputStream(fstream);
	 		BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String tmp="";
			while((tmp = br.readLine())!=null) {
				if(!tmp.contains("NO`NPM")) {
					li.add(tmp);
					//System.out.println(tmp);
				}	
				
			}
		}
		catch (Exception e) {
	   		e.printStackTrace();
	   		//empty_row = true;
		} 
 		
		return v;
    }
	
	public static void sinkMkPenyetaraan() {
		String btstu_thsms = null;
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector v = null;
    	ListIterator li = null;
		try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select distinct THSMSTRNLP,KDPSTTRNLP,KDKMKASALP,KDKMKTRNLP from TRNLP where KDKMKTRNLP is not null");
			rs = stmt.executeQuery();
			while(rs.next()) {
				String thsms = rs.getString(1);
				String kdpst = rs.getString(2);
				String kdasl = rs.getString(3);
				String kdkmk = rs.getString(4);
				if(v==null) {
					v = new Vector();
					li = v.listIterator();
					
				}
				li.add(thsms+"`"+kdpst+"`"+kdasl+"`"+kdkmk);
				//System.out.println(thsms+"`"+kdpst+"`"+kdasl+"`"+kdkmk);
			}
			//System.out.println("step-1");
			if(v!=null) {
				stmt = con.prepareStatement("update TRNLP set KDKMKTRNLP=? where THSMSTRNLP=? and KDPSTTRNLP=? and KDKMKASALP=? and KDKMKTRNLP is null");
				li= v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String thsms = st.nextToken();
					String kdpst = st.nextToken();
					String kdasl = st.nextToken();
					String kdkmk = st.nextToken();
					stmt.setString(1,kdkmk);
					stmt.setString(2,thsms);
					stmt.setString(3,kdpst);
					stmt.setString(4,kdasl);
					stmt.executeUpdate();
					
				}
				//System.out.println("step-2");
				stmt = con.prepareStatement("update TRNLP set KDKMKTRNLP=? where KDPSTTRNLP=? and KDKMKASALP=? and KDKMKTRNLP is null");
				li= v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String thsms = st.nextToken();
					String kdpst = st.nextToken();
					String kdasl = st.nextToken();
					String kdkmk = st.nextToken();
					stmt.setString(1,kdkmk);
					stmt.setString(2,kdpst);
					stmt.setString(3,kdasl);
					stmt.executeUpdate();
					
				}
				//System.out.println("step-3");
			}
			
			stmt = con.prepareStatement("update TRNLP set TRANSFERRED=? where KDKMKTRNLP is not null and KDKMKASALP is not null");
			stmt.setBoolean(1,true);
			stmt.executeUpdate();
			//System.out.println("step-4");
			Vector v1 = new Vector();
			ListIterator li1 = v1.listIterator();
			stmt = con.prepareStatement("select DISTINCT KDPSTTRNLP,KDKMKTRNLP from TRNLP where KDKMKTRNLP is not null");
			rs = stmt.executeQuery();
			while(rs.next()) {
				String kdpst = rs.getString(1);
				String kdkmk = rs.getString(2);
				li1.add(kdpst+"`"+kdkmk);
			}
			//System.out.println("step-5");
			li1 = v1.listIterator();
			stmt = con.prepareStatement("select SKSTMMAKUL,SKSPRMAKUL,SKSLPMAKUL from MAKUL where KDPSTMAKUL=? and KDKMKMAKUL=?");
			while(li1.hasNext()) {
				String brs1 = (String)li1.next();
				StringTokenizer st = new StringTokenizer(brs1,"`");
				String kdpst = st.nextToken();
				String kdkmk = st.nextToken();
				stmt.setString(1, kdpst);
				stmt.setString(2, kdkmk);
				rs = stmt.executeQuery();
				rs.next();
				int sksmk = rs.getInt(1)+rs.getInt(2)+rs.getInt(3);
				li1.set(brs1+"`"+sksmk);
			}
			//System.out.println("step-6");
			li1 = v1.listIterator();
			stmt = con.prepareStatement("update TRNLP set SKSMKTRNLP=? where KDPSTTRNLP=? and KDKMKTRNLP=?");
			while(li1.hasNext()) {
				String brs1 = (String)li1.next();
				StringTokenizer st = new StringTokenizer(brs1,"`");
				String kdpst = st.nextToken();
				String kdkmk = st.nextToken();
				String sksmk = st.nextToken();
				stmt.setInt(1, Integer.parseInt(sksmk));
				stmt.setString(2, kdpst);
				stmt.setString(3, kdkmk);
				//System.out.print(brs1);
				int i = stmt.executeUpdate();
				//System.out.println("="+i);
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
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
		
    }
	
    public static boolean isDouble(String str) {
    	boolean number = false;
    	if(!Checker.isStringNullOrEmpty(str)) {
    		try {
            	str = str.replace(",", ".");
                Double.parseDouble(str);
                number =  true;
            } catch (NumberFormatException e) {
                number= false;
            }	
    	}
    	
        return number;
    }
    
    public static Vector<Object> mergeVector(Vector<Object> Va, Vector<Object> Vb) {
    	Vector<Object> merge = new Vector<Object>();
    	merge.addAll(Va);
    	merge.addAll(Vb);
    	return merge;
    }
    
    public static String returnTokenKpdst(Vector v_scope, String output_seperator_used) {
    	String tkn_kdpst = null;
    	if(output_seperator_used==null) {
    		output_seperator_used = new String(" ");
    	}
    	if(v_scope!=null && v_scope.size()>0) {
    		ListIterator li = v_scope.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs);
    			st.nextToken();
    			String kdpst = st.nextToken();
    			if(tkn_kdpst==null) {
    				tkn_kdpst = new String(kdpst);
    			}
    			else {
    				tkn_kdpst=tkn_kdpst+output_seperator_used+kdpst;
    			}
    		}	
    	}
		return tkn_kdpst;
    }
    
    
    public static String returnTokenThsms(String range_thsms, String output_seperator_used) {
    	//cnontoh input :>20121,=20152,<=20062
    	//String list_thsms=null;
    	if(output_seperator_used==null) {
    		output_seperator_used = new String(" ");
    	}
    	String thsms_tmp = null;
    	String tkn_thsms = null;
    	String thsms_max = Checker.getThsmsPmb();
    	String thsms_min = "20021";
    	if(!Checker.isStringNullOrEmpty(range_thsms)) {
    		String seperator = Checker.getSeperatorYgDigunakan(range_thsms);
    		StringTokenizer st = new StringTokenizer(range_thsms,seperator);
    		range_thsms = null;
    		while(st.hasMoreTokens()) {
    			String token = st.nextToken();
    			while(token.contains(" ")) {
    				//pastikan tidak ada spasi untuk tiap token kata
    				token = token.replace(" ","");
    			}
    			if(range_thsms==null) {
    				range_thsms = new String(token);
    			}
    			else {
    				range_thsms = range_thsms+seperator+token;
    			}
    		}
    		
    		//populate semua range dari token
    		Vector vtmp = new Vector();
    		ListIterator litmp = vtmp.listIterator();
    			
    		st = new StringTokenizer(range_thsms,seperator);
    		while(st.hasMoreTokens()) {
    			String token = st.nextToken();
    			if(token.startsWith("=")) { //berarti fix harus ada
    				thsms_tmp = token.replace("=", "");
    				litmp.add(thsms_tmp);
    			}
    			else if(token.startsWith(">=")) {
    				thsms_tmp = token.replace(">=", "");
    				while(thsms_tmp.compareToIgnoreCase(thsms_max)<=0) {
    					litmp.add(thsms_tmp);
    					thsms_tmp = Tool.returnNextThsmsGivenTpAntara(thsms_tmp);
        			}
    			}
    			else if(token.startsWith(">")) {
    				thsms_tmp = token.replace(">", "");
    				thsms_tmp = Tool.returnNextThsmsGivenTpAntara(thsms_tmp);
    				while(thsms_tmp.compareToIgnoreCase(thsms_max)<=0) {
    					litmp.add(thsms_tmp);
    					thsms_tmp = Tool.returnNextThsmsGivenTpAntara(thsms_tmp);
        			}
    			}
    			/*
    			else if(token.startsWith("<>")) {
    				thsms_tmp = token.replace("<>", "");
    				litmp = vtmp.listIterator();
            		while(litmp.hasNext()) {
            			String thsms =(String)litmp.next();
            			if(thsms_tmp.compareToIgnoreCase(thsms)==0) {
            				litmp.remove();
            			}
            		}
    			}
    			*/
    			else if(token.startsWith("<=")) {
    				thsms_tmp = token.replace("<=", "");
    				while(thsms_tmp.compareToIgnoreCase(thsms_max)<=0 && thsms_tmp.compareToIgnoreCase(thsms_min)>=0) {
    					litmp.add(thsms_tmp);
    					thsms_tmp = Tool.returnPrevThsmsGivenTpAntara(thsms_tmp);
        			}
    			}
    			else if(token.startsWith("<")) {
    				thsms_tmp = token.replace("<", "");
    				thsms_tmp = Tool.returnPrevThsmsGivenTpAntara(thsms_tmp);
    				while(thsms_tmp.compareToIgnoreCase(thsms_max)<=0 && thsms_tmp.compareToIgnoreCase(thsms_min)>=0) {
    					litmp.add(thsms_tmp);
    					thsms_tmp = Tool.returnPrevThsmsGivenTpAntara(thsms_tmp);
        			}
    			}
    			else {
    				litmp.add(token);
    			}
    			
    		}
    		
    		//filtering
    		if(vtmp!=null && vtmp.size()>0) {
    			
    			st = new StringTokenizer(range_thsms,seperator);
        		while(st.hasMoreTokens()) {
        			String token = st.nextToken();
        			
        			if(token.startsWith(">=")) {
        				thsms_tmp = token.replace(">=", "");
        				litmp = vtmp.listIterator();
        				while(litmp.hasNext()) {
        					String thsms = (String)litmp.next();
        					if(thsms.compareToIgnoreCase(thsms_tmp)<0) {
        						litmp.remove();
        					}
        				}
        			}
        			else if(token.startsWith(">")) {
        				thsms_tmp = token.replace(">", "");
        				litmp = vtmp.listIterator();
        				while(litmp.hasNext()) {
        					String thsms = (String)litmp.next();
        					if(thsms.compareToIgnoreCase(thsms_tmp)<=0) {
        						litmp.remove();
        					}
        				}
        			}
        			/*
        			else if(token.startsWith("<>")) {
        				thsms_tmp = token.replace("<>", "");
        				litmp = vtmp.listIterator();
                		while(litmp.hasNext()) {
                			String thsms =(String)litmp.next();
                			if(thsms_tmp.compareToIgnoreCase(thsms)==0) {
                				litmp.remove();
                			}
                		}
        			}
        			*/
        			else if(token.startsWith("<=")) {
        				thsms_tmp = token.replace("<=", "");
        				litmp = vtmp.listIterator();
        				while(litmp.hasNext()) {
        					String thsms = (String)litmp.next();
        					if(thsms.compareToIgnoreCase(thsms_tmp)>0) {
        						litmp.remove();
        					}
        				}
        			}
        			else if(token.startsWith("<")) {
        				thsms_tmp = token.replace("<", "");
        				litmp = vtmp.listIterator();
        				while(litmp.hasNext()) {
        					String thsms = (String)litmp.next();
        					if(thsms.compareToIgnoreCase(thsms_tmp)>=0) {
        						litmp.remove();
        					}
        				}
        			}
        			/*
        			if(token.startsWith("=")) { //berarti fix harus ada
        				thsms_tmp = token.replace("=", "");
        				litmp.add(thsms_tmp);
        			}
        			else {
        				litmp.add(token);
        			}
        			*/
        		}
    		}
    			
    			
    		try {
    			vtmp=Tool.removeDuplicateFromVector(vtmp);
    			Collections.sort(vtmp);
    			litmp = vtmp.listIterator();
        		if(litmp.hasNext()) {
        			
        			String brs =(String)litmp.next();
        			tkn_thsms = new String(brs);
        			while(litmp.hasNext()) {
        				brs =(String)litmp.next();
        				tkn_thsms = tkn_thsms+output_seperator_used+brs;
        			}
        		}	
    		}
    		catch(Exception e) {}
    		
    		
    	}
		return tkn_thsms;
    }
    
    
    public static String fixDoubleSeperatorAndTknStartEndingWithSeperator(String token, String char_seperator) {
    	if(token!=null) {
    		token = token.trim();
    		if(token.startsWith(char_seperator)) {
    			token = "null"+token;
    		}
    		if(token.endsWith(char_seperator)) {
    			token = token+"null";
    		}
    		while(token.contains(char_seperator+char_seperator)) {
    			token = token.replace(char_seperator+char_seperator, char_seperator+"null"+char_seperator);
    		}
    	}
    	return token;	
    }
    
    public static String hitungNextPeriod(String periode_awal, String satuan_duarsi_periode, String lama_antar_periode) {
    	String periode=new String(periode_awal);
    	if(satuan_duarsi_periode.contains("semes")||satuan_duarsi_periode.contains("SEMES")) {
    		//semestertan
    		
    		if(periode.length()==4) {
    			periode=periode+"1";
    		}
    		if(periode.length()==5) {
    			int tot = Integer.parseInt(lama_antar_periode);
        		for(int i=0;i<tot;i++) {
        			periode = Tool.returnNextThsmsGivenTpAntara(periode);
        		}	
    		}
    		
    	}
    	else {
    		//tahunan
    		if(periode.length()==5) {
    			periode=periode.substring(0, 4);
    		}
    		if(periode.length()==5) {
    			int tot = Integer.parseInt(lama_antar_periode)*2;
        		for(int i=0;i<tot;i++) {
        			periode = Tool.returnNextThsmsGivenTpAntara(periode);
        		}	
    		}
    	}
    	return periode;	
    }

    
    public static String removeKurungTutup(String words) {
    	//System.out.println("words="+words);
    	try {
    		String left_kurung=null;
        	String right_kurung=null;
        	if(words.contains("]")) {
        		right_kurung = "]";
        		left_kurung = "[";
        	}
        	else if(words.contains("}")) {
        		right_kurung = "}";
        		left_kurung = "{";
        	}
        	else if(words.contains(")")) {
        		right_kurung = ")";
        		left_kurung = "(";
        	}
        	else if(words.contains(">")) {
        		right_kurung = ">";
        		left_kurung = "<";
        	}
        	
        	if(!Checker.isStringNullOrEmpty(right_kurung)) {
        		while(words.contains(right_kurung+left_kurung)) {
        			words=words.replace(right_kurung+left_kurung, "`"); 
        		}
        		while(words.contains(right_kurung)||words.contains(left_kurung)) {
        			words=words.replace(right_kurung, "");
        			words=words.replace(left_kurung, "");
        		}
        	}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	
		return words;
    }
    
    public static boolean createFolder(String folder_path) {
    	boolean created = false;
    	if(!Checker.isStringNullOrEmpty(folder_path)) {
    		StringTokenizer st = new StringTokenizer(folder_path,"/");
    		String path = "";
    		try {
    			while(st.hasMoreTokens()) {
    				path = path+"/"+st.nextToken();
    				if(!path.equalsIgnoreCase("/home")) {
    					File theDir = new File(path);
    					if (!theDir.exists()) {
    					    //System.out.println("creating directory: " + theDir.getName());
    					    try{
    					        theDir.mkdir();
    					        created = true;
    					    } 
    					    catch(SecurityException se){
    					        //handle it
    					    }
    					    /*
    					    if(created) {    
    					    	 System.out.println(path+" DIR created = "+created);  
    					    }
    					    */
    					}
    				}
    			}
        	}
        	catch(Exception e) {
        		
        	}	
    	}
    	
    	return created;
    }
}

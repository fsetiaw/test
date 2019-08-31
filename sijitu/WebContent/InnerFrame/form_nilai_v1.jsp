<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>	
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= null;  
boolean usrMhs = false;
if(validUsr.getObjNickNameGivenObjId().contains("MHS") || validUsr.getObjNickNameGivenObjId().contains("mhs") ) {
	usrMhs = true;
}
StringTokenizer stn = null;
String list_nilai_bobot = null;
//System.out.println("sampe sini hist krs");

%>

</head>
<body>
<div id="header">
<%@ include file="krsKhsSubMenu.jsp" %>
<%
String default_shift = Checker.getShiftMhs(npm);
default_shift = Getter.getKodeKonversiShift(default_shift);
String thsms_now = Checker.getThsmsNow();
String thsmsKrs=Checker.getThsmsKrs();
SearchDb sdb = new SearchDb();
String nmpst = sdb.getNmpst(kdpst);
String idkur = sdb.getIndividualKurikulum(kdpst, npm);
//System.out.println("hist valid approvee = "+validApprovee);
//String msg = request.getParameter("msg");
Vector vMk=null;
if(idkur!=null) {
	vMk = sdb.getListMatakuliahDalamKurikulum(kdpst,idkur);
}	
String infoKrsNotificationAtPmb = (String) request.getAttribute("infoKrsNotificationAtPmb");
String idNotif = null;
String kstegoriNotif = null;
String thsmsNotif = null;
String beritaNotif = null;
String npmSenderNotif = null;
String nmmSenderNotif = null;
String npmReceiverNotif = null;
String nmmReceiverNotif = null;
String kdpstReceiverNotif = null;
String smawlReceiverNotif = null;
String hiddenAtSenderNotif = null;
String hiddenAtReceiverNotif = null;
String delBySenderNotif = null;
String delByReceiverNotif = null;
String approvedNotif = null;
String declinedNotif = null;
String unlockApprovedNotif = null;
String unlockDeclinedNotif = null;
String noteNotif = null;
String updtmNotif = null;
//System.out.println("infoKrsNotificationAtPmb@historykrs.jsp="+infoKrsNotificationAtPmb);
if(infoKrsNotificationAtPmb!=null && !Checker.isStringNullOrEmpty(infoKrsNotificationAtPmb)) {
	StringTokenizer st = new StringTokenizer(infoKrsNotificationAtPmb,"#");
	idNotif = st.nextToken();
	kstegoriNotif = st.nextToken();
	thsmsNotif = st.nextToken();
	beritaNotif = st.nextToken();
	npmSenderNotif = st.nextToken();
	nmmSenderNotif = st.nextToken();
	npmReceiverNotif = st.nextToken();
	nmmReceiverNotif = st.nextToken();
	kdpstReceiverNotif = st.nextToken();
	smawlReceiverNotif = st.nextToken();
	hiddenAtSenderNotif = st.nextToken();
	hiddenAtReceiverNotif = st.nextToken();
	delBySenderNotif = st.nextToken();
	delByReceiverNotif = st.nextToken();
	approvedNotif = st.nextToken();
	declinedNotif = st.nextToken();
	unlockApprovedNotif = st.nextToken();
	unlockDeclinedNotif = st.nextToken();
	noteNotif = st.nextToken();
	updtmNotif = st.nextToken();
}
String tknPaInfo = (String)request.getAttribute("tknPaInfo");
String currentPa = (String)request.getAttribute("currentPa");
String curNpmPa = "N/A";
String curNmmPa = "N/A";
if(currentPa!=null) {
	StringTokenizer st = new StringTokenizer(currentPa,",");
	curNpmPa = st.nextToken();
	curNmmPa = st.nextToken();
}
request.removeAttribute("infoKrsNotificationAtPmb");
request.removeAttribute("tknPaInfo");
request.removeAttribute("currentPa");
//System.out.println("tknPaInfo="+tknPaInfo);
//System.out.println("currentPa="+currentPa);
//System.out.println("ini3");
Vector vSelectMk = new Vector();
ListIterator liSmk = vSelectMk.listIterator();
if(vMk!=null && vMk.size()>0) {
	//System.out.println("ini5");
	ListIterator liMk = vMk.listIterator();
	while(liMk.hasNext()) {
		String idkmk=(String)liMk.next();
		String kdkmk=(String)liMk.next();
		String nakmk=(String)liMk.next();
		String skstm=(String)liMk.next();
		String skspr=(String)liMk.next();
		String skslp=(String)liMk.next();
		String kdwpl=(String)liMk.next();
		String jenis=(String)liMk.next();
		String stkmk=(String)liMk.next();
		String nodos=(String)liMk.next();
		String semes=(String)liMk.next();
		liSmk.add(idkmk+","+kdkmk+","+nakmk);
	}	
}
//System.out.println("sampe sini hist krs2");
%>

</div>
<div class="colmask fullpage">
	<div class="col1">

		<br />
		
		<%
if(validUsr.isUsrAllowTo_updated("ink",v_npmhs)) {		
		/*
		msg di pas dari HistoryKrsKhs servlet, bilai msg !=null berarti blum ditentukan kurikulumnya
		*/
		//System.out.println("sampe sini");
		//System.out.println("msg ="+msg);
if(msg!=null) {
			//kurikulum blm ditentukan
	Vector v_cf = validUsr.getScopeUpd7des2012("hasEditMkKurikulum");
	if(v_cf!=null && v_cf.size()>0) {
		%>
		
		<h2 align="center">Untuk Menambah atau Edit Krs<br/>Harap <a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/editTargetKurikulum.jsp?kdpst_nmpst=<%=v_kdpst%>,<%=nmpst %>" target="inner_iframe" class="active">Tentukan Kurikulum Untuk <%=nmm %> </a>Terlebih Dahulu</h2>
		<br/>
		<%
	}
	else {
				%>
				<h2 align="center">Kurikulum Untuk <%=nmm %> Belum Diisi oleh Tata Usaha</h2>
				<%		
	}	
}
		//else {
			//System.out.println("allowViewKrs="+allowViewKrs);
if(allowViewKrs) {
				
				
	Vector vHistKrsKhs = (Vector)request.getAttribute("vHistKrsKhs");
	/*
	vHistTmp buat cetak krs - ditiadakan karena sama aja vHistKrsKhs
	
	Vector vHistTmp = (Vector)vHistKrsKhs.clone();
	//System.out.println("vHistTmp="+vHistTmp.size());
	//System.out.println("cetakKrsMode");
	if(cetakKrsMode||cetakKhsMode) {
		//System.out.println("cetakKrsMode1");
	
		session.setAttribute("vHistTmp", vHistTmp);
	}
	*/
	//System.out.println("size1 ="+vHistKrsKhs.size());
	Vector vTrnlp = (Vector)request.getAttribute("vTrnlp");
	//System.out.println("size1a ="+vTrnlp.size());

	session.setAttribute("vHistKrsKhsForEdit",vHistKrsKhs); //kayaknya sudah nggaguna nih = vHistKrsKhs
	session.setAttribute("vTrnlpForEdit",vTrnlp);
	request.removeAttribute("vHistKrsKhs");
	request.removeAttribute("vTrnlp");
				//System.out.println("vHistTmp1="+vHistTmp.size());	
			
				//System.out.println("point 1");
	if(vHistKrsKhs!=null && vHistKrsKhs.size()>0) {		
	%>
	<form action="go.updateNilaiMkIndividu">
	<input type="hidden" name="target_cmd" value="<%=cmd %>" />
	<input type="hidden" name="target_npmhs" value="<%=v_npmhs %>" />
	<input type="hidden" name="target_nmmhs" value="<%=v_nmmhs %>" />
	<input type="hidden" name="target_idobj" value="<%=v_id_obj %>" />
	<input type="hidden" name="target_objlv" value="<%=v_obj_lvl %>" />
	<input type="hidden" name="target_kdpst" value="<%=v_kdpst %>" />

	<%	
	
		ListIterator lih = vHistKrsKhs.listIterator();
		String prev_thsms="";
		String prev_kdkmk="";
		String prev_nakmk="";
		String prev_nlakh="";
		String prev_bobot="";
		String prev_sksmk="";
		String prev_kelas="";
		String prev_sksem="";
		String prev_nlips="";
		String prev_skstt="";
		String prev_nlipk="";
		String prev_shift="";
		String prev_krsdown="";
		String prev_khsdown="";
		String prev_bakprove="";
		String prev_paprove="";
		String prev_note="";
		String prev_lock="";
		String prev_baukprove="";
					
		String prev_idkmk ="";
		String prev_addReq ="";
		String prev_drpReq  ="";
		String prev_npmPa ="";
		String prev_npmBak ="";
		String prev_npmBaa ="";
		String prev_npmBauk ="";
		String prev_baaProve ="";
		String prev_ktuProve ="";
		String prev_dknProve ="";
		String prev_npmKtu ="";
		String prev_npmDekan ="";
		String prev_lockMhs ="";
		String prev_kodeKampus ="";
		
		String prev_cuid ="";
		String prev_cuid_init ="";
		String prev_npmdos ="";
		String prev_nodos ="";
		String prev_npmasdos ="";
		String prev_noasdos ="";
		String prev_nmmdos ="";
		String prev_nmmasdos =""; 
		String prev_nlakh_by_dsn ="";
		if(lih.hasNext()) {
			String thsms=(String)lih.next();
			String kdkmk=(String)lih.next();
			String nakmk=(String)lih.next();
			String nlakh=(String)lih.next();
			String bobot=(String)lih.next();
			String sksmk=(String)lih.next();
			String kelas=(String)lih.next();
			String sksem=(String)lih.next();
			String nlips=(String)lih.next();
			String skstt=(String)lih.next();
			String nlipk=(String)lih.next();
			String shift=(String)lih.next();
			String krsdown=(String)lih.next();
			String khsdown=(String)lih.next();
			String bakprove=(String)lih.next();
			String paprove=(String)lih.next();
			String note=(String)lih.next();
			String lock=(String)lih.next();
			String baukprove=(String)lih.next();
					
			String idkmk =(String)lih.next();
			String addReq =(String)lih.next();
			String drpReq  =(String)lih.next();
			String npmPa =(String)lih.next();
			String npmBak =(String)lih.next();
			String npmBaa =(String)lih.next();
			String npmBauk =(String)lih.next();
			String baaProve =(String)lih.next();
			String ktuProve =(String)lih.next();
			String dknProve =(String)lih.next();
			String npmKtu =(String)lih.next();
			String npmDekan =(String)lih.next();
			String lockMhs =(String)lih.next();
			String kodeKampus = (String)lih.next();
			
			String cuid = (String)lih.next();
			String cuid_init = (String)lih.next();
			String npmdos = (String)lih.next();
			String nodos = (String)lih.next();
			String npmasdos = (String)lih.next();
			String noasdos = (String)lih.next();
			String nmmdos = (String)lih.next();
			String nmmasdos = (String)lih.next(); 
			String nlakh_by_dsn = (String)lih.next();
			
			prev_thsms = ""+thsms;
			prev_kdkmk=""+kdkmk;
			prev_nakmk=""+nakmk;
			prev_nlakh=""+nlakh;
			prev_bobot=""+bobot;
			prev_sksmk=""+sksmk;
			prev_kelas=""+kelas;
			prev_sksem=""+sksem;
			prev_nlips=""+nlips;
			prev_skstt=""+skstt;
			prev_nlipk=""+nlipk;
			prev_shift=""+shift;
			prev_krsdown=""+krsdown;
			prev_khsdown=""+khsdown;
			prev_bakprove=""+bakprove;
			prev_paprove=""+paprove;
			prev_note=""+note;
			prev_lock=""+lock;
			prev_baukprove=""+baukprove;
					
			prev_idkmk =""+idkmk;
			prev_addReq =""+addReq;
			prev_drpReq  =""+drpReq;
			prev_npmPa =""+npmPa;
			prev_npmBak =""+npmBak;
			prev_npmBaa =""+npmBaa;
			prev_npmBauk =""+npmBauk;
			prev_baaProve =""+baaProve;
			prev_ktuProve =""+ktuProve;
			prev_dknProve =""+dknProve;
			prev_npmKtu =""+npmKtu;
			prev_npmDekan =""+npmDekan;
			prev_lockMhs =""+lockMhs;
			prev_kodeKampus =""+kodeKampus;
			
			prev_cuid  =""+cuid;
			prev_cuid_init  =""+cuid_init;
			prev_npmdos  =""+npmdos;
			prev_nodos  =""+nodos;
			prev_npmasdos  =""+npmasdos;
			prev_noasdos  =""+noasdos;
			prev_nmmdos  =""+nmmdos;
			prev_nmmasdos  =""+nmmasdos; 
			prev_nlakh_by_dsn  =""+nlakh_by_dsn;

					    %>
					<br/>
					<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:600px">
					<%
			String submitButtonValue="Download";
			if(!cetakKrsMode && !cetakKhsMode) {
						
					
						// updated ditambaha validasi validApprove = memberikan akses perwalian, jadi vurNpmPa=usr.npm harusnya tidak ada pengaruh lagi
						// updated lagi - cukup valid approvee ajah, krn yg penting filternya pada krs notifikasi, bila ada di krs notifikasi berarti validApprovee
						//if(paApprover && prev_lock.equalsIgnoreCase("false") && (curNpmPa.equalsIgnoreCase(validUsr.getNpm())||(validApprovee!=null && validApprovee.equalsIgnoreCase("true"))) && prev_thsms.equalsIgnoreCase(thsmsKrs)) { //mode view krs
				if(validApprovee!=null && validApprovee.equalsIgnoreCase("true") && prev_thsms.equalsIgnoreCase(thsmsKrs)) {
    			
 		   		}
				else {
					%>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center" colspan="3"><label><B>
        				<%
        			String keter_thsms_and_value = Converter.convertThsms(prev_thsms);
					StringTokenizer stt = new StringTokenizer(keter_thsms_and_value,"#&");
					String keter_thsms = stt.nextToken();
					String value_thsms = stt.nextToken();
        			out.print(keter_thsms); 
        			list_nilai_bobot = new String(Getter.getAngkaPenilaian(prev_thsms, v_kdpst));
        			stn = new StringTokenizer(list_nilai_bobot,"`");
        			//out.print("<br/>"+prev_thsms+" "+v_kdpst+" "+list_nilai_bobot);
        				%></B> </label></td>
        			</tr>
        			<%
				}
        	}
			else {
					
			}
        			%>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;width:80px"><label><B>KODE</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:left;width:455px"><label><B>MATAKULIAH</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:65px"><label><B>NILAI</B> </label></td>

        			</tr>
        			<tr>
        			
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kdkmk %></B> </label></td>
        				<td style="color:#000;text-align:left;"><label><B><%=prev_nakmk %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B>
        				<%
        	//if(prev_nlakh_by_dsn.equalsIgnoreCase("true")) {
        	if(false) {	
        			//berarti diinput oleh BAA dimana untuk melengkapi riwayat krs
        			//jadi kalo perbaikna harus via admin atau ditiban ulang
        			//atau telah diinput oleh dosennya dan harus dari input nilai per kelas
        			out.print(prev_nlakh);
        	}
        	else {
        				%>
        				<select name="nilai_akhir" style="width:99%;text-indent:20px;direction:ltr">
        				<%
        		stn = new StringTokenizer(list_nilai_bobot,"`");
        		while(stn.hasMoreTokens()) {
        			String nilai = stn.nextToken();
        			String boboti = stn.nextToken();
        			if(prev_nlakh.equalsIgnoreCase(nilai)) {
        				%>
        					<option value="<%=prev_thsms+"`"+prev_idkmk+"`"+prev_kdkmk+"`"+nilai+"`"+boboti %>" selected="selected"><%=nilai %></option>
        				<%		
        			}
        			else {
        					%>
        					<option value="<%=prev_thsms+"`"+prev_idkmk+"`"+prev_kdkmk+"`"+nilai+"`"+boboti %>"><%=nilai %></option>
        				<%
        			}
        		} 
        				%>
        				</select>
        	<%
        	}
        	%>			
        				</B> </label></td>
        				
        			</tr>
        			<%
			while(lih.hasNext()) {
				thsms=(String)lih.next();
				kdkmk=(String)lih.next();
				nakmk=(String)lih.next();
				nlakh=(String)lih.next();
				bobot=(String)lih.next();
				sksmk=(String)lih.next();
				kelas=(String)lih.next();
				sksem=(String)lih.next();
				nlips=(String)lih.next();
				skstt=(String)lih.next();
				nlipk=(String)lih.next();
				shift=(String)lih.next();
				krsdown=(String)lih.next();
				khsdown=(String)lih.next();
				bakprove=(String)lih.next();
				paprove=(String)lih.next();
				note=(String)lih.next();
				lock=(String)lih.next();
				baukprove=(String)lih.next();
						
				idkmk =(String)lih.next();
				addReq =(String)lih.next();
				drpReq  =(String)lih.next();
				npmPa =(String)lih.next();
				npmBak =(String)lih.next();
				npmBaa =(String)lih.next();
				npmBauk =(String)lih.next();
				baaProve =(String)lih.next();
				ktuProve =(String)lih.next();
				dknProve =(String)lih.next();
				npmKtu =(String)lih.next();
				npmDekan =(String)lih.next();
				lockMhs =(String)lih.next();
				kodeKampus =(String)lih.next();
						
				cuid = (String)lih.next();
				cuid_init = (String)lih.next();
				npmdos = (String)lih.next();
				nodos = (String)lih.next();
				npmasdos = (String)lih.next();
				noasdos = (String)lih.next();
				nmmdos = (String)lih.next();
				nmmasdos = (String)lih.next(); 
				nlakh_by_dsn = (String)lih.next(); 
						
				if(prev_thsms.equalsIgnoreCase(thsms)) {
					prev_thsms = ""+thsms;
					prev_kdkmk=""+kdkmk;
					prev_nakmk=""+nakmk;
					prev_nlakh=""+nlakh;
					prev_bobot=""+bobot;
					prev_sksmk=""+sksmk;
					prev_kelas=""+kelas;
					prev_sksem=""+sksem;
					prev_nlips=""+nlips;
					prev_skstt=""+skstt;
					prev_nlipk=""+nlipk;
					prev_shift=""+shift;
					prev_krsdown=""+krsdown;
					prev_khsdown=""+khsdown;
					prev_bakprove=""+bakprove;
					prev_paprove=""+paprove;
					prev_note=""+note;
					prev_lock=""+lock;
					prev_baukprove=""+baukprove;
					prev_idkmk = ""+idkmk;
					prev_addReq =""+addReq;
					prev_drpReq  =""+drpReq;
					prev_npmPa =""+npmPa;
					prev_npmBak =""+npmBak;
					prev_npmBaa =""+npmBaa;
					prev_npmBauk =""+npmBauk;
					prev_baaProve =""+baaProve;
					prev_ktuProve =""+ktuProve;
					prev_dknProve =""+dknProve;
					prev_npmKtu =""+npmKtu;
					prev_npmDekan =""+npmDekan;
					prev_lockMhs =""+lockMhs;
					prev_kodeKampus =""+kodeKampus;
					
					prev_cuid  =""+cuid;
					prev_cuid_init  =""+cuid_init;
					prev_npmdos  =""+npmdos;
					prev_nodos  =""+nodos;
					prev_npmasdos  =""+npmasdos;
					prev_noasdos  =""+noasdos;
					prev_nmmdos  =""+nmmdos;
					prev_nmmasdos  =""+nmmasdos; 
					prev_nlakh_by_dsn  =""+nlakh_by_dsn;
						%>
					<tr>
					
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kdkmk %></B> </label></td>
        				<td style="color:#000;text-align:left;"><label><B><%=prev_nakmk %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B>
        				<%
        			//if(prev_nlakh_by_dsn.equalsIgnoreCase("true")) {
        			if(false) {
        			
        			//berarti diinput oleh BAA dimana untuk melengkapi riwayat krs
        			//jadi kalo perbaikna harus via admin atau ditiban ulang
        			//atau telah diinput oleh dosennya dan harus dari input nilai per kelas
        				out.print(prev_nlakh);
        			}
        			else {
        				%>
        				<select name="nilai_akhir" style="width:99%;text-indent:20px;direction:ltr">
        				<%
        				stn = new StringTokenizer(list_nilai_bobot,"`");
        				while(stn.hasMoreTokens()) {
        					String nilai = stn.nextToken();
        					String boboti = stn.nextToken();
        					if(prev_nlakh.equalsIgnoreCase(nilai)) {
        				%>
        					<option value="<%=prev_thsms+"`"+prev_idkmk+"`"+prev_kdkmk+"`"+nilai+"`"+boboti %>" selected="selected"><%=nilai %></option>
        				<%		
        					}
        					else {
        					%>
        					<option value="<%=prev_thsms+"`"+prev_idkmk+"`"+prev_kdkmk+"`"+nilai+"`"+boboti %>"><%=nilai %></option>
        				<%
        					}
        				} 
        				%>
        				</select>
        	<%
        			}
        	%>
        				
        				
        				</B> </label></td>
        				
        			</tr>
        			<%
				}
				else {
					if(!prev_thsms.equalsIgnoreCase(thsms)) {
								//ganti tahun
								//1. tutup table sebelumnya dengan ngasih nilai ipk dan ips
					
	        		%>
					<tr>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="3"><label><B>SKS & IP SEMESTER</B> </label></td>
        				
        			</tr>
        			
        		</table>
        		<br />	
					<%		
						prev_thsms = ""+thsms;
						prev_kdkmk=""+kdkmk;
						prev_nakmk=""+nakmk;
						prev_nlakh=""+nlakh;
						prev_bobot=""+bobot;
						prev_sksmk=""+sksmk;
						prev_kelas=""+kelas;
						prev_sksem=""+sksem;
						prev_nlips=""+nlips;
						prev_skstt=""+skstt;
						prev_nlipk=""+nlipk;
						prev_shift=""+shift;
						prev_krsdown=""+krsdown;
						prev_khsdown=""+khsdown;
						prev_bakprove=""+bakprove;
						prev_paprove=""+paprove;
						prev_note=""+note;
						prev_lock=""+lock;
						prev_baukprove=""+baukprove;
						
						prev_idkmk = ""+idkmk;
						prev_addReq =""+addReq;
						prev_drpReq  =""+drpReq;
						prev_npmPa =""+npmPa;
						prev_npmBak =""+npmBak;
						prev_npmBaa =""+npmBaa;
						prev_npmBauk =""+npmBauk;
						prev_baaProve =""+baaProve;
						prev_ktuProve =""+ktuProve;
						prev_dknProve =""+dknProve;
						prev_npmKtu =""+npmKtu;
						prev_npmDekan =""+npmDekan;
						prev_lockMhs =""+lockMhs;
						prev_kodeKampus = ""+kodeKampus;
						
						prev_cuid  =""+cuid;
						prev_cuid_init  =""+cuid_init;
						prev_npmdos  =""+npmdos;
						prev_nodos  =""+nodos;
						prev_npmasdos  =""+npmasdos;
						prev_noasdos  =""+noasdos;
						prev_nmmdos  =""+nmmdos;
						prev_nmmasdos  =""+nmmasdos; 
						prev_nlakh_by_dsn  =""+nlakh_by_dsn;
							%>
					
				<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:600px">
        			
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center" colspan="3"><label><B>
        				<%
        				String keter_thsms_and_value = Converter.convertThsms(prev_thsms);
						StringTokenizer stt = new StringTokenizer(keter_thsms_and_value,"#&");
						String keter_thsms = stt.nextToken();
						String value_thsms = stt.nextToken();
        				out.print(keter_thsms); 
        				//System.out.println("prev_thsms="+prev_thsms);
        				list_nilai_bobot = new String(Getter.getAngkaPenilaian(prev_thsms, v_kdpst));
        				stn = new StringTokenizer(list_nilai_bobot,"`");
        			//out.print("<br/>"+prev_thsms+" "+v_kdpst+" "+list_nilai_bobot);
        				%></B> </label></td>
        			</tr>
        			
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;width:80px"><label><B>KODE</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:left;width:455px"><label><B>MATAKULIAH</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:65px"><label><B>NILAI</B> </label></td>
        				
        			</tr>
        			<tr>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kdkmk %></B> </label></td>
        				<td style="color:#000;text-align:left;"><label><B><%=prev_nakmk %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B>
        				<%
        				if(false) {
        				//if(prev_nlakh_by_dsn.equalsIgnoreCase("true")) {
        			//berarti diinput oleh BAA dimana untuk melengkapi riwayat krs
        			//jadi kalo perbaikna harus via admin atau ditiban ulang
        			//atau telah diinput oleh dosennya dan harus dari input nilai per kelas
        					out.print(prev_nlakh);
        				}
        				else {
        				%>
        				<select name="nilai_akhir" style="width:99%;text-indent:20px;direction:ltr">
        				<%
        					stn = new StringTokenizer(list_nilai_bobot,"`");
        					while(stn.hasMoreTokens()) {
        						String nilai = stn.nextToken();
        						String boboti = stn.nextToken();
        						if(prev_nlakh.equalsIgnoreCase(nilai)) {
        				%>
        					<option value="<%=prev_thsms+"`"+prev_idkmk+"`"+prev_kdkmk+"`"+nilai+"`"+boboti %>" selected="selected"><%=nilai %></option>
        				<%		
        						}
        						else {
        					%>
        					<option value="<%=prev_thsms+"`"+prev_idkmk+"`"+prev_kdkmk+"`"+nilai+"`"+boboti %>"><%=nilai %></option>
        				<%
        						}
        					} 
        				%>
        				</select>
        	<%
        				}
        	%>
        				
        				
        				</B> </label></td>
        				
        			</tr>
        		<% 	
					}
				}
			}//end while
					/*
					*tutup table setelah end while
					*/
	        		%>	
					<tr>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="3"><label><B>SKS & IP SEMESTER</B> </label></td>
        				
        			</tr>
        			
        		</table>
        		<%
		}
	%>
			<br/>
			<center><input type="submit" value="UPDATE NILAI" style="width:40%;height:35px"/></center>
			
	</form>	
	<%	
	}
	else {
					
				%>
				<h1 style="text-align:center">BELUM ADA REKORD KRS/KHS</h1>
				<div style="text-align:center;font-style:italic">Kami dalam tahap migrasi, harap "kirim pesan" ke tata usaha fakultas bila data riwayat studi tidak sesuai agar dapat mendapat prioritas update</div>
				<%
	}
}//end if(size>0)
else {
				%>
				<h2 align="center"><b>Anda Tidak Mempunyai Hak Akses Untuk Data ini</b></h2>
				<% 
}

}
else {
	out.println("ANDA TIDAK MEMILIKI AKSES KE LAMAN INI");
}
		//creta force back to
		//String forceTo = "get.histKrs?id_obj="+v_id_obj+"&nmm="+v_nmmhs+"&npm="+v_npmhs+"&obj_lvl="+v_obj_lvl+"&kdpst="+v_kdpst+"&cmd=histKrs";
		//session.removeAttribute("forceBackTo");
		//session ngga diapus krn hubungan dengan force to krs approval
		%>
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>
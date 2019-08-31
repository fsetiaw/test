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
<%@ page import="beans.dbase.trakm.HitungKhs" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>	
<%@ page import="beans.dbase.trnlp.*" %>
<%
//System.out.println("masuk22");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
UpdateDbTrnlp udp = new UpdateDbTrnlp();
Vector v= null;  
boolean usrMhs = false;
if(validUsr.getObjNickNameGivenObjId().contains("MHS") || validUsr.getObjNickNameGivenObjId().contains("mhs") ) {
	usrMhs = true;
}
//System.out.println("sampe sini hist krs v2");
%>


<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"></script>
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<style>
.table {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
}
.table thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9; }

.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px }
</style>
	<script>	
		$(document).ready(function() {
			$("#wait").hide();
			$("#main").show();
		});	
	</script>

</head>
<body>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
			<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai</p>
</div>
<div id="main">
	<div id="header">
<%@ include file="krsKhsSubMenu.jsp" %>
<%
int updated = udp.copyMataKulishAsal(npm);

boolean sudah_out = false; //k/d/l
String smawl = Checker.getSmawl(npm);

String default_shift = Checker.getShiftMhs(npm);
default_shift = Getter.getKodeKonversiShift(default_shift);
String thsms_now = Checker.getThsmsNow();
String thsmsKrs=Checker.getThsmsKrs();
SearchDb sdb = new SearchDb();
String nmpst = sdb.getNmpst(kdpst);
String idkur = sdb.getIndividualKurikulum(kdpst, npm);
String tipe_out = "";
String thsms_out = "";
Vector v_target_npmhs = new Vector();
v_target_npmhs.add(npm);
//System.out.println("npmhs111="+npm);
HitungKhs hk = new HitungKhs();
hk.hitungRiwayatTrakmMhs(v_target_npmhs);
//System.out.println("hist valid approvee = "+validApprovee);
//String msg = request.getParameter("msg");
Vector vMk=null;
if(idkur!=null) {
	vMk = sdb.getListMatakuliahDalamKurikulum(kdpst,idkur);
}	

String nmmhs = request.getParameter("nmm");
String npmhs = request.getParameter("npm");

String infoKrsNotificationAtPmb = (String) request.getAttribute("infoKrsNotificationAtPmb");
if(Checker.isStringNullOrEmpty(infoKrsNotificationAtPmb)) {
	infoKrsNotificationAtPmb = (String) session.getAttribute("infoKrsNotificationAtPmb_session");
	session.removeAttribute("infoKrsNotificationAtPmb_session");
}
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
if(Checker.isStringNullOrEmpty(tknPaInfo)) {
	tknPaInfo = (String)session.getAttribute("tknPaInfo_session");
	session.removeAttribute("tknPaInfo_session");
}
String currentPa = (String)request.getAttribute("currentPa");
if(Checker.isStringNullOrEmpty(currentPa)) {
	currentPa = (String)session.getAttribute("currentPa_session");
	session.removeAttribute("currentPa_session");
}


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
Vector vSelectMk = new Vector();
ListIterator liSmk = vSelectMk.listIterator();
if(vMk!=null && vMk.size()>0) {
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
%>

	</div>
	<div class="colmask fullpage">
	<div class="col1">
		<!-- Column 1 start -->
		<br>
			<table class="table" style="width:95%">
					<!--  table align="center" border="1px" bordercolor="#29A329" style="background:#D6EB99;color:#000;width:800px"-->
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;font-size:1.75em" colspan="5"><label><B>PENGAJUAN & RIWAYAT KRS <%=nmmhs %><br>[<%=npmhs %>]</B> </label></td>
        			</tr>
        	</table>
 			
		<%
		String tmp = "";
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
		
		<h2 align="center">Untuk Menambah atau Edit Krs<br/>Harap Tentukan Kurikulum Untuk <%=nmm %> Terlebih Dahulu</h2>
		<br/>
		<%
	}
	else {
				%>
				<h2 align="center">Kurikulum Untuk <%=nmm %> Belum Diisi oleh Tata Usaha</h2>
				<%		
	}	
}

if(allowViewKrs) {
	String last_thsms_after_iter="";			
				
	Vector vHistKrsKhs = (Vector)request.getAttribute("vHistKrsKhs");
	if(vHistKrsKhs==null || vHistKrsKhs.size()<1) {
		vHistKrsKhs = (Vector)session.getAttribute("vHistKrsKhs_session");
		session.removeAttribute("vHistKrsKhs_session");
	}
	Vector vHistTrlsm = (Vector)request.getAttribute("vHistTrlsm");
	if(vHistTrlsm==null || vHistTrlsm.size()<1) {
		vHistTrlsm = (Vector)session.getAttribute("vHistTrlsm_session");
		session.removeAttribute("vHistTrlsm_session");
	}
	
	
	
	Vector vTrnlp = (Vector)request.getAttribute("vTrnlp");
	if(vTrnlp==null || vTrnlp.size()<1) {
		vTrnlp = (Vector)session.getAttribute("vTrnlp_session");
		session.removeAttribute("vTrnlp_session");
	}

	session.setAttribute("vHistKrsKhsForEdit",vHistKrsKhs); //kayaknya sudah nggaguna nih = vHistKrsKhs
	session.setAttribute("vTrnlpForEdit",vTrnlp);
	request.removeAttribute("vHistKrsKhs");
	request.removeAttribute("vTrnlp");
				//System.out.println("vHistTmp1="+vHistTmp.size());	
	if(vTrnlp!=null && vTrnlp.size()>0) {
		ListIterator liT = vTrnlp.listIterator();
		if(liT.hasNext()) {
			String thsms=(String)liT.next();
			String kdkmk=(String)liT.next();
			String nakmk=(String)liT.next();
			String nlakh=(String)liT.next();
			String bobot=(String)liT.next();
			String kdasl=(String)liT.next();
			String nmasl=(String)liT.next();
			String sksmk=(String)liT.next();
			String totSksTransfer=(String)liT.next();
			String sksas = (String)liT.next();
			String transferred = (String)liT.next();
			if(!cetakKrsMode && !cetakKhsMode) {
					%>
					
					<P>
					<table class="table" style="width:95%">
					<!--  table align="center" border="1px" bordercolor="#29A329" style="background:#D6EB99;color:#000;width:800px"-->
        			<tr>
        				<td style="background:#29A329;color:#fff;text-align:center;font-size:1.75em" colspan="5"><label><B>MATAKULIAH PINDAHAN</B> </label></td>
        			</tr>
        			<tr>
        				<td style="background:#29A329;color:#fff;text-align:center;width:70px"><label><B>KODE</B> </label></td>
        				<td style="background:#29A329;color:#fff;text-align:left;width:585px"><label><B>MATAKULIAH</B> </label></td>
        				<td style="background:#29A329;color:#fff;text-align:center;width:55px"><label><B>NILAI</B> </label></td>
        				<td style="background:#29A329;color:#fff;text-align:center;width:55px"><label><B>BOBOT</B> </label></td>
        				<td style="background:#29A329;color:#fff;text-align:center;width:35px"><label><B>SKS</B> </label></td>
        			</tr>
        			<tr>
        				
        				<td style="color:#000;text-align:center;"><label><B><%=kdkmk %></B> </label></td>
        				<td style="color:#000;text-align:left;"><label><B><%=nakmk %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=nlakh %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=bobot %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=sksmk %></B> </label></td>
        				
        			</tr>
					<%	
				while(liT.hasNext()) {
					thsms=(String)liT.next();
					kdkmk=(String)liT.next();
					nakmk=(String)liT.next();
					nlakh=(String)liT.next();
					bobot=(String)liT.next();
					kdasl=(String)liT.next();
					nmasl=(String)liT.next();
					sksmk=(String)liT.next();
					totSksTransfer=(String)liT.next();
					sksas = (String)liT.next();
					transferred = (String)liT.next();
					%>
					<tr>
        				<td style="color:#000;text-align:center;"><label><B><%=kdkmk %></B> </label></td>
        				<td style="color:#000;text-align:left;"><label><B><%=nakmk %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=nlakh %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=bobot %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=sksmk %></B> </label></td>
        			
        			</tr>
        			<%		
				}
					//tutuup table
					%>
					<tr>
        				<td style="background:#29A329;color:#fff;text-align:center;" colspan="4"><label><B>TOTAL MATAKULIAH TRANSFER / PINDAHAN</B> </label></td>
        				<td style="background:#29A329;color:#fff;text-align:center;"><label><B><%=totSksTransfer %></B> </label></td>
        			</tr>
        			</table>
        			</P>
        			<br />
					<%
			}//end if(!cetakmode)
		}
	}//end cetak krs moew
		
	
	String thsms_btstu = Tool.calcBatasStudi(npm);			
	if(vHistKrsKhs!=null && vHistKrsKhs.size()>0) {		
		/*
			UPDATED BILA THSMS KRS pertama > smawl, maka cek trlsm
		*/
		ListIterator lih = vHistKrsKhs.listIterator();
		if(lih.hasNext()) {
			String thsms=(String)lih.next();
			
			last_thsms_after_iter = new String(thsms);
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
			//System.out.println("nlipk1="+nlipk);
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
			
			//boolean sms_pendek = Checker.isSmsPendek(smawl);
			//while(smawl.compareToIgnoreCase(thsms)<0 && !sms_pendek) {
			while(smawl.compareToIgnoreCase(thsms)<0  && !Checker.isSmsPendek(smawl)) {	
				ListIterator lit = vHistTrlsm.listIterator();
				boolean match = false;
				String status_trlsm = null;
				while(lit.hasNext() && !match) {
					String brs = (String)lit.next();
					if(brs.startsWith(smawl)) {
						match=true;
						StringTokenizer st = new StringTokenizer(brs,"`");
						st.nextToken();//thsms
						String stmhs = st.nextToken();
					%>
				<table class="table" style="width:95%">
				<!--  table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px" -->
        			<tr>
	    		     	<td style="background:#369;color:#fff;text-align:center;font-size:1.75em" colspan="8"><label><B>
	    		     	<%
	    		     	out.print(Converter.convertThsmsKeterOnly(smawl));
	        			if(smawl.compareToIgnoreCase(thsms_btstu)==0) {
			         		out.print("&nbsp&nbsp[BATAS MAKSIMAL LAMA STUDI]");
			         	}
	        			else if(smawl.compareToIgnoreCase(thsms_btstu)>0) {
			         		out.print("&nbsp&nbsp[LEWAT BATAS MAKSIMAL LAMA STUDI : "+thsms_btstu+"]");
			         	}
	    		     	//Converter.convertThsmsKeterOnly(target_next_thsms) 
	    		     	%></B> </label></td>
	    		   	 	</tr>
	    		   	 	<tr>
	    		     		<td style="text-align:center;font-weight:bold;background:#A0A0A0;font-size:1.5em" colspan="8"><label><B>
	    		     	<%
	    		     	if(stmhs!=null && !Checker.isStringNullOrEmpty(stmhs)) {
			    			if(stmhs.equalsIgnoreCase("C")) {
	    				    	out.print("CUTI AKADEMIK");
							}
	    		     		else if(stmhs.equalsIgnoreCase("K")) {
			    		    	out.print("KELUAR");
	    				    }
							else if(stmhs.equalsIgnoreCase("D")) {
								out.print("D.O.");
			    		    }
							else if(stmhs.equalsIgnoreCase("A")) {
								out.print("AKTIF [DATA KRS TIDAK TERCATAT]");
							}
							else if(stmhs.equalsIgnoreCase("N") || Checker.isStringNullOrEmpty(stmhs)) {
								out.print("NON AKTIF");
							}
							else if(stmhs.equalsIgnoreCase("P")) {
								out.print("PINDAH PRODI");
							}
							else if(stmhs.equalsIgnoreCase("L")) {
								out.print("L U L U S  /  W I S U D A [DATA KRS TIDAK TERCATAT]");
							}
	    		     	}
	    		     	else {
	    		     		out.print("NON AKTIF");
	    		     	}
						
	    		     	%></B> </label></td>
	    		    	</tr>
	    		    
	    			</table> 
	    			<br>   				
					<%	
					}
				}
				//untuk TRLSM tidak perlu cek sms antara
				smawl = Tool.returnNextThsmsGivenTpAntara(smawl);
			}
			
		}
		
		
		/*
		RIWAYAT KRS TIDAK PERLU DI CEK DENGAN THSMS_BTSU KRN KALO MEMANG DEMIKIAN YAH HARUS SEPERTI ITU
		TAPI UNTUK RIWAYAT TRLSM SETELAN > THSMS KRS TERAKHIR HARU DI CEK
		*/
		
		
		lih = vHistKrsKhs.listIterator();
		
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
			//ada riwayat krs (pertama) 
		
			String thsms=(String)lih.next();
			last_thsms_after_iter = new String(thsms);
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
			//System.out.println("nlipk2="+nlipk);
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
			
			ListIterator lit = vHistTrlsm.listIterator();
			boolean match = false;
			String status_trlsm = null;
			
			//boolean sms_pendek = Checker.isSmsPendek(thsms);
			//while(lit.hasNext() && !match && !sms_pendek) {
			while(lit.hasNext() && !match) {	
				String brs = (String)lit.next();
				if(brs.startsWith(thsms)) {
					match=true;
					status_trlsm = new String(brs);
					if(!Checker.isStringNullOrEmpty(status_trlsm) && (status_trlsm.contains("`K`")||status_trlsm.contains("`L`")||status_trlsm.contains("`D`")||status_trlsm.contains("`P`"))) {
						sudah_out = true;
						if(status_trlsm.contains("`K`")) {
							tipe_out = "K";	
						}
						else if(status_trlsm.contains("`L`")) {
							tipe_out = "L";
						}
						else if(status_trlsm.contains("`D`")) {
							tipe_out = "D";
						}
						else if(status_trlsm.contains("`P`")) {
							tipe_out = "P";
						}
						
					}
				}
			}
			 
			
			
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
			//System.out.println("nlipk3="+nlipk);
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
			if(false) {
			//if(!cetakKrsMode && !cetakKhsMode && validUsr.isAllowTo("noCalcIp")<1 && (validApprovee==null || (validApprovee!=null && !validApprovee.equalsIgnoreCase("true")))) {
						%>
					<form action="go.calcIndividualIpk">
						
        				<input type="hidden" name="id_obj" value="<%=objId %>" />
        				<input type="hidden" name="nmm" value="<%=nmm %>" />
        				<input type="hidden" name="npm" value="<%=npm %>" />
        				<input type="hidden" name="kdpst" value="<%=kdpst %>" />
        				<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
        				<input type="hidden" name="cmd" value="histKrs" />
        			
						<center><input type="submit" value="KLIK Untuk Menghitung IP/IPK" style="height:50px" /></center>
					</form>
					    <%
			}
			
					    %>
					<br/>
					<table class="table" style="width:95%">
					<!--  table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px" -->
					<%
			String submitButtonValue="Download";
			if(!cetakKrsMode && !cetakKhsMode) {
				// updated ditambaha validasi validApprove = memberikan akses perwalian, jadi vurNpmPa=usr.npm harusnya tidak ada pengaruh lagi
				// updated lagi - cukup valid approvee ajah, krn yg penting filternya pada krs notifikasi, bila ada di krs notifikasi berarti validApprovee
				if(validApprovee!=null && validApprovee.equalsIgnoreCase("true") && prev_thsms.equalsIgnoreCase(thsmsKrs)) {
    				%>
    					<tr>
    		        		<td style="background:#369;color:#fff;text-align:center;font-size:1.75em" colspan="8"><label><B>
    		        		<%
    		   		out.print(Converter.convertThsmsKeterOnly(prev_thsms));
    	        	if(prev_thsms.compareToIgnoreCase(thsms_btstu)==0) {
    			    	out.print("&nbsp&nbsp[BATAS MAKSIMAL LAMA STUDI]");
    			    }
    	        	else if(prev_thsms.compareToIgnoreCase(thsms_btstu)>0) {
    			    	out.print("&nbsp&nbsp[LEWAT BATAS MAKSIMAL LAMA STUDI : "+thsms_btstu+"]");
    			    }
    		        		%></B> </label></td>
    		        	</tr>       	
    		        	<tr>	
    		        		<td style="background:#369;color:#fff;text-align:center" colspan="8">
    		        		Catatan Penolakan / Persetujuan :<br/>
    		        		<form action="go.krsApproval">
    		        		<TEXTAREA rows="4" style="width:98%" name="catatan">
    		        		<%
    		        if(prev_note!=null && !Checker.isStringNullOrEmpty(prev_note)) {
    		        	out.print(prev_note);
    		        }
    		        	%>
    		        		</TEXTAREA>	<br/>
    	    	        	<%
    				if(prev_lockMhs.equalsIgnoreCase("true")) {
    					submitButtonValue = "Reset Approval";
    							%>
    	        					<input type="hidden" name="approval" value="reset" />
    	        				<%
    				}
    				else {
    					submitButtonValue = "Approved";
    							%>
    	        					<input type="hidden" name="approval" value="approved" />
    	        				<%
    				}
    	    	        	%>
							<input type="hidden" name="thsms" value="<%=prev_thsms %>" />
    						<input type="hidden" name="id_obj" value="<%=objId %>" />
    						<input type="hidden" name="nmm" value="<%=nmm %>" />
    						<input type="hidden" name="npm" value="<%=npm %>" />
    						<input type="hidden" name="kdpst" value="<%=kdpst %>" />
    						<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
    						<input type="hidden" name="beritaNotif" value="<%=beritaNotif %>" />
    						<input type="hidden" name="kategoriNotif" value="<%=kstegoriNotif %>" />
    					<%
  	  				boolean lewat = false;
    				if(approvedNotif!=null && approvedNotif.equalsIgnoreCase("false")&&declinedNotif!=null && declinedNotif.equalsIgnoreCase("false")) {	
    					lewat = true;    					
    					%>	
        					<input type="submit" name="statusApproval" value="Reject" style="width:35%;text-align:center;color:red"/>
        				<%
    				}
    				if(beritaNotif!=null && beritaNotif.equalsIgnoreCase("PERMOHONAN BUKA KUNCI")) {	
    					if(unlockDeclinedNotif.equalsIgnoreCase("false")) {
    						lewat = true;
    						%>	
            					<input type="submit" name="statusApproval" value="Reject Buka Kunci" style="width:35%;text-align:center;color:red"/>
            				<%
    					}
    				}
					if(lewat) {
        				%>
        					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        				<%
					}
        			if((approvedNotif!=null && approvedNotif.equalsIgnoreCase("true") && submitButtonValue!=null && submitButtonValue.equalsIgnoreCase("Reset Approval"))||((approvedNotif==null || approvedNotif.equalsIgnoreCase("false"))&&(declinedNotif==null || declinedNotif.equalsIgnoreCase("false")))) {		
        				%>
        					<input type="submit" name="statusApproval" value="<%=submitButtonValue %>" style="width:35%;text-align:center"/>
        				<%
        			}
        					%>
							</td>
        				</tr>
						<%
 		   		}
				else {
					%>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;font-size:1.75em" colspan="8"><label><B>
        				<%
        			String keter_thsms_and_value = Converter.convertThsms(prev_thsms);
        				//System.out.println("keter_thsms_and_value1="+keter_thsms_and_value);
					StringTokenizer stt = new StringTokenizer(keter_thsms_and_value,"#&");
					String keter_thsms = stt.nextToken();
					String value_thsms = stt.nextToken();
        			out.print(keter_thsms); 
        			if(prev_thsms.compareToIgnoreCase(thsms_btstu)==0) {
		         		out.print("&nbsp&nbsp[BATAS MAKSIMAL LAMA STUDI]");
		         	}
        			else if(prev_thsms.compareToIgnoreCase(thsms_btstu)>0) {
		         		out.print("&nbsp&nbsp[LEWAT BATAS MAKSIMAL LAMA STUDI : "+thsms_btstu+"]");
		         	}
        				%></B> </label></td>
        			</tr>
        			<%
    				//==============CEK KALO AJA ADA STATUS BENTROK DGN TRLSM===========================
    				String tmp_status_trlsm = null;
    				String keterangan_status_bentrok=""; 
    				boolean bentrok = false;
    				lit = vHistTrlsm.listIterator();
    				match = false;
    				while(lit.hasNext() && !match) {
						String brs = (String)lit.next();
						if(brs.startsWith(prev_thsms)) {
							match=true;
							tmp_status_trlsm = new String(brs);
							if(tmp_status_trlsm.contains("`K`")||tmp_status_trlsm.contains("`D`")||tmp_status_trlsm.contains("`P`")) {
								keterangan_status_bentrok = "STATUS MHS SUDAH KELUAR TAPI MEMILIKI KRS";
								bentrok = true;
							}
							else if(tmp_status_trlsm.contains("`C`")) {
								keterangan_status_bentrok = "STATUS MHS SEDANG TIDAK AKTIF TAPI MEMILIKI KRS";
								bentrok = true;
							}
						}
					}
					if(bentrok || sudah_out) {
						
						if(sudah_out && !tipe_out.equalsIgnoreCase("L")) {
    		        		keterangan_status_bentrok = "STATUS MHS SUDAH KELUAR TAPI MEMILIKI KRS";
						%>
    				<tr>
    		        	<td style="background:#DBA71C;color:#fff;text-align:center" colspan="8"><label><B>
    		        		<%
    		        	
    		        	
    		        	out.print(keterangan_status_bentrok);	
    		        		%>
    		        		
    		        	</B> </label></td>
    		        </tr>
    		<%		
						}
					}
					//==============END CEK KALO AJA ADA STATUS BENTROK DGN TRLSM===========================
    		%>  
        			<%
				}
        	}
			else {
					//System.out.println("CETAK MODE");
					%>
	        		<tr>
	        			<td style="background:#369;color:#fff;text-align:center;font-size:1.75em" colspan="6"><label><B>
	        			
	        			<%
	        	out.print(Converter.convertThsmsKeterOnly(prev_thsms));
	        	if(prev_thsms.compareToIgnoreCase(thsms_btstu)==0) {
			   		out.print("&nbsp&nbsp[BATAS MAKSIMAL LAMA STUDI]");
			    }
	        	else if(prev_thsms.compareToIgnoreCase(thsms_btstu)>0) {
			        out.print("&nbsp&nbsp[LEWAT BATAS MAKSIMAL LAMA STUDI : "+thsms_btstu+"]");
			    }
	        			%></B> </label></td>
	        			<td style="background:#369;color:#fff;text-align:center" colspan="2">
	        				<%
	        				
	       		if(cetakKrsMode) {
	        				%>
	        				<form action="file.downloadKrs">
	        					<%
	       		}
	       		else {
	       			if(cetakKhsMode) {
	        						%>
	    	        		<form action="file.downloadKhs">
	    	        				<%
	       			}
	       		}
	        					%>
	        					<input type="hidden" name="thsms" value="<%=prev_thsms %>" />
        						<input type="hidden" name="id_obj" value="<%=objId %>" />
        						<input type="hidden" name="nmm" value="<%=nmm %>" />
        						<input type="hidden" name="npm" value="<%=npm %>" />
        						<input type="hidden" name="kdpst" value="<%=kdpst %>" />
        						<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
	        					<input type="submit" value="<%=submitButtonValue %>" style="width:99%;text-align:center"/>
	        				</form>
						</td>
	        		</tr>
	        		<%	
			}
        			%>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;width:70px"><label><B>KODE</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:left;width:390px"><label><B>MATAKULIAH</B> </label></td>
        			<%
        	if(thsmsKrs.equalsIgnoreCase(prev_thsms)) {
        	%>
        				<td colspan="6" style="background:#369;color:#fff;text-align:center;"><label><B>INFO KELAS</B> </label></td>
        	<%	
        	}
        	else {
        			%>	
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>NILAI</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>BOBOT</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:35px"><label><B>SKS</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>KELAS</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:90px"><label><B>SHIFT</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>KODE KAMPUS</B> </label></td>
        	<%
        	}
        	%>			
        			</tr>
        			<tr>
        			
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kdkmk %></B> </label></td>
        				<td style="color:#000;text-align:left;"><label><B>
        				<%=prev_nakmk %></B> </label>
        				<br/><%="Nama Dosen: "+prev_nmmdos %>
        				<%
        	if(thsmsKrs.equalsIgnoreCase(prev_thsms)) {
        	%>
        				<br/><%="Shift : "+prev_shift %>
        				<%
        	}
        	%>	
        				</td>
        				
       		<%
        	if(thsmsKrs.equalsIgnoreCase(prev_thsms)) {
        	%>
        				<td colspan="6" style="color:#000;text-align:left;padding:0 0 0 10px"><label><B>
			<%
				JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/class_pool/info_kelas/"+prev_cuid);
				if(jsoa!=null && jsoa.length()>0) {
					for(int i=0;i<jsoa.length();i++) {
						JSONObject job = jsoa.getJSONObject(i);
						String value = "null";
						try {
							value = (String)job.get("TKN_HARI_TIME");
							value = value.replace("Sn", "Senin ");
							value = value.replace("Sl", "Selasa ");
							value = value.replace("Rb", "Rabu ");
							value = value.replace("Km", "Kamis ");
							value = value.replace("Jm", "Jumat ");
							value = value.replace("Sb", "Sabtu ");
							value = value.replace("Mn", "Minggu ");
							value = value.replace("&#x2f;", " jam: ");
							value = value.replace(" ,", ",");
							out.print("Jadwal : "+value);
						}
						catch(JSONException e) {}//ignore
						
						try {
							value = (String)job.get("KODE_GEDUNG");
							out.print("<br/>Nama Gedung : "+value);
						}
						catch(JSONException e) {}//ignore
						try {
							value = (String)job.get("KODE_RUANG");
							out.print("<br/>No Ruangan : "+value);
						}
						catch(JSONException e) {}//ignore
					}
				}
			
			%>
						</B> </label></td>
        	<%	
        	}
        	else {
        			%>	 				
        				<td style="color:#000;text-align:center;"><label><B><%=prev_nlakh %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_bobot %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_sksmk %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B>
        				<%
    	    	if(Checker.isStringNullOrEmpty(prev_kelas)) {
       				out.print("N/A");
       			}
       			else {
	       			out.print(prev_kelas);
       			}
        				
        				%>
        				
        				</B> </label></td>
        				<%
        		if(default_shift.equalsIgnoreCase(Getter.getKodeKonversiShift(prev_shift))) {
        				%>
        				<td style="color:#000;text-align:center;">
        				<%	
        		}
        		else {
        				%>
        				<td style="color:#A95D00;text-align:center;">
        				<%	
        		}
        				%>
        				<label><B><%=Getter.getKodeKonversiShift(prev_shift) %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kodeKampus %></B> </label></td>
        	<%
        	}
        	%>				
        			</tr>
        			<%
        	//krs kedua dst . . .		
			while(lih.hasNext()) {
				thsms=(String)lih.next();
				//System.out.println("thsms="+thsms);
				last_thsms_after_iter = new String(thsms);
				kdkmk=(String)lih.next();
				nakmk=(String)lih.next();
				nlakh=(String)lih.next();
				bobot=(String)lih.next();
				sksmk=(String)lih.next();
				kelas=(String)lih.next();
				//System.out.println(kelas);
				sksem=(String)lih.next();
				nlips=(String)lih.next();
				skstt=(String)lih.next();
				nlipk=(String)lih.next();
				//System.out.println("nlipk4="+nlipk);
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
					//masih semes yg sama
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
					//System.out.println("nlipk5="+nlipk);
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
        				<td style="color:#000;text-align:left;"><label><B>
        				<%=prev_nakmk %></B> </label>
        				<br/><%="Nama Dosen: "+prev_nmmdos %>
        				<%
        			if(thsmsKrs.equalsIgnoreCase(prev_thsms)) {
        	%>
        				<br/><%="Shift : "+prev_shift %>
        				<%
        			}
        	%>	
        				</td>
        			<%
        			if(thsmsKrs.equalsIgnoreCase(prev_thsms)) {
        	%>
        				<td colspan="6" style="color:#000;text-align:left;padding:0 0 0 10px"><label><B>
        				<%
						JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/class_pool/info_kelas/"+prev_cuid);
						if(jsoa!=null && jsoa.length()>0) {
							for(int i=0;i<jsoa.length();i++) {
								JSONObject job = jsoa.getJSONObject(i);
								String value = "null";
								try {
									value = (String)job.get("TKN_HARI_TIME");
									value = value.replace("Sn", "Senin ");
									value = value.replace("Sl", "Selasa ");
									value = value.replace("Rb", "Rabu ");
									value = value.replace("Km", "Kamis ");
									value = value.replace("Jm", "Jumat ");
									value = value.replace("Sb", "Sabtu ");
									value = value.replace("Mn", "Minggu ");
									value = value.replace("&#x2f;", " jam: ");
									value = value.replace(" ,", ",");
									out.print("Jadwal : "+value);
								}
								catch(JSONException e) {}//ignore
								
								try {
									value = (String)job.get("KODE_GEDUNG");
									out.print("<br/>Nama Gedung : "+value);
								}
								catch(JSONException e) {}//ignore
								try {
									value = (String)job.get("KODE_RUANG");
									out.print("<br/>No Ruangan : "+value);
								}
								catch(JSONException e) {}//ignore
							}
						}	
						
			%>
        				</B> </label></td>
        	<%	
        			}
        			else {
        			%>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_nlakh %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_bobot %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_sksmk %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B>
        				<%
        				if(Checker.isStringNullOrEmpty(prev_kelas)) {
	        				out.print("N/A");
    	    			}
        				else {
        					out.print(prev_kelas);
        				}
        				
        				%></B> </label></td>
        				<%
        				if(default_shift.equalsIgnoreCase(Getter.getKodeKonversiShift(prev_shift))) {
        				%>
        				<td style="color:#000;text-align:center;">
        				<%	
        				}
        				else {
        				%>
        				<td style="color:#A95D00;text-align:center;">
        				<%	
        				}
        				%>
        				<label><B><%=Getter.getKodeKonversiShift(prev_shift) %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kodeKampus %></B> </label></td>
        			<%
        			}
        			%>	
        			</tr>
        			<%
				}
				else {
					//System.out.println("status_trlsm="+status_trlsm);
					//System.out.println("thsms_krs="+thsmsKrs);
					if(!prev_thsms.equalsIgnoreCase(thsms)) {
						//ganti tahun
						//1. tutup table sebelumnya dengan ngasih nilai ipk dan ips
						//cek bila ada status di smsm ini
						/*
						UPD 28 Nov 2017
						*/
						lit = vHistTrlsm.listIterator();
						match = false;
						status_trlsm = null;
			
						//boolean sms_pendek = Checker.isSmsPendek(thsms);
						//while(lit.hasNext() && !match && !sms_pendek) {
						while(lit.hasNext() && !match) {	
							String brs = (String)lit.next();
							if(brs.startsWith(prev_thsms)) {
								match=true;
								status_trlsm = new String(brs);
								if(!Checker.isStringNullOrEmpty(status_trlsm) && (status_trlsm.contains("`K`")||status_trlsm.contains("`L`")||status_trlsm.contains("`D`")||status_trlsm.contains("`P`"))) {
									sudah_out = true;
									if(status_trlsm.contains("`K`")) {
										tipe_out = "K";	
									}
									else if(status_trlsm.contains("`L`")) {
										tipe_out = "L";
									}
									else if(status_trlsm.contains("`D`")) {
										tipe_out = "D";
									}
									else if(status_trlsm.contains("`P`")) {
										tipe_out = "P";
									}
						
								}
							}
						}
						
						
						//System.out.println("prev_thsms="+prev_thsms);
						if(status_trlsm!=null && status_trlsm.contains("`L`")) {
							sudah_out = true;
							
						
					%>
					<tr>
						<td style="text-align:center;font-weight:bold;background:#A0A0A0;font-size:1.5em" colspan="8">
						<label><B>L U L U S  /  W I S U D A</B></label>
						</td>
					</tr>
					<%		
						}
						//System.out.println("sudah_out="+sudah_out);
	        		%>
					<tr>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="6"><label><B>SKS & IP SEMESTER</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=prev_sksem %></B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=NumberFormater.return2digit(prev_nlips) %></B> </label></td>
        			</tr>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="6"><label><B>SKS & IP KOMULATIF</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=prev_skstt %></B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=NumberFormater.return2digit(prev_nlipk) %></B> </label></td>
        			</tr>
        		</table>
        		<br />	
					<%	
						status_trlsm = null;
						//String target_next_thsms = Tool.returnNextThsmsGiven_v1(prev_thsms, kdpst);
						String target_next_thsms = Tool.returnNextThsmsGiven_v1(prev_thsms, kdpst);
						//System.out.println("target_next_thsms="+target_next_thsms);
						//System.out.println("prev_thsms="+prev_thsms);
						//boolean sms_pendek_1 = Checker.isSmsPendek(target_next_thsms);
						if(!thsms.equalsIgnoreCase(target_next_thsms) && !Checker.isSmsPendek(thsms)) {
							while(!thsms.equalsIgnoreCase(target_next_thsms)) {
															
								lit = vHistTrlsm.listIterator();
								match = false;
								status_trlsm = null;
								while(lit.hasNext() && !match) {
									String brs = (String)lit.next();
									if(brs.startsWith(target_next_thsms)) {
										match=true;
										status_trlsm = new String(brs);
										if(!Checker.isStringNullOrEmpty(status_trlsm) && (status_trlsm.contains("`K`")||status_trlsm.contains("`L`")||status_trlsm.contains("`D`")||status_trlsm.contains("`P`"))) {
											sudah_out = true;
											if(status_trlsm.contains("`K`")) {
												tipe_out = "K";	
											}
											else if(status_trlsm.contains("`L`")) {
												tipe_out = "L";
											}
											else if(status_trlsm.contains("`D`")) {
												tipe_out = "D";
											}
											else if(status_trlsm.contains("`P`")) {
												tipe_out = "P";
											}
										}
									}
								}
								
								/*
								kreate table disini 
								*/
				%>
				<table class="table" style="width:95%">
				<!--  table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px"-->
        			<tr>
	    		     	<td style="background:#369;color:#fff;text-align:center;font-size:1.75em" colspan="8"><label><B>
	    		     	<%
	    		     			out.print(Converter.convertThsmsKeterOnly(target_next_thsms));
	        					if(target_next_thsms.compareToIgnoreCase(thsms_btstu)==0) {
			         				out.print("&nbsp&nbsp[BATAS MAKSIMAL LAMA STUDI]");
			         			}
	        					else if(target_next_thsms.compareToIgnoreCase(thsms_btstu)>0) {
			         				out.print("&nbsp&nbsp[LEWAT BATAS MAKSIMAL LAMA STUDI : "+thsms_btstu+"]");
			         			}
	    		     	%></B> </label></td>
	    		    </tr>
	    		    <tr>
	    		     	<td style="text-align:center;font-weight:bold;background:#A0A0A0;font-size:1.5em" colspan="8"><label><B>
	    		     	<%
	    		     			if(!Checker.isStringNullOrEmpty(status_trlsm)) {
				    		     	StringTokenizer st = new StringTokenizer(status_trlsm,"`");
		    				     	String thsmstrlsm = st.nextToken();
	    					     	String stmhstrlsm = st.nextToken();
	    			     			String notetrlsm = st.nextToken();
			    			     	if(stmhstrlsm.equalsIgnoreCase("C")) {
	    					     		out.print("CUTI AKADEMIK");
	    					     	}
	    		     				else if(stmhstrlsm.equalsIgnoreCase("K")) {
			    		     			out.print("KELUAR");
			    		     			sudah_out = true;
	    				     		}
									else if(stmhstrlsm.equalsIgnoreCase("D")) {
										out.print("D.O.");
										sudah_out = true;
				    		     	}
									else if(stmhstrlsm.equalsIgnoreCase("A")) {
										out.print("AKTIF [DATA KRS TIDAK TERCATAT]");
									}
									else if(stmhstrlsm.equalsIgnoreCase("N") || Checker.isStringNullOrEmpty(stmhstrlsm)) {
										out.print("NON AKTIF");
									}
									else if(stmhstrlsm.equalsIgnoreCase("P")) {
										out.print("PINDAH PRODI");
										sudah_out = true;
									}
									else if(stmhstrlsm.equalsIgnoreCase("L")) {
										out.print("L U L U S  /  W I S U D A [DATA KRS TIDAK TERCATAT]");
										sudah_out = true;
									}
	    		    		 	}
	    		     		else {
		    		     		out.print("NON AKTIF");
		    		     	}
						
	    		     	%></B> </label></td>
	    		    </tr>
	    		    
	    		</table> 
	    		<br>   
				<%				
								//cek next
								target_next_thsms = Tool.returnNextThsmsGiven_v1(target_next_thsms, kdpst);
							}
						}
						else {
							lit = vHistTrlsm.listIterator();
							match = false;
							status_trlsm = null;
							//boolean sms_pendek_2 = Checker.isSmsPendek(target_next_thsms);
							while(lit.hasNext() && !match) {
								String brs = (String)lit.next();
								if(brs.startsWith(target_next_thsms)) {
									match=true;
									status_trlsm = new String(brs);
									if(!Checker.isStringNullOrEmpty(status_trlsm) && (status_trlsm.contains("`K`")||status_trlsm.contains("`L`")||status_trlsm.contains("`D`")||status_trlsm.contains("`P`"))) {
										sudah_out = true;
										if(status_trlsm.contains("`K`")) {
											tipe_out = "K";	
										}
										else if(status_trlsm.contains("`L`")) {
											tipe_out = "L";
										}
										else if(status_trlsm.contains("`D`")) {
											tipe_out = "D";
										}
										else if(status_trlsm.contains("`P`")) {
											tipe_out = "P";
										}
									}
								}
							}
						}
					
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
						//System.out.println("nlipk6="+nlipk);
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
				<table class="table" style="width:95%">	
				<!--  table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px"-->
        			<%
						if(!cetakKrsMode && !cetakKhsMode) {
						//if(paApprover && prev_lock.equalsIgnoreCase("false") && (curNpmPa.equalsIgnoreCase(validUsr.getNpm())||(validApprovee!=null && validApprovee.equalsIgnoreCase("true"))) && prev_thsms.equalsIgnoreCase(thsmsKrs)) { //mode view krs { //mode view krs
							if(validApprovee!=null && validApprovee.equalsIgnoreCase("true") && prev_thsms.equalsIgnoreCase(thsmsKrs)) {	
	    				%>
	    			<tr>
	    		     	<td style="background:#369;color:#fff;text-align:center;font-size:1.75em" colspan="8"><label><B>
	    		     	<%
	    				     	out.print(Converter.convertThsmsKeterOnly(prev_thsms));
	        					if(prev_thsms.compareToIgnoreCase(thsms_btstu)==0) {
			       			  		out.print("&nbsp&nbsp[BATAS MAKSIMAL LAMA STUDI]");
			         			}
	        					else if(prev_thsms.compareToIgnoreCase(thsms_btstu)>0) {
			         				out.print("&nbsp&nbsp[LEWAT BATAS MAKSIMAL LAMA STUDI : "+thsms_btstu+"]");
			         			}
	    		     	//Converter.convertThsmsKeterOnly(prev_thsms) 
	    		     	%></B> </label></td>
	    		    </tr>
    		        <tr>
    		        	<td style="background:#369;color:#fff;text-align:center" colspan="8">
    		        	Catatan Penolakan / Persetujuan :<br/>
    		        	<form action="go.krsApproval">
    		        	<TEXTAREA rows="4" style="width:98%" name="catatan">
    		        	
    		        	<%
    		        	//out.print(noteNotif);
	    		      			if(prev_note!=null && !Checker.isStringNullOrEmpty(prev_note)) {
    			        			out.print(prev_note);
    		        		
    			        		}	
    		        	%>
    		        	</TEXTAREA>	<br/>
    	    	        	<%
    							if(prev_lockMhs.equalsIgnoreCase("true")) {
    								submitButtonValue = "Reset Approval";
    							%>
    	        					<input type="hidden" name="approval" value="reset" />
    	        				<%
    							}
    							else {
    								submitButtonValue = "Approved";
    								%>
    	        					<input type="hidden" name="approval" value="approved" />
    	        				<%
	    						}
    	    	        	%>
						<input type="hidden" name="thsms" value="<%=prev_thsms %>" />
    					<input type="hidden" name="id_obj" value="<%=objId %>" />
    					<input type="hidden" name="nmm" value="<%=nmm %>" />
    					<input type="hidden" name="npm" value="<%=npm %>" />
    					<input type="hidden" name="kdpst" value="<%=kdpst %>" />
    					<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
    					<input type="hidden" name="beritaNotif" value="<%=beritaNotif %>" />
    					<input type="hidden" name="kategoriNotif" value="<%=kstegoriNotif %>" />
        				<%
    							boolean lewat = false;
        					//System.out.println("approvedNotif="+approvedNotif);
        					//if(approvedNotif.equalsIgnoreCase("false")&&declinedNotif.equalsIgnoreCase("false")) {
        						if((approvedNotif==null || approvedNotif.equalsIgnoreCase("false"))&&(declinedNotif==null || declinedNotif.equalsIgnoreCase("false"))) {	
    								lewat = true;    					
    					%>	
        					<input type="submit" name="statusApproval" value="Reject" style="width:35%;text-align:center;color:red"/>
        				<%
    							}
        						if(beritaNotif!=null && beritaNotif.equalsIgnoreCase("PERMOHONAN BUKA KUNCI")) {
    								if(unlockDeclinedNotif.equalsIgnoreCase("false")) {
    									lewat = true;
    						%>	
            					<input type="submit" name="statusApproval" value="Reject Buka Kunci" style="width:35%;text-align:center;color:red"/>
            				<%
    								}
    							}
								if(lewat) {
        				%>
        					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        				<%
								}
        					//if((approvedNotif.equalsIgnoreCase("true") && submitButtonValue.equalsIgnoreCase("Reset Approval"))||(approvedNotif.equalsIgnoreCase("false"))&&declinedNotif.equalsIgnoreCase("false")) {
        						if((approvedNotif!=null && approvedNotif.equalsIgnoreCase("true") && submitButtonValue!=null && submitButtonValue.equalsIgnoreCase("Reset Approval"))||((approvedNotif==null || approvedNotif.equalsIgnoreCase("false"))&&(declinedNotif==null || declinedNotif.equalsIgnoreCase("false")))) {	
        					
        				%>
        					<input type="submit" name="statusApproval" value="<%=submitButtonValue %>" style="width:35%;text-align:center"/>
        				<%
        						}
        				%>
					</td>
	       		</tr>
					<%
	    					}
							else {
					%>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;font-size:1.75em" colspan="8"><label><B>
						<%
	        					String keter_thsms_and_value = Converter.convertThsms(prev_thsms);
        				//System.out.println("keter_thsms_and_value2="+keter_thsms_and_value);
								StringTokenizer stt = new StringTokenizer(keter_thsms_and_value,"#&");
								String keter_thsms = stt.nextToken();
								String value_thsms = stt.nextToken();
        						out.print(keter_thsms); 
        						if(prev_thsms.compareToIgnoreCase(thsms_btstu)==0) {
        			         		out.print("&nbsp&nbsp[BATAS MAKSIMAL LAMA STUDI]");
        			         	}
        						else if(prev_thsms.compareToIgnoreCase(thsms_btstu)>0) {
        			         		out.print("&nbsp&nbsp[LEWAT BATAS MAKSIMAL LAMA STUDI : "+thsms_btstu+"]");
        			         	}
        				%>
						</B> </label></td>
					</tr>
        			<%
    				//==============CEK KALO AJA ADA STATUS BENTROK DGN TRLSM===========================
    							String tmp_status_trlsm = null;
    							String keterangan_status_bentrok=""; 
    							boolean bentrok = false;
    							lit = vHistTrlsm.listIterator();
    							match = false;
    							//boolean sms_pendek_2 = Checker.isSmsPendek(prev_thsms);
								while(lit.hasNext() && !match) {
									String brs = (String)lit.next();
						//System.out.println(brs+" vs "+prev_thsms);
									if(brs.startsWith(prev_thsms)) {
										match=true;
										tmp_status_trlsm = new String(brs);
										if(!Checker.isStringNullOrEmpty(tmp_status_trlsm) && (tmp_status_trlsm.contains("`K`")||tmp_status_trlsm.contains("`D`")||tmp_status_trlsm.contains("`P`"))) {
											bentrok = true;
											keterangan_status_bentrok = "STATUS MHS SUDAH KELUAR TAPI MEMILIKI KRS";
										}
										else if(tmp_status_trlsm.contains("`C`")) {
											//Status N tidak diikutsertakan
											bentrok = true;
											keterangan_status_bentrok = "STATUS MHS SEDANG TIDAK AKTIF TAPI MEMILIKI KRS";
										}
									}
								}
								if(bentrok || sudah_out) {
									if(sudah_out && !tipe_out.equalsIgnoreCase("L")) {
		        						keterangan_status_bentrok = "STATUS MHS SUDAH KELUAR TAPI MEMILIKI KRS";
		        	
						%>
    				<tr>
    		        	<td style="background:#DBA71C;color:#fff;text-align:center" colspan="8"><label><B>
    		        		<%
    		        		
			    		        		out.print(keterangan_status_bentrok);	
    		        		%>
    		        		
    		        	</B> </label></td>
    		        </tr>
    		<%	
									}
								}
					//==============END CEK KALO AJA ADA STATUS BENTROK DGN TRLSM===========================
    		 
							}
	        			}
						else {
						//System.out.println("pint 3");
					
					%>
	        		<tr>
	        			<td style="background:#369;color:#fff;text-align:center;font-size:1.75em" colspan="6"><label><B>
	        			<%
		        			out.print(Converter.convertThsmsKeterOnly(prev_thsms));
		        			if(prev_thsms.compareToIgnoreCase(thsms_btstu)==0) {
				         		out.print("&nbsp&nbsp[BATAS MAKSIMAL LAMA STUDI]");
				         	}
	        				else if(prev_thsms.compareToIgnoreCase(thsms_btstu)>0) {
			        	 		out.print("&nbsp&nbsp[LEWAT BATAS MAKSIMAL LAMA STUDI : "+thsms_btstu+"]");
			         		}
		        			//Converter.convertThsmsKeterOnly(prev_thsms) 
	        			%></B> </label></td>
	        			<td style="background:#369;color:#fff;text-align:center" colspan="2">
	        			<%
	    	   				submitButtonValue = "Download";
	       					if(cetakKrsMode) {
	        				%>
	        				<form action="file.downloadKrs">
	        					<%
	       					}
	        				else {
	        					if(cetakKhsMode) {
	        						//System.out.println("in 2");
	        						%>
	    	        		<form action="file.downloadKhs">
	    	        				<%
	        					}
	        				}
	        			
	        				    //System.out.println("jsp vHistTmp Size 2 ="+vHistTmp.size());
	        				    
	        				    %>
        						<input type="hidden" name="thsms" value="<%=prev_thsms %>" />
        						<input type="hidden" name="id_obj" value="<%=objId %>" />
        						<input type="hidden" name="nmm" value="<%=nmm %>" />
        						<input type="hidden" name="npm" value="<%=npm %>" />
        						<input type="hidden" name="kdpst" value="<%=kdpst %>" />
        						<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
	        					<input type="submit" value="<%=submitButtonValue %>" style="width:99%;text-align:center"/>
	        				</form>
						</td>
	        		</tr>
	        		<%	
						}
        			%>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;width:70px"><label><B>KODE</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:left;width:390px"><label><B>MATAKULIAH</B> </label></td>
        				<%
        				if(thsmsKrs.equalsIgnoreCase(prev_thsms)) {
        	%>
        				<td colspan="6" style="background:#369;color:#fff;text-align:center;"><label><B>INFO KELAS</B> </label></td>
        	<%	
        				}
        				else {
        			%>
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>NILAI</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>BOBOT</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:35px"><label><B>SKS</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>KELAS</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:90px"><label><B>SHIFT</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>KODE KAMPUS</B> </label></td>
        			<%
        				}
        			%>	
        			</tr>
        			<tr>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kdkmk %></B> </label></td>
        				<td style="color:#000;text-align:left;"><label><B>
						<%=prev_nakmk %></B> </label>
        				<br/><%="Nama Dosen: "+prev_nmmdos %>
        				<%
        				if(thsmsKrs.equalsIgnoreCase(prev_thsms)) {
        	%>
        				<br/><%="Shift : "+prev_shift %>
        	<%
        				}
        	%>			
        				</td>
        		<%
        				if(thsmsKrs.equalsIgnoreCase(prev_thsms)) {
        	%>
        				<td colspan="6" style="color:#000;text-align:left;padding:0 0 0 10px"><label><B>
        				<%
        				
        				
							JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/class_pool/info_kelas/"+prev_cuid);
							if(jsoa!=null && jsoa.length()>0) {
								for(int i=0;i<jsoa.length();i++) {
									JSONObject job = jsoa.getJSONObject(i);
									String value = "null";
									try {
										value = (String)job.get("TKN_HARI_TIME");
										value = value.replace("Sn", "Senin ");
										value = value.replace("Sl", "Selasa ");
										value = value.replace("Rb", "Rabu ");
										value = value.replace("Km", "Kamis ");
										value = value.replace("Jm", "Jumat ");
										value = value.replace("Sb", "Sabtu ");
										value = value.replace("Mn", "Minggu ");
										value = value.replace("&#x2f;", " jam: ");
										value = value.replace(" ,", ",");
										out.print("Jadwal : "+value);
										
									}
									catch(JSONException e) {}//ignore
									
									try {
										value = (String)job.get("KODE_GEDUNG");
										out.print("<br/>Nama Gedung : "+value);
									}
									catch(JSONException e) {}//ignore
									try {
										value = (String)job.get("KODE_RUANG");
										out.print("<br/>No Ruangan : "+value);
									}
									catch(JSONException e) {}//ignore
								}
							}	
							
			%>
        				</B> </label></td>
        	<%	
        				}
        				else {
        			%>		
        				<td style="color:#000;text-align:center;"><label><B><%=prev_nlakh %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_bobot %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_sksmk %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B>
						<%
	        				if(Checker.isStringNullOrEmpty(prev_kelas)) {
    	    					out.print("N/A");
        					}
        					else {
        						out.print(prev_kelas);
        					}
        				
        				%>
						</B> </label></td>
						<%
        					if(default_shift.equalsIgnoreCase(Getter.getKodeKonversiShift(prev_shift))) {
        				%>
        				<td style="color:#000;text-align:center;">
        				<%	
        					}
        					else {
        				%>
        				<td style="color:#A95D00;text-align:center;">
        				<%	
        					}
        				%>
						<label><B><%=Getter.getKodeKonversiShift(prev_shift) %></B> </label></td>
						<td style="color:#000;text-align:center;"><label><B><%=prev_kodeKampus %></B> </label></td>
				<%
						}
				%>		
        			</tr>
        		<% 	
					}
				}
			}//end while
			//System.out.println(status_trlsm+"-"+status_trlsm);
			//System.out.println(thsmsKrs+"-"+thsmsKrs);
					/*
					*tutup table setelah end while
					*/
			lit = vHistTrlsm.listIterator();
			match = false;
			status_trlsm = null;
		
					//boolean sms_pendek = Checker.isSmsPendek(thsms);
					//while(lit.hasNext() && !match && !sms_pendek) {
			while(lit.hasNext() && !match) {	
				String brs = (String)lit.next();
				if(brs.startsWith(prev_thsms)) {
					match=true;
					status_trlsm = new String(brs);
					if(!Checker.isStringNullOrEmpty(status_trlsm) && (status_trlsm.contains("`K`")||status_trlsm.contains("`L`")||status_trlsm.contains("`D`")||status_trlsm.contains("`P`"))) {
						sudah_out = true;
						if(status_trlsm.contains("`K`")) {
							tipe_out = "K";	
						}
						else if(status_trlsm.contains("`L`")) {
							tipe_out = "L";
						}
						else if(status_trlsm.contains("`D`")) {
							tipe_out = "D";
						}
						else if(status_trlsm.contains("`P`")) {
							tipe_out = "P";
						}
					
					}
				}
			}		
			if(status_trlsm!=null && status_trlsm.contains("`L`")) {
				sudah_out = true;
				
			
						%>
					<tr>
						<td style="text-align:center;font-weight:bold;background:#A0A0A0;font-size:1.5em" colspan="8">
						<label><B>L U L U S  /  W I S U D A</B></label>
						</td>
					</tr>
						<%		
			}
	        		%>	
					<tr>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="6"><label><B>SKS & IP SEMESTER</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=prev_sksem %></B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=NumberFormater.return2digit(prev_nlips) %></B> </label></td>
        			</tr>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="6"><label><B>SKS & IP KOMULATIF</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=prev_skstt %></B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=NumberFormater.return2digit(prev_nlipk) %></B> </label></td>
        			</tr>
        		</table>
        		<br>
        		<%
        		/*
        		PENUTUPAN bila riwayat trlsm > riwayat krs
        		*/
        	//String target_next_thsms = Tool.returnNextThsmsGiven_v1(prev_thsms, kdpst);
        		
        	lit = vHistTrlsm.listIterator();
        	boolean stop = false;
        	
			while(lit.hasNext() && !sudah_out && !stop) {
				String brs = (String)lit.next();
				//System.out.println("brs1="+brs);
				StringTokenizer st = new StringTokenizer(brs,"`");
				String thsmstrlsm = st.nextToken();
				String stmhstrlsm = st.nextToken();
				if(!Checker.isStringNullOrEmpty(status_trlsm) && (status_trlsm.contains("`K`")||status_trlsm.contains("`L`")||status_trlsm.contains("`D`")||status_trlsm.contains("`P`"))) {
					sudah_out = true;
					if(status_trlsm.contains("`K`")) {
						tipe_out = "K";	
					}
					else if(status_trlsm.contains("`L`")) {
						tipe_out = "L";
					}
					else if(status_trlsm.contains("`D`")) {
						tipe_out = "D";
					}
					else if(status_trlsm.contains("`P`")) {
						tipe_out = "P";
					}
				}
				String notetrlsm = st.nextToken();
				
				if(Converter.thsmsForCompare(thsmstrlsm).compareToIgnoreCase(Converter.thsmsForCompare(prev_thsms))>0 && Converter.thsmsForCompare(thsmstrlsm).compareToIgnoreCase(thsms_btstu)<=0) {
				//if(thsmstrlsm.compareToIgnoreCase(prev_thsms)>0 && thsmstrlsm.compareToIgnoreCase(thsms_btstu)<=0) {
						%>
				<table class="table" style="width:95%">		
				<!--  table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px" -->
		        	<tr>
			         	<td style="background:#369;color:#fff;text-align:center;font-size:1.75em" colspan="8"><label><B>
			         	<%
			         	out.print(Converter.convertThsmsKeterOnly(thsmstrlsm));
			         	if(thsmstrlsm.compareToIgnoreCase(thsms_btstu)==0) {
			         		stop = true;
			         		out.print("&nbsp&nbsp[BATAS MAKSIMAL LAMA STUDI]");
			         	}
			         	else if(thsmstrlsm.compareToIgnoreCase(thsms_btstu)>0) {
			         		out.print("&nbsp&nbsp[LEWAT BATAS MAKSIMAL LAMA STUDI : "+thsms_btstu+"]");
			         	}
			         	%>
			         	</B> </label></td>
			        </tr>
			        <tr>
			    	    <td style="text-align:center;font-weight:bold;background:#A0A0A0;font-size:1.5em" colspan="8"><label><B>
			    		     	<%
			    	//st = new StringTokenizer(status_trlsm,"`");
			    	//String thsmstrlsm = st.nextToken();
			    	//String stmhstrlsm = st.nextToken();
			    	//String notetrlsm = st.nextToken();
			    	if(stmhstrlsm.equalsIgnoreCase("C")) {
			    		out.print("CUTI AKADEMIK");
			    	}
			    	else if(stmhstrlsm.equalsIgnoreCase("K")) {
    		     		out.print("KELUAR");
    		     		sudah_out = true;
    		     	}
					else if(stmhstrlsm.equalsIgnoreCase("D")) {
						out.print("D.O.");
						sudah_out = true;
    		     	}
					else if(stmhstrlsm.equalsIgnoreCase("A")) {
						out.print("AKTIF [DATA KRS TIDAK TERCATAT]");
					}
					else if(stmhstrlsm.equalsIgnoreCase("N") || Checker.isStringNullOrEmpty(stmhstrlsm)) {
						out.print("NON AKTIF");
					}
					else if(stmhstrlsm.equalsIgnoreCase("P")) {
						out.print("PINDAH PRODI");
						sudah_out = true;
					}
					else if(stmhstrlsm.equalsIgnoreCase("L")) {
						out.print("L U L U S  /  W I S U D A [DATA KRS TIDAK TERCATAT]");
						sudah_out = true;
					}
								
			    		     	%></B> </label></td>
			   		</tr>
			    </table> 
			    <br>   
				<%	
				}
				
			}	
        		
		}
	}
	else if(vHistKrsKhs!=null && vHistKrsKhs.size()>0) {			
		ListIterator lit = vHistTrlsm.listIterator();
		boolean stop = false;
		while(lit.hasNext() && !sudah_out && !stop) {
			String brs = (String)lit.next();
			//System.out.println("brs2="+brs);
			StringTokenizer st = new StringTokenizer(brs,"`");
			String thsmstrlsm = st.nextToken();
			String stmhstrlsm = st.nextToken();
			//if(stmhstrlsm.contains("`K`")||stmhstrlsm.contains("`L`")||stmhstrlsm.contains("`D`")||stmhstrlsm.contains("`P`")) {
			//	sudah_out = true;
			//}
			String notetrlsm = st.nextToken();

					%>
			<table class="table" style="width:95%">		
			<!--  table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px"-->
	        	<tr>
		         	<td style="background:#369;color:#fff;text-align:center;font-size:1.75em" colspan="8"><label><B>
		         	<%
		 	out.print(Converter.convertThsmsKeterOnly(thsmstrlsm));
		    if(thsmstrlsm.compareToIgnoreCase(thsms_btstu)==0) {
		    	stop = true;
		    	out.print("&nbsp&nbsp[BATAS MAKSIMAL LAMA STUDI]");
		  	}
		    else if(thsmstrlsm.compareToIgnoreCase(thsms_btstu)>0) {
		    	out.print("&nbsp&nbsp[LEWAT BATAS MAKSIMAL LAMA STUDI : "+thsms_btstu+"]");
		    }
		         	%></B> </label></td>
		        </tr>
		        <tr>
		    	    <td style="text-align:center;font-weight:bold;background:#A0A0A0;font-size:1.5em" colspan="8"><label><B>
		    		     	<%
		    if(stmhstrlsm.equalsIgnoreCase("C")) {
		    	out.print("CUTI AKADEMIK");
		    }
		    else if(stmhstrlsm.equalsIgnoreCase("K")) {
		    	out.print("KELUAR");
		    	sudah_out = true;
		    }
			else if(stmhstrlsm.equalsIgnoreCase("D")) {
				out.print("D.O.");
				sudah_out = true;
		    }
			else if(stmhstrlsm.equalsIgnoreCase("A")) {
				out.print("AKTIF [DATA KRS TIDAK TERCATAT]");
			}
			else if(stmhstrlsm.equalsIgnoreCase("N") || Checker.isStringNullOrEmpty(stmhstrlsm)) {
				out.print("NON AKTIF");
			}
			else if(stmhstrlsm.equalsIgnoreCase("P")) {
				out.print("PINDAH PRODI");
				sudah_out = true;
			}
			else if(stmhstrlsm.equalsIgnoreCase("L")) {
				out.print("L U L U S  /  W I S U D A [DATA KRS TIDAK TERCATAT]");
				sudah_out = true;
			}
							
		    		     	%></B> </label></td>
		   		</tr>
		    </table> 
		    <br>   
			<%	
			
		}					
	}
	else {
					
				%>
				<h1 style="text-align:center">BELUM ADA REKORD KRS/KHS</h1>
				<div style="text-align:center;font-style:italic">Kami dalam tahap migrasi, harap "kirim pesan" ke tata usaha fakultas bila data riwayat studi tidak sesuai agar dapat mendapat prioritas update</div>
				<%
	}
      
    /*
    UPDATED 27 Nov 2017
    */
    //if(vHistTrlsm!=null && vHistTrlsm.size()>0) {	
    if(false) {	
    	//sudah_out = false; //reset untuk proses ini aja biar ngga nyampur ma diatas
		ListIterator lit = vHistTrlsm.listIterator();
		while(lit.hasNext() && !sudah_out) {
			String brs = (String)lit.next();
			//System.out.println("brs3="+brs);
			//System.out.println("barisan="+brs);
			StringTokenizer st = new StringTokenizer(brs,"`");
			String thsmstrlsm = st.nextToken();
			String stmhstrlsm = st.nextToken();
			if(brs.contains("`K`")||brs.contains("`L`")||brs.contains("`D`")||brs.contains("`P`")) {
				thsms_out = new String(thsmstrlsm);
				tipe_out = new String(stmhstrlsm);
				sudah_out = true;
			}
			
			String notetrlsm = st.nextToken();
			if(thsmstrlsm.compareToIgnoreCase(last_thsms_after_iter)>0) {
				if(!sudah_out || (sudah_out && !stmhstrlsm.equalsIgnoreCase("N"))) {
				
					%>
			<table class="table" style="width:95%">
			<!--  table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px" -->
	        	<tr>
		         	<td style="background:#369;color:#fff;text-align:center;font-size:1.75em" colspan="8"><label><B>
		         	<%
		         	out.print(Converter.convertThsmsKeterOnly(thsmstrlsm));
		         	if(thsmstrlsm.compareToIgnoreCase(thsms_btstu)==0) {
		         		
		         		out.print("&nbsp&nbsp[BATAS MAKSIMAL LAMA STUDI]");
		         	}
		         	else if(thsmstrlsm.compareToIgnoreCase(thsms_btstu)>0) {
		         		out.print("&nbsp&nbsp[LEWAT BATAS MAKSIMAL LAMA STUDI : "+thsms_btstu+"]");
		         	}
		         	%></B> </label></td>
		        </tr>
		        <tr>
		    	    <td style="text-align:center;font-weight:bold;background:#A0A0A0;font-size:1.5em" colspan="8"><label><B>
		    		     	<%
			    	if(stmhstrlsm.equalsIgnoreCase("C")) {
			    		out.print("CUTI AKADEMIK");
			    	}
		    		else if(stmhstrlsm.equalsIgnoreCase("K")) {
		     			out.print("KELUAR");
		     			sudah_out = true;
			     	}
					else if(stmhstrlsm.equalsIgnoreCase("D")) {
						out.print("D.O.");
						sudah_out = true;
		    	 	}
					else if(stmhstrlsm.equalsIgnoreCase("A")) {
						out.print("AKTIF [DATA KRS TIDAK TERCATAT]");
					}
					else if(stmhstrlsm.equalsIgnoreCase("N") || Checker.isStringNullOrEmpty(stmhstrlsm)) {
						out.print("NON AKTIF");
					}
					else if(stmhstrlsm.equalsIgnoreCase("P")) {
						out.print("PINDAH PRODI");
						sudah_out = true;
					}
					else if(stmhstrlsm.equalsIgnoreCase("L")) {
						out.print("L U L U S  /  W I S U D A [DATA KRS TIDAK TERCATAT]");
						sudah_out = true;
					}
							
		    		     	%></B> </label></td>
		   		</tr>
		    </table> 
		    <br>   
<%
				}
			}
		}//end while
    }	
        			
}//end if(size>0)
else {
				%>
				<h2 align="center"><b>Anda Tidak Mempunyai Hak Akses Untuk Data ini</b></h2>
				<% 
}
		//creta force back to
		//String forceTo = "get.histKrs?id_obj="+v_id_obj+"&nmm="+v_nmmhs+"&npm="+v_npmhs+"&obj_lvl="+v_obj_lvl+"&kdpst="+v_kdpst+"&cmd=histKrs";
		//session.removeAttribute("forceBackTo");
		//session ngga diapus krn hubungan dengan force to krs approval
		%>
		<!-- Column 1 end -->
		</div>
	</div>
</div>	

</body>
</html>
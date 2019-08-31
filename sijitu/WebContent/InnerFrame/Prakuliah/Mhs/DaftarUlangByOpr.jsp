<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.dbase.daftarUlang.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= null; 
String id_obj = request.getParameter("id_obj");
String no_button = request.getParameter("no_button");
boolean hide_button = false;
/*
PRECAUTIPON AJA TAKUTNYA SESSION VALUENYA BELUM SEMPET KE UPDATE
*/
if(no_button!=null && no_button.equalsIgnoreCase("true")) {
	hide_button = true;
}
//System.out.println("hide_button="+hide_button);
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String obj_lvl= request.getParameter("obj_lvl");
String kdpst=request.getParameter("kdpst");
String cmd=request.getParameter("cmd");
String kmp = Getter.getDomisiliKampus(npm);
String thsms_heregistrasi = Checker.getThsmsHeregistrasi();
SearchDbInfoDaftarUlangTable sdd = new SearchDbInfoDaftarUlangTable(validUsr.getNpm());
String backTo = request.getParameter("backTo");

//String verdict = sdd.sudahMengajukanHeregistrasi(thsms_heregistrasi, npm);


boolean sdu = false;//sam @ home.jsp
boolean smdu = false;
boolean show_sdu = false;
if(validUsr.iAmStu()) {
	sdu = Checker.sudahDaftarUlang(session);
	smdu = Checker.sudahMengajukanDaftarUlang(session);
	show_sdu = Checker.showNotificasiDaftarUlang(session);
}



%>
<script type="text/javascript">
</script>
</head>
<body>
<div id="header">
<jsp:include page="innerMenuDaftarUlang.jsp" flush="true" />
<!--   include file="innerMenuDaftarUlang.jsp" % -->
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />

		<!-- Column 1 start -->
<%
if(validUsr.isUsrAllowTo_updated("hasPengajuanMhsMenu", npm)) {	
	if(!validUsr.isHakAksesReadOnly("hasPengajuanMhsMenu")) {
%>
<form action="proses.daftarUlangByOpr" method="post">
<%
	}
%>

	<input  type="hidden" name="npmhs" value="<%=npm %>" />
	<input  type="hidden" name="kdpst" value="<%=kdpst %>" />
	<input  type="hidden" name="id_obj" value="<%=id_obj %>" />
	<input  type="hidden" name="nmmhs" value="<%=nmm %>" />
	<input  type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
	<input  type="hidden" name="target_thsms" value="<%=thsms_heregistrasi %>" />


	
<section class="gradient">

<% 
	if(!validUsr.iAmStu()) {
		String msg = Checker.sudahDaftarUlang(kdpst, npm, thsms_heregistrasi);
		session.setAttribute("sdu", msg);
		sdu = Checker.sudahDaftarUlang(session);
		smdu = Checker.sudahMengajukanDaftarUlang(session);
		show_sdu = Checker.showNotificasiDaftarUlang(session);
	}
	
	if(smdu) {
		if(sdu) {
			
			out.print("<div style=\"text-align:left;font-size:1.5em;font-weight:bold;padding:0 0 0 50px\">Proses Pendaftaran Ulang Telah Selesai, "+nmm.toUpperCase()+" Telah Terdaftar Sebagai Mahasiswa Aktif "+thsms_heregistrasi+"</div>");
		//cek status pengajuan
			validUsr.hideNotifikasiDaftarUlang();
			String msg = Checker.sudahDaftarUlang(kdpst, npm, thsms_heregistrasi); //overide session value
			session.setAttribute("sdu", msg);
		}
		else {
			String psn = "";
			
			
			Vector v_status = sdd.cekStatusPengajuan(thsms_heregistrasi, npm, kdpst, kmp);
			ListIterator li = v_status.listIterator();
			if(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				if(li.hasNext()) {
					//psn = "<div style=\"text-align:left;font-weight:bold;font-size:1.2em;padding:0 0 0 50px\">Proses pengajuan pendaftaran ulang "+nmm.toUpperCase()+" sedang dalam proses dan akan divalidasi oleh "+st.nextToken()+"</div><div style=\"text-align:left;font-weight:italic;font-size:1em;padding:0 0 0 50px\">Status validasi:</div>";
					psn = "<div style=\"text-align:left;font-weight:bold;font-size:1.5em;padding:0 0 0 50px\">Status validasi proses pendaftaran ulang:</div>";
					while(li.hasNext()) {
						String brs1 = (String)li.next();
						if(brs1.equalsIgnoreCase("done")) {
							psn = "<div style=\"text-align:left;font-size:1.5em;font-weight:bold;padding:0 0 0 50px\">Proses Pendaftaran Ulang Telah Selesai, "+nmm.toUpperCase()+" Telah Terdaftar Sebagai Mahasiswa Aktif "+thsms_heregistrasi+"</div>";
						}
						else {
							st = new StringTokenizer(brs1,"`");
							if(st.countTokens()==1) {
								psn = psn+"<div style=\"text-align:left;font-weight:bold;font-size:1em;padding:0 0 0 90px\">"+st.nextToken()+" - Telah Diterima & Disetujui</div>";
							}
							else if(st.countTokens()==2) {
								psn = psn+"<div style=\"text-align:left;font-weight:bold;font-size:1em;padding:0 0 0 90px\">"+st.nextToken()+" - Dalam Proses Validasi</div>";
								//psn = psn+st.nextToken()+" - Dalam Proses Validasi<br/>";
							}
						}
					
					}
				}
				else {
				//belum ada yg approved
					StringTokenizer st5 = new StringTokenizer(brs,"`");
					psn = "<div style=\"text-align:left;font-weight:bold;font-size:1.2em;padding:0 0 0 50px\">Proses Pendaftaran Ulang "+nmm.toUpperCase()+" Sedang Dalam Proses Menunggu Validasi dari "+st5.nextToken()+"</div>";
				}
			
			}
			String updtm = (String)session.getAttribute("registrasi_updtm");
			if(updtm!=null && !Checker.isStringNullOrEmpty(updtm)) {
				out.print("<div style=\"text-align:left;font-weight:bold;font-size:1.2em;padding:0 0 0 50px\">Tanggal Pengajuan : "+updtm+"</div>");	
			}
			out.print(psn);
			//karena sudah mengajukan maka update status notificasi hidden
			if(validUsr.iAmStu()) {
				validUsr.hideNotifikasiDaftarUlang();
				String msg = Checker.sudahDaftarUlang(kdpst, npm, thsms_heregistrasi); //overide session value
				session.setAttribute("sdu", msg);
			}
		}
	}
	else {
		//belum mengajukan jadinya keluar prosedur
	%>
<div style="text-align:center">
	<h1 style="text-align:left;Padding:0 0 0 50px"><u>PROSEDUR PENGAJUAN DAFTAR ULANG / HEREGISTRASI <%=thsms_heregistrasi %></u></h1><br/>
	<div style="text-align:left;Padding:0 0 0 50px;font-size:1.2em">
	<b>
	<P>1. HARAP MELAKUKAN PENDAFTARAN ULANG SECARA ONLINE, DENGAN CARA: Klik,  TOMBOL "Ajukan Pendaftaran Ulang"</P>
	<p>2. PENGAJUAN ONLINE (Melakukan Klik Tombol "Ajukan Pendaftaran Ulang) HARUS DILAKUKAN PALING LAMBAT 1 (SATU) MINGGU SEBELUM PERKULIAHAN DIMULAI, ATAU DIKENAKAN DENDA ADMINISTRATIF (MULAI DIBERLAKUKAN 2016/GENAP)</p><br/>
	</b>
	<p>PROSES SELANJUTNYA:<br/>
	1. Hubungi TATA USAHA (TU) untuk melakukan pengisian KRS dan Validasi KTU<br/>
	2. Pengambilan slip pembayaran pada Biro Keuangan dan lakukan pembayaran <br/>
	3. Menunggu Validasi Biro Keuangan </p>
	</div>
</div>
	<%	
	}
	if(!smdu && !hide_button && !validUsr.isHakAksesReadOnly("hasPengajuanMhsMenu")) {
		if(!validUsr.isHakAksesReadOnly("hasPengajuanMhsMenu")) {
%>
<div style="text-align:center">
	<button style="padding: 5px 50px;font-size: 20px;">Ajukan Pendaftaran Ulang</button>
</div>
</section>
</form>
<%
		}
	}
	else {
		if(!validUsr.iAmStu() && !smdu) {
%>
<h1 style="text-align:left;Padding:0 0 0 50px">PENGAJUAN HARUS DIAJUKAN OLEH MAHASISWA TERKAIT</h1>
<%		
		}
	}
}	
else {
	
}



%>
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>
<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />	
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.classPoll.*" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.sistem.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%
////System.out.println("yap1");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= null;
//Vector vThsmsStmhs = (Vector)session.getAttribute("vThsmsStmhs");
//String tkn_stmhs_kode =  Getter.getListStmhs();

String id_obj= request.getParameter("id_obj");
String nmm= request.getParameter("nmm");
String npm= request.getParameter("npm");
String obj_lvl= request.getParameter("obj_lvl");
String scope= request.getParameter("scope");
String table_nm= request.getParameter("table");
if(table_nm==null) {
	table_nm = new String((String)session.getAttribute("nama_table"));
}
String type_pengajuan = table_nm.replace("_RULES", "");
String title_pengajuan = type_pengajuan.replace("_", " ");
String folder_pengajuan = (String)session.getAttribute("folder_pengajuan");
String kdpst= request.getParameter("kdpst");
String target_thsms = Checker.getThsmsPengajuanStmhs();
String cmd= request.getParameter("cmd");
String msg= request.getParameter("msg");
String at_menu = request.getParameter("atMenu");
String status_pengajuan = (String)session.getAttribute("status_pengajuan_"+title_pengajuan+"_"+npm); //di set di dash_pp_baru.jsp
Vector v_list_prodi = Getter.getListProdi();
String kdkmp_domisili = Getter.getDomisiliKampus(Integer.parseInt(id_obj));
boolean sdh_ada_rules = Checker.isTableRuleSdhDiisi(target_thsms, kdpst, table_nm, kdkmp_domisili);
////System.out.println("status_pengajuan="+status_pengajuan);
//session.removeAttribute("status_pengajuan_"+npm); // harus di home utk removenya
%>

</head>
<body>
<div id="header">
<ul>
	<li>
		<a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/indexPengajuanMhs.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=reqMhs" target="_self">BACK <span>&nbsp</span></a>
	</li>
<%
if(at_menu.equalsIgnoreCase("form")) {
		%>	
	<li>
		<a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/Pengajuan/<%=folder_pengajuan%>/form_pp.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&table=<%=table_nm %>&scope=<%=scope %>&atMenu=form" class="active" target="_self">FORM PENGAJUAN<span><%=title_pengajuan %></span></a>
	</li>
		<%			
}
else {
%>	
	<li>
		<a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/Pengajuan/<%=folder_pengajuan%>/form_pp.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&table=<%=table_nm %>&scope=<%=scope %>&atMenu=form" target="_self">FORM PENGAJUAN<span><%=title_pengajuan %></span></a>
	</li>
	<%		
}

%>
</ul>	
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<%
		//System.out.println("nm status_pengajuan ="+status_pengajuan);
		%>
		<!-- Column 1 start -->
		
		<%
	if(!sdh_ada_rules) {
			%>
			<h1 style="text-align:center;font-weight:bold">TABEL PERSETUJUAN BELUM DIISI,<br>
			Harap hubungi Tata Usaha Fakultas.
			</h1>
		<%	
	}
	else {	
		if(msg!=null && msg.equalsIgnoreCase("upd")) {
		%>
			<div align="right" style="font-size:0.5em">UPDATED : <%=AskSystem.getDateTime() %></div>
		<%
		}
		%>
		
		
		<%
		if(status_pengajuan==null || Checker.isStringNullOrEmpty(status_pengajuan) ||(status_pengajuan!=null && status_pengajuan.equalsIgnoreCase("ditolak")) || (status_pengajuan!=null && status_pengajuan.equalsIgnoreCase("batal"))) {
			if(status_pengajuan!=null) {
				if(status_pengajuan.equalsIgnoreCase("ditolak")) {
				%>
				<div style="text-align:center;font-size:1.5em">
				Pengajuan <%=title_pengajuan %> anda untuk tahun/sms <%=target_thsms %> ditolak, silahkan ajukan kembali setelah melengkapi persyaratan<br/>
				</div>
				<br/>
				<br/>
				<%	
				}
			}	
		%>
		
		<form onsubmit="button_submit.disabled = true; return true;" action="go.ajukanPp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&target_thsms=<%=target_thsms %>&scope=<%=scope %>&table=<%=table_nm %>&folder_pengajuan=<%=folder_pengajuan%>" method="post">
		<div style="width:90%">
		Dengan ini saya, <%=nmm %>, mengajukan permohonan untuk <%=title_pengajuan %> dengan alasan sbb :<br/>
		<br/><textarea name="alasan" style="width:88%" rows="4" required></textarea><br/><br/>
		dan telah memenuhi kewajiban / persyaratan yang telah ditentukan.
		<br/>
		<br/>
		
			<section class="gradient">
				<div style="text-align:center">
				<button style="padding: 5px 50px;font-size: 20px;">Ajukan Permohonan</button>
				</div>
			</section>
			
				
			
		</div>
		
			
        </form>
        <%
        }
		else if(status_pengajuan!=null && status_pengajuan.equalsIgnoreCase("inprogress")) {
		%>
		<div style="text-align:center;font-size:1.5em">
		Pengajuan <%=title_pengajuan %> anda untuk tahun/sms <%=target_thsms %> sedang dalam proses persetujuan, <br/>
		Apakah anda mau membatalkan pengajuan <%=title_pengajuan %> anda?<br/><br/>
		<form action="go.batalPengajuan?target_thsms=<%=target_thsms %>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&scope=<%=scope %>&table=<%=table_nm %>&folder_pengajuan=<%=folder_pengajuan%>" method="post" >
		<br/>
		<section class="gradient">
				<button style="padding: 5px 50px;font-size: 20px;">Klik untuk Batalkan Pengajuan</button>
		</section>
			<!--  div style="text-align:center">
				<input type="submit" value="Klik untuk Batalkan Pengajuan" style="color:red;height:35px" name="button_submit" />
			</div-->
		</form>
		
		</div>
		<%	
		}
		else if(status_pengajuan!=null && status_pengajuan.equalsIgnoreCase("disetujui")) {
		%>
		<div style="text-align:center;font-size:1.5em">
		Pengajuan <%=title_pengajuan %> anda untuk tahun/sms <%=target_thsms %> sudah diterima dan disetujui
		</div>
		<%			
		}
	}	
        %>
		<!-- Column 1 end -->
		
	</div>
</div>

</body>
</html>
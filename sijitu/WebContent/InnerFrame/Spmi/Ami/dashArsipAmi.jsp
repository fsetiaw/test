<!DOCTYPE html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.spmi.*" %>
<%@ page import="java.io.File" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/folderIcon/folderStyleVer2.css" media="screen" />
<!--  link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/folderIcon/folderStyle.css" media="screen" / -->
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"> </script>	
<script type="text/javascript">
//setTimeout(popup, 3000); // Setting time 3s to popup login form
function popup() {
	$("#logindiv").css("display", "inline");
}

$("#onclick").click(function() {
	$("#contactdiv").css("display", "inline");
});
	
$(document).ready(function() {
//	setTimeout(popup, 3000);
	function popup() {
		$("#logindiv").css("display", "inline");
	}
	$("#login #cancel").click(function() {
		$(this).parent().parent().hide();
	});
	
	
	// Login form popup login-button click event.
	$("#loginbtn").click(function() {
		var nameFolder = $("#username").val();
	//var password = $("#password").val();
	//if (username == "" || password == ""){
		if (nameFolder == ""){
			alert("Nama Folder Kosong atau Sudah Digunakan");
		}
		else{
			//$("#logindiv").css("display", "inline");
			$.post('file.createNuFolder', $('#login').serialize(), function(data) {

			});
		}
	});
});	
</script>

<style>
a.img:hover {
	text-decoration: none;
	background:none;
}

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
.table:hover td { background:#82B0C3 }
</style>
<style>
.table1 {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
}
.table1 thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table1 thead > tr > th, .table1 tbody > tr > t-->h, .table1 tfoot > tr > th, .table1 thead > tr > td, .table1 tbody > tr > td, .table1 tfoot > tr > td { border: 1px solid #2980B9; }

.table1-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table1-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table1-noborder thead > tr > th, .table1-noborder tbody > tr > th, .table1-noborder tfoot > tr > th, .table1-noborder thead > tr > td, .table1-noborder tbody > tr > td, .table1-noborder tfoot > tr > td { border: none;padding: 2px }

</style>
<%
	//System.out.println("mkan");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	ToolSpmi ts = new ToolSpmi();
	
	String kdpst_nmpst_kmp = (String)session.getAttribute("current_kdpst_nmpst_kmp");
	kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
	StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
	String kdpst = st.nextToken();
	String nmpst = st.nextToken();
	String kdkmp = st.nextToken();
	String id_ami = request.getParameter("id_ami");
	if(Checker.isStringNullOrEmpty(id_ami)) {
		id_ami = (String)session.getAttribute("id_ami");;
	}	
	String kode_activity = request.getParameter("kode_activity");
	if(Checker.isStringNullOrEmpty(kode_activity)) {
		kode_activity = (String)session.getAttribute("kode_activity");;
	}
	String tgl_plan = request.getParameter("tgl_plan");
	if(Checker.isStringNullOrEmpty(tgl_plan)) {
		tgl_plan = (String)session.getAttribute("tgl_plan");;
	}
	String ketua_tim = request.getParameter("ketua_tim");
	if(Checker.isStringNullOrEmpty(ketua_tim)) {
		ketua_tim = (String)session.getAttribute("ketua_tim");;
	}
	String anggota_tim = request.getParameter("anggota_tim");
	if(Checker.isStringNullOrEmpty(anggota_tim)) {
		anggota_tim = (String)session.getAttribute("anggota_tim");;
	}
	String id_cakupan_std = request.getParameter("id_cakupan_std");
	if(Checker.isStringNullOrEmpty(id_cakupan_std)) {
		id_cakupan_std = (String)session.getAttribute("id_cakupan_std");;
	}
	String ket_cakupan_std = request.getParameter("ket_cakupan_std");
	if(Checker.isStringNullOrEmpty(ket_cakupan_std)) {
		ket_cakupan_std = (String)session.getAttribute("ket_cakupan_std");;
	}
	String tgl_ril = request.getParameter("tgl_ril");
	if(Checker.isStringNullOrEmpty(tgl_ril)) {
		tgl_ril = (String)session.getAttribute("tgl_ril");;
	}
	String tgl_ril_done = request.getParameter("tgl_ril_done");
	if(Checker.isStringNullOrEmpty(tgl_ril_done)) {
		tgl_ril_done = (String)session.getAttribute("tgl_ril_done");;
	}
	String id_master_std = request.getParameter("id_master_std");
	if(Checker.isStringNullOrEmpty(id_master_std)) {
		id_master_std = (String)session.getAttribute("id_master_std");;
	}
	String ket_master_std = request.getParameter("ket_master_std");
	if(Checker.isStringNullOrEmpty(ket_master_std)) {
		ket_master_std = (String)session.getAttribute("ket_master_std");;
	}
	
	session.removeAttribute("root_ami_folder");
	Vector v_nm_alm_access=new Vector();
	ListIterator li=v_nm_alm_access.listIterator();
	String root_standar_ami_folder=ts.getRootStandarAmiFolder();
	root_standar_ami_folder = root_standar_ami_folder+"/"+Converter.getNamaKdpstDanJenjang(kdpst)+"/"+kode_activity+"_["+id_ami+"]"+tgl_plan;
	//li.add("Folder Utama`/home/usg/spmi/1/evaluasi/standar/kegiatan ami/HUKUM [S-1]/ami dua_2018-08-28`reid");
	li.add("Folder Utama`"+root_standar_ami_folder+"`reid");
	session.setAttribute("root_ami_folder", root_standar_ami_folder);
	//ts.createFolderStructureManualUmum(id_versi,"evaluasi");
	
	//ts.createFolderRiwayatKegiatanUmum(id_std, "evaluasi");
	
%>

</head>
<body>
<div id="header">
<jsp:include page="menu_back_ami.jsp" />
</div>

<!--  div class="colmask fullpage">
	<div class="col1">
		<div id="mainform">
<h2>jQuery Popup Form Example</h2>
<!-- Required Div Starts Here 
<div class="form" id="popup">
<b>1.Onload Popup Login Form</b><br/><hr/>
<span>Wait for 3 second.Login Popup form Will appears.</span><br/><br/><br/>
<b>2.Onclick Popup Contact Form</b><hr/>
<p id="onclick">Popup</p>
</div 
</div-->
<!-- Contact Form -->

<!--Login Form -->

		
		
		<br />


<br/>		
<table style="table-layout: fixed;" width="100%"  cellpadding="10">
	<tr>
		<!-- Column 1 start -->
<%
//if(listFileFolder!=null && listFileFolder.length>0) {
if(v_nm_alm_access!=null && v_nm_alm_access.size()>0) {		
	int j = 0;
	li = v_nm_alm_access.listIterator();
	for(int i=0;i<v_nm_alm_access.size();i++) {
		
		String brs = (String)li.next();
		//System.out.println(brs);
		st = new StringTokenizer(brs,"`");
		String keter_folder = st.nextToken();
		String alamat_folder = st.nextToken();
		//String nick_and_hak = st.nextToken();
		String hak_akses = st.nextToken();
	//only show directory	
		//if (listFileFolder[i].isDirectory()) {
		if(true) {	
			j++;
			if(j%4==0) {
%>
	</tr>		
	<tr>
<%				
			}
            //out.print("directory:"+listFileFolder[i].getName()+"<br/>");
%>
	
	<td width="200px" style="word-wrap: break-word">
<%
			session.removeAttribute("folder_asal");
			session.setAttribute("folder_asal", alamat_folder);
			
%>
    	<a href="get.folderContentAmi?at_menu=folder&root_dir=<%=alamat_folder %>&keter=<%=keter_folder %>&alm=<%=alamat_folder %>&hak=<%=hak_akses %>&kdpst=74201" class="docFolder"></a><div style="text-align:left"><%= keter_folder%></div>
	</td>
<%		    
        } 
		else {
        	j++;
			if(j%4==0) {
%>
	</tr>
	<tr>
<%				
			}        
%>
		<td width="200px" style="word-wrap: break-word">
    		<a href="#" class="docIcon"></a><div style="text-align:left"><%= "none"%></div>
    	</td>
<%
       }
	}
}
else {
%>
	<td style="text-align:center;font-size:1.5em">
	Belum Ada Riwayat Manual <br> Silahkan buat manual / draft manual terlebih dahulu
	</td>
<%		
}
%>		
	</tr>
</table>
	</div>
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>
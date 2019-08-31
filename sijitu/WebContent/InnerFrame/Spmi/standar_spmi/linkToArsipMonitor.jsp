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

//System.out.println("siap");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
ToolSpmi ts = new ToolSpmi();
SearchStandarMutu ssm = new SearchStandarMutu();
String id_tipe_std=request.getParameter("id_tipe_std");
//System.out.println("id_tipe_std="+id_tipe_std);
String id_master_std=request.getParameter("id_master_std");
//System.out.println("id_master_std="+id_master_std);
String kdpst = request.getParameter("kdpst");
//System.out.println("kdpst="+kdpst);
String id_std = request.getParameter("id_std");
//System.out.println("id_std="+id_std);
String id_std_isi = request.getParameter("id_std_isi");
//System.out.println("id_std_isi="+id_std_isi);
String id_versi = request.getParameter("id_versi");
//System.out.println("id_versi="+id_versi);
String ppepp = "pelaksanaan";
String tgl_eval = request.getParameter("tgl_eval");
while(tgl_eval.contains("~")) {
	tgl_eval = tgl_eval.replace("~", "-");
}
String isi_std = ssm.getPernyataanIsiStd(id_std_isi);
isi_std = isi_std.trim();

//String folder_asal1 = (String) session.getAttribute("folder_asal1");
//System.out.println("folder_asal1="+folder_asal1);
String nm_prodi = Converter.getNamaKdpstDanJenjang(kdpst);
//System.out.println("nm_prodi="+nm_prodi);
String info_std = ToolSpmi.getNmMasterDanNamaStandar(Integer.parseInt(id_std_isi));
//System.out.println("info_std="+info_std);
StringTokenizer stt = new StringTokenizer(info_std,"~");
String nm_rumpun_std = stt.nextToken();
String nm_standar = stt.nextToken();
String root_standar_spmi_folder = ts.getRootStandarSpmiFolder(id_versi, ppepp);
//String target_folder=Converter.autoConvertDateFormat(tgl_sta_hist, "-")+"_"+nama_kegiatan_hist;
String main_dir = root_standar_spmi_folder.trim()+"/monitoring/"+nm_prodi.trim()+"/"+nm_rumpun_std.trim()+"/"+nm_standar.trim()+"/"+isi_std.substring(0, 200)+"  . . .  "+isi_std.substring(isi_std.length()-43, isi_std.length()).trim()+"/"+tgl_eval.trim();
while(main_dir.contains("//")) {
	main_dir = main_dir.replace("//", "/");
}
//System.out.println("main_dir=="+main_dir);
ts.createFolderRiwayatKegiatanMonitoring(id_std, id_versi, kdpst);

Vector v_nm_alm_access=null;
ListIterator li=null;
if(!Checker.isStringNullOrEmpty(id_versi)) {
	ts.createFolderStructureManualUmum(id_versi,ppepp);
	ts.createFolderStructureStandarUmum(id_versi,ppepp);
	//File[] listFileFolder = (File[])session.getAttribute("listFileFolder");
	v_nm_alm_access = new Vector();
	li = v_nm_alm_access.listIterator();
	
	li.add("Folder Utama`"+main_dir.trim()+"`reid");
	//System.out.println("oke aja ="+main_dir);
	//li.add("Folder Utama`"+ts.getRootSpmiFolder()+"`reid");
	ts.createFolderRiwayatKegiatanUmum(id_versi, ppepp);
}
%>

</head>
<body>
<div id="header">

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
<div id="logindiv">
<form class="form" action="#" id="login" name="login"  method="post">
	<img src="<%=Constants.getRootWeb() %>/css/folderIcon/Cancel3.png" class="img" id="cancel"/>
	<label>Buat Folder Baru </label><br/>
	<input type="text" id="username" name="foldername" placeholder="Nama Folder Baru"/>
	<input type="hidden" name="currentFolder" value="/home/cg2/doc_spmi/manual_eval/<%=kdpst%>"/>
	<input type="button" id="loginbtn" name="loginbtn" value="Buat Folder"/>

</form>
</div>
		
		
		<br />
<%
if(false) {
%>		
<div id="mainform">
	<a href="javascript:popup()"><img style="border:0;" src="<%=Constants.getRootWeb() %>/css/folderIcon/Grey_Add_Folder.png" alt="Folder Baru" ></a>
</div>	
<%
}
%>	

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
		StringTokenizer st = new StringTokenizer(brs,"`");
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
			session.removeAttribute("folder_asal1");
			session.setAttribute("folder_asal", alamat_folder);
			session.setAttribute("hak_akses_asal", hak_akses);	
			
%>
    	<a href="get.folderContentSpmi?root_dir=<%=alamat_folder.trim() %>&keter=<%=keter_folder.trim() %>&alm=<%=alamat_folder.trim() %>&hak=<%=hak_akses.trim() %>&kdpst=<%=kdpst.trim() %>&id_tipe_std=<%=id_tipe_std.trim() %>&id_master_std=<%=id_master_std.trim() %>&id_std=<%=id_std.trim() %>&id_versi=<%=id_versi.trim() %>" class="docFolder"></a><div style="text-align:left"><%= keter_folder%></div>
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
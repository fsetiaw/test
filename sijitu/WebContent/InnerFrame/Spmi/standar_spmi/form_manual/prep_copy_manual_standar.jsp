<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.spmi.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.SearchStandarMutu"%>
<%@page import="beans.dbase.spmi.manual.*"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
//-------------------------------------------------
String ua=request.getHeader("User-Agent").toLowerCase();
boolean mobile=false;
if(ua.matches("(?i).*((android|bb\\d+|meego).+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\\.(browser|link)|vodafone|wap|windows ce|xda|xiino).*")||ua.substring(0,4).matches("(?i)1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\\-|your|zeto|zte\\-")) {
  //response.sendRedirect("http://detectmobilebrowser.com/mobile");
  //return;	
  mobile = true;
  
}
//--------------------------------------------

/*
SETTING SEARCHIN LIMIT DISINI  
*/
//System.out.println("dasbaord_std_manual_perencanaan");
String src_manual_limit="6";
session.setAttribute("src_manual_limit",src_manual_limit); 
boolean ada_manual_yg_bisa_diedit = false;
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String[]visi_misi_tujuan_nilai = Getter.getVisiMisiTujuanNilaiPt(Constants.getKdpti());

Boolean spmi_editor = (Boolean) session.getAttribute("spmi_editor");// asalnya !isu.isHakAksesReadOnly("hasSpmiMenu");
Boolean team_spmi = (Boolean) session.getAttribute("team_spmi");//asal jabatan ada mutu
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");

kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
String id_tipe_std = request.getParameter("id_tipe_std");
String id_master_std = request.getParameter("id_master_std");
String id_std = request.getParameter("id_std");
String at_menu_dash = request.getParameter("at_menu_dash");
String std_kdpst = "null";
String scope_std = "null";
String versi_standar = null;
boolean editor = spmi_editor.booleanValue();
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);
String unit_used=null;
ToolSpmi ts = new ToolSpmi();
String darimana = request.getParameter("darimana");

SearchManualMutu smm = new SearchManualMutu();
//smm.autoInsertManual(Integer.parseInt(id_versi),Integer.parseInt(id_std_isi), "perencanaan");
%>
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
/*.table:hover td { background:#82B0C3 }*/
</style>
<style>
.table0 {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
table-layout: fixed;
}
.table0 thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;
font-weight:bold;
word-wrap:break-word;
}
.table0 thead > tr > th, .table0 tbody > tr > t-->h, .table0 tfoot > tr > th, .table0 thead > tr > td, .table0 tbody > tr > td, .table0 tfoot > tr > td { border: 1px solid #2980B9;word-wrap:break-word; }

.table0-noborder { border: none;background:<%=Constant.lightColorBlu()%>;word-wrap:break-word; }
.table0-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold;word-wrap:break-word; }
.table0-noborder thead > tr > th, .table0-noborder tbody > tr > th, .table0-noborder tfoot > tr > th, .table0-noborder thead > tr > td, .table0-noborder tbody > tr > td, .table0-noborder tfoot > tr > td { border: none;padding: 2px;word-wrap:break-word; }

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
<style>
.table2 {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
}
.table2 thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table2 thead > tr > th, .table2 tbody > tr > t-->h, .table2 tfoot > tr > th, .table2 thead > tr > td, .table2 tbody > tr > td, .table2 tfoot > tr > td { border: 1px solid #2980B9; }

.table2-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table2-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table2-noborder thead > tr > th, .table2-noborder tbody > tr > th, .table2-noborder tfoot > tr > th, .table2-noborder thead > tr > td, .table2-noborder tbody > tr > td, .table2-noborder tfoot > tr > td { border: none;padding: 2px }
.table2 tr:hover td { background:#82B0C3 }
</style>
<script>
	$(document).ready(function() {
		$("#wait").hide();
		$("#main").show();
	});	
</script>
<script type="text/javascript">
$(document).ready(function(){
	
	$('table.CityTable th') .parents('table.CityTable') .children('tbody') .toggle();
	
	
	
	
	$('table.CityTable th') .click(
		function() {
			$(this) .parents('table.CityTable') .children('tbody') .toggle();
		}
	)
	$('table.StateTable tr.statetablerow th') .click(
		function() {
			$(this) .parents('table.StateTable') .children('tbody') .toggle();
		}
	)
	
	$('table.StateTable tr.statetablerow td') .click(
		function() {
			$(this) .parents('table.StateTable') .children('tbody') .toggle();
		}
	)
	
	
	
});
</script>
<style type="text/css">
	table.CityTable, table.StateTable{width:100%; border-color:#1C79C6; text-align:center;}
	table.StateTable{margin:0px; border:1px solid #fff;;text-align:center;}
	
	table td{padding:0px;}
	table.StateTable thead th{background: #369; padding: 0px; cursor:pointer; color:white;text-align:center;}
	table.CityTable thead th{padding: 0px; background: #C7DBF1;cursor:pointer; color:black;}
	table.CityTable thead th.blue{padding: 0px; background: #369;cursor:pointer; color:black;padding:5px 0 5px 5px;text-align:left}
	table.CityTable thead:hover th.blue { background:#82B0C3;;text-align:left;padding:5px 0 5px 5px; }
	
	table.StateTable td.nopad{background: #369;padding:0;;text-align:center;}
	table.StateTable th.yellow{background: #FE9A2E;padding:0;;text-align:center;}
	table.StateTable tr:hover td.nopad { background:#82B0C3 }
	table.StateTable thead tr:hover td { background:#82B0C3;;text-align:center; }
	table.StateTable tr:hover th.yellow { background:#FFB766;;text-align:center; }
	
</style>
<script>
	$(document).ready(function() {
		$("#form1").hide();
		$("#upload").hide();
		$("#chart").show();
		
		document.getElementById('chart').style.display='block';
		document.links[6].className = 'active';
		document.links[7].className = '';
		document.links[8].className = '';
		document.getElementById('form1').style.display='none';
		document.getElementById('upload').style.display='none';
	});	
</script>

<style type="text/css">
	table.CityTable, table.StateTable{width:100%; border-color:#1C79C6; text-align:center;}
	table.StateTable{margin:0px; border:1px solid #369;}
	
	table td{padding:0px;}
	table.StateTable thead th{background: #369; padding: 0px; cursor:pointer; color:white;}
	table.CityTable thead th{padding: 0px; background: #C7DBF1;cursor:pointer; color:black;}
	
	table.StateTable td.nopad{padding:0;}
</style>
<script>		
	$(document).ready(function() {
		$("#wait").hide();
		$("#main").show();
	});	
	
	function checkAll() {
	// Get the checkbox
		var checkBox = document.getElementById("all_laki");
		// Get the output text
		//var text = document.getElementById("text");

		// If the checkbox is checked, display the output text
		if (checkBox.checked == true){
			document.getElementById("gender_Male1").checked = true;
			document.getElementById("gender_Male2").checked = true;
			document.getElementById("gender_Male3").checked = true;
			document.getElementById("gender_Male4").checked = true;
		} 
		else {
		    //text.style.display = "none";
			document.getElementById("gender_Male1").checked = false;
			document.getElementById("gender_Male2").checked = false;
			document.getElementById("gender_Male3").checked = false;
			document.getElementById("gender_Male4").checked = false;
		}
	}
	
	function toggleSubCheckbox() {
		// Get the checkbox
		//var checkBox = document.getElementById("all_laki");
		// Get the output text
		//var text = document.getElementById("text");

		// If the checkbox is checked, display the output text
		if (gender_Male1.checked == false||gender_Male2.checked == false){
			document.getElementById("all_laki").checked = false;
		} 
		
	}
</script>
</head>
<%
if(!Checker.isStringNullOrEmpty(darimana)&&darimana.equalsIgnoreCase("riwayat")) {
	%>
<body onload="document.getElementById('chart').style.display='none';document.getElementById('form1').style.display='block';document.links[7].className = 'active';document.links[6].className = '';document.links[8].className = '';document.getElementById('upload').style.display='none';">
<%
}
else {
%>
<body onload="document.getElementById('chart').style.display='block';document.getElementById('form1').style.display='none';document.links[7].className = '';document.links[6].className = 'active';document.links[8].className = '';document.getElementById('upload').style.display='none';">
<%
}
%>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="tunggu ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai<br>[Lama proses bisa sampai 5 menit]</p>
</div>
<div id="main">
<jsp:include page="menu_back2manual.jsp" />
<div class="colmask fullpage">
	<div class="col1">

		<!-- Column 1 start -->
		siap
		<input type="checkbox" name="all_laki" id="all_laki" value="all_laki" onclick="checkAll()">LAKI1<br>
		<input type="checkbox" name="gender" id="gender_Male1" value="Male1" onclick="toggleSubCheckbox()">LAKI1<br>
		<input type="checkbox" name="gender" id="gender_Male2" value="Male2" onclick="toggleSubCheckbox()">LAKI2<br>
		<input type="checkbox" name="gender" id="gender_Male3" value="Male3" onclick="toggleSubCheckbox()">LAKI3<br>
		<input type="checkbox" name="gender" id="gender_Male4" value="Male4" onclick="toggleSubCheckbox()">LAKI4<br>
		<input type="checkbox" name="gender" id="gender_Male5" value="Male5" onclick="toggleSubCheckbox()">LAKI5<br>
		<input type="checkbox" name="gender" id="gender_Female" value="Female" >cewek
		<!-- Column 1 start -->
	</div>
</div>	
</div>	
</body>
</html>
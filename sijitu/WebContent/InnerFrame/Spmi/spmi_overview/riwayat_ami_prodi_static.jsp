<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.notification.*" %>
<%@ page import="beans.dbase.chitchat.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.overview.*" %>
<%@ page import="beans.dbase.spmi.*" %>
<%
//System.out.println("yap1");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
//Converter.getDetailKdpst_v1(kdpst)
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);
Vector v_riwayat_ami_prodi=(Vector)session.getAttribute("v_riwayat_ami_prodi");
session.removeAttribute("v_riwayat_ami_prodi");
Boolean spmi_editor = (Boolean) session.getAttribute("spmi_editor");
/*
MASUK KE pAGE INI VECOR > 0
*/




//SearchStandarMutu stm = new SearchStandarMutu(validUsr.getNpm());
//Vector v_list_main_std = stm.getListInfoStandar();
%>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/css/metro.css" rel="stylesheet">
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>

<style>
a.img:hover {
	text-decoration: none;
	background:none;
}

.table {
border: 1px solid #fff;
background:<%=Constant.lightColorBlu()%>;
}
.table thead > tr > th {
border-bottom: 1px solid #fff;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #fff; }

.table-noborder { border: 1px solid #fff;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: 1px solid #fff;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: 1px solid #fff;padding: 2px }
.table tr:hover td { background:#82B0C3 }
/*.table:hover td { background:#82B0C3 }*/
</style>
<style>
.table1 {
border: 1px solid #fff;
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
font-weight:bold;
}
.table2 thead > tr > th, .table2 tbody > tr > t-->h, .table2 tfoot > tr > th, .table2 thead > tr > td, .table2 tbody > tr > td, 
.table2 tfoot > tr > td 
{ 
	border: 1px solid #2980B9;
	text-align:center;
	font-size:1.5em;
	font-weight:bold; 
	padding: 3px 3px;
	color: #369;
}

.table2-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table2-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table2-noborder thead > tr > th, .table2-noborder tbody > tr > th, .table2-noborder tfoot > tr > th, .table2-noborder thead > tr > td, .table2-noborder tbody > tr > td, .table2-noborder tfoot > tr > td { border: none;padding: 2px }
.table2 th:hover { background:#82B0C3 }
</style>
<script>
		
		$(document).ready(function() {
			$("#wait").hide();
			$("#main").show();
		});	
	</script>
	<script>	
	
	$(document).ready(function() {
		
		$(document).on("click", "#prepDashOverviewSpmi", function() {
        	$.ajax({
        		url: 'go.prepDashOverviewSpmi',
        		type: 'POST',
        		data: {
        			kdpst_nmpst_kmp: '<%=kdpst_nmpst_kmp%>' //ignore karena sebenarnya dari wondow.loca.href
        	    },
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	        //this is where we append a loading image
        	    },
        	    success:function(data){
        	        //successful request; do something with the data 
        	        <%
        	        kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
        	        %>

        	        window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Spmi/spmi_overview/dash_overview.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        //failed request; give feedback to user
        	    }
        	})
        	return false;
        });
		
	});	
    </script>
<script type="text/javascript">
$(document).ready(function(){
	
	$('table.StateTable tr.statetablerow th') .parents('table.StateTable') .children('tbody') .toggle();
	$('table.StateTable tr.statetablerow td') .parents('table.StateTable') .children('tbody') .toggle();

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
	table.StateTable thead th{background: <%=Constant.lightColorGrey() %>; padding: 0px; cursor:pointer; color:white;text-align:center;}
	table.CityTable thead th{padding: 0px; background: #C7DBF1;cursor:pointer; color:black;}
	
	table.StateTable td.nopad{padding:0;;text-align:center;}
	table.StateTable tr.statetablerow { background:<%=Constant.lightColorBlu() %> }
	table.StateTable tr.statetablerow:hover { background:<%=Constant.lightColorGrey() %> }
	table.StateTable tbody:hover td.nopad { background:<%=Constant.lightColorGrey() %> }
</style>
</head>
<body>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai</p>
</div>
<div id="main">
	<div id="header">
	</div>
	<div class="colmask fullpage">
		<div class="col1">
		<br />
		<!-- Column 1 start -->
		<%@include file="menu_nav1.jsp" %>	
		<br>
<%


Vector v_scope_kdpst_spmi = (Vector)session.getAttribute("v_scope_kdpst_spmi");
//ListIterator lis = v_scope_kdpst_spmi.listIterator();

%>			
		
	<center>
	<br>
<%
if(false) {
%>
	<div style="text-align:center">
		<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/facepalm_itachi_small.png" alt="Gubraaak !@?!?">
		<h2>GuBRaAKk ...!!??@??!?<br>Maaf ada kesalahan yg memalukan, harap lapor ke admin<br>Sorry & Tengkiu ya... </h2>
	</div>
<%
}
else {
	int i=1;
	//ListIterator li = v_riwayat_ami_prodi.listIterator();
	//System.out.println("v_riwayat_ami_prodi="+v_riwayat_ami_prodi.size());
	//Vector v_master = ToolSpmi.getIdDanNmMasterStandar();
	//ListIterator lim = v_master.listIterator();
	if(true) {
	//if(lim.hasNext()) {
		//double tot_master_std = v_master.size();
%>		
	
	<table class="table2" style="width:90%">
	<thead>
	<tr>
		<th colspan="6" style="background:#369;font-size:2em;color:#fff;font-weight:bold;border-right:1px solid #369;height:40px">
				HASIL AMI
		</th>
	</tr>
	<tr>	
		<th style="background:#369;width:5%;color:#fff;font-weight:bold;border-right:1px solid #369;height:40px">
				No.
		</th>
		<th style="background:#369;width:20%;color:#fff;font-weight:bold;border-left:1px solid #369;border-right:1px solid #369">
				PROGRAM STUDI
		</th>
		<th onclick="(function(){
					var x = document.getElementById('wait');
					var y = document.getElementById('main');
					x.style.display = 'block';
					y.style.display = 'none';
					location.href='<%=Constants.getRootWeb() %>/ErrorPage/ServerMaintenance.jsp'})()"
          			style="color:#fff;font-weight:bold;border-right:1px solid #369"> 
				STANDAR NASIONAL PENDIDIKAN
		</th> 
		<th onclick="(function(){
					var x = document.getElementById('wait');
					var y = document.getElementById('main');
					x.style.display = 'block';
					y.style.display = 'none';
					location.href='<%=Constants.getRootWeb() %>/ErrorPage/ServerMaintenance.jsp'})()"
          			style="color:#fff;font-weight:bold;border-right:1px solid #369"> 
				STANDAR NASIONAL PENELITIAN
		</th> 
		<th onclick="(function(){
					var x = document.getElementById('wait');
					var y = document.getElementById('main');
					x.style.display = 'block';
					y.style.display = 'none';
					location.href='<%=Constants.getRootWeb() %>/ErrorPage/ServerMaintenance.jsp'})()"
          			style="color:#fff;font-weight:bold;border-right:1px solid #369"> 
				STANDAR NASIONAL PKM
		</th> 
		<th onclick="(function(){
					var x = document.getElementById('wait');
					var y = document.getElementById('main');
					x.style.display = 'block';
					y.style.display = 'none';
					location.href='<%=Constants.getRootWeb() %>/ErrorPage/ServerMaintenance.jsp'})()"
          			style="color:#fff;font-weight:bold;border-right:1px solid #369"> 
				STANDAR IDENTITAS
		</th> 
	</tr>
	</thead>
<%
		
%>
	<tbody>
		<tr>

			<td>	
			<%=i++ %>
			</td>
			<td style="text-align:left;padding:0 0 0 5px">
			AGAMA
			</td>
			<td>
			75.02%
			</td>
			<td>
			60.50%
			</td>
			<td>
			61.17%
			</td>
			<td>
			85%
			</td>
		</tr>
		<tr>
			<td>	
			<%=i++ %>
			</td>
			<td style="text-align:left;padding:0 0 0 5px">
			AGRIBISNIS
			</td>
			<td>
			69.83%
			</td>
			<td>
			67%
			</td>
			<td>
			71.20%
			</td>
			<td>
			87%
			<td>
		</tr>
		<tr>
			<td>	
			<%=i++ %>
			</td>
			<td style="text-align:left;padding:0 0 0 5px">
			AGROTEKNOLOGI
			</td>
			<td>
			50.50%
			</td>
			<td>
			45%
			</td>
			<td>
			49.73%
			</td>
			<td>
			72.32%
			</td>
		</tr>
		<tr>
			<td>	
			<%=i++ %>
			</td>
			<td style="text-align:left;padding:0 0 0 5px">
			AKUNTANSI
			</td>
			<td>
			81.50%
			</td>
			<td>
			66.70%
			</td>
			<td>
			67.30%
			</td>
			<td>
			88%
			</td>
		</tr>
		<tr>
			<td>	
			<%=i++ %>
			</td>
			<td style="text-align:left;padding:0 0 0 5px">
			ARSITEKTUR
			</td>
			<td>
			57.45%
			</td>
			<td>
			56.50%
			</td>
			<td>
			50.03%
			</td>
			<td>
			78%
			</td>
		</tr>
		<tr>
			<td>	
			<%=i++ %>
			</td>
			<td style="text-align:left;padding:0 0 0 5px">
			HUKUM
			</td>
			<td>
			73%
			</td>
			<td>
			67.13%
			</td>
			<td>
			75.91%
			</td>
			<td>
			89.20%
			</td>
		</tr>
		<tr>
			<td>	
			<%=i++ %>
			</td>
			<td style="text-align:left;padding:0 0 0 5px">
			ILMU HUBUNGAN INTERNASIONAL
			</td>
			<td>
			73.33%
			</td>
			<td>
			61.70%
			</td>
			<td>
			62.50%%
			</td>
			<td>
			77%
			</td>
		</tr>
		<tr>
			<td>	
			<%=i++ %>
			</td>
			<td style="text-align:left;padding:0 0 0 5px">
			ILMU PEMERINTAHAN (S-1)
			</td>
			<td>
			81%
			</td>
			<td>
			59.10%
			</td>
			<td>
			75%
			</td>
			<td>
			85%
			</td>
		</tr>
		<tr>
			<td>	
			<%=i++ %>
			</td>
			<td style="text-align:left;padding:0 0 0 5px">
			ILMU PEMERINTAHAN (S-2)
			</td>
			<td>
			83.45%
			</td>
			<td>
			69%
			</td>
			<td>
			77.89%
			</td>
			<td>
			85%
			</td>
		</tr>
		<tr>
			<td>	
			<%=i++ %>
			</td>
			<td style="text-align:left;padding:0 0 0 5px">
			ILMU PEMERINTAHAN (S-3)
			</td>
			<td>
			83.45%
			</td>
			<td>
			72.11%
			</td>
			<td>
			78%
			</td>
			<td>
			85.95%
			</td>
		</tr>
		<tr>
			<td>	
			<%=i++ %>
			</td>
			<td style="text-align:left;padding:0 0 0 5px">
			INDUSTRI PARIWISATA DAN PERHOTELAN
			</td>
			<td>
			69.50%
			</td>
			<td>
			60%
			</td>
			<td>
			65.77%
			</td>
			<td>
			70%
			</td>
		</tr>
		<tr>
			<td>	
			<%=i++ %>
			</td>
			<td style="text-align:left;padding:0 0 0 5px">
			MANAJEMEN (S-1)
			</td>
			<td>
			83.33%
			</td>
			<td>
			65.66%
			</td>
			<td>
			59.25%
			</td>
			<td>
			75%
			</td>
		</tr>
		<tr>
			<td>	
			<%=i++ %>
			</td>
			<td style="text-align:left;padding:0 0 0 5px">
			MANAJEMEN (S-2)
			</td>
			<td>
			82%
			</td>
			<td>
			62.67%
			</td>
			<td>
			60.25%
			</td>
			<td>
			75%
			</td>
		</tr>
		<tr>
			<td>	
			<%=i++ %>
			</td>
			<td style="text-align:left;padding:0 0 0 5px">
			TEKNIK ELEKTRO
			</td>
			<td>
			51.11%
			</td>
			<td>
			57.25%
			</td>
			<td>
			49%
			</td>
			<td>
			70%
			</td>
		</tr>
		<tr>
			<td>	
			<%=i++ %>
			</td>
			<td style="text-align:left;padding:0 0 0 5px">
			TEKNIK INDUSTRI
			</td>
			<td>
			52.25%
			</td>
			<td>
			52%
			</td>
			<td>
			55.75%
			</td>
			<td>
			69%
			</td>
		</tr>
		<tr>
			<td>	
			<%=i++ %>
			</td>
			<td style="text-align:left;padding:0 0 0 5px">
			TEKNIK INFORMATIKA
			</td>
			<td>
			57%
			</td>
			<td>
			52.25%
			</td>
			<td>
			55%
			</td>
			<td>
			67%
			</td>
		</tr>
		<tr>
			<td>	
			<%=i++ %>
			</td>
			<td style="text-align:left;padding:0 0 0 5px">
			TEKNIK SIPIL
			</td>
			<td>
			55%
			</td>
			<td>
			54.55%
			</td>
			<td>
			53.25%
			</td>
			<td>
			65%
			</td>
		</tr>
	</tbody>	
	</table>
<%
	}	
	
	
	
}
%>
			</center>	
		</div>
		<!-- Column 1 end -->
		
	</div>
</div>
</div>
</body>
</html>
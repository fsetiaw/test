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
Vector v_isi_std_never_monitored=(Vector)session.getAttribute("v_isi_std_never_monitored");
String target_kdpst = request.getParameter("target_kdpst");
Boolean spmi_editor = (Boolean) session.getAttribute("spmi_editor");
String target_id_eval = (String) request.getParameter("id_eval");
Vector v_pelanggaran_isi_std=(Vector)session.getAttribute("v_pelanggaran_isi_std");
/*
MASUK KE pAGE INI VECOR > 0
*/
/*
ListIterator li = v_isi_std_never_monitored.listIterator();
Vector v_scope = validUsr.returnScopeProdiOnlySortByKampusWithListIdobj("hasSpmiMenu");
if()
ListIterator lik = v_scope.listIterator();
while(lik.hasNext()) {
	String brs = (String)lik.next();
	System.out.println(brs);
}
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
border: 1px solid #369;
background:<%=Constant.lightColorBlu()%>;
}
.table thead > tr > th {
border-bottom: 1px solid #369;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #fff; }

.table-noborder { border: 1px solid #369;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: 1px solid #369;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: 1px solid #369;padding: 2px }
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
	table.StateTable tr:hover td.nopad { background:<%=Constant.lightColorGrey() %> }
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
		<%@include file="menu_nav2.jsp" %>	
		<br>
		<center>	
		


<%
ListIterator li = v_pelanggaran_isi_std.listIterator();
ListIterator lit = null;
boolean match = false;
while(li.hasNext() && !match) {
	String this_kdpst = (String)li.next();
	boolean masuk_scope=true;//sudah di filter di sqlnya
	String prodi = Converter.getNamaKdpstDanJenjang(this_kdpst);
	Vector v_tmp = (Vector)li.next();
	lit = v_tmp.listIterator();
	StringTokenizer stt = null;
	int counter = 1;
	while(lit.hasNext() && !match) {
		String brs = (String)lit.next();
		//System.out.println(brs);
		stt = new StringTokenizer(brs,"~");
		//id_eval+"~"+id_std_isi+"~"+id_versi+"~"+npm_eval+"~"+tgl_eval+"~"+kondisi_eval+"~"+analisa_eval+"~"+rekomen_eval+"~"+target_val+"~"+ril_val+"~"+id_kendal+"~"+rasional_kendal+"~"+tindakan_kendal+"~"+tgl_kendal+"~"+npm_kendal+"~"+pelanggaran+"~"+jenis_pelanggaran;
		String id_eval = stt.nextToken();
		String id_std_isi = stt.nextToken();
		String id_versi = stt.nextToken();
		String npm_eval = stt.nextToken();
		String tgl_eval = stt.nextToken();
		String kondisi_eval = stt.nextToken();
		String analisa_eval = stt.nextToken();
		String rekomen_eval = stt.nextToken();
		String target_val = stt.nextToken();
		String ril_val = stt.nextToken();
		String id_kendal = stt.nextToken();
		String rasional_kendal = stt.nextToken();
		String tindakan_kendal = stt.nextToken();
		String tgl_kendal = stt.nextToken();
		String npm_kendal = stt.nextToken();
		String pelanggaran = stt.nextToken();
		String jenis_pelanggaran = stt.nextToken();
		String isi_std = stt.nextToken();
		String unit = stt.nextToken();
		if(id_eval.equalsIgnoreCase(target_id_eval)) {
			match=true;
			String tmp = ToolSpmi.getNmMasterDanNamaStandar(Integer.parseInt(id_std_isi));
			StringTokenizer st1 = new StringTokenizer(tmp,"~");
			String nm_master = st1.nextToken();
			String nm_standar = st1.nextToken();
%>
		<table class="table" style="width:90%">
			<tr>
				<td colspan="4" style="text-align:center;background:#369;font-weight:bold;color:white;font-size:1.5em;border:1px solid #369">
					<%=nm_master.toUpperCase() %><br><%=nm_standar.toUpperCase() %>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="width:80%;border:1px solid #369;font-weight:bold;font-size:1.2em">
					PERNYATAAN ISI STANDAR
				</td>
				<td colspan="1" style="width:10%;border:1px solid #369;font-weight:bold;font-size:1.2em;text-align:center">
					TARGET
				</td>
				<td colspan="1" style="width:15%;border:1px solid #369;font-weight:bold;font-size:1.2em;text-align:center">
					RIL
				</td>
			</tr>
			<tr>
				<td colspan="2" style="width:80%;border:1px solid #369;background:#fff;padding:0 0 0 30px">
					<%=isi_std %>
				</td>
				<td colspan="1" style="width:10%;border:1px solid #369;background:#fff;text-align:center">
					<%=target_val %><br><%=unit %>
				</td>
				<td colspan="1" style="width:15%;border:1px solid #369;background:#fff;text-align:center">
					<%=ril_val %><br><%=unit %>
				</td>
			</tr>
			<tr>
				<td style="width:25%;border:1px solid #369;font-weight:bold;font-size:1.2em;background:<%=Constant.darkColorBlu() %>;color:#fff">
					NAMA EVALUATOR 
				</td>
				<td colspan="3" style="width:75%;border:1px solid #369;font-weight:bold;font-size:1.2em;background:#fff">
					<%=Checker.getNmmhs(npm_eval) %>
				</td>
			</tr>
			<tr>
				<td style="width:25%;border:1px solid #369;padding:0 0 0 25px;font-weight:bold;font-size:1em">
					KONDISI SAAT EVALUASI 
				</td>
				<td colspan="3" style="width:75%;border:1px solid #369;background:#fff">
					<%=kondisi_eval %>
				</td>
			</tr>
			<tr>
				<td style="width:25%;border:1px solid #369;padding:0 0 0 25px;font-weight:bold;font-size:1em">
					ANALISA HASIL EVALUASI 
				</td>
				<td colspan="3" style="width:75%;border:1px solid #369;background:#fff">
					<%=kondisi_eval %>
				</td>
			</tr>
			<tr>
				<td style="width:25%;border:1px solid #369;padding:0 0 0 25px;font-weight:bold;font-size:1em">
					REKOMENDASI 
				</td>
				<td colspan="3" style="width:75%;border:1px solid #369;background:#fff">
					<%=rekomen_eval %>
				</td>
			</tr>
			<tr>
				<td style="width:25%;border:1px solid #369;font-weight:bold;font-size:1.2em;background:<%=Constant.darkColorBlu() %>;color:#fff">
					NAMA PENGENDALI 
				</td>
				<td colspan="3" style="width:75%;border:1px solid #369;background:#fff">
					<%=Checker.getNmmhs(npm_kendal) %>
				</td>
			</tr>
			<tr>
				<td style="width:25%;border:1px solid #369;padding:0 0 0 25px;font-weight:bold;font-size:1em">
					RASIONALE PENGENDALIAN
				</td>
				<td colspan="3" style="width:75%;border:1px solid #369;background:#fff">
					<%=rasional_kendal %>
				</td>
			</tr>
			<tr>
				<td style="width:25%;border:1px solid #369;padding:0 0 0 25px;font-weight:bold;font-size:1em">
					TINDAKAN PENGENDALIAN 
				</td>
				<td colspan="3" style="width:75%;border:1px solid #369;background:#fff">
					<%=tindakan_kendal %>
				</td>
			</tr>
		</table>
<%			
		}
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
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
	//System.out.println(brs);
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
int norut=1;
int norut_std=1;
int norut_isi=1;
boolean first=true;
ListIterator lit = null;
ListIterator li = v_isi_std_never_monitored.listIterator();
while(li.hasNext()) {
	String this_kdpst = (String)li.next();
	String prodi = Converter.getNamaKdpstDanJenjang(this_kdpst);
	Vector v_tmp = (Vector)li.next();
	if(v_tmp.size()>0 && this_kdpst.equalsIgnoreCase(target_kdpst)) {
		//System.out.println("v_tmp "+target_kdpst+" = "+v_tmp.size());
		if(first) {
			first=false;
			//buat heade table
%>
<table style="width:70%">
	<th style="font-size:1.5em;background:#369;color:#fff;font-weight:bold;border-left:1px solid #369;border-right:1px solid #369;height:35px">
			PROGRAM STUDI <%=prodi %> [<%=v_tmp.size() %>]
	</th> 
</table>
<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="padding:0 0 0 0;border:1px solid white;width:70%;background:white;">
<%
		}
		lit = v_tmp.listIterator();
		int counter = 1;
		if(lit.hasNext()) {
			int no=1;
			String brs = (String)lit.next();
			//System.out.println("baris "+no+"="+brs);
			StringTokenizer stt = new StringTokenizer(brs,"~");
			String id_master_std = stt.nextToken();
			String id_std = stt.nextToken();
			String id_tipe_std = stt.nextToken();
			String id_std_isi = stt.nextToken();
			String nm_rumpun_std = stt.nextToken();
			String nm_std = stt.nextToken();
			String isi_std = stt.nextToken();
%>
	<thead>
		<tr class="statetablerow">
			<td rowspan="3" style="width:10%;text-align:center;height:35px;border:1px solid #369;">
				<%=norut %>
			</td>
			<td style="width:90%;border:1px solid #369;padding:0 0 0 10px;text-align:left">
				<%=nm_rumpun_std %>
			</td>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td style="background:<%=Constant.lightColorBlu() %>;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-bottom:none">
				<%="" %>
			</td>
			<td style="text-align:left;padding:0 0 0 10px;border:1px solid #369">
				<%=norut %>.<%=norut_std %>.&nbsp&nbsp&nbsp<%=nm_std %>
			</td>
		</tr>
		<tr>
			<td style="background:<%=Constant.lightColorBlu() %>;text-align:left;padding:0 0 0 50px;border:1px solid #369;border-top:none">
				<%="" %>
			</td>
			<td style="text-align:left;padding:0 0 0 22px;border:1px solid #369">
				<%=norut %>.<%=norut_std %>.<%=norut_isi++ %>.&nbsp&nbsp&nbsp<%=isi_std %>
			</td>
		</tr>
		
	
<%	
			while(lit.hasNext()) {
				brs = (String)lit.next();
				no++;
				//System.out.println("baris "+no+"="+brs);
				stt = new StringTokenizer(brs,"~");
				String curr_id_master_std = stt.nextToken();
				String curr_id_std = stt.nextToken();
				String curr_id_tipe_std = stt.nextToken();
				String curr_id_std_isi = stt.nextToken();
				String curr_nm_rumpun_std = stt.nextToken();
				String curr_nm_std = stt.nextToken();
				String curr_isi_std = stt.nextToken();
				if(curr_id_master_std.equalsIgnoreCase(id_master_std)) {
					//rumpun masih sama
					if(curr_id_tipe_std.equalsIgnoreCase(id_tipe_std)) {
						//std masih sama
%>
		<tr>
			<td style="background:<%=Constant.lightColorBlu() %>;text-align:left;padding:0 0 0 50px;border:1px solid #369;border-top:none">
				<%="" %>
			</td>
			<td style="text-align:left;padding:0 0 0 22px;border:1px solid #369">
				<%=norut %>.<%=norut_std %>.<%=norut_isi++ %>.&nbsp&nbsp&nbsp<%=curr_isi_std %>
			</td>
		</tr>
<%						
					}
					else {
						//ganti standar
						norut_std++;
						norut_isi=1;
						id_tipe_std = new String(curr_id_tipe_std);
%>
		<tr>
			<td style="background:<%=Constant.lightColorBlu() %>;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-bottom:none">
				<%="" %>
			</td>
			<td style="text-align:left;padding:0 0 0 10px;border:1px solid #369">
				<%=norut %>.<%=norut_std %>.&nbsp&nbsp&nbsp<%=curr_nm_std %>
			</td>
		</tr>
		<tr>
			<td style="background:<%=Constant.lightColorBlu() %>;text-align:left;padding:0 0 0 50px;border:1px solid #369;border-top:none">
				<%="" %>
			</td>
			<td style="text-align:left;padding:0 0 0 22px;border:1px solid #369">
				<%=norut %>.<%=norut_std %>.<%=norut_isi++ %>.&nbsp&nbsp&nbsp<%=curr_isi_std %>
			</td>
		</tr>
<%						
					}
				}
				else {
					//ganti rumpun
					norut++;
					norut_std=1;
					norut_isi=1;
					id_master_std = new String(curr_id_master_std);
					id_tipe_std = new String(curr_id_tipe_std);
%>
	</tbody>
</table>
<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="padding:0 0 0 0;border:1px solid white;width:70%;background:white;">
	<thead>
		<tr class="statetablerow">
			<td rowspan="3" style="width:10%;text-align:center;height:35px;border:1px solid #369;">
				<%=norut %>
			</td>
			<td style="width:90%;border:1px solid #369;padding:0 0 0 10px;text-align:left">
				<%=curr_nm_rumpun_std %>
			</td>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td style="background:<%=Constant.lightColorBlu() %>;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-bottom:none">
				<%="" %>
			</td>
			<td style="text-align:left;padding:0 0 0 10px;border:1px solid #369">
				<%=norut %>.<%=norut_std %>.&nbsp&nbsp&nbsp<%=curr_nm_std %>
			</td>
		</tr>
		<tr>
			<td style="background:<%=Constant.lightColorBlu() %>;text-align:left;padding:0 0 0 50px;border:1px solid #369;border-top:none">
				<%="" %>
			</td>
			<td style="text-align:left;padding:0 0 0 22px;border:1px solid #369">
				<%=norut %>.<%=norut_std %>.<%=norut_isi++ %>.&nbsp&nbsp&nbsp<%=curr_isi_std %>
			</td>
		</tr>
<%					
				}
			}
		}
%>
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
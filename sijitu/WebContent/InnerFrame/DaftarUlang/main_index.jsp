<!DOCTYPE html>
<head>

<%@ page import="beans.dbase.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.login.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.tools.PathFinder"%>
<%@ page import="beans.dbase.daftarUlang.*" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<title>Insert title here</title>
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" /><style>
<style>


.table {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
width: 90%;
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
<%


beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v_scope_edit_nim_id = validUsr.returnScopeProdiOnlySortByKampusWithListIdobj("nim");
Vector vf = (Vector)session.getAttribute("vf");
Vector v_scope_id = (Vector)session.getAttribute("v_scope_id");
String target_thsms=request.getParameter("target_thsms");
String scope_cmd=request.getParameter("scope_cmd");
String table_rule_nm=request.getParameter("table_rule_nm");
String at_kmp=request.getParameter("at_kmp");
ListIterator li3 =null;
String list_nim_bentrok = request.getParameter("list_nim_bentrok");
String root_caller = (String)request.getParameter("root_caller");
%>


<script type="text/javascript">
$(document).ready(function(){
	
	/*$('table.StateTable tr.statetablerow th') .parents('table.StateTable') .children('tbody') .toggle();*/
	/*$('table.StateTable tr.statetablerow td') .parents('table.StateTable') .children('tbody') .toggle();*/

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
	
	
	table.StateTable tr.statetablerow { background:<%=Constant.darkColorBlu() %> }
	table.StateTable tr.statetablerow:hover { background:<%=Constant.lightColorBlu() %> }
	table.StateTable td.nopad{padding:0 0 0 10px;font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:10%;color:#369;text-align:left;border:1px solid #369}
	table.StateTable tr:hover td.nopad { background:<%=Constant.lightColorBlu() %> }
 	 

</style>
<script>
	$(document).ready(function() {
		$("#wait").hide();
		$("#main").show();
	});	
	
	function myFunction() {
	    var x = document.getElementById('wait');
	    var y = document.getElementById('main');
	    if (x.style.display === "none") {
	        x.style.display = "block";
	        y.style.display = "none";
	    } else {
	        x.style.display = "none";
	        y.style.display = "block";
	    }
	    //wait(4000);
	    //location.href="www.google.com";
	}
</script>
</head>
<body style="background:white">
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai<br>[Lama proses bisa sampai 5 menit]</p>
</div>

<div id="header" style="background:white">
	<ul>
		<li><a href="index.jsp" target="inner_iframe"">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
	</ul>	
</div>
<div id="main">
<div class="colmask fullpage">
	<div class="col1">
		<br/>
		<center>
<%
boolean first=true;
String prev_kmp="null";
int norut=1;
if(vf!=null && vf.size()>0) {
	ListIterator li = vf.listIterator();
	ListIterator li1 = null;
	ListIterator lim = null;
	while(li.hasNext()) {
		String kmp = (String)li.next();
		
		Vector vtmp = (Vector)li.next();
		li1 = vtmp.listIterator();
		while(li1.hasNext()) {
			String idobj = (String)li1.next();
			
			Vector vmhs = (Vector)li1.next();
			if(vmhs.size()>0) {
				//lim = vmhs.listIterator();
				//while(lim.hasNext()) {
				//	String brs = (String)lim.next();
				//}	
				
				if(first) {
					first=false;
					prev_kmp = new String(kmp);
					//out.print("kampus="+kmp+"<br>");
					//out.print("&nbsp&nbspidobj="+idobj+"<br>");
					//out.print("&nbsp&nbsp&nbsp&nbspJumlah Pengajuan = "+vmhs.size()+"<br>");
%>
		<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="padding:50px 0 50px 0;border:1px solid white;width:70%;background:white;">	
		<thead>
			<tr class="statetablerow">
				<td colspan="3" style="padding:25px 10px;font-size:1.5em;text-align:center;font-weight:bold;color:#fff;border:1px solid #369">
				<%=Converter.getNamaKampus(kmp) %>
				</td>
			</tr>
				
		</thead>
		<tbody>
			<tr >
				<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:10%;color:#369;text-align:center;border:1px solid #369">
					NO
				</td>
				<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:60%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					PROGRAM STUDI
				</td>
				<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:30%;color:#369;text-align:center;padding:0 0 0 10px;border:1px solid #369">
					TOTAL PENGAJUAN
				</td>
			</tr>
			<tr onclick="(function(){
				var x = document.getElementById('wait');
		    	var y = document.getElementById('main');
		    	x.style.display = 'block';
		       	y.style.display = 'none';
		       	location.href='prep.formValidasiHeregistrasi?idobj=<%=idobj%>&kmp=<%=kmp%>'})()">
				<td class="nopad" style="text-align:center">
					<%=norut++ %>
				</td>
				<td class="nopad" >
					<%=Converter.getNamaKdpst(Integer.parseInt(idobj))%>
				</td>
				<td class="nopad" style="text-align:center">
					<%=vmhs.size() %>
				</td>
			</tr>
<%					
				}
				else {
					if(prev_kmp.equalsIgnoreCase(kmp)) {
							//same kampus
%>
			<tr onclick="(function(){
				var x = document.getElementById('wait');
		    	var y = document.getElementById('main');
		    	x.style.display = 'block';
		       	y.style.display = 'none';
		       	location.href='prep.formValidasiHeregistrasi?idobj=<%=idobj%>&kmp=<%=kmp%>'})()">
				<td class="nopad" style="text-align:center">
					<%=norut++ %>
				</td>
				<td class="nopad">
					<%=Converter.getNamaKdpst(Integer.parseInt(idobj))%>
				</td>
				<td class="nopad" style="text-align:center">
					<%=vmhs.size() %>
				</td>
			</tr>					
<%	
						//out.print("kampus="+kmp+"<br>");
						//out.print("&nbsp&nbspidobj="+idobj+"<br>");
						//out.print("&nbsp&nbsp&nbsp&nbspJumlah Pengajuan = "+vmhs.size()+"<br>");
					}
					else {
						//closing prev kampus 
%>
		</tbody>
	</table>	
<%						
						//header new kampus
						prev_kmp = new String(kmp);
						norut=1;
%>
	<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="padding:50px 0 50px 0;border:1px solid white;width:70%;background:white;">	
		<thead>
			<tr class="statetablerow">
				<td colspan="3" style="padding:25px 10px;font-size:1.5em;text-align:left;font-weight:bold;color:#fff;border:1px solid #369">
				<%=kmp %>
				</td>
			</tr>
				
		</thead>
		<tbody>
			<tr>
				<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:10%;color:#369;text-align:center;border:1px solid #369">
					NO
				</td>
				<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:60%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					PROGRAM STUDI
				</td>
				<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:30%;color:#369;text-align:center;padding:0 0 0 10px;border:1px solid #369">
					TOTAL PENGAJUAN
				</td>
			</tr>
			<tr onclick="(function(){
				var x = document.getElementById('wait');
		    	var y = document.getElementById('main');
		    	x.style.display = 'block';
		       	y.style.display = 'none';
		       	location.href='prep.formValidasiHeregistrasi?idobj=<%=idobj%>&kmp=<%=kmp%>'})()">
				<td class="nopad" style="text-align:center">
					<%=norut++ %>
				</td>
				<td class="nopad">
					<%=Converter.getNamaKdpst(Integer.parseInt(idobj))%>
				</td>
				<td class="nopad" style="text-align:center">
					<%=vmhs.size() %>
				</td>
			</tr>
<%						
						//out.print("Nu kampus="+kmp+"<br>");
						//out.print("&nbsp&nbspidobj="+idobj+"<br>");
						//out.print("&nbsp&nbsp&nbsp&nbspJumlah Pengajuan = "+vmhs.size()+"<br>");
						
						
						
					}
				}
			}
			if(!li1.hasNext()) {
				if(!first) {
						%>
		</tbody>
	</table>
				<%							
				}
			}	
			else {
				//out.print("&nbsp&nbsp&nbsp&nbsp0 MHS<br>");
			}
			
			//out.print("<br><br>");
		}
		//out.print("<br>------------------------------------------------------------<br>");
	}
}
%>

	</div>
	</center>
</div>
</div>		
</body>
	
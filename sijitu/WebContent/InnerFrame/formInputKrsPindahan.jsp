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
<%@ page import="beans.dbase.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
int i=0;
String aspti = request.getParameter("aspti");
String aspst = request.getParameter("aspst");
String fwdTo = request.getParameter("fwdTo");
String fwdPg = request.getParameter("fwdPg");
%>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>

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
<script type="text/javascript">
$(document).ready(function(){
	
	$('table.StateTable tr.statetablerow td') .parents('table.StateTable') .children('tbody') .toggle();
	
	/*
	kalo aktifin - maka headnya aja yg tampil
	*/
	/*
	$('table.CityTable th') .parents('table.CityTable') .children('tbody') .toggle();
	*/
	/*
	$('table.CityTable th') .parents('table.StateTable') .children('tbody') .toggle();
	*/
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
	table.StateTable{margin:0px; border:1px solid #369;}
	
	table td{padding:0px;}
	table.StateTable thead th{background: #369; padding: 0px; cursor:pointer; color:white;}
	table.CityTable thead th{padding: 0px; background: #C7DBF1;cursor:pointer; color:black;}
	
	table.StateTable td.nopad{padding:0;}
	table.StateTable tr.statetablerow { background:<%=Constant.lightColorBlu() %> }
	table.StateTable tr.statetablerow:hover { background:#fff }
</style>

</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">
<%@ include file="dataMhsPindahanSubMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		
		<form action="update.KrsAsalPT">
		<input type="hidden" name="id_obj" value="<%=objId %>" /><input type="hidden" name="nmm" value="<%= nmm%>" />
		<input type="hidden" name="npm" value="<%=npm %>" /><input type="hidden" name="obj_lvl" value="<%= obj_lvl%>" />
		<input type="hidden" name="kdpst" value="<%= kdpst%>" /><input type="hidden" name="aspti" value="<%= aspti%>" />
		<input type="hidden" name="cmd" value="<%=cmd %>" /><input type="hidden" name="fwdTo" value="<%= fwdTo%>" />
		<input type="hidden" name="fwdPg" value="indexMhsPindahan.jsp" /><input type="hidden" name="aspst" value="<%=aspst %>" />
		<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="width:100%">
		<thead>
			<tr class="statetablerow">
        		<td style="border-bottom:1px solid white;background:#369;color:#fff;text-align:center;font-size:2em" colspan="6"><label><B>INPUT FORM TRANSKRIPT ASAL PT</B> </label></td></td>
       		</tr>
       		<tr class="statetablerow">
      			<td style="border-right:1px solid white;background:#369;color:#fff;text-align:center;width:5%"><label><B>NO.</B> </label></td></td>
      			<td style="border-left:1px solid white;border-right:1px solid white;background:#369;color:#fff;text-align:center;width:55%;font-size:1.2em"><label><B>MATAKULIAH</B> </label></td></td>
      			<td style="border-left:1px solid white;border-right:1px solid white;background:#369;color:#fff;text-align:center;width:15%;font-size:1.2em"><label><B>KODE MK ASAL</B> </label></td></td>
     			<td style="border-left:1px solid white;border-right:1px solid white;background:#369;color:#fff;text-align:center;width:10%;font-size:1.2em"><label><B>NILAI</B> </label></td></td>
        		<td style="border-left:1px solid white;border-right:1px solid white;background:#369;color:#fff;text-align:center;width:10%;font-size:1.2em"><label><B>BOBOT</B> </label></td></td>
        		<td style="border-left:1px solid white;background:#369;color:#fff;text-align:center;width:5%"><label><B>SKS</B> </label></td></td>
        	</tr>
        
        
        		
		<%
		////System.out.println("btrnlp="+vtrnlp);
		if(vtrnlm) {
			//System.out.println("v_-trnlm- = "+v0.size());
		}	
		if(vtrnlp) {
			//System.out.println("v_-trnlp- = "+v0.size());
			ListIterator li0 = v0.listIterator();
			while(li0.hasNext()) {
				i++;
			%>
			<tr>
			<%
			
				String thsms = (String)li0.next();
				String kdkmk = (String)li0.next();
				String nakmk = (String)li0.next();
				String nlakh = (String)li0.next();
				String bobot = (String)li0.next();
				String kdasl = (String)li0.next();
				String nmasl = (String)li0.next();
				String sksmk = (String)li0.next();
				String totSks = (String)li0.next();//ignore
				String sksas = (String)li0.next();
				String transferred = (String)li0.next();
				//System.out.println(thsms+"-"+kdkmk+"-"+kdasl+"-"+nmasl+"-"+transferred);
		%>
				<td style="border-bottom:1px solid #369;border-right:1px solid #369;background:<%=Constant.lightColorGrey() %>;color:#000;text-align:center;"><label><B><%=i %></B> </label></td>
				<td style="border-bottom:1px solid #369;border-left:1px solid #369;border-right:1px solid #369;background:<%=Constant.lightColorGrey() %>;color:#000;text-align:center;"><label><B><input type="text" name="nakmkasal" value="<%=nmasl %>" style="border:none;width:100%;height:35px" /></B> </label></td>
        		<td style="border-bottom:1px solid #369;border-left:1px solid #369;border-right:1px solid #369;background:<%=Constant.lightColorGrey() %>;color:#000;text-align:left;"><label><B><input type="text" name="kdkmkasal" value="<%=kdasl %>" style="border:none;width:100%;height:35px;text-align:center" /></B> </label></td>
        		<td style="border-bottom:1px solid #369;border-left:1px solid #369;border-right:1px solid #369;background:<%=Constant.lightColorGrey() %>;color:#000;text-align:center;"><label><B><input type="text" name="nlakhasal" value="<%=nlakh %>" style="border:none;width:100%;height:35px;text-align:center" /></B> </label></td>
        		<td style="border-bottom:1px solid #369;border-left:1px solid #369;border-right:1px solid #369;background:<%=Constant.lightColorGrey() %>;color:#000;text-align:center;"><label><B><input type="text" name="bobotasal" value="<%=bobot %>" style="border:none;width:100%;height:35px;text-align:center" /></B> </label></td>
        		<td style="border-bottom:1px solid #369;border-left:1px solid white;background:<%=Constant.lightColorGrey() %>;color:#000;text-align:center;"><label><B><input type="text" name="sksmkasal" value="<%=sksas %>" style="border:none;width:100%;height:35px;text-align:center" /></B> </label></td>
        				
        	</tr>
        <%
			}
		}
		for(i=i+1;i<=70;i++) {
			if(i==20) {
				%>
			</thead>
			<tbody>
				<%		
			}
			%>
			<tr>
				<td style="border-bottom:1px solid #369;border-right:1px solid #369;background:<%=Constant.lightColorGrey() %>;color:#000;text-align:center;"><label><B><%=i %></B> </label></td>
				<td style="border-bottom:1px solid #369;border-left:1px solid #369;border-right:1px solid #369;background:<%=Constant.lightColorGrey() %>;color:#000;text-align:left;"><label><B><input type="text" name="nakmkasal" value="" style="border:none;width:100%;height:35px" /></B> </label></td>
        		<td style="border-bottom:1px solid #369;border-left:1px solid #369;border-right:1px solid #369;background:<%=Constant.lightColorGrey() %>;color:#000;text-align:left;"><label><B><input type="text" name="kdkmkasal" value="" style="border:none;width:100%;height:35px;text-align:center" /></B> </label></td>
        		<td style="border-bottom:1px solid #369;border-left:1px solid #369;border-right:1px solid #369;background:<%=Constant.lightColorGrey() %>;color:#000;text-align:center;"><label><B><input type="text" name="nlakhasal" value="" style="border:none;width:100%;height:35px;text-align:center" /></B> </label></td>
        		<td style="border-bottom:1px solid #369;border-left:1px solid #369;border-right:1px solid #369;background:<%=Constant.lightColorGrey() %>;color:#000;text-align:center;"><label><B><input type="text" name="bobotasal" value="" style="border:none;width:100%;height:35px;text-align:center" /></B> </label></td>
        		<td style="border-bottom:1px solid #369;border-left:1px solid white;background:<%=Constant.lightColorGrey() %>;color:#000;text-align:center;"><label><B><input type="text" name="sksmkasal" value="" style="border:none;width:100%;height:35px;text-align:center" /></B> </label></td>
        	</tr>
			<%
		}

        %>
        <thead>	
        	<thead>
			<tr class="statetablerow">
        		<td style="border-bottom:1px solid white;background:#369;color:#fff;text-align:center;font-size:0.8em" colspan="6"><label><B>klik disini bila form kurang</B> </label></td></td>
       		</tr>
        	<tr>
        		<td style="background:<%=Constant.lightColorBlu() %>;color:#fff;text-align:center;padding:10px 10px" colspan="6">
        		
        		<section class="gradient">
     				<div style="text-align:center">
           				<button style="padding: 5px 50px;font-size: 20px;">UPDATE TRANSKRIP PINDAHAN</button>
     				</div>
				</section>
        		</td>
       		</tr>
       	</thead>	
       		</tbody>
        </table>
		</form>		
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>
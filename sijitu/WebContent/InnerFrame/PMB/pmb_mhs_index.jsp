<!DOCTYPE html>
<head>

<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="beans.dbase.wilayah.*" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
  <link rel="stylesheet" href="<%=Constants.getRootWeb() %>/css/jquery-ui.css">
  <script src="<%=Constants.getRootWeb() %>/jquery/jquery.min.js"></script>
  <script src="<%=Constants.getRootWeb() %>/js/jquery-ui.js"></script>
<%
String id_wil_indo = "000000";
SearchDbWilayah sdw = new SearchDbWilayah();
Vector v = sdw.getListNegara();
ListIterator li = null;
Vector v1 = sdw.getListWilayah(1,id_wil_indo);
%>  
  <script>
  $( function() {
	  
  
    var listNegara = [
<%


if(v!=null && v.size()>0) {
	li = v.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
%>
		"<%=brs%>"
<%
		if(li.hasNext()) {
			%>
			,
	<%			
		}
	}
}
%>
    ];
    $( "#negara" ).autocomplete({
      source: listNegara
    });
  } );
  </script>
<%
	//System.out.println("pmb1");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
%>	
<style>
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
/*
tr:hover td { background:#82B0C3 }
*/
  
</style>
</head>
<body>
<div id="header">
<jsp:include page="InnerMenu0_pageVersion.jsp" flush="true" />
</div>
<div class="colmask fullpage">
	<div class="col1">


		<br />
	<center>
	<form action="buku_tamu.jsp">
		<table class="table" width="70%">
			<thead>
				<tr>
  					<th colspan="2" style="text-align: center; padding: 0px 10px;font-size:1.5em">BUKU TAMU</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						NAMA LENGKAP
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px;width:70%">
						<input type="text" style="width:99%;height:99%" name="nama" id="nama" />
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						NIK
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px;width:70%">
						<input type="text" style="width:99%;height:99%" name="nama" id="nama" />
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						KEWARGANEGARAAN
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px;width:70%">
						 <input id="negara" style="width:99%;height:99%" type="text">
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center; background-color: #f2f2f2;vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						INFO TEMPAT TINGGAL
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						PROVINSI
					</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px;width:70%">
						<select name="propinsi" style="width:99%;height:99%">
							<option value="null" %>">-pilih propinsi-</option>
						<%
						li = v1.listIterator();
						while(li.hasNext()) {
							String brs=(String)li.next();
							StringTokenizer st = new StringTokenizer(brs,"`");
							String id_wil = st.nextToken();
							String nm_wil = st.nextToken();
							String id_ind = st.nextToken();
						%>
							<option value="<%=brs %>"><%=nm_wil.replace("Prop.","")%></option>
						<%	
						}
						%>
						</select>
					</td>
				</tr>
				
				
								
				
  			</tbody>
		</table>
	</form>
	</center>
	</br/>
	</div>
</div>	
</body>
</html>
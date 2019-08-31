<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />

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
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	//System.out.println("form penialaian akhir");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector v_list_dsn = (Vector)request.getAttribute("v_list_dsn");
	Vector v_list_distinct_dsn = (Vector)request.getAttribute("v_list_distinct_dsn");
	
	request.removeAttribute("v_list_dsn");
	request.removeAttribute("v_list_distinct_dsn");
	String target_thsms = request.getParameter("target_thsms");
	//System.out.println("editable="+editable);
	//System.out.println("syDsnNya="+syDsnNya);
%>
</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">
<ul>
<%

//String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/DashPerkuliahan.jsp";
String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/index_prakuliah.jsp";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);		
%>

	<li><a href="<%=target %>?tab=pre" target="_self">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
	
</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<%
		/*
		if(v_list_distinct_dsn!=null && v_list_distinct_dsn.size()>0) {
			ListIterator litmp = v_list_distinct_dsn.listIterator();
			int counter = 0;
			while(litmp.hasNext()) {
				String brs = (String)litmp.next();
				counter++;
				out.print(counter+". "+brs+"<br>");
			}
		}
		*/
		%>
		<!--  iframe name="hidden" src="hidden_comet" frameborder="0" height="0" width="100%"><iframe> <br/>
    	<iframe name="counter" src="count.html" frameborder="0" height="100%" width="100%"><iframe -->
		<!-- Column 1 start -->
		<%
		if(v_list_dsn!=null && v_list_dsn.size()>0){
			int norut = 1;
		%>
				<br>
		
		<form action="go.prepPenugasanDsn?mode=upd&target_thsms=<%=target_thsms %>" method="POST">
	    	<table class="table" >
			<tr>
	       		<td style="background:#369;color:#fff;text-align:center" colspan="5"><label><h2>FORM PENUGASAN DOSEN <%=target_thsms %></h2><br/></h1><B></B> </label></td>
	       	</tr>	
			<tr>
	       		<td style="width:5%;background:#369;color:#fff;text-align:center"><label><B>NO.</B> </label></td>
	       		<td style="width:10%;background:#369;color:#fff;text-align:center"><label><B>KDPST</B> </label></td>
	       		<td style="width:20%;background:#369;color:#fff;text-align:center"><label><B>NPM</B> </label></td>
	       		<td style="width:35%;background:#369;color:#fff;text-align:center"><label><B>NAMA MAHASISWA</B> </label></td>
	       		<td style="width:30%;background:#369;color:#fff;text-align:center"><label><B>SURAT TUGAS</label></td>
	       	</tr>
		<%	
			ListIterator li = v_list_dsn.listIterator();
			while(li.hasNext()) {
				String brs=(String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				String kdpst = st.nextToken();
				String nodos = st.nextToken();
				String nmdos = st.nextToken();
				String thsms_surat = st.nextToken();
				String no_surat = st.nextToken();
				String wajib = st.nextToken();
				if(wajib.equalsIgnoreCase("yes")) {
					if(target_thsms.equalsIgnoreCase(thsms_surat)) {
		%>
			<tr>
	       		<td align="left" style="vertical-align: middle; padding: 0px 5px"><%=norut++ %></td>
	       		<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=kdpst %></td>
	       		<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=nodos %></td>
	       		<td align="left" style="vertical-align: middle; padding: 0px 5px"><%=nmdos %>&nbsp;&nbsp;&nbsp;&#9989;</td>
	       		<td align="center" style="vertical-align: middle; padding: 0px 0px">
	       		<input type="hidden" name="target_kdpst" value="<%=kdpst %>"/>
	       		<input type="hidden" name="target_nodos" value="<%=nodos %>"/>
	       		<input type="hidden" name="target_nmdos" value="<%=nmdos %>"/>
	       		<input type="text" name="surat" value="<%=no_surat %>" style="width:100%;height:30px;border:none;text-align:center"/>
	       		</td>
	       	</tr>
		<%				
					}
					else {
						if(Checker.isStringNullOrEmpty(no_surat)) {
							%>
			<tr>
				<td align="left" style="vertical-align: middle; padding: 0px 5px"><%=norut++ %></td>
				<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=kdpst %></td>
				<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=nodos %></td>
				<td align="left" style="vertical-align: middle; padding: 0px 5px"><%=nmdos %>&nbsp;&nbsp;&nbsp;&#9989;</td>
				<td align="center" style="vertical-align: middle; padding: 0px 0px">
				<input type="hidden" name="target_kdpst" value="<%=kdpst %>"/>
	       		<input type="hidden" name="target_nodos" value="<%=nodos %>"/>
	       		<input type="hidden" name="target_nmdos" value="<%=nmdos %>"/>
				<input type="text" name="surat" style="width:100%;height:30px;border:none;text-align:center"/>
				</td>
			</tr>
						<%							
						}
						else {
							%>
			<tr>
				<td align="left" style="vertical-align: middle; padding: 0px 5px"><%=norut++ %></td>
				<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=kdpst %></td>
				<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=nodos %></td>
				<td align="left" style="vertical-align: middle; padding: 0px 5px"><%=nmdos %>&nbsp;&nbsp;&nbsp;&#9989;</td>
				<td align="center" style="vertical-align: middle; padding: 0px 0px">
				<input type="hidden" name="target_kdpst" value="<%=kdpst %>"/>
	       		<input type="hidden" name="target_nodos" value="<%=nodos %>"/>
	       		<input type="hidden" name="target_nmdos" value="<%=nmdos %>"/>
				<input type="text" name="surat" value="<%=no_surat %>" style="width:100%;height:30px;border:none;text-align:center"/>
				</td>
			</tr>
						<%		
						}
					}
				}
				else {
					if(target_thsms.equalsIgnoreCase(thsms_surat)) {
						%>
			<tr>
				<td align="left" style="vertical-align: middle; padding: 0px 5px"><%=norut++ %></td>
				<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=kdpst %></td>
				<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=nodos %></td>
				<td align="left" style="vertical-align: middle; padding: 0px 5px"><%=nmdos %></td>
				<td align="center" style="vertical-align: middle; padding: 0px 0px">
				<input type="hidden" name="target_kdpst" value="<%=kdpst %>"/>
	       		<input type="hidden" name="target_nodos" value="<%=nodos %>"/>
	       		<input type="hidden" name="target_nmdos" value="<%=nmdos %>"/>
				<input type="text" name="surat" value="<%=no_surat %>" style="width:100%;height:30px;border:none;text-align:center"/>
				</td>
			</tr>
						<%				
					}
					else {
						if(Checker.isStringNullOrEmpty(no_surat)) {
											%>
			<tr>
				<td align="left" style="vertical-align: middle; padding: 0px 5px"><%=norut++ %></td>
				<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=kdpst %></td>
				<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=nodos %></td>
				<td align="left" style="vertical-align: middle; padding: 0px 5px"><%=nmdos %></td>
				<td align="center" style="vertical-align: middle; padding: 0px 0px">
				<input type="hidden" name="target_kdpst" value="<%=kdpst %>"/>
	       		<input type="hidden" name="target_nodos" value="<%=nodos %>"/>
	       		<input type="hidden" name="target_nmdos" value="<%=nmdos %>"/>
				<input type="text" name="surat" style="width:100%;height:30px;border:none;text-align:center"/>
				</td>
			</tr>
										<%							
						}
						else {
											%>
			<tr>
				<td align="left" style="vertical-align: middle; padding: 0px 5px"><%=norut++ %></td>
				<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=kdpst %></td>
				<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=nodos %></td>
				<td align="left" style="vertical-align: middle; padding: 0px 5px"><%=nmdos %></td>
				<td align="center" style="vertical-align: middle; padding: 0px 0px">
				<input type="hidden" name="target_kdpst" value="<%=kdpst %>"/>
	       		<input type="hidden" name="target_nodos" value="<%=nodos %>"/>
	       		<input type="hidden" name="target_nmdos" value="<%=nmdos %>"/>
				<input type="text" name="surat" value="<%=no_surat %>" style="width:100%;height:30px;border:none;text-align:center"/>
				</td>
			</tr>
										<%		
						}
					}
				}
				
			}
			%>
			<tr>
	       		<td style="padding:10px 10px;color:#fff;text-align:center" colspan="5">
	       			<section class="gradient">
					<div style="text-align:center">
						<button style="padding: 5px 50px;font-size: 20px;">Update Data</button>
					</div>
					</section>
	       		</td>
	       	</tr>	
		</table>
	</form>		
			<%
		}
		else {
		%>
		<p style="text-align:center;font-weight:bold;font-size:1.8em">	Total Data = 0 </p>
				<%	
		}
		%>
		<!--  jsp:include page="hamburger_menu.jsp" flush="true" / -->


			

		<!-- Column 1 start -->
	</div>
</div>			
</body>
</html>
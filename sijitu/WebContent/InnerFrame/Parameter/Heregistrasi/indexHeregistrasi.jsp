<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>


<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
  
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />


<%
	//System.out.println("okeh");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	//String tipeForm = request.getParameter("formType");
	//Vector vDwn = validUsr.getScopeUpd7des2012("hasDownloadMenu");
	//Vector v_cf = validUsr.getScopeUpd7des2012("hasEditMkKurikulum");
	
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
.table thead > tr > th, .table tbody > tr > th, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9; }

.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px }
</style>

</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">

<%
boolean form_urutan = true;
String atMenu = request.getParameter("atMenu");
String scopeCmd = request.getParameter("scopeCmd");
String target_thsms = request.getParameter("target_thsms");
//String target_thsms = "20142";
String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);
//JSONArray jsoa_info_akses = (JSONArray) request.getAttribute("jsoa");
String target_param = null;
String scope_prodi = null;
String scope_kmp = null;
String hak_akses = request.getParameter("hak_akses");
boolean editable = Boolean.parseBoolean(""+request.getParameter("editable"));
Vector vListRule = (Vector) session.getAttribute("vListRule");
session.removeAttribute("vListRule");
String table_rule_name = (String)request.getAttribute("table_rule_name");
String type_pengajuan = table_rule_name.replace("_RULES", "");
String title_pengajuan = type_pengajuan.replace("_", " ");
Vector vListJabatan = (Vector)request.getAttribute("vListJabatan");
request.removeAttribute("vListJabatan");
Vector vp = (Vector)request.getAttribute("vp");
request.removeAttribute("vp");
Vector vkmp = (Vector)request.getAttribute("vkmp");
request.removeAttribute("vkmp");
Vector vc = (Vector)request.getAttribute("vc");
request.removeAttribute("vc");
String all_prodi = null;
if(vp!=null) {
	all_prodi =new String();
	ListIterator litmp = vp.listIterator();
	int counter = 0;
	while(litmp.hasNext()) {
		counter++;
		String bar = (String)litmp.next();
	//System.out.println(bar);
		StringTokenizer stt = new StringTokenizer(bar,"`");
		String kdprod = stt.nextToken();
		String singkatan = stt.nextToken();
		String kdjen = stt.nextToken();
		String nmprod = stt.nextToken();
		all_prodi = all_prodi+kdprod;
		if(litmp.hasNext()) {
			all_prodi = all_prodi+"`";
		}
	}			
}
else {
	all_prodi = new String("null"); 
}
String all_kmp = null;
if(vkmp!=null) {
	all_kmp = new String();
	ListIterator litmp = vkmp.listIterator();
	int counter = 0;
	while(litmp.hasNext()) {
		counter++;
		String bar = (String)litmp.next();
	//System.out.println(bar);
		StringTokenizer stt = new StringTokenizer(bar,"`");
		String kdkmp = stt.nextToken();
		String nmkmp = stt.nextToken();
		String nickkmp = stt.nextToken();
		all_kmp = all_kmp+kdkmp;
		if(litmp.hasNext()) {
			all_kmp = all_kmp+"`";
		}
	}	
}
else {
	all_kmp = new String("null"); 
}
//System.out.println("table_rule_name="+table_rule_name);
%>
<jsp:include page="../InnerMenu0_pageVersion.jsp" flush="true" />
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		
		</br>
        
		<!-- Column 1 start -->
<%
if(vListJabatan!=null && vListJabatan.size()>0) {
	ListIterator li = vListJabatan.listIterator();
	
	
%>		
		<div class="table-responsive">
		<form action="go.updateRegistrasiRules" align="center">
		<input type="hidden" name="form_urutan" value="true"/>
		<input type="hidden" name="target_thsms" value="<%=target_thsms%>"/>
		<input type="hidden" name="table_rule_name" value="<%=table_rule_name%>"/>
		<input type="hidden" name="scopeCmd" value="<%=scopeCmd%>"/>
<%
	if(vc!=null && vc.size()>0) {
		ListIterator lic = vc.listIterator();
	
	
%>
		<table class="table">	
			<thead>
				<tr>
        			<th colspan="3" style="text-align: left; padding: 0px 10px"><%=title_pengajuan %> <%=target_thsms %></th>
        		</tr>	
        		<tr>
        			
          			<th width="35%">PRODI</th>
          			<th width="15%">SCOPE KAMPUS</th>
          			<th width="50%">VERIFICATOR</th>
          			
        		</tr>
      		</thead>
      		<tbody>
<%
		while(lic.hasNext()) {
			String baris = (String)lic.next();
			//System.out.println(baris);
			StringTokenizer st = new StringTokenizer(baris,"`");
			String kdpst = st.nextToken();
			String jenjang = Converter.getDetailKdpst(kdpst);
			String tkn_job = st.nextToken();
			String job_id = st.nextToken();
			String urutan = st.nextToken();
			String kdkmp = st.nextToken();
%>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px"><%=Converter.getDetailKdpst_v1(kdpst) %></td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=kdkmp %></td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=tkn_job %></td>
				</tr>
<%			
		}
%>      		
      		</tbody>
		</table>
		</br/>
<%	
}
%>
	
		<table class="table">	
			<thead>
				<tr>
        			<th colspan="3" style="text-align: center; padding: 0px 10px; font-size:2em">FORM PENGATURAN <%=title_pengajuan %></th>
        		</tr>
        		<tr>
          			<th>JABATAN VERIFICATOR</th>
          			<th>SCOPE PRODI</th>
          			<th>SCOPE KAMPUS</th>
        		</tr>
      		</thead>
			<tbody>
				<tr>
					<td>
					<table class="table-noborder">	
						<tbody>
							<tr>
<%
	int counter = 0;
	while(li.hasNext()) {
		boolean match = false;
		counter++;
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String job = st.nextToken();
		String sin = st.nextToken();
		if(counter%3!=0) {
			//if(match) {
			if(form_urutan) {
					%>
								<td align="left" style="vertical-align: top;">
									<input type="text" style="width:15px;text-align:center" name="urutan_job" value="0"/> <%=job %>
									<input type="hidden" name="job" value="<%=job %>"/> 
								</td>
<%					
			}
			else {
%>
								<td align="left" style="vertical-align: top;"><input type="checkbox" name="job" value="<%=job %>" > <%=job %></td>
<%
			
			}	
		}
		else {
			if(form_urutan) {
				%>
								<td align="left" style="vertical-align: top;">
									<input type="text" style="width:15px;text-align:center" name="urutan_job" value="0"/> <%=job %>
									<input type="hidden" name="job" value="<%=job %>"/> 
								</td>
							</tr>
							<tr>
<%					
			}
			else {
	%>
								<td align="left" style="vertical-align: top;"><input type="checkbox" name="job" value="<%=job %>" > <%=job %></td>
							</tr>
							<tr>
						
	<%		
			}
		}
		if(!li.hasNext()) {
			%>
							</tr>
			<%		
		}
	}	
%>
						</tbody>	
					</table>
					</td>
					<td align="left" style="vertical-align: top;">					
<%
	if(vp!=null && vp.size()>0) {
%>
						<table class="table-noborder">	
							<tbody>
								<tr>
									<td align="left" style="vertical-align: top;"><input type="checkbox" name="prodi" value="<%=all_prodi %>" >SEMUA PRODI</td>
								</tr>
								<tr>
									<td style="border-bottom: 1px solid #2980B9;border-top: 1px solid #2980B9" colspan="3"> ATAU PILIH PRODI DIBAWAH INI:</td>
								</tr>
								<tr>		
<%		
		ListIterator litmp = vp.listIterator();
		counter = 0;
		while(litmp.hasNext()) {
			counter++;
			String bar = (String)litmp.next();
			//System.out.println(bar);
			StringTokenizer stt = new StringTokenizer(bar,"`");
			String kdprod = stt.nextToken();
			String singkatan = stt.nextToken();
			String kdjen = stt.nextToken();
			String nmprod = stt.nextToken();
		    
			kdjen = Converter.getDetailKdjen(kdjen);
			//check apa sudag ada value sebelumnya
			boolean match = false;
			//if(v_curr_val!=null && v_curr_val.size()>0){
			
			if(counter%2!=0) {
				%>
									<td><input type="checkbox" name="prodi" value="<%=kdprod%>" > <%=nmprod %> (<%=kdjen %>)</td>
<%	
			}
			else {
				
				%>
									<td><input type="checkbox" name="prodi" value="<%=kdprod%>" ><%=nmprod %> (<%=kdjen %>)</td>
								</tr>
								<tr>
				
<%			
	
			}
			if(!litmp.hasNext()) {
			
			%>
									
								</tr>
			<%		
			}
			
		}
%>
							</tbody>
						</table>	
<%		
	}
	else {
		out.print("TABEL PRODI BELUM DIISI");
	}
%>
					</td>
					<td align="left" style="vertical-align: top;">
<%
	if(vkmp!=null && vkmp.size()>0) {
%>
						<table class="table-noborder">	
							<tbody>
								<tr>
									<td ><input type="checkbox" name="kmp" value="<%=all_kmp %>" >SEMUA KAMPUS</td>
								</tr>
								<tr>
									<td style="border-bottom: 1px solid #2980B9;border-top: 1px solid #2980B9" colspan="3"> ATAU PILIH KAMPUS DIBAWAH INI:</td>
								</tr>
								<tr>		
<%		
		ListIterator litmp = vkmp.listIterator();
		counter = 0;
		while(litmp.hasNext()) {
			counter++;
			String bar = (String)litmp.next();
		//System.out.println(bar);
			StringTokenizer stt = new StringTokenizer(bar,"`");
			String kdkmp = stt.nextToken();
			String nmkmp = stt.nextToken();
			String nickkmp = stt.nextToken();
			//check apa sudag ada value sebelumnya
			boolean match = false;
			
			if(counter%1!=0) {
				//if(match) {
				%>
									<td><input type="checkbox" name="kmp" value="<%=kdkmp%>" > <%=nmkmp %></td>
<%		
			}	
			else {
				//if(match) {
				%>
									<td><input type="checkbox" name="kmp" value="<%=kdkmp%>" > <%=nmkmp %></td>
								</tr>
								<tr>
									
				<%
			}
			if(!litmp.hasNext()) {
			
			%>
									
								</tr>
			<%		
			}
			
		}
%>
							</tbody>
						</table>	
<%		
	}
	else {
		out.print("TABEL KAMPUS BELUM DIISI");
	}
%> 
					</td>
				</tr>
				<tr>
					<td colspan="4" style="padding:5px 0px">
						<section class="gradient">
	            			<button style="padding: 5px 50px;font-size: 20px;">Update Data</button>
        				</section>
					</td>			
				</tbody>	
			</table>	
			
		</form>	
		
<%
}
else {
	out.print("TABEL JABATAN BELUM DIISI HARAP HUBUNGI ADMIN");
}
%>
		
		<!-- Column 1 start -->

		
	</div>
</div>		
</body>
</html>
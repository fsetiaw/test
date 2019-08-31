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
	String target_thsms = Checker.getThsmsInputNilai(); 
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector v = (Vector)request.getAttribute("v_kdpst_nilai");
	String atMenu = request.getParameter("atMenu");
	String scopeCmd = request.getParameter("scopeCmd");
	Vector vp = (Vector)request.getAttribute("vprodi");
	request.removeAttribute("vp");
//	Vector vkmp = (Vector)request.getAttribute("vkmp");
	request.removeAttribute("vkmp");
	String all_prodi = null;
	String all_kmp = null;
	//String all_prodi = null;
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
	/*String all_kmp = null;
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
	*/
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
String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);

%>
<jsp:include page="<%= url %>" flush="true" />
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		
		</br>
        
		<!-- Column 1 start -->
<%
if(v!=null && v.size()>0) {
	
%>	
		<div class="table-responsive">
		<form action="go.updateBobotNilai">
		<table class="table">	
			<thead>
				<tr>
        			<th colspan="3" style="text-align: left; padding: 0px 10px; font-size:2em">TABEL BOBOT NILAI <%=target_thsms %> </th>
        		</tr>	
        		<tr>
        			
          			<th style="text-align: left; padding: 0px 10px;">KAMPUS</th>
          			<th style="text-align: left; padding: 0px 10px;">PRODI</th>
          			<th style="text-align: left; padding: 0px 10px;">NILAI / BOBOT</th>			
        		</tr>
      		</thead>
      		<tbody>
<%
	ListIterator li = v.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		//System.out.println(brs);
		String kdpst = st.nextToken();
		String nmpst = st.nextToken();
		String kdjen = st.nextToken();
		String kdkmp = st.nextToken();
		String idobj = st.nextToken();
		//out.println(brs+"<br/>");

%>
				<tr>
					<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=kdkmp %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px"><%=nmpst %>&nbsp(<%=kdjen %>)</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px">
<%
		int counter = 0;
		String list_nilai = "";
		while(st.hasMoreTokens()) {
			counter++;
			String nilai = st.nextToken();
			String bobot = st.nextToken();
			if(counter<13) {
				list_nilai = list_nilai+"["+nilai+"/"+bobot+"]&nbsp";	
			}
			else {
				list_nilai = list_nilai+"["+nilai+"/"+bobot+"]<br>";
				counter=0;
			}
			
		}
%>					
					<%=list_nilai %>
					</td>
					
				</tr>
<%		
	}
%>    		
      		</tbody>
		</table>
<br/>
<%	
}
%>

	</table>
	<br/>
	
		<input type="hidden" name="target_thsms" value="<%=target_thsms %>"/>
		<input type="hidden" name="atMenu" value="<%="atMenu" %>"/>
		<table class="table">	
			<thead>
				<tr>
        			<th colspan="1" style="text-align: center; padding: 0px 10px;font-size:1.5em">FORM INPUT BOBOT NILAI <%=target_thsms %></th>
        		</tr>	
        		<tr>
        			
          			<th width="70%">SCOPE PRODI</th>
          			
          			
        		</tr>
      		</thead>
      		<tbody>

				<tr>
				
					<td align="left" style="vertical-align: top; padding: 0px 5px">
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
		    
			kdjen = Converter.getDetailKdjen(kdjen);
			//check apa sudag ada value sebelumnya
			boolean match = false;
			//if(v_curr_val!=null && v_curr_val.size()>0){
			
			if(counter%3!=0) {
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

					
				</tr>
				
				<tr>
					<td colspan="1" style="background:<%=Constant.darkColorBlu()%>;color:white;text-align: center; padding: 0px 10px;font-size:1.5em">ISIKAN DAFTAR BOBOT NILAI YANG BARU </td>
    			</tr>
    			<tr>
    				<td colspan="1" style="text-align:center">
						<input style="width:90%" type="text" name="list_bobot" placeholder="[NLAKH/BOBOT/NILAI MIN/NILAI_MAX][...." />
					</td>
    			</tr>
    			<tr>
					<td colspan="1" style="background:<%=Constant.darkColorBlu()%>;color:white;text-align: center; padding: 0px 10px;font-size:1.5em">
						<input type="submit" value="UPDATE BOBOT NILAI" style="width:40%;height:35px;padding:5px 5px" />
					</td>
    			</tr>
      		</tbody>
		</table>
		</div>
		</form>
		</br/>
	
		<!-- Column 1 start -->

		
	</div>
</div>		
</body>
</html>
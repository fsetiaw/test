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
<title>Insert title here</title>
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
boolean form_urutan = false;
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v_struk = (Vector) request.getAttribute("v_struk");
JSONArray jsoa_struk_grp_chat = (JSONArray) request.getAttribute("jsoa_struk_grp_chat");
request.removeAttribute("v_struk");
request.removeAttribute("jsoa_struk_grp_chat");
Vector vListJabatan = (Vector)request.getAttribute("vListJabatan");
request.removeAttribute("vListJabatan");
Vector vp = (Vector)request.getAttribute("vp");
request.removeAttribute("vp");
Vector vkmp = (Vector)request.getAttribute("vkmp");
request.removeAttribute("vkmp");
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
<jsp:include page="../InnerMenu0_pageVersion.jsp" flush="true" />
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
<div class="table-responsive">
<form action="go.updChatAssignment" align="center">
<%
if(vListJabatan!=null && vListJabatan.size()>0) {
	ListIterator li = vListJabatan.listIterator();
%>
		<table class="table">	
			<thead>
				<tr>
        			<th colspan="3" style="text-align: center; padding: 0px 10px; font-size:2em">FORM PENGATURAN STRUKTURAL GROUP CHAT</th>
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
						PILIH GROUP CHAT : 
						<select name="grp_chat" style="width:100%;height:35px">
							<option value="null">N/A</option>
<%
	if(jsoa_struk_grp_chat!=null && jsoa_struk_grp_chat.length()>0) {
		for(int i=0;i<jsoa_struk_grp_chat.length();i++) {
			JSONObject job = jsoa_struk_grp_chat.getJSONObject(i);
			String list_prodi = null;
			String list_kmp = null;
			String grp_id = "null";
			String grp_tp = "null";
			String grp_nm = "null";
			String grp_nick = "null";
			String grp_kdpst = "null";
			String grp_kmp = "null";
			String list_member_id = "null";
			String list_member_npm = "null";
			try {
				//grp_id+"`"+grp_tp+"`"+grp_nm+"`"+grp_nk+"`"+kdpst+"`"+kmp+"`"+mem_id+"`"+mem_npm;
				String tmp = (String)job.get("INFO");
				StringTokenizer st = new StringTokenizer(tmp,"`");
				grp_id =  st.nextToken();
				grp_tp =  st.nextToken();
				grp_nm = st.nextToken();
				grp_nick = st.nextToken();
				grp_kdpst = st.nextToken();
				grp_kdpst=grp_kdpst.replace("&#x5d;&#x5b;", ",");
				grp_kdpst=grp_kdpst.replace("&#x5b;", "");
				grp_kdpst=grp_kdpst.replace("&#x5d;", "");
				
				grp_kmp = st.nextToken();
				grp_kmp=grp_kmp.replace("&#x5d;&#x5b;", ",");
				grp_kmp=grp_kmp.replace("&#x5b;", "");
				grp_kmp=grp_kmp.replace("&#x5d;", "");
				list_member_id = st.nextToken();
				list_member_npm = st.nextToken();
				
				
				
				//out.print(value+"</br>");
			}
			catch(JSONException e) {}//ignore
			
			StringTokenizer st = new StringTokenizer(grp_kdpst,",");
			if(grp_kdpst.equalsIgnoreCase("ALL")) {
				list_prodi = "[ALL]";
			}
			else {
				while(st.hasMoreTokens()) {
					if(list_prodi==null) {
						list_prodi = new String("[");
					}
					String tkn_kdpst = st.nextToken();
					//System.out.println("tkn_kdpst="+tkn_kdpst);
					list_prodi=list_prodi+Converter.getDetailKdpst_v1(tkn_kdpst);
					if(st.hasMoreTokens()) {
						list_prodi=list_prodi+"/";
					}
					else {
						list_prodi=list_prodi+"]";
					}
				}
			}
			
			st = new StringTokenizer(grp_kmp,",");
			if(grp_kmp.equalsIgnoreCase("ALL")) {
				list_kmp = "[ALL]";
			}
			else {
				while(st.hasMoreTokens()) {
					if(list_kmp==null) {
						list_kmp = new String("[");
					}
					String tkn_kmp = st.nextToken();
					
					list_kmp=list_kmp+Converter.getNamaKampus(tkn_kmp);
					if(st.hasMoreTokens()) {
						list_kmp=list_kmp+"/";
					}
					else {
						list_kmp=list_kmp+"]";
					}
				}
			}
			
%>
							<option value="<%=grp_id%>"><%=grp_nm +" "+list_prodi+" "+list_kmp%> </option>
<%			
		}	
	}
%>													
						</select>
					</td>	
				</tr>		
				<tr>
					<td colspan="4" style="padding:5px 0px">
						<section class="gradient">
	            			<button style="padding: 5px 50px;font-size: 20px;">Update Data</button>
        				</section>
					</td>	
				</tr>			
				</tbody>	
			</table>	
<%
}
%>	
<br/>
<%

/*
			
*/
int table_no = 0;
if(jsoa_struk_grp_chat!=null && jsoa_struk_grp_chat.length()>0) {
%>
<br/>
	<br/>
	<center>
	
	<table class="table">	
	<tbody>	
        <tr>
 			<td style="width:50%;height:100%">
<%
	int i = 0;
	for(i=0;i<jsoa_struk_grp_chat.length();i++) {
		JSONObject job = jsoa_struk_grp_chat.getJSONObject(i);
		String value = "null";
		try {
				
			table_no++;
			//grp_id+"`"+grp_tp+"`"+grp_nm+"`"+grp_nk+"`"+kdpst+"`"+kmp+"`"+mem_id+"`"+mem_info;
			value = ""+(String)job.get("INFO");
			StringTokenizer st = new StringTokenizer(value,"`");
			String grp_id = st.nextToken();
			String grp_tp = st.nextToken();
			String grp_nm = st.nextToken();
			String grp_nk = st.nextToken();
			String kdpst = st.nextToken();
			String kmp = st.nextToken();
			String mem_id = st.nextToken();
			String mem_info = st.nextToken();
				
%>
	
      	
	
		<table class="table" width="100%" height="100%">	
			<thead>
				<tr>			
        			<th colspan="4" style="text-align: left; padding: 10px 10px"><%=grp_nm %> <br/>
        			<%
        	String list = "";
        	if(!kdpst.equalsIgnoreCase("ALL")) {
        		st = new StringTokenizer(kdpst,",");
        		while(st.hasMoreTokens()) {
        			list = list + Converter.getDetailKdpst_v1(st.nextToken());
        			if(st.hasMoreTokens()) {
        				list = list+" / ";
        			}
        		}
        		out.print(list);
        	}
        			%>
        			</th>
        		</tr>	
        	</thead>
        	<tbody>	
        		<tr>
        			<td style="width:100%;height:100%">
<%
			if(!mem_info.equalsIgnoreCase("null,null")) {
			
			
				st = new StringTokenizer(mem_info,",");
				int no = 0;
				while(st.hasMoreTokens()) {
					no++;
					String npm = st.nextToken();
					String nmm = st.nextToken();
					out.print(no+". "+nmm+"<br/>");
				}	
			}
			else {
				out.print("BELUM ADA MEMBER");
			}
			%>
					</td>
				</tr>	        		
      		</tbody>
      	</table>	
   </center>   		
<%
		}
		catch(JSONException e) {}//ignore
	 	if(table_no%2!=0) {
	 		%>	
	 		</td><td>
	 		<%		
	 	}
	 	else {
	 		%>	
	 		</td></tr><tr><td>
	 		<%		
	 	}
	 	if(i+1>=jsoa_struk_grp_chat.length()) {
	 		%>	
	 		</td></tr>
	 		<%	
	 	}
	}
		

%>
</tbody>
</table>	
		
<% 	
}



%>

		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>
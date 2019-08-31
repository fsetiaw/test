<!DOCTYPE html>
<%@page import="org.apache.catalina.util.Conversions"%>
<html>
<head>
<title>Insert title here</title>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
  
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>


<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String listKurAndSelected = ""+request.getParameter("listKurAndSelected");
//System.out.println("edit listKurAndSelected="+listKurAndSelected);
boolean profilePage = false;
boolean dashPage = false;
boolean keuPage = false;
Vector v= null; 
Vector vListDsn = (Vector) session.getAttribute("vListDsn");
session.removeAttribute("vListDsn");
String listTipeObj = (String)request.getAttribute("listTipeObj");
//idObj+"$"+kdpst+"$"+objName+"$"+objDesc+"$"+objLvl+"$"+acl+"$"+al+"$"+dvalue+"$"+nicknm;
request.removeAttribute("listTipeObj");
String curPa = (String)request.getAttribute("curPa");
//System.out.println("11curPacurPa="+curPa);
request.removeAttribute("curPa");
boolean objMhs = false;
if(validUsr.getObjNickNameGivenObjId().contains("MHS")||validUsr.getObjNickNameGivenObjId().contains("mhs")) {
	objMhs = true;
}

Vector vListJabatan = (Vector)request.getAttribute("vListJabatan");
request.removeAttribute("vListJabatan");
Vector vp = (Vector)request.getAttribute("vp");
request.removeAttribute("vp");
Vector vkmp = (Vector)request.getAttribute("vkmp");
request.removeAttribute("vkmp");
Vector v_curr_val = (Vector)request.getAttribute("v_curr_val");
request.removeAttribute("v_curr_val");
//request.setAttribute("v_curr_val", v_curr_val);
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
	
//ListIterator likmp = vkmp.listIterator();
//while(likmp.hasNext()) {
//	//System.out.println((String)likmp.next());
//}
//vp.listIterator();

//System.out.println("objMhs ="+objMhs);
/*
v = (Vector) request.getAttribute("v_profile");
String v_id_obj = (String)request.getAttribute("v_id_obj");
String v_nmmhs=(String)request.getAttribute("v_nmmhs");
String v_npmhs=(String)request.getAttribute("v_npmhs");
String v_nimhs=(String)request.getAttribute("v_nimhs");
String v_obj_lvl=(String)request.getAttribute("v_obj_lvl");
String v_kdpst=(String)request.getAttribute("v_kdpst");
String v_kdjen=(String)request.getAttribute("v_kdjen");
String v_smawl=(String)request.getAttribute("v_smawl");
String v_tplhr=(String)request.getAttribute("v_tplhr");
String v_tglhr=(String)request.getAttribute("v_tglhr");
String v_aspti=(String)request.getAttribute("v_aspti");
String v_aspst=(String)request.getAttribute("v_aspst");
String v_btstu=(String)request.getAttribute("v_btstu");
String v_kdjek=(String)request.getAttribute("v_kdjek");
String v_nmpek=(String)request.getAttribute("v_nmpek");
String v_ptpek=(String)request.getAttribute("v_ptpek");
String v_stmhs=(String)request.getAttribute("v_stmhs");
String v_stpid=(String)request.getAttribute("v_stpid");
String v_sttus=(String)request.getAttribute("v_sttus");
String v_email=(String)request.getAttribute("v_email");
String v_nohpe=(String)request.getAttribute("v_nohpe");
String v_almrm=(String)request.getAttribute("v_almrm");
String v_kotrm=(String)request.getAttribute("v_kotrm");
String v_posrm=(String)request.getAttribute("v_posrm");
String v_telrm=(String)request.getAttribute("v_telrm");
String v_almkt=(String)request.getAttribute("v_almkt");
String v_kotkt=(String)request.getAttribute("v_kotkt");
String v_poskt=(String)request.getAttribute("v_poskt");
String v_telkt=(String)request.getAttribute("v_telkt");
String v_jbtkt=(String)request.getAttribute("v_jbtkt");
String v_bidkt=(String)request.getAttribute("v_bidkt");
String v_jenkt=(String)request.getAttribute("v_jenkt");
String v_nmmsp=(String)request.getAttribute("v_nmmsp");
String v_almsp=(String)request.getAttribute("v_almsp");
String v_possp=(String)request.getAttribute("v_possp");
String v_kotsp=(String)request.getAttribute("v_kotsp");
String v_negsp=(String)request.getAttribute("v_negsp");
String v_telsp=(String)request.getAttribute("v_telsp");
String v_neglh=(String)request.getAttribute("v_neglh");
String v_agama=(String)request.getAttribute("v_agama");
*/
%>
<style>
.table { border: 1px solid #2980B9;background:#d9e1e5 }
.table thead > tr > th { border-bottom: none;background:#369;color:white;font-weight:bold }
.table thead > tr > th, .table tbody > tr > th, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9; }

.table-noborder { border: none;background:#d9e1e5 }
.table-noborder thead > tr > th { border-bottom: none;background:#369;color:white;font-weight:bold }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px }

</style>


</head>
<body>
<div id="header">
<%
//if(validUsr.iAmStu()) {
	
%>
<%@ include file="../profileInnerMenu.jsp" %>
<%	
//}

//else {
%>
<!--  %@ include file="../innerMenu.jsp" % -->
<%
//}
String scope_cmd = request.getParameter("cmd");
boolean readonly = true;
readonly = validUsr.isHakAksesReadOnly(scope_cmd);
%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<!-- Column 1 start -->

		<br />
<%
if(vListJabatan!=null && vListJabatan.size()>0) {
	ListIterator li = vListJabatan.listIterator();
	int no=1;
	if(!readonly) {
%>		
		<form action="go.updateJabatan" align="center">
<%
	}
%>		
			<input type="hidden" value="<%=v_id_obj %>" name="v_id_obj" /><input type="hidden" value="<%=v_obj_lvl %>" name="v_obj_lvl" />
			<input type="hidden" value="<%=v_nimhs %>" name="v_nimhs" /><input type="hidden" value="<%=v_npmhs %>" name="v_npmhs" /><input type="hidden" value="<%=v_kdpst %>" name="v_kdpst" />
			
		<div class="table-responsive">
	
		<table class="table">	
			<thead>
        		<tr>
         			<th>NO</th>
          			<th>NAMA JABATAN</th>
          			<th>SCOPE PRODI</th>
          			<th>SCOPE KAMPUS</th>
        		</tr>
      		</thead>
			<tbody>
       
      
<%
	while(li.hasNext()) {
%>	
				<tr>
					<td align="center" style="padding-left:2px"><%=no++ %> </td>
<%
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String job = st.nextToken();
		String sin = st.nextToken();
%>
					<td align="left" style="padding-left:2px"><%=job %></td>
					<td align="left" >
<%
		if(vp!=null && vp.size()>0) {
%>
						<table class="table-noborder">	
							<tbody>
								<tr>
									<td ><input type="checkbox" name="prodi" value="<%=job %>`<%=all_prodi %>" >SEMUA PRODI</td>
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
				if(v_curr_val!=null && v_curr_val.size()>0){
					ListIterator li1 = v_curr_val.listIterator();
					while(li1.hasNext() && !match) {
						String baris = (String)li1.next();
						//System.out.println("baris-"+baris);
						StringTokenizer st2 = new StringTokenizer(baris,"`");
						String nmjob = st2.nextToken();
						String prod = st2.nextToken();
						String kampus = st2.nextToken();
						if(job.equalsIgnoreCase(nmjob) && kdprod.equalsIgnoreCase(prod)) {
							match = true;
						}
						//li.add(nmjob+"`"+kdpst+"`"+kdkmp);
					}
				}
				if(counter%3!=0) {
					if(match) {
%>
									<td><input type="checkbox" name="prodi" value="<%=job %>`<%=kdprod%>" checked="checked"> <%=nmprod %> (<%=kdjen %>)</td>
<%						
					}
					else {
%>
									<td><input type="checkbox" name="prodi" value="<%=job %>`<%=kdprod%>" > <%=nmprod %> (<%=kdjen %>)</td>
<%
					}
				}
				else {
					if(match) {
						%>
									<td><input type="checkbox" name="prodi" value="<%=job %>`<%=kdprod%>" checked="checked"><%=nmprod %> (<%=kdjen %>)</td>
								</tr>
								<tr>
						
	<%						
					}
					else {
				%>
									<td><input type="checkbox" name="prodi" value="<%=job %>`<%=kdprod%>" ><%=nmprod %> (<%=kdjen %>)</td>
								</tr>
								<tr>
									
				<%			
					}
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
					<td align="left" style="padding-left:2px">
<%
		if(vkmp!=null && vkmp.size()>0) {
%>
						<table class="table-noborder">	
							<tbody>
								<tr>
									<td ><input type="checkbox" name="kmp" value="<%=job %>`<%=all_kmp %>" >SEMUA KAMPUS</td>
								</tr>
								<tr>
									<td style="border-bottom: 1px solid #2980B9;border-top: 1px solid #2980B9" colspan="3"> ATAU PILIH KAMPUS DIBAWAH INI:</td>
								</tr>
								<tr>		
<%		
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
				//check apa sudag ada value sebelumnya
				boolean match = false;
				if(v_curr_val!=null && v_curr_val.size()>0){
					ListIterator li1 = v_curr_val.listIterator();
					while(li1.hasNext() && !match) {
						String baris = (String)li1.next();
						//System.out.println("baris-"+baris);
						StringTokenizer st2 = new StringTokenizer(baris,"`");
						String nmjob = st2.nextToken();
						String prod = st2.nextToken();
						String kampus = st2.nextToken();
						if(job.equalsIgnoreCase(nmjob) && kdkmp.equalsIgnoreCase(kampus)) {
							match = true;
						}
						//li.add(nmjob+"`"+kdpst+"`"+kdkmp);
					}
				}
				if(counter%1!=0) {
					if(match) {
%>
									<td><input type="checkbox" name="kmp" value="<%=job %>`<%=kdkmp%>" checked="checked"> <%=nmkmp %></td>
<%						
					}
					else {
%>
									<td><input type="checkbox" name="kmp" value="<%=job %>`<%=kdkmp%>" > <%=nmkmp %></td>
<%			
					}
				}	
				else {
					if(match) {
						%>
									<td><input type="checkbox" name="kmp" value="<%=job %>`<%=kdkmp%>" checked="checked"> <%=nmkmp %></td>
						<%						
					}
					else {
				%>
									<td><input type="checkbox" name="kmp" value="<%=job %>`<%=kdkmp%>" > <%=nmkmp %></td>
								</tr>
								<tr>
									
				<%
					}
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
<%
	}
	if(!readonly) {
%>	
				<tr>
					<td colspan="4"><input type="submit" class="btn btn-primary" value="Update Jabatan"></td>		
				</tr>
<%
	}
%>						
				</tbody>	
			</table>	
			</div>
		</form>	
<%
}
else {
	out.print("TABEL JABATAN BELUM DIISI HARAP HUBUNGI ADMIN");
}
%>		
	</div>	
</div>		
</body>
</html>
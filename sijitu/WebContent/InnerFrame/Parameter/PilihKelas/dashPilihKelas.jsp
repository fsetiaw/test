<!DOCTYPE html>
<head>
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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%
	//System.out.println("okeh");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	//String tipeForm = request.getParameter("formType");
	//Vector vDwn = validUsr.getScopeUpd7des2012("hasDownloadMenu");
	//Vector v_cf = validUsr.getScopeUpd7des2012("hasEditMkKurikulum");
%>


</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">

<%
String target_thsms = Checker.getThsmsKrs();
AddHocFunction adf = new AddHocFunction();
adf.getListObjIdYgBlumAdaDiPilihanCakupanKelas(target_thsms);
String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);
JSONArray jsoa_info_akses = (JSONArray) request.getAttribute("jsoa");
String target_param = null;
String scope_prodi = null;
String scope_kmp = null;
String hak_akses = null;
if(jsoa_info_akses!=null && jsoa_info_akses.length()>0) {
	for(int i=0;i<jsoa_info_akses.length();i++) {
		JSONObject job = jsoa_info_akses.getJSONObject(i);
		//String brs = "";
		try {
			target_param = (String)job.get("ACCESS_LEVEL");
			scope_prodi = (String)job.get("ACCESS_LEVEL_CONDITIONAL"); 	
			hak_akses = (String)job.get("HAK_AKSES"); 	
			scope_kmp = (String)job.get("SCOPE_KAMPUS"); 	
			
   		}
   		catch(JSONException e) {}//ignore
   		//out.println(i+"."+brs+"<br/>");
	}	
}
else {
	//redirect = no access at alll
}

//String inner_menu = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0.jsp";
//out.print(inner_menu);
//out.print(url);
boolean editable = false;
if(hak_akses.contains("e") || hak_akses.contains("i")) {
	editable = true;
}
else if(!hak_akses.contains("r")) {
	//redirect - tidak punya akses baca
}
%>


<jsp:include page="../InnerMenu0_pageVersion.jsp" flush="true" />
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<br/>
		<%
if(editable)  {
			
			%>
		<br/>
		<br/>
		<form action="go.updPilihKelasRules" method="post">
			<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
				<tr>		
					<td style="background:#369;color:#fff;text-align:center;width:500px;valign:middle;font-size:1.5em" >
						COPY DARI THSMS SEBELUMNYA
					</td>	
					<td style="background:#369;color:#fff;text-align:center;width:150px">
						<input type="text" name="thsms_base" style="width:150px;height:35px;"/>
					</td>
					<td style="background:#369;color:#fff;text-align:center;height:35px;width:150px" >
						<input type="submit" name="submit" value="COPY" style="width:140px;height:30px;cellpadding:5px" />
					</td>
				</tr>
			</table>
			
		<br/> 	
		

			<%
}
		
		//String thsms = "";
		
		JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/pilih_kelas_rules/search/"+target_thsms);
		
		//JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/pilih_kelas_rules/search/20142");
		//System.out.println("jsoa leng="+jsoa.length());
		if(jsoa==null) {
			System.out.println("jsoa null");
		}
		else {
			if(jsoa.length()<1) {
				System.out.println("jsoa empty");
			}
		}
		if(jsoa!=null && jsoa.length()>0) {
			int no = 1;
			//System.out.println("jsoa ="+jsoa.toString());
			String kdpst = "";
			String id_obj_mhs = "";
			String shift_only = "";
			String tkn_shift = "";
			String all_shift = "";
			String all_prodi = "";
			String tkn_prodi = "";
			String all_fak = "";
			String tkn_fak = "";
			String all_kmp = "";
			String tkn_kmp = "";
			String npm_whitelist_shift = "";
			String npm_whitelist_prodi = "";
			String npm_whitelist_fak = "";
			String npm_whitelist_kmp = "";
			String kode_kmp = "";
	%>		
			<br/>
			<br/>
			<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
				<input type="hidden" name="thsms_target" value="<%=target_thsms%>"/>
		<%
					
			for(int i=0;i<jsoa.length();i++) {
				JSONObject job = jsoa.getJSONObject(i);
				//System.out.println("jobi="+job.toString());
				// brs = job.t;
				//int brs = 0;
				int j=1;
				try {
						
					//1
					if(job.isNull("KDPST")) {
						kdpst = "null";
					}
					else {	
						kdpst = ""+(String)job.get("KDPST");
					}
					//System.out.println(j+++"."+kdpst);
					//2
					if(job.isNull("ID_OBJ_MHS")) {
						id_obj_mhs = "null";
					}
					else {	
						id_obj_mhs = ""+(Integer)job.get("ID_OBJ_MHS");
					}
					//System.out.println(j+++"."+id_obj_mhs);
					
					//3
					if(job.isNull("SHIFT_ONLY")) {
						shift_only = "null";
					}
					else {	
						shift_only = ""+(String)job.get("SHIFT_ONLY"); 
					}
					//System.out.println(j+++"."+shift_only);
					
					//4
					if(job.isNull("ALL_SHIFT")) {
						all_shift = "null";
					}
					else {	
						all_shift = ""+job.get("ALL_SHIFT");
					}
					//5
					if(job.isNull("ALL_PRODI")) {
						all_prodi = "null";
					}
					else {	
						all_prodi = ""+job.get("ALL_PRODI");
					}
					//6
					if(job.isNull("TKN_PRODI")) {
						tkn_prodi = "null";
					}
					else {	
						tkn_prodi = ""+job.get("TKN_PRODI");
					}
					//7
					if(job.isNull("ALL_FAKULTAS")) {
						all_fak = "null";
					}
					else {	
						all_fak = ""+(String)job.get("ALL_FAKULTAS");
					}
					//8
					if(job.isNull("TKN_FAKULTAS")) {
						tkn_fak = "null";
					}
					else {	
						tkn_fak = ""+(String)job.get("TKN_FAKULTAS");
					}
					//9
					if(job.isNull("ALL_KAMPUS")) {
						all_kmp = "null";
					}
					else {	
						all_kmp = ""+(String)job.get("ALL_KAMPUS");
					}
					//10
					if(job.isNull("TKN_KAMPUS")) {
						tkn_kmp = "null";
					}
					else {	
						tkn_kmp = ""+(String)job.get("TKN_KAMPUS");
					}
					//11
					if(job.isNull("NPMHS_WHITELIST_SHIFT")) {
						npm_whitelist_shift = "null";
					}
					else {	
						npm_whitelist_shift = ""+(String)job.get("NPMHS_WHITELIST_SHIFT");
					}
					//12
					if(job.isNull("NPMHS_WHITELIST_PRODI")) {
						npm_whitelist_prodi = "null";
					}
					else {	
						npm_whitelist_prodi = ""+(String)job.get("NPMHS_WHITELIST_PRODI");
					}
					//13
					if(job.isNull("NPMHS_WHITELIST_FAK")) {
						npm_whitelist_fak = "null";
					}
					else {	
						npm_whitelist_fak = ""+(String)job.get("NPMHS_WHITELIST_FAK");
					}
					//14
					if(job.isNull("NPMHS_WHITELIST_KMP")) {
						npm_whitelist_kmp = "null";
					}
					else {	
						npm_whitelist_kmp = ""+(String)job.get("NPMHS_WHITELIST_KMP");
					}
					//15
					if(job.isNull("KODE_KAMPUS")) {
						kode_kmp = "null";
					}
					else {	
						kode_kmp = ""+(String)job.get("KODE_KAMPUS");
					}
					//16
					if(job.isNull("TKN_SHIFT")) {
						tkn_shift = "null";
					}
					else {	
						tkn_shift = ""+(String)job.get("TKN_SHIFT");
					}

					//get info objek yg termasuk pada prodi terkait
					JSONArray jsoa2 = Getter.readJsonArrayFromUrl("/v1/search_obj_type/kdpst/"+kdpst);
					//System.out.println("kdpst="+kdpst);
					//System.out.println("jsoa2="+jsoa2.toString());
					String obj_nickname = "";
					String obj_id = "";
					if(jsoa2!=null && jsoa2.length()>0) {
						for(int k=0;k<jsoa2.length();k++) {
							JSONObject job2 = jsoa2.getJSONObject(k);
							
							try {
								if(job2.isNull("ID_OBJ")) {
									obj_id = obj_id+"`null";
								}
								else {	
									obj_id = obj_id+"`"+(Integer)job2.get("ID_OBJ");
								}
								if(job2.isNull("OBJ_NICKNAME")) {
									obj_nickname = obj_nickname+"`null";
								}
								else {	
									obj_nickname = obj_nickname+"`"+(String)job2.get("OBJ_NICKNAME");
								}
							}
							catch(JSONException e) {}//ignore	
						}
					}
					//System.out.println("obj_nickname="+obj_nickname);
					//System.out.println("obj_id="+obj_id);
					
					%>					
				<tr>
					<td style="width:25px;background:#369;color:#fff;text-align:center;" valign="top" rowspan="10" align="center"><h3><%=no++ %>.</h3></td>
					<td style="background:#369;color:#fff;text-align:center;width:100%;valign:top" colspan="4" >
						<h3> 
						<input type="hidden" name="kdpst" value="<%=kdpst%>"/>
						<input type="hidden" name="id_obj_mhs" value="<%=id_obj_mhs%>"/>
						<%
						String obj_nick_name = Checker.getObjNickname(Integer.parseInt(id_obj_mhs));
						out.print(obj_nick_name);
						%>
						</h3>
					</td>
				</tr>
				<tr>
					<td style="width:100px;padding:3px" >ALL SHIFT</td>
					<td style="width:150px;background:white" >
						<%
					if(editable) {
						if(all_shift!=null && all_shift.equalsIgnoreCase("1")) {
						%>
						<input type="radio" name="all_shift_<%=id_obj_mhs %>" value="true" checked>Ya<br>
						<input type="radio" name="all_shift_<%=id_obj_mhs %>" value="false" >Tidak
						<%
						}
						else {
						%>
						<input type="radio" name="all_shift_<%=id_obj_mhs %>" value="true" >Ya<br>
						<input type="radio" name="all_shift_<%=id_obj_mhs %>" value="false" checked>Tidak
						<%	
						}
					}
					else {
						out.print(all_shift);
					}
						%>
					</td>
					<td style="width:175px;padding:3px" >LIST SHIFT ALLOW</td>
					<td style="width:350px;" >
						<%
					if(editable) {
						%>
						<textarea rows="2" style="width:98%" name="tkn_shift_<%=id_obj_mhs %>"><%=Checker.pnn(tkn_shift)%></textarea>
						<%	
					}
					else {
						
						out.print(Checker.pnn(tkn_shift));
					}
						%>
					</td>
				</tr>
				<tr>
					<td style="padding:3px" colspan="2">LIST NPM REVERSE SHIFT</td>
					<td style="padding:3px;" colspan="2">
						<%
					if(editable) {
						%>
						<textarea rows="2" style="width:98%" name="list_npm_shift_<%=id_obj_mhs %>"><%=Checker.pnn(npm_whitelist_shift)%></textarea>
						<%	
					}
					else {
						out.print(Checker.pnn(npm_whitelist_shift));
					}
						%>
					</td>
				</tr>
				<tr>
					<td style="padding:3px" colspan="2">LIST SCOPE OBJECT ID</td>
					<td style="padding:3px;" colspan="2">
						<%
					if(editable) {

						StringTokenizer st1 = new StringTokenizer(obj_nickname,"`");
						StringTokenizer st2 = new StringTokenizer(obj_id,"`");
						boolean match = false ; //kalo ngga match berearti valuenya nilai semua jd no checkbox
						while(st1.hasMoreTokens()) {
							 
							String nick_val = st1.nextToken();
							String obj_id_val = st2.nextToken();
							
							if(!Checker.isStringNullOrEmpty(nick_val)) {
								match= true;
								String kd_kmp = Getter.getDomisiliKampus(Integer.parseInt(obj_id_val));
								//obj_id_val = st2.nextToken();
								if(obj_nick_name.equalsIgnoreCase(nick_val)) {
						%>
							<input type="checkbox" name="list_objid_<%=id_obj_mhs %>" value="<%=obj_id_val+"-"+kd_kmp%>" checked="checked"><%=nick_val+" "+obj_id_val %><br>
						<%
								}
								else {
									/*
									%>
							<input type="checkbox" name="list_objid_<%=id_obj_mhs %>" value="<%=obj_id_val+"-"+kd_kmp%>"><%=nick_val+" "+obj_id_val %><br>
								<%
								*/
								}
							}
						}
						if(!match) {
							out.print("kososnsg");	
						}
						
					}
					else {
						
					}
						%>
					</td>
				</tr>
				<tr>
					<td style="width:100px;padding:3px" >ALL PRODI</td>
					<td style="width:150px;background:white" >
						<%
						//System.out.println("ali prodi="+all_prodi);
					if(editable) {
						if(all_prodi!=null && all_prodi.equalsIgnoreCase("1")) {
						%>
						<input type="radio" name="all_prodi_<%=id_obj_mhs %>" value="true" checked>Ya<br>
						<input type="radio" name="all_prodi_<%=id_obj_mhs %>" value="false" >Tidak
						<%
						}
						else {
						%>
						<input type="radio" name="all_prodi_<%=id_obj_mhs %>" value="true" >Ya<br>
						<input type="radio" name="all_prodi_<%=id_obj_mhs %>" value="false" checked>Tidak
						<%	
						}	
					}
					else {
						out.print(all_prodi);
					}
						%>
					</td>
					<td style="width:175px;padding:3px" >LIST KODE PRODI ALLOW</td>
					<td style="width:350px;" >
						<%
					if(editable) {
						%>
						<textarea rows="2" style="width:98%" name="tkn_prodi_<%=id_obj_mhs %>"><%=Checker.pnn(tkn_prodi)%></textarea>
						<%	
					}
					else {
						out.print(Checker.pnn(tkn_prodi));
					}
						%>
					</td>
				</tr>
				</tr>
					<td style="padding:3px" colspan="2">LIST NPM REVERSE PRODI</td>
					<td style="padding:3px;" colspan="2">
						<%
					if(editable) {
						%>
						<textarea rows="2" style="width:98%" name="list_npm_prodi_<%=id_obj_mhs %>"><%=Checker.pnn(npm_whitelist_prodi)%></textarea>
						<%	
					}
					else {
						out.print(Checker.pnn(npm_whitelist_prodi));
					}
						%>
					</td>
				</tr>
				<tr>
					<td style="width:100px;padding:3px" >ALL FAKULTAS</td>
					<td style="width:150px;background:white" >
						<%
					if(editable) {
						if(all_fak!=null && all_fak.equalsIgnoreCase("1")) {
						%>
						<input type="radio" name="all_fak_<%=id_obj_mhs %>" value="true" checked>Ya<br>
						<input type="radio" name="all_fak_<%=id_obj_mhs %>" value="false" >Tidak
						<%
						}
						else {
						%>
						<input type="radio" name="all_fak_<%=id_obj_mhs %>" value="true" >Ya<br>
						<input type="radio" name="all_fak_<%=id_obj_mhs %>" value="false" checked>Tidak
						<%	
						}	
					}
					else {
						out.print(all_fak);
					}
						%>
					</td>
					<td style="width:175px;padding:3px" >LIST KODE PRODI ALLOW</td>
					<td style="width:350px;" >
						<%
					if(editable) {
						%>
						<textarea rows="2" style="width:98%" name="tkn_prodi_<%=id_obj_mhs %>"><%=Checker.pnn(tkn_fak)%></textarea>
						<%	
					}
					else {
						out.print(Checker.pnn(tkn_fak));
					}
						%>
					</td>
				</tr>
				<tr>
					<td style="padding:3px" colspan="2">LIST NPM REVERSE FAKULTAS</td>
					<td style="padding:3px;" colspan="2">
						<%
					if(editable) {
						%>
						<textarea rows="2" style="width:98%" name="list_npm_fak_<%=id_obj_mhs %>"><%=Checker.pnn(npm_whitelist_fak)%></textarea>
						<%	
					}
					else {
						out.print(Checker.pnn(npm_whitelist_fak));
					}
						%>
					</td>
				</tr>
				<tr>
					<td style="width:100px;padding:3px" >ALL KAMPUS</td>
					<td style="width:150px;background:white" >
						<%
					if(editable) {
						if(all_kmp!=null && all_kmp.equalsIgnoreCase("1")) {
						%>
						<input type="radio" name="all_kmp_<%=id_obj_mhs %>" value="true" checked>Ya<br>
						<input type="radio" name="all_kmp_<%=id_obj_mhs %>" value="false" >Tidak
						<%
						}
						else {
						%>
						<input type="radio" name="all_kmp_<%=id_obj_mhs %>" value="true" >Ya<br>
						<input type="radio" name="all_kmp_<%=id_obj_mhs %>" value="false" checked>Tidak
						<%	
						}
					}
					else {
						out.print(all_kmp);
					}
						%>
					</td>
					<td style="width:175px;padding:3px" >LIST KODE KAMPUS ALLOW</td>
					<td style="width:350px;" >
						<%
					if(editable) {
						%>
						<textarea rows="2" style="width:98%" name="tkn_kmp_<%=id_obj_mhs %>"><%=Checker.pnn(tkn_kmp)%></textarea>
						<%	
					}
					else {
						out.print(Checker.pnn(tkn_kmp));
					}
						%>
					</td>
				</tr>
				<tr>
					<td style="padding:3px" colspan="2">LIST NPM REVERSE KAMPUS</td>
					<td style="padding:3px;" colspan="2">
						<%
					if(editable) {
						%>
						<textarea rows="2" style="width:98%" name="list_npm_kmp_<%=id_obj_mhs%>"><%=Checker.pnn(npm_whitelist_kmp)%></textarea>
						<%	
					}
					else {
						out.print(Checker.pnn(npm_whitelist_kmp));
					}
						%>
					</td>
				</tr>
				
		<%
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}	
		%>		
		   		<tr>
					<td style="background:#369;color:#fff;text-align:center;height:35px" colspan="5">
						<input type="submit" name="submit" value="UPDATE" style="width:70%;height:30px;cellpadding:5px" />
					</td>
				</tr>	
			</table>
		</form>		
		<%
			
		}
%>
 
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>
<!DOCTYPE html>
<head>
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



</head>
<!--  body onload="location.href='#'" -->
<%
//System.out.println("bener");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector vf = (Vector)session.getAttribute("vf");
Vector v_scope_id = (Vector)session.getAttribute("v_scope_id");
String target_thsms=request.getParameter("target_thsms");
String scope_cmd=request.getParameter("scope_cmd");
String table_rule_nm=request.getParameter("table_rule_nm");
String at_kmp = request.getParameter("at_kmp");
//request.setAttribute("vf", vf);
//request.setAttribute("v_scope_id", v_scope_id);
String target = Constants.getRootWeb()+"/InnerFrame/DaftarUlang/indexDaftarUlang_v1.jsp";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);
//System.out.println(target);
//System.out.println(uri);
//System.out.println(url);
String root_caller = (String)request.getParameter("root_caller");
String final_url = "InnerFrame/DaftarUlang/indexDaftarUlang_v1.jsp?target_thsms="+target_thsms+"&scope_cmd="+scope_cmd+"&table_rule_nm="+table_rule_nm;
%>
<body>
<ul>
<%
if(!Checker.isStringNullOrEmpty(root_caller) && root_caller.equalsIgnoreCase("home")) {
	%>
	
	<li><a href="get.notifications" target="inner_iframe"">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
<%	
}
else {
%>
	<li><a href="get.sebaranTrlsm?target_thsms=<%=target_thsms%>">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
<%
}

if(v_scope_id!=null && v_scope_id.size()>0) {
	ListIterator li = v_scope_id.listIterator();
	Vector v_combine = (Vector) li.next();
	li=v_combine.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String kode_kmp = st.nextToken();
		String nm_kmp = Converter.getNamaKampus(kode_kmp);
		
		boolean match_kmp = false;
		boolean ada_pengajuan = false;
		ListIterator li0 = vf.listIterator();
		while(li0.hasNext()&&!match_kmp) {
			
			String kmp = (String)li0.next();
			Vector vprodi = (Vector) li0.next();
			if(kmp.equalsIgnoreCase(kode_kmp)) {
				match_kmp = true;
				//out.println(kmp+"<br/>");
				
				ListIterator li1 = vprodi.listIterator();
				while(li1.hasNext() && !ada_pengajuan) {
					String idobj = (String)li1.next();
					String nmpst = Converter.getNamaKdpst(Integer.parseInt(idobj));
					//System.out.println("at nmpst ="+nmpst);
					Vector vmhs = (Vector)li1.next();
					if(vmhs!=null && vmhs.size()>0) {
						ada_pengajuan = true;
						//System.out.println("ada_pengajuan="+ada_pengajuan);
						//System.out.println("at kmp ="+kmp);
					}
				}
			}
		}	
		
		if(ada_pengajuan) {
			if(at_kmp!=null && at_kmp.equalsIgnoreCase(kode_kmp)) {
				
				//if(ada_pengajuan) {
			%>
		<li><a href="<%=Constants.getRootWeb() %>/<%=final_url+"&at_kmp="+kode_kmp+"&root_caller="+root_caller %>" class="active">KAMPUS <span><%=nm_kmp.replace("KAMPUS ", "") %></span></a></li>
			<%
				//}
			}
			
			else {
				%>
		<li><a href="<%=Constants.getRootWeb() %>/<%=final_url+"&at_kmp="+kode_kmp+"&root_caller="+root_caller %>" >KAMPUS <span><%=nm_kmp.replace("KAMPUS ", "") %></span></a></li>
			<%			
			}
		}
		
		
		
	}
}	

%>
</ul>

</body>
</html>
<!DOCTYPE html>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%@ page import="beans.tools.*" %>
<%@ page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.sistem.*" %>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.SearchStandarMutu"%>
<%@page import="beans.dbase.spmi.manual.*"%>
<%@page import="beans.dbase.spmi.riwayat.pengendalian.*"%>

<% 

/*
============common variable (ada di ReultSte.jsp & navigasi.jsp) 
*/
String id_versi = request.getParameter("id_versi");
String id_std_isi = request.getParameter("id_std_isi");
String norut_man = request.getParameter("norut_man");
SrcHistEvaluasi shk = new SrcHistEvaluasi();

String at_menu_dash = request.getParameter("at_menu_dash");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String at_menu_kendal = request.getParameter("at_menu_kendal");
String id_std = request.getParameter("std_isi");
String std_kdpst = request.getParameter("std_kdpst");
String scope_std = request.getParameter("scope_std");
String target_unit_used = request.getParameter("unit_used");
String src_manual_limit = (String) session.getAttribute("src_manual_limit");
String src_offset = request.getParameter("offset");
//String starting_no = request.getParameter("starting_no");
int next_sta_no = 0;
int prev_sta_no = 0;
//int limit = 0;
int offset = 0, next_offset=0, prev_offset=0;;
//if(!Checker.isStringNullOrEmpty(src_limit)) {
//	limit = Integer.parseInt(src_limit);
//}
if(!Checker.isStringNullOrEmpty(src_offset)) {
	offset = Integer.parseInt(src_offset);
}
//System.out.println("break1");
boolean next=false;
boolean prev = false;
Vector v_hist_eval=shk.getListRiwayatEvaluasiAmi(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), Integer.parseInt(norut_man),offset,Integer.parseInt(src_manual_limit),kdpst);
int tot_rec = 0;
if(v_hist_eval!=null) {
	tot_rec = v_hist_eval.size();
}
//System.out.println("tot_rec="+tot_rec);
if(tot_rec>=Integer.parseInt(src_manual_limit)) {
	next=true;
	next_offset = offset+(Integer.parseInt(src_manual_limit))-1;
	next_sta_no = next_offset+1; 
}
else {
	next = false;
}
//System.out.println("next_offset="+next_offset);
if(offset>0) {
	prev = true;
	prev_offset = offset - (Integer.parseInt(src_manual_limit)) +1;
	prev_sta_no = prev_offset+1;
}
//System.out.println("at_page="+at_page);
//System.out.println("max_data_per_pg="+max_data_per_pg);
//System.out.println("tot_data="+tot_data);
//System.out.println("max_pg_tampil="+max_pg_tampil);
Vector v_list_data = null;
/*
===========enf common variable (ada di ReultSte.jsp & navigasi.jsp)============== 
*/

%>

</head>
<body>
<table style="width:100%;border:none">
	<tr>
<%
if(prev) {
%>
		<td style="width:50%;text-align:left;padding:0 0 0 10px">
			<a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/form_manual_evaluasi/riwayat_evaluasi.jsp?starting_no=<%=prev_sta_no %>&offset=<%=prev_offset %>&unit_used=<%=target_unit_used %>&norut_man=<%=norut_man %>&scope_std=<%=scope_std%>&std_kdpst=<%=std_kdpst %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&norut_man=<%=norut_man %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_kendal=riwayat&at_menu_dash=control" style="font-weight:bold;font-size:1.6em" target="_self">PREV <</a>
		</td>	
<%
}
if(next) {
%>
		<td style="width:50%;text-align:right;padding:0 10px 0 0">
			<a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/form_manual_evaluasi/riwayat_evaluasi.jsp?starting_no=<%=next_sta_no %>&offset=<%=next_offset %>&unit_used=<%=target_unit_used %>&norut_man=<%=norut_man %>&scope_std=<%=scope_std%>&std_kdpst=<%=std_kdpst %>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&norut_man=<%=norut_man %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_kendal=riwayat&at_menu_dash=control" style="font-weight:bold;font-size:1.5em" target="_self">> Next</a>
		</td>	
<%
}
%>
	</tr>
</table>
</body>
</html>
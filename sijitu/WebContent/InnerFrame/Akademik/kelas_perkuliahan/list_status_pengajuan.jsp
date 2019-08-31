<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
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
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />

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
tr:hover td.nopad { background:#82B0C3 }
</style>
	<script>		
		$(document).ready(function() {
			$("#wait").hide();
			$("#main").show();
		});	
	</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String target_thsms = request.getParameter("target_thsms");
	String scope_cmd = request.getParameter("scope_cmd");
	String atMenu = "form";//kalo sampe sini atMenu=form
	//System.out.println("scope_cmd="+scope_cmd);
	String backTo = request.getParameter("backTo");
	//System.out.println("2");
	Vector v =  (Vector)request.getAttribute("v");
	//System.out.println("3");
	Vector v_scope_id_combine = (Vector)request.getAttribute("v_scope_id_combine");
	//System.out.println("4");
	Vector v_approval = (Vector)request.getAttribute("v_approval");
	//System.out.println("5");
	String atKmp = request.getParameter("atKmp");
	//System.out.println("6");
%>

<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
</head>
<body>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai<br>[Lama proses bisa sampai 5 menit]</p>
</div>
<div id="main">
<div id="header">
	<jsp:include page="innerMenuListKampus.jsp" />
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->

<!-- Column 1 start -->

<%
String me = ""+validUsr.getIdObj();
boolean iam_1stApprovee=false;
if(v!=null && v.size()>0) {
	ListIterator li = v.listIterator();
	boolean first = true;
	boolean cetak_header_tabel=true;
  	int i = 1;
  	
  	while(li.hasNext()) {
  		
  		//boolean ada_riwayat = false;
  		ListIterator lit = null;
  		Vector v_hist = null;
  		String brs = null;
  		Object ob = (Object)li.next();
  		if(ob instanceof String) {
  			brs = new String((String)ob);
  		}
  		else if(ob instanceof Vector) {
  			//ada_riwayat = true;
  			v_hist = new Vector((Vector)ob);
  			lit = v_hist.listIterator();
  			brs = (String)lit.next();
  		}
  		//System.out.println("brs="+brs);
  		StringTokenizer st = new StringTokenizer(brs,"`");
  		String id = st.nextToken();
		String kdpst = st.nextToken();
		String kmp = st.nextToken();
		String locked = st.nextToken();
		String passed = st.nextToken();
		String reject = st.nextToken();
		
		String list_job_approvee = "BELUM DITENTUKAN";
		String list_id_approvee = "BELUM DITENTUKAN";
		boolean complete = true;
		if(st.hasMoreTokens()) {
			list_job_approvee = st.nextToken();
			list_id_approvee = st.nextToken();
		
			//boolean complete = true;
			if(list_id_approvee.contains("null")) {
				complete = false;
			}
			String current_job_approvee = st.nextToken();
			String current_id_approvee = st.nextToken();
		}
		else {
			complete = false;
		}
		//cek siapa yg harus approval kalo sudah ada riwayat approval
		if(v_hist!=null && v_hist.size()>0) {
			ListIterator lih = v_hist.listIterator();
			while(lih.hasNext()) {
				String baris = (String)lih.next();
				
				//litmp.add(id_row+"`"+commen+"`"+approved+"`"+cur_id+"`"+cur_job+"`"+next_id+"`"+next_job+"`"+done+"`"+updtm);
				st = new StringTokenizer(baris,"`");
				String id_row = st.nextToken();
				String commen = st.nextToken();
				String approved = st.nextToken();
				String cur_id = st.nextToken();
				String cur_job = st.nextToken();
				String next_id = st.nextToken();
				String next_job = st.nextToken();
				String done = st.nextToken();
				String updtm = st.nextToken();
				
				brs = new String(id+"`"+kdpst+"`"+kmp+"`"+locked+"`"+passed+"`"+reject+"`"+list_job_approvee+"`"+list_id_approvee+"`"+next_job+"`"+next_id);
				//System.out.println("brs=="+brs);
			}
		}
		if(kmp.equalsIgnoreCase(atKmp)) {
			if(first) {
				if(cetak_header_tabel) {
				//first = false;
				//dipindah ke while lopp karena utk ngecek apakah sy approval pertama == boleh reset
				%>
<center>

	<table class="table" style="width:80%">
	<thead>
		<tr>
			<th colspan="3" style="text-align: center; padding: 0px 10px;font-size:1.5em">LIST PENGAJUAN KELAS PERKULIAHAN <%=target_thsms %></th>
		</tr>
		<tr>
			<th width="5%">NO</th>
			<th width="40%">PRODI</th>
			<!--  th width="20%">LOKASI</th -->
			<th width="55%">STATUS PERSETUJUAN</th>
		</tr>
	</thead>
	<tbody>
				  	<%
				  	cetak_header_tabel=false;
				}	  	
			}
  	%>
		<!--  tr onclick="location.href='get.listKelasPengajuan?target_thsms=<%=target_thsms%>&info=<%=brs%>&MenuBackOnly=true&backTo=get.statusPengajuanKelasKuliahTandaTanyaatMenuSamaDgnform'"-->
		
		<tr>
			<td class="nopad" onclick="location.href='get.listKelasPengajuan?atMenu=<%=atMenu %>&no_edit=<%=locked %>&target_thsms=<%=target_thsms%>&info=<%=brs%>&MenuBackOnly=true&backTo=history'" align="center" style="vertical-align: middle; padding: 0px 5px"><%=i++ %></td>
			<td class="nopad" onclick="location.href='get.listKelasPengajuan?atMenu=<%=atMenu %>&no_edit=<%=locked %>&target_thsms=<%=target_thsms%>&info=<%=brs%>&MenuBackOnly=true&backTo=history'" align="center" style="vertical-align: middle; padding: 0px 5px"><b><%=Converter.getDetailKdpst_v1(kdpst) %> <%=locked %></b></td>
			<!--  td align="center" style="vertical-align: middle; padding: 0px 5px"><%=Converter.getNamaKampus(kmp) %></td -->
			<td align="center" style="vertical-align: middle; padding: 0px 5px">
			<b>Prosedur Persetujuan:</b><br/>
			<%= list_job_approvee%><br/>
			<%
			if(v_hist!=null && v_hist.size()>0) {
				int no = 1;
				ListIterator lih = v_hist.listIterator();
				%>
				<b>Riwayat Persetujuan : </b><div style="text-align:left;padding:0px 10px;">
			
				<%	
				while(lih.hasNext()) {
					iam_1stApprovee=false;
					String baris = (String)lih.next();
					
					//litmp.add(id_row+"`"+commen+"`"+approved+"`"+cur_id+"`"+cur_job+"`"+next_id+"`"+next_job+"`"+done+"`"+updtm);
					st = new StringTokenizer(baris,"`");
					String id_row = st.nextToken();
					String commen = st.nextToken();
					String approved = st.nextToken();
					String cur_id = st.nextToken();
					String cur_job = st.nextToken();
					String next_id = st.nextToken();
					String next_job = st.nextToken();
					String done = st.nextToken();
					String updtm = st.nextToken();
					if(approved.equalsIgnoreCase("false")) {
						out.print("<p style=\"line-height: 105%;padding:0 0 0 50px;color:red\"><b>"+no+++". ["+cur_job+"] - Pengajuan Ditolak</b><br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp-&nbspDengan alasan, "+commen+"</p>");
					}
					else if(approved.equalsIgnoreCase("true")) {
						out.print("<p style=\"line-height: 105%;padding:0 0 0 50px;color:#369\"><b>"+no+++". ["+cur_job+"] - Pengajuan Diterima</p>");
					}
					//out.print(baris+"<br/>");
					//overide info baris
					brs = new String(id+"`"+kdpst+"`"+kmp+"`"+locked+"`"+passed+"`"+reject+"`"+list_job_approvee+"`"+list_id_approvee+"`"+next_job+"`"+next_id);
					//System.out.println("brs=="+brs);
					//System.out.println("my id=="+validUsr.getIdObj());
					//System.out.println("am i=="+validUsr.amI_v2("KTU", "61201"));
					if(first) {
						//System.out.println("brs1=="+brs);
						first = false;
						
					}
					if(!Checker.isStringNullOrEmpty(list_id_approvee)) {
						String tester = new String(list_id_approvee);
						tester = tester.substring(1,tester.length()-1);
						while(tester.contains("][")) {
							tester = tester.replace("][", "`");
						}
						//System.out.println("tester=="+tester);
						StringTokenizer stt =new StringTokenizer(tester,"`");
						//cek token pertama only (first approvee)
						String tkn_approvee = stt.nextToken();
						if(tkn_approvee.equalsIgnoreCase(me) || tkn_approvee.startsWith(me+",")
							|| tkn_approvee.contains(","+me+",") || tkn_approvee.endsWith(","+me)
						) {
							iam_1stApprovee=true;
						}
					}
				}
				if(iam_1stApprovee) {
					%>
					<center>
						<section class="gradient">
					
							<p style="line-height: 105%;padding:0 0 0 50px;color:#369"><b>
							<div style="text-align:center">
								<button class="button1" style="padding: 5px 50px;font-size: 20px;"
								onclick="(function(){
            						//scroll(0,0);
									parent.scrollTo(0,0);
									var x = document.getElementById('wait');
									var y = document.getElementById('main');
									x.style.display = 'block';
									y.style.display = 'none';
             						location.href='go.resetApproval?target_kdpst=<%=kdpst %>&target_thsms=<%=target_thsms %>&target_id_obj=<%=id %>&scope_cmd=<%=scope_cmd%>'})()">RESET PERSETUJUAN</button>
							</b></p>
						</section>
					</center>		
					<%	
				}
				out.print("</div>");
			}
			if(!complete) {
				%>
						
							<p align="center" style="vertical-align: middle; padding: 5px 5px;font-weight:bold;color:red">APPROVEE ID BELUM DIISI HARAP HUBUNGI ADMIN</p>
						
				<%			
			}
			
			%></td>
		</tr>
	<%
		}
	}
  	if(first) {
  	%>
  	<section style="text-align:center;font-size:1.5em;font-weight:bold">TIDAK ADA KELAS PERKULIAHAN</section>
  	<%
  	}
	%>	
  	</tbody>
	</table>

</center>
<%
}
%>
</br/>
</div>
</div>	
		<%


		%>
		<!-- Column 1 start -->
	</div>
</div>	
</div>	
</body>
</html>
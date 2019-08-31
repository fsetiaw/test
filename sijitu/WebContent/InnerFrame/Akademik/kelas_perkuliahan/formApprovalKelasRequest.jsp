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
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%
	//System.out.println("am in");
	session.removeAttribute("no_edit");
	session.removeAttribute("info");
	session.removeAttribute("target_thsms_add_kelas");
	
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	
	String target_thsms = request.getParameter("target_thsms");
	session.setAttribute("target_thsms_add_kelas",target_thsms);
	boolean no_edit = true;
	String non_editable = request.getParameter("no_edit");
	if(non_editable!=null && non_editable.equalsIgnoreCase("false")) {
		no_edit = false;
	}
	//System.out.println("no_edit="+no_edit);
	session.setAttribute("no_edit", ""+no_edit);
	
	String info = request.getParameter("info");
	session.setAttribute("info", info);
	StringTokenizer st = new StringTokenizer(info,"`");
	String id_info = st.nextToken();
	String kdpst_info = st.nextToken();
	String kmp_info = st.nextToken();
	String locked_info = st.nextToken();
	String passed_info = st.nextToken();
	String reject_info = st.nextToken();
	String list_job_approvee_info = st.nextToken();
	String list_id_approvee_info = st.nextToken();
	boolean complete_info = true;
	if(list_id_approvee_info.contains("null")) {
		complete_info = false;
	}
	String current_job_approvee_info = st.nextToken();
	String current_id_approvee_info = st.nextToken();
	//System.out.println("current_id_approvee_info="+current_id_approvee_info);
	boolean am_i_current_approvee = false;
	String my_id=""+validUsr.getIdObj();
	if(current_id_approvee_info!=null && (current_id_approvee_info.equalsIgnoreCase(my_id) 
			|| current_id_approvee_info.startsWith(my_id+",")
			|| current_id_approvee_info.contains(","+my_id+",")
			|| current_id_approvee_info.endsWith(","+my_id)
	)) {
		am_i_current_approvee=true;
	}
	
	Vector v_kls =  (Vector)request.getAttribute("v_kls");
	Vector v_scope_id_combine = (Vector)request.getAttribute("v_scope_id_combine");
	Vector v_approval = (Vector)request.getAttribute("v_approval");
%>

<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
</head>
<body>
<div id="header">
	<jsp:include page="innerMenu.jsp" />
</div>
<div class="colmask fullpage">
	<div class="col1">
		
		<br>
		<!-- Column 1 start -->

<!-- Column 1 start -->
<%
if(v_kls!=null && v_kls.size()>0) {
	ListIterator li = v_kls.listIterator();
%>
<center>
<br>
	<table class="table" style="width:80%">
	<thead>
		<tr>
  			<th colspan="6" style="text-align: center; padding: 0px 10px;font-size:1.5em">LIST PENGAJUAN KELAS PERKULIAHAN <%=Checker.getThsmsHeregistrasi() %></th>
  		</tr>
  		<tr>
  			<th width="5%">NO</th>
  			<th width="45%" style="text-align:left;padding:0px 10px">NAMA MATAKULIAH</th>
 	 		<th width="20%">SHIFT</th>
  			<th width="20%">DOSEN</th>
  			<th width="5%">KLS</th>
  			<th width="5%"># MHS</th>
  		</tr>
  	</thead>
  	<tbody>
  	<%
  	int i = 1;
  	
  	while(li.hasNext()) {
  		String baris = (String)li.next();
  		if(baris.contains("`")) {
  			String info_nakmk = "";
  			String info_nmdos = "";
  			String info_kls = "";
  			String info_shift = "";
  			String info_konsen = "";
  			String info_tot_mhs = "";
  			boolean first = true;
  			StringTokenizer st0 = new StringTokenizer(baris,"`");
  			int j = 1;
  			while(st0.hasMoreTokens()) {
  				String brs0 = st0.nextToken();
  				st = new StringTokenizer(brs0,"||");
  				String tot_mhs = "0";
  				if(first) {
  					tot_mhs = st.nextToken();
  				}
  				
  				String nakmk = st.nextToken();
  				String kdkmk = st.nextToken();
  	  			String idkur = st.nextToken();
  	  			String idkmk = st.nextToken();
  	  			String thsms = st.nextToken();
  	  			String kdpst = st.nextToken();
  	  			String shift = st.nextToken();
  	  			String noKlsPll = st.nextToken();
  	  			String initNpmInput = st.nextToken();
  	  			String locked = st.nextToken();
  	  			String npmdos = st.nextToken();
  	  			String nodos = st.nextToken();
  	  			String npmasdos = st.nextToken();
  	  			String noasdos = st.nextToken();
  	  			String cancel = st.nextToken();
  	  			String kodeKelas = st.nextToken();
  	  			String kodeRuang = st.nextToken();
  	  			String kodeGedung = st.nextToken();
  	  			String kodeKampus = st.nextToken();
  	  			String tknHrTime = st.nextToken();
  	  			String nmmdos = st.nextToken();
  	  			String nmmasdos = st.nextToken();
  	  			String enrolled = st.nextToken();
  	  			String maxEnrol = st.nextToken();
  	  			String minEnrol = st.nextToken();
  	  			String subKeterMk = st.nextToken();
  	  			String initReqTime = st.nextToken();
  	  			String targetTotMhs = st.nextToken();
  	  			String passed = st.nextToken();
  	  			String rejected = st.nextToken();
  	  			String konsen = st.nextToken();
  	  			String kode_gabung = st.nextToken();
  	  			String cuid = st.nextToken();
  	  			String skstm = st.nextToken();
  	  			String skspr = st.nextToken();
  	  			String skslp = st.nextToken();
  	  			String skstt = st.nextToken();
  	  			String jenis = st.nextToken();
  	  			String stkmk = st.nextToken();
  	  			String opt_keter = st.nextToken();
  	  			String kode_at_pdpt = st.nextToken();
  	  			String id_makul_sama = st.nextToken();
  	  			if(first) {
  	  				first = false;
  	  				info_tot_mhs = new String(tot_mhs);
  	  				info_nmdos = new String(nmmdos);
  	  				info_konsen = new String(konsen);
  	  				info_kls = new String(noKlsPll);
  	  				info_shift = new String(shift);
  	  				info_nakmk = nakmk+" "+Checker.pnn(konsen, "[]")+"<br/><div style=\"font-size:0.7em;font-weight:bold\">GABUNGAN KELAS:</div><div style=\"font-size:0.7em\">"+j+++". []"+nakmk+"]["+kdkmk+"]["+shift+"]<br/>";
  	  			}
  	  			else {
  	  			info_nakmk = info_nakmk +""+j+++". "+nakmk+"<br/>["+kdkmk+"]["+shift+"]<br/>";
  	  			}
  			}
  			info_nakmk = info_nakmk+"</div>";
  		  	%>
  		<tr>
  			<td align="center" style="vertical-align: top; padding: 5px 5px"><%=i++ %></td>
  			<td align="left" style="vertical-align: middle; padding: 0px 10px"><%=info_nakmk%></td>
  			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=info_shift %></td>
  			<td align="center" style="vertical-align: middle; padding: 0px 5px">
  				<%=info_nmdos %>
  			</td>
  			<td align="center" style="vertical-align: top; padding: 5px 5px"><%=info_kls %></td>
  			<td align="center" style="vertical-align: top; padding: 5px 5px"><%=info_tot_mhs %></td>
  		</tr>
  		<%

  			//kelas gabungan
  		}
  		else {
  			//single class
  			st = new StringTokenizer(baris,"||");
  			String tot_mhs = st.nextToken();
  			String nakmk = st.nextToken();
  			String kdkmk = st.nextToken();
  			String idkur = st.nextToken();
  			String idkmk = st.nextToken();
  			String thsms = st.nextToken();
  			String kdpst = st.nextToken();
  			String shift = st.nextToken();
  			String noKlsPll = st.nextToken();
  			String initNpmInput = st.nextToken();
  			String locked = st.nextToken();
  			String npmdos = st.nextToken();
  			String nodos = st.nextToken();
  			String npmasdos = st.nextToken();
  			String noasdos = st.nextToken();
  			String cancel = st.nextToken();
  			String kodeKelas = st.nextToken();
  			String kodeRuang = st.nextToken();
  			String kodeGedung = st.nextToken();
  			String kodeKampus = st.nextToken();
  			String tknHrTime = st.nextToken();
  			String nmmdos = st.nextToken();
  			String nmmasdos = st.nextToken();
  			String enrolled = st.nextToken();
  			String maxEnrol = st.nextToken();
  			String minEnrol = st.nextToken();
  			String subKeterMk = st.nextToken();
  			String initReqTime = st.nextToken();
  			String targetTotMhs = st.nextToken();
  			String passed = st.nextToken();
  			String rejected = st.nextToken();
  			String konsen = st.nextToken();
  			String kode_gabung = st.nextToken();
  			String cuid = st.nextToken();
  			String skstm = st.nextToken();
  			String skspr = st.nextToken();
  			String skslp = st.nextToken();
  			String skstt = st.nextToken();
  			String jenis = st.nextToken();
  			String stkmk = st.nextToken();
  			String opt_keter = st.nextToken();
  			String kode_at_pdpt = st.nextToken();
  			String id_makul_sama = st.nextToken();
  		  	%>
  		<tr>
  			<td align="center" style="vertical-align: top; padding: 5px 5px"><%=i++ %></td>
  			<td align="left" style="vertical-align: middle; padding: 0px 10px"><%=nakmk+" "+Checker.pnn(konsen, "[]")%></td>
  			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=shift %></td>
  			<td align="center" style="vertical-align: middle; padding: 0px 5px">
  				<%=nmmdos %>
  			</td>
  			<td align="center" style="vertical-align: top; padding: 5px 5px"><%=noKlsPll %></td>
  			<td align="center" style="vertical-align: top; padding: 5px 5px"><%=tot_mhs %></td>
  		</tr>
  		<%
  			
  		}
  		//boolean ada_riwayat = false;


  	}
  	//System.out.println(current_id_approvee_info+" vs. "+validUsr.getIdObj());
  	
	//if(complete_info && current_id_approvee_info.equalsIgnoreCase(""+validUsr.getIdObj())) {
	if(complete_info && am_i_current_approvee) {	
%>
	<tr>
		<form action="go.approvalKelasKuliah">
		<input type="hidden" name="target_thsms" value="<%=target_thsms %>"/>
		<input type="hidden" name="info" value="<%=info %>"/>
		
		<td colspan="6" align="center" style="vertical-align: middle; padding: 5px 5px">
		<textarea title="Harap diisi alasan penolakan" name="alasan" placeholder="Alasan Penolakan" style="width:99%;rows:3;" required></textarea>
		<br/>
		<center>
		<section class="gradient">
			<div style="text-align:center">
				<button name="verdict" value="tolak" class="button1" type="submit" style="padding: 5px 50px;font-size: 20px;">TOLAK PERMOHONAN</button>
				&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
				<button name="verdict" value="terima" formnovalidate type="submit" style="padding: 5px 50px;font-size: 20px;">TERIMA PERMOHONAN</button>
			</div>
			
		</section>
			
		</center>
		</td>
		</form>	
	</tr>	
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
</body>
</html>
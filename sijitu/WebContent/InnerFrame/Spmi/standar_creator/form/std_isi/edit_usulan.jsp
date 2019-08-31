
<!DOCTYPE html>
<%@page import="org.codehaus.jackson.map.introspect.BasicClassIntrospector.GetterMethodFilter"%>
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
<%@ page import="beans.dbase.jabatan.SearchDbJabatan"%>
<%@ page import="beans.dbase.spmi.request.*"%>
<%@ page import="beans.dbase.spmi.*"%>

<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
  
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />


<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String atMenu = request.getParameter("atMenu");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
String updated = request.getParameter("updated");
SearchDbJabatan sdb = new SearchDbJabatan(validUsr.getNpm());
Vector v_job = sdb.getListAvalablePihakTerkait_v1();
SearchRequest sr = new SearchRequest();
Vector v = sr.getListNuStdReq();
String mode = request.getParameter("mode");
Vector v_err = (Vector)session.getAttribute("v_err");
session.removeAttribute("v_err");
SearchSpmi ss = new SearchSpmi();
Vector v_root = ss.getListMasterStandar();
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

 <script type="text/javascript">

jQuery(function() {
    jQuery('#id_master').change(function() {
        this.form.submit();
    });
});


$(document).ready(function () {
	var mySelect = $('#first-disabled2');

	$('#special').on('click', function () {
		mySelect.find('option:selected').prop('disabled', true);
		mySelect.selectpicker('refresh');
	});

	$('#special2').on('click', function () {
		mySelect.find('option:disabled').prop('disabled', false);
		mySelect.selectpicker('refresh');
	});

	$('#basic2').selectpicker({
  		liveSearch: true,
		maxOptions: 1
	});
});
</script>

</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">
	<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/topMenu.jsp" >
		<jsp:param name="atMenu" value="<%=atMenu %>"/>
		<jsp:param name="target_kdpst" value="<%=kdpst %>"/>
		<jsp:param name="target_nmpst" value="<%=nmpst %>"/>
		<jsp:param name="target_kdkmp" value="<%=kdkmp %>"/>

	</jsp:include>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<!-- Column 1 start -->
	
		<br>
		
<%
if(v_err!=null && v_err.size()>0) {
	ListIterator litmp = v_err.listIterator();
%>
	<div style="text-align:center;font-size:0.9em;color:red;font-weight:bold">
<%
	while(litmp.hasNext()) {
		String brs = (String)litmp.next();
		out.print("* "+brs+"<br>");
	}
%>	
	</div>
	
<%	
}
if(false) {

}
else {
	//edit mode
	//System.out.println("edit mode");
	String id_std_isi = request.getParameter("id_std_isi");
	//System.out.println(" id_std_isi = "+id_std_isi);
	if(!Checker.isStringNullOrEmpty(id_std_isi)) {
		v = (Vector)session.getAttribute("v_target");
		if(v==null || v.size()<1) {
			v = sr.getInfoStd(Integer.parseInt(id_std_isi));	
		}
		session.removeAttribute("v_target");
		ListIterator li = v.listIterator();
		String brs = (String)li.next();
		//System.out.println("baris = "+brs);
		st = new StringTokenizer(brs,"`");
		//id+"`"+id_std+"`"+id_master+"`"+id_tipe+"`"+isi+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+versi+"`"+id_declare+"`"+id_do+"`"+id_eval+"`"+id_control+"`"+id_upgrade+"`"+tglsta+"`"+tglend+"`"+thsms1+"`"+thsms2+"`"+thsms3+"`"+thsms4+"`"+thsms5+"`"+thsms6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+norut+"`"+target_period_start+"`"+unit_period+"`"+lama_per_period+"`"+target_unit1+"`"+target_unit2+"`"+target_unit3+"`"+target_unit4+"`"+target_unit5+"`"+target_unit6
		String id = st.nextToken();
		String id_std = st.nextToken();
		String id_master = request.getParameter("id_master");
		String id_tipe = request.getParameter("id_tipe");
		String isi = st.nextToken();
		String butir = st.nextToken();
		String kdpst_std = st.nextToken();
		String rasionale = st.nextToken();
		String versi = st.nextToken();
		String id_declare = st.nextToken();
		String id_do = st.nextToken();
		String id_eval = st.nextToken();
		String id_control = st.nextToken();
		String id_upgrade = st.nextToken();
		String tglsta = st.nextToken();
		String tglend = st.nextToken();
		String thsms1 = st.nextToken();
		String thsms2 = st.nextToken();
		String thsms3 = st.nextToken();
		String thsms4 = st.nextToken();
		String thsms5 = st.nextToken();
		String thsms6 = st.nextToken();
		String pihak = st.nextToken();
		String tkn_doc = st.nextToken();
		//System.out.println("dokumen = "+tkn_doc);
		
		String indikator = st.nextToken();
		String norut = st.nextToken();
		
		String periode_awal = st.nextToken();
		String unit_period = st.nextToken();
		String lama_per_period = st.nextToken();
		String target_unit1 = st.nextToken();
		String target_unit2 = st.nextToken();
		String target_unit3 = st.nextToken();
		String target_unit4 = st.nextToken();
		String target_unit5 = st.nextToken();
		String target_unit6 = st.nextToken();
		
%>
		<form action="go.editUsulanStdIsi" method="post">
		<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp %>"/>
		<input type="hidden" name="id_std_isi" value="<%=id_std_isi %>"/>
		<table class="table" width="90%">
			<thead>
				<tr>
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">FORM PERNYATAAN ISI STANDAR</th>
  				</tr>
  				<!--  tr>
  					<th width="5%">NO</th>
  					<th width="20%">PRODI</th>
  					<th width="20%">LOKASI</th>
  					<th width="55%">STATUS PERSETUJUAN</th>
  				</tr-->
  			</thead>
  			<tbody>	
  				<tr>
					<td colspan="4" align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >RASIONALE&nbsp/&nbspALASAN PENGAJUAN&nbsp/&nbsp MASALAH YG SERING TERJADI DI LAPANGAN</td>
				</tr>
				<tr>
					<td colspan="4" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
					
<%
				if(Checker.isStringNullOrEmpty(rasionale)) {
					%>					
						<textarea name="rasionale" style="width:100%;height:100px;border:none;rows:5" placeholder="Harap ceritakan permasalahan yang terjadi di lapangan, serta centang/sebutkan pihak dan dokumen terkait.&#13;&#10;[contoh: Mahasiswa selalu terlambat melakukan heregistrasi] &#13;&#10;Bila pihak atau dokumen terkait belum ada di list, harap tuliskan juga pihak/dokumen yang terkait agar bisa didata."></textarea>
<%					
				}
				else {
%>					
						<textarea name="rasionale" style="width:100%;height:100px;border:none;rows:5"><%=rasionale %></textarea>
<%
				}
%>						
					</td>
				</tr>
				<tr>
					<td colspan="4" align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PERNYATAAN ISI STANDAR</td>
				</tr>
				<tr>
					<td colspan="4" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
						<textarea name="isi_std" style="width:100%;height:100px;border:none;rows:5"><%=Checker.pnn_v1(isi) %></textarea>
					</td>
				</tr>		

				<tr>
					<td colspan="4" align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >DOKUMEN TERKAIT</td>
				</tr>	
				<tr>
					<td colspan="4" align="center" style="vertical-align: middle; padding: 0px 0px;background:white" >
					<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/form/std_isi/selec_doc.jsp">
					<jsp:param name="tkn_doc" value="<%=tkn_doc %>"/>
					</jsp:include>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:white;font-weight:bold" >PIHAK TERKAIT</td>
				</tr>	
				<tr>
<%
		boolean first = true;
		String prev_grp = "";
		int counter = 0;
		ListIterator li_job = v_job.listIterator();
		while(li_job.hasNext()) {
			boolean match = false;
			counter++;
			String job = (String)li_job.next();
			StringTokenizer stj = new StringTokenizer(job,"`");
			job = stj.nextToken();
			String grp = stj.nextToken();
			if(first) {
				first = false;
				prev_grp = new String(grp);
			}
			//System.out.println(job+" vs "+pihak);
			if(!Checker.isStringNullOrEmpty(pihak)) {
				StringTokenizer stp = new StringTokenizer(pihak,",");
				while(stp.hasMoreTokens() && !match) {
					String terkait = stp.nextToken();
					if(job.equalsIgnoreCase(terkait)) {
						match = true;
					}
				}
			}
			
		//String sin = st.nextToken();
			if(counter%3!=0) {
					if(match) {
%>
							<td align="left" style="vertical-align: top;"><input type="checkbox" name="job" value="<%=job %>" checked="checked"> <%=job %></td>
<%						
					}
					else {
%>
							<td align="left" style="vertical-align: top;"><input type="checkbox" name="job" value="<%=job %>" > <%=job %></td>
<%
					}
			}
			else {
				if(match) {
					%>
							<td align="left" style="vertical-align: top;"><input type="checkbox" name="job" value="<%=job %>" checked="checked"> <%=job %></td>
<%
				}
				else {
			%>
							<td align="left" style="vertical-align: top;"><input type="checkbox" name="job" value="<%=job %>" > <%=job %></td>
<%
				}	
%>
							</tr>
							<tr>
<%			
				
			}
			if(!li_job.hasNext()) {
			%>
							</tr>
			<%		
			}
			if(!prev_grp.equalsIgnoreCase(grp)) {
				prev_grp = new String(grp);
				counter = 0;
				%>
				<tr>
					<td colspan="4" align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.darkColorBlu()%>;color:white;font-weight:bold" >GROUP / KELOMPOK JABATAN TERKAIT</td>
				</tr>	
				<tr>
<%
				
			}
		}	
%>

				<tr>
					<td colspan="4" style="padding:5px 0px">
						<section class="gradient" style="text-align:center">
	            			<button style="padding: 5px 50px;font-size: 20px;">UPDATE USULAN STANDAR</button>
        				</section>
					</td>		
				</tr>	
  			</tbody>
		</table>
		<br>
		</form>
<%
	}
}
%>
	</div>
</div>		
</body>
</html>	
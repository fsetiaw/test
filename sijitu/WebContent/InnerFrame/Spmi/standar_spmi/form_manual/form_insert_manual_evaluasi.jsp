<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.spmi.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.SearchStandarMutu"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.jabatan.SearchDbJabatan"%>

<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
//-------------------------------------------------
String ua=request.getHeader("User-Agent").toLowerCase();
boolean mobile=false;
if(ua.matches("(?i).*((android|bb\\d+|meego).+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\\.(browser|link)|vodafone|wap|windows ce|xda|xiino).*")||ua.substring(0,4).matches("(?i)1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\\-|your|zeto|zte\\-")) {
  //response.sendRedirect("http://detectmobilebrowser.com/mobile");
  //return;	
  mobile = true;
  
}
//--------------------------------------------


beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
SearchStandarMutu ssm = new SearchStandarMutu();
ToolSpmi ts = new ToolSpmi();
SearchDbJabatan sdb = new SearchDbJabatan(validUsr.getNpm());


String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
//Vector v= (Vector)request.getAttribute("v");
//request.removeAttribute("v");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
String id_kendali = request.getParameter("id_kendali");
String id_versi = request.getParameter("id_versi");
String id_std_isi = request.getParameter("id_std_isi");
int norut_terakhir = 0;
if(!Checker.isStringNullOrEmpty(id_std_isi)&&!Checker.isStringNullOrEmpty(id_versi)) {
	
	norut_terakhir = ts.getNorutTerakhirManEvaluasiPelaksanaanStd(Integer.parseInt(id_std_isi), Integer.parseInt(id_versi));
	//norut_terakhir = ts.getNorutTerakhirManEvalPelaksanaanStd(Integer.parseInt(id_std_isi), Integer.parseInt(id_versi));
}
 
String id_std = request.getParameter("id_std");
String at_menu_dash = request.getParameter("at_menu_dash");
String fwdto = request.getParameter("fwdto");
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);
String pernyataan_std = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "PERNYATAAN_STD");
String target_param = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TKN_PARAMETER");
String target_indikator = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TKN_INDIKATOR");
String starting_period = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TARGET_PERIOD_START");
String target_val_1 = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TARGET_THSMS_1");
String target_val_2 = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TARGET_THSMS_2");
String target_val_3 = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TARGET_THSMS_3");
String target_val_4 = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TARGET_THSMS_4");
String target_val_5 = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TARGET_THSMS_5");
String target_val_6 = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TARGET_THSMS_6");
String target_unit = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "TARGET_THSMS_1_UNIT");
String unit_per_period = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "UNIT_PERIOD_USED");
String lama_per_period = ssm.getStandardInfoForGivenColomn(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), "LAMA_NOMINAL_PER_PERIOD");
String thsms_now = Checker.getThsmsNow();

String list_pihak_terkait_std_isi = ssm.getListPihakTerkaitPadaIsiStd(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi));
//System.out.println("pernyataan_std="+pernyataan_std);
//System.out.println("thsms_now="+thsms_now);
//System.out.println("starting_period="+starting_period);
//System.out.println("unit_per_period="+unit_per_period);
//System.out.println("lama_per_period="+lama_per_period);
String target_val = null;
int norut_target_val = ToolSpmi.getNorutTargetParamIndikatorPeriod(starting_period, unit_per_period, lama_per_period);
if(norut_target_val==1) {
	target_val = new String(target_val_1);
}
else if(norut_target_val==2) {
	target_val = new String(target_val_2);
}
else if(norut_target_val==3) {
	target_val = new String(target_val_3);
}
else if(norut_target_val==4) {
	target_val = new String(target_val_4);
}
else if(norut_target_val==5) {
	target_val = new String(target_val_5);
}
else if(norut_target_val==6) {
	target_val = new String(target_val_6);
}
//System.out.println("norut_target_val="+norut_target_val);
//System.out.println("id_std_isi="+id_std_isi);
//System.out.println("list_pihak_terkait_std_isi="+list_pihak_terkait_std_isi);
Vector v_pihak = sdb.getListAvalablePihakTerkait_v1(false,list_pihak_terkait_std_isi);
Vector v_group = sdb.getListAvalablePihakTerkait_v1(true,list_pihak_terkait_std_isi);
//param dari form di page ini

String norut = request.getParameter("norut");

String ket_proses = request.getParameter("ket_proses");
if(!Checker.isStringNullOrEmpty(ket_proses)) {
	ket_proses = ket_proses.replace("<br>", "");
}
String capaian = request.getParameter("capaian");
if(!Checker.isStringNullOrEmpty(capaian)) {
	capaian = capaian.replace("<br>", "");
}
String[]job = request.getParameterValues("job");
String[]job_input = request.getParameterValues("job_input");
String manual = request.getParameter("manual");
if(!Checker.isStringNullOrEmpty(manual)) {
	manual = manual.replace("<br>", "");
}
String tot_repetisi = request.getParameter("tot_repetisi");
String satuan = request.getParameter("satuan");
String tipe_sarpras = request.getParameter("tipe_sarpras");
String catat_civitas = request.getParameter("catat_civitas");
//Vector v_kendal= (Vector)request.getAttribute("v_kendal");
//request.removeAttribute("v");

%>
<style>
a.img:hover {
	text-decoration: none;
	background:none;
}

.table {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
}
.table thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9; }

.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px }
.table:hover td { background:#82B0C3 }
</style>
<style>
.table1 {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
}
.table1 thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table1 thead > tr > th, .table1 tbody > tr > t-->h, .table1 tfoot > tr > th, .table1 thead > tr > td, .table1 tbody > tr > td, .table1 tfoot > tr > td { border: 1px solid #2980B9; }

.table1-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table1-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table1-noborder thead > tr > th, .table1-noborder tbody > tr > th, .table1-noborder tfoot > tr > th, .table1-noborder thead > tr > td, .table1-noborder tbody > tr > td, .table1-noborder tfoot > tr > td { border: none;padding: 2px }

</style>
<style>
.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px }

</style>

</head>
<body>
<jsp:include page="menu_man.jsp" />
<div class="colmask fullpage">
	<div class="col1">
	
		<!-- Column 1 start -->
		<div style="text-align:center;padding:0 0 0 3px">
		<span class="tile-group-title">
			<select style="width:100%;height:25px;border:none;text-align-last:center;border-radius: 10px;" disabled>
				<option value="null" selected="selected"><%=keter_prodi%></option> 
			</select>
		</span>
	</div>
		<br>
		
<%
Vector v_err = (Vector)request.getAttribute("v_err");
request.removeAttribute("v_err");
if(v_err!=null && v_err.size()>0) {
	ListIterator litmp = v_err.listIterator();
%>
	<div style="text-align:center;font-size:1.2em;color:red;font-weight:bold">
	HARAP PERBAIKI DATA BERIKUT:
	</div>
	<div style="text-align:center;font-size:0.9em;color:red;font-weight:bold">
<%
	while(litmp.hasNext()) {
		String brs = (String)litmp.next();
		out.print("* "+brs+"<br>");
	}
%>	
	</div>
	<br><%="manual="+manual %><br>
<%	
}		



boolean editor = false;
boolean terkait = false;
boolean pengawas = false;		

if(validUsr.amI("ADMIN")||validUsr.amIcontain("MUTU")) {
	editor = true; //terkait & pengaeas
	terkait = true;
	pengawas = true;
}
%>		
	<center>
	<form action="go.updManualStd" method="post">
		<input type="hidden" name="fwdto" value="<%=fwdto %>" />
		<input type="hidden" name="id_kendali" value="<%=id_kendali %>" />
		<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp %>" />
		<input type="hidden" name="id_versi" value="<%=id_versi %>" />
		<input type="hidden" name="id_std_isi" value="<%=id_std_isi %>" />
		<input type="hidden" name="id_std" value="<%=id_std %>" />
		<input type="hidden" name="at_menu_dash" value="<%=at_menu_dash %>" />
		<input type="hidden" name="norut" value="<%=norut_terakhir+1 %>" />
		<table class="table1" id="tabel" style="width:90%" >
		</fieldset>	
			<thead>
				<tr>
					<td colspan="10" style="font-size:2em;text-align:center;font-weight:bold;color:#369">
					INPUT FORM MANUAL EVALUASI
					</td>
				</tr>
				<!--  tr>
					<th colspan="10" style="border-left: none;text-align:left;height:30px;vertical-align:middle;padding:0 5px;width:95%;border:none;font-size:1.0em;font-weight:bold">
					NO URUT MANUAL &nbsp&nbsp
					<%
					if(Checker.isStringNullOrEmpty(norut)) {
					%> 
					<input type="hidden" name="norut" placeholder="1" value="1" required style="text-align:center;width:50px;height:20px;border:none" readonly="readonly"/>
					<%
					}
					else {
						%> 
					<input type="hidden" name="norut" placeholder="1" value="<%=norut %>" required style="text-align:center;width:50px;height:20px;border:none" readonly="readonly"/>
						<%
					}
					%> 
					&nbsp&nbsp(Optional: bila manual ada > 1)
					</th>
				</tr -->
				<tr>
					<th colspan="10" style="border-left: none;text-align:left;height:30px;vertical-align:middle;padding:0 5px;width:95%;border:none;font-size:1.0em;font-weight:bold">
					PERNYATAAN ISI STANDAR
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="10" style="vertical-align:top;text-align:center;padding:3px 5px;font-size:1.3em;background:white">
					<textarea name="isi_std" style="width:100%;height:50px;border:none;rows:2" placeholder="Berikan keterangan mengenai parameter/objek beserta proses yg hendak dikendalikan&#13;&#10;[contoh: Kelas perkuliahan wajib dimulai tepat waktu]" readonly="readonly"><%=Checker.pnn(pernyataan_std) %></textarea>	
					</td>
				</tr>
			</tbody>
			<tr>
					<th colspan="10" style="border-left: none;text-align:left;height:30px;vertical-align:middle;padding:0 5px;width:95%;border:none;font-size:1.0em;font-weight:bold">
					PARAMETER / PROSES / OBJEK YG SEDANG DIEVALUASI
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="10" style="vertical-align:top;text-align:center;padding:3px 5px;font-size:1.3em;background:white">
					<textarea name="ket_proses" style="width:100%;height:20px;border:none;rows:2" placeholder="Berikan keterangan mengenai parameter/objek beserta proses yg hendak dikendalikan&#13;&#10;[contoh: Kelas perkuliahan wajib dimulai tepat waktu]" readonly="readonly"><%=Checker.pnn(target_param) %></textarea>	
					</td>
				</tr>
			</tbody>
			<thead>
			<thead>
				<tr>
					<th colspan="10" style="border-left: none;text-align:left;height:30px;vertical-align:middle;padding:0 5px;width:95%;border:none;font-size:1.0em;font-weight:bold">
					INDIKATOR CAPAIAN
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="8" style="text-align:center;padding:3px 5px;font-size:1.3em;background:white">
					<input type="text" name="capaian" style="width:100%;height:25px;border:none;" value="<%=Checker.pnn(target_indikator) %>" readonly="readonly"/>
					</td>
					<td colspan="2" style="text-align:center;padding:3px 5px;font-size:1.3em;background:white">
						<%=target_val +" "+target_unit%>
					<!--  textarea name="capaian" style="width:100%;height:25px;border:none;rows:2" placeholder="[contoh: Kelas perkuliahan dimulai tepat waktu sesuai pernyataan isi std]" required><Checker.pnn(target_indikator) %> <target_val +" "+target_unit%></textarea -->	
					</td>
				</tr>
			</tbody>
			<thead>	  			
				<tr>
					<th colspan="10" style="border-left: none;text-align:left;height:30px;vertical-align:middle;padding:0 5px;width:95%;border:none;font-size:1.0em;font-weight:bold">
					PILIH 1 (SATU) PIHAK [JABATAN/GROUP] SEBAGAI PENGENDALI
					</th>
				</tr>
				<tr>
					<th colspan="10" style="background:#a6bac4;border-left: none;text-align:left;height:30px;vertical-align:middle;padding:0 5px;width:95%;border:none;font-size:1.0em;font-weight:bold">
					&nbsp&nbsp&nbsp&nbspLIST JABATAN 
					</th>
				</tr>
			</thead>
			<tbody>	
				<tr>
				
					<td colspan="10" style="text-align:center;padding:10px 0;font-size:0.9em">
					<table class="table-noborder" style="width:100%">
						<tbody>
							<tr>
<%
if(v_pihak==null || v_pihak.size()<1) {
%>
							<td style="text-align:center;width:100%">N/A</td></tr>
<%		
}
else {
	int counter = 0;
	ListIterator li = v_pihak.listIterator();
	while(li.hasNext()) {
		boolean match = false;
		counter++;
		String brs = (String)li.next();
		st = new StringTokenizer(brs,"`");
		String job_opt = st.nextToken();
		
		//String sin = st.nextToken();
		if(counter%3!=0) {
%>
								<td align="left" style="vertical-align: top;padding:0 20px"><input type="checkbox" name="job" value="<%=job_opt %>" <% if(job!=null && job.length>0 && job_opt.equalsIgnoreCase(job[0])) { out.print("checked"); } %>> <%=job_opt %></td>
<%
		}
		else {
			%>
								<td align="left" style="vertical-align: top;padding:0 20px"><input type="checkbox" name="job" value="<%=job_opt %> <% if(job!=null && job.length>0 && job_opt.equalsIgnoreCase(job[0])) { out.print("checked"); } %>"> <%=job_opt %></td>
							</tr>
							<tr>
<%			
		}
		if(!li.hasNext()) {
			%>
							</tr>
			<%		
		}
	}
}	
%>
						</tbody>	
					</table>
					</td>
				</tr>
			<thead>	  			
				<tr>
					<th colspan="10" style="background:#a6bac4;border-left: none;text-align:left;height:30px;vertical-align:middle;padding:0 5px;width:95%;border:none;font-size:1.0em;font-weight:bold">
					&nbsp&nbsp&nbsp&nbspLIST GROUP 
					</th>
				</tr>
			</thead>
				<tr>
					<td colspan="10" style="text-align:center;padding:10px 0;font-size:0.9em">
					<table class="table-noborder" style="width:100%">
						<tbody>
							<tr>
<%
if(v_group==null || v_group.size()<1) {
%>
							<td style="text-align:center;width:100%">N/A</td></tr>
<%		
}
else {
	int counter = 0;
	ListIterator li = v_group.listIterator();
	while(li.hasNext()) {
		boolean match = false;
		counter++;
		String brs = (String)li.next();
		st = new StringTokenizer(brs,"`");
		String job_opt = st.nextToken();
		//String sin = st.nextToken();
		if(counter%3!=0) {
%>
								<td align="left" style="vertical-align: top;padding:0 20px"><input type="checkbox" name="job" value="<%=job_opt %>" <% if(job!=null && job.length>0 && job_opt.equalsIgnoreCase(job[0])) { out.print("checked"); } %>> <%=job_opt %></td>
<%
		}
		else {
			%>
								<td align="left" style="vertical-align: top;padding:0 20px"><input type="checkbox" name="job" value="<%=job_opt %>" <% if(job!=null && job.length>0 && job_opt.equalsIgnoreCase(job[0])) { out.print("checked"); } %>> <%=job_opt %></td>
							</tr>
							<tr>
<%			
		}
		if(!li.hasNext()) {
			%>
							</tr>
			<%		
		}
	}	
}	
%>
						</tbody>	
					</table>
					</td>
				
				</tr>	
			</tbody>
			<thead>
				<tr>
					<th colspan="10" style="border-left: none;text-align:left;height:30px;vertical-align:middle;padding:0 5px;width:95%;border:none;font-size:1.0em;font-weight:bold">
					MANUAL STANDAR EVALUASI
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<%
					//String tmp="Tuliskan tahapan yang harus dilakukan pengawas (manual)&#13;&#10;Contoh:&#13;&#10;1. Pengawas wajib berada di kelas yg akan diawasi 15 menit sebelum kelas dimulai&#13;&#10;2. Perhatikan jam kedatangan dosen dan mahasiswa &#13;&#10;3. Bila terjadi keterlambatan, tanyakan pihak yg terlambat dan temukan/ tentukan penyebab keterlambatan  &#13;&#10;4. Gunakan 5 whys analysis dalam menentukan penyebab [Ajukan 5 urutan penyebab sehingga terjadi keterlambatan]&#13;&#10;[Pengawasan dilakukan setiap hari untuk setiap kelas perkuliahan]";
					String tmp=
							"Panduan penulisan manual evaluasi;&#13;&#10;"+
							"1. Lakukan pemantauan secara periodik dan jabarkan tata cara pemantauan terhadap pelaksanaan isi standar&#13;&#10;"+
							"2. Catat atau rekam semua temuan berupa penyimpangan, kelalaian, kesalahan, atau sejenisnya dari penyelenggaraan pendidikan yang tidak sesuai dengan isi standar.&#13;&#10;"+
							"3. Catat pula bila ditemukan ketidak-lengkapan dokumen seperti prosedur kerja, formulir, dsbnya dari setiap standar yang telah dilaksanakan.&#13;&#10;"+
							"4. Periksa dan pelajari alasan atau penyebab terjadinya penyimpangan, gunakan 5 why analysis dalam menentukan penyebab [Ajukan 5 urutan pertanyaan penyebab terjadinya penyimpangan]&#13;&#10;"+
							"5. Berikan rekomendasi tindakan korektif / pengendalian";
							%>
					<td colspan="10" style="text-align:left;padding:10px 0;font-size:1.3em;background:white">
					<%
					if(Checker.isStringNullOrEmpty(manual)) {
					%>
					<textarea name="manual" style="align-content:center;width:100%;height:150px;border:none;rows:15" placeholder="<%=tmp %>" required><%=tmp %></textarea>
					<%	
					}
					else {
						%>
					<textarea name="manual" style="align-content:center;width:100%;height:150px;border:none;rows:15" placeholder="<%=tmp %>" required><%=manual.trim() %></textarea>
						<%
					}
					%>
						
					</td>
				</tr>
			</tbody>
			<thead>
				<tr>
					<th colspan="10" style="border-left: none;text-align:left;height:30px;vertical-align:middle;padding:0 5px;width:95%;border:none;font-size:1.0em;font-weight:bold">
					PENCATATAN TAMBAHAN SARANA PRASARANA & CIVITAS (BILA DIPERLUKAN)
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
					<table style="width:100%;border:none">
						<tr>
							<td style="text-align:center;width:50%;background:<%=Constant.mildColorBlu()%>">
								TIPE DATA SARANA PRASARANA
							</td>
							<td style="text-align:center;width:50%;background:<%=Constant.mildColorBlu()%>">
								DATA CIVTAS
							</td>
						</tr>
						<tr>
							<td style="width:60%;">
							<%
if(!Checker.isStringNullOrEmpty(tipe_sarpras) && tipe_sarpras.equalsIgnoreCase("umum")) {
	%>
								<input type="radio" name="tipe_sarpras" value="none"> Tidak Diperlukan<br>
								<input type="radio" name="tipe_sarpras" value="umum" checked="checked"> Catat sbg Bentuk Dasar [contoh: Tipe Ruang Kelas e.g.Ruang Perkuliahan/Perpustakaan/Lab]<br>
								<input type="radio" name="tipe_sarpras" value="detil"> Catat sbg Bentuk Spesifik [Contoh: Kode Ruang Kelas]
	<%	
}
else if(!Checker.isStringNullOrEmpty(tipe_sarpras) && tipe_sarpras.equalsIgnoreCase("detil")) {
							%>
								<input type="radio" name="tipe_sarpras" value="none"> Tidak Diperlukan<br>
 					 			<input type="radio" name="tipe_sarpras" value="umum"> Catat sbg Bentuk Dasar [contoh: Kelas Kuliah]<br>
  								<input type="radio" name="tipe_sarpras" value="detil" checked="checked"> Catat sbg Bentuk Spesifik [Contoh: Kode Ruang Kelas]
  							<%
}
else if(!Checker.isStringNullOrEmpty(tipe_sarpras) && tipe_sarpras.equalsIgnoreCase("none")) {
							%>
								<input type="radio" name="tipe_sarpras" value="none" checked="checked"> Tidak Diperlukan<br>
								<input type="radio" name="tipe_sarpras" value="umum"> Catat sbg Bentuk Dasar [contoh: Tipe Ruang Kelas e.g.Ruang Perkuliahan/Perpustakaan/Lab]<br>
								<input type="radio" name="tipe_sarpras" value="detil"> Catat sbg Bentuk Spesifik [Contoh: Kode Ruang Kelas]
	<%	
}	
else {
  							%>
								<input type="radio" name="tipe_sarpras" value="none" required> Tidak Diperlukan<br>
 					 			<input type="radio" name="tipe_sarpras" value="umum"> Catat sbg Bentuk Dasar [contoh: Tipe Ruang Kelas e.g.Ruang Perkuliahan/Perpustakaan/Lab]<br>
  								<input type="radio" name="tipe_sarpras" value="detil"> Catat sbg Bentuk Spesifik [Contoh: Kode Ruang Kelas]
  							<%
}
							%>
							</td>
							<td style="width:40%;">
								<%
if(!Checker.isStringNullOrEmpty(catat_civitas) && catat_civitas.equalsIgnoreCase("1")) {
								%>
								<input type="radio" name="catat_civitas" value="null"> Tidak Diperlukan<br>
	 					 		<input type="radio" name="catat_civitas" value="true" checked="checked"> Catat Data Civitas
	 					 		<%	
}
else if(!Checker.isStringNullOrEmpty(catat_civitas) && catat_civitas.equalsIgnoreCase("0")) {
									%>
								<input type="radio" name="catat_civitas" value="null" checked="checked"> Tidak Diperlukan<br>
	 					 		<input type="radio" name="catat_civitas" value="true"> Catat Data Civitas
	 					 		<%
}
else {
								%>
								<input type="radio" name="catat_civitas" value="null" required> Tidak Diperlukan<br>
 					 			<input type="radio" name="catat_civitas" value="true"> Catat Data Civitas
 					 			<%
}
 					 			%>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</tbody>	
			<thead>	  			
				<tr>
					<th colspan="10" style="border-left: none;text-align:left;height:30px;vertical-align:middle;padding:0 5px;width:95%;border:none;font-size:1.0em;font-weight:bold">
					PILIH 1 (SATU) PIHAK [JABATAN/GROUP] EVALUATOR
					</th>
				</tr>
				<tr>
					<th colspan="10" style="background:#a6bac4;border-left: none;text-align:left;height:30px;vertical-align:middle;padding:0 5px;width:95%;border:none;font-size:1.0em;font-weight:bold">
					&nbsp&nbsp&nbsp&nbspLIST JABATAN 
					</th>
				</tr>
			</thead>
			<tbody>	
				<tr>
				
					<td colspan="10" style="text-align:center;padding:10px 0;font-size:0.9em">
					<table class="table-noborder" style="width:100%">
						<tbody>
							<tr>
<%
	if(v_pihak==null || v_pihak.size()<1) {
%>
								<td style="text-align:center;width:100%">N/A</td></tr>
<%		
	}
	else {
	int counter = 0;
	ListIterator li = v_pihak.listIterator();
	while(li.hasNext()) {
		boolean match = false;
		counter++;
		String brs = (String)li.next();
		st = new StringTokenizer(brs,"`");
		String job_opt = st.nextToken();
		//String sin = st.nextToken();
		if(counter%3!=0) {
%>
								<td align="left" style="vertical-align: top;padding:0 20px"><input type="checkbox" name="job_input" value="<%=job_opt %>" <% if(job_input!=null && job_input.length>0 && job_opt.equalsIgnoreCase(job_input[0])) { out.print("checked"); } %>> <%=job_opt %></td>
<%
		}
		else {
			%>
								<td align="left" style="vertical-align: top;padding:0 20px"><input type="checkbox" name="job_input" value="<%=job_opt %>" <% if(job_input!=null && job_input.length>0 && job_opt.equalsIgnoreCase(job_input[0])) { out.print("checked"); } %>> <%=job_opt %></td>
							</tr>
							<tr>
<%			
		}
		if(!li.hasNext()) {
			%>
							</tr>
			<%		
		}
	}
	}
%>
						</tbody>	
					</table>
					</td>
				</tr>
			<thead>	  			
				<tr>
					<th colspan="10" style="background:#a6bac4;border-left: none;text-align:left;height:30px;vertical-align:middle;padding:0 5px;width:95%;border:none;font-size:1.0em;font-weight:bold">
					&nbsp&nbsp&nbsp&nbspLIST GROUP 
					</th>
				</tr>
			</thead>
				<tr>
					<td colspan="10" style="text-align:center;padding:10px 0;font-size:0.9em">
					<table class="table-noborder" style="width:100%">	
						<tbody>
							<tr>
<%
if(v_group==null || v_group.size()<1) {
%>
							<td style="text-align:center;width:100%">N/A</td></tr>
<%		
}
else {
	int counter = 0;
	ListIterator li = v_group.listIterator();
	while(li.hasNext()) {
		boolean match = false;
		counter++;
		String brs = (String)li.next();
		st = new StringTokenizer(brs,"`");
		String job_opt = st.nextToken();
		//String sin = st.nextToken();
		if(counter%3!=0) {
%>
								<td align="left" style="vertical-align: top;padding:0 20px"><input type="checkbox" name="job_input" value="<%=job_opt %>" <% if(job_input!=null && job_input.length>0 && job_opt.equalsIgnoreCase(job_input[0])) { out.print("checked"); } %>> <%=job_opt %></td>
<%
		}
		else {
			%>
								<td align="left" style="vertical-align: top;padding:0 20px"><input type="checkbox" name="job_input" value="<%=job_opt %> <% if(job_input!=null && job_input.length>0 && job_opt.equalsIgnoreCase(job_input[0])) { out.print("checked"); } %>"> <%=job_opt %></td>
							</tr>
							<tr>
<%			
		}
		if(!li.hasNext()) {
			%>
							</tr>
			<%		
		}
	}
}	
%>
						</tbody>	
					</table>
					</td>
				</fieldset>	
				</tr>	
			</tbody>
			<tr>
				<thead>
					<th colspan="10" style="border-left: none;text-align:left;height:30px;vertical-align:middle;padding:0 5px;border:none;font-size:1.0em;font-weight:bold">
					PERIODE REPETISI PENGAWASAN DILAKUKAN PER
					<input type="number" name="tot_repetisi" placeholder="1" value="<%=Checker.pnn(tot_repetisi) %>" style="height:28px;text-align:center;border:none;width:50px" required/> &nbsp
					<select name="satuan" style="height:28px;border:none;width:100px" required>
						<option value="null">pilih satuan</option>
						<option value="hr" <% if(satuan!=null && satuan.equalsIgnoreCase("hr")) { out.print("selected=selected"); } %>>JAM</option>
						<option value="day" <% if(satuan!=null && satuan.equalsIgnoreCase("day")) { out.print("selected=selected"); } %>>HARI</option>
						<option value="week" <% if(satuan!=null && satuan.equalsIgnoreCase("week")) { out.print("selected=selected"); } %>>MINGGU</option>
						<option value="mon" <% if(satuan!=null && satuan.equalsIgnoreCase("mon")) { out.print("selected=selected"); } %>>BULAN</option>
						<option value="yr" <% if(satuan!=null && satuan.equalsIgnoreCase("yr")) { out.print("selected=selected"); } %>>TAHUN</option>
						<option value="dkd" <% if(satuan!=null && satuan.equalsIgnoreCase("dkd")) { out.print("selected=selected"); } %>>DEKADE</option>
					</select>
					</th>	
				</thead>
			</tr>
			<tr>
				<td style="padding:10px 10px">
				<section class="gradient">
					<div style="text-align:center">
						<button style="padding: 5px 50px;font-size: 20px;">Add / Update Manual</button>
					</div>
				</section>
				</td>
			</tr>
		<fieldset>	
		</table>
	</form>
	</center>
		<br><br>

		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>
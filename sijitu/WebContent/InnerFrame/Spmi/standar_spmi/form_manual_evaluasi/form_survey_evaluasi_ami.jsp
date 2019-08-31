<!DOCTYPE html>
<head>
<%@ page import="beans.sistem.*" %>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.SearchStandarMutu"%>
<%@page import="beans.dbase.spmi.manual.*"%>
<%@page import="beans.dbase.spmi.*"%>
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


/*
KHUSUS UNTUK FORM SURVEY DIBERI KEMUDAHAN DIMANA SCOPE KDPST BUKAN DARI PILIHAN PERTAMA DARI MENU PENJAMINAN MUTU
TAPI BERDASARKAN SKOPE COMAAND AGAR MEMPERMUDAH PROSES PENGAWASAN
*/
String thsms_now = Checker.getThsmsNow();
Vector v_scope_cmd = validUsr.returnTknDistinctInfoProdiOnlyGivenCommand("hasSpmiMenu", true, thsms_now);
Vector v_list_target_civitas = (Vector)request.getAttribute("v_list"); //hasil searching mode
request.removeAttribute("v_list");
SearchDbJabatan sdb = new SearchDbJabatan(validUsr.getNpm());
Vector v_pihak = sdb.getListAvalablePihakTerkait_v1(false);
Vector v_group = sdb.getListAvalablePihakTerkait_v1(true);

String status_manual = request.getParameter("status_manual");
String at_menu_dash = request.getParameter("at_menu_dash");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
String at_menu_kendal = request.getParameter("at_menu_kendal");
String id_versi = request.getParameter("id_versi");
String id_std_isi = request.getParameter("id_std_isi");
String id_std = request.getParameter("id_std");
String norut_man = request.getParameter("norut_man");
String std_kdpst = request.getParameter("std_kdpst");
String scope_std = request.getParameter("scope_std");
String target_unit_used = request.getParameter("target_unit_used");

//dari form
String nmm_obj_eval = request.getParameter("nmm_obj_eval");
String kdpst_kendali = request.getParameter("kdpst_kendali");
String tgl = request.getParameter("tgl");
String waktu = request.getParameter("waktu");
String target_val = request.getParameter("target_val");
String ril_val = request.getParameter("ril_val");
String indikator = request.getParameter("indikator");
String sikon = request.getParameter("sikon");
String analisa = request.getParameter("analisa");
String rekomendasi = request.getParameter("rekomendasi");
String next_waktu_survey = request.getParameter("next_waktu_survey");
String next_tgl_survey = request.getParameter("next_tgl_survey");



kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
//String id_kendali = request.getParameter("id_kendali");


//TIPE_SARPRAS & CATAT_CIVITAS didiapat dari vector, beda dengan form passing param dari menunya
//String tipe_sarpras = request.getParameter("tipe_sarpras");
//String catat_civitas = request.getParameter("catat_civitas");
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);	
//param dari form di page ini
SearchManual sm = new SearchManual();
//System.out.println("pit 1");
//System.out.println("versi_id="+id_versi);
//System.out.println("std_isi_id="+id_std_isi);
//System.out.println("norut_man="+norut_man);

//tidak berubah ini info std jadi riwayat evaluasi & ami sama2 pake ini
Vector v = sm.prepInfoFormEvaluasi(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), Integer.parseInt(norut_man)); 
//System.out.println("pit 2");
//String list_tipe_sarpras = Getter.getListTipeSarpras();
//Vector v_info_sarpras = Getter.getListDetilInfoSarpras();
//prepInfoFormSureveyPengendalian

boolean am_i_controller = true;
boolean am_i_surveyor = true;
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
<jsp:include page="menu_back_ami.jsp" />
<div class="colmask fullpage">
	<div class="col1">
	<br>
	<!-- Column 1 start -->
		<div style="text-align:center;padding:0 0 0 3px">
		<span class="tile-group-title">
			<select style="width:100%;height:25px;border:none;text-align-last:center;border-radius: 10px;" disabled>
				<option value="null" selected="selected"><%=keter_prodi%></option> 
			</select>
		</span>
	</div>
	
<%
Vector v_err = null;
v_err = (Vector) request.getAttribute("v_err");
request.removeAttribute("v_err");	
//String tgl = null;
//String waktu = null;
//String next_tgl_survey = null;
//String next_waktu_survey = null;
//String ril_val = null;
//String sikon = null;
//String analisa = null;
//String rekomendasi = null;


if(v_err!=null && v_err.size()>0) {
	ListIterator litmp = v_err.listIterator();
%>
	<div style="text-align:center;font-size:0.9em;color:red;font-weight:bold">
<%
	while(litmp.hasNext()) {
		String brs = (String)litmp.next();
		out.print("* "+brs+"<br>");
	}
	//get VALUE dari hasil validasi servlet
	/*
	tgl = (String) request.getAttribute("tgl");
	waktu = (String) request.getAttribute("waktu");
	next_tgl_survey = (String) request.getAttribute("next_tgl_survey");
	next_waktu_survey = (String) request.getAttribute("next_waktu_survey");
	ril_val = (String) request.getAttribute("ril_val");
	sikon = (String) request.getAttribute("sikon");
	analisa = (String) request.getAttribute("analisa");
	rekomendasi = (String) request.getAttribute("rekomendasi");
	request.removeAttribute("next_tgl_survey");
	request.removeAttribute("next_waktu_survey");
	
	request.removeAttribute("tgl");
	request.removeAttribute("waktu");
	request.removeAttribute("ril_val");
	request.removeAttribute("sikon");
	request.removeAttribute("analisa");
	request.removeAttribute("rekomendasi");
	*/
	
%>	
	</div>
	
<%	
}
%>		
		<br>
<%
if(v!=null && v.size()>0) {
	
	ListIterator li = v.listIterator();
	if(li.hasNext()) {
		id_versi = (String)li.next();
		id_std_isi = (String)li.next();
		norut_man = (String)li.next();
		String tgl_sta = (String)li.next();
		String tgl_end = (String)li.next();
		String tkn_jab_rumus = (String)li.next();
		String tkn_jab_cek = (String)li.next();
		String tkn_jab_stuju = (String)li.next();
		String tkn_jab_tetap = (String)li.next();
		String tkn_jab_kendali = (String)li.next();
		String tujuan = (String)li.next();
		String lingkup = (String)li.next();
		String definisi = (String)li.next();
		String prosedur = (String)li.next();
		String kuali = (String)li.next();
		String doc = (String)li.next();
		String ref = (String)li.next();
		
		//get info std_isi
		id_std = (String)li.next();
		String pernyataan_isi_std = (String)li.next();
		String stgl_sta = (String)li.next();
		String stgl_end = (String)li.next();
		String thsms1 = (String)li.next();
		String thsms2 = (String)li.next();
		String thsms3 = (String)li.next();
		String thsms4 = (String)li.next();
		String thsms5 = (String)li.next();
		String thsms6 = (String)li.next();
		String[]target_thsms = {thsms1,thsms2,thsms3,thsms4,thsms5,thsms6};
		String pihak_terkait = (String)li.next();
		String doc_terkait = (String)li.next();
		indikator = (String)li.next();
		String target_thsms_sta = (String)li.next();
		String satuan_periode = (String)li.next();
		String lama_per_period = (String)li.next();
		String target_unit_thsms1 = (String)li.next();
		String target_unit_thsms2 = (String)li.next();
		String target_unit_thsms3 = (String)li.next();
		String target_unit_thsms4 = (String)li.next();
		String target_unit_thsms5 = (String)li.next();
		String target_unit_thsms6 = (String)li.next();
		String[]target_unit_thsms = {target_unit_thsms1,target_unit_thsms2,target_unit_thsms3,target_unit_thsms4,target_unit_thsms5,target_unit_thsms6};
		String monitoree = (String)li.next();
		String param = (String)li.next();
		String aktif = (String)li.next();

		ToolSpmi ts = new ToolSpmi();
		int periode_ke = ToolSpmi.getNorutTargetParamIndikatorPeriod(target_thsms_sta, satuan_periode, lama_per_period)-1; //dikurang 1 krn buat index array
		//System.out.println("periode_ke="+periode_ke);
		//int periode_ke = ts.cekThsmsNowMasukPeriodeKeberapa(Integer.parseInt(id_std_isi), Integer.parseInt(id_versi));
%>

<form action="go.updRiwayatEvalAmi" method="post">
	<input type="hidden" name="status_manual" value="<%=status_manual %>" />
	<input type="hidden" name="target_unit_used" value="<%=target_unit_used %>" />
	<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp %>" />
	<input type="hidden" name="id_versi" value="<%=id_versi %>" />
	<input type="hidden" name="id_std_isi" value="<%=id_std_isi %>" />
	<input type="hidden" name="norut_man" value="<%=norut_man%>" />
	<input type="hidden" name="id_std" value="<%=id_std %>" />
	<input type="hidden" name="at_menu_kendal" value="<%=at_menu_kendal %>" />
	<input type="hidden" name="at_menu_dash" value="<%=at_menu_dash %>" />
	<input type="hidden" name="indikator" value="<%=indikator %>" />
	<input type="hidden" name="std_kdpst" value="<%=std_kdpst %>" />
	<input type="hidden" name="scope_std" value="<%=scope_std %>" />

	
	<table class="table1" id="tabel" style="width:100%" >
		<thead>
			<tr>
				<td colspan="6" style="font-size:2em;text-align:center;font-weight:bold;color:#369">
					MANUAL EVALUASI
				</td>
			</tr>
			<tr>
				<td colspan="6" style="vertical-align:top;text-align:left;padding:5px 5px;font-size:1.0em;background:white">
				<%=prosedur %>	
				</td>
			</tr>
			<tr>
				<td colspan="6" style="font-size:2em;text-align:center;font-weight:bold;color:#369">
					FORM KEGIATAN EVALUASI
				</td>
			</tr>
			<%
			if(scope_std.equalsIgnoreCase("prodi")) {
			%>
			<tr>
				<th colspan="6" style="font-size:1.3em;text-align:left;font-weight:bold;padding:5px 5px">
				PRODI YANG SEDANG DI SURVEY
				</th>
			</tr>
			<tr>
				<td colspan="6">
					<select name="kdpst_kendali" style="width:100%;height:30px;border:none;background:white;text-align-last:center">
					<!--  option disabled="disabled" value="null">-- Pilih Prodi --</option -->	
			<%
				if(v_scope_cmd!=null && v_scope_cmd.size()>0) {
					ListIterator litmp = v_scope_cmd.listIterator();
					while(litmp.hasNext()) {
						String brs = (String)litmp.next();
						//nmpst+"`"+kdpst+"`"+kdjen+"`"+kdfak);
						st = new StringTokenizer(brs,"`");
						String nmprodi = st.nextToken();
						String prodi = st.nextToken();
						if(Checker.isStringNullOrEmpty(std_kdpst)) {
							//seluruh prodi
							if(prodi.contains(kdpst)) {
			%>
					<option value="<%=brs%>" selected="selected"><%=Converter.getDetailKdpst_v1(prodi) %></option>
			<%			
							}
							else {
			%>
					<!--  option disabled="disabled" value="<=brs%>"><%=Converter.getDetailKdpst_v1(prodi) %></option -->
			<%		
							}
						}
						else {
							if(prodi.contains(std_kdpst)) {
						%>
					<!--  option disabled="disabled" value="<%=brs%>" selected="selected"><%=Converter.getDetailKdpst_v1(prodi) %></option -->
						<%			
							}
						}
				
					}
				}
			%>
				
					</select>
				</td>
			</tr>
	<%
			}
	%>			
			
			<tr style=";vertical-align:middle;">
				<thead>	
				<th colspan="6" style="valign:top;font-size:1.3em;text-align:left;font-weight:bold;padding:0 0 2px 0;vertical-align:middle">
				<%
				String dt = AskSystem.getLocalDate();
				String tm = ""+AskSystem.getWaktuSekarang();
			%>
					<table style="width:100%;border:none">
						<tr>
							<td style="width:60%;border:none;padding:0 0 0 10px">
			<%
				if(Checker.isStringNullOrEmpty(tgl)) {
			%>
					TGL KEGIATAN : <input type="text" name="tgl" value="<%=dt %>" placeholder="tgl/bln/thn" style="height:30px;vertical-align:middle;border:none;width:150px;text-align:center;font-size:1.2em" required/>
			<%	
				}
				else {
			%>
					TGL KEGIATAN : <input type="text" name="tgl" value="<%=tgl %>" placeholder="tgl/bln/thn" style="height:30px;vertical-align:middle;border:none;width:150px;text-align:center;font-size:1.2em" required/>
			<%
				}
			%>				
							</td>
							<td style="width:40%;border:none;padding:0 0 0 10px">
			<%
				if(Checker.isStringNullOrEmpty(waktu)) {
			%>
				&nbsp&nbsp  JAM : <input type="text" name="waktu" placeholder="hh:mm" value="<%=tm %>" style="height:30px;border:none;width:200px;text-align:center;font-size:1.2em" required/>
			<%
				}
				else {
			%>
				&nbsp&nbsp  JAM : <input type="text" name="waktu" placeholder="hh:mm" value="<%=waktu %>" style="height:30px;border:none;width:200px;text-align:center;font-size:1.2em" required/>
			<%		
				}
			%>					
							</td>
						</tr>
					</table>
			
					 
				</th>
				</thead>
			</tr>
			<tr>
				<thead>
				<th colspan="6" style="font-size:1.3em;text-align:left;font-weight:bold;">
					<table style="border:none;width:100%">
						<tr>
							<td style="width:5%;border:none;padding:0 0 0 10px">
							A.
							</td>
							<td style="width:65%;border:none;padding:0 0 0 10px">
							PARAMETER YG DIEVALUASI
							</td>
							<td colspan="2" style="background:white;color:#369;width:13%;font-size:0.8em;text-align:center;padding:0 0 0 0">
							<%=param.toUpperCase() %>	
							</td>
						</tr>
						<tr>
							<td rowspan="2" style="width:5%;border:none;padding:0 0 0 10px">
							B.
							</td>
							<td rowspan="2" style="width:65%;border:none;padding:0 0 0 10px">
							INDIKATOR CAPAIAN
							</td>
							<td style="background:<%=Constant.lightColorBlu()%>;color:#369;width:13%;font-size:0.8em;text-align:center;padding:0 0 0 0">
							TARGET
							</td>
							<td style="background:<%=Constant.lightColorBlu()%>;color:#369;width:17%;font-size:0.8em;text-align:center;padding:0 0 0 0">
							RIL
							</td>
						</tr>
							<td colspan="2" style="background:<%=Constant.lightColorBlu()%>;color:#369;width:13%;font-size:0.8em;text-align:center;padding:0 0 0 0">
							<%=target_unit_used %>
							</td>
						<tr>
						</tr>
					</table>
				</th>
				</thead>
			</tr>
			<tr>
				<td colspan="6" style="vertical-align:top;text-align:center;padding:0 0;font-size:1.0em;background:white">
					<table style="border:none;width:100%">
						<tr>
							<td style="width:70%;text-align:left;padding: 0 0 0 5px">
							<%=indikator.toUpperCase() %>
							
							</td>
							<td style="width:13%;text-align:center;vertical-align:middle">
							<%=target_thsms[periode_ke] %>
							<input type="hidden" name="target_val" value="<%=target_thsms[periode_ke] %>"/>
							<%
							String unit_used = new String(target_unit_thsms[periode_ke]);
							%>
							<!--  input type="hidden" name="unit_used" value="<=unit_used %>"/-->
							</td>
							<td style="width:17%;text-align:center;vertical-align:middle">
							<%
							if(Checker.isStringNullOrEmpty(ril_val)) {
								if(target_unit_thsms[periode_ke].equalsIgnoreCase("percent")) {
							%>
								<input type="number" step="0.01" name="ril_val" min="0" max="100" style="width:80%;border:none;height:35px;text-align:center" required/>
							<%	
								}
								else {
								%>
								<input type="number" step="0.01" name="ril_val" min="0" style="width:80%;border:none;height:35px;text-align:center" required/>
								<%	
								}
							}
							else {
								if(target_unit_thsms[periode_ke].equalsIgnoreCase("percent")) {
							%>
								<input type="number" step="0.01" name="ril_val" min="0" max="100" value="<%=ril_val %>" style="width:80%;border:none;height:35px;text-align:center" required/>
							<%			
								}
								else {
									%>
								<input type="number" step="0.01" name="ril_val" min="0" value="<%=ril_val %>" style="width:80%;border:none;height:35px;text-align:center" required/>
								<%		
								}
							}
							
							if(target_unit_thsms[periode_ke].equalsIgnoreCase("percent")) {
							//	out.print("&nbsp%");
							}
							%>
							</td>
						</tr>
					</table>	
				</td>
			</tr>
			<tr>
				<thead>
				<th colspan="6" style="font-size:1.3em;text-align:left;font-weight:bold;padding:0 0 0 10px">
					C. GAMBARKAN KONDISI PADA SAAT PENGAWASAN
				</th>
				</thead>
			</tr>
			<tr>
				<td colspan="6" style="vertical-align:top;text-align:center;padding:5px 5px;font-size:1.0em;background:white">
				<%
				String keter = "null";
				if(!Checker.isStringNullOrEmpty(sikon)) {
					keter = sikon;
				}
				String tmp = "1. Jelaskan kondisi proses yg terjadi dilapangan&#13;&#10;2. Perhatikan proses yg telah/dapat menimbulkan penyimpangan pada target capaian&#13;&#10;3. Jelaskan efek (berantai) dari permasalahan terhadap target capaian";
				%>
					<textarea name="sikon" minlength="15" style="width:100%;height:100px;border:none;rows:5" placeholder="<%=tmp %>" required><%=Checker.pnn(keter) %></textarea>	
				</td>
			</tr>
			<tr>
				<thead>
				<th colspan="6" style="font-size:1.3em;text-align:left;font-weight:bold;padding:0 0 0 10px">
					D. ANALISA SUMBER MASALAH
				</th>
				</thead>
			</tr>
			<tr>
				<td colspan="6" style="vertical-align:top;text-align:center;padding:5px 5px;font-size:1.0em;background:white">
				<%
				keter = "null";
				if(!Checker.isStringNullOrEmpty(analisa)) {
					keter = analisa;
				}
				tmp = "1. Buat list permasalahan yg terjadi dilapangan&#13;&#10;2. Perkiraan sumber masalah&#13;&#10;[Gunakan metod 5 why's : Pertanyakan 5 urutan penyebab sehingga terjadi permasalahan]";
				%>
					<textarea name="analisa" minlength="15" style="width:100%;height:100px;border:none;rows:5" placeholder="<%=tmp %>" required><%=Checker.pnn(keter) %></textarea>	
				</td>
			</tr>
			<tr>
				<thead>
				<th colspan="6" style="font-size:1.3em;text-align:left;font-weight:bold;padding:0 0 0 10px">
					E. REKOMENDASI TINDAKAN PENGENDALIAN
				</th>
				</thead>
			</tr>
			<tr>
				<td colspan="6" style="vertical-align:top;text-align:center;padding:5px 5px;font-size:1.0em;background:white">
				<%
				keter = "null";
				if(!Checker.isStringNullOrEmpty(rekomendasi)) {
					keter = rekomendasi;
				}
				tmp = "Tuliskan list rekomendasi tindakan pengendalian";
				%>
					<textarea name="rekomendasi" minlength="15" style="width:100%;height:100px;border:none;rows:5" placeholder="<%=tmp %>" required><%=Checker.pnn(keter) %></textarea>	
				</td>
			</tr>
			<tr>
				<thead>
				<th colspan="6" style="font-size:1.3em;text-align:left;font-weight:bold;padding:0 0 0 10px">
					F. NAMA / NIP CIVITAS / KODE RUANGAN YG SEDANG DIEVALUASI (OPTIONAL)
				</th>
				</thead>
			</tr>
			<tr style=";vertical-align:middle;">
				<td colspan="6" style="valign:top;font-size:1.3em;text-align:left;font-weight:bold;padding:0 0 2px 0;vertical-align:middle">
					<input type="text" name="nmm_obj_eval" placeholder="nama civitas / no induk pegawai civitas / nama ruangan / kode ruangan" value="<%=Checker.pnn(nmm_obj_eval)%>" style="height:30px;border:none;width:100%;text-align:center;font-size:1.2em"/>
				</td>
			</tr>
			<tr>
				<thead>
				<th colspan="6" style="font-size:1.3em;text-align:left;font-weight:bold;padding:0 0 0 10px">
					G. JADWAL PENGAWASAN BERIKUTNYA
				</th>
				</thead>
			</tr>
			<tr style=";vertical-align:middle;">
					
				<td colspan="6" style="valign:top;font-size:1.3em;text-align:left;font-weight:bold;padding:0 0 2px 0;vertical-align:middle">
				<%
				dt = AskSystem.getLocalDate();
				tm = ""+AskSystem.getWaktuSekarang();
			%>
					<table style="width:90%;border:none">
						<tr>
							<td style="width:50%;border:none;padding:0 0 0 10px;text-align:center">
			<%
				if(Checker.isStringNullOrEmpty(tgl)) {
			%>
					TGL : <input type="text" name="next_tgl_survey" value="<%=dt %>" placeholder="tgl/bln/thn" style="height:30px;vertical-align:middle;border:none;width:150px;text-align:center;font-size:1.2em" required/>
			<%	
				}
				else {
			%>
					TGL : <input type="text" name="next_tgl_survey" value="<%=next_tgl_survey %>" placeholder="tgl/bln/thn" style="height:30px;vertical-align:middle;border:none;width:150px;text-align:center;font-size:1.2em" required/>
			<%
				}
			%>				
							</td>
							<td style="width:50%;border:none;padding:0 0 0 10px;text-align:left">
			<%
				if(Checker.isStringNullOrEmpty(waktu)) {
			%>
				&nbsp&nbsp  JAM : <input type="text" name="next_waktu_survey" placeholder="hh:mm" value="<%=tm %>" style="height:30px;border:none;width:150px;text-align:center;font-size:1.2em" required/>
			<%
				}
				else {
			%>
				&nbsp&nbsp  JAM : <input type="text" name="next_waktu_survey" placeholder="hh:mm" value="<%=next_waktu_survey %>" style="height:30px;border:none;width:150px;text-align:center;font-size:1.2em" required/>
			<%		
				}
			%>					
							</td>
						</tr>
					</table>	 
				</td>
			</tr>
			<tr>
				<td colspan="6" style="padding:5px 0">
					<section class="gradient">
     					<div style="text-align:center">
           					<button style="padding: 5px 50px;font-size: 20px;">Insert Hasil Evaluasi</button>
     					</div>
					</section>
				</td>
			</tr>
		</thead>
		<%
		}//end if blum ada civitas
		%>	
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
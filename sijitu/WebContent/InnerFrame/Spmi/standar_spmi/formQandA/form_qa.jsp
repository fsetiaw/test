<!DOCTYPE html>
<html>
<head>
<title>Bootstrap-select test page</title>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="beans.dbase.spmi.request.*"%>
<%@ page import="beans.dbase.spmi.*"%>
<%@page import="beans.dbase.spmi.SearchStandarMutu"%>
<%@page import="beans.dbase.spmi.SearchQandA"%>
<title>UNIVERSITAS SATYAGAMA</title>
<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>

  	<script>
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
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9; }

.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px }
.table:hover td { background:#82B0C3 }
</style>	
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
//String fwdto = request.getParameter("fwdto");
//String status_manual = request.getParameter("status_manual");
String at_menu_dash = request.getParameter("at_menu_dash");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
String at_menu_kendal = request.getParameter("at_menu_kendal");
String id_versi = request.getParameter("id_versi");
String id_tipe_std = request.getParameter("id_tipe_std");
String id_master_std = request.getParameter("id_master_std");
String id_std = request.getParameter("id_std");
String id_std_isi = request.getParameter("id_std_isi");
String id_question = request.getParameter("id_question");
//String std_kdpst = request.getParameter("std_kdpst");
//String scope_std = request.getParameter("scope_std");
//String target_unit_used = request.getParameter("target_unit_used");
//boolean man_sdh_aktif = false;
//if(status_manual!=null && status_manual.equalsIgnoreCase("AKTIF")) {
//	man_sdh_aktif = true;
//}
//String src_limit = request.getParameter("limit");

//System.out.println("id_std_isi="+id_std_isi);
//request.removeAttribute("v");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
//String id_kendali = request.getParameter("id_kendali");

SearchStandarMutu ssm = new SearchStandarMutu();
String isi_std = ssm.getPernyataanIsiStd(id_std_isi);
%>	
</head>
<body>
<jsp:include page="go_back.jsp" />
<div class="colmask fullpage">
	<div class="col1">
	<%="id_question="+id_question %>
	<br><br>
	<form action="go.updateQandA">
	<input type="hidden" name="at_menu_dash" value="<%=at_menu_dash %>"/>
	<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp %>"/>
	<input type="hidden" name="at_menu_kendal" value="<%=at_menu_kendal %>"/>
	<input type="hidden" name="id_versi" value="<%=id_versi %>"/>
	<input type="hidden" name="id_tipe_std" value="<%=id_tipe_std %>"/>
	<input type="hidden" name="id_master_std" value="<%=id_master_std %>"/>
	<input type="hidden" name="id_std" value="<%=id_std %>"/>
	<input type="hidden" name="id_std_isi" value="<%=id_std_isi %>"/>
	<input type="hidden" name="id_question" value="<%=id_question %>"/>
	<table class="table" style="width:100%">
		<tr>
			<td colspan="4" style="border:none;width:98%;text-align:center;padding:5px 2px 5px 5px;background:<%=Constant.darkColorBlu()%>;color:#fff;font-size:1.1em;font-weight:bold">
					PERNYATAAN ISI STANDAR
			</td>
		</tr>
		<tr>
			<td colspan="4" style="border:none;width:98%;text-align:center;padding:5px 2px 5px 5px;background:<%=Constant.lightColorBlu()%>;color:#369;font-size:1.1em;font-weight:bold">
					<%=isi_std %>
			</td>
		</tr>
		<tr>
			<td style="width:50px;background:<%=Constant.lightColorGrey() %>;color:#369;text-align:center;font-size:1em">
				No.
			</td>
			<td colspan="2" style="background:<%=Constant.lightColorGrey() %>;color:#369;text-align:center;font-size:1em">
				Pertanyaan & Penilaian Jawaban (Q&A)
			</td>
			<td rowspan="2" style="vertical-align:middle;width:100px;background:<%=Constant.lightColorGrey() %>;color:#369;text-align:center;font-size:1em">
				Bobot Nilai
			</td>
		</tr>
		<tr>
			<td rowspan="6" style="vertical-align:top;background:#fff;color:#369;text-align:center;font-size:0.9em">
				1.
			</td>
			<td colspan="2" style="border-right:0px;color:#fff;padding:0 0 0 5px;background:#fff;color:#369;text-align:left;font-size:0.9em">
		<%
		SearchQandA sqa = new SearchQandA();
		String question = sqa.getPertanyaan(id_question);
		//if(Checker.isStringNullOrEmpty(question)) {
		%>
				<textarea name="question" minlength="15" style="width:100%;height:80px;border:none;rows:5" placeholder="isi pertanyaan yang harus ditanyakan pada saat audit internal" required><%=Checker.pnn(question) %></textarea>
		<%	
		//}
		%>
			</td>
		</tr>
		<%
		int max_jawab=5;
		Vector v_ans = sqa.getListIdJawabanNilaiDariPertanyaan(id_question);
		if(v_ans==null || v_ans.size()<1) {
			for(int j=0;j<max_jawab;j++) {
		%>
		<tr>
			<td style="vertical-align:top;border-right:0px;color:#fff;background:#fff;color:#369;text-align:center;width:5%;font-size:0.9em">
				<%=j+1 %>.
			</td>
			<td style="vertical-align:top;border-left:0px;color:#fff;padding:0 0 0 5px;background:#fff;color:#369;text-align:left;width:70%;font-size:0.9em">
		<%
				if(j==4) {
		%>		
				<textarea name="answer" minlength="1" style="width:100%;height:40px;border:none;rows:2" placeholder="isikan jawaban/kriteria kondisi serta bobot penilaiannya"></textarea>
		<%					
				}
				else {
		%>		
				<textarea name="answer" minlength="1" style="width:100%;height:40px;border:none;rows:2" placeholder="isikan jawaban/kriteria kondisi serta bobot penilaiannya" required></textarea>
		<%
				}
		%>		
			</td>
			<td style="vertical-align:middle;background:#fff;color:#369;text-align:center;width:25%;font-size:0.9em">
		<%
				if(j==4) {
		%>		
				<textarea name="nilai" minlength="1" style="text-align:center;width:100%;height:40px;border:none;rows:2" placeholder="4isi bobot nilai"></textarea>
		<%			
				}
				else {
		%>		
				<textarea name="nilai" minlength="1" style="text-align:center;width:100%;height:40px;border:none;rows:2" placeholder="isi bobot nilai" required></textarea>
		<%
				}
		%>		
			</td>
		</tr>
		<%		
			}
		}
		else {
			ListIterator lit = v_ans.listIterator();
			int counter=0;
			while(lit.hasNext() && counter<max_jawab) {
				String brs = (String)lit.next();
				st = new StringTokenizer(brs,"~");
				String id = st.nextToken();
				String answer = st.nextToken();
				String nilai = st.nextToken();
				%>
		<tr>
			<td style="vertical-align:top;border-right:0px;color:#fff;background:#fff;color:#369;text-align:center;width:5%;font-size:0.9em">
				<%=counter+1 %>.
			</td>
			<td style="vertical-align:top;border-left:0px;color:#fff;padding:0 0 0 5px;background:#fff;color:#369;text-align:left;width:70%;font-size:0.9em">
		<%
				if(counter==4) {
		%>		
				<textarea name="answer" minlength="1" style="width:100%;height:40px;border:none;rows:2" placeholder="isikan jawaban/kriteria kondisi serta bobot penilaiannya"><%=Checker.pnn(answer) %></textarea>
		<%					
				}
				else {
		%>						
				<textarea name="answer" minlength="1" style="width:100%;height:40px;border:none;rows:2" placeholder="isikan jawaban/kriteria kondisi serta bobot penilaiannya" required><%=Checker.pnn(answer) %></textarea>
		<%
				}
		%>		
			</td>
			<td style="vertical-align:middle;background:#fff;color:#369;text-align:center;width:25%;font-size:0.9em">
		<%
				if(counter==4) {
		%>		
				<textarea name="nilai" minlength="1" style="text-align:center;width:100%;height:40px;border:none;rows:2" placeholder="4isi bobot nilai"><%=Checker.pnn(nilai) %></textarea>
		<%			
				}
				else {
		%>				
				<textarea name="nilai" minlength="1" style="text-align:center;width:100%;height:40px;border:none;rows:2" placeholder="isi bobot nilai" required><%=Checker.pnn(nilai) %></textarea>
		<%
				}
		%>		
			</td>
		</tr>
				<%	
				counter++;
			}
			for(;counter<max_jawab;counter++) {
				%>
		<tr>
			<td style="vertical-align:top;border-right:0px;color:#fff;background:#fff;color:#369;text-align:center;width:5%;font-size:0.9em">
				<%=counter+1 %>.
			</td>
			<td style="vertical-align:top;border-left:0px;color:#fff;padding:0 0 0 5px;background:#fff;color:#369;text-align:left;width:70%;font-size:0.9em">
		<%
				if(counter==4) {
		%>		
				<textarea name="answer" minlength="1" style="width:100%;height:40px;border:none;rows:2" placeholder="isikan jawaban/kriteria kondisi serta bobot penilaiannya"></textarea>
		<%					
				}
				else {
		%>							
				<textarea name="answer" minlength="1" style="width:100%;height:40px;border:none;rows:2" placeholder="isikan jawaban/kriteria kondisi serta bobot penilaiannya" required></textarea>
		<%
				}
		%>		
			</td>
			<td style="vertical-align:middle;background:#fff;color:#369;text-align:center;width:25%;font-size:0.9em">
		<%
				if(counter==4) {
		%>		
				<textarea name="nilai" minlength="1" style="text-align:center;width:100%;height:40px;border:none;rows:2" placeholder="4isi bobot nilai"></textarea>
		<%			
				}
				else {
		%>			
				<textarea name="nilai" minlength="1" style="text-align:center;width:100%;height:40px;border:none;rows:2" placeholder="isi bobot nilai" required></textarea>
				<%
				}
		%>			
			</td>
		</tr>
				<%		
			}
		}
		
	%>	
		<tr>
			<td colspan="4" style="padding:10px 10px;background:<%=Constant.lightColorBlu()%>;">
				<section class="gradient">
				<div style="text-align:center">
					<button style="padding: 5px 50px;font-size: 20px;">UPDATE</button>
				</div>
				</section>
			</td>
		</tr>		
	</table>
	</form>
	</div>
</div>	
</body>
</html>
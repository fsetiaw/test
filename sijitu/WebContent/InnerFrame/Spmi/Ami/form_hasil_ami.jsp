<!DOCTYPE html>
<head>

<%@ page import="beans.sistem.*" %>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.ToolSpmi"%>
<%@page import="beans.dbase.spmi.SearchStandarMutu"%>
<%@page import="beans.dbase.spmi.manual.*"%>
<%@page import="beans.dbase.spmi.riwayat.pengendalian.*"%>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.jabatan.SearchDbJabatan"%>
<%@page import="beans.dbase.spmi.*"%>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
  <link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
    <link href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/css/metro.css" rel="stylesheet">
    <link href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/css/metro-icons.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
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
boolean editor = (Boolean) session.getAttribute("spmi_editor");
boolean team_spmi = (Boolean) session.getAttribute("team_spmi");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
//System.out.println("kdpst_nmpst_kmp="+kdpst_nmpst_kmp);
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);


//dari foem
/*
String kode_activity = (String)request.getParameter("kode_activity");
String tgl_plan = (String)request.getParameter("tgl_plan");
String ketua_tim = (String)request.getParameter("ketua_tim");
String[]anggota_tim = (String[])request.getParameterValues("anggota_tim");
String[]cek = (String[])request.getParameterValues("cek");
*/
Vector v_QA = (Vector)session.getAttribute("v_QA");
//System.out.println("v_QAsize = "+v_QA.size());
session.removeAttribute("v_QA");
//id_master_std+"`"+ket_tipe_std



%>
<style>
a.img:hover {
	text-decoration: none;
	background:none;
}

a.hover {
	text-decoration: none;
	background:none;
}

a.hover:hover {
	text-decoration: none;
	background:none;
}

.table {
border: 1px solid #fff;
background:<%=Constant.lightColorBlu()%>;
}
.table thead > tr > th {
border-bottom: 1px solid #fff;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #fff; }

.table-noborder { border: 1px solid #fff;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: 1px solid #fff;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: 1px solid #fff;padding: 2px }
/*.table:hover td { background:#82B0C3 }*/
</style>
<style>
.table1 {
border: 1px solid #fff;
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
.table2 {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
}
.table2 thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table2 thead > tr > th, .table2 tbody > tr > t-->h, .table2 tfoot > tr > th, .table2 thead > tr > td, .table2 tbody > tr > td, .table2 tfoot > tr > td { border: 1px solid #2980B9; }

.table2-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table2-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table2-noborder thead > tr > th, .table2-noborder tbody > tr > th, .table2-noborder tfoot > tr > th, .table2-noborder thead > tr > td, .table2-noborder tbody > tr > td, .table2-noborder tfoot > tr > td { border: none;padding: 2px }
.table2 tr:hover td { background:#82B0C3 }
</style>
<script>
	$(document).ready(function() {
		$("#wait").hide();
		$("#main").show();
		/*
		$('table.CityTable th') .parents('table.CityTable') .children('tbody') .toggle();
		*/
	});	
</script>
<script type="text/javascript">
$(document).ready(function(){
	
	$('table.CityTable th') .click(
		function() {
			$(this) .parents('table.CityTable') .children('tbody') .toggle();
		}
	)
		
	$('table.StateTable tr.statetablerow th') .click(
		function() {
			$(this) .parents('table.StateTable') .children('tbody') .toggle();
		}
	)
	
	

	
});
</script>
<style type="text/css">

	table.CityTable, table.StateTable{border-color:#1C79C6; text-align:center;}
	table.StateTable{margin:20px; border:1px solid #1C79C6;}
	
	table td{}
	table.StateTable thead th{background: #1C79C6; padding: 10px; cursor:pointer; color:white;}
	table.CityTable thead th{padding: 10px; background: #C7DBF1;cursor:pointer; color:black;}
	table.CityTable thead tr:hover th { background:#82B0C3; }
</style>
</head>
<body>
<jsp:include page="menu_back_indek_pelaksanaan_ami.jsp" />
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
		
		<center>
		<br>
<%
if(v_QA!=null && v_QA.size()>0) {
	//NAVIGASI PART
	int max_per_page=10;
	
	String id_ami = (String)session.getAttribute("id_ami");
	String id_master = (String)session.getAttribute("id_master_std");
	String at_page = request.getParameter("at_page");
	
	//System.out.println("id_ami="+id_ami);
	//System.out.println("at_page="+at_page);
	int tot_QA = ToolSpmi.getTotalQandA(Integer.parseInt(id_ami),Integer.parseInt(id_master),false,null);
	int tot_pg = (int)Math.ceil(tot_QA/(float)max_per_page);
	//System.out.println("tot_QA="+tot_QA);
	//System.out.println("tot_page="+tot_pg);
	boolean ada_next=false;
	boolean ada_prev=false;
	
	
	
	
	int this_page=Integer.parseInt(at_page);
	int norut_min_this_pg=1;
	int norut_max_this_pg=1;
	int next_page=0;
	int prev_page=0;
	if(this_page<=1) {
		if(tot_QA<=max_per_page) {
			norut_max_this_pg=tot_QA;
		}
		else {
			ada_next=true;
			next_page=this_page+1;
			norut_max_this_pg=max_per_page;
		}
	}
	else {
		ada_prev=true;
		prev_page=this_page-1;
		norut_min_this_pg = (this_page*max_per_page)-max_per_page+1;
		norut_max_this_pg=(this_page*max_per_page);
		if(max_per_page>1) {
			norut_max_this_pg=norut_max_this_pg+max_per_page;
		}
				
		if(tot_QA<=norut_max_this_pg) {
			norut_max_this_pg=tot_QA;
		}
		else {
			ada_next=true;
			next_page=this_page+1;
			
		}
	}
	
%>
<!--  a href="#" onclick="document.getElementById('updateHasilAmi').submit();"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/folder-doc-save.png" width="50" height="50" title="SAVE"><br></a><br -->

<form name="input_form" action="go.downloadVec2Excl?output_file_name=tmp_<%=AskSystem.getTime() %>" method="POST">
<button type="button" class="btn btn-default" onclick="document.input_form.submit()">
	<span class="glyphicon glyphicon-floppy-save"></span> Download
</button>
</form>
	
	
	

<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="width:95%">
   	<thead>
   		<tr>
         	<th colspan="3" style="text-align:center;padding:0 0 0 5px;font-size:2em">
         	FORM PENILAIAN AUDIT MUTU INTERNAL 
         	</th>
      	</tr>   
      	<tr>
       		<th style="width:10%;text-align:left">No</th>
         	<th style="width:70%;text-align:left">Pertanyaan & Hasil Penelitian</th>
         	<th style="width:20%">&nbsp</th>
      </tr>
   </thead>
   <tbody>
      
      <tr>
         <td class="nopad" colspan="3">
<%
	int i=0;
	int counter=0;
%>
	<input type="hidden" name="start_number" value="<%=(counter+1) %>"/>
<%
	ListIterator li = v_QA.listIterator();
	while(li.hasNext()) {
		i++;
		String brs = (String) li.next();
		//System.out.println(brs);
		st = new StringTokenizer(brs,"~");
		//ID_MASTER_STD
		String id_master_std = st.nextToken();
		//NAMA_MASTER_STD
		String nm_master_std = st.nextToken();
		//ID_STD
		String id_std = st.nextToken();
		//NAMA_STD
		String nm_std = st.nextToken();
		//ID_TIPE_STD
		String id_tipe_std = st.nextToken();
		//ID_STD_ISI
		String id_std_isi = st.nextToken();
		//SASARAN_VALUE
		String target_value = st.nextToken();
		//RIL_VALUE
		String ril_value = st.nextToken();
		//DOK_TERKAIT
		String dok_terkait = st.nextToken();
		//NORUT_QA
		String norut_question = st.nextToken();
		//RENCANA_KEGIATAN
		String rencana_kegiatan = st.nextToken();
		//ID_QUESTION
		String id_question = st.nextToken();
		//QUESTION
		String question = st.nextToken();
		//TKN_JAWABAN
		String tkn_jawaban = st.nextToken();
		//TKN_BOBOT
		String tkn_bobot = st.nextToken();
		//PELANGGARAN
		String pelanggaran = st.nextToken();
		//TIPE_PELANGGARAN
		String tipe_pelanggaran = st.nextToken();
		//PENYEBAB_PELANGGARAN
		String penyebab_pelanggaran = st.nextToken();
		//CATATAN_HASIL_AMI
		String note_hasil_ami = st.nextToken();
		//REKOMNDASI_HASIL_AMI
		String rekomendasi_hasil_ami = st.nextToken();
		//JAWABAN
		String jawaban = st.nextToken();
		//BOBOT
		String bobot = st.nextToken();
		if(i>=norut_min_this_pg && i<=norut_max_this_pg) {
			counter++;
			int no_soal=0;
			if(max_per_page==1) {
				no_soal = this_page;
			}
			else {
				//System.out.println("counter="+counter);
				no_soal=(this_page*max_per_page) + (counter-max_per_page);
				//System.out.println("no_soal="+no_soal);
			}
%>         
            <table class="CityTable" rules="all" cellpadding="0" cellspacing="0" style="width:100%">
               <thead>
               	
                  <tr>
                     <th style="font-size:1.3em;width:10%;text-align:left;border:1px solid #1C79C6;border-right:none"><%=no_soal %>.</th>
                     <th colspan="2" style="font-size:1.3em;text-align:left;border:1px solid #1C79C6;border-left:none"><%=question %></th>
                     
                  </tr>
               </thead>
               <tbody>
					<tr>
						<td style="background:<%=Constant.lightColorGrey() %>;font-weight:bold;color:black;width:10%;text-align:center;border:1px solid #1C79C6;border-right:none">
						&nbsp
						</td>
                     	<td style="background:<%=Constant.lightColorGrey() %>;font-weight:bold;color:black;width:70%;text-align:left;border:1px solid #1C79C6;border-left:none;border-right:none;padding:0 0 0 10px">Hasil Penilaian :</td>
                     	<td style="background:<%=Constant.lightColorGrey() %>;font-weight:bold;color:black;width:20%;text-align:center;text-align:center;border:1px solid #1C79C6;border-left:none">Bobot</td>
                  	</tr>
					<tr>
						<td style="font-weight:bold;color:black;width:10%;text-align:center;border:1px solid #1C79C6;border-right:none">
						&nbsp
						</td>
                     	<td style="font-weight:bold;color:black;width:70%;text-align:left;border:1px solid #1C79C6;border-left:none;border-right:none;padding:0 0 0 25px">
                     	<h2><%=Checker.pnn_v1(jawaban, "Belum Dilakukan Penilaian") %></h2>
                     	<%
                     	//out.print(jawaban);
                     	if(pelanggaran.equalsIgnoreCase("1")||pelanggaran.equalsIgnoreCase("true")) {
                     		out.print("<br>Pelanggaran&nbsp&nbsp&nbsp: Telah ditemukan pelanggaran");
                     	}
                     	if(!Checker.isStringNullOrEmpty(note_hasil_ami)) {
                     		out.print("<br>Catatan&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp: "+note_hasil_ami);
                     	}
                     	if(!Checker.isStringNullOrEmpty(rekomendasi_hasil_ami)) {
                     		out.print("<br>Rekomendasi&nbsp: "+rekomendasi_hasil_ami);
                     	}
                     	%></td>
                     	<td style="font-size:3em;font-weight:bold;color:black;width:20%;vertical-align:middle;text-align:center;border:1px solid #1C79C6;border-left:none;background: #F2F3F4">
                     		<%=Checker.pnn_v1(bobot, "N/A") %>
                     	</td>
                  	</tr>
                  
              </tbody>
           </table>
<%
		}
	}
%>           
        </td>
     </tr>
   </tbody>
</table>

<%
}
%> 
	</center>
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>
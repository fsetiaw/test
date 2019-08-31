<!DOCTYPE html>
<head>
<%@ page import="beans.sistem.*" %>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.ToolSpmi"%>
<%@page import="beans.dbase.spmi.SearchStandarMutu"%>
<%@page import="beans.dbase.spmi.manual.*"%>
<%@page import="beans.dbase.spmi.riwayat.pengendalian.*"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.jabatan.SearchDbJabatan"%>
<%@page import="beans.dbase.spmi.*"%>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
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
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);


//dari foem
String kode_activity = (String)request.getParameter("kode_activity");
String tgl_plan = (String)request.getParameter("tgl_plan");
String ketua_tim = (String)request.getParameter("ketua_tim");
String[]anggota_tim = (String[])request.getParameterValues("anggota_tim");
String[]cek = (String[])request.getParameterValues("cek");






SearchStandarMutu ss = new SearchStandarMutu();
Vector v_list_std = ss.getListMasterBookOfStandar();
//id_master_std+"`"+ket_tipe_std



%>
<style>
a.img:hover {
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
		
	});	
</script>
<script type="text/javascript">
$(document).ready(function(){
	
	$('table.CityTable th') .parents('table.CityTable') .children('tbody') .toggle();
	
	
	
	
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
	
	$('table.StateTable tr.statetablerow td') .click(
		function() {
			$(this) .parents('table.StateTable') .children('tbody') .toggle();
		}
	)
	
	
	
});
</script>
<style type="text/css">
	table.CityTable, table.StateTable{width:100%; border-color:#1C79C6; text-align:center;}
	table.StateTable{margin:0px; border:1px solid #fff;;text-align:center;}
	
	table td{padding:0px;}
	table.StateTable thead th{background: #369; padding: 0px; cursor:pointer; color:white;text-align:center;}
	table.CityTable thead th{padding: 0px; background: #C7DBF1;cursor:pointer; color:black;}
	
	table.StateTable td.nopad{padding:0;;text-align:center;}
	table.StateTable thead tr:hover td { background:#82B0C3;;text-align:center; }
	
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
		<br><br>
		<center>
		<%

Vector v_err = null;
v_err = (Vector) request.getAttribute("v_err");
request.removeAttribute("v_err");	
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
	<br>
<%	
}
		
if(v_list_std!=null && v_list_std.size()>0 && editor) {
%>
	<form action="upd.rencanaAmi" method="post">
	<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp%>"/>	
	<table class="table" style="width:90%">
		<thead>
			<tr>
				<th colspan="3" style="font-size:1.3em">
					RENCANA KEGIATAN AMI
				</th>
			</tr>
			<tr>
				<th colspan="1" style="width:10%;text-align:center">
					No.
				</th>
				<th colspan="2">
					Keterangan
				</th>
			</tr>
			<tr>
				<td colspan="1" style="text-align:center">
					A.
				</td>
				<td colspan="1" style="padding:0 0 0 10px;width:50%">
					Nama / Kode Kegiatan
				</td>
				<td colspan="1" style="text-align:center;width:40%">
	<%
	if(Checker.isStringNullOrEmpty("kode_activity")) {
	%>
					<input type="text" name="kode_activity" placeholder="isi nama/kode kegiatan" style="text-align:center;height:30px;width:100%;border:1px solid #369" required/>
	<%
	}
	else {
		%>
					<input type="text" name="kode_activity" placeholder="isi nama/kode kegiatan" value="<%=Checker.pnn_v1(kode_activity) %>" style="text-align:center;height:30px;width:100%;border:1px solid #369" required/>
<%
	}
	%>				
				</td>
			</tr>
			<tr>
				<td colspan="1" style="text-align:center">
					B.
				</td>
				<td colspan="1" style="padding:0 0 0 10px">
					Tanggal Rencana Kegiatan
				</td>
				<td colspan="1" style="text-align:center">
	<%
	if(Checker.isStringNullOrEmpty("tgl_plan")) {
	%>			
					<input type="text" name="tgl_plan" placeholder="tgl/bln/thn" style="text-align:center;height:30px;width:100%;border:1px solid #369" required/>
	<%
	}
	else {
		%>			
					<input type="text" name="tgl_plan" value="<%=Checker.pnn_v1(tgl_plan) %>" placeholder="tgl/bln/thn" style="text-align:center;height:30px;width:100%;border:1px solid #369" required/>
<%
	}
	%>
				</td>
			</tr>
			<tr>
				<td colspan="1" style="text-align:center">
					C.
				</td>
				<td colspan="1" style="padding:0 0 0 10px">
					Ketua Tim Auditor
				</td>
				<td colspan="1" style="text-align:center">
		<%
	if(Checker.isStringNullOrEmpty("ketua_tim")) {
	%>				
					<input type="text" name="ketua_tim" placeholder="isikan nama ketua tim" style="text-align:center;height:30px;width:100%;border:1px solid #369" required/>
	<%
	}
	else {
		%>				
					<input type="text" name="ketua_tim" value="<%=Checker.pnn_v1(ketua_tim) %>" placeholder="isikan nama ketua tim" style="text-align:center;height:30px;width:100%;border:1px solid #369" required/>
<%	
	}
	%>
				</td>
			</tr>
			<tr>
				<td colspan="1" style="text-align:center;vertical-align:top">
					D.
				</td>
				<td colspan="1" style="padding:0 0 0 10px;vertical-align:top">
					Anggota Tim Auditor
				</td>
				<td colspan="1" style="text-align:center">
	<%
	if(anggota_tim!=null && anggota_tim.length>0) {
		for(int i=0;i<anggota_tim.length;i++) {
			if(Checker.isStringNullOrEmpty(anggota_tim[i])) {
				if(i==0) {
	%>
					<input type="text" name="anggota_tim" placeholder="isikan nama anggota tim" style="text-align:center;height:30px;width:100%;border:1px solid #369" required/>
	<%				
				}
				else {
	%>
					<input type="text" name="anggota_tim" placeholder="isikan nama anggota tim" style="text-align:center;height:30px;width:100%;border:1px solid #369"/>
	<%			
				}
			}
			else {
				if(i==0) {
					%>
					<input type="text" name="anggota_tim" value="<%=anggota_tim[i] %>" placeholder="isikan nama anggota tim" style="text-align:center;height:30px;width:100%;border:1px solid #369" required/>
					<%				
				}
				else {
					%>
					<input type="text" name="anggota_tim" value="<%=anggota_tim[i] %>" placeholder="isikan nama anggota tim" style="text-align:center;height:30px;width:100%;border:1px solid #369"/>
					<%			
				}
			}
		}
	}
	else {
	%>					
					<input type="text" name="anggota_tim" placeholder="isikan nama anggota tim" style="text-align:center;height:30px;width:100%;border:1px solid #369" required/>
					<br>
					<input type="text" name="anggota_tim" placeholder="isikan nama anggota tim" style="text-align:center;height:30px;width:100%;border:1px solid #369"/>
					<br>
					<input type="text" name="anggota_tim" placeholder="isikan nama anggota tim" style="text-align:center;height:30px;width:100%;border:1px solid #369"/>
					<br>
					<input type="text" name="anggota_tim" placeholder="isikan nama anggota tim" style="text-align:center;height:30px;width:100%;border:1px solid #369"/>
					<br>
					<input type="text" name="anggota_tim" placeholder="isikan nama anggota tim" style="text-align:center;height:30px;width:100%;border:1px solid #369"/>
					<br>
				</td>
	<%
	}
	%>			
			</tr>
		</thead>	
	</table>
	<table class="table" style="width:90%">
		<thead>		
			<tr>
				<th colspan="3" style="font-size:1.3em">
					CAKUPAN STANDAR AMI
				</th>
			</tr>
			<tr>
				<th colspan="1" style="width:10%;text-align:center">
					No.
				</th>
				<th colspan="2">
					Nama Standar
				</th>
			</tr>
			
		
<%	
	ListIterator li = v_list_std.listIterator();
	int counter=0; 
	while(li.hasNext()) {
		
		String brs = (String)li.next();
		st = new StringTokenizer(brs,"`");
		String id_master_std = st.nextToken();
		String keter_std = st.nextToken();
		if(!keter_std.contains("BELUM")) {
%>
			<tr>
				<td colspan="1" style="text-align:center">
					<%=++counter %>.
				</td>
				<td colspan="1" style="padding:0 0 0 10px;width:80%;border-right: 1px solid <%=Constant.lightColorBlu()%>">
					<%=keter_std %>
				</td>
				<td colspan="1" style="text-align:center;width:10%">
<%
			if(cek!=null) {
				boolean match = false;
				for(int j=0;j<cek.length && !match;j++) {
					//System.out.println("cek["+j+"]="+cek[j]);
					if(cek[j]!=null && cek[j].equalsIgnoreCase(id_master_std)) {
						match=true;
					}
				}
				if(!match) {
%>				
					<input type="checkbox" name="cek" value="<%=id_master_std%>"/>
<%
				}
				else {
				%>				
					<input type="checkbox" name="cek" value="<%=id_master_std%>" checked="checked" />
<%				
				}
			}
			else {
%>
					<input type="checkbox" name="cek" value="<%=id_master_std%>"/>
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
				<td colspan="3" style="padding:5px 0">
					<section class="gradient">
     					<div style="text-align:center">
           					<button style="padding: 5px 50px;font-size: 20px;">Tambah Kegiatan AMI</button>
     					</div>
					</section>
				</td>
			</tr>
		</thead>
	</table>
	</form>
<%	
}		

%>
	</center>
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>
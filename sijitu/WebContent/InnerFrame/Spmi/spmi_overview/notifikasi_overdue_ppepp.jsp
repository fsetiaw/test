<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.notification.*" %>
<%@ page import="beans.dbase.chitchat.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.overview.*" %>
<%@ page import="beans.dbase.spmi.*" %>
<%
////System.out.println("yap1");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
//Converter.getDetailKdpst_v1(kdpst)
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);
Vector v_kegiatan_ppepp_yg_lewat=(Vector)request.getAttribute("v_kegiatan_ppepp_yg_lewat");
/*
MASUK KE pAGE INI VECOR > 0
*/

ListIterator li = v_kegiatan_ppepp_yg_lewat.listIterator();


//SearchStandarMutu stm = new SearchStandarMutu(validUsr.getNpm());
//Vector v_list_main_std = stm.getListInfoStandar();
%>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/css/metro.css" rel="stylesheet">
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>

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
.table:hover td { background:#82B0C3 }
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
	<script>	
	
	$(document).ready(function() {
		
		$(document).on("click", "#prepDashOverviewSpmi", function() {
        	$.ajax({
        		url: 'go.prepDashOverviewSpmi',
        		type: 'POST',
        		data: {
        			kdpst_nmpst_kmp: '<%=kdpst_nmpst_kmp%>' //ignore karena sebenarnya dari wondow.loca.href
        	    },
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	        //this is where we append a loading image
        	    },
        	    success:function(data){
        	        //successful request; do something with the data 
        	        <%
        	        kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
        	        %>

        	        window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Spmi/spmi_overview/dash_overview.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        //failed request; give feedback to user
        	    }
        	})
        	return false;
        });
		
	});	
    </script>
<script type="text/javascript">
$(document).ready(function(){
	
	$('table.StateTable tr.statetablerow th') .parents('table.StateTable') .children('tbody') .toggle();
	$('table.StateTable tr.statetablerow td') .parents('table.StateTable') .children('tbody') .toggle();

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
	table.StateTable tr.statetablerow { background:<%=Constant.darkColorBlu() %> }
	table.StateTable tr.statetablerow:hover { background:<%=Constant.lightColorBlu() %> }
	
</style>
</head>
<body>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai</p>
</div>
<div id="main">
	<div id="header">
	</div>
	<div class="colmask fullpage">
		<div class="col1">
		<br />
		<!-- Column 1 start -->
		<%@include file="menu_nav1.jsp" %>	
		<br>
			<div style="text-align:center;padding:0 0 0 5px">
				<span class="tile-group-title">
					<select style="width:98%;height:25px;border:none;text-align-last:center;border-radius: 10px;" disabled>
						<option value="null" selected="selected">TIPE KEGIATAN UNTUK SEMUA PRODI</option> 
					</select>
				</span>
			</div>
	<center>
	<br>
<%
	boolean first=true;
	while(li.hasNext()) {
		String brs = (String)li.next();
		st = new StringTokenizer(brs,"~");
		String tgl_plan = st.nextToken();
		String id_plan = st.nextToken();
		String nama_kegiatan = st.nextToken();
		String jenis_kegiatan = st.nextToken();
		String short_ket_kegiatan = st.nextToken();
		String isi_kegiatan = st.nextToken();
		String who_incharge = st.nextToken();
		String riwayat_tabel = st.nextToken();
		String keter = "";
		if(riwayat_tabel.equalsIgnoreCase("perencanaan")) {
			if(jenis_kegiatan.startsWith("rumus")) {
				if(jenis_kegiatan.endsWith("_std")) {
					keter = "[KEGIATAN PERUMUSAN STANDAR]";
				}
				else {
					keter = "[KEGIATAN PERUMUSAN MANUAL]";
				}
			}
			else if(jenis_kegiatan.startsWith("cek")) {
				if(jenis_kegiatan.endsWith("_std")) {
					keter = "[KEGIATAN PEMERIKSAAN STANDAR]";
				}
				else {
					keter = "[KEGIATAN PEMERIKSAAN MANUAL]";
				}
			}
			else if(jenis_kegiatan.startsWith("stuju")) {
				if(jenis_kegiatan.endsWith("_std")) {
					keter = "[KEGIATAN PERSETUJUAN STANDAR]";
				}
				else {
					keter = "[KEGIATAN PERSETUJUAN MANUAL]";
				}
			}
			else if(jenis_kegiatan.startsWith("tetap")) {
				if(jenis_kegiatan.endsWith("_std")) {
					keter = "[KEGIATAN PENETAPAN STANDAR]";
				}
				else {
					keter = "[KEGIATAN PENETAPAN MANUAL]";
				}
			}
			keter=keter+"<br>Tanggal Rencana Kegiatan: "+Converter.autoConvertDateFormat(tgl_plan, "/");
		}
		else if(riwayat_tabel.equalsIgnoreCase("pelaksanaan")) {
			keter="[KEGIATAN PELAKSANAAN STANDAR]<br>Tanggal Rencana Kegiatan: "+Converter.autoConvertDateFormat(tgl_plan, "/");
		}
		else if(riwayat_tabel.equalsIgnoreCase("evaluasi")) {
			keter="[KEGIATAN EVALUASI PELAKSANAAN STANDAR]<br>Tanggal Rencana Kegiatan: "+Converter.autoConvertDateFormat(tgl_plan, "/");
		}
		else if(riwayat_tabel.equalsIgnoreCase("pengendalian")) {
			keter="[KEGIATAN PENGENDALIAN PELAKSANAAN STANDAR]<br>Tanggal Rencana Kegiatan: "+Converter.autoConvertDateFormat(tgl_plan, "/");
		}
		else if(riwayat_tabel.equalsIgnoreCase("pelaksanaan")) {
			keter="[KEGIATAN PENINGKATAN STANDAR]<br>Tanggal Rencana Kegiatan: "+Converter.autoConvertDateFormat(tgl_plan, "/");
		}
		//keter = keter+"Nama Kegiatan: "+nama_kegiatan.toUpperCase();
%>
		<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="padding:50px 0 50px 0;border:1px solid white;width:70%;background:white;">	
			<thead>
				<tr class="statetablerow">
<%
		if(first) {
			first = false;
%>
					<td colspan="3" style="padding:25px 10px;font-size:1.5em;text-align:center;font-weight:bold;color:#fff;border:1px solid #369;">	
<%			
		}
		else {
%>
					<td colspan="3" style="padding:25px 10px;font-size:1.5em;text-align:center;font-weight:bold;color:#fff;border:1px solid #369;;border-top:1px solid #fff">
<%			
		}
%>						
				<%=keter %>
					</td>
				</tr>
				
			</thead>
			<tbody>
				<tr>
					<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:10%;color:#369;text-align:center;border:1px solid #369">
					a.
					</td>
					<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:30%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					Nama Kegiatan
					</td>
					<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:60%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					<%=nama_kegiatan.toUpperCase() %>
					</td>
				</tr>
				<tr>
					<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:10%;color:#369;text-align:center;border:1px solid #369">
					b.
					</td>
					<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:30%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					Penanggung Jawab
					</td>
					<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:60%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					<%=who_incharge.toUpperCase() %>
					</td>
				</tr>
				<tr>
					<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:10%;color:#369;text-align:center;border:1px solid #369">
					c.
					</td>
					<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:30%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					Keterangan Kegiatan
					</td>
					<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:60%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					<%=short_ket_kegiatan %>
					</td>
				</tr>
				<tr>
<%
		if(!li.hasNext()) {
%>
					<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:10%;color:#369;text-align:center;border:1px solid #369;">
					d.
					</td>
					<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:30%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369;">
					Rencana Isi Kegiatan
					</td>
					<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:60%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369;">
<%			
		}
		else {
%>
					<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:10%;color:#369;text-align:center;border:1px solid #369;border-bottom:1px solid #fff">
					d.
					</td>
					<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:30%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-bottom:1px solid #fff">
					Rencana Isi Kegiatan
					</td>
					<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:60%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-bottom:1px solid #fff">
<%			
		}
%>					<%=isi_kegiatan %>
					</td>
				</tr>
			</tbody>
		</table>					
<%
	}
%>
			</center>	
		</div>
		<!-- Column 1 end -->
		
	</div>
</div>
</div>
</body>
</html>
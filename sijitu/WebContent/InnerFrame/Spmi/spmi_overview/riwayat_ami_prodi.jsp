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
//System.out.println("yap1");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
//Converter.getDetailKdpst_v1(kdpst)
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);
Vector v_riwayat_ami_prodi=(Vector)session.getAttribute("v_riwayat_ami_prodi");
session.removeAttribute("v_riwayat_ami_prodi");
Boolean spmi_editor = (Boolean) session.getAttribute("spmi_editor");
/*
MASUK KE pAGE INI VECOR > 0
*/




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
.table tr:hover td { background:#82B0C3 }
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
font-weight:bold;
}
.table2 thead > tr > th, .table2 tbody > tr > t-->h, .table2 tfoot > tr > th, .table2 thead > tr > td, .table2 tbody > tr > td, 
.table2 tfoot > tr > td 
{ 
	border: 1px solid #2980B9;
	text-align:center;
	font-size:1.5em;
	font-weight:bold; 
	padding: 3px 3px;
	color: #369;
}

.table2-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table2-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table2-noborder thead > tr > th, .table2-noborder tbody > tr > th, .table2-noborder tfoot > tr > th, .table2-noborder thead > tr > td, .table2-noborder tbody > tr > td, .table2-noborder tfoot > tr > td { border: none;padding: 2px }
.table2 th:hover { background:#82B0C3 }
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
	table.StateTable thead th{background: <%=Constant.lightColorGrey() %>; padding: 0px; cursor:pointer; color:white;text-align:center;}
	table.CityTable thead th{padding: 0px; background: #C7DBF1;cursor:pointer; color:black;}
	
	table.StateTable td.nopad{padding:0;;text-align:center;}
	table.StateTable tr.statetablerow { background:<%=Constant.lightColorBlu() %> }
	table.StateTable tr.statetablerow:hover { background:<%=Constant.lightColorGrey() %> }
	table.StateTable tbody:hover td.nopad { background:<%=Constant.lightColorGrey() %> }
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
<%


Vector v_scope_kdpst_spmi = (Vector)session.getAttribute("v_scope_kdpst_spmi");
ListIterator lis = v_scope_kdpst_spmi.listIterator();

%>			
		
	<center>
	<br>
<%
if(v_riwayat_ami_prodi==null) {
%>
	<div style="text-align:center">
		<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/facepalm_itachi_small.png" alt="Gubraaak !@?!?">
		<h2>GuBRaAKk ...!!??@??!?<br>Maaf ada kesalahan yg memalukan, harap lapor ke admin<br>Sorry & Tengkiu ya... </h2>
	</div>
<%
}
else {
	ListIterator li = v_riwayat_ami_prodi.listIterator();
	//System.out.println("v_riwayat_ami_prodi="+v_riwayat_ami_prodi.size());
	Vector v_master = ToolSpmi.getIdDanNmMasterStandar();
	ListIterator lim = v_master.listIterator();
	if(lim.hasNext()) {
		double tot_master_std = v_master.size();
%>		
	
	<table class="table2" style="width:90%">
	<thead>
	<tr>
		<th colspan="<%=((int)tot_master_std)+2 %>" style="background:#369;font-size:2em;color:#fff;font-weight:bold;border-right:1px solid #369;height:40px">
				HASIL AMI
		</th>
	</tr>
	<tr>	
		<th style="background:#369;width:5%;color:#fff;font-weight:bold;border-right:1px solid #369;height:40px">
				No.
		</th>
		<th style="background:#369;width:20%;color:#fff;font-weight:bold;border-left:1px solid #369;border-right:1px solid #369">
				PROGRAM STUDI
		</th>
		
	
<%		
		do {
			String brs = (String)lim.next();
			
			st = new StringTokenizer(brs,"~");
			String id_master = st.nextToken();
			String nm_master = st.nextToken();
%>
		<th onclick="(function(){
					var x = document.getElementById('wait');
					var y = document.getElementById('main');
					x.style.display = 'block';
					y.style.display = 'none';
					location.href='get.histAmiProdiMasterStd?id_master_std=<%=id_master %>&fwdto=hist_ami_prodi&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>'})()"
          style="width:<%=(75/tot_master_std)%>%;color:#fff;font-weight:bold;border-right:1px solid #369">
				<%
				String tmp = nm_master.replace("STANDAR", "STD<br>");
				tmp = tmp.replace("NASIONAL", "");
				out.print(tmp);
				%>
		</th> 
<%			
		}
		while(lim.hasNext());
%>
	</tr>
	</thead>
<%
		int norut=1;
		li = v_riwayat_ami_prodi.listIterator();
		
		if(li.hasNext()) {
%>
	<tbody>
		<tr>
<%			
			String brs = (String)li.next();
//System.out.println("baris="+brs);
			st = new StringTokenizer(brs,"~");
			//kdpst+"~"+
			String prev_kdpst_ami = st.nextToken();
			//id_ami+"~"+
			String prev_id_ami = st.nextToken();
			//tgl_done+"~"+
			String prev_tgl_done = st.nextToken();
			//id_master+"~"+
			String prev_id_master_ami = st.nextToken();
			//nm_master+"~"+
			String prev_nm_master_ami = st.nextToken();
			//tot_qa+"~"+
			String prev_tot_qa = st.nextToken();
			//tot_nilai+"~"+
			String prev_tot_nilai = st.nextToken();
			//max_nilai
			String prev_max_nilai = st.nextToken();
			//nmpst
			String prev_nmpst_hist = st.nextToken();
			//kdjen
			String prev_kdjen = st.nextToken();
%>
			<td>	
			<%=norut++ %>
			</td>
			<td style="text-align:left;padding:0 0 0 5px">
			<%=prev_nmpst_hist %>
			</td>
<%			
			
			lim = v_master.listIterator();
			boolean match = false;
			while(lim.hasNext() && !match) {
				brs = (String)lim.next();
				st = new StringTokenizer(brs,"~");
				String id_master = st.nextToken();
				String nm_master = st.nextToken();	
				if(Integer.parseInt(prev_id_master_ami)==Integer.parseInt(id_master)) {
					match = true;
%>
			<td>
			<%=""+Converter.convertToPercentage(Double.parseDouble(prev_tot_nilai),Double.parseDouble(prev_max_nilai)) %> %
			
			</td>
<%					
				}
				else {
					%>
			<td>
			<%="0 %" %>
			</td>
			<%					
				}
			}
			
			while(li.hasNext()) {
				brs = (String)li.next();
				st = new StringTokenizer(brs,"~");
				//kdpst+"~"+
				String kdpst_ami = st.nextToken();
				//id_ami+"~"+
				String id_ami = st.nextToken();
				//tgl_done+"~"+
				String tgl_done = st.nextToken();
				//id_master+"~"+
				String id_master_ami = st.nextToken();
				//nm_master+"~"+
				String nm_master_ami = st.nextToken();
				//tot_qa+"~"+
				String tot_qa = st.nextToken();
				//tot_nilai+"~"+
				String tot_nilai = st.nextToken();
				//max_nilai
				String max_nilai = st.nextToken();
				//nmpst
				String nmpst_hist = st.nextToken();
				//kdjen
				String kdjen = st.nextToken();
				if(!kdpst_ami.equalsIgnoreCase(prev_kdpst_ami)) {
					//ganti prodi
					//a.cek apa hasil untuk setiap master stdnya sudah diisi
					while(lim.hasNext()) {
						//masih ada yg blum diisi
						lim.next();
						%>
			<td>
			<%="0 %" %>
			</td>
							<%					
					}
					//setelah pasti terisi ()
					
					//b. ganti prodi
					prev_kdpst_ami = kdpst_ami;
					//id_ami+"~"+
					prev_id_ami = id_ami;
					//tgl_done+"~"+
					prev_tgl_done = tgl_done;
					//id_master+"~"+
					prev_id_master_ami = id_master_ami;
					//nm_master+"~"+
					prev_nm_master_ami = nm_master_ami;
					//tot_qa+"~"+
					prev_tot_qa = tot_qa;
					//tot_nilai+"~"+
					prev_tot_nilai = tot_nilai;
					//max_nilai
					prev_max_nilai = max_nilai;
					//nmpst
					prev_nmpst_hist = nmpst_hist;
					//kdjen
					prev_kdjen = kdjen;
%>
		</tr>
		<tr>
			<td>	
					<%=norut++ %>
			</td>
			<td style="text-align:left;padding:0 0 0 5px">
					<%=prev_nmpst_hist %>
			</td>
<%
					//c. reset lim
					lim = v_master.listIterator();
					match = false;
					while(lim.hasNext() && !match) {
						brs = (String)lim.next();
						st = new StringTokenizer(brs,"~");
						String id_master = st.nextToken();
						String nm_master = st.nextToken();	
						if(Integer.parseInt(prev_id_master_ami)==Integer.parseInt(id_master)) {
							match = true;
%>
			<td>
						<%=""+Converter.convertToPercentage(Double.parseDouble(prev_tot_nilai),Double.parseDouble(prev_max_nilai)) %> %
						<br>
						
			</td>
<%					
						}
						else {
			%>
			<td>
				<%="0 %" %>
			</td>
<%					
						}
					}
				}
				else {
					//masih prodi yg sama
					//cek matching id_master 
					match=false;
					while(lim.hasNext() && !match) {
						brs = (String)lim.next();
						st = new StringTokenizer(brs,"~");
						String id_master = st.nextToken();
						String nm_master = st.nextToken();	
						if(Integer.parseInt(id_master_ami)==Integer.parseInt(id_master)) {
							match = true;
		%>
					<td>
					<%=""+Converter.convertToPercentage(Double.parseDouble(tot_nilai),Double.parseDouble(max_nilai)) %> %
					</td>
		<%					
						}
						else {
							%>
					<td>
					<%="0 %" %>
					</td>
					<%					
						}
					}
					prev_kdpst_ami = kdpst_ami;
					//id_ami+"~"+
					prev_id_ami = id_ami;
					//tgl_done+"~"+
					prev_tgl_done = tgl_done;
					//id_master+"~"+
					prev_id_master_ami = id_master_ami;
					//nm_master+"~"+
					prev_nm_master_ami = nm_master_ami;
					//tot_qa+"~"+
					prev_tot_qa = tot_qa;
					//tot_nilai+"~"+
					prev_tot_nilai = tot_nilai;
					//max_nilai
					prev_max_nilai = max_nilai;
					//nmpst
					prev_nmpst_hist = nmpst_hist;
					//kdjen
					prev_kdjen = kdjen;
				}
			}
			while(lim.hasNext()) {
				lim.next();
%>
				<td>
				<%="0 %" %>
			</td>
<%				
			}
		}
%>	
		</tr>
	</tbody>	
	</table>
<%
	}	
	
	
	
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
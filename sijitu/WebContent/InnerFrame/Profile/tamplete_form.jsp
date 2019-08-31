<!DOCTYPE html>
<html>
<head>
  <title>Bootstrap-select test page</title>
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.StringTokenizer" %>
	<%@ page import="java.util.ListIterator" %>


	<title>UNIVERSITAS SATYAGAMA</title>
	<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
	<meta name="description" content="" />
	<meta name="keywords" content="" />

  <meta charset="utf-8">

  <link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-select-1.12.2/dist/css/bootstrap-select.css">
	<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
  <script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
  <script src="<%=Constants.getRootWeb() %>/bootstrap-select-1.12.2/dist/js/bootstrap-select.js"></script>
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
 
<%
String info = (String)request.getAttribute("info");
String id_obj = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String obj_lvl = request.getParameter("obj_lvl");
String kdpst = request.getParameter("kdpst");
String nmmhs=null;
String tplhr=null;
String tglhr=null;
String kdjek=null;
String nisn=null;
String warganegara=null;
String niktp=null;
String nosim=null;
String paspo=null;
String angel=null;
String sttus=null;
String email=null;
String nohpe=null;
String almrm=null;
String no_rt=null;
String no_rw=null;
String prorm=null;
String proid=null;
String kotrm=null;
String kotid=null;
String kecrm=null;
String kecid=null;
String kelrm=null;
String dusun=null;
String posrm=null;
String telrm=null;
String nglhr=null;
String agama=null;

if(!Checker.isStringNullOrEmpty(info)) {
	StringTokenizer st = new StringTokenizer(info,"`");
	nmmhs=st.nextToken();
	tplhr=st.nextToken();
	tglhr=st.nextToken();
	kdjek=st.nextToken();
	nisn=st.nextToken();
	warganegara=st.nextToken();
	niktp=st.nextToken();
	nosim=st.nextToken();
	paspo=st.nextToken();
	angel=st.nextToken();
	sttus=st.nextToken();
	email=st.nextToken();
	nohpe=st.nextToken();
	almrm=st.nextToken();
	no_rt=st.nextToken();
	no_rw=st.nextToken();
	prorm=st.nextToken();
	proid=st.nextToken();
	kotrm=st.nextToken();
	kotid=st.nextToken();
	kecrm=st.nextToken();
	kecid=st.nextToken();
	kelrm=st.nextToken();
	dusun=st.nextToken();
	posrm=st.nextToken();
	telrm=st.nextToken();
	nglhr=st.nextToken();
	agama=st.nextToken();
}
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
</head>
<body>





        

		<table class="table" width="90%">
			<thead>
				<tr>
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">DATA PRIBADI</th>
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
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NAMA</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn(nmmhs) %></td>
					<td colspan="2" rowspan="4" align="center" style="vertical-align: middle; padding: 0px 5px">
						<img src="show.passPhoto?picfile=1.jpg&npmhs=1" class="img-thumbnail" alt="Cinque Terre" width="204" height="136">
					</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >GENDER</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" >1</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >STATUS</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" >1</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >AGAMA</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" >1</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >KOTA KELAHIRAN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" >1</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TANGGAL LAHIR</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" >1</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NEGARA KELAHIRAN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" >1</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >KEWARGANEGARAAN</td>
					<td align="center" style="vertical-align: middle; padding: 0px 5px" >
						<select width="99%" class="selectpicker show-tick form-control" data-live-search="true">
          					<option>cow</option>
							<option>ASD</option>
            				<option selected>Bla</option>
            				<option>Ble</option>
          					</optgroup>
        				</select>
					</td>
				</tr>
				<thead>
				<tr>
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">KETERANGAN DOMISILI</th>
				</tr>
				</thead>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >ALAMAT RUMAH</td>
					<td colspan="3" align="center" style="vertical-align: middle; padding: 0px 5px" >1</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >RT</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px" >1</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >RW</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px" >1</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >KELURAHAN</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px" >1</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >KECAMATAN</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px" >1</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >DESA</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px" ><%=Checker.pnn("") %></td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >DUSUN</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px" >1</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >KOTA</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px" >1</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PROVINSI</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px" >1</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >KODE POS</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px" >1</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >TELP</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px" >1</td>
				</tr>
				<thead>
				<tr>
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">INFO KONTAK & IDENTIFIKASI</th>
				</tr>
				</thead>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NIK</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px" >1</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NISN</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px" >1</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >SIM</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px" >1</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >PASPOR</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px" >1</td>
				</tr>
				<tr>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >NO HP</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px" >1</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;background: <%=Constant.mildColorBlu()%>;color:white;font-weight:bold" >EMAIL</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px" >1</td>
				</tr>
				<thead>
				<tr>
  					<th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em;height:55px">
  					<center>
					<section class="gradient">
						<div style="text-align:right;padding:5px 0">						
						<button formnovalidate class="button1" type="submit" style="padding: 5px 50px;font-size: 20px;">UPDATE DATA</button>
						</div>
					</section>
					</center>
					</th>
				</tr>
				</thead>
  			</tbody>
		</table>



</body>
</html>

<!DOCTYPE html>
<head>

<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="beans.dbase.wilayah.*" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
  <link rel="stylesheet" href="<%=Constants.getRootWeb() %>/css/jquery-ui.css">
  <script src="<%=Constants.getRootWeb() %>/jquery/jquery.min.js"></script>
  <script src="<%=Constants.getRootWeb() %>/js/jquery-ui.js"></script>
<%
String nuform = ""+request.getParameter("nuform");

String valid_pt1 = request.getParameter("valid_pt1");
String valid_pt2 = request.getParameter("valid_pt2");
boolean passed1 = true;
boolean passed2 = true;
if(valid_pt1!=null && valid_pt1.equalsIgnoreCase("false")) {
	passed1 = false;
}
if(valid_pt2!=null && valid_pt2.equalsIgnoreCase("false")) {
	passed2 = false;
}
//System.out.println("pased1="+passed1+" - "+valid_pt1);
//System.out.println("pased2="+passed2+" - "+valid_pt2);
String err_msg = request.getParameter("err_msg");
String id_wil_indo = "000000";
SearchDbWilayah sdw = new SearchDbWilayah();
Vector v = sdw.getListNegara();
ListIterator li = null;
Vector v1 = sdw.getListWilayah(1,id_wil_indo);
String propinsi = request.getParameter("propinsi");
//System.out.println("prop="+propinsi);
String id_wil_prop = "null";
String nm_wil_prop = "null";
//String id_wil_prop = null;
Vector v2 = null;

if(propinsi!=null && !Checker.isStringNullOrEmpty(propinsi) && passed1) {
	StringTokenizer st = new StringTokenizer(propinsi,"`");
	nm_wil_prop = st.nextToken();
	id_wil_prop = st.nextToken().trim();
	v2 = sdw.getListWilayah(2,id_wil_prop);
}
String id_wil_kot = "null";
String nm_wil_kot = "null";
String kota = request.getParameter("kota");
Vector v3 = null;
if(kota!=null && !Checker.isStringNullOrEmpty(kota) && passed1) {
	StringTokenizer st = new StringTokenizer(kota,"`");
	nm_wil_kot = st.nextToken();
	id_wil_kot = st.nextToken();
	v3 = sdw.getListWilayah(3,id_wil_kot);
}
String id_wil_kec = "null";
String nm_wil_kec = "null";
String kecamatan = request.getParameter("kecamatan");
if(kecamatan!=null && !Checker.isStringNullOrEmpty(kecamatan) && passed1) {
	kecamatan = kecamatan.replace("[", "`");
	kecamatan = kecamatan.replace("]", "");
	StringTokenizer st = new StringTokenizer(kecamatan,"`");
	
	nm_wil_kec = st.nextToken();
	id_wil_kec = st.nextToken();
}
%>  
  <script>
  $( function() {
	  
  
    var listKecamatan = [
<%


if(v3!=null && v3.size()>0) {
	li = v3.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
%>
		"<%=brs%>"
<%
		if(li.hasNext()) {
			%>
			,
	<%			
		}
	}
}
%>
    ];
    $( "#kecamatan" ).autocomplete({
      source: listKecamatan
    });
    
    
    var listNegara = [
<%


if(v!=null && v.size()>0) {
	li = v.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
%>
		"<%=brs.trim()%>"
<%
		if(li.hasNext()) {
			%>
			,
	<%			
		}
	}
}
%>
    ];
    $( "#negara" ).autocomplete({
      source: listNegara
    });
    $( "#nglhr" ).autocomplete({
        source: listNegara
      });
    
  });
  </script>

<%
	//System.out.println("pmb1");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
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
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9; }

.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px }
/*
tr:hover td { background:#82B0C3 }
*/
  
</style>
<script>
function resetForm($form) {
	$('#email').val('');
	$('#hape').val('');
    $form.find('input:text, input:password, input:file, select, textarea').val('');
    $form.find('input:radio, input:checkbox')
         .removeAttr('checked').removeAttr('selected');
}
</script>
</head>
<%
if(nuform!=null && nuform.equalsIgnoreCase("true")) {
%>
<body onload="resetForm($('#myform'))">
<%	
}
else {
%>
<body>
<%
}
%>
<div id="header">
<jsp:include page="InnerMenu0_pageVersion.jsp" flush="true" />
</div>
<div class="colmask fullpage">

		<br />
		<section align="center" style="font-size:.8em;color:red;line-height:12px">
			<center>
			<br>
			<br>
			<table>
				<tr>
					<td style="text-align:left"><%=Checker.pnn(err_msg) %></td>
				</tr>
			</table>
			</center>
		</section>
		<br/>
	<center>
	<form action="ins.bukuTamu?atMenu=insBt" method="post" id="myform" name="myform">
		
		<table class="table" width="90%">
			<thead>
				<tr>
  					<th colspan="2" style="text-align: center; padding: 0px 10px;font-size:1.5em">BUKU TAMU</th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						NAMA LENGKAP
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String nama = request.getParameter("nama");
					if(nama!=null && !Checker.isStringNullOrEmpty(nama)) {
					%>	
						<input type="text" style="width:99%;height:99%" name="nama" id="nama" value="<%=nama %>" placeholder="*wajib diisi"/>
					<%	
					}
					else {
					%>	
						<input type="text" style="width:99%;height:99%" name="nama" id="nama" placeholder="*wajib diisi"/>
					<%
					}
					%>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						EMAIL
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String email = request.getParameter("email");
					if(email!=null && !Checker.isStringNullOrEmpty(email)) {
					%>	
						<input type="email" style="width:45%;height:99%" name="email" id="email" value="<%=email %>" placeholder="*wajib diisi"/>
					<%	
					}
					else {
					%>	
						<input type="email" style="width:45%;height:99%" name="email" id="email" placeholder="*wajib diisi"/>
					<%
					}
					%>
					&nbsp&nbsp&nbsp
					NO HP :
					<%
					String hape = request.getParameter("hape");
					if(hape!=null && !Checker.isStringNullOrEmpty(hape)) {
					%>	
						<input type="number" style="width:25%;height:99%" name="hape" id="hape" value="<%=hape %>" placeholder="*wajib diisi"/>
					<%	
					}
					else {
					%>	
						<input type="number" style="width:25%;height:99%" name="hape" id="hape" placeholder="*wajib diisi"/>
					<%
					}
					%>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						GENDER
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String gender = request.getParameter("gender");
					%>
						<select name="gender" style="width:99%;height:99%;text-align-last:center;">
					<%
					String[]kdjek = Constants.getOptionGender();
					for(int i=0;i<kdjek.length;i++) {
						String baris = kdjek[i];
						StringTokenizer st = new StringTokenizer(baris);
						String val = st.nextToken();
						String ket = st.nextToken();
						if(val.equalsIgnoreCase(gender)) {
							%>
							<option value="<%=val%>" selected="selected"><%=ket%></option>
							<%	
						}
						else {
							%>
							<option value="<%=val%>"><%=ket%></option>
								<%
						}	
					}
					%>	 
						</select>
					</td>
				</tr>	
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						NIK
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String nik = request.getParameter("nik");
					if(nik!=null && !Checker.isStringNullOrEmpty(nik)) {
					%>
						<input type="text" style="width:99%;height:99%" name="nik" value="<%=nik %>" id="nik" />
					<%	
					}
					else {
					%>
						<input type="text" style="width:99%;height:99%" name="nik" id="nik" />
					<%
					}
					%>	
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						NISN
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String nisn = request.getParameter("nisn");
					if(nisn!=null && !Checker.isStringNullOrEmpty(nisn)) {
					%>
						<input type="text" style="width:99%;height:99%" name="nisn" value="<%=nisn %>" id="nisn" />
					<%	
					}
					else {
					%>
						<input type="text" style="width:99%;height:99%" name="nisn" id="nisn" />
					<%
					}
					%>	
					</td>
				</tr>				
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						KEWARGANEGARAAN
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String negara = request.getParameter("negara");
					//System.out.println("negara jsp = "+negara);
					if(negara!=null && !Checker.isStringNullOrEmpty(negara)) {
					%>
						 <input name="negara" id="negara" style="width:99%;height:99%" type="text" value="<%=negara %>" placeholder="*wajib diisi">
					<%
					}
					else {
					%>
						 <input name="negara" id="negara" style="width:99%;height:99%" type="text" placeholder="*wajib diisi">
					<%
					}
					%>	 
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						AGAMA
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String agama = request.getParameter("agama");
					%>
						<select name="agama" style="width:99%;height:99%;text-align-last:center;">
					<%
					Vector v_agama = Getter.getListAgama();
					ListIterator lia = v_agama.listIterator();
					while(lia.hasNext()) {
						String brs = (String)lia.next();
						StringTokenizer st1 = new StringTokenizer(brs,"`");
						String id = st1.nextToken();
						String nm = st1.nextToken();
						if(nm.equalsIgnoreCase(agama)) {
							%>
							<option value="<%=nm%>" selected="selected"><%=nm%></option>
							<%	
						}
						else {
							%>
							<option value="<%=nm%>"><%=nm%></option>
								<%
						}	
					}
					%>	 
						</select>
					</td>
				</tr>	
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						NEGARA KELAHIRAN
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String nglhr = request.getParameter("nglhr");
					if(nglhr!=null && !Checker.isStringNullOrEmpty(nglhr)) {
					%>
						 <input name="nglhr" id="nglhr" style="width:99%;height:99%" type="text" value="<%=nglhr %>" placeholder="*harap diisi bila berbeda dengan kewarganegaraan">
					<%
					}
					else {
					%>
						 <input name="nglhr" id="nglhr" style="width:99%;height:99%" type="text" placeholder="*harap diisi bila berbeda dengan kewarganegaraan">
					<%
					}
					%>	 
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						TEMPAT/TGL LAHIR
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String tplhr = request.getParameter("tplhr");
					if(tplhr!=null && !Checker.isStringNullOrEmpty(tplhr)) {
					%>
						<input name="tplhr" id="tplhr" style="width:50%;height:99%" type="text" value="<%=tplhr %>" placeholder="*wajib diisi">
					<%	
					}
					else {
					%>
						<input name="tplhr" id="tplhr" style="width:50%;height:99%" type="text" placeholder="*wajib diisi">
					<%
					}
					%>	
						&nbsp&nbspTANGGAL :
					<%
					String tglhr = request.getParameter("tglhr");
					if(tglhr!=null && !Checker.isStringNullOrEmpty(tglhr)) {
					%>	
						<input name="tglhr" id="tglhr" style="width:30%;height:99%" type="text" value="<%=tglhr %>" placeholder="*dd-mm-yyyy">
					<%	
					}
					else {
					%>	
						<input name="tglhr" id="tglhr" style="width:30%;height:99%" type="text" placeholder="*dd-mm-yyyy">
					<%
					}
					%>			 
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center; background-color: #f2f2f2;vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						INFO ORANGA TUA / WALI
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						NAMA AYAH
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String ayah = request.getParameter("ayah");
					if(ayah!=null && !Checker.isStringNullOrEmpty(ayah)) {
					%>
						<input type="text" style="width:99%;height:99%" name="ayah" value="<%=ayah %>" id="ayah" />
					<%	
					}
					else {
					%>
						<input type="text" style="width:99%;height:99%" name="ayah" id="ayah" />
					<%
					}
					%>	
					</td>
				</tr>	
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						NAMA IBU
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String ibu = request.getParameter("ibu");
					if(ibu!=null && !Checker.isStringNullOrEmpty(ibu)) {
					%>
						<input type="text" style="width:99%;height:99%" name="ibu" value="<%=ibu %>" id="ibu" />
					<%	
					}
					else {
					%>
						<input type="text" style="width:99%;height:99%" name="ibu" id="ibu" />
					<%
					}
					%>	
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center; background-color: #f2f2f2;vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						INFO TEMPAT TINGGAL
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						PROVINSI
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
						<select onchange="this.form.submit()" name="propinsi" style="width:99%;height:99%;text-align-last:center;">
							<option value="null">Pilih Propinsi</option>
						<%
						li = v1.listIterator();
						while(li.hasNext()) {
							String brs=(String)li.next();
							StringTokenizer st = new StringTokenizer(brs,"`");
							String nm_wil = st.nextToken();
							String id_wil = st.nextToken();
							
							if(id_wil_prop.equalsIgnoreCase(id_wil)) {
								%>
							<option value="<%=brs %>" selected="selected"><%=nm_wil.replace("Prop.","")%></option>
							<%	
							}
							else {
						%>
							<option value="<%=brs %>"><%=nm_wil.replace("Prop.","")%></option>
						<%	
							}
						}
						%>
						</select>
					</td>
				</tr>
<%
if(propinsi!=null && !Checker.isStringNullOrEmpty(propinsi) && passed1) {
%>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						KOTA / KABUPATEN
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
						<select onchange="this.form.submit()" name="kota" style="width:99%;height:99%;text-align-last:center;">
							<option value="null">Pilih Kota / Kabupaten</option>
						<%
						li = v2.listIterator();
						while(li.hasNext()) {
							String brs=(String)li.next();
							StringTokenizer st = new StringTokenizer(brs,"`");
							String nm_wil = st.nextToken();
							String id_wil = st.nextToken();
							
							
							if(id_wil_kot.equalsIgnoreCase(id_wil)) {
								%>
							<option value="<%=brs %>" selected="selected"><%=nm_wil.replace("Kab.","").replace("Kab", "").replace("Kota", "").replace("Kota.", "") %></option>
							<%	
							}
							else {
						%>
							<option value="<%=brs %>"><%=nm_wil.replace("Kab.","").replace("Kab", "").replace("Kota", "").replace("Kota.", "")%></option>
						<%	
							}
						}	
						%>
						</select>
					</td>
				</tr>
<%	
}

if(kota!=null && !Checker.isStringNullOrEmpty(kota) && passed1) {
%>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						KECAMATAN
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					kecamatan = request.getParameter("kecamatan");
					if(kecamatan!=null && !Checker.isStringNullOrEmpty(kecamatan)) {
						%>
						<input name="kecamatan" id="kecamatan" value="<%=kecamatan %>" style="width:99%;height:99%" type="text" placeholder="*wajib diisi">
					<%	
					}
					else {
					%>
						<input name="kecamatan" id="kecamatan" style="width:99%;height:99%" type="text" placeholder="*wajib diisi">
					<%
					}
					%>	
					</td>
				</tr>
				<tr>
					<td rowspan="2" style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						ALAMAT
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String alamat = request.getParameter("alamat");
					if(alamat!=null && !Checker.isStringNullOrEmpty(alamat)) {
						%>
						 <textarea placeholder="*wajib diisi" name="alamat" rows="3"  style="width:99%;vertical-align: middle;"><%=alamat %></textarea>
					<%
					}
					else {
						%>
						 <textarea placeholder="*wajib diisi" name="alamat" rows="3" style="width:99%;vertical-align: middle;"></textarea>
					<%
					}
					%>	 
					</td>
				</tr>
				<tr>
					<td colspan="2" style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						RT/RW :
					<%
					String rt = request.getParameter("rt");
					if(rt!=null && !Checker.isStringNullOrEmpty(rt)) {
						%> 
						<input type="text" value="<%=rt %>" style="width:10%;height:99%" name="rt" id="rt" />
					<%	
					}
					else {
						%> 
						<input type="text" style="width:10%;height:99%" name="rt" id="rt" /> /
					<%
					}
					
					String rw = request.getParameter("rw");
					if(rw!=null && !Checker.isStringNullOrEmpty(rw)) {
						%> 	 
						<input type="text" value="<%=rw %>" style="width:10%;height:99%" name="rw" id="rw" />
					<%
					}
					else {
						%> 	 
						<input type="text" style="width:10%;height:99%" name="rw" id="rw" />
					<%
					}
					String pos = request.getParameter("pos");
					if(pos!=null && !Checker.isStringNullOrEmpty(pos)) {
						%> 	 
					&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp  KODE POS : <input type="text" value="<%=pos %>" style="width:20%;height:99%" name="pos" id="pos" />
						<%	
					}
					else {
						%> 	 
					&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp  KODE POS : <input type="text" style="width:20%;height:99%" name="pos" id="pos" />
					<%
					}
					%>
					</td>
					
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						KELURAHAN
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String kelurahan = request.getParameter("kelurahan");
					if(kelurahan!=null && !Checker.isStringNullOrEmpty(kelurahan)) {
						%>
						<input name="kelurahan" value="<%=kelurahan %>" id="kelurahan" style="width:99%;height:99%" type="text" placeholder="*wajib diisi">
					<%	
					}
					else {
					%>
						<input name="kelurahan" id="kelurahan" style="width:99%;height:99%" type="text" placeholder="*wajib diisi">
					<%
					}
					%>	
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; padding: 5px 10px;width:30%; font-size:1.1em">
						DUSUN
					</td>
					<td align="left" style="vertical-align: middle; padding: 0px 5px;width:70%">
					<%
					String dusun = request.getParameter("dusun");
					if(dusun!=null && !Checker.isStringNullOrEmpty(dusun)) {
						%>
						<input name="dusun" id="dusun" value="<%=dusun %>" style="width:99%;height:99%" type="text">
					<%	
					}
					else {
						%>
						<input name="dusun" id="dusun" style="width:99%;height:99%" type="text">
					<%
					}
					%>	
					</td>
				</tr>
				<tr>
					<td colspan="2">
					<section class="gradient">
						<div style="text-align:right; padding: 5px 5px">
						<button formnovalidate type="submit" style="padding: 5px 50px;font-size: 20px;">NEXT</button>
						</div>
					</section>	
					</td>				
				</tr>
<%	
}

%>				
				
  			</tbody>
		</table>
	</form>
	<div class="col1">
	</center>
	</br/>
	</div>
</div>	
</body>
</html>
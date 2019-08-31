<!doctype html>
<html lang="en">
<head>
<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>
	<%@ page import="beans.dbase.wilayah.*" %>
	<%@ page import="org.codehaus.jettison.json.JSONArray" %>
	<%@ page import="org.codehaus.jettison.json.JSONObject" %>
	<%@ page import="org.codehaus.jettison.json.JSONException" %>
	<%@ page import="org.owasp.esapi.ESAPI" %>
  <meta charset="utf-8">
  <%
  beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
  SearchDbWilayah sdw = new SearchDbWilayah();
  Vector v = sdw.getListNegara();
  %>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>jQuery UI Autocomplete - Default functionality</title>
  <link rel="stylesheet" href="<%=Constants.getRootWeb() %>/css/jquery-ui.css">
  <script src="<%=Constants.getRootWeb() %>/jquery/jquery.min.js"></script>
  <script src="<%=Constants.getRootWeb() %>/js/jquery-ui.js"></script>
  <script>
  $( function() {
    var availableTags = [
"20201`ELEKTRO","22201`SIPIL","23201`ARSITEK","26201`INDUSTRI","54201`AGRIBIS","54211`AGROTEK","55201`INFORMATIKA","61101`MM","61201`MANAGEMEN","62201`AKUNTANSI","64201`HI","65001`DIMP","65101`MIP","65201`IP","74201`HUKUM","88888`agama","93402`HOTEL"
    ];
    $( "#tags" ).autocomplete({
      source: availableTags
    });
    
    $( "#tags2" ).autocomplete({
        source: availableTags
     });
    $( "#tags3" ).autocomplete({
        source: availableTags
     });
    $( "#tags4" ).autocomplete({
        source: availableTags
     });
    $( "#tags5" ).autocomplete({
        source: availableTags
     });
    $( "#tags6" ).autocomplete({
        source: availableTags
     });
    $( "#tags7" ).autocomplete({
        source: availableTags
     });
    $( "#tags8" ).autocomplete({
        source: availableTags
     });
    $( "#tags9" ).autocomplete({
        source: availableTags
     });
  } );
  </script>
</head>
<body>
	<br>
	<%
	Vector v_err = (Vector)request.getAttribute("v_err");
	if(v_err!=null && v_err.size()>0) {
	%>
	<p align="center"><h3>
	<%	
		ListIterator lie = v_err.listIterator();
		while(lie.hasNext()) {
			out.print((String)lie.next()+"<br>");
		}
	}
	%>
	</h3></p>
	<%
	if(Checker.isThisMyRole(session, "ADMIN")) {
	//$("#country").autocomplete("getdata.jsp");
	String target_thsms = "20152";
	%>
	<a href="go.syncWilayah">UPDATE TABEL WILAYAH</a><br/><br/>
	<a href="go.cleansDbImporter?thsms=<%=target_thsms%>">FILTER DB</a><br/><br/>
	<a href="go.importBobotNilai?thsms=<%=target_thsms%>">IMPORT DATA TBBNL</a><br/><br/>
	<a href="go.importKapasitas?thsms=<%=target_thsms%>">IMPORT DATA KAPASITAS</a><br/><br/>
	-------------------------------------------------------------------------------<br>
	export ke importer, krn dibutuhkan pada saat sync dgn feeder<br><br>
	<a href="go.importMakul?thsms=<%=target_thsms%>">IMPORT DATA MAKUL</a><br/><br/>
	<a href="go.importKrklm?thsms=<%=target_thsms%>">IMPORT KURIKULUM</a><br/><br/>
	<a href="go.importMakulKrklm?thsms=<%=target_thsms%>">IMPORT MATAKULIAH-KURIKULUM</a><br/><br/>
	-------------------------------------------------------------------------------<br>
	<a href="go.importKelasKuliah?thsms=<%=target_thsms%>">IMPORT KELAS KULIAH</a><br/><br/>
	<a href="go.importDosenPt?thsms=<%=target_thsms%>">IMPORT DOSEN_PT (BELUM DIBUAT - utk input surat tugas)</a><br/><br/>
	<a href="go.importAjarDosen?thsms=<%=target_thsms%>">IMPORT AJAR DOSEN</a><br/><br/>
	<a href="go.importNilaiMhs?thsms=<%=target_thsms%>">IMPORT NILAI MHS</a><br/><br/>
	<br>
	<br>
	
	<br>
	<a href="update_trnlp.jsp?thsms=<%=target_thsms%>">UPDATE TRNLP <%=target_thsms%> DARI EPSBED (TXT FILE)</a><br/>
	<br> 
	<br>
	<br>
0. ALLOW KRS EDIT
<form action="allow_edit_krs.jsp" method="post">
  <input type="text" name="thsms" value="20152"/>
  <br>
  <input type="submit" value="submit" />
</form>
<br><br>	
1. SET KURIKULUM
<form action="info_prodi.jsp" method="post">
  <input id="tags" name="tags">
  <br>
  <input type="text" name="thsms" value="20152"/>
  <br>
  <input type="submit" value="submit" />
</form>
<br><br>
2. SET SHIFT
<form action="info_prodi_shift.jsp" method="post">
  <input id="tags2" name="tags2">
  <br>
  <input type="text" name="thsms" value="20152"/>
  <br>
  <input type="submit" value="submit" />
</form> 
<br><br>
3. INSERT KRS MALAIKAT DARI SMAWL SAMPE SEKARANG
<form action="info_prodi_krs.jsp" method="post">
  <input id="tags3" name="tags3">
  <br>
  <input type="text" name="thsms" value="20152"/>
  <br>
  <input type="submit" value="submit" />
</form> 
<br><br>

<%
}
%>
4. LIST MHS PINDAHAN
<form action="info_prodi_pindahan.jsp" method="post">
  <input id="tags4" name="tags4">
  <br>
  <input type="text" name="thsms" value="20152"/>
  <br>
  <input type="submit" value="submit" />
</form> 
<br><br>
5. LIST MHS LULUSAN
<form action="info_prodi_lulusan.jsp" method="post">
  <input id="tags5" name="tags5">
  <br>
  <input type="text" name="thsms" value="20152"/>
  <br>
  <input type="submit" value="submit" />
</form> 
 <br><br>
 6. SETING DOSEN PENGAJAR
 <form action="setting_dosen_v1.jsp" method="post">
  <input type="text" name="thsms" value="20152"/>
  <br>
  <input type="submit" value="submit" />
</form> 
 <br><br>
<%
if(Checker.isThisMyRole(session, "ADMIN")) {
%> 
 7a. KASIH NILAI MALAIKAT
 <form action="nilai_malaikat.jsp" method="post">
  <input type="text" name="thsms" value="20152"/>
  <br>
  <input type="submit" value="submit" />
</form> 
 <br><br>
 8a. INPUT LULUSAN BERDASARKAN EXCEL
 <form action="insert_lulusan.jsp" method="post">
  <input type="text" name="thsms" value="20152"/>
  <br>
  <input type="submit" value="submit" />
</form> 
 <br><br>
<%
}
%> 
 6. LIST MALAIKAT BARU
 <form action="list_mhs_baru_malaikat.jsp" method="post">
 <input id="tags6" name="tags6">
  <br>
  <input type="text" name="thsms" value="20152"/>
  <br>
  <input type="submit" value="submit" />
</form> 
 <br><br>
 7. NPM YG BELUM ADA KRS
 <form action="cek_krs_20152_20161.jsp" method="post">
 
 
  <input type="text" name="thsms" value="20152"/>
  <br>
  <input type="submit" value="submit" />
</form> 
 <br><br>
 8. INSERT KRS MHS RIL DARI SMAWL SAMPE SEKARANG MASIH NULL
<form action="info_krs_rill_yg_null.jsp" method="post">
  <input id="tags8" name="tags8">
  <br>
  <input type="text" name="thsms" value="20152"/>
  <br>
  <input type="submit" value="submit" />
</form> 
<br><br>
 8. INSERT KRS MHS PINDAHAN (BATAL)
<form action="ins_krs_pindahan.jsp" method="post">
  <input id="tags8" name="tags8">
  <br>
  <input type="text" name="thsms" value="20152"/>
  <br>
  <input type="submit" value="submit" />
</form> 
<br><br>
 9. update tbbnl
<form action="update_tbbnl.jsp" method="post">
  <input type="text" name="thsms" value="20152"/>
  <br>
  <input type="submit" value="submit" />
</form> 
<br><br>
 10. KASIH NLAKH KRS NILAI TUNDA
<form action="kasih_nilai_krs.jsp" method="post">
  <input id="tags7" name="tags7">
  <br>
  <input type="text" name="thsms" value="20152"/>
  <br>
  <input type="submit" value="submit" />
</form> 
<br><br>
 11. HAPUS KRS SETELAH MHS KELUAR
<form action="hapus_krs_yg_tdk_ada_di_list_epsbed.jsp" method="post">
  <input id="tags9" name="tags9">
  <br>
  <input type="text" name="thsms" value="20152"/>
  <br>
  <input type="submit" value="submit" />
</form> 
<br><br>
</body>
</html>
<!doctype html>
<html lang="en">
<head>
<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>
	<%@ page import="java.util.*" %>
	<%@ page import="beans.dbase.wilayah.*" %>
	<%@ page import="beans.dbase.feeder.*" %>
	<%@ page import="org.codehaus.jettison.json.JSONArray" %>
	<%@ page import="org.codehaus.jettison.json.JSONObject" %>
	<%@ page import="org.codehaus.jettison.json.JSONException" %>
	<%@ page import="org.owasp.esapi.ESAPI" %>
  <meta charset="utf-8">
 
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
  } );
  </script>
  <%
  SearchDbFeeder sdf = new SearchDbFeeder();

  
  
  
  %>
</head>
<body>
<%
String thsms = request.getParameter("thsms"); 
String tkn_prodi = "20201`ELEKTRO`22201`SIPIL`23201`ARSITEK`26201`INDUSTRI`54201`AGRIBIS`54211`AGROTEK`55201`INFORMATIKA`61101`MM`61201`MANAGEMEN`62201`AKUNTANSI`64201`HI`65001`DIMP`65101`MIP`65201`IP`74201`HUKUM`88888`agama`93402`HOTEL";
StringTokenizer st = new StringTokenizer(tkn_prodi,"`");
while(st.hasMoreTokens()) {
	String kdpst = st.nextToken();
	String alm_file = "";
	if(kdpst.equalsIgnoreCase("88888")) {
	  alm_file = "/home/usg/USG/EPSBED/"+thsms+"/txt_files/mhs_aktif_86208_20152.txt";
	}
	else {
	  alm_file =  "/home/usg/USG/EPSBED/"+thsms+"/txt_files/mhs_aktif_"+kdpst+"_20152.txt";  
	}  
	String nmpst = st.nextToken();  
	out.print(nmpst+"<br>");
	Vector v=Tool.bacaFileTxt(alm_file, thsms);
	out.print("set 1. get no npm<br>");
	v = sdf.addInfoNpm(v);
	out.print("set 2. cek apa ada krs "+thsms+" dan tampilkan npm yg tidak ada<br>");
	v = sdf.cekApaAdaKrsDanCekTrlsm(v,thsms);
	ListIterator li = v.listIterator();
	while(li.hasNext()) {
	  String brs = (String)li.next();
	  if(brs.contains("non`non")||brs.contains("non`L")) {
		  out.print(brs+"<br>");  
	  }
	  
	}
	  
  }
%>
</body>
</html>
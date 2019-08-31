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
	<%@ page import="beans.dbase.trnlm.*" %>
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
 String prodi = request.getParameter("tags7");
 String thsms = request.getParameter("thsms");
  StringTokenizer st = new StringTokenizer(prodi,"`");
  String kdpst = st.nextToken();
  String kdjen = Checker.getKdjen(kdpst);
  String nmpst = st.nextToken();
  String alm_file = "";
  SearchDbFeeder sdf = new SearchDbFeeder();
  Vector v = null;
if(kdpst.equalsIgnoreCase("88888")) {
	alm_file = "/home/usg/USG/EPSBED/"+thsms+"/txt_files/mhs_aktif_86208_20152.txt";
}
else {
  	alm_file =  "/home/usg/USG/EPSBED/"+thsms+"/txt_files/mhs_aktif_"+kdpst+"_20152.txt";  
}  
%>
</head>
<body>
	<%=prodi %><br>
	<%=kdjen %><br>
	<%="<br>"+thsms %>
<%
out.print(nmpst+"<br>");
v=Tool.bacaFileTxt(alm_file, thsms);
v = sdf.addInfoNpm(v);
////System.out.println("1");
v = sdf.getKrsMhsDgnNilaiTunda(v, thsms);
////System.out.println("2");
////System.out.println("vsize="+v.size());
//kasih nilai
Vector vf = new Vector();
ListIterator lif = vf.listIterator();
UpdateDbTrnlm udt = new UpdateDbTrnlm();
if (v!=null) {
	ListIterator li = v.listIterator();
	while(li.hasNext()) {
		String brs1 = (String)li.next();
		////System.out.println("brs1="+brs1);
		st = new StringTokenizer(brs1,"`");
		String npmhs = st.nextToken();
		////System.out.println(brs1+"<br>");
		String brs2 = (String)li.next();
		////System.out.println("brs2="+brs2);
		boolean lulus = false;
		if(brs2.equalsIgnoreCase("L")) {
			//////System.out.println("Lulus<br>");
			lulus = true;
		}
		else {
			//////System.out.println(brs2+"<br>");
		}
		Vector v_trnlm = (Vector) li.next();
		ListIterator li1 = v_trnlm.listIterator();
		while(li1.hasNext()) {
			String brs3 = (String) li1.next();
			//////System.out.println("brs1="+brs1);
			////System.out.println(brs3);
			if(brs3.contains("ERROR")) {
				
			}
			else {
				st = new StringTokenizer(brs3,"`");
				kdpst = st.nextToken();
				String kdkmk = st.nextToken();
				String idkmk = st.nextToken();
				String final_mk  = st.nextToken();
				if(final_mk.equalsIgnoreCase("true")) {
					if(lulus) {
						//li1.set(brs3+"`"+udt.nextRandomNilai(kdjen));
						lif.add(thsms+"`"+npmhs+"`"+brs3+"`"+udt.nextRandomNilai(kdjen));
						//out.println("="+udt.nextRandomNilai(kdjen)+"<br>");
					}
					else {
						//li1.set(brs3+"`E`0");
						lif.add(thsms+"`"+npmhs+"`"+brs3+"`E`0");
						//out.println("=E`0<br>");
					}
				}
				else {
					//li1.set(brs3+"`"+udt.nextRandomNilai(kdjen));
					lif.add(thsms+"`"+npmhs+"`"+brs3+"`"+udt.nextRandomNilai(kdjen));
					//out.println("="+udt.nextRandomNilai(kdjen)+"<br>");
				}
			}
		}
	}
}
if (vf!=null) {
	udt.updateTrnlm(vf);
}
/*
ListIterator li = v.listIterator();
while(li.hasNext()) {
  	String brs = (String)li.next();
	////System.out.println(brs);
}
*/
//TAMBAHAN UNTUK LULUSAN tapi diepsbed keluar
v = sdf.getKrsMhsDgnNilaiTundaYgLulusAtThsms(thsms);
//System.out.println("3");
//System.out.println("vsize="+v.size());
//kasih nilai
vf = new Vector();
lif = vf.listIterator();

if (v!=null) {
	ListIterator li = v.listIterator();
	while(li.hasNext()) {
		String brs1 = (String)li.next();
		//System.out.println("brs1="+brs1);
		st = new StringTokenizer(brs1,"`");
		String npmhs = st.nextToken();
		////System.out.println(brs1+"<br>");
		String brs2 = (String)li.next();
		//System.out.println("brs2="+brs2);
		boolean lulus = false;
		if(brs2.equalsIgnoreCase("L")) {
			//////System.out.println("Lulus<br>");
			lulus = true;
		}
		else {
			//////System.out.println(brs2+"<br>");
		}
		Vector v_trnlm = (Vector) li.next();
		ListIterator li1 = v_trnlm.listIterator();
		while(li1.hasNext()) {
			String brs3 = (String) li1.next();
			//////System.out.println("brs1="+brs1);
			////System.out.println(brs3);
			if(brs3.contains("ERROR")) {
				
			}
			else {
				st = new StringTokenizer(brs3,"`");
				kdpst = st.nextToken();
				String kdkmk = st.nextToken();
				String idkmk = st.nextToken();
				String final_mk  = st.nextToken();
				if(final_mk.equalsIgnoreCase("true")) {
					if(lulus) {
						//li1.set(brs3+"`"+udt.nextRandomNilai(kdjen));
						lif.add(thsms+"`"+npmhs+"`"+brs3+"`"+udt.nextRandomNilai(kdjen));
						//out.println("="+udt.nextRandomNilai(kdjen)+"<br>");
					}
					else {
						//li1.set(brs3+"`E`0");
						lif.add(thsms+"`"+npmhs+"`"+brs3+"`E`0");
						//out.println("=E`0<br>");
					}
				}
				else {
					//li1.set(brs3+"`"+udt.nextRandomNilai(kdjen));
					lif.add(thsms+"`"+npmhs+"`"+brs3+"`"+udt.nextRandomNilai(kdjen));
					//out.println("="+udt.nextRandomNilai(kdjen)+"<br>");
				}
			}
		}
	}
}
if (vf!=null) {
	//System.out.println("vff="+vf.size());
	udt.updateTrnlm(vf);
}

%>
DONE 
</body>
</html>
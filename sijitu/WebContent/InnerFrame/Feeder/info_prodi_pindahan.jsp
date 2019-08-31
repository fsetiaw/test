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
  String prodi = request.getParameter("tags4");
  String thsms = request.getParameter("thsms");
  StringTokenizer st = new StringTokenizer(prodi,"`");
  String kdpst = st.nextToken();
  String nmpst = st.nextToken();
  SearchDbFeeder sdf = new SearchDbFeeder();
  Vector v = null;
  if(kdpst.equalsIgnoreCase("88888")) {
	  v = sdf.getInfoMalaikatAndReal(thsms, "/home/usg/USG/EPSBED/"+thsms+"/txt_files/mhs_aktif_86208_20152.txt");
  }
  else {
	  v = sdf.getInfoMalaikatAndReal(thsms, "/home/usg/USG/EPSBED/"+thsms+"/txt_files/mhs_aktif_"+kdpst+"_20152.txt");  
  }
  	Vector vmhs = null;
	Vector vang = null;
  %>
</head>
<body>

	<%=prodi %>
	<%="<br>"+thsms %>
	<%
	if(v!=null) {
		ListIterator li = v.listIterator();
		while(li.hasNext()) {
			vmhs = (Vector)li.next();
			vang = (Vector)li.next();
		}
	}
	else {
		System.out.println("v = null");
	}
	%>
	<br>
	Jumlah mahasiswa : <%=vmhs.size() %><br>
	<%
	Vector vshift = sdf.filterMhsPerShift(vmhs);
	ListIterator li = vshift.listIterator();
	while(li.hasNext()) {
		Vector v_mhs_per_shift = (Vector) li.next();
		
		ListIterator litmp = v_mhs_per_shift.listIterator();
		st = new StringTokenizer((String)litmp.next(),"`");
		String shift = st.nextToken();
		out.print("<br>"+shift+" = "+v_mhs_per_shift.size()+"<br>");
		Vector vsmawl = sdf.filterMhsPerAngkatan(v_mhs_per_shift);
		ListIterator lisma = vsmawl.listIterator();
		while(lisma.hasNext()) {
			String list_npm = null;
			Vector v_mhs_ang = (Vector)lisma.next();
			litmp = v_mhs_ang.listIterator();
			boolean first_line = true;
			while(litmp.hasNext()) {
				st = new StringTokenizer((String)litmp.next(),"`");
				String angkatan = st.nextToken();
				String npmhs = st.nextToken();
				String nimhs = st.nextToken();
				String nmmhs = st.nextToken();
				shift = st.nextToken();
				String stpid = st.nextToken();
				if(stpid.equalsIgnoreCase("P")) {
					if(first_line) {
						first_line = false;
						out.print("&nbsp&nbsp&nbsp&nbspangkatan "+angkatan+" = "+v_mhs_ang.size()+"<br>");
						out.print("&nbsp&nbsp&nbsp&nbsp["+npmhs);
						list_npm = new String(npmhs);
					}
					else {
						out.print(npmhs);
						list_npm = list_npm+"`"+npmhs;
					}	
					if(litmp.hasNext()) {
						out.print(",");
					}
				}
			}
			if(!first_line) {
				out.print("]<br>");	
			}
			if(list_npm!=null) {
				st = new StringTokenizer(list_npm,"`");
				if(st.countTokens()>1) {
			%>
				<form action="copyFromNpmPertama.jsp" method="post">
					<input type="hidden" name="list_npm_pindahan_dlm_1_angkatam" value="<%=list_npm%>" />
					<input type="submit" value="COPY FROM NPM PERTAMA" />	
				</form>
				<br/>
				sini
			<%		
				}
			}
			
		}
		
	}
	
	%>
	<br>Jumlah malaikat  : <%=vang.size() %><br>
	<%
	vshift = sdf.filterMhsPerShift(vang);
	li = vshift.listIterator();
	while(li.hasNext()) {
		Vector v_mhs_per_shift = (Vector) li.next();
		ListIterator litmp = v_mhs_per_shift.listIterator();
		st = new StringTokenizer((String)litmp.next(),"`");
		String shift = st.nextToken();
		out.print("<br>"+shift+" = "+v_mhs_per_shift.size()+"<br>");
		Vector vsmawl = sdf.filterMhsPerAngkatan(v_mhs_per_shift);
		ListIterator lisma = vsmawl.listIterator();
		while(lisma.hasNext()) {
			String list_npm = "";
			Vector v_mhs_ang = (Vector)lisma.next();
			litmp = v_mhs_ang.listIterator();
			boolean first_line = true;
			while(litmp.hasNext()) {
				st = new StringTokenizer((String)litmp.next(),"`");
				//lim.set(smawl+"`"+npmhs+"`"+nimhs+"`"+nmmhs+"`"+shift+"`"+stpid);
				String angkatan = st.nextToken();
				String npmhs = st.nextToken();
				String nimhs = st.nextToken();
				String nmmhs = st.nextToken();
				shift = st.nextToken();
				String stpid = st.nextToken();
				if(stpid.equalsIgnoreCase("P")) {
					//list_npm = list_npm+npmhs;
				
					if(first_line) {
						first_line = false;
						out.print("&nbspangkatan "+angkatan+" = "+v_mhs_ang.size()+"<br>");
						out.print("&nbsp&nbsp&nbsp&nbsp["+npmhs);
						list_npm = new String(npmhs);
					}
					else {
						out.print(npmhs);
						list_npm = list_npm+"`"+npmhs;
					}	
					if(litmp.hasNext()) {
						out.print(",");
					//list_npm = list_npm+",";
					}
				}
				
			}
			if(!first_line) {
				out.print("]<br>");	
			}
			if(list_npm!=null) {
				st = new StringTokenizer(list_npm,"`");
				if(st.countTokens()>1) {
			%>
				<form action="copyFromNpmPertama.jsp" method="post">
					<input type="hidden" name="list_npm_pindahan_dlm_1_angkatam" value="<%=list_npm%>" />
					<input type="submit" value="COPY FROM NPM PERTAMA" />	
				</form>
				<br/>
				<form action="go.updNilaiPenyetaraan" method="post">
					<input type="hidden" name="list_npm_pindahan_dlm_1_angkatam" value="<%=list_npm%>" />
					<input type="submit" value="UPDATE NILAI STPID" />	
				</form>
				<br/>
			<%		
				}
			}
			
		}
		
	}
	
	%>
	<br>
	
<%

%>
 
</body>
</html>
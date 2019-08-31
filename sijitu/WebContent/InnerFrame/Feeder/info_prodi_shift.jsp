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
  String prodi = request.getParameter("tags2");
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
			Vector v_mhs_ang = (Vector)lisma.next();
			litmp = v_mhs_ang.listIterator();
			boolean first_line = true;
			while(litmp.hasNext()) {
				st = new StringTokenizer((String)litmp.next(),"`");
				String angkatan = st.nextToken();
				String npmhs = st.nextToken();
				if(first_line) {
					first_line = false;
					out.print("&nbsp&nbsp&nbsp&nbspangkatan "+angkatan+" = "+v_mhs_ang.size()+"<br>");
					out.print("&nbsp&nbsp&nbsp&nbsp["+npmhs);
				}
				else {
					out.print(npmhs);
				}	
				if(litmp.hasNext()) {
					out.print(",");
				}
			}
			out.print("]<br>");
			Vector v_kur = sdf.filterMhsPerKurikulum(v_mhs_ang);
			ListIterator likur = v_kur.listIterator();
			while(likur.hasNext()) {
				Vector v_mhs_kur = (Vector)likur.next();
				litmp = v_mhs_kur.listIterator();
				st = new StringTokenizer((String)litmp.next(),"`");
				String ko = st.nextToken();
				out.print("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspKO "+ko+" = "+v_mhs_kur.size()+"<br>");	
					
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
				String angkatan = st.nextToken();
				String npmhs = st.nextToken();
				list_npm = list_npm+npmhs;
				if(first_line) {
					first_line = false;
					out.print("&nbspangkatan "+angkatan+" = "+v_mhs_ang.size()+"<br>");
					//out.print("&nbsp&nbsp&nbsp&nbsp["+npmhs);
				%>
				
			<form action="update_shift.jsp" method="post">
				<input type="hidden" name="angkatan" value="<%=angkatan %>"/>
				<input type="hidden" name="prodi" value="<%=prodi %>"/>
				<input type="hidden" name="thsms" value="<%=thsms %>"/>
				<%	
				}
				else {
					//out.print(npmhs);
				}	
				if(litmp.hasNext()) {
					//out.print(",");
					list_npm = list_npm+",";
				}
				
			}
			%>
			
				<table width="90%">
					<tr>
						<td>
							<textarea name="list_npm" rows="4" style="width:99%"><%=list_npm %></textarea>
						</td>
					</tr>
					<tr>
						<td>
						<%
						StringTokenizer st2 = new StringTokenizer(list_npm,",");
						%>
							SHIFT [<%=st2.countTokens() %>]<input type="text" name="target_shift" style="width:99%" placeholder="ttmhs1,shift1`ttmhs2,shift2"/>
						</td>
					</tr>
					<tr>
						<td>
							<input type="submit" value="submit" />
						</td>
					</tr>
				</table>
			</form>
			
			<%
			//out.print("]<br>");

		}
		
	}
	
	%>
	<br>
	
<%

%>
 
</body>
</html>
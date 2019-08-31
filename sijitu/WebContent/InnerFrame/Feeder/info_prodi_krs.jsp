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
  String prodi = request.getParameter("tags3");
  String thsms = request.getParameter("thsms");
  StringTokenizer st = new StringTokenizer(prodi,"`");
  String kdpst = st.nextToken();
  String kdjen = Checker.getKdjen(kdpst);
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
	<%=prodi %><br>
	<%=kdjen %><br>
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
	Vector vf = new Vector();
	ListIterator lif = vf.listIterator();
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
					out.print("&nbsp&nbsp&nbsp&nbsp["+npmhs);
				}
				else {
					out.print(npmhs);
				}	
				if(litmp.hasNext()) {
					out.print(",");
					list_npm = list_npm+",";
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
				out.print("&nbspKO "+ko+" = "+v_mhs_kur.size()+"<br><br><br>");	
				Vector vkrs = null;
				if(kdjen.equalsIgnoreCase("A")||kdjen.equalsIgnoreCase("B")) {
					sdf.getMkSesuaiAngkatanPerMhsDariSms1_Pasca(v_mhs_kur,Tool.returnNextThsmsGivenTpAntara(thsms),Integer.parseInt(ko));
					//vkrs = sdf.getMkSesuaiAngkatanPerMhs_Pasca(v_mhs_kur,thsms,Integer.parseInt(ko));
				}
				else if(kdjen.equalsIgnoreCase("C")) {
					//vkrs = sdf.getMkSesuaiAngkatanPerMhs_S1(v_mhs_kur,thsms,Integer.parseInt(ko));
					vkrs = sdf.getMkSesuaiAngkatanPerMhsDariSms1_S1(v_mhs_kur,Tool.returnNextThsmsGivenTpAntara(thsms),Integer.parseInt(ko));
				}
				else if(kdjen.equalsIgnoreCase("D")) {
					sdf.getMkSesuaiAngkatanPerMhsDariSms1_D3(v_mhs_kur,Tool.returnNextThsmsGivenTpAntara(thsms),Integer.parseInt(ko));
					//vkrs = sdf.getMkSesuaiAngkatanPerMhs_D3(v_mhs_kur,thsms,Integer.parseInt(ko));
				}
				lif.add(vkrs);
				ListIterator lik = vkrs.listIterator();
				//System.out.println("vkrs="+vkrs.size());
				
				
				while(lik.hasNext()) {
					String info_mhs = (String)lik.next();
					//System.out.println("info_mhs="+info_mhs);
					boolean nu_iter = false;
					while(lik.hasNext() && !nu_iter) {
						String sms = (String)lik.next();
						if(sms.contains("`")) {
							nu_iter = true;
							lik.previous();
						}
						else {
							//System.out.println("sms="+sms);
							out.print("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp ("+sms+")"+info_mhs+"<br>");
							
							
							Vector vtmp = (Vector)lik.next();
							//System.out.println("yes vector");
							ListIterator lit = vtmp.listIterator();
							int no = 0;
							while(lit.hasNext()) {
								no++;
								String info_mk = (String)lit.next();
								out.print("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp ("+sms+"-mk)"+info_mk+"<br>");
							}	
						}	
					}
				}
			}	
			
		}
		
	}

	lif = vf.listIterator();
	//System.out.println("vf.size="+vf.size());
	UpdateDbFeeder udf = new UpdateDbFeeder();
	while(lif.hasNext()) {
		Vector vkrs = (Vector)lif.next();
		//System.out.println("vkrs.size="+vkrs.size());
		udf.deletePrevKrsTheninsertKrs_v1(vkrs, kdpst, kdjen);
	}
	
	%>
	<br>
	DONE
<%

%>
 
</body>
</html>
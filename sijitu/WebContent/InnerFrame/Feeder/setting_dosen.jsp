<!doctype html>
<html lang="en">
<head>
<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>
	<%@ page import="java.util.*" %>
	<%@ page import="beans.dbase.wilayah.*" %>
	<%@ page import="beans.dbase.dosen.*" %>
	<%@ page import="beans.dbase.trlsm.*" %>
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

  <%
  //String prodi = request.getParameter("tags5");
  Vector v_dos = Getter.getListDosen_v1();
  String thsms = request.getParameter("thsms");
  SearchDbDsn sdd = new SearchDbDsn();
  Vector v = null;
  v = sdd.getListInfoTrakd(thsms);
  %>
</head>
<body>
SETTING DOSEN <%=thsms %><br>
<%
if(v!=null && v.size()>0) {
	ListIterator li = v.listIterator();
%>
	<form action="go.updateTrakd" method="post">
	<input type="hidden" name="target_thsms" value="<%=thsms %>">
	<table width="850px" border="1">
		<thead style="background:#369;color:white">
			<tr>
			<th width="25px">
			No.
			</th>
			<th style="text-align:left" width="125px">
			Prodi
			</th>
			<th width="75px">
			Kode MK
			</th>
			<th width="200px">
			Nama MK
			</th>
			<th width="100px">
			Shift
			</th>
			<th width="50px">
			Kelas
			</th>
			<th width="275px">
			Nama Dosen
			</th>
			
			
			</tr>
		</thead>	
		
<%	
	int norut=1;
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		thsms =  st.nextToken();
		String kdpst =  st.nextToken();
		String nodos =  st.nextToken();
		String nmdos =  st.nextToken();
		String idkmk =  st.nextToken();
		String kdkmk =  st.nextToken();
		String nakmk =  st.nextToken();
		String kelas =  st.nextToken();
		String sksmk =  st.nextToken();
		String shift =  st.nextToken();
		
%>
	<tr style="background:#d9e1e5">
		<td style="padding:0 0 0 3px">
			<%=norut++ %>
		</td>
		<td>
			<%=Converter.getNamaKdpst(kdpst) %>
			<input type="hidden" name="kdpst" value="<%=Checker.pnn(kdpst) %>"/>
		</td>
		<td>
			<%=kdkmk %>
			<input type="hidden" name="kdkmk" value="<%=Checker.pnn(kdkmk) %>"/>
		</td>
		<td>
			<%=nakmk %>
			
		</td>
		<td>
			<%=shift %>
			<input type="hidden" name="shift" value="<%=Checker.pnn(shift) %>"/>
		</td>
		<td>
			<input type="hidden" name="old_kelas" value="<%=Checker.pnn(kelas) %>"/>
			<input style="width:95%;height:99%;text-align:center" type="text" name="kelas" value="<%=Checker.pnn(kelas) %>"/>
		</td>
		<td>
		<%

		String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/select_dosen.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);

		%>
			<%@include file="../Tamplete/select_dosen.jsp" %>
			
		</td>
	</tr>
	
	
<%		
	}
%>
	<tr style="background:#369">
		<td colspan="7" align="center" style="padding:5px 5px">
			<input type="submit" value="Update" style="width:50%;height:35px"/>
		</td>
	</tr>
</table>
</form>
<%
}
%>
</body>
</html>
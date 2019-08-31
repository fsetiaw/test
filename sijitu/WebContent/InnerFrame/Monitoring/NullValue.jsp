<!DOCTYPE html>

<head>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%
Vector v = (Vector)request.getAttribute("v");
String target_thsms = request.getParameter("thsms_target");
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
int i = 0;
if(v!=null && v.size()>0) {
	ListIterator li = v.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		if(brs.startsWith("DAFTAR_ULANG")) {
			i=0;
			//list = list+"`"+id+"`"+kdpst+"`"+tkn_jab+"`"+tkn_id;
			StringTokenizer st = new StringTokenizer(brs,"`");//105`54201`PST`[KTU][Ka. BAUK]`[null][63]
			if(st.countTokens()>1) {
				String nm_tabel = st.nextToken();
				if(st.hasMoreTokens()) {
					out.print("<h3>"+nm_tabel+" "+target_thsms+"</h3>");
					do {
						i++;
						//105`54201`PST`[KTU][Ka. BAUK]`[null][63]
						String id_obj = st.nextToken();
						String kdpst = st.nextToken();
						
						String tkn_job = st.nextToken();	
						String tkn_id = st.nextToken();	
						out.print(i+". ["+id_obj+"]["+Converter.getDetailKdpst_v1(kdpst)+"]"+tkn_job+""+tkn_id+"<br/>");
					}
					while(st.hasMoreTokens());
				}
			}
		}
		else {
			i = 0;
			StringTokenizer st = new StringTokenizer(brs,"`");//105`54201`PST`[KTU][Ka. BAUK]`[null][63]
			if(st.countTokens()>1) {
				String nm_tabel = st.nextToken();
				if(st.hasMoreTokens()) {
					out.print("<h3>"+nm_tabel+" "+target_thsms+"</h3>");
					do {
						i++;
						//105`54201`PST`[KTU][Ka. BAUK]`[null][63]
						String id_obj = st.nextToken();
						String kdpst = st.nextToken();
						String kmp = st.nextToken();
						String tkn_job = st.nextToken();	
						String tkn_id = st.nextToken();	
						out.print(i+". ["+id_obj+"]["+Converter.getDetailKdpst_v1(kdpst)+"]["+kmp+"]"+tkn_job+tkn_id+"<br/>");
					}
					while(st.hasMoreTokens());
				}
			}
			
		}
		
	}
}
%>
</body>
</html>
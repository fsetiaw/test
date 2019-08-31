<!DOCTYPE html>
<head>
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>
	<%@ page import="java.util.StringTokenizer" %>
	
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
//System.out.println("sampe sini");
Vector v_list = (Vector)request.getAttribute("v_list");
request.removeAttribute("v_list");
%>
</head>
<body>
<%
if(v_list!=null && v_list.size()>0) {
	//System.out.println("vlist="+v_list.size());
	ListIterator li = v_list.listIterator();
	ListIterator lit = null;
	
	while(li.hasNext()) {
		String kmp = (String)li.next();
		
		Vector vtmp = (Vector)li.next();
		lit = vtmp.listIterator();
		while(lit.hasNext()) {
			String idobj = (String)lit.next();
			String nmprodi = Converter.getNamaKdpst(Integer.parseInt(idobj));
			%>
<h2>
	 PRODI: <%=nmprodi %>
</h2>			
			<%
			String list_mhs = (String)lit.next();
			//list_mhs = list_mhs+"`"+id+"`"+smawl+"`"+npmhs+"`"+nmmhs+"`"+usr+"`"+pwd;
			int i = 1;
			StringTokenizer st = new StringTokenizer(list_mhs,"`");
			if(list_mhs!=null && !Checker.isStringNullOrEmpty(list_mhs)) {
				while(st.hasMoreTokens()) {
					String id = st.nextToken();
					String smawl = st.nextToken();
					String npmhs = st.nextToken();
					String nmmhs = st.nextToken();
					String usr = st.nextToken();
					String pwd = st.nextToken();
		%>
			<%=i++ %>.&nbsp&nbspAngkatan:<%=smawl %>&nbsp&nbsp[<%=npmhs %>]&nbsp&nbsp<%=nmmhs %><br/>
			&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspUsername / Password = <%=usr %> / <%=pwd %><br/><br/>
		<%			
					
				}	
			}
			else {
				out.print("0 Mahasiswa");
			}
			//System.out.println(nmprodi+"="+list_mhs);
			
			
			
		}
		
		
		
	}
}
out.println("<CENTER>-----------------------------------------</CENTER>");
%>
</body>
</html>
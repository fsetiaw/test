<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.tools.*" %>
<%
/*
!!!!TAROH DI MAIN PAGE
Vector v_dos = Getter.getListDosen_v1();
*/


%>
<select name="target_dosen" style="width:99%;text-align-last:center;">
	<option value="null">PILIH DOSEN AJAR</option>
<%
ListIterator li_dos = v_dos.listIterator();
while(li_dos.hasNext()) {
	String inpo_dos = (String)li_dos.next();
	StringTokenizer st_dos = new StringTokenizer(inpo_dos,"`");
	String nama_dos = st_dos.nextToken();
	String nidn_dos = st_dos.nextToken();
	String nomor_dos = st_dos.nextToken();
	if(inpo_dos.startsWith(nmdos+"`")) {
		%>
		<option value="<%=inpo_dos%>" selected="selected"><%=nama_dos %></option>
	<%		
	}
	else {
%>
	<option value="<%=inpo_dos%>"><%=nama_dos %></option>
<%	
	}
}
%>
</select>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="beans.dbase.wilayah.*"%>
<%
	AutoCompleteNegara db = new AutoCompleteNegara();

	String query = request.getParameter("q");
	
	//List<String> countries = db.getData_v1(query);
	List<String> countries = db.getData(query);
	Iterator<String> iterator = countries.iterator();
	while(iterator.hasNext()) {
		String country = (String)iterator.next();
		out.println(country);
	}
%>
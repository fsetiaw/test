<!DOCTYPE html>
<html>
<head>
<title>Bootstrap-select test page</title>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="beans.dbase.spmi.request.*"%>
<%@ page import="beans.dbase.spmi.*"%>

<title>UNIVERSITAS SATYAGAMA</title>
<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-select-1.12.2/dist/css/bootstrap-select.css">
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
<script src="<%=Constants.getRootWeb() %>/bootstrap-select-1.12.2/dist/js/bootstrap-select.js"></script>

  	<script>
  	$(document).ready(function () {
    		var mySelect = $('#first-disabled2');

    		$('#special').on('click', function () {
      		mySelect.find('option:selected').prop('disabled', true);
      		mySelect.selectpicker('refresh');
    	});

    	$('#special2').on('click', function () {
      		mySelect.find('option:disabled').prop('disabled', false);
      		mySelect.selectpicker('refresh');
    	});

	    $('#basic2').selectpicker({
    	  	liveSearch: true,
      		maxOptions: 1
    	});
  	});
	</script>
	
<%
Vector v_tmp = null;
ListIterator li = null;
SearchSpmi ss = new SearchSpmi();
String tkn_param=request.getParameter("tkn_param");
if(Checker.isStringNullOrEmpty(tkn_param)) {
	tkn_param = "null";
}

//System.out.println("tkn_param="+tkn_param);
StringTokenizer std = new StringTokenizer(tkn_param,",");
String tmp_param = "";
%>	
</head>
<body>
<table width="100%">
	<tr>
		<td>
			<select class="selectpicker show-tick form-control" data-live-search="true" name="tkn_variable">
				<option value="null">Pilih Parameter  atau tambah (+) parameter bila belum terdaftar</option>
<%
	v_tmp = ss.getListParameter();
	li = v_tmp.listIterator();
	if(std.hasMoreTokens()) {
		tmp_param = std.nextToken();
	}
	else {
		tmp_param="";
	}
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String nama_param=st.nextToken();
		if(tmp_param.equalsIgnoreCase(nama_param)) {
%>
				<option value="<%=nama_param%>" selected="selected"><%=nama_param %></option>
<%		
		}
		else {
%>
				<option value="<%=nama_param%>"><%=nama_param %></option>
<%	
		}
	}

%>          					
			</select>
		</td>
	</tr>				
</table>

</body>
</html>
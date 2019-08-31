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
String tkn_doc=request.getParameter("tkn_doc");
if(Checker.isStringNullOrEmpty(tkn_doc)) {
	//minimal dokumen yg harus ada
	//System.out.println("tkn doc is null");
	tkn_doc = "MANUAL PENETAPAN,MANUAL PELAKSANAAN,MANUAL EVALUASI,MANUAL PENGAWASAN,MANUAL PENGENDALIAN,KEBIJAKAN MUTU";
}
else {
	//System.out.println("tkn doc is NOTnull");
}

//System.out.println("tkn_doc="+tkn_doc);
StringTokenizer std = new StringTokenizer(tkn_doc,",");
String tmp_doc = "";
%>	
</head>
<body>
<table width="100%">
	<tr>
		<td>
			<select class="selectpicker show-tick form-control" data-live-search="true" name="doc">
				<option value="null">Pilih Dokumen</option>
<%
	v_tmp = ss.getListNamaDokument();
	li = v_tmp.listIterator();
	if(std.hasMoreTokens()) {
		tmp_doc = std.nextToken();
	}
	else {
		tmp_doc="";
	}
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String nama_doc=st.nextToken();
		if(tmp_doc.equalsIgnoreCase(nama_doc)) {
%>
				<option value="<%=nama_doc%>" selected="selected"><%=nama_doc %></option>
<%		
		}
		else {
%>
				<option value="<%=nama_doc%>"><%=nama_doc %></option>
<%	
		}
	}

%>          					
			</select>
		</td>
		<td>
			<select class="selectpicker show-tick form-control" data-live-search="true" name="doc">
				<option value="null">Pilih Dokumen</option>
<%
	v_tmp = ss.getListNamaDokument();
	li = v_tmp.listIterator();
	if(std.hasMoreTokens()) {
		tmp_doc = std.nextToken();
	}
	else {
		tmp_doc="";
	}
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String nama_doc=st.nextToken();
		if(tmp_doc.equalsIgnoreCase(nama_doc)) {
%>
				<option value="<%=nama_doc%>" selected="selected"><%=nama_doc %></option>
<%		
		}
		else {
%>
				<option value="<%=nama_doc%>"><%=nama_doc %></option>
<%	
		}
	}

%>        					
			</select>
		</td>
		<td>
			<select class="selectpicker show-tick form-control" data-live-search="true" name="doc">
				<option value="null">Pilih Dokumen</option>
<%
	v_tmp = ss.getListNamaDokument();
	li = v_tmp.listIterator();
	if(std.hasMoreTokens()) {
		tmp_doc = std.nextToken();
	}
	else {
		tmp_doc="";
	}
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String nama_doc=st.nextToken();
		if(tmp_doc.equalsIgnoreCase(nama_doc)) {
%>
				<option value="<%=nama_doc%>" selected="selected"><%=nama_doc %></option>
<%		
		}
		else {
%>
				<option value="<%=nama_doc%>"><%=nama_doc %></option>
<%	
		}
	}

%>       					
			</select>
		</td>
	</tr>
	<tr>
		<td>
			<select class="selectpicker show-tick form-control" data-live-search="true" name="doc">
				<option value="null">Pilih Dokumen</option>
<%
	v_tmp = ss.getListNamaDokument();
	li = v_tmp.listIterator();
	if(std.hasMoreTokens()) {
		tmp_doc = std.nextToken();
	}
	else {
		tmp_doc="";
	}
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String nama_doc=st.nextToken();
		if(tmp_doc.equalsIgnoreCase(nama_doc)) {
%>
				<option value="<%=nama_doc%>" selected="selected"><%=nama_doc %></option>
<%		
		}
		else {
%>
				<option value="<%=nama_doc%>"><%=nama_doc %></option>
<%	
		}
	}

%>         					
			</select>
		</td>
		<td>
			<select class="selectpicker show-tick form-control" data-live-search="true" name="doc">
				<option value="null">Pilih Dokumen</option>
<%
	v_tmp = ss.getListNamaDokument();
	li = v_tmp.listIterator();
	if(std.hasMoreTokens()) {
		tmp_doc = std.nextToken();
	}
	else {
		tmp_doc="";
	}
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String nama_doc=st.nextToken();
		if(tmp_doc.equalsIgnoreCase(nama_doc)) {
%>
				<option value="<%=nama_doc%>" selected="selected"><%=nama_doc %></option>
<%		
		}
		else {
%>
				<option value="<%=nama_doc%>"><%=nama_doc %></option>
<%	
		}
	}

%>         					
			</select>
		</td>
		<td>
			<select class="selectpicker show-tick form-control" data-live-search="true" name="doc">
				<option value="null">Pilih Dokumen</option>
<%
	v_tmp = ss.getListNamaDokument();
	li = v_tmp.listIterator();
	if(std.hasMoreTokens()) {
		tmp_doc = std.nextToken();
	}
	else {
		tmp_doc="";
	}
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String nama_doc=st.nextToken();
		if(tmp_doc.equalsIgnoreCase(nama_doc)) {
%>
				<option value="<%=nama_doc%>" selected="selected"><%=nama_doc %></option>
<%		
		}
		else {
%>
				<option value="<%=nama_doc%>"><%=nama_doc %></option>
<%	
		}
	}

%>           					
			</select>
		</td>
	</tr>
	<tr>
		<td>
			<select class="selectpicker show-tick form-control" data-live-search="true" name="doc">
				<option value="null">Pilih Dokumen</option>
<%
	v_tmp = ss.getListNamaDokument();
	li = v_tmp.listIterator();
	if(std.hasMoreTokens()) {
		tmp_doc = std.nextToken();
	}
	else {
		tmp_doc="";
	}
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String nama_doc=st.nextToken();
		if(tmp_doc.equalsIgnoreCase(nama_doc)) {
%>
				<option value="<%=nama_doc%>" selected="selected"><%=nama_doc %></option>
<%		
		}
		else {
%>
				<option value="<%=nama_doc%>"><%=nama_doc %></option>
<%	
		}
	}

%>   				
			</select>
		</td>
		<td>
			<select class="selectpicker show-tick form-control" data-live-search="true" name="doc">
				<option value="null">Pilih Dokumen</option>
<%
	v_tmp = ss.getListNamaDokument();
	li = v_tmp.listIterator();
	if(std.hasMoreTokens()) {
		tmp_doc = std.nextToken();
	}
	else {
		tmp_doc="";
	}
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String nama_doc=st.nextToken();
		if(tmp_doc.equalsIgnoreCase(nama_doc)) {
%>
				<option value="<%=nama_doc%>" selected="selected"><%=nama_doc %></option>
<%		
		}
		else {
%>
				<option value="<%=nama_doc%>"><%=nama_doc %></option>
<%	
		}
	}

%>   					
			</select>
		</td>
		<td>
			<select class="selectpicker show-tick form-control" data-live-search="true" name="doc">
				<option value="null">Pilih Dokumen</option>
<%
	v_tmp = ss.getListNamaDokument();
	li = v_tmp.listIterator();
	if(std.hasMoreTokens()) {
		tmp_doc = std.nextToken();
	}
	else {
		tmp_doc="";
	}
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String nama_doc=st.nextToken();
		if(tmp_doc.equalsIgnoreCase(nama_doc)) {
%>
				<option value="<%=nama_doc%>" selected="selected"><%=nama_doc %></option>
<%		
		}
		else {
%>
				<option value="<%=nama_doc%>"><%=nama_doc %></option>
<%	
		}
	}

%>     					
			</select>
		</td>
	</tr>
	<tr>
		<td>
			<select class="selectpicker show-tick form-control" data-live-search="true" name="doc">
				<option value="null">Pilih Dokumen</option>
<%
	v_tmp = ss.getListNamaDokument();
	li = v_tmp.listIterator();
	if(std.hasMoreTokens()) {
		tmp_doc = std.nextToken();
	}
	else {
		tmp_doc="";
	}
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String nama_doc=st.nextToken();
		if(tmp_doc.equalsIgnoreCase(nama_doc)) {
%>
				<option value="<%=nama_doc%>" selected="selected"><%=nama_doc %></option>
<%		
		}
		else {
%>
				<option value="<%=nama_doc%>"><%=nama_doc %></option>
<%	
		}
	}

%>   				
			</select>
		</td>
		<td>
			<select class="selectpicker show-tick form-control" data-live-search="true" name="doc">
				<option value="null">Pilih Dokumen</option>
<%
	v_tmp = ss.getListNamaDokument();
	li = v_tmp.listIterator();
	if(std.hasMoreTokens()) {
		tmp_doc = std.nextToken();
	}
	else {
		tmp_doc="";
	}
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String nama_doc=st.nextToken();
		if(tmp_doc.equalsIgnoreCase(nama_doc)) {
%>
				<option value="<%=nama_doc%>" selected="selected"><%=nama_doc %></option>
<%		
		}
		else {
%>
				<option value="<%=nama_doc%>"><%=nama_doc %></option>
<%	
		}
	}

%>   				
			</select>
		</td>
		<td>
			<select class="selectpicker show-tick form-control" data-live-search="true" name="doc">
				<option value="null">Pilih Dokumen</option>
<%
	v_tmp = ss.getListNamaDokument();
	li = v_tmp.listIterator();
	if(std.hasMoreTokens()) {
		tmp_doc = std.nextToken();
	}
	else {
		tmp_doc="";
	}
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String nama_doc=st.nextToken();
		if(tmp_doc.equalsIgnoreCase(nama_doc)) {
%>
				<option value="<%=nama_doc%>" selected="selected"><%=nama_doc %></option>
<%		
		}
		else {
%>
				<option value="<%=nama_doc%>"><%=nama_doc %></option>
<%	
		}
	}

%>           					
			</select>
		</td>
	</tr>
	<tr>
		<td>
			<select class="selectpicker show-tick form-control" data-live-search="true" name="doc">
				<option value="null">Pilih Dokumen</option>
<%
	v_tmp = ss.getListNamaDokument();
	li = v_tmp.listIterator();
	if(std.hasMoreTokens()) {
		tmp_doc = std.nextToken();
	}
	else {
		tmp_doc="";
	}
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String nama_doc=st.nextToken();
		if(tmp_doc.equalsIgnoreCase(nama_doc)) {
%>
				<option value="<%=nama_doc%>" selected="selected"><%=nama_doc %></option>
<%		
		}
		else {
%>
				<option value="<%=nama_doc%>"><%=nama_doc %></option>
<%	
		}
	}

%>           					
			</select>
		</td>
		<td>
			<select class="selectpicker show-tick form-control" data-live-search="true" name="doc">
				<option value="null">Pilih Dokumen</option>
<%
	v_tmp = ss.getListNamaDokument();
	li = v_tmp.listIterator();
	if(std.hasMoreTokens()) {
		tmp_doc = std.nextToken();
	}
	else {
		tmp_doc="";
	}
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String nama_doc=st.nextToken();
		if(tmp_doc.equalsIgnoreCase(nama_doc)) {
%>
				<option value="<%=nama_doc%>" selected="selected"><%=nama_doc %></option>
<%		
		}
		else {
%>
				<option value="<%=nama_doc%>"><%=nama_doc %></option>
<%	
		}
	}

%>        					
			</select>
		</td>
		<td>
			<select class="selectpicker show-tick form-control" data-live-search="true" name="doc">
				<option value="null">Pilih Dokumen</option>
<%
	v_tmp = ss.getListNamaDokument();
	li = v_tmp.listIterator();
	if(std.hasMoreTokens()) {
		tmp_doc = std.nextToken();
	}
	else {
		tmp_doc="";
	}
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String nama_doc=st.nextToken();
		if(tmp_doc.equalsIgnoreCase(nama_doc)) {
%>
				<option value="<%=nama_doc%>" selected="selected"><%=nama_doc %></option>
<%		
		}
		else {
%>
				<option value="<%=nama_doc%>"><%=nama_doc %></option>
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
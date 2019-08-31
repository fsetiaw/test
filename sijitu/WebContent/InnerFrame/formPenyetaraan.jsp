<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>

<style>
a.img:hover {
	text-decoration: none;
	background:none;
}

.table {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
}
.table thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9; }

.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px }
.table:hover td { background:#82B0C3 }
</style>
<style>
.table1 {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
}
.table1 thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table1 thead > tr > th, .table1 tbody > tr > t-->h, .table1 tfoot > tr > th, .table1 thead > tr > td, .table1 tbody > tr > td, .table1 tfoot > tr > td { border: 1px solid #2980B9; }

.table1-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table1-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table1-noborder thead > tr > th, .table1-noborder tbody > tr > th, .table1-noborder tfoot > tr > th, .table1-noborder thead > tr > td, .table1-noborder tbody > tr > td, .table1-noborder tfoot > tr > td { border: none;padding: 2px }

</style>
<style>
.table2 {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
}
.table2 thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table2 thead > tr > th, .table2 tbody > tr > t-->h, .table2 tfoot > tr > th, .table2 thead > tr > td, .table2 tbody > tr > td, .table2 tfoot > tr > td { border: 1px solid #2980B9; }

.table2-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table2-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table2-noborder thead > tr > th, .table2-noborder tbody > tr > th, .table2-noborder tfoot > tr > th, .table2-noborder thead > tr > td, .table2-noborder tbody > tr > td, .table2-noborder tfoot > tr > td { border: none;padding: 2px }
.table2 tr:hover td { background:#82B0C3 }
</style>
<style type="text/css">
	table.CityTable, table.StateTable{width:100%; border-color:#1C79C6; text-align:center;}
	table.StateTable{margin:0px; border:1px solid #369;}
	
	table td{padding:0px;}
	table.StateTable thead th{background: #369; padding: 0px; cursor:pointer; color:white;}
	table.CityTable thead th{padding: 0px; background: #C7DBF1;cursor:pointer; color:black;}
	
	table.StateTable td.nopad{padding:0;}
	table.StateTable tr.statetablerow { background:<%=Constant.lightColorBlu() %> }
	table.StateTable tr.statetablerow:hover { background:#fff }
</style>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
int i=0;
String aspti = request.getParameter("aspti");
String aspst = request.getParameter("aspst");
String fwdTo = request.getParameter("fwdTo");
String fwdPg = request.getParameter("fwdPg");
Vector v_listMakul = (Vector) request.getAttribute("v_listMakul");
Vector v_sisaMakul = new Vector();
String comment = (String)request.getAttribute("comment");
//ListIterator lism = v_sisaMakul.listIterator();
if(v_listMakul!=null) {
	//System.out.println("v_listMakul="+v_listMakul.size());
	v_sisaMakul = (Vector)v_listMakul.clone();
	
}
else {
	//System.out.println("v_listMakul empty");
}
%>

</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">
<%@ include file="dataMhsPindahanSubMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<center><h2>
		<%
		if(v_listMakul==null || v_listMakul.size()==0) {
			out.print("KURIKULUM UNTUK MHS INI BELUM DITENTUKAN");
		}
		else {
			//System.out.println("v_listMakul size="+v_listMakul.size());
		}
		if(v_trnlp==null || v_trnlp.size()==0) {
			out.print("TRANSKRIP DARI PERGURUAN TINGGI ASAL BELUM DIINPUT");
		}
		
		if(!Checker.isStringNullOrEmpty(comment)) {
			out.print("<h3 align=\"center\">"+comment+"</h3>");
		}
		//System.out.println("vtrnlp="+vtrnlp);
		if(vtrnlp && (v_listMakul!=null && v_listMakul.size()>0)) {
		%>
		</h2>
		</center>
		<form action=update.penyetaraan>
		<input type="hidden" name="id_obj" value="<%=objId %>" /><input type="hidden" name="nmm" value="<%= nmm%>" />
		<input type="hidden" name="npm" value="<%=npm %>" /><input type="hidden" name="obj_lvl" value="<%= obj_lvl%>" />
		<input type="hidden" name="kdpst" value="<%= kdpst%>" /><input type="hidden" name="aspti" value="<%= aspti%>" />
		<input type="hidden" name="cmd" value="<%=cmd %>" /><input type="hidden" name="fwdTo" value="<%= fwdTo%>" />
		<input type="hidden" name="fwdPg" value="formPenyetaraan.jsp" /><input type="hidden" name="aspst" value="<%=aspst %>" />
		
		<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="width:100%">
		<thead>
			<tr>
        		<th style="border-bottom:1px solid white;background:#369;color:#fff;text-align:center;font-size:2em" colspan="6"><label><B>FORM PENYETARAAN MATAKULIAH</B> </label></th>
        		
       		</tr>
       		<tr>
      			<td style="border-right:1px solid white;background:#369;color:#fff;text-align:center;width:5%"><label><B>NO.</B> </label></td></td>
      			<td style="border-left:1px solid white;border-right:1px solid white;background:#369;color:#fff;text-align:center;width:55%;font-size:1.2em"><label><B>MATAKULIAH</B> </label></td></td>
      			<td style="border-left:1px solid white;border-right:1px solid white;background:#369;color:#fff;text-align:center;width:15%;font-size:1.2em"><label><B>KODE MK ASAL</B> </label></td></td>
     			<td style="border-left:1px solid white;border-right:1px solid white;background:#369;color:#fff;text-align:center;width:10%;font-size:1.2em"><label><B>NILAI</B> </label></td></td>
        		<td style="border-left:1px solid white;border-right:1px solid white;background:#369;color:#fff;text-align:center;width:10%;font-size:1.2em"><label><B>BOBOT</B> </label></td></td>
        		<td style="border-left:1px solid white;background:#369;color:#fff;text-align:center;width:5%"><label><B>SKS</B> </label></td></td>
        	</tr>
        </thead>
        <tbody>	
		<%
		//yg digunakan hanya vtrnlp - data dari tabel
		//if(vtrnlm) {
		//	//System.out.println("v_-trnlm- = "+v0.size());
		//}	
		//System.out.println("1");
		
			//System.out.println("2");
		
			//System.out.println("v_-trnlp- = "+v0.size());
			
			ListIterator li0 = v0.listIterator();
			if(v_sisaMakul!=null) {
				while(li0.hasNext()) {
					String thsms = (String)li0.next();
					String kdkmk = (String)li0.next();
					String nakmk = (String)li0.next();
					String nlakh = (String)li0.next();
					String bobot = (String)li0.next();
					String kdasl = (String)li0.next();
					String nmasl = (String)li0.next();
					String sksmk = (String)li0.next();
					String totSks = (String)li0.next();//ignore
					String sksas = (String)li0.next();
					String transferred = (String)li0.next();
					ListIterator lim = v_sisaMakul.listIterator();
					//System.out.println("v_sisaMakul0="+v_sisaMakul.size());
					boolean match = false;
					while(lim.hasNext()&&!match) {
						lim.next();
						String kodeMk = (String)lim.next();
						lim.next();
						lim.next();
						lim.next();
						lim.next();
						lim.next();
						lim.next();
						lim.next();
						lim.next();
						lim.next();
						//System.out.println("kdkmk="+kdkmk+" vs kodeMk="+kodeMk);
						//System.out.println(thsms+","+kdkmk+","+nakmk+","+nlakh+","+bobot+","+kdasl+","+nmasl+","+sksmk);
						if(kdkmk!=null&&kdkmk.equalsIgnoreCase(kodeMk)&&Boolean.valueOf(transferred).booleanValue()) {
							match = true;
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
						}
					}
				}
			}	
			
			if(v_listMakul!=null) {
				//System.out.println("v_listMakul="+v_listMakul.size());
				//System.out.println("v_sisaMakul="+v_sisaMakul.size());
			}
			else {
				//System.out.println("v_listMakul empty");
			}
			//System.out.println("--1");
			li0 = v0.listIterator();
			while(li0.hasNext()) {
				i++;
			%>
			<tr>
			<%
				String thsms = (String)li0.next();
				String kdkmk = (String)li0.next();
				String nakmk = (String)li0.next();
				String nlakh = (String)li0.next();
				String bobot = (String)li0.next();
				String kdasl = (String)li0.next();
				String nmasl = (String)li0.next();
				String sksmk = (String)li0.next();
				String totSks = (String)li0.next();//ignore
				String sksas = (String)li0.next();
				String transferred = (String)li0.next();
				//System.out.println(thsms+"-"+kdkmk+"-"+kdasl+"-"+nmasl+"-"+transferred);
		%>
				<td rowspan="2" style="border-bottom:1px solid #369;border-right:1px solid #369;background:<%=Constant.lightColorGrey() %>;color:#000;text-align:center;"><label><B><%=i %></B> </label></td>
				<td style="padding:0 0 0 5px;border-bottom:1px solid #369;border-left:1px solid #369;border-right:1px solid #369;background:<%=Constant.lightColorGrey() %>;color:#000;text-align:left;"><label><B><%=nmasl %></B> </label></td>
        		<td style="border-bottom:1px solid #369;border-left:1px solid #369;border-right:1px solid #369;background:<%=Constant.lightColorGrey() %>;color:#000;text-align:center;"><label><B><%=kdasl %></B> </label></td>
        		<td style="border-bottom:1px solid #369;border-left:1px solid #369;border-right:1px solid #369;background:<%=Constant.lightColorGrey() %>;color:#000;text-align:center;"><label><B><%=nlakh %></B> </label></td>
        		<td style="border-bottom:1px solid #369;border-left:1px solid #369;border-right:1px solid #369;background:<%=Constant.lightColorGrey() %>;color:#000;text-align:center;"><label><B><%=bobot %></B> </label></td>
        		<td style="border-bottom:1px solid #369;border-left:1px solid white;background:<%=Constant.lightColorGrey() %>;color:#000;text-align:center;"><label><B><%=sksas %></B> </label></td>
        	</tr>
        	<tr>	
        		<%
        		//if(Boolean.valueOf(transferred).booleanValue()) {
        		%>	
        		<td style="padding:0 0 0 5px;border-bottom:1px solid #369;border-right:1px solid #369;background:#88BCF0;color:#000;text-align:left;width:515px"><label><B>KODE MATAKULIAH PENYETARAAN</B> </label></td></td>
        		<td colspan="4" style="background:#88BCF0;color:#000;text-align:left;"><label><B>
       
        		<select name="listMakul" style="width:100%;height:35px;border-bottom:1px solid #369;text-align-last:center">
            	<%
            		//v_sisaMakul ngga jadi dipake
            		//System.out.println("--2");
            		ListIterator lism = v_listMakul.listIterator();
            		boolean match = false;
            		while(lism.hasNext()) {
            			String idkmk1 = (String)lism.next();
        				String kdkmk1 = (String)lism.next();
        				String nakmk1 = (String)lism.next();
        				String skstm1 = (String)lism.next();
        				String skspr1 = (String)lism.next();
        				String skslp1 = (String)lism.next();
        				String kdwpl1 = (String)lism.next();
        				String jenis1 = (String)lism.next();
        				String stkmk1 = (String)lism.next();
        				String nodos1 = (String)lism.next();
        				String semes1 = (String)lism.next();
        				int sksmk1 = Integer.valueOf(skstm1).intValue()+Integer.valueOf(skslp1).intValue()+Integer.valueOf(skspr1).intValue();
        				if(kdkmk1.equalsIgnoreCase(kdkmk)) {
        					match = true;
        				%>
                		<option value="<%= kdkmk1+"#"+kdasl+"#"+nakmk1%>" selected="selected"><%= kdkmk1%>-<%= nakmk1%>,<%= sksmk1%> sks</option>
                		<%
        				}
        				else {
        				%>
                		<option value="<%= kdkmk1+"#"+kdasl+"#"+nakmk1%>"><%= kdkmk1%>-<%= nakmk1%>,<%= sksmk1%> sks</option>
                		<%	
        				}
            		}
            		if(!match) {
            			%>
            			<option value="<%="0#"+kdasl+"#"%>" selected="selected">--PILIH MATAKULIAH PENYETARAAN--</option>
            			<%
            		}
            		else {
            			%>
            			<option value="<%= "0#"+kdasl+"#"%>">--PILIH MATAKULIAH PENYETARAAN--</option>
            			<%	
            		}
            	%>
            	</select>
        		</B> </label></td>
        	</tr>
        <%
			}
		
        %>	
    	<tr>
    		<td style="background:<%=Constant.lightColorBlu() %>;color:#fff;text-align:center;padding:10px 10px" colspan="6">
        		<section class="gradient">
     				<div style="text-align:center">
           				<button style="padding: 5px 50px;font-size: 20px;">SETARAKAN</button>
     				</div>
				</section>
        	</td>
   		</tr>
   		</tbody>
        </table>	
  		</form>
  		<%
  		}
  		%>
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>
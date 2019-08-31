<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%
/*
* viewKurikulumStdTamplete (based on)
*/
//System.out.println("nu versi");
String no_edit=(String)session.getAttribute("no_edit");
String info=(String)session.getAttribute("info");
String target_thsms_add_kelas=(String)session.getAttribute("target_thsms_add_kelas");
//System.out.println("nu no_edit="+no_edit);
//System.out.println("nu info="+info);
//System.out.println("nu target_thsms_add_kelas="+target_thsms_add_kelas);

	String callerPage = request.getParameter("callerPage");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector vListMakul = (Vector)request.getAttribute("vListMakul"); 
	if(vListMakul==null) {
		vListMakul = (Vector)session.getAttribute("vListMakul"); 
	}
	request.removeAttribute("vListMakul"); 
	session.setAttribute("vListMakul", vListMakul);
	String infoKur = request.getParameter("infoKur"); 
	if(infoKur.contains("||")) {
		infoKur = infoKur.replace("||", "#&");
	}
	else {
		if(infoKur.contains("##")) {
			infoKur = infoKur.replace("##", "#&");
		}
	}
	String tknShift = request.getParameter("tknShift");
	String kdpst_nmpst = request.getParameter("kdpst_nmpst");
	String kdjen = request.getParameter("kdjen");
	boolean atFormPengajuanBukaKelasTahap1 = true;
	boolean atFormPengajuanBukaKelasTahap2 = false;
	String backward2 = null;
	String kelasTambahan = request.getParameter("kelasTambahan");
	String kodeKampus = request.getParameter("kodeKampus");
	//String atMenu = request.getParameter("atMenu");
%>

<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
	<script>		
		$(document).ready(function() {
			$("#wait").hide();
			$("#main").show();
		});	
	</script>
	<style>
.table { 
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>; 
//table-layout: fixed;
}
.table thead > tr > th { 
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
word-wrap:break-word;
}
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9;word-wrap:break-word; }
.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; word-wrap:break-word;}
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; word-wrap:break-word;}
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px ;word-wrap:break-word;}
/*
.table tr:hover td { background:#82B0C3;word-wrap:break-word; }
.table thead:hover td { background:#82B0C3;word-wrap:break-word; } <thead> yg jd anchor
*/
</style>
	
</head>
<body>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai<br>[Lama proses bisa sampai 5 menit]</p>
</div>
<div id="main">
<div id="header">
	<ul>
	<!-- get.statusPengajuanKelasKuliah?atMenu=form&scope_cmd=viewAbsen&backTo=get.notifications' -->
	
		<li><a href="#" onclick="location.href='go.getListKurikulum?kdpst_nmpst=<%=kdpst_nmpst %>&kelasTambahan=yes&scope=reqBukaKelas&atMenu=bukakelas&cmd=bukakelas'" target="_self">BACK<span>&nbsp</b></span></a></li>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br>
		<!-- Column 1 start -->
		<center>
		<%
		
		if(infoKur!=null) {
			StringTokenizer st = new StringTokenizer(infoKur,"#&");
			String idkur = st.nextToken();
			String nmkur = st.nextToken();
			String skstt = st.nextToken();
			String smstt = st.nextToken();
			String start = st.nextToken();
			String ended = st.nextToken();
			String targt = st.nextToken();
		%>
		<table class="table" align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:80%">
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center" colspan="2"><label><B>INFO KURIKULUM</B> </label></td>
	        	</tr>	
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>NAMA / KODE KURIKULUM</B> </label></td>
	        		<td style="color:#000;text-align:left"><label><B><%=nmkur %></B> </label></td>
	        	</tr>
	        	<tr>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>TOTAL SKS / SMS KURIKULUM</B> </label></td>
	        		<td style="color:#000;text-align:left"><label><B><%=skstt %> sks / <%=smstt %> sms</B> </label></td>
	        	</tr>
	        	<tr>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>MULAI BERLAKU / BERAKHIR</B> </label></td>
	        		<td style="color:#000;text-align:left"><label><B><%=Converter.convertThsmsKeterOnly(start) %> / <%=Converter.convertThsmsKeterOnly(ended) %></B> </label></td>
	        	</tr>
	        	<!--  tr>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>BERLAKU UTK ANGKATAN</B> </label></td>
	        		<td style="color:#000;text-align:left"><label><B>
	        		<%
	        		st = new StringTokenizer(targt,",");
	        		if(st.countTokens()>1) {
	        			int i=0;
	        			while(st.hasMoreTokens()) {
	        				i++;
	        				if(i<6) {
	        					out.print(st.nextToken()+", ");
	        				}
	        				else {
	        					i=0;
	        					out.print(st.nextToken()+"<br/>");
	        				}
	        			}
	        		}
	        		%></B> </label></td>
	        	</tr-->
	    </table>
	    <br/>
	    <br/>  
	    
	    
		<%	
		}
		if(vListMakul!=null && vListMakul.size()>0) {
			ListIterator li = vListMakul.listIterator();
			int sksem=0;
			if(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"#&");
				String prev_semes=st.nextToken();
				String prev_kdpst=st.nextToken();
				String prev_kdkmk=st.nextToken();
				String prev_nakmk=st.nextToken();
				String prev_skstm=st.nextToken();
				String prev_skspr=st.nextToken();
				String prev_skslp=st.nextToken();
				String prev_skstt=st.nextToken();
				String prev_idkmk=st.nextToken();
				//prev_sms = ""+prev_semes;
				sksem = sksem+(Integer.valueOf(prev_skstt).intValue());
			%>
			<form action="<%=Constants.getRootWeb() %>/InnerFrame/Akademik/formPengajuanBukaKelasTahap2.jsp" method="post">
			<input type="hidden" name="kelasTambahan" value="<%=kelasTambahan %>" />  
			<input type="hidden" name="kodeKampus" value="<%=kodeKampus %>" />  
			<input type="hidden" name="tknShift" value="<%=tknShift %>" />
			<input type="hidden" name="infoKur" value="<%=infoKur %>" />
			<input type="hidden" name="kdjen" value="<%=kdjen %>" />
			<input type="hidden" name="kdpst_nmpst" value="<%=kdpst_nmpst %>" />
	    	<input type="hidden" name="cmd" value="bukaKelas" />
	    	<input type="hidden" name="atMenu" value="bukaKelas" />
	    	<input type="hidden" name="callerPage" value="<%=callerPage %>" />
			<table class="table" align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:80%">
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center" colspan="4"><label><B>SEMESTER <%=prev_semes %></B> </label></td>
	        	</tr>	
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center;width:13%"><label><B>KODE MK</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center;width:50%"><label><B>MATAKULIAH</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center;width:7%"><label><B>SKS</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center;width:30%"><label><B>JUMLAH KELAS PARALEL / SHIFT</B> </label></td>
	        		<!--  td style="background:#369;color:#fff;text-align:center;width:75px"><label><B>TOT KELAS</B> </label></td -->
	        	</tr>
	        	<tr>
	        		<td style="color:#000;text-align:center"><label><B><%=prev_kdkmk %></B> </label></td>
	        		<td style="color:#000;text-align:left"><label><B><%=prev_nakmk.toUpperCase() %></B> </label></td>
	        		<td style="color:#000;text-align:center"><label><B><%=prev_skstt %></B> </label></td>
	        		<td style="color:#000;text-align:left" colspan="2"><B>
	        		<%
	        		st = new StringTokenizer(tknShift,"#");
	        		String uniqKeter = null;
        			String shift = null;
        			String hari = null;
        			String tknKdjen = null;
        			String keterTampil = null;
	        		while(st.hasMoreTokens()) {
	        			uniqKeter = st.nextToken();
	        			shift = st.nextToken();
	        			hari = st.nextToken();
	        			tknKdjen = st.nextToken();
	        			keterTampil = st.nextToken();
	        			if(!uniqKeter.equalsIgnoreCase("N/A")) {	
	        		%>
	        			<input type="text" name="totKls" value="0" style="text-align:center;width:20px;"/>
	        			<input type="hidden" name="klsInfo" value="<%=prev_kdkmk+"||"+prev_nakmk.toUpperCase()+"||"+uniqKeter+"||"+prev_idkmk%>" style="text-align:center;" read only/>
	        			<%= keterTampil%><br>
	        		<%	
	        			}
	        		}
	        		%>	
	        		</B></td>
	        	</tr>	
			<%
				if(!li.hasNext()) {
				//cuma satu record
				//hitung total sks & tutup table
			%>
				<tr>
		      		<td style="background:#369;color:#fff;text-align:left" colspan="2"><label><B>TOTAL SKS</B> </label></td>
			   		<td style="background:#369;color:#fff;text-align:center"><label><B><%=sksem %></B> </label></td>
			   		<td style="background:#369;color:#fff;text-align:center" colspan="1"><label><B></B> </label></td>
		      	</tr>
		    </table>
		    <%
				}
				else {
					while(li.hasNext()) {
						brs = (String)li.next();
						st = new StringTokenizer(brs,"#&");
						String semes=st.nextToken();
						String kdpst=st.nextToken();
						String kdkmk=st.nextToken();
						String nakmk=st.nextToken();
						String skstm=st.nextToken();
						String skspr=st.nextToken();
						String skslp=st.nextToken();
						String skstt=st.nextToken();
						String idkmk=st.nextToken();
						if(prev_semes.equals(semes)) {
						//	prev_semes=""semes;
							sksem = sksem+(Integer.valueOf(skstt).intValue());
						%>
				<tr>
	        		<td style="color:#000;text-align:center"><label><B><%=kdkmk %></B> </label></td>
	        		<td style="color:#000;text-align:left"><label><B><%=nakmk.toUpperCase() %></B> </label></td>
	        		<td style="color:#000;text-align:center"><label><B><%=skstt %></B> </label></td>
	        		<td style="color:#000;text-align:left"  colspan="1"><B>
	        		<%
	        		st = new StringTokenizer(tknShift,"#");
	        		uniqKeter = null;
        			shift = null;
        			hari = null;
        			tknKdjen = null;
        			keterTampil = null;
	        		while(st.hasMoreTokens()) {
	        			uniqKeter = st.nextToken();
	        			shift = st.nextToken();
	        			hari = st.nextToken();
	        			tknKdjen = st.nextToken();
	        			keterTampil = st.nextToken();
	        			if(!uniqKeter.equalsIgnoreCase("N/A")) {
	        		%>
	        			<input type="text" name="totKls" value="0" style="text-align:center;width:20px;"/>
	        			<input type="hidden" name="klsInfo" value="<%=kdkmk+"||"+nakmk.toUpperCase()+"||"+uniqKeter+"||"+idkmk%>" style="text-align:center;" read only/>
	        			<%= keterTampil%><br>
	        		<%	
	        			
	        			}
	        		}
	        		%>	
	        		</B> </td>
	        	</tr>
						<%
						}
						else {		
							//pergantian thsms
							//tutup table & create new Table
						%>
				<tr>
		      		<td style="background:#369;color:#fff;text-align:left" colspan="2"><label><B>TOTAL SKS</B> </label></td>
			   		<td style="background:#369;color:#fff;text-align:center"><label><B><%=sksem %></B> </label></td>
			   		<td style="background:#369;color:#fff;text-align:center" colspan="1"><label><B></B> </label></td>
		      	</tr>
		    </table>
		    <br/>
		    <br/>
		    <%
		    prev_semes = ""+semes;
		    sksem = (Integer.valueOf(skstt).intValue());
		    %>
		  	<table class="table" align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:80%">	
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center" colspan="5"><label><B>SEMESTER <%=prev_semes %></B> </label></td>
	        	</tr>	
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center;width:13%"><label><B>KODE MK</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center;width:50%"><label><B>MATAKULIAH</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center;width:7%"><label><B>SKS</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center;width:30%"><label><B>JUMLAH KELAS PARALEL / SHIFT</B> </label></td>    	
	        	</tr>
	        	<tr>
	        		<td style="color:#000;text-align:center"><label><B><%=kdkmk %></B> </label></td>
	        		<td style="color:#000;text-align:left"><label><B><%=nakmk.toUpperCase() %></B> </label></td>
	        		<td style="color:#000;text-align:center"><label><B><%=skstt %></B> </label></td>
	        		<td style="color:#000;text-align:left"  colspan="1"><B>
	        		<%
	        		st = new StringTokenizer(tknShift,"#");
	        		uniqKeter = null;
        			shift = null;
        			hari = null;
        			tknKdjen = null;
        			keterTampil = null;
	        		while(st.hasMoreTokens()) {
	        			uniqKeter = st.nextToken();
	        			shift = st.nextToken();
	        			hari = st.nextToken();
	        			tknKdjen = st.nextToken();
	        			keterTampil = st.nextToken();
	        			if(!uniqKeter.equalsIgnoreCase("N/A")) {
	        		%>
	        			<input type="text" name="totKls" value="0" style="text-align:center;width:20px;"/>
	        			<input type="hidden" name="klsInfo" value="<%=kdkmk+"||"+nakmk.toUpperCase()+"||"+uniqKeter+"||"+idkmk%>" style="text-align:center;" read only/>
	        			<%= keterTampil%><br>
	        		<%	
	        			}
	        		}
	        		%>	
	        		</B></td>
	        	</tr>
							<%
						}
						if(!li.hasNext()) {
				%>
				<tr>
		     		<td style="background:#369;color:#fff;text-align:left" colspan="2"><label><B>TOTAL SKS</B> </label></td>
			   		<td style="background:#369;color:#fff;text-align:center"><label><B><%=sksem %></B> </label></td>
			   		<td style="background:#369;color:#fff;text-align:center" colspan="1"><label><B></B> </label></td>
		      	</tr>
		    </table>
					    <%
						}
					}	
				}	
			}
		%>
		</table>
		<br/>
		<section class="gradient">
			<div style="text-align:center">

			<!--  input type="submit" value="Next" style="width:500px;height:35px"/ -->
				<button style="padding: 5px 50px;font-size: 20px;"  onclick="(function(){
            		//scroll(0,0);
					parent.scrollTo(0,0);
           			var x = document.getElementById('wait');
           			var y = document.getElementById('main');
           			x.style.display = 'block';
            		y.style.display = 'none';
             		document.getElementsByTagName('form').submit()})()">Next
				</button>
			</div>
		</section>	
		</form>
		<%	
		}
		else {
			if(vListMakul==null) {
			%>
			<h2>BELUM MEMILIKI KURIKULUM</h2>
			<%
			}
		}
		%>
		</center>
		<!-- Column 1 start -->
	</div>
</div>
</div>			
</body>
</html>
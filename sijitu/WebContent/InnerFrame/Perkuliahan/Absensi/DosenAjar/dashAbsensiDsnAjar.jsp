<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.dbase.classPoll.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.sistem.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>

<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/metro.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />

<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />

<%
String target = null;
String uri = null;
String url = null;
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
//System.out.println("gogo");;
Vector v= null; 
String listTipeUjian = (String)session.getAttribute("listTipeUjian");
String targetThsms  = request.getParameter("target_thsms");
if(targetThsms==null || Checker.isStringNullOrEmpty(targetThsms)) {
	targetThsms = Checker.getThsmsNow();
}
Vector vListKelasPerProdi = (Vector) session.getAttribute("vListKelasPerProdi");
session.removeAttribute("vListKelasPerProdi");
String tknScopeKampus = request.getParameter("tknScopeKampus");
Vector vInfoKehadiranDosen = (Vector) session.getAttribute("vInfoKehadiranDosen");

String backto = request.getParameter("backto");
String cmd = request.getParameter("cmd");
//request.removeAttribute("listTipeUjian");
//String atMenu = request.getParameter("atMenu");
String target_kdpst = (String)session.getAttribute("target_kdpst");
String target_shift = (String)session.getAttribute("target_shift");
String target_thsms = (String)session.getAttribute("target_thsms");
%>
<script type="text/javascript">
</script>
<style>
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


</style>
	<script>
		
		$(document).ready(function() {
			$("#wait").hide();
			$("#main").show();
		});	
	</script>
	<script>	
	   	$(document).on("click", "#gohome", function() {
        	$.ajax({
        		url: '',
        		type: 'POST',
        		data: {
        	        name: $('#name').val()
        	    },
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	window.location.href = "<%=Constants.getRootWeb() %>/index.jsp";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });	
	</script>	
</head>
<body>
<div id="header">
<ul>
<%
if(backto!=null && backto.equalsIgnoreCase("home")) {
/*
CUMA BISA DIPAKE SEKALI AJA, DARI HOME
*/
%>
	<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
<%	
//reset backto = null;
	backto=null;
}
else if(backto!=null && backto.equalsIgnoreCase("index")) {
	target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/index_perkuliahan.jsp";
	uri = request.getRequestURI();
	//System.out.println(target+" / "+uri);
	url = PathFinder.getPath(uri, target);
%>
	<li><a href="<%=url %>" target="_self">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
<%	
}
else if(backto==null |Checker.isStringNullOrEmpty(backto)) {
%>
	<li><a href="#" id="gohome" target="_self" >BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
<%	
}
else {
%>
<%@ include file="../../innerMenu.jsp" %>
<%
}
%>
</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
	<div align="center">
	<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
		<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai<br>[Lama proses bisa sampai 5 menit]</p>
	</div>
	<div id="main">
		<!-- Column 1 start -->
		
	

				<%
/*
if(vKlsAjar!=null && vKlsAjar.size()>0) {
	
}
					*/
					
					 
if(vInfoKehadiranDosen!=null && vInfoKehadiranDosen.size()>0) {
	//System.out.println("vInfoKehadiranDosen="+vInfoKehadiranDosen.size());
	int no = 1;
%>
	<div style="font-weight:bold;font-size:0.8em;text-align:left;padding:0 0 0 10px">
		<p>
		1. Centang 'Batal', bila anda ingin membatalkan pertemuan untuk tanggal terkait.<br>
		2. Bila anda salah menginput tanggal / merubah tanggal, silahkan update dengan mengisi nilai'nol atau kosongkan kolom 'Perkiraan Waktu Keterlambatan' pertemuan pada tanggal terkait terlebih dahulu.
		</p>	 
	</div>
		<form action="update.statusKehadiranDosen" method="post" >
		<input type="hidden" name="cmd" value="<%=cmd %>"/>
		<input type="hidden" name="backto" value="<%=backto %>"/>
		<table class="table" width="95%">
		<tr> 
        	<td style="background:#369;color:#fff;text-align:center;font-size:1.7em" colspan="8"><label><B>FORM STATUS KEHADIRAN DOSEN AJAR <%=targetThsms %></B> </label></td> 
        </tr> 
        <tr> 
        	<td style="background:#369;color:#fff;text-align:center;width:5%;padding:0 2px">NO</td>
        	<td style="background:#369;color:#fff;text-align:center;width:10%;padding:0 2px">PRODI</td>
        	<td style="background:#369;color:#fff;text-align:left;width:30%;padding:0 2px">KODE & NAMA MATAKULIAH</td>
        	<td style="background:#369;color:#fff;text-align:center;width:15%;padding:0 2px">TANGGAL <br/>&nbsp</td>
        	<td style="background:#369;color:#fff;text-align:center;width:15%;padding:0 2px">JAM <br/>/ SHIFT</td>
        	<td style="background:#369;color:#fff;text-align:center;width:5%;padding:0 2px">NO <br/>KLS</td>
        	<td style="background:#369;color:#fff;text-align:center;width:15%;padding:0 2px">PERKIRAAN WAKTU <br/>KETERLAMBATAN<br/> (Menit)</td>
        	<td style="background:#369;color:#fff;text-align:center;width:5%;padding:0 2px">BATAL</td> 
        </tr>
<%
	ListIterator lit = vInfoKehadiranDosen.listIterator();
	int norut = 0;
	while(lit.hasNext()) {
		String brs = (String)lit.next();
		String brs2 = (String)lit.next(); //li.add(cuid+"`"+tgl+"`"+batal+"`"+delay_time+"`"+real_start+"`"+replace_date+"`"+usrNpm+"`"+superNpm+"`"+thsms);
		//System.out.println("brs22="+brs2);
		StringTokenizer st = new StringTokenizer(brs,"`");
		String cuid2 = null;
		String tgl2  = null;
		String batal2 = null;
		String delay_time2 = null;
		String real_start2 = null;
		String replace_date2 = null;
		String usrNpm2 = null;
		String superNpm2 = null;
		String thsms2 = null;
		if(brs2!=null && !Checker.isStringNullOrEmpty(brs2)) {
			StringTokenizer stt = new StringTokenizer(brs2,"`");
			cuid2 = stt.nextToken();
			tgl2  = stt.nextToken();
			batal2 = stt.nextToken();
			delay_time2 = stt.nextToken();
			real_start2 = stt.nextToken();
			replace_date2 = stt.nextToken();
			usrNpm2 = stt.nextToken();
			superNpm2 = stt.nextToken();
			thsms2 = stt.nextToken();
		}
		String kdpst = st.nextToken();
		String nakmk = st.nextToken();
		String idkur = st.nextToken();
		String idkmk = st.nextToken();
		String shift = st.nextToken();
		String thsms = st.nextToken();
		
		String nopll = st.nextToken();
		String initNpmInp = st.nextToken();
		String lasNpmUpd = st.nextToken();
		String lasStatInf = st.nextToken();
		String curAvailStat = st.nextToken();
		String locked = st.nextToken();
		String npmdos = st.nextToken();
		String nodos = st.nextToken();
		String npmasdos = st.nextToken();
		String noasdos = st.nextToken();
		String batal = st.nextToken();
		String kodeKls = st.nextToken();
		String kodeRuang = st.nextToken();
		String kodeGdg = st.nextToken();
		String kodeKmp = st.nextToken();
		String tknDayTime = st.nextToken();
		String nmmDos = st.nextToken();
		String nmmAsdos = st.nextToken();
		String totEnrol = st.nextToken();
		String maxEnrol = st.nextToken();
		String minEnrol = st.nextToken();
		String subKeterKdkmk = st.nextToken();
		String initReqTime = st.nextToken();
		String tknNpmApr = st.nextToken();
		String tknAprTime = st.nextToken();
		String targetTtmhs = st.nextToken();
		String passed = st.nextToken();
		String rejected = st.nextToken();
		String kodeGabung = st.nextToken();
		String kodeGabungUniv = st.nextToken();
		String cuid = st.nextToken();
		String kdkmk = st.nextToken();
		String konsen = Checker.getKonsentrasiKurikulum(idkur);
		//String konsen = st.nextToken();
		
%>
		<tr> 
        	<td style="vertical-align:middle;text-align:center;height:100%;padding:0 2px">
        		<%=no++ %>
        		<input type="hidden" name="cuid" value="<%=cuid %>"/>
        	</td>
        	<td style="vertical-align:middle;text-align:center;height:100%;padding:0 2px"><%=Converter.getNamaKdpst(kdpst) %>
        	<%
        	if(konsen!=null) {
        		out.print("<br/>konsentrasi<br/>"+konsen);
        	}
        	%>
        	</td>
        	<td style="vertical-align:middle;text-align:left;height:100%;padding:0 2px">(<%=kdkmk %>) - <%=nakmk %><%="<br/>DOSEN: "+nmmDos %></td>
        	
        	<%
        	if(brs2==null || Checker.isStringNullOrEmpty(brs2)) {
        	%>
        	<td style="vertical-align:middle;text-align:center;height:100%;background-color:white">
        		<input type="text" style="font-size:1.3em;border:none;width:100%;display:inline-block;position:relative;text-align:center" name="tgl_ttm" placeholder="tgl/bln/tahun"/>
        	<%	
        	}
        	else {
        	%>
        	<td style="vertical-align:middle;text-align:center;height:100%;">
        		<input type="text" style="font-size:1.3em;border:none;width:100%;display:inline-block;position:relative;text-align:center;background:none" name="tgl_ttm" placeholder="tgl/bln/tahun" value="<%= Converter.reformatSqlDateToTglBlnThn(tgl2) %>" readonly="readonly"/>
        	<%
        	}
        	%></td>
        	<td style="vertical-align:middle;text-align:center;height:100%;padding:0 2px"><%=shift %></td>
        	<td style="vertical-align:middle;text-align:center;height:100%;padding:0 2px"><%=nopll %></td>
        	<td style="vertical-align:middle;text-align:center;height:100%;padding:0;background-color:white"">
			<%
        	if(brs2==null || Checker.isStringNullOrEmpty(brs2)) {
        	%>
        		<input type="text" style="font-size:1.3em;border:none;width:100%;height:100%;text-align:center" name="delay_tm" />
        	<%	
        	}
        	else {
        	%>
        		<input type="text" style="font-size:1.3em;border:none;width:100%;height:100%;text-align:center" name="delay_tm" value="<%=delay_time2%>"/>
        	<%
        	}
        	%>
			</td>
        	<td style="vertical-align:middle;text-align:center;height:100%;padding:0 2px">
        	<%
        	if(brs2==null || Checker.isStringNullOrEmpty(brs2)) {
        	%>
        		<input type="checkbox" style="width:25px;height:25px" name="batal" value="<%=norut++%>">
        	<%	
        	}
        	else {
        		if(batal2!=null && batal2.equalsIgnoreCase("true")) {
        			%>
            	<input type="checkbox" style="width:25px;height:25px" name="batal" value="<%=norut++%>" checked="checked">
            	<%		
        		}
        		else {
        			%>
            	<input type="checkbox" style="width:25px;height:25px" name="batal" value="<%=norut++%>">
            	<%
        		}
        	}
        	%>
        	</td> 
        </tr>
<%		
	}
%>   
		<tr>
			<td style="vertical-align:middle;background:#369;color:#fff;text-align:center;padding:0 5px" colspan="8"><input type="submit" value="UPDATE DATA" style="width:50%;height:45px"/></td>
		</tr>     
        </table>
        </form>
<%
}
else {
	out.print("<h2 align=\"center\"> INFORMASI BELUM TERSEDIA </h2>");
}
%>         					
		<!-- Column 1 end -->
		</div>
		</div>
	</div>
</div>

</body>
</html>
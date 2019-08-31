<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />

<script type="text/javascript">
$(document).ready(function()
{
	$("#somebutton").click(function()	
	{
		//$.get('go.uploadFile2', function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
            //             // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
        $.post('process.validasiFormBukaKelasTahap2', $('#formUpload1').serialize(), function(data) {
        	parent.scrollTo(0,0);
   			var x = document.getElementById('wait');
   			var y = document.getElementById('main');
   			x.style.display = 'block';
    		y.style.display = 'none';
     		//document.getElementsByTagName('form').submit();
        	document.getElementById('div_msg').style.visibility='visible';
        	$('#div_msg').html(); 
        	$('#div_msg').html(data); 
        });
	});

});			
</script>

<%
/*
* viewKurikulumStdTamplete (based on)
*/
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
 	//System.out.println("tahap 2");
	Vector v_list_all_dosen = Getter.getListDosen_v1(false); //false==termasuk yg blum ada nidn
	String err_msg = request.getParameter("errmsg");
	if(err_msg==null || Checker.isStringNullOrEmpty(err_msg)) {
		err_msg="";
	}
	String[] klsInfo = request.getParameterValues("klsInfo"); 
	String[] totKls = request.getParameterValues("totKls");
	if(klsInfo==null) {
		klsInfo = (String[])session.getAttribute("klsInfo");
		totKls = (String[])session.getAttribute("totKls");
	}
	else {
		session.setAttribute("klsInfo",klsInfo);
		session.setAttribute("totKls",totKls);
	}	
	String infoKur = request.getParameter("infoKur");
	String kdjen = request.getParameter("kdjen");
	String kdpst_nmpst = request.getParameter("kdpst_nmpst");
	String kelasTambahan = request.getParameter("kelasTambahan");
	//System.out.println("kelasTambahan="+kelasTambahan);
	String kodeKampus = request.getParameter("kodeKampus");
	String kdpst = null;
	String nmpst = null;
	if(kdpst_nmpst!=null) {
		StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
		kdpst= st.nextToken();
		nmpst= st.nextToken();
	}
	//Vector vDos = Checker.getListDosenPengajar(null);
	//ListIterator liDos = vDos.listIterator();
	//System.out.println("totKls="+totKls.length);
	boolean atFormPengajuanBukaKelasTahap1 = false;
	boolean atFormPengajuanBukaKelasTahap2 = true;
	String backward2 ="go.prepFormRequestBukaKelas?cmd=bukakelas&atMenu=bukakelas&scope=reqBukaKelas&kdpst_nmpst="+kdpst_nmpst+"&infoKur="+infoKur;
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
<%@ include file="IndexAkademikPengajuanSubMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
	
		<br>
		<center>
		<!-- Column 1 start -->
		<%
String kdkmk = null,nakmk=null,shift=null;
if(totKls!=null && totKls.length>0) {
		//select EXT_CIVITAS_DATA_DOSEN.KDPST,EXT_CIVITAS_DATA_DOSEN.NPMHS,EXT_CIVITAS_DATA_DOSEN.NODOS_LOCAL,EXT_CIVITAS_DATA_DOSEN.GELAR_DEPAN,EXT_CIVITAS_DATA_DOSEN.GELAR_BELAKANG,EXT_CIVITAS_DATA_DOSEN.NIDNN,EXT_CIVITAS_DATA_DOSEN.TIPE_ID,EXT_CIVITAS_DATA_DOSEN.NOMOR_ID,EXT_CIVITAS_DATA_DOSEN.STATUS,EXT_CIVITAS_DATA_DOSEN.PT_S1,EXT_CIVITAS_DATA_DOSEN.JURUSAN_S1,EXT_CIVITAS_DATA_DOSEN.KDPST_S1,EXT_CIVITAS_DATA_DOSEN.GELAR_S1,EXT_CIVITAS_DATA_DOSEN.TKN_BIDANG_KEAHLIAN_S1,EXT_CIVITAS_DATA_DOSEN.NOIJA_S1,EXT_CIVITAS_DATA_DOSEN.TGLLS_S1,EXT_CIVITAS_DATA_DOSEN.FILE_IJA_S1,EXT_CIVITAS_DATA_DOSEN.JUDUL_TA_S1,EXT_CIVITAS_DATA_DOSEN.PT_S2,EXT_CIVITAS_DATA_DOSEN.JURUSAN_S2,EXT_CIVITAS_DATA_DOSEN.KDPST_S2,EXT_CIVITAS_DATA_DOSEN.GELAR_S2,EXT_CIVITAS_DATA_DOSEN.TKN_BIDANG_KEAHLIAN_S2,EXT_CIVITAS_DATA_DOSEN.NOIJA_S2,EXT_CIVITAS_DATA_DOSEN.TGLLS_S2,EXT_CIVITAS_DATA_DOSEN.FILE_IJA_S2,EXT_CIVITAS_DATA_DOSEN.JUDUL_TA_S2,EXT_CIVITAS_DATA_DOSEN.PT_S3,EXT_CIVITAS_DATA_DOSEN.JURUSAN_S3,EXT_CIVITAS_DATA_DOSEN.KDPST_S3,EXT_CIVITAS_DATA_DOSEN.GELAR_S3,EXT_CIVITAS_DATA_DOSEN.TKN_BIDANG_KEAHLIAN_S3,EXT_CIVITAS_DATA_DOSEN.NOIJA_S3,EXT_CIVITAS_DATA_DOSEN.TGLLS_S3,EXT_CIVITAS_DATA_DOSEN.FILE_IJA_S3,EXT_CIVITAS_DATA_DOSEN.JUDUL_TA_S3,EXT_CIVITAS_DATA_DOSEN.PT_PROF,EXT_CIVITAS_DATA_DOSEN.JURUSAN_PROF,EXT_CIVITAS_DATA_DOSEN.KDPST_PROF,EXT_CIVITAS_DATA_DOSEN.GELAR_PROF,EXT_CIVITAS_DATA_DOSEN.TKN_BIDANG_KEAHLIAN_PROF,EXT_CIVITAS_DATA_DOSEN.NOIJA_PROF,EXT_CIVITAS_DATA_DOSEN.TGLLS_PROF,EXT_CIVITAS_DATA_DOSEN.FILE_IJA_PROF,EXT_CIVITAS_DATA_DOSEN.JUDUL_TA_PROF,EXT_CIVITAS_DATA_DOSEN.TOTAL_KUM,EXT_CIVITAS_DATA_DOSEN.JABATAN_AKADEMIK_DIKTI,EXT_CIVITAS_DATA_DOSEN.JABATAN_AKADEMIK_LOCAL,EXT_CIVITAS_DATA_DOSEN.JABATAN_STRUKTURAL,EXT_CIVITAS_DATA_DOSEN.IKATAN_KERJA_DOSEN,EXT_CIVITAS_DATA_DOSEN.TANGGAL_MULAI_KERJA,EXT_CIVITAS_DATA_DOSEN.TANGGAL_KELUAR_KERJA,EXT_CIVITAS_DATA_DOSEN.SERDOS,EXT_CIVITAS_DATA_DOSEN.KDPTI_HOMEBASE,EXT_CIVITAS_DATA_DOSEN.KDPST_HOMEBASE,EXT_CIVITAS_DATA_DOSEN.EMAIL_INSTITUSI,EXT_CIVITAS_DATA_DOSEN.PANGKAT_GOLONGAN,EXT_CIVITAS_DATA_DOSEN.CATATAN_RIWAYAT,CIVITAS.NMMHSMSMHS from EXT_CIVITAS_DATA_DOSEN inner join CIVITAS on NPMHSMSMHS=NPMHS where STATUS=? order by NMMHSMSMHS
		//JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/search_dsn/status/A");//get dosen aktif
	int tot_kelas_diajukan=0;
	for(int i=0;i<totKls.length;i++) {
		if(!Checker.isStringNullOrEmpty(totKls[i]) && Integer.valueOf(totKls[i]).intValue()>0) {
			tot_kelas_diajukan++;
		}
	}
	
	//System.out.println("tot_kelas_diajukan="+tot_kelas_diajukan);
	if(tot_kelas_diajukan>0) {	
		%>
	<!--  form action="formPengajuanBukaKelasTahap3.jsp" method="post" -->
	
	<!--  form action="process.validasiFormBukaKelasTahap2" method="post" -->	
	<form name="formUpload1" id="formUpload1" method="post">
	<input type="hidden" name="kelasTambahan" value="<%=kelasTambahan %>" />
	<input type="hidden" name="kodeKampus" value="<%=kodeKampus %>" />  
	<input type="hidden" name="infoKur" value="<%=infoKur %>" />
	<input type="hidden" name="kdpst_nmpst" value="<%=kdpst_nmpst %>" />
	<input type="hidden" name="kdjen" value="<%=kdjen %>" />
	<%
		if(!Checker.isStringNullOrEmpty(err_msg)) {
	%>
	<p style="color:red;font-style:italic;text-align:center"><%=err_msg%></p>
	<%	
		}
	
	%>
	<table class="table" align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:80%">
		
		<tr>
			<td style="background:#369;color:#fff;text-align:center;" colspan="4"><label><B>FORM PENGISIAN DOSEN PENGAJAR</B> </label></td>
		</tr>
		<tr>
	        <td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>KODE MK</B> </label></td>
	        <td style="background:#369;color:#fff;text-align:center;width:265px"><label><B>MATAKULIAH</B> </label></td>
	        <td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>SHIFT</B> </label></td>
	        <td style="background:#369;color:#fff;text-align:center;width:285px"><label><B>NO URUT KELAS / DOSEN</B> </label></td>     
	    </tr>
		<%	
		for(int i=0;i<totKls.length;i++) {
			if(!Checker.isStringNullOrEmpty(totKls[i]) && Integer.valueOf(totKls[i]).intValue()>0) {
				StringTokenizer st = new StringTokenizer(klsInfo[i],"||");
				kdkmk = st.nextToken();
				nakmk = st.nextToken();
				shift = st.nextToken();
				//out.print(klsInfo[i]+" "+totKls[i]+"<br/>");
		%>
		<tr>
	        		<td style="color:#000;text-align:center"><label><B><%=kdkmk %></B> </label></td>
	        		<td style="color:#000;text-align:left"><label><B><%=nakmk %></B> </label></td>
	        		<td style="color:#000;text-align:center"><label><B><%=shift %></B> </label></td>
	        		<td style="color:#000;text-align:left"><B>
	        		<%
	        	for(int j=1;j<=Integer.valueOf(totKls[i]).intValue();j++) {
	        			//String tmp = nodos+"||"+nidn+"||"+noKtp+"||"+kdptiHome+"||"+kdpstHome+"||"+nmdos+"||"+gelar+"||"+smawl+"||"+kdpstAjar+"||"+email+"||"+tknTelp+"||"+tknHp+"||"+status;
	        			
	        		%>
	        			&nbspPilih Dosen Pengajar :&nbsp
	        			
	        			<select name="infoKelasDosen" style="width:100%;border:none;text-align-last:center;height:25px"/>
	        				<!--  option value="<%= klsInfo[i]%>||<%=j %>||tba||tba">Belum Ditentukan</option -->
	        		<%	
	        		//digandi dengan json
	        		/*
	        			if(vDos!=null) {
	        				ListIterator li = vDos.listIterator();
	        				while(li.hasNext()) {
	        					String brs = (String)li.next();
	        					st = new StringTokenizer(brs,"||");
	        					String nodos = st.nextToken();
	        					String nidn = st.nextToken();
	        					String noKtp = st.nextToken();
	        					String kdptiHome = st.nextToken();
	        					String kdpstHome = st.nextToken();
	        					String nmdos = st.nextToken();
	        					String gelar = st.nextToken();
	        					String smawl = st.nextToken();
	        					String kdpstAjar = st.nextToken();
	        					String email = st.nextToken();
	        					String tknTelp = st.nextToken();
	        					String tknHp = st.nextToken();
	        					String status= st.nextToken();
	        			*/
	        		if(v_list_all_dosen!=null && v_list_all_dosen.size()>0) {
	        			ListIterator lidos = v_list_all_dosen.listIterator();
	        				//li.add(nmdos+"`"+nodos+"`"+nomor+"`"+npmdos+"`"+nidk+"`"+nup);
	        			while(lidos.hasNext()) {
	        				String brs = (String)lidos.next();
	        				StringTokenizer std = new StringTokenizer(brs,"`");
	        				//nmdos+"`"+nodos+"`"+nomor+"`"+npmdos+"`"+nidk+"`"+nup
	        				String nmdos = std.nextToken();
	        				String nodos = std.nextToken();
	        				String nomor = std.nextToken();
	        				String npmdos = std.nextToken();
	        				String nidk = std.nextToken();
	        				String nup = std.nextToken();
	        				String info_dos = new String(nmdos);
	        				if(!Checker.isStringNullOrEmpty(nodos)) {
	        					info_dos=info_dos+" ["+nodos+"]";
	        				}
	        				else if(!Checker.isStringNullOrEmpty(nidk)) {
	        					info_dos=info_dos+" ["+nidk+"]";
	        				}
	        				else if(!Checker.isStringNullOrEmpty(nup)) {
	        					info_dos=info_dos+" ["+nup+"]";
	        				}
	        					//if(jsoa!=null && jsoa.length()>0) {
		        			//for(int k=0;k<jsoa.length();k++) {
		        				//JSONObject job = jsoa.getJSONObject(k);	
		        				//String nodos = job.getString("NPMHS");
		        				//String nmdos = job.getString("NMMHSMSMHS");
	        					%>
	        					<option value="<%= klsInfo[i]%>||<%=j %>||<%=npmdos%>||<%=nmdos%>"><%=info_dos %></option>
	        					<%
	        			}
	        		}
	        		%>
	        			</select>
	        			<br/>
	        			&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Jumalah Target Mhs:&nbsp&nbsp<input type="number" name="infoKelasMhs" value="1" style="text-align:center;width:39px"/>
	        			<br/>
	        		<%
	        	}
	        		%>
	        		</B>
	        		
	        		
	    </tr>	
		<%		
			}
		}
	%>
	</table>
	
		<!--  div style="text-align:center"><input type="button" id="somebutton" name="somebutton" value="Next" style="width:500px;height:35px;font-weght:bold;margin:5px"/></div-->
	<br>
	<section class="gradient">
			<div style="text-align:center">

			<!--  input type="submit" value="Next" style="width:500px;height:35px"/ -->
				<button id="somebutton" name="somebutton" style="padding: 5px 50px;font-size: 20px;"  onclick="(function(){
            		//scroll(0,0);
					parent.scrollTo(0,0);
           			var x = document.getElementById('wait');
           			var y = document.getElementById('main');
           			x.style.display = 'block';
            		y.style.display = 'none';
             		document.getElementsByTagName('form').submit()})()"> AJUKAN KELAS & DOSEN PENGAJAR
				</button>
			</div>
		</section>	
	</form>
	<%	
	}
	else {
		if(kelasTambahan==null || !kelasTambahan.equalsIgnoreCase("yes")) {
%>
	<h1 style="text-align:Center;color:orange"> Anda Belum Menuliskan Jumlah Kelas Yg Mau Diajukan Pada Form Sebelumnya (minimal 1)</h1>
<%				
		}
		else {
			%>
	<h1 style="text-align:Center;color:orange"> Anda Belum Menuliskan Jumlah Kelas Yg Mau Ditambahkan Pada Form Sebelumnya (minimal 1)</h1>
		<%			
		}
	}
}	        		
		%>
		<br/>
		<div id="div_msg" style="font-style:italic;color:red;text-align:left;background-color:#d9e1e5;width:750px;height:100%;visibility:hidden;margin:0px 0 0 132px;" ></div>

		    </center>
		<!-- Column 1 start -->
	</div>
</div>	
</div>			
</body>
</html>
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
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />

<%

beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
/*
SearchDbClassPoll scp = new SearchDbClassPoll(validUsr.getNpm());
Vector list_kdpst_ink = validUsr.getScopeUpd7des2012ProdiOnlyButKeepOwn("ink");
String group_proses = request.getParameter("group_proses");
Vector vKlsPenilaian = scp.getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya(list_kdpst_ink, Checker.getThsmsInputNilai());
boolean bolehEditNilai = false;
Vector vKlsAjar = scp.getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya(list_kdpst_sad, Checker.getThsmsNow());

//if(scopeHakAkses.contains("`e`")||scopeHakAkses.contains("`i`")) {
if(vKlsAjar!=null) {	
	bolehEdit = true;list_kdpst_sad
}
if(vKlsPenilaian!=null && !validUsr.isHakAksesReadOnly("ink")) {	
	bolehEditNilai = true;
}
*/
//String scopeHakAkses= request.getParameter("scopeHakAkses");
//String scopeKmp=request.getParameter("scopeKmp");
//Vector vListKelas = (Vector) request.getAttribute("vListKelas");
Vector vKlsPenilaian = (Vector)session.getAttribute("v_tmp");
session.removeAttribute("v_tmp");
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
.table tr:hover td { background:#82B0C3 }
.table thead:hover td { background:#82B0C3 } 
</style>
</head>
<body>
<div id="header">
<ul>
	<%
	String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/index_perkuliahan.jsp";
	String uri = request.getRequestURI();
	String url = PathFinder.getPath(uri, target);
			%>
	<li><a href="<%=url%>" target="inner_iframe">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		
	
		<%
		String thsms_inp_nilai = Checker.getThsmsInputNilai();
		if(vKlsPenilaian!=null && vKlsPenilaian.size()>0) {
			
			//System.out.println("thsms_inp_nilai="+thsms_inp_nilai);
			int j=0;
		%>
		<div align="center">		
		<table class="table" style="width:95%">
			<tr>
	    		<td style="background:#369;color:#fff;text-align:center;font-size:1.7em" colspan="8"><label><B>LIST PERKULIAHAN <%=Converter.convertThsmsKeterOnly(thsms_inp_nilai) %></B> </label></td>
	    	</tr>
	    	<tr>
    		    <td style="background:#369;color:#fff;text-align:center;width:5%"><label><B>NO.</B> </label></td>
        		<td style="background:#369;color:#fff;text-align:left;width:35%"><label><B>MATAKULIAH</B> </label></td>
        		<td style="background:#369;color:#fff;text-align:center;width:15%"><label><B>PRODI</B> </label></td>
        		<td style="background:#369;color:#fff;text-align:center;width:10%"><label><B>INFO KELAS</B> </label></td>
        		<td style="background:#369;color:#fff;text-align:center;width:35%"><label><B>NAMA DOSEN</B> </label></td>
    		</tr>
		<%	
			ListIterator li = vKlsPenilaian.listIterator();
			while(li.hasNext()) {
				String brs = (String) li.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				//kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid+"`"+kdkmk);
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
				/*
				String idkur = st.nextToken();
				String idkmk = st.nextToken();
				String thsms_inp_nilai = st.nextToken();
				String kdpst = st.nextToken();
				String shift = st.nextToken();
				String noKlsPll = st.nextToken();
				String npm_pertama_input = st.nextToken();
				String npm_terakhir_updat = st.nextToken();
				String status_akhir = st.nextToken();
				String curr_avail_status = st.nextToken();
				String locked_or_editable = st.nextToken();
				String npmdos = st.nextToken();
				String nodos = st.nextToken();
				String npmasdos = st.nextToken();
				String noasdos = st.nextToken();
				String canceled = st.nextToken();
				String kode_kelas = st.nextToken();
				String kode_ruang = st.nextToken();
				String kode_gedung = st.nextToken();
				String kode_kampus = st.nextToken();
				String tkn_day_time = st.nextToken();
				String nmmdos = st.nextToken();
				String nmmasdos = st.nextToken();
				String enrolled = st.nextToken();
				String max_enrolled = st.nextToken();
				String min_enrolled = st.nextToken();
				String sub_keter_kdkmk = st.nextToken();
				String init_req_time = st.nextToken();
				String tkn_npm_approval = st.nextToken();
				String tkn_approval_time = st.nextToken();
				String target_ttmhs = st.nextToken();
				String passed = st.nextToken();
				String rejected = st.nextToken();
				String kode_gabung_kls = st.nextToken();
				String kode_gabung_kmp = st.nextToken();
				String unique_id = st.nextToken();
				String kdkmk = st.nextToken();
				String nakmk = st.nextToken();
				String skstm = st.nextToken();
				String skspr = st.nextToken();
				String skslp = st.nextToken();
				*/
		%>
			<tr onclick="window.open('get.listMhsUtkPenilaian?group_proses=<%="" %>&thsms=<%=thsms_inp_nilai %>&idkmk=<%=idkmk %>&uniqueId=<%=cuid %>&kdkmk=<%=kdkmk %>&nakmk=<%=nakmk %>&shift=<%=shift %>&noKlsPll=<%=nopll %>&kode_kelas=<%=kodeKls %>&kode_gedung=<%=kodeGdg %>&kode_kampus=<%=kodeKmp %>&kode_gabung_kls=<%=kodeGabung %>&kode_gabung_kmp=<%=kodeGabungUniv %>&skstm=<%="0" %>&skspr=<%="0" %>&skslp=<%="0" %>&bolehEdit=true&nmmdos=<%=nmmDos %>&npmdos=<%=npmdos %>','_blank','width=850,height=600')">
        		<td style="color:#000;text-align:center;"><label><B><%= ++j %></B> </label></td>
        		<td style="color:#000;text-align:left;"><label><B>
        		<%
        		//if(bolehEdit) {
        			//getListMhs(String thsms, String idkmk,String uniqueId, String kdkmk, String nakmk, String shift)
        		%>
        			<!--  a href="get.listMhsUtkPenilaian?group_proses=<%="" %>&thsms=<%=thsms_inp_nilai %>&idkmk=<%=idkmk %>&uniqueId=<%=cuid %>&kdkmk=<%=kdkmk %>&nakmk=<%=nakmk %>&shift=<%=shift %>&noKlsPll=<%=nopll %>&kode_kelas=<%=kodeKls %>&kode_gedung=<%=kodeGdg %>&kode_kampus=<%=kodeKmp %>&kode_gabung_kls=<%=kodeGabung %>&kode_gabung_kmp=<%=kodeGabungUniv %>&skstm=<%="0" %>&skspr=<%="0" %>&skslp=<%="0" %>&bolehEdit=true&nmmdos=<%=nmmDos %>&npmdos=<%=npmdos %>"--><%= kdkmk+" - "+nakmk%><!--  /a -->
        		<%	
        		//}
        		//else {
        		//	out.print(kdkmk+" - "+nakmk);
        		//}	
        		%>
        		</B> </label></td>
        		<td style="color:#000;text-align:center;"><label><B><%=Converter.getNamaKdpst(kdpst) %>
        		<%
        		if(konsen!=null) {
        			out.print("<br/>konsentrasi<br/>"+konsen);
        		}
        		%>
        		</B> </label></td>
        		<td style="color:#000;text-align:center;"><label><B><%=shift %></B> </label></td>
        		<td style="color:#000;text-align:center;"><label><B><%=nmmDos %></B> </label></td>
        	</tr>	
		<%		
			}
		%>
		</table>
		<%
		}
		else {
		%>
		<div style="text-align:center;font-size:1.5em;font-weight:bold">
		Tidak Ada Pengajuan Kelas <%=thsms_inp_nilai %> atau KRS Mahaiswa Belum Diisi (Kelas 0 Mhs)
		</div>	
		<%
		}
		%>
		<!-- Column 1 end -->
	</div>
</div>

</body>
<%
//}
//catch(Exception e) {
//	out.println("ADA ERROR @nilai.dashPenilaian.jsp");
//}
%>
</html>
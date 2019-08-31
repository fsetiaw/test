<!DOCTYPE html>
<%
//try {
%>
<head>
<%@page import="beans.setting.Constants"%>
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
<style>
.table { 
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>; 

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
.table tr:hover td { background:#82B0C3;word-wrap:break-word; }
.table thead:hover td { background:#82B0C3;word-wrap:break-word; } <thead> yg jd anchor
</style>
	<script>		
		$(document).ready(function() {
			$("#wait").hide();
			$("#main").show();
		});	
	</script>

<%

beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String sta_limit = request.getParameter("sta_limit");
String range_limit = request.getParameter("range_limit");
String cmd = request.getParameter("cmd");
//System.out.println("sta_limit1="+sta_limit);
//System.out.println("range_limit1="+range_limit);
String startingThsms = request.getParameter("starting_thsms");
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


%>
<script type="text/javascript">
</script>
</head>
<body>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai<br>[Lama proses bisa sampai 5 menit]</p>
</div>
<div id="main">
<div id="header">
<ul>
		<li>
		<a href="#" onclick="(function(){
				//scroll(0,0);
 				parent.scrollTo(0,0);
 				var x = document.getElementById('wait');
 				var y = document.getElementById('main');
 				x.style.display = 'block';
 				y.style.display = 'none';
 				location.href='get.notifications'})()" target="_self">
				BACK <span><b style="color:#eee">&nbsp</b>
			</span></a></li>
	</ul>	
<!@include file="../innerMenu.jsp" %>
<%
boolean bolehEditNilai = false;
//if(scopeHakAkses.contains("`e`")||scopeHakAkses.contains("`i`")) {
Vector list_kdpst_ink = validUsr.getScopeUpd7des2012ProdiOnlyButKeepOwn("ink");
SearchDbClassPoll scp = new SearchDbClassPoll(validUsr.getNpm());
Vector vKlsPenilaian = scp.getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya(list_kdpst_ink, Checker.getThsmsInputNilai());
if(vKlsPenilaian!=null && !validUsr.isHakAksesReadOnly("ink")) {	
	bolehEditNilai = true;
}
String group_proses = request.getParameter("group_proses");
%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		
	<br/><br/>
		<%
		
		//scp = new SearchDbClassPoll(validUsr.getNpm());
		//String startingThsms = request.getParameter("starting_thsms");
		//String cmd = request.getParameter("cmd");
		//Vector v_scope_kdpst = validUsr.getScopeKdpst_vFinal(cmd);
		//Vector v = scp.getKelasYgMasihAdaNilaiTunda_v1(startingThsms, v_scope_kdpst, 0, 21);
		int tot_data_tampil=0;
		Vector v = (Vector)session.getAttribute("v_list_kelas");
		int tot_data = 0;
		int nu_sta_limit=0;
		if(v!=null) {
			tot_data = v.size();
			tot_data_tampil=tot_data;
		}
		boolean ada_next = false, ada_prev=false;
		if(tot_data==Integer.parseInt(range_limit)) {
			ada_next=true;
			tot_data_tampil=tot_data_tampil-1;
			nu_sta_limit=Integer.valueOf(sta_limit)+Integer.valueOf(range_limit);
		}
		if(Integer.parseInt(sta_limit)>1) {
			ada_prev=true;
			nu_sta_limit=Integer.valueOf(sta_limit)-(Integer.valueOf(range_limit)-1);
		}
		//if(vKlsPenilaian!=null && vKlsPenilaian.size()>0) {
		int counter=0;
		if(v!=null) {	
			//System.out.println("thsms_inp_nilai="+thsms_inp_nilai);
			int j=0;
			ListIterator li = v.listIterator();
			if(li.hasNext()) {
				
				counter++;
				//thsms+"~"+kdpst+"~"+nmpst+"~"+kdjen+"~"+kdkmk+"~"+nakmk+"~"+shift+"~"+nmdos+"~"+npmdos+"~"+cuid+"~"+kode_gabung;
				String brs = (String) li.next();
				StringTokenizer st = new StringTokenizer(brs,"~");
				String prev_thsms=st.nextToken();
				String prev_kdpst=st.nextToken();
				String prev_nmpst=st.nextToken();
				String prev_kdjen=st.nextToken();
				String prev_kdkmk=st.nextToken();
				String prev_nakmk=st.nextToken();
				String prev_shift=st.nextToken();
				String prev_nmdos=st.nextToken();
				String prev_npmdos=st.nextToken();
				String prev_cuid=st.nextToken();
				String prev_kode_gabung=st.nextToken();
				String prev_idkmk=st.nextToken();
		%>
		<center>
		
		<table style="width:90%;border:none">
			<tr>
<%
				if(ada_prev) {
					
%>
				<td style="width:50%;text-align:left;padding:0 0 0 10px">
					<a href="cek.classPoolDenganNilaiTunda?starting_thsms=<%=startingThsms %>&cmd=ink&atMenu=index&from=home&sta_limit=<%=nu_sta_limit %>&range_limit=<%=range_limit %>" style="font-weight:bold;font-size:1.6em" target="_self">Prev <</a>
				</td>	
<%
				}
				if(ada_next) {
%>
				<td style="width:50%;text-align:right;padding:0 10px 0 0">
					<a href="cek.classPoolDenganNilaiTunda?starting_thsms=<%=startingThsms %>&cmd=ink&atMenu=index&from=home&sta_limit=<%=nu_sta_limit %>&range_limit=<%=range_limit %>" style="font-weight:bold;font-size:1.25em" target="_self">> Next</a>
				</td>	
<%
				}
%>
			</tr>
		</table>	
		<table class="table" style="width:90%">
	    	<tr>
	    		<td style="background:#369;color:#fff;text-align:center;font-size:1.7em" colspan="5"><label><B>LIST PERKULIAHAN <%=prev_thsms %></B> </label></td>
	    	</tr>
	    	<tr>
    		    <td style="background:#369;color:#fff;text-align:center;width:5%"><label><B>NO.</B> </label></td>
        		<td style="background:#369;color:#fff;text-align:left;width:40%"><label><B>MATAKULIAH</B> </label></td>
        		<td style="background:#369;color:#fff;text-align:center;width:25%"><label><B>PRODI</B> </label></td>
        		<td style="background:#369;color:#fff;text-align:center;width:10%"><label><B>INFO KELAS</B> </label></td>
        		<td style="background:#369;color:#fff;text-align:center;width:20%"><label><B>NAMA DOSEN</B> </label></td>
    		</tr>
    		
    		<tr onclick="(function(){
 				//scroll(0,0);
 				parent.scrollTo(0,0);
 				var x = document.getElementById('wait');
 				var y = document.getElementById('main');
 				x.style.display = 'block';
 				y.style.display = 'none';
 				location.href='get.listMhsUtkPenilaian?kelas_thsms=<%=prev_thsms %>&starting_thsms=<%=startingThsms %>&sta_limit=<%=sta_limit %>&range_limit=<%=range_limit %>&from=home&atMenu=index&cmd=<%=cmd %>&group_proses=<%=group_proses %>&thsms=<%=prev_thsms %>&idkmk=<%=prev_idkmk %>&uniqueId=<%=prev_cuid %>&kdkmk=<%=prev_kdkmk %>&nakmk=<%=prev_nakmk %>&shift=<%=prev_shift %>&noKlsPll=&kode_kelas=&kode_gedung=&kode_kampus=&kode_gabung_kls=<%=prev_kode_gabung %>&kode_gabung_kmp=&skstm=0&skspr=0&skslp=0&bolehEdit=<%=bolehEditNilai%>&nmmdos=<%=prev_nmdos %>&npmdos=<%=prev_npmdos %>'})()">

        		<td style="color:#000;text-align:center;padding:3px 3px"><B><%= ++j %></B> </td>
        		<td style="color:#000;text-align:left;padding:3px 5px;font-size:1.25em"><B>
				<%=prev_nakmk.toUpperCase()+"<br>["+prev_kdkmk+"] " %>
        		</B> </td>
        		<td style="color:#000;text-align:center;padding:3px 3px"><B>
        		<%=prev_nmpst+"<br>["+prev_kdjen+"]" %>
        		</B> </td>
        		<td style="color:#000;text-align:center;padding:3px 3px"><B><%=prev_shift.toLowerCase() %></B> </td>
        		<td style="color:#000;text-align:center;padding:3px 3px"><B><%=prev_nmdos.toUpperCase() %></B></td>
        	</tr>	
		<%	
				
				while(li.hasNext()) {
					counter++;
					boolean tampilkan=true;
					if(counter>tot_data_tampil) {
						tampilkan=false;
					}
					brs = (String) li.next();
					st = new StringTokenizer(brs,"~");
					String thsms=st.nextToken();
					String kdpst=st.nextToken();
					String nmpst=st.nextToken();
					String kdjen=st.nextToken();
					String kdkmk=st.nextToken();
					String nakmk=st.nextToken();
					String shift=st.nextToken();
					String nmdos=st.nextToken();
					String npmdos=st.nextToken();
					String cuid=st.nextToken();
					String kode_gabung=st.nextToken();
					String idkmk=st.nextToken();
					if(tampilkan) {
						if(thsms.equalsIgnoreCase(prev_thsms)) {
					//masih sama	
					
		%>
			<tr onclick="(function(){
 				//scroll(0,0);
 				parent.scrollTo(0,0);
 				var x = document.getElementById('wait');
 				var y = document.getElementById('main');
 				x.style.display = 'block';
 				y.style.display = 'none';
 				location.href='get.listMhsUtkPenilaian?kelas_thsms=<%=thsms %>&starting_thsms=<%=startingThsms %>&sta_limit=<%=sta_limit %>&range_limit=<%=range_limit %>&from=home&atMenu=index&cmd=<%=cmd %>&group_proses=<%=group_proses %>&thsms=<%=thsms %>&idkmk=<%=idkmk %>&uniqueId=<%=cuid %>&kdkmk=<%=prev_kdkmk %>&nakmk=<%=nakmk %>&shift=<%=shift %>&noKlsPll=&kode_kelas=&kode_gedung=&kode_kampus=&kode_gabung_kls=<%=kode_gabung %>&kode_gabung_kmp=&skstm=0&skspr=0&skslp=0&bolehEdit=<%=bolehEditNilai%>&nmmdos=<%=nmdos %>&npmdos=<%=npmdos %>'})()">
        		<td style="color:#000;text-align:center;padding:3px 3px"><B><%= ++j %></B> </td>
        		<td style="color:#000;text-align:left;padding:3px 5px;font-size:1.25em"><B>
				<%=nakmk.toUpperCase()+"<br>["+kdkmk+"] " %>
        		</B> </td>
        		<td style="color:#000;text-align:center;padding:3px 3px"><B>
        		<%=nmpst+"<br>["+kdjen+"]" %>
        		</B> </td>
        		<td style="color:#000;text-align:center;padding:3px 3px"><B><%=shift.toLowerCase() %></B> </td>
        		<td style="color:#000;text-align:center;padding:3px 3px"><B><%=nmdos.toUpperCase() %></B></td>
        	</tr>	
		<%	
						}
						else {
							prev_thsms=new String(thsms);
							prev_kdpst=new String(kdpst);
							prev_nmpst=new String(nmpst);
							prev_kdjen=new String(kdjen);
							prev_kdkmk=new String(kdkmk);
							prev_nakmk=new String(nakmk);
							prev_shift=new String(shift);
							prev_nmdos=new String(nmdos);
							prev_npmdos=new String(npmdos);
							prev_cuid=new String(cuid);
							prev_kode_gabung=new String(kode_gabung);
							prev_idkmk=new String(idkmk);
							j=0;
						%>
		</table>
		<br><br>
		<table class="table" style="width:90%">
			<tr>
				<td style="background:#369;color:#fff;text-align:center;font-size:1.7em" colspan="5"><label><B>LIST PERKULIAHAN <%=prev_thsms %></B> </label></td>
			</tr>
			<tr>
				<td style="background:#369;color:#fff;text-align:center;width:5%"><label><B>NO.</B> </label></td>
				<td style="background:#369;color:#fff;text-align:left;width:40%"><label><B>MATAKULIAH</B> </label></td>
				<td style="background:#369;color:#fff;text-align:center;width:25%"><label><B>PRODI</B> </label></td>
				<td style="background:#369;color:#fff;text-align:center;width:10%"><label><B>INFO KELAS</B> </label></td>
				<td style="background:#369;color:#fff;text-align:center;width:20%"><label><B>NAMA DOSEN</B> </label></td>
			</tr>
			<tr onclick="(function(){
				//scroll(0,0);
 				parent.scrollTo(0,0);
 				var x = document.getElementById('wait');
 				var y = document.getElementById('main');
 				x.style.display = 'block';
 				y.style.display = 'none';
 				location.href='get.listMhsUtkPenilaian?kelas_thsms=<%=prev_thsms %>&starting_thsms=<%=startingThsms %>&sta_limit=<%=sta_limit %>&range_limit=<%=range_limit %>&from=home&atMenu=index&cmd=<%=cmd %>&group_proses=<%=group_proses %>&thsms=<%=prev_thsms %>&idkmk=<%=prev_idkmk %>&uniqueId=<%=prev_cuid %>&kdkmk=<%=prev_kdkmk %>&nakmk=<%=prev_nakmk %>&shift=<%=prev_shift %>&noKlsPll=&kode_kelas=&kode_gedung=&kode_kampus=&kode_gabung_kls=<%=prev_kode_gabung %>&kode_gabung_kmp=&skstm=0&skspr=0&skslp=0&bolehEdit=<%=bolehEditNilai%>&nmmdos=<%=prev_nmdos %>&npmdos=<%=prev_npmdos %>'})()">
				<td style="color:#000;text-align:center;padding:3px 3px"><B><%= ++j %></B> </td>
				<td style="color:#000;text-align:left;padding:3px 5px;font-size:1.25em"><B>
				<%=prev_nakmk.toUpperCase()+"<br>["+prev_kdkmk+"] " %>
				</B> </td>
				<td style="color:#000;text-align:center;padding:3px 3px"><B>
				<%=prev_nmpst+"<br>["+prev_kdjen+"]" %>
				</B> </td>
				<td style="color:#000;text-align:center;padding:3px 3px"><B><%=prev_shift.toLowerCase() %></B> </td>
				<td style="color:#000;text-align:center;padding:3px 3px"><B><%=prev_nmdos.toUpperCase() %></B></td>
			</tr>	
						<%					
						}
					}
				}
		%>
		</table>
		</center>
		<%
		
			}
		}	
		else {
		%>
		<div style="text-align:center;font-size:1.25em;font-weight:bold">
		
		</div>	
		<%
		}
		%>
		<!-- Column 1 end -->
	</div>
</div>
</div>
</body>
<%
//}
//catch(Exception e) {
//	out.println("ADA ERROR @nilai.dashPenilaian.jsp");
//}
//
%>
</html>
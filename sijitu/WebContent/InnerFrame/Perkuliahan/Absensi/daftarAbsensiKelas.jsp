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
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%@ page import="beans.dbase.classPoll.*" %>
<%

beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= null; 
Vector vListMhs = (Vector) session.getAttribute("vListMhs");
Vector vListMhsCetak = new Vector();
//System.out.println("vListMhs="+vListMhs.size());
boolean allowCetak = false;
String tmp = ""+request.getParameter("allow_cetak");
if(tmp.equalsIgnoreCase("true")) {
	allowCetak = true;
}

String kelas_gabungan = request.getParameter("kelas_gabungan");
String kelas_info = request.getParameter("kelasInfo");
StringTokenizer st = new StringTokenizer(kelas_info,"`");
String target_kmp = st.nextToken();
String kdkmk = st.nextToken();
String nakmk = st.nextToken();
String nmmdos = st.nextToken();
String shift = st.nextToken();
String cuid = st.nextToken();
//Vector vScopeCtk = validUsr.getScopeUpd7des2012ReturnDistinctKdpst("allowCetakAbsenKls");
/*
CEK APAKAH SUDAH MELAKUKAN HEREGISTRASI, BILA  BELUM MAKA HIDDEN BILA DICETAK
<form action="file.downloadAbsenKelas">
*/
//request.removeAttribute("listTipeUjian");
//String atMenu = request.getParameter("atMenu");
%>
<script type="text/javascript">
</script>
</head>
<body>
<div id="header">
	<ul>
		<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self"> GO<span>BACK</span></a></li>
	</ul>	
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br/>
		<br />
		<!-- Column 1 start -->
<%
if(vListMhs!=null && vListMhs.size()>0) {
	int norut = 1;
	if(allowCetak) {
%>
<form action="file.downloadAbsenKelas">
	<input type="hidden" name="kelasInfo" value="<%=kelas_info %>"/>
	<input type="hidden" name="kelas_gabungan" value="<%=kelas_gabungan %>"/>
<%		
	}
%>
	<P STYLE="font-size:0.7em;padding:0 0 0 12%">
	STATUS PROGRES : <br/>
	(1) TERDAFTAR = TELAH MELAKUKAN DAFTAR ULANG DAN TELAH DISETUJUI<br/>
	(2) PROCES PENGAJUAN = TELAH MELAKUKAN DAFTAR ULANG DAN DALAM PROSES MENUNGGU PERSETUJUAN<br/>
	(3) TIDAK TERDAFTAR = BELUM MELAKUKAN PENGAJUAN PENDAFTARAN ULANG<br/>
	</P>
	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">

	<tr>
		<td style="background:#369;color:#fff;text-align:center" colspan="6"><label><B><%="["+kdkmk+"]&nbsp"+nakmk+"<br/>"+shift+"<br/>"+nmmdos %>
		<%
		if(kelas_gabungan.equalsIgnoreCase("true")) {
			out.print("<br>[KELAS GABUNGAN]");
		}
		%>
		</B> </label></td>
	</tr>
	<tr>
		<td style="background:#369;color:#fff;text-align:center;width:25px"><label><B>NO.</B> </label></td>
		<td style="background:#369;color:#fff;text-align:left;width:250px"><label><B>NAMA MAHASISWA</B> </label></td>
		<td style="background:#369;color:#fff;text-align:center;width:150px"><label><B>NPM MAHASISWA</B> </label></td>
		<td style="background:#369;color:#fff;text-align:center;width:150px"><label><B>PRODI</B> </label></td>
		<td style="background:#369;color:#fff;text-align:center;width:75px"><label><B>ANGKATAN</B> </label></td>
		<td style="background:#369;color:#fff;text-align:center;width:150px"><label><B>STATUS<br/>HEREGISTRASI</B> </label></td>
	</tr>
			<%			
	ListIterator li = vListMhs.listIterator();
	ListIterator li1 = vListMhsCetak.listIterator();
			
	while(li.hasNext()) {
			
		String brs = (String)li.next();
		//System.out.println("bar="+brs);
		st = new StringTokenizer(brs,"`");
		String nmmhs = st.nextToken();
		String npmhs = st.nextToken();
		String kdpst = st.nextToken();
		String smawl = st.nextToken();
		String thsms = st.nextToken();
		String status = st.nextToken();
		
		//if(status!=null && !Checker.isStringNullOrEmpty(status) && status.contains("ULANG")) {
		//if(false) {
		if(status!=null && status.startsWith("true")) {
			status = "TERDAFTAR";
			li1.add(brs);
			st.nextToken();//buat dapetin nim di tokn terakhir
			st.nextToken();//buat dapetin nim di tokn terakhir
			
		}
		else if(status!=null && status.startsWith("false")) {
			status = "PROSES PENGAJUAN";
			st.nextToken();//buat dapetin nim di tokn terakhir
			st.nextToken();//buat dapetin nim di tokn terakhir
			
			li1.add(brs);
		}
		else {
			//if(status == null || Checker.isStringNullOrEmpty(status)) {
			//if(true) {	
			status = "TIDAK TERDAFTAR";
			
				//li1.add(brs);
			//}
		}
		String nimhs = st.nextToken();	
	%> 	
	 	<tr>	
	        <td style="color:#000;text-align:center;"><label><B><%=norut++%></B> </label></td>
	        <td style="color:#000;text-align:left;"><label><B><%=nmmhs %></B> </label></td>
	        <td style="color:#000;text-align:center;"><label><B>
	        <%=npmhs %><br/><%=nimhs %> </B> </label></td>
	        <td style="color:#000;text-align:center;"><label><B><%= Converter.getNamaKdpst(kdpst) %></B> </label></td>
	        <td style="color:#000;text-align:center;"><label><B><%=smawl %></B> </label></td>
	        <%
	        if(status.equalsIgnoreCase("TERDAFTAR")) {
	        %>
	        <td style="color:#369;text-align:center;"><label><B>
	        <%	
	        }
	        else if(status.equalsIgnoreCase("PROSES PENGAJUAN")) {
	       %>
		    <td style="color:#F3B200;text-align:center;"><label><B>
		    <%	
	        }
	        else {
	       	%>
		    <td style="color:#D60000;text-align:center;"><label><B>
		    <%	
	        }
	        %><%=status %></B> </label></td>
	  	</tr>
<%
	}
	if(allowCetak) {
		session.setAttribute("vListMhsCetak", vListMhsCetak);
%>
		<tr>
			<td style="background:#369;color:#fff;text-align:center" colspan="6">
				<table width="100%">
					<tr>
						<td><input type="submit" name="submit" value="CETAK ABSEN" style="width:200px;height:35px;text-align:center;text-weight:bold;"/></td>
					
						<td><input type="submit" name="submit" value="CETAK ABSEN UTS" style="width:200px;height:35px;text-align:center;text-weight:bold"/></td>
					
						<td></td>
					</tr>
				</table>
			</td>
		</tr>
<%		
	}
	
}
%>
	</table>
</form>	

		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>
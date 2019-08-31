<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.tbbnl.SearchDbTbbnl" %>
<%@ page import="beans.dbase.SearchDb" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@page import="beans.setting.Constants"%>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
String targetThsms = request.getParameter("targetThsms");
String whitelistValue = request.getParameter("whitelist");
String kdpst_nmpst = request.getParameter("kdpst_nmpst");
System.out.println("kdpst_nmpst="+kdpst_nmpst);
String char_pemisah = "`";
if(kdpst_nmpst.contains(",")) {
	char_pemisah = ",";
}
StringTokenizer st = new StringTokenizer(kdpst_nmpst,char_pemisah);
String target_kdpst = st.nextToken();
String target_npm = st.nextToken();

boolean whiteList = false;
if(whitelistValue!=null && !Checker.isStringNullOrEmpty(whitelistValue)) {
	whiteList = Boolean.parseBoolean(whitelistValue);
}

Vector vListMakulKurikuluim =(Vector) request.getAttribute("vf");

//System.out.println("vListMakulKurikuluim="+vListMakulKurikuluim.size());
//Vector vSdhCp =(Vector) request.getAttribute("vSdhCp");
//Vector vBlmCp =(Vector) request.getAttribute("vBlmCp");

//if(vCp!=null) {
//	//System.out.println("vCp.size = "+vCp.size());
//	if(vSdhCp!=null) {
//		//System.out.println("vSdhCp.size = "+vSdhCp.size());
//	}
//	if(vBlmCp!=null) {
//		//System.out.println("vBlmCp.size = "+vBlmCp.size());
//	}
//	ListIterator liCp = vCp.listIterator();
	
//}
Vector v = null;
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
SearchDbTbbnl sdt = new SearchDbTbbnl(validUsr.getNpm());
Vector vKdpst = new Vector();
vKdpst.add(new String(target_kdpst));
//Vector v_nilai = sdt.getInfoTabelNilaiYgBerlakuPerKdpst(vKdpst);

//boolean readOnlyMode = false;
//if(validUsr.isAllowTo("insertKrsReadOnlyMode")>0) {
//	readOnlyMode = true;
//} 
boolean usrMhs = false;
if(validUsr.getObjNickNameGivenObjId().contains("MHS") || validUsr.getObjNickNameGivenObjId().contains("mhs") ) {
	usrMhs = true;
}

boolean editor = validUsr.isUsrAllowTo_updated("insertKrsAllThsms", target_npm);
%>


</head>
<body>
<div id="header">
<!--   include file="../innerMenu.jsp" -->
<%@ include file="../krsKhsSubMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">

		<br />
		<!-- Column 1 start -->
		
		
<%
if(char_pemisah.equalsIgnoreCase(",")) {
	//DULU KASUS DIMANA PINDAH PRODI DAN KO nya MASIH DARI PRODI LAEN
%>
	<h1> OUps..ada error tolong hubungi ADMIN, terima kasih ya</h1>
<%
}
if(vListMakulKurikuluim!=null && vListMakulKurikuluim.size()>0 && editor) {
	ListIterator li = vListMakulKurikuluim.listIterator();
%>							
    <form action="proses.updKrsNonCp" >	
    <input type="hidden" name="id_obj" value="<%=v_id_obj %>" />
    <input type="hidden" name="nmm" value="<%=v_nmmhs %>" />
    <input type="hidden" name="npm" value="<%=v_npmhs %>" />
    <input type="hidden" name="kdpst" value="<%=v_kdpst %>" />
    <input type="hidden" name="obj_lvl" value="<%=v_obj_lvl %>" />	
    <input type="hidden" name="target_thsms" value="<%=targetThsms %>" />	
    
    	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:650px">
		<tr>
			<td style="background:#369;color:#fff;text-align:center;height:30px;font-size:25px" colspan="4"><label><B>PENGISIAN KRS/KSH</B></label>
				<select name="thsms" style="font-size:20px;">
					<%
						//String keter_thsms = Converter.convertThsmsKeterOnly(Checker.getThsmsKrs());
						//String value_thsms = Converter.convertThsmsValueOnly(Checker.getThsmsKrs());
						%>
						<option value="<%=targetThsms %>" selected="selected"><%=Converter.convertThsmsKeterOnly(targetThsms)%></option>
						<%
					%>
				</select>
			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:center;width:125px"><label><B>KODE</B> </label></td>
			<td style="background:#369;color:#fff;text-align:CENTER;width:425px;padding:0 0 0 15px"><label><B>MATAKULIAH</B> </label></td>
			<td style="background:#369;color:#fff;text-align:center;width:50px" ><label><B>SKS</B> </label></td>
			<td style="background:#369;color:#fff;text-align:center;width:50px" ><label><B>NILAI</B> </label></td>

		</tr>
<%
	String list_nilai = Getter.getAngkaPenilaian(targetThsms, v_kdpst);
//li.add(semes+"#&"+kdpst+"#&"+kdkmk+"#&"+nakmk+"#&"+skstm+"#&"+skspr+"#&"+skslp+"#&"+(skstm+skspr+skslp)+"#&"+idkmk);
	do {
		String brs = (String)li.next();
		st = new StringTokenizer(brs,"#&");
		String semes=st.nextToken();
		kdpst=st.nextToken();
		String kdkmk=st.nextToken();
		String nakmk=st.nextToken();
		String skstm=st.nextToken();
		String skspr=st.nextToken();
		String skslp=st.nextToken();
		String skstt=st.nextToken();
		String idkmk=st.nextToken();
%>
		<tr>
			<td style="color:#000;text-align:center;"><label><B><%=kdkmk %></B> </label></td>
			<td style="color:#000;text-align:left;padding:0 0 0 15px"><label><B><%=nakmk %></B> </label></td>
			<td style="color:#000;text-align:center;"><label><B><%=skstt %></B> </label></td>
			
			<td style="color:#000;text-align:center;"><label><B>
			<%
		if(list_nilai!=null && !Checker.isStringNullOrEmpty(list_nilai)) {
			//ListIterator lin = v_nilai.listIterator();
			
			%>
				<select name="nlakh" style="width:99%">
			<%
			//li.hasNext();
			//String nilai = (String)lin.next();
			//System.out.println("nilai="+nilai);
			StringTokenizer stn = new StringTokenizer(list_nilai,"`");
			//stn.nextToken();
			//String list_nilai = stn.nextToken();
			//System.out.println("list_nilai="+list_nilai);
			//stn = new StringTokenizer(list_nilai,"-");
			while(stn.hasMoreTokens()) {
				String nlakh = stn.nextToken(); 
				String bobot = stn.nextToken();
				if(nlakh.equalsIgnoreCase("T")) {
					%>
					<option selected="selected" value="<%=nlakh%>`<%=bobot%>`<%=idkmk%>`<%=kdkmk%>`<%=skstt%>"><%=nlakh %></option>
			<%		
				}
				else {
			%>
					<option value="<%=nlakh%>`<%=bobot%>`<%=idkmk%>`<%=kdkmk%>`<%=skstt%>"><%=nlakh %></option>
			<%
				}
			}
			%>
			</select>
			<%
		}
			%>
			</td>
		</tr>
<%
	}
	while(li.hasNext());
%>		
	</table>
	<br/>
	<table align="center"  style=";color:#000;width:700px">
    	<tr>
    		<td style="text-align:center">
    		<input type="submit" value="INSERT KRS" style="height:35px;width:100px" align="center"/>
    		</td>
    	</tr>
    	
    </table>	
	</form>
<%
}
%>	

		</div>
	</div>
</body>
</html>
<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.SearchDb" %>
<%@ page import="beans.dbase.UpdateDb" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	
	String err = (String)request.getAttribute("err");
	//String submit_value  = (String)request.getParameter("submit");
	//System.out.println("submit_value="+submit_value);
	request.removeAttribute("err");
	request.setAttribute("atPage", "listKurikulum");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String kdpst_nmpst = request.getParameter("kdpst_nmpst");
	String idkur_ = ""+request.getParameter("idkur_");
	//System.out.println("idkur_="+idkur_);
	StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
	String kdpst = st.nextToken();
	String nmpst = "";
	while(st.hasMoreTokens()) {
		nmpst = nmpst+st.nextToken();
		if(st.hasMoreTokens()) {
			nmpst = nmpst+" ";
		}
	}
	SearchDb sdb = new SearchDb();
	boolean allowEditListKur = false;
	String callerPage = request.getParameter("callerPage");
%>

<style>
.tooltip {
    position: relative;
    display: inline-block;
    
}

.tooltip .tooltiptext {
    visibility: hidden;
    width: 120px;
    background-color: #555;
    color: #fff;
    text-align: center;
    border-radius: 6px;
    padding: 5px 0;
    position: absolute;
    z-index: 1;
    bottom: 125%;
    left: 50%;
    
    margin-left: -60px;
    opacity: 0;
    transition: opacity 0.3s;
}

.tooltip .tooltiptext::after {
    content: "";
    position: absolute;
    top: 100%;
    left: 50%;
    margin-left: -5px;
    border-width: 5px;
    border-style: solid;
    border-color: #555 transparent transparent transparent;
}

.tooltip:hover .tooltiptext {
    visibility: visible;
    opacity: 1;
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

</style>
</head>
<body>
<div id="header">
	<ul>
	<%
	
	if(callerPage!=null && !Checker.isStringNullOrEmpty(callerPage) && (!callerPage.equalsIgnoreCase(request.getRequestURI()))) {
		%>
			<li><a href="<%=callerPage%>" target="inner_iframe">GO<span>BACK</span></a></li>
		<%
	}
	
	Vector vEditMkKur = validUsr.getScopeUpd7des2012("hasEditMkKurikulum");
	if(vEditMkKur.size()>0) {
		allowEditListKur = true;
	%>
		<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/innerMenu.jsp" />
	<%
	}
	%>
	</ul>
	
</div>

<div class="colmask fullpage">
	<div class="col1">
	<section style="vertical-align:middle;text-align:center;background:#eee;font-size:2em;color:#369;font-weight:bold">
		TAMBAH / EDIT KURIKULUM
	</section>
		<br />
		<!-- Column 1 start -->
		<%
		if(!Checker.isStringNullOrEmpty(err)) {
		%>
		<p style="vertical-align:middle;padding:0 0 0 20px;text-align:left;font-weight:bold;color:red">
			<%
			out.print("ERROR:<BR>"+err);
			%>
		</p>
		<%	
		}
		/*
		if(submit_value!=null && submit_value.equalsIgnoreCase("end")) {
			out.print("end<br>");
			UpdateDb udb = new UpdateDb();
			udb.expiredkanKrklm(Integer.parseInt(idkur_), request.getRemoteAddr(), validUsr);
			String target = Constants.getRootWeb()+"/InnerFrame/Akademik/indexAkademik.jsp";
			String uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			String url = PathFinder.getPath(uri, target);
		}
		else {
		*/	
		%>
		<p>
		<form action="get.listKurikulum" method="post">
		<input type="hidden" value="<%=kdpst %>,<%=nmpst %>" name="kdpst_nmpst" />
		
		<%
		//cek apa habis update dan lihat statusnya
		String baru_update = (String)request.getAttribute("update_status");
		request.removeAttribute("update_status");
		
		
		//idkur+","+kdkur+","+stkur+","+thsms1+","+thsms2+","+konsen+","+skstt+","+smstt+","+wajib+","+option
		
		String kdkur_1="";
		String smstt_1="";
		String skstt_1="";
		String wajib_1="";
		//System.out.println("wajib_1="+wajib_1);
		String pilihan_1="";
		String thsms_1="";
		String thsms_2="";
		String konsen_1="";
		String stkur_1="";
		if(Checker.isStringNullOrEmpty(baru_update) || !baru_update.equalsIgnoreCase("berhasil")) {
			kdkur_1=request.getParameter("kdkur");
			smstt_1=request.getParameter("smstt");
			skstt_1=request.getParameter("skstt");
			wajib_1=request.getParameter("wajib");
			//System.out.println("wajib_1="+wajib_1);
			pilihan_1=request.getParameter("pilihan");
			thsms_1=request.getParameter("thsms1");
			thsms_2=request.getParameter("thsms2");
			konsen_1=request.getParameter("konsen");
			stkur_1=request.getParameter("stkur");	
		}
		
		%>
		<input type="hidden" name="idkur_" value="<%=idkur_ %>" />
		<input type="hidden" name="stkur" value="<%=stkur_1 %>" />
		<table class="table" style="vertical-align:middle;width:90%">
			<tr>
				<td style="vertical-align:middle;background:#369;color:#fff;text-align:center;font-size:1.5em" colspan="4"><b>FORM TAMBAH / EDIT KURIKULUM <%=Converter.getNamaKdpst(kdpst) %></b></td>
			</tr>
			<tr>
				<td width="35%">&nbsp&nbspNAMA/KODE KURIKULUM</td>	
				<%
					//String kdkur_1 = request.getParameter("kdkur_1");
					if(kdkur_1!=null) {
				%>	
				<td colspan="3"><input type="text"  name="kdkur" value="<%=kdkur_1 %>" style="vertical-align:middle;padding:0 0 0 10px;width:100%;height:30px;border:none" required/></td>
				<%		
					}
					else {
				%>	
				<td colspan="3"><input type="text"  name="kdkur" value="" style="vertical-align:middle;padding:0 0 0 10px;width:100%;height:30px;border:none" required/></td>
				<%
					}
				%>
			</tr>
			<tr>
				<td width="35%">&nbsp&nbspNAMA KONSENTRASI KURIKULUM</td>	
				<%
					//String konsen_1 = request.getParameter("konsen_1");
					if(!Checker.isStringNullOrEmpty(konsen_1)) {
				%>	
				<td colspan="3"><input type="text"  name="konsen" value="<%=Checker.pnn(konsen_1)%>" style="vertical-align:middle;padding:0 0 0 10px;width:100%;height:30px;border:none"/></td>
				<%		
					}
					else {
				%>	
				<td colspan="3"><input type="text"  name="konsen" placeholder="Diisi bila prodi memiliki beberapa konsentrasi (optional)" value="" style="vertical-align:middle;padding:0 0 0 10px;width:100%;height:30px;border:none"/></td>
				<%
					}
				%>
			</tr>
			<tr>
				<td width="35%">&nbsp&nbspTOTAL SKS MATAKULIAH WAJIB</td>	
				<%
					//String wajib_1 = request.getParameter("wajib");
					if(wajib_1!=null) {
				%>
				<td width="15%"><input type="number"  name="wajib" value="<%=wajib_1 %>" style="vertical-align:middle;text-align:center;width:100%;height:30px;border:none" required/></td>
				<%		
					}
					else {
				%>
				<td width="15%"><input type="number"  name="wajib" value="" style="vertical-align:middle;text-align:center;width:100%;height:30px;border:none" required/></td>
				<%
					}
				%>				
				
				<td width="35%">&nbsp&nbspTOTAL SKS MATAKULIAH PILIHAN</td>		
				<%
					//String pilihan_1 = request.getParameter("pilihan");
					if(pilihan_1!=null) {
				%>		
				<td width="15%"><input type="number"  name="pilihan" value="<%=pilihan_1 %>" style="vertical-align:middle;text-align:center;width:100%;height:30px;border:none" required/></td>
				<%
					}
					else {
				%>		
				<td width="15%"><input type="number"  name="pilihan" value="" style="vertical-align:middle;text-align:center;width:100%;height:30px;border:none" required/></td>
				<%		
					}
				%>
			</tr>
			<tr>
				<td width="35%">&nbsp&nbspTHSMS AWAL KURIKULUM BERLAKU</td>	
				<%
					//String thsms_1 = request.getParameter("thsms_1");
					if(thsms_1!=null) {
				%>
				<td width="15%"><input type="text"  name="thsms1" value="<%=thsms_1 %>" style="vertical-align:middle;text-align:center;width:100%;height:30px;border:none" required/></td>
				<%		
					}
					else {
				%>
				<td width="15%"><input type="text"  name="thsms1" value="" style="vertical-align:middle;text-align:center;width:100%;height:30px;border:none" required/></td>
				<%
					}
				%>				
				
				<td width="35%">&nbsp&nbspTOTAL SEMESTER KURIKULUM</td>		
				<%
					//String smstt_1 = request.getParameter("smstt");
					if(smstt_1!=null) {
				%>		
				<td width="15%"><input type="number"  name="smstt" value="<%=smstt_1 %>" style="vertical-align:middle;text-align:center;width:100%;height:30px;border:none" required/></td>
				<%
					}
					else {
				%>		
				<td width="15%"><input type="number"  name="smstt" value="" style="vertical-align:middle;text-align:center;width:100%;height:30px;border:none" required/></td>
				<%		
					}
				%>
			</tr>
			<tr>
				<td style="vertical-align:middle;padding:5px 5px;background:#369;color:#fff;text-align:center;" colspan="4"><input type="submit" value="Next" style="vertical-align:middle;text-align:center;width:50%;height:30px;"/></td>
			</tr>
		</table>
		</form>
		</p>
		<br/>
		<%
		//}
		%>
		<p>
		<%
			Vector vKrklm = (Vector)request.getAttribute("vkrklm");
		//System.out.println("vKrklm = ");
		//System.out.println("vKrklm = "+vKrklm.size());
			if(vKrklm!=null && vKrklm.size()>0) {
				ListIterator li = vKrklm.listIterator();
				if(li.hasNext()){
					//SearchDb sdb = new SearchDb();
					int i=0;		
			%>
			<table class="table" style="vertical-align:middle;width:90%;background:white;border-left: 0px solid #FFFFFF;border-bottom: 0px solid #FFFFFF;border-top: 0px solid #FFFFFF;border-right: 0px solid #FFFFFF;">
			<tr>
				<td style="vertical-align:middle;background:#369;color:#fff;text-align:center;font-size:1.5em" colspan="9"><b>LIST KURIKULUM <%=nmpst %></b></td>
			</tr>
			<tr>
				<td style="vertical-align:middle;width:5%;text-align:center;background:#87B1DA"><b>NO.</b></td>
				<td style="vertical-align:middle;width:40%;;background:#87B1DA"><b>&nbsp&nbspNAMA / KODE KURIKULUM</b></td>
				<td style="vertical-align:middle;width:5%;text-align:center;background:#87B1DA"><b>TOT SMS</b></td>
				<td style="vertical-align:middle;width:6%;text-align:center;background:#87B1DA"><b>TOT SKS</b></td>
				<td style="vertical-align:middle;width:7%;text-align:center;background:#87B1DA"><b>TOT SKS WAJIB</b></td>
				<td style="vertical-align:middle;width:7%;text-align:center;background:#87B1DA"><b>TOT SKS PILIHAN</b></td>
				<td style="vertical-align:middle;width:10%;text-align:center;background:#87B1DA"><b>THSMS AWAL</b></td>
				<td style="vertical-align:middle;width:10%;text-align:center;background:#87B1DA"><b>THSMS AKHIR</b></td>
				<td style="vertical-align:middle;width:10%;text-align:center;background:#87B1DA"><b>STATUS</b></td>
			</tr>
			<%
			
					while(li.hasNext()) {
						String baris = (String)li.next();
						//idkur+","+kdkur+","+stkur+","+thsms1+","+thsms2+","+konsen+","+skstt+","+smstt+","+wajib+","+option
						
						//System.out.println("baris ="+baris);
						st = new StringTokenizer(baris,",");
						String idkur1 = st.nextToken();
						String kdkur1 = st.nextToken();
						String stkur1 = st.nextToken();
						//System.out.println("stkur1="+stkur1);
						if(stkur1.equalsIgnoreCase("null")) {
							stkur1 = "";
						}
						
						
						String thsms1 = st.nextToken();;
						if(thsms1.equalsIgnoreCase("null")) {
							thsms1 = "";
						}
						String thsms2 = st.nextToken();
						if(thsms2.equalsIgnoreCase("null")) {
							thsms2 = "";
						}
						
						String konsen1 = st.nextToken();
						String skstt1 = st.nextToken();
						String smstt1 = st.nextToken();
						String wajib1 = st.nextToken();
						String pilihan1 = st.nextToken();
						
						//li.add(idkur+","+kdkur+","+stkur+","+thsms1+","+thsms2);
						
						//proses keter status
						String keter_stkur = "";
						String listStatusKurikulum = Constants.getListStatusMk();
						//System.out.println("listStatusKurikulum="+listStatusKurikulum);
						st = new StringTokenizer(listStatusKurikulum,",");
						boolean match = false;
						while(st.hasMoreTokens()&& !match) {
							String tokn = st.nextToken();
							StringTokenizer stt = new StringTokenizer(tokn);
							String kode = stt.nextToken();
							String keter = stt.nextToken();
							if(kode.equalsIgnoreCase(stkur1)){
								match = true;
								keter_stkur = ""+keter;
							}
						}
			%>		
						<tr>
			<%
					//if(allowEditListKur==true && (keter_stkur!=null && !keter_stkur.equalsIgnoreCase("AKTIF") && !keter_stkur.equalsIgnoreCase("HAPUS"))) {
					if(allowEditListKur==true && (keter_stkur!=null && keter_stkur.equalsIgnoreCase("NON-AKTIF"))) {	
			%>			
						<form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/listKurikulum.jsp"  method="post">
						<%
					}
						////idkur+","+kdkur+","+stkur+","+thsms1+","+thsms2+","+konsen+","+skstt+","+smstt+","+wajib+","+option
						%>
						<input type="hidden" name="idkur_" value="<%=""+idkur1 %>" />
						<td style="vertical-align:middle;text-align:center;background:#d9e1e5;"><b><%=++i %><input type="hidden" value="<%=kdpst_nmpst%>" name="kdpst_nmpst"/></b></td>
						<td style="vertical-align:middle;text-align:left;background:#d9e1e5;"><b>&nbsp&nbsp
						<%
						out.print(kdkur1);
						if(!Checker.isStringNullOrEmpty(konsen1)) {
							out.print("<br/>&nbsp&nbsp&nbsp&nbspKonsentrasi "+konsen1);
						}
						%>
						<input type="hidden" value="<%=kdkur1%>" name="kdkur"/></b></td>
						<td style="vertical-align:middle;text-align:center;background:#d9e1e5;"><b><%=smstt1 %><input type="hidden" value="<%=smstt1%>" name="smstt" /></b></td>
						<td style="vertical-align:middle;text-align:center;background:#d9e1e5;"><b><%=skstt1 %><input type="hidden" value="<%=skstt1%>" name="skstt" /></b></td>
						<td style="vertical-align:middle;text-align:center;background:#d9e1e5;"><b><%=wajib1 %><input type="hidden" value="<%=wajib1%>" name="wajib" /></b></td>
						<td style="vertical-align:middle;text-align:center;background:#d9e1e5;"><b><%=pilihan1 %><input type="hidden" value="<%=pilihan1%>" name="pilihan" /></b></td>
						<td style="vertical-align:middle;text-align:center;background:#d9e1e5;"><b><%=thsms1 %><input type="hidden" value="<%=thsms1%>" name="thsms1" /></b></td>
						<td style="vertical-align:middle;text-align:center;background:#d9e1e5;"><b><%=thsms2 %><input type="hidden" value="<%=thsms2%>" name="thsms2" /></b></td>
						<input type="hidden" value="<%=konsen1%>" name="konsen" />
						<td style="vertical-align:middle;text-align:center;background:#d9e1e5;"><b><%=keter_stkur %><input type="hidden" value="<%=stkur1%>" name="stkur" /></b></td>
			<%
					//if(allowEditListKur==true && (keter_stkur!=null && !keter_stkur.equalsIgnoreCase("AKTIF") && !keter_stkur.equalsIgnoreCase("HAPUS") && !keter_stkur.equalsIgnoreCase("EXPIRED"))) {
					if(allowEditListKur==true && (keter_stkur!=null && keter_stkur.equalsIgnoreCase("NON-AKTIF"))) {				
			%>
						<td style="background:white;border-left: 1px solid #ddd;border-bottom: 0px solid #FFFFFF;border-top: 0px solid #FFFFFF;border-right: 0px solid #FFFFFF;">
							<div class="tooltip">
								<button type="submit"  value="edit" style="height:50%;width:20px;border:none;background:#FFF;"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/edit.png" alt="edit" width="20px" height="20px"><span class="tooltiptext">Edit Kurikulum</span></button>
							</div>
						</td>
					</form>	
						<!--  td style="background:#87B1DA"><input type="submit" value="edit" /></td -->		
			<%	
					}
					else if(allowEditListKur==true && (keter_stkur!=null && keter_stkur.equalsIgnoreCase("AKTIF"))) {
						%>
						
						<form action="go.expiredkanKrklm" method="post">
							<input type="hidden" name="idkur_" value="<%=""+idkur1 %>" />
							<input type="hidden" value="<%=kdpst_nmpst%>" name="kdpst_nmpst"/>
						<td style="background:white;border-left: 1px solid #ddd;border-bottom: 0px solid #FFFFFF;border-top: 0px solid #FFFFFF;border-right: 0px solid #FFFFFF;">
							<div class="tooltip">
								<button type="submit"  onclick="return confirm('Anda yakin untuk menutup kurikulum ini selamanya?');" value="end" style="height:50%;width:20px;border:none;background:#FFF;"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/end.png" alt="end" width="50px" height="50px"><span class="tooltiptext">Stop Kurikulum [Tidak Digunakan Lagi]</span></button>
							</div>
						</td>
						</form>
						<!--  td style="background:#87B1DA"><input type="submit" value="edit" /></td -->		
			<%		
					}
			%>
					
			<%
					Vector vHapusKur = validUsr.getScopeUpd7des2012("allowHapusKurikulum");
					if(vHapusKur!=null && vHapusKur.size()>0  && (keter_stkur!=null && !keter_stkur.equalsIgnoreCase("AKTIF") && !keter_stkur.equalsIgnoreCase("HAPUS") && !keter_stkur.equalsIgnoreCase("EXPIRED"))) {
						/*
						AKTIFKAN KURIKULUM TIDAK DISINI, TP STEP BERIKUTNYA
						%>
						<form action="go.aktifkanKrklm" method="post">
							<input type="hidden" name="idkur_" value="<%=""+idkur1 %>" />
							<input type="hidden" value="<%=kdpst_nmpst%>" name="kdpst_nmpst"/>
						<td style="background:white;border-left: 0px solid #FFFFFF;border:none;border-bottom: 0px solid #FFFFFF;border-top: 0px solid #FFFFFF;border-right: 0px solid #FFFFFF;"> 
							<div class="tooltip">
							<button type="submit"  value="aktif" style="height:50%;width:20px;border:none;background:#FFF;"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/checked.png" alt="aktif" width="20px" height="20px"><span class="tooltiptext">Aktifkan Kurikulum</span></button>
							</div>
						</td>
						</form>
						<%
						*/
						%>
						<form action="go.hapusKurikulum"  method="post">
						<input type="hidden" name="idkur_" value="<%=""+idkur1 %>" />
						<input type="hidden" value="<%=kdpst_nmpst%>" name="kdpst_nmpst"/>
						<!--  td> style="background:#87B1DA"><input type="submit" value="Hapus" /></td-->
						<td style="background:white;border-left: 0px solid #FFFFFF;border-bottom: 0px solid #FFFFFF;border-top: 0px solid #FFFFFF;border-right: 0px solid #FFFFFF;"> 
							<div class="tooltip">
								<button type="submit" onclick="return confirm('Anda yakin untuk menghapus kurikulum ini?');" value="hapus" style="height:50%;width:20px;border:none;background:#FFF;"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/delete.png" alt="hapus" width="20px" height="20px"><span class="tooltiptext">Hapus Kurikulum</span></button>
							</div>
						</td>
						
						</form>		
			<%	
					}
			%>
			
						</tr>
						
			<%
					}
				}
			%>
			</table>
			<%	
			}		
			%>
			
	</div>
</div>		
</body>
</html>
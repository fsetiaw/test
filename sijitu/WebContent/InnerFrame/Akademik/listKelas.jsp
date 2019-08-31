<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.SearchDb" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String kdpst_nmpst = request.getParameter("kdpst_nmpst");
	String idkmk_ = ""+request.getParameter("idkmk_");
	StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
	String kdpst = st.nextToken();
	String nmpst = "";
	request.setAttribute("atPage", "listKelas");
	while(st.hasMoreTokens()) {
		nmpst = nmpst+st.nextToken();
		if(st.hasMoreTokens()) {
			nmpst = nmpst+" ";
		}
	}
	String nama_prodi = Converter.getNamaKdpst(kdpst);
	SearchDb sdb = new SearchDb();
	boolean allowEditListKelas = false;
	String callerPage = request.getParameter("callerPage");
	String err = (String)request.getAttribute("err");
	//System.out.println("err="+err);
%>
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
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
		allowEditListKelas = true;
	%>
		<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/innerMenu.jsp" />
	<%
	}
	String kdkmk_1 = request.getParameter("kdkmk_1");
	%>
	</ul>
</div>

<div class="colmask fullpage">
	<div class="col1">
	<section style="vertical-align:middle;text-align:center;background:#eee;font-size:2em;color:#369;font-weight:bold">
	<%
	if(kdkmk_1!=null) {
				%>	
		EDIT MATAKULIAH
				<%
	}
	else {
				%>
		TAMBAH MATAKULIAH
				<%
	}
				%>
	
		
	</section>
	<%
	if(Checker.isStringNullOrEmpty(err)) {
	%>
	<br />
	<%	
	}
	else {
		%>
		<p style="vertical-align:middle;padding:0 0 0 20px;text-align:left;font-weight:bold;color:red">
			<%
			out.print("ERROR:<BR>"+err);
			%>
		</p>
		<%
	}
	%>
		
		<!-- Column 1 start -->
		
		
		<p>
		<form action="get.listKelas" method="post">
		<input type="hidden" value="<%=kdpst %>,<%=nmpst %>" name="kdpst_nmpst" />
		<%
		
		%>
		<input type="hidden" name="idkmk" value="<%=idkmk_ %>" />
		<table class="table" style="vertical-align:middle;width:90%;>
		<!--  table align="center" border="1" style="vertical-align:middle;color:#000;width:800px;background:#d9e1e5;"-->	
			<tr>
				<td style="vertical-align:middle;background:#369;color:#fff;text-align:center;font-size:1.5em" colspan="4">
				<b>
				<%
				
				if(kdkmk_1!=null) {
				%>	
				FORM EDIT MATAKULIAH <%=nama_prodi %>
				<%
				}
				else {
				%>
				FORM TAMBAH MATAKULIAH <%=nama_prodi %>
				<%
				}
				%>
				</b></td>
			</tr>
			<tr>
				<td><label>KODE MK</label></td>
				<%
					//String kdkmk_1 = request.getParameter("kdkmk_1");
					if(kdkmk_1!=null) {
				%>
				<td><input type="text"  name="kdkmk" value="<%=kdkmk_1 %>" style="vertical-align:middle;height:30px;width:100%;border:none"/></td>
				<%		
					}
					else {
				%>
				<td><input type="text"  name="kdkmk" style="vertical-align:middle;height:30px;width:100%;border:none"/></td>
				<%		
					}
				%>
				
				<td style="vertical-align:middle;background:#369;color:#fff;text-align:center" colspan="2"><b>SKS MK</b></td>
			</tr>
			<tr>
				<td><label>NAMA MK</label></td>
				<%
					String nakmk_1 = request.getParameter("nakmk_1");
					if(nakmk_1!=null) {
				%>
				<td><input type="text"  name="nakmk" value="<%=nakmk_1.replace("$",",") %>" style="vertical-align:middle;height:30px;width:100%;border:none"/></td>
				<%		
					}
					else {
				%>
				<td><input type="text"  name="nakmk" style="vertical-align:middle;height:30px;width:100%;border:none"/></td>
				<%		
					}
				%>
				<td><label>SKS TATAP MUKA</label></td>
				<%
					String skstm_1 = request.getParameter("skstm_1");
					if(skstm_1!=null) {
				%>
				<td><input type="text"  name="skstm" value="<%=skstm_1 %>" style="vertical-align:middle;height:30px;width:100%;border:none;text-align:center"/></td>
				<%		
					}
					else {
				%>
				<td><input type="text"  name="skstm"  value="0" style="vertical-align:middle;height:30px;width:100%;border:none;text-align:center"/></td>
				<%		
					}
				%>
			</tr>
			<tr>
				
				<td><label>MK TUGAS AKHIR/SKRIPSI</label></td>
				<td align="center">
					<input type="radio" name="kdwpl" value="R" checked>Tidak &nbsp&nbsp&nbsp
  					<input type="radio" name="kdwpl" value="S" > Ya<br>
				</td>
				<%
				
					
				%>	
				
				<!--  td><label>KELOMPOK MK</label></td>
				<td><select name="kdwpl" style="vertical-align:middle;width:100%;height:25px;border:none;text-align-last:center;border-radius: 10px;">
				<%
					String kdwpl_1=request.getParameter("kdwpl_1");
					String kode_ = "";
					String keter_ = "";
					if(kdwpl_1!=null) {
						st = new StringTokenizer(kdwpl_1,",");
						if(st.countTokens()>=2) {
							kode_ = st.nextToken();
							keter_ = st.nextToken();
						}
						else {
							kode_ = "null";
							keter_ = "null";
						}
					}
					else {
						kdwpl_1="null";
					}
					
					st= new StringTokenizer(Constants.getListKdwpl(),",");
					while(st.hasMoreTokens()){
						String brs = st.nextToken();
						StringTokenizer st1 = new StringTokenizer(brs);
						String kode = st1.nextToken();
						String keter = st1.nextToken();
						if(kode_.equalsIgnoreCase(kode)) {
						%>
						<option value="<%=kode%>" selected><%=keter %></option>
						<%
						}
						else {
						%>
						<option value="<%=kode%>"><%=keter %></option>
						<%
						}
						
					}
				%>
				</select>
				</td -->
				<td><label>SKS PRAKTEK</label></td>
				<%
					String skspr_1 = request.getParameter("skspr_1");
					if(skspr_1!=null) {
				%>
				<td><input type="text"  name="skspr" value="<%=skspr_1 %>" style="vertical-align:middle;height:30px;width:100%;border:none;text-align:center"/></td>
				<%		
					}
					else {
				%>
				<td><input type="text"  name="skspr" value="0" style="vertical-align:middle;height:30px;width:100%;border:none;text-align:center"/></td>
				<%		
					}
				%>
			</tr>
			<tr>
				<td><label>NODOS PENGAMPU</label></td>
				<%
				
					String nodos_1=request.getParameter("nodos_1");
					String nmdos = sdb.getNmdosFromMsdos(nodos_1);
					if(nmdos==null) {
						if(nodos_1==null || nodos_1.equalsIgnoreCase("null")) {
							%>
				<td><input type="text" name="nodos" style="vertical-align:middle;height:30px;width:100%;border:none"/></td>
							<%
						}
						else {
						%>
				<td><input type="text" value="<%=nodos_1 %>" name="nodos" style="vertical-align:middle;height:30px;width:100%;border:none"/></td>
						<%
						}
					}
					else {
					%>
				<td><input type="text" name="nodos" value="<%=nmdos %>" style="vertical-align:middle;height:30px;width:100%;border:none"/></td>
				<%
					}
				%>
				<td><label>SKS LAPANGAN</label></td>
				<%
					String skslp_1 = request.getParameter("skslp_1");
					if(skslp_1!=null) {
				%>
				<td><input type="text"  name="skslp" value="<%=skslp_1 %>" style="vertical-align:middle;height:30px;width:100%;border:none;text-align:center"/></td>
				<%		
					}
					else {
				%>
				<td><input type="text"  name="skslp"  value="0" style="vertical-align:middle;height:30px;width:100%;border:none;text-align:center"/></td>
				<%		
					}
				%>
			</tr>
			<tr>
				<td style="vertical-align:middle;background:#369;color:#fff;text-align:center" colspan="2"><b>METODE PELAKSANAAN KULIAH</b></td>
				<td><label>SKS SIMULASI</label></td>
				<%
					String sksim_1 = request.getParameter("sksim_1");
					if(sksim_1!=null) {
				%>
				<td><input type="text"  name="sksim" value="<%=sksim_1 %>" style="vertical-align:middle;height:30px;width:100%;border:none;text-align:center"/></td>
				<%		
					}
					else {
				%>
				<td><input type="text"  name="sksim"  value="0" style="vertical-align:middle;height:30px;width:100%;border:none;text-align:center"/></td>
				<%		
					}
				%>
			</tr>
			<tr>
				<td style="vertical-align:middle;background:white;color:#000;text-align:center" colspan="2">
				<select name="metode_kuliah" style="vertical-align:middle;width:100%;height:30px;border:none;text-align-last:center">
				<%
				Vector v_mtd = Getter.getMetodePelaksanaanKuliah();
				ListIterator lim=v_mtd.listIterator();
				while(lim.hasNext()) {
					String metode = (String)lim.next();
				%>
					<option value="<%=metode %>"><%=metode %></option>
				<%	
				}
				%>
				</select>
				</td>
				<!--  td><label>STATUS MK</label></td>
				<td><select name="sttmk" style="vertical-align:middle;width:100%;height:25px;border:none;text-align-last:center;border-radius: 10px;">
				<%
				String stkmk_1=request.getParameter("stkmk_1");
				kode_ = "";
				keter_ = "";
				if(stkmk_1!=null) {
					st = new StringTokenizer(stkmk_1,",");	
					if(st.hasMoreTokens()) {
						kode_ = st.nextToken();
						keter_ = st.nextToken();
					}	
				}	
			
				st= new StringTokenizer(Constants.getListStatusMk(),",");
				while(st.hasMoreTokens()){
					String brs = st.nextToken();
					StringTokenizer st1 = new StringTokenizer(brs);
					String kode = st1.nextToken();
					String keter = st1.nextToken();
					if(kode_.equalsIgnoreCase(kode)) {
					%>
					<option value="<%=kode%>" selected><%=keter %></option>
					<%
					}
					else {
					%>
					<option value="<%=kode%>"><%=keter %></option>
					<%
					}
				}
				%>
				</select>
				</td-->
				<td><label>SKS LAB</label></td>
				<%
					String skslb_1 = request.getParameter("skslb_1");
					if(skslb_1!=null) {
				%>
				<td><input type="text"  name="skslb" value="<%=skslb_1 %>" style="vertical-align:middle;height:30px;width:100%;border:none;text-align:center"/></td>
				<%		
					}
					else {
				%>
				<td><input type="text"  name="skslb"  value="0" style="vertical-align:middle;height:30px;width:100%;border:none;text-align:center"/></td>
				<%		
					}
				%>
			</tr>
			<!--  tr>
				<td style="vertical-align:middle;background:#369;color:#fff;text-align:center" colspan="2"><b>DOSEN PENGAMPU</b></td>
				<td style="vertical-align:middle;background:#369;color:#fff;text-align:center" colspan="2"><b>METODE PELAKSANAAN KULIAH</b></td>
			</tr>
			<tr>
				<td><label>NODOS PENGAMPU</label></td>
				<%
				
					//String nodos_1=request.getParameter("nodos_1");
					//String nmdos = sdb.getNmdosFromMsdos(nodos_1);
					if(nmdos==null) {
						if(nodos_1==null || nodos_1.equalsIgnoreCase("null")) {
							%>
				<td><input type="text" name="nodos" style="vertical-align:middle;height:30px;width:100%;border:none"/></td>
							<%
						}
						else {
						%>
				<td><input type="text" value="<%=nodos_1 %>" name="nodos" style="vertical-align:middle;height:30px;width:100%;border:none"/></td>
						<%
						}
					}
					else {
					%>
				<td><input type="text" name="nodos" value="<%=nmdos %>" style="vertical-align:middle;height:30px;width:100%;border:none"/></td>
					<%			
					}
				%>	
				<td style="vertical-align:middle;background:white;color:#000;text-align:center" colspan="2"><b>-- RESERVED --</b></td>
			</tr-->
			<tr>
				<td style="vertical-align:middle;padding:5px 5px;background:#369;color:#fff;text-align:center;" colspan="4"><input type="submit" value="Next" style="vertical-align:middle;height:35px;width:50%;text-align:center"/></td>
			</tr>
		</table>
		</form>
		</P>
		<br/>
		<p>
		<%
			Vector vMakul = (Vector)request.getAttribute("vMakul");
			if(vMakul!=null && vMakul.size()>0) {
				ListIterator li = vMakul.listIterator();
				if(li.hasNext()){
					//SearchDb sdb = new SearchDb();
					int i=0;		
			%>
			<!--  table align="center" border="1" style="vertical-align:middle;color:#000;width:800px;background:#d9e1e5;"-->	
			<table class="table" style="vertical-align:middle;width:90%;background:white;border-left: 0px solid #FFFFFF;border-bottom: 0px solid #FFFFFF;border-top: 0px solid #FFFFFF;border-right: 0px solid #FFFFFF;">
			<tr style="vertical-align:middle;background:white">
				<td style="vertical-align:middle;background:#369;color:#fff;text-align:center;font-size:1.5em" colspan="8"><b>LIST MATAKULIAH <%=nama_prodi %></b></td>
			</tr>
			<tr style="vertical-align:middle;background:white">
				<td style="vertical-align:middle;width:5%;text-align:center;background:#87B1DA"><b>NO.</b></td>
				<td style="vertical-align:middle;width:10%;text-align:center;background:#87B1DA"><b>KODE</b></td>
				<td style="vertical-align:middle;width:35%;;background:#87B1DA"><b>NAMA</b></td>
				<td style="vertical-align:middle;width:5%;text-align:center;background:#87B1DA"><b>SKS</b></td>
				<td style="vertical-align:middle;width:13%;text-align:center;background:#87B1DA"><b>KELOMPOK</b></td>
				<td style="vertical-align:middle;width:12%;text-align:center;background:#87B1DA"><b>JENIS</b></td>
				<td style="vertical-align:middle;width:10%;text-align:center;background:#87B1DA"><b>DOSEN</b></td>
				<td style="vertical-align:middle;width:10%;text-align:center;background:#87B1DA"><b>STATUS</b></td>
			</tr>
					<%
					while(li.hasNext()) {
						String baris = (String)li.next();
						//System.out.println("baris ="+baris);
						st = new StringTokenizer(baris,",");
						String idkmk1 = st.nextToken();
						String kdpst1 = st.nextToken();
						kdpst1 = kdpst1.replaceAll("null","");
						String kdkmk1 = st.nextToken();
						kdkmk1 = kdkmk1.replaceAll("null","");
						String nakmk1 = st.nextToken();
						if(nakmk1==null) {
							nakmk1="Harap Di-Update";
						}
						nakmk1 = nakmk1.replaceAll("null","");
						String sksmk1 = st.nextToken();
						sksmk1 = sksmk1.replaceAll("null","");
						String skstm1 = st.nextToken();
						skstm1 = skstm1.replaceAll("null","");
						String skspr1 = st.nextToken();
						skspr1 = skspr1.replaceAll("null","");
						String skslp1 = st.nextToken();
						skslp1 = skslp1.replaceAll("null","");
						String kdwpl1 = st.nextToken();
						String keter_kdwpl1 = "";
						kdwpl1 = kdwpl1.replaceAll("null","");
						String jenis1 = st.nextToken();
						String keter_jenis1 = "";
						jenis1 = jenis1.replaceAll("null","");
						String nodos1 = st.nextToken();
						nodos1 = nodos1.replaceAll("null","");
						String stkmk1 =st.nextToken();
						stkmk1 = stkmk1.replaceAll("null","");
						//if(st.hasMoreTokens()) {
						//	stkmk = st.nextToken();
						//	stkmk = stkmk.replaceAll("null","");
						//	}	
						String keter_stkmk1="";
						//proses keter kdwpl
						String list_kdwpl = Constants.getListKdwpl();
						st = new StringTokenizer(list_kdwpl,",");
						boolean match = false;
						while(st.hasMoreTokens()&& !match) {
							String tokn = st.nextToken();
							StringTokenizer stt = new StringTokenizer(tokn);
							String kode = stt.nextToken();
							String keter = stt.nextToken();
							if(kode.equalsIgnoreCase(kdwpl1)){
								match = true;
								keter_kdwpl1 = ""+keter;
							}
						}
						//proses keter jenis
						String listJenisMk = Constants.getListJenisMk();
						st = new StringTokenizer(listJenisMk,",");
						match = false;
						while(st.hasMoreTokens()&& !match) {
							String tokn = st.nextToken();
							StringTokenizer stt = new StringTokenizer(tokn);
							String kode = stt.nextToken();
							String keter = stt.nextToken();
							if(kode.equalsIgnoreCase(jenis1)){
								match = true;
								keter_jenis1 = ""+keter;
							}
						}
						//proses keter status
						String listStatusMk = Constants.getListStatusMk();
						st = new StringTokenizer(listStatusMk,",");
						match = false;
						while(st.hasMoreTokens()&& !match) {
							String tokn = st.nextToken();
							StringTokenizer stt = new StringTokenizer(tokn);
							String kode = stt.nextToken();
							String keter = stt.nextToken();
							if(kode.equalsIgnoreCase(stkmk1)){
								match = true;
								keter_stkmk1 = ""+keter;
							}
						}
						//proses nama dosen;
						String nmdos1 = sdb.getNmdosFromMsdos(nodos1);
						if(nmdos1==null) {
							nmdos1 = ""+nodos1;
						}
					%>
				<tr>	
					<%
					//String target = Constants.getRootWeb()+"/InnerFrame/Akademik/listKelas.jsp";
					//String uri = request.getRequestURI();
					//System.out.println(target+" / "+uri);
					//String url = PathFinder.getPath(uri, target);
					%>
					<form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/listKelas.jsp" method="post">
					<input type="hidden" name="idkmk_" value="<%=""+idkmk1 %>" />
					<td style="vertical-align:middle;width:5%;text-align:center;background:#d9e1e5;"><b><%=++i %><input type="hidden" value="<%=kdpst_nmpst%>" name="kdpst_nmpst"/></b></td>
					<td style="vertical-align:middle;width:10%;text-align:center;background:#d9e1e5;"><b><%=kdkmk1 %><input type="hidden" value="<%=kdkmk1%>" name="kdkmk_1"/></b></td>
					<td style="vertical-align:middle;width:35%;background:#d9e1e5;"><b><%=nakmk1.replace("$", ",") %><input type="hidden" value="<%=nakmk1.replace("$",",")%>" name="nakmk_1" /></b></td>
					<td style="vertical-align:middle;width:5%;text-align:center;background:#d9e1e5;"><b><%=sksmk1 %><input type="hidden" value="<%=skstm1%>" name="skstm_1" /><input type="hidden" value="<%=skspr1%>" name="skspr_1" /><input type="hidden" value="<%=skslp1%>" name="skslp_1" /></b></td>
					<td style="vertical-align:middle;width:13%;text-align:center;background:#d9e1e5;"><b><%=keter_kdwpl1 %><input type="hidden" value="<%=kdwpl1%>,<%=keter_kdwpl1 %>" name="kdwpl_1" /></b></td>
					<td style="vertical-align:middle;width:12%;text-align:center;background:#d9e1e5;"><b><%=keter_jenis1 %><input type="hidden" value="<%=jenis1%>,<%=keter_jenis1%>" name="jenis_1" /></b></td>
					<td style="vertical-align:middle;width:10%;text-align:center;background:#d9e1e5;"><b><%=nmdos1 %><input type="hidden" value="<%=nodos1%>" name="nodos_1" /></b></td>
					<td style="vertical-align:middle;width:10%;text-align:center;background:#d9e1e5;"><b><%=keter_stkmk1 %><input type="hidden" value="<%=stkmk1%>,<%=keter_stkmk1 %>" name="stkmk_1" /></b></td>
					<%
					if(allowEditListKelas==true && (keter_stkmk1!=null && !keter_stkmk1.equalsIgnoreCase("AKTIF") && !keter_stkmk1.equalsIgnoreCase("HAPUS"))) {
					%>
					<td style="vertical-align:middle;background:white;border-left: 1px solid #ddd;border-bottom: 0px solid #FFFFFF;border-top: 0px solid #FFFFFF;border-right: 0px solid #FFFFFF;">
						<div class="tooltip">
						<button type="submit"  value="edit" style="vertical-align:middle;height:50%;width:20px;border:none;background:#FFF;"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/edit.png" alt="edit" width="20px" height="20px"><span class="tooltiptext">Edit Matakuliah</span></button>
						</div>
					</td>
					<%	
					}
					%>
					</form>
					<%
					//allowHapusMakul
					Vector vHapus = validUsr.getScopeUpd7des2012("allowHapusMakul");
					if(vHapus!=null && vHapus.size()>0 && (keter_stkmk1!=null && !keter_stkmk1.equalsIgnoreCase("AKTIF") && !keter_stkmk1.equalsIgnoreCase("HAPUS"))) {
					%>
					<form action="go.aktifkanMakul" method="post">
					<input type="hidden" name="idkmk_" value="<%=""+idkmk1 %>" />
					<input type="hidden" value="<%=kdpst_nmpst%>" name="kdpst_nmpst"/>
					<td style="vertical-align:middle;background:white;border-left: 0px solid #FFFFFF;border:none;border-bottom: 0px solid #FFFFFF;border-top: 0px solid #FFFFFF;border-right: 0px solid #FFFFFF;"> 
						<div class="tooltip">
						<button type="submit"  value="aktif" style="vertical-align:middle;height:50%;width:20px;border:none;background:#FFF;"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/checked.png" alt="aktif" width="20px" height="20px"><span class="tooltiptext">Aktifkan Matakuliah</span></button>
						</div>
					</td>
					</form>
					
					<form action="go.hapusMakul"  method="post">
					
					<input type="hidden" name="idkmk_" value="<%=""+idkmk1 %>" />
					<input type="hidden" value="<%=kdpst_nmpst%>" name="kdpst_nmpst"/>
					<td style="vertical-align:middle;background:white;border-left: 0px solid #FFFFFF;border:none;border-bottom: 0px solid #FFFFFF;border-top: 0px solid #FFFFFF;border-right: 0px solid #FFFFFF;"> 
						<div class="tooltip">
						<button type="submit" onclick="return confirm('Anda yakin untuk menghapus matakuliah ini?');" value="hapus" style="vertical-align:middle;height:50%;width:20px;border:none;background:#FFF;"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/delete.png" alt="hapus" width="20px" height="20px"><span class="tooltiptext">Hapus Matakuliah</span></button>
						</div>
					</td>
					
					</form>
					<%	
					}
					%>
					
				</tr>	
					<%	
						
					}
					%>
			</table>		
					<%
				}
			}
		%>
		</p>
	</div>
</div>		
</body>
</html>
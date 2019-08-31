<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.dbase.SearchDb" %>
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
<%
	request.setAttribute("atPage", "listMakur");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String kdpst_nmpst = request.getParameter("kdpst_nmpst");
	String idkur_ = ""+request.getParameter("idkur");
	StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
	String kdpst = st.nextToken();
	String nmpst = "";
	while(st.hasMoreTokens()) {
		nmpst = nmpst+st.nextToken();
		if(st.hasMoreTokens()) {
			nmpst = nmpst+" ";
		}
	}
	//SearchDb sdb = new SearchDb();
	boolean allowEditListKelas = false;
	String callerPage = request.getParameter("callerPage");
	Vector vInfoMakur = (Vector)request.getAttribute("vInfoMakur");
	String nama_prodi = Converter.getNamaKdpst(kdpst);
	String err_msg = (String)session.getAttribute("err");
	session.removeAttribute("err");
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
		allowEditListKelas = true;
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
		TAMBAH / EDIT MATAKULIAH KURIKULUM
	</section>
		<br />
		<!-- Column 1 start -->
		<%
		if(!Checker.isStringNullOrEmpty(err_msg)) {
		%>
		<div style="padding:0 0 0 10px;color:red;font-weight:bold;font-style:italic">
			Kurikulum Gagal Di Aktifkan:<br>
			<%=err_msg %><br>
		</div>
		<%	
		}
		%>
		<br />
		<p>
		
		<%
		if(idkur_==null || idkur_.equalsIgnoreCase("null")) {
		%>
		<table class="table" style="vertical-align:middle;width:90%;background:white;border-left: 0px solid #FFFFFF;border-bottom: 0px solid #FFFFFF;border-top: 0px solid #FFFFFF;border-right: 0px solid #FFFFFF;">
			<tr>
				<td style="vertical-align:middle;background:#369;color:#fff;text-align:center;font-size:1.5em" colspan="6"><b>FORM EDIT MATAKULIAH DALAM KURIKULUM <br/><%= nama_prodi %></b></td>
			</tr>
			<tr>
				
				<td style="vertical-align:middle;width:40%;text-align:left;background:#87B1DA;">NAMA/KODE KURIKULUM</td>	
				<td style="vertical-align:middle;width:10%;text-align:center;background:#87B1DA;">TOTAL MATAKULIAH</td>
				<td style="vertical-align:middle;width:10%;text-align:center;background:#87B1DA;">KURIKULUM<br>TOTAL SKS</td>
				<td style="vertical-align:middle;width:10%;text-align:center;background:#87B1DA;">TOTAL SKS<br>WAJIB</td>
				<td style="vertical-align:middle;width:10%;text-align:center;background:#87B1DA;">TOTAL SKS<br>PILIHAN</td>
				<td style="vertical-align:middle;width:25%;text-align:center;background:#87B1DA;">STATUS</td>
			</tr>	
			
			<%
			ListIterator li=vInfoMakur.listIterator();
			if(li.hasNext()) {	
			
			%>
			
				
		<%
		
				while(li.hasNext()) {
					String baris1 = (String)li.next();
					//idkur+" "+start+" "+ended+" "+stkur+" "+skstt+" "+smstt+" "+skswj+" "+sksop);
					st = new StringTokenizer(baris1);
					String idkur = st.nextToken();
					String start = st.nextToken();
					String ended = st.nextToken();
					String stkur = st.nextToken();
					String skstt_manual = st.nextToken();
					String smstt_manual = st.nextToken();
					String skswj_manual = st.nextToken();
					String sksop_manual = st.nextToken();
					String nmkur = (String)li.next();
					
					//System.out.println(baris1);
					//System.out.println(nmkur);
					Vector vInfo = (Vector)li.next();
					String ttkls = (String)li.next();
					String skstt = (String)li.next();
					String skstt_wajib = (String)li.next();
					String skstt_pilihan = (String)li.next();
		%>
			<!--  form action="get.listMakur" method="post" -->
			
			<tr>
				<input type="hidden" name="idkur" value="<%=idkur%>" />
				<td valign="middle" style="vertical-align:middle" width="50%">&nbsp&nbsp<%=nmkur.toUpperCase() %></td>	
				<td valign="middle" style="vertical-align:middle;text-align:center;background:#d9e1e5;"><%=ttkls %></td>
				<td valign="middle" style="vertical-align:middle;text-align:center;background:#d9e1e5;">
				<%
				String tkn_status_krklm = Constants.getListStatusKurikulum();
				StringTokenizer st1 = new StringTokenizer(tkn_status_krklm,",");
				String kode_stkur = "";
				String keter_stkur = "";
				boolean match = false;
				while(st1.hasMoreTokens()&&!match) {
					String token = st1.nextToken();
					StringTokenizer st2 = new StringTokenizer(token);
					kode_stkur = st2.nextToken();
					keter_stkur = st2.nextToken();
					if(kode_stkur.equalsIgnoreCase(stkur)) {
						match = true;
					}
				}
				if(kode_stkur.equalsIgnoreCase("A")||kode_stkur.equalsIgnoreCase("E")||kode_stkur.equalsIgnoreCase("H")) {
					out.print(skstt);
				}
				else {
					out.print(skstt+"/"+skstt_manual);
				}
				%>
				</td>
				
				<td valign="middle" style="vertical-align:middle;text-align:center;background:#d9e1e5;">
				<%
				if(kode_stkur.equalsIgnoreCase("A")||kode_stkur.equalsIgnoreCase("E")||kode_stkur.equalsIgnoreCase("H")) {
					out.print(skswj_manual);
				}
				else {
					out.print(skstt_wajib+"/"+skswj_manual);
				}
				%>
				</td>
				<td valign="middle" style="vertical-align:middle;text-align:center;background:#d9e1e5;">
				<%
				if(kode_stkur.equalsIgnoreCase("A")||kode_stkur.equalsIgnoreCase("E")||kode_stkur.equalsIgnoreCase("H")) {
					out.print(sksop_manual);
				}
				else {
					out.print(skstt_pilihan+"/"+sksop_manual);
				}
				%>
				</td>
				<td valign="middle" style="vertical-align:middle;text-align:center;background:#d9e1e5;">
				<%
				
				if(!match) {
			%>
				N/A
			<%	
				}
				else  {
			%>
				<%=keter_stkur %>	
			<%
				}
			%>	
				</td>	
			<%
				if(!kode_stkur.equalsIgnoreCase("A")&&!kode_stkur.equalsIgnoreCase("E")&&!kode_stkur.equalsIgnoreCase("H")) {
			%>	
				<form action="go.listMakur" method="post">
					<input type="hidden" value="<%=kdpst %>,<%=nmpst %>" name="kdpst_nmpst" />
					<input type="hidden" name="idkur" value="<%=idkur%>" />		
				<td style="vertical-align:middle;background:white;border-left: 1px solid #ddd;border-bottom: 0px solid #FFFFFF;border-top: 0px solid #FFFFFF;border-right: 0px solid #FFFFFF;">
					<div class="tooltip">
						<button type="submit" value="edit" style="vertical-align:middle;height:50%;width:20px;border:none;background:#FFF;"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/edit.png" alt="edit" width="20px" height="20px"><span class="tooltiptext">Edit Kurikulum</span></button>
					</div>
				</td>
				</form>
				<form action="go.aktifkanKrklm" method="post">
					<input type="hidden" value="<%=kdpst %>,<%=nmpst %>" name="kdpst_nmpst" />
					<input type="hidden" name="idkur_" value="<%=idkur%>" />		
					<td style="vertical-align:middle;background:white;border-left: 0px solid #FFFFFF;border:none;border-bottom: 0px solid #FFFFFF;border-top: 0px solid #FFFFFF;border-right: 0px solid #FFFFFF;"> 
						<div class="tooltip">
						<button type="submit" onclick="return confirm('Pastikan seluruh matakuliah kurikulum sudah benar. Kurikulum yg aktif tidak dapat di-edit, Anda yakin untuk meng-Aktifkan kurikulum ini sekarang?');" value="aktif" style="vertical-align:middle;height:50%;width:20px;border:none;background:#FFF;"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/checked.png" alt="aktif" width="20px" height="20px"><span class="tooltiptext">Aktifkan Kurikulum</span></button>
						</div>
					</td>
				</form>
			<%
				}
				else {
			%>	

			<%		
				}
			%>
			</tr>
			<!--  /form -->
		<%	
				}
			}
			else {
			%>
			<tr>
				<td colspan="3">TIDAK ADA KURIKULUM AKTIF UNTUK SAAT INI</td>	
			</tr>
		<%	
			}
		%>
			
		</table>
		
		<%
		}
		else {
			Vector vJenisMakul = Getter.getListJenisMakul();
			ListIterator lijm = null;
			//System.out.println("okezone");
			//System.out.println("vJenisMakul="+vJenisMakul.size());
			Vector vInfoKelasAktif = (Vector) request.getAttribute("vInfoKelasAktif");
			ListIterator li = vInfoKelasAktif.listIterator();
			ListIterator li0=vInfoMakur.listIterator();
			String baris1 = (String)li0.next();
			////System.out.println("okezone1");
			st = new StringTokenizer(baris1);
			//li.add(idkur+" "+start+" "+ended+" "+stkur+" "+skstt+" "+smstt+" "+skswj+" "+sksop);
			
			String idkur = st.nextToken();
			String start = st.nextToken();
			String ended = st.nextToken();
			String stkur = st.nextToken();
			String skstt_value = st.nextToken();
			String smstt_value = st.nextToken();
			String skswj_value = st.nextToken();
			String sksop_value = st.nextToken();
			String nmkur = (String)li0.next();
			Vector vInfo = (Vector)li0.next();
			String ttkls = (String)li0.next();
			String skstt = (String)li0.next();
			//System.out.println("okezone3");
			%>
			<form action="get.listMakur" method="post">
			<input type="hidden" name="idkur" value="<%=idkur%>" />
			<input type="hidden" name="update1" value="yes" /><input type="hidden" value="<%=kdpst %>,<%=nmpst %>" name="kdpst_nmpst" />
			
			<!--  table align="center" border="1" style="vertical-align:middle;color:#000;width:800px;background:#d9e1e5;" -->	
			<table class="table" style="vertical-align:middle;width:90%;background:#d9e1e5;border-left: 0px solid #FFFFFF;border-bottom: 0px solid #FFFFFF;border-top: 0px solid #FFFFFF;border-right: 0px solid #FFFFFF;">
			<tr>
				<td style="vertical-align:middle;background:#369;color:#fff;text-align:center;font-size:1.5em" colspan="8"><b>FORM TAMBAH / EDIT MATAKULIAH <br/>KURIKULUM <%=nmkur %><br/> PROGRAM <%=nmpst %></b></td>
			</tr>
			<tr>
				<td style="vertical-align:middle;width:10%;text-align:left;background:#87B1DA;padding:0 0 0 5px">KODE MK</td>
				<td style="vertical-align:middle;width:60%;text-align:left;background:#87B1DA;padding:0 0 0 10px">NAMA MATAKULIAH</td>	
				<td style="vertical-align:middle;width:5%;text-align:center;background:#87B1DA;">SKS</td>
				<td style="vertical-align:middle;width:5%;text-align:center;background:#87B1DA;">
				<div class="tooltip">
						MK<br>K.O.
						<span class="tooltiptext">Centang untuk memasukan matakuliah ini ke dalam kurikulum</span></button>
				</div>
				</td>
				<td style="vertical-align:middle;width:5%;text-align:center;background:#87B1DA;">
				<div class="tooltip">
						MK<br>sms
						<span class="tooltiptext">Matakuliah semester berapakah ini menurut K.O?</span></button>
				</div>
				</td>
				<td style="vertical-align:middle;width:5%;text-align:center;background:#87B1DA;">
				<div class="tooltip">
						MK<br>WAJIB
						<span class="tooltiptext">Centang bila MK wajib</span></button>
				</div>
				
				</td>
				<td style="vertical-align:middle;width:10%;text-align:center;background:#87B1DA;">MK<br/>AKHIR</td>
			</tr>
			<%
			if(li.hasNext()) {
				
				while(li.hasNext()) {
					String idkmk=(String)li.next();
					String kdkmk=(String)li.next();
					String nakmk=(String)li.next();
					String skstm=(String)li.next();
					String skspr=(String)li.next();
					String skslp=(String)li.next();
					String kdwpl=(String)li.next();
					String jenis=(String)li.next();
					String nodos=(String)li.next();
					String skslb=(String)li.next();
					String sksim=(String)li.next();
				%>
			<tr>
				<input type="hidden" name="idkmk_" value=<%=idkmk %> />
				<td style="vertical-align:middle;text-align:left;padding:0 0 0 5px"><%=kdkmk.toUpperCase() %></td>
				<td style="vertical-align:middle;text-align:left;padding:0 0 0 10px"><%=nakmk.toUpperCase() %></td>	
				<td style="vertical-align:middle;text-align:center;"><%=""+(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %></td>
				<%
				/*
				%>
				<td style="vertical-align:middle;text-align:center;"> 
				<select name="jenisMakul" style="border:none;height:30px;width:100%;text-align-last:center">
					
				<%
				
				if(vJenisMakul!=null && vJenisMakul.size()>0) {
				//if(false) {	
					lijm = vJenisMakul.listIterator();
					
					while(lijm.hasNext()) {
						String brs = (String)lijm.next();
						StringTokenizer stt = new StringTokenizer(brs,"`");
						String id = stt.nextToken();
						String keter = stt.nextToken();
						String kode = stt.nextToken();
						if(kode.equalsIgnoreCase(jenis)) {
				%>
					<option value="<%=idkmk%>`<%=kdkmk %>`<%=nakmk %>`<%=skstm %>`<%=skspr %>`<%=skslp %>`<%=kdwpl %>`<%=kode %>" selected="selected"><%=keter %></option>
				<%		
						}
						else {
				%>
					<option value="<%=idkmk%>`<%=kdkmk %>`<%=nakmk %>`<%=skstm %>`<%=skspr %>`<%=skslp %>`<%=kdwpl %>`<%=kode %>"><%=keter %></option>
				<%		
						}
						
					}
				}
				else {
				%>
					<option value="<%=idkmk%>`<%=kdkmk %>`<%=nakmk %>`<%=skstm %>`<%=skspr %>`<%=skslp %>`<%=kdwpl %>`<%=jenis %>" selected="selected">
					<%
					//if(jenis.equalsIgnoreCase("0")) {
						out.print("ERROR: BELUM ADA PILIHAN");
					//}
					//else {
					//	out.print(jenis);
					//}
					%>
					</option>
				<%
				}
				%>
				</select>
				</td>
				
					<%
				*/	
					boolean mk_wajib = false;
					boolean match = false;
					boolean finalMk = false;
					if(vInfo!=null && vInfo.size()>0) {
						//System.out.println("vSize = "+vInfo.size());
						ListIterator linfo = vInfo.listIterator();
						//liTmp.add(idkmk+","+sksmk+","+semes+","+nakmk+","+mk_akhir);
						String smsmk = "";
						while(linfo.hasNext() && !match) {
							String baris = (String)linfo.next();
							StringTokenizer st1 = new StringTokenizer(baris,",");
						//liTmp.add(idkmk+" "+sksmk+" "+nakmk);
							String idkmk_1= st1.nextToken();
							//System.out.println(idkmk_1);
							String sksmk_1= st1.nextToken();
							//System.out.println(sksmk_1);
							String semes_1= st1.nextToken();
							String nakmk_1= st1.nextToken();
							String mk_akhir= st1.nextToken();
							String wajib= st1.nextToken();
							mk_wajib = Boolean.parseBoolean(wajib);
							//String pilihan= st1.nextToken();
							//liTmp.add(idkmk+","+sksmk+","+semes+","+nakmk+","+mk_akhir+","+wajib);
							
							//System.out.println(nakmk_1);
							if(Integer.valueOf(idkmk).intValue()==Integer.valueOf(idkmk_1).intValue()) {
								match=true;
								smsmk=""+semes_1;
								if(mk_akhir!=null && mk_akhir.equalsIgnoreCase("true")) {
									finalMk = true;
								}
								else {
									finalMk = false;
								}
							}
						}

						%>
				<td style="vertical-align:middle;text-align:center;">
					<div class="tooltip">
						
						<%
						if(!match) {
					%>
					<input type="checkbox" name="kdkmkInclude" value="<%=idkmk%>,<%=kdkmk %>,<%=nakmk %>,<%=skstm %>,<%=skspr %>,<%=skslp %>,<%=kdwpl %>,<%=jenis %>" />
					<%
						}
						else {
					%>
					<input type="checkbox" name="kdkmkInclude" value="<%=idkmk%>,<%=kdkmk %>,<%=nakmk %>,<%=skstm %>,<%=skspr %>,<%=skslp %>,<%=kdwpl %>,<%=jenis %>" checked/>
					<%
						}
					%>
						<span class="tooltiptext">Centang untuk memasukan ke dalam kurikulum</span>
					</div>
				</td>		
				<td style="vertical-align:middle;text-align:center;">
				<div class="tooltip">
						<input type="number" style="vertical-align:middle;width:100%;height:30px;text-align:center" name="<%=kdkmk %>-<%=idkmk %>" value="<%=smsmk.replaceAll("null","") %>"  />
						<span class="tooltiptext">Matakuliah semester berapakah ini menurut K.O.</span></button>
				</div>
				</td>
				<td style="vertical-align:middle;text-align:center;">
					<div class="tooltip">
					<%
					if(mk_wajib && match) {
					%>
						<input type="checkbox" name="kdkmkWajib" value="<%=idkmk%>,<%=kdkmk %>,<%=nakmk %>,<%=skstm %>,<%=skspr %>,<%=skslp %>,<%=kdwpl %>,<%=jenis %>" checked="checked" />
						<span class="tooltiptext">Centang bila MK wajib</span></button>
					<%
					}
					else {
						%>
						<input type="checkbox" name="kdkmkWajib" value="<%=idkmk%>,<%=kdkmk %>,<%=nakmk %>,<%=skstm %>,<%=skspr %>,<%=skslp %>,<%=kdwpl %>,<%=jenis %>" />
						<span class="tooltiptext">Centang bila MK wajib</span></button>
					<%	
					}
					%>	
					</div>	
				</td>
				<td style="vertical-align:middle;text-align:center;">
					<div class="tooltip">
						<%
						if(!finalMk) {
					%>
					<input type="checkbox" name="finalMk" value="<%=idkmk%>,<%=kdkmk %>,<%=nakmk %>,<%=skstm %>,<%=skspr %>,<%=skslp %>,<%=kdwpl %>,<%=jenis %>" />
					<%
						}
						else {
					%>
					<input type="checkbox" name="finalMk" value="<%=idkmk%>,<%=kdkmk %>,<%=nakmk %>,<%=skstm %>,<%=skspr %>,<%=skslp %>,<%=kdwpl %>,<%=jenis %>" checked/>
					<%
						}
					%>
					<span class="tooltiptext">Centang bila ini Matakuliah akhir (Penentu Kelulusan/Ajian Akhir) [Hanya 1 Untuk setiap Kurikulum]</span>
					</div>
				</td>
			</tr>
				<%
					}
					else {
					%>
				<td style="vertical-align:middle;text-align:center;">
					<div class="tooltip">
						<input type="checkbox" name="kdkmkInclude" value="<%=idkmk%>,<%=kdkmk %>,<%=nakmk %>,<%=skstm %>,<%=skspr %>,<%=skslp %>,<%=kdwpl %>,<%=jenis %>" />
						<span class="tooltiptext">Centang untuk memasukan matakuliah ini ke dalam kurikulum</span>
					</div>
				</td>
				<td style="vertical-align:middle;text-align:center;">
				<div class="tooltip">
						<input type="number" style="vertical-align:middle;width:100%;height:30px;text-align:center" name="<%=kdkmk %>-<%=idkmk %>" />
						<span class="tooltiptext">Matakuliah semester berapakah ini menurut K.O?</span></button>
				</div>
				</td>
				<td style="vertical-align:middle;text-align:center;">
				<div class="tooltip">
						<input type="checkbox" name="kdkmkWajib" value="<%=idkmk%>,<%=kdkmk %>,<%=nakmk %>,<%=skstm %>,<%=skspr %>,<%=skslp %>,<%=kdwpl %>,<%=jenis %>"/>
						<span class="tooltiptext">Centang bila MK wajib</span>
				</div>
					
				</td>
				
				<td style="vertical-align:middle;text-align:center;">
					<div class="tooltip">
						<input type="checkbox" name="finalMk" value="<%=idkmk%>,<%=kdkmk %>,<%=nakmk %>,<%=skstm %>,<%=skspr %>,<%=skslp %>,<%=kdwpl %>,<%=jenis %>" />
						<span class="tooltiptext">Centang bila ini Matakuliah akhir (Penentu Kelulusan/Ajian Akhir) [Hanya 1 Untuk setiap Kurikulum]</span>
					</div>
				</td>
			</tr>	
					<%
					}
				}
				%>
			<tr>
				<td colspan="8" style="padding:5px 5px;vertical-align:middle;height:30px;background:#369;text-align:center"><input type="submit" value="Update" style="vertical-align:middle;width:50%;height:40px;font-size:1.3em"/></td>
			</tr>	
				<%
			}
			else {
			%>
			<tr>	
				<td colspan="8">TIDAK ADA MATAKULIAH AKTIF, HARAP EDIT MATAKULIAH</td>
			</tr>	
			<% 	
			}
		%>
		</table>
		</form>
		<%
		}
		%>	
		</p>
	</div>
</div>		
</body>
</html>
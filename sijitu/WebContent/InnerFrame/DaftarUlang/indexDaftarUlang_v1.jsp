<!DOCTYPE html>
<head>

<%@ page import="beans.dbase.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.login.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.tools.PathFinder"%>
<%@ page import="beans.dbase.daftarUlang.*" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<title>Insert title here</title>
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" /><style>
.table {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
width: 90%;
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
<%


beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v_scope_edit_nim_id = validUsr.returnScopeProdiOnlySortByKampusWithListIdobj("nim");
Vector vf = (Vector)session.getAttribute("vf");
Vector v_scope_id = (Vector)session.getAttribute("v_scope_id");
String target_thsms=request.getParameter("target_thsms");
String scope_cmd=request.getParameter("scope_cmd");
String table_rule_nm=request.getParameter("table_rule_nm");
String at_kmp=request.getParameter("at_kmp");
ListIterator li3 =null;
String list_nim_bentrok = request.getParameter("list_nim_bentrok");
String root_caller = (String)request.getParameter("root_caller");
%>


</head>
<body>

<div id="header">
	<jsp:include page="indexInnerMenu.jsp" flush="true" />

</div>
<div class="colmask fullpage">
	<div class="col1">
	
	<br/>
<%
if(!Checker.isStringNullOrEmpty(list_nim_bentrok)) {
	out.print("INFO LIST NIM KONFLIK : "+list_nim_bentrok);
}
%>
	<br/>

<%

////System.out.println("dashAkademi.jsp");
boolean first = true;

if(vf!=null && vf.size()>0) {
	Vector v_list_jabatana_operator = validUsr.getListJabatan(-1);
	String list_jabatan_operator = "";
	if(v_list_jabatana_operator!=null && v_list_jabatana_operator.size()>0) {
		ListIterator li = v_list_jabatana_operator.listIterator();
		while(li.hasNext()) {
			String brs = (String)li.next();
			list_jabatan_operator = list_jabatan_operator+brs;
		}
	}
	
	
	

	ListIterator li = vf.listIterator();
	while(li.hasNext()) {
		
		String kmp = (String)li.next();
		Vector vprodi = (Vector) li.next();
		if(kmp.equalsIgnoreCase(at_kmp)) {
			//out.println(kmp+"<br/>");
			boolean ada_pengajuan = false;
			ListIterator li1 = vprodi.listIterator();
			while(li1.hasNext()) {
				li3 = v_scope_edit_nim_id.listIterator();
				boolean editor = false;
			
				String idobj = (String)li1.next();
				//CEK APA IDOBJ termasuk dalam scope user ini
				while(li3.hasNext() && !editor) {
					String brs3 = (String)li3.next();
					if(!brs3.endsWith("`")) {
						brs3 = brs3+"`";
					}
					if(brs3.contains("`"+idobj+"`")) {
						editor = true;
					}
				}
				String nmpst = Converter.getNamaKdpst(Integer.parseInt(idobj));
				//out.println("    "+idobj+"<br/>");
				Vector vmhs = (Vector)li1.next();
				if(vmhs!=null && vmhs.size()>0) {
					ada_pengajuan = true;
				
					int no = 1;
%>
<center>
<%
					if(v_scope_edit_nim_id!=null && v_scope_edit_nim_id.size()>0 && first) {
						first = false;

%>
<form action="upd.npmMhs?target_thsms=<%=target_thsms %>&at_kmp=<%=at_kmp %>" method="POST" id="update_nim"></form>
	<center style="padding:0 0 10px 0">
		<section class="gradient">
			<div style="text-align:center">
				<button formnovalidate type="submit" style="padding: 5px 10px;font-size: 20px;"form="update_nim" >UPDATE <%=Converter.getAlias("NIM") %></button>
			</div>
		</section>
	</center>
<%	
					}
%>
<br/>

<table class="table">
	<thead>
		<tr>
  			<th colspan="5" style="text-align: center; padding: 0px 10px;font-size:1.5em">LIST PENGAJUAN HEREGISTASI MHS <%=nmpst%> - <%=target_thsms %>
  			</th>
  		</tr>
  		<tr>
  			<th width="5%">NO</th>
  			<th width="10%">TIPE</th>
  			<th width="15%"><%=Converter.getAlias("NIM") %> </th>
  			<th width="30%"><%=Converter.getAlias("NPM")+" / NAMA" %></th>
  			<th width="40%">STATUS PERSETUJUAN</th>
  		</tr>
  	</thead>
<%									
					ListIterator li2 = vmhs.listIterator();
					if(li2.hasNext()) {
%>
	<tbody>
<%

						do {
							//boolean completed = false;
						
							String mhs = (String)li2.next();
							//System.out.println("mhs="+mhs);
							//tmp = kdpst+"`"+npmhs+"`"+nmmhs+"`"+reqdt+"`"+tkn_apr_hist+"`"+tkn_id_approval+"`"+tkn_job_approval+"`"+show_at_id+"`"+show_at_creator;
							StringTokenizer st = new StringTokenizer(mhs,"`");
							String kdpst = st.nextToken();
							String npmhs = st.nextToken();
							String nmmhs = st.nextToken();
							String reqdt = st.nextToken();
							String tkn_apr_hist = st.nextToken();
							//System.out.println("tkn_apr_hist="+tkn_apr_hist);
							String tkn_id_approval = st.nextToken();
							String tkn_job_approval = st.nextToken();
							String show_at_id = st.nextToken();
							String show_at_creator = st.nextToken();
							String stpid = st.nextToken();
							String asnim = st.nextToken();
							String nimhs = st.nextToken();
							
							if(stpid.equalsIgnoreCase("B")) {
								stpid ="BARU";
							}
							else if(stpid.equalsIgnoreCase("P")) {
								stpid ="PINDAHAN<br/>["+asnim+"]";
							}
							else {
								stpid ="ERROR";
							}
							boolean approvee = false;
							boolean sdh_approved = false;
							boolean next_sdh_approved = false;
							boolean all_approved = false;
							String myid = ""+ validUsr.getIdObj();
							//karena urutan harus di cek 1 per sata
							String cur_approvee_id = null;
							String cur_approvee_job = null;
							String tmp= new String(tkn_id_approval);
							String tmp1= new String(tkn_job_approval);
							//System.out.println("tmp="+tmp);
							//System.out.println("tmp1="+tmp1);
							tmp = tmp.replace("][", "`");
							tmp = tmp.replace("]", "`");
							tmp = tmp.replace("[", "`");
							tmp1 = tmp1.replace("][", "`");
							tmp1 = tmp1.replace("]", "`");
							tmp1 = tmp1.replace("[", "`");
							//System.out.println("tmp="+tmp);
							//System.out.println("tmp1="+tmp1);
							st = new StringTokenizer(tmp,"`");
							StringTokenizer st1 = new StringTokenizer(tmp1,"`");
							//cek apa sudah ada yg approveeSudah Memenuhi 
							if(tkn_apr_hist==null || Checker.isStringNullOrEmpty(tkn_apr_hist)) {
								//belum ada yg approved
								//get first approvee
								//boolean stop = false;
								//while(st.hasMoreTokens() && !stop) {
								cur_approvee_id = st.nextToken();
								cur_approvee_job = st1.nextToken();
								//System.out.println("cur_approvee_id="+cur_approvee_id);
								//System.out.println("myid="+myid);
								StringTokenizer stt = new StringTokenizer(cur_approvee_id,","); //kalo bisa > 1 orang yg approvefd dalam 1 jabatan
								boolean match = false;
								while(stt.hasMoreTokens() && !match) {
									if(myid.equalsIgnoreCase(stt.nextToken())) {
										approvee = true;
										match = true;
									}
									//if(myid.eq
								}
								
							}
							else {
								SearchDbInfoDaftarUlangTable sdd = new SearchDbInfoDaftarUlangTable(validUsr.getNpm());
								Vector v_status = sdd.cekStatusPengajuan(target_thsms, npmhs, kdpst, kmp);
								ListIterator lit = v_status.listIterator();
								if(lit.hasNext()) {
									String brs = (String)lit.next();
									//System.out.println("brs11="+brs);
									StringTokenizer st3 = new StringTokenizer(brs,"`");
									if(lit.hasNext()) {
										while(lit.hasNext()) {
											String brs1 = (String)lit.next();
											//System.out.println("brs12="+brs);
											if(brs1.equalsIgnoreCase("done")) { //1 token tapid done = all approved]
												all_approved = true; //bila di cek status bukan saat update approval terakhir
											}
											else {
												st3 = new StringTokenizer(brs1,"`");
												if(st3.countTokens()==1) {//1 token = sdh approved
													String approved_jabatan = st3.nextToken();
													if(validUsr.getListJabatan(-1).contains(approved_jabatan)) {
														sdh_approved = true;
													}
												
													//psn = psn+"<div style=\"text-align:left;font-weight:bold;font-size:1em;padding:0 0 0 90px\">"+st.nextToken()+" - Telah Diterima & Disetujui</div>";
												}
												else if(st3.countTokens()==2) {
													
													cur_approvee_job = st3.nextToken();// jabatan
													String tkn_id_needed = st3.nextToken();
													//System.out.println("tkn_id_needed="+tkn_id_needed);
													String opr_id = ""+validUsr.getIdObj();
													//System.out.println("opr_id="+opr_id);
													if(tkn_id_needed.contains("["+opr_id+"]")||tkn_id_needed.contains("["+opr_id+",")||tkn_id_needed.contains(","+opr_id+",")||tkn_id_needed.contains(","+opr_id+"]")) {
														approvee = true;
														//if(!lit.hasNext()) {//tidak ada lagi = last needed approval
														//	all_approved = true;
														//}
													}
												
													//psn = psn+"<div style=\"text-align:left;font-weight:bold;font-size:1em;padding:0 0 0 90px\">"+st.nextToken()+" - Dalam Proses Validasi</div>";
													//psn = psn+st.nextToken()+" - Dalam Proses Validasi<br/>";
												}
											}
										
										}
									}
									else {
									//belum ada yg approved
									//	psn = "Proses Pendaftaran Ulang Anda Masih Dalam Proses<br/>Menunggu Validasi dari "+brs;
									}
								
								}
							}
							
							
							
							
						//	if(tkn_id_approval.contains("["+myid+"]")||tkn_id_approval.contains("["+myid+",")||tkn_id_approval.contains(","+myid+",")||tkn_id_approval.contains(","+myid+"]")) {
						//		approvee = true;
						//		if(tkn_apr_hist.contains("["+myid+"]")) {
						//			sdh_approved = true;
						//		}
						//	}
						//System.out.println(nmmhs+"= am approve=="+approvee);
						//System.out.println(nmmhs+"= all approbved=="+all_approved);
							if(approvee) {
%>	
		
  		<tr>
			<td rowspan="1" align="center" style="vertical-align: middle; padding: 0px 5px"><%=no++ %></td>
			<td rowspan="1" align="center" style="vertical-align: middle; padding: 0px 5px"><%= stpid %></td>
			<%
								if(validUsr.isAllowTo("nim")>0) {
			
			%>
			<td rowspan="1" align="center" style="vertical-align: middle; padding: 0px 5px">
				<input type="hidden" name="npm_val" value="<%=npmhs %>" style="width:99%;text-align:center" form="update_nim"/>
				<input type="text" name="nim" value="<%=Checker.pnn(nimhs) %>" style="width:99%;text-align:center" form="update_nim"/>
			</td>
			<%	
								}
								else {
			%>
			<td rowspan="1" align="center" style="vertical-align: middle; padding: 0px 5px"><%= nimhs %></td>
			<%
								}
			%>
			<td rowspan="1" align="center" style="vertical-align: middle; padding: 0px 5px"><%="["+npmhs+"]<br/> "+nmmhs %></td>
		<%
								if(all_approved) {
									%>	
			<td rowspan="1" align="center" style="vertical-align: middle; padding: 0px 15px">
									<%							
								}
								else {
		%>	
			<td rowspan="1" align="left" style="vertical-align: middle; padding: 0px 15px">
			<%
								}
								if(Checker.isStringNullOrEmpty(tkn_apr_hist)) {
								//belum ada yg approved
			%>
			<b>Menunggu persetujuan <%=tkn_job_approval %></b>
			<%
								}
								else if(all_approved){
								//semua sudah apprve
									%>
			<b style="text-align:center">TELAH DISETUJUI</b>
											<%
								}
								else {
									String temp = new String(tkn_job_approval);
									//System.out.println("temp0="+temp);
									StringTokenizer st2 = new StringTokenizer(tkn_apr_hist,"#");
									
									int nomor=1;
									while(st2.hasMoreTokens()) {
										String npm_approvee = st2.nextToken();
										String jab_approvee = st2.nextToken();
										String updt = st2.nextToken();
										
										temp = temp.replace("["+jab_approvee+"]", "");
										//System.out.println("temp replace=["+jab_approvee+"]");
%>
				<b><%=nomor++ %>.  [<%=jab_approvee %>] - Telah Validasi</b>  [<%=updt %>]<br/>
<%				
									}
									temp = temp.replace("][", "`");
									temp = temp.replace("[", "");
									temp = temp.replace("]", "");
									//System.out.println("temp2="+temp);
									StringTokenizer st4 = new StringTokenizer(temp,"`");
									while(st4.hasMoreTokens()) {
									%>
				<b><%=nomor++ %>.  Menunggu Validasi [<%=st4.nextToken() %>]</b><br/>
						<%	
									}
								}
								if(sdh_approved) {
									
								}
								else {
				%>
				<form action="proses.updateDaftarUlang_v1">
				
				<input type="hidden" name="tkn_job_approval_needed" value="<%=tkn_job_approval %>"/>
				<input type="hidden" name="target_thsms" value="<%=target_thsms %>"/>
				<input type="hidden" name="npmhs" value="<%=npmhs %>"/>
				<input type="hidden" name="all_approved" value="<%=all_approved%>"/>
				<input type="hidden" name="show_at_mhs" value="true"/>
				<input type="hidden" name="scope_cmd" value="<%=scope_cmd %>"/>
				<input type="hidden" name="table_rule_nm" value="<%=table_rule_nm %>"/>
				<input type="hidden" name="at_kmp" value="<%=at_kmp %>"/>
				<input type="hidden" name="info_mhs" value="<%=mhs %>"/>
				<input type="hidden" name="cur_approvee_id" value="<%=cur_approvee_id %>"/>
				<input type="hidden" name="cur_approvee_job" value="<%=cur_approvee_job %>"/>
				<center style="padding:0 0 10px 0">
				<section class="gradient">
					<div style="text-align:center">
						<button formnovalidate type="submit" style="padding: 5px 10px;font-size: 20px;" >Klik, bila persyaratan sudah dipenuhi</button>
					</div>
				</section>
				</center>
				</form>
				<!--  /form -->
									<%	
								}
			%></td>
		</tr>		
<%	
							}
							else { //non approvee
								//System.out.println("tkn_apr_hist2="+tkn_apr_hist);
							
								%>	
		<tr>
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%=no++ %></td>
			<td rowspan="1" align="center" style="vertical-align: middle; padding: 0px 5px"><%= stpid %></td>
			<%
			if(validUsr.isAllowTo("nim")>0) {
			
			%>
			<td rowspan="1" align="center" style="vertical-align: middle; padding: 0px 5px">
				<input type="hidden" name="npm_val" value="<%=npmhs %>" style="width:99%;text-align:center" form="update_nim"/>
				<input type="text" name="nim" value="<%=Checker.pnn(nimhs) %>" style="width:99%;text-align:center" form="update_nim"/>
			</td>
			<%	
			}
			else {
			%>
			<td rowspan="1" align="center" style="vertical-align: middle; padding: 0px 5px"><%= nimhs %></td>
			<%
			}
			%>
			<td align="center" style="vertical-align: middle; padding: 0px 5px"><%="["+npmhs+"]<br/> "+nmmhs %></td>
			<%
			if(all_approved) {
									%>	
			<td rowspan="1" align="center" style="vertical-align: middle; padding: 0px 15px">
									<%							
								}
								else {
		%>	
			<td rowspan="1" align="left" style="vertical-align: middle; padding: 0px 15px">
			<%
								}
								
								if(Checker.isStringNullOrEmpty(tkn_apr_hist)) {
									%>
									<b>Menunggu persetujuan <%=tkn_job_approval %></b>
									<%
								}
								else if(all_approved){
									%>
									<b style="text-align:center">TELAH DISETUJUI</b>
											<%
								}
								else {
									String temp = new String(tkn_job_approval);
									//System.out.println("temp00="+temp);
									StringTokenizer st2 = new StringTokenizer(tkn_apr_hist,"#");
									
									int nomor=1;
									while(st2.hasMoreTokens()) {
										String npm_approvee = st2.nextToken();
										String jab_approvee = st2.nextToken();
										String updt = st2.nextToken();
										
										temp = temp.replace("["+jab_approvee+"]", "");
										//System.out.println("temp replace=["+jab_approvee+"]");
%>
				<b><%=nomor++ %>.  [<%=jab_approvee %>] - Telah Validasi</b>  [<%=updt %>]<br/>
<%				
									}
									temp = temp.replace("][]", "`");
									temp = temp.replace("[", "");
									temp = temp.replace("]", "");
									//System.out.println("temp2="+temp);
									StringTokenizer st4 = new StringTokenizer(temp,"`");
									while(st4.hasMoreTokens()) {
									%>
				<b><%=nomor++ %>.  Menunggu Validasi [<%=st4.nextToken() %>]</b><br/>
						<%	
									}										
								}
									%>
			</td>
		</tr>		
						<%
							}
						}
						while(li2.hasNext());
					}
%>
	</tbody>
</table>
<!--  /form-->
<%
					
				}
				
					
			}//end while
			if(!ada_pengajuan) {
			%>	
			<h1 style="text-align:center">0  PENGAJUAN<br/><br/>TIDAK ADA PENGAJUAN DAFTAR ULANG LAGI PADA SAAT INI</h1>
			 <META http-equiv="refresh" content="3;URL=get.notifications">
				
			<%		
			}
			else {
				
				if(v_scope_edit_nim_id!=null && v_scope_edit_nim_id.size()>0) {
				%>
					<br/>
					<br/>
					<center style="padding:0 0 10px 0">
						<section class="gradient">
							<div style="text-align:center">
								<button formnovalidate type="submit" style="padding: 5px 10px;font-size: 20px;"form="update_nim" >UPDATE <%=Converter.getAlias("NIM") %></button>
							</div>
						</section>
					</center>
				<%	
				}
			}
				//end
		}
		
	}
}
else {
		
}
%>

	</div>
</div>		
</body>
	
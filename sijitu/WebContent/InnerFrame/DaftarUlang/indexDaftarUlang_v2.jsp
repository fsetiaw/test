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
Vector vf = (Vector)request.getAttribute("v_list");
//Vector v_scope_id = (Vector)session.getAttribute("v_scope_id");
String target_thsms=request.getParameter("target_thsms");
String scope_cmd=request.getParameter("scope_cmd");
String table_rule_nm=request.getParameter("table_rule_nm");
String at_kmp=request.getParameter("at_kmp");
ListIterator li3 =null;
String list_nim_bentrok = request.getParameter("list_nim_bentrok");
String root_caller = (String)request.getParameter("root_caller");
String target_idobj = request.getParameter("idobj");
int my_objid = validUsr.getIdObj();
Vector v_my_list_jabatan = Getter.getListMyJabatan(my_objid);
//System.out.println("v_my_list_jabatan="+v_my_list_jabatan.size());
String tkn_jabatan_approvee = (String)request.getAttribute("tkn_jabatan_approvee");
String tkn_id_approvee = (String)request.getAttribute("tkn_id_approvee");
//System.out.println("vtkn_id_approvee="+tkn_id_approvee);
%>


</head>
<body style="background:white">

<div id="header">
	<ul>
		<li><a href="get.whoRegisterWip?root_caller=home" target="inner_iframe"">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
	</ul>	
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

//System.out.println("dashAkademi.jsp");
boolean first = true;

if(vf!=null && vf.size()>0) {

	
	
	

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
</form>	
<%	

					}
					boolean show_tombol_approval=false;
%>
<br/>
<form action="go.updApprovalRegistrasi" id="update_approval">
<input type="hidden" name="tkn_jabatan_approvee" value="<%=tkn_jabatan_approvee%>"/>
<input type="hidden" name="tkn_id_approvee" value="<%=tkn_id_approvee%>"/>

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
							      //kdpst+"`"+npmhs+"`"+nmmhs+"`"+reqdt+"`"+tkn_apr_hist+"`"+tkn_id_approval+"`"+tkn_job_approval+"`"+show_at_id+"`"+show_at_creator+"`"+stpid+"`"+asnim+"`"+nimhs+"`"+kd_kmp
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
							//boolean approvee = true;
							//boolean sdh_approved = false;
							//boolean next_sdh_approved = false;
							//boolean all_approved = false;
							
							
%>	
		
  		<tr>
			<td rowspan="1" align="center" style="vertical-align: middle; padding: 0px 5px"><%=no++ %></td>
			<td rowspan="1" align="center" style="vertical-align: middle; padding: 0px 5px"><%= stpid %></td>
			<td rowspan="1" align="center" style="vertical-align: middle; padding: 0px 5px">
				<input type="hidden" name="npm_val" value="<%=npmhs %>" style="width:99%;text-align:center" form="update_nim"/>
				<input type="text" name="nim" value="<%=Checker.pnn(nimhs) %>" style="width:99%;text-align:center" form="update_nim"/>
			</td>
			<td rowspan="1" align="center" style="vertical-align: middle; padding: 0px 5px"><%="["+npmhs+"]<br/> "+nmmhs %></td>	
			<td rowspan="1" align="center" style="vertical-align: middle; padding: 0px 15px">
				<b>
<%				
							String wait_for_whom =Checker.needApprovalFromWhomToHeregistrasi(tkn_job_approval, tkn_apr_hist);
							//System.out.println("wait_for_whom="+wait_for_whom);
							if(Checker.isStringNullOrEmpty(wait_for_whom)&&!Checker.isStringNullOrEmpty(tkn_job_approval)) {
								out.print("Telah Disetujui <br> Proses Heregistrasi Selesai");
							}
							else {
								int j=1;
%>
				<table style="width:100%;border:none">
<%					
								st = new StringTokenizer(wait_for_whom,"`");
								while(st.hasMoreTokens()) {
%>
					<tr>
<%
									String job = st.nextToken();
						//out.print("Menunggu Persetujuan "+job);
									if(st.hasMoreTokens()) {
									//cuma tabelnua ada border bawahnya ato ngga
%>
					
						<td style="text-align:center;width:10%;border:none;border-bottom:1px solid #369"><%=j++ %>.</td>
<%
										if(Checker.amI(job, v_my_list_jabatan,target_idobj)) {
											show_tombol_approval=true;
%>						
						<td style="text-align:left;width:60%;border:none;border-bottom:1px solid #369">Menunggu Persetujuan <%=job %></td>
						<td style="text-align:left;width:30%;border:none;border-bottom:1px solid #369;vertical-align:middle">	
							<input type="checkbox" name="approval" value="<%=target_idobj %>~<%=npmhs %>~<%=job %>~<%=wait_for_whom %>~<%=my_objid %>" />
						</td>
<%							
										}
										else {
							%>						
						<td colspan="2" style="text-align:left;border:none;;border-bottom:1px solid #369">Menunggu Persetujuan <%=job %></td>
	<%	
										}	
									}
									else {
										%>
										
						<td style="text-align:center;width:10%;border:none;"><%=j++ %>.</td>
				<%
										if(Checker.amI(job, v_my_list_jabatan,target_idobj)) {
											show_tombol_approval=true;
				%>						
						<td style="text-align:left;width:60%;border:none;">Menunggu Persetujuan <%=job %></td>
						<td style="text-align:left;width:30%;border:none;vertical-align:middle">	
							<input type="checkbox" name="approval" value="<%=target_idobj %>~<%=npmhs %>~<%=job %>~<%=wait_for_whom %>~<%=my_objid %>" />
						</td>
				<%													
										}
										else {
											%>						
						<td colspan="2" style="text-align:left;border:none;">Menunggu Persetujuan <%=job %></td>
					<%	
										}	
									}
%>
					</tr>
<%
						//if(st.hasMoreTokens()) {
						//	out.print("<br>");
						//}
								}		
								%>
				</table>
								<%					
							}
%>
				
				</b>
			</td>
		</tr>		
<%	
							 
						}
						while(li2.hasNext());
					}
		if(show_tombol_approval) {
%>
		<tr>
			<td colspan="5">
				<center style="padding:10px 10px">
				<section class="gradient">
					<div style="text-align:center">
					<button formnovalidate type="submit" style="padding: 5px 10px;font-size: 20px;"form="update_approval" >SETUJUI PENGAJUAN DAFTAR ULANG</button>
					</div>
				</section>
				</center>
			</td>
		</tr>
<%		
		}
%>
	</tbody>
</table>
<%

%>
</form>
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
			/*
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
			*/	//end
		}
		
	}
}
else {
		
}
%>

	</div>
</div>		
</body>
	
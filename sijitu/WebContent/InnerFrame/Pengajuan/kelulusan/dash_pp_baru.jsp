<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.classPoll.*" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.sistem.*" %>
<%@ page import="beans.dbase.overview.maintenance.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />

<%
//System.out.println("yap1");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= null, v_tmp=null;
ListIterator li_tmp = null;
//Vector vThsmsStmhs = (Vector)session.getAttribute("vThsmsStmhs");
//String tkn_stmhs_kode =  Getter.getListStmhs();
v = (Vector)request.getAttribute("vReqStat");
//System.out.println("vs="+v.size());
boolean backToHome = Boolean.parseBoolean(request.getParameter("backToHome"));
String oprNpm = validUsr.getNpm();
String oprObjId = ""+validUsr.getIdObj();
request.removeAttribute("vReqStat");
String target_thsms = request.getParameter("target_thsms");
String id_obj= request.getParameter("id_obj");
String nmm= request.getParameter("nmm");
String npm= request.getParameter("npm");
String at_menu = ""+request.getParameter("atMenu");
String scope = request.getParameter("scope");
String folder_pengajuan = request.getParameter("folder_pengajuan");
//System.out.println("folder_pengajuan=="+folder_pengajuan);
String table_nm = request.getParameter("table");
session.setAttribute("nama_table", table_nm); //kenapa redundant, karena kalo pasing via param, di form pp pas klik menu form pindah prodi, nama tanel jadi null
session.setAttribute("folder_pengajuan", folder_pengajuan); //kenapa redundant, karena kalo pasing via param, di form pp pas klik menu form pindah prodi, nama tanel jadi null
//System.out.println(table_nm);
String type_pengajuan = table_nm.replace("_RULES", "");
String title_pengajuan = type_pengajuan.replace("_", " ");
//System.out.println("npmm="+npm);
String obj_lvl= request.getParameter("obj_lvl");
String kdpst= request.getParameter("kdpst");
//System.out.println("kdpst="+kdpst);
boolean sdhKasihVerdict = false;
boolean ditolak = false;
String cmd= request.getParameter("cmd");
String msg= request.getParameter("msg");
%>
<script>		
	$(document).ready(function() {
		$("#wait").hide();
		$("#main").show();
	});	
</script>
<script>

	$(document).ready(function() {
		$("#approval").click(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		var formData = $(this).closest('form').serializeArray();
	   	  	formData.push({ name: this.name, value: this.value });
		});	
	});	   	
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


a.img:hover {
 text-decoration: none;
 background:none;
}
</style>		
</head>
<body>
<div id="header">
<ul>
<%
if(backToHome) {
%>
	<li>
		<a href="get.notifications" target="inner_iframe">BACK<span><b style="color:#eee">&nbsp</b></span></a>
	</li>
<%	
}
else {
%>
	<li>
	
		<a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/indexPengajuanMhs.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=reqMhs" target="_self">BACK<span>&nbsp</span></a>
	</li>
<%
	if(at_menu.equalsIgnoreCase("form")) {
		%>	
	<li>
		<a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/Pengajuan/<%=folder_pengajuan%>/form_pp.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&table=<%=table_nm %>&scope=<%=scope %>&atMenu=form" class="active" target="_self">FORM PENGAJUAN<span><%=title_pengajuan %></span></a>
	</li>
		<%			
	}
	else {
%>	
	<li>
		<a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/Pengajuan/<%=folder_pengajuan%>/form_pp.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&table=<%=table_nm %>&scope=<%=scope %>&atMenu=form" target="_self">FORM PENGAJUAN<span><%=title_pengajuan %></span></a>
	</li>
	<%		
	}
	
}
%>	
</ul>	
</div>
<div class="colmask fullpage">
	<div class="col1">
		<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
			<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai</p>
		</div>
		<div id="main">

		<!-- Column 1 start -->
		
		<%
		if(msg!=null && msg.equalsIgnoreCase("upd")) {
		%>
			<div align="center" style="font-size:0.8em">UPDATED : <%=AskSystem.getDateTime() %></div>
		<%
		}
		else if(msg!=null && !Checker.isStringNullOrEmpty(msg)) {
			%>
			<div align="center" style="font-size:1.2em;color:red"><%=msg %></div>
			
		<%	
		}
		
		if(v!=null && v.size()>0) {
			//System.out.println("!vnull");
			//System.out.println("v.size = "+v.size());
			ListIterator li = v.listIterator();
			if(li.hasNext()) {
				int i =1;
			%>
			<br/>
			<center>
			<table class="table" style="width:80%"> 
		        <tr> 
		             <td style="font-size:1.5em;background:#369;color:#fff;text-align:center" colspan="4"><label><B>
		             <%
		             if(validUsr.getObjNickNameGivenObjId().contains("MHS")) {
		             	out.print("STATUS PENGAJUAN "+title_pengajuan);		 
		             }
		             else {
		             %>         
		             DAFTAR MAHASISWA YANG MENGAJUKAN <%=title_pengajuan %>
		             <%
		             }
		             %>
		             </B> </label></td> 
		        </tr> 
		        <tr> 
		              <td style="background:#369;color:#fff;text-align:center;width:5%">NO</td><td style="background:#369;color:#fff;text-align:center;width:10%">THN/SMS</td><td style="background:#369;color:#fff;text-align:center;width:50%">NPM/NAMA</td><td style="background:#369;color:#fff;text-align:center;width:35%">STATUS</td> 
		        </tr> 
			<%	
				do {
					String brs = (String)li.next();
					//System.out.println("barisan="+brs);
					StringTokenizer st = new StringTokenizer(brs,"`");
					
					String id=st.nextToken();
					//System.out.println("id  "+id);
					String thsms_pengajuan=st.nextToken();
					String tipe_pengajuan=st.nextToken();
					String isi_topik_pengajuan=st.nextToken();
					String tkn_target_objnickname=st.nextToken();
					String tkn_target_objid=st.nextToken();
					//System.out.println("tkn_target_objnickname=="+tkn_target_objnickname);
					//System.out.println("tkn_target_objid=="+tkn_target_objid);
					String target_npm=st.nextToken();
					String creator_obj_id=st.nextToken();
					String creator_npm=st.nextToken();
					String creator_nmm=st.nextToken();
					String shwow_at_target=st.nextToken();
					String show_at_creator=st.nextToken();
					String updtm=st.nextToken();
					String approved=st.nextToken();
					//System.out.println("tkn_approved=="+approved);
					//System.out.println("anjing");
					boolean all_approved = AddHocFunction.isAllApproved(id, tkn_target_objid);
					//System.out.println("all_approved bos="+all_approved);
					
					boolean terkunci = false;
					
					String locked=st.nextToken();
					if(locked.equalsIgnoreCase("true")) {
						terkunci = true;
					}
					//System.out.println("tkn_target_objid="+tkn_target_objid);
					//System.out.println("oprObjId="+oprObjId);
					String rejected=st.nextToken();
					String creator_kdpst=st.nextToken();
					String target_kdpst=st.nextToken();
					boolean cancel = false;
					String batal=st.nextToken();
					if(batal.equalsIgnoreCase("true")) {
						cancel = true;
					}
					
					//System.out.println("sain");
					boolean am_i_approvee = false;
					if(tkn_target_objid.contains("["+oprObjId+"]")||tkn_target_objid.contains("["+oprObjId+",")||tkn_target_objid.contains(","+oprObjId+",")||tkn_target_objid.contains(","+oprObjId+"]")) {
						am_i_approvee = true;
					}
					//System.out.println("am_i_approvee="+am_i_approvee);
					
				%>
				<tr> 
				<%	
					if(Checker.am_i_stu(session) || !am_i_approvee || terkunci) {
						%>
				
					<td style="text-align:center;width:25px" valign="TOP"><%=i++ %>.
				<%	
					}
					else {
				%>
					<td style="text-align:center;width:25px" valign="TOP"><%=i++ %>.
				<%		
					}
					try {
						if(isi_topik_pengajuan!=null && all_approved) {
							java.sql.Date dt_lls = java.sql.Date.valueOf(Converter.autoConvertDateFormat(isi_topik_pengajuan, "-"));
							//System.out.println("dt_lls="+dt_lls.toString());
							if(validUsr.amI_v1("ADMIN", kdpst)) {
				%>
						<br><a class="img" href="go.moPp?folder_pengajuan=<%=folder_pengajuan %>&target_thsms=<%=target_thsms%>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=edit_lulus&folder_pengajuan=kelulusan&scope=lulus&table=KELULUSAN_RULES"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/edit_blue_pencil.png" alt="edit" style="width:30px;height:35px;border:0;"></a>
				<%
							}
						}	
					}catch(Exception e){
						
					}
		        %>	
		        	</td>
		        	<td style="text-align:center;width:50px" valign="TOP"><%=thsms_pengajuan %></td>
		        	<td style="text-align:center;width:300px" valign="TOP"><%=creator_npm %><br/><%=creator_nmm %><br>Tanggal Kelulusan : <%=isi_topik_pengajuan %></td>
		        	
		        <%
		        	String keterangan = "";
		        	if(isi_topik_pengajuan!=null && !Checker.isStringNullOrEmpty(isi_topik_pengajuan)) {
		        		if(title_pengajuan.contains("lulus")||title_pengajuan.contains("LULUS")) {
		        			keterangan = "<strong>TANGGAL "+title_pengajuan+" : "+isi_topik_pengajuan+"</strong><br/>";
		        		}
		        		else {
		        			keterangan = "<strong>Alasan "+title_pengajuan+" : "+isi_topik_pengajuan+"</strong><br/>";	
		        		}
		        		
		        	}
		        	//String 
		        	sdhKasihVerdict = false;
        			ditolak = false;
		        	if(!st.hasMoreTokens()) {
		        		//System.out.println("!st.hasMoreTokens()");
		        		if((approved==null || approved.equalsIgnoreCase("null")) && !cancel) {
		        			//System.out.println("(approved==null || approved.equalsIgnoreCase(null)) && !cancel)");
		        			%>
			       	<td style="text-align:center;width:275px" valign="midle">
			        		<%
			        		out.println("<strong>Menunggu persetujuan1 :<br/>");
			        		out.print(tkn_target_objnickname.replace("OPERATOR", "")+"</strong>");
			        		session.setAttribute("status_pengajuan_"+title_pengajuan+"_"+creator_npm, "inprogress");
			        	}
		        		
		        	}
		        	else {
		        		//System.out.println("st hasmore token");
		        		//sdhKasihVerdict = false;
		        		//ditolak = false;
		        		String nickname_blm_ngasih_verdict = "";
		        		String id_blm_ngasih_verdict = new String(tkn_target_objid);
		        		id_blm_ngasih_verdict = id_blm_ngasih_verdict.replace(",", "][");
		        		//System.out.println("id_blm_ngasih_verdict="+id_blm_ngasih_verdict);
		        		//id_blm_nga//
		        		int counter=0;
		        		while(st.hasMoreTokens()) {
		        			counter++;
		        			
		        			String isi_sub = st.nextToken();
		        			//System.out.println("counter="+counter);
		        			String creator_id_sub = st.nextToken();
		        			//System.out.println("creator_id_sub="+creator_id_sub);
		        			if(creator_id_sub.equalsIgnoreCase(oprObjId)) {
		        				sdhKasihVerdict = true;
		        			}
		        			if(id_blm_ngasih_verdict.contains("["+creator_id_sub+"]")) {
		        				id_blm_ngasih_verdict = id_blm_ngasih_verdict.replace("["+creator_id_sub+"]", "");
		        			}
		        			
		        			
		        			//id_blm_ngasih_verdict = id_blm_ngasih_verdict.replace("["+creator_id_sub+"]", "");
		        			String verdict_sub = st.nextToken();
		        			//System.out.println("verdict_sub="+verdict_sub);
		        			String updtm_sub = st.nextToken();
		        			//System.out.println("updtm_sub="+updtm_sub);
		        			if(verdict_sub.contains("TERIMA")) {
		        				//keterangan = keterangan+"<small>"+Converter.getDateFromTimestamp(updtm_sub)+"</small><br/>";
		        				String list_role = AddHocFunction.siapaIni(tkn_target_objnickname, tkn_target_objid, creator_id_sub,"OPERATOR");
		        				if(list_role!=null && list_role.contains("`")) {
		        					StringTokenizer str = new StringTokenizer(list_role,"`");
		        					while(str.hasMoreTokens()) {
		        						keterangan = keterangan+"<em>-&nbsp<strong>Disetujui</strong> "+str.nextToken()+" <small>["+Converter.getDateFromTimestamp(updtm_sub)+"]</small><br/>";	
		        					}
		        				}
		        				else {
		        					keterangan = keterangan+"<em>-&nbsp<strong>Disetujui</strong> "+list_role+"<br/>";	
		        				}
		        				
		        				//out.print("<small>"+Converter.getDateFromTimestamp(updtm_sub)+"</small><br/>") ;
		        				//out.print("<em>-&nbsp<strong>Disetujui</strong> "+AddHocFunction.siapaIni(tkn_target_objnickname, tkn_target_objid, creator_id_sub,"OPERATOR")+"<br/>");
		        				
		        			}
		        			else if(verdict_sub.contains("TOLAK")){
		        				//keterangan = keterangan+"<small>"+Converter.getDateFromTimestamp(updtm_sub)+"</small>";
		        				//keterangan = keterangan+"<br/><em>-&nbsp<strong>Ditolak</strong> "+AddHocFunction.siapaIni(tkn_target_objnickname, tkn_target_objid, creator_id_sub, "OPERATOR")+"<br/>";
		        				String list_role = AddHocFunction.siapaIni(tkn_target_objnickname, tkn_target_objid, creator_id_sub,"OPERATOR");
		        				if(list_role!=null && list_role.contains("`")) {
		        					StringTokenizer str = new StringTokenizer(list_role,"`");
		        					while(str.hasMoreTokens()) {
		        						keterangan = keterangan+"<em>-&nbsp<strong>Ditolak</strong> "+str.nextToken()+" <small>["+Converter.getDateFromTimestamp(updtm_sub)+"]</small><br/>";	
		        					}
		        				}
		        				else {
		        					keterangan = keterangan+"<em>-&nbsp<strong>Ditolak</strong> "+list_role+"<br/>";	
		        				}
		        				
		        				//out.print("<small>"+Converter.getDateFromTimestamp(updtm_sub)+"</small>") ;
		        				//out.print("<br/><em>-&nbsp<strong>Ditolak</strong> "+AddHocFunction.siapaIni(tkn_target_objnickname, tkn_target_objid, creator_id_sub, "OPERATOR")+"<br/>");
		        				//out.print(updtm_sub+"- "+AddHocFunction.siapaIni(tkn_target_objnickname, tkn_target_objid, creator_id_sub)+" karena "+isi_sub+"<br/>");
		        				ditolak = true;
		        			}
		        			keterangan = keterangan+"</em>";
		        			//System.out.println("keterangan0="+keterangan);
		        			//out.print("</em>");
		        		}
		        		if(!Checker.isStringNullOrEmpty(id_blm_ngasih_verdict) && !ditolak) {
		        			id_blm_ngasih_verdict = id_blm_ngasih_verdict.replace("[", "");
		        			id_blm_ngasih_verdict = id_blm_ngasih_verdict.replace("]", "`");
		        			id_blm_ngasih_verdict = id_blm_ngasih_verdict.replace(",", "`");
		        			st = new StringTokenizer(id_blm_ngasih_verdict,"`");
		        			if(st.hasMoreTokens()) {
		        				keterangan = keterangan+"<strong>Menunggu Persetujuan2 :<strong><br/>";
		        				//out.print("Menunggu Persetujuan :<br/>");
		        				String tmp = "";
		        				do {
		        					//keterangan = keterangan+"<em>-&nbsp"+AddHocFunction.siapaIni(tkn_target_objnickname, tkn_target_objid, st.nextToken(),"OPERATOR")+"<br/>";
		        					tmp = tmp+"`"+AddHocFunction.siapaIni(tkn_target_objnickname, tkn_target_objid, st.nextToken(),"OPERATOR");
		        					//out.print("<em>-&nbsp"+AddHocFunction.siapaIni(tkn_target_objnickname, tkn_target_objid, st.nextToken(),"OPERATOR")+"<br/>");
		        				}
		        				while(st.hasMoreTokens());
		        				//System.out.println("tmp0="+tmp);
		        				//cek duplicate jabatan dan remove
		        				if(!Checker.isStringNullOrEmpty(tmp)) {
		        					v_tmp = new Vector();
			        				li_tmp = v_tmp.listIterator();
			        				st = new StringTokenizer(tmp,"`");
			        				while(st.hasMoreTokens()) {
			        					li_tmp.add(st.nextToken());
			        				}
			        				try {
			        					v_tmp = Tool.removeDuplicateFromVector(v_tmp);
			        					li_tmp = v_tmp.listIterator();
			        				}
			        				catch(Exception e){}
			        				tmp = "";
			        				while(li_tmp.hasNext()) {
			        					tmp = tmp+"-&nbsp"+(String)li_tmp.next()+"<br>";
			        				}
			        				keterangan = "<em>"+keterangan+tmp+"</em>";
			        				//System.out.println("keterangan="+keterangan);
			        				//System.out.println("id_blm_ngasih_verdict="+id_blm_ngasih_verdict);
			        				if(v_tmp!=null) {
			        					//System.out.println("v_tmp_size="+v_tmp.size());
			        				}
			        				else {
			        					//System.out.println("v_tmp_size is null");
			        				}
		        				}
		        			}
		        			
		        		}
		        		//kalo semua approved approved
		        		%>
		        	<td style="text-align:center;width:275px" valign="midle">
		        		<%	
		        		//if(Checker.isStringNullOrEmpty(id_blm_ngasih_verdict) && !ditolak && !cancel) {
		        		if(all_approved && !ditolak && !cancel) {	
		        		
		        			out.print("<strong>Permohonan "+title_pengajuan+" Anda Telah Disetujui</strong>");
		        			session.setAttribute("status_pengajuan_"+title_pengajuan+"_"+creator_npm, "disetujui");
		        			
		        			
		        			//bila approvee pindah prodi maka insert new npm target
		        			
		        			
		        			
		        			
		        		}
		        		else if(ditolak){
		        		
		        			out.print(keterangan);
		        			session.setAttribute("status_pengajuan_"+title_pengajuan+"_"+creator_npm, "ditolak");
		        		}
		        		else if(cancel) {
		        			
			        		out.println("<strong>Pengajuan1 "+title_pengajuan+" telah dibatalkan</strong>");
			        		//out.print(tkn_target_objnickname.replace("OPERATOR", "")+"</strong>");
			        		session.setAttribute("status_pengajuan_"+title_pengajuan+"_"+creator_npm, "batal");
			        	}
		        		else {
		        			//kalo diterima tapi blum approved semua
		        			
			        		//System.out.println("kesono");
		        			out.print(keterangan);
			        		session.setAttribute("status_pengajuan_"+title_pengajuan+"_"+creator_npm, "inprogress");
		        		}
		        		%>
			       
			  	
						<%	
		        		
		        	}
		        	%>
		        	</td> 	
		        </tr> 
		        	<%
		        	
		        	
		        	
					if(!Checker.am_i_stu(session) && am_i_approvee && !terkunci) {
						//System.out.println("npm dash = "+npm);
						
				%>
				
					<!--  form action="proses.cutiApproval" method="POST"-->
					<form action="proses.pengajuanApproval" method="POST">
					
					<input type="hidden" name="trans_id" value="<%=brs %>" />
					<input type="hidden" name="id_obj" value="<%=id_obj %>" />
					<input type="hidden" name="nmm" value="<%=nmm %>" />
					<input type="hidden" name="npm" value="<%=npm %>" />
					<input type="hidden" name="scope" value="<%=scope %>" />
					<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
					<input type="hidden" name="kdpst" value="<%=kdpst %>" />
					<input type="hidden" name="cmd" value="<%=cmd %>" />
					<input type="hidden" name="msg" value="upd" />
					<input type="hidden" name="full_table_rules_nm" value="<%=table_nm %>" />
				
				
				<%
						if(!sdhKasihVerdict && !ditolak && !cancel) {			
				%>		
				<tr>
					<td colspan="4" valign="middle"  style="background:white"><textarea title="Harap diisi alasan penolakan" name="alasan" placeholder="Alasan Penolakan" style="border:none;width:100%;rows:5" required></textarea></td>
				</tr>
				<tr>
					<td text-align="center" colspan="4" style="background:#369;height:35px;padding:5px 0"><center>	
					<input type="submit" name="approval" value="TOLAK PERMOHONAN"  style="width:40%;height:30px;color:red;font-weight:bold"/>
					&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
					<input type="submit" name="approval" value="TERIMA PERMOHONAN" formnovalidate style="width:40%;height:30px;font-weight:bold"/>
					</center>	
					</td>
				</tr>	
				<%
						}
						else {
					//ngga pake text area
					//System.out.println("kesini");
					%>
				<tr>
							
					<%
							if(ditolak) {
					%>
				
					<td text-align="center" colspan="4" rowspan="2" style="background:#369;color:white;height:35px;padding:5px 0"><center>
					<%			
								out.print("Permohonan Sudah Ditolak");	
							}
							else if(cancel) {
					%>
					<td text-align="center" colspan="4" rowspan="2" style="background:#369;color:white;height:35px;padding:5px 0"><center>
					<%			
								out.print("Permohonan Sudah Dibatalkan");	
							}
							else if(sdhKasihVerdict){
								if(all_approved && type_pengajuan.equalsIgnoreCase("pindah_prodi") && !Checker.am_i_stu(session) && am_i_approvee) {
													        				
						        		%>
					<td text-align="center" colspan="4" rowspan="2" style="background:#369;color:white;height:35px;padding:5px 0"><center>	
						        		MASUKAN NO <%=Converter.npmAlias() %> PADA PRODI BARU <input type="text" width="50%" name="npm_pp"/>
					
						        				
						        		<%				
						        			
								}
								else if(!terkunci) {
				%>
					<td text-align="center" colspan="4" rowspan="2" style="background:#369;color:white;height:35px;padding:5px 0"><center>	
						<input type="submit" name="approval"  value="RESET ULANG PUTUSAN ANDA"  style="width:40%;height:30px;color:red;font-weight:bold"/>
					
				<%				
								}
								else {
				%>
					<td text-align="center" colspan="4" rowspan="2" style="background:#369;color:white;height:35px;padding:5px 0"><center>
				<%							
									out.print("Anda Sudah Menyetujui Permohonan");	
								}	
								
							}
					%>
					</center>
					</td>
				</tr>
						
			<%				
						}
			%>
			</form>		
			<%	

					}
		       /*
		       !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		       KENAPA HARUS ADA <TR> DIBAWAH SINI, KOK NGA KETAUAN SALAHNYA
		       */ 	
				%>
				<tr>
				<%	
				}
				while(li.hasNext());
			%>
			
		</table>
		</center>
			<%	
			}
			
		}
		else {
			%>
			<div style="text-align:center">
			<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/facepalm_itachi_small.png" alt="Gubraaak !@?!?">
			<h2>GuBRaAKk ...!!??@??!?<br>Maaf ada kesalahan data, harap hiraukan dulu notifikasi "PENGAJUAN <%=title_pengajuan %>" yang tampil di LAMAN MUKA</h2>
			</div>
			<%
			
			
			MaintenanceOverview mo = new MaintenanceOverview();
			String kode_stmhs = Converter.convertTipePengajuanJadiKodeStmhs(title_pengajuan);
    		Vector v_scope_id_keseluruhan = Getter.get_v_scope_id_keseluruhan(); 
    		mo.maintenaceCountDataMhsGivenStmhs_step2((Vector)v_scope_id_keseluruhan.clone(), kode_stmhs, target_thsms);
			mo.maintenaceCountDataMhsGivenStmhsInProgress_step3((Vector)v_scope_id_keseluruhan.clone(), kode_stmhs, target_thsms);
			mo.maintenaceUpdateListMhsGivenStmhsInProgress_step4((Vector)v_scope_id_keseluruhan.clone(), kode_stmhs, target_thsms);
    		

			session.removeAttribute("status_pengajuan_"+title_pengajuan+"_"+npm);
		%>
			<!--  div style="text-align:center;font-weight:bold;font-size:1.5em">Total Pengajuan: 0<br>Harap Menunggu Anda Sedang Dialihkan</div>
			<meta http-equiv="refresh" content="3; url=http:get.notifications" -->
		<%	
		}
		%>
		<br/>
		<%
		if(!backToHome) {
		%>
		<div style="text-align:center; font-size:1.5em;font-weight:bold">
		PENGAJUAN <%=title_pengajuan %> AKAN DIPROSES DAN DISETUJUI BILA TELAH MEMENUHI PERSYARATAN ADMINISTRASI AKADEMIK & KEUANGAN
		<%
		if(validUsr.isHakAksesReadOnly(scope)) {
		%>
			<br/><br/>PENGAJUAN HARUS DILAKUKAN SENDIRI OLEH MAHASISWA
		<%		
		}
		%>
		</div>
		<%
		}
		
		//buat session status pengajuan untuk form cuti page
		
		%>
		<!-- Column 1 end -->
		</div>
	</div>
</div>

</body>
</html>
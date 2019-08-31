<!DOCTYPE html>
<html>
<head lang="en">
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.classPoll.*" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.sistem.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>

    <script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<%
//System.out.println("yap1");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= null;
//Vector vThsmsStmhs = (Vector)session.getAttribute("vThsmsStmhs");
//String tkn_stmhs_kode =  Getter.getListStmhs();
v = (Vector)request.getAttribute("vReqStat");
boolean backToHome = Boolean.parseBoolean(request.getParameter("backToHome"));
String oprNpm = validUsr.getNpm();
String oprObjId = ""+validUsr.getIdObj();
request.removeAttribute("vReqStat");
String id_obj= request.getParameter("id_obj");
String nmm= request.getParameter("nmm");
String npm= request.getParameter("npm");
String at_menu = ""+request.getParameter("atMenu");
String scope = request.getParameter("scope");
String folder_pengajuan = request.getParameter("folder_pengajuan");
String table_nm = request.getParameter("table");
session.setAttribute("nama_table", table_nm); //kenapa redundant, karena kalo pasing via param, di form pp pas klik menu form pindah prodi, nama tanel jadi null
session.setAttribute("folder_pengajuan", folder_pengajuan); //kenapa redundant, karena kalo pasing via param, di form pp pas klik menu form pindah prodi, nama tanel jadi null
//System.out.println(table_nm);
String type_pengajuan = table_nm.replace("_RULES", "");
String title_pengajuan = type_pengajuan.replace("_", " ");
//System.out.println("scope="+scope);
String obj_lvl= request.getParameter("obj_lvl");
String kdpst= request.getParameter("kdpst");
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
		
		$("#resetPengajuanApproval").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.pengajuanApproval',
        		type: 'POST',
        		data: $("#resetPengajuanApproval").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";
        	        ///ToUnivSatyagama/WebContent/InnerFrame/
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Tamplete/Notification/ShowNotificationThenRedirect_v2.jsp?msg=Proses Approval Berhasil<br>Harap Menunggu Sebentar, Anda Sedang Dialihkan&redirectTo=get.notifications";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
       });
		
		$("#tolakPengajuanApproval").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.pengajuanApproval',
        		type: 'POST',
        		data: $("#tolakPengajuanApproval").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Tamplete/Notification/ShowNotificationThenRedirect_v2.jsp?msg=Proses Approval Berhasil<br>Harap Menunggu, Anda Sedang Dialihkan&redirectTo=get.notifications";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
       });
		
		$("#terimaPengajuanApproval").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.pengajuanApproval',
        		type: 'POST',
        		data: $("#terimaPengajuanApproval").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Tamplete/Notification/ShowNotificationThenRedirect_v2.jsp?msg=Proses Approval Berhasil<br>Harap Menunggu, Anda Sedang Dialihkan&redirectTo=get.notifications";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
       });
	});	
	</script>
</head>
<body>
<div id="header">
<ul>
<%
if(backToHome) {
%>
	<li>
		<a href="get.notifications" target="inner_iframe">BACK <span><b style="color:#eee">&nbsp</b></span></a>
	</li>
<%	
}
else {
%>
	<li>
	
		<a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/indexPengajuanMhs.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=reqMhs" target="_self">BACK <span>&nbsp</span></a>
	</li>
<%
	if(!validUsr.isHakAksesReadOnly("cuti")) {
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
	
}
%>	
</ul>	
</div>
<div class="colmask fullpage">
	<div class="col1">
	
		<br />
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
			<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:650px"> 
		        <tr> 
		             <td style="background:#369;color:#fff;text-align:center" colspan="4"><label><B>
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
		              <td style="background:#369;color:#fff;text-align:center;width:25px">NO</td><td style="background:#369;color:#fff;text-align:center;width:50px">THN/SMS</td><td style="background:#369;color:#fff;text-align:center;width:250px">NPM/NAMA</td><td style="background:#369;color:#fff;text-align:center;width:325px">STATUS</td> 
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
					boolean all_approved = AddHocFunction.isAllApproved(tkn_target_objid, approved);
					//System.out.println("all_approved="+all_approved);
					
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
					
					
				%>
				<tr> 
				<%	
					if(Checker.am_i_stu(session) || !am_i_approvee || terkunci) {
						%>
				
					<td style="text-align:center;width:25px" valign="TOP"><%=i++ %>.</td>
				<%	
					}
					else {
				%>
					<td rowspan="3" style="text-align:center;width:25px" valign="TOP"><%=i++ %>.</td>
				<%		
					}	
				
		        %>	
		        	<td style="text-align:center;width:50px" valign="TOP"><%=thsms_pengajuan %></td>
		        	<td style="text-align:center;width:250px" valign="TOP"><%=creator_npm %><br/><%=creator_nmm %></td>
		        	
		        <%
		        	String keterangan = "<strong>Alasan "+title_pengajuan+" : "+isi_topik_pengajuan+"</strong><br/>";
		        	sdhKasihVerdict = false;
        			ditolak = false;
		        	if(!st.hasMoreTokens()) {
		        		//System.out.println("!st.hasMoreTokens()");
		        		if((approved==null || approved.equalsIgnoreCase("null")) && !cancel) {
		        			//System.out.println("(approved==null || approved.equalsIgnoreCase(null)) && !cancel)");
		        			%>
			       	<td style="text-align:center;width:325px" valign="midle">
			        		<%
			        		out.println("<strong>Menunggu persetujuan :<br/>");
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
		        		
		        		while(st.hasMoreTokens()) {
		        			String isi_sub = st.nextToken();
		        			//System.out.println("isi_sub="+isi_sub);
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
		        				keterangan = keterangan+"<small>"+Converter.getDateFromTimestamp(updtm_sub)+"</small><br/>";
		        				keterangan = keterangan+"<em>-&nbsp<strong>Disetujui</strong> "+AddHocFunction.siapaIni(tkn_target_objnickname, tkn_target_objid, creator_id_sub,"OPERATOR")+"<br/>";
		        				//out.print("<small>"+Converter.getDateFromTimestamp(updtm_sub)+"</small><br/>") ;
		        				//out.print("<em>-&nbsp<strong>Disetujui</strong> "+AddHocFunction.siapaIni(tkn_target_objnickname, tkn_target_objid, creator_id_sub,"OPERATOR")+"<br/>");
		        				
		        			}
		        			else if(verdict_sub.contains("TOLAK")){
		        				keterangan = keterangan+"<small>"+Converter.getDateFromTimestamp(updtm_sub)+"</small>";
		        				keterangan = keterangan+"<br/><em>-&nbsp<strong>Ditolak</strong> "+AddHocFunction.siapaIni(tkn_target_objnickname, tkn_target_objid, creator_id_sub, "OPERATOR")+"<br/>";
		        				//out.print("<small>"+Converter.getDateFromTimestamp(updtm_sub)+"</small>") ;
		        				//out.print("<br/><em>-&nbsp<strong>Ditolak</strong> "+AddHocFunction.siapaIni(tkn_target_objnickname, tkn_target_objid, creator_id_sub, "OPERATOR")+"<br/>");
		        				//out.print(updtm_sub+"- "+AddHocFunction.siapaIni(tkn_target_objnickname, tkn_target_objid, creator_id_sub)+" karena "+isi_sub+"<br/>");
		        				ditolak = true;
		        			}
		        			keterangan = keterangan+"</em>";
		        			//out.print("</em>");
		        		}
		        		if(!Checker.isStringNullOrEmpty(id_blm_ngasih_verdict) && !ditolak) {
		        			id_blm_ngasih_verdict = id_blm_ngasih_verdict.replace("[", "");
		        			id_blm_ngasih_verdict = id_blm_ngasih_verdict.replace("]", "`");
		        			id_blm_ngasih_verdict = id_blm_ngasih_verdict.replace(",", "`");
		        			st = new StringTokenizer(id_blm_ngasih_verdict,"`");
		        			if(st.hasMoreTokens()) {
		        				keterangan = keterangan+"Menunggu Persetujuan :<br/>";
		        				//out.print("Menunggu Persetujuan :<br/>");
		        				do {
		        					keterangan = keterangan+"<em>-&nbsp"+AddHocFunction.siapaIni(tkn_target_objnickname, tkn_target_objid, st.nextToken(),"OPERATOR")+"<br/>";
		        					//out.print("<em>-&nbsp"+AddHocFunction.siapaIni(tkn_target_objnickname, tkn_target_objid, st.nextToken(),"OPERATOR")+"<br/>");
		        				}
		        				while(st.hasMoreTokens());
		        				//out.print("</em>");
		        				keterangan = keterangan+"</em>";
		        			}
		        			
		        		}
		        		//kalo semua approved approved
		        		%>
		        	<td style="text-align:center;width:325px" valign="midle">
		        		<%	
		        		if(Checker.isStringNullOrEmpty(id_blm_ngasih_verdict) && !ditolak && !cancel) {
		        		
		        			out.print("<strong>Permohonan "+title_pengajuan+" Anda Telah Disetujui</strong>");
		        			session.setAttribute("status_pengajuan_"+title_pengajuan+"_"+creator_npm, "disetujui");
		        			
		        			
		        			//bila approvee pindah prodi maka insert new npm target
		        			
		        			
		        			
		        			
		        		}
		        		else if(ditolak){
		        		
		        			out.print(keterangan);
		        			session.setAttribute("status_pengajuan_"+title_pengajuan+"_"+creator_npm, "ditolak");
		        		}
		        		else if(cancel) {
		        			
			        		out.println("<strong>Pengajuan "+title_pengajuan+" telah dibatalkan</strong>");
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
					<!--  form action="proses.pengajuanApproval" method="POST" -->
				
				
				
				<%
						if(!sdhKasihVerdict && !ditolak && !cancel) {			
				%>	
				<form id="tolakPengajuanApproval">
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
					<input type="hidden" name="approval" value="TOLAK PERMOHONAN" />	
				<tr>
					<td colspan="3" valign="middle"><textarea title="Harap diisi alasan penolakan" name="alasan" placeholder="Alasan Penolakan" style="width:99%;rows:3" required></textarea></td>
				</tr>
				<tr>
					<td text-align="center" colspan="3" ><center>	
					<input type="submit" name="approval" value="TOLAK PERMOHONAN"  style="width:40%;height:30px;color:red;font-weight:bold"/>
				</form>	
					&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
				<form id="terimaPengajuanApproval">
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
					<input type="hidden" name="approval" value="TERIMA PERMOHONAN" />	
					
					<input type="submit" name="approval" value="TERIMA PERMOHONAN" formnovalidate style="width:40%;height:30px;font-weight:bold"/>
				</form>	
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
				
					<td text-align="center" colspan="3" rowspan="2"><center>
					<%			
								out.print("Permohonan Sudah Ditolak");	
							}
							else if(cancel) {
					%>
					<td text-align="center" colspan="3" rowspan="2"><center>
					<%			
								out.print("Permohonan Sudah Dibatalkan");	
							}
							else if(sdhKasihVerdict){
								if(all_approved && type_pengajuan.equalsIgnoreCase("pindah_prodi") && !Checker.am_i_stu(session) && am_i_approvee) {
													        				
						        		%>
					<td text-align="center" colspan="3" rowspan="2"><center>	
						        		MASUKAN NO <%=Converter.npmAlias() %> PADA PRODI BARU <input type="text" width="50%" name="npm_pp"/>
					
						        				
						        		<%				
						        			
								}
								else if(!terkunci) {
				%>
					<td text-align="center" colspan="3" rowspan="2"><center>	
					<form id="resetPengajuanApproval">
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
					<input type="hidden" name="approval" value="RESET ULANG PUTUSAN ANDA" />	
						<input type="submit" name="approval" value="RESET ULANG PUTUSAN ANDA"  style="width:40%;height:30px;color:red;font-weight:bold"/>
					</form>
				<%				
								}
								else {
				%>
					<td text-align="center" colspan="3" rowspan="2"><center>
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
			<%	
			}
			
		}
		else {
			//System.out.println("vnull");
			session.removeAttribute("status_pengajuan_"+title_pengajuan+"_"+npm);
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
<%
v = (Vector) request.getAttribute("v_profile");
//System.out.println("sizev="+v.size());
//System.out.println("atPages="+atPage);
/*
* ada masalah redundan krn v_id_obj = idobj, v_npmhs = npm dst (variable diatas))
*/
String msg = request.getParameter("msg");
String objId = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String kdpst = request.getParameter("kdpst");
String obj_lvl =  request.getParameter("obj_lvl");

String v_id_obj = null;
if(objId!=null && !objId.equalsIgnoreCase("null")) {
	v_id_obj = ""+objId;
}
else {
	v_id_obj = (String)request.getAttribute("v_id_obj");
}
String v_nmmhs=null;
if(nmm!=null && !nmm.equalsIgnoreCase("null")) {
	v_nmmhs = ""+nmm;
}
else {
	v_nmmhs = (String)request.getAttribute("v_nmmhs");
}
//String v_nmmhs=(String)request.getAttribute("v_nmmhs");
String v_npmhs=null;
if(npm!=null && !npm.equalsIgnoreCase("null")) {
	v_npmhs = ""+npm;
}
else {
	v_npmhs = (String)request.getAttribute("v_npmhs");
}
String v_kdpst=null;
if(kdpst!=null && !kdpst.equalsIgnoreCase("null")) {
	v_kdpst = ""+kdpst;
}
else {
	v_kdpst = (String)request.getAttribute("v_kdpst");
}
String v_obj_lvl=null;
if(obj_lvl!=null && !obj_lvl.equalsIgnoreCase("null")) {
	v_obj_lvl = ""+obj_lvl;
}
else {
	v_obj_lvl = (String)request.getAttribute("v_obj_lvl");
}

String v_nimhs=(String)request.getAttribute("v_nimhs");

String v_kdjen=(String)request.getAttribute("v_kdjen");
String v_shift=(String)request.getAttribute("v_shift");
String v_smawl=(String)request.getAttribute("v_smawl");
String v_tplhr=(String)request.getAttribute("v_tplhr");
String v_tglhr=(String)request.getAttribute("v_tglhr");
String v_aspti=(String)request.getAttribute("v_aspti");
String v_aspst=(String)request.getAttribute("v_aspst");
String v_btstu=(String)request.getAttribute("v_btstu");
String v_kdjek=(String)request.getAttribute("v_kdjek");
String v_nmpek=(String)request.getAttribute("v_nmpek");
String v_ptpek=(String)request.getAttribute("v_ptpek");
String v_stmhs=(String)request.getAttribute("v_stmhs");
String v_stpid=(String)request.getAttribute("v_stpid");
String v_sttus=(String)request.getAttribute("v_sttus");
String v_email=(String)request.getAttribute("v_email");
String v_nohpe=(String)request.getAttribute("v_nohpe");
String v_almrm=(String)request.getAttribute("v_almrm");
String v_kotrm=(String)request.getAttribute("v_kotrm");
String v_posrm=(String)request.getAttribute("v_posrm");
String v_telrm=(String)request.getAttribute("v_telrm");
String v_almkt=(String)request.getAttribute("v_almkt");
String v_kotkt=(String)request.getAttribute("v_kotkt");
String v_poskt=(String)request.getAttribute("v_poskt");
String v_telkt=(String)request.getAttribute("v_telkt");
String v_jbtkt=(String)request.getAttribute("v_jbtkt");
String v_bidkt=(String)request.getAttribute("v_bidkt");
String v_jenkt=(String)request.getAttribute("v_jenkt");
String v_nmmsp=(String)request.getAttribute("v_nmmsp");
String v_almsp=(String)request.getAttribute("v_almsp");
String v_possp=(String)request.getAttribute("v_possp");
String v_kotsp=(String)request.getAttribute("v_kotsp");
String v_negsp=(String)request.getAttribute("v_negsp");
String v_telsp=(String)request.getAttribute("v_telsp");
String v_neglh=(String)request.getAttribute("v_neglh");
String v_agama=(String)request.getAttribute("v_agama");
//System.out.println("v_nimhs "+v_nimhs);
//System.out.println("v_nmmhs "+v_nmmhs);
//System.out.println("v_kdpst "+v_kdpst);
//System.out.println("v_nmmhs "+v_nmmhs);
	String cmd =  (String)request.getParameter("cmd");
	//System.out.println("cmd = "+cmd);
	boolean allowViewKrs=false, allowEditKrs=false,requestKonversiMakul=false;
%>	

<ul>
	<%
	if(cmd.equalsIgnoreCase("dashboard")) {
	%>
		<li><a href="get.profile?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=dashboard" target="_self" class="active">HOME <span><b style="color:#eee">---</b></span></a></li>
	<%
	}
	else {
	%>
		<li><a href="get.profile?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=dashboard" target="_self">HOME <span><b style="color:#eee">---</b></span></a></li>
	<%
	}
	
	
	boolean ada_akses_vop = false;
	boolean ada_akses_insDataDosen = false;
	boolean ada_akses_viewDataDosen = false;
	if(validUsr.isUsrAllowTo("vop", v_npmhs, v_obj_lvl)) {
		ada_akses_vop = true;
	}
	if(validUsr.isUsrAllowTo("insDataDosen", v_npmhs, v_obj_lvl)) {
		ada_akses_insDataDosen = true;
	}
	if(validUsr.isUsrAllowTo("allowViewDataDosen", npm, obj_lvl)) {
		ada_akses_viewDataDosen = true;
	}
	if(ada_akses_vop || ada_akses_insDataDosen ||ada_akses_viewDataDosen) {
		if(ada_akses_vop) {
			if(cmd.equalsIgnoreCase("viewProfile")) {
	%>
		<li><a href="get.profile?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=viewProfile" target="_self" class="active">DATA <span>PROFIL</span></a></li>
	<%
			}
			else {
	%>
		<li><a href="get.profile?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=viewProfile" target="_self" >DATA <span>PROFIL</span></a></li>
	<%	
			}
		}
		else if(ada_akses_insDataDosen || ada_akses_viewDataDosen) {
			JSONArray jsoaDsn = Getter.readJsonArrayFromUrl("/v1/search_dsn/npm/"+v_npmhs);
			if(jsoaDsn!=null && jsoaDsn.length()>0) {
				if(cmd!=null && cmd.equalsIgnoreCase("dataDosen")) {
					%>
						<li><a href="get.dataDosen?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=dataDosen" target="_self" class="active">DATA<span>DOSEN</span></a></li>
					<%
				}
				else {
					%>
						<li><a href="get.dataDosen?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=dataDosen" target="_self" >DATA<span>DOSEN</span></a></li>
					<%
						}	
						}
						else {
							if(!ToolMhs.getObjNickname(Integer.parseInt(v_id_obj)).contains("MHS") && validUsr.isUsrAllowTo("insDataDosen", v_npmhs, v_obj_lvl)) {
								if(cmd!=null && cmd.equalsIgnoreCase("dataDosen")) {
					%>
						<li><a href="get.dataDosen?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=dataDosen" target="_self" class="active">ADD DATA<span>DOSEN</span></a></li>
					<%	
					}
					else {
					%>
						<li><a href="get.dataDosen?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=dataDosen" target="_self" >ADD DATA<span>DOSEN</span></a></li>
					<%
					}
				}
			}
		}
	}
	//if(validUsr.isAllowTo("vbak")>0) {
	if(validUsr.isUsrAllowTo("vbak", v_npmhs, v_obj_lvl)) {	
		if(cmd.equalsIgnoreCase("payment")) {
			%>
				<li><a href="get.histPymnt?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&atMenu=riwayatBayaran" target="_self" class="active">DATA<span>KEUANGAN</span></a></li>
			<%
		}
		else {
			%>
			<li><a href="get.histPymnt?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&atMenu=riwayatBayaran" target="_self" >DATA<span>KEUANGAN</span></a></li>
			<%
		}
	}
	
	
	//if(validUsr.isUsrAllowTo("viewKrs", v_npmhs, v_obj_lvl)) {
	//System.out.println("innerMenu validUsr="+validUsr);
	//System.out.println("innerMenu v_npmhs="+v_npmhs);
	if(validUsr.isUsrAllowTo("viewKrs", v_npmhs, v_obj_lvl)) {
		allowViewKrs = true;
		/*
		* cek apa user boleh edit krs
		*/
		if(validUsr.isUsrAllowTo("editKrs", v_npmhs, v_obj_lvl)) {
			allowEditKrs=true;
		}
		
		
		//ngga usah ada pilihan class aktif krn sdh punya submenu sendiri
			%>
			<li><a href="get.histKrs?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=histKrs" target="_self" >DATA<span>KRS/KHS</span></a></li>
			<%
	}
/*	
	if(validUsr.isUsrAllowTo("requestKonversiMakul", v_npmhs, v_obj_lvl)) {
		requestKonversiMakul = true;
		if(cmd.equalsIgnoreCase("konversiKrs")) {
			%>
			<li><a href="get.prepKonversiKrs?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=konversiKrs" target="_self" class="active">KONVERSI<span>MATAKULIAH</span></a></li>
			<%
		}
		else {
			%>
			<li><a href="get.prepKonversiKrs?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=konversiKrs" target="_self" >KONVERSI<span>MATAKULIAH</span></a></li>
			<%
		}	
	}
*/	
/*
*  status menu = page isinya mengenai status akhir - heregistrasi - kelulusan - tracer studi
*/
	//if(validUsr.isAllowTo("hasCetakMenu")>0) {
	if(validUsr.isUsrAllowTo("hasCetakMenu", v_npmhs, v_obj_lvl)) {	
		if(cmd.equalsIgnoreCase("percetakan")) {
		%>
		<!--  li><a href="get.infoTracerStudy?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=percetakan" target="_self" class="active">CETAK /<span>PRINTING</span></a></li -->
		<li><a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/indexPercetakan.jsp?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=percetakan" target="_self" class="active">CETAK /<span>PRINTING</span></a></li>
		<%
		}
		else {
		%>
		<!--  li><a href="get.infoTracerStudy?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=percetakan" target="_self" >CETAK /<span>PRINTING</span></a></li -->
		<li><a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/indexPercetakan.jsp?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=percetakan" target="_self" >CETAK /<span>PRINTING</span></a></li>
		<%
		}	
	}

	//if(validUsr.isAllowTo("hasPengajuanMhsMenu")>0) {
	if(validUsr.isUsrAllowTo("hasPengajuanMhsMenu", v_npmhs, v_obj_lvl)) {	
		if(cmd.equalsIgnoreCase("reqMhs")) {
		%>
		<!--  li><a href="get.infoTracerStudy?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=reqMhs" target="_self" class="active">PENGAJUAN<span></span></a></li -->
		<li><a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/indexPengajuanMhs.jsp?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=reqMhs" target="_self" class="active">PENGAJUAN<span><div style="color:#eee">---</div></span></a></li>
		<%
		}
		else {
		%>
		<!--  li><a href="get.infoTracerStudy?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=reqMhs" target="_self" >CETAK /<span>PRINTING</span></a></li -->
		<li><a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/indexPengajuanMhs.jsp?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=reqMhs" target="_self" >PENGAJUAN<span><div style="color:#eee">---</div></span></a></li>
		<%
		}	
	}


	//if(validUsr.isAllowTo("hasUpDownFileMhs")>0) {
	if(validUsr.isUsrAllowTo("hasUpDownFileMhs", v_npmhs, v_obj_lvl)) {	
		//requestKonversiMakul = true;
		if(cmd.equalsIgnoreCase("upDownFile")) {
			%>
			<!--  li><a href="upAndDownloadFile.jsp?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=upDownFile" target="_self" class="active">DOWNLOAD<span>UPLOAD FILES</span></a></li -->
			
			<li><a href="uploadFile.jsp?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=uploadFile" target="_self" class="active">DOWNLOAD<span>UPLOAD FILES</span></a></li>
			<%
		}
		else {
			%>
			<li><a href="uploadFile.jsp?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=uploadFile" target="_self" >DOWNLOAD<span>UPLOAD FILES</span></a></li>
			<%
		}	
	}
	
	//if(validUsr.isAllowTo("resetUrsPwd")>0) {
	//System.out.println("aneh");	
	//System.out.println("v_npmhs, v_obj_lvl="+v_npmhs+","+v_obj_lvl);
	//System.out.println("validUsr.isUsrAllowTo="+validUsr.isUsrAllowTo("resetUrsPwd", v_npmhs, v_obj_lvl));
	if(validUsr.isUsrAllowTo("resetUrsPwd", v_npmhs, v_obj_lvl)) {	
		//requestKonversiMakul = true;
		String target = null,uri=null,url=null;
		boolean needCekShift = false;
		String[] listKdpstProdi = Constants.getListKdpstProdi();
		for(int i=0;i<listKdpstProdi.length && !needCekShift;i++) {
			if(v_kdpst.equalsIgnoreCase(listKdpstProdi[i])) {
				needCekShift = true;
			}
		}
		if((v_shift==null || v_shift.equalsIgnoreCase("N/A")) && needCekShift) {
			//ngga perlu update shift	
			//update 24 jan 2014 di hapus
			//target = "/InnerFrame/Tamplete/formUpdShiftTampleteTabStyle.jsp";
			target = "go.resetUsrPwd";
		}	
		else {
			target = "go.resetUsrPwd";
		}
		if(cmd.equalsIgnoreCase("resetUsrPwd")) {
			%>
			<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}<%=target %>?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=resetUsrPwd" onclick="return confirm('Anda akan me-RESET user dan password, apakah anda yakin?');" target="_self" class="active">RESET<span>USER / PWD</span></a></li>
			<!--  li><a href="#" onClick="myFunction(<%=target %>,<%=v_id_obj%>,<%=v_nmmhs%>,<%=v_npmhs%>,<%=v_obj_lvl %>,<%=v_kdpst %>,'resetUsrPwd')" target="_self" class="active">RESET<span>USER / PWD</span></a></li -->
			<%
		}
		else {
			%>
			<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}<%=target %>?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=resetUsrPwd" target="_self" onclick="return confirm('Anda akan me-RESET user dan password, apakah anda yakin?');" >RESET<span>USER / PWD</span></a></li>
			<!--  li><a href="#" onClick="myFunction()" target="_self" >RESET<span>USER / PWD</span></a></li -->
			<%
		}	
	}
	

	
	
/*	
	
	if(validUsr.isAllowTo("hasEtestMenu")>0) {
		%>
		<li><a href="go.cekOnlineTest?backTo=<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/index.jsp" target="inner_iframe">PENGATURAN<span>ONLINE TEST</span></a></li>
		<%
	}
*/
	%>
</ul>


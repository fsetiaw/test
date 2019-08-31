<%@ page import="beans.tools.Checker" %>


<%
String stm = (String)session.getAttribute("status_akhir_mahasiswa");
boolean keluar = false;
boolean dropout = false;
boolean non_aktif = false;
if(stm!=null && stm.equalsIgnoreCase("K")) {
keluar = true;
}
if(stm!=null && stm.equalsIgnoreCase("D")) {
dropout = true;
}
if(stm!=null && stm.equalsIgnoreCase("N")) {
non_aktif = true;
}

boolean continuSys = false;
String continuSystemParam = request.getParameter("continuSys");
if(continuSystemParam!=null && continuSystemParam.equalsIgnoreCase("true")) {
	continuSys = true;
}
v = (Vector) request.getAttribute("v_profile");
request.removeAttribute("v_profile");
request.setAttribute("v_profile", v);
if(v!=null) {
	//System.out.println("sizev="+v.size());
}
else {
	//System.out.println("sizev=0");
}

if(validUsr.getObjNickNameGivenObjId().contains("MHS") || validUsr.getObjNickNameGivenObjId().contains("mhs") ) {
	usrMhs = true;
}
//System.out.println("atPages="+atPage);
/*objId
* ada masalah redundan krn v_id_obj = idobj, v_npmhs = npm dst (variable diatas))
*/
String msg = request.getParameter("msg");
String objId = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String kdpst = request.getParameter("kdpst");
String obj_lvl =  request.getParameter("obj_lvl");
String validApprovee = request.getParameter("validApprovee");

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
//System.out.println("v_nimhs "+v_nimhs);
String v_kdjen=(String)request.getAttribute("v_kdjen");
String v_smawl=(String)request.getAttribute("v_smawl");
String v_shift=(String)request.getAttribute("v_shift");
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

	String cmd =  (String)request.getParameter("cmd");
	//System.out.println("cmd = "+cmd);
	boolean allowViewKrs=false, allowEditKrs=false,requestKonversiMakul=false,allowInsSksdi=false;
	boolean allowCetakKrs=false,cetakKrsMode=false,allowInsertKrs=false;
	boolean allowCetakKhs=false,cetakKhsMode=false,allowBatalTambahMk=false;
	boolean paApprover=false;
	if(validUsr.isUsrAllowTo("batalTambahMk", v_npmhs, v_obj_lvl)) {
		allowBatalTambahMk=true;
	}
	if(cmd!=null&&cmd.equalsIgnoreCase("cetakKrs")) {
		cetakKrsMode=true;
	}
	if(cmd!=null&&cmd.equalsIgnoreCase("cetakKhs")) {
		cetakKhsMode=true;
	}
	if(validUsr.isUsrAllowTo("krsPaApproval", v_npmhs, v_obj_lvl)) {
		paApprover = true;
		//System.out.println("pAprroval="+paApprover);
	}
	if(validUsr.isUsrAllowTo("allowCetakKrs", v_npmhs, v_obj_lvl)) {
		allowCetakKrs = true;
	}
	if(validUsr.isUsrAllowTo("allowCetakKhs", v_npmhs, v_obj_lvl)) {
		allowCetakKhs = true;
	}
	
	if(validUsr.isUsrAllowTo("insSksdi", v_npmhs, v_obj_lvl)) {
		allowInsSksdi = true;
	}	
	
	boolean semesteran = true;
	if(!Checker.getSistemPerkuliahan(v_id_obj, v_kdpst).equalsIgnoreCase("semester")) {
		semesteran = false;
	}
%>	
<script>
		
	$(document).ready(function() {
		$("#wait").hide();
		$("#main").show();
	});	
</script>
<script>		
	$(document).ready(function() {
		$(document).on("click", "#histKrs", function() {
        	$.ajax({
        		url: 'go.histKrs',
        		type: 'POST',
        		data: {
        			id_obj: "<%=v_id_obj%>",
        			nmm: "<%=v_nmmhs%>",
        			npm: "<%=v_npmhs%>",
        			obj_lvl: "<%=v_obj_lvl%>",
        			kdpst: "<%=v_kdpst%>",
        			cmd: "histKrs",
        			from: "dashboard"
        	    },
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	        <%
        	        if(Checker.getSistemPerkuliahan(v_id_obj, v_kdpst).equalsIgnoreCase("CONTINUE")) {
        	        %>
            	    window.location.href = "<%=Constants.getRootWeb()+"/" %>InnerFrame/HistKrsKhsContinuSistem.jsp?continuSys=true&id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=histKrs";
            	    <%	
        	        }
        	        else {
        	        %>
        	    	window.location.href = "<%=Constants.getRootWeb()+"/" %>InnerFrame/HistKrsKhs_v2.jsp?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=histKrs";
        	    	<%
        	        }
        	        %>
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });	
	});	
</script>

<%
//cek apa ada parameter backTo dan bila value = history, maka gunakan history -1
	String forceBackTo = (String)session.getAttribute("forceBackTo");
	String backTo = request.getParameter("backTo"); 
	if(usrMhs) {
		String target = Constants.getRootWeb()+"/InnerFrame/Akademik/indexAkademikMhs.jsp";
		String uri = request.getRequestURI();
		//System.out.println(target+" / "+uri);
		String url = PathFinder.getPath(uri, target);
	%>
	<ul>
		<li><a href="<%=target %>?callerPage=<%=Constants.getRootWeb()+"/index.jsp" %>" target="inner_iframe">BACK<span>&nbsp</span></a></li>
	<%		
	}
	else if(forceBackTo!=null && !Checker.isStringNullOrEmpty(forceBackTo)) {
	%>
	<ul>
		<li><a href="<%=forceBackTo %>" target="_self">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
	<%	
	}
	else if(backTo!=null && !Checker.isStringNullOrEmpty(backTo) && backTo.equalsIgnoreCase("history")) {
	%>
	<ul>
		<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
	<%	
	}
	else {
%>
	<ul>
	
		<li><a href="get.profile?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=dashboard" target="_self" >BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
	<%
	}

	if(validUsr.isUsrAllowTo("viewKrs", v_npmhs, v_obj_lvl) || validApprovee.equalsIgnoreCase("true")) {
		allowViewKrs = true;
		/*
		* cek apa user boleh edit krs
		*/
		if(validUsr.isUsrAllowTo("editKrs", v_npmhs, v_obj_lvl)) {
			allowEditKrs=true;
		}
		if(validUsr.isUsrAllowTo("insertKrs", v_npmhs, v_obj_lvl)) {
			allowInsertKrs=true;
		}
	}
	
	//validApprovee ditambahkan sehingga bila berasal dr krs approval hanya menu back saja yg tampil
	
	if(allowViewKrs && (validApprovee==null || (validApprovee!=null && !validApprovee.equalsIgnoreCase("true")))) {
		if(cmd.equalsIgnoreCase("histKrs")) {
			%>
			<li><a href="#" id="histKrs" target="_self" >RIWAYAT<span>KRS/KHS</span></a></li>
			<!--  li><a href="get.histKrs?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=histKrs" target="_self" class="active">RIWAYAT<span>KRS/KHS</span></a></li -->
			<%
		}
		else {
			%>
			<li><a href="#" id="histKrs" target="_self" >RIWAYAT<span>KRS/KHS</span></a></li>
			<!--  li><a href="get.histKrs?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=histKrs" target="_self" >RIWAYAT<span>KRS/KHS</span></a></li -->
			<%
		}
	}
	
	if(validUsr.amI("ADMIN")){
	//if(validUsr.isUsrAllowTo_updated("ink",v_npmhs) && !validUsr.isHakAksesReadOnly("ink")) {
		if(cmd.equalsIgnoreCase("ink")) {
			%>
		<li><a href="get.histKrs?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=ink" target="_self" class="active">INPUT <span>NILAI</span></a></li>
			<%	
		}
		else {
		%>
		<li><a href="get.histKrs?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=ink" target="_self" >INPUT <span>NILAI</span></a></li>
		<%	
		}
	}

//	else {

	
	if(false){
	//if(!continuSys && allowEditKrs && Checker.getIsOperatorAllowEditNilai() && (validApprovee==null || (validApprovee!=null && !validApprovee.equalsIgnoreCase("true")))) {
		if(cmd.equalsIgnoreCase("editKrs")) {
			%>
			<li><a href="get.histKrs?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=editKrs" target="_self" class="active">EDIT KELAS<span>KRS</span></a></li>
			<%
		}
		else {
			%>
			<li><a href="get.histKrs?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=editKrs" target="_self" >EDIT KELAS<span>KRS</span></a></li>
			<%
		}
	}
	
	//akses ke bahan ajar bagi MAHSISWA
	//if(usrMhs) {
	if(validUsr.getObjNickNameGivenObjId().contains("MHS") || validUsr.getObjNickNameGivenObjId().contains("mhs")) {	
		if(cmd!=null && cmd.equalsIgnoreCase("vba")) { //viewBahanAjar
			%>
			<li><a href="get.histKrs?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=vba" target="_self" class="active">BAHAN<span>AJAR</span></a></li>
			<%
		}
		else {
			%>
			<li><a href="get.histKrs?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=vba" target="_self" >BAHAN<span>AJAR</span></a></li>
			<%
		}	
	}
	
	boolean usrBolehUpdateKrs = validUsr.isUsrAllowTo_updated("insertKrs",v_npmhs);
	boolean usrReadKrsOnly = validUsr.isUsrAllowTo_updated("insertKrsReadOnlyMode",v_npmhs);
	if(!continuSys && (validApprovee==null || (validApprovee!=null && !validApprovee.equalsIgnoreCase("true")))) {
		if(usrBolehUpdateKrs) {
			if(validUsr.iAmStu()) {
				if(cmd!=null && cmd.equalsIgnoreCase("insertKrs")) {
				%>
			<li><a href="go.updateKrsKhs_v1?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=insertKrs" target="_self" class="active">PENGAJUAN<span>KRS BARU</span></a></li>
				<%
				}
				else {
					%>
			<li><a href="go.updateKrsKhs_v1?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=insertKrs" target="_self" >PENGAJUAN<span>KRS BARU</span></a></li>
						<%
				}
			}
			else if(!validUsr.iAmStu()) { //untuk operator
				//if(cmd!=null && cmd.equalsIgnoreCase("insertKrs") && !keluar && !dropout) {
				if(cmd!=null && cmd.equalsIgnoreCase("insertKrs")) {	
				%>
			<li><a href="go.updateKrsKhs_v1?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=insertKrs" target="_self" class="active">INSERT / EDIT<span>DATA KRS BARU</span></a></li>
				<%
				}
				else {
					%>
			<li><a href="go.updateKrsKhs_v1?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=insertKrs" target="_self" >INSERT / EDIT<span>DATA KRS BARU</span></a></li>
						<%
				}	
			}
		}
		else if(usrReadKrsOnly) {
			/*
			BUTUH UPDATE NIH UNTUK MAKA
			*/
		
			//read only
			/*
			if(cmd!=null && cmd.equalsIgnoreCase("insertKrs")) {
				
			DITAMBAH VARIABLE READ ONLY = USR SPT WAREK YG BISA MONITORING, TP KALO YG PUNYA KEMAMPUAN UPDATE SHIFT, HRS UPDATE SHIFT
			
			%>
			<li><a href="go.updateKrsKhs_v1?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=insertKrs&readOnly=yes" target="_self" class="active">PERBANDINGAN KRS<span>DENGAN KURIKULUM</span></a>
			<%
			}
			else {
				%>
			<li><a href="go.updateKrsKhs_v1?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=insertKrs&readOnly=yes" target="_self" >PERBANDINGAN KRS<span>DENGAN KURIKULUM</span></a>
				<%
			}
			*/
		}
		else {
			//no akses
		}
	}

	/* 
	if(!continuSys && (allowInsertKrs || validUsr.isAllowTo("insertKrsReadOnlyMode")>0)) {
		if(cmd.equalsIgnoreCase("insertKrs")||cmd.equalsIgnoreCase("insertKrsReadOnlyMode")) {
			if(Checker.getObjName(Integer.valueOf(validUsr.getIdObj()).intValue()).contains("MHS")&& (validApprovee==null || (validApprovee!=null && !validApprovee.equalsIgnoreCase("true")))) {
				%>
				<li><a href="go.updateKrsKhs_v1?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=insertKrs" target="_self" class="active">PENGAJUAN<span>KRS BARU</span></a></li>
				<%
			}
			else {
				if(Checker.getIsOperatorAllowInsKrs()&& (validApprovee==null || (validApprovee!=null && !validApprovee.equalsIgnoreCase("true")))) {
					%>
					<li><a href="go.updateKrsKhs_v1?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=insertKrs" target="_self" class="active">INSERT / EDIT<span>DATA KRS BARU</span></a></li>
					<%
				}
			}
		}
		else {
			//System.out.println("uobjek="+Checker.getObjName(Integer.valueOf(v_id_obj).intValue()));
			
			if(Checker.getObjName(Integer.valueOf(validUsr.getIdObj()).intValue()).contains("MHS") && (validApprovee==null || (validApprovee!=null && !validApprovee.equalsIgnoreCase("true")))) {
				%>
				<li><a href="go.updateKrsKhs_v1?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=insertKrs" target="_self" >PENGAJUAN<span>KRS BARU</span></a></li>
				<%
			}
			else {
				if(validUsr.isAllowTo("insertKrsReadOnlyMode")>0 && (validApprovee==null || (validApprovee!=null && !validApprovee.equalsIgnoreCase("true")))) {
				%>
				<li><a href="go.updateKrsKhs_v1?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=insertKrs" target="_self" >PERBANDINGAN KRS<span>DENGAN KURIKULUM</span></a>
				<%	
				}
				else {
					if(Checker.getIsOperatorAllowInsKrs() && (validApprovee==null || (validApprovee!=null && !validApprovee.equalsIgnoreCase("true")))) {
			%>
				<li><a href="go.updateKrsKhs_v1?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=insertKrs" target="_self" >INSERT / EDIT<span>DATA KRS BARU</span></a></li>
				<%	
					}
				}	
			}
					%>
			</li>
					<%

		}
	}	
	
	//System.out.println("allowInsSksdi="+allowInsSksdi);
	//System.out.println("v_stpid="+v_stpid);
	//hanya keluar bila pindahan
	//System.out.println("v_stpid="+v_stpid);
	if(allowInsSksdi&&v_stpid!=null&&v_stpid.equalsIgnoreCase("P") && (validApprovee==null || (validApprovee!=null && !validApprovee.equalsIgnoreCase("true")))) {
		if(cmd.equalsIgnoreCase("insSksdi")) {
			%>
			
			<li><a href="go.defaultVprofileRoute?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&aspti=<%=v_aspti %>&aspst=<%=v_aspst %>&cmd=insSksdi&fwdTo=toNonJsp.gatewayMhsPindahan&fwdPg=<%=beans.setting.Constants.getRootWeb()+"" %>${PageContext.ServletContext.ContextPath}/InnerFrame/indexMhsPindahan.jsp" target="_self" class="active">DATA MHS<span>PINDAHAN</span></a></li>
			<!--  li><a href="go.gatewayMhsPindahan?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&aspti=<%=v_aspti %>&aspst=<%=v_aspst %>&cmd=insSksdi" target="_self" class="active">DATA MHS<span>PINDAHAN</span></a></li -->
			<%
		}
		else {
			%>
			<li><a href="go.defaultVprofileRoute?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&aspti=<%=v_aspti %>&aspst=<%=v_aspst %>&cmd=insSksdi&fwdTo=toNonJsp.gatewayMhsPindahan&fwdPg=<%=beans.setting.Constants.getRootWeb()+"" %>${PageContext.ServletContext.ContextPath}/InnerFrame/indexMhsPindahan.jsp" target="_self">DATA MHS<span>PINDAHAN</span></a></li>
			<!--  li><a href="go.gatewayMhsPindahan?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&aspti=<%=v_aspti %>&aspst=<%=v_aspst %>&cmd=insSksdi" target="_self" >DATA MHS<span>PINDAHAN</span></a></li -->
			<%
		}
	}
	*/
	if(validUsr.isUsrAllowTo("cmd1", v_npmhs, v_obj_lvl)) {	
		//requestKonversiMakul = true;
		if(cmd.equalsIgnoreCase("atur")) {
			%>
			<!--  li><a href="upAndDownloadFile.jsp?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=upDownFile" target="_self" class="active">DOWNLOAD<span>UPLOAD FILES</span></a></li -->
			<li><a href="<%=Constants.getRootWeb() %>/InnerFrame/krsWhiteList.jsp?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=atur" target="_self" class="active">PEMUTIHAN THSMS<span>PENGISIAN KRS</span></a></li>
			<%
		}
		else {
			%>
			<li><a href="<%=Constants.getRootWeb() %>/InnerFrame/krsWhiteList.jsp?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=atur" target="_self" >PEMUTIHAN THSMS<span>PENGISIAN KRS</span></a></li>
			<%
		}	
	}
	
	%>
</ul>
<%
session.removeAttribute("forceBackTo");
%>

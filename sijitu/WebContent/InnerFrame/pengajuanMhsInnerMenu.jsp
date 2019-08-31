

<ul>

<li>
<%
if(am_i_stu!=null && am_i_stu.equalsIgnoreCase("true")) {
%>	
	<a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK <span><b style="color:#eee">---</b></span></a>
<%
}
else {
%>
	<!--  a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK <span><b style="color:#eee">---</b></span></a-->
	<a href="get.profile?npm=<%=npm %>&cmd=dashboard&nmm=<%=nmm %>&id_obj=<%=id_obj%>&kdpst=<%=kdpst %>&obj_lvl=<%=obj_lvl %>" target="_self">BACK <span><b style="color:#eee">---</b></span></a>
<%
}
%>
</li>
<%
boolean reqByOpr = false;
if(validUsr.isUsrAllowTo_updated("regByOpr", npm)) {
	reqByOpr = true;
}

//if(reqByOpr) {
if(validUsr.isUsrAllowTo_updated("hasPengajuanMhsMenu", npm)) {	
	
	
	if(cmd.equalsIgnoreCase("heregistrasi")) {
%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}InnerFrame/Prakuliah/Mhs/DaftarUlangByOpr.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=heregistrasi" target="_self" class="active">DAFTAR<span>ULANG</span></a></li>
<%
	}
	else {
%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}InnerFrame/Prakuliah/Mhs/DaftarUlangByOpr.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=heregistrasi" target="_self">DAFTAR<span>ULANG</span></a></li>
<%
	}	
}

if(validUsr.isUsrAllowTo_updated("ua", npm)) {
	//System.out.println("allow");
	if(cmd.equalsIgnoreCase("ua")) {
		///ToUnivSatyagama/WebContent/InnerFrame/Pengajuan/DashUjianAkhir.jsp
%>
	<li><a href="go.moPp?target_thsms=<%=ToolMhs.getThsmsPengajuanStmhs()%>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm%>&obj_lvl=<%=obj_lvl%>&kdpst=<%=kdpst%>&cmd=ua&folder_pengajuan=ujian_akhir&scope=ua&table=UJIAN_AKHIR_RULES" target="_self" class="active">UJIAN AKHIR<span>/ SIDANG</span></a></li>
<%
	//moCuti = riwayat cuti mhs 
	}
	else {
%>
	<li><a href="go.moPp?target_thsms=<%=ToolMhs.getThsmsPengajuanStmhs()%>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm%>&obj_lvl=<%=obj_lvl%>&kdpst=<%=kdpst%>&cmd=ua&folder_pengajuan=ujian_akhir&scope=ua&table=UJIAN_AKHIR_RULES" target="_self">UJIAN AKHIR<span>/ SIDANG</span></a></li>
<%
	}	
}


if(validUsr.isUsrAllowTo_updated("btstu", npm)) {
	//System.out.println("allow");
	if(cmd.equalsIgnoreCase("btstu")) {
		///ToUnivSatyagama/WebContent/InnerFrame/Pengajuan/DashUjianAkhir.jsp
%>
	<li><a href="go.moPp?target_thsms=<%=ToolMhs.getThsmsPengajuanStmhs()%>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm%>&obj_lvl=<%=obj_lvl%>&kdpst=<%=kdpst%>&cmd=btstu&folder_pengajuan=aktif_kembali&scope=btstu&table=AKTIF_KEMBALI_RULES" target="_self" class="active">AKTIF KEMBALI /<span>LEWAT BATAS STUDI</span></a></li>
<%
	//moCuti = riwayat cuti mhs 
	}
	else {
%>
	<li><a href="go.moPp?target_thsms=<%=ToolMhs.getThsmsPengajuanStmhs()%>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm%>&obj_lvl=<%=obj_lvl%>&kdpst=<%=kdpst%>&cmd=btstu&folder_pengajuan=aktif_kembali&scope=btstu&table=AKTIF_KEMBALI_RULES" target="_self">AKTIF KEMBALI /<span>LEWAT BATAS STUDI</span></a></li>
<%
	}	
}

//if(validUsr.isUsrAllowTo_updated("ua", npm)) {
if(false) {	
	if(cmd.equalsIgnoreCase("ua")) {
		///ToUnivSatyagama/WebContent/InnerFrame/Pengajuan/DashUjianAkhir.jsp
%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}InnerFrame/Pengajuan/DashUjianAkhir_part1.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=ua" target="_self" class="active">UJIAN<span>AKHIR</span></a></li>
<%
	}
	else {
%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}InnerFrame/Pengajuan/DashUjianAkhir_part1.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=ua" target="_self">UJIAN<span>AKHIR</span></a></li>
<%
	}	
}
/*
	AWALNYA DIDESIGN PENGAJUAN CUTI, TAPI UDAH DIGANTI ,UMGKIN BISA UNTUK DIGUNAKAN KEMUADIAN UNTUK PENGATURAN STATUS TRLSM 
*/
//if(validUsr.isUsrAllowTo_updated("trlsm", npm)) {
if(false) {	
	if(cmd.equalsIgnoreCase("trlsm")) {
		///ToUnivSatyagama/WebContent/InnerFrame/Pengajuan/DashUjianAkhir.jsp
%>
	<li><a href="go.prepAjuanCuti?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm%>&obj_lvl=<%=obj_lvl%>&kdpst=<%=kdpst%>&cmd=cuti" target="_self" class="active">CUTI<span>KULIAH</span></a></li>
<%
	}
	else {
%>
	<li><a href="go.prepAjuanCuti?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm%>&obj_lvl=<%=obj_lvl%>&kdpst=<%=kdpst%>&cmd=cuti" target="_self">CUTI<span>KULIAH</span></a></li>
<%
	}	
}
//System.out.println("allow cuti "+npm);
if(validUsr.isUsrAllowTo_updated("cuti", npm)) {
	//System.out.println("allow");
	if(cmd.equalsIgnoreCase("cuti")) {
		///ToUnivSatyagama/WebContent/InnerFrame/Pengajuan/DashUjianAkhir.jsp
%>
	<li><a href="go.moPp?target_thsms=<%=ToolMhs.getThsmsPengajuanStmhs()%>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm%>&obj_lvl=<%=obj_lvl%>&kdpst=<%=kdpst%>&cmd=cuti&folder_pengajuan=cuti&scope=cuti&table=CUTI_RULES" target="_self" class="active">CUTI<span>KULIAH</span></a></li>
<%
	//moCuti = riwayat cuti mhs 
	}
	else {
%>
	<li><a href="go.moPp?target_thsms=<%=ToolMhs.getThsmsPengajuanStmhs()%>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm%>&obj_lvl=<%=obj_lvl%>&kdpst=<%=kdpst%>&cmd=cuti&folder_pengajuan=cuti&scope=cuti&table=CUTI_RULES" target="_self">CUTI<span>KULIAH</span></a></li>
<%
	}	
}


if(validUsr.isUsrAllowTo_updated("lulus", npm)) {	
	if(cmd.equalsIgnoreCase("lulus")) {
		///ToUnivSatyagama/WebContent/InnerFrame/Pengajuan/DashUjianAkhir.jsp
%>
	<li><a href="go.moPp?target_thsms=<%=ToolMhs.getThsmsPengajuanStmhs()%>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm%>&obj_lvl=<%=obj_lvl%>&kdpst=<%=kdpst%>&cmd=lulus&folder_pengajuan=kelulusan&scope=lulus&table=KELULUSAN_RULES" target="_self" class="active">KELULUSAN<span>& IJAZAH</span></a></li>
<%
	//moCuti = riwayat cuti mhs 
	}
	else {
%>
	<li><a href="go.moPp?target_thsms=<%=ToolMhs.getThsmsPengajuanStmhs()%>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm%>&obj_lvl=<%=obj_lvl%>&kdpst=<%=kdpst%>&cmd=lulus&folder_pengajuan=kelulusan&scope=lulus&table=KELULUSAN_RULES" target="_self">KELULUSAN<span>& IJAZAH</span></a></li>
<%
	}	
}
	
if(validUsr.isUsrAllowTo_updated("reqPindahJurusan", npm)) {	
	if(cmd.equalsIgnoreCase("pp")) {
		///ToUnivSatyagama/WebContent/InnerFrame/Pengajuan/DashUjianAkhir.jsp
%>
	<li><a href="go.moPp?target_thsms=<%=ToolMhs.getThsmsPengajuanStmhs()%>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm%>&obj_lvl=<%=obj_lvl%>&kdpst=<%=kdpst%>&cmd=pp&folder_pengajuan=pindah_prodi&scope=reqPindahJurusan&table=PINDAH_PRODI_RULES" target="_self" class="active">PINDAH<span>PRODI</span></a></li>
<%
	//moCuti = riwayat cuti mhs 
	}
	else {
%>
	<li><a href="go.moPp?target_thsms=<%=ToolMhs.getThsmsPengajuanStmhs()%>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm%>&obj_lvl=<%=obj_lvl%>&kdpst=<%=kdpst%>&cmd=pp&folder_pengajuan=pindah_prodi&scope=reqPindahJurusan&table=PINDAH_PRODI_RULES" target="_self">PINDAH<span>PRODI</span></a></li>
<%
	}	
}
	
	
if(validUsr.isUsrAllowTo_updated("out", npm)) {	
	if(cmd.equalsIgnoreCase("out")) {
%>
	<li><a href="go.moPp?awal_pengajuan=true&target_thsms=<%=ToolMhs.getThsmsPengajuanStmhs()%>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm%>&obj_lvl=<%=obj_lvl%>&kdpst=<%=kdpst%>&cmd=out&folder_pengajuan=keluar&scope=out&table=KELUAR_RULES" target="_self" class="active">KELUAR<span>&nbsp</span></a></li>
	<%
		//moCuti = riwayat cuti mhs 
			}
			else {
	%>
	<li><a href="go.moPp?awal_pengajuan=true&target_thsms=<%=ToolMhs.getThsmsPengajuanStmhs()%>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=out&folder_pengajuan=keluar&scope=out&table=KELUAR_RULES" target="_self">KELUAR<span>&nbsp</span></a></li>
	<%
	}	
}
	
	//
	
//}
%>
	
</ul>


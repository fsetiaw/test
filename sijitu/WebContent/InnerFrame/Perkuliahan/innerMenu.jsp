<%String tab = request.getParameter("tab");
//System.out.println("tab="+tab);
//&&(tab!=null && tab.equalsIgnoreCase("pre"));

String group_proses = request.getParameter("group_proses");
Vector list_kdpst_sad = validUsr.getScopeUpd7des2012ProdiOnlyButKeepOwn("sad");
//Vector list_kdpst_ink = validUsr.getScopeUpd7des2012ReturnDistinctKdpst("ink");
Vector list_kdpst_ink = validUsr.getScopeUpd7des2012ProdiOnlyButKeepOwn("ink");
String atMenu = request.getParameter("atMenu");
SearchDbClassPoll scp = new SearchDbClassPoll(validUsr.getNpm());
boolean am_i_stu= validUsr.iAmStu();

/*
!!!!!!!!!!!!
harusnya utk f(getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya) cukup dengan yg limit 1 krn nanti diprocess lag di servlet
!!!!!!!!!!!!!!!!!
*/
//int limit = 6; //starting value
//int offset = 0; //starting value
Vector vKlsAjar = scp.getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya(list_kdpst_sad, ToolMhs.getThsmsNow());
//Vector vKlsPenilaian = scp.getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya(list_kdpst_ink, Checker.getThsmsInputNilai(), limit, offset);
Vector vKlsPenilaian = scp.getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya(list_kdpst_ink, ToolMhs.getThsmsInputNilai());
if(group_proses!=null && group_proses.equalsIgnoreCase("monitorNilaiTunda")) {
	vKlsPenilaian = (Vector) session.getAttribute("vListKelasDgnNilaiTunda");
}

boolean bolehEdit = false;
boolean bolehEditNilai = false;
//if(scopeHakAkses.contains("`e`")||scopeHakAkses.contains("`i`")) {
if(vKlsAjar!=null) {	
	bolehEdit = true;
}
if(vKlsPenilaian!=null && !validUsr.isHakAksesReadOnly("ink")) {	
	bolehEditNilai = true;
}
//System.out.println("vklsAjar = "+vKlsAjar.size());
//System.out.println("bolehEdit = "+bolehEdit);
//System.out.println("bolehEditNilai = "+bolehEditNilai);%>
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<META HTTP-EQUIV="EXPIRES" CONTENT="Mon, 22 Jul 2002 11:12:01 GMT">

<ul>
	<%
		if(group_proses!=null && group_proses.equalsIgnoreCase("monitorNilaiTunda")) {
			//group_proses=monitorNilaiTunda
	%>
	<li><a href="get.notifications" target="_self">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
	<%
		}
		else {
			//String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/DashPerkuliahan.jsp";
			//String uri = request.getRequestURI();
			//String url = PathFinder.getPath(uri, target);	
			if(ToolMhs.isStringNullOrEmpty(atMenu))	{
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/indexPerkuliahan.jsp" target="_self">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
		<%
			}
				else {
		%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/indexPerkuliahan.jsp" target="_self">BACK <span><b style="color:#eee">&nbsp</b></span></a></li>
				<%
					}
						if(validUsr.isAllowTo("jadwal")>0  &&(tab!=null && tab.equalsIgnoreCase("pre"))) {
					if(atMenu!=null && atMenu.equalsIgnoreCase("jadwal")) {
				%>
	<li><a href="get.getListOpenedClass?tab=<%=tab%>&cmd=jadwal&atMenu=jadwal&targetThsms=<%=ToolMhs.getThsmsNow()%>" target="_self" class="active">PENJADWALAN<span>KELAS PERKULIAHAN</span></a></li>
			<%
				}
				else {
			%>
	<li><a href="get.getListOpenedClass?tab=<%=tab%>&cmd=jadwal&atMenu=jadwal&targetThsms=<%=ToolMhs.getThsmsNow()%>" target="_self">PENJADWALAN<span>KELAS PERKULIAHAN</span></a></li>
			<%
				}
				
				if(atMenu!=null && atMenu.equalsIgnoreCase("tugas")&&(tab!=null && tab.equalsIgnoreCase("pre"))) {
			%>
		<li><a href="get.prepPenugasanDsn?tab=<%=tab%>&cmd=tugas&atMenu=jadwal&target_thsms=20161" target="_self" class="active">PENUGASAN<span>DOSEN AJAR</span></a></li>
				<%
					}
					else {
				%>
		<li><a href="get.prepPenugasanDsn?tab=<%=tab%>&cmd=tugas&atMenu=jadwal&target_thsms=20161" target="_self">PENUGASAN<span>DOSEN AJAR</span></a></li>
				<%
					}
						
						}
					
						if(validUsr.isAllowTo("viewAbsen")>0&&(tab!=null && tab.equalsIgnoreCase("wip"))) {
					if(atMenu!=null && atMenu.equalsIgnoreCase("absensi")) {
				%>
	<li><a href="get.getListOpenedClass?tab=<%=tab%>&cmd=viewAbsen&atMenu=absensi&targetThsms=<%=ToolMhs.getThsmsNow()%>" target="_self" class="active">DAFTAR<span>ABSENSI</span></a></li>
			<%
				}
				else {
			%>
	<li><a href="get.getListOpenedClass?tab=<%=tab%>&cmd=viewAbsen&atMenu=absensi&targetThsms=<%=ToolMhs.getThsmsNow()%>" target="_self">DAFTAR<span>ABSENSI</span></a></li>
			<%	
			}
		
		}
	
		if(validUsr.isAllowTo("hasKartuUjianMenu")>0&&(tab!=null && tab.equalsIgnoreCase("wip"))) {
			if(atMenu!=null && atMenu.equalsIgnoreCase("kartuUjian")) {
			%>
	<li><a href="get.listTipeUjian?tab=<%=tab %>&" target="_self" class="active">KARTU<span>UJIAN</span></a></li>
			<%	
			}
			else {
			%>
	<li><a href="get.listTipeUjian?tab=<%=tab %>&" target="_self">KARTU<span>UJIAN</span></a></li>
			<%	
			}	
		}
	//kalo dosen pengajar otomatis ada menu ini
	//if(validUsr.isAllowTo("ink")>0 || (vKlsPenilaian!=null && vKlsPenilaian.size()>0)) {
		if(bolehEditNilai &&(tab!=null && tab.equalsIgnoreCase("wip"))) {	
			if(atMenu!=null && atMenu.equalsIgnoreCase("inputNilai")) {
			//<a href="getClasPol.statusKehadiranKelas?atMenu=inputNilai" target="_self" class="active">INPUT<span>NILAI</span></a></li>
			%>
	<li><a href="getClasPol.statusKehadiranKelas?tab=<%=tab %>&atMenu=inputNilai&bolehEditNilai=<%=bolehEditNilai %>" target="_self" class="active">INPUT<span>NILAI</span></a></li>
			<%	
			}
			else {
			%>
	<li><a href="getClasPol.statusKehadiranKelas?tab=<%=tab %>&atMenu=inputNilai&bolehEditNilai=<%=bolehEditNilai %>" target="_self">INPUT<span>NILAI</span></a></li>
			<%	
			}	
		}
	
	//status absensi dosen
		if((validUsr.isAllowTo("sad")>0 || (vKlsAjar!=null && vKlsAjar.size()>0))&&(tab!=null && tab.equalsIgnoreCase("wip"))) {
			if(atMenu!=null && atMenu.equalsIgnoreCase("kehadiran")) {
			%>
	<li><a href="getClasPol.statusKehadiranKelas?tab=<%=tab %>&atMenu=kehadiran" target="_self" class="active">STATUS KEHADIRAN<span>DOSEN AJAR</span></a></li>
			<%	
			}
			else {
			%>
			
	<li><a href="getClasPol.statusKehadiranKelas?tab=<%=tab %>&atMenu=kehadiran" target="_self">STATUS KEHADIRAN<span>DOSEN AJAR</span></a></li>
			<%	
			}	
		}
	
	
		if((validUsr.isAllowTo("ua")>0 || am_i_stu )&&(tab!=null && tab.equalsIgnoreCase("wip"))) {
			if(atMenu!=null && atMenu.equalsIgnoreCase("ua")) {
			%>
	<li><a href="get.pengajuanUa?tab=<%=tab %>&atMenu=ua" target="_self" class="active">PENGAJUAN<span>UJIAN AKHIR</span></a></li>
			<%	
			}
			else {
			%>
			
	<li><a href="get.pengajuanUa?tab=<%=tab %>&atMenu=ua" target="_self">PENGAJUAN<span>UJIAN AKHIR</span></a></li>
			<%	
			}	
		}
		if(validUsr.isAllowTo("pba")>0 &&(tab!=null && tab.equalsIgnoreCase("pre"))) {
		//String target = Constants.getRootWeb()+"/InnerFrame/Akademik/dashPengajuan.jsp";
		//String uri = request.getRequestURI();
		//String url = PathFinder.getPath(uri, target);
			if(atMenu!=null && atMenu.equalsIgnoreCase("pba")) {
		%>		
	<li><a href="get.kelasYgDiajar?tab=<%=tab %>&atMenu=pba" target="_self" class="active">PENGAJUAN RANCANGAN<span>BAHAN AJAR</b></span></a></li>
		<%		
			}
			else {
		%>		
	<li><a href="get.kelasYgDiajar?tab=<%=tab %>&atMenu=pba" target="_self">PENGAJUAN RANCANGAN<span>BAHAN AJAR</b></span></a></li>
		<%
			}
	
		}
	}
	%>
</ul>


<!DOCTYPE html>
<!--
HALAMAN INI MENAMPILKAN LIST SELURUH STANDAR YG ADA
APAKAH SUDAH DIFILTER BERDASAR LINGKUP STANDAR (PRODI/FAK/UNIVERSITAS)? BELUM TAU
ADA SESSION VARIABLE 
1.spmi_editor, BERDASARKAN SCOPE R,E,I,D
2.terkait (spmi editor & biro penjaminan otomatis terkait)
3.pengawas (spmi editor & biro penjaminan otomatis pengaws)
!!! session variable diatas digunakan hanya untuk keperluan anggota spmi sehingga tidak perlu ada pengecekan lagi!!!!!! 
jadi kalo user biasa bila variable diatas = false, mka lakukan pengecekan secara individu
 
 -->
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.*"%>

<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SATYAGAMA</title>
<%
//System.out.println("sampe lseini");
//-------------------------------------------------
String ua=request.getHeader("User-Agent").toLowerCase();
boolean mobile=false;
if(ua.matches("(?i).*((android|bb\\d+|meego).+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\\.(browser|link)|vodafone|wap|windows ce|xda|xiino).*")||ua.substring(0,4).matches("(?i)1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\\-|your|zeto|zte\\-")) {
  //response.sendRedirect("http://detectmobilebrowser.com/mobile");
  //return;	
  mobile = true;
  
}
//--------------------------------------------

//System.out.println("home standr");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
//String check_page = request.getParameter("at_page");
//if(!Checker.isStringNullOrEmpty(check_page)) {
//String id_master = request.getParameter("id");
String tmp_at_page = request.getParameter("at_page");
if(Checker.isStringNullOrEmpty(tmp_at_page)) {
	tmp_at_page="1";
}	
String tmp_max_data_per_pg = request.getParameter("max_data_per_pg");
if(Checker.isStringNullOrEmpty(tmp_max_data_per_pg)) {
	tmp_max_data_per_pg=""+Constant.getMax_data_per_pg();
}	
int at_page = Integer.parseInt(tmp_at_page);
int max_pg_tampil = 10;
int max_data_per_pg = Integer.parseInt(tmp_max_data_per_pg);
int tot_data = 0;	
//}
String backTo = request.getParameter("backTo");
String mode = request.getParameter("mode");
//System.out.println("backto="+backTo);
//System.out.println("mode="+mode);
String first_index = request.getParameter("first_index");
Vector v= (Vector)request.getAttribute("v");
if(v==null || (v!=null && v.size()<1)) {
	//dari servlet /ToUnivSatyagama/src/servlets/spmi/GetListAllStandard.java
	v= (Vector)session.getAttribute("v");
}
else {
	session.setAttribute("v",v);	
}
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
String id_master_title = request.getParameter("id");
String id_tipe_std = request.getParameter("id_tipe_std");
//System.out.println("id_master2="+id_master_title);
//System.out.println("id_tipe_std2="+id_tipe_std);
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
Vector v_scope_kdpst_spmi = (Vector)session.getAttribute("v_scope_kdpst_spmi");//KDFAKMSPST,KDPSTMSPST,KDJENMSPST,NMPSTMSPST
Boolean spmi_editor = (Boolean) session.getAttribute("spmi_editor");// asalnya !isu.isHakAksesReadOnly("hasSpmiMenu");
boolean editor = spmi_editor.booleanValue();
//
boolean terkait = false;
boolean pengawas = false;
//bagi BIRO PENJAMINAN MUTU
if(editor || validUsr.amI("BIRO PENJAMINAN MUTU")) {
	editor = true; //terkait & pengaeas
	terkait = true;
	pengawas = true;
}
//session.setAttribute("terkait", ""+terkait);
//session.setAttribute("pengawas", ""+pengawas);
SearchSpmi ss = new SearchSpmi();
Vector v_list_std = ss.getListStandar();
%>
<style>
a.img {
	float: left;
    //height: 120px;
    margin-bottom: -25px;
    margin-left: 9px;
    position: relative;
    //width: 147px;
    //background-color: rgba(0, 0, 0, 0.5);
    border-radius: 3px;
}
a.img:hover {
	text-decoration: none;
	background:none;
	
}

a.img2 {
	float: right;
    //height: 120px;
    margin-bottom: -25px;
    margin-right: 9px;
    position: relative;
    //width: 147px;
    //background-color: rgba(0, 0, 0, 0.5);
    border-radius: 3px;
}
a.img2:hover {
	text-decoration: none;
	background:none;
	
}

a.teks {
	float: right;
    //height: 120px;
    margin-bottom: -10px;
    margin-left: 9px;
    position: relative;
    //width: 147px;
    //background-color: rgba(0, 0, 0, 0.5);
    //border-radius: 10px;
}
a.teks:hover {
	text-decoration: none;
	background:none;
	
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
.table:hover td { background:#82B0C3 }
</style>
<style>
.table1 {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
}
.table1 thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table1 thead > tr > th, .table1 tbody > tr > t-->h, .table1 tfoot > tr > th, .table1 thead > tr > td, .table1 tbody > tr > td, .table1 tfoot > tr > td { border: 1px solid #2980B9; }

.table1-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table1-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table1-noborder thead > tr > th, .table1-noborder tbody > tr > th, .table1-noborder tfoot > tr > th, .table1-noborder thead > tr > td, .table1-noborder tbody > tr > td, .table1-noborder tfoot > tr > td { border: none;padding: 2px }


</style>
<script>
	$(document).ready(function() {
		$("#wait").hide();
		$("#main").show();
	});	
</script>
<script>	
	
	$(document).ready(function() {
	<%
	for(int i=1;i<25;i++) {
	%>
		$("#copyStd<%=i%>").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		if(confirm('Konfirmasi meng-copy standard')==false) {
	   			return false;    
	        }
	        $.ajax({
        		url: 'go.copyStd',
        		type: 'POST',
        		data: $("#copyStd<%=i%>").serialize(),
        	    beforeSend:function(){
        	    	
        	    	$("#wait").show();
        	    	
        	    	$("#main").hide();
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	        
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/home_standar.jsp?id_master_std=0&id=0&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=<%=at_page%>&max_data_per_pg=<%=max_data_per_pg%>";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	<%
	}
	%>
	
	<%
	for(int i=1;i<25;i++) {
	%>
		//a href
		$(document).on("click", "#aktifkan<%=i%>", function() {
        	$.ajax({
        		url: 'go.dummy',
        		type: 'POST',
        		data: {
        			kdpst_nmpst_kmp: '<%=kdpst_nmpst_kmp%>' //ignore karena sebenarnya dari wondow.loca.href
        	    },
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	        window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/home_standar.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });	
		<%
	}
	%>	
		
	});	
    </script>

</head>
<body>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai</p>
</div>
<div id="main">

<jsp:include page="menu.jsp" />

<div class="colmask fullpage">
	<section style="text-align:center;font-size:2em;font-weight:bold">
	<%=Checker.pnn(Getter.getNamaTipeStandar(id_master_title, id_tipe_std))   %>
	<%
	if(true) {
	%> 
	<%=Converter.getNamaKdpstDanJenjang(kdpst) %>
	<%
	}
	%>
	</section>
	
	
		
		
	<section style="text-align:center">	
		<jsp:include page="navigasi.jsp" flush="true" />
	</section>

		<!-- Column 1 start -->
		<%
		
			

		if(v!=null && v.size()>0) {
			%>
			<br><br>

			<table class="table" style="margin:auto;width:90%">
			<thead>
			<tr>
			  <th colspan="4" style="text-align: center; padding: 0px 10px;font-size:1.5em">LIST STANDAR</th>
			  </tr>
			  <tr>
			  <th width="5%">NO</th>
			  <th width="20%">LINGKUP STD</th>
			  <th width="75%">ISI STANDAR</th>
			  </tr>
			  </thead>
			  <tbody>
			<%	
				ListIterator li = v.listIterator();
				int urutan_pertama = 0;
				int urutan_akhir = 0;
				int norut=0;
				if(at_page<2) {
					urutan_pertama=1;
					urutan_akhir = max_data_per_pg;
				}
				else {
					urutan_pertama=((at_page-1)*max_data_per_pg)+1;
					urutan_akhir = urutan_pertama+max_data_per_pg-1;
				}
				boolean done=false;
				//System.out.println("urutan_pertama="+urutan_pertama);
				//System.out.println("urutan_akhir="+urutan_akhir);
				while(li.hasNext() && !done) {
					norut++;
					String brs = (String)li.next();
					//id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey;
					if(norut>=urutan_pertama && norut<=urutan_akhir ) {
						//out.println(norut+". "+brs+"<br><br>");
						//System.out.println(norut+"->tampil");
						st = new StringTokenizer(brs,"`");
						String id_versi = st.nextToken();
						String id_std_isi = st.nextToken();
						String id_std = st.nextToken();
						String isi_std = st.nextToken();
						String butir = st.nextToken();
						String std_kdpst = st.nextToken();
						String rasionale = st.nextToken();
						String tgl_sta = st.nextToken();
						String tgl_end = st.nextToken();
						String target1 = st.nextToken();
						String target2 = st.nextToken();
						String target3 = st.nextToken();
						String target4 = st.nextToken();
						String target5 = st.nextToken();
						String target6 = st.nextToken();
						String tkn_pihak = st.nextToken();
						tkn_pihak = tkn_pihak.replace("null", "");
						tkn_pihak = tkn_pihak.replace(",,", "");
						String tkn_dokumen = st.nextToken();
						tkn_dokumen = tkn_dokumen.replace("null", "");
						tkn_dokumen = tkn_dokumen.replace(",,", "");
						String tkn_indikator = st.nextToken();
						String periode_start = st.nextToken();
						String unit_used_by_periode_start = st.nextToken();
						String besaran_interval_per_period = st.nextToken();
						String unit_used_byTarget = st.nextToken();
						String tkn_pengawas = st.nextToken();
						tkn_pengawas = tkn_pengawas.replace("null", "");
						tkn_pengawas = tkn_pengawas.replace(",,", "");
						String tkn_param = st.nextToken();
						String aktif = st.nextToken();
						String scope_std = st.nextToken();
						String tgl_mulai_aktif = st.nextToken();
						String tgl_stop_aktif = st.nextToken();
						boolean disabled = false;
						if(!Checker.isStringNullOrEmpty(tgl_stop_aktif)) {
							disabled = true;
						}
						String tipe_proses_pengawasan = st.nextToken();
						String info_status_kdpst = "&nbsp[SEMUA PRODI]";
						if(!Checker.isStringNullOrEmpty(std_kdpst)) {
							info_status_kdpst= "&nbsp["+Converter.getDetailKdpst_v1(std_kdpst)+"]";
							//remove kdpst yg sudah ada
							if(v_scope_kdpst_spmi!=null && v_scope_kdpst_spmi.size()>0) {
								boolean match = false;
								ListIterator litmp = v_scope_kdpst_spmi.listIterator();
								while(litmp.hasNext()&&!match) {
									String baris_info = (String)litmp.next();
									if(baris_info.startsWith(std_kdpst+"`")) {
										litmp.remove();
										match = true;
									}
								}
								if(v_scope_kdpst_spmi!=null && v_scope_kdpst_spmi.size()<1) {
									v_scope_kdpst_spmi=null;
								}
							}
						}
						
						String id_master = st.nextToken();
						String id_tipe = st.nextToken();
						
						
						//if(!editor && !terkait) {
						//	terkait = validUsr.amIcontain(tkn_pihak);
						//}
						//if(!editor && !pengawas) {
						//	pengawas = validUsr.amIcontain(tkn_pengawas);
						//}
						
			%>
					<!--  tr onclick="window.open('get.prepInfoStd?am_i_terkait=<=terkait %>&am_i_pengawas=<=pengawas%>&id_versi=<=id_versi %>&id_std_isi=<=id_std_isi %>&id_std=<=id_std %>&kdpst_nmpst_kmp=<=kdpst_nmpst_kmp %>&fwdto=dashboard_std_isi.jsp','popup','width=850,height=600')" -->
					<tr onclick="window.location.href='<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_creator/form/std_isi/edit_index_unknown.jsp?first_index=<%=first_index %>&mode=<%=mode%>&id_master=<%=id_master %>&id_tipe=<%=id_tipe %>&id_std_isi=<%=id_std_isi%>&id_versi=<%=id_versi%>&atMenu=edit_isi&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>&at_page=<%=at_page %>&max_data_per_pg=<%=max_data_per_pg %>&backTo=<%=backTo %>'">
						<td align="center" style="vertical-align: middle; padding: 5px 0px"><%=norut %></td>
						<td align="center" style="vertical-align: middle; padding: 5px 0px"><%=info_status_kdpst %></td>
						<td align="left" style="vertical-align: middle; padding: 5px 5px"><%=isi_std %></td>
					</tr>
			<%		
						
					}
					if(norut>urutan_akhir) {
						done=true;
					}

					
				}
					%>
				</tbody>
			</table>	

			<%
			}
			else {
			%>
					<div style="font-weight:bold;font-size:1.3em;padding:10px 100px">
						Hasil Pencarian : 0 data
					</div>
			<%
			}
			%>		
		<!-- Column 1 start -->
		
	</div>
</div>	
</div>	
</body>
</html>
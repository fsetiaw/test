<!DOCTYPE html>
<html>
<head lang="en">
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="beans.sistem.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>
	<%@ page import="org.codehaus.jettison.json.JSONArray" %>
	<%@ page import="org.codehaus.jettison.json.JSONObject" %>
	<%@ page import="org.codehaus.jettison.json.JSONException" %>
	<%@ page import="org.owasp.esapi.ESAPI" %>
	<%@ page import="org.codehaus.jettison.json.JSONArray" %>
	<%@ page import="java.util.StringTokenizer" %>
	<%@ page import="beans.dbase.notification.*" %>
	<%@ page import="beans.dbase.chitchat.*" %>
	<%@ page import="beans.tools.filter.*" %>
	<%@ page import="beans.dbase.overview.*" %>
	<%@ page import="beans.dbase.overview.maintenance.*" %>
	
<%
//System.out.println("load start time = "+AskSystem.getCurrentTimestamp());
Vector v = null;
ListIterator li = null;
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
//System.out.println("validUsr cihuy="+validUsr);
AddHocFunction adf = new AddHocFunction();
String atBoxMenu= request.getParameter("atBoxMenu"); 
String status_proses = request.getParameter("status_proses"); 
String cmd_scope = null;
String thsms_now1 = (String) session.getAttribute("thsms_now1");
String thsms_1 = (String) session.getAttribute("thsms_1");
String thsms_buka_kelas = (String) session.getAttribute("thsms_buka_kelas");
String starting_thsms_pengecekan = (String) session.getAttribute("starting_thsms_pengecekan");
String thsms_pelaporan = (String) session.getAttribute("thsms_pelaporan");

String scope_cmd=null;
//System.out.println("top 0 = "+AskSystem.getCurrentTimestamp());

/*
UPDATED 25 DES 2017
PROSES INI DIPINDAH KE CRONE 
int updated = adf.populateTableTopikPengajuanKartuUjian(thsms_now1);
*/


//System.out.println("top 1 = "+AskSystem.getCurrentTimestamp());
Boolean desktop_mod = (Boolean)session.getAttribute("desktop_mod");	

//System.out.println("top 1 = "+AskSystem.getCurrentTimestamp());
//String info_dosen_arrival = (String)request.getAttribute("info_kehadiran_dosen");

//Vector vf = adf.syncProdiDgnNilaiTunda("20152");
//ListIterator lif = vf.listIterator();
//while(lif.hasNext()) {
//	String brs = (String)lif.next();
//System.out.println("1."+brs);
//}
boolean sdu = false; //= allapproved 
boolean smdu = false;
boolean show_sdu = false;
boolean sy_mhs = validUsr.iAmStu();
//System.out.println("sy_mhs1="+sy_mhs);
String npm_sy = validUsr.getNpm();
//System.out.println("npm_sy="+npm_sy);
if(sy_mhs) {
	sdu = Checker.sudahDaftarUlang(session);
	smdu = Checker.sudahMengajukanDaftarUlang(session);
	show_sdu = Checker.showNotificasiDaftarUlang(session);
}
String curr_stmhsmsmhs = Checker.getCurrStmhs(session);
String tknKrsNotifications = (String)session.getAttribute("tknKrsNotifications");
//String tknKrsNotifications = request.getParameter("tknKrsNotifications");
String tknKrsNotificationsForSender = (String)session.getAttribute("tknKrsNotificationsForSender");
//String tknKrsNotificationsForSender = request.getParameter("tknKrsNotificationsForSender");
Vector vBk = (Vector) session.getAttribute("vBukaKelas"); //creared at /ToUnivSatyagama/src/servlets/Overview/PrepInfoReqBukaKelas.java
JSONArray jsoaPymntReq = (JSONArray) session.getAttribute("jsoaPymntReq");
String newMsgOnMonitoredInbox= request.getParameter("newMsgOnMonitoredInbox");
//String newMsgOnOwnInbox = request.getParameter("newMsgOnOwnInbox");
String listKdpstBk = (String) request.getAttribute("listKdpstBukaKelas");
String kdjenKdpstNmpstNoPengajuan = (String) request.getAttribute("kdjenKdpstNmpstNoPengajuan");
String hasBukaKelasCmd = (String)request.getAttribute("hasBukaKelasCmd");
String ada_pengajuan = (String)session.getAttribute("ada_pengajuan");
Vector v_scope_id = new Vector();

int err_topik_trlsm = 0;
JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/trlsm/err_checker/usync_data_topik_vs_trlsm/usr_id/"+validUsr.getIdObj()+"/usr_npm/"+npm_sy+"/target_cmd/s");
if(jsoa!=null && jsoa.length()>0) {
	try {
		JSONObject job = jsoa.getJSONObject(0);
		err_topik_trlsm = (Integer)job.get("TOTAL");
	}
	catch(JSONException e) {}//ignore
}
//System.out.println("1");
//System.out.println("tknKrsNotifications="+tknKrsNotifications);
//System.out.println("tknKrsNotificationsForSender="+tknKrsNotificationsForSender);
/*
*==================remove session object ===================================
*/
//----kegiatan  param audit mutu internal----------------
session.removeAttribute("v_list_kelas");
session.removeAttribute("id_ami");;
session.removeAttribute("kode_activity");
session.removeAttribute("tgl_plan");
session.removeAttribute("ketua_tim");
session.removeAttribute("anggota_tim");
session.removeAttribute("id_cakupan_std");
session.removeAttribute("ket_cakupan_std");
session.removeAttribute("tgl_ril");
session.removeAttribute("tgl_ril_done");
session.removeAttribute("id_master_std");
session.removeAttribute("ket_master_std");
//----END kegiatan  param audit mutu internal----------------
session.removeAttribute("src_manual_limit");
session.removeAttribute("vScopeObjId");
session.removeAttribute("v_scope_kdpst_spmi");
session.removeAttribute("spmi_editor");
session.removeAttribute("team_spmi");

session.removeAttribute("v_std_blm_aktif");
session.removeAttribute("v_std_aktif");
session.removeAttribute("err");
session.removeAttribute("v_err_info");	
request.removeAttribute("v_kui_search_result");
request.removeAttribute("v_search_result");
request.removeAttribute("vHistBea");
session.removeAttribute("v_kui_search_result");
session.removeAttribute("v_search_result");
session.removeAttribute("vHistBea");
session.removeAttribute("tmp");
session.removeAttribute("target_tkn_kdpst");

session.removeAttribute("npmhs");
session.removeAttribute("kdpst");
session.removeAttribute("nmmhs");

session.removeAttribute("vHistKrsKhs");
session.removeAttribute("vCp");
session.removeAttribute("tknPaInfo");
session.removeAttribute("currentPa");
session.removeAttribute("infoKrsNotificationAtPmb");

session.removeAttribute("v_multi_body");
session.removeAttribute("v_multi_header");
session.removeAttribute("err_msg");
session.removeAttribute("jsoaDsn");
session.removeAttribute("jsoaPst");
session.removeAttribute("v_scope_kdpst_list_distinct_npm_out_univ_at_trlsm");
session.removeAttribute("v_scope_kdpst_list_distinct_npm_mhs_out_univ");
session.removeAttribute("v_scope_kdpst_list_distinct_npm_info_daftar_ulang");
session.removeAttribute("v_scope_kdpst_list_distinct_npm_info_daftar_ulang_wip");
session.removeAttribute("v_scope_kdpst_list_distinct_npm_trnlm_at_target_thsms");
session.removeAttribute("v_scope_kdpst_list_distinct_npm_cuti_ato_nonaktif_at_target_thsms");
session.removeAttribute("v_scope_kdpst_list_distinct_npm_mhs_baru");
session.removeAttribute("v_scope_kdpst_list_distinct_npm_info_pindah_prodi_out");
session.removeAttribute("v_scope_kdpst_list_distinct_npm_info_pindah_prodi_in");
session.removeAttribute("v_scope_kdpst_list_distinct_npm_info_pindah_prodi_wip");
session.removeAttribute("v_scope_kdpst_list_distinct_npm_info_kelulusan");
session.removeAttribute("v_scope_kdpst_list_distinct_npm_info_kelulusan_wip");
session.removeAttribute("v_scope_kdpst_list_distinct_npm_lulusan_at_trlsm");

session.removeAttribute("vTrnlp_session");
session.removeAttribute("vHistTrlsm_session");
session.removeAttribute("vHistKrsKhs_session");
session.removeAttribute("infoKrsNotificationAtPmb_session");
session.removeAttribute("currentPa_session");
session.removeAttribute("tknPaInfo_session");
session.removeAttribute("target_thsms");
session.removeAttribute("show_angel");
session.removeAttribute("v_list_unexecute_survey");
session.removeAttribute("v_list_survey_no_evaluasi_oleh_saya");
session.removeAttribute("v_list_upcoming_survey");
session.removeAttribute("spmi_editor");
session.removeAttribute("terkait");
session.removeAttribute("pengawas");
session.removeAttribute("v_kur_npm");
session.removeAttribute("v_target");
session.removeAttribute("v_err");
session.removeAttribute("target_thsms");
session.removeAttribute("v_dos");
session.removeAttribute("tkn_header");
session.removeAttribute("v_list_mhs");
session.removeAttribute("v_mgs_hist");
session.removeAttribute("v_tmp");
request.removeAttribute("v_tmp");
session.removeAttribute("v");
request.removeAttribute("vHistTrlsm");
session.removeAttribute("status_malaikat");
session.removeAttribute("vListKelasDgnNilaiTunda");
session.removeAttribute("v_info_civ");
request.removeAttribute("vHistKrsKhs");
session.removeAttribute("vHistKrsKhs");
request.removeAttribute("jsoa_role");
session.removeAttribute("jsoa");
request.removeAttribute("jsoa");
session.removeAttribute("vf");
session.removeAttribute("v_scope_id");
session.removeAttribute("v_overview");
session.removeAttribute("status_pengajuan_cuti_"+npm_sy);
session.removeAttribute("status_akhir_mahasiswa");
request.removeAttribute("vApprovedHeregistrasi");
request.removeAttribute("v1v2"); 
request.removeAttribute("vListNpmOnTrnlm");
session.removeAttribute("vListMhs");
session.removeAttribute("vThsmsStmhs");
session.removeAttribute("vScope_cmd");
session.removeAttribute("v_scope_kdpst_spmi");
session.removeAttribute("spmi_editor");
session.removeAttribute("vScopeObjId");
session.removeAttribute("goto_approval");
session.removeAttribute("job");
session.removeAttribute("vHisBea");
session.removeAttribute("vListNamaPaketBea");
session.removeAttribute("vMhsUnHeregContainment");
session.removeAttribute("kodeKampus");
session.removeAttribute("dataForRouterAfterUpload");
session.removeAttribute("hakAksesUsrUtkFolderIni");
session.removeAttribute("v_nm_alm_access");
session.removeAttribute("target_kelas_info");
session.removeAttribute("vMhsContainer");
session.removeAttribute("kelasTambahan");
session.removeAttribute("forceBackTo");
session.removeAttribute("listMonitorNickname");
session.removeAttribute("fieldAndValue");
session.removeAttribute("v1v2v3");
session.removeAttribute("vInfoListKandidat");
Vector vReqAprKeu = (Vector)session.getAttribute("vReqAprKeu");
session.removeAttribute("listTipeUjian");
session.removeAttribute("validatedTransDate");
session.removeAttribute("infoKelasDosen");
session.removeAttribute("infoKelasMhs");
session.removeAttribute("vJsoa");
session.removeAttribute("saveToFolder");
session.removeAttribute("targetAkun");
session.removeAttribute("sumberDana");
session.removeAttribute("vListKelasAjar");
session.removeAttribute("vListKategori");
session.removeAttribute("vAssigned");
session.removeAttribute("vListForMhs");
session.removeAttribute("vCp");
session.removeAttribute("vInfoKehadiranDosen");
session.removeAttribute("target_npm");
session.removeAttribute("status_update");
session.removeAttribute("ada_pengajuan");
session.removeAttribute("vPengajuan");
session.removeAttribute("vRiwayatPengajuan");
session.removeAttribute("vKdpstNpm");
session.removeAttribute("vListRule");
request.removeAttribute("v_list_prodi_no_class");
session.removeAttribute("nama_table");
session.removeAttribute("folder_pengajuan");
request.removeAttribute("v_noapr");
session.removeAttribute("target_kdpst");
session.removeAttribute("target_shift");
session.removeAttribute("target_thsms");
session.removeAttribute("vInfoKehadiranDosen");

String thsms_reg = Checker.getThsmsHeregistrasi();
String thsms_reg_1 = Tool.returnPrevThsmsGivenTpAntara(thsms_reg);
String thsms_pengajuan = Checker.getThsmsPengajuanStmhs();
String thsms_now = Checker.getThsmsNow();
//System.out.println("pit1 time = "+AskSystem.getCurrentTimestamp());
%>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="description" content="Metro, a sleek, intuitive, and powerful framework for faster and easier web development for Windows Metro Style.">
    <meta name="keywords" content="HTML, CSS, JS, JavaScript, framework, metro, front-end, frontend, web development">
    <meta name="author" content="Sergey Pimenov and Metro UI CSS contributors">
	
    <link rel='shortcut icon' type='image/x-icon' href='../favicon.ico' />
    <title>Tiles examples :: Start Screen :: Metro UI CSS - The front-end framework for developing projects on the web in Windows Metro Style</title>

    <link href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/css/metro.css" rel="stylesheet">
    <link href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/css/metro-icons.css" rel="stylesheet">
    <!--<link href="../css/metro-responsive.css" rel="stylesheet">-->

    <script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
    <script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/metro.js"></script>


    <style>
        .tile-area-controls {
            position: fixed;
            right: 40px;
            top: 40px;
        }

        .tile-group {
            left: 100px;
        }

        .tile, .tile-small, .tile-sqaure, .tile-wide, .tile-large, .tile-big, .tile-super {
            opacity: 0;
            -webkit-transform: scale(.8);
            transform: scale(.8);
        }

        #charmSettings .button {
            margin: 5px;
        }

        .schemeButtons {
            /*width: 300px;*/
        }

        @media screen and (max-width: 640px) {
            .tile-area {
                overflow-y: scroll;
            }
            .tile-area-controls {
                display: none;
            }
        }

        @media screen and (max-width: 320px) {
            .tile-area {
                overflow-y: scroll;
            }

            .tile-area-controlstkn_kmp {
                display: none;
            }

        }
    </style>

    <script>

        /*
         * Do not use this is a google analytics fro Metro UI CSS
         * */
        if (window.location.hostname !== 'localhost') {

            (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
            })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

            ga('create', 'UA-58849249-3', 'auto');
            ga('send', 'pageview');

        }

    </script>

    <script>
        (function($) {
            $.StartScreen = function(){
                var plugin = this;
                var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;

                plugin.init = function(){
                    setTilesAreaSize();
                    if (width > 640) addMouseWheel();
                };

                var setTilesAreaSize = function(){
                    var groups = $(".tile-group");
                    var tileAreaWidth = 80;
                    $.each(groups, function(i, t){
                        if (width <= 640) {
                            tileAreaWidth = width;
                        } else {
                            tileAreaWidth += $(t).outerWidth() + 80;
                        }
                    });
                    $(".tile-area").css({
                        width: tileAreaWidth
                    });
                };

                var addMouseWheel = function (){
                    $("body").mousewheel(function(event, delta, deltaX, deltaY){
                        var page = $(document);
                        var scroll_value = delta * 50;
                        page.scrollLeft(page.scrollLeft() - scroll_value);
                        return false;
                    });
                };

                plugin.init();
            }
        })(jQuery);

        $(function(){
            $.StartScreen();

            var tiles = $(".tile, .tile-small, .tile-sqaure, .tile-wide, .tile-large, .tile-big, .tile-super");

            $.each(tiles, function(){
                var tile = $(this);
                setTimeout(function(){
                    tile.css({
                        opacity: 1,
                        "-webkit-transform": "scale(1)",
                        "transform": "scale(1)",
                        "-webkit-transition": ".3s",
                        "transition": ".3s"
                    });
                }, Math.floor(Math.random()*500));
            });

            $(".tile-group").animate({
                left: 0
            });
        });

        function showCharms(id){
            var  charm = $(id).data("charm");
            if (charm.element.data("opened") === true) {
                charm.close();
            } else {
                charm.open();
            }
        }

        function setSearchPlace(el){
            var a = $(el);
            var text = a.text();
            var toggle = a.parents('label').children('.dropdown-toggle');

            toggle.text(text);
        }

        $(function(){
            var current_tile_area_scheme = localStorage.getItem('tile-area-scheme') || "tile-area-scheme-dark";
            $(".tile-area").removeClass (function (index, css) {
                return (css.match (/(^|\s)tile-area-scheme-\S+/g) || []).join(' ');
            }).addClass(current_tile_area_scheme);

            $(".schemeButtons .button").hover(
                    function(){
                        var b = $(this);
                        var scheme = "tile-area-scheme-" +  b.data('scheme');
                        $(".tile-area").removeClass (function (index, css) {
                            return (css.match (/(^|\s)tile-area-scheme-\S+/g) || []).join(' ');
                        }).addClass(scheme);
                    },
                    function(){
                        $(".tile-area").removeClass (function (index, css) {
                            return (css.match (/(^|\s)tile-area-scheme-\S+/g) || []).join(' ');
                        }).addClass(current_tile_area_scheme);
                    }
            );

            $(".schemeButtons .button").on("click", function(){
                var b = $(this);
                var scheme = "tile-area-scheme-" +  b.data('scheme');

                $(".tile-area").removeClass (function (index, css) {
                    return (css.match (/(^|\s)tile-area-scheme-\S+/g) || []).join(' ');
                }).addClass(scheme);

                current_tile_area_scheme = scheme;
                localStorage.setItem('tile-area-scheme', scheme);

                showSettings();
            });
        });

        var weather_icons = {
            'clear-day': 'mif-sun',
            'clear-night': 'mif-moon2',
            'rain': 'mif-rainy',
            'snow': 'mif-snowy3',
            'sleet': 'mif-weather4',
            'wind': 'mif-wind',
            'fog': 'mif-cloudy2',
            'cloudy': 'mif-cloudy',
            'partly-cloudy-day': 'mif-cloudy3',
            'partly-cloudy-night': 'mif-cloud5'
        };

        var api_key = 'AIzaSyDPfgE0qhVmCcy-FNRLBjO27NbVrFM2abg';

        $(function(){
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function(position){
                    var lat = position.coords.latitude, lon = position.coords.longitude;
                    var pos = lat+','+lon;
                    var latlng = new google.maps.LatLng(lat, lon);
                    var geocoder = new google.maps.Geocoder();
                    $.ajax({
                        url: '//api.forecast.io/forecast/219588ba41dedc2f1019684e8ac393ad/'+pos+'?units=si',
                        dataType: 'jsonp',
                        success: function(data){
                            //do whatever you want with the data here
                            geocoder.geocode({latLng: latlng}, function(result, status){
                                console.log(result[3]);
                                $("#city_name").html(result[3].formatted_address);
                            });
                            var current = data.currently;
                            //$('#city_name').html(response.city+", "+response.country);
                            $("#city_temp").html(Math.round(current.temperature)+" &deg;C");
                            $("#city_weather").html(current.summary);
                            $("#city_weather_daily").html(data.daily.summary);
                            $("#weather_icon").addClass(weather_icons[current.icon]);
                            $("#pressure").html(current.pressure);
                            $("#ozone").html(current.ozone);
                            $("#wind_bearing").html(current.windBearing);
                            $("#wind_speed").html(current.windSpeed);
                            $("#weather_bg").css({
                                'background-image': 'url(../images/'+current.icon+'.jpg'+')'
                            });
                        }
                    });
                });
            }
        });
    </script>

	<script>		
		$(document).ready(function() {
			$("#wait").hide();
			$("#main").show();
		});	
	</script>
	<script>
	$(document).ready(function() {
		$("#cekMhsLewatBtstu").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.cekMhsLewatBtstu',
        		type: 'POST',
        		data: $("#cekMhsLewatBtstu").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/home.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
		
	   	$(document).on("click", "#getSebaranTrlsm", function() {
        	$.ajax({
        		url: 'go.getSebaranTrlsm',
        		type: 'POST',
        		data: {
        			//jangan lupa update juga
					// at window.location.href   
					show_angel: "true",
					target_thsms: "<%=thsms_reg%>",
        	    },
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	        <%
        	        //String target = Constants.getRootWeb()+"/InnerFrame/Overview/Trlsm/indexSebaranTrlsm_v1.jsp"; 
        	        //String target = Constants.getRootWeb()+"/InnerFrame/Monitoring/toListMhsProfilIncomplete.jsp";
        			//String uri = request.getRequestURI();
        			//String url = PathFinder.getPath(uri, target);
        	        %>
        	        window.location.href = "InnerFrame/Overview/Trlsm/indexSebaranTrlsm_v1.jsp";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });	
	
	
	   	$(document).on("click", "#getListMhsProfilIncomplete", function() {
        	$.ajax({
        		url: 'go.getListMhsProfilIncomplete',
        		type: 'POST',
        		data: {
        			//jangan lupa update juga
					// at window.location.href        		
        			starting_smawl: "<%=thsms_now1%>",
        			limit_per_page: "10",
        			at_hal: "0",
        			starting_page_shown: "1",
        			ending_page_shown: "0",
    				search_range: "90",
    				prev: "false",
        			next: "false",
    				atMenu: "index",
    				from: "home",
    				cmd: "vop",
    				nav: "getListMhsProfilIncomplete",
        	    },
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	        <%
        	        //String target = Constants.getRootWeb()+"/InnerFrame/Monitoring/toListMhsProfilIncomplete.jsp";
        			//String uri = request.getRequestURI();
        			//String url = PathFinder.getPath(uri, target);
        	        %>
        	        window.location.href = "InnerFrame/Monitoring/toListMhsProfilIncomplete.jsp";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });	
	   	
	   	$(document).on("click", "#getMhsPindahanNoPenyetaraan", function() {
        	$.ajax({
        		url: 'go.mhsPindahanNoPenyetaraan',
        		type: 'POST',
        		data: {
        			//jangan lupa update juga
					// at window.location.href        		
        			starting_smawl: "20161",
        			limit_per_page: "10",
        			at_hal: "0",
        			starting_page_shown: "1",
        			ending_page_shown: "0",
    				search_range: "90",
    				prev: "false",
        			next: "false",
    				atMenu: "index",
    				from: "home",
    				cmd: "vop",
    				nav: "getListMhsProfilIncomplete",
        	    },
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	        <%
        	        //String target = Constants.getRootWeb()+"/InnerFrame/Monitoring/toListMhsProfilIncomplete.jsp";
        			//String uri = request.getRequestURI();
        			//String url = PathFinder.getPath(uri, target);
        	        %>
        	        window.location.href = "InnerFrame/Monitoring/toListMhsPindahanBelumAdaPenyetaraan.jsp";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });	
	   	
	   	$(document).on("click", "#getDataErrPengajuanVsTrlsm", function() {
	    	$.ajax({
	    		url: 'go.getDataErrPengajuanVsTrlsm',
	    		type: 'POST',
	    		data: {
	    	        tot_err_data: "<%=err_topik_trlsm%>",
	    	    },
	    	    beforeSend:function(){
	    	    	$("#wait").show();
	    	    	$("#main").hide();
	    	    	parent.scrollTo(0,0);
	    	        // this is where we append a loading image
	    	    },
	    	    success:function(data){
	    	        // successful request; do something with the data 
	    	    	//window.location.href = "index_stp.jsp";
	    	    	window.location.href = "InnerFrame/sql/ResultSet_Multi_Vector_Header_Body.jsp";
	    	    },
	    	    error:function(){
	    	        // failed request; give feedback to user
	    	    }
	    	})
	    	return false;
	    });
	   	
	   	$(document).on("click", "#getDbErrorInfo", function() {
	    	$.ajax({
	    		url: 'go.getDbErrorInfo?jabatan=ADMIN',
	    		type: 'POST',
	    		data: {
	    	        
	    	    },
	    	    beforeSend:function(){
	    	    	$("#wait").show();
	    	    	$("#main").hide();
	    	    	parent.scrollTo(0,0);
	    	        // this is where we append a loading image
	    	    },
	    	    success:function(data){
	    	        // successful request; do something with the data 
	    	    	//window.location.href = "index_stp.jsp";
	    	        //ToUnivSatyagama/WebContent/InnerFrame/Monitoring/TableErrorInfo/indexErrorInfo.jsp
	    	    	window.location.href = "InnerFrame/Monitoring/TableErrorInfo/indexErrorInfo.jsp";
	    	    },
	    	    error:function(){
	    	        // failed request; give feedback to user
	    	    }
	    	})
	    	return false;
	    });
	   	
	   	$(document).on("click", "#prepPengajuanPymnt", function() {
	    	$.ajax({
	    		url: 'prep.pengajuanPymnt',
	    		type: 'POST',
	    		data: {
	    	    },
	    	    beforeSend:function(){
	    	    	$("#wait").show();
	    	    	$("#main").hide();
	    	    	parent.scrollTo(0,0);
	    	        // this is where we append a loading image
	    	    },
	    	    success:function(data){
	    	        // successful request; do something with the data 
	    	    	//window.location.href = "index_stp.jsp";
	    	    	window.location.href = "InnerFrame/Keu/requestKeuAprovalForm2.jsp?";
	    	    },
	    	    error:function(){
	    	        // failed request; give feedback to user
	    	    }
	    	})
	    	return false;
	    });
	   	
	   	$(document).on("click", "#getMhsPindahanNoPenyetaraan_part2", function() {
        	$.ajax({
        		url: 'go.mhsPindahanNoPenyetaraan_part2',
        		type: 'POST',
        		data: {
        			//jangan lupa update juga
					// at window.location.href        		
        			starting_smawl: "<%=starting_thsms_pengecekan%>",
        			limit_per_page: "10",
        			at_hal: "0",
        			starting_page_shown: "1",
        			ending_page_shown: "0",
    				search_range: "90",
    				prev: "false",
        			next: "false",
    				atMenu: "index",
    				from: "home",
    				cmd: "vop",
    				nav: "getListMhsProfilIncomplete",
        	    },
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	        <%
        	        //String target = Constants.getRootWeb()+"/InnerFrame/Monitoring/toListMhsProfilIncomplete.jsp";
        			//String uri = request.getRequestURI();
        			//String url = PathFinder.getPath(uri, target);
        	        %>
        	        window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=get.notifications&at_page=1&max_data_per_pg=20";
        	        //window.location.href = "InnerFrame/Monitoring/toListMhsPindahanBelumAdaPenyetaraan.jsp";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });  
	   	
	   	$("#getInfoLulusan").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.getDataLulusan',
        		type: 'POST',
        		data: $("#getInfoLulusan").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v5.jsp?backto=&at_page=1&max_data_per_pg=20&norut_col_npm=11";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	   	$("#kehadiranDosen").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
	   			//<a href="getClasPol.statusKehadiranKelas?atMenu=kehadiran&from=home
        		url: 'go.statusKehadiranKelas_v2',
        		type: 'POST',
        		data: $("#kehadiranDosen").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	        //InnerFrame/Perkuliahan/Absensi/DosenAjar/dashAbsensiDsnAjar.jsp";
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Perkuliahan/Absensi/DosenAjar/dashAbsensiDsnAjar.jsp?backto=home&cmd=sad";
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
<body style="overflow-y: hidden;">

<%

    response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");//HTTP 1.1
    response.setHeader("Pragma","no-cache"); //HTTP 1.0
    response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai<br>[Lama proses bisa sampai 5 menit]</p>
</div>
<div id="main">

<%
try {
	
	String cmd = null;
%>

    <div class="tile-area tile-area-scheme-dark fg-white" style="height: 100%; max-height: 100% !important;">
      
    	<!--  div class="tile-group four">
			<span class="tile-group-title">Notifikasi
			</span>
			<div class="tile-container">
				<a href="prep.infoListProdiNoClass?scope_cmd=<%=scope_cmd %>" target="inner_iframe" class="tile bg-orange fg-white" data-role="tile">
      				<div class="tile-content">
      		        	<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/kelas1.jpg" data-role="fitImage" data-format="square">
      		       	</div>
      		        <div style="margin:20px 0 0 40px;position: absolute;">   
      		        	<p class="no-margin text-shadow">PENGAJUAN<br/><span class="text-bold">KELAS</span><br/>PERKULIAHAN <%=thsms_buka_kelas %></p>
      		        </div>
      		 	</a>
     		</div>
  		</div-->		
    </div>
</div>    
	<%
	
}			
catch(Exception e) {
%>
<center>
<meta http-equiv="refresh" content="300; url=http:<%=Constants.getRootWeb() %>/InnerFrame/home.jsp">
</center>
<%	
}
System.out.println("load start end = "+AskSystem.getCurrentTimestamp());
	%>
<br>
			
</body>
</html>
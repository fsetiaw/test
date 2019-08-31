<!DOCTYPE html>
<html>
<head lang="en">
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>
	<%@page import="beans.dbase.spmi.SearchStandarMutu"%>
	<%@ page import="org.codehaus.jettison.json.JSONArray" %>
	<%@ page import="org.codehaus.jettison.json.JSONObject" %>
	<%@ page import="org.codehaus.jettison.json.JSONException" %>
	<%@ page import="org.owasp.esapi.ESAPI" %>
	<%@ page import="org.codehaus.jettison.json.JSONArray" %>
	<%@ page import="java.util.StringTokenizer" %>
	<%@ page import="beans.dbase.notification.*" %>
	<%@ page import="beans.dbase.chitchat.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="beans.dbase.overview.*" %>
<%	
session.removeAttribute("current_kdpst_nmpst_kmp");

beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
while(kdpst_nmpst_kmp.contains("~")) { //tambahan digunakan only cg2mutu
	kdpst_nmpst_kmp = kdpst_nmpst_kmp.replace("~", "`");
}
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
//System.out.println("kdpst_nmpst_kmp="+kdpst_nmpst_kmp);
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();//ngga kepake bis diignore
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);
session.removeAttribute("v_prodi_never_ami");
session.removeAttribute("v_status_doc_mutu");
session.removeAttribute("kdpst_asal");
session.removeAttribute("id_tipe_std_asal");
session.removeAttribute("id_master_std_asal");
session.removeAttribute("id_std_asal");
session.removeAttribute("id_versi_asal");
session.removeAttribute("folder_asal");
session.removeAttribute("folder_asal1");
session.removeAttribute("tkn_header");

request.removeAttribute("v");
session.removeAttribute("v");
session.removeAttribute("v_list_survey_no_evaluasi_oleh_saya");
int at_page=Constant.getAt_page();
int max_data_per_pg=Constant.getMax_data_per_pg();
int max_data_per_man_pg=Constant.getAt_page();
Vector v_scope_kdpst_spmi = (Vector)session.getAttribute("v_scope_kdpst_spmi");//KDFAKMSPST,KDPSTMSPST,KDJENMSPST,NMPSTMSPST

//System.out.println("v_scope_kdpst_spmi="+v_scope_kdpst_spmi.size());
Boolean spmi_editor = (Boolean) session.getAttribute("spmi_editor");
SearchStandarMutu ssm = new SearchStandarMutu();
int upd = ssm.pastikanStandarTableSudahAdaVersiPertamaPadaStandarTipeVersion();
upd = ssm.pastikanStandarTableSudahAdaVersiTerbaruPadaStandarTipeVersion();
session.removeAttribute("src_manual_limit");
session.removeAttribute("v_QA");
session.removeAttribute("v_pelanggaran_isi_std");
session.removeAttribute("v_kegiatan_ppepp_yg_lewat");
session.removeAttribute("v_kegiatan_ami_yg_lewat");
session.removeAttribute("v_kegiatan_monitoring_no_pengendalian");
session.removeAttribute("v_kegiatan_monitoring_no_kelanjutan");
session.removeAttribute("v_isi_std_never_monitored");
session.removeAttribute("id_ami");
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
session.removeAttribute("v_status_aktif_std");
session.removeAttribute("v_status_aktif_man");
session.removeAttribute("kdpst_folder");

session.setAttribute("kdpst_folder", kdpst);

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

            .tile-area-controls {
                display: none;
            }

        }
    </style>
	<script>
		
		$(document).ready(function() {
			$("#wait").hide();
			$("#main").show();
		});	
	</script>
	<script>	
	
	$(document).ready(function() {
		
		$("#cekMhsStatusUnknown").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.cekMhsStatusUnknown',
        		type: 'POST',
        		data: $("#cekMhsStatusUnknown").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	        //this is where we append a loading image
        	    },
        	    success:function(data){
        	        //successful request; do something with the data 
        	        
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v1.jsp";
        	    },
        	    error:function(){
        	        //failed request; give feedback to user
        	    }
        	})
        	return false;
        });
		
		//a href
		$(document).on("click", "#getListAllStd", function() {
        	$.ajax({
        		url: 'go.getListAllStd',
        		type: 'POST',
        		data: {
        			kdpst_nmpst_kmp: "<%=kdpst_nmpst_kmp%>", //ignore karena sebenarnya dari wondow.loca.href
        			at_page: "<%=at_page%>",
        			max_data_per_pg: "<%=max_data_per_pg%>",
        	    },
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	        //this is where we append a loading image
        	    },
        	    success:function(data){
        	        //successful request; do something with the data 
        	        <%
        	        kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
        	        %>
        	        window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/home_standar_unknown.jsp?id_master_std=0&id=0&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=<%=at_page%>&max_data_per_pg=<%=max_data_per_pg%>";
        	    },
        	    error:function(){
        	        //failed request; give feedback to user
        	    }
        	})
        	return false;
        });	
		
		$(document).on("click", "#getListAllMan", function() {
        	$.ajax({
        		url: 'go.getListAllStd',
        		type: 'POST',
        		data: {
        			kdpst_nmpst_kmp: "<%=kdpst_nmpst_kmp%>", //ignore karena sebenarnya dari wondow.loca.href
        			at_page: "<%=at_page%>",
        			max_data_per_pg: "<%=max_data_per_man_pg%>",
        	    },
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	        //this is where we append a loading image
        	    },
        	    success:function(data){
        	        //successful request; do something with the data 
        	        <%
        	        kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
        	        %>
        	        window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/home_manual_standar.jsp?id_master_std=0&id=0&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=<%=at_page%>&max_data_per_pg=<%=max_data_per_man_pg%>";
        	    },
        	    error:function(){
        	        //failed request; give feedback to user
        	    }
        	})
        	return false;
        });	
		
		$(document).on("click", "#prepDashOverviewSpmi", function() {
        	$.ajax({
        		url: 'go.prepDashOverviewSpmi',
        		type: 'POST',
        		data: {
        			kdpst_nmpst_kmp: '<%=kdpst_nmpst_kmp%>' //ignore karena sebenarnya dari wondow.loca.href
        	    },
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	        //this is where we append a loading image
        	    },
        	    success:function(data){
        	        //successful request; do something with the data 
        	        <%
        	        kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
        	        %>

        	        window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Spmi/spmi_overview/dash_overview.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        //failed request; give feedback to user
        	    }
        	})
        	return false;
        });	
		
		$(document).on("click", "#prepDashCalendar", function() {
        	$.ajax({
        		url: 'go.refreshCalenderMutu',
        		type: 'POST',
        		data: {
        			from: 'home',
        			kdpst_nmpst_kmp: '<%=kdpst_nmpst_kmp%>' //ignore karena sebenarnya dari wondow.loca.href
        	    },
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	        //this is where we append a loading image
        	    },
        	    success:function(data){
        	        //successful request; do something with the data 
        	        <%
        	        kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
        	        %>

        	        window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Spmi/calendar/calendar_mutu.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        //failed request; give feedback to user
        	    }
        	})
        	return false;
        });	
		
		$(document).on("click", "#prepDashOverviewAmi", function() {
        	$.ajax({
        		url: 'go.prepDashOverviewAmi',
        		type: 'POST',
        		data: {
        			kdpst_nmpst_kmp: '<%=kdpst_nmpst_kmp%>' //ignore karena sebenarnya dari wondow.loca.href
        	    },
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	        //this is where we append a loading image
        	    },
        	    success:function(data){
        	        //successful request; do something with the data 
        	        <%
        	        kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
        	        %>
        	        window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Spmi/Ami/riwayat_ami_v1.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        //failed request; give feedback to user
        	    }
        	})
        	return false;
        });	
		
		
		
	});	
    </script>


    
    
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
        $( document ).ajaxStart(function() {
  		  $( ".log" ).text( "Triggered ajaxStart handler." );
  		});
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
            }$( document ).ajaxStart(function() {
      		  $( ".log" ).text( "Triggered ajaxStart handler." );
    		});
        });
    </script>
<script type="text/javascript">
      jQuery(function() {
              jQuery('#kdpst_nmpst_kmp').change(function() {
                 this.form.submit();
              });
     });
</script>
</head>
<body style="overflow-y: hidden;">
<%
/*
%>
	<table style="width:98%;border:none">
		<tr>
			<td style="width:51%;text-align:center;">
			</td>
			<td style="width:49%;text-align:left;">
				<a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/home_spmi.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe" >
            		<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/home_black.png" width="45px" height="45px" style=";padding:0 0 0 0">
              		<p class="no-margin text-shadow" style="font-weight:bold;font-size:1.em;color:black;padding:0 0 0 1px">HOME</p>
				</a>
			</td>
		</tr>
	</table>
<%
*/
%>	
   	<div style="padding:10px 0 0 80px">
    	<form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/home_spmi.jsp?atMenu=spmi">
			<select onchange="this.form.submit()" name="kdpst_nmpst_kmp" id="kdpst_nmpst_kmp" style="width:100%;height:25px;border:none;text-align-last:center;border-radius: 10px;">
<%
ListIterator li = v_scope_kdpst_spmi.listIterator();

while(li.hasNext()) {
	String brs = (String)li.next();
	//System.out.println("brs scope="+brs);
	st = new StringTokenizer(brs,"`");
	String current_fak = st.nextToken();
	String current_kdpst = st.nextToken();
	String current_kdjen = st.nextToken();
	String current_nmpst = st.nextToken();
	String info_prodi = Converter.getDetailKdpst_v1(current_kdpst);
	if(current_kdpst.equalsIgnoreCase(kdpst)) {
		%>
				<option value="<%=current_kdpst %>`<%=current_nmpst %>`null" selected="selected"><%=info_prodi%></option> 
	<%	
	}
	else {
		%>
				<option value="<%=current_kdpst %>`<%=current_nmpst %>`null"><%=info_prodi%></option> 
	<%
	}

}
%>				
				
			</select>
		</form>		
	</div>	

<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai</p>
</div>
<div id="main">

	<div class="tile-area tile-area-scheme-dark fg-white" style="height: 100%; max-height: 100% !important;">
    <%
    %>  
        <div class="tile-group double">
        	
			<span class="tile-group-title" >DOKUMEN SPMI</span>
<%
if(new String("KEBIJAKAN SPMI "+keter_prodi).length()>30) {
	out.print("<br>");
}
%>

			<div class="tile-container">
				<div class="tile-wide bg-orange fg-white" data-role="tile">
            		<div class="tile-content image-set">
            			<a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Kebijakan/kebijakanSpmi.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/kebijakan.jpg" data-role="fitImage" data-format="fill"></a>
                	</div>
                	<div style="margin:10px 0 0 10px;position: absolute;">   
                   		<p class="no-margin text-shadow" style="font-weight:bold;font-size:1.5em;color:#white"><span class="text-bold">KEBIJAKAN SPMI</span></p>
                	</div>
          		</div>	
     		</div>
     		<div class="tile-container">
				<div class="tile-wide bg-orange fg-white" data-role="tile">
            		<div class="tile-content image-set">
            			<!--  a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_spmi/home_standar.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/book_of_std.jpg" data-role="fitImage" data-format="fill"></a -->
            			<a href="#" id="getListAllStd"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/book_of_std.jpg" data-role="fitImage" data-format="fill"></a>
                	</div>
                	<div style="margin:10px 0 0 10px;position: absolute;">   
                  		<p class="no-margin text-shadow" style="font-weight:bold;font-size:1.5em;color:white"><span class="text-bold">STANDAR & MANUAL</span></p>
                	</div>
                	<div style="margin:30px 0 0 10px;position: absolute;">   
                  		<p class="no-margin text-shadow" style="font-weight:bold;font-size:1.5em;color:white"><span class="text-bold">SPMI</span></p>
                	</div>
          		</div>
          	<div class="tile-container">
				<div class="tile-wide bg-orange fg-white" data-role="tile">
            		<div class="tile-content image-set">
            			<a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/Dokument/mutu/list_doc_mutu.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&atMenu=list"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/doc_std.jpg" data-role="fitImage" data-format="fill"></a>
                		<!--  a href="<Constants.getRootWeb() %>/InnerFrame/Keu/requestKeuAprovalForm.jsp" target="inner_iframe"><img src="<Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/payment.jpg" data-role="fitImage" data-format="fill"></a -->
                	</div>
                	<div style="margin:10px 0 0 10px;position: absolute;">   
                   		<p class="no-margin text-shadow" style="font-weight:bold;font-size:1.5em;color:#74888b"><span class="text-bold">DOKUMEN & FORMULIR SPMI</span></p>
                	</div>
          		</div>
			</div>
			<div class="tile-container">
				<div class="tile-wide bg-orange fg-white" data-role="tile">
            		<div class="tile-content image-set">
            			<a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/Tutorial/index_tutorial.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&atMenu=list"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/tutorial.png" data-role="fitImage" data-format="fill"></a>
                		<!--  a href="<Constants.getRootWeb() %>/InnerFrame/Keu/requestKeuAprovalForm.jsp" target="inner_iframe"><img src="<Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/payment.jpg" data-role="fitImage" data-format="fill"></a -->
                	</div>
                	<div style="margin:10px 0 0 10px;position: absolute;">   
                   		<p class="no-margin text-shadow" style="font-weight:bold;font-size:1.5em;color:#74888b"><span class="text-bold"></span></p>
                	</div>
          		</div>
			</div>
          		
				
			</div>
  		</div>     
    	
    	<div class="tile-group one">
			<span class="tile-group-title">PENGAJUAN STD</span>
<%
if(new String("STANDAR SPMI "+keter_prodi).length()>30) {
	out.print("<br>");
}
%>	
			
			<div class="tile-container">
				
          		<%
				if(validUsr.amI("KEPALA PENJAMINAN MUTU")||validUsr.isHakAksesFull("hasSpmiMenu")) {
				%>
				<a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/form/std_isi/edit_index_v2.jsp?atMenu=edit_isi&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&mode=list" target="inner_iframe" class="tile bg-darkTeal fg-white" data-role="tile">
            		<div class="padding20">
	               	<p style="font-size:1em;font-weight:bold">UBAH USULAN <br>MENJADI RANCANGAN<br>ISI STANDAR BARU</p>
					</div>
				</a> 
				<%
				}
				%>
          		<a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/indexCreator.jsp?atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">
            		<div class="padding20">
	               	<p class="no-margin text-shadow"><span class="text-bold">LIST PENGAJUAN USULAN STANDARISASI</p>
					</div>
				</a>
				<a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_creator/form/std_isi/input_form_single.jsp?atMenu=form_isi&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe" class="tile bg-orange fg-white" data-role="tile">
            		<div class="padding20">
	               	<p class="no-margin text-shadow"><span class="text-bold">FORM PENGAJUAN USULAN STANDARISASI</p>
					</div>
				</a>
				
			</div>
		</div>	
  	
    	<div class="tile-group double">
			<span class="tile-group-title">KEGIATAN MUTU</span>
		
			<div class="tile-container">
				<div class="tile-wide bg-orange fg-white" data-role="tile">
            		<div class="tile-content image-set">
            			<a href="#" ID="prepDashCalendar"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/calendar.jpg" data-role="fitImage" data-format="fill"></a>
                	</div>
                	<div style="margin:5px 0 0 10px;position: absolute;">   
		           		<p class="no-margin text-shadow" style="color:#393939">KALENDER<br/><span class="text-bold">KEGIATAN<br>MUTU</span><br/></p>
		           	</div>
          		</div>
				<div class="tile-wide bg-orange fg-white" data-role="tile">
            		<div class="tile-content image-set">
            			<!--  a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_spmi/home_standar.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/book_of_std.jpg" data-role="fitImage" data-format="fill"></a -->
            			<a href="#" id="prepDashOverviewAmi"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/internal_audit.jpeg" data-role="fitImage" data-format="fill"></a>
                	</div>
          		</div>
				
          		
          		<div class="tile-wide bg-orange fg-white" data-role="tile">
            		<div class="tile-content image-set">
            			<!--  a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_spmi/home_standar.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/book_of_std.jpg" data-role="fitImage" data-format="fill"></a -->
            			<a href="#" onclick="(function(){
var x = document.getElementById('wait');
var y = document.getElementById('main');
x.style.display = 'block';
y.style.display = 'none';
location.href='go.prepDashOverviewSpmi?fwdto=overview&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>'})()"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/total-quality-quality-assurance.jpg" data-role="fitImage" data-format="fill"></a>
                	</div>
                	<div style="margin:95px 0 0 10px;position: absolute;">   
		           		<p class="no-margin text-shadow" style="color:#393939">OVERVIEW<br/><span class="text-bold">PENJAMINAN MUTU</span><br/></p>
		           	</div>
          		</div>
          		
			</div>
		</div>	
<%
/*
%>  		
		<div class="tile-group double">
			<span class="tile-group-title">DOKUMEN SPMI <%=keter_prodi%></span>
<%
if(new String("DOKUMEN SPMI "+keter_prodi).length()>30) {
	out.print("<br>");
}
%>	
<div class="tile-wide bg-orange fg-white" data-role="tile">
            		<div class="tile-content image-set">
            			<!--  a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/standar_spmi/home_standar.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/book_of_std.jpg" data-role="fitImage" data-format="fill"></a -->
            			<a href="#" id="prepDashOverviewSpmi"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/total-quality-quality-assurance.jpg" data-role="fitImage" data-format="fill"></a>
                	</div>
                	<div style="margin:95px 0 0 10px;position: absolute;">   
		           		<p class="no-margin text-shadow" style="color:#393939">OVERVIEW<br/><span class="text-bold">PENJAMINAN MUTU</span><br/></p>
		           	</div>
          		</div>			
			<div class="tile-container">
				<div class="tile-wide bg-orange fg-white" data-role="tile">
            		<div class="tile-content image-set">
            			<a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/Dokument/mutu/list_doc_mutu.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&atMenu=list"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/doc_std.jpg" data-role="fitImage" data-format="fill"></a>
                		<!--  a href="<Constants.getRootWeb() %>/InnerFrame/Keu/requestKeuAprovalForm.jsp" target="inner_iframe"><img src="<Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/payment.jpg" data-role="fitImage" data-format="fill"></a -->
                	</div>
                	<div style="margin:10px 0 0 10px;position: absolute;">   
                   		<p class="no-margin text-shadow" style="font-weight:bold;font-size:1.5em;color:#74888b"><span class="text-bold">DOKUMEN SPMI</span></p>
                	</div>
          		</div>
          		<div class="tile-wide bg-orange fg-white" data-role="tile">
            		<div class="tile-content image-set">
            			<a href="#" id="getListAllMan"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/manual_std.jpg" data-role="fitImage" data-format="fill"></a>
                	</div>
                	<div style="margin:120px 0 0 10px;position: absolute;">   
                  		<p class="no-margin text-shadow" style="font-weight:bold;font-size:1.5em;color:white"><span class="text-bold">BUKU MANUAL SPMI</span></p>
                	</div>
          		</div>
			</div>
		</div>
<%
*/
%>			
	</div>
</div>
</body>
</html>
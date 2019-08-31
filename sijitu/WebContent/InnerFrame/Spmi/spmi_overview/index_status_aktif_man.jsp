<!DOCTYPE html>
<html>
<head lang="en">
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
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
	<%@ page import="beans.tools.*" %>
	<%@ page import="beans.dbase.overview.*" %>
	<%@ page import="beans.dbase.spmi.*" %>
<%	
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
//Converter.getDetailKdpst_v1(kdpst)
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);
session.removeAttribute("tkn_header");
request.removeAttribute("v");
session.removeAttribute("v");
int at_page=Constant.getAt_page();
int max_data_per_pg=Constant.getMax_data_per_pg();
int max_data_per_man_pg=Constant.getAt_page();
Vector v_scope_kdpst_spmi = (Vector)session.getAttribute("v_scope_kdpst_spmi");//KDFAKMSPST,KDPSTMSPST,KDJENMSPST,NMPSTMSPST
//System.out.println("disini kdpst_nmpst_kmp=="+kdpst_nmpst_kmp);
Boolean spmi_editor = (Boolean) session.getAttribute("spmi_editor");
//Vector v_list_survey_no_evaluasi_oleh_saya = (Vector)session.getAttribute("v_list_survey_no_evaluasi_oleh_saya");
//Vector v_list_upcoming_survey = (Vector)session.getAttribute("v_list_upcoming_survey");
//Vector v_list_unexecute_survey = (Vector)session.getAttribute("v_list_unexecute_survey");
Vector v_status_aktif_man=(Vector)session.getAttribute("v_status_aktif_man");


//SearchStandarMutu stm = new SearchStandarMutu(validUsr.getNpm());
//Vector v_list_main_std = stm.getListInfoStandar();
//session.removeAttribute("v_list_survey_no_evaluasi_oleh_saya");
//session.removeAttribute("v_list_upcoming_survey");
//session.removeAttribute("v_list_unexecute_survey");
//System.out.println("v_list_survey_no_evaluasi sie = "+v_list_survey_no_evaluasi_oleh_saya.size());
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

<style>
a.img:hover {
	text-decoration: none;
	background:none;
}

.table {
border: 1px solid #fff;
background:<%=Constant.lightColorBlu()%>;
}
.table thead > tr > th {
border-bottom: 1px solid #fff;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #fff; }

.table-noborder { border: 1px solid #fff;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: 1px solid #fff;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: 1px solid #fff;padding: 2px }
.table:hover td { background:#82B0C3 }
</style>
<style>
.table1 {
border: 1px solid #fff;
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
<style>
.table2 {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
}
.table2 thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table2 thead > tr > th, .table2 tbody > tr > t-->h, .table2 tfoot > tr > th, .table2 thead > tr > td, .table2 tbody > tr > td, .table2 tfoot > tr > td { border: 1px solid #2980B9; }

.table2-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table2-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table2-noborder thead > tr > th, .table2-noborder tbody > tr > th, .table2-noborder tfoot > tr > th, .table2-noborder thead > tr > td, .table2-noborder tbody > tr > td, .table2-noborder tfoot > tr > td { border: none;padding: 2px }
.table2 tr:hover td { background:#82B0C3 }
</style>
<script>
	$(document).ready(function() {
		$("#wait").hide();
		$("#main").show();
		
	});	
</script>
<script type="text/javascript">
$(document).ready(function(){
	
	$('table.StateTable tr.statetablerow th') .parents('table.StateTable') .children('tbody') .toggle();
	$('table.StateTable tr.statetablerow td') .parents('table.StateTable') .children('tbody') .toggle();

	$('table.CityTable th') .parents('table.CityTable') .children('tbody') .toggle();
	
	$('table.CityTable th') .click(
		function() {
			$(this) .parents('table.CityTable') .children('tbody') .toggle();
		}
	)

	$('table.StateTable tr.statetablerow th') .click(
		function() {
		$(this) .parents('table.StateTable') .children('tbody') .toggle();
		}
	)
	$('table.StateTable tr.statetablerow td') .click(
		function() {
			$(this) .parents('table.StateTable') .children('tbody') .toggle();
		}
	)	
});
</script>
<style type="text/css">
	table.CityTable, table.StateTable{width:100%; border-color:#1C79C6; text-align:center;}
	table.StateTable{margin:0px; border:1px solid #fff;;text-align:center;}
	
	table td{padding:0px;}
	table.StateTable thead th{background: #369; padding: 0px; cursor:pointer; color:white;text-align:center;}
	table.CityTable thead th{padding: 0px; background: #C7DBF1;cursor:pointer; color:black;}
	
	table.StateTable td.nopad{padding:0;;text-align:center;}
	table.StateTable tr.statetablerow { background:<%=Constant.darkColorBlu() %> }
	table.StateTable tr.statetablerow:hover { background:<%=Constant.lightColorBlu() %> }
	
</style>
</head>
<body style="overflow-y: hidden;">
	<!--  div style="padding:0 0 0 55px"-->
	<br>
	
	<table style="width:98%;border:none">
		<tr>
			<td style="width:33%;text-align:left;padding:0 0 0 10px">
				<a href="#" id="prepDashOverviewSpmi" target="inner_iframe" >
            		<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/go_back.png" width="35px" height="35px" >
              		<p class="no-margin text-shadow" style="font-weight:bold;font-size:1.em;color:black">BACK</p>
				</a>
			</td>
			<td style="width:34%;text-align:center">
				<a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/home_spmi.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe" >
            		<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/home_black.png" width="45px" height="45px" style=";padding:0 0 0 0">
              		<p class="no-margin text-shadow" style="font-weight:bold;font-size:1.em;color:black;padding:0 0 0 1px">HOME</p>
				</a>
			</td>
			<td style="width:33%;text-align:right;padding:0 10px 0 0">
				
			</td>
		</tr>
	</table>
	<br>
	<div style="text-align:center;padding:0 0 0 5px">
		<span class="tile-group-title">
			<select style="width:98%;height:25px;border:none;text-align-last:center;border-radius: 10px;" disabled>
				<option value="null" selected="selected"><%=keter_prodi%></option> 
			</select>
		</span>
	</div>

<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai</p>
</div>
<div id="main">
	<br>
	<center>
<%
if(v_status_aktif_man!=null && v_status_aktif_man.size()>0) {
	ListIterator li = v_status_aktif_man.listIterator();
	int no_rumpun=1,no_std=1;;
%>		
	
<%
	if(li.hasNext()) {
		String brs = (String)li.next();
		st = new StringTokenizer(brs,"~");
		String prev_id_master_std = st.nextToken();
		String prev_ket_master_std = st.nextToken();
		String prev_id_std = st.nextToken();
		String prev_id_tipe_std = st.nextToken();
		String prev_ket_tipe_std = st.nextToken();
		String prev_tgl_sta_plan = st.nextToken();
		String prev_id_versi_plan = st.nextToken();
		String prev_tgl_sta_do = st.nextToken();
		String prev_id_versi_do = st.nextToken();
		String prev_tgl_sta_eval = st.nextToken();
		String prev_id_versi_eval = st.nextToken();
		String prev_tgl_sta_kendal = st.nextToken();
		String prev_id_versi_kendal = st.nextToken();
		String prev_tgl_sta_upg = st.nextToken();
		String prev_id_versi_upg = st.nextToken();
		%>
	<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="padding:50px 0 50px 0;border:1px solid white;width:70%;background:white;">	
		<thead>
			<tr class="statetablerow">
				<td colspan="3" style="padding:25px 10px;font-size:1.5em;text-align:left;font-weight:bold;color:#fff;border:1px solid #369">
				<%=no_rumpun++%>.  <%=prev_ket_master_std %>
				</td>
			</tr>
				
		</thead>
		<tbody>
			<tr>
				<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:10%;color:#369;text-align:center;border:1px solid #369">
					NO
				</td>
				<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					KETERANGAN
				</td>
				<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:20%;color:#369;text-align:center;padding:0 0 0 10px;border:1px solid #369">
					STATUS
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:center;border:1px solid #369;border-bottom:none">
					<%=no_std++ %>.
				</td>
				<td colspan="2" style="background:<%=Constant.lightColorBlu() %>;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					<%=prev_ket_tipe_std %>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:right;border:1px solid #369;border-bottom:none;border-top:none">
					&nbsp
				</td>
				<td style="background:white;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-right:none">
					a. Manual Perencanaan
				</td>
				<td style="background:white;width:20%;color:#369;text-align:center;padding:0 0 0 10px;border:1px solid #369;border-left:none">
				<%
				if(Checker.isStringNullOrEmpty(prev_tgl_sta_plan)) {
					out.print("Non-Aktif");
				}
				else {
					out.print("Aktif");
				}
				%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:right;border:1px solid #369;border-bottom:none;border-top:none">
					&nbsp
				</td>
				<td style="background:white;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-right:none">
					b. Manual Pelaksanaan
				</td>
				<td style="background:white;width:20%;color:#369;text-align:center;padding:0 0 0 10px;border:1px solid #369;border-left:none">
				<%
				if(Checker.isStringNullOrEmpty(prev_tgl_sta_do)) {
					out.print("Non-Aktif");
				}
				else {
					out.print("Aktif");
				}
				%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:right;border:1px solid #369;border-bottom:none;border-top:none">
					&nbsp
				</td>
				<td style="background:white;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-right:none">
					c. Manual Evaluasi
				</td>
				<td style="background:white;width:20%;color:#369;text-align:center;padding:0 0 0 10px;border:1px solid #369;border-left:none">
				<%
				if(Checker.isStringNullOrEmpty(prev_tgl_sta_eval)) {
					out.print("Non-Aktif");
				}
				else {
					out.print("Aktif");
				}
				%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:right;border:1px solid #369;border-bottom:none;border-top:none">
					&nbsp
				</td>
				<td style="background:white;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-right:none">
					d. Manual Pengandalian
				</td>
				<td style="background:white;width:20%;color:#369;text-align:center;padding:0 0 0 10px;border:1px solid #369;border-left:none">
				<%
				if(Checker.isStringNullOrEmpty(prev_tgl_sta_kendal)) {
					out.print("Non-Aktif");
				}
				else {
					out.print("Aktif");
				}
				%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:right;border:1px solid #369;border-bottom:none;border-top:none">
					&nbsp
				</td>
				<td style="background:white;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-right:none">
					e. Manual Peningkatan
				</td>
				<td style="background:white;width:20%;color:#369;text-align:center;padding:0 0 0 10px;border:1px solid #369;border-left:none">
				<%
				if(Checker.isStringNullOrEmpty(prev_tgl_sta_upg)) {
					out.print("Non-Aktif");
				}
				else {
					out.print("Aktif");
				}
				%>
				</td>
			</tr>
		<%
		while(li.hasNext()) {
			brs = (String)li.next();
			st = new StringTokenizer(brs,"~");
			String id_master_std = st.nextToken();
			String ket_master_std = st.nextToken();
			String id_std = st.nextToken();
			String id_tipe_std = st.nextToken();
			String ket_tipe_std = st.nextToken();
			String tgl_sta_plan = st.nextToken();
			String id_versi_plan = st.nextToken();
			String tgl_sta_do = st.nextToken();
			String id_versi_do = st.nextToken();
			String tgl_sta_eval = st.nextToken();
			String id_versi_eval = st.nextToken();
			String tgl_sta_kendal = st.nextToken();
			String id_versi_kendal = st.nextToken();
			String tgl_sta_upg = st.nextToken();
			String id_versi_upg = st.nextToken();
			if(prev_id_master_std.equalsIgnoreCase(id_master_std)) {
				//masih satu rumpun
				//body
%>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:center;border:1px solid #369;border-bottom:none">
					<%=no_std++ %>.
				</td>
				<td colspan="2" style="background:<%=Constant.lightColorBlu() %>;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					<%=ket_tipe_std %>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:right;border:1px solid #369;border-bottom:none;border-top:none">
					&nbsp
				</td>
				<td style="background:white;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-right:none">
					a. Manual Perencanaan
				</td>
				<td style="background:white;width:20%;color:#369;text-align:center;padding:0 0 0 10px;border:1px solid #369;border-left:none">
				<%
				if(Checker.isStringNullOrEmpty(tgl_sta_plan)) {
					out.print("Non-Aktif");
				}
				else {
					out.print("Aktif");
				}
				%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:right;border:1px solid #369;border-bottom:none;border-top:none">
					&nbsp
				</td>
				<td style="background:white;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-right:none">
					b. Manual Pelaksanaan
				</td>
				<td style="background:white;width:20%;color:#369;text-align:center;padding:0 0 0 10px;border:1px solid #369;border-left:none">
				<%
				if(Checker.isStringNullOrEmpty(tgl_sta_do)) {
					out.print("Non-Aktif");
				}
				else {
					out.print("Aktif");
				}
				%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:right;border:1px solid #369;border-bottom:none;border-top:none">
					&nbsp
				</td>
				<td style="background:white;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-right:none">
					c. Manual Evaluasi
				</td>
				<td style="background:white;width:20%;color:#369;text-align:center;padding:0 0 0 10px;border:1px solid #369;border-left:none">
				<%
				if(Checker.isStringNullOrEmpty(tgl_sta_eval)) {
					out.print("Non-Aktif");
				}
				else {
					out.print("Aktif");
				}
				%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:right;border:1px solid #369;border-bottom:none;border-top:none">
					&nbsp
				</td>
				<td style="background:white;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-right:none">
					d. Manual Pengandalian
				</td>
				<td style="background:white;width:20%;color:#369;text-align:center;padding:0 0 0 10px;border:1px solid #369;border-left:none">
				<%
				if(Checker.isStringNullOrEmpty(tgl_sta_kendal)) {
					out.print("Non-Aktif");
				}
				else {
					out.print("Aktif");
				}
				%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:right;border:1px solid #369;border-bottom:none;border-top:none">
					&nbsp
				</td>
				<td style="background:white;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-right:none">
					e. Manual Peningkatan
				</td>
				<td style="background:white;width:20%;color:#369;text-align:center;padding:0 0 0 10px;border:1px solid #369;border-left:none">
				<%
				if(Checker.isStringNullOrEmpty(tgl_sta_upg)) {
					out.print("Non-Aktif");
				}
				else {
					out.print("Aktif");
				}
				%>
				</td>
			</tr>	
<%				
			}
			else {
				//header
				no_std=1;
%>
		</tbody>
	</table>
	<table class="StateTable" rules="all" cellpadding="0" cellspacing="0" style="border:none;width:70%;background:white;">	
		<thead>
			<tr class="statetablerow">
				<td colspan="3" style="border-top:1px solid #fff;padding:25px 10px;font-size:1.5em;text-align:left;font-weight:bold;color:#fff;border-top:1px solid #fff">
				<%=no_rumpun++%>.  <%=ket_master_std %>
				</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:10%;color:#369;text-align:center;border:1px solid #369">
					NO
				</td>
				<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					KETERANGAN
				</td>
				<td style="font-weight:bold;background:<%=Constant.lightColorGrey() %>;width:20%;color:#369;text-align:center;padding:0 0 0 10px;border:1px solid #369">
					STATUS
				</td>
			</tr>
			<tr>
				<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:center;border:1px solid #369;border-bottom:none">
					<%=no_std++ %>.
				</td>
				<td colspan="2" style="background:<%=Constant.lightColorBlu() %>;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369">
					<%=ket_tipe_std %>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:right;border:1px solid #369;border-bottom:none;border-top:none">
					&nbsp
				</td>
				<td style="background:white;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-right:none">
					a. Manual Perencanaan
				</td>
				<td style="background:white;width:20%;color:#369;text-align:center;padding:0 0 0 10px;border:1px solid #369;border-left:none">
				<%
				if(Checker.isStringNullOrEmpty(tgl_sta_plan)) {
					out.print("Non-Aktif");
				}
				else {
					out.print("Aktif");
				}
				%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:right;border:1px solid #369;border-bottom:none;border-top:none">
					&nbsp
				</td>
				<td style="background:white;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-right:none">
					b. Manual Pelaksanaan
				</td>
				<td style="background:white;width:20%;color:#369;text-align:center;padding:0 0 0 10px;border:1px solid #369;border-left:none">
				<%
				if(Checker.isStringNullOrEmpty(tgl_sta_do)) {
					out.print("Non-Aktif");
				}
				else {
					out.print("Aktif");
				}
				%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:right;border:1px solid #369;border-bottom:none;border-top:none">
					&nbsp
				</td>
				<td style="background:white;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-right:none">
					c. Manual Evaluasi
				</td>
				<td style="background:white;width:20%;color:#369;text-align:center;padding:0 0 0 10px;border:1px solid #369;border-left:none">
				<%
				if(Checker.isStringNullOrEmpty(tgl_sta_eval)) {
					out.print("Non-Aktif");
				}
				else {
					out.print("Aktif");
				}
				%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:right;border:1px solid #369;border-bottom:none;border-top:none">
					&nbsp
				</td>
				<td style="background:white;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-right:none">
					d. Manual Pengandalian
				</td>
				<td style="background:white;width:20%;color:#369;text-align:center;padding:0 0 0 10px;border:1px solid #369;border-left:none">
				<%
				if(Checker.isStringNullOrEmpty(tgl_sta_kendal)) {
					out.print("Non-Aktif");
				}
				else {
					out.print("Aktif");
				}
				%>
				</td>
			</tr>
			<tr>
				<td style="background:<%=Constant.lightColorBlu() %>;width:10%;color:#369;text-align:right;border:1px solid #369;border-bottom:none;border-top:none">
					&nbsp
				</td>
				<td style="background:white;width:70%;color:#369;text-align:left;padding:0 0 0 10px;border:1px solid #369;border-right:none">
					e. Manual Peningkatan
				</td>
				<td style="background:white;width:20%;color:#369;text-align:center;padding:0 0 0 10px;border:1px solid #369;border-left:none">
				<%
				if(Checker.isStringNullOrEmpty(tgl_sta_upg)) {
					out.print("Non-Aktif");
				}
				else {
					out.print("Aktif");
				}
				%>
				</td>
			</tr>
<%	
				prev_id_master_std = new String(id_master_std);
			}
		}
	}	
%>		
		
		
			
		</tbody>	
	</table>
<%
}
else {
%>
	<h2 style="text-align:center">Belum Ada Standar</h2>
<%	
}
%>	
	</center>	
	<!-- div class="tile-area tile-area-scheme-dark fg-white" style="height: 100%; max-height: 100% !important;">
    
		<div class="tile-group seven">
			<span class="tile-group-title">&nbsp</span>
			<div class="tile-container">
				<a href="#" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">
	            	<div class="padding20">
	               	<p class="no-margin text-shadow"><span class="text-bold">keter2</p></span>
					</div>
	            	<div style="margin:5px 0 0 10px;position: absolute;font-weight:bold">  
	            	</div>
				</a>
				
			</div>
				
		</div>
	</div -->
</div>
</body>
</html>
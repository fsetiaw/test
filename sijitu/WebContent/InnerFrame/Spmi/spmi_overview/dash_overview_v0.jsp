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

Vector v_scope_kdpst_spmi = (Vector)session.getAttribute("v_scope_kdpst_spmi");//KDFAKMSPST,KDPSTMSPST,KDJENMSPST,NMPSTMSPST
Boolean spmi_editor = (Boolean) session.getAttribute("spmi_editor");
Vector v_list_survey_no_evaluasi_oleh_saya = (Vector)session.getAttribute("v_list_survey_no_evaluasi_oleh_saya");
Vector v_list_upcoming_survey = (Vector)session.getAttribute("v_list_upcoming_survey");
Vector v_list_unexecute_survey = (Vector)session.getAttribute("v_list_unexecute_survey");

session.removeAttribute("v_list_survey_no_evaluasi_oleh_saya");
session.removeAttribute("v_list_upcoming_survey");
session.removeAttribute("v_list_unexecute_survey");
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
        	        window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/home_standar.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=1&max_data_per_pg=20";
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

</head>
<body style="overflow-y: hidden;">

	<div style="padding:0 0 0 55px">
    		<a href="<%=Constants.getRootWeb() %>/InnerFrame/Spmi/home_spmi.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe" class="tile-small bg-white" data-role="tile">
            	<div class="tile-content iconic">
                    <img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/go_back.png" class="icon" >
                </div>
            	<div style="margin:20px 0 0 55px;">   
                   	<p class="no-margin text-shadow" style="font-weight:bold;font-size:1.em;color:black">GO BACK</p>
                </div>
			</a>	
	</div>	

<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai</p>
</div>
<div id="main">
<%
boolean ada_kegiatan = false;
%>
	<div class="tile-area tile-area-scheme-dark fg-white" style="height: 100%; max-height: 100% !important;">
    
        <div class="tile-group five">
			<span class="tile-group-title">EVALUASI & PENGENDALIAN <%=keter_prodi %></span>
			<div class="tile-container">
			<%

//else {
if(v_list_survey_no_evaluasi_oleh_saya!=null && v_list_survey_no_evaluasi_oleh_saya.size()>0) {	
	ada_kegiatan = true;
	ListIterator li = v_list_survey_no_evaluasi_oleh_saya.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		//tgl_sidak+"~"+kdpst+"~"+controller+"~"+surveyor+"~"+param+"~"+id_hist+"~"+id_kendali+"~"+id_versi+"~"+id_std_isi+"~"+id_std+"~"+id_master_std+"~"+id_tipe_std+"~"+ket_tipe_std;
		StringTokenizer stt = new StringTokenizer(brs,"~");
		String tgl_sidak = stt.nextToken();
		String target_kdpst = stt.nextToken();
		String controller = stt.nextToken();
		String surveyor = stt.nextToken();
		String param_watch = stt.nextToken();
		String id_hist  = stt.nextToken();
		String id_kendali = stt.nextToken();
		String id_versi = stt.nextToken();
		String id_std_isi = stt.nextToken();
		String id_std = stt.nextToken();
		String id_master_std = stt.nextToken();
		String id_tipe_std = stt.nextToken();
		String ket_tipe_std = stt.nextToken();
		
		
    %>    	
          		<a href="get.prepInfoStd?backto=overview&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&fwdto=dashboard_std_isi.jsp" target="inner_iframe" class="tile bg-darkRed fg-white" data-role="tile">
            		<div style="display: table;height: 50px;width: 150px;text-align: center;">
                		<span class="text-bold" style="display: table-cell;vertical-align: middle;font-size:1.1em;font-weight:bold;text-decoration:underline"> <%=ket_tipe_std.toUpperCase()%></span>
                	</div>	
                	<div style="display: table;height: 100px;width: 150px;text-align: center;">
                		<span class="text-bold" style="display: table-cell;vertical-align: middle;font-size:0.9em;text-decoration:none"> EVALUASI & PENGENDALIAN<br><%=param_watch.toUpperCase()%></span>
                	</div>
				</a>
<%
	}
}
		
//upcomming survey
if(v_list_upcoming_survey!=null && v_list_upcoming_survey.size()>0) {
	ada_kegiatan = true;
	ListIterator li = v_list_upcoming_survey.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		//tgl_sidak+"~"+kdpst+"~"+controller+"~"+surveyor+"~"+param+"~"+id_hist+"~"+id_kendali+"~"+id_versi+"~"+id_std_isi+"~"+id_std+"~"+id_master_std+"~"+id_tipe_std+"~"+ket_tipe_std;
		StringTokenizer stt = new StringTokenizer(brs,"~");
		String tgl_sidak = stt.nextToken();
		String target_kdpst = stt.nextToken();
		String controller = stt.nextToken();
		String surveyor = stt.nextToken();
		String param_watch = stt.nextToken();
		String id_hist  = stt.nextToken();
		String id_kendali = stt.nextToken();
		String id_versi = stt.nextToken();
		String id_std_isi = stt.nextToken();
		String id_std = stt.nextToken();
		String id_master_std = stt.nextToken();
		String id_tipe_std = stt.nextToken();
		String ket_tipe_std = stt.nextToken();
		String tgl_next_sidak = stt.nextToken();
		double tot_days = Converter.getHowManyDayAgo(java.sql.Date.valueOf(tgl_next_sidak))*-1;
		int hr= (int)tot_days;
    %>    	
          		<a href="get.prepInfoStd?backto=overview&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&fwdto=dashboard_std_isi.jsp" target="inner_iframe" class="tile bg-orange fg-white" data-role="tile">
            		<div style="display: table;height: 50px;width: 150px;text-align: center;">
                		<span class="text-bold" style="display: table-cell;vertical-align: middle;font-size:1.1em;font-weight:bold;text-decoration:underline"> <%=ket_tipe_std.toUpperCase()%></span>
                	</div>	
                	<div style="display: table;height: 50px;width: 150px;text-align: center;">
                		<span class="text-bold" style="display: table-cell;vertical-align: middle;font-size:0.9em;text-decoration:none"> SURVEY<br><%=param_watch.toUpperCase()%></span>
                	</div>
                	<div style="display: table;height: 50px;width: 150px;text-align: center;">
                		<span class="text-bold" style="display: table-cell;vertical-align: middle;font-size:0.9em;text-decoration:none"> <%=hr %> hari lg <br><%=tgl_next_sidak %></span>
                	</div>
					
				</a>
<%
	}
}



//unexecute survey
if(v_list_unexecute_survey!=null && v_list_unexecute_survey.size()>0) {
	ada_kegiatan = true;
	ListIterator li = v_list_unexecute_survey.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		//tgl_sidak+"~"+kdpst+"~"+controller+"~"+surveyor+"~"+param+"~"+id_hist+"~"+id_kendali+"~"+id_versi+"~"+id_std_isi+"~"+id_std+"~"+id_master_std+"~"+id_tipe_std+"~"+ket_tipe_std;
		StringTokenizer stt = new StringTokenizer(brs,"~");
		String tgl_sidak = stt.nextToken();
		String target_kdpst = stt.nextToken();
		String controller = stt.nextToken();
		String surveyor = stt.nextToken();
		String param_watch = stt.nextToken();
		String id_hist  = stt.nextToken();
		String id_kendali = stt.nextToken();
		String id_versi = stt.nextToken();
		String id_std_isi = stt.nextToken();
		String id_std = stt.nextToken();
		String id_master_std = stt.nextToken();
		String id_tipe_std = stt.nextToken();
		String ket_tipe_std = stt.nextToken();
		String tgl_next_sidak = stt.nextToken();
		//double tot_days = Converter.getHowManyDayAgo(java.sql.Date.valueOf(tgl_next_sidak))*-1;
		//int hr= (int)tot_days;
  %>    	
        		<a href="get.prepInfoStd?backto=overview&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&fwdto=dashboard_std_isi.jsp" target="inner_iframe" class="tile bg-black fg-white" data-role="tile">
          		<div style="display: table;height: 50px;width: 150px;text-align: center;">
              		<span class="text-bold" style="display: table-cell;vertical-align: middle;font-size:1.1em;font-weight:bold;text-decoration:underline"> <%=ket_tipe_std.toUpperCase()%></span>
              	</div>	
              	<div style="display: table;height: 100px;width: 150px;text-align: center;">
              		<span class="text-bold" style="display: table-cell;vertical-align: middle;font-size:0.9em;text-decoration:none">JADWAL SURVEY '<%=param_watch.toUpperCase()%>' TGL <%=Converter.formatDdSlashMmSlashYy(tgl_next_sidak)  %> BELUM DILAKUKAN</span>
              	</div>
              		
				</a>
<%
	}
}

if(!ada_kegiatan) {
	%>    	
			<a href="#" target="inner_iframe" class="tile bg-green fg-white" data-role="tile">
            	<div class="padding20">
                	<p class="no-margin text-shadow"><span class="text-bold">SAAT INI TIDAK ADA KEGIATAN<br> EVALUASI & PENGENDALIAN<br>PENJAMINAN MUTU</p></span>
				</div>
			</a>
<%	
}
%>          		
     		</div>
  		</div>     
    	
    	<div class="tile-group double">
			<span class="tile-group-title">PERENCANAAN</span>
			<div class="tile-container">
			</div>
		</div>	
    	
    	<div class="tile-group double">
			<span class="tile-group-title">PELAKSANAAN</span>
			<div class="tile-container">
			</div>
		</div>	
		
		<div class="tile-group double">
			<span class="tile-group-title">PENINGKATAN</span>
			<div class="tile-container">
			</div>
		</div>	
	</div>
</div>
</body>
</html>
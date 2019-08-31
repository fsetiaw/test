<!DOCTYPE html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />

<link href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/css/metro.css" rel="stylesheet">
<link href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/css/metro-icons.css" rel="stylesheet">
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/metro.js"></script>
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String tipeForm = request.getParameter("formType");
	Vector vDwn = validUsr.getScopeUpd7des2012("hasDownloadMenu");
	Vector v_cf = validUsr.getScopeUpd7des2012("hasEditMkKurikulum");
	String errMsg = request.getParameter("errMsg");
	boolean atFormPengajuanBukaKelasTahap1 = false;
	boolean atFormPengajuanBukaKelasTahap2 = false;
	String kdpst_nmpst = null;
	String backward2 = null;
	
	session.removeAttribute("klsInfo");
	session.removeAttribute("totKls");
	String msg = request.getParameter("msg");
	String pb = request.getParameter("pb");
	String alihkan =request.getParameter("alihkan");
	Vector v_list_prodi_no_class = (Vector)request.getAttribute("v_list_prodi_no_class"); //PST`57301`MANAJEMEN INFORMATIKA`D`117
	request.removeAttribute("v_list_prodi_no_class");
%>


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


</head>
<body style="overflow-y: hidden;">
<div id="header">

<%@ include file="IndexAkademikPengajuanSubMenu.jsp" %>

</div>
<div class="tile-area tile-area-scheme-dark fg-white" style="height: 100%; max-height: 100% !important;">
	<div class="tile-group four">
		<span class="tile-group-title">&nbsp</span>
		<div class="tile-container">

		<!-- Column 1 start -->
	  		<a href="get.listScope?scope=reqBukaKelas&callerPage=null&cmd=bukakelas&atMenu=bukakelas&backTo=<%=backTo %>" target="inner_iframe" class="tile bg-orange fg-white" data-role="tile">
				<div class="tile-content">
		            <img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/buka_kelas2.jpg" alt="Smiley face" data-role="fitImage" data-format="square">
		    	</div>
		    	<div style="margin:5px 0 0 5px;position: absolute;">   
               		<p class="no-margin text-shadow" style="color:#DAA22A;font-size:1.3em"><span class="text-bold" id="pressure">PENGAJUAN </span></p>
				</div>
		    	<div style="margin:78px 0 0 5px;position: absolute;">   
               		<p class="no-margin text-shadow" style="color:#DAA22A"><span class="text-bold" id="pressure">PEMBUKAAN<br>KELAS</span></p>
				</div>
				<div style="margin:110px 0 0 5px;position: absolute;">   
               		<p class="no-margin text-shadow" style="color:#DAA22A"><span class="text-bold" id="pressure">PERKULIAHAN</span></p>
				</div>
         	</a>
			<a href="get.listScope?nuFwdPage=get.listKelasClassPool&scope=reqGabungKelasFak&callerPage=dashPengajuan.jsp&cmd=gabungKelasFak&atMenu=gabungKelasFak&scopeType=prodyOnly&backTo=<%=backTo %>" target="inner_iframe" class="tile-wide bg-orange fg-white" data-role="tile">
				<div class="tile-content">
		            <img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/buka_kelas3a.jpg" alt="Smiley face" data-role="fitImage">
		    	</div>
		    	<div style="margin:5px 0 0 5px;position: absolute;">   
               		<p class="no-margin text-shadow" style="color:#DAA22A;font-size:1.3em"><span class="text-bold" id="pressure">PENGAJUAN </span></p>
				</div>
		    	<div style="margin:35px 0 0 5px;position: absolute;">   
               		<p class="no-margin text-shadow" style="color:#DAA22A"><span class="text-bold" id="pressure">PENGGABUNGAN KELAS</span></p>
				</div>
				<div style="margin:50px 0 0 5px;position: absolute;">   
               		<p class="no-margin text-shadow" style="color:#DAA22A"><span class="text-bold" id="pressure">PERKULIAHAN</span></p>
				</div>
         	</a>
         	<a href="get.listScope?nuFwdPage=get.listKelasClassPool&scope=reqUbahDsnAjar&callerPage=dashPengajuan.jsp&cmd=ubahDosenAjar&atMenu=ubahDosenAjar&scopeType=prodyOnly&backTo=<%=backTo %>" target="inner_iframe" class="tile-wide bg-orange fg-white" data-role="tile">
				<div class="tile-content">
		            <img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/dosen_ajar.jpg" alt="Smiley face" data-role="fitImage">
		    	</div>
		    	<div style="margin:5px 0 0 5px;position: absolute;">   
               		<p class="no-margin text-shadow" style="color:#DAA22A;font-size:1.3em"><span class="text-bold" id="pressure">PENGAJUAN </span></p>
				</div>
		    	<div style="margin:100px 0 0 5px;position: absolute;">   
               		<p class="no-margin text-shadow" style="color:#DAA22A"><span class="text-bold" id="pressure">PERUBAHAN DOSEN<br>PENGAJAR</span></p>
				</div>
         	</a>
		</div>
	</div>
</div>		
</body>
</html>
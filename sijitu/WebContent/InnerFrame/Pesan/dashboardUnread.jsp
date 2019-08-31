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
	
<%	

beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
JSONArray jsoa = (JSONArray)request.getAttribute("jsoa");
request.removeAttribute("jsoa");
String str_range = "21";

//String id = "null";
//String npm = "null";


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
<%
String list_color = "darkTeal,darkBrown,darkCobalt,olive,darkOrange";
StringTokenizer stw = new StringTokenizer(list_color,",");
//String warna = "";
%>    

    <div class="tile-area tile-area-scheme-dark fg-white" style="height: 100%; max-height: 100% !important;">
  
        <div class="tile-group quadro">
			<span class="tile-group-title">PESAN BARU</span>
			<div class="tile-container">
		<%
boolean no_item = true;
if(jsoa!=null && jsoa.length()>0) {
	String info = "";
	
		
	for(int i=0;i<jsoa.length();i++) {
				
		
		JSONObject job = jsoa.getJSONObject(i);
		try {
			info = (String)job.get("INFO");
		}
		catch(JSONException e) {}
		//returnString = returnString+"{\"INFO\":\""+usr_mem_id+","+usr_name+","+id_conv_grp+","+unread+","+grp_nm+","+id_indiv+","+id_struk+","+npm_indiv+","+nmm_indiv+"\"}";
		StringTokenizer st = new StringTokenizer(info,",");
		String usr_npm = st.nextToken();
		String usr_mem_id = st.nextToken();
		String usr_name = st.nextToken();
		String id_conv_grp = st.nextToken();
		String unread = st.nextToken();
		String grp_nm = st.nextToken();
		String id_indiv = st.nextToken();
		String id_struk = st.nextToken();
		String npm_indiv = st.nextToken();
		String nmm_indiv = st.nextToken();
		
		if(!stw.hasMoreTokens()) {
			stw = new StringTokenizer(list_color,",");
		}
		String shown_group_name = "";
		if(usr_npm.equalsIgnoreCase(npm_indiv)) { // anda individunya
			shown_group_name = new String(grp_nm);
            
        	}
        	else { // anda anggota struk
        		String kdpst = Checker.getKdpst(npm_indiv);
        		String prodi = Converter.getNamaKdpst(kdpst);
        		shown_group_name = new String(nmm_indiv+" ["+npm_indiv+"]<br/>"+prodi);
        	
        }
			  	%>
					<a href="<%=Constants.getRootWeb() %>/InnerFrame/Pesan/conversation_main_frame.jsp?npm_indiv=<%=npm_indiv %>&str_range=<%=str_range %>&grp_nm=<%=shown_group_name %>&grp_id=<%=id_conv_grp %>" target="inner_iframe" class="tile bg-<%=stw.nextToken() %> fg-white" data-role="tile">
		            	<div class="padding10">
		                	<p class="no-margin text-shadow"><span class="text-bold">
		                	<%
		                	if(usr_npm.equalsIgnoreCase(npm_indiv)) { // anda individunya
		                    %>
		                    <%=grp_nm %>
		                    <%
		                	}
		                	else { // anda anggota struk
		                		String kdpst = Checker.getKdpst(npm_indiv);
		                		String prodi = Converter.getNamaKdpst(kdpst);
		                		
		                	%>
			                <%=nmm_indiv %><br/>[<%=npm_indiv %>]<br/><br/><%=prodi %>
			                <%	
		                	}
		                	%></span></p>
						</div>
						<%
						if(grp_nm.length()>54){
						%>
						<div style="margin:2px 0 0 10px;position: absolute;font-size:0.8em">   
						<%	
						}
						else if(grp_nm.length()>36){
						%>
						<div style="margin:15px 0 0 10px;position: absolute;font-size:0.8em">
						<% 	
						}
						else if(grp_nm.length()>18){
						%>
						<div style="margin:27px 0 0 10px;position: absolute;font-size:0.8em">
						<%	
						}
						else {
						%>
						<div style="margin:35px 0 0 10px;position: absolute;font-size:0.8em">
						<%	
						}
						%>
                   			<p class="no-margin text-shadow" style="color:white"><span class="text-bold"><%=grp_nm %></span></p>
            			</div>
					</a>       			
			                	<%	

		  	
	}	
}
	 
		
		
		
		
		//==================END OVERVIEW_SEBARAN_TRLSM===========================
		%>

     		
  		</div>     
	</div>  
</div> 


</body>
</html>
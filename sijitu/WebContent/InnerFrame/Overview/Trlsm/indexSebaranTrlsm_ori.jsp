<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<head>
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
String scope_kampus = request.getParameter("scope_kmp");
if(scope_kampus==null) {
	scope_kampus = new String();
}
String target_thsms = request.getParameter("target_thsms");
StringTokenizer st_kampus = new StringTokenizer(scope_kampus,",");


%>
	
   <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="description" content="Metro, a sleek, intuitive, and powerful framework for faster and easier web development for Windows Metro Style.">
    <meta name="keywords" content="HTML, CSS, JS, JavaScript, framework, metro, front-end, frontend, web development">
    <meta name="author" content="Sergey Pimenov and Metro UI CSS contributors">

   

   <meta name="robots" content="noindex, nofollow">
   <meta name="description" content="BootMetro : Simple and complete web UI framework to create web apps with Windows 8 Metro user interface." />
   <meta name="keywords" content="bootmetro, modern ui, modern-ui, metro, metroui, metro-ui, metro ui, windows 8, metro style, bootstrap, framework, web framework, css, html" />
   <meta name="author" content="AozoraLabs by Marcello Palmitessa"/>
   <link rel="publisher" href="https://plus.google.com/117689250782136016574">

   <!-- remove or comment this line if you want to use the local fonts -->
   <!--  link href='http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700' rel='stylesheet' type='text/css' -->
   <link rel="shortcut icon" href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/favicon.ico">
   <link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/css/metro.css">
   <link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/css/metro-icons.css">

   <script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
   <script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/metro.js"></script>

    <style>
    	a:hover {
    		font-size:2em;
    	}
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
<form action="get.sebaranTrlsm" method="post">
<h2 style="color:#369;padding:0 0 0 80px">INFORMASI TAHUN-SEMESTER : 
<select name="target_thsms" onchange="this.form.submit()">
<%
String thsms_reg = Checker.getThsmsHeregistrasi();
String thsms_starting_point = "20152";
for(int i=0;i<10 && thsms_starting_point.compareToIgnoreCase(thsms_reg)<=0;i++) {
	if(target_thsms.equalsIgnoreCase(thsms_starting_point)) {
		%>
	<option value="<%=thsms_starting_point%>" selected="selected"><%=thsms_starting_point%></option>
	<%				
	}
	else {
	%>
	<option value="<%=thsms_starting_point%>"><%=thsms_starting_point%></option>
<%		
	}
	thsms_starting_point = Tool.returnNextThsmsGivenTpAntara(thsms_starting_point);
}
%>
</select>

</h2>
<div class="tile-area tile-area-scheme-dark fg-white" style="height: 100%; max-height: 100% !important;">
</form>
<%
Vector v_overview=(Vector)session.getAttribute("v_overview");

//lit.add(kdpst+"`"+tot_du+"`"+tot_du_wip+"`"+list_npm_du_wip+"`"+tot_cu+"`"+tot_cu_wip+"`"+list_npm_cu_wip+"`"+tot_ou+"`"+tot_ou_wip+"`"+list_npm_ou_wip+"`"+tot_do+"`"+tot_do_wip+"`"+list_npm_do_wip+"`"+tot_mhs_aktif+"`"+list_npm_du_no_krs+"`"+tot_pp_out+"`"+tot_pp_in+"`"+list_npm_pp_wip);
ListIterator li = null,li2=null;
StringTokenizer st = null;




if(v_overview!=null && v_overview.size()>0) {
	//int counter = 0;
	//int tot_aktif=0;
	
//	int tot_aktif = 0;
	String list_distinct_npm_pindah_prodi = "";
	li = v_overview.listIterator();
		while(li.hasNext()) {
			String target_kampus = (String)li.next();
			int tot_cuti=0;
			int tot_cuti_wip=0;
			int tot_od=0;
			int tot_keluar=0;
			
			int tot_her = 0;
			int tot_her_wip = 0;
			int no_krs = 0;
			int tot_pp_out = 0;
			int tot_pp_in = 0;
			int tot_pp_wip = 0;
			int tot_mhs_aktif_per_kampus=0;
			int tot_pmb = 0;
			int tot_pmb_noshow = 0;
			
			Vector vtmp = (Vector)li.next();
			ListIterator litmp = vtmp.listIterator();
			while(litmp.hasNext()) {
				String brs = (String)litmp.next();
				st = new StringTokenizer(brs,"`"); 
				String kdpst = st.nextToken();
				String tot_du = st.nextToken();
				String tot_du_wip = st.nextToken();
				tot_her_wip = tot_her_wip+Integer.parseInt(tot_du_wip);
				String list_npm_du_wip = st.nextToken();
				String tot_cu = st.nextToken();
				tot_cuti=tot_cuti+Integer.parseInt(tot_cu);
				String tot_cu_wip = st.nextToken();
				tot_cuti_wip = tot_cuti_wip+Integer.parseInt(tot_cu_wip);
				String list_npm_cu_wip = st.nextToken();
				String tot_ou = st.nextToken();
				tot_keluar = tot_keluar+Integer.parseInt(tot_ou);
				String tot_ou_wip = st.nextToken();
				String list_npm_ou_wip = st.nextToken();
				String tot_do = st.nextToken();
				tot_od=tot_od+Integer.parseInt(tot_ou)+Integer.parseInt(tot_do);;
				String tot_do_wip = st.nextToken();
				String list_npm_do_wip = st.nextToken();
				String tot_mhs_aktif = st.nextToken();
				tot_mhs_aktif_per_kampus=tot_mhs_aktif_per_kampus+Integer.parseInt(tot_mhs_aktif); //sdh aftare ulang & krs
				tot_her = tot_her+Integer.parseInt(tot_du);
				String list_npm_du_nokrs = st.nextToken();
				tot_pp_out  = tot_pp_out+Integer.parseInt(st.nextToken());
				tot_pp_in  = tot_pp_in+Integer.parseInt(st.nextToken());
				String list_npm_pindah_prodi = st.nextToken();
				if(!Checker.isStringNullOrEmpty(list_npm_pindah_prodi) && !list_distinct_npm_pindah_prodi.contains(list_npm_pindah_prodi)) {
					list_distinct_npm_pindah_prodi = list_distinct_npm_pindah_prodi+"`"+list_npm_pindah_prodi;
				}
				if(!Checker.isStringNullOrEmpty(list_npm_du_nokrs)) {
					StringTokenizer st1 = new StringTokenizer(list_npm_du_nokrs,",");
					no_krs = no_krs+st1.countTokens();
				}
				
				String tt_pmb = st.nextToken();
				tot_pmb=tot_pmb+Integer.parseInt(tt_pmb);
				String tt_pmb_noshow = st.nextToken();
				tot_pmb_noshow=tot_pmb_noshow+Integer.parseInt(tt_pmb_noshow);
				String list_npm_pmb_noshow = st.nextToken();
				//if(!Checker.isStringNullOrEmpty(list_npm_pindah_prodi)) {
				//System.out.println("list_npm_pindah_prodi="+list_npm_pindah_prodi);
				//	StringTokenizer st1 = new StringTokenizer(list_npm_pindah_prodi,",");
				//	tot_pp_wip = tot_pp_wip+st1.countTokens();
				//}
				//if(!Checker.isStringNullOrEmpty(list_npm_du_wip)) {
				//	StringTokenizer st1 = new StringTokenizer(list_npm_du_wip,",");
				//	tot_her_wip = tot_her_wip+st1.countTokens();
				//}
				
				
				//tot_aktif=tot_aktif+Integer.parseInt("0");
				
				
			}
			tot_pp_wip = 0;
			//System.out.println("list_distinct_npm_pindah_prodi="+list_distinct_npm_pindah_prodi);
			if(!Checker.isStringNullOrEmpty(list_distinct_npm_pindah_prodi)) {
				StringTokenizer st1 = new StringTokenizer(list_distinct_npm_pindah_prodi,"`");
				tot_pp_wip = st1.countTokens();
			}
		%>        
        
		<div class="tile-group triple">
			<span class="tile-group-title"><%=Tool.getTokenKe(Checker.getNickNameKampus(target_kampus), 2, "`") %></span>
			<div class="tile-container">
			<div class="tile bg-amber fg-white" data-role="tile">  
        		<div style="margin:10px 0 0 10px;position: absolute;">   
               		<p class="no-margin text-shadow">PENDAFTARAN<span class="text-bold" id="pressure">  MAHASISWA BARU</span></p>
				</div>
				<div style="margin:30px 0 0 10px;position: absolute;">   
               		<p class="no-margin text-shadow">&nbsp<span class="text-bold" id="pressure"></span></p>
				</div>
				<div style="margin:50px 0 0 10px;position: absolute;">   
               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">TOTAL PENDAFTAR /</span></p>
				</div><!--  div class="tile bg-darkOrange fg-white" data-role="tile" -->
				<div style="margin:65px 0 0 10px;position: absolute;">   
               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">BELUM DAFTAR ULANG</span></p>
				</div>
				<div style="margin:110px 0 0 60px;position: absolute;">   
               		<!--  p class="no-margin text-shadow"><span class="text-bold" id="pressure"><a style="color:white;" href="get.whoRegister?target_thsms=<%=target_thsms%>"><%=tot_her %></a>/ <a style="color:white;" href="get.whoRegisterUnapproved"><%=tot_her_wip %></a></span></p-->
               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure"><a href="#" style="color:white;"><%=tot_pmb %></a>&nbsp&nbsp/&nbsp&nbsp<%=tot_pmb_noshow %></span></p>
				</div>
				
          	</div>  
				
				<!--  div class="tile bg-darkOrange fg-white" data-role="tile" -->
				<%
				if(tot_her>0 || tot_her_wip>0) {
				%>
	            <a class="tile bg-darkOrange fg-white" data-role="tile" href="get.whoRegister?target_thsms=<%=target_thsms%>&at_kmp=<%=target_kampus %>" target="inner_iframe">
	            <%
				}
				else {
					%>
		        <a class="tile bg-darkOrange fg-white" data-role="tile" href="#" target="inner_iframe">
		            <%	
				}
	            %>    
	        		<div style="margin:10px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow">TOTAL<span class="text-bold" id="pressure"> PENGAJUAN</span></p>
					</div>
					<div style="margin:30px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow">HEREGISTRASI / DALAM PROSES PERSETUJUAN<span class="text-bold" id="pressure"></span></p>
					</div>
					<div style="margin:60px 0 0 30px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">&nbsp</span></p>
					</div>
					<div style="margin:70px 0 0 60px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">&nbsp</span></p>
					</div>
					<div style="margin:110px 0 0 50px;position: absolute;">   
	               		<!--  p class="no-margin text-shadow"><span class="text-bold" id="pressure"><a style="color:white;" href="get.whoRegister?target_thsms=<%=target_thsms%>"><%=tot_her %></a>/ <a style="color:white;" href="get.whoRegisterUnapproved"><%=tot_her_wip %></a></span></p-->
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure"><%=tot_her %>&nbsp&nbsp/&nbsp&nbsp<%=tot_her_wip %></span></p>
					</div>
					
	          	</a>           	
				<!--  div class="tile bg-amber fg-white" data-role="tile" -->
				<a class="tile bg-amber fg-white" data-role="tile" href="get.whoNoKrs?target_thsms=<%=target_thsms%>" target="inner_iframe">    
	        		<div style="margin:10px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow">TOTAL<span class="text-bold" id="pressure"> MAHASISWA </span></p>
					</div>
					<div style="margin:30px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">SUDAH DAFTAR ULANG TAPI BELUM MEMILIKI KRS</span></p>
					</div>
					<div style="margin:60px 0 0 30px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">&nbsp</span></p>
					</div>
					<div style="margin:70px 0 0 60px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">&nbsp</span></p>
					</div>
					<div style="margin:110px 0 0 50px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure"><%=no_krs %></span></p>
					</div>
					
	          	</a>
	          	
	          	<a class="tile bg-darkOrange fg-white" data-role="tile" href="get.sebaranTrlsm" target="inner_iframe">    
	        		<div style="margin:10px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow">TOTAL<span class="text-bold" id="pressure"> PENGAJUAN</span></p>
					</div>
					<div style="margin:30px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow">PINDAH PRODI<span class="text-bold" id="pressure"></span></p>
					</div>
					<div style="margin:70px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">MASUK : <%=tot_pp_in %></span></p>
					</div>
					<div style="margin:85px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">KELUAR: <%=tot_pp_out %></span></p>
					</div>
					<div style="margin:110px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">DALAM PROSES: <%=tot_pp_wip %></span></p>
					</div>
					
	          	</a>	   				
	   			<%
	   			if(tot_cuti>0 || tot_cuti_wip>0) {
	   			%>	
	   			<a class="tile bg-amber fg-white" data-role="tile" href="go.getWhoRequest?fullname_table_rules=CUTI_RULES&target_thsms=<%=target_thsms %>" target="inner_iframe">
	   			<%
	   			}
	   			else {
	   				%>	
		   		<a class="tile bg-amber fg-white" data-role="tile" href="#" target="inner_iframe">
		   		<%
		   		}
		   		%>    
	        		<div style="margin:10px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow">TOTAL<span class="text-bold" id="pressure"> MAHASISWA</span></p>
					</div>
					<div style="margin:30px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">CUTI / PROSES PERSETUJUAN </span></p>
					</div>
					<div style="margin:60px 0 0 30px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">&nbsp</span></p>
					</div>
					<div style="margin:70px 0 0 60px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">&nbsp</span></p>
					</div>
					<div style="margin:110px 0 0 50px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure"><%=tot_cuti %>&nbsp&nbsp/&nbsp&nbsp<%=tot_cuti_wip %></span></p>
					</div>
					
	          	</a>		
	   			<a class="tile bg-darkOrange fg-white" data-role="tile" href="get.sebaranTrlsm" target="inner_iframe">    
	        		<div style="margin:10px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow">TOTAL<span class="text-bold" id="pressure"> MAHASISWA</span></p>
					</div>
					<div style="margin:30px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">KELUAR/DO</span></p>
					</div>
					<div style="margin:60px 0 0 30px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">&nbsp</span></p>
					</div>
					<div style="margin:70px 0 0 60px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure"></span></p>
					</div>
					<div style="margin:110px 0 0 50px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure"><%=tot_keluar %>&nbsp&nbsp/&nbsp&nbsp<%=tot_od %></span></p>
					</div>
					
	          	</a>
				
     	</div>
  	</div>     
	
<%
	}
}			
//	target_kampus = st_kampus.nextToken();
%>	

</div>

    <!-- hit.ua -->
    <a href='http://hit.ua/?x=136046' target='_blank'>
        <script language="javascript" type="text/javascript"><!--
        Cd=document;Cr="&"+Math.random();Cp="&s=1";
        Cd.cookie="b=b";if(Cd.cookie)Cp+="&c=1";
        Cp+="&t="+(new Date()).getTimezoneOffset();
        if(self!=top)Cp+="&f=1";
        //--></script>
        <script language="javascript1.1" type="text/javascript"><!--
        if(navigator.javaEnabled())Cp+="&j=1";
        //--></script>
        <script language="javascript1.2" type="text/javascript"><!--
        if(typeof(screen)!='undefined')Cp+="&w="+screen.width+"&h="+
        screen.height+"&d="+(screen.colorDepth?screen.colorDepth:screen.pixelDepth);
        //--></script>
        <script language="javascript" type="text/javascript"><!--
        Cd.write("<img src='http://c.hit.ua/hit?i=136046&g=0&x=2"+Cp+Cr+
        "&r="+escape(Cd.referrer)+"&u="+escape(window.location.href)+
        "' border='0' wi"+"dth='1' he"+"ight='1'/>");
        //--></script></a>
    <!-- / hit.ua -->
</body>
</html>

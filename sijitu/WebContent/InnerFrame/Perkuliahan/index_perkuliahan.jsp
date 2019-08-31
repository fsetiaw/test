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
session.removeAttribute("target_tkn_kdpst");
session.removeAttribute("tkn_header");
request.removeAttribute("v");
session.removeAttribute("v");
String thsms_now = Checker.getThsmsNow();
String thsms_nilai = Checker.getThsmsInputNilai();
boolean sy_mhs = validUsr.iAmStu();
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
		$("#cetakKartuUjian").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.cetakKartuUjian',
        		type: 'POST',
        		data: $("#cetakKartuUjian").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";/ToUnivSatyagama/WebContent/InnerFrame/sql/kartu_ujian/ResultSet_v4.jsp
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/kartu_ujian/ResultSet_vkartuUjian.jsp?backto=&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
		
		$("#absenKelas").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.getListOpenedClass_v1',
        		type: 'POST',
        		data: $("#absenKelas").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";/ToUnivSatyagama/WebContent/InnerFrame/sql/kartu_ujian/ResultSet_v4.jsp
        	    	
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Perkuliahan/Absensi/ListKelasOpened_v1.jsp?backto=&target_thsms=<%=thsms_now%>&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
		
		$("#nilaiKelas").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'get.statusKehadiranKelas_v1',
        		type: 'POST',
        		data: $("#nilaiKelas").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";/ToUnivSatyagama/WebContent/InnerFrame/sql/kartu_ujian/ResultSet_v4.jsp
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Perkuliahan/Nilai/dashPenilaian_v2.jsp?backto=&target_thsms=<%=thsms_nilai%>&at_page=1&max_data_per_pg=20";
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
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Perkuliahan/Absensi/DosenAjar/dashAbsensiDsnAjar.jsp?backto=index&cmd=sad";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	});	
    </script>
	<script>	
	   	$(document).on("click", "#updateUsername", function() {
        	$.ajax({
        		url: 'go.maintenanceTrnlm',
        		type: 'POST',
        		data: {
        	        name: $('#name').val()
        	    },
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	window.location.href = "index_stp.jsp";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
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
<%
ListIterator li = null;
Vector v = null;
String target = null, uri = null, url = null;
String cmd = null;
%>
</head>
<body style="overflow-y: hidden;">
    <!--  a href="index.jsp">index</a--><br>
   
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai</p>
</div>
<div id="main">
	<div class="tile-area tile-area-scheme-dark fg-white" style="height: 100%; max-height: 100% !important;">
    
   		<div class="tile-group quarto">
		<span class="tile-group-title">TAHUN/SMS <%=thsms_now %></span>
		<div class="tile-container">
	    <%
boolean jadwal_dosen_editor = false;
boolean ngajar_kelas = false;
if(!sy_mhs && (session.getAttribute("ada_kelas_yg_diajar")!=null && ((String)session.getAttribute("ada_kelas_yg_diajar")).equalsIgnoreCase("true")) || (jadwal_dosen_editor && !sy_mhs)) {
    ngajar_kelas = true;
}
if(validUsr.isAllowTo("sad")>0) {
	jadwal_dosen_editor = true;
}
//System.out.println("1");
   			//System.out.println("ngajar_kelas="+ngajar_kelas);
if(ngajar_kelas || jadwal_dosen_editor) {
				//if(!sy_mhs && (session.getAttribute("ada_kelas_yg_diajar")!=null && ((String)session.getAttribute("ada_kelas_yg_diajar")).equalsIgnoreCase("true")) || (jadwal_dosen_editor && !sy_mhs)) {
  				
	v = validUsr.getScopeObjScope_vFinal("sad", true, false, false, "KDPST", null);
	if((v!=null && v.size()>0) || ngajar_kelas) {
		//System.out.println("2");
	%>
	
		<div class="tile bg-orange fg-white" data-role="tile">
    	<form id="kehadiranDosen">
    		<input type="hidden" name="cmd" value="sad"/>
    		<input type="hidden" name="target_thsms" value="<%=thsms_now%>"/>
    		<input type="hidden" name="backto" value="home"/>
   			<div style="margin:10px 0 0 5px;position: absolute;">  
        		<section style="font-weight:bold;text-align:center">STATUS KEDATANGAN DOSEN</section>
			</div>
			<%
		if(jadwal_dosen_editor) {
			%>
			<div style="margin:50px 0 0 10px;position: absolute;">
    	    	<select name=target_kdpst style="width:130px;height:25px;border:none;text-align-last:center">
    	    		<option value="all">Semua Kelas</option>
    	    <%
	  		li = v.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				//System.out.println("anu="+brs);
				StringTokenizer st = new StringTokenizer(brs);
				String idobj = st.nextToken();
				if(st.hasMoreTokens()) {
					String kdpst = st.nextToken();
					String nmm = st.nextToken();
					nmm = nmm.replace("MHS_", "");
					while(nmm.contains("_")) {
						nmm = nmm.replace("_", " ");
					}
				%>
					<option value="<%=kdpst%>"><%=nmm.toUpperCase() %></option>
				<%
				}
				
			}
    	    %>		
    	    	</select>
    	    </div>
    	    <div style="margin:80px 0 0 10px;position: absolute;">
    	    	<select name=target_shift style="width:130px;height:25px;border:none;text-align-last:center" required>
    	    		<option value="all">Semua Shift</option>
    	    		<%
    	    v = Checker.getListShiftInVector();
    	    li = v.listIterator();
    		while(li.hasNext()) {
    	    	String brs = (String)li.next();
    	    	String seperator = Checker.getSeperatorYgDigunakan(brs);
    	    	String keter_shift = Tool.getTokenKe(brs, 1,seperator);
    	    		%>
    	    		<option value="<%=keter_shift%>"><%=keter_shift%></option>
    	    		<%	
 			}
    	    				
    	    		%>
    	    	</select>
    	    </div>
    	   <%
		}
			//System.out.println("3");	
			%> 
			<div style="margin:115px 0 0 13px;position: absolute;">
				<input type="submit" value="Next" style="width:125px;height:25px"/>
			</div>
			
       	</form>
       	</div>
 <%  
	}
}
         
//System.out.println("4");			 
			 
v = validUsr.getScopeObjScope_vFinal("viewAbsen", true, false, true, "KDPST", null);
if(v!=null && v.size()>0) {		 
	//if(validUsr.isAllowTo("viewAbsen")>0) {
	%>
			<div class="tile bg-orange fg-white" data-role="tile">
        	<form id="absenKelas">
        		<input type="hidden" name="cmd" value="viewAbsen"/>
        		<input type="hidden" name="target_thsms" value="<%=thsms_now%>"/>
        		
       			<div style="margin:10px 0 0 5px;position: absolute;">  
            		<section style="font-weight:bold;text-align:center">ABSENSI KELAS</section>
				</div>
				<div style="margin:50px 0 0 10px;position: absolute;">
        	    	<select name=target_kdpst style="width:130px;height:25px;border:none;text-align-last:center">
        	    		<option value="all">ALL PRODI</option>
        	    <%
	li = v.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs);
		String idobj = st.nextToken();
		if(st.hasMoreTokens()) {
			String kdpst = st.nextToken();
			String nmm = st.nextToken();
			nmm = nmm.replace("MHS_", "");
			while(nmm.contains("_")) {
				nmm = nmm.replace("_", " ");
			}
					%>
						<option value="<%=kdpst%>"><%=nmm.toUpperCase() %></option>
					<%
		}
		
	}
        	    %>		
        	    	</select>
        	    </div>
        	    <div style="margin:80px 0 0 10px;position: absolute;">
        	    	<select name=target_shift style="width:130px;height:25px;border:none;text-align-last:center" required>
        	    		<option value="all">ALL SHIFT</option>
        	    		<%
        	    		
    v = Checker.getListShiftInVector();
        	    		//System.out.println("5");	
        	    		//v = Checker.getListTipeKartuUjianAktif(null,"KODE_UJIAN");
    li = v.listIterator();
    while(li.hasNext()) {
    	String brs = (String)li.next();
        String seperator = Checker.getSeperatorYgDigunakan(brs);
        String keter_shift = Tool.getTokenKe(brs, 1,seperator);
        	    		%>
        	    		<option value="<%=keter_shift%>"><%=keter_shift%></option>
        	    		<%	
    }
        	    				
        	    		%>
        	    	</select>
        	    </div>
				<div style="margin:115px 0 0 13px;position: absolute;">
					<input type="submit" value="Next" style="width:125px;height:25px"/>
				</div>
           	</form>
           	</div>
           	
           				
	<%			 
}
	//System.out.println("6");	
cmd = "cetakKartuUjian";				 
v = validUsr.getScopeObjScope_vFinal(cmd, true, false, true, "KDPST", null);
if(v!=null && v.size()>0) {
    %> 
			<div class="tile bg-taupe fg-white" data-role="tile">
        	<form id="cetakKartuUjian">
        		<input type="hidden" name="cmd" value="<%=cmd%>"/>
       			<div style="margin:10px 0 0 5px;position: absolute;">  
            		<section style="font-weight:bold;text-align:center">PEMBUATAN KARTU UJIAN</section>
				</div>
				<div style="margin:50px 0 0 10px;position: absolute;">
        	    	<select name=target_kdpst style="width:130px;height:25px;border:none;text-align-last:center">
        	    		<option value="all">ALL PRODI</option>
        	    <%
	li = v.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs);
		String idobj = st.nextToken();
		if(st.hasMoreTokens()) {
			String kdpst = st.nextToken();
			String nmm = st.nextToken();
			nmm = nmm.replace("MHS_", "");
			while(nmm.contains("_")) {
				nmm = nmm.replace("_", " ");
			}
					%>
						<option value="<%=kdpst%>"><%=nmm.toUpperCase() %></option>
					<%
		}
		
	}
        	    %>		
        	    	</select>
        	    </div>
        	    <div style="margin:80px 0 0 10px;position: absolute;">
        	    	<select name=tipe_ujian style="width:130px;height:25px;border:none;text-align-last:center" required>
        	    		<option value="">TIPE UJIAN</option>
        	    		<%
        	    		//System.out.println("7");	
v = Checker.getListTipeKartuUjianAktif(null,"KODE_UJIAN");
li = v.listIterator();
while(li.hasNext()) {
	String kode = (String)li.next();
        	    		%>
        	    		<option value="<%=kode%>"><%=kode%></option>
        	    		<%	
}
        	    				
        	    		%>
        	    	</select>
        	    </div>
				<div style="margin:115px 0 0 13px;position: absolute;">
					<input type="submit" value="Next" style="width:125px;height:25px"/>
				</div>
           	</form>
           	</div>
           	<%
           	/*
target = Constants.getRootWeb()+"/InnerFrame/indexPerkuliahan.jsp";
uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
url = PathFinder.getPath(uri, target);
			%>
    		<a href="get.listTipeUjian?tab=wip" target="inner_iframe" class="tile bg-darkTeal fg-white" data-role="tile">
          		<div class="padding20">
              	<p class="no-margin text-shadow"><span class="text-bold">KARTU UJIAN VERSI LAMA</span></p>
				</div>
			</a> 
        <%
        	*/
} 
//System.out.println("8");	
        %>   
          		

     	</div>
  		</div>    
    
        <div class="tile-group quarto">
		<span class="tile-group-title">TAHUN/SMS <%=thsms_nilai %></span>
		<div class="tile-container">
		<%
cmd = "ink";
v = validUsr.getScopeObjScope_vFinal(cmd, true, false, true, "KDPST", null);
if(v!=null && v.size()>0) {		 
	//if(validUsr.isAllowTo("viewAbsen")>0) {
	%>
			<div class="tile bg-taupe fg-white" data-role="tile">
			<form id="nilaiKelas">
        		<input type="hidden" name="cmd" value="<%=cmd%>"/>
        		<input type="hidden" name="target_thsms" value="<%=thsms_nilai%>"/>
        		
       			<div style="margin:10px 0 0 5px;position: absolute;">  
            		<section style="font-weight:bold;text-align:center">INPUT NILAI KELAS PERKULIAHAN</section>
				</div>
				<div style="margin:50px 0 0 10px;position: absolute;">
        	    	<select name=target_kdpst style="width:130px;height:25px;border:none;text-align-last:center">
        	    		<option value="all">ALL PRODI</option>
        	    <%
	li = v.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		//System.out.println("brs = "+brs);
		StringTokenizer st = new StringTokenizer(brs);
		String idobj = st.nextToken();
		
		if(st.hasMoreTokens()) {
			String kdpst = st.nextToken();
			String nmm = st.nextToken();
			nmm = nmm.replace("MHS_", "");
			while(nmm.contains("_")) {
				nmm = nmm.replace("_", " ");
			}
					%>
						<option value="<%=kdpst%>"><%=nmm.toUpperCase() %></option>
					<%
		}
		
	}
        	    %>		
        	    	</select>
        	    </div>
        	    <div style="margin:80px 0 0 10px;position: absolute;">
        	    	<select name=target_shift style="width:130px;height:25px;border:none;text-align-last:center" required>
        	    		<option value="all">ALL SHIFT</option>
        	    		<%
        	    		
    v = Checker.getListShiftInVector();
        	    		//v = Checker.getListTipeKartuUjianAktif(null,"KODE_UJIAN");
    li = v.listIterator();
    while(li.hasNext()) {
    	String brs = (String)li.next();
        String seperator = Checker.getSeperatorYgDigunakan(brs);
        String keter_shift = Tool.getTokenKe(brs, 1,seperator);
        	    		%>
        	    		<option value="<%=keter_shift%>"><%=keter_shift%></option>
        	    		<%	
  	}
        	    				
        	    		%>
        	    	</select>
        	    </div>
				<div style="margin:115px 0 0 13px;position: absolute;">
					<input type="submit" value="Next" style="width:125px;height:25px"/>
				</div>
           	</form>
           	</div>
	<%			 
}
	%>
		</div>
		</div>	
		
	</div>
</div>
</body>
</html>
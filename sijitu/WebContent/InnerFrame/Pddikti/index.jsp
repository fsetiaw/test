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
	<%@ page import="beans.tools.filter.*" %>
	<%@ page import="beans.dbase.overview.*" %>
<%

//System.out.println("cihuy");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");

String atBoxMenu= request.getParameter("atBoxMenu"); 
String status_proses = request.getParameter("status_proses"); 
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
			
			$("#cekKrsError").submit(function(e) {	
		   		e.preventDefault(); //STOP default action
		   		$.ajax({
	        		url: 'go.cekKrsError',
	        		type: 'POST',
	        		data: $("#cekKrsError").serialize(),
	        	    beforeSend:function(){
	        	    	$("#wait").show();
	        	    	$("#main").hide();
	        	        // this is where we append a loading image
	        	    },
	        	    success:function(data){
	        	        // successful request; do something with the data 
	        	    	//window.location.href = "index_stp.jsp";
	        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v3.jsp";
	        	    },
	        	    error:function(){
	        	        // failed request; give feedback to user
	        	    }
	        	})
	        	return false;
	        });
			
			$("#cekInputDobel").submit(function(e) {	
		   		e.preventDefault(); //STOP default action
		   		$.ajax({
	        		url: 'go.cekInputDobel',
	        		type: 'POST',
	        		data: $("#cekInputDobel").serialize(),
	        	    beforeSend:function(){
	        	    	$("#wait").show();
	        	    	$("#main").hide();
	        	        // this is where we append a loading image
	        	    },
	        	    success:function(data){
	        	        // successful request; do something with the data 
	        	    	//window.location.href = "index_stp.jsp";
	        	        
	        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=get.sebaranTrlsm&at_page=1&max_data_per_pg=20";
	        	    },
	        	    error:function(){
	        	        // failed request; give feedback to user
	        	    }
	        	})
	        	return false;
	        });
			
			
			$("#setDsnAndKlsPll").submit(function(e) {	
		   		e.preventDefault(); //STOP default action
		   		$.ajax({
	        		url: 'go.setDsnAndKlsPll',
	        		type: 'POST',
	        		data: $("#setDsnAndKlsPll").serialize(),
	        	    beforeSend:function(){
	        	    	$("#wait").show();
	        	    	$("#main").hide();
	        	        // this is where we append a loading image
	        	    },
	        	    success:function(data){
	        	        // successful request; do something with the data 
	        	    	//window.location.href = "index_stp.jsp";
	        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Pddikti/Dosen/setting_dosen_v2.jsp";
	        	    },
	        	    error:function(){
	        	        // failed request; give feedback to user
	        	    }
	        	})
	        	return false;
	        });
			
			$("#setBtstu").submit(function(e) {	
		   		e.preventDefault(); //STOP default action
		   		$.ajax({
	        		url: 'go.setBtstu',
	        		type: 'POST',
	        		data: $("#setBtstu").serialize(),
	        	    beforeSend:function(){
	        	    	$("#wait").show();
	        	    	$("#main").hide();
	        	        // this is where we append a loading image
	        	    },
	        	    success:function(data){
	        	        // successful request; do something with the data 
	        	    	//window.location.href = "index_stp.jsp";
	        	    	window.location.href = "index.jsp";
	        	    },
	        	    error:function(){
	        	        // failed request; give feedback to user
	        	    }
	        	})
	        	return false;
	        });
			
			
			$(document).on("click", "#hitungSksdi", function() {
	        	$.ajax({
	        		url: 'go.hitungSksdi',
	        		type: 'POST',
	        		data: {
	        	        name: $('#name').val()
	        	    },
	        	    beforeSend:function(){
	        	    	$("#wait").show();
	        	    	$("#main").hide();
	        	        // this is where we append a loading image
	        	    },
	        	    success:function(data){
	        	        // successful request; do something with the data 
	        	    	window.location.href = "index.jsp";
	        	    },
	        	    error:function(){
	        	        // failed request; give feedback to user
	        	    }
	        	})
	        	return false;
	        });
			
			
			$("#updateColRobotNilaiLessThenMin").submit(function(e) {	
		   		e.preventDefault(); //STOP default action
		   		$.ajax({
		    		url: 'go.gantiNilaiDibawahMinimal',
		    		type: 'POST',
		    		data: $("#updateColRobotNilaiLessThenMin").serialize(),
		    	    beforeSend:function(){
		    	    	$("#wait").show();
		    	    	$("#main").hide();
		    	        // this is where we append a loading image
		    	    },
		    	    success:function(data){
		    	        // successful request; do something with the data 
		    	    	window.location.href = "index.jsp";
		    	    },
		    	    error:function(){
		    	        // failed request; give feedback to user
		    	    }
		    	})
		    	return false;
		    });
			
			$("#hitungTrakmKolomRobot").submit(function(e) {	
		   		e.preventDefault(); //STOP default action
		   		$.ajax({
		    		url: 'go.hitungTrakmKolomRobot',
		    		type: 'POST',
		    		data: $("#hitungTrakmKolomRobot").serialize(),
		    	    beforeSend:function(){
		    	    	$("#wait").show();
		    	    	$("#main").hide();
		    	        // this is where we append a loading image
		    	    },
		    	    success:function(data){
		    	        // successful request; do something with the data 
		    	    	window.location.href = "index.jsp";
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
			
		});	
		
		
		
	</script>
	<script>	
		

		     
	   	
	   	
    </script>	
		

</head>
<body style="overflow-y: hidden;">
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai</p>
</div>
<div id="main">

<%
try {
%>

    <div class="tile-area tile-area-scheme-dark fg-white" style="height: 100%; max-height: 100% !important;">
    <%
SearchDbMainNotification sdmn = new SearchDbMainNotification(validUsr.getNpm());
if(!validUsr.iAmStu() && validUsr.isAllowTo("hasPddiktiMenu")>0) {
    	
    	String thsms_now = Checker.getThsmsNow();
    %>
    
	<div class="tile-group quadro">
   		<span class="tile-group-title">ERROR CHECKER</span>
        <div class="tile-container">
        	<div class="tile bg-darkRed fg-white" data-role="tile">
        		<form id="cekInputDobel">
            		<div style="margin:10px 0 0 5px;position: absolute;">  
            		<section style="font-weight:bold">(1)<br>CEK INPUT MHS DOBEL ANGKATAN:</section><br>
            		&nbsp&nbsp<input type="text" name="smawl" style="width:75px"/><br>
            		<p style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px"/></p>
            		</div>
            	</form>
          	</div>
        	<div class="tile bg-lime fg-white" data-role="tile">
        		<form id="cekKrsError">
            		<div style="margin:10px 0 0 5px;position: absolute;">  
            		<section style="font-weight:bold">(1)<br>CEK KRS ERROR </section><br>
            		THSMS &nbsp&nbsp<input type="text" name="thsms" style="width:75px"/><br>
            		<p style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px"/></p>
            		</div>
            	</form>
          	</div>
			<a href="<%=Constants.getRootWeb() %>/InnerFrame/Pddikti/Dosen/setting_dosen_v1.jsp?cmd=ink&target_thsms=20152&backTo=get.notifications" target="inner_iframe" class="tile bg-lime fg-white" data-role="tile">
            	<div class="padding20">
                	<p class="no-margin text-shadow">FORM DOSEN<span class="text-bold"><br>PENGAJAR<br>PERKULIAHAN</span><br/></p>
				</div>
			</a>  
			<a href="<%=Constants.getRootWeb() %>/InnerFrame/Pddikti/Mhs/setting_mhs_v1.jsp?cmd=ink&target_thsms=20152&backTo=get.notifications" target="inner_iframe" class="tile bg-grey fg-white" data-role="tile">
            	<div class="padding20">
                	<p class="no-margin text-shadow">DAFTARKAN MAHASISWA<span class="text-bold"><br>KE DALAM KELAS<br>PERKULIAHAN</span><br/></p>
				</div>
			</a>        
        	<div class="tile bg-orange fg-white" data-role="tile">
            	<div class="tile-content image-set">
            	<br/>
            		<form action="go.inputKrsSmawl" method="POST">
            		<div style="margin:0 0 0 25px;position: absolute;">  
            		<section style="font-weight:bold">INPUT KRS </section>
            		PRODI : <input type="text" name="kdpst" style="width:75px"/><br>
            		SMAWL : <input type="text" name="smawl" style="width:75px"/>
            		<br><input type="submit" value="execute" style="width:100px"/>
            		</div>
            		</form>
                </div>
          	</div>
            <div class="tile-wide bg-orange fg-white" data-role="tile">
            	<div class="tile-content image-set">
            	
            		<a href="get.bebanDosenEpsbed?target_thsms=20152&backTo=get.notifications" target="inner_iframe"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/workload.jpg" data-role="fitImage" data-format="fill"></a>
                </div>
                <div style="margin:0 0 0 172px;position: absolute;">   
                	<p id="city_weather_daily"></p> 
                    <p class="no-margin text-shadow"><span class="text-bold">BEBAN MENGAJAR<br>DOSEN</span></p>
                </div>
          	</div>
          	
			<%
			if(!Checker.isStringNullOrEmpty(atBoxMenu) && atBoxMenu.equalsIgnoreCase("sink_nilai_robot")) {
			//if(true) {	
			%>
			<div class="tile-wide bg-darkBlue fg-white" data-role="tile">
	           	<form action="go.nilaiByRobot" method="POST">
	           		<div class="padding10">
	               		<p class="no-margin text-shadow">SINKRONISASI NILAI SEMENTARA<span class="text-bold"><br>MAHASISWA</span>
	               		</p><br>
	               		Tahun-sms : <input type="text" name="thsms" style="width:65px"/>
	           			&nbsp&nbsp&nbsp&nbsp<input type="submit" value="Next" style="width:75px;height:25px"/>
	           			<br>
	           			<div style="font-weight:bold;text-align:center;padding:5px 0 0 0">
	           			<%=status_proses %>
	           			</div>
	           		</div>
	           	</form>
	        </div>  
	         
	        	<%	
			}
			else {
			%>
			<div class="tile bg-darkBlue fg-white" data-role="tile">
            	<form action="go.nilaiByRobot" method="POST">
            		<div class="padding10">
                		<p class="no-margin text-shadow">COPY NILAI RIL <span class="text-bold"><br>KE KOLOM BACKUP NILAI</span>
                		</p>
                		THSMS : <input type="text" name="thsms" style="width:75px" required/><br>
            			<p style="padding:0 0 0 0"><input type="submit" value="Next" style="width:125px"/></p>
            		</div>
            	</form>
          	</div>
          	<!--  a href="go.gantiNilaiTundaByRobot" target="inner_iframe" class="tile bg-lime fg-white" data-role="tile">
            	<div class="padding10">
                	<p class="no-margin text-shadow">NILAI BY ROBOT <span class="text-bold"><br>MATAKULIAH YG MASIH <br>NILAI TUNDA</span></p>
            	</div>
            </a-->       
        	<%
			}
        	%>
        	<a href="go.syncDataDosen" target="inner_iframe" class="tile bg-darkRed fg-white" data-role="tile">
            	<div class="padding20">
                	<p class="no-margin text-shadow">SINKRONISASI<span class="text-bold"><br>DATA DOSEN<br>DARI FEEDER</span><br/></p>
				</div>
			</a> 
        	<!--  a href="#" id="updateColRobotNilaiLessThenMin" target="inner_iframe" class="tile bg-lime fg-white" data-role="tile">
            	<div class="padding10">
                	<p class="no-margin text-shadow">KASIH NILAI SEMENTARA <span class="text-bold"><br>KOLOM ROBOT UNTUK<br>MATAKULIAH DENGAN NILAI <br>DIBAWAH MINIMAL</span></p>
            	</div>
            </a --> 
            <div class="tile-wide bg-lime fg-white" data-role="tile">
            	<form id="updateColRobotNilaiLessThenMin">
            		<div style="margin:5px 0 0 5px;position: absolute;">  
            		<p class="no-margin text-shadow">KASIH NILAI SEMENTARA <span class="text-bold">KOLOM ROBOT UNTUK MATAKULIAH DENGAN NILAI DIBAWAH MINIMAL</span></p>
            		THSMS &nbsp&nbsp: <input type="text" id="thsms" name="thsms" style="width:75px"/><br>
            		<p style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px;height:25px"/></p>
            		</div>
            	</form>	
            </div>
            <div class="tile bg-darkOrange fg-white" data-role="tile">
            	<form id="hitungTrakmKolomRobot">
            		<div style="margin:10px 0 0 3px;position: absolute;">  
            		<section style="font-weight:bold">HITUNG TRAKM KOLOM ROBOT</section><br>
            		THSMS : <input type="text" name="thsms" style="width:75px"/><br>
            		PRODI &nbsp: <input type="text" name="kdpst" style="width:75px"/><br>
            		<p style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px"/></p>
            		</div>
            	</form>
          	</div>
          	<a  href="#" id="hitungSksdi" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">
            	<div class="padding20">
                	<p class="no-margin text-shadow"><span class="text-bold">HITUNG SKSDI SELURUH MAHASISWA BERDASARKAN TOTAL SKS MATAKULIAH P.T. ASAL (ONE TIME)</span></p>
				</div>
			</a>   
   		</div>		
    </div>
    
    <div class="tile-group quadro">
   		<span class="tile-group-title">FIXER</span>
        <div class="tile-container">
        	
        	<div class="tile bg-darkOrange fg-white" data-role="tile">
            	<form id="setBtstu">
            		<div style="margin:10px 0 0 3px;position: absolute;">  
            		<section style="font-weight:bold">(1)<br>SET MHS BATAS STUDI</section><br>
            		<% 
            		/*
            		RESERVED
            		PRODI &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp: <input type="text" name="kdpst" style="width:55px"/><br>
            		*/
            		%>
            		ANGKATAN : <input type="text" name="smawl" style="width:55px"/><br>
            		<p style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px"/></p>
            		</div>
            		
            	</form>
          	</div>
          	<div class="tile bg-lime fg-white" data-role="tile">
            	<form id="setDsnAndKlsPll">
            		<input type="hidden" name="dari" value="index"/>
            		<div style="margin:10px 0 0 3px;position: absolute;">  
            		<section style="font-weight:bold">(2)<br>SET KELAS PARALEL & DOSEN PENGAJAR</section><br>
            		<% 
            		/*
            		RESERVED
            		PRODI &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp: <input type="text" name="kdpst" style="width:55px"/><br>
            		*/
            		%>
            		THSMS : <input type="text" name="thsms" style="width:55px"/><br>
            		<p style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px"/></p>
            		</div>
            		
            	</form>
          	</div>
        	<div class="tile bg-darkBlue fg-white" data-role="tile">
            	<form action="go.prepFormKo" method="POST">
            		<div style="margin:10px 0 0 5px;position: absolute;">  
            		<section style="font-weight:bold">SETTING KO MHS</section><br>
            		PRODI &nbsp: <input type="text" name="kdpst" style="width:75px"/><br>
            		THSMS : <input type="text" name="thsms" style="width:75px"/><br>
            		<p style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px"/></p>
            		</div>
            		
            	</form>
          	</div>
			<a href="<%=Constants.getRootWeb() %>/InnerFrame/Pddikti/Dosen/setting_dosen_v1.jsp?cmd=ink&target_thsms=20152&backTo=get.notifications" target="inner_iframe" class="tile bg-lime fg-white" data-role="tile">
            	<div class="padding20">
                	<p class="no-margin text-shadow">FORM DOSEN<span class="text-bold"><br>PENGAJAR<br>PERKULIAHAN</span><br/></p>
				</div>
			</a>  
			<a href="<%=Constants.getRootWeb() %>/InnerFrame/Pddikti/Mhs/setting_mhs_v1.jsp?cmd=ink&target_thsms=20152&backTo=get.notifications" target="inner_iframe" class="tile bg-grey fg-white" data-role="tile">
            	<div class="padding20">
                	<p class="no-margin text-shadow">DAFTARKAN MAHASISWA<span class="text-bold"><br>KE DALAM KELAS<br>PERKULIAHAN</span><br/></p>
				</div>
			</a>        
        	<div class="tile bg-orange fg-white" data-role="tile">
            	<div class="tile-content image-set">
            	<br/>
            		<form action="go.inputKrsSmawl" method="POST">
            		<div style="margin:0 0 0 25px;position: absolute;">  
            		<section style="font-weight:bold">INPUT KRS </section>
            		PRODI : <input type="text" name="kdpst" style="width:75px"/><br>
            		SMAWL : <input type="text" name="smawl" style="width:75px"/>
            		<br><input type="submit" value="execute" style="width:100px"/>
            		</div>
            		</form>
                </div>
          	</div>
            <div class="tile-wide bg-orange fg-white" data-role="tile">
            	<div class="tile-content image-set">
            	
            		<a href="get.bebanDosenEpsbed?target_thsms=20152&backTo=get.notifications" target="inner_iframe"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/workload.jpg" data-role="fitImage" data-format="fill"></a>
                </div>
                <div style="margin:0 0 0 172px;position: absolute;">   
                	<p id="city_weather_daily"></p> 
                    <p class="no-margin text-shadow"><span class="text-bold">BEBAN MENGAJAR<br>DOSEN</span></p>
                </div>
          	</div>
          	
			<%
			if(!Checker.isStringNullOrEmpty(atBoxMenu) && atBoxMenu.equalsIgnoreCase("sink_nilai_robot")) {
			//if(true) {	
			%>
			<div class="tile-wide bg-darkBlue fg-white" data-role="tile">
	           	<form action="go.nilaiByRobot" method="POST">
	           		<div class="padding10">
	               		<p class="no-margin text-shadow">SINKRONISASI NILAI SEMENTARA<span class="text-bold"><br>MAHASISWA</span>
	               		</p><br>
	               		Tahun-sms : <input type="text" name="thsms" style="width:65px"/>
	           			&nbsp&nbsp&nbsp&nbsp<input type="submit" value="Next" style="width:75px;height:25px"/>
	           			<br>
	           			<div style="font-weight:bold;text-align:center;padding:5px 0 0 0">
	           			<%=status_proses %>
	           			</div>
	           		</div>
	           	</form>
	        </div>  
	         
	        	<%	
			}
			else {
			%>
			<div class="tile bg-darkBlue fg-white" data-role="tile">
            	<form action="go.nilaiByRobot" method="POST">
            		<div class="padding10">
                		<p class="no-margin text-shadow"> <span class="text-bold"><br>NILAI SEMENTARA <br>MAHASISWA</span>
                		</p>
                		THSMS : <input type="text" name="thsms" style="width:75px" required/><br>
            			<p style="padding:0 0 0 0"><input type="submit" value="Next" style="width:125px"/></p>
            		</div>
            	</form>
          	</div>
          	<!--  a href="go.gantiNilaiTundaByRobot" target="inner_iframe" class="tile bg-lime fg-white" data-role="tile">
            	<div class="padding10">
                	<p class="no-margin text-shadow">NILAI BY ROBOT <span class="text-bold"><br>MATAKULIAH YG MASIH <br>NILAI TUNDA</span></p>
            	</div>
            </a-->       
        	<%
			}
        	%>
        	<a href="go.syncDataDosen" target="inner_iframe" class="tile bg-darkRed fg-white" data-role="tile">
            	<div class="padding20">
                	<p class="no-margin text-shadow">SINKRONISASI<span class="text-bold"><br>DATA DOSEN<br>DARI FEEDER</span><br/></p>
				</div>
			</a> 
        	<!--  a href="#" id="updateColRobotNilaiLessThenMin" target="inner_iframe" class="tile bg-lime fg-white" data-role="tile">
            	<div class="padding10">
                	<p class="no-margin text-shadow">KASIH NILAI SEMENTARA <span class="text-bold"><br>KOLOM ROBOT UNTUK<br>MATAKULIAH DENGAN NILAI <br>DIBAWAH MINIMAL</span></p>
            	</div>
            </a --> 
            <div class="tile-wide bg-lime fg-white" data-role="tile">
            	<form id="updateColRobotNilaiLessThenMin">
            		<div style="margin:5px 0 0 5px;position: absolute;">  
            		<p class="no-margin text-shadow">KASIH NILAI SEMENTARA <span class="text-bold">KOLOM ROBOT UNTUK MATAKULIAH DENGAN NILAI DIBAWAH MINIMAL</span></p>
            		THSMS &nbsp&nbsp: <input type="text" id="thsms" name="thsms" style="width:75px"/><br>
            		<p style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px;height:25px"/></p>
            		</div>
            	</form>	
            </div>
            <div class="tile bg-darkOrange fg-white" data-role="tile">
            	<form id="hitungTrakmKolomRobot">
            		<div style="margin:10px 0 0 3px;position: absolute;">  
            		<section style="font-weight:bold">HITUNG TRAKM KOLOM ROBOT</section><br>
            		THSMS : <input type="text" name="thsms" style="width:75px"/><br>
            		PRODI &nbsp: <input type="text" name="kdpst" style="width:75px"/><br>
            		<p style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px"/></p>
            		</div>
            	</form>
          	</div>
          	<a  href="#" id="hitungSksdi" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">
            	<div class="padding20">
                	<p class="no-margin text-shadow"><span class="text-bold">HITUNG SKSDI SELURUH MAHASISWA BERDASARKAN TOTAL SKS MATAKULIAH P.T. ASAL (ONE TIME)</span></p>
				</div>
			</a>   
   		</div>		
    </div>
    
</div>    
	<%
	}
}			
catch(Exception e) {
%>
<center>
<meta http-equiv="refresh" content="35; url=http:<%=Constants.getRootWeb() %>/InnerFrame/home.jsp">
</center>
<%	
}
	%>

</body>
</html>
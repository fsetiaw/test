<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
	
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>	
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.pengajuan.ua.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/js/accordion/accordion.css" media="screen" />
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/accordion/accordion-center.js"></script>
	<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
  <script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
  
      <link rel='shortcut icon' type='image/x-icon' href='../favicon.ico' />
    <title>Tiles examples :: Start Screen :: Metro UI CSS - The front-end framework for developing projects on the web in Windows Metro Style</title>

    <link href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/css/metro.css" rel="stylesheet">
    <link href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/css/metro-icons.css" rel="stylesheet">
    <!--<link href="../css/metro-responsive.css" rel="stylesheet">-->

    <script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
    <script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/metro.js"></script>
  
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
JSONArray jsoa_role = (JSONArray) request.getAttribute("jsoa_role");
request.removeAttribute("jsoa_role");
Vector v = null;


%>
   <style>
    .button {
  -moz-border-radius: 25px;
  -moz-box-shadow: #6E7849 0px 0px 10px;
  -moz-transition: all 0.5s ease;
  -ms-transition: all 0.5s ease;
  -o-transition: all 0.5s ease;
  -webkit-border-radius: 25px;
  -webkit-box-shadow: #6E7849 0 0 10px;
  -webkit-transition: all 0.5s ease;
  background-color: #369;
  border-radius: 25px;
  border: 2px solid #4a5032;
  box-shadow: #6E7849 0px 0px 10px;
  color: #ffffff;
  display: inline-block;
  font-size: 1em;
  margin: auto;
  padding: 15px;
  text-decoration: none;
  text-shadow: #000000 5px 5px 15px;
  transition: all 0.5s ease;
}
.button:hover {
  padding: 15px;
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
<div id="header">
<%@ include file="../../innerMenu.jsp"%>
</div>

<br />
<div class="tile-area tile-area-scheme-dark fg-white" style="height: 100%; max-height: 100% !important;">
    <%
//SearchDbMainNotification sdmn = new SearchDbMainNotification(validUsr.getNpm());
    String list_color = "bg-darkTeal,bg-darkCobalt,bg-olive,bg-darkOrange";
    StringTokenizer stw = new StringTokenizer(list_color,",");
    String warna = "";

    %>  
	<div class="tile-group triple">
		<span class="tile-group-title">Kirim Pesan Sebagai:</span>
		<div class="tile-container">
		<%
		boolean no_item = true;
		if(jsoa_role!=null && jsoa_role.length()>0) {
			//System.out.println("ADA AKSES UNTUK MENGIRIM PESAN");
			no_item = false;
			try {
				for(int i=0;i<jsoa_role.length();i++) {
					JSONObject job = jsoa_role.getJSONObject(i);
					String grp_id = "";
					String grp_tp = "";
					String grp_nm = "";
					String grp_nc = "";
					String grp_mem_id = "";
					String grp_adm_id = "";
					
					try {
						grp_id = ""+(Integer)job.get("GROUP_ID");
					}
					catch(JSONException e) {}//ignore
					try {
						grp_tp = (String)job.get("TIPE_GROUP");
					}
					catch(JSONException e) {}//ignore
					try {
						grp_nm = (String)job.get("GROUP_NAME");
					}
					catch(JSONException e) {}//ignore
					try {
						grp_nc = (String)job.get("GROUP_NICKNAME");
					}
					catch(JSONException e) {}//ignore
					try {
						grp_mem_id = (String)job.get("LIST_MEMBER_ID");
					}
					catch(JSONException e) {}//ignore
					try {
						grp_adm_id = (String)job.get("LIST_ADMIN_ID");
					}
					catch(JSONException e) {}//ignore
					if(!stw.hasMoreTokens()) {
			  			stw = new StringTokenizer(list_color,",");
			  		}
			  		warna = stw.nextToken();
			  		JSONArray jsoa_id = Getter.readJsonArrayFromUrl("/v1/citcat/get/msg/conversation_id/between/"+v_npmhs+"/"+grp_id);
					if(jsoa_id==null || jsoa_id.length()<1) {
						//System.out.println(i+".  jsoa ko kesisni "+v_npmhs+" "+grp_id);
					%>
					<div class="tile-wide <%=warna %> fg-white" data-role="tile">
				 		<form action="go.groupMsgToIndividu">
		            		<div class="tile-content image-set">
		           				<input type="hidden" name="sender_npm"  value="<%=validUsr.getNpm()%>"/>
		           				<input type="hidden" name="sender_grp_id"  value="<%=grp_id%>"/>
		           				<input type="hidden" name="target_npm"  value="<%=v_npmhs%>"/>
		           				<input type="hidden" name="target_nmm"  value="<%=v_nmmhs%>"/>
		           				<input type="hidden" name="target_idobj"  value="<%=v_id_obj%>"/>
		           				<input type="hidden" name="target_kdpst"  value="<%=v_kdpst%>"/>
		           				<input type="hidden" name="target_objlv"  value="<%=v_obj_lvl%>"/>
		            		</div>
		            		<div style="margin:15px 0 0 10px;position: absolute;">   
		                   		<p class="no-margin text-shadow" style="color:white"><span class="text-bold"><%=grp_nm %></span></p>
		            		</div>       	
		            		<div style="margin:45px 0 0 10px;position: absolute;">
		                   		<textarea name="isi_pesan" rows="6" style="width:225px;color:black" placeholder="isi pesan"></textarea>
		            		</div>   
		            		<div style="margin:42px 0 0 240px;position: absolute;">    	
		                 		<input class="button" type="submit" value="send" style="height:90px;background:grey;color:white;text-weight:bold"/>	
		            		</div>
		            	</form>	
		       		</div> 
		       		<%
		       		}
		       		else {
		       			
		       			int grp_conv_id = 0;
		       			try {
		       				JSONObject job_id = jsoa_id.getJSONObject(0);
		       				grp_conv_id = (Integer)job_id.get("ID");
		       			}
		       			catch(Exception e){
		       				e.printStackTrace();
		       			}
		       		//get.infoMsgDashboard
		       		//System.out.println("grp_conv_id="+grp_conv_id+" "+v_npmhs);
		       		%>
		       		<!--  a href="<%=Constants.getRootWeb() %>/InnerFrame/Pesan/conversation_main_frame.jsp?str_range=<%=Constant.rangeMsgPerPage() %>&grp_nm=<%=grp_nm %>&grp_id=<%=grp_conv_id %>" target="inner_iframe" class="tile <%=warna %> fg-white" data-role="tile" -->
		       		
		       		<a href="<%=Constants.getRootWeb() %>/InnerFrame/Pesan/FormSendMsg/frame_at_menu_mhs.jsp?str_range=<%=Constant.rangeMsgPerPage() %>&grp_nm=<%=grp_nm %>&grp_id=<%=grp_conv_id %>&atMenu=msg&cmd=msg&id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>" target="inner_iframe" class="tile <%=warna %> fg-white" data-role="tile">
            			<div class="padding20">
                			<p class="no-margin text-shadow"><span class="text-bold"><%=grp_nm %></span><br/></p>
						</div>
					</a>      			
			                	<%	
		       		}        	
			                	
				}
			}
			catch(Exception e) {}	
		}
		else {
			//System.out.print("TIDAK ADA AKSES UNTUK MENGIRIM PESAN");
		}
	  	//======kehadiran dosen==================
	  	//link untuk mahasiswa
	  
		//System.out.println("info_dosen_arrival="+info_dosen_arrival);  			     

%>
     	</div>
  	</div>     
    <%
    if(false) {
    	
    	String thsms_reg = Checker.getThsmsHeregistrasi();
    %>
			<div class="tile-group quadro">
   				<span class="tile-group-title">Overview</span>
        		<div class="tile-container">
                
            		<a class="tile bg-darkOrange fg-white" data-role="tile" href="get.sebaranTrlsm?target_thsms=<%=thsms_reg %>" target="inner_iframe">    
        				<div style="margin:10px 0 0 22px;position: absolute;">   
               				<p class="no-margin text-shadow">RIWAYAT <span class="text-bold" id="pressure">PESAN</span></p>
						</div>
						<div style="margin:35px 0 0 13px;position: absolute;">   
               				<p class="no-margin text-shadow">&nbsp<span class="text-bold" id="pressure">   
                			</p>
						</div>
						<div style="margin:52px 0 0 23px;position: absolute;">   
               				<p class="no-margin text-shadow">&nbsp<span class="text-bold" id="pressure"> &nbsp  
                			</p>
						</div>

				
          			</a>
          
 
    			</div>
    		</div>	
    	</div>	
	<%
	}
	%>
</div>
</body>
</html>
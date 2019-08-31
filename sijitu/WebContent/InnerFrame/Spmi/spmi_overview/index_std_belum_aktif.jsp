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
Vector v_std_aktif=(Vector)session.getAttribute("v_std_aktif");
Vector v_std_blm_aktif=(Vector)session.getAttribute("v_std_blm_aktif");

SearchStandarMutu stm = new SearchStandarMutu(validUsr.getNpm());
Vector v_list_main_std = stm.getListInfoStandar();
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

</head>
<body style="overflow-y: hidden;">
	<div style="padding:0 0 0 55px">
    		<a href="#" id="prepDashOverviewSpmi" target="inner_iframe" class="tile-small bg-white" data-role="tile">
            	<div class="tile-content iconic">
                    <img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/go_back.png" class="icon" >
                </div>
            	<div style="margin:20px 0 0 55px;">   
                   	<p class="no-margin text-shadow" style="font-weight:bold;font-size:1.em;color:black">GO BACK</p>
                </div>
			</a>	
	</div>	
	<div style="text-align:center;padding:0 0 0 55px">
		<span class="tile-group-title">
			<select style="width:100%;height:25px;border:none;text-align-last:center;border-radius: 10px;" disabled>
				<option value="null" selected="selected"><%=keter_prodi%></option> 
			</select>
		</span>
	</div>

<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai</p>
</div>
<div id="main">
<%
boolean ada_kegiatan = false;
//Vector v_distinct_std = null;
if(v_std_blm_aktif!=null) {
	//ListIterator lis = v_distinct_std.listIterator();
	ListIterator li = v_std_blm_aktif.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		st =  new StringTokenizer(brs,"`");
		//System.out.println("counter="+st.countTokens());
		//System.out.println("bar ="+brs);
		
		String id_versi = st.nextToken();
		String id_std_isi = st.nextToken();
		String id_std = st.nextToken();
		String isi_std = st.nextToken();
		String butir = st.nextToken();
		String kdpst_val = st.nextToken();
		String rasionale = st.nextToken();
		String tgl_sta = st.nextToken();
		String tgl_end = st.nextToken();
		String target1 = st.nextToken();
		String target2 = st.nextToken();
		String target3 = st.nextToken();
		String target4 = st.nextToken();
		String target5 = st.nextToken();
		String target6 = st.nextToken();
		String pihak = st.nextToken();
		String dokumen = st.nextToken();
		String indikator = st.nextToken();
		String periode_start = st.nextToken();
		String unit_used_by_periode_start = st.nextToken();
		String besaran_interval_per_period = st.nextToken();
		String unit_used_byTarget = st.nextToken();
		String pengawas = st.nextToken();
		String param = st.nextToken();
		String aktif = st.nextToken();
		String cakupan = st.nextToken();
		String tgl_mulai_aktif = st.nextToken();
		String tgl_stop_aktif = st.nextToken();
		String tipe_survey = st.nextToken();
		String id_master = st.nextToken();
		String id_tipe = st.nextToken();
		//lis.add(id_master+"`"+id_tipe);
	}
	//try {
	//	v_distinct_std = Tool.removeDuplicateFromVector(v_distinct_std);
	//}
	//catch(Exception e){}
}

%>
	<div class="tile-area tile-area-scheme-dark fg-white" style="height: 100%; max-height: 100% !important;">
    
        
      		<%
if(v_list_main_std!=null && v_list_main_std.size()>0) {
	Vector v_sdh_tampil = new Vector();
	Vector v_master_kategori_sdh_tampil = new Vector();
	ListIterator li_sdh_tampil = v_sdh_tampil.listIterator();
	ListIterator li_master_tampil = v_master_kategori_sdh_tampil.listIterator();
	ListIterator li = v_list_main_std.listIterator();
	if(li.hasNext()) {
		String brs = (String)li.next();
		//System.out.println("1st kategor = "+brs);
		st = new StringTokenizer(brs,"~");
		String prev_id_master = st.nextToken();
		String prev_ket_master_std = st.nextToken();
		String prev_id_std = st.nextToken();
		String prev_id_tipe_std = st.nextToken();
		String prev_ket_tipe_std = st.nextToken();
		//System.out.println("prev_ket_master_std="+prev_ket_master_std);
		/*
		FIRST MASTER KATEGORI
		*/
		boolean cocok = false;
		boolean id_master_match = false;
		boolean tipe_id_match = false;
		if(v_std_blm_aktif!=null) {
			ListIterator lit = v_std_blm_aktif.listIterator();
			while(lit.hasNext() && cocok) {
				String brs1 = (String)lit.next();
				StringTokenizer st1 =  new StringTokenizer(brs1,"`");
				//System.out.println("counter="+st.countTokens());
				//System.out.println("bar ="+brs1);
				
				String id_versi = st1.nextToken();
				String id_std_isi = st1.nextToken();
				String id_std = st1.nextToken();
				String isi_std = st1.nextToken();
				String butir = st1.nextToken();
				String kdpst_val = st1.nextToken();
				String rasionale = st1.nextToken();
				String tgl_sta = st1.nextToken();
				String tgl_end = st1.nextToken();
				String target1 = st1.nextToken();
				String target2 = st1.nextToken();
				String target3 = st1.nextToken();
				String target4 = st1.nextToken();
				String target5 = st1.nextToken();
				String target6 = st1.nextToken();
				String pihak = st1.nextToken();
				String dokumen = st1.nextToken();
				String indikator = st1.nextToken();
				String periode_start = st1.nextToken();
				String unit_used_by_periode_start = st1.nextToken();
				String besaran_interval_per_period = st1.nextToken();
				String unit_used_byTarget = st1.nextToken();
				String pengawas = st1.nextToken();
				String param = st1.nextToken();
				String aktif = st1.nextToken();
				String cakupan = st1.nextToken();
				String tgl_mulai_aktif = st1.nextToken();
				String tgl_stop_aktif = st1.nextToken();
				String tipe_survey = st1.nextToken();
				String id_master = st1.nextToken();
				String id_tipe = st1.nextToken();
				if(prev_id_master.equalsIgnoreCase(id_master)) {
					id_master_match = true;
					li_master_tampil.add(prev_id_master);	
				}
				if(prev_id_master.equalsIgnoreCase(id_master) && prev_id_tipe_std.equalsIgnoreCase(id_tipe)) {
					tipe_id_match = true;
				}
				if(id_master_match && tipe_id_match) {
					li_sdh_tampil.add(prev_id_master+"`"+prev_id_tipe_std);
					cocok=true;
				}
			}
			if(cocok) { //first iter mesti kalo cocok pertama maka match master & tipe
				//System.out.println("first iter match");
				%>
		<div class="tile-group two">
			<span class="tile-group-title"><%=prev_ket_master_std %></span>
			<div class="tile-container">
				<a href="go.getListAllStd?mode=std_non_aktif&id_tipe_std=<%=prev_id_tipe_std %>&id_master_std=<%=prev_id_master %>&atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=<%="" %>&max_data_per_pg=<%="" %>" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">
	            	<div class="padding20">
	               	<p class="no-margin text-shadow"><span class="text-bold"><%=prev_ket_tipe_std %>[<%=prev_id_master %>][<%=prev_id_tipe_std %>]</p></span>
					</div>
	            	<div style="margin:5px 0 0 10px;position: absolute;font-weight:bold">  
	            	</div>
				</a>
					<%		
			}
			else {
				//System.out.println("first iter not match");
			}
		}
		//System.out.println("1st = "+cocok);
		while(li.hasNext()) {
			brs = (String)li.next();
			//System.out.println(brs);
			//System.out.println("prev_ket_master_std="+prev_ket_master_std);
			st = new StringTokenizer(brs,"~");
			String id_master = st.nextToken();
			String ket_master_std = st.nextToken();
			String id_std = st.nextToken();
			String id_tipe_std = st.nextToken();
			String ket_tipe_std = st.nextToken();
			//System.out.println("ket_master_std="+ket_master_std);
			if(prev_ket_master_std.equalsIgnoreCase(ket_master_std)) {
				/*
				MASTER KATEGORI MASIH SAMA
				*/
				
				//System.out.println("same-master");
				cocok = false;
				id_master_match=false; 
				tipe_id_match=false;
				if(v_std_blm_aktif!=null) {
					//System.out.println("kesini");
					ListIterator lit = v_std_blm_aktif.listIterator();
					while(lit.hasNext() && !cocok) {
						//System.out.println("cocok gila="+cocok);
						String brs1 = (String)lit.next();
						StringTokenizer st1 =  new StringTokenizer(brs1,"`");
						//System.out.println("counter="+st.countTokens());
						//System.out.println("bar ="+brs1);
						
						String id_versi = st1.nextToken();
						String id_std_isi = st1.nextToken();
						String id_std_new = st1.nextToken();
						String isi_std = st1.nextToken();
						String butir = st1.nextToken();
						String kdpst_val = st1.nextToken();
						String rasionale = st1.nextToken();
						String tgl_sta = st1.nextToken();
						String tgl_end = st1.nextToken();
						String target1 = st1.nextToken();
						String target2 = st1.nextToken();
						String target3 = st1.nextToken();
						String target4 = st1.nextToken();
						String target5 = st1.nextToken();
						String target6 = st1.nextToken();
						String pihak = st1.nextToken();
						String dokumen = st1.nextToken();
						String indikator = st1.nextToken();
						String periode_start = st1.nextToken();
						String unit_used_by_periode_start = st1.nextToken();
						String besaran_interval_per_period = st1.nextToken();
						String unit_used_byTarget = st1.nextToken();
						String pengawas = st1.nextToken();
						String param = st1.nextToken();
						String aktif = st1.nextToken();
						String cakupan = st1.nextToken();
						String tgl_mulai_aktif = st1.nextToken();
						String tgl_stop_aktif = st1.nextToken();
						String tipe_survey = st1.nextToken();
						String id_master_new = st1.nextToken();
						String id_tipe = st1.nextToken();
						if(id_master.equalsIgnoreCase(id_master_new)) {
							id_master_match = true;	
							//System.out.println("id_master0="+id_master);
							//System.out.println("id_master_new0="+id_master_new);
						}
						if(id_master.equalsIgnoreCase(id_master_new) && id_tipe_std.equalsIgnoreCase(id_tipe)) {
							tipe_id_match = true;
							//System.out.println("brs_blm_aktif="+brs1);
							//System.out.println("id_master="+id_master);
							//System.out.println("id_master_new="+id_master_new);
							//System.out.println("id_tipe_std="+id_tipe_std);
							//System.out.println("id_tipe="+id_tipe);
						}
						if(id_master_match && tipe_id_match) {
							//li_sdh_tampil.add(prev_id_master+"`"+prev_id_tipe_std);
							
							cocok=true;
						}
					}
					//System.out.println("cocok-sama="+cocok);
					if(cocok) {
						boolean match_master = false;
						li_master_tampil = v_master_kategori_sdh_tampil.listIterator();
						while(li_master_tampil.hasNext() && !match_master) {
							String id_master_sdh_tampil = (String)li_master_tampil.next();
							if(id_master_sdh_tampil.equalsIgnoreCase(id_master)) {
								match_master = true;
							}
						}
						if(!match_master) {
							li_master_tampil.add(id_master);
							//System.out.println("buka div baru");
							
							if(v_sdh_tampil.size()>0) { //just incase FIRST MASTER KATEGORI tidak match
							//System.out.println("tutp prev div");
		%>

				</div>
		  	</div>   
		<%
						}
		%>					
		<div class="tile-group two">
			<span class="tile-group-title"><%=ket_master_std %></span>
			<div class="tile-container">
								<%
						}
					}
					if(cocok) {
						
						boolean match_tipe = false;
						li_sdh_tampil = v_sdh_tampil.listIterator();
						while(li_sdh_tampil.hasNext() && !match_tipe) {
							String brs_sdh_tampil = (String)li_sdh_tampil.next();
							//System.out.println("brs_sdh_tampil_sama="+brs_sdh_tampil);
							if(brs_sdh_tampil.equalsIgnoreCase(id_master+"`"+id_tipe_std)) {
								match_tipe = true;
							}
						}
						if(!match_tipe) {
							//li_sdh_tampil.add(id_tipe_std);
							//System.out.println("adding_brs_sdh_tampil_sama="+id_master+"`"+id_tipe_std);
							li_sdh_tampil.add(id_master+"`"+id_tipe_std);
							%>			
					<a href="go.getListAllStd?mode=std_non_aktif&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master %>&atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=<%=at_page %>&max_data_per_pg=<%=max_data_per_pg %>" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">
					    <div class="padding20">
					    <p class="no-margin text-shadow"><span class="text-bold"><%=ket_tipe_std %>[<%=id_master %>][<%=id_tipe_std %>]</p></span>
						</div>
					    <div style="margin:5px 0 0 10px;position: absolute;font-weight:bold">  
					    </div>
					</a>
					<%				
						}
					}
					
				}	
				
			}
			else {
				/*
				MASTER KATEGORI BARU
				*/
				//System.out.println("beda-master");
				//tutup div sev\belumnya
				
				/*
				buka DIV BARU
				*/
				cocok = false;
				id_master_match = false;
				tipe_id_match = false;
				if(v_std_blm_aktif!=null) {
					ListIterator lit = v_std_blm_aktif.listIterator();
					while(lit.hasNext() && !cocok) {
						String brs1 = (String)lit.next();
						
						StringTokenizer st1 =  new StringTokenizer(brs1,"`");
						//System.out.println("counter="+st.countTokens());
						//System.out.println("bar ="+brs1);
						
						String id_versi = st1.nextToken();
						String id_std_isi = st1.nextToken();
						String id_std_new = st1.nextToken();
						String isi_std = st1.nextToken();
						String butir = st1.nextToken();
						String kdpst_val = st1.nextToken();
						String rasionale = st1.nextToken();
						String tgl_sta = st1.nextToken();
						String tgl_end = st1.nextToken();
						String target1 = st1.nextToken();
						String target2 = st1.nextToken();
						String target3 = st1.nextToken();
						String target4 = st1.nextToken();
						String target5 = st1.nextToken();
						String target6 = st1.nextToken();
						String pihak = st1.nextToken();
						String dokumen = st1.nextToken();
						String indikator = st1.nextToken();
						String periode_start = st1.nextToken();
						String unit_used_by_periode_start = st1.nextToken();
						String besaran_interval_per_period = st1.nextToken();
						String unit_used_byTarget = st1.nextToken();
						String pengawas = st1.nextToken();
						String param = st1.nextToken();
						String aktif = st1.nextToken();
						String cakupan = st1.nextToken();
						String tgl_mulai_aktif = st1.nextToken();
						String tgl_stop_aktif = st1.nextToken();
						String tipe_survey = st1.nextToken();
						String id_master_new = st1.nextToken();
						String id_tipe = st1.nextToken();
						if(id_master.equalsIgnoreCase(id_master_new)) {
							id_master_match = true;	
						}
						if(id_master.equalsIgnoreCase(id_master_new) && id_tipe_std.equalsIgnoreCase(id_tipe)) {
							tipe_id_match = true;			
						}
						if(id_master_match && tipe_id_match) {
							//li_sdh_tampil.add(id_master+"`"+id_tipe_std);
							//System.out.println("brs_blm_aktif="+brs1);
							//System.out.println("id_master="+id_master);
							//System.out.println("id_master_new="+id_master_new);
							//System.out.println("id_tipe_std="+id_tipe_std);
							//System.out.println("id_tipe="+id_tipe);
							cocok=true;
						}
					}
					//System.out.println("cocok-beda="+cocok);
					if(cocok) {
						//System.out.println("cocok="+id_master+"`"+id_tipe_std);
						if(v_sdh_tampil.size()>0) { //just incase FIRST MASTER KATEGORI tidak match
							//System.out.println("tutp prev div");
		%>

				</div>
		  	</div>   
		<%
						}
					}
					if(cocok) {
						boolean match_master = false;
						li_master_tampil = v_master_kategori_sdh_tampil.listIterator();
						while(li_master_tampil.hasNext() && !match_master) {
							String id_master_sdh_tampil = (String)li_master_tampil.next();
							if(id_master_sdh_tampil.equalsIgnoreCase(id_master)) {
								match_master = true;
							}
						}
						if(!match_master) {
							li_master_tampil.add(id_master);
							//System.out.println("buka div baru");
							%>
			<div class="tile-group two">
				<span class="tile-group-title"><%=ket_master_std %></span>
				<div class="tile-container">
								<%
						}
					}
					if(cocok) {
						
						boolean match_tipe = false;
						li_sdh_tampil = v_sdh_tampil.listIterator();
						while(li_sdh_tampil.hasNext() && !match_tipe) {
							String brs_sdh_tampil = (String)li_sdh_tampil.next();
							//System.out.println("brs_sdh_tampil_beda="+brs_sdh_tampil);
							if(brs_sdh_tampil.equalsIgnoreCase(id_master+"`"+id_tipe_std)) {
								match_tipe = true;
							}
						}
						if(!match_tipe) {
							//li_sdh_tampil.add(id_tipe_std);
							//System.out.println("adding_brs_sdh_tampil_beda="+id_master+"`"+id_tipe_std);
							li_sdh_tampil.add(id_master+"`"+id_tipe_std);
							%>			
					<a href="go.getListAllStd?mode=std_non_aktif&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master %>&atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=<%=at_page %>&max_data_per_pg=<%=max_data_per_pg %>" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">
					    <div class="padding20">
					    <p class="no-margin text-shadow"><span class="text-bold"><%=ket_tipe_std %>[<%=id_master %>][<%=id_tipe_std %>]</p></span>
						</div>
					    <div style="margin:5px 0 0 10px;position: absolute;font-weight:bold">  
					    </div>
					</a>
					<%				
						}
					}
				}
				prev_ket_master_std = new String(ket_master_std);
				//System.out.println("ganti = "+cocok);	
				
			}	
			if(!li.hasNext()&&v_sdh_tampil.size()>0) {
				//System.out.println("END");
				//tutup
%>
			</div>
	  	</div>   
<%				
			}
		}
	}
}
      		%>
     		  

	</div>
</div>
</body>
</html>
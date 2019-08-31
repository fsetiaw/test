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
Vector v_scope_kdpst_list_distinct_npm_trnlm_at_target_thsms=(Vector)session.getAttribute("v_scope_kdpst_list_distinct_npm_trnlm_at_target_thsms");
Vector v_scope_kdpst_list_distinct_npm_cuti_ato_nonaktif_at_target_thsms=(Vector)session.getAttribute("v_scope_kdpst_list_distinct_npm_cuti_ato_nonaktif_at_target_thsms");
Vector v_scope_kdpst_list_distinct_npm_mhs_baru = (Vector)session.getAttribute("v_scope_kdpst_list_distinct_npm_mhs_baru");
Vector v_scope_kdpst_list_distinct_npm_info_daftar_ulang = (Vector)session.getAttribute("v_scope_kdpst_list_distinct_npm_info_daftar_ulang");
Vector v_scope_kdpst_list_distinct_npm_info_daftar_ulang_wip = (Vector)session.getAttribute("v_scope_kdpst_list_distinct_npm_info_daftar_ulang_wip");
Vector v_scope_kdpst_list_distinct_npm_angel_thsms_1 = (Vector)session.getAttribute("v_scope_kdpst_list_distinct_npm_angel_thsms_1");
Vector v_scope_kdpst_list_distinct_npm_info_pindah_prodi_out=(Vector)session.getAttribute("v_scope_kdpst_list_distinct_npm_info_pindah_prodi_out");
Vector v_scope_kdpst_list_distinct_npm_info_pindah_prodi_in=(Vector)session.getAttribute("v_scope_kdpst_list_distinct_npm_info_pindah_prodi_in");
Vector v_scope_kdpst_list_distinct_npm_info_pindah_prodi_wip=(Vector)session.getAttribute("v_scope_kdpst_list_distinct_npm_info_pindah_prodi_wip");
Vector v_scope_kdpst_list_distinct_npm_info_kelulusan=(Vector)session.getAttribute("v_scope_kdpst_list_distinct_npm_info_kelulusan");
Vector v_scope_kdpst_list_distinct_npm_info_kelulusan_wip=(Vector)session.getAttribute("v_scope_kdpst_list_distinct_npm_info_kelulusan_wip");
Vector v_scope_kdpst_list_distinct_npm_lulusan_at_trlsm = (Vector)session.getAttribute("v_scope_kdpst_list_distinct_npm_lulusan_at_trlsm");
Vector v_scope_kdpst_list_distinct_npm_mhs_out_univ = (Vector)session.getAttribute("v_scope_kdpst_list_distinct_npm_mhs_out_univ");
Vector v_v_scope_kdpst_list_distinct_npm_out_univ_at_trlsm = (Vector)session.getAttribute("v_scope_kdpst_list_distinct_npm_out_univ_at_trlsm");
String target_thsms = (String)session.getAttribute("target_thsms");
String show_angel = (String)session.getAttribute("show_angel");

//System.out.println("--masuk");

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
<!--  link rel="publisher" href="https://plus.google.com/117689250782136016574" -->

<!-- remove or comment this line if you want to use the local fonts -->
<!--  link href='http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700' rel='stylesheet' type='text/css' -->
<link rel="shortcut icon" href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/favicon.ico">
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/css/metro.css">
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/css/metro-icons.css">

<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/metro.js"></script>

<style>
    	a {
    		color:white;
    	}
    	a:link {
    		text-decoration: none;
    	}
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
		
		$(document).ready(function() {
			$("#wait").hide();
			$("#main").show();
		});	
</script>
<script>	
	
	
	$(document).ready(function() {
		$(document).on("click", "#getListMhsKeluar", function() {
	    	$.ajax({
	    		url: 'go.getListMhsGivenStmhs',
	    		type: 'POST',
	    		data: {
	    	        thsms: "<%=target_thsms%>",
	    	        stmhs: "k",
	    	    },
	    	    beforeSend:function(){
	    	    	$("#wait").show();
	    	    	$("#main").hide();
	    	        // this is where we append a loading image
	    	    },
	    	    success:function(data){
	    	        // successful request; do something with the data 
	    	    	//window.location.href = "index_stp.jsp";
	    	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=get.sebaranTrlsm&target_thsms=<%=target_thsms %>&at_page=1&max_data_per_pg=20";
	    	    },
	    	    error:function(){
	    	        // failed request; give feedback to user
	    	    }
	    	})
	    	return false;
	    });	
		
		$("#getSebaranTrlsm").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.getSebaranTrlsm',
        		type: 'POST',
        		data: $("#getSebaranTrlsm").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	window.location.href = "indexSebaranTrlsm_v1.jsp";
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
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai<br>[Lama proses bisa sampai 5 menit]</p>
</div>
<div id="main">

	<form id="getSebaranTrlsm">
	<h2 style="color:#369;padding:0 0 0 80px">INFORMASI TAHUN-SEMESTER : 
	<select name="target_thsms">
<%
String thsms_reg = Checker.getThsmsHeregistrasi();

String thsms_starting_point = Constant.getValue("BASED_THSMS");
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
	<h4 style="color:#369;padding:0 0 0 80px">
<%
if(!Checker.isStringNullOrEmpty(show_angel) && show_angel.equalsIgnoreCase("true")) {
%>
	<input type="checkbox" name="show_angel" value="true" checked> Tot Mhs (reserved)
<%	
}
else {
%>
	<input type="checkbox" name="show_angel" value="false"> Tot Mhs (reserved)
<%	
}
%>
	&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type="submit" value="HITUNG ULANG" style="width:155px;height:30px"/>
	
	</h4>
	</form>
	<div class="tile-area tile-area-scheme-dark fg-white" style="height: 100%; max-height: 100% !important;">
	
 
<%

if(v_scope_kdpst_list_distinct_npm_trnlm_at_target_thsms!=null) {
	//size vector dibawah ini harusnya sama semua karena berdasarkan v_scope_kdpst
	ListIterator li = v_scope_kdpst_list_distinct_npm_trnlm_at_target_thsms.listIterator();
	ListIterator li1 = v_scope_kdpst_list_distinct_npm_cuti_ato_nonaktif_at_target_thsms.listIterator();
	ListIterator li_nube = v_scope_kdpst_list_distinct_npm_mhs_baru.listIterator();
	ListIterator li_reg = v_scope_kdpst_list_distinct_npm_info_daftar_ulang.listIterator();
	ListIterator li_reg_wip = v_scope_kdpst_list_distinct_npm_info_daftar_ulang_wip.listIterator();
	ListIterator li_angel = v_scope_kdpst_list_distinct_npm_angel_thsms_1.listIterator();
	ListIterator li_pp_out = v_scope_kdpst_list_distinct_npm_info_pindah_prodi_out.listIterator();
	ListIterator li_pp_in = v_scope_kdpst_list_distinct_npm_info_pindah_prodi_in.listIterator();
	ListIterator li_pp_wip = v_scope_kdpst_list_distinct_npm_info_pindah_prodi_wip.listIterator();
	ListIterator li_lulus = v_scope_kdpst_list_distinct_npm_info_kelulusan.listIterator();
	ListIterator li_lulus_wip = v_scope_kdpst_list_distinct_npm_info_kelulusan_wip.listIterator();
	ListIterator li_lulus_at_trlsm = v_scope_kdpst_list_distinct_npm_lulusan_at_trlsm.listIterator();
	ListIterator li_mhs_out_univ = v_scope_kdpst_list_distinct_npm_mhs_out_univ.listIterator();
	ListIterator li_out_univ_at_trlsm = v_v_scope_kdpst_list_distinct_npm_out_univ_at_trlsm.listIterator();
	while(li.hasNext()) {
		String brs=(String)li.next();
		String brs1=(String)li1.next();
		String nube=(String)li_nube.next();
		String reg=(String)li_reg.next();
		String reg_wip=(String)li_reg_wip.next();
		String angel =(String)li_angel.next();
		String pp_out =(String)li_pp_out.next();
		String pp_in =(String)li_pp_in.next();
		String pp_wip =(String)li_pp_wip.next();
		String lulus =(String)li_lulus.next();
		String lulus_wip =(String)li_lulus_wip.next();
		String lulus_at_trlsm = (String)li_lulus_at_trlsm.next();
		String mhs_out_univ = (String)li_mhs_out_univ.next();
		String mhs_out_univ_trlsm = (String)li_out_univ_at_trlsm.next();
		
		//System.out.println("br="+brs);
		//System.out.println("br="+brs1);
		//System.out.println("nube="+nube);
		//System.out.println("reg="+reg);
		//System.out.println("reg_wip="+reg_wip);
		StringTokenizer st = new StringTokenizer(brs,"`");
		String kdkmp = st.nextToken();
		String nmkmp = Converter.getNamaKampus(kdkmp);
		
		//hitung jumlah mhs krs
		int mhs_krs = 0;
		st = new StringTokenizer(brs,"~");
		if(st.countTokens()>1) {
			st.nextToken();
			st = new StringTokenizer(st.nextToken(),",");
			mhs_krs = st.countTokens()/2;
		}
		//hitung jumlah mhs_cuti_nonaktif
		int mhs_cuti_nonaktif=0;
		st = new StringTokenizer(brs1,"~");
		if(st.countTokens()>1) {
			st.nextToken();
			st = new StringTokenizer(st.nextToken(),",");
			mhs_cuti_nonaktif = st.countTokens()/2;
		}
		//hitung mhs baru & ppindahan
		int mhs_baru=0;
		int mhs_baru_pindahan=0;
		int mhs_baru_laki=0;
		int mhs_baru_perem=0;
		st = new StringTokenizer(nube,"~");
		if(st.countTokens()>1) {
			st.nextToken();
			st = new StringTokenizer(st.nextToken(),",");
			while(st.hasMoreTokens()) {
				String kdpst = st.nextToken();
				String npmhs = st.nextToken();
				String stpid = st.nextToken();
				String kdjek = st.nextToken();
				if(stpid.equalsIgnoreCase("B")) {
					mhs_baru++;
				}
				else {
					mhs_baru_pindahan++;
				}
				if(kdjek.equalsIgnoreCase("L")) {
					mhs_baru_laki++;
				}
				else {
					mhs_baru_perem++;
				}
			}
		}
		
		//hitung jumlah [engajuan daftar ulang]
		int mhs_reg = 0, mhs_reg_wip = 0;
		st = new StringTokenizer(reg,"~");
		//System.out.println("tkn= "+st.countTokens());
		if(st.countTokens()>1) {
			st.nextToken();
			st = new StringTokenizer(st.nextToken(),",");
			mhs_reg = st.countTokens()/4;
		}
		//hitung jumlah [engajuan daftar ulang wip
		st = new StringTokenizer(reg_wip,"~");
		if(st.countTokens()>1) {
			st.nextToken();
			st = new StringTokenizer(st.nextToken(),",");
			mhs_reg_wip = st.countTokens()/4;
		}
		
		int tot_angel=0;
		st = new StringTokenizer(angel,"~");
		//System.out.println("tkn= "+st.countTokens());
		if(st.countTokens()>1) {
			st.nextToken();
			st = new StringTokenizer(st.nextToken(),",");
			tot_angel = st.countTokens()/4;
		}
		
		int tot_pp_out=0;
		st = new StringTokenizer(pp_out,"~");
		//System.out.println("tkn= "+st.countTokens());
		if(st.countTokens()>1) {
			st.nextToken();
			st = new StringTokenizer(st.nextToken(),",");
			tot_pp_out = st.countTokens()/6;
		}
		
		int tot_pp_in=0;
		st = new StringTokenizer(pp_in,"~");
		//System.out.println("tkn= "+st.countTokens());
		if(st.countTokens()>1) {
			st.nextToken();
			st = new StringTokenizer(st.nextToken(),",");
			tot_pp_in = st.countTokens()/6;
		}
		int tot_pp_wip=0;
		st = new StringTokenizer(pp_wip,"~");
		//System.out.println("tkn= "+st.countTokens());
		if(st.countTokens()>1) {
			st.nextToken();
			st = new StringTokenizer(st.nextToken(),",");
			tot_pp_wip = st.countTokens()/6;
		}
		
		//hitung lulus
		int mhs_lls = 0, mhs_lls_wip = 0, mhs_lls_trlsm=0;
		st = new StringTokenizer(lulus,"~");
		if(st.countTokens()>1) {
			st.nextToken();
			st = new StringTokenizer(st.nextToken(),",");
			mhs_lls = st.countTokens()/5;
		}
		st = new StringTokenizer(lulus_wip,"~");
		if(st.countTokens()>1) {
			st.nextToken();
			st = new StringTokenizer(st.nextToken(),",");
			mhs_lls_wip = st.countTokens()/5;
		}
		st = new StringTokenizer(lulus_at_trlsm,"~");
		if(st.countTokens()>1) {
			st.nextToken();
			st = new StringTokenizer(st.nextToken(),",");
			mhs_lls_trlsm = st.countTokens()/5;
		}
		
		//hitung pengajuan out univ 
		int tot_out_req=0, tot_do_req=0, tot_out_req_wip=0, tot_out_trlsm=0;
		st = new StringTokenizer(mhs_out_univ,"~");
		if(st.countTokens()>1) {
			st.nextToken();
			st = new StringTokenizer(st.nextToken(),",");
			tot_out_req = st.countTokens()/7; // == total request (keluar + do)
			//hitung pengajuan do
			while(st.hasMoreTokens()) {
				//kdpst+","+npmhs+","+nmmhs+","+smawl+","+locked+","+tipe_out+","+angel
				st.nextToken();//kdpst
				st.nextToken();//npmhs
				st.nextToken();//nmmhs
				st.nextToken();//smawl
				String locked = st.nextToken();
				//System.out.println("locked = "+locked);
				String tipe_out = st.nextToken();
				//System.out.println("tipe_out = "+tipe_out);
				if(tipe_out.equalsIgnoreCase("DO")||tipe_out.equalsIgnoreCase("D")) {
					tot_do_req++;
				}
				if(locked.equalsIgnoreCase("false")||locked.equalsIgnoreCase("0")) {
					tot_out_req_wip++;
				}
				st.nextToken();
			}
		} 
		//hitung mhs out trlsm
		st = new StringTokenizer(mhs_out_univ_trlsm,"~");
		if(st.countTokens()>1) {
			st.nextToken();
			st = new StringTokenizer(st.nextToken(),",");
			tot_out_trlsm = st.countTokens()/5;
		}
		 
%>  
        
		<div class="tile-group triple">
			<span class="tile-group-title"><%=nmkmp %></span>
			<div class="tile-container">
		<%
		if(mhs_baru+mhs_baru_pindahan<1) {
			%>		
				<div class="tile bg-darkOrange fg-white" data-role="tile">
		<%	
		}
		else {
		%>		
				<a href="sebaran_mhs_baru.jsp?kdkmp=<%=kdkmp %>&target_thsms=<%=target_thsms %>" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">
		<%
		}
		%>		
        			<div style="margin:10px 0 0 10px;position: absolute;">   
        	       		<p class="no-margin text-shadow" style="text-align:center;font-weight:bold;"><span class="text-bold" id="pressure">MAHASISWA BARU</span></p>
					</div>
					<div style="margin:30px 0 0 10px;position: absolute;">   
        	       		<p class="no-margin text-shadow">&nbsp<span class="text-bold" id="pressure"></span></p>
					</div>
					<div style="margin:40px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">BARU </span></p>
					</div>
					<div style="margin:40px 0 0 105px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">:&nbsp<%= mhs_baru%></span></p>
					</div>
					<div style="margin:55px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">PINDAHAN </span></p>
					</div>
					<div style="margin:55px 0 0 105px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">:&nbsp<%= mhs_baru_pindahan%></span></p>
					</div>
					<div style="margin:70px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">PRIA</span></p>
					</div>
					<div style="margin:70px 0 0 65px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">[<%= mhs_baru_laki%>]</span></p>
					</div>
					<div style="margin:85px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">WANITA</span></p>
					</div>
					<div style="margin:85px 0 0 65px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">[<%= mhs_baru_perem%>]</span></p>
					</div>
					<div style="margin:120px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">----------------------------</span></p>
					</div>
					<div style="margin:130px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">TOTAL </span></p>
					</div>
					<div style="margin:130px 0 0 105px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">:&nbsp<%= mhs_baru+mhs_baru_pindahan%></span></p>
					</div>
		<%
		if(mhs_baru+mhs_baru_pindahan<1) {
		%>
				</div>
		<%	
		}
		else {
		%>
				</a>
		<%	
		}
		//=============================================end=================================================================
		
		if(mhs_krs+mhs_cuti_nonaktif<1) {
			%>
				<div class="tile bg-darkTeal fg-white" data-role="tile">
			<%	
		}
		else {
			%>
				<a href="sebaran_mhs_aktif.jsp?kdkmp=<%=kdkmp %>&target_thsms=<%=target_thsms %>" target="inner_iframe" class="tile bg-darkTeal fg-white" data-role="tile">
			<%	
		}
		%>	
		
				
        			<div style="margin:10px 0 0 10px;position: absolute;">   
        	       		<p class="no-margin text-shadow" style="text-align:center;font-weight:bold;"><span class="text-bold" id="pressure">MAHASISWA AKTIF</span></p>
					</div>
					<div style="margin:25px 0 0 2px;position: absolute;">   
        	       		<p class="no-margin text-shadow" style="text-align:center;font-weight:bold;"><span class="text-bold" id="pressure" style="font-size:0.8em">[AKTIF + NON-AKTIF + CUTI]</span></p>
					</div>
					<div style="margin:50px 0 0 10px;position: absolute;">   
        	       		<p class="no-margin text-shadow">&nbsp<span class="text-bold" id="pressure"></span></p>
					</div>
					<div style="margin:60px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">TOTAL KRS </span></p>
					</div>
					<div style="margin:60px 0 0 105px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">:&nbsp<%= mhs_krs%></span></p>
					</div>
					<div style="margin:75px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">CUTI &</span></p>
					</div>
					<div style="margin:90px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">NON AKTIF </span></p>
					</div>
					<div style="margin:90px 0 0 105px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">:&nbsp<%= mhs_cuti_nonaktif%></span></p>
					</div>
					<div style="margin:120px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">----------------------------</span></p>
					</div>
					<div style="margin:130px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">TOTAL  </span></p>
					</div>
					<div style="margin:130px 0 0 105px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">:&nbsp<%= mhs_krs+mhs_cuti_nonaktif%></span></p>
					</div>
	    <%
		if(mhs_krs+mhs_cuti_nonaktif<1) {
			%>
				</div>
			<%	
		}
		else {
			%>
				</a>
		<%	
		}
		//=============================================end=================================================================
	    
		if(mhs_reg_wip+mhs_reg<1) {
			%>		
				<div class="tile bg-darkOrange fg-white" data-role="tile">
		<%	
		}
		else {
		%>		
				<a href="sebaran_mhs_hereg.jsp?kdkmp=<%=kdkmp %>&target_thsms=<%=target_thsms %>" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">
		<%
		}
		%>	        			
					<div style="margin:10px 0 0 0;position: absolute;">   
        	       		<p class="no-margin text-shadow" style="text-align:center;font-weight:bold;"><span class="text-bold" id="pressure">MAHASISWA HEREGISTRASI</span></p>
					</div>
					<div style="margin:30px 0 0 10px;position: absolute;">   
        	       		<p class="no-margin text-shadow">&nbsp<span class="text-bold" id="pressure"></span></p>
					</div>
					<div style="margin:55px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">TOTAL</span></p>
					</div>
					<div style="margin:70px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">PENGAJUAN </span></p>
					</div>
					<%
		if(show_angel!=null && show_angel.equalsIgnoreCase("true")) {
			%>
					<div style="margin:70px 0 0 105px;position: absolute;">   
           				<p class="no-margin text-shadow"><span class="text-bold" id="pressure">:&nbsp<%= mhs_reg+tot_angel%></span></p>
					</div>
			<%	
		}
		else {			
					%>
					<div style="margin:70px 0 0 105px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">:&nbsp<%= mhs_reg%></span></p>
					</div>
		<%
		}
		%>			
					<div style="margin:95px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">PROSES PERSETUJUAN [<%= mhs_reg_wip%>]</span></p>
					</div>
					
					<div style="margin:120px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">----------------------------</span></p>
					</div>
					<div style="margin:130px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">TOTAL </span></p>
					</div>
		<%
		if(show_angel!=null && show_angel.equalsIgnoreCase("true")) {
			%>			
					<div style="margin:130px 0 0 105px;position: absolute;">   
    	   				<p class="no-margin text-shadow"><span class="text-bold" id="pressure">:&nbsp<%= mhs_reg+tot_angel%></span></p>
					</div>
      	<%	
		}
		else {
			%>			
					<div style="margin:130px 0 0 105px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">:&nbsp<%= mhs_reg%></span></p>
					</div>
	          	<%
		}      	
	    if(mhs_reg_wip+mhs_reg<1) {
	    %>
				</div>
		<%	
		}
		else {
		%>
				</a>
		<%	
		}   
	    //=============================================end=================================================================
		//=============================================end=================================================================
		
		if(tot_pp_out+tot_pp_in+tot_pp_wip<1) {
			%>
				<div class="tile bg-darkTeal fg-white" data-role="tile">
			<%	
		}
		else {
			%>
				<a href="sebaran_mhs_pindah_prodi.jsp?kdkmp=<%=kdkmp %>&target_thsms=<%=target_thsms %>" target="inner_iframe" class="tile bg-darkTeal fg-white" data-role="tile">
			<%	
		}
		%>	
        			<div style="margin:10px 0 0 0;position: absolute;">   
        	       		<p class="no-margin text-shadow" style="text-align:center;font-weight:bold;"><span class="text-bold" id="pressure">MAHASISWA PINDAH PRODI</span></p>
					</div>
					<div style="margin:30px 0 0 10px;position: absolute;">   
        	       		<p class="no-margin text-shadow">&nbsp<span class="text-bold" id="pressure"></span></p>
					</div>
					<div style="margin:55px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">TOTAL PENGAJUAN</span></p>
					</div>
					<div style="margin:70px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">KELUAR</span></p>
					</div>
					<div style="margin:70px 0 0 105px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">:&nbsp<%= tot_pp_out%></span></p>
					</div>
					<div style="margin:85px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">MASUK</span></p>
					</div>
					<div style="margin:85px 0 0 105px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">:&nbsp<%= tot_pp_in%></span></p>
					</div>
					<div style="margin:100px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">PROSES PERSETUJUAN [<%= tot_pp_wip%>]</span></p>
					</div>
					<div style="margin:120px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure"></span></p>
					</div>
					<div style="margin:130px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">  </span></p>
					</div>
					<div style="margin:130px 0 0 105px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure"></span></p>
					</div>
	    <%
		if(tot_pp_out+tot_pp_in+tot_pp_wip<1) {
			%>
				</div>
			<%	
		}
		else {
			%>
				</a>
		<%	
		}
		//=============================================end=================================================================
		
				
		//=============================================kelulusa=================================================================
		if(mhs_lls+mhs_lls_wip<1) {
			%>		
				<div class="tile bg-darkOrange fg-white" data-role="tile">
		<%	
		}
		else {
		%>		
				<a href="sebaran_mhs_lulus.jsp?mhs_lls_trlsm=<%=mhs_lls_trlsm %>&mhs_lls_wip=<%=mhs_lls_wip %>&mhs_lls=<%=mhs_lls %>&kdkmp=<%=kdkmp %>&target_thsms=<%=target_thsms %>" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">
		<%
		}
		%>		
        			<div style="margin:10px 0 0 10px;position: absolute;">   
        	       		<p class="no-margin text-shadow" style="text-align:center;font-weight:bold;"><span class="text-bold" id="pressure">MAHASISWA LULUS</span></p>
					</div>
					<div style="margin:30px 0 0 10px;position: absolute;">   
        	       		<p class="no-margin text-shadow">&nbsp<span class="text-bold" id="pressure"></span></p>
					</div>
					<div style="margin:40px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">TOTAL </span></p>
					</div>
					<div style="margin:55px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">PENGAJUAN </span></p>
					</div>
					<!-- TOTAL PENGAJUAN = TOTAL AT TRLSM -->
					<div style="margin:55px 0 0 105px;position: absolute;">   
    	   				<p class="no-margin text-shadow"><span class="text-bold" id="pressure">:&nbsp<%= mhs_lls_trlsm%></span></p>
					</div>		
					<div style="margin:70px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure"></span></p>
					</div>
					<div style="margin:70px 0 0 65px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure"></span></p>
					</div>
					<div style="margin:85px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">PROSES PERSETUJUAN [<%= mhs_lls_wip%>]</span></p></span></p>
					</div>
					<div style="margin:120px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">----------------------------</span></p>
					</div>
					<div style="margin:130px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">TOTAL </span></p>
					</div>
					<div style="margin:130px 0 0 105px;position: absolute;">   
				   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">:&nbsp<%= mhs_lls_trlsm%></span></p>
					</div>
			<%	
		if(mhs_lls+mhs_lls_wip<1) {
		%>
				</div>
		<%	
		}
		else {
		%>
				</a>
		<%	
		}
		//=============================================end=================================================================

		//=============================================end=================================================================
		
		if(tot_out_req<1 && tot_out_trlsm+tot_out_req_wip<1) {
			%>
				<div class="tile bg-darkTeal fg-white" data-role="tile">
			<%	
		}
		else {
			%>
				<a href="sebaran_mhs_out_univ.jsp?tot_out_req=<%=tot_out_req %>&tot_out_trlsm=<%=tot_out_trlsm %>&tot_out_req_wip=<%=tot_out_req_wip %>&tot_do_req=<%=tot_do_req %>&kdkmp=<%=kdkmp %>&target_thsms=<%=target_thsms %>" target="inner_iframe" class="tile bg-darkTeal fg-white" data-role="tile">
			<%	
		}
		%>	
		
				
        			<div style="margin:10px 0 0 3px;position: absolute;">   
        	       		<p class="no-margin text-shadow" style="text-align:center;font-weight:bold;"><span class="text-bold" id="pressure">MAHASISWA KELUAR & D.O.</span></p>
					</div>
					<div style="margin:25px 0 0 2px;position: absolute;">   
        	       		<p class="no-margin text-shadow" style="text-align:center;font-weight:bold;"><span class="text-bold" id="pressure" style="font-size:0.8em"></span></p>
					</div>
					<div style="margin:50px 0 0 10px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">TOTAL PENGAJUAN </span></p>
					</div>
					<div style="margin:50px 0 0 105px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure"></span></p>
					</div>
					<div style="margin:65px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">D.O.</span></p>
					</div>
					<div style="margin:65px 0 0 105px;position: absolute;">   
	               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">:&nbsp<%= (tot_do_req)%></span></p>
					</div>
					<div style="margin:80px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">KELUAR </span></p>
					</div>
		<%
		//if(show_angel!=null && show_angel.equalsIgnoreCase("true")) {
		//untuk TOTAL REQ : mo angel apa ngga, sudah di filter di getListNpmhsPengajuanKeluarDo(String target_thsms, Vector v_scope_id, boolean show_angel)
		%>				
					<div style="margin:80px 0 0 105px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">:&nbsp<%= (tot_out_req-tot_do_req)%></span></p>
					</div>	
					<div style="margin:95px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">PROSES PERSETUJUAN [<%= tot_out_req_wip%>]</span></p></span></p>
					</div>
					<div style="margin:120px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">----------------------------</span></p>
					</div>
					<div style="margin:130px 0 0 10px;position: absolute;">   
            	   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">TOTAL  </span></p>
					</div>
		<%
		//if(show_angel!=null && show_angel.equalsIgnoreCase("true")) {
		//untuk TOTAL REQ : mo angel apa ngga, sudah di filter di getListNpmhsPengajuanKeluarDo(String target_thsms, Vector v_scope_id, boolean show_angel)	
			%>			
					<div style="margin:130px 0 0 105px;position: absolute;">   
    			   		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">:&nbsp<%= tot_out_req%></span></p>
					</div>

	    <%
		
		if(tot_out_req<1 && tot_out_trlsm+tot_out_req_wip<1) {
			%>
				</div>
			<%	
		}
		else {
			%>
				</a>
		<%	
		}
		//=============================================end=================================================================

				
		%>	   	
     		</div>
  		</div>  
  <%
	}//end while
%>		  
  	</div>     
<%
}
%>	


</div>
<%

%>
</div>    
</body>
</html>

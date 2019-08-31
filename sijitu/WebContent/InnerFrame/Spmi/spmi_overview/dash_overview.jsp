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
	<%@ page import="beans.dbase.spmi.overview.*" %>
<%	
ListIterator li = null;
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
int tot_scope_prodi=0;
if(v_scope_kdpst_spmi!=null) {
	tot_scope_prodi = v_scope_kdpst_spmi.size();
}
Boolean spmi_editor = (Boolean) session.getAttribute("spmi_editor");

Vector v_status_aktif_std=(Vector)session.getAttribute("v_status_aktif_std");
Vector v_status_aktif_man=(Vector)session.getAttribute("v_status_aktif_man");
Vector v_pelanggaran_isi_std=(Vector)session.getAttribute("v_pelanggaran_isi_std");
Vector v_status_doc_mutu=(Vector)session.getAttribute("v_status_doc_mutu");
Vector v_prodi_never_ami=(Vector)session.getAttribute("v_prodi_never_ami");
Vector v_riwayat_ami_prodi=(Vector)session.getAttribute("v_riwayat_ami_prodi");

//session.removeAttribute("v_isi_std_never_monitored");
request.removeAttribute("v_list_survey_no_evaluasi_oleh_saya");
request.removeAttribute("v_list_upcoming_survey");
request.removeAttribute("v_list_unexecute_survey");
request.removeAttribute("v_prodi_never_ami");
request.removeAttribute("v_riwayat_ami_prodi");
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
	<div style="text-align:center;padding:0 0 0 55px">
		<span class="tile-group-title">
			<select style="width:100%;height:25px;border:none;text-align-last:center;border-radius: 10px;" disabled>
				<option value="null" selected="selected">SELURUH CAKUPAN PRODI</option> 
			</select>
		</span>
	</div>

<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai</p>
</div>
<div id="main">
<%
boolean ada_notifikasi = false;
int tot_notifikasi=0;
Vector v_kegiatan_ppepp_yg_lewat=null;
Vector v_kegiatan_ami_yg_lewat=(Vector)request.getAttribute("v_kegiatan_ami_yg_lewat");
Vector v_kegiatan_monitoring_no_pengendalian = (Vector)request.getAttribute("v_kegiatan_monitoring_no_pengendalian");
Vector v_kegiatan_monitoring_no_kelanjutan = (Vector)request.getAttribute("v_kegiatan_monitoring_no_kelanjutan");
Vector v_isi_std_never_monitored = (Vector)session.getAttribute("v_isi_std_never_monitored");

if(spmi_editor) {
	v_kegiatan_ppepp_yg_lewat=(Vector)request.getAttribute("v_kegiatan_ppepp_yg_lewat");
	if(v_kegiatan_ppepp_yg_lewat!=null && v_kegiatan_ppepp_yg_lewat.size()>0) {
		ada_notifikasi=true;
		tot_notifikasi++;
	}
}
if(v_kegiatan_ami_yg_lewat!=null && v_kegiatan_ami_yg_lewat.size()>0) {
	ada_notifikasi=true;
	tot_notifikasi++;
}
if(v_kegiatan_monitoring_no_pengendalian!=null && v_kegiatan_monitoring_no_pengendalian.size()>0) {
	ada_notifikasi=true;
	tot_notifikasi++;
}
if(v_kegiatan_monitoring_no_kelanjutan!=null && v_kegiatan_monitoring_no_kelanjutan.size()>0) {
	ada_notifikasi=true;
	tot_notifikasi++;
}
if(v_isi_std_never_monitored!=null && v_isi_std_never_monitored.size()>0) {
	ada_notifikasi=true;
	tot_notifikasi++;
}
%>
	<div class="tile-area tile-area-scheme-dark fg-white" style="height: 100%; max-height: 100% !important;">
<%
//if(false) {
if(ada_notifikasi) {
	if(tot_notifikasi>3) {
%>    
        <div class="tile-group three">
<%		
	}
	else {
%>    
        <div class="tile-group double">
<%
	}
%>        
			<span class="tile-group-title">NOTIFIKASI / WARNING</span>
			<div class="tile-container">
<%	
	if(v_isi_std_never_monitored!=null && v_isi_std_never_monitored.size()>0) {
		ListIterator lit = v_isi_std_never_monitored.listIterator();
		int tot = 0;
		while(lit.hasNext()) {
			String this_kdpst = (String)lit.next();
			ListIterator lis = v_scope_kdpst_spmi.listIterator();
			boolean masuk_scope=false;
			while(lis.hasNext()) {
				String brs=(String)lis.next();
				if(brs.contains("`"+this_kdpst+"`")) {
					masuk_scope = true;
				}
			}
			Vector v_tmp = (Vector)lit.next();
			if(masuk_scope) {
				tot = tot+v_tmp.size();	
			}
			
		}
				%>
		<a href="#" onclick="(function(){
		var x = document.getElementById('wait');
		var y = document.getElementById('main');
		x.style.display = 'block';
		y.style.display = 'none';
		location.href='go.prepDashOverviewSpmi?fwdto=never_mon&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>'})()" target="inner_iframe" class="tile bg-darkRed fg-white" data-role="tile">
			<div class="padding10">
			   	<p class="no-margin text-shadow"><span class="text-bold">PERNYATAAN ISI STANDAR YG BELUM PERNAH DILAKUKAN MONITORING
			       	<br><br><br>
			           <%=tot %> &nbsp&nbspISI STANDAR
			           <br>
			           DARI &nbsp<%=tot_scope_prodi %> PRODI
				          </span>
			     	</p>
			</div>
		</a>
				<%
	}
	if(v_kegiatan_ppepp_yg_lewat!=null && v_kegiatan_ppepp_yg_lewat.size()>0) {
%>
			<a href="#" onclick="(function(){
var x = document.getElementById('wait');
var y = document.getElementById('main');
x.style.display = 'block';
y.style.display = 'none';
location.href='go.prepDashOverviewSpmi?fwdto=overdue_ppepp&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>'})()" target="inner_iframe" class="tile-wide bg-darkRed fg-white" data-role="tile">
            	<div class="padding10">
                	<p class="no-margin text-shadow"><span class="text-bold">DAFTAR KEGIATAN PENETAPAN, PELAKSANAAN, EVALUASI, PENGENDALIAN,
                	 PENINGKATAN, YANG BELUM DILAKSANAKAN SAMPAI DENGAN TANGGAL YG SUDAH DIRENCANAKAN 
                	<br><br><br><br><%=v_kegiatan_ppepp_yg_lewat.size() %>  &nbsp&nbspKEGIATAN</span>
                	</p>
				</div>
			</a>
<%
	}
	if(v_kegiatan_ami_yg_lewat!=null && v_kegiatan_ami_yg_lewat.size()>0) {
		%>
			<a href="#" onclick="(function(){
var x = document.getElementById('wait');
var y = document.getElementById('main');
x.style.display = 'block';
y.style.display = 'none';
location.href='go.prepDashOverviewSpmi?fwdto=overdue_ami&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>'})()" target="inner_iframe" class="tile bg-darkRed fg-white" data-role="tile">
		   		<div class="padding10">
		        	<p class="no-margin text-shadow"><span class="text-bold">KEGIATAN AUDIT MUTU INTERNAL YANG BELUM DILAKSANAKAN s/d TANGGAL YG SUDAH DIRENCANAKAN
		            	<br><br>
		                <%=v_kegiatan_ami_yg_lewat.size() %> &nbsp&nbspKEGIATAN
		                </span>
		           	</p>
				</div>
			</a>
		<%
	}
	if(v_kegiatan_monitoring_no_pengendalian!=null && v_kegiatan_monitoring_no_pengendalian.size()>0) {
			%>
			<a href="#" onclick="(function(){
	var x = document.getElementById('wait');
	var y = document.getElementById('main');
	x.style.display = 'block';
	y.style.display = 'none';
	location.href='go.prepDashOverviewSpmi?fwdto=mon_no_kendal&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>'})()" target="inner_iframe" class="tile bg-darkRed fg-white" data-role="tile">
				<div class="padding10">
			    	<p class="no-margin text-shadow"><span class="text-bold">HASIL MONITORING PELAKSANAAN STANDAR YANG BELUM ADA KEGIATAN PENGENDALIAN
			        	<br><br>
			            <%=v_kegiatan_monitoring_no_pengendalian.size() %> &nbsp&nbspKEGIATAN
			            </span>
			       	</p>
				</div>
			</a>
			<%
	}
	if(v_kegiatan_monitoring_no_kelanjutan!=null && v_kegiatan_monitoring_no_kelanjutan.size()>0) {
				%>
				<a href="#" onclick="(function(){
		var x = document.getElementById('wait');
		var y = document.getElementById('main');
		x.style.display = 'block';
		y.style.display = 'none';
		location.href='go.prepDashOverviewSpmi?fwdto=mon_no_lanjut&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>'})()" target="inner_iframe" class="tile bg-darkRed fg-white" data-role="tile">
					<div class="padding10">
				    	<p class="no-margin text-shadow"><span class="text-bold">MONITORING PELAKSANAAN STD YANG BELUM DILAKSANAKAN s/d TANGGAL YG SUDAH DIRENCANAKAN
				        	<br><br>
				            <%=v_kegiatan_monitoring_no_kelanjutan.size() %> &nbsp&nbspKEGIATAN
				            </span>
				       	</p>
					</div>
				</a>
				<%
	}
	
	if(v_prodi_never_ami!=null && v_prodi_never_ami.size()>0) {
					%>
					<a href="#" onclick="(function(){
			var x = document.getElementById('wait');
			var y = document.getElementById('main');
			x.style.display = 'block';
			y.style.display = 'none';
			location.href='go.prepDashOverviewSpmi?fwdto=never_ami&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>'})()" target="inner_iframe" class="tile bg-darkRed fg-white" data-role="tile">
						<div class="padding10">
					    	<p class="no-margin text-shadow"><span class="text-bold">LIST PRODI YANG BELUM PERNAH DILAKUKAN KEGIATAN AMI</span></p>
					    </div>	
					   	<div style="margin:50px 0 0 10px;position: absolute;">   
					   		<p class="no-margin text-shadow"><span class="text-bold">
					            <%=v_prodi_never_ami.size() %> &nbsp&nbspPRODI
					            </span>
					       	</p>
						</div>
					</a>
					<%
		}
	
				
%>          		
     		</div>
  		</div>     
<%
}
%>    	
    	<div class="tile-group four">
			<span class="tile-group-title">STATUS STANDAR / MANUAL / DOKUMEN</span>
			<div class="tile-container">
		<%
if(v_pelanggaran_isi_std!=null && v_pelanggaran_isi_std.size()>0) {
	li = v_pelanggaran_isi_std.listIterator();
	int tot=0;
	while(li.hasNext()) {
		li.next();
		Vector vtmp = (Vector)li.next();
		tot = tot+vtmp.size();
	}
		%>
			<a href="#" onclick="(function(){
				var x = document.getElementById('wait');
				var y = document.getElementById('main');
				x.style.display = 'block';
				y.style.display = 'none';
				location.href='go.prepDashOverviewSpmi?fwdto=pelanggaran&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>'})()" target="inner_iframe" class="tile-wide bg-darkTeal fg-white" data-role="tile">
		    	<div class="tile-content">
		    		<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/violation.png" data-role="fitImage" data-format="fill">
				</div>	
		    	<div style="margin:95px 0 0 250px;position: absolute;">   
		       		<p class="no-margin text-shadow" style="color:red"><span class="text-bold" style="font-size:2em">
		       			<%=tot %></span><br/></p>
		       	</div>
			</a>
				
		<%	
		}
		
if(true) {		
	int tot_prodi = 0;
	int tot_doc_mutu_all_scope = 0;
	int tot_doc_mutu_uploaded_all_scope = 0;
	li = v_status_doc_mutu.listIterator();
	ListIterator lit = null;
	while(li.hasNext()) {
		tot_prodi++;
		String brs = (String)li.next();
		//FH`74201`C`HUKUM~58~0
		st = new StringTokenizer(brs,"~");
		String nmfak_doc = st.nextToken();
		String kdpst_doc = st.nextToken();
		String kdjen_doc = st.nextToken();
		String nmpst_doc = st.nextToken();
		String tot_doc = st.nextToken();
		String tot_doc_uploaded = st.nextToken();
		tot_doc_mutu_all_scope = tot_doc_mutu_all_scope+Integer.parseInt(tot_doc);
		tot_doc_mutu_uploaded_all_scope = tot_doc_mutu_uploaded_all_scope+Integer.parseInt(tot_doc_uploaded);
		
		Vector v_tmp = (Vector)li.next();
		/*
		lit = v_tmp.listIterator();
		while(lit.hasNext()) {
			brs = (String)lit.next();
			//System.out.println(brs);
		}
		*/
	}
	//System.out.println("tot_prodi="+tot_prodi);
	//System.out.println("tot_doc_mutu_all_scope="+tot_doc_mutu_all_scope);
	//System.out.println("tot_doc_mutu_uploaded_all_scope="+tot_doc_mutu_uploaded_all_scope);
	%>
				<a href="#" onclick="(function(){
					var x = document.getElementById('wait');
					var y = document.getElementById('main');
					x.style.display = 'block';
					y.style.display = 'none';
					location.href='go.prepDashOverviewSpmi?fwdto=dokumen&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>'})()" target="inner_iframe" class="tile-wide bg-darkTeal fg-white" data-role="tile">
					<div class="tile-content">
						<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/books-doc.jpg" data-role="fitImage" data-format="fill">
    				</div>	
					<div style="margin:15px 0 0 10px;position: absolute;">   
						<p class="no-margin text-shadow" style="color:#000"><span class="text-bold" style="font-size:1.1em">
						TOTAL TIPE DOKUMEN:</span><br/></p>
					</div>
					<div style="margin:30px 0 0 10px;position: absolute;">   
						<p class="no-margin text-shadow" style="color:#369"><span class="text-bold" style="font-size:1.1em">
						<%=tot_doc_mutu_all_scope %> dari <%=tot_prodi %> Prodi</span><br/></p>
					</div>
					<div style="margin:70px 0 0 10px;position: absolute;">   
						<p class="no-margin text-shadow" style="color:#000"><span class="text-bold" style="font-size:1.1em">
						TOTAL DOKUMEN</span><br/></p>
					</div>
					<div style="margin:90px 0 0 10px;position: absolute;">   
						<p class="no-margin text-shadow" style="color:#000"><span class="text-bold" style="font-size:1.1em">
						SUDAH DIUNGGAH:</span><br/></p>
					</div>
					<div style="margin:110px 0 0 10px;position: absolute;">   
						<p class="no-margin text-shadow" style="color:#369"><span class="text-bold" style="font-size:1.1em">
						<%=tot_doc_mutu_uploaded_all_scope %> dokumen</span><br/></p>
					</div>
				</a>

<%	
}		
		

		
if(v_status_aktif_std!=null && v_status_aktif_std.size()>0) {
	int tot_std = v_status_aktif_std.size();
	int tot_std_aktif = 0;
	li = v_status_aktif_std.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		st = new StringTokenizer(brs,"~");
		String id_master_std = st.nextToken();
		String ket_master_std = st.nextToken();
		String id_std = st.nextToken();
		String id_tipe_std = st.nextToken();
		String ket_tipe_std = st.nextToken();
		String tgl_sta = st.nextToken();
		String id_versi	= st.nextToken();
		if(!Checker.isStringNullOrEmpty(tgl_sta)) {
			tot_std_aktif++;
		}
	}
	
%>
				<a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/spmi_overview/index_status_aktif_std_v1.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" id="#" target="inner_iframe" class="tile-wide bg-darkTeal fg-white" data-role="tile">
                	<div class="tile-content">
                		<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/checknocheck_small_people.jpg" data-role="fitImage" data-format="fill">
                	</div>	
                	<div style="margin:5px 0 0 120px;position: absolute;">   
		           		<p class="no-margin text-shadow" style="color:black"><span class="text-bold">&nbsp&nbsp[TOTAL STANDAR AKTIF]<br>--------------------------------------<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp[TOTAL STANDAR]</span><br/></p>
		           	</div>
		           	<div style="margin:75px 0 0 190px;position: absolute;">   
		           		<p class="no-margin text-shadow" style="color:black"><span class="text-bold" style="font-size:1.5em">
		           		<%
		           		int beda = (""+tot_std).length()-(""+tot_std_aktif).length();
		           		String adjustment = "";
		           		for(int i=0;i<beda;i++) {
		           			adjustment=adjustment+"&nbsp";
		           		}
		           		%>
		           		&nbsp&nbsp<%=adjustment+tot_std_aktif%><br>-------<br>&nbsp&nbsp<%=tot_std %></span><br/></p>
		           	</div>
          		</a>
          		
<%
}
else {
	%>
				<a href="#" target="inner_iframe" class="tile-wide bg-darkTeal fg-white" data-role="tile">
                	<div class="tile-content">
                		<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/check_small_people.jpg" data-role="fitImage" data-format="fill">
                	</div>	
                	<div style="margin:5px 0 0 120px;position: absolute;">   
		           		<p class="no-margin text-shadow" style="color:black"><span class="text-bold">TOTAL STANDAR YANG <br> SEDANG DIJALANKAN<br>[AKTIF]<br><br><br>0 &nbsp&nbsp&nbspSTANDAR</span><br/></p>
		           	</div>
          		</a>
<%
}	

if(v_status_aktif_man!=null && v_status_aktif_man.size()>0) {
	int tot_man = v_status_aktif_man.size()*5;
	int tot_man_aktif = 0;
	li = v_status_aktif_man.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
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
		if(!Checker.isStringNullOrEmpty(tgl_sta_plan)) {
			tot_man_aktif++;
		}
		if(!Checker.isStringNullOrEmpty(tgl_sta_do)) {
			tot_man_aktif++;
		}
		if(!Checker.isStringNullOrEmpty(tgl_sta_eval)) {
			tot_man_aktif++;
		}
		if(!Checker.isStringNullOrEmpty(tgl_sta_kendal)) {
			tot_man_aktif++;
		}
		if(!Checker.isStringNullOrEmpty(tgl_sta_upg)) {
			tot_man_aktif++;
		}
	}
			
		%>
						
		          		<a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/spmi_overview/index_status_aktif_man_v1.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" id="#" target="inner_iframe" class="tile-wide bg-darkTeal fg-white" data-role="tile">
		                	<div class="tile-content">
		                		<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/manual_checklist2.jpg" data-role="fitImage" data-format="fill">
		                	</div>	
		                	<div style="margin:5px 0 0 120px;position: absolute;">   
				           		<p class="no-margin text-shadow" style="color:black"><span class="text-bold">&nbsp&nbsp[TOTAL MANUAL AKTIF]<br>--------------------------------------<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp[TOTAL MANUAL]</span><br/></p>
				           	</div>
				           	<div style="margin:75px 0 0 190px;position: absolute;">   
		           				<p class="no-margin text-shadow" style="color:black"><span class="text-bold" style="font-size:1.5em">
		           		<%
		           		int beda = (""+tot_man).length()-(""+tot_man_aktif).length();
		           		String adjustment = "";
		           		for(int i=0;i<beda;i++) {
		           			adjustment=adjustment+"&nbsp";
		           		}
		           		%>
		           		&nbsp&nbsp<%=adjustment+tot_man_aktif%><br>-------<br>&nbsp&nbsp<%=tot_man %></span><br/></p>
		           			</div>
		          		</a>
		<%
}
else {
			%>
						<a href="#" target="inner_iframe" class="tile-wide bg-darkTeal fg-white" data-role="tile">
		                	<div class="tile-content">
		                		<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/check_small_people.jpg" data-role="fitImage" data-format="fill">
		                	</div>	
		                	<div style="margin:5px 0 0 120px;position: absolute;">   
				           		<p class="no-margin text-shadow" style="color:black"><span class="text-bold">TOTAL MANUAL YANG <br> SEDANG DIJALANKAN<br>[AKTIF]<br><br><br>0 &nbsp&nbsp&nbspMANUAL</span><br/></p>
				           	</div>
		          		</a>
		<%
}			

//if(true) {
if(v_riwayat_ami_prodi!=null && v_riwayat_ami_prodi.size()>0) {
	%>
	
				<a href="#" onclick="(function(){
					var x = document.getElementById('wait');
					var y = document.getElementById('main');
					x.style.display = 'block';
					y.style.display = 'none';
					location.href='get.histAmiProdi?fwdto=hist_ami_prodi&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>'})()" target="inner_iframe" class="tile-wide bg-darkTeal fg-white" data-role="tile">
    				<div class="tile-content">
    					<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/ami_v3.jpeg" data-role="fitImage" data-format="fill">
    				</div>	
    				
       				<div style="margin:113px 0 0 75px;position: absolute;">   
						<p class="no-margin text-shadow" style="color:#fff;font-weight:bold"><span class="text-bold" style="font-size:1.2em;font-weight:bold">
						<!--  (<%=v_riwayat_ami_prodi.size() %>)&nbspPRODI</span><br/>--></p>
					</div>
				</a>
<%	
}
		%>	
			</div>
		</div>	
    	
    	<!--  div class="tile-group double">
			<span class="tile-group-title">STANDAR BELUM AKTIF</span>
			<div class="tile-container">
			</div>
		</div-->	
		
		<!-- div class="tile-group double">
			<span class="tile-group-title">PENINGKATAN</span>
			<div class="tile-container">
			</div>
		</div-->	
	</div>
</div>
</body>
</html>
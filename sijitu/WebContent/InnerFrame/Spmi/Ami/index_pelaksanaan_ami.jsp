<!DOCTYPE html>
<html>
<head lang="en">
	<%@page import="beans.sistem.AskSystem"%>
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>
	<%@page import="beans.dbase.spmi.SearchStandarMutu"%>
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
	<%@ page import="beans.dbase.mail.*" %>
<%	
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
if(Checker.isStringNullOrEmpty(kdpst_nmpst_kmp)) {
	kdpst_nmpst_kmp = (String)session.getAttribute("current_kdpst_nmpst_kmp");
}
while(kdpst_nmpst_kmp.contains("~")) { //tambahan digunakan only cg2mutu
	kdpst_nmpst_kmp = kdpst_nmpst_kmp.replace("~", "`");
}
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
//System.out.println("kdpst_nmpst_kmp1=>>="+kdpst_nmpst_kmp);
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();//ngga kepake bis diignore
String keter_prodi = Converter.getDetailKdpst_v1(kdpst);
int at_page=Constant.getAt_page();
int max_data_per_pg=Constant.getMax_data_per_pg();
int max_data_per_man_pg=Constant.getAt_page();

//DARI FORM
//kalo yg sessio dari list urutan question
String id_ami = (String)request.getParameter("id_ami");
if(Checker.isStringNullOrEmpty(id_ami)) {
	id_ami = (String)session.getAttribute("id_ami");;
}
String kode_activity = (String)request.getParameter("kode_activity");
if(Checker.isStringNullOrEmpty(kode_activity)) {
	kode_activity = (String)session.getAttribute("kode_activity");
}
String tgl_plan = (String)request.getParameter("tgl_plan");
if(Checker.isStringNullOrEmpty(tgl_plan)) {
	tgl_plan = (String)session.getAttribute("tgl_plan");
}
String ketua_tim = (String)request.getParameter("ketua_tim");
if(Checker.isStringNullOrEmpty(ketua_tim)) {
	ketua_tim = (String)session.getAttribute("ketua_tim");
}
String anggota_tim = (String)request.getParameter("anggota_tim");
if(Checker.isStringNullOrEmpty(anggota_tim)) {
	anggota_tim = (String)session.getAttribute("anggota_tim");
}
String id_cakupan_std = (String)request.getParameter("id_cakupan_std");
if(Checker.isStringNullOrEmpty(id_cakupan_std)) {
	id_cakupan_std = (String)session.getAttribute("id_cakupan_std");
}
String ket_cakupan_std = (String)request.getParameter("ket_cakupan_std");
if(Checker.isStringNullOrEmpty(ket_cakupan_std)) {
	ket_cakupan_std = (String)session.getAttribute("ket_cakupan_std");
}
String tgl_ril = (String)request.getParameter("tgl_ril");
if(Checker.isStringNullOrEmpty(tgl_ril)) {
	tgl_ril = (String)session.getAttribute("tgl_ril");
}
String tgl_ril_done = (String)request.getParameter("tgl_ril_done");
if(Checker.isStringNullOrEmpty(tgl_ril_done)) {
	tgl_ril_done = (String)session.getAttribute("tgl_ril_done");
}
double max_score = ToolSpmi.getAvailMaxPenilaian(Integer.parseInt(id_ami));
double min_score = ToolSpmi.getAvailMinPenilaian(Integer.parseInt(id_ami));
//System.out.println("id_cakupan_std = "+id_cakupan_std);

//SendEmail.senEmail("f_setiaw@yahoo.com", "no subjek", "iseng ajah");


//System.out.println("ketua_tim=="+ketua_tim);
//System.out.println("id_ami0="+id_ami);
ToolSpmi ts = new ToolSpmi();
String status = ts.getStatusKegiatanAmi(Integer.parseInt(id_ami));
boolean ami_belum_dilaksanakan = false;
if(status!=null && status.equalsIgnoreCase("belum")) {
	ami_belum_dilaksanakan = true;
}
int tot_QA = ToolSpmi.getTotalQandA(Integer.parseInt(id_ami),ami_belum_dilaksanakan);

String hasil_penilaian = "0";
double total_hasil_penilaian = ToolSpmi.getHasilPenilaian(Integer.parseInt(id_ami));
if(total_hasil_penilaian>0) {
	hasil_penilaian = ""+total_hasil_penilaian;
	st = new StringTokenizer(hasil_penilaian,".");
	String bilangan = st.nextToken();
	String decimal =  st.nextToken();
	try {
		int tester = Integer.parseInt(decimal);
		if(tester==0) {
			hasil_penilaian = new String(bilangan);
		}
	}
	catch(Exception e) {
		
	}
}
//System.out.println("tot_QA="+tot_QA);
//System.out.println("ami_belum_dilaksanakan="+ami_belum_dilaksanakan);
Vector v_scope_kdpst_spmi = (Vector)session.getAttribute("v_scope_kdpst_spmi");//KDFAKMSPST,KDPSTMSPST,KDJENMSPST,NMPSTMSPST
//System.out.println("id_ami0="+id_ami);
//System.out.println("status="+status);
Boolean spmi_editor = (Boolean) session.getAttribute("spmi_editor");




%>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="description" content="Metro, a sleek, intuitive, and powerful framework for faster and easier web development for Windows Metro Style.">
    <meta name="keywords" content="HTML, CSS, JS, JavaScript, framework, metro, front-end, frontend, web development">
    <meta name="author" content="Sergey Pimenov and Metro UI CSS contributors">

    <link rel='shortcut icon' type='image/x-icon' href='../favicon.ico' />
    <title>Tiles examples :: Start Screen :: Metro UI CSS - The front-end framework for developing projects on the web in Windows Metro Style</title>
  <link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
    <link href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/css/metro.css" rel="stylesheet">
    <link href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/css/metro-icons.css" rel="stylesheet">
    <!--<link href="../css/metro-responsive.css" rel="stylesheet">-->

    <script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
    <script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/metro.js"></script>
  


<style>
    a.img:hover {
		text-decoration: none;
		background:none;
	}

	a.hover {
		text-decoration: none;
		background:none;
	}

	a.hover:hover {
		text-decoration: underline;
		background:none;
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
<%
if(!Checker.isStringNullOrEmpty(id_cakupan_std)) {		
	int norut_link=0;
    StringTokenizer std = new StringTokenizer(id_cakupan_std,",");
	while(std.hasMoreTokens()) {
		std.nextToken();
%>
		$("#prepFormAmi<%=norut_link%>").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.prepFormAmi',
        		type: 'POST',
        		data: $("#prepFormAmi<%=norut_link%>").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	        //this is where we append a loading image
        	    },
        	    success:function(data){
        	        //successful request; do something with the data 
        	        //&id_ami=<=id_ami%>&kode_activity=<=kode_activity%>&tgl_plan=<=tgl_plan%>&ketua_tim=<=ketua_tim%>&anggota_tim=<=anggota_tim%>&id_cakupan_std=<=id_cakupan_std%>&ket_cakupan_std=<=ket_cakupan_std%>&tgl_ril=<=tgl_ril%>&tgl_ril_done=<=tgl_ril_done%>
        	    	<%
        	    	if(status.equalsIgnoreCase("belum")) {
        	    	%>
        	    		window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Spmi/Ami/form_penilaian_ami.jsp?status=<%=status%>&at_page=1&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>";
        	    	<%
        	    	
        	    	}
        	    	else if(status.equalsIgnoreCase("sedang")) {
        	    		%>
        	    		window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Spmi/Ami/form_pelaksanaan_ami.jsp?status=<%=status%>&at_page=1&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>";
        	    	<%	
        	    	}
        	    	else if(status.equalsIgnoreCase("selesai")) {
        	    		%>
        	    		window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Spmi/Ami/form_hasil_ami.jsp?status=<%=status%>&at_page=1&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>";
        	    	<%	
        	    	}
        	    	%>
        	        
        	    },
        	    error:function(){
        	        //failed request; give feedback to user
        	    }
        	})
        	return false;
        });
		$("#link<%=norut_link%>").click(function(){
	        $("#prepFormAmi<%=norut_link%>").submit();
	        return false;
	    });
		
		<%
		norut_link++;
	}
	
	
}
%>		
		$("#toogleAmi").submit(function(e) {	
			e.preventDefault(); //STOP default action
			$.ajax({
			url: 'go.toogleAmi',
			type: 'POST',
			data: $("#toogleAmi").serialize(),
	    	beforeSend:function(){
	    		$("#wait").show();
		    	$("#main").hide();
		    	parent.scrollTo(0,0);
	        // this is where we append a loading image
	    	},
	    	success:function(data){
		        // successful request; do something with the data 
		    	//window.location.href = "index_stp.jsp";
	    		window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Spmi/Ami/index_pelaksanaan_ami.jsp?at_page=1&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>";
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
<script type="text/javascript">
      jQuery(function() {
              jQuery('#kdpst_nmpst_kmp').change(function() {
                 this.form.submit();
              });
     });
</script>
</head>
<body style="overflow-y: hidden;">
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai</p>
</div>
<div id="main">
	<jsp:include page="menu_back_ami.jsp" />
	
   	<div style="padding:50px 0 0 80px">
   		<br>
    	<form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/home_spmi.jsp?atMenu=spmi">
			<select onchange="this.form.submit()" name="kdpst_nmpst_kmp" id="kdpst_nmpst_kmp" style="width:100%;height:25px;border:none;text-align-last:center;border-radius: 10px;">
<%
ListIterator li = v_scope_kdpst_spmi.listIterator();

while(li.hasNext()) {
	String brs = (String)li.next();
	//System.out.println("brs scope="+brs);
	st = new StringTokenizer(brs,"`");
	String current_fak = st.nextToken();
	String current_kdpst = st.nextToken();
	String current_kdjen = st.nextToken();
	String current_nmpst = st.nextToken();
	String info_prodi = Converter.getDetailKdpst_v1(current_kdpst);
	if(current_kdpst.equalsIgnoreCase(kdpst)) {
	%>
				<option value="<%=current_kdpst %>`<%=current_nmpst %>`null" selected="selected"><%=info_prodi%></option> 
	<%	
	}
	else {
		%>
				<option value="<%=current_kdpst %>`<%=current_nmpst %>`null"><%=info_prodi%></option> 
	<%
	}

}
%>				
				
			</select>
		</form>		
	</div>


<%
if(!Checker.isStringNullOrEmpty(status)) {
%>
	<center>
	<br>
<%
	if(status.equalsIgnoreCase("belum")) {
%>	
		<form id="toogleAmi">
			<input type="hidden" name="status" value="<%=status%>"/>
			<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp%>"/>
			<input type="hidden" name="id_ami" value="<%=id_ami%>"/>
			<input type="hidden" name="kode_activity" value="<%=kode_activity %>"/>
			<input type="hidden" name="tgl_plan" value="<%=tgl_plan %>"/>
			<input type="hidden" name="ketua_tim" value="<%=ketua_tim %>"/>
			<input type="hidden" name="anggota_tim" value="<%=anggota_tim %>"/>
			<input type="hidden" name="id_cakupan_std" value="<%=id_cakupan_std %>"/>
			<input type="hidden" name="tgl_ril" value="<%=tgl_ril %>"/>
			<input type="hidden" name="ket_cakupan_std" value="<%=ket_cakupan_std %>"/>
			<input type="hidden" name="tgl_ril_done" value="<%=tgl_ril_done %>"/>
			<input type=image border="0" alt="PLAY" src="<%=Constants.getRootWeb() %>/images/play_blue.png" width="75" height="30" style="text-decoration: none;" onclick="if (confirm('Apakah anda yakin akan memulai kegiatan Audit Mutu Internal? \nCatatan:\n1. Pastikan Cakupan Standar kegiatan audit mutu telah sesuai.\n2. Pastikan Q&A isi standar sudah dipersiapkan beserta bobot penilaiannya.')) return true;return false; ">
		</form>
<%
	}
	else if(status.equalsIgnoreCase("sedang")) {
		%>	
		<form id="toogleAmi">
			<input type="hidden" name="status" value="<%=status%>"/>
			<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp%>"/>
			<input type="hidden" name="id_ami" value="<%=id_ami%>"/>
			<input type="hidden" name="kode_activity" value="<%=kode_activity %>"/>
			<input type="hidden" name="tgl_plan" value="<%=tgl_plan %>"/>
			<input type="hidden" name="ketua_tim" value="<%=ketua_tim %>"/>
			<input type="hidden" name="anggota_tim" value="<%=anggota_tim %>"/>
			<input type="hidden" name="id_cakupan_std" value="<%=id_cakupan_std %>"/>
			<input type="hidden" name="tgl_ril" value="<%=tgl_ril %>"/>
			<input type="hidden" name="ket_cakupan_std" value="<%=ket_cakupan_std %>"/>
			<input type="hidden" name="tgl_ril_done" value="<%=tgl_ril_done %>"/>
			<input type=image border="0" alt="STOP" src="<%=Constants.getRootWeb() %>/images/stop_red.png" width="30" height="30" style="text-decoration: none;" onclick="if (confirm('Apakah anda yakin akan mengakhiri kegiatan Audit Mutu Internal? \nCatatan:\n1. Anda tidak dapat melakukan perubahan apapun setelah kegiatan berakhir. \n2. Pastikan seluruh Q&A dan Saran telah diisi.  ')) return true;return false; ">
		</form>

<%		
	}
	else if(status.equalsIgnoreCase("selesai")) {
%>
		<form name="input_form" action="go.downloadVec2Excl?output_file_name=tmp_<%=AskSystem.getTime() %>" method="POST">
<button type="button" class="btn btn-default" onclick="document.input_form.submit()">
	<span class="glyphicon glyphicon-floppy-save"></span> Download
</button>
</form>
<%		
	}
%>		
	</center>
<%
}
%>	
	<div class="tile-area tile-area-scheme-dark fg-white" style="height: 100%; max-height: 100% !important;">
    	<div class="tile-group double">
        	<span class="tile-group-title" >INFO KEGIATAN AMI (<%=id_ami %>)</span>
        	<a href="#" target="inner_iframe" class="tile-wide bg-darkBlue fg-white" data-role="tile">
            	<div class="padding20">
                	<p class="no-margin text-shadow"><span class="text-bold">NAMA / KODE KEGIATAN :</span></p>
                	<p class="no-margin text-shadow" style="text-align:right"><span class="text-bold"><%=kode_activity %></span></p>
                	<p class="no-margin text-shadow"><span class="text-bold">TGL RENCANA KEGIATAN :</span></p>
                	<p class="no-margin text-shadow" style="text-align:right"><span class="text-bold"><%=Converter.autoConvertDateFormat(tgl_plan, "/") %></span></p>
                	<p class="no-margin text-shadow"><span class="text-bold">KETUA TIM AUDIT :</span></p>
                	<p class="no-margin text-shadow" style="text-align:right"><span class="text-bold"><%=ketua_tim.toUpperCase() %></span></p>
				</div>
			</a>
			<a href="#" target="inner_iframe" class="tile-wide bg-darkBlue fg-white" data-role="tile">
            	<div style="margin:15px 0 0 20px;position: absolute;">
                	<p class="no-margin text-shadow"><span class="text-bold">ANGGOTA TIM AUDIT :</span>
                	<span class="text-bold">
                	
<%
st = new StringTokenizer(anggota_tim,",");
int counter=1;
if(st.hasMoreTokens()) {
%>
						<table style="width:100%">
                			<tr>
<%                			
	while(st.hasMoreTokens()) {
		if(counter%2!=0) {
%>
								<td style="width:5%;padding:0 0 0 0">(<%=counter++%>)</td>
								<td style="width:44%;padding:0 0 0 5px"><%=st.nextToken().toUpperCase() %></td>
<%		
		}
		else {
%>
						
								<td style="width:5%;padding:0 0 0 5px">(<%=counter++%>)</td>
								<td style="width:46%;padding:0 0 0 5px"><%=st.nextToken().toUpperCase() %></td>
							
<%
			if(st.hasMoreTokens()) {
				%>
							</tr>
							<tr>
<%
			}
			else {
				%>
							</tr>
<%
			}
		}
	}
%>
						
						</table>
<%
}

%> 
					  
					</span>             	
					</p>
				</div>
			</a>
			<a href="#" target="inner_iframe" class="tile-wide bg-darkBlue fg-white" data-role="tile">
            	<div style="margin:15px 0 0 20px;position: absolute;">
                	<p class="no-margin text-shadow"><span class="text-bold"><br>TOTAL PERTANYAAN :</span></p>
                	<p class="no-margin text-shadow" style="text-align:left"><span class="text-bold">&nbsp&nbsp&nbsp<%=tot_QA %></span></p>
                	<p class="no-margin text-shadow"><span class="text-bold"><br>RANGE NILAI :</span></p>
                	<%
                		String minimum = "0",maximum="0";
                		if((""+min_score).endsWith(".0")) {
                			minimum = (""+min_score).substring(0, (""+min_score).length()-2);
                		}
                		if((""+max_score).endsWith(".0")) {
                			maximum = (""+max_score).substring(0, (""+max_score).length()-2);
                		}
                	%>
                	<p class="no-margin text-shadow" style="text-align:left"><span class="text-bold">&nbsp&nbsp&nbsp<%=minimum %>&nbsp&nbsp s/d &nbsp&nbsp<%=maximum %></span></p>
				
				</div>
			</a>
<%
;//ToolSpmi.getTotalPenilaianAmi(Integer.parseInt(id_ami));
%>			
			<a href="#" target="inner_iframe" class="tile-wide bg-darkBlue fg-white" data-role="tile">
            	<div class="padding20">
                	<p class="no-margin text-shadow"><span class="text-bold">HASIL PENILAIAN :</span></p>
					<p class="no-margin text-shadow"><span class="text-bold"><br>TOTAL NILAI :</span></p>
                	<p class="no-margin text-shadow" style="text-align:right"><span class="text-bold">&nbsp&nbsp&nbsp<%=hasil_penilaian %></span></p>
                	<p class="no-margin text-shadow"><span class="text-bold"><br>TOTAL PELANGGARAN :</span></p>
                	<p class="no-margin text-shadow" style="text-align:right"><span class="text-bold">&nbsp&nbsp&nbsp<%=ToolSpmi.getTotalPelanggaran(Integer.parseInt(id_ami)) %></span></p>
				</div>
			</a>
        </div>	
        <div class="tile-group six">
        	<span class="tile-group-title" >CAKUPAN STANDAR KEGIATAN AMI</span>

 <%
if(!Checker.isStringNullOrEmpty(id_cakupan_std)) {
	int norut_link=0;
	int urutan=1;
    StringTokenizer std = new StringTokenizer(id_cakupan_std,",");
    StringTokenizer stk = new StringTokenizer(ket_cakupan_std,",");
    while(std.hasMoreTokens()) {
    	String id_master_std = std.nextToken();
    	String ket_master_std = stk.nextToken();
    	st = new StringTokenizer(ket_master_std);
%>
			
            	<form id="prepFormAmi<%=norut_link %>" method="post">
            		<input type="hidden" name="at_page" value="1"/>
            		<input type="hidden" name="status" value="<%=status%>"/>
            		<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp %>" />
            		<input type="hidden" name="id_ami" value="<%=id_ami %>"/>
					<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp%>"/>
					<input type="hidden" name="kode_activity" value="<%=kode_activity %>"/>
					<input type="hidden" name="tgl_plan" value="<%=tgl_plan %>"/>
					<input type="hidden" name="ketua_tim" value="<%=ketua_tim %>"/>
					<input type="hidden" name="anggota_tim" value="<%=anggota_tim %>"/>
					<input type="hidden" name="id_cakupan_std" value="<%=id_cakupan_std %>"/>
					<input type="hidden" name="tgl_ril" value="<%=tgl_ril %>"/>
					<input type="hidden" name="ket_cakupan_std" value="<%=ket_cakupan_std %>"/>
					<input type="hidden" name="tgl_ril_done" value="<%=tgl_ril_done %>"/>
					<input type="hidden" name="id_master_std" value="<%=id_master_std %>"/>
					<input type="hidden" name="ket_master_std" value="<%=ket_master_std %>"/>
            		<a  href="#" id="link<%=norut_link++ %>" target="inner_iframe" class="tile bg-darkBlue fg-white" data-role="tile">
            		<div style="margin:5px 0 0 63px;position: absolute;font-weight:bold">   
            			<p class="no-margin text-shadow" style="text-align:center">
            			<span class="text-bold">
            			[<%=urutan++ %>]
            			</span>
            			</p>
            		</div>
            		<div style="margin:20px 0 0 25px;position: absolute;font-weight:bold">   
                	<p class="no-margin text-shadow" style="text-align:center">
                	<span class="text-bold">
<%
		hasil_penilaian="0";//ToolSpmi.getTotalPenilaianAmi(Integer.parseInt(id_ami),Integer.parseInt(id_master_std));
		total_hasil_penilaian = ToolSpmi.getHasilPenilaian(Integer.parseInt(id_ami),Integer.parseInt(id_master_std));
		if(total_hasil_penilaian>0) {
			hasil_penilaian = ""+total_hasil_penilaian;
			StringTokenizer stt = new StringTokenizer(hasil_penilaian,".");
			String bilangan = stt.nextToken();
			String decimal =  stt.nextToken();
			try {
				int tester = Integer.parseInt(decimal);
				if(tester==0) {
					hasil_penilaian = new String(bilangan);
				}
			}
			catch(Exception e) {
				
			}
		}
		while(st.hasMoreTokens()) {
			out.print(st.nextToken().toUpperCase());
			if(st.hasMoreTokens()) {
				out.print("<br>");
			}
		}
		out.print("<br><br>");
%>                	
					</span></p>
					</div>
					<div style="margin:80px 0 0 10px;position: absolute;font-weight:bold">   
						<%="Total Nilai:" %>
					</div>	
					<div style="margin:80px 0 0 100px;position: absolute;font-weight:bold">
						<%=hasil_penilaian %> /
						<%
			String hasil = ""+ToolSpmi.getAvailMaxPenilaian(Integer.parseInt(id_ami),Integer.parseInt(id_master_std));
			StringTokenizer stt = new StringTokenizer(hasil,".");
			String int_val = stt.nextToken();
			String dec_val = stt.nextToken();
			if(Double.parseDouble(dec_val)>0) {
				out.print(hasil);
			}
			else {
				out.print(int_val);
			}
						%>
					</div>	
					<div style="margin:110px 0 0 10px;position: absolute;font-weight:bold">  
						<%="Pelanggaran:"%>
					</div>	
					<div style="margin:110px 0 0 110px;position: absolute;font-weight:bold">
						<%= ToolSpmi.getTotalPelanggaran(Integer.parseInt(id_ami),Integer.parseInt(id_master_std))%> 
						
					</div>
					</a>
				</form>	
			
			
			
			
			
<% 
    }
}
else {
%>
			<a href="#" target="inner_iframe" class="tile bg-darkBlue fg-white" data-role="tile">
            	<div class="padding20">
                	<p class="no-margin text-shadow">CAKUPAN STANDAR <span class="text-bold">KEGIATAN AMI <br>BELUM DITENTUKAN</span></p>
				</div>
			</a>
<%
}
%>          		
				
			</div>
  		</div>     
			
	</div>
</div>

</body>
</html>
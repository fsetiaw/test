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
    

    <div class="tile-area tile-area-scheme-dark fg-white" style="height: 100%; max-height: 100% !important;">
    <%
SearchDbMainNotification sdmn = new SearchDbMainNotification(validUsr.getNpm());
    %>  
        <div class="tile-group triple">
			<span class="tile-group-title">Kirim Pesan</span>
			<div class="tile-container">
		<%
		boolean no_item = true;
	  	//======kehadiran dosen==================
	  	//link untuk mahasiswa
	  
		////System.out.println("info_dosen_arrival="+info_dosen_arrival);  			     
	  	if(true) {
	  	//if(true) {
	  		no_item=false;
	  	%>
		<div class="tile-wide bg-darkTeal fg-white" data-role="tile">
		 	<form action="go.kirimPesanGroup">
            <div class="tile-content image-set">
           <input type="hidden" name="target_group_id"  value="<%=Checker.getGroupId("BAAK")%>"/>
            </div>
            <div style="margin:15px 0 0 10px;position: absolute;">   
                   	<p class="no-margin text-shadow" style="color:white">BIRO<span class="text-bold"> ADMINISTRASI AKADEMIK</span></p>
            </div>       	
            <div style="margin:45px 0 0 10px;position: absolute;">
                   	<textarea name="isi_pesan" rows="6" style="width:225px;" placeholder="isi pesan"></textarea>
            </div>   
            <div style="margin:42px 0 0 240px;position: absolute;">    	
                   	<input class="button" type="submit" value="send" style="height:90px;background:grey;color:white;text-weight:bold"/>	
            </div>
            </form>	
       </div>    			
	                	<%	
	  	}
	    //cari null value  
	    String cmd = "viewWhoRegister";


	  	//link kehadiran dosen bagi pegawai, triger by kalender akademik tanggal mulai perkuliahan (BELUM DIBUAT)
	  	//dan bila memiliki akases untuk sad -status absensi dosen
	  	
	  	if(true) {
	  		no_item=false;
	  		%>
			<a href="get.infoMsgDashboard" target="inner_iframe" class="tile bg-darkTeal fg-white" data-role="tile">
            	<div class="padding20">
                	<p class="no-margin text-shadow">RIWAYAT <br/><span class="text-bold">PESAN</span><br/></p>
				</div>
			</a>       			
	                	<%	
	                	
	  	}
	  	
	  	
	  	//======end kehadiran dosen====================
	  			
	  	//=================my inbox====================
	  	if(true) {
	  		no_item=false;
                        	%>
            <a href="get.msgInbox?sta_index=0&range=<%=Constants.getRangeMgsInbox()%>&show=unread" target="inner_iframe" class="tile bg-amber fg-white" data-role="tile">            	
          		<div class="tile-content iconic">
                	<span class="icon mif-envelop"></span>
                </div>
                    <span class="tile-label">New Inbox</span>
            </a>           	
<%
		}
	  	//=================my inbox====================
	  	//++++++++++++daftar ulang untuk mhs+++++++++++++++++++++++++++++
	  	
	  	if(true) {
	  		
	  		String usr_npm = validUsr.getNpm();
	  		no_item=false;	
	  		%>
	  	<a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}InnerFrame/Prakuliah/Mhs/DaftarUlangByOpr.jsp?id_obj=<%=validUsr.getIdObj()%>&nmm=<%=validUsr.getNmmhs(usr_npm)%>&npm=<%=usr_npm %>&obj_lvl=<%=validUsr.getObjLevel() %>&kdpst=<%=validUsr.getKdpst() %>&cmd=heregistrasi" target="inner_iframe" class="tile bg-darkBlue fg-white" data-role="tile">            	
      		<div class="tile-content iconic">
            	<span class="icon mif-user"></span>
            </div>
                <span class="tile-label">PENGAJUAN DAFTAR ULANG</span>
        </a>
       <% 
	  	}
	  //++++++++++++end daftar ulang untuk mhs+++++++++++++++++++++++++++++
	  	
	  	//=================krs Approval====================
	  	// ====================================krs start====================================================
   						
   						/*
   						<bagian penerima>
   						*/
   		
   			
   		if(sdmn.adaPengajuanKrsApprovalUntukSaya(validUsr.getNpm())) {
   			no_item=false;
		//if(true) {	
   			//StringTokenizer st = new StringTokenizer(tknKrsNotifications,"||");
   			//application.setAttribute("tknKrsNotifications", tknKrsNotifications);
   							////System.out.println("1."+tknKrsNotifications);
   						%>
   						
			<!--  a class="tile bg-indigo fg-white" data-role="tile" href="<Constants.getRootWeb() %>/InnerFrame/Notifications/krsNotification.jsp" target="inner_iframe"-->   						
   			<a class="tile bg-indigo fg-white" data-role="tile" href="prep.pengajuanKrs" target="inner_iframe">
            	<div class="tile-content iconic">
                	<span class="icon mif-file-binary"></span>
                </div>
                    <span class="tile-label">Pengajuan KRS</span>
           	</a>		
   				

   						<%
		}
   						/*
   						<bagian pengirim>
   						*/
		if(true) {
			no_item=false;				////System.out.println("ini0="+tknKrsNotificationsForSender);
			StringTokenizer st = new StringTokenizer("","||");
   						%>
   			<a class="tile bg-indigo fg-white" data-role="tile" href="<%=Constants.getRootWeb() %>/InnerFrame/Notifications/krsNotification.jsp" target="inner_iframe">
            	<div class="tile-content iconic">
                	<span class="icon mif-file-binary"></span>
                </div>
                <div style="margin:115px 0 0 10px">   
                	<p id="city_weather_daily"></p> 
                    <p class="no-margin text-shadow">STATUS<br/> <span class="text-bold">PENGAJUAN KRS</span></p>
                </div>
          	</a>		
   				
   						<%
		}
	  	//===============end Krs approval =================
	  	
	  	
	  	//======request daftar ulang ======
	  	String her_req = request.getParameter("registrasiReq");
	  
	  	if(her_req!=null && !Checker.isStringNullOrEmpty(her_req) && her_req.equalsIgnoreCase("true")) {
	  		String thsms_heregistrasi = Checker.getThsmsHeregistrasi();
	  		no_item=false;
	  	%>
	  		 <a href="get.whoRegisterWip" target="inner_iframe" class="tile bg-darkBlue fg-white" data-role="tile">            	
       		<div class="tile-content iconic">
             	<span class="icon mif-user"></span>
             </div>
                 <span class="tile-label">PENGAJUAN DAFTAR ULANG</span>
         </a>
        <%  
	  	}
	  	
	  	
	  	
	  	//=====end request daftar ulang ======
	  			
	  	//=============buti pembayaran=============	
	  	
	  	
	  	////System.out.println("``"+sdmn.adaRequestPymntApproval(v_scope_id));
	  	//if(validUsr.isAllowTo("pymntApprovee")>0 && jsoaPymntReq!=null && jsoaPymntReq.length()>0) {
	  	if(true) {
	  		no_item=false;
		%>
			<div class="tile-wide bg-orange fg-white" data-role="tile">
            	<div class="tile-content image-set">
            		<a href="prep.pengajuanPymnt" target="inner_iframe"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/payment.jpg" data-role="fitImage" data-format="fill"></a>
                	<!--  a href="<Constants.getRootWeb() %>/InnerFrame/Keu/requestKeuAprovalForm.jsp" target="inner_iframe"><img src="<Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/payment.jpg" data-role="fitImage" data-format="fill"></a -->
                </div>
                <div style="margin:15px 0 0 10px;position: absolute;">   
                   	<p class="no-margin text-shadow" style="color:#74888b">VALIDASI<br/><span class="text-bold"> PEMBAYARAN</span><br/>MAHASISWA</p>
                </div>
          	</div>
                	
			
		<%	
		}
	  	//===========end buti pembayaran=============		
	  	//======================pengajuan ujian sidang===============================
	  	if(true) {
		//if(true) {
		String alm = "";
			//if(validUsr.getObjNickNameGivenObjId().contains("MHS")||validUsr.getObjNickNameGivenObjId().contains("mhs")) {
				alm = "get.pengajuanUa?atMenu=ua";
			//}
			//else {
				alm = "get.pengajuanUa?atMenu=ua";
			//}
			//validUsr.
			if(validUsr.iAmStu()) {
				no_item=false;
				%>
			<div class="tile-wide bg-orange fg-white" data-role="tile">
            	<div class="tile-content image-set">
                	<a href="<%=alm%>" target="inner_iframe"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/presentation.jpg" data-role="fitImage" data-format="fill"></a>
                </div>
                <div style="margin:115px 0 0 140px;position: absolute;">   
                   	<p class="no-margin text-shadow">PENGAJUAN UJIAN<br/><span class="text-bold">SKRIPSI/TESIS/DISERTASI </span><br/></p>
                </div>
            </div>
        	<%		
			}
			else {
				no_item=false;
			%>
			<div class="tile bg-orange fg-white">
            	<div class="tile-content">
                	<a href="<%=alm%>" target="inner_iframe"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/appointment5.jpg" data-role="fitImage" data-format="square"></a>
                </div>
                <div style="margin:15px 0 0 10px;position: absolute;">   
                	<p class="no-margin text-shadow" style="color:#555a5e">PENGAJUAN UJIAN<br/><span class="text-bold">SKRIPSI/TESIS<br/>& DISERTASI </span><br/></p>
               	</div>
          	</div>
	<%
			}
		}
	  	//======================end pengajuan ujian sidang===============================
		//=====================pengajuan cuti===========================
		
		
	  	
	  	////System.out.println("v_scope_id="+v_scope_id);
		String full_nm_pengajuan_table_rules = "CUTI_RULES";
		String type_pengajuan = full_nm_pengajuan_table_rules.replace("_RULES", "");
		String title_pengajuan = type_pengajuan.replace("_", " ");
		String cmd_scope = "cuti";
		String folder_pengajuan = "cuti";
		String pic_name = "cuti.jpg";
		if(true) {
				//if(sdmn.getNotificationPengajuanCuti()) {
		
		//if(sdmn.isTherePengajuanCuti(v_scope_id)) {	
			no_item=false; 	
				//get.listPengajuan?fullnm_tabel_rules=<%=full_nm_pengajuan_table_rules &target_thsms=<%=Checker.getThsmsHeregistrasi() 
					%>
					<a href="go.moPp?target_thsms=<%=Checker.getThsmsHeregistrasi()%>&folder_pengajuan=<%=folder_pengajuan %>&scope=<%=cmd_scope %>&table=<%=full_nm_pengajuan_table_rules %>" target="inner_iframe" class="tile bg-orange fg-white" data-role="tile">
						<div class="tile-content">
		            		<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/<%=pic_name %>" data-role="fitImage" data-format="square">
		            	</div>
		            	<div style="margin:105px 0 0 10px;position: absolute;">   
		<%
			if(validUsr.iAmStu()) {
			%>            	
           		<p class="no-margin text-shadow">STATUS PENGAJUAN<br/><span class="text-bold"><%=title_pengajuan %></span></p>
			<%		
			}
			else {
		%>            	
		               		<p class="no-margin text-shadow">PENGAJUAN<br/><span class="text-bold"><%=title_pengajuan %></span></p>
		<%
			}
		%>               		
		             	</div>
		  	       	</a>	
		<%
		}
		//======================================end cuti=============================================================

		//=====================pengajuan keluar===========================
		
	
	  	
	  	////System.out.println("v_scope_id="+v_scope_id);
		full_nm_pengajuan_table_rules = "KELUAR_RULES";
		type_pengajuan = full_nm_pengajuan_table_rules.replace("_RULES", "");
		title_pengajuan = type_pengajuan.replace("_", " ");
		cmd_scope = "out";
		folder_pengajuan = "keluar";
		pic_name = "keluar.jpg";
		if(true) {
				//if(sdmn.getNotificationPengajuanCuti()) {
		
		//if(sdmn.isTherePengajuanCuti(v_scope_id)) {	
			no_item=false; 	
				//get.listPengajuan?fullnm_tabel_rules=<%=full_nm_pengajuan_table_rules &target_thsms=<%=Checker.getThsmsHeregistrasi() 
					%>
					<a href="go.moPp?target_thsms=<%=Checker.getThsmsHeregistrasi()%>&folder_pengajuan=<%=folder_pengajuan %>&scope=<%=cmd_scope %>&table=<%=full_nm_pengajuan_table_rules %>" target="inner_iframe" class="tile bg-orange fg-white" data-role="tile">
						<div class="tile-content">
		            		<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/<%=pic_name %>" data-role="fitImage" data-format="square">
		            	</div>
		            	<div style="margin:115px 0 0 65px;position: absolute;">   
		<%
			if(validUsr.iAmStu()) {
			%>            	
           		<p class="no-margin text-shadow">STATUS PENGAJUAN<br/><span class="text-bold"><%=title_pengajuan %></span></p>
			<%		
			}
			else {
		%>            	
		               		<p class="no-margin text-shadow">PENGAJUAN<br/><span class="text-bold"><%=title_pengajuan %></span></p>
		<%
			}
		%>               		
		             	</div>
		  	       	</a>	
		<%
		}
		//======================================end cuti=============================================================				
				
		//=====================pengajuan pindah jurusan===========================
		
		full_nm_pengajuan_table_rules = "PINDAH_PRODI_RULES";
		if(true) {
			no_item=false;
		//if(sdmn.isTherePengajuanCuti(v_scope_id)) {	
		//if(true) {	 	
				//get.listPengajuan?fullnm_tabel_rules=<%=full_nm_pengajuan_table_rules &target_thsms=<%=Checker.getThsmsHeregistrasi() 
					%>
					<a href="go.moPp?target_thsms=<%=Checker.getThsmsHeregistrasi()%>&folder_pengajuan=pindah_prodi&scope=reqPindahJurusan&table=PINDAH_PRODI_RULES" target="inner_iframe" class="tile bg-orange fg-white" data-role="tile">
						<div class="tile-content">
		            		<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/pp.jpg" data-role="fitImage" data-format="square">
		            	</div>
		            	<div style="margin:5px 0 0 10px;position: absolute;">   
		               		<p class="no-margin text-shadow">PENGAJUAN<br/><span class="text-bold">PINDAH PRODI</span></p>
		             	</div>
		  	       	</a>	
		<%
		}

		//=====================pengajuan kelulusan===========================
		
		////System.out.println("size=="+v_scope_id.size());
		full_nm_pengajuan_table_rules = "KELULUSAN_RULES";
		if(true) {
			no_item=false;
		//if(sdmn.isTherePengajuanCuti(v_scope_id)) {	
		//if(true) {	 	
				//get.listPengajuan?fullnm_tabel_rules=<%=full_nm_pengajuan_table_rules &target_thsms=<%=Checker.getThsmsHeregistrasi() 
					%>
					<a href="go.moPp?target_thsms=<%=Checker.getThsmsHeregistrasi()%>&folder_pengajuan=kelulusan&scope=lulus&table=KELULUSAN_RULES" target="inner_iframe" class="tile bg-orange fg-white" data-role="tile">
						<div class="tile-content">
		            		<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/grad.jpg" data-role="fitImage" data-format="square">
		            	</div>
		            	<div style="margin:5px 0 0 10px;position: absolute;">   
		               		<p class="no-margin text-shadow">PENGAJUAN<br/><span class="text-bold">KELULUSAN</span></p>
		             	</div>
		  	       	</a>	
		<%
		}		
		
		//=====================prodi yg belum mengajukan kelas===========================
				String scope_cmd= "reqBukaKelas";
				//v_scope_id = validUsr.returnScopeProdiOnlySortByKampusWithListIdobj(scope_cmd); 
				//Vector v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
				//if(sdmn.getNotificationPengajuanCuti()) {
				if(true) {	
				//if(sdmn.isThereProdiYgBelumMengajukanKelasPerkuliahan_v2(v_scope_kdpst)) {	
					no_item=false;
					%>
					<a href="prep.infoListProdiNoClass?scope_cmd=<%=scope_cmd %>" target="inner_iframe" class="tile bg-orange fg-white" data-role="tile">
						<div class="tile-content">
		            		<img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/kelas1.jpg" data-role="fitImage" data-format="square">
		            	</div>
		            	<div style="margin:105px 0 0 55px;position: absolute;">   
		               		<p class="no-margin text-shadow">PENGAJUAN<br/><span class="text-bold">KELAS</span><br/>PERKULIAHAN</p>
		             	</div>
		  	       	</a>	
		<%
				}
		
		//==================OVERVIEW_SEBARAN_TRLSM===========================
		if(false) {
                        	%>
            <a href="#" target="inner_iframe" class="tile bg-yellow fg-white" data-role="tile">            	
          		<div class="tile-content iconic">
                	<span class="icon mif-users"></span>
                </div>
                    <span class="tile-label">Pengajuan MHS</span>
            </a>           	
<%
		}
		//====if no item====
		if(no_item) {
			%>
           <div class="tile-wide bg-grey fg-white">         	
          		<div style="margin:15px 0 0 10px;position: absolute;">   
                	<p class="no-margin text-shadow" style="color:#555a5e"><span class="text-bold">
                	BAGIAN NOTIFIKASI INI BERFUNGSI UNTUK MENAMPILKAN SECARA OTOMATIS, DENGAN MENAMPILKAN ICON-ICON TERKAIT DENGAN INFORMASI SEPERTI STATUS PENGAJUAN, PESAN BARU, KEHADIRAN DOSEN, DLL. 
                	</span><br/></p>
               	</div>
            </div>           	
<%
		}
		
		
		
		//==================END OVERVIEW_SEBARAN_TRLSM===========================
		%>

     	</div>
  	</div>     
    <%
    if(!validUsr.iAmStu()) {
    	
    	String thsms_reg = Checker.getThsmsHeregistrasi();
    %>
	<div class="tile-group quadro">
   		<span class="tile-group-title">Overview</span>
        <div class="tile-container">
                
            <a class="tile bg-darkOrange fg-white" data-role="tile" href="get.sebaranTrlsm?target_thsms=<%=thsms_reg %>" target="inner_iframe">    
        		<div style="margin:10px 0 0 22px;position: absolute;">   
               		<p class="no-margin text-shadow">SUMMARY <span class="text-bold" id="pressure">MHS</span></p>
				</div>
				<div style="margin:35px 0 0 13px;position: absolute;">   
               		<p class="no-margin text-shadow">MAHASISWA<span class="text-bold" id="pressure"> BARU  
                	</p>
				</div>
				<div style="margin:52px 0 0 23px;position: absolute;">   
               		<p class="no-margin text-shadow">DAFTAR<span class="text-bold" id="pressure"> ULANG  
                	</p>
				</div>
				<div style="margin:69px 0 0 33px;position: absolute;">   
               		<p class="no-margin text-shadow">AKTIF/<span class="text-bold" id="pressure">CUTI</span></p>
				</div>
				<div style="margin:86px 0 0 53px;position: absolute;">   
               		<p class="no-margin text-shadow"><span class="text-bold" id="pressure">LULUS</span></p>
				</div>
				<div style="margin:103px 0 0 33px;position: absolute;">   
               		<p class="no-margin text-shadow">KELUAR/<span class="text-bold" id="pressure">DO</span></p>
				</div>
				<div style="margin:120px 0 0 23px;position: absolute;">   
               		<p class="no-margin text-shadow">PINDAH<span class="text-bold" id="pressure"> PRODI</span></p>
				</div>
				
          	</a>
          
          <%	
            //===============buka kelas perkuliahan=============
	  //if(!validUsr.iAmStu()) {	
		scope_cmd= "viewAbsen";
		
		if(true) {
		%>	
   							
   			<div class="tile-wide bg-orange fg-white" data-role="tile">
            	<div class="tile-content image-set">
            	
            		<a href="get.statusPengajuanKelasKuliah?atMenu=form&scope_cmd=<%=scope_cmd %>&backTo=get.notifications" target="inner_iframe"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/classroom3.jpg" data-role="fitImage" data-format="fill"></a>
            		<!--  a href="prep.dataReBukaKelas" target="inner_iframe"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/classroom3.jpg" data-role="fitImage" data-format="fill"></a-->
                
                </div>
                <div style="margin:0 0 0 160px;position: absolute;">   
                	<p id="city_weather_daily"></p> 
                    <p class="no-margin text-shadow"><span class="text-bold">KELAS PERKULIAHAN</span></p>
                </div>
          	</div>
   		</div>		
   			<%
   		}
		//}
	  	//=============end buka kelas perkuliahan=============
        %>    	
       

        <!--  div class="tile-group one">
            <span class="tile-group-title">Office</span>

            <div class="tile-small bg-blue" data-role="tile">
                <div class="tile-content iconic">
                    <img src="../images/outlook.png" class="icon">
                </div>
            </div>
            <div class="tile-small bg-darkBlue" data-role="tile">
                <div class="tile-content iconic">
                    <img src="../images/word.png" class="icon">
                </div>
            </div>
            <div class="tile-small bg-green" data-role="tile">
                <div class="tile-content iconic">
                    <img src="../images/excel.png" class="icon">
                </div>
            </div>
            <div class="tile-small bg-red" data-role="tile">
                <div class="tile-content iconic">
                    <img src="../images/access.png" class="icon">
                </div>
            </div>
            <div class="tile-small bg-orange" data-role="tile">
                <div class="tile-content iconic">
                    <img src="../images/powerpoint.png" class="icon">
                </div>
            </div>
        </div>

        <div class="tile-group double">
            <span class="tile-group-title">Games</span>
            <div class="tile-container">
                <div class="tile" data-role="tile">
                    <div class="tile-content">
                        <img src="../images/grid2.jpg" data-role="fitImage" data-format="square">
                    </div>
                </div>
                <div class="tile-small" data-role="tile">
                    <div class="tile-content">
                        <img src="../images/Battlefield_4_Icon.png" data-role="fitImage" data-format="square">
                    </div>
                </div>
                <div class="tile-small" data-role="tile">
                    <div class="tile-content">
                        <img src="../images/Crysis-2-icon.png" data-role="fitImage" data-format="square" data-frame-color="bg-steel">
                    </div>
                </div>
                <div class="tile-small" data-role="tile">
                    <div class="tile-content">
                        <img src="../images/WorldofTanks.png" data-role="fitImage" data-format="square" data-frame-color="bg-dark">
                    </div>
                </div>
                <div class="tile-small" data-role="tile">
                    <div class="tile-content">
                        <img src="../images/halo.jpg" data-role="fitImage" data-format="square">
                    </div>
                </div>
                <div class="tile-wide bg-green fg-white" data-role="tile">
                    <div class="tile-content iconic">
                        <img src="../images/x-box.png" class="icon">
                    </div>
                    <div class="tile-label">X-Box Live</div>
                </div>
            </div>
        </div>

        <div class="tile-group double">
            <span class="tile-group-title">Other</span>
            <div class="tile-container">
                <div class="tile bg-teal fg-white" data-role="tile">
                    <div class="tile-content iconic">
                        <span class="icon mif-pencil"></span>
                    </div>
                    <span class="tile-label">Editor</span>
                </div>
                <div class="tile bg-darkGreen fg-white" data-role="tile">
                    <div class="tile-content iconic">
                        <span class="icon mif-shopping-basket"></span>
                    </div>
                    <span class="tile-label">Store</span>
                </div>
                <div class="tile bg-cyan fg-white" data-role="tile">
                    <div class="tile-content iconic">
                        <span class="icon mif-skype"></span>
                    </div>
                    <div class="tile-label">Skype</div>
                </div>
                <div class="tile bg-darkBlue fg-white" data-role="tile">
                    <div class="tile-content iconic">
                        <span class="icon mif-cloud"></span>
                    </div>
                    <span class="tile-label">OneDrive</span>
                </div>
            </div>
        </div-->
    </div>
	<%
	}
	%>

</body>
</html>
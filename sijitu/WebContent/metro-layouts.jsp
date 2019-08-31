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
   <meta charset="utf-8" />
   <!-- Always force latest IE rendering engine (even in intranet) & Chrome Frame -->
   <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
   <!-- Mobile viewport optimized: h5bp.com/viewport -->
   <meta name="viewport" content="width=device-width">

   <title>BootMetro Demo - Metro</title>

   <meta name="robots" content="noindex, nofollow">
   <meta name="description" content="BootMetro : Simple and complete web UI framework to create web apps with Windows 8 Metro user interface." />
   <meta name="keywords" content="bootmetro, modern ui, modern-ui, metro, metroui, metro-ui, metro ui, windows 8, metro style, bootstrap, framework, web framework, css, html" />
   <meta name="author" content="AozoraLabs by Marcello Palmitessa"/>
   <link rel="publisher" href="https://plus.google.com/117689250782136016574">

   <!-- remove or comment this line if you want to use the local fonts -->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700' rel='stylesheet' type='text/css'>

   <link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/bootmetro/assets/css/bootmetro.css">
   <link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/bootmetro/assets/css/bootmetro-responsive.css">
   <link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/bootmetro/assets/css/bootmetro-icons.css">
   <link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/bootmetro/assets/css/bootmetro-ui-light.css">
   <link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/bootmetro/assets/css/datepicker.css">

   <!--  these two css are to use only for documentation -->
   <link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/bootmetro/assets/css/demo.css">

   <!-- Le fav and touch icons -->
   <link rel="shortcut icon" href="<%=Constants.getRootWeb() %>/bootmetro/assets/ico/favicon.ico">
   <link rel="apple-touch-icon-precomposed" sizes="144x144" href="<%=Constants.getRootWeb() %>/bootmetro/assets/ico/apple-touch-icon-144-precomposed.png">
   <link rel="apple-touch-icon-precomposed" sizes="114x114" href="<%=Constants.getRootWeb() %>/bootmetro/assets/ico/apple-touch-icon-114-precomposed.png">
   <link rel="apple-touch-icon-precomposed" sizes="72x72" href="<%=Constants.getRootWeb() %>/bootmetro/assets/ico/apple-touch-icon-72-precomposed.png">
   <link rel="apple-touch-icon-precomposed" href="<%=Constants.getRootWeb() %>/bootmetro/assets/ico/apple-touch-icon-57-precomposed.png">
  
   <!-- All JavaScript at the bottom, except for Modernizr and Respond.
      Modernizr enables HTML5 elements & feature detects; Respond is a polyfill for min/max-width CSS3 Media Queries
      For optimal performance, use a custom Modernizr build: www.modernizr.com/download/ -->
   <script src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/modernizr-2.6.2.min.js"></script>

   <script type="text/javascript">
      var _gaq = _gaq || [];
      _gaq.push(['_setAccount', 'UA-3182578-6']);
      _gaq.push(['_trackPageview']);
      (function() {
         var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
         ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
         var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
      })();
   </script>
</head>

<body>
   <!--[if lt IE 7]>
   <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
   <![endif]-->

   <!-- Header
   ================================================== -->
   <header id="nav-bar" class="container">
      <div class="row">
         <div class="span12">
            <div id="header-container">
               <a id="backbutton" class="win-backbutton" href="<%=Constants.getRootWeb() %>/bootmetro/demo/hub.html"></a>
               <h5>BootMetro</h5>
               <div class="dropdown">
                  <a class="header-dropdown dropdown-toggle accent-color" data-toggle="dropdown" href="#" >
                     Metro Layouts
                     <b class="caret"></b>
                  </a>
                  <ul class="dropdown-menu">
                  <li><a href="<%=Constants.getRootWeb() %>/bootmetro/demo/metro-layouts.html">Metro Layouts</a></li>
                  <li><a href="<%=Constants.getRootWeb() %>/bootmetro/<%=Constants.getRootWeb() %>/bootmetro/demo/hub.html">Hub</a></li>
                  <li><a href="<%=Constants.getRootWeb() %>/bootmetro/<%=Constants.getRootWeb() %>/bootmetro/demo/tiles-templates.html">Tile Templates</a></li>
                  <li><a href="<%=Constants.getRootWeb() %>/bootmetro/<%=Constants.getRootWeb() %>/bootmetro/demo/listviews.html">ListViews</a></li>
                  <li><a href="<%=Constants.getRootWeb() %>/bootmetro/<%=Constants.getRootWeb() %>/bootmetro/demo/appbar-demo.html">App-Bar Demo</a></li>
                  <li><a href="<%=Constants.getRootWeb() %>/bootmetro/<%=Constants.getRootWeb() %>/bootmetro/demo/table.html">Table Demo</a></li>
                  <li><a href="<%=Constants.getRootWeb() %>/bootmetro/<%=Constants.getRootWeb() %>/bootmetro/demo/wizard.html">Wizard</a></li>
                  <li><a href="./icons.html">Icons</a></li>
                  <li><a href="./toast.html">Toast Notifications</a></li>
                  <li><a href="./pivot.html">Pivot</a></li>
                  <li><a href="./metro-components.html">Metro Components</a></li>
                  <li class="divider"></li>
                  <li><a href="./scaffolding.html">Bootstrap Scaffolding</a></li>
                  <li><a href="./base-css.html">Bootstrap Base CSS</a></li>
                  <li><a href="./components.html">Bootstrap Components</a></li>
                  <li><a href="./javascript.html">Bootstrap Javascript</a></li>
                  <li class="divider"></li>
                  <li><a href="./index.html">Home</a></li>
               </ul>
            </div>
            </div>
            <div id="top-info" class="pull-right">
            <a id="settings" href="#" class="win-command pull-right">
               <span class="win-commandicon win-commandring icon-cog-3"></span>
            </a>
            <a id="logged-user" href="#" class="win-command pull-right">
               <span class="win-commandicon win-commandring icon-user"></span>
            </a>
            <div class="pull-left">
               <h3>FirstName</h3>
               <h4>LastName</h4>
            </div>
         </div>
      </div>
      </div>
   </header>
   
   <div class="container">
      <div class="row">
         <div class="metro span12">
            <div class="metro-sections">
   
               <div class="metro-section" id="section1">
   
                  <a class="tile wide imagetext bg-color-orange" href="<%=Constants.getRootWeb() %>/bootmetro/demo/list-detail.html">
                     <div class="image-wrapper">
                        <img src="<%=Constants.getRootWeb() %>/bootmetro/assets/img/metro-tiles.jpg" />
                     </div>
                     <div class="column-text">
                        <div class="text-header3">Listview with Details</div>
                     </div>
                  </a>
   
                  <a class="tile wide imagetext tile-double bg-color-green" href="#">
                     <div class="image-wrapper">
                        <img src="<%=Constants.getRootWeb() %>/bootmetro/assets/img/metro-tiles-wide.jpg" />
                     </div>
                     <div class="column-text">
                        <div class="text-header3">...more to come...</div>
                     </div>
                  </a>
   
               </div>
   
            </div>
         </div>
      </div>
   </div>   <div id="charms" class="win-ui-dark slide">
   
      <div id="theme-charms-section" class="charms-section">
         <div class="charms-header">
            <a href="#" class="close-charms win-backbutton"></a>
            <h2>Settings</h2>
         </div>
   
         <div class="row-fluid">
            <div class="span12">
   
               <form class="">
                  <label for="win-theme-select">Change theme:</label>
                  <select id="win-theme-select" class="">
                     <option value="metro-ui-light">Light</option>
                     <option value="metro-ui-dark">Dark</option>
                  </select>
               </form>
   
            </div>
         </div>
      </div>
   
   </div>

   <!-- Grab Google CDN's jQuery. fall back to local if necessary -->
   <script src="//code.jquery.com/jquery-1.10.0.min.js"></script>
   <script>window.jQuery || document.write("<script src='assets/js/jquery-1.10.0.min.js'>\x3C/script>")</script>

   <!--[if IE 7]>
   <script type="text/javascript" src="scripts/bootmetro-icons-ie7.js">
   <![endif]-->

   <script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/min/bootstrap.min.js"></script>
   <script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/bootmetro-panorama.js"></script>
   <script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/bootmetro-pivot.js"></script>
   <script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/bootmetro-charms.js"></script>
   <script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/bootstrap-datepicker.js"></script>

   <script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/jquery.mousewheel.min.js"></script>
   <script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/jquery.touchSwipe.min.js"></script>

   <script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/holder.js"></script>
   <!--<script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/perfect-scrollbar.with-mousewheel.min.js"></script>-->
   <script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/demo.js"></script>


   <script type="text/javascript">

      $('.panorama').panorama({
         //nicescroll: false,
         showscrollbuttons: true,
         keyboard: true,
         parallax: true
      });

//      $(".panorama").perfectScrollbar();

      $('#pivot').pivot();

   </script>
</body>
</html>
<!DOCTYPE html>
<head>
<%
//System.out.println("sini dong");
%>
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>
	<%@ page import="org.codehaus.jettison.json.JSONArray" %>
	<%@ page import="org.codehaus.jettison.json.JSONObject" %>
	<%@ page import="org.codehaus.jettison.json.JSONException" %>
	<%@ page import="org.owasp.esapi.ESAPI" %>

	<title>UNIVERSITAS SATYAGAMA</title>
	<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<meta name="robots" content="index, follow" />
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/2colsLayout/screen.css" media="screen" />
	<link rel="icon" href="/favicon.ico">
  	<link rel="stylesheet" type="text/css" media="all" href="<%=Constants.getRootWeb()%>/expSearchfield/demo/styles.css">
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/js/accordion/accordion_left.css" media="screen" />
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/accordion/accordion-left.js"></script>
	<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
  	<script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
  
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/breakIframe/breakIframe.js"></script>
	<script type="text/javascript">
    
	
	if (top.location!= self.location) {
        top.location = self.location.href
                   //or you can use your logout page as
                   //top.location='logout.jsp' here...
    }
	</script>
	<script>		
		$(document).ready(function() {
			$("#wait").hide();
			$("#main").show();
		});	
	</script>
	<%
	
	//System.out.println("sampe sini");
	Vector vTmp = null;
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	//System.out.println("validUsr sini2= "+validUsr.toString());
%>
<style>
.scroll-wrapper {
 // position: fixed; 
 // right: 0; 
 // bottom: 0; 
 // left: 0;
 // top: 0;
  -webkit-overflow-scrolling: touch;
  overflow-y: scroll;
}

.scroll-wrapper iframe {
  //height: 100%;
 // width: 100%;
}
</style>
	<script>	
	   	$(document).on("click", "#home1", function() {
        	$.ajax({
        		url: 'index.jsp',
        		type: 'POST',
        		data: {},
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	        <%
        	        //String target = Constants.getRootWeb()+"/InnerFrame/Monitoring/toListMhsProfilIncomplete.jsp";
        			//String uri = request.getRequestURI();
        			//String url = PathFinder.getPath(uri, target);
        	        %>
        	        //top.location.href = "get.notifications";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });	
        
    </script>	
	
	<script>
		function myFunction() {
			$("#wait").show();
			
			
			//$("#main").hide();
			
			//onload="scroll(0,0);resize_iframe()"
			$(document).ready(function() {
				$("#wait").remove();
				scroll(0,0);
				resize_iframe();
				//$("#main").show();
			});
		}
		
			
	</script>
</head>
<body>
<%
    response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");//HTTP 1.1
    response.setHeader("Pragma","no-cache"); //HTTP 1.0
    response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
    
//if(hide_menu==null || !hide_menu.equalsIgnoreCase("true")) {
%>
<div id="header">
</div>
<div class="colmask fullpage">
	<div class="col1">
		<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
			<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai<br>[Lama proses bisa sampai 5 menit]</p>
		</div>
		<div id="main">
		</div>
	</div>
</div>	
</body>
</html>

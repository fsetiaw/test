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
		$("#keluarkanMhsLewatBtstu").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.keluarkanMhsLewatBtstu',
        		type: 'POST',
        		data: $("#keluarkanMhsLewatBtstu").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
		
		$("#cekMhsLewatBtstu").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.cekMhsLewatBtstu',
        		type: 'POST',
        		data: $("#cekMhsLewatBtstu").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
		
		$("#cekErrorKo").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.cekErrorKo',
        		type: 'POST',
        		data: $("#cekErrorKo").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
		
		$("#prepFormKo").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.prepFormKo',
        		type: 'POST',
        		data: $("#prepFormKo").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Pddikti/Kurikulum/PrepFormKoMhs.jsp";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
		
		
		$("#upKoMhs").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.setKoMhs',
        		type: 'POST',
        		data: $("#upKoMhs").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=&at_page=1&max_data_per_pg=20";
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
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
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
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20&mode=dobel";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
		
		$("#cekInputDobelTglhr").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.cekInputDobelTglhr',
        		type: 'POST',
        		data: $("#cekInputDobelTglhr").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
		
		$("#cekMhsStatusUnknown").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		//parent.scrollTo(0,0);
	   		$.ajax({
        		url: 'go.cekMhsStatusUnknown',
        		type: 'POST',
        		data: $("#cekMhsStatusUnknown").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	        //	window.location.href = "<>/InnerFrame/sql/ResultSet_v1.jsp";
        	        
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
		
		$("#cekMalaikatStatusUnknown").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.cekMalaikatStatusUnknown',
        		type: 'POST',
        		data: $("#cekMalaikatStatusUnknown").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	        //	window.location.href = "<>/InnerFrame/sql/ResultSet_v1.jsp";
        	        
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
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
	    	    	parent.scrollTo(0,0);
	    	        // this is where we append a loading image
	    	    },
	    	    success:function(data){
	    	        // successful request; do something with the data 
	    	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
	    	    },
	    	    error:function(){
	    	        // failed request; give feedback to user
	    	    }
	    	})
	    	return false;
	    });
		
		$("#copyNilaiRilKeColRobot").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.nilaiByRobot',
        		type: 'POST',
        		data: $("#copyNilaiRilKeColRobot").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	        //	window.location.href = "<>/InnerFrame/sql/ResultSet_v1.jsp";
        	        
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
		
		$("#gantiNilaiTundaByRobot").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.gantiNilaiTundaByRobot',
        		type: 'POST',
        		data: $("#gantiNilaiTundaByRobot").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	        //	window.location.href = "<>/InnerFrame/sql/ResultSet_v1.jsp";
        	        
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
		
		$("#cekErrorDataKemahasiswaan").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.cekErrorDataKemahasiswaan',
        		type: 'POST',
        		data: $("#cekErrorDataKemahasiswaan").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
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
		
		$("#insKrsBasedOnKo").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.insKrsBasedOnKo',
        		type: 'POST',
        		data: $("#insKrsBasedOnKo").serialize(),
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
		
		$("#hitungTrakmKolomRobot").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
	    		url: 'go.hitungTrakmKolomRobot',
	    		type: 'POST',
	    		data: $("#hitungTrakmKolomRobot").serialize(),
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
	
		
	   	$("#setIpkMinimal").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.setIpkMinimal',
        		type: 'POST',
        		data: $("#setIpkMinimal").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "#";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
		
		
	   	$("#calcTrakm").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.calcTrakm',
        		type: 'POST',
        		data: $("#calcTrakm").serialize(),
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
	   	
	   	$("#updateRiwayatKrsContinu").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.updateRiwayatKrsContinu',
        		type: 'POST',
        		data: $("#updateRiwayatKrsContinu").serialize(),
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
	   	
	   	
	   	$("#getListKdmkAdaDiTrlmnTidakAdaDiMakul").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.getListKdmkAdaDiTrlmnTidakAdaDiMakul',
        		type: 'POST',
        		data: $("#getListKdmkAdaDiTrlmnTidakAdaDiMakul").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	   	
	   	$("#updateSksmkTrnlp").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.updateSksmkTrnlp',
        		type: 'POST',
        		data: $("#updateSksmkTrnlp").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	   	
	   	$("#cekKrsMax").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.cekKrsMax',
        		type: 'POST',
        		data: $("#cekKrsMax").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	   	
	   	$("#cekNoija").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.cekNoija',
        		type: 'POST',
        		data: $("#cekNoija").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	window.location.href = "ResultSet.jsp";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	   	
	   	$("#cekMhsDobel").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.cekMhsDobel',
        		type: 'POST',
        		data: $("#cekMhsDobel").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	window.location.href = "ResultSet.jsp";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	   	
	   	
	   	$("#getMhsDibawahIpMin").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
	   			url: 'go.getMhsDibawahIpMin',
	   			//url: 'go.setStmhsTdkAdaKabar',
        		type: 'POST',
        		data: $("#getMhsDibawahIpMin").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";\
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	   	
	   	$("#deleteKelasKosong").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
	   			url: 'go.deleteKelasKosong',
	   			//url: 'go.setStmhsTdkAdaKabar',
        		type: 'POST',
        		data: $("#deleteKelasKosong").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";\
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	   	
	   	$("#deleteAfterOut").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
	   			url: 'go.deleteRecAfterOut',
	   			//url: 'go.setStmhsTdkAdaKabar',
        		type: 'POST',
        		data: $("#deleteAfterOut").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";\
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });	   	
	   	
	   	$("#setBlawlBlakh").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
	   			url: 'go.setBlawlBlakh',
	   			//url: 'go.setStmhsTdkAdaKabar',
        		type: 'POST',
        		data: $("#setBlawlBlakh").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";\
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	   	
	   	
	   	$("#fixErrorStatusKeluarBeforeSmawl").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
	   			url: 'go.fixErrorStatusKeluarBeforeSmawl',
	   			//url: 'go.setStmhsTdkAdaKabar',
        		type: 'POST',
        		data: $("#fixErrorStatusKeluarBeforeSmawl").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";\
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	   	
	   	$("#setSksttTrakmLulusan").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
	   			url: 'go.setSksttTrakmLulusan',
	   			//url: 'go.setStmhsTdkAdaKabar',
        		type: 'POST',
        		data: $("#setSksttTrakmLulusan").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";\
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	   	
	   	$("#setJudulTa").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
	   			url: 'go.setJudulTa',
	   			//url: 'go.setStmhsTdkAdaKabar',
        		type: 'POST',
        		data: $("#setJudulTa").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";\
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	   	
	   	$("#assignMhsKeKelasEpsbed").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
	   			url: 'go.assignMhsKeKelasEpsbed',
	   			//url: 'go.setStmhsTdkAdaKabar',
        		type: 'POST',
        		data: $("#assignMhsKeKelasEpsbed").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";\
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Pddikti/Mhs/setting_mhs_v2.jsp?backTo=<%=Constants.getRootWeb()+"/" %>InnerFrame/standalone_proses/index_stp.jsp";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	   	
	   	
	   	$("#errMhsAdaRecAfterOut").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
	   			url: 'go.errMhsAdaRecAfterOut',
	   			//url: 'go.setStmhsTdkAdaKabar',
        		type: 'POST',
        		data: $("#errMhsAdaRecAfterOut").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";\
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	   	
	   	
	   	$("#cekFinalMkLulusan").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
	   			url: 'go.cekFinalMkLulusan',
	   			//url: 'go.setStmhsTdkAdaKabar',
        		type: 'POST',
        		data: $("#cekFinalMkLulusan").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";\
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
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
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Pddikti/Dosen/setting_dosen_v2.jsp?backTo=<%=Constants.getRootWeb()+"/" %>InnerFrame/standalone_proses/index_stp.jsp";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	   	
	   	$("#setStmhsTdkAdaKabar").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
	   			url: 'go.cekMhsStatusUnknown',
	   			//url: 'go.setStmhsTdkAdaKabar',
        		type: 'POST',
        		data: $("#setStmhsTdkAdaKabar").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	    	parent.scrollTo(0,0);
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";\
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v4.jsp?backto=<%=Constants.getRootWeb() %>/InnerFrame/standalone_proses/index_stp.jsp&at_page=1&max_data_per_pg=20";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	   	
	   	$("#setGroup1").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.setGroup1',
        		type: 'POST',
        		data: $("#setGroup1").serialize(),
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
	   	
	   	$(document).on("click", "#cekKonflikKrsStatus", function() {
        	$.ajax({
        		url: 'go.cekKonflikKrsStatus',
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
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/sql/ResultSet_v3.jsp";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	   	
	   	$(document).on("click", "#maintenanceDataKemahasiswaan", function() {
        	$.ajax({
        		url: 'go.maintenanceDataKemahasiswaan',
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
        	    	window.location.href = "summary_info.jsp";
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

</head>
<body style="overflow-y: hidden;">
    <!--  a href="index.jsp">index</a--><br>
   
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai</p>
</div>
<div id="main">
	<div class="tile-area tile-area-scheme-dark fg-white" style="height: 100%; max-height: 100% !important;">
    <%
    %>  
        <div class="tile-group five">
			<span class="tile-group-title">DATA AWAL SEMESTER</span>
			<div class="tile-container">
				<!--  a  href="#" id="hitungSksdi" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">
            		<div class="padding20">
                	<p class="no-margin text-shadow"><span class="text-bold">HITUNG SKSDI SELURUH MAHASISWA BERDASARKAN TOTAL SKS MATAKULIAH P.T. ASAL (ONE TIME)</span></p>
					</div>
				</a-->
				<div class="tile-wide bg-lime fg-white" data-role="tile">
            		<!--  form id="updateRiwayatKrsContinu" action="go.updateRiwayatKrsContinu" method="POST" -->
            		<form id="updateRiwayatKrsContinu">
            		<div style="margin:5px 0 0 3px;position: absolute;">  
            		<section style="font-weight:bold">RESERVED FOR<br>INITIALIZE DB THSMS KRS</section>
            		<section style="padding:2px 0 0 9px">
            		* initialize overvieew<br>
            		* initialize Rules<br>
            		* update KALENDER AKADEMIK
					</section>
            		</div>
            		</form>
          		</div>
          		<div class="tile bg-darkRed fg-white" data-role="tile">
        			<form id="cekInputDobel">
            			<div style="margin:10px 0 0 5px;position: absolute;">  
            			<section style="font-weight:bold">(1)<br>CEK INPUT MHS DOBEL ANGKATAN:</section><br>
            			&nbsp&nbsp<input type="text" name="smawl" style="width:75px"/><br>
            			<p style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px;height:25px"/></p>
            			</div>
            		</form>
          		</div>
          		<div class="tile bg-darkRed fg-white" data-role="tile">
        			<form id="cekInputDobelTglhr">
        				<div style="margin:10px 0 0 0px;position: absolute;">  
            			<section style="font-weight:bold;text-align:center">CEK MHS TGL LAHIR SAMA DALAM 1 ANGKATAN</section>
						</div>
						<div style="margin:70px 0 0 15px;position: absolute;">THSMS</div>
            			<div style="margin:70px 0 0 65px;position: absolute;">:&nbsp<input type="text" name="smawl" value="" placeholder="" style="width:70px;text-align:center"/></div>
						<div style="margin:115px 0 0 13px;position: absolute;"><input type="submit" value="Next" style="width:125px;height:25px"/></p>
            			</div>
            			
            		</form>
          		</div> 
          		<div class="tile bg-amber fg-white" data-role="tile">
            		<!--  form id="updateRiwayatKrsContinu" action="go.updateRiwayatKrsContinu" method="POST" -->
            		<form id="updateSksmkTrnlp">
            		<div style="margin:5px 0 0 8px;position: absolute;"> 
            			<section style="font-weight:bold">FIX SKSMK TRNLP & UPDATE SKSDI ANGKATAN &nbsp:<br></section>
            		 	<input type="text" name="smawl" id="smawl"  style="width:75px"/><br>
            			<section style="font-weight:bold;font-size:0.7em">isi bila mo update sksdi [MK yg diakui sudah diisi terlebih dahulu]</section>
            		<br>
            		<section style="padding:2px 0 0 0px"><input type="submit" value="Next" style="width:125px;height:25px"/></section>
            		</div>
            		</form>
          		</div>
          		<div class="tile bg-darkBlue fg-white" data-role="tile">
            		<form id="cekErrorKo">
           	 			<div style="margin:10px 0 0 5px;position: absolute;">  
            			<section style="font-weight:bold">CEK MHS AKTIF TANPA KO</section><br>
            			THSMS : <input type="text" name="thsms" style="width:75px"/><br>
            			<p style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px;height:25px"/></p>
            			</div>
            		</form>
          		</div>
          		<div class="tile bg-darkBlue fg-white" data-role="tile">
            		<form id="prepFormKo">
           	 			<div style="margin:10px 0 0 5px;position: absolute;">  
            			<section style="font-weight:bold">LIHAT SEBARAN KO MHS</section><br>
            			PRODI &nbsp: <input type="text" name="kdpst" style="width:75px"/><br>
            			THSMS : <input type="text" name="thsms" style="width:75px"/><br>
            			<p style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px;height:25px"/></p>
            			</div>
            		</form>
          		</div>
          		<div class="tile bg-darkBlue fg-white" data-role="tile">
            		<form id="upKoMhs">
           	 			<div style="margin:10px 0 0 5px;position: absolute;">  
            			<section style="font-weight:bold">SET KO MHS</section><br>
            			LIST NPMHS &nbsp: <input type="text" name="tkn_npm" placeholder="npm1,npm2,..." style="width:135px"/><br>
            			ID KO &nbsp: <input type="number" name="idko" style="width:55px"/><br>
            			<p style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px;height:25px"/></p>
            			</div>
            		</form>
          		</div>
          		<% 
          		String scope_cmd= "s";
				Vector v_scope_id = validUsr.returnScopeProdiOnlySortByKampusWithListIdobj(scope_cmd); 
				if(v_scope_id!=null && v_scope_id.size()>0) {
			
					Vector v = validUsr.getScopeObjScope_vFinal(scope_cmd, true, false, true, "KDPST", null);
		%>	  
		  		<div class="tile-wide bg-teal fg-white" data-role="tile">
            		<form id="keluarkanMhsLewatBtstu">
           	 			<div style="margin:10px 0 0 5px;position: absolute;">  
            			<section style="font-weight:bold">SET STATUS KELUAR UNTUK MHS LEWAT BATAS STUDI</section>
            			</div>
            			<div style="margin:45px 0 0 40px;position: absolute;">PILIH PRODI :</div>
        	    		<div style="margin:40px 0 0 140px;position: absolute;">
        	    		<select name="kdpst" style="width:130px;height:25px;border:none;text-align-last:center">
        	    			<option value="null">PILIH PRODI</option>
        	    <%
        	    ListIterator li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs);
					String idobj = st.nextToken();
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
        	    %>
						</select>
        	    		</div>
        	    		<div style="margin:70px 0 0 40px;position: absolute;">
            			THSMS : 
            			</div>
            			<div style="margin:70px 0 0 140px;position: absolute;">
            			<input type="text" name="thsms" value="<%=Checker.getThsmsInputNilai() %>" style="width:130px;height:25px;border:none;text-align:center"/>
            			</div>
            			<div style="margin:110px 0 0 45px;position: absolute;">
            			<input type="submit" value="Next" style="width:225px;height:30px"/>
            			</div>
            		</form>
          		</div> 
                
            
		<%  
		}
		%>
          		<div class="tile bg-black fg-white" data-role="tile">
            		<form id="cekMalaikatStatusUnknown">
            			<div style="margin:5px 0 0 5px;position: absolute;">  
            			<section style="font-weight:bold">(2)<br>LIST MALAIKAT BELUM ADA STATUS AKHIR </section><br>
            			
            			THSMS &nbsp&nbsp: <input type="text" id="thsms" name="thsms" style="width:75px" placeholder="* wajib"/><br>
            			
            			<p style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px;height:25px"/></p>
            			</div>
            		</form>	
            	</div>
            	<div class="tile bg-black fg-white" data-role="tile">
            		<form id="cekMhsStatusUnknown">
            			<div style="margin:5px 0 0 5px;position: absolute;">  
            			<section style="font-weight:bold">(2)<br>LIST MHS BELUM ADA STATUS AKHIR (CEK YG VALID ONLY)</section><br>
            			
            			THSMS &nbsp&nbsp: <input type="text" id="thsms" name="thsms" style="width:75px" placeholder="* wajib"/><br>
            			
            			<p style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px;height:25px"/></p>
            			</div>
            		</form>	
            	</div>
            	<div class="tile bg-orange fg-white" data-role="tile">
            		<form id="insKrsBasedOnKo">
            			<div style="margin:5px 0 0 5px;position: absolute;">  
            			<section style="font-weight:bold">INSERT PAKET KRS MHS BARU [BUKAN PINDAHAN]</section><br>
            			THSMS &nbsp&nbsp: <input type="text" id="thsms" name="thsms" style="width:75px" placeholder="* wajib"/><br>
            			NPMHS &nbsp: <input type="text" id="npmhs" name="npmhs" placeholder="npm1`npm2`.." style="width:75px"/><br>
            			<div style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px;height:25px"/></div>
            			</div>
            		</form>	
            	</div>
          		<div class="tile bg-olive fg-white" data-role="tile">
            		<!--  form id="updateRiwayatKrsContinu" action="go.updateRiwayatKrsContinu" method="POST" -->
            		<form id="updateRiwayatKrsContinu">
            		<div style="margin:5px 0 0 3px;position: absolute;">  
            		<section style="font-weight:bold">INSERT KRS CONTINU<br>GIVEN</section>
            		(STMHS &nbsp: <input type="text" name="stmhs" id="stmhs" style="width:75px"/><br>
            		&nbspKDPST &nbsp&nbsp: <input type="text" id="kdpst" name="kdpst" style="width:75px"/>)<br>
            		Atau NPMHS :<br> <input type="text" id="npmhs" name="npmhs" style="width:145px"/><br>
            		
            		<section style="padding:2px 0 0 9px"><input type="submit" value="Next" style="width:125px;height:25px"/></section>
            		</div>
            		</form>
          		</div>
          		<%
          		if(validUsr.amI("ADMIN")) {
        	  		//no_item=false;
        	  		%>
        	  	<div class="tile bg-red fg-white" data-role="tile">	
        	  		<form id="getListKdmkAdaDiTrlmnTidakAdaDiMakul">
					<!--  form action="go.getListKdmkAdaDiTrlmnTidakAdaDiMakul?atMenu=kehadiran&from=home" method="post" target="_blank" -->
            			<div style="margin:10px 0 0 0px;position: absolute;">  
            			<section style="font-weight:bold;text-align:center">CEK KRS ERROR: <BR>MK TDK TERDAFTAR DI PRODI</section>
						</div>
						<div style="margin:60px 0 0 15px;position: absolute;">KDPST</div>
            			<div style="margin:60px 0 0 65px;position: absolute;">:&nbsp<input type="text" name="target_kdpst" value="" placeholder="optinal" style="width:70px;text-align:center"/></div>
						<div style="margin:85px 0 0 15px;position: absolute;">THSMS</div>
						<div style="margin:85px 0 0 65px;position: absolute;">:&nbsp<input type="text" name="target_thsms" value="" style="width:70px;text-align:center" required=required/></div>
						<div style="margin:115px 0 0 13px;position: absolute;"><input type="submit" value="Next" style="width:125px;height:25px"/></p>
            			</div>
            		</form>
				</div>	
        			<!--  a href="go.getListKdmkAdaDiTrlmnTidakAdaDiMakul?atMenu=kehadiran&from=home" target="inner_iframe" class="tile bg-darkTeal fg-white" data-role="tile">
                    	<div class="padding20">
                        	<p class="no-margin text-shadow"><span class="text-bold">KRS MAHASISWA YANG HARUS</span><br/>DIPERBAIKI</p>
        				</div>
        			</a-->       			
        	                	<%	
        	                	
        	  	}
          		%>
            	<div class="tile-wide bg-mauve fg-white" data-role="tile">
            		<form id="setStmhsTdkAdaKabar">
            			<div class="padding10">
                			<p class="no-margin text-shadow"><span class="text-bold">(3)<br>SET STMHS MHS YG TIDAK ADA KABAR MENJADI
                				&nbsp&nbsp<input type="text" id="stmhs" name="stmhs" style="width:30px" value="C"/>&nbsp
                				BERDASARKAN MAHASISWA AKTIF @THSMS :<input type="text" id="thsms" name="thsms" style="width:55px"/>
                				&nbsp <br>
                				(Dilakukan setiap mo pergantian semester & sebelum pelaporan pddikti)
                			</span><br>
                			<input type="submit" value="Next" style="width:115px;height:25px"/>
                			</p>
                		</div>               		
            		</form>	
            	</div>
            	<div class="tile bg-green fg-white" data-role="tile">
					<form id="errMhsAdaRecAfterOut">
            		<div style="margin:5px 0 0 10px;font-weight:bold;font-size:1.1em;position: absolute;"><span class="text-bold">CEK MHS YG SDH KELUAR/DO/LULUS TAPI ADA DATA BARU</span></div>
					<div style="margin:70px 0 0 10px;font-weight:bold;font-size:1em;position: absolute;">THSMS &nbsp: <input type="text" name="thsms" style="width:65px"/><br></div>
					<div style="margin:90px 0 0 10px;font-weight:bold;font-size:1em;position: absolute;">PRODI &nbsp&nbsp&nbsp: <input type="text" name="kdpst" style="width:65px" placeholder="*optional"/><br></div>
        	    	<div style="margin:115px 0 0 10px;font-weight:bold;font-size:1em;position: absolute;"><input type="submit" value="Next" style="width:125px;height:25px"/></div>
            		</form>	
            	</div>
            	<div class="tile bg-grayed fg-white" data-role="tile">
            		<form id="cekKrsMax">
            			<div class="padding10">
                			<p class="no-margin text-shadow"><span class="text-bold">CEK KRS LEBIH BESAR DARI<br>
                				<input type="text" id="sks" name="sks" style="width:50px" value=">24"/>&nbspsks<br>
                				@ THSMS :<input type="text" id="thsms" name="thsms" value="<%=Checker.getThsmsNow() %>" style="width:75px"/>
                			</span></p>
                		</div>
                		<div style="margin: 0 0 0 10px;position: absolute;">  	
                			<input type="submit" value="Next" style="width:115px;height:25px"/>
						</div>
            		</form>	
            	</div>
            	<div class="tile bg-crimson fg-white" data-role="tile">
            		<form id="copyNilaiRilKeColRobot">
            		<!--  form action="go.nilaiByRobot" method="POST" -->
            		<div class="padding10">
                		<p class="no-margin text-shadow">COPY NILAI RIL <span class="text-bold"><br>KE KOLOM BACKUP NILAI</span>
                		</p>
                		THSMS : <input type="text" name="thsms" style="width:75px" required/><br>
            			<section style="padding:5px 0 0 0px"><input type="submit" value="Next" style="width:125px;height:25px"/></section>
            		</div>
            		</form>
          		</div>
          		<div class="tile bg-steel fg-white" data-role="tile">
            		<form id="gantiNilaiTundaByRobot">
            		<!--  form action="go.nilaiByRobot" method="POST" -->
            		<div class="padding10">
                		<p class="no-margin text-shadow">NILAI BY ROBOT <span class="text-bold"><br>MATAKULIAH YG MASIH <br>NILAI TUNDA</span></p>
                		THSMS : <input type="text" name="thsms" style="width:75px" required/><br>
            			<section style="padding:5px 0 0 0px"><input type="submit" value="Next" style="width:125px;height:25px"/></section>
            		</div>
            		</form>
          		</div>
          		
          		<div class="tile-wide bg-lime fg-white" data-role="tile">
            		<form id="updateColRobotNilaiLessThenMin">
            		<div style="margin:5px 0 0 5px;position: absolute;">  
            		<p class="no-margin text-shadow">KASIH NILAI SEMENTARA <span class="text-bold">KOLOM ROBOT UNTUK MATAKULIAH DENGAN NILAI DIBAWAH MINIMAL (optional)</span></p>
            		THSMS &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp: <input type="text" id="thsms" name="thsms" style="width:76px"/><br>
            		BOBOT MIN &nbsp: <input type="text" id="nilai_min" name="nilai_min" style="width:75px"/><br>
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
            	<div class="tile bg-darkBlue fg-white" data-role="tile">
            		<form id="calcTrakm">
            			<div style="margin:5px 0 0 5px;position: absolute;">  
            			<section style="font-weight:bold">HITUNG KHS MHS</section><br>
            			PRODI &nbsp&nbsp&nbsp: <input type="text" id="kdpst" name="kdpst" style="width:75px"/><br>
            			THSMS &nbsp&nbsp: <input type="text" id="smawl" name="smawl" style="width:75px"/><br>
            			NPMHS &nbsp: <input type="text" id="npmhs" name="npmhs" style="width:75px"/><br>
            			<p style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px;height:25px"/></p>
            			</div>
            		</form>	
            	</div>
            	
            	<div class="tile-wide bg-mauve fg-white" data-role="tile">
            		<form id="getMhsDibawahIpMin">
            			<input type="hidden" name="cmd_scope" value="s"/>
            			<div style="margin:15px 0 0 5px;position: absolute;">  
            				<section style="font-weight:bold">GET LIST MAHASISWA DENGAN NILAI<br> 
            				<select name="tipe_ip" style="width:70px;border:none">
            					<option value="ipk">IPK</option>	
            				</select> &nbsp < &nbsp
            				<input type="text" id="ips_min" name="ips_min" value="0.1" style="width:65px" readonly="readonly"/>
            				<br>
            				ANGKATAN  &nbsp <&nbsp
            				<input type="text" id="smawl" name="smawl" style="width:65px" required="required" placeholder="* wajib"/>
            				<br> THSMS &nbsp&nbsp&nbsp&nbsp:&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp <input type="text" id="thsms" name="thsms" style="width:65px" placeholder="* wajib"/><br>
            				<!--  input type="checkbox" name="col_robot" value="true"> Nilai Sementara<br>
            				<input type="checkbox" name="angel" value="true"> [M]<br>
            				ADA DUA YA DI BAGIAN KRS & KHS  & STATUS MHS cuma disini semua yg di cek
            				-->
            				<input type="hidden" name="semua" value="true"/>
            				<div style="padding:5px 0 0 105px"><input type="submit" value="Next" style="width:125px;height:25px"/></div>
            			
                		</div>               		
            		</form>	
            	</div> 
            	<!--  div class="tile-wide bg-darkBlue fg-white" data-role="tile">
            		<form id="setIpkMinimal">
            			<input type="hidden" name="min_ipk_value" value="0" />
            			<div style="margin:5px 0 0 5px;position: absolute;">  
            			<section style="font-weight:bold">SET IPK BARU MHS ANG LALU JIKA NILAI IPK SEKARANG LEBIH KECIL DARI IPK MIN </section>
            			</div>
            			<div style="margin:40px 0 0 5px;position: absolute;">  
            			NILAI IPK MIN / BARU &nbsp: <input type="text" id="ipk_min" name="ipk_min" style="width:50px"/> / <input type="text" id="nu_ipk" name="nu_ipk" style="width:50px"/><br>
            			</div>
            			<div style="margin:65px 0 0 5px;position: absolute;">  
            			THSMS &nbsp&nbsp&nbsp&nbsp&nbsp: <input type="text" id="thsms" name="thsms" style="width:75px"/><br>
            			</div>
            			<div style="margin:90px 0 0 5px;position: absolute;">  
            			!!!BELUM BERFUNGSI !!!!!
            			</div>
            			<div style="margin:110px 0 0 100px;position: absolute;"><input type="submit" value="Next" style="width:125px;height:25px"/></div>
            		</form>	
            	</div -->
            	<!--   href="<%=Constants.getRootWeb() %>/InnerFrame/Pddikti/Mhs/setting_mhs_v1.jsp?cmd=ink&target_thsms=20152&backTo=get.notifications" target="inner_iframe" class="tile bg-black fg-white" data-role="tile">
            		<div class="padding20">
                	<p class="no-margin text-shadow">DAFTARKAN MAHASISWA<span class="text-bold"><br>KE DALAM KELAS<br>PERKULIAHAN</span><br/></p>
					</div>
				</a-->
				
				<div class="tile bg-green fg-white" data-role="tile">
					<form id="cekFinalMkLulusan">
            		<div style="margin:15px 0 0 10px;font-weight:bold;font-size:1.1em;position: absolute;"><span class="text-bold">CEK LULUSAN TANPA MK UJIAN AKHIR</span></div>
					<div style="margin:75px 0 0 10px;font-weight:bold;font-size:1em;position: absolute;">THSMS &nbsp: <input type="text" id="thsms_lulus" name="thsms_lulus" style="width:65px"/><br></div>
        	    	<div style="margin:100px 0 0 10px;font-weight:bold;font-size:1em;position: absolute;"><input type="submit" value="Next" style="width:125px;height:25px"/></div>
            		</form>	
            	</div>
				<div class="tile bg-lime fg-white" data-role="tile">
            		<form id="setDsnAndKlsPll">
           		 		<input type="hidden" name="dari" value="index"/>
            			<div style="margin:10px 0 0 3px;position: absolute;">  
            				<section style="font-weight:bold">(4)<br>SET KELAS PARALEL & DOSEN PENGAJAR</section><br>
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
				<div class="tile bg-taupe fg-white" data-role="tile">
					<form id="assignMhsKeKelasEpsbed">
            		<!--  form action="<%=Constants.getRootWeb() %>/InnerFrame/Pddikti/Mhs/setting_mhs_v1.jsp" method="post" -->
            			<div style="margin:5px 0 0 10px;position: absolute;">  
            			(5)&nbsp&nbspSET MHS<span class="text-bold"><br>KE DALAM KELAS<br>PERKULIAHAN</span><br/>
            			THSMS &nbsp: <input type="text" id="target_thsms" name="target_thsms" style="width:65px"/><br>
            			KDPST &nbsp&nbsp: <input type="text" id="tkn_kdpst" name="tkn_kdpst" placeholder="*optional" style="width:65px"/><br>
            			<div style="font-size:0.8em"> kosong = semua prodi</div>
            			<div style="font-size:0.8em"> (15 mnt)1200 row/min</div>
            			<div style="font-size:0.8em"><input type="submit" value="Next" style="width:125px;height:25px"/></div>
            			</div>
            		</form>	
            	</div>
            	<div class="tile bg-taupe fg-white" data-role="tile">
					<form id="deleteKelasKosong">
            		<!--  form action="<%=Constants.getRootWeb() %>/InnerFrame/Pddikti/Mhs/setting_mhs_v1.jsp" method="post" -->
            			<div style="margin:5px 0 0 10px;position: absolute;">  
            			(6)&nbsp&nbsp<span class="text-bold"><br>DELETE KELAS KOSONG (TRAKD EPSBED)<br></span><br/>
            			THSMS &nbsp: <input type="text" id="target_thsms" name="target_thsms" style="width:65px"/><br>
            			<div style="font-size:0.8em"></div>
            			<p style="padding:0 0 0 0px"><input type="submit" value="Next" style="width:125px;height:25px"/></p>
            			</div>
            		</form>	
            	</div>
            	<a  href="#" id="maintenanceDataKemahasiswaan" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">
            		<div style="margin:10px 0 0 5px;font-size:0.9em;position: absolute;">1. Fix data </div>
            		<div style="margin:25px 0 0 20px;font-size:0.9em;position: absolute;">Kemahasiswaan</div>
					<div style="margin:45px 0 0 5px;font-size:0.9em;position: absolute;">2. Set NIM = NPM </div>
        	    	<div style="margin:65px 0 0 5px;font-size:0.9em;position: absolute;">3. Hapus Record Setelah </div>
        	    	<div style="margin:80px 0 0 20px;font-size:0.9em;position: absolute;">Keluar/Lulus/DO</div>
				</a>
				<div class="tile bg-taupe fg-white" data-role="tile">
					<form id="deleteAfterOut">
            		<div style="margin:10px 0 0 5px;font-weight:bold;font-size:1.1em;position: absolute;">Hapus Record Setelah THSMS Keluar/Lulus/DO</div>
            		<div style="margin:25px 0 0 20px;font-weight:bold;font-size:1.1em;position: absolute;"></div>
					<div style="margin:75px 0 0 5px;font-weight:bold;font-size:1em;position: absolute;">THSMS &nbsp: <input type="text" id="target_thsms" name="target_thsms" style="width:65px"/><br></div>
        	    	<div style="margin:100px 0 0 10px;font-weight:bold;font-size:1em;position: absolute;"><input type="submit" value="Next" style="width:125px;height:25px"/></div>
            		</form>	
            	</div> 
				<div class="tile bg-taupe fg-white" data-role="tile">
					<form id="setBlawlBlakh">
            		<!--  form action="<%=Constants.getRootWeb() %>/InnerFrame/Pddikti/Mhs/setting_mhs_v1.jsp" method="post" -->
            			<div style="margin:5px 0 0 10px;position: absolute;font-size:0.9em">  
            			<span class="text-bold">SET BULAN AWAL & AKHIR<br>BIMBINGAN BAGI MHS<br>YG LULUS</span><br/>
            			THSMS &nbsp: <input type="text" id="target_thsms" name="target_thsms" style="width:65px"/><br>
            			KDPST &nbsp&nbsp: <input type="text" id="tkn_kdpst" name="tkn_kdpst" placeholder="*optional" style="width:65px"/><br>
            			<div style="font-size:0.8em"> kosong = semua prodi</div>
            			<div style="font-size:0.8em"> (15 mnt)1200 row/min</div>
            			<div style="font-size:0.8em"><input type="submit" value="Next" style="width:125px;height:25px"/></div>
            			</div>
            		</form>	
            	</div>
            	<div class="tile bg-taupe fg-white" data-role="tile">
					<form id="setJudulTa">
            		<!--  form action="<%=Constants.getRootWeb() %>/InnerFrame/Pddikti/Mhs/setting_mhs_v1.jsp" method="post" -->
            			<div style="margin:5px 0 0 10px;position: absolute;font-size:0.9em">  
            			<span class="text-bold">SET JUDUL TUGAS AKHIR LULUSAN</span><br/>
            			THSMS &nbsp: <input type="text" id="target_thsms" name="target_thsms" style="width:65px"/><br>
            			KDPST &nbsp&nbsp: <input type="text" id="tkn_kdpst" name="tkn_kdpst" placeholder="*optional" style="width:65px"/><br>
            			<div style="font-size:0.8em"> kosong = semua prodi</div>
            			<div style="font-size:0.8em"> (15 mnt)1200 row/min</div>
            			<div style="font-size:0.8em"><input type="submit" value="Next" style="width:125px;height:25px"/></div>
            			</div>
            		</form>	
            	</div>
            	<div class="tile bg-taupe fg-white" data-role="tile">
					<form id="setSksttTrakmLulusan">
            		<!--  form action="<%=Constants.getRootWeb() %>/InnerFrame/Pddikti/Mhs/setting_mhs_v1.jsp" method="post" -->
            			<div style="margin:5px 0 0 10px;position: absolute;font-size:0.9em">  
            			<span class="text-bold">SET SKSTT TRAKM LULUSAN BERDASARKAN EXCEL LULUSAN (SKSTT TRLSM)</span><br/>
            			THSMS &nbsp: <input type="text" id="target_thsms" name="target_thsms" style="width:65px"/><br>
            			<div style="font-size:0.8em"><input type="submit" value="Next" style="width:125px;height:25px"/></div>
            			</div>
            		</form>	
            	</div>
            	<div class="tile bg-taupe fg-white" data-role="tile">
					<form id="fixErrorStatusKeluarBeforeSmawl">
            		<!--  form action="<%=Constants.getRootWeb() %>/InnerFrame/Pddikti/Mhs/setting_mhs_v1.jsp" method="post" -->
            			<div style="margin:5px 0 0 10px;position: absolute;font-size:0.9em">  
            			<span class="text-bold">FIX ERROR STATUS KELUAR SEBELUM SMAWL</span><br/>
            			STMHS &nbsp: <input type="text" id="stmhs" name="stmhs" style="width:65px"/><br>
            			SMAWL &nbsp&nbsp: <input type="text" id="smawl" name="smawl" style="width:65px"/><br>
            			<div style="font-size:0.8em"> SMAWL = based awal angkatan</div>
            			<div style="font-size:0.8em"> yang akan diperiksa</div>
            			<div style="font-size:0.8em"><input type="submit" value="Next" style="width:125px;height:25px"/></div>
            			</div>
            		</form>	
            	</div>
          		<!--
					!!!!!!!!!!!TERLAMPAU BERBAHAYA!!!!!!!!!!!!!!!
					 GUNAKAN 'CEK KONFLIK ANTARA STATUS AKHIR MAHASISWA DGN RIWAYAT KRS'
				<a  href="#" id="updateUsername" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">
            		<div class="padding20">
                	<p class="no-margin text-shadow"><span class="text-bold">DELETE KRS & KHS THSMS SETELAH</span><br/>- KELUAR<br>- LULUS<br>- DO</p>
					</div>
				</a>
				-->       			
				
            	

     		</div>
  		</div>    
    
        <div class="tile-group triple">
			<span class="tile-group-title">KRS & KHS & STATUS MHS</span>
			<div class="tile-container">
		<%
	  	%>
	  	
				
				<div class="tile bg-lime fg-white" data-role="tile">
            		<!--  form id="updateRiwayatKrsContinu" action="go.updateRiwayatKrsContinu" method="POST" -->
            		<form id="updateRiwayatKrsContinu">
            		<div style="margin:5px 0 0 3px;position: absolute;">  
            		<section style="font-weight:bold">INSERT KRS CONTINU<br>GIVEN</section>
            		(STMHS &nbsp: <input type="text" name="stmhs" id="stmhs" style="width:75px"/><br>
            		&nbspKDPST &nbsp&nbsp: <input type="text" id="kdpst" name="kdpst" style="width:75px"/>)<br>
            		Atau NPMHS :<br> <input type="text" id="npmhs" name="npmhs" style="width:145px"/><br>
            		
            		<section style="padding:2px 0 0 9px"><input type="submit" value="Next" style="width:125px;height:25px"/></section>
            		</div>
            		</form>
          		</div>
          		
          		
          		<!--
					!!!!!!!!!!!TERLAMPAU BERBAHAYA!!!!!!!!!!!!!!!
					 GUNAKAN 'CEK KONFLIK ANTARA STATUS AKHIR MAHASISWA DGN RIWAYAT KRS'
				<a  href="#" id="updateUsername" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">
            		<div class="padding20">
                	<p class="no-margin text-shadow"><span class="text-bold">DELETE KRS & KHS THSMS SETELAH</span><br/>- KELUAR<br>- LULUS<br>- DO</p>
					</div>
				</a>
				-->       			
				<div class="tile bg-darkBlue fg-white" data-role="tile">
            		<form id="calcTrakm">
            			<div style="margin:5px 0 0 5px;position: absolute;">  
            			<section style="font-weight:bold">HITUNG KHS MHS</section><br>
            			PRODI &nbsp&nbsp&nbsp: <input type="text" id="kdpst" name="kdpst" style="width:75px"/><br>
            			THSMS &nbsp&nbsp: <input type="text" id="smawl" name="smawl" style="width:75px"/><br>
            			NPMHS &nbsp: <input type="text" id="npmhs" name="npmhs" style="width:75px"/><br>
            			<p style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px;height:25px"/></p>
            			</div>
            		</form>	
            	</div>
            	
            	<div class="tile-wide bg-darkViolet fg-white" data-role="tile">
            		<form id="setStmhsTdkAdaKabar">
            			<div class="padding10">
                			<p class="no-margin text-shadow"><span class="text-bold">SET STMHS MHS YG TIDAK ADA KABAR MENJADI
                				&nbsp&nbsp<input type="text" id="stmhs" name="stmhs" style="width:30px" value="C"/>&nbsp
                				BERDASARKAN MAHASISWA AKTIF @THSMS :<input type="text" id="thsms" name="thsms" style="width:55px"/>
                				&nbsp <br>
                				(Dilakukan setiap mo pergantian semester & sebelum pelaporan pddikti)
                			</span><br>
                			<input type="submit" value="Next" style="width:115px;height:25px"/>
                			</p>
                		</div>
                		
            		</form>	
            	</div>
       			<!--  div class="tile bg-darkBlue fg-white" data-role="tile">
            		<form id="insKrsBasedOnKo">
            			<div style="margin:5px 0 0 5px;position: absolute;">  
            			<section style="font-weight:bold">INSERT PAKET KRS MHS BARU</section><br>
            			PRODI &nbsp&nbsp&nbsp: <input type="text" id="kdpst" name="kdpst" placeholder="Tdk Berfungsi" style="width:75px"/><br>
            			THSMS &nbsp&nbsp: <input type="text" id="thsms" name="thsms" style="width:75px" placeholder="* wajib"/><br>
            			NPMHS &nbsp: <input type="text" id="npmhs" name="npmhs" style="width:75px"/><br>
            			<div style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px;height:25px"/></div>
            			</div>
            		</form>	
            	</div-->
            	
            	<div class="tile bg-lime fg-white" data-role="tile">
            		<form id="cekMhsStatusUnknown">
            			<div style="margin:5px 0 0 5px;position: absolute;">  
            			<section style="font-weight:bold">LIST MHS BELUM ADA STATUS AKHIR </section><br>
            			
            			THSMS &nbsp&nbsp: <input type="text" id="thsms" name="thsms" style="width:75px" placeholder="* wajib"/><br>
            			
            			<p style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px;height:25px"/></p>
            			</div>
            		</form>	
            	</div>
            	<a  href="#" id="cekKonflikKrsStatus" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">
            		<div class="padding20">
                	<p class="no-margin text-shadow"><span class="text-bold">CEK KONFLIK ANTARA STATUS AKHIR MAHASISWA DGN RIWAYAT KRS</span></p>
					</div>
				</a> 
				<div class="tile-wide bg-mauve fg-white" data-role="tile">
            		<form id="getMhsDibawahIpMin">
            			<input type="hidden" name="cmd_scope" value="s"/>
            			<div style="margin:15px 0 0 5px;position: absolute;">  
            				<section style="font-weight:bold">GET LIST MAHASISWA DENGAN NILAI<br> 
            				<select name="tipe_ip" style="width:70px;border:none">
            					<option value="both">IPS/IPK</option>
            					<option value="ips">IPS</option>
            					<option value="ipk">IPK</option>	
            				</select> &nbsp < &nbsp
            				<input type="text" id="ips_min" name="ips_min" style="width:65px" placeholder="* wajib"/> 
            				<br> THSMS &nbsp&nbsp&nbsp&nbsp:&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp <input type="text" id="thsms" name="thsms" style="width:65px" placeholder="* wajib"/><br>
            				<input type="checkbox" name="col_robot" value="true"> Nilai Sementara<br>
            				<input type="checkbox" name="angel" value="true"> [M]<br>
            				<div style="padding:5px 0 0 105px"><input type="submit" value="Next" style="width:125px;height:25px"/></div>
            			
                		</div>               		
            		</form>	
            	</div> 
     		</div>
  		</div>     
    	
    	<div class="tile-group triple">
			<span class="tile-group-title">DATA LULUSAN & IJAZAH</span>
			<div class="tile-container">
				<a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/input_file/uploader_form.jsp?form=noijazah" target="inner_iframe" class="tile bg-darkTeal fg-white" data-role="tile">
            		<div class="padding20">
	                	<p class="no-margin text-shadow">EXCEL FILE <br><span class="text-bold">UPLOADER</p>
					</div>
				</a> 
				<div class="tile-wide bg-darkViolet fg-white" data-role="tile">
            		<form id="cekNoija">
            			<div class="padding10">
                			<p class="no-margin text-shadow"><span class="text-bold">CEK DATA LULUSAN :<br>
                			Filter berdasarkan : <br>
                				<select id="filter_tipe" name="filter_tipe" style="width:290px">
                					<option value="0">NO FILTER</option>
                					<option value="1">LULUSAN BELUM ADA NO IJAZAH</option>
                				</select>
                				<br>@ THSMS :<input type="text" id="thsms" name="thsms" style="width:75px"/>
                			</span></p>
                		</div>
                		<div style="margin: 0 0 0 10px;position: absolute;">  	
                			<input type="submit" value="Next" style="width:115px;height:25px"/>
						</div>
            		</form>	
            	</div>
			</div>
		</div>	
    	
    	<div class="tile-group triple">
			<span class="tile-group-title">DATA KEMAHASISWAAN & PINDAHAN</span>
			<div class="tile-container">
				<a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/input_file/uploader_form.jsp?form=nim" target="inner_iframe" class="tile bg-darkTeal fg-white" data-role="tile">
            		<div class="padding20">
	                	<p class="no-margin text-shadow">EXCEL NIM FILE <br><span class="text-bold">UPLOADER</p>
					</div>
				</a> 
				<div class="tile bg-darkBlue fg-white" data-role="tile">
            		<form id="cekErrorDataKemahasiswaan">
            			<div style="margin:5px 0 0 5px;position: absolute;">  
            			<section style="font-weight:bold">CEK ERROR DATA KEMAHASISWAAN</section><br>
            			PRODI &nbsp&nbsp&nbsp: <input type="text" id="kdpst" name="kdpst" placeholder="Tdk Berfungsi" style="width:75px"/><br>
            			ANGKATAN &nbsp&nbsp: <input type="text" id="smawl" name="smawl" style="width:75px" placeholder="* wajib"/><br>
            			NPMHS &nbsp: <input type="text" id="npmhs" name="npmhs" style="width:75px"/><br>
            			<div style="padding:0 0 0 8px"><input type="submit" value="Next" style="width:125px;height:25px"/></div>
            			</div>
            		</form>	
            	</div>
				<div class="tile bg-lime fg-white" data-role="tile">
            		<!--  form id="updateRiwayatKrsContinu" action="go.updateRiwayatKrsContinu" method="POST" -->
            		<form id="updateSksmkTrnlp">
            		<div style="margin:5px 0 0 3px;position: absolute;">  
            		<section style="font-weight:bold">FIX SKSMK TRNLP & UPDATE SKSDI ANGKATAN &nbsp:<br></section>
            		 <input type="text" name="smawl" id="smawl"  style="width:75px"/><br>
            		<section style="font-weight:bold;font-size:0.7em">isi bila mo update sksdi [MK yg diakui sudah diisi terlebih dahulu]</section>
            		<br><br>
            		<section style="padding:2px 0 0 9px"><input type="submit" value="Next" style="width:125px;height:25px"/></section>
            		</div>
            		</form>
          		</div>
				<a  href="#" id="maintenanceDataKemahasiswaan" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">
            		<div style="margin:10px 0 0 5px;font-size:0.9em;position: absolute;">1. Fix data </div>
            		<div style="margin:25px 0 0 20px;font-size:0.9em;position: absolute;">Kemahasiswaan</div>
					<div style="margin:45px 0 0 5px;font-size:0.9em;position: absolute;">2. Set NIM = NPM </div>
        	    	<div style="margin:65px 0 0 5px;font-size:0.9em;position: absolute;">3. Hapus Record Setelah </div>
        	    	<div style="margin:80px 0 0 20px;font-size:0.9em;position: absolute;">Keluar/Lulus/DO</div>
                	
				</a> 
				<div class="tile-wide bg-darkViolet fg-white" data-role="tile">
            		<form id="cekMhsDobel">
            			<div class="padding10">
                			<p class="no-margin text-shadow"><span class="text-bold">CEK MAHASISWA DOBEL :<br>
                			Filter berdasarkan : <br>
                				<select id="filter_tipe" name="filter_tipe" style="width:290px">
                					<option value="0">NO FILTER</option>
                					<!--  option value="1">LULUSAN BELUM ADA NO IJAZAH</option -->
                				</select>
                				<br>@ ANGKATAN :<input type="text" id="smawl" name="smawl" style="width:75px"/>
                			</span></p>
                		</div>
                		<div style="margin: 0 0 0 10px;position: absolute;">  	
                			<input type="submit" value="Next" style="width:115px;height:25px"/>
						</div>
            		</form>	
            	</div>
            	
			</div>
		</div>	
		
		<div class="tile-group triple">
			<span class="tile-group-title">DATA AWAL</span>
			<div class="tile-container">
				<div class="tile-wide bg-darkViolet fg-white" data-role="tile">
           		 	<form id="setGroup1">
            			<div class="padding10">
                			<p class="no-margin text-shadow">
                			<span class="text-bold">
                			(*) Set Based Thsms: <input type="number" name="based_thsms" placeholder="20151" style="width:75px"/><br>
                			(1) Set Batas Studi Mhs Aktif Thsms: <input type="number" name="thsms_btstu" style="width:75px"/><br>
                			(2) Forced Set K Mhs Lewat Btstu:&nbsp <input type="number" name="thsms_out" style="width:75px"/><br>
                			</span></p>
                		</div>
                		<div style="margin: 0 0 0 10px;position: absolute;">  	
                			<input type="submit" value="Next" style="width:115px;height:25px"/>
						</div>
            		</form>	
            	</div>
			</div>
		</div>	
		
	</div>
</div>
</body>
</html>
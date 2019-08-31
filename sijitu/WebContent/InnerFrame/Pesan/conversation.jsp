<!DOCTYPE html>
<html>
<head lang="en">

	<%@ page import="java.util.concurrent.TimeUnit" %>
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>
	<%@ page import="java.text.SimpleDateFormat" %>
	<%@ page import="org.codehaus.jettison.json.JSONArray" %>
	<%@ page import="org.codehaus.jettison.json.JSONObject" %>
	<%@ page import="org.codehaus.jettison.json.JSONException" %>
	<%@ page import="org.owasp.esapi.ESAPI" %>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
  	<script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
  	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/imgResize/resize.css" media="screen" />
  	
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String str_range = request.getParameter("str_range");
String npm_indiv = request.getParameter("npm_indiv");
String grp_conversation_nm = request.getParameter("grp_conversation_nm");
String grp_conversation_id = request.getParameter("grp_conversation_id");
String cur_sta_index = request.getParameter("cur_sta_index");
if(cur_sta_index==null || Checker.isStringNullOrEmpty(cur_sta_index)) {
	cur_sta_index = "0";
}
JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/citcat/get/msg/room/"+grp_conversation_id+"/sta_idx/"+cur_sta_index+"/range/"+str_range);
//JSONArray jsoa = (JSONArray)session.getAttribute("jsoa");
String my_npm = validUsr.getNpm();
String imgUsed = "opr.jpg";
boolean stu = validUsr.iAmStu();


//System.out.println(grp_conversation_nm+"`"+grp_conversation_id+"`"+cur_sta_index+"`"+str_range);
//set read
Getter.readJsonArrayFromUrl("/v1/citcat/upd/dashboard/set_read/"+validUsr.getNpm()+"/"+grp_conversation_id);



%>
<!--  meta http-equiv="refresh" content="20; url=prep.conversation?npm_indiv=<%=npm_indiv %>&str_range=<%=str_range %>&cur_sta_index=<%=cur_sta_index %>&grp_conversation_nm=<%=grp_conversation_nm %>&grp_conversation_id=<%=grp_conversation_id %>" seamless="seamless" width="100%" height="400px" onload="scroll(0,0);resize_iframe()" -->
<style>
.chat
{
    list-style: none;
    margin: 0;
    padding: 0;
    width: 100%;
    
}

.chat li
{
    margin-bottom: 10px;
    padding-bottom: 5px;
    border-bottom: 1px dotted #B3A9A9;
}

.chat li.left .chat-body
{
    margin-left: 60px;
  
}

.chat li.right .chat-body
{
    margin-right: 60px;
  
}


.chat li .chat-body p
{
    margin: 0;
    color: #777777;
   
    
}

.panel .slidedown .glyphicon, .chat .glyphicon
{
    margin-right: 5px;
}

.panel-body.chat
{
    list-style: none;
    margin: 0;
    padding: 0;
   
}

.chat li
{
    margin-bottom: 10px;
    padding-bottom: 5px;
    border-bottom: 1px dotted #B3A9A9;
}

.chat li.left .chat-body
{
    margin-left: 60px;
}

.chat li.right .chat-body
{
    margin-right: 60px;
}


.chat li .chat-body p
{
    margin: 0;
    color: #777777;
}

.panel .slidedown .glyphicon, .chat .glyphicon
{
    margin-right: 5px;
}

.panel-body
{
    overflow: hidden;
    //height: 1000px;
}

::-webkit-scrollbar-track
{
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
    background-color: #F5F5F5;
}

::-webkit-scrollbar
{
    width: 12px;
    background-color: #F5F5F5;
}

::-webkit-scrollbar-thumb
{
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
    background-color: #555;
}

{
    overflow-y: scroll;
    width: 500px;
    //height: 1000px;
}

::-webkit-scrollbar-track
{
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
    background-color: #F5F5F5;
}

::-webkit-scrollbar
{
    width: 12px;
    background-color: #F5F5F5;
}

::-webkit-scrollbar-thumb
{
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
    background-color: #555;
}



</style>
<script>

</script>
<!--  meta http-equiv="refresh" content="20; url=prep.conversation?npm_indiv=<%=npm_indiv %>&str_range=<%=str_range %>&cur_sta_index=<%=cur_sta_index %>&grp_coversation_nm=<%=grp_conversation_nm %>&grp_coversation_id=<%=grp_conversation_id %>" /-->

</head>
<body onload="window.scroll(0, document.documentElement.scrollHeight)">
                <div class="panel-body" style="text-align:justified">
                    <ul class="chat">
<%
//System.out.println("jsoa.length()="+jsoa.length());
if(jsoa.length()==Integer.parseInt(str_range)) {
	
%>          
                    <li align="center">
                    	<a href="prep.conversation?str_range=<%=str_range %>&cur_sta_index=<%=Integer.parseInt(cur_sta_index)+Integer.parseInt(str_range)-1 %>&grp_conversation_nm=<%=grp_conversation_nm %>&grp_conversation_id=<%=grp_conversation_id %>">
                    	<img src="<%=Constants.getRootWeb() %>/images/icon/blue_up_arrow.png" alt="User Avatar" class="img-circle" style="height:45px;width:45px">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplihat pesan sebelumnya
                    </a>
                    </li>
<%
}
if(jsoa!=null && jsoa.length()>0) {
	String sender_npm = "null";
	String sender_nmm = "null";
	String updtm = "null";
	String isi_pesan = "null";
	String name_grp_s = "null";

	//for(int i=0;i<jsoa.length();i++) {
	int min = 2;
	if(jsoa.length()<Integer.parseInt(str_range)) {
		min =1;
	}
	for(int i=jsoa.length()-min;i>=0;i--) {	
		JSONObject job = jsoa.getJSONObject(i);
		try {
			sender_npm = (String)job.get("npm_sender");
			//System.out.println("sender_npm="+sender_npm);
		}
		catch(JSONException e) {}//ignore
		try {
			sender_nmm = (String)job.get("fullname_sender");
		}
		catch(JSONException e) {}//ignore
		try {
			updtm = (String)job.get("creatime");
			
			//System.out.println("updtm="+updtm);
		}
		catch(JSONException e) {}//ignore
		try {
			isi_pesan = (String)job.get("pesan");
			//isi_pesan= Converter.prepStringFromUrlPassingToScreen(isi_pesan);
			isi_pesan = java.net.URLDecoder.decode(isi_pesan, "UTF-8");
			isi_pesan= Converter.prepStringFromUrlPassingToScreen(isi_pesan);
		}
		catch(JSONException e) {}//ignore>
		try {
			name_grp_s = (String)job.get("name_grp_s");
		}
		catch(JSONException e) {}//ignore>
		if(name_grp_s!=null &&!Checker.isStringNullOrEmpty(name_grp_s)) {
			imgUsed="opr.jpg";
        }
        else {
        	imgUsed="stu.jpg";
        }
		//java.util.Date dt_created = Converter.parseDate(updtm);
		//System.out.println("dt created = "+dt_created.toString());
		String how_long = ""+Converter.getHowLongAgo(updtm);
		if(my_npm.equalsIgnoreCase(sender_npm)) {
%>
						<li class="left clearfix"><span class="chat-img pull-left">
						<div id="myDiv1">
                            <img src="<%=Constants.getRootWeb() %>/images/icon/<%=imgUsed %>" alt="User Avatar" class="img-circle">
                        </div>
                        </span>
                            <div class="chat-body clearfix">
                                <div class="header">
                                    <strong class="primary-font"> 
                                    <%
                                    if(name_grp_s!=null &&!Checker.isStringNullOrEmpty(name_grp_s)) {
                                    	out.print(name_grp_s);
                                    }
                                    else {
                                    	out.print(sender_nmm);
                                    }
                                    
                                    %> </strong> <small class="pull-right text-muted">
                                        <span class="glyphicon glyphicon-time"></span><%=how_long %></small>
                                </div>
                                <p>
                                    <%=isi_pesan %>
     							</p>
                            </div>
                        </li>
<%			
		}
		else {
%>
						<li class="right clearfix"><span class="chat-img pull-right">
						<div id="myDiv1">
                            <img src="<%=Constants.getRootWeb() %>/images/icon/<%=imgUsed %>" alt="User Avatar" class="img-circle">
                        </div>
                        </span>
                            <div class="chat-body clearfix">
                                <div class="header">
                                  	<small class=" text-muted"><span class="glyphicon glyphicon-time"></span><%=how_long %></small>
                                    <strong class="pull-right primary-font">
                                    <%
                                    if(name_grp_s!=null &&!Checker.isStringNullOrEmpty(name_grp_s)) {
                                    	out.print(name_grp_s);
                                    }
                                    else {
                                    	out.print(sender_nmm);
                                    }
                                    
                                    %>
									</strong>
                                </div>
                                <p>
                                    <%=isi_pesan %>
     							</p>
                            </div>
                        </li>
<%			
		}
%>                    
                     	
                        
                        
                        <!--  li class="left clearfix"><span class="chat-img pull-left">
                            <img src="http://placehold.it/50/55C1E7/fff&amp;text=U" alt="User Avatar" class="img-circle">
                        </span>
                            <div class="chat-body clearfix">
                                <div class="header">
                                    <strong class="primary-font">Jack Sparrow</strong> <small class="pull-right text-muted">
                                        <span class="glyphicon glyphicon-time"></span>14 mins ago</small>
                                </div>
                                <p>
                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum ornare
                                    dolor, quis ullamcorper ligula sodales.
                                </p>
                            </div>
                        </li-->
                        
 	<%
	}
	//if(jsoa.length()<Integer.parseInt(str_range)) {
	if(Integer.parseInt(cur_sta_index)>0) {
		int idx = 0;
	
		if(Integer.parseInt(cur_sta_index)>Integer.parseInt(str_range))	{
			idx = Integer.parseInt(cur_sta_index)-Integer.parseInt(str_range);
		}
	%>          
	                    <li align="center">
	                    	<a href="prep.conversation?str_range=<%=str_range %>&cur_sta_index=<%=idx %>&grp_conversation_nm=<%=grp_conversation_nm %>&grp_conversation_id=<%=grp_conversation_id %>">
	                    	<img src="<%=Constants.getRootWeb() %>/images/icon/blue_down_arrow.png" alt="User Avatar" class="img-circle" style="height:45px;width:45px">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsplihat pesan selanjutnya
	                    </a>
	                    </li>
	<%
	}
	
}
else {
%>
						<li class="left clearfix"><span class="chat-img pull-left">
                            <img src="http://placehold.it/50/55C1E7/fff&amp;text=U" alt="User Avatar" class="img-circle">
                       	 	</span>
                            <div class="chat-body clearfix">
                                <p>
                                    BELUM ADA PERCAKAPAN
     							</p>
                            </div>
                        </li>
<%	
}
 %> 
 						                      
                    </ul>
                </div>
                
<iframe id="glu_msg_checker" src="<%=Constants.getRootWeb() %>/InnerFrame/Pesan/msg_checker.jsp?npm_indiv=<%=npm_indiv %>&str_range=<%=str_range %>&cur_sta_index=0&grp_conversation_nm=<%=grp_conversation_nm %>&grp_conversation_id=<%=grp_conversation_id %>" style="width:0;height:0;border:0; border:none;" name="nu_msg_checker_inner_iframe" >
</iframe>
</body>
</html>
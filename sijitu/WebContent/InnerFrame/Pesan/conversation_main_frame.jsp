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
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
  	<script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String grp_conversation_nm = request.getParameter("grp_nm");
String grp_conversation_id = request.getParameter("grp_id");
String cur_sta_index = request.getParameter("cur_sta_index");
String str_range = request.getParameter("str_range");
String npm_indiv = request.getParameter("npm_indiv");
//System.out.println("sip."+grp_conversation_nm+"`"+grp_conversation_id+"`"+cur_sta_index+"`"+str_range);
/*
SET sudah baca

*/
//JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/citcat/upd/dashboard/set_read/"+validUsr.getNpm()+"/"+grp_coversation_id);
%>
<style>

</style>
<script>
</script>

</head>
<body>

<br/>
<div class="container">
    <div class="row">
        <div class="col-md-9">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <span class="glyphicon glyphicon-comment"></span> <%=grp_conversation_nm %>
                    
                </div>
                
<iframe id="sub_glu" src="prep.conversation?npm_indiv=<%=npm_indiv %>&str_range=<%=str_range %>&cur_sta_index=<%=cur_sta_index %>&grp_conversation_nm=<%=grp_conversation_nm %>&grp_conversation_id=<%=grp_conversation_id %>" seamless="seamless" width="100%" height="400px" onload="scroll(0,0);resize_iframe()" name="sub_inner_iframe"></iframe>
				
<div class="panel-footer">
					<form id="form1" action="go.replyMsg" method="post" target="sub_inner_iframe" >
                    <div class="input-group">
                    	<input type="hidden" name="str_range" value="<%=str_range %>"/>
             	       	<input type="hidden" name="grp_conversation_nm" value="<%=grp_conversation_nm %>"/>
                	    <input type="hidden" name="grp_conversation_id" value="<%=grp_conversation_id %>"/>
                    	<input type="hidden" name="cur_sta_index" value="<%=cur_sta_index %>"/>
                    	<input type="hidden" name="npm_sender" value="<%=validUsr.getNpm() %>"/>
                    	<input type="hidden" name="npm_indiv" value="<%=npm_indiv %>"/>
                    	
           	            <input id="btn-input" name="pesan" type="text" class="form-control input-sm" placeholder="isi pesan anda...">
                        <span class="input-group-btn">
                            <button class="btn btn-warning btn-sm" id="btn-chat">
                                Kirim</button>
                        </span>
                   
                    </div>
                   	</form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
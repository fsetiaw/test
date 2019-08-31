<!DOCTYPE html>
<html>
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
	<%@ page import="beans.dbase.chitchat.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="beans.dbase.overview.*" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
        <script>
       		$(document).ready(function() {
        		$('#updateUsername').submit(function() {
        			$.ajax({
        				url: 'go.update',
        				type: 'POST',
        				dataType: 'json',
        				data: $('#updateUsername').serialize(),
        				success: function(data) {
        					if(data.isValid) {
        						$('#displayName').html('Your name is: '+data.username);
        						$('#displayName').sliderDown(500);
        					}
        					else {
        						alert('Please enter valid name');
        					}
        				}
        			})
        			return false;
        		});	
        	})
        </script>
    </head>
    <body>
        <h1>SHOUT-OUT!</h1>
        <form id="updateUsername">
            <table>
                <tr>
                    <td>Your name:</td>
                    <td><input type="text" id="username" name="username"/></td>
                </tr>
                
                <tr>
                    <td><input type="submit" value="submit" /></td>
                </tr>
            </table>
        </form>
        <h2> Current Shouts </h2>
        <p id="displayName"></p>
        <hr />
        
    </body>
</html>
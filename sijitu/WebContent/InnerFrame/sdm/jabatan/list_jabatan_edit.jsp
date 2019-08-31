<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.dbase.spmi.*"%>

<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SATYAGAMA</title>
<%
//System.out.println("sampe lseini");
//-------------------------------------------------
String ua=request.getHeader("User-Agent").toLowerCase();
boolean mobile=false;
if(ua.matches("(?i).*((android|bb\\d+|meego).+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\\.(browser|link)|vodafone|wap|windows ce|xda|xiino).*")||ua.substring(0,4).matches("(?i)1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\\-|your|zeto|zte\\-")) {
  //response.sendRedirect("http://detectmobilebrowser.com/mobile");
  //return;	
  mobile = true;
  
}
//--------------------------------------------


beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v_list_job = Getter.getListJabatanStruktural();
String tkn_posisi_jabatan = Checker.getListPosisiJabatan();
StringTokenizer st_job = null;
%>
<style>
a.img {
	float: left;
    //height: 120px;
    margin-bottom: -25px;
    margin-left: 9px;
    position: relative;
    //width: 147px;
    //background-color: rgba(0, 0, 0, 0.5);
    border-radius: 3px;
}
a.img:hover {
	text-decoration: none;
	background:none;
	
}

a.img2 {
	float: right;
    //height: 120px;
    margin-bottom: -25px;
    margin-right: 9px;
    position: relative;
    //width: 147px;
    //background-color: rgba(0, 0, 0, 0.5);
    border-radius: 3px;
}
a.img2:hover {
	text-decoration: none;
	background:none;
	
}

a.teks {
	float: right;
    //height: 120px;
    margin-bottom: -10px;
    margin-left: 9px;
    position: relative;
    //width: 147px;
    //background-color: rgba(0, 0, 0, 0.5);
    //border-radius: 10px;
}
a.teks:hover {
	text-decoration: none;
	background:none;
	
}

.table {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
}
.table thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9; }

.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px }

</style>
<style>
.table1 {
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>;
}
.table1 thead > tr > th {
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
}
.table1 thead > tr > th, .table1 tbody > tr > t-->h, .table1 tfoot > tr > th, .table1 thead > tr > td, .table1 tbody > tr > td, .table1 tfoot > tr > td { border: 1px solid #2980B9; }

.table1-noborder { border: none;background:<%=Constant.lightColorBlu()%>; }
.table1-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; }
.table1-noborder thead > tr > th, .table1-noborder tbody > tr > th, .table1-noborder tfoot > tr > th, .table1-noborder thead > tr > td, .table1-noborder tbody > tr > td, .table1-noborder tfoot > tr > td { border: none;padding: 2px }

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
	for(int i=1;i<25;i++) {
	%>
		$("#copyStd<%=i%>").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		if(confirm('Konfirmasi meng-copy standard')==false) {
	   			return false;    
	        }
	        $.ajax({
        		url: 'go.copyStd',
        		type: 'POST',
        		data: $("#copyStd<%=i%>").serialize(),
        	    beforeSend:function(){
        	    	
        	    	$("#wait").show();
        	    	
        	    	$("#main").hide();
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	        
        	    	window.location.href = "<%=Constants.getRootWeb() %>/InnerFrame/Spmi/standar_spmi/home_standar.jsp?id_master_std=0";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
	<%
	}
	%>

		
	});	
    </script>
<%

%>
</head>
<body>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai</p>
</div>
<div id="main">

<jsp:include page="menu.jsp" />

<div class="colmask fullpage">
	<div class="col1">
		<!-- Column 1 start -->
<%
	if(v_list_job==null||v_list_job.size()<1) {
%>
		<div style="padding:50px 100px;text-size:1.5em;font-weight:bold">Hasil Pencarian Jabatan Struktural : 0 Result</div>
<%		
	}
	else {
		int i=1;
%>
		<br> <br>
		<form action="go.editJabStruk" method="POST">
		<table class="table" style="margin:auto;width:90%" align="center">
<%	
		String nama_original_jabatan = null;
		ListIterator li = v_list_job.listIterator();
		if(li.hasNext()) {
			
			String brs = (String)li.next();
			StringTokenizer st =new StringTokenizer(brs,"~");
			String nm_jab = st.nextToken();
			String alias = st.nextToken();
			String singkatan = st.nextToken();
			nama_original_jabatan = new String(alias);
			//tkn_posisi_jabatan
			String posisi = Tool.getTokenKe(alias, 1);
			String prev_init_letter = alias.substring(0, 1).toUpperCase();
%>
			<thead>
				<tr>
  					<th colspan="1" style="border-right:none;width:5%;color:#369;background-color:white;text-align: center; padding: 0px 10px;font-size:2em">&nbsp</th>
  					<th colspan="1" style="border-left:none;border-right:none;width:90%;color:#369;background-color:white;text-align: center; padding: 0px 10px;font-size:2em">EDIT NAMA JABATAN</th>
  					<th colspan="1" style="border-left:none;width:5%;color:#369;background-color:white;text-align: center; padding: 0px 10px;font-size:2em">&nbsp</th>
  				</tr>
  				<tr>
  					<th colspan="3" style="text-align: left; padding: 0px 10px;font-size:1.7em"><%=prev_init_letter %></th>
  				</tr>
  			</thead>
  			<tbody>
  				<tr>
  					<td width="5%" align="right" style="border:none;vertical-align: middle; padding: 0px 5px">(<%=i++ %>)</td>
					<td colspan="2" width="95%" align="left" style="border:none;vertical-align: middle; padding: 0px 0px">
						<table width="100%">
							<tr>
								<td style="width:25%">
									<select name="posisi_jabatan" style="width:100%;text-align-last:center;border:none;height:45px">
										<option value="null">Tanpa posisi jabatan</option>
										<%
			boolean posisi_match=false;							
			st_job = new StringTokenizer(tkn_posisi_jabatan,"~");
			while(st_job.hasMoreTokens()) {
				String pilihan_posisi = st_job.nextToken();
				if(posisi.equalsIgnoreCase(pilihan_posisi)) {
					posisi_match=true;
					alias = alias.replace(pilihan_posisi+" ", "");
										%>
										<option value="<%=pilihan_posisi.toUpperCase()%>" selected="selected"><%=pilihan_posisi.toUpperCase()%></option>
										<%			
				}
				else {
					 					%>
					 					<option value="<%=pilihan_posisi.toUpperCase()%>"><%=pilihan_posisi.toUpperCase()%></option>
					 					<%
				}
			}
										%>
									</select>
								</td>
								<td style="width:60%">
									<%
			boolean urutan_posisi_match=false;
			st_job = new StringTokenizer(alias);
			String last_word = "null";
			String last_word_ori = "null";
			String nm_jab_only = "";
			while(st_job.hasMoreTokens()) {
				last_word = st_job.nextToken();
				nm_jab_only = nm_jab_only+last_word.trim();
				if(st_job.hasMoreTokens()) {
					nm_jab_only=nm_jab_only+" ";
				}
			}
			if(last_word.equalsIgnoreCase("I")) {
				last_word="1";
				last_word_ori="I";
				urutan_posisi_match = true;
			}
			else if(last_word.equalsIgnoreCase("II")) {
				last_word="2";
				last_word_ori="II";
				urutan_posisi_match = true;
			}
			else if(last_word.equalsIgnoreCase("III")) {
				last_word="3";
				last_word_ori="III";
				urutan_posisi_match = true;
			}
			else if(last_word.equalsIgnoreCase("IV")) {
				last_word="4";
				last_word_ori="IV";
				urutan_posisi_match = true;
			}
			else if(last_word.equalsIgnoreCase("V")) {
				last_word="5";
				last_word_ori="V";
				urutan_posisi_match = true;
			}
			else if(last_word.equalsIgnoreCase("VI")) {
				last_word="6";
				last_word_ori="VI";
				urutan_posisi_match = true;
			}
			else if(last_word.equalsIgnoreCase("VII")) {
				last_word="7";
				last_word_ori="VII";
				urutan_posisi_match = true;
			}
			else if(last_word.equalsIgnoreCase("VIII")) {
				last_word="8";
				last_word_ori="VIII";
				urutan_posisi_match = true;
			}
			else if(last_word.equalsIgnoreCase("IX")) {
				last_word="9";
				last_word_ori="IX";
				urutan_posisi_match = true;
			}
			else if(last_word.equalsIgnoreCase("X")) {
				last_word="10";
				last_word_ori="X";
				urutan_posisi_match = true;
			}
			else {
				try {
					last_word = ""+Integer.parseInt(last_word);
					urutan_posisi_match = true;
					last_word_ori=new String(last_word);
				}
				catch(Exception e) {
					last_word = "null";
				}
			}
			if(urutan_posisi_match)	{
				//System.out.println("urutan_posisi_match");
				//System.out.println("alias="+alias);
				//System.out.println(alias.lastIndexOf(last_word_ori));
				alias = alias.substring(0, alias.lastIndexOf(last_word_ori)-1);
			}
			//out.print(alias.toUpperCase());
			
									%>
									<input type="hidden" name="nama_original_jabatan" value="<%=nama_original_jabatan.toUpperCase() %>" style="width:100%;border:none;padding:0 0 0 10px;height:45px"/>
									<input type="text" name="jabatan" value="<%=alias.toUpperCase() %>" style="width:100%;border:none;padding:0 0 0 10px;height:45px"/>
								</td>
								<td style="width:15%">
									<select name="urutan_posisi_jabatan" style="width:100%;text-align-last:center;border:none;height:45px">
			<%
			for(int j=1;j<6;j++) {
				if(last_word.equalsIgnoreCase("null")) {
			%>
										<option value="null" selected="selected">Biarkan kosong</option>
			<%		
				}
				else {
					if(last_word.equalsIgnoreCase(""+j)) {
						%>
										<option value="<%=j %>" selected="selected"><%=j %></option>
<%		
					}
					else {
						%>
										<option value="<%=j %>"><%=j %></option>
<%					
					}
				}
			}
			%>							
									</select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
  			</tbody>	
<%
			while(li.hasNext()) {
				brs = (String)li.next();
				st =new StringTokenizer(brs,"~");
				nm_jab = st.nextToken();
				alias = st.nextToken();
				singkatan = st.nextToken();
				nama_original_jabatan = new String(alias);
				posisi = Tool.getTokenKe(alias, 1);
				String curr_init_letter = alias.substring(0, 1).toUpperCase();
				if(!curr_init_letter.equalsIgnoreCase(prev_init_letter)) { 
					i=1;
	%>
			<thead>
				<tr>
	  				<th colspan="3" style="text-align: left; padding: 0px 10px;font-size:1.7em"><%=curr_init_letter %></th>
	  			</tr>
	  		</thead>
	  		<%
	  				prev_init_letter = new String(curr_init_letter);
				}
	  		%>
	  		<tbody>
	  			<tr>
	  				<td width="5%" align="right" style="border:none;vertical-align: middle; padding: 0px 5px">(<%=i++ %>)</td>
					<td colspan="2" width="95%" align="left" style="border:none;vertical-align: middle; padding: 0px 0px">
						<table width="100%">
							<tr>
								<td style="width:25%">
									<select name="posisi_jabatan" style="width:100%;text-align-last:center;border:none;height:45px">
										<option value="null">Tanpa posisi jabatan</option>
										<%
				posisi_match = false; 
				st_job = new StringTokenizer(tkn_posisi_jabatan,"~");
				while(st_job.hasMoreTokens()) {
					String pilihan_posisi = st_job.nextToken();
					if(posisi.equalsIgnoreCase(pilihan_posisi)) {
						posisi_match=true;
						alias = alias.replace(pilihan_posisi+" ", "");
										%>
										<option value="<%=pilihan_posisi.toUpperCase()%>" selected="selected"><%=pilihan_posisi.toUpperCase()%></option>
										<%			
					}
					else {
					 					%>
					 					<option value="<%=pilihan_posisi.toUpperCase()%>"><%=pilihan_posisi.toUpperCase()%></option>
					 					<%
					}
				}
										%>
									</select>
								</td>
								<td style="width:60%">
									<%
			urutan_posisi_match=false;
			st_job = new StringTokenizer(alias);
			last_word = "null";
			last_word_ori = "null";
			nm_jab_only = "";
			while(st_job.hasMoreTokens()) {
				last_word = st_job.nextToken();
				nm_jab_only = nm_jab_only+" "+last_word.trim();
				if(st_job.hasMoreTokens()) {
					nm_jab_only=nm_jab_only+" ";
				}
			}
			if(last_word.equalsIgnoreCase("I")) {
				last_word="1";
				last_word_ori="I";
				urutan_posisi_match = true;
			}
			else if(last_word.equalsIgnoreCase("II")) {
				last_word="2";
				last_word_ori="II";
				urutan_posisi_match = true;
			}
			else if(last_word.equalsIgnoreCase("III")) {
				last_word="3";
				last_word_ori="III";
				urutan_posisi_match = true;
			}
			else if(last_word.equalsIgnoreCase("IV")) {
				last_word="4";
				last_word_ori="IV";
				urutan_posisi_match = true;
			}
			else if(last_word.equalsIgnoreCase("V")) {
				last_word="5";
				last_word_ori="V";
				urutan_posisi_match = true;
			}
			else if(last_word.equalsIgnoreCase("VI")) {
				last_word="6";
				last_word_ori="VI";
				urutan_posisi_match = true;
			}
			else if(last_word.equalsIgnoreCase("VII")) {
				last_word="7";
				last_word_ori="VII";
				urutan_posisi_match = true;
			}
			else if(last_word.equalsIgnoreCase("VIII")) {
				last_word="8";
				last_word_ori="VIII";
				urutan_posisi_match = true;
			}
			else if(last_word.equalsIgnoreCase("IX")) {
				last_word="9";
				last_word_ori="IX";
				urutan_posisi_match = true;
			}
			else if(last_word.equalsIgnoreCase("X")) {
				last_word="10";
				last_word_ori="X";
				urutan_posisi_match = true;
			}
			else {
				try {
					last_word = ""+Integer.parseInt(last_word);
					urutan_posisi_match = true;
					last_word_ori=new String(last_word);
				}
				catch(Exception e) {
					last_word = "null";
				}
			}
			if(urutan_posisi_match)	{
				//System.out.println("urutan_posisi_match");
				//System.out.println("alias="+alias);
				//System.out.println(alias.lastIndexOf(last_word_ori));
				alias = alias.substring(0, alias.lastIndexOf(last_word_ori)-1);
			}
			//out.print(alias.toUpperCase());
									%>
									<input type="hidden" name="nama_original_jabatan" value="<%=nama_original_jabatan.toUpperCase() %>" style="width:100%;border:none;padding:0 0 0 10px;height:45px"/>
									<input type="text" name="jabatan" value="<%=alias.toUpperCase() %>" style="width:100%;border:none;padding:0 0 0 10px;height:45px"/>
								</td>
								<td style="width:15%">
									<select name="urutan_posisi_jabatan" style="width:100%;text-align-last:center;border:none;height:45px">
			<%
			for(int j=1;j<6;j++) {
				if(last_word.equalsIgnoreCase("null")) {
			%>
										<option value="null" selected="selected">Biarkan kosong</option>
			<%		
				}
				else {
					if(last_word.equalsIgnoreCase(""+j)) {
						%>
										<option value="<%=j %>" selected="selected"><%=j %></option>
<%		
					}
					else {
						%>
										<option value="<%=j %>"><%=j %></option>
<%					
					}
				}
			}
			%>							
									</select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
	  		</tbody>	
	<%				
			}
%>  				
 
			
<%			
		}
		 %>
		 	<tr>
				<td colspan="3" style="padding:15px 0px">
					<section class="gradient" style="text-align:center">
	            		<button style="padding: 5px 50px;font-size: 20px;">UPDATE NAMA JABATAN</button>
        			</section>
				</td>		
			</tr>	
		 </table>
		 </form>
<%		 		
	}
%>		
	</div>
</div>	
</div>	
</body>

</html>

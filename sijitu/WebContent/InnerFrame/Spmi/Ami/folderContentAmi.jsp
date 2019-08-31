<!DOCTYPE html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<%@ page import="beans.tools.*" %>
<%@ page import="java.io.File" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/folderIcon/folderStyleVer2.css" media="screen" />
	<!--  link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/folderIcon/folderStyle.css" media="screen" / -->
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"> </script>	
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/css/progressBar/normalize.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/css/progressBar/style.css">
<script src="<%=Constants.getRootWeb() %>/js/modernizr.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"> </script>

<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	File[] listFileFolder = (File[])session.getAttribute("listFileFolder");
	String hakAksesUsrUtkFolderIni = (String)session.getAttribute("hakAksesUsrUtkFolderIni");
	String cur_dir = (String)request.getParameter("cur_dir");
	String keter = (String)request.getParameter("keter");
	String root_dir = (String)request.getParameter("root_dir");
	String kdpst = (String)request.getParameter("kdpst");
	if(Checker.isStringNullOrEmpty(kdpst)) {
		kdpst = (String)session.getAttribute("kdpst_folder");
	}
	String nm_prodi = Converter.getNamaKdpstDanJenjang(kdpst);
	//System.out.println("cur_dir="+cur_dir);
	//System.out.println("kdpst2="+kdpst);
	//System.out.println("kdpst folder="+(String)session.getAttribute("kdpst_folder"));
	//System.out.println("root_dir="+root_dir);
	session.setAttribute("saveToFolder", cur_dir);
	session.setAttribute("dataForRouterAfterUpload", root_dir+"`"+keter+"`"+hakAksesUsrUtkFolderIni+"`"+cur_dir);
	//Vector v_nm_alm_access = (Vector) session.getAttribute("v_nm_alm_access");

	String folder_utama = (String)session.getAttribute("folder_asal");
	if(folder_utama!=null && folder_utama.endsWith("/")) {
		folder_utama = folder_utama.substring(0, folder_utama.length()-1);
	}
	
	String root_ami_folder = (String)session.getAttribute("root_ami_folder");
	while(root_ami_folder.contains("//")) {
		root_ami_folder = root_ami_folder.replace("//", "/");
	}
	root_dir = root_ami_folder;
	//System.out.println("root_ami_folder="+root_ami_folder);
%>
<script type="text/javascript">
//setTimeout(popup, 3000); // Setting time 3s to popup login form


function popup() {
	$("#logindiv").css("display", "inline");
}



$("#onclick").click(function() {
	$("#contactdiv").css("display", "inline");
});
	
$(document).ready(function() {
//	setTimeout(popup, 3000);


	function popup() {
		$("#logindiv").css("display", "inline");
	}
	
	
	
	$("#login #cancel").click(function() {
		$(this).parent().parent().hide();
		document.getElementById('konten').style.visibility='visible';
        document.getElementById('bartext').style.visibility='visible';
	});
	
	
	
	
	
	// Login form popup login-button click event.
	$("#loginbtn").click(function() {
		var nameFolder = $("#username").val();
	//var password = $("#password").val();
	//if (username == "" || password == ""){
		if (nameFolder == ""){
			alert("Nama Folder Kosong atau Sudah Digunakan");
		}
		else{
			//$("#logindiv").css("display", "inline");
			$.post('file.createNuFolderAmi', $('#login').serialize(), function(data) {
				window.location.replace("go.createNuFolderredirectAfterGenericUploadFileAmi?kdpst=<%=kdpst%>");
				//document.getElementById('konten').style.visibility='visible';
			    //$('#konten').html(); 
			    //$('#konten').html(data);
			    //$("#konten").load(location.href + " #konten");
			    //document.getElementById('logindiv').style.visibility='hide';
			    //$(this).parent().parent().hide();
				//document.getElementById('konten').style.visibility='visible';
		        //document.getElementById('bartext').style.visibility='visible';
			});
		}
	});
});	
</script>
<!-- popupload  -->
<script type="text/javascript">
//setTimeout(popup, 3000); // Setting time 3s to popup login form


function popupload() {
	$("#uploaddiv").css("display", "inline");
	document.getElementById('konten').style.visibility='hidden';
    document.getElementById('bartext').style.visibility='hidden';
	//$("#uploaddiv")[0].focus();
	//document.getElementById("uploaddiv").focus();
	//document.getElementById("upload_button").focus();
	
}



//$("#onclick").click(function() {
//	$("#contactdiv").css("display", "inline");
//	document.getElementById("uploaddiv").focus();
//});
	
$(document).ready(function() {
//	setTimeout(popup, 3000);


	function popup() {
		$("#uploaddiv").css("display", "inline");
		//$("#uploaddiv")[0].focus();
		//document.getElementById("uploaddiv").focus();
	}
	
	
	
	$("#login #cancel").click(function() {
		$(this).parent().parent().hide();
	});
	
	
	// Login form popup login-button click event.
	$("#loginbtn").click(function() {
		var nameFolder = $("#username").val();
	//var password = $("#password").val();
	//if (username == "" || password == ""){
		if (nameFolder == ""){
			alert("Nama Folder Kosong atau Sudah Digunakan");
		}
		else{
			//$("#logindiv").css("display", "inline");
			$.post('file.createNuFolderAmi?kdpst=<%=kdpst%>', $('#login').serialize(), function(data) {
				document.getElementById('div_msg').style.visibility='visible';
			    $('#div_msg').html(); 
			    $('#div_msg').html(data); 
			});
		}
	});
});	
</script>

<!-- process upload file --> 
<script type="text/javascript">
	var objId="none";
	var nmm="none";
	var npm="none";
	var obj_lvl="none";
	var kdpst="none";
    function fileSelected() {
    	var file = document.getElementById('fileToUpload').files[0];
        if (file) {
          var fileSize = 0;
          if (file.size > 1024 * 1024)
            fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
          else
            fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';

          document.getElementById('fileName').innerHTML = 'Name: ' + file.name;
          document.getElementById('fileSize').innerHTML = 'Size: ' + fileSize;
          document.getElementById('fileType').innerHTML = 'Type: ' + file.type;
          document.getElementById('konten').style.visibility='hidden';
          document.getElementById('bartext').style.visibility='hidden';
          
        }
      }

      function uploadFile() {
        var fd = new FormData();
        //var fd1 = new FormData();
        fd.append("fileToUpload", document.getElementById('fileToUpload').files[0]);
       // fd1.append("saveToFolder", document.getElementById('saveToFolder').nodeValue);
        var xhr = new XMLHttpRequest();
        xhr.upload.addEventListener("progress", uploadProgress, false);
        xhr.addEventListener("load", uploadComplete, false);
        xhr.addEventListener("error", uploadFailed, false);
        xhr.addEventListener("abort", uploadCanceled, false);
        xhr.open("POST", "go.processUploadGenerikFile?kdpst=<%=kdpst%>");
        xhr.send(fd);
        document.getElementById('konten').style.visibility='visible';
        //xhr.send(fd1);
      }

      function uploadProgress(evt) {
        if (evt.lengthComputable) {
          var percentComplete = Math.round(evt.loaded * 100 / evt.total);
          document.getElementById('progressNumber').innerHTML = percentComplete.toString() + '%';
          document.getElementById('pb').style.visibility='visible';
          //$('.progress').style.visibility = "visible";;
          $('.progress').val(percentComplete.toString());
          //$('.progress-value').html(percentComplete.toString() + '%');
        }
        else {
          document.getElementById('progressNumber').innerHTML = 'unable to compute';
        }
      }

      function uploadComplete(evt) {
        /* This event is raised when the server send back a response */
        //alert(evt.target.responseText);
        
       // window.location.replace("proses.updateBuktiPembayaran?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&atMenu=riwayatBayaran");
    	window.location.replace("go.redirectAfterGenericUploadFileAmi?kdpst=<%=kdpst%>");
    	document.getElementById('konten').style.visibility='hidden';
      }

      function uploadFailed(evt) {
        alert("File Gagal Di Upload");
        window.location.replace("go.redirectAfterGenericUploadFileAmi?kdpst=<%=kdpst%>");
      	//window.location.replace("get.histPymnt?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&atMenu=riwayatBayaran");
      }

      function uploadCanceled(evt) {
        alert("Proses Upload Dibatalkan Oleh Pengguna atau Koneksi Internet Terputus");
        window.location.replace("go.redirectAfterGenericUploadFileAmi?kdpst=<%=kdpst%>");
        
      	//window.location.replace("get.histPymnt?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&atMenu=riwayatBayaran");
      }
    </script>
</head>
<body>
<div id="header">
<jsp:include page="menu_back_ami.jsp" />
</div>

<!--  div class="colmask fullpage">
	<div class="col1">
		<div id="mainform">
<h2>jQuery Popup Form Example</h2>
<!-- Required Div Starts Here 
<div class="form" id="popup">
<b>1.Onload Popup Login Form</b><br/><hr/>
<span>Wait for 3 second.Login Popup form Will appears.</span><br/><br/><br/>
<b>2.Onclick Popup Contact Form</b><hr/>
<p id="onclick">Popup</p>
</div 
</div-->
<!-- Contact Form -->
<div id="contactdiv">
</div>

<!--Login Form -->
<div id="logindiv">
<form class="form" action="#" id="login" name="login"  method="post">
	<img src="<%=Constants.getRootWeb() %>/css/folderIcon/Cancel3.png" class="img" id="cancel"/>
	<label>Buat Folder Baru </label><br/>
<%
	//System.out.println("cur_dir="+cur_dir);
		while(cur_dir.contains("//")) {
			cur_dir = cur_dir.replace("//", "/");
		}
		
		 %>	
	<input type="text" id="username" name="foldername" placeholder="Nama Folder Baru"/>
	<input type="hidden" name="root_dir" value="<%=root_dir%>"/>
	<input type="hidden" name="alm" value="<%=cur_dir%>"/>
	<input type="hidden" name="keter" value="<%=keter%>"/>
	<input type="hidden" name="hak" value="<%=hakAksesUsrUtkFolderIni%>"/>
	<input type="hidden" name="kdpst" value="<%=kdpst%>"/>
	<input type="button" id="loginbtn" name="loginbtn" value="Buat Folder"/>
<!--  input type="button" id="cancel" value="Batal"/-->
<!--  label>password : </label>
<input type="text" id="password" placeholder="************"/>
<input type="button" id="loginbtn" value="Login"/>
<input type="button" id="cancel" value="Cancel"/ -->
</form>
</div>
<div id="uploaddiv">
<form class="form" action="#" id="login" name="login"  method="post" enctype="multipart/form-data">
	
	<!--  form id="form1" enctype="multipart/form-data" method="post" action="go.processUploadFile" -->
	<img src="<%=Constants.getRootWeb() %>/css/folderIcon/Cancel3.png" class="img" id="cancel"/>
	
    	<div style="font-size:1.5em;font-weight:bold;text-align:center;background-color:#a6bac4;width:100%;">PILIH FILE UNTUK DIUPLOAD</div>
      	<input type="file" name="fileToUpload" id="fileToUpload" onchange="fileSelected();" style="font-size:1.5em;color:#900505;font-weight:bold;background-color:#d9e1e5;width:99%;"/>
		<input type="hidden" name="saveToFolder" id="saveToFolder" value="<%=cur_dir %>" />
	
	<%
	String target_nm_folder = "/Folder Utama/"+cur_dir.replace(root_dir, "");
	while(target_nm_folder.contains("//")) {
		target_nm_folder = target_nm_folder.replace("//","/");
	}
	if(target_nm_folder.endsWith("/")) {
		target_nm_folder = target_nm_folder.substring(0,target_nm_folder.length()-1);
	}
	%>
	Upload ke : <%=target_nm_folder %></br>
    	<div id="fileName"></div>
   	 	<div id="fileSize"></div>
    	<div id="fileType"></div>
    <center>
  		<div id="progressNumber" style="text-align:center"></div>

  		<div id="pb" style="visibility:hidden">
  			<progress class="progress"  value="0" max="100"></progress>
  		</div>
  </center>
    	<div class="row" style="font-size:1.5em;color:#900505;font-weight:bold;background-color:#d9e1e5;width:100%;text-align:center;opacity:100">
    		<input id="upload_button" type="button" onclick="uploadFile()" value="UPLOAD FILE" style="font-size:1em;font-weight:bold;width:300px;"/>
    	</div>
    
</form>

</div>

<br/>
	
	
	<table>
		<%
		//System.out.println("folder_utama="+folder_utama);
		//System.out.println("cur_utama="+cur_dir);
		if(!cur_dir.equalsIgnoreCase(root_ami_folder)) {
			String backToDir = "";
			String nameKeterDir = "";
			StringTokenizer st = new StringTokenizer(cur_dir,"/");
			//System.out.println("countTokens="+st.countTokens());
			int i=0;
			while(st.hasMoreTokens() && st.countTokens()>1) {
				nameKeterDir = ""+st.nextToken();
				//System.out.println(i+".nameKeterDir="+nameKeterDir);
			
				backToDir = backToDir+"/"+nameKeterDir;
				i++;
				
			}
			//System.out.println("backToDir="+backToDir);
		%>
		<tr>
			<td style="text-align:center;padding-left:10px">
				<a href="get.folderContentAmi?root_dir=<%=root_dir %>&keter=<%=nameKeterDir %>&alm=<%=backToDir %>&hak=<%=hakAksesUsrUtkFolderIni %>&kdpst=<%=kdpst%>"><img style="border:0;" src="<%=Constants.getRootWeb() %>/css/folderIcon/folder_previous_grey.png" alt="Prev Dir" ></a>
			</td>
			<td>
			<h2>
			<%
			
				out.print(cur_dir.replace(root_dir, ".."));	
			
			%></h2>
			</td>
		</tr>
		<%
		}
		else {
		%>
		<tr>
			<td style="text-align:center;padding-left:10px" colspan="2">
				<h2>Folder Utama</h2>
			</td>
		</tr>

		<%	
		}
		%>
	</table>	
	
	<%
if(hakAksesUsrUtkFolderIni!=null && hakAksesUsrUtkFolderIni.contains("i") && cur_dir!=null && !Checker.isStringNullOrEmpty(cur_dir)) {
	%>		
	
	<table>
		<tr>
	
			<td style="text-align:center;padding-left:10px">
				<a href="javascript:popup()"><img style="border:0;" src="<%=Constants.getRootWeb() %>/css/folderIcon/Grey_Add_Folder.png" alt="Folder Baru" ></a>
			</td>
			<td>
			Add Folder
			</td>
			<td style="text-align:center;padding-left:50px">
				<!--  a href="<%=Constants.getRootWeb() %>/InnerFrame/Tamplete/upload_file/uploadFile.jsp"><img style="border:0;" src="<%=Constants.getRootWeb() %>/css/folderIcon/upload_file55.png" alt="Prev Dir" ></a -->
				<a href="javascript:popupload()"><img  src="<%=Constants.getRootWeb() %>/css/folderIcon/upload_file55.png" alt="" ></a>
			</td>
			<td>
			Upload File
			</td>
		</tr>
	</table>
<%
}
%>		

<div id="bartext" class="bartext"></div> 
<br/>	
<div id="konten">
<div class="container">		
<table class="table">
	<tr>
		<!-- Column 1 start -->
<%
if(listFileFolder!=null && listFileFolder.length>0) {
  //if(v_nm_alm_access!=null && v_nm_alm_access.size()>0) {		
	int j = -1;
	//ListIterator li = v_nm_alm_access.listIterator();
	for(int i=0;i<listFileFolder.length;i++) {
		
		//String brs = (String)li.next();
		//StringTokenizer st = new StringTokenizer(brs,"`");
		//String keter_folder = st.nextToken();
		//String alamat_folder = st.nextToken();
		//String nick_and_hak = st.nextToken();
	//only show directory	
		if(cur_dir.endsWith("/monitoring")||cur_dir.endsWith("/kegiatan ami")) {
			//System.out.println("folder end with monitoring");
			if (listFileFolder[i].isDirectory()) {
				//if(true) {	
				String nm_folder = listFileFolder[i].getName();
				//System.out.println("nm_folder---"+nm_folder);
				//System.out.println("nm_prodi---"+nm_prodi);
				if(nm_folder.equalsIgnoreCase(nm_prodi)) {
					if(nm_folder.contains("_")) {
						StringTokenizer st = new StringTokenizer(nm_folder,"_");
						nm_folder = "";
						while(st.hasMoreTokens()) {
							nm_folder = nm_folder+st.nextToken();
							if(st.hasMoreTokens()) {
								nm_folder = nm_folder+"<br>";
							}
						}
							
					}	
					%>
					
					<td width="200px" style="word-wrap: break-word;z-index: 1; position:relative">
				    <a href="get.folderContentAmi?root_dir=<%=root_dir %>&keter=<%=listFileFolder[i].getName() %>&alm=<%=cur_dir %>/<%=listFileFolder[i].getName() %>&hak=<%=hakAksesUsrUtkFolderIni %>&kdpst=<%=kdpst%>" class="docFolder"></a><div style="text-align:left"><%= nm_folder%></div>
				    </td>
				<%                
						        
						            
						 	
				}
			}	
					
				
		}
		else {
			//System.out.println("nope folder end with monitoring");
			j++;
			if (listFileFolder[i].isDirectory()) {
				//if(true) {	
				String nm_folder = listFileFolder[i].getName();
				//System.out.println("nm_folder---"+nm_folder);
				if(nm_folder.contains("_")) {
					StringTokenizer st = new StringTokenizer(nm_folder,"_");
					nm_folder = "";
					while(st.hasMoreTokens()) {
						nm_folder = nm_folder+st.nextToken();
						if(st.hasMoreTokens()) {
							nm_folder = nm_folder+"<br>";
						}
					}
						
				}
					
				if(j%3==0) {
		%>
			</tr>		
			<tr>
		<%				
				}
		            //out.print("directory:"+listFileFolder[i].getName()+"<br/>");
		           
		            
		%>
			
			<td width="200px" style="word-wrap: break-word;z-index: 1; position:relative">
		    <a href="get.folderContentAmi?root_dir=<%=root_dir %>&keter=<%=listFileFolder[i].getName() %>&alm=<%=cur_dir %>/<%=listFileFolder[i].getName() %>&hak=<%=hakAksesUsrUtkFolderIni %>&kdpst=<%=kdpst%>" class="docFolder"></a><div style="text-align:left"><%= nm_folder%></div>
		    </td>
		<%            
		            
		 	} else {
		        //	j++;
				if(j%3==0) {
		%>
			</tr>
			<tr>
		<%				
				}        
		%>
				<td width="200px" style="word-wrap: break-word;z-index: 1; position:relative">
		    		<a href="go.downloadFileAsIs?root_dir=<%=root_dir %>&keter=<%=listFileFolder[i].getName() %>&alm=<%=cur_dir %>&namaFile=<%=listFileFolder[i].getName() %>&hak=<%=hakAksesUsrUtkFolderIni %>&kdpst=<%=kdpst%>" class="docIcon"></a><div style="text-align:left"><%= listFileFolder[i].getName()%></div>
		    	</td>
		<%
			}
		}
		

	}
}	
%>		
	</tr>
</table>
</div>
</div>
<div id="div_msg" style="font-style:italic;color:red;text-align:left;background-color:#d9e1e5;width:750px;height:100%;visibility:hidden;margin:0px 0 0 132px;" ></div>
		<!-- Column 1 start -->
	
</body>
</html>
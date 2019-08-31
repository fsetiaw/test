<!DOCTYPE html>
<html lang="en">
<head>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<meta charset=utf-8>
<meta name="viewport" content="width=620">
<title>HTML5 Demo: Drag and drop, automatic upload</title>
<link rel="stylesheet" href="css/html5demos.css">
<script src=""></script>
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/h5utils.js"></script>
<%
/*
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
upload.dragFile
PROSES DILAKUKAN PADA SERVLET SERVLET.FILES.DRAGDROP
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
*/

beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String form = request.getParameter("form");
String err = request.getParameter("err");
if(!Checker.isStringNullOrEmpty(err)) {
%>

	<br><h1 align="center" style="color:red"><%=err %></h1><br><br>
<%	
}
String keterangan = "";
if(form.equalsIgnoreCase("noijazah")) {
	keterangan = "EXCEL DAFTAR NO IJAZAH";
}
else if(form.equalsIgnoreCase("potoMhs")) {
	keterangan = "FOTO MHS";
}
else if(form.equalsIgnoreCase("nilaiMk")) {
	keterangan = "EXCEL NILAI KELAS PERKULIAHAN ";
}
else if(form.equalsIgnoreCase("nim")) {
	keterangan = "EXCEL UPDATE NIM MHS ";
}
else if(form.equalsIgnoreCase("nilaiMkPasca")) {
	keterangan = "EXCEL KRS & NILAI PASCASARJANA  ";
}
else if(form.equalsIgnoreCase("kelulusan")) {
	keterangan = "DATA KELULUSAN  ";
}
%>
</head>
<body>
<div id="header">
	<jsp:include page="InnerMenu_pageVersion2.jsp" flush="true" />
	
</div>
<section id="wrapper">

    <header>
      <h1 align="center"></h1>
    </header>

<style>
#holder { border: 10px dashed #ccc; width: 300px; min-height: 100px; margin: 20px auto;}
#holder.hover { border: 10px dashed #0c0; }
#holder img { display: block; margin: 10px auto; }
#holder p { margin: 10px; font-size: 14px; }
progress { width: 50%; }
progress:after { content: '%'; }
.fail { background: #c00; padding: 2px; color: #fff; }
.hidden { display: none !important;}
</style>
<article>
	<div id="pesan" align="center" style="font-size:1.5em;font-weight:bold">Drag and drop file "<%=keterangan %>"</div>
  <div id="holder">
  </div> 
  
  <p id="filereader">File API & FileReader API not supported</p>
  <p id="formdata">XHR2's FormData is not supported</p>
  <p id="progress">XHR2's upload progress isn't supported</p>
  <p align="center"><progress id="uploadprogress" min="0" max="100" value="0">0</progress></p>
  <p align="center">Drag file dari komputer anda ke drop zone diatas, file akan diupload secara otomatis<br>Atau Klik 'Choose File' dibawah ini</p>
  <p align="center" id="upload" class=hidden><input type="file" id="target_file"></p>
  <div id="pesan1" align="center" style="font-size:1.5em;font-weight:bold"></div>
</article>

<script>
var holder = document.getElementById('holder'),
    tests = {
      filereader: typeof FileReader != 'undefined',
      dnd: 'draggable' in document.createElement('span'),
      formdata: !!window.FormData,
      progress: "upload" in new XMLHttpRequest
    }, 
    support = {
      filereader: document.getElementById('filereader'),
      formdata: document.getElementById('formdata'),
      progress: document.getElementById('progress')
    },
    acceptedTypes = {
      'image/png': true,
      'image/jpeg': true,
      'image/gif': true
    },
    progress = document.getElementById('uploadprogress'),
    fileupload = document.getElementById('upload');

"filereader formdata progress".split(' ').forEach(function (api) {
  if (tests[api] === false) {
    support[api].className = 'fail';
  } else {
    // FFS. I could have done el.hidden = true, but IE doesn't support
    // hidden, so I tried to create a polyfill that would extend the
    // Element.prototype, but then IE10 doesn't even give me access
    // to the Element object. Brilliant.
    support[api].className = 'hidden';
  }
});

function previewfile(file) {
  if (tests.filereader === true && acceptedTypes[file.type] === true) {
    var reader = new FileReader();
    reader.onload = function (event) {
      var image = new Image();
      image.src = event.target.result;
      image.width = 250; // a fake resize
      holder.appendChild(image);
    };

    reader.readAsDataURL(file);
  }  else {
    holder.innerHTML += '<p>Uploaded ' + file.name + ' ' + (file.size ? (file.size/1024|0) + 'K' : '');
    console.log(file);
  }
}

function readfiles(files) {
    debugger;
    var formData = tests.formdata ? new FormData() : null;
    for (var i = 0; i < files.length; i++) {
      if (tests.formdata) formData.append('file', files[i]);
      previewfile(files[i]);
    }

    // now post a new XHR request
    if (tests.formdata) {
      var xhr = new XMLHttpRequest();
      xhr.open('POST', 'upload.dragFile?form=<%=form%>');
      xhr.onload = function() {
        progress.value = progress.innerHTML = 100;
      };

      if (tests.progress) {
        xhr.upload.onprogress = function (event) {
          if (event.lengthComputable) {
            var complete = (event.loaded / event.total * 100 | 0);
            progress.value = progress.innerHTML = complete;
          }
        }
      }

      xhr.send(formData);
      
    }
    document.getElementById('pesan1').innerHTML ="";
    var alm= '<%=Constants.getRootWeb()%>/InnerFrame/input_file/uploader_form.jsp?form=<%=form%>';
    var goback= '<%=Constants.getRootWeb()%>/InnerFrame/input_file/index_uploader.jsp';
    document.getElementById('pesan1').innerHTML += '<br>Proses Upload File Sudah Selesai<br>' +
    '<form action="'+alm+'" method="POST"><br><input type="submit" value="Clear Form" style="width:200px;height:35px" /></form>' ;
    document.getElementById("target_file").value = "";
    //document.getElementById('pesan1').innerHTML += '<br>Proses Upload File Sudah Selesai<br>' +
    //'<form action="'+goback+'" method="POST"><br><input type="submit" value="BACK" style="width:200px;height:35px" /></form>' ;
    //formData.reset();
    //files.reset();
    //window.setTimeout(function(){
    //	window.location.href = "<%=Constants.getRootWeb()%>/InnerFrame/input_file/index_uploader.jsp";
    //}, 3000);
}

if (tests.dnd) { 
  holder.ondragover = function () { this.className = 'hover'; return false; };
  holder.ondragend = function () { this.className = ''; return false; };
  holder.ondrop = function (e) {
    this.className = '';
    e.preventDefault();
    readfiles(e.dataTransfer.files);
  }
//} else { original
//fileupload.className = 'hidden'; original
  fileupload.className = 'show';
  fileupload.querySelector('input').onchange = function () {
    readfiles(this.files);
  };
}

</script>
     


<script src="js/prettify.packed.js"></script>

</body>
</html>
<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />	
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.classPoll.*" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.mhs.kurikulum.*" %>
<%@ page import="beans.dbase.krklm.*" %>
<%@ page import="beans.sistem.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/h5utils.js"></script>
<%
//System.out.println("yap1");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= null;
//Vector vThsmsStmhs = (Vector)session.getAttribute("vThsmsStmhs");
//String tkn_stmhs_kode =  Getter.getListStmhs();
String form = null;
String id_obj= request.getParameter("id_obj");
String nmm= request.getParameter("nmm");
String npm= request.getParameter("npm");
String obj_lvl= request.getParameter("obj_lvl");
String scope= request.getParameter("scope");
String table_nm= request.getParameter("table");
if(table_nm==null) {
	table_nm = new String((String)session.getAttribute("nama_table"));
}

int idkur = SearchDbInfoKurikulum.getKurIdMhs(npm);
SearchDbKrklm sdk = new SearchDbKrklm();
Vector v_mk_sidang = sdk.getMkTipeSkripsi(idkur, "NAKMKMAKUL");
String type_pengajuan = table_nm.replace("_RULES", "");
String title_pengajuan = type_pengajuan.replace("_", " ");
String folder_pengajuan = (String)session.getAttribute("folder_pengajuan");
String kdpst= request.getParameter("kdpst");
String target_thsms = Checker.getThsmsPengajuanStmhs();
String cmd= request.getParameter("cmd");
String msg= request.getParameter("msg");
String at_menu = request.getParameter("atMenu");
String status_pengajuan = (String)session.getAttribute("status_pengajuan_"+title_pengajuan+"_"+npm); //di set di dash_pp_baru.jsp
Vector v_list_prodi = Getter.getListProdi();
String kdkmp_domisili = Getter.getDomisiliKampus(Integer.parseInt(id_obj));
boolean sdh_ada_rules = Checker.isTableRuleSdhDiisi(target_thsms, kdpst, table_nm, kdkmp_domisili);
//System.out.println("status_pengajuan="+status_pengajuan);
//session.removeAttribute("status_pengajuan_"+npm); // harus di home utk removenya
%>
<style>
//#holder { border: 10px dashed #ccc; width: 300px; min-height: 100px; margin: 20px auto;}
//#holder.hover { border: 10px dashed #0c0; }
//#holder img { display: block; margin: 10px auto; }
#holder p { margin: 10px; font-size: 14px; }
progress { width: 50%; }
progress:after { content: '%'; }
.fail { background: #c00; padding: 2px; color: #fff; }
.hidden { display: none !important;}
</style>
</head>
<body>
<div id="header">
<ul>
	<li>
		<a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/indexPengajuanMhs.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=reqMhs" target="_self">BACK <span>&nbsp</span></a>
	</li>
<%
if(at_menu.equalsIgnoreCase("form")) {
		%>	
	<li>
		<a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/Pengajuan/<%=folder_pengajuan%>/form_pp.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&table=<%=table_nm %>&scope=<%=scope %>&atMenu=form" class="active" target="_self">FORM PENGAJUAN<span><%=title_pengajuan %></span></a>
	</li>
		<%			
}
else {
%>	
	<li>
		<a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/Pengajuan/<%=folder_pengajuan%>/form_pp.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&table=<%=table_nm %>&scope=<%=scope %>&atMenu=form" target="_self">FORM PENGAJUAN<span><%=title_pengajuan %></span></a>
	</li>
	<%		
}

%>
</ul>	
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<%
		//System.out.println("nm status_pengajuan ="+status_pengajuan);
		%>
		<!-- Column 1 start -->
		
			
		
		<%
if(!sdh_ada_rules) {
	%>
	<h1 style="text-align:center;font-weight:bold">TABEL PERSETUJUAN BELUM DIISI,<br>
	Harap hubungi Tata Usaha Fakultas.
	</h1>
<%	
}
else if(idkur>0) {	
	if(v_mk_sidang!=null&&v_mk_sidang.size()>0) {
			
		
		if(msg!=null && msg.equalsIgnoreCase("upd")) {
		%>
			<div align="right" style="font-size:0.5em">UPDATED : <%=AskSystem.getDateTime() %></div>
		<%
		}
		%>
		
		
		<%
		if(status_pengajuan==null || Checker.isStringNullOrEmpty(status_pengajuan) ||(status_pengajuan!=null && status_pengajuan.equalsIgnoreCase("ditolak")) || (status_pengajuan!=null && status_pengajuan.equalsIgnoreCase("batal"))) {
			if(status_pengajuan!=null) {
				if(status_pengajuan.equalsIgnoreCase("ditolak")) {
				%>
				<div style="text-align:center;font-size:1.5em">
				Pengajuan <%=title_pengajuan %> anda untuk tahun/sms <%=target_thsms %> ditolak, silahkan ajukan kembali setelah melengkapi persyaratan<br/>
				</div>
				<br/>
				<br/>
				<%	
				}
			}
		
			
		
			
		form = new String("ua");
		
		%>
		<form onsubmit="button_submit.disabled = true; return true;" action="go.ajukanPp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&target_thsms=<%=target_thsms %>&scope=<%=scope %>&table=<%=table_nm %>&folder_pengajuan=<%=folder_pengajuan%>" method="post">
		<div style="width:90%"><div id="pesan" align="left" style="font-size:1.5em;font-weight:bold">
		Dengan ini saya, <%=nmm %>, mengajukan permohonan untuk <%=title_pengajuan %>.<br><br>
		I. Tipe Ujian Akhir : 
		<select name="tipe_ujian" style="width:50%;height:25px;border:none;text-align-last:center;" required>
		<%
		ListIterator li = v_mk_sidang.listIterator();
		if(v_mk_sidang.size()>1) {
		%>
			<option value="">Pilih Tipe Ujian</option>
		<%	
		}
		while(li.hasNext()) {
			String nm_test = (String)li.next();
			%>
			<option value="<%=nm_test%>"><%=nm_test%></option>
		<%
		}
		%> 
		</select><br><br>
		II. Judul Penelitian / Karya Ilmiah :</div><br/>
		<textarea name="alasan" style="width:88%" placeholder="harap isi judul penelitian" rows="4" required></textarea><br/><br/>
		<br/>
		<article>
			<div id="pesan" align="left" style="font-size:1.5em;font-weight:bold">III. Pilih File Untuk di Upload Softcopy Penelitian:</div>
  			<div id="holder"></div> 
  			<div id="pesan1" align="left" style="font-size:1.5em;font-weight:bold"></div>
  			<div id="main">
  				<p id="filereader">File API & FileReader API not supported</p>
  				<p id="formdata">XHR2's FormData is not supported</p>
  				<p align="left" id="progress">XHR2's upload progress isn't supported</p>
  				<p align="left">Klik 'Choose File' untuk memilih file</p>
  				<p align="left" id="upload" class=hidden><input type="file" id="target_file"></p>
  				<p align="left"><progress id="uploadprogress" min="0" max="100" value="0">0</progress></p>
  				
  				
  			</div>  
		</article>


		<br/>
		
			<!--  section class="gradient">
				<div style="text-align:center">
				<button style="padding: 5px 50px;font-size: 20px;">Ajukan Permohonan</button>
				</div>
			</section -->
			
				
			
		</div>
		
			
        </form>
        <%
        }
		else if(status_pengajuan!=null && status_pengajuan.equalsIgnoreCase("inprogress")) {
		%>
		<div style="text-align:center;font-size:1.5em">
		Pengajuan <%=title_pengajuan %> anda untuk tahun/sms <%=target_thsms %> sedang dalam proses persetujuan, <br/>
		Apakah anda mau membatalkan pengajuan <%=title_pengajuan %> anda?<br/><br/>
		<form action="go.batalPengajuan?target_thsms=<%=target_thsms %>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=<%=cmd %>&scope=<%=scope %>&table=<%=table_nm %>&folder_pengajuan=<%=folder_pengajuan%>" method="post" >
		<br/>
		<section class="gradient">
				<button style="padding: 5px 50px;font-size: 20px;">Klik untuk Batalkan Pengajuan</button>
		</section>
			<!--  div style="text-align:center">
				<input type="submit" value="Klik untuk Batalkan Pengajuan" style="color:red;height:35px" name="button_submit" />
			</div-->
		</form>
		
		</div>
		<%	
		}
		else if(status_pengajuan!=null && status_pengajuan.equalsIgnoreCase("disetujui")) {
		%>
		<div style="text-align:center;font-size:1.5em">
		Pengajuan <%=title_pengajuan %> anda untuk tahun/sms <%=target_thsms %> sudah diterima dan disetujui
		</div>
		<%			
		}
	}
	else {
		%>
		<h1 style="text-align:center;font-weight:bold">Matakuliah Sidang Belum Ditentukan,<br>
		Harap hubungi Tata Usaha Fakultas.
		</h1>
	<%		
	}
}	
else {
	%>
		<h1 style="text-align:center;font-weight:bold">Kurikulum Mahasiswa Belum Ditentukan,<br>
		Harap hubungi Tata Usaha Fakultas.
		</h1>
	<%	
}
        %>
		<!-- Column 1 end -->
		
	</div>
</div>
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
    holder.innerHTML += '<p style=\"text-align:center\">Uploaded ' + file.name + ' ' + (file.size ? (file.size/1024|0) + 'K' : '');
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
      <%
  		String nu_file_name = ""+AskSystem.getCurrentTimestamp();
  		while(nu_file_name.contains(" ")) {
  			nu_file_name = nu_file_name.replace(" ", "_");	
  		}
  		//System.out.println("2");
  		while(nu_file_name.contains(":")) {
  			nu_file_name = nu_file_name.replace(":", "-");	
  		}
  		while(nu_file_name.contains(".")) {
  			nu_file_name = nu_file_name.replace(".", "_");	
	  	}
  		nu_file_name = "UjianAkhir_"+nu_file_name;
      %>
      xhr.open('POST', 'upload.dragFile?form=<%=form%>&target_npm=<%=npm%>&nu_file_name=<%=nu_file_name%>');
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
    document.getElementById('holder').style.visibility = "hidden";
    document.getElementById('main').style.visibility = "hidden";
    document.getElementById('pesan1').innerHTML ="";
    //var alm= '<%=Constants.getRootWeb()%>/InnerFrame/input_file/uploader_form.jsp?form=';
    //var goback= '<%=Constants.getRootWeb()%>/InnerFrame/input_file/index_uploader.jsp';
    document.getElementById('pesan1').innerHTML += '<p style=\"font-size:0.7em;color:#00510C\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspFile berhasil disimpan dgn name \"<%=nu_file_name%>\"<br>' +
    '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspAnda dapat mengakses melalui tab menu \"File ARSIP - KARYA ILMIAH\"</p><br><br>' +
    '<input type=\"hidden\" name=\"file_nmm\" value=\"<%=nu_file_name%>\">'+
    '<section class=\"gradient\">'+
		'<div style=\"text-align:center\">' +
			'<button style=\"padding: 5px 50px;font-size: 20px;\">Ajukan Permohonan</button>' +
		'</div>' +
	'</section><br>';
    //'<form action="'+alm+'" method="POST"><br><input type="submit" value="Clear Form" style="width:200px;height:35px" /></form>' ;
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
</body>
</html>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<title>Insert title here</title>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>	
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.pengajuan.ua.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/js/accordion/accordion.css" media="screen" />
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"></script>
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/accordion/accordion-center.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
<script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<%
//System.out.println("siop");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
//String stm = (String)session.getAttribute("status_akhir_mahasiswa");
Vector v= null;
SearchDbUa sdbu = new SearchDbUa(validUsr.getNpm());

%>
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

</head>
<body>
<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
			<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai</p>
</div>
<div id="main">
	<div id="header">
	<%@ include file="innerMenu.jsp"%>
	</div>
<div class="colmask fullpage">
	<div class="col1">

		
	<%	
//	String objId = request.getParameter("id_obj");
	//System.out.println("yoa kdpst="+kdpst);
	//System.out.println("npm="+npm);
	String status_akhir_mahasiswa = AddHocFunction.getAndSyncStmhsBetweenTrlsmAndMsmhs(kdpst, npm);
	//System.out.println("status_akhir_mahasiswa="+status_akhir_mahasiswa);
	session.setAttribute("status_akhir_mahasiswa", status_akhir_mahasiswa); //status mahasiswa bukan status user yg login
	session.setAttribute("status_malaikat", malaikat);
	
	//out.print(Checker.getObjDesc(Integer.parseInt(v_id_obj))+"<br>");
	
	/*if(validUsr.getObjNickNameGivenObjId().contains("ADMIN")) {				
	%>
	<p style="text-align:right">
	<%="<br/>"+validUsr.getUsrnameAndPwd(npm)+"<br/>" %>
	</p>
	<%		//}
	}
	*/
	%>
		<!-- Column 1 start -->
		
		<br />
		<div class="container-fluid">
			<div class="row">
    
    <%
    if(validUsr.getObjNickNameGivenObjId().contains("OPERATOR")) {
    %>
    <center>
    <table width="95%">
    	<tr>
    		<td style="font-size:.8em;font-weight:bold">NAMA</td>
    		<td>:</td>
    		<%
    		if(malaikat!=null && malaikat.equalsIgnoreCase("true")) {
    			%>
        	<td style="font-size:.8em;font-weight:bold;color:red"><%=nmm.toUpperCase() %></td>
        		<%
    		}
    		else {
    		%>
    		<td style="font-size:.8em;font-weight:bold"><%=nmm.toUpperCase() %></td>
    		<%
    		}
    		%>
    		<td style="font-size:.8em;font-weight:bold">STATUS</td>
    		<td>:</td>
    		<td style="font-size:.8em;font-weight:bold">
    <%	
    	//status_akhir_mahasiswa = K/C/L/D`status daftar ulang`status error
    	StringTokenizer stt = new StringTokenizer(status_akhir_mahasiswa,"`");
    	String status_stmhs = stt.nextToken();
    	String status_registrasi = stt.nextToken();
    	String status_error = stt.nextToken();
    	if(status_akhir_mahasiswa.equalsIgnoreCase("K")||status_akhir_mahasiswa.startsWith("K`")) {
    %>
    [KELUAR]
    <%	
    	}
    	else if(status_akhir_mahasiswa.equalsIgnoreCase("A")||status_akhir_mahasiswa.startsWith("A`")) {
    	%>
    [AKTIF]
        <%		
    	}
    	else if(status_akhir_mahasiswa.equalsIgnoreCase("D")||status_akhir_mahasiswa.startsWith("D`")) {
        	%>
    [D.O.]
            <%		
        }
		else if(status_akhir_mahasiswa.equalsIgnoreCase("L")||status_akhir_mahasiswa.startsWith("L`")) {
		%>
	[LULUS]
	    <%		
    	}
		else if(status_akhir_mahasiswa.equalsIgnoreCase("N")||status_akhir_mahasiswa.startsWith("N`")) {
			
		%>
	[TIDAK AKTIF]
	    <%	  	
    	}
		else if(status_akhir_mahasiswa.equalsIgnoreCase("P")||status_akhir_mahasiswa.startsWith("P`")) {
			%>
		[PINDAH PRODI]
		    <%	  	
	    }
    	if(!Checker.isStringNullOrEmpty(status_registrasi)&&!Checker.isStringNullOrEmpty(status_stmhs)&&status_stmhs.equalsIgnoreCase("N")) {
    		out.print("["+status_registrasi.toUpperCase()+"]");
    	}
    	if(!Checker.isStringNullOrEmpty(status_error)) {
    		out.print("["+status_error.toUpperCase()+"]");
    	}
    %>
    		</td>
    	</tr>	
    	<tr>
    		<td style="font-size:.8em;font-weight:bold">THSMS MASUK</td>
    		<td>:</td>
    		<td style="font-size:.8em;font-weight:bold"><%=v_smawl %></td>
    		<td style="font-size:.8em;font-weight:bold">SHIFT</td>
    		<td>:</td>
    		<td style="font-size:.8em;font-weight:bold"><%=v_shift %></td>
    	</tr>
    	<%
    	
    	if(validUsr.getObjNickNameGivenObjId().contains("ADMIN")) {	
    		String info = validUsr.getUsrnameAndPwd(npm);
    		if(info!=null) {
    			StringTokenizer st = new StringTokenizer(info,"`");
    	%>
    	<tr>
    		<td style="font-size:.8em;font-weight:bold">USER</td>
    		<td>:</td>
    		<td style="font-size:.8em;font-weight:bold">
    		<%
    			if(st.hasMoreTokens()) {
    				out.print(st.nextToken());	
    			}
    		%>
    		</td>
    		<td style="font-size:.8em;font-weight:bold">PWD</td>
    		<td>:</td>
    		<td style="font-size:.8em;font-weight:bold">
    		<%
    			if(st.hasMoreTokens()) {
    				out.print(st.nextToken());	
    			}
    		%>
			</td>
    	</tr>
    	<%
    		}
    	}
    	else if(validUsr.getObjNickNameGivenObjId().contains("OPERATOR") && Checker.getObjDesc(Integer.parseInt(v_id_obj)).contains("MHS")) {
    		String info = validUsr.getUsrnameAndPwd(npm);
    		if(info!=null) {
    			StringTokenizer st = new StringTokenizer(info,"`");
    	%>
    	<tr>
    		<td style="font-size:.8em;font-weight:bold">USER</td>
    		<td>:</td>
    		<td style="font-size:.8em;font-weight:bold">
    		<%
    			if(st.hasMoreTokens()) {
    				out.print(st.nextToken());	
    			}
    		%>
    		</td>
    		<td style="font-size:.8em;font-weight:bold">PWD</td>
    		<td>:</td>
    		<td style="font-size:.8em;font-weight:bold">
    		<%
    			if(st.hasMoreTokens()) {
    				out.print(st.nextToken());	
    			}
    		%>
			</td>
    	</tr>
    	<%
    		}
    	}
    	%>
    </table>
    </center>
    <%
    }
		Vector v_riwayat_pengajuan_ujian = sdbu.getRiwayatUaForDashboardMhs(v_npmhs);
		if(v_riwayat_pengajuan_ujian!=null && v_riwayat_pengajuan_ujian.size()>0) {
		%>
				<div class="col-sm-4" >
					<div class="accordion">
    					<div class="accordion-section">
        					<a class="accordion-section-title-center" href="#accordion-1">RIWAYAT UJIAN SIDANG</a>
         					<div id="accordion-1" class="accordion-section-content">
         					<ul>
        	<%
		
			ListIterator liu = v_riwayat_pengajuan_ujian.listIterator();
        	int norut = 1;
			if(liu.hasNext()) {
				%>
				
				<%
				do {
					String brs = (String)liu.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					//li.add(kdkmk+"`"+date+"`"+time+"`"+status);
					String kdkmk = st.nextToken();
					String date = st.nextToken();
					String time = st.nextToken();
					String status = st.nextToken();
					out.println("<li>Ujian "+kdkmk+" telah dilaksanakan pada tanggal "+Converter.convertFormatTanggalKeFormatDeskriptif(date)+" - "+time+"</li>");
					norut++;
				}
				while(liu.hasNext());
			}
		
		%>
							</ul>
        					</div>
						</div>
					</div>
		 		</div>
		<%
			}

		
		//System.out.println(validUsr.isUsrAllowTo_vFinal("deleteMhs", npm));
		if(validUsr.isUsrAllowTo("deleteMhs", npm, obj_lvl)){
			//System.out.println("masuk");
		%>
				<div class="col-sm-4" >
					<div class="accordion">
    					<div class="accordion-section">
        					<a class="accordion-section-title-center" href="#accordion-1">HAPUS MAHASISWA INI</a>
         					<div id="accordion-1" class="accordion-section-content">
								<form action="validasiHapusMhs.jsp">
									<input type="submit" value="HAPUS MAHASISWA" style="color:red;font-weight:bold" />
									<input type="hidden" name="nmm" value="<%=nmm %>" />
									<input type="hidden" name="npm" value="<%=npm %>" />
									<input type="hidden" name="objId" value="<%=objId %>" />
									<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
									<input type="hidden" name="kdpst" value="<%=kdpst %>" />
									<input type="hidden" name="backTo" value="dashHomeMhs.jsp" />	
								</form>
								<br>
								<form action="validasiResetPengajuan.jsp">
									<input type="hidden" name="nmm" value="<%=nmm %>" />
									<input type="hidden" name="npm" value="<%=npm %>" />
									<input type="hidden" name="objId" value="<%=objId %>" />
									<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
									<input type="hidden" name="kdpst" value="<%=kdpst %>" />
									<input type="hidden" name="backTo" value="dashHomeMhs.jsp" />
									TARGET THSMS&nbsp&nbsp&nbsp&nbsp: <input type="text" name="target_thsms" style="width:100px"><br/>
									TIPE PENGAJUAN : <input type="text" name="tipe_pengajuan" style="width:50px"><br>
									<input type="submit" value="RESET PENGAJUAN" style="color:red;font-weight:bold" />	
								</form>
							</div>
						</div>
					</div>
		 		</div>
		 		
		 		<div class="col-sm-4" >
					<div class="accordion">
    					<div class="accordion-section">
        					<a class="accordion-section-title-center" href="#accordion-1">AKSES MOODLE</a>
         					<div id="accordion-1" class="accordion-section-content">
								<form action="go.PrepFormMoodle">
									<input type="submit" value="PROSES AKSES" style="color:red;font-weight:bold" />
									<input type="hidden" name="nmm" value="<%=nmm %>" />
									<input type="hidden" name="npm" value="<%=npm %>" />
									<input type="hidden" name="objId" value="<%=objId %>" />
									<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
									<input type="hidden" name="kdpst" value="<%=kdpst %>" />
									<input type="hidden" name="backTo" value="dashHomeMhs.jsp" />	
								</form>
							</div>
						</div>
					</div>
		 		</div>
		<%
		}
		%>
    			<div class="col-sm-4" > </div>
  			</div>
		</div>	
		
		
		</div>	
	</div>
</div>	

</body>
</html>
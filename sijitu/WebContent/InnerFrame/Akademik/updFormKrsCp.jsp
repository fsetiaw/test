<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.SearchDb" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@page import="beans.setting.Constants"%>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
//System.out.println("kkkkk");
String usrReadOnlyNoAksesUpdateShiftOrInsert=""+request.getParameter("usrReadOnlyNoAksesUpdateShiftOrInsert");
//beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
ListIterator liSdh = null;
ListIterator liBlm = null;
ListIterator liCp = null;
Vector vSeataraYgDibuka = (Vector)request.getAttribute("vSeataraYgDibuka");
////System.out.println("vSeataraYgDibukaAupdFormKrsCp size = "+vSeataraYgDibuka.size());
Vector vSdh =(Vector) request.getAttribute("vSdh");
String kelasSesuaiShift = request.getParameter("sesuaiShift");
////System.out.println("kelasSesuaiShift0="+kelasSesuaiShift);
//String kelasSemuaShift = request.getParameter("semuaShift"); //sudah tidak dipakai lagi
String targetThsms = request.getParameter("targetThsms");
boolean notThsmsKrs = !targetThsms.equalsIgnoreCase(Checker.getThsmsKrs());
boolean whiteList = false;
boolean usrMhs = false;
//if(validUsr.getObjNickNameGivenObjId().contains("MHS") || validUsr.getObjNickNameGivenObjId().contains("mhs") ) {
//	usrMhs = true;
//}
////System.out.println("1");
String whiteListMode = request.getParameter("whiteListMode"); // urusan thsms, input krs diluar thsms krs
if(whiteListMode.equalsIgnoreCase("true")) { //urusan thsms, input krs diluar thsms krs
	whiteList = true;
}
////System.out.println("whiteListMode="+whiteListMode);
////System.out.println("aturan2="+kelasSemuaShift);
////System.out.println("2");
if(vSdh!=null && vSdh.size()>0) {
	liSdh = vSdh.listIterator();
}
else {
	vSdh =(Vector) session.getAttribute("vSdh");
	session.removeAttribute("vSdh");
	if(vSdh!=null && vSdh.size()>0) {
		liSdh = vSdh.listIterator();
	}
	else {
		liSdh = new Vector().listIterator();
	}	
}
////System.out.println("3");
Vector vBlm =(Vector) request.getAttribute("vBlm");
if(vBlm!=null && vBlm.size()>0) {
	liBlm = vBlm.listIterator();
}
else {
	vBlm =(Vector) session.getAttribute("vBlm");
	session.removeAttribute("vBlm");
	if(vBlm!=null && vBlm.size()>0) {
		liBlm = vBlm.listIterator();
	}
	else {
		liSdh = new Vector().listIterator();
	}	
}
////System.out.println("4");
Vector vCp =(Vector) request.getAttribute("vCp");
if(vCp!=null && vCp.size()>0) {
	liCp = vCp.listIterator();
}
else {
	vCp =(Vector) session.getAttribute("vCp");
	session.removeAttribute("vCp");
	if(vCp!=null && vCp.size()>0) {
		liCp = vCp.listIterator();
	}
	else {
		liCp = new Vector().listIterator();
	}
}
////System.out.println("5");
Vector v = null;
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
boolean cpMode = false;
if(vCp!=null && vCp.size()>0) {
	cpMode = true;
}

%>

<style>
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
</head>
<body>

<div id="header">
<!--   include file="../innerMenu.jsp" -->
<%@ include file="../krsKhsSubMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">

	<!-- Column 1 start -->

<%
boolean sudah_mengajukan_daftar_ulang = Checker.sudahMengajukanDaftarUlang(v_kdpst, v_npmhs);
%>
<br>
<%
if(!notThsmsKrs && !sudah_mengajukan_daftar_ulang) {
%>
<br>
<div style="text-align:center;font-size:1.3em;font-weight:bold">
	Harap Mengajukan Daftar Ulang Secara Online Menggunakan Menu `Pengajuan` Terlebih Dahulu.
</div>
<%	
}
else {
%>
		<br />
		<%
		////System.out.println("vshift="+v_shift);
boolean allowedAllThsms = validUsr.isUsrAllowTo("insertKrsAllThsms", kdpst);
		/*
		liBlm = vBlm.listIterator();
		while(liBlm.hasNext()) {
			out.print((String)liBlm.next()+"<br/>");
		}
		liBlm = vBlm.listIterator();
		

		ListIterator litm = vCp.listIterator();
		while(litm.hasNext()) {
			String brs1 = (String)litm.next();
		
			if(brs1.startsWith("idkmk")) {

				out.print(brs1+"<br/>");
			}
			else {
				if(brs1.startsWith("shift")) {
					out.print(brs1+"<br/>");
					out.print("------------------------------------------------------");
					Vector vKls = (Vector)litm.next();
					ListIterator liKls = vKls.listIterator();
					while(liKls.hasNext()) {
						out.print((String)liKls.next()+"<br/>");
					}
				}
			}	
		}

		out.print("vsudah<br/>");
		while(liSdh.hasNext()) {
			String baris = (String)liSdh.next();
			out.println(baris+"<br/>");
		}
		out.print("belum<br/>");
		while(liBlm.hasNext()) {
			String baris = (String)liBlm.next();
			out.println(baris+"<br/>");
		}
*/		
		/*
		msg di pas dari HistoryKrsKhs servlet, bilai msg !=null berarti blum ditentukan kurikulumnya
		*/
if(!Checker.isStringNullOrEmpty(msg)) {
	SearchDb sdb = new SearchDb();
	String nmpst = sdb.getNmpst(kdpst);
	//kurikulum blm ditentukan
	Vector v_cf = validUsr.getScopeUpd7des2012("hasEditMkKurikulum");
	if(v_cf.size()>0) {
		%>
		
		<h2 align="center"><br/>Harap <a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/editTargetKurikulum.jsp?kdpst_nmpst=<%=v_kdpst%>,<%=nmpst %>" target="inner_iframe" class="active">Tentukan Kurikulum Untuk <%=nmm %> </a>Terlebih Dahulu</h2>
		<br/>
		<%
	}
	else {
				%>
				<h2 align="center">Kurikulum Untuk <%=nmm %> Belum Diisi oleh Tata Usaha</h2>
				<%		
	}
}
else {
			
		%>
		
<P>
					
    <form action="proses.updKrsCp" >	
    <input type="hidden" name="id_obj" value="<%=v_id_obj %>" />
    <input type="hidden" name="nmm" value="<%=v_nmmhs %>" />
    <input type="hidden" name="npm" value="<%=v_npmhs %>" />
    <input type="hidden" name="kdpst" value="<%=v_kdpst %>" />
    <input type="hidden" name="kdjen" value="<%=v_kdjen %>" />
    <input type="hidden" name="obj_lvl" value="<%=v_obj_lvl %>" />	
    		<table class="table" style="width:95%">
		<tr>
			<td style="background:#369;color:#fff;text-align:center;height:30px;font-size:25px" colspan="4"><label><B>PENGISIAN KRS</B></label>
			<%
			/* 
			*untuk CP mode maka thsms PMB only **** updated diganti thsms krs
			*dapat di overider kalo boleh all thsms
			*/
					%>
				<select name="thsms" style="font-size:20px;">
					<%
					//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					/*
					FOR FURTURE MODIFIKASI BILA OPERATOR BOLEH MENGISI KRS ALL THSMS
					*/
	if(true) {	
					//if(!allowedAllThsms) {
						
		String keter_thsms = ""+targetThsms;
		String value_thsms = ""+targetThsms;
						%>
						<option value="<%=value_thsms %>" selected="selected"><%=keter_thsms%></option>
						<%
	}
	/*
	else {
	//String keter_thsms = ""+targetThsms;
	//String value_thsms = ""+targetThsms;
	//buat range thsms - 10
		String base_thsms = new String(targetThsms);
		for(int i=0;i<10;i++) {
			base_thsms = Tool.returnPrevThsmsGivenTpAntara(base_thsms);
		}
						
		for(int i=0;i<10;i++) {
			base_thsms = Tool.returnNextThsmsGivenTpAntara(base_thsms);
							%>
						<option value="<%=base_thsms %>" selected="selected"><%=base_thsms%></option>
							<%
		}
						
	}
					*/
					%>
				</select>
			</td>
		</tr>
    	
	<%
	String prevSms = "";
	boolean adaData=false; //cuma buat flag utk submit button
	String atPage="update Form Krs";
	int i=0;
/*

AKAN ADA MASALAH PADA SAAT RESET/PEMBATALAN PENGGABUNGAN KEALAS DIMANA
TRNLM INIT_CUID AKAN KE RESET PADA ID BERDASARKAN SHIFT, JADI BILA DALAM 1 SHIFT ADA > 1 KELAS YG DIBUKA MAKA AKAN 
	KE RESET KE KELAS BERDASARKAN SHIFT, SEHINGGA HARUSNYA DI CEK BERDASARKAN SHIFT DAN NO KELAS PARALELNYA.



*/

	if(vBlm!=null && vBlm.size()>0) {
//		ListIterator lib = vBlm.listIterator();
//		while(lib.hasNext()) {
//			String blm = (String)lib.next();
//			////System.out.println("blm="+blm);
//		}
	%>
		<tr>
			<td style="background:#369;color:#fff;text-align:center;width:15%"><label><B>KODE</B> </label></td>
			<td style="background:#369;color:#fff;text-align:left;width:30%;padding:0 0 0 5px"><label><B>MATAKULIAH</B> </label></td>
			<td style="background:#369;color:#fff;text-align:center;width:5%"><label><B>SKS</B> </label></td>
			<td style="background:#369;color:#fff;text-align:center;width:50%" ><label><B>INFO KELAS</B> </label></td>
		</tr>
	<%	
		boolean first = true;
		adaData=true;
		ListIterator li = vBlm.listIterator();
		while(li.hasNext()) {
			i++;
			String baris = (String)li.next();
			////System.out.println("barisan0="+baris);
			StringTokenizer st = new StringTokenizer(baris,",");
			String idkmk = st.nextToken();
			////System.out.println("idkmkBlm="+idkmk);
			String kdkmk = st.nextToken();
			////System.out.println("kdkmkBlm="+kdkmk);
			String nakmk = st.nextToken();
			
			////System.out.println("nakmkBlm="+nakmk);
			String skstm = st.nextToken();
			////System.out.println("skstmBlm="+skstm);
			String skspr = st.nextToken();
			////System.out.println("sksprBlm="+skspr);
			String skslp = st.nextToken();
			////System.out.println("skslpBlm="+skslp);
			int sksmk = (Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue());
			String semes = st.nextToken();
			////System.out.println("semesBlm="+semes);
			
	
			boolean avail = false;
			String idkur = "null";
			String shift = "null";
			String norutKelasParalel = "null";
			String currStatus = "null";
			String npmdos = "null";
			String npmasdos = "null";
			String canceled = "null";
			String kodeKelas = "null";
			String kodeRuang = "null";
			String kodeGedung = "null";
			String kodeKampus = "null";
			String tknDayTime = "null";
			String nmmdos = "null";
			String nmmasdos = "null";
			String enrolled = "null";
			String maxEnrolled = "null";
			String minEnrolled= "null";
			String subNakmk= "null";
			String uniqueId= "null";
			String cuid_cp =  "null";
			String idkmk_cp =  "null";		
			String kode_gab_cp =  "null";				
//			////System.out.println("v_shift="+v_shift);
//			////System.out.println("kelasSesuaiShift="+kelasSesuaiShift);
			
			if(st.hasMoreTokens()) {
				//kalo masih ada token berarti ada kelas yg di offer		
				if(kelasSesuaiShift.equalsIgnoreCase("true")) {
					if(baris.contains(v_shift)) {
						avail = true;
					}
					
				}
				else {
					avail = true;
				}
			}
//			////System.out.println("avail="+avail);
			//HEADER MATAKILUAH SEMESTER:
			if(first || !semes.equalsIgnoreCase(prevSms)) {
				prevSms=""+semes;
				first = false;
				%>		
		<tr>
			<td style="background:#9FADB4;color:#000;text-align:center;width:100%" colspan="4"><label><B>MATAKULIAH SEMESTER :
					<%
					if(semes==null || semes.equalsIgnoreCase("null")) {
						out.print("?? - semester tiap matakuliah belum diisi di kurikulum");
					}
					else {
						out.print(semes);
					}
					%>
			</B> </label></td>
		</tr>
			<%
			}
			
		
	%>	
		<tr>
			<td style="color:#000;text-align:center;"><label><B><%=kdkmk %></B> </label></td>
			<td style="color:#000;text-align:left;padding:0 0 0 5px"><label><B><%=nakmk.replace("tandaKoma",",") %></B> </label></td>
			<td style="color:#000;text-align:center;"><label><B><%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue())%></B> </label></td>
			<td style="background: #E0E0E0">
			<%
			if(!avail) {
			%>
			<div style="font-size:0.8em;text-align:center">KELAS TIDAK DI TAWARKAN PADA SEMESTER INI</div>
			<%	
			}
			else {
				if((avail && !notThsmsKrs) || whiteList) { //!notThsmsKrs = berarti thsms krs
					boolean match = false;	
			%>
			<select name="kelasSelected" style="width:100%;height:100%;border:none;text-align-last:center">
				<option value="null">--PILIH KELAS--</option>
				<%
					while(st.hasMoreTokens()) {
						idkur = st.nextToken();
					
						shift = st.nextToken();
						////System.out.println("shift="+shift);
						norutKelasParalel = st.nextToken();
						////System.out.println("norutKelasParalel="+norutKelasParalel);
						currStatus = st.nextToken();
						////System.out.println("currStatus="+currStatus);
						npmdos = st.nextToken();
						////System.out.println("npmdos="+npmdos);
						npmasdos = st.nextToken();
						////System.out.println("npmasdos="+npmasdos);
						canceled = st.nextToken();
						////System.out.println("canceled="+canceled);
						kodeKelas = st.nextToken();
						////System.out.println("kodeKelas="+kodeKelas);
						kodeRuang = st.nextToken();
						////System.out.println("kodeRuang="+kodeRuang);
						kodeGedung = st.nextToken();
						////System.out.println("kodeGedung="+kodeGedung);
						kodeKampus = st.nextToken();
						////System.out.println("kodeKampus="+kodeKampus);
						tknDayTime = st.nextToken();
						////System.out.println("tknDayTime="+tknDayTime);
						nmmdos = st.nextToken();
						////System.out.println("nmmdos="+nmmdos);
						nmmasdos = st.nextToken();
						////System.out.println("nmmasdos="+nmmasdos);
						enrolled = st.nextToken();
						////System.out.println("enrolled="+enrolled);
						maxEnrolled = st.nextToken();
						////System.out.println("maxEnrolled="+maxEnrolled);
						minEnrolled= st.nextToken();
						cuid_cp= st.nextToken();//tambahan variable
						idkmk_cp= st.nextToken();//tambahan variable
						//begini karna debugging aja
						 
						subNakmk= st.nextToken();
						kode_gab_cp = st.nextToken();
						uniqueId= cuid_cp;

					/*
					*filter disini bila ada : !kelas canceled
					*/
						if(kelasSesuaiShift.equalsIgnoreCase("true")) {
							////System.out.println("kelasSesuaiShift1="+kelasSesuaiShift);
							if(Checker.isStringNullOrEmpty(kode_gab_cp)) {
								
							
								if(shift.equalsIgnoreCase(v_shift) && canceled.equalsIgnoreCase("false")) {
									match = true;
						%>
						<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>"><%=Checker.pnn(subNakmk,";")%>  <%=Checker.pnn(Converter.getKonsentrasiKurikulum(idkur),"[]")%><%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
						<%
								}
							}
							else {
								//jika kelas gabungan
								/*
								KELAS GABUNGAN PEDULI SHIFT krn tetap kelas harus ditawarkan terlebih dahulu 
								tapi tiak peduli canceled karen bila kelas yg digabungkan
								maka statusnya kabce
								*/
								if(shift.equalsIgnoreCase(v_shift)) {
									
									match = true;
										//match pada token pertama
										////System.out.println("-2- "+kdkmk+" "+cuid_cp+" null");
										%>
						<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_cp%>"><%=Checker.pnn(Converter.getKonsentrasiKurikulum(idkur),"[]")%><%=Checker.pnn(subNakmk,";")%>  <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
										<%	
									while(st.hasMoreTokens()) {
										st.nextToken(); //looping biar no more tokens
									}
									
								}
							}
						}
						else {
						//TIDAK PERLU SESUAI SHIFT
							if(Checker.isStringNullOrEmpty(kode_gab_cp) && canceled.equalsIgnoreCase("false")) {
								match=true;
							
								%>
					<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>"><%=Checker.pnn(Converter.getKonsentrasiKurikulum(idkur),"[]")%><%=Checker.pnn(subNakmk,";")%>  <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
								<%
							}
							else {
								
								if(canceled.equalsIgnoreCase("false")) {
								/*
								KELAS GABUNGAN
								KARENA VALUE DIATAS ADALAH TOKEN PERTAMA MAKA ITU KELAS INTI
								SEKARANG TINGGAL DICEK APA SHIFT MHS INI TERMASUK YG DIGABUNG, BILA TIDAK INIT CUIDDI TRNLMNYA KOSONG
								,
								*/
								//karena bebas shift jadi value di token pertama / kelas inti 
								//loop abisin token 
								////System.out.println("-3- "+kdkmk+" "+cuid_cp+" null");
								%>
					<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_cp%>"><%=Checker.pnn(Converter.getKonsentrasiKurikulum(idkur),"[]")%><%=Checker.pnn(subNakmk,";")%>  <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
								<%
								}	
							}
						}
					}
					if(!match) {
					%>
					<option value="null" selected>KELAS TIDAK DI TAWARKAN PADA SEMESTER INI</option>
					<%
					}
				%>
				
			</select>
			
			<%
				}
			}
			%>
			</td>
		</tr>
<%
			if(!li.hasNext()) {
				if(vSdh==null ||vSdh.size()<1) {
						
					if((adaData && !notThsmsKrs) || whiteList)  {
						//System.out.println("2");
						if(usrReadOnlyNoAksesUpdateShiftOrInsert==null || usrReadOnlyNoAksesUpdateShiftOrInsert.equalsIgnoreCase("false")) { //addhoc - bila read only ngga boleh ada button ini
							//System.out.println("3");
						%>
						<tr>
							<td colspan="6" style="text-align:center;background:#369;padding:5px 5px"><input type="submit" value="UPDATE KRS" style="width:60%;height:35px" /></td>
						</tr>
						<%
						}
					}
				}	
%>
	</table>	
<%				
				
			}
		}
	}		

	////System.out.println("sampai kesini 3");
	if(vSdh!=null && vSdh.size()>0) {	
		
		%>	
	<table class="table" style="width:95%">
		<tr>
			<td style="background:#369;color:#fff;text-align:center;font-size:25px" colspan="6"><label><B>MATAKULIAH YANG SEDANG DAN SUDAH DIAMBIL</B></label></td>
		</tr>
		<tr>
			<td style="background:#9FADB4;color:#000;text-align:center;width:10%"><label><B>KODE</B> </label></td>
			<td style="background:#9FADB4;color:#000;text-align:left;width:25%;padding:0 0 0 5px"><label><B>MATAKULIAH</B> </label></td>
			<td style="background:#9FADB4;color:#000;text-align:center;width:5%;font-size:0.8em"><label><B>NILAI</B> </label></td>
			<td style="background:#9FADB4;color:#000;text-align:center;width:5%;font-size:0.8em"><label><B>BOBOT</B> </label></td>
			<td style="background:#9FADB4;color:#000;text-align:center;width:5%;font-size:0.8em"><label><B>THSMS</B> </label></td>
			<td style="background:#9FADB4;color:#000;text-align:center;width:50%"><label><B>PILIHAN KELAS</B> </label></td>
		</tr>
		<%	
		boolean first = true;
		adaData=true;
		ListIterator li1 = vSdh.listIterator();
	//mo di sort berdasarkan nilai terrendah
		Vector v2 = new Vector();
		ListIterator li2 = v2.listIterator();
	
		while(li1.hasNext()) {
			//29,20091,KU 1033,PENDIDIKAN KEWARGANEGARAAN,B,3.0
		
			String baris = (String)li1.next();
			////System.out.println("barisan3="+baris);
			//liS.add(idkmk+","+thsmsh+","+kdkmkh+","+nakmk.replace(",","tandaKoma")+","+nlakhh+","+boboth+","+cuid+","+cuid_init);
			StringTokenizer st = new StringTokenizer(baris,",");
			String idkmk=st.nextToken();
			String thsms=st.nextToken();
			String kdkmk=st.nextToken();
			String nakmk=st.nextToken();
			String nlakh=st.nextToken();
			String bobot=st.nextToken();
			String cuidh=st.nextToken();
			String cuid_inith=st.nextToken();

			boolean avail = false;
			String idkur = "null";
			String shift = "null";
			String norutKelasParalel = "null";
			String currStatus = "null";
			String npmdos = "null";
			String npmasdos = "null";
			String canceled = "null";
			String kodeKelas = "null";
			String kodeRuang = "null";
			String kodeGedung = "null";
			String kodeKampus = "null";
			String tknDayTime = "null";
			String nmmdos = "null";
			String nmmasdos = "null";
			String enrolled = "null";
			String maxEnrolled = "null";
			String minEnrolled= "null";
			String subNakmk = "null";
			String uniqueId = "null";
			String cuid_cp =  "null";
			String idkmk_cp =  "null";	
			String kode_gab_cp = "null";
			
			
			if(st.hasMoreTokens()) {
				//kalo masih ada token berarti ada kelas yg di offer		
				if(kelasSesuaiShift.equalsIgnoreCase("true")) {
					if(baris.contains(v_shift)) {
						avail = true;
					}
					
				}
				else {
					avail = true;
				}
			}
			
			%>
		<tr>
			<td style="color:#000;text-align:center;width:60px"><label><B><%=kdkmk %></B> </label></td>
			<td style="color:#000;text-align:left;width:330px"><label><B><%=nakmk.replace("tandaKoma",",") %></B> </label></td>
			<td style="color:#000;text-align:center;width:10px;font-size:0.8em"><label><B><%=nlakh %></B> </label></td>
			<td style="color:#000;text-align:center;width:10px;font-size:0.8em"><label><B><%=bobot %></B> </label></td>
			<td style="color:#000;text-align:center;width:10px;font-size:0.8em"><label><B><%=thsms %></B> </label></td>
			<td style="background: #E0E0E0">
			<%
			if(!avail) {
				%>
				<div style="font-size:0.8em;text-align:center">KELAS TIDAK DI TAWARKAN PADA SEMESTER INI</div>
				<%	
			}
			else {
				if((avail && !notThsmsKrs) || whiteList) {
					boolean match = false;
					%>
					<select name="kelasSelected" style="width:100%;height:100%;border:none;text-align-last:center">
						<option value="null">--PILIH KELAS--</option>
						<%
					while(st.hasMoreTokens()) {
						idkur = st.nextToken();
						shift = st.nextToken();
						norutKelasParalel = st.nextToken();
						currStatus = st.nextToken();
						npmdos = st.nextToken();
						npmasdos = st.nextToken();
						canceled = st.nextToken();
						kodeKelas = st.nextToken();
						kodeRuang = st.nextToken();
						kodeGedung = st.nextToken();
						kodeKampus = st.nextToken();
						////System.out.println("kodeKampus="+kodeKampus);
						tknDayTime = st.nextToken();
						nmmdos = st.nextToken();
						nmmasdos = st.nextToken();
						enrolled = st.nextToken();
						maxEnrolled = st.nextToken();
						minEnrolled= st.nextToken();
						cuid_cp = st.nextToken();
						idkmk_cp = st.nextToken();
							//begini karna debugging aja
						
						if(st.hasMoreTokens()) {
							subNakmk=st.nextToken();	
						}
						else {
							subNakmk="null";
						}
						kode_gab_cp = st.nextToken();
						uniqueId=cuid_cp;	;
						/*
						*filter disini bila ada : !kelas canceled
						*/	
						if(kelasSesuaiShift.equalsIgnoreCase("true")) {
							////System.out.println("kelasSesuaiShift1="+kelasSesuaiShift);
							if(Checker.isStringNullOrEmpty(kode_gab_cp)) {
									
								
								if(shift.equalsIgnoreCase(v_shift) && canceled.equalsIgnoreCase("false")) {
									match = true;
							%>
						<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,null,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>"><%=Checker.pnn(Converter.getKonsentrasiKurikulum(idkur),"[]")%><%=Checker.pnn(subNakmk,";")%>  <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
							
							<%
								}
							}
							else {
									//jika kelas gabungan
									/*
									KELAS GABUNGAN PEDULI SHIFT krn tetap kelas harus ditawarkan terlebih dahulu 
									tapi tiak peduli canceled karen bila kelas yg digabungkan
									maka statusnya kabce
									*/
								if(shift.equalsIgnoreCase(v_shift)) {
										
									match = true;
											//match pada token pertama
											////System.out.println("-2- "+kdkmk+" "+cuid_cp+" null");
											%>
						<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,null,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>"><%=Checker.pnn(Converter.getKonsentrasiKurikulum(idkur),"[]")%><%=Checker.pnn(subNakmk,";")%>  <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
											<%	
									while(st.hasMoreTokens()) {
										st.nextToken(); //looping biar no more tokens
									}
										
								}
							}
						}
						else {
								//TIDAK PERLU SESUAI SHIFT
							if(Checker.isStringNullOrEmpty(kode_gab_cp) && canceled.equalsIgnoreCase("false")) {
								match=true;
									
										%>
						<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,null,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>"><%=Checker.pnn(Converter.getKonsentrasiKurikulum(idkur),"[]")%><%=Checker.pnn(subNakmk,";")%>  <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
										<%
							}
							else {
										
								if(canceled.equalsIgnoreCase("false")) {
										/*
										KELAS GABUNGAN
										KARENA VALUE DIATAS ADALAH TOKEN PERTAMA MAKA ITU KELAS INTI
										SEKARANG TINGGAL DICEK APA SHIFT MHS INI TERMASUK YG DIGABUNG, BILA TIDAK INIT CUIDDI TRNLMNYA KOSONG
										,
										*/
										//karena bebas shift jadi value di token pertama / kelas inti 
										//loop abisin token 
										////System.out.println("-3- "+kdkmk+" "+cuid_cp+" null");
										%>
						<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,null,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>"><%=Checker.pnn(Converter.getKonsentrasiKurikulum(idkur),"[]")%><%=Checker.pnn(subNakmk,";")%>  <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
										<%
								}	
							}
						}
					}
					if(!match) {
						%>
						<option value="null" selected>KELAS TIDAK DI TAWARKAN PADA SEMESTER INI</option>
						<%
					}
					%>
					
					</select>
					
					<%
				}
			}
					%>
					</td>
				</tr>
		<%
		}
		if(!li1.hasNext()) {
				
				//System.out.println("1");
			if((adaData && !notThsmsKrs) || whiteList)  {
				//System.out.println("2");
				if(usrReadOnlyNoAksesUpdateShiftOrInsert==null || usrReadOnlyNoAksesUpdateShiftOrInsert.equalsIgnoreCase("false")) { //addhoc - bila read only ngga boleh ada button ini
					//System.out.println("3");
				%>
				<tr>
					<td colspan="6" style="text-align:center;background:#369;padding:5px 5px"><input type="submit" value="UPDATE KRS" style="width:60%;height:35px" /></td>
				</tr>
				<%
				}
			}
		}	
		%>
			</table>	
		<%				
		//}	
			//li2.add(bobot+","+nlakh+","+nakmk+","+kdkmk+","+thsms+","+idkmk+","+noKlsPll+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+max_enrolled+","+min_enrolled+","+avail);
		//}
		
	}	
		
	//System.out.println("usrReadOnlyNoAksesUpdateShiftOrInsert="+usrReadOnlyNoAksesUpdateShiftOrInsert);

	
/*
	if((adaData && !notThsmsKrs) || whiteList)  {		
		if(usrReadOnlyNoAksesUpdateShiftOrInsert==null || !usrReadOnlyNoAksesUpdateShiftOrInsert.equalsIgnoreCase("true")) { //addhoc - bila read only ngga boleh ada button ini
	%>
	<table class="table" style="width:95%">
	<tr>
		<td><input type="submit" value="UPDATE KRS" style="width:98%;height:35px" /></td>
	</tr>
	</table>
	
	<%
		}
	}
	*/
	%>
	</form>
	<%
}
					
}//end sudah mengajukan
	%>
	<!-- Column 1 end -->
	</div>
</div>
</body>
</html>
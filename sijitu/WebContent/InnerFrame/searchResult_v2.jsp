<!DOCTYPE html>
<html>
<head>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />

<style>
.table { 
border: 1px solid #2980B9;
background:<%=Constant.lightColorBlu()%>; 
//table-layout: fixed;
}
.table thead > tr > th { 
border-bottom: none;
background: <%=Constant.darkColorBlu()%>;
color:white;font-weight:bold;
word-wrap:break-word;
}
.table thead > tr > th, .table tbody > tr > t-->h, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td { border: 1px solid #2980B9;word-wrap:break-word; }

.table-noborder { border: none;background:<%=Constant.lightColorBlu()%>; word-wrap:break-word;}
.table-noborder thead > tr > th { border-bottom: none;background:<%=Constant.darkColorBlu()%>;color:white;font-weight:bold; word-wrap:break-word;}
.table-noborder thead > tr > th, .table-noborder tbody > tr > th, .table-noborder tfoot > tr > th, .table-noborder thead > tr > td, .table-noborder tbody > tr > td, .table-noborder tfoot > tr > td { border: none;padding: 2px ;word-wrap:break-word;}
.table tr:hover td { background:#82B0C3;word-wrap:break-word; }

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
	<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai<br>[Lama proses bisa sampai 5 menit]</p>
</div>
<div id="main">
<div class="colmask fullpage">
	<div class="col1">
		<br>
		<br>
		
<%
//UPDAED TAMBAHAN SETELAH VARIABLE MALAIKAT
//Vector v_tmp = new Vector();
//ListIterator li_tmp = v_tmp.listIterator();

Vector vHistBea = (Vector)request.getAttribute("vHistBea");
request.removeAttribute("vHistBea");
session.removeAttribute("forceBackTo");
Vector v= null;  
v = (Vector) request.getAttribute("v_search_result");
Vector vKui = (Vector) request.getAttribute("v_kui_search_result");
if((v==null || v.size()<1) && (vKui==null || vKui.size()<1)) {
	out.print("NO DATA MATCH FOUND");
}
else {
	if(v!=null && v.size()>0) {
		beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
		boolean merge = false;
		if(validUsr.isUsrAccessCommandContain("MERGE")) {
			merge = true;
		}
		int norut=1;
		ListIterator li = v.listIterator();
		ListIterator li2 = vHistBea.listIterator();
		double posisi = 1;
		boolean first = true;
%>
		<center>
		<table class="table" style="width:90%">
			<thead>
				<th style="font-size:1.5em;text-align:center;width:10%">
				NO
				</th>
				<th style="font-size:1.5em;text-align:left;width:50%;padding:0 0 0 10px">
				INFO MAHASISWA
				</th>
				<th style="font-size:1.5em;text-align:center;width:30%">
				PRODI
				</th>
				<th style="font-size:1.5em;text-align:center;width:10%">
				STATUS
				</th>
			</thead>	
			<tbody>
<%
		while(li.hasNext()) {
			String npm = (String)li.next();
			//li_tmp.add(npm);
			String nim = (String)li.next();
			//li_tmp.add(nim);
			String kdpst = (String)li.next();
			//li_tmp.add(kdpst);
			String nmm = (String)li.next();
			//li_tmp.add(nmm);
			String tplhr = (String)li.next();
			//li_tmp.add(tplhr);
			String tglhr = (String)li.next();
			//li_tmp.add(tglhr);
			String stmhs = (String)li.next();
			//li_tmp.add(stmhs);
			String id_obj = (String)li.next();
			//li_tmp.add(id_obj);
			String obj_lvl = (String)li.next();
			//li_tmp.add(obj_lvl);
			String obj_desc = (String)li.next();
			//li_tmp.add(obj_desc);
			
			String malaikat = (String)li.next();
			
			String jenisBea = "null";
			String thsmsBea = "null";
			String baris = (String)li2.next();
			if(!baris.startsWith("null")) {
				StringTokenizer stt = new StringTokenizer(baris,"`");
				//li.add(thsms+"`"+kdpst+"`"+npmhs+"`"+namaPaket+"`"+namaBank+"`"+noRekBank+"`"+nmPemilik+"`"+jenisBea);
				thsmsBea = stt.nextToken();
				stt.nextToken();
				stt.nextToken();
				stt.nextToken();
				stt.nextToken();
				stt.nextToken();
				stt.nextToken();
				jenisBea = stt.nextToken(); 
			}
%>
				
				<form action="get.profile" id="form<%=norut%>">
				<tr onclick="(function(){
           					//scroll(0,0);
							parent.scrollTo(0,0);
							
        					var x = document.getElementById('wait');
             				var y = document.getElementById('main');
             				x.style.display = 'block';
             				y.style.display = 'none';
             				document.getElementById('form<%=norut %>').submit();
             	})();">	
						<input type="hidden" value="<%=npm %>" name="npm"/>
						<input type="hidden" value="dashboard" name="cmd"/>
						<input type="hidden" value="<%=nim %>" name="nim"/>
						<input type="hidden" value="<%=stmhs %>" name="stmhs"/>
						<input type="hidden" value="<%=kdpst %>" name="kdpst" />
						<input type="hidden" value="<%=id_obj %>" name="id_obj" />
						<input type="hidden" value="<%=obj_lvl %>" name="obj_lvl" />
						<input type="hidden" value="<%=malaikat %>" name="malaikat" />
						<input type="hidden" value="<%=nmm %>" name="nmm" />
					<td style=";font-size:1.45em;color:#369;padding:0 0 0 0;text-align:center;vertical-align: middle;">
						<%=norut++ %> 
					</td>
					<td style="color:#369;text-align:left;vertical-align: middle;;font-size:1.45em;font-weight:bold;padding:0 0 0 20px">
						<%=nmm %>
						<section style="color:#369;font-size:0.9em">
						NO NPM / NIM &nbsp&nbsp&nbsp:&nbsp&nbsp&nbsp&nbsp<%=npm %> / <%=nim %>
						<br>
						TPT/TGL LAHIR  :&nbsp&nbsp&nbsp&nbsp<%=tplhr %> / <%=tglhr %>
						</section>
						
						
						<%
        				if(!baris.startsWith("null")) {
        					out.print("<br>"+jenisBea+" ("+thsmsBea+")");
        				}
        				%>
					</td>
					<td style="font-size:1.45em;color:#369;padding:0 0 0 0;text-align:center;vertical-align: middle;">
						<%=obj_desc.replace("MHS", "") %>
					</td>	
					<td style="font-size:1em;color:#369;padding:0 0 0 0;text-align:center;vertical-align: middle;">
        				<b>
        			<%
        			System.out.println(stmhs);
        			switch(stmhs.charAt(0)) {
        				case 'A':
        					out.print("Aktif");
        					break;
        				case 'K':
        					out.print("Keluar");
        					break;
        				case 'L':
        					out.print("Lulus");
        					break;
        				case 'D':
        					out.print("D.O");
        					break;
        				case 'C':
        					out.print("Cuti");
        					break;	
        				default :
        					out.print("Lain2");
        					
        			}
        			%>
        				</b>
        			</td>
        			</form>
        		</tr>
<%
		}
%>
			<tbody>
		</table>
		<center>	
<%
	}
	else {
		if(vKui!=null && vKui.size()>0) {
			ListIterator liKui = vKui.listIterator();
			while(liKui.hasNext()){
				String baris = (String)liKui.next();
				//li.add(kuiid+","+kdpst+","+npmhs+","+tgkui+","+tgtrs+","+keter+","+amont+","+opnmm+","+voidd);
				StringTokenizer st = new StringTokenizer(baris,",");
				String kuiid = st.nextToken();
				String norut = st.nextToken();
				String kdpst = st.nextToken();
				String npmhs = st.nextToken();
				String nmmhs = st.nextToken();
				String tgkui = st.nextToken();
				String tgtrs = st.nextToken();
				String keter = st.nextToken();
				String amont = st.nextToken();
				String opnmm = st.nextToken();
				String voidd = st.nextToken();
				out.print("<br /> ");
				String status = "VALID";
				String backColor = "#d9e1e5";
				if(voidd.equalsIgnoreCase("true")) {
					backColor = "#C20B0B";
					status = "BATAL";
				}
				%>
				<table align="center" bordercolor="#369" style="background:<%=backColor %>;color:#000;width:550px">
					<tr>
						<td style="background:#369;color:#fff;width:30%">NO KUITANSI</td>
						<td><label><%=norut %></label></td>
					</tr>
					<tr>
						<td style="background:#369;color:#fff;width:30%">ATAS NAMA</td>
						<td><label><%=nmmhs %>(NPM :<%=npmhs %>)</label></td>
					</tr>
					<tr>
						<td style="background:#369;color:#fff;width:30%">KETERANGAN</td>
						<td><label><%=keter %></label></td>
					</tr>
					<tr>
						<td style="background:#369;color:#fff;width:30%">JUMLAH</td>
						<td><label><%=amont %></label></td>
					</tr>
					<tr>
						<td style="background:#369;color:#fff;width:30%">PENERIMA</td>
						<td><label><%=opnmm %></label></td>
					</tr>
					<tr>
						<td style="background:#369;color:#fff;width:30%">STATUS</td>
						<td><label><%=status %></label></td>
					</tr>
				</table>
		       
				<%			
			}
		}
	}
}	
%>
	</div>
	</div>
</div>
</body>
</html>
<!DOCTYPE html>
<html>
<head>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />

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
<div class="colmask fullpage">
	<div class="col1">
		<br />
		
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
	
		ListIterator li = v.listIterator();
		ListIterator li2 = vHistBea.listIterator();
		double posisi = 1;
		boolean first = true;
%>

	<!--  table cellspacing="1" -->
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
		
			
				<form action="InnerFrame/get.profile">
					<input type="hidden" value="<%=npm %>" name="npm"/><input type="hidden" value="dashboard" name="cmd"/><input type="hidden" value="<%=nim %>" name="nim"/><input type="hidden" value="<%=stmhs %>" name="stmhs"/><input type="hidden" value="<%=kdpst %>" name="kdpst" /><input type="hidden" value="<%=id_obj %>" name="id_obj" /><input type="hidden" value="<%=obj_lvl %>" name="obj_lvl" /><input type="hidden" value="<%=malaikat %>" name="malaikat" />
					<table class="table" style="width:90%">
						<thead>
						<tr>
							<td style="background:#369;;width:55%;padding:0 0 0 5px"><input type="text" value="<%=nmm %>" name="nmm" readonly style="color:white;width:100%;background-color: transparent;font-weight:bold;font-size:1.8em;border:none" /></td>
							<td align="right" style=";background:#369;color:white;width:45%;padding:0 5px 0 0;font-size:1.5em"><label><%=obj_desc %></label></td>
							
						</tr>

        				<tr>
        					<td style="background:transparent"><B>NPM/NIM :</B> <%=npm %>/<%=nim %></td>
        					<td align="center">
        						<b>STATUS :<%=stmhs %>
        					<%
        					if(!baris.startsWith("null")) {
        						out.print(jenisBea+" ("+thsmsBea+")");
        					}
        					%>
        						</b>
        					</td>
        				</tr>
        				<tr>
        					<td style="background:transparent"><b>TEMPAT/TGL LAHIR : </b> <%=tplhr %>/<%=tglhr %></td>
        					<%
        					if(malaikat.equalsIgnoreCase("true")) {
        						%>
            				<td align="center" style="background:#369"><input type="submit" name="submit" value="NEXT" formtarget="_self" style="width: 50%;height:30px;color:red"/></td>
            					<%
        					}
        					else {
        					%>
        					<td align="center" style="background:#369;padding: 5px 0"><input type="submit" name="submit" value="NEXT" formtarget="_self" style="width: 50%;height:30px; "/></td>
        					<%
        					}
        					%>
        				</tr>
  <%
  							if(merge) {
  %>      				
        				<tr>
        					<td align="center" style="border: 1px solid #808791;background:#808791;padding: 5px 0 5px 5px"><input type="submit" name="submit" value="GABUNG" formtarget="_self" style="width: 50%;height:30px;"/></td>
        					<td style="background:#808791;border: 1px solid #808791;  "><input type="text" value="GABUNGKAN KE NPM" name="dummy" readonly style="color:white;width:50%;background-color: transparent;font-weight:bold;font-size:1em;border:none" /><input type="text" name="mergeToNpm" style="border:none;width:50%;height:40px" /> </td>
        					
        				</tr>
        				<tr>
        					<td style="border: 1px solid #369;background:#369"><input type="text" value="PINDAH KE ANGKATAN " name="dummy" readonly style=";width:50%;background-color: transparent;font-weight:bold;font-size:1em;border:none;color:white;padding:0 0 0 5px" /><input type="text" name="moveToSmawl" style="border:none;width:50%;height:40px" /> </td>
        					<td align="center" style="border: 1px solid #369;background:#369;padding: 5px 0 5px 5px"><input type="submit" name="submit" value="GANTI ANGKATAN" formtarget="_self" style="width: 50%;height:30px;"/></td>
        				</tr>
        					<%
        						if(malaikat!=null && malaikat.equalsIgnoreCase("true")) {
        					%>
        				<tr>
        					<td align="center" style="border: 1px solid #808791;background:#808791;padding: 5px 0 5px 5px"><input type="submit" name="submit" value="PINDAH PRODI" formtarget="_self" style="width: 50%;height:30px;"/></td>
        					<td style="border: 1px solid #808791;background:#808791;"><input type="text" value="PINDAH KE PRODI" name="dummy" readonly style=";width:50%;background-color: transparent;font-weight:bold;font-size:1em;border:none;color:white" /><input type="text" name="moveToKdpst" style="border:none;width:50%;height:40px" /> </td>
        				</tr>
        				<%
        						}
  							}
        				%>
        				</thead>
        			</table>					
        		</form>
        		<br>
			
<%
		}
%>
	<!--  /table-->
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
</body>
</html>
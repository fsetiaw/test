<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/font-awesome/font-awesome.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/base.css" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/stylish-css-buttons-source/css/style.css" />
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
.table tr:hover td { background:#82B0C3 }
</style>

<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />

<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
//Vector vReqAprKeu= (Vector)session.getAttribute("vReqAprKeu");  
//JSONArray jsoaPymntReq = (JSONArray) session.getAttribute("jsoaPymntReq");
String target_norut = request.getParameter("norut");
String target_grpid = request.getParameter("grpid");
//String noKuiReq = request.getParameter("nokuireq"); //diganti target_norut
boolean match = false;
String targetFile = null;
String targetNpmhs =  null;
Vector v_list_pymnt = (Vector)session.getAttribute("v_list_pymnt");  
JSONArray jsoaAkun = Getter.readJsonArrayFromUrl("/v1/akun");
//System.out.println("jsoaAkun lengt="+jsoaAkun.length());
boolean approvee = false;
if(validUsr.isAllowTo("pymntApprovee")>0) {
	approvee = true;
}
%>

	<script>		
		$(document).ready(function() {
			$("#wait").hide();
			$("#main").show();
		});	
	</script>
	<script>
$(document).ready(function() {
	$('input[type=submit]').click(function(){
		$('#button_action').val($(this).attr('name'));
	});
	
	$("#pymntApproval").submit(function(e) {	
   		e.preventDefault(); //STOP default action
   		$.ajax({
    		url: 'go.pymntApproval',
    		type: 'POST',
    		data: $("#pymntApproval").serialize(),
    	    beforeSend:function(){
    	    	$("#wait").show();
    	    	$("#main").hide();
    	        // this is where we append a loading image
    	    },
    	    success:function(data){
    	        // successful request; do something with the data 
    	    	//window.location.href = "indexSebaranTrlsm_v1.jsp";
    	    },
    	    error:function(){
    	        // failed request; give feedback to user
    	    }
    	})
    	return false;
    });
   	
});	
</script>		   	
</head>
<body>
<div id="header">
	<ul>
		<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK<span>&nbsp</span></a></li>
	</ul>
<%
%>

</div>
<div class="colmask fullpage">
	<div class="col1">
		<div id="wait" style="padding: 100px 0;text-align:center;position:absolute; width:100%; height:100%"><img src="<%=Constants.getRootWeb() %>/ajax/animation/ajax-loader.gif" alt="Wait ... " height="82" width="82">
			<p style="text-align:center;font-weight:bold;font-size:1.5em">Harap Menunggu Sampai Proses selesai</p>
		</div>
		<div id="main">		
		<br />
		<!-- Column 1 start -->
		<%
		String err_msg = (String) session.getAttribute("err_msg");
		session.removeAttribute("err_msg");
		if(!Checker.isStringNullOrEmpty(err_msg)) {
		%>
		<p style="text-align:left;padding:0 0 0 25px;color:red">
			<%=err_msg %>
		</p>
		<%	
		}
		%>
		<br/>
		<%
		//if(jsoaPymntReq!=null && jsoaPymntReq.length()>0) {
		if(v_list_pymnt!=null && v_list_pymnt.size()>0) {	
        	ListIterator li = v_list_pymnt.listIterator();
        	while(li.hasNext()) {
        		String brs = (String)li.next();
        		StringTokenizer st = new StringTokenizer(brs,"~");
        		String kuid = st.nextToken();
        		String kdpst = st.nextToken();
        		String npmhs = st.nextToken();
        		String norut = st.nextToken();
        		String tgkui = st.nextToken();
        		String tgtrs = st.nextToken();
        		String keter = st.nextToken();
        		String keter_pymnt = st.nextToken();
        		String payee = st.nextToken();
        		String amount = st.nextToken();
        		String pymnt = st.nextToken();
        		String gelom = st.nextToken();
        		String cicil = st.nextToken();
        		String krs = st.nextToken();
        		String noacc = st.nextToken();
        		String opnpm = st.nextToken();
        		String opnmm = st.nextToken();
        		String setor = st.nextToken();
        		String nonpmp = st.nextToken();
        		String voidpymnt = st.nextToken();
        		String nokod = st.nextToken();
        		String updtm = st.nextToken();
        		String voidop = st.nextToken();
        		String voidkt = st.nextToken();
        		String voidnmm = st.nextToken();
        		String filenm = st.nextToken();
        		String updtmm = st.nextToken();
        		String apptmm = st.nextToken();
        		String rejtm = st.nextToken();
        		String rejnot = st.nextToken();
        		String npm_appr = st.nextToken();
        		String grpid = st.nextToken();
        		String nmmhs = st.nextToken();
        		String nimhs = st.nextToken();
        		String idpaket = st.nextToken();
        		String idobj = st.nextToken();
        		String kdkmp = st.nextToken();
        	//for(int i=0;i<jsoaPymntReq.length();i++) {	
    			//JSONObject job = jsoaPymntReq.getJSONObject(i);
    			//System.out.println("("+i+")"+job.toString());
        		if(norut.equalsIgnoreCase(target_norut)) {
        			match = true;
        %>
        <table class="table">
        	<!--  form name="formUpload1" id="formUpload1" method="post" -->
        	<form action="go.pymntApproval" method="post">

        		<input type="hidden" name="target_norut" value="<%=target_norut%>"/>
        		<input type="hidden" name="target_grpid" value="<%=target_grpid%>"/>
        		<tr>
        			<td colspan="4" style="background:#369;color:#fff;text-align:center;font-size:1.5em;padding:3px"><label><B>FORM VALIDASI PEMBAYARAN</B> </label></td>
        		</tr>	
        		<tr>
        			<td style="background:#369;color:#fff;text-align:left;width:15%;padding:3px"><label><B>NPM / NIM</B> </label></td></td>
	        		<td style="text-align:center;width:275px;padding:3px;width:35%"><label><B><%=npmhs %> / <%=nimhs.replace("null", "N/A") %></B> </label></td></td>
        			<td style="background:#369;color:#fff;text-align:left;width:15%;padding:3px"><label><B>NAMA</B> </label></td></td>
        			<td style="text-align:center;width:275px;padding:3px;width:35%"><label><B><%=nmmhs %></B> </label></td></td>
        		</tr>
        		<tr>
        			<td style="background:#369;color:#fff;text-align:left;padding:3px"><label><B>TGL SETOR</B> </label></td></td>
        			<td style="background:white;text-align:center;padding:3px"><label><B>
        			<%
        			//DateFormater.keteranganDate(Tool.jsobGetString(job, "TGTRSPYMNT"));
        			String trsDate = tgtrs;
        			StringTokenizer stt = new StringTokenizer(trsDate,"-");
        			String yy = stt.nextToken();
        			String mm = stt.nextToken();
        			if(mm.length()==2 && mm.startsWith("0")) {
        				mm = mm.substring(1,2);
        			}
        			String dd = stt.nextToken();
        			if(dd.length()==2 && dd.startsWith("0")) {
        				dd = dd.substring(1,2);
        			}
        			trsDate = dd+"/"+mm+"/"+yy;
        			//out.print(trsDate);
        			%><input type="text" name="tgl_trs" style="text-align:center;border:none;width:100%;height:30px" value="<%=trsDate%>"/></B> </label></td></td>
        			<td style="background:#369;color:#fff;text-align:left;padding:3px"><label><B>PENYETOR</B> </label></td></td>
        			<td style="text-align:center;padding:3px"><label><B>
        <%    		
        			if(Checker.isStringNullOrEmpty(payee)) {
        				out.print(nmmhs);
        			}
        			else {
        				out.print(payee);
        			}
        %>			</B> </label></td></td>
        		</tr>
        		<tr>	
        			<td colspan="4">
        				<table width="100%">	
        					<tr>
        						<td style="background:#369;color:#fff;text-align:left;padding:3px;width:60%">
        							<%="SUMBER DANA" %>
        						</td>
        						<td style="background:grey;color:#fff;text-align:center;width:40%;" colspan="2">
        							<select name="sumber_dana" style="width:100%;height:30px;border:none;text-align-last:center;">
<%
					JSONArray jsoaBea = Getter.readJsonArrayFromUrl("/v1/bea");
					for(int j=0;j<jsoaBea.length();j++) {	
						JSONObject job1 = jsoaBea.getJSONObject(j);
						//System.out.println("("+i+")"+job.toString());
						String idbea = job1.getString("IDPAKETBEASISWA");
						String nama_paket = job1.getString("NAMAPAKET");
						if(idbea.equalsIgnoreCase(idpaket)) {
									%>
							<option value="<%=idbea%>" selected="selected"><%=nama_paket%></option>
									<%	
						}
						else {
									%>
							<option value="<%=idbea%>"><%=nama_paket%></option>
									<%	
						}
					}	
%>
									</select>
        						</td>
        					</tr>
        					<tr>
        						<td style="background:#369;color:#fff;text-align:left;padding:3px">
        							<%="KETERANGAN TRANSAKSI" %>
        						</td>
        						<td style="background:#369;color:#fff;text-align:center;padding:3px">
        							<%="BESARAN (Rp.)" %>
        						</td>
        						<td style="background:#369;color:#fff;text-align:center;padding:3px">
        							<%="TARGET AKUN"%>
        						</td>
        					</tr>	
        <%
        			targetNpmhs = npmhs;
					targetFile = filenm;
		//			//System.out.println("kok "+targetNpmhs+","+targetFile);
					int k=1;
	        		String groupId = grpid+"";
    	  //  		String keterAll = job.getString("KETER_PYMNT_DETAIL") ;
    	    		//System.out.println(keterAll+","+groupId);
        			double totalBesaran = Double.parseDouble(amount);
        			//JSONObject jobTmp = null;
        			int j = 0;
        			//Systemtem.out.println(i);
        	%>
        					<tr>
        						<%
        			if(approvee) {
        		%>
        						<td style="text-align:left;padding:3px;background:white">
        							<input type="text" name="keter" value="<%=keter_pymnt %>" style="text-align:left;border:none;width:100%;height:30px"/>
        						</td>
        						<td style="text-align:right;padding:3px;background:white">
        							<input type="text" name="amount" value="<%=NumberFormater.indoNumberFormat(amount+"") %>" style="text-align:right;border:none;width:100%;height:30px"/>
        						</td>
        						
        							
        		<%			
        			}
        			else {
        		%>
        						<td style="text-align:left;padding:3px;background:white">
        							<input type="text" name="keter" value="<%=keter_pymnt %>" style="text-align:left;border:none;width:100%;height:30px" readonly="readonly"/>
        						</td>
        						<td style="text-align:right;padding:3px;background:white">
        							<input type="text" name="amount" value="<%=NumberFormater.indoNumberFormat(amount+"") %>" style="text-align:right;border:none;width:100%;height:30px" readonly="readonly"/>
        						</td>
        		<%
        			}
        		%>
        						
        						
        						<td style="text-align:center;padding:3px">	
        							<select name="target_akun" style="width:100%;height:30px;border:none;text-align-last:center;">
        						<%
        			for(int l=0; l<jsoaAkun.length();l++) {
        	        	JSONObject jobAkun = jsoaAkun.getJSONObject(l);
        	        	if(Tool.jsobGetString(jobAkun,"NAMA_AKUN").equalsIgnoreCase(noacc) || Tool.jsobGetString(jobAkun,"OLD_NICKNAME").equalsIgnoreCase(noacc)) {
        	        			%>
        	        					<option value="<%=pymnt+":"+jobAkun.getString("NAMA_AKUN") %>" selected="selected"><%=jobAkun.getString("NAMA_AKUN") %></option>
        	        			<%		
        	        	}
        	        	else {
        	        			%>
        	        					<option value="<%=pymnt+":"+jobAkun.getString("NAMA_AKUN") %>"><%=jobAkun.getString("NAMA_AKUN") %></option>
        	        			<%			
        	        	}
        	        }
								%>
									</select>
        						</td>
        					</tr>
        	<%		
        			if(groupId!=null && !Checker.isStringNullOrEmpty(groupId) && !groupId.equalsIgnoreCase("0")) {
        			//multiple item transakasi
        	%>
        		
        					
        	<%
        				//keterAll = "<p>"+k+"."+keterAll+"</p>";
        				k++;
        				boolean sameGroupId = true;
        				while(li.hasNext()&&sameGroupId) {
	        			//for(j=i+1;j<jsoaPymntReq.length()&&sameGroupId;j++) {	
	        				//jobTmp = jsoaPymntReq.getJSONObject(j);
	        				//System.out.println(jobTmp.toString());
	        				brs = (String)li.next();
        					st = new StringTokenizer(brs,"~");
        					String kuid1 = st.nextToken();
        					String kdpst1 = st.nextToken();
        					String npmhs1 = st.nextToken();
        					String norut1 = st.nextToken();
        					String tgkui1 = st.nextToken();
        					String tgtrs1 = st.nextToken();
        					String keter1 = st.nextToken();
        					String keter_pymnt1 = st.nextToken();
        					String payee1 = st.nextToken();
        					String amount1 = st.nextToken();
        					String pymnt1 = st.nextToken();
        					String gelom1 = st.nextToken();
        					String cicil1 = st.nextToken();
        					String krs1 = st.nextToken();
        					String noacc1 = st.nextToken();
        					String opnpm1 = st.nextToken();
        					String opnmm1 = st.nextToken();
        					String setor1 = st.nextToken();
        					String nonpmp1 = st.nextToken();
        					String voidpymnt1 = st.nextToken();
        					String nokod1 = st.nextToken();
        					String updtm1 = st.nextToken();
        					String voidop1 = st.nextToken();
        					String voidkt1 = st.nextToken();
        					String voidnmm1 = st.nextToken();
        					String filenm1 = st.nextToken();
        					String updtmm1 = st.nextToken();
        					String apptmm1 = st.nextToken();
        					String rejtm1 = st.nextToken();
        					String rejnot1 = st.nextToken();
        					String npm_appr1 = st.nextToken();
        					String grpid1 = st.nextToken();
        					String nmmhs1 = st.nextToken();
        					String nimhs1 = st.nextToken();
        					String idpaket1 = st.nextToken();
        					String idobj1 = st.nextToken();
        					String kdkmp1 = st.nextToken();
	        				if(grpid1!=null && grpid1.equalsIgnoreCase(groupId)) {
    	    					//keterAll = keterAll+"<p>"+k+"."+jobTmp.getString("KETER_PYMNT_DETAIL")+"</p>";
    	    					k++;
        						totalBesaran = totalBesaran + Double.parseDouble(amount1);
        	%>
        					<tr>
        						<%
        						if(approvee) {
        		%>
        						<td style="text-align:left;padding:3px;background:white">
        							<input type="text" name="keter" value="<%=keter_pymnt1 %>" style="text-align:left;border:none;width:100%;height:30px"/>
        						</td>
        						<td style="text-align:right;padding:3px;background:white">
        							<input type="text" name="amount" value="<%=NumberFormater.indoNumberFormat(amount1+"") %>" style="text-align:right;border:none;width:100%;height:30px"/>
        						</td>	
        							
        		<%			
        						}
        						else {
        		%>
        						<td style="text-align:left;padding:3px;background:white">
        							<input type="text" name="keter" value="<%=keter_pymnt1 %>" style="text-align:left;border:none;width:100%;height:30px" readonly="readonly"/>
        						</td>
        						<td style="text-align:right;padding:3px;background:white">
        							<input type="text" name="amount" value="<%=NumberFormater.indoNumberFormat(amount1+"") %>" style="text-align:right;border:none;width:100%;height:30px" readonly="readonly"/>
        						</td>		
        		<%
        						}
        		%>
        						
        						
        						
        						<td style="text-align:center;padding:3px">	
        							<select name="target_akun" style="width:100%;height:30px;border:none;text-align-last:center;">
        						<%
        						for(int l=0; l<jsoaAkun.length();l++) {
        	        				JSONObject jobAkun = jsoaAkun.getJSONObject(l);
        	        				
        	        				if(Tool.jsobGetString(jobAkun,"NAMA_AKUN").equalsIgnoreCase(noacc1) || Tool.jsobGetString(jobAkun,"OLD_NICKNAME").equalsIgnoreCase(noacc1)) {
        	        			%>
        	        					<option value="<%= pymnt1+":"+jobAkun.getString("NAMA_AKUN") %>" selected="selected"><%=jobAkun.getString("NAMA_AKUN") %></option>
        	        			<%		
        	        				}
        	        				else {
        	        			%>
        	        					<option value="<%= pymnt1+":"+jobAkun.getString("NAMA_AKUN") %>"><%=jobAkun.getString("NAMA_AKUN") %></option>
        	        			<%			
        	        				}
        	        					
        	        			}
								%>
									</select>
        						</td>
        					</tr>
        							
        	<%					
	        				}
	        				else {
	        					sameGroupId = false;
	        				}
        				}	
        			}		
        			%>
        					<tr>
        						<td style="background:#369;color:#fff;text-align:left;padding:3px"><label><B>TOTAL BESARAN</B> </label></td></td>
        						<td style="background:#369;color:#fff;text-align:right;padding:3px"><label><B>Rp. <%=NumberFormater.indoNumberFormat(totalBesaran+"") %></B> </label></td></td>
        						<td style="background:#369;color:#fff;text-align:center;padding:3px" colspan="1"> 
        <%
        			if(match) {
        				if(targetFile!=null && !Checker.isStringNullOrEmpty(targetFile)) {
    						String target = Constants.getRootWeb()+"/InnerFrame/showPic.jsp";
    						String uri = request.getRequestURI();
    						String url = PathFinder.getPath(uri, target);
    						if(targetFile.contains(".jpg")||targetFile.contains(".jpeg")||targetFile.contains(".gif")||targetFile.contains(".tiff")||targetFile.contains(".bmp")||targetFile.contains(".png")) {
    					
    		    %>
								<a style="color:#fff" href="#" onclick="javascript:winOpen('<%=url %>?namafile=<%=Constants.getFolderBuktiBayaran()+"/"+targetNpmhs+"/"+targetFile %>')">Lihat Bukti Bank</a>
    			<%
    						}
    						else {
    			%>		
    							<a style="color:#fff" href="go.downloadFileAsIs?root_dir=null&keter=null&alm=<%=Constants.getFolderBuktiBayaran()+"/"+targetNpmhs%>&namaFile=<%=targetFile%>&hak=null">Download Bukti Bank</a>
    			<%	
    						}
        				}
        			}	
        			

        		%>

        				</table>
        			</td>
        		</tr>	
        <%
        			if(approvee) {
        			//if(validUsr.isAllowTo("pymntApprovee")>0) {
        %>	
        
        	<!--  input type="hidden" name="kuiidReqested_Int_Wajib" value="kuiidReq %>" / -->
        	
        	<input type="hidden" name="target_npmhs" value="<%=npmhs %>" />
        	<input type="hidden" name="target_kdpst" value="<%=kdpst %>" />
        	<input type="hidden" id="button_action" name="button_action" />
        	<tr>
				<td valign="middle" style="background-color:#a6bac4;vertical-align: middle;">
					<!--  input type="submit" name="verdict" value="Tolak" style="font-weight:bold;width:90px;color:red;height:50px;display:inline"/ -->
					<div id="btn" style="text-align:center;background-color:#a6bac4;height:45px;visibility:visible" ><input type="submit" id="somebutton" name="somebutton" value="Tolak" style="width:90%;height:35px;font-weght:bold;color:red;margin:5px"/></div>
				</td>	
        		<td style="background:#369;padding:3px;" colspan="3">
        			<textarea name="alasan" rows="3" style="display:inline;width:95%;margin:10px" placeholder="isi alasan penolakan"></textarea>
				</td>
        	</tr>
        	<tr>
        		<td style="background:#369;text-align:right" colspan="4">		
        				<div id="btn" style="text-align:right;background-color:#a6bac4;height:45px;visibility:visible" >Bukti Bank Sesuai, <input type="submit" id="somebutton" name="somebutton" value="Terima" style="width:95px;height:35px;font-weight:bold;margin:5px"/></div> 
        			<!-- input type="submit" name="verdict" value="Terima" style="font-weight:bold;width:90px;height:50px;display:inline"/ -->
				</td>
        	</tr>
        <%
        			}
        %>	
        </form>
        <%
        		}	
        	}//end for
        %>	
		</table>
		<%
			
		}
		else {
			//empti jason array
		}
		%>	
		<br/>
		<div id="div_msg" style="font-style:italic;color:red;text-align:left;background-color:#d9e1e5;height:100%;visibility:hidden;margin:0px 0 0 132px;" ></div>
		<!-- Column 1 end -->
		</div>
	</div>
</div>

</body>
</html>
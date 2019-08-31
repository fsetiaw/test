<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
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
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
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
<script>		
	$(document).ready(function() {
		$("#wait").hide();
		$("#main").show();
	});	
</script>
<script>
	$(document).ready(function() {
		
		$("#pengajuanPymntPakeFilter").submit(function(e) {	
	   		e.preventDefault(); //STOP default action
	   		$.ajax({
        		url: 'go.pengajuanPymntPakeFilter',
        		type: 'POST',
        		data: $("#pengajuanPymntPakeFilter").serialize(),
        	    beforeSend:function(){
        	    	$("#wait").show();
        	    	$("#main").hide();
        	        // this is where we append a loading image
        	    },
        	    success:function(data){
        	        // successful request; do something with the data 
        	    	//window.location.href = "index_stp.jsp";
        	    	window.location.href = "requestKeuAprovalForm2.jsp?backto=list_awal";
        	    },
        	    error:function(){
        	        // failed request; give feedback to user
        	    }
        	})
        	return false;
        });
    });
</script>
	
<%
//System.out.println("form2");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector vReqAprKeu= (Vector)session.getAttribute("vReqAprKeu");  //depricated
session.removeAttribute("vReqAprKeu");
Vector v_list_pymnt = (Vector)session.getAttribute("v_list_pymnt");  
//JSONArray jsoaPymntReq = (JSONArray) session.getAttribute("jsoaPymntReq");
/*
JSONArray jArray = readJsonArrayFromUrl("/v1/status/usg_db/listMhs");
			for (int i = 0; i < jArray.length(); i++) {
		        JSONObject jObj = jArray.getJSONObject(i);
		    
		        //System.out.println(i + ". " + jObj.toString());
		        //System.out.println("-----------------------------------------");
			}
*/			
%>

</head>
<body>
<div id="header">
	<jsp:include page="InnerMenu_pageVersion.jsp" flush="true" />
<%
%>

</div>
<div class="colmask fullpage">
	<div class="col1">
		<br>
		<!-- Column 1 start -->
		<div style="text-align:left;padding:0 0 0 20px">
			<form id="pengajuanPymntPakeFilter">
			NAMA/ NPM &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp: &nbsp&nbsp<input type="text" name="target_npmhs" style="width:200px" /><br>
			LIMIT PENCARIAN&nbsp: &nbsp&nbsp<input type="text" name="target_limit" style="width:30px" value="100"/> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type="submit" value="Cari" style="width:100px"/>
			</form>			
		</div>	
		
		<%
		
		//for (int i = 0; i < jsoaPymntReq.length(); i++) {
	     //   JSONObject jObj = jsoaPymntReq.getJSONObject(i);
	    
	   //     out.println(i + ". " + jObj.toString());
	    //    out.println("-----------------------------------------<br/>");
		//}
		//if(vReqAprKeu!=null && vReqAprKeu.size()>0) {
		int no=0;	
		if(v_list_pymnt!=null && v_list_pymnt.size()>0) {	
			ListIterator li = v_list_pymnt.listIterator();
			no++;
		
		%>			
		<table class="table">
        	<tr>
        		<td style="background:#369;color:#fff;text-align:center;width:5%;font-size:1.3em"><B>NO</B> </td>
        		<td style="background:#369;color:#fff;text-align:center;width:10%;font-size:1.3em"><B>TGL</B> </td>
        		<td style="background:#369;color:#fff;text-align:center;width:15%;font-size:1.3em"><B>NPM [NIM]</B> </td>
        		<td style="background:#369;color:#fff;text-align:center;width:20%;font-size:1.3em"><B>NAMA</B> </td>
        		<td style="background:#369;color:#fff;text-align:center;width:35%;font-size:1.3em"><B>KETERANGAN ITEM BAYARAN</B> </td>
        		<td style="background:#369;color:#fff;text-align:center;width:15%;font-size:1.3em"><B>BESARAN</B> </td>
        	</tr>
        <%
        	int no_item=0;		
        	//ListIterator li=vReqAprKeu.listIterator();
        	//int no_item = 0;
        //li.add(kuiid+"#"+kdpst+"#"+npmhs+"#"+norut+"#"+tgkui+"#"+tgtrs+"#"+keter+"#"+keterDetail+"#"+penyetor+"#"+besaran+"#"+posBiaya+"#"+gelombangKe+"#"+cicilanKe+"#"+krs+"#"+targetBankAcc+"#"+opnpm+"#"+opnmm+"#"+sdhDstorKeBank+"#"+nonpmNggaTauUtkApa+"#"+batal+"#"+noKodePmnt+"#"+initUpdTm+"#"+npmVoider+"#"+keterVoid+"#"+nmmVoider+"#"+namaBuktiFile+"#"+uploadTm+"#"+aprovalTm+"#"+rejectedTm+"#"+rejectedNote+"#"+npmApprovee);
        	if(li.hasNext()) {
        		no_item++;
        		String brs = (String)li.next();
        		//System.out.println(no_item+". "+brs);
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

        %>	
        	<tr onclick="window.open('<%=Constants.getRootWeb() %>/InnerFrame/Keu/keuApprovalForm_ver2.jsp?norut=<%=norut %>&grpid=<%=grpid %>','_self')">
        		<td style="color:#000;text-align:center;padding:3px"><%=no %><br/>
        		<td style="color:#000;text-align:center;padding:3px"><%=Converter.reformatSqlDateToTglBlnThn((tgtrs))%><br/>	
        		<td style="color:#000;text-align:center;padding:3px"><%=Checker.pnn(npmhs) %><br/>
        		<%
        		//String nim = Tool.jsobGetString(job, "NIMHSMSMHS");
        		if(Checker.isStringNullOrEmpty(nimhs)) {
        			out.print("[N/A]");
        		}
        		else {
        			out.print("["+nimhs+"]");
        		}
        		%></td>
        		<td style="color:#000;text-align:left;padding:3px"><%=Checker.pnn(nmmhs) %></td>
        		<td style="color:#000;text-align:left;padding:3px">
        		<%
        		if(!Checker.isStringNullOrEmpty(keter_pymnt)&&amount.compareTo("0")>0 ) {
        			out.print(no_item+". "+keter_pymnt+" [Rp. "+NumberFormater.indoNumberFormat(amount+"")+"]<br>");
        		}
        		
	        	double totalBesaran = Double.parseDouble(amount);
        		boolean same_group = true;	
        		while(li.hasNext() && same_group) {
        			no_item++;
        			brs = (String)li.next();
        			//System.out.println(no_item+". "+brs);
        			//System.out.println("barisan="+brs);
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
            		if(grpid.equalsIgnoreCase(grpid1) && (!grpid.equalsIgnoreCase("0") && !grpid1.equalsIgnoreCase("0"))) {
            			//masih group transaksi yg sama
            			//1.lanjut ngeprint keter_pymnt & hiutng total besaran
            			//System.out.println("amount1="+amount1);
            			totalBesaran = totalBesaran + Double.parseDouble(amount1);
            			if(!Checker.isStringNullOrEmpty(keter_pymnt1) && amount1.compareTo("0")>0 ) {
        					out.print(no_item+". "+keter_pymnt1+" [Rp. "+NumberFormater.indoNumberFormat(amount1+"")+"]<br>");
        				}
            		}
            		else {
            			//beda
            			//1.tutup td keter paymnet & tampilkan total besaran
            			grpid = new String(grpid1);
            			no_item = 1; //reset item pembayaran
            		%>
            	</td> 
            	<td style="color:#000;text-align:left;padding:3px">Rp. <%=NumberFormater.indoNumberFormat(totalBesaran+"") %></td>
            </tr>
            <tr onclick="window.open('<%=Constants.getRootWeb() %>/InnerFrame/Keu/keuApprovalForm_ver2.jsp?norut=<%=norut1 %>&grpid=<%=grpid1 %>','_self')">
            	<td style="color:#000;text-align:center;padding:3px"><%=++no %><br/>
        		<td style="color:#000;text-align:center;padding:3px"><%=Converter.reformatSqlDateToTglBlnThn(tgtrs1)  %><br/>
        		<td style="color:#000;text-align:center;padding:3px"><%=Checker.pnn(npmhs1) %><br/>
        		<%
        		//String nim = Tool.jsobGetString(job, "NIMHSMSMHS");
        				if(Checker.isStringNullOrEmpty(nimhs1)) {
        					out.print("[N/A]");
        				}
        				else {
        					out.print("["+nimhs1+"]");
        				}
        		%></td>
        		<td style="color:#000;text-align:left;padding:3px"><%=Checker.pnn(nmmhs1) %></td>
        		<td style="color:#000;text-align:left;padding:3px">
        		<%
        				if(!Checker.isStringNullOrEmpty(keter_pymnt1)&&amount1.compareTo("0")>0 ) {
        					out.print(no_item+". "+keter_pymnt1+" [Rp. "+NumberFormater.indoNumberFormat(amount1+"")+"]<br>");
        				}
        		
	        			totalBesaran = Double.parseDouble(amount1);
        				same_group = true;	

            		}
        		
        		
        			if(!li.hasNext()) {
        		%>
        		</td>
        		<td style="color:#000;text-align:left;padding:3px">
        			<%=Checker.pnn("Rp. "+NumberFormater.indoNumberFormat(totalBesaran+"")) %>
        		</td>
        		<%
        			}
        		}
        		 
        	}
        	%>	
			</table>
			<%
       
		}
		%>	
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>
package servlets.process;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.classPoll.SearchDbClassPoll;
import beans.dbase.dosen.SearchDbDsn;
import beans.dbase.keu.UpdateDbKeu;
import beans.dbase.mhs.btstu.SearchDbInfoMhsBtstu;
import beans.dbase.mhs.kurikulum.SearchDbInfoKurikulum;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.NumberFormater;
import beans.tools.PathFinder;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.owasp.esapi.ESAPI;
/**
 * Servlet implementation class PymntApproval
 */
@WebServlet("/PymntApproval_ver2")
public class PymntApproval_ver2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PymntApproval_ver2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.
		response.setContentType("text/html");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");

		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
		//PrintWriter out = response.getWriter();
			Vector v_list_pymnt = (Vector)session.getAttribute("v_list_pymnt");
			
			String somebutton = request.getParameter("somebutton");
			String button_action = request.getParameter("button_action");
			String namaAkun_String_Wajib = request.getParameter("namaAkun_String_Wajib");
			String alasan = request.getParameter("alasan");
			String target_norut = request.getParameter("target_norut");
			String target_grpid = request.getParameter("target_grpid");
			String target_npmhs = null;
			String target_tgltrs = null;
			double total_bayaran = 0;
			String target_kdpst = null;
			String npm_yg_input_transaksi = null;
			//System.out.println("namaAkun_String_Wajib = "+namaAkun_String_Wajib);
			//System.out.println("button_action = "+button_action);
			//System.out.println("somebuttin = "+somebutton);
			String err_msg = null;
			if(somebutton!=null && somebutton.equalsIgnoreCase("tolak")) {
				if(Checker.isStringNullOrEmpty(alasan)) {
					err_msg = new String("(*) Harap Mengisi Alasan Penolakan");
					session.setAttribute("err_msg", err_msg);
					String target = Constants.getRootWeb()+"/InnerFrame/Keu/keuApprovalForm_ver2.jsp";
					String uri = request.getRequestURI();
					String url_ff = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url_ff+"?norut="+target_norut+"&grpid="+target_grpid).forward(request,response);
				}
				else {
					//1. delete transakasi di session vector
					session.removeAttribute("v_list_pymnt");
					ListIterator li = v_list_pymnt.listIterator();
					while(li.hasNext()) {
						//String tmp  = kuid+"~"+kdpst+"~"+npmhs+"~"+norut+"~"+tgkui+"~"+tgtrs+"~"+keter+"~"+keter_pymnt+"~"+payee+"~"+amount+"~"+pymnt+"~"+gelom+"~"+cicil+"~"+krs+"~"+noacc+"~"+opnpm+"~"+opnmm+"~"+setor+"~"+nonpmp+"~"+voidpymnt+"~"+nokod+"~"+updtm+"~"+voidop+"~"+voidkt+"~"+voidnmm+"~"+filenm+"~"+updtmm+"~"+apptmm+"~"+rejtm+"~"+rejnot+"~"+npm_appr+"~"+grpid+"~"+nmmhs+"~"+nimhs+"~"+idpaket+"~"+idobj+"~"+kdkmp;
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
						//String voidop+"~"+voidkt+"~"+voidnmm+"~"+filenm+"~"+updtmm+"~"+apptmm+"~"+rejtm+"~"+rejnot+"~"+npm_appr+"~"+grpid+"~"+nmmhs+"~"+nimhs+"~"+idpaket+"~"+idobj+"~"+kdkmp;
						if(norut.equalsIgnoreCase(target_norut)) {
							li.remove();
							target_npmhs = new String(npmhs);
							target_kdpst = new String(kdpst);
							npm_yg_input_transaksi = new String(opnpm);
							total_bayaran = total_bayaran+Double.parseDouble(amount);
							target_tgltrs = new String(tgtrs);
						}
						
					}
					session.setAttribute("v_list_pymnt",v_list_pymnt);
					//2. hapus di tabel transit
					UpdateDbKeu udb = new UpdateDbKeu(isu.getNpm());
					int updated = udb.deleteFromPymntTransit(Long.parseLong(target_norut));
					//3.send messege
					//send pesan telah ditolak utk mhs, ,
					
					String sender_grp_id = "9"; //group biro keuangan tabel CHITCHAT.STRUKTURAL_GROUP
					String isi_pesan = "Bayaran anda sebesar Rp. "+NumberFormater.indoNumberFormat(total_bayaran+"")+", tgl "+Converter.reformatSqlDateToTglBlnThn(target_tgltrs).replace("/", "-")+" kami tolak dikarenakan "+alasan+", silahkan hubungi kami bila ada pertanyaan.  Terima Kasih";
					isi_pesan = Converter.prepStringForUrlPassing(isi_pesan);
					//@Path("/msg/kirim/group/{sender_nm}/{sender_npm}/{sender_role}/{target_group_id}/{list_monitoree_role}/{isi_pesan}"
					JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/citcat/upd/msg/kirim/single_npm_receiver/"+target_npmhs+"/group/"+isu.getNpm()+"/null/"+sender_grp_id+"/"+isi_pesan);
					
					
					
		    		String target = Constants.getRootWeb()+"/InnerFrame/Keu/requestKeuAprovalForm2.jsp";
					String uri = request.getRequestURI();
					String url_ff = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url_ff+"?norut="+target_norut+"&grpid="+target_grpid).forward(request,response);
		    		
				}
			}
			else if(somebutton!=null && somebutton.equalsIgnoreCase("terima")) {
				UpdateDbKeu udb = new UpdateDbKeu(isu.getNpm());
				session.removeAttribute("v_list_pymnt");
				String tgl_trs = request.getParameter("tgl_trs");
				String sumber_dana = request.getParameter("sumber_dana");
				String[]keter_pymnt = request.getParameterValues("keter");
				String[]amount_pymnt = request.getParameterValues("amount");
				String[]target_akun = request.getParameterValues("target_akun");
				//System.out.println("keter size="+keter_pymnt.length);
				//System.out.println("amount size="+amount_pymnt.length);
				//System.out.println("target_akun size="+target_akun.length);
				//1. filter data yg diinput
				Vector v_tobe_input = new Vector();
				ListIterator lin = v_tobe_input.listIterator();
				ListIterator li = v_list_pymnt.listIterator();
				while(li.hasNext()) {
					//String tmp  = kuid+"~"+kdpst+"~"+npmhs+"~"+norut+"~"+tgkui+"~"+tgtrs+"~"+keter+"~"+keter_pymnt+"~"+payee+"~"+amount+"~"+pymnt+"~"+gelom+"~"+cicil+"~"+krs+"~"+noacc+"~"+opnpm+"~"+opnmm+"~"+setor+"~"+nonpmp+"~"+voidpymnt+"~"+nokod+"~"+updtm+"~"+voidop+"~"+voidkt+"~"+voidnmm+"~"+filenm+"~"+updtmm+"~"+apptmm+"~"+rejtm+"~"+rejnot+"~"+npm_appr+"~"+grpid+"~"+nmmhs+"~"+nimhs+"~"+idpaket+"~"+idobj+"~"+kdkmp;
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"~");
					String kuid = st.nextToken();
					String kdpst = st.nextToken();
					String npmhs = st.nextToken();
					String norut = st.nextToken();
					String tgkui = st.nextToken();
					String tgtrs = st.nextToken();
					String keter = st.nextToken();
					String keter_pymnt_detil = st.nextToken();
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
					//String voidop+"~"+voidkt+"~"+voidnmm+"~"+filenm+"~"+updtmm+"~"+apptmm+"~"+rejtm+"~"+rejnot+"~"+npm_appr+"~"+grpid+"~"+nmmhs+"~"+nimhs+"~"+idpaket+"~"+idobj+"~"+kdkmp;
					if(norut.equalsIgnoreCase(target_norut)) {
						lin.add(brs);
						li.remove();
						target_npmhs = new String(npmhs);
						target_kdpst = new String(kdpst);
						npm_yg_input_transaksi = new String(opnpm);
						total_bayaran = total_bayaran+Double.parseDouble(amount);
						//overide
						total_bayaran=0;
						for(int k=0;k<amount_pymnt.length;k++) {
							total_bayaran = total_bayaran+Double.parseDouble(amount_pymnt[k].replace(".", ""));
						}
						target_tgltrs = new String(tgtrs);
					}
					
				}
				//2. insert ke pymnt tabel
				int upd = udb.terimaBuktiPembayaranVectorStyle(v_tobe_input, tgl_trs, sumber_dana, keter_pymnt, amount_pymnt, target_akun);
				//jika sukses
				if(upd>0) {
				//3. delete dari tabel payment_trsnsit	
					int updated = udb.deleteFromPymntTransit(Long.parseLong(target_norut));
				//4. create session variable updated (- data yg diinput)
					session.setAttribute("v_list_pymnt",v_list_pymnt);
				//5.send pesan telah ditolak utk mhs, ,
					String sender_grp_id = "9"; //group biro keuangan tabel CHITCHAT.STRUKTURAL_GROUP
					String isi_pesan = "Bayaran anda sebesar Rp. "+NumberFormater.indoNumberFormat(total_bayaran+"")+", tgl "+Converter.reformatSqlDateToTglBlnThn(target_tgltrs).replace("/", "-")+" telah kami terima.  Terima Kasih";
					isi_pesan = Converter.prepStringForUrlPassing(isi_pesan);
					//@Path("/msg/kirim/group/{sender_nm}/{sender_npm}/{sender_role}/{target_group_id}/{list_monitoree_role}/{isi_pesan}"
					JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/citcat/upd/msg/kirim/single_npm_receiver/"+target_npmhs+"/group/"+isu.getNpm()+"/null/"+sender_grp_id+"/"+isi_pesan);
					
				}
				
	    		String target = Constants.getRootWeb()+"/InnerFrame/Keu/requestKeuAprovalForm2.jsp";
				String uri = request.getRequestURI();
				String url_ff = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url_ff+"?norut="+target_norut+"&grpid="+target_grpid).forward(request,response);
				
			}
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
			//String uri = request.getRequestURI();
			//String url = PathFinder.getPath(uri, target);
			//request.getRequestDispatcher(url).forward(request,response);
		}
		
		//System.out.println("doget pymntApproval");
		
		
		/*
		
		//Vector vReqAprKeu= (Vector)session.getAttribute("vReqAprKeu");  
		JSONArray jsoaPymntReq = (JSONArray) session.getAttribute("jsoaPymntReq");
		String submitButtonValue = (String)session.getAttribute("submitButtonValue");
		//validatedTransDate
		String validatedTransDate = (String)session.getAttribute("validatedTransDate");
		session.removeAttribute("validatedTransDate");
		//System.out.println("validatedTransDate @ ptmmntApppeo="+validatedTransDate);
		session.setAttribute("submitButtonValue",submitButtonValue);
		String field_name = (String)session.getAttribute("fieldAndValue");
		String []targetAkun = (String[]) session.getAttribute("targetAkun");
		
		String sumberDana = (String) session.getAttribute("sumberDana");
		session.removeAttribute("targetAkun");
		session.removeAttribute("sumberDana");
		String idsumberDana = null;
		String namaSumberDana  = null;
		if(sumberDana!=null && !Checker.isStringNullOrEmpty(sumberDana)) {
			StringTokenizer st = new StringTokenizer(sumberDana,",");
			
			idsumberDana = st.nextToken();
			namaSumberDana  = st.nextToken();
		}
		
		//System.out.println("targetAkun lengt = "+targetAkun.length);
		//System.out.println("submitButtonValue lengt = "+submitButtonValue);
		for(int i=0;targetAkun!=null && i<targetAkun.length;i++) {
			//System.out.println(targetAkun[i]);
		}
		//String noKuiReq_Int_Wajib = 
		//System.out.println("field_name="+field_name);
		//System.out.println("somebutton="+submitButtonValue);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		UpdateDbKeu udb = new UpdateDbKeu(isu.getNpm());
		/*
		 * sebelum update harus dicek dulu status akhirnya, krn bermain dengan session variable
		 * jadi kalo dah ada yg duluan update, ngga perlu update (multiplr approval operator case)
		*/ 
		//if(false) {
		//	//System.out.println("penolakan");
			/*
			String keterDetail_besaran = udb.tolakBuktiPembayaranJsonStyle(field_name, jsoaPymntReq);
			
			if(keterDetail_besaran!=null) {
				StringTokenizer st = new StringTokenizer(keterDetail_besaran,"||");
				if(st.countTokens()>1) {
					String keter = st.nextToken();//ngga kepake
					String besaran = st.nextToken();//ngga kepake
					
					boolean match = false;
					st = new StringTokenizer(field_name,"||");
					//get id target
		    		String element = null;
					String value = null;
					String content = null;
					String alasanPenolakan = null;
					String targetNpmhs = null;
					String targetKdpst = null;
					String tglSetorBank = null;
		    		while(st.hasMoreTokens()) {
		    			element = st.nextToken();
		    			String tmpValue = st.nextToken();
		    			//if(element.contains("kuiidReqested")) {
		    			if(element.contains("noKuiReq")) {
		    				//match = true;
		    				value = ""+tmpValue;
		    			}
		    			if(element.contains("msgContent")) {
		    				content = ""+tmpValue;
		    			}
		    			if(element.contains("targetNpmhs")) {
		    				targetNpmhs = ""+tmpValue;
		    			}
		    			if(element.contains("targetKdpst")) {
		    				targetKdpst = ""+tmpValue;
		    			}
		    			if(element.contains("Alasan-Penolakan")) {
		    				alasanPenolakan = ""+tmpValue;
		    			}
		    			if(element.contains("Tgl-Setor_Date_Wajib")) {
		    				tglSetorBank = ""+tmpValue;
		    			}
		    			
		    		}
		    		//System.out.println("tglSetorBank @ pymntApproval.java = "+tglSetorBank);
		    		//send pesan telah ditolak utk mhs, ,
		    		String isCreatorPetugas = null;
		    		String operNickname = isu.getObjNickNameGivenObjId();
		    		//dibikin umum jadi kalo kepala bak dirubah ke bak aja
		    		String operNicknameHapusKepala = null;
		    		if(operNickname!=null) {
		    			st = new StringTokenizer(operNickname,",");
		    			operNicknameHapusKepala = st.nextToken();
		    			if(operNicknameHapusKepala!=null && !Checker.isStringNullOrEmpty(operNicknameHapusKepala)) {
		    				operNicknameHapusKepala = operNicknameHapusKepala.replace("KEPALA", "");
		    				st = new StringTokenizer(operNicknameHapusKepala);
		    				operNicknameHapusKepala = "";
		    				while(st.hasMoreTokens()) {
		    					operNicknameHapusKepala = operNicknameHapusKepala+st.nextToken();
		    					if(st.hasMoreTokens()) {
		    						operNicknameHapusKepala=operNicknameHapusKepala+" ";
		    					}
		    				}
		    			}
		    		}
		    		//deprecated
		    		//kayaknya salah - ini mah selalu operator
		    		if(operNickname.contains("OPERATOR")) {
		    			isCreatorPetugas = "true";
		    		}
		    		else {
		    			isCreatorPetugas = "false";
		    		}
		    		//proses diatas di ganti ma ini
		    		try {
		    			if(jsoaPymntReq!=null && jsoaPymntReq.length()>0) {
			    			JSONObject job = jsoaPymntReq.getJSONObject(0);
			    			String NpmYgInputTransaksi = job.getString("OPNPMPYMNT");
			    			String targetKdpstTransaksi = job.getString("KDPSTPYMNT");
			    			JSONArray joa = Getter.readJsonArrayFromUrl("/v1/search_obj_type/npm/"+NpmYgInputTransaksi+"/nickname");
			    			if(joa!=null && joa.length()>0) {
			    				JSONObject jobTmp = joa.getJSONObject(0);
			    				operNickname = jobTmp.getString("OBJ_NICKNAME");
			    				//System.out.println("operNickname="+operNickname);
			    				if(operNickname.contains("OPERATOR")) {
					    			isCreatorPetugas = "true";
					    		}
					    		else {
					    			isCreatorPetugas = "false";
					    		}
			    				//System.out.println("isCreatorPetugas="+isCreatorPetugas);
			    			}
			    		}	
		    		}
		    		catch(JSONException je) {
		    			je.printStackTrace();
		    		}
		    		
		    		
		    		String targetObjNickName = udb.getObjNicknameGivenNpmhs(targetKdpst, targetNpmhs);
		    		/*
		    		 * post berita - pada pengirim, karena bisa jadi yg input adalah operator
		    		 
		    		if(Boolean.parseBoolean(isCreatorPetugas)) {
		    			//System.out.println("yes petugas");
		    			String approvee = Constants.getDefaultPymntReqApprovee(Checker.getKdjen(targetKdpst));
		    			operNicknameHapusKepala = ""+operNicknameHapusKepala;
		    			targetObjNickName = Constants.getDefaultTargetNicknameBilaPembayaranDiinputOlehOperator(Converter.getKdjen(targetKdpst));
		    			udb.postMainTopicTargetedIndividualNpmhs(Constants.getKodePesanPengumuman()+"Proses verifikasi "+content+" telah selesai dan PEMBAYARAN TELAH DITOLAK, Terima Kasih", isu.getObjLevel(), isu.getIdObj(), operNicknameHapusKepala, targetObjNickName, null, "true", isCreatorPetugas);
		    		}
		    		else {
		    			udb.postMainTopicTargetedIndividualNpmhs(Constants.getKodePesanPengumuman()+"Proses verifikasi "+content+" telah selesai dan PEMBAYARAN TELAH DITOLAK, dikarenakan "+alasanPenolakan+", Terima Kasih", isu.getObjLevel(), isu.getIdObj(), operNicknameHapusKepala, targetObjNickName, targetNpmhs, "true", isCreatorPetugas);
		    		}
		    		match = false;
		    		//ganti ke json stye

		    		JSONArray jsoa = new JSONArray();
		    		if(jsoaPymntReq!=null && jsoaPymntReq.length()>0) {
		    			for(int i=0;i<jsoaPymntReq.length();i++) {
		    				try {
		    					JSONObject joba = jsoaPymntReq.getJSONObject(i);
		    					st = new StringTokenizer(field_name,"||");
								String id = "";
								while(st.hasMoreTokens()) {
									String elemenNama = st.nextToken();
									//if(elemenNama.contains("kuiidReqested")) {
									if(elemenNama.contains("noKuiReq")) {	
										id = st.nextToken();
									}
								}	
								//if(id.equalsIgnoreCase(value)) {
								if(!joba.getString("NORUTPYMNT").equalsIgnoreCase(id)) {
									//li.remove();
									jsoa.put(joba);
									//match = true;
								}
		    				
		    				}
		    				catch(JSONException e) {
		    					e.printStackTrace();
		    				}
		    				
		    			}
		    		}
					//if(vReqAprKeu!=null && vReqAprKeu.size()>0) {
					//	target = Constants.getRootWeb()+"/InnerFrame/Keu/requestKeuAprovalForm.jsp";
					//	session.removeAttribute("vReqAprKeu");
					//	session.setAttribute("vReqAprKeu", vReqAprKeu);
					//}
					if(jsoa!=null && jsoa.length()>0) {
						target = Constants.getRootWeb()+"/InnerFrame/Keu/requestKeuAprovalForm.jsp";
						session.removeAttribute("jsoaPymntReq");
						session.setAttribute("jsoaPymntReq", jsoa);
					}
					else {
						target = Constants.getRootWeb()+"/InnerFrame/home.jsp";
						session.removeAttribute("jsoaPymntReq");
						jsoaPymntReq = new JSONArray();
						session.setAttribute("jsoaPymntReq", jsoaPymntReq);
						//session.removeAttribute("vReqAprKeu");
						//vReqAprKeu = new Vector();
						//session.setAttribute("vReqAprKeu", vReqAprKeu);

					}	
				}
			}
			*/
		//}
		//else {
			//kalo terima dibikin mark as read aja deh kalo operator
			//if(submitButtonValue.equalsIgnoreCase("terima")) {
			//if(false) {
				/*
				//int i = udb.terimaBuktiPembayaran(field_name, vReqAprKeu);
				int j = 0;
				//perlaihan dalam rangka penambahan sumberdana
				//jadi kalo sumber dana null berearati pake fungsi yg lama
				if(idsumberDana!=null && !Checker.isStringNullOrEmpty(idsumberDana)) {
					j = udb.terimaBuktiPembayaranJsonStyle(field_name, jsoaPymntReq, targetAkun, validatedTransDate, idsumberDana);
				}
				else {
					j = udb.terimaBuktiPembayaranJsonStyle(field_name, jsoaPymntReq, targetAkun, validatedTransDate);
				}
				//jika update sukses maka remove element dgn reqidTerkait  dari vReqAprKeu
				
				if(j>0) {
					
					boolean match = false;
					StringTokenizer st = new StringTokenizer(field_name,"||");
					//get id target
		    		String element = null;
					String value = null;
					String content = null;
					String targetNpmhs = null;
					String targetKdpst = null;
		    		while(st.hasMoreTokens()) {
		    			element = st.nextToken();
		    			String tmpValue = st.nextToken();
		    			if(element.contains("noKuiReq")) {
		    				//match = true;
		    				value = ""+tmpValue;
		    			}
		    			if(element.contains("msgContent")) {
		    				content = ""+tmpValue;
		    			}
		    			if(element.contains("targetNpmhs")) {
		    				targetNpmhs = ""+tmpValue;
		    			}
		    			if(element.contains("targetKdpst")) {
		    				targetKdpst = ""+tmpValue;
		    			}
		    		}
		    		
		    		//send pesan telah diterima utk mhs, ,
		    		String isCreatorPetugas = null;
		    		String operNickname = isu.getObjNickNameGivenObjId();
		    		//dibikin umum jadi kalo kepala bak dirubah ke bak aja
		    		/*
		    		 * depricated dibawah akan di overidoe dengan sistim default approvee
		    		 
		    		String operNicknameHapusKepala = null;
		    		if(operNickname!=null) {
		    			st = new StringTokenizer(operNickname,",");
		    			operNicknameHapusKepala = st.nextToken();
		    			if(operNicknameHapusKepala!=null && !Checker.isStringNullOrEmpty(operNicknameHapusKepala)) {
		    				operNicknameHapusKepala = operNicknameHapusKepala.replace("KEPALA", "");
		    				st = new StringTokenizer(operNicknameHapusKepala);
		    				operNicknameHapusKepala = "";
		    				while(st.hasMoreTokens()) {
		    					operNicknameHapusKepala = operNicknameHapusKepala+st.nextToken();
		    					if(st.hasMoreTokens()) {
		    						operNicknameHapusKepala=operNicknameHapusKepala+" ";
		    					}
		    				}
		    			}
		    		}
		    		
		    		//depricated
		    		if(operNickname.contains("OPERATOR")) {
		    			isCreatorPetugas = "true";
		    		}
		    		else {
		    			isCreatorPetugas = "false";
		    		}
		    		//proses diatas di ganti ma ini
		    		try {
		    			if(jsoaPymntReq!=null && jsoaPymntReq.length()>0) {
			    			JSONObject job = jsoaPymntReq.getJSONObject(0);
			    			String NpmYgInputTransaksi = job.getString("OPNPMPYMNT");
			    			String targetKdpstTransaksi = job.getString("KDPSTPYMNT");
			    			JSONArray joa = Getter.readJsonArrayFromUrl("/v1/search_obj_type/npm/"+NpmYgInputTransaksi+"/nickname");
			    			if(joa!=null && joa.length()>0) {
			    				JSONObject jobTmp = joa.getJSONObject(0);
			    				operNickname = jobTmp.getString("OBJ_NICKNAME");
			    				//System.out.println("operNickname="+operNickname);
			    				if(operNickname.contains("OPERATOR")) {
					    			isCreatorPetugas = "true";
					    		}
					    		else {
					    			isCreatorPetugas = "false";
					    		}
			    				//System.out.println("isCreatorPetugas="+isCreatorPetugas);
			    			}
			    		}	
		    		}
		    		catch(JSONException je) {
		    			je.printStackTrace();
		    		}
		    		String targetObjNickName = udb.getObjNicknameGivenNpmhs(targetKdpst, targetNpmhs);
		    		/*
		    		 * post berita - pada pengirim, karena bisa jadi yg input adalah operator
		    		 *
		    		if(Boolean.parseBoolean(isCreatorPetugas)) {
		    			//System.out.println("yes petugas");
		    			String approvee = Constants.getDefaultPymntReqApprovee(Checker.getKdjen(targetKdpst));
		    			operNicknameHapusKepala = ""+operNicknameHapusKepala;
		    			targetObjNickName = Constants.getDefaultTargetNicknameBilaPembayaranDiinputOlehOperator(Converter.getKdjen(targetKdpst));
		    			if(targetObjNickName.contains("OPERATOR")) {
		    				//make marked as read at target
		    				udb.postMainTopicTargetedIndividualNpmhsMarkedAsRead(Constants.getKodePesanPengumuman()+"Proses verifikasi "+content+" telah selesai dan PEMBAYARAN TELAH DITERIMA, Terima Kasih", isu.getObjLevel(), isu.getIdObj(), operNicknameHapusKepala, targetObjNickName, null, "true", isCreatorPetugas);
		    			}
		    			else {
		    				udb.postMainTopicTargetedIndividualNpmhs(Constants.getKodePesanPengumuman()+"Proses verifikasi "+content+" telah selesai dan PEMBAYARAN TELAH DITERIMA, Terima Kasih", isu.getObjLevel(), isu.getIdObj(), operNicknameHapusKepala, targetObjNickName, null, "true", isCreatorPetugas);
		    		
		    			}
		    		}	
		    		else {
		    			udb.postMainTopicTargetedIndividualNpmhs(Constants.getKodePesanPengumuman()+"Proses verifikasi "+content+" telah selesai dan PEMBAYARAN TELAH DITERIMA, Terima Kasih", isu.getObjLevel(), isu.getIdObj(), operNicknameHapusKepala, targetObjNickName, targetNpmhs, "true", isCreatorPetugas);
		    		}
		    		match = false;
		    		// ganti ke JSON STYLE

		    		JSONArray jsoa = new JSONArray();
		    		if(jsoaPymntReq!=null && jsoaPymntReq.length()>0) {
		    			for(int i=0;i<jsoaPymntReq.length();i++) {
		    				try {
		    					JSONObject joba = jsoaPymntReq.getJSONObject(i);
		    					st = new StringTokenizer(field_name,"||");
								String id = "";
								while(st.hasMoreTokens()) {
									String elemenNama = st.nextToken();
									//if(elemenNama.contains("kuiidReqested")) {
									if(elemenNama.contains("noKuiReq")) {	
										id = st.nextToken();
									}
								}	
								//if(id.equalsIgnoreCase(value)) {
								if(!joba.getString("NORUTPYMNT").equalsIgnoreCase(id)) {
									//li.remove();
									jsoa.put(joba);
									//match = true;
								}
		    				
		    				}
		    				catch(JSONException e) {
		    					e.printStackTrace();
		    				}
		    				
		    			}
		    		}
		    		if(jsoa!=null && jsoa.length()>0) {
						target = Constants.getRootWeb()+"/InnerFrame/Keu/requestKeuAprovalForm.jsp";
						session.removeAttribute("jsoaPymntReq");
						session.setAttribute("jsoaPymntReq", jsoa);
					}
					else {
						target = Constants.getRootWeb()+"/InnerFrame/home.jsp";
						session.removeAttribute("jsoaPymntReq");
						jsoaPymntReq = new JSONArray();
						session.setAttribute("jsoaPymntReq", jsoaPymntReq);
					}	
				}
			*/
			//}
		//}
		//bis di proses, redirect:
		//if(target==null || Checker.isStringNullOrEmpty(target)) {
	//		target = Constants.getRootWeb()+"/get.notifications";
		//}
		//target = Constants.getRootWeb()+"/InnerFrame/Keu/requestKeuAprovalForm.jsp";
		//String uri = request.getRequestURI();
		//String url_ff = PathFinder.getPath(uri, target);
		//request.getRequestDispatcher(url_ff).forward(request,response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}

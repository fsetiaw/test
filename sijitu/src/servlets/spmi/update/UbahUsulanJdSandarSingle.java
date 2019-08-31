package servlets.spmi.update;

import java.io.IOException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.naming.directory.SearchResult;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.classPoll.SearchDbClassPoll;
import beans.dbase.dosen.SearchDbDsn;
import beans.dbase.mhs.btstu.SearchDbInfoMhsBtstu;
import beans.dbase.mhs.kurikulum.SearchDbInfoKurikulum;
import beans.dbase.spmi.SearchSpmi;
import beans.dbase.spmi.SearchStandarMutu;
import beans.dbase.spmi.request.UpdateRequest;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UbahUsulanJdSandarSingle
 */
@WebServlet("/UbahUsulanJdSandarSingle")
public class UbahUsulanJdSandarSingle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UbahUsulanJdSandarSingle() {
        super();
        //TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO Auto-generated method stub
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); //HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0.
		response.setHeader("Expires", "0"); //Proxies.
		response.setContentType("text/html");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");

		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			
			
			
			//System.out.println("masuk ubah usulan");
			if(true) {
		//PrintWriter out = response.getWriter();
				//System.out.println("submit="+tombol);
				Vector v_err = null;
				ListIterator<String> li=null;
				//String atMenu = request.getParameter("atMenu");
				
				
				String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");

				String id_std_isi = request.getParameter("id_std_isi");
				String id_master_std = request.getParameter("id_master_std");
				String id_tipe_std = request.getParameter("id_tipe_std");
				//System.out.println("id_master_std2="+id_master_std);
				//System.out.println("id_tipe_std2="+id_tipe_std);
				String rasionale = ""+request.getParameter("rasionale");
				String isi_std = ""+request.getParameter("isi_std");

				




				
				
				StringTokenizer st = null;
				if(kdpst_nmpst_kmp!=null && kdpst_nmpst_kmp.contains("-")) {
					st = new StringTokenizer(kdpst_nmpst_kmp,"-");
				}
				else {
					st = new StringTokenizer(kdpst_nmpst_kmp,"`");
				}
				String kdpst_menu = st.nextToken();
				String nmpst_menu = st.nextToken();
				String kdkmp_menu = st.nextToken();
				
				
				//System.out.println("id_master="+id_master);
				if(Checker.isStringNullOrEmpty(id_master_std)||id_master_std.equalsIgnoreCase("0")) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Rumpun Standar harus diisi");
				}
				
				
				//System.out.println("id_tipe="+id_tipe);
				if(Checker.isStringNullOrEmpty(id_tipe_std)||id_tipe_std.equalsIgnoreCase("0")) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Tipe Standar harus diisi");
				}
				
				/*
				if(Checker.isStringNullOrEmpty(rasionale)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Rasionale/Alasan harus diisi");
				}
				*/
				if(Checker.isStringNullOrEmpty(isi_std)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Isi Standar harus diisi");
				}
				/*
				if(Checker.isStringNullOrEmpty(cakupan_std)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Cakupan Standar harus diisi");
					cakupan_std = "null";
				}
				*/
				
				/*
				if(Checker.isStringNullOrEmpty(lama_per_period)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Lama/periode harus diisi");
				}
				
				if(Checker.isStringNullOrEmpty(target1)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Target Periode I harus diisi");
				}
				
				if(Checker.isStringNullOrEmpty(target2)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Target Periode II harus diisi");
				}
				
				if(Checker.isStringNullOrEmpty(target3)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Target Periode III harus diisi");
				}
				
				if(Checker.isStringNullOrEmpty(target4)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Target Periode IV harus diisi");
				}
				
				if(Checker.isStringNullOrEmpty(target5)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Target Periode V harus diisi");
				}
				
				if(Checker.isStringNullOrEmpty(target6)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Target Periode VI harus diisi");
				}
				
				if(Checker.isStringNullOrEmpty(periode_awal)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Periode aswal dimulai harus diisi");
				}
				
				if(Checker.isStringNullOrEmpty(tkn_variable)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Parameter harus diisi");
				}
				
				
				if(Checker.isStringNullOrEmpty(tkn_indikator)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Indikator variable harus diisi");
					
				}
				
				if(Checker.isStringNullOrEmpty(strategi)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Strategi pelaksanaan standar harus diisi");
					
				}
				else {
					if(strategi.length()<25) {
						if(v_err==null) {
							v_err = new Vector();
							li = v_err.listIterator();
						}
						li.add("Strategi pelaksanaan minimal terdiri dari 25 char");
					}
				}

				String tipe_proses_pengawasan = request.getParameter("tipe_proses_pengawasan");
				if(Checker.isStringNullOrEmpty(tipe_proses_pengawasan)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Harap pilih tipe proses pengawasan");
				}

				String tkn_doc=null;
				
				if(doc!=null && doc.length>0) {
					for(int j=0;j<doc.length;j++) {
						if(tkn_doc==null) {
							tkn_doc = new String(doc[j]);
						}
						else {
							tkn_doc = tkn_doc+","+doc[j];
						}
					}
				}

				if(Checker.isStringNullOrEmpty(tkn_doc)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Dokumen terkait harus diisi");
				}
				*/
				/*
				String[]job = request.getParameterValues("job");
				if(job==null || job.length<1) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Pihak terkait harus diisi");
				}
				String[]job_mon = request.getParameterValues("job_mon");
				if(job_mon==null || job_mon.length<1) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Pihak pengawas harus diisi");
				}
				*/

				//if(Checker.isStringNullOrEmpty(versi)) {
				//	versi = "1"; //default
				//}
				
				

				/*
				if(job!=null && job.length>0) {
					for(int i=0;i<job.length;i++) {
						if(!Checker.isStringNullOrEmpty(job[i])) {
							if(pihak.equalsIgnoreCase("null")) {
								pihak = new String(job[i]);
							}
							else {
								pihak = pihak+","+job[i];
							}
						}
					}
				}
				
				if(job_mon!=null && job_mon.length>0) {
					for(int i=0;i<job_mon.length;i++) {
						if(!Checker.isStringNullOrEmpty(job_mon[i])) {
							if(pihak_mon.equalsIgnoreCase("null")) {
								pihak_mon = new String(job_mon[i]);
							}
							else {
								pihak_mon = pihak_mon+","+job_mon[i];
							}
						}
					}
				}
				*/
				//int id_std=0;
				//if((!Checker.isStringNullOrEmpty(id_master) && !id_master.equalsIgnoreCase("0")) && (!Checker.isStringNullOrEmpty(id_tipe) && !id_tipe.equalsIgnoreCase("0"))) {
					//System.out.println("masuk sisni");
					//SearchSpmi sr = new SearchSpmi();
					//id_std=sr.getIdStd(Integer.parseInt(id_master),Integer.parseInt(id_tipe));
				//}
				//String norut = "0";//pertama kali
				//String tmp = id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+versi+"`"+id_declare+"`"+id_do+"`"+id_eval+"`"+id_control+"`"+id_upgrade+"`"+tglsta+"`"+tglend+"`"+thsms1+"`"+thsms2+"`"+thsms3+"`"+thsms4+"`"+thsms5+"`"+thsms6+"`"+pihak+"`"+tkn_doc+"`"+tkn_indikator+"`"+norut+"`"+periode_awal+"`"+periode_unit_used+"`"+lama_per_period+"`"+unit1+"`"+unit2+"`"+unit3+"`"+unit4+"`"+unit5+"`"+unit6+"`"+tkn_variable+"`"+cakupan_std+"`"+tipe_proses_pengawasan;
				//String tmp = id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+versi+"`"+id_declare+"`"+id_do+"`"+id_eval+"`"+id_control+"`"+id_upgrade+"`"+tglsta+"`"+tglend+"`"+thsms1+"`"+thsms2+"`"+thsms3+"`"+thsms4+"`"+thsms5+"`"+thsms6+"`"+pihak+"`"+tkn_doc+"`"+tkn_indikator+"`"+norut+"`"+periode_awal+"`"+periode_unit_used+"`"+lama_per_period+"`"+unit1+"`"+unit2+"`"+unit3+"`"+unit4+"`"+unit5+"`"+unit6+"`"+tkn_variable+"`"+cakupan_std+"`null";
	    		//tmp = tmp.replace("``", "`null`");
	    		//tmp = tmp.replace("``", "`null`");
	    		//tmp = tmp.replace("``", "`null`");
	    		//tmp = tmp.replace("``", "`null`");
	    		//tmp = tmp.replace("``", "`null`");
	    		//tmp = tmp.replace("``", "`null`");
	    		//Vector v_target = new Vector();
	    		//ListIterator lit = v_target.listIterator();
	    		//lit.add(tmp);
	    		if(v_err!=null && v_err.size()>0) {
	    			//System.out.println("ada error");
	    			//session.setAttribute("v_target", v_target);
	    			session.setAttribute("v_err", v_err);
	    			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_creator/form/std_isi/edit_usulan_std.jsp";
	    			String uri = request.getRequestURI();
	    			String url = PathFinder.getPath(uri, target);
	    			//System.out.println("?mode=edit&id_master="+id_master+"&id_tipe="+id_tipe+"&atMenu="+atMenu+"&tkn_pengawas="+pihak_mon);;
	    			//request.getRequestDispatcher(url+"?mode=edit&id_master="+id_master+"&id_tipe="+id_tipe+"&atMenu="+atMenu+"&tkn_pengawas=null").forward(request,response);
	    			request.getRequestDispatcher(url).forward(request,response);
	    		}
	    		else {
	    			
	    			//System.out.println("komplit "+id_std);
	    			SearchStandarMutu ssm = new SearchStandarMutu();
	    			String id_std_versi_terkini="1";
	    			Vector v_tmp = ssm.getLatestInfoTampleteStandar(Integer.parseInt(id_master_std), Integer.parseInt(id_tipe_std));
	    			if(v_tmp!=null && v_tmp.size()>0) {
	    				li = v_tmp.listIterator();
	    				String brs = (String)li.next();
	    				st = new StringTokenizer(brs,"~");
	    				id_std_versi_terkini = st.nextToken();
	    			}
	    			UpdateRequest ur = new UpdateRequest();
	    			int id_std = ssm.getIdStd(id_master_std, id_tipe_std);
	    			int updated = ur.ubahUsulanJadiStandarSingle(id_std, id_std_isi, isi_std,id_std_versi_terkini);
	    			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_creator/form/std_isi/edit_index_v2.jsp";
					String uri = request.getRequestURI();
					String url = PathFinder.getPath_v2(uri, target);
	    			
	    			request.getRequestDispatcher(url+"?&mode=list&atMenu=edit_isi&kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);
	    			
	    			//System.out.println("backTo -- "+backTo);
	    			/*
	    			if(Checker.isStringNullOrEmpty(backTo)) {
	    				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_creator/form/std_isi/edit_index_single.jsp";
	        			String uri = request.getRequestURI();
	        			String url = PathFinder.getPath(uri, target);
	        			request.getRequestDispatcher(url+"?atMenu=edit_isi&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&mode=list").forward(request,response);	
	    			}
	    			else {
	    				if(!Checker.isStringNullOrEmpty(backTo)&&(backTo.contains("index_std_belum_aktif.jsp")||backTo.contains("index_std_sudah_aktif.jsp"))) {
	    					//String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
	    					String mode = request.getParameter("mode");
	    					String at_page = request.getParameter("at_page");
	    					//String id_tipe_std = request.getParameter("id_tipe_std");
	    					//String id_master_std = request.getParameter("id_master_std");
	    					//System.out.println("1.id_tipe_std="+id_tipe);
	    					//System.out.println("2.id_master_std="+id_master);
	    					String max_data_per_pg = request.getParameter("max_data_per_pg");
	    					kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
	    					st = new StringTokenizer(kdpst_nmpst_kmp,"-");
	    					String target_kdpst = st.nextToken();
	    					String target_nmpst = st.nextToken();
	    					String target_kdkmp = st.nextToken();//ngga kepake b
	    					SearchStandarMutu stm = new SearchStandarMutu();
	    					boolean editor = !isu.isHakAksesReadOnly("hasSpmiMenu");
	    					//System.out.println("saya editor="+editor);
	    					Vector v_list = null;
	    					
	    					v_list = stm.getListStandarIsi(editor, target_kdpst, isu.getIdObj(), Integer.parseInt(id_master), Integer.parseInt(id_tipe));

	    					session.setAttribute("v", v_list);
	    					String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/home_standar.jsp";
	    					String uri = request.getRequestURI();
	    					String url = PathFinder.getPath_v2(uri, target);
	    					//System.out.println("url2="+url);
	    					if(backTo.contains("index_std_belum_aktif.jsp")) {
	    						request.getRequestDispatcher(url+"?backTo="+Constants.getRootWeb()+"/InnerFrame/Spmi/spmi_overview/index_std_belum_aktif.jsp&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&id="+id_master+"&id_tipe_std="+id_tipe+"&at_page="+at_page+"&max_data_per_pg="+max_data_per_pg+"&mode="+mode).forward(request,response);	
	    					}
	    					else if(backTo.contains("index_std_sudah_aktif.jsp")) {
	    						request.getRequestDispatcher(url+"?backTo="+Constants.getRootWeb()+"/InnerFrame/Spmi/spmi_overview/index_std_sudah_aktif.jsp&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&id="+id_master+"&id_tipe_std="+id_tipe+"&at_page="+at_page+"&max_data_per_pg="+max_data_per_pg+"&mode="+mode).forward(request,response);
	    					}
	    						
	    				}
	    				else {
	    					request.getRequestDispatcher("go.getListAllStd?backTo="+backTo+"&atMenu=edit_isi&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&mode=list").forward(request,response);
	    					
	    				}
	    				
	    			}
	    			*/
	    		}	
			}	
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO Auto-generated method stub
		doGet(request, response);
	}

}

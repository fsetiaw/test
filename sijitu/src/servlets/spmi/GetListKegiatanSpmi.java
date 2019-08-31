package servlets.spmi;

import java.io.IOException;
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
import beans.dbase.mhs.btstu.SearchDbInfoMhsBtstu;
import beans.dbase.mhs.kurikulum.SearchDbInfoKurikulum;
import beans.dbase.spmi.SearchStandarMutu;
import beans.dbase.spmi.manual.SearchManual;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;
/**
 * Servlet implementation class GetListKegiatanSpmi
 */
@WebServlet("/GetListKegiatanSpmi")
public class GetListKegiatanSpmi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetListKegiatanSpmi() {
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
			//System.out.println("sabar");
			Boolean spmi_editor = (Boolean) session.getAttribute("spmi_editor");
			//System.out.println("spmi_editor="+spmi_editor);
			StringTokenizer st = null;
			String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
			String fwdto = request.getParameter("fwdto");
			kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
			if(kdpst_nmpst_kmp.contains("~")) {
				st = new StringTokenizer(kdpst_nmpst_kmp,"~");
			}
			else {
				st = new StringTokenizer(kdpst_nmpst_kmp,"-");
			}
			//StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
			String kdpst = st.nextToken();
			String nmpst = st.nextToken();
			String kdkmp = st.nextToken();
			//1. cek survey yg blum dievaluasi filter dgn jabatan user
			SearchManual sm = new SearchManual();
    		Vector v_list_survey_no_evaluasi = sm.getSurveyYgBlumDiEvaluasi(kdpst);
    		if(v_list_survey_no_evaluasi!=null && v_list_survey_no_evaluasi.size()>0) {
    			ListIterator li = v_list_survey_no_evaluasi.listIterator();
    			while(li.hasNext() ) {
    				String brs = (String)li.next();
    				//tgl_sidak+"~"+kdpst+"~"+controller+"~"+surveyor+"~"+param+"~"+id_hist+"~"+id_kendali+"~"+id_versi+"~"+id_std_isi+"~"+id_std+"~"+id_master_std+"~"+id_tipe_std+"~"+ket_tipe_std;
    				st = new StringTokenizer(brs,"~");
    				String tgl_sidak = st.nextToken();
    				String target_kdpst = st.nextToken();
    				String controller = st.nextToken();
    				String surveyor = st.nextToken();
    				String param = st.nextToken();
    				if(!isu.amI_v1(controller, target_kdpst)) {
    					li.remove();
    				}
    				//else {
    				//	//System.out.println(brs);	
    				//}
    			}
    		}
    		session.setAttribute("v_list_survey_no_evaluasi_oleh_saya", v_list_survey_no_evaluasi);
    		
    		Vector v_list_upcoming_survey = sm.getSurveyYgAkanDatang(kdpst);
    		if(v_list_upcoming_survey!=null && v_list_upcoming_survey.size()>0) {
    			ListIterator li = v_list_upcoming_survey.listIterator();
    			while(li.hasNext() ) {
    				String brs = (String)li.next();
    				//tgl_sidak+"~"+kdpst+"~"+controller+"~"+surveyor+"~"+param+"~"+id_hist+"~"+id_kendali+"~"+id_versi+"~"+id_std_isi+"~"+id_std+"~"+id_master_std+"~"+id_tipe_std+"~"+ket_tipe_std;
    				st = new StringTokenizer(brs,"~");
    				String tgl_sidak = st.nextToken();
    				String target_kdpst = st.nextToken();
    				String controller = st.nextToken();
    				String surveyor = st.nextToken();
    				//System.out.println("controller="+controller);
    				//System.out.println("surveyor="+surveyor);
    				String seperator = "`";
    				if(controller.contains(",")||surveyor.contains(",")) {
    					seperator = ",";
    				}
    				else if(controller.contains("~")||surveyor.contains("~")) {
    					seperator = "~";
    				}
    				String role_gabungan = controller+seperator+surveyor;
    				role_gabungan = role_gabungan.replace(seperator+seperator, seperator);
    				String param = st.nextToken();
    				if(!isu.amI_v1(role_gabungan, target_kdpst)) {
    					li.remove();
    				}
    				//else {
    				//	//System.out.println(brs);	
    				//}
    			}
    		}
    		session.setAttribute("v_list_upcoming_survey", v_list_upcoming_survey);
    		//System.out.println("v_list_upcoming_survey sie = "+v_list_upcoming_survey.size());
    		
    		
    		Vector v_list_unexecute_survey = sm.getSurveyYgBelumDilaksanakan(kdpst);
    		if(v_list_unexecute_survey!=null && v_list_unexecute_survey.size()>0) {
    			ListIterator li = v_list_unexecute_survey.listIterator();
    			while(li.hasNext() ) {
    				String brs = (String)li.next();
    				//tgl_sidak+"~"+kdpst+"~"+controller+"~"+surveyor+"~"+param+"~"+id_hist+"~"+id_kendali+"~"+id_versi+"~"+id_std_isi+"~"+id_std+"~"+id_master_std+"~"+id_tipe_std+"~"+ket_tipe_std;
    				st = new StringTokenizer(brs,"~");
    				String tgl_sidak = st.nextToken();
    				String target_kdpst = st.nextToken();
    				String controller = st.nextToken();
    				String surveyor = st.nextToken();
    				//System.out.println("controller="+controller);
    				//System.out.println("surveyor="+surveyor);
    				String seperator = "`";
    				if(controller.contains(",")||surveyor.contains(",")) {
    					seperator = ",";
    				}
    				else if(controller.contains("~")||surveyor.contains("~")) {
    					seperator = "~";
    				}
    				String role_gabungan = controller+seperator+surveyor;
    				role_gabungan = role_gabungan.replace(seperator+seperator, seperator);
    				String param = st.nextToken();
    				if(!isu.amI_v1(role_gabungan, target_kdpst)) {
    					li.remove();
    				}
    				//else {
    				//	//System.out.println(brs);	
    				//}
    			}
    		}
    		session.setAttribute("v_list_unexecute_survey", v_list_unexecute_survey);
    		
    		
    		/*
    		 * get standar aktif
    		 */
    		SearchStandarMutu stm = new SearchStandarMutu(isu.getNpm());
    		Vector v_aktif = null;
    		if(spmi_editor) {
    			v_aktif = stm.getStandardAktif(true, kdpst, isu.getIdObj(), "prodi");
    		}
    		else {
    			v_aktif = stm.getStandardAktif(false, kdpst, isu.getIdObj(), "prodi");
    		}
    		session.setAttribute("v_std_aktif", v_aktif);
    		
    		/*
    		* get standar aktif
   		 	*/
    		Vector v_blm_aktif = null;
   			v_blm_aktif = stm.getStandardBelumAktif(spmi_editor, kdpst, isu.getIdObj(), "prodi");
   			session.setAttribute("v_std_blm_aktif", v_blm_aktif);
   			
   			
   			
   			/*
    		if(v_aktif!=null) {
    			//System.out.println("v_active="+v_aktif.size());
    		}
    		else {
    			//System.out.println("v_active=null");
    		}
    		*/
    		if(!Checker.isStringNullOrEmpty(fwdto)&&fwdto.equalsIgnoreCase("overview")) {
    			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/spmi_overview/dash_overview.jsp";
    			String uri = request.getRequestURI();
    			String url = PathFinder.getPath(uri, target);
    			request.getRequestDispatcher(url+"?kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);
    		}
    		//PrintWriter out = response.getWriter();
			//
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package servlets.spmi.standard.manual;

import java.io.PrintWriter;
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
import beans.dbase.spmi.form.UpdateForm;
import beans.dbase.spmi.manual.SearchManual;
import beans.dbase.spmi.manual.UpdateManual;
import beans.dbase.spmi.request.SearchRequest;
import beans.dbase.spmi.riwayat.pengendalian.UpdHistEval;
import beans.dbase.spmi.riwayat.pengendalian.UpdHistKendal;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateRiwayatMonitoring
 */
@WebServlet("/UpdateRiwayatMonitoring")
public class UpdateRiwayatMonitoring extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateRiwayatMonitoring() {
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
			
			//System.out.println("gowes");
		

			
			
			String std_kdpst = request.getParameter("std_kdpst");
			String id_versi = request.getParameter("id_versi");
			String id_std_isi = request.getParameter("id_std_isi");
			String id_std = request.getParameter("id_std");
			String id_tipe_std = request.getParameter("id_tipe_std");
			String id_master_std = request.getParameter("id_master_std");
			String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
			String kdpst_kendali = request.getParameter("kdpst_kendali");
			String at_menu_kendal = request.getParameter("at_menu_kendal");
			String at_menu_dash = request.getParameter("at_menu_dash");
			String tgl = request.getParameter("tgl");
			String waktu = request.getParameter("waktu");
			String target_val = request.getParameter("target_val");
			String target_unit_used = request.getParameter("target_unit_used");
			String ril_val = request.getParameter("ril_val");
			String indikator = request.getParameter("indikator");
			String sikon = request.getParameter("sikon");
			String analisa = request.getParameter("analisa");
			String rekomendasi = request.getParameter("rekomendasi");
			String nmm_obj_eval = request.getParameter("nmm_obj_eval");
			String scope_std = request.getParameter("scope_std");
			String scope_isi_std = request.getParameter("scope_isi_std");
			//String next_waktu_survey = request.getParameter("next_waktu_survey");
			//String next_tgl_survey = request.getParameter("next_tgl_survey");
			
			String npm_eval = isu.getNpm();
			
			kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
			StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
			String kdpst = st.nextToken();
			//String nmpst = st.nextToken();
			//String kdkmp = st.nextToken();
			//System.out.println("id_kendali="+id_kendali);
			//System.out.println("id_hist="+id_hist);
			//String tgl = request.getParameter("tgl");
			//String waktu = request.getParameter("waktu");
			
			
			
			
			
			Vector v_err=null;
			ListIterator li = null;
			
			if(!Converter.cekInputTglValidity(tgl)) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Tgl kegiatan harap diisi dengan format tgl/bln/thn");
				//System.out.println("Tgl pengawasan harap diisi dengan format tgl/bln/thn");
			}
			//else {
			//	request.setAttribute("tgl", tgl);
			//}
			/*
			if(!Converter.cekInputTglValidity(next_tgl_survey)) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Tgl pengawasan berikutnya harap diisi dengan format tgl/bln/thn");
				//System.out.println("Tgl pengawasan harap diisi dengan format tgl/bln/thn");
			}
			*/
			//else {
			//	request.setAttribute("tgl", tgl);
			//}
			
			if(!Converter.cekInputWaktuValidity(waktu)) {
				//System.out.println("wkatu salah");
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Waktu pengawasan harap diisi dengan format jam:mnt [contoh 28:30]");
				
			}
			/*
			if(!Converter.cekInputWaktuValidity(next_waktu_survey)) {
				//System.out.println("wkatu salah");
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Waktu pengawasan berikutnya harap diisi dengan format jam:mnt [contoh 28:30]");
				
			}
			*/
			//else {
			//	request.setAttribute("waktu", waktu);
			//}
			
			
			
			//System.out.println("tgl="+tgl);
			//System.out.println("waktu="+waktu);
			//System.out.println("rasionale_eval="+rasionale_eval);
			//System.out.println("tindakan_eval="+tindakan_eval);
			
			if(v_err!=null) {
				//ada error BALIK ke form
				//System.out.println("ada error");
				request.setAttribute("v_err", v_err);
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual_pelaksanaan/form_survey_pelaksanaan_monitor_v1.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url).forward(request,response);
			}
			else {
				//System.out.println("no error");
				
				UpdHistEval uhk = new UpdHistEval();
				//id_versi
        		//id_std_isi
        		//norut_man
        		//NPM_EVALUATOR
        		//tgl
        		//waktu
        		//
        		//sikon
        		//analisa
        		//rekomendasi
        		//next_tgl_survey
        		//next_waktu_survey
				//int updated = uhk.updateRiwayatEvaluasiAmi_v1(id_versi, id_std_isi, npm_eval, tgl, waktu, sikon, analisa, rekomendasi, next_tgl_survey, next_waktu_survey, target_val, ril_val,kdpst);
				if(scope_isi_std.equalsIgnoreCase("prodi")) {
					int updated = uhk.updateRiwayatEvaluasiAmi_v1(id_versi, id_std_isi, npm_eval, tgl, waktu, sikon, analisa, rekomendasi, target_val, ril_val,kdpst);	
				}
				else if(scope_isi_std.equalsIgnoreCase("univ")) {
					Vector v_list_kdpst=Getter.getListKdpst();
					int updated = uhk.updateRiwayatEvaluasiAmiLevelUniv_v1(id_versi, id_std_isi, npm_eval, tgl, waktu, sikon, analisa, rekomendasi, target_val, ril_val,v_list_kdpst);
				}
				
				//System.out.println("updated="+updated);
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual_pelaksanaan/riwayat_monitor_v1.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
												   
				request.getRequestDispatcher(url+"?starting_no=1&unit_used="+target_unit_used+"&scope_std="+scope_std+"&std_kdpst="+std_kdpst+"&id_versi="+id_versi+"&id_std_isi="+id_std_isi+"&id_tipe_std="+id_tipe_std+"&id_master_std="+id_master_std+"&id_std="+id_std+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&at_menu_kendal=ami&at_menu_dash=do").forward(request,response);
				
			}
			/*
			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_pengendalian.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url).forward(request,response);
			*/
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

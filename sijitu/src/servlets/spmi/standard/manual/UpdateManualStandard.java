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
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateManualStandard
 */
@WebServlet("/UpdateManualStandard")
public class UpdateManualStandard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateManualStandard() {
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
			//System.out.println("masuk");
			String hapus = request.getParameter("hapus");
			String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
			StringTokenizer st = null;
			if(kdpst_nmpst_kmp!=null && kdpst_nmpst_kmp.contains("-")) {
				st = new StringTokenizer(kdpst_nmpst_kmp,"-");
			}
			else {
				st = new StringTokenizer(kdpst_nmpst_kmp,"`");
			}
			String kdpst = st.nextToken();
			String nmpst = st.nextToken();
			String kdkmp = st.nextToken();
			
			String id_versi = request.getParameter("id_versi");
			String id_std_isi = request.getParameter("id_std_isi");
			String id_std = request.getParameter("id_std");
			String at_menu_dash = request.getParameter("at_menu_dash");
			String id_kendali = request.getParameter("id_kendali");
			
			String tipe_sarpras = request.getParameter("tipe_sarpras");//none, umum,detil
			String catat_civitas = request.getParameter("catat_civitas");
			//System.out.println("tipe_sarpras="+tipe_sarpras);
			//System.out.println("catat_civitas="+catat_civitas);
			
			if(!Checker.isStringNullOrEmpty(hapus) && hapus.equalsIgnoreCase("true")) {
				UpdateManual um =new UpdateManual();
				int updated = um.hapusManualStandard(Integer.parseInt(id_kendali));
				String tmp = "get.prepInfoStd?id_versi="+id_versi+"&id_std_isi="+id_std_isi+"&id_std="+id_std+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&at_menu_dash="+ at_menu_dash;
				request.getRequestDispatcher(tmp).forward(request,response);
			}
			else {
				
				Vector v_err = null;
				ListIterator li = null;
				boolean no_err=true;
				String norut = request.getParameter("norut");
				if(Checker.isStringNullOrEmpty(norut)) {
					no_err=false;
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("'No Urut' Manual harus diisi");
				}
				String ket_proses = request.getParameter("ket_proses");
				if(Checker.isStringNullOrEmpty(ket_proses)) {
					no_err=false;
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("'Proses / Objek / Parameter yg Dikendalikan' harus diisi");
				}
				else {
					ket_proses = ket_proses.replace("<br>", "");
					if(ket_proses.contains("\n")) {
						ket_proses = ket_proses.replace("\n", "<br>");
					}
				}
				String capaian = request.getParameter("capaian");
				if(Checker.isStringNullOrEmpty(capaian)) {
					no_err=false;
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("'Target Capaian Kondisi' harus diisi");
				}
				else {
					capaian = capaian.replace("<br>", "");
					if(capaian.contains("\n")) {
						capaian = capaian.replace("\n", "<br>");
					}
				}
				String[]job = request.getParameterValues("job");
				if(job==null || job.length<1) {
					no_err=false;
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("'Pengawas' harus diisi");
				}
				else if(job.length>1) {
					no_err=false;
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("'Pengawas' tidak boleh lebih dari satu");
				}
				String manual = request.getParameter("manual");
				if(Checker.isStringNullOrEmpty(manual)) {
					no_err=false;
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("'Manual Proses Pengawasan' harus diisi");
				}
				else {
					manual = manual.replace("<br>", "");
					if(manual.contains("\n")) {
						manual = manual.replace("\n", "<br>");
					}
				}
				String[]job_input = request.getParameterValues("job_input");
				if(job_input==null || job_input.length<1) {
					no_err=false;
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("'Penginput Data Awal' harus diisi");
				}
				else if(job_input.length>1) {
					no_err=false;
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("'Penginput Data Awal' tidak boleh lebih dari satu");
				}
				String tot_repetisi = request.getParameter("tot_repetisi");
				String satuan = request.getParameter("satuan");
				if(Checker.isStringNullOrEmpty(tot_repetisi)||Checker.isStringNullOrEmpty(satuan)) {
					no_err=false;
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("'Periode Pengawasan' harus diisi");
				}
				
				if(!no_err) {
					request.setAttribute("v_err", v_err);
					///ToUnivSatyagama/WebContent/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_pengendalian.jsp
					String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_pengendalian.jsp";
					String uri = request.getRequestURI();
					String url = PathFinder.getPath(uri, target);
					//String tmp = "get.prepInfoStd?id_versi="+id_versi+"&id_std_isi="+id_std_isi+"&id_std="+id_std+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&at_menu_dash="+ at_menu_dash;
					//request.getRequestDispatcher(url+"?id_versi="+id_versi+"&id_std_isi="+id_std_isi+"&id_std="+id_std+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&at_menu_dash="+at_menu_dash).forward(request,response);
					request.getRequestDispatcher(url).forward(request,response);
				}
				else {
					UpdateManual um =new UpdateManual();
					int updated = um.updateManualStandard(id_kendali, Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), Integer.parseInt(norut), capaian, ket_proses, manual, null, null, Integer.parseInt(tot_repetisi), satuan, job[0], job_input[0], null, null, tipe_sarpras, Boolean.parseBoolean(catat_civitas));
					
					String tmp = "get.prepInfoStd?id_versi="+id_versi+"&id_std_isi="+id_std_isi+"&id_std="+id_std+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&at_menu_dash="+ at_menu_dash;
					//String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_pengendalian.jsp";
					//String uri = request.getRequestURI();
					//String url = PathFinder.getPath(uri, target);
					
					//request.getRequestDispatcher(url+"?id_versi="+id_versi+"&id_std_isi="+id_std_isi+"&id_std="+id_std+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&at_menu_dash="+at_menu_dash).forward(request,response);
					request.getRequestDispatcher(tmp).forward(request,response);
				}	
			}
			
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

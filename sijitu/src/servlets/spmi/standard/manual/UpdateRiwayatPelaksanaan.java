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
import beans.dbase.spmi.ToolSpmi;
import beans.dbase.spmi.form.UpdateForm;
import beans.dbase.spmi.manual.SearchManual;
import beans.dbase.spmi.manual.UpdateManual;
import beans.dbase.spmi.request.SearchRequest;
import beans.dbase.spmi.riwayat.pengendalian.UpdHistEval;
import beans.dbase.spmi.riwayat.pengendalian.UpdHistKendal;
import beans.dbase.spmi.riwayat.pengendalian.UpdHistPelaksanaan;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateRiwayatPelaksanaan
 */
@WebServlet("/UpdateRiwayatPelaksanaan")
public class UpdateRiwayatPelaksanaan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateRiwayatPelaksanaan() {
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
		

			
			String scope_std = request.getParameter("scope_std");
			String std_kdpst = request.getParameter("std_kdpst");
			String id_versi = request.getParameter("id_versi");
			String id_std_isi = request.getParameter("id_std_isi");
			String id_std = request.getParameter("id_std");
			String norut_man = request.getParameter("norut_man");
			String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
			StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
			String kdpst = st.nextToken();
			String nmpst = st.nextToken();
			String kdkmp = st.nextToken();
			//String kdpst_kendali = request.getParameter("kdpst_kendali");
			String at_menu_kendal = request.getParameter("at_menu_kendal");
			String at_menu_dash = request.getParameter("at_menu_dash");
			
			//form rencana
			String id_plan = request.getParameter("id_plan"); //null kalo pertama kali
			String form_tgl_sta = request.getParameter("tgl_sta");
			String form_waktu_sta = request.getParameter("waktu_sta");
			String form_jenis_kegiatan = request.getParameter("jenis_kegiatan");
			String form_nmm_kegiatan = request.getParameter("nmm_kegiatan");
			String form_tujuan_kegiatan = request.getParameter("tujuan_kegiatan");
			String form_isi_kegiatan = request.getParameter("isi_kegiatan");
			String[] form_job_jawab = request.getParameterValues("job_jawab");
			String[] form_job_target = request.getParameterValues("job_target");
			String[] form_dok_mutu = request.getParameterValues("dok_mutu");
			String form_started = request.getParameter("started");
			
			String npm_eval = isu.getNpm();
			
			
			//System.out.println("id_kendali="+id_kendali);
			//System.out.println("id_hist="+id_hist);
			//String tgl = request.getParameter("tgl");
			//String waktu = request.getParameter("waktu");
			
			
			
			
			
			Vector v_err=null, v_ok=null;
			ListIterator li = null;
			
			if(!Converter.cekInputTglValidity(form_tgl_sta)) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Tgl kegiatan harap diisi dengan format tgl/bln/thn");
				//System.out.println("Tgl pengawasan harap diisi dengan format tgl/bln/thn");
			}
			if(!Converter.cekInputWaktuValidity(form_waktu_sta)) {
				//System.out.println("wkatu salah");
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Waktu kegiatan harap diisi dengan format jam:mnt [contoh 28:30]");
				
			}
			if(!Converter.cekInputStringValidity(form_jenis_kegiatan, 1)) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Jenis kegiatan harap diisi");
			}
			if(!Converter.cekInputStringValidity(form_nmm_kegiatan, 5)) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Nama kegiatan harap diisi [min 5 char]");
			}
			if(!Converter.cekInputStringValidity(form_tujuan_kegiatan, 15)) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Tujuan kegiatan harap diisi [min 15 char]");
			}
			if(!Converter.cekInputStringValidity(form_isi_kegiatan, 15)) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Isi kegiatan harap diisi [min 15 char]");
			}
			
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
			if(!Converter.cekInputStringArrayValidity(form_job_jawab, 1)) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Penanggung-jawab kegiatan harap diisi");
			}
			if(!Converter.cekInputStringArrayValidity(form_job_target, 1)) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Target anggota kegiatan harap diisi ");
			}
			if(!Converter.cekInputStringArrayValidity(form_dok_mutu, 1)) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Bukti Dokumen kegiatan harap diisi");
			}
			if(form_started==null) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Status dimulainya kegiatan harap diisi");
			}
			
			
			//System.out.println("tgl="+tgl);
			//System.out.println("waktu="+waktu);
			//System.out.println("rasionale_eval="+rasionale_eval);
			//System.out.println("tindakan_eval="+tindakan_eval);
			
			if(v_err!=null) {
				//ada error BALIK ke form
				//System.out.println("ada error");
				request.setAttribute("v_err", v_err);
				String target = null;
				if(!Checker.isStringNullOrEmpty(id_plan)) {
					target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual_pelaksanaan/riwayat_pelaksanaan.jsp";
				}
				else {
					target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual_pelaksanaan/form_survey_pelaksanaan.jsp";
				}
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url).forward(request,response);
			}
			else {
				//System.out.println("no error");
				
				UpdHistPelaksanaan uhk = new UpdHistPelaksanaan();

				int updated = uhk.updateRiwayatPelaksanaan(id_plan,id_versi,id_std_isi,norut_man,form_tgl_sta,form_waktu_sta,form_nmm_kegiatan,form_jenis_kegiatan,form_tujuan_kegiatan,form_isi_kegiatan,form_job_jawab,form_job_target,form_dok_mutu,form_started);
				if(updated<1) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Update data gagal, harap hubungi admin\nMohon maaf atas ketidak nyamanannya");
					request.setAttribute("v_err", v_err);
				}
				else {
					if(v_ok==null) {
						v_ok = new Vector();
						li = v_ok.listIterator();
					}
					li.add("Update data berhasil");
					
					request.setAttribute("v_ok", v_ok);
					//create folder kegiatan
					ToolSpmi ts = new ToolSpmi();
					ts.createSubFolderManualPelaksanaan(id_std_isi, id_versi, norut_man, kdpst, form_jenis_kegiatan, form_nmm_kegiatan);
					
				}
				//System.out.println("updated="+updated);
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual_pelaksanaan/riwayat_pelaksanaan.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				
				//request.getRequestDispatcher(url+"?starting_no=1&&norut_man="+norut_man+"&scope_std="+scope_std+"&std_kdpst="+std_kdpst+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&at_menu_kendal="+at_menu_kendal+"&at_menu_dash="+at_menu_dash).forward(request,response);
				request.getRequestDispatcher(url).forward(request,response);
				
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

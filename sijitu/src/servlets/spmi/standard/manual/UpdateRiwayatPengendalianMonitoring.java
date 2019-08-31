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
import beans.dbase.spmi.riwayat.pengendalian.UpdHistKendal;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateRiwayatPengendalianMonitoring
 */
@WebServlet("/UpdateRiwayatPengendalianMonitoring")
public class UpdateRiwayatPengendalianMonitoring extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateRiwayatPengendalianMonitoring() {
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
			//System.out.println("siaoop");
			String scope_std_isi = request.getParameter("scope_std_isi");
			String tgl_kendal = request.getParameter("tgl_kendal");
			//System.out.println("tgl_kendal="+tgl_kendal);
			String waktu_kendal = request.getParameter("waktu_kendal");
			//System.out.println("waktu_kendal="+waktu_kendal);
			String rasionale_kendal = request.getParameter("rasionale_kendal");
			//System.out.println("rasionale_kendal="+rasionale_kendal);
			String tindakan_kendal = request.getParameter("tindakan_kendal");
			//System.out.println("tindakan_kendal="+tindakan_kendal);
			String id_eval = request.getParameter("id_eval");
			//System.out.println("id_eval="+id_eval);
			String tilang = request.getParameter("tilang");
			String jenis_pelanggaran = null;
			String next_waktu_survey = request.getParameter("next_waktu_survey");
			String next_tgl_survey = request.getParameter("next_tgl_survey");
			//System.out.println("next_tgl_survey="+next_tgl_survey);
			Vector v_err=null;
			ListIterator li = null;
			
			if(!Converter.cekInputTglValidity(tgl_kendal)) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Tgl pengendalian harap diisi dengan format tgl/bln/thn");
				//System.out.println("Tgl pengawasan harap diisi dengan format tgl/bln/thn");
			}
			
			if(!Converter.cekInputWaktuValidity(waktu_kendal)) {
				//System.out.println("wkatu salah");
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Waktu pengendalian harap diisi dengan format jam:mnt [contoh 28:30]");
				
			}
			if(Checker.isStringNullOrEmpty(rasionale_kendal)) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Rasionale harap diisi");
			}
			else {
				while(rasionale_kendal.contains("\n")) {
					rasionale_kendal = rasionale_kendal.replace("\n", "<br>");
				}
			}
			
			if(Checker.isStringNullOrEmpty(tindakan_kendal)) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Tindakan pengendalian harap diisi");
			}
			else {
				while(tindakan_kendal.contains("\n")) {
					tindakan_kendal = tindakan_kendal.replace("\n", "<br>");
				}
			}
			
			if(Checker.isStringNullOrEmpty(tilang)) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Status penyimpangan harap diisi");
			}
			
			if(!Converter.cekInputTglValidity(next_tgl_survey)) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Tgl pengawasan berikutnya harap diisi dengan format tgl/bln/thn");
				//System.out.println("Tgl pengawasan harap diisi dengan format tgl/bln/thn");
			}
			
			if(v_err!=null) {
				//ada error BALIK ke form
				//System.out.println("ada error");
				request.setAttribute("v_err", v_err);
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual_pelaksanaan/riwayat_kendali_mon_v1.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url).forward(request,response);
			}
			else {
				//System.out.println("no error lanjut insert");
				String npm_kendal = isu.getNpm();
				UpdHistKendal uhk = new UpdHistKendal();
				Vector v_list_kdpst=Getter.getListKdpst();
				//int upd = uhk.updateRiwayatPengendalianAmi(id_eval, tgl_kendal, waktu_kendal, rasionale_kendal, tindakan_kendal, npm_kendal, tilang, jenis_pelanggaran,next_tgl_survey,next_waktu_survey);
				int upd = uhk.updateRiwayatPengendalianAmiLevelUniv(id_eval, tgl_kendal, waktu_kendal, rasionale_kendal, tindakan_kendal, npm_kendal, tilang, jenis_pelanggaran,next_tgl_survey,next_waktu_survey,v_list_kdpst);
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual_pelaksanaan/riwayat_kendali_mon_v1.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url).forward(request,response);
			}
			/*
			String mode = request.getParameter("mode");
			String tipe_sarpras = request.getParameter("tipe_sarpras");
			//jika tipe_sarpras = umum
			String tipe_sarpras_kendali = request.getParameter("tipe_sarpras_kendali");
			//jika tipe_sarpras = detil
			String sarpras_kendali = request.getParameter("sarpras_kendali");
			String id_sarpras="null";
			
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
			String id_kendali = request.getParameter("id_kendali");
			String at_menu_kendal = request.getParameter("at_menu_kendal");
			String id_versi = request.getParameter("id_versi");
			String id_std_isi = request.getParameter("id_std_isi");
			String id_std = request.getParameter("id_std");
			//yg ini ngga perlu dicek & di set parameternya karena selalu diitung ulang di di formnya
			String periode_ke = request.getParameter("periode_ke");
			String target_val = request.getParameter("target_val");
			String unit_used = request.getParameter("unit_used");
			//String unit_used = request.getParameter("unit_used");
			String std_kdpst = request.getParameter("std_kdpst");

			String catat_civitas = request.getParameter("catat_civitas");
			//System.out.println("catat_civitas="+catat_civitas);
			//jika catat civitas = 1
			String info_target_civitas = request.getParameter("info_target_civitas");
			//System.out.println("info_target_civitas="+info_target_civitas);
			String npm_target_civitas = "null";
			//System.out.println("info_target_civitas="+info_target_civitas);
			
			
			String scope_std = request.getParameter("scope_std");
			//bila scope = prodi
			String kdpst_kendali = request.getParameter("kdpst_kendali");
			
			
			Vector v_err=null;
			ListIterator li = null;
			
			
			if(!Checker.isStringNullOrEmpty(mode)&&mode.equalsIgnoreCase("searching")) {
				//System.out.println("searching mode");
				String kword = request.getParameter("kword");
				Vector v_list = new Vector();
				if(!Checker.isStringNullOrEmpty(kword)) {
					v_list = isu.searchCivitasPegawaiOnly(kword,null);
				}
				request.setAttribute("v_list", v_list);
				//String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual_pengendalian/form_survey_pengendalian.jsp";
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual_evaluasi/form_survey_evaluasi.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url+"?id_versi="+id_versi+"&id_std_isi="+id_std_isi+"&id_std="+id_std+"&id_kendali="+id_kendali+"&at_menu_kendal=form&kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);
			}
			else {
				//cek sarpras
				if(tipe_sarpras!=null && tipe_sarpras.equalsIgnoreCase("umum") && Checker.isStringNullOrEmpty(tipe_sarpras_kendali)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Harap pilih tipe sarpras (sarana prasarana)");
				}
				//cek sarpras
				if(tipe_sarpras!=null && tipe_sarpras.equalsIgnoreCase("detil") && Checker.isStringNullOrEmpty(sarpras_kendali)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Harap pilih sarpras (sarana prasarana)");
				}
				else if(tipe_sarpras!=null && tipe_sarpras.equalsIgnoreCase("detil") && !Checker.isStringNullOrEmpty(sarpras_kendali)) {
					st = new StringTokenizer(sarpras_kendali,"`");
					if(st.hasMoreTokens()) {
				//id+"`"+tipe_sar+"`"+sub_tipe_sar+"`"+kode_sar+"`"+nama_sar+"`"+dimensi+"`"+luas+"`"+kdkmp+"`"+gedung+"`"+lantai;
						id_sarpras = st.nextToken();
						String tipe_sar = st.nextToken();
						String sub_tipe_sar = st.nextToken();
						String kode_sar = st.nextToken();
						String nama_sar = st.nextToken();
						String dimensi = st.nextToken();
						String luas = st.nextToken();
						String kodkmp = st.nextToken();
						String gedung = st.nextToken();
						String lantai = st.nextToken();
					}
					else {
						if(v_err==null) {
							v_err = new Vector();
							li = v_err.listIterator();
						}
						li.add("Harap pilih sarpras (sarana prasarana)");
					}
				}
				
				//cek jika scope_std = prodi tapi blum ada kdpst yg dipilih
				if(scope_std!=null && scope_std.equalsIgnoreCase("prodi") && Checker.isStringNullOrEmpty(kdpst_kendali)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Harap pilih prodi yg sedang disurvey");
				}
				else if(scope_std!=null && scope_std.equalsIgnoreCase("prodi") && !Checker.isStringNullOrEmpty(kdpst_kendali)) {
					//nmpst+"`"+kdpst+"`"+kdjen+"`"+kdfak);
					st = new StringTokenizer(kdpst_kendali,"`");
					if(st.countTokens()==4) {
						st.nextToken();	
						kdpst_kendali = st.nextToken();	
					}
					else {
						if(v_err==null) {
							v_err = new Vector();
							li = v_err.listIterator();
						}
						li.add("Harap pilih prodi yg sedang disurvey");
					}
					
				}
				//cek target_civitas
				if(catat_civitas.equalsIgnoreCase("1") && Checker.isStringNullOrEmpty(info_target_civitas)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Harap pilih civitas yg sedang disurvey");
				}
				else {
					////nmmhs+"`"+objdesc+"`"+npmhs;
					if(!Checker.isStringNullOrEmpty(info_target_civitas)) {
						//System.out.println("info_target_civitas="+info_target_civitas);
						if(info_target_civitas.contains("~")) {
							st = new StringTokenizer(info_target_civitas,"~");
						}
						else {
							st = new StringTokenizer(info_target_civitas,"`");	
						}
						
						st.nextToken();
						st.nextToken();
						npm_target_civitas = st.nextToken();
					}
					
					
				}
				
				
				String tgl = request.getParameter("tgl");
				if(!Converter.cekInputTglValidity(tgl)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Tgl pengawasan harap diisi dengan format tgl/bln/thn");
					//System.out.println("Tgl pengawasan harap diisi dengan format tgl/bln/thn");
				}
				else {
					request.setAttribute("tgl", tgl);
				}
				String waktu = request.getParameter("waktu");
				//System.out.println("waktu="+waktu);
				if(!Converter.cekInputWaktuValidity(waktu)) {
					//System.out.println("wkatu salah");
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Waktu pengawasan harap diisi dengan format jam:mnt [contoh 28:30]");
					
				}
				else {
					request.setAttribute("waktu", waktu);
				}
				
				String next_tgl_survey = request.getParameter("next_tgl_survey");
				if(!Converter.cekInputTglValidity(next_tgl_survey)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Tgl jadwal pengawasan berikutnya harap diisi dengan format tgl/bln/thn");
					//System.out.println("Tgl pengawasan harap diisi dengan format tgl/bln/thn");
				}
				else {
					request.setAttribute("next_tgl_survey", next_tgl_survey);
				}
				String next_waktu_survey = request.getParameter("next_waktu_survey");
				//System.out.println("waktu="+waktu);
				if(!Converter.cekInputWaktuValidity(next_waktu_survey)) {
					//System.out.println("wkatu salah");
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Waktu jadwal pengawasan berikutnya harap diisi dengan format jam:mnt [contoh 28:30]");
					
				}
				else {
					request.setAttribute("next_waktu_survey", next_waktu_survey);
				}
				
				String ril_val = request.getParameter("ril_val");
				//System.out.println("ril_val="+ril_val);
				if(!Converter.cekInputDoubleValidity(ril_val, "0", null)) {
					//System.out.println("salah");
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Besaran ril harap diisi dengan benar");
					
				}
				else {
					//System.out.println("benar");
					request.setAttribute("ril_val", ril_val);				
				}
				
				String sikon = request.getParameter("sikon");
				if(!Converter.cekInputStringValidity(sikon, 15)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Gambarkan kondisi saat pengawasan harap diisi [minimal 25 karakter]");
				}
				else {
					request.setAttribute("sikon", sikon);
				}
				String analisa = request.getParameter("analisa");
				if(!Converter.cekInputStringValidity(analisa, 15)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Hasil Analisa harap diisi [minimal 25 karakter]");
				}
				else {
					request.setAttribute("analisa", analisa);
				}
				String rekomendasi = request.getParameter("rekomendasi");
				if(!Converter.cekInputStringValidity(rekomendasi, 15)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Rekomendasi tindakan harap diisi [minimal 25 karakter]");
				}
				else {
					request.setAttribute("rekomendasi", rekomendasi);
				}
				
				
				
				
				
				
				
				
				
				 * error checking
			
				if(v_err!=null) {
					//ada error BALIK ke form
					request.setAttribute("v_err", v_err);
					//String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual_pengendalian/form_survey_pengendalian.jsp";
					String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual_evaluasi/form_survey_evaluasi.jsp";
					String uri = request.getRequestURI();
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url+"?id_versi="+id_versi+"&id_std_isi="+id_std_isi+"&id_std="+id_std+"&id_kendali="+id_kendali+"&at_menu_kendal=form&kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);
				}
				else {
					//ngga ada error
					//removes semua attribue
					request.removeAttribute("next_tgl_survey");
					request.removeAttribute("next_waktu_survey");
					request.removeAttribute("tgl");
					request.removeAttribute("waktu");
					request.removeAttribute("ril_val");
					request.removeAttribute("sikon");
					request.removeAttribute("analisa");
					request.removeAttribute("rekomendasi");
					UpdHistKendal uhk = new UpdHistKendal();
					
					//int updated = uhk.updateRiwayatPengendalian(id_kendali, tgl, waktu, periode_ke, Double.parseDouble(ril_val), sikon, analisa, rekomendasi, isu.getNpm(), target_val, unit_used, kdpst_kendali, id_sarpras, tipe_sarpras, npm_target_civitas,next_tgl_survey,next_waktu_survey);
					int updated = uhk.updateRiwayatPengendalian(id_kendali, tgl, waktu, periode_ke, Double.parseDouble(ril_val), sikon, analisa, rekomendasi, isu.getNpm(), target_val, unit_used, kdpst, id_sarpras, tipe_sarpras, npm_target_civitas,next_tgl_survey,next_waktu_survey);
					//String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual_pengendalian/riwayat_kendal.jsp";
					String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual_evaluasi/riwayat_evaluasi.jsp";
					String uri = request.getRequestURI();
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url+"?id_versi="+id_versi+"&id_std_isi="+id_std_isi+"&id_std="+id_std+"&id_kendali="+id_kendali+"&at_menu_kendal=riwayat&kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);	
				}
			}
			
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

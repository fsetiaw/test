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
//import beans.dbase.spmi.riwayat.pengendalian.UpdHistPelaksanaan;
import beans.dbase.spmi.riwayat.pengendalian.UpdHistPeningkatan;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateRiwayatHasilPeningkatan_v1
 */
@WebServlet("/UpdateRiwayatHasilPeningkatan_v1")
public class UpdateRiwayatHasilPeningkatan_v1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateRiwayatHasilPeningkatan_v1() {
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
			String id_tipe_std = request.getParameter("id_tipe_std");
			String id_std = request.getParameter("id_std");
			String id_master_std = request.getParameter("id_master_std");
			String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
			String at_menu_kendal = request.getParameter("at_menu_kendal");
			String at_menu_dash = request.getParameter("at_menu_dash");
			String id_plan = request.getParameter("id_plan");
			//form rencana
			
			String form_tgl_end = request.getParameter("tgl_end");
			String form_waktu_end = request.getParameter("waktu_end");
			String form_hasil = request.getParameter("hasil");
			String form_note = request.getParameter("note");
			String form_penetapan_tgl = request.getParameter("penetapan_tgl");
			//System.out.println("form_penetapan_tgl--"+form_penetapan_tgl);
			String npm_eval = isu.getNpm();
			
			
			//System.out.println("id_kendali="+id_kendali);
			//System.out.println("id_hist="+id_hist);
			//String tgl = request.getParameter("tgl");
			//String waktu = request.getParameter("waktu");
			
			
			
			
			
			Vector v_err=null;
			ListIterator li = null;
			//System.out.println("form_tgl_end="+form_tgl_end);
		
			if(!Checker.isStringNullOrEmpty(form_tgl_end)) {
				form_tgl_end = form_tgl_end.trim();
				//form_tgl_end=Converter.autoConvertDateFormat(form_tgl_end, "-");
				//System.out.println("form_tgl_end="+form_tgl_end);
				if(!Converter.cekInputTglValidity(form_tgl_end)) {
					//System.out.println("form_tgl_end=error");
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Tgl kegiatan berakhir harap diisi dengan format tgl/bln/thn");
					//System.out.println("Tgl pengawasan harap diisi dengan format tgl/bln/thn");
				}
				else {
					//System.out.println("form_tgl_end=true");
				}
			}
			
			
			if(!Checker.isStringNullOrEmpty(form_waktu_end)&&!Converter.cekInputWaktuValidity(form_waktu_end)) {
				//System.out.println("wkatu salah");
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Waktu kegiatan berakhir harap diisi dengan format jam:mnt [contoh 28:30]");
				
			}
			if(!Converter.cekInputStringValidity(form_hasil, 10)) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Hasil dari kegiatan harap diisi");
			}
			if(form_penetapan_tgl==null) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("harap diisi status apakah dijadikan sebagai tanggal penetapan");
			}
			if(form_penetapan_tgl!=null&&!form_penetapan_tgl.equalsIgnoreCase("no")&&(Checker.isStringNullOrEmpty(form_tgl_end)||!Converter.cekInputTglValidity(form_tgl_end))) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Sebagai tanggal penetapan, namun tanggal selesai belum terisi dengan benar");
			}
			
			//System.out.println("tgl="+tgl);
			//System.out.println("waktu="+waktu);
			//System.out.println("rasionale_eval="+rasionale_eval);
			//System.out.println("tindakan_eval="+tindakan_eval);
			
			if(v_err!=null) {
				//ada error BALIK ke form
				//System.out.println("ada error22");
				request.setAttribute("v_err", v_err);
				String target = null;
				target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual_peningkatan/riwayat_peningkatan_v1.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url).forward(request,response);
			}
			else {
				
				//System.out.println("no error");
				
				UpdHistPeningkatan uhk = new UpdHistPeningkatan();
				int updated = uhk.updateRiwayatHasilPeningkatan_v1(id_plan, form_hasil, form_note, form_tgl_end, form_waktu_end, form_penetapan_tgl, id_versi, id_std);	
				//uhk.UpdateRiwayatHasilPeningkatan_v1(id_plan, form_hasil, form_note, form_tgl_end, form_waktu_end, form_penetapan_tgl, id_versi, id_std_isi, norut_man);
				//System.out.println("updated="+updated);
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual_peningkatan/riwayat_peningkatan_v1.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				
				request.getRequestDispatcher(url+"?starting_no=1&id_versi="+id_versi+"&id_std="+id_std+"&id_tipe_std="+id_tipe_std+"&id_master_std="+id_master_std+"&scope_std="+scope_std+"&std_kdpst="+std_kdpst+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&at_menu_kendal="+at_menu_kendal+"&at_menu_dash="+at_menu_dash).forward(request,response);
				
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

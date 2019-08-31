package servlets.spmi.standard;

import java.io.IOException;
import java.util.ListIterator;
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
import beans.dbase.spmi.SearchManualMutu;
import beans.dbase.spmi.UpdateManualMutu;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;
/**
 * Servlet implementation class SetAktivasiMan
 */
@WebServlet("/SetAktivasiMan")
public class SetAktivasiMan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetAktivasiMan() {
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
			request.removeAttribute("v_err_man");
		//PrintWriter out = response.getWriter();
			String norut_man=request.getParameter("norut_man");
			String id_versi=request.getParameter("id_versi");
			String id_std_isi=request.getParameter("id_std_isi");
			String id_std=request.getParameter("id_std");
			String kdpst_nmpst_kmp=request.getParameter("kdpst_nmpst_kmp");
			String at_menu_dash=request.getParameter("at_menu_dash");
			String fwdto=request.getParameter("fwdto");
			String status=request.getParameter("status");
			String tipe_manual=request.getParameter("tipe_manual");
			boolean aktif = false;
			if(status.equalsIgnoreCase("aktif")) {
				aktif = true;
			}
			//System.out.println("aktif="+aktif);
			Vector v_err_man = null;
			ListIterator li = null;
			if(!aktif) {
				//bila tombol play (aktifkan), maka lakukan validasi kondisi
				SearchManualMutu smm = new SearchManualMutu();
				/*
				 * ngga bisa dipake, ust revisit fungsi apaAdaManualYgAktif
				 */
				//boolean ada_manual_yg_aktif = smm.apaAdaManualYgAktif(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi));
				//System.out.println("ada_manual_yg_aktif="+ada_manual_yg_aktif);
				boolean ada_manual_yg_aktif=false;
				if(ada_manual_yg_aktif) {
					if(v_err_man==null) {
						v_err_man=new Vector();
						li = v_err_man.listIterator();
					}
					li.add("Ada manual yang masih aktif, silahkan non-aktifkan manual tsb untu mengaktifkan manual yang baru");
				}
				
				boolean manual_ada_tgl_perumusan = smm.apaManualSudahAdaTglPerumusan(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), Integer.parseInt(norut_man));
				if(!manual_ada_tgl_perumusan) {
					if(v_err_man==null) {
						v_err_man=new Vector();
						li = v_err_man.listIterator();
					}
					li.add("Manual yang dipilih belum memiliki kegiatan perumusan manual");
				}
				
				boolean manual_ada_tgl_pemeriksaan = smm.apaManualSudahAdaTglPemeriksaan(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), Integer.parseInt(norut_man));
				if(!manual_ada_tgl_pemeriksaan) {
					if(v_err_man==null) {
						v_err_man=new Vector();
						li = v_err_man.listIterator();
					}
					li.add("Manual yang dipilih belum memiliki kegiatan pemeriksaan manual");
				}
				
				boolean manual_ada_tgl_persetujuan = smm.apaManualSudahAdaTglPersetujuan(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), Integer.parseInt(norut_man));
				if(!manual_ada_tgl_persetujuan) {
					if(v_err_man==null) {
						v_err_man=new Vector();
						li = v_err_man.listIterator();
					}
					li.add("Manual yang dipilih belum memiliki kegiatan persetujuan manual");
				}
				
				boolean manual_ada_tgl_penetapan = smm.apaManualSudahAdaTglPenetapan(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), Integer.parseInt(norut_man));
				if(!manual_ada_tgl_penetapan) {
					if(v_err_man==null) {
						v_err_man=new Vector();
						li = v_err_man.listIterator();
					}
					li.add("Manual yang dipilih belum memiliki kegiatan penetapan manual");
				}
			}
			else {
				//kalo mo stop, tidak ada validasi UNTUK SAAT INI
			}
			
			if(v_err_man!=null) {
				//System.out.println("v_err_man is not null");
				request.setAttribute("v_err_man", v_err_man);
				//request.getRequestDispatcher("get.prepInfoStd?id_versi="+id_versi+"&id_std_isi="+id_std_isi+"&id_std="+id_std+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&at_menu_dash="+ at_menu_dash+"&fwdto=dashboard_std_manual_perencanaan.jsp&darimana=riwayat").forward(request,response);
				request.getRequestDispatcher("get.prepInfoStd?id_versi="+id_versi+"&id_std_isi="+id_std_isi+"&id_std="+id_std+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&at_menu_dash="+ at_menu_dash+"&fwdto="+fwdto+"&darimana=").forward(request,response);
			}
			else {
				//System.out.println("v_err_man is null");
				UpdateManualMutu umm = new UpdateManualMutu();
				umm.toogleAktifasiMan(tipe_manual, Integer.parseInt(norut_man), Integer.parseInt(id_versi), Integer.parseInt(id_std_isi), aktif);
				String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher("get.prepInfoStd?id_versi="+id_versi+"&id_std_isi="+id_std_isi+"&id_std="+id_std+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&at_menu_dash="+ at_menu_dash+"&fwdto="+fwdto+"&darimana=").forward(request,response);
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

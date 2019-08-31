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
import beans.dbase.spmi.SearchManualMutu;
import beans.dbase.spmi.SearchStandarMutu;
import beans.dbase.spmi.doc.SearchDocMutu;
import beans.dbase.spmi.manual.SearchManual;
import beans.dbase.spmi.overview.SearchOverview;
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
public class GetOverviewMutu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetOverviewMutu() {
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
			Vector v_scope_kdpst_spmi = (Vector)session.getAttribute("v_scope_kdpst_spmi");
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
			
    		
    		SearchStandarMutu stm = new SearchStandarMutu(isu.getNpm());
    		Vector v_status_aktif_std = null;
    		v_status_aktif_std = stm.getOverviewStatusAktifStandar();
    		if(v_status_aktif_std!=null) {
    			session.setAttribute("v_status_aktif_std", v_status_aktif_std);	
    		}
    		
    		
    		SearchManualMutu smm = new SearchManualMutu(isu.getNpm());
    		Vector v_status_aktif_man = null;
    		v_status_aktif_man = smm.getOverviewStatusAktifManual();
    		if(v_status_aktif_man!=null) {
    			session.setAttribute("v_status_aktif_man", v_status_aktif_man);	
    		}
    		
    		
    		
    		SearchOverview so = new SearchOverview();
    		Vector v = so.getKegiatanPpeppYgTerlewat();
    		if(v!=null) {
    			request.setAttribute("v_kegiatan_ppepp_yg_lewat", v);	
    		}
    		
    		
    		if(spmi_editor) {
    			v = so.getKegiatanAmiYgTerlewat("all");	
    		}
    		else {
    			v = so.getKegiatanAmiYgTerlewat(kdpst);	
    		}
    		if(v!=null) {
    			request.setAttribute("v_kegiatan_ami_yg_lewat", v);
    		}
    		
    		v = so.getKegiatanMonitoringYgBelumAdaKegiatanPengendalian(kdpst);
    		if(v!=null) {
    			request.setAttribute("v_kegiatan_monitoring_no_pengendalian", v);
    		}
    		
    		v = so.getKegiatanMonitoringYgTerlewat(kdpst);
    		if(v!=null) {
    			request.setAttribute("v_kegiatan_monitoring_no_kelanjutan", v);
    		}
    		
    		v = (Vector)session.getAttribute("v_scope_kdpst_spmi");
    		v = so.getListIsiStdYgBelumPernahDiMonitor(v);
    		if(v!=null) {
    			session.setAttribute("v_isi_std_never_monitored", v);
    		}
    		
    		
    		v = so.getListPelanggaranIsiStd(v_scope_kdpst_spmi);
    		if(v!=null) {
    			//System.out.println("v_pelanggaran_isi_std="+v.size());
    			session.setAttribute("v_pelanggaran_isi_std", v);
    		}
    		else {
    			//System.out.println("v_pelanggaran_isi_std=null");
    		}
    		
    		//get status docuken
    		SearchDocMutu sdm = new SearchDocMutu();
    		Vector v_ListDokumenMutu = Getter.getListDokumenMutu();
    		v = sdm.getStatusKeberadaanDokumen(v_scope_kdpst_spmi, v_ListDokumenMutu);
    		//FH`74201`C`HUKUM~58(tot dok mutu)~0(tot-uploaded)
    		//vector =  nm_doc ~ status
    		if(v!=null) {
    			//System.out.println("kesini");
    			session.setAttribute("v_status_doc_mutu", v);
    		}
    		else {
    			//System.out.println("kesitu");
    			//System.out.println("v_pelanggaran_isi_std=null");
    		}
    		
    		//get prodi yg belum pernai AMI
    		v = so.getListStandardProdiYgBelumPernahDilakukanAmi(v_scope_kdpst_spmi);
    		if(v!=null) {
    			//System.out.println("kesini");
    			session.setAttribute("v_prodi_never_ami", v);
    		}
    		else {
    			//System.out.println("kesitu");
    			//System.out.println("v_pelanggaran_isi_std=null");
    		}
    		
    		
    		//get prodi yg belum pernai AMI
    		v = so.getListProdiYgSudahPernahDiAmi(v_scope_kdpst_spmi);
    		if(v!=null) {
    			//System.out.println("kesini");
    			session.setAttribute("v_riwayat_ami_prodi", v);
    		}
    		else {
    			//System.out.println("kesitu");
    			//System.out.println("v_pelanggaran_isi_std=null");
    		}
    		
    		/*
    		 * get standar aktif
    		 
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
   		
    		Vector v_blm_aktif = null;
   			v_blm_aktif = stm.getStandardBelumAktif(spmi_editor, kdpst, isu.getIdObj(), "prodi");
   			session.setAttribute("v_std_blm_aktif", v_blm_aktif);
   			*/
   			
   			
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
    		else if(!Checker.isStringNullOrEmpty(fwdto)&&fwdto.equalsIgnoreCase("overdue_ppepp")) {
    			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/spmi_overview/notifikasi_overdue_ppepp.jsp";
        		String uri = request.getRequestURI();
        		String url = PathFinder.getPath(uri, target);
        		request.getRequestDispatcher(url+"?kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);	
    		}
    		else if(!Checker.isStringNullOrEmpty(fwdto)&&fwdto.equalsIgnoreCase("overdue_ami")) {
    			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/spmi_overview/notifikasi_overdue_ami.jsp";
        		String uri = request.getRequestURI();
        		String url = PathFinder.getPath(uri, target);
        		request.getRequestDispatcher(url+"?kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);	
    		}
    		else if(!Checker.isStringNullOrEmpty(fwdto)&&fwdto.equalsIgnoreCase("mon_no_kendal")) {
    			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/spmi_overview/notifikasi_mon_no_kendal.jsp";
        		String uri = request.getRequestURI();
        		String url = PathFinder.getPath(uri, target);
        		request.getRequestDispatcher(url+"?kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);	
    		}
    		else if(!Checker.isStringNullOrEmpty(fwdto)&&fwdto.equalsIgnoreCase("mon_no_lanjut")) {
    			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/spmi_overview/notifikasi_mon_no_kelanjutan.jsp";
        		String uri = request.getRequestURI();
        		String url = PathFinder.getPath(uri, target);
        		request.getRequestDispatcher(url+"?kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);	
    		}
    		else if(!Checker.isStringNullOrEmpty(fwdto)&&fwdto.equalsIgnoreCase("never_mon")) {
    			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/spmi_overview/notifikasi_never_mon.jsp";
        		String uri = request.getRequestURI();
        		String url = PathFinder.getPath(uri, target);
        		request.getRequestDispatcher(url+"?kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);	
    		}
    		else if(!Checker.isStringNullOrEmpty(fwdto)&&fwdto.equalsIgnoreCase("never_ami")) {
    			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/spmi_overview/notifikasi_never_ami.jsp";
        		String uri = request.getRequestURI();
        		String url = PathFinder.getPath(uri, target);
        		request.getRequestDispatcher(url+"?kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);	
    		}
    		else if(!Checker.isStringNullOrEmpty(fwdto)&&fwdto.equalsIgnoreCase("pelanggaran")) {
    			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/spmi_overview/notifikasi_pelanggaran_isi.jsp";
        		String uri = request.getRequestURI();
        		String url = PathFinder.getPath(uri, target);
        		request.getRequestDispatcher(url+"?kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);	
    		}
    		else if(!Checker.isStringNullOrEmpty(fwdto)&&fwdto.equalsIgnoreCase("dokumen")) {
    			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/spmi_overview/status_doc_mutu.jsp";
        		String uri = request.getRequestURI();
        		String url = PathFinder.getPath(uri, target);
        		request.getRequestDispatcher(url+"?kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);	
    		}
    		
    		//}
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

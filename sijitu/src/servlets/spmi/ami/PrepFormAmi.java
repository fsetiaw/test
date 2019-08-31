package servlets.spmi.ami;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.ListIterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.StringTokenizer;

import beans.dbase.spmi.riwayat.ami.SearchAmi;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class PrepFormAmi
 */
@WebServlet("/PrepFormAmi")
public class PrepFormAmi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepFormAmi() {
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
			//System.out.println("okkaaayyy");
			String dari_update_susunan_question = (String)request.getParameter("dari_update_susunan_question");
			String kdpst_nmpst_kmp = (String)request.getParameter("kdpst_nmpst_kmp");
			kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
			StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
			String kdpst = st.nextToken();
			String nmpst = st.nextToken();
			String kdkmp = st.nextToken();
			//System.out.println("kdpst="+kdpst);
			Vector v=null;
			String id_master_std = null;
			String status = (String)request.getParameter("status");
			String at_page = (String)request.getParameter("at_page");
			//System.out.println("status="+status);
			String id_ami = "";
			if(true) {
				if(dari_update_susunan_question==null) {
					//System.out.println("masuk ke 1");
				//if(false) {	
					/*
					 * dipindah kje PrepAmi.java
					 */
					//DARI FORM
					//id_ami = (String)request.getParameter("id_ami");
					//String kode_activity = (String)request.getParameter("kode_activity");
					//String tgl_plan = (String)request.getParameter("tgl_plan");
					//String ketua_tim = (String)request.getParameter("ketua_tim");
					//String anggota_tim = (String)request.getParameter("anggota_tim");
					//String id_cakupan_std = (String)request.getParameter("id_cakupan_std");
					//String ket_cakupan_std = (String)request.getParameter("ket_cakupan_std");
					//String tgl_ril = (String)request.getParameter("tgl_ril");
					//String tgl_ril_done = (String)request.getParameter("tgl_ril_done");
					id_master_std = (String)request.getParameter("id_master_std");
					String ket_master_std = (String)request.getParameter("ket_master_std");
					//System.out.println("id_master_std=="+id_master_std);
					//session.setAttribute("id_ami", id_ami);
					//session.setAttribute("kode_activity", kode_activity);
					//session.setAttribute("tgl_plan", tgl_plan);
					//session.setAttribute("ketua_tim", ketua_tim);
					//session.setAttribute("anggota_tim", anggota_tim);
					//session.setAttribute("id_cakupan_std", id_cakupan_std);
					//session.setAttribute("ket_cakupan_std", ket_cakupan_std);
					//session.setAttribute("tgl_ril", tgl_ril);
					//session.setAttribute("tgl_ril_done", tgl_ril_done);
					session.setAttribute("id_master_std", id_master_std);
					session.setAttribute("ket_master_std", ket_master_std);
					
				}
				else {
					//System.out.println("masuk ke 2");
					id_ami = (String)session.getAttribute("id_ami");
					String kode_activity = (String)session.getAttribute("kode_activity");
					String tgl_plan = (String)session.getAttribute("tgl_plan");
					String ketua_tim = (String)session.getAttribute("ketua_tim");
					String anggota_tim = (String)session.getAttribute("anggota_tim");
					String id_cakupan_std = (String)session.getAttribute("id_cakupan_std");
					String ket_cakupan_std = (String)session.getAttribute("ket_cakupan_std");
					String tgl_ril = (String)session.getAttribute("tgl_ril");
					String tgl_ril_done = (String)session.getAttribute("tgl_ril_done");
					id_master_std = (String)session.getAttribute("id_master_std");
					String ket_master_std = (String)session.getAttribute("ket_master_std");
					//System.out.println("id_master_std=="+id_master_std);
					
				}
				if(status!=null&& status.equalsIgnoreCase("belum")) {
					SearchAmi sa = new SearchAmi();
					v = sa.previewAmiQandA(Integer.parseInt(id_master_std),true,kdpst);
					session.removeAttribute("v_QA");
					session.setAttribute("v_QA", v);
					
					if(dari_update_susunan_question!=null) {
						String target = Constants.getRootWeb()+"/InnerFrame/Spmi/Ami/form_penilaian_ami.jsp";
						String uri = request.getRequestURI();
						String url = PathFinder.getPath(uri, target);
						//System.out.println("go to "+url);
						request.getRequestDispatcher(url+"?kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);	
					}
				}
				else if(status!=null&& status.equalsIgnoreCase("sedang")) {
					//System.out.println("sedang berjalan");
					//System.out.println("id id_master_std = "+id_master_std);
					id_ami = (String)session.getAttribute("id_ami");
					SearchAmi sa = new SearchAmi();
					v = sa.getHasilAmiQandA(Integer.parseInt(id_ami),Integer.parseInt(id_master_std));
					session.removeAttribute("v_QA");
					session.setAttribute("v_QA", v);
					String dari_pelaksanaan_ami = request.getParameter("dari_pelaksanaan_ami");
					if(dari_pelaksanaan_ami!=null) {
						//System.out.println("id dari_pelaksanaan_ami = "+dari_pelaksanaan_ami);
						//System.out.println("id dari_pelaksanaan_ami = "+dari_pelaksanaan_ami);
						String target = Constants.getRootWeb()+"/InnerFrame/Spmi/Ami/form_pelaksanaan_ami.jsp";
						String uri = request.getRequestURI();
						String url = PathFinder.getPath(uri, target);
						request.getRequestDispatcher(url+"?at_page="+at_page+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);
					}
				}
				else if(status!=null&& status.equalsIgnoreCase("selesai")) {
					//System.out.println("sedang berjalan");
					SearchAmi sa = new SearchAmi();
					id_ami = (String)session.getAttribute("id_ami");
					v = sa.getHasilAmiQandA(Integer.parseInt(id_ami),Integer.parseInt(id_master_std));
					session.removeAttribute("v_QA");
					session.setAttribute("v_QA", v);
					String dari_pelaksanaan_ami = request.getParameter("dari_pelaksanaan_ami");
					if(dari_pelaksanaan_ami!=null) {
						String target = Constants.getRootWeb()+"/InnerFrame/Spmi/Ami/form_hasil_ami.jsp";
						String uri = request.getRequestURI();
						String url = PathFinder.getPath(uri, target);
						request.getRequestDispatcher(url+"?at_page="+at_page+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);
					}
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

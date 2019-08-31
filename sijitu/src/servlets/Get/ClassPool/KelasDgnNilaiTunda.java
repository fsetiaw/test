package servlets.Get.ClassPool;

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
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class KelasDgnNilaiTunda
 */
@WebServlet("/KelasDgnNilaiTunda")
public class KelasDgnNilaiTunda extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KelasDgnNilaiTunda() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
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
			//PrintWriter out = response.getWriter();
			session.removeAttribute("v_list_kelas");
			SearchDbClassPoll scp = new SearchDbClassPoll();
			String startingThsms = request.getParameter("starting_thsms");
			String cmd = request.getParameter("cmd");
			String sta_limit = request.getParameter("sta_limit");
			String range_limit = request.getParameter("range_limit");
			//System.out.println("sta_limit="+sta_limit);
			//System.out.println("range_limit="+range_limit);
			Vector v_scope_kdpst = isu.getScopeKdpst_vFinal(cmd);
			Vector v_list_kelas = scp.getKelasYgMasihAdaNilaiTunda_v1(startingThsms, v_scope_kdpst, Integer.parseInt(sta_limit), Integer.parseInt(range_limit));
			//System.out.println("v_list_kelas="+v_list_kelas.size());
			session.setAttribute("v_list_kelas", v_list_kelas);
			String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/Nilai/dashPenilaian_v1.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			
			
			//request.getRequestDispatcher(url).forward(request,response);
			
			request.getRequestDispatcher(url+"?group_proses=monitorNilaiTunda").forward(request,response);
		}

		
		
		

		//kode here
		//System.out.println("nilai tunda");
		
		
		
		/*
		String cmd = request.getParameter("cmd");
		String thsms_nilai = Checker.getThsmsInputNilai();
		SearchDbClassPoll sdb = new SearchDbClassPoll(isu.getNpm());
		String list_scope_obj_id = null;
		Vector v_list_scope_obj_id = isu.getScopeUpd11Jan2016ProdiOnly(cmd); //format brs 114 65201 MHS_ILMU_PEMERINTAHAN 114 C
		//karena untuk cetak absen maka di filter hanya yg ada mhsnya saja
		Vector vListKelasDgnNilaiTunda = sdb.getKelasYgMasihAdaNilaiTunda(thsms_nilai, v_list_scope_obj_id);
		//li.add(idkur+"`"+idkmk+"`"+kdpst+"`"+shift+"`"+noKlsPll+"`"+npm_pertama_input+"`"+npm_terakhir_updat+"`"+status_akhir+"`"+curr_avail_status+"`"+locked_or_editable+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+canceled+"`"+kode_kelas+"`"+kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
		
		session.setAttribute("vListKelasDgnNilaiTunda", vListKelasDgnNilaiTunda);
		///ToUnivSatyagama/WebContent/InnerFrame/Perkuliahan/Nilai/dashPenilaian.jsp
		//String target = Constants.getRootWeb()+"/InnerFrame/Monitoring/ListKelasYgAdaNilaiTunda.jsp";
		 

		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
			request.getRequestDispatcher(url+"?group_proses=monitorNilaiTunda").forward(request,response);
		}
* 
		 */
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

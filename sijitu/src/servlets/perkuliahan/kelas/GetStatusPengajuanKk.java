package servlets.perkuliahan.kelas;

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
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class GetStatusPengajuanKk
 */
@WebServlet("/GetStatusPengajuanKk")
public class GetStatusPengajuanKk extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStatusPengajuanKk() {
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
			//System.out.println("siap");
			String atKmp = request.getParameter("atKmp");
			String backTo = request.getParameter("backTo");
			//System.out.println("back sini ="+backTo);
			if(atKmp==null || Checker.isStringNullOrEmpty(atKmp)) {
				atKmp = new String(isu.getKode_kmp_dom());
			}
			//String default_kmp = 
			String scope_cmd = request.getParameter("scope_cmd");
			Vector v_scope_id_combine = null;
			Vector v_approval = null;
			String target_thsms = Checker.getThsmsBukaKelas();
			Vector vf = isu.returnScopeProdiOnlySortByKampusWithListIdobj(scope_cmd, "KELAS_PERKULIAHAN_RULES", target_thsms);//v1 list combine scope, v2 lsit approval onlu
			String list_kampus = Getter.getScopeKampus(vf);
			//System.out.println("list_kmp = "+list_kampus);
			SearchDbClassPoll sdb = new SearchDbClassPoll(isu.getNpm());
			Vector v = sdb.getAStatusPengajuanKelas(vf, target_thsms);
			//System.out.println(v.size());
			request.setAttribute("v", v);
			//request.setAttribute("v_scope_id_combine", v_scope_id_combine);
			//request.setAttribute("v_approval", v_approval);
			
			String target = Constants.getRootWeb()+"/InnerFrame/Akademik/kelas_perkuliahan/list_status_pengajuan.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);

			request.getRequestDispatcher(url+"?target_thsms="+target_thsms+"&scope_cmd="+scope_cmd+"&target_thsms="+target_thsms+"&atMenu=form&list_kampus="+list_kampus+"&atKmp="+atKmp+"&backTo="+backTo).forward(request,response);

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

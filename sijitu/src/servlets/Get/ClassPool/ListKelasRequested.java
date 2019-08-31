package servlets.Get.ClassPool;

import java.io.IOException;
import java.util.StringTokenizer;
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
import beans.tools.Converter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class ListKelasRequested
 */
@WebServlet("/ListKelasRequested")
public class ListKelasRequested extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListKelasRequested() {
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
			//System.out.println("siap bro");
			Vector v_scope_id_combine = null;
			Vector v_approval = null;
			String target_thsms = request.getParameter("target_thsms");
			String info = request.getParameter("info");
			if(info!=null && !Checker.isStringNullOrEmpty(info))  {
				StringTokenizer st = new StringTokenizer(info,"`");
		  		String id = st.nextToken();
				String kdpst = st.nextToken();
				String kmp = st.nextToken();
				String locked = st.nextToken();
				String passed = st.nextToken();
				String reject = st.nextToken();
				String list_job_approvee = st.nextToken();
				String list_id_approvee = st.nextToken();
				boolean complete = true;
				if(list_id_approvee.contains("null")) {
					complete = false;
				}
				String current_job_approvee = st.nextToken();
				String current_id_approvee = st.nextToken();
				
				SearchDbClassPoll sdb = new SearchDbClassPoll(isu.getNpm());
				
				Vector v_kls = sdb.getInfoListKelasYgDiajukan(target_thsms, kdpst, kmp);
				request.setAttribute("v_kls", v_kls);
				
				String atMenu = request.getParameter("atMenu");
				String target="";
				if(!Checker.isStringNullOrEmpty(atMenu) && atMenu.equalsIgnoreCase("ubahDosenAjar")) {
					target = Constants.getRootWeb()+"/InnerFrame/Akademik/kelas_perkuliahan/formApprovalKelasRequest_versi_ubah_dosen.jsp";
				}
				else {
					target = Constants.getRootWeb()+"/InnerFrame/Akademik/kelas_perkuliahan/formApprovalKelasRequest.jsp";
				}
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				
				request.getRequestDispatcher(url+"?target_thsms="+target_thsms+"&atMenu="+atMenu).forward(request,response);
			}
			//String kdpst = request.getParameter("kdpst");
			//String kmp = request.getParameter("kmp");
			//String locked = request.getParameter("locked");
			//System.out.println("ss="+target_thsms+" "+info);
			/*
			Vector vf = isu.returnScopeProdiOnlySortByKampusWithListIdobj("reqBukaKelas", "KELAS_PERKULIAHAN_RULES", target_thsms);
			
			SearchDbClassPoll sdb = new SearchDbClassPoll(isu.getNpm());
			Vector v = sdb.getDistinctClassPerKdpst_v1(String thsms, String kdpst, String tknScopeKampus);
			request.setAttribute("v", v);
			//request.setAttribute("v_scope_id_combine", v_scope_id_combine);
			//request.setAttribute("v_approval", v_approval);
			
			String target = Constants.getRootWeb()+"/InnerFrame/Akademik/kelas_perkuliahan/list_status_pengajuan.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
*/
			//request.getRequestDispatcher(url+"?target_thsms="+target_thsms+"&atMenu=form").forward(request,response);
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

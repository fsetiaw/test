package servlets.pddikti;

import java.io.IOException;
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
import beans.dbase.trnlm.*;
import beans.dbase.mhs.kurikulum.SearchDbInfoKurikulum;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class SetKelasDanDosen
 */
@WebServlet("/SetKelasDanDosen")
public class SetKelasDanDosen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetKelasDanDosen() {
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
			//System.out.println("gogoooon");
			String target_thsms = request.getParameter("thsms");
			String dari = request.getParameter("dari");
			session.setAttribute("target_thsms", target_thsms);
			Vector v_dos = Getter.getListDosen_v1(true);
			session.setAttribute("v_dos", v_dos);
			
			SearchDbTrnlm sdt = new SearchDbTrnlm();
			Vector v = sdt.getListKelasKuliahForFeeder_v1(target_thsms);
			session.setAttribute("v", v);
			
			if(Checker.isStringNullOrEmpty(dari)||!dari.equalsIgnoreCase("index")) {
				String target = Constants.getRootWeb()+"/InnerFrame/Pddikti/Dosen/setting_dosen_v2.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);

				if(isu==null) {
					response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
				}
				else {
					request.getRequestDispatcher(url).forward(request,response);
				}
	
			}
			/*
						*/
		//PrintWriter out = response.getWriter();
		//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
		//String uri = request.getRequestURI();
		//String url = PathFinder.getPath(uri, target);
		//request.getRequestDispatcher(url).forward(request,response);
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

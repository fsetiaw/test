package servlets.Get;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.overview.GetSebaranTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class NoKrs
 */
@WebServlet("/NoKrs")
public class NoKrs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoKrs() {
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
			//System.out.println("no krs here");
			String target_thsms = request.getParameter("target_thsms");
			if(target_thsms==null || Checker.isStringNullOrEmpty(target_thsms)) {
				target_thsms=Checker.getThsmsNow();
			}
			//String thsms_now = Checker.getThsmsNow();
			GetSebaranTrlsm gt = new GetSebaranTrlsm();
			Vector v_scope = Getter.returnListProdiOnlySortByKampusWithListIdobj();
    		Vector v_info_daftar_ulang = gt.getSummaryNoKrs(v_scope, target_thsms);
    		//if(v_info_daftar_ulang!=null) {
    			//System.out.println("v_info_daftar_ulang="+v_info_daftar_ulang);	
    		//}
    		//else {
    			//System.out.println("nol");
    		//}
    		
    		request.setAttribute("v_noapr",v_info_daftar_ulang);
			String target = Constants.getRootWeb()+"/InnerFrame/Overview/krs/unapproved.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);

			request.getRequestDispatcher(url+"?target_thsms="+target_thsms).forward(request,response);

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

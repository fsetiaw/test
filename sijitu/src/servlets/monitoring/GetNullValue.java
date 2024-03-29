package servlets.monitoring;

import java.io.IOException;
import java.util.ListIterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.notification.SearchDbMainNotification;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class GetNullValue
 */
@WebServlet("/GetNullValue")
public class GetNullValue extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetNullValue() {
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
			//System.out.println("yes");
			String cmd = request.getParameter("cmd");
			String thsms_reg = Checker.getThsmsHeregistrasi();
			SearchDbMainNotification sdmn = new SearchDbMainNotification(isu.getNpm());
			
			Vector v_scope_id = isu.returnScopeProdiOnlySortByKampusWithListIdobj(cmd);
			Vector vf = sdmn.cekTableRulesForNullApproveeId_complete(v_scope_id,thsms_reg);
			request.setAttribute("v", vf);

			
			String target = Constants.getRootWeb()+"/InnerFrame/Monitoring/NullValue.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);

			request.getRequestDispatcher(url+"?thsms_target="+thsms_reg).forward(request,response);

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

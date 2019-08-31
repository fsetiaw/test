package servlets.spmi.calendar;

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

import beans.dbase.spmi.ToolSpmi;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class RefreshCalendar
 */
@WebServlet("/RefreshCalendar")
public class RefreshCalendar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefreshCalendar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
			String from = request.getParameter("from");
			//System.out.println("from="+from);
			String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
			ToolSpmi.refreshKalenderMutu();
			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/calendar/calendar_mutu.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			if(Checker.isStringNullOrEmpty(from)) {
				request.getRequestDispatcher(url+"?kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);	
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

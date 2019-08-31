package servlets.Get;

import java.io.IOException;
import beans.dbase.SearchDb;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.dbase.keu.*;
import beans.dbase.notification.SearchDbMainNotification;
import beans.dbase.status_kehadiran_dosen.SearchDbkehadiranDosen;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;
import beans.tools.Tool;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.owasp.esapi.ESAPI;

/**
 * Servlet implementation class Notifications
 */
@WebServlet("/Notifications")
public class Notifications extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Notifications() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("notifications");
		//System.out.println("start time = "+AskSystem.getCurrentTimestamp());
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		
		//System.out.println("starting");
		if(isu==null) {
			//System.out.println("isnull");
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
			
		}
		else {
			String thsms_now1 = Checker.getThsmsNow();
			String thsms_1 = Tool.returnPrevThsmsGivenTpAntara(thsms_now1);
			String thsms_buka_kelas = Checker.getThsmsBukaKelas();
			String thsms_pelaporan = "20172"; 
			String starting_thsms_pengecekan = Tool.returnPrevThsmsGivenTpAntara(thsms_pelaporan);
			session.setAttribute("thsms_now1", thsms_now1);
			session.setAttribute("thsms_1", thsms_1);
			session.setAttribute("thsms_buka_kelas", thsms_buka_kelas);
			session.setAttribute("thsms_pelaporan", thsms_pelaporan);
			session.setAttribute("starting_thsms_pengecekan", starting_thsms_pengecekan);
			
			
			String target = Constants.getRootWeb()+"/InnerFrame/home.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
		
			if(isu==null) {
				response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
			}
			else {
				
				//request.getRequestDispatcher(url).forward(request,response);
				request.getRequestDispatcher("goto.tampleteRouteBasedOnScopeKdpst?atMenu=spmi&fwdTo=InnerFrame/Spmi/home_spmi.jsp").forward(request,response);
			}
			
		}
		
	}	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}

package servlets.prep;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;

import beans.dbase.chitchat.UpdateDashboard;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class ContactsDashboard
 */
@WebServlet("/ContactsDashboard")
public class ContactsDashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactsDashboard() {
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
			String stu_npm = null;
			UpdateDashboard udb = new UpdateDashboard(stu_npm=isu.getNpm());
			String stu_kdpst = Checker.getKdpst(stu_npm);
			String stu_kmp = Getter.getDomisiliKampus(stu_npm);
			//System.out.println("stu_kdpst=="+stu_kdpst);
			//System.out.println("stu_kmp=="+stu_kmp);
			udb.autoRegisterUrNpmhs();
			JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/citcat/role/avail_for_stu/stu_npm/"+stu_npm+"/stu_kdpst/"+stu_kdpst+"/stu_kmp/"+stu_kmp);
			request.setAttribute("jsoa", jsoa);
			
			String target = Constants.getRootWeb()+"/InnerFrame/ContactUs/ContactHome.jsp";
		    String uri = request.getRequestURI();
		    String url = PathFinder.getPath(uri, target);

		    request.getRequestDispatcher(url).forward(request,response);
		      

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

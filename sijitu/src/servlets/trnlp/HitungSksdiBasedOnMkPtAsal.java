package servlets.trnlp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.trnlp.SearchDbTrnlp;
import beans.dbase.trnlp.UpdateDbTrnlp;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class HitungSksdiBasedOnMkPtAsal
 */
@WebServlet("/HitungSksdiBasedOnMkPtAsal")
public class HitungSksdiBasedOnMkPtAsal extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HitungSksdiBasedOnMkPtAsal() {
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
			//System.out.println("lanjut");
			int updated = 0;
			Vector v = null;
			//String target_smawl = request.getParameter("smawl");
			//if(Checker.isStringNullOrEmpty(target_smawl)) {
			SearchDbTrnlp sdt = new SearchDbTrnlp();
			String tkn_npm = sdt.getListMhsPindahan();
			if(!Checker.isStringNullOrEmpty(tkn_npm)) {
				UpdateDbTrnlp udp = new UpdateDbTrnlp();
				updated = updated+udp.hitungDanUpdateSksdiBerdasarkanMkAsal(tkn_npm);
				v = new Vector();
				v.add(0,""+updated);
				v.add(0,"TOTAL DATA UPDATED");
				v.add(0,"600px");
				v.add(0,"95");
				v.add(0,"center");
				v.add(0,"String");
				
			}	
			session.setAttribute("v", v);
			//}
			
			
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

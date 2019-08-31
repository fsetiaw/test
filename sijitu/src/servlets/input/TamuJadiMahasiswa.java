package servlets.input;

import java.io.IOException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.mhs.SearchDbInfoMhs;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class TamuJadiMahasiswa
 */
@WebServlet("/TamuJadiMahasiswa")
public class TamuJadiMahasiswa extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TamuJadiMahasiswa() {
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

		//kode here
		//System.out.println("turn");
		String npmhs = request.getParameter("npm");
		SearchDbInfoMhs sdim = new SearchDbInfoMhs(isu.getNpm());
		Vector v = sdim.getDataProfileCivitasAndExtMhs(npmhs);
		//ListIterator li = v.listIterator();
		//String tkn_civ = (String)li.next();
		//String tkn_eciv = (String)li.next();
		session.setAttribute("v_info_civ", v);
		String target = Constants.getRootWeb()+"/InnerFrame/PMB/tamu_jd_mhs.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		/*
		 * ASSUMED PART1 & 2  BENAR
		 */
			request.getRequestDispatcher(url+"?valid_pt1=true&valid_pt2=true").forward(request,response);
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

package servlets.maintenance;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.mhs.SearchDbInfoMhs;
import beans.dbase.mhs.UpdateDbInfoMhs;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateSettinganGroup1
 */
@WebServlet("/UpdateSettinganGroup1")
public class UpdateSettinganGroup1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateSettinganGroup1() {
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
		//PrintWriter out = response.getWriter();
			//System.out.println("group1");
			String thsms_btstu = request.getParameter("thsms_btstu");
			String thsms_force_out = request.getParameter("thsms_out");
			String based_thsms = request.getParameter("based_thsms");
			SearchDbInfoMhs sdm = new SearchDbInfoMhs();
			int updated = 0;
			UpdateDbInfoMhs udim = new UpdateDbInfoMhs();
			if(!Checker.isStringNullOrEmpty(based_thsms)) {
				updated = udim.setBasedThsmsData(based_thsms);
			}
			if(!Checker.isStringNullOrEmpty(thsms_btstu)) {
				updated = udim.setBtstuMhsAktif(thsms_btstu);
			}
			if(!Checker.isStringNullOrEmpty(thsms_force_out)) {
				updated = udim.forcedKeluarMhsLewatBtstu(thsms_force_out);
			}
			//String tkn_npm = sdm.getMhsAktifVersiPddikti(thsms);
			
			//System.out.println("done");
			String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
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

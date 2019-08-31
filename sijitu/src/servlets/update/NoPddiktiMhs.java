package servlets.update;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.mhs.UpdateDbInfoMhs;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class NoPddiktiMhs
 */
@WebServlet("/NoPddiktiMhs")
public class NoPddiktiMhs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoPddiktiMhs() {
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
			//System.out.println("kesisni");
			String[]nimhs = request.getParameterValues("nim");
			String[]npmhs = request.getParameterValues("npm_val");
			String target_thsms = request.getParameter("target_thsms");
			String at_kmp = request.getParameter("at_kmp");
			String list_conflict_nim_jika_ada = "null";
			//System.out.println(npmhs.length+" == "+nimhs.length);
			if(npmhs.length == nimhs.length) {
				UpdateDbInfoMhs udim = new UpdateDbInfoMhs(isu.getNpm());
				list_conflict_nim_jika_ada = udim.updateNpmPddikti(npmhs, nimhs);
			}
			//System.out.println("done");
			
			
			request.getRequestDispatcher("get.whoRegister?target_thsms="+target_thsms+"&at_kmp="+at_kmp+"&list_nim_bentrok="+list_conflict_nim_jika_ada).forward(request,response);

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

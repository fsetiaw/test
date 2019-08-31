package servlets.trnlp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.trnlp.UpdateDbTrnlp;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class CopyMkPenyetaraan
 */
@WebServlet("/CopyMkPenyetaraan")
public class CopyMkPenyetaraan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CopyMkPenyetaraan() {
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
			String list_npm_pindahan_dlm_1_angkatam = request.getParameter("list_npm_pindahan_dlm_1_angkatam");
			//System.out.println("list_npm_pindahan_dlm_1_angkatam="+list_npm_pindahan_dlm_1_angkatam);
			if(list_npm_pindahan_dlm_1_angkatam!=null && !Checker.isStringNullOrEmpty(list_npm_pindahan_dlm_1_angkatam)) {
				UpdateDbTrnlp udt = new UpdateDbTrnlp();
				udt.copyStpidDariNpmPertamaOnTheList(list_npm_pindahan_dlm_1_angkatam);
				
					
			}
			
			String target = Constants.getRootWeb()+"/InnerFrame/Feeder/index_importer.jsp";
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

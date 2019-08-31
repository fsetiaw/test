package servlets.feeder.importer;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.dosen.SearchDbDsn;
import beans.dbase.feeder.importer.SyncDataDosen;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class SyncDataDosen
 */
@WebServlet("/ServletSyncDataDosen")
public class ServletSyncDataDosen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletSyncDataDosen() {
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
			//System.out.println("uhuy");
			
			SearchDbDsn sdd = new SearchDbDsn();
			Vector v_list_dsn = sdd.getListDosenHidup();
			if(v_list_dsn!=null && v_list_dsn.size()>0) {
				SyncDataDosen sdn = new SyncDataDosen();
				Vector v_err = sdn.syncDataDosen(v_list_dsn);
				session.setAttribute("v_err", v_err);
			}
			String target = Constants.getRootWeb()+"/InnerFrame/Feeder/done.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			
			if(isu==null) {
				response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
			}
			else {
				request.getRequestDispatcher(url).forward(request,response);
			}
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

package servlets.mhs.pmb.maintenance;

import java.io.IOException;
import java.util.ListIterator;
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
 * Servlet implementation class CheckInputMhsDobel
 */
@WebServlet("/CheckInputMhsDobel")
public class CheckInputMhsDobel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckInputMhsDobel() {
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
			//System.out.println("okay");
			String smawl = request.getParameter("smawl");
			SearchDbInfoMhs sdb = new SearchDbInfoMhs();
			Vector v_scope_id = isu.returnScopeProdiOnlySortByKampusWithListIdobj("s");
			boolean prodi_only = true;
			String tkn_col_name = "TGLHRMSMHS,NMMHSMSMHS,NPMHSMSMHS";
			String tkn_col_type = "string`string";
			Vector v = sdb.cekMhsInputMultipleTimesBasedTglhr(smawl, v_scope_id, tkn_col_name, tkn_col_type,prodi_only);
			ListIterator li = v.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				//System.out.println(brs);
			}
			session.setAttribute("v_rs", v);
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

package servlets.Get;

import java.io.IOException;
import java.util.ListIterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.daftarUlang.SearchDbInfoDaftarUlangTable;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.AddHocFunction;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UsrPwd
 */
@WebServlet("/UsrPwd")
public class UsrPwd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsrPwd() {
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
			String cmd_scope = request.getParameter("cmd_scope");
			Vector v_scope_id = isu.returnScopeProdiOnlySortByKampusWithListIdobj(cmd_scope);
			
			String thsms_now = Checker.getThsmsNow();//jgn heregistrasi krn monya thsms reg-1
			Vector vf = null;
			if(v_scope_id!=null && v_scope_id.size()>0) {
				SearchDbInfoDaftarUlangTable sdu = new SearchDbInfoDaftarUlangTable(isu.getNpm());
				vf = sdu.getListMhsYgSdhMengajukanPengajuanDaftarUlang(thsms_now, v_scope_id);
				if(vf!=null && vf.size()>0) {
					vf = AddHocFunction.addInfoUsrPwd(vf);
				}
			}
			request.setAttribute("v_list",vf);
			String target = Constants.getRootWeb()+"/InnerFrame/listUsrPwd.jsp";
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

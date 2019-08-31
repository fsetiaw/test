package servlets.Overview;

import java.io.IOException;
import java.util.ListIterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.pengajuan.SearchDbPengajuan;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class WhoRequest
 */
@WebServlet("/WhoRequest")
public class WhoRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WhoRequest() {
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
			//String atKmp = request.getParameter("atKmp");
			String target_thsms = request.getParameter("target_thsms");
			String target_kampus = request.getParameter("target_kampus");
			if(target_thsms==null || Checker.isStringNullOrEmpty(target_thsms)) {
				target_thsms = Checker.getThsmsHeregistrasi();
			}
			String fullname_table_rules = request.getParameter("fullname_table_rules");
			Vector v_scope_id = isu.returnScopeProdiOnlySortByKampusWithListIdobj("ov");
			//Vector v_scope_id = isu.returnScopeProdiOnlySortByKampusWithListIdobj("cuti");
			Vector v = null;
			if(v_scope_id==null || v_scope_id.size()==0) {
				v = new Vector();
			}
			else {
				SearchDbPengajuan sdp = new SearchDbPengajuan(isu.getNpm());
				v = sdp.getListNpmAndStatusPengajuan_v1(target_thsms, fullname_table_rules, v_scope_id,target_kampus);
				
				//if(atKmp==null || Checker.isStringNullOrEmpty(atKmp)) {
				//	atKmp = sdp.get1stAtKmpYgAdaIsinya(target_thsms, fullname_table_rules, v_scope_id);
				//}	
			}
			session.setAttribute("vf", v);
			//ListIterator li = v.listIterator();
			//while(li.hasNext()) {
			//	String brs = (String)li.next();
				//System.out.println(brs);
			//	Vector v1 = (Vector)li.next();
			//	ListIterator li1 = v1.listIterator();
				//while(li1.hasNext()) {
					//String brs1 = (String)li1.next();
					//System.out.println(brs1);
				//}
			//}
			//System.out.println("at_kmp="+at_kmp);
			String target = Constants.getRootWeb()+"/InnerFrame/Overview/listMhsReq.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			//if(atKmp==null || Checker.isStringNullOrEmpty(atKmp) ) {
				request.getRequestDispatcher(url+"?target_thsms="+target_thsms+"&fullname_table_rules="+fullname_table_rules).forward(request,response);	
			//}
			//else {
				//request.getRequestDispatcher("go.getWhoRequest?target_thsms="+target_thsms+"&fullname_table_rules="+fullname_table_rules+"&atKmp="+atKmp+"&no_mhs=false").forward(request,response);
			//}
			

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

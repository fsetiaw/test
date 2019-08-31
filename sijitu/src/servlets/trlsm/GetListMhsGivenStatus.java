package servlets.trlsm;

import java.io.IOException;
import java.util.ListIterator;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.classPoll.SearchDbClassPoll;
import beans.dbase.dosen.SearchDbDsn;
import beans.dbase.mhs.btstu.SearchDbInfoMhsBtstu;
import beans.dbase.mhs.kurikulum.SearchDbInfoKurikulum;
import beans.dbase.overview.maintenance.MaintenanceOverview;
import beans.dbase.trlsm.SearchDbTrlsm;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;
import beans.tools.filter.FilterKampus;

/**
 * Servlet implementation class GetListMhsGivenStatus
 */
@WebServlet("/GetListMhsGivenStatus")
public class GetListMhsGivenStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetListMhsGivenStatus() {
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
			System.out.println("okay");
			String thsms = request.getParameter("thsms");
			String stmhs = request.getParameter("stmhs");
			System.out.println("thsms="+thsms);
			System.out.println("stmhs="+stmhs);
			Vector v_scope = isu.returnScopeProdiOnlySortByKampusWithListIdobj_v1("s", true, thsms);
			//Vector v_clone = (Vector)v_scope.clone();
			MaintenanceOverview mo = new MaintenanceOverview();
			Vector v_list = null;
			if(v_scope!=null && v_scope.size()>0) {
				//mo.maintenaceCountDataMhsGivenStmhs((Vector)v_scope.clone(), stmhs, thsms);
				//System.out.println("done");
				//mo.maintenaceCountDataMhsGivenStmhsInProgress((Vector)v_scope.clone(), stmhs, thsms);
				SearchDbTrlsm sdt = new SearchDbTrlsm();
				v_list = sdt.getListMhsGivenStmhs(v_scope, thsms, stmhs);
				
				if(v_list!=null && v_list.size()>0) {
					//kdpst+"`"+npmhs+"`"+nmmhs+"`"+nimhs+"`"+smawl+"`"+stpid+"`"+angel+"`"+kdjen

					System.out.println("size="+v_list.size());
					v_list.add(0,"PRODI`NPM`NAMA");
					v_list.add(0,"600px");
					v_list.add(0,"5`20`70");
					v_list.add(0,"center`center`left");
					v_list.add(0,"String`String`String");
					System.out.println("size2="+v_list.size());
				}
				else {
					v_list = null;
				}
					
			}
			session.setAttribute("v", v_list);
			System.out.println("done");
			
			//PrintWriter out = response.getWriter();
			//String target = Constants.getRootWeb()+"/InnerFrame/sql/ResultSet_v4.jsp";
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

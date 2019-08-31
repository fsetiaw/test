package servlets.mhs.profile;

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
import beans.tools.Checker;
import beans.tools.PathFinder;
import beans.tools.Tool;
import beans.tools.filter.FilterKampus;

/**
 * Servlet implementation class GetListMhsYgDataProfilBelumLengkap
 */
@WebServlet("/GetListMhsYgDataProfilBelumLengkap")
public class GetListMhsYgDataProfilBelumLengkap extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetListMhsYgDataProfilBelumLengkap() {
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
		//System.out.println("imni");;
		
		String cmd = request.getParameter("cmd");
		String from = request.getParameter("from");
		String limit_per_page = request.getParameter("limit_per_page");
		String at_hal = request.getParameter("at_hal");
		String search_range = request.getParameter("search_range");
		String starting_smawl = request.getParameter("starting_smawl");
		//starting_smawl = Tool.returnPrevThsmsGivenTpAntara(starting_smawl);
		String starting_page_shown = request.getParameter("starting_page_shown");
		String ending_page_shown = request.getParameter("ending_page_shown");
		
		//System.out.println("at_hal0="+at_hal);
		Vector v_scope_id = isu.returnScopeProdiOnlySortByKampusWithListIdobj(cmd);
		//ListIterator li = v_scope_id.listIterator();
		//while(li.hasNext()) {
		//	String abris = (String)li.next();
		//	//System.out.println("abris="+abris);
		//}
		SearchDbInfoMhs sdm = new SearchDbInfoMhs(isu.getNpm());
		//Vector v_scope_id, int limit_per_page, int page, int search_range, String starting_smawl) {
		//Vector v_list_mhs = sdm.getMhsDgnDataProfilIncomplete(v_scope_id,Integer.parseInt(limit_per_page),Integer.parseInt(init_hal),Integer.parseInt(search_range),starting_smawl);
		Vector v_list_mhs = null;
		//if(!Checker.isStringNullOrEmpty(at_hal)&&(at_hal.equalsIgnoreCase("0")||at_hal.equalsIgnoreCase("1"))) {
			//panggilan pertama dari home
		v_list_mhs = sdm.getMhsDgnDataProfilIncomplete_v1(v_scope_id,Integer.parseInt(limit_per_page),Integer.parseInt(at_hal),Integer.parseInt(search_range),starting_smawl,Integer.parseInt(starting_page_shown),Integer.parseInt(ending_page_shown));
		
		//}
		//else {
		//	v_list_mhs = sdm.getMhsDgnDataProfilIncomplete_v1(v_scope_id,Integer.parseInt(limit_per_page),Integer.parseInt(at_hal),Integer.parseInt(search_range),starting_smawl);
		//}
		
		//if(v_list_mhs==null) {
		//	//System.out.println("v_list_mhs is null");
		//}
		//else {
		//	//System.out.println("v_list_mhs size is ="+v_list_mhs.size());
		//}
		//System.out.println("v_list_mhs="+v_list_mhs.size());
		//request.setAttribute("v_list_mhs", v_list_mhs);
		session.setAttribute("v_list_mhs", v_list_mhs);
		
		String target = Constants.getRootWeb()+"/InnerFrame/Monitoring/ListMhsProfilIncomplete.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		//System.out.println("url="+url);

		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
			/*
			 * peralihan penggunaan ajax
			 */
			if(Checker.isStringNullOrEmpty(from)) {
				request.getRequestDispatcher(url).forward(request,response);	
			}
			
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

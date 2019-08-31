package servlets.mhs.pindahan.krs;

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

/**
 * Servlet implementation class GetListMhsYgBelumAdaPenyetaraan
 */
@WebServlet("/GetListMhsYgBelumAdaPenyetaraan_part2")
public class GetListMhsYgBelumAdaPenyetaraan_part2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetListMhsYgBelumAdaPenyetaraan_part2() {
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
		//System.out.println("monngo");;
		String cmd = request.getParameter("cmd");
		String starting_smawl = request.getParameter("starting_smawl");
		
		/*
		String from = request.getParameter("from");
		String limit_per_page = request.getParameter("limit_per_page");
		String at_hal = request.getParameter("at_hal");
		String search_range = request.getParameter("search_range");
		
		String starting_page_shown = request.getParameter("starting_page_shown");
		String ending_page_shown = request.getParameter("ending_page_shown");
		*/
		//System.out.println("starting_smawl="+starting_smawl);;
		
		
		
		Vector v_scope_id = isu.returnScopeProdiOnlySortByKampusWithListIdobj(cmd); 
		
		
		SearchDbInfoMhs sdm = new SearchDbInfoMhs(isu.getNpm());
		//getMhsDgnDataProfilIncomplete
		Vector v_list_mhs = null;
		v_list_mhs = sdm.getMhsPindahanDataIncomplete_part2(v_scope_id,starting_smawl);
		
		if(v_list_mhs!=null) {
			Vector vtmp = sdm.getMhsPindahanDataIncomplete_v1(v_scope_id);
			if(vtmp!=null) {
				v_list_mhs.addAll(vtmp);	
			}
			
			v_list_mhs.add(0,"PRODI`NPM`NAMA`SMAWL");
			v_list_mhs.add(0,"800px");
			v_list_mhs.add(0,"10`25`50`15");
			v_list_mhs.add(0,"center`center`left`center");
			v_list_mhs.add(0,"String`String`String`String");
		}
		else {
			/*
			 * kalo ngga salah ngga bisa null yg ada size=0,
			 * jadi ini jaga2 aja
			 */
			v_list_mhs = sdm.getMhsPindahanDataIncomplete_v1(v_scope_id);
			if(v_list_mhs!=null) {
				v_list_mhs.add(0,"PRODI`NPM`NAMA`SMAWL");
				v_list_mhs.add(0,"800px");
				v_list_mhs.add(0,"10`25`50`15");
				v_list_mhs.add(0,"center`center`left`center");
				v_list_mhs.add(0,"String`String`String`String");	
			}
			
		
		}
		session.setAttribute("v", v_list_mhs);
		
		//String list_mhs = sdm.belumAdakrsPenyetaraan(v_scope_id);
		//request.setAttribute("list_mhs", list_mhs);
		/*
		String target = Constants.getRootWeb()+"/InnerFrame/Monitoring/ListMhsPindahanBelumAdaPenyetaraan.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);

		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		*/
		
			/*
			 * peralihan penggunaan ajax
			
			if(Checker.isStringNullOrEmpty(from)) {
				request.getRequestDispatcher(url).forward(request,response);	
			}
		}
		 */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

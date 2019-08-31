package servlets.trlsm;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.mhs.SearchDbInfoMhs;
import beans.dbase.pengajuan.SearchDbPengajuan;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class CekNpmAdaDiTopikPengajuan
 */
@WebServlet("/CekNpmAdaDiTopikPengajuan")
public class CekNpmAdaDiTopikPengajuan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CekNpmAdaDiTopikPengajuan() {
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
		String limit_per_page = request.getParameter("limit_per_page");
		String init_hal = request.getParameter("init_hal");
		String search_range = request.getParameter("search_range");
		String starting_smawl = request.getParameter("starting_smawl");
		Vector v_scope_id = isu.returnScopeProdiOnlySortByKampusWithListIdobj(cmd); 
		SearchDbPengajuan sdp = new SearchDbPengajuan(isu.getNpm());
		//getMhsDgnDataProfilIncomplete
		Vector v_list_mhs = sdp.getNpmYgAdaDiTrlsmTapiTidakAdaDiTopikPengajuan(v_scope_id,Integer.parseInt(limit_per_page),Integer.parseInt(init_hal),Integer.parseInt(search_range),starting_smawl);
		request.setAttribute("v_list_mhs", v_list_mhs);
		
		
		//String list_mhs = sdm.belumAdakrsPenyetaraan(v_scope_id);
		//request.setAttribute("list_mhs", list_mhs);
		
		String target = Constants.getRootWeb()+"/InnerFrame/Monitoring/ListNpmTidakAdaDiPengajuan.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);

		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
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

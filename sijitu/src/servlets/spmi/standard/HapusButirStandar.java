package servlets.spmi.standard;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.ListIterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.StringTokenizer;

import beans.dbase.spmi.UpdateStandarMutu;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class HapusButirStandar
 */
@WebServlet("/HapusButirStandar")
public class HapusButirStandar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HapusButirStandar() {
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
			PrintWriter out = response.getWriter();
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
			//String uri = request.getRequestURI();
			//String url = PathFinder.getPath(uri, target);
			String mode="start";
			String id_master_std=request.getParameter("id_master_std");
			String id_tipe_std=request.getParameter("id_tipe_std");
			String id_std_isi=request.getParameter("id_std_isi");
			String id_std=request.getParameter("id_std");
			String id_versi=request.getParameter("id_versi");
			String atMenu=request.getParameter("atMenu");
			String kdpst_nmpst_kmp=request.getParameter("kdpst_nmpst_kmp");
			String at_menu=request.getParameter("at_menu");
			
			UpdateStandarMutu usm = new UpdateStandarMutu();
			int upd = usm.deleteButirStd(Integer.parseInt(id_std_isi));
			/*
			 * versi berikutnya bisa dicek apa kah riwayat folder arsipnya juga dihapus
			 */
			request.getRequestDispatcher("go.getListAllStd?mode="+mode+"&id_master_std="+id_master_std+"&id_tipe_std="+id_tipe_std+"&id_std_isi="+id_std_isi+"&id_std="+id_std+"&id_versi="+id_versi+"&atMenu="+atMenu+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&at_menu="+at_menu).forward(request,response);
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

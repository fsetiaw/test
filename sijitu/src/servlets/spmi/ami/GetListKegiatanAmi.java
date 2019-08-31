package servlets.spmi.ami;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import beans.dbase.spmi.riwayat.ami.SearchAmi;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;



/**
 * Servlet implementation class GetListKegiatanAmi
 */
@WebServlet("/GetListKegiatanAmi")
public class GetListKegiatanAmi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetListKegiatanAmi() {
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
			String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
			StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
			String kdpst = st.nextToken();
			String nmpst = st.nextToken();
			String kdkmp = st.nextToken();
			
			//prep rangkuman ami yg sudah selesai
			SearchAmi sa = new SearchAmi();
			Vector v = sa.prepSummaryRiwayatAmiYgSudahDilakukan(kdpst);
			if(v!=null) {
				session.setAttribute("v_list_riwayat_ami_sudah_done", v);
			}
			
			
			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/Ami/riwayat_ami_v1.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);
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

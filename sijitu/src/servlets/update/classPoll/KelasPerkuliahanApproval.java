package servlets.update.classPoll;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.classPoll.UpdateDbClassPoll;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class KelasPerkuliahanApproval
 */
@WebServlet("/KelasPerkuliahanApproval")
public class KelasPerkuliahanApproval extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KelasPerkuliahanApproval() {
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
			String verdict = request.getParameter("verdict");
			String info = request.getParameter("info");
			String target_thsms = request.getParameter("target_thsms");
			String alasan = request.getParameter("alasan");
			//String jabatan_approval = request.getParameter("jabatan_approval");
			//System.out.println(verdict+"--"+info+"--"+target_thsms+"--"+alasan);
			int userid=isu.getIdObj();
			UpdateDbClassPoll udc = new UpdateDbClassPoll(isu.getNpm());
			udc.prosesApprovalKelasPerkuliahan(target_thsms, verdict, info, alasan, userid);
	  					
			
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
			//String uri = request.getRequestURI();
			//String url = PathFinder.getPath(uri, target);

			request.getRequestDispatcher("get.statusPengajuanKelasKuliah?atMenu=form").forward(request,response);

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

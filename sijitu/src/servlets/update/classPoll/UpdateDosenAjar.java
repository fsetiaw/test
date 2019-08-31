package servlets.update.classPoll;

import java.io.IOException;
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
 * Servlet implementation class UpdateDosenAjar
 */
@WebServlet("/UpdateDosenAjar")
public class UpdateDosenAjar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateDosenAjar() {
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
			//PrintWriter out = response.getWriter();
			String[]kelas_dosen_ajar = request.getParameterValues("dosen");
			String target_thsms = request.getParameter("target_thsms");
			String no_edit = request.getParameter("no_edit");
			String info = request.getParameter("info");
			if(kelas_dosen_ajar!=null) {
				UpdateDbClassPoll udp = new UpdateDbClassPoll();
				udp.updateDosenAjar(kelas_dosen_ajar);
			}
			
			request.getRequestDispatcher("get.listKelasPengajuan?atMenu=form&no_edit="+no_edit+"&target_thsms="+target_thsms+"&info="+info+"&MenuBackOnly=true&backTo=listPengajuanProdi").forward(request,response);
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

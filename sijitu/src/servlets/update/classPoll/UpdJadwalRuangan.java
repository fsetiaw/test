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
 * Servlet implementation class UpdJadwalRuangan
 */
@WebServlet("/UpdJadwalRuangan")
public class UpdJadwalRuangan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdJadwalRuangan() {
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
			String target_thsms = request.getParameter("target_thsms");
			String cuid = request.getParameter("cuid");
			String gedung = request.getParameter("gedung");
			String room = request.getParameter("room");
			String[]hari = request.getParameterValues("hari");
			String senin = ""+request.getParameter("sen");
			String selasa = ""+request.getParameter("sel");
			String rabu = ""+request.getParameter("rab");
			String kamis = ""+request.getParameter("kam");
			String jumat = ""+request.getParameter("jum");
			String sabtu = ""+request.getParameter("sab");
			String minggu = ""+request.getParameter("min");
			String[]time = {senin,selasa,rabu,kamis,jumat,sabtu,minggu};
			UpdateDbClassPoll udp = new UpdateDbClassPoll(isu.getNpm());
			udp.updateJadwalRuanganKelas(cuid,gedung,room,hari,time);
			//for(int i=0;i<hari.length;i++) {
				
				////System.out.println(hari[i]);
			//}
		    //String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
		    //String uri = request.getRequestURI();
		    //String url = PathFinder.getPath(uri, target);

		    request.getRequestDispatcher("get.getListOpenedClass?cmd=jadwal&atMenu=jadwal&targetThsms="+target_thsms).forward(request,response);

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

package servlets.update.status_kehadiran_dosen;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.status_kehadiran_dosen.UpdateDbkehadiranDosen;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class StatusKehadiranDosen
 */
@WebServlet("/StatusKehadiranDosen")
public class StatusKehadiranDosen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatusKehadiranDosen() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here
			//String thsms_now = Checker.getThsmsNow();
			String target_kdpst = (String)session.getAttribute("target_kdpst");
			String target_shift = (String)session.getAttribute("target_shift");
			String target_thsms = (String)session.getAttribute("target_thsms");
			String cmd = request.getParameter("cmd");
			String backto = request.getParameter("backto");
			//System.out.println(target_kdpst+"`"+target_shift+"`"+target_thsms);
			String[]cuid = request.getParameterValues("cuid");
			String[]tgl_ttm = request.getParameterValues("tgl_ttm");
			String[]delay_tm = request.getParameterValues("delay_tm");
			String[]batal = request.getParameterValues("batal");
			
			UpdateDbkehadiranDosen udb = new UpdateDbkehadiranDosen(isu.getNpm());
			udb.updateStatusKehadiranDosen(cuid, tgl_ttm, delay_tm, batal, target_thsms);
		
			
			String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/ShowNotificationThenRedirect_v2.jsp"; 
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath(uri, target);
			//request.getRequestDispatcher("getClasPol.statusKehadiranKelas?atMenu=kehadiran").forward(request,response);
			//request.getRequestDispatcher(url+"?redirectTo=go.statusKehadiranKelas_v2&paramNeeded=target_thsms``"+target_thsms+"&target_kdpst``"+target_kdpst+"`target_shift``"+target_shift+"&msg=Sedang Update Data, Harap Menunggu&timeout=3").forward(request,response);
			request.getRequestDispatcher(url+"?redirectTo=go.statusKehadiranKelas_v2&paramNeeded=cmd``"+cmd+"`backto``"+backto+"&msg=Sedang Update Data, Harap Menunggu&timeout=3").forward(request,response);
			/*
			if(from!=null && from.equalsIgnoreCase("home")) {
				request.getRequestDispatcher(url+"?redirectTo=getClasPol.statusKehadiranKelas&paramNeeded=from``home`atMenu``kehadiran&msg=Sedang Update Data, Harap Menunggu&timeout=3").forward(request,response);
			}
			else {
				request.getRequestDispatcher(url+"?redirectTo=getClasPol.statusKehadiranKelas&paramNeeded=atMenu``kehadiran&msg=Sedang Update Data, Harap Menunggu&timeout=3").forward(request,response);
			}
			*/
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

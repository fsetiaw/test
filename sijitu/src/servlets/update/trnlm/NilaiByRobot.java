package servlets.update.trnlm;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.tbbnl.UpdateDbTbbnl;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class NilaiByRobot
 */
@WebServlet("/NilaiByRobot")
public class NilaiByRobot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NilaiByRobot() {
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
			String target_thsms = request.getParameter("thsms");
			String atBoxMenu = "sink_nilai_robot";
			String msg = "done";
			int tot_rec_effected = 0;
			if(!Checker.isStringNullOrEmpty(target_thsms)) {
				UpdateDbTbbnl udt = new UpdateDbTbbnl();
				tot_rec_effected = udt.copyNilaiKeKolomRobot(target_thsms);
				msg = "done<br>tot_rec_effected="+tot_rec_effected;
			}
			else {
				msg = "Err : Tahun-sms harap diisi";
			}
			
			Vector v = new Vector();
			v.add(0,""+tot_rec_effected);
			v.add(0,"TOTAL DATA UPDATED");
			v.add(0,"600px");
			v.add(0,"95");
			v.add(0,"center");
			v.add(0,"String");
			session.setAttribute("v", v);
			
			//request.getRequestDispatcher("get.notifications?atBoxMenu="+atBoxMenu+"&status_proses="+msg).forward(request,response);
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

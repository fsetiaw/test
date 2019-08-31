package servlets.maintenance;

import java.io.IOException;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.classPoll.SearchDbClassPoll;
import beans.dbase.dosen.SearchDbDsn;
import beans.dbase.mhs.btstu.SearchDbInfoMhsBtstu;
import beans.dbase.mhs.kurikulum.SearchDbInfoKurikulum;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.dbase.trnlm.MaintenanceDbTrnlm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;
/**
 * Servlet implementation class CekErrorKrs
 */
@WebServlet("/CekErrorKrs")
public class CekErrorKrs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CekErrorKrs() {
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
			String thsms = request.getParameter("thsms");
			boolean makul_aktif_only = true;
			//System.out.println("gowes");
			MaintenanceDbTrnlm mdt = new MaintenanceDbTrnlm();
			Vector v = mdt.cekErrorMakulYgTidakTerdafarPadaProdi(thsms, makul_aktif_only);
			if(v!=null && v.size()>0) {
				//kdpst+"`"+npmhs+"`"+nmmhs+"`"+nimhs+"`"+smawl+"`"+stpid+"`"+angel+"`"+kdjen

				//System.out.println("size="+v_npmhs.size());
				v.add(0,"KETERANGAN ERROR`VARIABLE");
				v.add(0,"600px"); 
				v.add(0,"60`35");
				v.add(0,"center`center");
				
				v.add(0,"String`String");
			}
			else {
				v=null;
			}
				session.setAttribute("v", v);
			//PrintWriter out = response.getWriter();
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
			//String uri = request.getRequestURI();
			//String url = PathFinder.getPath(uri, target);
			//request.getRequestDispatcher(url).forward(request,response);
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

package servlets.mhs.profile.kemahasiswaan;

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
import beans.dbase.mhs.maintenance.MaintenaceSearchDb;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class CekErrorKo
 */
@WebServlet("/CekErrorKo")
public class CekErrorKo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CekErrorKo() {
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
			//System.out.println("thsms = "+thsms);
			MaintenaceSearchDb msd = new MaintenaceSearchDb();
			Vector v_scope_id = isu.returnScopeProdiOnlySortByKampusWithListIdobj("s");
			Vector vf = null; 
			if(v_scope_id!=null && v_scope_id.size()>0) {
				vf = msd.cekErrorKo(thsms, v_scope_id);
			}
			
			if(vf!=null) {
				//kdpst+"`"+npmhs+"`"+nmmhs+"`"+nimhs+"`"+smawl+"`"+stpid+"`"+angel+"`"+kdjen

				//System.out.println("size="+v_npmhs.size());
				vf.add(0,"PRODI`NPM`NAMA`KETERANGAN ERROR");
				vf.add(0,"800px");
				vf.add(0,"5`15`30`45");
				vf.add(0,"center`center`left`left");
				vf.add(0,"String`String`String`String");
			}
			session.setAttribute("v", vf);
			//kdpst+"`"+npmhs+"`"+nmmhs+"`Menggunakan KO Prodi lain");
			
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

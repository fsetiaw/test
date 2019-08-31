package servlets.pddikti;

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
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class PrepFormKoMhs
 */
@WebServlet("/SetKeluarMhsLewatStudi")
public class SetKeluarMhsLewatStudi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetKeluarMhsLewatStudi() {
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
			//System.out.println("siap");
			String target_thsms = request.getParameter("thsms");
			String target_kdpst = request.getParameter("kdpst");
			SearchDbInfoMhsBtstu sdt = new SearchDbInfoMhsBtstu(isu.getNpm());
			
			Vector v = sdt.getListMhsLewatBtstuNpmhOnly(target_thsms, target_kdpst);
			UpdateDbTrlsm udt = new UpdateDbTrlsm();
			udt.setKeluarMhs(v, target_thsms, target_kdpst);
			//Vector v = sdt.getListMhsLewatBtstu(target_thsms, target_kdpst);
			//SearchDbInfoKurikulum sdk = new SearchDbInfoKurikulum();
			//Vector v = sdk.getListNpmMhsAktifDgnKurikulumnya(target_thsms, target_kdpst);
			request.setAttribute("v_npm", v);
			//SearchDbDsn sdd = new SearchDbDsn();
			//sdd.hitungBebanMengajarVersiEpsbed(target_thsms);
			String target = Constants.getRootWeb()+"/InnerFrame/Pddikti/BatasStudi/MhsLewatBtsStudi.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);

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

package servlets.update.trnlm;

import java.io.IOException;
import java.util.ListIterator;
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
import beans.dbase.trnlm.SearchDbTrnlm;
import beans.dbase.trnlm.UpdateDbTrnlm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class AssignKlsEpsbed
 */
@WebServlet("/AssignKlsEpsbed")
public class AssignKlsEpsbed extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignKlsEpsbed() {
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
			//System.out.println("goggadte");
			//System.out.println(AskSystem.getCurrentTimestamp());;
			String target_thsms = request.getParameter("target_thsms");
			String tkn_kdpst = request.getParameter("tkn_kdpst");
			session.setAttribute("target_thsms", target_thsms);
			session.setAttribute("target_tkn_kdpst", tkn_kdpst);
			//System.out.println("target_thsms = "+target_thsms);
			Vector v_dos = Getter.getListDosen_v1();
			ListIterator lid = null;
			//String thsms = request.getParameter("thsms");
			SearchDbDsn sdd = new SearchDbDsn();
			UpdateDbTrnlm udt = new UpdateDbTrnlm();
			SearchDbTrnlm sdt = new SearchDbTrnlm();
			Vector v = null;
			  //v = sdd.getListInfoTrakd(thsms);
			//isert kelas ke trakd_epsbed
			if(Checker.isStringNullOrEmpty(tkn_kdpst)) {
				v = sdt.getListKelasKuliahForFeeder_v1(target_thsms);
			}
			else {
				v = sdt.getListKelasKuliahForFeeder_v1(target_thsms,tkn_kdpst);
			}
			//System.out.println("1");
			//System.out.println(v.size());
			//System.out.println("2");
			//System.out.println(v.size());
			v = sdt.addInfoDaftarMahasiswaDanDaftarkanKeTrnlmEpsbed(v, target_thsms);
			int updated = udt.updateTrnlmKelasEpsbed(v, target_thsms);
			//System.out.println("3="+updated);
			//System.out.println(AskSystem.getCurrentTimestamp());;
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

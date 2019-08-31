package servlets.pddikti;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.mhs.SearchDbInfoMhs;
import beans.dbase.mhs.btstu.SearchDbInfoMhsBtstu;
import beans.dbase.mhs.btstu.UpdateDbInfoMhsBtstu;
import beans.dbase.mhs.maintenance.MaintenaceSearchDb;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class KeluarkanMhsLewatBtstu
 */
@WebServlet("/KeluarkanMhsLewatBtstu")
public class KeluarkanMhsLewatBtstu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KeluarkanMhsLewatBtstu() {
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
			//System.out.println("cek mhs btstu");
			String thsms_now = request.getParameter("thsms");
			String target_kdpst = request.getParameter("kdpst");
			//System.out.println("thsms_now "+thsms_now);
			//System.out.println("target_kdpst "+target_kdpst);
			//SearchDbInfoMhs sdi = new SearchDbInfoMhs(isu.getNpm());
			
			//Vector v = sdi.getListNpmhsYgAdaDiTrnlm(thsms, kdpst, true);
			SearchDbInfoMhsBtstu sdb = new SearchDbInfoMhsBtstu(isu.getNpm());
			boolean malaikat_included = true;
			Vector v = sdb.getListMhsAktifLewatBtstu(thsms_now, target_kdpst, malaikat_included);
			UpdateDbInfoMhsBtstu udb = new UpdateDbInfoMhsBtstu(isu.getNpm());
			int upd = udb.insertStmhstrlsmMhsLewatBatasStudi(v, thsms_now, "L", "Lewat Batas Studi");
			v = new Vector();
			v.add(new String(""+upd));
			if(v!=null && v.size()>0) {
				//kdpst+"`"+npmhs+"`"+nmmhs+"`"+nimhs+"`"+smawl+"`"+stpid+"`"+angel+"`"+kdjen

				//System.out.println("size="+v.size());
				//kdpst+"`"+npmhs+"`"+nmmhs+"`"+smawl+"`"+batas
				v.add(0,"JUMLAH DATA UPDATED");
				v.add(0,"800px");
				v.add(0,"100");
				v.add(0,"center");
				
				v.add(0,"String");
			}
			//else {
			//	System.out.println("v is null");
			//}
			session.setAttribute("v", v);
			//String target = Constants.getRootWeb()+"/InnerFrame/Pddikti/InputDobel/MhsDobelInput.jsp";
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

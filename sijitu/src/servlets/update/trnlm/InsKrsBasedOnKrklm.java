package servlets.update.trnlm;

import java.io.IOException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.mhs.SearchDbInfoMhs;
import beans.dbase.trnlm.UpdateDbTrnlm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.AddHocFunction;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class InsKrsBasedOnKrklm
 */
@WebServlet("/InsKrsBasedOnKrklm")
public class InsKrsBasedOnKrklm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsKrsBasedOnKrklm() {
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
			String kdpst = request.getParameter("kdpst");
			String thsms = request.getParameter("thsms");
			String npmhs = request.getParameter("npmhs");
			//System.out.println("npmhs = "+npmhs);
			if(kdpst!=null) {
				if(kdpst.equalsIgnoreCase("999999")) {
					//kelas malaikat
					SearchDbInfoMhs sdim = new SearchDbInfoMhs();
					String tkn_npm = sdim.getMhsAktifVersiPddikti(thsms, kdpst);
					Vector v = AddHocFunction.hitungTotSemesterDariSamwlSampaiTargetThsmsDikurangNonAktifTrlsm_v1(tkn_npm, thsms);
					v = sdim.addInfoKrklm(v);
					v = sdim.addInfoIdobjKdjenKdpstKodeKmp(v);
					
					if(v!=null) {
						UpdateDbTrnlm udt = new UpdateDbTrnlm();
						udt.insertKrsBasedOnKoAtThsms(v, thsms);
					}
				}
			}
			if(!Checker.isStringNullOrEmpty(npmhs) && !Checker.isStringNullOrEmpty(thsms)) {
				//System.out.println("npmhs = kesini");
				SearchDbInfoMhs sdim = new SearchDbInfoMhs();
				String tkn_npm = new String(npmhs);
				Vector v = AddHocFunction.hitungTotSemesterDariSamwlSampaiTargetThsmsDikurangNonAktifTrlsm_v1(tkn_npm, thsms);		
				v = sdim.addInfoKrklm(v);				
				v = sdim.addInfoIdobjKdjenKdpstKodeKmp(v);
							
				if(v!=null) {
					UpdateDbTrnlm udt = new UpdateDbTrnlm();
					udt.insertKrsBasedOnKoAtThsms(v, thsms);
				}
				
			}
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

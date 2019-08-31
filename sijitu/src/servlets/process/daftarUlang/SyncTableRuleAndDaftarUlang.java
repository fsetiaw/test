package servlets.process.daftarUlang;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.AddHocFunction;
import beans.tools.PathFinder;

/**
 * Servlet implementation class SyncTableRuleAndDaftarUlang
 */
@WebServlet("/SyncTableRuleAndDaftarUlang")
public class SyncTableRuleAndDaftarUlang extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SyncTableRuleAndDaftarUlang() {
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
			//System.out.println("sinc okay");
			
			
			AddHocFunction.initializeTableRule("HEREGISTRASI_RULES",true);
			//System.out.println("sinc HEREGISTRASI_RULES");
			AddHocFunction.updateTableRule("HEREGISTRASI_RULES");
			//System.out.println("sinc okay");
			AddHocFunction.initializeTableRule("PINDAH_PRODI_RULES",false);
			//System.out.println("sinc PINDAH_PRODI_RULES");
			AddHocFunction.updateTableRule("PINDAH_PRODI_RULES");
			//System.out.println("sinc okay");
			AddHocFunction.initializeTableRule("CUTI_RULES",false);
			//System.out.println("sinc CUTI_RULES");
			AddHocFunction.updateTableRule("CUTI_RULES");
			//System.out.println("sinc okay");
			AddHocFunction.initializeTableRule("KELAS_PERKULIAHAN_RULES",true);
			//System.out.println("sinc KELAS_PERKULIAHAN_RULES");
			AddHocFunction.updateTableRule("KELAS_PERKULIAHAN_RULES");
			//System.out.println("sinc okay");
			AddHocFunction.syncTabelDaftarUlangWithHeregistrasiRule();
			
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
			//String uri = request.getRequestURI();
			//String url = PathFinder.getPath(uri, target);

			request.getRequestDispatcher("get.notifications").forward(request,response);

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

package servlets.process.pindah_prodi_rules;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.Param.SearchDbParam;
import beans.dbase.jabatan.SearchDbJabatan;
import beans.dbase.pengajuan.pindah_prodi.SearchDbPp;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class PrepPindahProdi
 */
@WebServlet("/PrepPindahProdi")
public class PrepPindahProdi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepPindahProdi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
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
			String target_thsms = Checker.getThsmsHeregistrasi();
			//System.out.println("goonm");
			String atMenu = request.getParameter("atMenu");
			SearchDbJabatan sdb = new SearchDbJabatan(isu.getNpm());
			//Vector v = sdb.getListTitleJabatan();
			Vector v = sdb.getListTitleJabatanIndividu();
			Vector vp = Getter.getListProdi();
			Vector vkmp = Getter.getListAllKampus();
			//SearchDbPp sdp = new SearchDbPp(isu.getNpm());
			//Vector vc = sdp.getCurrentRule(target_thsms);
			SearchDbParam sdp = new SearchDbParam(isu.getNpm());
			Vector vc = sdp.getCurrentRule(target_thsms,"PINDAH_PRODI_RULES");
			request.setAttribute("vListJabatan", v);
			request.setAttribute("vp", vp);
			request.setAttribute("vkmp",vkmp);
			request.setAttribute("vc",vc);
			
			
			///ToUnivSatyagama/WebContent/InnerFrame/Parameter/PindahProdi/dashPindahProdi.jsp
			String target = Constants.getRootWeb()+"/InnerFrame/Parameter/PindahProdi/dashPindahProdi.jsp"; 
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?atMenu="+atMenu).forward(request,response);

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

package servlets.update.param;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.Param.UpdateDbParam;
import beans.dbase.tbbnl.UpdateDbTbbnl;
import beans.login.InitSessionUsr;
import beans.setting.Constants;

/**
 * Servlet implementation class UpdateParamBobotNilai
 */
@WebServlet("/UpdateParamBobotNilai")
public class UpdateParamBobotNilai extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateParamBobotNilai() {
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
			try {
				//System.out.println("okay");
				//String[]job = request.getParameterValues("job");
				String[]prodi = request.getParameterValues("prodi");
				//String[]kmp = request.getParameterValues("kmp");
				String target_thsms = request.getParameter("target_thsms");
				String list_nilai_bobot = request.getParameter("list_bobot");
				String scopeCmd = request.getParameter("scopeCmd");
				//for(int i=0;i<prodi.length;i++) {
				//	System.out.println(prodi[i]);
				//}
				//System.out.println(list_nilai_bobot);
				
				UpdateDbTbbnl udt = new UpdateDbTbbnl(isu.getNpm());
				udt.updateParamNilaiBobot(target_thsms,prodi, list_nilai_bobot);
				//System.out.println(""+list_bobot);
				//System.out.println("target_thsms="+target_thsms);
				String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp"; 
				//String uri = request.getRequestURI(); 
				//String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher("prep.prepParamBobotNilai?atMenu="+scopeCmd).forward(request,response);
			}
			catch(Exception e) {}
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

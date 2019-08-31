package servlets.monitoring;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.overview.maintenance.CekTableMaintenance;
import beans.dbase.trnlm.SearchDbTrnlm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class GetInfoDbError
 */
@WebServlet("/GetInfoDbError")
public class GetInfoDbError extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetInfoDbError() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here
			///ToUnivSatyagama/WebContent/InnerFrame/Spmi/Monitoring/pengisian_krs.jsp
			CekTableMaintenance ctm = new CekTableMaintenance();
			String nm_jab = request.getParameter("jabatan");
			String scope_kdpst = isu.getScopeKdpstJabatan(nm_jab, true);
			//System.out.println("nm_jab="+nm_jab);
			//System.out.println("scope_kdpst="+scope_kdpst);
			Vector v = ctm.getListErrorInfo(scope_kdpst);
			
			session.setAttribute("v_err_info", v);
			/*
			  
			 
			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/Monitoring/pengisian_krs.jsp"; 
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url).forward(request,response);
			 */
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

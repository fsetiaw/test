package servlets.monitoring;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.makul.SearchDbMk;
import beans.dbase.trnlm.SearchDbTrnlm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class ErrorKdkmkTrnlmVsMakul
 */
@WebServlet("/ErrorKdkmkTrnlmVsMakul")
public class ErrorKdkmkTrnlmVsMakul extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErrorKdkmkTrnlmVsMakul() {
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
			///ToUnivSatyagama/WebContent/InnerFrame/Spmi/Monitoring/pengisian_krs.jsp
			//System.out.println("okey");
			String target_thsms = request.getParameter("target_thsms");
			String target_kdpst = request.getParameter("target_kdpst");
			SearchDbMk sdm = new SearchDbMk();
			Vector v_npmhs = sdm.findMkError(target_kdpst, target_thsms);
			//System.out.println("v0="+v.size());
			v_npmhs = sdm.listKrsNeedToBeFixManualy(v_npmhs);
			//System.out.println("v1="+v.size());
			//request.setAttribute("v_err", v_npmhs);
			
			if(v_npmhs!=null) {
				v_npmhs.add(0,"THSMS KRS`NPM`KDKMK");
				v_npmhs.add(0,"80%"); 
				v_npmhs.add(0,"15`25`55");
				v_npmhs.add(0,"center`center`center");
				v_npmhs.add(0,"String`String`String");
			}
			session.setAttribute("v", v_npmhs);
			//String target = Constants.getRootWeb()+"/InnerFrame/Monitoring/listKdkmkNggaAdaDiMakul.jsp"; 
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

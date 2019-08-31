package servlets.logout;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.UpdateDb;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

import java.io.PrintWriter;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public Logout() {
//        super();
        // TODO Auto-generated constructor stub
//    }
	public void init(ServletConfig config) throws ServletException {
		
		super.init(config);
		/*
		Context initContext = null;
		Context envContext = null;
	    try {
	      // Look up the JNDI data source only once at init time
    		initContext  = new InitialContext();
    		envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		
    		envContext.close();
    		initContext.close();
    		if(ds==null) {
    			throw new ServletException("missing data source");
    		}		
	    }
	    catch (NamingException e) {
	      e.printStackTrace();
	    }
	    */
	  }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		UpdateDb udb = new UpdateDb();
		String ses_id = request.getSession().getId();
		udb.insertLogMeOut(ses_id );
		isu = null;
		session.removeAttribute("validUsr");
		session.invalidate();
		//response.setContentType("text/html");
		String target = Constants.getRootWeb()+"/login.html";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		//request.getRequestDispatcher(target).forward(request,response);
		response.sendRedirect("../login.html");
		//return;
		/*
	    PrintWriter out = response.getWriter();

	    out.println("<!DOCTYPE html>");
	    out.println("<head></head>");
	    out.println("<body>");
	    out.println("<h2>login out</h2>");
	    out.println("</body>");
	    out.println("</html>");
	    session.removeAttribute("validUsr");
	    session.invalidate();
	    out.print("<meta http-equiv=\"refresh\" content=\"2;url="+Constants.getRootWebUniv()+"\">");
	    out.close();
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			    doGet(request, response);

	}

}

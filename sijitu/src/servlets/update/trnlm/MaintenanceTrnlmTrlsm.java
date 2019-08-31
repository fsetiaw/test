package servlets.update.trnlm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.trnlm.MaintenanceDbTrnlm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class MaintenanceTrnlmTrlsm
 */
@WebServlet("/MaintenanceTrnlmTrlsm")
public class MaintenanceTrnlmTrlsm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MaintenanceTrnlmTrlsm() {
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
			//System.out.println("mainetnacnr");
			MaintenanceDbTrnlm mdt = new MaintenanceDbTrnlm(isu.getNpm());
			boolean lanjut = true;
	    	int offset = 0;
	    	int limit = 10;
	    	StringTokenizer st = null;
	    	while(lanjut) {
	    		//System.out.println("offset="+offset);
	    		String tot_data_and_tot_updated = mdt.maintenanceTrnlmTrakmMhsYgSdhOut(limit, offset);
	    		//System.out.println("tot_data_and_tot_updated="+tot_data_and_tot_updated);
	    		st = new StringTokenizer(tot_data_and_tot_updated,"`");
	    		int tot_data = Integer.parseInt(st.nextToken());
	    		if(tot_data<limit) {
	    			lanjut = false;
	    		}
	    		else {
	    			offset = offset + limit;	    		
	    		}
	    		
	    	}	

		    //System.out.println("done 1");
			//PrintWriter out = response.getWriter();
			//out.print("<hmtl></html>")
			//String target = Constants.getRootWeb()+"/InnerFrame/standalone_proses/index_stp.jsp";
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

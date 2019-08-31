package servlets.feeder.importer;

import java.io.IOException;
import java.util.ListIterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.feeder.importer.KapasitasImporter;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class Kapasitas
 */
@WebServlet("/Kapasitas")
public class Kapasitas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Kapasitas() {
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

		//kode here
		String target_thsms = request.getParameter("thsms");
		//System.out.println("import kapasitas");
		KapasitasImporter ki = new KapasitasImporter(isu.getNpm());
		Vector v_err = ki.importCapacity(target_thsms);
		if(v_err.size()>0) {
			ListIterator li = v_err.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				//System.out.println("err="+brs);
			}
		}
//		Vector v_error = bni.importKapasitas();
//		if(v_error!=null && v_error.size()>0) {
//			ListIterator li = v_error.listIterator();
//			while(li.hasNext()) {
//				//System.out.println((String)li.next());
//			}
//		}
		//System.out.println("done");
		
		String target = Constants.getRootWeb()+"/InnerFrame/Feeder/done.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);

		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
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

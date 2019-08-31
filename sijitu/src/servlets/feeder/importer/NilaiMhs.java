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

import beans.dbase.feeder.importer.KurikulumImporter;
import beans.dbase.feeder.importer.NilaiMhsImporter;
import beans.dbase.feeder.importer.MataKuliahImporter;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class MataKuliah
 */
@WebServlet("/NilaiMhs")
public class NilaiMhs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NilaiMhs() {
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
		Vector v_err = new Vector();
		ListIterator lier = v_err.listIterator();
		//kode here
		//String target_thsms = request.getParameter("thsms");
		String target_thsms = request.getParameter("thsms");
		System.out.println("import nilai mhs "+target_thsms);
		int limit = 1000;
		int offset = 0;
		boolean done = false;
		int iter = 0;
		NilaiMhsImporter ki = new NilaiMhsImporter(isu.getNpm());
		while(!done) {
			System.out.println("offset="+offset);
			Vector v = ki.getDataTrnlm(target_thsms, limit, offset);
			
			
			if(v==null || v.size()<limit) {
				done = true;
			}
			if(v!=null) {
				System.out.println("vsie="+v.size());
				Vector vtmp = ki.importNilaiMhs(v);
				if(vtmp!=null) {
					ListIterator litmp = vtmp.listIterator();
					while(litmp.hasNext()) {
						String brs =(String)litmp.next();
						System.out.println(brs);
						lier.add(brs);
					}
				}
				System.out.println("vsie(1)="+v.size());
			}
			else {
				System.out.println("vsie=0");
			}
			offset = offset+limit;
			
		}
		
		//ki.importNilaiMhs(target_thsms);
		//Vector v_err = ki.importMakulKrklm();
		//Vector v_err = ki.importKrklm();
//		Vector v_err = ki.importMakul();
		session.setAttribute("v_err", v_err);
//		Vector v_error = bni.importMataKuliah();
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
			request.getRequestDispatcher(url).forward(request,response);
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

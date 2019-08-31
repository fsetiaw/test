package servlets.pddikti;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.mhs.SearchDbInfoMhs;
import beans.dbase.mhs.maintenance.MaintenaceSearchDb;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class CekMhsDiInputDobelTglhr
 */
@WebServlet("/CekMhsDiInputDobelTglhr")
public class CekMhsDiInputDobelTglhr extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CekMhsDiInputDobelTglhr() {
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
			String target_angkatan = request.getParameter("smawl");
			//System.out.println("iyalah "+target_thsms);
			MaintenaceSearchDb sdb = new MaintenaceSearchDb(isu.getNpm());
			Vector v = sdb.cekMhsDenganTglhrSama(target_angkatan);
			
			if(v!=null) {
				//kdpst+"`"+npmhs+"`"+nmmhs+"`"+nimhs+"`"+smawl+"`"+stpid+"`"+angel+"`"+kdjen

				//System.out.println("size="+v_npmhs.size());
				//System.out.println("v size = "+v.size());
				v.add(0,"PRODI`NPM`NAMA`TANGGAL LAHIR`TEMPAT LAHIR");
				v.add(0,"800px");
				v.add(0,"10`15`35`20`20");
				v.add(0,"center`center`center`center`center");
				
				v.add(0,"String`String`String`String`String");
			}
			
			session.setAttribute("v", v);
			//String target = Constants.getRootWeb()+"/InnerFrame/Pddikti/InputDobel/MhsDobelInput.jsp";
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

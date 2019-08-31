package servlets.spmi.ami;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.ListIterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.StringTokenizer;

import beans.dbase.spmi.riwayat.ami.SearchAmi;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetRiwayatAmiProdi
 */
@WebServlet("/GetRiwayatAmiProdi")
public class GetRiwayatAmiProdi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRiwayatAmiProdi() {
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
			//PrintWriter out = response.getWriter();
			//System.out.println("okay");
			String fwdto=request.getParameter("fwdto");
			String kdpst_nmpst_kmp=request.getParameter("kdpst_nmpst_kmp");
			SearchAmi sa = new SearchAmi();
			Vector v_scope_kdpst_spmi = (Vector)session.getAttribute("v_scope_kdpst_spmi");
			//Vector v = sa.getRiwayatAmi(v_scope_kdpst_spmi,false, true);
			Vector v = sa.getOverviewRiwayatAmi(v_scope_kdpst_spmi);
			if(v!=null) {
				session.setAttribute("v_riwayat_ami_prodi", v);
			}
			//System.out.println("fwdto="+fwdto);
			//System.out.println("kdpst_nmpst_kmp="+kdpst_nmpst_kmp);
			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/spmi_overview/riwayat_ami_prodi.jsp";
			//String target = Constants.getRootWeb()+"/InnerFrame/Spmi/spmi_overview/riwayat_ami_prodi_static.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
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

package servlets.spmi.doc;

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

import beans.dbase.spmi.ToolSpmi;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Getter;
import beans.tools.PathFinder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PrepDocMutu
 */
@WebServlet("/PrepDocMutu")
public class PrepDocMutu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepDocMutu() {
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
			//System.out.println("docmutu");
			Vector v_ListKdpstKdjenNmpstNmjen = Getter.getListKdpstKdjenNmpstNmjen();
			Vector v_ListDokumenMutu = Getter.getListDokumenMutu();
			
			ToolSpmi ts = new ToolSpmi();
			ts.createFolderStructureDocMutu(v_ListKdpstKdjenNmpstNmjen, v_ListDokumenMutu);
			PrintWriter out = response.getWriter();
			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/linkToDokumenMutu.jsp";
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

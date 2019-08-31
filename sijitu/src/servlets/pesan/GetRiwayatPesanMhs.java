package servlets.pesan;

import java.io.IOException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;
import beans.dbase.pesan.*;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.owasp.esapi.ESAPI;
/**
 * Servlet implementation class GetRiwayatPesan
 */
@WebServlet("/GetRiwayatPesanMhs")
public class GetRiwayatPesanMhs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRiwayatPesanMhs() {
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
			String cmd = request.getParameter("cmd");
			String limit_per_page = request.getParameter("limit_per_page");
			String nav = request.getParameter("nav");
			String offset = request.getParameter("offset");
			//if(offset==null) {
			//	offset = new String("0");
			//}
			//if(limit_per_page==null) {
			//	limit_per_page = new String("10");
			//}
			String tkn_my_nick = isu.getObjNickNameGivenObjId();
			Vector v_mgs_hist = null; 
			Vector v_scope_id = isu.returnScopeProdiOnlySortByKampusWithListIdobj(cmd);
			if(v_scope_id!=null) {
				HistoryPesanMhs gpt = new HistoryPesanMhs(isu.getNpm()) ;
				v_mgs_hist = gpt.getRiwayatPesan(Integer.parseInt(offset),Integer.parseInt(limit_per_page)+1,v_scope_id,tkn_my_nick);
			}
			else {}
			session.setAttribute("v_mgs_hist", v_mgs_hist);
		    String target = Constants.getRootWeb()+"/InnerFrame/Pesan/Riwayat/riwayat_pesan_mhs.jsp";
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

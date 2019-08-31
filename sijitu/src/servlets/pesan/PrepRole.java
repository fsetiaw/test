package servlets.pesan;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Getter;
import beans.tools.PathFinder;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.owasp.esapi.ESAPI;

/**
 * Servlet implementation class PrepRole
 */
@WebServlet("/PrepRole")
public class PrepRole extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepRole() {
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
			//System.out.println("tole");
			//System.out.println("npm="+isu.getNpm());
			//System.out.println("kdpst="+isu.getKdpst());
			//System.out.println("kmp="+isu.getKode_kmp_dom());
			String atMenu=request.getParameter("atMenu");
			String cmd=request.getParameter("cmd");
			String target_id_obj=request.getParameter("id_obj");
			String target_nmm=request.getParameter("nmm");
			String target_npm=request.getParameter("npm");
			String target_obj_lvl=request.getParameter("obj_lvl");
			String target_kdpst=request.getParameter("kdpst");
			String target_kmp = Getter.getDomisiliKampus(target_npm);
			JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/citcat/role/usr_npm/"+isu.getNpm()+"/to_send_msg_to/target_kdpst/"+target_kdpst+"/target_kmp/"+target_kmp);
			request.setAttribute("jsoa_role", jsoa);
			
			//ToUnivSatyagama/WebContent/InnerFrame/Pesan/FormSendMsg/at_menu_mhs.jsp
			String target = Constants.getRootWeb()+"/InnerFrame/Pesan/FormSendMsg/at_menu_mhs.jsp";
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

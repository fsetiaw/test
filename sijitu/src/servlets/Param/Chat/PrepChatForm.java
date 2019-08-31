package servlets.Param.Chat;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Path;

import org.codehaus.jettison.json.JSONArray;

import beans.dbase.Param.SearchDbParam;
import beans.dbase.jabatan.SearchDbJabatan;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class PrepChatForm
 */
@WebServlet("/PrepChatForm")
public class PrepChatForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepChatForm() {
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
			SearchDbJabatan sdb = new SearchDbJabatan(isu.getNpm());
			Vector v = sdb.getListTitleJabatan();
			Vector vp = Getter.getListProdi();
			Vector vkmp = Getter.getListAllKampus();
			SearchDbParam sdp = new SearchDbParam(isu.getNpm());
			Vector v_struk = sdp.getInfoStruktural();
			JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/citcat/group/get/list/struktural_group");
			request.setAttribute("v_struk", v_struk);
			request.setAttribute("jsoa_struk_grp_chat", jsoa);
			request.setAttribute("vListJabatan", v);
			request.setAttribute("vp", vp);
			request.setAttribute("vkmp",vkmp);

		    String target = Constants.getRootWeb()+"/InnerFrame/Parameter/ChatRoom/dashChatRoom.jsp";
		    String uri = request.getRequestURI();
		    String url = PathFinder.getPath(uri, target);
		    //System.out.println("masa sih");
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

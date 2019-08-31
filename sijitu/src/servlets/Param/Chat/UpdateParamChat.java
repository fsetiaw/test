package servlets.Param.Chat;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;

import beans.dbase.Param.SearchDbParam;
import beans.dbase.Param.UpdateDbParam;
import beans.dbase.jabatan.SearchDbJabatan;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateParamChat
 */
@WebServlet("/UpdateParamChat")
public class UpdateParamChat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateParamChat() {
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
			//System.out.println("chat rr");
			String[]job = request.getParameterValues("job");
			String[]prodi = request.getParameterValues("prodi");
			String[]kmp = request.getParameterValues("kmp");
			String grp_chat_id = request.getParameter("grp_chat");
			
			UpdateDbParam udp = new UpdateDbParam(isu.getNpm());
			udp.updateChatGroup(job,prodi,kmp, grp_chat_id);
			//System.out.println(job.length);
			//System.out.println(prodi.length);
			//System.out.println(kmp.length);
			//System.out.println(grp_chat_id);
		    //String target = Constants.getRootWeb()+"/InnerFrame/Parameter/ChatRoom/dashChatRoom.jsp";
		    //String uri = request.getRequestURI();
		    //String url = PathFinder.getPath(uri, target);
		    //System.out.println("masa sih");
		    request.getRequestDispatcher("go.prepFormChatAssignment?atMenu=chat").forward(request,response);

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

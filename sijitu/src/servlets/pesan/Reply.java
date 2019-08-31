package servlets.pesan;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class Reply
 */
@WebServlet("/Reply")
public class Reply extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Reply() {
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
			//System.out.println("nu pesan");
			///msg/reply/{from_npm}/{conversation_group_id}/{isi_pesan}")
			String current_sta_index = request.getParameter("cur_sta_index");
			if(current_sta_index==null||Checker.isStringNullOrEmpty(current_sta_index)) {
				current_sta_index = "0";
			}
			String str_range = request.getParameter("str_range");
			String npm_sender = request.getParameter("npm_sender");
			String grp_conversation_id = request.getParameter("grp_conversation_id");
			String grp_conversation_nm = request.getParameter("grp_conversation_nm");
			String isi_pesan = request.getParameter("pesan");
			if(isi_pesan!=null) {
				isi_pesan = URLEncoder.encode(isi_pesan, "UTF-8");
				isi_pesan = Converter.prepStringForUrlPassing(isi_pesan);
			}
			//isi_pesan = Converter.prepStringForUrlPassing(isi_pesan);
			String npm_indiv = request.getParameter("npm_indiv");
			//System.out.println("isi_pesan="+isi_pesan);
			JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/citcat/upd/msg/reply/"+npm_sender+"/"+grp_conversation_id+"/"+isi_pesan);
			
		    //String target = Constants.getRootWeb()+"/InnerFrame/Pesan/conversation_main_frame.jsp";
		    //String uri = request.getRequestURI();
		    //String url = PathFinder.getPath(uri, target);
		//<%=Constants.getRootWeb() %>/InnerFrame/Pesan/conversation_main_frame.jsp?str_range=<%=Constant.rangeMsgPerPage() %>&grp_nm=<%=v_nmmhs %> [<%=v_npmhs %>]&grp_id=<%=grp_id %>
			//request.getRequestDispatcher(url+"?str_range="+str_range+"&grp_conversation_nm="+grp_conversation_nm+"&grp_id="+grp_conversation_id).forward(request,response);
		   request.getRequestDispatcher("prep.conversation?str_range="+str_range+"&cur_sta_index="+current_sta_index+"&grp_conversation_nm="+grp_conversation_nm+"&grp_conversation_id="+grp_conversation_id).forward(request,response);

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

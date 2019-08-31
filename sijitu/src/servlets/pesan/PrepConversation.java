package servlets.pesan;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.owasp.esapi.ESAPI;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class PrepConversation
 */
@WebServlet("/PrepConversation")
public class PrepConversation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepConversation() {
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
			String str_range = request.getParameter("str_range");
			int range = Integer.parseInt(str_range);
			String cur_sta_index = request.getParameter("cur_sta_index");
			if(cur_sta_index==null || Checker.isStringNullOrEmpty(cur_sta_index)) {
				cur_sta_index = "0";
			}
			String grp_conversation_nm = request.getParameter("grp_conversation_nm");
			String grp_conversation_id = request.getParameter("grp_conversation_id");
			//JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/citcat/get/msg/room/"+grp_conversation_id+"/sta_idx/"+cur_sta_index+"/range/"+range);
			//session.setAttribute("jsoa", jsoa);
			//System.out.println("sip11."+grp_conversation_nm+"`"+grp_conversation_id+"`"+cur_sta_index+"`"+str_range);
			/*
			 * 	"msg_id": 71,
	"delivered": true,
	"pinned": false,
	"sender_id": 5214,
	"npm_sender": "0000000000001",
	"fullname_sender": "FAJAR SETIAWAN",
	"default_role_sender": "null",
	"sender_grp_id": 0,
	"tipe_grp_s": "null",
	"name_grp_s": "null",
	"nick_grp_s": "null",
	"list_grp_id_s": "null",
	"kdpst_s": "null",
	"kampus_s": "null",
	"receiver_id": 0,
	"npm_receiver": "null",
	"fullname_receiver": "null",
	"default_role_receiver": "null",
	"receiver_grp_id": 7,
	"tipe_grp_r": "G",coversation
	"name_grp_r": "BIRO ADMINISTRASI AKADEMIK",
	"nick_grp_r": "BAAK",
	"list_grp_id_r": "[3847][48120]",
	"kdpst_r": "[ALL]",
	"kampus_r": "[PST]",
	"list_id_reed": "null",
	"trash": false,
	"creatime": "2016-08-18 16:12:00.0",
	"pesan": "pesan 1",
	"grp_conv_id": 10

			 */
					//ToJson converter = new ToJson();
		       String target = Constants.getRootWeb()+"/InnerFrame/Pesan/conversation.jsp";
		       String uri = request.getRequestURI();
		       String url = PathFinder.getPath(uri, target);
		       //System.out.println("sini. --"+grp_conversation_nm+"`"+grp_conversation_id+"`"+cur_sta_index+"`"+str_range);
		       request.getRequestDispatcher(url+"?str_range="+str_range+"&cur_sta_index="+cur_sta_index+"&grp_conversation_nm="+grp_conversation_nm+"&grp_conversation_id="+grp_conversation_id).forward(request,response);

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

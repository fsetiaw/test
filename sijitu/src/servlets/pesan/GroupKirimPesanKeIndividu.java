package servlets.pesan;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Path;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.PathFinder;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.owasp.esapi.ESAPI;

/**
 * Servlet implementation class GroupKirimPesanKeIndividu
 */
@WebServlet("/GroupKirimPesanKeIndividu")
public class GroupKirimPesanKeIndividu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupKirimPesanKeIndividu() {
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
			//System.out.println("kirim pean");
			String sender_npm = request.getParameter("sender_npm");
			String sender_grp_id = request.getParameter("sender_grp_id");
			String target_npm = request.getParameter("target_npm");
			String target_nmm = request.getParameter("target_nmm");
			String target_idobj = request.getParameter("target_idobj");
			String target_kdpst = request.getParameter("target_kdpst");
			String target_objlv = request.getParameter("target_objlv");
			String isi_pesan = request.getParameter("isi_pesan");
			
			
			isi_pesan = Converter.prepStringForUrlPassing(isi_pesan);
			
			
			//@Path("/msg/kirim/group/{sender_nm}/{sender_npm}/{sender_role}/{target_group_id}/{list_monitoree_role}/{isi_pesan}"
			JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/citcat/upd/msg/kirim/single_npm_receiver/"+target_npm+"/group/"+sender_npm+"/null/"+sender_grp_id+"/"+isi_pesan);
			
			
			String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/ShowNotificationThenRedirect_v2.jsp";
		    String uri = request.getRequestURI();
		    String url = PathFinder.getPath(uri, target);
		    
		    
		    request.getRequestDispatcher(url+"?msg=Pesan Telah Terkirim<br/>Harap Menunggu Anda Sedang Dialihkan&redirectTo=go.prepYourRole&paramNeeded=atMenu``msg`cmd``msg`id_obj``"+target_idobj+"`nmm``"+target_nmm+"`npm``"+target_npm+"`obj_lvl``"+target_objlv+"`kdpst``"+target_kdpst).forward(request,response);

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

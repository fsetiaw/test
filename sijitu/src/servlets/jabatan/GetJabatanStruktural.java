package servlets.jabatan;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.jabatan.SearchDbJabatan;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class GetJabatanStruktural
 */
@WebServlet("/GetJabatanStruktural")
public class GetJabatanStruktural extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetJabatanStruktural() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//System.out.println("jabatam");;
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
			long target_objid = Long.parseLong((String)request.getParameter("id_obj")); 
			SearchDbJabatan sdb = new SearchDbJabatan(isu.getNpm());
			Vector v = sdb.getListTitleJabatan();
			Vector vp = Getter.getListProdi();
			Vector vkmp = Getter.getListAllKampus();
			Vector v_curr_val = sdb.getCurrentJabatan(target_objid);
			request.setAttribute("vListJabatan", v);
			request.setAttribute("vp", vp);
			request.setAttribute("vkmp",vkmp);
			request.setAttribute("v_curr_val", v_curr_val);
			//String target = Constants.getRootWeb()+"/InnerFrame/Edit/formJabatan.jsp";
			String atMenu = request.getParameter("atMenu");
			String target="";
			if(atMenu!=null && atMenu.equalsIgnoreCase("js")) {
				target = Constants.getRootWeb()+"/InnerFrame/profil_jabatan/profileJabatan.jsp";
			}
			else if(atMenu!=null && atMenu.equalsIgnoreCase("ejs")) {
				target = Constants.getRootWeb()+"/InnerFrame/profil_jabatan/formJabatan.jsp";
			}
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

package servlets.mhs.profile.kemahasiswaan;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.classPoll.SearchDbClassPoll;
import beans.dbase.dosen.SearchDbDsn;
import beans.dbase.mhs.btstu.SearchDbInfoMhsBtstu;
import beans.dbase.mhs.kurikulum.SearchDbInfoKurikulum;
import beans.dbase.mhs.kurikulum.UpdateDbInfoKurikulum;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;
/**
 * Servlet implementation class UpdateKoMhs
 */
@WebServlet("/UpdateKoMhs")
public class UpdateKoMhs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateKoMhs() {
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
			Vector v_npmhs = null;
			int updated = 0;
			//System.out.println("siap update");
			String command = "allowViewKurikulum";
			String tkn_npm = request.getParameter("tkn_npm");
			String idko = request.getParameter("idko");
			//System.out.println("tkn_npm = "+tkn_npm);
			//System.out.println("idko = "+idko);
			if(!Checker.isStringNullOrEmpty(tkn_npm)) {
				String allow_npm = null;
				StringTokenizer st =new StringTokenizer(tkn_npm,Checker.getSeperatorYgDigunakan(tkn_npm));	
				while(st.hasMoreTokens()) {
					String npmhs = st.nextToken();
					if(isu.isUsrAllowTo_vFinal(command, npmhs)) {
						if(Checker.isStringNullOrEmpty(allow_npm)) {
							allow_npm = new String(npmhs);
						}
						else {
							allow_npm = allow_npm+","+npmhs;
						}
					}
				}
				if(!Checker.isStringNullOrEmpty(allow_npm)) {
					st =new StringTokenizer(allow_npm,Checker.getSeperatorYgDigunakan(allow_npm));
					allow_npm = null;
					while(st.hasMoreTokens()) {
						String npmhs = st.nextToken();
						if(!isu.isHakAksesReadOnly(command)) {
							if(Checker.isStringNullOrEmpty(allow_npm)) {
								allow_npm = new String(npmhs);
							}
							else {
								allow_npm = allow_npm+","+npmhs;
							}
						}	
					}
					if(!Checker.isStringNullOrEmpty(allow_npm)) {
						UpdateDbInfoKurikulum udif = new UpdateDbInfoKurikulum();
						updated =  udif.setKoMhs(allow_npm, Integer.parseInt(idko) );
						v_npmhs = new Vector();
						v_npmhs.add(0,""+updated);
						v_npmhs.add(0,"TOTAL DATA UPDATED");
						v_npmhs.add(0,"600px");
						v_npmhs.add(0,"95");
						v_npmhs.add(0,"center");
						v_npmhs.add(0,"String");
						
					}
				}
				session.setAttribute("v", v_npmhs);
			}
			
					
			
			
			
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
			//String uri = request.getRequestURI();
			//String url = PathFinder.getPath(uri, target);
		//request.getRequestDispatcher(url).forward(request,response);
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

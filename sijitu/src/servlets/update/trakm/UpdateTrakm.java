package servlets.update.trakm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.mhs.SearchDbInfoMhs;
import beans.dbase.trakm.HitungKhs;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;
import beans.tools.Tool;

/**
 * Servlet implementation class UpdateTrakm
 */
@WebServlet("/UpdateTrakm")
public class UpdateTrakm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateTrakm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.
		response.setContentType("text/html");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");

		if(isu==null) {
			response.sendRedirect(Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			HitungKhs hk = new HitungKhs(isu.getNpm());
			//System.out.println("hitung khs");
			String kdpst = request.getParameter("kdpst");
			String npmhs = request.getParameter("npmhs");
			String smawl = request.getParameter("smawl");
			//System.out.println(kdpst+"`"+npmhs+"`"+smawl);
			if(!Checker.isStringNullOrEmpty(kdpst) && Checker.isStringNullOrEmpty(smawl)) {
				//ngga pake thsms berarti seluruh thsms
				SearchDbInfoMhs sdim = new SearchDbInfoMhs(isu.getNpm());
				boolean lanjut = true;
				int limit = 1000;
				int offset = 0;
				while(lanjut) {
					Vector v_npmhs = sdim.getListNpmhsGiven(kdpst, limit+1, offset);
					hk.hitungRiwayatTrakmMhs(v_npmhs);
					//System.out.println("v_size="+v_npmhs.size());
					//System.out.println("limit="+limit);
					//System.out.println("offset="+offset);
					if(v_npmhs!=null && v_npmhs.size()<=limit) {
						lanjut = false;
					}
					else {
						offset = offset + limit;
					}
				}
				/*
				String smawl_angkatan_1 = Getter.smawlAngkatanPertama(kdpst);
				String thsms_pmb = Checker.getThsmsPmb();
				while(thsms_pmb.compareToIgnoreCase(smawl_angkatan_1)>0) {
					//hk.hitungTrakmSemesteran(kdpst, smawl_angkatan_1);
					//System.out.println("smawl_angkatan_1="+smawl_angkatan_1);
					smawl_angkatan_1 = Tool.returnNextThsmsGivenTpAntara(smawl_angkatan_1);
					
				}	
				*/
			}
			else if(!Checker.isStringNullOrEmpty(kdpst) && !Checker.isStringNullOrEmpty(smawl)) {
				//System.out.println(kdpst+" angkatan "+smawl);
				SearchDbInfoMhs sdim = new SearchDbInfoMhs(isu.getNpm());
				boolean lanjut = true;
				int limit = 1000;
				int offset = 0;
				while(lanjut) {
					Vector v_npmhs = sdim.getListNpmhsGiven(kdpst, smawl, limit+1, offset);
					hk.hitungRiwayatTrakmMhs(v_npmhs);
					//System.out.println("v_size="+v_npmhs.size());
					//System.out.println("limit="+limit);
					//System.out.println("offset="+offset);
					if(v_npmhs!=null && v_npmhs.size()<=limit) {
						lanjut = false;
					}
					else {
						offset = offset + limit;
					}
				}
			}
			if(!Checker.isStringNullOrEmpty(npmhs)) {
				Vector<String> v_npmhs = new Vector<>();
				v_npmhs.add(npmhs);
				//System.out.println("npmhs="+npmhs);
				hk.hitungRiwayatTrakmMhs(v_npmhs);
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

package servlets.update.trlsm;

import java.io.IOException;
import java.util.ListIterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.mhs.SearchDbInfoMhs;
import beans.dbase.trlsm.SearchDbTrlsm;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;

/**
 * Servlet implementation class UpdateStmhsAtThsmsTidakAdaKabar
 */
@WebServlet("/UpdateStmhsAtThsmsTidakAdaKabar")
public class UpdateStmhsAtThsmsTidakAdaKabar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateStmhsAtThsmsTidakAdaKabar() {
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
			String stmhs_value = request.getParameter("stmhs");
			String target_thsms = request.getParameter("thsms");
			String target_kdpst = request.getParameter("kdpst");
			//System.out.println("im here "+target_thsms);
			
			SearchDbInfoMhs sdim = new SearchDbInfoMhs();
			SearchDbTrlsm sdt = new SearchDbTrlsm();
			UpdateDbTrlsm udt = new UpdateDbTrlsm();
			Vector v_npm = sdim.getListNpmhsYgAdaDiTrnlm(target_thsms, target_kdpst);
			if(v_npm!=null) {
				Vector v_npmhs_smawl = sdim.getInfoMhs(v_npm, "SMAWLMSMHS", "s");
				if(v_npmhs_smawl!=null) {
					v_npmhs_smawl = sdt.cekThsmsTanpaKabar(v_npmhs_smawl, target_thsms);
					if(v_npmhs_smawl!=null) {
						int updated = udt.updStmhsTanpaBerita(v_npmhs_smawl, target_kdpst, stmhs_value);
					}
				}
			}
			//System.out.println("im DONE "+target_kdpst);
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

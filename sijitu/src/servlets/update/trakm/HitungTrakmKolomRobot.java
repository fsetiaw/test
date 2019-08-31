package servlets.update.trakm;

import java.io.IOException;
import java.util.ListIterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.krklm.SearchDbKrklm;
import beans.dbase.mhs.SearchDbInfoMhs;
import beans.dbase.trakm.HitungKhs;
import beans.dbase.trakm.UpdateDbTrakm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;

/**
 * Servlet implementation class HitungTrakmKolomRobot
 */
@WebServlet("/HitungTrakmKolomRobot")
public class HitungTrakmKolomRobot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HitungTrakmKolomRobot() {
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
			//System.out.println("sampe");
			Vector vtmp = null;
			ListIterator litmp = null;
			String target_thsms = request.getParameter("thsms");
			String target_kdpst = request.getParameter("kdpst");
			SearchDbInfoMhs sdim = new SearchDbInfoMhs();
			UpdateDbTrakm udt = new UpdateDbTrakm();
			
			Vector v_npm = sdim.getListNpmhsYgAdaDiTrnlm(target_thsms, target_kdpst);
			if(v_npm!=null) {
				v_npm = sdim.getInfoMhs(v_npm, "SMAWLMSMHS`STPIDMSMHS`KRKLMMSMHS", "s`s`s");
				v_npm = sdim.getTotSmsLamaStudiDariSmawlDanTotalNonAktif(v_npm, target_thsms);
				HitungKhs hk = new HitungKhs();
				Vector v_npm_skstt = hk.hitungSksttTrakmRobotBilaSelamaLamaStudiDianggapAktif(new Vector(v_npm));
				int updated = udt.updTrakmSksttRobot(v_npm_skstt, target_thsms);
				
				hk.hitungSksemDanNlipsBasedOnTrnlmRilDanRobot(v_npm, target_thsms);
				updated = hk.sinkNlipkDanKasihNlipkRobotDiatasMinimalBilaNlipkDibawa(v_npm, target_thsms);
				/*
				ListIterator lil = v_npm.listIterator();
				while(lil.hasNext()) {
					String brs = (String)lil.next();
					//System.out.println("--"+brs);
				}
				*/
				//System.out.println("updated "+target_kdpst+"= "+updated);
				//hitung nlips dari trakm
				/*
				SearchDbKrklm sdk = new SearchDbKrklm();
				Vector v_list_krklm = sdk.getListInfoKrklm(target_kdpst);
				
				ListIterator lil = v_list_krklm.listIterator();
				while(lil.hasNext()) {
					vtmp = (Vector)lil.next();
					litmp = vtmp.listIterator();
					while(litmp.hasNext()) {
						String brs = (String)litmp.next();
						//System.out.println(brs);
					}
				}
				*/
			}
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

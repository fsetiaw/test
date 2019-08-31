package servlets.spmi.standard.manual;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.ListIterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.StringTokenizer;

import beans.dbase.spmi.SearchStandarMutu;
import beans.dbase.spmi.manual.UpdateManual;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class CopyManualForAllStd
 */
@WebServlet("/CopyManualForAllStd")
public class CopyManualForAllStd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CopyManualForAllStd() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("copying manual");
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
			/*
			 * PARAMETER YG ADA DISINI
			 * id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=plan&fwdto=dashboard_std_manual_perencanaan_v1.jsp
			 */
		//PrintWriter out = response.getWriter();
			try {
				TimeUnit.SECONDS.sleep(1);	
			}
			catch(InterruptedException ex) 
			{
			    Thread.currentThread().interrupt();
			}
			SearchStandarMutu ssm = new SearchStandarMutu();
			Vector v = ssm.getListSemuaStandar("ID_STD");
			String tkn_id_std = null;
			if(v!=null) {
				tkn_id_std = new String("");
				ListIterator li = v.listIterator();
				while(li.hasNext()) {
					tkn_id_std = tkn_id_std+(String)li.next();
					if(li.hasNext()) {
						tkn_id_std = tkn_id_std+"~";
					}
				}	
			}
			
			//status=aktif&tipe_manual=perencanaan&id_versi=<%=id_versi %>&id_tipe_std=<%=id_tipe_std %>&id_master_std=<%=id_master_std %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp%>&at_menu_dash=<%= at_menu_dash%>&fwdto=dashboard_std_manual_perencanaan_v1.jsp
			String ppepp = request.getParameter("tipe_manual");
			String id_versi = request.getParameter("id_versi");
			String id_std = request.getParameter("id_std");
			UpdateManual um = new UpdateManual();
			int updated = um.copyManual(ppepp, id_versi, id_std, tkn_id_std);
			updated = updated + um.copyRiwayatKegiatanManual(ppepp, id_versi, id_std, tkn_id_std);
			
			
			String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher("get.prepInfoMan_v1").forward(request,response);
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

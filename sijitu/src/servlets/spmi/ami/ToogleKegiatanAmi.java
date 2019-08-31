package servlets.spmi.ami;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.ListIterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.StringTokenizer;

import beans.dbase.spmi.ToolSpmi;
import beans.dbase.spmi.riwayat.ami.UpdateAmi;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class ToogleKegiatanAmi
 */
@WebServlet("/ToogleKegiatanAmi")
public class ToogleKegiatanAmi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToogleKegiatanAmi() {
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
			String id_ami = (String) request.getParameter("id_ami");
			String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
			StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
			String kdpst = st.nextToken();
			String nmpst = st.nextToken();
			String kdkmp = st.nextToken();//ngga kepake bis diignore
			String kode_activity = (String)request.getParameter("kode_activity");
			String tgl_plan = (String)request.getParameter("tgl_plan");
			String ketua_tim = (String)request.getParameter("ketua_tim");
			String anggota_tim = (String)request.getParameter("anggota_tim");
			String id_cakupan_std = (String)request.getParameter("id_cakupan_std");
			String ket_cakupan_std = (String)request.getParameter("ket_cakupan_std");
			String tgl_ril = (String)request.getParameter("tgl_ril");
			String tgl_ril_done = (String)request.getParameter("tgl_ril_done");
			
			session.setAttribute("id_ami",id_ami);
			session.setAttribute("kode_activity",kode_activity);
			session.setAttribute("tgl_plan",tgl_plan);
			session.setAttribute("ketua_tim",ketua_tim);
			session.setAttribute("anggota_tim",anggota_tim);
			session.setAttribute("id_cakupan_std",id_cakupan_std);
			session.setAttribute("ket_cakupan_std",ket_cakupan_std);
			session.setAttribute("tgl_ril",tgl_ril);
			session.setAttribute("tgl_ril_done",tgl_ril_done);

			ToolSpmi ts = new ToolSpmi();
			String status = ts.getStatusKegiatanAmi(Integer.parseInt(id_ami));
			//System.out.println("status="+status);
			UpdateAmi ua = new UpdateAmi();
			if(status.equalsIgnoreCase("belum")) {
				//System.out.println("pintu 1");
				ua.startKegiatanAmi(Integer.parseInt(id_ami),kdpst);
			}
			else if(status.equalsIgnoreCase("sedang")) {
				//System.out.println("pintu 2");
				int i = ua.stopKegiatanAmi(Integer.parseInt(id_ami));
				if(i>0) {
					ua.updateNlakhAmi(Integer.parseInt(id_ami));
				}
			}
			//System.out.println("pintu 3");
			//System.out.println("id_ami1="+id_ami);
			//System.out.println("kdpst_nmpst_kmp1="+kdpst_nmpst_kmp);
			/*
			PrintWriter out = response.getWriter();
			String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url).forward(request,response);
			*/
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

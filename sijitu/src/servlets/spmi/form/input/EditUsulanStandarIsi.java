package servlets.spmi.form.input;


import java.io.PrintWriter;
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
import beans.dbase.spmi.form.UpdateForm;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UsulanStandarIsi
 */
@WebServlet("/EditUsulanStandarIsi")
public class EditUsulanStandarIsi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUsulanStandarIsi() {
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
			String id_std_isi = request.getParameter("id_std_isi");
			String[]job = request.getParameterValues("job");
			String isi_std = request.getParameter("isi_std");
			String[]doc = request.getParameterValues("doc");
			String rasionale = request.getParameter("rasionale");
			String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
			StringTokenizer st = null;
			if(kdpst_nmpst_kmp!=null && kdpst_nmpst_kmp.contains("-")) {
				st = new StringTokenizer(kdpst_nmpst_kmp,"-");
			}
			else {
				st = new StringTokenizer(kdpst_nmpst_kmp,"`");
			}
			String kdpst = st.nextToken();
			String nmpst = st.nextToken();
			String kdkmp = st.nextToken();
			//System.out.println("--id_std_isi="+id_std_isi);
			//System.out.println("--job[0]="+job[0]);
			//System.out.println("--isi_std="+isi_std);
			//System.out.println("--rasionale="+rasionale);
			//System.out.println("--doc="+doc);
			
			UpdateForm uf = new UpdateForm();
			int updated = uf.updateNuStandarIsi(id_std_isi, job, isi_std, doc, rasionale);
			
			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_creator/form/std_isi/input_form.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?updated="+updated).forward(request,response);
			
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

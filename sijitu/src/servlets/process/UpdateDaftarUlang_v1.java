package servlets.process;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.mhs.*;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;
/**
 * Servlet implementation class UpdateDaftarUlang_v1
 */
@WebServlet("/UpdateDaftarUlang_v1")
public class UpdateDaftarUlang_v1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateDaftarUlang_v1() {
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
			String target_thsms=request.getParameter("target_thsms");
			String npmhs=request.getParameter("npmhs");
			//boolean all_approved = Boolean.parseBoolean(request.getParameter("all_approved"));
		//	boolean show_at_mhs = Boolean.parseBoolean(request.getParameter("show_at_mhs"));
			String scope_cmd=request.getParameter("scope_cmd");
			String table_rule_nm=request.getParameter("table_rule_nm");
			String at_kmp=request.getParameter("at_kmp");
			UpdateDbInfoMhs udb = new UpdateDbInfoMhs(isu.getNpm());
			String info_mhs = request.getParameter("info_mhs");
			String tkn_job_approval_needed = request.getParameter("tkn_job_approval_needed");
			String cur_approvee_id = request.getParameter("cur_approvee_id");
			String cur_approvee_job = request.getParameter("cur_approvee_job");
			//System.out.println("is all_approved="+all_approved);
			//System.out.println("info_mhs="+info_mhs);
			//System.out.println("cur_approvee_id="+cur_approvee_id);
			//System.out.println("cur_approvee_job="+cur_approvee_job);
			
			
			/*
			 * fungsi dibawah ini juga meriksa apa sebelumnya jabatan sudah approved
			 */
			udb.updateStatusDaftarUlangTable(target_thsms, npmhs, isu.getNpm(), cur_approvee_job, cur_approvee_id, tkn_job_approval_needed);
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
			//String uri = request.getRequestURI();
			//String url = PathFinder.getPath(uri, target);

			request.getRequestDispatcher("get.whoRegisterWip").forward(request,response);

		}
		//System.out.println("dafatrulang_v1");
		
		
		/*
		String from = request.getParameter("from");
		String[]opt1 = request.getParameterValues("option1");
		String thsms_regis = (String)request.getParameter("thsms_regis");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		UpdateDbInfoMhs udb = new UpdateDbInfoMhs(isu.getNpm());
		if(from!=null && from.equalsIgnoreCase("notification")) {
			if(opt1!=null) {
				udb.updateCekListTabelDaftarUlang(opt1, thsms_regis);
			}
		}
		else {
			if(opt1!=null) {
				//System.out.println("opt1 Not Null");
				udb.setNullCekListTabelDaftarUlang(opt1, thsms_regis);
				udb.updateCekListTabelDaftarUlang(opt1, thsms_regis);
			}
			else {
				//System.out.println("Null");
				udb.setNullCekListTabelDaftarUlang(opt1, thsms_regis);
			}	
		}
		if(from!=null && from.equalsIgnoreCase("notification")) {
			request.getRequestDispatcher("get.whoRegisterWip").forward(request,response);	
		}
		else {
			request.getRequestDispatcher("get.whoRegister").forward(request,response);
		}
		*/
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}

package servlets.spmi.update;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.spmi.UpdateQandA;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateTanyaJawab
 */
@WebServlet("/UpdateTanyaJawab")
public class UpdateTanyaJawab extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateTanyaJawab() {
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
			String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
			String id_versi = request.getParameter("id_versi");
			String id_std_isi = request.getParameter("id_std_isi");
			String id_std = request.getParameter("id_std");
			String id_master_std = request.getParameter("id_master_std");
			String id_tipe_std = request.getParameter("id_tipe_std");
			String id_question = request.getParameter("id_question");
			String at_menu_dash = request.getParameter("at_menu_dash");
			String at_menu_kendal = request.getParameter("at_menu_kendal");
			String question = request.getParameter("question");
			String[]answer = request.getParameterValues("answer");
			String[]nilai = request.getParameterValues("nilai");
			
			
			if(!Checker.isStringNullOrEmpty(question)) {
				UpdateQandA uqa = new UpdateQandA();
				int updated = uqa.updateQuestionAnswerNilai(id_std_isi, id_question, question, answer, nilai);
			}
			
			String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher("get.prepInfoMan_v1?id_versi="+id_versi+"&id_tipe_std="+id_tipe_std+"&id_master_std="+id_master_std+"&id_std="+id_std+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&at_menu_dash="+at_menu_dash+"&fwdto=dashboard_std_manual_pengendalian_eval_v1.jsp&darimana=prep").forward(request,response);
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

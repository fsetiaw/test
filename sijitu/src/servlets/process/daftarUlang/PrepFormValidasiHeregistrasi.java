package servlets.process.daftarUlang;

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

import beans.dbase.daftarUlang.SearchDbInfoDaftarUlangTable;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PrepFormValidasiHeregistrasi
 */
@WebServlet("/PrepFormValidasiHeregistrasi")
public class PrepFormValidasiHeregistrasi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepFormValidasiHeregistrasi() {
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
			//System.out.println("iam in");
			String scope_cmd = "hasHeregitrasiMenu";
			String table_rule_nm = "HEREGISTRASI_RULES";
			//PrintWriter out = response.getWriter();
			String target_idobj = request.getParameter("idobj");
			String target_kmp = request.getParameter("kmp");
			//System.out.println("idobj ="+target_idobj);
			//System.out.println("kmp ="+target_kmp);
			String target_thsms = Checker.getThsmsHeregistrasi();
			Vector v_scope_id = new Vector();
			Vector vtmp = new Vector();
			String tkn_approvee_jabatan = Checker.listVerificatorHeregistrasi(target_thsms, Integer.parseInt(target_idobj));
			//System.out.println("heregistrasi approvee="+tkn_approvee_jabatan);
			String tkn_id_approvee = Checker.listIdobjForJabatan(tkn_approvee_jabatan, Integer.parseInt(target_idobj));
			//System.out.println("tkn_id_approvee approvee="+tkn_id_approvee);
			request.setAttribute("tkn_jabatan_approvee", tkn_approvee_jabatan);
			request.setAttribute("tkn_id_approvee", tkn_id_approvee);
			vtmp.add(target_kmp+"`"+target_idobj);
			v_scope_id.add(vtmp);
			SearchDbInfoDaftarUlangTable sddu = new SearchDbInfoDaftarUlangTable(isu.getNpm());
			Vector v = sddu.getInfoDaftarUlangFilterByScopeFinale_v1(target_thsms,v_scope_id,true);
			request.setAttribute("v_list", v);
			String target = Constants.getRootWeb()+"/InnerFrame/DaftarUlang/indexDaftarUlang_v2.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?at_kmp="+target_kmp+"&target_thsms="+target_thsms+"&scope_cmd="+scope_cmd+"&table_rule_nm="+table_rule_nm).forward(request,response);
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

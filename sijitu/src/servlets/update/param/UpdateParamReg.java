package servlets.update.param;

import java.io.IOException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.Param.UpdateDbParam;
import beans.dbase.daftarUlang.UpdateDaftarUlangTable;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Tool;

/**
 * Servlet implementation class UpdateParamReg
 */
@WebServlet("/UpdateParamReg")
public class UpdateParamReg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateParamReg() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
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
			try {
				//System.out.println("okay sini");
				String[]urutan_job = request.getParameterValues("urutan_job");
				String[]job = request.getParameterValues("job");
				String[]prodi = request.getParameterValues("prodi");
				String[]kmp = request.getParameterValues("kmp");
				String target_thsms = request.getParameter("target_thsms");
				String table_rule_name = request.getParameter("table_rule_name");
				String scopeCmd = request.getParameter("scopeCmd");
				
				if(job!=null && prodi!=null && kmp!=null) {
					UpdateDbParam udp = new UpdateDbParam(isu.getNpm());
					UpdateDaftarUlangTable udt = new UpdateDaftarUlangTable(isu.getNpm());
					
					//udp.updateKelasKuliahRulesUrutan(urutan_job,job,prodi,kmp,target_thsms);
					udp.updateTableRules(urutan_job,job,prodi,kmp,target_thsms,table_rule_name);
					int upd = udt.syncDaftarUlangDenganHeregistrasiRule(target_thsms);
					//update table daftar ulang
					
					//System.out.println("upd="+upd);
					//System.out.println("</br>prod="+prodi[0]);
					//System.out.println("</br>kmp="+kmp[0]);
				}	
				else {
					//forward ke dashPindahProdi
				}
				//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp"; 
				//String uri = request.getRequestURI(); 
				//String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher("prep.prepParamPengajuanRegistrasiRules?atMenu="+scopeCmd).forward(request,response);
			}
			catch(Exception e) {}
			

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

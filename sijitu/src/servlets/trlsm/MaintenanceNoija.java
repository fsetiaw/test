package servlets.trlsm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.trlsm.SearchDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class MaintenanceNoija
 */
@WebServlet("/MaintenanceNoija")
public class MaintenanceNoija extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MaintenanceNoija() {
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
			//System.out.println("ayo");
			String thsms = request.getParameter("thsms");
			String filter_tipe = request.getParameter("filter_tipe");
			Vector v_scope = isu.returnScopeProdiOnlySortByKampusWithListIdobj("s");
			boolean full_editor = false;
			if(isu.isAllowTo("allowCetakIjazah")>0) {
				full_editor = true;
			}
			
			
			SearchDbTrlsm sdt = new SearchDbTrlsm();
			if(filter_tipe.equalsIgnoreCase("0")) {
				v_scope = Converter.convertVscopeidToKdpst(v_scope);
				if(v_scope!=null && v_scope.size()>0) {
					
					Vector v_rs = null;
					if(Checker.isStringNullOrEmpty(thsms)) {
						v_rs = sdt.getDataTrlsm(v_scope, null, full_editor);
					}
					else {
						v_rs = sdt.getDataTrlsm(v_scope, thsms, full_editor);
					}
					 
					session.setAttribute("v_rs", v_rs);
				}
				
			}
			else if(filter_tipe.equalsIgnoreCase("1")) {
				v_scope = Converter.convertVscopeidToKdpst(v_scope);
				if(v_scope!=null && v_scope.size()>0) {
					
					Vector v_rs = null;
					if(Checker.isStringNullOrEmpty(thsms)) {
						v_rs = sdt.cekNoijaEmpty(v_scope, null, full_editor);
					}
					else {
						v_rs = sdt.cekNoijaEmpty(v_scope, thsms, full_editor);
					}
					 
					session.setAttribute("v_rs", v_rs);
				}
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

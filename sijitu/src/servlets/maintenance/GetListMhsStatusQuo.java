package servlets.maintenance;

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

import beans.dbase.mhs.SearchDbInfoMhs;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Tool;

/**
 * Servlet implementation class GetListMhsStatusQuo
 */
@WebServlet("/GetListMhsStatusQuo")
public class GetListMhsStatusQuo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetListMhsStatusQuo() {
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
			String stmhs = request.getParameter("stmhs");
			String kdpst = request.getParameter("kdpst");
			String thsms = request.getParameter("thsms");
			//System.out.println("stmhs="+stmhs);
			String thsms_1 = Tool.returnPrevThsmsGivenTpAntara(thsms);
			//System.out.println("thsms = "+thsms);
			SearchDbInfoMhs sdim = new SearchDbInfoMhs();
			//1. cek mhs aktif @thsms-1
			Vector v_npmhs = sdim.getMhsAktifStatusQuo(thsms);
			if(Checker.isStringNullOrEmpty(stmhs)) {
				//mode show list
				if(v_npmhs!=null && v_npmhs.size()>0) {
					//kdpst+"`"+npmhs+"`"+nmmhs+"`"+nimhs+"`"+smawl+"`"+stpid+"`"+angel+"`"+kdjen
					
					//System.out.println("size="+v_npmhs.size());
					v_npmhs.add(0,"PRODI`NPM`NAMA`NIM`SMAWL`STPID`VALID`KDJEN");
					v_npmhs.add(0,"null");
					v_npmhs.add(0,"5`15`40`15`5`5`5`5");
					v_npmhs.add(0,"center`center`left`center`center`center`center`center");
					v_npmhs.add(0,"String`String`String`String`String`String`String`String");
				}
				else {
					v_npmhs=null;
				}
				session.setAttribute("v", v_npmhs);
			}
			else {
				//System.out.println("siap update");
				//mode update status 
				if(v_npmhs!=null && v_npmhs.size()>0) {
					UpdateDbTrlsm udt = new UpdateDbTrlsm();
					//kdpst+"`"+npmhs+"`"+nmmhs+"`"+nimhs+"`"+smawl+"`"+stpid+"`"+angel+"`"+kdjen
					//1. reduce vector hanya butuh kdpst & npmhs 
					ListIterator li = v_npmhs.listIterator();
					while(li.hasNext()) {
						String brs = (String)li.next();
						String tkn_kdpst = Tool.getTokenKe(brs, 1, "`");
						String tkn_npmhs = Tool.getTokenKe(brs, 2, "`");
						li.set(tkn_kdpst+"`"+tkn_npmhs);
						//System.out.println(tkn_kdpst+"`"+tkn_npmhs);
					}
					int i = udt.updStmhsTanpaBerita_v1(v_npmhs, stmhs, thsms);
					v_npmhs = new Vector();
					v_npmhs.add(0,""+i);	
					v_npmhs.add(0,"TOTAL DATA UPDATED");
					v_npmhs.add(0,"600px");
					v_npmhs.add(0,"95");
					v_npmhs.add(0,"center");
					v_npmhs.add(0,"String");
					
				}
				else {
					v_npmhs=null;
				}
				
				session.setAttribute("v", v_npmhs);
			}
			//System.out.println("v_npmhs size = "+v_npmhs.size());
			//else {
			//System.out.println("size=0");
			//}
			
			//PrintWriter out = response.getWriter();
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
			//String uri = request.getRequestURI();
			//String url = PathFinder.getPath(uri, target);
			//request.getRequestDispatcher(url).forward(request,response);
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

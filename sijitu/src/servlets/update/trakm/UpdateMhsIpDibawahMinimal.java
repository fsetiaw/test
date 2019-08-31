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

import beans.dbase.classPoll.SearchDbClassPoll;
import beans.dbase.dosen.SearchDbDsn;
import beans.dbase.mhs.btstu.SearchDbInfoMhsBtstu;
import beans.dbase.mhs.kurikulum.SearchDbInfoKurikulum;
import beans.dbase.trakm.SearchDbTrakm;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateMhsIpDibawahMinimal
 */
@WebServlet("/UpdateMhsIpDibawahMinimal")
public class UpdateMhsIpDibawahMinimal extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMhsIpDibawahMinimal() {
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
			Vector v_npmhs = null;
			//System.out.println("masuk");;
			String cmd_scope = request.getParameter("cmd_scope");
			Vector v_scope_id = null;
			if(!Checker.isStringNullOrEmpty(cmd_scope)) {
				v_scope_id = isu.returnScopeProdiOnlySortByKampusWithListIdobj(cmd_scope);
			}
			String ipk_min = request.getParameter("ipk_min");
			String nu_ipk = request.getParameter("nu_ipk");
			String target_thsms = request.getParameter("thsms");
			String[]col_robot = request.getParameterValues("col_robot");
			String[]angel = request.getParameterValues("angel");
			boolean mhs_ril = false;
			boolean nilai_ril = false;
			System.out.println("ipk_min"+ipk_min);;
			System.out.println("nu_ipk"+nu_ipk);;
			System.out.println("target_thsms"+target_thsms);;
			//System.out.println(target_thsms+" -> "+nilai_min);;
			if(col_robot==null) {
				System.out.println("nilai asli");
				nilai_ril = true;
			}
			else {
				System.out.println("nilai robot");
			}
			
			if(angel==null) {
				System.out.println("mhs rill");
				mhs_ril = true;
			}
			else {
				System.out.println("malaikat");
			}
			
			if(v_scope_id!=null && v_scope_id.size()>0) {
				ListIterator li = v_scope_id.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					System.out.println("baris= "+brs);
				}
				//SearchDbTrakm sdt = new SearchDbTrakm();
				//v_npmhs = sdt.getListMhsDgnIpDibawahMinimal(v_scope_id, target_thsms, tipe_ip, mhs_ril, nilai_ril, Double.parseDouble(nilai_min));	
			}
			/*
			if(v_npmhs!=null) {
				//kdpst+"`"+npmhs+"`"+nmmhs+"`"+nimhs+"`"+smawl+"`"+stpid+"`"+angel+"`"+kdjen

				//System.out.println("size="+v_npmhs.size());
				v_npmhs.add(0,"PRODI`NPM`NAMA`IPS`IPK");
				v_npmhs.add(0,"800px");
				v_npmhs.add(0,"5`15`45`15`15");
				v_npmhs.add(0,"center`center`left`center`center");
				v_npmhs.add(0,"String`String`String`String`String");
			}
			session.setAttribute("v", v_npmhs);
			*/
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

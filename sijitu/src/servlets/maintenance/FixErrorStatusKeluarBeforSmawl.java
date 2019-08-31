package servlets.maintenance;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ListIterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.mhs.SearchDbInfoMhs;
import beans.dbase.mhs.maintenance.MaintenaceSearchDb;
import beans.dbase.mhs.maintenance.MaintenanceUpdateDb;
import beans.dbase.overview.GetSebaranTrlsm;
import beans.dbase.trakm.CekTotKrs;
import beans.dbase.trakm.SearchDbTrakm;
import beans.dbase.trlsm.SearchDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class FixErrorStatusKeluarBeforSmawl
 */
@WebServlet("/FixErrorStatusKeluarBeforSmawl")
public class FixErrorStatusKeluarBeforSmawl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FixErrorStatusKeluarBeforSmawl() {
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
		//System.out.println("masukin");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			int upd=0;
			String stmhs = request.getParameter("stmhs");
			String smawl = request.getParameter("smawl");
			//GetSebaranTrlsm gst = new GetSebaranTrlsm();
			//Vector v = gst.getListNpmMhsAktif(thsms,kdpst);
			//System.out.println("mhs aktif = "+v.size());
			MaintenaceSearchDb msd = new MaintenaceSearchDb(); 
			Vector v = msd.getListNpmMhsYgAdaStatusTrlsmSebelumSmawl(stmhs, smawl);
			if(v!=null && v.size()>0) {
				MaintenanceUpdateDb usd = new MaintenanceUpdateDb(); 
				upd = usd.deleteErrorRec(v);
				
			}
			v = new Vector();
			v.add(""+upd);
			//System.out.println("mhs aktif2 = "+v.size());
			if(v!=null) {
				//li.set(kdpst+"`"+npmhs1+"`"+nmmhs+"`"+nimhs+"`"+stmhs1+"`"+thsms1);
				v.add(0,"TOTAL RECORD UPDATED");
				v.add(0,"800px"); 
				v.add(0,"100"); //total = 95, krn 5% jatah untuk norut
				v.add(0,"center");
				v.add(0,"String");
				
				session.setAttribute("v", v);
			}
/* 
			SearchDbInfoMhs sdm = new SearchDbInfoMhs();
			Vector v_trnlm = null, v_trlsm = null;
			if(Checker.isStringNullOrEmpty(kdpst)) {
				v_trnlm = sdm.getListNpmhsYgAdaDiTrnlm(thsms);	
			}
			else {
				v_trnlm = sdm.getListNpmhsYgAdaDiTrnlm(thsms,kdpst);
			}
			SearchDbTrlsm sdt = new SearchDbTrlsm();
			v_trlsm = sdt.getListNpmMhsCutiDanNonAktif(thsms,kdpst);
			
*/
			/*
			Vector v_scope = isu.returnScopeProdiOnlySortByKampusWithListIdobj_v1("s");
			if(v_scope!=null && v_scope.size()>0) {
				v_scope=  Converter.convertVscopeidToKdpst(v_scope);
				
				//CekTotKrs ctk = new CekTotKrs(); harusnya gabung searchdb
				SearchDbTrakm ctk = new SearchDbTrakm();
				Vector v_rs = ctk.getNpmhsGivenTotalKrsCondition(thsms, sks_condition, v_scope);
			*/
				/*
				
				*/
			//}
			
			
			//PrintWriter out = response.getWriter();
			//String target = Constants.getRootWeb()+"/InnerFrame/InnerFrame/sql/ResultSet.jsp";
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

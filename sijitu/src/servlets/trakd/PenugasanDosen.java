package servlets.trakd;

import java.io.IOException;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.classPoll.SearchDbClassPoll;
import beans.dbase.dosen.UpdateDbDsn;
import beans.dbase.dosen.SearchDbDsn;
import beans.dbase.mhs.btstu.SearchDbInfoMhsBtstu;
import beans.dbase.mhs.kurikulum.SearchDbInfoKurikulum;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class PenugasanDosen
 */
@WebServlet("/PenugasanDosen")
public class PenugasanDosen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PenugasanDosen() {
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
			//System.out.println("prep surat tugas");
			String mode = request.getParameter("mode");
			String target_thsms = request.getParameter("target_thsms");
			if(!Checker.isStringNullOrEmpty(mode)) {
				
				//System.out.println("update mode");
				
	       		String[]target_kdpst = request.getParameterValues("target_kdpst");
	       		String[]target_nodos = request.getParameterValues("target_nodos");
	       		String[]target_nmdos = request.getParameterValues("target_nmdos");
	       		String[]surat = request.getParameterValues("surat");
	       		
	       		UpdateDbDsn udd = new UpdateDbDsn();
	       		
	       		udd.updateSuratTugasDosenAjarTrakd_v1(target_thsms,target_kdpst,target_nodos,surat);
	       		//System.out.println("target_kdpst = "+target_kdpst.length);
	       		//System.out.println("target_nodos = "+target_nodos.length);
	       		//System.out.println("target_nmdos = "+target_nmdos.length);
	       		//System.out.println("surat = "+surat.length);
			}
			
			SearchDbDsn sdb = new SearchDbDsn();
			Vector v_scope_id = isu.returnScopeProdiOnlySortByKampusWithListIdobj("s");
			Vector v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
			Vector v = sdb.getListDosenPengajar(v_scope_kdpst,target_thsms);
			request.setAttribute("v_list_dsn", v);
			Vector v_distinct = null;
			if(v!=null && v.size()>0) {
				v_distinct = new Vector();
				v_distinct = sdb.showDistinctDsn(v);
			}
			request.setAttribute("v_list_distinct_dsn", v_distinct);
			
			
			//PrintWriter out = response.getWriter();
			String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/penugasan/formPenugasan.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url).forward(request,response);
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

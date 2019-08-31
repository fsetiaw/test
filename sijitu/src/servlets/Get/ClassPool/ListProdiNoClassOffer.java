package servlets.Get.ClassPool;

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

import beans.dbase.classPoll.SearchDbClassPoll;
import beans.dbase.notification.SearchDbMainNotification;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class ListProdiNoClassOffer
 */
@WebServlet("/ListProdiNoClassOffer")
public class ListProdiNoClassOffer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListProdiNoClassOffer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		//System.out.println("no class");
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
			String target_thsms = Checker.getThsmsBukaKelas();
			
			SearchDbClassPoll sdcp = new SearchDbClassPoll(isu.getNpm());
			String scope_cmd = request.getParameter("scope_cmd");
			Vector v_scope_id = isu.returnScopeProdiOnlySortByKampusWithListIdobj(scope_cmd);
			SearchDbMainNotification sdmn = new SearchDbMainNotification(isu.getNpm());
			//Vector v = sdcp.listProdiYgBelumMengajukanKelasPerkuliahan(v_scope_id, target_thsms);
			Vector v = (Vector)session.getAttribute("v_list_prodi_no_pengajuan_kelas");
			/*
			if(v==null && v.size()<1) {
				SearchDbMainNotification sdm = new SearchDbMainNotification(isu.getNpm());
				Vector v_scope_kdpst = isu.getScopeKdpst_vFinal(scope_cmd);
				v = sdm.listProdiYgBelumMengajukanKelasPerkuliahan_v1(v_scope_kdpst, target_thsms, 0);
			}
			*/
			String target="",url="";
			//if(v!=null && v.size()>0) {
				/*
				//ubah ke format 
				
				//kmp+"`"+kdpst+"`"+nmpst+"`"+kdjen+"`"+id
				ListIterator li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					//System.out.println("brs00="+brs);
					StringTokenizer st = new StringTokenizer(brs,"~");
					String id_obj = st.nextToken();
					String kdpst = st.nextToken();
					String nmpst = st.nextToken();
					String obj_lvl = st.nextToken();
					String kdjen = st.nextToken();
					String kdkmp = st.nextToken();
					//System.out.println("kdkmp="+kdkmp);
					String kmpdom = st.nextToken();
					String ket_jen = st.nextToken();
					li.set(kdkmp+"`"+kdpst+"`"+nmpst+"`"+kdjen+"`"+id_obj);
				}
				*/
				target = Constants.getRootWeb()+"/InnerFrame/Akademik/kelas_perkuliahan/listProdiNoClass.jsp";
				String uri = request.getRequestURI(); 
				url = PathFinder.getPath(uri, target);
				//request.setAttribute("v_list_prodi_no_class",v);
			//}
			//else {
				//skip ke page berikutnya
				/*
				 * NOTE: TIDAK BOLEH SAMPE SINI KRN KALO NGGA ADA KELAS NO PENGAJUAN ICON DI HOME TIDAK BOLEH NONGOL
				 * 
				url="get.listScope?scope=reqBukaKelas&callerPage=null&cmd=bukakelas&atMenu=bukakelas&backTo=get.notifications";
				*/
			//}
			request.getRequestDispatcher(url+"?target_thsms="+target_thsms).forward(request,response);

		}
		
		//if(sdmn.getNotificationPengajuanCuti()) {
			
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

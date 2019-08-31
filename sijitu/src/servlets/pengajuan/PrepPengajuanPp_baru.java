package servlets.pengajuan;

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
import beans.dbase.pengajuan.pindah_prodi.SearchDbPp;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.PathFinder;
import beans.tools.Tool;

/**
 * Servlet implementation class PrepPengajuanPp
 */
@WebServlet("/PrepPengajuanPp_baru")
public class PrepPengajuanPp_baru extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepPengajuanPp_baru() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here
			String cmd = request.getParameter("cmd");
			String npmhs = request.getParameter("npm");
			String scope = request.getParameter("scope");
			//System.out.println("pengajuan scope baru = "+scope);
			String full_table_nm = request.getParameter("table");
			boolean show = false;
			if(npmhs==null || Checker.isStringNullOrEmpty(npmhs)) {
					//yg manggil dr home = notification
				npmhs = isu.getNpm();
				show = true;
			}
			String kdpst = request.getParameter("kdpst");
			String smawl = Getter.getSmawlCivitas(npmhs);
			String target_thsms = request.getParameter("target_thsms");
				//Vector vListThsms = Tool.returnTokensListThsmsTpAntara(smawl, Checker.getThsmsKrs());
				//get status aktif pe thsms
				//SearchDbInfoMhs sdb = new SearchDbInfoMhs(isu.getNpm());
				//Vector vThsmsStmhs = sdb.getStatusMhsPerThsms(vListThsms, npmhs);
				//session.setAttribute("vThsmsStmhs", vThsmsStmhs);
				/*cek statusnya saat ini
				 * 1. BILA ADA KRS BERARTI AKTIF
				 * 2. BILA TIDAK ADA CEK TABLE TRLSM
				 */
				//ListIterator li = vThsmsStmhs.listIterator();
				//while(li.hasNext()) {
				//	//System.out.println((String)li.next());
				//}
				
				/*
				 * yg manggil ini dari inner menu mahasiswa
				 * untuk memperlihatkan apakah ada riwayat pengajuan cuti utk thsms terkait
				 * jadi yg bisa liat adalah target_obj_id dan yg ada scopenya mencakup
				 * kdpst mhs terkait
				 */
				//get scope pengajuan
			//System.out.println("-1-");
			boolean boleh_liat_berdasarkan_scope = false;
			Vector vScope = isu.getScopeUpd11Jan2016ProdiOnly(scope);
			if(vScope==null) {
				boleh_liat_berdasarkan_scope = false;
			}
			else {
				ListIterator li = vScope.listIterator();
				while(li.hasNext() && !boleh_liat_berdasarkan_scope) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs);
					if(st.countTokens()==1) {
						//own
						if(npmhs.equalsIgnoreCase(isu.getNpm())) {
							boleh_liat_berdasarkan_scope = true;
						}
					}
					else {
						st.nextToken();
						String scope_kdpst = st.nextToken();
						if(scope_kdpst.equalsIgnoreCase(kdpst)) {
							boleh_liat_berdasarkan_scope = true;
						}	
					}
					
						//System.out.println("scope-"+brs);
				}
			}
			//System.out.println("-2-");
			Vector v = null;
			boolean am_i_stu = Checker.am_i_stu(session);
				//if(am_i_stu) {
			
			Vector v_tipe_pengajuan_scope = isu.returnScopeProdiOnlySortByKampusWithListIdobj(scope);
			SearchDbPp sdc = new SearchDbPp(isu.getNpm());
			v = sdc.getStatusPpRequest_v2(target_thsms, npmhs, show, am_i_stu, boleh_liat_berdasarkan_scope, v_tipe_pengajuan_scope, full_table_nm );	
			//System.out.println("-3-");	
			//if(v==null) {
					//System.out.println("its null");
				//}
				//else {
					//System.out.println("its not null");
				//}
				//}
				//else {
				//	v = sdc.getStatusCutiRequest(target_thsms, false);	
				//}
				
				//System.out.println("v sini");

			request.setAttribute("vReqStat", v);
			String folder_pengajuan = request.getParameter("folder_pengajuan");
			String target = Constants.getRootWeb()+"/InnerFrame/Pengajuan/"+folder_pengajuan+"/dash_pp_baru.jsp";
			if(cmd!=null && cmd.startsWith("edit_")) {
				target = Constants.getRootWeb()+"/InnerFrame/Pengajuan/"+folder_pengajuan+"/dash_pp_edit.jsp";
			}
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?backToHome="+show+"&smawl="+smawl+"&target_thsms="+target_thsms).forward(request,response);
			//request.getRequestDispatcher(url+"?backToHome="+show+"&smawl="+smawl).forward(request,response);

		}
		/*
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
			request.getRequestDispatcher(url+"?backToHome="+show+"&smawl="+smawl).forward(request,response);
		}
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

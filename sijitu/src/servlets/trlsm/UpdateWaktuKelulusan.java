package servlets.trlsm;

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

import beans.dbase.pengajuan.pindah_prodi.SearchDbPp;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateWaktuKelulusan
 */
@WebServlet("/UpdateWaktuKelulusan")
public class UpdateWaktuKelulusan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateWaktuKelulusan() {
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
			String target_thsms = request.getParameter("thsms_pengajuan");
			String tgl_kelulusan = request.getParameter("tgl_kelulusan");
			String id_obj = request.getParameter("id_obj");
			String nmm = request.getParameter("nmm");
			String npm = request.getParameter("npm");
			String obj_lvl = request.getParameter("obj_lvl");
			String kdpst = request.getParameter("kdpst");
			//System.out.println("kdpst11="+kdpst);
			String cmd = request.getParameter("cmd");
			String folder_pengajuan = (String)session.getAttribute("folder_pengajuan");
			//System.out.println("folder_pengajuan11="+folder_pengajuan);
			String scope = request.getParameter("scope");
			String table = (String)session.getAttribute("nama_table");
			String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
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
						if(npm.equalsIgnoreCase(isu.getNpm())) {
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
			boolean show = false;
			boolean am_i_stu = Checker.am_i_stu(session);
			if(npm==null || Checker.isStringNullOrEmpty(npm)) {
					//yg manggil dr home = notification
				npm = isu.getNpm();
				show = true;
			}
			String msg = "update data berhasil";
			String thsms_pengajuan = Checker.getThsmsPengajuanStmhs();
			
			Vector v_tipe_pengajuan_scope = isu.returnScopeProdiOnlySortByKampusWithListIdobj(scope);
			SearchDbPp sdc = new SearchDbPp(isu.getNpm());
			Vector v = sdc.getStatusPpRequest_v2(target_thsms, npm, show, am_i_stu, boleh_liat_berdasarkan_scope, v_tipe_pengajuan_scope, table );	
			UpdateDbTrlsm udt = new UpdateDbTrlsm();
			if(target_thsms.compareToIgnoreCase(thsms_pengajuan)<0) {
				msg = "Anda tidak dapat merubah thsms lulusan < "+thsms_pengajuan;
				msg = msg+"<br>Karena dapat menyebabkan konflik data di PDDIKTI bila laporan "+thsms_pengajuan+" sudah terlapor";
			}
			else {
				int upd = udt.updWaktuKelulusan(npm, target_thsms, tgl_kelulusan);
				if(upd<1) {
					msg = "update data GAGAL, harap hubungi ADMIN.<br>Sorry ya gaes";
				}
			}
			
			request.setAttribute("vReqStat", v);
			request.getRequestDispatcher("go.moPp?msg="+msg+"&target_thsms="+target_thsms+"&id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd="+cmd+"&folder_pengajuan="+folder_pengajuan+"&scope="+scope+"&table="+table).forward(request,response);
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

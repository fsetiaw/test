package servlets.spmi.update;

import java.io.IOException;
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
import beans.dbase.spmi.UpdateParamMutu;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class TambahParamMutu
 */
@WebServlet("/TambahParamMutu")
public class TambahParamMutu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TambahParamMutu() {
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
		//PrintWriter out = response.getWriter();
			//System.out.println("sip");
			String id_master_std=request.getParameter("id_master_std");
			//System.out.println("id_master_std="+id_master_std);
			String id_tipe_std=request.getParameter("id_tipe_std");
			//System.out.println("id_tipe_std="+id_tipe_std);
			String id_std_isi=request.getParameter("id_std_isi");
			//System.out.println("id_std_isi="+id_std_isi);
			String id_std=request.getParameter("id_std");
			//System.out.println("id_std="+id_std);
			String id_versi=request.getParameter("id_versi");
			//System.out.println("id_versi="+id_versi);
			String atMenu=request.getParameter("atMenu");
			//System.out.println("atMenu="+atMenu);
			String kdpst_nmpst_kmp=request.getParameter("kdpst_nmpst_kmp");
			//System.out.println("kdpst_nmpst_kmp="+kdpst_nmpst_kmp);
			String nama_param = request.getParameter("nama_param");
			//System.out.println("nama_param="+nama_param);
			String mode = request.getParameter("mode");
			//System.out.println("mode="+mode);
			
			UpdateParamMutu upm = new UpdateParamMutu();
			int upd=upm.addNuParam(nama_param);
			//go.getListAllStd?mode="+jenis_mode %>&id_tipe_std="+id %>&id_master_std="+id_master %>&atMenu=&kdpst_nmpst_kmp="+kdpst_nmpst_kmp %>&at_page="+at_page %>&max_data_per_pg="+max_data_per_pg %>&at_menu=nama_std
			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_creator/form/std_isi/edit_index_single.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?first_index=1&mode="+mode+"&id_master_std="+id_master_std +"&id_tipe_std="+id_tipe_std +"&id_std_isi="+id_std_isi+"&id_std="+id_std+"&id_versi="+id_versi+"&atMenu=edit_isi&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&at_page=1").forward(request,response);
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

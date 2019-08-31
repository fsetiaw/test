package servlets.spmi.standard;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.StringTokenizer;
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
import beans.dbase.spmi.SearchStandarMutu;
import beans.dbase.spmi.UpdateStandarMutu;
import beans.dbase.spmi.form.UpdateForm;
import beans.dbase.spmi.manual.SearchManual;
import beans.dbase.spmi.request.SearchRequest;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class SetAktivasiStd_v2
 */
@WebServlet("/SetAktivasiStd_v2")
public class SetAktivasiStd_v2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetAktivasiStd_v2() {
        super();
        //TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO Auto-generated method stub
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); //HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0.
		response.setHeader("Expires", "0"); //Proxies.
		response.setContentType("text/html");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");

		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
			//System.out.println("ampun");
			//mode=view&id_tipe_std=<%=id %>&id_master_std=<%=id_master %>&atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=<%=at_page %>&max_data_per_pg=<%=max_data_per_pg %>
			
			//set.aktivasiStd_v2?am_i_terkait=<%=am_i_terkait %>&am_i_pengawas=<%=am_i_pengawas%>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=std&fwdto=dashboard_std_isi.jsp
			//		get.prepInfoStd?am_i_terkait=<%=terkait %>&am_i_pengawas=<%=pengawas%>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=std&fwdto=dashboard_std_isi.jsp','popup','width=850,height=600')"">
			String aktif = request.getParameter("aktif");
			//String fwdto = request.getParameter("fwdto");
			String id_std_isi = request.getParameter("id_std_isi");
			
			UpdateStandarMutu usm = new UpdateStandarMutu();
			int updated=usm.toogleAktifasi(Integer.parseInt(id_std_isi), Boolean.parseBoolean(aktif));
			
			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/home_standar.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath_v2(uri, target);
				//System.out.println("url2="+url);
				
			request.getRequestDispatcher("get.prepInfoStd").forward(request,response);	
			
		}
	}

	/**
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO Auto-generated method stub
		doGet(request, response);
	}

}

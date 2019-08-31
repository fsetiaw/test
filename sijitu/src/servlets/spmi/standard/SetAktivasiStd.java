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
 * Servlet implementation class SetAktivasiStd
 */
@WebServlet("/SetAktivasiStd")
public class SetAktivasiStd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetAktivasiStd() {
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
			
			String aktif = request.getParameter("aktif");
			String id_std_isi = request.getParameter("id_std_isi");
			String id_master = request.getParameter("id_master");
			String id_tipe_std = request.getParameter("id_tipe_std");
			//String mode = request.getParameter("mode");
			String backTo = request.getParameter("backTo");
			String max_data_per_pg = request.getParameter("max_data_per_pg");
			String at_page = request.getParameter("at_page");
			String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
			//System.out.println("at_page0="+at_page);
			//System.out.println("kdpst_nmpst_kmp0="+kdpst_nmpst_kmp);
			UpdateStandarMutu usm = new UpdateStandarMutu();
			int updated=usm.toogleAktifasi(Integer.parseInt(id_std_isi), Boolean.parseBoolean(aktif));
			//String target = Constants.getRootWeb()+"InnerFrame/Spmi/standar_spmi/home_standar_nasional.jsp";
			//String uri = request.getRequestURI();
			//String url = PathFinder.getPath_v2(uri, target);
			//System.out.println("id_master-0="+id_master);
			//System.out.println("id_std_isi-0="+id_std_isi);
			//mode=view&id_tipe_std=<%=id %>&id_master_std=<%=id_master %>&atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=<%=at_page %>&max_data_per_pg=<%=max_data_per_pg %>
			//home_standar_nasional.jsp?at_page=<%=at_page %>&max_data_per_pg=<%=max_data_per_pg %>&id=<%=id %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>
			//request.getRequestDispatcher("go.getListAllStd?mode=aktivasi&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&id_master_std="+id_master+"&id_tipe_std="+id_std_isi+"&max_data_per_pg="+max_data_per_pg+"&at_page="+at_page).forward(request,response);
			
			if(!Checker.isStringNullOrEmpty(backTo)&&(backTo.contains("index_std_belum_aktif.jsp")||backTo.contains("index_std_sudah_aktif.jsp"))) {
				//String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
				String mode = request.getParameter("mode");
				//String at_page = request.getParameter("at_page");
				//String id_tipe_std = request.getParameter("id_tipe_std");
				//String id_master_std = request.getParameter("id_master_std");
				//System.out.println("1.id_tipe_std="+id_tipe_std);
				//System.out.println("2.id_master_std="+id_master);
				//String max_data_per_pg = request.getParameter("max_data_per_pg");
				kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
				StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
				String target_kdpst = st.nextToken();
				String target_nmpst = st.nextToken();
				String target_kdkmp = st.nextToken();//ngga kepake b
				SearchStandarMutu stm = new SearchStandarMutu();
				boolean editor = !isu.isHakAksesReadOnly("hasSpmiMenu");
				//System.out.println("saya editor="+editor);
				Vector v_list = null;
				
				v_list = stm.getListStandarIsi(editor, target_kdpst, isu.getIdObj(), Integer.parseInt(id_master), Integer.parseInt(id_tipe_std));

				session.setAttribute("v", v_list);
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/home_standar.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath_v2(uri, target);
				//System.out.println("url2="+url);
				if(backTo.contains("index_std_belum_aktif.jsp")) {
					request.getRequestDispatcher(url+"?backTo="+Constants.getRootWeb()+"/InnerFrame/Spmi/spmi_overview/index_std_belum_aktif.jsp&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&id="+id_master+"&id_tipe_std="+id_tipe_std+"&at_page="+at_page+"&max_data_per_pg="+max_data_per_pg+"&mode="+mode).forward(request,response);	
				}
				else if(backTo.contains("index_std_sudah_aktif.jsp")) {
					request.getRequestDispatcher(url+"?backTo="+Constants.getRootWeb()+"/InnerFrame/Spmi/spmi_overview/index_std_sudah_aktif.jsp&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&id="+id_master+"&id_tipe_std="+id_tipe_std+"&at_page="+at_page+"&max_data_per_pg="+max_data_per_pg+"&mode="+mode).forward(request,response);
				}
					
			}
			else {
				request.getRequestDispatcher("go.getListAllStd?mode=view&id_tipe_std="+id_tipe_std+"&id_master_std="+id_master+"&atMenu=&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&at_page="+at_page+"&max_data_per_pg="+max_data_per_pg).forward(request,response);	
			}
			
			//go.getListAllStd?mode=view&id_tipe_std=<%=id %>&id_master_std=<%=id_master %>&atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=<%=at_page %>&max_data_per_pg=<%=max_data_per_pg %>
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

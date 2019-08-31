package servlets.spmi;

import java.io.PrintWriter;
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
import beans.dbase.dosen.SearchDbDsn;
import beans.dbase.mhs.btstu.SearchDbInfoMhsBtstu;
import beans.dbase.mhs.kurikulum.SearchDbInfoKurikulum;
import beans.dbase.spmi.SearchStandarMutu;
import beans.dbase.spmi.form.UpdateForm;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class GetListAllStandard
 */
@WebServlet("/GetListAllStandard")
public class GetListAllStandard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetListAllStandard() {
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
		//kode here
			//InnerFrame/Spmi/standar_spmi/home_standar.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>
			//nnerFrame/Spmi/standar_creator/indexCreator.jsp?atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp
			//System.out.println("betul kesini");
			String mode = request.getParameter("mode");
			String max_data_per_pg = request.getParameter("max_data_per_pg");
			String at_page = request.getParameter("at_page");
			String pernyataan_isi_std = request.getParameter("pernyataan_isi_std");
			
			String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
			kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
			StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
			String target_kdpst = st.nextToken();
			String target_nmpst = st.nextToken();
			String target_kdkmp = st.nextToken();//ngga kepake b
			//System.out.println("target_kdpst="+target_kdpst);
			//System.out.println("max_data_per_pg="+max_data_per_pg);
			//System.out.println("at_page="+at_page);
			int id_master_std = 0, id_tipe_std=0;
			String tipe_std = request.getParameter("id_tipe_std");
			if(!Checker.isStringNullOrEmpty(tipe_std)) {
				id_tipe_std = Integer.parseInt(tipe_std);
			}
			//System.out.println("tipe_std="+tipe_std);
			String master_std = request.getParameter("id_master_std");
			if(!Checker.isStringNullOrEmpty(master_std)) {
				id_master_std = Integer.parseInt(master_std);
			}
			//System.out.println("master_std="+master_std);
			SearchStandarMutu stm = new SearchStandarMutu();
			boolean editor = !isu.isHakAksesReadOnly("hasSpmiMenu");
			//System.out.println("saya editor="+editor);
			Vector v_list = null;
			//if(id_master_std>0) {
			/*
				Vector v_scope_id = isu.getScopeObjScope_vFinal("hasSpmiMenu", true, true, false, null, null);
				ListIterator lit = v_scope_id.listIterator();
				while(lit.hasNext()) {
					String brs = (String)lit.next();
					//System.out.println("lit = "+brs);
				}
				v_scope_id = Converter.convertVscopeidToTokenDistinctKdpst(v_scope_id);
				lit = v_scope_id.listIterator();
				while(lit.hasNext()) {
					String brs = (String)lit.next();
					//System.out.println("lit1 = "+brs);
				}
				*/
				//v_list = stm.getListStandarIsi(editor, isu.getIdObj(),id_master_std,id_tipe_std);
				v_list = stm.getListStandarIsi(editor, target_kdpst, isu.getIdObj(), id_master_std, id_tipe_std);
				session.setAttribute("v", v_list);
			//}
			//else {
			//	v_list = stm.getListStandarIsi(editor, isu.getIdObj());
			//} 
			//Vector v_list = stm.getListStandarIsi(editor, isu.getIdObj());
			/*
			if(v_list!=null) {
				//System.out.println("v_list size="+v_list.size());
			}
			else {
				//System.out.println("v_list size=null");
			}
			*/
			
			//System.out.println("part1");
			String backTo = request.getParameter("backTo");
			//System.out.println("part2");
			//System.out.println("backto="+backTo);
			if(!Checker.isStringNullOrEmpty(backTo)) {
				//System.out.println("url1=");
				String target = backTo;
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				//System.out.println("url1="+url);
				request.getRequestDispatcher(url).forward(request,response);	
			}
			//System.out.println("mode="+mode);
			if(!Checker.isStringNullOrEmpty(mode) && (mode.equalsIgnoreCase("start"))) {
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/tamplete_standar.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath_v2(uri, target);
				//System.out.println("url2="+url);
				request.getRequestDispatcher(url+"?id="+id_master_std+"&at_page="+at_page+"&max_data_per_pg="+max_data_per_pg).forward(request,response);
			}
			else if(!Checker.isStringNullOrEmpty(mode) && (mode.equalsIgnoreCase("edit_std"))) {
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/tamplete_standar_edit.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath_v2(uri, target);
				//System.out.println("url2="+url);
				request.getRequestDispatcher(url+"?id="+id_master_std+"&at_page="+at_page+"&max_data_per_pg="+max_data_per_pg).forward(request,response);
			}
			else if(!Checker.isStringNullOrEmpty(mode) && (mode.equalsIgnoreCase("nuview"))) {
				//System.out.println("url2");
				session.removeAttribute("v");
				v_list = stm.getListStandarIsi(editor, target_kdpst, isu.getIdObj(), id_master_std, id_tipe_std,pernyataan_isi_std);
				session.setAttribute("v", v_list);
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/home_standar.jsp";
				if(id_master_std<1) {
					target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/home_standar_unknown.jsp";
				}
				String uri = request.getRequestURI();
				String url = PathFinder.getPath_v2(uri, target);
				//System.out.println("url2="+url);
				request.getRequestDispatcher(url+"?backTo=home_standar_nasional.jsp&id="+id_master_std+"&at_page="+at_page+"&max_data_per_pg="+max_data_per_pg).forward(request,response);	
			}
			else if(!Checker.isStringNullOrEmpty(mode) && (mode.equalsIgnoreCase("create"))) {
				//System.out.println("url2");
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/tamplete_standar_creation.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath_v2(uri, target);
				//System.out.println("url2="+url);
				request.getRequestDispatcher(url+"?id="+id_master_std+"&at_page="+at_page+"&max_data_per_pg="+max_data_per_pg).forward(request,response);	
			}
			else if(!Checker.isStringNullOrEmpty(mode) && (mode.equalsIgnoreCase("view"))) {
				//System.out.println("url2");
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/home_standar.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath_v2(uri, target);
				//System.out.println("url2="+url);
				request.getRequestDispatcher(url+"?backTo=home_standar_nasional.jsp&id="+id_master_std+"&at_page="+at_page+"&max_data_per_pg="+max_data_per_pg).forward(request,response);	
			}
			else if(!Checker.isStringNullOrEmpty(mode) && (mode.equalsIgnoreCase("aktivasi"))) {
				//System.out.println("url2");
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/home_standar.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				//System.out.println("url2="+url);
				request.getRequestDispatcher(url+"?backTo=home_standar_nasional.jsp&id="+id_master_std+"&at_page="+at_page+"&max_data_per_pg="+max_data_per_pg).forward(request,response);	
			}
			else if(!Checker.isStringNullOrEmpty(mode) && (mode.equalsIgnoreCase("man_page"))) {
				//System.out.println("url2");
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/home_manual_standar.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				//System.out.println("url2="+url);
				request.getRequestDispatcher(url+"?backTo=home_manual_standar_nasional.jsp&id="+id_master_std+"&at_page="+at_page+"&max_data_per_pg="+max_data_per_pg).forward(request,response);	
			}
			else if(!Checker.isStringNullOrEmpty(mode) && (mode.equalsIgnoreCase("std_non_aktif"))) {
				//System.out.println("url2");
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/home_standar.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath_v2(uri, target);
				//System.out.println("url2="+url);
				request.getRequestDispatcher(url+"?backTo="+Constants.getRootWeb()+"/InnerFrame/Spmi/spmi_overview/index_std_belum_aktif.jsp&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&id="+id_master_std+"&at_page="+at_page+"&max_data_per_pg="+max_data_per_pg+"&mode="+mode).forward(request,response);	
			}	//?";
			else if(!Checker.isStringNullOrEmpty(mode) && (mode.equalsIgnoreCase("std_sdh_aktif"))) {
				//System.out.println("url2");
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/home_standar.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath_v2(uri, target);
				//System.out.println("url2="+url);
				request.getRequestDispatcher(url+"?backTo="+Constants.getRootWeb()+"/InnerFrame/Spmi/spmi_overview/index_std_sudah_aktif.jsp&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&id="+id_master_std+"&at_page="+at_page+"&max_data_per_pg="+max_data_per_pg+"&mode="+mode).forward(request,response);	
			}	
			/*
			
			*/
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO Auto-generated method stub
		doGet(request, response);
	}

}

package servlets.Overview;

import java.io.IOException;
import java.sql.Connection;
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
import beans.dbase.overview.GetSebaranTrlsm;
import beans.dbase.trlsm.SearchDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.Overview;
import beans.tools.PathFinder;
import beans.tools.Tool;
import beans.tools.filter.FilterKampus;

/**
 * Servlet implementation class SebaranTrlsm_v2
 */
@WebServlet("/SebaranTrlsm_v2")
public class SebaranTrlsm_v2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SebaranTrlsm_v2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
			String target_thsms = request.getParameter("target_thsms");
			String show_angel = request.getParameter("show_angel");
			//System.out.println("show_angel_dari_home="+show_angel_dari_home);
			
			if(show_angel==null) {
				show_angel = "false";
			}
			else {
				show_angel="true";
			
			}
			
			
			//if(Checker.isStringNullOrEmpty(show_angel)) {
			//	show_angel = "true";
			//}
			boolean include_angel = Boolean.valueOf(show_angel);
			//session.setAttribute("target_thsms", target_thsms);
			//session.setAttribute("show_angel", show_angel);
			
			
			
			
			System.out.println("target_thsms="+target_thsms);
			//System.out.println("show_angel="+show_angel);
			Vector v_scope_kdpst = isu.getScopeKdpst_vFinal("s");
			
			try {
				Connection con = Overview.createConnection();
				Vector v = Getter.getListAllKampus();
				ListIterator li = v.listIterator();
				System.out.println("time start="+AskSystem.getCurrentTimestamp());
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String kdkmp = st.nextToken();
					int tot_krs = Overview.getTotalMhsAktif(target_thsms, v_scope_kdpst, kdkmp, include_angel, con);
					session.setAttribute("tot_krs_"+kdkmp, ""+tot_krs);
					int tot_cuti_nonaktif = Overview.getTotalMhsCutiDanNonaktif(target_thsms, v_scope_kdpst, kdkmp, include_angel, con);
					session.setAttribute("tot_cuti_nonaktif_"+kdkmp, ""+tot_cuti_nonaktif);
					//
				}
				System.out.println("time end="+AskSystem.getCurrentTimestamp());
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			/*
			Vector v_scope_id = isu.returnScopeProdiOnlySortByKampusWithListIdobj("s");
			SearchDbInfoMhs sdim = new SearchDbInfoMhs();
			SearchDbTrlsm sdt = new SearchDbTrlsm();
			Vector v_scope_kdpst_list_distinct_npm_trnlm_at_target_thsms = sdim.getListNpmhsYgAdaDiTrnlm(target_thsms, v_scope_id, Boolean.parseBoolean(show_angel));
			Vector v_scope_kdpst_list_distinct_npm_cuti_ato_nonaktif_at_target_thsms = sdim.getListNpmhsGivenTknStmhs(target_thsms, v_scope_id, Boolean.parseBoolean(show_angel), "C`N");
			Vector v_scope_kdpst_list_distinct_npm_mhs_baru = sdim.getListNpmhsMhsBaru(target_thsms, v_scope_id, Boolean.parseBoolean(show_angel));
			Vector v_scope_kdpst_list_distinct_npm_info_daftar_ulang = sdim.getListNpmhsPengajuanDaftarUlang(target_thsms, v_scope_id);
			Vector v_scope_kdpst_list_distinct_npm_info_daftar_ulang_wip = sdim.getListNpmhsPengajuanDaftarUlangWip(target_thsms, v_scope_id);
			Vector v_scope_kdpst_list_distinct_npm_angel_thsms_1 = Getter.getListNpmhsMalaikatKrsBerdasarkanAktifSmsLalu(Tool.returnPrevThsmsGivenTpAntara(target_thsms) , v_scope_id);
			Vector v_scope_kdpst_list_distinct_npm_info_pindah_prodi_out = sdim.getListNpmhsPengajuanPindahProdiOut(target_thsms, v_scope_id);
			Vector v_scope_kdpst_list_distinct_npm_info_pindah_prodi_in = sdim.getListNpmhsPengajuanPindahProdiIn(target_thsms, v_scope_id);
			Vector v_scope_kdpst_list_distinct_npm_info_pindah_prodi_wip = sdim.getListNpmhsPengajuanPindahProdiWip(target_thsms, v_scope_id);
			Vector v_scope_kdpst_list_distinct_npm_info_kelulusan = sdim.getListNpmhsPengajuanKelulusan(target_thsms, v_scope_id, Boolean.parseBoolean(show_angel));
			Vector v_scope_kdpst_list_distinct_npm_info_kelulusan_wip = sdim.getListNpmhsPengajuanKelulusanWip(target_thsms, v_scope_id, Boolean.parseBoolean(show_angel));
			Vector v_scope_kdpst_list_distinct_npm_lulusan_at_trlsm = sdt.getListMhsGivenStmhsTiapKampus(target_thsms, v_scope_id, "L", Boolean.parseBoolean(show_angel));
			Vector v_scope_kdpst_list_distinct_npm_mhs_out_univ = sdim.getListNpmhsPengajuanKeluarDo(target_thsms, v_scope_id, Boolean.parseBoolean(show_angel));
			Vector v_scope_kdpst_list_distinct_npm_out_univ_at_trlsm = sdt.getListMhsGivenStmhsTiapKampus(target_thsms, v_scope_id, "K`D`DO", Boolean.parseBoolean(show_angel));
			
			//jangan lupa dihapus at home.jsp
			session.setAttribute("v_scope_kdpst_list_distinct_npm_out_univ_at_trlsm", v_scope_kdpst_list_distinct_npm_out_univ_at_trlsm);
			session.setAttribute("v_scope_kdpst_list_distinct_npm_mhs_out_univ", v_scope_kdpst_list_distinct_npm_mhs_out_univ);
			session.setAttribute("v_scope_kdpst_list_distinct_npm_trnlm_at_target_thsms", v_scope_kdpst_list_distinct_npm_trnlm_at_target_thsms);
			session.setAttribute("v_scope_kdpst_list_distinct_npm_cuti_ato_nonaktif_at_target_thsms", v_scope_kdpst_list_distinct_npm_cuti_ato_nonaktif_at_target_thsms);
			session.setAttribute("v_scope_kdpst_list_distinct_npm_mhs_baru", v_scope_kdpst_list_distinct_npm_mhs_baru);
			session.setAttribute("v_scope_kdpst_list_distinct_npm_info_daftar_ulang", v_scope_kdpst_list_distinct_npm_info_daftar_ulang);
			session.setAttribute("v_scope_kdpst_list_distinct_npm_info_daftar_ulang_wip", v_scope_kdpst_list_distinct_npm_info_daftar_ulang_wip);
			session.setAttribute("v_scope_kdpst_list_distinct_npm_angel_thsms_1", v_scope_kdpst_list_distinct_npm_angel_thsms_1);
			session.setAttribute("v_scope_kdpst_list_distinct_npm_info_pindah_prodi_out", v_scope_kdpst_list_distinct_npm_info_pindah_prodi_out);
			session.setAttribute("v_scope_kdpst_list_distinct_npm_info_pindah_prodi_in", v_scope_kdpst_list_distinct_npm_info_pindah_prodi_in);
			session.setAttribute("v_scope_kdpst_list_distinct_npm_info_pindah_prodi_wip", v_scope_kdpst_list_distinct_npm_info_pindah_prodi_wip);
			session.setAttribute("v_scope_kdpst_list_distinct_npm_info_kelulusan", v_scope_kdpst_list_distinct_npm_info_kelulusan);
			session.setAttribute("v_scope_kdpst_list_distinct_npm_info_kelulusan_wip", v_scope_kdpst_list_distinct_npm_info_kelulusan_wip);
			session.setAttribute("v_scope_kdpst_list_distinct_npm_lulusan_at_trlsm", v_scope_kdpst_list_distinct_npm_lulusan_at_trlsm);
			*/
			/*
			String scope_kampus = isu.getScopeKampus("s");
			scope_kampus =FilterKampus.kampusAktifOnly(target_thsms, scope_kampus);
			GetSebaranTrlsm sb = new GetSebaranTrlsm(isu.getNpm());
			Vector v_overview = sb.getInfoOverviewSebaranTrlsmTable(v_scope,target_thsms);
			session.setAttribute("v_overview", v_overview);
			*/
			String target = Constants.getRootWeb()+"/InnerFrame/Overview/Trlsm/indexSebaranTrlsm_v2.jsp"; 
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?show_angel="+show_angel+"&target_thsms="+target_thsms).forward(request,response);

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

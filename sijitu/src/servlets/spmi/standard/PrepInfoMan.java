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
 * Servlet implementation class PrepInfoMan
 */
@WebServlet("/PrepInfoMan")
public class PrepInfoMan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepInfoMan() {
        super();
        //TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO Auto-generated method stub
		//System.out.println("masuk prepInfoMan");
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
		//PrintWriter oString kdpst_nmpst_kmp= request.getParameter("kdpst_nmpst_kmp");
			Boolean spmi_editor = (Boolean) session.getAttribute("spmi_editor");// asalnya !isu.isHakAksesReadOnly("hasSpmiMenu");
			boolean editor = spmi_editor.booleanValue();
			//String terkait = (String)session.getAttribute("terkait");
			//String pengawas = (String)session.getAttribute("pengawas");
			//String tkn_pengawas = request.getParameter("tkn_pengawas");
			//String tkn_pihak = request.getParameter("tkn_terkait");
			//System.out.println("tkn_awas="+tkn_pengawas);
			//System.out.println("tkn_pihsk="+tkn_pihak);
			//boolean am_i_pengawas = isu.amI(tkn_pengawas);
			//boolean am_i_terkait = isu.amI(tkn_pihak);
			//if(terkait.equalsIgnoreCase("false")||pengawas.equalsIgnoreCase("false")) {
				
			//}
			
			
			
			
			String at_menu_dash = request.getParameter("at_menu_dash");
			String fwdto = request.getParameter("fwdto");
			//System.out.println("fwdtoo="+fwdto);
			String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
			//System.out.println("kdpst_nmpst_kmp="+kdpst_nmpst_kmp);
			StringTokenizer st = null;
			if(kdpst_nmpst_kmp!=null && kdpst_nmpst_kmp.contains("-")) {
				st = new StringTokenizer(kdpst_nmpst_kmp,"-");
			}
			else {
				st = new StringTokenizer(kdpst_nmpst_kmp,"`");
			}
		 
			String kdpst = st.nextToken();
			String nmpst = st.nextToken();
			String kdkmp = st.nextToken();
			String id_tipe_std = request.getParameter("id_tipe_std");
			String id_master_std = request.getParameter("id_master_std");
			String id_std = request.getParameter("id_std");
			SearchRequest sr = new SearchRequest();
			//Vector v = sr.getInfoStd(Integer.parseInt(id_std_isi));
			//if(v!=null) {
			//System.out.println("v si="+v.size());
			//}
			//else {
			//System.out.println("v null=");
			//}
			//request.setAttribute("v", v);
			
			//if(!Checker.isStringNullOrEmpty(at_menu_dash) && at_menu_dash.equalsIgnoreCase("control")) {
				//SearchManual sm = new SearchManual();
				//Vector v_kendal = sm.searchManualStandardPengendalian_v1(Integer.parseInt(id_versi), Integer.parseInt(id_std_isi));
				//li.add(id+"`"+id_versi+"`"+id_std_isi+"`"+norut+"`"+target_kondisi+"`"+target_proses+"`"+manual_kegiatan+"`"+interval_pengawasan+"`"+unit_interval_pengawasan+"`"+jabatan_pengawas+"`"+tkn_id_uu+"`"+tkn_id_permen);
				//request.setAttribute("v_kendal", v_kendal);	
			//}
			
			//System.out.println("fwdTo="+fwdto);
			//String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/"+fwdto+".jsp";
			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/"+fwdto;
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url).forward(request,response);
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

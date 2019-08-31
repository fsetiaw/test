package servlets.spmi.form.input;


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
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class AddDraftStdIsi
 */
@WebServlet("/AddDraftStdIsi")
public class AddDraftStdIsi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddDraftStdIsi() {
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
			Vector v_err = null;
			ListIterator<String> li=null;
			/*
			 * note : id_versi sudah sesuai
			 */
			String id_versi = request.getParameter("id_versi");
			String id_tipe_std = request.getParameter("id_tipe_std");
			String id_master_std = request.getParameter("id_master_std");
			String isi_std = request.getParameter("isi_std");
			String cakupan_std = request.getParameter("cakupan_std");
			String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
			if(Checker.isStringNullOrEmpty(cakupan_std)) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Cakupan Standar harus diisi");
				cakupan_std = "null";
			}
			
			if(v_err!=null && v_err.size()>0) {
    			//System.out.println("ada error");
    			//session.setAttribute("v_target", v_target);
    			session.setAttribute("v_err", v_err);
    			
    			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_creator/form/std_isi/add_std_isi.jsp";
    			String uri = request.getRequestURI();
    			String url = PathFinder.getPath(uri, target);
    			//System.out.println("?mode=edit&id_master="+id_master+"&id_tipe="+id_tipe+"&atMenu="+atMenu+"&tkn_pengawas="+pihak_mon);;
    			//request.getRequestDispatcher(url+"?mode=edit&id_master="+id_master+"&id_tipe="+id_tipe+"&atMenu="+atMenu+"&tkn_pengawas=null").forward(request,response);
    			request.getRequestDispatcher(url+"?d_versi="+id_versi+"&id_tipe_std="+id_tipe_std+"&id_master_std="+id_master_std+"&atMenu=&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&isi_std="+isi_std).forward(request,response);
    		}
    		else {
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
    			SearchStandarMutu ssm = new SearchStandarMutu();
    			int id_std = ssm.getIdStd(id_master_std, id_tipe_std);
    			UpdateForm uf = new UpdateForm();
    			//isi std selalu mengikuti versi std yg request (id_versi)
    			int updated = uf.addDraftIsiStd(id_std,isi_std,Integer.parseInt(id_versi),cakupan_std);
    			
    			request.getRequestDispatcher("go.getListAllStd?mode=start&id_versi="+id_versi+"&id_tipe_std="+id_tipe_std+"&id_master_std="+id_master_std+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);	
    		}
			
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

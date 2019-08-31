package servlets.spmi.update;

import java.io.IOException;
import java.util.Vector;
import java.util.ListIterator;
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
import beans.dbase.spmi.ToolSpmi;
import beans.dbase.spmi.UpdateStandarMutu;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.dbase.visi_misi_pt.UpdateVisiMisiPt;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdStdMutu
 */
@WebServlet("/UpdStdMutu")
public class UpdStdMutu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdStdMutu() {
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
			Vector v_err = null;
			//RESERVED ERROR CHECKER - saat ini tidak perlu 
			//ListIterator lie = v_err.listIterator();
			//lie.add("ada error");
			session.setAttribute("v_err", v_err);
			String mode=request.getParameter("mode");
			String id_versi=request.getParameter("id_versi");
			String id_tipe_std=request.getParameter("id_tipe_std");
			String id_master_std=request.getParameter("id_master_std");
			String kdpst_nmpst_kmp=request.getParameter("kdpst_nmpst_kmp");
			
			//form
			String visi_pt=request.getParameter("visi_pt");
			String misi_pt=request.getParameter("misi_pt");
			String tujuan_pt=request.getParameter("tujuan_pt");
			String nilai_pt=request.getParameter("nilai_pt");
			String rasionale_std=request.getParameter("rasionale_std");
			String pihak_resposible=request.getParameter("pihak_terkait_std");
			String def_std=request.getParameter("definisi_std");
			String ref_std=request.getParameter("ref_std");
			String dok_terkait_std=null;
			String[]doc = request.getParameterValues("doc");
			if(doc!=null && doc.length>0) {
				for(int j=0;j<doc.length;j++) {
					if(dok_terkait_std==null) {
						dok_terkait_std = new String(doc[j]);
					}
					else {
						dok_terkait_std = dok_terkait_std+","+doc[j];
					}
				}
			}
			String tombol = request.getParameter("tombol");
			if(tombol!=null && tombol.equalsIgnoreCase("dok_terkait")) {
				//System.out.println("submit="+tombol);
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/Dokument/mutu/form_tambah_dok_std.jsp";
    			String uri = request.getRequestURI();
    			String url = PathFinder.getPath(uri, target);
    			request.getRequestDispatcher(url).forward(request,response);
				//System.out.println("submit=null");	
			}
			else {
				if(v_err!=null) {
					request.getRequestDispatcher("go.getListAllStd?mode=edit_std&id_tipe_std="+id_tipe_std+"&id_master_std="+id_master_std+"&atMenu=&kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);	
				}
				else {
					SearchStandarMutu ssm = new SearchStandarMutu();
					int id_std = ssm.getIdStd(id_master_std, id_tipe_std);
					UpdateStandarMutu usm = new UpdateStandarMutu();
					int upd = usm.editStandarMutu(id_std, id_versi, rasionale_std, pihak_resposible, def_std, ref_std, null, null, null, null, null, null, dok_terkait_std);
					UpdateVisiMisiPt uvm = new UpdateVisiMisiPt();
					upd = upd + uvm.updateVisiMisiTujuanNilaiPt(visi_pt, misi_pt, tujuan_pt, nilai_pt);
					request.getRequestDispatcher("go.getListAllStd?mode=start&id_tipe_std="+id_tipe_std+"&id_master_std="+id_master_std+"&atMenu=&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&updated="+upd).forward(request,response);
				}
			}
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
			//String uri = request.getRequestURI();
			//String url = PathFinder.getPath(uri, target);
			
			
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

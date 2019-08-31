package servlets.spmi.doc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.jabatan.UpdateDbJabatan;
import beans.dbase.trlsm.UpdateStandarDokumen;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class TambahTipeDokumenManual
 */
@WebServlet("/TambahTipeDokumenManual")
public class TambahTipeDokumenManual extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TambahTipeDokumenManual() {
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
			//System.out.println("servlet jabatan");
			String nama_doc= request.getParameter("nama_doc");
			if(!Checker.isStringNullOrEmpty(nama_doc)) {
				nama_doc = nama_doc.toUpperCase();
			}
			//System.out.println("norut_jab = "+norut_posisi_jabatan);
			//System.out.println("pos_jab = "+posisi_jabatan);
			//System.out.println("nm_jab = "+nm_jabatan);
			
			UpdateStandarDokumen udj = new UpdateStandarDokumen(isu.getNpm());
			int upd = udj.tambahTipeDokumenMutu(nama_doc);
			//System.out.println("upd = "+upd);
			
			
			String mode = request.getParameter("mode");
			String ppepp = request.getParameter("ppepp");
			String at_menu_dash = request.getParameter("at_menu_dash");
			String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
			String id_std=request.getParameter("id_std"); 
			String id_tipe_std=request.getParameter("id_tipe_std"); 
			String id_versi=request.getParameter("id_versi"); 
			String id_master_std=request.getParameter("id_master_std"); 
			String target = "";
			String fwdto ="";
			if(ppepp.equalsIgnoreCase("perencanaan")) {
				target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_perencanaan_v1.jsp";
				fwdto = "dashboard_std_manual_perencanaan_v1.jsp";
			}
			else if(ppepp.equalsIgnoreCase("pelaksanaan")) {
				target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_pelaksanaan_v1.jsp";
				fwdto = "dashboard_std_manual_pelaksanaan_v1.jsp";
			}
			else if(ppepp.equalsIgnoreCase("evaluasi")) {
				target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_evaluasi_v1.jsp";
				fwdto = "dashboard_std_manual_pengendalian_eval_v1.jsp";
			}
			else if(ppepp.equalsIgnoreCase("pengendalian")) {
				target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_pengendalian_v1.jsp";
				fwdto = "dashboard_std_manual_pengendalian_kendali_v1.jsp";
			}
			else if(ppepp.equalsIgnoreCase("peningkatan")) {
				target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/form_manual/form_insert_manual_peningkatan_v1.jsp";
				fwdto = "dashboard_std_manual_peningkatan_v1.jsp";
			}
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?fwdto="+fwdto+"&mode="+mode+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&id_std="+id_std+"&at_menu_dash="+at_menu_dash+"&id_versi="+id_versi+"&id_tipe_std="+id_tipe_std+"&id_master_std="+id_master_std).forward(request,response);
				

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

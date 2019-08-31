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
 * Servlet implementation class TambahTipeDokumenSingle
 */
@WebServlet("/TambahTipeDokumenSingle")
public class TambahTipeDokumenSingle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TambahTipeDokumenSingle() {
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
			String id_std_isi = request.getParameter("id_std_isi");
			String atMenu = request.getParameter("atMenu");
			String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
			String id_tipe_std=request.getParameter("id_tipe_std"); 
			String id_versi=request.getParameter("id_versi"); 
			String id_master_std=request.getParameter("id_master_std"); 
			String target = "go.getListAllStd?mode=edit_std&id_versi="+id_versi+"&id_tipe_std="+id_tipe_std+"&id_master_std="+id_master_std+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp;
			request.getRequestDispatcher(target).forward(request,response);
				

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

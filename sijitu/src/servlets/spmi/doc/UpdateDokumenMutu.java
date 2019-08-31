package servlets.spmi.doc;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.jabatan.UpdateDbJabatan;
import beans.dbase.spmi.ToolSpmi;
import beans.dbase.trlsm.UpdateStandarDokumen;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateDokumenMutu
 */
@WebServlet("/UpdateDokumenMutu")
public class UpdateDokumenMutu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateDokumenMutu() {
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
			String[] nm_doc_mutu_ori= request.getParameterValues("nm_doc_mutu_ori");
			String[] nm_doc_mutu_nu= request.getParameterValues("nm_doc_mutu_nu");
			//System.out.println("norut_jab = "+norut_posisi_jabatan);
			//System.out.println("pos_jab = "+posisi_jabatan);
			//System.out.println("nm_jab = "+nm_jabatan);
			//for(int i=0;i<nm_doc_mutu_ori.length;i++) {
			//	System.out.println("ori = "+nm_doc_mutu_ori[i]);
			//	System.out.println("new = "+nm_doc_mutu_nu[i]);
			//}
			
			UpdateStandarDokumen udj = new UpdateStandarDokumen(isu.getNpm());
			int upd = udj.updateNamaDokumenMutu(nm_doc_mutu_ori, nm_doc_mutu_nu);
			ToolSpmi ts = new ToolSpmi();
			Vector v_ListKdpstKdjenNmpstNmjen = Getter.getListKdpstKdjenNmpstNmjen();
			ts.renameFolderStructureDocMutu(v_ListKdpstKdjenNmpstNmjen, nm_doc_mutu_ori, nm_doc_mutu_nu);
			//int upd = udj.UpdateDokumenMutuMutu(nama_doc);
			//System.out.println("upd = "+upd);
			
			
			
			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/Dokument/mutu/list_doc_mutu.jsp";;
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath_v2(uri, target);
			request.getRequestDispatcher(url+"?atMenu=list").forward(request,response);
			
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

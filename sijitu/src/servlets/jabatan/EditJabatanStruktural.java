package servlets.jabatan;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.jabatan.UpdateDbJabatan;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class EditJabatanStruktural
 */
@WebServlet("/EditJabatanStruktural")
public class EditJabatanStruktural extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditJabatanStruktural() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
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
			String[]nama_original_jabatan=request.getParameterValues("nama_original_jabatan");
			String[]jabatan=request.getParameterValues("jabatan");
			String[]urutan_posisi_jabatan=request.getParameterValues("urutan_posisi_jabatan");
			String[]posisi_jabatan=request.getParameterValues("posisi_jabatan");
			int upd=0;
			if(nama_original_jabatan!=null && nama_original_jabatan.length>0) {
				UpdateDbJabatan udj = new UpdateDbJabatan(isu.getNpm());
				upd = udj.editNamaJabatan(nama_original_jabatan, posisi_jabatan, jabatan, urutan_posisi_jabatan);
			}
			String target = Constants.getRootWeb()+"/InnerFrame/sdm/jabatan/list_jabatan.jsp?atMenu=list"; 
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url).forward(request,response);
			
			/*
			
			if(prodi!=null && kmp!=null) {
				
				udj.updProfileJabatan(prodi, kmp, Long.parseLong(id_obj), Getter.getDomisiliKampus(npm));
				//get.jabatanStruk?listKurAndSelected=<%=listKurAndSelected %>&nim=<%=v_nimhs %>&id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&atMenu=js&cmd=js
			}
			else {
				int upd = udj.resetProfileJabatan(Long.parseLong(id_obj));
				//System.out.println("is null");
				//redirecting
				//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
			}
			String target = "get.jabatanStruk?nim="+nim+"&id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&atMenu=js&cmd=js"; 
			//String uri = request.getRequestURI(); 
			//String url = PathFinder.getPath(uri, target);
			//request.getRequestDispatcher(target).forward(request,response);
			*/
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

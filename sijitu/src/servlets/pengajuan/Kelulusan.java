package servlets.pengajuan;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.topik.UpdateDbTopik;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class Kelulusan
 */
@WebServlet("/Kelulusan")
public class Kelulusan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Kelulusan() {
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
			//System.out.println("cutiii");
			/*
			 * hanya posting pengajuan ke 
			 */
			String tglls= request.getParameter("tglls");
			String err_msg = null;
			if(tglls==null || Checker.isStringNullOrEmpty(tglls)) {
				err_msg = new String("*Tanggal kelulusan wajib diisi");
			}
			try {
				java.sql.Date.valueOf(Converter.autoConvertDateFormat(tglls, "-"));
			}
			catch (Exception e) {
				err_msg = new String("*Tanggal kelulusan harap diisi dengan format dd/mm/yyyy");
			}
			String id_obj=""+request.getParameter("id_obj");
			String nmm=""+request.getParameter("nmm");
			String npm=""+request.getParameter("npm");
			String obj_lvl=""+request.getParameter("obj_lvl");
			String kdpst=""+request.getParameter("kdpst");
			String cmd=""+request.getParameter("cmd");
			String alasan=""+tglls;
			String target_thsms=""+request.getParameter("target_thsms");
			String scope=""+request.getParameter("scope");
			String table_nm=""+request.getParameter("table");
			String folder_pengajuan=""+request.getParameter("folder_pengajuan");
			String type_pengajuan = table_nm.replace("_RULES", "");
			if(tglls!=null && !Checker.isStringNullOrEmpty(tglls) && Checker.isStringNullOrEmpty(err_msg)) {
				UpdateDbTopik udb = new UpdateDbTopik(isu.getNpm());
				String msg = udb.postTopikPengajuanKelulusan(target_thsms,kdpst, npm, alasan, nmm, table_nm);
				
				if(msg==null || Checker.isStringNullOrEmpty(msg)) {
					msg = new String("upd");
				}
				//<a href="go.moCuti?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=cuti" target="_self" class="active">CUTI<span>KULIAH</span></a></li>
				//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp"; 
				//String uri = request.getRequestURI(); 
				//String url = PathFinder.getPath(uri, target);
				//request.getRequestDispatcher("go.moCuti?target_thsms="+target_thsms+"&id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm +"&obj_lvl="+obj_lvl +"&kdpst="+kdpst+"&cmd=cuti&msg="+msg).forward(request,response);
				request.getRequestDispatcher("go.moPp?target_thsms="+target_thsms+"&id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm +"&obj_lvl="+obj_lvl +"&kdpst="+kdpst+"&cmd=pp&msg="+msg+"&scope="+scope+"&table="+table_nm+"&folder_pengajuan="+folder_pengajuan).forward(request,response);	
			}
			else {
				String target = Constants.getRootWeb()+"/InnerFrame/Pengajuan/"+folder_pengajuan+"/form_pp.jsp"; 
				String uri = request.getRequestURI(); 
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url+"?id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm +"&obj_lvl="+obj_lvl +"&kdpst="+kdpst+"&cmd="+cmd+"&err_msg="+err_msg+"&scope="+scope+"&table="+table_nm+"&folder_pengajuan="+folder_pengajuan+"&atMenu=form").forward(request,response);
				
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

package servlets.process.daftarUlang;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.dbase.mhs.*;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.AddHocFunction;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class DaftarUlangByOp
 */
@WebServlet("/DaftarUlangByOp")
public class DaftarUlangByOp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DaftarUlangByOp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("daftar Ulang");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String id_obj = request.getParameter("id_obj");
		String nmmhs = request.getParameter("nmmhs");
		String npmhs = request.getParameter("npmhs");
		String obj_lvl= request.getParameter("obj_lvl");
		String kdpst=request.getParameter("kdpst");
		String target_thsms=request.getParameter("target_thsms");
		UpdateDbInfoMhs udb = new UpdateDbInfoMhs(isu.getNpm());
		//String thsms_heregistrasi = Checker.getThsmsHeregistrasi();
		
		Vector v_scope_id = isu.returnScopeProdiOnlySortByKampusWithListIdobj("hasHeregitrasiMenu", "HEREGISTRASI_RULES", target_thsms);
		udb.updateDaftarUlangTableByOperator_v1(kdpst, npmhs, target_thsms);
		String psn = Checker.sudahDaftarUlang(kdpst, npmhs, target_thsms); //overide session value
		session.setAttribute("sdu", psn);
		if(isu.iAmStu()) {
			String target = Constants.getRootWeb()+"/InnerFrame/Prakuliah/Mhs/DaftarUlangByOpr.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?am_i_stu=true&id_obj="+id_obj+"&nmm="+nmmhs+"&npm="+npmhs +"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd=heregistrasi&no_button=true&backTo=get.notifications").forward(request,response);	
		
		}
		else {
			//kalo operator yang mengajukan
			request.getRequestDispatcher("get.profile?id_obj="+id_obj+"&nmm="+nmmhs+"&npm="+npmhs+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd=dashboard").forward(request,response);	
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}

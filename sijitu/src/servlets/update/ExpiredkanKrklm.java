package servlets.update;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.*;
import beans.login.InitSessionUsr;
import beans.tools.Checker;
/**
 * Servlet implementation class ExpiredkanKrklm
 */
@WebServlet("/ExpiredkanKrklm")
public class ExpiredkanKrklm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public ExpiredkanKrklm() {
//        super();
        // TODO Auto-generated constructor stub
//    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String idkur = request.getParameter("idkur_");
		String kdpst_nmpst = request.getParameter("kdpst_nmpst");
		UpdateDb udb = new UpdateDb();
		String thsms_reg = Checker.getThsmsHeregistrasi();
		int i = udb.expiredkanKrklm(Integer.valueOf(idkur).intValue(),request.getRemoteAddr(),isu,thsms_reg);
		//System.out.println("masuk kesini");
		//System.out.println(kdpst_nmpst);
		//System.out.println("aktifkan = "+idkur+" = "+i);
		
		request.getRequestDispatcher("get.listKurikulum?idkmk_=null&kdpst_nmpst="+kdpst_nmpst).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}

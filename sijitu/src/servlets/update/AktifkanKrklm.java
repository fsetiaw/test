package servlets.update;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.*;
import beans.dbase.krklm.SearchDbKrklm;
import beans.dbase.krklm.UpdateDbKrklm;
import beans.login.InitSessionUsr;
import beans.tools.Checker;
/**
 * Servlet implementation class AktifkanKrklm
 */
@WebServlet("/AktifkanKrklm")
public class AktifkanKrklm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public AktifkanKrklm() {
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
		
		SearchDbKrklm sdk = new SearchDbKrklm();
		
		String err_msg = sdk.validateKurikulum(Integer.parseInt(idkur));
		if(Checker.isStringNullOrEmpty(err_msg)) {
			UpdateDbKrklm udb = new UpdateDbKrklm();
			udb.aktifkanKrklm(Integer.valueOf(idkur).intValue(),request.getRemoteAddr(),isu);	
			int upd = udb.aktifkanMatakuliahKrklm(Integer.parseInt(idkur));
			//System.out.println("updated = "+upd);
		}
		else {
			session.setAttribute("err", err_msg);
		}
		//System.out.println("aktifkan = "+idkmk);
		//System.out.println(kdpst_nmpst);
		//get.listMakur?kdpst_nmpst=<%=kdpst_nmpst %>
		//request.getRequestDispatcher("get.listKurikulum?idkmk_=null&kdpst_nmpst="+kdpst_nmpst).forward(request,response);
		request.getRequestDispatcher("get.listMakur?idkur_=null&kdpst_nmpst="+kdpst_nmpst).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}

package servlets.trlsm;

import java.io.IOException;
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
import beans.dbase.trlsm.SearchDbTrlsm;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class CekKrsLulusanForMakulAkhir
 */
@WebServlet("/CekKrsLulusanForMakulAkhir")
public class CekKrsLulusanForMakulAkhir extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CekKrsLulusanForMakulAkhir() {
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
			String thsms_lulus = request.getParameter("thsms_lulus");
			//System.out.println("thsms_lulus="+thsms_lulus);
			SearchDbTrlsm sdt = new SearchDbTrlsm();
			Vector v = sdt.getListLulusanTanpaMkAkhir(thsms_lulus);
			if(v!=null && v.size()>0) {
				//KDPSTTRAKM,NPMHSTRAKM,NMMHSMSMHS,NIMHSMSMHS,SKSEMTRAKM		
				v.add(0,"PRODI`NPM`NAMA`KETERANGAN");
				v.add(0,"850px"); 
				v.add(0,"5`15`30`50"); //total = 95, krn 5% jatah untuk norut
				v.add(0,"center`center`left`left");
				v.add(0,"String`String`String`String");
				
				session.setAttribute("v", v);
			}
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
			//String uri = request.getRequestURI();
			//String url = PathFinder.getPath(uri, target);
			//request.getRequestDispatcher(url).forward(request,response);
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

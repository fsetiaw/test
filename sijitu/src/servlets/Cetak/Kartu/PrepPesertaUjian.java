package servlets.Cetak.Kartu;

import java.io.IOException;
import java.util.ListIterator;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.classPoll.SearchDbClassPoll;
import beans.dbase.daftarUlang.SearchDbInfoDaftarUlangTable;
import beans.dbase.dosen.SearchDbDsn;
import beans.dbase.mhs.btstu.SearchDbInfoMhsBtstu;
import beans.dbase.mhs.kurikulum.SearchDbInfoKurikulum;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class PrepPesertaUjian
 */
@WebServlet("/PrepPesertaUjian")
public class PrepPesertaUjian extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepPesertaUjian() {
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
			/*
			PrintWriter out = response.getWriter();
			String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url).forward(request,response);
			*/
			String target_thsms = Checker.getThsmsNow();
			String target_kdpst = request.getParameter("target_kdpst");
			String tipe_ujian = request.getParameter("tipe_ujian");
			String cmd = request.getParameter("cmd");
			SearchDbInfoDaftarUlangTable sdi = new SearchDbInfoDaftarUlangTable();
			Vector vf = sdi.getListMhsYgSdhMengajukanPengajuanDaftarUlang(target_thsms, target_kdpst);
			if(vf!=null) {
				//kdpst+"`"+npmhs+"`"+nmmhs+"`"+nimhs+"`"+approved
				vf.add(0,"PRODI`NPM`NAMA`NIM`STATUS HEREGISTRASI");
				vf.add(0,"700px");
				vf.add(0,"5`15`45`15`15"); //total = 95, krn 5% jatah untuk norut
				vf.add(0,"center`center`left`center`center");
				vf.add(0,"String`String`String`String`String");
			}
			session.setAttribute("v", vf);
			session.setAttribute("tmp",tipe_ujian);
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

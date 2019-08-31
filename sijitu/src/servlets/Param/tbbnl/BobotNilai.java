package servlets.Param.tbbnl;

import java.io.IOException;
import java.util.ListIterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.tbbnl.SearchDbTbbnl;
import beans.dbase.trnlm.SearchDbTrnlm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.PathFinder;
import beans.tools.Tool;

/**
 * Servlet implementation class BobotNilai
 */
@WebServlet("/BobotNilai")
public class BobotNilai extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BobotNilai() {
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
			//System.out.println("itbbnl");
			//Vector v_kdpst= isu.getScopeUpd7des2012ReturnDistinctKdpst
			Vector v_kdpst=		isu.getScopeUpd7des2012ProdiOnly_rev2("tbbnl");
			v_kdpst=isu.getScopeUpd11Jan2016ProdiOnly("tbbnl");
			ListIterator li = v_kdpst.listIterator();
			String list_kdpst = null;
			while (li.hasNext()) {
				String brs = (String)li.next();
				String kdpst = Tool.getTokenKe(brs, 2);
				if(list_kdpst==null) {
					list_kdpst=new String("`");
				}
				list_kdpst = list_kdpst+kdpst+"`";
				
			}
			//System.out.println("list_kdpst="+list_kdpst);
				
			//Vector v_scope = isu.returnScopeProdiOnlySortByKampusWithListIdobj("tbbnl");
			//v_scope = Converter.convertVscopeidToKdpst(v_scope);
			SearchDbTbbnl sdt = new SearchDbTbbnl(isu.getNpm());
			Vector v = sdt.getInfoTabelNilaiYgBerlakuPerKdpstAtThsmsInputNilai(list_kdpst);
			request.setAttribute("v_kdpst_nilai", v);
			Vector vprodi = Getter.getListProdi();
			Vector vkmp = Getter.getListAllKampus();
			request.setAttribute("vprodi", vprodi);
			request.setAttribute("vkmp",vkmp);
			String atMenu = request.getParameter("atMenu");
			//ToUnivSatyagama/WebContent/InnerFrame/Parameter/BobotNilai/formBobotNilai.jsp
			String target = Constants.getRootWeb()+"/InnerFrame/Parameter/BobotNilai/formBobotNilai.jsp";
		    String uri = request.getRequestURI();
		    String url = PathFinder.getPath(uri, target);

		    request.getRequestDispatcher(url+"?atMenu="+atMenu).forward(request,response);

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

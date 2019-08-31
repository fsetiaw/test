package servlets.search.trakm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ListIterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.trakm.CekTotKrs;
import beans.dbase.trakm.SearchDbTrakm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Converter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class CekTotSksKrsMhs
 */
@WebServlet("/CekTotSksKrsMhs")
public class CekTotSksKrsMhs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CekTotSksKrsMhs() {
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
		//System.out.println("masuk");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			String sks_condition = request.getParameter("sks");
			String thsms = request.getParameter("thsms");
			Vector v_scope = isu.returnScopeProdiOnlySortByKampusWithListIdobj_v1("s");
			if(v_scope!=null && v_scope.size()>0) {
				v_scope=  Converter.convertVscopeidToKdpst(v_scope);
				
				//CekTotKrs ctk = new CekTotKrs(); harusnya gabung searchdb
				SearchDbTrakm ctk = new SearchDbTrakm();
				Vector v_rs = ctk.getNpmhsGivenTotalKrsCondition(thsms, sks_condition, v_scope);
				//ListIterator li = v_rs.listIterator();
				//while(li.hasNext()) {
				//	String brs = (String)li.next();
				//	//System.out.println(brs);
				//}
				if(v_rs!=null) {
					//KDPSTTRAKM,NPMHSTRAKM,NMMHSMSMHS,NIMHSMSMHS,SKSEMTRAKM		
					v_rs.add(0,"PRODI`NPM`NAMA`NIM`SKS");
					v_rs.add(0,"800px"); 
					v_rs.add(0,"5`15`55`15`5"); //total = 95, krn 5% jatah untuk norut
					v_rs.add(0,"center`center`left`center`center");
					v_rs.add(0,"String`String`String`String`String");
					
					session.setAttribute("v", v_rs);
				}
				/*
				//THSMSTRAKM,KDPSTTRAKM,NPMHSTRAKM,NMMHSMSMHS,NIMHSMSMHS,SKSEMTRAKM
				if(v_rs!=null) {
					v_rs.add(0,"800px"); 
					v_rs.add(0,"10`15`40`15`15");
					v_rs.add(0,"center`center`left`center`center");
					v_rs.add(0,"PRODI`NPM`NAMA`NIM`SKS semester");
					v_rs.add(0,"String`String`String`String`String`String");	
				}
				
				
				session.setAttribute("v", v_rs);
				*/
			}
			
			
			//PrintWriter out = response.getWriter();
			//String target = Constants.getRootWeb()+"/InnerFrame/InnerFrame/sql/ResultSet.jsp";
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

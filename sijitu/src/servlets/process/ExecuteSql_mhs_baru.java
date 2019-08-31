package servlets.process;

import java.io.IOException;
import java.util.ListIterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.sql.ExecuteCmd;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class ExecuteSql_mhs_baru
 */
@WebServlet("/ExecuteSql_mhs_baru")
public class ExecuteSql_mhs_baru extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecuteSql_mhs_baru() {
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
			//
			//System.out.println("exe swl mhs baru");
			String target_kdkmp=request.getParameter("target_kdkmp");
			String target_thsms=request.getParameter("target_thsms");
			String target_kdpst=request.getParameter("target_kdpst");
			
			String mhs_perem=request.getParameter("mhs_perem");
			String mhs_laki=request.getParameter("mhs_laki");
			String mhs_pindah=request.getParameter("mhs_pindah");
			String mhs_baru=request.getParameter("mhs_baru");
			Vector v_body = null;
			Vector v_header = new Vector();
			ListIterator lih = v_header.listIterator();
			//format vector untuk eksel
			//1. tipe
			//2. nama colomn
			//3. value
			lih.add("string`string`string`string");
			lih.add("TOTAL MHS BARU`TOTAL MHS PINDAHAN`TOTAL MHS PRIA`TOTAL MHS WANITA");
			lih.add(mhs_baru+"`"+mhs_pindah+"`"+mhs_laki+"`"+mhs_perem);
			
			//System.out.println("target_kdkmp="+target_kdkmp);
			//System.out.println("target_thsms="+target_thsms);
			//System.out.println("target_kdpst="+target_kdpst);
			if(!Checker.isStringNullOrEmpty(target_kdkmp)&&!Checker.isStringNullOrEmpty(target_kdpst)&&!Checker.isStringNullOrEmpty(target_thsms)) {
				String sql_cmd = "SELECT NPMHSMSMHS as NPM,NMMHSMSMHS as NAMA, KDJEKMSMHS as 'LAKI/PEREMPUAN',STPIDMSMHS as 'BARU/PINDAHAN' FROM CIVITAS A inner join OBJECT B on A.ID_OBJ=B.ID_OBJ where KODE_KAMPUS_DOMISILI='"+target_kdkmp+"' and KDPSTMSMHS='"+target_kdpst+"' and SMAWLMSMHS='"+target_thsms+"'";	
				ExecuteCmd exc = new ExecuteCmd();
				v_body = exc.executeCmd_v2(sql_cmd);
			}
			if(v_body==null) {
				v_body = new Vector();
			}
			request.setAttribute("v_body", v_body);
			request.setAttribute("v_header", v_header);
			String target = Constants.getRootWeb()+"/InnerFrame/sql/ResultSet_2Vector.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url).forward(request,response);
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

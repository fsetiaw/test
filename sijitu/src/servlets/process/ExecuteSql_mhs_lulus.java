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
import beans.tools.Converter;
import beans.tools.PathFinder;
import beans.tools.Tool;

/**
 * Servlet implementation class ExecuteSql_mhs_lulus
 */
@WebServlet("/ExecuteSql_mhs_lulus")
public class ExecuteSql_mhs_lulus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecuteSql_mhs_lulus() {
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
			//System.out.println("kok ngga kesini");
			String target_kdkmp=request.getParameter("target_kdkmp");
			String target_thsms=request.getParameter("target_thsms");
			String target_kdpst=request.getParameter("target_kdpst");
			String tot_trlsm=request.getParameter("tot_trlsm");
			String tot_req=request.getParameter("tot_req");
			String tot_wip=request.getParameter("tot_wip");
			String show_angel = (String)session.getAttribute("show_angel");
			//System.out.println("angel_included="+show_angel);
			
			Vector v_multi_body = null;
			Vector v_multi_header = new Vector();
			Vector v_header = new Vector();
			ListIterator lih = v_header.listIterator();
			//format vector untuk eksel
			//1. tipe
			//2. nama colomn
			//3. value
			lih.add("string`string");
			lih.add("TOTAL PENGAJUAN KELULUSAN`TOTAL PROSES PERSETUJUAN");
			lih.add(tot_req+"`"+tot_wip);
			v_multi_header.add(0,v_header);//[0]
			v_multi_header.add(0,"SUMMARY PENGAJUAN KELULUSAN ["+Converter.getDetailKdpst_v1(target_kdpst)+"]");//[1]

			if(!Checker.isStringNullOrEmpty(target_kdkmp)&&!Checker.isStringNullOrEmpty(target_kdpst)&&!Checker.isStringNullOrEmpty(target_thsms)) {
				String sql_cmd = null;
				if(show_angel!=null && show_angel.equalsIgnoreCase("true")) {
					sql_cmd = "SELECT KDPSTMSMHS as 'PRODI',NPMHS as NPM,NMMHSMSMHS as NAMA,SMAWLMSMHS as ANGKATAN,LOCKED as 'SUDAH DISETUJUI' from TRLSM A inner join CIVITAS B on NPMHS=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ left join TOPIK_PENGAJUAN D on NPMHSMSMHS=CREATOR_NPM where THSMS='"+target_thsms+"' and STMHS='L' and KDPSTMSMHS='"+target_kdpst+"' and KODE_KAMPUS_DOMISILI='"+target_kdkmp+"' order by A.NPMHS";
				}
				else {
					sql_cmd = "SELECT KDPSTMSMHS as 'PRODI',NPMHS as NPM,NMMHSMSMHS as NAMA,SMAWLMSMHS as ANGKATAN,LOCKED as 'SUDAH DISETUJUI' from TRLSM A inner join CIVITAS B on NPMHS=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ left join TOPIK_PENGAJUAN D on NPMHSMSMHS=CREATOR_NPM where MALAIKAT=false and THSMS='"+target_thsms+"' and STMHS='L' and KDPSTMSMHS='"+target_kdpst+"' and KODE_KAMPUS_DOMISILI='"+target_kdkmp+"' order by A.NPMHS";
				}
				
				//System.out.println("sql= "+sql_cmd);
				
				ExecuteCmd exc = new ExecuteCmd();
				Vector v_body = exc.executeCmd_v2(sql_cmd);
				if(v_multi_body==null) {
					v_multi_body = new Vector();
				}
				v_multi_body.add(0,v_body);
				v_multi_body.add(0,"LIST MAHASISWA LULUS ["+Converter.getDetailKdpst_v1(target_kdpst)+"]");
			}
			
			
			if(v_multi_body!=null) {
				try {
					v_multi_body = Tool.removeDuplicateFromVector(v_multi_body);
				}
				catch(Exception e) {}
				
			}
			request.setAttribute("v_multi_body", v_multi_body);
			request.setAttribute("v_multi_header", v_multi_header);
			String target = Constants.getRootWeb()+"/InnerFrame/sql/ResultSet_Multi_Vector_Header_Body.jsp";
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

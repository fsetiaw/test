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

/**
 * Servlet implementation class ExecuteSql_mhs_aktif
 */
@WebServlet("/ExecuteSql_mhs_aktif")
public class ExecuteSql_mhs_aktif extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecuteSql_mhs_aktif() {
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
			String tot_mhs_aktif=request.getParameter("tot_mhs_aktif");
			String tot_mhs_non_aktif=request.getParameter("tot_mhs_non_aktif");
			
			
			Vector v_multi_body = null;
			Vector v_multi_header = new Vector();
			Vector v_header = new Vector();
			ListIterator lih = v_header.listIterator();
			//format vector untuk eksel
			//1. tipe
			//2. nama colomn
			//3. value
			lih.add("string`string");
			lih.add("TOTAL MHS AKTIF`TOTAL CUTI & NON AKTIF");
			lih.add(tot_mhs_aktif+"`"+tot_mhs_non_aktif);
			v_multi_header.add(0,v_header);//[0]
			v_multi_header.add(0,"SUMMARY MAHASISWA AKTIF ["+Converter.getDetailKdpst_v1(target_kdpst)+"]");//[1]

			if(!Checker.isStringNullOrEmpty(target_kdkmp)&&!Checker.isStringNullOrEmpty(target_kdpst)&&!Checker.isStringNullOrEmpty(target_thsms)) {
				String sql_cmd = "SELECT distinct NPMHSMSMHS as NPM,NMMHSMSMHS as NAMA,SMAWLMSMHS as ANGKATAN FROM CIVITAS A inner join TRNLM B on NPMHSMSMHS=NPMHSTRNLM inner join OBJECT C on A.ID_OBJ=C.ID_OBJ where KDPSTTRNLM='"+target_kdpst+"' and THSMSTRNLM='"+target_thsms+"' and KODE_KAMPUS_DOMISILI='"+target_kdkmp+"'";	
				ExecuteCmd exc = new ExecuteCmd();
				Vector v_body = exc.executeCmd_v2(sql_cmd);
				if(v_multi_body==null) {
					v_multi_body = new Vector();
				}
				v_multi_body.add(0,v_body);
				v_multi_body.add(0,"LIST MAHASISWA AKTIF ["+Converter.getDetailKdpst_v1(target_kdpst)+"]");
			}
			
			if(!Checker.isStringNullOrEmpty(target_kdkmp)&&!Checker.isStringNullOrEmpty(target_kdpst)&&!Checker.isStringNullOrEmpty(target_thsms)) {
				String sql_cmd = "SELECT distinct NPMHS as NPM,NMMHSMSMHS as NAMA,SMAWLMSMHS as ANGKATAN,STMHS as STATUS FROM TRLSM A inner join CIVITAS B on NPMHS=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ where KDPSTMSMHS='"+target_kdpst+"' and THSMS='"+target_thsms+"' and KODE_KAMPUS_DOMISILI='"+target_kdkmp+"' and STMHS='C'";
				//String sql_cmd = "SELECT distinct NPMHSMSMHS FROM CIVITAS A inner join TRNLM B on NPMHSMSMHS=NPMHSTRNLM inner join OBJECT C on A.ID_OBJ=C.ID_OBJ where KDPSTTRNLM='"+target_kdpst+"' and THSMSTRNLM='"+target_thsms+"' and KODE_KAMPUS_DOMISILI='"+target_kdkmp+"'";	
				ExecuteCmd exc = new ExecuteCmd();
				Vector v_body = exc.executeCmd_v2(sql_cmd);
				if(v_multi_body==null) {
					v_multi_body = new Vector();
				}
				v_multi_body.add(0,v_body);
				v_multi_body.add(0,"LIST MAHASISWA CUTI ["+Converter.getDetailKdpst_v1(target_kdpst)+"]");
			}
			//if(v_multi_body==null) {
			//	v_multi_body = new Vector();
			//}
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

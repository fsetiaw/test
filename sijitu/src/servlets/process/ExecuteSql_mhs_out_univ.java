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
 * Servlet implementation class ExecuteSql_mhs_out_univ
 */
@WebServlet("/ExecuteSql_mhs_out_univ")
public class ExecuteSql_mhs_out_univ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecuteSql_mhs_out_univ() {
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
			String tot_req_out=request.getParameter("tot_req_out");
			String tot_req_do_wip=request.getParameter("tot_req_do_wip");
			String tot_req_out_wip=request.getParameter("tot_req_out_wip");
			String tot_req_do=request.getParameter("tot_req_do");
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
			lih.add("TOTAL PENGAJUAN KELUAR & D.O.`TOTAL PROSES PERSETUJUAN");
			lih.add((Integer.parseInt(tot_req_out) +Integer.parseInt(tot_req_do))+"`"+((Integer.parseInt(tot_req_out_wip) +Integer.parseInt(tot_req_do_wip))));
			v_multi_header.add(0,v_header);//[0]
			v_multi_header.add(0,"SUMMARY PENGAJUAN KELUAR & D.O. ["+Converter.getDetailKdpst_v1(target_kdpst)+"]");//[1]

			if(!Checker.isStringNullOrEmpty(target_kdkmp)&&!Checker.isStringNullOrEmpty(target_kdpst)&&!Checker.isStringNullOrEmpty(target_thsms)) {
				String sql_cmd = null;
				if(show_angel!=null && show_angel.equalsIgnoreCase("true")) {
					sql_cmd = "select A.KDPST as PRODI,NPMHS As NPM,NMMHSMSMHS as NAMA,SMAWLMSMHS as ANGKATAN,STMHS as STATUS,MALAIKAT as 'MHS VALID' from TRLSM A inner join CIVITAS B on A.NPMHS=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ where A.THSMS='"+target_thsms+"' and A.KDPST='"+target_kdpst+"' and (STMHS='D' or STMHS='DO' or STMHS='K') and KODE_KAMPUS_DOMISILI='"+target_kdkmp+"' union all SELECT KDPSTMSMHS as PRODI,NPMHSMSMHS as NPM,NMMHSMSMHS as NAMA,SMAWLMSMHS as ANGKATAN,TIPE_PENGAJUAN as 'STATUS',MALAIKAT as 'MHS VALID' FROM TOPIK_PENGAJUAN A inner join CIVITAS B on CREATOR_NPM=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ where KDPSTMSMHS='"+target_kdpst+"' and TARGET_THSMS_PENGAJUAN='"+target_thsms+"' and BATAL=false and (TIPE_PENGAJUAN='DO' or TIPE_PENGAJUAN='KELUAR') and TARGET_KDPST is null and REJECTED is null and KODE_KAMPUS_DOMISILI='"+target_kdkmp+"'";
				}
				else {
					sql_cmd = "select A.KDPST as PRODI,NPMHS As NPM,NMMHSMSMHS as NAMA,SMAWLMSMHS as ANGKATAN,STMHS as STATUS,MALAIKAT as 'MHS VALID' from TRLSM A inner join CIVITAS B on A.NPMHS=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ where A.THSMS='"+target_thsms+"' and A.KDPST='"+target_kdpst+"' and (STMHS='D' or STMHS='DO' or STMHS='K') and KODE_KAMPUS_DOMISILI='"+target_kdkmp+"' and MALAIKAT=false union all SELECT KDPSTMSMHS as PRODI,NPMHSMSMHS as NPM,NMMHSMSMHS as NAMA,SMAWLMSMHS as ANGKATAN,TIPE_PENGAJUAN as 'STATUS',MALAIKAT as 'MHS VALID' FROM TOPIK_PENGAJUAN A inner join CIVITAS B on CREATOR_NPM=NPMHSMSMHS inner join OBJECT C on B.ID_OBJ=C.ID_OBJ where KDPSTMSMHS='"+target_kdpst+"' and TARGET_THSMS_PENGAJUAN='"+target_thsms+"' and BATAL=false and (TIPE_PENGAJUAN='DO' or TIPE_PENGAJUAN='KELUAR') and TARGET_KDPST is null and REJECTED is null and KODE_KAMPUS_DOMISILI='"+target_kdkmp+"' and MALAIKAT=false";
				}
				
				//System.out.println("sql= "+sql_cmd);
				
				ExecuteCmd exc = new ExecuteCmd();
				Vector v_body = exc.executeCmd_v2_khusus1(sql_cmd);
				if(v_multi_body==null) {
					v_multi_body = new Vector();
				}
				v_multi_body.add(0,v_body);
				v_multi_body.add(0,"LIST MAHASISWA KELUAR & D.O. ["+Converter.getDetailKdpst_v1(target_kdpst)+"]");
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

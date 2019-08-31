package servlets.trlsm;

import java.io.IOException;
import java.util.ListIterator;
import java.util.StringTokenizer;
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
import beans.dbase.overview.maintenance.MaintenanceOverview;
import beans.dbase.sql.ExecuteCmd;
import beans.dbase.trlsm.SearchDbTrlsm;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.PathFinder;
import beans.tools.filter.FilterKampus;

/**
 * Servlet implementation class GetDataErrPengajuanVsTrlsm
 */
@WebServlet("/GetDataErrPengajuanVsTrlsm")
public class GetDataErrPengajuanVsTrlsm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDataErrPengajuanVsTrlsm() {
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
			//System.out.println("okay");
			String tot_err_data = request.getParameter("tot_err_data");
			Vector v_multi_body = null;
			Vector v_multi_header = new Vector();
			Vector v_header = new Vector();
			ListIterator lih = v_header.listIterator();
			//format vector untuk eksel
			//1. tipe
			//2. nama colomn
			//3. value
			lih.add("string");
			lih.add("TOTAL ERROR DATA");
			lih.add(tot_err_data);
			v_multi_header.add(0,v_header);//[0]
			v_multi_header.add(0,"KETERANGAN ERROR: PENGAJUAN TELAH DISETUJUI TAPI TIDAK TERCATAT PADA STATUS AKHIR MAHASISWA [TRLSM]");//[1]
			
			String target_thsms = Checker.getThsmsNow();
			Vector v_list =  isu.returnTknDistinctInfoProdiOnlyGivenCommand("s", true, target_thsms)	;
			ListIterator li = v_list.listIterator();
			String sql_cmd = "";
			while(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				st.nextToken();
				String kdpst = st.nextToken();
				st.nextToken();
				st.nextToken();
				sql_cmd = sql_cmd+"CREATOR_KDPST='"+kdpst+"'";
				if(li.hasNext()) {
					sql_cmd = sql_cmd+" or ";
				}
						
			}
			if(!Checker.isStringNullOrEmpty(sql_cmd)) {
				sql_cmd = "SELECT TARGET_THSMS_PENGAJUAN as 'THSMS PENGAJUAN',TIPE_PENGAJUAN as 'JENIS PENGAJUAN',CREATOR_NPM as NPM,CREATOR_NMM as NAMA  FROM TOPIK_PENGAJUAN A LEFT join TRLSM B on (CREATOR_NPM=NPMHS AND TARGET_THSMS_PENGAJUAN=THSMS) where ("+sql_cmd+") and (TIPE_PENGAJUAN='KELULUSAN' OR TIPE_PENGAJUAN='KELUAR' OR TIPE_PENGAJUAN='PINDAH_PRODI' OR TIPE_PENGAJUAN='CUTI' OR TIPE_PENGAJUAN='DO') AND LOCKED=TRUE AND REJECTED IS NULL AND NPMHS IS NULL";
				//System.out.println("sql_cmd="+sql_cmd);
				ExecuteCmd exc = new ExecuteCmd();
				Vector v_body = exc.executeCmd_v2(sql_cmd);
				if(v_multi_body==null) {
					v_multi_body = new Vector();
				}
				v_multi_body.add(0,v_body);
				v_multi_body.add(0,"LIST INFORMASI DATA ERROR");
				
			}
			/*
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
			*/
			
			//if(v_multi_body==null) {
			//	v_multi_body = new Vector();
			//}
			session.setAttribute("v_multi_body", v_multi_body);
			session.setAttribute("v_multi_header", v_multi_header);
			//String target = Constants.getRootWeb()+"/InnerFrame/sql/ResultSet_Multi_Vector_Header_Body.jsp";
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

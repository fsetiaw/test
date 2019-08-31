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
import beans.dbase.trlsm.SearchDbTrlsm;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;
import beans.tools.Tool;


/**
 * Servlet implementation class GetDataLulusan
 */
@WebServlet("/GetDataLulusan")
public class GetDataLulusan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDataLulusan() {
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
			//System.out.println("siap lulus");
			SearchDbTrlsm sdt = new SearchDbTrlsm();
			
			String cmd_used = request.getParameter("cmd_used");
			//System.out.println("cmd_used="+cmd_used);
			String tkn_thsms = request.getParameter("target_thsms");
			String tkn_kdpst = request.getParameter("target_kdpst");
			Vector v = null;
			if(tkn_kdpst!=null && tkn_kdpst.equalsIgnoreCase("all")) {
				tkn_kdpst=null;
				v = isu.getScopeObjScope_vFinal(cmd_used, true, false, true, "OBJ_DESC", null);
				tkn_kdpst = Tool.returnTokenKpdst(v,null); //seperator = spase
			}
			
			Vector v_npmhs = sdt.getDataLulusan(tkn_kdpst, tkn_thsms);
			
			if(v_npmhs!=null) {
				//System.out.println("size="+v_npmhs.size());
				//li.add(       thsms+"`"+            kdpti+"`"+kdpst+"`"+            nimhs+"`"+             nmmhs+"`"+      skstt+"`"+tglls+"`"+         tglwis+"`"+     tplhr+"`"+  tglhr+"`"+     npmhs+"`"+            nirl+"`"+tglskr+"`"+noskr+"`"+judul+"`"+noija+"`"+tgl_cetak);
				v_npmhs.add(0,"TAHUN SEMESTER LULUS`KODE PTS`KODE PROGRAM STUDI`NOMOR INDUK MAHASISWA (NIM)`NAMA WISUDAWAN`TOTAL SKS`NILAI IPK`TANGGAL YUDISIUM`TANGGAL WISUDA`TEMPAT LAHIR`TANGGAL LAHIR`NOMOR POKOK MAHASISWA (NPM)`NIRL`TGL SK REKTOR`NO SK REKTOR`JUDUL`NO IJAZAH`TGL TERBIT");
				v_npmhs.add(0,"1024px");
				v_npmhs.add(0,"3`3`3`5`6`3`3`5`5`6`6`6`6`6`6`11`6`6"); //total = 95, krn 5% jatah untuk norut
				v_npmhs.add(0,"center`center`center`center`left`center`center`center`center`center`center`center`center`center`center`center`center`center");
				v_npmhs.add(0,"String`String`String`String`String`String`String`String`String`String`String`String`String`String`String`String`String`String");
			}
			else {
				//System.out.println("size=0");
			}
			//System.out.println("list_thsms="+list_thsms); 	
			//sdt.getDataLulusan(v,target_thsms);
			//select THSMS as 'TAHUN SEMESTER LULUS',KDPTIMSMHS as 'KODE PTS',KDPSTMSMHS as 'KODE PROGRAM STUDI',NIMHSMSMHS as 'NOMOR INDUK MAHASISWA (NIM)',NMMHSMSMHS as 'NAMA WISUDAWAN',SKSTT as 'TOTAL SKS',TGLRE as 'TANGGAL YUDISIUM',TGL_WISUDA as 'TANGGAL WISUDA', TPLHRMSMHS as 'TEMPAT LAHIR', TGLHRMSMHS as 'TANGGAL LAHIR', NPMHSMSMHS as 'NOMOR POKOK MAHASISWA (NPM)',NIRL  from CIVITAS left join TRLSM on NPMHSMSMHS=NPMHS where STMHS='L' and THSMS='20162'
			
			
			session.setAttribute("v", v_npmhs);
			
			
			
			//int updated = udt.setBlawlBlakh(target_thsms, tkn_kdpst);
			//Vector v_npmhs = new Vector();
			//v_npmhs.add(0,""+updated);
			//v_npmhs.add(0,"TOTAL DATA UPDATED");
			//v_npmhs.add(0,"600px");
			//v_npmhs.add(0,"95");
			//v_npmhs.add(0,"center");
			//v_npmhs.add(0,"String");
			//session.setAttribute("v", v_npmhs);
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

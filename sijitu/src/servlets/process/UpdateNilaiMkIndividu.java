package servlets.process;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jdbc.pool.DataSource;

import beans.dbase.trnlm.UpdateDbTrnlm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateNilaiMkIndividu
 */
@WebServlet("/UpdateNilaiMkIndividu")
public class UpdateNilaiMkIndividu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateNilaiMkIndividu() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		

        
        
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
			//System.out.println("sampe");
			String cmd = request.getParameter("target_cmd");
			String npmhs = request.getParameter("target_npmhs");
			String nmmhs = request.getParameter("target_nmmhs");
			String idobj = request.getParameter("target_idobj");
			String objlv = request.getParameter("target_objlv");
			String kdpst = request.getParameter("target_kdpst");
			String[]nilai_akhir = request.getParameterValues("nilai_akhir");
			UpdateDbTrnlm udt = new UpdateDbTrnlm(isu.getNpm());
			udt.updateNilaiPerIndividu(nilai_akhir,npmhs);
		//for(int i=0;i<nilai_akhir.length;i++) {
			//	//System.out.println(nilai_akhir[i]+"<br/>");
			//}
			//System.out.println(npmhs);
			//System.out.println(nmmhs);
			//System.out.println(idobj);
			//System.out.println(objlv);
			//System.out.println(kdpst);
			//String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/ShowNotificationThenRedirect.jsp";
			//String uri = request.getRequestURI();
			//String url = PathFinder.getPath(uri, target);
			
			////System.out.println("redirectTo="+redirectTo);
			//request.getRequestDispatcher(url+"?redirectTo="+redirectTo+"&paramNeeded="+paramNeeded).forward(request,response);
			request.getRequestDispatcher("get.histKrs?id_obj="+idobj+"&nmm="+nmmhs+"&npm="+npmhs+"&kdpst="+kdpst+"&obj_lvl="+objlv+"&cmd="+cmd).forward(request,response);
			//<a href="get.listMhsUtkPenilaian?thsms=<%=thsms_inp_nilai %>&idkmk=<%=idkmk %>&uniqueId=<%=unique_id %>&kdkmk=<%=kdkmk %>&nakmk=<%=nakmk %>&shift=<%=shift %>&noKlsPll=<%=noKlsPll %>&kode_kelas=<%=kode_kelas %>&kode_gedung=<%=kode_gedung %>&kode_kampus=<%=kode_kampus %>&kode_gabung_kls=<%=kode_gabung_kls %>&kode_gabung_kmp=<%=kode_gabung_kmp %>&skstm=<%=skstm %>&skspr=<%=skspr %>&skslp=<%=skslp %>&bolehEdit=<%=bolehEdit%>"><%= kdkmk+" - "+nakmk%></a>
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		
		doGet(request,response);
	}

}

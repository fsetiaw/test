package servlets.Get.ClassPool;

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
import beans.dbase.status_kehadiran_dosen.SearchDbkehadiranDosen;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.AddHocFunction;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class ListKelasAjar
 */
@WebServlet("/KehadiranKelas")
public class KehadiranKelas_v2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KehadiranKelas_v2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here
			
			//String atMenu = request.getParameter("atMenu");
			Vector vf = null;
			String cmd = request.getParameter("cmd");
			String target_kdpst = request.getParameter("target_kdpst");
			String target_shift = request.getParameter("target_shift");
			String target_thsms = request.getParameter("target_thsms");
			String backto = request.getParameter("backto");
			//System.out.println("target_thsms = "+target_thsms);
			if(target_thsms==null || Checker.isStringNullOrEmpty(target_thsms)) {
				target_thsms=(String)session.getAttribute("target_thsms");
			}
			else {
				session.setAttribute("target_thsms", target_thsms);
			}
			if(target_kdpst==null || Checker.isStringNullOrEmpty(target_kdpst)) {
				target_kdpst=(String)session.getAttribute("target_kdpst");
			}
			else {
				session.setAttribute("target_kdpst", target_kdpst);
			}
			if(target_shift==null || Checker.isStringNullOrEmpty(target_shift)) {
				target_shift=(String)session.getAttribute("target_shift");
			}
			else {
				session.setAttribute("target_shift", target_shift);
			}
			//if(Checker.isStringNullOrEmpty(target_kdpst)) {
				//cuma ngajar kelas
			//}
			//else {
				//String atMenu = request.getParameter("atMenu");
				String target = "";
				Vector list_kdpst_sad = isu.getScopeUpd7des2012ProdiOnlyButKeepOwn(cmd);
				//ListIterator li = list_kdpst_sad.listIterator();
				//while(li.hasNext()) {
				//	String brs = (String)li.next();
				//	//System.out.println("brs");
				//}
				SearchDbClassPoll scp = new SearchDbClassPoll(isu.getNpm());
				Vector vKlsAjar = scp.getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya(list_kdpst_sad, target_thsms, target_kdpst, target_shift);//disini sdh difilter kelas yg ada mhs sja
				//if(vKlsAjar!=null) {
				//	//System.out.println("vKlsAjar="+vKlsAjar.size());
				//}
				//else {
				//	//System.out.println("vKlsAjar=null");
				//}
				SearchDbkehadiranDosen sdb = new SearchDbkehadiranDosen(isu.getNpm());
				Vector vKehadiranDsn = null;
				
				if(Checker.isStringNullOrEmpty(target_kdpst)) {
					vf = vKlsAjar;
					//if(vf!=null) {
					//	vf = sdb.mergeClassPollDenganKehadiranDosen(vKlsAjar);
					//	//System.out.println("vf="+vf.size());
					//}
					//else {
					//	//System.out.println("vf is null");
					//}
				}
				else if(target_kdpst.equalsIgnoreCase("all")) {
					
					if((list_kdpst_sad==null || list_kdpst_sad.size()<1) && ((String)session.getAttribute("ada_kelas_yg_diajar")).equalsIgnoreCase("false")) {
						//System.out.println("no akses");
						/*
						 * BISA ADA AKSES KALO MEMANG ADA KELAS YANG DIAJAR
						 */
						vf = vKlsAjar;
					}
					else {
						vKehadiranDsn = sdb.getStatusKehadiranDosenHariIniDanKedepan(target_thsms,list_kdpst_sad,target_shift);
						//vKehadiranDsn = sdb.getStatusKehadiranDosenHariIniDanKedepan(target_thsms,target_kdpst,target_shift);
						//vf = sdb.mergeClassPollDenganKehadiranDosen(vKlsAjar,vKehadiranDsn);
						
					}
				}
				else {
					vKehadiranDsn = sdb.getStatusKehadiranDosenHariIniDanKedepan(target_thsms,target_kdpst,target_shift);
					//vKehadiranDsn = sdb.getStatusKehadiranDosenHariIniDanKedepan(target_thsms,target_kdpst,target_shift);
					//vf = sdb.mergeClassPollDenganKehadiranDosen(vKlsAjar,vKehadiranDsn);	
				}
				//if(vf!=null) {
				//	//System.out.println("vf="+vf.size());
				//}
				//else {
				//	//System.out.println("vf=null");
				//}
				
				//if(atMenu!=null && atMenu.equalsIgnoreCase("kehadiran")) {
				/*
				
				*/
			//}
				vf = sdb.mergeClassPollDenganKehadiranDosen(vKlsAjar);
				session.setAttribute("vInfoKehadiranDosen", vf);
				/*
				 * String target_kdpst = request.getParameter("target_kdpst");
			String target_shift = request.getParameter("target_shift");
			String target_thsms = request.getParameter("target_thsms");
				 */
				if(backto==null) {
					//System.out.println("backto");;
					target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/Absensi/DosenAjar/dashAbsensiDsnAjar.jsp";
					String uri = request.getRequestURI(); 
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url+"?cmd="+cmd).forward(request,response);	
				}
				else if(backto.equalsIgnoreCase("index")) {
					//System.out.println("backto");;
					target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/Absensi/DosenAjar/dashAbsensiDsnAjar.jsp";
					String uri = request.getRequestURI(); 
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url+"?cmd="+cmd).forward(request,response);	
				}
				
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

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
@WebServlet("/KehadiranKelas_v1")
public class KehadiranKelas_v1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KehadiranKelas_v1() {
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
			//System.out.println("hadir");
			String cmd = request.getParameter("cmd");
			String target_kdpst = request.getParameter("target_kdpst");
			String target_shift = request.getParameter("target_shift");
			String targetThsms = request.getParameter("target_thsms");

			//System.out.println("targetThsms="+targetThsms);
			if(targetThsms==null || Checker.isStringNullOrEmpty(targetThsms)) {
				targetThsms=Checker.getThsmsNow();
			}
			//String atMenu = request.getParameter("atMenu");
			
			Vector list_kdpst_ink = isu.getScopeUpd7des2012ProdiOnlyButKeepOwn(cmd);
			SearchDbClassPoll scp = new SearchDbClassPoll(isu.getNpm());
			
			Vector vKlsPenilaian = scp.getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya(list_kdpst_ink, targetThsms, target_kdpst, target_shift);
			//if(mode!=null&& mode.equalsIgnoreCase("input_nilai")) {
				
			//}
			//else {
				
				
			//}	
			session.setAttribute("v_tmp", vKlsPenilaian);
			//System.out.println("done");
			/*
			if(atMenu!=null && atMenu.equalsIgnoreCase("inputNilai")) {
				//System.out.println("input nilai");
				
				//Vector list_kdpst_ink = isu.getScopeUpd7des2012ReturnDistinctKdpst("ink");
				Vector list_kdpst_ink = isu.getScopeUpd7des2012ProdiOnlyButKeepOwn("ink");
				if(list_kdpst_ink==null || list_kdpst_ink.size()<1) {
					//System.out.println("no akses");
					
				}
				else {
					//SearchDbClassPoll scp = new SearchDbClassPoll(isu.getNpm());
					//Vector vKlsAjar = scp.getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya(list_kdpst_ink);
					//format lis.add(kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid+"`"+kdkmk);
					//System.out.println("csope= "+vKlsAjar.size());
					//session.setAttribute("vKlsAjar", vKlsAjar);
					target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/Nilai/dashPenilaian.jsp";
					String uri = request.getRequestURI(); 
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url).forward(request,response);
				}
				
				
				
			}
			else if(atMenu!=null && atMenu.equalsIgnoreCase("kehadiran")) {
				
				//Vector list_kdpst_sad = isu.getScopeUpd7des2012ReturnDistinctKdpst("sad");
				Vector list_kdpst_sad = isu.getScopeUpd7des2012ProdiOnlyButKeepOwn("sad");
				if((list_kdpst_sad==null || list_kdpst_sad.size()<1) && ((String)session.getAttribute("ada_kelas_yg_diajar")).equalsIgnoreCase("false")) {
					//System.out.println("no akses");
					
					 * BISA ADA AKSES KALO MEMANG ADA KELAS YANG DIAJAR
					
					
				}
				else {
					String target_thsms=Checker.getThsmsNow();
					SearchDbClassPoll scp = new SearchDbClassPoll(isu.getNpm());
					Vector vKlsAjar = scp.getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya(list_kdpst_sad, target_thsms);//disini sdh difilter kelas yg ada mhs sja
					//System.out.println("vKlsAjar="+vKlsAjar.size());
					//ListIterator li = vKlsAjar.listIterator();
					//while(li.hasNext()) {
					//	String brs = (String)li.next();
					//	//System.out.println("li-"+brs);
					//}
					//vKlsAjar = AddHocFunction.addKonsentrasiKurikulum(vKlsAjar, target_thsms);
					SearchDbkehadiranDosen sdb = new SearchDbkehadiranDosen(isu.getNpm());
					Vector vKehadiranDsn = sdb.getStatusKehadiranDosenHariIniDanKedepan();
					//System.out.println("vKehadiranDsn="+vKehadiranDsn.size());
					Vector vf = sdb.mergeClassPollDenganKehadiranDosen(vKlsAjar);
					//System.out.println("vf="+vf.size());
					session.setAttribute("vInfoKehadiranDosen", vf);
					//SearchDbkehadiranDosen sdd = new SearchDbkehadiranDosen(isu.getNpm());
					//Vector vKehadiranDosen = sdd.getListStatKehadiranDosenThsmsNow();
					//1. get info status kehadiran dosen
					//2. filter mana yg tglnya masih valid < tgl hari ini dan mana yg belum ada info samasekali
						//bila belum ada info maka nantinya 
					
					///ToUnivSatyagama/WebContent/InnerFrame/Prakuliah/BahanAjar/DashPengajuanBahanAjar.jsp
					target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/Absensi/DosenAjar/dashAbsensiDsnAjar.jsp";
					String uri = request.getRequestURI(); 
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url+"?target_thsms="+target_thsms).forward(request,response);
				}
			}
			//String uri = request.getRequestURI(); 
			//String url = PathFinder.getPath(uri, target);
			//request.getRequestDispatcher(url).forward(request,response);
			*/
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

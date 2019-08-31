package servlets.update.trnlm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.tbbnl.UpdateDbTbbnl;
import beans.dbase.trnlm.SearchDbTrnlm;
import beans.dbase.trnlm.UpdateDbTrnlm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class GantiNilaiKolomRobotYgNilaiLessThenMin
 */
@WebServlet("/GantiNilaiKolomRobotYgNilaiLessThenMin")
public class GantiNilaiKolomRobotYgNilaiLessThenMin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GantiNilaiKolomRobotYgNilaiLessThenMin() {
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
			//System.out.println("ahy");
			String nilai_min = request.getParameter("nilai_min");
			String target_thsms = request.getParameter("thsms");
			int updated = 0, updated1=0, updated2=0;
			int tot_size=0;
			//System.out.println("target_thsms="+target_thsms);
			if(!Checker.isStringNullOrEmpty(target_thsms) && !Checker.isStringNullOrEmpty(nilai_min)) {
				int tot_rec_effected = 0;
				//1. get krs dengan nilai tunda		
				SearchDbTrnlm sdt = new SearchDbTrnlm();
				UpdateDbTrnlm udt = new UpdateDbTrnlm();
				boolean lanjut = true;
				int limit = 1000;
				int offset = 0;
				int iter = 0;
				
				while(lanjut) {
					iter++;
					//System.out.println("iter="+iter);
					//System.out.println("offset="+offset);
					Vector v = sdt.getKrsYgMemilikiNilaiDibawahMinimal(target_thsms,Double.parseDouble(nilai_min), limit, offset);
					
					if(v!=null && v.size()>0) {
						tot_size = v.size();
						updated = udt.updateNilaiKolomRobotDgnNilai(v,"B`3`70");
					
					}
					
					if(v==null || (v!=null && v.size()<limit)) {
						lanjut = false;
						
					}
					else {
						//System.out.println("v_size="+v.size());
						offset = offset + limit;
					}
					//System.out.println("lanjut="+lanjut);
				}
				updated1 = udt.kasihNilaiMengulangUntukMkFinalAtAllThsmsAtKolomRobot(target_thsms);
				updated2 = udt.kasihNilaiMkFinalAtThsmsKelulusanAtKolomRobot(target_thsms);
				
				
			}
			Vector v_npmhs = new Vector();
			
			v_npmhs.add(0,"TOTAL KRS NILAI DIBERI NILAI SEMENTARA [MK-FINAL @ THSMS KELULUSAN]`"+updated2);
			v_npmhs.add(0,"TOTAL KRS NILAI DIUBAH JADI 'E' [MK FINAL @ THSMS NON KELULUSAN]`"+updated1);
			v_npmhs.add(0,"TOTAL KRS DGN NILAI TUNDA UPDATED`"+updated);
			v_npmhs.add(0,"TOTAL KRS DGN NILAI TUNDA`"+tot_size);
			v_npmhs.add(0,"KETERANGAN`VALUE");
			v_npmhs.add(0,"650px");
			v_npmhs.add(0,"85`10");
			v_npmhs.add(0,"left`center");
			v_npmhs.add(0,"String`String");
			session.setAttribute("v", v_npmhs);
			//System.out.println("pos 2");
			/*
			int updated = 0, updated1=0, updated2=0;
			Vector v_tot = sdt.getKrsYgMemilikiNilaiTunda();
			Vector v = sdt.getKrsYgMemilikiNilaiTunda(25000);
			UpdateDbTrnlm udt = new UpdateDbTrnlm();
			if(v!=null && v.size()>0) {
				updated = udt.updateNilaiKolomRobot(v);
			}
			updated1 = udt.kasihNilaiTundaUntukMkFinalAtAllThsmsAtKolomRobot();
			updated2 = udt.kasihNilaiMkFinalAtThsmsKelulusanAtKolomRobot();
			PrintWriter out = response.getWriter();
			if(v_tot==null) {
				out.println (
						"<html>"
						+ "<body>"
						//1. get krs dengan nilai tunda		
						+ "<h2> 1. GET KRS DGN NILAI TUNDA = 0</h2>"
						+ "<h2> 2. KASIH NILAI BY ROBOT, UPDATED = "+updated+"</h2>"
						+ "<h2> 3. KASIH NILAI T MK FINAL, UPDATED = "+updated1+"</h2>"
						+ "<h2> 4. KASIH NILAI MK FINAL AT THSMS KELULUSAN BY ROBOT, UPDATED = "+updated2+"</h2>"
						+ "</body>"
						+ "</html>"
						);
			}
			else {
				out.println (
						"<html>"
						+ "<body>"
						//1. get krs dengan nilai tunda		
						+ "<h2> 1. GET KRS DGN NILAI TUNDA = "+v_tot.size()+"</h2>"
						+ "<h2> 2. KASIH NILAI BY ROBOT, UPDATED = "+updated+"</h2>"
						+ "<h2> 3. KASIH NILAI T MK FINAL UPDATED = "+updated1+"</h2>"
						+ "<h2> 4. KASIH NILAI MK FINAL AT THSMS KELULUSAN BY ROBOT UPDATED = "+updated2+"</h2>"
						+ "</body>"
						+ "</html>"
						);
			}
			

			
			
			*/
			//System.out.println("uhuy");
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
			//String uri = request.getRequestURI();
			//String url = PathFinder.getPath(uri, target);
			
			//request.getRequestDispatcher("get.notifications?atBoxMenu="+atBoxMenu+"&status_proses="+msg).forward(request,response);
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

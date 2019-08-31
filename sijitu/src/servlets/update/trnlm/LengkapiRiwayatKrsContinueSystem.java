package servlets.update.trnlm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ListIterator;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.mhs.SearchDbInfoMhs;
import beans.dbase.tbbnl.UpdateDbTbbnl;
import beans.dbase.trnlm.SearchDbTrnlm;
import beans.dbase.trnlm.UpdateDbTrnlm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class LengkapiRiwayatKrsContinueSystem
 */
@WebServlet("/LengkapiRiwayatKrsContinueSystem")
public class LengkapiRiwayatKrsContinueSystem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LengkapiRiwayatKrsContinueSystem() {
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
			//System.out.println("okay");			//System.out.println("uhuy");
			String stmhs_value = request.getParameter("stmhs");
			String kdpst_value = request.getParameter("kdpst");
			String npmhs_value = request.getParameter("npmhs");
			//System.out.println("stmhs_value="+stmhs_value);
			//System.out.println("kdpst_value="+kdpst_value);
			//System.out.println("npmhs_value="+npmhs_value);
			
			if(!Checker.isStringNullOrEmpty(kdpst_value)&&!Checker.isStringNullOrEmpty(stmhs_value)) {
				int limit = 50;
				int offset = 0;
				int counter = 0;
				ListIterator li = null;
				boolean lanjut = true;
				SearchDbInfoMhs sdm = new SearchDbInfoMhs(isu.getNpm());
				while(lanjut) {
					//System.out.println("offset = "+offset);
					Vector v_list_target_npm = sdm.getListNpmhsGivenStmhs(kdpst_value, stmhs_value, limit, offset);
					if(v_list_target_npm!=null) {
						if(v_list_target_npm.size()<limit) {
							lanjut = false;
						}
						else {
							offset = offset + limit;	
						}
						
						li = v_list_target_npm.listIterator();
						while(li.hasNext()) {
							counter++;
							String npmhs = (String) li.next();
							String smawl = Checker.getSmawl(npmhs);
							//System.out.println(counter+". "+smawl+"`"+kdpst_value+"`"+npmhs);
							SearchDbTrnlm sdb = new SearchDbTrnlm(isu.getNpm());
							UpdateDbTrnlm udt = new UpdateDbTrnlm(isu.getNpm());
							String idKur = sdb.getIndividualKurikulum(kdpst_value, npmhs);
							Vector vTrnlp = sdb.getListMatakuliahTrnlp(kdpst_value, npmhs);
							udt.deleteMkTrnlpYgAdaDiTrnlm(vTrnlp, npmhs);
							Vector vTrlsm = sdb.getRiwayatTrlsm(npmhs);
							Vector vTrnlm = sdb.getHistoryKrsKhs_v1(kdpst_value, npmhs, idKur);
							Vector vMkKur = sdb.getListMatakuliahDalamKurikulum_v1(kdpst_value, idKur);
							String tknPaInfo = sdb.getHistoryNpmPa(kdpst_value, npmhs);
							String currentPa = sdb.getCurrrentPa(kdpst_value, npmhs);
							String kode_kmp = Getter.getDomisiliKampus(npmhs);
							Vector vNeedToBeInsert = sdb.continuSistemAdjustment(npmhs,vTrlsm,vTrnlp,vTrnlm,vMkKur,smawl,currentPa,kode_kmp);
							/*
							if(vTrnlm!=null) {
								ListIterator li = vTrnlm.listIterator();
								while(li.hasNext()) {
									String brs = (String)li.next();
									//System.out.println(brs);
								}
							}
							if(vNeedToBeInsert!=null) {
								ListIterator li = vNeedToBeInsert.listIterator();
								while(li.hasNext()) {
									String brs = (String)li.next();
									//System.out.println(brs);
								}
							}
							*/
							if(vNeedToBeInsert!=null && vNeedToBeInsert.size()>0) {
								udt.insertKrs(vNeedToBeInsert,npmhs, kdpst_value);
							}
							if(vTrlsm!=null && vTrlsm.size()>0) {
								udt.deleteKrs(vTrlsm, npmhs);
							}
						}
					}
					else {
						lanjut = false;
					}
				}	
			}
			else if(!Checker.isStringNullOrEmpty(npmhs_value)) {
				String kdpst = Checker.getKdpst(npmhs_value);
				String smawl = Checker.getSmawl(npmhs_value);
				SearchDbTrnlm sdb = new SearchDbTrnlm(isu.getNpm());
				UpdateDbTrnlm udt = new UpdateDbTrnlm(isu.getNpm());
				String idKur = sdb.getIndividualKurikulum(kdpst, npmhs_value);
				Vector vTrnlp = sdb.getListMatakuliahTrnlp(kdpst, npmhs_value);
				udt.deleteMkTrnlpYgAdaDiTrnlm(vTrnlp, npmhs_value);
				Vector vTrlsm = sdb.getRiwayatTrlsm(npmhs_value);
				Vector vTrnlm = sdb.getHistoryKrsKhs_v1(kdpst, npmhs_value, idKur);
				Vector vMkKur = sdb.getListMatakuliahDalamKurikulum_v1(kdpst, idKur);
				String tknPaInfo = sdb.getHistoryNpmPa(kdpst, npmhs_value);
				String currentPa = sdb.getCurrrentPa(kdpst, npmhs_value);
				String kode_kmp = Getter.getDomisiliKampus(npmhs_value);
				Vector vNeedToBeInsert = sdb.continuSistemAdjustment(npmhs_value,vTrlsm,vTrnlp,vTrnlm,vMkKur,smawl,currentPa,kode_kmp);
				/*
				if(vTrnlm!=null) {
					ListIterator li = vTrnlm.listIterator();
					while(li.hasNext()) {
						String brs = (String)li.next();
						//System.out.println(brs);
					}
				}
				if(vNeedToBeInsert!=null) {
					ListIterator li = vNeedToBeInsert.listIterator();
					while(li.hasNext()) {
						String brs = (String)li.next();
						//System.out.println(brs);
					}
				}
				*/
				if(vNeedToBeInsert!=null && vNeedToBeInsert.size()>0) {
					udt.insertKrs(vNeedToBeInsert,npmhs_value, kdpst);
				}
				if(vTrlsm!=null && vTrlsm.size()>0) {
					udt.deleteKrs(vTrlsm, npmhs_value);
				}
			}
			
			
			//System.out.println("==================done=======================");
			
			
			
			
			//String target = Constants.getRootWeb()+"/InnerFrame/standalone_proses/index_stp.jsp";
			//String uri = request.getRequestURI();
			//String url = PathFinder.getPath(uri, target);
			//request.getRequestDispatcher(url).forward(request,response);
			/*
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    int counter = 0;
			for(int i=0;i<10;i++) {
				counter++;
				waitForData();
				response.getWriter().write(counter);	
				//System.out.println("ok - "+counter);
			}
			*/
			
			
		}
	}
	
	public static void waitForData() {
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(2000));
	    } catch (InterruptedException e) {
	    	e.printStackTrace();
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

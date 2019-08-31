package servlets.maintenance;

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

import beans.dbase.mhs.SearchDbInfoMhs;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.dbase.trnlm.UpdateDbTrnlm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.AddHocFunction;
import beans.tools.Checker;
import beans.tools.Tool;

/**
 * Servlet implementation class GetListMhsMalaikatStatusQuo
 */
@WebServlet("/GetListMhsMalaikatStatusQuo")
public class GetListMhsMalaikatStatusQuo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetListMhsMalaikatStatusQuo() {
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
			String stmhs = request.getParameter("stmhs");
			String kdpst = request.getParameter("kdpst");
			String thsms = request.getParameter("thsms");
			//System.out.println("stmhs="+stmhs);
			String thsms_1 = Tool.returnPrevThsmsGivenTpAntara(thsms);
			//System.out.println("thsms = "+thsms);
			SearchDbInfoMhs sdim = new SearchDbInfoMhs();
			//1. cek mhs aktif @thsms-1
			boolean malaikat = true;
			Vector v_npmhs = sdim.getMhsAktifStatusQuo(thsms,malaikat);
			String tkn_npm = null;
			
			if(v_npmhs!=null) {
				/*
				 * semua malaikat langsung diinput krsnya, tp kadang blum ada KO nya
				 * jadi sekarang harus dicek dulu
				 * 
				 * proses berikut kayaknya ngga ada gunanya krn otomatis ini malaikat semua
				 * trus jangan li.remove dulu krn blum tau ada KOnya ato blum
				 */
				//li.set(kdpst+"`"+npmhs+"`"+nmmhs+"`"+nimhs+"`"+smawl+"`"+stpid+"`"+angel+"`"+kdjen);
				ListIterator li = v_npmhs.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					//System.out.println("baris="+brs);
					StringTokenizer st = new StringTokenizer(brs,"`");
					kdpst = st.nextToken();
					String npmhs = st.nextToken();
					String nmmhs = st.nextToken();
					String nimhs = st.nextToken();
					String smawl = st.nextToken();
					String stpid = st.nextToken();
					String angel = st.nextToken();
					if(angel.equalsIgnoreCase("true")) { 
						//if(stpid.equalsIgnoreCase("B")) {
							if(tkn_npm == null) {
								tkn_npm = new String(npmhs);
							}
							else {
								tkn_npm=tkn_npm+"`"+npmhs;
							}
							//li.remove();
						//}
						//else {
							//ignore yg sisa tinggal yg pindahan aja
						//}
					}
					else {
						li.remove();
					}
				}
			}
			Vector v_npm_no_ko = null;
			ListIterator lik = null;
			//SearchDbInfoMhs sdim = new SearchDbInfoMhs();
			if(!Checker.isStringNullOrEmpty(tkn_npm)) {
				//System.out.println("tkn_npm = "+tkn_npm);
				Vector v = AddHocFunction.hitungTotSemesterDariSamwlSampaiTargetThsmsDikurangNonAktifTrlsm_v1(tkn_npm, thsms);		
				v = sdim.addInfoKrklm(v);				
				v = sdim.addInfoIdobjKdjenKdpstKodeKmp(v);
				
				if(v!=null) {
					ListIterator li_npm_blum_ada_ko = v.listIterator();
					while(li_npm_blum_ada_ko.hasNext()) {
						String brs = (String)li_npm_blum_ada_ko.next();
	        			StringTokenizer st = new StringTokenizer(brs,"`");
	        			String npmhs = st.nextToken();
	        			String target_sms = st.nextToken();
	        			String id_krklm = st.nextToken();
	        			try {
	        				int test = Integer.parseInt(id_krklm);	 
	        				//valid lanjut insert
	        				UpdateDbTrnlm udt = new UpdateDbTrnlm();
	    					udt.insertKrsBasedOnKoAtThsms(v, thsms);
	        			}
	        			catch(Exception e) {
	        				if(v_npm_no_ko==null) {
	        					v_npm_no_ko = new Vector();
	        					lik =v_npm_no_ko.listIterator();
	        				}
	        				lik.add(npmhs);
	        			}
					}
					//UpdateDbTrnlm udt = new UpdateDbTrnlm();
					//udt.insertKrsBasedOnKoAtThsms(v, thsms);
				}
			}
			
			if(v_npm_no_ko!=null && v_npmhs!=null) {
				ListIterator li = v_npmhs.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					//cek apa termasuk yg blum ada ko
					boolean match = false;
					lik =v_npm_no_ko.listIterator();
					while(lik.hasNext()&&!match) {
						String npm_no_ko = (String)lik.next();
						if(brs.contains("`"+npm_no_ko+"`")) {
							match = true;
						}
					}
					if(!match) {
						li.remove();
					}
				}
				
				
			}
			if(Checker.isStringNullOrEmpty(stmhs)) {
				//mode show list
				if(v_npmhs!=null && v_npmhs.size()>0) {
					//kdpst+"`"+npmhs+"`"+nmmhs+"`"+nimhs+"`"+smawl+"`"+stpid+"`"+angel+"`"+kdjen
					
					//System.out.println("size="+v_npmhs.size());
					v_npmhs.add(0,"PRODI`NPM`NAMA`NIM`SMAWL`STPID`VALID`KDJEN");
					v_npmhs.add(0,"null");
					v_npmhs.add(0,"5`15`40`15`5`5`5`5");
					v_npmhs.add(0,"center`center`left`center`center`center`center`center");
					v_npmhs.add(0,"String`String`String`String`String`String`String`String");
				}
				else {
					v_npmhs=null;
				}
				session.setAttribute("v", v_npmhs);
			}
			else {
				//System.out.println("siap update");
				//mode update status 
				if(v_npmhs!=null && v_npmhs.size()>0) {
					UpdateDbTrlsm udt = new UpdateDbTrlsm();
					//kdpst+"`"+npmhs+"`"+nmmhs+"`"+nimhs+"`"+smawl+"`"+stpid+"`"+angel+"`"+kdjen
					//1. reduce vector hanya butuh kdpst & npmhs 
					ListIterator li = v_npmhs.listIterator();
					while(li.hasNext()) {
						String brs = (String)li.next();
						String tkn_kdpst = Tool.getTokenKe(brs, 1, "`");
						String tkn_npmhs = Tool.getTokenKe(brs, 2, "`");
						li.set(tkn_kdpst+"`"+tkn_npmhs);
						//System.out.println(tkn_kdpst+"`"+tkn_npmhs);
					}
					int i = udt.updStmhsTanpaBerita_v1(v_npmhs, stmhs, thsms);
					v_npmhs = new Vector();
					v_npmhs.add(0,""+i);	
					v_npmhs.add(0,"TOTAL DATA UPDATED");
					v_npmhs.add(0,"600px");
					v_npmhs.add(0,"95");
					v_npmhs.add(0,"center");
					v_npmhs.add(0,"String");
					
				}
				else {
					v_npmhs=null;
				}
				
				session.setAttribute("v", v_npmhs);
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

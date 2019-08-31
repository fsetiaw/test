package servlets.Router;

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

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.*;

/**
 * Servlet implementation class RouteBasedOnScopeKdpst
 */
@WebServlet("/RouteBasedOnScopeKdpst")
public class RouteBasedOnScopeKdpst extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RouteBasedOnScopeKdpst() {
        super();
        //TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO Auto-generated method stub
		response.getWriter();
		//System.out.println("raoute");
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here
			String atMenu = ""+request.getParameter("atMenu");
			String fwdTo =  ""+request.getParameter("fwdTo");
			//System.out.println("atMenu="+atMenu);
			//System.out.println("fwdTo="+fwdTo);
			/*
			Vector vScopeObjId = isu.getScopeObjIdFinal("hasSpmiMenu",true, true, false);
			if(vScopeObjId!=null && vScopeObjId.size()>0) {
				String target_kdpst = null; 
				ListIterator li = vScopeObjId.listIterator();
				String scope = (String) li.next();
				if(scope.equalsIgnoreCase("own") || scope.equalsIgnoreCase("yes")) {
					//hanya untuk cek baris pertama kalo nilainya yes ato own berarti error
					//tampleteSetTargetKdpst hanya menerima vormat dari vScopeObjId
					//System.out.println("hasSpmiMenu sudah tidak terima yes, tp scope ato ada error own value");
				}
				else {
					session.setAttribute("vScopeObjId", vScopeObjId);
					String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/tampleteSetTargetKdpst.jsp"; 
					String uri = request.getRequestURI(); 
					String url = PathFinder.getPath(uri, target);
					//request.getRequestDispatcher(url+"?fwd_to=/InnerFrame/Spmi/indexSpmi.jsp").forward(request,response);
					request.getRequestDispatcher(url+"?fwd_to=/"+fwdTo).forward(request,response);
				}
				*/
			Vector vScopeObjId = isu.getScopeObjIdFinal("s",true, true, false);
			if(vScopeObjId==null) {
				vScopeObjId = new Vector();
			}
			//
			
			Boolean iam_spmi_editor = !isu.isHakAksesReadOnly("hasSpmiMenu");
			boolean iam_team_mutu = isu.amIcontain("mutu");
			System.out.println("iam_team_mutu="+iam_team_mutu);
			Vector v_scope_kdpst_spmi = new Vector();
			ListIterator lit = v_scope_kdpst_spmi.listIterator();
			if(atMenu!=null && atMenu.equalsIgnoreCase("spmi")) {
				vScopeObjId = isu.getScopeObjIdFinal("hasSpmiMenu",true, true, false);
				if(vScopeObjId==null) {
					vScopeObjId = new Vector();
				}
				Vector v_scope_id = isu.returnScopeProdiOnlySortByKampusWithListIdobj_v1("hasSpmiMenu");
				//System.out.println("v_scope_id="+v_scope_id);
				Vector v_scope_kdpst = Converter.convertVscopeidToKdpst(v_scope_id);
				//System.out.println("v_scope_kdpst="+v_scope_kdpst);
				v_scope_kdpst_spmi = Converter.convertVscopeKdpstToDistinctInfoKdpst(v_scope_kdpst, "KDFAKMSPST,KDPSTMSPST,KDJENMSPST,NMPSTMSPST");
				//System.out.println("v_scope_kdpst_spmi="+v_scope_kdpst_spmi);
			}
			if(v_scope_kdpst_spmi==null) {
				v_scope_kdpst_spmi = new Vector();
			}
			session.setAttribute("vScopeObjId", vScopeObjId);
			session.setAttribute("v_scope_kdpst_spmi", v_scope_kdpst_spmi);
			session.setAttribute("spmi_editor", iam_spmi_editor);
			session.setAttribute("team_spmi", iam_team_mutu);
			String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/tampleteSetTargetKdpst.jsp"; 
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath(uri, target);
			//request.getRequestDispatcher(url+"?fwd_to=/InnerFrame/Spmi/indexSpmi.jsp").forward(request,response);
			//request.getRequestDispatcher(url+"?fwd_to=/"+fwdTo).forward(request,response);
			request.getRequestDispatcher(fwdTo+"?kdpst_nmpst_kmp=74201~HUKUM~PST").forward(request,response);
			
			
		
			//isu.getsc
			//isu.getScopeUpd7des2012ProdiOnlyButKeepOwn(command_code)
			
			/*
			Vector vSpm = isu.getScopeUpd7des2012ProdiOnlyButKeepOwn("hasSpmiMenu");
			if(vSpm!=null && vSpm.size()>0) {
				String kdpst = null;
				if(vSpm.size()==1) {
					ListIterator li = vSpm.listIterator();
					while(li.hasNext()) {
						String brs = (String)li.next();
						if(brs.equalsIgnoreCase("own")) {
							kdpst = new String(isu.getKdpst()); //buat mahasiswa only
						}
						else {
							kdpst = new String(Tool.getTokenKe(brs, 2));
						}
					}
					String target = Constants.getRootWeb()+"/InnerFrame/Spmi/indexSpmi.jsp"; 
					String uri = request.getRequestURI(); 
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url+"?kdpst="+kdpst).forward(request,response);
				}
				else {
					
				}
			}
			else {
				//System.out.println("scope pada hasSpmiMenu belum diperbaiki");
			}
			
			//request.getRequestDispatcher(url).forward(request,response);
*/
		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO Auto-generated method stub
		doGet(request, response);
	}

}

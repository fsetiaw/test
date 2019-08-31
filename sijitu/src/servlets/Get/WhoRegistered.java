package servlets.Get;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Vector;
import java.util.ListIterator;

import beans.dbase.daftarUlang.SearchDbInfoDaftarUlangTable;
import beans.dbase.mhs.SearchDbInfoMhs;
import beans.dbase.mhs.UpdateDbInfoMhs;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.PathFinder;

import java.util.StringTokenizer;
/**
 * Servlet implementation class WhoRegistered
 */
@WebServlet("/WhoRegistered")
public class WhoRegistered extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WhoRegistered() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("who WhoRegistered");
		
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
		     response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			
			String scope_cmd = "hasHeregitrasiMenu";
			String table_rule_nm = "HEREGISTRASI_RULES";
			
			String target_thsms = request.getParameter("target_thsms");
			if(target_thsms==null || Checker.isStringNullOrEmpty(target_thsms)) {
				target_thsms=Checker.getThsmsHeregistrasi();
			}		
			Vector v_scope_id = isu.returnScopeProdiOnlySortByKampusWithListIdobj(scope_cmd, table_rule_nm, target_thsms);
			String tkn_kmp = null;
			Vector vf = null;
			if(v_scope_id!=null && v_scope_id.size()>0) {
				//cek who register dari tabel daftar ulang
				SearchDbInfoDaftarUlangTable sddu = new SearchDbInfoDaftarUlangTable(isu.getNpm());
				vf = sddu.getInfoDaftarUlangFilterByScope_v1(target_thsms,v_scope_id);
			}
			session.setAttribute("vf", vf);
			session.setAttribute("v_scope_id", v_scope_id);
			String target = Constants.getRootWeb()+"/InnerFrame/DaftarUlang/indexDaftarUlang_v1.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?target_thsms="+target_thsms+"&scope_cmd="+scope_cmd+"&table_rule_nm="+table_rule_nm).forward(request,response);
			/*
			Vector vSc = isu.getScopeUpd7des2012("hasHeregitrasiMenu");
			String target_thsms = request.getParameter("target_thsms");
			String thsms_her = null;
			if(target_thsms==null || Checker.isStringNullOrEmpty(target_thsms)) {
				thsms_her = new String(Checker.getThsmsHeregistrasi());
			}
			else {
				thsms_her = new String(target_thsms);
			}
			
			SearchDbInfoMhs sdb = new SearchDbInfoMhs(isu.getNpm());
			//String listInfoMhs = sdb.getListMhsTrnlm(thsms_her);
			String listInfoMhs = sdb.getListMhsDaftarUlang(thsms_her);
			UpdateDbInfoMhs udb = new UpdateDbInfoMhs(isu.getNpm()); 
			udb.updateTabelDaftarUlang(listInfoMhs, thsms_her);
			SearchDbInfoDaftarUlangTable sdbu = new SearchDbInfoDaftarUlangTable(isu.getNpm());
			String  finalList = sdbu.getInfoDaftarUlangFilterByScopeAndUnapproved(thsms_her,vSc);
			String  listApprovee = sdbu.getDistinctNicknameDaftarUlangApprovee(thsms_her);
			int totApproval=0;
			String listObjApproval = "";
			if(finalList!=null) {
				StringTokenizer st = new StringTokenizer(finalList,"$");
				if(st.hasMoreTokens()) {
					String nmpst=st.nextToken();
					String kdpst=st.nextToken();
					String npmhs=st.nextToken();
					String nimhs=st.nextToken();
					String nmmhs=st.nextToken();
				
					String smawl=st.nextToken();
					String stpid=st.nextToken();
					String tglAju=st.nextToken();
					String tknApr=st.nextToken();
					String tknVerObj=st.nextToken();
					st = new StringTokenizer(tknVerObj,",");
					listObjApproval = ""+tknVerObj;
					totApproval = st.countTokens();
					String urutan=st.nextToken();
				}	
			}
			request.setAttribute("finalList", finalList);
			request.setAttribute("totApproval", totApproval+"");v_scope_id
			request.setAttribute("listObjApproval", listObjApproval);
			request.setAttribute("listApprovee", listApprovee);
			String target = Constants.getRootWeb()+"/InnerFrame/DaftarUlang/indexDaftarUlang.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?from=notification&thsms_regis="+thsms_her).forward(request,response);
			*/
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

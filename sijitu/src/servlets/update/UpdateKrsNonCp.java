package servlets.update;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;
import beans.dbase.*;
import beans.dbase.tbbnl.SearchDbTbbnl;
import beans.dbase.trnlm.UpdateDbTrnlm;
/**
 * Servlet implementation class UpdateKrsNonCp
 */
@WebServlet("/UpdateKrsNonCp")
public class UpdateKrsNonCp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateKrsNonCp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("sampe");
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
			boolean updated = false;
			String target = "";
			String id_obj = request.getParameter("id_obj");
			String nmm = request.getParameter("nmm");
			String npm = request.getParameter("npm");
			String kdpst = request.getParameter("kdpst");
			String obj_lvl = request.getParameter("obj_lvl");	
			String target_thsms = request.getParameter("target_thsms");
			
			String[]nlakh = request.getParameterValues("nlakh");
			UpdateDbTrnlm udt = new UpdateDbTrnlm(isu.getNpm());
			updated = udt.insertKrsNonClasspoll(nlakh, npm, target_thsms, kdpst);
			
			
			if(!updated) {
				target = Constants.getRootWeb()+"/InnerFrame/Tamplete/tampleteGoBackTabStyleForMhsMenu.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);

				request.getRequestDispatcher(url+"?msg=Tidak Ada Matakuliah yg Dipilih&nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&objLvl="+obj_lvl+"&objId="+id_obj+"&backTo=go.updateKrsKhs_v1&cmd=insertKrs").forward(request,response);
			}
			else {
				
				request.getRequestDispatcher("get.histKrs?nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&objLvl="+obj_lvl+"&objId="+id_obj+"&cmd=histKrs").forward(request,response);
			}
			
			
			

		}
		
		
		/*
		String TargetThsmsKrs=request.getParameter("thsms");
		String objId = request.getParameter("id_obj");
		String nmm = request.getParameter("nmm");
		String npm = request.getParameter("npm");
		String kdpst = request.getParameter("kdpst");
		String obj_lvl =  request.getParameter("obj_lvl");
		//System.out.println("obj_lvl="+obj_lvl);
		String cmd =  request.getParameter("cmd");
		String[]idkmk = request.getParameterValues("makul");
		
		
		//System.out.println("linfo UpdateKrsNonCp = "+TargetThsmsKrs+","+npm+","+kdpst+","+obj_lvl);
		if(idkmk!=null) {
			//System.out.println("idkmk!=null");
			Vector vInfo = new Vector();
			ListIterator linfo = vInfo.listIterator();
			for(int i=0;i<idkmk.length;i++) {
				if(idkmk[i]!=null&&!idkmk[i].equalsIgnoreCase("null")) {
					//idmakul tidak boleh null = belum melakukan seleksi
					linfo.add(TargetThsmsKrs+","+idkmk[i]+","+npm+","+kdpst+","+obj_lvl);
				}
				//System.out.println("linfo = "+TargetThsmsKrs+","+idkmk[i]+","+npm+","+kdpst+","+obj_lvl);
			}
			//System.out.println("vInfo = "+vInfo.size());
			UpdateDb udb = new UpdateDb();
			//System.out.println("update krs");
			if(cmd!= null && cmd.equalsIgnoreCase("addMakul")) {
				udb.addMakulKeKrs(vInfo,npm);
				
			}
			else {
				//System.out.println("kesinitoh");
				udb.UpdateKrsNonCp(vInfo,npm);
				//update krs_whietlist - remove target Thsms dari white list
				udb.removeTargetThsmsFromWhiteList(TargetThsmsKrs, kdpst, npm);
			}	
			//System.out.println("update krs2");
		}else {
			//no update redirect
			//System.out.println("its null");
		}
		//System.out.println("sampe1");
		//String target = Constants.getRootWeb()+"/InnerFrame/HistKrsKhs.jsp";
		//String uri = request.getRequestURI();
		//String url = PathFinder.getPath(uri, target);
		request.getRequestDispatcher("get.histKrs?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd=histKrs").forward(request,response);
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
		
	}

}

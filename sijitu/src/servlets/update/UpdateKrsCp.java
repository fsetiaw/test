package servlets.update;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.ListIterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
import beans.dbase.*;
import beans.dbase.trnlm.UpdateDbTrnlm;
/**
 * Servlet implementation class UpdateKrs
 */
@WebServlet("/UpdateKrsCp")
public class UpdateKrsCp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateKrsCp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("update CP");
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String continuSys = request.getParameter("continuSys");
		boolean ban_berjalan = false;
		if(continuSys!=null && continuSys.equalsIgnoreCase("true")) {
			ban_berjalan = true;
		}
		if(ban_berjalan) {
			//System.out.println("masuk ke ban berjaan");	
			
			String objId = request.getParameter("id_obj");
			String nmm = request.getParameter("nmm");
			String npm = request.getParameter("npm");
			String kdpst = request.getParameter("kdpst");
			String obj_lvl =  request.getParameter("obj_lvl");
			
			/*
			 * PROSES PINDAH THSMS MK KRS
			 */
			String[]nama_var = request.getParameterValues("nama_var");
			if(nama_var!=null && nama_var.length>0) {
				String tkn_info = null;
				for(int i=0;i<nama_var.length;i++) {
					String namanya = nama_var[i];
					String valuenya = null;
					if(!Checker.isStringNullOrEmpty(namanya)) {
						valuenya = new String(request.getParameter(namanya));
						if(!Checker.isStringNullOrEmpty(valuenya) && !namanya.startsWith(valuenya)) {
							if(tkn_info==null) {
								tkn_info = new String();
							}
							tkn_info = tkn_info+namanya+"`"+valuenya;		
						}
						
					}	
				}
				//update
				if(!Checker.isStringNullOrEmpty(tkn_info)) {
					UpdateDbTrnlm udt = new UpdateDbTrnlm(isu.getNpm());
					udt.geserThsmsMakulKrsBanBerjalan(tkn_info, npm);
				}
				
			}
			String [] kelas_info = request.getParameterValues("kelas_info");
			if(kelas_info!=null && kelas_info.length>0) {
				UpdateDb udb = new UpdateDb(isu.getNpm());
				udb.updateKrsCpContinuSystem(npm, kelas_info);
				
			}
			                             
			request.getRequestDispatcher("get.histKrs?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd=histKrs").forward(request,response);
			
		}
		else {
			
			//variable dibawah ini adalah info target bukan operator
			String TargetThsmsKrs=request.getParameter("thsms");
			String objId = request.getParameter("id_obj");
			String nmm = request.getParameter("nmm");
			String npm = request.getParameter("npm");
			String kdjen = request.getParameter("kdjen");
			String kdpst = request.getParameter("kdpst");
			String obj_lvl =  request.getParameter("obj_lvl");
			//System.out.println("obj_lvl="+obj_lvl);
			String cmd =  request.getParameter("cmd");
			
			
			
			
			String[]kelasSelected = request.getParameterValues("kelasSelected");
			//for(int i=0;i<kelasSelected.length;i++) {
			//	System.out.println("kelas seleccted="+kelasSelected[i]);
			//}
			SearchDb sdb = new SearchDb();
			Vector VkelasSelected = sdb.filterRequestKrsAddOrDrop(kelasSelected,TargetThsmsKrs,npm);
			if(VkelasSelected!=null && VkelasSelected.size()>0) {
				ListIterator li = VkelasSelected.listIterator();
				//while(li.hasNext()) {
				//	String brs = (String)li.next();
				//	System.out.println("iniloh="+brs);
				//}
				UpdateDb udb = new UpdateDb();
				udb.updateKrsCp_v1(TargetThsmsKrs,kdjen,kdpst,npm,VkelasSelected);
				udb.removeTargetThsmsFromWhiteList(TargetThsmsKrs, kdpst, npm);
				udb.individualRequestApprovalKrsNotificationTable(TargetThsmsKrs,kdpst,npm, nmm);
			}

			String target = Constants.getRootWeb()+"/InnerFrame/HistKrsKhs.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher("get.histKrs?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd=histKrs").forward(request,response);
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

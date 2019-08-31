package servlets.view;

import java.io.IOException;
import java.io.PrintWriter;

import beans.folder.FolderManagement;
import beans.folder.file.*;
//import beans.dbase.SearchDb;
import beans.dbase.mhs.SearchDbInfoMhs;
import beans.dbase.mhs.UpdateDbInfoMhs;
import beans.dbase.mhs.data_pribadi.SearchDbInfoMhsDataPri;
import beans.dbase.Beasiswa.SearchDbBeasiswa;
import beans.dbase.dosen.SearchDbDsn;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.sql.Connection;
import java.util.Vector;
import java.util.ListIterator;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.AddHocFunction;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class DataCivitas
 */
@WebServlet("/ProfileCivitas_v1")
public class ProfileCivitas_v1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;   
    DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
 //   public ProfileCivitas_v1() {
 //       super();
        // TODO Auto-generated constructor stub
 //   }
	public void init(ServletConfig config) throws ServletException {
		
		super.init(config);
		/*
		Context initContext = null;
		Context envContext = null;
	    try {
	      // Look up the JNDI data source only once at init time
    		initContext  = new InitialContext();
    		envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		
    		envContext.close();
    		initContext.close();
    		if(ds==null) {
    			throw new ServletException("missing data source");
    		}		
	    }
	    catch (NamingException e) {
	      e.printStackTrace();
	    }
	    */
	  }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("masuk profile civits");
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
			//System.out.println("oce");
			String cmd = request.getParameter("cmd");
			String atMenu = request.getParameter("atMenu");
			String npmhs = request.getParameter("npm");
			String target = "";
			
			/*
			 * SELALU BERPASANGAN, SATU UNTUK VIEW SATU LAGI UNTUK EDIT
			 */
			if(atMenu.equalsIgnoreCase("pribadi") && (cmd.equalsIgnoreCase("profile")||cmd.equalsIgnoreCase("edit"))) { 
				//get data yg dibutuhkan
				SearchDbInfoMhsDataPri sdp = new SearchDbInfoMhsDataPri(isu.getNpm());
				String info = sdp.getDataPribadi(npmhs);
				if(info!=null) {
					info = info.replace("``", "`null`");
				}
				//System.out.println("info="+info);
				request.setAttribute("info", info);
				if(cmd.equalsIgnoreCase("profile")) {
					target = Constants.getRootWeb()+"/InnerFrame/Profile/profile_v1.jsp";	
				}
				else if(cmd.equalsIgnoreCase("edit")) {
					target = Constants.getRootWeb()+"/InnerFrame/Profile/edit_profile_v1.jsp";
				}
				//System.out.println("target="+target);
				//System.out.println("info="+info);
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url+"?cmd="+cmd+"&atMenu="+atMenu).forward(request,response);
			}
			else if(atMenu.equalsIgnoreCase("ortu")&& (cmd.equalsIgnoreCase("profile")||cmd.equalsIgnoreCase("edit"))) {
				//get data yg dibutuhkan
				SearchDbInfoMhsDataPri sdp = new SearchDbInfoMhsDataPri(isu.getNpm());
				String info = sdp.getDataOrtuDanWali(npmhs);
				//System.out.println("info="+info);
				request.setAttribute("info", info);
				if(cmd.equalsIgnoreCase("edit")) {
					target = Constants.getRootWeb()+"/InnerFrame/Profile/ortu/edit_profile_ortu.jsp";
				}
				else {
					cmd = "profile";
					target = Constants.getRootWeb()+"/InnerFrame/Profile/ortu/profile_ortu.jsp";	
				}
				
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url+"?cmd="+cmd+"&atMenu="+atMenu).forward(request,response);
			} 
			else if(atMenu.equalsIgnoreCase("kemhsan")) { 
				//get data yg dibutuhkan
				SearchDbInfoMhsDataPri sdp = new SearchDbInfoMhsDataPri(isu.getNpm());
				String info = sdp.getDataKemahasiswaan(npmhs);
				//System.out.println("info="+info);
				request.setAttribute("info", info);
				if(cmd.equalsIgnoreCase("profile")) {
					target = Constants.getRootWeb()+"/InnerFrame/Profile/kemahasiswaan/profile_kemahasiswaan.jsp";	
				}
				else if(cmd.equalsIgnoreCase("edit")) {
					target = Constants.getRootWeb()+"/InnerFrame/Profile/kemahasiswaan/edit_profile_kemahasiswaan.jsp";
				}
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url+"?cmd="+cmd+"&atMenu="+atMenu).forward(request,response);
			}
			else if(atMenu.equalsIgnoreCase("dsn")) { 
				//get data yg dibutuhkan
				SearchDbInfoMhsDataPri sdp = new SearchDbInfoMhsDataPri(isu.getNpm());
				String info = sdp.getDataAsDosen(npmhs);
				//System.out.println("info="+info);
				request.setAttribute("info", info);
				if(cmd.equalsIgnoreCase("profile")) {
					target = Constants.getRootWeb()+"/InnerFrame/Profile/dosen/profile_dosen.jsp";	
				}
				else if(cmd.equalsIgnoreCase("edit")) {
					target = Constants.getRootWeb()+"/InnerFrame/Profile/dosen/edit_profile_dosen.jsp";
				}
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url+"?cmd="+cmd+"&atMenu="+atMenu).forward(request,response);
			}
			else if(atMenu.equalsIgnoreCase("pindahan")) { 
				if(cmd.equalsIgnoreCase("profile")) {
					target = Constants.getRootWeb()+"/InnerFrame/Profile/pindahan/profile_pindahan.jsp";	
				}
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url+"?cmd="+cmd+"&atMenu="+atMenu).forward(request,response);
			}
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

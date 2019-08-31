package servlets.Files;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

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

/**
 * Servlet implementation class CreateNewFolderDocMutu
 */
@WebServlet("/CreateNewFolderDocMutu")
public class CreateNewFolderDocMutu extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String foldername;
	String alm;
	String keter;
	String hak; 
	String root_dir;
	String kdpst;
	String folder_type;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNewFolderDocMutu() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	String msg = "<meta http-equiv=\"refresh\" content=\"0; url=get.folderContent?keter="+keter+"&alm="+alm+"&hak="+hak+"&root_dir="+root_dir+"\" >";
	//request.getRequestDispatcher("get.folderContent?keter="+keter+"&alm="+alm+"&hak="+hak).forward(request,response);
	
    response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
    response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
    response.getWriter().write(msg); 
    msg="";
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			//System.out.println("create folder ini");
			foldername = request.getParameter("foldername");
			while(foldername.contains("~DAN~")) {
				foldername = foldername.replace("~DAN~", "&");
			}
			while(foldername.contains("~OR~")) {
				foldername = foldername.replace("~OR~", "/");
			}	
			folder_type = request.getParameter("folder_type");
			foldername = foldername.replace(" ", "_");
			//System.out.println("create foldername="+alm+"/"+foldername);
			//System.out.println("create type="+folder_type);
			alm = request.getParameter("alm");
			while(alm.contains("~DAN~")) {
				alm = alm.replace("~DAN~", "&");
			}
			while(alm.contains("~OR~")) {
				alm = alm.replace("~OR~", "/");
			}	
			kdpst = (String)request.getParameter("kdpst");
			if(Checker.isStringNullOrEmpty(kdpst)) {
				kdpst = (String)session.getAttribute("kdpst_folder");
			}
			//kdpst = (String)session.getAttribute("kdpst_folder");
			keter = request.getParameter("keter");
			
			hak = request.getParameter("hak");
			root_dir = request.getParameter("root_dir");//masalah kalo dipajke
			//System.out.println(foldername);
			//System.out.println("create folder="+alm+"/"+foldername);
			StringTokenizer stt = new StringTokenizer(alm,"/");
			//String dir = "/";
			/*
			while(stt.hasMoreTokens()) {
				String dir = dir+stt.nextToken();
						try {
							dir.mkdir();
							//System.out.println("created1");
						}
						catch (Exception e) {
							e.printStackTrace();
						}
			}
			*/
			File dir = new File(alm.trim());
			try {
				dir.mkdirs();
				//System.out.println("created1");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			dir = new File(alm.trim()+"/"+foldername.trim());
			try {
				dir.mkdirs();
				//System.out.println("created2");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			//request.getRequestDispatcher("get.folderContent?keter="+keter+"&alm="+alm+"&hak="+hak).forward(request,response);
		}
		doGet(request,response);
	}

}

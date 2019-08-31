package servlets.Router;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.dbase.keu.*;
import beans.login.InitSessionUsr;
import beans.tools.Checker;

import java.util.StringTokenizer;
/**
 * Servlet implementation class RedirectAfterGenericFileUploadDocMutu
 */
@WebServlet("/RedirectAfterGenericFileUploadDocMutu")
public class RedirectAfterGenericFileUploadDocMutu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RedirectAfterGenericFileUploadDocMutu() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("RedirectAfterGenericFileUploadDocMutu");
		HttpSession session = request.getSession(true);
		 
 		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
 		String dataForRouterAfterUpload = (String)session.getAttribute("dataForRouterAfterUpload");
 		session.removeAttribute("dataForRouterAfterUpload");
 		StringTokenizer st = new StringTokenizer(dataForRouterAfterUpload,"`");
 		//, root_dir+"`"+keter+"`"+hakAksesUsrUtkFolderIni+"`"+cur_dir);
 		String root_dir = st.nextToken();
 		String keter = st.nextToken();
 		String hak = st.nextToken();
 		String alm = st.nextToken();
 		String kdpst = (String)request.getParameter("kdpst");
		if(Checker.isStringNullOrEmpty(kdpst)) {
			kdpst = (String)session.getAttribute("kdpst_folder");
		}

		//session.removeAttribute("nuFileName");
		//System.out.println("namaFileSetoran="+namaFileSetoran);

		request.getRequestDispatcher("get.folderContentDocMutu?kdpst="+kdpst+"&root_dir="+root_dir+"&keter="+keter+"&alm="+alm+"&hak="+hak).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}

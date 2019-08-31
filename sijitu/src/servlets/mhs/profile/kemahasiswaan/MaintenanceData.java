package servlets.mhs.profile.kemahasiswaan;

import java.io.IOException;
import java.util.ListIterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.mhs.UpdateDbInfoMhs;
import beans.dbase.mhs.data_pribadi.UpdateDbInfoMhsDataPri;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;
import beans.tools.Tool;

/**
 * Servlet implementation class MaintenanceData
 */
@WebServlet("/MaintenanceData")
public class MaintenanceData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MaintenanceData() {
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
			//PrintWriter out = response.getWriter();
			//System.out.println("masuk");
			Vector v = null;
			UpdateDbInfoMhs udm = new UpdateDbInfoMhs();
			int effected = udm.setNimEqualsNpm("20162");
			//System.out.println("effected = "+effected);
			UpdateDbInfoMhsDataPri udb = new UpdateDbInfoMhsDataPri();
			
			
			int updated =  udb.removeUnwantedChar("'", "NIMHSMSMHS", "CIVITAS");
			//System.out.println("nim ay = "+updated);
			updated =  udb.removeUnwantedChar("-", "NIMHSMSMHS", "CIVITAS");
			//System.out.println("nim ay = "+updated);
			updated =  udb.removeUnwantedChar("'", "NOHAPE_AYAH", "CIVITAS");
			//System.out.println("hp ay = "+updated);
			updated =  udb.removeUnwantedChar("-", "NOHAPE_AYAH", "CIVITAS");
			//System.out.println("hp ay = "+updated);
			updated =  udb.removeUnwantedChar("'", "NIK_AYAH", "CIVITAS");
			//System.out.println("nik ay = "+updated);
			updated =  udb.removeUnwantedChar("-", "NIK_AYAH", "CIVITAS");
			//System.out.println("nik ay = "+updated);
			updated =  udb.removeUnwantedChar("'", "NOHAPE_IBU", "CIVITAS");
			//System.out.println("hp bu = "+updated);
			updated =  udb.removeUnwantedChar("-", "NOHAPE_IBU", "CIVITAS");
			//System.out.println("hp bu = "+updated);
			updated =  udb.removeUnwantedChar("'", "NIK_IBU", "CIVITAS");
			//System.out.println("nohp ay = "+updated);
			updated =  udb.removeUnwantedChar("-", "NIK_IBU", "CIVITAS");
			//System.out.println("nohp ay = "+updated);
			updated =  udb.removeUnwantedChar("'", "NOHAPE_WALI", "CIVITAS");
			//System.out.println("nohp ay = "+updated);
			updated =  udb.removeUnwantedChar("-", "NOHAPE_WALI", "CIVITAS");
			//System.out.println("nohp ay = "+updated);
			updated =  udb.removeUnwantedChar("'", "NIK_WALI", "CIVITAS");
			//System.out.println("nohp ay = "+updated);
			updated =  udb.removeUnwantedChar("-", "NIK_WALI", "CIVITAS");
			
			updated =  udb.removeUnwantedChar("'", "NIKTPMSMHS", "CIVITAS");
			//System.out.println("nohp ay = "+updated);
			updated =  udb.removeUnwantedChar("-", "NIKTPMSMHS", "CIVITAS");
			//System.out.println("nohp ay = "+updated);
			updated =  udb.removeUnwantedChar("'", "NISNMSMHS", "CIVITAS");
			//System.out.println("nohp ay = "+updated);
			updated =  udb.removeUnwantedChar("-", "NISNMSMHS", "CIVITAS");
			
			updated =  udb.removeUnwantedChar("'", "NOHPEMSMHS", "EXT_CIVITAS");
			//System.out.println("nohp ay = "+updated);
			updated =  udb.removeUnwantedChar("-", "NOHPEMSMHS", "EXT_CIVITAS");
			//System.out.println("nohp ay = "+updated);
			updated =  udb.removeUnwantedChar("'", "NOHPEMSMHS", "EXT_CIVITAS");
			//System.out.println("nohp ay = "+updated);
			updated =  udb.removeUnwantedChar("-", "NOHPEMSMHS", "EXT_CIVITAS");
			
			updated =  udb.removeUnwantedChar("'", "NRTRMMSMHS", "EXT_CIVITAS");
			updated =  udb.removeUnwantedChar("-", "NRTRMMSMHS", "EXT_CIVITAS");
			updated =  udb.removeUnwantedChar("R", "NRTRMMSMHS", "EXT_CIVITAS");
			updated =  udb.removeUnwantedChar("T", "NRTRMMSMHS", "EXT_CIVITAS");
			updated =  udb.removeUnwantedChar("r", "NRTRMMSMHS", "EXT_CIVITAS");
			updated =  udb.removeUnwantedChar("t", "NRTRMMSMHS", "EXT_CIVITAS");
			
			updated =  udb.removeUnwantedChar("'", "NRWRMMSMHS", "EXT_CIVITAS");
			updated =  udb.removeUnwantedChar("-", "NRWRMMSMHS", "EXT_CIVITAS");
			updated =  udb.removeUnwantedChar("R", "NRWRMMSMHS", "EXT_CIVITAS");
			updated =  udb.removeUnwantedChar("W", "NRWRMMSMHS", "EXT_CIVITAS");
			updated =  udb.removeUnwantedChar("r", "NRWRMMSMHS", "EXT_CIVITAS");
			updated =  udb.removeUnwantedChar("w", "NRWRMMSMHS", "EXT_CIVITAS");
			updated =  udb.setDataAgamaToDefaultJikaBelumTerisi("ISLAM");
			updated = udb.resetInvalidEmailFormat();
			updated = udb.setDefaultKewarganegaraanIfNull("INDONESIA");
			updated = udb.resetInvalidNik();
			updated = udb.setDefaultTelprm();
			Vector v1 = udb.fixDataRt();
			Vector v2 = udb.fixDataRw();
			//System.out.println("0");
			if(v1!=null && v2!=null) {
				//System.out.println("1");
				v = Tool.mergeVector(v1, v2);
			}
			else if(v1!=null && v2==null) {
				//System.out.println("2");
					v = v1;
			}
			else if(v1==null && v2!=null) {
				//System.out.println("3");
				v = v2;
			}
			session.setAttribute("v", v);
			session.setAttribute("tkn_header","No.`NPMHS`No RT");
			
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
			//String uri = request.getRequestURI();
			//String url = PathFinder.getPath(uri, target);
			//request.getRequestDispatcher(url).forward(request,response);
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

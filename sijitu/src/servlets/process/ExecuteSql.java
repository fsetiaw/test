package servlets.process;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.sql.ExecuteCmd;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class ExecuteSql
 */
@WebServlet("/ExecuteSql")
public class ExecuteSql extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecuteSql() {
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
			//System.out.println("sql");
			String sql_cmd = request.getParameter("sql_cmd");
			ExecuteCmd exc = new ExecuteCmd();
			String tipe_cmd = null;
			Vector v = null;
			if(sql_cmd!=null && !Checker.isStringNullOrEmpty(sql_cmd)) {
				sql_cmd = sql_cmd.trim();
				
				if(!sql_cmd.toLowerCase().startsWith("select") && !isu.amI("ADMIN")) {
					//sql_cmd = sql_cmd.toLowerCase();
					//boleh nambah data dokumen
					if(sql_cmd.toLowerCase().contains("tambah")&&sql_cmd.toLowerCase().contains("dokumen")&&sql_cmd.toLowerCase().contains("terkait")&&sql_cmd.toLowerCase().contains(":")) {
						StringTokenizer st = new StringTokenizer(sql_cmd,":");
						st.nextToken();
						String nu_doc = new String(st.nextToken().toUpperCase());
						nu_doc = nu_doc.trim();
						sql_cmd = "insert into STANDARD_DOKUMEN(NAMA_DOKUMEN)values('"+nu_doc+"')";
						//System.out.println("coomand = "+sql_cmd);
						v = exc.executeCmd_v1(sql_cmd);
					}
					else if(sql_cmd.toLowerCase().contains("tambah")&&sql_cmd.toLowerCase().contains("jabatan")&&sql_cmd.toLowerCase().contains("terkait")&&sql_cmd.toLowerCase().contains(":")) {
						StringTokenizer st = new StringTokenizer(sql_cmd,":");
						st.nextToken();
						String nu_doc = new String(st.nextToken().toUpperCase());
						nu_doc = nu_doc.trim();
						int id_jab = Getter.getIdJabatanTerakhir()+1;
						sql_cmd = "insert into JABATAN(NAMA_JABATAN,AKTIF,ID_JAB)values('"+nu_doc+"',true,"+id_jab+")";
						//System.out.println("coomand = "+sql_cmd);
						v = exc.executeCmd_v1(sql_cmd);
					}
					//selain admin tidak boleh execute
				}
				else {
					//sql_cmd = sql_cmd.toLowerCase();
					//boleh nambah data dokumen
					if(sql_cmd.toLowerCase().contains("tambah")&&sql_cmd.toLowerCase().contains("dokumen")&&sql_cmd.toLowerCase().contains("terkait")&&sql_cmd.toLowerCase().contains(":")) {
						StringTokenizer st = new StringTokenizer(sql_cmd,":");
						st.nextToken();
						String nu_doc = new String(st.nextToken().toUpperCase());
						nu_doc = nu_doc.trim();
						sql_cmd = "insert into STANDARD_DOKUMEN(NAMA_DOKUMEN)values('"+nu_doc+"')";
						//System.out.println("coomand = "+sql_cmd);
						//v = exc.executeCmd_v1(sql_cmd);
					}
					else if(sql_cmd.toLowerCase().contains("tambah")&&sql_cmd.toLowerCase().contains("jabatan")&&sql_cmd.toLowerCase().contains("terkait")&&sql_cmd.toLowerCase().contains(":")) {
						StringTokenizer st = new StringTokenizer(sql_cmd,":");
						st.nextToken();
						String nu_doc = new String(st.nextToken().toUpperCase());
						nu_doc = nu_doc.trim();
						int id_jab = Getter.getIdJabatanTerakhir()+1;
						sql_cmd = "insert into JABATAN(NAMA_JABATAN,AKTIF,ID_JAB)values('"+nu_doc+"',true,"+id_jab+")";
						//System.out.println("coomand = "+sql_cmd);
						//v = exc.executeCmd_v1(sql_cmd);
					}
					v = exc.executeCmd_v1(sql_cmd);	
				}
				
				if(sql_cmd.contains("select")||sql_cmd.contains("SELECT")||sql_cmd.contains("Select")) {
					tipe_cmd = "select";
				}
				else {
					tipe_cmd = "update";
				}
			}
			if(v==null) {
				v = new Vector();
			}
			request.setAttribute("v", v);
			String target = "";
			if(tipe_cmd.equalsIgnoreCase("select")) {
				target = Constants.getRootWeb()+"/InnerFrame/sql/ResultSet.jsp";
			}
			else {
				target = Constants.getRootWeb()+"/InnerFrame/sql/ResultSet_v0.jsp";
			}
			
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?tipe_cmd="+tipe_cmd).forward(request,response);
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

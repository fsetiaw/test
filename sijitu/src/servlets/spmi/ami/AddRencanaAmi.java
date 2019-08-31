package servlets.spmi.ami;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.spmi.riwayat.ami.UpdateAmi;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.PathFinder;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;

/**
 * Servlet implementation class AddRencanaAmi
 */
@WebServlet("/AddRencanaAmi")
public class AddRencanaAmi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddRencanaAmi() {
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
			Vector v_err = null;
			ListIterator li = null;
			
			String kdpst_nmpst_kmp = (String) request.getParameter("kdpst_nmpst_kmp");
			String kode_activity = (String) request.getParameter("kode_activity");
			String tgl_plan = (String) request.getParameter("tgl_plan");
			//System.out.println("tgl_plan="+tgl_plan);
			String ketua_tim = (String) request.getParameter("ketua_tim");
			String[]anggota_tim = (String[]) request.getParameterValues("anggota_tim");
			String[]cek = (String[]) request.getParameterValues("cek");
			
			if(Checker.isStringNullOrEmpty(kode_activity)) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Nama / Kode Kegiatan harap diisi");
				
			}
			
			if(Checker.isStringNullOrEmpty(tgl_plan)) {
				//System.out.println("ampun");
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Tgl Rencana Kegiatan harap diisi dengan benar");
			}
			else {
				try {
					//System.out.println("ampun2");
					tgl_plan = Converter.autoConvertDateFormat(tgl_plan, "-");
					java.sql.Date dt = Date.valueOf(tgl_plan);
					//System.out.println("date = "+dt);
				}
				catch (Exception e) {
					//System.out.println("ampun3");
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Tgl Rencana Kegiatan harap diisi dengan benar");	
				}
			}
			//System.out.println("v_er1="+v_err.size());
			if(Checker.isStringNullOrEmpty(ketua_tim)) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Nama Ketua Tim harap diisi");
			}
			//System.out.println("v_er2="+v_err.size());
			if(anggota_tim==null) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Nama Anggota Tim harap diisi (min 1 orang)");
			}
			else {
				boolean ada = false;
				for(int i=0;i<anggota_tim.length && !ada;i++) {
					if(!Checker.isStringNullOrEmpty(anggota_tim[i])) {
						ada = true;
					}
				}
				if(!ada) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Nama Anggota Tim harap diisi (min 1 orang)");
				}
			}
			//System.out.println("v_er3="+v_err.size());
			if(cek==null) {
				if(v_err==null) {
					v_err = new Vector();
					li = v_err.listIterator();
				}
				li.add("Cakupan Standar harap diisi (min 1 standar)");
			}
			else {
				boolean ada = false;
				for(int i=0;i<cek.length && !ada;i++) {
					if(!Checker.isStringNullOrEmpty(cek[i])) {
						ada = true;
					}
				}
				if(!ada) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Cakupan Standar harap diisi (min 1 standar)");
				}
			}
			
			//System.out.println("v_er="+v_err.size());
			if(v_err!=null) {
				request.setAttribute("v_err", v_err);
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/Ami/form_add_ami.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url+"?kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);
			}
			else {
				UpdateAmi ua = new UpdateAmi();
				StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
				String target_kdpst = st.nextToken();
				String nmpst = st.nextToken();
				String kdkmp = st.nextToken();
				String id_ami = request.getParameter("id_ami");
				int updated=ua.addRencanaAmi(id_ami,target_kdpst, kode_activity, tgl_plan, ketua_tim, anggota_tim, cek);
				 
				request.getRequestDispatcher("go.prepDashOverviewAmi?kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);
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

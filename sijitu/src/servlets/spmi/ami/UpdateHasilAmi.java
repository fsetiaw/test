package servlets.spmi.ami;

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
import java.util.StringTokenizer;

import beans.dbase.spmi.riwayat.ami.SearchAmi;
import beans.dbase.spmi.riwayat.ami.UpdateAmi;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateHasilAmi
 */
@WebServlet("/UpdateHasilAmi")
public class UpdateHasilAmi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateHasilAmi() {
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
			//System.out.println("okay update");
			String nav_button = request.getParameter("tombol");
			String dari_pelaksanaan_ami = request.getParameter("dari_pelaksanaan_ami");
			String kdpst_nmpst_kmp = (String)request.getParameter("kdpst_nmpst_kmp");
			kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
			StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
			String kdpst = st.nextToken();
			String nmpst = st.nextToken();
			String kdkmp = st.nextToken();
			String id_ami = (String)session.getAttribute("id_ami");
			//System.out.println("id_ami="+id_ami);
			String start_number = request.getParameter("start_number");
			//System.out.println("start_number="+start_number);
			String ending_number = request.getParameter("ending_number");
			String at_page = request.getParameter("at_page");
			//System.out.println("ending_number="+ending_number);
			int sta_no = Integer.parseInt(start_number);
			int end_no = Integer.parseInt(ending_number);
			Vector v_idq_answer_langgar_note_saran = new Vector();
			ListIterator lit = v_idq_answer_langgar_note_saran.listIterator();
			for(;sta_no<=end_no;sta_no++) {
				
				String id_question = request.getParameter("id_question"+sta_no);
				lit.add(id_question);
				String[]answer = request.getParameterValues("answer"+sta_no);
				String pelanggaran = request.getParameter("pelanggaran"+sta_no);
				String note = request.getParameter("note"+sta_no);
				String saran = request.getParameter("saran"+sta_no);
				//System.out.println("nomor="+sta_no);
				//System.out.println("id_question="+id_question);
				if(answer!=null) {
					for(int i=0;i<answer.length;i++) {
						//System.out.println("answer["+i+"]="+answer[i]);
					}
					lit.add(answer);
				}
				else {
					//System.out.println("no_answer");
					answer = new String[1];
					answer[0]="null";
					lit.add(answer);
				}
				lit.add(pelanggaran);
				if(Checker.isStringNullOrEmpty(note)) {
					lit.add("null");
				}
				else {
					lit.add(note);
				}
				if(Checker.isStringNullOrEmpty(saran)) {
					lit.add("null");
				}
				else {
					lit.add(saran);
				}
				//System.out.println("pelanggaran="+pelanggaran);
				//System.out.println("note="+note);
				//System.out.println("saran="+saran);
			}
			
			UpdateAmi ua = new UpdateAmi();
			String goto_index= request.getParameter("goto_index");
			//System.out.println("goto_index="+goto_index);
			
			int updated = ua.updateHasilAmi(Integer.parseInt(id_ami), v_idq_answer_langgar_note_saran);
			
			SearchAmi sa = new SearchAmi();
			String id_master = (String)session.getAttribute("id_master_std");
			Vector v = sa.getHasilAmiQandA(Integer.parseInt(id_ami),Integer.parseInt(id_master));
			session.removeAttribute("v_QA");
			session.setAttribute("v_QA", v);
			if(dari_pelaksanaan_ami!=null) {
				if(goto_index!=null&&goto_index.equalsIgnoreCase("true")) {
					String kode_activity = (String)session.getAttribute("kode_activity");
					String tgl_plan = (String)session.getAttribute("tgl_plan");
					String ketua_tim = (String)session.getAttribute("ketua_tim");
					String anggota_tim = (String)session.getAttribute("anggota_tim");
					String id_cakupan_std = (String)session.getAttribute("id_cakupan_std");
					String ket_cakupan_std = (String)session.getAttribute("ket_cakupan_std");
					String tgl_ril = (String)session.getAttribute("tgl_ril");
					String tgl_ril_done = (String)session.getAttribute("tgl_ril_done");
					String id_master_std = (String)session.getAttribute("id_master_std");
					String ket_master_std = (String)session.getAttribute("ket_master_std");
					String target = Constants.getRootWeb()+"/InnerFrame/Spmi/Ami/index_pelaksanaan_ami.jsp";
					String uri = request.getRequestURI();
					String url = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url+"?kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&id_ami="+id_ami+"&kode_activity="+kode_activity+"&tgl_plan="+tgl_plan+"&ketua_tim="+ketua_tim+"&anggota_tim="+anggota_tim+"&id_cakupan_std="+id_cakupan_std+"&ket_cakupan_std="+ket_cakupan_std+"&tgl_ril="+tgl_ril+"&tgl_ril_done="+tgl_ril_done).forward(request,response);
				}
				else {
					String target = Constants.getRootWeb()+"/InnerFrame/Spmi/Ami/form_pelaksanaan_ami.jsp";
					String uri = request.getRequestURI();
					String url = PathFinder.getPath(uri, target);
					
					
					//System.out.println("nav_button="+nav_button);
					if(!Checker.isStringNullOrEmpty(nav_button)&&nav_button.equalsIgnoreCase("next")) {
						at_page = ""+(Integer.parseInt(at_page)+1);
					}
					else if(!Checker.isStringNullOrEmpty(nav_button)&&nav_button.equalsIgnoreCase("prev")) {
						int prev_pg = (Integer.parseInt(at_page)-1);
						if(prev_pg<0) {
							prev_pg=0;
						}
						at_page=""+prev_pg;
					}
					request.getRequestDispatcher(url+"?at_page="+at_page+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp).forward(request,response);
				}
				
				
				
			}
			
			/*
			else {
				String id_ami_menu = (String)session.getAttribute("id_ami");;
				String kode_activity_menu = (String)session.getAttribute("kode_activity");
				String tgl_plan_menu = (String)session.getAttribute("tgl_plan");
				String ketua_tim_menu = (String)session.getAttribute("ketua_tim");
				String anggota_tim_menu = (String)session.getAttribute("anggota_tim");
				String id_cakupan_std_menu = (String)session.getAttribute("id_cakupan_std");
				String ket_cakupan_std_menu = (String)session.getAttribute("ket_cakupan_std");
				String tgl_ril_menu = (String)session.getAttribute("tgl_ril");
				String tgl_ril_done_menu = (String)session.getAttribute("tgl_ril_done");
				String id_master_std_menu = (String)session.getAttribute("id_master_std");
				String ket_master_std_menu = (String)session.getAttribute("ket_master_std");
			}
			*/
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

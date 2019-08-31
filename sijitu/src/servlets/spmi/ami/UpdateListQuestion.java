package servlets.spmi.ami;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.dbase.spmi.riwayat.ami.UpdateAmi;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateListQuestion
 */
@WebServlet("/UpdateListQuestion")
public class UpdateListQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateListQuestion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
		kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
		StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
		String kdpst = st.nextToken();
		String nmpst = st.nextToken();
		String kdkmp = st.nextToken();
		String[] idq_id_isi_norut = (String[]) request.getParameterValues("norut_question");
		String id_cakupan_std = (String) request.getParameter("id_cakupan_std");
		String id_master_std = (String) request.getParameter("id_master_std");
		String reset = (String) request.getParameter("reset");
		/*
		if(norut_question!=null) {
			for(int i=0;i<norut_question.length;i++) {
				//System.out.println("norut_question["+i+"]="+norut_question[i]);
			}
		}
		*/
		//System.out.println("kdpst_nmpst_kmp="+kdpst_nmpst_kmp);
		int updated =0;
		UpdateAmi ua = new UpdateAmi();
		if(reset!=null && reset.equalsIgnoreCase("true")) {
			
			updated = ua.resetSusunanPertanyaan(kdpst, Integer.parseInt(id_master_std));
			//System.out.println("updated1="+updated);
		}
		else {
			updated = ua.updateSusunanPertanyaan(kdpst, Integer.parseInt(id_master_std), idq_id_isi_norut);
			//System.out.println("updated2="+updated);
		}
		 
		/*
		String target = Constants.getRootWeb()+"/InnerFrame/Spmi/Ami/form_penilaian_ami.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		*/
		request.getRequestDispatcher("go.prepFormAmi").forward(request,response);
		//request.getRequestDispatcher("go.prepFormAmi").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

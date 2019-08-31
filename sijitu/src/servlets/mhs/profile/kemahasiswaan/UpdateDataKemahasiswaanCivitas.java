package servlets.mhs.profile.kemahasiswaan;

import java.io.IOException;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.mhs.data_pribadi.UpdateDbInfoMhsDataPri;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UpdateDataKemahasiswaanCivitas
 */
@WebServlet("/UpdateDataKemahasiswaanCivitas")
public class UpdateDataKemahasiswaanCivitas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateDataKemahasiswaanCivitas() {
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
			//System.out.println("--------------------------------");
			//Enumeration paramNames = request.getParameterNames();
			//while(paramNames.hasMoreElements())
			//{
			//      String paramName = (String)paramNames.nextElement();
			      //System.out.println(paramName);
			//}
			StringTokenizer st = null;
			String err_msg = "";
			String info = request.getParameter("info");
			String id_obj = request.getParameter("id_obj");
			String nmm = request.getParameter("nmm");
			String npm = request.getParameter("npm");
			String obj_lvl = request.getParameter("obj_lvl");
			String kdpst = request.getParameter("kdpst");
			String cmd = request.getParameter("cmd");
			String atMenu = request.getParameter("atMenu");
			String nimhs=null;
			String shift=null;
			String tahun=null;
			String smawl=null;
			String btstu=null;
			String assma=null;
			String stpid=null;
			String noprm=null;
			String nokp1=null;
			String nokp2=null;
			String nokp3=null;
			String nokp4=null;
			String krklm=null;
			String npm_pa=null;
			String nmm_pa=null;
			String sksdi=null;
			String asnim=null;
			String aspti=null;
			String asjen=null;
			String aspst=null;
			String aspti_unlisted=null;
			if(!Checker.isStringNullOrEmpty(info)) {
				st = new StringTokenizer(info,"`");
				nimhs=st.nextToken();
				shift=st.nextToken();
				tahun=st.nextToken();
				smawl=st.nextToken();
				btstu=st.nextToken();
				assma=st.nextToken();
				stpid=st.nextToken();
				noprm=st.nextToken();
				nokp1=st.nextToken();
				nokp2=st.nextToken();
				nokp3=st.nextToken();
				nokp4=st.nextToken();
				krklm=st.nextToken();
				npm_pa=st.nextToken();
				nmm_pa=st.nextToken();
				sksdi=st.nextToken();
				asnim=st.nextToken();
				aspti=st.nextToken();
				asjen=st.nextToken();
				aspst=st.nextToken();
				aspti_unlisted=st.nextToken();
			}
			
			
			nimhs = ""+request.getParameter("nimhs");
			shift = ""+request.getParameter("shift");
			tahun = ""+request.getParameter("tahun");
			stpid = ""+request.getParameter("stpid");
			smawl = ""+request.getParameter("smawl");
			btstu = ""+request.getParameter("btstu");
			String bimbing = ""+request.getParameter("bimbing"); //npmdos`nmdos
			String kurikulum = ""+request.getParameter("krklm");//idkur+"`"+nmkur+"`"+stkur+"`"+start+"`"+end+"`"+skstt+"`"+smstt
			sksdi = ""+request.getParameter("sksdi");
			asnim = ""+request.getParameter("asnim");
			aspti = ""+request.getParameter("aspti");
			
			asjen = ""+request.getParameter("asjen");
			aspst = ""+request.getParameter("aspst");
			aspti_unlisted = ""+request.getParameter("aspti_unlisted");
			
			String tombol_asal = ""+request.getParameter("tombol_asal");
			//System.out.println("tombol asal="+tombol_asal);
			
			
			/*
			 * =================reserved-===================================
			 * PROSES VALIDASI UNTUK CREATE WARNING ONLY KARENA INI UPDATE PROSES BUKAN INPUT PERTAMA
			 * =================reserved-===================================
			 * 
			 */
			
			
			
			
			/*
			 * form menerima tkn string info, disini nilainya di replace dengan apa yg edit dai form
			 * 				VARIABLE PERTAMA dan akhir WAJIB ADA VALUEYA WALAU NULL
			 */
			if(Checker.isStringNullOrEmpty(nimhs)) {
				nimhs="null";
			}
			if(!Checker.isStringNullOrEmpty(bimbing)) {
				st = new StringTokenizer(bimbing,"`");
				npm_pa=st.nextToken();
				nmm_pa=st.nextToken();
			}
			if(Checker.isStringNullOrEmpty(nmm_pa)) {
				nmm_pa="null";
			}
			if(!Checker.isStringNullOrEmpty(kurikulum)) {
				st = new StringTokenizer(kurikulum,"`");
				krklm=st.nextToken();
				
			}
			if(Checker.isStringNullOrEmpty(aspst)) {
				aspst="null";
			}
			if(Checker.isStringNullOrEmpty(aspti_unlisted)) {
				aspti_unlisted="null";
			}
			
			info = nimhs +"`"+shift +"`"+tahun +"`"+smawl +"`"+btstu +"`"+assma +"`"+stpid +"`"+noprm +"`"+nokp1 +"`"+nokp2 +"`"+nokp3 +"`"+nokp4 +"`"+krklm +"`"+npm_pa +"`"+nmm_pa+"`"+sksdi+"`"+asnim+"`"+aspti+"`"+asjen+"`"+aspst+"`"+aspti_unlisted;

			info = Checker.pnn_v1(info);
			//System.out.println("ifo2="+info);
			String target = Constants.getRootWeb()+"/InnerFrame/Profile/kemahasiswaan/edit_profile_kemahasiswaan.jsp";
			String url = "";
			
			if(!Checker.isStringNullOrEmpty(tombol_asal) && tombol_asal.equalsIgnoreCase("tombol_submit")) {
				//update lalu redirect ke profile
				url = "get.profile_v1";
				cmd = "profile";
				UpdateDbInfoMhsDataPri udp = new UpdateDbInfoMhsDataPri(isu.getNpm());
				udp.updateDataKemahasiswaan(npm, info);
			}
			else {
				String uri = request.getRequestURI();
				url = PathFinder.getPath(uri, target);	
			}

			
			
			request.setAttribute("info", info);
			request.getRequestDispatcher(url+"?id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd="+cmd+"&atMenu="+atMenu).forward(request,response);
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

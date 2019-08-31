package servlets.mhs.profile.ortu;

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
 * Servlet implementation class UpdateDataOrtuCivitas
 */
@WebServlet("/UpdateDataOrtuCivitas")
public class UpdateDataOrtuCivitas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateDataOrtuCivitas() {
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
			Enumeration paramNames = request.getParameterNames();
			while(paramNames.hasMoreElements())
			{
			      String paramName = (String)paramNames.nextElement();
			      //System.out.println(paramName);
			}
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
			String nmmay=null;
			String tglay=null;
			String tplay=null;
			String llsay=null;
			String hpeay=null;
			String jobay=null;
			String payay=null;
			String nikay=null;
			String rilay=null;
			String nmmbu=null;
			String tglbu=null;
			String tplbu=null;
			String llsbu=null;
			String hpebu=null;
			String jobbu=null;
			String paybu=null;
			String nikbu=null;
			String rilbu=null;
			String nmmwa=null;
			String tglwa=null;
			String tplwa=null;
			String llswa=null;
			String hpewa=null;
			String jobwa=null;
			String paywa=null;
			String nikwa=null;
			String hubwa=null;
			String nmer1=null;
			String hper1=null;
			String hber1=null;
			String nmer2=null;
			String hper2=null;
			String hber2=null;

			if(!Checker.isStringNullOrEmpty(info)) {
				st = new StringTokenizer(info,"`");
				nmmay=st.nextToken();
				tglay=st.nextToken();
				tplay=st.nextToken();
				llsay=st.nextToken();
				hpeay=st.nextToken();
				jobay=st.nextToken();
				payay=st.nextToken();
				nikay=st.nextToken();
				rilay=st.nextToken();
				nmmbu=st.nextToken();
				tglbu=st.nextToken();
				tplbu=st.nextToken();
				llsbu=st.nextToken();
				hpebu=st.nextToken();
				jobbu=st.nextToken();
				paybu=st.nextToken();
				nikbu=st.nextToken();
				rilbu=st.nextToken();
				nmmwa=st.nextToken();
				tglwa=st.nextToken();
				tplwa=st.nextToken();
				llswa=st.nextToken();
				hpewa=st.nextToken();
				jobwa=st.nextToken();
				paywa=st.nextToken();
				nikwa=st.nextToken();
				hubwa=st.nextToken();
				nmer1=st.nextToken();
				hper1=st.nextToken();
				hber1=st.nextToken();
				nmer2=st.nextToken();
				hper2=st.nextToken();
				hber2=st.nextToken();
			}
			
			
			
			
			//li.add(id_wil+"`"+id_negara+"`"+nm_wil+"`"+id_induk_wilayah);
			
			nmmay = ""+request.getParameter("nmmay");
			
			tglay = ""+request.getParameter("tglay");
			tplay = ""+request.getParameter("tplay");
			llsay = ""+request.getParameter("llsay");
			hpeay = ""+request.getParameter("hpeay");
			jobay = ""+request.getParameter("jobay");
			payay = ""+request.getParameter("payay");
			nikay = ""+request.getParameter("nikay");
			rilay = ""+request.getParameter("rilay");
			nmmbu = ""+request.getParameter("nmmbu");
			tglbu = ""+request.getParameter("tglbu");
			tplbu = ""+request.getParameter("tplbu");
			llsbu = ""+request.getParameter("llsbu");
			hpebu = ""+request.getParameter("hpebu");
			jobbu = ""+request.getParameter("jobbu");
			paybu = ""+request.getParameter("paybu");
			nikbu = ""+request.getParameter("nikbu");
			rilbu = ""+request.getParameter("rilbu");
			nmmwa = ""+request.getParameter("nmmwa");
			tglwa = ""+request.getParameter("tglwa");
			tplwa = ""+request.getParameter("tplwa");
			llswa = ""+request.getParameter("llswa");
			hpewa = ""+request.getParameter("hpewa");
			jobwa = ""+request.getParameter("jobwa");
			paywa = ""+request.getParameter("paywa");
			nikwa = ""+request.getParameter("nikwa");
			hubwa = ""+request.getParameter("hubwa");
			nmer1 = ""+request.getParameter("nmer1");
			hper1 = ""+request.getParameter("hper1");
			hber1 = ""+request.getParameter("hber1");
			nmer2 = ""+request.getParameter("nmer2");
			hper2 = ""+request.getParameter("hper2");
			hber2 = ""+request.getParameter("hber2");
			
			
			
			String tombol_asal = ""+request.getParameter("tombol_asal");
			//System.out.println("tombol asal="+tombol_asal);
			
			
			/*
			 * ========boolean wajib_update_profile = Boolean.valueOf((String)session.getAttribute("wajib_update_profil"));
			if(wajib_update_profile) {
				if(isu.iAmStu() && !isu.sudahUpdateDataNikDanIbuKandung()) {
					session.setAttribute("wajib_update_profil", "true");
				}
				else {
					session.setAttribute("wajib_update_profil", "false");
					target = Constants.getRootWeb()+"/InnerFrame/Tamplete/home_versi_selesai_upd_profile.jsp";
					String uri = request.getRequestURI();
					url = PathFinder.getPath(uri, target);
				}
				//
			}=========reserved-===================================
			 * PROSES VALIDASI UNTUK CREATE WARNING ONLY KARENA INI UPDATE PROSES BUKAN INPUT PERTAMA
			 * =================reserved-===================================
			 * 
			 */
			
			
			
			
			/*
			 * form menerima tkn string info, disini nilainya di replace dengan apa yg edit dai form
			 * 				VARIABLE PERTAMA dan akhir WAJIB ADA VALUEYA WALAU NULL
			 */
			if(Checker.isStringNullOrEmpty(nmmay)) {
				nmmay="null";
			}
			if(Checker.isStringNullOrEmpty(hber2)) {
				hber2="null";
			}
			info = nmmay+"`"+tglay+"`"+tplay+"`"+llsay+"`"+hpeay+"`"+jobay+"`"+payay+"`"+nikay+"`"+rilay+"`"+nmmbu+"`"+tglbu+"`"+tplbu+"`"+llsbu+"`"+hpebu+"`"+jobbu+"`"+paybu+"`"+nikbu+"`"+rilbu+"`"+nmmwa+"`"+tglwa+"`"+tplwa+"`"+llswa+"`"+hpewa+"`"+jobwa+"`"+paywa+"`"+nikwa+"`"+hubwa+"`"+nmer1+"`"+hper1+"`"+hber1+"`"+nmer2+"`"+hper2+"`"+hber2;

			info = Checker.pnn_v1(info);
			//System.out.println("ifo2="+info);
			String target = Constants.getRootWeb()+"/InnerFrame/Profile/edit_profile_ortu.jsp";
			String url = "";
			
			if(!Checker.isStringNullOrEmpty(tombol_asal) && tombol_asal.equalsIgnoreCase("tombol_submit")) {
				//update lalu redirect ke profile
				url = "get.profile_v1";
				cmd = "profile";
				UpdateDbInfoMhsDataPri udp = new UpdateDbInfoMhsDataPri(isu.getNpm());
				udp.updateDataOrtu(npm, info);
			}
			else {
				String uri = request.getRequestURI();
				url = PathFinder.getPath(uri, target);	
			}
			/*
			 * SECTION : WAJIB UPDATE FORM PROFILE
			 */
			//cek apa saat ini dalam status "wajib update"
			//jika iya maka cek status setelah update dan kemudian update status barunya
			boolean wajib_update_profile = Boolean.valueOf((String)session.getAttribute("wajib_update_profil"));
			if(wajib_update_profile) {
				if(isu.iAmStu() && !isu.sudahUpdateDataNikDanIbuKandung()) {
					session.setAttribute("wajib_update_profil", "true");
				}
				else {
					session.setAttribute("wajib_update_profil", "false");
					target = Constants.getRootWeb()+"/InnerFrame/Tamplete/home_versi_selesai_upd_profile.jsp";
					String uri = request.getRequestURI();
					url = PathFinder.getPath(uri, target);
				}
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

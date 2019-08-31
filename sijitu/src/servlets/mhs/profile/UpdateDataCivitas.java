package servlets.mhs.profile;

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
 * Servlet implementation class UpdateDataCivitas
 */
@WebServlet("/UpdateDataCivitas")
public class UpdateDataCivitas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateDataCivitas() {
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
			String nmmhs=null;
			String tplhr=null;
			String tglhr=null;
			String kdjek=null;
			String nisn=null;
			String warganegara=null;
			String niktp=null;
			String nosim=null;
			String paspo=null;
			String angel=null;
			String sttus=null;
			String email=null;
			String nohpe=null;
			String almrm=null;
			String no_rt=null;
			String no_rw=null;
			String prorm=null;
			String proid=null;
			String kotrm=null;
			String kotid=null;
			String kecrm=null;
			String kecid=null;
			String kelrm=null;
			String dusun=null;
			String posrm=null;
			String telrm=null;
			String nglhr=null;
			String agama=null;

			if(!Checker.isStringNullOrEmpty(info)) {
				st = new StringTokenizer(info,"`");
				nmmhs=st.nextToken();
				tplhr=st.nextToken();
				tglhr=st.nextToken();
				kdjek=st.nextToken();
				nisn=st.nextToken();
				warganegara=st.nextToken();
				niktp=st.nextToken();
				nosim=st.nextToken();
				paspo=st.nextToken();
				angel=st.nextToken();
				sttus=st.nextToken();
				email=st.nextToken();
				nohpe=st.nextToken();
				almrm=st.nextToken();
				no_rt=st.nextToken();
				no_rw=st.nextToken();
				prorm=st.nextToken();
				proid=st.nextToken();
				kotrm=st.nextToken();
				kotid=st.nextToken();
				kecrm=st.nextToken();
				kecid=st.nextToken();
				kelrm=st.nextToken();
				dusun=st.nextToken();
				posrm=st.nextToken();
				telrm=st.nextToken();
				nglhr=st.nextToken();
				agama=st.nextToken();
			}
			
			
			
			
			//li.add(id_wil+"`"+id_negara+"`"+nm_wil+"`"+id_induk_wilayah);
			
			String nglhrid = "null";
			String warganegaraid = "null";
			nmmhs = ""+request.getParameter("nmmhs");
			tplhr = ""+request.getParameter("tplhr");
			tglhr = ""+request.getParameter("tglhr");
			kdjek = ""+request.getParameter("kdjek");
			nisn = ""+request.getParameter("nisn");
			warganegara = ""+request.getParameter("warganegara");
			if(!Checker.isStringNullOrEmpty(warganegara)) {
				//System.out.println("warganegara="+warganegara);
				st = new StringTokenizer(warganegara,"`");
				warganegaraid = st.nextToken();
				st.nextToken();
				warganegara = st.nextToken();
			}
			niktp = ""+request.getParameter("niktp");
			nosim = ""+request.getParameter("nosim");
			paspo = ""+request.getParameter("paspo");
			angel = ""+request.getParameter("angel");
			sttus = ""+request.getParameter("sttus");
			email = ""+request.getParameter("email");
			nohpe = ""+request.getParameter("nohpe");
			almrm = ""+request.getParameter("almrm");
			no_rt = ""+request.getParameter("no_rt");
			no_rw = ""+request.getParameter("no_rw");
			prorm = ""+request.getParameter("prorm");
			if(!Checker.isStringNullOrEmpty(prorm)) {
				//System.out.println("prorm="+prorm);
				st = new StringTokenizer(prorm,"`");
				proid = st.nextToken();
				st.nextToken();
				prorm = st.nextToken();
			}
			kotrm = request.getParameter("kotrm");
			if(!Checker.isStringNullOrEmpty(kotrm)) {
				//System.out.println("kotrm="+kotrm);
				st = new StringTokenizer(kotrm,"`");
				kotid = st.nextToken();
				st.nextToken();
				kotrm = st.nextToken();
			}
			kecrm = request.getParameter("kecrm");
			if(!Checker.isStringNullOrEmpty(kecrm)) {
				//System.out.println("kecrm="+kecrm);
				st = new StringTokenizer(kecrm,"`");
				kecid = st.nextToken();
				st.nextToken();
				kecrm = st.nextToken();
			}
			kelrm = ""+request.getParameter("kelrm");
			dusun = ""+request.getParameter("dusun");
			posrm = ""+request.getParameter("posrm");
			telrm = ""+request.getParameter("telrm");
			nglhr = ""+request.getParameter("nglhr");
			if(!Checker.isStringNullOrEmpty(nglhr)) {
				//System.out.println("nglhr="+nglhr);
				st = new StringTokenizer(nglhr,"`");
				nglhrid = st.nextToken();
				st.nextToken();
				nglhr = st.nextToken();
			}
			agama = ""+request.getParameter("agama");
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
			if(Checker.isStringNullOrEmpty(nmmhs)) {
				nmmhs="null";
			}
			if(Checker.isStringNullOrEmpty(agama)) {
				agama="null";
			}
			info = nmmhs+"`"+tplhr+"`"+tglhr+"`"+kdjek+"`"+nisn+"`"+warganegara+"`"+niktp+"`"+nosim+"`"+paspo+"`"+angel+"`"+sttus+"`"+email+"`"+nohpe+"`"+almrm+"`"+no_rt+"`"+no_rw+"`"+prorm+"`"+proid+"`"+kotrm+"`"+kotid+"`"+kecrm+"`"+kecid+"`"+kelrm+"`"+dusun+"`"+posrm+"`"+telrm+"`"+nglhr+"`"+agama;

			info = Checker.pnn_v1(info);
			String target = Constants.getRootWeb()+"/InnerFrame/Profile/edit_profile_v1.jsp";
			String url = "";
			String cmd = request.getParameter("cmd");
			String atMenu = request.getParameter("atMenu");
			if(!Checker.isStringNullOrEmpty(tombol_asal) && tombol_asal.equalsIgnoreCase("tombol_submit")) {
				//update lalu redirect ke profile
				url = "get.profile_v1";
				cmd = "profile";
				UpdateDbInfoMhsDataPri udp = new UpdateDbInfoMhsDataPri(isu.getNpm());
				udp.updateDataPribadi(npm, info);
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

package servlets.mhs.profile.dosen;

import java.io.IOException;
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
 * Servlet implementation class UpdateDataDosenCivitas
 */
@WebServlet("/UpdateDataDosenCivitas")
public class UpdateDataDosenCivitas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateDataDosenCivitas() {
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
			
			String npmhs = null;
			String local = null;
			String gelar_depan = null;
			String gelar_belakang = null;
			String nidn = null;
			String tipe_id = null;
			String no_id = null;
			String status = null;
			String pt_s1 = null;
			String jur_s1 = null;
			String kdpst_s1 = null;
			String gelar_s1 = null;
			String bidil_s1 = null;
			String noija_s1 = null;
			String tglls_s1 = null;
			String file_ija_s1 = null;
			String judul_s1 = null;
			String pt_s2 = null;
			String jur_s2 = null;
			String kdpst_s2 = null;
			String gelar_s2 = null;
			String bidil_s2 = null;
			String noija_s2 = null;
			String tglls_s2 = null;
			String file_ija_s2 = null;
			String judul_s2 = null;
			String pt_s3 = null;
			String jur_s3 = null;
			String kdpst_s3 = null;
			String gelar_s3 = null;
			String bidil_s3 = null;
			String noija_s3 = null;
			String tglls_s3 = null;
			String file_ija_s3 = null;
			String judul_s3 = null;
			String pt_gb = null;
			String jur_gb = null;
			String kdpst_gb = null;
			String gelar_gb = null;
			String bidil_gb = null;
			String noija_gb = null;
			String tglls_gb = null;
			String file_ija_gb = null;
			String judul_gb = null;
			String tot_kum = null;
			String jja_dikti = null;
			String jja_local = null;
			String jab_struk = null;
			String tipe_ika = null;
			String tgl_in = null;
			String tgl_out = null;
			String serdos = null;
			String kdpti_home = null;
			String kdpst_home = null;
			String email_org = null;
			String pangkat_gol = null;
			String catatan_riwayat = null;
			String ktp_sim_paspo = null;
			String no_ktp_sim_paspo = null;
			String nik = null;
			String nip = null;
			String niy_nigk = null;
			String nuptk = null;
			String nsdmi = null;
			String nidk = null;
			String nup = null;
			if(!Checker.isStringNullOrEmpty(info)) {
				st = new StringTokenizer(info,"`");
				kdpst = st.nextToken();
				npmhs = st.nextToken();
				local = st.nextToken();
				gelar_depan = st.nextToken();
				gelar_belakang = st.nextToken();
				nidn = st.nextToken();
				tipe_id = st.nextToken();
				no_id = st.nextToken();
				status = st.nextToken();
				pt_s1 = st.nextToken();
				jur_s1 = st.nextToken();
				kdpst_s1 = st.nextToken();
				gelar_s1 = st.nextToken();
				bidil_s1 = st.nextToken();
				noija_s1 = st.nextToken();
				tglls_s1 = st.nextToken();
				file_ija_s1 = st.nextToken();
				judul_s1 = st.nextToken();
				pt_s2 = st.nextToken();
				jur_s2 = st.nextToken();
				kdpst_s2 = st.nextToken();
				gelar_s2 = st.nextToken();
				bidil_s2 = st.nextToken();
				noija_s2 = st.nextToken();
				tglls_s2 = st.nextToken();
				file_ija_s2 = st.nextToken();
				judul_s2 = st.nextToken();
				pt_s3 = st.nextToken();
				jur_s3 = st.nextToken();
				kdpst_s3 = st.nextToken();
				gelar_s3 = st.nextToken();
				bidil_s3 = st.nextToken();
				noija_s3 = st.nextToken();
				tglls_s3 = st.nextToken();
				file_ija_s3 = st.nextToken();
				judul_s3 = st.nextToken();
				pt_gb = st.nextToken();
				jur_gb = st.nextToken();
				kdpst_gb = st.nextToken();
				gelar_gb = st.nextToken();
				bidil_gb = st.nextToken();
				noija_gb = st.nextToken();
				tglls_gb = st.nextToken();
				file_ija_gb = st.nextToken();
				judul_gb = st.nextToken();
				tot_kum = st.nextToken();
				jja_dikti = st.nextToken();
				jja_local = st.nextToken();
				jab_struk = st.nextToken();
				tipe_ika = st.nextToken();
				tgl_in = st.nextToken();
				tgl_out = st.nextToken();
				serdos = st.nextToken();
				kdpti_home = st.nextToken();
				kdpst_home = st.nextToken();
				email_org = st.nextToken();
				pangkat_gol = st.nextToken();
				catatan_riwayat = st.nextToken();
				ktp_sim_paspo = st.nextToken();
				no_ktp_sim_paspo = st.nextToken();
				nik = st.nextToken();
				nip = st.nextToken();
				niy_nigk = st.nextToken();
				nuptk = st.nextToken();
				nsdmi = st.nextToken();
			}
			
			//kdpst = ""+request.getParameter("kdpst");
			//npmhs = ""+request.getParameter("npmhs");
			//local = ""+request.getParameter("local");//DEPRECATED-TIDAK DIPAKE
			gelar_depan = ""+request.getParameter("gelar_depan");
			gelar_belakang = ""+request.getParameter("gelar_belakang");
			nidn = ""+request.getParameter("nidn");
			/*
			 DOBEL ktp_sim_paspo dan no_ktp_sim_paspo
			tipe_id = ""+request.getParameter("tipe_id");
			no_id = ""+request.getParameter("no_id");
			*/
			
			status = ""+request.getParameter("statusDsn");
			pt_s1 = ""+request.getParameter("pt_s1");
			jur_s1 = ""+request.getParameter("jur_s1");
			kdpst_s1 = ""+request.getParameter("kdpst_s1");
			gelar_s1 = ""+request.getParameter("gelar_s1");
			bidil_s1 = ""+request.getParameter("bidil_s1");
			noija_s1 = ""+request.getParameter("noija_s1");
			tglls_s1 = ""+request.getParameter("tglls_s1");
			//file_ija_s1 = ""+request.getParameter("file_ija_s1");
			//judul_s1 = ""+request.getParameter("judul_s1");
			pt_s2 = ""+request.getParameter("pt_s2");
			jur_s2 = ""+request.getParameter("jur_s2");
			kdpst_s2 = ""+request.getParameter("kdpst_s2");
			gelar_s2 = ""+request.getParameter("gelar_s2");
			bidil_s2 = ""+request.getParameter("bidil_s2");
			noija_s2 = ""+request.getParameter("noija_s2");
			tglls_s2 = ""+request.getParameter("tglls_s2");
			//file_ija_s2 = ""+request.getParameter("file_ija_s2");
			//judul_s2 = ""+request.getParameter("judul_s2");
			pt_s3 = ""+request.getParameter("pt_s3");
			jur_s3 = ""+request.getParameter("jur_s3");
			kdpst_s3 = ""+request.getParameter("kdpst_s3");
			gelar_s3 = ""+request.getParameter("gelar_s3");
			bidil_s3 = ""+request.getParameter("bidil_s3");
			noija_s3 = ""+request.getParameter("noija_s3");
			tglls_s3 = ""+request.getParameter("tglls_s3");
			//file_ija_s3 = ""+request.getParameter("file_ija_s3");
			//judul_s3 = ""+request.getParameter("judul_s3");
			pt_gb = ""+request.getParameter("pt_gb");
			jur_gb = ""+request.getParameter("jur_gb");
			kdpst_gb = ""+request.getParameter("kdpst_gb");
			gelar_gb = ""+request.getParameter("gelar_gb");
			bidil_gb = ""+request.getParameter("bidil_gb");
			noija_gb = ""+request.getParameter("noija_gb");
			tglls_gb = ""+request.getParameter("tglls_gb");
			//file_ija_gb = ""+request.getParameter("file_ija_gb");
			//judul_gb = ""+request.getParameter("judul_gb");
			tot_kum = ""+request.getParameter("tot_kum");
			jja_dikti = ""+request.getParameter("jja_dikti");
			jja_local = ""+request.getParameter("jja_local");
			jab_struk = ""+request.getParameter("jab_struk");
			tipe_ika = ""+request.getParameter("tipe_ika");
			tgl_in = ""+request.getParameter("tgl_in");
			tgl_out = ""+request.getParameter("tgl_out");
			serdos = ""+request.getParameter("serdos");
			kdpti_home = ""+request.getParameter("kdpti_home");
			kdpst_home = ""+request.getParameter("kdpst_home");
			//email_org = ""+request.getParameter("email_org");
			//pangkat_gol = ""+request.getParameter("pangkat_gol");
			catatan_riwayat = ""+request.getParameter("catatan_riwayat");
			if(!Checker.isStringNullOrEmpty(catatan_riwayat)) {
				catatan_riwayat = catatan_riwayat.trim();
				if(catatan_riwayat.contains(System.getProperty("line.separator"))) {
					catatan_riwayat=catatan_riwayat.replace(System.getProperty("line.separator"), "<br>");
					catatan_riwayat=catatan_riwayat.replace("\t", "");
					
				}
				//System.out.println(catatan_riwayat.contains("\t"));
				
				//System.out.println(catatan_riwayat.contains("\t"));
				
			}
			
			ktp_sim_paspo = ""+request.getParameter("tipe_id");
			no_ktp_sim_paspo = ""+request.getParameter("no_id");
			nik = ""+request.getParameter("nik");
			nip = ""+request.getParameter("nip");
			niy_nigk = ""+request.getParameter("niy_nigk");
			nuptk = ""+request.getParameter("nuptk");
			nsdmi = ""+request.getParameter("nsdmi");
			nidk = ""+request.getParameter("nidk");
			nup = ""+request.getParameter("nup");
			
			String tombol_asal = ""+request.getParameter("tombol_asal");
			System.out.println("jja_local asal="+jja_local);
			
			
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
			if(Checker.isStringNullOrEmpty(kdpst)) {
				kdpst="null";
			}
			
			if(Checker.isStringNullOrEmpty(nsdmi)) {
				nsdmi="null";
			}
			
			if(Checker.isStringNullOrEmpty(nup)) {
				nup="null";
			}
			
			
			//info = kdpst+"`"+npmhs+"`"+local+"`"+gelar_depan+"`"+gelar_belakang+"`"+nidn+"`"+tipe_id+"`"+no_id+"`"+status+"`"+pt_s1+"`"+jur_s1+"`"+kdpst_s1+"`"+gelar_s1+"`"+bidil_s1+"`"+noija_s1+"`"+tglls_s1+"`"+file_ija_s1+"`"+judul_s1+"`"+pt_s2+"`"+jur_s2+"`"+kdpst_s2+"`"+gelar_s2+"`"+bidil_s2+"`"+noija_s2+"`"+tglls_s2+"`"+file_ija_s2+"`"+judul_s2+"`"+pt_s3+"`"+jur_s3+"`"+kdpst_s3+"`"+gelar_s3+"`"+bidil_s3+"`"+noija_s3+"`"+tglls_s3+"`"+file_ija_s3+"`"+judul_s3+"`"+pt_gb+"`"+jur_gb+"`"+kdpst_gb+"`"+gelar_gb+"`"+bidil_gb+"`"+noija_gb+"`"+tglls_gb+"`"+file_ija_gb+"`"+judul_gb+"`"+tot_kum+"`"+jja_dikti+"`"+jja_local+"`"+jab_struk+"`"+tipe_ika+"`"+tgl_in+"`"+tgl_out+"`"+serdos+"`"+kdpti_home+"`"+kdpst_home+"`"+email_org+"`"+pangkat_gol+"`"+catatan_riwayat+"`"+ktp_sim_paspo+"`"+no_ktp_sim_paspo+"`"+nik+"`"+nip+"`"+niy_nigk+"`"+nuptk+"`"+nsdmi;
			info = kdpst+"`"+npmhs+"`"+local+"`"+gelar_depan+"`"+gelar_belakang+"`"+nidn+"`"+tipe_id+"`"+no_id+"`"+status+"`"+pt_s1+"`"+jur_s1+"`"+kdpst_s1+"`"+gelar_s1+"`"+bidil_s1+"`"+noija_s1+"`"+tglls_s1+"`"+file_ija_s1+"`"+judul_s1+"`"+pt_s2+"`"+jur_s2+"`"+kdpst_s2+"`"+gelar_s2+"`"+bidil_s2+"`"+noija_s2+"`"+tglls_s2+"`"+file_ija_s2+"`"+judul_s2+"`"+pt_s3+"`"+jur_s3+"`"+kdpst_s3+"`"+gelar_s3+"`"+bidil_s3+"`"+noija_s3+"`"+tglls_s3+"`"+file_ija_s3+"`"+judul_s3+"`"+pt_gb+"`"+jur_gb+"`"+kdpst_gb+"`"+gelar_gb+"`"+bidil_gb+"`"+noija_gb+"`"+tglls_gb+"`"+file_ija_gb+"`"+judul_gb+"`"+tot_kum+"`"+jja_dikti+"`"+jja_local+"`"+jab_struk+"`"+tipe_ika+"`"+tgl_in+"`"+tgl_out+"`"+serdos+"`"+kdpti_home+"`"+kdpst_home+"`"+email_org+"`"+pangkat_gol+"`"+catatan_riwayat+"`"+ktp_sim_paspo+"`"+no_ktp_sim_paspo+"`"+nik+"`"+nip+"`"+niy_nigk+"`"+nuptk+"`"+nsdmi+"`"+nidk+"`"+nup;

			info = Checker.pnn_v1(info);
			//System.out.println("ifo2="+info);
			String target = Constants.getRootWeb()+"/InnerFrame/Profile/dosen/edit_profile_dosen.jsp";
			String url = "";
			
			if(!Checker.isStringNullOrEmpty(tombol_asal) && tombol_asal.equalsIgnoreCase("tombol_submit")) {
				//update lalu redirect ke profile
				url = "get.profile_v1";
				cmd = "profile";
				UpdateDbInfoMhsDataPri udp = new UpdateDbInfoMhsDataPri(isu.getNpm());
				udp.updateDataDosen(npm, info);
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

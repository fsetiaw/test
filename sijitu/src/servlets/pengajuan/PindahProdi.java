package servlets.pengajuan;

import java.io.IOException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.topik.UpdateDbTopik;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class Cuti
 */
@WebServlet("/PindahProdi")
public class PindahProdi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PindahProdi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
			//System.out.println("PindahProdiii");
			/*
			 * hanya posting pengajuan ke 
			 */
			String info_target_nu_prodi=""+request.getParameter("nu_prodi");
			StringTokenizer st = new StringTokenizer(info_target_nu_prodi,"`");
			String target_nu_prodi = st.nextToken(); 
			String id_obj=""+request.getParameter("id_obj");
			String nmm=""+request.getParameter("nmm");
			String scope=""+request.getParameter("scope");
			String table_nm=""+request.getParameter("table");
			//System.out.println("table_nl="+table_nm);
			String npm=""+request.getParameter("npm");
			String obj_lvl=""+request.getParameter("obj_lvl");
			String kdpst=""+request.getParameter("kdpst");
			String folder_pengajuan=""+request.getParameter("folder_pengajuan");
			String cmd=""+request.getParameter("pp");
			String alasan=""+request.getParameter("alasan");
			String file_nmm=""+request.getParameter("file_nmm");
			if(!Checker.isStringNullOrEmpty(file_nmm)) { //digunakan oleh pengajuan ujian akhir
				String tipe_ujian = request.getParameter("tipe_ujian");
				alasan = tipe_ujian+"~"+alasan+"~"+file_nmm;
			}
			String target_thsms=""+request.getParameter("target_thsms");
			String domisili_kmp = Getter.getDomisiliKampus(npm);
			String tipe_pengajuan = table_nm.replace("_RULES", "");
			String title_pengajuan = table_nm.replace("_", "_");
			////System.out.println(kdpst+" to "+target_nu_prodi);
			UpdateDbTopik udb = new UpdateDbTopik(isu.getNpm());
			Vector v = Getter.getVerificatorFromTableRule(table_nm, target_thsms, kdpst, target_nu_prodi, domisili_kmp, domisili_kmp);
			//ListIterator li = v.listIterator();
			//String merge = Getter.mergeVerificator(v);
			//System.out.println("merge="+merge);
			//check jika vector > 1 spt kasus pindah prodi
			String msg = null;
			if(info_target_nu_prodi!=null && !Checker.isStringNullOrEmpty(info_target_nu_prodi)) {
				//berARTI pindah prodi butuh 2 variable in out
				//System.out.println("1");
				if(tipe_pengajuan.equalsIgnoreCase("PINDAH_PRODI")) {
					//postTopikPengajuanPP(String nama_pengajuan_yg_juga_nama_tabel_rules, String target_thsms, String npmhs, String alasan, String nmmhs, String kdpst_origin, String kdpst_target, String kmp_origin, String kmp_target)
					msg = udb.postTopikPengajuan(table_nm, target_thsms, npm, alasan, nmm, kdpst, target_nu_prodi, domisili_kmp, domisili_kmp);
					//System.out.println("2");
					udb.updatePengajuanAtTabelOverview(tipe_pengajuan, target_thsms, npm,   kdpst, domisili_kmp, "out");//info origin
					//System.out.println("3");
					udb.updatePengajuanAtTabelOverview(tipe_pengajuan, target_thsms, npm,   target_nu_prodi, domisili_kmp, "in");//info target prodi
					//System.out.println("4");
				}
			}
			//else if(tipe_pengajuan.equalsIgnoreCase("AKTIF_KEMBALI")) {
			//	msg = udb.postTopikPengajuan(table_nm, target_thsms, npm, alasan, nmm, kdpst, null, domisili_kmp, null);
			//}
			else {
				msg = udb.postTopikPengajuan(table_nm, target_thsms, npm, alasan, nmm, kdpst, null, domisili_kmp, null);
				udb.updatePengajuanAtTabelOverview(tipe_pengajuan, target_thsms, npm,   kdpst, domisili_kmp, null);//info origin
				//udb.updatePengajuanAtTabelOverview(table_nm.replace("_RULES", ""), target_thsms, npm,   target_nu_prodi, domisili_kmp, "in");//info target prodi
			}
			
			
			
			//String msg = udb.postTopikPengajuanPindahProdi(target_thsms,kdpst, npm, alasan, nmm);
			
			if(msg==null || Checker.isStringNullOrEmpty(msg)) {
				msg = new String("upd");
			}
			//go.moPp?target_thsms=<%=Checker.getThsmsHeregistrasi() %>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=pp
			
			//System.out.println("go.moPp?target_thsms="+target_thsms+"&id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm +"&obj_lvl="+obj_lvl +"&kdpst="+kdpst+"&cmd=pp&msg="+msg+"&scope="+scope+"&table="+table_nm+"&folder_pengajuan="+folder_pengajuan); 
			request.getRequestDispatcher("go.moPp?target_thsms="+target_thsms+"&id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm +"&obj_lvl="+obj_lvl +"&kdpst="+kdpst+"&cmd=pp&msg="+msg+"&scope="+scope+"&table="+table_nm+"&folder_pengajuan="+folder_pengajuan).forward(request,response);
			
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

package servlets.spmi.standard;

import java.io.PrintWriter;
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

import beans.dbase.classPoll.SearchDbClassPoll;
import beans.dbase.dosen.SearchDbDsn;
import beans.dbase.mhs.btstu.SearchDbInfoMhsBtstu;
import beans.dbase.mhs.kurikulum.SearchDbInfoKurikulum;
import beans.dbase.spmi.SearchManualMutu;
import beans.dbase.spmi.SearchStandarMutu;
import beans.dbase.spmi.UpdateStandarMutu;
import beans.dbase.spmi.form.UpdateForm;
import beans.dbase.spmi.manual.SearchManual;
import beans.dbase.spmi.request.SearchRequest;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class SetAktivasiStd_v1
 */
@WebServlet("/SetAktivasiStd_v1")
public class SetAktivasiStd_v1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetAktivasiStd_v1() {
        super();
        //TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO Auto-generated method stub
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); //HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0.
		response.setHeader("Expires", "0"); //Proxies.
		response.setContentType("text/html");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");

		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
			//System.out.println("ampun");
			//mode=view&id_tipe_std=<%=id %>&id_master_std=<%=id_master %>&atMenu=&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_page=<%=at_page %>&max_data_per_pg=<%=max_data_per_pg %>
			
			//set.aktivasiStd_v2?am_i_terkait=<%=am_i_terkait %>&am_i_pengawas=<%=am_i_pengawas%>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=std&fwdto=dashboard_std_isi.jsp
			//		get.prepInfoStd?am_i_terkait=<%=terkait %>&am_i_pengawas=<%=pengawas%>&id_versi=<%=id_versi %>&id_std_isi=<%=id_std_isi %>&id_std=<%=id_std %>&kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>&at_menu_dash=std&fwdto=dashboard_std_isi.jsp','popup','width=850,height=600')"">
			String aktif = request.getParameter("aktif");
			String at_menu = request.getParameter("at_menu");
			String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
			StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
			String kdpst = st.nextToken();
			String nmpst = st.nextToken();
			String kdkmp = st.nextToken();
			String id_master_std = request.getParameter("id_master_std");
			String id_tipe_std=request.getParameter("id_tipe_std");;
			String id_std = request.getParameter("id_std");
			String id_versi = request.getParameter("id_versi");
			//System.out.println("id versi="+id_versi);
			UpdateStandarMutu usm = new UpdateStandarMutu();
			
			SearchStandarMutu ssm = new SearchStandarMutu();
			//System.out.println("anu="+ssm.apaStandarSudahAdaTglPerumusanUmum(Integer.parseInt(id_versi),Integer.parseInt(id_std)));
			Vector v_err=null;
			ListIterator li=null;
			boolean valid = true;
			boolean all_manual_active=true;
			if(!ssm.apaAdaManualPerencanaanYgAktif(Integer.parseInt(id_std))) {
				all_manual_active=false;
				valid = false;
				if(v_err==null) {
					v_err=new Vector();
					li = v_err.listIterator();
				}
				li.add("Standar ini belum memiliki MANUAL PERENCANAAN yang aktif");
			}
			if(!ssm.apaAdaManualPelaksanaanYgAktif(Integer.parseInt(id_std))) {
				all_manual_active=false;
				valid = false;
				if(v_err==null) {
					v_err=new Vector();
					li = v_err.listIterator();
				}
				li.add("Standar ini belum memiliki MANUAL PELAKSANAAN yang aktif");
			}
			if(!ssm.apaAdaManualEvaluasiYgAktif(Integer.parseInt(id_std))) {
				all_manual_active=false;
				valid = false;
				if(v_err==null) {
					v_err=new Vector();
					li = v_err.listIterator();
				}
				li.add("Standar ini belum memiliki MANUAL EVALUASI yang aktif");
			}
			if(!ssm.apaAdaManualPengendalianYgAktif(Integer.parseInt(id_std))) {
				all_manual_active=false;
				valid = false;
				if(v_err==null) {
					v_err=new Vector();
					li = v_err.listIterator();
				}
				li.add("Standar ini belum memiliki MANUAL PENGENDALIAN yang aktif");
			}
			if(!ssm.apaAdaManualPeningkatanYgAktif(Integer.parseInt(id_std))) {
				all_manual_active=false;
				valid = false;
				if(v_err==null) {
					v_err=new Vector();
					li = v_err.listIterator();
				}
				li.add("Standar ini belum memiliki MANUAL PENINGKATAN yang aktif");
			}
			if(!ssm.apaStandarSudahAdaTglPerumusanUmum(Integer.parseInt(id_versi),Integer.parseInt(id_std))) {
				valid = false;
				if(v_err==null) {
					v_err=new Vector();
					li = v_err.listIterator();
				}
				li.add("Standar ini belum melalui proses kegiatan perumusan. Silahkan tambahkan kegiatan perumusan pada menu MANUAL PERENCANAAN STANDAR ");
			}
			if(!ssm.apaStandarSudahAdaTglPemeriksaanUmum(Integer.parseInt(id_versi),Integer.parseInt(id_std))) {
				valid = false;
				if(v_err==null) {
					v_err=new Vector();
					li = v_err.listIterator();
				}
				li.add("Standar ini belum melalui proses kegiatan pemeriksaan. Silahkan tambahkan kegiatan pemeriksaan pada menu MANUAL PERENCANAAN STANDAR");
			}
			if(!ssm.apaStandarSudahAdaTglPersetujuanUmum(Integer.parseInt(id_versi),Integer.parseInt(id_std))) {
				valid = false;
				if(v_err==null) {
					v_err=new Vector();
					li = v_err.listIterator();
				}
				li.add("Standar ini belum melalui proses kegiatan persetujuan. Silahkan tambahkan kegiatan persetujuan pada menu MANUAL PERENCANAAN STANDAR");
			}
			if(!ssm.apaStandarSudahAdaTglPenetapanUmum(Integer.parseInt(id_versi),Integer.parseInt(id_std))) {
				valid = false;
				if(v_err==null) {
					v_err=new Vector();
					li = v_err.listIterator();
				}
				li.add("Standar ini belum melalui proses kegiatan penetapan. Silahkan tambahkan kegiatan penetapan pada menu MANUAL PERENCANAAN STANDAR");
			}
			if(!ssm.apaSudahAdaKegiatanPelaksanaanStandar(id_std, id_versi)) {
				valid = false;
				if(v_err==null) {
					v_err=new Vector();
					li = v_err.listIterator();
				}
				li.add("Standar tidak bisa diaktifkan tanpa ada kegiatan pelaksanaan standar terlebih dahulu (kegiatan sosialisasi std, dll)");
			}
			SearchManualMutu smm = new  SearchManualMutu();
			boolean std_sdh_aktif=ssm.isStandardActivated(Integer.parseInt(id_versi), Integer.parseInt(id_std));
			boolean std_sdh_expired=ssm.isStandardExpired(Integer.parseInt(id_versi), Integer.parseInt(id_std));
			/*boolean all_manual_active=smm.apaSeluruhManualPpeppSudahAktif(Integer.parseInt(id_versi),Integer.parseInt(id_std));
			//System.out.println("all_manual_active="+all_manual_active);
			if(!all_manual_active && !std_sdh_aktif) {
				valid = false;
				if(v_err==null) {
					v_err=new Vector();
					li = v_err.listIterator();
				}
				li.add("Standar bisa diaktifkan jika seluruh manual perencanaan, pelaksanaan, evaluasi, pengendalian, dan peningkatan standar telah aktif");
			}
			*/
			request.setAttribute("v_err_std", v_err);
			if(v_err!=null) {
				//ada error
				
			}
			else {
				//updated pihak terkait draft jadi final
				int upd = usm.setPihakTerkaitDraftMenjadiFinal(id_std, id_versi);
				upd = upd+usm.toogleAktifasiStdUmum(Integer.parseInt(id_versi) ,Integer.parseInt(id_std));
			}
			request.getRequestDispatcher("go.getListAllStd?mode=start&id_tipe_std="+id_tipe_std+"&id_master_std="+id_master_std+"&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&at_menu="+at_menu).forward(request,response);	
			
		}
	}

	/**
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO Auto-generated method stub
		doGet(request, response);
	}

}

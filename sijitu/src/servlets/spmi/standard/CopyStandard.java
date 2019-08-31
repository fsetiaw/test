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
import beans.dbase.spmi.SearchStandarMutu;
import beans.dbase.spmi.UpdateStandarMutu;
import beans.dbase.spmi.form.UpdateForm;
import beans.dbase.spmi.manual.SearchManual;
import beans.dbase.spmi.request.SearchRequest;
import beans.dbase.spmi.request.UpdateRequest;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class CopyStandard
 */
@WebServlet("/CopyStandard")
public class CopyStandard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CopyStandard() {
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
			//System.out.println("clone");;
			String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
			String at_page = request.getParameter("at_page");
			String max_data_per_pg = request.getParameter("max_data_per_pg");
			String id_std_isi = request.getParameter("id_std_isi");
			String id_versi = request.getParameter("id_versi");
			
			String copy_as_prodi = request.getParameter("copy_as_prodi");
			
			//System.out.println("copy_as_prodi="+copy_as_prodi);
			
			if(Checker.isStringNullOrEmpty(copy_as_prodi)) {
				
			}
			else {
				
				UpdateStandarMutu usm = new UpdateStandarMutu();
				
				if(copy_as_prodi.equalsIgnoreCase("baru")) {
					int id_std_isi_baru = usm.copyStandard(id_std_isi, id_versi, "baru");
					
					
							//copy utk std baru
					//bedanya STANDARD_ISI_TABLE.ID_STD dikosongin karena buat std baru
					//int i = uf.insertNuStandarIsi(pihak_terkait,null , isi, doc_terkait, rasionale);
					//standar baru jdai belum ditentukan
					//int updated = ur.ubahUsulanJadiStandar(-1,id_std_isi,rasionale,isi,unit_period,lama_per_period,periode_awal,thsms1,target_unit1,thsms2,target_unit1,thsms3,target_unit1,thsms4,target_unit1,thsms5,target_unit1,thsms6,target_unit1,tkn_param,tkn_doc,versi,pihak,tkn_pengawas,norut,tkn_indikator,cakupan_std,tipe_proses_pengawasan);
				}
				else if(copy_as_prodi.equalsIgnoreCase("revisi")) {
					//copy up revisi
					int id_std_isi_baru = usm.copyStandard(id_std_isi, id_versi, "revisi");
				}
				else {
					//copy untuk prodi
					//System.out.println("masuk");
					int id_std_isi_baru = usm.copyStandard(id_std_isi, id_versi, copy_as_prodi);
				}
				SearchStandarMutu stm = new SearchStandarMutu();
				Boolean spmi_editor = (Boolean) session.getAttribute("spmi_editor");
				//Vector v_list = stm.getListStandarIsi(spmi_editor, isu.getIdObj());
				Vector v_list = stm.getListStandarIsi(spmi_editor, isu.getIdObj(),0,0);
				session.setAttribute("v", v_list);
			}
				
			//request.getRequestDispatcher("go.getListAllStd?atMenu=edit_isi&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&mode=list").forward(request,response);

			/*
			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/"+fwdto;
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url).forward(request,response);
			*/
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO Auto-generated method stub
		doGet(request, response);
	}

}

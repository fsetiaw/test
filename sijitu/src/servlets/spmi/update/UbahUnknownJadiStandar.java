package servlets.spmi.update;

import java.io.IOException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.naming.directory.SearchResult;
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
import beans.dbase.spmi.SearchSpmi;
import beans.dbase.spmi.SearchStandarMutu;
import beans.dbase.spmi.request.UpdateRequest;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Constant;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class UbahUnknownJadiStandar
 */
@WebServlet("/UbahUnknownJadiStandar")
public class UbahUnknownJadiStandar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UbahUnknownJadiStandar() {
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
		//kode here
			//System.out.println("masuk ubah usulan");
			String tombol = request.getParameter("tombol");
			if(tombol!=null && tombol.equalsIgnoreCase("dok_terkait")) {
				//System.out.println("submit="+tombol);
				String target = Constants.getRootWeb()+"/InnerFrame/Spmi/Dokument/mutu/form_tambah_dok.jsp";
    			String uri = request.getRequestURI();
    			String url = PathFinder.getPath(uri, target);
    			request.getRequestDispatcher(url+"?atMenu=form&backTo=mutu").forward(request,response);
				//System.out.println("submit=null");	
			}
			else if(tombol!=null && tombol.equalsIgnoreCase("pihak_terkait")) {
				String target = Constants.getRootWeb()+"/InnerFrame/sdm/jabatan/form_tambah_jabatan.jsp";
    			String uri = request.getRequestURI();
    			String url = PathFinder.getPath(uri, target);
    			request.getRequestDispatcher(url+"?atMenu=form&backTo=mutu").forward(request,response);
    			//System.out.println("submit="+tombol);
			}
			else {
		//PrintWriter out = response.getWriter();
				//System.out.println("submit="+tombol);
				Vector v_err = null;
				ListIterator<String> li=null;
				String atMenu = request.getParameter("atMenu");
				String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
				StringTokenizer st = null;
				if(kdpst_nmpst_kmp!=null && kdpst_nmpst_kmp.contains("-")) {
					st = new StringTokenizer(kdpst_nmpst_kmp,"-");
				}
				else {
					st = new StringTokenizer(kdpst_nmpst_kmp,"`");
				}
				String kdpst_menu = st.nextToken();
				String nmpst_menu = st.nextToken();
				String kdkmp_menu = st.nextToken();
				
				String versi = request.getParameter("versi");
				String id_std_isi = request.getParameter("id_std_isi");
				String id_master = request.getParameter("id_master");
				//System.out.println("id_master="+id_master);
				if(Checker.isStringNullOrEmpty(id_master)||id_master.equalsIgnoreCase("0")) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Rumpun Standar harus diisi");
				}
				
				String id_tipe = request.getParameter("id_tipe");
				//System.out.println("id_tipe="+id_tipe);
				if(Checker.isStringNullOrEmpty(id_tipe)||id_tipe.equalsIgnoreCase("0")) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Tipe Standar harus diisi");
				}
				
				String rasionale = ""+request.getParameter("rasionale");
				/*
				if(Checker.isStringNullOrEmpty(rasionale)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Rasionale/Alasan harus diisi");
				}
				*/
				String isi_std = ""+request.getParameter("isi_std");
				if(Checker.isStringNullOrEmpty(isi_std)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Isi Standar harus diisi");
				}
				
				String cakupan_std = ""+request.getParameter("cakupan_std");
				/*
				if(Checker.isStringNullOrEmpty(cakupan_std)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Cakupan Standar harus diisi");
					cakupan_std = "null";
				}
				
				
				String periode_unit_used = ""+request.getParameter("periode_unit_used");
				String lama_per_period = ""+request.getParameter("qtt_unit_per_period");
				if(Checker.isStringNullOrEmpty(lama_per_period)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Lama/periode harus diisi");
				}
				String target1 = ""+request.getParameter("target1");
				if(Checker.isStringNullOrEmpty(target1)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Target Periode I harus diisi");
				}
				String unit1 = ""+request.getParameter("unit1");
				
				String target2 = ""+request.getParameter("target2");
				if(Checker.isStringNullOrEmpty(target1)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Target Periode II harus diisi");
				}
				String unit2 = ""+request.getParameter("unit1");
				
				String target3 = ""+request.getParameter("target3");
				if(Checker.isStringNullOrEmpty(target1)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Target Periode III harus diisi");
				}
				String unit3 = ""+request.getParameter("unit1");
				
				String target4 = ""+request.getParameter("target4");
				if(Checker.isStringNullOrEmpty(target1)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Target Periode IV harus diisi");
				}
				String unit4 = ""+request.getParameter("unit1");
				
				String target5 = ""+request.getParameter("target5");
				if(Checker.isStringNullOrEmpty(target1)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Target Periode V harus diisi");
				}
				String unit5 = ""+request.getParameter("unit1");
				
				String target6 = ""+request.getParameter("target6");
				if(Checker.isStringNullOrEmpty(target1)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Target Periode VI harus diisi");
				}
				String unit6 = ""+request.getParameter("unit1");
				
				String periode_awal = ""+request.getParameter("periode_awal");
				if(Checker.isStringNullOrEmpty(periode_awal)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Periode aswal dimulai harus diisi");
				}
				String tkn_variable = ""+request.getParameter("tkn_variable");
				if(Checker.isStringNullOrEmpty(tkn_variable)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Parameter harus diisi");
				}
				
				String tkn_indikator = ""+request.getParameter("tkn_indikator");
				if(Checker.isStringNullOrEmpty(tkn_indikator)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Indikator variable harus diisi");
					tkn_indikator = "null"; //(TOKEN TERAKHIR HARUS ADA ISINYA)
				}
				
				String tipe_proses_pengawasan = request.getParameter("tipe_proses_pengawasan");
				if(Checker.isStringNullOrEmpty(tipe_proses_pengawasan)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Harap pilih tipe proses pengawasan");
				}
				
				String tkn_doc=null;
				String[]doc = request.getParameterValues("doc");
				if(doc!=null && doc.length>0) {
					for(int j=0;j<doc.length;j++) {
						if(tkn_doc==null) {
							tkn_doc = new String(doc[j]);
						}
						else {
							tkn_doc = tkn_doc+","+doc[j];
						}
					}
				}
				
				if(Checker.isStringNullOrEmpty(tkn_doc)) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Dokumen terkait harus diisi");
				}
				String[]job = request.getParameterValues("job");
				if(job==null || job.length<1) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Pihak terkait harus diisi");
				}
				String[]job_mon = request.getParameterValues("job_mon");
				if(job_mon==null || job_mon.length<1) {
					if(v_err==null) {
						v_err = new Vector();
						li = v_err.listIterator();
					}
					li.add("Pihak pengawas harus diisi");
				}
				
				String butir = "null";
				String kdpst = "null";//pertama kali selalu umum
				if(Checker.isStringNullOrEmpty(versi)) {
					versi = "1"; //default
				}
				
				
				String id_declare = "null";
				String id_do = "null";
				String id_eval = "null";
				String id_control = "null";
				String id_upgrade = "null";
				String tglsta = "null";
				String tglend = "null";
				String thsms1 = target1;
				String thsms2 = target2;
				String thsms3 = target3;
				String thsms4 = target4;
				String thsms5 = target5;
				String thsms6 = target6;
				String pihak ="null";
				if(job!=null && job.length>0) {
					for(int i=0;i<job.length;i++) {
						if(!Checker.isStringNullOrEmpty(job[i])) {
							if(pihak.equalsIgnoreCase("null")) {
								pihak = new String(job[i]);
							}
							else {
								pihak = pihak+","+job[i];
							}
						}
					}
				}
				String pihak_mon ="null";
				if(job_mon!=null && job_mon.length>0) {
					for(int i=0;i<job_mon.length;i++) {
						if(!Checker.isStringNullOrEmpty(job_mon[i])) {
							if(pihak_mon.equalsIgnoreCase("null")) {
								pihak_mon = new String(job_mon[i]);
							}
							else {
								pihak_mon = pihak_mon+","+job_mon[i];
							}
						}
					}
				}
				
				int id_std=0;
				if((!Checker.isStringNullOrEmpty(id_master) && !id_master.equalsIgnoreCase("0")) && (!Checker.isStringNullOrEmpty(id_tipe) && !id_tipe.equalsIgnoreCase("0"))) {
					//System.out.println("masuk sisni");
					SearchSpmi sr = new SearchSpmi();
					id_std=sr.getIdStd(Integer.parseInt(id_master),Integer.parseInt(id_tipe));
				}
				String norut = "0";//pertama kali
				String tmp = id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+versi+"`"+id_declare+"`"+id_do+"`"+id_eval+"`"+id_control+"`"+id_upgrade+"`"+tglsta+"`"+tglend+"`"+thsms1+"`"+thsms2+"`"+thsms3+"`"+thsms4+"`"+thsms5+"`"+thsms6+"`"+pihak+"`"+tkn_doc+"`"+tkn_indikator+"`"+norut+"`"+periode_awal+"`"+periode_unit_used+"`"+lama_per_period+"`"+unit1+"`"+unit2+"`"+unit3+"`"+unit4+"`"+unit5+"`"+unit6+"`"+tkn_variable+"`"+cakupan_std+"`"+tipe_proses_pengawasan;
	    		tmp = tmp.replace("``", "`null`");
	    		tmp = tmp.replace("``", "`null`");
	    		tmp = tmp.replace("``", "`null`");
	    		tmp = tmp.replace("``", "`null`");
	    		tmp = tmp.replace("``", "`null`");
	    		tmp = tmp.replace("``", "`null`");
	    		Vector v_target = new Vector();
	    		ListIterator lit = v_target.listIterator();
	    		lit.add(tmp);
	    		*/
				int max_data_per_pg=Constant.getMax_data_per_pg();
	    		if(v_err!=null && v_err.size()>0) {
	    			//System.out.println("ada error");
	    			//session.setAttribute("v_target", v_target);
	    			
	    			session.setAttribute("v_err", v_err);
	    			String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_creator/form/std_isi/edit_index_unknown.jsp";
	    			String uri = request.getRequestURI();
	    			String url = PathFinder.getPath(uri, target);
	    			//System.out.println("?mode=edit&id_master="+id_master+"&id_tipe="+id_tipe+"&atMenu="+atMenu+"&tkn_pengawas="+pihak_mon);;
	    			request.getRequestDispatcher(url+"?id_master_std=0&id=0&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&at_page=1&max_data_per_pg="+max_data_per_pg).forward(request,response);
	    		}
	    		else {
	    			//System.out.println("komplit "+id_std);
	    			SearchStandarMutu ssm = new SearchStandarMutu();
	    			//get versi terkini
	    			Vector v_info_tamplete_std = ssm.getLatestInfoTampleteStandar(Integer.parseInt(id_master), Integer.parseInt(id_tipe));
	    			String versi_std = null;
	    			if(v_info_tamplete_std!=null&&v_info_tamplete_std.size()>0) {
	    				li = v_info_tamplete_std.listIterator();
	    					//versi
	    								//tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendali+"~"+tkn_jab_lap+"~"+rasionale+"~"+pihak_terkait_capaian+"~"+definisi+"~"+referensi+"~"+tglsta+"~"+tglend+"~"+dok_terkait;
	    				String brs = (String)li.next();
	    				st = new StringTokenizer(brs,"~");
	    				versi_std = st.nextToken();
	    			}
	    			if(!Checker.isStringNullOrEmpty(versi_std)) {
	    				UpdateRequest ur = new UpdateRequest();
	    				int id_std = ssm.getIdStd(id_master, id_tipe);   
	    				ur.UbahUnknownJdStd(id_std, Integer.parseInt(versi_std), id_std_isi, isi_std, cakupan_std);
	    			}
	    			
	    			
	    			//int updated = ur.UbahUnknownJadiStandar(id_std,id_std_isi,rasionale,isi_std,periode_unit_used,lama_per_period,periode_awal,target1,unit1,target2,unit2,target3,unit3,target4,unit4,target5,unit5,target6,unit6,tkn_variable,tkn_doc,versi,pihak,pihak_mon,norut,tkn_indikator,cakupan_std,tipe_proses_pengawasan);
	    			//System.out.println("updated "+updated);
	    			//System.out.println("backTo -- "+backTo);
	    			//String target = Constants.getRootWeb()+"/InnerFrame/Spmi/standar_spmi/home_standar_unknown.jsp";
	        		//String uri = request.getRequestURI();
	        		//String url = PathFinder.getPath(uri, target);
	        		request.getRequestDispatcher("go.getListAllStd?mode=nuview&id_master_std=0&id=0&kdpst_nmpst_kmp="+kdpst_nmpst_kmp+"&at_page=1&max_data_per_pg="+max_data_per_pg).forward(request,response);	
	    				    				
	    			
	    			
	    		}	
			}	
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

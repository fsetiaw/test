package servlets.view;

import java.io.IOException;
import java.io.PrintWriter;

import beans.folder.FolderManagement;
import beans.folder.file.*;
//import beans.dbase.SearchDb;
import beans.dbase.mhs.SearchDbInfoMhs;
import beans.dbase.mhs.UpdateDbInfoMhs;
import beans.dbase.mhs.pindah_prodi.UpdateDbInfoMhsPp;
import beans.dbase.trnlp.UpdateDbTrnlp;
import beans.dbase.Beasiswa.SearchDbBeasiswa;
import beans.dbase.dosen.SearchDbDsn;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.sql.Connection;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.AddHocFunction;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class DataCivitas
 */
@WebServlet("/DataCivitas")
public class ProfileCivitas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;   
    DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
 //   public ProfileCivitas() {
 //       super();
        // TODO Auto-generated constructor stub
 //   }
	public void init(ServletConfig config) throws ServletException {
		
		super.init(config);
		/*
		Context initContext = null;
		Context envContext = null;
	    try {
	      // Look up the JNDI data source only once at init time
    		initContext  = new InitialContext();
    		envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		
    		envContext.close();
    		initContext.close();
    		if(ds==null) {
    			throw new ServletException("missing data source");
    		}		
	    }
	    catch (NamingException e) {
	      e.printStackTrace();
	    }
	    */
	  }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("masuk profile civits");
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String url_ff = "";
		//if(isu==null) {
		//	response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		//}
		//else {
		String thsms_reg = Checker.getThsmsHeregistrasi();
		String objId = request.getParameter("id_obj");
		String nmm = request.getParameter("nmm");
		String npm = request.getParameter("npm");
		String kdpst = request.getParameter("kdpst");
		String obj_lvl =  request.getParameter("obj_lvl");
		String cmd =  request.getParameter("cmd");
		String submit = request.getParameter("submit");
		String malaikat = request.getParameter("malaikat");
		if(malaikat==null || Checker.isStringNullOrEmpty(malaikat)) {
			malaikat = (String)session.getAttribute("status_malaikat");
		}
		//System.out.println("11profileCivitas "+objId+" "+npm+" "+nmm+" "+obj_lvl+" isu npm = "+isu.getNpm()+" cmd="+cmd);
		
		/*
		 * DI dashHomeMhs fungsi ini dipanggil lagi (buat make sure kalo??)
		 */
		String status_akhir_mahasiswa = AddHocFunction.getAndSyncStmhsBetweenTrlsmAndMsmhs(kdpst, npm);
		//System.out.println("status_akhir_mahasiswa22="+status_akhir_mahasiswa);
		session.setAttribute("status_akhir_mahasiswa", status_akhir_mahasiswa); //status mahasiswa bukan status user yg login
		session.setAttribute("status_malaikat", malaikat);
		//isu.isu
		
		
		
		boolean ada_akses_vop = false;
		boolean ada_akses_insDataDosen = false;
		boolean ada_akses_viewDataDosen = false;
		/*
		 * boleh liat data bila dia boleh liat profile atau fungsi khusus lainya spt:
		 * 1.insDataDosen // bukan view- kalo viewDataDosen harusnya scopenya sama dengan s
		 * 
		 * -default scope yg dipake adalah vop, bila usr memilik akses untuk keduanya
		 * penambahan tipe-command, harus sync dengan di fungsi
		 * isu.tryGetInfo(norut,Integer.valueOf(obj_lvl).intValue(),npm,"vop",kdpst); 
		 */
		if(isu.isUsrAllowTo("vop", npm, obj_lvl)) {
			ada_akses_vop = true;
		}
		if(isu.isUsrAllowTo("insDataDosen", npm, obj_lvl)) {
			ada_akses_insDataDosen = true;
		}
		if(isu.isUsrAllowTo("allowViewDataDosen", npm, obj_lvl)) {
			ada_akses_viewDataDosen = true;
		}
		//System.out.println("ada_akses_vop ="+ada_akses_vop);
		//System.out.println("ada_akses_insDataDosen ="+ada_akses_insDataDosen);
		/*
		 * 2baris dibawah ini updated ma proses diatas 		
		 */
		//if(norut>0) {
		//
		if(ada_akses_vop||ada_akses_insDataDosen||ada_akses_viewDataDosen) {
			int norut=0;
			Vector v = null;
			if(ada_akses_vop) {
				norut = isu.isAllowTo("vop");
				v = isu.tryGetInfo(norut,Integer.valueOf(obj_lvl).intValue(),npm,"vop",kdpst);
			}
			else if(ada_akses_insDataDosen){
				norut = isu.isAllowTo("insDataDosen");
				v = isu.tryGetInfo(norut,Integer.valueOf(obj_lvl).intValue(),npm,"insDataDosen",kdpst);
			}
			else if(ada_akses_viewDataDosen){
				norut = isu.isAllowTo("allowViewDataDosen");
				v = isu.tryGetInfo(norut,Integer.valueOf(obj_lvl).intValue(),npm,"allowViewDataDosen",kdpst);
			}
			FolderManagement folder = new FolderManagement(isu.getDbSchema(),kdpst,npm);
			folder.cekAndCreateFolderIfNotExist();
		//	Vector v = isu.tryGetInfo(norut,Integer.valueOf(obj_lvl).intValue(),npm,"vop",kdpst);
			//System.out.println("v--size="+v.size());
			if(v.size()==0) {
				//System.out.println("masuk no vop");
				String target = Constants.getRootWeb()+"/ErrorPage/NoDataFound.jsp";
				//System.out.println(target);
				String uri = request.getRequestURI();
				//System.out.println(uri);
				
				url_ff = PathFinder.getPath(uri, target);
				// -- request.getRequestDispatcher(url_ff).forward(request,response);
			}
			else {
				//System.out.println("masuk vop or ins dosen");
				//update ada di 2 tmp = ProfileCivitas.java && HistoryBakCivitas.java
				//if(submit!=null && submit.equalsIgnoreCase("merge")) {
				if(submit!=null && submit.equalsIgnoreCase("gabung")) {	
					//System.out.println("nerging");
					String mergeToNpm = ""+request.getParameter("mergeToNpm");
					if(mergeToNpm==null || Checker.isStringNullOrEmpty(mergeToNpm)) {
						//System.out.println("NO NPM VALUE TO MERGE TO");
					}
					else {
						UpdateDbInfoMhs udb = new UpdateDbInfoMhs(isu.getNpm());
						udb.mergeDataCivitas(npm, mergeToNpm);
					}
					url_ff="get.notifications";
				}
				else if(submit!=null && submit.equalsIgnoreCase("GANTI ANGKATAN")) {
					//System.out.println("nerging");
					String moveToSmawl = ""+request.getParameter("moveToSmawl");
					if(moveToSmawl==null || Checker.isStringNullOrEmpty(moveToSmawl)) {
						//System.out.println("NO NPM VALUE TO MERGE TO");
					}
					else {
						UpdateDbInfoMhs udb = new UpdateDbInfoMhs(isu.getNpm());
						//String nu
						udb.pindahAngkatan(npm, moveToSmawl);
					}
					url_ff="get.notifications";
				}
				else if(submit!=null && submit.equalsIgnoreCase("PINDAH PRODI")) {
					//System.out.println("nerging");
					boolean sukses = false;
					String moveToKdpst = ""+request.getParameter("moveToKdpst");
					//System.out.println("moveToKdpst="+moveToKdpst);
					if(moveToKdpst==null || Checker.isStringNullOrEmpty(moveToKdpst)) {
						//ignore target kdpst null
					}
					else {
						SearchDbInfoMhs sdm = new SearchDbInfoMhs(isu.getNpm());
						Vector v_civ_ext = sdm.getDataProfileCivitasAndExtMhs(npm);
						
						if(v_civ_ext!=null && v_civ_ext.size()>0) {
							ListIterator li = v_civ_ext.listIterator();
							String nmmhs=null,tplhr=null,tglhr=null,smawl=null;
							int i=0;
							String info_civitas_tabel = (String)li.next();
							StringTokenizer st = new StringTokenizer(info_civitas_tabel,"`");
							//System.out.println("info_civitas_tabel="+info_civitas_tabel);
							while(st.hasMoreTokens()) {
								i++;
								String tkn = st.nextToken();
								if(i==8) {
									nmmhs=new String(tkn);
								}
								else if(i==10) {
									tplhr=new String(tkn);
								}
								else if(i==11) {
									tglhr=new String(tkn);
								}
								else if(i==14) {
									smawl=new String(tkn);
								}	
							}
							
							if(smawl.equalsIgnoreCase(thsms_reg)) {
								//yang diproses hanya mhs malaikat baru diinput
								UpdateDbInfoMhsPp udp = new UpdateDbInfoMhsPp(isu.getNpm());
								sukses = udp.pindahProdiMhsBaruDaftar(v_civ_ext, moveToKdpst);	
							}
						}
					}
					url_ff="transit.jsp?sukses="+sukses;
				}
				else {
					//System.out.println("bener kesini");
					ListIterator li = v.listIterator();
					String v_obj_lvl=(String)li.next();
					request.setAttribute("v_obj_lvl", v_obj_lvl);
					String v_id_kotaku=(String)li.next();
					request.setAttribute("v_id_kotaku", v_id_kotaku);
					String v_id_obj=(String)li.next();
					request.setAttribute("v_id_obj", v_id_obj);
					String v_kdpti=(String)li.next();
					request.setAttribute("v_kdpti", v_kdpti);
					String v_kdjen=(String)li.next();
					request.setAttribute("v_kdjen", v_kdjen);
					String v_kdpst=(String)li.next();
					request.setAttribute("v_kdpst", v_kdpst);
					String v_npmhs=(String)li.next();
					request.setAttribute("v_npmhs", v_npmhs);
					String v_nimhs=(String)li.next();
					request.setAttribute("v_nimhs", v_nimhs);
					String v_nmmhs=(String)li.next();
					request.setAttribute("v_nmmhs", v_nmmhs);
					String v_shift=(String)li.next();
					request.setAttribute("v_shift", v_shift);
					String v_tplhr=(String)li.next();
					request.setAttribute("v_tplhr", v_tplhr);
					String v_tglhr=(String)li.next();
					request.setAttribute("v_tglhr", v_tglhr);
					String v_kdjek=(String)li.next();
					request.setAttribute("v_kdjek", v_kdjek);
					String v_tahun=(String)li.next();
					request.setAttribute("v_tahun", v_tahun);
					String v_smawl=(String)li.next();
					request.setAttribute("v_smawl", v_smawl);
					String v_btstu=(String)li.next();
					request.setAttribute("v_btstu", v_btstu);
					String v_assma=(String)li.next();
					request.setAttribute("v_assma", v_assma);
					String v_tgmsk=(String)li.next();
					request.setAttribute("v_tgmsk", v_tgmsk);
					String v_tglls=(String)li.next();
					request.setAttribute("v_tglls", v_tglls);
					String v_stmhs=(String)li.next();
					request.setAttribute("v_stmhs", v_stmhs);
					String v_stpid=(String)li.next();
					request.setAttribute("v_stpid", v_stpid);
					String v_sksdi=(String)li.next();
					request.setAttribute("v_sksdi", v_sksdi);
					String v_asnim=(String)li.next();
					request.setAttribute("v_asnim", v_asnim);
					String v_aspti=(String)li.next();
					request.setAttribute("v_aspti", v_aspti);
					String v_asjen=(String)li.next();
					request.setAttribute("v_asjen", v_asjen);
					String v_aspst=(String)li.next();
					request.setAttribute("v_aspst", v_aspst);
					String v_bistu=(String)li.next();
					request.setAttribute("v_bistu", v_bistu);
					String v_peksb=(String)li.next();
					request.setAttribute("v_peksb", v_peksb);
					String v_nmpek=(String)li.next();
					request.setAttribute("v_nmpek", v_nmpek);
					String v_ptpek=(String)li.next();
					request.setAttribute("v_ptpek", v_ptpek);
					String v_pspek=(String)li.next();
					request.setAttribute("v_pspek", v_pspek);
					String v_noprm=(String)li.next();
					request.setAttribute("v_noprm", v_noprm);
					String v_nokp1=(String)li.next();
					request.setAttribute("v_nokp1", v_nokp1);
					String v_nokp2=(String)li.next();
					request.setAttribute("v_nokp2", v_nokp2);
					String v_nokp3=(String)li.next();
					request.setAttribute("v_nokp3", v_nokp3);
					String v_nokp4=(String)li.next();
					request.setAttribute("v_nokp4", v_nokp4);
					String v_sttus=(String)li.next();
					request.setAttribute("v_sttus", v_sttus);
					String v_email=(String)li.next();
					request.setAttribute("v_email", v_email);
					String v_nohpe=(String)li.next();
					request.setAttribute("v_nohpe", v_nohpe);
					String v_almrm=(String)li.next();
					request.setAttribute("v_almrm", v_almrm);
					String v_kotrm=(String)li.next();
					request.setAttribute("v_kotrm", v_kotrm);
					String v_posrm=(String)li.next();
					request.setAttribute("v_posrm", v_posrm);
					String v_telrm=(String)li.next();
					request.setAttribute("v_telrm", v_telrm);
					String v_almkt=(String)li.next();
					request.setAttribute("v_almkt", v_almkt);
					String v_kotkt=(String)li.next();
					request.setAttribute("v_kotkt", v_kotkt);
					String v_poskt=(String)li.next();
					request.setAttribute("v_poskt", v_poskt);
					String v_telkt=(String)li.next();
					request.setAttribute("v_telkt", v_telkt);
					String v_jbtkt=(String)li.next();
					request.setAttribute("v_jbtkt", v_jbtkt);
					String v_bidkt=(String)li.next();
					request.setAttribute("v_bidkt", v_bidkt);
					String v_jenkt=(String)li.next();
					request.setAttribute("v_jenkt", v_jenkt);
					String v_nmmsp=(String)li.next();
					request.setAttribute("v_nmmsp", v_nmmsp);
					String v_almsp=(String)li.next();
					request.setAttribute("v_almsp", v_almsp);
					String v_possp=(String)li.next();
					request.setAttribute("v_possp", v_possp);
					String v_kotsp=(String)li.next();
					request.setAttribute("v_kotsp", v_kotsp);
					String v_negsp=(String)li.next();
					request.setAttribute("v_negsp", v_negsp);
					String v_telsp=(String)li.next();
					request.setAttribute("v_telsp", v_telsp);
					String v_neglh=(String)li.next();
					request.setAttribute("v_neglh", v_neglh);
					String v_agama=(String)li.next();
					request.setAttribute("v_agama", v_agama);
					
					request.setAttribute("v_profile", v);
					request.setAttribute("atr_name", "atr_val");
					
					//update sksdi jika mhs pindahan
					if(!Checker.isStringNullOrEmpty(v_stpid) && v_stpid.equalsIgnoreCase("P")) {
						UpdateDbTrnlp udt = new UpdateDbTrnlp();
						udt.hitungDanUpdateSksdi(v_npmhs);
					}
					
					//	tambahan 1 = get list kurikulum dan selected
					String listKurAndSelected = request.getParameter("listKurAndSelected");
					if(listKurAndSelected==null) {
						SearchDbInfoMhs sdb = new SearchDbInfoMhs(isu.getNpm());
						Vector vListAndSelectedKurikulum = sdb.gotListAndSelectedKurikulum(v_kdpst,v_npmhs,v_smawl);
						ListIterator litmp = vListAndSelectedKurikulum.listIterator();
						while(litmp.hasNext()) {
							listKurAndSelected = listKurAndSelected+litmp.next()+"__";
						}
						listKurAndSelected = listKurAndSelected.replace("null","");
					}	
					//System.out.println("listKurAndSelected="+listKurAndSelected);
					//request.setAttribute("vListAndSelectedKurikulum", vListAndSelectedKurikulum);
					String target="";
					if(cmd!=null && cmd.equalsIgnoreCase("edit")) {
						//	get list obj
						SearchDbInfoMhs sdb = new SearchDbInfoMhs(isu.getNpm());
						String listObj = sdb.getListTipeObj();
						String curPa = ""+sdb.getInfoPA(v_kdpst, v_npmhs);
						SearchDbDsn sdd = new SearchDbDsn(isu.getNpm());
						 
						//	//System.out.println("curPacurPa="+curPa);
						//String kdpst_dosen = "00011"; //kapan2 harus di centralizd
						//Vector v_list_dosen = sdd.getListKaprodi(Constants.getKdpstDosen());
						Vector v_list_dosen = sdd.getListDosenAktif();
						session.setAttribute("vListDsn", v_list_dosen);
						request.setAttribute("listTipeObj", listObj);
						request.setAttribute("curPa", curPa);
						target = Constants.getRootWeb()+"/InnerFrame/Edit/editProfile.jsp";
						String uri = request.getRequestURI();
						url_ff = PathFinder.getPath(uri, target);
						// --request.getRequestDispatcher(url_ff+"?targetPage=editProfile&listKurAndSelected="+listKurAndSelected).forward(request,response);
						url_ff = url_ff+"?targetPage=editProfile&listKurAndSelected="+listKurAndSelected;
					}
					else if(cmd!=null && cmd.equalsIgnoreCase("dashboard")) {
						//System.out.println("sampe dashboard");
						target = Constants.getRootWeb()+"/InnerFrame/dashHomeMhs.jsp";
						String uri = request.getRequestURI();
						url_ff = PathFinder.getPath(uri, target);
						
						//System.out.println("sampe dashboard1");
						// --request.getRequestDispatcher(url_ff+"?targetPage=dashHomePage").forward(request,response);
						url_ff = url_ff+"?targetPage=dashHomePage";
					
					
					}	
					else if(cmd!=null && (cmd.equalsIgnoreCase("viewProfile") || cmd.equalsIgnoreCase("updated"))) {
						SearchDbInfoMhs sdb = new SearchDbInfoMhs(isu.getNpm());
						String curPa = ""+sdb.getInfoPA(v_kdpst, v_npmhs);
						request.setAttribute("curPa", curPa);
						SearchDbBeasiswa sdbe = new SearchDbBeasiswa(isu.getNpm());
						Vector vHisBea = sdbe.getHistBeasiswa(kdpst, npm);
						session.setAttribute("vHisBea", vHisBea);
						target = Constants.getRootWeb()+"/InnerFrame/profile.jsp";
						String uri = request.getRequestURI();
						url_ff = PathFinder.getPath(uri, target);
						// --request.getRequestDispatcher(url_ff+"?atMenu=dataPribadi&targetPage=profile&listKurAndSelected="+listKurAndSelected).forward(request,response);
						url_ff = url_ff+"?atMenu=dataPribadi&targetPage=profile&listKurAndSelected="+listKurAndSelected;
					}
					/*
					else if(cmd!=null && cmd.equalsIgnoreCase("editProfileKeu")) {
						//	get list obj
						SearchDbInfoMhs sdb = new SearchDbInfoMhs(isu.getNpm());
						String listObj = sdb.getListTipeObj();
						String curPa = ""+sdb.getInfoPA(v_kdpst, v_npmhs);
						SearchDbDsn sdd = new SearchDbDsn(isu.getNpm());
						 
						//System.out.println("curPacurPa="+curPa);
						//String kdpst_dosen = "00011"; //kapan2 harus di centralizd
						//Vector v_list_dosen = sdd.getListKaprodi(Constants.getKdpstDosen());
						Vector v_list_dosen = sdd.getListDosenAktif();
						session.setAttribute("vListDsn", v_list_dosen);
						request.setAttribute("listTipeObj", listObj);
						request.setAttribute("curPa", curPa);
						target = Constants.getRootWeb()+"/InnerFrame/Edit/editProfileKeu.jsp";
						String uri = request.getRequestURI();
						String url_ff = PathFinder.getPath(uri, target);
						request.getRequestDispatcher(url_ff+"?targetPage=editProfile&listKurAndSelected="+listKurAndSelected).forward(request,response);
					}
					*/
					//String uri = request.getRequestURI();
					//String url_ff = PathFinder.getPath(uri, target);
				
					//request.getRequestDispatcher(url_ff).forward(request,response);
					//request.getRequestDispatcher("profile.jsp").forward(request,response);
				}
			}
		}
		else {
			String target = Constants.getRootWeb()+"/ErrorPage/authorization.jsp";
			String uri = request.getRequestURI();
			url_ff = PathFinder.getPath(uri, target);
			//--request.getRequestDispatcher(url_ff).forward(request,response);
		}
		//}
		//System.out.println("url_ff="+url_ff);
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
			request.getRequestDispatcher(url_ff).forward(request,response);
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}

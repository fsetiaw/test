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

import beans.dbase.UpdateDb;
import beans.dbase.mhs.SearchDbInfoMhs;
import beans.dbase.mhs.UpdateDbInfoMhs;
import beans.dbase.mhs.pindah_prodi.UpdateDbInfoMhsPp;
import beans.dbase.penelitian.UpdateDbRiwayatKaryaIlmiah;
import beans.dbase.topik.UpdateDbTopik;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.AddHocFunction;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class PengajuanApproval
 */
@WebServlet("/PengajuanApproval")
public class PengajuanApproval extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PengajuanApproval() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		//response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		//response.setHeader("Expires", "0"); // Proxies.
		//response.setContentType("text/html");
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		
		String thsms_pelaporan = (String) session.getAttribute("thsms_pelaporan");
		//if(isu==null) { 
		//	response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		//} 
		//else { 
		//kode here
		//System.out.println("cuti approval");
		//data target
		String id_obj = request.getParameter("id_obj");
		String nmm = request.getParameter("nmm");
		String npm = request.getParameter("npm");
		
		String obj_lvl = request.getParameter("obj_lvl");
		String kdpst= request.getParameter("kdpst");
		String scope = request.getParameter("scope");
		//System.out.println("scope="+scope);
		String cmd= request.getParameter("cmd");
		//System.out.println("cmd="+cmd);
		String msg= request.getParameter("msg");
		String full_table_rules_nm = request.getParameter("full_table_rules_nm");
		//System.out.println("full_table_rules_nm="+full_table_rules_nm);
		String nmm_folder = full_table_rules_nm.replace("_RULES", "").toLowerCase();
		//target_thsms="+Checker.getThsmsPengajuanStmhs()+"&cmd="+cmd+"&folder_pengajuan="+nmm_folder+"&scope="+scope+"&table="+full_table_rules_nm.toUpperCase()).forward(request,response);
		String trans_info = request.getParameter("trans_id");
		String approval = request.getParameter("approval");
		String alasan = request.getParameter("alasan");
		StringTokenizer st = new StringTokenizer(trans_info,"`");
		//System.out.println("approval="+approval);
		//System.out.println("full_table_rules_nm2="+full_table_rules_nm);
		//System.out.println(alasan);
		//System.out.println("npm-"+npm);
		String id=st.nextToken();
		String thsms_pengajuan=st.nextToken();
		String tipe_pengajuan=st.nextToken();
		String isi_topik_pengajuan=st.nextToken();
		//System.out.println("isi_topik_pengajuan--"+isi_topik_pengajuan);
		String tkn_target_objnickname=st.nextToken();
		String tkn_target_objid=st.nextToken();
		String target_npm=st.nextToken();
		String creator_obj_id=st.nextToken();
		String creator_npm=st.nextToken();
		String creator_nmm=st.nextToken();
		String shwow_at_target=st.nextToken();
		String show_at_creator=st.nextToken();
		String updtm=st.nextToken();
		String approved=st.nextToken();
		String locked=st.nextToken();
		String rejected=st.nextToken();
		String creator_kdpst=st.nextToken();
		String target_kdpst=st.nextToken();
		String batal=st.nextToken();
		String valid_if_null=null;
		
		//String npm_creator = request.getParameter("npm_creator");
		//String kdpst_asal = Checker.getKdpst(npm_creator);
		//System.out.println("creator_npm="+creator_npm);
		//System.out.println("thsms_pengajuan="+thsms_pengajuan);
		//System.out.println("id="+id);
		//System.out.println(locked);
		UpdateDbTopik udt = new UpdateDbTopik(isu.getNpm());
		UpdateDbTrlsm udtr = new UpdateDbTrlsm(isu.getNpm());
		//udt.approvalPengajuanCuti(id, isu.getIdObj(), isu.getNpm(), approval, alasan, full_table_nm);
		//System.out.println("APPRIVAL="+approval);
		if(approval!=null && approval.equalsIgnoreCase("UPDATE DATA "+Converter.npmAlias()+" BARU")) {
			//khusus pindah prodi - input no npm pada prodi baru
			
			String npm_pindahan = request.getParameter("npm_pindahan");
			String kdpst_pindahan = Checker.getKdpst(npm_pindahan);
			String kdpst_lama = Checker.getKdpst(creator_npm);
			valid_if_null = udt.cekNpmProdiBaru(npm_pindahan, kdpst, Converter.npmAlias());
			if(valid_if_null==null || Checker.isStringNullOrEmpty(valid_if_null)) {
				//System.out.println("siaop lanjut");
				//System.out.println("npm_creator="+creator_npm);
				//System.out.println("kdpst_lama="+kdpst_lama);
				String kode_univ_asal = Checker.getKdpti();
				//String kdpst_asal = Checker.getKdpst(npm_creator);
				//update approvl untuk pindah prodi
				//AddHocFunction.finalizePindahProdi(npm_creator, npm_pindahan, kdpst_asal, kode_univ_asal);
				udt.approvalPengajuan(id, isu.getIdObj(), isu.getNpm(), "proses_npm_pp", alasan, full_table_rules_nm);
				udtr.updStmhsPindahProdi(kdpst_lama, creator_npm, new String[]{thsms_pengajuan+"`K"}, new String[]{"Pindah Prodi karena, "+isi_topik_pengajuan}, npm_pindahan, kdpst_pindahan, kode_univ_asal);
				//finaloze process;
				
			}
			else {
				//data ngga valid - ignore
				//System.out.println("tidak valid");
			}
			
		}
		//else if(approval.equalsIgnoreCase("AUTO GENERATE DATA "+Converter.npmAlias()+" BARU")) {
		else if(approval!=null && approval.equalsIgnoreCase("KLIK UNTUK FINALISASI PROSES PEMINDAHAN")) {
			//khusus pindah prodi - input no npm pada prodi baru
			//1. ambil data civitas
			//System.out.println("kesinsi");
			//System.out.println("npm="+npm);
			//String thsms_pmb = Checker.getThsmsPmb();
			SearchDbInfoMhs sdm = new SearchDbInfoMhs(isu.getNpm());
			Vector v_civ_ext = sdm.getDataProfileCivitasAndExtMhs(creator_npm);
			
			if(v_civ_ext!=null && v_civ_ext.size()>0) {
				//System.out.println("lanjut");
				//System.out.println("thsms_pmb="+thsms_pmb);
				ListIterator li = v_civ_ext.listIterator();
				String nmmhs=null,tplhr=null,tglhr=null,smawl=null;
				int i=0;
				String info_civitas_tabel = (String)li.next();
				st = new StringTokenizer(info_civitas_tabel,"`");
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
				//System.out.println("nmmhs="+nmmhs);
				boolean pindah_prodi_at_smawl = false;
				
				if(smawl.equalsIgnoreCase(thsms_pengajuan)) {
					pindah_prodi_at_smawl=true;
				}
				//System.out.println("pindah_prodi_at_smawl="+pindah_prodi_at_smawl);
				//2. cek apa sudah ada 
				String kode_univ_asal = Checker.getKdpti();
				String npm_prodi_baru = sdm.getNpmMhsGiven(target_kdpst, nmmhs, tplhr, tglhr, smawl);
				//System.out.println("npm_prodi_baru="+npm_prodi_baru);
				if(Checker.isStringNullOrEmpty(npm_prodi_baru)) {
					//System.out.println("no npm baro");
					UpdateDbInfoMhsPp udp = new UpdateDbInfoMhsPp(isu.getNpm());
				//belum ada npm at prodi barunya
					if(pindah_prodi_at_smawl) {
						//update data lama dgn npm baru begitu juga degna folder pembayaran
						//UpdateDbInfoMhsPp udp = new UpdateDbInfoMhsPp(isu.getNpm());
						//System.out.println("pit 1");
						udp.pindahProdiMhsBaruDaftar(v_civ_ext, target_kdpst);	
						//System.out.println("pit 2");
						valid_if_null = null;
						udt.approvalPengajuan(id, isu.getIdObj(), isu.getNpm(), "pindah_at_smawl", alasan, full_table_rules_nm);
						//System.out.println("pit 3");pindahProdiMhsLama
					}
					else {
						//utk mhs lama yg pindah prodi 
						//System.out.println("jadi kesini dong");
						
						String kdpst_lama = Checker.getKdpst(creator_npm);
						
						npm_prodi_baru = udp.pindahProdiMhsLama(v_civ_ext, target_kdpst);
						//System.out.println("npm_prodi_baru0="+npm_prodi_baru);
						//System.out.println("npm_prodi_baru="+npm_prodi_baru);
						udt.approvalPengajuan(id, isu.getIdObj(), isu.getNpm(), "proses_npm_pp", alasan, full_table_rules_nm);
						udtr.updStmhsPindahProdi(kdpst_lama, creator_npm, new String[]{thsms_pengajuan+"`K"}, new String[]{"Pindah Prodi karena, "+isi_topik_pengajuan}, npm_prodi_baru, target_kdpst, kode_univ_asal);
						//@updStmhsPindahProdi transaksi keuangan dicopy ke npm baru
						udp.withdrawCashPadaAcountNpmLama(creator_npm, kdpst_lama);
						AddHocFunction.getAndSyncStmhsBetweenTrlsmAndMsmhs(kdpst_lama,creator_npm);	
					}
				}
				else {
					//HARUSNYA SUDAH TIDAK PAKAI INI LAGI
					//ASUMSI DATA SUDAH DIINPUT PADA PRODI BARU
					//String npm_pindahan = request.getParameter("npm_pindahan");
					String npm_pindahan = new String(npm_prodi_baru);
					String kdpst_pindahan = Checker.getKdpst(npm_pindahan);
					String kdpst_lama = Checker.getKdpst(creator_npm);
					valid_if_null = udt.cekNpmProdiBaru(npm_pindahan, kdpst, Converter.npmAlias());
					if(valid_if_null==null || Checker.isStringNullOrEmpty(valid_if_null)) {
						//System.out.println("siaop lanjut");
						//System.out.println("npm_creator="+creator_npm);
						//System.out.println("kdpst_lama="+kdpst_lama);
						//String kode_univ_asal = Checker.getKdpti();
						//String kdpst_asal = Checker.getKdpst(npm_creator);
						//update approvl untuk pindah prodi
						//AddHocFunction.finalizePindahProdi(npm_creator, npm_pindahan, kdpst_asal, kode_univ_asal);
						udt.approvalPengajuan(id, isu.getIdObj(), isu.getNpm(), "proses_npm_pp", alasan, full_table_rules_nm);
						udtr.updStmhsPindahProdi(kdpst_lama, creator_npm, new String[]{thsms_pengajuan+"`K"}, new String[]{"Pindah Prodi karena, "+isi_topik_pengajuan}, npm_pindahan, kdpst_pindahan, kode_univ_asal);
						//finaloze process;
						
					}
					else {
						//data ngga valid - ignore
						//System.out.println("tidak valid");
					}
				}
			}
		
			
			
			//valid_if_null = udt.cekNpmProdiBaru(npm_pindahan, kdpst, Converter.npmAlias());
			//if(valid_if_null==null || Checker.isStringNullOrEmpty(valid_if_null)) {
			//	String kode_univ_asal = Checker.getKdpti();
			//	udt.approvalPengajuan(id, isu.getIdObj(), isu.getNpm(), "proses_npm_pp", alasan, full_table_rules_nm);
			//	udtr.updStmhsPindahProdi(kdpst_lama, creator_npm, new String[]{thsms_pengajuan+"`K"}, new String[]{"Pindah Prodi karena, "+isi_topik_pengajuan}, npm_pindahan, kdpst_pindahan, kode_univ_asal);
			//}
			//else {
				//data ngga valid - ignore
				//System.out.println("tidak valid");
			//}
			
		}
		else {
			//approval pengajuan lainnya
			//System.out.println("pits1="+full_table_rules_nm+"-"+approval);
			udt.approvalPengajuan(id, isu.getIdObj(), isu.getNpm(), approval, alasan, full_table_rules_nm);
			//cek setelah approval diatas apakah semua sudah approved
			boolean all_approved = AddHocFunction.isAllApproved(Long.parseLong(id), tipe_pengajuan);
			//System.out.println("all_approved_finale="+all_approved);
			if(all_approved) {
				if(tipe_pengajuan.equalsIgnoreCase("PINDAH_PRODI")) {
					/*
					 * khusus pindah prodi - finalize process ada di 
					 * if(valid_if_null==null || Checker.isStringNullOrEmpty(valid_if_null)) { barket
					 */
					request.getRequestDispatcher("go.moPp?target_thsms="+thsms_pelaporan+"&cmd="+cmd+"&folder_pengajuan="+nmm_folder+"&scope="+scope+"&table="+full_table_rules_nm.toUpperCase()).forward(request,response);
				}
				else if(tipe_pengajuan.equalsIgnoreCase("CUTI")) {
					UpdateDbTrlsm udl = new UpdateDbTrlsm(isu.getNpm());
					//set status mhs jadi cuti
					String[]thsms_stmhs = {thsms_pengajuan+"`C"};
					String[]alasan_cuti = {isi_topik_pengajuan};
					//System.out.println("thsms_stmhs="+thsms_stmhs[0]);
					//System.out.println("alasan_cuti="+alasan_cuti[0]);
					//System.out.println("kdpst = "+kdpst);
					//System.out.println("npm = "+npm);
					udl.updStmhs(creator_kdpst, creator_npm, thsms_stmhs, alasan_cuti);	
					AddHocFunction.getAndSyncStmhsBetweenTrlsmAndMsmhs(creator_kdpst,creator_npm);
					
					request.getRequestDispatcher("go.moPp?target_thsms="+thsms_pelaporan+"&cmd="+cmd+"&folder_pengajuan="+nmm_folder+"&scope="+scope+"&table="+full_table_rules_nm.toUpperCase()).forward(request,response);
					//request.getRequestDispatcher("go.moPp?target_thsms="+Checker.getThsmsPengajuanStmhs()+"&folder_pengajuan=cuti&scope=cuti&table=CUTI_RULES").forward(request,response);
				}
				else if(tipe_pengajuan.equalsIgnoreCase("KELUAR")) {
					UpdateDbTrlsm udl = new UpdateDbTrlsm(isu.getNpm());
					//set status mhs jadi cuti
					String[]thsms_stmhs = {thsms_pengajuan+"`K"};
					String[]alasan_ = {isi_topik_pengajuan};
					//System.out.println("thsms_stmhs="+thsms_stmhs[0]);
					//System.out.println("alasan_cuti="+alasan_[0]);
					//System.out.println("kdpst = "+kdpst);
					//System.out.println("npm = "+npm);
					udl.updStmhs(creator_kdpst, creator_npm, thsms_stmhs, alasan_);	
					AddHocFunction.getAndSyncStmhsBetweenTrlsmAndMsmhs(creator_kdpst,creator_npm);	
					request.getRequestDispatcher("go.moPp?target_thsms="+thsms_pelaporan+"&cmd="+cmd+"&folder_pengajuan="+nmm_folder+"&scope="+scope+"&table="+full_table_rules_nm.toUpperCase()).forward(request,response);
					//request.getRequestDispatcher("go.moPp?target_thsms="+Checker.getThsmsPengajuanStmhs()+"&folder_pengajuan=keluar&scope=out&table=KELUAR_RULES").forward(request,response);
				}
				else if(tipe_pengajuan.equalsIgnoreCase("AKTIF_KEMBALI")) {
					//1. copy from  npm ori
					
					//System.out.println("betul kesini="+creator_npm);
					
					String kode_univ_asal = Checker.getKdpti();
					SearchDbInfoMhs sdb = new SearchDbInfoMhs();
					Vector v_SearchDbInfoMhs_getDataProfileCivitasAndExtMhs = sdb.getDataProfileCivitasAndExtMhs(creator_npm);
					UpdateDbInfoMhsPp udp = new UpdateDbInfoMhsPp(isu.getNpm());
					String npm_baru = udp.insertCivitasBaru(v_SearchDbInfoMhs_getDataProfileCivitasAndExtMhs, creator_kdpst, thsms_pengajuan);
					
					
					UpdateDbTrlsm udl = new UpdateDbTrlsm(isu.getNpm());
					String[]thsms_stmhs = {thsms_pengajuan+"`K"};
					String[]alasan_aktif = {isi_topik_pengajuan};
					udl.updStmhs(creator_kdpst, creator_npm, thsms_stmhs, alasan_aktif);	
					AddHocFunction.getAndSyncStmhsBetweenTrlsmAndMsmhs(creator_kdpst,creator_npm);
					String[]thsms_stmhs_nunpm = {thsms_pengajuan+"`A"};
					String[]alasan_aktif_nunpm = {isi_topik_pengajuan};
					udl.updStmhs(creator_kdpst, npm_baru, thsms_stmhs_nunpm, alasan_aktif_nunpm);	
					AddHocFunction.getAndSyncStmhsBetweenTrlsmAndMsmhs(creator_kdpst,npm_baru);
					//System.out.println("npm_baru="+npm_baru);
					/*
					if(v_info!=null && v_info.size()>0) {
						UpdateDb udb = new UpdateDb(isu.getNpm());
						//insert sebagai mhs baru
						String nu_npm = udb.insertCivitasAktifKembali(v_info,thsms_pengajuan);
						String[]thsms_stmhs = {thsms_pengajuan+"`K"};
						String[]alasan_ = {"AKTIF DENGAN NPM BARU "+nu_npm};
						UpdateDbTrlsm udl = new UpdateDbTrlsm(isu.getNpm());
						//udl.updStmhs(creator_kdpst, creator_npm, thsms_stmhs, alasan_);	
						//udtr.updStmhsPindahProdi(kdpst_lama, creator_npm, new String[]{thsms_pengajuan+"`K"}, new String[]{"Pindah Prodi karena, "+isi_topik_pengajuan}, npm_pindahan, kdpst_pindahan, kode_univ_asal);
						udl.updStmhsPindahProdi(creator_kdpst, creator_npm, thsms_stmhs, alasan_, nu_npm, creator_kdpst, kode_univ_asal);
						AddHocFunction.getAndSyncStmhsBetweenTrlsmAndMsmhs(creator_kdpst,creator_npm);	
						//pengauan daftar ulang utk mhs baru
						UpdateDbInfoMhs udbm = new UpdateDbInfoMhs(isu.getNpm());
						udbm.updateDaftarUlangTableByOperator_v1(kdpst, nu_npm, thsms_pengajuan);
						AddHocFunction.getAndSyncStmhsBetweenTrlsmAndMsmhs(creator_kdpst,creator_npm);	
						//System.out.print("nu_npm="+nu_npm);
					}
					*/
					
					/*
					UpdateDbTrlsm udl = new UpdateDbTrlsm(isu.getNpm());
					String[]thsms_stmhs = {thsms_pengajuan+"`K"};
					String[]alasan_ = {isi_topik_pengajuan};
					udl.updStmhs(creator_kdusgLoco12Apr2017-2.sqlpst, creator_npm, thsms_stmhs, alasan_);	
					AddHocFunction.getAndSyncStmhsBetweenTrlsmAndMsmhs(creator_kdpst,creator_npm);
					*/	
				}
				else if(tipe_pengajuan.equalsIgnoreCase("KELULUSAN")) {
					//System.out.println("masuk sini");
					UpdateDbTrlsm udl = new UpdateDbTrlsm(isu.getNpm());
					//set status mhs jadi cuti
					String[]thsms_stmhs = {thsms_pengajuan+"`L"};
					String[]tglls = {isi_topik_pengajuan};
					//udl.updStmhs(creator_kdpst, creator_npm, thsms_stmhs, tglls);	
					udl.updStmhsLulusan(creator_kdpst, creator_npm, thsms_stmhs, tglls);
					request.getRequestDispatcher("go.moPp?target_thsms="+thsms_pelaporan+"&cmd="+cmd+"&folder_pengajuan="+nmm_folder+"&scope="+scope+"&table="+full_table_rules_nm.toUpperCase()).forward(request,response);
					//request.getRequestDispatcher("go.moPp?target_thsms="+Checker.getThsmsPengajuanStmhs()+"&folder_pengajuan=kelulusan&scope=lulus&table=KELULUSAN_RULES").forward(request,response);
					
				}
				else if(tipe_pengajuan.equalsIgnoreCase("UJIAN_AKHIR")) {
					//System.out.println("masuk ");
					UpdateDbRiwayatKaryaIlmiah ua = new UpdateDbRiwayatKaryaIlmiah();
		        	int updated = ua.prosesPengajuanUjianAkhirMhs(thsms_pengajuan, creator_npm, creator_kdpst, isi_topik_pengajuan);
		        	request.getRequestDispatcher("go.moPp?target_thsms="+thsms_pelaporan+"&cmd="+cmd+"&folder_pengajuan="+nmm_folder+"&scope="+scope+"&table="+full_table_rules_nm.toUpperCase()).forward(request,response);
					//System.out.println("masuk sini");
					//UpdateDbTrlsm udl = new UpdateDbTrlsm(isu.getNpm());
					//set status mhs jadi cuti
					//String[]thsms_stmhs = {thsms_pengajuan+"`L"};
					//String[]tglls = {isi_topik_pengajuan};
					//UpdateDbRiwayatKaryaIlmiah udil = new UpdateDbRiwayatKaryaIlmiah();
					//udil.prosesPengajuanUjianAkhirMhs(String thsms_pengajuan,String npmhs,String kdpst,String info_karya_ilmiah);
					//udl.updStmhs(creator_kdpst, creator_npm, thsms_stmhs, tglls);	
					//request.getRequestDispatcher("go.moPp?target_thsms="+Checker.getThsmsPengajuanStmhs()+"&cmd="+cmd+"&folder_pengajuan="+nmm_folder+"&scope="+scope+"&table="+full_table_rules_nm.toUpperCase()).forward(request,response);
					//request.getRequestDispatcher("go.moPp?target_thsms="+Checker.getThsmsPengajuanStmhs()+"&folder_pengajuan=kelulusan&scope=lulus&table=KELULUSAN_RULES").forward(request,response);
					
				}
			}
			else {
				//belum approved semua
				//System.out.println("all_approved=sampe sini");
				//System.out.println("all_approved="+all_approved);
				//if(tipe_pengajuan.equalsIgnoreCase("KELULUSAN")) {
				if(tipe_pengajuan.equalsIgnoreCase("UJIAN_AKHIR")) {
					request.getRequestDispatcher("go.moPp?target_thsms="+thsms_pelaporan+"&cmd="+cmd+"&folder_pengajuan="+nmm_folder+"&scope="+scope+"&table="+full_table_rules_nm.toUpperCase()).forward(request,response);
						
				}
				else if(tipe_pengajuan.equalsIgnoreCase("PINDAH_PRODI")) {
					//else if(tipe_pengajuan.equalsIgnoreCase("CUTI")) {
						request.getRequestDispatcher("go.moPp?target_thsms="+thsms_pelaporan+"&folder_pengajuan="+tipe_pengajuan.toLowerCase()+"&scope="+tipe_pengajuan.toLowerCase()+"&table="+tipe_pengajuan.toUpperCase()+"_RULES").forward(request,response);
				} 
				else {
				//else if(tipe_pengajuan.equalsIgnoreCase("CUTI")) {
					request.getRequestDispatcher("go.moPp?target_thsms="+Checker.getThsmsPengajuanStmhs()+"&folder_pengajuan="+tipe_pengajuan.toLowerCase()+"&scope="+tipe_pengajuan.toLowerCase()+"&table="+tipe_pengajuan.toUpperCase()+"_RULES").forward(request,response);
				} 
			}
		}
		
		//System.out.println("all_approved=sampe sini");
		
		
		
		
		//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp"; 
		//String uri = request.getRequestURI(); 
		//String url = PathFinder.getPath(uri, target);
		//go.moPp?target_thsms=<%=Checker.getThsmsHeregistrasi()%>&folder_pengajuan=pindah_prodi&scope=reqPindahJurusan&table=PINDAH_PRODI_RULES
		//request.getRequestDispatcher("go.moPp?id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm +"&obj_lvl="+obj_lvl +"&kdpst="+kdpst+"&cmd=cuti&msg=upd").forward(request,response);
		
		
		//if(tipe_pengajuan.equalsIgnoreCase("KELULUSAN")) {
		//	request.getRequestDispatcher("go.moPp?npm_pp_valid="+valid_if_null+"&target_thsms="+Checker.getThsmsPengajuanStmhs()+"&folder_pengajuan="+tipe_pengajuan.toLowerCase()+"&scope="+scope+"&table="+full_table_rules_nm).forward(request,response);
		//}
		//else {
		//request.getRequestDispatcher("go.moPp?npm_pp_valid="+valid_if_null+"&target_thsms="+thsms_pengajuan+"&folder_pengajuan="+tipe_pengajuan.toLowerCase()+"&scope="+scope+"&table="+full_table_rules_nm).forward(request,response);
			//request.getRequestDispatcher("go.moPp?npm_pp_valid="+valid_if_null+"&target_thsms="+Checker.getThsmsHeregistrasi()+"&folder_pengajuan="+tipe_pengajuan.toLowerCase()+"&scope="+scope+"&table="+full_table_rules_nm).forward(request,response);
		//}
		//request.getRequestDispatcher(url).forward(request,response);	
		//if(isu==null) {
		//	response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		//}
		//else {
			//request.getRequestDispatcher("go.moPp?npm_pp_valid="+valid_if_null+"&target_thsms="+thsms_pengajuan+"&folder_pengajuan="+tipe_pengajuan.toLowerCase()+"&scope="+scope+"&table="+full_table_rules_nm).forward(request,response);
		//}	
			 

		//}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

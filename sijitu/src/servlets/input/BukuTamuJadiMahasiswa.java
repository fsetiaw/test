package servlets.input;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.UpdateDb;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;
import beans.tools.Tool;

/**
 * Servlet implementation class BukuTamu
 */
@WebServlet("/BukuTamuJadiMahasiswa")
public class BukuTamuJadiMahasiswa extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BukuTamuJadiMahasiswa() {
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

		//kode here
		//System.out.println("bukutamu");
		String err_msg = "";
		String npm_sbg_mhs=null;
		//boolean all_valid = false;
		
		boolean valid_pt1 = true;
		boolean valid_pt2 = true;
		boolean all_valid = false;
		boolean sukses = false;
		int i = 0;
		String angkatan = request.getParameter("angkatan");
		String npm_sbg_tamu = request.getParameter("npm");
		String gelombang = request.getParameter("gelombang");
		String prodi = request.getParameter("prodi");
		String idobj = null;
		String kdpst = null;
		if(prodi==null || Checker.isStringNullOrEmpty(prodi)) {
			i++;
			valid_pt1 = false;
			err_msg = err_msg+"("+i+") Tujuan Prodi wajib diisi  ";
		}
		else {
			StringTokenizer st = new StringTokenizer(prodi,"-");
			idobj = st.nextToken();
			kdpst = st.nextToken();
		}
		String stpid = request.getParameter("stpid");
		if(stpid==null || Checker.isStringNullOrEmpty(stpid)) {
			i++;
			valid_pt1 = false;
			err_msg = err_msg+"("+i+") Tipe Mahasiswa wajib diisi  ";
		}
		String shift_kls = request.getParameter("shift_kls");
		if(shift_kls==null || Checker.isStringNullOrEmpty(shift_kls)) {
			i++;
			valid_pt1 = false;
			err_msg = err_msg+"("+i+") Shift perkuliahan wajib diisi  ";
		}
		String bistu = request.getParameter("bistu"); //tidak mungkin null
		String id_paket = null;
		String nm_paket = null;
		if(bistu==null && !Checker.isStringNullOrEmpty(bistu)) {
			StringTokenizer st = new StringTokenizer(bistu,"-");
			id_paket = st.nextToken();
			nm_paket = st.nextToken();	
		}
		
		if(nm_paket==null || nm_paket.equalsIgnoreCase("NON BEASISWA")) {
			nm_paket = "BIAYA MANDIRI";
		}
		String nama = request.getParameter("nama");
		if(nama!=null && Checker.isStringNullOrEmpty(nama)) {
			i++;
			valid_pt1 = false;
			err_msg = err_msg+"("+i+") Nama wajib diisi  ";
		}
		String email = request.getParameter("email");
		if(email==null || Checker.isStringNullOrEmpty(email)) {
			i++;
			valid_pt1 = false;
			err_msg = err_msg+"("+i+") Alamat email wajib diisi  ";
		}
		String hape = request.getParameter("hape");
		if(hape==null || Checker.isStringNullOrEmpty(hape)) {
			i++;
			valid_pt1 = false;
			err_msg = err_msg+"("+i+") No handphone wajib diisi  ";
		}
		String gender = request.getParameter("gender");
		String negara = request.getParameter("negara");
		String paspor = null;
		//System.out.println("negara serv="+negara);
		if(negara==null || Checker.isStringNullOrEmpty(negara)) {
			i++;
			valid_pt1 = false;
			err_msg = err_msg+"("+i+") Kewarganegaraan wajib diisi  ";
		}
		String nik = request.getParameter("nik");
		//if(nik!=null && Checker.isStringNullOrEmpty(nik)) {
		//	if(negara==null || Checker.isStringNullOrEmpty(negara) || negara.equalsIgnoreCase("indonesia")) {
		//		i++;
		//		valid_pt1 = false;
		//		err_msg = err_msg+"("+i+") NIK wajib diisi  ";	
	//		}
			
		//}
		if(negara!=null && negara.equalsIgnoreCase("indonesia")) {
			if(nik!=null && Checker.isStringNullOrEmpty(nik)) {
				i++;
				valid_pt1 = false;
				err_msg = err_msg+"("+i+") NIK wajib diisi  ";
			}
		}
		//paspor
		if(negara!=null && !negara.equalsIgnoreCase("indonesia")) {
			if(paspor==null) {
				paspor = request.getParameter("paspor");
				if(paspor==null || Checker.isStringNullOrEmpty(paspor)) {
					i++;
					valid_pt1 = false;
					err_msg = err_msg+"("+i+") No Paspor wajib diisi  ";	
				}
			}
		}
		String nisn = request.getParameter("nisn");
		
		String agama = request.getParameter("agama");
		String nglhr = request.getParameter("nglhr");
		String tplhr = request.getParameter("tplhr");
		if(tplhr!=null && Checker.isStringNullOrEmpty(tplhr)) {
			i++;
			valid_pt1 = false;
			err_msg = err_msg+"("+i+") Tempat lahir wajib diisi  ";
		}
		String tglhr = request.getParameter("tglhr");
		if(tglhr!=null && Checker.isStringNullOrEmpty(tglhr)) {
			i++;
			valid_pt1 = false;
			err_msg = err_msg+"("+i+") Tanggal lahir wajib diisi  ";
		}
		//System.out.println("validkah0="+valid_pt1);
		String ayah = request.getParameter("ayah");
		String ibu = request.getParameter("ibu");
		if(ibu!=null && Checker.isStringNullOrEmpty(ibu)) {
			i++;
			valid_pt1 = false;
			err_msg = err_msg+"("+i+") Nama ibu wajib diisi  ";
		}
		String propinsi = request.getParameter("propinsi");
		String kota = request.getParameter("kota");
		String kecamatan = request.getParameter("kecamatan");
		if(kecamatan!=null && !Checker.isStringNullOrEmpty(kecamatan)) {
			//Kec. Tanah Abang               [016001]
			kecamatan = kecamatan.replace("[", "`").replace("]", "");
			kecamatan = Tool.buatSatuSpasiAntarKata(kecamatan);
//			StringTokenizer st = new StringTokenizer(kecamatan,"`");
//			kecamatan = kecamatan+"`"+Getter.getIdWilKecamatan(kecamatan);
		}
		String kelurahan = request.getParameter("kelurahan");
		String dusun = request.getParameter("dusun");
		String alamat = request.getParameter("alamat");
		String rt = request.getParameter("rt");
		String rw = request.getParameter("rw");
		String pos = request.getParameter("pos");
		//System.out.println("valid_pt1="+valid_pt1);
		if(valid_pt1) {
			//String kecamatan = request.getParameter("kecamatan");
			if(propinsi!=null && !Checker.isStringNullOrEmpty(propinsi)) {
				//String kecamatan = request.getParameter("kecamatan");
				//cek nilainya valid apa ngga
				if(kota!=null && Checker.isStringNullOrEmpty(kota)) {
					i++;
					valid_pt2 = false;
					err_msg = err_msg+"("+i+") Kota wajib diisi  ";
				}
	//		}
	//	}
				if(valid_pt2 && kota!=null && !Checker.isStringNullOrEmpty(kota)) {
					if(kecamatan!=null && Checker.isStringNullOrEmpty(kecamatan)) {
						i++;
						valid_pt2 = false;
						err_msg = err_msg+"("+i+") Kecamatan wajib diisi  ";
					}
	//	}	
			//}
					if(valid_pt2 && kecamatan!=null && !Checker.isStringNullOrEmpty(kecamatan)) {
						if(kelurahan!=null && Checker.isStringNullOrEmpty(kelurahan)) {
							i++;
							valid_pt2 = false;
							err_msg = err_msg+"("+i+") Kelurahan wajib diisi  ";
						}
//		}	
				//String alamat = ""+request.getParameter("alamat");
						//if(valid_pt2 && kelurahan!=null && !Checker.isStringNullOrEmpty(kelurahan)) {
						if((alamat!=null && Checker.isStringNullOrEmpty(alamat))) {
							i++;
							valid_pt2 = false;
							err_msg = err_msg+"("+i+") Alamat wajib diisi  ";
						}
						if(valid_pt2 && kelurahan!=null && !Checker.isStringNullOrEmpty(kelurahan)) {
							all_valid = true;
						}
						//}
					}
				}
			}
		}	
			//if(valid) {
			//	all_valid = true;
			//}
//		}
		String url = "";
		//System.out.println("valid_pt2="+valid_pt2);
		//System.out.println("all_valid="+all_valid);
		if(all_valid) {
		//if(false) {
			UpdateDb udb = new UpdateDb(isu.getNpm());
			//complete varible
			if(Checker.isStringNullOrEmpty(angkatan)) {
				angkatan = Checker.getThsmsPmb();
			}
			//String thsms_pmb = Checker.getThsmsPmb();
    		String NIMHSMSMHS = "null";
    		String NMMHSMSMHS = new String(nama.toUpperCase());
    		NMMHSMSMHS = Tool.buatSatuSpasiAntarKata(NMMHSMSMHS);
    		String SHIFTMSMHS = shift_kls;
    		String TPLHRMSMHS = new String(tplhr);
    		String TGLHRMSMHS = new String(tglhr);
    		String KDJEKMSMHS = new String(gender);
    		String TAHUNMSMHS = angkatan.substring(0,4);
    		String SMAWLMSMHS = angkatan;
    		String BTSTUMSMHS = "null";
    		String ASSMAMSMHS = "null";
    		String TGMSKMSMHS = "null";
    		String TGLLSMSMHS = "null";
    		String STMHSMSMHS = "null";
    		String STPIDMSMHS = stpid;
    		String SKSDIMSMHS = "null";
    		String ASNIMMSMHS = "null";
    		String ASPTIMSMHS = "null";
    		String ASJENMSMHS = "null";
    		String ASPSTMSMHS = "null";
    		String BISTUMSMHS = nm_paket;
    		String PEKSBMSMHS = "null";
    		String NMPEKMSMHS = "null";
    		String PTPEKMSMHS = "null";
    		String PSPEKMSMHS = "null";
    		String NOPRMMSMHS = "null";
    		String NOKP1MSMHS = "null";
    		String NOKP2MSMHS = "null";
    		String NOKP3MSMHS = "null";
    		String NOKP4MSMHS = "null";
    		String GELOMMSMHS = "null";
    		if(gelombang!=null && !Checker.isStringNullOrEmpty(gelombang)) {
    			GELOMMSMHS = gelombang;
    		}
    		
    		String NAMA_AYAH = "null";
    		if(ayah!=null && !Checker.isStringNullOrEmpty(ayah)) {
    			NAMA_AYAH = ayah;
    		}
    		String TGLHR_AYAH = "null";
    		String TPLHR_AYAH = "null";
    		String LULUSAN_AYAH = "null";
    		String NOHAPE_AYAH = "null";
    		String KERJA_AYAH = "null";
    		String GAJI_AYAH = "null";
    		String NIK_AYAH = "null";
    		String KANDUNG_AYAH = "null";
    		String NAMA_IBU = "null";
    		if(ibu!=null && !Checker.isStringNullOrEmpty(ibu)) {
    			NAMA_IBU = ibu;
    		}
    		String TGLHR_IBU = "null";
    		String TPLHR_IBU = "null";
    		String LULUSAN_IBU = "null";
    		String NOHAPE_IBU = "null";
    		String KERJA_IBU = "null";
    		String GAJI_IBU = "null";
    		String NIK_IBU = "null";
    		String KANDUNG_IBU = "null";
    		String NAMA_WALI = "null";
    		String TGLHR_WALI = "null";
    		String TPLHR_WALI = "null";
    		String LULUSAN_WALI = "null";
    		String NOHAPE_WALI = "null";
    		String KERJA_WALI = "null";
    		String GAJI_WALI = "null";
    		String NIK_WALI = "null";
    		String HUBUNGAN_WALI = "null";
    		String NAMA_DARURAT1 = "null";
    		String NOHAPE_DARURAT1 = "null";
    		String HUBUNGAN_DARURAT1 = "null";
    		String NAMA_DARURAT2 = "null";
    		String NOHAPE_DARURAT2 = "null";
    		String HUBUNGAN_DARURAT2 = "null";
    		String NISNMSMHS = "null";
    		if(nisn!=null && !Checker.isStringNullOrEmpty(nisn)) {
    			NISNMSMHS = nisn;
    		}
    		String NIKTPMSMHS = "null";
    		if(nik!=null && !Checker.isStringNullOrEmpty(nik)) {
    			NIKTPMSMHS = nik;
    		}
    		String NOSIMMSMHS = "null";
    		String PASPORMSMHS = "null";
    		if(paspor!=null && !Checker.isStringNullOrEmpty(paspor)) {
    			PASPORMSMHS = paspor;
    		}
    		String KEWARGANEGARAAN = new String(negara.toUpperCase());
    		KEWARGANEGARAAN = Tool.buatSatuSpasiAntarKata(KEWARGANEGARAAN);
    		

    		
    		
    		
    		
    		//System.out.println("KEWARGANEGARAAN1="+KEWARGANEGARAAN);
    		String tkn_info_civitas_tanpa_kdpti_kdpst_kdjen_npmhs = NIMHSMSMHS+"$"+NMMHSMSMHS+"$"+SHIFTMSMHS+"$"+TPLHRMSMHS+"$"+TGLHRMSMHS+"$"+KDJEKMSMHS+"$"+TAHUNMSMHS+"$"+SMAWLMSMHS+"$"+BTSTUMSMHS+"$"+ASSMAMSMHS+"$"+TGMSKMSMHS+"$"+TGLLSMSMHS+"$"+STMHSMSMHS+"$"+STPIDMSMHS+"$"+SKSDIMSMHS+"$"+ASNIMMSMHS+"$"+ASPTIMSMHS+"$"+ASJENMSMHS+"$"+ASPSTMSMHS+"$"+BISTUMSMHS+"$"+PEKSBMSMHS+"$"+NMPEKMSMHS+"$"+PTPEKMSMHS+"$"+PSPEKMSMHS+"$"+NOPRMMSMHS+"$"+NOKP1MSMHS+"$"+NOKP2MSMHS+"$"+NOKP3MSMHS+"$"+NOKP4MSMHS+"$"+GELOMMSMHS+"$"+NAMA_AYAH+"$"+TGLHR_AYAH+"$"+TPLHR_AYAH+"$"+LULUSAN_AYAH+"$"+NOHAPE_AYAH+"$"+KERJA_AYAH+"$"+GAJI_AYAH+"$"+NIK_AYAH+"$"+KANDUNG_AYAH+"$"+NAMA_IBU+"$"+TGLHR_IBU+"$"+TPLHR_IBU+"$"+LULUSAN_IBU+"$"+NOHAPE_IBU+"$"+KERJA_IBU+"$"+GAJI_IBU+"$"+NIK_IBU+"$"+KANDUNG_IBU+"$"+NAMA_WALI+"$"+TGLHR_WALI+"$"+TPLHR_WALI+"$"+LULUSAN_WALI+"$"+NOHAPE_WALI+"$"+KERJA_WALI+"$"+GAJI_WALI+"$"+NIK_WALI+"$"+HUBUNGAN_WALI+"$"+NAMA_DARURAT1+"$"+NOHAPE_DARURAT1+"$"+HUBUNGAN_DARURAT1+"$"+NAMA_DARURAT2+"$"+NOHAPE_DARURAT2+"$"+HUBUNGAN_DARURAT2+"$"+NISNMSMHS+"$"+KEWARGANEGARAAN+"$"+NIKTPMSMHS+"$"+NOSIMMSMHS+"$"+PASPORMSMHS;
    		
    		//variable tkn_info_ext_civitas_tanpa_kdpst_npmhs
    		String STTUSMSMHS = "null";
    		String EMAILMSMHS = "null";
    		if(email!=null && !Checker.isStringNullOrEmpty(email)) {
    			EMAILMSMHS = email;
    		}
    		String NOHPEMSMHS = "null";
    		if(hape!=null && !Checker.isStringNullOrEmpty(hape)) {
    			NOHPEMSMHS = hape;
    		}
    		String ALMRMMSMHS = "null";
    		if(alamat!=null && !Checker.isStringNullOrEmpty(alamat)) {
    			ALMRMMSMHS = alamat;
    		}
    		String NRTRMMSMHS = "null";
    		if(rt!=null && !Checker.isStringNullOrEmpty(rt)) {
    			NRTRMMSMHS = rt;
    		}
    		String NRWRMMSMHS = "null";
    		if(rw!=null && !Checker.isStringNullOrEmpty(rw)) {
    			NRWRMMSMHS = rw;
    		}
    		String PROVRMMSMHS = "null";
    		String PROVRMIDWIL = "null";
    		if(propinsi!=null && !Checker.isStringNullOrEmpty(propinsi)) {
    			StringTokenizer st = new StringTokenizer(propinsi,"`");
    			PROVRMMSMHS = st.nextToken();
    			PROVRMMSMHS = Tool.buatSatuSpasiAntarKata(PROVRMMSMHS);
    			PROVRMIDWIL = st.nextToken().trim();
    		}
    		
    		String KOTRMMSMHS = "null";
    		String KOTRMIDWIL = "null";
    		if(kota!=null && !Checker.isStringNullOrEmpty(kota)) {
    			StringTokenizer st = new StringTokenizer(kota,"`");
    			KOTRMMSMHS = st.nextToken();
    			KOTRMMSMHS = Tool.buatSatuSpasiAntarKata(KOTRMMSMHS);
    			KOTRMIDWIL = st.nextToken().trim();
    		}
    		
    		String KECRMMSMHS = "null";
    		String KECRMIDWIL = "null";
    		if(kecamatan!=null && !Checker.isStringNullOrEmpty(kecamatan)) {
    			StringTokenizer st = new StringTokenizer(kecamatan,"`");
    			KECRMMSMHS = st.nextToken();
    			KECRMMSMHS = Tool.buatSatuSpasiAntarKata(KECRMMSMHS);
    			KECRMIDWIL = st.nextToken().trim();
    		}
    		String KELRMMSMHS = "null";
    		if(kelurahan!=null && !Checker.isStringNullOrEmpty(kelurahan)) {
    			KELRMMSMHS = kelurahan;
    			KELRMMSMHS = Tool.buatSatuSpasiAntarKata(KELRMMSMHS);
    		}
    		String DUSUNMSMHS = "null";
    		if(dusun!=null && !Checker.isStringNullOrEmpty(dusun)) {
    			DUSUNMSMHS = dusun;
    			DUSUNMSMHS = Tool.buatSatuSpasiAntarKata(DUSUNMSMHS);
    		}
    		String POSRMMSMHS = "null";
    		if(pos!=null && !Checker.isStringNullOrEmpty(pos)) {
    			POSRMMSMHS = pos;
    		}
    		String TELRMMSMHS = "null";
    		String ALMKTMSMHS = "null";
    		String KOTKTMSMHS = "null";
    		String POSKTMSMHS = "null";
    		String TELKTMSMHS = "null";
    		String JBTKTMSMHS = "null";
    		String BIDKTMSMHS = "null";
    		String JENKTMSMHS = "null";
    		String NMMSPMSMHS = "null";
    		String ALMSPMSMHS = "null";
    		String POSSPMSMHS = "null";
    		String KOTSPMSMHS = "null";
    		String NEGSPMSMHS = "null";
    		String TELSPMSMHS = "null";
    		String NEGLHMSMHS = new String(KEWARGANEGARAAN);
    		if(nglhr!=null && !Checker.isStringNullOrEmpty(nglhr)) {
    			nglhr = new String(nglhr.toUpperCase());
        		if(!nglhr.equalsIgnoreCase(KEWARGANEGARAAN)) {
        			NEGLHMSMHS = nglhr;
        		}
    			
    		}
    		String AGAMAMSMHS = "null";
    		if(agama!=null && !Checker.isStringNullOrEmpty(agama)) {
    			AGAMAMSMHS = agama;
    		}
    		String KRKLMMSMHS = "null";
    		String TTLOGMSMHS = "null";
    		String TMLOGMSMHS = "null";
    		String DTLOGMSMHS = "null";
    		String IDPAKETBEASISWA = id_paket;//default
    		String NPM_PA = "null";
    		String NMM_PA = "null";
    		String PETUGAS = "false"; //default non mhs
    		String ASAL_SMA = "null";
    		String KOTA_SMA = "null";
    		String LULUS_SMA = "null";

    		String tkn_info_ext_civitas_tanpa_kdpst_npmhs = STTUSMSMHS+"$"+EMAILMSMHS+"$"+NOHPEMSMHS+"$"+ALMRMMSMHS+"$"+NRTRMMSMHS+"$"+NRWRMMSMHS+"$"+PROVRMMSMHS+"$"+PROVRMIDWIL+"$"+KOTRMMSMHS+"$"+KOTRMIDWIL+"$"+KECRMMSMHS+"$"+KECRMIDWIL+"$"+KELRMMSMHS+"$"+DUSUNMSMHS+"$"+POSRMMSMHS+"$"+TELRMMSMHS+"$"+ALMKTMSMHS+"$"+KOTKTMSMHS+"$"+POSKTMSMHS+"$"+TELKTMSMHS+"$"+JBTKTMSMHS+"$"+BIDKTMSMHS+"$"+JENKTMSMHS+"$"+NMMSPMSMHS+"$"+ALMSPMSMHS+"$"+POSSPMSMHS+"$"+KOTSPMSMHS+"$"+NEGSPMSMHS+"$"+TELSPMSMHS+"$"+NEGLHMSMHS+"$"+AGAMAMSMHS+"$"+KRKLMMSMHS+"$"+TTLOGMSMHS+"$"+TMLOGMSMHS+"$"+DTLOGMSMHS+"$"+IDPAKETBEASISWA+"$"+NPM_PA+"$"+NMM_PA+"$"+PETUGAS+"$"+ASAL_SMA+"$"+KOTA_SMA+"$"+LULUS_SMA;
			//System.out.println("update22="+idobj+","+kdpst);
    		npm_sbg_mhs = udb.updateDariTamuJadiMhs(angkatan, npm_sbg_tamu,idobj, kdpst, tkn_info_civitas_tanpa_kdpti_kdpst_kdjen_npmhs, tkn_info_ext_civitas_tanpa_kdpst_npmhs);
			
			
			
			//if(sukses) {
			//	err_msg = "DATA "+NMMHSMSMHS+" BERHASIL DIINPUT";
		//		url = PathFinder.getPath(uri+"?nuform=true", target);
			//}
			//else {
			//	err_msg = "DATA "+NMMHSMSMHS+" GAGAL DIINPUT";
			//	url = PathFinder.getPath(uri+"?nuform=false", target);
			//}
			//System.out.println("insert data");
			
			
		}
		//else {
		//	String target = Constants.getRootWeb()+"/InnerFrame/PMB/buku_tamu.jsp";
			
		//	url = PathFinder.getPath(uri, target);
	//	}	
		
		//System.out.println("sukseskah="+sukses);
		//System.out.println("allvalid="+all_valid);
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
			//System.out.println("negara let="+negara);
			String target = Constants.getRootWeb()+"/InnerFrame/PMB/tamu_jd_mhs.jsp";
			String uri = request.getRequestURI();
			url = PathFinder.getPath(uri, target);
			if(npm_sbg_mhs!=null) {
				request.getRequestDispatcher("go.search?kword="+npm_sbg_mhs).forward(request,response);
			}
			else {
				//System.out.println("back to tamu_jd_mhs");
				request.getRequestDispatcher(url+"?valid_pt1=true&valid_pt2="+valid_pt2+"&err_msg="+err_msg).forward(request,response);
			}
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

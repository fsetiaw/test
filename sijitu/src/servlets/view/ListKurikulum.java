package servlets.view;

import java.io.IOException;
import beans.dbase.*;
import beans.dbase.krklm.SearchDbKrklm;
import beans.dbase.krklm.UpdateDbKrklm;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
import java.util.Vector;
import java.util.StringTokenizer;
/**
 * Servlet implementation class ListKelas
 */
@WebServlet("/ListKurikulum")
public class ListKurikulum extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public ListKurikulum() {
//        super();
        // TODO Auto-generated constructor stub
//    }
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
		//System.out.println("listKurikulum");
		String err=null;
		String callerPage=request.getParameter("callerPage");
		//System.out.println("cp = "+callerPage);
		SearchDbKrklm sdb = new SearchDbKrklm();
		//UpdateDb udb = new UpdateDb();
		UpdateDbKrklm udkr = new UpdateDbKrklm();
		/*
		 * cek form parameter dari listKelas.jsp
		 * bila parameter valid, proses apa insert atau update kelas 
		 */
		String kdpst_nmpst = request.getParameter("kdpst_nmpst");
		String kdpst = null, nmpst = null;
		if(kdpst_nmpst!=null) {
			StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
			kdpst = st.nextToken();
			nmpst = st.nextToken();
		}
		
		
		//System.out.println("kurikulum page = "+kdpst+" "+nmpst);
		
		String kdkur = request.getParameter("kdkur");
		if(kdkur!=null) {
			StringTokenizer st = new StringTokenizer(kdkur);
			if(st.countTokens()<1) {
				kdkur = null;
			}
		}
	
		String thsms1 = request.getParameter("thsms1");
		if(thsms1!=null) {
			StringTokenizer st = new StringTokenizer(thsms1);
			if(st.countTokens()<1) {
				thsms1 = null;
			}
		}
		
		String thsms2 = request.getParameter("thsms2");
		if(thsms2!=null) {
			StringTokenizer st = new StringTokenizer(thsms2);
			if(st.countTokens()<1) {
				thsms2 = null;
			}
		}
		
		String stkur = request.getParameter("stkur");
		String konsen = request.getParameter("konsen");
		String wajib = request.getParameter("wajib");
		String pilihan = request.getParameter("pilihan");
		int sks_wajib = 0, sks_pilihan=0;
		try {
			sks_wajib = Integer.parseInt(wajib);
		}
		catch(Exception e) {
			sks_wajib = 0;
		}
		try {
			sks_pilihan = Integer.parseInt(pilihan);
		}
		catch(Exception e) {
			sks_pilihan = 0;
		}
		String smstt = request.getParameter("smstt");
		String idkur = request.getParameter("idkur_");//parameter dari form 
		//System.out.println("idkur="+idkur);
		//System.out.println("kdkur="+kdkur);
		/*
		 * cek id idkmk exist, if exist update, if not insert & update proses
		 */
		
		if(idkur!=null && kdkur!=null) {
			StringTokenizer st = new StringTokenizer(kdkur);
			//StringTokenizer st1 = new StringTokenizer(nakmk);
			if(st.countTokens()>0) {
				if(idkur==null || idkur.equalsIgnoreCase("null")) {
					/*
					 * insert
					 */
					//System.out.println("insert kurikulum");
					boolean ada = Checker.isNamaKurikulumAda(kdkur, konsen);
					if(ada) {
						if(err==null) {
							err = new String("Kode/Name Kurikulum sudah dipakai<br>");
						}
						else {
							err = err+"Kode/Name Kurikulum sudah dipakai<br>";
						}
					}
					else {
						int updated = udkr.insertNewKurikulum(kdpst,kdkur,thsms1,konsen,Integer.parseInt(wajib),Integer.parseInt(pilihan),Integer.parseInt(smstt));
						if(updated>0) {
							request.setAttribute("update_status", "berhasil");
						}
						else {
							err =  "Status Insert Kurikulum : Gagal [Harap lakukan screenshot dan kirim ke admin, mohon maaf & terima kasih]";
						}
						//String id = udb.insertNewKurikulum(kdpst,kdkur,thsms1,thsms2,konsen);
					}
				
					
					//System.out.println("idkmk insert="+id);
					//udb.updateMakul(id,kdpst,kdkmk,nakmk,kdwpl,jenis,skstm,skspr,skslp,sttmk,nodos);
				}
				else {
					//int id = Integer.valueOf(idkur).intValue();
					//System.out.println("update kurikulum");
					int updated = udkr.updateKurikulum(kdpst, idkur, kdkur, stkur, thsms1, thsms2, konsen, ""+(sks_wajib+sks_pilihan), smstt, wajib, pilihan);
					if(updated>0) {
						request.setAttribute("update_status", "berhasil");
					}
					else {
						err =  "Status Update Kurikulum : Gagal [Harap lakukan screenshot dan kirim ke admin, mohon maaf & terima kasih]";
					}
					//System.out.println("updating=="+updated);
					//udb.updateKurikulum(""+id,kdpst,kdkur,thsms1,thsms2,konsen);
					
				}			 
			}	
		}
		
		//2nd proses, get vector kelas utk kdpst trsb
		request.setAttribute("err", err);
		Vector vkrklm = sdb.getListKurikulumProdi(kdpst);
		request.setAttribute("vkrklm", vkrklm);
		//System.out.println("kdkmk = "+kdkmk);
		//get vector list kelas lalu ff ke 
		String target = Constants.getRootWeb()+"/InnerFrame/Akademik/listKurikulum.jsp";
		String uri = request.getRequestURI();
		//System.out.println(target+" / "+uri);
		String url = PathFinder.getPath(uri, target);
		//System.out.println("ff_callerPahe="+callerPage);
		request.getRequestDispatcher(url).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}

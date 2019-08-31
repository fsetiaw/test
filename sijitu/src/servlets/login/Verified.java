package servlets.login;
import beans.*;
import beans.dbase.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;

import beans.login.Verificator;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.AddHocFunction;
import beans.tools.Checker;
import beans.tools.Constant;
import beans.tools.PathFinder;

import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Servlet implementation class Verified
 */
@WebServlet("/Verified")
//public class Verified extends ConnectorDb {
public class Verified extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Connection con;   
    DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
 //   public Verified() {
 //       super();
        // TODO Auto-generated constructor stub
 //   }
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		Context initContext = null;
		Context envContext = null;
	    try {
	      // Look up the JNDI data source only once at init time
    		initContext  = new InitialContext();
    		envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		
    		//envContext.close();
    		initContext.close();
    		if(ds==null) {
    			throw new ServletException("missing data source");
    		}		
	    }
	    catch (NamingException e) {
	    	e.printStackTrace();
	    }
	  }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stuba
		UpdateDb udb = new UpdateDb();
		InitSessionUsr isu = null;
		HttpSession session = request.getSession(true);
		String ses_id = request.getSession().getId();
		String usrname = request.getParameter("usr");
		String usrpwd = request.getParameter("pwd");
		String lebar  = (String)request.getParameter( "scrHeight" );
		String panjang  = (String)request.getParameter( "scrWidth" );
		//System.out.println(panjang+" x "+lebar);
		session.setAttribute("lebar", lebar);
		session.setAttribute("panjang", panjang);
		session.removeAttribute("validUsr");
		String info=null;
		try {
			con = ds.getConnection();
			Verificator ve=new Verificator(con);
			String status_maintenance = ve.verifiedIsServerUnderMaintenanceAndIsUsrAllow(usrname, usrpwd);
			if(status_maintenance.equalsIgnoreCase("maintenance")) {
				//forward to maintenance pahe
				String target = Constants.getRootWeb()+"/ErrorPage/underMaintenance.jsp";
				String uri = request.getRequestURI();
				String url_ff = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url_ff+"?backTo=www.satyagama.ac.id&errMsg=MOHON MAAF, SERVER DALAM PERAWATAN").forward(request,response);
			}
			else {
				//System.out.println("verified");
				info = ve.verifiedUsrPwd(usrname, usrpwd);
				//System.out.println("verified info = "+info);
				if(info!=null) {
					StringTokenizer st = new StringTokenizer(info);
					String id = st.nextToken();
					String schema = st.nextToken();
					String force_redirect = st.nextToken();
					schema = Constants.getDbschema().toUpperCase();
					if(schema.startsWith("/")) {
						schema = schema.substring(1, schema.length());
					}
					
					if((isu = ve.connectToSchema_v1(schema,id))!=null) {
						//System.out.println("isu = "+isu.toString());
						SearchDb sdb = new SearchDb();
						String isuKdpst=isu.getKdpst();
						String isuNpmhs=isu.getNpm();
						String isuKdjek=isu.getKdjek();
						String isuObjNickname = isu.getObjNickNameGivenObjId();
						String ttlog_tmlog = sdb.getInfoLoginHistory(isuKdpst,isuNpmhs);
						st = new StringTokenizer(ttlog_tmlog,",");
						int ttlog = Integer.valueOf(st.nextToken()).intValue();
						int tmlog = Integer.valueOf(st.nextToken()).intValue();
						String target_thsms = Checker.getThsmsHeregistrasi();
						
						Vector v_jabatan = isu.getListJabatan_detail(-1);//<0 artinya id sendiri
						String psn = Checker.sudahDaftarUlang(isuKdpst, isuNpmhs, target_thsms); //fungsi ini juga update stmhsmhmhs
						String req_time = Checker.getTglPengajuanDaftarUlang(isuKdpst, isuNpmhs, target_thsms);
						String curr_stmhsmsmhs = AddHocFunction.getAndSyncStmhsBetweenTrlsmAndMsmhs(isuKdpst, isuNpmhs);
						boolean blocked = false;
						if(curr_stmhsmsmhs.equalsIgnoreCase("K")||curr_stmhsmsmhs.equalsIgnoreCase("D")||isu.lagiDiBlokir()) {
							blocked = true;
						}	
						//session.setAttribute("blocked", ""+blocked);
						if(blocked) {
							//System.out.println("sedang di bloke");
							isu = null;
							session.setAttribute("validUsr",isu);
							String npmhs = isu.getNpm();
							String nmmhs = isu.getNimhs(npmhs);
							//System.out.println("usr/pwd="+usrname+"/"+usrpwd);
							//System.out.println("ses_id="+ses_id);
							udb.insertLogMe(isu.getIdObj(), npmhs, "login", nmmhs+" mencoba untuk login menggunakan "+usrname+"/"+usrpwd+"<br>Status Blocked ["+curr_stmhsmsmhs+"]",request.getRemoteAddr(),ses_id );
							  
							//blok akses  
							//String msg = request.getParameter("msg");
							//String redirectTo = request.getParameter("redirectTo");
							//String paramNeeded = request.getParameter("paramNeeded");
							///ToUnivSatyagama/WebContent/InnerFrame/Tamplete/Notification/ShowNotificationThenRedirect_v3.jsp
							String target = Constants.getRootWeb()+"/InnerFrame/Tamplete/Notification/blokir.jsp";
							String uri = request.getRequestURI();
							String url = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url).forward(request, response);
							
							//String target = Constants.getRootWeb()+"/index.jsp";
							//String uri = request.getRequestURI();
							//String url = PathFinder.getPath(uri, target);
							//response.sendRedirect("index.jsp");
							//response.sendRedirect(url);
						}
						else {
							//System.out.println("pit 1");
							//ini session variable status usr yg login
							//untuk mhs = status_akhir_mahasiswa (create saat search)
							session.setAttribute("curr_stmhsmsmhs", curr_stmhsmsmhs); 
							
							
							//System.out.println("psng="+psn);
							session.setAttribute("sdu", psn);
							session.setAttribute("registrasi_updtm", req_time);
							//if(psn==null) {
								//System.out.println("sini");
							//	session.setAttribute("sdu", "true`"+thsms_now);
							//}
							//else {
							//	session.setAttribute("sdu", "false`"+psn);
								//System.out.println("sana");
							//}
							ttlog = ttlog+1;
							udb.updateLogHistory(isuKdpst,isuNpmhs,ttlog,tmlog);
							udb.updateMyCurPa(isuKdpst, isuNpmhs);
							//set attribute ada kelas yg diajar
							if(isuObjNickname!=null && !isuObjNickname.contains("MHS")) {
								session.setAttribute("ada_kelas_yg_diajar", ""+isu.adakelasYgDiajar());
								/*
								 * JGN DIREMOVE di home
								 */
							}
							session.setAttribute("v_jabatan", v_jabatan);
							session.setAttribute("validUsr",isu);
							//System.out.println("validUsr="+isu.getNpm());
							session.setAttribute("ObjGenderAndNickname",isuKdjek+"__"+isuObjNickname);
							session.setAttribute("nmpw", usrname+"||"+usrpwd);
							if(force_redirect!=null && !Checker.isStringNullOrEmpty(force_redirect)) {
								//System.out.println("pit 2");
								String npmhs = isu.getNpm();
								String nmmhs = isu.getNimhs(npmhs);
								//System.out.println("usr/pwd="+usrname+"/"+usrpwd);
								//System.out.println("ses_id="+ses_id);
								udb.insertLogMe(isu.getIdObj(), npmhs, "login", nmmhs+" mencoba untuk login menggunakan "+usrname+"/"+usrpwd+"<br>Status redirected ["+force_redirect+"]",request.getRemoteAddr(),ses_id );
								
								response.sendRedirect(force_redirect);
							}
							else {
								//System.out.println("pit 3");
								String target = Constants.getRootWeb()+"/index.jsp";
								String uri = request.getRequestURI();
								String url = PathFinder.getPath(uri, target);
								/*
								RESERVED BILA HARUS UPDATE PADA SAAT LOGIN
								*/
								//boolean mhs_hrs_upd_profile = false;
								if(isu.iAmStu() && !isu.sudahUpdateDataNikDanIbuKandung()) {
									session.setAttribute("wajib_update_profil", "true");
								}
								else {
									session.setAttribute("wajib_update_profil", "false");
								}
								String npmhs = isu.getNpm();
								String nmmhs = isu.getNmmhs(npmhs);
								//System.out.println("usr/pwd="+usrname+"/"+usrpwd);
								//System.out.println("ses_id="+ses_id);
								String ket = nmmhs+" berhasil login menggunakan "+usrname+"/"+usrpwd;
								//System.out.println("ket="+ket);
								udb.insertLogMe(isu.getIdObj(), npmhs, "login", ket ,request.getRemoteAddr(),ses_id );
								
								//System.out.println("redirect to index");
								response.sendRedirect(url);
							}	
						}
					}
					else {
						//System.out.println("pit 4");
						String npmhs = isu.getNpm();
						String nmmhs = isu.getNmmhs(npmhs);
						String ket = nmmhs+" gagal login menggunakan "+usrname+"/"+usrpwd+"<br>Status No schema DB";
						udb.insertLogMe(isu.getIdObj(), npmhs, "login", ket ,request.getRemoteAddr(),ses_id );
						String login_page = Constant.getValue("LOGIN_PAGE_ADDRESS");
						//response.sendRedirect("http://www.satyagama.ac.id");
						
						response.sendRedirect("http://"+login_page);
					
					}
				}
				else {
					//System.out.println("pit 5");
					//String npmhs = isu.getNpm();
					//String nmmhs = isu.getNmmhs(npmhs);
					//String ket = nmmhs+" gagal login menggunakan "+usrname+"/"+usrpwd+"<br>Status invalid usr/pwd";
					//udb.insertLogMe(isu.getIdObj(), npmhs, "login", ket ,request.getRemoteAddr(),ses_id );
					String login_page = Constant.getValue("LOGIN_PAGE_ADDRESS");
					//response.sendRedirect("http://www.satyagama.ac.id");
					
					response.sendRedirect("http://"+login_page);
				}
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
        	if (con!=null) {
        		try {
        			con.close();
        		}
        		catch (Exception ignore) {
            		//System.out.println(ignore);
        			//System.out.println("pit 6");
        		}
        	}
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
		// TODO Auto-generated method stub
	}

}

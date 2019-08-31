package servlets.search;
import beans.dbase.*;
import beans.dbase.Beasiswa.SearchDbBeasiswa;
import beans.dbase.mhs.SearchDbInfoMhs;
import beans.login.*;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

import java.util.ListIterator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.ResultSet;
/**
 * Servlet implementation class searched
 */
@WebServlet("/searched")
public class Searched extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public Searched() {
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
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
		     response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			response.setContentType("text/html");
		    //PrintWriter out = response.getWriter();
			//HttpSession session = request.getSession(true);
			//InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
			
			if(isu==null) {
				String target = Constants.getRootWeb()+"/index.jsp";
				String uri = request.getRequestURI();
				String url_ff = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(Constants.getRootWeb()).forward(request,response);
			}
			String mode = request.getParameter("mode");
			String kword = request.getParameter("kword");
			
			
			if(mode!=null && mode.equalsIgnoreCase("dobel")) {
				String info_target = request.getParameter("info_target");
				StringTokenizer st = new StringTokenizer(info_target,"~");
				String nmmhs = st.nextToken();
				String tglhr = st.nextToken();
				//System.out.println("tanggal ="+tglhr);
				Vector v = SearchDbInfoMhs.searchCivitas(nmmhs.trim(), java.sql.Date.valueOf(tglhr));
				if(v!=null) {
					//(kdpst+"`"+npmhs+"`"+nmmhs+"`"+tglhr+"`"+smawl+"`"+stpid+"`"+malaikat);
					//System.out.println("size 22="+v.size());
					v.add(0,"PRODI`NPM`NAMA`TGLHR`SMAWL`STPID`VALID");
					v.add(0,"800px");
					v.add(0,"5`15`45`15`5`5`5");
					v.add(0,"center`center`left`center`center`center`center");
					v.add(0,"String`String`String`String`String`String`String");
				}
				session.setAttribute("v", v);
				String target = Constants.getRootWeb()+"/InnerFrame/sql/ResultSet_v4.jsp";
				String uri = request.getRequestURI();
				String url_ff = PathFinder.getPath(uri, target);
				//System.out.println("url_ff="+url_ff);
				request.getRequestDispatcher(url_ff+"?backto=&at_page=1&max_data_per_pg=20").forward(request,response);
			}
			if(kword==null ||(kword!=null&&Checker.isStringNullOrEmpty(kword))) {
				request.getRequestDispatcher("get.notifications").forward(request,response);
				
			}
			else {
				kword = kword.trim();
				if(kword.startsWith(Constants.getKuiSearchChar())) {
					/*
					 * search kui
					 */
					Vector vRecKui = null;
					Vector vScope = isu.getScope("searchKui");
					
					if(vScope!=null && vScope.size()>0) {
						SearchDb sdb = new SearchDb();
						try {
							//StringTokenizer st = new StringTokenizer(kword,"-");
							//st.nextToken();//replace kuiSearchChar
							//kword = st.nextToken();
							//System.out.println("search kuitansi");
							StringTokenizer st = new StringTokenizer(kword,"-");
					    	st.nextToken();//removing kui- part
					    	kword=st.nextToken();
					    	//System.out.println(kword);
							int i = Integer.valueOf(kword).intValue();
							vRecKui=sdb.searchKui(vScope,kword);
							//System.out.println(vRecKui.size()+"");
							request.setAttribute("v_kui_search_result", vRecKui);
							//request.getRequestDispatcher("InnerFrame/searchResult.jsp").forward(request,response);
							String target = Constants.getRootWeb()+"/InnerFrame/searchResult.jsp";
							String uri = request.getRequestURI();
							String url_ff = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url_ff).forward(request,response);
						}
						catch(Exception e) {
							//System.out.println("error search kui ="+e);
							Vector v = null;
							request.setAttribute("v_search_result", v);
							//request.getRequestDispatcher("InnerFrame/searchResult.jsp").forward(request,response);
							String target = Constants.getRootWeb()+"/InnerFrame/searchResult.jsp";
							String uri = request.getRequestURI();
							String url_ff = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url_ff).forward(request,response);
						}
					}
					else {
						/* no akses search,
						 * 
						 */
						Vector v = new Vector();
						request.setAttribute("v_search_result", v);
						//request.getRequestDispatcher("InnerFrame/searchResult.jsp").forward(request,response);
						String target = Constants.getRootWeb()+"/InnerFrame/searchResult.jsp";
						String uri = request.getRequestURI();
						String url_ff = PathFinder.getPath(uri, target);
						request.getRequestDispatcher(url_ff).forward(request,response);
					}
				}
				else {
					/*
					 * search civitas
					 */
					//pilihan menggunakan - prodi
					//bila pencarian kontain '-' format nama-prodi berarti pencarian berdasar prodi
					//itupun bila usr punya wewenang terhadap prodi tertulis
					//System.out.println("getObjNickNameGivenObjId="+isu.getObjNickNameGivenObjId());
					if(!isu.getObjNickNameGivenObjId().contains("MHS")) {
						if(kword.contains(":")) {
							//System.out.println("pencarian berdasar prodi");
							StringTokenizer st = new StringTokenizer(kword,":");
							
							kword = "%"+st.nextToken()+"%";
							int norut =  isu.isAllowTo("s");
							
							Vector v = isu.searchCivitasProdiBased(norut,kword,st.nextToken());
							SearchDbBeasiswa  sdb = new SearchDbBeasiswa(); 
							Vector vHistBea = sdb.getLastBeasiswa(v);
							request.setAttribute("v_search_result", v);
							request.setAttribute("vHistBea", vHistBea);
							//request.getRequestDispatcher("InnerFrame/searchResult.jsp").forward(request,response);
							String target = Constants.getRootWeb()+"/InnerFrame/searchResult_v2.jsp";
							String uri = request.getRequestURI();
							String url_ff = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url_ff).forward(request,response);
						}
						else {
							Vector v =null;
							//System.out.println("pencarian berdasar prodi reguler");
							StringTokenizer st = new StringTokenizer(kword);
							kword="%";
							while(st.hasMoreTokens()) {
								kword = kword+st.nextToken()+"%";
							}
							//System.out.println("kword="+kword);
							
							
							int norut =  isu.isAllowTo("s");
							String tipe_pencarian = request.getParameter("tipe");
							//System.out.println("tipe_pencarian="+tipe_pencarian);
							if(tipe_pencarian.equalsIgnoreCase("mhs")) {
								//Vector v = isu.searchCivitas(norut, kword);
								Vector v_scope_kdpst=isu.getScopeKdpst_vFinal("s");
								String target_kdpst=null, token_stmhs=null, target_stpid=null, target_kdjen=null, range_smawl=null;
								String prodi = request.getParameter("prodi");
								if(!Checker.isStringNullOrEmpty(prodi)) {
									st = new StringTokenizer(prodi,"~");
									target_kdpst=st.nextToken();
								}
								String status = request.getParameter("status");
								if(!Checker.isStringNullOrEmpty(status)) {
									token_stmhs=status;
								}
								
								String operandi = request.getParameter("operandi");
								String angkatan = request.getParameter("angkatan");
								if(!Checker.isStringNullOrEmpty(angkatan)) {
									if(angkatan.length()==4) {
										angkatan=angkatan+"1";
									}
									range_smawl=operandi+"'"+angkatan+"'";
								}
								
								v = isu.searchCivitas(kword, v_scope_kdpst, target_kdpst, token_stmhs, target_stpid, target_kdjen, range_smawl, tipe_pencarian);
							}
							//else if(tipe_pencarian.equalsIgnoreCase("dsn")) {
							else {
								//dosen
								v = isu.searchCivitas(kword, null, null, null, null, null, null, tipe_pencarian);
							}
							
							SearchDbBeasiswa  sdb = new SearchDbBeasiswa(); 
							Vector vHistBea = sdb.getLastBeasiswa(v);
							request.setAttribute("v_search_result", v);
							request.setAttribute("vHistBea", vHistBea);
							//request.getRequestDispatcher("InnerFrame/searchResult.jsp").forward(request,response);
							String target = Constants.getRootWeb()+"/InnerFrame/searchResult_v2.jsp";
							String uri = request.getRequestURI();
							String url_ff = PathFinder.getPath(uri, target);
							request.getRequestDispatcher(url_ff).forward(request,response);
						}	
					}
					else {
						//reserved untuk search bagi usr MAHASISWA
					}
						
				}	
			}
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

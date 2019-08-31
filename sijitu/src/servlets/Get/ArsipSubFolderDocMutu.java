package servlets.Get;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.arsip.SearchDbArsip;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class ArsipSubFolderDocMutu
 */
@WebServlet("/ArsipSubFolderDocMutu")
public class ArsipSubFolderDocMutu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArsipSubFolderDocMutu() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("sub arsip");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			String root_dir = ""+request.getParameter("root_dir");
			String keter = ""+request.getParameter("keter");
			String alm = ""+request.getParameter("alm");
			String alm_ori = new String(alm);
			while(alm_ori.contains("~DAN~")) {
				alm_ori = alm_ori.replace("~DAN~", "&");
			}
			while(alm_ori.contains("~OR~")) {
				alm_ori = alm_ori.replace("~OR~", "/");
			}
			String hak =  ""+request.getParameter("hak");
			String kdpst = (String)request.getParameter("kdpst");
			if(Checker.isStringNullOrEmpty(kdpst)) {
				kdpst = (String)session.getAttribute("kdpst_folder");
			}
			//String kdpst =  (String)session.getAttribute("kdpst_folder"); //diset di home_spmi.jsp
			String nm_kdpst = Converter.getDetailKdpst_v1(kdpst);
			nm_kdpst = nm_kdpst.replace("(", "[");
			nm_kdpst = nm_kdpst.replace(")", "]");
			while(nm_kdpst.contains("  ")) {
				nm_kdpst=nm_kdpst.replace("  ", " ");
			}
			//System.out.println("root_dir="+root_dir);
			//System.out.println("alm="+alm);
			//System.out.println("kdpst0="+kdpst);
			//
			
			//Vector v = sdb.getAllowableFileFolder(objNickname);
			//session.setAttribute("v_nm_alm_access", v);
			/*
			 * !!!!UPDATED hakAksesUsrUtkFolderIni
			 
					
			String objNickname = null;
			String hakAksesUsrUtkFolderIni = null;
			if(hak!=null && !Checker.isStringNullOrEmpty(hak) && hak.contains("-")) {
				SearchDbArsip sdb = new SearchDbArsip(isu.getNpm());
				objNickname = isu.getObjNickNameGivenObjId();
				hakAksesUsrUtkFolderIni = sdb.getHakAksesGiven(hak, objNickname);	
				
			}
			else {
				hakAksesUsrUtkFolderIni = (String)session.getAttribute("hakAksesUsrUtkFolderIni");
			}
			*/
			String hakAksesUsrUtkFolderIni = null;
			if(hak!=null && !Checker.isStringNullOrEmpty(hak)) {
				//SearchDbArsip sdb = new SearchDbArsip(isu.getNpm());
				//objNickname = isu.getObjNickNameGivenObjId();
				//hakAksesUsrUtkFolderIni = sdb.getHakAksesGiven(hak, objNickname);	
				hakAksesUsrUtkFolderIni  = new String(hak.trim());
			}
			else {
				hakAksesUsrUtkFolderIni = (String)session.getAttribute("hakAksesUsrUtkFolderIni");
			}
			//System.out.println("hakAksesUsrUtkFolderIni="+hakAksesUsrUtkFolderIni);
			
			File f = new File(alm_ori);

			File[] listFile = f.listFiles();
			Vector v = new Vector();
			ListIterator li = v.listIterator();
			if(listFile!=null) {
				for(int k=0;k<listFile.length;k++) {
					li.add(listFile[k].getName().trim()+"`"+k);
				}
				Collections.sort(v);
			}
			File[] sortedListFile =null;
			if(listFile!=null&&listFile.length>0) {
				sortedListFile = new File[listFile.length];
				li = v.listIterator();
				int l = 0;
				while(li.hasNext()) {
					
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String namaFile = st.nextToken().trim();
					String norutFile = st.nextToken().trim();
					sortedListFile[l++] = listFile[Integer.parseInt(norutFile)];
				}
			}
			
			
			//System.out.println("listFile="+listFile.length);
			session.setAttribute("listFileFolder", sortedListFile);
			session.setAttribute("hakAksesUsrUtkFolderIni", hakAksesUsrUtkFolderIni);
			String target = Constants.getRootWeb()+"/InnerFrame/Arsip/folderContentDocMutu.jsp";
			String uri = request.getRequestURI();
			String url_ff = PathFinder.getPath(uri, target);
			//request.getRequestDispatcher(url_ff+"?cur_dir="+alm+"&keter="+keter).forward(request,response);
			String redirect = response.encodeRedirectURL(url_ff+"?kdpst="+kdpst.trim()+"&root_dir="+root_dir.trim()+"&cur_dir="+alm.trim()+"&keter="+keter.trim());
			//System.out.println("redirect="+redirect);
			response.sendRedirect(redirect);
			/*
			if(listFile!=null && listFile.length>0) {
				for(int i=0;i<listFile.length;i++) {
					if (listFile[i].isDirectory()) {
			            //System.out.println("directory:"+listFile[i].getName());
			        } else {
			            //System.out.println("     file:"+listFile[i].getName());;
			        }
					//System.out.println(listFile[i].getName());
				}
			    */
			
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

package servlets.update.trnlm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ListIterator;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.folder.file.FileManagement;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Constant;
import beans.tools.PathFinder;


/**
 * Servlet implementation class UpdNilaiViaExcel
 */
@WebServlet("/UpdNilaiViaExcel")
public class UpdNilaiViaExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Constants ----------------------------------------------------------------------------------
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    // Properties ---------------------------------------------------------------------------------
    private String filePath;
   
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdNilaiViaExcel() {
        super();
        // TODO Auto-generated constructor stub
    }

    
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
		/*
	    */
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

		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			PrintWriter out = response.getWriter();
			//System.out.println("okayu");
			Vector v = (Vector)session.getAttribute("v_tmp");
			session.removeAttribute("v_tmp");
			String thsms = request.getParameter("thsms");
			String cuid = request.getParameter("uniqueId");
			String kdkmk = request.getParameter("kdkmk");
			String nakmk = request.getParameter("nakmk");
			String shiftKelas = request.getParameter("shiftKelas");
			String nmmdos = request.getParameter("nmmdos");
			String npmdos = request.getParameter("npmdos");
			String noKlsPll = request.getParameter("noKlsPll");
			
			String[]info_kls  = {""+cuid,""+kdkmk,""+nakmk,""+shiftKelas,""+nmmdos,""+npmdos,""+noKlsPll,""+thsms};
			//String output_file_name = request.getParameter("output_file_name");
			
			//System.out.println("okay");
			//System.out.println("v.size="+v.size());
			
			FileManagement fm = new FileManagement();
			//prepFormPenilaianMk(Vector v_list_mhs_dan_nilai, String[] info_kls, String operator_id, String operator_npm)
			String output_file_name = fm.prepFormPenilaianMk(v, info_kls,""+isu.getIdObj(),isu.getNpm());
			//System.out.println("output_file_name="+output_file_name);
			//Constant.getVelueFromConstantTable("FOLDER_TMP")+"/"+out_file_name);
			File file = new File(Constant.getVelueFromConstantTable("FOLDER_TMP")+"/"+output_file_name);
	        if (!file.exists()) {
	        	//System.out.println("excel out tfk ditemeukan bo");
	            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
	            return;
	        }
	        //System.out.println("excel out  ditemeukan bo");
	        String contentType = getServletContext().getMimeType(file.getName());
	        if (contentType == null) {
	            contentType = "application/octet-stream";
	        }
	        response.reset(); 
	        response.setBufferSize(DEFAULT_BUFFER_SIZE);
	        response.setContentType(contentType);
	        response.setHeader("Content-Length", String.valueOf(file.length()));
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

	        // Prepare streams.
	        BufferedInputStream input = null;
	        BufferedOutputStream output = null;

	        try {
	            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
	            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
	            //System.out.println("29");
	            // Write file contents to response.
	            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
	            int length;
	            while ((length = input.read(buffer)) > 0) {
	                output.write(buffer, 0, length);
	            }
	            //System.out.println("30");
	        } finally {
	            // Gently close streams.
	            close(output);
	            close(input);
	        }
			//request.getRequestDispatcher(url).forward(request,response);
		}
	}
	
    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
                //System.out.println("closing");
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
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

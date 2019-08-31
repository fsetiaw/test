package servlets.Files;

import java.io.IOException;
import java.util.Vector;
import java.util.ListIterator;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.StringTokenizer;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ListIterator;
import java.util.Vector;
import beans.dbase.*;
import beans.setting.*;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Constant;
import beans.tools.Converter;
import beans.tools.PathFinder;
import beans.folder.file.*;
import beans.login.InitSessionUsr;
/**
 * Servlet implementation class FileServlet
 */
@WebServlet("/Vector2ExcelDownloader_KrsPasca")
public class Vector2ExcelDownloader_KrsPasca extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    // Constants ----------------------------------------------------------------------------------

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    // Properties ---------------------------------------------------------------------------------

    private String filePath;

    // Actions ------------------------------------------------------------------------------------
	
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public KrsDownload() {
//        super();
        // TODO Auto-generated constructor stub
//    }
    
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
		/*
	    */
	  }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		//String output_file_name = request.getParameter("output_file_name");
		//if(Checker.isStringNullOrEmpty(output_file_name)) {
		//	output_file_name="tmp_"+AskSystem.getTime();
		//}
		//System.out.println("output_file_name="+output_file_name);	
		Vector vHistKrsKhs = (Vector)session.getAttribute("vHistKrsKhsForEdit");
		Vector v = new Vector();
		ListIterator li = v.listIterator();
		String target_npmhs = (String)session.getAttribute("npmhs");
		target_npmhs = target_npmhs.trim();
		target_npmhs = target_npmhs.replace(" ", "_");
		String target_kdpst = (String)session.getAttribute("kdpst");
		target_kdpst = target_kdpst.trim();
		target_kdpst = target_kdpst.replace(" ", "_");
		String target_nmmhs = (String)session.getAttribute("nmmhs");
		target_nmmhs = target_nmmhs.trim();
		target_nmmhs = target_nmmhs.replace(" ", "_");
		session.removeAttribute("npmhs");
		session.removeAttribute("kdpst");
		session.removeAttribute("nmmhs");
		
		li.add("string`string`string`string`string`string`string`double`long");
		li.add("THSMS`PRODI`NPM`NAMA`KODE MK`MATAKULIAH`NILAI[ANGKA/HURUF]`BOBOT`SKS");
		//session.removeAttribute("v");
		ListIterator lih = vHistKrsKhs.listIterator();
		while(lih.hasNext()) {
			String thsms=(String)lih.next();//1
			String kdkmk=(String)lih.next();//2
			String nakmk=(String)lih.next();//3
			String nlakh=(String)lih.next();//4
			String bobot=(String)lih.next();//5
			String sksmk=(String)lih.next();//6
			String kelas=(String)lih.next();//7
			String sksem=(String)lih.next();//8
			String nlips=(String)lih.next();//9
			String skstt=(String)lih.next();//10
			String nlipk=(String)lih.next();//11
			String shift=(String)lih.next();//12
			String krsdown=(String)lih.next();//13
			String khsdown=(String)lih.next();//14
			String bakprove=(String)lih.next();//15
			String paprove=(String)lih.next();//16
			String note=(String)lih.next();//17
			String lock=(String)lih.next();//18
			String baukprove=(String)lih.next();//19
					
			String idkmk =(String)lih.next();//20
			String addReq =(String)lih.next();//21
			String drpReq  =(String)lih.next();//22
			String npmPa =(String)lih.next();//23
			String npmBak =(String)lih.next();//24
			String npmBaa =(String)lih.next();//25
			String npmBauk =(String)lih.next();//26
			String baaProve =(String)lih.next();//27
			String ktuProve =(String)lih.next();//28
			String dknProve =(String)lih.next();//29
			String npmKtu =(String)lih.next();//30
			String npmDekan =(String)lih.next();//31
			String lockMhs =(String)lih.next();//32
			String kodeKampus =(String)lih.next();//33
			String cuid =(String)lih.next();//34
			String cuid_init =(String)lih.next();//35
			String npmdos =(String)lih.next();//36
			String nodos =(String)lih.next();//37
			String npmasdos =(String)lih.next();//38
			String noasdos =(String)lih.next();//39
			String nmmdos =(String)lih.next();//40
			String nmmasdos =(String)lih.next(); //41
			String brs =  thsms+"`"+target_kdpst+"`"+target_npmhs+"`"+target_nmmhs+"`"+kdkmk+"`"+nakmk+"`"+nlakh+"`"+bobot+"`"+sksmk;
			li.add(brs);
		}	
		System.out.println("okay");
		System.out.println("v.size="+v.size());
		//System.out.println("output_file_name="+output_file_name);
		FileManagement fm = new FileManagement();
		
		
		String output_file_name = new String("krs_banjalan_"+target_nmmhs+"_"+target_npmhs);
		
		fm.prepEmptyExcel(v, output_file_name);
		
		//Constant.getVelueFromConstantTable("FOLDER_TMP")+"/"+out_file_name);
		File file = new File(Constant.getVelueFromConstantTable("FOLDER_TMP")+"/"+output_file_name+".xlsx");
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
		doGet(request,response);
	}
}

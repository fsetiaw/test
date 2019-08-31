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
import beans.dbase.spmi.SearchManualMutu;
import beans.dbase.spmi.SearchStandarMutu;
import beans.dbase.spmi.manual.SearchManual;
import beans.setting.*;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Constant;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.PathFinder;
import beans.folder.file.*;
import beans.login.InitSessionUsr;
/**
 * Servlet implementation class FileServlet
 */
@WebServlet("/Vector2ExcelDownloader_v3")
public class DownloadManual extends HttpServlet {
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
		String output_file_name = request.getParameter("output_file_name");
		String tipe = request.getParameter("tipe");
		String versi_std = request.getParameter("versi_std");
		String id_std = request.getParameter("id_std");
		String no_dok_spmi = request.getParameter("no_dok_spmi");
		String tmp = null;
		String[]visi_misi_tujuan_nilai = Getter.getVisiMisiTujuanNilaiPt(Constants.getKdpti());
		SearchManual sm = new SearchManual();
		if(Checker.isStringNullOrEmpty(output_file_name)) {
			output_file_name="tmp_"+AskSystem.getTime();
		}
		if(tipe.equalsIgnoreCase("penetapan")||tipe.equalsIgnoreCase("perencanaan")) {
			output_file_name="man_penetapan_"+AskSystem.getTime();
			tipe = "penetapan";
			tmp = sm.searchListManualPerencanaanUmum(Integer.parseInt(versi_std), Integer.parseInt(id_std));
		}
		else if(tipe.equalsIgnoreCase("pelaksanaan")) {
			output_file_name="man_pelaksanaan_"+AskSystem.getTime();
			tmp = sm.searchListManualPelaksanaanUmum(Integer.parseInt(versi_std), Integer.parseInt(id_std));
		}
		else if(tipe.equalsIgnoreCase("evaluasi")) {
			output_file_name="man_evaluasi_"+AskSystem.getTime();
			tmp = sm.searchListManualEvaluasiUmum(Integer.parseInt(versi_std), Integer.parseInt(id_std));
		}
		else if(tipe.equalsIgnoreCase("pengendalian")) {
			output_file_name="man_pengendalian_"+AskSystem.getTime();
			tmp = sm.searchListManualPengendalianUmum(Integer.parseInt(versi_std), Integer.parseInt(id_std));
		}
		else if(tipe.equalsIgnoreCase("penigkatan")) {
			output_file_name="man_peningkatan_"+AskSystem.getTime();
			tmp = sm.searchListManualPeningkatanUmum(Integer.parseInt(versi_std), Integer.parseInt(id_std));
		}

		if(!Checker.isStringNullOrEmpty(tmp)) {
			Vector v = new Vector();
			ListIterator li = v.listIterator();
			SearchStandarMutu ssm = new SearchStandarMutu();
			String nm_std = ssm.getNamaStandar(Integer.parseInt(id_std));
			li.add(tipe);
			li.add(nm_std);
			li.add(no_dok_spmi);
			
			String tgl_rumus = ""+ssm.getTglPenetapan(""+id_std, versi_std, "perumusan");
			String tgl_cek = ""+ssm.getTglPenetapan(""+id_std, versi_std, "pemeriksaan");
			String tgl_stuju = ""+ssm.getTglPenetapan(""+id_std, versi_std, "persetujuan");
			String tgl_tetap = ""+ssm.getTglPenetapan(""+id_std, versi_std, "penetapan");
			String tgl_kendali = ""+ssm.getTglPenetapan(""+id_std, versi_std, "pengendalian");
			li.add(tgl_rumus);
			li.add(tgl_cek);
			li.add(tgl_stuju);
			li.add(tgl_tetap);
			li.add(tgl_kendali);
			String visi = "null";
			String misi = "null";
			String tujuan = "null";
			String nilai = "null";
			if(visi_misi_tujuan_nilai!=null) {
				if(visi_misi_tujuan_nilai[0]!=null) {
					visi = visi_misi_tujuan_nilai[0];
				}
				if(visi_misi_tujuan_nilai[1]!=null) {
					misi = visi_misi_tujuan_nilai[1];
				}
				if(visi_misi_tujuan_nilai[2]!=null) {
					tujuan = visi_misi_tujuan_nilai[2];
				}
				if(visi_misi_tujuan_nilai[3]!=null) {
					nilai = visi_misi_tujuan_nilai[3];
				}
			}
			li.add(visi);
			li.add(misi);
			li.add(tujuan);
			li.add(nilai);
			li.add(tmp);
			
			FileManagement fm = new FileManagement();
			if(tipe.equalsIgnoreCase("penetapan")||tipe.equalsIgnoreCase("perencanaan")) {
				fm.prepManualPenetapan(v, output_file_name);
			}
			else if(tipe.equalsIgnoreCase("pelaksanaan")) {
				
			}
			else if(tipe.equalsIgnoreCase("evaluasi")) {
				
			}
			else if(tipe.equalsIgnoreCase("pengendalian")) {
				
			}
			else if(tipe.equalsIgnoreCase("penigkatan")) {
				
			}
			
			//Constant.getVelueFromConstantTable("FOLDER_TMP")+"/"+out_file_name);
			File file = new File(Constant.getVelueFromConstantTable("FOLDER_TMP_CG2")+"/"+output_file_name+".xlsx");
	        if (!file.exists()) {
	        	System.out.println("excel out tfk ditemeukan bo");
	            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
	            return;
	        }
	        System.out.println("excel out  ditemeukan bo");
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
		System.out.println("output_file_name="+output_file_name);
		System.out.println("output_file_name="+Constant.getVelueFromConstantTable("FOLDER_TMP_CG2")+"/"+output_file_name+".xlsx");
		//System.out.println("v.size="+v.size());
		//System.out.println("output_file_name="+output_file_name);

		
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

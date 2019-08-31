package servlets.Files;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import beans.dbase.mhs.data_pribadi.UpdateDbInfoMhsDataPri;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.dbase.trnlm.UpdateDbTrnlm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Constant;
import beans.tools.Getter;
import beans.tools.PathFinder;
import beans.tools.Tool;

/**
 * Servlet implementation class DragDrop
 */
@WebServlet("/DragDrop")
public class DragDrop extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DragDrop() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//System.out.println("okay");
    	PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String form = request.getParameter("form");
		//System.out.println("form="+form);
		String target = "";		
		String uri = "";
		String url = "";
		
        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (!isMultipart) {
            	//System.out.println("not multipart");
            }
            // multipart form
            else {
            	//System.out.println("is multipart");
                ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
                List fileItems = upload.parseRequest(request);
                Iterator iter = fileItems.iterator();
                
                int i = 0;

                while (iter.hasNext()) {
                	i++;
                	//System.out.println(i);
                    FileItem item = (FileItem) iter.next();
                    String fieldName = item.getFieldName();
                    //System.out.println("fieldName="+fieldName);
                    if (item.isFormField()) {
                    	//===process upload bahanajar
                    	//String fullNameFile = item.getName();
                        //System.out.println("fullNameFile="+fullNameFile);
                    }
                    else {
                    	// upload field
                        
                    	//System.out.println("6");
                        //String fileName = item.getName();
                        String fullNameFile = item.getName();
                        //System.out.println("fullNameFile="+fullNameFile);
                        //System.out.println("uploadToFolder="+uploadToFolder);
                        //String fullNameFileLc = fullNameFile.toLowerCase();
                        String fileName=null;
                        
                        StringTokenizer st = new StringTokenizer(fullNameFile,".");
                        if(st.countTokens()>1) {
                        	fileName = new String();
                        	//filename ada titiknya
                        	for(int j=0;j<st.countTokens();j++) {
                        		fileName = fileName+st.nextToken();
                        		if(j<st.countTokens()) {
                        			fileName = fileName+".";
                        		}
                        	}
                        }
                        while(fileName.contains(".")) {
                        	fileName = fileName.replace(".", "_");
                        }
                        
                        //fileName = st.nextToken();
                        //fileName = fileName.toLowerCase();
                        String ext = st.nextToken();
                        File fileTo = null;
                        ext = ext.toLowerCase();
                        if(form.equalsIgnoreCase("noijazah")) {
                            	//taro ke folder tmp
                           	
                           	String nuFileName = "list_ijazah."+ext;
                            String uploadToFolder = Getter.getTmpFolderPath();
                            //System.out.println("uploadToFolder2="+uploadToFolder);
                            fileTo = new File(uploadToFolder);
                            fileTo.mkdirs();
                            fileTo = new File(uploadToFolder +"/"+ nuFileName);
                                //session.setAttribute("nuFileName", nuFileName);

                    		item.write(fileTo);
                    		boolean full_editor = false;
                			if(isu.isAllowTo("allowCetakIjazah")>0) {
                				full_editor = true;
                			}
                    		UpdateDbTrlsm udt = new UpdateDbTrlsm(isu.getNpm());
                			Vector v_mismatch = udt.updStmhsBassedOnExcelIjazah_v1(nuFileName, full_editor);
                    		//System.out.println("done");	
                    		//NGGA BIS DI FORWARD KALO PAKE PROGRESS BAR
                    		
                        }
                        else if(form.equalsIgnoreCase("potoMhs")) {
                        	//System.out.println("mantab");
                        	//FILE PHOTO HARUS DGN NAMAFILE = NPM
                        	String uploadToFolder = Constant.getVelueFromConstantTable("ROOT_PATH_FOLDER_MHS");
                        	String nuFileName = fileName+"."+ext;
                        	fileTo = new File(uploadToFolder);
                            fileTo.mkdirs();
                            fileTo = new File(uploadToFolder +"/"+ nuFileName);
                            item.write(fileTo);
                        }
                        else if(form.equalsIgnoreCase("ua")) {
                        	//UJAIN AKHIR
                        	//System.out.println("1");
                        	//String load_date = ;
                        	//String load_time = AskSystem.getWaktuSekarang();
                        	String nu_file_name = request.getParameter("nu_file_name");
                        	String target_npm = request.getParameter("target_npm");
                        	String uploadToFolder = Constant.getVelueFromConstantTable("ROOT_PATH_FOLDER_MHS");
                        	String folder_karya_ilmiah = uploadToFolder+"/"+target_npm+"/KARYA_ILMIAH";
                        	//System.out.println("folder_karya_ilmiah="+folder_karya_ilmiah);
                        	String nuFileName = nu_file_name+"."+ext;
                        	fileTo = new File(folder_karya_ilmiah);
                            fileTo.mkdirs();
                            fileTo = new File(folder_karya_ilmiah +"/"+ nuFileName);
                            item.write(fileTo);
                        }
                        else if(form.equalsIgnoreCase("nilaiMk")) {
                        	//System.out.println("mantab");
                        	//FILE PHOTO HARUS DGN NAMAFILE = NPM
                        	String uploadToFolder = Constant.getVelueFromConstantTable("FOLDER_TMP");
                        	
                        	String nuFileName = fileName+"."+ext;
                        	fileTo = new File(uploadToFolder);
                            fileTo.mkdirs();
                            fileTo = new File(uploadToFolder+"/"+ nuFileName);
                            item.write(fileTo);
                            String tkn_col = "0`1`2`3`";
                            int starting_row = 0;
                            Vector v_info_kls = Tool.bacaFileExcel(uploadToFolder, fileName, tkn_col, starting_row);
                            if(v_info_kls!=null) {
                            	//get cuid
                            	ListIterator li = v_info_kls.listIterator();
                            	if(li.hasNext()) {
                            		String brs = (String)li.next();
                            		//System.out.println("baris="+brs);
                            		st = new StringTokenizer(brs,"`");
                            		String first_token = st.nextToken();
                            		st = new StringTokenizer(first_token,",");
                            		String cuid = st.nextToken();
                            		String thsms = st.nextToken();
                            		//System.out.println("cuid="+cuid);
                            		//System.out.println("thsms="+thsms);
                            		//info mk
                            		brs = (String)li.next();
                            		st = new StringTokenizer(brs,"`");
                            		first_token = st.nextToken();
                            		first_token = first_token.replace("[", "`");
                            		first_token = first_token.replace("]", "");
                            		st = new StringTokenizer(first_token,"`");
                            		String nakmk = st.nextToken();
                            		String kdkmk = st.nextToken();
                            		//info dosen
                            		brs = (String)li.next(); //nmdos[npmdos]
                            		st = new StringTokenizer(brs,"`");
                            		first_token = st.nextToken();
                            		first_token = first_token.replace("[", "`");
                            		first_token = first_token.replace("]", "");
                            		st = new StringTokenizer(first_token,"`");
                            		String nmmdos = st.nextToken();
                            		String npmdos = st.nextToken();
                            		//System.out.println("kdkmk="+kdkmk);
                            		//System.out.println("npm dosen="+npmdos);
                            		String sy_dosennya = "false";
                            		if(npmdos.equalsIgnoreCase(isu.getNpm())) {
                            			sy_dosennya = "true";
                            		}
                            		//get nilai
                            		starting_row = 7;
                            		
                                    Vector v_info_mhs_nilai = Tool.bacaFileExcel(uploadToFolder, fileName, tkn_col, starting_row);
                                    if(v_info_mhs_nilai!=null && v_info_mhs_nilai.size()>0) {
                                    	int size = v_info_mhs_nilai.size();
                                    	String[] tkn_nilai = new String[size];
                                    	String[] tkn_npmhs = new String[size];
                                    	String[] tkn_kdpst = new String[size];
                                    	li = v_info_mhs_nilai.listIterator();
                                    	int no = 0;
                                    	while(li.hasNext()) {
                                    		brs = (String)li.next();
                                    		//System.out.println("baris1="+brs);
                                    		st = new StringTokenizer(brs,"`");
                                    		st.nextToken(); //norut - ignore
                                    		String nmmhs = st.nextToken(); //[kdpst]nmmhs
                                    		nmmhs = nmmhs.replace("[", "");
                                    		nmmhs = nmmhs.replace("]", "`");
                                    		String npmhs = st.nextToken();
                                    		String nilai = st.nextToken();
                                    		st = new StringTokenizer(nmmhs,"`");
                                    		String kdpst = st.nextToken();
                                    		nmmhs = st.nextToken();
                                    		tkn_kdpst[no] = kdpst;
                                    		tkn_npmhs[no] = npmhs;
                                    		tkn_nilai[no] = nilai;
                                    		no++;
                                    	}
                                    	//System.out.println("length tkn = "+tkn_nilai.length+" - "+tkn_npmhs.length);
                                    	UpdateDbTrnlm udt = new UpdateDbTrnlm(isu.getNpm());
                                    	//updateNilaiPerKelas(String thsms, String kdkmk, int idkmk, String[] nilai_value, String npmdos, String saya_dosennya, String cuid, String[]kdpst, String[]npmhs)
                                    	udt.updateNilaiPerKelas(thsms, kdkmk, -1, tkn_nilai, npmdos, sy_dosennya, cuid, tkn_kdpst, tkn_npmhs);
                                    }
                                    	
                                    
                            	}
                            	
                            }
                            else {
                            	//System.out.println("v = 0");
                            }
                        }
                        else if(form.equalsIgnoreCase("nilaiMkPasca")) {
                        	//System.out.println("nilai pasca");
                        	//FILE PHOTO HARUS DGN NAMAFILE = NPM
                        	String uploadToFolder = Constant.getVelueFromConstantTable("FOLDER_TMP");
                        	String nuFileName = fileName+"."+ext;
                        	//System.out.println("nuFileName pasca= "+nuFileName);
                        	if(nuFileName.startsWith("krs_banjalan_")) {
                        		fileTo = new File(uploadToFolder);
                                fileTo.mkdirs();
                                fileTo = new File(uploadToFolder+"/"+ nuFileName);
                                item.write(fileTo);
                                String tkn_col = "0`1`2`4`6`7`8";
                                int starting_row = 0;
                                Vector v_hist_krs = Tool.bacaFileExcel(uploadToFolder, fileName, tkn_col, starting_row);
                                Vector v_hist_lsm = (Vector)v_hist_krs.clone();
                                ListIterator li = v_hist_krs.listIterator();
                                //while(li.hasNext()) {
                                //	String brs = (String)li.next();
                                //	//System.out.println(brs);
                                //}
                                UpdateDbTrnlm udt = new UpdateDbTrnlm(isu.getNpm());
                                int updated = udt.updateNilaiDanKrsPascaBasedOnExcel(v_hist_krs);
                                
                                UpdateDbTrlsm uds = new UpdateDbTrlsm(isu.getNpm());
                                updated = uds.updateTrlsmPascaBasedOnExcel(v_hist_lsm);
                                //System.out.println("nilai pasca selesai");
                        	}
                        	
                        }
                        else if(form.equalsIgnoreCase("nim")) {
                        	//System.out.println("bemer");
                        	String uploadToFolder = Constant.getVelueFromConstantTable("FOLDER_TMP");
                        	st = null;
                        	String nuFileName = fileName+"."+ext;
                        	fileTo = new File(uploadToFolder);
                            fileTo.mkdirs();
                            fileTo = new File(uploadToFolder+"/"+ nuFileName);
                            item.write(fileTo);
                            String tkn_col = "0`1`2`";
                            String tkn_col_type = "s`s`s`";
                            int starting_row = 1;
                            Vector v_npm_nim = Tool.bacaFileExcel(uploadToFolder, fileName, tkn_col, starting_row);
                            if(v_npm_nim!=null) {
                            	//get cuid
                            	ListIterator li = v_npm_nim.listIterator();
                            	while(li.hasNext()) {
                            		String brs = (String)li.next();
                            		brs = brs.replace("`'`", "`null`");
                            		brs = brs.replace("'", "");
                            		st =  new StringTokenizer(brs,"`");
                            		String npmhs = st.nextToken();
                            		String nimhs = st.nextToken();
                            		nimhs = nimhs.trim();
                            		String nmmhs = st.nextToken();
                            		li.set(npmhs+"`"+nimhs);
                            		//System.out.println("baris == "+brs);
                            		//System.out.println("nimhs == "+nimhs);
                            	}
                            	UpdateDbInfoMhsDataPri udb = new UpdateDbInfoMhsDataPri();
                                int updated = udb.updateNimhsFromExcel(v_npm_nim);
                            }
                            //UpdateDbInfoMhsDataPri udb = new UpdateDbInfoMhsDataPri();
                            //int updated = udb.updateNimhsFromExcel(v_npm_nim);
                        }
                        else if(form.equalsIgnoreCase("kelulusan")) {
                        	String uploadToFolder = Constant.getVelueFromConstantTable("FOLDER_TMP");
                        	st = null;
                        	String nuFileName = fileName+"."+ext;
                        	fileTo = new File(uploadToFolder);
                            fileTo.mkdirs();
                            fileTo = new File(uploadToFolder+"/"+ nuFileName);
                            item.write(fileTo);
                            boolean allowEditIjazah = false;
                            if(isu.isAllowTo("allowEditIjazah")>0) {
                            	allowEditIjazah = true;
                            }
                            String tkn_col = "0`1`2`3`4`5`6`7`8`9`10`11`12`13`14`15`16";
                            String tkn_col_type = "s`s`s`s`s`s`s`s`s`s`s`s`s`s`s`s`s`";
                            int starting_row = 1;//mulai dari 0
                            Vector v_npm_nim = Tool.bacaFileExcel(uploadToFolder, fileName, tkn_col, starting_row);
                            if(v_npm_nim!=null) {
                            	//get cuid
                            	ListIterator li = v_npm_nim.listIterator();
                            	while(li.hasNext()) {
                            		String brs = (String)li.next();
                            		while(brs.contains("``")) {
                            			brs = brs.replace("``", "`null`");
                            		}
                            		li.set(brs);
                            	}
                            	UpdateDbTrlsm udt = new UpdateDbTrlsm();
                            	int upd = udt.updLulusanBerdasarExcelWisudawan_v1(v_npm_nim, allowEditIjazah);
                            }
                            
                        }
                    }
                }
            }
           // 
        } catch (Exception ex) {
        	//System.out.println("error ex disini");

        	
            ex.printStackTrace();
        } finally {
        	///out.close();
            
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

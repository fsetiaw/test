package beans.folder.file;

import javax.ejb.LocalBean;
import beans.setting.*;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.owasp.esapi.ESAPI;
import java.util.Collections;
import java.util.Vector;
import java.util.ListIterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.StringTokenizer;
import beans.tools.*;
import beans.setting.*;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import beans.dbase.*;
import beans.sistem.*;

//import org.apache.poi.hssf.usermodel.HSSFFont;
//poi staff
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Session Bean implementation class FileManagement
 */
@Stateless
@LocalBean
public class FileManagement {
	String schema,kdpst,npm;
	
    /**
     * Default constructor. 
     */
    public FileManagement(String schema) {
        // TODO Auto-generated constructor stub
    	this.schema = schema;
    }
    public FileManagement() {
        // TODO Auto-generated constructor stub
    	this.schema = schema;
    }
    
    public String prosesFormKurikulum(String fileName, String kdpst, String msg, Vector v) {
    	msg = "proses form kurikulum "+kdpst;
		java.io.File file = new File(Constants.getIncomingUploadFile()+"/"+fileName);
		ListIterator li = v.listIterator();
    	if(file.exists()) {
    		try {
    			InputStream inp = new FileInputStream(file);
    			msg = msg+"<br /> file "+fileName+" ditemukan";
        	    //InputStream inp = new FileInputStream("workbook.xlsx");
    			Workbook wb = WorkbookFactory.create(inp);
    			int tot_sheet = wb.getNumberOfSheets();
    			msg = msg+"<br /> total sheet = "+tot_sheet;
    			
    			for(int i=0;i<tot_sheet;i++) {
    				//System.out.println("i="+i);
    				Sheet sheet = wb.getSheetAt(i);
    				String baris = "";
    				//get status
    				Row row = sheet.getRow(0);
					Cell cell = row.getCell(4);
					String value = "";
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						value = ""+cell.getRichStringCellValue().getString();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							value = ""+cell.getDateCellValue();
						} else {
							value = ""+cell.getNumericCellValue();
						}
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						value = ""+cell.getBooleanCellValue();
						break;
					case Cell.CELL_TYPE_FORMULA:
						value = ""+cell.getCellFormula();
						break;
					default:
						value = null;
					}
					baris = baris+value;
					//System.out.println("baris="+baris);
					li.add(i+","+baris);
					boolean stop = false;
    				for(int j=4;j<300 && !stop;j++) {
    					//System.out.println("j="+j);
    					String tmp = "";
    					baris="";
    					row = sheet.getRow(j);
    					for(int k=0;k<12 && !stop;k++) {
    						//System.out.println("i,j,k="+i+","+j+","+k);
    						cell = row.getCell(k);
    						value = "";
    						switch (cell.getCellType()) {
    						case Cell.CELL_TYPE_STRING:
    							value = ""+cell.getRichStringCellValue().getString();
    							break;
    						case Cell.CELL_TYPE_NUMERIC:
    							if (DateUtil.isCellDateFormatted(cell)) {
    								value = ""+cell.getDateCellValue();
    							} else {
    								value = ""+cell.getNumericCellValue();
    							}
    							break;
    						case Cell.CELL_TYPE_BOOLEAN:
    							value = ""+cell.getBooleanCellValue();
    							break;
    						case Cell.CELL_TYPE_FORMULA:
    							//value = ""+cell.getCellFormula();
    							switch(cell.getCachedFormulaResultType()) {
    				            case Cell.CELL_TYPE_NUMERIC:
    				                value =  ""+cell.getNumericCellValue();
    				                break;
    				            case Cell.CELL_TYPE_STRING:
    				                value =  ""+cell.getRichStringCellValue();
    				                break;
    				        }
    							break;
    						default:
    							value = null;
    						}
    						//System.out.println("value="+value);
    						tmp = tmp + value+",";
    						if((k==2 || k==3 || k==4 || k==10)&&(value==null)) {
    							tmp = null;
    							stop = true;
    						}
    					}//end for k
    					if(tmp!=null) {
    						baris = baris + tmp;
    						baris = baris.replaceAll("null", " ");
    						li.add(baris);
    					}
    					//System.out.println("baris1="+baris);
    				}	
    			}//end int i
    			//System.out.println("selesai");
    			Vector v1 = new Vector();
    			ListIterator li1 = v1.listIterator();
    			Vector vtp = new Vector();//utk ngecek thsms, cuma boleh 1 thsms per-file
    			ListIterator litp = vtp.listIterator();
    			li = v.listIterator();
    			Vector vTmp = new Vector();
				ListIterator liTmp = vTmp.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,",");
    				//System.out.println("tokens = "+st.countTokens());
    				
    				if(st.countTokens()>2) {
    					String tkn1 = st.nextToken();
    					StringTokenizer stt = new StringTokenizer(tkn1,".");
    					tkn1 = stt.nextToken();
    					String tkn2 = st.nextToken();
    					stt = new StringTokenizer(tkn2,".");
    					tkn2 = stt.nextToken();
    					String tkn3 = st.nextToken();
    					stt = new StringTokenizer(tkn3,".");
    					tkn3 = stt.nextToken();
    					String tkn4 = st.nextToken();
    					stt = new StringTokenizer(tkn4,".");
    					tkn4 = stt.nextToken();
    					String tkn5 = st.nextToken();
    					stt = new StringTokenizer(tkn5,".");
    					tkn5 = stt.nextToken();
    					String tkn6 = st.nextToken();//kelompok MK
    					stt = new StringTokenizer(tkn6,".");
    					tkn6 = stt.nextToken();
    					String tkn7 = st.nextToken();//jenis mk
    					stt = new StringTokenizer(tkn7,".");
    					tkn7 = stt.nextToken();
    					String tkn8 = st.nextToken();
    					stt = new StringTokenizer(tkn8,".");
    					tkn8 = stt.nextToken();
    					String tkn9 = st.nextToken();
    					stt = new StringTokenizer(tkn9,".");
    					tkn9 = stt.nextToken();
    					String tkn10 = st.nextToken();
    					stt = new StringTokenizer(tkn10,".");
    					tkn10 = stt.nextToken();
    					String tkn11 = st.nextToken();
    					stt = new StringTokenizer(tkn11,".");
    					tkn11 = stt.nextToken();
    					String tkn12 = st.nextToken();//pengampu
    					stt = new StringTokenizer(tkn12,".");
    					tkn12 = stt.nextToken();
    					//String tkn13 = st.nextToken();
    					//stt = new StringTokenizer(tkn13,".");
    					//tkn13 = stt.nextToken();//reserved
    					liTmp.add(tkn1+","+tkn2+","+tkn5+","+tkn3+","+tkn4+","+tkn6+","+tkn7+","+tkn8+","+tkn9+","+tkn10+","+tkn11+","+tkn12);
    					//System.out.println(tkn1+","+tkn2+","+tkn5+","+tkn3+","+tkn4+","+tkn6+","+tkn7+","+tkn8+","+tkn9+","+tkn10+","+tkn11+","+tkn12+","+tkn13);
    					litp.add(tkn1);//buat cek 
    				}
    				else {
    					if(vTmp.size()>0) {
    						Collections.sort(vTmp);
    						li1.add(vTmp);
    					}
    					li1.add(brs);
    					vTmp = new Vector();
        				liTmp = vTmp.listIterator();
    				}
    			}	
    			/*
    			 * pastikan hanya satu thsms di filenya based n size vtp
    			 */
    			vtp = Tool.removeDuplicateFromVector(vtp);
    			litp = vtp.listIterator();
    			if(vtp.size()>1) {
    				//ada lebih dari satu thsms, terminated
    				msg = msg+"<br /> <h3>Ada lebih dari 1 thsms, proses terminated</h3>";
    			}
    			else {
    				/*
    				 * lolos pengecekan 1 thsms only
    				 */
    				String thsms_target =  (String)litp.next(); //thsms under work
    				/*
    				 * sort sehingga berurutan berdasar semes dan kdkmk
    				 */
    				Collections.sort(vTmp);
    				li1.add(vTmp);
    				li1 = v1.listIterator();
    			
    				/*
    				 * proses seleksi distinct mk pada tiap kurikulum
    				 * menggunakan vc
    				 * vt - menyimpan data status kurikulum (norut)A/H, untuk digunakan utk update status kurikulum
    				 */
    				Vector vt = new Vector();
    				ListIterator lit = vt.listIterator();
    				Vector vc = new Vector();
    				ListIterator lic = vc.listIterator();
    				
    				while(li1.hasNext()) {
    					String brs = (String)li1.next();
    					StringTokenizer st = new StringTokenizer(brs,",");
    					String nomokur = st.nextToken();
    					String statKur = st.nextToken();
    					if(statKur.equalsIgnoreCase("aktif")) {
    						statKur = nomokur+"A";
    						
    					}
    					else {
    						if(statKur.equalsIgnoreCase("non-aktif")) {
    							statKur = nomokur+"N";
    						}
    					}
    					lit.add(statKur);
    					v = (Vector)li1.next();
    					li = v.listIterator();
    					while(li.hasNext()) {
    						String baris = (String)li.next();
    						st = new StringTokenizer(baris,",");
    						String thsms = st.nextToken();
    						kdpst = st.nextToken();
    						String smsmk = st.nextToken();
    						String kdkmk = st.nextToken();
    						StringTokenizer stt = new StringTokenizer(kdkmk);
    						kdkmk = "";
    						while(stt.hasMoreTokens()) {
    							kdkmk = kdkmk+stt.nextToken();
    							if(stt.hasMoreTokens()) {
    								kdkmk = kdkmk+" ";
    							}
    						}
    						String nakmk = st.nextToken();
    						stt = new StringTokenizer(nakmk);
    						nakmk = "";
    						while(stt.hasMoreTokens()) {
    							nakmk = nakmk+stt.nextToken();
    							if(stt.hasMoreTokens()) {
    								nakmk = nakmk+" ";
    							}
    						}
    						String kdkel = st.nextToken();
    						String kdwpl = st.nextToken();
    						String skstm = st.nextToken();
    						String skspr = st.nextToken();
    						String skslp = st.nextToken();
    						String sksmk = st.nextToken();
    						String nodos = st.nextToken();
    						//String tkndos = st.nextToken();
    						/*
    						 * variable2 utk menentukan distinct
    						 */
    						//lic.add(kdkmk+" "+sksmk+" "+smsmk+" "+nakmk);
    						lic.add(smsmk+" "+kdkmk+" "+sksmk+" "+nakmk);
    					}
    				}	
    				/*
    				 * hapus prev rec di tbkmk
    				 */
    				UpdateDb udb = new UpdateDb();
    				udb.deletePrevRecTbkmk(thsms_target, kdpst);
    				msg = msg + "<br/>Menghapus rekord tbkmk untuk prodi="+kdpst+" dan thsms="+thsms_target;
    					
    				/*
    				 * insert init tbkmk - distinct thsms kdpst kdkmk nakmk ke tbkmk
    				 */
    				vc = Tool.removeDuplicateFromVector(vc);
    				udb.insertInitTbkmk(thsms_target,kdpst,vc);
    				msg = msg + "<br/>Insert rekord tbkmk baru untuk prodi="+kdpst+" dan thsms="+thsms_target;
    				/*
    				 * update status mk hapus
    				 */
    				msg = msg + "<br/>Cek Mata Kuliah yang Aktif / Hapus";
    				//System.out.println("mulai hapus");
    				li1 = v1.listIterator();
    				while(li1.hasNext()) {
    					String baris1=(String)li1.next();
    					Vector vtmp = (Vector)li1.next();
    					//ListIterator litmp = vtmp.listIterator();
    					StringTokenizer st = new StringTokenizer(baris1,",");
    					String nokur = st.nextToken();
    					String status = st.nextToken();
    					if(status.equalsIgnoreCase("NON-AKTIF")) {
    						/*
    						 * proses status hapus
    						 */
    						udb.setStatusMkTo(vtmp, "H");
    					}
    				}
    				/*
    				 * update status mk aktif
    				*/
    				msg = msg + "<br/>Cek Mata Kuliah yang Aktif / Hapus";
    				//System.out.println("mulai aktif");
    				li1 = v1.listIterator();
    				while(li1.hasNext()) {
    					String baris1=(String)li1.next();
    					Vector vtmp =(Vector)li1.next();
    					//ListIterator litmp = vtmp.listIterator();
    					StringTokenizer st = new StringTokenizer(baris1,",");
    					String nokur = st.nextToken();
    					String status = st.nextToken();
    					if(status.equalsIgnoreCase("AKTIF")) {
    						/*
    						 * proses status aktif
    						 */
    						udb.setStatusMkTo(vtmp, "A");
    					}
    				}
    				/*
    				 * set kur untuk masin2 kdkmk
    				 */
    				msg = msg+"<br/>Set kode Kurikulum per Mata Kuliah";
    				v = new Vector();
					li = v.listIterator();
    				//System.out.println("vc");
    				lic = vc.listIterator();
    				while(lic.hasNext()) {
    					String baris1 = (String)lic.next();
    					//System.out.println(baris1);
    					StringTokenizer st = new StringTokenizer(baris1);
    					String semes1 = st.nextToken();
    					String kdkmk1 = st.nextToken();
    					String sksmk1 = st.nextToken();
    					String nakmk1="";
    					while(st.hasMoreTokens()) {
    						nakmk1 = nakmk1 +st.nextToken();
    						if(st.hasMoreTokens()) {
    							nakmk1 = nakmk1+" ";
    						}
    					}
    					String tmp = semes1+","+sksmk1+","+kdkmk1+","+nakmk1+",";
    					//System.out.println("init tmp = "+tmp);;
    					String list_kur = "";
    					li1 = v1.listIterator();
    					while(li1.hasNext()){
    						String brs = (String)li1.next();
    						//System.out.println(brs);
    						Vector vtmp = (Vector)li1.next();
    						//System.out.println("lanjut-vsize="+vtmp.size());
        					st = new StringTokenizer(brs,",");
        					String nokur = st.nextToken();
        					String stkur = st.nextToken();
        					ListIterator litmp = vtmp.listIterator();
        					while(litmp.hasNext()) {
        						String baris = (String)litmp.next();
        						//System.out.println("baris ="+baris);
        						st = new StringTokenizer(baris,",");
        						String thsms = st.nextToken();
        						kdpst = st.nextToken();
        						String smsmk = st.nextToken();
        						String kdkmk = st.nextToken();
        						StringTokenizer stt = new StringTokenizer(kdkmk);
        						kdkmk = "";
        						while(stt.hasMoreTokens()) {
        							kdkmk = kdkmk+stt.nextToken();
        							if(stt.hasMoreTokens()) {
        								kdkmk = kdkmk+" ";
        							}
        						}
        						String nakmk = st.nextToken();
        						stt = new StringTokenizer(nakmk);
        						nakmk = "";
        						while(stt.hasMoreTokens()) {
        							nakmk = nakmk+stt.nextToken();
        							if(stt.hasMoreTokens()) {
        								nakmk = nakmk+" ";
        							}
        						}
        						String kdkel = st.nextToken();
        						String kdwpl = st.nextToken();
        						String skstm = st.nextToken();
        						String skspr = st.nextToken();
        						String skslp = st.nextToken();
        						String sksmk = st.nextToken();
        						String nodos = st.nextToken();
        						//String tkndos = st.nextToken();
        						if(semes1.equalsIgnoreCase(smsmk)&&kdkmk1.equalsIgnoreCase(kdkmk)&&sksmk1.equalsIgnoreCase(sksmk)&&nakmk1.equalsIgnoreCase(nakmk)){
        							list_kur = list_kur+nokur+" ";
        							//System.out.println(list_kur);
        						}
        					}
    					}
    					st = new StringTokenizer(list_kur);
    					list_kur = "";
    					while(st.hasMoreTokens()) {
    						list_kur = list_kur+st.nextToken();
    						if(st.hasMoreTokens()) {
    							list_kur = list_kur+" ";
    						}
    					}
    					tmp = tmp+list_kur;
    					li.add(tmp);
    					//System.out.println(tmp);
    				}
    				udb.setKodeKurikulumPerMk(v,thsms_target,kdpst);
    				/*
    				 * proses kurikulum status (aktif,non-aktif)
    				 */
    				vt = Tool.removeDuplicateFromVector(vt);
    				udb.setKurikulumSatus(vt,thsms_target,kdpst);
    				/*
    				 * update kelompok dan jenis mata kuliah dan pengampu
    				 */
    				//System.out.println("nue");
    				Vector v_nokur_stkur_sms_kdkmk_nakmk_sks_kodekelompok_keterkelompok_kodejenis_keterjenis_tkn_dosen = new Vector();
    				ListIterator liv = v_nokur_stkur_sms_kdkmk_nakmk_sks_kodekelompok_keterkelompok_kodejenis_keterjenis_tkn_dosen.listIterator();
    				li1 = v1.listIterator();
    				while(li1.hasNext()) {
    					String baris = (String)li1.next();
    					liv.add(baris);
    					Vector v2 = (Vector)li1.next();
    					//System.out.println(baris+" v size ="+v2.size());
    					ListIterator li2 = v2.listIterator();
    					String nokur=null,stkur=null;
    					String kode_kelompok = null;
    					String keter_kelompok = null;
    					String kode_jenis = null;
    					String keter_jenis = null;
    					StringTokenizer st = new StringTokenizer(baris,",");
    					nokur = st.nextToken();
    					stkur = st.nextToken();
    					while(li2.hasNext()) {
    						String brs = (String) li2.next();
    						st = new StringTokenizer(brs,",");
    	    		//		//System.out.println("brs>2 = "+brs);
    	    				String tkn1 = st.nextToken();
    	    				String tkn2 = st.nextToken();
    	    				String tkn3 = st.nextToken();
    	    				String tkn4 = st.nextToken();
    	    				String tkn5 = st.nextToken();
    	    				String tkn6 = st.nextToken();//kelompok MK
    	    				kode_kelompok = null;
    	    				keter_kelompok = null;
    	    				StringTokenizer stp = new StringTokenizer(tkn6,"_");
    	    				if(stp!=null && stp.countTokens()==2) {
    	    					kode_kelompok = stp.nextToken();
        	    				keter_kelompok = stp.nextToken();
    	    				}
    	    				
    	    				String tkn7 = st.nextToken();//jenis mk
    	    				kode_jenis = null;
    	    				keter_jenis = null;
    	    				stp = new StringTokenizer(tkn7,"_");
    	    				if(stp!=null && stp.countTokens()==2) {
    	    					kode_jenis = stp.nextToken();
        	    				keter_jenis = stp.nextToken();
    	    				}
    	    					
    	    				String tkn8 = st.nextToken();
    	    				String tkn9 = st.nextToken();
    	    				String tkn10 = st.nextToken();
    	    				String tkn11 = st.nextToken();
    	    				String tkn12 = st.nextToken();//pengampu
    	    				//liv.add();
    	    				//String tkn13 = st.nextToken();
    	    				//stt = new StringTokenizer(tkn13,".");
    	    				//tkn13 = stt.nextToken();//reserved
    	    				//liTmp.add(tkn1+","+tkn2+","+tkn5+","+tkn3+","+tkn4+","+tkn6+","+tkn7+","+tkn8+","+tkn9+","+tkn10+","+tkn11+","+tkn12);
    	    				//System.out.println(tkn1+","+tkn2+","+tkn5+","+tkn3+","+tkn4+","+tkn6+","+tkn7+","+tkn8+","+tkn9+","+tkn10+","+tkn11+","+tkn12+","+tkn13);
    	    				//litp.add(tkn1);//buat cek 
    	    				//liv.add(nokur+","+stkur+","+sms+","+kdkmk+","+nakmk+","+sks+","+kode_kelompok+","+keter_kelompok+","+kode_jenis+","+keter_jenis+","+tkn_dosen);
    	    				liv.add(nokur+","+stkur+","+tkn3+","+tkn4+","+tkn5+","+tkn11+","+kode_kelompok+","+keter_kelompok+","+kode_jenis+","+keter_jenis+","+tkn12);
    	    				
    					}
    				}
    				liv = v_nokur_stkur_sms_kdkmk_nakmk_sks_kodekelompok_keterkelompok_kodejenis_keterjenis_tkn_dosen.listIterator();
    				udb.setPengampuKodeDanJenisMataKuliah(v_nokur_stkur_sms_kdkmk_nakmk_sks_kodekelompok_keterkelompok_kodejenis_keterjenis_tkn_dosen,thsms_target,kdpst);
    			}//end lolos 1 thsms	
    		}
    		catch (Exception e) {
    			//System.out.println("poi err "+e);
    			msg = msg+"<br /> ada error ngga jelas";
    		}
    	}
    	else {
    		//System.out.println("excel missing");
    		msg = msg+"<br /> file "+fileName+" tidak ditemukan";
    	}
    	return msg;
    }
    
    public String prosesUploadFile(String fileName) {
    	String msg = "";
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	StringTokenizer st = new StringTokenizer(fileName,"-");
    	String kdpst = st.nextToken();
    	kdpst = kdpst.substring(kdpst.length()-5,kdpst.length());
    	if(fileName.startsWith("form_kurikulum")) {
    		msg = msg + prosesFormKurikulum(fileName, kdpst, msg, v);
    	}
    	return msg;
    }
    
    public String getFormKurikulum(String kdpst, String keter)  {
    	//get aktif thsms
    	SearchDb sdb = new SearchDb();
    	String thsms = sdb.getThsmsAktif();
    	StringTokenizer st = new StringTokenizer(keter);
    	keter = "";
    	while(st.hasMoreTokens()) {
    		keter = keter+st.nextToken();
    		if(st.hasMoreTokens()) {
    			keter = keter+"_";
    		}
    	}
    	
    	java.io.File file = new File(Constants.getMasterFormKurikulumFile());
    	//System.out.println("out file = "+Constants.getTmpFile()+"/form_kurikulum_"+kdpst+".xlsx");
    	java.io.File outFile = new File(Constants.getTmpFile()+"/form_kurikulum_"+keter+"_"+kdpst+".xlsx");
    	String namafile = Constants.getTmpFile()+"/form_kurikulum_"+keter+"_"+kdpst+".xlsx";
    	//System.out.println("nama file ="+Constants.getTmpFile()+"/form_kurikulum_"+keter+"_"+kdpst+".xlsx");
    	if(file.exists()) {
    		try {
    			InputStream inp = new FileInputStream(file);
        	    //InputStream inp = new FileInputStream("workbook.xlsx");
    			Workbook wb = WorkbookFactory.create(inp);
        	    Sheet sheet = wb.getSheetAt(0);
        	    //col npm
        	    for(int i=0;i<300;i++) {
        	    	Row row = sheet.getRow(i+2);
        	    	Cell cell = row.getCell(0);
        	    	if (cell == null) {
        	    		cell = row.createCell(0);
        	    	}    
        	    	cell.setCellType(Cell.CELL_TYPE_STRING);
        	    	cell.setCellValue(thsms);
        	    	
        	    	row = sheet.getRow(i+2);
        	    	cell = row.getCell(1);
        	    	if (cell == null) {
        	    		cell = row.createCell(1);
        	    	}    
        	    	cell.setCellType(Cell.CELL_TYPE_STRING);
        	    	cell.setCellValue(kdpst);
        	    
        	    }
        	    //Workbook wb = WorkbookFactory.create(inp);
        	    FileOutputStream fileOut = new FileOutputStream(outFile);
        	    wb.write(fileOut);
        	    fileOut.close();
    		}
    		catch (Exception e) {
    			//System.out.println("poi err "+e);
    		}
    	}
    	else {
    		//System.out.println("excel missing");
    	}
    	return namafile;
    }
    
    public void prepMasterKuitansiPembayaranMhs(String npm,String nim,String nmm,String kuiid,String norut,String tgkui,String tgtrs,String keter,String payee,String amont,String pymtp,String noacc,String opnpm,String opnmm,String setor,String nonpm,String voidd,String dbschema) {
    	java.io.File file = new File(Constants.getMasterKuiFile());
    	//System.out.println("out file = "+Constants.getTmpFile()+"/"+kuiid+".xlsx");
    	java.io.File outFile = new File(Constants.getTmpFile()+"/"+kuiid+".xlsx");
    	if(file.exists()) {
    		try {
    			//System.out.println("excel ditemeukan");
        		InputStream inp = new FileInputStream(file);
        	    //InputStream inp = new FileInputStream("workbook.xlsx");
        		//System.out.println("1");
        	    Workbook wb = WorkbookFactory.create(inp);
        	    Sheet sheet = wb.getSheetAt(0);
        	    //System.out.println("2");
        	    //col npm
        	    Row row = sheet.getRow(4);
        	    if(row == null) {
        	    	sheet.createRow(4);
        	    }
        	    Cell cell = row.getCell(5);
        	    if (cell == null) {
        	        cell = row.createCell(5);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(npm+"/"+nim);
        	    //System.out.println("3");
        	    //col nama
        	    row = sheet.getRow(5);
        	    if(row == null) {
        	    	sheet.createRow(5);
        	    }
        	    cell = row.getCell(5);
        	    if (cell == null) {
        	        cell = row.createCell(5);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(nmm.toUpperCase());
        	    //System.out.println("4");
        	  //col payee
        	    row = sheet.getRow(6);
        	    if(row == null) {
        	    	sheet.createRow(6);
        	    }
        	    cell = row.getCell(5);
        	    if (cell == null) {
        	        cell = row.createCell(5);
        	    }
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(payee.toUpperCase());
        	    //System.out.println("5");
          	    //col norut
        	    //System.out.println("norut="+norut);
        	    row = sheet.getRow(4);
        	    if(row == null) {
        	    	sheet.createRow(4);
        	    }
        	    cell = row.getCell(12);
        	    if (cell == null) {
        	        cell = row.createCell(12);
        	    }
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(norut.toUpperCase());
        	    //System.out.println("6");
          	    //col tgl trs
        	    //System.out.println("tgtrs="+tgtrs);
        	    row = sheet.getRow(6);
        	    if(row == null) {
        	    	sheet.createRow(6);
        	    }
        	    cell = row.getCell(12);
        	    if (cell == null) {
        	        cell = row.createCell(12);
        	    }
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    if(tgtrs.equalsIgnoreCase("null")) {
        	    	tgtrs = "N/A";
        	    }
        	    cell.setCellValue(tgtrs.toUpperCase());
        	    //System.out.println("7");
    			
    			//ganti dengan json style
        	    double total_pymnt = 0;
    			JSONArray joa = Getter.readJsonArrayFromUrl("/v1/pymnt/norut/"+norut);
    			//System.out.println("joa="+joa.toString());
    			int starting_row_ket = 9;
    			if(joa!=null && joa.length()>0) {
    				for(int j=0;j<joa.length();j++) {
    					JSONObject jobTmp = joa.getJSONObject(j);
    					String keter_detail = null;
    					String keter_pymnt = null;
    					if(jobTmp.has("KETER_PYMNT_DETAIL")) {
    						keter_detail = new String(jobTmp.getString("KETER_PYMNT_DETAIL"));
    					}
    					if(jobTmp.has("KETERPYMNT")) {
    						keter_pymnt = jobTmp.getString("KETERPYMNT");
    					}
    					if(keter_detail==null || Checker.isStringNullOrEmpty(keter_detail)) {
    						keter_detail = keter_pymnt;
    					}
    					String amont_pymnt =  jobTmp.getString("AMONTPYMNT");
    					total_pymnt = total_pymnt + Double.parseDouble(amont_pymnt);
    					//no ket
    		    	    row = sheet.getRow(starting_row_ket);
    		    	    if(row == null) {
    	        	    	sheet.createRow(starting_row_ket);
    	        	    }
    		    	    cell = row.getCell(0);
    		    	    if (cell == null) {
    		    	        cell = row.createCell(0);
    		    	    }
    		    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    		    	    cell.setCellValue(starting_row_ket-8+".");
    		    	  //ket
    		    	    row = sheet.getRow(starting_row_ket);
    		    	    if(row == null) {
    	        	    	sheet.createRow(starting_row_ket);
    	        	    }
    		    	    cell = row.getCell(2);
    		    	    if (cell == null) {
    		    	        cell = row.createCell(2);
    		    	    }
    		    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    		    	    cell.setCellValue(keter_detail);
    		    	    //System.out.println("keter_detail="+keter_detail);
    		      	  	//amnt
    		    	    row = sheet.getRow(starting_row_ket++);
    		    	    if(row == null) {
    	        	    	sheet.createRow(starting_row_ket++);
    	        	    }
    		    	    cell = row.getCell(12);
    		    	    if (cell == null) {
    		    	        cell = row.createCell(12);
    		    	    }
    		    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    		    	    cell.setCellValue(amont_pymnt);
    		    	    //System.out.println("amont_pymnt="+amont_pymnt);
    		    	    //System.out.println("8");
    				}
    			}
    			
    		/*
      	    //no ket
    	    row = sheet.getRow(9);
    	    cell = row.getCell(0);
    	    if (cell == null) {
    	        cell = row.createCell(0);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue("1.");
    	    //System.out.println("8");
    	  
    	  //ket
    	    row = sheet.getRow(9);
    	    cell = row.getCell(2);
    	    if (cell == null) {
    	        cell = row.createCell(2);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(keter);
    	    //System.out.println("9");
      	  	//amnt
    	    row = sheet.getRow(9);
    	    cell = row.getCell(12);
    	    if (cell == null) {
    	        cell = row.createCell(12);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(amont);
    	    //System.out.println("10");
    	      */
    	    //total
    	    row = sheet.getRow(13);
    	    if(row == null) {
    	    	sheet.createRow(13);
    	    }
    	    cell = row.getCell(12);
    	    if (cell == null) {
    	        cell = row.createCell(12);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    //cell.setCellValue(amont);total_pymnt
    	    cell.setCellValue(total_pymnt);
    	    //System.out.println("11");
    	  //tgl kui
    	    row = sheet.getRow(15);
    	    if(row == null) {
    	    	sheet.createRow(15);
    	    }
    	    cell = row.getCell(12);
    	    if (cell == null) {
    	        cell = row.createCell(12);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(tgkui);

    	    //System.out.println("12");
      	    //nama operator
    	    row = sheet.getRow(20);
    	    if(row == null) {
    	    	sheet.createRow(20);
    	    }
    	    cell = row.getCell(8);
    	    if (cell == null) {
    	        cell = row.createCell(8);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(opnmm);
    	    //System.out.println("13");
    	    // Write the output to a file
    	    FileOutputStream fileOut = new FileOutputStream(outFile);
    	    wb.write(fileOut);
    	    //System.out.println("14");
    	    fileOut.close();
    	    //System.out.println("15");
    		}
    		catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	else {
    		//System.out.println("excel missing");
    	}
    	//System.out.println("done");
    }

    
    public void prepMasterKuitansiPmbMhs(String npm,String nim,String nmm,String kuiid,String norut,String tgkui,String tgtrs,String keter,String payee,String amont,String pymtp,String noacc,String opnpm,String opnmm,String setor,String nonpm,String voidd,String dbschema) {
    	java.io.File file = new File(Constants.getMasterKuiPmbFile());
    	//System.out.println("out file = "+Constants.getTmpFile()+"/"+kuiid+".xlsx");
    	java.io.File outFile = new File(Constants.getTmpFile()+"/"+kuiid+".xlsx");
    	if(file.exists()) {
    		try {
    		//System.out.println("excel ditemeukan");
    		InputStream inp = new FileInputStream(file);
    	    //InputStream inp = new FileInputStream("workbook.xlsx");
    		//System.out.println("1");
    	    Workbook wb = WorkbookFactory.create(inp);
    	    Sheet sheet = wb.getSheetAt(0);
    	    //System.out.println("2");
    	    //col npm
    	    Row row = sheet.getRow(4);
    	    Cell cell = row.getCell(5);
    	    if (cell == null) {
    	        cell = row.createCell(5);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(npm+"/"+nim);
    	    //System.out.println("3");
    	    //col nama
    	    row = sheet.getRow(5);
    	    cell = row.getCell(5);
    	    if (cell == null) {
    	        cell = row.createCell(5);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(nmm.toUpperCase());
    	    //System.out.println("4");
    	  //col payee
    	    row = sheet.getRow(6);
    	    cell = row.getCell(5);
    	    //System.out.println("cell ="+cell);
    	    if (cell == null) {
    	        cell = row.createCell(5);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(payee.toUpperCase());
    	    //System.out.println("5");
      	    //col norut
    	    //System.out.println("norut="+norut);
    	    row = sheet.getRow(4);
    	    cell = row.getCell(12);
    	    if (cell == null) {
    	        cell = row.createCell(12);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(norut.toUpperCase());
    	    //System.out.println("6");
      	    //col tgl trs
    	    //System.out.println("tgtrs="+tgtrs);
    	    row = sheet.getRow(6);
    	    cell = row.getCell(12);
    	    if (cell == null) {
    	        cell = row.createCell(12);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    if(tgtrs.equalsIgnoreCase("null")) {
    	    	tgtrs = "N/A";
    	    }
    	    cell.setCellValue(tgtrs.toUpperCase());
    	    //System.out.println("7");
      	    //no ket
    	    row = sheet.getRow(9);
    	    cell = row.getCell(0);
    	    if (cell == null) {
    	        cell = row.createCell(0);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue("1.");
    	    //System.out.println("8");
    	  //ket
    	    row = sheet.getRow(9);
    	    cell = row.getCell(2);
    	    if (cell == null) {
    	        cell = row.createCell(2);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(keter);
    	    //System.out.println("9");
      	  	//amnt
    	    row = sheet.getRow(9);
    	    cell = row.getCell(12);
    	    if (cell == null) {
    	        cell = row.createCell(12);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(amont);
    	    //System.out.println("10");
    	    //total
    	    row = sheet.getRow(13);
    	    cell = row.getCell(12);
    	    if (cell == null) {
    	        cell = row.createCell(12);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(amont);
    	    //System.out.println("11");
    	  //tgl kui
    	    row = sheet.getRow(15);
    	    cell = row.getCell(12);
    	    if (cell == null) {
    	        cell = row.createCell(12);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(tgkui);

    	    //System.out.println("12");
      	    //nama operator
    	    row = sheet.getRow(20);
    	    cell = row.getCell(8);
    	    if (cell == null) {
    	        cell = row.createCell(8);
    	    }
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(opnmm);
    	    //System.out.println("13");
    	    // Write the output to a file
    	    FileOutputStream fileOut = new FileOutputStream(outFile);
    	    wb.write(fileOut);
    	    //System.out.println("14");
    	    fileOut.close();
    	    //System.out.println("15");
    		}
    		catch (Exception e) {
    			//System.out.println("poi err "+e);
    		}
    	}
    	else {
    		//System.out.println("excel missing");
    	}
    	//System.out.println("done");
    }
    
    

    public void prepMasterKrsMhs(String thsms,String kdpst,String npm,String nmm,Vector vKrsHist,String outNameFile) {
    	String info_kdpst = Converter.getDetailKdpst(kdpst);
    	String nmpst = "N/A";
    	String kdjen = "N/A";
    	String thn_sms = thsms.substring(0,4)+"/"+thsms.substring(4,5);
    	if(info_kdpst!=null) {
    		StringTokenizer st = new StringTokenizer(info_kdpst,"#&");
    		nmpst = st.nextToken();
    		kdjen = st.nextToken();
    		kdjen = Converter.getDetailKdjen(kdjen);
    	}	
    	java.io.File file = new File(Constants.getRootMasterKrsFile()+"/"+kdpst+"/krs.xlsx");
    	//System.out.println("out file = "+Constants.getRootMasterKrsFile()+"/"+kdpst+"/krs.xlsx");
    	//String outNameFile = "krs_"+npm;
    	java.io.File outFile = new File(Constants.getTmpFile()+"/"+outNameFile+".xlsx");
    	if(file.exists()) {
    		try {
    		//System.out.println("excel ditemeukan");
    		InputStream inp = new FileInputStream(file);
    	    //InputStream inp = new FileInputStream("workbook.xlsx");
    	    Workbook wb = WorkbookFactory.create(inp);
    	    Sheet sheet = wb.getSheetAt(0);
    	    
    	    //col nmm - D2
    	    Row row = sheet.getRow(1);
    	    Cell cell = row.getCell(3);
    	    if (cell == null) {
    	        cell = row.createCell(3);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(nmm);
    	  //col npm - N2
    	    row = sheet.getRow(1);
    	    cell = row.getCell(13);
    	    if (cell == null) {
    	        cell = row.createCell(13);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(npm);
    	    //col prodi - D3 
    	    row = sheet.getRow(2);
    	    cell = row.getCell(3);
    	    if (cell == null) {
    	        cell = row.createCell(3);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(nmpst+" ("+kdjen+")");
    	  //col prodi - N3 
    	    row = sheet.getRow(2);
    	    cell = row.getCell(13);
    	    if (cell == null) {
    	        cell = row.createCell(13);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(Converter.convertThsmsKeterOnly(thsms));
    	    ListIterator li = vKrsHist.listIterator();
    	    
    	    int i = 0;
    	   // int no_sta_pos_x = 0,kode_sta_pos_x = 0,nakmk_sta_pos_x = 0;
    	    int sta_pos_y = 6;//cuma butuh satu - kan inputnya perbaris
    	    String sksemi = "0";
    	    while(li.hasNext()) {
    	    	i++;
    	    	String brs = (String)li.next();
    	    	brs = brs.replace("#&", "`");
    	    	//System.out.println("baris="+brs);
    	    	StringTokenizer st = new StringTokenizer(brs,"`");
    	    	//System.out.println("krs baris = "+brs);
    	    	//StringTokenizer st = new StringTokenizer(brs,"#&");
    	    	String thsmsi = st.nextToken();
    	    	String kdkmki = st.nextToken(); 
    	    	String nakmki = st.nextToken(); 
    	    	String nlakhi = st.nextToken();
    	    	String boboti = st.nextToken();
    	    	String sksmki = st.nextToken();
    	    	String kelasi = st.nextToken();
    	    	sksemi = st.nextToken();
    	    	String nlipsi = st.nextToken();
    	    	String skstti = st.nextToken();
    	    	String nlipki = st.nextToken();
    	    	//no A7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(0);
        	    if (cell == null) {
        	        cell = row.createCell(0);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(i);
        	    
        	  //kode B7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(1);
        	    if (cell == null) {
        	        cell = row.createCell(1);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(kdkmki);
        	    
        	  //nakmk D7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(3);
        	    if (cell == null) {
        	        cell = row.createCell(3);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(nakmki);
        	    
        	  //sksmk H7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(7);
        	    if (cell == null) {
        	        cell = row.createCell(7);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(sksmki);
        	    
        	    sta_pos_y++;
    	    }
    	  //sksem H18
    	    row = sheet.getRow(17);
    	    cell = row.getCell(7);
    	    if (cell == null) {
    	        cell = row.createCell(7);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(sksemi);
    	    
    	  //sksem k19
    	    row = sheet.getRow(18);
    	    cell = row.getCell(10);
    	    if (cell == null) {
    	        cell = row.createCell(10);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue("JAKARTA, "+AskSystem.getCurrentTimeInLongStringFormat());
    	    // Write the output to a file
    	    FileOutputStream fileOut = new FileOutputStream(outFile);
    	    wb.write(fileOut);
    	    //System.out.println("14");
    	    fileOut.close();
    	    //System.out.println("15");
    		}
    		catch (Exception e) {
    			//System.out.println("poi err "+e);
    		}
    	}
    	else {
    		//System.out.println("excel missing");
    	}
    	//System.out.println("done");
    }
    
    public void prepEmptyExcel(Vector v, String out_file_name) {
    	if(v!=null && v.size()>0) {
    		ListIterator li = v.listIterator();
        	java.io.File master_file_empty_excel = new File(Constant.getVelueFromConstantTable("ROOT_PATH_MASTER_FILE")+"/master_empty.xlsx");

        	java.io.File outFile = new File(Constant.getVelueFromConstantTable("FOLDER_TMP")+"/"+out_file_name+".xlsx");
        	if(master_file_empty_excel.exists()) {
        		try {
        			//System.out.println("excel ditemeukan bo");
        			InputStream inp = new FileInputStream(master_file_empty_excel);
            	    //InputStream inp = new FileInputStream("workbook.xlsx");
            	    Workbook wb = WorkbookFactory.create(inp);
            	    CellStyle cellStyle = wb.createCellStyle();
            	    cellStyle.setWrapText(true);
            	    Sheet sheet = wb.getSheetAt(0);
            	    CreationHelper createHelper = wb.getCreationHelper();
            	    Row row = null;
            	    Cell cell = null;
            	    String tkn_info_coltype = (String)li.next();
            	    String tkn_info_colname = (String)li.next();
            	    StringTokenizer st_coltype = null;
            	    StringTokenizer st_colname = new StringTokenizer(tkn_info_colname,"`");
            	    //cetak baris nama kolom
            	    int baris = 0;
            	    int kolom = 0;
            	    while(st_colname.hasMoreTokens()) {
            	    	String label=st_colname.nextToken();
            	    	row = sheet.getRow(baris);
    					if(row == null) {
    						row = sheet.createRow(baris);
    					}
    					row.setRowStyle(cellStyle);
    					cell = row.getCell(kolom);
    					if (cell == null) {
    						cell = row.createCell(kolom);
    					} 
    					cell.setCellStyle(cellStyle);
    					cell.setCellType(Cell.CELL_TYPE_STRING);
    					if(Checker.isStringNullOrEmpty(label)) {
    						cell.setCellValue(label);
    					}
    					else {
    						cell.setCellValue(label);
    					}
    					kolom++;
            	    }
            	    StringTokenizer st = null;
            	    while(li.hasNext()) {
            	    	baris++;
            	    	kolom = 0;
            	    	st_coltype = new StringTokenizer(tkn_info_coltype,"`");
            	    	String brs = (String)li.next();
            	    	st = new StringTokenizer(brs,"`");
            	    	while(st_coltype.hasMoreTokens()) {
            	    		String tipe = st_coltype.nextToken();
            	    		String value = st.nextToken();
            	    		//date`double`long`string`time`boolean`timestamp`
            	    		if(tipe.equalsIgnoreCase("date")) {
            	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yyyy")); 
            	    			row = sheet.getRow(baris);
            					if(row == null) {
            						row = sheet.createRow(baris);
            					}
            					row.setRowStyle(cellStyle);
            					cell = row.getCell(kolom);
            					if (cell == null) {
            						cell = row.createCell(kolom);
            					} 
            					cell.setCellStyle(cellStyle);
            					//cell.setCellType(Cell.CELL_TYPE_STRING);
            					if(Checker.isStringNullOrEmpty(value)) {
            						cell.setCellType(Cell.CELL_TYPE_BLANK);
            					}
            					else {
            						cell.setCellValue(java.sql.Date.valueOf(value));
            						cell.setCellStyle(cellStyle);
            					}
            	    		}
            	    		else if(tipe.equalsIgnoreCase("double")||tipe.equalsIgnoreCase("long")) {
            	    			row = sheet.getRow(baris);
    							if(row == null) {
    								row = sheet.createRow(baris);
    							}
    							row.setRowStyle(cellStyle);
    							cell = row.getCell(kolom);
    							if (cell == null) {
    								cell = row.createCell(kolom);
    							}    
    							cell.setCellStyle(cellStyle);
    							cell.setCellType(Cell.CELL_TYPE_NUMERIC);
    							if(Checker.isStringNullOrEmpty(value)) {
    								cell.setCellType(Cell.CELL_TYPE_BLANK);
    							}
    							else {
    								cell.setCellValue(value);
    							}	
            	    		}
            	    		else if(tipe.equalsIgnoreCase("string")) {
            	    			row = sheet.getRow(baris);
    							if(row == null) {
    								row = sheet.createRow(baris);
    							}
    							row.setRowStyle(cellStyle);
    							cell = row.getCell(kolom);
    							if (cell == null) {
    								cell = row.createCell(kolom);
    							}    
    							cell.setCellStyle(cellStyle);
    							cell.setCellType(Cell.CELL_TYPE_STRING);
    							if(Checker.isStringNullOrEmpty(value)) {
    								cell.setCellType(Cell.CELL_TYPE_BLANK);
    							}
    							else {
    								cell.setCellValue(value);
    							}	
            	    		}
            	    		else if(tipe.equalsIgnoreCase("time")) {
            	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("h:mm:ss")); 
            	    			row = sheet.getRow(baris);
            					if(row == null) {
            						row = sheet.createRow(baris);
            					}
            					row.setRowStyle(cellStyle);
            					cell = row.getCell(kolom);
            					if (cell == null) {
            						cell = row.createCell(kolom);
            					}   
            					cell.setCellStyle(cellStyle);
            					//cell.setCellType(Cell.CELL_TYPE_STRING);
            					if(Checker.isStringNullOrEmpty(value)) {
            						cell.setCellType(Cell.CELL_TYPE_BLANK);
            					}
            					else {
            						cell.setCellValue(java.sql.Date.valueOf(value));
            						cell.setCellStyle(cellStyle);
            					}
            	    		}
            	    		else if(tipe.equalsIgnoreCase("boolean")) {
            	    			row = sheet.getRow(baris);
    							if(row == null) {
    								row = sheet.createRow(baris);
    							}
    							row.setRowStyle(cellStyle);
    							cell = row.getCell(kolom);
    							if (cell == null) {
    								cell = row.createCell(kolom);
    							}  
    							cell.setCellStyle(cellStyle);
    							cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
    							if(Checker.isStringNullOrEmpty(value)) {
    								cell.setCellType(Cell.CELL_TYPE_BLANK);
    							}
    							else {
    								cell.setCellValue(value);
    							}
            	    		}
            	    		else if(tipe.equalsIgnoreCase("timestamp")) {
            	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yyyy h:mm:ss")); 
            	    			row = sheet.getRow(baris);
            					if(row == null) {
            						row = sheet.createRow(baris);
            					}
            					row.setRowStyle(cellStyle);
            					cell = row.getCell(kolom);
            					if (cell == null) {
            						cell = row.createCell(kolom);
            					}    
            					cell.setCellStyle(cellStyle);
            					//cell.setCellType(Cell.CELL_TYPE_STRING);
            					if(Checker.isStringNullOrEmpty(value)) {
            						cell.setCellType(Cell.CELL_TYPE_BLANK);
            					}
            					else {
            						cell.setCellValue(java.sql.Timestamp.valueOf(value));
            						cell.setCellStyle(cellStyle);
            					}
            	    		}
            	    		kolom++;
            	    	}
            	    }
            	    
         
            	    FileOutputStream fileOut = new FileOutputStream(outFile);
            	    wb.write(fileOut);
            	    //System.out.println("14");
            	    fileOut.close();
            	    //System.out.println("15");
        		}
        		catch (Exception e) {
        			e.printStackTrace();;
        		}
        	}
        	else {
        		//System.out.println("excel missing");
        	}    		
    	}

    	//System.out.println("done");
    }
    
    public void prepEmptyExcel(Vector v_multi_header,Vector v_multi_body, String out_file_name) {
    	//System.out.println("siap");
    	java.io.File master_file_empty_excel = new File(Constant.getVelueFromConstantTable("ROOT_PATH_MASTER_FILE")+"/master_empty.xlsx");
    	java.io.File outFile = new File(Constant.getVelueFromConstantTable("FOLDER_TMP")+"/"+out_file_name+".xlsx");
    	InputStream inp = null;
	    Workbook wb = null;
	    CellStyle cellStyle = null;
	    Sheet sheet = null;
	    CreationHelper createHelper = null;
	    int baris = 0;
	    int kolom = 0;
	    try {
    		if(v_multi_header!=null && v_multi_header.size()>1) {
        		//baris pertama adalah Strng header
        		ListIterator li = v_multi_header.listIterator();
        		while(li.hasNext()) {
        			String title_header = (String)li.next();
        			Vector v_tmp = (Vector)li.next();
        			if(v_tmp!=null && v_tmp.size()>2) {
        				ListIterator litmp = v_tmp.listIterator();
        				//java.io.File master_file_empty_excel = new File(Constant.getVelueFromConstantTable("ROOT_PATH_MASTER_FILE")+"/master_empty.xlsx");

                    	//java.io.File outFile = new File(Constant.getVelueFromConstantTable("FOLDER_TMP")+"/"+out_file_name+".xlsx");
                    	if(master_file_empty_excel.exists()) {
                    		//PRINT HEADER
                    		if(inp==null) {
                    			inp = new FileInputStream(master_file_empty_excel);
                        	    wb = WorkbookFactory.create(inp);
                        	    cellStyle = wb.createCellStyle();
                        	    cellStyle.setWrapText(true);
                        	    sheet = wb.getSheetAt(0);
                        	    createHelper = wb.getCreationHelper();
                        	    baris = 0;
                    		}	
                			
                    	    Row row = null;
                    	    Cell cell = null;
                    	    //String header_label = (String)litmp.next();
                    	    String tkn_info_coltype = (String)litmp.next();
                    	    //System.out.println("tkn_info_coltype="+tkn_info_coltype);
                    	    String tkn_info_colname = (String)litmp.next();
                    	    //System.out.println("tkn_info_colname="+tkn_info_colname);
                    	    StringTokenizer st_coltype = null;
                    	    StringTokenizer st_colname = new StringTokenizer(tkn_info_colname,"`");
                    	    //cetak baris nama kolom
                    	    //baris = 0;
                    	    kolom = 0;
                    	    while(st_colname.hasMoreTokens()) {
                    	    	String label=st_colname.nextToken();
                    	    	row = sheet.getRow(baris);
            					if(row == null) {
            						row = sheet.createRow(baris);
            					}
            					row.setRowStyle(cellStyle);
            					cell = row.getCell(kolom);
            					if (cell == null) {
            						cell = row.createCell(kolom);
            					} 
            					cell.setCellStyle(cellStyle);
            					cell.setCellType(Cell.CELL_TYPE_STRING);
            					if(Checker.isStringNullOrEmpty(label)) {
            						cell.setCellValue(label);
            					}
            					else {
            						cell.setCellValue(label);
            					}
            					kolom++;
                    	    }
                    	    StringTokenizer st = null;
                    	    while(litmp.hasNext()) {
                    	    	baris++;
                    	    	kolom = 0;
                    	    	st_coltype = new StringTokenizer(tkn_info_coltype,"`");
                    	    	String brs = (String)litmp.next();
                    	    	st = new StringTokenizer(brs,"`");
                    	    	while(st_coltype.hasMoreTokens()) {
                    	    		String tipe = st_coltype.nextToken();
                    	    		String value = st.nextToken();
                    	    		//date`double`long`string`time`boolean`timestamp`
                    	    		if(tipe.equalsIgnoreCase("date")) {
                    	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yyyy")); 
                    	    			row = sheet.getRow(baris);
                    					if(row == null) {
                    						row = sheet.createRow(baris);
                    					}
                    					row.setRowStyle(cellStyle);
                    					cell = row.getCell(kolom);
                    					if (cell == null) {
                    						cell = row.createCell(kolom);
                    					} 
                    					cell.setCellStyle(cellStyle);
                    					//cell.setCellType(Cell.CELL_TYPE_STRING);
                    					if(Checker.isStringNullOrEmpty(value)) {
                    						cell.setCellType(Cell.CELL_TYPE_BLANK);
                    					}
                    					else {
                    						cell.setCellValue(java.sql.Date.valueOf(value));
                    						cell.setCellStyle(cellStyle);
                    					}
                    	    		}
                    	    		else if(tipe.equalsIgnoreCase("double")||tipe.equalsIgnoreCase("long")) {
                    	    			row = sheet.getRow(baris);
            							if(row == null) {
            								row = sheet.createRow(baris);
            							}
            							row.setRowStyle(cellStyle);
            							cell = row.getCell(kolom);
            							if (cell == null) {
            								cell = row.createCell(kolom);
            							}    
            							cell.setCellStyle(cellStyle);
            							cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            							if(Checker.isStringNullOrEmpty(value)) {
            								cell.setCellType(Cell.CELL_TYPE_BLANK);
            							}
            							else {
            								cell.setCellValue(value);
            							}	
                    	    		}
                    	    		else if(tipe.equalsIgnoreCase("string")) {
                    	    			row = sheet.getRow(baris);
            							if(row == null) {
            								row = sheet.createRow(baris);
            							}
            							row.setRowStyle(cellStyle);
            							cell = row.getCell(kolom);
            							if (cell == null) {
            								cell = row.createCell(kolom);
            							}    
            							cell.setCellStyle(cellStyle);
            							cell.setCellType(Cell.CELL_TYPE_STRING);
            							if(Checker.isStringNullOrEmpty(value)) {
            								cell.setCellType(Cell.CELL_TYPE_BLANK);
            							}
            							else {
            								cell.setCellValue(value);
            							}	
                    	    		}
                    	    		else if(tipe.equalsIgnoreCase("time")) {
                    	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("h:mm:ss")); 
                    	    			row = sheet.getRow(baris);
                    					if(row == null) {
                    						row = sheet.createRow(baris);
                    					}
                    					row.setRowStyle(cellStyle);
                    					cell = row.getCell(kolom);
                    					if (cell == null) {
                    						cell = row.createCell(kolom);
                    					}   
                    					cell.setCellStyle(cellStyle);
                    					//cell.setCellType(Cell.CELL_TYPE_STRING);
                    					if(Checker.isStringNullOrEmpty(value)) {
                    						cell.setCellType(Cell.CELL_TYPE_BLANK);
                    					}
                    					else {
                    						cell.setCellValue(java.sql.Date.valueOf(value));
                    						cell.setCellStyle(cellStyle);
                    					}
                    	    		}
                    	    		else if(tipe.equalsIgnoreCase("boolean")) {
                    	    			row = sheet.getRow(baris);
            							if(row == null) {
            								row = sheet.createRow(baris);
            							}
            							row.setRowStyle(cellStyle);
            							cell = row.getCell(kolom);
            							if (cell == null) {
            								cell = row.createCell(kolom);
            							}  
            							cell.setCellStyle(cellStyle);
            							cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
            							if(Checker.isStringNullOrEmpty(value)) {
            								cell.setCellType(Cell.CELL_TYPE_BLANK);
            							}
            							else {
            								cell.setCellValue(value);
            							}
                    	    		}
                    	    		else if(tipe.equalsIgnoreCase("timestamp")) {
                    	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yyyy h:mm:ss")); 
                    	    			row = sheet.getRow(baris);
                    					if(row == null) {
                    						row = sheet.createRow(baris);
                    					}
                    					row.setRowStyle(cellStyle);
                    					cell = row.getCell(kolom);
                    					if (cell == null) {
                    						cell = row.createCell(kolom);
                    					}    
                    					cell.setCellStyle(cellStyle);
                    					//cell.setCellType(Cell.CELL_TYPE_STRING);
                    					if(Checker.isStringNullOrEmpty(value)) {
                    						cell.setCellType(Cell.CELL_TYPE_BLANK);
                    					}
                    					else {
                    						cell.setCellValue(java.sql.Timestamp.valueOf(value));
                    						cell.setCellStyle(cellStyle);
                    					}
                    	    		}
                    	    		kolom++;
                    	    	}
                    	    }
                    	    
                    	    //kasih jarak baris kosong antara header dgn body 
                    	    baris = baris+2;
                    	    //print body
                    	}	
        			}
        				
        		}
    		}	
    		
    		//print body
    		if(v_multi_body!=null && v_multi_body.size()>1) {
        		//baris pertama adalah Strng header
        		ListIterator li = v_multi_body.listIterator();
        		while(li.hasNext()) {
        			String title_header = (String)li.next();
        			Vector v_tmp = (Vector)li.next();
        			if(v_tmp!=null && v_tmp.size()>2) {
        				ListIterator litmp = v_tmp.listIterator();
        				//java.io.File master_file_empty_excel = new File(Constant.getVelueFromConstantTable("ROOT_PATH_MASTER_FILE")+"/master_empty.xlsx");

                    	//java.io.File outFile = new File(Constant.getVelueFromConstantTable("FOLDER_TMP")+"/"+out_file_name+".xlsx");
                    	if(master_file_empty_excel.exists()) {
                    		//PRINT HEADER
                    		if(inp==null) {
                    			inp = new FileInputStream(master_file_empty_excel);
                        	    wb = WorkbookFactory.create(inp);
                        	    cellStyle = wb.createCellStyle();
                        	    cellStyle.setWrapText(true);
                        	    sheet = wb.getSheetAt(0);
                        	    createHelper = wb.getCreationHelper();
                        	    baris = 0;
                    		}	
                			
                    	    Row row = null;
                    	    Cell cell = null;
                    	    String tkn_info_coltype = (String)litmp.next();
                    	    String tkn_info_colname = (String)litmp.next();
                    	    StringTokenizer st_coltype = null;
                    	    StringTokenizer st_colname = new StringTokenizer(tkn_info_colname,"`");
                    	    //cetak baris nama kolom
                    	    
                    	    kolom = 0;
                    	    while(st_colname.hasMoreTokens()) {
                    	    	String label=st_colname.nextToken();
                    	    	row = sheet.getRow(baris);
            					if(row == null) {
            						row = sheet.createRow(baris);
            					}
            					row.setRowStyle(cellStyle);
            					cell = row.getCell(kolom);
            					if (cell == null) {
            						cell = row.createCell(kolom);
            					} 
            					cell.setCellStyle(cellStyle);
            					cell.setCellType(Cell.CELL_TYPE_STRING);
            					if(Checker.isStringNullOrEmpty(label)) {
            						cell.setCellValue(label);
            					}
            					else {
            						cell.setCellValue(label);
            					}
            					kolom++;
                    	    }
                    	    StringTokenizer st = null;
                    	    while(litmp.hasNext()) {
                    	    	baris++;
                    	    	kolom = 0;
                    	    	st_coltype = new StringTokenizer(tkn_info_coltype,"`");
                    	    	String brs = (String)litmp.next();
                    	    	st = new StringTokenizer(brs,"`");
                    	    	while(st_coltype.hasMoreTokens()) {
                    	    		String tipe = st_coltype.nextToken();
                    	    		String value = st.nextToken();
                    	    		//date`double`long`string`time`boolean`timestamp`
                    	    		if(tipe.equalsIgnoreCase("date")) {
                    	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yyyy")); 
                    	    			row = sheet.getRow(baris);
                    					if(row == null) {
                    						row = sheet.createRow(baris);
                    					}
                    					row.setRowStyle(cellStyle);
                    					cell = row.getCell(kolom);
                    					if (cell == null) {
                    						cell = row.createCell(kolom);
                    					} 
                    					cell.setCellStyle(cellStyle);
                    					//cell.setCellType(Cell.CELL_TYPE_STRING);
                    					if(Checker.isStringNullOrEmpty(value)) {
                    						cell.setCellType(Cell.CELL_TYPE_BLANK);
                    					}
                    					else {
                    						cell.setCellValue(java.sql.Date.valueOf(value));
                    						cell.setCellStyle(cellStyle);
                    					}
                    	    		}
                    	    		else if(tipe.equalsIgnoreCase("double")||tipe.equalsIgnoreCase("long")) {
                    	    			row = sheet.getRow(baris);
            							if(row == null) {
            								row = sheet.createRow(baris);
            							}
            							row.setRowStyle(cellStyle);
            							cell = row.getCell(kolom);
            							if (cell == null) {
            								cell = row.createCell(kolom);
            							}    
            							cell.setCellStyle(cellStyle);
            							cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            							if(Checker.isStringNullOrEmpty(value)) {
            								cell.setCellType(Cell.CELL_TYPE_BLANK);
            							}
            							else {
            								cell.setCellValue(value);
            							}	
                    	    		}
                    	    		else if(tipe.equalsIgnoreCase("string")) {
                    	    			row = sheet.getRow(baris);
            							if(row == null) {
            								row = sheet.createRow(baris);
            							}
            							row.setRowStyle(cellStyle);
            							cell = row.getCell(kolom);
            							if (cell == null) {
            								cell = row.createCell(kolom);
            							}    
            							cell.setCellStyle(cellStyle);
            							cell.setCellType(Cell.CELL_TYPE_STRING);
            							if(Checker.isStringNullOrEmpty(value)) {
            								cell.setCellType(Cell.CELL_TYPE_BLANK);
            							}
            							else {
            								cell.setCellValue(value);
            							}	
                    	    		}
                    	    		else if(tipe.equalsIgnoreCase("time")) {
                    	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("h:mm:ss")); 
                    	    			row = sheet.getRow(baris);
                    					if(row == null) {
                    						row = sheet.createRow(baris);
                    					}
                    					row.setRowStyle(cellStyle);
                    					cell = row.getCell(kolom);
                    					if (cell == null) {
                    						cell = row.createCell(kolom);
                    					}   
                    					cell.setCellStyle(cellStyle);
                    					//cell.setCellType(Cell.CELL_TYPE_STRING);
                    					if(Checker.isStringNullOrEmpty(value)) {
                    						cell.setCellType(Cell.CELL_TYPE_BLANK);
                    					}
                    					else {
                    						cell.setCellValue(java.sql.Date.valueOf(value));
                    						cell.setCellStyle(cellStyle);
                    					}
                    	    		}
                    	    		else if(tipe.equalsIgnoreCase("boolean")) {
                    	    			row = sheet.getRow(baris);
            							if(row == null) {
            								row = sheet.createRow(baris);
            							}
            							row.setRowStyle(cellStyle);
            							cell = row.getCell(kolom);
            							if (cell == null) {
            								cell = row.createCell(kolom);
            							}  
            							cell.setCellStyle(cellStyle);
            							cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
            							if(Checker.isStringNullOrEmpty(value)) {
            								cell.setCellType(Cell.CELL_TYPE_BLANK);
            							}
            							else {
            								cell.setCellValue(value);
            							}
                    	    		}
                    	    		else if(tipe.equalsIgnoreCase("timestamp")) {
                    	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yyyy h:mm:ss")); 
                    	    			row = sheet.getRow(baris);
                    					if(row == null) {
                    						row = sheet.createRow(baris);
                    					}
                    					row.setRowStyle(cellStyle);
                    					cell = row.getCell(kolom);
                    					if (cell == null) {
                    						cell = row.createCell(kolom);
                    					}    
                    					cell.setCellStyle(cellStyle);
                    					//cell.setCellType(Cell.CELL_TYPE_STRING);
                    					if(Checker.isStringNullOrEmpty(value)) {
                    						cell.setCellType(Cell.CELL_TYPE_BLANK);
                    					}
                    					else {
                    						cell.setCellValue(java.sql.Timestamp.valueOf(value));
                    						cell.setCellStyle(cellStyle);
                    					}
                    	    		}
                    	    		kolom++;
                    	    	}
                    	    }
                    	    
                    	    //kasih jarak baris kosong antara header dgn body 
                    	    baris = baris+2;
                    	    //print body
                    	}	
        			}
        				
        		}
    		}
    		FileOutputStream fileOut = new FileOutputStream(outFile);
     	    wb.write(fileOut);
     	    //System.out.println("14");
     	    fileOut.close();
    	}
		catch (Exception e) {
			e.printStackTrace();;
		}	
    }
    
    
    public void prepEmptyExcel_old(Vector v_header,Vector v_body, String out_file_name) {
    	//System.out.println("siap");
    	if(v_header!=null && v_header.size()>0) {
    		ListIterator li = v_header.listIterator();
        	java.io.File master_file_empty_excel = new File(Constant.getVelueFromConstantTable("ROOT_PATH_MASTER_FILE")+"/master_empty.xlsx");

        	java.io.File outFile = new File(Constant.getVelueFromConstantTable("FOLDER_TMP")+"/"+out_file_name+".xlsx");
        	if(master_file_empty_excel.exists()) {
        		try {
        			//PRINT HEADER
        			InputStream inp = new FileInputStream(master_file_empty_excel);
            	    //InputStream inp = new FileInputStream("workbook.xlsx");
            	    Workbook wb = WorkbookFactory.create(inp);
            	    CellStyle cellStyle = wb.createCellStyle();
            	    cellStyle.setWrapText(true);
            	    Sheet sheet = wb.getSheetAt(0);
            	    CreationHelper createHelper = wb.getCreationHelper();
            	    Row row = null;
            	    Cell cell = null;
            	    String tkn_info_coltype = (String)li.next();
            	    String tkn_info_colname = (String)li.next();
            	    StringTokenizer st_coltype = null;
            	    StringTokenizer st_colname = new StringTokenizer(tkn_info_colname,"`");
            	    //cetak baris nama kolom
            	    int baris = 0;
            	    int kolom = 0;
            	    while(st_colname.hasMoreTokens()) {
            	    	String label=st_colname.nextToken();
            	    	row = sheet.getRow(baris);
    					if(row == null) {
    						row = sheet.createRow(baris);
    					}
    					row.setRowStyle(cellStyle);
    					cell = row.getCell(kolom);
    					if (cell == null) {
    						cell = row.createCell(kolom);
    					} 
    					cell.setCellStyle(cellStyle);
    					cell.setCellType(Cell.CELL_TYPE_STRING);
    					if(Checker.isStringNullOrEmpty(label)) {
    						cell.setCellValue(label);
    					}
    					else {
    						cell.setCellValue(label);
    					}
    					kolom++;
            	    }
            	    StringTokenizer st = null;
            	    while(li.hasNext()) {
            	    	baris++;
            	    	kolom = 0;
            	    	st_coltype = new StringTokenizer(tkn_info_coltype,"`");
            	    	String brs = (String)li.next();
            	    	st = new StringTokenizer(brs,"`");
            	    	while(st_coltype.hasMoreTokens()) {
            	    		String tipe = st_coltype.nextToken();
            	    		String value = st.nextToken();
            	    		//date`double`long`string`time`boolean`timestamp`
            	    		if(tipe.equalsIgnoreCase("date")) {
            	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yyyy")); 
            	    			row = sheet.getRow(baris);
            					if(row == null) {
            						row = sheet.createRow(baris);
            					}
            					row.setRowStyle(cellStyle);
            					cell = row.getCell(kolom);
            					if (cell == null) {
            						cell = row.createCell(kolom);
            					} 
            					cell.setCellStyle(cellStyle);
            					//cell.setCellType(Cell.CELL_TYPE_STRING);
            					if(Checker.isStringNullOrEmpty(value)) {
            						cell.setCellType(Cell.CELL_TYPE_BLANK);
            					}
            					else {
            						cell.setCellValue(java.sql.Date.valueOf(value));
            						cell.setCellStyle(cellStyle);
            					}
            	    		}
            	    		else if(tipe.equalsIgnoreCase("double")||tipe.equalsIgnoreCase("long")) {
            	    			row = sheet.getRow(baris);
    							if(row == null) {
    								row = sheet.createRow(baris);
    							}
    							row.setRowStyle(cellStyle);
    							cell = row.getCell(kolom);
    							if (cell == null) {
    								cell = row.createCell(kolom);
    							}    
    							cell.setCellStyle(cellStyle);
    							cell.setCellType(Cell.CELL_TYPE_NUMERIC);
    							if(Checker.isStringNullOrEmpty(value)) {
    								cell.setCellType(Cell.CELL_TYPE_BLANK);
    							}
    							else {
    								cell.setCellValue(value);
    							}	
            	    		}
            	    		else if(tipe.equalsIgnoreCase("string")) {
            	    			row = sheet.getRow(baris);
    							if(row == null) {
    								row = sheet.createRow(baris);
    							}
    							row.setRowStyle(cellStyle);
    							cell = row.getCell(kolom);
    							if (cell == null) {
    								cell = row.createCell(kolom);
    							}    
    							cell.setCellStyle(cellStyle);
    							cell.setCellType(Cell.CELL_TYPE_STRING);
    							if(Checker.isStringNullOrEmpty(value)) {
    								cell.setCellType(Cell.CELL_TYPE_BLANK);
    							}
    							else {
    								cell.setCellValue(value);
    							}	
            	    		}
            	    		else if(tipe.equalsIgnoreCase("time")) {
            	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("h:mm:ss")); 
            	    			row = sheet.getRow(baris);
            					if(row == null) {
            						row = sheet.createRow(baris);
            					}
            					row.setRowStyle(cellStyle);
            					cell = row.getCell(kolom);
            					if (cell == null) {
            						cell = row.createCell(kolom);
            					}   
            					cell.setCellStyle(cellStyle);
            					//cell.setCellType(Cell.CELL_TYPE_STRING);
            					if(Checker.isStringNullOrEmpty(value)) {
            						cell.setCellType(Cell.CELL_TYPE_BLANK);
            					}
            					else {
            						cell.setCellValue(java.sql.Date.valueOf(value));
            						cell.setCellStyle(cellStyle);
            					}
            	    		}
            	    		else if(tipe.equalsIgnoreCase("boolean")) {
            	    			row = sheet.getRow(baris);
    							if(row == null) {
    								row = sheet.createRow(baris);
    							}
    							row.setRowStyle(cellStyle);
    							cell = row.getCell(kolom);
    							if (cell == null) {
    								cell = row.createCell(kolom);
    							}  
    							cell.setCellStyle(cellStyle);
    							cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
    							if(Checker.isStringNullOrEmpty(value)) {
    								cell.setCellType(Cell.CELL_TYPE_BLANK);
    							}
    							else {
    								cell.setCellValue(value);
    							}
            	    		}
            	    		else if(tipe.equalsIgnoreCase("timestamp")) {
            	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yyyy h:mm:ss")); 
            	    			row = sheet.getRow(baris);
            					if(row == null) {
            						row = sheet.createRow(baris);
            					}
            					row.setRowStyle(cellStyle);
            					cell = row.getCell(kolom);
            					if (cell == null) {
            						cell = row.createCell(kolom);
            					}    
            					cell.setCellStyle(cellStyle);
            					//cell.setCellType(Cell.CELL_TYPE_STRING);
            					if(Checker.isStringNullOrEmpty(value)) {
            						cell.setCellType(Cell.CELL_TYPE_BLANK);
            					}
            					else {
            						cell.setCellValue(java.sql.Timestamp.valueOf(value));
            						cell.setCellStyle(cellStyle);
            					}
            	    		}
            	    		kolom++;
            	    	}
            	    }
            	    
            	    //kasih jarak baris kosong antara header dgn body 
            	    baris = baris+2;
            	    //print body
            	    if(v_body!=null && v_body.size()>0) {
            	    	li = v_body.listIterator();
            	    	tkn_info_coltype = (String)li.next();
                	    tkn_info_colname = (String)li.next();
                	    st_coltype = null;
                	    st_colname = new StringTokenizer(tkn_info_colname,"`");
                	    //cetak baris nama kolom
                	    
                	    kolom = 0;
                	    while(st_colname.hasMoreTokens()) {
                	    	String label=st_colname.nextToken();
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyle);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyle);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellValue(label);
        					}
        					else {
        						cell.setCellValue(label);
        					}
        					kolom++;
                	    }
                	    st = null;
                	    while(li.hasNext()) {
                	    	baris++;
                	    	kolom = 0;
                	    	st_coltype = new StringTokenizer(tkn_info_coltype,"`");
                	    	String brs = (String)li.next();
                	    	st = new StringTokenizer(brs,"`");
                	    	while(st_coltype.hasMoreTokens()) {
                	    		String tipe = st_coltype.nextToken();
                	    		String value = st.nextToken();
                	    		//date`double`long`string`time`boolean`timestamp`
                	    		if(tipe.equalsIgnoreCase("date")) {
                	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yyyy")); 
                	    			row = sheet.getRow(baris);
                					if(row == null) {
                						row = sheet.createRow(baris);
                					}
                					row.setRowStyle(cellStyle);
                					cell = row.getCell(kolom);
                					if (cell == null) {
                						cell = row.createCell(kolom);
                					} 
                					cell.setCellStyle(cellStyle);
                					//cell.setCellType(Cell.CELL_TYPE_STRING);
                					if(Checker.isStringNullOrEmpty(value)) {
                						cell.setCellType(Cell.CELL_TYPE_BLANK);
                					}
                					else {
                						cell.setCellValue(java.sql.Date.valueOf(value));
                						cell.setCellStyle(cellStyle);
                					}
                	    		}
                	    		else if(tipe.equalsIgnoreCase("double")||tipe.equalsIgnoreCase("long")) {
                	    			row = sheet.getRow(baris);
        							if(row == null) {
        								row = sheet.createRow(baris);
        							}
        							row.setRowStyle(cellStyle);
        							cell = row.getCell(kolom);
        							if (cell == null) {
        								cell = row.createCell(kolom);
        							}    
        							cell.setCellStyle(cellStyle);
        							cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        							if(Checker.isStringNullOrEmpty(value)) {
        								cell.setCellType(Cell.CELL_TYPE_BLANK);
        							}
        							else {
        								cell.setCellValue(value);
        							}	
                	    		}
                	    		else if(tipe.equalsIgnoreCase("string")) {
                	    			row = sheet.getRow(baris);
        							if(row == null) {
        								row = sheet.createRow(baris);
        							}
        							row.setRowStyle(cellStyle);
        							cell = row.getCell(kolom);
        							if (cell == null) {
        								cell = row.createCell(kolom);
        							}    
        							cell.setCellStyle(cellStyle);
        							cell.setCellType(Cell.CELL_TYPE_STRING);
        							if(Checker.isStringNullOrEmpty(value)) {
        								cell.setCellType(Cell.CELL_TYPE_BLANK);
        							}
        							else {
        								cell.setCellValue(value);
        							}	
                	    		}
                	    		else if(tipe.equalsIgnoreCase("time")) {
                	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("h:mm:ss")); 
                	    			row = sheet.getRow(baris);
                					if(row == null) {
                						row = sheet.createRow(baris);
                					}
                					row.setRowStyle(cellStyle);
                					cell = row.getCell(kolom);
                					if (cell == null) {
                						cell = row.createCell(kolom);
                					}   
                					cell.setCellStyle(cellStyle);
                					//cell.setCellType(Cell.CELL_TYPE_STRING);
                					if(Checker.isStringNullOrEmpty(value)) {
                						cell.setCellType(Cell.CELL_TYPE_BLANK);
                					}
                					else {
                						cell.setCellValue(java.sql.Date.valueOf(value));
                						cell.setCellStyle(cellStyle);
                					}
                	    		}
                	    		else if(tipe.equalsIgnoreCase("boolean")) {
                	    			row = sheet.getRow(baris);
        							if(row == null) {
        								row = sheet.createRow(baris);
        							}
        							row.setRowStyle(cellStyle);
        							cell = row.getCell(kolom);
        							if (cell == null) {
        								cell = row.createCell(kolom);
        							}  
        							cell.setCellStyle(cellStyle);
        							cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
        							if(Checker.isStringNullOrEmpty(value)) {
        								cell.setCellType(Cell.CELL_TYPE_BLANK);
        							}
        							else {
        								cell.setCellValue(value);
        							}
                	    		}
                	    		else if(tipe.equalsIgnoreCase("timestamp")) {
                	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yyyy h:mm:ss")); 
                	    			row = sheet.getRow(baris);
                					if(row == null) {
                						row = sheet.createRow(baris);
                					}
                					row.setRowStyle(cellStyle);
                					cell = row.getCell(kolom);
                					if (cell == null) {
                						cell = row.createCell(kolom);
                					}    
                					cell.setCellStyle(cellStyle);
                					//cell.setCellType(Cell.CELL_TYPE_STRING);
                					if(Checker.isStringNullOrEmpty(value)) {
                						cell.setCellType(Cell.CELL_TYPE_BLANK);
                					}
                					else {
                						cell.setCellValue(java.sql.Timestamp.valueOf(value));
                						cell.setCellStyle(cellStyle);
                					}
                	    		}
                	    		kolom++;
                	    	}
                	    }
            	    }
         
            	    FileOutputStream fileOut = new FileOutputStream(outFile);
            	    wb.write(fileOut);
            	    //System.out.println("14");
            	    fileOut.close();
            	    //System.out.println("15");
        		}
        		catch (Exception e) {
        			e.printStackTrace();;
        		}
        	}
        	else {
        		//System.out.println("excel missing");
        	}    		
    	}

    	//System.out.println("done");
    }
    
    
    public void prepEmptyExcel_v2(Vector v, String out_file_name) {
    	if(v!=null && v.size()>0) {
    		ListIterator li = v.listIterator();
        	java.io.File master_file_empty_excel = new File(Constant.getVelueFromConstantTable("ROOT_PATH_MASTER_FILE")+"/master_empty.xlsx");

        	java.io.File outFile = new File(Constant.getVelueFromConstantTable("FOLDER_TMP")+"/"+out_file_name+".xlsx");
        	if(master_file_empty_excel.exists()) {
        		try {
        			//System.out.println("excel ditemeukan bo");
        			InputStream inp = new FileInputStream(master_file_empty_excel);
            	    //InputStream inp = new FileInputStream("workbook.xlsx");
            	    Workbook wb = WorkbookFactory.create(inp);
            	    CellStyle cellStyle = wb.createCellStyle();
            	    cellStyle.setWrapText(true);
            	    Sheet sheet = wb.getSheetAt(0);
            	    CreationHelper createHelper = wb.getCreationHelper();
            	    Row row = null;
            	    Cell cell = null;
            	    String tkn_info_coltype = (String)li.next();
            	    String tkn_info_colname = (String)li.next();
            	    String tkn_align = (String)li.next();
            		String tkn_width = (String)li.next();
            		String tabel_width = (String)li.next();
            	    StringTokenizer st_coltype = null;
            	    StringTokenizer st_colname = new StringTokenizer(tkn_info_colname,"`");
            	    //cetak baris nama kolom
            	    int baris = 0;
            	    int kolom = 0;
            	    while(st_colname.hasMoreTokens()) {
            	    	String label=st_colname.nextToken();
            	    	row = sheet.getRow(baris);
    					if(row == null) {
    						row = sheet.createRow(baris);
    					}
    					row.setRowStyle(cellStyle);
    					cell = row.getCell(kolom);
    					if (cell == null) {
    						cell = row.createCell(kolom);
    					} 
    					cell.setCellStyle(cellStyle);
    					cell.setCellType(Cell.CELL_TYPE_STRING);
    					if(Checker.isStringNullOrEmpty(label)) {
    						cell.setCellValue(label);
    					}
    					else {
    						cell.setCellValue(label);
    					}
    					kolom++;
            	    }
            	    StringTokenizer st = null;
            	    while(li.hasNext()) {
            	    	baris++;
            	    	kolom = 0;
            	    	st_coltype = new StringTokenizer(tkn_info_coltype,"`");
            	    	String brs = (String)li.next();
            	    	st = new StringTokenizer(brs,"`");
            	    	while(st_coltype.hasMoreTokens()) {
            	    		String tipe = st_coltype.nextToken();
            	    		String value = st.nextToken();
            	    		//date`double`long`string`time`boolean`timestamp`
            	    		if(tipe.equalsIgnoreCase("date")) {
            	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yyyy")); 
            	    			row = sheet.getRow(baris);
            					if(row == null) {
            						row = sheet.createRow(baris);
            					}
            					row.setRowStyle(cellStyle);
            					cell = row.getCell(kolom);
            					if (cell == null) {
            						cell = row.createCell(kolom);
            					} 
            					cell.setCellStyle(cellStyle);
            					//cell.setCellType(Cell.CELL_TYPE_STRING);
            					if(Checker.isStringNullOrEmpty(value)) {
            						cell.setCellType(Cell.CELL_TYPE_BLANK);
            					}
            					else {
            						cell.setCellValue(java.sql.Date.valueOf(value));
            						cell.setCellStyle(cellStyle);
            					}
            	    		}
            	    		else if(tipe.equalsIgnoreCase("double")||tipe.equalsIgnoreCase("long")) {
            	    			row = sheet.getRow(baris);
    							if(row == null) {
    								row = sheet.createRow(baris);
    							}
    							row.setRowStyle(cellStyle);
    							cell = row.getCell(kolom);
    							if (cell == null) {
    								cell = row.createCell(kolom);
    							}    
    							cell.setCellStyle(cellStyle);
    							cell.setCellType(Cell.CELL_TYPE_NUMERIC);
    							if(Checker.isStringNullOrEmpty(value)) {
    								cell.setCellType(Cell.CELL_TYPE_BLANK);
    							}
    							else {
    								cell.setCellValue(value);
    							}	
            	    		}
            	    		else if(tipe.equalsIgnoreCase("string")) {
            	    			row = sheet.getRow(baris);
    							if(row == null) {
    								row = sheet.createRow(baris);
    							}
    							row.setRowStyle(cellStyle);
    							cell = row.getCell(kolom);
    							if (cell == null) {
    								cell = row.createCell(kolom);
    							}    
    							cell.setCellStyle(cellStyle);
    							cell.setCellType(Cell.CELL_TYPE_STRING);
    							if(Checker.isStringNullOrEmpty(value)) {
    								cell.setCellType(Cell.CELL_TYPE_BLANK);
    							}
    							else {
    								cell.setCellValue(value);
    							}	
            	    		}
            	    		else if(tipe.equalsIgnoreCase("time")) {
            	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("h:mm:ss")); 
            	    			row = sheet.getRow(baris);
            					if(row == null) {
            						row = sheet.createRow(baris);
            					}
            					row.setRowStyle(cellStyle);
            					cell = row.getCell(kolom);
            					if (cell == null) {
            						cell = row.createCell(kolom);
            					}   
            					cell.setCellStyle(cellStyle);
            					//cell.setCellType(Cell.CELL_TYPE_STRING);
            					if(Checker.isStringNullOrEmpty(value)) {
            						cell.setCellType(Cell.CELL_TYPE_BLANK);
            					}
            					else {
            						cell.setCellValue(java.sql.Date.valueOf(value));
            						cell.setCellStyle(cellStyle);
            					}
            	    		}
            	    		else if(tipe.equalsIgnoreCase("boolean")) {
            	    			row = sheet.getRow(baris);
    							if(row == null) {
    								row = sheet.createRow(baris);
    							}
    							row.setRowStyle(cellStyle);
    							cell = row.getCell(kolom);
    							if (cell == null) {
    								cell = row.createCell(kolom);
    							}  
    							cell.setCellStyle(cellStyle);
    							cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
    							if(Checker.isStringNullOrEmpty(value)) {
    								cell.setCellType(Cell.CELL_TYPE_BLANK);
    							}
    							else {
    								cell.setCellValue(value);
    							}
            	    		}
            	    		else if(tipe.equalsIgnoreCase("timestamp")) {
            	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yyyy h:mm:ss")); 
            	    			row = sheet.getRow(baris);
            					if(row == null) {
            						row = sheet.createRow(baris);
            					}
            					row.setRowStyle(cellStyle);
            					cell = row.getCell(kolom);
            					if (cell == null) {
            						cell = row.createCell(kolom);
            					}    
            					cell.setCellStyle(cellStyle);
            					//cell.setCellType(Cell.CELL_TYPE_STRING);
            					if(Checker.isStringNullOrEmpty(value)) {
            						cell.setCellType(Cell.CELL_TYPE_BLANK);
            					}
            					else {
            						cell.setCellValue(java.sql.Timestamp.valueOf(value));
            						cell.setCellStyle(cellStyle);
            					}
            	    		}
            	    		kolom++;
            	    	}
            	    }
            	    
         
            	    FileOutputStream fileOut = new FileOutputStream(outFile);
            	    wb.write(fileOut);
            	    //System.out.println("14");
            	    fileOut.close();
            	    //System.out.println("15");
        		}
        		catch (Exception e) {
        			e.printStackTrace();;
        		}
        	}
        	else {
        		//System.out.println("excel missing");
        	}    		
    	}

    	//System.out.println("done");
    }
    
  
    public void prepEmptyExcel_v3(Vector v, String out_file_name) {
    	if(v!=null && v.size()>0) {
    		ListIterator li = v.listIterator();
        	java.io.File master_file_empty_excel = new File(Constant.getVelueFromConstantTable("ROOT_PATH_MASTER_FILE")+"/master_empty.xlsx");

        	java.io.File outFile = new File(Constant.getVelueFromConstantTable("FOLDER_TMP")+"/"+out_file_name+".xlsx");
        	if(master_file_empty_excel.exists()) {
        		try {
        			//System.out.println("excel ditemeukan bo");
        			InputStream inp = new FileInputStream(master_file_empty_excel);
            	    //InputStream inp = new FileInputStream("workbook.xlsx");
            	    Workbook wb = WorkbookFactory.create(inp);
            	    CellStyle cellStyle = wb.createCellStyle();
            	    cellStyle.setWrapText(true);
            	    Sheet sheet = wb.getSheetAt(0);
            	    CreationHelper createHelper = wb.getCreationHelper();
            	    Row row = null;
            	    Cell cell = null;
            	    String tkn_info_coltype = (String)li.next();
            	    
            	    String tkn_align = (String)li.next();
            		String tkn_width = (String)li.next();
            		String tabel_width = (String)li.next();
            		String tkn_info_colname = (String)li.next();
            	    StringTokenizer st_coltype = null;
            	    StringTokenizer st_colname = new StringTokenizer(tkn_info_colname,"`");
            	    //cetak baris nama kolom
            	    int baris = 0;
            	    int kolom = 0;
            	    while(st_colname.hasMoreTokens()) {
            	    	String label=st_colname.nextToken();
            	    	row = sheet.getRow(baris);
    					if(row == null) {
    						row = sheet.createRow(baris);
    					}
    					row.setRowStyle(cellStyle);
    					cell = row.getCell(kolom);
    					if (cell == null) {
    						cell = row.createCell(kolom);
    					} 
    					cell.setCellStyle(cellStyle);
    					cell.setCellType(Cell.CELL_TYPE_STRING);
    					if(Checker.isStringNullOrEmpty(label)) {
    						cell.setCellValue(label);
    					}
    					else {
    						cell.setCellValue(label);
    					}
    					kolom++;
            	    }
            	    StringTokenizer st = null;
            	    while(li.hasNext()) {
            	    	baris++;
            	    	kolom = 0;
            	    	st_coltype = new StringTokenizer(tkn_info_coltype,"`");
            	    	String brs = (String)li.next();
            	    	st = new StringTokenizer(brs,"`");
            	    	while(st_coltype.hasMoreTokens()) {
            	    		String tipe = st_coltype.nextToken();
            	    		String value = st.nextToken();
            	    		//date`double`long`string`time`boolean`timestamp`
            	    		if(tipe.equalsIgnoreCase("date")) {
            	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yyyy")); 
            	    			row = sheet.getRow(baris);
            					if(row == null) {
            						row = sheet.createRow(baris);
            					}
            					row.setRowStyle(cellStyle);
            					cell = row.getCell(kolom);
            					if (cell == null) {
            						cell = row.createCell(kolom);
            					} 
            					cell.setCellStyle(cellStyle);
            					//cell.setCellType(Cell.CELL_TYPE_STRING);
            					if(Checker.isStringNullOrEmpty(value)) {
            						cell.setCellType(Cell.CELL_TYPE_BLANK);
            					}
            					else {
            						cell.setCellValue(java.sql.Date.valueOf(value));
            						cell.setCellStyle(cellStyle);
            					}
            	    		}
            	    		else if(tipe.equalsIgnoreCase("double")||tipe.equalsIgnoreCase("long")) {
            	    			row = sheet.getRow(baris);
    							if(row == null) {
    								row = sheet.createRow(baris);
    							}
    							row.setRowStyle(cellStyle);
    							cell = row.getCell(kolom);
    							if (cell == null) {
    								cell = row.createCell(kolom);
    							}    
    							cell.setCellStyle(cellStyle);
    							cell.setCellType(Cell.CELL_TYPE_NUMERIC);
    							if(Checker.isStringNullOrEmpty(value)) {
    								cell.setCellType(Cell.CELL_TYPE_BLANK);
    							}
    							else {
    								cell.setCellValue(value);
    							}	
            	    		}
            	    		else if(tipe.equalsIgnoreCase("string")) {
            	    			row = sheet.getRow(baris);
    							if(row == null) {
    								row = sheet.createRow(baris);
    							}
    							row.setRowStyle(cellStyle);
    							cell = row.getCell(kolom);
    							if (cell == null) {
    								cell = row.createCell(kolom);
    							}    
    							cell.setCellStyle(cellStyle);
    							cell.setCellType(Cell.CELL_TYPE_STRING);
    							if(Checker.isStringNullOrEmpty(value)) {
    								cell.setCellType(Cell.CELL_TYPE_BLANK);
    							}
    							else {
    								cell.setCellValue(value);
    							}	
            	    		}
            	    		else if(tipe.equalsIgnoreCase("time")) {
            	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("h:mm:ss")); 
            	    			row = sheet.getRow(baris);
            					if(row == null) {
            						row = sheet.createRow(baris);
            					}
            					row.setRowStyle(cellStyle);
            					cell = row.getCell(kolom);
            					if (cell == null) {
            						cell = row.createCell(kolom);
            					}   
            					cell.setCellStyle(cellStyle);
            					//cell.setCellType(Cell.CELL_TYPE_STRING);
            					if(Checker.isStringNullOrEmpty(value)) {
            						cell.setCellType(Cell.CELL_TYPE_BLANK);
            					}
            					else {
            						cell.setCellValue(java.sql.Date.valueOf(value));
            						cell.setCellStyle(cellStyle);
            					}
            	    		}
            	    		else if(tipe.equalsIgnoreCase("boolean")) {
            	    			row = sheet.getRow(baris);
    							if(row == null) {
    								row = sheet.createRow(baris);
    							}
    							row.setRowStyle(cellStyle);
    							cell = row.getCell(kolom);
    							if (cell == null) {
    								cell = row.createCell(kolom);
    							}  
    							cell.setCellStyle(cellStyle);
    							cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
    							if(Checker.isStringNullOrEmpty(value)) {
    								cell.setCellType(Cell.CELL_TYPE_BLANK);
    							}
    							else {
    								cell.setCellValue(value);
    							}
            	    		}
            	    		else if(tipe.equalsIgnoreCase("timestamp")) {
            	    			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yyyy h:mm:ss")); 
            	    			row = sheet.getRow(baris);
            					if(row == null) {
            						row = sheet.createRow(baris);
            					}
            					row.setRowStyle(cellStyle);
            					cell = row.getCell(kolom);
            					if (cell == null) {
            						cell = row.createCell(kolom);
            					}    
            					cell.setCellStyle(cellStyle);
            					//cell.setCellType(Cell.CELL_TYPE_STRING);
            					if(Checker.isStringNullOrEmpty(value)) {
            						cell.setCellType(Cell.CELL_TYPE_BLANK);
            					}
            					else {
            						cell.setCellValue(java.sql.Timestamp.valueOf(value));
            						cell.setCellStyle(cellStyle);
            					}
            	    		}
            	    		kolom++;
            	    	}
            	    }
            	    
         
            	    FileOutputStream fileOut = new FileOutputStream(outFile);
            	    wb.write(fileOut);
            	    //System.out.println("14");
            	    fileOut.close();
            	    //System.out.println("15");
        		}
        		catch (Exception e) {
        			e.printStackTrace();;
        		}
        	}
        	else {
        		//System.out.println("excel missing");
        	}    		
    	}

    	//System.out.println("done");
    }
    
    
    public void prepManualPenetapan(Vector v, String out_file_name) {
    	String nm_pt=Constant.getNama_pt();
    	if(v!=null && v.size()>0) {
    		ListIterator li = v.listIterator();
        	java.io.File master_file_empty_excel = new File(Constant.getVelueFromConstantTable("ROOT_PATH_MASTER_FILE_CG2")+"/tamplete_manual_std.xlsx");
        	System.out.println("excel master_file_empty_excel="+master_file_empty_excel);
        	java.io.File outFile = new File(Constant.getVelueFromConstantTable("FOLDER_TMP_CG2")+"/"+out_file_name+".xlsx");
        	if(master_file_empty_excel.exists()) {
        		try {
        			System.out.println("excel ditemeukan bo");
        			InputStream inp = new FileInputStream(master_file_empty_excel);
            	    //InputStream inp = new FileInputStream("workbook.xlsx");
            	    Workbook wb = WorkbookFactory.create(inp);
        			//XSSFWorkbook wb = new XSSFWorkbook();
            	    CellStyle cellStyleCenterMiddle = wb.createCellStyle();
            	    cellStyleCenterMiddle.setWrapText(true);
            	    cellStyleCenterMiddle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            	    cellStyleCenterMiddle.setAlignment(CellStyle.ALIGN_CENTER);
            	    Font font= wb.createFont();
            	    font.setFontHeightInPoints((short)11);
                    font.setFontName("Times New Roman");
                    cellStyleCenterMiddle.setFont(font);
                    cellStyleCenterMiddle.setBorderTop(XSSFCellStyle.BORDER_THIN);
                    cellStyleCenterMiddle.setBorderRight(XSSFCellStyle.BORDER_THIN);
                    cellStyleCenterMiddle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
                    cellStyleCenterMiddle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
                    
            	    CellStyle cellStyleLeftMiddle = wb.createCellStyle();
            	    cellStyleLeftMiddle.setWrapText(true);
            	    cellStyleLeftMiddle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            	    cellStyleLeftMiddle.setAlignment(CellStyle.ALIGN_LEFT);
            	    font= wb.createFont();
            	    font.setFontHeightInPoints((short)11);
                    font.setFontName("Times New Roman");
                    cellStyleLeftMiddle.setFont(font);
                    cellStyleLeftMiddle.setBorderTop(XSSFCellStyle.BORDER_THIN);
                    cellStyleLeftMiddle.setBorderRight(XSSFCellStyle.BORDER_THIN);
                    cellStyleLeftMiddle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
                    cellStyleLeftMiddle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            	    
            	    CellStyle cellStyleCenterTop = wb.createCellStyle();
            	    cellStyleCenterTop.setWrapText(true);
            	    cellStyleCenterTop.setAlignment(CellStyle.ALIGN_CENTER);
            	    cellStyleCenterTop.setVerticalAlignment(CellStyle.VERTICAL_TOP);
            	    font= wb.createFont();
            	    font.setFontHeightInPoints((short)30);
                    font.setFontName("Times New Roman");
                    font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                    cellStyleCenterTop.setFont(font);
                    
                    CellStyle cellStyleCenterBottom = wb.createCellStyle();
                    cellStyleCenterBottom.setWrapText(true);
                    cellStyleCenterBottom.setAlignment(CellStyle.ALIGN_CENTER);
                    cellStyleCenterBottom.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
            	    font= wb.createFont();
            	    font.setFontHeightInPoints((short)30);
                    font.setFontName("Times New Roman");
                    font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                    cellStyleCenterBottom.setFont(font);
            	    
            	    CellStyle cellStyle = wb.createCellStyle();
            	    cellStyle.setWrapText(true);
            	    Sheet sheet = wb.getSheetAt(0);
            	    CreationHelper createHelper = wb.getCreationHelper();
            	    Row row = null;
            	    Cell cell = null;
            	    String tipe = (String)li.next();
            	    String nm_std = (String)li.next();
            	    String no_dok = (String)li.next();
            	    String tgl_rumus = (String)li.next();
            	    String tgl_cek = (String)li.next();
            	    String tgl_stuju = (String)li.next();
            	    String tgl_tetap = (String)li.next();
            	    String tgl_kendali = (String)li.next();
            	    String visi_pt = (String)li.next();
            	    String misi_pt = (String)li.next();
            	    String tujuan_pt = (String)li.next();
            	    String nilai_pt = (String)li.next();
            	    String brs = (String)li.next();
            	    if(!Checker.isStringNullOrEmpty(brs)) {
            	    	//brs = id_versi+"~"+id_std+"~"+tgl_sta+"~"+tgl_end+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendal+"~"+tkn_jab_lap+"~"+tujuan+"~"+lingkup+"~"+definisi+"~"+prosedur+"~"+kualifikasi+"~"+dokuman+"~"+referensi;
                	    StringTokenizer st = new StringTokenizer(brs,"~");
                	    String id_versi = st.nextToken();
                	    String id_std = st.nextToken();
                	    String tgl_sta = st.nextToken();
                	    String tgl_end = st.nextToken();
                	    String tkn_jab_rumus = st.nextToken();
                	    String tkn_jab_cek = st.nextToken();
                	    String tkn_jab_stuju = st.nextToken();
                	    String tkn_jab_tetap = st.nextToken();
                	    String tkn_jab_kendal = st.nextToken();
                	    String tkn_jab_lap = st.nextToken();
                	    String tujuan = st.nextToken();
                	    String lingkup = st.nextToken();
                	    String definisi = st.nextToken();
                	    String prosedur = st.nextToken();
                	    String kualifikasi = st.nextToken();
                	    String dokuman = st.nextToken();
                	    String referensi = st.nextToken();
                	    
                	    //C1 nama PT
                	    int baris = 0;
                	    int kolom = 2;
                	    String label=new String(nm_pt.toUpperCase());
            	    	row = sheet.getRow(baris);
    					if(row == null) {
    						row = sheet.createRow(baris);
    					}
    					row.setRowStyle(cellStyle);
    					cell = row.getCell(kolom);
    					if (cell == null) {
    						cell = row.createCell(kolom);
    					} 
    					cell.setCellStyle(cellStyleCenterMiddle);
    					cell.setCellType(Cell.CELL_TYPE_STRING);
    					if(Checker.isStringNullOrEmpty(label)) {
    						cell.setCellType(Cell.CELL_TYPE_BLANK);
    					}
    					else {
    						cell.setCellValue(label);
    					}
    					
    					 //F2 no doku
                	    kolom = 5;
                	    baris = 1;
                	    if(!Checker.isStringNullOrEmpty(no_dok)) {
                	    	label=new String(no_dok);
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyle);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyleCenterMiddle);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        					}
        					else {
        						cell.setCellValue(label);
        					}	
                	    }
                	    
                	    //H3 tgl sta
                	    kolom = 7;
                	    baris = 2;
                	    if(!Checker.isStringNullOrEmpty(tgl_sta)) {
                	    	tgl_sta = Converter.autoConvertDateFormat(tgl_sta, "/");
                	    	label=new String(tgl_sta);
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyle);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyleCenterMiddle);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        					}
        					else {
        						cell.setCellValue(label);
        					}	
                	    }
                	    
                	  //H4 versi
                	    kolom = 7;
                	    baris = 3;
                	    if(!Checker.isStringNullOrEmpty(id_versi)) {
                	    	//tgl_sta = Converter.autoConvertDateFormat(tgl_sta, "/");
                	    	id_versi = ""+(2+Integer.parseInt(id_versi));
                	    	label=new String(id_versi);
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyle);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyleCenterMiddle);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        					}
        					else {
        						cell.setCellValue(label);
        					}	
                	    }
                	    
                	  //A7 nama tipe manual
                	    kolom = 0;
                	    baris = 6;
                	    if(!Checker.isStringNullOrEmpty(tipe)) {
                	    	//tgl_sta = Converter.autoConvertDateFormat(tgl_sta, "/");
                	    	//id_versi = ""+(2+Integer.parseInt(id_versi));
                	    	label=new String("MANUAL "+tipe.toUpperCase());
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyleCenterBottom);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyleCenterBottom);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        					}
        					else {
        						cell.setCellValue(label);
        					}	
                	    }
                	  //A8 nama standar
                	    kolom = 0;
                	    baris = 7;
                	    if(!Checker.isStringNullOrEmpty(nm_std)) {
                	    	//tgl_sta = Converter.autoConvertDateFormat(tgl_sta, "/");
                	    	//id_versi = ""+(2+Integer.parseInt(id_versi));
                	    	label=new String(nm_std.toUpperCase());
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyleCenterTop);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyleCenterTop);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        					}
        					else {
        						cell.setCellValue(label);
        					}	
                	    }
                	    
                	  //E12 jabatan perumus
                	    kolom = 4;
                	    baris = 11;
                	    if(!Checker.isStringNullOrEmpty(tkn_jab_rumus)) {
                	    	//tgl_sta = Converter.autoConvertDateFormat(tgl_sta, "/");
                	    	//id_versi = ""+(2+Integer.parseInt(id_versi));
                	    	label=new String(tkn_jab_rumus);
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyleCenterMiddle);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyleCenterMiddle);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        					}
        					else {
        						cell.setCellValue(label);
        					}	
                	    }
                	    
                	  //E13 jabatan perumus
                	    kolom = 4;
                	    baris = 12;
                	    if(!Checker.isStringNullOrEmpty(tkn_jab_cek)) {
                	    	//tgl_sta = Converter.autoConvertDateFormat(tgl_sta, "/");
                	    	//id_versi = ""+(2+Integer.parseInt(id_versi));
                	    	label=new String(tkn_jab_cek);
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyleCenterMiddle);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyleCenterMiddle);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        					}
        					else {
        						cell.setCellValue(label);
        					}	
                	    }
                	  //E14 jabatan perumus
                	    kolom = 4;
                	    baris = 13;
                	    if(!Checker.isStringNullOrEmpty(tkn_jab_stuju)) {
                	    	//tgl_sta = Converter.autoConvertDateFormat(tgl_sta, "/");
                	    	//id_versi = ""+(2+Integer.parseInt(id_versi));
                	    	label=new String(tkn_jab_stuju);
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyleCenterMiddle);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyleCenterMiddle);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        					}
        					else {
        						cell.setCellValue(label);
        					}	
                	    }
                	    
                	  //E15 jabatan perumus
                	    kolom = 4;
                	    baris = 14;
                	    if(!Checker.isStringNullOrEmpty(tkn_jab_tetap)) {
                	    	//tgl_sta = Converter.autoConvertDateFormat(tgl_sta, "/");
                	    	//id_versi = ""+(2+Integer.parseInt(id_versi));
                	    	label=new String(tkn_jab_tetap);
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyleCenterMiddle);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyleCenterMiddle);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        					}
        					else {
        						cell.setCellValue(label);
        					}	
                	    }
                	    
                	  //E16 jabatan pengendali
                	    kolom = 4;
                	    baris = 15;
                	    if(!Checker.isStringNullOrEmpty(tkn_jab_kendal)) {
                	    	//tgl_sta = Converter.autoConvertDateFormat(tgl_sta, "/");
                	    	//id_versi = ""+(2+Integer.parseInt(id_versi));
                	    	label=new String(tkn_jab_kendal);
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyleCenterMiddle);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyleCenterMiddle);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        					}
        					else {
        						cell.setCellValue(label);
        					}	
                	    }
                	    
                	  //H12 tgl perumus
                	    kolom = 7;
                	    baris = 11;
                	    if(!Checker.isStringNullOrEmpty(tgl_rumus)) {
                	    	tgl_rumus = Converter.autoConvertDateFormat(tgl_rumus, "/");
                	    	//id_versi = ""+(2+Integer.parseInt(id_versi));
                	    	label=new String(tgl_rumus);
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyleCenterMiddle);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyleCenterMiddle);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        					}
        					else {
        						cell.setCellValue(label);
        					}	
                	    }
                	  //H13 tgl cek
                	    kolom = 7;
                	    baris = 12;
                	    if(!Checker.isStringNullOrEmpty(tgl_cek)) {
                	    	tgl_cek = Converter.autoConvertDateFormat(tgl_cek, "/");
                	    	//id_versi = ""+(2+Integer.parseInt(id_versi));
                	    	label=new String(tgl_cek);
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyleCenterMiddle);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyleCenterMiddle);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        					}
        					else {
        						cell.setCellValue(label);
        					}	
                	    }
                	  //H14 tgl stuju
                	    kolom = 7;
                	    baris = 13;
                	    if(!Checker.isStringNullOrEmpty(tgl_stuju)) {
                	    	tgl_stuju = Converter.autoConvertDateFormat(tgl_stuju, "/");
                	    	//id_versi = ""+(2+Integer.parseInt(id_versi));
                	    	label=new String(tgl_stuju);
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyleCenterMiddle);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyleCenterMiddle);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        					}
        					else {
        						cell.setCellValue(label);
        					}	
                	    }
                	  //H15 tgl tetap
                	    kolom = 7;
                	    baris = 14;
                	    if(!Checker.isStringNullOrEmpty(tgl_tetap)) {
                	    	tgl_tetap = Converter.autoConvertDateFormat(tgl_tetap, "/");
                	    	//id_versi = ""+(2+Integer.parseInt(id_versi));
                	    	label=new String(tgl_tetap);
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyleCenterMiddle);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyleCenterMiddle);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        					}
        					else {
        						cell.setCellValue(label);
        					}	
                	    }
                	  //H16 tgl kendali
                	    kolom = 7;
                	    baris = 15;
                	    if(!Checker.isStringNullOrEmpty(tgl_kendali)) {
                	    	tgl_kendali = Converter.autoConvertDateFormat(tgl_kendali, "/");
                	    	//id_versi = ""+(2+Integer.parseInt(id_versi));
                	    	label=new String(tgl_kendali);
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyleCenterMiddle);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyleCenterMiddle);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        					}
        					else {
        						cell.setCellValue(label);
        					}	
                	    }
                	    
                	  //B18 visi misi
                	    kolom = 1;
                	    baris = 17;
                	    String tmp = "Visi, Misi, Tujuan, dan Nilai "+Converter.convertTokenHurufPertamaBesar(nm_pt,null);
                	    if(!Checker.isStringNullOrEmpty(tmp)) {
                	    	//tgl_kendali = Converter.autoConvertDateFormat(tgl_kendali, "/");
                	    	//id_versi = ""+(2+Integer.parseInt(id_versi));
                	    	label=new String(tmp);
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyleLeftMiddle);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyleLeftMiddle);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        					}
        					else {
        						cell.setCellValue(label);
        					}	
                	    }
                	  //D18 visi misi
                	    kolom = 3;
                	    baris = 17;
                	    tmp = "A. Visi "+Converter.convertTokenHurufPertamaBesar(nm_pt, null)+"\n"+visi_pt+"\n\nTujuan "+Converter.convertTokenHurufPertamaBesar(nm_pt, null)+"\n"+misi_pt;
                	    if(!Checker.isStringNullOrEmpty(tmp)) {
                	    	//tgl_kendali = Converter.autoConvertDateFormat(tgl_kendali, "/");
                	    	//id_versi = ""+(2+Integer.parseInt(id_versi));
                	    	label=new String(tmp);
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyleLeftMiddle);
        					row.setHeight((short)-1);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyleLeftMiddle);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        					}
        					else {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        						cell.setCellValue(label);
        					}	
                	    }
                	  //B19 tujuan
                	    kolom = 1;
                	    baris = 18;
                	    tmp = "Tujuan dan Maksud Dokumen Manual Pelaksanaan "+Converter.convertTokenHurufPertamaBesar(nm_std,null);
                	    if(!Checker.isStringNullOrEmpty(tmp)) {
                	    	//tgl_kendali = Converter.autoConvertDateFormat(tgl_kendali, "/");
                	    	//id_versi = ""+(2+Integer.parseInt(id_versi));
                	    	label=new String(tmp);
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyleLeftMiddle);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyleLeftMiddle);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        					}
        					else {
        						cell.setCellValue(label);
        					}	
                	    }
                	  //C19 tujuan
                	    kolom = 2;
                	    baris = 18;
                	    if(!Checker.isStringNullOrEmpty("null")) {
                	    	//tgl_kendali = Converter.autoConvertDateFormat(tgl_kendali, "/");
                	    	//id_versi = ""+(2+Integer.parseInt(id_versi));
                	    	label=new String(tujuan);
                	    	row = sheet.getRow(baris);
        					if(row == null) {
        						row = sheet.createRow(baris);
        					}
        					row.setRowStyle(cellStyleLeftMiddle);
        					cell = row.getCell(kolom);
        					if (cell == null) {
        						cell = row.createCell(kolom);
        					} 
        					cell.setCellStyle(cellStyleLeftMiddle);
        					cell.setCellType(Cell.CELL_TYPE_STRING);
        					if(Checker.isStringNullOrEmpty(label)) {
        						cell.setCellType(Cell.CELL_TYPE_BLANK);
        					}
        					else {
        						cell.setCellValue(label);
        					}	
                	    }
            	    }
            	    
            	    sheet.autoSizeColumn(2);
            	    FileOutputStream fileOut = new FileOutputStream(outFile);
            	    wb.write(fileOut);
            	    //System.out.println("14");
            	    fileOut.close();
            	    //System.out.println("15");
        		}
        		catch (Exception e) {
        			e.printStackTrace();;
        		}
        	}
        	else {
        		//System.out.println("excel missing");
        	}    		
    	}

    	//System.out.println("done");
    }
    
    
    public String prepFormPenilaianMk(Vector v_list_mhs_dan_nilai, String[] info_kls, String operator_id, String operator_npm) {
    	String nm_file = null;
    	if(v_list_mhs_dan_nilai!=null && v_list_mhs_dan_nilai.size()>0) {
    		int i=0;
    		//info_kls  = {""+cuid,""+kdkmk,""+nakmk,""+shiftKelas,""+nmmdos,""+npmdos,""+noKlsPll};
    		String cuid = info_kls[i++];
    		String kdkmk = info_kls[i++];
    		String nakmk = info_kls[i++];
    		String shiftKelas = info_kls[i++];
    		String nmmdos = info_kls[i++];
    		String npmdos = info_kls[i++];
    		String noKlsPll = info_kls[i++];
    		String thsms = info_kls[i++];
    		boolean nilai_by_dosen = false;
    		if(operator_npm.equalsIgnoreCase(npmdos)){
    			nilai_by_dosen = true;
    		}
    		ListIterator li = v_list_mhs_dan_nilai.listIterator();
        	java.io.File master_file_empty_excel = new File(Constant.getVelueFromConstantTable("ROOT_PATH_MASTER_FILE")+"/master_nilai.xlsx");
        	java.io.File outFile = new File(Constant.getVelueFromConstantTable("FOLDER_TMP")+"/nilai_"+operator_id+"_"+AskSystem.getTime()+".xlsx");
        	nm_file = outFile.getName();
        	if(master_file_empty_excel.exists()) {
        		try {
        			//System.out.println("excel ditemeukan bo");
        			InputStream inp = new FileInputStream(master_file_empty_excel);
            	    //InputStream inp = new FileInputStream("workbook.xlsx");
            	    Workbook wb = WorkbookFactory.create(inp);
            	    CellStyle cellStyle = wb.createCellStyle();
            	    Sheet sheet = wb.getSheetAt(0);
            	    CreationHelper createHelper = wb.getCreationHelper();
            	    Row row = null;
            	    Cell cell = null;
            	    
            	    
            	    //cuid (A1)
            	    Tool.setCellValue(wb, 0, "A1", "String", cuid+","+thsms);
            	    //NAMA MK [kodemk] (A2)
            	    Tool.setCellValue(wb, 0, "A2", "String", nakmk.toUpperCase()+" ["+kdkmk+"]");
            	    //nama dosen
            	    Tool.setCellValue(wb, 0, "A3", "String", nmmdos.toUpperCase()+" ["+npmdos+"]");
            	    //shif (pll)
            	    Tool.setCellValue(wb, 0, "A4", "String", shiftKelas.toUpperCase()+" ["+noKlsPll+"]");
            	    int baris_awal_list_mhs=8;
            	    int norut = 0;
            	    StringTokenizer st = null;
            	    while(li.hasNext()) {
            	    	norut++;
            	    	String brs = (String)li.next();
            	    	//20201`2020112100007`12710250004`HEVMYI RANDO KURNIAWAN`REGULER MALAM`L`20121`2020112100007`66.9`B-`false`2.75
            	    	st = new StringTokenizer(brs,"`");
            	    	String kdpst = st.nextToken();
            	    	String npmhs = st.nextToken();
            	    	String nimhs = st.nextToken();
            	    	String nmmhs = st.nextToken();
            	    	String shift_mhs = st.nextToken();
            	    	String kdjek = st.nextToken();
            	    	String smawl = st.nextToken();
            	    	String npmhs_lagi = st.nextToken();
            	    	String nilai = st.nextToken();
            	    	String nlakh = st.nextToken();
            	    	String nilai_by_dosen_ignore = st.nextToken(); 
            	    	String bobot = st.nextToken();
            	    	Tool.setCellValue(wb, 0, "A"+baris_awal_list_mhs, "int", ""+norut);
            	    	Tool.setCellValue(wb, 0, "B"+baris_awal_list_mhs, "string", "["+kdpst+"]"+nmmhs);
            	    	Tool.setCellValue(wb, 0, "C"+baris_awal_list_mhs, "string", ""+npmhs);
            	    	Tool.setCellValue(wb, 0, "D"+baris_awal_list_mhs, "int", ""+nilai);
            	    	baris_awal_list_mhs++;
            	    }
            	    
         			
            	    FileOutputStream fileOut = new FileOutputStream(outFile);
            	    wb.write(fileOut);
            	    //System.out.println("14");
            	    fileOut.close();
            	   
            	    //System.out.println("15");
        		}
        		catch (Exception e) {
        			e.printStackTrace();;
        		}
        	}
        	else {
        		//System.out.println("excel missing");
        	}    		
    	}
    	return nm_file;
    	//System.out.println("done");
    }
    
    public void prepMasterKhsMhs(String thsms,String kdpst,String npm,String nmm,Vector vKrsHist,String outNameFile) {
    	String info_kdpst = Converter.getDetailKdpst(kdpst);
    	String nmpst = "N/A";
    	String kdjen = "N/A";
    	String thn_sms = thsms.substring(0,4)+"/"+thsms.substring(4,5);
    	if(info_kdpst!=null) {
    		StringTokenizer st = new StringTokenizer(info_kdpst,"#&");
    		nmpst = st.nextToken();
    		kdjen = st.nextToken();
    		kdjen = Converter.getDetailKdjen(kdjen);
    	}	
    	java.io.File file = new File(Constants.getRootMasterKhsFile()+"/"+kdpst+"/khs.xlsx");
    	//System.out.println("out file = "+Constants.getRootMasterKhsFile()+"/"+kdpst+"/khs.xlsx");
    	//String outNameFile = "khs_"+npm;
    	java.io.File outFile = new File(Constants.getTmpFile()+"/"+outNameFile+".xlsx");
    	if(file.exists()) {
    		try {
    		//System.out.println("excel ditemeukan");
    		InputStream inp = new FileInputStream(file);
    	    //InputStream inp = new FileInputStream("workbook.xlsx");
    	    Workbook wb = WorkbookFactory.create(inp);
    	    Sheet sheet = wb.getSheetAt(0);
    	    
    	    //col nmm - D2
    	    Row row = sheet.getRow(1);
    	    Cell cell = row.getCell(3);
    	    if (cell == null) {
    	        cell = row.createCell(3);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(nmm);
    	  //col npm - N2
    	    row = sheet.getRow(1);
    	    cell = row.getCell(13);
    	    if (cell == null) {
    	        cell = row.createCell(13);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(npm);
    	    //col prodi - D3 
    	    row = sheet.getRow(2);
    	    cell = row.getCell(3);
    	    if (cell == null) {
    	        cell = row.createCell(3);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(nmpst+" ("+kdjen+")");
    	  //col prodi - N3 
    	    row = sheet.getRow(2);
    	    cell = row.getCell(13);
    	    if (cell == null) {
    	        cell = row.createCell(13);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(Converter.convertThsmsKeterOnly(thsms));
    	    ListIterator li = vKrsHist.listIterator();
    	    
    	    int i = 0;
    	   // int no_sta_pos_x = 0,kode_sta_pos_x = 0,nakmk_sta_pos_x = 0;
    	    int sta_pos_y = 6;//cuma butuh satu - kan inputnya perbaris
    	    String sksemi = "0";
    	    String nlipsi = "0";
	    	String skstti = "0";
	    	String nlipki = "0";
    	    while(li.hasNext()) {
    	    	i++;
    	    	String brs = (String)li.next();
    	    	brs = brs.replace("#&", "`");
    	    	//System.out.println("baris="+brs);
    	    	StringTokenizer st = new StringTokenizer(brs,"`");
    	    	String thsmsi = st.nextToken();
    	    	String kdkmki = st.nextToken(); 
    	    	String nakmki = st.nextToken(); 
    	    	String nlakhi = st.nextToken();
    	    	String boboti = st.nextToken();
    	    	String sksmki = st.nextToken();
    	    	double mutu = Double.valueOf(boboti).doubleValue()*Double.valueOf(sksmki).doubleValue();
    	    	String kelasi = st.nextToken();
    	    	sksemi = st.nextToken();
    	    	nlipsi = st.nextToken();
    	    	skstti = st.nextToken();
    	    	nlipki = st.nextToken();
    	    	//no A7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(0);
        	    if (cell == null) {
        	        cell = row.createCell(0);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(i);
        	    
        	  //kode B7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(1);
        	    if (cell == null) {
        	        cell = row.createCell(1);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(kdkmki);
        	    
        	  //nakmk D7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(3);
        	    if (cell == null) {
        	        cell = row.createCell(3);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(nakmki);
        	    
        	  //sksmk L7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(11);
        	    if (cell == null) {
        	        cell = row.createCell(11);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(sksmki);
        	   
        	    //nlakh N7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(13);
        	    if (cell == null) {
        	        cell = row.createCell(13);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(nlakhi);
        	    
        	  //bobot O7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(14);
        	    if (cell == null) {
        	        cell = row.createCell(14);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(boboti);
        	    
        	  //mutu P7
        	    row = sheet.getRow(sta_pos_y);
        	    cell = row.getCell(15);
        	    if (cell == null) {
        	        cell = row.createCell(15);
        	    }    
        	    cell.setCellType(Cell.CELL_TYPE_STRING);
        	    cell.setCellValue(""+mutu);
        	    
        	    
        	    sta_pos_y++;
    	    }
    	  //sksem N17
    	    row = sheet.getRow(16);
    	    cell = row.getCell(13);
    	    if (cell == null) {
    	        cell = row.createCell(13);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(sksemi);
    	    
    	  //nlipsi P17
    	    row = sheet.getRow(16);
    	    cell = row.getCell(15);
    	    if (cell == null) {
    	        cell = row.createCell(15);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(""+NumberFormater.return2digit(nlipsi));
    	    
    	  //skstti N18
    	    row = sheet.getRow(17);
    	    cell = row.getCell(13);
    	    if (cell == null) {
    	        cell = row.createCell(13);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(skstti);
    	    
    	  //nlipki P18
    	    row = sheet.getRow(17);
    	    cell = row.getCell(15);
    	    if (cell == null) {
    	        cell = row.createCell(15);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(NumberFormater.return2digit(nlipki));
    	  
    	    
    	    
    	  //tpt-tgl K19
    	    row = sheet.getRow(18);
    	    cell = row.getCell(10);
    	    if (cell == null) {
    	        cell = row.createCell(10);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue("JAKARTA, "+AskSystem.getCurrentTimeInLongStringFormat());
    	    // Write the output to a file
    	    FileOutputStream fileOut = new FileOutputStream(outFile);
    	    wb.write(fileOut);
    	    //System.out.println("14");
    	    fileOut.close();
    	    //System.out.println("15");
    		}
    		catch (Exception e) {
    			//System.out.println("poi err "+e);
    		}
    	}
    	else {
    		//System.out.println("excel missing");
    	}
    	//System.out.println("done");
    }
    
    public void prepMasterUsrPwd(String npm,String nmm,String usr,String pwd,String outNameFile) {
    	//System.out.println("outNameFile="+outNameFile);	
    	java.io.File file = new File(Constants.getRootMasterUsrPwdFile()+"/usrpwd.xlsx");
    	//System.out.println("out file = "+Constants.getRootMasterUsrPwdFile()+"/usrpwd.xlsx");
    	//String outNameFile = "khs_"+npm;
    	java.io.File outFile = new File(Constants.getTmpFile()+"/"+outNameFile+".xlsx");
    	if(file.exists()) {
    		try {
    		//System.out.println("excel ditemeukan");
    		InputStream inp = new FileInputStream(file);
    	    //InputStream inp = new FileInputStream("workbook.xlsx");
    	    Workbook wb = WorkbookFactory.create(inp);
    	    Sheet sheet = wb.getSheetAt(0);
    	    
    	    //col nmm - B3
    	   // //System.out.println("1");
    	    Row row = sheet.createRow(2);
    	    row = sheet.getRow(2);
    	    //System.out.println("1a");
    	    //if(row == null) {
    	    //	sheet.createRow(2);
    	    //}
    	    Cell cell = row.getCell(1);
    	    //System.out.println("1b");
    	    if (cell == null) {
    	    	 //System.out.println("1c");
    	        cell = row.createCell(1);
    	        //System.out.println("1d");
    	    }    
    	    //System.out.println("1e");
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    //System.out.println("1f");
    	    cell.setCellValue(Tool.capFirstLetterInWord(nmm));
    	    //System.out.println("2");
    	    //usrname D10
    	    row = sheet.getRow(9);
    	    cell = row.getCell(3);
    	    if (cell == null) {
    	        cell = row.createCell(3);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(usr);
    	    //System.out.println("3");
    	  //usrpwd D11
    	    row = sheet.getRow(10);
    	    cell = row.getCell(3);
    	    if (cell == null) {
    	        cell = row.createCell(3);
    	    }    
    	    cell.setCellType(Cell.CELL_TYPE_STRING);
    	    cell.setCellValue(pwd);
    	    //System.out.println("4");
    	    
    	    // Write the output to a file
    	    FileOutputStream fileOut = new FileOutputStream(outFile);
    	    wb.write(fileOut);
    	    //System.out.println("14");
    	    fileOut.close();
    	    //System.out.println("15");
    		}
    		catch (Exception e) {
    			//System.out.println("poi err "+e);
    		}
    	}
    	else {
    		//System.out.println("excel missing");
    	}
    	//System.out.println("done");
    }

    
    
    public void prepMasterIjazahMhs(String kdpst_mhs,Vector v_ija) {
    //public void prepMasterIjazahMhs(String kdpst,String nmmhs,String nirl) {
    	//prep ijazah MM
		ListIterator lija = v_ija.listIterator();
		String nomoija = "";
		String namaija = "";
		String tptglhrija = "";
		String nonirlija = "";
		String nimija = "";
		String gelarija = "";
		if(lija.hasNext()) {
			nomoija = (String)lija.next();
			namaija = (String)lija.next();
			tptglhrija = (String)lija.next();
			nonirlija = (String)lija.next();
			nimija = (String)lija.next();
			gelarija = (String)lija.next();	
			
			StringTokenizer st = new StringTokenizer(namaija.toUpperCase());
			String nmfile = "";
			while(st.hasMoreTokens()) {
				nmfile = nmfile+st.nextToken();
				if(st.hasMoreTokens()) {
					nmfile=nmfile+"_";
				}
			}
		
			if(kdpst_mhs.equalsIgnoreCase("61101")) {
				java.io.File file = new File(Constants.getRootFolderMasterIjazah()+"/"+kdpst_mhs+"/"+kdpst_mhs+".xlsx");
    		//java.io.File file = new File(Constants.getMasterKuiFile());
				//System.out.println("out file = "+Constants.getTmpFile()+"/ijazah_"+nmfile+".xlsx");
				java.io.File outFile = new File(Constants.getTmpFile()+"/ijazah_"+nmfile+".xlsx");
				if(file.exists()) {
					try {
						//System.out.println("excel ditemeukan");
						InputStream inp = new FileInputStream(file);
						//InputStream inp = new FileInputStream("workbook.xlsx");
						//System.out.println("1");
						Workbook wb = WorkbookFactory.create(inp);
						Sheet sheet = wb.getSheetAt(0);
						//System.out.println("2");
    				
						//1.col noija
						Row row = sheet.getRow(0);
						if(row == null) {
							row = sheet.createRow(0);
						}
						Cell cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(nomoija);
						
						//2.col nama
						row = sheet.getRow(11);
						if(row == null) {
							row = sheet.createRow(11);
						}
						cell = row.getCell(6);
						if (cell == null) {
							cell = row.createCell(6);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(namaija);
    				
						//3.col tptglhr
						row = sheet.getRow(12);
						if(row == null) {
							row = sheet.createRow(12);
						}
						cell = row.getCell(6);
						if (cell == null) {
							cell = row.createCell(6);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(tptglhrija);
    				
						//4.col nimhs
						row = sheet.getRow(13);
						if(row == null) {
							row = sheet.createRow(13);
						}
						cell = row.getCell(6);
						if (cell == null) {
							cell = row.createCell(6);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(nimija);
    				
						//5.col gelar
						row = sheet.getRow(14);
						if(row == null) {
							row = sheet.createRow(14);
						}
						cell = row.getCell(6);
						if (cell == null) {
							cell = row.createCell(6);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(gelarija);
						
						
						//6.col nirl
						row = sheet.getRow(23);
						if(row == null) {
							row = sheet.createRow(23);
						}
						cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(nonirlija);
    				
						//6.col tgl terbit /download
						String tglTerbit = AskSystem.getCurrentTimeInLongStringFormat();
						tglTerbit = Tool.capFirstLetterInWord(tglTerbit);
						row = sheet.getRow(24);
						if(row == null) {
							row = sheet.createRow(24);
						}
						cell = row.getCell(10);
						if (cell == null) {
							cell = row.createCell(10);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(tglTerbit);
    				
    				
    				
    				
						FileOutputStream fileOut = new FileOutputStream(outFile);
						wb.write(fileOut);
						//System.out.println("14");
						fileOut.close();
						//System.out.println("15");
					}
					catch (Exception e) {
						//System.out.println("poi err "+e);
					}
				}
				else {
					//System.out.println("excel missing");
				}
				//System.out.println("done");
			}	
		}
    }	
    
    /*
     * deprecated pake v1 = add variable gabungan
     */
    public String prepMasterAbsensiKelas(String info_kelas, Vector vListMhs) {
    	//kelasInfo" value="<%=target_kmp+"`"+kdkmk+"`"+nakmk+"`"+nmmdos+"`"+shift+"`"+unique_id+"`"+idkmk+"`"+idkur+"`"+kdpst+"`"+noKlsPll %>" />
    	String out_file = "";
    	int norut_interval = 16; //tot mhs dalam 1 halaman
    	StringTokenizer st = new StringTokenizer(info_kelas,"`");
    	String target_kmp = st.nextToken();
    	String kdkmk = st.nextToken();
    	String nakmk = st.nextToken();
    	String nmmdos = st.nextToken();
    	String shift = st.nextToken();
    	String unique_id = st.nextToken();
    	String idkmk = st.nextToken();
    	String idkur = st.nextToken();
    	String kdpst = st.nextToken();
    	String noKlsPll = st.nextToken();
    	String target_thsms = st.nextToken();
    	String nama_fakultas = Converter.getNamaFakultas(kdpst);
    	//String npm_nama_singkatanKtu = Getter.getDefaultNpmNamaBerdasarkanJabatan(target_thsms, kdpst, "KEPALA TATA USAHA");
    	//String npm_nama_singkatanKabaa = Getter.getDefaultNpmNamaBerdasarkanJabatan(target_thsms, "00003", "KEPALA BIRO AKADEMIK");
    	//String npm_nama_singkatanKabauk = Getter.getDefaultNpmNamaBerdasarkanJabatan(target_thsms, "00005", "KEPALA BIRO UMUM DAN KEUANGAN");
    	String npm_nama_singkatanKtu = Getter.getDefaultNpmNamaBerdasarkanJabatan(kdpst, "KEPALA TATA USAHA");
    	String npm_nama_singkatanKabaa = Getter.getDefaultNpmNamaBerdasarkanJabatan(kdpst, "KEPALA BIRO AKADEMIK");
    	String npm_nama_singkatanKabauk = Getter.getDefaultNpmNamaBerdasarkanJabatan(kdpst, "KEPALA BIRO UMUM DAN KEUANGAN");
    	//npm_nama_singkatanKtu = "kosong`kosong";
    	//npm_nama_singkatanKabaa = "kosong`kosong";
    	//npm_nama_singkatanKabauk = "kosong`kosong";
    	
    	//System.out.println(npm_nama_singkatanKtu);
    	//System.out.println(npm_nama_singkatanKabaa);
    	//System.out.println(npm_nama_singkatanKabauk);
    	out_file=Constants.getTmpFile()+"/absen"+unique_id+".xlsx";
    	java.io.File file = new File(Getter.getMasterTampletAbsenPath());
    	//System.out.println("out file = "+out_file);
    	java.io.File outFile = new File(out_file);
    	if(file.exists()) {
    		try {
    			//System.out.println("excel ditemeukan");
        		InputStream inp = new FileInputStream(file);
        	    //InputStream inp = new FileInputStream("workbook.xlsx");
        		//System.out.println("1");
        	    Workbook wb = WorkbookFactory.create(inp);
        	    Sheet sheet = wb.getSheetAt(0);
        	    //System.out.println("2");
        	    boolean first = true;
        	    ListIterator li = vListMhs.listIterator();
        	    int one_page_row = 34;
        	    int hal = 0;
        	    int no = 1;
        	    int row_mhs = 0;
				do {
					String brs = (String)li.next();
					//li.add(nmmhs+"`"+npmhs+"`"+kdpst+"`"+smawl+"`"+status);
					st = new StringTokenizer(brs,"`");
					//System.out.println("brsa="+brs);
					String nmmhs = st.nextToken();
					String npmhs = st.nextToken();
					String kdpst_mhs = st.nextToken();
					String smawl = st.nextToken();
					String thsms = st.nextToken();
					String status = st.nextToken();
					if(first) {
						first = false;
						//1.C5 nama makul
						//System.out.println("1");
						Row row = sheet.getRow(4+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(4+(hal*one_page_row));
						}
						//System.out.println("1");
						Cell cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(kdkmk+" - "+nakmk.toUpperCase());
						
						//P5 kelas pll
						//System.out.println("2");
						row = sheet.getRow(4+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(4+(hal*one_page_row));
						}
						//System.out.println("2");
						cell = row.getCell(15);
						if (cell == null) {
							cell = row.createCell(15);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(noKlsPll);
						
						
						//2.C6 nama dosen
						//System.out.println("3");
						row = sheet.getRow(5+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(5+(hal*one_page_row));
						}
						//System.out.println("3");
						cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(nmmdos.toUpperCase());
						//
						
						//3.P6 thsms
						//System.out.println("4");
						row = sheet.getRow(5+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(5+(hal*one_page_row));
						}
						//System.out.println("4");
						cell = row.getCell(15);
						if (cell == null) {
							cell = row.createCell(15);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(Converter.ubahKeformatTahunAkademik(target_thsms));
						
						//3.C7 prodi
						//System.out.println("5");
						row = sheet.getRow(6+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(6+(hal*one_page_row));
						}
						//System.out.println("5");
						cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(Converter.getNamaKdpst(kdpst));
						
						//P7 shift
						//System.out.println("6");
						row = sheet.getRow(6+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(6+(hal*one_page_row));
						}
						//System.out.println("6");
						cell = row.getCell(15);
						if (cell == null) {
							cell = row.createCell(15);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(shift.toUpperCase());
						
						
						
						//A11 no urut mhs pertama
						//System.out.println("7");
						row_mhs = 10+(hal*one_page_row);
						row = sheet.getRow(row_mhs);
						if(row == null) {
							row = sheet.createRow(row_mhs);
						}
						//System.out.println("7");
						cell = row.getCell(0);
						if (cell == null) {
							cell = row.createCell(0);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(no++);
						//B11 npm mhs pertama
						//System.out.println("8");
						row = sheet.getRow(row_mhs);
						if(row == null) {
							row = sheet.createRow(row_mhs);
						}
						//System.out.println("8");
						cell = row.getCell(1);
						if (cell == null) {
							cell = row.createCell(1);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(npmhs);
						//C11 nama mhs pertama
						//System.out.println("9");
						row = sheet.getRow(row_mhs);
						if(row == null) {
							row = sheet.createRow(row_mhs);
						}
						cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(nmmhs.toUpperCase());
						row_mhs++;
						
						if(!li.hasNext()) {
							//cetak bagian bawah part
							//C29 nama ktu
							//System.out.println("10");
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(0);
							if (cell == null) {
								cell = row.createCell(0);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKtu, 2, "`"));
							
							//cetak bagian bawah part
							//G29 nama kbaak
							//System.out.println("11");
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(5);
							if (cell == null) {
								cell = row.createCell(5);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabaa, 2, "`"));
							
							//cetak bagian bawah part
							//L29 nama kbauk
							//System.out.println("12");
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(14);
							if (cell == null) {
								cell = row.createCell(14);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabauk, 2, "`"));
						}
						
					}
					else {
						if(no % norut_interval == 0) {
							//A11 no urut mhs pertama
							//System.out.println("13");
							Row row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							Cell cell = row.getCell(0);
							if (cell == null) {
								cell = row.createCell(0);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(no++);
							//B11 npm mhs pertama
							//System.out.println("14");
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(1);
							if (cell == null) {
								cell = row.createCell(1);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(npmhs);
							//C11 nama mhs pertama
							//System.out.println("15");
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(2);
							if (cell == null) {
								cell = row.createCell(2);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(nmmhs.toUpperCase());
							row_mhs++;
							
							//cetak bagian bawah part
							//C29 nama ktu
							//System.out.println("16");
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(0);
							if (cell == null) {
								cell = row.createCell(0);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKtu, 2, "`"));
							
							//cetak bagian bawah part
							//G29 nama kbaak
							//System.out.println("17");
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(5);
							if (cell == null) {
								cell = row.createCell(5);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabaa, 2, "`"));
							
							//cetak bagian bawah part
							//L29 nama kbauk
							//System.out.println("18");
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(14);
							if (cell == null) {
								cell = row.createCell(14);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabauk, 2, "`"));
								
							
							
							//yg berikutnya lembar baru
							first = true;
							hal++;
						}
						else {
							//System.out.println("19");
							Row row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							Cell cell = row.getCell(0);
							if (cell == null) {
								cell = row.createCell(0);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(no++);
							//B11 npm mhs pertama
							//System.out.println("20");
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(1);
							if (cell == null) {
								cell = row.createCell(1);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(npmhs);
							//C11 nama mhs pertama
							//System.out.println("21");
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(2);
							if (cell == null) {
								cell = row.createCell(2);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(nmmhs.toUpperCase());
							row_mhs++;
							
							if(!li.hasNext()) {
								//cetak bagian bawah part
								//C29 nama ktu
								//System.out.println("22");
								row = sheet.getRow(29+(hal*one_page_row));
								//System.out.println("22");
								if(row == null) {
									row = sheet.createRow(29+(hal*one_page_row));
								}
								//System.out.println("22");
								cell = row.getCell(0);
								if (cell == null) {
									cell = row.createCell(0);
								}   
								//System.out.println("22");
								cell.setCellType(Cell.CELL_TYPE_STRING);
								cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKtu, 2, "`"));
								
								//cetak bagian bawah part
								//G29 nama kbaak
								//System.out.println("23");
								row = sheet.getRow(29+(hal*one_page_row));
								if(row == null) {
									row = sheet.createRow(29+(hal*one_page_row));
								}
								cell = row.getCell(5);
								if (cell == null) {
									cell = row.createCell(5);
								}    
								cell.setCellType(Cell.CELL_TYPE_STRING);
								cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabaa, 2, "`"));
								
								//cetak bagian bawah part
								//L29 nama kbauk
								//System.out.println("24");
								row = sheet.getRow(29+(hal*one_page_row));
								if(row == null) {
									row = sheet.createRow(29+(hal*one_page_row));
								}
								cell = row.getCell(14);
								if (cell == null) {
									cell = row.createCell(14);
								}    
								cell.setCellType(Cell.CELL_TYPE_STRING);
								cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabauk, 2, "`"));
							}
						}
					}
				} while(li.hasNext());
        	    
        	  
				
				//A11 data mhs
				
				
        	    // Write the output to a file
        	    FileOutputStream fileOut = new FileOutputStream(outFile);
        	    wb.write(fileOut);
        	    //System.out.println("14");
        	    fileOut.close();
        	    //System.out.println("15");
    		}
    		catch (Exception e) {
    			//System.out.println("poi err "+e);
    		}
    	}
    	else {
    		//System.out.println("excel missing");
    	}
    	//System.out.println("done");
    	return out_file;
    }
    
    
    public String prepMasterAbsensiKelas_v1(String info_kelas, Vector vListMhs, String kelas_gabungan) {
    	//kelasInfo" value="<%=target_kmp+"`"+kdkmk+"`"+nakmk+"`"+nmmdos+"`"+shift+"`"+unique_id+"`"+idkmk+"`"+idkur+"`"+kdpst+"`"+noKlsPll %>" />
    	String out_file = "";
    	int norut_interval = 16; //tot mhs dalam 1 halaman
    	StringTokenizer st = new StringTokenizer(info_kelas,"`");
    	String target_kmp = st.nextToken();
    	String kdkmk = st.nextToken();
    	String nakmk = st.nextToken();
    	String nmmdos = st.nextToken();
    	String shift = st.nextToken();
    	String unique_id = st.nextToken();
    	String idkmk = st.nextToken();
    	String idkur = st.nextToken();
    	String kdpst = st.nextToken();
    	String noKlsPll = st.nextToken();
    	String target_thsms = st.nextToken();
    	String nama_fakultas = Converter.getNamaFakultas(kdpst);
    	//String npm_nama_singkatanKtu = Getter.getDefaultNpmNamaBerdasarkanJabatan(target_thsms, kdpst, "KEPALA TATA USAHA");
    	//String npm_nama_singkatanKabaa = Getter.getDefaultNpmNamaBerdasarkanJabatan(target_thsms, "00003", "KEPALA BIRO AKADEMIK");
    	//String npm_nama_singkatanKabauk = Getter.getDefaultNpmNamaBerdasarkanJabatan(target_thsms, "00005", "KEPALA BIRO UMUM DAN KEUANGAN");
    	String npm_nama_singkatanKtu = Getter.getDefaultNpmNamaBerdasarkanJabatan(kdpst, "KEPALA TATA USAHA");
    	String npm_nama_singkatanKabaa = Getter.getDefaultNpmNamaBerdasarkanJabatan(kdpst, "KEPALA BIRO AKADEMIK");
    	String npm_nama_singkatanKabauk = Getter.getDefaultNpmNamaBerdasarkanJabatan(kdpst, "KEPALA BIRO UMUM DAN KEUANGAN");
    	//npm_nama_singkatanKtu = "kosong`kosong";
    	//npm_nama_singkatanKabaa = "kosong`kosong";
    	//npm_nama_singkatanKabauk = "kosong`kosong";
    	
    	//System.out.println(npm_nama_singkatanKtu);
    	//System.out.println(npm_nama_singkatanKabaa);
    	//System.out.println(npm_nama_singkatanKabauk);
    	out_file=Constants.getTmpFile()+"/absen"+unique_id+".xlsx";
    	java.io.File file = new File(Getter.getMasterTampletAbsenPath());
    	//System.out.println("out file = "+out_file);
    	java.io.File outFile = new File(out_file);
    	if(file.exists()) {
    		try {
    			//System.out.println("excel ditemeukan");
        		InputStream inp = new FileInputStream(file);
        	    //InputStream inp = new FileInputStream("workbook.xlsx");
        		//System.out.println("1");
        	    Workbook wb = WorkbookFactory.create(inp);
        	    Sheet sheet = wb.getSheetAt(0);
        	    //System.out.println("2");
        	    boolean first = true;
        	    ListIterator li = vListMhs.listIterator();
        	    int one_page_row = 34;
        	    int hal = 0;
        	    int no = 1;
        	    int row_mhs = 0;
				do {
					String brs = (String)li.next();
					//li.add(nmmhs+"`"+npmhs+"`"+kdpst+"`"+smawl+"`"+status);
					st = new StringTokenizer(brs,"`");
					//System.out.println("brsa="+brs);
					String nmmhs = st.nextToken();
					String npmhs = st.nextToken();
					String kdpst_mhs = st.nextToken();
					String nmpst = "";
					if(kelas_gabungan.equalsIgnoreCase("true")) {
						nmpst = Converter.getNamaKdpst(kdpst_mhs);
					}
					String smawl = st.nextToken();
					String thsms = st.nextToken();
					String status = st.nextToken();
					st.nextToken();
					st.nextToken();
					String nimhs = st.nextToken();
					if(first) {
						first = false;
						//1.C5 nama makul
						//System.out.println("1");
						Row row = sheet.getRow(4+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(4+(hal*one_page_row));
						}
						//System.out.println("1");
						Cell cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(kdkmk+" - "+nakmk.toUpperCase());
						
						//P5 kelas pll
						//System.out.println("2");
						row = sheet.getRow(4+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(4+(hal*one_page_row));
						}
						//System.out.println("2");
						cell = row.getCell(15);
						if (cell == null) {
							cell = row.createCell(15);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if(kelas_gabungan.equalsIgnoreCase("true")) {
							cell.setCellValue("[KLS GABUNGAN]");
						}
						else {
							cell.setCellValue(noKlsPll);
						}
						
						//2.C6 nama dosen
						//System.out.println("3");
						row = sheet.getRow(5+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(5+(hal*one_page_row));
						}
						//System.out.println("3");
						cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(nmmdos.toUpperCase());
						//
						
						//3.P6 thsms
						//System.out.println("4");
						row = sheet.getRow(5+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(5+(hal*one_page_row));
						}
						//System.out.println("4");
						cell = row.getCell(15);
						if (cell == null) {
							cell = row.createCell(15);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(Converter.ubahKeformatTahunAkademik(target_thsms));
						
						//3.C7 prodi
						//System.out.println("5");
						row = sheet.getRow(6+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(6+(hal*one_page_row));
						}
						//System.out.println("5");
						cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(Converter.getNamaKdpst(kdpst));
						
						//P7 shift
						//System.out.println("6");
						row = sheet.getRow(6+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(6+(hal*one_page_row));
						}
						//System.out.println("6");
						cell = row.getCell(15);
						if (cell == null) {
							cell = row.createCell(15);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(shift.toUpperCase());
						
						
						
						//A11 no urut mhs pertama
						//System.out.println("7");
						row_mhs = 10+(hal*one_page_row);
						row = sheet.getRow(row_mhs);
						if(row == null) {
							row = sheet.createRow(row_mhs);
						}
						//System.out.println("7");
						cell = row.getCell(0);
						if (cell == null) {
							cell = row.createCell(0);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(no++);
						//B11 npm mhs pertama
						//System.out.println("8");
						row = sheet.getRow(row_mhs);
						if(row == null) {
							row = sheet.createRow(row_mhs);
						}
						//System.out.println("8");
						cell = row.getCell(1);
						if (cell == null) {
							cell = row.createCell(1);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if(Checker.isStringNullOrEmpty(nimhs)) {
							cell.setCellValue(npmhs);
						}
						else {
							cell.setCellValue(nimhs);
						}
						//C11 nama mhs pertama
						//System.out.println("9");
						row = sheet.getRow(row_mhs);
						if(row == null) {
							row = sheet.createRow(row_mhs);
						}
						cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if(kelas_gabungan.equalsIgnoreCase("true")) {	
							cell.setCellValue("["+nmpst.toUpperCase()+"] "+nmmhs.toUpperCase());
						}
						else {
							cell.setCellValue(nmmhs.toUpperCase());
						}	
						row_mhs++;
						
						if(!li.hasNext()) {
							//cetak bagian bawah part
							//C29 nama ktu
							//System.out.println("10");
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(0);
							if (cell == null) {
								cell = row.createCell(0);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKtu, 2, "`"));
							
							//cetak bagian bawah part
							//G29 nama kbaak
							//System.out.println("11");
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(5);
							if (cell == null) {
								cell = row.createCell(5);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabaa, 2, "`"));
							
							//cetak bagian bawah part
							//L29 nama kbauk
							//System.out.println("12");
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(14);
							if (cell == null) {
								cell = row.createCell(14);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabauk, 2, "`"));
						}
						
					}
					else {
						if(no % norut_interval == 0) {
							//A11 no urut mhs pertama
							//System.out.println("13");
							Row row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							Cell cell = row.getCell(0);
							if (cell == null) {
								cell = row.createCell(0);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(no++);
							//B11 npm mhs pertama
							//System.out.println("14");
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(1);
							if (cell == null) {
								cell = row.createCell(1);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(Checker.isStringNullOrEmpty(nimhs)) {
								cell.setCellValue(npmhs);
							}
							else {
								cell.setCellValue(nimhs);
							}
							//C11 nama mhs pertama
							//System.out.println("15");
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(2);
							if (cell == null) {
								cell = row.createCell(2);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(kelas_gabungan.equalsIgnoreCase("true")) {	
								cell.setCellValue("["+nmpst.toUpperCase()+"] "+nmmhs.toUpperCase());
							}
							else {
								cell.setCellValue(nmmhs.toUpperCase());
							}	
							row_mhs++;
							
							//cetak bagian bawah part
							//C29 nama ktu
							//System.out.println("16");
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(0);
							if (cell == null) {
								cell = row.createCell(0);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKtu, 2, "`"));
							
							//cetak bagian bawah part
							//G29 nama kbaak
							//System.out.println("17");
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(5);
							if (cell == null) {
								cell = row.createCell(5);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabaa, 2, "`"));
							
							//cetak bagian bawah part
							//L29 nama kbauk
							//System.out.println("18");
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(14);
							if (cell == null) {
								cell = row.createCell(14);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabauk, 2, "`"));
								
							
							
							//yg berikutnya lembar baru
							first = true;
							hal++;
						}
						else {
							//System.out.println("19");
							Row row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							Cell cell = row.getCell(0);
							if (cell == null) {
								cell = row.createCell(0);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(no++);
							//B11 npm mhs pertama
							//System.out.println("20");
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(1);
							if (cell == null) {
								cell = row.createCell(1);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(Checker.isStringNullOrEmpty(nimhs)) {
								cell.setCellValue(npmhs);
							}
							else {
								cell.setCellValue(nimhs);
							}
							//C11 nama mhs pertama
							//System.out.println("21");
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(2);
							if (cell == null) {
								cell = row.createCell(2);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(kelas_gabungan.equalsIgnoreCase("true")) {	
								cell.setCellValue("["+nmpst.toUpperCase()+"] "+nmmhs.toUpperCase());
							}
							else {
								cell.setCellValue(nmmhs.toUpperCase());
							}	
							row_mhs++;
							
							if(!li.hasNext()) {
								//cetak bagian bawah part
								//C29 nama ktu
								//System.out.println("22");
								row = sheet.getRow(29+(hal*one_page_row));
								//System.out.println("22");
								if(row == null) {
									row = sheet.createRow(29+(hal*one_page_row));
								}
								//System.out.println("22");
								cell = row.getCell(0);
								if (cell == null) {
									cell = row.createCell(0);
								}   
								//System.out.println("22");
								cell.setCellType(Cell.CELL_TYPE_STRING);
								cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKtu, 2, "`"));
								
								//cetak bagian bawah part
								//G29 nama kbaak
								//System.out.println("23");
								row = sheet.getRow(29+(hal*one_page_row));
								if(row == null) {
									row = sheet.createRow(29+(hal*one_page_row));
								}
								cell = row.getCell(5);
								if (cell == null) {
									cell = row.createCell(5);
								}    
								cell.setCellType(Cell.CELL_TYPE_STRING);
								cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabaa, 2, "`"));
								
								//cetak bagian bawah part
								//L29 nama kbauk
								//System.out.println("24");
								row = sheet.getRow(29+(hal*one_page_row));
								if(row == null) {
									row = sheet.createRow(29+(hal*one_page_row));
								}
								cell = row.getCell(14);
								if (cell == null) {
									cell = row.createCell(14);
								}    
								cell.setCellType(Cell.CELL_TYPE_STRING);
								cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabauk, 2, "`"));
							}
						}
					}
				} while(li.hasNext());
        	    
        	  
				
				//A11 data mhs
				
				
        	    // Write the output to a file
        	    FileOutputStream fileOut = new FileOutputStream(outFile);
        	    wb.write(fileOut);
        	    //System.out.println("14");
        	    fileOut.close();
        	    //System.out.println("15");
    		}
    		catch (Exception e) {
    			//System.out.println("poi err "+e);
    		}
    	}
    	else {
    		//System.out.println("excel missing");
    	}
    	//System.out.println("done");
    	return out_file;
    }
    
    
    /*
     * deprecated pake v1 = add variable gabungan
     */
    public String prepMasterAbsensiUts(String info_kelas, Vector vListMhs) {
    	//kelasInfo" value="<%=target_kmp+"`"+kdkmk+"`"+nakmk+"`"+nmmdos+"`"+shift+"`"+unique_id+"`"+idkmk+"`"+idkur+"`"+kdpst+"`"+noKlsPll %>" />
    	String out_file = "";
    	int norut_interval = 20; //tot mhs dalam 1 halaman
    	StringTokenizer st = new StringTokenizer(info_kelas,"`");
    	String target_kmp = st.nextToken();
    	String kdkmk = st.nextToken();
    	String nakmk = st.nextToken();
    	String nmmdos = st.nextToken();
    	String shift = st.nextToken();
    	String unique_id = st.nextToken();
    	String idkmk = st.nextToken();
    	String idkur = st.nextToken();
    	String kdpst = st.nextToken();
    	String noKlsPll = st.nextToken();
    	String target_thsms = st.nextToken();
    	String nama_fakultas = Converter.getNamaFakultas(kdpst);
    	String npm_nama_singkatanKtu = Getter.getDefaultNpmNamaBerdasarkanJabatan(kdpst, "KEPALA TATA USAHA");
    	String npm_nama_singkatanKabaa = Getter.getDefaultNpmNamaBerdasarkanJabatan(kdpst, "KEPALA BIRO AKADEMIK");
    	String npm_nama_singkatanKabauk = Getter.getDefaultNpmNamaBerdasarkanJabatan(kdpst, "KEPALA BIRO UMUM DAN KEUANGAN");
    	out_file=Constants.getTmpFile()+"/absenUts"+unique_id+".xlsx";
    	java.io.File file = new File(Getter.getMasterTampletAbsenUtsPath());
    	java.io.File outFile = new File(out_file);
    	if(file.exists()) {
    		try {
    			//System.out.println("excel ditemeukan");
        		InputStream inp = new FileInputStream(file);
        	    //InputStream inp = new FileInputStream("workbook.xlsx");
        		//System.out.println("1");
        	    Workbook wb = WorkbookFactory.create(inp);
        	    Sheet sheet = wb.getSheetAt(0);
        	    //System.out.println("2");
        	    boolean first = true;
        	    ListIterator li = vListMhs.listIterator();
        	    int one_page_row = 37;
        	    int hal = 0;
        	    int no = 1;
        	    int row_mhs = 0;
				do {
					String brs = (String)li.next();
					//li.add(nmmhs+"`"+npmhs+"`"+kdpst+"`"+smawl+"`"+status);
					st = new StringTokenizer(brs,"`");
					//System.out.println("brsa="+brs);
					String nmmhs = st.nextToken();
					String npmhs = st.nextToken();
					String kdpst_mhs = st.nextToken();
					String nmpst = Converter.getNamaKdpst(kdpst_mhs);
					String smawl = st.nextToken();
					String thsms = st.nextToken();
					String status = st.nextToken();
					st.nextToken();
					st.nextToken();
					String nimhs = st.nextToken();
					if(first) {
						first = false;
						Row row = sheet.getRow(1+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(1+(hal*one_page_row));
						}
						Cell cell = row.getCell(0);
						if (cell == null) {
							cell = row.createCell(0);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue("DAFTAR HADIR UJIAN TENGAH SEMESTER "+Converter.getSemesterGenapGanjil(thsms));
						//
						row = sheet.getRow(2+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(2+(hal*one_page_row));
						}
						cell = row.getCell(0);
						if (cell == null) {
							cell = row.createCell(0);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue("TAHUN AKADEMIK "+Converter.ubahKeformatTahunAkademik(thsms));
						//1.C5 nama makul
						//System.out.println("1");
						row = sheet.getRow(4+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(4+(hal*one_page_row));
						}
						//System.out.println("1");
						cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(Converter.getNamaKdpst(kdpst));
						
						//P5 kelas pll
						//System.out.println("2");
						row = sheet.getRow(4+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(4+(hal*one_page_row));
						}
						//System.out.println("2");
						cell = row.getCell(15);
						if (cell == null) {
							cell = row.createCell(15);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(noKlsPll);
						
						
						//2.C6 nama dosen
						//System.out.println("3");
						row = sheet.getRow(5+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(5+(hal*one_page_row));
						}
						//System.out.println("3");
						cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(nmmdos.toUpperCase());
						//
						
						//3.P6 thsms
						//System.out.println("4");
						//row = sheet.getRow(5+(hal*one_page_row));
						//if(row == null) {
						//	row = sheet.createRow(5+(hal*one_page_row));
						//}
						//System.out.println("4");
						//cell = row.getCell(15);
						//if (cell == null) {
						//	cell = row.createCell(15);
						//}    
						//cell.setCellType(Cell.CELL_TYPE_STRING);
						//cell.setCellValue(Converter.ubahKeformatTahunAkademik(target_thsms));
						
						//3.C7 prodi
						//System.out.println("5");
						row = sheet.getRow(6+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(6+(hal*one_page_row));
						}
						//System.out.println("5");
						cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(kdkmk+" - "+nakmk.toUpperCase());
						
						
						//P7 shift
						//System.out.println("6");
						//row = sheet.getRow(6+(hal*one_page_row));
						//if(row == null) {
						//	row = sheet.createRow(6+(hal*one_page_row));
						//}
						//System.out.println("6");
						//cell = row.getCell(15);
						//if (cell == null) {
						//	cell = row.createCell(15);
						//}    
						//cell.setCellType(Cell.CELL_TYPE_STRING);
						//cell.setCellValue(shift.toUpperCase());
						
						
						
						//A11 no urut mhs pertama
						//System.out.println("7");
						row_mhs = 10+(hal*one_page_row);
						row = sheet.getRow(row_mhs);
						if(row == null) {
							row = sheet.createRow(row_mhs);
						}
						//System.out.println("7");
						cell = row.getCell(0);
						if (cell == null) {
							cell = row.createCell(0);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(no++);
						//B11 npm mhs pertama
						//System.out.println("8");
						row = sheet.getRow(row_mhs);
						if(row == null) {
							row = sheet.createRow(row_mhs);
						}
						//System.out.println("8");
						cell = row.getCell(1);
						if (cell == null) {
							cell = row.createCell(1);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if(Checker.isStringNullOrEmpty(nimhs)) {
							cell.setCellValue(npmhs);
						}
						else {
							cell.setCellValue(nimhs);
						}
						//C11 nama mhs pertama
						//System.out.println("9");
						row = sheet.getRow(row_mhs);
						if(row == null) {
							row = sheet.createRow(row_mhs);
						}
						cell = row.getCell(3);
						if (cell == null) {
							cell = row.createCell(3);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(nmmhs.toUpperCase());
						row_mhs++;
						
						if(!li.hasNext()) {
							//cetak bagian bawah part
							//C29 nama ktu
							//System.out.println("10");
							//row = sheet.getRow(29+(hal*one_page_row));
							//if(row == null) {
							//	row = sheet.createRow(29+(hal*one_page_row));
							//}
							//cell = row.getCell(0);
							//if (cell == null) {
							//	cell = row.createCell(0);
							//}    
							//cell.setCellType(Cell.CELL_TYPE_STRING);
							//cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKtu, 2, "`"));
							
							//cetak bagian bawah part
							//G29 nama kbaak
							//System.out.println("11");
							//row = sheet.getRow(29+(hal*one_page_row));
							//if(row == null) {
							//	row = sheet.createRow(29+(hal*one_page_row));
							//}
							//cell = row.getCell(5);
							//if (cell == null) {
							//	cell = row.createCell(5);
							//}    
							//cell.setCellType(Cell.CELL_TYPE_STRING);
							//cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabaa, 2, "`"));
							
							//cetak bagian bawah part
							//L29 nama kbauk
							//System.out.println("12");
							//row = sheet.getRow(29+(hal*one_page_row));
							//if(row == null) {
						//		row = sheet.createRow(29+(hal*one_page_row));
						//	}
						//	cell = row.getCell(14);
						//	if (cell == null) {
							//	cell = row.createCell(14);
							//}    
							//cell.setCellType(Cell.CELL_TYPE_STRING);
							//cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabauk, 2, "`"));
						}
						
					}
					else {
						if(no % norut_interval == 0) {
							//A11 no urut mhs pertama
							//System.out.println("13");
							Row row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							Cell cell = row.getCell(0);
							if (cell == null) {
								cell = row.createCell(0);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(no++);
							//B11 npm mhs pertama
							//System.out.println("14");
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(1);
							if (cell == null) {
								cell = row.createCell(1);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(npmhs);
							//C11 nama mhs pertama
							//System.out.println("15");
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(3);
							if (cell == null) {
								cell = row.createCell(3);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(nmmhs.toUpperCase());
							row_mhs++;
							
							//cetak bagian bawah part
							//C29 nama ktu
							//System.out.println("16");
							//row = sheet.getRow(29+(hal*one_page_row));
							//if(row == null) {
							//	row = sheet.createRow(29+(hal*one_page_row));
							//}
							//cell = row.getCell(0);
							//if (cell == null) {
							//	cell = row.createCell(0);
							//}    
							//cell.setCellType(Cell.CELL_TYPE_STRING);
							//cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKtu, 2, "`"));
							
							//cetak bagian bawah part
							//G29 nama kbaak
							//System.out.println("17");
							/*
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(5);
							if (cell == null) {
								cell = row.createCell(5);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabaa, 2, "`"));
							*/
							//cetak bagian bawah part
							//L29 nama kbauk
							//System.out.println("18");
							/*
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(14);
							if (cell == null) {
								cell = row.createCell(14);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabauk, 2, "`"));
							*/	
							
							
							//yg berikutnya lembar baru
							first = true;
							hal++;
						}
						else {
							//System.out.println("19");
							Row row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							Cell cell = row.getCell(0);
							if (cell == null) {
								cell = row.createCell(0);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(no++);
							//B11 npm mhs pertama
							//System.out.println("20");
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(1);
							if (cell == null) {
								cell = row.createCell(1);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(npmhs);
							//C11 nama mhs pertama
							//System.out.println("21");
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(3);
							if (cell == null) {
								cell = row.createCell(3);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(nmmhs.toUpperCase());
							row_mhs++;
							
							if(!li.hasNext()) {
								//cetak bagian bawah part
								//C29 nama ktu
								//System.out.println("22");
								/*
								row = sheet.getRow(29+(hal*one_page_row));
								//System.out.println("22");
								if(row == null) {
									row = sheet.createRow(29+(hal*one_page_row));
								}
								//System.out.println("22");
								cell = row.getCell(0);
								if (cell == null) {
									cell = row.createCell(0);
								}   
								//System.out.println("22");
								cell.setCellType(Cell.CELL_TYPE_STRING);
								cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKtu, 2, "`"));
								
								//cetak bagian bawah part
								//G29 nama kbaak
								//System.out.println("23");
								row = sheet.getRow(29+(hal*one_page_row));
								if(row == null) {
									row = sheet.createRow(29+(hal*one_page_row));
								}
								cell = row.getCell(5);
								if (cell == null) {
									cell = row.createCell(5);
								}    
								cell.setCellType(Cell.CELL_TYPE_STRING);
								cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabaa, 2, "`"));
								
								//cetak bagian bawah part
								//L29 nama kbauk
								//System.out.println("24");
								row = sheet.getRow(29+(hal*one_page_row));
								if(row == null) {
									row = sheet.createRow(29+(hal*one_page_row));
								}
								cell = row.getCell(14);
								if (cell == null) {
									cell = row.createCell(14);
								}    
								cell.setCellType(Cell.CELL_TYPE_STRING);
								cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabauk, 2, "`"));
								*/
							}
						}
					}
				} while(li.hasNext());
        	    
        	  
				
				//A11 data mhs
				
				
        	    // Write the output to a file
        	    FileOutputStream fileOut = new FileOutputStream(outFile);
        	    wb.write(fileOut);
        	    //System.out.println("14");
        	    fileOut.close();
        	    //System.out.println("15");
    		}
    		catch (Exception e) {
    			//System.out.println("poi err "+e);
    		}
    	}
    	else {
    		//System.out.println("excel missing");
    	}
    	//System.out.println("done");
    	return out_file;
    }
    
    
    public String prepMasterAbsensiUts_v1(String info_kelas, Vector vListMhs, String kelas_gabungan) {
    	//kelasInfo" value="<%=target_kmp+"`"+kdkmk+"`"+nakmk+"`"+nmmdos+"`"+shift+"`"+unique_id+"`"+idkmk+"`"+idkur+"`"+kdpst+"`"+noKlsPll %>" />
    	
    	String out_file = "";
    	int norut_interval = 20; //tot mhs dalam 1 halaman
    	StringTokenizer st = new StringTokenizer(info_kelas,"`");
    	String target_kmp = st.nextToken();
    	String kdkmk = st.nextToken();
    	String nakmk = st.nextToken();
    	String nmmdos = st.nextToken();
    	String shift = st.nextToken();
    	String unique_id = st.nextToken();
    	String idkmk = st.nextToken();
    	String idkur = st.nextToken();
    	String kdpst = st.nextToken();
    	String noKlsPll = st.nextToken();
    	String target_thsms = st.nextToken();
    	String nama_fakultas = Converter.getNamaFakultas(kdpst);
    	String npm_nama_singkatanKtu = Getter.getDefaultNpmNamaBerdasarkanJabatan(kdpst, "KEPALA TATA USAHA");
    	String npm_nama_singkatanKabaa = Getter.getDefaultNpmNamaBerdasarkanJabatan(kdpst, "KEPALA BIRO AKADEMIK");
    	String npm_nama_singkatanKabauk = Getter.getDefaultNpmNamaBerdasarkanJabatan(kdpst, "KEPALA BIRO UMUM DAN KEUANGAN");
    	out_file=Constants.getTmpFile()+"/absenUts"+unique_id+".xlsx";
    	java.io.File file = new File(Getter.getMasterTampletAbsenUtsPath());
    	java.io.File outFile = new File(out_file);
    	if(file.exists()) {
    		try {
    			//System.out.println("excel ditemeukan");
        		InputStream inp = new FileInputStream(file);
        	    //InputStream inp = new FileInputStream("workbook.xlsx");
        		//System.out.println("1");
        	    Workbook wb = WorkbookFactory.create(inp);
        	    Sheet sheet = wb.getSheetAt(0);
        	    //System.out.println("2");
        	    boolean first = true;
        	    ListIterator li = vListMhs.listIterator();
        	    int one_page_row = 37;
        	    int hal = 0;
        	    int no = 1;
        	    int row_mhs = 0;
				do {
					String brs = (String)li.next();
					//li.add(nmmhs+"`"+npmhs+"`"+kdpst+"`"+smawl+"`"+status);
					st = new StringTokenizer(brs,"`");
					//System.out.println("brsa="+brs);
					String nmmhs = st.nextToken();
					String npmhs = st.nextToken();
					String kdpst_mhs = st.nextToken();
					String nmpst = "";
					if(kelas_gabungan.equalsIgnoreCase("true")) {
						nmpst = Converter.getNamaKdpst(kdpst_mhs);
					}
					String smawl = st.nextToken();
					String thsms = st.nextToken();
					String status = st.nextToken();
					st.nextToken();
					st.nextToken();
					String nimhs = st.nextToken();
					if(first) {
						first = false;
						Row row = sheet.getRow(1+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(1+(hal*one_page_row));
						}
						Cell cell = row.getCell(0);
						if (cell == null) {
							cell = row.createCell(0);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue("DAFTAR HADIR UJIAN TENGAH SEMESTER "+Converter.getSemesterGenapGanjil(thsms));
						//
						row = sheet.getRow(2+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(2+(hal*one_page_row));
						}
						cell = row.getCell(0);
						if (cell == null) {
							cell = row.createCell(0);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue("TAHUN AKADEMIK "+Converter.ubahKeformatTahunAkademik(thsms));
						//1.C5 nama makul
						//System.out.println("1");
						row = sheet.getRow(4+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(4+(hal*one_page_row));
						}
						//System.out.println("1");
						cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if(kelas_gabungan.equalsIgnoreCase("true")) {
							cell.setCellValue("KELAS GABUNGAN");
						}
						else {	
							cell.setCellValue(Converter.getNamaKdpst(kdpst));
						}
						//P5 kelas pll
						//System.out.println("2");
						row = sheet.getRow(4+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(4+(hal*one_page_row));
						}
						//System.out.println("2");
						cell = row.getCell(15);
						if (cell == null) {
							cell = row.createCell(15);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(noKlsPll);
						
						
						//2.C6 nama dosen
						//System.out.println("3");
						row = sheet.getRow(5+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(5+(hal*one_page_row));
						}
						//System.out.println("3");
						cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(nmmdos.toUpperCase());
						//
						
						//3.P6 thsms
						//System.out.println("4");
						//row = sheet.getRow(5+(hal*one_page_row));
						//if(row == null) {
						//	row = sheet.createRow(5+(hal*one_page_row));
						//}
						//System.out.println("4");
						//cell = row.getCell(15);
						//if (cell == null) {
						//	cell = row.createCell(15);
						//}    
						//cell.setCellType(Cell.CELL_TYPE_STRING);
						//cell.setCellValue(Converter.ubahKeformatTahunAkademik(target_thsms));
						
						//3.C7 prodi
						//System.out.println("5");
						row = sheet.getRow(6+(hal*one_page_row));
						if(row == null) {
							row = sheet.createRow(6+(hal*one_page_row));
						}
						//System.out.println("5");
						cell = row.getCell(2);
						if (cell == null) {
							cell = row.createCell(2);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(kdkmk+" - "+nakmk.toUpperCase());
						
						
						//P7 shift
						//System.out.println("6");
						//row = sheet.getRow(6+(hal*one_page_row));
						//if(row == null) {
						//	row = sheet.createRow(6+(hal*one_page_row));
						//}
						//System.out.println("6");
						//cell = row.getCell(15);
						//if (cell == null) {
						//	cell = row.createCell(15);
						//}    
						//cell.setCellType(Cell.CELL_TYPE_STRING);
						//cell.setCellValue(shift.toUpperCase());
						
						
						
						//A11 no urut mhs pertama
						//System.out.println("7");
						row_mhs = 10+(hal*one_page_row);
						row = sheet.getRow(row_mhs);
						if(row == null) {
							row = sheet.createRow(row_mhs);
						}
						//System.out.println("7");
						cell = row.getCell(0);
						if (cell == null) {
							cell = row.createCell(0);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(no++);
						//B11 npm mhs pertama
						//System.out.println("8");
						row = sheet.getRow(row_mhs);
						if(row == null) {
							row = sheet.createRow(row_mhs);
						}
						//System.out.println("8");
						cell = row.getCell(1);
						if (cell == null) {
							cell = row.createCell(1);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if(Checker.isStringNullOrEmpty(nimhs)) {
							cell.setCellValue(npmhs);
						}
						else {
							cell.setCellValue(nimhs);
						}
						//C11 nama mhs pertama
						//System.out.println("9");
						row = sheet.getRow(row_mhs);
						if(row == null) {
							row = sheet.createRow(row_mhs);
						}
						cell = row.getCell(3);
						if (cell == null) {
							cell = row.createCell(3);
						}    
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if(kelas_gabungan.equalsIgnoreCase("true")) {	
							cell.setCellValue("["+nmpst.toUpperCase()+"] "+nmmhs.toUpperCase());
						}
						else {
							cell.setCellValue(nmmhs.toUpperCase());
						}	
						row_mhs++;
						
						if(!li.hasNext()) {
							//cetak bagian bawah part
							//C29 nama ktu
							//System.out.println("10");
							//row = sheet.getRow(29+(hal*one_page_row));
							//if(row == null) {
							//	row = sheet.createRow(29+(hal*one_page_row));
							//}
							//cell = row.getCell(0);
							//if (cell == null) {
							//	cell = row.createCell(0);
							//}    
							//cell.setCellType(Cell.CELL_TYPE_STRING);
							//cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKtu, 2, "`"));
							
							//cetak bagian bawah part
							//G29 nama kbaak
							//System.out.println("11");
							//row = sheet.getRow(29+(hal*one_page_row));
							//if(row == null) {
							//	row = sheet.createRow(29+(hal*one_page_row));
							//}
							//cell = row.getCell(5);
							//if (cell == null) {
							//	cell = row.createCell(5);
							//}    
							//cell.setCellType(Cell.CELL_TYPE_STRING);
							//cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabaa, 2, "`"));
							
							//cetak bagian bawah part
							//L29 nama kbauk
							//System.out.println("12");
							//row = sheet.getRow(29+(hal*one_page_row));
							//if(row == null) {
						//		row = sheet.createRow(29+(hal*one_page_row));
						//	}
						//	cell = row.getCell(14);
						//	if (cell == null) {
							//	cell = row.createCell(14);
							//}    
							//cell.setCellType(Cell.CELL_TYPE_STRING);
							//cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabauk, 2, "`"));
						}
						
					}
					else {
						if(no % norut_interval == 0) {
							//A11 no urut mhs pertama
							//System.out.println("13");
							Row row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							Cell cell = row.getCell(0);
							if (cell == null) {
								cell = row.createCell(0);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(no++);
							//B11 npm mhs pertama
							//System.out.println("14");
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(1);
							if (cell == null) {
								cell = row.createCell(1);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(Checker.isStringNullOrEmpty(nimhs)) {
								cell.setCellValue(npmhs);
							}
							else {
								cell.setCellValue(nimhs);
							}
							//C11 nama mhs pertama
							//System.out.println("15");
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(3);
							if (cell == null) {
								cell = row.createCell(3);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(kelas_gabungan.equalsIgnoreCase("true")) {	
								cell.setCellValue("["+nmpst.toUpperCase()+"] "+nmmhs.toUpperCase());
							}
							else {
								cell.setCellValue(nmmhs.toUpperCase());
							}
							row_mhs++;
							
							//cetak bagian bawah part
							//C29 nama ktu
							//System.out.println("16");
							//row = sheet.getRow(29+(hal*one_page_row));
							//if(row == null) {
							//	row = sheet.createRow(29+(hal*one_page_row));
							//}
							//cell = row.getCell(0);
							//if (cell == null) {
							//	cell = row.createCell(0);
							//}    
							//cell.setCellType(Cell.CELL_TYPE_STRING);
							//cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKtu, 2, "`"));
							
							//cetak bagian bawah part
							//G29 nama kbaak
							//System.out.println("17");
							/*
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(5);
							if (cell == null) {
								cell = row.createCell(5);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabaa, 2, "`"));
							*/
							//cetak bagian bawah part
							//L29 nama kbauk
							//System.out.println("18");
							/*
							row = sheet.getRow(29+(hal*one_page_row));
							if(row == null) {
								row = sheet.createRow(29+(hal*one_page_row));
							}
							cell = row.getCell(14);
							if (cell == null) {
								cell = row.createCell(14);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabauk, 2, "`"));
							*/	
							
							
							//yg berikutnya lembar baru
							first = true;
							hal++;
						}
						else {
							//System.out.println("19");
							Row row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							Cell cell = row.getCell(0);
							if (cell == null) {
								cell = row.createCell(0);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cell.setCellValue(no++);
							//B11 npm mhs pertama
							//System.out.println("20");
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(1);
							if (cell == null) {
								cell = row.createCell(1);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(Checker.isStringNullOrEmpty(nimhs)) {
								cell.setCellValue(npmhs);
							}
							else {
								cell.setCellValue(nimhs);
							}
							//C11 nama mhs pertama
							//System.out.println("21");
							row = sheet.getRow(row_mhs);
							if(row == null) {
								row = sheet.createRow(row_mhs);
							}
							cell = row.getCell(3);
							if (cell == null) {
								cell = row.createCell(3);
							}    
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(kelas_gabungan.equalsIgnoreCase("true")) {	
								cell.setCellValue("["+nmpst.toUpperCase()+"] "+nmmhs.toUpperCase());
							}
							else {
								cell.setCellValue(nmmhs.toUpperCase());
							}
							row_mhs++;
							
							if(!li.hasNext()) {
								//cetak bagian bawah part
								//C29 nama ktu
								//System.out.println("22");
								/*
								row = sheet.getRow(29+(hal*one_page_row));
								//System.out.println("22");
								if(row == null) {
									row = sheet.createRow(29+(hal*one_page_row));
								}
								//System.out.println("22");
								cell = row.getCell(0);
								if (cell == null) {
									cell = row.createCell(0);
								}   
								//System.out.println("22");
								cell.setCellType(Cell.CELL_TYPE_STRING);
								cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKtu, 2, "`"));
								
								//cetak bagian bawah part
								//G29 nama kbaak
								//System.out.println("23");
								row = sheet.getRow(29+(hal*one_page_row));
								if(row == null) {
									row = sheet.createRow(29+(hal*one_page_row));
								}
								cell = row.getCell(5);
								if (cell == null) {
									cell = row.createCell(5);
								}    
								cell.setCellType(Cell.CELL_TYPE_STRING);
								cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabaa, 2, "`"));
								
								//cetak bagian bawah part
								//L29 nama kbauk
								//System.out.println("24");
								row = sheet.getRow(29+(hal*one_page_row));
								if(row == null) {
									row = sheet.createRow(29+(hal*one_page_row));
								}
								cell = row.getCell(14);
								if (cell == null) {
									cell = row.createCell(14);
								}    
								cell.setCellType(Cell.CELL_TYPE_STRING);
								cell.setCellValue(Tool.getTokenKe(npm_nama_singkatanKabauk, 2, "`"));
								*/
							}
						}
					}
				} while(li.hasNext());
        	    
        	  
				
				//A11 data mhs
				
				
        	    // Write the output to a file
        	    FileOutputStream fileOut = new FileOutputStream(outFile);
        	    wb.write(fileOut);
        	    //System.out.println("14");
        	    fileOut.close();
        	    //System.out.println("15");
    		}
    		catch (Exception e) {
    			//System.out.println("poi err "+e);
    		}
    	}
    	else {
    		//System.out.println("excel missing");
    	}
    	//System.out.println("done");
    	return out_file;
    }
    
    
}

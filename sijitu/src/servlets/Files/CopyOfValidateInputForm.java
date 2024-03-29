package servlets.Files;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.Servlet;
import java.io.*;
import java.util.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import com.missiondata.fileupload.*;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
import listeners.*;
/**
 * Servlet implementation class ValidateInputForm
 */
@WebServlet("/CopyOfValidateInputForm")
public class CopyOfValidateInputForm extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
    String msg = "";
    boolean valid = true;
    String field_name = "";
    String fwdPageIfValid = "";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CopyOfValidateInputForm() {
        super();
        
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet");
		
		///String tipeForm = request.getParameter("tipeForm_String_Opt");
		//form2 - dpp
		///String namaPenyetor=request.getParameter("Nama-Penyetor_Huruf_Opt");
		///String besaran=request.getParameter("Besaran_Double_Wajib");
		///String tglTrans=request.getParameter("Tgl-Transaksi_Date_Wajib");
		///String angsuranKe=request.getParameter("Angsuran-DPP-Ke_Int_Wajib");
		///String gelombangKe=request.getParameter("Gelombang_Int_Wajib");
		///String objId = request.getParameter("idObj_Int_Opt");
		///String nmm = request.getParameter("nmm_String_Opt");
		///String npm = request.getParameter("npm_String_Opt");
		///String kdpst = request.getParameter("kdpst_String_Opt");
		///String obj_lvl =  request.getParameter("objLvl_Int_Opt");
		
		
		
		//msg = request.getParameter("minor");
		///System.out.println("nmm npm ="+nmm+"-"+npm+"-"+fwdPageIfValid);
		System.out.println("fwdPageIfValid ="+fwdPageIfValid);
		System.out.println("msg ="+msg);
		if(valid) {
			///msg = "<meta http-equiv=\"refresh\" content=\"0; url="+fwdPageIfValid+"?tipeForm="+tipeForm+"&objId="+objId+"&nmm="+nmm+"&npm="+npm+"&kdpst="+kdpst+"&obj_lvl="+obj_lvl+"&namaPenyetor="+namaPenyetor+"&besaran="+besaran+"&tglTrans="+tglTrans+"&angsuranKe="+angsuranKe+"&gelombangKe="+gelombangKe+"\" >";
			msg = "<meta http-equiv=\"refresh\" content=\"0; url="+fwdPageIfValid+"\" >";
	    }
		
	    response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
	    response.getWriter().write(msg); 
	    msg="";
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("doPusr");
		//StringTokenizer st = null;
		/*
		 * perhatikan huruf pertama = huruf besar
		 */
		//wajib ada field "elementValue" untuk forward value
		//format = Nama_Tipe-data_Status
		//Status = Wajib,Opt
		//Tipe-data = Int,Double,String,TimeStamp,Email,Date,Time,Huruf,Angka,Hape,Telp
		HttpSession session = request.getSession(true);
		valid = true;
		field_name = "";
		Enumeration attrs =  request.getParameterNames();
		Enumeration attrTmp =  request.getParameterNames();
		String somebutotonValue =null;
		while(attrs.hasMoreElements()) {
			boolean validasiTest = true;
			String elementName = (String)attrs.nextElement();
			//khusus button aja 
			if(elementName.equalsIgnoreCase("somebutton")) {
				somebutotonValue=request.getParameter("somebutton");
			}
			else {
				String elementValue = request.getParameter(elementName);
				if(Checker.isStringNullOrEmpty(elementValue)) {
					elementValue = "null";
				}
				if(elementName.contains("fwdPageIfValid")) {
					fwdPageIfValid = ""+elementValue;
				}
				field_name=field_name+"||"+elementName+"||"+elementValue;
			//System.out.println("elementName="+elementName);
			//System.out.println("elementValue="+elementValue);
				StringTokenizer st = new StringTokenizer(elementName,"_");
				String namaElement = st.nextToken().replace("-", " ");
			//System.out.println("namaElement="+namaElement);
				String dataTypeElement = st.nextToken();
				//System.out.println("dataTypeElement="+dataTypeElement);
				String StatusElement = st.nextToken();
				//System.out.println("StatusElement="+StatusElement);
				if(StatusElement.contains("Wajib")) {
					String alasan_err = "";
					if(dataTypeElement.contains("Double")) {
						validasiTest = validasiAngka(elementValue);
						alasan_err="Harap diisi hanya dengan angka.<br/>";
					}
					else {
						if(dataTypeElement.contains("Tgl")||dataTypeElement.contains("Date")) {
							validasiTest = validasiDate(elementValue);
							alasan_err="Harap diisi sesuai dengan format yang ditentukan.<br/>";
						}
						else {
							if(dataTypeElement.contains("Int")||dataTypeElement.contains("Integer")) {
								validasiTest = validasiInt(elementValue);
								alasan_err="Harap diisi dengan angka tanpa bilangan desimal.<br/>";
							}
							else {
								if(dataTypeElement.contains("Hape")||dataTypeElement.contains("Telp")) {
									validasiTest = validasiInt(elementValue);
									alasan_err="Harap diisi dengan hanya menggunakan angka.<br/>";
								}
								else {
									if(dataTypeElement.contains("Email")) {
										validasiTest = validasiEmail(elementValue);
										alasan_err="Harap diisi dengan benar.<br/>";
									}
									else {
										if(dataTypeElement.contains("huruf")) {
											validasiTest = validasiHuruf(elementValue);
											alasan_err="Harap diisi hanya dengan huruf.<br/>";
										}
										else {
											if(dataTypeElement.contains("String")) {
												System.out.println("sinsi");
												st = new StringTokenizer(elementName,"_");
												if(st.countTokens()>3) {
													String namaElement_ = st.nextToken().replace("-", " ");
													String dataTypeElement_ = st.nextToken();
													String StatusElement_ = st.nextToken();
													String namaElemenPenentu = st.nextToken();
													String valueElemenPenentu = st.nextToken();
													System.out.println("namaElemenPenentu="+namaElemenPenentu);
													System.out.println("valueElemenPenentu="+valueElemenPenentu);
													String tmp = request.getParameter(namaElemenPenentu);
													System.out.println("tmp="+tmp);
													if(valueElemenPenentu.equalsIgnoreCase(tmp)) {
														System.out.println("proses validasi=");
														validasiTest = validasiString(elementValue);
														alasan_err="Harap diisi.<br/>";
													}
													else {
													//	kalo tidak sesuai maka status optional, tidak perlu dites
													}
												}
												else {
													validasiTest = validasiString(elementValue);
													alasan_err="Harap diisi.<br/>";
												}	
											}
										}
									}
								}
							}
						}
					}
					if(!validasiTest) {
						valid = false;
						if(msg==null) {
							msg = "'"+ namaElement +"', "+ alasan_err;
						}
						else {
							msg = msg +"'"+ namaElement +"', "+ alasan_err;
						}
					}
				}
			}//end else
		}

		//msg=""+request.getParameter("penyetor");
		//System.out.println("field_name=="+field_name);
		//System.out.println("valid=="+valid);
		//System.out.println("msg=="+msg);
		if(false) {
			//kalo pake sistem ini jadi dobel krn yg diupdate div
			String target = Constants.getRootWeb()+fwdPageIfValid;
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url).forward(request,response);
		}
		else {
			if(valid) {
				session.setAttribute("fieldAndValue", field_name);
			}
			doGet(request,response);
		}	
	} 
	
	public boolean validasiAngka(String value) {
		boolean valid = true;
		if(value==null) {
			valid = false;
		}
		else {
			try {
				
				Double.valueOf(value).doubleValue();
			}
			catch(Exception e) {
				valid = false;
			}
		}
		return valid;
	}
	
	public boolean validasiInt(String value) {
		boolean valid = true;
		if(value==null) {
			valid = false;
		}
		else {
			try {
				Integer.valueOf(value).intValue();
			}
			catch(Exception e) {
				valid = false;
			}
		}	
		return valid;
	}
	
	public boolean validasiEmail(String value) {
		boolean valid = true;
		if(value==null) {
			valid = false;
		}
		else {
			if(!value.contains("@")&&!value.contains(".")&&value.length()<7) {
				valid = false;
			}
		}	
		return valid;
	}	
	
	public boolean validasiDate(String value) {
		boolean valid = true;
		if(value==null) {
			valid = false;
		}
		else {
			try {
				StringTokenizer st = null;
				if(value.contains("/")) {
					st = new StringTokenizer(value,"/");
				}
				else {
					st = new StringTokenizer(value,"-");
				}
				//System.out.println("cont token = "+st.countTokens());
				if(st.countTokens()!=3) {
					valid = false;
				}
				else {
					String tgl = st.nextToken();
					String bln = st.nextToken();
					String tahun = st.nextToken();
					//System.out.println(tahun+"-"+bln+"-"+tgl);
					java.sql.Date.valueOf(tahun+"-"+bln+"-"+tgl);
				}
				
			}
			catch(Exception e) {
				valid = false;
			}
		}	
		return valid;
	}
	
	public boolean validasiHuruf(String value) {
		boolean valid = true;
		if(value==null) {
			valid = false;
		}
		else {
			char[] chars = value.toCharArray();
			for (char c : chars) {
				if(!Character.isLetter(c)) {
					valid = false;
				}
			}
		}	
	    return valid;
	}
	
	public boolean validasiString(String value) {
		boolean valid = true;
		System.out.println("validasiString="+value);
		if(value==null || Checker.isStringNullOrEmpty(value)) {
			valid = false;
		}
		
	    return valid;
	}
}

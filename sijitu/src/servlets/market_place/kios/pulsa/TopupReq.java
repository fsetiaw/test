package servlets.market_place.kios.pulsa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import beans.dbase.classPoll.SearchDbClassPoll;
import beans.dbase.dosen.SearchDbDsn;
import beans.dbase.market_place.pulsa.ToolMip;
import beans.dbase.mhs.btstu.SearchDbInfoMhsBtstu;
import beans.dbase.mhs.kurikulum.SearchDbInfoKurikulum;
import beans.dbase.trlsm.UpdateDbTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class TopupReq
 */
@WebServlet("/TopupReq")
public class TopupReq extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopupReq() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");

		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
			//kode here
			/*
			 * AMBIL PARAM DARI FORM AWAL:
			 */
			String user_id = request.getParameter("user_id");
			String trx_id = request.getParameter("trx_id");
			String cmd = request.getParameter("cmd");
			String pin = request.getParameter("pin");
			String cust_id = request.getParameter("cust_id");
			String product = request.getParameter("product");
				
			
			/*
			 * creating signature
			 */
			ToolMip tm = new ToolMip();
			String mlink_usr_id = tm.getMlink_usr_id();
			
			String usr_id = tm.getMlink_usr_id();
			String signatur = tm.GenSign(trx_id);
			//System.out.println("sign="+ttd);
			//String signatur = mlink_usr_id+trx_id+app_tkn;
			//signatur = DigestUtils.shaHex(signatur);
			
			String pesan_xml = "<?xml version=\"1.0\"?><mlinkRequest><user_id>"+mlink_usr_id+"</user_id><cmd>"+cmd+"</cmd><trx_id>"+trx_id+"</trx_id><pin>"+pin+"</pin><cust_id>"+cust_id+"</cust_id><product>"+product+"</product><sign>"+signatur+"</sign></mlinkRequest>";
			
			
				
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost postRequest = new HttpPost(
					"http://202.43.169.131:7775/xml");
				
				StringEntity input = new StringEntity(pesan_xml);
				input.setContentType("text/xml");
				/*
				 *Set HEADER 
				 */
				postRequest.addHeader("content-type", "text/xml");
				postRequest.addHeader("Connection", "Keep-Alive");
				postRequest.setEntity(input);
					
				HttpResponse htresponse = httpClient.execute(postRequest);
				htresponse.addHeader("Connection", "Keep-Alive");
				BufferedReader br = new BufferedReader(
			                        new InputStreamReader((htresponse.getEntity().getContent())));

				String  feedback = "";
				String output = null;
				while ((output = br.readLine()) != null) {
					feedback = feedback+output;
				}
				httpClient.getConnectionManager().shutdown();
				
				
				/*
				 * create session variable untuk ditampilkan di FORM AWAL
				 */
				session.setAttribute("pesan_xml",pesan_xml);
				session.setAttribute("signature",signatur);
				session.setAttribute("feedback",feedback);
				
				
				/*
				 * FORWARD KE FORM AWAL
				 */
				String target = Constants.getRootWeb()+"/InnerFrame/market_place/tmp.jsp";
				String uri = request.getRequestURI();
				String url = PathFinder.getPath(uri, target);
				request.getRequestDispatcher(url).forward(request,response);

			}
			catch(Exception e) {
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

package servlets.ajax;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class UpdateUsername
 */
@WebServlet("/update")
public class UpdateUsername extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUsername() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("okay");
		int counter = 0;
		
		Map <String, Object> map = new HashMap<String, Object>();
		boolean isValid=false;
		String username =request.getParameter("username");
		if(username!=null && username.trim().length()>0) {
			isValid=true;
			map.put("username", username);
		}	
		map.put("isValid", isValid);
			//for(int i=0;i<10;i++) {
			//	counter++;
		waitForData();
		System.out.println("ok - "+counter);
		write(response,map);
		waitForData();
		write(response,map);
		waitForData();
		write(response,map);
		waitForData();
			//}
		
		
	}
	

    private void write(HttpServletResponse response,Map<String,Object>map) throws IOException{
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8");
    	response.getWriter().write(new Gson().toJson(map));
    }
    
	private static void waitForData() {
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(2000));
	    } catch (InterruptedException e) {
	    	e.printStackTrace();
	    }
	}
}

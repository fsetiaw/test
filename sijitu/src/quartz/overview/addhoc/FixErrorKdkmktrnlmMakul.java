package quartz.overview.addhoc;
import java.util.ListIterator;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;  
import org.apache.commons.logging.LogFactory;  
import org.quartz.Job;  

import org.quartz.JobDataMap;  
import org.quartz.JobExecutionContext;  
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import beans.dbase.SearchDb;
import beans.dbase.makul.SearchDbMk;
import beans.dbase.overview.GetSebaranTrlsm;
import beans.login.InitSessionUsr;
import beans.tools.AddHocFunction;
import beans.tools.Checker;
import beans.tools.Getter;  

//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;

public class FixErrorKdkmktrnlmMakul implements Job {
	public static final String npm = "url";
    @Override
    public void execute(final JobExecutionContext ctx)
            throws JobExecutionException {
    	JobDataMap dataMap = ctx.getJobDetail().getJobDataMap();
    	//JobDataMap data = context.MergedJobDataMap;

        String command = dataMap.getString("url");
		//fetch parameters from JobDataMap
		//String npm_user = dataMap.getString(npm);
		JobKey jobKey = ctx.getJobDetail().getKey();
		
    	try {
    		SearchDbMk sdm = new SearchDbMk();
    		String thsms_krs = Checker.getThsmsKrs();
			Vector v = sdm.findMkError(null,thsms_krs);
    		Vector v_unfixable = AddHocFunction.tryFixErrorDariPenggabunganKlsProses(v);
    		ListIterator li = v_unfixable.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			System.out.println(brs);
    		}

    	}
    	catch(Exception e) {}
    	
    	
        //System.out.println(jobKey+" == overview mhs cuti");
    	//System.out.println(command+" == overview mhs cuti "+jobKey);

    }
    
}

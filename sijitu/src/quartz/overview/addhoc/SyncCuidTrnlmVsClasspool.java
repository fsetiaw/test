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
import beans.dbase.overview.GetSebaranTrlsm;
import beans.dbase.trnlm.UpdateDbTrnlm;
import beans.login.InitSessionUsr;
import beans.tools.AddHocFunction;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;  

//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;

public class SyncCuidTrnlmVsClasspool implements Job {
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
    		Vector v_id = Getter.returnListProdiOnlySortByKampusWithListIdobj();
    		Vector v_scope_id = Converter.convertVscopeidToKdpst(v_id);
			
			UpdateDbTrnlm udb = new UpdateDbTrnlm();
			String target_thsms = Checker.getThsmsBukaKelas();
			udb.sinkCuidIdWithClassPoll(target_thsms, null, v_scope_id);
			if(!Checker.getThsmsNow().equalsIgnoreCase(target_thsms)) {
				target_thsms = Checker.getThsmsNow();
				udb.sinkCuidIdWithClassPoll(target_thsms, null, v_scope_id);
			}
    		
    		

    	}
    	catch(Exception e) {}
    	
    	
        //System.out.println(jobKey+" == overview mhs cuti");
    	//System.out.println(command+" == overview mhs cuti "+jobKey);

    }
    
}

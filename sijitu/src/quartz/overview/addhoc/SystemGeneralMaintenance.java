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
import beans.tools.Getter;
import beans.tools.Tool;  

//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;

public class SystemGeneralMaintenance implements Job {
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
    		//String target_thsms = Checker.getThsmsHeregistrasi();
    		//String target_thsms_1 = Tool.returnPrevThsmsGivenTpAntara(target_thsms);
    		//String target_thsms_2 = Tool.returnPrevThsmsGivenTpAntara(target_thsms_1);
    		//String target_thsms_3 = Tool.returnPrevThsmsGivenTpAntara(target_thsms_2);
    		AddHocFunction ahf = new AddHocFunction();
    		AddHocFunction.hapusFileAtTmpFolder();
    		//reset KRS_WHITE_LIST bagi NPM yg token thsms = null;
    		UpdateDbTrnlm udb = new UpdateDbTrnlm();
			udb.resetNullThsmsKrsWhitelist();
			double day_before_exp = 1;
			int upd = udb.deleteAllThsmsKrsWhitelist(day_before_exp);
			//System.out.println("updated = "+upd);
    		//AddHocFunction.sinkTopikPengajuanRequestDgnTabelOverviewSebaranTrlsm("KELULUSAN", target_thsms);
    		
    		//AddHocFunction.sinkTopikPengajuanRequestDgnTabelOverviewSebaranTrlsm("KELULUSAN", target_thsms_2);
    		//AddHocFunction.sinkTopikPengajuanRequestDgnTabelOverviewSebaranTrlsm("KELULUSAN", target_thsms_3);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	
        //System.out.println(jobKey+" == overview mhs cuti");
    	//System.out.println(command+" == overview mhs cuti "+jobKey);

    }
    
}

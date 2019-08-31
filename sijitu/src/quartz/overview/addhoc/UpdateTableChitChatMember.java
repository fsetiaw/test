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
import beans.login.InitSessionUsr;
import beans.tools.AddHocFunction;
import beans.tools.Checker;
import beans.tools.Getter;  

//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;

public class UpdateTableChitChatMember implements Job {
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
    		

    		
    		AddHocFunction.syncChitChatMemberTable("A");
    		AddHocFunction.syncChitChatMemberTable("B");
    		AddHocFunction.syncChitChatMemberTable("C");
    		AddHocFunction.syncChitChatMemberTable("D");
    		AddHocFunction.syncChitChatMemberTable("E");
    		AddHocFunction.syncChitChatMemberTable("F");
    		AddHocFunction.syncChitChatMemberTable("G");
    		AddHocFunction.syncChitChatMemberTable("H");
    		AddHocFunction.syncChitChatMemberTable("I");
    		AddHocFunction.syncChitChatMemberTable("J");
    		AddHocFunction.syncChitChatMemberTable("K");
    		AddHocFunction.syncChitChatMemberTable("L");
    		AddHocFunction.syncChitChatMemberTable("M");
    		AddHocFunction.syncChitChatMemberTable("N");
    		AddHocFunction.syncChitChatMemberTable("O");
    		AddHocFunction.syncChitChatMemberTable("P");
    		AddHocFunction.syncChitChatMemberTable("Q");
    		AddHocFunction.syncChitChatMemberTable("R");
    		AddHocFunction.syncChitChatMemberTable("S");
    		AddHocFunction.syncChitChatMemberTable("T");
    		AddHocFunction.syncChitChatMemberTable("U");
    		AddHocFunction.syncChitChatMemberTable("V");
    		AddHocFunction.syncChitChatMemberTable("W");
    		AddHocFunction.syncChitChatMemberTable("X");
    		AddHocFunction.syncChitChatMemberTable("Y");
    		AddHocFunction.syncChitChatMemberTable("Z");
    		
    		//System.out.println("pit3");
    		/*
    		 * ahf.initializeTableRule("CUTI_RULES",false);
    		ahf.updateTableRule("CUTI_RULES");
    		if(v!=null && v.size()>0) {
    			ListIterator li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				//System.out.println(brs);
    			}
    		}
    		*/
    	}
    	catch(Exception e) {}
    	
    	
        //System.out.println(jobKey+" == overview mhs cuti");
    	//System.out.println(command+" == overview mhs cuti "+jobKey);

    }
    
}

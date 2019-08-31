package quartz.overview.trlsm;
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
import beans.dbase.overview.maintenance.MaintenanceOverview;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Constant;
import beans.tools.Getter;
import beans.tools.Tool;  

//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;

public class OverviewTrlsmMhs implements Job {
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
    		//SearchDb sdb = new SearchDb();	
    		//Vector v = Getter.returnListProdiOnlySortByKampusWithListIdobj();
    		//System.out.println("pit1");
    		String kdpti = Constants.getKdpti();
    		String based_thsms = Constant.getValue("BASED_THSMS");
    		String thsms_now = Checker.getThsmsNow();
    		//GetSebaranTrlsm gt = new GetSebaranTrlsm();
    		MaintenanceOverview mo = new MaintenanceOverview();
    		Vector v_scope_id_keseluruhan = Getter.get_v_scope_id_keseluruhan(); 
    		//System.out.println("pit2");
    		for(int i=0;i<3;i++)  {
    			//System.out.println("thsms_now="+thsms_now);
    			//System.out.println("based_thsms="+based_thsms);
    			//System.out.println(thsms_now.compareToIgnoreCase(based_thsms));
    			if(thsms_now.compareToIgnoreCase(based_thsms)>=0) {
    				//System.out.println("thsms="+thsms_now);
    				try {
    					mo.initializeTableOverviewSebaranTrlsm_step1(thsms_now);
        				//System.out.println("keluar");
        				mo.maintenaceCountDataMhsGivenStmhs_step2((Vector)v_scope_id_keseluruhan.clone(), "K", thsms_now);
        				//System.out.println("keluar-2c");
        				mo.maintenaceCountDataMhsGivenStmhsInProgress_step3((Vector)v_scope_id_keseluruhan.clone(), "K", thsms_now);
        				//System.out.println("keluar-2d");
        				mo.maintenaceUpdateListMhsGivenStmhsInProgress_step4((Vector)v_scope_id_keseluruhan.clone(), "K", thsms_now);
        				//System.out.println("CUTI-2e");
        				//System.out.println("cuti");
        				mo.maintenaceCountDataMhsGivenStmhs_step2((Vector)v_scope_id_keseluruhan.clone(), "C", thsms_now);
        				//System.out.println("CUTI-2c");
        				mo.maintenaceCountDataMhsGivenStmhsInProgress_step3((Vector)v_scope_id_keseluruhan.clone(), "C", thsms_now);
        				//System.out.println("CUTI-2d");
        				mo.maintenaceUpdateListMhsGivenStmhsInProgress_step4((Vector)v_scope_id_keseluruhan.clone(), "C", thsms_now);
        				//System.out.println("CUTI-2e");
        				//System.out.println("DO");
        				mo.maintenaceCountDataMhsGivenStmhs_step2((Vector)v_scope_id_keseluruhan.clone(), "D", thsms_now);
        				//System.out.println("DO-2c");
        				mo.maintenaceCountDataMhsGivenStmhsInProgress_step3((Vector)v_scope_id_keseluruhan.clone(), "D", thsms_now);
        				//System.out.println("DO-2d");
        				mo.maintenaceUpdateListMhsGivenStmhsInProgress_step4((Vector)v_scope_id_keseluruhan.clone(), "D", thsms_now);
        				//System.out.println("DO-2e");
        				
        				//System.out.println("LULUS");
        				mo.maintenaceCountDataMhsGivenStmhs_step2((Vector)v_scope_id_keseluruhan.clone(), "L", thsms_now);
        				//System.out.println("LULUS-2c");
        				mo.maintenaceCountDataMhsGivenStmhsInProgress_step3((Vector)v_scope_id_keseluruhan.clone(), "L", thsms_now);
        				//System.out.println("LULUS-2d");
        				mo.maintenaceUpdateListMhsGivenStmhsInProgress_step4((Vector)v_scope_id_keseluruhan.clone(), "L", thsms_now);
        				//System.out.println("LULUS-2e");
    				}
    				catch(Exception e) {
    					e.printStackTrace();
    				}
    				
    				
    				thsms_now = Tool.returnPrevThsmsGivenTpAntara(thsms_now);
    			}
    		}	

    		
    		/*
    		gt.updateOverviewSebaranTrlsmTable(based_thsms,kdpti);
    		for(int i=0;i<3;i++)  {
    			//System.out.println("thsms_now="+thsms_now);
    			//System.out.println("based_thsms="+based_thsms);
    			//System.out.println(thsms_now.compareToIgnoreCase(based_thsms));
    			if(thsms_now.compareToIgnoreCase(based_thsms)>=0) {
    				//System.out.println(i+"."+thsms_now);
    				gt.updateOverviewSebaranTrlsmTable(thsms_now,kdpti);
    				mo.maintenaceCountDataMhsGivenStmhs((Vector)v_scope_id_keseluruhan.clone(), "K", thsms_now);
    				mo.maintenaceCountDataMhsGivenStmhsInProgress((Vector)v_scope_id_keseluruhan.clone(), "K", thsms_now);
    				thsms_now = Tool.returnPrevThsmsGivenTpAntara(thsms_now);
    			}
    		//while(based_thsms.compareToIgnoreCase(thsms_now)<=0) {
    			//System.out.println("prosessing="+based_thsms);
    			//gt.updateOverviewSebaranTrlsmTable(based_thsms,kdpti);
    			//based_thsms = Tool.returnNextThsmsGiven(based_thsms);
    		}
    		*/
    		/*
    		gt.updateOverviewSebaranTrlsmTable(based_thsms,kdpti);
    		if(!Checker.getThsmsPengajuanStmhs().equalsIgnoreCase(Checker.getThsmsNow())) {
    			gt.updateOverviewSebaranTrlsmTable(Checker.getThsmsNow(),kdpti);	
    		}
    		*/
    		
    		//System.out.println("pit3");
    		/*
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

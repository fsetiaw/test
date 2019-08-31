package beans.dbase.tbbnl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import beans.dbase.UpdateDb;
import beans.tools.Checker;
import beans.tools.DateFormater;
import beans.tools.Getter;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;



/**
 * Session Bean implementation class UpdateDbTbbnl
 */
@Stateless
@LocalBean
public class UpdateDbTbbnl extends UpdateDb {
	String operatorNpm;
	String operatorNmm;
	String tknOperatorNickname;
	boolean petugas;
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;  
    /**
     * @see UpdateDb#UpdateDb()
     */
    public UpdateDbTbbnl() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbTbbnl(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }


    public void updateParamNilaiBobot(String target_thsms,String[]prodi,String list_nilai_bobot) {
    	if(prodi!=null && prodi.length>0 && list_nilai_bobot!=null && list_nilai_bobot.length()>1) {
    		list_nilai_bobot = list_nilai_bobot.replace(" ", "");
    		list_nilai_bobot = list_nilai_bobot.replace("][", "`");
    		list_nilai_bobot = list_nilai_bobot.replace("[", "");
    		list_nilai_bobot = list_nilai_bobot.replace("]", "");
    		//list_nilai_bobot = list_nilai_bobot.replace(",", "`");
    		//list_nilai_bobot = list_nilai_bobot.replace("/", "`");
    		//System.out.println("list_nilai_bobot="+list_nilai_bobot);
    		StringTokenizer st = new StringTokenizer(list_nilai_bobot,"`");
    		StringTokenizer st1 = null;
    		String nlakh = "", bobot="";
    		String min = "",max="";
    		list_nilai_bobot = "";
    		while(st.hasMoreTokens()) {
    			String tkn = (String)st.nextToken();
    			st1 = new StringTokenizer(tkn,"/");
    			if(st1.countTokens()==4) {
    				nlakh = st1.nextToken();
    				bobot = st1.nextToken();
    				min = st1.nextToken();
    				max = st1.nextToken();
    			}
    			else {
    				nlakh = st1.nextToken();
    				bobot = st1.nextToken();
    				min = new String(nlakh);
    				max = new String(nlakh);
    			}
    			list_nilai_bobot = list_nilai_bobot+"`"+nlakh+"`"+bobot+"`"+min+"`"+max;
    		}


    		//System.out.println("list_nilai_bobot="+list_nilai_bobot);
    		
    		try {
        		//String ipAddr =  request.getRemoteAddr();
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//delete prev record
        		
        		stmt = con.prepareStatement("delete from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=?");
        		for(int i=0;i<prodi.length;i++) {
        			st = new StringTokenizer(prodi[i],"`");
        			st1 = null;
        			while(st.hasMoreTokens()) {
        				String kdpst = st.nextToken();
        				stmt.setString(1, target_thsms);
        				stmt.setString(2, kdpst);
        				stmt.executeUpdate();
        			}
        		}	
        		//update
        		
        		stmt = con.prepareStatement("insert into TBBNL(THSMSTBBNL,KDPSTTBBNL,NLAKHTBBNL,BOBOTTBBNL,ACTIVE,NILAI_MIN,NILAI_MAX)values(?,?,?,?,?,?,?)");
        		//stmt = con.prepareStatement("UPDATE TBBNL set BOBOTTBBNL=?,ACTIVE=? where THSMSTBBNL=? and KDPSTTBBNL=? and NLAKHTBBNL=?");
        		for(int i=0;i<prodi.length;i++) {
        			st = new StringTokenizer(prodi[i],"`");
        			st1 = null;
        			while(st.hasMoreTokens()) {
        				String kdpst = st.nextToken();
        				st1 = new StringTokenizer(list_nilai_bobot,"`");
        				while(st1.hasMoreTokens()) {
        					nlakh = st1.nextToken();
        					bobot = st1.nextToken();
        					min = st1.nextToken();
        					max = st1.nextToken();
        					stmt.setString(1, target_thsms);
        					stmt.setString(2, kdpst);
        					stmt.setString(3, nlakh);
        					stmt.setDouble(4, Double.parseDouble(bobot));
        					stmt.setBoolean(5, true);
        					stmt.setDouble(6, Double.parseDouble(min));
        					stmt.setDouble(7, Double.parseDouble(max));
        					
        					stmt.executeUpdate();
        					//System.out.println(kdpst+" = "+upd);
        					
        				}
        			}
        		}
        	}
        	catch (NamingException e) {
        		e.printStackTrace();
        	}
        	catch (SQLException ex) {
        		ex.printStackTrace();
        	} 
        	finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		    if (con!=null) try { con.close();} catch (Exception ignore){}
    		}
    	}
    	
    }	

    
    
    public void getDistinckNlahBobotInsertKeTargetThsms(String target_thsms) {
    	Vector v_prodi = Getter.getListProdi();
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		Vector v_nilai = new Vector();
    		ListIterator lin = v_nilai.listIterator();
    		stmt = con.prepareStatement("SELECT DISTINCT NLAKHTBBNL,BOBOTTBBNL FROM TBBNL order by BOBOTTBBNL");
    		rs = stmt.executeQuery();
			while(rs.next()) {
				String nlakh = rs.getString(1);
				double bobot  = rs.getDouble(2);
				lin.add(nlakh+"`"+bobot);
			}
			
			stmt = con.prepareStatement("insert ignore into TBBNL(THSMSTBBNL,KDPSTTBBNL,NLAKHTBBNL,BOBOTTBBNL,ACTIVE,NILAI_MIN,NILAI_MAX)values(?,?,?,?,?,?,?)");
    		ListIterator li = v_prodi.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kdpst = st.nextToken();
    			//System.out.println("insert "+kdpst);
    			lin = v_nilai.listIterator();
        		while(lin.hasNext()) {
        			String brs1 = (String)lin.next();
        			st = new StringTokenizer(brs1,"`");
        			String nlakh = st.nextToken();
        			String bobot = st.nextToken();
        			stmt.setString(1, target_thsms);
        			stmt.setString(2, kdpst);
        			stmt.setString(3, nlakh);
        			stmt.setFloat(4, Float.parseFloat(bobot));
        			stmt.setBoolean(5, true);
        			if(nlakh.equalsIgnoreCase("A")) {
        				stmt.setDouble(6, 90);
            			stmt.setDouble(7, 100);	
        			}	
        			else if(nlakh.equalsIgnoreCase("A-")) {
        				stmt.setDouble(6, 80);
            			stmt.setDouble(7, 89.99);
        			}
        			else if(nlakh.equalsIgnoreCase("B+")) {
        				stmt.setDouble(6, 75);
            			stmt.setDouble(7, 79.99);
        			}
        			else if(nlakh.equalsIgnoreCase("B")) {
        				stmt.setDouble(6, 70);
            			stmt.setDouble(7, 74.99);
        			}
        			else if(nlakh.equalsIgnoreCase("B-")) {
        				stmt.setDouble(6, 65);
            			stmt.setDouble(7, 69.99);
        			}
        			else if(nlakh.equalsIgnoreCase("C+")) {
        				stmt.setDouble(6, 61.66);
            			stmt.setDouble(7, 64.99);
        			}
        			else if(nlakh.equalsIgnoreCase("C")) {
        				stmt.setDouble(6, 58.33);
            			stmt.setDouble(7, 61.65);
        			}
        			else if(nlakh.equalsIgnoreCase("C-")) {
        				stmt.setDouble(6, 55);
            			stmt.setDouble(7, 58.32);
        			}
        			else if(nlakh.equalsIgnoreCase("D")) {
        				stmt.setDouble(6, 45);
            			stmt.setDouble(7, 54.99);
        			}
        			else if(nlakh.equalsIgnoreCase("F") || nlakh.equalsIgnoreCase("E")) {
        				stmt.setDouble(6, 0);
            			stmt.setDouble(7, 44.99);
        			}
        			else if(nlakh.equalsIgnoreCase("T")) {
        				stmt.setDouble(6, 0);
            			stmt.setDouble(7, 0);	
        			}
        			else {
        				stmt.setFloat(6, Float.parseFloat(nlakh));
            			stmt.setFloat(7, Float.parseFloat(nlakh));	
        			}
        			stmt.executeUpdate();
        		}
    		}
    		
			stmt = con.prepareStatement("update ignore TBBNL set BOBOTTBBNL=?,NILAI_MIN=?,NILAI_MAX=?,ACTIVE=? where THSMSTBBNL=? and KDPSTTBBNL=? and NLAKHTBBNL=?");
    		li = v_prodi.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kdpst = st.nextToken();
    			//System.out.println("update"+kdpst);
    			lin = v_nilai.listIterator();
        		while(lin.hasNext()) {
        			String brs1 = (String)lin.next();
        			st = new StringTokenizer(brs1,"`");
        			String nlakh = st.nextToken();
        			String bobot = st.nextToken();
        			if(nlakh.equalsIgnoreCase("A")) {
        				stmt.setFloat(1, Float.parseFloat(bobot));
        				stmt.setDouble(2, 90);
            			stmt.setDouble(3, 100);	
        			}	
        			else if(nlakh.equalsIgnoreCase("A-")) {
        				stmt.setFloat(1, Float.parseFloat(bobot));
        				stmt.setDouble(2, 80);
            			stmt.setDouble(3, 89.99);
        			}
        			else if(nlakh.equalsIgnoreCase("B+")) {
        				stmt.setFloat(1, Float.parseFloat(bobot));
        				stmt.setDouble(2, 75);
            			stmt.setDouble(3, 79.99);
        			}
        			else if(nlakh.equalsIgnoreCase("B")) {
        				stmt.setFloat(1, Float.parseFloat(bobot));
        				stmt.setDouble(2, 70);
            			stmt.setDouble(3, 74.99);
        			}
        			else if(nlakh.equalsIgnoreCase("B-")) {
        				stmt.setFloat(1, Float.parseFloat(bobot));
        				stmt.setDouble(2, 65);
            			stmt.setDouble(3, 69.99);
        			}
        			else if(nlakh.equalsIgnoreCase("C+")) {
        				stmt.setFloat(1, Float.parseFloat(bobot));
        				stmt.setDouble(2, 61.66);
            			stmt.setDouble(3, 64.99);
        			}
        			else if(nlakh.equalsIgnoreCase("C")) {
        				stmt.setFloat(1, Float.parseFloat(bobot));
        				stmt.setDouble(2, 58.33);
            			stmt.setDouble(3, 61.65);
        			}
        			else if(nlakh.equalsIgnoreCase("C-")) {
        				stmt.setFloat(1, Float.parseFloat(bobot));
        				stmt.setDouble(2, 55);
            			stmt.setDouble(3, 58.32);
        			}
        			else if(nlakh.equalsIgnoreCase("D")) {
        				stmt.setFloat(1, Float.parseFloat(bobot));
        				stmt.setDouble(2, 45);
            			stmt.setDouble(3, 54.99);
        			}
        			else if(nlakh.equalsIgnoreCase("F") || nlakh.equalsIgnoreCase("E")) {
        				stmt.setFloat(1, Float.parseFloat(bobot));
        				stmt.setDouble(2, 0);
            			stmt.setDouble(3, 44.99);
        			}
        			else if(nlakh.equalsIgnoreCase("T")) {
        				stmt.setFloat(1, Float.parseFloat(bobot));
        				stmt.setDouble(2, 0);
            			stmt.setDouble(3, 0);	
        			}
        			else {
        				stmt.setFloat(1, Float.parseFloat(bobot));
        				stmt.setFloat(2, Float.parseFloat(nlakh));
            			stmt.setFloat(3, Float.parseFloat(nlakh));	
        			}
        			stmt.setBoolean(4, true);
        			stmt.setString(5, target_thsms);
        			stmt.setString(6, kdpst);
        			stmt.setString(7, nlakh);
        			//System.out.println("update "+nlakh);
        			stmt.executeUpdate();
        		}
    		}
    		stmt = con.prepareStatement("update TBBNL set ACTIVE=? where (NLAKHTBBNL<>? and NLAKHTBBNL<>? and NLAKHTBBNL<>? and NLAKHTBBNL<>? and NLAKHTBBNL<>? and NLAKHTBBNL<>? and NLAKHTBBNL<>? and NLAKHTBBNL<>? and NLAKHTBBNL<>? and NLAKHTBBNL<>? and NLAKHTBBNL<>? and NLAKHTBBNL<>?) and THSMSTBBNL=?");
    		li = v_prodi.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kdpst = st.nextToken();
    			
    			String kdfak = st.nextToken();
    			String kdjen = st.nextToken(); 
    			if(!kdjen.equalsIgnoreCase("A") && !kdjen.equalsIgnoreCase("B")) {
    				//System.out.println("upd "+brs);
    				stmt.setBoolean(1, false);
    				stmt.setString(2, "A");
    				stmt.setString(3, "A-");
    				stmt.setString(4, "B+");
    				stmt.setString(5, "B");
    				stmt.setString(6, "B-");
    				stmt.setString(7, "C+");
    				stmt.setString(8, "C");
    				stmt.setString(9, "C-");
    				stmt.setString(10, "D");
    				stmt.setString(11, "E");
    				stmt.setString(12, "F");
    				stmt.setString(13, "T");
    				stmt.setString(14, target_thsms);
    				stmt.executeUpdate();
    			}
    		}	
    		stmt = con.prepareStatement("update TBBNL set ACTIVE=? where THSMSTBBNL=? and (KDPSTTBBNL=? or KDPSTTBBNL=? or KDPSTTBBNL=?)");
    		stmt.setBoolean(1, true);
    		stmt.setString(2, target_thsms);
    		stmt.setString(3, "65101");
    		stmt.setString(4, "61101");
    		stmt.setString(5, "65001");
    		stmt.executeUpdate();
    		
    	}
    	catch (NamingException e) {
    		e.printStackTrace();
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	} 
    	finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	
    }	

    public int copyNilaiKeKolomRobot(String thsms) {
    	int tot_rec_effected=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		Vector v_nilai = new Vector();
    		ListIterator lin = v_nilai.listIterator();
    		stmt = con.prepareStatement("SELECT NPMHSTRNLM,KDKMKTRNLM,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM from TRNLM where THSMSTRNLM=?");
    		stmt.setString(1, thsms);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String npmhs = ""+rs.getString(1);
    			String kdkmk = ""+rs.getString(2);
    			String nilai = ""+rs.getFloat(3);
    			String nlakh = ""+rs.getString(4);
    			String bobot = ""+rs.getFloat(5);
    			lin.add(npmhs+"`"+kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot);
    		}
    		//System.out.println("size="+v_nilai.size());
    		stmt = con.prepareStatement("update TRNLM set NILAIROBOT=?,NLAKHROBOT=?,BOBOTROBOT=? where THSMSTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=?");
    		lin = v_nilai.listIterator();
    		while(lin.hasNext()) {
    			String brs = (String)lin.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String npmhs = st.nextToken();
    			String kdkmk = st.nextToken();
    			String nilai = st.nextToken();
    			if(Checker.isStringNullOrEmpty(nilai)) {
    				nilai = "0";
    			}
    			String nlakh = st.nextToken();
    			if(Checker.isStringNullOrEmpty(nlakh)) {
    				nlakh = "T";
    			}
    			String bobot = st.nextToken();
    			if(Checker.isStringNullOrEmpty(bobot)) {
    				bobot = "0";
    			}
    			stmt.setFloat(1, Float.parseFloat(nilai));
    			stmt.setString(2, nlakh);
    			stmt.setFloat(3, Float.parseFloat(bobot));
    			stmt.setString(4, thsms);
    			stmt.setString(5, npmhs);
    			stmt.setString(6, kdkmk);
    			tot_rec_effected=tot_rec_effected+stmt.executeUpdate();
    			//System.out.println("tot_rec_effected="+tot_rec_effected);;
    		}
    	}
    	catch (NamingException e) {
    		e.printStackTrace();
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	} 
    	finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return tot_rec_effected;
    }	
}

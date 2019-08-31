package beans.dbase.spmi.overview;

import beans.dbase.UpdateDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.StringTokenizer;
import java.util.ListIterator;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class UpdateOverviewSpmiTable
 */
@Stateless
@LocalBean
public class UpdateOverviewSpmiTable extends UpdateDb {
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
    public UpdateOverviewSpmiTable() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateOverviewSpmiTable(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    
    public int updateNotificationJabatanOverviewSpmi(Vector v_spmiManual_getSurveyYgBlumDiEvaluasi, String target_cycle_spmi) {
    	//pilihan target_cycle_spmi = plan,do,study,act
    	int updated = 0;
    	if(v_spmiManual_getSurveyYgBlumDiEvaluasi!=null && v_spmiManual_getSurveyYgBlumDiEvaluasi.size()>0) {
    		try {
    			Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	
            	//tahapan evaluasi dan pengendalian
            	Vector v_tmp = null;
            	stmt = con.prepareStatement("SELECT NOTIFY_TKN_JABATAN,NOTIFY_TKN_NPM,KDPST,FAKULTAS FROM OVERVIEW_SPMI where TAHAPAN_CYCLE_SPMI=?");
            	stmt.setString(1, target_cycle_spmi);
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		v_tmp = new Vector();
            		ListIterator lit = v_tmp.listIterator();
            		String noted_jab = ""+rs.getString(1);
            		String noted_npm = ""+rs.getString(2);
            		String kdpst= ""+rs.getString(3);
            		String fak = ""+rs.getString(4);
            		String tmp = target_cycle_spmi+"~"+noted_jab+"~"+noted_npm+"~"+kdpst+"~"+fak;
            		lit.add(tmp.replace("~~", "~null~"));
            		while(rs.next()) {
            			noted_jab = ""+rs.getString(1);
                		noted_npm = ""+rs.getString(2);
                		kdpst= ""+rs.getString(3);
                		fak = ""+rs.getString(4);
                		tmp = target_cycle_spmi+"~"+noted_jab+"~"+noted_npm+"~"+kdpst+"~"+fak;
                		lit.add(tmp.replace("~~", "~null~"));
            		}
            	}
            	
            	if(v_tmp!=null && v_tmp.size()>0) {
            		ListIterator li = v_spmiManual_getSurveyYgBlumDiEvaluasi.listIterator();
            		while(li.hasNext()) {
            			String brs = (String)li.next();
            			StringTokenizer st = new StringTokenizer(brs,"~");
            			//tgl_sidak+"~"+kdpst+"~"+controller+"~"+surveyor;
            			String tgl_sidak = st.nextToken();
            			String kdpst = st.nextToken();
            			String controller = st.nextToken();
            			String surveyor = st.nextToken();//hanya boleh ssatu jabatan
            			boolean match = false;
            			ListIterator lit = v_tmp.listIterator();
            			while(!match && lit.hasNext()) {
            				brs = (String)lit.next();
            				st = new StringTokenizer(brs,"~");
            				//at_cycle+"~"+noted_jab+"~"+noted_npm+"~"+kdpst+"~"+fak;
            				String at_cycle = st.nextToken();
            				String noted_jab = st.nextToken();
            				if(!noted_jab.startsWith("`")) {
            					noted_jab = "`"+noted_jab;
            				}
            				if(!noted_jab.endsWith("`")) {
            					noted_jab = noted_jab+"`";
            				}
            				String noted_npm = st.nextToken();
            				String kdpst_val = st.nextToken();
            				String fak = st.nextToken();
            				if(at_cycle.equalsIgnoreCase("study")) {
            					//only 1 row per cycle / per kadpst
            					//jadi kalo kdpst match dan noted_jabatan blum termasuk controller, ya ditambah saja
            					if(kdpst.equalsIgnoreCase(kdpst_val) && noted_jab.contains("`"+controller+"`")) {
            						match = true;
                					li.remove();
            					}
            					else if(kdpst.equalsIgnoreCase(kdpst_val) && !noted_jab.contains("`"+controller+"`")) {
            						controller = noted_jab+controller+"`";
            						li.set(tgl_sidak+"~"+kdpst+"~"+controller+"~"+surveyor);
            					}
            				}
            				else if(at_cycle.equalsIgnoreCase("plan")) {
            				}
            				else if(at_cycle.equalsIgnoreCase("do")) {
            				}
            				else if(at_cycle.equalsIgnoreCase("act")) {
            				}
            			}	
            		}
            		li = v_spmiManual_getSurveyYgBlumDiEvaluasi.listIterator();
            		stmt = con.prepareStatement("insert ignore into OVERVIEW_SPMI(TAHAPAN_CYCLE_SPMI,KDPST,FAKULTAS,NOTIFY_TKN_JABATAN,NOTIFY_TKN_NPM)values(?,?,?,?,?)");
            		while(li.hasNext()) {
            			String brs = (String)li.next();
            			StringTokenizer st = new StringTokenizer(brs,"~");
            			//tgl_sidak+"~"+kdpst+"~"+controller+"~"+surveyor;
            			String tgl_sidak = st.nextToken();
            			String kdpst = st.nextToken();
            			String controller = st.nextToken();
            			String surveyor = st.nextToken();
            			stmt.setString(1, target_cycle_spmi);
            			stmt.setString(2, kdpst);
            			stmt.setNull(3, java.sql.Types.VARCHAR);
            			stmt.setString(4, controller);
            			stmt.setNull(5, java.sql.Types.VARCHAR);
            			updated = stmt.executeUpdate();
            			
            		}
            		
            		li = v_spmiManual_getSurveyYgBlumDiEvaluasi.listIterator();
            		stmt = con.prepareStatement("update ignore OVERVIEW_SPMI set NOTIFY_TKN_JABATAN=? where KDPST=?");
            		while(li.hasNext()) {
            			String brs = (String)li.next();
            			StringTokenizer st = new StringTokenizer(brs,"~");
            			//tgl_sidak+"~"+kdpst+"~"+controller+"~"+surveyor;
            			String tgl_sidak = st.nextToken();
            			String kdpst = st.nextToken();
            			String controller = st.nextToken();
            			String surveyor = st.nextToken();
            			stmt.setString(1, controller);	
            			stmt.setString(2, kdpst);
            			updated = stmt.executeUpdate();
            		}
            	}
            	else {
            		ListIterator li = v_spmiManual_getSurveyYgBlumDiEvaluasi.listIterator();
            		stmt = con.prepareStatement("insert ignore into OVERVIEW_SPMI(TAHAPAN_CYCLE_SPMI,KDPST,FAKULTAS,NOTIFY_TKN_JABATAN,NOTIFY_TKN_NPM)values(?,?,?,?,?)");
            		while(li.hasNext()) {
            			String brs = (String)li.next();
            			StringTokenizer st = new StringTokenizer(brs,"~");
            			//tgl_sidak+"~"+kdpst+"~"+controller+"~"+surveyor;
            			String tgl_sidak = st.nextToken();
            			String kdpst = st.nextToken();
            			String controller = st.nextToken();
            			String surveyor = st.nextToken();
            			stmt.setString(1, target_cycle_spmi);
            			stmt.setString(2, kdpst);
            			stmt.setNull(3, java.sql.Types.VARCHAR);
            			stmt.setString(4, controller);
            			stmt.setNull(5, java.sql.Types.VARCHAR);
            			updated = stmt.executeUpdate();            			
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
    	return updated;
    	
    }

}

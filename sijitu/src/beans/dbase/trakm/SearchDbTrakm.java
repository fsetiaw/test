package beans.dbase.trakm;

import beans.dbase.SearchDb;
import beans.tools.Checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class SearchDbTrakm
 */
@Stateless
@LocalBean
public class SearchDbTrakm extends SearchDb {
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
     * @see SearchDb#SearchDb()
     */
    public SearchDbTrakm() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbTrakm(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(Connection)
     */
    public SearchDbTrakm(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public Vector getListMhsDgnIpDibawahMinimal(Vector v_scope_id, String target_thsms, String tipe_ip, boolean mhs_ril, boolean nilai_ril, double nilai_min) {
    	Vector v = null;
    	ListIterator li = null;
    	if(!Checker.isStringNullOrEmpty(target_thsms) && v_scope_id!=null && v_scope_id.size()>0) {
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			String sql_cmd = "";
    			li = v_scope_id.listIterator();
                while(li.hasNext()) {
                	String info_scope = (String)li.next();
                    StringTokenizer st = new StringTokenizer(info_scope,"`");
                    st.nextToken(); // ignoerw kode kmp
                    while(st.hasMoreTokens()) {
                    	String objid = st.nextToken();
                    	sql_cmd = sql_cmd+"ID_OBJ="+objid+"";
                    	if(st.hasMoreTokens()) {
                    		sql_cmd = sql_cmd+" or ";
                        }
                    }
                    if(li.hasNext()) {
                    	sql_cmd = sql_cmd+" or ";
                    }
                }
    			if(tipe_ip.equalsIgnoreCase("ips")) {
    				if(mhs_ril) {
    					if(nilai_ril) {
    						stmt = con.prepareStatement("select KDPSTTRAKM,NPMHSTRAKM,NMMHSMSMHS,NLIPSTRAKM,NLIPKTRAKM from TRAKM inner join CIVITAS on NPMHSTRAKM=NPMHSMSMHS where THSMSTRAKM='"+target_thsms+"' and MALAIKAT=false and NLIPSTRAKM<? and ("+sql_cmd+")");
    					}
    					else {
    						stmt = con.prepareStatement("select KDPSTTRAKM,NPMHSTRAKM,NMMHSMSMHS,NLIPSROBOT,NLIPKROBOT from TRAKM inner join CIVITAS on NPMHSTRAKM=NPMHSMSMHS where THSMSTRAKM='"+target_thsms+"' and MALAIKAT=false and NLIPSROBOT<? and ("+sql_cmd+")");
    					}
    				}
    				else {
    					if(nilai_ril) {
    						stmt = con.prepareStatement("select KDPSTTRAKM,NPMHSTRAKM,NMMHSMSMHS,NLIPSTRAKM,NLIPKTRAKM from TRAKM inner join CIVITAS on NPMHSTRAKM=NPMHSMSMHS where THSMSTRAKM='"+target_thsms+"' and MALAIKAT=true and NLIPSTRAKM<? and ("+sql_cmd+")");
    					}
    					else {
    						stmt = con.prepareStatement("select KDPSTTRAKM,NPMHSTRAKM,NMMHSMSMHS,NLIPSROBOT,NLIPKROBOT from TRAKM inner join CIVITAS on NPMHSTRAKM=NPMHSMSMHS where THSMSTRAKM='"+target_thsms+"' and MALAIKAT=true and NLIPSROBOT<? and ("+sql_cmd+")");
    					}
    				}
    				stmt.setDouble(1, nilai_min);
    			}
    			else if(tipe_ip.equalsIgnoreCase("ipk")) {
    				if(mhs_ril) {
    					if(nilai_ril) {
    						stmt = con.prepareStatement("select KDPSTTRAKM,NPMHSTRAKM,NMMHSMSMHS,NLIPSTRAKM,NLIPKTRAKM from TRAKM inner join CIVITAS on NPMHSTRAKM=NPMHSMSMHS where THSMSTRAKM='"+target_thsms+"' and MALAIKAT=false and NLIPKTRAKM<? and ("+sql_cmd+")");
    					}
    					else {
    						stmt = con.prepareStatement("select KDPSTTRAKM,NPMHSTRAKM,NMMHSMSMHS,NLIPSROBOT,NLIPKROBOT from TRAKM inner join CIVITAS on NPMHSTRAKM=NPMHSMSMHS where THSMSTRAKM='"+target_thsms+"' and MALAIKAT=false and NLIPKROBOT<? and ("+sql_cmd+")");
    					}
    				}
    				else {
    					if(nilai_ril) {
    						stmt = con.prepareStatement("select KDPSTTRAKM,NPMHSTRAKM,NMMHSMSMHS,NLIPSTRAKM,NLIPKTRAKM from TRAKM inner join CIVITAS on NPMHSTRAKM=NPMHSMSMHS where THSMSTRAKM='"+target_thsms+"' and MALAIKAT=true and NLIPKTRAKM<? and ("+sql_cmd+")");
    					}
    					else {
    						stmt = con.prepareStatement("select KDPSTTRAKM,NPMHSTRAKM,NMMHSMSMHS,NLIPSROBOT,NLIPKROBOT from TRAKM inner join CIVITAS on NPMHSTRAKM=NPMHSMSMHS where THSMSTRAKM='"+target_thsms+"' and MALAIKAT=true and NLIPKROBOT<? and ("+sql_cmd+")");
    					}
    				}
    				stmt.setDouble(1, nilai_min);
    			}
    			else {
    				if(mhs_ril) {
    					if(nilai_ril) {
    						stmt = con.prepareStatement("select KDPSTTRAKM,NPMHSTRAKM,NMMHSMSMHS,NLIPSTRAKM,NLIPKTRAKM from TRAKM inner join CIVITAS on NPMHSTRAKM=NPMHSMSMHS where THSMSTRAKM='"+target_thsms+"' and MALAIKAT=false and (NLIPKTRAKM<? or NLIPSTRAKM<?) and ("+sql_cmd+")");
    					}
    					else {
    						stmt = con.prepareStatement("select KDPSTTRAKM,NPMHSTRAKM,NMMHSMSMHS,NLIPSROBOT,NLIPKROBOT from TRAKM inner join CIVITAS on NPMHSTRAKM=NPMHSMSMHS where THSMSTRAKM='"+target_thsms+"' and MALAIKAT=false and (NLIPKROBOT<? or NLIPSROBOT<?) and ("+sql_cmd+")");
    					}
    				}
    				else {
    					if(nilai_ril) {
    						stmt = con.prepareStatement("select KDPSTTRAKM,NPMHSTRAKM,NMMHSMSMHS,NLIPSTRAKM,NLIPKTRAKM from TRAKM inner join CIVITAS on NPMHSTRAKM=NPMHSMSMHS where THSMSTRAKM='"+target_thsms+"' and MALAIKAT=true and (NLIPKTRAKM<? or NLIPSTRAKM<?) and ("+sql_cmd+")");
    					}
    					else {
    						stmt = con.prepareStatement("select KDPSTTRAKM,NPMHSTRAKM,NMMHSMSMHS,NLIPSROBOT,NLIPKROBOT from TRAKM inner join CIVITAS on NPMHSTRAKM=NPMHSMSMHS where THSMSTRAKM='"+target_thsms+"' and MALAIKAT=true and (NLIPKROBOT<? or NLIPSROBOT<?) and ("+sql_cmd+")");
    					}
    				}
    				stmt.setDouble(1, nilai_min);
    				stmt.setDouble(2, nilai_min);
    			}
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v = new Vector();
    				li = v.listIterator();
    				String kdpst = rs.getString(1);
    				String npmhs = rs.getString(2);
    				String nmmhs = rs.getString(3);
    				double ips = rs.getDouble(4);
    				double ipk = rs.getDouble(5);
    				li.add(kdpst+"`"+npmhs+"`"+nmmhs+"`"+ips+"`"+ipk);
    				while(rs.next()) {
    					kdpst = rs.getString(1);
        				npmhs = rs.getString(2);
        				nmmhs = rs.getString(3);
        				ips = rs.getDouble(4);
        				ipk = rs.getDouble(5);
        				li.add(kdpst+"`"+npmhs+"`"+nmmhs+"`"+ips+"`"+ipk);
    				}
    			}
    		}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
    		catch (Exception ex) {
    			ex.printStackTrace();
    		} 
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}	
    	}
		
    	return v;
    }	
    
    public Vector getListMhsDgnIpDibawahMinimal(Vector v_scope_id, String target_thsms,String range_angkatan, double nilai_min) {
    	Vector v = null;
    	ListIterator li = null;
    	if(!Checker.isStringNullOrEmpty(target_thsms) && v_scope_id!=null && v_scope_id.size()>0) {
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			String sql_cmd = "";
    			li = v_scope_id.listIterator();
                while(li.hasNext()) {
                	String info_scope = (String)li.next();
                    StringTokenizer st = new StringTokenizer(info_scope,"`");
                    st.nextToken(); // ignoerw kode kmp
                    while(st.hasMoreTokens()) {
                    	String objid = st.nextToken();
                    	sql_cmd = sql_cmd+"ID_OBJ="+objid+"";
                    	if(st.hasMoreTokens()) {
                    		sql_cmd = sql_cmd+" or ";
                        }
                    }
                    if(li.hasNext()) {
                    	sql_cmd = sql_cmd+" or ";
                    }
                }
    			
                stmt = con.prepareStatement("select KDPSTTRAKM,NPMHSTRAKM,NMMHSMSMHS,NLIPSTRAKM,NLIPKTRAKM from TRAKM inner join CIVITAS on NPMHSTRAKM=NPMHSMSMHS where THSMSTRAKM='"+target_thsms+"' and SMAWLMSMHS<'"+range_angkatan+"' and (NLIPKROBOT<? and NLIPKTRAKM<? ) and ("+sql_cmd+")");
                //System.out.println("select KDPSTTRAKM,NPMHSTRAKM,NMMHSMSMHS,NLIPSTRAKM,NLIPKTRAKM from TRAKM inner join CIVITAS on NPMHSTRAKM=NPMHSMSMHS where THSMSTRAKM='"+target_thsms+"' and SMAWLMSMHS<'"+range_angkatan+"' and (NLIPKROBOT<? and NLIPKTRAKM<? ) and ("+sql_cmd+")");
                stmt.setDouble(1, nilai_min);
    			stmt.setDouble(2, nilai_min);
    			
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v = new Vector();
    				li = v.listIterator();
    				String kdpst = rs.getString(1);
    				String npmhs = rs.getString(2);
    				String nmmhs = rs.getString(3);
    				double ips = rs.getDouble(4);
    				double ipk = rs.getDouble(5);
    				li.add(kdpst+"`"+npmhs+"`"+nmmhs+"`"+ips+"`"+ipk);
    				while(rs.next()) {
    					kdpst = rs.getString(1);
        				npmhs = rs.getString(2);
        				nmmhs = rs.getString(3);
        				ips = rs.getDouble(4);
        				ipk = rs.getDouble(5);
        				li.add(kdpst+"`"+npmhs+"`"+nmmhs+"`"+ips+"`"+ipk);
    				}
    			}
    		}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
    		catch (Exception ex) {
    			ex.printStackTrace();
    		} 
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}	
    	}
		
    	return v;
    }	
    
    public Vector getNpmhsGivenTotalKrsCondition(String thsms, String tot_krs_condition, Vector v_scope_kdpst) {
    	Vector v = null;
    	ListIterator li = null;
    	
    	try {
    		if(v_scope_kdpst!=null) {
    			String sql_cmd = "";
    			
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			li = v_scope_kdpst.listIterator();
    			while(li.hasNext()) {
    				String info_scope = (String)li.next();
        			StringTokenizer st = new StringTokenizer(info_scope,"`");
        			st.nextToken();
        			while(st.hasMoreTokens()) {
        				String kdpst = st.nextToken();
        				sql_cmd = sql_cmd+"KDPSTTRAKM='"+kdpst+"'";
        				if(st.hasMoreTokens()) {
        					sql_cmd = sql_cmd+" or ";
        				}
        			}
        			if(li.hasNext()) {
        				sql_cmd = sql_cmd+" or ";
        			}
    			}
    			
    			
    			//sql_cmd = "SELECT THSMSTRAKM,KDPSTTRAKM,NPMHSTRAKM,NMMHSMSMHS,NIMHSMSMHS,SKSEMTRAKM FROM TRAKM inner join CIVITAS on NPMHSTRAKM=NPMHSMSMHS where THSMSTRAKM=? and ("+sql_cmd+") and SKSEMTRAKM"+tot_krs_condition;
    			sql_cmd = "SELECT KDPSTTRAKM,NPMHSTRAKM,NMMHSMSMHS,NIMHSMSMHS,SKSEMTRAKM FROM TRAKM inner join CIVITAS on NPMHSTRAKM=NPMHSMSMHS where THSMSTRAKM=? and ("+sql_cmd+") and SKSEMTRAKM"+tot_krs_condition;
    			//System.out.println("command = "+sql_cmd);
    			stmt = con.prepareStatement(sql_cmd);
    			stmt.setString(1, thsms);
    			rs = stmt.executeQuery();
        		ResultSetMetaData rsmd = rs.getMetaData();
        		int columnsNumber = rsmd.getColumnCount();
        		String col_label = null;
        		String col_type = null;
        		for(int i=1;i<=columnsNumber;i++) {
    				String col_name = rsmd.getColumnName(i);
    				if(col_label==null) {
    					col_label = new String(col_name);
    					col_type = new String("`");
    				}
    				else {
    					col_label = col_label+"`"+col_name;
    				}
    				//get col type
    				int type = rsmd.getColumnType(i);
    				if(type == java.sql.Types.DATE) {
    					col_type = col_type+"date`";
    				}
    				else if(type == java.sql.Types.DECIMAL || type == java.sql.Types.DOUBLE || type == java.sql.Types.FLOAT ) {
    					col_type = col_type+"double`";
    				}
    				else if(type == java.sql.Types.INTEGER || type == java.sql.Types.BIGINT || type == java.sql.Types.NUMERIC || type == java.sql.Types.SMALLINT) {
    					col_type = col_type+"long`";
    				}
    				else if(type == java.sql.Types.VARCHAR || type == java.sql.Types.LONGNVARCHAR || type == java.sql.Types.LONGVARCHAR || type == java.sql.Types.CHAR || type == java.sql.Types.NCHAR) {
    					col_type = col_type+"string`";
    				}
    				else if(type == java.sql.Types.TIME) {
    					col_type = col_type+"time`";
    				}
    				else if(type == java.sql.Types.BOOLEAN || type == java.sql.Types.TINYINT) {
    					col_type = col_type+"boolean`";
    				}
    				else if(type == java.sql.Types.TIMESTAMP) {
    					col_type = col_type+"timestamp`";
    				}
    			}
        		/*
        		 * updated - proses pindah ke servlet
        		 
        		if(v==null) {
    				v = new Vector();
    				li=v.listIterator();
    				li.add(col_type);
    				li.add(col_label);
    			}
        		*/
        		String brs = null;
        		while(rs.next()) {
        			for(int i=1;i<=columnsNumber;i++) {
        				String tmp = "";
        				/*
        				 * ADA 2 metode cek column type, karena diupdate yg baruan adalah cara yg diatas, belum tau mana yg lebih efektif
        				 */
        				col_type = rsmd.getColumnTypeName(i);
        				
        				if(col_type.equalsIgnoreCase("VARCHAR")||col_type.equalsIgnoreCase("TEXT")||col_type.startsWith("CHAR")) {
        					tmp = ""+rs.getString(i);
        				}
        				else if(col_type.equalsIgnoreCase("TINYINT")) {
        					tmp = ""+rs.getBoolean(i);
        				}
        				else if(col_type.contains("INT")||col_type.contains("LONG")) {
        					tmp = ""+rs.getLong(i);
        				}
        				else if(col_type.equalsIgnoreCase("DATE")) {
        					tmp = ""+rs.getDate(i);
        				}
        				else if(col_type.equalsIgnoreCase("DECIMAL")||col_type.equalsIgnoreCase("DOUBLE")||col_type.equalsIgnoreCase("FLOAT")) {
        					tmp = ""+rs.getDouble(i);
        				}
        				else if(col_type.equalsIgnoreCase("TIMESTAMP")||col_type.equalsIgnoreCase("DATETIME")) {
        					tmp = ""+rs.getTimestamp(i);
        				}
        				
        				if(brs==null) {
        					if(Checker.isStringNullOrEmpty(tmp)) {
        						brs = new String("null");
        					}
        					else {
        						brs = new String(tmp);
        					}
        				}
        				else {
        					if(Checker.isStringNullOrEmpty(tmp)) {
        						brs = brs +"`null";
        					}
        					else {
        						brs = brs+"`"+tmp;
        					}
        				}	
        			}
        			if(v==null) {
        				v = new Vector();
        				li=v.listIterator();
        			}
        			li.add(brs);
        			brs = null;
        		}
    		}
			
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return v;
	}	
}

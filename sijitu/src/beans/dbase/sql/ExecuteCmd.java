package beans.dbase.sql;

import beans.dbase.UpdateDb;
import beans.tools.Checker;
import beans.tools.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
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
 * Session Bean implementation class ExecuteCmd
 */
@Stateless
@LocalBean
public class ExecuteCmd extends UpdateDb {
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
    public ExecuteCmd() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public ExecuteCmd(String operatorNpm) {
    	super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        
        // TODO Auto-generated constructor stub
    }
    
    public Vector executeCmd(String sql_stmt) {
    	//System.out.println("sql_stmt="+sql_stmt);
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	//String ipAddr =  request.getRemoteAddr();
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//update civitas
        	stmt = con.prepareStatement(sql_stmt);
        	if(sql_stmt.startsWith("select")||sql_stmt.startsWith("SELECT")||sql_stmt.startsWith("Select")) {
        		rs = stmt.executeQuery();
        		ResultSetMetaData rsmd = rs.getMetaData();
        		int columnsNumber = rsmd.getColumnCount();
        		String col_label = null;
        		for(int i=1;i<=columnsNumber;i++) {
    				String col_name = rsmd.getColumnName(i);
    				if(col_label==null) {
    					col_label = new String(col_name);
    				}
    				else {
    					col_label = col_label+"`"+col_name;
    				}
    			}
        		if(v==null) {
    				v = new Vector();
    				li=v.listIterator();
    				li.add(col_label);
    			}
    			
        		//System.out.println("columnsNumber="+columnsNumber);
        		String brs = null;
        		while(rs.next()) {
        			for(int i=1;i<=columnsNumber;i++) {
        				String tmp = "";
        				//int col_tipe = rsmd.getColumnType(i);
        				//if(col_tipe == Types.DATE)
        				String col_type = rsmd.getColumnTypeName(i);
        				
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
        			li.add(brs);
        			brs = null;
        		}
        	}
        	else {
        		//non select
        		int i = stmt.executeUpdate();
        		if(v==null) {
    				v = new Vector();
    				li=v.listIterator();
    				li.add("COMMAND`EXE");
    				li.add("string`string");
    			}
        		
        		li.add(sql_stmt+"`"+i);
        	}
    	}
       	catch (NamingException e) {
       		e.printStackTrace();
       		if(v==null) {
				v = new Vector();
				li=v.listIterator();
				li.add("ERROR");
			}
       		li.add(e.toString());
       	}
       	catch (SQLException ex) {
       		ex.printStackTrace();
       		if(v==null) {
				v = new Vector();
				li=v.listIterator();
				li.add("ERROR");
			}
       		li.add(ex.toString());
       	} 
       	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    	    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    	    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	return v;
    }
    
    public Vector executeCmd_v1(String sql_stmt) {
    	//System.out.println("sql_stmt="+sql_stmt);
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	//String ipAddr =  request.getRemoteAddr();
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//update civitas
        	stmt = con.prepareStatement(sql_stmt);
        	if(sql_stmt.startsWith("select")||sql_stmt.startsWith("SELECT")||sql_stmt.startsWith("Select")) {
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
        		if(v==null) {
    				v = new Vector();
    				li=v.listIterator();
    				li.add(col_type);
    				li.add(col_label);
    			}
    			
        		//System.out.println("columnsNumber="+columnsNumber);
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
        					tmp = tmp.replace("`",",");
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
        				
        			li.add(brs);
        			brs = null;
        		}
        	}
        	else {
        		//non select
        		int i = stmt.executeUpdate();
        		if(v==null) {
    				v = new Vector();
    				li=v.listIterator();
    				li.add("string`string");
    				li.add("COMMAND`EXE");
    			}
        		
        		li.add(sql_stmt+"`"+i);
        	}
    	}
       	catch (NamingException e) {
       		e.printStackTrace();
       		if(v==null) {
				v = new Vector();
				li=v.listIterator();
				li.add("ERROR");
			}
       		li.add(e.toString());
       	}
       	catch (SQLException ex) {
       		ex.printStackTrace();
       		if(v==null) {
				v = new Vector();
				li=v.listIterator();
				li.add("ERROR");
			}
       		li.add(ex.toString());
       	} 
       	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    	    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    	    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	return v;
    }
    
    public Vector executeCmd_v2(String sql_stmt) {
    	//sudah include: REMOVE DUPLICATE
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	//String ipAddr =  request.getRemoteAddr();
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//update civitas
        	stmt = con.prepareStatement(sql_stmt);
        	if(sql_stmt.startsWith("select")||sql_stmt.startsWith("SELECT")||sql_stmt.startsWith("Select")) {
        		rs = stmt.executeQuery();
        		ResultSetMetaData rsmd = rs.getMetaData();
        		int columnsNumber = rsmd.getColumnCount();
        		String col_label = null;
        		String col_type = null;
        		for(int i=1;i<=columnsNumber;i++) {
        			//getColumnName
    				String col_name = rsmd.getColumnLabel(i);
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
        		if(v==null) {
    				v = new Vector();
    				li=v.listIterator();
    				li.add(col_type);
    				li.add(col_label);
    			}
    			
        		//System.out.println("columnsNumber="+columnsNumber);
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
        				
        			li.add(brs);
        			brs = null;
        		}
        	}
        	else {
        		//non select
        		int i = stmt.executeUpdate();
        		if(v==null) {
    				v = new Vector();
    				li=v.listIterator();
    				li.add("string`string");
    				li.add("COMMAND`EXE");
    			}
        		
        		li.add(sql_stmt+"`"+i);
        	}
    	}
       	catch (NamingException e) {
       		e.printStackTrace();
       		if(v==null) {
				v = new Vector();
				li=v.listIterator();
				li.add("ERROR");
			}
       		li.add(e.toString());
       	}
       	catch (SQLException ex) {
       		ex.printStackTrace();
       		if(v==null) {
				v = new Vector();
				li=v.listIterator();
				li.add("ERROR");
			}
       		li.add(ex.toString());
       	} 
       	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    	    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    	    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	try {
    		v = Tool.removeDuplicateFromVector(v);
    	}
    	catch(Exception e) {}
    	return v;
    }
    
    public Vector executeCmd_v2_khusus1(String sql_stmt) {
    	//sudah include: REMOVE DUPLICATE
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	//String ipAddr =  request.getRemoteAddr();
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//update civitas
        	stmt = con.prepareStatement(sql_stmt);
        	if(sql_stmt.startsWith("select")||sql_stmt.startsWith("SELECT")||sql_stmt.startsWith("Select")) {
        		rs = stmt.executeQuery();
        		ResultSetMetaData rsmd = rs.getMetaData();
        		int columnsNumber = rsmd.getColumnCount();
        		String col_label = null;
        		String col_type = null;
        		for(int i=1;i<=columnsNumber;i++) {
        			//getColumnName
    				String col_name = rsmd.getColumnLabel(i);
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
        		if(v==null) {
    				v = new Vector();
    				li=v.listIterator();
    				li.add(col_type);
    				li.add(col_label);
    			}
    			
        		//System.out.println("columnsNumber="+columnsNumber);
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
        			brs = brs.replace("`K`", "`KELUAR`");
        			li.add(brs);
        			brs = null;
        		}
        	}
        	else {
        		//non select
        		int i = stmt.executeUpdate();
        		if(v==null) {
    				v = new Vector();
    				li=v.listIterator();
    				li.add("string`string");
    				li.add("COMMAND`EXE");
    			}
        		
        		li.add(sql_stmt+"`"+i);
        	}
    	}
       	catch (NamingException e) {
       		e.printStackTrace();
       		if(v==null) {
				v = new Vector();
				li=v.listIterator();
				li.add("ERROR");
			}
       		li.add(e.toString());
       	}
       	catch (SQLException ex) {
       		ex.printStackTrace();
       		if(v==null) {
				v = new Vector();
				li=v.listIterator();
				li.add("ERROR");
			}
       		li.add(ex.toString());
       	} 
       	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    	    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    	    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	try {
    		v = Tool.removeDuplicateFromVector(v);
    	}
    	catch(Exception e) {}
    	return v;
    }
    
    public Vector executeCmd_v2(String sql_stmt, String tknColtype_tknCollabel_tknColvalue) {
    	Vector v = null;
    	ListIterator li = null;
    	System.out.println("sql_stmt="+sql_stmt);
    	if(!Checker.isStringNullOrEmpty(tknColtype_tknCollabel_tknColvalue)) {
    		StringTokenizer st1 = null;
    		StringTokenizer st2 = null;
			StringTokenizer st = new StringTokenizer(tknColtype_tknCollabel_tknColvalue,"~");//info per baris excel seperated by ~
			while(st.hasMoreTokens()) {
				String brs = st.nextToken();
				st1 = new StringTokenizer(brs,",");
				String tknColtype = st1.nextToken();//seperator per token = "`"
				String tknCollabel = st1.nextToken();//seperator per token = "`"
				String tknColvalue = st1.nextToken();//seperator per token = "`"
				if(v==null) {
					v = new Vector();
					li=v.listIterator();
				}	
				li.add(tknColtype);
				li.add(tknCollabel);
				li.add(tknColvalue);
			}
			
			
			
		}
    	
    	try {
        	//String ipAddr =  request.getRemoteAddr();
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//update civitas
        	stmt = con.prepareStatement(sql_stmt);
        	if(sql_stmt.startsWith("select")||sql_stmt.startsWith("SELECT")||sql_stmt.startsWith("Select")) {
        		rs = stmt.executeQuery();
        		ResultSetMetaData rsmd = rs.getMetaData();
        		int columnsNumber = rsmd.getColumnCount();
        		String col_label = null;
        		String col_type = null;
        		for(int i=1;i<=columnsNumber;i++) {
        			//getColumnName
    				String col_name = rsmd.getColumnLabel(i);
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
        		if(v==null) {
    				v = new Vector();
    				li=v.listIterator();
    				
    				li.add(col_type);
    				li.add(col_label);
    			}
    			
        		//System.out.println("columnsNumber="+columnsNumber);
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
        			
        			li.add(brs);
        			brs = null;
        		}
        	}
        	else {
        		//non select
        		int i = stmt.executeUpdate();
        		if(v==null) {
    				v = new Vector();
    				li=v.listIterator();
    				li.add("string`string");
    				li.add("COMMAND`EXE");
    			}
        		
        		li.add(sql_stmt+"`"+i);
        	}
    	}
       	catch (NamingException e) {
       		e.printStackTrace();
       		if(v==null) {
				v = new Vector();
				li=v.listIterator();
				li.add("ERROR");
			}
       		li.add(e.toString());
       	}
       	catch (SQLException ex) {
       		ex.printStackTrace();
       		if(v==null) {
				v = new Vector();
				li=v.listIterator();
				li.add("ERROR");
			}
       		li.add(ex.toString());
       	} 
       	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    	    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    	    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	try {
    		v = Tool.removeDuplicateFromVector(v);
    	}
    	catch(Exception e) {}
    	return v;
    }

}

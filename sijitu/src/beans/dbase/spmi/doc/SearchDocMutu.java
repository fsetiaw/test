package beans.dbase.spmi.doc;

import beans.dbase.SearchDb;
import beans.dbase.spmi.ToolSpmi;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
 * Session Bean implementation class SearchDocMutu
 */
@Stateless
@LocalBean
public class SearchDocMutu extends SearchDb {
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
    public SearchDocMutu() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDocMutu(String operatorNpm) {
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
    public SearchDocMutu(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public Vector getStatusKeberadaanDokumen(Vector v_scope_kdpst, Vector v_list_doc_mutu) {
    	ToolSpmi ts = new ToolSpmi();
    	String root_dokumen_mutu_folder = ts.getRootDokMutuFolder();
    	File files = new File(root_dokumen_mutu_folder);
    	new File(root_dokumen_mutu_folder).mkdirs();
    	Vector v_kdpst_nmProdi_totDoc_totDocUploaded_vecStatusDocPerProdi = new Vector();
    	ListIterator li = null, li1 = null, liv = null, lif = null;
    	li = v_scope_kdpst.listIterator();
    	lif =  v_kdpst_nmProdi_totDoc_totDocUploaded_vecStatusDocPerProdi.listIterator();
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select VERSI_ID from STANDARD_MANUAL_PERENCANAAN_UMUM where ID_STD=? order by VERSI_ID desc limit 1");
        	while(li.hasNext()) {
        		String brs = (String)li.next();
        		StringTokenizer st = new StringTokenizer(brs,"`");
        		String kdfak = st.nextToken();
        		String kdpst = st.nextToken();
        		String kdjen = st.nextToken();
        		String nmpst = st.nextToken();
        		String nmjen = "";
        		if(kdjen.equalsIgnoreCase("A")) {
        			nmjen="S-3";
        		}
        		else if(kdjen.equalsIgnoreCase("B")) {
        			nmjen="S-2";
        		}
        		else if(kdjen.equalsIgnoreCase("C")) {
        			nmjen="S-1";
        		}
        		else if(kdjen.equalsIgnoreCase("D")) {
        			nmjen="D-4";
        		}
        		else if(kdjen.equalsIgnoreCase("E")) {
        			nmjen="D-3";
        		}
        		else if(kdjen.equalsIgnoreCase("F")) {
        			nmjen="D-2";
        		}
        		else if(kdjen.equalsIgnoreCase("G")) {
        			nmjen="D-1";
        		}
        		
        		int tot_doc_std = v_list_doc_mutu.size();
        		int tot_uploaded = 0;
        		String tmp = new String(brs+"~"+tot_doc_std);
        		while(tmp.contains("`")) {
        			tmp = tmp.replace("`", "~");
        		}
        		
        		Vector v_statusDocPerProdi = new Vector();
    			liv =  v_statusDocPerProdi.listIterator();
        		li1 = v_list_doc_mutu.listIterator();
        		while(li1.hasNext()) {
        			String brs2 = (String)li1.next();
        			//System.out.println(brs2);
        			st =new StringTokenizer(brs2,"~");
        			String nm_doc = st.nextToken();
        			while(nm_doc.contains("/")) {
        				nm_doc = nm_doc.replace("/", " ATAU ");
        			}
        			while(nm_doc.contains("  ")) {
        				nm_doc = nm_doc.replace("  ", " ");
        			}
        			String target_dir = root_dokumen_mutu_folder+nmpst.trim()+" ["+nmjen.toUpperCase().trim()+"]/"+nm_doc.toUpperCase().trim();
            		//System.out.println("dir_subdir 000="+target_dir);
        			new File(root_dokumen_mutu_folder+nmpst.trim()+" ["+nmjen.toUpperCase().trim()+"]").mkdirs();
        			new File(target_dir).mkdirs();
        			files = new File(target_dir);
            		File[] list = files.listFiles();
            		boolean uploaded = false;
            		if(list!=null) {
            			for(int i=0;i<list.length;i++) {
                			String nm_file = list[i].getName();
                			if(list[i].isDirectory()) {
                				//System.out.println(nm_file+" is directory");
                			}
                			else {
                				//System.out.println(nm_file+" is file");
                				tot_uploaded++;
                				uploaded = true;
                			}
                		}
                		liv.add(nm_doc+"~"+uploaded);
            		}
            		
            		//System.out.println(nm_doc+"~"+uploaded);
            		//FE`61201`C`MANAJEMEN
        		}
        		tmp = new String(brs+"~"+tot_doc_std+"~"+tot_uploaded);
        		while(tmp.contains("`")) {
        			tmp = tmp.replace("`", "~");
        		}
        		lif.add(tmp);
        		lif.add(v_statusDocPerProdi);
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
    	return v_kdpst_nmProdi_totDoc_totDocUploaded_vecStatusDocPerProdi;
    }	

}

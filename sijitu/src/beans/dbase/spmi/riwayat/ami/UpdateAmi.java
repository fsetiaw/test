package beans.dbase.spmi.riwayat.ami;

import beans.dbase.UpdateDb;
import beans.dbase.spmi.ToolSpmi;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Tool;

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
 * Session Bean implementation class UpdateAmi
 */
@Stateless
@LocalBean
public class UpdateAmi extends UpdateDb {
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
    public UpdateAmi() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateAmi(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }

    
    
    
    public int addRencanaAmi(String id_ami,String target_kdpst,String kode_activity,String tgl_plan,String ketua_tim,String[]anggota_tim,String[]cek) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//get cakupan nama standar
        	stmt = con.prepareStatement("select KET_TIPE_STD from STANDARD_MASTER_TABLE where ID_MASTER_STD=?");
        	String tkn_ket_std = "";
        	for(int j=0;j<cek.length;j++) {
        		if(!Checker.isStringNullOrEmpty(cek[j])) {
        			try {
        				int id = Integer.parseInt(cek[j]);
        				stmt.setInt(1, id);
        				rs = stmt.executeQuery();
        				if(rs.next()) {
        					String ket_std = rs.getString(1);
        					if(!Checker.isStringNullOrEmpty(ket_std) && !ket_std.contains("BELUM")) {
        						if(Checker.isStringNullOrEmpty(tkn_ket_std)) {
        							tkn_ket_std = new String(ket_std);
        						}
        						else {
        							tkn_ket_std = tkn_ket_std+","+ket_std;
        						}
        					}
        				}
        			}
        			catch (Exception e) {
        				
        			}
        		}
        	}
        	String sql="";
        	if(Checker.isStringNullOrEmpty(id_ami)) {
        		sql = "INSERT INTO AUDIT_MUTU_INTERNAL(KDPST,NAMA_KEGIATAN_AMI,TGL_RENCANA_AMI,KETUA_TIM,TKN_ANGGOTA_TIM,TKN_CAKUPAN_ID_MASTER_STD,TKN_CAKUPAN_MASTER_STD)values(?,?,?,?,?,?,?)";
            	
        	}
        	else {
        		//update
        		sql = "update AUDIT_MUTU_INTERNAL set KDPST=?,NAMA_KEGIATAN_AMI=?,TGL_RENCANA_AMI=?,KETUA_TIM=?,TKN_ANGGOTA_TIM=?,TKN_CAKUPAN_ID_MASTER_STD=?,TKN_CAKUPAN_MASTER_STD=? where ID="+id_ami;
            	
        	}
        	stmt = con.prepareStatement(sql);
        	int i=1;
        	//KDPST,
        	stmt.setString(i++, target_kdpst);
        	//NAMA_KEGIATAN_AMI,
        	stmt.setString(i++, kode_activity);
        	//TGL_RENCANA_AMI,
        	stmt.setDate(i++, java.sql.Date.valueOf(tgl_plan));
        	//KETUA_TIM,
        	stmt.setString(i++, ketua_tim.toUpperCase());
        	String tmp ="";
        	for(int j=0;j<anggota_tim.length;j++) {
        		if(!Checker.isStringNullOrEmpty(anggota_tim[j])) {
        			if(Checker.isStringNullOrEmpty(tmp)) {
        				tmp = tmp + anggota_tim[j];
        			}
        			else {
        				tmp = tmp +","+ anggota_tim[j];	
        			}
        			
        		}
        	}
        	//TKN_ANGGOTA_TIM,
        	stmt.setString(i++, tmp.toUpperCase());
        	tmp ="";
        	for(int j=0;j<cek.length;j++) {
        		if(!Checker.isStringNullOrEmpty(cek[j])) {
        			if(Checker.isStringNullOrEmpty(tmp)) {
        				tmp = tmp + cek[j];
        			}
        			else {
        				tmp = tmp +","+ cek[j];	
        			}	
        		}
        	}
        	//TKN_CAKUPAN_ID_MASTER_STD
        	stmt.setString(i++, tmp);
        	stmt.setString(i++, tkn_ket_std);
        	updated = stmt.executeUpdate();
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
    	return updated;
    }
    
    public int updateSusunanPertanyaan(String kdpst, int id_master_std, String[]id_que_and_isi_dan_norut_std) {
    	int updated=0;
    	StringTokenizer st = null;
    	if(id_que_and_isi_dan_norut_std!=null && id_que_and_isi_dan_norut_std.length>0) {
    		try {
    			//System.out.println("id_que_and_isi_dan_norut_std="+id_que_and_isi_dan_norut_std.length);
        		Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	stmt = con.prepareStatement("delete from AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN where ID_MASTER_STD=? and KDPST=?");
            	stmt.setInt(1, id_master_std);
            	stmt.setString(2, kdpst);
            	stmt.executeUpdate();
            	stmt = con.prepareStatement("insert into AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN(KDPST,ID_MASTER_STD,ID_STD_ISI,ID_QUESTION,NORUT)values(?,?,?,?,?)");
            	for(int i=0;i<id_que_and_isi_dan_norut_std.length;i++) {
            		stmt.setString(1,kdpst);
            		stmt.setInt(2, id_master_std);
            		st = new StringTokenizer(id_que_and_isi_dan_norut_std[i],"`");
            		String id_question = st.nextToken();
            		String id_std_isi = st.nextToken();
            		String norut = st.nextToken();
            		stmt.setInt(3,Integer.parseInt(id_std_isi));
            		stmt.setInt(4,Integer.parseInt(id_question));
            		stmt.setInt(5,Integer.parseInt(norut));
            		updated = updated+stmt.executeUpdate();
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
    
    
    public int resetSusunanPertanyaan(String kdpst, int id_master_std) {
    	int updated=0;
    	StringTokenizer st = null;
    	try {
			//System.out.println("kdpst11="+kdpst);
			//System.out.println("id_master_std11="+id_master_std);
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//1. hapus susunan yang dulu
        	stmt = con.prepareStatement("delete from AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN where ID_MASTER_STD=? and KDPST=?");
        	stmt.setInt(1, id_master_std);
        	stmt.setString(2, kdpst);
        	updated = stmt.executeUpdate();
        	//2. get id question urutkan berdasarkan id_question
        	String sqlGetTotQandA = "select D.ID,D.ID_STD_ISI " + 
        			"	from STANDARD_MASTER_TABLE A " + 
        			"    inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD " + 
        			"    inner join STANDARD_ISI_TABLE C on B.ID_STD=C.ID_STD " + 
        			"    inner join STANDARD_ISI_QUESTION D on C.ID=D.ID_STD_ISI " + 
        			"    where A.ID_MASTER_STD=? order by D.ID"; 
        	stmt = con.prepareStatement(sqlGetTotQandA);
        	stmt.setInt(1, id_master_std);
        	rs = stmt.executeQuery();
        	Vector v =null;
        	ListIterator li = null;
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		do {
        			li.add(rs.getString(1));
        			li.add(rs.getString(2));
        		}
        		while(rs.next());
        		if(v!=null) {
        			stmt = con.prepareStatement("insert into AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN(KDPST,ID_MASTER_STD,ID_STD_ISI,ID_QUESTION,NORUT)values(?,?,?,?,?)");
        			li = v.listIterator();
        			int norut=1;
        			while(li.hasNext()) {
        				String idq = (String)li.next();
        				String id_std_isi = (String)li.next();
        				stmt.setString(1, kdpst);
        				stmt.setInt(2, id_master_std);
        				stmt.setInt(3, Integer.parseInt(id_std_isi));
        				stmt.setInt(4, Integer.parseInt(idq));
        				stmt.setInt(5, norut++);
        				updated= updated+stmt.executeUpdate();
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
    	return updated;
    }
    
    public int setDefaultSusunanPertanyaan(int id_ami) {
    	int updated = 0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TKN_CAKUPAN_ID_MASTER_STD,KDPST from AUDIT_MUTU_INTERNAL where ID=?");
			stmt.setInt(1, id_ami);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String cakupan_master_id = rs.getString(1);
    			String kdpst = rs.getString(2);
    			StringTokenizer st = new StringTokenizer(cakupan_master_id,",");
    			while(st.hasMoreTokens()) {
    				String master_id = st.nextToken();
    				//System.out.println("master_id="+master_id);
    				updated = updated+setDefaultSusunanPertanyaan(kdpst, Integer.parseInt(master_id), con);
    				//System.out.println("subtot="+tot);
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
    	return updated;
    }
    
    public int setDefaultSusunanPertanyaan(String kdpst, int id_master_std, Connection connect) {
    	//cek apakah pernah ada urutan untuk prodinya
    	//bila blum cek apa ada dari prodi lain
    	//selanjutnya tambahkan urutan pertanyaan bagi pertanyaan yg baru (blum ada recordnya)
    	int updated=0;
    	StringTokenizer st = null;
    	try {
			//System.out.println("kdpst11="+kdpst);
			//System.out.println("id_master_std11="+id_master_std);
    		if(connect==null) {
    			Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();	
    		}
    		else {
    			con = connect;
    		}
    		
        	//1. cek susunan sebelumnya 
        	stmt = con.prepareStatement("select * from AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN where ID_MASTER_STD=? and KDPST=? order by NORUT");
        	stmt.setInt(1, id_master_std);
        	stmt.setString(2, kdpst);
        	rs = stmt.executeQuery();
        	Vector v =null;
        	ListIterator li = null;
        	if(rs.next()) {
        		//jika pernah ada record sebelumnya 
        		v = new Vector();
        		li = v.listIterator();
        		do {
        			//li.add(rs.getString("KDPST"));
        			//li.add(rs.getString("ID_MASTER_STD"));
        			li.add(rs.getString("ID_STD_ISI"));
        			li.add(rs.getString("ID_QUESTION"));
        			//li.add(rs.getString("ID_NORUT"));
        		}
        		while(rs.next());
        	}
        	
        	if(v==null) {
        		//blum ada record dari target prodi
        		//a.check apa ada prodi laen
        		stmt = con.prepareStatement("select KDPST from AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN where ID_MASTER_STD=? limit 1");
            	stmt.setInt(1, id_master_std);
            	rs=stmt.executeQuery();
            	if(rs.next()) {
            		String kdpst_acuan = rs.getString(1);
            		//1. cek susunan dari prodi ini
                	stmt = con.prepareStatement("select * from AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN where ID_MASTER_STD=? and KDPST=? order by NORUT");
                	stmt.setInt(1, id_master_std);
                	stmt.setString(2, kdpst_acuan);
                	rs = stmt.executeQuery();
                	v =null;
                	li = null;
                	//int counter=0;
                	if(rs.next()) {
                		//jika pernah ada record sebelumnya 
                		v = new Vector();
                		li = v.listIterator();
                		do {
	                		//counter++;
                			//li.add(kdpst);
                			//li.add(rs.getString("ID_MASTER_STD"));
                			li.add(rs.getString("ID_STD_ISI"));
                			li.add(rs.getString("ID_QUESTION"));
                			//li.add(""+counter);
                		}
                		while(rs.next());
                		//2. get all id question 
                    	String sqlGetTotQandA = "select D.ID,D.ID_STD_ISI " + 
                    			"	from STANDARD_MASTER_TABLE A " + 
                    			"    inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD " + 
                    			"    inner join STANDARD_ISI_TABLE C on B.ID_STD=C.ID_STD " + 
                    			"    inner join STANDARD_ISI_QUESTION D on C.ID=D.ID_STD_ISI " + 
                    			"    where A.ID_MASTER_STD=? order by D.ID"; 
                    	stmt = con.prepareStatement(sqlGetTotQandA);
                    	stmt.setInt(1, id_master_std);
                    	rs = stmt.executeQuery();
                    	
                    	//pasti masuk ke rs next dibawah ini
                    	if(rs.next()) {
                    		do {
                    			
                    			String idq = rs.getString(1);
                    			String id_std_isi = rs.getString(2);
                    			//cek apakah sudah ada di vector v
                    			li = v.listIterator();
                    			String norut_tmp = "0";
                    			boolean match = false;
                    			while(li.hasNext() && !match) {
                    				//String kdpst_tmp = (String)li.next();
                    				//String id_master_tmp = (String)li.next();
                    				String id_isi_tmp = (String)li.next();
                    				String idq_tmp = (String)li.next();
                    				//norut_tmp = (String)li.next();
                    				if(idq.trim().equalsIgnoreCase(idq_tmp.trim())) {
                    					match = true;
                    				}
                    			}
                    			if(!match) {
                    				//li.add(kdpst);
                    				//li.add(""+id_master_std);
                    				li.add(""+id_std_isi);
                    				li.add(""+idq);
                    				//li.add(""+(Integer.parseInt(norut_tmp)+1));
                    			}
                    		}
                    		while(rs.next());
                    		
                    		//insert urutan sesuai target prodi
                    		stmt = con.prepareStatement("insert into AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN(KDPST,ID_MASTER_STD,ID_STD_ISI,ID_QUESTION,NORUT)values(?,?,?,?,?)");
                			li = v.listIterator();
                			int norut=1;
                			while(li.hasNext()) {
                				String id_std_isi = (String)li.next();
                				String idq = (String)li.next();
                				
                				stmt.setString(1, kdpst);
                				stmt.setInt(2, id_master_std);
                				stmt.setInt(3, Integer.parseInt(id_std_isi));
                				stmt.setInt(4, Integer.parseInt(idq));
                				stmt.setInt(5, norut++);
                				updated= updated+stmt.executeUpdate();
                			}
                    	}
                	}	
            	}
            	else {
            		//blum ada record dari prodi apapun
            		//2. get id question urutkan berdasarkan id_question
                	String sqlGetTotQandA = "select D.ID,D.ID_STD_ISI " + 
                			"	from STANDARD_MASTER_TABLE A " + 
                			"    inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD " + 
                			"    inner join STANDARD_ISI_TABLE C on B.ID_STD=C.ID_STD " + 
                			"    inner join STANDARD_ISI_QUESTION D on C.ID=D.ID_STD_ISI " + 
                			"    where A.ID_MASTER_STD=? order by D.ID"; 
                	stmt = con.prepareStatement(sqlGetTotQandA);
                	stmt.setInt(1, id_master_std);
                	rs = stmt.executeQuery();
                	
                	if(rs.next()) {
                		v = new Vector();
                		li = v.listIterator();
                		do {
                			li.add(rs.getString(1));
                			li.add(rs.getString(2));
                		}
                		while(rs.next());
                		if(v!=null) {
                			stmt = con.prepareStatement("insert into AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN(KDPST,ID_MASTER_STD,ID_STD_ISI,ID_QUESTION,NORUT)values(?,?,?,?,?)");
                			li = v.listIterator();
                			int norut=1;
                			while(li.hasNext()) {
                				String idq = (String)li.next();
                				String id_std_isi = (String)li.next();
                				stmt.setString(1, kdpst);
                				stmt.setInt(2, id_master_std);
                				stmt.setInt(3, Integer.parseInt(id_std_isi));
                				stmt.setInt(4, Integer.parseInt(idq));
                				stmt.setInt(5, norut++);
                				updated= updated+stmt.executeUpdate();
                			}
                		}
                	}
            	}
        	}
            else {
            	//sudah ada record sebelumnya 
            	//2. get all id question 
            	String sqlGetTotQandA = "select D.ID,D.ID_STD_ISI " + 
            			"	from STANDARD_MASTER_TABLE A " + 
            			"    inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD " + 
            			"    inner join STANDARD_ISI_TABLE C on B.ID_STD=C.ID_STD " + 
            			"    inner join STANDARD_ISI_QUESTION D on C.ID=D.ID_STD_ISI " + 
            			"    where A.ID_MASTER_STD=? order by D.ID"; 
            	stmt = con.prepareStatement(sqlGetTotQandA);
            	stmt.setInt(1, id_master_std);
            	rs = stmt.executeQuery();
            	
            	//pasti masuk ke rs next dibawah ini
            	if(rs.next()) {
            		do {
            			
            			String idq = rs.getString(1);
            			String id_std_isi = rs.getString(2);
            			//cek apakah sudah ada di vector v
            			li = v.listIterator();
            			String norut_tmp = "0";
            			boolean match = false;
            			while(li.hasNext() && !match) {
            				//String kdpst_tmp = (String)li.next();
            				//String id_master_tmp = (String)li.next();
            				String id_isi_tmp = (String)li.next();
            				String idq_tmp = (String)li.next();
            				//norut_tmp = (String)li.next();
            				if(idq.trim().equalsIgnoreCase(idq_tmp.trim())) {
            					match = true;
            				}
            			}
            			if(!match) {
            				//li.add(kdpst);
            				//li.add(""+id_master_std);
            				li.add(""+id_std_isi);
            				li.add(""+idq);
            				//li.add(""+(Integer.parseInt(norut_tmp)+1));
            			}
            		}
            		while(rs.next());
            		
            		//update dulu
            		Vector v_ins = null;
            		ListIterator lins = null;
            		li = v.listIterator();
            		stmt = con.prepareStatement("update AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN set NORUT=? where KDPST=? and ID_QUESTION=?");
        			int norut=1;
        			while(li.hasNext()) {
        				String id_std_isi = (String)li.next();
        				String idq = (String)li.next();
        				stmt.setInt(1, norut++);
        				stmt.setString(2, kdpst);
        				stmt.setInt(3, Integer.parseInt(idq));
        				int i = 0;
        				i = stmt.executeUpdate();
        				if(i<1) {
        					norut--;
        					if(v_ins==null) {
        						v_ins = new Vector();
        						lins = v_ins.listIterator();
        					}
        					lins.add(id_std_isi);
        					lins.add(idq);
        				}
        			}
        			if(v_ins!=null) {
        				//insert urutan sesuai target prodi
                		lins = v_ins.listIterator();
                		stmt = con.prepareStatement("insert into AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN(KDPST,ID_MASTER_STD,ID_STD_ISI,ID_QUESTION,NORUT)values(?,?,?,?,?)");
            			while(lins.hasNext()) {
            				String id_std_isi = (String)lins.next();
            				String idq = (String)lins.next();
            				
            				stmt.setString(1, kdpst);
            				stmt.setInt(2, id_master_std);
            				stmt.setInt(3, Integer.parseInt(id_std_isi));
            				stmt.setInt(4, Integer.parseInt(idq));
            				stmt.setInt(5, norut++);
            				updated= updated+stmt.executeUpdate();
            			}	
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
        	if(connect==null) {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        	    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        	    if (con!=null) try { con.close();} catch (Exception ignore){}	
        	}
    		
    	}
    	return updated;
    }
    
    public int stopKegiatanAmi(int id_ami) {
    	java.sql.Date end_dt = AskSystem.getCurrentTime();
    	String thsms_now = Checker.getThsmsNow();
    	String tahun_now = Checker.getTahunNow();
    	int updated=0;
    	StringTokenizer st = null;
    	boolean aktif = false;
    	try {
    		//System.out.println("id_que_and_isi_dan_norut_std="+id_que_and_isi_dan_norut_std.length);
        	Context initContext  = new InitialContext();
           	Context envContext  = (Context)initContext.lookup("java:/comp/env");
           	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
           	con = ds.getConnection();
           	stmt = con.prepareStatement("update AUDIT_MUTU_INTERNAL set TGL_RIL_AMI_DONE=? where ID=?");
           	stmt.setDate(1, end_dt);
           	stmt.setInt(2, id_ami);
           	updated = updated+stmt.executeUpdate();	
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
    	return updated;
	}
    
    public void updateNlakhAmi(int id_ami) {
    	java.sql.Date end_dt = AskSystem.getCurrentTime();
    	String thsms_now = Checker.getThsmsNow();
    	String tahun_now = Checker.getTahunNow();
    	int updated=0;
    	StringTokenizer st = null;
    	boolean aktif = false;
    	try {
    		//System.out.println("id_que_and_isi_dan_norut_std="+id_que_and_isi_dan_norut_std.length);
        	Context initContext  = new InitialContext();
           	Context envContext  = (Context)initContext.lookup("java:/comp/env");
           	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
           	con = ds.getConnection();
           	//delete prev record
           	String sql = "delete from AUDIT_MUTU_INTERNAL_NILAI_AKHIR where ID_AMI=?";
           	stmt = con.prepareStatement(sql);
           	stmt.setInt(1, id_ami);
           	stmt.executeUpdate();
           	//get nilai
           	sql = "select A.ID_MASTER_STD,A.KET_TIPE_STD,B.ID_STD,B.KET_TIPE_STD,B.ID_TIPE_STD,C.TOTAL_NILAI from STANDARD_MASTER_TABLE as A " + 
           			"inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD " + 
           			"left join (SELECT ID_MASTER_STD,ID_STD,sum(bobot) as TOTAL_NILAI FROM AUDIT_MUTU_INTERNAL_HASIL where ID_AMI=?  group by ID_MASTER_STD,ID_STD  order by ID_MASTER_STD,ID_STD) C " + 
           			"on (B.ID_MASTER_STD=C.ID_MASTER_STD and B.ID_STD=C.ID_STD) " + 
           			"WHERE A.ID_MASTER_STD>0 order by A.ID_MASTER_STD,B.ID_TIPE_STD ";
           	
           	stmt = con.prepareStatement(sql);
           	stmt.setInt(1, id_ami);
           	rs = stmt.executeQuery();
           	if(rs.next()) {
           		Vector v =new Vector();
           		ListIterator li = v.listIterator();
           		do {
           			String id_master = rs.getString(1);
           			String nm_master = rs.getString(2);
           			String id_std = rs.getString(3);
           			String nm_std = rs.getString(4);
           			String id_tipe_std = rs.getString(5);
           			String tot_hasil_penilaian = rs.getString(6);
           			if(Checker.isStringNullOrEmpty(tot_hasil_penilaian)) {
           				tot_hasil_penilaian="0";
           			}
           			String tmp = id_master+"~"+nm_master+"~"+id_std+"~"+nm_std+"~"+id_tipe_std+"~"+tot_hasil_penilaian;
           			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
           			li.add(tmp);
           		}
           		while(rs.next());
           		
           		//insert
           		sql = "insert into AUDIT_MUTU_INTERNAL_NILAI_AKHIR (ID_AMI,ID_MASTER_STD,NAMA_MASTER_STD,ID_STD,NAMA_STD,ID_TIPE_STD,TOT_HASIL_PENILAIAN)" + 
           				"values (?,?,?,?,?,?,?)";
           		stmt = con.prepareStatement(sql);
           		li = v.listIterator();
           		while(li.hasNext()) {
           			String brs = (String)li.next();
           			st = new StringTokenizer(brs,"~");
           			String id_master = st.nextToken();
           			String nm_master = st.nextToken();
           			String id_std = st.nextToken();
           			String nm_std = st.nextToken();
           			String id_tipe_std = st.nextToken();
           			String tot_hasil_penilaian = st.nextToken();
           			stmt.setInt(1, id_ami);
           			stmt.setInt(2, Integer.parseInt(id_master));
           			stmt.setString(3, nm_master);
           			stmt.setInt(4, Integer.parseInt(id_std));
           			stmt.setString(5, nm_std);
           			stmt.setInt(6, Integer.parseInt(id_tipe_std));
           			stmt.setDouble(7, Double.parseDouble(tot_hasil_penilaian));
           			int i= stmt.executeUpdate();
           			
           		}
           		//get total pertanyaan & nilai avail
           		sql = "SELECT G.ID_STD,H.TOT_QUESTION,MAX_NILAI FROM AUDIT_MUTU_INTERNAL_NILAI_AKHIR As G" + 
           				"	inner join " + 
           				"    (" + 
           				"		select I.ID_STD,SUM(L.MAX_BOBOT) as MAX_NILAI from STANDARD_ISI_TABLE as I" + 
           				"		inner join " + 
           				"		(" + 
           				"			SELECT J.ID_STD_ISI,J.ID as ID_QUESTION,MAX(K.BOBOT) as MAX_BOBOT FROM STANDARD_ISI_QUESTION as J" + 
           				"			inner join " + 
           				"			STANDARD_ISI_ANSWER as K on J.ID=K.ID_QUESTION" + 
           				"			group by J.ID" + 
           				"		) as L on I.ID=L.ID_STD_ISI" + 
           				"		group by I.ID_STD" + 
           				"    ) as M on G.ID_STD=M.ID_STD" + 
           				"    inner join " + 
           				"    (" + 
           				"		select A.ID_MASTER_STD,B.ID_STD,A.ID_TIPE_STD,COUNT(C.ID) as TOT_QUESTION from STANDARD_TABLE A " + 
           				"		inner join STANDARD_ISI_TABLE B on A.ID_STD=B.ID_STD" + 
           				"		inner join STANDARD_ISI_QUESTION C on B.ID=C.ID_STD_ISI" + 
           				"		group by A.ID_MASTER_STD,B.ID_STD,A.ID_TIPE_STD" + 
           				"    ) as H on G.ID_STD=H.ID_STD" + 
           				"    where G.ID_AMI=?";
           		stmt = con.prepareStatement(sql);
           		stmt.setInt(1, id_ami);
           		rs = stmt.executeQuery();
           		if(rs.next()) {
           			v = new Vector();
           			li = v.listIterator();
           			do {
           				String id_std = rs.getString(1);
           				String tot_que = rs.getString(2);
           				String max_val = rs.getString(3);
           				li.add(id_std+"~"+tot_que+"~"+max_val);
           			}
           			while(rs.next());
           			
           			//update
           			sql = "update AUDIT_MUTU_INTERNAL_NILAI_AKHIR set TOT_QUESTION=?,MAX_NILAI_PENILAIAN=? where ID_AMI=? and ID_STD=?";
           			stmt = con.prepareStatement(sql);
           			li = v.listIterator();
           			while(li.hasNext()) {
           				String brs = (String)li.next();
           				st = new StringTokenizer(brs,"~");
           				String id_std = st.nextToken();
           				String tot_que = st.nextToken();
           				String max_val = st.nextToken();
           				stmt.setInt(1, Integer.parseInt(tot_que));
           				stmt.setDouble(2, Double.parseDouble(max_val));
           				stmt.setInt(3, id_ami);
           				stmt.setInt(4, Integer.parseInt(id_std));
           				stmt.executeUpdate();
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
    	//return updated;
	}
	
	
    
    public int startKegiatanAmi(int id_ami, String target_kdpst) {
    	//System.out.println("id_ami="+id_ami);
    	//System.out.println("target_kdpst_ami="+target_kdpst);
    	java.sql.Date sta_dt = AskSystem.getCurrentTime();
    	String thsms_now = Checker.getThsmsNow();
    	String tahun_now = Checker.getTahunNow();
    	int updated=0;
    	StringTokenizer st = null;
    	boolean aktif = false;
    	try {
    		//System.out.println("id_que_and_isi_dan_norut_std="+id_que_and_isi_dan_norut_std.length);
        	Context initContext  = new InitialContext();
           	Context envContext  = (Context)initContext.lookup("java:/comp/env");
           	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
           	con = ds.getConnection();
           	
           	//1. cek cakupan 
           	Vector v = null;
           	ListIterator li = null;
           	stmt = con.prepareStatement("select * from AUDIT_MUTU_INTERNAL where ID=?");
           	stmt.setInt(1, id_ami);
           	rs = stmt.executeQuery();
           	if(rs.next()) {
           		//System.out.println("step 1");
           		String tkn_id_master = rs.getString("TKN_CAKUPAN_ID_MASTER_STD");
           		if(!Checker.isStringNullOrEmpty(tkn_id_master)) {
           			v = new Vector();
           			li = v.listIterator();
           			st = new StringTokenizer(tkn_id_master,",");
           			while(st.hasMoreTokens()) {
           				//iter informasi per id_master
           				String id_master = st.nextToken();
           				stmt = con.prepareStatement("select * from STANDARD_MASTER_TABLE A inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD where A.ID_MASTER_STD="+id_master);
           				rs = stmt.executeQuery();
           				rs.next();
           				String nama_master_std = rs.getString("A.KET_TIPE_STD");
           				String kode_master_std = rs.getString("A.KODE");
           				String nama_std = rs.getString("B.KET_TIPE_STD");
           				//li.add(id_std+"~"+id_tipe_std+"~"+nama_master_std+"~"+kode_master_std+"~"+nama_std);
           			
           				//get pertanyaan sesuai urutan
           				Vector v_q = new Vector();
           	        	ListIterator li1 =  v_q.listIterator();
           				String sql = "select G.NORUT,D.ID,A.KODE,A.ID_MASTER_STD,B.ID_STD,B.ID_TIPE_STD,C.ID AS ID_STD_ISI,A.KET_TIPE_STD,B.KET_TIPE_STD,B.TGL_STA,B.TGL_END,C.PERNYATAAN_STD,C.NO_BUTIR,C.KDPST,C.RASIONALE,C.AKTIF,C.TGL_MULAI_AKTIF,C.TGL_STOP_AKTIF,C.SCOPE,C.TIPE_PROSES_PENGAWASAN,D.QUESTION from STANDARD_MASTER_TABLE A inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD inner join STANDARD_ISI_TABLE C on B.ID_STD=C.ID_STD inner join STANDARD_ISI_QUESTION D on C.ID=D.ID_STD_ISI left join AUDIT_MUTU_INTERNAL_SUSUNAN_PERTANYAAN G on (C.ID=G.ID_STD_ISI and D.ID=G.ID_QUESTION) where A.ID_MASTER_STD=? and G.KDPST=? and D.QUESTION is not NULL and D.QUESTION<>'' order BY -G.NORUT desc,A.ID_MASTER_STD, B.ID_TIPE_STD";
           				stmt = con.prepareStatement(sql);
           				stmt.setInt(1, Integer.parseInt(id_master));
           	        	stmt.setString(2, target_kdpst);
           	        	
           	        	rs = stmt.executeQuery();
           	        	while(rs.next()) {
           	        		String norut = rs.getString("G.NORUT");
           	        		String id_std = rs.getString("ID_STD");
           	        		String id_tipe_std = rs.getString("ID_TIPE_STD");
           	        		String id_std_isi = rs.getString("ID_STD_ISI");
           	        		String isi_std = rs.getString("C.PERNYATAAN_STD");
           	        		String rasionale_std = rs.getString("C.RASIONALE");
           	        		String id_que = rs.getString("D.ID");
           	        		String que = rs.getString("D.QUESTION");
           	        		String nmm_master = rs.getString("A.KET_TIPE_STD");
           	        		String nmm_std = rs.getString("B.KET_TIPE_STD");
           	        		String tmp = norut+"~"+id_std+"~"+id_tipe_std+"~"+id_std_isi+"~"+isi_std+"~"+rasionale_std+"~"+id_que+"~"+que+"~"+nmm_master+"~"+nmm_std;
           	        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
           	        		li1.add(tmp);
           	        	}
           	        	
           	        	//yg dipake hanya master id yg memiliki pertanyaan
           	        	if(v_q.size()>0) {
           	        		String tmp = id_master+"~"+nama_master_std+"~"+kode_master_std+"~"+nama_std; 
           	        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
           	        		li.add(tmp);
           	        		li.add(v_q);
           	        	}
           	        	
           			}
           			//get info target indikator param dll
           			if(v!=null) {
           				//System.out.println("step 2");
           				String sql = "SELECT * FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on A.ID=B.ID_STD_ISI where A.ID=? order by ID_VERSI desc limit 1";
           				stmt = con.prepareStatement(sql);
           				li = v.listIterator();
           				while(li.hasNext()) {
           					String brs = (String)li.next();
           					//System.out.println("baris 2= "+brs);
           					//id_std+"~"+id_tipe_std+"~"+nama_master_std+"~"+kode_master_std+"~"+nama_std
   							st = new StringTokenizer(brs,"~");
   							//id_std+"~"+
   							String id_master_std = st.nextToken();
   							String nama_master_std = st.nextToken();
   							//kode_master_std
   							String kode_master_std = st.nextToken();
   							//nama_std
   							/*
   							 * NAMA STANDAR INI NGACo JADi TIDAK BOLEH DIPAKE, PAKE YG ADA Di VECTOR BAWAH
   							 */
   							String nama_std = st.nextToken();
           					Vector v1 = (Vector)li.next();
           					ListIterator li1 = v1.listIterator();
           					if(li1.hasNext()) {
           						while(li1.hasNext()) {
           							brs = (String)li1.next();
           							//System.out.println("baris 2a= "+brs);
           							st = new StringTokenizer(brs,"~");
           							//norut
           							String norut = st.nextToken();
           							//id_std
           							String id_std = st.nextToken();
           							//id_tipe_isi
           							String id_tipe_std = st.nextToken();
           							//id_std_isi
           							String id_std_isi = st.nextToken();
           							//isi_std
           							String isi_std = st.nextToken();
           							//rasionale_std
           							String rasionale_std = st.nextToken();
           							//que;
           							String id_que = st.nextToken();
           							//que;
           							String que = st.nextToken();
           							//que;
           							String nmm_master = st.nextToken();
           							//que;
           							String nmm_std = st.nextToken();
               						stmt.setInt(1, Integer.parseInt(id_std_isi));
               						rs = stmt.executeQuery();
               						if(rs.next()) {
               							String target_thsms1 = rs.getString("TARGET_THSMS_1");
               							String target_thsms2 = rs.getString("TARGET_THSMS_2");
               							String target_thsms3 = rs.getString("TARGET_THSMS_3");
               							String target_thsms4 = rs.getString("TARGET_THSMS_4");
               							String target_thsms5 = rs.getString("TARGET_THSMS_5");
               							String indikator = rs.getString("TKN_PARAMETER");
               							String parameter = rs.getString("TKN_INDIKATOR");
               							String starting_periode = rs.getString("TARGET_PERIOD_START");
               							String unit_periode = rs.getString("UNIT_PERIOD_USED");
               							String lama_per_periode = rs.getString("LAMA_NOMINAL_PER_PERIOD");
               							String target_unit = rs.getString("TARGET_THSMS_1_UNIT");
               							String strategi = rs.getString("STRATEGI");
               							/*
               							//System.out.println("---------------------------");
               							//System.out.println(thsms_now);
               							//System.out.println("target_thsms1="+target_thsms1);
               							//System.out.println("target_thsms2="+target_thsms2);
               							//System.out.println("target_thsms3="+target_thsms3);
               							//System.out.println("target_thsms4="+target_thsms4);
               							//System.out.println("target_thsms5="+target_thsms5);
               							//System.out.println("starting_periode="+starting_periode);
               							//System.out.println("unit_periode="+unit_periode);
               							//System.out.println("lama_per_periode="+lama_per_periode);
               							*/
               							int periode_ke=ToolSpmi.getNorutTargetParamIndikatorPeriod(starting_periode, unit_periode, lama_per_periode);
               							String target = "";
               							if(periode_ke==1) {
               								target = new String(target_thsms1);
               							}
               							else if(periode_ke==2) {
               								target = new String(target_thsms2);
               							}
               							else if(periode_ke==3) {
               								target = new String(target_thsms4);
               							}
               							else if(periode_ke==4) {
               								target = new String(target_thsms4);
               							}
               							else if(periode_ke==5) {
               								target = new String(target_thsms5);
               							}
               							//System.out.println("periode_ke="+periode_ke);
               							li1.set(brs+"~"+indikator+"~"+parameter+"~"+strategi+"~"+target+"~"+target_unit);
               							
               						}
               						else {
               							li1.set(brs+"~null~null~null~null~null");
               						}
               						//System.out.println("---------------------------<br><br>");
           						}
           						
           					}
           					else {
           						//System.out.println("empty");
           					}
           					li.set(v1);
           				}
           				
           				
           				//System.out.println("step 3");
           				//add jawaban bobot
           				stmt = con.prepareStatement("select ID,ANSWER,BOBOT from STANDARD_ISI_ANSWER where ID_QUESTION=?");
           				Vector vf = new Vector();
           				ListIterator lif = vf.listIterator();
           				li = v.listIterator();
           				while(li.hasNext()) {
           					//System.out.println("step 4");
           					String brs = (String)li.next();
           					//System.out.println("baris 4 = "+brs);
           					lif.add(brs);
           					//System.out.println(brs);
           					//id_std+"~"+id_tipe_std+"~"+nama_master_std+"~"+kode_master_std+"~"+nama_std
   							st = new StringTokenizer(brs,"~");
   							//id_std+"~"+
   							String id_master_std = st.nextToken();
   							String nama_master_std = st.nextToken();
   							//kode_master_std
   							String kode_master_std = st.nextToken();
   							//nama_std
   							String nama_std = st.nextToken();
           					Vector v1 = (Vector)li.next();
           					lif.add(v1);
           					ListIterator li1 = v1.listIterator();
           					if(li1.hasNext()) {
           						while(li1.hasNext()) {
           							brs = (String)li1.next();
           							//System.out.println("baris 4a= "+brs);
           							st = new StringTokenizer(brs,"~");
           							//norut
           							String norut = st.nextToken();
           							//id_std
           							String id_std = st.nextToken();
           							//id_tipe_isi
           							String id_tipe_std = st.nextToken();
           							//id_std_isi
           							String id_std_isi = st.nextToken();
           							//isi_std
           							String isi_std = st.nextToken();
           							//rasionale_std
           							String rasionale_std = st.nextToken();
           							//id que;
           							String id_que = st.nextToken();
           							//que;
           							String que = st.nextToken();
           							String nmm_master = st.nextToken();
           							String nmm_std = st.nextToken();
           							String indikator = st.nextToken();
           							String parameter = st.nextToken();
           							String strategi = st.nextToken();
           							String target = st.nextToken();
           							String target_unit = st.nextToken();
               						stmt.setInt(1, Integer.parseInt(id_que));
               						rs = stmt.executeQuery();
               						String tkn_id="";
               						String tkn_answer="";
               						String tkn_bobot="";
               						while(rs.next()) {
               							String id = rs.getString(1);
               							tkn_id = tkn_id+"`"+id;
               							String answer = rs.getString(2);
               							tkn_answer = tkn_answer+"`"+answer;
               							String bobot = rs.getString(3);
               							tkn_bobot = tkn_bobot+"`"+bobot;
               						}
               						tkn_id = tkn_id.substring(1, tkn_id.length());
               						tkn_answer = tkn_answer.substring(1, tkn_answer.length());
               						tkn_bobot = tkn_bobot.substring(1, tkn_bobot.length());
               						tkn_id = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tkn_id, "`");
               						tkn_answer = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tkn_answer, "`");
               						tkn_bobot = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tkn_bobot, "`");
               						
               						li1.set(brs+"~"+tkn_id+"~"+tkn_answer+"~"+tkn_bobot);
               						//System.out.println("~"+tkn_id+"~"+tkn_answer+"~"+tkn_bobot);
           						}
           					}
           					else {
           						//System.out.println("empty");
           					}
           					li.set(v1);
           				}
           				
           				//insert
           				//delete previous record just to make sure 
           				stmt = con.prepareStatement("delete from AUDIT_MUTU_INTERNAL_HASIL where ID_AMI=?");
           				stmt.setInt(1, id_ami);
           				stmt.executeUpdate();
           				//System.out.println("step 5");
           				stmt = con.prepareStatement("INSERT INTO AUDIT_MUTU_INTERNAL_HASIL(ID_AMI,ID_MASTER_STD,NAMA_MASTER_STD,ID_STD,NAMA_STD,ID_TIPE_STD,ID_STD_ISI,SASARAN_VALUE,NORUT_QA,ID_QUESTION,QUESTION,TKN_JAWABAN,TKN_BOBOT)values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
           				li = v.listIterator();
           				while(li.hasNext()) {
           					//System.out.println("step 6");
           					String brs = (String)li.next();
           					//System.out.println("baris 6= "+brs);
           					//System.out.println(brs);
           					//id_std+"~"+id_tipe_std+"~"+nama_master_std+"~"+kode_master_std+"~"+nama_std
   							st = new StringTokenizer(brs,"~");
   							//id_std+"~"+
   							String id_master_std = st.nextToken();
   							String nama_master_std = st.nextToken();
   							//kode_master_std
   							String kode_master_std = st.nextToken();
   							//nama_std
   							String nama_std = st.nextToken();
           					Vector v1 = (Vector)li.next();
           					ListIterator li1 = v1.listIterator();
           					if(li1.hasNext()) {
           						while(li1.hasNext()) {
           							brs = (String)li1.next();
           							//System.out.println("baris 6a= "+brs);
           							st = new StringTokenizer(brs,"~");
           							//norut
           							String norut = st.nextToken();
           							//id_std
           							String id_std = st.nextToken();
           							//id_tipe_isi
           							String id_tipe_std = st.nextToken();
           							//id_std_isi
           							String id_std_isi = st.nextToken();
           							//isi_std
           							String isi_std = st.nextToken();
           							//rasionale_std
           							String rasionale_std = st.nextToken();
           							//id que;
           							String id_que = st.nextToken();
           							//que;
           							String que = st.nextToken();
           							String nmm_master = st.nextToken();
           							String nmm_std = st.nextToken();
           							String indikator = st.nextToken();
           							String parameter = st.nextToken();
           							String strategi = st.nextToken();
           							String target = st.nextToken();
           							String target_unit = st.nextToken();
               						
               						String tkn_id_answer = st.nextToken();
               						String tkn_answer = st.nextToken();
               						String tkn_bobot = st.nextToken();
               						
               						int i=1;
               						//ID_AMI
               						stmt.setInt(i++,id_ami);
               						//ID_MASTER_STD
               						stmt.setInt(i++,Integer.parseInt(id_master_std));
               						//NAMA_MASTER_STD
               						//stmt.setString(i++, nama_master_std);
               						stmt.setString(i++, nmm_master);
               						//ID_STD
               						stmt.setInt(i++,Integer.parseInt(id_std));
               						//NAMA_STD
               						//stmt.setString(i++, nama_std);
               						stmt.setString(i++, nmm_std);
               						//ID_TIPE_STD
               						stmt.setInt(i++,Integer.parseInt(id_tipe_std));
               						//ID_STD_ISI
               						stmt.setInt(i++,Integer.parseInt(id_std_isi));
               						//SASARAN_VALUE
               						stmt.setFloat(i++, Float.parseFloat(target));
               						//NORUT_QA
               						stmt.setInt(i++, Integer.parseInt(norut));
               						//id_q
               						stmt.setInt(i++, Integer.parseInt(id_que));
               						//QUESTION
               						stmt.setString(i++, que);
               						//TKN_JAWABAN
               						stmt.setString(i++, tkn_answer);
               						//TKN_BOBOT
               						stmt.setString(i++, tkn_bobot);
               						updated = updated+stmt.executeUpdate();
           						}
           					}
           					else {
           						//System.out.println("empty 6");
           					}

           				}
           			}
           			
           			//insert tgl sta
                   	if(updated>0) {
                   		//System.out.println("step 7");
                   		stmt = con.prepareStatement("update AUDIT_MUTU_INTERNAL set TGL_RIL_AMI=? where ID=?");
                       	stmt.setDate(1, sta_dt);
                       	stmt.setInt(2, id_ami);
                       	updated = updated+stmt.executeUpdate();	
                   	}
                   	
                   	
           		}
           	}
           	
           	
           	
           	//1. cek status saat ini
           	
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
	
    	return updated;
    }
    
    public int updateHasilAmi(int id_ami, Vector v_idq_answer_langgar_note_saran) {
    	int updated=0;
    	StringTokenizer st = null;
    	ListIterator li=null;
    	if(v_idq_answer_langgar_note_saran!=null) {
    		try {
        		//System.out.println("id_que_and_isi_dan_norut_std="+id_que_and_isi_dan_norut_std.length);
            	Context initContext  = new InitialContext();
               	Context envContext  = (Context)initContext.lookup("java:/comp/env");
               	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
               	con = ds.getConnection();
               	String sql = "update AUDIT_MUTU_INTERNAL_HASIL set PELANGGARAN=?,CATATAN_HASIL_AMI=?,REKOMNDASI_HASIL_AMI=?,JAWABAN=?,BOBOT=? where ID_AMI=? and ID_QUESTION=?";
               	stmt = con.prepareStatement(sql);
               	li = v_idq_answer_langgar_note_saran.listIterator();
               	while(li.hasNext()) {
               		String idq = (String)li.next();
               		String[]answer = (String[])li.next();
               		String langgar = (String)li.next();
               		String note = (String)li.next();
               		String saran = (String)li.next();
               		if(Checker.isStringNullOrEmpty(langgar)||langgar.equalsIgnoreCase("false")) {
               			stmt.setBoolean(1, false);
               		}
               		else {
               			stmt.setBoolean(1, true);
               		}
               		if(Checker.isStringNullOrEmpty(note)) {
               			stmt.setNull(2, java.sql.Types.VARCHAR);
               		}
               		else {
               			stmt.setString(2, note);
               		}
               		if(Checker.isStringNullOrEmpty(saran)) {
               			stmt.setNull(3, java.sql.Types.VARCHAR);
               		}
               		else {
               			stmt.setString(3, saran);
               		}
               		if(Checker.isStringNullOrEmpty(answer[0])) {
               			stmt.setNull(4, java.sql.Types.VARCHAR);
               			stmt.setNull(5, java.sql.Types.DOUBLE);
               		}
               		else {
               			st = new StringTokenizer(answer[0],"~");
               			stmt.setString(4, st.nextToken());
               			stmt.setDouble(5, Double.parseDouble(st.nextToken()));
               		}
               		stmt.setInt(6, id_ami);
               		stmt.setInt(7, Integer.parseInt(idq));
               		updated = updated+stmt.executeUpdate();
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

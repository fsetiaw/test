package beans.dbase.feeder.importer;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.Tool;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
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
 * Session Bean implementation class KapasitasImporter
 */
@Stateless
@LocalBean
public class NilaiMhsImporter extends SearchDb {
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
    public NilaiMhsImporter() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public NilaiMhsImporter(String operatorNpm) {
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
    public NilaiMhsImporter(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

    
    public Vector getDataTrnlm(String target_thsms) {
    	//Tool.buatSatuSpasiAntarKata(brs)
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,KELASTRNLM,NIMHSMSMHS,NILAIROBOT,NLAKHROBOT,BOBOTROBOT from TRNLM inner join CIVITAS on NPMHSTRNLM=NPMHSMSMHS where THSMSTRNLM=?");
    		stmt.setString(1, target_thsms);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdpst = ""+rs.getString(1);
    			String npmhs = ""+rs.getString(2);
    			String kdkmk = ""+rs.getString(3);
    			String nilai = ""+rs.getDouble(4);
    			String nlakh = ""+rs.getString(5);
    			String bobot = ""+rs.getDouble(6);
    			String sksmk = ""+rs.getInt(7);
    			String kelas = ""+rs.getString(8);
    			String nimhs = ""+rs.getString(9);
    			String nilaib = ""+rs.getDouble(10);
    			String nlakhb = ""+rs.getString(11);
    			String bobotb = ""+rs.getDouble(12);
    			if(!kdpst.equalsIgnoreCase("57301") && !kdpst.equalsIgnoreCase("57302")) {
    				String tmp = "";
    				if(!nlakh.equalsIgnoreCase("null")&&!nlakh.equalsIgnoreCase("T")) {
    					tmp = kdpst+"`"+npmhs+"`"+kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+kelas+"`"+nimhs;	
    				}
    				else {
    					//berarti pake bbot
    					tmp = kdpst+"`"+npmhs+"`"+kdkmk+"`"+nilaib+"`"+nlakhb+"`"+bobotb+"`"+sksmk+"`"+kelas+"`"+nimhs;
    				}
    				
    				tmp = tmp.replace("``", "`null`");
    				if(v==null) {
    					v = new Vector();
    					li = v.listIterator();
    				}
    				li.add(tmp);
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
    	return v;
    }
    
    public Vector getDataTrnlm(String target_thsms, int limit, int offset) {
    	//Tool.buatSatuSpasiAntarKata(brs)
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,KELASTRNLM,NIMHSMSMHS from TRNLM inner join CIVITAS on NPMHSTRNLM=NPMHSMSMHS where THSMSTRNLM=? limit ? OFFSET ?");
    		stmt.setString(1, target_thsms);
    		stmt.setInt(2, limit);
    		stmt.setInt(3, offset);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdpst = ""+rs.getString(1);
    			String npmhs = ""+rs.getString(2);
    			String kdkmk = ""+rs.getString(3);
    			String nilai = ""+rs.getDouble(4);
    			String nlakh = ""+rs.getString(5);
    			String bobot = ""+rs.getDouble(6);
    			String sksmk = ""+rs.getInt(7);
    			String kelas = ""+rs.getString(8);
    			String nimhs = ""+rs.getString(9);

    			if(!kdpst.equalsIgnoreCase("57301") && !kdpst.equalsIgnoreCase("57302")) {
    				String tmp = kdpst+"`"+npmhs+"`"+kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+kelas+"`"+nimhs;
    				tmp = tmp.replace("``", "`null`");
    				if(v==null) {
    					v = new Vector();
    					li = v.listIterator();
    				}
    				li.add(tmp);
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
    	return v;
    }
    
    
    public Vector importNilaiMhs(String thsms) {
    	Vector v_err = new Vector();
    	ListIterator lier= v_err.listIterator();
    	//ListIterator lio = v_out.listIterator();
    	Vector v = getDataTrnlm(thsms);
    	if(v!=null && v.size()>0) {
    		//System.out.println("v_size_init="+v.size());
    		ListIterator li = v.listIterator();
        	try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc/FEEDER_IMPORTER30min");
    			con = ds.getConnection();
    			//get id_sms
    			stmt = con.prepareStatement("select id_sms from sms where kode_prodi=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				//System.out.println(brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				//kdpst+"`"+npmhs+"`"+kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+kelas+"`"+nimhs;
    				String kdpst = st.nextToken();
    				if(kdpst.equalsIgnoreCase("88888")) {
    					kdpst = "86208";
    	    		}
    				String npmhs = st.nextToken();
    				String kdkmk = st.nextToken();
    				String nilai = st.nextToken();
    				String nlakh = st.nextToken();
    				String bobot = st.nextToken();
    				String sksmk = st.nextToken();
    				String kelas = st.nextToken();
    				String nimhs = st.nextToken();
    				stmt.setString(1, kdpst);
					rs = stmt.executeQuery();
					if(rs.next()) {
						String id_sms = rs.getString(1);
						li.set(brs+"`"+id_sms);
					}
    			}	
    			stmt = con.prepareStatement("select id_kls,kelas_kuliah.id_mk,kelas_kuliah.sks_tm,kelas_kuliah.sks_prak,kelas_kuliah.sks_prak_lap,kelas_kuliah.sks_sim from kelas_kuliah inner join mata_kuliah on kelas_kuliah.id_mk=mata_kuliah.id_mk where kelas_kuliah.id_sms=? and nm_kls=? and kode_mk=? and kelas_kuliah.sks_mk=?");
    			//stmt = con.prepareStatement("select id_mk,sks_tm,sks_prak,sks_prak_lap,sks_sim from mata_kuliah where id_sms=? and kode_mk=? and nm_mk like ? and sks_mk=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				//System.out.println(brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdpst = st.nextToken();
    				if(kdpst.equalsIgnoreCase("88888")) {
    					kdpst = "86208";
    	    		}
    				String npmhs = st.nextToken();
    				String kdkmk = st.nextToken();
    				String nilai = st.nextToken();
    				String nlakh = st.nextToken();
    				String bobot = st.nextToken();
    				String sksmk = st.nextToken();
    				String kelas = st.nextToken();
    				String nimhs = st.nextToken();
    				String id_sms = st.nextToken();
    				stmt.setString(1, id_sms);
    				stmt.setString(2, kelas);
    				stmt.setString(3, kdkmk);
    				//stmt.setString(4, Tool.buatSatuSpasiAntarKata(nakmk)+"%");
    				stmt.setInt(4, Integer.parseInt(sksmk));
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					String id_kls = ""+rs.getString(1);
        				String id_mk = ""+rs.getString(2);
        				String sks_tm = ""+rs.getInt(3);
        				String sks_prak = ""+rs.getInt(4);
        				String sks_prak_lap = ""+rs.getInt(5);
        				String sks_sim = ""+rs.getInt(6);
        				//li.set(brs+"`"+id_mk+"`"+id_kls+"`"+sks_tm+"`"+sks_prak+"`"+sks_prak_lap+"`"+sks_sim);
        				li.set(brs+"`"+id_mk+"`"+id_kls);
    				}
    				else {
    					//System.out.println("kelas = "+brs);
    					lier.add("Missing kelas : "+brs);;
    					li.remove();
    				}
    			}
    			
    			stmt = con.prepareStatement("select id_reg_pd from mahasiswa_pt where nipd=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				//System.out.println(brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdpst = st.nextToken();
    				if(kdpst.equalsIgnoreCase("88888")) {
    					kdpst = "86208";
    	    		}
    				String npmhs = st.nextToken();
    				String kdkmk = st.nextToken();
    				String nilai = st.nextToken();
    				String nlakh = st.nextToken();
    				String bobot = st.nextToken();
    				String sksmk = st.nextToken();
    				String kelas = st.nextToken();
    				String nimhs = st.nextToken();
    				String id_sms = st.nextToken();
    				String id_mk = st.nextToken();
    				String id_kls = st.nextToken();
    				stmt.setString(1,nimhs);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					String id_reg_pd = rs.getString(1);
    					li.add(brs+"`"+id_reg_pd);
    				}
    				else {
    					//System.out.println("missing- "+brs);
    					lier.add("Missing Mhs : "+brs);
    					li.remove();
    				}
    			}
    			Vector v_ins = null;
    			ListIterator lins = null;
    			stmt = con.prepareStatement("update nilai set nilai_angka=?,nilai_huruf=?,nilai_indeks=? where id_kls=? and id_reg_pd=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				//System.out.println(brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdpst = st.nextToken();
    				if(kdpst.equalsIgnoreCase("88888")) {
    					kdpst = "86208";
    	    		}
    				String npmhs = st.nextToken();
    				String kdkmk = st.nextToken();
    				String nilai = st.nextToken();
    				String nlakh = st.nextToken();
    				String bobot = st.nextToken();
    				String sksmk = st.nextToken();
    				String kelas = st.nextToken();
    				String nimhs = st.nextToken();
    				String id_sms = st.nextToken();
    				String id_mk = st.nextToken();
    				String id_kls = st.nextToken();
    				String id_reg_pd = st.nextToken();
    				stmt.setFloat(1, Float.parseFloat(nilai));
    				stmt.setString(2, nlakh);
    				stmt.setDouble(3, Double.parseDouble(bobot));
    				stmt.setString(4, id_kls);
    				stmt.setString(5, id_reg_pd);
    				int i = stmt.executeUpdate();
    				if(i<1) {
    					if(v_ins==null) {
    						v_ins = new Vector();
    						lins = v_ins.listIterator();
    					}
    					lins.add(brs);
    				}
    			}
    			stmt = con.prepareStatement("insert into nilai(id_kls,id_reg_pd,nilai_angka,nilai_huruf,nilai_indeks)values(?,?,?,?,?)");
    			li = v_ins.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				System.out.print(brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdpst = st.nextToken();
    				if(kdpst.equalsIgnoreCase("88888")) {
    					kdpst = "86208";
    	    		}
    				String npmhs = st.nextToken();
    				String kdkmk = st.nextToken();
    				String nilai = st.nextToken();
    				String nlakh = st.nextToken();
    				String bobot = st.nextToken();
    				String sksmk = st.nextToken();
    				String kelas = st.nextToken();
    				String nimhs = st.nextToken();
    				String id_sms = st.nextToken();
    				String id_mk = st.nextToken();
    				String id_kls = st.nextToken();
    				String id_reg_pd = st.nextToken();
    				stmt.setString(1, id_kls);
    				stmt.setString(2, id_reg_pd);
    				stmt.setFloat(3, Float.parseFloat(nilai));
    				stmt.setString(4, nlakh);
    				stmt.setDouble(5, Double.parseDouble(bobot));
    				int i = stmt.executeUpdate();
    				System.out.println(" = "+i);
    				if(i<1) {
    					lier.add(brs);
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
    	
    	return v_err;  
    	
    }
    
    public Vector importNilaiMhs(Vector v) {//v_getDataTrnlm
    	Vector v_err = new Vector();
    	ListIterator lier= v_err.listIterator();
    	//ListIterator lio = v_out.listIterator();
    	
    	if(v!=null && v.size()>0) {
    		//System.out.println("v_size_init="+v.size());
    		ListIterator li = v.listIterator();
        	try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc/FEEDER_IMPORTER30min");
    			con = ds.getConnection();
    			//get id_sms
    			stmt = con.prepareStatement("select id_sms from sms where kode_prodi=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				//System.out.println(brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				//kdpst+"`"+npmhs+"`"+kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+kelas+"`"+nimhs;
    				//kdpst+"`"+npmhs+"`"+kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+kelas+"`"+nimhs;
    				String kdpst = st.nextToken();
    				if(kdpst.equalsIgnoreCase("88888")) {
    					kdpst = "86208";
    	    		}
    				String npmhs = st.nextToken();
    				String kdkmk = st.nextToken();
    				String nilai = st.nextToken();
    				String nlakh = st.nextToken();
    				String bobot = st.nextToken();
    				String sksmk = st.nextToken();
    				String kelas = st.nextToken();
    				String nimhs = st.nextToken();
    				stmt.setString(1, kdpst);
					rs = stmt.executeQuery();
					if(rs.next()) {
						String id_sms = rs.getString(1);
						li.set(brs+"`"+id_sms);
					}
					else {
						lier.add("Missing id_sms : "+brs);
						li.remove();
						//System.out.print("prodi="+brs);
					}
    			}	
    			stmt = con.prepareStatement("select id_kls,kelas_kuliah.id_mk,kelas_kuliah.sks_tm,kelas_kuliah.sks_prak,kelas_kuliah.sks_prak_lap,kelas_kuliah.sks_sim from kelas_kuliah inner join mata_kuliah on kelas_kuliah.id_mk=mata_kuliah.id_mk where kelas_kuliah.id_sms=? and nm_kls=? and kode_mk=? and kelas_kuliah.sks_mk=?");
    			//stmt = con.prepareStatement("select id_mk,sks_tm,sks_prak,sks_prak_lap,sks_sim from mata_kuliah where id_sms=? and kode_mk=? and nm_mk like ? and sks_mk=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				//System.out.println(brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdpst = st.nextToken();
    				if(kdpst.equalsIgnoreCase("88888")) {
    					kdpst = "86208";
    	    		}
    				String npmhs = st.nextToken();
    				String kdkmk = st.nextToken();
    				String nilai = st.nextToken();
    				String nlakh = st.nextToken();
    				String bobot = st.nextToken();
    				String sksmk = st.nextToken();
    				String kelas = st.nextToken();
    				String nimhs = st.nextToken();
    				String id_sms = st.nextToken();
    				stmt.setString(1, id_sms);
    				stmt.setString(2, kelas);
    				stmt.setString(3, kdkmk);
    				//stmt.setString(4, Tool.buatSatuSpasiAntarKata(nakmk)+"%");
    				stmt.setInt(4, Integer.parseInt(sksmk));
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					String id_kls = ""+rs.getString(1);
        				String id_mk = ""+rs.getString(2);
        				String sks_tm = ""+rs.getInt(3);
        				String sks_prak = ""+rs.getInt(4);
        				String sks_prak_lap = ""+rs.getInt(5);
        				String sks_sim = ""+rs.getInt(6);
        				//li.set(brs+"`"+id_mk+"`"+id_kls+"`"+sks_tm+"`"+sks_prak+"`"+sks_prak_lap+"`"+sks_sim);
        				li.set(brs+"`"+id_mk+"`"+id_kls);
    				}
    				else {
    					//System.out.println("kelas = "+brs);
    					lier.add("Missing id_kls : "+brs);
    					li.remove();
    				}
    			}
    			
    			stmt = con.prepareStatement("select id_reg_pd from mahasiswa_pt where nipd=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				//System.out.println(brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdpst = st.nextToken();
    				if(kdpst.equalsIgnoreCase("88888")) {
    					kdpst = "86208";
    	    		}
    				String npmhs = st.nextToken();
    				String kdkmk = st.nextToken();
    				String nilai = st.nextToken();
    				String nlakh = st.nextToken();
    				String bobot = st.nextToken();
    				String sksmk = st.nextToken();
    				String kelas = st.nextToken();
    				String nimhs = st.nextToken();
    				String id_sms = st.nextToken();
    				String id_mk = st.nextToken();
    				String id_kls = st.nextToken();
    				stmt.setString(1,nimhs);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					String id_reg_pd = rs.getString(1);
    					li.set(brs+"`"+id_reg_pd);
    					//System.out.println("kok="+brs+"`"+id_reg_pd);
    				}
    				else {
    					//System.out.println("missing- "+brs);
    					lier.add("Missing id_reg_pd : "+brs);
    					li.remove();
    				}
    			}
    			Vector v_ins = null;
    			ListIterator lins = null;
    			stmt = con.prepareStatement("update nilai set nilai_angka=?,nilai_huruf=?,nilai_indeks=? where id_kls=? and id_reg_pd=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				System.out.print("update "+brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdpst = st.nextToken();
    				if(kdpst.equalsIgnoreCase("88888")) {
    					kdpst = "86208";
    	    		}
    				String npmhs = st.nextToken();
    				String kdkmk = st.nextToken();
    				String nilai = st.nextToken();
    				String nlakh = st.nextToken();
    				String bobot = st.nextToken();
    				String sksmk = st.nextToken();
    				String kelas = st.nextToken();
    				String nimhs = st.nextToken();
    				String id_sms = st.nextToken();
    				String id_mk = st.nextToken();
    				String id_kls = st.nextToken();
    				String id_reg_pd = st.nextToken();
    				stmt.setFloat(1, Float.parseFloat(nilai));
    				stmt.setString(2, nlakh);
    				stmt.setDouble(3, Double.parseDouble(bobot));
    				stmt.setString(4, id_kls);
    				stmt.setString(5, id_reg_pd);
    				int i = stmt.executeUpdate();
    				System.out.println(" = "+i);
    				if(i<1) {
    					if(v_ins==null) {
    						v_ins = new Vector();
    						lins = v_ins.listIterator();
    					}
    					lins.add(brs);
    				}
    			}
    			stmt = con.prepareStatement("insert ignore into nilai(id_kls,id_reg_pd,nilai_angka,nilai_huruf,nilai_indeks)values(?,?,?,?,?)");
    			if(v_ins!=null) {
    				li = v_ins.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				System.out.print("insert "+brs);
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				String kdpst = st.nextToken();
        				if(kdpst.equalsIgnoreCase("88888")) {
        					kdpst = "86208";
        	    		}
        				String npmhs = st.nextToken();
        				String kdkmk = st.nextToken();
        				String nilai = st.nextToken();
        				String nlakh = st.nextToken();
        				String bobot = st.nextToken();
        				String sksmk = st.nextToken();
        				String kelas = st.nextToken();
        				String nimhs = st.nextToken();
        				String id_sms = st.nextToken();
        				String id_mk = st.nextToken();
        				String id_kls = st.nextToken();
        				String id_reg_pd = st.nextToken();
        				stmt.setString(1, id_kls);
        				stmt.setString(2, id_reg_pd);
        				stmt.setFloat(3, Float.parseFloat(nilai));
        				stmt.setString(4, nlakh);
        				stmt.setDouble(5, Double.parseDouble(bobot));
        				int i = stmt.executeUpdate();
        				System.out.println(" = "+i);
        				if(i<1) {
        					lier.add(brs);
        					lier.add("Gagal insert : "+brs);
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
    	
    	return v_err;  
    	
    }
    
}

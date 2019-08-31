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
public class MataKuliahImporter extends SearchDb {
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
    public MataKuliahImporter() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public MataKuliahImporter(String operatorNpm) {
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
    public MataKuliahImporter(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

    
    public Vector getFixed() {
    	
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//v = Getter.getListProdi();
    		stmt = con.prepareStatement("select * from MAKUL order by KDPSTMAKUL,KDKMKMAKUL,SKSTMMAKUL");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String prev_idkmk = ""+rs.getLong("IDKMKMAKUL");
    			String prev_kdpst = ""+rs.getString("KDPSTMAKUL");
    			String prev_kdkmk = ""+rs.getString("KDKMKMAKUL");
    			String prev_nakmk = ""+rs.getString("NAKMKMAKUL");
    			String prev_sksmk = ""+rs.getLong("SKSTMMAKUL");
    			while(rs.next()) {
    				String idkmk = ""+rs.getLong("IDKMKMAKUL");
    				String kdpst = ""+rs.getString("KDPSTMAKUL");
    				String kdkmk = ""+rs.getString("KDKMKMAKUL");
        			String nakmk = ""+rs.getString("NAKMKMAKUL");
        			String sksmk = ""+rs.getLong("SKSTMMAKUL");
        			if(kdpst.equalsIgnoreCase(prev_kdpst) && kdkmk.equalsIgnoreCase(prev_kdkmk)) {
        				if(v==null) {
        					v=new Vector();
        					li = v.listIterator();
        				}
        				li.add(prev_idkmk+"`"+prev_kdpst+"`"+prev_kdkmk);
        				li.add(idkmk+"`"+kdpst+"`"+kdkmk);
        			}
        			prev_idkmk = new String(idkmk);
        			prev_kdpst = new String(kdpst);
        			prev_kdkmk = new String(kdkmk);
        			prev_nakmk = new String(nakmk);
        			prev_sksmk = new String(sksmk);
    			}	
    		}
    		v = Tool.removeDuplicateFromVector(v);
    		li = v.listIterator();
    		stmt = con.prepareStatement("select * from MAKUR where IDKMKMAKUR=?");
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			System.out.println("ha="+brs);
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String idkmk = st.nextToken();
    			String kdpst = st.nextToken();
    			String kdkmk = st.nextToken();
    			
    			//stmt.setLong(1, Long.parseLong(idkmk));
    			//rs = stmt.executeQuery();
    			//if(!rs.next()) {
    				//System.out.println("hapus="+brs);
    			//}
    			//else {
    			//	li.remove();
    				//System.out.println("ada="+brs);
    			//}
    			//System.out.println(brs);
    		}
    		/*
    		li = v.listIterator();
    		stmt = con.prepareStatement("delete from MAKUL where IDKMKMAKUL=?");
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String idkmk = st.nextToken();
    			String kdpst = st.nextToken();
    			String kdkmk = st.nextToken();
    			stmt.setLong(1, Long.parseLong(idkmk));
    			int i = stmt.executeUpdate();
    			System.out.println(i+". hapus="+brs);
    		}	
    		*/
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
    
    public Vector getDataMakul() {
    	
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();    		
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//v = Getter.getListProdi();
    		stmt = con.prepareStatement("select IDKURKRKLM from KRKLM");
    		rs = stmt.executeQuery();
    		Vector v_kur = new Vector();
    		ListIterator li_kur = v_kur.listIterator();
    		while(rs.next()) {
    			String id = ""+rs.getInt(1);
    			li_kur.add(id);
    		}
    		
    		li_kur = v_kur.listIterator();
    		stmt = con.prepareStatement("select * from MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=?");
    		Vector v_mk = new Vector();
    		ListIterator li_mk = v_mk.listIterator();
    		while(li_kur.hasNext()) {
    			String id = (String)li_kur.next();
    			stmt.setLong(1, Long.parseLong(id));
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String kdpst = ""+rs.getString("KDPSTMAKUL");
    				if(kdpst.equalsIgnoreCase("88888")) {
    					kdpst = "86208";
    				}
    				String kdkmk = ""+rs.getString("KDKMKMAKUL"); //KDKMK tidak boleh berubah
    				String nakmk = ""+rs.getString("NAKMKMAKUL");
    				nakmk = Tool.buatSatuSpasiAntarKata(nakmk);
    				String skstm = ""+rs.getInt("SKSTMMAKUL");
    				String skspr = ""+rs.getInt("SKSPRMAKUL");
    				String skslp = ""+rs.getInt("SKSLPMAKUL");
    				String kdwpl = ""+rs.getString("KDWPLMAKUL");
    				if(Checker.isStringNullOrEmpty(kdwpl)) {
    					kdwpl = "null";
    				}
    				String jenis = ""+rs.getString("JENISMAKUL");
    				if(Checker.isStringNullOrEmpty(jenis)) {
    					jenis = "null";
    				}
    				String nodos = ""+rs.getString("NODOSMAKUL");
    				if(Checker.isStringNullOrEmpty(nodos)) {
    					nodos = "null";
    				}
    				String stkmk = ""+rs.getString("STKMKMAKUL");
    				if(Checker.isStringNullOrEmpty(stkmk)) {
    					stkmk = "null";
    				}
    				/*
    				 *SEMES BELUM DIMASUKAN KARENA MO DISTINCT DULU 
    				 */
    				//String semes = ""+rs.getInt("SEMESMAKUR"); 
    				
    				li_mk.add(kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp+"`"+kdwpl+"`"+jenis+"`"+nodos+"`"+kdpst);
    			}
    		}
    		
    		v_mk = Tool.removeDuplicateFromVector(v_mk);
    		v = v_mk;
    		
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
    
    public Vector importMakul() {
    	Vector v_err = new Vector();
    	ListIterator lie = v_err.listIterator();
    	//ListIterator lio = v_out.listIterator();
    	Vector v = getDataMakul();
    	
    	if(v!=null && v.size()>0) {
    		
        	try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc/FEEDER_IMPORTER");
    			con = ds.getConnection();
    			Vector v_tmp = new Vector();
    			ListIterator lit = v_tmp.listIterator();
    			
    			/*
    			 * SINKRON NAMA MK AGAR SEMUA MENGGUNAKAN 1 SPASE AGAR BISA DIGUNAKAN PERBANDINGAN ANTAR MATA KULIAH YG SUDAH ADA DGN
    			 * YG BELUM
    			 */
    			stmt = con.prepareStatement("select * from mata_kuliah");
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String id_mk = rs.getString("id_mk");
        			String nm_mk = rs.getString("nm_mk");
        			lit.add(id_mk+"`"+nm_mk);	
    			}
    			lit = v_tmp.listIterator();
    			stmt = con.prepareStatement("update mata_kuliah set nm_mk=? where id_mk=?");
    			while(lit.hasNext()) {
    				String brs = (String)lit.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String id = st.nextToken();
    				String nakmk = st.nextToken();
    				stmt.setString(1, Tool.buatSatuSpasiAntarKata(nakmk));
    				stmt.setString(2,id);
    				stmt.executeUpdate();
    				
    			}
    			//==================================END SINKRON 1 SPASI==============================
    			

    			//1. TAMBAHKAN INFO id jenjang didik
    			ListIterator li = v.listIterator();
    			stmt = con.prepareStatement("select * from sms where kode_prodi=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String skstm = st.nextToken();
    				String skspr = st.nextToken();
    				String skslp = st.nextToken();
    				String kdwpl = st.nextToken();
    				String jenis = st.nextToken();
    				String nodos = st.nextToken();
    				String kdpst = st.nextToken();
    				stmt.setString(1, kdpst);
    				rs = stmt.executeQuery();
    				rs.next();
    				String id_sms = rs.getString("id_sms");
    				long id_jenj_didik = rs.getLong("id_jenj_didik");
    				li.set(brs+"`"+id_jenj_didik+"`"+id_sms);
    				
    			}
    			Collections.sort(v);
    			
    			Vector v_ins = new Vector();
    			ListIterator li_ins = v_ins.listIterator();
    			li = v.listIterator();
    			String prev_kdkmk = "";
				String prev_nakmk = "";
				String prev_skstm = "";
				String prev_skspr = "";
				String prev_skslp = "";
				String prev_kdwpl = "";
				String prev_jenis = "";
				String prev_nodos = "";
				String prev_kdpst = "";
				String prev_id_jen = "";
				String prev_id_sms = "";
				long prev_sksmk = 0;
				boolean first = true;
    			//filter mk yg sudah ada di feeder
				//NM_MK sudah menngunakan 1 spasi baik di feeder maupun di makul
    			stmt = con.prepareStatement("select * from mata_kuliah where id_sms=? and kode_mk=? and nm_mk=? and sks_mk=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String skstm = st.nextToken();
    				String skspr = st.nextToken();
    				String skslp = st.nextToken();
    				String kdwpl = st.nextToken();
    				String jenis = st.nextToken();
    				String nodos = st.nextToken();
    				String kdpst = st.nextToken();
    				String id_jen = st.nextToken();
    				String id_sms = st.nextToken();
    				long sksmk = Long.parseLong(skstm)+Long.parseLong(skspr)+Long.parseLong(skslp);
    				
    				stmt.setString(1, id_sms);
    				stmt.setString(2, kdkmk);
    				stmt.setString(3, nakmk.toUpperCase());
    				stmt.setLong(4, sksmk);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					li.remove();
    				}
    				else {
						if(first) {
							first = false;
							prev_kdkmk = new String(kdkmk);
							prev_nakmk = new String(nakmk);
							prev_skstm = new String(skstm);
							prev_skspr = new String(skspr);
							prev_skslp = new String(skslp);
							prev_kdwpl = new String(kdwpl);
							prev_jenis = new String(jenis);
							prev_nodos = new String(nodos);
							prev_kdpst = new String(kdpst);
							prev_id_jen = new String(id_jen);
							prev_id_sms = new String(id_sms);
							prev_sksmk = sksmk;
						}
						else {
							if(kdkmk.equalsIgnoreCase(prev_kdkmk) && nakmk.equalsIgnoreCase(prev_nakmk)) {
							//masalah harus dimodifikasi nakmk
								int dobel = 0;
								boolean match = true;
								while(match) {
									nakmk = prev_nakmk+" ["+prev_kdpst+"-"+dobel+++"]";
									stmt.setString(1, id_sms);
				    				stmt.setString(2, kdkmk);
				    				stmt.setString(3, nakmk.toUpperCase());
				    				stmt.setLong(4, sksmk);
				    				//if(!nakmk.equalsIgnoreCase(prev_nakmk)) {
									if(!rs.next()) {
										match = false;
									}
								}
								prev_kdkmk = new String(kdkmk);
								//prev_nakmk = new String(nakmk); pake nakmk yg sebelumnya saja
								prev_skstm = new String(skstm);
								prev_skspr = new String(skspr);
								prev_skslp = new String(skslp);
								prev_kdwpl = new String(kdwpl);
	        					prev_jenis = new String(jenis);
	        					prev_nodos = new String(nodos);
	        					prev_kdpst = new String(kdpst);
	        					prev_id_jen = new String(id_jen);
	        					prev_id_sms = new String(id_sms);
	        					prev_sksmk = sksmk;
								
							}
							else {
								prev_kdkmk = new String(kdkmk);
								prev_nakmk = new String(nakmk);
								prev_skstm = new String(skstm);
								prev_skspr = new String(skspr);
								prev_skslp = new String(skslp);
								prev_kdwpl = new String(kdwpl);
	        					prev_jenis = new String(jenis);
	        					prev_nodos = new String(nodos);
	        					prev_kdpst = new String(kdpst);
	        					prev_id_jen = new String(id_jen);
	        					prev_id_sms = new String(id_sms);
	        					prev_sksmk = sksmk;
							}	
						}
						li.set(kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp+"`"+kdwpl+"`"+jenis+"`"+nodos+"`"+kdpst+"`"+id_jen+"`"+id_sms+"`"+sksmk);
					}
    			}
    			//System.out.println("v.size="+v.size());
    			
    			
    			
    			//cek apa sudah ada kdkmk & nakmknya di feeder tanpa id_sms
    			li = v.listIterator();
    			stmt = con.prepareStatement("select * from mata_kuliah where kode_mk=? and nm_mk=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String skstm = st.nextToken();
    				String skspr = st.nextToken();
    				String skslp = st.nextToken();
    				String kdwpl = st.nextToken();
    				String jenis = st.nextToken();
    				String nodos = st.nextToken();
    				String kdpst = st.nextToken();
    				String id_jen = st.nextToken();
    				String id_sms = st.nextToken();
    				String sksmk = st.nextToken();
    				stmt.setString(1,kdkmk);
    				stmt.setString(2,nakmk);
    				rs = stmt.executeQuery();
    				
    				if(rs.next()) {
    					int dobel = 0;
    					boolean match = true;
    					while(match) {
    						nakmk = nakmk+"["+kdpst+"-"+dobel+++"]";
    						stmt.setString(1, kdkmk);
    						stmt.setString(2, nakmk);
    						rs = stmt.executeQuery();
    						if(!rs.next()) {
    							match = false;
    						}
    					}
    					li.set(kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp+"`"+kdwpl+"`"+jenis+"`"+nodos+"`"+kdpst+"`"+id_jen+"`"+id_sms+"`"+sksmk);
    					//System.out.println("dobel="+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp+"`"+kdwpl+"`"+jenis+"`"+nodos+"`"+kdpst+"`"+id_jen+"`"+id_sms+"`"+sksmk);
    				}
    			}
    			
    			
    			int j=0;
    			li = v.listIterator();
    			stmt = con.prepareStatement("INSERT ignore INTO mata_kuliah (id_mk,id_sms,id_jenj_didik,kode_mk,nm_mk,sks_mk,tgl_mulai_efektif,tgl_akhir_efektif)values(?,?,?,?,?,?,?,?)");
    			while(li.hasNext()) {
    				
    				String brs = (String)li.next();
    				//System.out.println(brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String skstm = st.nextToken();
    				String skspr = st.nextToken();
    				String skslp = st.nextToken();
    				String kdwpl = st.nextToken();
    				String jenis = st.nextToken();
    				String nodos = st.nextToken();
    				String kdpst = st.nextToken();
    				String id_jen = st.nextToken();
    				String id_sms = st.nextToken();
    				String sksmk = st.nextToken();
    				stmt.setString(1, ""+j++);
    				stmt.setString(2, id_sms);
    				stmt.setString(3, id_jen);
    				stmt.setString(4, kdkmk);
    				stmt.setString(5, nakmk);
    				stmt.setLong(6, Long.parseLong(sksmk));
    				stmt.setDate(7, java.sql.Date.valueOf("2016-01-02"));
    				stmt.setNull(8, java.sql.Types.DATE);
    				int i = stmt.executeUpdate();
    				if(i<1) {
    					lie.add(brs);
    				}
    			}
    			
    			//update mata_kuliah agar tidak ada penamaan dobel
    			Vector v_up = new Vector();
    			ListIterator liu = v_up.listIterator();
    			
    			stmt = con.prepareStatement("select * from mata_kuliah inner join sms on mata_kuliah.id_sms=sms.id_sms order by kode_mk,nm_mk");
    			rs = stmt.executeQuery();
    			//String prev_id_mk = "";
    			String prev_kode_mk = "";
    			String prev_nm_mk = "";
    			String prev_prodi = "";
    			
    			if(rs.next()) {
    				String id_mk = rs.getString("id_mk");
    				prev_kode_mk = rs.getString("kode_mk");
    				prev_nm_mk = rs.getString("nm_mk");
    				prev_nm_mk = rs.getString("kode_prodi");
    				//liu.add(id_mk+"`"+prev_kode_mk+"`"+prev_nm_mk);
    				while(rs.next()) {
    					int dobel=0;
    					id_mk = rs.getString("id_mk");
        				String kode_mk = rs.getString("kode_mk");
        				String nm_mk = rs.getString("nm_mk");
        				String prodi = rs.getString("kode_prodi");
        				if(kode_mk.equalsIgnoreCase(prev_kode_mk)&&nm_mk.equalsIgnoreCase(prev_nm_mk)) {
        					boolean match = true;
        					//while(match) {
        					//	nm_mk=nm_mk+"["+dobel+++"]";
        					//	if(!nm_mk.equalsIgnoreCase(prev_nm_mk)) {
        					//		match = false;
        					//	}
        					//}
        					/*
        					 * yg diupdate yg dobel saja
        					 */
        					liu.add(id_mk+"`"+kode_mk+"`"+nm_mk+"`"+prodi);
        					//System.out.println("update = "+id_mk+"`"+kode_mk+"`"+nm_mk);
        				}
        				
        				prev_kode_mk = new String(kode_mk);
        				prev_nm_mk = new String(nm_mk);
        				
        				
    				}
    			}
    			
    			liu = v_up.listIterator();
    			if(liu.hasNext()) {
    				int dobel=0;
    				stmt=con.prepareStatement("select * from mata_kuliah where kode_mk=? and nm_mk=?");
    				do {
    					String brs = (String)liu.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String id_mk =st.nextToken();
    					String kode_mk =st.nextToken();
    					String nm_mk =st.nextToken();
    					String prodi =st.nextToken();
    					stmt.setString(1, kode_mk);
    					stmt.setString(2, nm_mk);
    					rs = stmt.executeQuery();
    					
    					if(rs.next()) {
    						boolean match = true;
    						while(match) {
    							if(dobel==0) {
    								dobel++;
    								nm_mk=nm_mk+" ["+prodi+"]";
    							}
    							else {
    								nm_mk=nm_mk+" ["+prodi+"-"+dobel+++"]";
    							}	
    							stmt.setString(1, kode_mk);
    	    					stmt.setString(2, nm_mk);
    	    					rs = stmt.executeQuery();
    	    					if(!rs.next()) {
    	    						match = false;
    	    					}
    						}
    						//dobel=0;
    						liu.set(id_mk+"`"+kode_mk+"`"+nm_mk);
    					}
    					//else {
    						//dobel=0;
    					//}
    					
    				}
    				while(liu.hasNext());
    				
    			}
    			liu = v_up.listIterator();
    			if(liu.hasNext()) {
    				stmt=con.prepareStatement("update mata_kuliah set nm_mk=? where id_mk=?");
    				do {
    					String brs = (String)liu.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String id_mk =st.nextToken();
    					String kode_mk =st.nextToken();
    					String nm_mk =st.nextToken();
    					stmt.setString(1, nm_mk);
    					stmt.setString(2, id_mk);
    					stmt.executeUpdate();
    				}
    				while(liu.hasNext());
    			}	
    			//INSERT INTO mata_kuliah (id_mk,id_sms,id_jenj_didik,kode_mk,nm_mk,sks_mk,tgl_mulai_efektif,tgl_akhir_efektif)
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
    
    
    public Vector importMakul_v1() {
    	Vector v_err = new Vector();
    	ListIterator lie = v_err.listIterator();
    	//ListIterator lio = v_out.listIterator();
    	Vector v = getDataMakul();
    	
    	if(v!=null && v.size()>0) {
    		
        	try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc/FEEDER_IMPORTER");
    			con = ds.getConnection();
    			Vector v_tmp = new Vector();
    			ListIterator lit = v_tmp.listIterator();
    			
    			/*
    			 * SINKRON NAMA MK AGAR SEMUA MENGGUNAKAN 1 SPASE AGAR BISA DIGUNAKAN PERBANDINGAN ANTAR MATA KULIAH YG SUDAH ADA DGN
    			 * YG BELUM
    			 */
    			stmt = con.prepareStatement("select * from mata_kuliah");
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String id_mk = rs.getString("id_mk");
        			String nm_mk = rs.getString("nm_mk");
        			lit.add(id_mk+"`"+nm_mk);	
    			}
    			lit = v_tmp.listIterator();
    			stmt = con.prepareStatement("update mata_kuliah set nm_mk=? where id_mk=?");
    			while(lit.hasNext()) {
    				String brs = (String)lit.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String id = st.nextToken();
    				String nakmk = st.nextToken();
    				stmt.setString(1, Tool.buatSatuSpasiAntarKata(nakmk));
    				stmt.setString(2,id);
    				stmt.executeUpdate();
    				
    			}
    			//==================================END SINKRON 1 SPASI==============================
    			

    			//1. TAMBAHKAN INFO id jenjang didik
    			ListIterator li = v.listIterator();
    			stmt = con.prepareStatement("select * from sms where kode_prodi=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String skstm = st.nextToken();
    				String skspr = st.nextToken();
    				String skslp = st.nextToken();
    				String kdwpl = st.nextToken();
    				String jenis = st.nextToken();
    				String nodos = st.nextToken();
    				String kdpst = st.nextToken();
    				stmt.setString(1, kdpst);
    				rs = stmt.executeQuery();
    				rs.next();
    				String id_sms = rs.getString("id_sms");
    				long id_jenj_didik = rs.getLong("id_jenj_didik");
    				li.set(brs+"`"+id_jenj_didik+"`"+id_sms);
    				
    			}
    			Collections.sort(v);
    			
    			Vector v_ins = new Vector();
    			ListIterator li_ins = v_ins.listIterator();
    			li = v.listIterator();
    			String prev_kdkmk = "";
				String prev_nakmk = "";
				String prev_skstm = "";
				String prev_skspr = "";
				String prev_skslp = "";
				String prev_kdwpl = "";
				String prev_jenis = "";
				String prev_nodos = "";
				String prev_kdpst = "";
				String prev_id_jen = "";
				String prev_id_sms = "";
				long prev_sksmk = 0;
				boolean first = true;
    			//filter mk yg sudah ada di feeder
				//NM_MK sudah menngunakan 1 spasi baik di feeder maupun di makul
    			stmt = con.prepareStatement("select * from mata_kuliah where id_sms=? and kode_mk=? and nm_mk=? and sks_mk=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String skstm = st.nextToken();
    				String skspr = st.nextToken();
    				String skslp = st.nextToken();
    				String kdwpl = st.nextToken();
    				String jenis = st.nextToken();
    				String nodos = st.nextToken();
    				String kdpst = st.nextToken();
    				String id_jen = st.nextToken();
    				String id_sms = st.nextToken();
    				long sksmk = Long.parseLong(skstm)+Long.parseLong(skspr)+Long.parseLong(skslp);
    				
    				stmt.setString(1, id_sms);
    				stmt.setString(2, kdkmk);
    				stmt.setString(3, nakmk.toUpperCase());
    				stmt.setLong(4, sksmk);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					li.remove();
    				}
    				else {
						if(first) {
							first = false;
							prev_kdkmk = new String(kdkmk);
							prev_nakmk = new String(nakmk);
							prev_skstm = new String(skstm);
							prev_skspr = new String(skspr);
							prev_skslp = new String(skslp);
							prev_kdwpl = new String(kdwpl);
							prev_jenis = new String(jenis);
							prev_nodos = new String(nodos);
							prev_kdpst = new String(kdpst);
							prev_id_jen = new String(id_jen);
							prev_id_sms = new String(id_sms);
							prev_sksmk = sksmk;
						}
						else {
							int dobel = 0;
							if(kdkmk.equalsIgnoreCase(prev_kdkmk) && nakmk.equalsIgnoreCase(prev_nakmk)) {
							//masalah harus dimodifikasi nakmk
							//revisi ngga perlu dipikirin sekaran, belakangan aja setelah diinsert	
								
								boolean match = true;
								while(match) {
									if(dobel==0) {
										dobel++;
										nakmk = prev_nakmk+" ["+prev_kdpst+"]";
									}
									else {
										nakmk = prev_nakmk+" ["+prev_kdpst+"-"+dobel+++"]";
									}	
									stmt.setString(1, id_sms);
				    				stmt.setString(2, kdkmk);
				    				stmt.setString(3, nakmk.toUpperCase());
				    				stmt.setLong(4, sksmk);
				    				//if(!nakmk.equalsIgnoreCase(prev_nakmk)) {
									if(!rs.next()) {
										match = false;
									}
								}
								prev_kdkmk = new String(kdkmk);
								//prev_nakmk = new String(nakmk); pake nakmk yg sebelumnya saja
								prev_skstm = new String(skstm);
								prev_skspr = new String(skspr);
								prev_skslp = new String(skslp);
								prev_kdwpl = new String(kdwpl);
	        					prev_jenis = new String(jenis);
	        					prev_nodos = new String(nodos);
	        					prev_kdpst = new String(kdpst);
	        					prev_id_jen = new String(id_jen);
	        					prev_id_sms = new String(id_sms);
	        					prev_sksmk = sksmk;
								
							}
							else {
								prev_kdkmk = new String(kdkmk);
								prev_nakmk = new String(nakmk);
								prev_skstm = new String(skstm);
								prev_skspr = new String(skspr);
								prev_skslp = new String(skslp);
								prev_kdwpl = new String(kdwpl);
	        					prev_jenis = new String(jenis);
	        					prev_nodos = new String(nodos);
	        					prev_kdpst = new String(kdpst);
	        					prev_id_jen = new String(id_jen);
	        					prev_id_sms = new String(id_sms);
	        					prev_sksmk = sksmk;
							}	
						}
						li.set(kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp+"`"+kdwpl+"`"+jenis+"`"+nodos+"`"+kdpst+"`"+id_jen+"`"+id_sms+"`"+sksmk);
					}
    			}
    			//System.out.println("v.size="+v.size());
    			
    			
    			
    			//cek apa sudah ada kdkmk & nakmknya di feeder tanpa id_sms
    			li = v.listIterator();
    			stmt = con.prepareStatement("select * from mata_kuliah where kode_mk=? and nm_mk=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String skstm = st.nextToken();
    				String skspr = st.nextToken();
    				String skslp = st.nextToken();
    				String kdwpl = st.nextToken();
    				String jenis = st.nextToken();
    				String nodos = st.nextToken();
    				String kdpst = st.nextToken();
    				String id_jen = st.nextToken();
    				String id_sms = st.nextToken();
    				String sksmk = st.nextToken();
    				stmt.setString(1,kdkmk);
    				stmt.setString(2,nakmk);
    				rs = stmt.executeQuery();
    				
    				if(rs.next()) {
    					int dobel = 0;
    					boolean match = true;
    					while(match) {
    						if(dobel==0) {
    							dobel++;
    							nakmk = nakmk+"["+kdpst+"]";
    						}
    						else {
    							nakmk = nakmk+"["+kdpst+"-"+dobel+++"]";	
    						}
    						
    						stmt.setString(1, kdkmk);
    						stmt.setString(2, nakmk);
    						rs = stmt.executeQuery();
    						if(!rs.next()) {
    							match = false;
    						}
    					}
    					li.set(kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp+"`"+kdwpl+"`"+jenis+"`"+nodos+"`"+kdpst+"`"+id_jen+"`"+id_sms+"`"+sksmk);
    					//System.out.println("dobel="+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp+"`"+kdwpl+"`"+jenis+"`"+nodos+"`"+kdpst+"`"+id_jen+"`"+id_sms+"`"+sksmk);
    				}
    			}
    			
    			
    			int j=0;
    			li = v.listIterator();
    			stmt = con.prepareStatement("INSERT ignore INTO mata_kuliah (id_mk,id_sms,id_jenj_didik,kode_mk,nm_mk,sks_mk,tgl_mulai_efektif,tgl_akhir_efektif,tmp1)values(?,?,?,?,?,?,?,?,?)");
    			while(li.hasNext()) {
    				
    				String brs = (String)li.next();
    				//System.out.println(brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String skstm = st.nextToken();
    				String skspr = st.nextToken();
    				String skslp = st.nextToken();
    				String kdwpl = st.nextToken();
    				String jenis = st.nextToken();
    				String nodos = st.nextToken();
    				String kdpst = st.nextToken();
    				String id_jen = st.nextToken();
    				String id_sms = st.nextToken();
    				String sksmk = st.nextToken();
    				stmt.setString(1, ""+j);
    				stmt.setString(2, id_sms);
    				stmt.setString(3, id_jen);
    				stmt.setString(4, kdkmk);
    				stmt.setString(5, nakmk);
    				stmt.setLong(6, Long.parseLong(sksmk));
    				stmt.setDate(7, java.sql.Date.valueOf("2016-01-02"));
    				stmt.setNull(8, java.sql.Types.DATE);
    				stmt.setString(9, ""+j++);
    				int i = stmt.executeUpdate();
    				if(i<1) {
    					lie.add(brs);
    				}
    			}
    			
    			//update mata_kuliah agar tidak ada penamaan dobel
    			Vector v_up = new Vector();
    			ListIterator liu = v_up.listIterator();
    			
    			stmt = con.prepareStatement("select * from mata_kuliah inner join sms on mata_kuliah.id_sms=sms.id_sms order by kode_mk,nm_mk");
    			rs = stmt.executeQuery();
    			//String prev_id_mk = "";
    			String prev_kode_mk = "";
    			String prev_nm_mk = "";
    			String prev_prodi = "";
    			
    			if(rs.next()) {
    				String id_mk = rs.getString("id_mk");
    				prev_kode_mk = rs.getString("kode_mk");
    				prev_nm_mk = rs.getString("nm_mk");
    				prev_nm_mk = rs.getString("kode_prodi");
    				//liu.add(id_mk+"`"+prev_kode_mk+"`"+prev_nm_mk);
    				while(rs.next()) {
    					int dobel=0;
    					id_mk = rs.getString("id_mk");
        				String kode_mk = rs.getString("kode_mk");
        				String nm_mk = rs.getString("nm_mk");
        				String prodi = rs.getString("kode_prodi");
        				if(kode_mk.equalsIgnoreCase(prev_kode_mk)&&nm_mk.equalsIgnoreCase(prev_nm_mk)) {
        					boolean match = true;
        					//while(match) {
        					//	nm_mk=nm_mk+"["+dobel+++"]";
        					//	if(!nm_mk.equalsIgnoreCase(prev_nm_mk)) {
        					//		match = false;
        					//	}
        					//}
        					/*
        					 * yg diupdate yg dobel saja
        					 */
        					liu.add(id_mk+"`"+kode_mk+"`"+nm_mk+"`"+prodi);
        					//System.out.println("update = "+id_mk+"`"+kode_mk+"`"+nm_mk);
        				}
        				
        				prev_kode_mk = new String(kode_mk);
        				prev_nm_mk = new String(nm_mk);
        				
        				
    				}
    			}
    			
    			liu = v_up.listIterator();
    			if(liu.hasNext()) {
    				int dobel=0;
    				stmt=con.prepareStatement("select * from mata_kuliah where kode_mk=? and nm_mk=?");
    				do {
    					String brs = (String)liu.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String id_mk =st.nextToken();
    					String kode_mk =st.nextToken();
    					String nm_mk =st.nextToken();
    					String prodi =st.nextToken();
    					stmt.setString(1, kode_mk);
    					stmt.setString(2, nm_mk);
    					rs = stmt.executeQuery();
    					
    					if(rs.next()) {
    						boolean match = true;
    						while(match) {
    							if(dobel==0) {
    								dobel++;
    								nm_mk=nm_mk+" ["+prodi+"]";
    							}
    							else {
    								nm_mk=nm_mk+" ["+prodi+"-"+dobel+++"]";
    							}	
    							stmt.setString(1, kode_mk);
    	    					stmt.setString(2, nm_mk);
    	    					rs = stmt.executeQuery();
    	    					if(!rs.next()) {
    	    						match = false;
    	    					}
    						}
    						
    						liu.set(id_mk+"`"+kode_mk+"`"+nm_mk);
    					}
    					//else {
    					dobel=0;
    					//}
    					
    				}
    				while(liu.hasNext());
    				
    			}
    			liu = v_up.listIterator();
    			if(liu.hasNext()) {
    				stmt=con.prepareStatement("update mata_kuliah set nm_mk=? where id_mk=?");
    				do {
    					String brs = (String)liu.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String id_mk =st.nextToken();
    					String kode_mk =st.nextToken();
    					String nm_mk =st.nextToken();
    					stmt.setString(1, nm_mk);
    					stmt.setString(2, id_mk);
    					stmt.executeUpdate();
    				}
    				while(liu.hasNext());
    			}	
    			//INSERT INTO mata_kuliah (id_mk,id_sms,id_jenj_didik,kode_mk,nm_mk,sks_mk,tgl_mulai_efektif,tgl_akhir_efektif)
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
    
    public Vector importMakul_v2() {
    	Vector v_err = new Vector();
    	ListIterator lie = v_err.listIterator();
    	//ListIterator lio = v_out.listIterator();
    	Vector v = getDataMakul();
    	
    	if(v!=null && v.size()>0) {
    		
        	try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc/FEEDER_IMPORTER");
    			con = ds.getConnection();
    			Vector v_tmp = new Vector();
    			ListIterator lit = v_tmp.listIterator();
    			
    			/*
    			 * SINKRON NAMA MK AGAR SEMUA MENGGUNAKAN 1 SPASE AGAR BISA DIGUNAKAN PERBANDINGAN ANTAR MATA KULIAH YG SUDAH ADA DGN
    			 * YG BELUM
    			 */
    			stmt = con.prepareStatement("select * from mata_kuliah");
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String id_mk = rs.getString("id_mk");
        			String nm_mk = rs.getString("nm_mk");
        			lit.add(id_mk+"`"+nm_mk);	
    			}
    			lit = v_tmp.listIterator();
    			stmt = con.prepareStatement("update mata_kuliah set nm_mk=? where id_mk=?");
    			while(lit.hasNext()) {
    				String brs = (String)lit.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String id = st.nextToken();
    				String nakmk = st.nextToken();
    				stmt.setString(1, Tool.buatSatuSpasiAntarKata(nakmk));
    				stmt.setString(2,id);
    				stmt.executeUpdate();
    				
    			}
    			//==================================END SINKRON 1 SPASI==============================
    			

    			//1. TAMBAHKAN INFO id jenjang didik
    			ListIterator li = v.listIterator();
    			stmt = con.prepareStatement("select * from sms where kode_prodi=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String skstm = st.nextToken();
    				String skspr = st.nextToken();
    				String skslp = st.nextToken();
    				String kdwpl = st.nextToken();
    				String jenis = st.nextToken();
    				String nodos = st.nextToken();
    				String kdpst = st.nextToken();
    				stmt.setString(1, kdpst);
    				rs = stmt.executeQuery();
    				rs.next();
    				String id_sms = rs.getString("id_sms");
    				long id_jenj_didik = rs.getLong("id_jenj_didik");
    				li.set(brs+"`"+id_jenj_didik+"`"+id_sms);
    				
    			}
    			Collections.sort(v);
    			
    			Vector v_ins = new Vector();
    			ListIterator li_ins = v_ins.listIterator();
    			li = v.listIterator();
    			String prev_kdkmk = "";
				String prev_nakmk = "";
				String prev_skstm = "";
				String prev_skspr = "";
				String prev_skslp = "";
				String prev_kdwpl = "";
				String prev_jenis = "";
				String prev_nodos = "";
				String prev_kdpst = "";
				String prev_id_jen = "";
				String prev_id_sms = "";
				long prev_sksmk = 0;
				boolean first = true;
    			//filter mk yg sudah ada di feeder
				//NM_MK sudah menngunakan 1 spasi baik di feeder maupun di makul
    			stmt = con.prepareStatement("select * from mata_kuliah where id_sms=? and kode_mk=? and nm_mk=? and sks_mk=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String skstm = st.nextToken();
    				String skspr = st.nextToken();
    				String skslp = st.nextToken();
    				String kdwpl = st.nextToken();
    				String jenis = st.nextToken();
    				String nodos = st.nextToken();
    				String kdpst = st.nextToken();
    				String id_jen = st.nextToken();
    				String id_sms = st.nextToken();
    				long sksmk = Long.parseLong(skstm)+Long.parseLong(skspr)+Long.parseLong(skslp);
    				
    				stmt.setString(1, id_sms);
    				stmt.setString(2, kdkmk);
    				stmt.setString(3, nakmk.toUpperCase());
    				stmt.setLong(4, sksmk);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					li.remove();
    				}
    				else {
						if(first) {
							first = false;
							prev_kdkmk = new String(kdkmk);
							prev_nakmk = new String(nakmk);
							prev_skstm = new String(skstm);
							prev_skspr = new String(skspr);
							prev_skslp = new String(skslp);
							prev_kdwpl = new String(kdwpl);
							prev_jenis = new String(jenis);
							prev_nodos = new String(nodos);
							prev_kdpst = new String(kdpst);
							prev_id_jen = new String(id_jen);
							prev_id_sms = new String(id_sms);
							prev_sksmk = sksmk;
						}
						else {
							int dobel = 0;
							if(kdkmk.equalsIgnoreCase(prev_kdkmk) && nakmk.equalsIgnoreCase(prev_nakmk)) {
							//masalah harus dimodifikasi nakmk
							//revisi ngga perlu dipikirin sekaran, belakangan aja setelah diinsert	
								
								boolean match = true;
								while(match) {
									if(dobel==0) {
										dobel++;
										nakmk = nakmk+" ["+kdpst+"]";
									}
									else {
										nakmk = nakmk+" ["+kdpst+"-"+dobel+++"]";
									}	
									stmt.setString(1, id_sms);
				    				stmt.setString(2, kdkmk);
				    				stmt.setString(3, nakmk.toUpperCase());
				    				stmt.setLong(4, sksmk);
				    				//if(!nakmk.equalsIgnoreCase(prev_nakmk)) {
									if(!rs.next()) {
										match = false;
									}
								}
								prev_kdkmk = new String(kdkmk);
								//prev_nakmk = new String(nakmk); pake nakmk yg sebelumnya saja
								prev_skstm = new String(skstm);
								prev_skspr = new String(skspr);
								prev_skslp = new String(skslp);
								prev_kdwpl = new String(kdwpl);
	        					prev_jenis = new String(jenis);
	        					prev_nodos = new String(nodos);
	        					prev_kdpst = new String(kdpst);
	        					prev_id_jen = new String(id_jen);
	        					prev_id_sms = new String(id_sms);
	        					prev_sksmk = sksmk;
								
							}
							else {
								prev_kdkmk = new String(kdkmk);
								prev_nakmk = new String(nakmk);
								prev_skstm = new String(skstm);
								prev_skspr = new String(skspr);
								prev_skslp = new String(skslp);
								prev_kdwpl = new String(kdwpl);
	        					prev_jenis = new String(jenis);
	        					prev_nodos = new String(nodos);
	        					prev_kdpst = new String(kdpst);
	        					prev_id_jen = new String(id_jen);
	        					prev_id_sms = new String(id_sms);
	        					prev_sksmk = sksmk;
							}	
						}
						li.set(kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp+"`"+kdwpl+"`"+jenis+"`"+nodos+"`"+kdpst+"`"+id_jen+"`"+id_sms+"`"+sksmk);
					}
    			}
    			//System.out.println("v.size="+v.size());
    			
    			
    			
    			//cek apa sudah ada kdkmk & nakmknya di feeder tanpa id_sms
    			li = v.listIterator();
    			stmt = con.prepareStatement("select * from mata_kuliah where kode_mk=? and nm_mk=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String skstm = st.nextToken();
    				String skspr = st.nextToken();
    				String skslp = st.nextToken();
    				String kdwpl = st.nextToken();
    				String jenis = st.nextToken();
    				String nodos = st.nextToken();
    				String kdpst = st.nextToken();
    				String id_jen = st.nextToken();
    				String id_sms = st.nextToken();
    				String sksmk = st.nextToken();
    				stmt.setString(1,kdkmk);
    				stmt.setString(2,nakmk);
    				rs = stmt.executeQuery();
    				
    				if(rs.next()) {
    					int dobel = 0;
    					boolean match = true;
    					while(match) {
    						if(dobel==0) {
    							dobel++;
    							nakmk = nakmk+"["+kdpst+"]";
    						}
    						else {
    							nakmk = nakmk+"["+kdpst+"-"+dobel+++"]";	
    						}
    						
    						stmt.setString(1, kdkmk);
    						stmt.setString(2, nakmk);
    						rs = stmt.executeQuery();
    						if(!rs.next()) {
    							match = false;
    						}
    					}
    					li.set(kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp+"`"+kdwpl+"`"+jenis+"`"+nodos+"`"+kdpst+"`"+id_jen+"`"+id_sms+"`"+sksmk);
    					//System.out.println("dobel="+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp+"`"+kdwpl+"`"+jenis+"`"+nodos+"`"+kdpst+"`"+id_jen+"`"+id_sms+"`"+sksmk);
    				}
    			}
    			
    			
    			int j=0;
    			li = v.listIterator();
    			stmt = con.prepareStatement("INSERT ignore INTO mata_kuliah (id_mk,id_sms,id_jenj_didik,kode_mk,nm_mk,sks_mk,tgl_mulai_efektif,tgl_akhir_efektif,tmp1)values(?,?,?,?,?,?,?,?,?)");
    			while(li.hasNext()) {
    				
    				String brs = (String)li.next();
    				//System.out.println(brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String skstm = st.nextToken();
    				String skspr = st.nextToken();
    				String skslp = st.nextToken();
    				String kdwpl = st.nextToken();
    				String jenis = st.nextToken();
    				String nodos = st.nextToken();
    				String kdpst = st.nextToken();
    				String id_jen = st.nextToken();
    				String id_sms = st.nextToken();
    				String sksmk = st.nextToken();
    				stmt.setString(1, ""+j);
    				stmt.setString(2, id_sms);
    				stmt.setString(3, id_jen);
    				stmt.setString(4, kdkmk);
    				stmt.setString(5, nakmk);
    				stmt.setLong(6, Long.parseLong(sksmk));
    				stmt.setDate(7, java.sql.Date.valueOf("2016-01-02"));
    				stmt.setNull(8, java.sql.Types.DATE);
    				stmt.setString(9, ""+j++);
    				int i = stmt.executeUpdate();
    				if(i<1) {
    					lie.add(brs);
    				}
    			}
    			
    			//update mata_kuliah agar tidak ada penamaan dobel
    			Vector v_up = new Vector();
    			ListIterator liu = v_up.listIterator();
    			
    			stmt = con.prepareStatement("select * from mata_kuliah inner join sms on mata_kuliah.id_sms=sms.id_sms order by kode_mk,nm_mk");
    			rs = stmt.executeQuery();
    			//String prev_id_mk = "";
    			String prev_kode_mk = "";
    			String prev_nm_mk = "";
    			String prev_prodi = "";
    			
    			if(rs.next()) {
    				String id_mk = rs.getString("id_mk");
    				prev_kode_mk = rs.getString("kode_mk");
    				prev_nm_mk = rs.getString("nm_mk");
    				prev_nm_mk = rs.getString("kode_prodi");
    				//liu.add(id_mk+"`"+prev_kode_mk+"`"+prev_nm_mk);
    				while(rs.next()) {
    					int dobel=0;
    					id_mk = rs.getString("id_mk");
        				String kode_mk = rs.getString("kode_mk");
        				String nm_mk = rs.getString("nm_mk");
        				String prodi = rs.getString("kode_prodi");
        				if(kode_mk.equalsIgnoreCase(prev_kode_mk)&&nm_mk.equalsIgnoreCase(prev_nm_mk)) {
        					boolean match = true;
        					//while(match) {
        					//	nm_mk=nm_mk+"["+dobel+++"]";
        					//	if(!nm_mk.equalsIgnoreCase(prev_nm_mk)) {
        					//		match = false;
        					//	}
        					//}
        					/*
        					 * yg diupdate yg dobel saja
        					 */
        					liu.add(id_mk+"`"+kode_mk+"`"+nm_mk+"`"+prodi);
        					//System.out.println("update = "+id_mk+"`"+kode_mk+"`"+nm_mk);
        				}
        				
        				prev_kode_mk = new String(kode_mk);
        				prev_nm_mk = new String(nm_mk);
        				
        				
    				}
    			}
    			
    			liu = v_up.listIterator();
    			if(liu.hasNext()) {
    				int dobel=0;
    				stmt=con.prepareStatement("select * from mata_kuliah where kode_mk=? and nm_mk=?");
    				do {
    					String brs = (String)liu.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String id_mk =st.nextToken();
    					String kode_mk =st.nextToken();
    					String nm_mk =st.nextToken();
    					String prodi =st.nextToken();
    					stmt.setString(1, kode_mk);
    					stmt.setString(2, nm_mk);
    					rs = stmt.executeQuery();
    					
    					if(rs.next()) {
    						boolean match = true;
    						while(match) {
    							if(dobel==0) {
    								dobel++;
    								nm_mk=nm_mk+" ["+prodi+"]";
    							}
    							else {
    								nm_mk=nm_mk+" ["+prodi+"-"+dobel+++"]";
    							}	
    							stmt.setString(1, kode_mk);
    	    					stmt.setString(2, nm_mk);
    	    					rs = stmt.executeQuery();
    	    					if(!rs.next()) {
    	    						match = false;
    	    					}
    						}
    						
    						liu.set(id_mk+"`"+kode_mk+"`"+nm_mk);
    					}
    					//else {
    					dobel=0;
    					//}
    					
    				}
    				while(liu.hasNext());
    				
    			}
    			liu = v_up.listIterator();
    			if(liu.hasNext()) {
    				stmt=con.prepareStatement("update mata_kuliah set nm_mk=? where id_mk=?");
    				do {
    					String brs = (String)liu.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String id_mk =st.nextToken();
    					String kode_mk =st.nextToken();
    					String nm_mk =st.nextToken();
    					stmt.setString(1, nm_mk);
    					stmt.setString(2, id_mk);
    					stmt.executeUpdate();
    				}
    				while(liu.hasNext());
    			}	
    			//INSERT INTO mata_kuliah (id_mk,id_sms,id_jenj_didik,kode_mk,nm_mk,sks_mk,tgl_mulai_efektif,tgl_akhir_efektif)
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

package beans.dbase.trlsm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import beans.dbase.UpdateDb;
import beans.dbase.tbbnl.SearchDbTbbnl;
import beans.dbase.trnlm.SearchDbTrnlm;
import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.Tool;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.EncodingException;

/**
 * Session Bean implementation class UpdateDbTrlsm
 */
@Stateless
@LocalBean
public class UpdateDbTrlsm extends UpdateDb {
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
    public UpdateDbTrlsm() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbTrlsm(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }

    /*
     * DEPRECATED pake yg 4 input var
     */
    public void updStmhs(String kdpst, String npmhs, String[]thsms_stmhs) {
    	int upd = 0;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	Vector v = null;
    	try {
    		if(thsms_stmhs!=null && thsms_stmhs.length>0) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		v = new Vector();
            	li = v.listIterator();
        		stmt = con.prepareStatement("update TRLSM set STMHS=? where THSMS=? and NPMHS=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				//System.out.println("thsms_stmhs["+i+"]="+thsms_stmhs[i]);
        				if(stmhs==null || Checker.isStringNullOrEmpty(stmhs)) {
        					stmt.setNull(1, java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt.setString(1, stmhs);
        				}
        				stmt.setString(2,thsms);
        				stmt.setString(3,npmhs);
        				upd = stmt.executeUpdate();
        				//System.out.println(upd);
        				if(upd<1) {
        					//no prev rec - harus diinsert
        					li.add(thsms_stmhs[i]);
        				}
        			}
        		}
        		//System.out.println("vsizer="+v.size());
        		li = v.listIterator();
        		if(li.hasNext()) {
        			stmt = con.prepareStatement("insert into TRLSM(THSMS,KDPST,NPMHS,STMHS)values(?,?,?,?)");
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				st = new StringTokenizer(brs,"`");
        				//System.out.println("bariss="+brs);
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				stmt.setString(1, thsms);
        				stmt.setString(2, kdpst);
        				stmt.setString(3, npmhs);
        				if(stmhs==null || Checker.isStringNullOrEmpty(stmhs)) {
        					stmt.setNull(4, java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt.setString(4, stmhs);
        				}
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
		catch (Exception ex) {
			ex.printStackTrace();
		} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    }
    
    
    public void setKeluarMhs(Vector v_npmhs, String thsms, String kdpst) {
    	ListIterator li = null;
    	Vector v_ins = null;
    	ListIterator lins = null;
    	if(v_npmhs!=null && v_npmhs.size()>0) {
    		try {
    	    	li = v_npmhs.listIterator();
    	    	Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
    	    	stmt = con.prepareStatement("update TRLSM set STMHS=? where THSMS=? and NPMHS=?");
    	    	while(li.hasNext()) {
    	    		String brs = (String) li.next();
    	    		StringTokenizer st = new StringTokenizer(brs,"`");
    	    		String npmhs = st.nextToken();
    	    		
    	    		stmt.setString(1, "L");
    				stmt.setString(2, thsms);
    				stmt.setString(3, npmhs);
    				int i = stmt.executeUpdate();
    				if(i<1) {
    					if(v_ins==null) {
    						v_ins = new Vector();
    						lins = v_ins.listIterator();
    					}
    					lins.add(brs);
    				}
    	    	}
    	    	if(v_ins!=null&& v_ins.size()>0) {
    	    		
    	    		stmt = con.prepareStatement("insert into TRLSM(THSMS,KDPST,NPMHS,STMHS)values(?,?,?,?)");
        	    	while(li.hasNext()) {
        	    		String brs = (String) li.next();
        	    		StringTokenizer st = new StringTokenizer(brs,"`");
        	    		String npmhs = st.nextToken();
        	    		
        	    		stmt.setString(1, thsms);
        				stmt.setString(2, kdpst);
        				stmt.setString(3, npmhs);
        				stmt.setString(4, "L");
        				stmt.executeUpdate();
    				
    	    	}	
        		//delete TRAKM
        	    li = v_npmhs.listIterator();
        	    stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM=? and NPMHSTRAKM=?");
        	    while(li.hasNext()) {
        	    	String brs = (String) li.next();
        	    	StringTokenizer st = new StringTokenizer(brs,"`");
        	    	String npmhs = st.nextToken();
        	    	stmt.setString(1, thsms);
        	    	stmt.setString(2, npmhs);
        	    	stmt.executeUpdate();
        	    }	
        	    //delete TRNL
        	    li = v_npmhs.listIterator();
        	    stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
        	    while(li.hasNext()) {
        	    	String brs = (String) li.next();
        	    	StringTokenizer st = new StringTokenizer(brs,"`");
        	    	String npmhs = st.nextToken();
        	    	stmt.setString(1, thsms);
        	    	stmt.setString(2, npmhs);
        	    	stmt.executeUpdate();
        	    }
    		}
    	} 
		//catch (NamingException e) {
		//	e.printStackTrace();
		//}
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
    	//return upd;
    }
    
    public void updStmhsPindahProdi(String kdpst_lama, String npmhs_lama, String[]thsms_stmhs, String[] alasan, String npm_prodi_baru, String kdpst_prodi_baru, String kode_univ_asal) {
    	long old_objid = Checker.getObjectId(npmhs_lama); 
    	long nomrut =1+getNoRutTerakhir();
    	String kdjen = Converter.getKdjen(kdpst_lama);
    	java.util.Date date= new java.util.Date();
    	JSONObject jObj = null;
    	String target_shift = null;
    	JSONArray jArray = null;
    	/*
    	try {
    		jArray = Getter.readJsonArrayFromUrl("/v1/mhs/"+npmhs_lama+"/shift");
    		if(jArray!=null && jArray.length()>0) {
        		JSONObject job = jArray.getJSONObject(0);
        		target_shift = job.getString("KETERANGAN");
        		if(target_shift.contains("&#x2f;")) {
        			target_shift = Constants.getDefaultShift();
        		}
        	}
        	else {
        		target_shift = Constants.getDefaultShift();
        	}
    		//target_shift = target_shift.replace("&#x2f;", "/");
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    	catch(JSONException e) {
    		e.printStackTrace();
    	}
    	*/
    	try {
    		int ins = 0;
    		target_shift = Constants.getDefaultShift();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		
    		//update status pad npm baru
    		stmt = con.prepareStatement("update CIVITAS set ASNIMMSMHS=?,ASPTIMSMHS=?,ASPSTMSMHS=?,STPIDMSMHS=? where NPMHSMSMHS=?");
    		stmt.setString(1, npmhs_lama);
    		stmt.setString(2, kode_univ_asal);
    		stmt.setString(3, kdpst_lama);
    		stmt.setString(4, "P");
    		stmt.setString(5, npm_prodi_baru);
    		//System.out.println(npmhs_lama+"-"+kode_univ_asal+"-"+kdpst_lama+"-"+npm_prodi_baru);
    		ins = stmt.executeUpdate();
    		//System.out.println("ins = "+ins);
    		ins=0;
    		//2. ubah status npm lama = keluar
    		updStmhs(kdpst_lama, npmhs_lama, thsms_stmhs,alasan);
    		//3. set status aktif pada prodi baru - tidak jadi aktif hanya bila da krs
			//4. copy pembayaran dari yg lama ke npm baru
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from PYMNT where NPMHSPYMNT=? and VOIDDPYMNT=?");
    		stmt.setString(1, npmhs_lama);
    		stmt.setBoolean(2, false);
    		rs = stmt.executeQuery();
    		Vector v = new Vector();
    		ListIterator li = v.listIterator();
    		double tot = 0;
    		while(rs.next()) {
    			String kuiid =""+rs.getLong("KUIIDPYMNT");
    			//System.out.println("1");
    			String kdpst =""+rs.getString("KDPSTPYMNT");
    			//System.out.println("2");
    			String npmhs =""+rs.getString("NPMHSPYMNT");
    			//System.out.println("3");
    			String norut =""+rs.getLong("NORUTPYMNT");
    			//System.out.println("4");
    			String tgkui =""+rs.getDate("TGKUIPYMNT");
    			//System.out.println("5");
    			String tgtrs =""+rs.getDate("TGTRSPYMNT");
    			//System.out.println("6");
    			String keter =""+rs.getString("KETERPYMNT");
    			//System.out.println("7");
    			String detil =""+rs.getString("KETER_PYMNT_DETAIL");
    			//System.out.println("8");
    			String payee =""+rs.getString("PAYEEPYMNT");
    			//System.out.println("9");
    			String amont =""+rs.getDouble("AMONTPYMNT");
    			//System.out.println("10");
    			tot = tot+Double.parseDouble(amont);
    			//System.out.println("11");
    			String pmntp =""+rs.getString("PYMTPYMNT");
    			//System.out.println("12");
    			String gelom =""+rs.getInt("GELOMBANG");
    			//System.out.println("13");
    			String cicil =""+rs.getInt("CICILAN");
    			//System.out.println("14");
    			String krs =""+rs.getInt("KRS");
    			//System.out.println("15");
    			String sms =""+rs.getInt("SMS");
    			//System.out.println("16");
    			String noacc =""+rs.getString("NOACCPYMNT");
    			//System.out.println("17");
    			String opnpm =""+rs.getString("OPNPMPYMNT");
    			//System.out.println("18");
    			String opnmm =""+rs.getString("OPNMMPYMNT");
    			//System.out.println("19");
    			String setor =""+rs.getBoolean("SETORPYMNT");
    			//System.out.println("20");
    			String nonpm =""+rs.getString("NONPMPYMNT");
    			//System.out.println("21");
    			String voidp =""+rs.getBoolean("VOIDDPYMNT");
    			//System.out.println("22");
    			String nokod =""+rs.getString("NOKODPYMNT");
    			//System.out.println("23");
    			String updtm =""+rs.getTimestamp("UPDTMPYMNT");
    			//System.out.println("24");
    			String voidnpm =""+rs.getString("VOIDOPNPM");
    			//System.out.println("25");
    			String voidktr =""+rs.getString("VOIDKETER");
    			//System.out.println("26");
    			String voidnmm =""+rs.getString("VOIDOPNMM");
    			//System.out.println("27");
    			String filenm =""+rs.getString("FILENAME");
    			//System.out.println("28");
    			String upldtm =""+rs.getTimestamp("UPLOADTM");
    			//System.out.println("29");
    			String aprtm =""+rs.getTimestamp("APROVALTM");
    			//System.out.println("30");
    			String rejtm =""+rs.getTimestamp("REJECTTM");
    			//System.out.println("31");
    			String rejnt =""+rs.getString("REJECTION_NOTE");
    			//System.out.println("32");
    			String npmapr =""+rs.getString("NPM_APPROVEE");
    			//System.out.println("33");
    			String group =""+rs.getLong("GROUP_ID");
    			//System.out.println("34");
    			String idbea =""+rs.getLong("IDPAKETBEASISWA");
    			//System.out.println("35");
    			String idobj =""+rs.getLong("ID_OBJ");
    			//System.out.println("36");
    			String kdkmp =""+rs.getString("KODE_KAMPUS_DOMISILI");
    			//System.out.println("37");
    			String asal_npm =""+rs.getString("ASAL_NPM_TRANSAKSI");
    			//System.out.println("38");
    			String mertm =null;
    			//System.out.println("39");
    			//System.out.println("mertm="+mertm);
    			//System.out.println("40");
    			li.add(kuiid+"`"+kdpst+"`"+npmhs+"`"+norut+"`"+tgkui+"`"+tgtrs+"`"+keter+"`"+detil+"`"+payee+"`"+amont+"`"+pmntp+"`"+gelom+"`"+cicil+"`"+krs+"`"+sms+"`"+noacc+"`"+opnpm+"`"+opnmm+"`"+setor+"`"+nonpm+"`"+voidp+"`"+nokod+"`"+updtm+"`"+voidnpm+"`"+voidktr+"`"+voidnmm+"`"+filenm+"`"+upldtm+"`"+aprtm+"`"+rejtm+"`"+rejnt+"`"+npmapr+"`"+group+"`"+idbea+"`"+idobj+"`"+kdkmp+"`"+asal_npm+"`null");
    		}
    		if(tot>0) {
    			//System.out.println("kdjen="+kdjen);
    			String targetAkunTunai = null;
    			stmt = con.prepareStatement("select NAMA_AKUN from AKUN where NAMA_BANK='TUNAI' and TKN_KDJEN_AVAILIBILITY LIKE ?");
    			stmt.setString(1, "%"+kdjen+"%");
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				targetAkunTunai = new String(rs.getString(1));
    			}
    		//5.ionsert negatig tot agar balance
    			//diambil dari UpdateDbKeu.insertCashPymntForm1Tunai
        		stmt = con.prepareStatement("select KODE_KAMPUS_DOMISILI  from OBJECT where ID_OBJ=?");
        		stmt.setLong(1,old_objid);
        		rs = stmt.executeQuery();
        		rs.next();
        		String kode_kampus = ""+rs.getString("KODE_KAMPUS_DOMISILI");
        		stmt = con.prepareStatement("insert into PYMNT(KDPSTPYMNT,NPMHSPYMNT,NORUTPYMNT,TGKUIPYMNT,TGTRSPYMNT,PAYEEPYMNT,OPNPMPYMNT,OPNMMPYMNT,SETORPYMNT,NONPMPYMNT,NOKODPYMNT,UPDTMPYMNT,FILENAME,UPLOADTM,GROUP_ID,KETER_PYMNT_DETAIL,AMONTPYMNT,PYMTPYMNT,GELOMBANG,CICILAN,KRS,SMS,NOACCPYMNT,ID_OBJ,KODE_KAMPUS_DOMISILI) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        		int i=1;
        		//KUIIDPYMNT,
        		//stmt.setString(i++,kdpst);//1
        		//KDPSTPYMNT-fix
            	stmt.setString(i++,kdpst_lama);//2
            	//NPMHSPYMNT-fix
            	stmt.setString(i++,npmhs_lama);//3
            	//NORUTPYMNT-fix
            	stmt.setLong(i++,nomrut);//4
            	//TGKUIPYMNT-fix
            	date = new java.util.Date();
            	java.sql.Date todayDate = new java.sql.Date( date.getTime() );
            	String nokod = null;
            	String tmp = ""+todayDate;
        		StringTokenizer st = new StringTokenizer(tmp);
        		while(st.hasMoreTokens()) {
        			tmp = st.nextToken();
        		}
        		tmp = tmp.replaceAll(":","");
        		tmp = tmp.replaceAll("\\.","");
        		nokod=""+tmp;
        		stmt.setDate(i++, todayDate);//5
            	stmt.setDate(i++, todayDate);//6
            	/*TGTRSPYMNT-fix
            	try {
            		java.sql.Date trsdt = java.sql.Date.valueOf(tglTransCash);
            		stmt.setDate(i++,trsdt);//6
            	}
            	catch(Exception e) {
            		stmt.setDate(i++, todayDate);//6
            	}
            	*/
            	//PAYEEPYMNT
            	stmt.setString(i++, this.operatorNmm);//7
            	//OPNPMPYMNT
            	stmt.setString(i++,this.operatorNpm);//8
            	//OPNMMPYMNT
            	stmt.setString(i++,this.operatorNmm);//9
            	//SETORPYMNT krn ini bayaran cash jadi always blum ke setor
            	//try {
            		//java.sql.Date trsdt = java.sql.Date.valueOf(tglTransCash);
            		//stmt.setBoolean(i++,true);//10
            	//}
            	//catch(Exception e) {
            	stmt.setBoolean(i++, false);//10
            	//}
            	//NONPMPYMNT-ngga tau kolom ini mo dipake utk apa??
            	stmt.setNull(i++,java.sql.Types.VARCHAR);//11
            	//NOKODPYMNT
            	stmt.setString(i++,nokod);//12
            	//UPDTMPYMNT
            	stmt.setTimestamp(i++, AskSystem.getCurrentTimestamp());//13
            	//FILENAME
            	stmt.setNull(i++,java.sql.Types.VARCHAR);//14
            	//UPLOADTM
            	stmt.setTimestamp(i++, AskSystem.getCurrentTimestamp());//15
            	//GROUP_ID,
            	//if(groupMode) {
            	//	stmt.setLong(i++,lastGroupId+1);//16
            	//}
            	//else {
            	stmt.setNull(i++, java.sql.Types.INTEGER);
            	//}
            	/*
            	try {
            		//http://localhost:8080/com.otaku.rest/api/v1/akun/getAkunTunai/B
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getAkunTunai/"+kdjen);
            	    jObj = jArray.getJSONObject(0);	
            	    targetAkunTunai = ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN"));//24
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            	*/
            	int j = i;
            	stmt.setString(j++,"Pindah Prodi");//17
            	//AMONTPYMNT,-iter
            	stmt.setDouble(j++, -tot);//18
            	//PYMTPYMNT,-iter
            	stmt.setString(j++,"N/A");//19
            	//GELOMBANG-,-iter
            	stmt.setInt(j++,0);//20
            	//CICILAN-,-iter
            	stmt.setInt(j++,0);//21
            	//KRS-,iter
            	stmt.setInt(j++,0);//22
            	//SMS
            	stmt.setInt(j++,0);//23
            	
            	//NOACCPYMNT,-iter
            	//System.out.println("targetAkunTunai="+targetAkunTunai);
            	stmt.setString(j++,targetAkunTunai);//24
            	//id obj akun terbayar
            	stmt.setLong(j++,old_objid);//25
            	//kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
            	ins = stmt.executeUpdate();
            	//System.out.println("ins = "+ins);
        		ins=0;
    		}
			//6. copy pembayaran dari yg lama ke npm baru
    		if(v!=null && v.size()>0) {
    			li = v.listIterator();
    			String cmd = "insert into PYMNT(KDPSTPYMNT,NPMHSPYMNT,NORUTPYMNT,TGKUIPYMNT,TGTRSPYMNT,KETERPYMNT,KETER_PYMNT_DETAIL,PAYEEPYMNT,AMONTPYMNT,PYMTPYMNT,GELOMBANG,CICILAN,KRS,SMS,NOACCPYMNT,OPNPMPYMNT,OPNMMPYMNT,SETORPYMNT,NONPMPYMNT,VOIDDPYMNT,NOKODPYMNT,VOIDOPNPM,VOIDKETER,VOIDOPNMM,FILENAME,UPLOADTM,APROVALTM,REJECTTM,REJECTION_NOTE,NPM_APPROVEE,GROUP_ID,IDPAKETBEASISWA,ID_OBJ,KODE_KAMPUS_DOMISILI,ASAL_NPM_TRANSAKSI,MERGE_TIME)values("
    					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    			
    			stmt = con.prepareStatement(cmd);
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kuiid = st.nextToken();
    				String kdpst = st.nextToken();
    				String npmhs = st.nextToken();
    				String norut = st.nextToken();
    				String tgkui = st.nextToken();
    				String tgtrs = st.nextToken();
    				String keter = st.nextToken();
    				String detil = st.nextToken();
    				String payee = st.nextToken();
    				String amont = st.nextToken();
    				String pmntp = st.nextToken();
    				String gelom = st.nextToken();
    				String cicil = st.nextToken();
    				String krs = st.nextToken();
    				String sms = st.nextToken();
    				String noacc = st.nextToken();
    				String opnpm = st.nextToken();
    				String opnmm = st.nextToken();
    				String setor = st.nextToken();
    				String nonpm = st.nextToken();
    				String voidp = st.nextToken();
    				String nokod = st.nextToken();
    				String updtm = st.nextToken();
    				String voidnpm = st.nextToken();
    				String voidktr = st.nextToken();
    				String voidnmm = st.nextToken();
    				String filenm = st.nextToken();
    				String upldtm = st.nextToken();
    				String aprtm = st.nextToken();
    				String rejtm = st.nextToken();
    				String rejnt = st.nextToken();
    				String npmapr = st.nextToken();
    				String group = st.nextToken();
    				String idbea = st.nextToken();
    				String idobj = st.nextToken();
    				String kdkmp = st.nextToken();
    				String asal_npm = st.nextToken();
    				String mertm = st.nextToken();
    				int i = 1;
    				//KDPSTPYMNT,
    				stmt.setString(i++, kdpst_prodi_baru);
    				//NPMHSPYMNT
    				stmt.setString(i++, npm_prodi_baru);
    				//,NORUTPYMNT,
    				stmt.setLong(i++,++nomrut);
    				//TGKUIPYMNT,
    				stmt.setDate(i++,java.sql.Date.valueOf(tgkui));
    				//TGTRSPYMNT,
    				stmt.setDate(i++,java.sql.Date.valueOf(tgtrs));
    				//KETERPYMNT,KETER_PYMNT_DETAIL,PAYEEPYMNT,AMONTPYMNT,PYMTPYMNT,GELOMBANG,CICILAN,KRS,SMS,NOACCPYMNT,OPNPMPYMNT,OPNMMPYMNT,SETORPYMNT,NONPMPYMNT,VOIDDPYMNT,NOKODPYMNT,VOIDOPNPM,VOIDKETER,VOIDOPNMM,FILENAME,UPLOADTM,APROVALTM,REJECTTM,REJECTION_NOTE,NPM_APPROVEE,GROUP_ID,IDPAKETBEASISWA,ID_OBJ,KODE_KAMPUS_DOMISILI,ASAL_NPM_TRANSAKSI,MERGE_TIME)values("
    				if(keter==null || Checker.isStringNullOrEmpty(keter)) {
    					stmt.setNull(i++, java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(i++, keter);	
    				}
    				//KETER_PYMNT_DETAIL,
    	    		if(detil==null || Checker.isStringNullOrEmpty(detil)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, detil);	
    	    		}
    	    		//PAYEEPYMNT,
    	    		if(payee==null || Checker.isStringNullOrEmpty(payee)) {
    	    			stmt.setString(i++, "N/A");	
    	    		}
    	    		else {
    	    			stmt.setString(i++, payee);	
    	    		}	
    	    		//AMONTPYMNT,
    	    		stmt.setDouble(i++, Double.parseDouble(amont));
    	    		//PYMTPYMNT,
    	    		if(pmntp==null || Checker.isStringNullOrEmpty(pmntp)) {
    	    			stmt.setString(i++, "N/A");	
    	    		}
    	    		else {
    	    			stmt.setString(i++, pmntp);	
    	    		}
//					GELOMBANG,
    	    		if(gelom==null || Checker.isStringNullOrEmpty(gelom)) {
    	    			stmt.setNull(i++, java.sql.Types.INTEGER);
    	    		}
    	    		else {
    	    			stmt.setInt(i++, Integer.parseInt(gelom));
    	    		}
    				
    				//CICILAN,
    	    		if(cicil==null || Checker.isStringNullOrEmpty(cicil)) {
    	    			stmt.setNull(i++, java.sql.Types.INTEGER);
    	    		}
    	    		else {
    	    			stmt.setInt(i++, Integer.parseInt(cicil));
    	    		}
    				
    				//KRS,
    				if(krs==null || Checker.isStringNullOrEmpty(krs)) {
    	    			stmt.setNull(i++, java.sql.Types.INTEGER);
    	    		}
    	    		else {
    	    			stmt.setInt(i++, Integer.parseInt(krs));
    	    		}
    				
    				//SMS,
    				if(sms==null || Checker.isStringNullOrEmpty(sms)) {
    	    			stmt.setNull(i++, java.sql.Types.INTEGER);
    	    		}
    	    		else {
    	    			stmt.setInt(i++, Integer.parseInt(sms));
    	    		}
    				
    				//NOACCPYMNT,
       	    		if(noacc==null || Checker.isStringNullOrEmpty(noacc)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, noacc);	
    	    		}	
       	    		//OPNPMPYMNT,OPNMMPYMNT,SETORPYMNT,NONPMPYMNT,VOIDDPYMNT,NOKODPYMNT,VOIDOPNPM,VOIDKETER,VOIDOPNMM,FILENAME,UPLOADTM,APROVALTM,REJECTTM,REJECTION_NOTE,NPM_APPROVEE,GROUP_ID,IDPAKETBEASISWA,ID_OBJ,KODE_KAMPUS_DOMISILI,ASAL_NPM_TRANSAKSI,MERGE_TIME)values("
       	    		if(opnpm==null || Checker.isStringNullOrEmpty(opnpm)) {
       	    			stmt.setString(i++, "N/A");	
    	    		}
    	    		else {
    	    			stmt.setString(i++, opnpm);	
    	    		}
       	    		//OPNMMPYMNT,
       	    		if(opnmm==null || Checker.isStringNullOrEmpty(opnmm)) {
       	    			stmt.setString(i++, "N/A");	
    	    		}
    	    		else {
    	    			stmt.setString(i++, opnmm);	
    	    		}
       	    		//SETORPYMNT,
    				stmt.setBoolean(i++, Boolean.parseBoolean(setor));
    				//NONPMPYMNT
    				if(nonpm==null || Checker.isStringNullOrEmpty(nonpm)) {
    					stmt.setString(i++, "N/A");	
    	    		}
    	    		else {
    	    			stmt.setString(i++, nonpm);	
    	    		}
    				//voiddprmnt
    				stmt.setBoolean(i++, Boolean.parseBoolean(voidp));
    				//,NOKODPYMNT,
    				if(nokod==null || Checker.isStringNullOrEmpty(nokod)) {
    					stmt.setString(i++, "N/A");	
    	    		}
    	    		else {
    	    			stmt.setString(i++, nokod);	
    	    		}
    				//VOIDOPNPM,
    				if(voidnpm==null || Checker.isStringNullOrEmpty(voidnpm)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, voidnpm);	
    	    		}
    				//VOIDKETER,
    				if(voidktr==null || Checker.isStringNullOrEmpty(voidktr)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, voidktr);	
    	    		}
    				//VOIDOPNMM,
    				if(voidnmm==null || Checker.isStringNullOrEmpty(voidnmm)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, voidnmm);	
    	    		}
    				//FILENAME,
    				if(filenm==null || Checker.isStringNullOrEmpty(filenm)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, filenm);	
    	    		}
    				//UPLOADTM,
    				if(upldtm==null || Checker.isStringNullOrEmpty(upldtm)) {
    	    			stmt.setNull(i++, java.sql.Types.TIMESTAMP);
    	    		}
    	    		else {
    	    			stmt.setTimestamp(i++, Timestamp.valueOf(upldtm));	
    	    		}
    				//APROVALTM,REJECTTM,REJECTION_NOTE,NPM_APPROVEE,GROUP_ID,IDPAKETBEASISWA,ID_OBJ,KODE_KAMPUS_DOMISILI,ASAL_NPM_TRANSAKSI,MERGE_TIME)values("
    				if(aprtm==null || Checker.isStringNullOrEmpty(aprtm)) {
    	    			stmt.setNull(i++, java.sql.Types.TIMESTAMP);
    	    		}
    	    		else {
    	    			stmt.setTimestamp(i++, Timestamp.valueOf(aprtm));	
    	    		}
    				//REJECTTM,
    				if(rejtm==null || Checker.isStringNullOrEmpty(rejtm)) {
    	    			stmt.setNull(i++, java.sql.Types.TIMESTAMP);
    	    		}
    	    		else {
    	    			stmt.setTimestamp(i++, Timestamp.valueOf(rejtm));	
    	    		}
    				//REJECTION_NOTE,NPM_APPROVEE,GROUP_ID,IDPAKETBEASISWA,ID_OBJ,KODE_KAMPUS_DOMISILI,ASAL_NPM_TRANSAKSI,MERGE_TIME)values("
    				if(rejnt==null || Checker.isStringNullOrEmpty(rejnt)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, rejnt);	
    	    		}
    				//NPM_APPROVEE,GROUP_ID,IDPAKETBEASISWA,ID_OBJ,KODE_KAMPUS_DOMISILI,ASAL_NPM_TRANSAKSI,MERGE_TIME)values("
    				if(npmapr==null || Checker.isStringNullOrEmpty(npmapr)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, npmapr);	
    	    		}
    				//GROUP_ID,IDPAKETBEASISWA,ID_OBJ,KODE_KAMPUS_DOMISILI,ASAL_NPM_TRANSAKSI,MERGE_TIME)values("
    				if(group==null || Checker.isStringNullOrEmpty(group)) {
    	    			stmt.setNull(i++, java.sql.Types.INTEGER);
    	    		}
    	    		else {
    	    			stmt.setLong(i++, Long.parseLong(group));	
    	    		}
    				//IDPAKETBEASISWA,
    				if(idbea==null || Checker.isStringNullOrEmpty(idbea)) {
    	    			stmt.setNull(i++, java.sql.Types.INTEGER);
    	    		}
    	    		else {
    	    			stmt.setLong(i++, Long.parseLong(idbea));	
    	    		}
    				//ID_OBJ,
    				if(idobj==null || Checker.isStringNullOrEmpty(idobj)) {
    	    			stmt.setNull(i++, java.sql.Types.INTEGER);
    	    		}
    	    		else {
    	    			stmt.setLong(i++, Long.parseLong(idobj));	
    	    		}
    				//KODE_KAMPUS_DOMISILI,
    				if(kdkmp==null || Checker.isStringNullOrEmpty(kdkmp)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, kdkmp);	
    	    		}
    				//ASAL_NPM_TRANSAKSI,
    				if(asal_npm==null || Checker.isStringNullOrEmpty(asal_npm)) {
    	    			stmt.setNull(i++, java.sql.Types.VARCHAR);
    	    		}
    	    		else {
    	    			stmt.setString(i++, asal_npm);	
    	    		}
    				//MERGE_TIME)values("
    				if(mertm==null || Checker.isStringNullOrEmpty(mertm)) {
    	    			stmt.setNull(i++, java.sql.Types.TIMESTAMP);
    	    		}
    	    		else {
    	    			stmt.setTimestamp(i++, Timestamp.valueOf(mertm));	
    	    		}
    				ins = stmt.executeUpdate();
    				//System.out.println("insert = "+ins);
    	    		ins=0;
    			}	
    				
    		}
			//7. change transksi at pymnt_transit ke npm baru
    		//System.out.println("kdpst_prodi_baru="+kdpst_prodi_baru);
    		//System.out.println("npm_prodi_baru="+npm_prodi_baru);
    		//System.out.println("npmhs_lama="+npmhs_lama);
    		stmt = con.prepareStatement("update PYMNT_TRANSIT set KDPSTPYMNT=?,NPMHSPYMNT=? where NPMHSPYMNT=?");
    		stmt.setString(1, kdpst_prodi_baru);
    		stmt.setString(2, npm_prodi_baru);
    		stmt.setString(3, npmhs_lama);
    		stmt.executeUpdate();
    		//6. update password - pindahkan password lama ke yg baru
    		stmt = con.prepareStatement("select ID from CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1, npmhs_lama);
    		rs = stmt.executeQuery();
    		String id = null;
    		if(rs.next()) {
    			id = ""+rs.getLong("ID");
    		}
    		else {
    			//ignore blum pernah dikasih pwd
    		}
    		//System.out.println("ganti pwd1="+id);
    		if(id==null || Checker.isStringNullOrEmpty(id)) {
    			//ignore blum pernah dikasih pwd
    		}
    		else {
    			//System.out.println("ganti pwd2="+id);
    			stmt=con.prepareStatement("update CIVITAS set ID=? where NPMHSMSMHS=?");
    			stmt.setNull(1,java.sql.Types.INTEGER);
    			stmt.setString(2, npmhs_lama);
    			stmt.executeUpdate();
    			//pindah ke npm baru
    			stmt.setLong(1, Long.parseLong(id));
    			stmt.setString(2, npm_prodi_baru);
    			stmt.executeUpdate();
    		}
    	} 
		//catch (NamingException e) {
		//	e.printStackTrace();
		//}
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
    
    /*
     * fungsi ini tidak untuk stmhs A
     */
    public void updStmhs(String kdpst, String npmhs, String[]thsms_stmhs, String[] alasan) {
    	int upd = 0;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	Vector v = null;
    	try {
    		if(thsms_stmhs!=null && thsms_stmhs.length>0) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		v = new Vector();
            	li = v.listIterator();
        		stmt = con.prepareStatement("update TRLSM set STMHS=?,NOTE=? where THSMS=? and NPMHS=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				//System.out.println("thsms_stmhs[i]="+thsms_stmhs[i]);
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				if(stmhs!=null && !Checker.isStringNullOrEmpty(stmhs) && !stmhs.equalsIgnoreCase("A")) {
        					//System.out.println("thsms_stmhs["+i+"]="+thsms_stmhs[i]);
            				if(stmhs==null || Checker.isStringNullOrEmpty(stmhs)) {
            					stmt.setNull(1, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(1, stmhs);
            				}
            				
            				if(alasan==null || alasan[i]==null || Checker.isStringNullOrEmpty(alasan[i])) {
            					stmt.setNull(2, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(2, alasan[i]);
            				}
            				
            				stmt.setString(3,thsms);
            				stmt.setString(4,npmhs);
            				upd = stmt.executeUpdate();
            				//System.out.println(upd);
            				if(upd<1) {
            					//no prev rec - harus diinsert
            					li.add(thsms_stmhs[i]+"`"+alasan[i]);
            				}
        				}
        				
        			}
        		}
        		//System.out.println("vsizer="+v.size());
        		li = v.listIterator();
        		if(li.hasNext()) {
        			stmt = con.prepareStatement("insert into TRLSM(THSMS,KDPST,NPMHS,STMHS,NOTE)values(?,?,?,?,?)");
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				st = new StringTokenizer(brs,"`");
        				//System.out.println("bariss="+brs);
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				String note = st.nextToken();
        				stmt.setString(1, thsms);
        				stmt.setString(2, kdpst);
        				stmt.setString(3, npmhs);
        				if(stmhs==null || Checker.isStringNullOrEmpty(stmhs)) {
        					stmt.setNull(4, java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt.setString(4, stmhs);
        				}
        				if(note==null || Checker.isStringNullOrEmpty(note)) {
        					stmt.setNull(5, java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt.setString(5, note);
        				}
        				stmt.executeUpdate();
        			}
        		}
        		
        		//updaate trnlm
        		//kalo cuti - hanya thsms terkait
        		stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				if(stmhs!=null && (stmhs.equalsIgnoreCase("C")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D")||stmhs.equalsIgnoreCase("P"))) {
        					stmt.setString(1, thsms);
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        				}
        			}
        		}
        		//jika Keluar,DO,L
        		stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM>? and NPMHSTRNLM=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				if(stmhs!=null && (stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D")||stmhs.equalsIgnoreCase("L")||stmhs.equalsIgnoreCase("P"))) {
        					stmt.setString(1, thsms);
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        				}
        			}
        		}
        		//updaate trakm 
        		stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM=? and NPMHSTRAKM=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				if(stmhs!=null && (stmhs.equalsIgnoreCase("C")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D")||stmhs.equalsIgnoreCase("P"))) {
        					stmt.setString(1, thsms);
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        				}
        			}
        		}
        		stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM>? and NPMHSTRAKM=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				if(stmhs!=null && (stmhs.equalsIgnoreCase("L")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D")||stmhs.equalsIgnoreCase("P"))) {
        					stmt.setString(1, thsms);
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        				}
        			}
        		}
        		//update krs notification
        		stmt = con.prepareStatement("delete from KRS_NOTIFICATION where THSMS_TARGET=? and NPM_SENDER=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				if(stmhs!=null && (stmhs.equalsIgnoreCase("C")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D")||stmhs.equalsIgnoreCase("P"))) {
        					stmt.setString(1, thsms);
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        				}
        			}
        		}
        		stmt = con.prepareStatement("delete from KRS_NOTIFICATION where THSMS_TARGET>? and NPM_SENDER=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				if(stmhs!=null && (stmhs.equalsIgnoreCase("L")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D")||stmhs.equalsIgnoreCase("P"))) {
        					stmt.setString(1, thsms);
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        				}
        			}
        		}
        		
        		//update daftar ulang
        		stmt = con.prepareStatement("delete from DAFTAR_ULANG where THSMS=? and NPMHS=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				if(stmhs!=null && (stmhs.equalsIgnoreCase("C")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D")||stmhs.equalsIgnoreCase("P"))) {
        					stmt.setString(1, thsms);
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        				}
        			}
        		}
        		stmt = con.prepareStatement("delete from DAFTAR_ULANG where THSMS>? and NPMHS=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				if(stmhs!=null && (stmhs.equalsIgnoreCase("L")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D")||stmhs.equalsIgnoreCase("P"))) {
        					stmt.setString(1, thsms);
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        				}
        			}
        		}
        		//update topik pengajuan (batalakan yg bukan sesuai dgn pengajuan = takut ada pengajuan laen)
        		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set BATAL=?,LOCKED=?,SHOW_AT_TARGET=?,SHOW_AT_CREATOR=? where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=? and TIPE_PENGAJUAN<>?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				//if(stmhs!=null && (stmhs.equalsIgnoreCase("C")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D")||stmhs.equalsIgnoreCase("L"))) {
        				if(stmhs!=null && (stmhs.equalsIgnoreCase("C")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D")||stmhs.equalsIgnoreCase("L")||stmhs.equalsIgnoreCase("P"))) {
        					stmt.setBoolean(1, true);
        					stmt.setBoolean(2, true);
        					stmt.setNull(3, java.sql.Types.VARCHAR);
        					stmt.setBoolean(4, false);
        					stmt.setString(5, thsms);
        					stmt.setString(6, npmhs);
        					if(stmhs.equalsIgnoreCase("C")) {
        						stmt.setString(7, "CUTI");	
        					}
        					else if(stmhs.equalsIgnoreCase("K")) {
        						stmt.setString(7, "KELUAR");
        					}
        					else if(stmhs.equalsIgnoreCase("D")) {
        						stmt.setString(7, "DO");
        					}
        					else if(stmhs.equalsIgnoreCase("L")) {
        						stmt.setString(7, "KELULUSAN");
        					}
        					else if(stmhs.equalsIgnoreCase("P")) {
        						stmt.setString(7, "PINDAH_PRODI");
        					}
        					
        					stmt.executeUpdate();
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
		catch (Exception ex) {
			ex.printStackTrace();
		} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    }
    
    
    public void updStmhsLulusan(String kdpst, String npmhs, String[]thsms_stmhs, String[] tglls) {
    	/*
    	 * HARUS SAMA STEPS PROSESNYA DENGAN fn forcedInputLulusanViaExcel 
    	 */
    	int upd = 0;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	Vector v = null;
    	try {
    		if(thsms_stmhs!=null && thsms_stmhs.length>0) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		v = new Vector();
            	li = v.listIterator();
        		stmt = con.prepareStatement("update TRLSM set STMHS=?,NOTE=?,TGLLS=? where THSMS=? and NPMHS=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				//System.out.println("thsms_stmhs[i]="+thsms_stmhs[i]);
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				if(stmhs!=null && !Checker.isStringNullOrEmpty(stmhs) && !stmhs.equalsIgnoreCase("A")) {
        					//System.out.println("thsms_stmhs["+i+"]="+thsms_stmhs[i]);
            				if(stmhs==null || Checker.isStringNullOrEmpty(stmhs)) {
            					stmt.setNull(1, java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(1, stmhs);
            				}
            				stmt.setNull(2, java.sql.Types.VARCHAR);
            				stmt.setDate(3, Converter.formatDateBeforeInsert(tglls[i]));
            				
            				
            				stmt.setString(4,thsms);
            				stmt.setString(5,npmhs);
            				upd = stmt.executeUpdate();
            				//System.out.println(upd);
            				if(upd<1) {
            					//no prev rec - harus diinsert
            					li.add(thsms_stmhs[i]+"`"+tglls[i]);
            				}
        				}
        				
        			}
        		}
        		//System.out.println("vsizer="+v.size());
        		li = v.listIterator();
        		if(li.hasNext()) {
        			stmt = con.prepareStatement("insert into TRLSM(THSMS,KDPST,NPMHS,STMHS,TGLLS)values(?,?,?,?,?)");
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				st = new StringTokenizer(brs,"`");
        				//System.out.println("bariss="+brs);
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				String str_tglls = st.nextToken();
        				stmt.setString(1, thsms);
        				stmt.setString(2, kdpst);
        				stmt.setString(3, npmhs);
        				if(stmhs==null || Checker.isStringNullOrEmpty(stmhs)) {
        					stmt.setNull(4, java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt.setString(4, stmhs);
        				}
        				stmt.setDate(5, Converter.formatDateBeforeInsert(str_tglls));
        				
        				stmt.executeUpdate();
        			}
        		}
        		
        		//updaate trnlm 
        		stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM>? and NPMHSTRNLM=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				if(stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D")) {
        					thsms = Tool.returnNextThsmsGivenTpAntara(thsms);
        				}
        				stmt.setString(1, thsms);
        				stmt.setString(2, npmhs);
        				stmt.executeUpdate();
        			}
        		}	
        		//updaate trakm 
        		stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM>? and NPMHSTRAKM=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				stmt.setString(1, thsms);
        				stmt.setString(2, npmhs);
        				stmt.executeUpdate();	
        			}
        		}
        		
        		//update krs notification
        		stmt = con.prepareStatement("delete from KRS_NOTIFICATION where THSMS_TARGET=? and NPM_SENDER=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				if(stmhs!=null && (stmhs.equalsIgnoreCase("C")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D"))) {
        					stmt.setString(1, thsms);
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        				}
        			}
        		}
        		
        		//update daftar ulang
        		stmt = con.prepareStatement("delete from DAFTAR_ULANG where THSMS=? and NPMHS=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				if(stmhs!=null && (stmhs.equalsIgnoreCase("C")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D"))) {
        					stmt.setString(1, thsms);
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        				}
        			}
        		}
        		//update topik pengajuan (batalakan yg bukan sesuai dgn pengajuan = takut ada pengajuan laen)
        		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set BATAL=?,LOCKED=?,SHOW_AT_TARGET=?,SHOW_AT_CREATOR=? where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=? and TIPE_PENGAJUAN<>?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				if(stmhs!=null && (stmhs.equalsIgnoreCase("C")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D")||stmhs.equalsIgnoreCase("L"))) {
        					stmt.setBoolean(1, true);
        					stmt.setBoolean(2, true);
        					stmt.setNull(3, java.sql.Types.VARCHAR);
        					stmt.setBoolean(4, false);
        					stmt.setString(5, thsms);
        					stmt.setString(6, npmhs);
        					stmt.setString(7, "KELULUSAN");
        					stmt.executeUpdate();
        				}
        			}
        		}
        		//update topik pengajuan lulusnya
        		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set BATAL=?,LOCKED=?,SHOW_AT_TARGET=?,SHOW_AT_CREATOR=? where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=? and TIPE_PENGAJUAN=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				if(stmhs!=null && (stmhs.equalsIgnoreCase("C")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D")||stmhs.equalsIgnoreCase("L"))) {
        					stmt.setBoolean(1, false);
        					stmt.setBoolean(2, true);
        					stmt.setNull(3, java.sql.Types.VARCHAR);
        					stmt.setBoolean(4, false);
        					stmt.setString(5, thsms);
        					stmt.setString(6, npmhs);
        					stmt.setString(7, "KELULUSAN");
        					stmt.executeUpdate();
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
		catch (Exception ex) {
			ex.printStackTrace();
		} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    }
    
    public void forcedInputLulusanViaExcel(Vector v_from_excel, String default_tglls) {
    	/*
    	 * HARUS SAMA STEPS PROSESNYA DENGAN fn updStmhsLulusan 
    	 */
    	int upd = 0;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	Vector v = null;
    	try {
    		if(v_from_excel!=null && v_from_excel.size()>0) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select ID_OBJ,NPMHSMSMHS,NMMHSMSMHS from CIVITAS where NIMHSMSMHS=?");
        		li = v_from_excel.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			String norut = st.nextToken();
        			String thsms = st.nextToken();
        			String kdpti = st.nextToken();
        			String kdpst = st.nextToken();
        			String nimhs = st.nextToken();
        			if(nimhs.equalsIgnoreCase("11340357030")) {
        				 nimhs = "11340357037";
        			}
        			//else if(nimhs.equalsIgnoreCase("11340357030")) {
        			//	nimhs = "11340357037";
        			//}
        			//10710650053
        			//else if()
        			stmt.setString(1, nimhs);
        			//System.out.println(nimhs);;
        			rs = stmt.executeQuery();
        			rs.next();
        			long objid = rs.getLong(1);
        			String npmhs = rs.getString(2);
        			String nmmhs = rs.getString(3);
        			li.set(brs+"`"+npmhs+"`"+objid+"`"+nmmhs.replace("`", "-"));
        		}
        		ListIterator li1 = null;
        		Vector v_ins = null;
        		stmt = con.prepareStatement("update TRLSM set STMHS=?,NOTE=?,TGLLS=? where THSMS=? and NPMHS=?");
        		li = v_from_excel.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			String norut = st.nextToken();
        			String thsms = st.nextToken();
        			String kdpti = st.nextToken();
        			String kdpst = st.nextToken();
        			String nimhs = st.nextToken();
        			String nmmhs = st.nextToken();
        			String ttsks = st.nextToken();
        			String tgl_lls = st.nextToken();
        			String tgl_wis = st.nextToken();
        			String npmhs = st.nextToken();
        			String objid = st.nextToken();
        			//System.out.println(npmhs);
        			stmt.setString(1, "L");
					stmt.setNull(2, java.sql.Types.VARCHAR);
					stmt.setDate(3, Converter.formatDateBeforeInsert(default_tglls));
					stmt.setString(4,thsms);
					stmt.setString(5,npmhs);
					upd = stmt.executeUpdate();
				//System.out.println(upd);
					if(upd<1) {
					//no prev rec - harus diinsert
						if(v_ins==null) {
							v_ins = new Vector();
							li1 = v_ins.listIterator();
						}
						li1.add(brs);
					}
        		}
        		
        		if(v_ins!=null) {
        			//System.out.println("v_ins = "+v_ins.size());
        			stmt = con.prepareStatement("insert ignore into TRLSM(THSMS,KDPST,NPMHS,STMHS,TGLLS)values(?,?,?,?,?)");
            		li1 = v_ins.listIterator();
            		while(li1.hasNext()) {
            			String brs = (String)li1.next();
            			st = new StringTokenizer(brs,"`");
            			String norut = st.nextToken();
            			String thsms = st.nextToken();
            			String kdpti = st.nextToken();
            			String kdpst = st.nextToken();
            			if(kdpst.contains(".")) {
            				StringTokenizer stp = new StringTokenizer(kdpst,".");
            				kdpst = stp.nextToken();
            			}
            			if(kdpst.equalsIgnoreCase("86208")) {
            				kdpst = "88888";
            			}
            			String nimhs = st.nextToken();
            			String nmmhs = st.nextToken();
            			String ttsks = st.nextToken();
            			String tgl_lls = st.nextToken();
            			String tgl_wis = st.nextToken();
            			String npmhs = st.nextToken();
            			stmt.setString(1, thsms);
        				stmt.setString(2, kdpst);
        				
        				stmt.setString(3, npmhs);
        				stmt.setString(4, "L");
        				stmt.setDate(5, Converter.formatDateBeforeInsert(default_tglls));
        				//System.out.print(brs);
        				int j = stmt.executeUpdate();
        				//System.out.println(" = "+j);
            		}
        		}
        		else {
        			//System.out.println("v_ins=null");
        		}
        		
        		//updaate trnlm 
        		stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM>? and NPMHSTRNLM=?");
        		li = v_from_excel.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			String norut = st.nextToken();
        			String thsms = st.nextToken();
        			String kdpti = st.nextToken();
        			String kdpst = st.nextToken();
        			if(kdpst.contains(".")) {
        				StringTokenizer stp = new StringTokenizer(kdpst,".");
        				kdpst = stp.nextToken();
        			}
        			String nimhs = st.nextToken();
        			String nmmhs = st.nextToken();
        			String ttsks = st.nextToken();
        			String tgl_lls = st.nextToken();
        			String tgl_wis = st.nextToken();
        			String npmhs = st.nextToken();
        			String objid = st.nextToken();
        			stmt.setString(1, thsms);
        			stmt.setString(2, npmhs);
        			stmt.executeUpdate();
        		}	
        		
        		
        		
        		stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM>? and NPMHSTRAKM=?");
        		li = v_from_excel.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			String norut = st.nextToken();
        			String thsms = st.nextToken();
        			String kdpti = st.nextToken();
        			String kdpst = st.nextToken();
        			String nimhs = st.nextToken();
        			String nmmhs = st.nextToken();
        			String ttsks = st.nextToken();
        			String tgl_lls = st.nextToken();
        			String tgl_wis = st.nextToken();
        			String npmhs = st.nextToken();
        			String objid = st.nextToken();
        			stmt.setString(1, thsms);
        			stmt.setString(2, npmhs);
        			stmt.executeUpdate();
        		}	
        		//update krs notification
        		stmt = con.prepareStatement("delete from KRS_NOTIFICATION where THSMS_TARGET=? and NPM_SENDER=?");
        		li = v_from_excel.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			String norut = st.nextToken();
        			String thsms = st.nextToken();
        			String kdpti = st.nextToken();
        			String kdpst = st.nextToken();
        			String nimhs = st.nextToken();
        			String nmmhs = st.nextToken();
        			String ttsks = st.nextToken();
        			String tgl_lls = st.nextToken();
        			String tgl_wis = st.nextToken();
        			String npmhs = st.nextToken();
        			String objid = st.nextToken();
        			stmt.setString(1, thsms);
        			stmt.setString(2, npmhs);
        			stmt.executeUpdate();
        		}	
        		
        		//update daftar ulang
        		stmt = con.prepareStatement("delete from DAFTAR_ULANG where THSMS=? and NPMHS=?");
        		li = v_from_excel.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			String norut = st.nextToken();
        			String thsms = st.nextToken();
        			String kdpti = st.nextToken();
        			String kdpst = st.nextToken();
        			String nimhs = st.nextToken();
        			String nmmhs = st.nextToken();
        			String ttsks = st.nextToken();
        			String tgl_lls = st.nextToken();
        			String tgl_wis = st.nextToken();
        			String npmhs = st.nextToken();
        			String objid = st.nextToken();
        			stmt.setString(1, thsms);
        			stmt.setString(2, npmhs);
        			stmt.executeUpdate();
        		}	
        		
        		
        		//get kelulusan approval
        		stmt = con.prepareStatement("select TKN_JABATAN_VERIFICATOR,TKN_VERIFICATOR_ID from KELULUSAN_RULES where THSMS=? and KDPST=?");
        		li = v_from_excel.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			String norut = st.nextToken();
        			String thsms = st.nextToken();
        			String kdpti = st.nextToken();
        			String kdpst = st.nextToken();
        			if(kdpst.contains(".")) {
        				StringTokenizer stp = new StringTokenizer(kdpst,".");
        				kdpst = stp.nextToken();
        			}
        			if(kdpst.equalsIgnoreCase("86208")) {
        				kdpst = "88888";
        			}
        			String nimhs = st.nextToken();
        			if(nimhs.equalsIgnoreCase("11340357030")) {
        				 nimhs = "11340357037";
        			}
        			stmt.setString(1, thsms);
        			stmt.setString(2, kdpst);
        			//System.out.println(nimhs);;
        			rs = stmt.executeQuery();
        			//System.out.println("verificator="+brs);
        			rs.next();
        			//String npmhs = rs.getString(1);
        			li.set(brs+"`"+rs.getString(1)+"`"+rs.getString(2));
        		}
        		v_ins = new Vector();
        		ListIterator lin = v_ins.listIterator();
        		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set TIPE_PENGAJUAN=?,ISI_TOPIK_PENGAJUAN=?,TOKEN_TARGET_OBJ_NICKNAME=?,TOKEN_TARGET_OBJID=?,SHOW_AT_TARGET=?,SHOW_AT_CREATOR=?,APPROVED=?,LOCKED=?,REJECTED=? where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=?");
        		li = v_from_excel.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			String norut = st.nextToken();
        			String thsms = st.nextToken();
        			String kdpti = st.nextToken();
        			String kdpst = st.nextToken();
        			if(kdpst.contains(".")) {
        				StringTokenizer stp = new StringTokenizer(kdpst,".");
        				kdpst = stp.nextToken();
        			}
        			String nimhs = st.nextToken();
        			String nmmhs = st.nextToken();
        			String ttsks = st.nextToken();
        			String tgl_lls = st.nextToken();
        			String tgl_wis = st.nextToken();
        			String npmhs = st.nextToken();
        			String objid = st.nextToken();
        			nmmhs = st.nextToken();
        			nmmhs = nmmhs.replace("-", "`");
        			nmmhs = nmmhs.toUpperCase();
        			String tkn_jbt = st.nextToken();
        			String tkn_jbt_id = st.nextToken();
        			stmt.setString(1, "KELULUSAN");
        			stmt.setString(2, ""+AskSystem.getTodayDate());
        			stmt.setString(3, tkn_jbt);
        			stmt.setString(4, tkn_jbt_id);
        			stmt.setNull(5, java.sql.Types.VARCHAR);
        			stmt.setBoolean(6, false);
        			stmt.setString(7, tkn_jbt_id);
        			stmt.setBoolean(8, true);
        			stmt.setNull(9, java.sql.Types.VARCHAR);
        			stmt.setString(10, thsms);
        			stmt.setString(11, npmhs);
        			int i = stmt.executeUpdate();
        			if(i<1) {
        				lin.add(brs);
        			}
        		}
        		if(v_ins.size()>0) {
        			stmt = con.prepareStatement("INSERT INTO TOPIK_PENGAJUAN(TARGET_THSMS_PENGAJUAN,TIPE_PENGAJUAN,ISI_TOPIK_PENGAJUAN,TOKEN_TARGET_OBJ_NICKNAME,TOKEN_TARGET_OBJID,CREATOR_OBJ_ID,CREATOR_NPM,CREATOR_NMM,SHOW_AT_CREATOR,APPROVED,LOCKED,CREATOR_KDPST) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
        			lin = v_ins.listIterator();
        			while(lin.hasNext()) {
        				String brs = (String)lin.next();
        				st = new StringTokenizer(brs,"`");
        				String norut = st.nextToken();
            			String thsms = st.nextToken();
            			String kdpti = st.nextToken();
            			String kdpst = st.nextToken();
            			if(kdpst.contains(".")) {
            				StringTokenizer stp = new StringTokenizer(kdpst,".");
            				kdpst = stp.nextToken();
            			}
            			if(kdpst.equalsIgnoreCase("86208")) {
            				kdpst = "88888";
            			}
            			String nimhs = st.nextToken();
            			String nmmhs = st.nextToken();
            			String ttsks = st.nextToken();
            			String tgl_lls = st.nextToken();
            			String tgl_wis = st.nextToken();
            			String npmhs = st.nextToken();
            			String objid = st.nextToken();
            			nmmhs = st.nextToken();
            			nmmhs = nmmhs.replace("-", "`");
            			nmmhs = nmmhs.toUpperCase();
            			String tkn_jbt = st.nextToken();
            			String tkn_jbt_id = st.nextToken();
            			int i=1;
            			//TARGET_THSMS_PENGAJUAN,
            			stmt.setString(i++, thsms);
            			//TIPE_PENGAJUAN,
            			stmt.setString(i++, "KELULUSAN");
            			//ISI_TOPIK_PENGAJUAN,
            			stmt.setString(i++, ""+AskSystem.getTodayDate());
            			//TOKEN_TARGET_OBJ_NICKNAME,
            			stmt.setString(i++, tkn_jbt);
            			//TOKEN_TARGET_OBJID,
            			stmt.setString(i++, tkn_jbt_id);
            			//CREATOR_OBJ_ID,
            			stmt.setString(i++, objid);
            			//CREATOR_NPM,
            			stmt.setString(i++, npmhs);
            			//CREATOR_NMM,
            			stmt.setString(i++, nmmhs);
            			//SHOW_AT_CREATOR,
            			stmt.setBoolean(i++, false);
            			//APPROVED,
            			stmt.setString(i++, tkn_jbt_id);
            			//LOCKED,
            			stmt.setBoolean(i++, true);
            			//CREATOR_KDPST
            			stmt.setString(i++, kdpst);
            			i = stmt.executeUpdate();
            			if(i<1) {
            				//System.out.println("gagal insert = "+brs);
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
		catch (Exception ex) {
			ex.printStackTrace();
		} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    }
    
    public Vector updStmhsBassedOnExcelIjazah() {
    	String file_list_ijazah = "list_ijazah";
		Vector v = Tool.bacaFileExcel(Getter.getTmpFolderPath(),file_list_ijazah, "0`1`2`3`4`5`6`7", 1);
    	int upd = 0;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	Vector v_mismatch = null;
    	ListIterator li1 = null;
    	try {
    		if(v!=null && v.size()>0) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
    			//System.out.println("v_size="+v.size());
    			stmt = con.prepareStatement("select NPMHSMSMHS from CIVITAS where (NPMHSMSMHS=? or NIMHSMSMHS=?) or (NPMHSMSMHS=? or NIMHSMSMHS=?) limit 1");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				brs = brs.replace("``", "`null`");
    				//System.out.println(brs);
    				st = new StringTokenizer(brs,"`");
    				String no = st.nextToken();
    				String noija = st.nextToken();
    				String nmmhs = st.nextToken();
    				String tglhr = st.nextToken();
    				String nimhs = st.nextToken();
    				String nirl = st.nextToken();
    				String datetime = st.nextToken();
    				String npm_di_sistem = st.nextToken();
    				String tglls ="";
    				if(!Checker.isStringNullOrEmpty(datetime)) {
    					st = new StringTokenizer(datetime);
    					//System.out.println("datetime="+datetime);
    					String dt = "";
    					String month = "";
    					String year = "";
    					if(st.countTokens()==6) {
    						String hr = st.nextToken();
    						month = st.nextToken();
    						dt = st.nextToken();
    						String tm = st.nextToken();
    						String zone = st.nextToken();
    						year = st.nextToken();
    					}
    					else {
    						dt = st.nextToken();
    						month = st.nextToken();
    						year = st.nextToken();
    					}
    					if(month.equalsIgnoreCase("JAN")||month.equalsIgnoreCase("JANUARY")||month.equalsIgnoreCase("JANUARI")) {
    						month = "1";
    					}
    					else if(month.equalsIgnoreCase("FEB")||month.equalsIgnoreCase("FEBRUARY")||month.equalsIgnoreCase("FEBRUARI")||month.equalsIgnoreCase("PEBRUARI")||month.equalsIgnoreCase("PEB")) {
    						month = "2";
    					}
    					else if(month.equalsIgnoreCase("MAR")||month.equalsIgnoreCase("MARCH")||month.equalsIgnoreCase("MARET")) {
    						month = "3";
    					}
    					else if(month.equalsIgnoreCase("APR")||month.equalsIgnoreCase("APRIL")) {
    						month = "4";
    					}
    					else if(month.equalsIgnoreCase("MAY")||month.equalsIgnoreCase("MEI")) {
    						month = "5";
    					}
    					else if(month.equalsIgnoreCase("JUN")||month.equalsIgnoreCase("JUNY")||month.equalsIgnoreCase("JUNI")) {
    						month = "6";
    					}
    					else if(month.equalsIgnoreCase("JUL")||month.equalsIgnoreCase("JULY")||month.equalsIgnoreCase("JULI")) {
    						month = "7";
    					}
    					else if(month.equalsIgnoreCase("AUG")||month.equalsIgnoreCase("AUGUST")||month.equalsIgnoreCase("AGUSTUS")) {
    						month = "8";
    					}
    					else if(month.equalsIgnoreCase("SEP")||month.equalsIgnoreCase("SEPTEMBER")) {
    						month = "9";
    					}
    					else if(month.equalsIgnoreCase("NOV")||month.equalsIgnoreCase("NOVEMBER")||month.equalsIgnoreCase("NOPEMBER")||month.equalsIgnoreCase("NOP")) {
    						month = "11";
    					}
    					else if(month.equalsIgnoreCase("OCT")||month.equalsIgnoreCase("OCTOBER")||month.equalsIgnoreCase("OKT")||month.equalsIgnoreCase("OKTOBER")) {
    						month = "10";
    					}
    					else if(month.equalsIgnoreCase("DES")||month.equalsIgnoreCase("DESEMBER")||month.equalsIgnoreCase("DEC")||month.equalsIgnoreCase("DECEMBER")) {
    						month = "12";
    					}
    					
    					tglls = year+"-"+month+"-"+dt;
    					//System.out.println("tglls="+tglls);
    				}
    				
    				if(!Checker.isStringNullOrEmpty(npm_di_sistem)) {
    					stmt.setString(1, npm_di_sistem);
    					stmt.setString(2, npm_di_sistem);
    					stmt.setString(3, nimhs);
    					stmt.setString(4, nimhs);
    				}
    				else {
    					stmt.setString(1, nimhs);
    					stmt.setString(2, nimhs);
    					stmt.setString(3, nimhs);
    					stmt.setString(4, nimhs);
    				}
    				
    				rs = stmt.executeQuery();
    				if(!rs.next()) {
    					li.remove();
    					if(v_mismatch==null) {
    						v_mismatch = new Vector();
    						li1 = v_mismatch.listIterator();
    					}
    					li1.add("NIM/NPM tidak ada di sistem : "+nimhs+"`"+nmmhs);
    				}
    				else {
    					String npmhs = rs.getString(1);
    					li.set(noija+"`"+npmhs+"`"+nirl+"`"+tglls+"`"+nimhs+"`"+nmmhs);
    				}
    			}
    			//System.out.println("v_size="+v.size());
    		}
    		
    		if(v!=null && v.size()>0) {
    			li = v.listIterator();
    			/*
    			 * filter : cek apa sudah ada status kelulusan di TRLSM
    			 */
    			stmt = con.prepareStatement("select * from TRLSM where NPMHS=? and STMHS=? limit 1");
    			while(li.hasNext()) {
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			String noija = st.nextToken();
        			String npmhs = st.nextToken();
        			String nirl = st.nextToken();
        			String tglls = st.nextToken();
        			String nimhs = st.nextToken();
        			String nmmhs = st.nextToken();
        			//String npm_di_sistem = st.nextToken();
        			stmt.setString(1, npmhs);
        			stmt.setString(2, "L");
        			rs = stmt.executeQuery();
        			if(!rs.next()) {
        				li.remove();
        				if(v_mismatch==null) {
    						v_mismatch = new Vector();
    						li1 = v_mismatch.listIterator();
    					}
        				li1.add("BELUM ADA PENGAJUAN LULUSNYA : "+brs);
        			}
    			}	
    			/*
    			 * UPDATE DOANG, HARUS ADA STATUS LULUS DULU VIA PENGAJUAN !!!!JADI NO INSERT!!!!
    			 */ 
        		stmt = con.prepareStatement("update TRLSM set TGLLS=?,NOIJA=? where NPMHS=? and STMHS=?");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			String noija = st.nextToken();
        			String npmhs = st.nextToken();
        			String nirl = st.nextToken();
        			String tglls = st.nextToken();
        			String nimhs = st.nextToken();
        			String nmmhs = st.nextToken();
        			//System.out.println("tglls1="+tglls);
        			stmt.setDate(1, java.sql.Date.valueOf(tglls));
        			stmt.setString(2, noija);
        			stmt.setString(3, npmhs);
        			stmt.setString(4, "L");
        			int i = stmt.executeUpdate();
        			if(i<1) {
        				if(v_mismatch==null) {
    						v_mismatch = new Vector();
    						li1 = v_mismatch.listIterator();
    					}
        				li1.add("UPDATE GAGAL : "+brs);
        			}
        		}
    		}
    		
    	} 
		catch (NamingException e) {
			e.printStackTrace();
			if(v_mismatch==null) {
				v_mismatch = new Vector();
				li1 = v_mismatch.listIterator();
			}
			li1.add("ERROR NamingException : HARAP HUBUNGI ADMIN");
			
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			if(v_mismatch==null) {
				v_mismatch = new Vector();
				li1 = v_mismatch.listIterator();
			}
			li1.add("ERROR SQLException : HARAP HUBUNGI ADMIN");
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			if(v_mismatch==null) {
				v_mismatch = new Vector();
				li1 = v_mismatch.listIterator();
			}
			li1.add("ERROR Exception : HARAP HUBUNGI ADMIN");
		} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	return v_mismatch;
    }
    
    public Vector updStmhsBassedOnExcelIjazah_v1(String file_name, boolean full_editor) {
    	//System.out.println("cek file : "+Getter.getTmpFolderPath() +" = "+file_name );
    	String info = null;
		Vector v = Tool.bacaFileExcel(Getter.getTmpFolderPath(),file_name, "0`1`2`3`4`5`6`7`8`9`10`11`12`13`14`15", 1);
    	int upd = 0;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	Vector v_info = null;
    	ListIterator li1 = null;
    	int updated = 0;
    	try {
    		if(v!=null && v.size()>0) {
    			info = new String("Total Data: "+v.size());
    			//System.out.println("goon");
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
    			
    			li = v.listIterator();
    			
    			while(li.hasNext()) {
    				int i = 1;
    				String brs = (String)li.next();
    				brs = brs.replace("``", "`null`");
    				//System.out.println(brs);
    				st = new StringTokenizer(brs,"`");
    				String norut = st.nextToken();
    				String thsms = st.nextToken();
    				String kdpst = st.nextToken();
    				String npmhs = st.nextToken();
    				String nimhs = st.nextToken();
    				String nmmhs = st.nextToken();
    				String stmhs = st.nextToken();
    				String skstt = st.nextToken();
    				String nlipk = st.nextToken();
    				String noskr = st.nextToken();
    				String tglls = st.nextToken();
    				String tgl_wisuda = st.nextToken();
    				String judul = st.nextToken();
    				String noija = st.nextToken();
    				String nirl = st.nextToken();
    				String tgl_terbit_ija = st.nextToken();
    				
    				String sql_cmd = "update TRLSM set";
    				boolean lanjut_execute = false;
    				boolean first = true;
    				//skstt
					if(!Checker.isStringNullOrEmpty(skstt) && Integer.parseInt(skstt)>0) {
						lanjut_execute = true;
						if(first) {
							first = false;
							sql_cmd = sql_cmd+" SKSTT="+skstt;
						}
						else {
							sql_cmd = sql_cmd+",SKSTT="+skstt;	
						}
						 
					}
					//nlipk
					if(!Checker.isStringNullOrEmpty(nlipk) && Double.parseDouble(nlipk)>0) {
						lanjut_execute = true;
						if(first) {
							first = false;
							sql_cmd = sql_cmd+" NLIPK="+nlipk;
						}
						else {
							sql_cmd = sql_cmd+",NLIPK="+nlipk; 
						}
						
					}
					
					//noskr
					if(!Checker.isStringNullOrEmpty(noskr)) {
						lanjut_execute = true;
						if(first) {
							first = false;
							sql_cmd = sql_cmd+" NOSKR='"+noskr+"'";
						}
						else {
							sql_cmd = sql_cmd+",NOSKR='"+noskr+"'"; 
						}
						
					}
					
					//tglls
					if(!Checker.isStringNullOrEmpty(tglls)) {
						//java.sql.Date tmp_dt = Converter.formatDateBeforeInsert(tglls);
						java.sql.Date tmp_dt = java.sql.Date.valueOf(Converter.autoConvertDateFormat(tglls, "-"));
						if(tmp_dt!=null) {
							lanjut_execute = true;
							if(first) {
								first = false;
								sql_cmd = sql_cmd+" TGLLS='"+tmp_dt+"'";
							}
							else {
								sql_cmd = sql_cmd+",TGLLS='"+tmp_dt+"'";	
							}
							
						}
					}
					
					//tgl_wisuda
					if(!Checker.isStringNullOrEmpty(tglls)) {
						//java.sql.Date tmp_dt = Converter.formatDateBeforeInsert(tgl_wisuda);
						java.sql.Date tmp_dt = java.sql.Date.valueOf(Converter.autoConvertDateFormat(tgl_wisuda, "-"));
						if(tmp_dt!=null) {
							lanjut_execute = true;
							if(first) {
								first = false;
								sql_cmd = sql_cmd+" TGL_WISUDA='"+tmp_dt+"'";
							}
							else {
								sql_cmd = sql_cmd+",TGL_WISUDA='"+tmp_dt+"'";	
							}
							
						}
					}
					
					//judul
					if(!Checker.isStringNullOrEmpty(judul)) {
						lanjut_execute = true;
						if(first) {
							first = false;
							sql_cmd = sql_cmd+" JUDUL='"+judul+"'";
						}
						else {
							sql_cmd = sql_cmd+",JUDUL='"+judul+"'"; 
						}
						
					}
					
    				if(full_editor) {
    					//noija
    					if(!Checker.isStringNullOrEmpty(noija)) {
    						lanjut_execute = true;
    						if(first) {
    							first = false;
    							sql_cmd = sql_cmd+" NOIJA='"+noija+"'";
    						}
    						else {
    							sql_cmd = sql_cmd+",NOIJA='"+noija+"'"; 
    						}
    						
    					}
        				//nirl
    					if(!Checker.isStringNullOrEmpty(nirl)) {
    						lanjut_execute = true;
    						if(first) {
    							first = false;
    							sql_cmd = sql_cmd+" NIRL='"+nirl+"'";
    						}
    						else {
    							sql_cmd = sql_cmd+",NIRL='"+nirl+"'"; 
    						}
    						
    					}
        				//tgl_terbit_ija
    					if(!Checker.isStringNullOrEmpty(tgl_terbit_ija)) {
    						lanjut_execute = true;
    						if(first) {
    							first = false;
    							sql_cmd = sql_cmd+" TGL_TERBIT_IJA='"+Converter.autoConvertDateFormat(tgl_terbit_ija, "-")+"'";
    						}
    						else {
    							sql_cmd = sql_cmd+",TGL_TERBIT_IJA='"+Converter.autoConvertDateFormat(tgl_terbit_ija, "-")+"'"; 
    						}
    						
    					}
            		}
    				
    				if(lanjut_execute) {
    					sql_cmd = sql_cmd +" where NPMHS='"+npmhs+"'";
    					//System.out.println(sql_cmd);
    					stmt = con.prepareStatement(sql_cmd);
    					updated = updated+stmt.executeUpdate();
    					
    				}
            		
    			}//end wjile
    			//info = info +"`updated`"+updated;
    		} 
    		else {
    			//System.out.println("v is null");
    		}
    		if(v_info==null) {
				v_info = new Vector();
				li1 = v_info.listIterator();
			}
			li1.add(info);
			li1.add("Updated: "+updated);
    	} 
		catch (NamingException e) {
			e.printStackTrace();
			if(v_info==null) {
				v_info = new Vector();
				li1 = v_info.listIterator();
			}
			li1.add("ERROR NamingException : HARAP HUBUNGI ADMIN");
			
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			if(v_info==null) {
				v_info = new Vector();
				li1 = v_info.listIterator();
			}
			li1.add("ERROR SQLException : HARAP HUBUNGI ADMIN");
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			if(v_info==null) {
				v_info = new Vector();
				li1 = v_info.listIterator();
			}
			li1.add("ERROR Exception : HARAP HUBUNGI ADMIN");
		} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	return v_info;
    }
    
    
    
    public void resetPengajuan(String tipe, String thsms, String npmhs) {	
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
			//System.out.println("v_size="+v.size());
			stmt = con.prepareStatement("delete from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN like ? and CREATOR_NPM=?");
			stmt.setString(1, thsms);
			if(tipe.equalsIgnoreCase("C")) {
				stmt.setString(2, "%CUTI%");	
			}
			else if(tipe.equalsIgnoreCase("D")) {
				stmt.setString(2, "%DO%");	
			}
			else if(tipe.equalsIgnoreCase("K")) {
				stmt.setString(2, "%KELUAR%");	
			}
			else if(tipe.equalsIgnoreCase("L")) {
				stmt.setString(2, "%LULUS%");	
			}
			else if(tipe.equalsIgnoreCase("P")) {
				stmt.setString(2, "%PINDAH%");	
			}
			else if(tipe.equalsIgnoreCase("PP")) {
				stmt.setString(2, "%PINDAH%");	
			}
			stmt.setString(3, npmhs);
			int i = stmt.executeUpdate();
			if(i>0) {
				stmt = con.prepareStatement("delete from TRLSM where THSMS=? and NPMHS=? and STMHS=?");
				stmt.setString(1, thsms);
				stmt.setString(2, npmhs);
				stmt.setString(3, tipe);
				stmt.executeUpdate();
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
    /*
     * KAYAKNYA DEPRECATED
     */
    public int updStmhsTanpaBerita(Vector v_npmhs_tkn_thsms, String kdpst, String stmhs) {
    	ListIterator li = null;
    	StringTokenizer st = null;
    	int updated = 0;
    	try {
    		if(v_npmhs_tkn_thsms!=null) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("insert into TRLSM(THSMS,KDPST,NPMHS,STMHS)values(?,?,?,?)");
        		li = v_npmhs_tkn_thsms.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			String npmhs = st.nextToken();
        			while(st.hasMoreTokens()) {
        				String thsms = st.nextToken();
        				stmt.setString(1, thsms);
        				stmt.setString(2, kdpst);
        				stmt.setString(3, npmhs);
        				stmt.setString(4, stmhs);
        				int i = stmt.executeUpdate();
        				updated = updated + i;
        				//System.out.println("insert "+npmhs+"-"+thsms+"="+i );
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
		catch (Exception ex) {
			ex.printStackTrace();
		} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	return updated;
    }
    
    
    public int updStmhsTanpaBerita_v1(Vector v_npmhs_kdpst, String stmhs, String thsms) {
    	ListIterator li = null;
    	StringTokenizer st = null;
    	int updated = 0;
    	try {
    		if(v_npmhs_kdpst!=null) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
        		con = ds.getConnection();
        		stmt = con.prepareStatement("insert into TRLSM(THSMS,KDPST,NPMHS,STMHS)values(?,?,?,?)");
        		li = v_npmhs_kdpst.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			String kdpst = st.nextToken();
        			String npmhs = st.nextToken();
        			
        			stmt.setString(1, thsms);
        			stmt.setString(2, kdpst);
        			stmt.setString(3, npmhs);
        			stmt.setString(4, stmhs);
        			int i = stmt.executeUpdate();
        			updated = updated + i;
        			//System.out.println("insert "+npmhs+"-"+thsms+"="+i );
        			
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
    	return updated;
    }
    
    public int updateTrlsmPascaBasedOnExcel(Vector v_riwayat_excel) {
    	int updated = 0;
    	String target_kdpst = "";
    	String target_kdjen = "";
		String target_thsms = "";
    	String kdpti = Constants.getKdpti();
    	String target_npmhs = "";
    	if(v_riwayat_excel!=null && v_riwayat_excel.size()>0) {
    		ListIterator li = v_riwayat_excel.listIterator();
    		//filter untuk krsdoang (krn v_hist ada data trlsm juga)
    		boolean first_row_title = true;
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			if(first_row_title) {
    				//hapus header
    				first_row_title = false;
    				li.remove();
    			}
    			else {
    				if(!brs.endsWith("`null`null`null")) {
    					StringTokenizer st = new StringTokenizer(brs,"`");
            			target_thsms = st.nextToken();
            			target_kdpst = st.nextToken();
            			target_npmhs = st.nextToken();
        				li.remove();
        			}
    			}
    		}
    		target_kdjen = Checker.getKdjen(target_kdpst);
    		if(v_riwayat_excel!=null && v_riwayat_excel.size()>0) {
    			try {
        			Context initContext  = new InitialContext();
            		Context envContext  = (Context)initContext.lookup("java:/comp/env");
            		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
            		con = ds.getConnection();
            		Vector v_riwayat_yg_ada_disistem_skrg = SearchDbTrlsm.getStandarRiwayatTrlsmMhs(target_npmhs,con);
            		li = v_riwayat_excel.listIterator();
            		//String kdjen = Checker.getKdjen(target_kdpst,con);
            		//bandingkan dgn lsm yg ada sekarang, hapus mk yg tidak ada di list atas
            		//stmt = con.prepareStatement(sql)
            		Vector v_del = null;
            		ListIterator lid = null;
            		//Vector v_riwayat_yg_ada_disistem_skrg = SearchDbTrnlm.getStandarRiwayatKrsMhs(target_npmhs,con);
            		if(v_riwayat_yg_ada_disistem_skrg!=null && v_riwayat_yg_ada_disistem_skrg.size()>0) {
            			ListIterator li_tmp = v_riwayat_yg_ada_disistem_skrg.listIterator();
            			while(li_tmp.hasNext()) {
            				String old_hist = (String)li_tmp.next();
            				//li.add(thsms+"`"+kdpst+"`"+npmhs+"`"+kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+sksmk);
            				li = v_riwayat_excel.listIterator();
            				boolean match = false;
                    		while(li.hasNext() && !match) {
                    			String brs = (String)li.next();
                    			StringTokenizer st = new StringTokenizer(brs,"`");
                    			target_thsms = st.nextToken();
                    			target_kdpst = st.nextToken();
                    			target_npmhs = st.nextToken();
                    			String stmhs = st.nextToken();
                    			//String nlakh = st.nextToken();
                    			//String bobot = st.nextToken();
                    			//String sks = st.nextToken();
                    			//String kdjen = Checker.getKdjen(target_kdpst,con);
                    			if(old_hist.startsWith(target_thsms+"`") &&  (old_hist.contains("`"+stmhs+"`")||old_hist.contains("`"+stmhs.toUpperCase()+"`")||old_hist.contains("`"+stmhs.toLowerCase()+"`"))) {
                    				match = true;
                    			}
                    			if(!li.hasNext() && !match) {
                    				if(v_del==null) {
                    					v_del = new Vector();
                    					lid = v_del.listIterator();
                    				}
                    				lid.add(old_hist);
                    			}
                    		}	
                    	}
            		}
            		//delete yg ngga ada di excel
            		if(v_del!=null && v_del.size()>0) {
                		stmt = con.prepareStatement("delete from TRLSM where THSMS=? and NPMHS=? and STMHS=?");
            			lid = v_del.listIterator();
            			while(lid.hasNext()) {
            				String hapus = (String)lid.next();
            				//20172`61101`6110117100001`DP08`0.0`T`0.0`3
            				StringTokenizer st = new StringTokenizer(hapus,"`");
            				String thsms = st.nextToken();
            				String kdpst = st.nextToken();
            				String npmhs = st.nextToken();
            				String stmhs = st.nextToken();
            				stmt.setString(1, thsms);
            				stmt.setString(2, npmhs);
            				stmt.setString(3, stmhs);
            				updated = stmt.executeUpdate();
            			}
            		}
            		
            		if(v_riwayat_excel!=null && v_riwayat_excel.size()>0) {
            			Vector v_ins = null;
            			ListIterator lins = null;
            			li = v_riwayat_excel.listIterator();
            			//coba update nilai
            			stmt = con.prepareStatement("update TRLSM set STMHS=? where THSMS=? and NPMHS=?");
                		while(li.hasNext()) {
                			String brs = (String)li.next();
                			StringTokenizer st = new StringTokenizer(brs,"`");
                			target_thsms = st.nextToken();
                			target_kdpst = st.nextToken();
                			target_npmhs = st.nextToken();
                			String stmhs = st.nextToken();
                			
                			stmt.setString(1, stmhs);
                			stmt.setString(2, target_thsms);
                			stmt.setString(3, target_npmhs);
                			updated = 0;
                			updated = stmt.executeUpdate();
                			if(updated<=0) {
                				if(v_ins==null) {
                					v_ins = new Vector();
                					lins = v_ins.listIterator();
                				}
                				lins.add(brs);
                			}
                		}
                		
                		if(v_ins!=null && v_ins.size()>0) {
                			stmt = con.prepareStatement("insert into TRLSM(THSMS,KDPST,NPMHS,STMHS)values(?,?,?,?)");
                			lins = v_ins.listIterator();
                			while(lins.hasNext()) {
                    			String brs = (String)lins.next();
                    			StringTokenizer st = new StringTokenizer(brs,"`");
                    			target_thsms = st.nextToken();
                    			target_kdpst = st.nextToken();
                    			target_npmhs = st.nextToken();
                    			String stmhs = st.nextToken();
                    			
                    			int i = 1;
                    			//THSMSTRNLM,
                    			stmt.setString(i++, target_thsms);
                    			//KDPSTTRNLM,
                    			stmt.setString(i++, target_kdpst);
                    			//NPMHSTRNLM,
                    			stmt.setString(i++, target_npmhs);
                    			//KDKMKTRNLM,
                    			stmt.setString(i++, stmhs);
                    			updated = updated + stmt.executeUpdate();
                			}	
                		}
            		}
            		//update trnlm & trakm & trlsm bila diperlukan
            		if(v_riwayat_excel!=null && v_riwayat_excel.size()>0) {
            			boolean ada_rec_keluar = false;
            			boolean ada_rec_lulus = false;
            			
            			//coba update trnlm // TRAKM tidak perlu didelete, tapi harus dihitung ulang
            			stmt = con.prepareStatement("DELETE FROM TRNLM WHERE THSMSTRNLM=? and NPMHSTRNLM=?");
            			li = v_riwayat_excel.listIterator();
                		while(li.hasNext()) {
                			String brs = (String)li.next();
                			StringTokenizer st = new StringTokenizer(brs,"`");
                			target_thsms = st.nextToken();
                			target_kdpst = st.nextToken();
                			target_npmhs = st.nextToken();
                			String stmhs = st.nextToken();
                			if(stmhs.equalsIgnoreCase("C")||stmhs.equalsIgnoreCase("N")||stmhs.equalsIgnoreCase("P")) {
                				stmt.setString(1,target_thsms);
                				stmt.setString(2,target_npmhs);
                				stmt.executeUpdate();
                			}
                			else if(stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D")) {
                				ada_rec_keluar = true;
                			}
                			else if(stmhs.equalsIgnoreCase("L")) {
                				ada_rec_lulus = true;
                			}
                		}
                		
                		if(ada_rec_keluar) {
                			stmt = con.prepareStatement("DELETE FROM TRNLM WHERE THSMSTRNLM>=? and NPMHSTRNLM=?");
                			li = v_riwayat_excel.listIterator();
                    		while(li.hasNext()) {
                    			String brs = (String)li.next();
                    			StringTokenizer st = new StringTokenizer(brs,"`");
                    			target_thsms = st.nextToken();
                    			target_kdpst = st.nextToken();
                    			target_npmhs = st.nextToken();
                    			String stmhs = st.nextToken();
                    			if(stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("D")) {
                    				stmt.setString(1,target_thsms);
                    				stmt.setString(2,target_npmhs);
                    				stmt.executeUpdate();
                    			}
                    		}	
                		}
                		if(ada_rec_lulus) {
                			stmt = con.prepareStatement("DELETE FROM TRNLM WHERE THSMSTRNLM>? and NPMHSTRNLM=?");
                			li = v_riwayat_excel.listIterator();
                    		while(li.hasNext()) {
                    			String brs = (String)li.next();
                    			StringTokenizer st = new StringTokenizer(brs,"`");
                    			target_thsms = st.nextToken();
                    			target_kdpst = st.nextToken();
                    			target_npmhs = st.nextToken();
                    			String stmhs = st.nextToken();
                    			if(stmhs.equalsIgnoreCase("L")) {
                    				stmt.setString(1,target_thsms);
                    				stmt.setString(2,target_npmhs);
                    				stmt.executeUpdate();
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
    			catch (Exception ex) {
    				ex.printStackTrace();
    			} 
    	    	finally {
    	    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			    if (con!=null) try { con.close();} catch (Exception ignore){}
    	    	}
    		}
    	}
    	return updated;
    }	
    
    public int setBlawlBlakh(String thsms, String tkn_kdpst_optional) {
    	int updated=0;
    	Vector v = null;
    	ListIterator li = null;
    	try {
			Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		if(Checker.isStringNullOrEmpty(tkn_kdpst_optional)) {
    			stmt = con.prepareStatement("select NPMHSMSMHS,KDPSTMSMHS,KRKLMMSMHS,NOTE,TGLLS from TRLSM inner join EXT_CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and STMHS=?");
    			stmt.setString(1, thsms);
    			stmt.setString(2, "L");
    		}
    		else {
    			String seperator = Checker.getSeperatorYgDigunakan(tkn_kdpst_optional);
    			StringTokenizer st = new StringTokenizer(tkn_kdpst_optional,seperator);
    			String tmp = "";
    			if(st.hasMoreTokens()) {
    				tmp = new String("KDPST='"+st.nextToken()+"'");
    				while(st.hasMoreTokens()) {
    					tmp = tmp + "or KDPST='"+st.nextToken()+"'";
    				}
    			}
    			stmt = con.prepareStatement("select NPMHSMSMHS,KDPSTMSMHS,KRKLMMSMHS,NOTE,TGLLS from TRLSM inner join EXT_CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and STMHS=? and ("+tmp+")");
    			stmt.setString(1, thsms);
    			stmt.setString(2, "L");
    			
    		}
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				String npmhs = rs.getString(1);
    				String kdpst = rs.getString(2);
    				String idkur = ""+rs.getString(3);
    				String tglaju = ""+rs.getString(4);
    				String tglls = ""+rs.getString(5);
    				//System.out.println("tglls~"+tglls);
    				if(!Checker.isStringNullOrEmpty(tglaju)){
    					li.add(npmhs+"~"+kdpst+"~"+idkur+"~"+Converter.formatDateBeforeInsert(tglaju)+"~"+tglls);
    				}
    				else {
    					li.add(npmhs+"~"+kdpst+"~"+idkur+"~null~"+tglls);
    				}
    				
    				//System.out.println(npmhs+"~"+kdpst+"~"+idkur);
    			}
    			while(rs.next());
    		}
    		if(v!=null) {
    			//cari mk akhir
    			stmt = con.prepareStatement("SELECT IDKMKMAKUL,KDKMKMAKUL,NAKMKMAKUL FROM MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=? and FINAL_MK=true");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				String seperator = Checker.getSeperatorYgDigunakan(brs);
    				StringTokenizer st = new StringTokenizer(brs,seperator);
    				String npmhs = st.nextToken();
    				String kdpst = st.nextToken();
    				String idkur = st.nextToken();
    				String tglaju = st.nextToken();
    				String tglls = st.nextToken();
    				stmt.setInt(1, Integer.parseInt(idkur));
    				rs = stmt.executeQuery();;
    				rs.next();
    				String idkmk = rs.getString(1);
    				String kdkmk = rs.getString(2);
    				String nakmk = rs.getString(3);
    				li.set(brs+seperator+idkmk+seperator+kdkmk+seperator+nakmk);
    				//System.out.println(brs+seperator+idkmk+seperator+kdkmk+seperator+nakmk);
    			}
    		}
    		//cari thsms mk akhir pertama kali
    		if(v!=null) {
    			//cari mk akhir
    			stmt = con.prepareStatement("SELECT THSMSTRNLM from TRNLM where NPMHSTRNLM=? and KDKMKTRNLM=? order by THSMSTRNLM limit 1;");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				String seperator = Checker.getSeperatorYgDigunakan(brs);
    				StringTokenizer st = new StringTokenizer(brs,seperator);
    				String npmhs = st.nextToken();
    				String kdpst = st.nextToken();
    				String idkur = st.nextToken();
    				String tglaju = st.nextToken();
    				String tglls = st.nextToken();
    				String idkmk = st.nextToken();
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				stmt.setString(1, npmhs);
    				stmt.setString(2, kdkmk);
    				String thsms_pertama = "null";
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					thsms_pertama = rs.getString(1);
    				}
    				if(Checker.isStringNullOrEmpty(thsms_pertama)) {
    					thsms_pertama = new String(thsms);
    				}
    				//System.out.println("thsms_pertama="+thsms_pertama);
    				String tgl_awal_bimbingan = ""+Tool.setDefaultBlawl(thsms_pertama);
    				//System.out.println("tglls="+tglls);
    				String tgl_akhir_bimbingan = ""+Tool.setDefaultBlakh(java.sql.Date.valueOf(tglls));
    				li.set(brs+seperator+thsms_pertama+seperator+tgl_awal_bimbingan+seperator+tgl_akhir_bimbingan);
    				
    				//System.out.println(brs+seperator+thsms_pertama+seperator+tgl_awal_bimbingan+seperator+tgl_akhir_bimbingan);
    			}
    		}
    		//update trlsm
    		if(v!=null) {
    			//cari mk akhir
    			stmt = con.prepareStatement("update TRLSM set TGL_AWAL_BIMBINGAN=?,TGL_AKHIR_BIMBINGAN=? where THSMS=? and NPMHS=? and STMHS=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				String seperator = Checker.getSeperatorYgDigunakan(brs);
    				StringTokenizer st = new StringTokenizer(brs,seperator);
    				String npmhs = st.nextToken();
    				String kdpst = st.nextToken();
    				String idkur = st.nextToken();
    				String tglaju = st.nextToken();
    				String tglls = st.nextToken();
    				String idkmk = st.nextToken();
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String thsms_pertama = st.nextToken();
    				String tgl_awal_bimbingan = st.nextToken();
    				String tgl_akhir_bimbingan = st.nextToken();
    				stmt.setDate(1, java.sql.Date.valueOf(tgl_awal_bimbingan));
    				stmt.setDate(2, java.sql.Date.valueOf(tgl_akhir_bimbingan));
    				stmt.setString(3, thsms);
    				stmt.setString(4, npmhs);
    				stmt.setString(5, "L");
    				updated = updated+stmt.executeUpdate();
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
    	return updated;
    }
    
    public int setJudulLulusan(String thsms, String tkn_kdpst_optional) {
    	int updated=0;
    	Vector v = null;
    	ListIterator li = null;
    	try {
			Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		if(Checker.isStringNullOrEmpty(tkn_kdpst_optional)) {
    			stmt = con.prepareStatement("select NPMHSMSMHS,KDPSTMSMHS from TRLSM inner join EXT_CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and STMHS=?");
    			stmt.setString(1, thsms);
    			stmt.setString(2, "L");
    		}
    		else {
    			String seperator = Checker.getSeperatorYgDigunakan(tkn_kdpst_optional);
    			StringTokenizer st = new StringTokenizer(tkn_kdpst_optional,seperator);
    			String tmp = "";
    			if(st.hasMoreTokens()) {
    				tmp = new String("KDPST='"+st.nextToken()+"'");
    				while(st.hasMoreTokens()) {
    					tmp = tmp + "or KDPST='"+st.nextToken()+"'";
    				}
    			}
    			stmt = con.prepareStatement("select NPMHSMSMHS,KDPSTMSMHS from TRLSM inner join EXT_CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? and STMHS=? and ("+tmp+")");
    			stmt.setString(1, thsms);
    			stmt.setString(2, "L");
    			
    		}
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				String npmhs = rs.getString(1);
    				String kdpst = rs.getString(2);
    				
    				//System.out.println("tglls~"+tglls);
    				
    				li.add(npmhs+"~"+kdpst);
    				//System.out.println(npmhs+"~"+kdpst+"~"+idkur);
    			}
    			while(rs.next());
    		}
    		if(v!=null) {
    			//cari judul
    			stmt = con.prepareStatement("SELECT JUDUL FROM RIWAYAT_KARYA_ILMIAH where NPMHS=? order by THSMS desc limit 1;");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				String seperator = Checker.getSeperatorYgDigunakan(brs);
    				StringTokenizer st = new StringTokenizer(brs,seperator);
    				String npmhs = st.nextToken();
    				String kdpst = st.nextToken();
    				
    				stmt.setString(1, npmhs);
    				rs = stmt.executeQuery();;
    				if(rs.next()) {
    					String judul = rs.getString(1);
    					if(!Checker.isStringNullOrEmpty(judul) ) {
    						li.set(brs+seperator+judul);
    					}
    					else {
    						li.remove();
    					}
    				}
    				else {
    					li.remove();
    				}
    			}
    		}

    		//update trlsm
    		if(v!=null) {
    			//cari mk akhir
    			stmt = con.prepareStatement("update TRLSM set JUDUL=? where THSMS=? and NPMHS=? and STMHS=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				String seperator = Checker.getSeperatorYgDigunakan(brs);
    				StringTokenizer st = new StringTokenizer(brs,seperator);
    				String npmhs = st.nextToken();
    				String kdpst = st.nextToken();
    				String judul = st.nextToken();
    				
    				stmt.setString(1, judul);
    				stmt.setString(2, thsms);
    				stmt.setString(3, npmhs);
    				stmt.setString(4, "L");
    				updated = updated+stmt.executeUpdate();
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
    	return updated;
    }
    
    /*
     * deprecated
     */
    public int updLulusanBerdasarExcelWisudawan(Vector v_list) {
    	int upd = 0;
    	StringTokenizer st = null, st1 = null;
    	ListIterator li = null;
    	
    	try {
    		if(v_list!=null && v_list.size()>0) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
            	li = v_list.listIterator();
            	//1.  get NPMHS
            	//stmt = con.prepareStatement("update TRLSM set STMHS=? where THSMS=? and NPMHS=?");
        		stmt = con.prepareStatement("select NPMHSMSMHS from CIVITAS where NIMHSMSMHS=?");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			//System.out.print(brs);
        			String seperator = Checker.getSeperatorYgDigunakan(brs);
        			st = new StringTokenizer(brs,seperator);
        			String norut = st.nextToken();
        			String thsms = st.nextToken();
        			while(thsms.contains("'")) {
        				thsms = thsms.replace("'", "");
        			}
        			thsms = thsms.substring(0,5);
        			String kdpti = st.nextToken();
        			while(kdpti.contains("'")) {
        				kdpti = kdpti.replace("'", "");
        			}
        			kdpti = kdpti.substring(0,6);
        			String kdpst = st.nextToken();
        			while(kdpst.contains("'")) {
        				kdpst = kdpst.replace("'", "");
        			}
        			kdpst = kdpst.substring(0,5);
        			if(kdpst.equalsIgnoreCase("86208")) {
        				kdpst="88888";
        			}
        			String nimhs = st.nextToken();
        			while(nimhs.contains("'")) {
        				nimhs = nimhs.replace("'", "");
        			}
        			String nmmhs = st.nextToken();
        			String skstt = st.nextToken();
        			while(skstt.contains("'")) {
        				skstt = skstt.replace("'", "");
        			}
        			String tglls = st.nextToken();
        			while(tglls.contains("'")) {
        				tglls = tglls.replace("'", "");
        			}
        			String tglwis = st.nextToken();
        			while(tglwis.contains("'")) {
        				tglwis = tglwis.replace("'", "");
        			}
        			
        			stmt.setString(1, nimhs);
        			rs = stmt.executeQuery();
        			rs.next();
        			String npmhs = rs.getString(1);
        			//     norut,    thsms`    kdpti`    kdpst`    nimhs`   nmmhs`    skstt`    tglls`    tglwis `tplhr `tglhr`
        			//NOMOR POKOK MAHASISWA (NPM)`NIRL`SK REKTOR`JUDUL
        			li.set(norut+"`"+thsms+"`"+kdpti+"`"+kdpst+"`"+nimhs+"`"+nmmhs+"`"+skstt+"`"+tglls+"`"+tglwis+"`"+npmhs);
        		}
        		
        		
            	//2.  UPDATE
        		li = v_list.listIterator();
            	stmt = con.prepareStatement("update TRLSM set STMHS=?,TGLLS=?,SKSTT=?,TGL_WISUDA=? where THSMS=? and NPMHS=?");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			//System.out.print("update "+brs);
        			String seperator = Checker.getSeperatorYgDigunakan(brs);
        			st = new StringTokenizer(brs,seperator);
        			String norut = st.nextToken();
        			String thsms = st.nextToken();
        			thsms = thsms.substring(0,5);
        			String kdpti = st.nextToken();
        			kdpti = kdpti.substring(0,6);
        			String kdpst = st.nextToken();
        			kdpst = kdpst.substring(0,5);
        			if(kdpst.equalsIgnoreCase("86208")) {
        				kdpst="88888";
        			}
        			String nimhs = st.nextToken();
        			String nmmhs = st.nextToken();
        			while(nimhs.contains("'")) {
        				nimhs = nimhs.replace("'", "");
        			}
        			String skstt = st.nextToken();
        			String tglls = st.nextToken();
        			if(!Checker.isStringNullOrEmpty(tglls)) { //pake seprator /
        				seperator = Checker.getSeperatorYgDigunakan(tglls);
        				st1 = new StringTokenizer(tglls,seperator);
        				String tgl = st1.nextToken();
        				String bln = st1.nextToken();
        				String thn = st1.nextToken();
        				tglls = thn+"-"+bln+"-"+tgl;
        			}
        			
        			String tglwis = st.nextToken();
        			if(!Checker.isStringNullOrEmpty(tglwis)) { //pake seprator /
        				seperator = Checker.getSeperatorYgDigunakan(tglwis);
        				st1 = new StringTokenizer(tglwis,seperator);
        				String tgl = st1.nextToken();
        				String bln = st1.nextToken();
        				String thn = st1.nextToken();
        				tglwis = thn+"-"+bln+"-"+tgl;
        			}
        			String npmhs = st.nextToken();
        			
        			
        			stmt.setString(1, "L");
        			if(Checker.isStringNullOrEmpty(tglls)) {
        				stmt.setNull(2, java.sql.Types.DATE);
        			}
        			else {
        				stmt.setDate(2, java.sql.Date.valueOf(tglls));	
        			}
        			try {
        				stmt.setInt(3, Integer.parseInt(skstt));	
        			}
        			catch(Exception e) {
        				stmt.setInt(3, 0);
        			}
        			
        			if(Checker.isStringNullOrEmpty(tglwis)) {
        				stmt.setNull(4, java.sql.Types.DATE);
        			}
        			else {
        				stmt.setDate(4, java.sql.Date.valueOf(tglwis));	
        			}
        			stmt.setString(5, thsms);
        			stmt.setString(6, npmhs);
        			int i = stmt.executeUpdate();
        			//System.out.println(" = "+i);
        			if(i>0) {
        				upd++;
        				li.remove();
        			}
        		}
        		
        		//2.  INSERT SISANYA
        		li = v_list.listIterator();
            	stmt = con.prepareStatement("insert ignore into TRLSM(THSMS,KDPST,NPMHS,STMHS,TGLLS,SKSTT,TGL_WISUDA)values(?,?,?,?,?,?,?)");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			//System.out.print("insert "+brs);
        			String seperator = Checker.getSeperatorYgDigunakan(brs);
        			st = new StringTokenizer(brs,seperator);
        			String norut = st.nextToken();
        			String thsms = st.nextToken();
        			thsms = thsms.substring(0,5);
        			String kdpti = st.nextToken();
        			kdpti = kdpti.substring(0,6);
        			String kdpst = st.nextToken();
        			kdpst = kdpst.substring(0,5);
        			if(kdpst.equalsIgnoreCase("86208")) {
        				kdpst="88888";
        			}
        			String nimhs = st.nextToken();
        			while(nimhs.contains("'")) {
        				nimhs = nimhs.replace("'", "");
        			}
        			String nmmhs = st.nextToken();
        			String skstt = st.nextToken();
        			String tglls = st.nextToken();
        			if(!Checker.isStringNullOrEmpty(tglls)) { //pake seprator /
        				seperator = Checker.getSeperatorYgDigunakan(tglls);
        				st1 = new StringTokenizer(tglls,seperator);
        				String tgl = st1.nextToken();
        				String bln = st1.nextToken();
        				String thn = st1.nextToken();
        				tglls = thn+"-"+bln+"-"+tgl;
        			}
        			
        			String tglwis = st.nextToken();
        			if(!Checker.isStringNullOrEmpty(tglwis)) { //pake seprator /
        				seperator = Checker.getSeperatorYgDigunakan(tglwis);
        				st1 = new StringTokenizer(tglwis,seperator);
        				String tgl = st1.nextToken();
        				String bln = st1.nextToken();
        				String thn = st1.nextToken();
        				tglwis = thn+"-"+bln+"-"+tgl;
        			}
        			String npmhs = st.nextToken();
        			
        			stmt.setString(1, thsms);
        			stmt.setString(2, kdpst);
        			stmt.setString(3, npmhs);
        			stmt.setString(4, "L");
        			if(Checker.isStringNullOrEmpty(tglls)) {
        				stmt.setNull(5, java.sql.Types.DATE);
        			}
        			else {
        				stmt.setDate(5, java.sql.Date.valueOf(tglls));	
        			}
        			
        			try {
        				stmt.setInt(6, Integer.parseInt(skstt));	
        			}
        			catch(Exception e) {
        				stmt.setInt(6, 0);
        			}
        			
        			if(Checker.isStringNullOrEmpty(tglwis)) {
        				stmt.setNull(7, java.sql.Types.DATE);
        			}
        			else {
        				stmt.setDate(7, java.sql.Date.valueOf(tglwis));	
        			}
        			
        			
        			int i = stmt.executeUpdate();
        			//System.out.println(" = "+i);
        			if(i>0) {
        				upd++;
        				li.remove();
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
		catch (Exception ex) {
			ex.printStackTrace();
		} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	return upd;
    }
    
    public int updLulusanBerdasarExcelWisudawan_v1(Vector v_list, boolean editor_ija) {
    	int upd = 0;
    	StringTokenizer st = null, st1 = null;
    	ListIterator li = null;
    	//System.out.println("editor_ija="+editor_ija);
    	try {
    		if(v_list!=null && v_list.size()>0) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//2.  UPDATE
        		li = v_list.listIterator();
        		if(editor_ija) {
        			stmt = con.prepareStatement("update TRLSM set NOIJA=?,NIRL=?,TGL_TERBIT_IJA=? where THSMS=? and NPMHS=?");
        		}
        		else {
        			stmt = con.prepareStatement("update TRLSM set TGLLS=?,SKSTT=?,TGL_WISUDA=?,NOSKR=?,TGLRE=?,JUDUL=? where THSMS=? and NPMHS=?");
        		}
            	//stmt = con.prepareStatement("update TRLSM set STMHS=?,TGLLS=?,SKSTT=?,TGL_WISUDA=?,NOSKR=?,TGLRE=?,NOIJA=?,JUDUL=?,NIRL=?,TGL_TERBIT_IJA=? where THSMS=? and NPMHS=?");
        		while(li.hasNext()) {
        		//if(li.hasNext()) {	
        			String brs = (String)li.next();
        			
//        		     thsms`    kdpti`    kdpst`    nimhs`   nmmhs`    skstt`    tglls`    tglwis 
        			
        			//System.out.print("update "+brs);
        			String seperator = Checker.getSeperatorYgDigunakan(brs);
        			st = new StringTokenizer(brs,seperator);
        			//String norut = st.nextToken();  nnga ada norut
        			String thsms = st.nextToken();
        			while(thsms.contains("'")) {
        				thsms = thsms.replace("'", "");
        			}
        			thsms = thsms.substring(0,5);
        			String kdpti = st.nextToken();
        			while(kdpti.contains("'")) {
        				kdpti = kdpti.replace("'", "");
        			}
        			
        			kdpti = kdpti.substring(0,6);
        			String kdpst = st.nextToken();
        			while(kdpst.contains("'")) {
        				kdpst = kdpst.replace("'", "");
        			}
        			kdpst = kdpst.substring(0,5);
        			if(kdpst.equalsIgnoreCase("86208")) {
        				kdpst="88888";
        			}
        			String nimhs = st.nextToken();
        			while(nimhs.contains("'")) {
        				nimhs = nimhs.replace("'", "");
        			}
        			String nmmhs = st.nextToken();
        			
        			String skstt = st.nextToken();
        			while(skstt.contains("'")) {
        				skstt = skstt.replace("'", "");
        			}
        			//System.out.println("skstt0="+skstt);
        			String tglls = st.nextToken();
        			while(tglls.contains("'")) {
        				tglls = tglls.replace("'", "");
        			}
        			if(!Checker.isStringNullOrEmpty(tglls)) { //pake seprator /
        				seperator = Checker.getSeperatorYgDigunakan(tglls);
        				st1 = new StringTokenizer(tglls,seperator);
        				String tgl = st1.nextToken();
        				String bln = st1.nextToken();
        				String thn = st1.nextToken();
        				tglls = thn+"-"+bln+"-"+tgl;
        			}
        			
        			String tglwis = st.nextToken();
        			while(tglwis.contains("'")) {
        				tglwis = tglwis.replace("'", "");
        			}
        			if(!Checker.isStringNullOrEmpty(tglwis)) { //pake seprator /
        				seperator = Checker.getSeperatorYgDigunakan(tglwis);
        				st1 = new StringTokenizer(tglwis,seperator);
        				String tgl = st1.nextToken();
        				String bln = st1.nextToken();
        				String thn = st1.nextToken();
        				tglwis = thn+"-"+bln+"-"+tgl;
        			}
        			//`tplhr
        			String tplhr = st.nextToken();
        			while(tplhr.contains("'")) {
        				tplhr = tplhr.replace("'", "");
        			}
        			//tglhr
        			String tglhr = st.nextToken();
        			while(tglhr.contains("'")) {
        				tglhr = tglhr.replace("'", "");
        			}
        			if(!Checker.isStringNullOrEmpty(tglhr)) { //pake seprator /
        				seperator = Checker.getSeperatorYgDigunakan(tglhr);
        				st1 = new StringTokenizer(tglhr,seperator);
        				String tgl = st1.nextToken();
        				String bln = st1.nextToken();
        				String thn = st1.nextToken();
        				tglhr = thn+"-"+bln+"-"+tgl;
        			}
        			//npmhs
        			String npmhs = st.nextToken();
        			while(npmhs.contains("'")) {
        				npmhs = npmhs.replace("'", "");
        			}
        			//`NIRL
        			String nirl = st.nextToken();
        			while(nirl.contains("'")) {
        				nirl = nirl.replace("'", "");
        			}
        			//tglskr
        			String tglskr = st.nextToken();
        			while(tglskr.contains("'")) {
        				tglskr = tglskr.replace("'", "");
        			}
        			if(!Checker.isStringNullOrEmpty(tglskr)) { //pake seprator /
        				seperator = Checker.getSeperatorYgDigunakan(tglskr);
        				st1 = new StringTokenizer(tglskr,seperator);
        				String tgl = st1.nextToken();
        				String bln = st1.nextToken();
        				String thn = st1.nextToken();
        				tglskr = thn+"-"+bln+"-"+tgl;
        			}
        			//`SK REKTOR`
        			String noskr = st.nextToken();
        			while(noskr.contains("'")) {
        				noskr = noskr.replace("'", "");
        			}
        			//JUDUL
        			String judul = st.nextToken();
        			while(judul.contains("'")) {
        				judul = judul.replace("'", "");
        			}
        			//noja
        			String noija = st.nextToken();
        			while(noija.contains("'")) {
        				noija = noija.replace("'", "");
        			}
        			//tgl terbit
        			String tglcetak = st.nextToken();
        			while(tglcetak.contains("'")) {
        				tglcetak = tglcetak.replace("'", "");
        			}
        			if(!Checker.isStringNullOrEmpty(tglcetak)) { //pake seprator /
        				seperator = Checker.getSeperatorYgDigunakan(tglcetak);
        				st1 = new StringTokenizer(tglcetak,seperator);
        				String tgl = st1.nextToken();
        				String bln = st1.nextToken();
        				String thn = st1.nextToken();
        				tglcetak = thn+"-"+bln+"-"+tgl;
        			}
        			
        			if(editor_ija) {
            			stmt = con.prepareStatement("update TRLSM set STMHS=?,NOIJA=?,NIRL=?,TGL_TERBIT_IJA=? where THSMS=? and NPMHS=?");
            			//stmhs
            			stmt.setString(1, "L");
            			//noija
            			if(Checker.isStringNullOrEmpty(noija)) {
            				stmt.setNull(2, java.sql.Types.VARCHAR);
            			}
            			else {
            				stmt.setString(2, noija);	
            			}
            			//nirl
            			if(Checker.isStringNullOrEmpty(nirl)) {
            				stmt.setNull(3, java.sql.Types.VARCHAR);
            			}
            			else {
            				stmt.setString(3, nirl);	
            			}
            			//tglcetak
            			if(Checker.isStringNullOrEmpty(tglcetak)) {
            				stmt.setNull(4, java.sql.Types.DATE);
            			}
            			else {
            				stmt.setDate(4, java.sql.Date.valueOf(tglcetak));	
            			}
            			
            			stmt.setString(5, thsms);
            			stmt.setString(6, npmhs);
            			int i = stmt.executeUpdate();
            			//System.out.println(" = "+i);
            			if(i>0) {
            				upd++;
            				li.remove();
            			}
        			
        			}
            		else {
            			stmt = con.prepareStatement("update TRLSM set STMHS=?,TGLLS=?,SKSTT=?,TGL_WISUDA=?,NOSKR=?,TGLRE=?,JUDUL=? where THSMS=? and NPMHS=?");
            			stmt.setString(1, "L");
            			//tglls
            			if(Checker.isStringNullOrEmpty(tglls)) {
            				stmt.setNull(2, java.sql.Types.DATE);
            			}
            			else {
            				stmt.setDate(2, java.sql.Date.valueOf(tglls));	
            			}
            			//skstt
            			try {
            				if(skstt.contains(".")) {
            					skstt = skstt.substring(0,skstt.indexOf("."));
            				}
            				stmt.setInt(3, Integer.parseInt(skstt));	
            			}
            			catch(Exception e) {
            				stmt.setInt(3, 0);
            			}
            			//System.out.println("skstt="+skstt);
            			if(Checker.isStringNullOrEmpty(tglwis)) {
            				stmt.setNull(4, java.sql.Types.DATE);
            			}
            			else {
            				stmt.setDate(4, java.sql.Date.valueOf(tglwis));	
            			}
            			//noskr
            			if(Checker.isStringNullOrEmpty(noskr)) {
            				stmt.setNull(5, java.sql.Types.VARCHAR);
            			}
            			else {
            				stmt.setString(5, noskr);	
            			}
            			//tglskr
            			if(Checker.isStringNullOrEmpty(tglskr)) {
            				stmt.setNull(6, java.sql.Types.DATE);
            			}
            			else {
            				stmt.setDate(6, java.sql.Date.valueOf(tglskr));	
            			}
            			//judul
            			if(Checker.isStringNullOrEmpty(judul)) {
            				stmt.setNull(7, java.sql.Types.VARCHAR);
            			}
            			else {
            				stmt.setString(7, judul);	
            			}
            			stmt.setString(8, thsms);
            			stmt.setString(9, npmhs);
            			int i = stmt.executeUpdate();
            			//System.out.println(" = "+i);
            			if(i>0) {
            				upd++;
            				li.remove();
            			}
            		}
        			
        		}
        		
        		//2.  INSERT SISANYA
        		li = v_list.listIterator();
        		while(li.hasNext()) {
        		//if(li.hasNext()) {
        			String brs = (String)li.next();
//       		      thsms`    kdpti`    kdpst`    nimhs`   nmmhs`    skstt`    tglls`    tglwis 
       			
        			//System.out.print("insert "+brs);
        			String seperator = Checker.getSeperatorYgDigunakan(brs);
        			st = new StringTokenizer(brs,seperator);
        			//String norut = st.nextToken();
        			String thsms = st.nextToken();
        			while(thsms.contains("'")) {
        				thsms = thsms.replace("'", "");
        			}
        			thsms = thsms.substring(0,5);
        			String kdpti = st.nextToken();
        			while(kdpti.contains("'")) {
        				kdpti = kdpti.replace("'", "");
        			}
        			kdpti = kdpti.substring(0,6);
        			String kdpst = st.nextToken();
        			while(kdpst.contains("'")) {
        				kdpst = kdpst.replace("'", "");
        			}
        			kdpst = kdpst.substring(0,5);
        			if(kdpst.equalsIgnoreCase("86208")) {
        				kdpst="88888";
        			}
        			String nimhs = st.nextToken();
        			while(nimhs.contains("'")) {
        				nimhs = nimhs.replace("'", "");
        			}
        			String nmmhs = st.nextToken();
        			
        			String skstt = st.nextToken();
        			//skstt
        			try {
        				if(skstt.contains(".")) {
        					skstt = skstt.substring(0,skstt.indexOf("."));
        				}	
        			}
        			catch(Exception e) {
        				skstt="0";
        			}
        			while(nimhs.contains("'")) {
        				nimhs = nimhs.replace("'", "");
        			}
        			String tglls = st.nextToken();
        			while(tglls.contains("'")) {
        				tglls = tglls.replace("'", "");
        			}
        			if(!Checker.isStringNullOrEmpty(tglls)) { //pake seprator /
        				seperator = Checker.getSeperatorYgDigunakan(tglls);
        				st1 = new StringTokenizer(tglls,seperator);
        				String tgl = st1.nextToken();
        				String bln = st1.nextToken();
        				String thn = st1.nextToken();
        				tglls = thn+"-"+bln+"-"+tgl;
        			}
       			
        			String tglwis = st.nextToken();
        			while(tglwis.contains("'")) {
        				tglwis = tglwis.replace("'", "");
        			}
        			if(!Checker.isStringNullOrEmpty(tglwis)) { //pake seprator /
        				seperator = Checker.getSeperatorYgDigunakan(tglwis);
        				st1 = new StringTokenizer(tglwis,seperator);
        				String tgl = st1.nextToken();
        				String bln = st1.nextToken();
        				String thn = st1.nextToken();
        				tglwis = thn+"-"+bln+"-"+tgl;
        			}
        			//`tplhr
        			String tplhr = st.nextToken();
        			while(tplhr.contains("'")) {
        				tplhr = tplhr.replace("'", "");
        			}
        			//tglhr
        			String tglhr = st.nextToken();
        			while(tglhr.contains("'")) {
        				tglhr = tglhr.replace("'", "");
        			}
        			if(!Checker.isStringNullOrEmpty(tglhr)) { //pake seprator /
        				seperator = Checker.getSeperatorYgDigunakan(tglhr);
        				st1 = new StringTokenizer(tglhr,seperator);
        				String tgl = st1.nextToken();
        				String bln = st1.nextToken();
        				String thn = st1.nextToken();
        				tglhr = thn+"-"+bln+"-"+tgl;
        			}
        			//npmhs
        			String npmhs = st.nextToken();
        			while(npmhs.contains("'")) {
        				npmhs = npmhs.replace("'", "");
        			}
        			//`NIRL
        			String nirl = st.nextToken();
        			while(nirl.contains("'")) {
        				nirl = nirl.replace("'", "");
        			}
        			//tglskr
        			String tglskr = st.nextToken();
        			while(tglskr.contains("'")) {
        				tglskr = tglskr.replace("'", "");
        			}
        			if(!Checker.isStringNullOrEmpty(tglskr)) { //pake seprator /
        				seperator = Checker.getSeperatorYgDigunakan(tglskr);
        				st1 = new StringTokenizer(tglskr,seperator);
        				String tgl = st1.nextToken();
        				String bln = st1.nextToken();
        				String thn = st1.nextToken();
        				tglskr = thn+"-"+bln+"-"+tgl;
        			}
        			//`SK REKTOR`
        			String noskr = st.nextToken();
        			while(noskr.contains("'")) {
        				noskr = noskr.replace("'", "");
        			}
        			//JUDUL
        			String judul = st.nextToken();
        			while(judul.contains("'")) {
        				judul = judul.replace("'", "");
        			}
        			//noja
        			String noija = st.nextToken();
        			while(noija.contains("'")) {
        				noija = noija.replace("'", "");
        			}
        			//tgl terbit
        			String tglcetak = st.nextToken();
        			while(tglcetak.contains("'")) {
        				tglcetak = tglcetak.replace("'", "");
        			}
        			if(!Checker.isStringNullOrEmpty(tglcetak)) { //pake seprator /
        				seperator = Checker.getSeperatorYgDigunakan(tglcetak);
        				st1 = new StringTokenizer(tglcetak,seperator);
        				String tgl = st1.nextToken();
        				String bln = st1.nextToken();
        				String thn = st1.nextToken();
        				tglcetak = thn+"-"+bln+"-"+tgl;
        			}
        			
        			if(editor_ija) {
            			stmt = con.prepareStatement("insert ignore into TRLSM(THSMS,KDPST,NPMHS,STMHS,NOIJA,NIRL,TGL_TERBIT_IJA)values(?,?,?,?,?,?,?)");
            			stmt.setString(1, thsms);
            			stmt.setString(2, kdpst);
            			stmt.setString(3, npmhs);
            			stmt.setString(4, "L");
            			if(Checker.isStringNullOrEmpty(noija)) {
            				stmt.setNull(5, java.sql.Types.VARCHAR);
            			}
            			else {
            				stmt.setString(5, noija);
            			}
            			if(Checker.isStringNullOrEmpty(nirl)) {
            				stmt.setNull(6, java.sql.Types.VARCHAR);
            			}
            			else {
            				stmt.setString(6, nirl);
            			}
            			if(Checker.isStringNullOrEmpty(tglcetak)) {
            				stmt.setNull(7, java.sql.Types.DATE);
            			}
            			else {
            				stmt.setDate(7, java.sql.Date.valueOf(tglcetak));
            			}
            		}
            		else {
            			stmt = con.prepareStatement("insert ignore into TRLSM(THSMS,KDPST,NPMHS,STMHS,TGLLS,SKSTT,TGL_WISUDA,NOSKR,TGLRE,JUDUL)values(?,?,?,?,?,?,?,?,?,?)");
            			stmt.setString(1, thsms);
            			stmt.setString(2, kdpst);
            			stmt.setString(3, npmhs);
            			stmt.setString(4, "L");
            			if(Checker.isStringNullOrEmpty(tglls)) {
            				stmt.setNull(5, java.sql.Types.DATE);
            			}
            			else {
            				stmt.setDate(5, java.sql.Date.valueOf(tglls));	
            			}
            			
            			try {
            				stmt.setInt(6, Integer.parseInt(skstt));	
            			}
            			catch(Exception e) {
            				stmt.setInt(6, 0);
            			}
            			
            			if(Checker.isStringNullOrEmpty(tglwis)) {
            				stmt.setNull(7, java.sql.Types.DATE);
            			}
            			else {
            				stmt.setDate(7, java.sql.Date.valueOf(tglwis));	
            			}
            			//noskr
            			if(Checker.isStringNullOrEmpty(noskr)) {
            				stmt.setNull(8, java.sql.Types.VARCHAR);
            			}
            			else {
            				stmt.setString(8, noskr);	
            			}
            			//tglskr
            			if(Checker.isStringNullOrEmpty(tglskr)) {
            				stmt.setNull(9, java.sql.Types.DATE);
            			}
            			else {
            				stmt.setDate(9, java.sql.Date.valueOf(tglskr));	
            			}
            			//judul
            			if(Checker.isStringNullOrEmpty(judul)) {
            				stmt.setNull(10, java.sql.Types.VARCHAR);
            			}
            			else {
            				stmt.setString(10, judul);	
            			}
            		}
                	
            		
        			
        			
        			
        			
        			int i = stmt.executeUpdate();
        			//System.out.println(" = "+i);
        			if(i>0) {
        				upd++;
        				li.remove();
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
		catch (Exception ex) {
			ex.printStackTrace();
		} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	return upd;
    }
  
    public int updWaktuKelulusan(String npmhs, String thsms_pengajuan, String tglls) {
    	int upd = 0;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	Vector v = null;
    	java.sql.Date dt_lls = null;
    	boolean valid=true;
    	try {
    		dt_lls = java.sql.Date.valueOf(Converter.autoConvertDateFormat(tglls, "-"));
    	}
    	catch(Exception e) {
    		valid=false;
    	}
    	if(valid) {
    		try {
        		
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//update topik_pengajuan
        		String sql = "update TOPIK_PENGAJUAN set TARGET_THSMS_PENGAJUAN='"+thsms_pengajuan+"',ISI_TOPIK_PENGAJUAN=? where TIPE_PENGAJUAN='KELULUSAN' and CREATOR_NPM='"+npmhs+"' and APPROVED is not null and BATAL=false";
        		stmt = con.prepareStatement(sql);
        		tglls = Converter.autoConvertDateFormat(tglls, "/");
        		stmt.setString(1, tglls);
        		upd = stmt.executeUpdate();
        		if(upd>0) {
        			//update TRLSM
        			sql = "update TRLSM set THSMS='"+thsms_pengajuan+"',NOTE=?,TGLLS=? where NPMHS='"+npmhs+"' and STMHS='L'";
        			stmt = con.prepareStatement(sql);
            		tglls = Converter.autoConvertDateFormat(tglls, "/");
            		stmt.setString(1, tglls);
            		stmt.setDate(2, dt_lls);
            		upd=0;
            		upd = stmt.executeUpdate();
            		if(upd>0) {
            			sql = "update CIVITAS set STMHSMSMHS='L',TGLLSMSMHS=? where NPMHSMSMHS='"+npmhs+"'";
            			stmt = con.prepareStatement(sql);
                		stmt.setDate(1, dt_lls);
                		upd=0;
                		upd = stmt.executeUpdate();
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
    	return upd;
    }
    
 }

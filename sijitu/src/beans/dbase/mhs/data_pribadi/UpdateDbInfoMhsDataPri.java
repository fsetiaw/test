package beans.dbase.mhs.data_pribadi;

import beans.dbase.mhs.UpdateDbInfoMhs;
import beans.tools.Checker;
import beans.tools.Converter;
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

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class UpdateDbInfoMhsDataPri
 */
@Stateless
@LocalBean
public class UpdateDbInfoMhsDataPri extends UpdateDbInfoMhs {
	String operatorNpm;
	String tknOperatorNickname;
	String operatorNmm;
	boolean petugas;
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds; 
    /**
     * @see UpdateDbInfoMhs#UpdateDbInfoMhs()
     */
    public UpdateDbInfoMhsDataPri() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDbInfoMhs#UpdateDbInfoMhs(String)
     */
    public UpdateDbInfoMhsDataPri(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.tknOperatorNickname = getTknOprNickname();
    	//System.out.println("tknOperatorNickname1="+this.tknOperatorNickname);
    	this.petugas = cekApaUsrPetugas();
        // TODO Auto-generated constructor stub
    }

    public void updateDataPribadi(String npmhs, String info) {
    	//info = nmmhs+"`"+tplhr+"`"+tglhr+"`"+kdjek+"`"+nisn+"`"+warganegara+"`"+niktp+"`"+nosim+"`"+paspo+"`"+angel+"`"+sttus+"`"+email+"`"+nohpe+"`"+almrm+"`"+no_rt+"`"+no_rw+"`"+prorm+"`"+proid+"`"+kotrm+"`"+kotid+"`"+kecrm+"`"+kecid+"`"+kelrm+"`"+dusun+"`"+posrm+"`"+telrm+"`"+nglhr+"`"+agama;
    	if(!Checker.isStringNullOrEmpty(info)) {
    		StringTokenizer st = new StringTokenizer(info,"`");
    		String nmmhs=st.nextToken();
    		String tplhr=st.nextToken();
    		String tglhr=st.nextToken();
    		String kdjek=st.nextToken();
    		String nisn=st.nextToken();
    		String warganegara=st.nextToken();
    		String niktp=st.nextToken();
    		String nosim=st.nextToken();
    		String paspo=st.nextToken();
    		String angel=st.nextToken();
    		//ext
    		String sttus=st.nextToken();
    		String email=st.nextToken();
    		String nohpe=st.nextToken();
    		String almrm=st.nextToken();
    		String no_rt=st.nextToken();
    		String no_rw=st.nextToken();
    		String prorm=st.nextToken();
    		String proid=st.nextToken();
    		String kotrm=st.nextToken();
    		String kotid=st.nextToken();
    		String kecrm=st.nextToken();
    		String kecid=st.nextToken();
    		String kelrm=st.nextToken();
    		String dusun=st.nextToken();
    		String posrm=st.nextToken();
    		String telrm=st.nextToken();
    		String nglhr=st.nextToken();
    		String agama=st.nextToken();
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("update CIVITAS set NMMHSMSMHS=?,TPLHRMSMHS=?,TGLHRMSMHS=?,KDJEKMSMHS=?,NISNMSMHS=?,KEWARGANEGARAAN=?,NIKTPMSMHS=?,NOSIMMSMHS=?,PASPORMSMHS=? where NPMHSMSMHS=?");
        		int i=1;
        		stmt.setString(i++, nmmhs);
        		if(Checker.isStringNullOrEmpty(tplhr)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, tplhr);
        		}
        		
        		if(Checker.isStringNullOrEmpty(tglhr)) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(tglhr));
        		}
        		if(Checker.isStringNullOrEmpty(kdjek)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, kdjek);
        		}
        		
        		if(Checker.isStringNullOrEmpty(nisn)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, nisn);
        		}
        		if(Checker.isStringNullOrEmpty(warganegara)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, warganegara);
        		}
        		if(Checker.isStringNullOrEmpty(niktp)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, niktp);
        		}
        		if(Checker.isStringNullOrEmpty(nosim)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, nosim);
        		}
        		if(Checker.isStringNullOrEmpty(paspo)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, paspo);
        		}
        		stmt.setString(i++, npmhs);
        		
        		i = stmt.executeUpdate();
        		//System.out.println("execute1="+i);
        		i=1;
        		//ext civitas
        		stmt = con.prepareStatement("UPDATE EXT_CIVITAS SET STTUSMSMHS=?,EMAILMSMHS=?,NOHPEMSMHS=?,ALMRMMSMHS=?,NRTRMMSMHS=?,NRWRMMSMHS=?,PROVRMMSMHS=?,PROVRMIDWIL=?,KOTRMMSMHS=?,KOTRMIDWIL=?,KECRMMSMHS=?,KECRMIDWIL=?,KELRMMSMHS=?,DUSUNMSMHS=?,POSRMMSMHS=?,TELRMMSMHS=?,NEGLHMSMHS=?,AGAMAMSMHS=? WHERE NPMHSMSMHS=?");
        		//STTUSMSMHS=?,
        		if(Checker.isStringNullOrEmpty(sttus)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, sttus);
        		}
        		//EMAILMSMHS=?,
        		if(Checker.isStringNullOrEmpty(email)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, email);
        		}
        		//NOHPEMSMHS=?,
        		if(Checker.isStringNullOrEmpty(nohpe)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, nohpe);
        		}
        		//ALMRMMSMHS=?,
        		if(Checker.isStringNullOrEmpty(almrm)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, almrm);
        		}
        		//NRTRMMSMHS=?,
        		if(Checker.isStringNullOrEmpty(no_rt)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, no_rt);
        		}
        		//NRWRMMSMHS=?,
        		if(Checker.isStringNullOrEmpty(no_rw)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, no_rw);
        		}
        		//PROVRMMSMHS=?,
        		if(Checker.isStringNullOrEmpty(prorm)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, prorm);
        		}
        		//PROVRMIDWIL=?,
        		if(Checker.isStringNullOrEmpty(proid)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, proid);
        		}
        		//KOTRMMSMHS=?,
        		if(Checker.isStringNullOrEmpty(kotrm)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, kotrm);
        		}
        		//KOTRMIDWIL=?,
        		if(Checker.isStringNullOrEmpty(kotid)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, kotid);
        		}
        		//KECRMMSMHS=?,
        		if(Checker.isStringNullOrEmpty(kecrm)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, kecrm);
        		}
        		//KECRMIDWIL=?,
        		if(Checker.isStringNullOrEmpty(kecid)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, kecid);
        		}
        		//KELRMMSMHS=?,
        		if(Checker.isStringNullOrEmpty(kelrm)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, kelrm);
        		}
        		//DUSUNMSMHS=?,
        		if(Checker.isStringNullOrEmpty(dusun)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, dusun);
        		}
        		//POSRMMSMHS=?,
        		if(Checker.isStringNullOrEmpty(posrm)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, posrm);
        		}
        		//TELRMMSMHS=?,
        		if(Checker.isStringNullOrEmpty(telrm)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, telrm);
        		}
        		//NEGLHMSMHS=?,
        		if(Checker.isStringNullOrEmpty(nglhr)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, nglhr);
        		}
        		//AGAMAMSMHS=?
        		if(Checker.isStringNullOrEmpty(agama)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, agama);
        		}
        		
        		stmt.setString(i++, npmhs);
        		
        		i = stmt.executeUpdate();
        		//System.out.println("execute2="+i);
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
    
    public void updateDataOrtu(String npmhs, String info) {
    	//`		null`		null`	null`		null`	null`		null`	null`		null`	ibuku`		null`	null`		null`		null`	null`		null`	null`	null`		null`	null`		null`		null`	null`	null`		null`	null`		null`		null`	null`	null`		null`	null`
    	//info = nmmay+"`"+tglay+"`"+tplay+"`"+llsay+"`"+hpeay+"`"+jobay+"`"+payay+"`"+nikay+"`"+rilay+"`"+nmmbu+"`"+tglbu+"`"+tplbu+"`"+llsbu+"`"+hpebu+"`"+jobbu+"`"+paybu+"`"+nikbu+"`"+rilbu+"`"+nmmwa+"`"+tglwa+"`"+tplwa+"`"+llswa+"`"+hpewa+"`"+jobwa+"`"+paywa+"`"+nikwa+"`"+hubwa+"`"+nmer1+"`"+hper1+"`"+hber1+"`"+nmer2+"`"+hper2+"`"+hber2;
    	if(!Checker.isStringNullOrEmpty(info)) {
    		//System.out.println("inph="+info);
    		StringTokenizer st = new StringTokenizer(info,"`");
    		
    		String nmmay=st.nextToken();
    		String tglay=st.nextToken();
    		String tplay=st.nextToken();
    		String llsay=st.nextToken();
    		String hpeay=st.nextToken();
    		String jobay=st.nextToken();
    		String payay=st.nextToken();
    		String nikay=st.nextToken();
    		String rilay=st.nextToken();
    		
    		String nmmbu=st.nextToken();
    		String tglbu=st.nextToken();
    		String tplbu=st.nextToken();
    		String llsbu=st.nextToken();
    		String hpebu=st.nextToken();
    		String jobbu=st.nextToken();
    		String paybu=st.nextToken();
    		String nikbu=st.nextToken();
    		String rilbu=st.nextToken();
    		
    		String nmmwa=st.nextToken();
    		String tglwa=st.nextToken();
    		String tplwa=st.nextToken();
    		String llswa=st.nextToken();
    		String hpewa=st.nextToken();
    		String jobwa=st.nextToken();
    		String paywa=st.nextToken();
    		String nikwa=st.nextToken();
    		String hubwa=st.nextToken();
    		
    		String nmer1=st.nextToken();
    		String hper1=st.nextToken();
    		String hber1=st.nextToken();
    		String nmer2=st.nextToken();
    		String hper2=st.nextToken();
    		String hber2=st.nextToken();
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("update CIVITAS set NAMA_AYAH=?,TGLHR_AYAH=?,TPLHR_AYAH=?,LULUSAN_AYAH=?,NOHAPE_AYAH=?,KERJA_AYAH=?,GAJI_AYAH=?,NIK_AYAH=?,KANDUNG_AYAH=?,NAMA_IBU=?,TGLHR_IBU=?,TPLHR_IBU=?,LULUSAN_IBU=?,NOHAPE_IBU=?,KERJA_IBU=?,GAJI_IBU=?,NIK_IBU=?,KANDUNG_IBU=?,NAMA_WALI=?,TGLHR_WALI=?,TPLHR_WALI=?,LULUSAN_WALI=?,NOHAPE_WALI=?,KERJA_WALI=?,GAJI_WALI=?,NIK_WALI=?,HUBUNGAN_WALI=?,NAMA_DARURAT1=?,NOHAPE_DARURAT1=?,HUBUNGAN_DARURAT1=?,NAMA_DARURAT2=?,NOHAPE_DARURAT2=?,HUBUNGAN_DARURAT2=? where NPMHSMSMHS=?");
        		int i=1;
        		
        		//1.NAMA_AYAH=?,
        		if(Checker.isStringNullOrEmpty(nmmay)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, nmmay);
        		}
        		//2. TGLHR_AYAH=?,
        		if(Checker.isStringNullOrEmpty(tglay)) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(tglay));
        		}
        		//3. TPLHR_AYAH=?,
        		if(Checker.isStringNullOrEmpty(tplay)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, tplay);
        		}
        		//4. LULUSAN_AYAH=?,
        		if(Checker.isStringNullOrEmpty(llsay)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, llsay);
        		}
        		//5. NOHAPE_AYAH=?,
        		if(Checker.isStringNullOrEmpty(hpeay)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, hpeay);
        		}
        		//6. KERJA_AYAH=?,
        		if(Checker.isStringNullOrEmpty(jobay)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, jobay);
        		}
        		//7GAJI_AYAH=?,
        		if(Checker.isStringNullOrEmpty(payay)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, payay);
        		}
        		//8NIK_AYAH=?,
        		if(Checker.isStringNullOrEmpty(nikay)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, nikay);
        		}
        		//9KANDUNG_AYAH=?,
        		if(Checker.isStringNullOrEmpty(rilay)) {
        			stmt.setNull(i++, java.sql.Types.BOOLEAN);	
        		}
        		else {
        			stmt.setBoolean(i++, Boolean.valueOf(rilay));
        		}
        		//10NAMA_IBU=?,
        		if(Checker.isStringNullOrEmpty(nmmbu)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, nmmbu);
        		}
        		//11TGLHR_IBU=?,
        		if(Checker.isStringNullOrEmpty(tglbu)) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(tglbu));
        		}
        		//12TPLHR_IBU=?,
        		if(Checker.isStringNullOrEmpty(tplbu)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, tplbu);
        		}
        		//13LULUSAN_IBU=?,
        		if(Checker.isStringNullOrEmpty(llsbu)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, llsbu);
        		}
        		//14NOHAPE_IBU=?,
        		if(Checker.isStringNullOrEmpty(hpebu)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, hpebu);
        		}
        		//15KERJA_IBU=?,
        		if(Checker.isStringNullOrEmpty(jobbu)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, jobbu);
        		}
        		//16GAJI_IBU=?,
        		if(Checker.isStringNullOrEmpty(paybu)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, paybu);
        		}
        		//17NIK_IBU=?,
        		if(Checker.isStringNullOrEmpty(nikbu)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, nikbu);
        		}
        		//18KANDUNG_IBU=?,
        		if(Checker.isStringNullOrEmpty(rilbu)) {
        			stmt.setNull(i++, java.sql.Types.BOOLEAN);	
        		}
        		else {
        			stmt.setBoolean(i++, Boolean.valueOf(rilbu));
        		}
            			
        		//19NAMA_WALI=?,
        		if(Checker.isStringNullOrEmpty(nmmwa)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, nmmwa);
        		}
        		//20TGLHR_WALI=?,
        		if(Checker.isStringNullOrEmpty(tglwa)) {
        			stmt.setNull(i++, java.sql.Types.DATE);
        		}
        		else {
        			stmt.setDate(i++, Converter.formatDateBeforeInsert(tglwa));
        		}
        		//21TPLHR_WALI=?,
        		if(Checker.isStringNullOrEmpty(tplwa)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, tplwa);
        		}
        		//22LULUSAN_WALI=?,
        		if(Checker.isStringNullOrEmpty(llswa)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, llswa);
        		}
        		//23NOHAPE_WALI=?,
        		if(Checker.isStringNullOrEmpty(hpewa)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, hpewa);
        		}
        		//24KERJA_WALI=?,
        		if(Checker.isStringNullOrEmpty(jobwa)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, jobwa);
        		}
        		//25GAJI_WALI=?,
        		if(Checker.isStringNullOrEmpty(paywa)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, paywa);
        		}
        		//26NIK_WALI=?,
        		if(Checker.isStringNullOrEmpty(nikwa)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, nikwa);
        		}
        		//27HUBUNGAN_WALI=?,
        		if(Checker.isStringNullOrEmpty(hubwa)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, hubwa);
        		}
        		//28NAMA_DARURAT1=?,
        		if(Checker.isStringNullOrEmpty(nmer1)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, nmer1);
        		}
        		//29NOHAPE_DARURAT1=?,
        		if(Checker.isStringNullOrEmpty(hper1)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, hper1);
        		}
        		//30HUBUNGAN_DARURAT1=?,
        		if(Checker.isStringNullOrEmpty(hber1)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, hber1);
        		}
        		//31NAMA_DARURAT2=?,
        		if(Checker.isStringNullOrEmpty(nmer2)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, nmer2);
        		}
        		//32NOHAPE_DARURAT2=?,
        		if(Checker.isStringNullOrEmpty(hper2)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, hper2);
        		}
        		//33HUBUNGAN_DARURAT2=?
        		if(Checker.isStringNullOrEmpty(hber2)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, hber2);
        		}
        		

        		
        		stmt.setString(i++, npmhs);
        		
        		i = stmt.executeUpdate();
        		//System.out.println("execute2="+i);
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
    
    
    public void updateDataKemahasiswaan(String npmhs, String info) {
    	//`		null`		null`	null`		null`	null`		null`	null`		null`	ibuku`		null`	null`		null`		null`	null`		null`	null`	null`		null`	null`		null`		null`	null`	null`		null`	null`		null`		null`	null`	null`		null`	null`
    	//info = nmmay+"`"+tglay+"`"+tplay+"`"+llsay+"`"+hpeay+"`"+jobay+"`"+payay+"`"+nikay+"`"+rilay+"`"+nmmbu+"`"+tglbu+"`"+tplbu+"`"+llsbu+"`"+hpebu+"`"+jobbu+"`"+paybu+"`"+nikbu+"`"+rilbu+"`"+nmmwa+"`"+tglwa+"`"+tplwa+"`"+llswa+"`"+hpewa+"`"+jobwa+"`"+paywa+"`"+nikwa+"`"+hubwa+"`"+nmer1+"`"+hper1+"`"+hber1+"`"+nmer2+"`"+hper2+"`"+hber2;
    	if(!Checker.isStringNullOrEmpty(info)) {
    		//System.out.println("inph="+info);
    		StringTokenizer st = new StringTokenizer(info,"`");
    		
    		String nimhs = st.nextToken();
    		String shift = st.nextToken();
    		String tahun = st.nextToken();
    		String smawl = st.nextToken();
    		String btstu = st.nextToken();
    		String assma = st.nextToken();
    		String stpid = st.nextToken();
    		String noprm = st.nextToken();
    		String nokp1 = st.nextToken();
    		String nokp2 = st.nextToken();
    		String nokp3 = st.nextToken();
    		String nokp4 = st.nextToken();
    		String krklm = st.nextToken();
    		String npm_pa = st.nextToken();
    		String nmm_pa = st.nextToken();
    		String sksdi=st.nextToken();
    		String asnim=st.nextToken();
    		String aspti=st.nextToken();
    		String asjen=st.nextToken();
    		String aspst=st.nextToken();
    		String aspti_unlisted=st.nextToken();
    		String aspti_kdpti = null;
    		String aspti_nmpti = null;
    		if(!Checker.isStringNullOrEmpty(aspti)) {
    			st = new StringTokenizer(aspti,"-");
    			aspti_kdpti = new String(st.nextToken());
    			aspti_nmpti = new String(st.nextToken());
    			if(aspti_kdpti.contains("N/A")) {
    				aspti_kdpti = null;
    				aspti_nmpti = null;
    			}
    		}
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		stmt = con.prepareStatement("update CIVITAS set NIMHSMSMHS=?,SHIFTMSMHS=?,TAHUNMSMHS=?,SMAWLMSMHS=?,BTSTUMSMHS=?,ASSMAMSMHS=?,STPIDMSMHS=?,NOPRMMSMHS=?,NOKP1MSMHS=?,NOKP2MSMHS=?,NOKP3MSMHS=?,NOKP4MSMHS=?,SKSDIMSMHS=?,ASNIMMSMHS=?,ASPTIMSMHS=?,ASJENMSMHS=?,ASPSTMSMHS=?,ASPTI_UNLISTED=?,ASPTI_KDPTI=? where NPMHSMSMHS=?");
        		int i=1;
        		
        		//1.NIMHSMSMHS=?,
        		if(Checker.isStringNullOrEmpty(nimhs)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, nimhs);
        		}
        		//2. SHIFTMSMHS=?,
        		if(Checker.isStringNullOrEmpty(shift)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, shift);
        		}
        		//3. TAHUNMSMHS=?,
        		if(Checker.isStringNullOrEmpty(tahun)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, tahun);
        		}
        		//4. SMAWLMSMHS=?,
        		if(Checker.isStringNullOrEmpty(smawl)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, smawl);
        		}
        		//5. BTSTUMSMHS=?,
        		if(Checker.isStringNullOrEmpty(btstu)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, btstu);
        		}
        		//6. ASSMAMSMHS=?,
        		if(Checker.isStringNullOrEmpty(assma)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, assma);
        		}
        		//STPIDMSMHS=?,
        		if(Checker.isStringNullOrEmpty(stpid)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, stpid);
        		}
        		//NOPRMMSMHS
        		if(Checker.isStringNullOrEmpty(noprm)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, noprm);
        		}
        		//NOKP1MSMHS=?,
        		if(Checker.isStringNullOrEmpty(nokp1)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, nokp1);
        		}
        		//NOKP2MSMHS=?,
        		if(Checker.isStringNullOrEmpty(nokp2)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, nokp2);
        		}
        		//NOKP3MSMHS=?,
        		if(Checker.isStringNullOrEmpty(nokp3)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, nokp3);
        		}
        		//NOKP4MSMHS=?,
        		if(Checker.isStringNullOrEmpty(nokp4)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,nokp4);
        		}
        		//SKSDIMSMHS=?,
        		if(Checker.isStringNullOrEmpty(sksdi)) {
        			stmt.setNull(i++, java.sql.Types.INTEGER);	
        		}
        		else {
        			stmt.setInt(i++,Integer.parseInt(sksdi));
        		}
        		//ASNIMMSMHS=?,
        		if(Checker.isStringNullOrEmpty(asnim)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,asnim);
        		}
        		//ASPTIMSMHS=?,
        		if(Checker.isStringNullOrEmpty(aspti_nmpti)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			aspti_unlisted = null;
        			stmt.setString(i++,aspti_nmpti);
        		}
        		//ASJENMSMHS=?,
        		if(Checker.isStringNullOrEmpty(asjen)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,asjen);
        		}
        		//ASPSTMSMHS=?
        		if(Checker.isStringNullOrEmpty(aspst)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,aspst);
        		}
        		//ASPTI_UNLISTED
        		if(Checker.isStringNullOrEmpty(aspti_unlisted)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,aspti_unlisted);
        		}
        		//ASPTI_KDPTI=?,
        		if(Checker.isStringNullOrEmpty(aspti_kdpti)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,aspti_kdpti);
        		}
        		
        		
        		stmt.setString(i++, npmhs);
        		i = stmt.executeUpdate();
        		
        		//,=?,=?,
        		stmt = con.prepareStatement("update EXT_CIVITAS set KRKLMMSMHS=?,NPM_PA=?,NMM_PA=? where NPMHSMSMHS=?");
        		i=1;
        		
        		//1.KRKLMMSMHS=?,
        		if(Checker.isStringNullOrEmpty(krklm)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, krklm);
        		}
        		//2. NPM_PA=?,
        		if(Checker.isStringNullOrEmpty(npm_pa)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, npm_pa);
        		}
        		//3. NMM_PA=?,
        		if(Checker.isStringNullOrEmpty(nmm_pa)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, nmm_pa);
        		}
        		stmt.setString(i++, npmhs);
        		i = stmt.executeUpdate();
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
    
    
    public void updateDataDosen(String npmhs, String info) {
    	//`		null`		null`	null`		null`	null`		null`	null`		null`	ibuku`		null`	null`		null`		null`	null`		null`	null`	null`		null`	null`		null`		null`	null`	null`		null`	null`		null`		null`	null`	null`		null`	null`
    	//info = kdpst+"`"+npmhs+"`"+local+"`"+gelar_depan+"`"+gelar_belakang+"`"+nidn+"`"+tipe_id+"`"+no_id+"`"+status+"`"+pt_s1+"`"+jur_s1+"`"+kdpst_s1+"`"+gelar_s1+"`"+bidil_s1+"`"+noija_s1+"`"+tglls_s1+"`"+file_ija_s1+"`"+judul_s1+"`"+pt_s2+"`"+jur_s2+"`"+kdpst_s2+"`"+gelar_s2+"`"+bidil_s2+"`"+noija_s2+"`"+tglls_s2+"`"+file_ija_s2+"`"+judul_s2+"`"+pt_s3+"`"+jur_s3+"`"+kdpst_s3+"`"+gelar_s3+"`"+bidil_s3+"`"+noija_s3+"`"+tglls_s3+"`"+file_ija_s3+"`"+judul_s3+"`"+pt_gb+"`"+jur_gb+"`"+kdpst_gb+"`"+gelar_gb+"`"+bidil_gb+"`"+noija_gb+"`"+tglls_gb+"`"+file_ija_gb+"`"+judul_gb+"`"+tot_kum+"`"+jja_dikti+"`"+jja_local+"`"+jab_struk+"`"+tipe_ika+"`"+tgl_in+"`"+tgl_out+"`"+serdos+"`"+kdpti_home+"`"+kdpst_home+"`"+email_org+"`"+pangkat_gol+"`"+catatan_riwayat+"`"+ktp_sim_paspo+"`"+no_ktp_sim_paspo+"`"+nik+"`"+nip+"`"+niy_nigk+"`"+nuptk+"`"+nsdmi;
    	if(!Checker.isStringNullOrEmpty(info)) {
    		//System.out.println("inph="+info);
    		StringTokenizer st = new StringTokenizer(info,"`");
    		String kdpst = st.nextToken();
    		st.nextToken();//npmhs
    		String local = st.nextToken();
    		String gelar_depan = st.nextToken();
    		String gelar_belakang = st.nextToken();
    		String nidn = st.nextToken();
    		String tipe_id = st.nextToken();
    		String no_id = st.nextToken();
    		String status = st.nextToken();
    		String pt_s1 = st.nextToken();
    		String jur_s1 = st.nextToken();
    		String kdpst_s1 = st.nextToken();
    		String gelar_s1 = st.nextToken();
    		String bidil_s1 = st.nextToken();
    		String noija_s1 = st.nextToken();
    		String tglls_s1 = st.nextToken();
    		String file_ija_s1 = st.nextToken();
    		String judul_s1 = st.nextToken();
    		String pt_s2 = st.nextToken();
    		String jur_s2 = st.nextToken();
    		String kdpst_s2 = st.nextToken();
    		String gelar_s2 = st.nextToken();
    		String bidil_s2 = st.nextToken();
    		String noija_s2 = st.nextToken();
    		String tglls_s2 = st.nextToken();
    		String file_ija_s2 = st.nextToken();
    		String judul_s2 = st.nextToken();
    		String pt_s3 = st.nextToken();
    		String jur_s3 = st.nextToken();
    		String kdpst_s3 = st.nextToken();
    		String gelar_s3 = st.nextToken();
    		String bidil_s3 = st.nextToken();
    		String noija_s3 = st.nextToken();
    		String tglls_s3 = st.nextToken();
    		String file_ija_s3 = st.nextToken();
    		String judul_s3 = st.nextToken();
    		String pt_gb = st.nextToken();
    		String jur_gb = st.nextToken();
    		String kdpst_gb = st.nextToken();
    		String gelar_gb = st.nextToken();
    		String bidil_gb = st.nextToken();
    		String noija_gb = st.nextToken();
    		String tglls_gb = st.nextToken();
    		String file_ija_gb = st.nextToken();
    		String judul_gb = st.nextToken();
    		String tot_kum = st.nextToken();
    		String jja_dikti = st.nextToken();
    		String jja_local = st.nextToken();
    		String jab_struk = st.nextToken();
    		String tipe_ika = st.nextToken();
    		String tgl_in = st.nextToken();
    		String tgl_out = st.nextToken();
    		String serdos = st.nextToken();
    		String kdpti_home = st.nextToken();
    		String kdpst_home = st.nextToken();
    		String email_org = st.nextToken();
    		String pangkat_gol = st.nextToken();
    		String catatan_riwayat = st.nextToken();
    		String ktp_sim_paspo = st.nextToken();
    		String no_ktp_sim_paspo = st.nextToken();
    		String nik = st.nextToken();
    		String nip = st.nextToken();
    		String niy_nigk = st.nextToken();
    		String nuptk = st.nextToken();
    		String nsdmi = st.nextToken();
    		String nidk = st.nextToken();
    		String nup = st.nextToken();
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		stmt = con.prepareStatement("update EXT_CIVITAS_DATA_DOSEN set NODOS_LOCAL=?,GELAR_DEPAN=?,GELAR_BELAKANG=?,NIDNN=?,TIPE_ID=?,NOMOR_ID=?,STATUS=?,PT_S1=?,JURUSAN_S1=?,KDPST_S1=?,GELAR_S1=?,TKN_BIDANG_KEAHLIAN_S1=?,NOIJA_S1=?,TGLLS_S1=?,FILE_IJA_S1=?,JUDUL_TA_S1=?,PT_S2=?,JURUSAN_S2=?,KDPST_S2=?,GELAR_S2=?,TKN_BIDANG_KEAHLIAN_S2=?,NOIJA_S2=?,TGLLS_S2=?,FILE_IJA_S2=?,JUDUL_TA_S2=?,PT_S3=?,JURUSAN_S3=?,KDPST_S3=?,GELAR_S3=?,TKN_BIDANG_KEAHLIAN_S3=?,NOIJA_S3=?,TGLLS_S3=?,FILE_IJA_S3=?,JUDUL_TA_S3=?,PT_PROF=?,JURUSAN_PROF=?,KDPST_PROF=?,GELAR_PROF=?,TKN_BIDANG_KEAHLIAN_PROF=?,NOIJA_PROF=?,TGLLS_PROF=?,FILE_IJA_PROF=?,JUDUL_TA_PROF=?,TOTAL_KUM=?,JABATAN_AKADEMIK_DIKTI=?,JABATAN_AKADEMIK_LOCAL=?,JABATAN_STRUKTURAL=?,IKATAN_KERJA_DOSEN=?,TANGGAL_MULAI_KERJA=?,TANGGAL_KELUAR_KERJA=?,SERDOS=?,KDPTI_HOMEBASE=?,KDPST_HOMEBASE=?,EMAIL_INSTITUSI=?,PANGKAT_GOLONGAN=?,CATATAN_RIWAYAT=?,TIPE_KTP=?,NO_KTP=?,NIK=?,NIP=?,NIY_NIGK=?,NUPTK=?,NSDMI=?,NIDK=?,NUP=? where NPMHS=?");
        		int i=1;
        		
        		//1.NIMHSMSMHS=?,
        		if(Checker.isStringNullOrEmpty(local)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, local);
        		}
        		//2. SHIFTMSMHS=?,
        		if(Checker.isStringNullOrEmpty(gelar_depan)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, gelar_depan);
        		}
        		//3. TAHUNMSMHS=?,
        		if(Checker.isStringNullOrEmpty(gelar_belakang)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, gelar_belakang);
        		}
        		//4. SMAWLMSMHS=?,
        		if(Checker.isStringNullOrEmpty(nidn)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, nidn);
        		}
        		//5. BTSTUMSMHS=?,
        		if(Checker.isStringNullOrEmpty(tipe_id)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, tipe_id);
        		}
        		//6. ASSMAMSMHS=?,
        		if(Checker.isStringNullOrEmpty(no_id)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, no_id);
        		}
        		//STPIDMSMHS=?,
        		if(Checker.isStringNullOrEmpty(status)) {
        			stmt.setString(i++, "N/A");
        		}
        		else {
        			stmt.setString(i++, status);
        		}
        		//NOPRMMSMHS
        		if(Checker.isStringNullOrEmpty(pt_s1)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, pt_s1);
        		}
        		
        		//s1
        		if(Checker.isStringNullOrEmpty(jur_s1)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, jur_s1);
        		}
        		//NOKP2MSMHS=?,
        		if(Checker.isStringNullOrEmpty(kdpst_s1)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, kdpst_s1);
        		}
        		//NOKP3MSMHS=?,
        		if(Checker.isStringNullOrEmpty(gelar_s1)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, gelar_s1);
        		}
        		//NOKP4MSMHS=?,
        		if(Checker.isStringNullOrEmpty(bidil_s1)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,bidil_s1);
        		}
        		
        		if(Checker.isStringNullOrEmpty(noija_s1)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,noija_s1);
        		}
        		
        		if(Checker.isStringNullOrEmpty(tglls_s1)) {
        			stmt.setNull(i++, java.sql.Types.DATE);	
        		}
        		else {
        			stmt.setDate(i++,Converter.formatDateBeforeInsert(tglls_s1));
        		}
        		
        		if(Checker.isStringNullOrEmpty(file_ija_s1)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,file_ija_s1);
        		}
        		
        		if(Checker.isStringNullOrEmpty(judul_s1)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,judul_s1);
        		}
        		
        		//s2
        		if(Checker.isStringNullOrEmpty(pt_s2)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, pt_s2);
        		}
        		//NOKP1MSMHS=?,
        		if(Checker.isStringNullOrEmpty(jur_s2)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, jur_s2);
        		}
        		//NOKP2MSMHS=?,
        		if(Checker.isStringNullOrEmpty(kdpst_s2)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, kdpst_s2);
        		}
        		//NOKP3MSMHS=?,
        		if(Checker.isStringNullOrEmpty(gelar_s2)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, gelar_s2);
        		}
        		//NOKP4MSMHS=?,
        		if(Checker.isStringNullOrEmpty(bidil_s2)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,bidil_s2);
        		}
        		
        		if(Checker.isStringNullOrEmpty(noija_s2)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,noija_s2);
        		}
        		
        		if(Checker.isStringNullOrEmpty(tglls_s2)) {
        			stmt.setNull(i++, java.sql.Types.DATE);	
        		}
        		else {
        			stmt.setDate(i++,Converter.formatDateBeforeInsert(tglls_s2));
        		}
        		
        		if(Checker.isStringNullOrEmpty(file_ija_s2)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,file_ija_s2);
        		}
        		
        		if(Checker.isStringNullOrEmpty(judul_s2)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,judul_s2);
        		}
        		
        		//s3
        		if(Checker.isStringNullOrEmpty(pt_s3)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, pt_s3);
        		}
        		//NOKP1MSMHS=?,
        		if(Checker.isStringNullOrEmpty(jur_s3)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, jur_s3);
        		}
        		//NOKP2MSMHS=?,
        		if(Checker.isStringNullOrEmpty(kdpst_s3)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, kdpst_s3);
        		}
        		//NOKP3MSMHS=?,
        		if(Checker.isStringNullOrEmpty(gelar_s3)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, gelar_s3);
        		}
        		//NOKP4MSMHS=?,
        		if(Checker.isStringNullOrEmpty(bidil_s3)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,bidil_s3);
        		}
        		
        		if(Checker.isStringNullOrEmpty(noija_s3)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,noija_s3);
        		}
        		
        		if(Checker.isStringNullOrEmpty(tglls_s3)) {
        			stmt.setNull(i++, java.sql.Types.DATE);	
        		}
        		else {
        			stmt.setDate(i++,Converter.formatDateBeforeInsert(tglls_s3));
        		}
        		
        		if(Checker.isStringNullOrEmpty(file_ija_s3)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,file_ija_s3);
        		}
        		
        		if(Checker.isStringNullOrEmpty(judul_s3)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,judul_s3);
        		}
        		
        		//gb
        		if(Checker.isStringNullOrEmpty(pt_gb )) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, pt_gb );
        		}
        		//NOKP1MSMHS=?,
        		if(Checker.isStringNullOrEmpty(jur_gb )) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, jur_gb );
        		}
        		//NOKP2MSMHS=?,
        		if(Checker.isStringNullOrEmpty(kdpst_gb )) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(i++, kdpst_gb );
        		}
        		//NOKP3MSMHS=?,
        		if(Checker.isStringNullOrEmpty(gelar_gb )) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++, gelar_gb );
        		}
        		//NOKP4MSMHS=?,
        		if(Checker.isStringNullOrEmpty(bidil_gb )) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,bidil_gb );
        		}
        		
        		if(Checker.isStringNullOrEmpty(noija_gb )) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,noija_gb );
        		}
        		
        		if(Checker.isStringNullOrEmpty(tglls_gb )) {
        			stmt.setNull(i++, java.sql.Types.DATE);	
        		}
        		else {
        			stmt.setDate(i++,Converter.formatDateBeforeInsert(tglls_gb ));
        		}
        		
        		if(Checker.isStringNullOrEmpty(file_ija_gb )) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,file_ija_gb );
        		}
        		
        		if(Checker.isStringNullOrEmpty(judul_gb)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,judul_gb);
        		}
        		
        		if(Checker.isStringNullOrEmpty(tot_kum)) {
        			stmt.setInt(i++,0);	
        		}
        		else {
        			stmt.setInt(i++,Integer.parseInt(tot_kum));
        		}
        		
        		if(Checker.isStringNullOrEmpty(jja_dikti)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,jja_dikti);
        		}
        		if(Checker.isStringNullOrEmpty(jja_local)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,jja_local);
        		}
        		if(Checker.isStringNullOrEmpty(jab_struk)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,jab_struk);
        		}
        		if(Checker.isStringNullOrEmpty(tipe_ika)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,tipe_ika);
        		}
        		
        		if(Checker.isStringNullOrEmpty(tgl_in)) {
        			stmt.setNull(i++, java.sql.Types.DATE);	
        		}
        		else {
        			stmt.setDate(i++,Converter.formatDateBeforeInsert(tgl_in));
        		}
        		if(Checker.isStringNullOrEmpty(tgl_out)) {
        			stmt.setNull(i++, java.sql.Types.DATE);	
        		}
        		else {
        			stmt.setDate(i++,Converter.formatDateBeforeInsert(tgl_out));
        		}
        		
        		if(Checker.isStringNullOrEmpty(serdos)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,serdos);
        		}
        		if(Checker.isStringNullOrEmpty(kdpti_home)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,kdpti_home);
        		}
        		if(Checker.isStringNullOrEmpty(kdpst_home)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,kdpst_home);
        		}
        		if(Checker.isStringNullOrEmpty(email_org)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,email_org);
        		}
        		
        		if(Checker.isStringNullOrEmpty(pangkat_gol)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,pangkat_gol);
        		}
        		if(Checker.isStringNullOrEmpty(catatan_riwayat)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,catatan_riwayat);
        		}
        		if(Checker.isStringNullOrEmpty(ktp_sim_paspo)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,ktp_sim_paspo);
        		}
        		if(Checker.isStringNullOrEmpty(no_ktp_sim_paspo)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,no_ktp_sim_paspo);
        		}
        		if(Checker.isStringNullOrEmpty(nik)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,nik);
        		}
        		if(Checker.isStringNullOrEmpty(nip)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,nip);
        		}
        		if(Checker.isStringNullOrEmpty(niy_nigk)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,niy_nigk);
        		}
        		if(Checker.isStringNullOrEmpty(nuptk)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,nuptk);
        		}
        		if(Checker.isStringNullOrEmpty(nsdmi)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,nsdmi);
        		}
        		if(Checker.isStringNullOrEmpty(nidk)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,nidk);
        		}
        		if(Checker.isStringNullOrEmpty(nup)) {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);	
        		}
        		else {
        			stmt.setString(i++,nup);
        		}

        		stmt.setString(i++, npmhs);
        		i = stmt.executeUpdate();
        		//System.out.println("info="+info+" -> "+i);
        		if(i<1) {
        			i=1;
        			stmt = con.prepareStatement("INSERT INTO EXT_CIVITAS_DATA_DOSEN(KDPST,NPMHS,NODOS_LOCAL,GELAR_DEPAN,GELAR_BELAKANG,NIDNN,TIPE_ID,NOMOR_ID,STATUS,PT_S1,JURUSAN_S1,KDPST_S1,GELAR_S1,TKN_BIDANG_KEAHLIAN_S1,NOIJA_S1,TGLLS_S1,FILE_IJA_S1,JUDUL_TA_S1,PT_S2,JURUSAN_S2,KDPST_S2,GELAR_S2,TKN_BIDANG_KEAHLIAN_S2,NOIJA_S2,TGLLS_S2,FILE_IJA_S2,JUDUL_TA_S2,PT_S3,JURUSAN_S3,KDPST_S3,GELAR_S3,TKN_BIDANG_KEAHLIAN_S3,NOIJA_S3,TGLLS_S3,FILE_IJA_S3,JUDUL_TA_S3,PT_PROF,JURUSAN_PROF,KDPST_PROF,GELAR_PROF,TKN_BIDANG_KEAHLIAN_PROF,NOIJA_PROF,TGLLS_PROF,FILE_IJA_PROF,JUDUL_TA_PROF,TOTAL_KUM,JABATAN_AKADEMIK_DIKTI,JABATAN_AKADEMIK_LOCAL,JABATAN_STRUKTURAL,IKATAN_KERJA_DOSEN,TANGGAL_MULAI_KERJA,TANGGAL_KELUAR_KERJA,SERDOS,KDPTI_HOMEBASE,KDPST_HOMEBASE,EMAIL_INSTITUSI,PANGKAT_GOLONGAN,CATATAN_RIWAYAT,TIPE_KTP,NO_KTP,NIK,NIP,NIY_NIGK,NUPTK,NSDMI,NIDK,NUP)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        			stmt.setString(i++, kdpst);
        			stmt.setString(i++, npmhs);
        			if(Checker.isStringNullOrEmpty(local)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++, local);
            		}
            		//2. SHIFTMSMHS=?,
            		if(Checker.isStringNullOrEmpty(gelar_depan)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);
            		}
            		else {
            			stmt.setString(i++, gelar_depan);
            		}
            		//3. TAHUNMSMHS=?,
            		if(Checker.isStringNullOrEmpty(gelar_belakang)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++, gelar_belakang);
            		}
            		//4. SMAWLMSMHS=?,
            		if(Checker.isStringNullOrEmpty(nidn)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++, nidn);
            		}
            		//5. BTSTUMSMHS=?,
            		if(Checker.isStringNullOrEmpty(tipe_id)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++, tipe_id);
            		}
            		//6. ASSMAMSMHS=?,
            		if(Checker.isStringNullOrEmpty(no_id)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++, no_id);
            		}
            		//STPIDMSMHS=?,
            		if(Checker.isStringNullOrEmpty(status)) {
            			stmt.setString(i++, "N/A");
            		}
            		else {
            			stmt.setString(i++, status);
            		}
            		//NOPRMMSMHS
            		if(Checker.isStringNullOrEmpty(pt_s1)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++, pt_s1);
            		}
            		
            		//s1
            		if(Checker.isStringNullOrEmpty(jur_s1)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++, jur_s1);
            		}
            		//NOKP2MSMHS=?,
            		if(Checker.isStringNullOrEmpty(kdpst_s1)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);
            		}
            		else {
            			stmt.setString(i++, kdpst_s1);
            		}
            		//NOKP3MSMHS=?,
            		if(Checker.isStringNullOrEmpty(gelar_s1)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++, gelar_s1);
            		}
            		//NOKP4MSMHS=?,
            		if(Checker.isStringNullOrEmpty(bidil_s1)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,bidil_s1);
            		}
            		
            		if(Checker.isStringNullOrEmpty(noija_s1)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,noija_s1);
            		}
            		
            		if(Checker.isStringNullOrEmpty(tglls_s1)) {
            			stmt.setNull(i++, java.sql.Types.DATE);	
            		}
            		else {
            			stmt.setDate(i++,Converter.formatDateBeforeInsert(tglls_s1));
            		}
            		
            		if(Checker.isStringNullOrEmpty(file_ija_s1)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,file_ija_s1);
            		}
            		
            		if(Checker.isStringNullOrEmpty(judul_s1)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,judul_s1);
            		}
            		
            		//s2
            		if(Checker.isStringNullOrEmpty(pt_s2)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++, pt_s2);
            		}
            		//NOKP1MSMHS=?,
            		if(Checker.isStringNullOrEmpty(jur_s2)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++, jur_s2);
            		}
            		//NOKP2MSMHS=?,
            		if(Checker.isStringNullOrEmpty(kdpst_s2)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);
            		}
            		else {
            			stmt.setString(i++, kdpst_s2);
            		}
            		//NOKP3MSMHS=?,
            		if(Checker.isStringNullOrEmpty(gelar_s2)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++, gelar_s2);
            		}
            		//NOKP4MSMHS=?,
            		if(Checker.isStringNullOrEmpty(bidil_s2)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,bidil_s2);
            		}
            		
            		if(Checker.isStringNullOrEmpty(noija_s2)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,noija_s2);
            		}
            		
            		if(Checker.isStringNullOrEmpty(tglls_s2)) {
            			stmt.setNull(i++, java.sql.Types.DATE);	
            		}
            		else {
            			stmt.setDate(i++,Converter.formatDateBeforeInsert(tglls_s2));
            		}
            		
            		if(Checker.isStringNullOrEmpty(file_ija_s2)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,file_ija_s2);
            		}
            		
            		if(Checker.isStringNullOrEmpty(judul_s2)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,judul_s2);
            		}
            		
            		//s3
            		if(Checker.isStringNullOrEmpty(pt_s3)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++, pt_s3);
            		}
            		//NOKP1MSMHS=?,
            		if(Checker.isStringNullOrEmpty(jur_s3)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++, jur_s3);
            		}
            		//NOKP2MSMHS=?,
            		if(Checker.isStringNullOrEmpty(kdpst_s3)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);
            		}
            		else {
            			stmt.setString(i++, kdpst_s3);
            		}
            		//NOKP3MSMHS=?,
            		if(Checker.isStringNullOrEmpty(gelar_s3)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++, gelar_s3);
            		}
            		//NOKP4MSMHS=?,
            		if(Checker.isStringNullOrEmpty(bidil_s3)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,bidil_s3);
            		}
            		
            		if(Checker.isStringNullOrEmpty(noija_s3)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,noija_s3);
            		}
            		
            		if(Checker.isStringNullOrEmpty(tglls_s3)) {
            			stmt.setNull(i++, java.sql.Types.DATE);	
            		}
            		else {
            			stmt.setDate(i++,Converter.formatDateBeforeInsert(tglls_s3));
            		}
            		
            		if(Checker.isStringNullOrEmpty(file_ija_s3)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,file_ija_s3);
            		}
            		
            		if(Checker.isStringNullOrEmpty(judul_s3)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,judul_s3);
            		}
            		
            		//gb
            		if(Checker.isStringNullOrEmpty(pt_gb )) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++, pt_gb );
            		}
            		//NOKP1MSMHS=?,
            		if(Checker.isStringNullOrEmpty(jur_gb )) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++, jur_gb );
            		}
            		//NOKP2MSMHS=?,
            		if(Checker.isStringNullOrEmpty(kdpst_gb )) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);
            		}
            		else {
            			stmt.setString(i++, kdpst_gb );
            		}
            		//NOKP3MSMHS=?,
            		if(Checker.isStringNullOrEmpty(gelar_gb )) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++, gelar_gb );
            		}
            		//NOKP4MSMHS=?,
            		if(Checker.isStringNullOrEmpty(bidil_gb )) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,bidil_gb );
            		}
            		
            		if(Checker.isStringNullOrEmpty(noija_gb )) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,noija_gb );
            		}
            		
            		if(Checker.isStringNullOrEmpty(tglls_gb )) {
            			stmt.setNull(i++, java.sql.Types.DATE);	
            		}
            		else {
            			stmt.setDate(i++,Converter.formatDateBeforeInsert(tglls_gb ));
            		}
            		
            		if(Checker.isStringNullOrEmpty(file_ija_gb )) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,file_ija_gb );
            		}
            		
            		if(Checker.isStringNullOrEmpty(judul_gb)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,judul_gb);
            		}
            		
            		if(Checker.isStringNullOrEmpty(tot_kum)) {
            			stmt.setInt(i++,0);	
            		}
            		else {
            			stmt.setInt(i++,Integer.parseInt(tot_kum));
            		}
            		
            		if(Checker.isStringNullOrEmpty(jja_dikti)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,jja_dikti);
            		}
            		if(Checker.isStringNullOrEmpty(jja_local)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,jja_local);
            		}
            		if(Checker.isStringNullOrEmpty(jab_struk)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,jab_struk);
            		}
            		if(Checker.isStringNullOrEmpty(tipe_ika)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,tipe_ika);
            		}
            		
            		if(Checker.isStringNullOrEmpty(tgl_in)) {
            			stmt.setNull(i++, java.sql.Types.DATE);	
            		}
            		else {
            			stmt.setDate(i++,Converter.formatDateBeforeInsert(tgl_in));
            		}
            		if(Checker.isStringNullOrEmpty(tgl_out)) {
            			stmt.setNull(i++, java.sql.Types.DATE);	
            		}
            		else {
            			stmt.setDate(i++,Converter.formatDateBeforeInsert(tgl_out));
            		}
            		
            		if(Checker.isStringNullOrEmpty(serdos)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,serdos);
            		}
            		if(Checker.isStringNullOrEmpty(kdpti_home)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,kdpti_home);
            		}
            		if(Checker.isStringNullOrEmpty(kdpst_home)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,kdpst_home);
            		}
            		if(Checker.isStringNullOrEmpty(email_org)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,email_org);
            		}
            		
            		if(Checker.isStringNullOrEmpty(pangkat_gol)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,pangkat_gol);
            		}
            		if(Checker.isStringNullOrEmpty(catatan_riwayat)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,catatan_riwayat);
            		}
            		if(Checker.isStringNullOrEmpty(ktp_sim_paspo)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,ktp_sim_paspo);
            		}
            		if(Checker.isStringNullOrEmpty(no_ktp_sim_paspo)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,no_ktp_sim_paspo);
            		}
            		if(Checker.isStringNullOrEmpty(nik)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,nik);
            		}
            		if(Checker.isStringNullOrEmpty(nip)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,nip);
            		}
            		if(Checker.isStringNullOrEmpty(niy_nigk)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,niy_nigk);
            		}
            		if(Checker.isStringNullOrEmpty(nuptk)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,nuptk);
            		}
            		if(Checker.isStringNullOrEmpty(nsdmi)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,nsdmi);
            		}
            		if(Checker.isStringNullOrEmpty(nidk)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,nidk);
            		}
            		if(Checker.isStringNullOrEmpty(nup)) {
            			stmt.setNull(i++, java.sql.Types.VARCHAR);	
            		}
            		else {
            			stmt.setString(i++,nup);
            		}

            		
            		i = stmt.executeUpdate();
            		//System.out.println("info insert="+info+" -> "+i);	
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
    
    public Vector fixDataRt() {
    	Vector v = null;
    	ListIterator li = null;
    	StringTokenizer st = null;
    	try {
    		Vector v_fix = null;
    		ListIterator lif = null;
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		
    		
    		stmt = con.prepareStatement("select a.NPMHSMSMHS,NRTRMMSMHS from CIVITAS a inner join EXT_CIVITAS b on a.NPMHSMSMHS=b.NPMHSMSMHS where SMAWLMSMHS>='20161' and NRTRMMSMHS is not null and char_length(NRTRMMSMHS)>2");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String npmhs = rs.getString(1);
    			String nrtrw = rs.getString(2);
    			
    			nrtrw = nrtrw.replace("r", "");
    			nrtrw = nrtrw.replace("R", "");
    			nrtrw = nrtrw.replace("t", "");
    			nrtrw = nrtrw.replace("T", "");
    			nrtrw = nrtrw.replace("w", "");
    			nrtrw = nrtrw.replace("W", "");
    			nrtrw = nrtrw.replace(".", "");
    			if(nrtrw.contains("/")) {
    				st = new StringTokenizer(nrtrw,"/");
    				nrtrw = st.nextToken();
    			}
    			nrtrw = nrtrw.trim();
    			boolean bilangan = false;
    			try {
					Integer.parseInt(nrtrw);
					bilangan = true;
    			}
				catch(Exception e) {
					
					
				}
    			if(bilangan) {
    				if(nrtrw.length()>1) {
    					nrtrw = nrtrw.substring(1, nrtrw.length());	
    				}
    				//proses
    				if(v_fix==null) {
    					v_fix=new Vector();
    					lif = v_fix.listIterator();
    				}
    				lif.add(npmhs+"`"+nrtrw);
    			}
    			else {
    				if(v==null) {
    					v=new Vector();
    					li = v.listIterator();
    				}
    				li.add(npmhs+"`"+nrtrw);
    			}
			
    		}
    		if(v_fix!=null) {
    			lif = v_fix.listIterator();
    			stmt = con.prepareStatement("update EXT_CIVITAS set NRTRMMSMHS=? where NPMHSMSMHS=?");
    			while(lif.hasNext()) {
    				String brs = (String)lif.next();
    				
    				st  = new StringTokenizer(brs,"`");
    				String npmhs = st.nextToken();
    				String value = st.nextToken();
    				stmt.setString(1, value);
    				stmt.setString(2, npmhs);
    				stmt.executeUpdate();
    				//System.out.println(brs+"="+k);
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
    	return v;
    }
    
    public Vector fixDataRw() {
    	Vector v = null;
    	ListIterator li = null;
    	StringTokenizer st = null;
    	try {
    		Vector v_fix = null;
    		ListIterator lif = null;
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		
    		
    		stmt = con.prepareStatement("select a.NPMHSMSMHS,NRWRMMSMHS from CIVITAS a inner join EXT_CIVITAS b on a.NPMHSMSMHS=b.NPMHSMSMHS where SMAWLMSMHS>='20161' and NRWRMMSMHS is not null and char_length(NRWRMMSMHS)>2");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String npmhs = rs.getString(1);
    			String nrtrw = rs.getString(2);
    			
    			nrtrw = nrtrw.replace("r", "");
    			nrtrw = nrtrw.replace("R", "");
    			nrtrw = nrtrw.replace("t", "");
    			nrtrw = nrtrw.replace("T", "");
    			nrtrw = nrtrw.replace("w", "");
    			nrtrw = nrtrw.replace("W", "");
    			nrtrw = nrtrw.replace(".", "");
    			if(nrtrw.contains("/")) {
    				st = new StringTokenizer(nrtrw,"/");
    				st.nextToken();//tkn pertama no rt
    				nrtrw = st.nextToken();
    			}
    			nrtrw = nrtrw.trim();
    			boolean bilangan = false;
    			try {
					Integer.parseInt(nrtrw);
					bilangan = true;
    			}
				catch(Exception e) {
					
					
				}
    			if(bilangan) {
    				if(nrtrw.length()>1) {
    					nrtrw = nrtrw.substring(1, nrtrw.length());	
    				}
    				//proses
    				if(v_fix==null) {
    					v_fix=new Vector();
    					lif = v_fix.listIterator();
    				}
    				
    				lif.add(npmhs+"`"+nrtrw);
    			}
    			else {
    				if(v==null) {
    					v=new Vector();
    					li = v.listIterator();
    				}
    				li.add(npmhs+"`"+nrtrw);
    			}
			
    		}
    		if(v_fix!=null) {
    			
    			lif = v_fix.listIterator();
    			stmt = con.prepareStatement("update EXT_CIVITAS set NRWRMMSMHS=? where NPMHSMSMHS=?");
    			while(lif.hasNext()) {
    				String brs = (String)lif.next();
    				
    				st  = new StringTokenizer(brs,"`");
    				String npmhs = st.nextToken();
    				String value = st.nextToken();
    				stmt.setString(1, value);
    				stmt.setString(2, npmhs);
    				stmt.executeUpdate();
    				//System.out.println(brs+"="+k);
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
    	return v;
    }
    
    
    public int  removeUnwantedChar(String unwanted_char, String target_colomn, String table_name) {
    	int updated = 0;
    	try {
    		Vector v_fix = null;
    		ListIterator lif = null;
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		String sql_cmd = "";
    		if(unwanted_char.equalsIgnoreCase("'")) {
    			sql_cmd = "UPDATE "+table_name+" set "+target_colomn+"=replace("+target_colomn+",'\\\'','') where "+target_colomn+" is not null and "+target_colomn+" like '%\\\'%'";
    			stmt = con.prepareStatement(sql_cmd);
    		}
    		else {
    			sql_cmd = "UPDATE "+table_name+" set "+target_colomn+"=replace("+target_colomn+",'"+unwanted_char+"','') where "+target_colomn+" is not null and "+target_colomn+" like '%"+unwanted_char+"%'";
    			stmt = con.prepareStatement(sql_cmd);	
    		}
    		//System.out.println("sql_cmd = "+sql_cmd);
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
    
    public int  setDataAgamaToDefaultJikaBelumTerisi(String def_nm_agama){
    	int updated = 0;
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		String sql_cmd = "update EXT_CIVITAS set AGAMAMSMHS='"+def_nm_agama+"' where AGAMAMSMHS is null or char_length(AGAMAMSMHS)<2";
    		stmt = con.prepareStatement(sql_cmd);	
    		//System.out.println("sql_cmd = "+sql_cmd);
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
    
    public int  resetInvalidNik(){
    	int updated = 0;
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		String sql_cmd = "update CIVITAS set NIKTPMSMHS=null where NIKTPMSMHS is not null and char_length(NIKTPMSMHS)<9";
    		stmt = con.prepareStatement(sql_cmd);
    		sql_cmd = "update CIVITAS set NIK_AYAH=null where NIK_AYAH is not null and char_length(NIK_AYAH)<9";
    		stmt = con.prepareStatement(sql_cmd);
    		sql_cmd = "update CIVITAS set NIK_IBU=null where NIK_IBU is not null and char_length(NIK_IBU)<9";
    		stmt = con.prepareStatement(sql_cmd);
    		//System.out.println("sql_cmd = "+sql_cmd);
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
    
    public int  resetInvalidEmailFormat(){
    	int updated = 0;
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		//String sql_cmd = "update EXT_CIVITAS set EMAILMSMHS=NULL where EMAILMSMHS not like '%@%.%'";
    		//stmt = con.prepareStatement(sql_cmd);
    		//updated = updated+stmt.executeUpdate();
    		//
    		//stmt = con.prepareStatement("update EXT_CIVITAS set EMAILMSMHS=LOWER(EMAILMSMHS),EMAILMSMHS=TRIM(EMAILMSMHS),EMAILMSMHS=TRIM(TRAILING '_' FROM EMAILMSMHS),EMAILMSMHS=REPLACE(EMAILMSMHS,' ','_') where EMAILMSMHS is not null");
    		stmt = con.prepareStatement("update EXT_CIVITAS set EMAILMSMHS='error@error.com' where EMAILMSMHS is null");
    		updated = updated+stmt.executeUpdate();
    		stmt = con.prepareStatement("update EXT_CIVITAS set EMAILMSMHS=LOWER(EMAILMSMHS),EMAILMSMHS=TRIM(EMAILMSMHS),EMAILMSMHS=TRIM(TRAILING '_' FROM EMAILMSMHS),EMAILMSMHS=REPLACE(EMAILMSMHS,' ','_'),EMAILMSMHS=concat(EMAILMSMHS,'@error.com') where EMAILMSMHS not like '%@%.%'");
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
    
    public int  setDefaultTelprm(){
    	int updated = 0;
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		String sql_cmd = "update EXT_CIVITAS set TELRMMSMHS='08100000000' where  TELRMMSMHS is null or char_length(TELRMMSMHS)<7";
    		stmt = con.prepareStatement(sql_cmd);
    		updated = updated+stmt.executeUpdate();
    		//
    		//stmt = con.prepareStatement("update EXT_CIVITAS set EMAILMSMHS=LOWER(EMAILMSMHS),EMAILMSMHS=TRIM(EMAILMSMHS),EMAILMSMHS=TRIM(TRAILING '_' FROM EMAILMSMHS),EMAILMSMHS=REPLACE(EMAILMSMHS,' ','_') where EMAILMSMHS is not null");
    		//stmt = con.prepareStatement("update EXT_CIVITAS set EMAILMSMHS='error@error.com' where EMAILMSMHS is null");
    		//stmt.executeUpdate();
    		//stmt = con.prepareStatement("update EXT_CIVITAS set EMAILMSMHS=LOWER(EMAILMSMHS),EMAILMSMHS=TRIM(EMAILMSMHS),EMAILMSMHS=TRIM(TRAILING '_' FROM EMAILMSMHS),EMAILMSMHS=REPLACE(EMAILMSMHS,' ','_'),EMAILMSMHS=concat(EMAILMSMHS,'@error.com') where EMAILMSMHS not like '%@%.%'");
    		//stmt.executeUpdate();
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
    
    public int  setDefaultKewarganegaraanIfNull(String nm_negara){
    	int updated = 0;
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
    		con = ds.getConnection();
    		String sql_cmd = "update CIVITAS set KEWARGANEGARAAN=? where (KEWARGANEGARAAN is null or char_length(KEWARGANEGARAAN)<1)";
    		stmt = con.prepareStatement(sql_cmd);
    		stmt.setString(1, nm_negara);
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
    
    public int updateNimhsFromExcel(Vector v_npm_nim) {
    	int updated = 0;
    	
    	if(v_npm_nim!=null && v_npm_nim.size()>0) {
    		StringTokenizer st = null;
    		ListIterator li = v_npm_nim.listIterator();
        	
        	try {
        		
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema()+"30min");
        		con = ds.getConnection();
        		stmt = con.prepareStatement("update CIVITAS set NIMHSMSMHS=? where NPMHSMSMHS=?");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			st = new StringTokenizer(brs,"`");
        			String npmhs = st.nextToken();
        			String nimhs = st.nextToken();
        			if(!Checker.isStringNullOrEmpty(nimhs)) {
        				nimhs = nimhs.replace("'", "");
        				nimhs = nimhs.trim();
        				stmt.setString(1, nimhs);
        				stmt.setString(2, npmhs);
        				updated = updated + stmt.executeUpdate();
        				//System.out.println(brs+"="+updated);
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

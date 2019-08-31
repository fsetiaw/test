package beans.dbase.spmi;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.Getter;
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
 * Session Bean implementation class SearchStandarMutu
 */
@Stateless
@LocalBean
public class SearchStandarMutu extends SearchDb {
	String operatorNpm;
	String operatorNmm;
	String tknOperatorNickname;
	boolean petugas;
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;  
	
	final String cmd_search_list_std_tamplet =
			"SELECT A.ID,A.ID_STD,A.PERNYATAAN_STD,A.NO_BUTIR,A.KDPST,A.RASIONALE,A.AKTIF,A.TGL_MULAI_AKTIF,A.TGL_STOP_AKTIF,A.SCOPE,A.TIPE_PROSES_PENGAWASAN,B.ID_VERSI,B.ID_STD_ISI,B.ID_MANUAL_PENETAPAN,B.ID_MANUAL_PELAKSANAAN,B.ID_MANUAL_EVALUASI,B.ID_MANUAL_PENGENDALIAN,B.ID_MANUAL_PENINGKATAN,B.TGL_STA,B.TGL_END,B.TARGET_THSMS_1,B.TARGET_THSMS_2,B.TARGET_THSMS_3,B.TARGET_THSMS_4,B.TARGET_THSMS_5,B.TARGET_THSMS_6,B.PIHAK_TERKAIT,B.DOKUMEN_TERKAIT,B.TKN_INDIKATOR,B.NO_URUT_TAMPIL,B.TARGET_PERIOD_START,B.UNIT_PERIOD_USED,B.LAMA_NOMINAL_PER_PERIOD,B.TARGET_THSMS_1_UNIT,B.TARGET_THSMS_2_UNIT,B.TARGET_THSMS_3_UNIT,B.TARGET_THSMS_4_UNIT,B.TARGET_THSMS_5_UNIT,B.TARGET_THSMS_6_UNIT,B.PIHAK_MONITOR,B.TKN_PARAMETER,B.AKTIF,B.KDPST,H.ID_MASTER_STD,H.ID_TIPE_STD FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on A.ID=B.ID_STD_ISI inner join STANDARD_TABLE H on A.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD where A.ID_STD is not null AND H.ID_MASTER_STD=? and H.ID_TIPE_STD=? and (A.AKTIF=true or A.AKTIF=false) and (A.KDPST='targetKdpst') "+
			"union all "+
			"SELECT C.ID,C.ID_STD,C.PERNYATAAN_STD,C.NO_BUTIR,C.KDPST,C.RASIONALE,C.AKTIF,C.TGL_MULAI_AKTIF,C.TGL_STOP_AKTIF,C.SCOPE,C.TIPE_PROSES_PENGAWASAN,D.ID_VERSI,D.ID_STD_ISI,D.ID_MANUAL_PENETAPAN,D.ID_MANUAL_PELAKSANAAN,D.ID_MANUAL_EVALUASI,D.ID_MANUAL_PENGENDALIAN,D.ID_MANUAL_PENINGKATAN,D.TGL_STA,D.TGL_END,D.TARGET_THSMS_1,D.TARGET_THSMS_2,D.TARGET_THSMS_3,D.TARGET_THSMS_4,D.TARGET_THSMS_5,D.TARGET_THSMS_6,D.PIHAK_TERKAIT,D.DOKUMEN_TERKAIT,D.TKN_INDIKATOR,D.NO_URUT_TAMPIL,D.TARGET_PERIOD_START,D.UNIT_PERIOD_USED,D.LAMA_NOMINAL_PER_PERIOD,D.TARGET_THSMS_1_UNIT,D.TARGET_THSMS_2_UNIT,D.TARGET_THSMS_3_UNIT,D.TARGET_THSMS_4_UNIT,D.TARGET_THSMS_5_UNIT,D.TARGET_THSMS_6_UNIT,D.PIHAK_MONITOR,D.TKN_PARAMETER,D.AKTIF,D.KDPST,H.ID_MASTER_STD,H.ID_TIPE_STD FROM STANDARD_ISI_TABLE C inner join STANDARD_VERSION D on C.ID=D.ID_STD_ISI inner join STANDARD_TABLE H on C.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD where C.ID_STD is not null AND H.ID_MASTER_STD=? and H.ID_TIPE_STD=? and (C.AKTIF=true or C.AKTIF=false) and C.KDPST is null "+ 
			"AND NOT EXISTS (SELECT * FROM STANDARD_ISI_TABLE E  inner join STANDARD_VERSION F on E.ID=F.ID_STD_ISI inner join STANDARD_TABLE H on E.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD  where H.ID_MASTER_STD=? and H.ID_TIPE_STD=? and (E.KDPST='targetKdpst') AND E.PERNYATAAN_STD=C.PERNYATAAN_STD AND F.TKN_INDIKATOR=D.TKN_INDIKATOR) order by PERNYATAAN_STD,TKN_INDIKATOR";
	
	final String cmd_search_list_std_for_editor =
			"SELECT A.ID,A.ID_STD,A.PERNYATAAN_STD,A.NO_BUTIR,A.KDPST,A.RASIONALE,A.AKTIF,A.TGL_MULAI_AKTIF,A.TGL_STOP_AKTIF,A.SCOPE,A.TIPE_PROSES_PENGAWASAN,B.ID_VERSI,B.ID_STD_ISI,B.ID_MANUAL_PENETAPAN,B.ID_MANUAL_PELAKSANAAN,B.ID_MANUAL_EVALUASI,B.ID_MANUAL_PENGENDALIAN,B.ID_MANUAL_PENINGKATAN,B.TGL_STA,B.TGL_END,B.TARGET_THSMS_1,B.TARGET_THSMS_2,B.TARGET_THSMS_3,B.TARGET_THSMS_4,B.TARGET_THSMS_5,B.TARGET_THSMS_6,B.PIHAK_TERKAIT,B.DOKUMEN_TERKAIT,B.TKN_INDIKATOR,B.NO_URUT_TAMPIL,B.TARGET_PERIOD_START,B.UNIT_PERIOD_USED,B.LAMA_NOMINAL_PER_PERIOD,B.TARGET_THSMS_1_UNIT,B.TARGET_THSMS_2_UNIT,B.TARGET_THSMS_3_UNIT,B.TARGET_THSMS_4_UNIT,B.TARGET_THSMS_5_UNIT,B.TARGET_THSMS_6_UNIT,B.PIHAK_MONITOR,B.TKN_PARAMETER,B.AKTIF,B.KDPST,H.ID_MASTER_STD,H.ID_TIPE_STD FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on A.ID=B.ID_STD_ISI inner join STANDARD_TABLE H on A.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD where A.ID_STD is not null AND H.ID_MASTER_STD=? and H.ID_TIPE_STD=? and (A.AKTIF=true or A.AKTIF=false) and (A.KDPST is null or A.KDPST is not null) order by A.PERNYATAAN_STD,B.TKN_INDIKATOR";
	
	
	final String cmd_search_list_std_tamplet_use_ID_STD =
			"SELECT A.ID,A.ID_STD,A.PERNYATAAN_STD,A.NO_BUTIR,A.KDPST,A.RASIONALE,A.AKTIF,A.TGL_MULAI_AKTIF,A.TGL_STOP_AKTIF,A.SCOPE,A.TIPE_PROSES_PENGAWASAN,B.ID_VERSI,B.ID_STD_ISI,B.ID_MANUAL_PENETAPAN,B.ID_MANUAL_PELAKSANAAN,B.ID_MANUAL_EVALUASI,B.ID_MANUAL_PENGENDALIAN,B.ID_MANUAL_PENINGKATAN,B.TGL_STA,B.TGL_END,B.TARGET_THSMS_1,B.TARGET_THSMS_2,B.TARGET_THSMS_3,B.TARGET_THSMS_4,B.TARGET_THSMS_5,B.TARGET_THSMS_6,B.PIHAK_TERKAIT,B.DOKUMEN_TERKAIT,B.TKN_INDIKATOR,B.NO_URUT_TAMPIL,B.TARGET_PERIOD_START,B.UNIT_PERIOD_USED,B.LAMA_NOMINAL_PER_PERIOD,B.TARGET_THSMS_1_UNIT,B.TARGET_THSMS_2_UNIT,B.TARGET_THSMS_3_UNIT,B.TARGET_THSMS_4_UNIT,B.TARGET_THSMS_5_UNIT,B.TARGET_THSMS_6_UNIT,B.PIHAK_MONITOR,B.TKN_PARAMETER,B.AKTIF,B.KDPST,H.ID_MASTER_STD,H.ID_TIPE_STD FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on A.ID=B.ID_STD_ISI inner join STANDARD_TABLE H on A.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD where A.ID_STD is not null AND H.ID_STD=? and (A.AKTIF=true or A.AKTIF=false) and (A.KDPST='targetKdpst') "+
			"union all "+
			"SELECT C.ID,C.ID_STD,C.PERNYATAAN_STD,C.NO_BUTIR,C.KDPST,C.RASIONALE,C.AKTIF,C.TGL_MULAI_AKTIF,C.TGL_STOP_AKTIF,C.SCOPE,C.TIPE_PROSES_PENGAWASAN,D.ID_VERSI,D.ID_STD_ISI,D.ID_MANUAL_PENETAPAN,D.ID_MANUAL_PELAKSANAAN,D.ID_MANUAL_EVALUASI,D.ID_MANUAL_PENGENDALIAN,D.ID_MANUAL_PENINGKATAN,D.TGL_STA,D.TGL_END,D.TARGET_THSMS_1,D.TARGET_THSMS_2,D.TARGET_THSMS_3,D.TARGET_THSMS_4,D.TARGET_THSMS_5,D.TARGET_THSMS_6,D.PIHAK_TERKAIT,D.DOKUMEN_TERKAIT,D.TKN_INDIKATOR,D.NO_URUT_TAMPIL,D.TARGET_PERIOD_START,D.UNIT_PERIOD_USED,D.LAMA_NOMINAL_PER_PERIOD,D.TARGET_THSMS_1_UNIT,D.TARGET_THSMS_2_UNIT,D.TARGET_THSMS_3_UNIT,D.TARGET_THSMS_4_UNIT,D.TARGET_THSMS_5_UNIT,D.TARGET_THSMS_6_UNIT,D.PIHAK_MONITOR,D.TKN_PARAMETER,D.AKTIF,D.KDPST,H.ID_MASTER_STD,H.ID_TIPE_STD FROM STANDARD_ISI_TABLE C inner join STANDARD_VERSION D on C.ID=D.ID_STD_ISI inner join STANDARD_TABLE H on C.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD where C.ID_STD is not null AND H.ID_STD=? and (C.AKTIF=true or C.AKTIF=false) and C.KDPST is null "+ 
			"AND NOT EXISTS (SELECT * FROM STANDARD_ISI_TABLE E  inner join STANDARD_VERSION F on E.ID=F.ID_STD_ISI inner join STANDARD_TABLE H on E.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD  where H.ID_STD=? and (E.KDPST='targetKdpst') AND E.PERNYATAAN_STD=C.PERNYATAAN_STD AND F.TKN_INDIKATOR=D.TKN_INDIKATOR) order by PERNYATAAN_STD,TKN_INDIKATOR";
	/*
	 * //mungkin deprecated, ngga tau da yg make pa ngga?
	final String cmd_search_list_std_for_editor_use_ID_STD =
			"SELECT A.ID,A.ID_STD,A.PERNYATAAN_STD,A.NO_BUTIR,A.KDPST,A.RASIONALE,A.AKTIF,A.TGL_MULAI_AKTIF,A.TGL_STOP_AKTIF,A.SCOPE,A.TIPE_PROSES_PENGAWASAN,B.ID_VERSI,B.ID_STD_ISI,B.ID_MANUAL_PENETAPAN,B.ID_MANUAL_PELAKSANAAN,B.ID_MANUAL_EVALUASI,B.ID_MANUAL_PENGENDALIAN,B.ID_MANUAL_PENINGKATAN,B.TGL_STA,B.TGL_END,B.TARGET_THSMS_1,B.TARGET_THSMS_2,B.TARGET_THSMS_3,B.TARGET_THSMS_4,B.TARGET_THSMS_5,B.TARGET_THSMS_6,B.PIHAK_TERKAIT,B.DOKUMEN_TERKAIT,B.TKN_INDIKATOR,B.NO_URUT_TAMPIL,B.TARGET_PERIOD_START,B.UNIT_PERIOD_USED,B.LAMA_NOMINAL_PER_PERIOD,B.TARGET_THSMS_1_UNIT,B.TARGET_THSMS_2_UNIT,B.TARGET_THSMS_3_UNIT,B.TARGET_THSMS_4_UNIT,B.TARGET_THSMS_5_UNIT,B.TARGET_THSMS_6_UNIT,B.PIHAK_MONITOR,B.TKN_PARAMETER,B.AKTIF,B.KDPST,H.ID_MASTER_STD,H.ID_TIPE_STD FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on A.ID=B.ID_STD_ISI inner join STANDARD_TABLE H on A.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD where A.ID_STD is not null AND H.ID_STD=? and (A.AKTIF=true or A.AKTIF=false) and (A.KDPST is null or A.KDPST is not null) order by A.PERNYATAAN_STD,B.TKN_INDIKATOR";

	*/
	
	final String cmd_search_list_std_tamplet_based_on_pernyataan_isi_std =
			"SELECT A.ID,A.ID_STD,A.PERNYATAAN_STD,A.NO_BUTIR,A.KDPST,A.RASIONALE,A.AKTIF,A.TGL_MULAI_AKTIF,A.TGL_STOP_AKTIF,A.SCOPE,A.TIPE_PROSES_PENGAWASAN,B.ID_VERSI,B.ID_STD_ISI,B.ID_MANUAL_PENETAPAN,B.ID_MANUAL_PELAKSANAAN,B.ID_MANUAL_EVALUASI,B.ID_MANUAL_PENGENDALIAN,B.ID_MANUAL_PENINGKATAN,B.TGL_STA,B.TGL_END,B.TARGET_THSMS_1,B.TARGET_THSMS_2,B.TARGET_THSMS_3,B.TARGET_THSMS_4,B.TARGET_THSMS_5,B.TARGET_THSMS_6,B.PIHAK_TERKAIT,B.DOKUMEN_TERKAIT,B.TKN_INDIKATOR,B.NO_URUT_TAMPIL,B.TARGET_PERIOD_START,B.UNIT_PERIOD_USED,B.LAMA_NOMINAL_PER_PERIOD,B.TARGET_THSMS_1_UNIT,B.TARGET_THSMS_2_UNIT,B.TARGET_THSMS_3_UNIT,B.TARGET_THSMS_4_UNIT,B.TARGET_THSMS_5_UNIT,B.TARGET_THSMS_6_UNIT,B.PIHAK_MONITOR,B.TKN_PARAMETER,B.AKTIF,B.KDPST,H.ID_MASTER_STD,H.ID_TIPE_STD FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on A.ID=B.ID_STD_ISI inner join STANDARD_TABLE H on A.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD where A.ID_STD is not null AND H.ID_MASTER_STD=? and H.ID_TIPE_STD=? and (A.AKTIF=true or A.AKTIF=false) and (A.KDPST='targetKdpst') "+
			"union all "+
			"SELECT C.ID,C.ID_STD,C.PERNYATAAN_STD,C.NO_BUTIR,C.KDPST,C.RASIONALE,C.AKTIF,C.TGL_MULAI_AKTIF,C.TGL_STOP_AKTIF,C.SCOPE,C.TIPE_PROSES_PENGAWASAN,D.ID_VERSI,D.ID_STD_ISI,D.ID_MANUAL_PENETAPAN,D.ID_MANUAL_PELAKSANAAN,D.ID_MANUAL_EVALUASI,D.ID_MANUAL_PENGENDALIAN,D.ID_MANUAL_PENINGKATAN,D.TGL_STA,D.TGL_END,D.TARGET_THSMS_1,D.TARGET_THSMS_2,D.TARGET_THSMS_3,D.TARGET_THSMS_4,D.TARGET_THSMS_5,D.TARGET_THSMS_6,D.PIHAK_TERKAIT,D.DOKUMEN_TERKAIT,D.TKN_INDIKATOR,D.NO_URUT_TAMPIL,D.TARGET_PERIOD_START,D.UNIT_PERIOD_USED,D.LAMA_NOMINAL_PER_PERIOD,D.TARGET_THSMS_1_UNIT,D.TARGET_THSMS_2_UNIT,D.TARGET_THSMS_3_UNIT,D.TARGET_THSMS_4_UNIT,D.TARGET_THSMS_5_UNIT,D.TARGET_THSMS_6_UNIT,D.PIHAK_MONITOR,D.TKN_PARAMETER,D.AKTIF,D.KDPST,H.ID_MASTER_STD,H.ID_TIPE_STD FROM STANDARD_ISI_TABLE C inner join STANDARD_VERSION D on C.ID=D.ID_STD_ISI inner join STANDARD_TABLE H on C.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD where C.ID_STD is not null AND H.ID_MASTER_STD=? and H.ID_TIPE_STD=? and (C.AKTIF=true or C.AKTIF=false) and C.KDPST is null "+ 
			"AND NOT EXISTS (SELECT * FROM STANDARD_ISI_TABLE E  inner join STANDARD_VERSION F on E.ID=F.ID_STD_ISI inner join STANDARD_TABLE H on E.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD  where H.ID_MASTER_STD=? and H.ID_TIPE_STD=? and (E.KDPST='targetKdpst') AND E.PERNYATAAN_STD=C.PERNYATAAN_STD AND F.TKN_INDIKATOR=D.TKN_INDIKATOR) and PERNYATAAN_STD=? order by PERNYATAAN_STD,TKN_INDIKATOR";

	final String cmd_search_list_std_tamplet_use_ID_STD_select_all_for_editor =
			"SELECT * FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on A.ID=B.ID_STD_ISI inner join STANDARD_TABLE H on A.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD where A.ID_STD is not null AND H.ID_STD=? and (A.AKTIF=true or A.AKTIF=false) and (A.KDPST='targetKdpst' or A.KDPST is null) order by PERNYATAAN_STD,TKN_INDIKATOR";
	
	final String cmd_search_list_std_tamplet_use_ID_STD_select_all_aktifonly_for_editor =
			"SELECT * FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on A.ID=B.ID_STD_ISI inner join STANDARD_TABLE H on A.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD where A.ID_STD is not null AND H.ID_STD=? and (A.AKTIF=true or A.AKTIF=true) and (A.KDPST='targetKdpst' or A.KDPST is null) order by PERNYATAAN_STD,TKN_INDIKATOR";
	
	final String cmd_search_list_std_tamplet_use_ID_STD_select_all =
			"SELECT * FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on A.ID=B.ID_STD_ISI inner join STANDARD_TABLE H on A.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD where A.ID_STD is not null AND H.ID_STD=? and (A.AKTIF=true or A.AKTIF=false) and (A.KDPST='targetKdpst') "+
			"union all "+
			"SELECT * FROM STANDARD_ISI_TABLE C inner join STANDARD_VERSION D on C.ID=D.ID_STD_ISI inner join STANDARD_TABLE H on C.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD where C.ID_STD is not null AND H.ID_STD=? and (C.AKTIF=true or C.AKTIF=false) and C.KDPST is null "+ 
			//"AND NOT EXISTS (SELECT * FROM STANDARD_ISI_TABLE E  inner join STANDARD_VERSION F on E.ID=F.ID_STD_ISI inner join STANDARD_TABLE H on E.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD  where H.ID_STD=? and (E.KDPST='targetKdpst') AND E.PERNYATAAN_STD=C.PERNYATAAN_STD AND F.TKN_INDIKATOR=D.TKN_INDIKATOR) order by PERNYATAAN_STD,TKN_INDIKATOR";
			"AND NOT EXISTS (SELECT * FROM STANDARD_ISI_TABLE E  inner join STANDARD_VERSION F on E.ID=F.ID_STD_ISI inner join STANDARD_TABLE H on E.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD  where H.ID_STD=? and (E.KDPST='targetKdpst') AND E.PERNYATAAN_STD=C.PERNYATAAN_STD) order by PERNYATAAN_STD,TKN_INDIKATOR";
			
	final String cmd_search_list_std_tamplet_use_ID_STD_select_all_aktifonly =
			"SELECT * FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on A.ID=B.ID_STD_ISI inner join STANDARD_TABLE H on A.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD where A.ID_STD is not null AND H.ID_STD=? and (A.AKTIF=true or A.AKTIF=true) and (A.KDPST='targetKdpst') "+
			"union all "+
			"SELECT * FROM STANDARD_ISI_TABLE C inner join STANDARD_VERSION D on C.ID=D.ID_STD_ISI inner join STANDARD_TABLE H on C.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD where C.ID_STD is not null AND H.ID_STD=? and (C.AKTIF=true or C.AKTIF=true) and C.KDPST is null "+
			//"AND NOT EXISTS (SELECT * FROM STANDARD_ISI_TABLE E  inner join STANDARD_VERSION F on E.ID=F.ID_STD_ISI inner join STANDARD_TABLE H on E.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD  where H.ID_STD=? and (E.KDPST='targetKdpst') AND E.PERNYATAAN_STD=C.PERNYATAAN_STD AND F.TKN_INDIKATOR=D.TKN_INDIKATOR) order by PERNYATAAN_STD,TKN_INDIKATOR";
			"AND NOT EXISTS (SELECT * FROM STANDARD_ISI_TABLE E  inner join STANDARD_VERSION F on E.ID=F.ID_STD_ISI inner join STANDARD_TABLE H on E.ID_STD=H.ID_STD inner join STANDARD_MASTER_TABLE I ON H.ID_MASTER_STD=I.ID_MASTER_STD  where H.ID_STD=? and (E.KDPST='targetKdpst') AND E.PERNYATAAN_STD=C.PERNYATAAN_STD) order by PERNYATAAN_STD,TKN_INDIKATOR";
	//		
;    /**
     * @see SearchDb#SearchDb()
     */
    public SearchStandarMutu() {
        super();
        //TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchStandarMutu(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        //TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(Connection)
     */
    public SearchStandarMutu(Connection con) {
        super(con);
        //TODO Auto-generated constructor stub
    }

    
    public Vector getListInfoStandar() {
    	Vector v = null;
    	v = getListInfoStandar(true);
    	/*
    	
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("SELECT A.ID_MASTER_STD,A.KET_TIPE_STD,B.ID_STD,B.ID_TIPE_STD,B.KET_TIPE_STD FROM STANDARD_MASTER_TABLE A inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD order by A.ID_MASTER_STD,B.ID_TIPE_STD");
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		
        		v = new Vector();
        		li = v.listIterator();
        		do {
        			int i=1;
        			String id_master = ""+rs.getString(i++);
        			String ket_master_std = ""+rs.getString(i++);
        			String id_std = ""+rs.getString(i++);
        			String id_tipe_std = ""+rs.getString(i++);
        			String ket_tipe_std = ""+rs.getString(i++);
        			String tmp = id_master+"~"+ket_master_std+"~"+id_std+"~"+id_tipe_std+"~"+ket_tipe_std;
        			if(tmp.startsWith("~")) {
        				tmp = "null"+ket_tipe_std;
        			}
        			if(tmp.endsWith("~")) {
        				tmp = ket_tipe_std+"null";
        			}
        			while(tmp.contains("~~")) {
        				tmp =tmp.replace("~~", "~null~");
        			}
        			li.add(tmp);
        		}
        		while(rs.next());
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
    	*/
    	return v;
    }
    
    public Vector getListInfoStandar(boolean include_yg_blum_ditentukan) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(include_yg_blum_ditentukan) {
        		stmt = con.prepareStatement("SELECT A.ID_MASTER_STD,A.KET_TIPE_STD,B.ID_STD,B.ID_TIPE_STD,B.KET_TIPE_STD FROM STANDARD_MASTER_TABLE A inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD order by A.ID_MASTER_STD,B.ID_TIPE_STD");	
        	}
        	else {
        		stmt = con.prepareStatement("SELECT A.ID_MASTER_STD,A.KET_TIPE_STD,B.ID_STD,B.ID_TIPE_STD,B.KET_TIPE_STD FROM STANDARD_MASTER_TABLE A inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD where A.KET_TIPE_STD<>'STANDAR BELUM DITENTUKAN' order by A.ID_MASTER_STD,B.ID_TIPE_STD");
        	}
        	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		
        		v = new Vector();
        		li = v.listIterator();
        		do {
        			int i=1;
        			String id_master = ""+rs.getString(i++);
        			String ket_master_std = ""+rs.getString(i++);
        			String id_std = ""+rs.getString(i++);
        			String id_tipe_std = ""+rs.getString(i++);
        			String ket_tipe_std = ""+rs.getString(i++);
        			String tmp = id_master+"~"+ket_master_std+"~"+id_std+"~"+id_tipe_std+"~"+ket_tipe_std;
        			if(tmp.startsWith("~")) {
        				tmp = "null"+ket_tipe_std;
        			}
        			if(tmp.endsWith("~")) {
        				tmp = ket_tipe_std+"null";
        			}
        			while(tmp.contains("~~")) {
        				tmp =tmp.replace("~~", "~null~");
        			}
        			li.add(tmp);
        		}
        		while(rs.next());
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
    
    public Vector getListMasterBookOfStandar() {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select * from STANDARD_MASTER_TABLE  order by ID_MASTER_STD");
        	rs = stmt.executeQuery();
        	while(rs.next()) {
        		int id_std = rs.getInt("ID_MASTER_STD");
        		String ket_tipe_std = rs.getString("KET_TIPE_STD");
        		li.add(id_std+"`"+ket_tipe_std);
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
 
    public Vector getPihakTerkaitDanPihakMonitorStd(int id_versi, int id_std_isi) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select PIHAK_TERKAIT,PIHAK_MONITOR from STANDARD_VERSION where ID_VERSI=? and ID_STD_ISI=?");
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		String terkait = rs.getString(1);
        		String monitoree = rs.getString(2);
        		li.add(terkait);
        		li.add(monitoree);
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
    
    
    public String getParameterDanIndikator(int id_versi, int id_std_isi) {
    	String parameter_indikator = null;
    	
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TKN_PARAMETER,TKN_INDIKATOR from STANDARD_VERSION where ID_VERSI=? and ID_STD_ISI=?");
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std_isi);
        	rs = stmt.executeQuery();
        	while(rs.next()) {
        		String param = rs.getString(1);
        		String indi = rs.getString(2);
        		parameter_indikator = new String(param+"`"+indi);
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
    	return parameter_indikator;
    }	
    
    public Vector getListStandarGivenIdMaster(int id_master) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select * from STANDARD_TABLE  where ID_MASTER_STD=? order by ID_TIPE_STD");
        	stmt.setInt(1, id_master);
        	rs = stmt.executeQuery();
        	while(rs.next()) {
        		int id_std = rs.getInt("ID_TIPE_STD");
        		String ket_tipe_std = rs.getString("KET_TIPE_STD");
        		li.add(id_master+"`"+id_std+"`"+ket_tipe_std);
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
    
    public Vector getListSemuaStandar(String token_selected_columns_seperated_by_koma) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		StringTokenizer st = new StringTokenizer(token_selected_columns_seperated_by_koma,",");
    		int count_token = st.countTokens();
    		
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select "+token_selected_columns_seperated_by_koma+" from STANDARD_TABLE where KET_TIPE_STD<>'BELUM DITENTUKAN' order by ID_MASTER_STD,ID_STD");
        	rs = stmt.executeQuery();
        	while(rs.next()) {
        		if(v==null) {
        			v = new Vector();
        			li = v.listIterator();		
        		}
        		
        		String tmp = "";
        		for(int i=1;i<=count_token;i++) {
        			tmp = tmp+rs.getString(i)+"~";
        		}
        		tmp = tmp.substring(0, tmp.length()-1);
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
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
    
    public Vector getListStandarIsi(boolean editor, int usr_objid) {
    	Vector v = null;
    	ListIterator li = null;
    	getListStandarIsi(editor, usr_objid,0 ,0);
    	/*
    	try {
    		
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("SELECT * FROM STANDARD_ISI_TABLE inner join STANDARD_VERSION on ID=ID_STD_ISI where ID_STD is not null order by ID;");
        	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		//rs.getInt("ID"); = ID_VERSI
        		//rs.getInt("ID_STD"); = ID_STD_ISI
        		String id_versi=""+rs.getInt("ID_VERSI");
        		if(Checker.isStringNullOrEmpty(id_versi)) {
        			id_versi = "null";
        		}
        		String id_std_isi=""+rs.getInt("ID_STD_ISI");
        		String id_std = ""+rs.getInt("ID_STD"); 
        		String isi_std=""+rs.getString("PERNYATAAN_STD");
        		String butir=""+rs.getInt("NO_BUTIR");
        		String kdpst=""+rs.getString("KDPST");
        		String rasionale=""+rs.getString("RASIONALE");
        		String aktif=""+rs.getBoolean("AKTIF");
        		String tgl_mulai_aktif=""+rs.getDate("TGL_MULAI_AKTIF");
        		String tgl_stop_aktif=""+rs.getDate("TGL_STOP_AKTIF");
        		String cakupan = ""+rs.getString("SCOPE");
        		String tipe_survey = ""+rs.getString("TIPE_PROSES_PENGAWASAN");
        		
        		String tgl_sta=""+rs.getDate("TGL_STA");
        		String tgl_end=""+rs.getDate("TGL_END");
        		String target1=""+rs.getString("TARGET_THSMS_1");
        		String target2=""+rs.getString("TARGET_THSMS_2");
        		String target3=""+rs.getString("TARGET_THSMS_3");
        		String target4=""+rs.getString("TARGET_THSMS_4");
        		String target5=""+rs.getString("TARGET_THSMS_5");
        		String target6=""+rs.getString("TARGET_THSMS_6");
        		String pihak=""+rs.getString("PIHAK_TERKAIT");
        		String dokumen=""+rs.getString("DOKUMEN_TERKAIT");
        		String indikator=""+rs.getString("TKN_INDIKATOR");
        		String periode_start=""+rs.getString("TARGET_PERIOD_START");
        		String unit_used_by_periode_start=""+rs.getString("UNIT_PERIOD_USED");
        		String besaran_interval_per_period=""+rs.getInt("LAMA_NOMINAL_PER_PERIOD");
        		String unit_used_byTarget=""+rs.getString("TARGET_THSMS_1_UNIT");
        		String pengawas=""+rs.getString("PIHAK_MONITOR");
        		String param=""+rs.getString("TKN_PARAMETER");
        		if(Checker.isStringNullOrEmpty(tipe_survey)) {
        			tipe_survey = "null";
        		}
        		String tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey;
        		li.add(tmp);
        		while(rs.next()) {
        			id_versi=""+rs.getInt("ID_VERSI");
            		if(Checker.isStringNullOrEmpty(id_versi)) {
            			id_versi = "null";
            		}
            		id_std_isi=""+rs.getInt("ID_STD_ISI");
            		id_std = ""+rs.getInt("ID_STD");
            		isi_std=""+rs.getString("PERNYATAAN_STD");
            		butir=""+rs.getInt("NO_BUTIR");
            		kdpst=""+rs.getString("KDPST");
            		rasionale=""+rs.getString("RASIONALE");
            		aktif=""+rs.getBoolean("AKTIF");
            		tgl_mulai_aktif=""+rs.getDate("TGL_MULAI_AKTIF");
            		tgl_stop_aktif=""+rs.getDate("TGL_STOP_AKTIF");
            		cakupan = ""+rs.getString("SCOPE");
            		tipe_survey = ""+rs.getString("TIPE_PROSES_PENGAWASAN");
            		tgl_sta=""+rs.getDate("TGL_STA");
            		tgl_end=""+rs.getDate("TGL_END");
            		target1=""+rs.getString("TARGET_THSMS_1");
            		target2=""+rs.getString("TARGET_THSMS_2");
            		target3=""+rs.getString("TARGET_THSMS_3");
            		target4=""+rs.getString("TARGET_THSMS_4");
            		target5=""+rs.getString("TARGET_THSMS_5");
            		target6=""+rs.getString("TARGET_THSMS_6");
            		pihak=""+rs.getString("PIHAK_TERKAIT");
            		dokumen=""+rs.getString("DOKUMEN_TERKAIT");
            		indikator=""+rs.getString("TKN_INDIKATOR");
            		periode_start=""+rs.getString("TARGET_PERIOD_START");
            		unit_used_by_periode_start=""+rs.getString("UNIT_PERIOD_USED");
            		besaran_interval_per_period=""+rs.getInt("LAMA_NOMINAL_PER_PERIOD");
            		unit_used_byTarget=""+rs.getString("TARGET_THSMS_1_UNIT");
            		pengawas=""+rs.getString("PIHAK_MONITOR");
            		param=""+rs.getString("TKN_PARAMETER");
            		if(Checker.isStringNullOrEmpty(tipe_survey)) {
            			tipe_survey = "null";
            		}
            		tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey;
            		li.add(tmp);
        		}
        		if(!editor) {
        			String list_jabatan = Getter.listAllNamaJabatanOnly(usr_objid);
        			li = v.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				//id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey;
        				id_versi=st.nextToken();
        				id_std_isi=st.nextToken();
        				id_std=st.nextToken();
        				isi_std=st.nextToken();
        				butir=st.nextToken();
        				kdpst=st.nextToken();
        				rasionale=st.nextToken();
        				tgl_sta=st.nextToken();
        				tgl_end=st.nextToken();
        				target1=st.nextToken();
        				target2=st.nextToken();
        				target3=st.nextToken();
        				target4=st.nextToken();
        				target5=st.nextToken();
        				target6=st.nextToken();
        				pihak=st.nextToken();
        				if(!Checker.isStringNullOrEmpty(pihak)) {
        					pihak = pihak.replace(",", "`");
        					pihak = "`"+pihak+"`";
        				}
        				dokumen=st.nextToken();
        				indikator=st.nextToken();
        				periode_start=st.nextToken();
        				unit_used_by_periode_start=st.nextToken();
        				besaran_interval_per_period=st.nextToken();
        				unit_used_byTarget=st.nextToken();
        				pengawas=st.nextToken();
        				if(!Checker.isStringNullOrEmpty(pengawas)) {
        					pengawas = pengawas.replace(",", "`");
        					pengawas = "`"+pengawas+"`";
        				}
        				boolean terkait = false;
        				if(!Checker.isStringNullOrEmpty(list_jabatan)) {
        					
        					if(!list_jabatan.startsWith("`")) {
        						list_jabatan="`"+list_jabatan;
        					}
        					if(!list_jabatan.endsWith("`")) {
        						list_jabatan=list_jabatan+"`";
        					}
        					st = new StringTokenizer(list_jabatan,"`");
        					while(st.hasMoreTokens() && !terkait) {
        						String usr_jabatan = st.nextToken();
        						if(pengawas.contains("`"+usr_jabatan+"`")||pihak.contains("`"+usr_jabatan+"`")) {
        							terkait = true;
        						}
        					}
        				}
        				if(!terkait) {
        					li.remove();
        				}
        			}
        		}
        	}
        	
    	}
        catch (NamingException e) {
        	e.printStackTrace();
        }
        
        catch (Exception ex) {
        	ex.printStackTrace();
        } 
        finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    	    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    	    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	*/
    	return v;
    }	
    
    public Vector getListStandarIsi(boolean editor, int usr_objid, int id_master_std, int id_tipe_std) {
    	Vector v = null;
    	ListIterator li = null;
    	//Vector v_scope_kdpst = "";
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//cmd_search_list_std 
        	if(id_master_std>0 || id_master_std==0) {
        		stmt = con.prepareStatement("SELECT A.ID,A.ID_STD,A.PERNYATAAN_STD,A.NO_BUTIR,A.KDPST,A.RASIONALE,A.AKTIF,A.TGL_MULAI_AKTIF,A.TGL_STOP_AKTIF,A.SCOPE,A.TIPE_PROSES_PENGAWASAN,B.ID_VERSI,B.ID_STD_ISI,B.ID_MANUAL_PENETAPAN,B.ID_MANUAL_PELAKSANAAN,B.ID_MANUAL_EVALUASI,B.ID_MANUAL_PENGENDALIAN,B.ID_MANUAL_PENINGKATAN,B.TGL_STA,B.TGL_END,B.TARGET_THSMS_1,B.TARGET_THSMS_2,B.TARGET_THSMS_3,B.TARGET_THSMS_4,B.TARGET_THSMS_5,B.TARGET_THSMS_6,B.PIHAK_TERKAIT,B.DOKUMEN_TERKAIT,B.TKN_INDIKATOR,B.NO_URUT_TAMPIL,B.TARGET_PERIOD_START,B.UNIT_PERIOD_USED,B.LAMA_NOMINAL_PER_PERIOD,B.TARGET_THSMS_1_UNIT,B.TARGET_THSMS_2_UNIT,B.TARGET_THSMS_3_UNIT,B.TARGET_THSMS_4_UNIT,B.TARGET_THSMS_5_UNIT,B.TARGET_THSMS_6_UNIT,B.PIHAK_MONITOR,B.TKN_PARAMETER,B.AKTIF,B.KDPST,D.ID_MASTER_STD,ID_TIPE_STD FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on ID=ID_STD_ISI inner join STANDARD_TABLE C on A.ID_STD=C.ID_STD inner join STANDARD_MASTER_TABLE D ON C.ID_MASTER_STD=D.ID_MASTER_STD where A.ID_STD is not null AND D.ID_MASTER_STD=? and ID_TIPE_STD=? order by A.PERNYATAAN_STD,B.TKN_INDIKATOR");	
        		stmt.setInt(1, id_master_std);
        		stmt.setInt(2, id_tipe_std);
        	}
        	else {
        		//kayaknya ngga sampe sini
        		stmt = con.prepareStatement("SELECT A.ID,A.ID_STD,A.PERNYATAAN_STD,A.NO_BUTIR,A.KDPST,A.RASIONALE,A.AKTIF,A.TGL_MULAI_AKTIF,A.TGL_STOP_AKTIF,A.SCOPE,A.TIPE_PROSES_PENGAWASAN,B.ID_VERSI,B.ID_STD_ISI,B.ID_MANUAL_PENETAPAN,B.ID_MANUAL_PELAKSANAAN,B.ID_MANUAL_EVALUASI,B.ID_MANUAL_PENGENDALIAN,B.ID_MANUAL_PENINGKATAN,B.TGL_STA,B.TGL_END,B.TARGET_THSMS_1,B.TARGET_THSMS_2,B.TARGET_THSMS_3,B.TARGET_THSMS_4,B.TARGET_THSMS_5,B.TARGET_THSMS_6,B.PIHAK_TERKAIT,B.DOKUMEN_TERKAIT,B.TKN_INDIKATOR,B.NO_URUT_TAMPIL,B.TARGET_PERIOD_START,B.UNIT_PERIOD_USED,B.LAMA_NOMINAL_PER_PERIOD,B.TARGET_THSMS_1_UNIT,B.TARGET_THSMS_2_UNIT,B.TARGET_THSMS_3_UNIT,B.TARGET_THSMS_4_UNIT,B.TARGET_THSMS_5_UNIT,B.TARGET_THSMS_6_UNIT,B.PIHAK_MONITOR,B.TKN_PARAMETER,B.AKTIF,B.KDPST,D.ID_MASTER_STD,ID_TIPE_STD FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on ID=ID_STD_ISI inner join STANDARD_TABLE C on A.ID_STD=C.ID_STD inner join STANDARD_MASTER_TABLE D ON C.ID_MASTER_STD=D.ID_MASTER_STD where A.ID_STD is not null order by A.PERNYATAAN_STD,B.TKN_INDIKATOR");
        		//;
        		//stmt = con.prepareStatement("SELECT * FROM STANDARD_ISI_TABLE inner join STANDARD_VERSION on ID=ID_STD_ISI where ID_STD is not null order by ID;");
        	}
        	
        	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		//rs.getInt("ID"); = ID_VERSI
        		//rs.getInt("ID_STD"); = ID_STD_ISI
        		String id_versi=""+rs.getInt("ID_VERSI");
        		if(Checker.isStringNullOrEmpty(id_versi)) {
        			id_versi = "null";
        		}
        		String id_std_isi=""+rs.getInt("ID_STD_ISI");
        		String id_std = ""+rs.getInt("ID_STD"); 
        		String isi_std=""+rs.getString("PERNYATAAN_STD");
        		String butir=""+rs.getInt("NO_BUTIR");
        		String kdpst=""+rs.getString("KDPST");
        		String rasionale=""+rs.getString("RASIONALE");
        		String aktif=""+rs.getBoolean("AKTIF");
        		String tgl_mulai_aktif=""+rs.getDate("TGL_MULAI_AKTIF");
        		String tgl_stop_aktif=""+rs.getDate("TGL_STOP_AKTIF");
        		String cakupan = ""+rs.getString("SCOPE");
        		String tipe_survey = ""+rs.getString("TIPE_PROSES_PENGAWASAN");
        		
        		String tgl_sta=""+rs.getDate("TGL_STA");
        		String tgl_end=""+rs.getDate("TGL_END");
        		String target1=""+rs.getString("TARGET_THSMS_1");
        		String target2=""+rs.getString("TARGET_THSMS_2");
        		String target3=""+rs.getString("TARGET_THSMS_3");
        		String target4=""+rs.getString("TARGET_THSMS_4");
        		String target5=""+rs.getString("TARGET_THSMS_5");
        		String target6=""+rs.getString("TARGET_THSMS_6");
        		String pihak=""+rs.getString("PIHAK_TERKAIT");
        		String dokumen=""+rs.getString("DOKUMEN_TERKAIT");
        		String indikator=""+rs.getString("TKN_INDIKATOR");
        		String periode_start=""+rs.getString("TARGET_PERIOD_START");
        		String unit_used_by_periode_start=""+rs.getString("UNIT_PERIOD_USED");
        		String besaran_interval_per_period=""+rs.getInt("LAMA_NOMINAL_PER_PERIOD");
        		String unit_used_byTarget=""+rs.getString("TARGET_THSMS_1_UNIT");
        		String pengawas=""+rs.getString("PIHAK_MONITOR");
        		String param=""+rs.getString("TKN_PARAMETER");
        		//if(Checker.isStringNullOrEmpty(tipe_survey)) {
        		//	tipe_survey = "null";
        		//}
        		String id_master = "0";//default
        		try {
        			id_master=""+rs.getString("ID_MASTER_STD");
        		}
        		catch (Exception e) {}
        		String id_tipe = "0";//default
        		try {
        			id_tipe=""+rs.getString("ID_TIPE_STD");
        		}
        		catch(Exception e) {}
        		
        		//if(Checker.isStringNullOrEmpty(id_tipe)) {
        		//	id_tipe = "null";
        		//}
        		
        		String tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey+"`"+id_master+"`"+id_tipe;
        		li.add(tmp);
        		while(rs.next()) {
        			id_versi=""+rs.getInt("ID_VERSI");
            		if(Checker.isStringNullOrEmpty(id_versi)) {
            			id_versi = "null";
            		}
            		id_std_isi=""+rs.getInt("ID_STD_ISI");
            		id_std = ""+rs.getInt("ID_STD");
            		isi_std=""+rs.getString("PERNYATAAN_STD");
            		butir=""+rs.getInt("NO_BUTIR");
            		kdpst=""+rs.getString("KDPST");
            		rasionale=""+rs.getString("RASIONALE");
            		aktif=""+rs.getBoolean("AKTIF");
            		tgl_mulai_aktif=""+rs.getDate("TGL_MULAI_AKTIF");
            		tgl_stop_aktif=""+rs.getDate("TGL_STOP_AKTIF");
            		cakupan = ""+rs.getString("SCOPE");
            		tipe_survey = ""+rs.getString("TIPE_PROSES_PENGAWASAN");
            		tgl_sta=""+rs.getDate("TGL_STA");
            		tgl_end=""+rs.getDate("TGL_END");
            		target1=""+rs.getString("TARGET_THSMS_1");
            		target2=""+rs.getString("TARGET_THSMS_2");
            		target3=""+rs.getString("TARGET_THSMS_3");
            		target4=""+rs.getString("TARGET_THSMS_4");
            		target5=""+rs.getString("TARGET_THSMS_5");
            		target6=""+rs.getString("TARGET_THSMS_6");
            		pihak=""+rs.getString("PIHAK_TERKAIT");
            		dokumen=""+rs.getString("DOKUMEN_TERKAIT");
            		indikator=""+rs.getString("TKN_INDIKATOR");
            		periode_start=""+rs.getString("TARGET_PERIOD_START");
            		unit_used_by_periode_start=""+rs.getString("UNIT_PERIOD_USED");
            		besaran_interval_per_period=""+rs.getInt("LAMA_NOMINAL_PER_PERIOD");
            		unit_used_byTarget=""+rs.getString("TARGET_THSMS_1_UNIT");
            		pengawas=""+rs.getString("PIHAK_MONITOR");
            		param=""+rs.getString("TKN_PARAMETER");
            		//if(Checker.isStringNullOrEmpty(tipe_survey)) {
            		//	tipe_survey = "null";
            		//}
            		id_master = "0";//default
            		try {
            			id_master=""+rs.getString("ID_MASTER_STD");
            		}
            		catch (Exception e) {}
            		id_tipe = "0";//default
            		try {
            			id_tipe=""+rs.getString("ID_TIPE_STD");
            		}
            		catch(Exception e) {}
            		tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey+"`"+id_master+"`"+id_tipe;
            		li.add(tmp);
        		}
        		if(!editor) {
        			String list_jabatan = Getter.listAllNamaJabatanOnly(usr_objid);
        			li = v.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				//id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey;
        				id_versi=st.nextToken();
        				id_std_isi=st.nextToken();
        				id_std=st.nextToken();
        				isi_std=st.nextToken();
        				butir=st.nextToken();
        				kdpst=st.nextToken();
        				rasionale=st.nextToken();
        				tgl_sta=st.nextToken();
        				tgl_end=st.nextToken();
        				target1=st.nextToken();
        				target2=st.nextToken();
        				target3=st.nextToken();
        				target4=st.nextToken();
        				target5=st.nextToken();
        				target6=st.nextToken();
        				pihak=st.nextToken();
        				if(!Checker.isStringNullOrEmpty(pihak)) {
        					pihak = pihak.replace(",", "`");
        					pihak = "`"+pihak+"`";
        				}
        				dokumen=st.nextToken();
        				indikator=st.nextToken();
        				periode_start=st.nextToken();
        				unit_used_by_periode_start=st.nextToken();
        				besaran_interval_per_period=st.nextToken();
        				unit_used_byTarget=st.nextToken();
        				pengawas=st.nextToken();
        				if(!Checker.isStringNullOrEmpty(pengawas)) {
        					pengawas = pengawas.replace(",", "`");
        					pengawas = "`"+pengawas+"`";
        				}
        				boolean terkait = false;
        				if(!Checker.isStringNullOrEmpty(list_jabatan)) {
        					
        					if(!list_jabatan.startsWith("`")) {
        						list_jabatan="`"+list_jabatan;
        					}
        					if(!list_jabatan.endsWith("`")) {
        						list_jabatan=list_jabatan+"`";
        					}
        					st = new StringTokenizer(list_jabatan,"`");
        					while(st.hasMoreTokens() && !terkait) {
        						String usr_jabatan = st.nextToken();
        						if(pengawas.contains("`"+usr_jabatan+"`")||pihak.contains("`"+usr_jabatan+"`")) {
        							terkait = true;
        						}
        					}
        				}
        				if(!terkait) {
        					li.remove();
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
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    	    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    	    if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	return v;
    }
    
        
    public Vector getListStandarIsi(boolean editor, String target_kdpst, int usr_objid, int id_master_std, int id_tipe_std) {
    	Vector v = null;
    	ListIterator li = null;
    	//Vector v_scope_kdpst = "";

   		
    	if(!Checker.isStringNullOrEmpty(target_kdpst)) {
    		String tmp = new String(cmd_search_list_std_tamplet);
    		tmp = tmp.replace("A.KDPST='targetKdpst'", "A.KDPST='"+target_kdpst+"'");
    		tmp = tmp.replace("E.KDPST='targetKdpst'", "E.KDPST='"+target_kdpst+"'");
    		//System.out.println("cmd_search_list_std_tamplet="+cmd_search_list_std_tamplet);
    		//System.out.println("id_master_std="+id_master_std);
    		//System.out.println("id_tipe_std="+id_tipe_std);
        	try {
            	Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	//cmd_search_list_std 
            	if(id_master_std>0 || id_master_std==0) {
            		//stmt = con.prepareStatement("SELECT A.ID,A.ID_STD,A.PERNYATAAN_STD,A.NO_BUTIR,A.KDPST,A.RASIONALE,A.AKTIF,A.TGL_MULAI_AKTIF,A.TGL_STOP_AKTIF,A.SCOPE,A.TIPE_PROSES_PENGAWASAN,B.ID_VERSI,B.ID_STD_ISI,B.ID_MANUAL_PENETAPAN,B.ID_MANUAL_PELAKSANAAN,B.ID_MANUAL_EVALUASI,B.ID_MANUAL_PENGENDALIAN,B.ID_MANUAL_PENINGKATAN,B.TGL_STA,B.TGL_END,B.TARGET_THSMS_1,B.TARGET_THSMS_2,B.TARGET_THSMS_3,B.TARGET_THSMS_4,B.TARGET_THSMS_5,B.TARGET_THSMS_6,B.PIHAK_TERKAIT,B.DOKUMEN_TERKAIT,B.TKN_INDIKATOR,B.NO_URUT_TAMPIL,B.TARGET_PERIOD_START,B.UNIT_PERIOD_USED,B.LAMA_NOMINAL_PER_PERIOD,B.TARGET_THSMS_1_UNIT,B.TARGET_THSMS_2_UNIT,B.TARGET_THSMS_3_UNIT,B.TARGET_THSMS_4_UNIT,B.TARGET_THSMS_5_UNIT,B.TARGET_THSMS_6_UNIT,B.PIHAK_MONITOR,B.TKN_PARAMETER,B.AKTIF,B.KDPST,D.ID_MASTER_STD,ID_TIPE_STD FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on ID=ID_STD_ISI inner join STANDARD_TABLE C on A.ID_STD=C.ID_STD inner join STANDARD_MASTER_TABLE D ON C.ID_MASTER_STD=D.ID_MASTER_STD where A.ID_STD is not null AND D.ID_MASTER_STD=? and ID_TIPE_STD=? order by A.PERNYATAAN_STD,B.TKN_INDIKATOR");
            		if(editor) {
            			stmt = con.prepareStatement(cmd_search_list_std_for_editor);
            			stmt.setInt(1, id_master_std);
                		stmt.setInt(2, id_tipe_std);
            		}
            		else {
            			stmt = con.prepareStatement(tmp);
            			stmt.setInt(1, id_master_std);
                		stmt.setInt(2, id_tipe_std);
                		stmt.setInt(3, id_master_std);
                		stmt.setInt(4, id_tipe_std);
                		stmt.setInt(5, id_master_std);
                		stmt.setInt(6, id_tipe_std);

            		}
            			
            		
            	}
            	//else {
            		//kayaknya ngga sampe sini
            		//stmt = con.prepareStatement("SELECT A.ID,A.ID_STD,A.PERNYATAAN_STD,A.NO_BUTIR,A.KDPST,A.RASIONALE,A.AKTIF,A.TGL_MULAI_AKTIF,A.TGL_STOP_AKTIF,A.SCOPE,A.TIPE_PROSES_PENGAWASAN,B.ID_VERSI,B.ID_STD_ISI,B.ID_MANUAL_PENETAPAN,B.ID_MANUAL_PELAKSANAAN,B.ID_MANUAL_EVALUASI,B.ID_MANUAL_PENGENDALIAN,B.ID_MANUAL_PENINGKATAN,B.TGL_STA,B.TGL_END,B.TARGET_THSMS_1,B.TARGET_THSMS_2,B.TARGET_THSMS_3,B.TARGET_THSMS_4,B.TARGET_THSMS_5,B.TARGET_THSMS_6,B.PIHAK_TERKAIT,B.DOKUMEN_TERKAIT,B.TKN_INDIKATOR,B.NO_URUT_TAMPIL,B.TARGET_PERIOD_START,B.UNIT_PERIOD_USED,B.LAMA_NOMINAL_PER_PERIOD,B.TARGET_THSMS_1_UNIT,B.TARGET_THSMS_2_UNIT,B.TARGET_THSMS_3_UNIT,B.TARGET_THSMS_4_UNIT,B.TARGET_THSMS_5_UNIT,B.TARGET_THSMS_6_UNIT,B.PIHAK_MONITOR,B.TKN_PARAMETER,B.AKTIF,B.KDPST,D.ID_MASTER_STD,ID_TIPE_STD FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on ID=ID_STD_ISI inner join STANDARD_TABLE C on A.ID_STD=C.ID_STD inner join STANDARD_MASTER_TABLE D ON C.ID_MASTER_STD=D.ID_MASTER_STD where A.ID_STD is not null order by A.PERNYATAAN_STD,B.TKN_INDIKATOR");
            		//;
            		//stmt = con.prepareStatement("SELECT * FROM STANDARD_ISI_TABLE inner join STANDARD_VERSION on ID=ID_STD_ISI where ID_STD is not null order by ID;");
            	//}
            	
            	
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		v = new Vector();
            		li = v.listIterator();
            		//rs.getInt("ID"); = ID_VERSI
            		//rs.getInt("ID_STD"); = ID_STD_ISI
            		String id_versi=""+rs.getInt("ID_VERSI");
            		if(Checker.isStringNullOrEmpty(id_versi)) {
            			id_versi = "null";
            		}
            		String id_std_isi=""+rs.getInt("ID_STD_ISI");
            		String id_std = ""+rs.getInt("ID_STD"); 
            		String isi_std=""+rs.getString("PERNYATAAN_STD");
            		String butir=""+rs.getInt("NO_BUTIR");
            		String kdpst=""+rs.getString("KDPST");
            		String rasionale=""+rs.getString("RASIONALE");
            		String aktif=""+rs.getBoolean("AKTIF");
            		String tgl_mulai_aktif=""+rs.getDate("TGL_MULAI_AKTIF");
            		String tgl_stop_aktif=""+rs.getDate("TGL_STOP_AKTIF");
            		String cakupan = ""+rs.getString("SCOPE");
            		String tipe_survey = ""+rs.getString("TIPE_PROSES_PENGAWASAN");
            		
            		String tgl_sta=""+rs.getDate("TGL_STA");
            		String tgl_end=""+rs.getDate("TGL_END");
            		String target1=""+rs.getString("TARGET_THSMS_1");
            		String target2=""+rs.getString("TARGET_THSMS_2");
            		String target3=""+rs.getString("TARGET_THSMS_3");
            		String target4=""+rs.getString("TARGET_THSMS_4");
            		String target5=""+rs.getString("TARGET_THSMS_5");
            		String target6=""+rs.getString("TARGET_THSMS_6");
            		String pihak=""+rs.getString("PIHAK_TERKAIT");
            		String dokumen=""+rs.getString("DOKUMEN_TERKAIT");
            		String indikator=""+rs.getString("TKN_INDIKATOR");
            		String periode_start=""+rs.getString("TARGET_PERIOD_START");
            		String unit_used_by_periode_start=""+rs.getString("UNIT_PERIOD_USED");
            		String besaran_interval_per_period=""+rs.getInt("LAMA_NOMINAL_PER_PERIOD");
            		String unit_used_byTarget=""+rs.getString("TARGET_THSMS_1_UNIT");
            		String pengawas=""+rs.getString("PIHAK_MONITOR");
            		String param=""+rs.getString("TKN_PARAMETER");
            		//if(Checker.isStringNullOrEmpty(tipe_survey)) {
            		//	tipe_survey = "null";
            		//}
            		String id_master = "0";//default
            		try {
            			id_master=""+rs.getString("ID_MASTER_STD");
            		}
            		catch (Exception e) {}
            		String id_tipe = "0";//default
            		try {
            			id_tipe=""+rs.getString("ID_TIPE_STD");
            		}
            		catch(Exception e) {}
            		
            		//if(Checker.isStringNullOrEmpty(id_tipe)) {
            		//	id_tipe = "null";
            		//}
            		
            		tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey+"`"+id_master+"`"+id_tipe;
            		li.add(tmp);
            		while(rs.next()) {
            			id_versi=""+rs.getInt("ID_VERSI");
                		if(Checker.isStringNullOrEmpty(id_versi)) {
                			id_versi = "null";
                		}
                		id_std_isi=""+rs.getInt("ID_STD_ISI");
                		id_std = ""+rs.getInt("ID_STD");
                		isi_std=""+rs.getString("PERNYATAAN_STD");
                		butir=""+rs.getInt("NO_BUTIR");
                		kdpst=""+rs.getString("KDPST");
                		rasionale=""+rs.getString("RASIONALE");
                		aktif=""+rs.getBoolean("AKTIF");
                		tgl_mulai_aktif=""+rs.getDate("TGL_MULAI_AKTIF");
                		tgl_stop_aktif=""+rs.getDate("TGL_STOP_AKTIF");
                		cakupan = ""+rs.getString("SCOPE");
                		tipe_survey = ""+rs.getString("TIPE_PROSES_PENGAWASAN");
                		tgl_sta=""+rs.getDate("TGL_STA");
                		tgl_end=""+rs.getDate("TGL_END");
                		target1=""+rs.getString("TARGET_THSMS_1");
                		target2=""+rs.getString("TARGET_THSMS_2");
                		target3=""+rs.getString("TARGET_THSMS_3");
                		target4=""+rs.getString("TARGET_THSMS_4");
                		target5=""+rs.getString("TARGET_THSMS_5");
                		target6=""+rs.getString("TARGET_THSMS_6");
                		pihak=""+rs.getString("PIHAK_TERKAIT");
                		dokumen=""+rs.getString("DOKUMEN_TERKAIT");
                		indikator=""+rs.getString("TKN_INDIKATOR");
                		periode_start=""+rs.getString("TARGET_PERIOD_START");
                		unit_used_by_periode_start=""+rs.getString("UNIT_PERIOD_USED");
                		besaran_interval_per_period=""+rs.getInt("LAMA_NOMINAL_PER_PERIOD");
                		unit_used_byTarget=""+rs.getString("TARGET_THSMS_1_UNIT");
                		pengawas=""+rs.getString("PIHAK_MONITOR");
                		param=""+rs.getString("TKN_PARAMETER");
                		//if(Checker.isStringNullOrEmpty(tipe_survey)) {
                		//	tipe_survey = "null";
                		//}
                		id_master = "0";//default
                		try {
                			id_master=""+rs.getString("ID_MASTER_STD");
                		}
                		catch (Exception e) {}
                		id_tipe = "0";//default
                		try {
                			id_tipe=""+rs.getString("ID_TIPE_STD");
                		}
                		catch(Exception e) {}
                		tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey+"`"+id_master+"`"+id_tipe;
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
            finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        	    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        	    if (con!=null) try { con.close();} catch (Exception ignore){}
        	}
    	}
    	return v;
    }
    
    
    public Vector getListSingleStandarIsi(int id_versi, int id_std_isi) {
    	Vector v = null;
    	ListIterator li = null;
    	if(true) {
    		String tmp = "SELECT A.ID,A.ID_STD,A.PERNYATAAN_STD,A.NO_BUTIR,A.KDPST,A.RASIONALE,A.AKTIF,A.TGL_MULAI_AKTIF,A.TGL_STOP_AKTIF,A.SCOPE,A.TIPE_PROSES_PENGAWASAN,B.ID_VERSI,B.ID_STD_ISI,B.ID_MANUAL_PENETAPAN,B.ID_MANUAL_PELAKSANAAN,B.ID_MANUAL_EVALUASI,B.ID_MANUAL_PENGENDALIAN,B.ID_MANUAL_PENINGKATAN,B.TGL_STA,B.TGL_END,B.TARGET_THSMS_1,B.TARGET_THSMS_2,B.TARGET_THSMS_3,B.TARGET_THSMS_4,B.TARGET_THSMS_5,B.TARGET_THSMS_6,B.PIHAK_TERKAIT,B.DOKUMEN_TERKAIT,B.TKN_INDIKATOR,B.NO_URUT_TAMPIL,B.TARGET_PERIOD_START,B.UNIT_PERIOD_USED,B.LAMA_NOMINAL_PER_PERIOD,B.TARGET_THSMS_1_UNIT,B.TARGET_THSMS_2_UNIT,B.TARGET_THSMS_3_UNIT,B.TARGET_THSMS_4_UNIT,B.TARGET_THSMS_5_UNIT,B.TARGET_THSMS_6_UNIT,B.PIHAK_MONITOR,B.TKN_PARAMETER,B.AKTIF,B.KDPST,H.ID_MASTER_STD,H.ID_TIPE_STD,B.STRATEGI FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on A.ID=B.ID_STD_ISI inner join STANDARD_TABLE H on A.ID_STD=H.ID_STD where B.ID_VERSI=? and B.ID_STD_ISI=?";
    		try {
            	Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	//cmd_search_list_std 
            	stmt = con.prepareStatement(tmp);
    			stmt.setInt(1, id_versi);
        		stmt.setInt(2, id_std_isi);
            	
            	
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		v = new Vector();
            		li = v.listIterator();
            		//rs.getInt("ID"); = ID_VERSI
            		//rs.getInt("ID_STD"); = ID_STD_ISI
            		String id_std = ""+rs.getInt("ID_STD"); 
            		String isi_std=""+rs.getString("PERNYATAAN_STD");
            		String butir=""+rs.getInt("NO_BUTIR");
            		String kdpst=""+rs.getString("KDPST");
            		String rasionale=""+rs.getString("RASIONALE");
            		String aktif=""+rs.getBoolean("AKTIF");
            		String tgl_mulai_aktif=""+rs.getDate("TGL_MULAI_AKTIF");
            		String tgl_stop_aktif=""+rs.getDate("TGL_STOP_AKTIF");
            		String cakupan = ""+rs.getString("SCOPE");
            		String tipe_survey = ""+rs.getString("TIPE_PROSES_PENGAWASAN");
            		
            		String tgl_sta=""+rs.getDate("TGL_STA");
            		String tgl_end=""+rs.getDate("TGL_END");
            		String target1=""+rs.getString("TARGET_THSMS_1");
            		String target2=""+rs.getString("TARGET_THSMS_2");
            		String target3=""+rs.getString("TARGET_THSMS_3");
            		String target4=""+rs.getString("TARGET_THSMS_4");
            		String target5=""+rs.getString("TARGET_THSMS_5");
            		String target6=""+rs.getString("TARGET_THSMS_6");
            		String pihak=""+rs.getString("PIHAK_TERKAIT");
            		String dokumen=""+rs.getString("DOKUMEN_TERKAIT");
            		String indikator=""+rs.getString("TKN_INDIKATOR");
            		String periode_start=""+rs.getString("TARGET_PERIOD_START");
            		String unit_used_by_periode_start=""+rs.getString("UNIT_PERIOD_USED");
            		String besaran_interval_per_period=""+rs.getInt("LAMA_NOMINAL_PER_PERIOD");
            		String unit_used_byTarget=""+rs.getString("TARGET_THSMS_1_UNIT");
            		String pengawas=""+rs.getString("PIHAK_MONITOR");
            		String param=""+rs.getString("TKN_PARAMETER");
            		//if(Checker.isStringNullOrEmpty(tipe_survey)) {
            		//	tipe_survey = "null";
            		//}
            		String id_master = "0";//default
            		try {
            			id_master=""+rs.getString("ID_MASTER_STD");
            		}
            		catch (Exception e) {}
            		String id_tipe = "0";//default
            		try {
            			id_tipe=""+rs.getString("ID_TIPE_STD");
            		}
            		catch(Exception e) {}
            		String strategi=""+rs.getString("STRATEGI");
            		//if(Checker.isStringNullOrEmpty(id_tipe)) {
            		//	id_tipe = "null";
            		//}
            		
            		tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey+"`"+id_master+"`"+id_tipe+"`"+strategi;
            		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "`");
            		li.add(tmp);
            		
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
    	return v;
    }
    
    //public Vector getListStandarIsi(String target_kdpst, int id_std) {
    public Vector getListStandarIsi(boolean editor, String target_kdpst, int id_std) {	
    	Vector v = null;
    	boolean aktif_only=true;
    	ListIterator li = null;
        		
    	if(!Checker.isStringNullOrEmpty(target_kdpst)) {
    		/*
    		 * original
    		 
    		String tmp = new String(cmd_search_list_std_tamplet_use_ID_STD);
    		tmp = tmp.replace("A.KDPST='targetKdpst'", "A.KDPST='"+target_kdpst+"'");
    		tmp = tmp.replace("E.KDPST='targetKdpst'", "E.KDPST='"+target_kdpst+"'");
    		*/
    		
    		String tmp = "";
    		if(!editor) {
    			tmp = new String(cmd_search_list_std_tamplet_use_ID_STD_select_all_aktifonly);
    			if(!aktif_only) {
    				tmp = new String(cmd_search_list_std_tamplet_use_ID_STD_select_all);
    			}
    			tmp = tmp.replace("A.KDPST='targetKdpst'", "A.KDPST='"+target_kdpst+"'");
    			tmp = tmp.replace("E.KDPST='targetKdpst'", "E.KDPST='"+target_kdpst+"'");
    		}
    		else {
    			tmp = new String(cmd_search_list_std_tamplet_use_ID_STD_select_all_aktifonly_for_editor);
    			if(!aktif_only) {
    				tmp = new String(cmd_search_list_std_tamplet_use_ID_STD_select_all_for_editor);
    			}
    			tmp = tmp.replace("A.KDPST='targetKdpst'", "A.KDPST='"+target_kdpst+"'");
    		}

    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			//cmd_search_list_std 
    			stmt = con.prepareStatement(tmp);
    			/*
    			 * original
    			
    			stmt.setInt(1, id_std);
    			stmt.setInt(2, id_std);
    			stmt.setInt(3, id_std);
    			 */
    			
    			if(!editor) {
    				stmt.setInt(1, id_std);
        			stmt.setInt(2, id_std);
        			stmt.setInt(3, id_std);	
    			}
    			else {
    				stmt.setInt(1, id_std);
    			}
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v = new Vector();
    				li = v.listIterator();
    				//rs.getInt("ID"); = ID_VERSI
    				//rs.getInt("ID_STD"); = ID_STD_ISI
    				String id_versi=""+rs.getInt("ID_VERSI");
    				if(Checker.isStringNullOrEmpty(id_versi)) {
    					id_versi = "null";
    				}
    				String id_std_isi=""+rs.getInt("ID_STD_ISI");
    				id_std = rs.getInt("ID_STD"); 
    				String isi_std=""+rs.getString("PERNYATAAN_STD");
    				String butir=""+rs.getInt("NO_BUTIR");
    				String kdpst=""+rs.getString("KDPST");
    				String rasionale=""+rs.getString("RASIONALE");
    				String aktif=""+rs.getBoolean("AKTIF");
    				String tgl_mulai_aktif=""+rs.getDate("TGL_MULAI_AKTIF");
    				String tgl_stop_aktif=""+rs.getDate("TGL_STOP_AKTIF");
    				String cakupan = ""+rs.getString("SCOPE");
    				String tipe_survey = ""+rs.getString("TIPE_PROSES_PENGAWASAN");
    				
    				String tgl_sta=""+rs.getDate("TGL_STA");
    				String tgl_end=""+rs.getDate("TGL_END");
    				String target1=""+rs.getString("TARGET_THSMS_1");
    				String target2=""+rs.getString("TARGET_THSMS_2");
    				String target3=""+rs.getString("TARGET_THSMS_3");
    				String target4=""+rs.getString("TARGET_THSMS_4");
    				String target5=""+rs.getString("TARGET_THSMS_5");
    				String target6=""+rs.getString("TARGET_THSMS_6");
    				String pihak=""+rs.getString("PIHAK_TERKAIT");
    				String dokumen=""+rs.getString("DOKUMEN_TERKAIT");
    				String indikator=""+rs.getString("TKN_INDIKATOR");
    				String periode_start=""+rs.getString("TARGET_PERIOD_START");
    				String unit_used_by_periode_start=""+rs.getString("UNIT_PERIOD_USED");
    				String besaran_interval_per_period=""+rs.getInt("LAMA_NOMINAL_PER_PERIOD");
    				String unit_used_byTarget=""+rs.getString("TARGET_THSMS_1_UNIT");
    				String pengawas=""+rs.getString("PIHAK_MONITOR");
    				String param=""+rs.getString("TKN_PARAMETER");
    				//if(Checker.isStringNullOrEmpty(tipe_survey)) {
    				//	tipe_survey = "null";
    				//}
    				String id_master = "0";//default
    				try {
    					id_master=""+rs.getString("ID_MASTER_STD");
                	}
    				catch (Exception e) {}
                	String id_tipe = "0";//default
                	try {
                		id_tipe=""+rs.getString("ID_TIPE_STD");
                	}
                	catch(Exception e) {}
                	
                		
                	tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey+"`"+id_master+"`"+id_tipe;
                	li.add(tmp);
                	while(rs.next()) {
                		id_versi=""+rs.getInt("ID_VERSI");
                		if(Checker.isStringNullOrEmpty(id_versi)) {
                			id_versi = "null";
                		}
                		id_std_isi=""+rs.getInt("ID_STD_ISI");
                		id_std = rs.getInt("ID_STD");
                		isi_std=""+rs.getString("PERNYATAAN_STD");
                		butir=""+rs.getInt("NO_BUTIR");
                		kdpst=""+rs.getString("KDPST");
                		rasionale=""+rs.getString("RASIONALE");
                		aktif=""+rs.getBoolean("AKTIF");
                		tgl_mulai_aktif=""+rs.getDate("TGL_MULAI_AKTIF");
                		tgl_stop_aktif=""+rs.getDate("TGL_STOP_AKTIF");
                		cakupan = ""+rs.getString("SCOPE");
                		tipe_survey = ""+rs.getString("TIPE_PROSES_PENGAWASAN");
                		tgl_sta=""+rs.getDate("TGL_STA");
                		tgl_end=""+rs.getDate("TGL_END");
                		target1=""+rs.getString("TARGET_THSMS_1");
                		target2=""+rs.getString("TARGET_THSMS_2");
                		target3=""+rs.getString("TARGET_THSMS_3");
                		target4=""+rs.getString("TARGET_THSMS_4");
                		target5=""+rs.getString("TARGET_THSMS_5");
                		target6=""+rs.getString("TARGET_THSMS_6");
                		pihak=""+rs.getString("PIHAK_TERKAIT");
                		dokumen=""+rs.getString("DOKUMEN_TERKAIT");
                		indikator=""+rs.getString("TKN_INDIKATOR");
                		periode_start=""+rs.getString("TARGET_PERIOD_START");
                		unit_used_by_periode_start=""+rs.getString("UNIT_PERIOD_USED");
                		besaran_interval_per_period=""+rs.getInt("LAMA_NOMINAL_PER_PERIOD");
                		unit_used_byTarget=""+rs.getString("TARGET_THSMS_1_UNIT");
                		pengawas=""+rs.getString("PIHAK_MONITOR");
                		param=""+rs.getString("TKN_PARAMETER");
                		//if(Checker.isStringNullOrEmpty(tipe_survey)) {
                		//	tipe_survey = "null";
                		//}
                		id_master = "0";//default
                		try {
                			id_master=""+rs.getString("ID_MASTER_STD");
                		}
                		catch (Exception e) {}
                		id_tipe = "0";//default
                		try {
                			id_tipe=""+rs.getString("ID_TIPE_STD");
                    	}
                		catch(Exception e) {}
                		tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey+"`"+id_master+"`"+id_tipe;
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
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
            }
        }

    	return v;
    }
    
    
    public Vector getInfoTampletStandar(boolean editor,String target_kdpst, int id_std, boolean aktif_only) {
    	Vector v = null;
    	ListIterator li = null;
    	String tmp = null;	
    	if(!Checker.isStringNullOrEmpty(target_kdpst)) {
    		if(!editor) {
    			tmp = new String(cmd_search_list_std_tamplet_use_ID_STD_select_all_aktifonly);
    			if(!aktif_only) {
    				tmp = new String(cmd_search_list_std_tamplet_use_ID_STD_select_all);
    			}
    			tmp = tmp.replace("A.KDPST='targetKdpst'", "A.KDPST='"+target_kdpst+"'");
    			tmp = tmp.replace("E.KDPST='targetKdpst'", "E.KDPST='"+target_kdpst+"'");
    		}
    		else {
    			tmp = new String(cmd_search_list_std_tamplet_use_ID_STD_select_all_aktifonly_for_editor);
    			if(!aktif_only) {
    				tmp = new String(cmd_search_list_std_tamplet_use_ID_STD_select_all_for_editor);
    			}
    			tmp = tmp.replace("A.KDPST='targetKdpst'", "A.KDPST='"+target_kdpst+"'");
    		}
    		//System.out.println("tmp sql="+tmp);
    		//System.out.println("id_master_std="+id_master_std);
    		//System.out.println("id_tipe_std="+id_tipe_std);
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			//cmd_search_list_std 
    			stmt = con.prepareStatement(tmp);
    			//System.out.println("command="+tmp);
    			if(!editor) {
    				stmt.setInt(1, id_std);
        			stmt.setInt(2, id_std);
        			stmt.setInt(3, id_std);	
    			}
    			else {
    				stmt.setInt(1, id_std);
    			}
    			    			
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v = new Vector();
    				li = v.listIterator();
    				//rs.getInt("ID"); = ID_VERSI
    				//rs.getInt("ID_STD"); = ID_STD_ISI
    				String id_versi=""+rs.getInt("ID_VERSI");
    				if(Checker.isStringNullOrEmpty(id_versi)) {
    					id_versi = "null";
    				}
    				String id_std_isi=""+rs.getInt("ID_STD_ISI");
    				id_std = rs.getInt("ID_STD"); 
    				String isi_std=""+rs.getString("PERNYATAAN_STD");
    				String butir=""+rs.getInt("NO_BUTIR");
    				String kdpst=""+rs.getString("KDPST");
    				String rasionale=""+rs.getString("RASIONALE");
    				String aktif=""+rs.getBoolean("AKTIF");
    				String tgl_mulai_aktif=""+rs.getDate("TGL_MULAI_AKTIF");
    				String tgl_stop_aktif=""+rs.getDate("TGL_STOP_AKTIF");
    				String cakupan = ""+rs.getString("SCOPE");
    				String tipe_survey = ""+rs.getString("TIPE_PROSES_PENGAWASAN");
    				
    				String tgl_sta=""+rs.getDate("TGL_STA");
    				String tgl_end=""+rs.getDate("TGL_END");
    				String target1=""+rs.getString("TARGET_THSMS_1");
    				String target2=""+rs.getString("TARGET_THSMS_2");
    				String target3=""+rs.getString("TARGET_THSMS_3");
    				String target4=""+rs.getString("TARGET_THSMS_4");
    				String target5=""+rs.getString("TARGET_THSMS_5");
    				String target6=""+rs.getString("TARGET_THSMS_6");
    				String pihak=""+rs.getString("PIHAK_TERKAIT");
    				String dokumen=""+rs.getString("DOKUMEN_TERKAIT");
    				String indikator=""+rs.getString("TKN_INDIKATOR");
    				String periode_start=""+rs.getString("TARGET_PERIOD_START");
    				String unit_used_by_periode_start=""+rs.getString("UNIT_PERIOD_USED");
    				String besaran_interval_per_period=""+rs.getInt("LAMA_NOMINAL_PER_PERIOD");
    				String unit_used_byTarget=""+rs.getString("TARGET_THSMS_1_UNIT");
    				String pengawas=""+rs.getString("PIHAK_MONITOR");
    				String param=""+rs.getString("TKN_PARAMETER");
    				String strategi=""+rs.getString("STRATEGI");
    				//if(Checker.isStringNullOrEmpty(tipe_survey)) {
    				//	tipe_survey = "null";
    				//}
    				String id_master = "0";//default
    				try {
    					id_master=""+rs.getString("ID_MASTER_STD");
                	}
    				catch (Exception e) {}
                	String id_tipe = "0";//default
                	try {
                		id_tipe=""+rs.getString("ID_TIPE_STD");
                	}
                	catch(Exception e) {}
                	
                		
                	tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey+"`"+id_master+"`"+id_tipe+"`"+strategi;
                	li.add(tmp);
                	while(rs.next()) {
                		id_versi=""+rs.getInt("ID_VERSI");
                		if(Checker.isStringNullOrEmpty(id_versi)) {
                			id_versi = "null";
                		}
                		id_std_isi=""+rs.getInt("ID_STD_ISI");
                		id_std = rs.getInt("ID_STD");
                		isi_std=""+rs.getString("PERNYATAAN_STD");
                		butir=""+rs.getInt("NO_BUTIR");
                		kdpst=""+rs.getString("KDPST");
                		rasionale=""+rs.getString("RASIONALE");
                		aktif=""+rs.getBoolean("AKTIF");
                		tgl_mulai_aktif=""+rs.getDate("TGL_MULAI_AKTIF");
                		tgl_stop_aktif=""+rs.getDate("TGL_STOP_AKTIF");
                		cakupan = ""+rs.getString("SCOPE");
                		tipe_survey = ""+rs.getString("TIPE_PROSES_PENGAWASAN");
                		tgl_sta=""+rs.getDate("TGL_STA");
                		tgl_end=""+rs.getDate("TGL_END");
                		target1=""+rs.getString("TARGET_THSMS_1");
                		target2=""+rs.getString("TARGET_THSMS_2");
                		target3=""+rs.getString("TARGET_THSMS_3");
                		target4=""+rs.getString("TARGET_THSMS_4");
                		target5=""+rs.getString("TARGET_THSMS_5");
                		target6=""+rs.getString("TARGET_THSMS_6");
                		pihak=""+rs.getString("PIHAK_TERKAIT");
                		dokumen=""+rs.getString("DOKUMEN_TERKAIT");
                		indikator=""+rs.getString("TKN_INDIKATOR");
                		periode_start=""+rs.getString("TARGET_PERIOD_START");
                		unit_used_by_periode_start=""+rs.getString("UNIT_PERIOD_USED");
                		besaran_interval_per_period=""+rs.getInt("LAMA_NOMINAL_PER_PERIOD");
                		unit_used_byTarget=""+rs.getString("TARGET_THSMS_1_UNIT");
                		pengawas=""+rs.getString("PIHAK_MONITOR");
                		param=""+rs.getString("TKN_PARAMETER");
                		strategi=""+rs.getString("STRATEGI");
                		//if(Checker.isStringNullOrEmpty(tipe_survey)) {
                		//	tipe_survey = "null";
                		//}
                		id_master = "0";//default
                		try {
                			id_master=""+rs.getString("ID_MASTER_STD");
                		}
                		catch (Exception e) {}
                		id_tipe = "0";//default
                		try {
                			id_tipe=""+rs.getString("ID_TIPE_STD");
                    	}
                		catch(Exception e) {}
                		tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey+"`"+id_master+"`"+id_tipe+"`"+strategi;
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
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
            }
        }

    	return v;
    }

    
    
    public Vector getListStandarIsi(boolean editor, String target_kdpst, int usr_objid, int id_master_std, int id_tipe_std, String pernyataan_isi_std) {
    	Vector v = null;
    	ListIterator li = null;
    	//Vector v_scope_kdpst = "";
    	if(!Checker.isStringNullOrEmpty(target_kdpst)) {
    		String tmp = new String(cmd_search_list_std_tamplet_based_on_pernyataan_isi_std);
    		tmp = tmp.replace("A.KDPST='targetKdpst'", "A.KDPST='"+target_kdpst+"'");
    		tmp = tmp.replace("E.KDPST='targetKdpst'", "E.KDPST='"+target_kdpst+"'");
    		//System.out.println("cmd_search_list_std_tamplet="+cmd_search_list_std_tamplet);
    		//System.out.println("id_master_std="+id_master_std);
    		//System.out.println("id_tipe_std="+id_tipe_std);
        	try {
            	Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	//cmd_search_list_std 
            	if(id_master_std>0 || id_master_std==0) {
            		//stmt = con.prepareStatement("SELECT A.ID,A.ID_STD,A.PERNYATAAN_STD,A.NO_BUTIR,A.KDPST,A.RASIONALE,A.AKTIF,A.TGL_MULAI_AKTIF,A.TGL_STOP_AKTIF,A.SCOPE,A.TIPE_PROSES_PENGAWASAN,B.ID_VERSI,B.ID_STD_ISI,B.ID_MANUAL_PENETAPAN,B.ID_MANUAL_PELAKSANAAN,B.ID_MANUAL_EVALUASI,B.ID_MANUAL_PENGENDALIAN,B.ID_MANUAL_PENINGKATAN,B.TGL_STA,B.TGL_END,B.TARGET_THSMS_1,B.TARGET_THSMS_2,B.TARGET_THSMS_3,B.TARGET_THSMS_4,B.TARGET_THSMS_5,B.TARGET_THSMS_6,B.PIHAK_TERKAIT,B.DOKUMEN_TERKAIT,B.TKN_INDIKATOR,B.NO_URUT_TAMPIL,B.TARGET_PERIOD_START,B.UNIT_PERIOD_USED,B.LAMA_NOMINAL_PER_PERIOD,B.TARGET_THSMS_1_UNIT,B.TARGET_THSMS_2_UNIT,B.TARGET_THSMS_3_UNIT,B.TARGET_THSMS_4_UNIT,B.TARGET_THSMS_5_UNIT,B.TARGET_THSMS_6_UNIT,B.PIHAK_MONITOR,B.TKN_PARAMETER,B.AKTIF,B.KDPST,D.ID_MASTER_STD,ID_TIPE_STD FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on ID=ID_STD_ISI inner join STANDARD_TABLE C on A.ID_STD=C.ID_STD inner join STANDARD_MASTER_TABLE D ON C.ID_MASTER_STD=D.ID_MASTER_STD where A.ID_STD is not null AND D.ID_MASTER_STD=? and ID_TIPE_STD=? order by A.PERNYATAAN_STD,B.TKN_INDIKATOR");
            		if(editor) {
            			stmt = con.prepareStatement(cmd_search_list_std_for_editor);
            			stmt.setInt(1, id_master_std);
                		stmt.setInt(2, id_tipe_std);
            		}
            		else {
            			stmt = con.prepareStatement(tmp);
            			stmt.setInt(1, id_master_std);
                		stmt.setInt(2, id_tipe_std);
                		stmt.setInt(3, id_master_std);
                		stmt.setInt(4, id_tipe_std);
                		stmt.setInt(5, id_master_std);
                		stmt.setInt(6, id_tipe_std);

            		}
            			
            		
            	}
            	//else {
            		//kayaknya ngga sampe sini
            		//stmt = con.prepareStatement("SELECT A.ID,A.ID_STD,A.PERNYATAAN_STD,A.NO_BUTIR,A.KDPST,A.RASIONALE,A.AKTIF,A.TGL_MULAI_AKTIF,A.TGL_STOP_AKTIF,A.SCOPE,A.TIPE_PROSES_PENGAWASAN,B.ID_VERSI,B.ID_STD_ISI,B.ID_MANUAL_PENETAPAN,B.ID_MANUAL_PELAKSANAAN,B.ID_MANUAL_EVALUASI,B.ID_MANUAL_PENGENDALIAN,B.ID_MANUAL_PENINGKATAN,B.TGL_STA,B.TGL_END,B.TARGET_THSMS_1,B.TARGET_THSMS_2,B.TARGET_THSMS_3,B.TARGET_THSMS_4,B.TARGET_THSMS_5,B.TARGET_THSMS_6,B.PIHAK_TERKAIT,B.DOKUMEN_TERKAIT,B.TKN_INDIKATOR,B.NO_URUT_TAMPIL,B.TARGET_PERIOD_START,B.UNIT_PERIOD_USED,B.LAMA_NOMINAL_PER_PERIOD,B.TARGET_THSMS_1_UNIT,B.TARGET_THSMS_2_UNIT,B.TARGET_THSMS_3_UNIT,B.TARGET_THSMS_4_UNIT,B.TARGET_THSMS_5_UNIT,B.TARGET_THSMS_6_UNIT,B.PIHAK_MONITOR,B.TKN_PARAMETER,B.AKTIF,B.KDPST,D.ID_MASTER_STD,ID_TIPE_STD FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on ID=ID_STD_ISI inner join STANDARD_TABLE C on A.ID_STD=C.ID_STD inner join STANDARD_MASTER_TABLE D ON C.ID_MASTER_STD=D.ID_MASTER_STD where A.ID_STD is not null order by A.PERNYATAAN_STD,B.TKN_INDIKATOR");
            		//;
            		//stmt = con.prepareStatement("SELECT * FROM STANDARD_ISI_TABLE inner join STANDARD_VERSION on ID=ID_STD_ISI where ID_STD is not null order by ID;");
            	//}
            	
            	
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		v = new Vector();
            		li = v.listIterator();
            		//rs.getInt("ID"); = ID_VERSI
            		//rs.getInt("ID_STD"); = ID_STD_ISI
            		String id_versi=""+rs.getInt("ID_VERSI");
            		if(Checker.isStringNullOrEmpty(id_versi)) {
            			id_versi = "null";
            		}
            		String id_std_isi=""+rs.getInt("ID_STD_ISI");
            		String id_std = ""+rs.getInt("ID_STD"); 
            		String isi_std=""+rs.getString("PERNYATAAN_STD");
            		String butir=""+rs.getInt("NO_BUTIR");
            		String kdpst=""+rs.getString("KDPST");
            		String rasionale=""+rs.getString("RASIONALE");
            		String aktif=""+rs.getBoolean("AKTIF");
            		String tgl_mulai_aktif=""+rs.getDate("TGL_MULAI_AKTIF");
            		String tgl_stop_aktif=""+rs.getDate("TGL_STOP_AKTIF");
            		String cakupan = ""+rs.getString("SCOPE");
            		String tipe_survey = ""+rs.getString("TIPE_PROSES_PENGAWASAN");
            		
            		String tgl_sta=""+rs.getDate("TGL_STA");
            		String tgl_end=""+rs.getDate("TGL_END");
            		String target1=""+rs.getString("TARGET_THSMS_1");
            		String target2=""+rs.getString("TARGET_THSMS_2");
            		String target3=""+rs.getString("TARGET_THSMS_3");
            		String target4=""+rs.getString("TARGET_THSMS_4");
            		String target5=""+rs.getString("TARGET_THSMS_5");
            		String target6=""+rs.getString("TARGET_THSMS_6");
            		String pihak=""+rs.getString("PIHAK_TERKAIT");
            		String dokumen=""+rs.getString("DOKUMEN_TERKAIT");
            		String indikator=""+rs.getString("TKN_INDIKATOR");
            		String periode_start=""+rs.getString("TARGET_PERIOD_START");
            		String unit_used_by_periode_start=""+rs.getString("UNIT_PERIOD_USED");
            		String besaran_interval_per_period=""+rs.getInt("LAMA_NOMINAL_PER_PERIOD");
            		String unit_used_byTarget=""+rs.getString("TARGET_THSMS_1_UNIT");
            		String pengawas=""+rs.getString("PIHAK_MONITOR");
            		String param=""+rs.getString("TKN_PARAMETER");
            		//if(Checker.isStringNullOrEmpty(tipe_survey)) {
            		//	tipe_survey = "null";
            		//}
            		String id_master = "0";//default
            		try {
            			id_master=""+rs.getString("ID_MASTER_STD");
            		}
            		catch (Exception e) {}
            		String id_tipe = "0";//default
            		try {
            			id_tipe=""+rs.getString("ID_TIPE_STD");
            		}
            		catch(Exception e) {}
            		
            		//if(Checker.isStringNullOrEmpty(id_tipe)) {
            		//	id_tipe = "null";
            		//}
            		
            		tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey+"`"+id_master+"`"+id_tipe;
            		li.add(tmp);
            		while(rs.next()) {
            			id_versi=""+rs.getInt("ID_VERSI");
                		if(Checker.isStringNullOrEmpty(id_versi)) {
                			id_versi = "null";
                		}
                		id_std_isi=""+rs.getInt("ID_STD_ISI");
                		id_std = ""+rs.getInt("ID_STD");
                		isi_std=""+rs.getString("PERNYATAAN_STD");
                		butir=""+rs.getInt("NO_BUTIR");
                		kdpst=""+rs.getString("KDPST");
                		rasionale=""+rs.getString("RASIONALE");
                		aktif=""+rs.getBoolean("AKTIF");
                		tgl_mulai_aktif=""+rs.getDate("TGL_MULAI_AKTIF");
                		tgl_stop_aktif=""+rs.getDate("TGL_STOP_AKTIF");
                		cakupan = ""+rs.getString("SCOPE");
                		tipe_survey = ""+rs.getString("TIPE_PROSES_PENGAWASAN");
                		tgl_sta=""+rs.getDate("TGL_STA");
                		tgl_end=""+rs.getDate("TGL_END");
                		target1=""+rs.getString("TARGET_THSMS_1");
                		target2=""+rs.getString("TARGET_THSMS_2");
                		target3=""+rs.getString("TARGET_THSMS_3");
                		target4=""+rs.getString("TARGET_THSMS_4");
                		target5=""+rs.getString("TARGET_THSMS_5");
                		target6=""+rs.getString("TARGET_THSMS_6");
                		pihak=""+rs.getString("PIHAK_TERKAIT");
                		dokumen=""+rs.getString("DOKUMEN_TERKAIT");
                		indikator=""+rs.getString("TKN_INDIKATOR");
                		periode_start=""+rs.getString("TARGET_PERIOD_START");
                		unit_used_by_periode_start=""+rs.getString("UNIT_PERIOD_USED");
                		besaran_interval_per_period=""+rs.getInt("LAMA_NOMINAL_PER_PERIOD");
                		unit_used_byTarget=""+rs.getString("TARGET_THSMS_1_UNIT");
                		pengawas=""+rs.getString("PIHAK_MONITOR");
                		param=""+rs.getString("TKN_PARAMETER");
                		//if(Checker.isStringNullOrEmpty(tipe_survey)) {
                		//	tipe_survey = "null";
                		//}
                		id_master = "0";//default
                		try {
                			id_master=""+rs.getString("ID_MASTER_STD");
                		}
                		catch (Exception e) {}
                		id_tipe = "0";//default
                		try {
                			id_tipe=""+rs.getString("ID_TIPE_STD");
                		}
                		catch(Exception e) {}
                		tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey+"`"+id_master+"`"+id_tipe;
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
            finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        	    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        	    if (con!=null) try { con.close();} catch (Exception ignore){}
        	}
    	}

    	return v;
    }
    
    
 
    /*
    public Vector getStandardAktif(boolean editor, String target_kdpst, int usr_objid, String scope) {
    	Vector v = null;
    	ListIterator li = null;
 		
    	if(!Checker.isStringNullOrEmpty(target_kdpst)) {
    		String tmp = new String(cmd_search_list_std_tamplet);
    		tmp = tmp.replace("A.KDPST='targetKdpst'", "A.KDPST='"+target_kdpst+"'");
    		tmp = tmp.replace("E.KDPST='targetKdpst'", "E.KDPST='"+target_kdpst+"'");
    		tmp = tmp.replace("(A.AKTIF=true or A.AKTIF=false)", "(A.AKTIF=true)");
    		tmp = tmp.replace("(C.AKTIF=true or C.AKTIF=false)", "(C.AKTIF=true)");
    		while(tmp.contains("H.ID_MASTER_STD=?")) {
    			tmp = tmp.replace("H.ID_MASTER_STD=?", "H.ID_MASTER_STD>0");	
    		}
    		while(tmp.contains("H.ID_TIPE_STD=?")) {
    			tmp = tmp.replace("H.ID_TIPE_STD=?", "H.ID_TIPE_STD>0");	
    		}
    		
    		
    		//System.out.println("tmp="+tmp);
    		//System.out.println("id_master_std="+id_master_std);
    		//System.out.println("id_tipe_std="+id_tipe_std);
        	try {
            	Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	//cmd_search_list_std 
            	if(true) {
            		//stmt = con.prepareStatement("SELECT A.ID,A.ID_STD,A.PERNYATAAN_STD,A.NO_BUTIR,A.KDPST,A.RASIONALE,A.AKTIF,A.TGL_MULAI_AKTIF,A.TGL_STOP_AKTIF,A.SCOPE,A.TIPE_PROSES_PENGAWASAN,B.ID_VERSI,B.ID_STD_ISI,B.ID_MANUAL_PENETAPAN,B.ID_MANUAL_PELAKSANAAN,B.ID_MANUAL_EVALUASI,B.ID_MANUAL_PENGENDALIAN,B.ID_MANUAL_PENINGKATAN,B.TGL_STA,B.TGL_END,B.TARGET_THSMS_1,B.TARGET_THSMS_2,B.TARGET_THSMS_3,B.TARGET_THSMS_4,B.TARGET_THSMS_5,B.TARGET_THSMS_6,B.PIHAK_TERKAIT,B.DOKUMEN_TERKAIT,B.TKN_INDIKATOR,B.NO_URUT_TAMPIL,B.TARGET_PERIOD_START,B.UNIT_PERIOD_USED,B.LAMA_NOMINAL_PER_PERIOD,B.TARGET_THSMS_1_UNIT,B.TARGET_THSMS_2_UNIT,B.TARGET_THSMS_3_UNIT,B.TARGET_THSMS_4_UNIT,B.TARGET_THSMS_5_UNIT,B.TARGET_THSMS_6_UNIT,B.PIHAK_MONITOR,B.TKN_PARAMETER,B.AKTIF,B.KDPST,D.ID_MASTER_STD,ID_TIPE_STD FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on ID=ID_STD_ISI inner join STANDARD_TABLE C on A.ID_STD=C.ID_STD inner join STANDARD_MASTER_TABLE D ON C.ID_MASTER_STD=D.ID_MASTER_STD where A.ID_STD is not null AND D.ID_MASTER_STD=? and ID_TIPE_STD=? order by A.PERNYATAAN_STD,B.TKN_INDIKATOR");
            		if(editor) {
            			String tmpe = new String(cmd_search_list_std_for_editor);
            			tmpe = tmpe.replace("(A.AKTIF=true or A.AKTIF=false)", "(A.AKTIF=true)");
            			while(tmpe.contains("H.ID_MASTER_STD=?")) {
            				tmpe = tmpe.replace("H.ID_MASTER_STD=?", "H.ID_MASTER_STD>0");	
            			}
            			while(tmpe.contains("H.ID_TIPE_STD=?")) {
            				tmpe = tmpe.replace("H.ID_TIPE_STD=?", "H.ID_TIPE_STD>0");	
            			}
            			
            			//System.out.println("cmd_search_list_std_for_editor="+cmd_search_list_std_for_editor);
            			stmt = con.prepareStatement(tmpe);
            			//stmt.setInt(1, id_master_std);
                		//stmt.setInt(2, id_tipe_std);
            		}
            		else {
            			stmt = con.prepareStatement(tmp);
            			//stmt.setInt(1, id_master_std);
                		//stmt.setInt(2, id_tipe_std);
                		//stmt.setInt(3, id_master_std);
                		//stmt.setInt(4, id_tipe_std);
                		//stmt.setInt(5, id_master_std);
                		//stmt.setInt(6, id_tipe_std);

            		}
            			
            		
            	}
            	//else {
            		//kayaknya ngga sampe sini
            		//stmt = con.prepareStatement("SELECT A.ID,A.ID_STD,A.PERNYATAAN_STD,A.NO_BUTIR,A.KDPST,A.RASIONALE,A.AKTIF,A.TGL_MULAI_AKTIF,A.TGL_STOP_AKTIF,A.SCOPE,A.TIPE_PROSES_PENGAWASAN,B.ID_VERSI,B.ID_STD_ISI,B.ID_MANUAL_PENETAPAN,B.ID_MANUAL_PELAKSANAAN,B.ID_MANUAL_EVALUASI,B.ID_MANUAL_PENGENDALIAN,B.ID_MANUAL_PENINGKATAN,B.TGL_STA,B.TGL_END,B.TARGET_THSMS_1,B.TARGET_THSMS_2,B.TARGET_THSMS_3,B.TARGET_THSMS_4,B.TARGET_THSMS_5,B.TARGET_THSMS_6,B.PIHAK_TERKAIT,B.DOKUMEN_TERKAIT,B.TKN_INDIKATOR,B.NO_URUT_TAMPIL,B.TARGET_PERIOD_START,B.UNIT_PERIOD_USED,B.LAMA_NOMINAL_PER_PERIOD,B.TARGET_THSMS_1_UNIT,B.TARGET_THSMS_2_UNIT,B.TARGET_THSMS_3_UNIT,B.TARGET_THSMS_4_UNIT,B.TARGET_THSMS_5_UNIT,B.TARGET_THSMS_6_UNIT,B.PIHAK_MONITOR,B.TKN_PARAMETER,B.AKTIF,B.KDPST,D.ID_MASTER_STD,ID_TIPE_STD FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on ID=ID_STD_ISI inner join STANDARD_TABLE C on A.ID_STD=C.ID_STD inner join STANDARD_MASTER_TABLE D ON C.ID_MASTER_STD=D.ID_MASTER_STD where A.ID_STD is not null order by A.PERNYATAAN_STD,B.TKN_INDIKATOR");
            		//;
            		//stmt = con.prepareStatement("SELECT * FROM STANDARD_ISI_TABLE inner join STANDARD_VERSION on ID=ID_STD_ISI where ID_STD is not null order by ID;");
            	//}
            	
            	
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		
            		 // FILTER DISTINCT STD : jadi bila ada versi khusus prodi dan general maka hanya terhitung 1 krn stdnya sama
            		 
            		v = new Vector();
            		li = v.listIterator();
            		//rs.getInt("ID"); = ID_VERSI
            		//rs.getInt("ID_STD"); = ID_STD_ISI
            		String id_versi=""+rs.getInt("ID_VERSI");
            		if(Checker.isStringNullOrEmpty(id_versi)) {
            			id_versi = "null";
            		}
            		String id_std_isi=""+rs.getInt("ID_STD_ISI");
            		String id_std = ""+rs.getInt("ID_STD"); 
            		String isi_std=""+rs.getString("PERNYATAAN_STD");
            		String butir=""+rs.getInt("NO_BUTIR");
            		String kdpst=""+rs.getString("KDPST");
            		String rasionale=""+rs.getString("RASIONALE");
            		String aktif=""+rs.getBoolean("AKTIF");
            		String tgl_mulai_aktif=""+rs.getDate("TGL_MULAI_AKTIF");
            		String tgl_stop_aktif=""+rs.getDate("TGL_STOP_AKTIF");
            		String cakupan = ""+rs.getString("SCOPE");
            		String tipe_survey = ""+rs.getString("TIPE_PROSES_PENGAWASAN");
            		
            		String tgl_sta=""+rs.getDate("TGL_STA");
            		String tgl_end=""+rs.getDate("TGL_END");
            		String target1=""+rs.getString("TARGET_THSMS_1");
            		String target2=""+rs.getString("TARGET_THSMS_2");
            		String target3=""+rs.getString("TARGET_THSMS_3");
            		String target4=""+rs.getString("TARGET_THSMS_4");
            		String target5=""+rs.getString("TARGET_THSMS_5");
            		String target6=""+rs.getString("TARGET_THSMS_6");
            		String pihak=""+rs.getString("PIHAK_TERKAIT");
            		String dokumen=""+rs.getString("DOKUMEN_TERKAIT");
            		String indikator=""+rs.getString("TKN_INDIKATOR");
            		String periode_start=""+rs.getString("TARGET_PERIOD_START");
            		String unit_used_by_periode_start=""+rs.getString("UNIT_PERIOD_USED");
            		String besaran_interval_per_period=""+rs.getInt("LAMA_NOMINAL_PER_PERIOD");
            		String unit_used_byTarget=""+rs.getString("TARGET_THSMS_1_UNIT");
            		String pengawas=""+rs.getString("PIHAK_MONITOR");
            		String param=""+rs.getString("TKN_PARAMETER");
            		//if(Checker.isStringNullOrEmpty(tipe_survey)) {
            		//	tipe_survey = "null";
            		//}
            		String id_master = "0";//default
            		try {
            			id_master=""+rs.getString("ID_MASTER_STD");
            		}
            		catch (Exception e) {}
            		String id_tipe = "0";//default
            		try {
            			id_tipe=""+rs.getString("ID_TIPE_STD");
            		}
            		catch(Exception e) {}
            		String prev_indikator=new String(indikator);
            		String prev_isi_std=new String(isi_std);
            		//if(Checker.isStringNullOrEmpty(id_tipe)) {
            		//	id_tipe = "null";
            		//}
            		
            		tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey+"`"+id_master+"`"+id_tipe;
            		li.add(tmp);
            		while(rs.next()) {
            			id_versi=""+rs.getInt("ID_VERSI");
                		if(Checker.isStringNullOrEmpty(id_versi)) {
                			id_versi = "null";
                		}
                		id_std_isi=""+rs.getInt("ID_STD_ISI");
                		id_std = ""+rs.getInt("ID_STD");
                		isi_std=""+rs.getString("PERNYATAAN_STD");
                		butir=""+rs.getInt("NO_BUTIR");
                		kdpst=""+rs.getString("KDPST");
                		rasionale=""+rs.getString("RASIONALE");
                		aktif=""+rs.getBoolean("AKTIF");
                		tgl_mulai_aktif=""+rs.getDate("TGL_MULAI_AKTIF");
                		tgl_stop_aktif=""+rs.getDate("TGL_STOP_AKTIF");
                		cakupan = ""+rs.getString("SCOPE");
                		tipe_survey = ""+rs.getString("TIPE_PROSES_PENGAWASAN");
                		tgl_sta=""+rs.getDate("TGL_STA");
                		tgl_end=""+rs.getDate("TGL_END");
                		target1=""+rs.getString("TARGET_THSMS_1");
                		target2=""+rs.getString("TARGET_THSMS_2");
                		target3=""+rs.getString("TARGET_THSMS_3");
                		target4=""+rs.getString("TARGET_THSMS_4");
                		target5=""+rs.getString("TARGET_THSMS_5");
                		target6=""+rs.getString("TARGET_THSMS_6");
                		pihak=""+rs.getString("PIHAK_TERKAIT");
                		dokumen=""+rs.getString("DOKUMEN_TERKAIT");
                		indikator=""+rs.getString("TKN_INDIKATOR");
                		periode_start=""+rs.getString("TARGET_PERIOD_START");
                		unit_used_by_periode_start=""+rs.getString("UNIT_PERIOD_USED");
                		besaran_interval_per_period=""+rs.getInt("LAMA_NOMINAL_PER_PERIOD");
                		unit_used_byTarget=""+rs.getString("TARGET_THSMS_1_UNIT");
                		pengawas=""+rs.getString("PIHAK_MONITOR");
                		param=""+rs.getString("TKN_PARAMETER");
                		//if(Checker.isStringNullOrEmpty(tipe_survey)) {
                		//	tipe_survey = "null";
                		//}
                		id_master = "0";//default
                		try {
                			id_master=""+rs.getString("ID_MASTER_STD");
                		}
                		catch (Exception e) {}
                		id_tipe = "0";//default
                		try {
                			id_tipe=""+rs.getString("ID_TIPE_STD");
                		}
                		catch(Exception e) {}
                		if(prev_isi_std.trim().toUpperCase().equalsIgnoreCase(isi_std.trim().toUpperCase()) && prev_indikator.toUpperCase().trim().equalsIgnoreCase(indikator.toUpperCase().trim())) {
                			//kalo sama ignore = kita cari distinct
                		}
                		else {
                			tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey+"`"+id_master+"`"+id_tipe;
                    		li.add(tmp);	
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

    	return v;
    }
    */
    /*
    public Vector getStandardBelumAktif(boolean editor, String target_kdpst, int usr_objid, String scope) {
    	Vector v = null;
    	ListIterator li = null;
 		
    	if(!Checker.isStringNullOrEmpty(target_kdpst)) {
    		String tmp = new String(cmd_search_list_std_tamplet);
    		tmp = tmp.replace("A.KDPST='targetKdpst'", "A.KDPST='"+target_kdpst+"'");
    		tmp = tmp.replace("E.KDPST='targetKdpst'", "E.KDPST='"+target_kdpst+"'");
    		tmp = tmp.replace("(A.AKTIF=true or A.AKTIF=false)", "(A.AKTIF=false)");
    		tmp = tmp.replace("(C.AKTIF=true or C.AKTIF=false)", "(C.AKTIF=false)");
    		while(tmp.contains("H.ID_MASTER_STD=?")) {
    			tmp = tmp.replace("H.ID_MASTER_STD=?", "H.ID_MASTER_STD>0");	
    		}
    		while(tmp.contains("H.ID_TIPE_STD=?")) {
    			tmp = tmp.replace("H.ID_TIPE_STD=?", "H.ID_TIPE_STD>0");	
    		}
    		
    		
    		//System.out.println("cmd_search_list_std_tamplet="+cmd_search_list_std_tamplet);
    		//System.out.println("id_master_std="+id_master_std);
    		//System.out.println("id_tipe_std="+id_tipe_std);
        	try {
            	Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	//cmd_search_list_std 
            	if(true) {
            		//stmt = con.prepareStatement("SELECT A.ID,A.ID_STD,A.PERNYATAAN_STD,A.NO_BUTIR,A.KDPST,A.RASIONALE,A.AKTIF,A.TGL_MULAI_AKTIF,A.TGL_STOP_AKTIF,A.SCOPE,A.TIPE_PROSES_PENGAWASAN,B.ID_VERSI,B.ID_STD_ISI,B.ID_MANUAL_PENETAPAN,B.ID_MANUAL_PELAKSANAAN,B.ID_MANUAL_EVALUASI,B.ID_MANUAL_PENGENDALIAN,B.ID_MANUAL_PENINGKATAN,B.TGL_STA,B.TGL_END,B.TARGET_THSMS_1,B.TARGET_THSMS_2,B.TARGET_THSMS_3,B.TARGET_THSMS_4,B.TARGET_THSMS_5,B.TARGET_THSMS_6,B.PIHAK_TERKAIT,B.DOKUMEN_TERKAIT,B.TKN_INDIKATOR,B.NO_URUT_TAMPIL,B.TARGET_PERIOD_START,B.UNIT_PERIOD_USED,B.LAMA_NOMINAL_PER_PERIOD,B.TARGET_THSMS_1_UNIT,B.TARGET_THSMS_2_UNIT,B.TARGET_THSMS_3_UNIT,B.TARGET_THSMS_4_UNIT,B.TARGET_THSMS_5_UNIT,B.TARGET_THSMS_6_UNIT,B.PIHAK_MONITOR,B.TKN_PARAMETER,B.AKTIF,B.KDPST,D.ID_MASTER_STD,ID_TIPE_STD FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on ID=ID_STD_ISI inner join STANDARD_TABLE C on A.ID_STD=C.ID_STD inner join STANDARD_MASTER_TABLE D ON C.ID_MASTER_STD=D.ID_MASTER_STD where A.ID_STD is not null AND D.ID_MASTER_STD=? and ID_TIPE_STD=? order by A.PERNYATAAN_STD,B.TKN_INDIKATOR");
            		if(editor) {
            			String tmpe = new String(cmd_search_list_std_for_editor);
            			tmpe = tmpe.replace("(A.AKTIF=true or A.AKTIF=false)", "(A.AKTIF=false)");
            			while(tmpe.contains("H.ID_MASTER_STD=?")) {
            				tmpe = tmpe.replace("H.ID_MASTER_STD=?", "H.ID_MASTER_STD>0");	
            			}
            			while(tmpe.contains("H.ID_TIPE_STD=?")) {
            				tmpe = tmpe.replace("H.ID_TIPE_STD=?", "H.ID_TIPE_STD>0");	
            			}
            			
            			//System.out.println("cmd_search_list_std_for_editor="+cmd_search_list_std_for_editor);
            			stmt = con.prepareStatement(tmpe);
            			//stmt.setInt(1, id_master_std);
                		//stmt.setInt(2, id_tipe_std);
            		}
            		else {
            			stmt = con.prepareStatement(tmp);
            			//stmt.setInt(1, id_master_std);
                		//stmt.setInt(2, id_tipe_std);
                		//stmt.setInt(3, id_master_std);
                		//stmt.setInt(4, id_tipe_std);
                		//stmt.setInt(5, id_master_std);
                		//stmt.setInt(6, id_tipe_std);

            		}
            			
            		
            	}
            	//else {
            		//kayaknya ngga sampe sini
            		//stmt = con.prepareStatement("SELECT A.ID,A.ID_STD,A.PERNYATAAN_STD,A.NO_BUTIR,A.KDPST,A.RASIONALE,A.AKTIF,A.TGL_MULAI_AKTIF,A.TGL_STOP_AKTIF,A.SCOPE,A.TIPE_PROSES_PENGAWASAN,B.ID_VERSI,B.ID_STD_ISI,B.ID_MANUAL_PENETAPAN,B.ID_MANUAL_PELAKSANAAN,B.ID_MANUAL_EVALUASI,B.ID_MANUAL_PENGENDALIAN,B.ID_MANUAL_PENINGKATAN,B.TGL_STA,B.TGL_END,B.TARGET_THSMS_1,B.TARGET_THSMS_2,B.TARGET_THSMS_3,B.TARGET_THSMS_4,B.TARGET_THSMS_5,B.TARGET_THSMS_6,B.PIHAK_TERKAIT,B.DOKUMEN_TERKAIT,B.TKN_INDIKATOR,B.NO_URUT_TAMPIL,B.TARGET_PERIOD_START,B.UNIT_PERIOD_USED,B.LAMA_NOMINAL_PER_PERIOD,B.TARGET_THSMS_1_UNIT,B.TARGET_THSMS_2_UNIT,B.TARGET_THSMS_3_UNIT,B.TARGET_THSMS_4_UNIT,B.TARGET_THSMS_5_UNIT,B.TARGET_THSMS_6_UNIT,B.PIHAK_MONITOR,B.TKN_PARAMETER,B.AKTIF,B.KDPST,D.ID_MASTER_STD,ID_TIPE_STD FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on ID=ID_STD_ISI inner join STANDARD_TABLE C on A.ID_STD=C.ID_STD inner join STANDARD_MASTER_TABLE D ON C.ID_MASTER_STD=D.ID_MASTER_STD where A.ID_STD is not null order by A.PERNYATAAN_STD,B.TKN_INDIKATOR");
            		//;
            		//stmt = con.prepareStatement("SELECT * FROM STANDARD_ISI_TABLE inner join STANDARD_VERSION on ID=ID_STD_ISI where ID_STD is not null order by ID;");
            	//}
            	
            	
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		
            		 //FILTER DISTINCT STD : jadi bila ada versi khusus prodi dan general maka hanya terhitung 1 krn stdnya sama
            		 
            		v = new Vector();
            		li = v.listIterator();
            		//rs.getInt("ID"); = ID_VERSI
            		//rs.getInt("ID_STD"); = ID_STD_ISI
            		String id_versi=""+rs.getInt("ID_VERSI");
            		if(Checker.isStringNullOrEmpty(id_versi)) {
            			id_versi = "null";
            		}
            		String id_std_isi=""+rs.getInt("ID_STD_ISI");
            		String id_std = ""+rs.getInt("ID_STD"); 
            		String isi_std=""+rs.getString("PERNYATAAN_STD");
            		String butir=""+rs.getInt("NO_BUTIR");
            		String kdpst=""+rs.getString("KDPST");
            		String rasionale=""+rs.getString("RASIONALE");
            		String aktif=""+rs.getBoolean("AKTIF");
            		String tgl_mulai_aktif=""+rs.getDate("TGL_MULAI_AKTIF");
            		String tgl_stop_aktif=""+rs.getDate("TGL_STOP_AKTIF");
            		String cakupan = ""+rs.getString("SCOPE");
            		String tipe_survey = ""+rs.getString("TIPE_PROSES_PENGAWASAN");
            		
            		String tgl_sta=""+rs.getDate("TGL_STA");
            		String tgl_end=""+rs.getDate("TGL_END");
            		String target1=""+rs.getString("TARGET_THSMS_1");
            		String target2=""+rs.getString("TARGET_THSMS_2");
            		String target3=""+rs.getString("TARGET_THSMS_3");
            		String target4=""+rs.getString("TARGET_THSMS_4");
            		String target5=""+rs.getString("TARGET_THSMS_5");
            		String target6=""+rs.getString("TARGET_THSMS_6");
            		String pihak=""+rs.getString("PIHAK_TERKAIT");
            		String dokumen=""+rs.getString("DOKUMEN_TERKAIT");
            		String indikator=""+rs.getString("TKN_INDIKATOR");
            		String periode_start=""+rs.getString("TARGET_PERIOD_START");
            		String unit_used_by_periode_start=""+rs.getString("UNIT_PERIOD_USED");
            		String besaran_interval_per_period=""+rs.getInt("LAMA_NOMINAL_PER_PERIOD");
            		String unit_used_byTarget=""+rs.getString("TARGET_THSMS_1_UNIT");
            		String pengawas=""+rs.getString("PIHAK_MONITOR");
            		String param=""+rs.getString("TKN_PARAMETER");
            		//if(Checker.isStringNullOrEmpty(tipe_survey)) {
            		//	tipe_survey = "null";
            		//}
            		String id_master = "0";//default
            		try {
            			id_master=""+rs.getString("ID_MASTER_STD");
            		}
            		catch (Exception e) {}
            		String id_tipe = "0";//default
            		try {
            			id_tipe=""+rs.getString("ID_TIPE_STD");
            		}
            		catch(Exception e) {}
            		String prev_indikator=new String(indikator);
            		String prev_isi_std=new String(isi_std);
            		//if(Checker.isStringNullOrEmpty(id_tipe)) {
            		//	id_tipe = "null";
            		//}
            		
            		tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey+"`"+id_master+"`"+id_tipe;
            		li.add(tmp);
            		while(rs.next()) {
            			id_versi=""+rs.getInt("ID_VERSI");
                		if(Checker.isStringNullOrEmpty(id_versi)) {
                			id_versi = "null";
                		}
                		id_std_isi=""+rs.getInt("ID_STD_ISI");
                		id_std = ""+rs.getInt("ID_STD");
                		isi_std=""+rs.getString("PERNYATAAN_STD");
                		butir=""+rs.getInt("NO_BUTIR");
                		kdpst=""+rs.getString("KDPST");
                		rasionale=""+rs.getString("RASIONALE");
                		aktif=""+rs.getBoolean("AKTIF");
                		tgl_mulai_aktif=""+rs.getDate("TGL_MULAI_AKTIF");
                		tgl_stop_aktif=""+rs.getDate("TGL_STOP_AKTIF");
                		cakupan = ""+rs.getString("SCOPE");
                		tipe_survey = ""+rs.getString("TIPE_PROSES_PENGAWASAN");
                		tgl_sta=""+rs.getDate("TGL_STA");
                		tgl_end=""+rs.getDate("TGL_END");
                		target1=""+rs.getString("TARGET_THSMS_1");
                		target2=""+rs.getString("TARGET_THSMS_2");
                		target3=""+rs.getString("TARGET_THSMS_3");
                		target4=""+rs.getString("TARGET_THSMS_4");
                		target5=""+rs.getString("TARGET_THSMS_5");
                		target6=""+rs.getString("TARGET_THSMS_6");
                		pihak=""+rs.getString("PIHAK_TERKAIT");
                		dokumen=""+rs.getString("DOKUMEN_TERKAIT");
                		indikator=""+rs.getString("TKN_INDIKATOR");
                		periode_start=""+rs.getString("TARGET_PERIOD_START");
                		unit_used_by_periode_start=""+rs.getString("UNIT_PERIOD_USED");
                		besaran_interval_per_period=""+rs.getInt("LAMA_NOMINAL_PER_PERIOD");
                		unit_used_byTarget=""+rs.getString("TARGET_THSMS_1_UNIT");
                		pengawas=""+rs.getString("PIHAK_MONITOR");
                		param=""+rs.getString("TKN_PARAMETER");
                		//if(Checker.isStringNullOrEmpty(tipe_survey)) {
                		//	tipe_survey = "null";
                		//}
                		id_master = "0";//default
                		try {
                			id_master=""+rs.getString("ID_MASTER_STD");
                		}
                		catch (Exception e) {}
                		id_tipe = "0";//default
                		try {
                			id_tipe=""+rs.getString("ID_TIPE_STD");
                		}
                		catch(Exception e) {}
                		if(prev_isi_std.trim().toUpperCase().equalsIgnoreCase(isi_std.trim().toUpperCase()) && prev_indikator.toUpperCase().trim().equalsIgnoreCase(indikator.toUpperCase().trim())) {
                			//kalo sama ignore = kita cari distinct
                		}
                		else {
                			tmp = id_versi+"`"+id_std_isi+"`"+id_std+"`"+isi_std+"`"+butir+"`"+kdpst+"`"+rasionale+"`"+tgl_sta+"`"+tgl_end+"`"+target1+"`"+target2+"`"+target3+"`"+target4+"`"+target5+"`"+target6+"`"+pihak+"`"+dokumen+"`"+indikator+"`"+periode_start+"`"+unit_used_by_periode_start+"`"+besaran_interval_per_period+"`"+unit_used_byTarget+"`"+pengawas+"`"+param+"`"+aktif+"`"+cakupan+"`"+tgl_mulai_aktif+"`"+tgl_stop_aktif+"`"+tipe_survey+"`"+id_master+"`"+id_tipe;
                    		li.add(tmp);	
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

    	return v;
    }
    */
   /* 
    public boolean isStandardActivated(int id_std_isi) {
    	boolean active = false;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select AKTIF from STANDARD_ISI_TABLE where ID=?");
        	stmt.setInt(1,id_std_isi);
        	rs = stmt.executeQuery();
        	rs.next();
        	active = rs.getBoolean(1);
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
    	return active;
    }
    */
    public Boolean apaStandarSudahAdaTglPerumusanUmum(int id_versi, int id_std) {
    	boolean ada=false;;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_KEGIATAN_PERUMUSAN_STD_END from STANDARD_TIPE_VERSION where ID_VERSI=? and ID_STD=? and TGL_KEGIATAN_PERUMUSAN_STD_END IS NOT NULL");
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std);
        	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ada = true;
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
    	return ada;
    }
    
    public Boolean apaAdaManualPerencanaanYgAktif(int id_std) {
    	boolean ada=false;;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_STA from STANDARD_MANUAL_PERENCANAAN_UMUM where ID_STD=? and TGL_STA IS NOT NULL AND TGL_END IS NULL");
        	stmt.setInt(1, id_std);
        	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ada = true;
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
    	return ada;
    }
    
    public Boolean apaAdaManualPelaksanaanYgAktif(int id_std) {
    	boolean ada=false;;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_STA from STANDARD_MANUAL_PELAKSANAAN_UMUM where ID_STD=? and TGL_STA IS NOT NULL AND TGL_END IS NULL");
        	stmt.setInt(1, id_std);
        	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ada = true;
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
    	return ada;
    }
    
    public Boolean apaAdaManualEvaluasiYgAktif(int id_std) {
    	boolean ada=false;;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_STA from STANDARD_MANUAL_EVALUASI_UMUM where ID_STD=? and TGL_STA IS NOT NULL AND TGL_END IS NULL");
        	stmt.setInt(1, id_std);
        	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ada = true;
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
    	return ada;
    }
    
    public Boolean apaAdaManualPengendalianYgAktif(int id_std) {
    	boolean ada=false;;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_STA from STANDARD_MANUAL_PENGENDALIAN_UMUM where ID_STD=? and TGL_STA IS NOT NULL AND TGL_END IS NULL");
        	stmt.setInt(1, id_std);
        	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ada = true;
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
    	return ada;
    }
    
    public Boolean apaAdaManualPeningkatanYgAktif(int id_std) {
    	boolean ada=false;;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_STA from STANDARD_MANUAL_PENINGKATAN_UMUM where ID_STD=? and TGL_STA IS NOT NULL AND TGL_END IS NULL");
        	stmt.setInt(1, id_std);
        	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ada = true;
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
    	return ada;
    }
    
    public Boolean apaStandarSudahAdaTglPersetujuanUmum(int id_versi, int id_std) {
    	boolean ada=false;;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_KEGIATAN_PERSETUJUAN_STD_END from STANDARD_TIPE_VERSION where ID_VERSI=? and ID_STD=? and TGL_KEGIATAN_PERSETUJUAN_STD_END is not NULL");
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std);
        	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ada = true;
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
    	return ada;
    }

    public Boolean apaStandarSudahAdaTglPemeriksaanUmum(int id_versi, int id_std) {
    	boolean ada=false;;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_KEGIATAN_PEMERIKSAAN_STD_END from STANDARD_TIPE_VERSION where ID_VERSI=? and ID_STD=? and TGL_KEGIATAN_PEMERIKSAAN_STD_END is not null");
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ada = true;
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
    	return ada;
    }
    
    public Boolean apaStandarSudahAdaTglPenetapanUmum(int id_versi, int id_std) {
    	boolean ada=false;;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_KEGIATAN_PENETAPAN_STD_END from STANDARD_TIPE_VERSION where ID_VERSI=? and ID_STD=? and TGL_KEGIATAN_PENETAPAN_STD_END is not null");
        	stmt.setInt(1, id_versi);
        	stmt.setInt(2, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		ada = true;
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
    	return ada;
    }
    
    public boolean isStandardActivated(int id_versi, int id_std) {
    	boolean active = false;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_STA from STANDARD_TIPE_VERSION where ID_STD=? and ID_VERSI=?");
        	stmt.setInt(1,id_std);
        	stmt.setInt(2,id_versi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		java.sql.Date dt = rs.getDate(1);
        		if(dt!=null) {
        			active = true;
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
    	return active;
    }
    
    public boolean isStandardExpired(int id_versi, int id_std) {
    	boolean expired = false;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_END from STANDARD_TIPE_VERSION where ID_STD=? and ID_VERSI=?");
        	stmt.setInt(1,id_versi);
        	stmt.setInt(2,id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		java.sql.Date dt = rs.getDate(1);
        		if(dt!=null) {
        			expired = true;
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
    	return expired;
    }
    /*
    public boolean isStandardExpired(int id_std_isi) {
    	boolean expired = true;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select TGL_STOP_AKTIF from STANDARD_ISI_TABLE where ID=?");
        	stmt.setInt(1,id_std_isi);
        	rs = stmt.executeQuery();
        	rs.next();
        	java.sql.Date dt = rs.getDate(1);
        	if(dt==null) {
        		expired = false;
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
    	return expired;
    }
    */
    
    public boolean isStandardOnlyForThisProdi(int id_std_isi, String target_kdpst) {
    	boolean this_prodi_only = false;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select KDPST from STANDARD_ISI_TABLE where ID=? and KDPST=?");
        	stmt.setInt(1,id_std_isi);
        	stmt.setString(2,target_kdpst);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		this_prodi_only = true;
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
    	return this_prodi_only;
    }
    
    
    public String getListPihakTerkaitPadaIsiStd(int id_versi, int id_std_isi) {
    	String list_pihak_terkait=null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select PIHAK_TERKAIT from STANDARD_VERSION where ID_VERSI=? and ID_STD_ISI=?");
        	stmt.setInt(1,id_versi);
        	stmt.setInt(2,id_std_isi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		list_pihak_terkait = new String(rs.getString(1));
        		if(!Checker.isStringNullOrEmpty(list_pihak_terkait)) {
        			list_pihak_terkait = list_pihak_terkait.replace(",", "`"); //mastiin aja char pemisahnya
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
    	return list_pihak_terkait;
    }
    
    public Vector getStandardInfo(int id_versi, int id_std_isi) {
    	Vector v= null;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("SELECT * FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on A.ID=B.ID_STD_ISI where A.ID=? and B.ID_VERSI=?");
        	stmt.setInt(1, id_std_isi);
        	stmt.setInt(2, id_versi);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		ListIterator li = v.listIterator();
        		li.add(""+rs.getString("A.ID_STD"));
        		li.add(""+rs.getString("A.PERNYATAAN_STD"));
        		li.add(""+rs.getString("A.NO_BUTIR"));
        		li.add(""+rs.getString("A.KDPST"));
        		li.add(""+rs.getString("A.RASIONALE"));
        		li.add(""+rs.getString("A.AKTIF"));
        		li.add(""+rs.getString("A.TGL_MULAI_AKTIF"));
        		li.add(""+rs.getString("A.TGL_STOP_AKTIF"));
        		li.add(""+rs.getString("A.SCOPE"));
        		li.add(""+rs.getString("A.TIPE_PROSES_PENGAWASAN"));
        		li.add(""+rs.getString("B.ID_VERSI"));
        		li.add(""+rs.getString("B.ID_MANUAL_PENETAPAN"));
        		li.add(""+rs.getString("B.ID_MANUAL_PELAKSANAAN"));
        		li.add(""+rs.getString("B.ID_MANUAL_EVALUASI"));
        		li.add(""+rs.getString("B.ID_MANUAL_PENGENDALIAN"));
        		li.add(""+rs.getString("B.ID_MANUAL_PENINGKATAN"));
        		li.add(""+rs.getString("B.TGL_STA"));
        		li.add(""+rs.getString("B.TGL_END"));
        		li.add(""+rs.getString("B.TARGET_THSMS_1"));
        		li.add(""+rs.getString("B.TARGET_THSMS_2"));
        		li.add(""+rs.getString("B.TARGET_THSMS_3"));
        		li.add(""+rs.getString("B.TARGET_THSMS_4"));
        		li.add(""+rs.getString("B.TARGET_THSMS_5"));
        		li.add(""+rs.getString("B.TARGET_THSMS_6"));
        		li.add(""+rs.getString("B.PIHAK_TERKAIT"));
        		li.add(""+rs.getString("B.DOKUMEN_TERKAIT"));
        		li.add(""+rs.getString("B.TKN_INDIKATOR"));
        		li.add(""+rs.getString("B.NO_URUT_TAMPIL"));
        		li.add(""+rs.getString("B.TARGET_PERIOD_START"));
        		li.add(""+rs.getString("B.UNIT_PERIOD_USED"));
        		li.add(""+rs.getString("B.LAMA_NOMINAL_PER_PERIOD"));
        		li.add(""+rs.getString("B.TARGET_THSMS_1_UNIT"));
        		li.add(""+rs.getString("B.PIHAK_MONITOR"));
        		li.add(""+rs.getString("B.TKN_PARAMETER"));
        		li.add(""+rs.getString("B.AKTIF"));
        		li.add(""+rs.getString("B.KDPST"));//disini tidak berguna , krn kita berdasarkan std isi id, kalo ada spesifik utk prodi tertentu ,aka sudah terpisah di std_isi_id
        		
        		
        		
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
    
    public String getStandardInfoForGivenColomn(int id_versi, int id_std_isi, String tkn_target_colomn) {
    	String token_value = null;
    	token_value = getPernyataanStd(id_versi,id_std_isi, tkn_target_colomn);
    	return token_value;
    }
    //deprecated
    /*
     * PENAMAANNYA YG DIGANTI, pake public String getStandardInfoForGivenColomn(int id_versi, int id_std_isi, String tkn_target_colomn)
     */
    public String getPernyataanStd(int id_versi, int id_std_isi, String tkn_target_colomn) {
    	String token_value = null;
    	if(!Checker.isStringNullOrEmpty(tkn_target_colomn)) {
    		String seperator =Checker.getSeperatorYgDigunakan(tkn_target_colomn);
    		if(!seperator.equalsIgnoreCase(",")) {
    			while(tkn_target_colomn.contains(seperator)) {
    				tkn_target_colomn = tkn_target_colomn.replace(seperator, ",");
    			}
    		}
    		StringTokenizer st = new StringTokenizer(tkn_target_colomn,",");
    		int total = st.countTokens();
    		try {
    			//int i = 1;
            	Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	stmt = con.prepareStatement("SELECT "+tkn_target_colomn+" FROM STANDARD_ISI_TABLE A inner join STANDARD_VERSION B on ID=ID_STD_ISI where ID=? and ID_VERSI=?");
            	stmt.setInt(1,id_std_isi);
            	stmt.setInt(2,id_versi);
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		token_value = new String();
            		for(int i=0;i<total;) {
            			token_value = token_value+rs.getString(++i).trim();
            			if(i<total) {
            				token_value = token_value+"~";
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
    	
    	return token_value;
    }
    
    public String getNamaRumpunStandar(int id_master_std) {
    	String nmm=null;
    	try {
			//int i = 1;
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select KET_TIPE_STD from STANDARD_MASTER_TABLE where ID_MASTER_STD=?");
        	stmt.setInt(1, id_master_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		nmm = rs.getString(1);
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
    	return nmm;
    }
    
    public String getNamaStandar(int id_master_std, int id_tipe_std) {
    	String nmm=null;
    	try {
			//int i = 1;
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select KET_TIPE_STD from STANDARD_TABLE where ID_MASTER_STD=? and ID_TIPE_STD=?");
        	stmt.setInt(1, id_master_std);
        	stmt.setInt(2, id_tipe_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		nmm = rs.getString(1);
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
    	return nmm;
    }
    
    public String getNamaStandar(int id_std) {
    	String nmm=null;
    	try {
			//int i = 1;
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select KET_TIPE_STD from STANDARD_TABLE where ID_STD=?");
        	stmt.setInt(1, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		nmm = rs.getString(1);
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
    	return nmm;
    }
    
    
    public Vector getInfoTampleteStandar(int id_std) {
    	Vector v=null;
    	ListIterator li=null;
    	try {
			//int i = 1;
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select ID_VERSI,TKN_JAB_PERUMUS,TKN_JAB_PERIKSA,TKN_JAB_SETUJU,TKN_JAB_PENETAP,TKN_JAB_KENDALI,TKN_JAB_PETUGAS_LAP,RASIONALE,PIHAK_TERKAIT_MENCAPAI_STD,DEFINISI,REFERENSI from STANDARD_SUBMASTER_VERSION where ID_STD=? order by ID_VERSI desc");
        	stmt.setInt(1, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		int i=1;
        		String versi = rs.getString(i++);
        		String tkn_jab_rumus = rs.getString(i++);
        		String tkn_jab_cek = rs.getString(i++);
        		String tkn_jab_stuju = rs.getString(i++);
        		String tkn_jab_tetap = rs.getString(i++);
        		String tkn_jab_kendali = rs.getString(i++);
        		String tkn_jab_lap = rs.getString(i++);
        		String rasionale = rs.getString(i++);
        		String pihak_terkait_capaian = rs.getString(i++);
        		String definisi = rs.getString(i++);
        		String referensi = rs.getString(i++);
        		String tmp = versi.trim()+"~"+tkn_jab_rumus.trim()+"~"+tkn_jab_cek.trim()+"~"+tkn_jab_stuju.trim()+"~"+tkn_jab_tetap.trim()+"~"+tkn_jab_kendali.trim()+"~"+tkn_jab_lap.trim()+"~"+rasionale.trim()+"~"+pihak_terkait_capaian.trim()+"~"+definisi.trim()+"~"+referensi.trim();
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
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
    
    /*
    public Vector getInfoTampleteStandar(int id_master, int id_tipe_std) {
    	Vector v=null;
    	ListIterator li=null;
    	try {
			//int i = 1;
    		
    		 * ambil yang terkini, dengan asumsi bila > 1, maka data yg diambil data yg ke 2 dan data yg pertama harus sudah expired tgl_end != null
    		 
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select B.ID_VERSI,B.TKN_JAB_PERUMUS,B.TKN_JAB_PERIKSA,B.TKN_JAB_SETUJU,B.TKN_JAB_PENETAP,B.TKN_JAB_KENDALI,B.TKN_JAB_PETUGAS_LAP,B.RASIONALE,B.PIHAK_TERKAIT_MENCAPAI_STD,B.DEFINISI,B.REFERENSI,B.TGL_STA,B.TGL_END from STANDARD_TABLE A left join STANDARD_TIPE_VERSION B on (A.ID_STD=B.ID_STD) where A.ID_MASTER_STD=? AND A.ID_TIPE_STD=? order by B.ID_VERSI desc");
        	stmt.setInt(1, id_master);
        	stmt.setInt(2, id_tipe_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		int i=1;
        		String versi = rs.getString(i++);
        		String tkn_jab_rumus = rs.getString(i++);
        		if(tkn_jab_rumus!=null) {
        			tkn_jab_rumus=tkn_jab_rumus.trim();
        		}
        		String tkn_jab_cek = rs.getString(i++);
        		if(tkn_jab_cek!=null) {
        			tkn_jab_cek=tkn_jab_cek.trim();
        		}
        		String tkn_jab_stuju = rs.getString(i++);
        		if(tkn_jab_stuju!=null) {
        			tkn_jab_stuju=tkn_jab_stuju.trim();
        		}
        		String tkn_jab_tetap = rs.getString(i++);
        		if(tkn_jab_tetap!=null) {
        			tkn_jab_tetap=tkn_jab_tetap.trim();
        		}
        		String tkn_jab_kendali = rs.getString(i++);
        		if(tkn_jab_kendali!=null) {
        			tkn_jab_kendali=tkn_jab_kendali.trim();
        		}
        		String tkn_jab_lap = rs.getString(i++);
        		if(tkn_jab_lap!=null) {
        			tkn_jab_lap=tkn_jab_lap.trim();
        		}
        		String rasionale = rs.getString(i++);
        		if(rasionale!=null) {
        			rasionale=rasionale.trim();
        		}
        		String pihak_terkait_capaian = rs.getString(i++);
        		if(pihak_terkait_capaian!=null) {
        			pihak_terkait_capaian=pihak_terkait_capaian.trim();
        		}
        		String definisi = rs.getString(i++);
        		if(definisi!=null) {
        			definisi=definisi.trim();
        		}
        		String referensi = rs.getString(i++);
        		if(referensi!=null) {
        			referensi=referensi.trim();
        		}
        		String tglsta = rs.getString(i++);
        		if(tglsta!=null) {
        			tglsta=tglsta.trim();
        		}
        		String tglend = rs.getString(i++);
        		if(tglend!=null) {
        			tglend=tglend.trim();
        		}
        		String tmp = versi+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendali+"~"+tkn_jab_lap+"~"+rasionale+"~"+pihak_terkait_capaian+"~"+definisi+"~"+referensi+"~"+tglsta+"~"+tglend;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
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
    */
    
    public Vector getLatestInfoTampleteStandar(int id_master, int id_tipe_std) {
    	Vector v=null;
    	ListIterator li=null;
    	try {
			//int i = 1;
    		/*
    		 * ambil yang terkini, dengan asumsi bila > 1, maka data yg diambil data yg ke 2 dan data yg pertama harus sudah expired tgl_end != null
    		 */
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select B.ID_VERSI,B.TKN_JAB_PERUMUS,B.TKN_JAB_PERIKSA,B.TKN_JAB_SETUJU,B.TKN_JAB_PENETAP,B.TKN_JAB_KENDALI,B.TKN_JAB_PETUGAS_LAP,B.RASIONALE,B.PIHAK_TERKAIT_MENCAPAI_STD,B.DEFINISI,B.REFERENSI,B.TGL_STA,B.TGL_END,B.DOKUMEN_TERKAIT from STANDARD_TABLE A left join STANDARD_TIPE_VERSION B on (A.ID_STD=B.ID_STD) where A.ID_MASTER_STD=? AND A.ID_TIPE_STD=? order by B.ID_VERSI desc limit 1");
        	stmt.setInt(1, id_master);
        	stmt.setInt(2, id_tipe_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		int i=1;
        		String versi = rs.getString(i++);
        		String tkn_jab_rumus = rs.getString(i++);
        		if(tkn_jab_rumus!=null) {
        			tkn_jab_rumus=tkn_jab_rumus.trim();
        		}
        		String tkn_jab_cek = rs.getString(i++);
        		if(tkn_jab_cek!=null) {
        			tkn_jab_cek=tkn_jab_cek.trim();
        		}
        		String tkn_jab_stuju = rs.getString(i++);
        		if(tkn_jab_stuju!=null) {
        			tkn_jab_stuju=tkn_jab_stuju.trim();
        		}
        		String tkn_jab_tetap = rs.getString(i++);
        		if(tkn_jab_tetap!=null) {
        			tkn_jab_tetap=tkn_jab_tetap.trim();
        		}
        		String tkn_jab_kendali = rs.getString(i++);
        		if(tkn_jab_kendali!=null) {
        			tkn_jab_kendali=tkn_jab_kendali.trim();
        		}
        		String tkn_jab_lap = rs.getString(i++);
        		if(tkn_jab_lap!=null) {
        			tkn_jab_lap=tkn_jab_lap.trim();
        		}
        		String rasionale = rs.getString(i++);
        		if(rasionale!=null) {
        			rasionale=rasionale.trim();
        		}
        		String pihak_terkait_capaian = rs.getString(i++);
        		if(pihak_terkait_capaian!=null) {
        			pihak_terkait_capaian=pihak_terkait_capaian.trim();
        		}
        		String definisi = rs.getString(i++);
        		if(definisi!=null) {
        			definisi=definisi.trim();
        		}
        		String referensi = rs.getString(i++);
        		if(referensi!=null) {
        			referensi=referensi.trim();
        		}
        		String tglsta = rs.getString(i++);
        		if(tglsta!=null) {
        			tglsta=tglsta.trim();
        		}
        		String tglend = rs.getString(i++);
        		if(tglend!=null) {
        			tglend=tglend.trim();
        		}
        		String dok_terkait = rs.getString(i++);
        		if(dok_terkait!=null) {
        			dok_terkait=dok_terkait.trim();
        		}
        		String tmp = versi+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendali+"~"+tkn_jab_lap+"~"+rasionale+"~"+pihak_terkait_capaian+"~"+definisi+"~"+referensi+"~"+tglsta+"~"+tglend+"~"+dok_terkait;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
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
    
    public Vector getLatestInfoTampleteStandar(int id_master, int id_tipe_std, int id_versi_std) {
    	Vector v=null;
    	ListIterator li=null;
    	try {
			//int i = 1;
    		/*
    		 * ambil yang terkini, dengan asumsi bila > 1, maka data yg diambil data yg ke 2 dan data yg pertama harus sudah expired tgl_end != null
    		 */
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select B.ID_VERSI,B.TKN_JAB_PERUMUS,B.TKN_JAB_PERIKSA,B.TKN_JAB_SETUJU,B.TKN_JAB_PENETAP,B.TKN_JAB_KENDALI,B.TKN_JAB_PETUGAS_LAP,B.RASIONALE,B.PIHAK_TERKAIT_MENCAPAI_STD,B.DEFINISI,B.REFERENSI,B.TGL_STA,B.TGL_END,B.DOKUMEN_TERKAIT from STANDARD_TABLE A left join STANDARD_TIPE_VERSION B on (A.ID_STD=B.ID_STD) where A.ID_MASTER_STD=? AND A.ID_TIPE_STD=? and B.ID_VERSI=? order by B.ID_VERSI desc limit 1");
        	stmt.setInt(1, id_master);
        	stmt.setInt(2, id_tipe_std);
        	stmt.setInt(3, id_versi_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		int i=1;
        		String versi = rs.getString(i++);
        		String tkn_jab_rumus = rs.getString(i++);
        		if(tkn_jab_rumus!=null) {
        			tkn_jab_rumus=tkn_jab_rumus.trim();
        		}
        		String tkn_jab_cek = rs.getString(i++);
        		if(tkn_jab_cek!=null) {
        			tkn_jab_cek=tkn_jab_cek.trim();
        		}
        		String tkn_jab_stuju = rs.getString(i++);
        		if(tkn_jab_stuju!=null) {
        			tkn_jab_stuju=tkn_jab_stuju.trim();
        		}
        		String tkn_jab_tetap = rs.getString(i++);
        		if(tkn_jab_tetap!=null) {
        			tkn_jab_tetap=tkn_jab_tetap.trim();
        		}
        		String tkn_jab_kendali = rs.getString(i++);
        		if(tkn_jab_kendali!=null) {
        			tkn_jab_kendali=tkn_jab_kendali.trim();
        		}
        		String tkn_jab_lap = rs.getString(i++);
        		if(tkn_jab_lap!=null) {
        			tkn_jab_lap=tkn_jab_lap.trim();
        		}
        		String rasionale = rs.getString(i++);
        		if(rasionale!=null) {
        			rasionale=rasionale.trim();
        		}
        		String pihak_terkait_capaian = rs.getString(i++);
        		if(pihak_terkait_capaian!=null) {
        			pihak_terkait_capaian=pihak_terkait_capaian.trim();
        		}
        		String definisi = rs.getString(i++);
        		if(definisi!=null) {
        			definisi=definisi.trim();
        		}
        		String referensi = rs.getString(i++);
        		if(referensi!=null) {
        			referensi=referensi.trim();
        		}
        		String tglsta = rs.getString(i++);
        		if(tglsta!=null) {
        			tglsta=tglsta.trim();
        		}
        		String tglend = rs.getString(i++);
        		if(tglend!=null) {
        			tglend=tglend.trim();
        		}
        		String dok_terkait = rs.getString(i++);
        		if(dok_terkait!=null) {
        			dok_terkait=dok_terkait.trim();
        		}
        		String tmp = versi+"~"+tkn_jab_rumus+"~"+tkn_jab_cek+"~"+tkn_jab_stuju+"~"+tkn_jab_tetap+"~"+tkn_jab_kendali+"~"+tkn_jab_lap+"~"+rasionale+"~"+pihak_terkait_capaian+"~"+definisi+"~"+referensi+"~"+tglsta+"~"+tglend+"~"+dok_terkait;
        		tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        		li.add(tmp);
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
    
    
    public int pastikanStandarTableSudahAdaVersiPertamaPadaStandarTipeVersion() {
    	int updated=0;
    	Vector v=null;
    	ListIterator li=null;
    	try {
			//int i = 1;
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select distinct ID_STD from STANDARD_TABLE");
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		do {
        			li.add(rs.getString(1));
        		}
        		while(rs.next());
        		
        		li = v.listIterator();
        		stmt = con.prepareStatement("select ID_VERSI from STANDARD_TIPE_VERSION where ID_STD=? and ID_VERSI=1");
        		while(li.hasNext()) {
        			String id_std = (String)li.next();
        			stmt.setInt(1, Integer.parseInt(id_std));
        			rs = stmt.executeQuery();
        			if(rs.next()) { 
        				//sudah ada versi 1 maka remove
        				li.remove();
        			}
        		}
        		if(v!=null&&v.size()>0) {
        			stmt = con.prepareStatement("insert into STANDARD_TIPE_VERSION(ID_STD,ID_VERSI)values(?,?)");
        			li = v.listIterator();
        			while(li.hasNext()) {
        				String id_std = (String)li.next();
        				stmt.setInt(1, Integer.parseInt(id_std));
        				stmt.setInt(2, 1);
        				updated = updated+stmt.executeUpdate();
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
    
    
    public int pastikanStandarTableSudahAdaVersiTerbaruPadaStandarTipeVersion() {
    	int updated=0;
    	Vector v=null;
    	ListIterator li=null;
    	try {
			//int i = 1;
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select distinct ID_STD from STANDARD_TABLE");
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		do {
        			li.add(rs.getString(1));
        		}
        		while(rs.next());
        		
        		li = v.listIterator();
        		stmt = con.prepareStatement("select ID_VERSI,TGL_END from STANDARD_TIPE_VERSION where ID_STD=? order by ID_VERSI desc limit 1");
        		while(li.hasNext()) {
        			String id_std = (String)li.next();
        			stmt.setInt(1, Integer.parseInt(id_std));
        			rs = stmt.executeQuery();
        			if(rs.next()) { 
        				int id_versi = rs.getInt(1);
        				java.sql.Date dt = rs.getDate(2);
        				if(dt!=null) {
        					id_versi++;
        					li.set(id_std+"~"+id_versi);
        				}
        				else {
        					li.remove();
        				}
        			}
        		}
        		if(v!=null&&v.size()>0) {
        			stmt = con.prepareStatement("insert into STANDARD_TIPE_VERSION(ID_STD,ID_VERSI)values(?,?)");
        			li = v.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				StringTokenizer st = new StringTokenizer(brs,"~");
        				String id_std = st.nextToken();
        				String id_versi = st.nextToken();
        				stmt.setInt(1, Integer.parseInt(id_std));
        				stmt.setInt(2, Integer.parseInt(id_versi));
        				updated = updated+stmt.executeUpdate();
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
    
    public int getIdStd(int id_master_std, int id_tipe_std) {
    	int id_std=0;
    	try {
			//int i = 1;
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select ID_STD from STANDARD_TABLE where ID_MASTER_STD=? and ID_TIPE_STD=?");
        	stmt.setInt(1, id_master_std);
        	stmt.setInt(2, id_tipe_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		id_std=rs.getInt(1);
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
    	return id_std;
    }
    
    public Vector getListPernyataanIsiStd(int id_master_std, int id_tipe_std, boolean aktif_only, boolean termasuk_yg_kadaluarsa) {
    	Vector v=null;
    	ListIterator li = null;
    	int id_std=getIdStd(id_master_std,id_tipe_std);
    	try {
			//int i = 1;
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "select DISTINCT PERNYATAAN_STD from STANDARD_ISI_TABLE where ID_STD=?";
        	if(aktif_only) {
        		sql = sql+" AND TGL_MULAI_AKTIF is not null";
        	}
        	if(!termasuk_yg_kadaluarsa) {
        		sql = sql+" AND TGL_STOP_AKTIF is null";
        	}
        	stmt = con.prepareStatement(sql+" order by PERNYATAAN_STD");
        	stmt.setInt(1, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		do {
        			String isi = rs.getString(1);
        			li.add(isi.trim());
        		}
        		while(rs.next());
        	}
        	v = Tool.removeDuplicateFromVector(v);
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
    
    
    public Vector getListPernyataanIsiStdAndIdStdIsiAndId(int id_master_std, int id_tipe_std, String kdpst, boolean aktif_only, boolean termasuk_yg_kadaluarsa) {
    	Vector v=null;
    	ListIterator li = null;
    	int id_std=getIdStd(id_master_std,id_tipe_std);
    	try {
			//int i = 1;
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "select DISTINCT PERNYATAAN_STD from STANDARD_ISI_TABLE where ID_STD=?";
        	if(aktif_only) {
        		sql = sql+" AND TGL_MULAI_AKTIF is not null";
        	}
        	if(!termasuk_yg_kadaluarsa) {
        		sql = sql+" AND TGL_STOP_AKTIF is null";
        	}
        	stmt = con.prepareStatement(sql+" order by PERNYATAAN_STD");
        	stmt.setInt(1, id_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		do {
        			String isi = rs.getString(1);
        			li.add(isi.trim());
        		}
        		while(rs.next());
        	}
        	v = Tool.removeDuplicateFromVector(v);
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
    

    public int getIdStd(String id_master, String id_tipe_std) {
    	int id_std=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt=con.prepareStatement("select ID_STD from STANDARD_TABLE where ID_MASTER_STD="+id_master+" and ID_TIPE_STD="+id_tipe_std);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		id_std=rs.getInt(1);
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
    	return id_std;
    }
    
    public java.sql.Date getTglPenetapan(String id_std, String id_versi, String siklus_ppepp) {
    	java.sql.Date dt = null;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(siklus_ppepp!=null&&siklus_ppepp.equalsIgnoreCase("perumusan")) {
        		stmt=con.prepareStatement("select TGL_KEGIATAN_PERUMUSAN_STD_END from STANDARD_TIPE_VERSION where ID_STD="+id_std+" and ID_VERSI="+id_versi);	
        	}
        	else if(siklus_ppepp!=null&&siklus_ppepp.equalsIgnoreCase("pemeriksaan")) {
        		stmt=con.prepareStatement("select TGL_KEGIATAN_PEMERIKSAAN_STD_END from STANDARD_TIPE_VERSION where ID_STD="+id_std+" and ID_VERSI="+id_versi);	
        	}
        	else if(siklus_ppepp!=null&&siklus_ppepp.equalsIgnoreCase("persetujuan")) {
        		stmt=con.prepareStatement("select TGL_KEGIATAN_PERSETUJUAN_STD_END from STANDARD_TIPE_VERSION where ID_STD="+id_std+" and ID_VERSI="+id_versi);	
        	}
        	else if(siklus_ppepp!=null&&siklus_ppepp.equalsIgnoreCase("penetapan")) {
        		stmt=con.prepareStatement("select TGL_KEGIATAN_PENETAPAN_STD_END from STANDARD_TIPE_VERSION where ID_STD="+id_std+" and ID_VERSI="+id_versi);	
        	}
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		dt = rs.getDate(1);
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
    	return dt;
    }
    
    
    public String getPihakTerkaitDraft(String id_std, String siklus_ppepp) {
    	String pihak_terkait=null;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(siklus_ppepp!=null&&siklus_ppepp.equalsIgnoreCase("perumusan")) {
        		stmt=con.prepareStatement("select TKN_JAB_PERUMUS from STANDARD_MANUAL_PERENCANAAN_UMUM where ID_STD="+id_std+" and TGL_TETAP is not null order by VERSI_ID desc");	
        	}
        	else if(siklus_ppepp!=null&&siklus_ppepp.equalsIgnoreCase("pemeriksaan")) {
        		stmt=con.prepareStatement("select TKN_JAB_PERIKSA from STANDARD_MANUAL_PERENCANAAN_UMUM where ID_STD="+id_std+" and TGL_TETAP is not null order by VERSI_ID desc");	
        	}
        	else if(siklus_ppepp!=null&&siklus_ppepp.equalsIgnoreCase("persetujuan")) {
        		stmt=con.prepareStatement("select TKN_JAB_SETUJU from STANDARD_MANUAL_PERENCANAAN_UMUM where ID_STD="+id_std+" and TGL_TETAP is not null order by VERSI_ID desc");	
        	}
        	else if(siklus_ppepp!=null&&siklus_ppepp.equalsIgnoreCase("penetapan")) {
        		stmt=con.prepareStatement("select TKN_JAB_PENETAP from STANDARD_MANUAL_PERENCANAAN_UMUM where ID_STD="+id_std+" and TGL_TETAP is not null order by VERSI_ID desc");
        	}
        	else if(siklus_ppepp!=null&&siklus_ppepp.equalsIgnoreCase("pengendalian")) {
        		stmt=con.prepareStatement("select TKN_JAB_KENDALI from STANDARD_MANUAL_PERENCANAAN_UMUM where ID_STD="+id_std+" and TGL_TETAP is not null order by VERSI_ID desc");
        	}
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		pihak_terkait = rs.getString(1);
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
    	return pihak_terkait;
    }
    
    

    
    
    public String getPihakTerkaitFinal(String id_std, String id_versi, String siklus_ppepp) {
    	//System.out.println("id_std="+id_std);
    	//System.out.println("id_versi="+id_versi);
    	//System.out.println("siklus_ppepp="+siklus_ppepp);
    	String pihak_terkait=null;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(siklus_ppepp!=null&&siklus_ppepp.equalsIgnoreCase("perumusan")) {
        		stmt=con.prepareStatement("select TKN_JAB_PERUMUS from STANDARD_TIPE_VERSION where ID_STD="+id_std+" and ID_VERSI="+id_versi);	
        	}
        	else if(siklus_ppepp!=null&&siklus_ppepp.equalsIgnoreCase("pemeriksaan")) {
        		stmt=con.prepareStatement("select TKN_JAB_PERIKSA from STANDARD_TIPE_VERSION where ID_STD="+id_std+" and ID_VERSI="+id_versi);	
        	}
        	else if(siklus_ppepp!=null&&siklus_ppepp.equalsIgnoreCase("persetujuan")) {
        		stmt=con.prepareStatement("select TKN_JAB_SETUJU from STANDARD_TIPE_VERSION where ID_STD="+id_std+" and ID_VERSI="+id_versi);	
        	}
        	else if(siklus_ppepp!=null&&siklus_ppepp.equalsIgnoreCase("penetapan")) {
        		stmt=con.prepareStatement("select TKN_JAB_PENETAP from STANDARD_TIPE_VERSION where ID_STD="+id_std+" and ID_VERSI="+id_versi);
        	}
        	else if(siklus_ppepp!=null&&siklus_ppepp.equalsIgnoreCase("pengendalian")) {
        		stmt=con.prepareStatement("select TKN_JAB_KENDALI from STANDARD_TIPE_VERSION where ID_STD="+id_std+" and ID_VERSI="+id_versi);
        	}
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		pihak_terkait = rs.getString(1);
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
    	return pihak_terkait;
    }
    
    
    public boolean apaSudahAdaKegiatanPelaksanaanStandar(String id_std, String id_versi) {
    	/*
    	 * cari kegiatan minimal sama dengan tanggal peetapan standar
    	 */
    	boolean ada=false;
    	try {
    		java.sql.Date tgl_penetapan_std = getTglPenetapan(id_std, id_versi,"penetapan");
    		if(tgl_penetapan_std!=null) {
    			Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	stmt=con.prepareStatement("select ID_PLAN from RIWAYAT_PELAKSANAAN_UMUM where ID_STD="+id_std+" and TGL_STA_KEGIATAN is not null and TGL_END_KEGIATAN>=? limit 1");	
            	stmt.setDate(1, tgl_penetapan_std);	
            	rs = stmt.executeQuery();
            	if(rs.next()) {
            		ada = true;
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
    	return ada;
    }
    
    
    public java.sql.Date getTanggalStandarActivated(String id_std, String id_versi) {
    	java.sql.Date tgl_sta=null;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt=con.prepareStatement("select TGL_STA from STANDARD_TIPE_VERSION where ID_STD="+id_std+" and ID_VERSI="+id_versi);	
        	//stmt.setDate(1, tgl_penetapan_std);	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		tgl_sta=rs.getDate(1);
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
    	return tgl_sta;
    }
    
    public int getVersiDiatasnya(String id_std, String current_id_versi) {
    	int versi = -1;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt=con.prepareStatement("select ID_VERSI from STANDARD_TIPE_VERSION where ID_STD="+id_std+" and ID_VERSI>"+current_id_versi+" order by ID_VERSI limit 1");	
        	//stmt.setDate(1, tgl_penetapan_std);	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		versi=rs.getInt(1);
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
    	return versi;
    }
    
    public int getVersiDibawahnya(String id_std, String current_id_versi) {
    	int versi = -1;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt=con.prepareStatement("select ID_VERSI from STANDARD_TIPE_VERSION where ID_STD="+id_std+" and ID_VERSI<"+current_id_versi+" order by ID_VERSI limit 1");	
        	//stmt.setDate(1, tgl_penetapan_std);	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		versi=rs.getInt(1);
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
    	return versi;
    }
    
    
    public String getPernyataanIsiStd(String id_std_isi) {
    	String isi=null;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt=con.prepareStatement("select PERNYATAAN_STD from STANDARD_ISI_TABLE where ID="+id_std_isi);	
        	//stmt.setDate(1, tgl_penetapan_std);	
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		isi=rs.getString(1);
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
    	return isi;
    }
    
    public Vector getOverviewStatusAktifStandar() {
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("SELECT distinct A.ID_MASTER_STD, A.KET_TIPE_STD,B.ID_STD,B.ID_TIPE_STD,B.KET_TIPE_STD FROM STANDARD_MASTER_TABLE A inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD  where A.ID_MASTER_STD>0 order by A.ID_MASTER_STD,B.ID_STD,B.ID_TIPE_STD");
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		v = new Vector();
        		li = v.listIterator();
        		String tmp = null;
        		do {
        			String id_master_std = rs.getString(1);
        			String ket_master_std = rs.getString(2);
        			String id_std = rs.getString(3);
        			String id_tipe_std = rs.getString(4);
        			String ket_tipe_std = rs.getString(5);
        			tmp = new String(id_master_std+"~"+ket_master_std+"~"+id_std+"~"+id_tipe_std+"~"+ket_tipe_std);
        			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        			li.add(tmp);
        		}
        		while(rs.next());
        		
        		stmt=con.prepareStatement("SELECT C.TGL_STA,C.ID_VERSI FROM STANDARD_MASTER_TABLE A inner join STANDARD_TABLE B on A.ID_MASTER_STD=B.ID_MASTER_STD inner join STANDARD_TIPE_VERSION C on B.ID_STD=C.ID_STD where A.ID_MASTER_STD=? and B.ID_STD=? and C.TGL_STA is not null and C.TGL_END is null");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"~");
        			String id_master_std = st.nextToken();
        			String ket_master_std = st.nextToken();
        			String id_std = st.nextToken();
        			String id_tipe_std = st.nextToken();
        			String ket_tipe_std = st.nextToken();
        			stmt.setInt(1, Integer.parseInt(id_master_std));
            		stmt.setInt(2, Integer.parseInt(id_std));
            		rs = stmt.executeQuery();
            		String tgl_sta = "null";
            		String id_versi = "null";
            		if(rs.next()) {
            			tgl_sta=rs.getString(1);
            			id_versi=rs.getString(2);
            		}
            		tmp = new String(id_master_std+"~"+ket_master_std+"~"+id_std+"~"+id_tipe_std+"~"+ket_tipe_std+"~"+tgl_sta+"~"+id_versi);
        			tmp = Tool.fixDoubleSeperatorAndTknStartEndingWithSeperator(tmp, "~");
        			li.set(tmp);
        			//System.out.println(tmp);
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

}

    
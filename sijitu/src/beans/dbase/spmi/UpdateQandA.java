package beans.dbase.spmi;

import beans.dbase.UpdateDb;
import beans.tools.Checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class UpdateQandA
 */
@Stateless
@LocalBean
public class UpdateQandA extends UpdateDb {
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
    public UpdateQandA() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateQandA(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    
    public int updateQuestionAnswerNilai(String id_std_isi, String id_std_isi_question, String nu_question, String[]answer, String[]nilai) {
    	int updated=0;
    	try {
    		Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	if(Checker.isStringNullOrEmpty(id_std_isi_question)) {
        		//insert
        		stmt = con.prepareStatement("insert into STANDARD_ISI_QUESTION(ID_STD_ISI,QUESTION)values(?,?)");
        		stmt.setInt(1, Integer.parseInt(id_std_isi));
        		stmt.setString(2, nu_question);
        		updated = stmt.executeUpdate();
        		//get id_question
        		stmt = con.prepareStatement("select ID from STANDARD_ISI_QUESTION where QUESTION=? order by ID desc limit 1");
        		stmt.setString(1, nu_question);
        		rs = stmt.executeQuery();
        		rs.next();
        		id_std_isi_question = ""+rs.getInt(1);
        		
        	}
        	else {
        		try {
        			int id = Integer.parseInt(id_std_isi_question);
        			stmt = con.prepareStatement("update STANDARD_ISI_QUESTION set QUESTION=? where ID="+id_std_isi_question);
                	stmt.setString(1, nu_question);
                	updated = stmt.executeUpdate();
                	//delete prev answer
                	stmt = con.prepareStatement("delete from STANDARD_ISI_ANSWER where ID_QUESTION="+id_std_isi_question);
                	stmt.executeUpdate();
        		}
        		catch(Exception e) {
        			e.printStackTrace();
        			System.out.println("id_question bukan integer, cek prosesnya");
        		}
        	}
        	//insert answer & nilai
        	if(answer!=null) {
    			stmt = con.prepareStatement("insert into STANDARD_ISI_ANSWER(ID_QUESTION,ANSWER,BOBOT)values(?,?,?)");
    			for(int i=0;i<answer.length;i++) {
    				if(!Checker.isStringNullOrEmpty(answer[i])) {
    					stmt.setInt(1, Integer.parseInt(id_std_isi_question));
    					stmt.setString(2, answer[i]);
    					if(nilai!=null && nilai.length>=i) {
    						if(!Checker.isStringNullOrEmpty(nilai[i])) {
    							try {
        							float bobot = Float.parseFloat(nilai[i]);
        							stmt.setFloat(3, bobot);
        						}
        						catch(Exception e) {
        							stmt.setNull(3, java.sql.Types.FLOAT);
        						}	
    						}
    						else {
    							stmt.setNull(3, java.sql.Types.FLOAT);
    						}
    						
    					}
    					else {
    						stmt.setNull(3, java.sql.Types.FLOAT);
    					}
    					updated = stmt.executeUpdate();
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
    
}

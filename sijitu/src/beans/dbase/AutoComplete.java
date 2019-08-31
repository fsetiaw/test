package beans.dbase;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class AutoComplete
 */
@Stateless
@LocalBean
public class AutoComplete {
	private int totalCountries;
	private String data = "Afghanistan,	Albania, Zimbabwe";
	private List<String> countries;
    /**
     * Default constructor. 
     */
    public AutoComplete() {
        // TODO Auto-generated constructor stub
    	countries = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(data, ",");
		
		while(st.hasMoreTokens()) {
			countries.add(st.nextToken().trim());
		}
		totalCountries = countries.size();
    }
    
    public List<String> getData(String query) {
		String country = null;
		query = query.toLowerCase();
		List<String> matched = new ArrayList<String>();
		for(int i=0; i<totalCountries; i++) {
			country = countries.get(i).toLowerCase();
			//if(country.startsWith(query)) {
			if(country.contains(query)) {
				matched.add(countries.get(i));
			}
		}
		return matched;
	}

}

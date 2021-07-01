package it.algos.albergo.tableau;

import java.util.HashMap;

/**
 * UserObject di una cella nel grafo delle risorse
 *
 * @author Alessandro Valbonesi
 * @version 1.0 / 16-apr-2014
 */
public class UserObjectRisorsa {


    private int idRisorsa;
    
    private HashMap<String,Object> values = new HashMap<String,Object>();

    /**
     * Ritorna il nome della risorsa (una valore stringa con chiave "name")
     * @return il nome della risorsa
     */
    public String getNome() {
    	String nomeRisorsa="";
    	Object obj = get("name");
    	if ((obj!=null) &&  (obj instanceof String)) {
    		nomeRisorsa=(String)obj;
		}
        return nomeRisorsa;
    }

    public void put(String key, Object value){
    	values.put(key, value);
    }
    
    public Object get(String key){
    	return values.get(key);
    }

    public HashMap<String,Object> getValues(){
    	return values;
    }
    
    public int getIdRisorsa() {
        return idRisorsa;
    }


    public void setIdRisorsa(int idRisorsa) {
        this.idRisorsa = idRisorsa;
    }
    
}

package it.algos.albergo.tableau;

import it.algos.albergo.risorse.Risorsa;
import it.algos.albergo.risorse.RisorsaModulo;
import it.algos.base.modulo.Modulo;

import java.awt.Color;

/**
 * Cella di tipo Risorsa.
 * </p>
 */
public class CellRisorsa extends CellRisorsaAbs {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param idRisorsa di riferimento
     * @param nome della risorsa
     * @param dettglio della risorsa
     */
    public CellRisorsa(int idRisorsa, String nome, String dettglio) {
        super(idRisorsa); // non ha ancora lo UserObject
        
        /* crea e registra uno UserObject con i dati ricevuti */
        UserObjectRisorsa uo = new UserObjectRisorsa();
        uo.setIdRisorsa(idRisorsa);
        uo.put("name", nome);
        uo.put("dett", dettglio);
        this.setUserObject(uo);

    }// fine del metodo costruttore completo

	/**
	 * Crea il componente video
	 */
	public CompRisorsaIF creaComponente(int idRisorsa){
		Color colore=new Color(Color.yellow.getRGB());
		Modulo modRisorsa = RisorsaModulo.get();
		if (modRisorsa!=null) {
	        int idColore = modRisorsa.query().valoreInt(Risorsa.Cam.colore.get(), idRisorsa);
	        if (idColore!=0) {
		        colore = new Color(idColore);
			} else {
		        colore = Risorsa.COLORE_DEFAULT_CELLE;
			}
		}
		CompRisorsaDefault comp = new CompRisorsaDefault(colore);
		return comp;
	}

}

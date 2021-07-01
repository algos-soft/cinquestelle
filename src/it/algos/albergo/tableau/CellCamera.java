package it.algos.albergo.tableau;

import it.algos.albergo.camera.Camera;

/**
 * Cella di tipo Camera.
 * </p>
 */
public class CellCamera extends CellRisorsaAbs {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param nome della camera
     * @param composizione della camera
     * @param codCamera codice camera di riferimento
     */
    public CellCamera(String nome, String composizione, int codCamera) {
        super(codCamera); // non ha ancora lo UserObject
        
        /* crea e registra uno UserObject con i dati ricevuti */
        UserObjectRisorsa uo = new UserObjectRisorsa();
        uo.setIdRisorsa(codCamera);
        uo.put("name", nome);
        uo.put("dett", composizione);
        this.setUserObject(uo);

    }// fine del metodo costruttore completo


	/**
	 * Crea il componente video
	 */
	public CompRisorsaIF creaComponente(int idRisorsa){
		return new CompRisorsaDefault(Camera.COLORE_DEFAULT_CELLE);
	}

} // fine della classe CellCamera}
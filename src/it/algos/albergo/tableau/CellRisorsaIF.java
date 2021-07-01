package it.algos.albergo.tableau;

import java.awt.Color;

public interface CellRisorsaIF {

	/**
	 * Ritorna lo UserObject di questa cella.
	 * <p/>
	 * 
	 * @return lo UserObject
	 */
	public UserObjectRisorsa getUO();

	/**
	 * Assegna una posizione alla cella nel grafo.
	 * <p/>
	 * 
	 * @param posizione
	 *            nella barra (0 per la prima posizione)
	 */
	public void setPosizione(int posizione);

	/**
	 * Ritorna il codice risorsa di questa cella.
	 * <p/>
	 * 
	 * @return il codice risorsa
	 */
	public int getCodice();

	/**
	 * Ritorna lo UserObject di questa cella
	 * @return lo UserObject
	 */
	public Object getUserObject();

	/**
	 * Crea il componente video
	 */
	public CompRisorsaIF creaComponente(int idRisorsa);
	
	/**
	 * Ritorna il componente di interfaccia relativo alla cella
	 * @return il componente
	 */
	public CompRisorsaIF getComponente();
	

}

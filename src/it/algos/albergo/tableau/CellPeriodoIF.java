package it.algos.albergo.tableau;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.GraphCell;

public interface CellPeriodoIF {

	/**
	 * Ritorna lo UserObject di questa cella.
	 * <p/>
	 * 
	 * @return lo UserObject
	 */
	public UserObjectPeriodo getUO();

	/**
	 * Ritorna lo UserObject di questa cella
	 * @return lo UserObject
	 */
	public Object getUserObject();

	/**
	 * Crea il componente video (memorizzato staticamente)
	 */
	public CompPeriodoIF creaComponente();

	/**
	 * Ritorna il componente di interfaccia relativo alla cella
	 * @return il componente
	 */
	public CompPeriodoIF getComponente();
	
    /**
     * Ritorna il codice periodo di prenotazione relativo a questa cella.
     * <p/>
     *
     * @return il codice del periodo di prenotazione
     */
    public int getCodicePeriodo();
    
    /**
     * Ritorna l'id del record da cui ha origine la cella.
     * <p/>
     * In caso di cella di Periodo è il codice del periodo, 
     * in caso di cella di Risorsa è l'id del PeriodoRisorsa
     * ecc...
     *
     * @return l'id sorgente
     */
    public int getIdSorgente();


    /**
     * @return the AttributeMap of the cell
     */
    public AttributeMap getAttributes();
    
    /**
     * Restituisce l'oggetto come GraphCell
     */
    public GraphCell getGraphCell();

}

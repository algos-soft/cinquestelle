package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;

import java.util.Map;

import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphCell;
import org.jgraph.graph.GraphConstants;

public abstract class CellPeriodoAbs extends DefaultGraphCell implements CellPeriodoIF{

	// componente video per rappresentare la cella nella UI
    protected static CompPeriodoIF componente;

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param idRisorsa id della risorsa impegnata
     */
	public CellPeriodoAbs(int idRisorsa) {
		super();
		
        /* crea e registra uno UserObject */
        UserObjectPeriodo uo = new UserObjectPeriodo(idRisorsa);
        setUserObject(uo);

        Map map = this.getAttributes();
        GraphConstants.setSizeableAxis(map, GraphConstants.X_AXIS);

        // crea il componente video nella sottoclasse e lo 
        // memorizza in una variabile statica
        componente = creaComponente();

	}

	@Override
	public CompPeriodoIF getComponente() {
		return componente;
	}

	
    /**
     * Ritorna lo UserObject specifico per questo tipo di cella.
     * <p/>
     * @return lo UserObject
     */
    public UserObjectPeriodo getUO() {
        /* variabili e costanti locali di lavoro */
        UserObjectPeriodo objPeriodo = null;
        Object ogg;

        try {    // prova ad eseguire il codice
            ogg = this.getUserObject();
            if (ogg instanceof UserObjectPeriodo) {
                objPeriodo = (UserObjectPeriodo)ogg;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return objPeriodo;
    }
    

    /**
     * Restituisce l'oggetto come GraphCell
     */
    public GraphCell getGraphCell(){
    	return this;
    }


	/**
	 * Crea un tooltip per la cella
	 * @return il tooltip
	 */
	public static String creaTooltipText(GrafoPrenotazioni graph, UserObjectPeriodo uo){
		return "";
	}
	
    /**
     * Ritorna l'id del record da cui ha origine la cella.
     * <p/>
     * In caso di cella di Periodo è il codice del periodo, 
     * in caso di cella di Risorsa è l'id del PeriodoRisorsa
     * ecc...
     *
     * @return l'id sorgente
     */
    public int getIdSorgente(){
    	return getUO().getInt(UserObjectPeriodo.KEY_IDSORGENTE);
    }


}

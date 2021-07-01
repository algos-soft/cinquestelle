package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.Map;

import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

/**
 * Rappresenta una cella di tipo Risorsa.
 * </p>
 */
public abstract class CellRisorsaAbs extends DefaultGraphCell implements CellRisorsaIF {

	// componente video per rappresentare la cella nella UI
    protected CompRisorsaIF componente;

    /**
     * Costruttore completo con parametri.
     * <p/>
     */
    public CellRisorsaAbs(int idRisorsa) {

        super(null); // non ha ancora lo UserObject


        // crea il componente video nella sottoclasse e lo 
        // memorizza in una variabile statica
        componente = creaComponente(idRisorsa);

    }// fine del metodo costruttore completo


    /**
     * Assegna una posizione alla cella nel grafo.
     * <p/>
     *
     * @param posizione nella barra (0 per la prima posizione)
     */
    public void setPosizione(int posizione) {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice
            /* variabili e costanti locali di lavoro */

            int hCell = Tableau.V_MODULE;
            int wCell = Tableau.W_CELLE_CAMERA;
            int x = 0;
            int y = posizione * hCell;

            Map map = this.getAttributes();
            GraphConstants.setBounds(map, new Rectangle2D.Double(x, y, wCell, hCell));

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna lo UserObject di questa cella.
     * <p/>
     *
     * @return lo UserObject
     */
    public UserObjectRisorsa getUO() {
        /* variabili e costanti locali di lavoro */
        UserObjectRisorsa objRisorsa=null;
        Object ogg;

        try {    // prova ad eseguire il codice

            ogg = this.getUserObject();
            if (ogg instanceof UserObjectRisorsa) {
                objRisorsa = (UserObjectRisorsa)ogg;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return objRisorsa;

    }

    /**
     * Ritorna il codice risorsa di questa cella.
     * <p/>
     *
     * @return il codice risorsa
     */
    public int getCodice() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        UserObjectRisorsa objRisorsa;

        try {    // prova ad eseguire il codice

            objRisorsa = this.getUO();
            codice = objRisorsa.getIdRisorsa();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;

    }

	@Override
	public CompRisorsaIF getComponente() {
		return componente;
	}
    
    
}

package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import java.util.Map;

/**
 * Cella di tipo Nuovo Periodo
 * (provvisoria, dopo la creazione del periodo viene eliminata).
 * </p>
 */
class CellNuovo extends DefaultGraphCell {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     */
    public CellNuovo() {

        super(null); // non ha ancora lo UserObject

        /* crea e registra uno UserObject con i dati ricevuti */
        UserObjectNuovo uo = new UserObjectNuovo();
        this.setUserObject(uo);
        this.inizia();

    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     */
    private void inizia() {

        try { // prova ad eseguire il codice

            Map map = this.getAttributes();
            GraphConstants.setSizeableAxis(map, GraphConstants.X_AXIS);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Ritorna lo UserObject specifico per questo tipo di cella.
     * <p/>
     * @return lo UserObject
     */
    public UserObjectNuovo getUO() {
        /* variabili e costanti locali di lavoro */
        UserObjectNuovo obj = null;
        Object ogg;

        try {    // prova ad eseguire il codice
            ogg = this.getUserObject();
            if (ogg instanceof UserObjectNuovo) {
                obj = (UserObjectNuovo)ogg;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return obj;
    }


    /**
     * Assegna il testo visualizzato.
     * <p/>
     */
    public void setTesto () {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice
            getUO();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }




} // fine della classe
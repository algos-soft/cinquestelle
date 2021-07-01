/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 22 ottobre 2003 alle 12.30
 */
package it.algos.base.campo.tipodati.tipoarchivio;

import it.algos.base.campo.tipodati.TDBase;
import it.algos.base.errore.Errore;

/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Implementare un oggetto astratto che descrive il tipo dati Archivio
 * da associare al campo <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  22 ottobre 2003 alle 12.30
 */
public abstract class TABase extends TDBase implements TipoArchivio {


    /**
     * codice chiave per il tipo dati utilizzato sul database
     */
    private int chiaveTipoDatiDb = 0;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    protected TABase() {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo costruttore base */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo inizia */


    public int getChiaveTipoDatiDb() {
        return chiaveTipoDatiDb;
    }


    protected void setChiaveTipoDatiDb(int chiaveTipoDatiDb) {
        this.chiaveTipoDatiDb = chiaveTipoDatiDb;
    }


}// fine della classe
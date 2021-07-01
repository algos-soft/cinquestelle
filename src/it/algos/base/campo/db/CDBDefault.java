/**
 * Title:        CDBDefault.java
 * Package:      it.algos.base.campo.db
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 22 luglio 2003 alle 16.05
 */

package it.algos.base.campo.db;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Regola le funzionalita di interazione dei Campi col database <br>
 * B - Classe concreta che implementa la superclasse astratta <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  22 luglio 2003 ore 16.05
 */
public final class CDBDefault extends CDBBase {

    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDBDefault() {
        /* rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDBDefault(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

        /* regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo inizia */

}
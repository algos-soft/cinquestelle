/**
 * Title:     TipoDatiMemBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      12-feb-2007
 */
package it.algos.base.database.memoria.tipodati;

import it.algos.base.database.tipodati.TipoDatiBase;
import it.algos.base.errore.Errore;

/**
 * Implementazione astratta di un tipo di dati per un Db Sql
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-ott-2004 ore 13.51.14
 */
public class TipoDatiMemBase extends TipoDatiBase implements TipoDatiMemoria {


    /**
     * Costruttore completo
     * <p/>
     *
     * @param dbSql il database Sql proprietario di questo oggetto
     */
    public TipoDatiMemBase() {
        /* rimanda al costruttore della superclasse */
        super(null);

        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /* messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore base */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     */
    private void inizia() throws Exception {
    } /* fine del metodo inizia */


}// fine della classe

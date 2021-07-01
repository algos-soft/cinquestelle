/**
 * Title:     SqlStandard
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-ott-2004
 */
package it.algos.base.database.sql.implem.standard;

import it.algos.base.database.sql.DbSqlBase;
import it.algos.base.errore.Errore;

/**
 * Database di tipo Sql generico
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-ott-2004 ore 11.18.34
 */
public final class DbSqlStandard extends DbSqlBase {

    /**
     * Costruttore completo.
     * <p/>
     * Non avendo modificatore, puo' essere invocato solo da
     * una classe interna al proprio package.<br>
     * Viene invocato da CreaIstanza.
     */
    DbSqlStandard() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /*
         * Regolazione finale dei dati del database prima della inizializzazione.
         * Deve essere invocato obbligatoriamente da ogni
         * sottoclasse concreta alla fine del metodo Inizia().
         */
        super.regolaDatiDb();

    }// fine del metodo inizia


}// fine della classe

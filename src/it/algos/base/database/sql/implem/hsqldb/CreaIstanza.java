/**
 * Title:     CreaIstanza
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-ott-2004
 */
package it.algos.base.database.sql.implem.hsqldb;

import it.algos.base.database.Db;
import it.algos.base.errore.Errore;

/**
 * Utility per la creazione di un'istanza di questo package.
 * </p>
 * Questa classe astratta factory: <ul>
 * <li> Costruisce un'istanza del Db di questo package <br>
 * <li> Serve per limitare lo scope della classe base di questo
 * package, che cosi puo essere creata solo passando da qui </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-ott-2004 ore 11.33.30
 */
public abstract class CreaIstanza extends Object {

    /**
     * Crea un db di questo package. <br>
     * <p/>
     *
     * @return database appena creato
     */
    public static Db crea() {
        /* variabili e costanti locali di lavoro */
        Db db = null;

        try { // prova ad eseguire il codice
            db = new DbSqlHsqldb();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return db;
    }// fine del metodo

}// fine della classe

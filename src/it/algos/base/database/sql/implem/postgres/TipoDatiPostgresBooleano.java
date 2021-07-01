/**
 * Title:     TipoDatiPostgresBooleano
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      17-mar-2005
 */

package it.algos.base.database.sql.implem.postgres;

import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.tipodati.TipoDatiSqlBooleano;
import it.algos.base.errore.Errore;

import java.sql.Types;

/**
 * Implementazione concreta di un tipo di dati booleano
 * per un Db Postgres
 * </p>
 * <p/>
 * Postgres gestisce il tipo booleano come BIT.<br>
 * Se creo un campo con la keyword BOOLEAN, il
 * driver Jdbc lo restituisce come BIT<br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-ott-2004 ore 13.51.14
 */
public class TipoDatiPostgresBooleano extends TipoDatiSqlBooleano {


    /**
     * Costruttore completo
     * <p/>
     *
     * @param dbSql il database Sql proprietario di questo oggetto
     */
    public TipoDatiPostgresBooleano(DbSql dbSql) {
        /** rimanda al costruttore della superclasse */
        super(dbSql);

        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore base */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     */
    private void inizia() throws Exception {
        this.setTipoJdbc(Types.BIT);
    } /* fine del metodo inizia */

    //-------------------------------------------------------------------------
}// fine della classe
//-----------------------------------------------------------------------------


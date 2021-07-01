/**
 * Title:     TipoDatiSqlBooleano
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-ott-2004
 */

package it.algos.base.database.sql.tipodati;

import it.algos.base.database.sql.DbSql;
import it.algos.base.errore.Errore;

import java.sql.Types;

/**
 * Implementazione concreta di un tipo di dati booleano
 * per un Db Sql
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-ott-2004 ore 13.51.14
 */
public class TipoDatiSqlBooleano extends TipoDatiSqlBase {


    /**
     * Costruttore completo
     * <p/>
     *
     * @param dbSql il database Sql proprietario di questo oggetto
     */
    public TipoDatiSqlBooleano(DbSql dbSql) {
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
        this.setClasseBl(new Boolean(false).getClass());
        this.setTipoJdbc(Types.BOOLEAN);
        this.setKeyword(this.getDatabaseSql().getKeyBooleano());
        this.setValoreVuotoDb(new Boolean(false));
    } /* fine del metodo inizia */


}// fine della classe
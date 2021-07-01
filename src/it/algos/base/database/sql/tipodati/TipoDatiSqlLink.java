/**
 * Title:     TipoDatiSqlLink
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      12-gen-2005
 */

package it.algos.base.database.sql.tipodati;

import it.algos.base.database.sql.DbSql;
import it.algos.base.errore.Errore;

import java.sql.Types;

/**
 * Implementazione concreta di un tipo di dati Link
 * per un Db Sql
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 12-gen-2005 ore 13.02.14
 */
public class TipoDatiSqlLink extends TipoDatiSqlBase {


    /**
     * Costruttore completo
     * <p/>
     *
     * @param dbSql il database Sql proprietario di questo oggetto
     */
    public TipoDatiSqlLink(DbSql dbSql) {
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
        this.setClasseBl(new Integer(0).getClass());
        this.setTipoJdbc(Types.INTEGER);
        this.setKeyword(this.getDatabaseSql().getKeyIntero());
        this.setValoreVuotoDb(null);
    } /* fine del metodo inizia */

}// fine della classe
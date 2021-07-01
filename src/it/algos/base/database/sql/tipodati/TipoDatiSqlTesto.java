/**
 * Title:     TipoDatiSqlTesto
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-ott-2004
 */

package it.algos.base.database.sql.tipodati;

import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.util.OperatoreSql;
import it.algos.base.errore.Errore;

import java.sql.Types;

/**
 * Implementazione concreta di un tipo di dati Testo
 * per un Db Sql
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-ott-2004 ore 13.51.14
 */
public class TipoDatiSqlTesto extends TipoDatiSqlBase {

    /**
     * Costruttore completo
     * <p/>
     *
     * @param dbSql il database Sql proprietario di questo oggetto
     */
    public TipoDatiSqlTesto(DbSql dbSql) {
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
        this.setClasseBl(new String().getClass());
        this.setTipoJdbc(Types.LONGVARCHAR);
        this.setKeyword(this.getDatabaseSql().getKeyTesto());
        this.setWrapperValore(this.getDatabaseSql().getDelimTesto());
        this.setValoreVuotoDb(new String());
    } /* fine del metodo inizia */


    /**
     * Ritorna la stringa rappresentante il valore completata
     * con gli eventuali caratteri di confronto.
     * <p/>
     * Sovrascrive il metodo della superclasse.<br>
     *
     * @param valore l'oggetto valore da convertire
     * @param operatore l'operatore di filtro
     *
     * @return la striga completata
     */
    protected String valoreConfronto(Object valore, OperatoreSql operatore) {
        /* variabili e costanti locali di lavoro */
        String stringa = null;
        String wildcard = null;
        int posizione = 0;

        try { // prova ad eseguire il codice
            wildcard = operatore.getWildcard();
            stringa = valore.toString();

            if (wildcard != null) {
                posizione = operatore.getPosizioneWildcard();
                switch (posizione) {
                    case OperatoreSql.SINISTRA:
                        stringa = wildcard + stringa;
                        break;
                    case OperatoreSql.DESTRA:
                        stringa = stringa + wildcard;
                        break;
                    case OperatoreSql.ENTRAMBE:
                        stringa = wildcard + stringa + wildcard;
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


}// fine della classe
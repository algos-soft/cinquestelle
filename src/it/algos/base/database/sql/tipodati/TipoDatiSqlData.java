/**
 * Title:     TipoDatiSqlData
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-ott-2004
 */

package it.algos.base.database.sql.tipodati;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.sql.DbSql;
import it.algos.base.errore.Errore;

import java.sql.Types;

/**
 * Implementazione concreta di un tipo di dati data
 * per un Db Sql
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-ott-2004 ore 13.51.14
 */
public class TipoDatiSqlData extends TipoDatiSqlBase {


    /**
     * Costruttore completo
     * <p/>
     *
     * @param dbSql il database Sql proprietario di questo oggetto
     */
    public TipoDatiSqlData(DbSql dbSql) {
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
        this.setClasseBl(new java.util.Date(0).getClass());
        this.setTipoJdbc(Types.DATE);
        this.setUsaKeyword(false);
        this.setKeyword(this.getDatabaseSql().getKeyData());
        this.setValoreVuotoDb(null);
        this.setWrapperValore(this.getDatabaseSql().getDelimTesto());
    } /* fine del metodo inizia */


    /**
     * Ritorna la stringa rappresentante un valore generico
     * <p/>
     * Prima di effettuare la conversione a stringa, crea
     * un oggetto java.sql.date da java.util.date
     * perche' il metodo toString di java.sql.date fornisce
     * una stringa DD-MM-YYYY accettata da tutti i database.
     *
     * @param valore l'oggetto valore da convertire
     *
     * @return la stringa rappresentante il valore generico
     */
    protected String valoreGenerico(Object valore) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        java.sql.Date dataSql = null;
        java.util.Date dataUtil = null;
        long tempo = 0;

        try { // prova ad eseguire il codice
            if (valore != null) {
                if (valore instanceof java.util.Date) {
                    dataUtil = (java.util.Date)valore;
                    tempo = dataUtil.getTime();
                    dataSql = new java.sql.Date(tempo);
                    stringa = dataSql.toString();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return stringa;
    }


    /**
     * Converte un valore da livello Database
     * a livello Business Logic per questo tipo di dati e per un dato Campo.
     * <p/>
     * Converte il valore da java.sql.Date a java.util.Date<br>
     * (java.sql.Date e' sottoclasse di java.util.Date)<br>
     * Devo subito convertire a java.util.Date perche' essendo java,sql.Date
     * istanza di java.util.Date, la differenza non viene riconosciuta
     * e i valori memoria e backup diventano di classi diverse e non sono
     * piu' confrontabili.
     *
     * @param valoreIn il valore a livello di Database da convertire
     * @param campo il campo a fronte del quale effettuare la conversione
     *
     * @return il valore convertito al livello di Business Logic
     */
    public Object db2bl(Object valoreIn, Campo campo) {
        /* variabili e costanti locali di lavoro */
        Object valoreOut = null;
        java.util.Date dataIn = null;
        java.util.Date dataOut = null;
        long tempo = 0;

        try {    // prova ad eseguire il codice

            if (valoreIn != null) {
                dataOut = (java.util.Date)valoreIn;
                if (valoreIn instanceof java.util.Date) {
                    dataIn = (java.util.Date)valoreIn;
                    tempo = dataIn.getTime();
                    dataOut = new java.util.Date(tempo);
                }// fine del blocco if
            }// fine del blocco if

            valoreOut = super.db2bl(dataOut, campo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valoreOut;
    }


}// fine della classe
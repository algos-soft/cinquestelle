/**
 * Title:     TipoDatiSqlBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-ott-2004
 */

package it.algos.base.database.sql.tipodati;

import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.util.OperatoreSql;
import it.algos.base.database.tipodati.TipoDatiBase;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import java.lang.reflect.Array;

/**
 * Implementazione astratta di un tipo di dati per un Db Sql
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-ott-2004 ore 13.51.14
 */
public abstract class TipoDatiSqlBase extends TipoDatiBase implements TipoDatiSql {

    /**
     * Codifica tipo del campo JDBC<br>
     * (vedi costanti in Java.Sql.Types)
     */
    private int tipoJdbc = 0;

    /**
     * Flag - usa la keyword SQL nella specifica
     * del valore di un campo nella query
     */
    private boolean usaKeyword = false;

    /**
     * Keyword sql<br>
     * Viene aggiunta all'esterno del valore nelle query,
     * es. DATE '12-08-2004' anziche' '12-08-2004' per un valore di data.<br>
     * Significativo solo se il flag usaKeyword e' acceso.
     */
    private String keyword = null;

    /**
     * Wrapper del valore Sql<br>
     * Racchiude il valore del campo<br>
     * Esempio ' (apice singolo) per il testo
     */
    private String wrapperValore = null;


    /**
     * Costruttore completo
     * <p/>
     *
     * @param dbSql il database Sql proprietario di questo oggetto
     */
    public TipoDatiSqlBase(DbSql dbSql) {
        /* rimanda al costruttore della superclasse */
        super(dbSql);

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


    /**
     * Ritorna una stringa Sql che rappresenta
     * un valore per questo tipo di dato.
     * <p/>
     *
     * @param valore un oggetto valore
     *
     * @return una stringa rappresentante il valore Sql
     */
    public String stringaValore(Object valore) {
        return this.stringaVC(valore, null);
    } /* fine del metodo */


    /**
     * Ritorna una stringa Sql che rappresenta
     * la stringa di confronto per un valore, un
     * operatore e questo tipo di dato.
     * <p/>
     *
     * @param valore l' oggetto valore
     * @param operatore l'operatore di confronto
     *
     * @return una stringa rappresentante la stringa Sql di confronto
     */
    public String stringaConfronto(Object valore, OperatoreSql operatore) {
        return this.stringaVC(valore, operatore);
    }


    /**
     * Ritorna una stringa Sql che rappresenta
     * il valore generico o il valore per confronto (ricerca).
     * <p/>
     * Se l'operatore e' nullo ritorna il valore generico
     * Se l'operatore non e' nullo ritorna il valore per confronto
     *
     * @param valore l'oggetto valore da convertire
     * @param operatore l'operatore Sql di confronto
     *
     * @return una stringa rappresentante il valore Sql
     */
    private String stringaVC(Object valore, OperatoreSql operatore) {
        /** variabili e costanti locali di lavoro */
        String stringaSql = null;
        String wrapper;
        String keyword;

        try { // prova ad eseguire il codice

            /* se il valore e' nullo, ritorna la stringa Sql per null,
*  altrimenti ritorna la stringa Sql specifica per il tipo di valore */
            if (valore != null) {

                /* recupera il wrapper Sql */
                wrapper = this.getWrapperValore();

                /* recupera la keyword Sql */
                keyword = this.getKeyword();

                /* converte il valore in stringa */
                if (operatore != null) {
                    stringaSql = this.getValoreConfronto(valore, operatore);
                } else {
                    stringaSql = valoreGenerico(valore);
                }// fine del blocco if-else

                /* protegge gli eventuali caratteri riservati
            * per il DB */
                // todo rendere specifico del database??
                stringaSql = Lib.Db.proteggiCaratteriRiservati(stringaSql);

                /* Incapsula il valore nell'eventuale wrapper Sql */
                if (Lib.Testo.isValida(wrapper)) {
                    stringaSql = wrapper + stringaSql + wrapper;
                } /* fine del blocco if */

                /* Aggiunge eventualmente la keyword Sql */
                if (this.isUsaKeyword()) {
                    if (Lib.Testo.isValida(keyword)) {
                        stringaSql = keyword + stringaSql;
                    } /* fine del blocco if */
                }// fine del blocco if

            } else {    // il valore e' null
                stringaSql = this.getDatabaseSql().getNullo();
            } /* fine del blocco if-else */
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /** valore di ritorno */
        return stringaSql;

    } /* fine del metodo */



    /**
     * Ritorna la stringa rappresentante il valore completata
     * con gli eventuali caratteri di confronto.
     * <p/>
     * Tratta valori singoli o arrays
     * @param valore l'oggetto valore da convertire
     * @param operatore l'operatore di filtro
     *
     * @return la striga completata
     */
    private String getValoreConfronto(Object valore, OperatoreSql operatore) {
        /* variabili e costanti locali di lavoro */
        String stringa="";
        Class classeIn;
        Object objIn;
        String unaStringa;
        int len;

        try {    // prova ad eseguire il codice

            classeIn = valore.getClass();
            if (classeIn.isArray()) {
                len = Array.getLength(valore);
                for (int k = 0; k < len; k++) {
                    objIn = Array.get(valore, k);
                    unaStringa = this.valoreConfronto(objIn, operatore);
                    if (Lib.Testo.isValida(stringa)) {
                        stringa+=", ";
                    }// fine del blocco if
                    stringa+=unaStringa;
                } // fine del ciclo for
                stringa = "("+stringa+")";
            } else {
                stringa = this.valoreConfronto(valore, operatore);
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }





    /**
     * Ritorna la stringa rappresentante il valore completata
     * con gli eventuali caratteri di confronto.
     * <p/>
     * Sovrascritto dalle sottoclassi.<br>
     * La classe base non considera l'operatore e ritorna il valore generico.
     *
     * @param valore l'oggetto valore da convertire
     * @param operatore l'operatore di filtro
     *
     * @return la striga completata
     */
    protected String valoreConfronto(Object valore, OperatoreSql operatore) {
        return valoreGenerico(valore);
    }


    /**
     * Ritorna la stringa rappresentante un valore generico
     * <p/>
     *
     * @param valore l'oggetto valore da convertire
     *
     * @return la stringa rappresentante il valore generico
     */
    protected String valoreGenerico(Object valore) {
        return valore.toString();
    }


    /**
     * Ritorna la stringa Sql che identifica la sezione tipo
     * nei comandi di creazione delle colonne.
     * <p/>
     *
     * @return la stringa
     */
    public String stringaSqlTipo() {
        /* variabili e costanti locali di lavoro */
        String stringa;

        stringa = this.getKeyword();

        /* valore di ritorno */
        return stringa;
    }


    protected DbSql getDatabaseSql() {
        return (DbSql)this.getDatabase();
    }


    public int getTipoJdbc() {
        return tipoJdbc;
    }


    public boolean isUsaKeyword() {
        return usaKeyword;
    }


    public void setUsaKeyword(boolean usaKeyword) {
        this.usaKeyword = usaKeyword;
    }


    protected void setTipoJdbc(int tipoJdbc) {
        this.tipoJdbc = tipoJdbc;
    }


    public String getKeyword() {
        return keyword;
    }


    protected void setKeyword(String keyword) {
        this.keyword = keyword;
    }


    private String getWrapperValore() {
        return wrapperValore;
    }


    protected void setWrapperValore(String wrapperValore) {
        this.wrapperValore = wrapperValore;
    }

    //-------------------------------------------------------------------------
}// fine della classe
//-----------------------------------------------------------------------------


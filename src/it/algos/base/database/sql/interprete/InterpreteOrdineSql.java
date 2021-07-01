/**
 * Title:     InterpreteFiltro
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2-nov-2004
 */
package it.algos.base.database.sql.interprete;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.util.LibreriaSql;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.query.ordine.Ordine;

import java.util.ArrayList;

/**
 * Interprete di un Ordine per un database Sql generico.
 * <p/>
 * Trasforma un Ordine nel corrispondente comando ORDER BY Sql.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 2-nov-2004 ore 10.35.20
 */
public abstract class InterpreteOrdineSql extends InterpreteSqlBase {


    /**
     * Costruttore completo.<br>
     *
     * @param dbSql il database Sql proprietario
     */
    public InterpreteOrdineSql(DbSql dbSql) {
        /** rimanda al costruttore della superclasse */
        super(dbSql);
    } /* fine del metodo costruttore completo */


    /**
     * Ritorna la stringa Sql corrispondente a un dato Ordine.
     * <p/>
     *
     * @param ordine l'Ordine per il quale costruire la stringa
     *
     * @return la stringa Sql per la clausola Order By
     */
    public String stringaSql(Ordine ordine) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";

        try {    // prova ad eseguire il codice

            /* controlla che non sia nullo o vuoto */
            if (ordine != null) {
                if (ordine.getSize() > 0) {

                    /* Aggiunge la parola chiave WHERE */
                    stringa += this.getDatabaseSql().getOrderBy();

                    /* Aggiunge la stringa Sql per il nodo Root del filtro */
                    stringa += elaboraOrdine(ordine);

                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Elabora un Ordine e ritorna la corrispondente stringa Sql.
     * <p/>
     *
     * @param ordine l'ordine da elaborare
     *
     * @return la stringa Sql relativa all'ordine
     */
    private String elaboraOrdine(Ordine ordine) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        ArrayList elementi = null;
        Ordine.Elemento e = null;
        Campo campo = null;
        String codOperatore = null;
        boolean caseSensitive = false;
        int chiaveTipo = 0;
        boolean isCampoTesto = false;
        String stringaCampo = null;
        String stringaOperatore = null;

        try {    // prova ad eseguire il codice

            /* recupera gli elementi dell'Ordine */
            elementi = ordine.getElementi();

            /* Spazzola gli elementi e costruisce la stringa */
            for (int k = 0; k < elementi.size(); k++) {
                e = (Ordine.Elemento)elementi.get(k);
                campo = e.getCampo();
                codOperatore = e.getOperatore();
                caseSensitive = e.isCaseSensitive();

                /* recupera la chiave del tipo dati del campo e regola il flag isTesto */
                chiaveTipo = campo.getCampoDati().getChiaveTipoDatiDb();
                isCampoTesto = this.getDatabase().isTipoTesto(chiaveTipo);

                /* costruisce la parte relativa al campo */
                stringaCampo = this.getDatabaseSql().getStringaCampoQualificata(campo);
                /* se non case sensitive e campo di tipo testo
                 * introduce la funzione UPPER sul campo */
                if (caseSensitive == false) {
                    if (isCampoTesto) {
                        stringaCampo = LibreriaSql.upper(stringaCampo, this.getDatabaseSql());
                    }// fine del blocco if
                }// fine del blocco if

                /* costruisce la parte relativa all'operatore */
                stringaOperatore = this.getDatabaseSql().getOperatoreOrdine(codOperatore);

                /* aggiunge il separatore campi (virgola) (non la prima volta) */
                if (Lib.Testo.isValida(stringa)) {
                    stringa += this.getDatabaseSql().getSeparatoreCampi();
                }// fine del blocco if

                /* aggiunge alla stringa finale */
                stringa += stringaCampo + stringaOperatore;

            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }

    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.db.DBSelect.java

//-----------------------------------------------------------------------------


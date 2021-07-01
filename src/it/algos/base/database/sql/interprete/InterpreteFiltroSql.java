/**
 * Title:     InterpreteFiltroSql
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      19-ott-2004
 */
package it.algos.base.database.sql.interprete;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.sql.DbSql;
import it.algos.base.database.sql.tipodati.TipoDatiSql;
import it.algos.base.database.sql.util.LibreriaSql;
import it.algos.base.database.sql.util.OperatoreSql;
import it.algos.base.database.tipodati.TipoDati;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroNodo;
import it.algos.base.query.filtro.FiltroNodoOggetto;

import java.util.Enumeration;

/**
 * Interprete di un Filtro per un database Sql generico.
 * <p/>
 * Trasforma un Filtro nel corrispondente comando WHERE Sql.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 19-ott-2004 ore 12.30.47
 */
public abstract class InterpreteFiltroSql extends InterpreteSqlBase {


    /**
     * Costruttore completo.<br>
     *
     * @param dbSql il database Sql proprietario
     */
    public InterpreteFiltroSql(DbSql dbSql) {
        /** rimanda al costruttore della superclasse */
        super(dbSql);
    } /* fine del metodo costruttore completo */


    /**
     * Ritorna la stringa Sql corrispondente a un dato filtro.
     * <p/>
     *
     * @param filtro il Filtro per il quale costruire la Where
     *
     * @return la stringa Sql per la clausola Where
     */
    public String stringaSql(Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";

        try {    // prova ad eseguire il codice

            /* controlla che non sia nullo o vuoto */
            if (filtro != null) {
                if (filtro.getSize() > 0) {

                    /* Aggiunge la parola chiave WHERE */
                    stringa += this.getDatabaseSql().getWhere();

                    /* Aggiunge la stringa Sql per il nodo Root del filtro */
                    stringa += elaboraNodo(filtro.getRootFiltro());

                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Elabora un nodo di un filtro e ritorna la corrispondente stringa Sql.
     * <p/>
     * Se e' una foglia finale, estrae la relativa stringa
     * Se e' un nodo padre, aggiunge la clausola di unione
     * e le parentesi ed elabora ricorsivamente i figli.
     *
     * @param nodo il nodo da elaborare
     *
     * @return la stringa Sql relativa al nodo
     */
    private String elaboraNodo(FiltroNodo nodo) {
        /* variabili e costanti locali di lavoro */
        String stringaFigli = "";
        String stringa = "";
        FiltroNodoOggetto oggetto = null;
        String codUnione = null;

        try {    // prova ad eseguire il codice

            /* recupera i dati dall'oggetto associato */
            oggetto = nodo.getOggetto();
            codUnione = oggetto.getUnione();


            if (nodo.isFinalLeaf()) {    // nodo di tipo finale

                /* aggiunge eventualmente la clausola di unione */
                if (this.usaUnioneNodo(nodo)) {
                    stringa += this.getDatabaseSql().getUnione(codUnione);
                }// fine del blocco if

                /* aggiunge la stringa campo - operatore - valore */
                stringa += this.stringaOggettoNodo(oggetto);

            } else {    // nodo di tipo non finale

                if (nodo.isRoot()) {    // e' il nodo Root

                    /* elabora ricorsivamente i figli */
                    stringa += this.elaboraFigli(nodo);

                    /* Se il nodo ha l'opzione inverso,
                     * racchiude tutto dentro a NOT() */
                    if (oggetto.isInverso()) {
                        stringa = LibreriaSql.not(stringa, this.getDatabaseSql());
                    }// fine del blocco if

                } else {    // non e' il nodo Root

                    /* aggiunge eventualmente la clausola di unione */
                    if (this.usaUnioneNodo(nodo)) {
                        stringa += this.getDatabaseSql().getUnione(codUnione);
                    }// fine del blocco if

                    /* crea una stringa vuota per i figli */
                    stringaFigli = "";

                    /* apre la parentesi */
                    stringaFigli += this.getDatabaseSql().getParentesiAperta();

                    /* elabora ricorsivamente i figli */
                    stringaFigli += this.elaboraFigli(nodo);

                    /* chiude la parentesi */
                    stringaFigli += this.getDatabaseSql().getParentesiChiusa();

                    /* Se il nodo ha l'opzione inverso,
                     * racchiude la stringa figli dentro a NOT() */
                    if (oggetto.isInverso()) {
                        stringaFigli = LibreriaSql.not(stringaFigli, this.getDatabaseSql());
                    }// fine del blocco if

                    /* aggiunge la stringa relativa ai figli */
                    stringa += stringaFigli;

                }// fine del blocco if-else

            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Elabora tutti i figli di un dato nodo del filtro.
     * <p/>
     *
     * @return la stringa Sql relativa ai figli
     */
    private String elaboraFigli(FiltroNodo nodo) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Enumeration figli = null;
        FiltroNodo figlio = null;

        /* recupera i nodi figli di questo nodo */
        figli = nodo.children();

        /* elabora ricorsivamente i figli */
        while (figli.hasMoreElements()) {
            figlio = (FiltroNodo)figli.nextElement();
            stringa += this.elaboraNodo(figlio);
        }
        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna true se deve usare la clausola di unione per un nodo.
     * <p/>
     * Se il nodo e' il primo child del proprio parente,
     * non usa l'unione, altrimenti la usa.
     *
     * @param nodo il nodo da controllare
     */
    private boolean usaUnioneNodo(AlberoNodo nodo) {
        /* variabili e costanti locali di lavoro */
        boolean usaUnione = false;
        AlberoNodo nodoParente = null;
        AlberoNodo primoChild = null;

        try {    // prova ad eseguire il codice
            nodoParente = (AlberoNodo)nodo.getParent();
            primoChild = (AlberoNodo)nodoParente.getFirstChild();
            if (nodo != primoChild) {
                usaUnione = true;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return usaUnione;
    }


    /**
     * Restituisce la stringa Sql relativa a un oggetto
     * di un nodo filtro di tipo foglia finale.
     * <p/>
     *
     * @param o l'oggetto del nodo per il quale creare la stringa
     *
     * @return la stringa Sql relativa all'oggetto
     */
    private String stringaOggettoNodo(FiltroNodoOggetto o) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Campo campo = null;
        String codOperatore = null;
        Object valore = null;
        boolean caseSensitive = false;
        boolean inverso = false;
        int chiaveTipo = 0;
        boolean isCampoTesto = false;
        OperatoreSql operatore = null;
        String stringaCampo = null;
        String stringaOp = null;
        String stringaVal = null;
        TipoDati tipoDati = null;
        TipoDatiSql tipoDatiSql = null;

        try {    // prova ad eseguire il codice

            /* recupera i dati dall'oggetto del nodo */
            campo = o.getCampo();
            codOperatore = o.getOperatore();
            valore = o.getValoreDb();
            caseSensitive = o.isCaseSensitive();
            inverso = o.isInverso();

            /* recupera la chiave del tipo dati del campo e regola il flag isTesto */
            chiaveTipo = campo.getCampoDati().getChiaveTipoDatiDb();
            isCampoTesto = this.getDatabase().isTipoTesto(chiaveTipo);

            /* costruisce la parte relativa al campo */
            stringaCampo = this.getDatabaseSql().getStringaCampoQualificata(campo);
            /* se non case sensitive e campo di tipo testo
             * introduce la funzione UPPER sul campo */
            if (!caseSensitive) {
                if (isCampoTesto) {
                    stringaCampo = LibreriaSql.upper(stringaCampo, this.getDatabaseSql());
                }// fine del blocco if
            }// fine del blocco if

            /* recupera l'operatore Sql */
            operatore = this.getDatabaseSql().getOperatoreFiltro(codOperatore);

            /*
             * Costruisce la parte relativa all'operatore e al valore
             * - Se il valore non e' null, usa l'operatore Sql
             * corrispondente all'operatore generico.
             * - Se il valore e' null, usa l'operatore Sql IS e il valore NULL
             */
            if (valore != null) {

                stringaOp = operatore.getSimbolo();

                /* recupera il tipo dati che gestisce il campo */
                tipoDati = this.getDatabase().getTipoDati(campo);

                /* effettua il casting a tipo Sql */
                tipoDatiSql = (TipoDatiSql)tipoDati;

                /* chiede al tipo dati Sql la stringa relativa al valore */
                stringaVal = tipoDatiSql.stringaConfronto(valore, operatore);

            } else {    // valore nullo

                /* in caso di confronto con valore nullo,
                 * l'operatore puo' essere solo UGUALE o DIVERSO */
                if (codOperatore == Operatore.UGUALE) {
                    stringaOp = this.getDatabaseSql().getIs();
                } else {
                    if (codOperatore == Operatore.DIVERSO) {
                        stringaOp = this.getDatabaseSql().getIs();
                        stringaOp += this.getDatabaseSql().getNot();
                    } else {
                        String t = " In caso di confronto con valori nulli ";
                        t += "Si puo' usare solo l'operatore UGUALE o DIVERSO";
                        throw new Exception(t);
                    }// fine del blocco if-else
                }// fine del blocco if-else

                stringaVal = this.getDatabaseSql().getNullo();

            }// fine del blocco if-else

            /*
             * Se non case sensitive e campo di tipo testo
             * e valore di confronto non nullo,
             * introduce la funzione UPPER sul valore di confronto
             */
            if (caseSensitive == false) {
                if (isCampoTesto) {
                    if (valore != null) {
                        stringaVal = LibreriaSql.upper(stringaVal, this.getDatabaseSql());
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /*
             * Se l'oggetto filtro ha il flag inverso acceso,
             * racchiude il valore in una funzione NOT
             */
            if (inverso) {
                stringaVal = LibreriaSql.not(stringaVal, this.getDatabaseSql());
            }// fine del blocco if

            /* assembla la stringa finale */
            stringa = stringaCampo;
            stringa += stringaOp;
            stringa += stringaVal;

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }

    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.db.DBSelect.java

//-----------------------------------------------------------------------------


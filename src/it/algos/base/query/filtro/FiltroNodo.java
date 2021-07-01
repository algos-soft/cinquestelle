/**
 * Title:     FiltroNodo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      15-ott-2004
 */
package it.algos.base.query.filtro;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

/**
 * Nodo di un albero Filtri.
 * </p>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 7-giu-2004 ore 14.04.00
 */
public class FiltroNodo extends AlberoNodo {

    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * <br>
     * Creates a tree node that has no parent and no children, but which
     * allows children.
     */
    public FiltroNodo() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo. <br>
     * <br>
     * Creates a tree node with no parent, no children, but which allows
     * children, and initializes it with the specified user object.
     *
     * @param unOggetto an Object provided by the user that constitutes
     * the node's data
     */
    public FiltroNodo(Object unOggetto) {
        /* rimanda al costruttore della superclasse */
        super(unOggetto);
    }// fine del metodo costruttore completo


    /**
     * Ritorna l'oggetto associato a questo nodo.
     * <p/>
     *
     * @return l'oggetto associato a questo nodo.
     */
    public FiltroNodoOggetto getOggetto() {
        FiltroNodoOggetto oggettoFiltro = null;
        Object oggettoUtente = null;
        oggettoUtente = this.getUserObject();
        if (oggettoUtente instanceof FiltroNodoOggetto) {
            oggettoFiltro = (FiltroNodoOggetto)oggettoUtente;
        }// fine del blocco if

        /* valore di ritorno */
        return oggettoFiltro;
    }


    /**
     * Ritorna una stringa rappresentante questo nodo.
     * <p/>
     *
     * @return una stringa rappresentante il nodo.
     */
    public String toString() {
        String stringa = "";
        String opzioni = "";
        FiltroNodoOggetto oggetto = null;
        boolean continua;

        try { // prova ad eseguire il codice

            oggetto = this.getOggetto();

            /* Se e' root aggiunge "root"*/
            if (this.isRoot()) {
                stringa += "Root ";
            }// fine del blocco if

            continua = oggetto != null;
            if (continua) {
                /* unione */
                stringa += oggetto.getUnione();

                if (this.isFinalLeaf()) {   // foglia finale
                    stringa += oggetto.toString();
                }// fine del blocco if

                /* parte rappresentante le opzioni */

                /* case sensitive */
                if (oggetto.isCaseSensitive()) {
                    opzioni += " SENS ";
                }// fine del blocco if

                /* inverso */
                if (oggetto.isInverso()) {
                    opzioni += " INV ";
                }// fine del blocco if
            }// fine del blocco if

            /* se ci sono opzioni le fa vedere */
            if (Lib.Testo.isValida(opzioni)) {
                stringa += "(" + opzioni + ")";
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


}// fine della classe

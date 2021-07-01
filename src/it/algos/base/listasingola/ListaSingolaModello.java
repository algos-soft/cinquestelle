/**
 * Title:     ListaSingolaModello
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      02.03.2006
 */
package it.algos.base.listasingola;

import it.algos.base.errore.Errore;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Modello dei dati per una lista a colonna singola.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce un tipo di dati </li>
 * <li> Raggruppa questi dati in un wrapper </li>
 * <li> Estende le funzionalita' della classe standard
 * <code>DefaultListModel</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 19-feb-2004 ore 5.50.32
 */
public class ListaSingolaModello extends DefaultListModel {

    /**
     * Costruttore completo senza parametri. <br>
     * <br>
     */
    public ListaSingolaModello() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Ritorna tutti gli elementi della lista.
     * <p/>
     * @return tutti gli elementi della lista
     */
    public ArrayList<Object> getElementi() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Object> elementi = null;
        int dimensione;
        Object oggetto;

        try {    // prova ad eseguire il codice
            elementi = new ArrayList<Object>();
            dimensione = this.getSize();
            for (int k = 0; k < dimensione; k++) {
                oggetto = this.get(k);
                elementi.add(oggetto);
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return elementi;
    }


    /**
     * Assegna tutti gli elementi della lista.
     * <p/>
     * Svuota la lista e assegna i nuovi elementi
     * @param elementi lista di elementi da assegnare
     */
    public void setElementi(ArrayList<Object> elementi) {

        try {    // prova ad eseguire il codice

            this.removeAllElements();
            for(Object ogg : elementi){
                this.addElement(ogg);
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }



}// fine della classe

/**
 * Title:        RendererElenco.java
 * Package:      it.algos.base.tavola.renderer
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 29 novembre 2003 alle 14.04
 */

package it.algos.base.tavola.renderer;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.elemento.Elemento;
import it.algos.base.errore.Errore;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Implementare la funzione di <i>disegnare</i> una colonna di tipo Elenco <br>
 * B - Serve <strong>solo</strong> per gli elenchi <strong>interni</strong> di
 * radiobottoni e di liste <br>
 *
 * @author Guido Andrea Ceresa, Alberto Colombo e Alessandro Valbonesi
 * @version 1.0  /  29 novembre 2003 ore 14.04
 */
public class RendererElenco extends RendererBase {

    /**
     * flag - controllo di validita della classe
     * la classe Campo che arriva come parametro DEVE essere compatibile
     */
    private boolean isClasseValida = false;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default <br>
     */
    public RendererElenco() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampo nome del campo a cui appartiene la colonna da <i>disegnare</i> <br>
     */
    public RendererElenco(Campo unCampo) {
        /** rimanda al costruttore della superclasse */
        super(unCampo);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try {    // prova ad eseguire il codice
            /* conferma la validita' di questa classe */
            this.setClasseValida(true);

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo inizia */


    public Object rendValue(Object objIn) {
        /* variabili e costanti locali di lavoro */
        int unIntero = 0;
        Object unOggetto = null;
        Object objOut = null;

        try {    // prova ad eseguire il codice
            /* controllo di validita'
             *
             * deve essere valida la classe,
             * non deve essere nulla la lista valori,
             * deve arrivare un valore intero dalla JTable
             */
            if ((this.isClasseValida()) && (objIn instanceof Integer)) {

                /* recupera il valore */
                unIntero = (Integer)objIn;

                /* controllo di congruita' */
                if (unIntero > 0) {
                    unOggetto = campo.getCampoDati().getValoreElenco(unIntero);
                    if (unOggetto != null) {
                        if (unOggetto instanceof Elemento) {
                            objOut = "";
                        } else {
                            objOut = unOggetto.toString();
                        } /* fine del blocco if/else */
                    } else {
                        objOut = "";
                    }// fine del blocco if-else

                } else {
                    super.setValue("");
                } /* fine del blocco if/else */

            } else {
                objOut = objIn;
            } /* fine del blocco if/else */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return objOut;
    }


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setClasseValida(boolean isClasseValida) {
        this.isClasseValida = isClasseValida;
    } /* fine del metodo setter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public boolean isClasseValida() {
        return this.isClasseValida;
    } /* fine del metodo getter */


}   // fine della classe
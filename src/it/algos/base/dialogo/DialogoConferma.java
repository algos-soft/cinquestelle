/**
 * Title:        DialogoBase.java
 * Package:      it.algos.base.dialogo
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 2 novembre 2003 alle 7.53
 */

package it.algos.base.dialogo;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;

/**
 * Dialogo con un bottone di Conferma.
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  2 novembre 2003 ore 7.53
 */
public class DialogoConferma extends DialogoBase {

    /**
     * Costruttore senza parametri.
     * <p/>
     */
    public DialogoConferma() {
        /* invoca il costruttore coi parametri */
        this((Modulo)null);
    } /* fine del metodo costruttore completo */


    /**
     * Costruttore con parametri.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public DialogoConferma(Modulo modulo) {
        /* invoca il costruttore coi parametri */
        this(modulo, "");
    } /* fine del metodo costruttore completo */


    /**
     * Costruttore con parametri.
     * <p/>
     *
     * @param titolo della finestra del dialogo
     */
    public DialogoConferma(String titolo) {
        /* invoca il costruttore coi parametri */
        this(null, titolo);
    } /* fine del metodo costruttore completo */


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo di riferimento
     * @param titolo della finestra del dialogo
     */
    public DialogoConferma(Modulo modulo, String titolo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        /* regolazioni iniziali di riferimenti e variabili */
        try {
            /* regola le variabili di istanza coi parametri */
            this.setTitolo(titolo);

            this.inizia();
        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione <br>
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice
            /* aggiunge il bottone di conferma */
            this.addBottoneConferma();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo inizia */


}// fine della classe

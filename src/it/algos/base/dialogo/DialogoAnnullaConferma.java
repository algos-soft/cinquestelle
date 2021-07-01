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
 * Dialogo con un bottone di Conferma e di Annulla.
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  2 novembre 2003 ore 7.53
 */
public class DialogoAnnullaConferma extends DialogoBase {

    /**
     * Costruttore senza parametri.
     */
    public DialogoAnnullaConferma() {
        /* invoca il costruttore coi parametri */
        this((Modulo)null);
    } /* fine del metodo costruttore */


    /**
     * Costruttore con parametri.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public DialogoAnnullaConferma(Modulo modulo) {
        /* invoca il costruttore coi parametri */
        this(modulo, "");
    } /* fine del metodo costruttore */


    /**
     * Costruttore con parametri.
     * <p/>
     *
     * @param titolo della finestra del dialogo
     */
    public DialogoAnnullaConferma(String titolo) {
        /* invoca il costruttore coi parametri */
        this(null, titolo);
    } /* fine del metodo costruttore */


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo di riferimento
     * @param titolo della finestra del dialogo
     */
    public DialogoAnnullaConferma(Modulo modulo, String titolo) {
        /* rimanda al costruttore della superclasse */
        super(modulo, titolo);

        try {
            /* regolazioni iniziali di riferimenti e variabili */
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

            this.addBottoneConferma();
            this.addBottoneAnnulla();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo inizia */

}// fine della classe

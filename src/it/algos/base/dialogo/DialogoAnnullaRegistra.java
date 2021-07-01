/**
 * Title:     DialogoAnnullaRegistra
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22-giu-2007
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
public class DialogoAnnullaRegistra extends DialogoBase {

    /**
     * Costruttore senza parametri.
     * <p/>
     */
    public DialogoAnnullaRegistra() {
        this((Modulo)null);
    } /* fine del metodo costruttore completo */


    /**
     * Costruttore  con parametri.
     * <p/>
     */
    public DialogoAnnullaRegistra(Modulo modulo) {
        this(modulo, "");
    } /* fine del metodo costruttore completo */


    /**
     * Costruttore  con parametri.
     * <p/>
     */
    public DialogoAnnullaRegistra(String titolo) {
        this(null, titolo);
    } /* fine del metodo costruttore completo */


    /**
     * Costruttore base con parametri.
     * <p/>
     */
    public DialogoAnnullaRegistra(Modulo modulo, String titolo) {

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

            this.addBottoneAnnulla();
            this.addBottoneRegistra();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo inizia */

}// fine della classe

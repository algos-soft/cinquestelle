/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      12-apr-2005
 */
package it.algos.base.componente.bottone;

import it.algos.base.errore.Errore;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 12-apr-2005 ore 16.06.13
 */
public class BottoneDialogo extends BottoneBase {

    /**
     * flag di dismissione dialogo
     */
    private boolean dismetti = false;

    /**
     * flag di conferma dialogo
     */
    private boolean conferma = false;


    /**
     * Costruttore completo senza parametri.
     * <p/>
     */
    public BottoneDialogo() {
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
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    public boolean isDismetti() {
        return dismetti;
    }


    public void setDismetti(boolean dismetti) {
        this.dismetti = dismetti;
    }


    public boolean isConferma() {
        return conferma;
    }


    public void setConferma(boolean conferma) {
        this.conferma = conferma;
    }
}// fine della classe

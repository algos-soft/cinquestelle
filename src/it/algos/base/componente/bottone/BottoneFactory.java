/**
 * Title:        BottoneFactory.java
 * Package:      it.algos.base.componente.bottone
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 31 ottobre 2003 alle 16.41
 */

package it.algos.base.componente.bottone;

import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;

/**
 * Questa classe astratta factory e' responsabile di: <br>
 * A - Costruire un bottone <br>
 *
 * @author Guido Andrea Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  31 ottobre 2003 ore 16.41
 */
public abstract class BottoneFactory {

    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public BottoneFactory() {
        /** rimanda al costruttore della superclasse */
        super();
    } /* fine del metodo costruttore completo */


    /**
     * Costruisce un bottone vuoto per i dialoghi <br>
     * Larghezza, altezza, colore e font standard <br>
     * Azione, ascissa ed ordinata NON vengono regolate <br>
     */
    public static BottoneDialogo creaDialogo(String testo) {
        /* variabili e costanti locali di lavoro */
        BottoneDialogo bottone = null;

        try { // prova ad eseguire il codice
            bottone = creaDialogo(true);
            bottone.getBottone().setText(testo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bottone;
    }


    /**
     * Costruisce un bottone vuoto per i dialoghi <br>
     * Larghezza, altezza, colore e font standard <br>
     * Azione, ascissa ed ordinata NON vengono regolate <br>
     */
    public static BottoneDialogo creaDialogo() {
        return creaDialogo(true);
    }


    /**
     * Costruisce un bottone vuoto per i dialoghi <br>
     * Larghezza, altezza, colore e font standard <br>
     * Azione, ascissa ed ordinata NON vengono regolate <br>
     *
     * @param dismetti flag per controllare la chiusura del dialogo
     */
    public static BottoneDialogo creaDialogo(boolean dismetti) {
        return creaDialogo(null, dismetti);
    }


    /**
     * Costruisce un bottone vuoto per i dialoghi <br>
     * Larghezza, altezza, colore e font standard <br>
     * Azione, ascissa ed ordinata NON vengono regolate <br>
     *
     * @param unAzione azione da associare al bottone
     */
    public static BottoneDialogo creaDialogo(Azione unAzione) {
        return creaDialogo(unAzione, false);
    }


    /**
     * Costruisce un bottone vuoto per i dialoghi <br>
     * Larghezza, altezza, colore e font standard <br>
     * Azione, ascissa ed ordinata NON vengono regolate <br>
     *
     * @param unAzione azione da associare al bottone
     * @param dismette flag per controllare la chiusura del dialogo
     */
    public static BottoneDialogo creaDialogo(Azione unAzione, boolean dismette) {
        /* variabili e costanti locali di lavoro */
        BottoneDialogo unBottone = null;

        try {    // prova ad eseguire il codice

            /* crea un'istanza del bottone */
            unBottone = new BottoneDialogo();

            /* associa l'azione */
            unBottone.setAction(unAzione);

            unBottone.setDismetti(dismette);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unBottone;
    }


}// fine della classe
/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.base.bottone;

import it.algos.base.azione.Azione;
import it.algos.base.componente.bottone.Bottone;
import it.algos.base.componente.bottone.BottoneVuoto;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import javax.swing.*;

/**
 * Factory per la creazione di bottoni con azione.
 * </p>
 * Questa classe astratta factory: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Factory Method</b> </li>
 * <li> Fornisce metodi <code>statici</code> per la  creazione degli oggetti
 * di questo package </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 3-4-05
 */
public abstract class BottoneFactory {

    /**
     * Crea un bottone.
     * <p/>
     * Crea un bottone associato alla classe dell'oggetto <br>
     * Crea un'azione associata al bottone <br>
     * Regola un metodo invocato dall'evento dell'azione <br>
     *
     * @param oggetto proprietario di questo bottone
     * @param metodo invocato dal bottone
     */
    private static BottoneAzione creaBase(Object oggetto, String metodo) {
        /* variabili e costanti locali di lavoro */
        BottoneAzione bottone = null;

        try { // prova ad eseguire il codice
            /* crea l'istanza */
            bottone = new BottoneAzione(oggetto, metodo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bottone;
    }


    /**
     * Crea un bottone.
     * <p/>
     * Crea un bottone associato alla classe dell'oggetto <br>
     * Crea un'azione associata al bottone <br>
     * Regola un metodo invocato dall'evento dell'azione <br>
     * Regola il testo del bottone <br>
     *
     * @param oggetto proprietario di questo bottone
     * @param metodo invocato dal bottone
     * @param titolo testo del bottone
     */
    public static BottoneAzione crea(Object oggetto, String metodo, String titolo) {
        /* variabili e costanti locali di lavoro */
        BottoneAzione bottone = null;

        try { // prova ad eseguire il codice

            /* crea l'istanza */
            bottone = BottoneFactory.creaBase(oggetto, metodo);

            /* regola il parametro */
            bottone.setText(titolo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bottone;
    }


    /**
     * Crea un bottone.
     * <p/>
     * Crea un bottone associato alla classe dell'oggetto <br>
     * Crea un'azione associata al bottone <br>
     * Regola un metodo invocato dall'evento dell'azione <br>
     * Regola l'icona del bottone <br>
     *
     * @param oggetto proprietario di questo bottone
     * @param metodo invocato dal bottone
     * @param icona disegno del bottone
     */
    public static BottoneAzione crea(Object oggetto, String metodo, Icon icona) {
        /* variabili e costanti locali di lavoro */
        BottoneAzione bottone = null;

        try { // prova ad eseguire il codice

            /* crea l'istanza */
            bottone = BottoneFactory.creaBase(oggetto, metodo);

            /* regola il parametro */
            bottone.setIcon(icona);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bottone;
    }


    /**
     * Crea un bottone.
     * <p/>
     * Crea un bottone associato alla classe dell'oggetto <br>
     * Crea un'azione associata al bottone <br>
     * Regola un metodo invocato dall'evento dell'azione <br>
     * Regola l'icona del bottone <br>
     *
     * @param oggetto proprietario di questo bottone
     * @param metodo invocato dal bottone
     * @param nomeIcona nome del disegno del bottone
     */
    public static BottoneAzione creaIcona(Object oggetto, String metodo, String nomeIcona) {
        /* variabili e costanti locali di lavoro */
        BottoneAzione bottone = null;
        Icon icona;

        try { // prova ad eseguire il codice
            /* recupera l'icona con metodo da libreria */
            icona = Lib.Risorse.getIconaBase(nomeIcona);

            /* Invoca il metodo sovrascritto della classe */
            bottone = BottoneFactory.crea(oggetto, metodo, icona);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bottone;
    }


    /**
     * Costruisce un bottone vuoto <br>
     * Larghezza, altezza, colore e font standard <br>
     * Azione, ascissa ed ordinata NON vengono regolate <br>
     *
     * @deprecated
     */
    public static Bottone crea() {
        /* variabili e costanti locali di lavoro */
        Bottone unBottone = null;

        try {    // prova ad eseguire il codice

            /* crea un'istanza del bottone */
            unBottone = new BottoneVuoto();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unBottone;
    } /* fine del metodo */


    /**
     * Costruisce un bottone con azione.
     * <p/>
     * Larghezza, altezza, colore e font standard <br>
     * Ascissa ed ordinata NON vengono regolate <br>
     *
     * @return il bottone appena creato
     *
     * @deprecated
     */
    public static Bottone crea(Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Bottone bottone = null;

        try { // prova ad eseguire il codice
            /* crea un'istanza del bottone */
            bottone = crea();

            /* associa l'azione */
            bottone.setAction(unAzione);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bottone;
    }


    /**
     * Costruisce un bottone con azione e posizione <br>
     * Larghezza, altezza, colore e font standard <br>
     * Ascissa ed ordinata vengono regolate <br>
     *
     * @deprecated
     */
    public static Bottone crea(Azione unAzione, int ascissa, int ordinata) {
        /* variabili e costanti locali di lavoro */
        Bottone unBottone = null;

        try {    // prova ad eseguire il codice

            /** crea un'istanza del bottone con azione associata */
            unBottone = crea(unAzione);

            /** regola la posizione */
            unBottone.setLocation(ascissa, ordinata);

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unBottone;
    } /* fine del metodo */


    /**
     * Costruisce un bottone con azione, posizione e larghezza <br>
     * Altezza, colore e font standard <br>
     * Larghezza, ascissa ed ordinata vengono regolate <br>
     *
     * @deprecated
     */
    public static Bottone crea(Azione unAzione, int ascissa, int ordinata, int larghezza) {
        /* variabili e costanti locali di lavoro */
        Bottone unBottone = null;

        try {    // prova ad eseguire il codice

            /** crea un'istanza del bottone con azione associata e posizione */
            unBottone = crea(unAzione, ascissa, ordinata);

            /** regola la larghezza */
            unBottone.setLarghezza(larghezza);

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unBottone;
    } /* fine del metodo */

} // fine della classe

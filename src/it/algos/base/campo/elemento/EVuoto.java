/**
 * Title:        EVuoto.java
 * Package:      it.algos.base.campo.elemento
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 14 novembre 2003 alle 10.19
 */

package it.algos.base.campo.elemento;

import it.algos.base.errore.Errore;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.*;
import java.awt.*;

/**
 * Elemento vuoto di una JList.
 * </p>
 * Questa classe concreta: <br>
 * A - Costruisce un elemento vuoto da usare nelle lista (JList e JComboBox) <br>
 *
 * @author Guido Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  14 novembre 2003 ore 10.19
 */
public final class EVuoto extends EBase {

    /**
     * eventuale icona mostrata a sinistra del testo
     */
    private static final String ICONA = "puntodidomanda16";

    /**
     * eventuale icona disabiolitata
     */
    private static final String ICONA_DISABILITATA = "puntodidomanda_disab16";

    /**
     * testo che viene mostrato nel popup da questo elemento (riga)
     */
    private static final String TESTO_CORTO = "non spec.";

    public static final String TESTO_LUNGO = "non specificato";

    /**
     * codifica della lunghezza del testo lungo (per decidere quale usare)
     */
    private static final int CORTO_LUNGO = 140;

    /**
     * colore del testo di questo elemento (riga)
     */
    private static final Color COLORE = Color.blue;

    /**
     * valore del codice associato a questo elemento nella matriceDoppia
     */
    private static final int CODICE = 0;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public EVuoto() {
        /* rimanda al costruttore di questa classe */
        this(0);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param larghezzaPopup larghezza del campo nella scheda
     */
    public EVuoto(int larghezzaPopup) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia(larghezzaPopup);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia(int larghezzaPopup) throws Exception {
        /** variabili e costanti locali di lavoro */
        JLabel unaLabel = null;

        try {    // prova ad eseguire il codice

            /* crea l'istanza della etichetta e regola il font */
            unaLabel = new JLabel();
            TestoAlgos.setField(unaLabel);

            /** crea l'icona - attributo della superclasse */
            super.setIcona(ICONA);

            /** crea l'icona - attributo della superclasse */
            super.setIconaDisabilitata(ICONA_DISABILITATA);

            /* regola icona,  testo e colore del testo */
            unaLabel.setIcon(super.getIcona());
            unaLabel.setDisabledIcon(super.getIconaDisabilitata());

            /* seleziona il testo da usare in funzione della larghezza disponibile */
            if (larghezzaPopup > CORTO_LUNGO) {
                unaLabel.setText(TESTO_LUNGO);
            } else {
                unaLabel.setText(TESTO_CORTO);
            }// fine del blocco if-else

            unaLabel.setForeground(COLORE);

            /** regola il componente - attributo della superclasse */
            super.setComponente(unaLabel);

            /** regola il codice - attributo della superclasse */
            super.setCodice(CODICE);

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo inizia */


    /**
     * Sovrascrive il metodo standard della classe Object <br>
     *
     * @return testo che viene mostrato nel popup
     */
    public String toString() {
        return ((JLabel)this.getComponente()).getText();
    } /* fine del metodo */
}// fine della classe

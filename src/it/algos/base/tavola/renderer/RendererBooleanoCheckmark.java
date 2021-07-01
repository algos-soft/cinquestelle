/**
 * Title:     RendererBooleanoPallino
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      16-mar-2007
 */
package it.algos.base.tavola.renderer;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import javax.swing.*;
import java.awt.*;

/**
 * Renderer che mostra un booleano come checkmark grafico quando
 * true, vuoto quando false
 * <p/>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  16-mar-2007 ore 10.25
 */
public class RendererBooleanoCheckmark extends RendererBase {

    Icon icona;

    JLabel label;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public RendererBooleanoCheckmark() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampo
     */
    public RendererBooleanoCheckmark(Campo unCampo) {
        /** rimanda al costruttore della superclasse */
        super(unCampo);

        try {
            this.inizia();
        } catch (Exception unErrore) {
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice
            icona = Lib.Risorse.getIconaBase("checkmark12");
            label = new JLabel();
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo inizia */


    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {

        /* variabili e costanti locali di lavoro */
        boolean flag;

        try { // prova ad eseguire il codice

            label.setIcon(null);
            if ((value != null) && (value instanceof Boolean)) {
                flag = (Boolean)value;
                if (flag) {
                    label.setIcon(icona);
                } /* fine del blocco if/else */
            } /* fine del blocco if */

            /** regola i colori del componente
             * in funzione del fatto che la riga sia
             * selezionata o meno*/
            if (label != null) {
                label.setOpaque(true); // devo rimetterlo se no non funziona
                if (isSelected) {
                    label.setBackground(table.getSelectionBackground());
                    label.setForeground(table.getSelectionForeground());
                } else {
                    label.setBackground(table.getBackground());
                    label.setForeground(table.getForeground());
                }
            }// fine del blocco if

        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return label;
    }


}// fine della classe

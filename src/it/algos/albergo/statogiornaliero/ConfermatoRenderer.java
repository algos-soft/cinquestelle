/**
 * Title:     ConfermatoRenderer
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2-lug-2009
 */
package it.algos.albergo.statogiornaliero;

import it.algos.base.campo.base.Campo;
import it.algos.base.libreria.Libreria;
import it.algos.base.tavola.renderer.RendererBase;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.Component;

/**
 * Renderer per il campo booleano Confermato.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 2-lug-2009 ore 10.36.21
 */
public final class ConfermatoRenderer extends RendererBase {

    StatoGiornaliero stato;

    JCheckBox box;


    /**
     * @param campo di riferimento
     * @param stato oggetto StatoGiornaliero di riferimento
     */
    public ConfermatoRenderer(Campo campo, StatoGiornaliero stato) {
        super(campo);
        this.stato = stato;

        box = new JCheckBox();
        box.setOpaque(false);
        box.setHorizontalAlignment(SwingConstants.CENTER);


    }


    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        /* variabili e costanti locali di lavoro */
        Component comp;
        boolean arrivo, partenza;

        /**
         * Recupera i valori di Arrivo e Partenza
         */
        int chiave = this.getChiaveRiga(row, table);
        arrivo = stato.isArrivo(chiave);
        partenza = stato.isPartenza(chiave);

        /**
         * Sceglie e configura il componente per il rendering
         */
        if (arrivo || partenza) {    // è arrivo o partenza

            boolean acceso = Libreria.getBool(value);
            comp = box;
            box.setSelected(acceso);

        } else {

            comp = super.getTableCellRendererComponent(
                    table, "", isSelected, hasFocus, row, column);

        }// fine del blocco if-else

        /**
         * regola i colori di selezione
         */
        if (isSelected) {
            comp.setForeground(table.getSelectionForeground());
            comp.setBackground(table.getSelectionBackground());
        } else {
            comp.setForeground(table.getForeground());
            comp.setBackground(table.getBackground());
        }

        /* valore di ritorno */
        return comp;

    }


}// fine della classe

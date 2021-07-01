/**
 * Title:     PartenzaConRenderer
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2-lug-2009
 */
package it.algos.albergo.statogiornaliero;

import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.font.FontFactory;
import it.algos.base.libreria.Libreria;
import it.algos.base.tavola.renderer.RendererBase;

import javax.swing.JComboBox;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Component;

/**
 * Renderer per il ComboBox arrivo/partenza con
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 2-lug-2009 ore 10.25.45
 */
public final class APConRenderer extends RendererBase {

    JComboBox combo;
    StatoGiornaliero stato;

    /**
     * @param campo di riferimento
     * @param stato oggetto StatoGiornaliero di riferimento
     */
    public APConRenderer(Campo campo, StatoGiornaliero stato) {
        super(campo);
        this.stato = stato;

        combo = new JComboBox(Periodo.TipiAP.getElementiPartenza());
        combo.setOpaque(true);
        combo.setFont(FontFactory.creaScreenFont(10f));

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

            int cod = Libreria.getInt(value);
            Periodo.TipiAP elem = Periodo.TipiAP.get(cod);
            combo.setSelectedItem(elem);
            comp = combo;

        } else {
            comp = super.getTableCellRendererComponent(
                    table, "", isSelected, hasFocus, row, column);
        }// fine del blocco if-else

        /**
         * regola i colori di selezione
         */
        if (isSelected) {
//            comp.setForeground(table.getSelectionForeground());
            comp.setForeground(Color.blue);
            comp.setBackground(table.getSelectionBackground());
        } else {
            comp.setForeground(table.getForeground());
            comp.setBackground(table.getBackground());
        }

        /* valore di ritorno */
        return comp;

    }


}// fine della classe



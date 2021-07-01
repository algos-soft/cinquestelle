/**
 * Title:     TabTotali
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      5-giu-2009
 */
package it.algos.albergo.statogiornaliero;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.pannello.PannelloBase;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

/**
 * Tabella totali dello Stato Giornaliero
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 5-giu-2009
 */
public class TabTotali extends PannelloBase {

    private String titolo;

    private JTable table;

    private String[] righe = new String[0];


    /**
     * Costruttore completo con parametri
     * <p/>
     * @param titolo della tabella
     * @param righe testi per le righe
     */
    public TabTotali(String titolo, String... righe) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice

            this.setTitolo(titolo);
            this.setRighe(righe);

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice

            this.setLayout(new BorderLayout());
            
            /* crea il bordo con titolo */
            this.creaBordo(this.getTitolo());

            /**
             * Crea la jtable con il numero richiesto di righe e 3 colonne
             * Riempie la prima colonna con i valori di testo delle righe
             */
            JTable table = new totTable(this.getRighe().length,3);

            for (int k = 0; k < this.getRighe().length; k++) {
                String riga =  this.getRighe()[k];
                table.setValueAt(riga,k,0);
            } // fine del ciclo for


            /* regola le larghezze e l'allineamento delle colonne */
            this.regolaColonna(table,0,120, SwingConstants.LEFT);
            this.regolaColonna(table,1,40, SwingConstants.CENTER);
            this.regolaColonna(table,2,60, SwingConstants.CENTER);
            table.setOpaque(false);

            /* registra e aggiunge la JTable*/
            this.setTable(table);
            this.add(table);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Assegna i valori a una riga esistente.
     * <p/>
     * @param chiave - testo della prima colonna, per identificare la riga
     * @param valore - valore numerico per la colonna numero
     * @param dettaglio - valore di testo per la colonna dettaglio
     */
    public void setValore (String chiave, int valore, String dettaglio) {
        /* variabili e costanti locali di lavoro */
        int numRiga;

        try {    // prova ad eseguire il codice
            numRiga = this.getNumRiga(chiave);
            if (numRiga>=0) {
                JTable table = this.getTable();
                table.setValueAt(valore, numRiga, 1);
                table.setValueAt(dettaglio, numRiga, 2);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il numero di riga della tabella corrispondente alla chiave fornita.
     * <p/>
     * @param chiave - testo per identificare la riga
     * @return il numero della riga, -1 se non trovata
     */
    private int getNumRiga (String chiave) {
        /* variabili e costanti locali di lavoro */
        int numRiga  = -1;

        try {    // prova ad eseguire il codice
            TableModel model = this.getTable().getModel();
            for (int k = 0; k < model.getRowCount(); k++) {
                Object ogg = model.getValueAt(k,0);
                String stringa = Libreria.getString(ogg);
                if (stringa!=null) {
                    if (stringa.equals(chiave)) {
                        numRiga=k;
                        break;
                    }// fine del blocco if
                }// fine del blocco if
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return numRiga;
    }



    /**
     * Regola una colonna della JTable.
     * <p/>
     *
     * @param table la jtable
     * @param colonna il numero della colonna
     * @param w la larghezza
     * @param align il tipo di allineamento (da SwingConstants)
     */
    private void regolaColonna(JTable table, int colonna, int w, int align) {
        try {    // prova ad eseguire il codice
            TableColumnModel cmodel = table.getColumnModel();
            TableColumn col = cmodel.getColumn(colonna); //camera
            col.setMinWidth(w);
            col.setPreferredWidth(w);
            col.setCellRenderer(new RendererCelle(align));
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }

    private String getTitolo() {
        return titolo;
    }


    private void setTitolo(String titolo) {
        this.titolo = titolo;
    }


    private JTable getTable() {
        return table;
    }


    private void setTable(JTable table) {
        this.table = table;
    }

    private String[] getRighe() {
        return righe;
    }


    private void setRighe(String[] righe) {
        this.righe = righe;
    }


    /**
     * Classe 'interna'.</p>
     */
    private final class RendererCelle extends DefaultTableCellRenderer {

        int alignment;


        /**
         * Costruttore base senza parametri. <br>
         * Indispensabile anche se non viene utilizzato
         * (anche solo per compilazione in sviluppo) <br>
         * Rimanda al costruttore completo <br>
         * Utilizza eventuali valori di default <br>
         *
         * @param align allineamento da SwingConstants
         */
        public RendererCelle(int align) {
            this.alignment = align;
        }// fine del metodo costruttore base


        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {
            JLabel renderedLabel = (JLabel)super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
            renderedLabel.setHorizontalAlignment(alignment);
            return renderedLabel;
        }
    } // fine della classe 'interna'

    /**
     * JTable non editabile.
     * </p>
     */
    private class totTable extends JTable {

        public totTable(int i, int i1) {
            super(i,i1);
            this.setCellSelectionEnabled(false);
//            this.setColumnSelectionAllowed(false);
//            this.setShowGrid(true);
//            this.setShowHorizontalLines(true);
//            this.setShowVerticalLines(true);
//            this.setOpaque(false);

            this.setShowGrid(true);
            this.setGridColor(Color.gray);
            
        }

        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    } // fine della classe 'interna'


}// fine della classe
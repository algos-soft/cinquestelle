/**
 * Title:     PartenzaConEditor
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2-lug-2009
 */
package it.algos.albergo.statogiornaliero;

import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.tavola.editor.EditorBase;
import it.algos.base.wrapper.Campi;

import javax.swing.JComboBox;
import javax.swing.JTable;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.EventObject;

/**
 * Editor per le celle di Arrivo/Partenza con
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 2-lug-2009 ore 11.18.05
 */
class APConEditor extends EditorBase {

    JComboBox combo;
    StatoGiornaliero stato;
    int currRow;
    JTable currTable;
    ItemListener itemListener;

    /**
     * Costruttore completo con parametri.
     * <p>
     * @param stato oggetto StatoGiornaliero di riferimento
     */
    APConEditor(StatoGiornaliero stato) {
        /* rimanda al costruttore della superclasse */
        super();

        this.stato = stato;

        combo = new JComboBox(Periodo.TipiAP.getElementiPartenza());
        itemListener = new ComboItemListener();
        combo.addItemListener(itemListener);

    }// fine del metodo costruttore completo


    public Component getTableCellEditorComponent(
            JTable jTable, Object value, boolean b, int row, int col) {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            /* memorizza alcuni parametri nel caso che poi voglia registrare */
            currRow=row;
            currTable=jTable;

            /**
             * Recupera il valore corrente
             */
            int cod = Libreria.getInt(value);
            Periodo.TipiAP tipo = Periodo.TipiAP.get(cod);

            /**
             * Assegna il valore corrente al combo.
             * <p>
             * Poiché nel meccanismo degli Editor il componente è sempre lo stesso,
             * devo evitare di generare l'evento di Item Selected per il combo quando
             * è l'editor stesso che regola il combo per la prima volta.
             * Pertanto tolgo provvisoriamente il listener e poi lo rimetto.
             */
            combo.removeItemListener(itemListener);
            combo.setSelectedItem(tipo);
            combo.addItemListener(itemListener);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return combo;
    }


    // This method is called when editing is completed.
    // It must return the new value to be stored in the cell.
    public Object getCellEditorValue() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;

        try { // prova ad eseguire il codice
            Object ogg = combo.getSelectedItem();
            if (ogg != null) {
                if (ogg instanceof Periodo.TipiAP) {
                    Periodo.TipiAP tipo = (Periodo.TipiAP)ogg;
                    codice = tipo.getCodice();
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    // This method is called just before the cell value
    // is saved. If the value is not valid, false should be returned.
    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }


    @Override
    public boolean isCellEditable(EventObject eventObject) {
        return super.isCellEditable(eventObject);
    }


    /**
     * Registra il tipo di arrivo/partenza nel periodo di competenza.
     * <p/>
     * @param tipo di arrivo/partenza
     */
    private void updatePeriodo (Periodo.TipiAP tipo) {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice

            int chiave = this.getChiaveRiga(currRow, currTable);
            boolean arrivo = stato.isArrivo(chiave);
            boolean partenza = stato.isPartenza(chiave);
            int codPeri = stato.getPeriodo(chiave);

            Campi campoCon=null;
            Campi campoCheck=null;
            if (arrivo) {
                campoCon = Periodo.Cam.arrivoCon;
                campoCheck = Periodo.Cam.arrivoConfermato;
            }// fine del blocco if

            if (partenza) {
                campoCon = Periodo.Cam.partenzaCon;
                campoCheck = Periodo.Cam.partenzaConfermata;
            }// fine del blocco if

            if ((campoCon!=null) && (codPeri>0)) {
                PeriodoModulo.get().query().registra(codPeri, campoCon, tipo.getCodice());
                PeriodoModulo.get().query().registra(codPeri, campoCheck, true);
                stato.refreshPeriodo(codPeri);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Invocato quando cambia l'item selezionato nel combo.
     */
    private class ComboItemListener implements ItemListener {

        public void itemStateChanged(ItemEvent itemEvent) {
            
            if (itemEvent.getStateChange()==ItemEvent.SELECTED) {
                Object item = itemEvent.getItem();
                if (item instanceof Periodo.TipiAP) {
                    Periodo.TipiAP tipo = (Periodo.TipiAP)item;
                    updatePeriodo(tipo);
                }// fine del blocco if
            }// fine del blocco if

        }

    } // fine della classe 'interna'


}// fine della classe

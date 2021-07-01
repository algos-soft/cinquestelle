/**
 * Title:     ConfermatoEditor
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

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Component;
import java.util.EventObject;

/**
 * Editor per le celle booleane di Arrivo/Partenza Confermati
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 2-lug-2009 ore 11.18.05
 */
class ConfermatoEditor extends EditorBase {

    JCheckBox box;
    StatoGiornaliero stato;
    int currRow;
    JTable currTable;
    ChangeListener changeListener;

    /**
     * Costruttore completo con parametri.
     * <p>
     * @param stato oggetto StatoGiornaliero di riferimento
     */
    ConfermatoEditor(StatoGiornaliero stato) {
        /* rimanda al costruttore della superclasse */
        super();

        this.stato = stato;

        box = new JCheckBox();
        box.setHorizontalAlignment(SwingConstants.CENTER);

        changeListener = new BoxChangeListener();
        box.addChangeListener(changeListener);

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
            boolean flag = Libreria.getBool(value);

            /**
             * Assegna il valore corrente al componente.
             * <p>
             * Poiché nel meccanismo degli Editor il componente è sempre lo stesso,
             * devo evitare di generare l'evento di Item Selected per il componente quando
             * è l'editor stesso che regola il componente per la prima volta.
             * Pertanto tolgo provvisoriamente il listener e poi lo rimetto.
             */
            box.removeChangeListener(changeListener);
            box.setSelected(flag);
            box.addChangeListener(changeListener);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return box;
    }


    // This method is called when editing is completed.
    // It must return the new value to be stored in the cell.
    public Object getCellEditorValue() {
        return box.isSelected();
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
     * Registra la conferma nel periodo di competenza.
     * <p/>
     * @param flag valore booleano da registrare
     */
    private void updatePeriodo (boolean flag) {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice

            int chiave = this.getChiaveRiga(currRow, currTable);
            boolean arrivo = stato.isArrivo(chiave);
            boolean partenza = stato.isPartenza(chiave);
            int codPeri = stato.getPeriodo(chiave);

            Campi campoCheck=null;
            if (arrivo) {
                campoCheck = Periodo.Cam.arrivoConfermato;
            }// fine del blocco if

            if (partenza) {
                campoCheck = Periodo.Cam.partenzaConfermata;
            }// fine del blocco if

            if ((campoCheck!=null) && (codPeri>0)) {
                PeriodoModulo.get().query().registra(codPeri, campoCheck, flag);
                stato.refreshPeriodo(codPeri);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Invocato quando si preme il check box.
     */
    private class BoxChangeListener implements ChangeListener {

        public void stateChanged(ChangeEvent changeEvent) {
            AbstractButton abstractButton = (AbstractButton) changeEvent.getSource();
            
            boolean armed = abstractButton.getModel().isArmed();
            boolean pressed = abstractButton.getModel().isPressed();
            boolean selected = abstractButton.getModel().isSelected();

            updatePeriodo(pressed);
        }

    } // fine della classe 'interna'


}// fine della classe
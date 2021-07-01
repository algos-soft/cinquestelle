package it.algos.albergo.arrivipartenze;

import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.lista.TavolaModello;
import it.algos.base.tavola.Tavola;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import java.awt.Component;
import java.util.EventObject;

/**
 * Classe 'interna'
 * <p/>
 * Editor del campo Bambino
 */
public final class EditorBambino extends DefaultCellEditor {

    /**
     * Costruttore completo con parametri.
     * <p/>
     */
    public EditorBambino() {
        /* rimanda al costruttore della superclasse */
        super(new JCheckBox());

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public boolean isCellEditable(EventObject eventObject) {
        return true;
    }


    public Component getTableCellEditorComponent(JTable table, Object o, boolean b, int i, int i1) {
        /* variabili e costanti locali di lavoro */
        Component comp = null;

        try { // prova ad eseguire il codice
            comp = super.getTableCellEditorComponent(table, o, b, i, i1);
            comp.setEnabled(false);
            int a = 87;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


    /**
     * Ritorna true se una riga è spuntata col check di arrivo.
     * <p/>
     *
     * @param table la jtable
     * @param riga da esaminare
     *
     * @return true se spuntata
     */
    private boolean isSpuntata(JTable table, int riga) {
        /* variabili e costanti locali di lavoro */
        boolean spuntata = false;
        boolean continua = true;
        Tavola tavola = null;
        TavolaModello modelloDati;
        Dati dati;

        try {    // prova ad eseguire il codice

            if (table == null) {
                continua = false;
            }// fine del blocco if

            if (continua) {
                if (table instanceof Tavola) {
                    tavola = (Tavola)table;
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            if (continua) {
                modelloDati = tavola.getModello();
                dati = modelloDati.getDati();
                spuntata = dati.getBoolAt(riga, ConfermaArrivoDialogo.Nomi.check.get());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return spuntata;
    }


}
package it.algos.albergo.arrivipartenze;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.lista.TavolaModello;
import it.algos.base.tavola.Tavola;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @version 1.0 11/09/98
 */
public class EditorConto extends DefaultCellEditor {

    /**
     * Riferimento al dialogo padre
     */
    private ConfermaArrivoDialogo dialogoPadre;

    private JButton bottone;

    private String label;

    private boolean isPushed;

    private int codRiga;


    /**
     * Costruttore base senza parametri.
     * <p/>
     *
     * @param dialogoPadre proprietario di questo dialogo
     */
    public EditorConto(ConfermaArrivoDialogo dialogoPadre) {
        /* rimanda al costruttore della superclasse */
        super(new JCheckBox());

        try { // prova ad eseguire il codice
            /* regola le variabili di istanza coi parametri */
            this.setDialogoPadre(dialogoPadre);

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
            this.setBottone(ConfermaArrivoDialogo.getBottoneLista());
            this.getBottone().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    try { // prova ad eseguire il codice
                        mostraDialogo();
                        fireEditingStopped();
                    } catch (Exception unErrore) { // intercetta l'errore
                        Errore.crea(unErrore);
                    }// fine del blocco try-catch
                }
            });

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Presenta il dialogo opzioni conto.
     * <p/>
     */
    private void mostraDialogo() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ConfermaArrivoDialogo dialogoPadre;
        OpzioniContoDialogo dialogoOpzioni;
        int codRiga = 0;

        try {    // prova ad eseguire il codice
            dialogoPadre = this.getDialogoPadre();
            continua = (dialogoPadre != null);

            if (continua) {
                codRiga = this.getCodRiga();
                continua = (codRiga > 0);
            }// fine del blocco if

            if (continua) {
                dialogoPadre.editConto(codRiga);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    public Component getTableCellEditorComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 int riga,
                                                 int column) {
        /* variabili e costanti locali di lavoro */
        boolean abilitato;
        int codRiga;

        try { // prova ad eseguire il codice

            abilitato = ConfermaArrivoDialogo.isRigaSpuntata(table, riga);
            if (abilitato) {
//            button.setForeground(table.getSelectionForeground());
//            bottone.setBackground(table.getSelectionBackground());
            } else {
//            button.setForeground(table.getForeground());
//            bottone.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString();
            bottone.setText(label);
            bottone.setEnabled(abilitato);
            isPushed = true;

            codRiga = this.getCodRiga(table, riga);
            this.setCodRiga(codRiga);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bottone;
    }


    public Object getCellEditorValue() {
        /* variabili e costanti locali di lavoro */
        ConfermaArrivoDialogo dialogoPadre;

        try { // prova ad eseguire il codice
            if (isPushed) {

                dialogoPadre = this.getDialogoPadre();
                if (dialogoPadre != null) {
//                    dialogoPadre.editConto();
                }// fine del blocco if
            }

            isPushed = false;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return new String(label);
    }


    /**
     * Ritorna il codice relativo a una riga.
     * <p/>
     *
     * @param table la jtable
     * @param riga da esaminare
     *
     * @return il codice
     */
    private int getCodRiga(JTable table, int riga) {
        /* variabili e costanti locali di lavoro */
        int codcli = 0;
        boolean continua = true;
        Tavola tavola = null;
        TavolaModello modelloDati;
        Dati dati;
        Campo campo;

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
                campo = this.getDialogoPadre().getMemoria().getCampoChiave();
                codcli = dati.getIntAt(riga, campo);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codcli;
    }


    private ConfermaArrivoDialogo getDialogoPadre() {
        return dialogoPadre;
    }


    private void setDialogoPadre(ConfermaArrivoDialogo dialogoPadre) {
        this.dialogoPadre = dialogoPadre;
    }


    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }


    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }


    private JButton getBottone() {
        return bottone;
    }


    private void setBottone(JButton bottone) {
        this.bottone = bottone;
    }


    private int getCodRiga() {
        return codRiga;
    }


    private void setCodRiga(int codRiga) {
        this.codRiga = codRiga;
    }
}

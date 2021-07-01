package it.algos.albergo.presenza;

import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.lista.TavolaModello;
import it.algos.base.modulo.Modulo;
import it.algos.base.tavola.Tavola;
import it.algos.base.tavola.renderer.RendererIcone;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.util.Date;

/**
 * Renderer per i campi data Entrata e data Uscita
 * <p/>
 * Aggiunge alla data l'icona di arrivo, partenza o cambio in funzione dei
 * valori dei relativi campi booleani.
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  16-mar-2007 ore 10.25
 */
class RendererEntrataUscita extends RendererIcone {

    /* label da visualizzare */
    private JLabel label;

    /* flag - true per entrata false per uscita (serve per l'ordine testo-icona) */
    private boolean entrata;

    /* Campo booleano di riferimento per la lettura del flag Cambio */
    private Campi campoBool;


    /**
     * Costruttore completo con parametri
     *
     * @param campo di riferimento
     * @param entrata true per entrata false per uscita
     */
    public RendererEntrataUscita(Campo campo, boolean entrata) {
        /* rimanda al costruttore della superclasse */
        super(campo);

        try {
            /* regolazioni iniziali di riferimenti e variabili */
            this.setEntrata(entrata);
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        JLabel label;
        int pos;
        Icon iconaVero;
        Icon iconaFalso;
        Modulo mod;
        Campi campoRif;

        try { // prova ad eseguire il codice

            /* crea e registra la JLabel da visualizzare */
            label = new JLabel();
            this.setLabel(label);
            label.setOpaque(true);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.CENTER);
            TestoAlgos.setLista(label);

            /* regola alcuni parametri in funzione di entrata o uscita */
            mod = PeriodoModulo.get();
            iconaVero = mod.getIcona("frecciacambio");
            if (this.isEntrata()) {
                iconaFalso = mod.getIcona("automobile");
                pos = SwingConstants.TRAILING;
                campoRif = Presenza.Cam.cambioEntrata;
            } else {
                iconaFalso = mod.getIcona("bandierina");
                pos = SwingConstants.LEADING;
                campoRif = Presenza.Cam.cambioUscita;
            }// fine del blocco if-else
            label.setHorizontalTextPosition(pos);
            this.setIconaVero(iconaVero);
            this.setIconaFalso(iconaFalso);
            this.setCampoBool(campoRif);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo inizia */


    public Component getTableCellRendererComponent(JTable table,
                                                   Object valore,
                                                   boolean selezionata,
                                                   boolean hasFocus,
                                                   int riga,
                                                   int colonna) {

        /* variabili e costanti locali di lavoro */
        Component comp = null;
        int codRecord = 0;
        boolean flag = false;
        Tavola tavola = null;
        TavolaModello modello;
        JLabel label = null;
        Date data = Lib.Data.getVuota();
        Icon icona = null;
        String stringaData = "";

        try { // prova ad eseguire il codice

            /* recupera il codice del record */
            if (table != null) {
                if (table instanceof Tavola) {
                    tavola = (Tavola)table;
                    modello = tavola.getModello();
                    codRecord = modello.getChiave(riga);
                }// fine del blocco if
            }// fine del blocco if

            /* recupera il valore del campo booleano */
            if (codRecord > 0) {
                flag = PresenzaModulo.get().query().valoreBool(this.getCampoBool().get(),
                        codRecord);
            }// fine del blocco if

            /* recupera stringa per la data da visualizzare */
            if (valore != null) {
                if (valore instanceof Date) {
                    data = (Date)valore;
                    stringaData = Lib.Data.getStringa(data);
                }// fine del blocco if
            }// fine del blocco if

            /* recupera l'icona dalla superclasse */
            comp = super.getTableCellRendererComponent(table,
                    flag,
                    selezionata,
                    hasFocus,
                    riga,
                    colonna);
            if (comp != null) {
                if (comp instanceof JLabel) {
                    label = (JLabel)comp;
                    icona = label.getIcon();
                }// fine del blocco if
            }// fine del blocco if

            /* assegna l'icona e la data alla JLabel da visualizzare */
            label = this.getLabel();
            label.setIcon(null);
            label.setText("");
            if (Lib.Testo.isValida(stringaData)) {
                label.setIcon(icona);
                label.setText(stringaData);
            }// fine del blocco if

            /* regola i colori del componente
             * in funzione del fatto che la riga sia
             * selezionata o meno */
            if (label != null) {
                label.setOpaque(true); // devo rimetterlo se no non funziona
                if (selezionata) {
                    label.setBackground(tavola.getSelectionBackground());
                    label.setForeground(tavola.getSelectionForeground());
                } else {
                    label.setBackground(tavola.getBackground());
                    label.setForeground(tavola.getForeground());
                }
            }// fine del blocco if


        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return label;
    }


    private JLabel getLabel() {
        return label;
    }


    private void setLabel(JLabel label) {
        this.label = label;
    }


    private boolean isEntrata() {
        return entrata;
    }


    private void setEntrata(boolean entrata) {
        this.entrata = entrata;
    }


    private Campi getCampoBool() {
        return campoBool;
    }


    private void setCampoBool(Campi campoBool) {
        this.campoBool = campoBool;
    }
}// fine della classe
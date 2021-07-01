package it.algos.albergo.stampeobbligatorie;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.tavola.Tavola;
import it.algos.base.tavola.renderer.RendererBase;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Component;

public abstract class RendererInfo extends RendererBase {

    private Icon iconaValido;

    private Icon iconaNonValido;

    private JLabel label;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param campo di riferimento
     */
    public RendererInfo(Campo campo) {
        /* rimanda al costruttore della superclasse */
        super(campo);

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
        JLabel label;

        try { // prova ad eseguire il codice

            this.setIconaValido(Lib.Risorse.getIconaBase("checkmarkgreen16"));
            this.setIconaNonValido(Lib.Risorse.getIconaBase("Danger16"));

            label = new JLabel();
            label.setOpaque(true);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            this.setLabel(label);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public Component getTableCellRendererComponent(JTable jTable,
                                                   Object o,
                                                   boolean isSelected,
                                                   boolean b1,
                                                   int riga,
                                                   int colonna) {
        /* variabili e costanti locali di lavoro */
        JLabel label = null;
        boolean continua;
        boolean valida;
        Icon icona = null;
        Tavola tavola=null;
        int cod;

        try { // prova ad eseguire il codice

            /* recupera la JLabel */
            label = this.getLabel();
            continua = (label != null);

            /* controlla se la scheda è valida */
            if (continua) {

//                valida = NotificaLogica.isSchedaValida(jTable, riga);
                if (jTable instanceof Tavola) {
                    tavola = (Tavola)jTable;
                }// fine del blocco if
                cod = this.getChiaveRiga(riga, tavola);
                valida = this.isValido(cod);

                /* decide quale icona usare */
                if (valida) {   // riga spuntata
                    icona = this.getIconaValido();
                } else {    // riga non spuntata
                    icona = this.getIconaNonValido();
                }// fine del blocco if-else

                continua = (icona != null);
            } // fine del blocco if

            /** regola i colori della label
             * in funzione del fatto che la riga sia
             * selezionata o meno*/
            if (continua) {
                label.setIcon(icona);
                label.setOpaque(true); // devo rimetterlo se no non funziona
                if (isSelected) {
                    label.setBackground(jTable.getSelectionBackground());
                    label.setForeground(jTable.getSelectionForeground());
                } else {
                    label.setBackground(jTable.getBackground());
                    label.setForeground(jTable.getForeground());
                }
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return label;
    }



    /**
     * Verifica se il codice record è valido.
     * <p/>
     * Sovrascritto dalle sottoclassi
     * @param codice il codice record della riga
     * @return true se è valido
     */
    protected boolean isValido(int codice) {
        return false;
    }


    protected Icon getIconaValido() {
        return iconaValido;
    }


    private void setIconaValido(Icon iconaValido) {
        this.iconaValido = iconaValido;
    }


    protected Icon getIconaNonValido() {
        return iconaNonValido;
    }


    private void setIconaNonValido(Icon iconaNonValido) {
        this.iconaNonValido = iconaNonValido;
    }


    protected JLabel getLabel() {
        return label;
    }


    private void setLabel(JLabel label) {
        this.label = label;
    }
}
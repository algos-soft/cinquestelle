package it.algos.albergo.stampeobbligatorie.testastampe;

import it.algos.albergo.stampeobbligatorie.notifica.NotificaLogica;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.tavola.renderer.RendererBase;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Component;

public class TestaRendererStampato extends RendererBase {

    private Icon iconaStampato;

    private Icon iconaNonStampato;

    private JLabel label;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param campo di riferimento
     */
    public TestaRendererStampato(Campo campo) {
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

            this.setIconaStampato(Lib.Risorse.getIconaBase("lucchettochiuso"));
            this.setIconaNonStampato(Lib.Risorse.getIconaBase("lucchettoaperto"));

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
        boolean stampato;
        Icon icona = null;
        Dati dati;

        try { // prova ad eseguire il codice

            /* recupera la JLabel */
            label = this.getLabel();
            continua = (label != null);

            /* controlla se il giorno è stato stampato */
            if (continua) {
                dati = NotificaLogica.getDati(jTable);
                stampato = dati.getBoolAt(riga, TestaStampe.Cam.stampato.get());

                /* decide quale icona usare */
                if (stampato) {   // riga spuntata
                    icona = this.getIconaStampato();
                } else {    // riga non spuntata
                    icona = this.getIconaNonStampato();
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


    private Icon getIconaStampato() {
        return iconaStampato;
    }


    private void setIconaStampato(Icon iconaStampato) {
        this.iconaStampato = iconaStampato;
    }


    private Icon getIconaNonStampato() {
        return iconaNonStampato;
    }


    private void setIconaNonStampato(Icon iconaNonStampato) {
        this.iconaNonStampato = iconaNonStampato;
    }


    private JLabel getLabel() {
        return label;
    }


    private void setLabel(JLabel label) {
        this.label = label;
    }
}

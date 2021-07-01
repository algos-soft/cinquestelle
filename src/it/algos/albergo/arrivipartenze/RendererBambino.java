package it.algos.albergo.arrivipartenze;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.Component;

/**
 * Classe 'interna'
 * <p/>
 * Renderer del campo Documento Valido
 */
final class RendererBambino extends RendererSpunta {

    private JCheckBox box;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param campo di riferimento
     */
    public RendererBambino(Campo campo) {
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
        JCheckBox box;

        try { // prova ad eseguire il codice

            box = new JCheckBox();
            box.setHorizontalAlignment(SwingConstants.CENTER);
            this.setBox(box);

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
        JCheckBox box = null;
        boolean spuntata;

        try { // prova ad eseguire il codice

            /* recupera il codice cliente e controlla se il documento è valido */
            spuntata = this.isSpuntata(jTable, riga);

            box = this.getBox();
            box.setSelected(Libreria.getBool(o));

            /* decide se abilitare */
            if (spuntata) {   // riga spuntata
                box.setEnabled(true);
            } else {    // riga non spuntata
                box.setEnabled(false);
            }// fine del blocco if-else

            /** regola i colori del componente
             * in funzione del fatto che la riga sia
             * selezionata o meno*/
            box.setOpaque(true); // devo rimetterlo se no non funziona
            if (isSelected) {
                box.setBackground(jTable.getSelectionBackground());
                box.setForeground(jTable.getSelectionForeground());
            } else {
                box.setBackground(jTable.getBackground());
                box.setForeground(jTable.getForeground());
            }


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return box;
    }


    private JCheckBox getBox() {
        return box;
    }


    private void setBox(JCheckBox box) {
        this.box = box;
    }
}
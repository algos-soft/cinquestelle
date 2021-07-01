package it.algos.base.tavola.renderer;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.*;
import java.awt.*;

/**
 * Renderer di una cella (colonna) di una lista.
 * <p/>
 * Basato su un campo booleano con associate due icone, mostra una o l'altra <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  16-mar-2007 ore 10.25
 */
public class RendererIcone extends RendererBase {

    /**
     * Icona associata al valore true del campo booleano
     */
    private Icon iconaVero;

    /**
     * Icona associata al valore false del campo booleano
     */
    private Icon iconaFalso;

    /**
     * label per disegnare l'icona
     */
    private JLabel label;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public RendererIcone() {
        /* rimanda al costruttore di questa classe */
        this(null, null, null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore con parametri
     *
     * @param campo di riferimento
     */
    public RendererIcone(Campo campo) {
        /* rimanda al costruttore di questa classe */
        this(campo, null, null);
    } /* fine del metodo costruttore */


    /**
     * Costruttore completo con parametri
     *
     * @param campo di riferimento
     * @param modulo di riferimento per le icone
     * @param nomeIconaVero associata al valore true del campo booleano
     * @param nomeIconaFalso associata al valore false del campo booleano
     */
    public RendererIcone(Campo campo, Modulo modulo, String nomeIconaVero, String nomeIconaFalso) {
        /* rimanda al costruttore della superclasse */
        super(campo);

        try {
            /* regola le variabili di istanza coi parametri */
            this.setIconaVero(modulo.getIcona(nomeIconaVero));
            this.setIconaFalso(modulo.getIcona(nomeIconaFalso));

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo costruttore completo */


    /**
     * Costruttore completo con parametri
     *
     * @param campo di riferimento
     * @param iconaVero associata al valore true del campo booleano
     * @param iconaFalso associata al valore false del campo booleano
     */
    public RendererIcone(Campo campo, Icon iconaVero, Icon iconaFalso) {
        /* rimanda al costruttore della superclasse */
        super(campo);

        try {
            /* regola le variabili di istanza coi parametri */
            this.setIconaVero(iconaVero);
            this.setIconaFalso(iconaFalso);

            /* regolazioni iniziali di riferimenti e variabili */
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

        try { // prova ad eseguire il codice
            label = this.setLabel(new JLabel());
            label.setOpaque(true);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.CENTER);
            TestoAlgos.setListaBold(label);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo inizia */


    public Component getTableCellRendererComponent(JTable tavola,
                                                   Object valore,
                                                   boolean selezionata,
                                                   boolean hasFocus,
                                                   int riga,
                                                   int colonna) {

        /* variabili e costanti locali di lavoro */
        boolean flag;
        JLabel label = null;

        try { // prova ad eseguire il codice
            label = this.getLabel();
            label.setIcon(null);
            if ((valore != null) && (valore instanceof Boolean)) {
                flag = (Boolean)valore;
                if (flag) {
                    label.setIcon(this.getIconaVero());
                } else {
                    label.setIcon(this.getIconaFalso());
                }// fine del blocco if-else
            } /* fine del blocco if */

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


    private Icon getIconaVero() {
        return iconaVero;
    }


    public void setIconaVero(Icon iconaVero) {
        this.iconaVero = iconaVero;
    }


    private Icon getIconaFalso() {
        return iconaFalso;
    }


    public void setIconaFalso(Icon iconaFalso) {
        this.iconaFalso = iconaFalso;
    }


    private JLabel getLabel() {
        return label;
    }


    private JLabel setLabel(JLabel label) {
        this.label = label;
        return this.getLabel();
    }
}// fine della classe

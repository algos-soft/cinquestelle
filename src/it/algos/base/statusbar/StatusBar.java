/**
 * Title:     StatusBar
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      18-giu-2006
 */
package it.algos.base.statusbar;

import it.algos.base.costante.CostanteColore;
import it.algos.base.errore.Errore;
import it.algos.base.progressbar.ProgressBar;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.*;
import java.awt.*;

/**
 * Barra di stato, usata generalmente nelle finestre
 * </p>
 * Puo' contenere tre componenti, uno a sinistra, uno al centro e uno a destra
 * Di default contiene:
 * - a sinistra una JLabel, con testo sostituibile
 * - al centro nulla
 * - a destra una Progress Bar
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 18-giu-2006 ore 23.58.27
 */
public final class StatusBar extends JPanel {

    /**
     * componenti della StatusBar
     */
    private JComponent compSx, compCentro, compDx;

    /**
     * componente di tipo label
     */
    private JLabel label;

    /**
     * componente di tipo progress bar
     */
    private ProgressBar progressBar;


    /**
     * Costruttore completo senza parametri.<br>
     */
    public StatusBar() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
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
        JLabel label;

        /* regola il layout */
        this.setLayout(new LayoutStatusBar());

        /* colore dello sfondo del pannello */
        this.setBackground(CostanteColore.SFONDO_FINESTRA_LISTA_STATUSBAR);
        this.setOpaque(true);

        /* i bordi sono top, left, bottom, rigth */
        this.setBorder(BorderFactory.createEmptyBorder(3, 7, 2, 20));

        /* crea la JLabel */
        label = new JLabel();
        this.setLabel(label);
        TestoAlgos.setStatusBarFinestra(label);
        label.setOpaque(false);
        this.setLabel(label);

        /* crea la progress bar */
        this.setProgressBar(new ProgressBar());

        /* aggiunge label e progress bar */
        this.setLeftComponent(this.getLabel());
        this.setRightComponent(this.getProgressBar());

        /* di default rende la ProgressBar invisibile */
//        this.setProgressBarVisibile(false);
        this.setProgressBarVisibile(true);

    }


    /**
     * Sostituisce il componente sinistro della StatusBar
     * <p/>
     *
     * @param componente da assegnare
     */
    public void setLeftComponent(JComponent componente) {
        this.setComponent(componente, BorderLayout.WEST);
    }


    /**
     * Sostituisce il componente centrale della StatusBar
     * <p/>
     *
     * @param componente da assegnare
     */
    public void setCenterComponent(JComponent componente) {
        this.setComponent(componente, BorderLayout.CENTER);
    }


    /**
     * Sostituisce il componente destro della StatusBar
     * <p/>
     *
     * @param componente da assegnare
     */
    public void setRightComponent(JComponent componente) {
        this.setComponent(componente, BorderLayout.EAST);
    }


    /**
     * Sostituisce un componente alla StatusBar in una data posizione.
     * <p/>
     *
     * @param componente da assegnare
     * @param pos la posizione
     *
     * @see BorderLayout
     */
    private void setComponent(JComponent componente, String pos) {
        /* variabili e costanti locali di lavoro */
        JComponent compRemove = null;

        try {    // prova ad eseguire il codice

            /* determina quale componente eliminare */
            if (pos.equals(BorderLayout.EAST)) {
                compRemove = this.getCompDx();
                this.setCompDx(componente);
            }// fine del blocco if
            if (pos.equals(BorderLayout.CENTER)) {
                compRemove = this.getCompCentro();
                this.setCompCentro(componente);
            }// fine del blocco if
            if (pos.equals(BorderLayout.WEST)) {
                compRemove = this.getCompSx();
                this.setCompSx(componente);
            }// fine del blocco if

            /* elimina il componente */
            if (compRemove != null) {
                this.remove(compRemove);
            }// fine del blocco if

            /* aggiunge il nuovo componente */
            this.add(componente, pos);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il testo della label.
     * <p/>
     *
     * @param testo da assegnare
     */
    public void setTesto(String testo) {
        try {    // prova ad eseguire il codice
            this.getLabel().setText(testo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Rende la progress bar visibile o invisibile.
     * <p/>
     *
     * @param flag per rendere la ProgressBar visibile o invisibile
     */
    public void setProgressBarVisibile(boolean flag) {
        try {    // prova ad eseguire il codice
            this.getProgressBar().setVisible(flag);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    private JComponent getCompSx() {
        return compSx;
    }


    private void setCompSx(JComponent compSx) {
        this.compSx = compSx;
    }


    private JComponent getCompCentro() {
        return compCentro;
    }


    private void setCompCentro(JComponent compCentro) {
        this.compCentro = compCentro;
    }


    private JComponent getCompDx() {
        return compDx;
    }


    private void setCompDx(JComponent compDx) {
        this.compDx = compDx;
    }


    private JLabel getLabel() {
        return label;
    }


    private void setLabel(JLabel label) {
        this.label = label;
    }


    public ProgressBar getProgressBar() {
        return progressBar;
    }


    private void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }


    /**
     * Classe interna - layout manager della StatusBar.
     * </p>
     * Considera anche le dimensioni dei componenti invisibili
     *
     * @author Guido Andrea Ceresa, Alessandro Valbonesi
     * @author alex
     * @version 1.0    / 04-02-2005 ore 17.05.26
     */
    private class LayoutStatusBar extends BorderLayout {


        private boolean visibileSx, visibileCen, visibileDx;


        /**
         * Costruttore completo.
         * <p/>
         * Indispensabile anche se non viene utilizzato
         * (anche solo per compilazione in sviluppo) <br>
         */
        public LayoutStatusBar() {
            /* rimanda al costruttore della superclasse */
            super();

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }// fine del metodo costruttore completo


        /**
         * Regolazioni statiche di riferimenti e variabili.
         * </p>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
        }// fine del metodo inizia


        /**
         * Ritorna la dimensione preferita
         * <p/>
         * Rende provvisoriamente visibili i componenti invisibili
         *
         * @param contenitore il contenitore
         */
        public Dimension preferredLayoutSize(Container contenitore) {
            /* variabili e costanti locali di lavoro */
            Dimension dim = null;

            try { // prova ad eseguire il codice
                this.setVisibile();
                dim = super.preferredLayoutSize(contenitore);
                this.ripristinaVisibile();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return dim;
        }


        /**
         * Ritorna la dimensione minima
         * <p/>
         * Rende provvisoriamente visibili i componenti invisibili
         *
         * @param contenitore il contenitore
         */
        public Dimension minimumLayoutSize(Container contenitore) {
            /* variabili e costanti locali di lavoro */
            Dimension dim = null;

            try { // prova ad eseguire il codice
                this.setVisibile();
                dim = super.minimumLayoutSize(contenitore);
                this.ripristinaVisibile();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return dim;
        }


        /**
         * Ritorna la dimensione massima
         * <p/>
         * Rende provvisoriamente visibili i componenti invisibili
         *
         * @param contenitore il contenitore
         */
        public Dimension maximumLayoutSize(Container contenitore) {
            /* variabili e costanti locali di lavoro */
            Dimension dim = null;

            try { // prova ad eseguire il codice
                this.setVisibile();
                dim = super.maximumLayoutSize(contenitore);
                this.ripristinaVisibile();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return dim;
        }


        /**
         * .
         * <p/>
         */
        private void setVisibile() {
            /* variabili e costanti locali di lavoro */
            JComponent comp;

            try {    // prova ad eseguire il codice
                comp = getCompSx();
                visibileSx = this.setVisibile(comp);

                comp = getCompCentro();
                visibileCen = this.setVisibile(comp);

                comp = getCompDx();
                visibileDx = this.setVisibile(comp);

            } catch (Exception unErrore) {
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Rende un componente visibile.
         * <p/>
         *
         * @return lo stato di visibilita' prima dell'operazione
         */
        private boolean setVisibile(Component comp) {
            /* variabili e costanti locali di lavoro */
            boolean eraVisibile = false;

            try {    // prova ad eseguire il codice
                if (comp != null) {
                    eraVisibile = comp.isVisible();
                    if (!eraVisibile) {
                        comp.setVisible(true);
                    }// fine del blocco if
                }// fine del blocco if
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return eraVisibile;
        }


        /**
         * .
         * <p/>
         */
        private void ripristinaVisibile() {
            try {    // prova ad eseguire il codice
                ripristinaVisibile(getCompSx(), visibileSx);
                ripristinaVisibile(getCompCentro(), visibileCen);
                ripristinaVisibile(getCompDx(), visibileDx);
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * .
         * <p/>
         */
        private void ripristinaVisibile(Component comp, boolean visibile) {
            try {    // prova ad eseguire il codice
                if (comp != null) {
                    comp.setVisible(visibile);
                }// fine del blocco if
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


    }// fine della classe interna


}// fine della classe
